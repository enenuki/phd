/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.AnnotationException;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.annotations.ForeignKey;
/*   8:    */ import org.hibernate.annotations.common.reflection.XClass;
/*   9:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  10:    */ import org.hibernate.cfg.annotations.PropertyBinder;
/*  11:    */ import org.hibernate.internal.util.StringHelper;
/*  12:    */ import org.hibernate.mapping.Column;
/*  13:    */ import org.hibernate.mapping.DependantValue;
/*  14:    */ import org.hibernate.mapping.Join;
/*  15:    */ import org.hibernate.mapping.KeyValue;
/*  16:    */ import org.hibernate.mapping.ManyToOne;
/*  17:    */ import org.hibernate.mapping.OneToOne;
/*  18:    */ import org.hibernate.mapping.PersistentClass;
/*  19:    */ import org.hibernate.mapping.Property;
/*  20:    */ import org.hibernate.mapping.SimpleValue;
/*  21:    */ import org.hibernate.mapping.Value;
/*  22:    */ import org.hibernate.type.ForeignKeyDirection;
/*  23:    */ 
/*  24:    */ public class OneToOneSecondPass
/*  25:    */   implements SecondPass
/*  26:    */ {
/*  27:    */   private String mappedBy;
/*  28:    */   private Mappings mappings;
/*  29:    */   private String ownerEntity;
/*  30:    */   private String ownerProperty;
/*  31:    */   private PropertyHolder propertyHolder;
/*  32:    */   private boolean ignoreNotFound;
/*  33:    */   private PropertyData inferredData;
/*  34:    */   private XClass targetEntity;
/*  35:    */   private boolean cascadeOnDelete;
/*  36:    */   private boolean optional;
/*  37:    */   private String cascadeStrategy;
/*  38:    */   private Ejb3JoinColumn[] joinColumns;
/*  39:    */   
/*  40:    */   public OneToOneSecondPass(String mappedBy, String ownerEntity, String ownerProperty, PropertyHolder propertyHolder, PropertyData inferredData, XClass targetEntity, boolean ignoreNotFound, boolean cascadeOnDelete, boolean optional, String cascadeStrategy, Ejb3JoinColumn[] columns, Mappings mappings)
/*  41:    */   {
/*  42: 76 */     this.ownerEntity = ownerEntity;
/*  43: 77 */     this.ownerProperty = ownerProperty;
/*  44: 78 */     this.mappedBy = mappedBy;
/*  45: 79 */     this.propertyHolder = propertyHolder;
/*  46: 80 */     this.mappings = mappings;
/*  47: 81 */     this.ignoreNotFound = ignoreNotFound;
/*  48: 82 */     this.inferredData = inferredData;
/*  49: 83 */     this.targetEntity = targetEntity;
/*  50: 84 */     this.cascadeOnDelete = cascadeOnDelete;
/*  51: 85 */     this.optional = optional;
/*  52: 86 */     this.cascadeStrategy = cascadeStrategy;
/*  53: 87 */     this.joinColumns = columns;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void doSecondPass(Map persistentClasses)
/*  57:    */     throws MappingException
/*  58:    */   {
/*  59: 92 */     OneToOne value = new OneToOne(this.mappings, this.propertyHolder.getTable(), this.propertyHolder.getPersistentClass());
/*  60:    */     
/*  61:    */ 
/*  62: 95 */     String propertyName = this.inferredData.getPropertyName();
/*  63: 96 */     value.setPropertyName(propertyName);
/*  64: 97 */     String referencedEntityName = ToOneBinder.getReferenceEntityName(this.inferredData, this.targetEntity, this.mappings);
/*  65: 98 */     value.setReferencedEntityName(referencedEntityName);
/*  66: 99 */     AnnotationBinder.defineFetchingStrategy(value, this.inferredData.getProperty());
/*  67:    */     
/*  68:101 */     value.setCascadeDeleteEnabled(this.cascadeOnDelete);
/*  69:104 */     if (!this.optional) {
/*  70:104 */       value.setConstrained(true);
/*  71:    */     }
/*  72:105 */     value.setForeignKeyType(value.isConstrained() ? ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT : ForeignKeyDirection.FOREIGN_KEY_TO_PARENT);
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:110 */     PropertyBinder binder = new PropertyBinder();
/*  78:111 */     binder.setName(propertyName);
/*  79:112 */     binder.setValue(value);
/*  80:113 */     binder.setCascade(this.cascadeStrategy);
/*  81:114 */     binder.setAccessType(this.inferredData.getDefaultAccess());
/*  82:115 */     Property prop = binder.makeProperty();
/*  83:116 */     if (BinderHelper.isEmptyAnnotationValue(this.mappedBy))
/*  84:    */     {
/*  85:123 */       boolean rightOrder = true;
/*  86:125 */       if (rightOrder)
/*  87:    */       {
/*  88:126 */         String path = StringHelper.qualify(this.propertyHolder.getPath(), propertyName);
/*  89:127 */         new ToOneFkSecondPass(value, this.joinColumns, !this.optional, this.propertyHolder.getEntityOwnerClassName(), path, this.mappings).doSecondPass(persistentClasses);
/*  90:    */         
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:134 */         this.propertyHolder.addProperty(prop, this.inferredData.getDeclaringClass());
/*  97:    */       }
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:142 */       PersistentClass otherSide = (PersistentClass)persistentClasses.get(value.getReferencedEntityName());
/* 102:    */       Property otherSideProperty;
/* 103:    */       try
/* 104:    */       {
/* 105:145 */         if (otherSide == null) {
/* 106:146 */           throw new MappingException("Unable to find entity: " + value.getReferencedEntityName());
/* 107:    */         }
/* 108:148 */         otherSideProperty = BinderHelper.findPropertyByName(otherSide, this.mappedBy);
/* 109:    */       }
/* 110:    */       catch (MappingException e)
/* 111:    */       {
/* 112:151 */         throw new AnnotationException("Unknown mappedBy in: " + StringHelper.qualify(this.ownerEntity, this.ownerProperty) + ", referenced property unknown: " + StringHelper.qualify(value.getReferencedEntityName(), this.mappedBy));
/* 113:    */       }
/* 114:157 */       if (otherSideProperty == null) {
/* 115:158 */         throw new AnnotationException("Unknown mappedBy in: " + StringHelper.qualify(this.ownerEntity, this.ownerProperty) + ", referenced property unknown: " + StringHelper.qualify(value.getReferencedEntityName(), this.mappedBy));
/* 116:    */       }
/* 117:164 */       if ((otherSideProperty.getValue() instanceof OneToOne))
/* 118:    */       {
/* 119:165 */         this.propertyHolder.addProperty(prop, this.inferredData.getDeclaringClass());
/* 120:    */       }
/* 121:167 */       else if ((otherSideProperty.getValue() instanceof ManyToOne))
/* 122:    */       {
/* 123:168 */         Iterator it = otherSide.getJoinIterator();
/* 124:169 */         Join otherSideJoin = null;
/* 125:170 */         while (it.hasNext())
/* 126:    */         {
/* 127:171 */           Join otherSideJoinValue = (Join)it.next();
/* 128:172 */           if (otherSideJoinValue.containsProperty(otherSideProperty))
/* 129:    */           {
/* 130:173 */             otherSideJoin = otherSideJoinValue;
/* 131:174 */             break;
/* 132:    */           }
/* 133:    */         }
/* 134:177 */         if (otherSideJoin != null)
/* 135:    */         {
/* 136:179 */           Join mappedByJoin = buildJoinFromMappedBySide((PersistentClass)persistentClasses.get(this.ownerEntity), otherSideProperty, otherSideJoin);
/* 137:    */           
/* 138:    */ 
/* 139:182 */           ManyToOne manyToOne = new ManyToOne(this.mappings, mappedByJoin.getTable());
/* 140:    */           
/* 141:184 */           manyToOne.setIgnoreNotFound(this.ignoreNotFound);
/* 142:185 */           manyToOne.setCascadeDeleteEnabled(value.isCascadeDeleteEnabled());
/* 143:186 */           manyToOne.setEmbedded(value.isEmbedded());
/* 144:187 */           manyToOne.setFetchMode(value.getFetchMode());
/* 145:188 */           manyToOne.setLazy(value.isLazy());
/* 146:189 */           manyToOne.setReferencedEntityName(value.getReferencedEntityName());
/* 147:190 */           manyToOne.setUnwrapProxy(value.isUnwrapProxy());
/* 148:191 */           prop.setValue(manyToOne);
/* 149:192 */           Iterator otherSideJoinKeyColumns = otherSideJoin.getKey().getColumnIterator();
/* 150:193 */           while (otherSideJoinKeyColumns.hasNext())
/* 151:    */           {
/* 152:194 */             Column column = (Column)otherSideJoinKeyColumns.next();
/* 153:195 */             Column copy = new Column();
/* 154:196 */             copy.setLength(column.getLength());
/* 155:197 */             copy.setScale(column.getScale());
/* 156:198 */             copy.setValue(manyToOne);
/* 157:199 */             copy.setName(column.getQuotedName());
/* 158:200 */             copy.setNullable(column.isNullable());
/* 159:201 */             copy.setPrecision(column.getPrecision());
/* 160:202 */             copy.setUnique(column.isUnique());
/* 161:203 */             copy.setSqlType(column.getSqlType());
/* 162:204 */             copy.setCheckConstraint(column.getCheckConstraint());
/* 163:205 */             copy.setComment(column.getComment());
/* 164:206 */             copy.setDefaultValue(column.getDefaultValue());
/* 165:207 */             manyToOne.addColumn(copy);
/* 166:    */           }
/* 167:209 */           mappedByJoin.addProperty(prop);
/* 168:    */         }
/* 169:    */         else
/* 170:    */         {
/* 171:212 */           this.propertyHolder.addProperty(prop, this.inferredData.getDeclaringClass());
/* 172:    */         }
/* 173:215 */         value.setReferencedPropertyName(this.mappedBy);
/* 174:    */         
/* 175:217 */         String propertyRef = value.getReferencedPropertyName();
/* 176:218 */         if (propertyRef != null) {
/* 177:219 */           this.mappings.addUniquePropertyReference(value.getReferencedEntityName(), propertyRef);
/* 178:    */         }
/* 179:    */       }
/* 180:    */       else
/* 181:    */       {
/* 182:226 */         throw new AnnotationException("Referenced property not a (One|Many)ToOne: " + StringHelper.qualify(otherSide.getEntityName(), this.mappedBy) + " in mappedBy of " + StringHelper.qualify(this.ownerEntity, this.ownerProperty));
/* 183:    */       }
/* 184:    */     }
/* 185:236 */     ForeignKey fk = (ForeignKey)this.inferredData.getProperty().getAnnotation(ForeignKey.class);
/* 186:237 */     String fkName = fk != null ? fk.name() : "";
/* 187:238 */     if (!BinderHelper.isEmptyAnnotationValue(fkName)) {
/* 188:238 */       value.setForeignKeyName(fkName);
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   private Join buildJoinFromMappedBySide(PersistentClass persistentClass, Property otherSideProperty, Join originalJoin)
/* 193:    */   {
/* 194:252 */     Join join = new Join();
/* 195:253 */     join.setPersistentClass(persistentClass);
/* 196:    */     
/* 197:    */ 
/* 198:256 */     join.setTable(originalJoin.getTable());
/* 199:257 */     join.setInverse(true);
/* 200:258 */     SimpleValue key = new DependantValue(this.mappings, join.getTable(), persistentClass.getIdentifier());
/* 201:    */     
/* 202:260 */     join.setKey(key);
/* 203:261 */     join.setSequentialSelect(false);
/* 204:    */     
/* 205:263 */     join.setOptional(true);
/* 206:264 */     key.setCascadeDeleteEnabled(false);
/* 207:265 */     Iterator mappedByColumns = otherSideProperty.getValue().getColumnIterator();
/* 208:266 */     while (mappedByColumns.hasNext())
/* 209:    */     {
/* 210:267 */       Column column = (Column)mappedByColumns.next();
/* 211:268 */       Column copy = new Column();
/* 212:269 */       copy.setLength(column.getLength());
/* 213:270 */       copy.setScale(column.getScale());
/* 214:271 */       copy.setValue(key);
/* 215:272 */       copy.setName(column.getQuotedName());
/* 216:273 */       copy.setNullable(column.isNullable());
/* 217:274 */       copy.setPrecision(column.getPrecision());
/* 218:275 */       copy.setUnique(column.isUnique());
/* 219:276 */       copy.setSqlType(column.getSqlType());
/* 220:277 */       copy.setCheckConstraint(column.getCheckConstraint());
/* 221:278 */       copy.setComment(column.getComment());
/* 222:279 */       copy.setDefaultValue(column.getDefaultValue());
/* 223:280 */       key.addColumn(copy);
/* 224:    */     }
/* 225:282 */     persistentClass.addJoin(join);
/* 226:283 */     return join;
/* 227:    */   }
/* 228:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.OneToOneSecondPass
 * JD-Core Version:    0.7.0.1
 */