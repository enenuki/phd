/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.internal.CoreMessageLogger;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddable;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntity;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbMappedSuperclass;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTable;
/*  11:    */ import org.hibernate.internal.util.StringHelper;
/*  12:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  13:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  14:    */ import org.hibernate.metamodel.source.annotations.xml.filter.IndexedAnnotationFilter;
/*  15:    */ import org.jboss.jandex.AnnotationInstance;
/*  16:    */ import org.jboss.jandex.AnnotationValue;
/*  17:    */ import org.jboss.jandex.DotName;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ class DefaultConfigurationHelper
/*  21:    */ {
/*  22: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultConfigurationHelper.class.getName());
/*  23: 53 */   static final DefaultConfigurationHelper INSTANCE = new DefaultConfigurationHelper();
/*  24: 54 */   static final DotName[] GLOBAL_ANNOTATIONS = { JPADotNames.SEQUENCE_GENERATOR, JPADotNames.TABLE_GENERATOR, JPADotNames.NAMED_QUERIES, JPADotNames.NAMED_QUERY, JPADotNames.NAMED_NATIVE_QUERIES, JPADotNames.NAMED_NATIVE_QUERY, JPADotNames.SQL_RESULT_SET_MAPPING, JPADotNames.SQL_RESULT_SET_MAPPINGS };
/*  25: 64 */   static final DotName[] SCHEMA_AWARE_ANNOTATIONS = { JPADotNames.TABLE, JPADotNames.JOIN_TABLE, JPADotNames.COLLECTION_TABLE, JPADotNames.SECONDARY_TABLE, JPADotNames.SECONDARY_TABLES, JPADotNames.TABLE_GENERATOR, JPADotNames.SEQUENCE_GENERATOR };
/*  26: 73 */   static final DotName[] ASSOCIATION_ANNOTATIONS = { JPADotNames.ONE_TO_MANY, JPADotNames.ONE_TO_ONE, JPADotNames.MANY_TO_ONE, JPADotNames.MANY_TO_MANY };
/*  27:    */   
/*  28:    */   void applyDefaults(SchemaAware schemaAware, EntityMappingsMocker.Default defaults)
/*  29:    */   {
/*  30: 81 */     if (hasSchemaOrCatalogDefined(defaults))
/*  31:    */     {
/*  32: 82 */       if (StringHelper.isEmpty(schemaAware.getSchema())) {
/*  33: 83 */         schemaAware.setSchema(defaults.getSchema());
/*  34:    */       }
/*  35: 85 */       if (StringHelper.isEmpty(schemaAware.getCatalog())) {
/*  36: 86 */         schemaAware.setCatalog(defaults.getCatalog());
/*  37:    */       }
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   void applyDefaults(Map<DotName, List<AnnotationInstance>> annotationsMap, EntityMappingsMocker.Default defaults)
/*  42:    */   {
/*  43: 92 */     if ((annotationsMap.isEmpty()) || (defaults == null)) {
/*  44: 93 */       return;
/*  45:    */     }
/*  46: 95 */     if (hasSchemaOrCatalogDefined(defaults)) {
/*  47: 96 */       applyDefaultSchemaAndCatalog(annotationsMap, defaults);
/*  48:    */     }
/*  49: 98 */     if ((defaults.isCascadePersist() != null) && (defaults.isCascadePersist().booleanValue())) {
/*  50: 99 */       applyDefaultCascadePersist(annotationsMap);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   void applyDefaults(JaxbMappedSuperclass mappedSuperclass, EntityMappingsMocker.Default defaults)
/*  55:    */   {
/*  56:104 */     applyDefaultsToEntityObject(new MappedSuperClassEntityObject(mappedSuperclass, null), defaults);
/*  57:    */   }
/*  58:    */   
/*  59:    */   void applyDefaults(JaxbEmbeddable embeddable, EntityMappingsMocker.Default defaults)
/*  60:    */   {
/*  61:108 */     applyDefaultsToEntityObject(new EmbeddableEntityObject(embeddable, null), defaults);
/*  62:    */   }
/*  63:    */   
/*  64:    */   void applyDefaults(JaxbEntity entity, EntityMappingsMocker.Default defaults)
/*  65:    */   {
/*  66:112 */     mockTableIfNonExist(entity, defaults);
/*  67:113 */     applyDefaultsToEntityObject(new EntityEntityObject(entity, null), defaults);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void applyDefaultsToEntityObject(EntityObject entityObject, EntityMappingsMocker.Default defaults)
/*  71:    */   {
/*  72:117 */     if (defaults == null) {
/*  73:118 */       return;
/*  74:    */     }
/*  75:120 */     String className = MockHelper.buildSafeClassName(entityObject.getClazz(), defaults.getPackageName());
/*  76:121 */     entityObject.setClazz(className);
/*  77:122 */     if (entityObject.isMetadataComplete() == null) {
/*  78:123 */       entityObject.setMetadataComplete(defaults.isMetadataComplete());
/*  79:    */     }
/*  80:125 */     LOG.debugf("Adding XML overriding information for %s", className);
/*  81:    */   }
/*  82:    */   
/*  83:    */   private boolean hasSchemaOrCatalogDefined(EntityMappingsMocker.Default defaults)
/*  84:    */   {
/*  85:129 */     return (defaults != null) && ((StringHelper.isNotEmpty(defaults.getSchema())) || (StringHelper.isNotEmpty(defaults.getCatalog())));
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void applyDefaultCascadePersist(Map<DotName, List<AnnotationInstance>> annotationsMap)
/*  89:    */   {
/*  90:135 */     for (DotName annName : ASSOCIATION_ANNOTATIONS) {
/*  91:136 */       if (annotationsMap.containsKey(annName)) {
/*  92:137 */         addCascadePersistIfNotExist(annName, annotationsMap);
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void applyDefaultSchemaAndCatalog(Map<DotName, List<AnnotationInstance>> annotationsMap, EntityMappingsMocker.Default defaults)
/*  98:    */   {
/*  99:143 */     for (DotName annName : SCHEMA_AWARE_ANNOTATIONS)
/* 100:    */     {
/* 101:144 */       mockTableIfNonExist(annotationsMap, annName);
/* 102:145 */       if (annotationsMap.containsKey(annName)) {
/* 103:146 */         overrideSchemaCatalogByDefault(annName, annotationsMap, defaults);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void mockTableIfNonExist(Map<DotName, List<AnnotationInstance>> annotationsMap, DotName annName)
/* 109:    */   {
/* 110:152 */     if ((annName == JPADotNames.TABLE) && (!annotationsMap.containsKey(JPADotNames.TABLE)) && (annotationsMap.containsKey(JPADotNames.ENTITY)))
/* 111:    */     {
/* 112:155 */       AnnotationInstance entity = JandexHelper.getSingleAnnotation(annotationsMap, JPADotNames.ENTITY);
/* 113:156 */       AnnotationInstance table = MockHelper.create(JPADotNames.TABLE, entity.target(), MockHelper.EMPTY_ANNOTATION_VALUE_ARRAY);
/* 114:    */       
/* 115:    */ 
/* 116:159 */       List<AnnotationInstance> annotationInstanceList = new ArrayList(1);
/* 117:160 */       annotationInstanceList.add(table);
/* 118:161 */       annotationsMap.put(JPADotNames.TABLE, annotationInstanceList);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void mockTableIfNonExist(JaxbEntity entity, EntityMappingsMocker.Default defaults)
/* 123:    */   {
/* 124:166 */     if (hasSchemaOrCatalogDefined(defaults))
/* 125:    */     {
/* 126:167 */       JaxbTable table = entity.getTable();
/* 127:168 */       if (table == null)
/* 128:    */       {
/* 129:169 */         table = new JaxbTable();
/* 130:170 */         entity.setTable(table);
/* 131:    */       }
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void addCascadePersistIfNotExist(DotName annName, Map<DotName, List<AnnotationInstance>> indexedAnnotationMap)
/* 136:    */   {
/* 137:176 */     List<AnnotationInstance> annotationInstanceList = (List)indexedAnnotationMap.get(annName);
/* 138:177 */     if ((annotationInstanceList == null) || (annotationInstanceList.isEmpty())) {
/* 139:178 */       return;
/* 140:    */     }
/* 141:180 */     List<AnnotationInstance> newAnnotationInstanceList = new ArrayList(annotationInstanceList.size());
/* 142:181 */     for (AnnotationInstance annotationInstance : annotationInstanceList)
/* 143:    */     {
/* 144:182 */       AnnotationValue cascadeValue = annotationInstance.value("cascade");
/* 145:183 */       List<AnnotationValue> newAnnotationValueList = new ArrayList();
/* 146:184 */       newAnnotationValueList.addAll(annotationInstance.values());
/* 147:185 */       if (cascadeValue == null)
/* 148:    */       {
/* 149:186 */         AnnotationValue temp = AnnotationValue.createEnumValue("", JPADotNames.CASCADE_TYPE, "PERSIST");
/* 150:187 */         cascadeValue = AnnotationValue.createArrayValue("cascade", new AnnotationValue[] { temp });
/* 151:    */       }
/* 152:    */       else
/* 153:    */       {
/* 154:190 */         newAnnotationValueList.remove(cascadeValue);
/* 155:191 */         String[] cascadeTypes = cascadeValue.asEnumArray();
/* 156:192 */         boolean hasPersistDefined = false;
/* 157:193 */         for (String type : cascadeTypes) {
/* 158:194 */           if ("PERSIST".equals(type)) {
/* 159:195 */             hasPersistDefined = true;
/* 160:    */           }
/* 161:    */         }
/* 162:199 */         if (hasPersistDefined)
/* 163:    */         {
/* 164:200 */           newAnnotationInstanceList.add(annotationInstance);
/* 165:201 */           continue;
/* 166:    */         }
/* 167:203 */         String[] newCascadeTypes = new String[cascadeTypes.length + 1];
/* 168:204 */         newCascadeTypes[0] = "PERSIST";
/* 169:205 */         System.arraycopy(cascadeTypes, 0, newCascadeTypes, 1, cascadeTypes.length);
/* 170:206 */         AnnotationValue[] cascades = new AnnotationValue[newCascadeTypes.length];
/* 171:207 */         for (int i = 0; i < newCascadeTypes.length; i++) {
/* 172:208 */           cascades[i] = AnnotationValue.createEnumValue("", JPADotNames.CASCADE_TYPE, newCascadeTypes[i]);
/* 173:    */         }
/* 174:210 */         cascadeValue = AnnotationValue.createArrayValue("cascade", cascades);
/* 175:    */       }
/* 176:213 */       newAnnotationValueList.add(cascadeValue);
/* 177:    */       
/* 178:215 */       AnnotationInstance newAnnotationInstance = MockHelper.create(annotationInstance.name(), annotationInstance.target(), MockHelper.toArray(newAnnotationValueList));
/* 179:    */       
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:220 */       newAnnotationInstanceList.add(newAnnotationInstance);
/* 184:    */     }
/* 185:222 */     indexedAnnotationMap.put(annName, newAnnotationInstanceList);
/* 186:    */   }
/* 187:    */   
/* 188:    */   private void overrideSchemaCatalogByDefault(DotName annName, Map<DotName, List<AnnotationInstance>> indexedAnnotationMap, EntityMappingsMocker.Default defaults)
/* 189:    */   {
/* 190:227 */     List<AnnotationInstance> annotationInstanceList = (List)indexedAnnotationMap.get(annName);
/* 191:228 */     if ((annotationInstanceList == null) || (annotationInstanceList.isEmpty())) {
/* 192:229 */       return;
/* 193:    */     }
/* 194:231 */     List<AnnotationInstance> newAnnotationInstanceList = new ArrayList(annotationInstanceList.size());
/* 195:232 */     for (AnnotationInstance annotationInstance : annotationInstanceList) {
/* 196:233 */       if (annName.equals(IndexedAnnotationFilter.SECONDARY_TABLES))
/* 197:    */       {
/* 198:234 */         AnnotationInstance[] secondaryTableAnnotationInstanceArray = annotationInstance.value().asNestedArray();
/* 199:235 */         AnnotationValue[] newAnnotationValueArray = new AnnotationValue[secondaryTableAnnotationInstanceArray.length];
/* 200:236 */         for (int i = 0; i < secondaryTableAnnotationInstanceArray.length; i++) {
/* 201:237 */           newAnnotationValueArray[i] = MockHelper.nestedAnnotationValue("", overrideSchemaCatalogByDefault(secondaryTableAnnotationInstanceArray[i], defaults));
/* 202:    */         }
/* 203:244 */         AnnotationInstance secondaryTablesAnnotationInstance = MockHelper.create(annName, annotationInstance.target(), new AnnotationValue[] { AnnotationValue.createArrayValue("value", newAnnotationValueArray) });
/* 204:    */         
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:251 */         newAnnotationInstanceList.add(secondaryTablesAnnotationInstance);
/* 211:    */       }
/* 212:    */       else
/* 213:    */       {
/* 214:254 */         newAnnotationInstanceList.add(overrideSchemaCatalogByDefault(annotationInstance, defaults));
/* 215:    */       }
/* 216:    */     }
/* 217:257 */     indexedAnnotationMap.put(annName, newAnnotationInstanceList);
/* 218:    */   }
/* 219:    */   
/* 220:    */   private AnnotationInstance overrideSchemaCatalogByDefault(AnnotationInstance annotationInstance, EntityMappingsMocker.Default defaults)
/* 221:    */   {
/* 222:261 */     List<AnnotationValue> newAnnotationValueList = new ArrayList();
/* 223:262 */     newAnnotationValueList.addAll(annotationInstance.values());
/* 224:263 */     boolean schemaDefined = false;
/* 225:264 */     boolean catalogDefined = false;
/* 226:265 */     if (annotationInstance.value("schema") != null) {
/* 227:266 */       schemaDefined = true;
/* 228:    */     }
/* 229:268 */     if (annotationInstance.value("catalog") != null) {
/* 230:269 */       catalogDefined = true;
/* 231:    */     }
/* 232:271 */     if ((schemaDefined) && (catalogDefined)) {
/* 233:272 */       return annotationInstance;
/* 234:    */     }
/* 235:274 */     if ((!catalogDefined) && (StringHelper.isNotEmpty(defaults.getCatalog()))) {
/* 236:275 */       newAnnotationValueList.add(AnnotationValue.createStringValue("catalog", defaults.getCatalog()));
/* 237:    */     }
/* 238:281 */     if ((!schemaDefined) && (StringHelper.isNotEmpty(defaults.getSchema()))) {
/* 239:282 */       newAnnotationValueList.add(AnnotationValue.createStringValue("schema", defaults.getSchema()));
/* 240:    */     }
/* 241:288 */     return MockHelper.create(annotationInstance.name(), annotationInstance.target(), MockHelper.toArray(newAnnotationValueList));
/* 242:    */   }
/* 243:    */   
/* 244:    */   private static abstract interface EntityObject
/* 245:    */   {
/* 246:    */     public abstract String getClazz();
/* 247:    */     
/* 248:    */     public abstract void setClazz(String paramString);
/* 249:    */     
/* 250:    */     public abstract Boolean isMetadataComplete();
/* 251:    */     
/* 252:    */     public abstract void setMetadataComplete(Boolean paramBoolean);
/* 253:    */   }
/* 254:    */   
/* 255:    */   private static class EntityEntityObject
/* 256:    */     implements DefaultConfigurationHelper.EntityObject
/* 257:    */   {
/* 258:    */     private JaxbEntity entity;
/* 259:    */     
/* 260:    */     private EntityEntityObject(JaxbEntity entity)
/* 261:    */     {
/* 262:309 */       this.entity = entity;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public String getClazz()
/* 266:    */     {
/* 267:314 */       return this.entity.getClazz();
/* 268:    */     }
/* 269:    */     
/* 270:    */     public void setClazz(String className)
/* 271:    */     {
/* 272:319 */       this.entity.setClazz(className);
/* 273:    */     }
/* 274:    */     
/* 275:    */     public Boolean isMetadataComplete()
/* 276:    */     {
/* 277:324 */       return this.entity.isMetadataComplete();
/* 278:    */     }
/* 279:    */     
/* 280:    */     public void setMetadataComplete(Boolean isMetadataComplete)
/* 281:    */     {
/* 282:329 */       this.entity.setMetadataComplete(isMetadataComplete);
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   private static class EmbeddableEntityObject
/* 287:    */     implements DefaultConfigurationHelper.EntityObject
/* 288:    */   {
/* 289:    */     private JaxbEmbeddable entity;
/* 290:    */     
/* 291:    */     private EmbeddableEntityObject(JaxbEmbeddable entity)
/* 292:    */     {
/* 293:337 */       this.entity = entity;
/* 294:    */     }
/* 295:    */     
/* 296:    */     public String getClazz()
/* 297:    */     {
/* 298:342 */       return this.entity.getClazz();
/* 299:    */     }
/* 300:    */     
/* 301:    */     public void setClazz(String className)
/* 302:    */     {
/* 303:347 */       this.entity.setClazz(className);
/* 304:    */     }
/* 305:    */     
/* 306:    */     public Boolean isMetadataComplete()
/* 307:    */     {
/* 308:352 */       return this.entity.isMetadataComplete();
/* 309:    */     }
/* 310:    */     
/* 311:    */     public void setMetadataComplete(Boolean isMetadataComplete)
/* 312:    */     {
/* 313:357 */       this.entity.setMetadataComplete(isMetadataComplete);
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   private static class MappedSuperClassEntityObject
/* 318:    */     implements DefaultConfigurationHelper.EntityObject
/* 319:    */   {
/* 320:    */     private JaxbMappedSuperclass entity;
/* 321:    */     
/* 322:    */     private MappedSuperClassEntityObject(JaxbMappedSuperclass entity)
/* 323:    */     {
/* 324:365 */       this.entity = entity;
/* 325:    */     }
/* 326:    */     
/* 327:    */     public String getClazz()
/* 328:    */     {
/* 329:370 */       return this.entity.getClazz();
/* 330:    */     }
/* 331:    */     
/* 332:    */     public void setClazz(String className)
/* 333:    */     {
/* 334:375 */       this.entity.setClazz(className);
/* 335:    */     }
/* 336:    */     
/* 337:    */     public Boolean isMetadataComplete()
/* 338:    */     {
/* 339:380 */       return this.entity.isMetadataComplete();
/* 340:    */     }
/* 341:    */     
/* 342:    */     public void setMetadataComplete(Boolean isMetadataComplete)
/* 343:    */     {
/* 344:385 */       this.entity.setMetadataComplete(isMetadataComplete);
/* 345:    */     }
/* 346:    */   }
/* 347:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.DefaultConfigurationHelper
 * JD-Core Version:    0.7.0.1
 */