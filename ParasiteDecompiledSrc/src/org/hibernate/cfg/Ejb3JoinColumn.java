/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Set;
/*   7:    */ import javax.persistence.JoinColumn;
/*   8:    */ import javax.persistence.PrimaryKeyJoinColumn;
/*   9:    */ import org.hibernate.AnnotationException;
/*  10:    */ import org.hibernate.AssertionFailure;
/*  11:    */ import org.hibernate.MappingException;
/*  12:    */ import org.hibernate.annotations.JoinColumnOrFormula;
/*  13:    */ import org.hibernate.annotations.JoinColumnsOrFormulas;
/*  14:    */ import org.hibernate.annotations.JoinFormula;
/*  15:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  16:    */ import org.hibernate.internal.util.StringHelper;
/*  17:    */ import org.hibernate.mapping.Column;
/*  18:    */ import org.hibernate.mapping.Join;
/*  19:    */ import org.hibernate.mapping.KeyValue;
/*  20:    */ import org.hibernate.mapping.PersistentClass;
/*  21:    */ import org.hibernate.mapping.SimpleValue;
/*  22:    */ import org.hibernate.mapping.Table;
/*  23:    */ import org.hibernate.mapping.Value;
/*  24:    */ 
/*  25:    */ public class Ejb3JoinColumn
/*  26:    */   extends Ejb3Column
/*  27:    */ {
/*  28:    */   private String referencedColumn;
/*  29:    */   private String mappedBy;
/*  30:    */   private String mappedByPropertyName;
/*  31:    */   private String mappedByTableName;
/*  32:    */   private String mappedByEntityName;
/*  33:    */   private boolean JPA2ElementCollection;
/*  34:    */   private String manyToManyOwnerSideEntityName;
/*  35:    */   public static final int NO_REFERENCE = 0;
/*  36:    */   public static final int PK_REFERENCE = 1;
/*  37:    */   public static final int NON_PK_REFERENCE = 2;
/*  38:    */   
/*  39:    */   public void setJPA2ElementCollection(boolean JPA2ElementCollection)
/*  40:    */   {
/*  41: 69 */     this.JPA2ElementCollection = JPA2ElementCollection;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getManyToManyOwnerSideEntityName()
/*  45:    */   {
/*  46: 74 */     return this.manyToManyOwnerSideEntityName;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setManyToManyOwnerSideEntityName(String manyToManyOwnerSideEntityName)
/*  50:    */   {
/*  51: 78 */     this.manyToManyOwnerSideEntityName = manyToManyOwnerSideEntityName;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setReferencedColumn(String referencedColumn)
/*  55:    */   {
/*  56: 84 */     this.referencedColumn = referencedColumn;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getMappedBy()
/*  60:    */   {
/*  61: 88 */     return this.mappedBy;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setMappedBy(String mappedBy)
/*  65:    */   {
/*  66: 92 */     this.mappedBy = mappedBy;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private Ejb3JoinColumn()
/*  70:    */   {
/*  71: 97 */     setMappedBy("");
/*  72:    */   }
/*  73:    */   
/*  74:    */   private Ejb3JoinColumn(String sqlType, String name, boolean nullable, boolean unique, boolean insertable, boolean updatable, String referencedColumn, String secondaryTable, Map<String, Join> joins, PropertyHolder propertyHolder, String propertyName, String mappedBy, boolean isImplicit, Mappings mappings)
/*  75:    */   {
/*  76:118 */     setImplicit(isImplicit);
/*  77:119 */     setSqlType(sqlType);
/*  78:120 */     setLogicalColumnName(name);
/*  79:121 */     setNullable(nullable);
/*  80:122 */     setUnique(unique);
/*  81:123 */     setInsertable(insertable);
/*  82:124 */     setUpdatable(updatable);
/*  83:125 */     setSecondaryTableName(secondaryTable);
/*  84:126 */     setPropertyHolder(propertyHolder);
/*  85:127 */     setJoins(joins);
/*  86:128 */     setMappings(mappings);
/*  87:129 */     setPropertyName(BinderHelper.getRelativePath(propertyHolder, propertyName));
/*  88:130 */     bind();
/*  89:131 */     this.referencedColumn = referencedColumn;
/*  90:132 */     this.mappedBy = mappedBy;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getReferencedColumn()
/*  94:    */   {
/*  95:136 */     return this.referencedColumn;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Ejb3JoinColumn[] buildJoinColumnsOrFormulas(JoinColumnsOrFormulas anns, String mappedBy, Map<String, Join> joins, PropertyHolder propertyHolder, String propertyName, Mappings mappings)
/*  99:    */   {
/* 100:146 */     JoinColumnOrFormula[] ann = anns.value();
/* 101:147 */     Ejb3JoinColumn[] joinColumns = new Ejb3JoinColumn[ann.length];
/* 102:148 */     for (int i = 0; i < ann.length; i++)
/* 103:    */     {
/* 104:149 */       JoinColumnOrFormula join = ann[i];
/* 105:150 */       JoinFormula formula = join.formula();
/* 106:151 */       if ((formula.value() != null) && (!formula.value().equals(""))) {
/* 107:152 */         joinColumns[i] = buildJoinFormula(formula, mappedBy, joins, propertyHolder, propertyName, mappings);
/* 108:    */       } else {
/* 109:157 */         joinColumns[i] = buildJoinColumns(new JoinColumn[] { join.column() }, mappedBy, joins, propertyHolder, propertyName, mappings)[0];
/* 110:    */       }
/* 111:    */     }
/* 112:163 */     return joinColumns;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static Ejb3JoinColumn buildJoinFormula(JoinFormula ann, String mappedBy, Map<String, Join> joins, PropertyHolder propertyHolder, String propertyName, Mappings mappings)
/* 116:    */   {
/* 117:176 */     Ejb3JoinColumn formulaColumn = new Ejb3JoinColumn();
/* 118:177 */     formulaColumn.setFormula(ann.value());
/* 119:178 */     formulaColumn.setReferencedColumn(ann.referencedColumnName());
/* 120:179 */     formulaColumn.setMappings(mappings);
/* 121:180 */     formulaColumn.setPropertyHolder(propertyHolder);
/* 122:181 */     formulaColumn.setJoins(joins);
/* 123:182 */     formulaColumn.setPropertyName(BinderHelper.getRelativePath(propertyHolder, propertyName));
/* 124:183 */     formulaColumn.bind();
/* 125:184 */     return formulaColumn;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static Ejb3JoinColumn[] buildJoinColumns(JoinColumn[] anns, String mappedBy, Map<String, Join> joins, PropertyHolder propertyHolder, String propertyName, Mappings mappings)
/* 129:    */   {
/* 130:194 */     return buildJoinColumnsWithDefaultColumnSuffix(anns, mappedBy, joins, propertyHolder, propertyName, "", mappings);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static Ejb3JoinColumn[] buildJoinColumnsWithDefaultColumnSuffix(JoinColumn[] anns, String mappedBy, Map<String, Join> joins, PropertyHolder propertyHolder, String propertyName, String suffixForDefaultColumnName, Mappings mappings)
/* 134:    */   {
/* 135:207 */     JoinColumn[] actualColumns = propertyHolder.getOverriddenJoinColumn(StringHelper.qualify(propertyHolder.getPath(), propertyName));
/* 136:210 */     if (actualColumns == null) {
/* 137:210 */       actualColumns = anns;
/* 138:    */     }
/* 139:211 */     if ((actualColumns == null) || (actualColumns.length == 0)) {
/* 140:212 */       return new Ejb3JoinColumn[] { buildJoinColumn((JoinColumn)null, mappedBy, joins, propertyHolder, propertyName, suffixForDefaultColumnName, mappings) };
/* 141:    */     }
/* 142:224 */     int size = actualColumns.length;
/* 143:225 */     Ejb3JoinColumn[] result = new Ejb3JoinColumn[size];
/* 144:226 */     for (int index = 0; index < size; index++) {
/* 145:227 */       result[index] = buildJoinColumn(actualColumns[index], mappedBy, joins, propertyHolder, propertyName, suffixForDefaultColumnName, mappings);
/* 146:    */     }
/* 147:237 */     return result;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private static Ejb3JoinColumn buildJoinColumn(JoinColumn ann, String mappedBy, Map<String, Join> joins, PropertyHolder propertyHolder, String propertyName, String suffixForDefaultColumnName, Mappings mappings)
/* 151:    */   {
/* 152:251 */     if (ann != null)
/* 153:    */     {
/* 154:252 */       if (BinderHelper.isEmptyAnnotationValue(mappedBy)) {
/* 155:253 */         throw new AnnotationException("Illegal attempt to define a @JoinColumn with a mappedBy association: " + BinderHelper.getRelativePath(propertyHolder, propertyName));
/* 156:    */       }
/* 157:258 */       Ejb3JoinColumn joinColumn = new Ejb3JoinColumn();
/* 158:259 */       joinColumn.setJoinAnnotation(ann, null);
/* 159:260 */       if ((StringHelper.isEmpty(joinColumn.getLogicalColumnName())) && (!StringHelper.isEmpty(suffixForDefaultColumnName))) {
/* 160:262 */         joinColumn.setLogicalColumnName(propertyName + suffixForDefaultColumnName);
/* 161:    */       }
/* 162:264 */       joinColumn.setJoins(joins);
/* 163:265 */       joinColumn.setPropertyHolder(propertyHolder);
/* 164:266 */       joinColumn.setPropertyName(BinderHelper.getRelativePath(propertyHolder, propertyName));
/* 165:267 */       joinColumn.setImplicit(false);
/* 166:268 */       joinColumn.setMappings(mappings);
/* 167:269 */       joinColumn.bind();
/* 168:270 */       return joinColumn;
/* 169:    */     }
/* 170:273 */     Ejb3JoinColumn joinColumn = new Ejb3JoinColumn();
/* 171:274 */     joinColumn.setMappedBy(mappedBy);
/* 172:275 */     joinColumn.setJoins(joins);
/* 173:276 */     joinColumn.setPropertyHolder(propertyHolder);
/* 174:277 */     joinColumn.setPropertyName(BinderHelper.getRelativePath(propertyHolder, propertyName));
/* 175:281 */     if (!StringHelper.isEmpty(suffixForDefaultColumnName))
/* 176:    */     {
/* 177:282 */       joinColumn.setLogicalColumnName(propertyName + suffixForDefaultColumnName);
/* 178:283 */       joinColumn.setImplicit(false);
/* 179:    */     }
/* 180:    */     else
/* 181:    */     {
/* 182:286 */       joinColumn.setImplicit(true);
/* 183:    */     }
/* 184:288 */     joinColumn.setMappings(mappings);
/* 185:289 */     joinColumn.bind();
/* 186:290 */     return joinColumn;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setJoinAnnotation(JoinColumn annJoin, String defaultName)
/* 190:    */   {
/* 191:297 */     if (annJoin == null)
/* 192:    */     {
/* 193:298 */       setImplicit(true);
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:301 */       setImplicit(false);
/* 198:302 */       if (!BinderHelper.isEmptyAnnotationValue(annJoin.columnDefinition())) {
/* 199:302 */         setSqlType(annJoin.columnDefinition());
/* 200:    */       }
/* 201:303 */       if (!BinderHelper.isEmptyAnnotationValue(annJoin.name())) {
/* 202:303 */         setLogicalColumnName(annJoin.name());
/* 203:    */       }
/* 204:304 */       setNullable(annJoin.nullable());
/* 205:305 */       setUnique(annJoin.unique());
/* 206:306 */       setInsertable(annJoin.insertable());
/* 207:307 */       setUpdatable(annJoin.updatable());
/* 208:308 */       setReferencedColumn(annJoin.referencedColumnName());
/* 209:309 */       setSecondaryTableName(annJoin.table());
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static Ejb3JoinColumn buildJoinColumn(PrimaryKeyJoinColumn pkJoinAnn, JoinColumn joinAnn, Value identifier, Map<String, Join> joins, PropertyHolder propertyHolder, Mappings mappings)
/* 214:    */   {
/* 215:323 */     Column col = (Column)identifier.getColumnIterator().next();
/* 216:324 */     String defaultName = mappings.getLogicalColumnName(col.getQuotedName(), identifier.getTable());
/* 217:325 */     if ((pkJoinAnn != null) || (joinAnn != null))
/* 218:    */     {
/* 219:    */       String referencedColumnName;
/* 220:    */       String colName;
/* 221:    */       String columnDefinition;
/* 222:    */       String referencedColumnName;
/* 223:329 */       if (pkJoinAnn != null)
/* 224:    */       {
/* 225:330 */         String colName = pkJoinAnn.name();
/* 226:331 */         String columnDefinition = pkJoinAnn.columnDefinition();
/* 227:332 */         referencedColumnName = pkJoinAnn.referencedColumnName();
/* 228:    */       }
/* 229:    */       else
/* 230:    */       {
/* 231:335 */         colName = joinAnn.name();
/* 232:336 */         columnDefinition = joinAnn.columnDefinition();
/* 233:337 */         referencedColumnName = joinAnn.referencedColumnName();
/* 234:    */       }
/* 235:340 */       String sqlType = "".equals(columnDefinition) ? null : mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(columnDefinition);
/* 236:    */       
/* 237:    */ 
/* 238:343 */       String name = "".equals(colName) ? defaultName : colName;
/* 239:    */       
/* 240:    */ 
/* 241:346 */       name = mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(name);
/* 242:347 */       return new Ejb3JoinColumn(sqlType, name, false, false, true, true, referencedColumnName, null, joins, propertyHolder, null, null, false, mappings);
/* 243:    */     }
/* 244:357 */     defaultName = mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(defaultName);
/* 245:358 */     return new Ejb3JoinColumn((String)null, defaultName, false, false, true, true, null, (String)null, joins, propertyHolder, null, null, true, mappings);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setPersistentClass(PersistentClass persistentClass, Map<String, Join> joins, Map<XClass, InheritanceState> inheritanceStatePerClass)
/* 249:    */   {
/* 250:375 */     this.propertyHolder = PropertyHolderBuilder.buildPropertyHolder(persistentClass, joins, getMappings(), inheritanceStatePerClass);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public static void checkIfJoinColumn(Object columns, PropertyHolder holder, PropertyData property)
/* 254:    */   {
/* 255:379 */     if (!(columns instanceof Ejb3JoinColumn[])) {
/* 256:380 */       throw new AnnotationException("@Column cannot be used on an association property: " + holder.getEntityName() + "." + property.getPropertyName());
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void copyReferencedStructureAndCreateDefaultJoinColumns(PersistentClass referencedEntity, Iterator columnIterator, SimpleValue value)
/* 261:    */   {
/* 262:395 */     if (!isNameDeferred()) {
/* 263:396 */       throw new AssertionFailure("Building implicit column but the column is not implicit");
/* 264:    */     }
/* 265:398 */     while (columnIterator.hasNext())
/* 266:    */     {
/* 267:399 */       Column synthCol = (Column)columnIterator.next();
/* 268:400 */       linkValueUsingDefaultColumnNaming(synthCol, referencedEntity, value);
/* 269:    */     }
/* 270:403 */     setMappingColumn(null);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void linkValueUsingDefaultColumnNaming(Column referencedColumn, PersistentClass referencedEntity, SimpleValue value)
/* 274:    */   {
/* 275:411 */     String logicalReferencedColumn = getMappings().getLogicalColumnName(referencedColumn.getQuotedName(), referencedEntity.getTable());
/* 276:    */     
/* 277:    */ 
/* 278:414 */     String columnName = buildDefaultColumnName(referencedEntity, logicalReferencedColumn);
/* 279:    */     
/* 280:416 */     setLogicalColumnName(columnName);
/* 281:417 */     setReferencedColumn(logicalReferencedColumn);
/* 282:418 */     initMappingColumn(columnName, null, referencedColumn.getLength(), referencedColumn.getPrecision(), referencedColumn.getScale(), getMappingColumn() != null ? getMappingColumn().isNullable() : false, referencedColumn.getSqlType(), getMappingColumn() != null ? getMappingColumn().isUnique() : false, false);
/* 283:    */     
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:428 */     linkWithValue(value);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void addDefaultJoinColumnName(PersistentClass referencedEntity, String logicalReferencedColumn)
/* 296:    */   {
/* 297:432 */     String columnName = buildDefaultColumnName(referencedEntity, logicalReferencedColumn);
/* 298:433 */     getMappingColumn().setName(columnName);
/* 299:434 */     setLogicalColumnName(columnName);
/* 300:    */   }
/* 301:    */   
/* 302:    */   private String buildDefaultColumnName(PersistentClass referencedEntity, String logicalReferencedColumn)
/* 303:    */   {
/* 304:439 */     boolean mappedBySide = (this.mappedByTableName != null) || (this.mappedByPropertyName != null);
/* 305:440 */     boolean ownerSide = getPropertyName() != null;
/* 306:    */     
/* 307:442 */     Boolean isRefColumnQuoted = Boolean.valueOf(StringHelper.isQuoted(logicalReferencedColumn));
/* 308:443 */     String unquotedLogicalReferenceColumn = isRefColumnQuoted.booleanValue() ? StringHelper.unquote(logicalReferencedColumn) : logicalReferencedColumn;
/* 309:    */     String columnName;
/* 310:447 */     if (mappedBySide)
/* 311:    */     {
/* 312:448 */       String unquotedMappedbyTable = StringHelper.unquote(this.mappedByTableName);
/* 313:449 */       String ownerObjectName = (this.JPA2ElementCollection) && (this.mappedByEntityName != null) ? StringHelper.unqualify(this.mappedByEntityName) : unquotedMappedbyTable;
/* 314:    */       
/* 315:451 */       String columnName = getMappings().getNamingStrategy().foreignKeyColumnName(this.mappedByPropertyName, this.mappedByEntityName, ownerObjectName, unquotedLogicalReferenceColumn);
/* 316:458 */       if ((isRefColumnQuoted.booleanValue()) || (StringHelper.isQuoted(this.mappedByTableName))) {
/* 317:459 */         columnName = StringHelper.quote(columnName);
/* 318:    */       }
/* 319:    */     }
/* 320:462 */     else if (ownerSide)
/* 321:    */     {
/* 322:463 */       String logicalTableName = getMappings().getLogicalTableName(referencedEntity.getTable());
/* 323:464 */       String unquotedLogicalTableName = StringHelper.unquote(logicalTableName);
/* 324:465 */       String columnName = getMappings().getNamingStrategy().foreignKeyColumnName(getPropertyName(), referencedEntity.getEntityName(), unquotedLogicalTableName, unquotedLogicalReferenceColumn);
/* 325:472 */       if ((isRefColumnQuoted.booleanValue()) || (StringHelper.isQuoted(logicalTableName))) {
/* 326:473 */         columnName = StringHelper.quote(columnName);
/* 327:    */       }
/* 328:    */     }
/* 329:    */     else
/* 330:    */     {
/* 331:478 */       String logicalTableName = getMappings().getLogicalTableName(referencedEntity.getTable());
/* 332:479 */       String unquotedLogicalTableName = StringHelper.unquote(logicalTableName);
/* 333:480 */       columnName = getMappings().getNamingStrategy().joinKeyColumnName(unquotedLogicalReferenceColumn, unquotedLogicalTableName);
/* 334:485 */       if ((isRefColumnQuoted.booleanValue()) || (StringHelper.isQuoted(logicalTableName))) {
/* 335:486 */         columnName = StringHelper.quote(columnName);
/* 336:    */       }
/* 337:    */     }
/* 338:489 */     return columnName;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void linkValueUsingAColumnCopy(Column column, SimpleValue value)
/* 342:    */   {
/* 343:496 */     initMappingColumn(column.getQuotedName(), null, column.getLength(), column.getPrecision(), column.getScale(), getMappingColumn().isNullable(), column.getSqlType(), getMappingColumn().isUnique(), false);
/* 344:    */     
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:507 */     linkWithValue(value);
/* 355:    */   }
/* 356:    */   
/* 357:    */   protected void addColumnBinding(SimpleValue value)
/* 358:    */   {
/* 359:512 */     if (StringHelper.isEmpty(this.mappedBy))
/* 360:    */     {
/* 361:513 */       String unquotedLogColName = StringHelper.unquote(getLogicalColumnName());
/* 362:514 */       String unquotedRefColumn = StringHelper.unquote(getReferencedColumn());
/* 363:515 */       String logicalColumnName = getMappings().getNamingStrategy().logicalCollectionColumnName(unquotedLogColName, getPropertyName(), unquotedRefColumn);
/* 364:517 */       if ((StringHelper.isQuoted(getLogicalColumnName())) || (StringHelper.isQuoted(getLogicalColumnName()))) {
/* 365:518 */         logicalColumnName = StringHelper.quote(logicalColumnName);
/* 366:    */       }
/* 367:520 */       getMappings().addColumnBinding(logicalColumnName, getMappingColumn(), value.getTable());
/* 368:    */     }
/* 369:    */   }
/* 370:    */   
/* 371:    */   public static int checkReferencedColumnsType(Ejb3JoinColumn[] columns, PersistentClass referencedEntity, Mappings mappings)
/* 372:    */   {
/* 373:537 */     Set<Column> idColumns = new HashSet();
/* 374:538 */     Iterator idColumnsIt = referencedEntity.getKey().getColumnIterator();
/* 375:539 */     while (idColumnsIt.hasNext()) {
/* 376:540 */       idColumns.add((Column)idColumnsIt.next());
/* 377:    */     }
/* 378:543 */     boolean isFkReferencedColumnName = false;
/* 379:544 */     boolean noReferencedColumn = true;
/* 380:546 */     if (columns.length == 0) {
/* 381:546 */       return 0;
/* 382:    */     }
/* 383:547 */     Object columnOwner = BinderHelper.findColumnOwner(referencedEntity, columns[0].getReferencedColumn(), mappings);
/* 384:550 */     if (columnOwner == null) {
/* 385:    */       try
/* 386:    */       {
/* 387:552 */         throw new MappingException("Unable to find column with logical name: " + columns[0].getReferencedColumn() + " in " + referencedEntity.getTable() + " and its related " + "supertables and secondary tables");
/* 388:    */       }
/* 389:    */       catch (MappingException e)
/* 390:    */       {
/* 391:559 */         throw new RecoverableException(e);
/* 392:    */       }
/* 393:    */     }
/* 394:562 */     Table matchingTable = (columnOwner instanceof PersistentClass) ? ((PersistentClass)columnOwner).getTable() : ((Join)columnOwner).getTable();
/* 395:566 */     for (Ejb3JoinColumn ejb3Column : columns)
/* 396:    */     {
/* 397:567 */       String logicalReferencedColumnName = ejb3Column.getReferencedColumn();
/* 398:568 */       if (StringHelper.isNotEmpty(logicalReferencedColumnName))
/* 399:    */       {
/* 400:    */         String referencedColumnName;
/* 401:    */         try
/* 402:    */         {
/* 403:571 */           referencedColumnName = mappings.getPhysicalColumnName(logicalReferencedColumnName, matchingTable);
/* 404:    */         }
/* 405:    */         catch (MappingException me)
/* 406:    */         {
/* 407:575 */           throw new MappingException("Unable to find column with logical name: " + logicalReferencedColumnName + " in " + matchingTable.getName());
/* 408:    */         }
/* 409:580 */         noReferencedColumn = false;
/* 410:581 */         Column refCol = new Column(referencedColumnName);
/* 411:582 */         boolean contains = idColumns.contains(refCol);
/* 412:583 */         if (!contains)
/* 413:    */         {
/* 414:584 */           isFkReferencedColumnName = true;
/* 415:585 */           break;
/* 416:    */         }
/* 417:    */       }
/* 418:    */     }
/* 419:589 */     if (isFkReferencedColumnName) {
/* 420:590 */       return 2;
/* 421:    */     }
/* 422:592 */     if (noReferencedColumn) {
/* 423:593 */       return 0;
/* 424:    */     }
/* 425:595 */     if (idColumns.size() != columns.length) {
/* 426:597 */       return 2;
/* 427:    */     }
/* 428:600 */     return 1;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void overrideFromReferencedColumnIfNecessary(Column column)
/* 432:    */   {
/* 433:610 */     if (getMappingColumn() != null)
/* 434:    */     {
/* 435:613 */       if (StringHelper.isEmpty(this.sqlType))
/* 436:    */       {
/* 437:614 */         this.sqlType = column.getSqlType();
/* 438:615 */         getMappingColumn().setSqlType(this.sqlType);
/* 439:    */       }
/* 440:619 */       getMappingColumn().setLength(column.getLength());
/* 441:620 */       getMappingColumn().setPrecision(column.getPrecision());
/* 442:621 */       getMappingColumn().setScale(column.getScale());
/* 443:    */     }
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void redefineColumnName(String columnName, String propertyName, boolean applyNamingStrategy)
/* 447:    */   {
/* 448:627 */     if (StringHelper.isNotEmpty(columnName)) {
/* 449:628 */       getMappingColumn().setName(applyNamingStrategy ? getMappings().getNamingStrategy().columnName(columnName) : columnName);
/* 450:    */     }
/* 451:    */   }
/* 452:    */   
/* 453:    */   public static Ejb3JoinColumn[] buildJoinTableJoinColumns(JoinColumn[] annJoins, Map<String, Join> secondaryTables, PropertyHolder propertyHolder, String propertyName, String mappedBy, Mappings mappings)
/* 454:    */   {
/* 455:    */     Ejb3JoinColumn[] joinColumns;
/* 456:    */     Ejb3JoinColumn[] joinColumns;
/* 457:644 */     if (annJoins == null)
/* 458:    */     {
/* 459:645 */       Ejb3JoinColumn currentJoinColumn = new Ejb3JoinColumn();
/* 460:646 */       currentJoinColumn.setImplicit(true);
/* 461:647 */       currentJoinColumn.setNullable(false);
/* 462:648 */       currentJoinColumn.setPropertyHolder(propertyHolder);
/* 463:649 */       currentJoinColumn.setJoins(secondaryTables);
/* 464:650 */       currentJoinColumn.setMappings(mappings);
/* 465:651 */       currentJoinColumn.setPropertyName(BinderHelper.getRelativePath(propertyHolder, propertyName));
/* 466:    */       
/* 467:    */ 
/* 468:654 */       currentJoinColumn.setMappedBy(mappedBy);
/* 469:655 */       currentJoinColumn.bind();
/* 470:    */       
/* 471:657 */       joinColumns = new Ejb3JoinColumn[] { currentJoinColumn };
/* 472:    */     }
/* 473:    */     else
/* 474:    */     {
/* 475:663 */       joinColumns = new Ejb3JoinColumn[annJoins.length];
/* 476:    */       
/* 477:665 */       int length = annJoins.length;
/* 478:666 */       for (int index = 0; index < length; index++)
/* 479:    */       {
/* 480:667 */         JoinColumn annJoin = annJoins[index];
/* 481:668 */         Ejb3JoinColumn currentJoinColumn = new Ejb3JoinColumn();
/* 482:669 */         currentJoinColumn.setImplicit(true);
/* 483:670 */         currentJoinColumn.setPropertyHolder(propertyHolder);
/* 484:671 */         currentJoinColumn.setJoins(secondaryTables);
/* 485:672 */         currentJoinColumn.setMappings(mappings);
/* 486:673 */         currentJoinColumn.setPropertyName(BinderHelper.getRelativePath(propertyHolder, propertyName));
/* 487:674 */         currentJoinColumn.setMappedBy(mappedBy);
/* 488:675 */         currentJoinColumn.setJoinAnnotation(annJoin, propertyName);
/* 489:676 */         currentJoinColumn.setNullable(false);
/* 490:    */         
/* 491:678 */         currentJoinColumn.bind();
/* 492:679 */         joinColumns[index] = currentJoinColumn;
/* 493:    */       }
/* 494:    */     }
/* 495:682 */     return joinColumns;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public void setMappedBy(String entityName, String logicalTableName, String mappedByProperty)
/* 499:    */   {
/* 500:686 */     this.mappedByEntityName = entityName;
/* 501:687 */     this.mappedByTableName = logicalTableName;
/* 502:688 */     this.mappedByPropertyName = mappedByProperty;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public String toString()
/* 506:    */   {
/* 507:693 */     StringBuilder sb = new StringBuilder();
/* 508:694 */     sb.append("Ejb3JoinColumn");
/* 509:695 */     sb.append("{logicalColumnName='").append(getLogicalColumnName()).append('\'');
/* 510:696 */     sb.append(", referencedColumn='").append(this.referencedColumn).append('\'');
/* 511:697 */     sb.append(", mappedBy='").append(this.mappedBy).append('\'');
/* 512:698 */     sb.append('}');
/* 513:699 */     return sb.toString();
/* 514:    */   }
/* 515:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.Ejb3JoinColumn
 * JD-Core Version:    0.7.0.1
 */