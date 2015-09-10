/*   1:    */ package org.hibernate.persister.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.QueryException;
/*  12:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  13:    */ import org.hibernate.cfg.Settings;
/*  14:    */ import org.hibernate.dialect.Dialect;
/*  15:    */ import org.hibernate.engine.OptimisticLockStyle;
/*  16:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  17:    */ import org.hibernate.engine.spi.Mapping;
/*  18:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  19:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  20:    */ import org.hibernate.mapping.Column;
/*  21:    */ import org.hibernate.mapping.Join;
/*  22:    */ import org.hibernate.mapping.KeyValue;
/*  23:    */ import org.hibernate.mapping.PersistentClass;
/*  24:    */ import org.hibernate.mapping.PrimaryKey;
/*  25:    */ import org.hibernate.mapping.Property;
/*  26:    */ import org.hibernate.mapping.Selectable;
/*  27:    */ import org.hibernate.mapping.Subclass;
/*  28:    */ import org.hibernate.mapping.Table;
/*  29:    */ import org.hibernate.mapping.Value;
/*  30:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  31:    */ import org.hibernate.sql.CaseFragment;
/*  32:    */ import org.hibernate.sql.SelectFragment;
/*  33:    */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  34:    */ import org.hibernate.type.StandardBasicTypes;
/*  35:    */ import org.hibernate.type.Type;
/*  36:    */ 
/*  37:    */ public class JoinedSubclassEntityPersister
/*  38:    */   extends AbstractEntityPersister
/*  39:    */ {
/*  40:    */   private final int tableSpan;
/*  41:    */   private final String[] tableNames;
/*  42:    */   private final String[] naturalOrderTableNames;
/*  43:    */   private final String[][] tableKeyColumns;
/*  44:    */   private final String[][] tableKeyColumnReaders;
/*  45:    */   private final String[][] tableKeyColumnReaderTemplates;
/*  46:    */   private final String[][] naturalOrderTableKeyColumns;
/*  47:    */   private final String[][] naturalOrderTableKeyColumnReaders;
/*  48:    */   private final String[][] naturalOrderTableKeyColumnReaderTemplates;
/*  49:    */   private final boolean[] naturalOrderCascadeDeleteEnabled;
/*  50:    */   private final String[] spaces;
/*  51:    */   private final String[] subclassClosure;
/*  52:    */   private final String[] subclassTableNameClosure;
/*  53:    */   private final String[][] subclassTableKeyColumnClosure;
/*  54:    */   private final boolean[] isClassOrSuperclassTable;
/*  55:    */   private final int[] naturalOrderPropertyTableNumbers;
/*  56:    */   private final int[] propertyTableNumbers;
/*  57:    */   private final int[] subclassPropertyTableNumberClosure;
/*  58:    */   private final int[] subclassColumnTableNumberClosure;
/*  59:    */   private final int[] subclassFormulaTableNumberClosure;
/*  60:    */   private final boolean[] subclassTableSequentialSelect;
/*  61:    */   private final boolean[] subclassTableIsLazyClosure;
/*  62:103 */   private final Map subclassesByDiscriminatorValue = new HashMap();
/*  63:    */   private final String[] discriminatorValues;
/*  64:    */   private final String[] notNullColumnNames;
/*  65:    */   private final int[] notNullColumnTableNumbers;
/*  66:    */   private final String[] constraintOrderedTableNames;
/*  67:    */   private final String[][] constraintOrderedKeyColumnNames;
/*  68:    */   private final Object discriminatorValue;
/*  69:    */   private final String discriminatorSQLString;
/*  70:    */   private final int coreTableSpan;
/*  71:    */   private final boolean[] isNullableTable;
/*  72:    */   
/*  73:    */   public JoinedSubclassEntityPersister(PersistentClass persistentClass, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping mapping)
/*  74:    */     throws HibernateException
/*  75:    */   {
/*  76:127 */     super(persistentClass, cacheAccessStrategy, factory);
/*  77:131 */     if (persistentClass.isPolymorphic())
/*  78:    */     {
/*  79:    */       try
/*  80:    */       {
/*  81:133 */         this.discriminatorValue = Integer.valueOf(persistentClass.getSubclassId());
/*  82:134 */         this.discriminatorSQLString = this.discriminatorValue.toString();
/*  83:    */       }
/*  84:    */       catch (Exception e)
/*  85:    */       {
/*  86:137 */         throw new MappingException("Could not format discriminator value to SQL string", e);
/*  87:    */       }
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:141 */       this.discriminatorValue = null;
/*  92:142 */       this.discriminatorSQLString = null;
/*  93:    */     }
/*  94:145 */     if ((optimisticLockStyle() == OptimisticLockStyle.ALL) || (optimisticLockStyle() == OptimisticLockStyle.DIRTY)) {
/*  95:146 */       throw new MappingException("optimistic-lock=all|dirty not supported for joined-subclass mappings [" + getEntityName() + "]");
/*  96:    */     }
/*  97:151 */     int idColumnSpan = getIdentifierColumnSpan();
/*  98:    */     
/*  99:153 */     ArrayList tables = new ArrayList();
/* 100:154 */     ArrayList keyColumns = new ArrayList();
/* 101:155 */     ArrayList keyColumnReaders = new ArrayList();
/* 102:156 */     ArrayList keyColumnReaderTemplates = new ArrayList();
/* 103:157 */     ArrayList cascadeDeletes = new ArrayList();
/* 104:158 */     Iterator titer = persistentClass.getTableClosureIterator();
/* 105:159 */     Iterator kiter = persistentClass.getKeyClosureIterator();
/* 106:160 */     while (titer.hasNext())
/* 107:    */     {
/* 108:161 */       Table tab = (Table)titer.next();
/* 109:162 */       KeyValue key = (KeyValue)kiter.next();
/* 110:163 */       String tabname = tab.getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/* 111:    */       
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:168 */       tables.add(tabname);
/* 116:169 */       String[] keyCols = new String[idColumnSpan];
/* 117:170 */       String[] keyColReaders = new String[idColumnSpan];
/* 118:171 */       String[] keyColReaderTemplates = new String[idColumnSpan];
/* 119:172 */       Iterator citer = key.getColumnIterator();
/* 120:173 */       for (int k = 0; k < idColumnSpan; k++)
/* 121:    */       {
/* 122:174 */         Column column = (Column)citer.next();
/* 123:175 */         keyCols[k] = column.getQuotedName(factory.getDialect());
/* 124:176 */         keyColReaders[k] = column.getReadExpr(factory.getDialect());
/* 125:177 */         keyColReaderTemplates[k] = column.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/* 126:    */       }
/* 127:179 */       keyColumns.add(keyCols);
/* 128:180 */       keyColumnReaders.add(keyColReaders);
/* 129:181 */       keyColumnReaderTemplates.add(keyColReaderTemplates);
/* 130:182 */       cascadeDeletes.add(Boolean.valueOf((key.isCascadeDeleteEnabled()) && (factory.getDialect().supportsCascadeDelete())));
/* 131:    */     }
/* 132:186 */     this.coreTableSpan = tables.size();
/* 133:    */     
/* 134:188 */     this.isNullableTable = new boolean[persistentClass.getJoinClosureSpan()];
/* 135:    */     
/* 136:190 */     int tableIndex = 0;
/* 137:191 */     Iterator joinIter = persistentClass.getJoinClosureIterator();
/* 138:192 */     while (joinIter.hasNext())
/* 139:    */     {
/* 140:193 */       Join join = (Join)joinIter.next();
/* 141:    */       
/* 142:195 */       this.isNullableTable[(tableIndex++)] = join.isOptional();
/* 143:    */       
/* 144:197 */       Table table = join.getTable();
/* 145:    */       
/* 146:199 */       String tableName = table.getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/* 147:    */       
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:204 */       tables.add(tableName);
/* 152:    */       
/* 153:206 */       KeyValue key = join.getKey();
/* 154:207 */       int joinIdColumnSpan = key.getColumnSpan();
/* 155:    */       
/* 156:209 */       String[] keyCols = new String[joinIdColumnSpan];
/* 157:210 */       String[] keyColReaders = new String[joinIdColumnSpan];
/* 158:211 */       String[] keyColReaderTemplates = new String[joinIdColumnSpan];
/* 159:    */       
/* 160:213 */       Iterator citer = key.getColumnIterator();
/* 161:215 */       for (int k = 0; k < joinIdColumnSpan; k++)
/* 162:    */       {
/* 163:216 */         Column column = (Column)citer.next();
/* 164:217 */         keyCols[k] = column.getQuotedName(factory.getDialect());
/* 165:218 */         keyColReaders[k] = column.getReadExpr(factory.getDialect());
/* 166:219 */         keyColReaderTemplates[k] = column.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/* 167:    */       }
/* 168:221 */       keyColumns.add(keyCols);
/* 169:222 */       keyColumnReaders.add(keyColReaders);
/* 170:223 */       keyColumnReaderTemplates.add(keyColReaderTemplates);
/* 171:224 */       cascadeDeletes.add(Boolean.valueOf((key.isCascadeDeleteEnabled()) && (factory.getDialect().supportsCascadeDelete())));
/* 172:    */     }
/* 173:227 */     this.naturalOrderTableNames = ArrayHelper.toStringArray(tables);
/* 174:228 */     this.naturalOrderTableKeyColumns = ArrayHelper.to2DStringArray(keyColumns);
/* 175:229 */     this.naturalOrderTableKeyColumnReaders = ArrayHelper.to2DStringArray(keyColumnReaders);
/* 176:230 */     this.naturalOrderTableKeyColumnReaderTemplates = ArrayHelper.to2DStringArray(keyColumnReaderTemplates);
/* 177:231 */     this.naturalOrderCascadeDeleteEnabled = ArrayHelper.toBooleanArray(cascadeDeletes);
/* 178:    */     
/* 179:233 */     ArrayList subtables = new ArrayList();
/* 180:234 */     ArrayList isConcretes = new ArrayList();
/* 181:235 */     ArrayList isDeferreds = new ArrayList();
/* 182:236 */     ArrayList isLazies = new ArrayList();
/* 183:    */     
/* 184:238 */     keyColumns = new ArrayList();
/* 185:239 */     titer = persistentClass.getSubclassTableClosureIterator();
/* 186:240 */     while (titer.hasNext())
/* 187:    */     {
/* 188:241 */       Table tab = (Table)titer.next();
/* 189:242 */       isConcretes.add(Boolean.valueOf(persistentClass.isClassOrSuperclassTable(tab)));
/* 190:243 */       isDeferreds.add(Boolean.FALSE);
/* 191:244 */       isLazies.add(Boolean.FALSE);
/* 192:245 */       String tabname = tab.getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/* 193:    */       
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:250 */       subtables.add(tabname);
/* 198:251 */       String[] key = new String[idColumnSpan];
/* 199:252 */       Iterator citer = tab.getPrimaryKey().getColumnIterator();
/* 200:253 */       for (int k = 0; k < idColumnSpan; k++) {
/* 201:254 */         key[k] = ((Column)citer.next()).getQuotedName(factory.getDialect());
/* 202:    */       }
/* 203:256 */       keyColumns.add(key);
/* 204:    */     }
/* 205:260 */     joinIter = persistentClass.getSubclassJoinClosureIterator();
/* 206:261 */     while (joinIter.hasNext())
/* 207:    */     {
/* 208:262 */       Join join = (Join)joinIter.next();
/* 209:    */       
/* 210:264 */       Table tab = join.getTable();
/* 211:    */       
/* 212:266 */       isConcretes.add(Boolean.valueOf(persistentClass.isClassOrSuperclassTable(tab)));
/* 213:267 */       isDeferreds.add(Boolean.valueOf(join.isSequentialSelect()));
/* 214:268 */       isLazies.add(Boolean.valueOf(join.isLazy()));
/* 215:    */       
/* 216:270 */       String tabname = tab.getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/* 217:    */       
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:275 */       subtables.add(tabname);
/* 222:276 */       String[] key = new String[idColumnSpan];
/* 223:277 */       Iterator citer = tab.getPrimaryKey().getColumnIterator();
/* 224:278 */       for (int k = 0; k < idColumnSpan; k++) {
/* 225:279 */         key[k] = ((Column)citer.next()).getQuotedName(factory.getDialect());
/* 226:    */       }
/* 227:281 */       keyColumns.add(key);
/* 228:    */     }
/* 229:284 */     String[] naturalOrderSubclassTableNameClosure = ArrayHelper.toStringArray(subtables);
/* 230:285 */     String[][] naturalOrderSubclassTableKeyColumnClosure = ArrayHelper.to2DStringArray(keyColumns);
/* 231:286 */     this.isClassOrSuperclassTable = ArrayHelper.toBooleanArray(isConcretes);
/* 232:287 */     this.subclassTableSequentialSelect = ArrayHelper.toBooleanArray(isDeferreds);
/* 233:288 */     this.subclassTableIsLazyClosure = ArrayHelper.toBooleanArray(isLazies);
/* 234:    */     
/* 235:290 */     this.constraintOrderedTableNames = new String[naturalOrderSubclassTableNameClosure.length];
/* 236:291 */     this.constraintOrderedKeyColumnNames = new String[naturalOrderSubclassTableNameClosure.length][];
/* 237:292 */     int currentPosition = 0;
/* 238:293 */     for (int i = naturalOrderSubclassTableNameClosure.length - 1; i >= 0; currentPosition++)
/* 239:    */     {
/* 240:294 */       this.constraintOrderedTableNames[currentPosition] = naturalOrderSubclassTableNameClosure[i];
/* 241:295 */       this.constraintOrderedKeyColumnNames[currentPosition] = naturalOrderSubclassTableKeyColumnClosure[i];i--;
/* 242:    */     }
/* 243:308 */     this.tableSpan = this.naturalOrderTableNames.length;
/* 244:309 */     this.tableNames = reverse(this.naturalOrderTableNames, this.coreTableSpan);
/* 245:310 */     this.tableKeyColumns = reverse(this.naturalOrderTableKeyColumns, this.coreTableSpan);
/* 246:311 */     this.tableKeyColumnReaders = reverse(this.naturalOrderTableKeyColumnReaders, this.coreTableSpan);
/* 247:312 */     this.tableKeyColumnReaderTemplates = reverse(this.naturalOrderTableKeyColumnReaderTemplates, this.coreTableSpan);
/* 248:313 */     this.subclassTableNameClosure = reverse(naturalOrderSubclassTableNameClosure, this.coreTableSpan);
/* 249:314 */     this.subclassTableKeyColumnClosure = reverse(naturalOrderSubclassTableKeyColumnClosure, this.coreTableSpan);
/* 250:    */     
/* 251:316 */     this.spaces = ArrayHelper.join(this.tableNames, ArrayHelper.toStringArray(persistentClass.getSynchronizedTables()));
/* 252:    */     
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:322 */     this.customSQLInsert = new String[this.tableSpan];
/* 258:323 */     this.customSQLUpdate = new String[this.tableSpan];
/* 259:324 */     this.customSQLDelete = new String[this.tableSpan];
/* 260:325 */     this.insertCallable = new boolean[this.tableSpan];
/* 261:326 */     this.updateCallable = new boolean[this.tableSpan];
/* 262:327 */     this.deleteCallable = new boolean[this.tableSpan];
/* 263:328 */     this.insertResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.tableSpan];
/* 264:329 */     this.updateResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.tableSpan];
/* 265:330 */     this.deleteResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.tableSpan];
/* 266:    */     
/* 267:332 */     PersistentClass pc = persistentClass;
/* 268:333 */     int jk = this.coreTableSpan - 1;
/* 269:334 */     while (pc != null)
/* 270:    */     {
/* 271:335 */       this.customSQLInsert[jk] = pc.getCustomSQLInsert();
/* 272:336 */       this.insertCallable[jk] = ((this.customSQLInsert[jk] != null) && (pc.isCustomInsertCallable()) ? 1 : false);
/* 273:337 */       this.insertResultCheckStyles[jk] = (pc.getCustomSQLInsertCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLInsert[jk], this.insertCallable[jk]) : pc.getCustomSQLInsertCheckStyle());
/* 274:    */       
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:342 */       this.customSQLUpdate[jk] = pc.getCustomSQLUpdate();
/* 279:343 */       this.updateCallable[jk] = ((this.customSQLUpdate[jk] != null) && (pc.isCustomUpdateCallable()) ? 1 : false);
/* 280:344 */       this.updateResultCheckStyles[jk] = (pc.getCustomSQLUpdateCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLUpdate[jk], this.updateCallable[jk]) : pc.getCustomSQLUpdateCheckStyle());
/* 281:    */       
/* 282:    */ 
/* 283:347 */       this.customSQLDelete[jk] = pc.getCustomSQLDelete();
/* 284:348 */       this.deleteCallable[jk] = ((this.customSQLDelete[jk] != null) && (pc.isCustomDeleteCallable()) ? 1 : false);
/* 285:349 */       this.deleteResultCheckStyles[jk] = (pc.getCustomSQLDeleteCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLDelete[jk], this.deleteCallable[jk]) : pc.getCustomSQLDeleteCheckStyle());
/* 286:    */       
/* 287:    */ 
/* 288:352 */       jk--;
/* 289:353 */       pc = pc.getSuperclass();
/* 290:    */     }
/* 291:356 */     if (jk != -1) {
/* 292:357 */       throw new AssertionFailure("Tablespan does not match height of joined-subclass hiearchy.");
/* 293:    */     }
/* 294:360 */     joinIter = persistentClass.getJoinClosureIterator();
/* 295:361 */     int j = this.coreTableSpan;
/* 296:362 */     while (joinIter.hasNext())
/* 297:    */     {
/* 298:363 */       Join join = (Join)joinIter.next();
/* 299:    */       
/* 300:365 */       this.customSQLInsert[j] = join.getCustomSQLInsert();
/* 301:366 */       this.insertCallable[j] = ((this.customSQLInsert[j] != null) && (join.isCustomInsertCallable()) ? 1 : false);
/* 302:367 */       this.insertResultCheckStyles[j] = (join.getCustomSQLInsertCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLInsert[j], this.insertCallable[j]) : join.getCustomSQLInsertCheckStyle());
/* 303:    */       
/* 304:    */ 
/* 305:370 */       this.customSQLUpdate[j] = join.getCustomSQLUpdate();
/* 306:371 */       this.updateCallable[j] = ((this.customSQLUpdate[j] != null) && (join.isCustomUpdateCallable()) ? 1 : false);
/* 307:372 */       this.updateResultCheckStyles[j] = (join.getCustomSQLUpdateCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLUpdate[j], this.updateCallable[j]) : join.getCustomSQLUpdateCheckStyle());
/* 308:    */       
/* 309:    */ 
/* 310:375 */       this.customSQLDelete[j] = join.getCustomSQLDelete();
/* 311:376 */       this.deleteCallable[j] = ((this.customSQLDelete[j] != null) && (join.isCustomDeleteCallable()) ? 1 : false);
/* 312:377 */       this.deleteResultCheckStyles[j] = (join.getCustomSQLDeleteCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLDelete[j], this.deleteCallable[j]) : join.getCustomSQLDeleteCheckStyle());
/* 313:    */       
/* 314:    */ 
/* 315:380 */       j++;
/* 316:    */     }
/* 317:384 */     int hydrateSpan = getPropertySpan();
/* 318:385 */     this.naturalOrderPropertyTableNumbers = new int[hydrateSpan];
/* 319:386 */     this.propertyTableNumbers = new int[hydrateSpan];
/* 320:387 */     Iterator iter = persistentClass.getPropertyClosureIterator();
/* 321:388 */     int i = 0;
/* 322:389 */     while (iter.hasNext())
/* 323:    */     {
/* 324:390 */       Property prop = (Property)iter.next();
/* 325:391 */       String tabname = prop.getValue().getTable().getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/* 326:    */       
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:396 */       this.propertyTableNumbers[i] = getTableId(tabname, this.tableNames);
/* 331:397 */       this.naturalOrderPropertyTableNumbers[i] = getTableId(tabname, this.naturalOrderTableNames);
/* 332:398 */       i++;
/* 333:    */     }
/* 334:405 */     ArrayList columnTableNumbers = new ArrayList();
/* 335:406 */     ArrayList formulaTableNumbers = new ArrayList();
/* 336:407 */     ArrayList propTableNumbers = new ArrayList();
/* 337:    */     
/* 338:409 */     iter = persistentClass.getSubclassPropertyClosureIterator();
/* 339:410 */     while (iter.hasNext())
/* 340:    */     {
/* 341:411 */       Property prop = (Property)iter.next();
/* 342:412 */       Table tab = prop.getValue().getTable();
/* 343:413 */       String tabname = tab.getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/* 344:    */       
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:418 */       Integer tabnum = Integer.valueOf(getTableId(tabname, this.subclassTableNameClosure));
/* 349:419 */       propTableNumbers.add(tabnum);
/* 350:    */       
/* 351:421 */       Iterator citer = prop.getColumnIterator();
/* 352:422 */       while (citer.hasNext())
/* 353:    */       {
/* 354:423 */         Selectable thing = (Selectable)citer.next();
/* 355:424 */         if (thing.isFormula()) {
/* 356:425 */           formulaTableNumbers.add(tabnum);
/* 357:    */         } else {
/* 358:428 */           columnTableNumbers.add(tabnum);
/* 359:    */         }
/* 360:    */       }
/* 361:    */     }
/* 362:434 */     this.subclassColumnTableNumberClosure = ArrayHelper.toIntArray(columnTableNumbers);
/* 363:435 */     this.subclassPropertyTableNumberClosure = ArrayHelper.toIntArray(propTableNumbers);
/* 364:436 */     this.subclassFormulaTableNumberClosure = ArrayHelper.toIntArray(formulaTableNumbers);
/* 365:    */     
/* 366:    */ 
/* 367:    */ 
/* 368:440 */     int subclassSpan = persistentClass.getSubclassSpan() + 1;
/* 369:441 */     this.subclassClosure = new String[subclassSpan];
/* 370:442 */     this.subclassClosure[(subclassSpan - 1)] = getEntityName();
/* 371:443 */     if (persistentClass.isPolymorphic())
/* 372:    */     {
/* 373:444 */       this.subclassesByDiscriminatorValue.put(this.discriminatorValue, getEntityName());
/* 374:445 */       this.discriminatorValues = new String[subclassSpan];
/* 375:446 */       this.discriminatorValues[(subclassSpan - 1)] = this.discriminatorSQLString;
/* 376:447 */       this.notNullColumnTableNumbers = new int[subclassSpan];
/* 377:448 */       int id = getTableId(persistentClass.getTable().getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName()), this.subclassTableNameClosure);
/* 378:    */       
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:456 */       this.notNullColumnTableNumbers[(subclassSpan - 1)] = id;
/* 386:457 */       this.notNullColumnNames = new String[subclassSpan];
/* 387:458 */       this.notNullColumnNames[(subclassSpan - 1)] = this.subclassTableKeyColumnClosure[id][0];
/* 388:    */     }
/* 389:    */     else
/* 390:    */     {
/* 391:461 */       this.discriminatorValues = null;
/* 392:462 */       this.notNullColumnTableNumbers = null;
/* 393:463 */       this.notNullColumnNames = null;
/* 394:    */     }
/* 395:466 */     iter = persistentClass.getSubclassIterator();
/* 396:467 */     int k = 0;
/* 397:468 */     while (iter.hasNext())
/* 398:    */     {
/* 399:469 */       Subclass sc = (Subclass)iter.next();
/* 400:470 */       this.subclassClosure[k] = sc.getEntityName();
/* 401:    */       try
/* 402:    */       {
/* 403:472 */         if (persistentClass.isPolymorphic())
/* 404:    */         {
/* 405:476 */           Integer subclassId = Integer.valueOf(sc.getSubclassId());
/* 406:477 */           this.subclassesByDiscriminatorValue.put(subclassId, sc.getEntityName());
/* 407:478 */           this.discriminatorValues[k] = subclassId.toString();
/* 408:479 */           int id = getTableId(sc.getTable().getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName()), this.subclassTableNameClosure);
/* 409:    */           
/* 410:    */ 
/* 411:    */ 
/* 412:    */ 
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:487 */           this.notNullColumnTableNumbers[k] = id;
/* 417:488 */           this.notNullColumnNames[k] = this.subclassTableKeyColumnClosure[id][0];
/* 418:    */         }
/* 419:    */       }
/* 420:    */       catch (Exception e)
/* 421:    */       {
/* 422:492 */         throw new MappingException("Error parsing discriminator value", e);
/* 423:    */       }
/* 424:494 */       k++;
/* 425:    */     }
/* 426:497 */     initLockers();
/* 427:    */     
/* 428:499 */     initSubclassPropertyAliasesMap(persistentClass);
/* 429:    */     
/* 430:501 */     postConstruct(mapping);
/* 431:    */   }
/* 432:    */   
/* 433:    */   public JoinedSubclassEntityPersister(EntityBinding entityBinding, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping mapping)
/* 434:    */     throws HibernateException
/* 435:    */   {
/* 436:510 */     super(entityBinding, cacheAccessStrategy, factory);
/* 437:    */     
/* 438:512 */     this.tableSpan = -1;
/* 439:513 */     this.tableNames = null;
/* 440:514 */     this.naturalOrderTableNames = null;
/* 441:515 */     this.tableKeyColumns = ((String[][])null);
/* 442:516 */     this.tableKeyColumnReaders = ((String[][])null);
/* 443:517 */     this.tableKeyColumnReaderTemplates = ((String[][])null);
/* 444:518 */     this.naturalOrderTableKeyColumns = ((String[][])null);
/* 445:519 */     this.naturalOrderTableKeyColumnReaders = ((String[][])null);
/* 446:520 */     this.naturalOrderTableKeyColumnReaderTemplates = ((String[][])null);
/* 447:521 */     this.naturalOrderCascadeDeleteEnabled = null;
/* 448:522 */     this.spaces = null;
/* 449:523 */     this.subclassClosure = null;
/* 450:524 */     this.subclassTableNameClosure = null;
/* 451:525 */     this.subclassTableKeyColumnClosure = ((String[][])null);
/* 452:526 */     this.isClassOrSuperclassTable = null;
/* 453:527 */     this.naturalOrderPropertyTableNumbers = null;
/* 454:528 */     this.propertyTableNumbers = null;
/* 455:529 */     this.subclassPropertyTableNumberClosure = null;
/* 456:530 */     this.subclassColumnTableNumberClosure = null;
/* 457:531 */     this.subclassFormulaTableNumberClosure = null;
/* 458:532 */     this.subclassTableSequentialSelect = null;
/* 459:533 */     this.subclassTableIsLazyClosure = null;
/* 460:534 */     this.discriminatorValues = null;
/* 461:535 */     this.notNullColumnNames = null;
/* 462:536 */     this.notNullColumnTableNumbers = null;
/* 463:537 */     this.constraintOrderedTableNames = null;
/* 464:538 */     this.constraintOrderedKeyColumnNames = ((String[][])null);
/* 465:539 */     this.discriminatorValue = null;
/* 466:540 */     this.discriminatorSQLString = null;
/* 467:541 */     this.coreTableSpan = -1;
/* 468:542 */     this.isNullableTable = null;
/* 469:    */   }
/* 470:    */   
/* 471:    */   protected boolean isNullableTable(int j)
/* 472:    */   {
/* 473:546 */     if (j < this.coreTableSpan) {
/* 474:547 */       return false;
/* 475:    */     }
/* 476:549 */     return this.isNullableTable[(j - this.coreTableSpan)];
/* 477:    */   }
/* 478:    */   
/* 479:    */   protected boolean isSubclassTableSequentialSelect(int j)
/* 480:    */   {
/* 481:553 */     return (this.subclassTableSequentialSelect[j] != 0) && (this.isClassOrSuperclassTable[j] == 0);
/* 482:    */   }
/* 483:    */   
/* 484:    */   public String getSubclassPropertyTableName(int i)
/* 485:    */   {
/* 486:563 */     return this.subclassTableNameClosure[this.subclassPropertyTableNumberClosure[i]];
/* 487:    */   }
/* 488:    */   
/* 489:    */   public Type getDiscriminatorType()
/* 490:    */   {
/* 491:567 */     return StandardBasicTypes.INTEGER;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public Object getDiscriminatorValue()
/* 495:    */   {
/* 496:571 */     return this.discriminatorValue;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public String getDiscriminatorSQLValue()
/* 500:    */   {
/* 501:575 */     return this.discriminatorSQLString;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public String getSubclassForDiscriminatorValue(Object value)
/* 505:    */   {
/* 506:579 */     return (String)this.subclassesByDiscriminatorValue.get(value);
/* 507:    */   }
/* 508:    */   
/* 509:    */   public Serializable[] getPropertySpaces()
/* 510:    */   {
/* 511:583 */     return this.spaces;
/* 512:    */   }
/* 513:    */   
/* 514:    */   protected String getTableName(int j)
/* 515:    */   {
/* 516:588 */     return this.naturalOrderTableNames[j];
/* 517:    */   }
/* 518:    */   
/* 519:    */   protected String[] getKeyColumns(int j)
/* 520:    */   {
/* 521:592 */     return this.naturalOrderTableKeyColumns[j];
/* 522:    */   }
/* 523:    */   
/* 524:    */   protected boolean isTableCascadeDeleteEnabled(int j)
/* 525:    */   {
/* 526:596 */     return this.naturalOrderCascadeDeleteEnabled[j];
/* 527:    */   }
/* 528:    */   
/* 529:    */   protected boolean isPropertyOfTable(int property, int j)
/* 530:    */   {
/* 531:600 */     return this.naturalOrderPropertyTableNumbers[property] == j;
/* 532:    */   }
/* 533:    */   
/* 534:    */   private static final void reverse(Object[] objects, int len)
/* 535:    */   {
/* 536:629 */     Object[] temp = new Object[len];
/* 537:630 */     for (int i = 0; i < len; i++) {
/* 538:631 */       temp[i] = objects[(len - i - 1)];
/* 539:    */     }
/* 540:633 */     for (int i = 0; i < len; i++) {
/* 541:634 */       objects[i] = temp[i];
/* 542:    */     }
/* 543:    */   }
/* 544:    */   
/* 545:    */   private static String[] reverse(String[] objects, int n)
/* 546:    */   {
/* 547:649 */     int size = objects.length;
/* 548:650 */     String[] temp = new String[size];
/* 549:652 */     for (int i = 0; i < n; i++) {
/* 550:653 */       temp[i] = objects[(n - i - 1)];
/* 551:    */     }
/* 552:656 */     for (int i = n; i < size; i++) {
/* 553:657 */       temp[i] = objects[i];
/* 554:    */     }
/* 555:660 */     return temp;
/* 556:    */   }
/* 557:    */   
/* 558:    */   private static String[][] reverse(String[][] objects, int n)
/* 559:    */   {
/* 560:672 */     int size = objects.length;
/* 561:673 */     String[][] temp = new String[size][];
/* 562:674 */     for (int i = 0; i < n; i++) {
/* 563:675 */       temp[i] = objects[(n - i - 1)];
/* 564:    */     }
/* 565:678 */     for (int i = n; i < size; i++) {
/* 566:679 */       temp[i] = objects[i];
/* 567:    */     }
/* 568:682 */     return temp;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public String fromTableFragment(String alias)
/* 572:    */   {
/* 573:687 */     return getTableName() + ' ' + alias;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public String getTableName()
/* 577:    */   {
/* 578:691 */     return this.tableNames[0];
/* 579:    */   }
/* 580:    */   
/* 581:    */   private static int getTableId(String tableName, String[] tables)
/* 582:    */   {
/* 583:695 */     for (int j = 0; j < tables.length; j++) {
/* 584:696 */       if (tableName.equals(tables[j])) {
/* 585:697 */         return j;
/* 586:    */       }
/* 587:    */     }
/* 588:700 */     throw new AssertionFailure("Table " + tableName + " not found");
/* 589:    */   }
/* 590:    */   
/* 591:    */   public void addDiscriminatorToSelect(SelectFragment select, String name, String suffix)
/* 592:    */   {
/* 593:704 */     if (hasSubclasses()) {
/* 594:705 */       select.setExtraSelectList(discriminatorFragment(name), getDiscriminatorAlias());
/* 595:    */     }
/* 596:    */   }
/* 597:    */   
/* 598:    */   private CaseFragment discriminatorFragment(String alias)
/* 599:    */   {
/* 600:710 */     CaseFragment cases = getFactory().getDialect().createCaseFragment();
/* 601:712 */     for (int i = 0; i < this.discriminatorValues.length; i++) {
/* 602:713 */       cases.addWhenColumnNotNull(generateTableAlias(alias, this.notNullColumnTableNumbers[i]), this.notNullColumnNames[i], this.discriminatorValues[i]);
/* 603:    */     }
/* 604:720 */     return cases;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public String filterFragment(String alias)
/* 608:    */   {
/* 609:724 */     return hasWhere() ? " and " + getSQLWhereString(generateFilterConditionAlias(alias)) : "";
/* 610:    */   }
/* 611:    */   
/* 612:    */   public String generateFilterConditionAlias(String rootAlias)
/* 613:    */   {
/* 614:730 */     return generateTableAlias(rootAlias, this.tableSpan - 1);
/* 615:    */   }
/* 616:    */   
/* 617:    */   public String[] getIdentifierColumnNames()
/* 618:    */   {
/* 619:734 */     return this.tableKeyColumns[0];
/* 620:    */   }
/* 621:    */   
/* 622:    */   public String[] getIdentifierColumnReaderTemplates()
/* 623:    */   {
/* 624:738 */     return this.tableKeyColumnReaderTemplates[0];
/* 625:    */   }
/* 626:    */   
/* 627:    */   public String[] getIdentifierColumnReaders()
/* 628:    */   {
/* 629:742 */     return this.tableKeyColumnReaders[0];
/* 630:    */   }
/* 631:    */   
/* 632:    */   public String[] toColumns(String alias, String propertyName)
/* 633:    */     throws QueryException
/* 634:    */   {
/* 635:746 */     if ("class".equals(propertyName)) {
/* 636:753 */       return new String[] { discriminatorFragment(alias).toFragmentString() };
/* 637:    */     }
/* 638:756 */     return super.toColumns(alias, propertyName);
/* 639:    */   }
/* 640:    */   
/* 641:    */   protected int[] getPropertyTableNumbersInSelect()
/* 642:    */   {
/* 643:761 */     return this.propertyTableNumbers;
/* 644:    */   }
/* 645:    */   
/* 646:    */   protected int getSubclassPropertyTableNumber(int i)
/* 647:    */   {
/* 648:765 */     return this.subclassPropertyTableNumberClosure[i];
/* 649:    */   }
/* 650:    */   
/* 651:    */   public int getTableSpan()
/* 652:    */   {
/* 653:769 */     return this.tableSpan;
/* 654:    */   }
/* 655:    */   
/* 656:    */   public boolean isMultiTable()
/* 657:    */   {
/* 658:773 */     return true;
/* 659:    */   }
/* 660:    */   
/* 661:    */   protected int[] getSubclassColumnTableNumberClosure()
/* 662:    */   {
/* 663:777 */     return this.subclassColumnTableNumberClosure;
/* 664:    */   }
/* 665:    */   
/* 666:    */   protected int[] getSubclassFormulaTableNumberClosure()
/* 667:    */   {
/* 668:781 */     return this.subclassFormulaTableNumberClosure;
/* 669:    */   }
/* 670:    */   
/* 671:    */   protected int[] getPropertyTableNumbers()
/* 672:    */   {
/* 673:785 */     return this.naturalOrderPropertyTableNumbers;
/* 674:    */   }
/* 675:    */   
/* 676:    */   protected String[] getSubclassTableKeyColumns(int j)
/* 677:    */   {
/* 678:789 */     return this.subclassTableKeyColumnClosure[j];
/* 679:    */   }
/* 680:    */   
/* 681:    */   public String getSubclassTableName(int j)
/* 682:    */   {
/* 683:793 */     return this.subclassTableNameClosure[j];
/* 684:    */   }
/* 685:    */   
/* 686:    */   public int getSubclassTableSpan()
/* 687:    */   {
/* 688:797 */     return this.subclassTableNameClosure.length;
/* 689:    */   }
/* 690:    */   
/* 691:    */   protected boolean isSubclassTableLazy(int j)
/* 692:    */   {
/* 693:801 */     return this.subclassTableIsLazyClosure[j];
/* 694:    */   }
/* 695:    */   
/* 696:    */   protected boolean isClassOrSuperclassTable(int j)
/* 697:    */   {
/* 698:806 */     return this.isClassOrSuperclassTable[j];
/* 699:    */   }
/* 700:    */   
/* 701:    */   public String getPropertyTableName(String propertyName)
/* 702:    */   {
/* 703:810 */     Integer index = getEntityMetamodel().getPropertyIndexOrNull(propertyName);
/* 704:811 */     if (index == null) {
/* 705:812 */       return null;
/* 706:    */     }
/* 707:814 */     return this.tableNames[this.propertyTableNumbers[index.intValue()]];
/* 708:    */   }
/* 709:    */   
/* 710:    */   public String[] getConstraintOrderedTableNameClosure()
/* 711:    */   {
/* 712:818 */     return this.constraintOrderedTableNames;
/* 713:    */   }
/* 714:    */   
/* 715:    */   public String[][] getContraintOrderedTableKeyColumnClosure()
/* 716:    */   {
/* 717:822 */     return this.constraintOrderedKeyColumnNames;
/* 718:    */   }
/* 719:    */   
/* 720:    */   public String getRootTableName()
/* 721:    */   {
/* 722:826 */     return this.naturalOrderTableNames[0];
/* 723:    */   }
/* 724:    */   
/* 725:    */   public String getRootTableAlias(String drivingAlias)
/* 726:    */   {
/* 727:830 */     return generateTableAlias(drivingAlias, getTableId(getRootTableName(), this.tableNames));
/* 728:    */   }
/* 729:    */   
/* 730:    */   public Queryable.Declarer getSubclassPropertyDeclarer(String propertyPath)
/* 731:    */   {
/* 732:834 */     if ("class".equals(propertyPath)) {
/* 733:836 */       return Queryable.Declarer.SUBCLASS;
/* 734:    */     }
/* 735:838 */     return super.getSubclassPropertyDeclarer(propertyPath);
/* 736:    */   }
/* 737:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.JoinedSubclassEntityPersister
 * JD-Core Version:    0.7.0.1
 */