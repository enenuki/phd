/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.UniqueConstraint;
/*   8:    */ import org.hibernate.AnnotationException;
/*   9:    */ import org.hibernate.AssertionFailure;
/*  10:    */ import org.hibernate.annotations.Index;
/*  11:    */ import org.hibernate.cfg.BinderHelper;
/*  12:    */ import org.hibernate.cfg.Ejb3JoinColumn;
/*  13:    */ import org.hibernate.cfg.IndexOrUniqueKeySecondPass;
/*  14:    */ import org.hibernate.cfg.Mappings;
/*  15:    */ import org.hibernate.cfg.NamingStrategy;
/*  16:    */ import org.hibernate.cfg.ObjectNameNormalizer;
/*  17:    */ import org.hibernate.cfg.ObjectNameNormalizer.NamingStrategyHelper;
/*  18:    */ import org.hibernate.cfg.ObjectNameSource;
/*  19:    */ import org.hibernate.cfg.PropertyHolder;
/*  20:    */ import org.hibernate.cfg.UniqueConstraintHolder;
/*  21:    */ import org.hibernate.internal.CoreMessageLogger;
/*  22:    */ import org.hibernate.internal.util.StringHelper;
/*  23:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  24:    */ import org.hibernate.mapping.Collection;
/*  25:    */ import org.hibernate.mapping.Column;
/*  26:    */ import org.hibernate.mapping.DependantValue;
/*  27:    */ import org.hibernate.mapping.JoinedSubclass;
/*  28:    */ import org.hibernate.mapping.KeyValue;
/*  29:    */ import org.hibernate.mapping.PersistentClass;
/*  30:    */ import org.hibernate.mapping.Property;
/*  31:    */ import org.hibernate.mapping.SimpleValue;
/*  32:    */ import org.hibernate.mapping.Table;
/*  33:    */ import org.hibernate.mapping.ToOne;
/*  34:    */ import org.hibernate.mapping.Value;
/*  35:    */ import org.jboss.logging.Logger;
/*  36:    */ 
/*  37:    */ public class TableBinder
/*  38:    */ {
/*  39: 66 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TableBinder.class.getName());
/*  40:    */   private String schema;
/*  41:    */   private String catalog;
/*  42:    */   private String name;
/*  43:    */   private boolean isAbstract;
/*  44:    */   private List<UniqueConstraintHolder> uniqueConstraints;
/*  45:    */   String constraints;
/*  46:    */   Table denormalizedSuperTable;
/*  47:    */   Mappings mappings;
/*  48:    */   private String ownerEntityTable;
/*  49:    */   private String associatedEntityTable;
/*  50:    */   private String propertyName;
/*  51:    */   private String ownerEntity;
/*  52:    */   private String associatedEntity;
/*  53:    */   private boolean isJPA2ElementCollection;
/*  54:    */   
/*  55:    */   public void setSchema(String schema)
/*  56:    */   {
/*  57: 85 */     this.schema = schema;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setCatalog(String catalog)
/*  61:    */   {
/*  62: 89 */     this.catalog = catalog;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getName()
/*  66:    */   {
/*  67: 93 */     return this.name;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setName(String name)
/*  71:    */   {
/*  72: 97 */     this.name = name;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setAbstract(boolean anAbstract)
/*  76:    */   {
/*  77:101 */     this.isAbstract = anAbstract;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setUniqueConstraints(UniqueConstraint[] uniqueConstraints)
/*  81:    */   {
/*  82:105 */     this.uniqueConstraints = buildUniqueConstraintHolders(uniqueConstraints);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setConstraints(String constraints)
/*  86:    */   {
/*  87:109 */     this.constraints = constraints;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setDenormalizedSuperTable(Table denormalizedSuperTable)
/*  91:    */   {
/*  92:113 */     this.denormalizedSuperTable = denormalizedSuperTable;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setMappings(Mappings mappings)
/*  96:    */   {
/*  97:117 */     this.mappings = mappings;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setJPA2ElementCollection(boolean isJPA2ElementCollection)
/* 101:    */   {
/* 102:121 */     this.isJPA2ElementCollection = isJPA2ElementCollection;
/* 103:    */   }
/* 104:    */   
/* 105:    */   private static class AssociationTableNameSource
/* 106:    */     implements ObjectNameSource
/* 107:    */   {
/* 108:    */     private final String explicitName;
/* 109:    */     private final String logicalName;
/* 110:    */     
/* 111:    */     private AssociationTableNameSource(String explicitName, String logicalName)
/* 112:    */     {
/* 113:129 */       this.explicitName = explicitName;
/* 114:130 */       this.logicalName = logicalName;
/* 115:    */     }
/* 116:    */     
/* 117:    */     public String getExplicitName()
/* 118:    */     {
/* 119:134 */       return this.explicitName;
/* 120:    */     }
/* 121:    */     
/* 122:    */     public String getLogicalName()
/* 123:    */     {
/* 124:138 */       return this.logicalName;
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Table bind()
/* 129:    */   {
/* 130:145 */     String unquotedOwnerTable = StringHelper.unquote(this.ownerEntityTable);
/* 131:146 */     final String unquotedAssocTable = StringHelper.unquote(this.associatedEntityTable);
/* 132:    */     
/* 133:    */ 
/* 134:    */ 
/* 135:150 */     final String ownerObjectName = (this.isJPA2ElementCollection) && (this.ownerEntity != null) ? StringHelper.unqualify(this.ownerEntity) : unquotedOwnerTable;
/* 136:    */     
/* 137:152 */     ObjectNameSource nameSource = buildNameContext(ownerObjectName, unquotedAssocTable);
/* 138:    */     
/* 139:    */ 
/* 140:    */ 
/* 141:156 */     final boolean ownerEntityTableQuoted = StringHelper.isQuoted(this.ownerEntityTable);
/* 142:157 */     final boolean associatedEntityTableQuoted = StringHelper.isQuoted(this.associatedEntityTable);
/* 143:158 */     ObjectNameNormalizer.NamingStrategyHelper namingStrategyHelper = new ObjectNameNormalizer.NamingStrategyHelper()
/* 144:    */     {
/* 145:    */       public String determineImplicitName(NamingStrategy strategy)
/* 146:    */       {
/* 147:161 */         String strategyResult = strategy.collectionTableName(TableBinder.this.ownerEntity, ownerObjectName, TableBinder.this.associatedEntity, unquotedAssocTable, TableBinder.this.propertyName);
/* 148:    */         
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:169 */         return (ownerEntityTableQuoted) || (associatedEntityTableQuoted) ? StringHelper.quote(strategyResult) : strategyResult;
/* 156:    */       }
/* 157:    */       
/* 158:    */       public String handleExplicitName(NamingStrategy strategy, String name)
/* 159:    */       {
/* 160:175 */         return strategy.tableName(name);
/* 161:    */       }
/* 162:178 */     };
/* 163:179 */     return buildAndFillTable(this.schema, this.catalog, nameSource, namingStrategyHelper, this.isAbstract, this.uniqueConstraints, this.constraints, this.denormalizedSuperTable, this.mappings, null);
/* 164:    */   }
/* 165:    */   
/* 166:    */   private ObjectNameSource buildNameContext(String unquotedOwnerTable, String unquotedAssocTable)
/* 167:    */   {
/* 168:194 */     String logicalName = this.mappings.getNamingStrategy().logicalCollectionTableName(this.name, unquotedOwnerTable, unquotedAssocTable, this.propertyName);
/* 169:200 */     if ((StringHelper.isQuoted(this.ownerEntityTable)) || (StringHelper.isQuoted(this.associatedEntityTable))) {
/* 170:201 */       logicalName = StringHelper.quote(logicalName);
/* 171:    */     }
/* 172:204 */     return new AssociationTableNameSource(this.name, logicalName, null);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static Table buildAndFillTable(String schema, String catalog, ObjectNameSource nameSource, ObjectNameNormalizer.NamingStrategyHelper namingStrategyHelper, boolean isAbstract, List<UniqueConstraintHolder> uniqueConstraints, String constraints, Table denormalizedSuperTable, Mappings mappings, String subselect)
/* 176:    */   {
/* 177:218 */     schema = BinderHelper.isEmptyAnnotationValue(schema) ? mappings.getSchemaName() : schema;
/* 178:219 */     catalog = BinderHelper.isEmptyAnnotationValue(catalog) ? mappings.getCatalogName() : catalog;
/* 179:    */     
/* 180:221 */     String realTableName = mappings.getObjectNameNormalizer().normalizeDatabaseIdentifier(nameSource.getExplicitName(), namingStrategyHelper);
/* 181:    */     Table table;
/* 182:    */     Table table;
/* 183:227 */     if (denormalizedSuperTable != null) {
/* 184:228 */       table = mappings.addDenormalizedTable(schema, catalog, realTableName, isAbstract, subselect, denormalizedSuperTable);
/* 185:    */     } else {
/* 186:238 */       table = mappings.addTable(schema, catalog, realTableName, subselect, isAbstract);
/* 187:    */     }
/* 188:247 */     if ((uniqueConstraints != null) && (uniqueConstraints.size() > 0)) {
/* 189:248 */       mappings.addUniqueConstraintHolders(table, uniqueConstraints);
/* 190:    */     }
/* 191:251 */     if (constraints != null) {
/* 192:251 */       table.addCheckConstraint(constraints);
/* 193:    */     }
/* 194:254 */     String logicalName = nameSource.getLogicalName();
/* 195:255 */     if (logicalName != null) {
/* 196:256 */       mappings.addTableBinding(schema, catalog, logicalName, realTableName, denormalizedSuperTable);
/* 197:    */     }
/* 198:258 */     return table;
/* 199:    */   }
/* 200:    */   
/* 201:    */   @Deprecated
/* 202:    */   public static Table fillTable(String schema, String catalog, String realTableName, String logicalName, boolean isAbstract, List uniqueConstraints, String constraints, Table denormalizedSuperTable, Mappings mappings)
/* 203:    */   {
/* 204:288 */     schema = BinderHelper.isEmptyAnnotationValue(schema) ? mappings.getSchemaName() : schema;
/* 205:289 */     catalog = BinderHelper.isEmptyAnnotationValue(catalog) ? mappings.getCatalogName() : catalog;
/* 206:    */     Table table;
/* 207:    */     Table table;
/* 208:291 */     if (denormalizedSuperTable != null) {
/* 209:292 */       table = mappings.addDenormalizedTable(schema, catalog, realTableName, isAbstract, null, denormalizedSuperTable);
/* 210:    */     } else {
/* 211:302 */       table = mappings.addTable(schema, catalog, realTableName, null, isAbstract);
/* 212:    */     }
/* 213:310 */     if ((uniqueConstraints != null) && (uniqueConstraints.size() > 0)) {
/* 214:311 */       mappings.addUniqueConstraints(table, uniqueConstraints);
/* 215:    */     }
/* 216:313 */     if (constraints != null) {
/* 217:313 */       table.addCheckConstraint(constraints);
/* 218:    */     }
/* 219:315 */     if (logicalName != null) {
/* 220:316 */       mappings.addTableBinding(schema, catalog, logicalName, realTableName, denormalizedSuperTable);
/* 221:    */     }
/* 222:318 */     return table;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public static void bindFk(PersistentClass referencedEntity, PersistentClass destinationEntity, Ejb3JoinColumn[] columns, SimpleValue value, boolean unique, Mappings mappings)
/* 226:    */   {
/* 227:    */     PersistentClass associatedClass;
/* 228:    */     PersistentClass associatedClass;
/* 229:329 */     if (destinationEntity != null) {
/* 230:331 */       associatedClass = destinationEntity;
/* 231:    */     } else {
/* 232:334 */       associatedClass = columns[0].getPropertyHolder() == null ? null : columns[0].getPropertyHolder().getPersistentClass();
/* 233:    */     }
/* 234:338 */     String mappedByProperty = columns[0].getMappedBy();
/* 235:339 */     if (StringHelper.isNotEmpty(mappedByProperty))
/* 236:    */     {
/* 237:344 */       LOG.debugf("Retrieving property %s.%s", associatedClass.getEntityName(), mappedByProperty);
/* 238:    */       
/* 239:346 */       Property property = associatedClass.getRecursiveProperty(columns[0].getMappedBy());
/* 240:    */       Iterator mappedByColumns;
/* 241:    */       Iterator mappedByColumns;
/* 242:348 */       if ((property.getValue() instanceof Collection))
/* 243:    */       {
/* 244:349 */         Collection collection = (Collection)property.getValue();
/* 245:350 */         Value element = collection.getElement();
/* 246:351 */         if (element == null) {
/* 247:352 */           throw new AnnotationException("Illegal use of mappedBy on both sides of the relationship: " + associatedClass.getEntityName() + "." + mappedByProperty);
/* 248:    */         }
/* 249:357 */         mappedByColumns = element.getColumnIterator();
/* 250:    */       }
/* 251:    */       else
/* 252:    */       {
/* 253:360 */         mappedByColumns = property.getValue().getColumnIterator();
/* 254:    */       }
/* 255:362 */       while (mappedByColumns.hasNext())
/* 256:    */       {
/* 257:363 */         Column column = (Column)mappedByColumns.next();
/* 258:364 */         columns[0].overrideFromReferencedColumnIfNecessary(column);
/* 259:365 */         columns[0].linkValueUsingAColumnCopy(column, value);
/* 260:    */       }
/* 261:    */     }
/* 262:368 */     else if (columns[0].isImplicit())
/* 263:    */     {
/* 264:    */       Iterator idColumns;
/* 265:    */       Iterator idColumns;
/* 266:374 */       if ((referencedEntity instanceof JoinedSubclass)) {
/* 267:375 */         idColumns = referencedEntity.getKey().getColumnIterator();
/* 268:    */       } else {
/* 269:378 */         idColumns = referencedEntity.getIdentifier().getColumnIterator();
/* 270:    */       }
/* 271:380 */       while (idColumns.hasNext())
/* 272:    */       {
/* 273:381 */         Column column = (Column)idColumns.next();
/* 274:382 */         columns[0].overrideFromReferencedColumnIfNecessary(column);
/* 275:383 */         columns[0].linkValueUsingDefaultColumnNaming(column, referencedEntity, value);
/* 276:    */       }
/* 277:    */     }
/* 278:    */     else
/* 279:    */     {
/* 280:387 */       int fkEnum = Ejb3JoinColumn.checkReferencedColumnsType(columns, referencedEntity, mappings);
/* 281:389 */       if (2 == fkEnum)
/* 282:    */       {
/* 283:    */         String referencedPropertyName;
/* 284:391 */         if ((value instanceof ToOne))
/* 285:    */         {
/* 286:392 */           referencedPropertyName = ((ToOne)value).getReferencedPropertyName();
/* 287:    */         }
/* 288:    */         else
/* 289:    */         {
/* 290:    */           String referencedPropertyName;
/* 291:394 */           if ((value instanceof DependantValue))
/* 292:    */           {
/* 293:395 */             String propertyName = columns[0].getPropertyName();
/* 294:    */             String referencedPropertyName;
/* 295:396 */             if (propertyName != null)
/* 296:    */             {
/* 297:397 */               Collection collection = (Collection)referencedEntity.getRecursiveProperty(propertyName).getValue();
/* 298:    */               
/* 299:399 */               referencedPropertyName = collection.getReferencedPropertyName();
/* 300:    */             }
/* 301:    */             else
/* 302:    */             {
/* 303:402 */               throw new AnnotationException("SecondaryTable JoinColumn cannot reference a non primary key");
/* 304:    */             }
/* 305:    */           }
/* 306:    */           else
/* 307:    */           {
/* 308:407 */             throw new AssertionFailure("Do a property ref on an unexpected Value type: " + value.getClass().getName());
/* 309:    */           }
/* 310:    */         }
/* 311:    */         String referencedPropertyName;
/* 312:412 */         if (referencedPropertyName == null) {
/* 313:413 */           throw new AssertionFailure("No property ref found while expected");
/* 314:    */         }
/* 315:417 */         Property synthProp = referencedEntity.getRecursiveProperty(referencedPropertyName);
/* 316:418 */         if (synthProp == null) {
/* 317:419 */           throw new AssertionFailure("Cannot find synthProp: " + referencedEntity.getEntityName() + "." + referencedPropertyName);
/* 318:    */         }
/* 319:423 */         linkJoinColumnWithValueOverridingNameIfImplicit(referencedEntity, synthProp.getColumnIterator(), columns, value);
/* 320:    */       }
/* 321:429 */       else if (0 == fkEnum)
/* 322:    */       {
/* 323:431 */         if (columns.length != referencedEntity.getIdentifier().getColumnSpan()) {
/* 324:432 */           throw new AnnotationException("A Foreign key refering " + referencedEntity.getEntityName() + " from " + associatedClass.getEntityName() + " has the wrong number of column. should be " + referencedEntity.getIdentifier().getColumnSpan());
/* 325:    */         }
/* 326:439 */         linkJoinColumnWithValueOverridingNameIfImplicit(referencedEntity, referencedEntity.getIdentifier().getColumnIterator(), columns, value);
/* 327:    */       }
/* 328:    */       else
/* 329:    */       {
/* 330:448 */         Iterator idColItr = referencedEntity.getKey().getColumnIterator();
/* 331:    */         
/* 332:450 */         Table table = referencedEntity.getTable();
/* 333:451 */         if (!idColItr.hasNext()) {
/* 334:452 */           LOG.debug("No column in the identifier!");
/* 335:    */         }
/* 336:454 */         while (idColItr.hasNext())
/* 337:    */         {
/* 338:455 */           boolean match = false;
/* 339:    */           
/* 340:457 */           Column col = (Column)idColItr.next();
/* 341:458 */           for (Ejb3JoinColumn joinCol : columns)
/* 342:    */           {
/* 343:459 */             String referencedColumn = joinCol.getReferencedColumn();
/* 344:460 */             referencedColumn = mappings.getPhysicalColumnName(referencedColumn, table);
/* 345:462 */             if (referencedColumn.equalsIgnoreCase(col.getQuotedName()))
/* 346:    */             {
/* 347:464 */               if (joinCol.isNameDeferred()) {
/* 348:465 */                 joinCol.linkValueUsingDefaultColumnNaming(col, referencedEntity, value);
/* 349:    */               } else {
/* 350:470 */                 joinCol.linkWithValue(value);
/* 351:    */               }
/* 352:472 */               joinCol.overrideFromReferencedColumnIfNecessary(col);
/* 353:473 */               match = true;
/* 354:474 */               break;
/* 355:    */             }
/* 356:    */           }
/* 357:477 */           if (!match) {
/* 358:478 */             throw new AnnotationException("Column name " + col.getName() + " of " + referencedEntity.getEntityName() + " not found in JoinColumns.referencedColumnName");
/* 359:    */           }
/* 360:    */         }
/* 361:    */       }
/* 362:    */     }
/* 363:487 */     value.createForeignKey();
/* 364:488 */     if (unique) {
/* 365:489 */       createUniqueConstraint(value);
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   public static void linkJoinColumnWithValueOverridingNameIfImplicit(PersistentClass referencedEntity, Iterator columnIterator, Ejb3JoinColumn[] columns, SimpleValue value)
/* 370:    */   {
/* 371:498 */     for (Ejb3JoinColumn joinCol : columns)
/* 372:    */     {
/* 373:499 */       Column synthCol = (Column)columnIterator.next();
/* 374:500 */       if (joinCol.isNameDeferred())
/* 375:    */       {
/* 376:502 */         joinCol.linkValueUsingDefaultColumnNaming(synthCol, referencedEntity, value);
/* 377:    */       }
/* 378:    */       else
/* 379:    */       {
/* 380:505 */         joinCol.linkWithValue(value);
/* 381:506 */         joinCol.overrideFromReferencedColumnIfNecessary(synthCol);
/* 382:    */       }
/* 383:    */     }
/* 384:    */   }
/* 385:    */   
/* 386:    */   public static void createUniqueConstraint(Value value)
/* 387:    */   {
/* 388:512 */     Iterator iter = value.getColumnIterator();
/* 389:513 */     ArrayList cols = new ArrayList();
/* 390:514 */     while (iter.hasNext()) {
/* 391:515 */       cols.add(iter.next());
/* 392:    */     }
/* 393:517 */     value.getTable().createUniqueKey(cols);
/* 394:    */   }
/* 395:    */   
/* 396:    */   public static void addIndexes(Table hibTable, Index[] indexes, Mappings mappings)
/* 397:    */   {
/* 398:521 */     for (Index index : indexes) {
/* 399:523 */       mappings.addSecondPass(new IndexOrUniqueKeySecondPass(hibTable, index.name(), index.columnNames(), mappings));
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   @Deprecated
/* 404:    */   public static List<String[]> buildUniqueConstraints(UniqueConstraint[] constraintsArray)
/* 405:    */   {
/* 406:535 */     List<String[]> result = new ArrayList();
/* 407:536 */     if (constraintsArray.length != 0) {
/* 408:537 */       for (UniqueConstraint uc : constraintsArray) {
/* 409:538 */         result.add(uc.columnNames());
/* 410:    */       }
/* 411:    */     }
/* 412:541 */     return result;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public static List<UniqueConstraintHolder> buildUniqueConstraintHolders(UniqueConstraint[] annotations)
/* 416:    */   {
/* 417:    */     List<UniqueConstraintHolder> result;
/* 418:    */     List<UniqueConstraintHolder> result;
/* 419:554 */     if ((annotations == null) || (annotations.length == 0))
/* 420:    */     {
/* 421:555 */       result = Collections.emptyList();
/* 422:    */     }
/* 423:    */     else
/* 424:    */     {
/* 425:558 */       result = new ArrayList(CollectionHelper.determineProperSizing(annotations.length));
/* 426:559 */       for (UniqueConstraint uc : annotations) {
/* 427:560 */         result.add(new UniqueConstraintHolder().setName(uc.name()).setColumns(uc.columnNames()));
/* 428:    */       }
/* 429:    */     }
/* 430:567 */     return result;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setDefaultName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName)
/* 434:    */   {
/* 435:574 */     this.ownerEntity = ownerEntity;
/* 436:575 */     this.ownerEntityTable = ownerEntityTable;
/* 437:576 */     this.associatedEntity = associatedEntity;
/* 438:577 */     this.associatedEntityTable = associatedEntityTable;
/* 439:578 */     this.propertyName = propertyName;
/* 440:579 */     this.name = null;
/* 441:    */   }
/* 442:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.TableBinder
 * JD-Core Version:    0.7.0.1
 */