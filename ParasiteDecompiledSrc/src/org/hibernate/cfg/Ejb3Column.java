/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.AnnotationException;
/*   5:    */ import org.hibernate.AssertionFailure;
/*   6:    */ import org.hibernate.annotations.ColumnTransformer;
/*   7:    */ import org.hibernate.annotations.ColumnTransformers;
/*   8:    */ import org.hibernate.annotations.Index;
/*   9:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  10:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  11:    */ import org.hibernate.cfg.annotations.Nullability;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.StringHelper;
/*  14:    */ import org.hibernate.mapping.Join;
/*  15:    */ import org.hibernate.mapping.SimpleValue;
/*  16:    */ import org.hibernate.mapping.Table;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class Ejb3Column
/*  20:    */ {
/*  21: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Ejb3Column.class.getName());
/*  22:    */   private org.hibernate.mapping.Column mappingColumn;
/*  23: 56 */   private boolean insertable = true;
/*  24: 57 */   private boolean updatable = true;
/*  25:    */   private String secondaryTableName;
/*  26:    */   protected Map<String, Join> joins;
/*  27:    */   protected PropertyHolder propertyHolder;
/*  28:    */   private Mappings mappings;
/*  29:    */   private boolean isImplicit;
/*  30:    */   public static final int DEFAULT_COLUMN_LENGTH = 255;
/*  31:    */   public String sqlType;
/*  32: 65 */   private int length = 255;
/*  33:    */   private int precision;
/*  34:    */   private int scale;
/*  35:    */   private String logicalColumnName;
/*  36:    */   private String propertyName;
/*  37:    */   private boolean unique;
/*  38: 71 */   private boolean nullable = true;
/*  39:    */   private String formulaString;
/*  40:    */   private org.hibernate.mapping.Formula formula;
/*  41:    */   private Table table;
/*  42:    */   private String readExpression;
/*  43:    */   private String writeExpression;
/*  44:    */   
/*  45:    */   public void setTable(Table table)
/*  46:    */   {
/*  47: 79 */     this.table = table;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getLogicalColumnName()
/*  51:    */   {
/*  52: 83 */     return this.logicalColumnName;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getSqlType()
/*  56:    */   {
/*  57: 87 */     return this.sqlType;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getLength()
/*  61:    */   {
/*  62: 91 */     return this.length;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getPrecision()
/*  66:    */   {
/*  67: 95 */     return this.precision;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getScale()
/*  71:    */   {
/*  72: 99 */     return this.scale;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isUnique()
/*  76:    */   {
/*  77:103 */     return this.unique;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isFormula()
/*  81:    */   {
/*  82:107 */     return StringHelper.isNotEmpty(this.formulaString);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getFormulaString()
/*  86:    */   {
/*  87:111 */     return this.formulaString;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getSecondaryTableName()
/*  91:    */   {
/*  92:115 */     return this.secondaryTableName;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setFormula(String formula)
/*  96:    */   {
/*  97:119 */     this.formulaString = formula;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isImplicit()
/* 101:    */   {
/* 102:123 */     return this.isImplicit;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setInsertable(boolean insertable)
/* 106:    */   {
/* 107:127 */     this.insertable = insertable;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setUpdatable(boolean updatable)
/* 111:    */   {
/* 112:131 */     this.updatable = updatable;
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected Mappings getMappings()
/* 116:    */   {
/* 117:135 */     return this.mappings;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setMappings(Mappings mappings)
/* 121:    */   {
/* 122:139 */     this.mappings = mappings;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setImplicit(boolean implicit)
/* 126:    */   {
/* 127:143 */     this.isImplicit = implicit;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setSqlType(String sqlType)
/* 131:    */   {
/* 132:147 */     this.sqlType = sqlType;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setLength(int length)
/* 136:    */   {
/* 137:151 */     this.length = length;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setPrecision(int precision)
/* 141:    */   {
/* 142:155 */     this.precision = precision;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setScale(int scale)
/* 146:    */   {
/* 147:159 */     this.scale = scale;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setLogicalColumnName(String logicalColumnName)
/* 151:    */   {
/* 152:163 */     this.logicalColumnName = logicalColumnName;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPropertyName(String propertyName)
/* 156:    */   {
/* 157:167 */     this.propertyName = propertyName;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getPropertyName()
/* 161:    */   {
/* 162:171 */     return this.propertyName;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setUnique(boolean unique)
/* 166:    */   {
/* 167:175 */     this.unique = unique;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean isNullable()
/* 171:    */   {
/* 172:179 */     return this.mappingColumn.isNullable();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void bind()
/* 176:    */   {
/* 177:186 */     if (StringHelper.isNotEmpty(this.formulaString))
/* 178:    */     {
/* 179:187 */       LOG.debugf("Binding formula %s", this.formulaString);
/* 180:188 */       this.formula = new org.hibernate.mapping.Formula();
/* 181:189 */       this.formula.setFormula(this.formulaString);
/* 182:    */     }
/* 183:    */     else
/* 184:    */     {
/* 185:192 */       initMappingColumn(this.logicalColumnName, this.propertyName, this.length, this.precision, this.scale, this.nullable, this.sqlType, this.unique, true);
/* 186:195 */       if (LOG.isDebugEnabled()) {
/* 187:196 */         LOG.debugf("Binding column: %s", toString());
/* 188:    */       }
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected void initMappingColumn(String columnName, String propertyName, int length, int precision, int scale, boolean nullable, String sqlType, boolean unique, boolean applyNamingStrategy)
/* 193:    */   {
/* 194:211 */     if (StringHelper.isNotEmpty(this.formulaString))
/* 195:    */     {
/* 196:212 */       this.formula = new org.hibernate.mapping.Formula();
/* 197:213 */       this.formula.setFormula(this.formulaString);
/* 198:    */     }
/* 199:    */     else
/* 200:    */     {
/* 201:216 */       this.mappingColumn = new org.hibernate.mapping.Column();
/* 202:217 */       redefineColumnName(columnName, propertyName, applyNamingStrategy);
/* 203:218 */       this.mappingColumn.setLength(length);
/* 204:219 */       if (precision > 0)
/* 205:    */       {
/* 206:220 */         this.mappingColumn.setPrecision(precision);
/* 207:221 */         this.mappingColumn.setScale(scale);
/* 208:    */       }
/* 209:223 */       this.mappingColumn.setNullable(nullable);
/* 210:224 */       this.mappingColumn.setSqlType(sqlType);
/* 211:225 */       this.mappingColumn.setUnique(unique);
/* 212:227 */       if ((this.writeExpression != null) && (!this.writeExpression.matches("[^?]*\\?[^?]*"))) {
/* 213:228 */         throw new AnnotationException("@WriteExpression must contain exactly one value placeholder ('?') character: property [" + propertyName + "] and column [" + this.logicalColumnName + "]");
/* 214:    */       }
/* 215:233 */       if (this.readExpression != null) {
/* 216:234 */         this.mappingColumn.setCustomRead(this.readExpression);
/* 217:    */       }
/* 218:236 */       if (this.writeExpression != null) {
/* 219:237 */         this.mappingColumn.setCustomWrite(this.writeExpression);
/* 220:    */       }
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   public boolean isNameDeferred()
/* 225:    */   {
/* 226:243 */     return (this.mappingColumn == null) || (StringHelper.isEmpty(this.mappingColumn.getName()));
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void redefineColumnName(String columnName, String propertyName, boolean applyNamingStrategy)
/* 230:    */   {
/* 231:247 */     if (applyNamingStrategy)
/* 232:    */     {
/* 233:248 */       if (StringHelper.isEmpty(columnName))
/* 234:    */       {
/* 235:249 */         if (propertyName != null) {
/* 236:250 */           this.mappingColumn.setName(this.mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(this.mappings.getNamingStrategy().propertyToColumnName(propertyName)));
/* 237:    */         }
/* 238:    */       }
/* 239:    */       else
/* 240:    */       {
/* 241:259 */         columnName = this.mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(columnName);
/* 242:260 */         columnName = this.mappings.getNamingStrategy().columnName(columnName);
/* 243:261 */         columnName = this.mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(columnName);
/* 244:262 */         this.mappingColumn.setName(columnName);
/* 245:    */       }
/* 246:    */     }
/* 247:266 */     else if (StringHelper.isNotEmpty(columnName)) {
/* 248:267 */       this.mappingColumn.setName(this.mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(columnName));
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public String getName()
/* 253:    */   {
/* 254:273 */     return this.mappingColumn.getName();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public org.hibernate.mapping.Column getMappingColumn()
/* 258:    */   {
/* 259:277 */     return this.mappingColumn;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public boolean isInsertable()
/* 263:    */   {
/* 264:281 */     return this.insertable;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isUpdatable()
/* 268:    */   {
/* 269:285 */     return this.updatable;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setNullable(boolean nullable)
/* 273:    */   {
/* 274:289 */     if (this.mappingColumn != null) {
/* 275:290 */       this.mappingColumn.setNullable(nullable);
/* 276:    */     } else {
/* 277:293 */       this.nullable = nullable;
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setJoins(Map<String, Join> joins)
/* 282:    */   {
/* 283:298 */     this.joins = joins;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public PropertyHolder getPropertyHolder()
/* 287:    */   {
/* 288:302 */     return this.propertyHolder;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setPropertyHolder(PropertyHolder propertyHolder)
/* 292:    */   {
/* 293:306 */     this.propertyHolder = propertyHolder;
/* 294:    */   }
/* 295:    */   
/* 296:    */   protected void setMappingColumn(org.hibernate.mapping.Column mappingColumn)
/* 297:    */   {
/* 298:310 */     this.mappingColumn = mappingColumn;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void linkWithValue(SimpleValue value)
/* 302:    */   {
/* 303:314 */     if (this.formula != null)
/* 304:    */     {
/* 305:315 */       value.addFormula(this.formula);
/* 306:    */     }
/* 307:    */     else
/* 308:    */     {
/* 309:318 */       getMappingColumn().setValue(value);
/* 310:319 */       value.addColumn(getMappingColumn());
/* 311:320 */       value.getTable().addColumn(getMappingColumn());
/* 312:321 */       addColumnBinding(value);
/* 313:322 */       this.table = value.getTable();
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   protected void addColumnBinding(SimpleValue value)
/* 318:    */   {
/* 319:327 */     String logicalColumnName = this.mappings.getNamingStrategy().logicalColumnName(this.logicalColumnName, this.propertyName);
/* 320:    */     
/* 321:329 */     this.mappings.addColumnBinding(logicalColumnName, getMappingColumn(), value.getTable());
/* 322:    */   }
/* 323:    */   
/* 324:    */   public Table getTable()
/* 325:    */   {
/* 326:340 */     if (this.table != null) {
/* 327:340 */       return this.table;
/* 328:    */     }
/* 329:341 */     if (isSecondary()) {
/* 330:342 */       return getJoin().getTable();
/* 331:    */     }
/* 332:345 */     return this.propertyHolder.getTable();
/* 333:    */   }
/* 334:    */   
/* 335:    */   public boolean isSecondary()
/* 336:    */   {
/* 337:350 */     if (this.propertyHolder == null) {
/* 338:351 */       throw new AssertionFailure("Should not call getTable() on column wo persistent class defined");
/* 339:    */     }
/* 340:353 */     if (StringHelper.isNotEmpty(this.secondaryTableName)) {
/* 341:354 */       return true;
/* 342:    */     }
/* 343:357 */     return false;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public Join getJoin()
/* 347:    */   {
/* 348:361 */     Join join = (Join)this.joins.get(this.secondaryTableName);
/* 349:362 */     if (join == null) {
/* 350:363 */       throw new AnnotationException("Cannot find the expected secondary table: no " + this.secondaryTableName + " available for " + this.propertyHolder.getClassName());
/* 351:    */     }
/* 352:369 */     return join;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void forceNotNull()
/* 356:    */   {
/* 357:374 */     this.mappingColumn.setNullable(false);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setSecondaryTableName(String secondaryTableName)
/* 361:    */   {
/* 362:378 */     if ("``".equals(secondaryTableName)) {
/* 363:379 */       this.secondaryTableName = "";
/* 364:    */     } else {
/* 365:382 */       this.secondaryTableName = secondaryTableName;
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   public static Ejb3Column[] buildColumnFromAnnotation(javax.persistence.Column[] anns, org.hibernate.annotations.Formula formulaAnn, Nullability nullability, PropertyHolder propertyHolder, PropertyData inferredData, Map<String, Join> secondaryTables, Mappings mappings)
/* 370:    */   {
/* 371:394 */     return buildColumnFromAnnotation(anns, formulaAnn, nullability, propertyHolder, inferredData, null, secondaryTables, mappings);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public static Ejb3Column[] buildColumnFromAnnotation(javax.persistence.Column[] anns, org.hibernate.annotations.Formula formulaAnn, Nullability nullability, PropertyHolder propertyHolder, PropertyData inferredData, String suffixForDefaultColumnName, Map<String, Join> secondaryTables, Mappings mappings)
/* 375:    */   {
/* 376:    */     Ejb3Column[] columns;
/* 377:    */     Ejb3Column[] columns;
/* 378:408 */     if (formulaAnn != null)
/* 379:    */     {
/* 380:409 */       Ejb3Column formulaColumn = new Ejb3Column();
/* 381:410 */       formulaColumn.setFormula(formulaAnn.value());
/* 382:411 */       formulaColumn.setImplicit(false);
/* 383:412 */       formulaColumn.setMappings(mappings);
/* 384:413 */       formulaColumn.setPropertyHolder(propertyHolder);
/* 385:414 */       formulaColumn.bind();
/* 386:415 */       columns = new Ejb3Column[] { formulaColumn };
/* 387:    */     }
/* 388:    */     else
/* 389:    */     {
/* 390:418 */       javax.persistence.Column[] actualCols = anns;
/* 391:419 */       javax.persistence.Column[] overriddenCols = propertyHolder.getOverriddenColumn(StringHelper.qualify(propertyHolder.getPath(), inferredData.getPropertyName()));
/* 392:422 */       if (overriddenCols != null)
/* 393:    */       {
/* 394:424 */         if ((anns != null) && (overriddenCols.length != anns.length)) {
/* 395:425 */           throw new AnnotationException("AttributeOverride.column() should override all columns for now");
/* 396:    */         }
/* 397:427 */         actualCols = overriddenCols.length == 0 ? null : overriddenCols;
/* 398:428 */         LOG.debugf("Column(s) overridden for property %s", inferredData.getPropertyName());
/* 399:    */       }
/* 400:    */       Ejb3Column[] columns;
/* 401:430 */       if (actualCols == null)
/* 402:    */       {
/* 403:431 */         columns = buildImplicitColumn(inferredData, suffixForDefaultColumnName, secondaryTables, propertyHolder, nullability, mappings);
/* 404:    */       }
/* 405:    */       else
/* 406:    */       {
/* 407:441 */         int length = actualCols.length;
/* 408:442 */         columns = new Ejb3Column[length];
/* 409:443 */         for (int index = 0; index < length; index++)
/* 410:    */         {
/* 411:444 */           ObjectNameNormalizer nameNormalizer = mappings.getObjectNameNormalizer();
/* 412:445 */           javax.persistence.Column col = actualCols[index];
/* 413:446 */           String sqlType = col.columnDefinition().equals("") ? null : nameNormalizer.normalizeIdentifierQuoting(col.columnDefinition());
/* 414:    */           
/* 415:    */ 
/* 416:449 */           String tableName = !StringHelper.isEmpty(col.table()) ? nameNormalizer.normalizeIdentifierQuoting(mappings.getNamingStrategy().tableName(col.table())) : "";
/* 417:    */           
/* 418:    */ 
/* 419:452 */           String columnName = nameNormalizer.normalizeIdentifierQuoting(col.name());
/* 420:453 */           Ejb3Column column = new Ejb3Column();
/* 421:454 */           column.setImplicit(false);
/* 422:455 */           column.setSqlType(sqlType);
/* 423:456 */           column.setLength(col.length());
/* 424:457 */           column.setPrecision(col.precision());
/* 425:458 */           column.setScale(col.scale());
/* 426:459 */           if ((StringHelper.isEmpty(columnName)) && (!StringHelper.isEmpty(suffixForDefaultColumnName))) {
/* 427:460 */             column.setLogicalColumnName(inferredData.getPropertyName() + suffixForDefaultColumnName);
/* 428:    */           } else {
/* 429:463 */             column.setLogicalColumnName(columnName);
/* 430:    */           }
/* 431:466 */           column.setPropertyName(BinderHelper.getRelativePath(propertyHolder, inferredData.getPropertyName()));
/* 432:    */           
/* 433:    */ 
/* 434:469 */           column.setNullable(col.nullable());
/* 435:    */           
/* 436:    */ 
/* 437:472 */           column.setUnique(col.unique());
/* 438:473 */           column.setInsertable(col.insertable());
/* 439:474 */           column.setUpdatable(col.updatable());
/* 440:475 */           column.setSecondaryTableName(tableName);
/* 441:476 */           column.setPropertyHolder(propertyHolder);
/* 442:477 */           column.setJoins(secondaryTables);
/* 443:478 */           column.setMappings(mappings);
/* 444:479 */           column.extractDataFromPropertyData(inferredData);
/* 445:480 */           column.bind();
/* 446:481 */           columns[index] = column;
/* 447:    */         }
/* 448:    */       }
/* 449:    */     }
/* 450:485 */     return columns;
/* 451:    */   }
/* 452:    */   
/* 453:    */   private void extractDataFromPropertyData(PropertyData inferredData)
/* 454:    */   {
/* 455:490 */     if (inferredData != null)
/* 456:    */     {
/* 457:491 */       XProperty property = inferredData.getProperty();
/* 458:492 */       if (property != null)
/* 459:    */       {
/* 460:493 */         processExpression((ColumnTransformer)property.getAnnotation(ColumnTransformer.class));
/* 461:494 */         ColumnTransformers annotations = (ColumnTransformers)property.getAnnotation(ColumnTransformers.class);
/* 462:495 */         if (annotations != null) {
/* 463:496 */           for (ColumnTransformer annotation : annotations.value()) {
/* 464:497 */             processExpression(annotation);
/* 465:    */           }
/* 466:    */         }
/* 467:    */       }
/* 468:    */     }
/* 469:    */   }
/* 470:    */   
/* 471:    */   private void processExpression(ColumnTransformer annotation)
/* 472:    */   {
/* 473:505 */     String nonNullLogicalColumnName = this.logicalColumnName != null ? this.logicalColumnName : "";
/* 474:506 */     if ((annotation != null) && ((StringHelper.isEmpty(annotation.forColumn())) || (annotation.forColumn().equals(nonNullLogicalColumnName))))
/* 475:    */     {
/* 476:509 */       this.readExpression = annotation.read();
/* 477:510 */       if (StringHelper.isEmpty(this.readExpression)) {
/* 478:511 */         this.readExpression = null;
/* 479:    */       }
/* 480:513 */       this.writeExpression = annotation.write();
/* 481:514 */       if (StringHelper.isEmpty(this.writeExpression)) {
/* 482:515 */         this.writeExpression = null;
/* 483:    */       }
/* 484:    */     }
/* 485:    */   }
/* 486:    */   
/* 487:    */   private static Ejb3Column[] buildImplicitColumn(PropertyData inferredData, String suffixForDefaultColumnName, Map<String, Join> secondaryTables, PropertyHolder propertyHolder, Nullability nullability, Mappings mappings)
/* 488:    */   {
/* 489:527 */     Ejb3Column column = new Ejb3Column();
/* 490:528 */     Ejb3Column[] columns = new Ejb3Column[1];
/* 491:529 */     columns[0] = column;
/* 492:532 */     if ((nullability != Nullability.FORCED_NULL) && (inferredData.getClassOrElement().isPrimitive()) && (!inferredData.getProperty().isArray())) {
/* 493:535 */       column.setNullable(false);
/* 494:    */     }
/* 495:537 */     column.setLength(255);
/* 496:538 */     String propertyName = inferredData.getPropertyName();
/* 497:539 */     column.setPropertyName(BinderHelper.getRelativePath(propertyHolder, propertyName));
/* 498:    */     
/* 499:    */ 
/* 500:542 */     column.setPropertyHolder(propertyHolder);
/* 501:543 */     column.setJoins(secondaryTables);
/* 502:544 */     column.setMappings(mappings);
/* 503:547 */     if (!StringHelper.isEmpty(suffixForDefaultColumnName))
/* 504:    */     {
/* 505:548 */       column.setLogicalColumnName(propertyName + suffixForDefaultColumnName);
/* 506:549 */       column.setImplicit(false);
/* 507:    */     }
/* 508:    */     else
/* 509:    */     {
/* 510:552 */       column.setImplicit(true);
/* 511:    */     }
/* 512:554 */     column.extractDataFromPropertyData(inferredData);
/* 513:555 */     column.bind();
/* 514:556 */     return columns;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public static void checkPropertyConsistency(Ejb3Column[] columns, String propertyName)
/* 518:    */   {
/* 519:560 */     int nbrOfColumns = columns.length;
/* 520:562 */     if (nbrOfColumns > 1) {
/* 521:563 */       for (int currentIndex = 1; currentIndex < nbrOfColumns; currentIndex++) {
/* 522:565 */         if ((!columns[currentIndex].isFormula()) && (!columns[(currentIndex - 1)].isFormula()))
/* 523:    */         {
/* 524:569 */           if (columns[currentIndex].isInsertable() != columns[(currentIndex - 1)].isInsertable()) {
/* 525:570 */             throw new AnnotationException("Mixing insertable and non insertable columns in a property is not allowed: " + propertyName);
/* 526:    */           }
/* 527:574 */           if (columns[currentIndex].isNullable() != columns[(currentIndex - 1)].isNullable()) {
/* 528:575 */             throw new AnnotationException("Mixing nullable and non nullable columns in a property is not allowed: " + propertyName);
/* 529:    */           }
/* 530:579 */           if (columns[currentIndex].isUpdatable() != columns[(currentIndex - 1)].isUpdatable()) {
/* 531:580 */             throw new AnnotationException("Mixing updatable and non updatable columns in a property is not allowed: " + propertyName);
/* 532:    */           }
/* 533:584 */           if (!columns[currentIndex].getTable().equals(columns[(currentIndex - 1)].getTable())) {
/* 534:585 */             throw new AnnotationException("Mixing different tables in a property is not allowed: " + propertyName);
/* 535:    */           }
/* 536:    */         }
/* 537:    */       }
/* 538:    */     }
/* 539:    */   }
/* 540:    */   
/* 541:    */   public void addIndex(Index index, boolean inSecondPass)
/* 542:    */   {
/* 543:595 */     if (index == null) {
/* 544:595 */       return;
/* 545:    */     }
/* 546:596 */     String indexName = index.name();
/* 547:597 */     addIndex(indexName, inSecondPass);
/* 548:    */   }
/* 549:    */   
/* 550:    */   void addIndex(String indexName, boolean inSecondPass)
/* 551:    */   {
/* 552:601 */     IndexOrUniqueKeySecondPass secondPass = new IndexOrUniqueKeySecondPass(indexName, this, this.mappings, false);
/* 553:602 */     if (inSecondPass) {
/* 554:603 */       secondPass.doSecondPass(this.mappings.getClasses());
/* 555:    */     } else {
/* 556:606 */       this.mappings.addSecondPass(secondPass);
/* 557:    */     }
/* 558:    */   }
/* 559:    */   
/* 560:    */   void addUniqueKey(String uniqueKeyName, boolean inSecondPass)
/* 561:    */   {
/* 562:613 */     IndexOrUniqueKeySecondPass secondPass = new IndexOrUniqueKeySecondPass(uniqueKeyName, this, this.mappings, true);
/* 563:614 */     if (inSecondPass) {
/* 564:615 */       secondPass.doSecondPass(this.mappings.getClasses());
/* 565:    */     } else {
/* 566:618 */       this.mappings.addSecondPass(secondPass);
/* 567:    */     }
/* 568:    */   }
/* 569:    */   
/* 570:    */   public String toString()
/* 571:    */   {
/* 572:626 */     StringBuilder sb = new StringBuilder();
/* 573:627 */     sb.append("Ejb3Column");
/* 574:628 */     sb.append("{table=").append(getTable());
/* 575:629 */     sb.append(", mappingColumn=").append(this.mappingColumn.getName());
/* 576:630 */     sb.append(", insertable=").append(this.insertable);
/* 577:631 */     sb.append(", updatable=").append(this.updatable);
/* 578:632 */     sb.append(", unique=").append(this.unique);
/* 579:633 */     sb.append('}');
/* 580:634 */     return sb.toString();
/* 581:    */   }
/* 582:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.Ejb3Column
 * JD-Core Version:    0.7.0.1
 */