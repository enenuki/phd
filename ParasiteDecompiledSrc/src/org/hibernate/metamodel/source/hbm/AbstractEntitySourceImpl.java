/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.AssertionFailure;
/*   7:    */ import org.hibernate.EntityMode;
/*   8:    */ import org.hibernate.internal.jaxb.Origin;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.hbm.EntityElement;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbAnyElement;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbBagElement;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbComponentElement;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbIdbagElement;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbListElement;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbLoaderElement;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbManyToOneElement;
/*  17:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbMapElement;
/*  18:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbOneToOneElement;
/*  19:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbPropertyElement;
/*  20:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbSetElement;
/*  21:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbSynchronizeElement;
/*  22:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbTuplizerElement;
/*  23:    */ import org.hibernate.internal.util.StringHelper;
/*  24:    */ import org.hibernate.metamodel.binding.CustomSQL;
/*  25:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  26:    */ import org.hibernate.metamodel.source.binder.AttributeSource;
/*  27:    */ import org.hibernate.metamodel.source.binder.ConstraintSource;
/*  28:    */ import org.hibernate.metamodel.source.binder.EntitySource;
/*  29:    */ import org.hibernate.metamodel.source.binder.JpaCallbackClass;
/*  30:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  31:    */ import org.hibernate.metamodel.source.binder.SubclassEntitySource;
/*  32:    */ import org.hibernate.metamodel.source.binder.TableSource;
/*  33:    */ 
/*  34:    */ public abstract class AbstractEntitySourceImpl
/*  35:    */   implements EntitySource
/*  36:    */ {
/*  37:    */   private final MappingDocument sourceMappingDocument;
/*  38:    */   private final EntityElement entityElement;
/*  39: 65 */   private List<SubclassEntitySource> subclassEntitySources = new ArrayList();
/*  40:    */   private EntityHierarchyImpl entityHierarchy;
/*  41:    */   
/*  42:    */   protected AbstractEntitySourceImpl(MappingDocument sourceMappingDocument, EntityElement entityElement)
/*  43:    */   {
/*  44: 68 */     this.sourceMappingDocument = sourceMappingDocument;
/*  45: 69 */     this.entityElement = entityElement;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected EntityElement entityElement()
/*  49:    */   {
/*  50: 73 */     return this.entityElement;
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected MappingDocument sourceMappingDocument()
/*  54:    */   {
/*  55: 77 */     return this.sourceMappingDocument;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Origin getOrigin()
/*  59:    */   {
/*  60: 82 */     return this.sourceMappingDocument.getOrigin();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public LocalBindingContext getLocalBindingContext()
/*  64:    */   {
/*  65: 87 */     return this.sourceMappingDocument.getMappingLocalBindingContext();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getEntityName()
/*  69:    */   {
/*  70: 92 */     return StringHelper.isNotEmpty(this.entityElement.getEntityName()) ? this.entityElement.getEntityName() : getClassName();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getClassName()
/*  74:    */   {
/*  75: 99 */     return getLocalBindingContext().qualifyClassName(this.entityElement.getName());
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getJpaEntityName()
/*  79:    */   {
/*  80:104 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isAbstract()
/*  84:    */   {
/*  85:109 */     return Helper.getBooleanValue(this.entityElement.isAbstract(), false);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isLazy()
/*  89:    */   {
/*  90:114 */     return Helper.getBooleanValue(this.entityElement.isAbstract(), true);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getProxy()
/*  94:    */   {
/*  95:119 */     return this.entityElement.getProxy();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getBatchSize()
/*  99:    */   {
/* 100:124 */     return Helper.getIntValue(this.entityElement.getBatchSize(), -1);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isDynamicInsert()
/* 104:    */   {
/* 105:129 */     return this.entityElement.isDynamicInsert();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isDynamicUpdate()
/* 109:    */   {
/* 110:134 */     return this.entityElement.isDynamicUpdate();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isSelectBeforeUpdate()
/* 114:    */   {
/* 115:139 */     return this.entityElement.isSelectBeforeUpdate();
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected EntityMode determineEntityMode()
/* 119:    */   {
/* 120:143 */     return StringHelper.isNotEmpty(getClassName()) ? EntityMode.POJO : EntityMode.MAP;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getCustomTuplizerClassName()
/* 124:    */   {
/* 125:148 */     if (this.entityElement.getTuplizer() == null) {
/* 126:149 */       return null;
/* 127:    */     }
/* 128:151 */     EntityMode entityMode = determineEntityMode();
/* 129:152 */     for (JaxbTuplizerElement tuplizerElement : this.entityElement.getTuplizer()) {
/* 130:153 */       if (entityMode == EntityMode.parse(tuplizerElement.getEntityMode())) {
/* 131:154 */         return tuplizerElement.getClazz();
/* 132:    */       }
/* 133:    */     }
/* 134:157 */     return null;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getCustomPersisterClassName()
/* 138:    */   {
/* 139:162 */     return getLocalBindingContext().qualifyClassName(this.entityElement.getPersister());
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getCustomLoaderName()
/* 143:    */   {
/* 144:167 */     return this.entityElement.getLoader() != null ? this.entityElement.getLoader().getQueryRef() : null;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public CustomSQL getCustomSqlInsert()
/* 148:    */   {
/* 149:172 */     return Helper.buildCustomSql(this.entityElement.getSqlInsert());
/* 150:    */   }
/* 151:    */   
/* 152:    */   public CustomSQL getCustomSqlUpdate()
/* 153:    */   {
/* 154:177 */     return Helper.buildCustomSql(this.entityElement.getSqlUpdate());
/* 155:    */   }
/* 156:    */   
/* 157:    */   public CustomSQL getCustomSqlDelete()
/* 158:    */   {
/* 159:182 */     return Helper.buildCustomSql(this.entityElement.getSqlDelete());
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<String> getSynchronizedTableNames()
/* 163:    */   {
/* 164:187 */     List<String> tableNames = new ArrayList();
/* 165:188 */     for (JaxbSynchronizeElement synchronizeElement : this.entityElement.getSynchronize()) {
/* 166:189 */       tableNames.add(synchronizeElement.getTable());
/* 167:    */     }
/* 168:191 */     return tableNames;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 172:    */   {
/* 173:196 */     return Helper.buildMetaAttributeSources(this.entityElement.getMeta());
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getPath()
/* 177:    */   {
/* 178:201 */     return this.sourceMappingDocument.getMappingLocalBindingContext().determineEntityName(this.entityElement);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Iterable<AttributeSource> attributeSources()
/* 182:    */   {
/* 183:206 */     List<AttributeSource> attributeSources = new ArrayList();
/* 184:207 */     for (Object attributeElement : this.entityElement.getPropertyOrManyToOneOrOneToOne()) {
/* 185:208 */       if (JaxbPropertyElement.class.isInstance(attributeElement)) {
/* 186:209 */         attributeSources.add(new PropertyAttributeSourceImpl((JaxbPropertyElement)JaxbPropertyElement.class.cast(attributeElement), sourceMappingDocument().getMappingLocalBindingContext()));
/* 187:216 */       } else if (JaxbComponentElement.class.isInstance(attributeElement)) {
/* 188:217 */         attributeSources.add(new ComponentAttributeSourceImpl((JaxbComponentElement)attributeElement, this, this.sourceMappingDocument.getMappingLocalBindingContext()));
/* 189:225 */       } else if (JaxbManyToOneElement.class.isInstance(attributeElement)) {
/* 190:226 */         attributeSources.add(new ManyToOneAttributeSourceImpl((JaxbManyToOneElement)JaxbManyToOneElement.class.cast(attributeElement), sourceMappingDocument().getMappingLocalBindingContext()));
/* 191:233 */       } else if (!JaxbOneToOneElement.class.isInstance(attributeElement)) {
/* 192:236 */         if (!JaxbAnyElement.class.isInstance(attributeElement)) {
/* 193:239 */           if (JaxbBagElement.class.isInstance(attributeElement)) {
/* 194:240 */             attributeSources.add(new BagAttributeSourceImpl((JaxbBagElement)JaxbBagElement.class.cast(attributeElement), this));
/* 195:247 */           } else if (!JaxbIdbagElement.class.isInstance(attributeElement)) {
/* 196:250 */             if (JaxbSetElement.class.isInstance(attributeElement)) {
/* 197:251 */               attributeSources.add(new SetAttributeSourceImpl((JaxbSetElement)JaxbSetElement.class.cast(attributeElement), this));
/* 198:258 */             } else if (!JaxbListElement.class.isInstance(attributeElement)) {
/* 199:261 */               if (!JaxbMapElement.class.isInstance(attributeElement)) {
/* 200:265 */                 throw new AssertionFailure("Unexpected attribute element type encountered : " + attributeElement.getClass());
/* 201:    */               }
/* 202:    */             }
/* 203:    */           }
/* 204:    */         }
/* 205:    */       }
/* 206:    */     }
/* 207:268 */     return attributeSources;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void injectHierarchy(EntityHierarchyImpl entityHierarchy)
/* 211:    */   {
/* 212:274 */     this.entityHierarchy = entityHierarchy;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void add(SubclassEntitySource subclassEntitySource)
/* 216:    */   {
/* 217:279 */     add((SubclassEntitySourceImpl)subclassEntitySource);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void add(SubclassEntitySourceImpl subclassEntitySource)
/* 221:    */   {
/* 222:283 */     this.entityHierarchy.processSubclass(subclassEntitySource);
/* 223:284 */     this.subclassEntitySources.add(subclassEntitySource);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Iterable<SubclassEntitySource> subclassEntitySources()
/* 227:    */   {
/* 228:289 */     return this.subclassEntitySources;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String getDiscriminatorMatchValue()
/* 232:    */   {
/* 233:294 */     return null;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Iterable<ConstraintSource> getConstraints()
/* 237:    */   {
/* 238:299 */     return Collections.emptySet();
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Iterable<TableSource> getSecondaryTables()
/* 242:    */   {
/* 243:304 */     return Collections.emptySet();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<JpaCallbackClass> getJpaCallbackClasses()
/* 247:    */   {
/* 248:309 */     return Collections.EMPTY_LIST;
/* 249:    */   }
/* 250:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.AbstractEntitySourceImpl
 * JD-Core Version:    0.7.0.1
 */