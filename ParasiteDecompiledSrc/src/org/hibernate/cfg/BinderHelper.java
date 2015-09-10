/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Map.Entry;
/*  11:    */ import java.util.Properties;
/*  12:    */ import java.util.Set;
/*  13:    */ import java.util.StringTokenizer;
/*  14:    */ import org.hibernate.AnnotationException;
/*  15:    */ import org.hibernate.MappingException;
/*  16:    */ import org.hibernate.annotations.AnyMetaDef;
/*  17:    */ import org.hibernate.annotations.AnyMetaDefs;
/*  18:    */ import org.hibernate.annotations.MetaValue;
/*  19:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  20:    */ import org.hibernate.annotations.common.reflection.XAnnotatedElement;
/*  21:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  22:    */ import org.hibernate.annotations.common.reflection.XPackage;
/*  23:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  24:    */ import org.hibernate.cfg.annotations.EntityBinder;
/*  25:    */ import org.hibernate.cfg.annotations.Nullability;
/*  26:    */ import org.hibernate.cfg.annotations.TableBinder;
/*  27:    */ import org.hibernate.id.MultipleHiLoPerTableGenerator;
/*  28:    */ import org.hibernate.internal.CoreMessageLogger;
/*  29:    */ import org.hibernate.internal.util.StringHelper;
/*  30:    */ import org.hibernate.mapping.Any;
/*  31:    */ import org.hibernate.mapping.Collection;
/*  32:    */ import org.hibernate.mapping.Component;
/*  33:    */ import org.hibernate.mapping.IdGenerator;
/*  34:    */ import org.hibernate.mapping.Join;
/*  35:    */ import org.hibernate.mapping.MappedSuperclass;
/*  36:    */ import org.hibernate.mapping.PersistentClass;
/*  37:    */ import org.hibernate.mapping.Property;
/*  38:    */ import org.hibernate.mapping.SimpleValue;
/*  39:    */ import org.hibernate.mapping.SyntheticProperty;
/*  40:    */ import org.hibernate.mapping.Table;
/*  41:    */ import org.hibernate.mapping.ToOne;
/*  42:    */ import org.hibernate.mapping.Value;
/*  43:    */ import org.hibernate.type.DiscriminatorType;
/*  44:    */ import org.hibernate.type.Type;
/*  45:    */ import org.hibernate.type.TypeResolver;
/*  46:    */ import org.jboss.logging.Logger;
/*  47:    */ 
/*  48:    */ public class BinderHelper
/*  49:    */ {
/*  50:    */   public static final String ANNOTATION_STRING_DEFAULT = "";
/*  51: 76 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BinderHelper.class.getName());
/*  52:    */   public static final Set<String> PRIMITIVE_NAMES;
/*  53:    */   
/*  54:    */   static
/*  55:    */   {
/*  56: 82 */     Set<String> primitiveNames = new HashSet();
/*  57: 83 */     primitiveNames.add(Byte.TYPE.getName());
/*  58: 84 */     primitiveNames.add(Short.TYPE.getName());
/*  59: 85 */     primitiveNames.add(Integer.TYPE.getName());
/*  60: 86 */     primitiveNames.add(Long.TYPE.getName());
/*  61: 87 */     primitiveNames.add(Float.TYPE.getName());
/*  62: 88 */     primitiveNames.add(Double.TYPE.getName());
/*  63: 89 */     primitiveNames.add(Character.TYPE.getName());
/*  64: 90 */     primitiveNames.add(Boolean.TYPE.getName());
/*  65: 91 */     PRIMITIVE_NAMES = Collections.unmodifiableSet(primitiveNames);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static Property shallowCopy(Property property)
/*  69:    */   {
/*  70:100 */     Property clone = new Property();
/*  71:101 */     clone.setCascade(property.getCascade());
/*  72:102 */     clone.setInsertable(property.isInsertable());
/*  73:103 */     clone.setLazy(property.isLazy());
/*  74:104 */     clone.setName(property.getName());
/*  75:105 */     clone.setNodeName(property.getNodeName());
/*  76:106 */     clone.setNaturalIdentifier(property.isNaturalIdentifier());
/*  77:107 */     clone.setOptimisticLocked(property.isOptimisticLocked());
/*  78:108 */     clone.setOptional(property.isOptional());
/*  79:109 */     clone.setPersistentClass(property.getPersistentClass());
/*  80:110 */     clone.setPropertyAccessorName(property.getPropertyAccessorName());
/*  81:111 */     clone.setSelectable(property.isSelectable());
/*  82:112 */     clone.setUpdateable(property.isUpdateable());
/*  83:113 */     clone.setValue(property.getValue());
/*  84:114 */     return clone;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static void createSyntheticPropertyReference(Ejb3JoinColumn[] columns, PersistentClass ownerEntity, PersistentClass associatedEntity, Value value, boolean inverse, Mappings mappings)
/*  88:    */   {
/*  89:125 */     if ((columns[0].isImplicit()) || (StringHelper.isNotEmpty(columns[0].getMappedBy()))) {
/*  90:125 */       return;
/*  91:    */     }
/*  92:126 */     int fkEnum = Ejb3JoinColumn.checkReferencedColumnsType(columns, ownerEntity, mappings);
/*  93:127 */     PersistentClass associatedClass = columns[0].getPropertyHolder() != null ? columns[0].getPropertyHolder().getPersistentClass() : null;
/*  94:130 */     if (2 == fkEnum)
/*  95:    */     {
/*  96:138 */       StringBuilder propertyNameBuffer = new StringBuilder("_");
/*  97:139 */       propertyNameBuffer.append(associatedClass.getEntityName().replace('.', '_'));
/*  98:140 */       propertyNameBuffer.append("_").append(columns[0].getPropertyName());
/*  99:141 */       String syntheticPropertyName = propertyNameBuffer.toString();
/* 100:    */       
/* 101:143 */       Object columnOwner = findColumnOwner(ownerEntity, columns[0].getReferencedColumn(), mappings);
/* 102:144 */       List<Property> properties = findPropertiesByColumns(columnOwner, columns, mappings);
/* 103:    */       
/* 104:146 */       Property synthProp = null;
/* 105:147 */       if (properties != null)
/* 106:    */       {
/* 107:149 */         Component embeddedComp = (columnOwner instanceof PersistentClass) ? new Component(mappings, (PersistentClass)columnOwner) : new Component(mappings, (Join)columnOwner);
/* 108:    */         
/* 109:    */ 
/* 110:152 */         embeddedComp.setEmbedded(true);
/* 111:153 */         embeddedComp.setNodeName(syntheticPropertyName);
/* 112:154 */         embeddedComp.setComponentClassName(embeddedComp.getOwner().getClassName());
/* 113:155 */         for (Property property : properties)
/* 114:    */         {
/* 115:156 */           Property clone = shallowCopy(property);
/* 116:157 */           clone.setInsertable(false);
/* 117:158 */           clone.setUpdateable(false);
/* 118:159 */           clone.setNaturalIdentifier(false);
/* 119:160 */           clone.setGeneration(property.getGeneration());
/* 120:161 */           embeddedComp.addProperty(clone);
/* 121:    */         }
/* 122:163 */         synthProp = new SyntheticProperty();
/* 123:164 */         synthProp.setName(syntheticPropertyName);
/* 124:165 */         synthProp.setNodeName(syntheticPropertyName);
/* 125:166 */         synthProp.setPersistentClass(ownerEntity);
/* 126:167 */         synthProp.setUpdateable(false);
/* 127:168 */         synthProp.setInsertable(false);
/* 128:169 */         synthProp.setValue(embeddedComp);
/* 129:170 */         synthProp.setPropertyAccessorName("embedded");
/* 130:171 */         ownerEntity.addProperty(synthProp);
/* 131:    */         
/* 132:173 */         TableBinder.createUniqueConstraint(embeddedComp);
/* 133:    */       }
/* 134:    */       else
/* 135:    */       {
/* 136:177 */         StringBuilder columnsList = new StringBuilder();
/* 137:178 */         columnsList.append("referencedColumnNames(");
/* 138:179 */         for (Ejb3JoinColumn column : columns) {
/* 139:180 */           columnsList.append(column.getReferencedColumn()).append(", ");
/* 140:    */         }
/* 141:182 */         columnsList.setLength(columnsList.length() - 2);
/* 142:183 */         columnsList.append(") ");
/* 143:185 */         if (associatedEntity != null) {
/* 144:187 */           columnsList.append("of ").append(associatedEntity.getEntityName()).append(".").append(columns[0].getPropertyName()).append(" ");
/* 145:194 */         } else if (columns[0].getPropertyHolder() != null) {
/* 146:195 */           columnsList.append("of ").append(columns[0].getPropertyHolder().getEntityName()).append(".").append(columns[0].getPropertyName()).append(" ");
/* 147:    */         }
/* 148:202 */         columnsList.append("referencing ").append(ownerEntity.getEntityName()).append(" not mapped to a single property");
/* 149:    */         
/* 150:    */ 
/* 151:205 */         throw new AnnotationException(columnsList.toString());
/* 152:    */       }
/* 153:211 */       if ((value instanceof ToOne))
/* 154:    */       {
/* 155:212 */         ((ToOne)value).setReferencedPropertyName(syntheticPropertyName);
/* 156:213 */         mappings.addUniquePropertyReference(ownerEntity.getEntityName(), syntheticPropertyName);
/* 157:    */       }
/* 158:215 */       else if ((value instanceof Collection))
/* 159:    */       {
/* 160:216 */         ((Collection)value).setReferencedPropertyName(syntheticPropertyName);
/* 161:    */         
/* 162:218 */         mappings.addPropertyReference(ownerEntity.getEntityName(), syntheticPropertyName);
/* 163:    */       }
/* 164:    */       else
/* 165:    */       {
/* 166:221 */         throw new org.hibernate.AssertionFailure("Do a property ref on an unexpected Value type: " + value.getClass().getName());
/* 167:    */       }
/* 168:226 */       mappings.addPropertyReferencedAssociation((inverse ? "inverse__" : "") + associatedClass.getEntityName(), columns[0].getPropertyName(), syntheticPropertyName);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   private static List<Property> findPropertiesByColumns(Object columnOwner, Ejb3JoinColumn[] columns, Mappings mappings)
/* 173:    */   {
/* 174:239 */     Map<org.hibernate.mapping.Column, Set<Property>> columnsToProperty = new HashMap();
/* 175:240 */     List<org.hibernate.mapping.Column> orderedColumns = new ArrayList(columns.length);
/* 176:241 */     Table referencedTable = null;
/* 177:242 */     if ((columnOwner instanceof PersistentClass)) {
/* 178:243 */       referencedTable = ((PersistentClass)columnOwner).getTable();
/* 179:245 */     } else if ((columnOwner instanceof Join)) {
/* 180:246 */       referencedTable = ((Join)columnOwner).getTable();
/* 181:    */     } else {
/* 182:249 */       throw new org.hibernate.AssertionFailure("columnOwner neither PersistentClass nor Join: " + columnOwner.getClass());
/* 183:    */     }
/* 184:256 */     for (Ejb3JoinColumn column1 : columns)
/* 185:    */     {
/* 186:257 */       org.hibernate.mapping.Column column = new org.hibernate.mapping.Column(mappings.getPhysicalColumnName(column1.getReferencedColumn(), referencedTable));
/* 187:    */       
/* 188:    */ 
/* 189:260 */       orderedColumns.add(column);
/* 190:261 */       columnsToProperty.put(column, new HashSet());
/* 191:    */     }
/* 192:263 */     boolean isPersistentClass = columnOwner instanceof PersistentClass;
/* 193:264 */     Iterator it = isPersistentClass ? ((PersistentClass)columnOwner).getPropertyIterator() : ((Join)columnOwner).getPropertyIterator();
/* 194:267 */     while (it.hasNext()) {
/* 195:268 */       matchColumnsByProperty((Property)it.next(), columnsToProperty);
/* 196:    */     }
/* 197:270 */     if (isPersistentClass) {
/* 198:271 */       matchColumnsByProperty(((PersistentClass)columnOwner).getIdentifierProperty(), columnsToProperty);
/* 199:    */     }
/* 200:277 */     List<Property> orderedProperties = new ArrayList();
/* 201:278 */     for (org.hibernate.mapping.Column column : orderedColumns)
/* 202:    */     {
/* 203:279 */       boolean found = false;
/* 204:280 */       for (Property property : (Set)columnsToProperty.get(column)) {
/* 205:281 */         if (property.getColumnSpan() == 1)
/* 206:    */         {
/* 207:282 */           orderedProperties.add(property);
/* 208:283 */           found = true;
/* 209:284 */           break;
/* 210:    */         }
/* 211:    */       }
/* 212:287 */       if (!found) {
/* 213:287 */         return null;
/* 214:    */       }
/* 215:    */     }
/* 216:289 */     return orderedProperties;
/* 217:    */   }
/* 218:    */   
/* 219:    */   private static void matchColumnsByProperty(Property property, Map<org.hibernate.mapping.Column, Set<Property>> columnsToProperty)
/* 220:    */   {
/* 221:293 */     if (property == null) {
/* 222:293 */       return;
/* 223:    */     }
/* 224:294 */     if (("noop".equals(property.getPropertyAccessorName())) || ("embedded".equals(property.getPropertyAccessorName()))) {
/* 225:296 */       return;
/* 226:    */     }
/* 227:306 */     Iterator columnIt = property.getColumnIterator();
/* 228:307 */     while (columnIt.hasNext())
/* 229:    */     {
/* 230:308 */       Object column = columnIt.next();
/* 231:310 */       if (columnsToProperty.containsKey(column)) {
/* 232:311 */         ((Set)columnsToProperty.get(column)).add(property);
/* 233:    */       }
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static Property findPropertyByName(PersistentClass associatedClass, String propertyName)
/* 238:    */   {
/* 239:322 */     Property property = null;
/* 240:323 */     Property idProperty = associatedClass.getIdentifierProperty();
/* 241:324 */     String idName = idProperty != null ? idProperty.getName() : null;
/* 242:    */     try
/* 243:    */     {
/* 244:326 */       if ((propertyName == null) || (propertyName.length() == 0) || (propertyName.equals(idName)))
/* 245:    */       {
/* 246:330 */         property = idProperty;
/* 247:    */       }
/* 248:    */       else
/* 249:    */       {
/* 250:333 */         if (propertyName.indexOf(idName + ".") == 0)
/* 251:    */         {
/* 252:334 */           property = idProperty;
/* 253:335 */           propertyName = propertyName.substring(idName.length() + 1);
/* 254:    */         }
/* 255:337 */         StringTokenizer st = new StringTokenizer(propertyName, ".", false);
/* 256:338 */         while (st.hasMoreElements())
/* 257:    */         {
/* 258:339 */           String element = (String)st.nextElement();
/* 259:340 */           if (property == null)
/* 260:    */           {
/* 261:341 */             property = associatedClass.getProperty(element);
/* 262:    */           }
/* 263:    */           else
/* 264:    */           {
/* 265:344 */             if (!property.isComposite()) {
/* 266:344 */               return null;
/* 267:    */             }
/* 268:345 */             property = ((Component)property.getValue()).getProperty(element);
/* 269:    */           }
/* 270:    */         }
/* 271:    */       }
/* 272:    */     }
/* 273:    */     catch (MappingException e)
/* 274:    */     {
/* 275:    */       try
/* 276:    */       {
/* 277:353 */         if (associatedClass.getIdentifierMapper() == null) {
/* 278:353 */           return null;
/* 279:    */         }
/* 280:354 */         StringTokenizer st = new StringTokenizer(propertyName, ".", false);
/* 281:355 */         while (st.hasMoreElements())
/* 282:    */         {
/* 283:356 */           String element = (String)st.nextElement();
/* 284:357 */           if (property == null)
/* 285:    */           {
/* 286:358 */             property = associatedClass.getIdentifierMapper().getProperty(element);
/* 287:    */           }
/* 288:    */           else
/* 289:    */           {
/* 290:361 */             if (!property.isComposite()) {
/* 291:361 */               return null;
/* 292:    */             }
/* 293:362 */             property = ((Component)property.getValue()).getProperty(element);
/* 294:    */           }
/* 295:    */         }
/* 296:    */       }
/* 297:    */       catch (MappingException ee)
/* 298:    */       {
/* 299:367 */         return null;
/* 300:    */       }
/* 301:    */     }
/* 302:370 */     return property;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static Property findPropertyByName(Component component, String propertyName)
/* 306:    */   {
/* 307:377 */     Property property = null;
/* 308:    */     try
/* 309:    */     {
/* 310:379 */       if ((propertyName == null) || (propertyName.length() == 0)) {
/* 311:382 */         return null;
/* 312:    */       }
/* 313:385 */       StringTokenizer st = new StringTokenizer(propertyName, ".", false);
/* 314:386 */       while (st.hasMoreElements())
/* 315:    */       {
/* 316:387 */         String element = (String)st.nextElement();
/* 317:388 */         if (property == null)
/* 318:    */         {
/* 319:389 */           property = component.getProperty(element);
/* 320:    */         }
/* 321:    */         else
/* 322:    */         {
/* 323:392 */           if (!property.isComposite()) {
/* 324:392 */             return null;
/* 325:    */           }
/* 326:393 */           property = ((Component)property.getValue()).getProperty(element);
/* 327:    */         }
/* 328:    */       }
/* 329:    */     }
/* 330:    */     catch (MappingException e)
/* 331:    */     {
/* 332:    */       try
/* 333:    */       {
/* 334:401 */         if (component.getOwner().getIdentifierMapper() == null) {
/* 335:401 */           return null;
/* 336:    */         }
/* 337:402 */         StringTokenizer st = new StringTokenizer(propertyName, ".", false);
/* 338:403 */         while (st.hasMoreElements())
/* 339:    */         {
/* 340:404 */           String element = (String)st.nextElement();
/* 341:405 */           if (property == null)
/* 342:    */           {
/* 343:406 */             property = component.getOwner().getIdentifierMapper().getProperty(element);
/* 344:    */           }
/* 345:    */           else
/* 346:    */           {
/* 347:409 */             if (!property.isComposite()) {
/* 348:409 */               return null;
/* 349:    */             }
/* 350:410 */             property = ((Component)property.getValue()).getProperty(element);
/* 351:    */           }
/* 352:    */         }
/* 353:    */       }
/* 354:    */       catch (MappingException ee)
/* 355:    */       {
/* 356:415 */         return null;
/* 357:    */       }
/* 358:    */     }
/* 359:418 */     return property;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public static String getRelativePath(PropertyHolder propertyHolder, String propertyName)
/* 363:    */   {
/* 364:422 */     if (propertyHolder == null) {
/* 365:422 */       return propertyName;
/* 366:    */     }
/* 367:423 */     String path = propertyHolder.getPath();
/* 368:424 */     String entityName = propertyHolder.getPersistentClass().getEntityName();
/* 369:425 */     if (path.length() == entityName.length()) {
/* 370:426 */       return propertyName;
/* 371:    */     }
/* 372:429 */     return StringHelper.qualify(path.substring(entityName.length() + 1), propertyName);
/* 373:    */   }
/* 374:    */   
/* 375:    */   public static Object findColumnOwner(PersistentClass persistentClass, String columnName, Mappings mappings)
/* 376:    */   {
/* 377:441 */     if (StringHelper.isEmpty(columnName)) {
/* 378:442 */       return persistentClass;
/* 379:    */     }
/* 380:444 */     PersistentClass current = persistentClass;
/* 381:    */     
/* 382:446 */     boolean found = false;
/* 383:    */     Object result;
/* 384:    */     do
/* 385:    */     {
/* 386:448 */       result = current;
/* 387:449 */       Table currentTable = current.getTable();
/* 388:    */       try
/* 389:    */       {
/* 390:451 */         mappings.getPhysicalColumnName(columnName, currentTable);
/* 391:452 */         found = true;
/* 392:    */       }
/* 393:    */       catch (MappingException me) {}
/* 394:457 */       Iterator joins = current.getJoinIterator();
/* 395:458 */       while ((!found) && (joins.hasNext()))
/* 396:    */       {
/* 397:459 */         result = joins.next();
/* 398:460 */         currentTable = ((Join)result).getTable();
/* 399:    */         try
/* 400:    */         {
/* 401:462 */           mappings.getPhysicalColumnName(columnName, currentTable);
/* 402:463 */           found = true;
/* 403:    */         }
/* 404:    */         catch (MappingException me) {}
/* 405:    */       }
/* 406:469 */       current = current.getSuperclass();
/* 407:471 */     } while ((!found) && (current != null));
/* 408:472 */     return found ? result : null;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public static void makeIdGenerator(SimpleValue id, String generatorType, String generatorName, Mappings mappings, Map<String, IdGenerator> localGenerators)
/* 412:    */   {
/* 413:484 */     Table table = id.getTable();
/* 414:485 */     table.setIdentifierValue(id);
/* 415:    */     
/* 416:487 */     id.setIdentifierGeneratorStrategy(generatorType);
/* 417:488 */     Properties params = new Properties();
/* 418:    */     
/* 419:490 */     params.setProperty("target_table", table.getName());
/* 420:494 */     if (id.getColumnSpan() == 1) {
/* 421:495 */       params.setProperty("target_column", ((org.hibernate.mapping.Column)id.getColumnIterator().next()).getName());
/* 422:    */     }
/* 423:501 */     params.put("identifier_normalizer", mappings.getObjectNameNormalizer());
/* 424:503 */     if (!isEmptyAnnotationValue(generatorName))
/* 425:    */     {
/* 426:505 */       IdGenerator gen = mappings.getGenerator(generatorName, localGenerators);
/* 427:506 */       if (gen == null) {
/* 428:507 */         throw new AnnotationException("Unknown Id.generator: " + generatorName);
/* 429:    */       }
/* 430:510 */       String identifierGeneratorStrategy = gen.getIdentifierGeneratorStrategy();
/* 431:    */       
/* 432:512 */       boolean avoidOverriding = (identifierGeneratorStrategy.equals("identity")) || (identifierGeneratorStrategy.equals("seqhilo")) || (identifierGeneratorStrategy.equals(MultipleHiLoPerTableGenerator.class.getName()));
/* 433:516 */       if ((generatorType == null) || (!avoidOverriding)) {
/* 434:517 */         id.setIdentifierGeneratorStrategy(identifierGeneratorStrategy);
/* 435:    */       }
/* 436:520 */       Iterator genParams = gen.getParams().entrySet().iterator();
/* 437:521 */       while (genParams.hasNext())
/* 438:    */       {
/* 439:522 */         Map.Entry elt = (Map.Entry)genParams.next();
/* 440:523 */         params.setProperty((String)elt.getKey(), (String)elt.getValue());
/* 441:    */       }
/* 442:    */     }
/* 443:526 */     if ("assigned".equals(generatorType)) {
/* 444:526 */       id.setNullValue("undefined");
/* 445:    */     }
/* 446:527 */     id.setIdentifierGeneratorProperties(params);
/* 447:    */   }
/* 448:    */   
/* 449:    */   public static boolean isEmptyAnnotationValue(String annotationString)
/* 450:    */   {
/* 451:531 */     return (annotationString != null) && (annotationString.length() == 0);
/* 452:    */   }
/* 453:    */   
/* 454:    */   public static Any buildAnyValue(String anyMetaDefName, Ejb3JoinColumn[] columns, javax.persistence.Column metaColumn, PropertyData inferredData, boolean cascadeOnDelete, Nullability nullability, PropertyHolder propertyHolder, EntityBinder entityBinder, boolean optional, Mappings mappings)
/* 455:    */   {
/* 456:547 */     Any value = new Any(mappings, columns[0].getTable());
/* 457:548 */     AnyMetaDef metaAnnDef = (AnyMetaDef)inferredData.getProperty().getAnnotation(AnyMetaDef.class);
/* 458:550 */     if (metaAnnDef != null) {
/* 459:552 */       bindAnyMetaDefs(inferredData.getProperty(), mappings);
/* 460:    */     } else {
/* 461:555 */       metaAnnDef = mappings.getAnyMetaDef(anyMetaDefName);
/* 462:    */     }
/* 463:557 */     if (metaAnnDef != null)
/* 464:    */     {
/* 465:558 */       value.setIdentifierType(metaAnnDef.idType());
/* 466:559 */       value.setMetaType(metaAnnDef.metaType());
/* 467:    */       
/* 468:561 */       HashMap values = new HashMap();
/* 469:562 */       Type metaType = mappings.getTypeResolver().heuristicType(value.getMetaType());
/* 470:563 */       for (MetaValue metaValue : metaAnnDef.metaValues()) {
/* 471:    */         try
/* 472:    */         {
/* 473:565 */           Object discrim = ((DiscriminatorType)metaType).stringToObject(metaValue.value());
/* 474:    */           
/* 475:567 */           String entityName = metaValue.targetEntity().getName();
/* 476:568 */           values.put(discrim, entityName);
/* 477:    */         }
/* 478:    */         catch (ClassCastException cce)
/* 479:    */         {
/* 480:571 */           throw new MappingException("metaType was not a DiscriminatorType: " + metaType.getName());
/* 481:    */         }
/* 482:    */         catch (Exception e)
/* 483:    */         {
/* 484:575 */           throw new MappingException("could not interpret metaValue", e);
/* 485:    */         }
/* 486:    */       }
/* 487:578 */       if (!values.isEmpty()) {
/* 488:578 */         value.setMetaValues(values);
/* 489:    */       }
/* 490:    */     }
/* 491:    */     else
/* 492:    */     {
/* 493:581 */       throw new AnnotationException("Unable to find @AnyMetaDef for an @(ManyTo)Any mapping: " + StringHelper.qualify(propertyHolder.getPath(), inferredData.getPropertyName()));
/* 494:    */     }
/* 495:585 */     value.setCascadeDeleteEnabled(cascadeOnDelete);
/* 496:586 */     if (!optional) {
/* 497:587 */       for (Ejb3JoinColumn column : columns) {
/* 498:588 */         column.setNullable(false);
/* 499:    */       }
/* 500:    */     }
/* 501:592 */     Ejb3Column[] metaColumns = Ejb3Column.buildColumnFromAnnotation(new javax.persistence.Column[] { metaColumn }, null, nullability, propertyHolder, inferredData, entityBinder.getSecondaryTables(), mappings);
/* 502:597 */     for (Ejb3Column column : metaColumns) {
/* 503:598 */       column.setTable(value.getTable());
/* 504:    */     }
/* 505:601 */     for (Ejb3Column column : metaColumns) {
/* 506:602 */       column.linkWithValue(value);
/* 507:    */     }
/* 508:606 */     String propertyName = inferredData.getPropertyName();
/* 509:607 */     Ejb3Column.checkPropertyConsistency(columns, propertyHolder.getEntityName() + propertyName);
/* 510:608 */     for (Ejb3JoinColumn column : columns) {
/* 511:609 */       column.linkWithValue(value);
/* 512:    */     }
/* 513:611 */     return value;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public static void bindAnyMetaDefs(XAnnotatedElement annotatedElement, Mappings mappings)
/* 517:    */   {
/* 518:615 */     AnyMetaDef defAnn = (AnyMetaDef)annotatedElement.getAnnotation(AnyMetaDef.class);
/* 519:616 */     AnyMetaDefs defsAnn = (AnyMetaDefs)annotatedElement.getAnnotation(AnyMetaDefs.class);
/* 520:617 */     boolean mustHaveName = (XClass.class.isAssignableFrom(annotatedElement.getClass())) || (XPackage.class.isAssignableFrom(annotatedElement.getClass()));
/* 521:619 */     if (defAnn != null)
/* 522:    */     {
/* 523:620 */       checkAnyMetaDefValidity(mustHaveName, defAnn, annotatedElement);
/* 524:621 */       bindAnyMetaDef(defAnn, mappings);
/* 525:    */     }
/* 526:623 */     if (defsAnn != null) {
/* 527:624 */       for (AnyMetaDef def : defsAnn.value())
/* 528:    */       {
/* 529:625 */         checkAnyMetaDefValidity(mustHaveName, def, annotatedElement);
/* 530:626 */         bindAnyMetaDef(def, mappings);
/* 531:    */       }
/* 532:    */     }
/* 533:    */   }
/* 534:    */   
/* 535:    */   private static void checkAnyMetaDefValidity(boolean mustHaveName, AnyMetaDef defAnn, XAnnotatedElement annotatedElement)
/* 536:    */   {
/* 537:632 */     if ((mustHaveName) && (isEmptyAnnotationValue(defAnn.name())))
/* 538:    */     {
/* 539:633 */       String name = XClass.class.isAssignableFrom(annotatedElement.getClass()) ? ((XClass)annotatedElement).getName() : ((XPackage)annotatedElement).getName();
/* 540:    */       
/* 541:    */ 
/* 542:636 */       throw new AnnotationException("@AnyMetaDef.name cannot be null on an entity or a package: " + name);
/* 543:    */     }
/* 544:    */   }
/* 545:    */   
/* 546:    */   private static void bindAnyMetaDef(AnyMetaDef defAnn, Mappings mappings)
/* 547:    */   {
/* 548:641 */     if (isEmptyAnnotationValue(defAnn.name())) {
/* 549:641 */       return;
/* 550:    */     }
/* 551:642 */     if (LOG.isDebugEnabled()) {
/* 552:643 */       LOG.debugf("Binding Any Meta definition: %s", defAnn.name());
/* 553:    */     }
/* 554:645 */     mappings.addAnyMetaDef(defAnn);
/* 555:    */   }
/* 556:    */   
/* 557:    */   public static MappedSuperclass getMappedSuperclassOrNull(XClass declaringClass, Map<XClass, InheritanceState> inheritanceStatePerClass, Mappings mappings)
/* 558:    */   {
/* 559:652 */     boolean retrieve = false;
/* 560:653 */     if (declaringClass != null)
/* 561:    */     {
/* 562:654 */       InheritanceState inheritanceState = (InheritanceState)inheritanceStatePerClass.get(declaringClass);
/* 563:655 */       if (inheritanceState == null) {
/* 564:656 */         throw new org.hibernate.annotations.common.AssertionFailure("Declaring class is not found in the inheritance state hierarchy: " + declaringClass);
/* 565:    */       }
/* 566:660 */       if (inheritanceState.isEmbeddableSuperclass()) {
/* 567:661 */         retrieve = true;
/* 568:    */       }
/* 569:    */     }
/* 570:664 */     return retrieve ? mappings.getMappedSuperclass(mappings.getReflectionManager().toClass(declaringClass)) : null;
/* 571:    */   }
/* 572:    */   
/* 573:    */   public static String getPath(PropertyHolder holder, PropertyData property)
/* 574:    */   {
/* 575:670 */     return StringHelper.qualify(holder.getPath(), property.getPropertyName());
/* 576:    */   }
/* 577:    */   
/* 578:    */   static PropertyData getPropertyOverriddenByMapperOrMapsId(boolean isId, PropertyHolder propertyHolder, String propertyName, Mappings mappings)
/* 579:    */   {
/* 580:    */     XClass persistentXClass;
/* 581:    */     try
/* 582:    */     {
/* 583:680 */       persistentXClass = mappings.getReflectionManager().classForName(propertyHolder.getPersistentClass().getClassName(), AnnotationBinder.class);
/* 584:    */     }
/* 585:    */     catch (ClassNotFoundException e)
/* 586:    */     {
/* 587:684 */       throw new org.hibernate.AssertionFailure("PersistentClass name cannot be converted into a Class", e);
/* 588:    */     }
/* 589:686 */     if (propertyHolder.isInIdClass())
/* 590:    */     {
/* 591:687 */       PropertyData pd = mappings.getPropertyAnnotatedWithIdAndToOne(persistentXClass, propertyName);
/* 592:688 */       if ((pd == null) && (mappings.isSpecjProprietarySyntaxEnabled())) {
/* 593:689 */         pd = mappings.getPropertyAnnotatedWithMapsId(persistentXClass, propertyName);
/* 594:    */       }
/* 595:691 */       return pd;
/* 596:    */     }
/* 597:693 */     String propertyPath = isId ? "" : propertyName;
/* 598:694 */     return mappings.getPropertyAnnotatedWithMapsId(persistentXClass, propertyPath);
/* 599:    */   }
/* 600:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.BinderHelper
 * JD-Core Version:    0.7.0.1
 */