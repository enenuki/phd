/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import javax.persistence.AccessType;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAttributes;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbDiscriminatorColumn;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntity;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityListeners;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbIdClass;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbInheritance;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostLoad;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostPersist;
/*  17:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostRemove;
/*  18:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostUpdate;
/*  19:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPrePersist;
/*  20:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreRemove;
/*  21:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreUpdate;
/*  22:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbSecondaryTable;
/*  23:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTable;
/*  24:    */ import org.hibernate.internal.util.StringHelper;
/*  25:    */ import org.jboss.jandex.AnnotationInstance;
/*  26:    */ import org.jboss.jandex.AnnotationTarget;
/*  27:    */ import org.jboss.jandex.AnnotationValue;
/*  28:    */ import org.jboss.jandex.ClassInfo;
/*  29:    */ import org.jboss.jandex.DotName;
/*  30:    */ import org.jboss.logging.Logger;
/*  31:    */ 
/*  32:    */ class EntityMocker
/*  33:    */   extends AbstractEntityObjectMocker
/*  34:    */ {
/*  35: 63 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EntityMocker.class.getName());
/*  36:    */   private JaxbEntity entity;
/*  37:    */   
/*  38:    */   EntityMocker(IndexBuilder indexBuilder, JaxbEntity entity, EntityMappingsMocker.Default defaults)
/*  39:    */   {
/*  40: 70 */     super(indexBuilder, defaults);
/*  41: 71 */     this.entity = entity;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected String getClassName()
/*  45:    */   {
/*  46: 76 */     return this.entity.getClazz();
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void processExtra()
/*  50:    */   {
/*  51: 82 */     create(ENTITY, MockHelper.stringValueArray("name", this.entity.getName()));
/*  52: 85 */     if (this.entity.isCacheable() != null) {
/*  53: 87 */       create(CACHEABLE, MockHelper.booleanValueArray("value", this.entity.isCacheable()));
/*  54:    */     }
/*  55: 93 */     if (StringHelper.isNotEmpty(this.entity.getDiscriminatorValue())) {
/*  56: 95 */       create(DISCRIMINATOR_VALUE, MockHelper.stringValueArray("value", this.entity.getDiscriminatorValue()));
/*  57:    */     }
/*  58:102 */     parserTable(this.entity.getTable());
/*  59:103 */     parserInheritance(this.entity.getInheritance());
/*  60:104 */     parserDiscriminatorColumn(this.entity.getDiscriminatorColumn());
/*  61:105 */     parserAttributeOverrides(this.entity.getAttributeOverride(), getTarget());
/*  62:106 */     parserAssociationOverrides(this.entity.getAssociationOverride(), getTarget());
/*  63:107 */     parserPrimaryKeyJoinColumnList(this.entity.getPrimaryKeyJoinColumn(), getTarget());
/*  64:108 */     parserSecondaryTableList(this.entity.getSecondaryTable(), getTarget());
/*  65:    */   }
/*  66:    */   
/*  67:    */   private AnnotationInstance parserTable(JaxbTable table)
/*  68:    */   {
/*  69:114 */     if (table == null) {
/*  70:115 */       return null;
/*  71:    */     }
/*  72:117 */     DefaultConfigurationHelper.INSTANCE.applyDefaults(new SchemaAware.TableSchemaAware(table), getDefaults());
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:121 */     List<AnnotationValue> annotationValueList = new ArrayList();
/*  77:122 */     MockHelper.stringValue("name", table.getName(), annotationValueList);
/*  78:123 */     MockHelper.stringValue("catalog", table.getCatalog(), annotationValueList);
/*  79:124 */     MockHelper.stringValue("schema", table.getSchema(), annotationValueList);
/*  80:125 */     nestedUniqueConstraintList("uniqueConstraints", table.getUniqueConstraint(), annotationValueList);
/*  81:126 */     return create(TABLE, annotationValueList);
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected AccessType getDefaultAccess()
/*  85:    */   {
/*  86:130 */     if (this.entity.getAccess() != null) {
/*  87:131 */       return AccessType.valueOf(this.entity.getAccess().value());
/*  88:    */     }
/*  89:134 */     return null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected AccessType getAccessFromIndex(DotName className)
/*  93:    */   {
/*  94:138 */     Map<DotName, List<AnnotationInstance>> indexedAnnotations = this.indexBuilder.getIndexedAnnotations(className);
/*  95:139 */     List<AnnotationInstance> accessAnnotationInstances = (List)indexedAnnotations.get(ACCESS);
/*  96:140 */     if (MockHelper.isNotEmpty(accessAnnotationInstances)) {
/*  97:141 */       for (AnnotationInstance annotationInstance : accessAnnotationInstances) {
/*  98:142 */         if ((annotationInstance.target() != null) && ((annotationInstance.target() instanceof ClassInfo)))
/*  99:    */         {
/* 100:143 */           ClassInfo ci = (ClassInfo)annotationInstance.target();
/* 101:144 */           if (className.equals(ci.name())) {
/* 102:146 */             return AccessType.valueOf(annotationInstance.value().asEnum());
/* 103:    */           }
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:151 */     return null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void applyDefaults()
/* 111:    */   {
/* 112:156 */     DefaultConfigurationHelper.INSTANCE.applyDefaults(this.entity, getDefaults());
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected JaxbPrePersist getPrePersist()
/* 116:    */   {
/* 117:161 */     return this.entity.getPrePersist();
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected JaxbPreRemove getPreRemove()
/* 121:    */   {
/* 122:166 */     return this.entity.getPreRemove();
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected JaxbPreUpdate getPreUpdate()
/* 126:    */   {
/* 127:171 */     return this.entity.getPreUpdate();
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected JaxbPostPersist getPostPersist()
/* 131:    */   {
/* 132:176 */     return this.entity.getPostPersist();
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected JaxbPostUpdate getPostUpdate()
/* 136:    */   {
/* 137:181 */     return this.entity.getPostUpdate();
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected JaxbPostRemove getPostRemove()
/* 141:    */   {
/* 142:186 */     return this.entity.getPostRemove();
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected JaxbPostLoad getPostLoad()
/* 146:    */   {
/* 147:191 */     return this.entity.getPostLoad();
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected JaxbAttributes getAttributes()
/* 151:    */   {
/* 152:196 */     return this.entity.getAttributes();
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected boolean isMetadataComplete()
/* 156:    */   {
/* 157:201 */     return (this.entity.isMetadataComplete() != null) && (this.entity.isMetadataComplete().booleanValue());
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected boolean isExcludeDefaultListeners()
/* 161:    */   {
/* 162:206 */     return this.entity.getExcludeDefaultListeners() != null;
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected boolean isExcludeSuperclassListeners()
/* 166:    */   {
/* 167:211 */     return this.entity.getExcludeSuperclassListeners() != null;
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected JaxbIdClass getIdClass()
/* 171:    */   {
/* 172:216 */     return this.entity.getIdClass();
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected JaxbEntityListeners getEntityListeners()
/* 176:    */   {
/* 177:221 */     return this.entity.getEntityListeners();
/* 178:    */   }
/* 179:    */   
/* 180:    */   protected JaxbAccessType getAccessType()
/* 181:    */   {
/* 182:226 */     return this.entity.getAccess();
/* 183:    */   }
/* 184:    */   
/* 185:    */   protected AnnotationInstance parserInheritance(JaxbInheritance inheritance)
/* 186:    */   {
/* 187:231 */     if (inheritance == null) {
/* 188:232 */       return null;
/* 189:    */     }
/* 190:234 */     return create(INHERITANCE, MockHelper.enumValueArray("strategy", INHERITANCE_TYPE, inheritance.getStrategy()));
/* 191:    */   }
/* 192:    */   
/* 193:    */   protected AnnotationInstance parserDiscriminatorColumn(JaxbDiscriminatorColumn discriminatorColumn)
/* 194:    */   {
/* 195:244 */     if (discriminatorColumn == null) {
/* 196:245 */       return null;
/* 197:    */     }
/* 198:247 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 199:248 */     MockHelper.stringValue("name", discriminatorColumn.getName(), annotationValueList);
/* 200:249 */     MockHelper.stringValue("columnDefinition", discriminatorColumn.getColumnDefinition(), annotationValueList);
/* 201:    */     
/* 202:    */ 
/* 203:252 */     MockHelper.integerValue("length", discriminatorColumn.getLength(), annotationValueList);
/* 204:253 */     MockHelper.enumValue("discriminatorType", DISCRIMINATOR_TYPE, discriminatorColumn.getDiscriminatorType(), annotationValueList);
/* 205:    */     
/* 206:    */ 
/* 207:256 */     return create(DISCRIMINATOR_COLUMN, annotationValueList);
/* 208:    */   }
/* 209:    */   
/* 210:    */   protected AnnotationInstance parserSecondaryTable(JaxbSecondaryTable secondaryTable, AnnotationTarget target)
/* 211:    */   {
/* 212:266 */     if (secondaryTable == null) {
/* 213:267 */       return null;
/* 214:    */     }
/* 215:269 */     DefaultConfigurationHelper.INSTANCE.applyDefaults(new SchemaAware.SecondaryTableSchemaAware(secondaryTable), getDefaults());
/* 216:    */     
/* 217:    */ 
/* 218:    */ 
/* 219:273 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 220:274 */     MockHelper.stringValue("name", secondaryTable.getName(), annotationValueList);
/* 221:275 */     MockHelper.stringValue("catalog", secondaryTable.getCatalog(), annotationValueList);
/* 222:276 */     MockHelper.stringValue("schema", secondaryTable.getSchema(), annotationValueList);
/* 223:277 */     nestedPrimaryKeyJoinColumnList("pkJoinColumns", secondaryTable.getPrimaryKeyJoinColumn(), annotationValueList);
/* 224:    */     
/* 225:    */ 
/* 226:280 */     nestedUniqueConstraintList("uniqueConstraints", secondaryTable.getUniqueConstraint(), annotationValueList);
/* 227:    */     
/* 228:    */ 
/* 229:283 */     return create(SECONDARY_TABLE, target, annotationValueList);
/* 230:    */   }
/* 231:    */   
/* 232:    */   protected AnnotationInstance parserSecondaryTableList(List<JaxbSecondaryTable> primaryKeyJoinColumnList, AnnotationTarget target)
/* 233:    */   {
/* 234:291 */     if (MockHelper.isNotEmpty(primaryKeyJoinColumnList))
/* 235:    */     {
/* 236:292 */       if (primaryKeyJoinColumnList.size() == 1) {
/* 237:293 */         return parserSecondaryTable((JaxbSecondaryTable)primaryKeyJoinColumnList.get(0), target);
/* 238:    */       }
/* 239:296 */       return create(SECONDARY_TABLES, target, nestedSecondaryTableList("value", primaryKeyJoinColumnList, null));
/* 240:    */     }
/* 241:303 */     return null;
/* 242:    */   }
/* 243:    */   
/* 244:    */   protected AnnotationValue[] nestedSecondaryTableList(String name, List<JaxbSecondaryTable> secondaryTableList, List<AnnotationValue> annotationValueList)
/* 245:    */   {
/* 246:308 */     if (MockHelper.isNotEmpty(secondaryTableList))
/* 247:    */     {
/* 248:309 */       AnnotationValue[] values = new AnnotationValue[secondaryTableList.size()];
/* 249:310 */       for (int i = 0; i < secondaryTableList.size(); i++)
/* 250:    */       {
/* 251:311 */         AnnotationInstance annotationInstance = parserSecondaryTable((JaxbSecondaryTable)secondaryTableList.get(i), null);
/* 252:312 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 253:    */       }
/* 254:316 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/* 255:    */       
/* 256:    */ 
/* 257:319 */       return values;
/* 258:    */     }
/* 259:321 */     return MockHelper.EMPTY_ANNOTATION_VALUE_ARRAY;
/* 260:    */   }
/* 261:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.EntityMocker
 * JD-Core Version:    0.7.0.1
 */