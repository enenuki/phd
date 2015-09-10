/*   1:    */ package org.hibernate.persister.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.LinkedHashSet;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.hibernate.AssertionFailure;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.LockMode;
/*  14:    */ import org.hibernate.MappingException;
/*  15:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  16:    */ import org.hibernate.cfg.Settings;
/*  17:    */ import org.hibernate.dialect.Dialect;
/*  18:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  19:    */ import org.hibernate.engine.spi.Mapping;
/*  20:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  21:    */ import org.hibernate.id.IdentityGenerator;
/*  22:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  23:    */ import org.hibernate.internal.util.collections.JoinedIterator;
/*  24:    */ import org.hibernate.internal.util.collections.SingletonIterator;
/*  25:    */ import org.hibernate.mapping.Column;
/*  26:    */ import org.hibernate.mapping.PersistentClass;
/*  27:    */ import org.hibernate.mapping.PrimaryKey;
/*  28:    */ import org.hibernate.mapping.Subclass;
/*  29:    */ import org.hibernate.mapping.Table;
/*  30:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  31:    */ import org.hibernate.sql.SelectFragment;
/*  32:    */ import org.hibernate.sql.SimpleSelect;
/*  33:    */ import org.hibernate.type.StandardBasicTypes;
/*  34:    */ import org.hibernate.type.Type;
/*  35:    */ 
/*  36:    */ public class UnionSubclassEntityPersister
/*  37:    */   extends AbstractEntityPersister
/*  38:    */ {
/*  39:    */   private final String subquery;
/*  40:    */   private final String tableName;
/*  41:    */   private final String[] subclassClosure;
/*  42:    */   private final String[] spaces;
/*  43:    */   private final String[] subclassSpaces;
/*  44:    */   private final Object discriminatorValue;
/*  45:    */   private final String discriminatorSQLValue;
/*  46: 75 */   private final Map subclassByDiscriminatorValue = new HashMap();
/*  47:    */   private final String[] constraintOrderedTableNames;
/*  48:    */   private final String[][] constraintOrderedKeyColumnNames;
/*  49:    */   
/*  50:    */   public UnionSubclassEntityPersister(PersistentClass persistentClass, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping mapping)
/*  51:    */     throws HibernateException
/*  52:    */   {
/*  53: 88 */     super(persistentClass, cacheAccessStrategy, factory);
/*  54: 90 */     if ((getIdentifierGenerator() instanceof IdentityGenerator)) {
/*  55: 91 */       throw new MappingException("Cannot use identity column key generation with <union-subclass> mapping for: " + getEntityName());
/*  56:    */     }
/*  57: 99 */     this.tableName = persistentClass.getTable().getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:113 */     boolean callable = false;
/*  72:114 */     ExecuteUpdateResultCheckStyle checkStyle = null;
/*  73:115 */     String sql = persistentClass.getCustomSQLInsert();
/*  74:116 */     callable = (sql != null) && (persistentClass.isCustomInsertCallable());
/*  75:117 */     checkStyle = persistentClass.getCustomSQLInsertCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(sql, callable) : sql == null ? ExecuteUpdateResultCheckStyle.COUNT : persistentClass.getCustomSQLInsertCheckStyle();
/*  76:    */     
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:122 */     this.customSQLInsert = new String[] { sql };
/*  81:123 */     this.insertCallable = new boolean[] { callable };
/*  82:124 */     this.insertResultCheckStyles = new ExecuteUpdateResultCheckStyle[] { checkStyle };
/*  83:    */     
/*  84:126 */     sql = persistentClass.getCustomSQLUpdate();
/*  85:127 */     callable = (sql != null) && (persistentClass.isCustomUpdateCallable());
/*  86:128 */     checkStyle = persistentClass.getCustomSQLUpdateCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(sql, callable) : sql == null ? ExecuteUpdateResultCheckStyle.COUNT : persistentClass.getCustomSQLUpdateCheckStyle();
/*  87:    */     
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:133 */     this.customSQLUpdate = new String[] { sql };
/*  92:134 */     this.updateCallable = new boolean[] { callable };
/*  93:135 */     this.updateResultCheckStyles = new ExecuteUpdateResultCheckStyle[] { checkStyle };
/*  94:    */     
/*  95:137 */     sql = persistentClass.getCustomSQLDelete();
/*  96:138 */     callable = (sql != null) && (persistentClass.isCustomDeleteCallable());
/*  97:139 */     checkStyle = persistentClass.getCustomSQLDeleteCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(sql, callable) : sql == null ? ExecuteUpdateResultCheckStyle.COUNT : persistentClass.getCustomSQLDeleteCheckStyle();
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:144 */     this.customSQLDelete = new String[] { sql };
/* 103:145 */     this.deleteCallable = new boolean[] { callable };
/* 104:146 */     this.deleteResultCheckStyles = new ExecuteUpdateResultCheckStyle[] { checkStyle };
/* 105:    */     
/* 106:148 */     this.discriminatorValue = Integer.valueOf(persistentClass.getSubclassId());
/* 107:149 */     this.discriminatorSQLValue = String.valueOf(persistentClass.getSubclassId());
/* 108:    */     
/* 109:    */ 
/* 110:    */ 
/* 111:153 */     int subclassSpan = persistentClass.getSubclassSpan() + 1;
/* 112:154 */     this.subclassClosure = new String[subclassSpan];
/* 113:155 */     this.subclassClosure[0] = getEntityName();
/* 114:    */     
/* 115:    */ 
/* 116:158 */     this.subclassByDiscriminatorValue.put(Integer.valueOf(persistentClass.getSubclassId()), persistentClass.getEntityName());
/* 117:162 */     if (persistentClass.isPolymorphic())
/* 118:    */     {
/* 119:163 */       Iterator iter = persistentClass.getSubclassIterator();
/* 120:164 */       int k = 1;
/* 121:165 */       while (iter.hasNext())
/* 122:    */       {
/* 123:166 */         Subclass sc = (Subclass)iter.next();
/* 124:167 */         this.subclassClosure[(k++)] = sc.getEntityName();
/* 125:168 */         this.subclassByDiscriminatorValue.put(Integer.valueOf(sc.getSubclassId()), sc.getEntityName());
/* 126:    */       }
/* 127:    */     }
/* 128:176 */     int spacesSize = 1 + persistentClass.getSynchronizedTables().size();
/* 129:177 */     this.spaces = new String[spacesSize];
/* 130:178 */     this.spaces[0] = this.tableName;
/* 131:179 */     Iterator iter = persistentClass.getSynchronizedTables().iterator();
/* 132:180 */     for (int i = 1; i < spacesSize; i++) {
/* 133:181 */       this.spaces[i] = ((String)iter.next());
/* 134:    */     }
/* 135:184 */     HashSet subclassTables = new HashSet();
/* 136:185 */     iter = persistentClass.getSubclassTableClosureIterator();
/* 137:186 */     while (iter.hasNext())
/* 138:    */     {
/* 139:187 */       Table table = (Table)iter.next();
/* 140:188 */       subclassTables.add(table.getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName()));
/* 141:    */     }
/* 142:194 */     this.subclassSpaces = ArrayHelper.toStringArray(subclassTables);
/* 143:    */     
/* 144:196 */     this.subquery = generateSubquery(persistentClass, mapping);
/* 145:198 */     if (isMultiTable())
/* 146:    */     {
/* 147:199 */       int idColumnSpan = getIdentifierColumnSpan();
/* 148:200 */       ArrayList tableNames = new ArrayList();
/* 149:201 */       ArrayList keyColumns = new ArrayList();
/* 150:202 */       if (!isAbstract())
/* 151:    */       {
/* 152:203 */         tableNames.add(this.tableName);
/* 153:204 */         keyColumns.add(getIdentifierColumnNames());
/* 154:    */       }
/* 155:206 */       iter = persistentClass.getSubclassTableClosureIterator();
/* 156:207 */       while (iter.hasNext())
/* 157:    */       {
/* 158:208 */         Table tab = (Table)iter.next();
/* 159:209 */         if (!tab.isAbstractUnionTable())
/* 160:    */         {
/* 161:210 */           String tableName = tab.getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/* 162:    */           
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:215 */           tableNames.add(tableName);
/* 167:216 */           String[] key = new String[idColumnSpan];
/* 168:217 */           Iterator citer = tab.getPrimaryKey().getColumnIterator();
/* 169:218 */           for (int k = 0; k < idColumnSpan; k++) {
/* 170:219 */             key[k] = ((Column)citer.next()).getQuotedName(factory.getDialect());
/* 171:    */           }
/* 172:221 */           keyColumns.add(key);
/* 173:    */         }
/* 174:    */       }
/* 175:225 */       this.constraintOrderedTableNames = ArrayHelper.toStringArray(tableNames);
/* 176:226 */       this.constraintOrderedKeyColumnNames = ArrayHelper.to2DStringArray(keyColumns);
/* 177:    */     }
/* 178:    */     else
/* 179:    */     {
/* 180:229 */       this.constraintOrderedTableNames = new String[] { this.tableName };
/* 181:230 */       this.constraintOrderedKeyColumnNames = new String[][] { getIdentifierColumnNames() };
/* 182:    */     }
/* 183:233 */     initLockers();
/* 184:    */     
/* 185:235 */     initSubclassPropertyAliasesMap(persistentClass);
/* 186:    */     
/* 187:237 */     postConstruct(mapping);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public UnionSubclassEntityPersister(EntityBinding entityBinding, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping mapping)
/* 191:    */     throws HibernateException
/* 192:    */   {
/* 193:246 */     super(entityBinding, cacheAccessStrategy, factory);
/* 194:    */     
/* 195:248 */     this.subquery = null;
/* 196:249 */     this.tableName = null;
/* 197:250 */     this.subclassClosure = null;
/* 198:251 */     this.spaces = null;
/* 199:252 */     this.subclassSpaces = null;
/* 200:253 */     this.discriminatorValue = null;
/* 201:254 */     this.discriminatorSQLValue = null;
/* 202:255 */     this.constraintOrderedTableNames = null;
/* 203:256 */     this.constraintOrderedKeyColumnNames = ((String[][])null);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Serializable[] getQuerySpaces()
/* 207:    */   {
/* 208:260 */     return this.subclassSpaces;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String getTableName()
/* 212:    */   {
/* 213:264 */     return this.subquery;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Type getDiscriminatorType()
/* 217:    */   {
/* 218:268 */     return StandardBasicTypes.INTEGER;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Object getDiscriminatorValue()
/* 222:    */   {
/* 223:272 */     return this.discriminatorValue;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String getDiscriminatorSQLValue()
/* 227:    */   {
/* 228:276 */     return this.discriminatorSQLValue;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String[] getSubclassClosure()
/* 232:    */   {
/* 233:280 */     return this.subclassClosure;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String getSubclassForDiscriminatorValue(Object value)
/* 237:    */   {
/* 238:284 */     return (String)this.subclassByDiscriminatorValue.get(value);
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Serializable[] getPropertySpaces()
/* 242:    */   {
/* 243:288 */     return this.spaces;
/* 244:    */   }
/* 245:    */   
/* 246:    */   protected boolean isDiscriminatorFormula()
/* 247:    */   {
/* 248:292 */     return false;
/* 249:    */   }
/* 250:    */   
/* 251:    */   protected String generateSelectString(LockMode lockMode)
/* 252:    */   {
/* 253:299 */     SimpleSelect select = new SimpleSelect(getFactory().getDialect()).setLockMode(lockMode).setTableName(getTableName()).addColumns(getIdentifierColumnNames()).addColumns(getSubclassColumnClosure(), getSubclassColumnAliasClosure(), getSubclassColumnLazyiness()).addColumns(getSubclassFormulaClosure(), getSubclassFormulaAliasClosure(), getSubclassFormulaLazyiness());
/* 254:314 */     if (hasSubclasses()) {
/* 255:315 */       if (isDiscriminatorFormula()) {
/* 256:316 */         select.addColumn(getDiscriminatorFormula(), getDiscriminatorAlias());
/* 257:    */       } else {
/* 258:319 */         select.addColumn(getDiscriminatorColumnName(), getDiscriminatorAlias());
/* 259:    */       }
/* 260:    */     }
/* 261:322 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 262:323 */       select.setComment("load " + getEntityName());
/* 263:    */     }
/* 264:325 */     return select.addCondition(getIdentifierColumnNames(), "=?").toStatementString();
/* 265:    */   }
/* 266:    */   
/* 267:    */   protected String getDiscriminatorFormula()
/* 268:    */   {
/* 269:329 */     return null;
/* 270:    */   }
/* 271:    */   
/* 272:    */   protected String getTableName(int j)
/* 273:    */   {
/* 274:333 */     return this.tableName;
/* 275:    */   }
/* 276:    */   
/* 277:    */   protected String[] getKeyColumns(int j)
/* 278:    */   {
/* 279:337 */     return getIdentifierColumnNames();
/* 280:    */   }
/* 281:    */   
/* 282:    */   protected boolean isTableCascadeDeleteEnabled(int j)
/* 283:    */   {
/* 284:341 */     return false;
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected boolean isPropertyOfTable(int property, int j)
/* 288:    */   {
/* 289:345 */     return true;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String fromTableFragment(String name)
/* 293:    */   {
/* 294:351 */     return getTableName() + ' ' + name;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public String filterFragment(String name)
/* 298:    */   {
/* 299:355 */     return hasWhere() ? " and " + getSQLWhereString(name) : "";
/* 300:    */   }
/* 301:    */   
/* 302:    */   public String getSubclassPropertyTableName(int i)
/* 303:    */   {
/* 304:361 */     return getTableName();
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected void addDiscriminatorToSelect(SelectFragment select, String name, String suffix)
/* 308:    */   {
/* 309:365 */     select.addColumn(name, getDiscriminatorColumnName(), getDiscriminatorAlias());
/* 310:    */   }
/* 311:    */   
/* 312:    */   protected int[] getPropertyTableNumbersInSelect()
/* 313:    */   {
/* 314:369 */     return new int[getPropertySpan()];
/* 315:    */   }
/* 316:    */   
/* 317:    */   protected int getSubclassPropertyTableNumber(int i)
/* 318:    */   {
/* 319:373 */     return 0;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public int getSubclassPropertyTableNumber(String propertyName)
/* 323:    */   {
/* 324:377 */     return 0;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean isMultiTable()
/* 328:    */   {
/* 329:382 */     return (isAbstract()) || (hasSubclasses());
/* 330:    */   }
/* 331:    */   
/* 332:    */   public int getTableSpan()
/* 333:    */   {
/* 334:386 */     return 1;
/* 335:    */   }
/* 336:    */   
/* 337:    */   protected int[] getSubclassColumnTableNumberClosure()
/* 338:    */   {
/* 339:390 */     return new int[getSubclassColumnClosure().length];
/* 340:    */   }
/* 341:    */   
/* 342:    */   protected int[] getSubclassFormulaTableNumberClosure()
/* 343:    */   {
/* 344:394 */     return new int[getSubclassFormulaClosure().length];
/* 345:    */   }
/* 346:    */   
/* 347:    */   protected boolean[] getTableHasColumns()
/* 348:    */   {
/* 349:398 */     return new boolean[] { true };
/* 350:    */   }
/* 351:    */   
/* 352:    */   protected int[] getPropertyTableNumbers()
/* 353:    */   {
/* 354:402 */     return new int[getPropertySpan()];
/* 355:    */   }
/* 356:    */   
/* 357:    */   protected String generateSubquery(PersistentClass model, Mapping mapping)
/* 358:    */   {
/* 359:407 */     Dialect dialect = getFactory().getDialect();
/* 360:408 */     Settings settings = getFactory().getSettings();
/* 361:410 */     if (!model.hasSubclasses()) {
/* 362:411 */       return model.getTable().getQualifiedName(dialect, settings.getDefaultCatalogName(), settings.getDefaultSchemaName());
/* 363:    */     }
/* 364:418 */     HashSet columns = new LinkedHashSet();
/* 365:419 */     Iterator titer = model.getSubclassTableClosureIterator();
/* 366:420 */     while (titer.hasNext())
/* 367:    */     {
/* 368:421 */       Table table = (Table)titer.next();
/* 369:422 */       if (!table.isAbstractUnionTable())
/* 370:    */       {
/* 371:423 */         Iterator citer = table.getColumnIterator();
/* 372:424 */         while (citer.hasNext()) {
/* 373:424 */           columns.add(citer.next());
/* 374:    */         }
/* 375:    */       }
/* 376:    */     }
/* 377:428 */     StringBuilder buf = new StringBuilder().append("( ");
/* 378:    */     
/* 379:    */ 
/* 380:431 */     Iterator siter = new JoinedIterator(new SingletonIterator(model), model.getSubclassIterator());
/* 381:436 */     while (siter.hasNext())
/* 382:    */     {
/* 383:437 */       PersistentClass clazz = (PersistentClass)siter.next();
/* 384:438 */       Table table = clazz.getTable();
/* 385:439 */       if (!table.isAbstractUnionTable())
/* 386:    */       {
/* 387:441 */         buf.append("select ");
/* 388:442 */         Iterator citer = columns.iterator();
/* 389:443 */         while (citer.hasNext())
/* 390:    */         {
/* 391:444 */           Column col = (Column)citer.next();
/* 392:445 */           if (!table.containsColumn(col))
/* 393:    */           {
/* 394:446 */             int sqlType = col.getSqlTypeCode(mapping);
/* 395:447 */             buf.append(dialect.getSelectClauseNullString(sqlType)).append(" as ");
/* 396:    */           }
/* 397:450 */           buf.append(col.getName());
/* 398:451 */           buf.append(", ");
/* 399:    */         }
/* 400:453 */         buf.append(clazz.getSubclassId()).append(" as clazz_");
/* 401:    */         
/* 402:455 */         buf.append(" from ").append(table.getQualifiedName(dialect, settings.getDefaultCatalogName(), settings.getDefaultSchemaName()));
/* 403:    */         
/* 404:    */ 
/* 405:    */ 
/* 406:    */ 
/* 407:    */ 
/* 408:461 */         buf.append(" union ");
/* 409:462 */         if (dialect.supportsUnionAll()) {
/* 410:463 */           buf.append("all ");
/* 411:    */         }
/* 412:    */       }
/* 413:    */     }
/* 414:468 */     if (buf.length() > 2) {
/* 415:470 */       buf.setLength(buf.length() - (dialect.supportsUnionAll() ? 11 : 7));
/* 416:    */     }
/* 417:473 */     return " )";
/* 418:    */   }
/* 419:    */   
/* 420:    */   protected String[] getSubclassTableKeyColumns(int j)
/* 421:    */   {
/* 422:477 */     if (j != 0) {
/* 423:477 */       throw new AssertionFailure("only one table");
/* 424:    */     }
/* 425:478 */     return getIdentifierColumnNames();
/* 426:    */   }
/* 427:    */   
/* 428:    */   public String getSubclassTableName(int j)
/* 429:    */   {
/* 430:482 */     if (j != 0) {
/* 431:482 */       throw new AssertionFailure("only one table");
/* 432:    */     }
/* 433:483 */     return this.tableName;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public int getSubclassTableSpan()
/* 437:    */   {
/* 438:487 */     return 1;
/* 439:    */   }
/* 440:    */   
/* 441:    */   protected boolean isClassOrSuperclassTable(int j)
/* 442:    */   {
/* 443:491 */     if (j != 0) {
/* 444:491 */       throw new AssertionFailure("only one table");
/* 445:    */     }
/* 446:492 */     return true;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public String getPropertyTableName(String propertyName)
/* 450:    */   {
/* 451:497 */     return getTableName();
/* 452:    */   }
/* 453:    */   
/* 454:    */   public String[] getConstraintOrderedTableNameClosure()
/* 455:    */   {
/* 456:501 */     return this.constraintOrderedTableNames;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public String[][] getContraintOrderedTableKeyColumnClosure()
/* 460:    */   {
/* 461:505 */     return this.constraintOrderedKeyColumnNames;
/* 462:    */   }
/* 463:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.UnionSubclassEntityPersister
 * JD-Core Version:    0.7.0.1
 */