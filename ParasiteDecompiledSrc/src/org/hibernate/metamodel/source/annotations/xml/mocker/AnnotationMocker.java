/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAssociationOverride;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAttributeOverride;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbCollectionTable;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbColumn;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEnumType;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbJoinColumn;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbJoinTable;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbLob;
/*  17:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOrderColumn;
/*  18:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPrimaryKeyJoinColumn;
/*  19:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTemporalType;
/*  20:    */ import org.jboss.jandex.AnnotationInstance;
/*  21:    */ import org.jboss.jandex.AnnotationTarget;
/*  22:    */ import org.jboss.jandex.AnnotationValue;
/*  23:    */ import org.jboss.jandex.DotName;
/*  24:    */ 
/*  25:    */ abstract class AnnotationMocker
/*  26:    */   extends AbstractMocker
/*  27:    */ {
/*  28:    */   private EntityMappingsMocker.Default defaults;
/*  29:    */   
/*  30:    */   AnnotationMocker(IndexBuilder indexBuilder, EntityMappingsMocker.Default defaults)
/*  31:    */   {
/*  32: 57 */     super(indexBuilder);
/*  33: 58 */     this.defaults = defaults;
/*  34:    */   }
/*  35:    */   
/*  36:    */   abstract void process();
/*  37:    */   
/*  38:    */   protected EntityMappingsMocker.Default getDefaults()
/*  39:    */   {
/*  40: 64 */     return this.defaults;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected boolean isDefaultCascadePersist()
/*  44:    */   {
/*  45: 68 */     return (this.defaults.isCascadePersist() != null) && (this.defaults.isCascadePersist().booleanValue());
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected AnnotationInstance parserJoinTable(JaxbJoinTable joinTable, AnnotationTarget target)
/*  49:    */   {
/*  50: 73 */     if (joinTable == null) {
/*  51: 74 */       return null;
/*  52:    */     }
/*  53: 76 */     DefaultConfigurationHelper.INSTANCE.applyDefaults(new SchemaAware.JoinTableSchemaAware(joinTable), getDefaults());
/*  54:    */     
/*  55:    */ 
/*  56:    */ 
/*  57: 80 */     List<AnnotationValue> annotationValueList = new ArrayList();
/*  58: 81 */     MockHelper.stringValue("name", joinTable.getName(), annotationValueList);
/*  59: 82 */     MockHelper.stringValue("catalog", joinTable.getCatalog(), annotationValueList);
/*  60: 83 */     MockHelper.stringValue("schema", joinTable.getSchema(), annotationValueList);
/*  61: 84 */     nestedJoinColumnList("joinColumns", joinTable.getJoinColumn(), annotationValueList);
/*  62: 85 */     nestedJoinColumnList("inverseJoinColumns", joinTable.getInverseJoinColumn(), annotationValueList);
/*  63:    */     
/*  64:    */ 
/*  65: 88 */     nestedUniqueConstraintList("uniqueConstraints", joinTable.getUniqueConstraint(), annotationValueList);
/*  66:    */     
/*  67:    */ 
/*  68: 91 */     return create(JOIN_TABLE, target, annotationValueList);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private AnnotationInstance parserAssociationOverride(JaxbAssociationOverride associationOverride, AnnotationTarget target)
/*  72:    */   {
/*  73: 96 */     if (associationOverride == null) {
/*  74: 97 */       return null;
/*  75:    */     }
/*  76: 99 */     List<AnnotationValue> annotationValueList = new ArrayList();
/*  77:100 */     MockHelper.stringValue("name", associationOverride.getName(), annotationValueList);
/*  78:101 */     if ((associationOverride instanceof JaxbAssociationOverrideProxy))
/*  79:    */     {
/*  80:102 */       JaxbAssociationOverrideProxy proxy = (JaxbAssociationOverrideProxy)associationOverride;
/*  81:103 */       MockHelper.addToCollectionIfNotNull(annotationValueList, proxy.getJoinColumnsAnnotationValue());
/*  82:104 */       MockHelper.addToCollectionIfNotNull(annotationValueList, proxy.getJoinTableAnnotationValue());
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:107 */       nestedJoinColumnList("joinColumns", associationOverride.getJoinColumn(), annotationValueList);
/*  87:    */       
/*  88:    */ 
/*  89:110 */       MockHelper.nestedAnnotationValue("joinTable", parserJoinTable(associationOverride.getJoinTable(), null), annotationValueList);
/*  90:    */     }
/*  91:114 */     return create(ASSOCIATION_OVERRIDE, target, annotationValueList);
/*  92:    */   }
/*  93:    */   
/*  94:    */   private AnnotationValue[] nestedJoinColumnList(String name, List<JaxbJoinColumn> columns, List<AnnotationValue> annotationValueList)
/*  95:    */   {
/*  96:118 */     if (MockHelper.isNotEmpty(columns))
/*  97:    */     {
/*  98:119 */       AnnotationValue[] values = new AnnotationValue[columns.size()];
/*  99:120 */       for (int i = 0; i < columns.size(); i++)
/* 100:    */       {
/* 101:121 */         AnnotationInstance annotationInstance = parserJoinColumn((JaxbJoinColumn)columns.get(i), null);
/* 102:122 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 103:    */       }
/* 104:126 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/* 105:    */       
/* 106:    */ 
/* 107:129 */       return values;
/* 108:    */     }
/* 109:131 */     return MockHelper.EMPTY_ANNOTATION_VALUE_ARRAY;
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected AnnotationInstance parserColumn(JaxbColumn column, AnnotationTarget target)
/* 113:    */   {
/* 114:137 */     if (column == null) {
/* 115:138 */       return null;
/* 116:    */     }
/* 117:140 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 118:141 */     MockHelper.stringValue("name", column.getName(), annotationValueList);
/* 119:142 */     MockHelper.stringValue("columnDefinition", column.getColumnDefinition(), annotationValueList);
/* 120:143 */     MockHelper.stringValue("table", column.getTable(), annotationValueList);
/* 121:144 */     MockHelper.booleanValue("unique", column.isUnique(), annotationValueList);
/* 122:145 */     MockHelper.booleanValue("nullable", column.isNullable(), annotationValueList);
/* 123:146 */     MockHelper.booleanValue("insertable", column.isInsertable(), annotationValueList);
/* 124:147 */     MockHelper.booleanValue("updatable", column.isUpdatable(), annotationValueList);
/* 125:148 */     MockHelper.integerValue("length", column.getLength(), annotationValueList);
/* 126:149 */     MockHelper.integerValue("precision", column.getPrecision(), annotationValueList);
/* 127:150 */     MockHelper.integerValue("scale", column.getScale(), annotationValueList);
/* 128:151 */     return create(COLUMN, target, annotationValueList);
/* 129:    */   }
/* 130:    */   
/* 131:    */   private AnnotationInstance parserAttributeOverride(JaxbAttributeOverride attributeOverride, AnnotationTarget target)
/* 132:    */   {
/* 133:156 */     if (attributeOverride == null) {
/* 134:157 */       return null;
/* 135:    */     }
/* 136:159 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 137:160 */     MockHelper.stringValue("name", attributeOverride.getName(), annotationValueList);
/* 138:161 */     if ((attributeOverride instanceof JaxbAttributeOverrideProxy))
/* 139:    */     {
/* 140:162 */       JaxbAttributeOverrideProxy proxy = (JaxbAttributeOverrideProxy)attributeOverride;
/* 141:163 */       MockHelper.addToCollectionIfNotNull(annotationValueList, proxy.getColumnAnnotationValue());
/* 142:    */     }
/* 143:    */     else
/* 144:    */     {
/* 145:166 */       MockHelper.nestedAnnotationValue("column", parserColumn(attributeOverride.getColumn(), null), annotationValueList);
/* 146:    */     }
/* 147:170 */     return create(ATTRIBUTE_OVERRIDE, target, annotationValueList);
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected AnnotationInstance parserOrderColumn(JaxbOrderColumn orderColumn, AnnotationTarget target)
/* 151:    */   {
/* 152:179 */     if (orderColumn == null) {
/* 153:180 */       return null;
/* 154:    */     }
/* 155:182 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 156:183 */     MockHelper.stringValue("name", orderColumn.getName(), annotationValueList);
/* 157:184 */     MockHelper.stringValue("columnDefinition", orderColumn.getColumnDefinition(), annotationValueList);
/* 158:185 */     MockHelper.booleanValue("nullable", orderColumn.isNullable(), annotationValueList);
/* 159:186 */     MockHelper.booleanValue("insertable", orderColumn.isInsertable(), annotationValueList);
/* 160:187 */     MockHelper.booleanValue("updatable", orderColumn.isUpdatable(), annotationValueList);
/* 161:188 */     return create(ORDER_COLUMN, target, annotationValueList);
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected AnnotationInstance parserJoinColumn(JaxbJoinColumn column, AnnotationTarget target)
/* 165:    */   {
/* 166:193 */     if (column == null) {
/* 167:194 */       return null;
/* 168:    */     }
/* 169:196 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 170:197 */     MockHelper.stringValue("name", column.getName(), annotationValueList);
/* 171:198 */     MockHelper.stringValue("columnDefinition", column.getColumnDefinition(), annotationValueList);
/* 172:199 */     MockHelper.stringValue("table", column.getTable(), annotationValueList);
/* 173:200 */     MockHelper.stringValue("referencedColumnName", column.getReferencedColumnName(), annotationValueList);
/* 174:    */     
/* 175:    */ 
/* 176:203 */     MockHelper.booleanValue("unique", column.isUnique(), annotationValueList);
/* 177:204 */     MockHelper.booleanValue("nullable", column.isNullable(), annotationValueList);
/* 178:205 */     MockHelper.booleanValue("insertable", column.isInsertable(), annotationValueList);
/* 179:206 */     MockHelper.booleanValue("updatable", column.isUpdatable(), annotationValueList);
/* 180:207 */     return create(JOIN_COLUMN, target, annotationValueList);
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected AnnotationInstance parserLob(JaxbLob lob, AnnotationTarget target)
/* 184:    */   {
/* 185:211 */     if (lob == null) {
/* 186:212 */       return null;
/* 187:    */     }
/* 188:214 */     return create(LOB, target);
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected AnnotationInstance parserTemporalType(JaxbTemporalType temporalType, AnnotationTarget target)
/* 192:    */   {
/* 193:218 */     if (temporalType == null) {
/* 194:219 */       return null;
/* 195:    */     }
/* 196:221 */     return create(TEMPORAL, target, MockHelper.enumValueArray("value", TEMPORAL_TYPE, temporalType));
/* 197:    */   }
/* 198:    */   
/* 199:    */   protected AnnotationInstance parserEnumType(JaxbEnumType enumerated, AnnotationTarget target)
/* 200:    */   {
/* 201:225 */     if (enumerated == null) {
/* 202:226 */       return null;
/* 203:    */     }
/* 204:228 */     return create(ENUMERATED, target, MockHelper.enumValueArray("value", ENUM_TYPE, enumerated));
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected AnnotationInstance parserPrimaryKeyJoinColumn(JaxbPrimaryKeyJoinColumn primaryKeyJoinColumn, AnnotationTarget target)
/* 208:    */   {
/* 209:233 */     if (primaryKeyJoinColumn == null) {
/* 210:234 */       return null;
/* 211:    */     }
/* 212:236 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 213:237 */     MockHelper.stringValue("name", primaryKeyJoinColumn.getName(), annotationValueList);
/* 214:238 */     MockHelper.stringValue("referencedColumnName", primaryKeyJoinColumn.getReferencedColumnName(), annotationValueList);
/* 215:    */     
/* 216:    */ 
/* 217:241 */     MockHelper.stringValue("columnDefinition", primaryKeyJoinColumn.getColumnDefinition(), annotationValueList);
/* 218:    */     
/* 219:    */ 
/* 220:244 */     return create(PRIMARY_KEY_JOIN_COLUMN, target, annotationValueList);
/* 221:    */   }
/* 222:    */   
/* 223:    */   protected AnnotationInstance parserPrimaryKeyJoinColumnList(List<JaxbPrimaryKeyJoinColumn> primaryKeyJoinColumnList, AnnotationTarget target)
/* 224:    */   {
/* 225:252 */     if (MockHelper.isNotEmpty(primaryKeyJoinColumnList))
/* 226:    */     {
/* 227:253 */       if (primaryKeyJoinColumnList.size() == 1) {
/* 228:254 */         return parserPrimaryKeyJoinColumn((JaxbPrimaryKeyJoinColumn)primaryKeyJoinColumnList.get(0), target);
/* 229:    */       }
/* 230:257 */       return create(PRIMARY_KEY_JOIN_COLUMNS, target, nestedPrimaryKeyJoinColumnList("value", primaryKeyJoinColumnList, null));
/* 231:    */     }
/* 232:265 */     return null;
/* 233:    */   }
/* 234:    */   
/* 235:    */   protected AnnotationValue[] nestedPrimaryKeyJoinColumnList(String name, List<JaxbPrimaryKeyJoinColumn> constraints, List<AnnotationValue> annotationValueList)
/* 236:    */   {
/* 237:270 */     if (MockHelper.isNotEmpty(constraints))
/* 238:    */     {
/* 239:271 */       AnnotationValue[] values = new AnnotationValue[constraints.size()];
/* 240:272 */       for (int i = 0; i < constraints.size(); i++)
/* 241:    */       {
/* 242:273 */         AnnotationInstance annotationInstance = parserPrimaryKeyJoinColumn((JaxbPrimaryKeyJoinColumn)constraints.get(i), null);
/* 243:274 */         values[i] = MockHelper.nestedAnnotationValue("", annotationInstance);
/* 244:    */       }
/* 245:278 */       MockHelper.addToCollectionIfNotNull(annotationValueList, AnnotationValue.createArrayValue(name, values));
/* 246:    */       
/* 247:    */ 
/* 248:281 */       return values;
/* 249:    */     }
/* 250:283 */     return MockHelper.EMPTY_ANNOTATION_VALUE_ARRAY;
/* 251:    */   }
/* 252:    */   
/* 253:    */   protected void getAnnotationInstanceByTarget(DotName annName, AnnotationTarget target, Operation operation)
/* 254:    */   {
/* 255:288 */     Map<DotName, List<AnnotationInstance>> annotatedMap = this.indexBuilder.getIndexedAnnotations(getTargetName());
/* 256:289 */     if (!annotatedMap.containsKey(annName)) {
/* 257:290 */       return;
/* 258:    */     }
/* 259:292 */     List<AnnotationInstance> annotationInstanceList = (List)annotatedMap.get(annName);
/* 260:293 */     if (MockHelper.isNotEmpty(annotationInstanceList)) {
/* 261:294 */       for (AnnotationInstance annotationInstance : annotationInstanceList)
/* 262:    */       {
/* 263:295 */         AnnotationTarget annotationTarget = annotationInstance.target();
/* 264:296 */         if ((MockHelper.targetEquals(target, annotationTarget)) && 
/* 265:297 */           (operation.process(annotationInstance))) {
/* 266:298 */           return;
/* 267:    */         }
/* 268:    */       }
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   protected AnnotationInstance parserAttributeOverrides(List<JaxbAttributeOverride> attributeOverrides, AnnotationTarget target)
/* 273:    */   {
/* 274:307 */     if (target == null) {
/* 275:308 */       throw new AssertionFailure("target can not be null");
/* 276:    */     }
/* 277:310 */     if ((attributeOverrides == null) || (attributeOverrides.isEmpty())) {
/* 278:311 */       return null;
/* 279:    */     }
/* 280:313 */     Set<String> names = new HashSet();
/* 281:314 */     for (JaxbAttributeOverride attributeOverride : attributeOverrides) {
/* 282:315 */       names.add(attributeOverride.getName());
/* 283:    */     }
/* 284:317 */     Operation operation = new AttributeOverrideOperation(names, attributeOverrides);
/* 285:318 */     getAnnotationInstanceByTarget(ATTRIBUTE_OVERRIDES, target, new ContainerOperation(operation));
/* 286:    */     
/* 287:    */ 
/* 288:321 */     getAnnotationInstanceByTarget(ATTRIBUTE_OVERRIDE, target, operation);
/* 289:324 */     if (attributeOverrides.size() == 1) {
/* 290:325 */       return parserAttributeOverride((JaxbAttributeOverride)attributeOverrides.get(0), target);
/* 291:    */     }
/* 292:328 */     AnnotationValue[] values = new AnnotationValue[attributeOverrides.size()];
/* 293:329 */     for (int i = 0; i < values.length; i++) {
/* 294:330 */       values[i] = MockHelper.nestedAnnotationValue("", parserAttributeOverride((JaxbAttributeOverride)attributeOverrides.get(i), null));
/* 295:    */     }
/* 296:334 */     return create(ATTRIBUTE_OVERRIDES, target, new AnnotationValue[] { AnnotationValue.createArrayValue("value", values) });
/* 297:    */   }
/* 298:    */   
/* 299:    */   protected AnnotationInstance parserAssociationOverrides(List<JaxbAssociationOverride> associationOverrides, AnnotationTarget target)
/* 300:    */   {
/* 301:343 */     if (target == null) {
/* 302:344 */       throw new AssertionFailure("target can not be null");
/* 303:    */     }
/* 304:346 */     if ((associationOverrides == null) || (associationOverrides.isEmpty())) {
/* 305:347 */       return null;
/* 306:    */     }
/* 307:350 */     Set<String> names = new HashSet();
/* 308:351 */     for (JaxbAssociationOverride associationOverride : associationOverrides) {
/* 309:352 */       names.add(associationOverride.getName());
/* 310:    */     }
/* 311:354 */     Operation operation = new AssociationOverrideOperation(names, associationOverrides);
/* 312:355 */     getAnnotationInstanceByTarget(ASSOCIATION_OVERRIDES, target, new ContainerOperation(operation));
/* 313:    */     
/* 314:    */ 
/* 315:358 */     getAnnotationInstanceByTarget(ASSOCIATION_OVERRIDE, target, operation);
/* 316:363 */     if (associationOverrides.size() == 1) {
/* 317:364 */       return parserAssociationOverride((JaxbAssociationOverride)associationOverrides.get(0), target);
/* 318:    */     }
/* 319:367 */     AnnotationValue[] values = new AnnotationValue[associationOverrides.size()];
/* 320:368 */     for (int i = 0; i < values.length; i++) {
/* 321:369 */       values[i] = MockHelper.nestedAnnotationValue("", parserAssociationOverride((JaxbAssociationOverride)associationOverrides.get(i), null));
/* 322:    */     }
/* 323:373 */     return create(ASSOCIATION_OVERRIDES, target, new AnnotationValue[] { AnnotationValue.createArrayValue("value", values) });
/* 324:    */   }
/* 325:    */   
/* 326:    */   protected AnnotationInstance parserCollectionTable(JaxbCollectionTable collectionTable, AnnotationTarget target)
/* 327:    */   {
/* 328:383 */     if (collectionTable == null) {
/* 329:384 */       return null;
/* 330:    */     }
/* 331:386 */     DefaultConfigurationHelper.INSTANCE.applyDefaults(new SchemaAware.CollectionTableSchemaAware(collectionTable), getDefaults());
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:390 */     List<AnnotationValue> annotationValueList = new ArrayList();
/* 336:391 */     MockHelper.stringValue("name", collectionTable.getName(), annotationValueList);
/* 337:392 */     MockHelper.stringValue("catalog", collectionTable.getCatalog(), annotationValueList);
/* 338:393 */     MockHelper.stringValue("schema", collectionTable.getSchema(), annotationValueList);
/* 339:394 */     nestedJoinColumnList("joinColumns", collectionTable.getJoinColumn(), annotationValueList);
/* 340:395 */     nestedUniqueConstraintList("uniqueConstraints", collectionTable.getUniqueConstraint(), annotationValueList);
/* 341:396 */     return create(COLLECTION_TABLE, target, annotationValueList);
/* 342:    */   }
/* 343:    */   
/* 344:    */   protected AnnotationInstance parserJoinColumnList(List<JaxbJoinColumn> joinColumnList, AnnotationTarget target)
/* 345:    */   {
/* 346:401 */     if (MockHelper.isNotEmpty(joinColumnList))
/* 347:    */     {
/* 348:402 */       if (joinColumnList.size() == 1) {
/* 349:403 */         return parserJoinColumn((JaxbJoinColumn)joinColumnList.get(0), target);
/* 350:    */       }
/* 351:406 */       AnnotationValue[] values = nestedJoinColumnList("value", joinColumnList, null);
/* 352:407 */       return create(JOIN_COLUMNS, target, values);
/* 353:    */     }
/* 354:414 */     return null;
/* 355:    */   }
/* 356:    */   
/* 357:    */   protected static abstract interface Operation
/* 358:    */   {
/* 359:    */     public abstract boolean process(AnnotationInstance paramAnnotationInstance);
/* 360:    */   }
/* 361:    */   
/* 362:    */   class ContainerOperation
/* 363:    */     implements AnnotationMocker.Operation
/* 364:    */   {
/* 365:    */     private AnnotationMocker.Operation child;
/* 366:    */     
/* 367:    */     ContainerOperation(AnnotationMocker.Operation child)
/* 368:    */     {
/* 369:426 */       this.child = child;
/* 370:    */     }
/* 371:    */     
/* 372:    */     public boolean process(AnnotationInstance annotationInstance)
/* 373:    */     {
/* 374:431 */       AnnotationValue value = annotationInstance.value();
/* 375:432 */       AnnotationInstance[] indexedAttributeOverridesValues = value.asNestedArray();
/* 376:433 */       for (AnnotationInstance ai : indexedAttributeOverridesValues) {
/* 377:434 */         this.child.process(ai);
/* 378:    */       }
/* 379:436 */       return true;
/* 380:    */     }
/* 381:    */   }
/* 382:    */   
/* 383:    */   class AttributeOverrideOperation
/* 384:    */     implements AnnotationMocker.Operation
/* 385:    */   {
/* 386:    */     private Set<String> names;
/* 387:    */     private List<JaxbAttributeOverride> attributeOverrides;
/* 388:    */     
/* 389:    */     AttributeOverrideOperation(List<JaxbAttributeOverride> names)
/* 390:    */     {
/* 391:445 */       this.names = names;
/* 392:446 */       this.attributeOverrides = attributeOverrides;
/* 393:    */     }
/* 394:    */     
/* 395:    */     public boolean process(AnnotationInstance annotationInstance)
/* 396:    */     {
/* 397:451 */       String name = annotationInstance.value("name").asString();
/* 398:452 */       if (!this.names.contains(name))
/* 399:    */       {
/* 400:453 */         AnnotationMocker.JaxbAttributeOverrideProxy attributeOverride = new AnnotationMocker.JaxbAttributeOverrideProxy(AnnotationMocker.this);
/* 401:454 */         attributeOverride.setName(name);
/* 402:455 */         attributeOverride.setColumnAnnotationValue(annotationInstance.value("column"));
/* 403:456 */         this.attributeOverrides.add(attributeOverride);
/* 404:    */       }
/* 405:458 */       return false;
/* 406:    */     }
/* 407:    */   }
/* 408:    */   
/* 409:    */   class AssociationOverrideOperation
/* 410:    */     implements AnnotationMocker.Operation
/* 411:    */   {
/* 412:    */     private Set<String> names;
/* 413:    */     private List<JaxbAssociationOverride> associationOverrides;
/* 414:    */     
/* 415:    */     AssociationOverrideOperation(List<JaxbAssociationOverride> names)
/* 416:    */     {
/* 417:468 */       this.names = names;
/* 418:469 */       this.associationOverrides = associationOverrides;
/* 419:    */     }
/* 420:    */     
/* 421:    */     public boolean process(AnnotationInstance annotationInstance)
/* 422:    */     {
/* 423:474 */       String name = annotationInstance.value("name").asString();
/* 424:475 */       if (!this.names.contains(name))
/* 425:    */       {
/* 426:476 */         AnnotationMocker.JaxbAssociationOverrideProxy associationOverride = new AnnotationMocker.JaxbAssociationOverrideProxy(AnnotationMocker.this);
/* 427:477 */         associationOverride.setName(name);
/* 428:478 */         associationOverride.setJoinColumnsAnnotationValue(annotationInstance.value("joinColumns"));
/* 429:479 */         associationOverride.setJoinTableAnnotationValue(annotationInstance.value("joinTable"));
/* 430:480 */         this.associationOverrides.add(associationOverride);
/* 431:    */       }
/* 432:482 */       return false;
/* 433:    */     }
/* 434:    */   }
/* 435:    */   
/* 436:    */   class JaxbAssociationOverrideProxy
/* 437:    */     extends JaxbAssociationOverride
/* 438:    */   {
/* 439:    */     private AnnotationValue joinTableAnnotationValue;
/* 440:    */     private AnnotationValue joinColumnsAnnotationValue;
/* 441:    */     
/* 442:    */     JaxbAssociationOverrideProxy() {}
/* 443:    */     
/* 444:    */     AnnotationValue getJoinColumnsAnnotationValue()
/* 445:    */     {
/* 446:492 */       return this.joinColumnsAnnotationValue;
/* 447:    */     }
/* 448:    */     
/* 449:    */     void setJoinColumnsAnnotationValue(AnnotationValue joinColumnsAnnotationValue)
/* 450:    */     {
/* 451:496 */       this.joinColumnsAnnotationValue = joinColumnsAnnotationValue;
/* 452:    */     }
/* 453:    */     
/* 454:    */     AnnotationValue getJoinTableAnnotationValue()
/* 455:    */     {
/* 456:500 */       return this.joinTableAnnotationValue;
/* 457:    */     }
/* 458:    */     
/* 459:    */     void setJoinTableAnnotationValue(AnnotationValue joinTableAnnotationValue)
/* 460:    */     {
/* 461:504 */       this.joinTableAnnotationValue = joinTableAnnotationValue;
/* 462:    */     }
/* 463:    */   }
/* 464:    */   
/* 465:    */   class JaxbAttributeOverrideProxy
/* 466:    */     extends JaxbAttributeOverride
/* 467:    */   {
/* 468:    */     private AnnotationValue columnAnnotationValue;
/* 469:    */     
/* 470:    */     JaxbAttributeOverrideProxy() {}
/* 471:    */     
/* 472:    */     AnnotationValue getColumnAnnotationValue()
/* 473:    */     {
/* 474:512 */       return this.columnAnnotationValue;
/* 475:    */     }
/* 476:    */     
/* 477:    */     void setColumnAnnotationValue(AnnotationValue columnAnnotationValue)
/* 478:    */     {
/* 479:516 */       this.columnAnnotationValue = columnAnnotationValue;
/* 480:    */     }
/* 481:    */   }
/* 482:    */   
/* 483:    */   protected AnnotationInstance create(DotName name)
/* 484:    */   {
/* 485:529 */     return create(name, MockHelper.EMPTY_ANNOTATION_VALUE_ARRAY);
/* 486:    */   }
/* 487:    */   
/* 488:    */   protected AnnotationInstance create(DotName name, AnnotationValue[] annotationValues)
/* 489:    */   {
/* 490:533 */     return create(name, getTarget(), annotationValues);
/* 491:    */   }
/* 492:    */   
/* 493:    */   protected AnnotationInstance create(DotName name, List<AnnotationValue> annotationValueList)
/* 494:    */   {
/* 495:538 */     return create(name, getTarget(), annotationValueList);
/* 496:    */   }
/* 497:    */   
/* 498:    */   protected abstract DotName getTargetName();
/* 499:    */   
/* 500:    */   protected abstract AnnotationTarget getTarget();
/* 501:    */   
/* 502:    */   protected AnnotationInstance push(AnnotationInstance annotationInstance)
/* 503:    */   {
/* 504:552 */     if ((annotationInstance != null) && (annotationInstance.target() != null)) {
/* 505:553 */       this.indexBuilder.addAnnotationInstance(getTargetName(), annotationInstance);
/* 506:    */     }
/* 507:555 */     return annotationInstance;
/* 508:    */   }
/* 509:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.AnnotationMocker
 * JD-Core Version:    0.7.0.1
 */