/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.hibernate.FlushMode;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.LockMode;
/*  14:    */ import org.hibernate.LockOptions;
/*  15:    */ import org.hibernate.MappingException;
/*  16:    */ import org.hibernate.Query;
/*  17:    */ import org.hibernate.QueryException;
/*  18:    */ import org.hibernate.SQLQuery;
/*  19:    */ import org.hibernate.SQLQuery.FetchReturn;
/*  20:    */ import org.hibernate.SQLQuery.ReturnProperty;
/*  21:    */ import org.hibernate.SQLQuery.RootReturn;
/*  22:    */ import org.hibernate.ScrollMode;
/*  23:    */ import org.hibernate.ScrollableResults;
/*  24:    */ import org.hibernate.engine.ResultSetMappingDefinition;
/*  25:    */ import org.hibernate.engine.query.spi.ParameterMetadata;
/*  26:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryJoinReturn;
/*  27:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
/*  28:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryRootReturn;
/*  29:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryScalarReturn;
/*  30:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
/*  31:    */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*  32:    */ import org.hibernate.engine.spi.QueryParameters;
/*  33:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  34:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  35:    */ import org.hibernate.internal.util.StringHelper;
/*  36:    */ import org.hibernate.persister.entity.EntityPersister;
/*  37:    */ import org.hibernate.type.Type;
/*  38:    */ 
/*  39:    */ public class SQLQueryImpl
/*  40:    */   extends AbstractQueryImpl
/*  41:    */   implements SQLQuery
/*  42:    */ {
/*  43:    */   private List<NativeSQLQueryReturn> queryReturns;
/*  44:    */   private List<ReturnBuilder> queryReturnBuilders;
/*  45:    */   private boolean autoDiscoverTypes;
/*  46:    */   private Collection<String> querySpaces;
/*  47:    */   private final boolean callable;
/*  48:    */   
/*  49:    */   SQLQueryImpl(NamedSQLQueryDefinition queryDef, SessionImplementor session, ParameterMetadata parameterMetadata)
/*  50:    */   {
/*  51: 81 */     super(queryDef.getQueryString(), queryDef.getFlushMode(), session, parameterMetadata);
/*  52: 82 */     if (queryDef.getResultSetRef() != null)
/*  53:    */     {
/*  54: 83 */       ResultSetMappingDefinition definition = session.getFactory().getResultSetMapping(queryDef.getResultSetRef());
/*  55: 85 */       if (definition == null) {
/*  56: 86 */         throw new MappingException("Unable to find resultset-ref definition: " + queryDef.getResultSetRef());
/*  57:    */       }
/*  58: 91 */       this.queryReturns = Arrays.asList(definition.getQueryReturns());
/*  59:    */     }
/*  60: 93 */     else if ((queryDef.getQueryReturns() != null) && (queryDef.getQueryReturns().length > 0))
/*  61:    */     {
/*  62: 94 */       this.queryReturns = Arrays.asList(queryDef.getQueryReturns());
/*  63:    */     }
/*  64:    */     else
/*  65:    */     {
/*  66: 97 */       this.queryReturns = new ArrayList();
/*  67:    */     }
/*  68:100 */     this.querySpaces = queryDef.getQuerySpaces();
/*  69:101 */     this.callable = queryDef.isCallable();
/*  70:    */   }
/*  71:    */   
/*  72:    */   SQLQueryImpl(String sql, String[] returnAliases, Class[] returnClasses, LockMode[] lockModes, SessionImplementor session, Collection<String> querySpaces, FlushMode flushMode, ParameterMetadata parameterMetadata)
/*  73:    */   {
/*  74:114 */     super(sql, flushMode, session, parameterMetadata);
/*  75:115 */     this.queryReturns = new ArrayList(returnAliases.length);
/*  76:116 */     for (int i = 0; i < returnAliases.length; i++)
/*  77:    */     {
/*  78:117 */       NativeSQLQueryRootReturn ret = new NativeSQLQueryRootReturn(returnAliases[i], returnClasses[i].getName(), lockModes == null ? LockMode.NONE : lockModes[i]);
/*  79:    */       
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:122 */       this.queryReturns.add(ret);
/*  84:    */     }
/*  85:124 */     this.querySpaces = querySpaces;
/*  86:125 */     this.callable = false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   SQLQueryImpl(String sql, String[] returnAliases, Class[] returnClasses, SessionImplementor session, ParameterMetadata parameterMetadata)
/*  90:    */   {
/*  91:134 */     this(sql, returnAliases, returnClasses, null, session, null, null, parameterMetadata);
/*  92:    */   }
/*  93:    */   
/*  94:    */   SQLQueryImpl(String sql, SessionImplementor session, ParameterMetadata parameterMetadata)
/*  95:    */   {
/*  96:138 */     super(sql, null, session, parameterMetadata);
/*  97:139 */     this.queryReturns = new ArrayList();
/*  98:140 */     this.querySpaces = null;
/*  99:141 */     this.callable = false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private NativeSQLQueryReturn[] getQueryReturns()
/* 103:    */   {
/* 104:145 */     return (NativeSQLQueryReturn[])this.queryReturns.toArray(new NativeSQLQueryReturn[this.queryReturns.size()]);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List list()
/* 108:    */     throws HibernateException
/* 109:    */   {
/* 110:149 */     verifyParameters();
/* 111:150 */     before();
/* 112:    */     
/* 113:152 */     Map namedParams = getNamedParams();
/* 114:153 */     NativeSQLQuerySpecification spec = generateQuerySpecification(namedParams);
/* 115:    */     try
/* 116:    */     {
/* 117:156 */       return getSession().list(spec, getQueryParameters(namedParams));
/* 118:    */     }
/* 119:    */     finally
/* 120:    */     {
/* 121:159 */       after();
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   private NativeSQLQuerySpecification generateQuerySpecification(Map namedParams)
/* 126:    */   {
/* 127:164 */     return new NativeSQLQuerySpecification(expandParameterLists(namedParams), getQueryReturns(), this.querySpaces);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ScrollableResults scroll(ScrollMode scrollMode)
/* 131:    */     throws HibernateException
/* 132:    */   {
/* 133:172 */     verifyParameters();
/* 134:173 */     before();
/* 135:    */     
/* 136:175 */     Map namedParams = getNamedParams();
/* 137:176 */     NativeSQLQuerySpecification spec = generateQuerySpecification(namedParams);
/* 138:    */     
/* 139:178 */     QueryParameters qp = getQueryParameters(namedParams);
/* 140:179 */     qp.setScrollMode(scrollMode);
/* 141:    */     try
/* 142:    */     {
/* 143:182 */       return getSession().scroll(spec, qp);
/* 144:    */     }
/* 145:    */     finally
/* 146:    */     {
/* 147:185 */       after();
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public ScrollableResults scroll()
/* 152:    */     throws HibernateException
/* 153:    */   {
/* 154:190 */     return scroll(ScrollMode.SCROLL_INSENSITIVE);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Iterator iterate()
/* 158:    */     throws HibernateException
/* 159:    */   {
/* 160:194 */     throw new UnsupportedOperationException("SQL queries do not currently support iteration");
/* 161:    */   }
/* 162:    */   
/* 163:    */   public QueryParameters getQueryParameters(Map namedParams)
/* 164:    */   {
/* 165:199 */     QueryParameters qp = super.getQueryParameters(namedParams);
/* 166:200 */     qp.setCallable(this.callable);
/* 167:201 */     qp.setAutoDiscoverScalarTypes(this.autoDiscoverTypes);
/* 168:202 */     return qp;
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected void verifyParameters()
/* 172:    */   {
/* 173:209 */     prepare();
/* 174:210 */     verifyParameters(this.callable);
/* 175:211 */     boolean noReturns = (this.queryReturns == null) || (this.queryReturns.isEmpty());
/* 176:212 */     if (noReturns) {
/* 177:213 */       this.autoDiscoverTypes = noReturns;
/* 178:    */     } else {
/* 179:216 */       for (NativeSQLQueryReturn queryReturn : this.queryReturns) {
/* 180:217 */         if ((queryReturn instanceof NativeSQLQueryScalarReturn))
/* 181:    */         {
/* 182:218 */           NativeSQLQueryScalarReturn scalar = (NativeSQLQueryScalarReturn)queryReturn;
/* 183:219 */           if (scalar.getType() == null)
/* 184:    */           {
/* 185:220 */             this.autoDiscoverTypes = true;
/* 186:221 */             break;
/* 187:    */           }
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   private void prepare()
/* 194:    */   {
/* 195:229 */     if (this.queryReturnBuilders != null)
/* 196:    */     {
/* 197:230 */       if (!this.queryReturnBuilders.isEmpty())
/* 198:    */       {
/* 199:231 */         if (this.queryReturns != null)
/* 200:    */         {
/* 201:232 */           this.queryReturns.clear();
/* 202:233 */           this.queryReturns = null;
/* 203:    */         }
/* 204:235 */         this.queryReturns = new ArrayList();
/* 205:236 */         for (ReturnBuilder builder : this.queryReturnBuilders) {
/* 206:237 */           this.queryReturns.add(builder.buildReturn());
/* 207:    */         }
/* 208:239 */         this.queryReturnBuilders.clear();
/* 209:    */       }
/* 210:241 */       this.queryReturnBuilders = null;
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String[] getReturnAliases()
/* 215:    */     throws HibernateException
/* 216:    */   {
/* 217:247 */     throw new UnsupportedOperationException("SQL queries do not currently support returning aliases");
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Type[] getReturnTypes()
/* 221:    */     throws HibernateException
/* 222:    */   {
/* 223:252 */     throw new UnsupportedOperationException("not yet implemented for SQL queries");
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Query setLockMode(String alias, LockMode lockMode)
/* 227:    */   {
/* 228:256 */     throw new UnsupportedOperationException("cannot set the lock mode for a native SQL query");
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Query setLockOptions(LockOptions lockOptions)
/* 232:    */   {
/* 233:260 */     throw new UnsupportedOperationException("cannot set lock options for a native SQL query");
/* 234:    */   }
/* 235:    */   
/* 236:    */   public LockOptions getLockOptions()
/* 237:    */   {
/* 238:266 */     return null;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public SQLQuery addScalar(final String columnAlias, final Type type)
/* 242:    */   {
/* 243:270 */     if (this.queryReturnBuilders == null) {
/* 244:271 */       this.queryReturnBuilders = new ArrayList();
/* 245:    */     }
/* 246:273 */     this.queryReturnBuilders.add(new ReturnBuilder()
/* 247:    */     {
/* 248:    */       public NativeSQLQueryReturn buildReturn()
/* 249:    */       {
/* 250:276 */         return new NativeSQLQueryScalarReturn(columnAlias, type);
/* 251:    */       }
/* 252:279 */     });
/* 253:280 */     return this;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public SQLQuery addScalar(String columnAlias)
/* 257:    */   {
/* 258:284 */     return addScalar(columnAlias, null);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public SQLQuery.RootReturn addRoot(String tableAlias, String entityName)
/* 262:    */   {
/* 263:288 */     RootReturnBuilder builder = new RootReturnBuilder(tableAlias, entityName, null);
/* 264:289 */     if (this.queryReturnBuilders == null) {
/* 265:290 */       this.queryReturnBuilders = new ArrayList();
/* 266:    */     }
/* 267:292 */     this.queryReturnBuilders.add(builder);
/* 268:293 */     return builder;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public SQLQuery.RootReturn addRoot(String tableAlias, Class entityType)
/* 272:    */   {
/* 273:297 */     return addRoot(tableAlias, entityType.getName());
/* 274:    */   }
/* 275:    */   
/* 276:    */   public SQLQuery addEntity(String entityName)
/* 277:    */   {
/* 278:301 */     return addEntity(StringHelper.unqualify(entityName), entityName);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public SQLQuery addEntity(String alias, String entityName)
/* 282:    */   {
/* 283:305 */     addRoot(alias, entityName);
/* 284:306 */     return this;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public SQLQuery addEntity(String alias, String entityName, LockMode lockMode)
/* 288:    */   {
/* 289:310 */     addRoot(alias, entityName).setLockMode(lockMode);
/* 290:311 */     return this;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public SQLQuery addEntity(Class entityType)
/* 294:    */   {
/* 295:315 */     return addEntity(entityType.getName());
/* 296:    */   }
/* 297:    */   
/* 298:    */   public SQLQuery addEntity(String alias, Class entityClass)
/* 299:    */   {
/* 300:319 */     return addEntity(alias, entityClass.getName());
/* 301:    */   }
/* 302:    */   
/* 303:    */   public SQLQuery addEntity(String alias, Class entityClass, LockMode lockMode)
/* 304:    */   {
/* 305:323 */     return addEntity(alias, entityClass.getName(), lockMode);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public SQLQuery.FetchReturn addFetch(String tableAlias, String ownerTableAlias, String joinPropertyName)
/* 309:    */   {
/* 310:327 */     FetchReturnBuilder builder = new FetchReturnBuilder(tableAlias, ownerTableAlias, joinPropertyName, null);
/* 311:328 */     if (this.queryReturnBuilders == null) {
/* 312:329 */       this.queryReturnBuilders = new ArrayList();
/* 313:    */     }
/* 314:331 */     this.queryReturnBuilders.add(builder);
/* 315:332 */     return builder;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public SQLQuery addJoin(String tableAlias, String ownerTableAlias, String joinPropertyName)
/* 319:    */   {
/* 320:336 */     addFetch(tableAlias, ownerTableAlias, joinPropertyName);
/* 321:337 */     return this;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public SQLQuery addJoin(String alias, String path)
/* 325:    */   {
/* 326:341 */     createFetchJoin(alias, path);
/* 327:342 */     return this;
/* 328:    */   }
/* 329:    */   
/* 330:    */   private SQLQuery.FetchReturn createFetchJoin(String tableAlias, String path)
/* 331:    */   {
/* 332:346 */     int loc = path.indexOf('.');
/* 333:347 */     if (loc < 0) {
/* 334:348 */       throw new QueryException("not a property path: " + path);
/* 335:    */     }
/* 336:350 */     String ownerTableAlias = path.substring(0, loc);
/* 337:351 */     String joinedPropertyName = path.substring(loc + 1);
/* 338:352 */     return addFetch(tableAlias, ownerTableAlias, joinedPropertyName);
/* 339:    */   }
/* 340:    */   
/* 341:    */   public SQLQuery addJoin(String alias, String path, LockMode lockMode)
/* 342:    */   {
/* 343:356 */     createFetchJoin(alias, path).setLockMode(lockMode);
/* 344:357 */     return this;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public SQLQuery setResultSetMapping(String name)
/* 348:    */   {
/* 349:361 */     ResultSetMappingDefinition mapping = this.session.getFactory().getResultSetMapping(name);
/* 350:362 */     if (mapping == null) {
/* 351:363 */       throw new MappingException("Unknown SqlResultSetMapping [" + name + "]");
/* 352:    */     }
/* 353:365 */     NativeSQLQueryReturn[] returns = mapping.getQueryReturns();
/* 354:366 */     this.queryReturns.addAll(Arrays.asList(returns));
/* 355:367 */     return this;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public SQLQuery addSynchronizedQuerySpace(String querySpace)
/* 359:    */   {
/* 360:371 */     if (this.querySpaces == null) {
/* 361:372 */       this.querySpaces = new ArrayList();
/* 362:    */     }
/* 363:374 */     this.querySpaces.add(querySpace);
/* 364:375 */     return this;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public SQLQuery addSynchronizedEntityName(String entityName)
/* 368:    */   {
/* 369:379 */     return addQuerySpaces(getSession().getFactory().getEntityPersister(entityName).getQuerySpaces());
/* 370:    */   }
/* 371:    */   
/* 372:    */   public SQLQuery addSynchronizedEntityClass(Class entityClass)
/* 373:    */   {
/* 374:383 */     return addQuerySpaces(getSession().getFactory().getEntityPersister(entityClass.getName()).getQuerySpaces());
/* 375:    */   }
/* 376:    */   
/* 377:    */   private SQLQuery addQuerySpaces(Serializable[] spaces)
/* 378:    */   {
/* 379:387 */     if (spaces != null)
/* 380:    */     {
/* 381:388 */       if (this.querySpaces == null) {
/* 382:389 */         this.querySpaces = new ArrayList();
/* 383:    */       }
/* 384:391 */       this.querySpaces.addAll(Arrays.asList((String[])spaces));
/* 385:    */     }
/* 386:393 */     return this;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public int executeUpdate()
/* 390:    */     throws HibernateException
/* 391:    */   {
/* 392:397 */     Map namedParams = getNamedParams();
/* 393:398 */     before();
/* 394:    */     try
/* 395:    */     {
/* 396:400 */       return getSession().executeNativeUpdate(generateQuerySpecification(namedParams), getQueryParameters(namedParams));
/* 397:    */     }
/* 398:    */     finally
/* 399:    */     {
/* 400:406 */       after();
/* 401:    */     }
/* 402:    */   }
/* 403:    */   
/* 404:    */   private class RootReturnBuilder
/* 405:    */     implements SQLQuery.RootReturn, SQLQueryImpl.ReturnBuilder
/* 406:    */   {
/* 407:    */     private final String alias;
/* 408:    */     private final String entityName;
/* 409:413 */     private LockMode lockMode = LockMode.READ;
/* 410:    */     private Map<String, String[]> propertyMappings;
/* 411:    */     
/* 412:    */     private RootReturnBuilder(String alias, String entityName)
/* 413:    */     {
/* 414:417 */       this.alias = alias;
/* 415:418 */       this.entityName = entityName;
/* 416:    */     }
/* 417:    */     
/* 418:    */     public SQLQuery.RootReturn setLockMode(LockMode lockMode)
/* 419:    */     {
/* 420:422 */       this.lockMode = lockMode;
/* 421:423 */       return this;
/* 422:    */     }
/* 423:    */     
/* 424:    */     public SQLQuery.RootReturn setDiscriminatorAlias(String alias)
/* 425:    */     {
/* 426:427 */       addProperty("class", alias);
/* 427:428 */       return this;
/* 428:    */     }
/* 429:    */     
/* 430:    */     public SQLQuery.RootReturn addProperty(String propertyName, String columnAlias)
/* 431:    */     {
/* 432:432 */       addProperty(propertyName).addColumnAlias(columnAlias);
/* 433:433 */       return this;
/* 434:    */     }
/* 435:    */     
/* 436:    */     public SQLQuery.ReturnProperty addProperty(final String propertyName)
/* 437:    */     {
/* 438:437 */       if (this.propertyMappings == null) {
/* 439:438 */         this.propertyMappings = new HashMap();
/* 440:    */       }
/* 441:440 */       new SQLQuery.ReturnProperty()
/* 442:    */       {
/* 443:    */         public SQLQuery.ReturnProperty addColumnAlias(String columnAlias)
/* 444:    */         {
/* 445:442 */           String[] columnAliases = (String[])SQLQueryImpl.RootReturnBuilder.this.propertyMappings.get(propertyName);
/* 446:443 */           if (columnAliases == null)
/* 447:    */           {
/* 448:444 */             columnAliases = new String[] { columnAlias };
/* 449:    */           }
/* 450:    */           else
/* 451:    */           {
/* 452:446 */             String[] newColumnAliases = new String[columnAliases.length + 1];
/* 453:447 */             System.arraycopy(columnAliases, 0, newColumnAliases, 0, columnAliases.length);
/* 454:448 */             newColumnAliases[columnAliases.length] = columnAlias;
/* 455:449 */             columnAliases = newColumnAliases;
/* 456:    */           }
/* 457:451 */           SQLQueryImpl.RootReturnBuilder.this.propertyMappings.put(propertyName, columnAliases);
/* 458:452 */           return this;
/* 459:    */         }
/* 460:    */       };
/* 461:    */     }
/* 462:    */     
/* 463:    */     public NativeSQLQueryReturn buildReturn()
/* 464:    */     {
/* 465:458 */       return new NativeSQLQueryRootReturn(this.alias, this.entityName, this.propertyMappings, this.lockMode);
/* 466:    */     }
/* 467:    */   }
/* 468:    */   
/* 469:    */   private class FetchReturnBuilder
/* 470:    */     implements SQLQuery.FetchReturn, SQLQueryImpl.ReturnBuilder
/* 471:    */   {
/* 472:    */     private final String alias;
/* 473:    */     private String ownerTableAlias;
/* 474:    */     private final String joinedPropertyName;
/* 475:465 */     private LockMode lockMode = LockMode.READ;
/* 476:    */     private Map<String, String[]> propertyMappings;
/* 477:    */     
/* 478:    */     private FetchReturnBuilder(String alias, String ownerTableAlias, String joinedPropertyName)
/* 479:    */     {
/* 480:469 */       this.alias = alias;
/* 481:470 */       this.ownerTableAlias = ownerTableAlias;
/* 482:471 */       this.joinedPropertyName = joinedPropertyName;
/* 483:    */     }
/* 484:    */     
/* 485:    */     public SQLQuery.FetchReturn setLockMode(LockMode lockMode)
/* 486:    */     {
/* 487:475 */       this.lockMode = lockMode;
/* 488:476 */       return this;
/* 489:    */     }
/* 490:    */     
/* 491:    */     public SQLQuery.FetchReturn addProperty(String propertyName, String columnAlias)
/* 492:    */     {
/* 493:480 */       addProperty(propertyName).addColumnAlias(columnAlias);
/* 494:481 */       return this;
/* 495:    */     }
/* 496:    */     
/* 497:    */     public SQLQuery.ReturnProperty addProperty(final String propertyName)
/* 498:    */     {
/* 499:485 */       if (this.propertyMappings == null) {
/* 500:486 */         this.propertyMappings = new HashMap();
/* 501:    */       }
/* 502:488 */       new SQLQuery.ReturnProperty()
/* 503:    */       {
/* 504:    */         public SQLQuery.ReturnProperty addColumnAlias(String columnAlias)
/* 505:    */         {
/* 506:490 */           String[] columnAliases = (String[])SQLQueryImpl.FetchReturnBuilder.this.propertyMappings.get(propertyName);
/* 507:491 */           if (columnAliases == null)
/* 508:    */           {
/* 509:492 */             columnAliases = new String[] { columnAlias };
/* 510:    */           }
/* 511:    */           else
/* 512:    */           {
/* 513:494 */             String[] newColumnAliases = new String[columnAliases.length + 1];
/* 514:495 */             System.arraycopy(columnAliases, 0, newColumnAliases, 0, columnAliases.length);
/* 515:496 */             newColumnAliases[columnAliases.length] = columnAlias;
/* 516:497 */             columnAliases = newColumnAliases;
/* 517:    */           }
/* 518:499 */           SQLQueryImpl.FetchReturnBuilder.this.propertyMappings.put(propertyName, columnAliases);
/* 519:500 */           return this;
/* 520:    */         }
/* 521:    */       };
/* 522:    */     }
/* 523:    */     
/* 524:    */     public NativeSQLQueryReturn buildReturn()
/* 525:    */     {
/* 526:506 */       return new NativeSQLQueryJoinReturn(this.alias, this.ownerTableAlias, this.joinedPropertyName, this.propertyMappings, this.lockMode);
/* 527:    */     }
/* 528:    */   }
/* 529:    */   
/* 530:    */   private static abstract interface ReturnBuilder
/* 531:    */   {
/* 532:    */     public abstract NativeSQLQueryReturn buildReturn();
/* 533:    */   }
/* 534:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.SQLQueryImpl
 * JD-Core Version:    0.7.0.1
 */