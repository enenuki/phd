/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.LinkedHashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.MappingException;
/*  15:    */ import org.hibernate.dialect.Dialect;
/*  16:    */ import org.hibernate.engine.spi.Mapping;
/*  17:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  18:    */ import org.hibernate.tool.hbm2ddl.ColumnMetadata;
/*  19:    */ import org.hibernate.tool.hbm2ddl.TableMetadata;
/*  20:    */ 
/*  21:    */ public class Table
/*  22:    */   implements RelationalModel, Serializable
/*  23:    */ {
/*  24:    */   private String name;
/*  25:    */   private String schema;
/*  26:    */   private String catalog;
/*  27: 54 */   private Map columns = new LinkedHashMap();
/*  28:    */   private KeyValue idValue;
/*  29:    */   private PrimaryKey primaryKey;
/*  30: 57 */   private Map indexes = new HashMap();
/*  31: 58 */   private Map foreignKeys = new HashMap();
/*  32: 59 */   private Map uniqueKeys = new HashMap();
/*  33:    */   private final int uniqueInteger;
/*  34:    */   private boolean quoted;
/*  35:    */   private boolean schemaQuoted;
/*  36: 63 */   private static int tableCounter = 0;
/*  37: 64 */   private List checkConstraints = new ArrayList();
/*  38:    */   private String rowId;
/*  39:    */   private String subselect;
/*  40:    */   private boolean isAbstract;
/*  41: 68 */   private boolean hasDenormalizedTables = false;
/*  42:    */   private String comment;
/*  43:    */   
/*  44:    */   static class ForeignKeyKey
/*  45:    */     implements Serializable
/*  46:    */   {
/*  47:    */     String referencedClassName;
/*  48:    */     List columns;
/*  49:    */     List referencedColumns;
/*  50:    */     
/*  51:    */     ForeignKeyKey(List columns, String referencedClassName, List referencedColumns)
/*  52:    */     {
/*  53: 77 */       this.referencedClassName = referencedClassName;
/*  54: 78 */       this.columns = new ArrayList();
/*  55: 79 */       this.columns.addAll(columns);
/*  56: 80 */       if (referencedColumns != null)
/*  57:    */       {
/*  58: 81 */         this.referencedColumns = new ArrayList();
/*  59: 82 */         this.referencedColumns.addAll(referencedColumns);
/*  60:    */       }
/*  61:    */       else
/*  62:    */       {
/*  63: 85 */         this.referencedColumns = CollectionHelper.EMPTY_LIST;
/*  64:    */       }
/*  65:    */     }
/*  66:    */     
/*  67:    */     public int hashCode()
/*  68:    */     {
/*  69: 90 */       return this.columns.hashCode() + this.referencedColumns.hashCode();
/*  70:    */     }
/*  71:    */     
/*  72:    */     public boolean equals(Object other)
/*  73:    */     {
/*  74: 94 */       ForeignKeyKey fkk = (ForeignKeyKey)other;
/*  75: 95 */       return (fkk.columns.equals(this.columns)) && (fkk.referencedClassName.equals(this.referencedClassName)) && (fkk.referencedColumns.equals(this.referencedColumns));
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Table()
/*  80:    */   {
/*  81:102 */     this.uniqueInteger = (tableCounter++);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Table(String name)
/*  85:    */   {
/*  86:106 */     this();
/*  87:107 */     setName(name);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getQualifiedName(Dialect dialect, String defaultCatalog, String defaultSchema)
/*  91:    */   {
/*  92:111 */     if (this.subselect != null) {
/*  93:112 */       return "( " + this.subselect + " )";
/*  94:    */     }
/*  95:114 */     String quotedName = getQuotedName(dialect);
/*  96:115 */     String usedSchema = this.schema == null ? defaultSchema : getQuotedSchema(dialect);
/*  97:    */     
/*  98:    */ 
/*  99:118 */     String usedCatalog = this.catalog == null ? defaultCatalog : this.catalog;
/* 100:    */     
/* 101:    */ 
/* 102:121 */     return qualify(usedCatalog, usedSchema, quotedName);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static String qualify(String catalog, String schema, String table)
/* 106:    */   {
/* 107:125 */     StringBuffer qualifiedName = new StringBuffer();
/* 108:126 */     if (catalog != null) {
/* 109:127 */       qualifiedName.append(catalog).append('.');
/* 110:    */     }
/* 111:129 */     if (schema != null) {
/* 112:130 */       qualifiedName.append(schema).append('.');
/* 113:    */     }
/* 114:132 */     return table;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getName()
/* 118:    */   {
/* 119:136 */     return this.name;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getQuotedName()
/* 123:    */   {
/* 124:143 */     return this.quoted ? "`" + this.name + "`" : this.name;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getQuotedName(Dialect dialect)
/* 128:    */   {
/* 129:149 */     return this.quoted ? dialect.openQuote() + this.name + dialect.closeQuote() : this.name;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getQuotedSchema()
/* 133:    */   {
/* 134:158 */     return this.schemaQuoted ? "`" + this.schema + "`" : this.schema;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getQuotedSchema(Dialect dialect)
/* 138:    */   {
/* 139:164 */     return this.schemaQuoted ? dialect.openQuote() + this.schema + dialect.closeQuote() : this.schema;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setName(String name)
/* 143:    */   {
/* 144:170 */     if (name.charAt(0) == '`')
/* 145:    */     {
/* 146:171 */       this.quoted = true;
/* 147:172 */       this.name = name.substring(1, name.length() - 1);
/* 148:    */     }
/* 149:    */     else
/* 150:    */     {
/* 151:175 */       this.name = name;
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Column getColumn(Column column)
/* 156:    */   {
/* 157:186 */     if (column == null) {
/* 158:187 */       return null;
/* 159:    */     }
/* 160:190 */     Column myColumn = (Column)this.columns.get(column.getCanonicalName());
/* 161:    */     
/* 162:192 */     return column.equals(myColumn) ? myColumn : null;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Column getColumn(int n)
/* 166:    */   {
/* 167:198 */     Iterator iter = this.columns.values().iterator();
/* 168:199 */     for (int i = 0; i < n - 1; i++) {
/* 169:200 */       iter.next();
/* 170:    */     }
/* 171:202 */     return (Column)iter.next();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void addColumn(Column column)
/* 175:    */   {
/* 176:206 */     Column old = getColumn(column);
/* 177:207 */     if (old == null)
/* 178:    */     {
/* 179:208 */       this.columns.put(column.getCanonicalName(), column);
/* 180:209 */       column.uniqueInteger = this.columns.size();
/* 181:    */     }
/* 182:    */     else
/* 183:    */     {
/* 184:212 */       column.uniqueInteger = old.uniqueInteger;
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getColumnSpan()
/* 189:    */   {
/* 190:217 */     return this.columns.size();
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Iterator getColumnIterator()
/* 194:    */   {
/* 195:221 */     return this.columns.values().iterator();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Iterator getIndexIterator()
/* 199:    */   {
/* 200:225 */     return this.indexes.values().iterator();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Iterator getForeignKeyIterator()
/* 204:    */   {
/* 205:229 */     return this.foreignKeys.values().iterator();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Iterator getUniqueKeyIterator()
/* 209:    */   {
/* 210:233 */     return getUniqueKeys().values().iterator();
/* 211:    */   }
/* 212:    */   
/* 213:    */   Map getUniqueKeys()
/* 214:    */   {
/* 215:237 */     if (this.uniqueKeys.size() > 1)
/* 216:    */     {
/* 217:241 */       Iterator it = this.uniqueKeys.entrySet().iterator();
/* 218:242 */       Map finalUniqueKeys = new HashMap(this.uniqueKeys.size());
/* 219:243 */       while (it.hasNext())
/* 220:    */       {
/* 221:244 */         Map.Entry entry = (Map.Entry)it.next();
/* 222:245 */         UniqueKey uk = (UniqueKey)entry.getValue();
/* 223:246 */         List columns = uk.getColumns();
/* 224:247 */         int size = finalUniqueKeys.size();
/* 225:248 */         boolean skip = false;
/* 226:249 */         Iterator tempUks = finalUniqueKeys.entrySet().iterator();
/* 227:250 */         while (tempUks.hasNext())
/* 228:    */         {
/* 229:251 */           UniqueKey currentUk = (UniqueKey)((Map.Entry)tempUks.next()).getValue();
/* 230:252 */           if ((currentUk.getColumns().containsAll(columns)) && (columns.containsAll(currentUk.getColumns())))
/* 231:    */           {
/* 232:254 */             skip = true;
/* 233:255 */             break;
/* 234:    */           }
/* 235:    */         }
/* 236:258 */         if (!skip) {
/* 237:258 */           finalUniqueKeys.put(entry.getKey(), uk);
/* 238:    */         }
/* 239:    */       }
/* 240:260 */       return finalUniqueKeys;
/* 241:    */     }
/* 242:263 */     return this.uniqueKeys;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void validateColumns(Dialect dialect, Mapping mapping, TableMetadata tableInfo)
/* 246:    */   {
/* 247:268 */     Iterator iter = getColumnIterator();
/* 248:269 */     while (iter.hasNext())
/* 249:    */     {
/* 250:270 */       Column col = (Column)iter.next();
/* 251:    */       
/* 252:272 */       ColumnMetadata columnInfo = tableInfo.getColumnMetadata(col.getName());
/* 253:274 */       if (columnInfo == null) {
/* 254:275 */         throw new HibernateException("Missing column: " + col.getName() + " in " + qualify(tableInfo.getCatalog(), tableInfo.getSchema(), tableInfo.getName()));
/* 255:    */       }
/* 256:278 */       boolean typesMatch = (col.getSqlType(dialect, mapping).toLowerCase().startsWith(columnInfo.getTypeName().toLowerCase())) || (columnInfo.getTypeCode() == col.getSqlTypeCode(mapping));
/* 257:281 */       if (!typesMatch) {
/* 258:282 */         throw new HibernateException("Wrong column type in " + qualify(tableInfo.getCatalog(), tableInfo.getSchema(), tableInfo.getName()) + " for column " + col.getName() + ". Found: " + columnInfo.getTypeName().toLowerCase() + ", expected: " + col.getSqlType(dialect, mapping));
/* 259:    */       }
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Iterator sqlAlterStrings(Dialect dialect, Mapping p, TableMetadata tableInfo, String defaultCatalog, String defaultSchema)
/* 264:    */     throws HibernateException
/* 265:    */   {
/* 266:299 */     StringBuffer root = new StringBuffer("alter table ").append(getQualifiedName(dialect, defaultCatalog, defaultSchema)).append(' ').append(dialect.getAddColumnString());
/* 267:    */     
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:304 */     Iterator iter = getColumnIterator();
/* 272:305 */     List results = new ArrayList();
/* 273:306 */     while (iter.hasNext())
/* 274:    */     {
/* 275:307 */       Column column = (Column)iter.next();
/* 276:    */       
/* 277:309 */       ColumnMetadata columnInfo = tableInfo.getColumnMetadata(column.getName());
/* 278:311 */       if (columnInfo == null)
/* 279:    */       {
/* 280:313 */         StringBuffer alter = new StringBuffer(root.toString()).append(' ').append(column.getQuotedName(dialect)).append(' ').append(column.getSqlType(dialect, p));
/* 281:    */         
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:319 */         String defaultValue = column.getDefaultValue();
/* 287:320 */         if (defaultValue != null) {
/* 288:321 */           alter.append(" default ").append(defaultValue);
/* 289:    */         }
/* 290:324 */         if (column.isNullable()) {
/* 291:325 */           alter.append(dialect.getNullColumnString());
/* 292:    */         } else {
/* 293:328 */           alter.append(" not null");
/* 294:    */         }
/* 295:331 */         boolean useUniqueConstraint = (column.isUnique()) && (dialect.supportsUnique()) && ((!column.isNullable()) || (dialect.supportsNotNullUnique()));
/* 296:334 */         if (useUniqueConstraint) {
/* 297:335 */           alter.append(" unique");
/* 298:    */         }
/* 299:338 */         if ((column.hasCheckConstraint()) && (dialect.supportsColumnCheck())) {
/* 300:339 */           alter.append(" check(").append(column.getCheckConstraint()).append(")");
/* 301:    */         }
/* 302:344 */         String columnComment = column.getComment();
/* 303:345 */         if (columnComment != null) {
/* 304:346 */           alter.append(dialect.getColumnComment(columnComment));
/* 305:    */         }
/* 306:349 */         results.add(alter.toString());
/* 307:    */       }
/* 308:    */     }
/* 309:354 */     return results.iterator();
/* 310:    */   }
/* 311:    */   
/* 312:    */   public boolean hasPrimaryKey()
/* 313:    */   {
/* 314:358 */     return getPrimaryKey() != null;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public String sqlTemporaryTableCreateString(Dialect dialect, Mapping mapping)
/* 318:    */     throws HibernateException
/* 319:    */   {
/* 320:362 */     StringBuffer buffer = new StringBuffer(dialect.getCreateTemporaryTableString()).append(' ').append(this.name).append(" (");
/* 321:    */     
/* 322:    */ 
/* 323:    */ 
/* 324:366 */     Iterator itr = getColumnIterator();
/* 325:367 */     while (itr.hasNext())
/* 326:    */     {
/* 327:368 */       Column column = (Column)itr.next();
/* 328:369 */       buffer.append(column.getQuotedName(dialect)).append(' ');
/* 329:370 */       buffer.append(column.getSqlType(dialect, mapping));
/* 330:371 */       if (column.isNullable()) {
/* 331:372 */         buffer.append(dialect.getNullColumnString());
/* 332:    */       } else {
/* 333:375 */         buffer.append(" not null");
/* 334:    */       }
/* 335:377 */       if (itr.hasNext()) {
/* 336:378 */         buffer.append(", ");
/* 337:    */       }
/* 338:    */     }
/* 339:381 */     buffer.append(") ");
/* 340:382 */     buffer.append(dialect.getCreateTemporaryTablePostfix());
/* 341:383 */     return buffer.toString();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public String sqlCreateString(Dialect dialect, Mapping p, String defaultCatalog, String defaultSchema)
/* 345:    */   {
/* 346:387 */     StringBuilder buf = new StringBuilder(hasPrimaryKey() ? dialect.getCreateTableString() : dialect.getCreateMultisetTableString()).append(' ').append(getQualifiedName(dialect, defaultCatalog, defaultSchema)).append(" (");
/* 347:    */     
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:392 */     boolean identityColumn = (this.idValue != null) && (this.idValue.isIdentityColumn(p.getIdentifierGeneratorFactory(), dialect));
/* 352:    */     
/* 353:    */ 
/* 354:395 */     String pkname = null;
/* 355:396 */     if ((hasPrimaryKey()) && (identityColumn)) {
/* 356:397 */       pkname = ((Column)getPrimaryKey().getColumnIterator().next()).getQuotedName(dialect);
/* 357:    */     }
/* 358:400 */     Iterator iter = getColumnIterator();
/* 359:401 */     while (iter.hasNext())
/* 360:    */     {
/* 361:402 */       Column col = (Column)iter.next();
/* 362:    */       
/* 363:404 */       buf.append(col.getQuotedName(dialect)).append(' ');
/* 364:407 */       if ((identityColumn) && (col.getQuotedName(dialect).equals(pkname)))
/* 365:    */       {
/* 366:409 */         if (dialect.hasDataTypeInIdentityColumn()) {
/* 367:410 */           buf.append(col.getSqlType(dialect, p));
/* 368:    */         }
/* 369:412 */         buf.append(' ').append(dialect.getIdentityColumnString(col.getSqlTypeCode(p)));
/* 370:    */       }
/* 371:    */       else
/* 372:    */       {
/* 373:417 */         buf.append(col.getSqlType(dialect, p));
/* 374:    */         
/* 375:419 */         String defaultValue = col.getDefaultValue();
/* 376:420 */         if (defaultValue != null) {
/* 377:421 */           buf.append(" default ").append(defaultValue);
/* 378:    */         }
/* 379:424 */         if (col.isNullable()) {
/* 380:425 */           buf.append(dialect.getNullColumnString());
/* 381:    */         } else {
/* 382:428 */           buf.append(" not null");
/* 383:    */         }
/* 384:    */       }
/* 385:433 */       boolean useUniqueConstraint = (col.isUnique()) && ((!col.isNullable()) || (dialect.supportsNotNullUnique()));
/* 386:435 */       if (useUniqueConstraint) {
/* 387:436 */         if (dialect.supportsUnique())
/* 388:    */         {
/* 389:437 */           buf.append(" unique");
/* 390:    */         }
/* 391:    */         else
/* 392:    */         {
/* 393:440 */           UniqueKey uk = getOrCreateUniqueKey(col.getQuotedName(dialect) + '_');
/* 394:441 */           uk.addColumn(col);
/* 395:    */         }
/* 396:    */       }
/* 397:445 */       if ((col.hasCheckConstraint()) && (dialect.supportsColumnCheck())) {
/* 398:446 */         buf.append(" check (").append(col.getCheckConstraint()).append(")");
/* 399:    */       }
/* 400:451 */       String columnComment = col.getComment();
/* 401:452 */       if (columnComment != null) {
/* 402:453 */         buf.append(dialect.getColumnComment(columnComment));
/* 403:    */       }
/* 404:456 */       if (iter.hasNext()) {
/* 405:457 */         buf.append(", ");
/* 406:    */       }
/* 407:    */     }
/* 408:461 */     if (hasPrimaryKey()) {
/* 409:462 */       buf.append(", ").append(getPrimaryKey().sqlConstraintString(dialect));
/* 410:    */     }
/* 411:466 */     if (dialect.supportsUniqueConstraintInCreateAlterTable())
/* 412:    */     {
/* 413:467 */       Iterator ukiter = getUniqueKeyIterator();
/* 414:468 */       while (ukiter.hasNext())
/* 415:    */       {
/* 416:469 */         UniqueKey uk = (UniqueKey)ukiter.next();
/* 417:470 */         String constraint = uk.sqlConstraintString(dialect);
/* 418:471 */         if (constraint != null) {
/* 419:472 */           buf.append(", ").append(constraint);
/* 420:    */         }
/* 421:    */       }
/* 422:    */     }
/* 423:482 */     if (dialect.supportsTableCheck())
/* 424:    */     {
/* 425:483 */       Iterator chiter = this.checkConstraints.iterator();
/* 426:484 */       while (chiter.hasNext()) {
/* 427:485 */         buf.append(", check (").append(chiter.next()).append(')');
/* 428:    */       }
/* 429:    */     }
/* 430:491 */     buf.append(')');
/* 431:493 */     if (this.comment != null) {
/* 432:494 */       buf.append(dialect.getTableComment(this.comment));
/* 433:    */     }
/* 434:497 */     return dialect.getTableTypeString();
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String sqlDropString(Dialect dialect, String defaultCatalog, String defaultSchema)
/* 438:    */   {
/* 439:501 */     StringBuffer buf = new StringBuffer("drop table ");
/* 440:502 */     if (dialect.supportsIfExistsBeforeTableName()) {
/* 441:503 */       buf.append("if exists ");
/* 442:    */     }
/* 443:505 */     buf.append(getQualifiedName(dialect, defaultCatalog, defaultSchema)).append(dialect.getCascadeConstraintsString());
/* 444:507 */     if (dialect.supportsIfExistsAfterTableName()) {
/* 445:508 */       buf.append(" if exists");
/* 446:    */     }
/* 447:510 */     return buf.toString();
/* 448:    */   }
/* 449:    */   
/* 450:    */   public PrimaryKey getPrimaryKey()
/* 451:    */   {
/* 452:514 */     return this.primaryKey;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setPrimaryKey(PrimaryKey primaryKey)
/* 456:    */   {
/* 457:518 */     this.primaryKey = primaryKey;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public Index getOrCreateIndex(String indexName)
/* 461:    */   {
/* 462:523 */     Index index = (Index)this.indexes.get(indexName);
/* 463:525 */     if (index == null)
/* 464:    */     {
/* 465:526 */       index = new Index();
/* 466:527 */       index.setName(indexName);
/* 467:528 */       index.setTable(this);
/* 468:529 */       this.indexes.put(indexName, index);
/* 469:    */     }
/* 470:532 */     return index;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public Index getIndex(String indexName)
/* 474:    */   {
/* 475:536 */     return (Index)this.indexes.get(indexName);
/* 476:    */   }
/* 477:    */   
/* 478:    */   public Index addIndex(Index index)
/* 479:    */   {
/* 480:540 */     Index current = (Index)this.indexes.get(index.getName());
/* 481:541 */     if (current != null) {
/* 482:542 */       throw new MappingException("Index " + index.getName() + " already exists!");
/* 483:    */     }
/* 484:544 */     this.indexes.put(index.getName(), index);
/* 485:545 */     return index;
/* 486:    */   }
/* 487:    */   
/* 488:    */   public UniqueKey addUniqueKey(UniqueKey uniqueKey)
/* 489:    */   {
/* 490:549 */     UniqueKey current = (UniqueKey)this.uniqueKeys.get(uniqueKey.getName());
/* 491:550 */     if (current != null) {
/* 492:551 */       throw new MappingException("UniqueKey " + uniqueKey.getName() + " already exists!");
/* 493:    */     }
/* 494:553 */     this.uniqueKeys.put(uniqueKey.getName(), uniqueKey);
/* 495:554 */     return uniqueKey;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public UniqueKey createUniqueKey(List keyColumns)
/* 499:    */   {
/* 500:558 */     String keyName = "UK" + uniqueColumnString(keyColumns.iterator());
/* 501:559 */     UniqueKey uk = getOrCreateUniqueKey(keyName);
/* 502:560 */     uk.addColumns(keyColumns.iterator());
/* 503:561 */     return uk;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public UniqueKey getUniqueKey(String keyName)
/* 507:    */   {
/* 508:565 */     return (UniqueKey)this.uniqueKeys.get(keyName);
/* 509:    */   }
/* 510:    */   
/* 511:    */   public UniqueKey getOrCreateUniqueKey(String keyName)
/* 512:    */   {
/* 513:569 */     UniqueKey uk = (UniqueKey)this.uniqueKeys.get(keyName);
/* 514:571 */     if (uk == null)
/* 515:    */     {
/* 516:572 */       uk = new UniqueKey();
/* 517:573 */       uk.setName(keyName);
/* 518:574 */       uk.setTable(this);
/* 519:575 */       this.uniqueKeys.put(keyName, uk);
/* 520:    */     }
/* 521:577 */     return uk;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public void createForeignKeys() {}
/* 525:    */   
/* 526:    */   public ForeignKey createForeignKey(String keyName, List keyColumns, String referencedEntityName)
/* 527:    */   {
/* 528:584 */     return createForeignKey(keyName, keyColumns, referencedEntityName, null);
/* 529:    */   }
/* 530:    */   
/* 531:    */   public ForeignKey createForeignKey(String keyName, List keyColumns, String referencedEntityName, List referencedColumns)
/* 532:    */   {
/* 533:589 */     Object key = new ForeignKeyKey(keyColumns, referencedEntityName, referencedColumns);
/* 534:    */     
/* 535:591 */     ForeignKey fk = (ForeignKey)this.foreignKeys.get(key);
/* 536:592 */     if (fk == null)
/* 537:    */     {
/* 538:593 */       fk = new ForeignKey();
/* 539:594 */       if (keyName != null) {
/* 540:595 */         fk.setName(keyName);
/* 541:    */       } else {
/* 542:598 */         fk.setName("FK" + uniqueColumnString(keyColumns.iterator(), referencedEntityName));
/* 543:    */       }
/* 544:602 */       fk.setTable(this);
/* 545:603 */       this.foreignKeys.put(key, fk);
/* 546:604 */       fk.setReferencedEntityName(referencedEntityName);
/* 547:605 */       fk.addColumns(keyColumns.iterator());
/* 548:606 */       if (referencedColumns != null) {
/* 549:607 */         fk.addReferencedColumns(referencedColumns.iterator());
/* 550:    */       }
/* 551:    */     }
/* 552:611 */     if (keyName != null) {
/* 553:612 */       fk.setName(keyName);
/* 554:    */     }
/* 555:615 */     return fk;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public String uniqueColumnString(Iterator iterator)
/* 559:    */   {
/* 560:620 */     return uniqueColumnString(iterator, null);
/* 561:    */   }
/* 562:    */   
/* 563:    */   public String uniqueColumnString(Iterator iterator, String referencedEntityName)
/* 564:    */   {
/* 565:624 */     int result = 0;
/* 566:625 */     if (referencedEntityName != null) {
/* 567:626 */       result += referencedEntityName.hashCode();
/* 568:    */     }
/* 569:628 */     while (iterator.hasNext()) {
/* 570:629 */       result += iterator.next().hashCode();
/* 571:    */     }
/* 572:631 */     return (Integer.toHexString(this.name.hashCode()) + Integer.toHexString(result)).toUpperCase();
/* 573:    */   }
/* 574:    */   
/* 575:    */   public String getSchema()
/* 576:    */   {
/* 577:636 */     return this.schema;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public void setSchema(String schema)
/* 581:    */   {
/* 582:640 */     if ((schema != null) && (schema.charAt(0) == '`'))
/* 583:    */     {
/* 584:641 */       this.schemaQuoted = true;
/* 585:642 */       this.schema = schema.substring(1, schema.length() - 1);
/* 586:    */     }
/* 587:    */     else
/* 588:    */     {
/* 589:645 */       this.schema = schema;
/* 590:    */     }
/* 591:    */   }
/* 592:    */   
/* 593:    */   public String getCatalog()
/* 594:    */   {
/* 595:650 */     return this.catalog;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public void setCatalog(String catalog)
/* 599:    */   {
/* 600:654 */     this.catalog = catalog;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public int getUniqueInteger()
/* 604:    */   {
/* 605:658 */     return this.uniqueInteger;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public void setIdentifierValue(KeyValue idValue)
/* 609:    */   {
/* 610:662 */     this.idValue = idValue;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public KeyValue getIdentifierValue()
/* 614:    */   {
/* 615:666 */     return this.idValue;
/* 616:    */   }
/* 617:    */   
/* 618:    */   public boolean isSchemaQuoted()
/* 619:    */   {
/* 620:670 */     return this.schemaQuoted;
/* 621:    */   }
/* 622:    */   
/* 623:    */   public boolean isQuoted()
/* 624:    */   {
/* 625:674 */     return this.quoted;
/* 626:    */   }
/* 627:    */   
/* 628:    */   public void setQuoted(boolean quoted)
/* 629:    */   {
/* 630:678 */     this.quoted = quoted;
/* 631:    */   }
/* 632:    */   
/* 633:    */   public void addCheckConstraint(String constraint)
/* 634:    */   {
/* 635:682 */     this.checkConstraints.add(constraint);
/* 636:    */   }
/* 637:    */   
/* 638:    */   public boolean containsColumn(Column column)
/* 639:    */   {
/* 640:686 */     return this.columns.containsValue(column);
/* 641:    */   }
/* 642:    */   
/* 643:    */   public String getRowId()
/* 644:    */   {
/* 645:690 */     return this.rowId;
/* 646:    */   }
/* 647:    */   
/* 648:    */   public void setRowId(String rowId)
/* 649:    */   {
/* 650:694 */     this.rowId = rowId;
/* 651:    */   }
/* 652:    */   
/* 653:    */   public String toString()
/* 654:    */   {
/* 655:698 */     StringBuffer buf = new StringBuffer().append(getClass().getName()).append('(');
/* 656:700 */     if (getCatalog() != null) {
/* 657:701 */       buf.append(getCatalog() + ".");
/* 658:    */     }
/* 659:703 */     if (getSchema() != null) {
/* 660:704 */       buf.append(getSchema() + ".");
/* 661:    */     }
/* 662:706 */     buf.append(getName()).append(')');
/* 663:707 */     return buf.toString();
/* 664:    */   }
/* 665:    */   
/* 666:    */   public String getSubselect()
/* 667:    */   {
/* 668:711 */     return this.subselect;
/* 669:    */   }
/* 670:    */   
/* 671:    */   public void setSubselect(String subselect)
/* 672:    */   {
/* 673:715 */     this.subselect = subselect;
/* 674:    */   }
/* 675:    */   
/* 676:    */   public boolean isSubselect()
/* 677:    */   {
/* 678:719 */     return this.subselect != null;
/* 679:    */   }
/* 680:    */   
/* 681:    */   public boolean isAbstractUnionTable()
/* 682:    */   {
/* 683:723 */     return (hasDenormalizedTables()) && (this.isAbstract);
/* 684:    */   }
/* 685:    */   
/* 686:    */   public boolean hasDenormalizedTables()
/* 687:    */   {
/* 688:727 */     return this.hasDenormalizedTables;
/* 689:    */   }
/* 690:    */   
/* 691:    */   void setHasDenormalizedTables()
/* 692:    */   {
/* 693:731 */     this.hasDenormalizedTables = true;
/* 694:    */   }
/* 695:    */   
/* 696:    */   public void setAbstract(boolean isAbstract)
/* 697:    */   {
/* 698:735 */     this.isAbstract = isAbstract;
/* 699:    */   }
/* 700:    */   
/* 701:    */   public boolean isAbstract()
/* 702:    */   {
/* 703:739 */     return this.isAbstract;
/* 704:    */   }
/* 705:    */   
/* 706:    */   public boolean isPhysicalTable()
/* 707:    */   {
/* 708:743 */     return (!isSubselect()) && (!isAbstractUnionTable());
/* 709:    */   }
/* 710:    */   
/* 711:    */   public String getComment()
/* 712:    */   {
/* 713:747 */     return this.comment;
/* 714:    */   }
/* 715:    */   
/* 716:    */   public void setComment(String comment)
/* 717:    */   {
/* 718:751 */     this.comment = comment;
/* 719:    */   }
/* 720:    */   
/* 721:    */   public Iterator getCheckConstraintsIterator()
/* 722:    */   {
/* 723:755 */     return this.checkConstraints.iterator();
/* 724:    */   }
/* 725:    */   
/* 726:    */   public Iterator sqlCommentStrings(Dialect dialect, String defaultCatalog, String defaultSchema)
/* 727:    */   {
/* 728:759 */     List comments = new ArrayList();
/* 729:760 */     if (dialect.supportsCommentOn())
/* 730:    */     {
/* 731:761 */       String tableName = getQualifiedName(dialect, defaultCatalog, defaultSchema);
/* 732:762 */       if (this.comment != null)
/* 733:    */       {
/* 734:763 */         StringBuffer buf = new StringBuffer().append("comment on table ").append(tableName).append(" is '").append(this.comment).append("'");
/* 735:    */         
/* 736:    */ 
/* 737:    */ 
/* 738:    */ 
/* 739:    */ 
/* 740:769 */         comments.add(buf.toString());
/* 741:    */       }
/* 742:771 */       Iterator iter = getColumnIterator();
/* 743:772 */       while (iter.hasNext())
/* 744:    */       {
/* 745:773 */         Column column = (Column)iter.next();
/* 746:774 */         String columnComment = column.getComment();
/* 747:775 */         if (columnComment != null)
/* 748:    */         {
/* 749:776 */           StringBuffer buf = new StringBuffer().append("comment on column ").append(tableName).append('.').append(column.getQuotedName(dialect)).append(" is '").append(columnComment).append("'");
/* 750:    */           
/* 751:    */ 
/* 752:    */ 
/* 753:    */ 
/* 754:    */ 
/* 755:    */ 
/* 756:    */ 
/* 757:784 */           comments.add(buf.toString());
/* 758:    */         }
/* 759:    */       }
/* 760:    */     }
/* 761:788 */     return comments.iterator();
/* 762:    */   }
/* 763:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Table
 * JD-Core Version:    0.7.0.1
 */