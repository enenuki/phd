/*   1:    */ package org.hibernate.stat.internal;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import java.util.concurrent.ConcurrentHashMap;
/*   5:    */ import java.util.concurrent.ConcurrentMap;
/*   6:    */ import java.util.concurrent.atomic.AtomicLong;
/*   7:    */ import org.hibernate.cache.spi.Region;
/*   8:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   9:    */ import org.hibernate.internal.CoreMessageLogger;
/*  10:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  11:    */ import org.hibernate.service.Service;
/*  12:    */ import org.hibernate.stat.CollectionStatistics;
/*  13:    */ import org.hibernate.stat.EntityStatistics;
/*  14:    */ import org.hibernate.stat.QueryStatistics;
/*  15:    */ import org.hibernate.stat.SecondLevelCacheStatistics;
/*  16:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class ConcurrentStatisticsImpl
/*  20:    */   implements StatisticsImplementor, Service
/*  21:    */ {
/*  22: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ConcurrentStatisticsImpl.class.getName());
/*  23:    */   private SessionFactoryImplementor sessionFactory;
/*  24:    */   private volatile boolean isStatisticsEnabled;
/*  25:    */   private volatile long startTime;
/*  26: 57 */   private AtomicLong sessionOpenCount = new AtomicLong();
/*  27: 58 */   private AtomicLong sessionCloseCount = new AtomicLong();
/*  28: 59 */   private AtomicLong flushCount = new AtomicLong();
/*  29: 60 */   private AtomicLong connectCount = new AtomicLong();
/*  30: 62 */   private AtomicLong prepareStatementCount = new AtomicLong();
/*  31: 63 */   private AtomicLong closeStatementCount = new AtomicLong();
/*  32: 65 */   private AtomicLong entityLoadCount = new AtomicLong();
/*  33: 66 */   private AtomicLong entityUpdateCount = new AtomicLong();
/*  34: 67 */   private AtomicLong entityInsertCount = new AtomicLong();
/*  35: 68 */   private AtomicLong entityDeleteCount = new AtomicLong();
/*  36: 69 */   private AtomicLong entityFetchCount = new AtomicLong();
/*  37: 70 */   private AtomicLong collectionLoadCount = new AtomicLong();
/*  38: 71 */   private AtomicLong collectionUpdateCount = new AtomicLong();
/*  39: 72 */   private AtomicLong collectionRemoveCount = new AtomicLong();
/*  40: 73 */   private AtomicLong collectionRecreateCount = new AtomicLong();
/*  41: 74 */   private AtomicLong collectionFetchCount = new AtomicLong();
/*  42: 76 */   private AtomicLong secondLevelCacheHitCount = new AtomicLong();
/*  43: 77 */   private AtomicLong secondLevelCacheMissCount = new AtomicLong();
/*  44: 78 */   private AtomicLong secondLevelCachePutCount = new AtomicLong();
/*  45: 80 */   private AtomicLong queryExecutionCount = new AtomicLong();
/*  46: 81 */   private AtomicLong queryExecutionMaxTime = new AtomicLong();
/*  47:    */   private volatile String queryExecutionMaxTimeQueryString;
/*  48: 83 */   private AtomicLong queryCacheHitCount = new AtomicLong();
/*  49: 84 */   private AtomicLong queryCacheMissCount = new AtomicLong();
/*  50: 85 */   private AtomicLong queryCachePutCount = new AtomicLong();
/*  51: 87 */   private AtomicLong updateTimestampsCacheHitCount = new AtomicLong();
/*  52: 88 */   private AtomicLong updateTimestampsCacheMissCount = new AtomicLong();
/*  53: 89 */   private AtomicLong updateTimestampsCachePutCount = new AtomicLong();
/*  54: 91 */   private AtomicLong committedTransactionCount = new AtomicLong();
/*  55: 92 */   private AtomicLong transactionCount = new AtomicLong();
/*  56: 94 */   private AtomicLong optimisticFailureCount = new AtomicLong();
/*  57: 99 */   private final ConcurrentMap secondLevelCacheStatistics = new ConcurrentHashMap();
/*  58:103 */   private final ConcurrentMap entityStatistics = new ConcurrentHashMap();
/*  59:107 */   private final ConcurrentMap collectionStatistics = new ConcurrentHashMap();
/*  60:111 */   private final ConcurrentMap queryStatistics = new ConcurrentHashMap();
/*  61:    */   
/*  62:    */   public ConcurrentStatisticsImpl()
/*  63:    */   {
/*  64:115 */     clear();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public ConcurrentStatisticsImpl(SessionFactoryImplementor sessionFactory)
/*  68:    */   {
/*  69:119 */     clear();
/*  70:120 */     this.sessionFactory = sessionFactory;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void clear()
/*  74:    */   {
/*  75:127 */     this.secondLevelCacheHitCount.set(0L);
/*  76:128 */     this.secondLevelCacheMissCount.set(0L);
/*  77:129 */     this.secondLevelCachePutCount.set(0L);
/*  78:    */     
/*  79:131 */     this.sessionCloseCount.set(0L);
/*  80:132 */     this.sessionOpenCount.set(0L);
/*  81:133 */     this.flushCount.set(0L);
/*  82:134 */     this.connectCount.set(0L);
/*  83:    */     
/*  84:136 */     this.prepareStatementCount.set(0L);
/*  85:137 */     this.closeStatementCount.set(0L);
/*  86:    */     
/*  87:139 */     this.entityDeleteCount.set(0L);
/*  88:140 */     this.entityInsertCount.set(0L);
/*  89:141 */     this.entityUpdateCount.set(0L);
/*  90:142 */     this.entityLoadCount.set(0L);
/*  91:143 */     this.entityFetchCount.set(0L);
/*  92:    */     
/*  93:145 */     this.collectionRemoveCount.set(0L);
/*  94:146 */     this.collectionUpdateCount.set(0L);
/*  95:147 */     this.collectionRecreateCount.set(0L);
/*  96:148 */     this.collectionLoadCount.set(0L);
/*  97:149 */     this.collectionFetchCount.set(0L);
/*  98:    */     
/*  99:151 */     this.queryExecutionCount.set(0L);
/* 100:152 */     this.queryCacheHitCount.set(0L);
/* 101:153 */     this.queryExecutionMaxTime.set(0L);
/* 102:154 */     this.queryExecutionMaxTimeQueryString = null;
/* 103:155 */     this.queryCacheMissCount.set(0L);
/* 104:156 */     this.queryCachePutCount.set(0L);
/* 105:    */     
/* 106:158 */     this.updateTimestampsCacheMissCount.set(0L);
/* 107:159 */     this.updateTimestampsCacheHitCount.set(0L);
/* 108:160 */     this.updateTimestampsCachePutCount.set(0L);
/* 109:    */     
/* 110:162 */     this.transactionCount.set(0L);
/* 111:163 */     this.committedTransactionCount.set(0L);
/* 112:    */     
/* 113:165 */     this.optimisticFailureCount.set(0L);
/* 114:    */     
/* 115:167 */     this.secondLevelCacheStatistics.clear();
/* 116:168 */     this.entityStatistics.clear();
/* 117:169 */     this.collectionStatistics.clear();
/* 118:170 */     this.queryStatistics.clear();
/* 119:    */     
/* 120:172 */     this.startTime = System.currentTimeMillis();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void openSession()
/* 124:    */   {
/* 125:176 */     this.sessionOpenCount.getAndIncrement();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void closeSession()
/* 129:    */   {
/* 130:180 */     this.sessionCloseCount.getAndIncrement();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void flush()
/* 134:    */   {
/* 135:184 */     this.flushCount.getAndIncrement();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void connect()
/* 139:    */   {
/* 140:188 */     this.connectCount.getAndIncrement();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void loadEntity(String entityName)
/* 144:    */   {
/* 145:192 */     this.entityLoadCount.getAndIncrement();
/* 146:193 */     ((ConcurrentEntityStatisticsImpl)getEntityStatistics(entityName)).incrementLoadCount();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void fetchEntity(String entityName)
/* 150:    */   {
/* 151:197 */     this.entityFetchCount.getAndIncrement();
/* 152:198 */     ((ConcurrentEntityStatisticsImpl)getEntityStatistics(entityName)).incrementFetchCount();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public EntityStatistics getEntityStatistics(String entityName)
/* 156:    */   {
/* 157:209 */     ConcurrentEntityStatisticsImpl es = (ConcurrentEntityStatisticsImpl)this.entityStatistics.get(entityName);
/* 158:210 */     if (es == null)
/* 159:    */     {
/* 160:211 */       es = new ConcurrentEntityStatisticsImpl(entityName);
/* 161:    */       ConcurrentEntityStatisticsImpl previous;
/* 162:213 */       if ((previous = (ConcurrentEntityStatisticsImpl)this.entityStatistics.putIfAbsent(entityName, es)) != null) {
/* 163:216 */         es = previous;
/* 164:    */       }
/* 165:    */     }
/* 166:219 */     return es;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void updateEntity(String entityName)
/* 170:    */   {
/* 171:223 */     this.entityUpdateCount.getAndIncrement();
/* 172:224 */     ConcurrentEntityStatisticsImpl es = (ConcurrentEntityStatisticsImpl)getEntityStatistics(entityName);
/* 173:225 */     es.incrementUpdateCount();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void insertEntity(String entityName)
/* 177:    */   {
/* 178:229 */     this.entityInsertCount.getAndIncrement();
/* 179:230 */     ConcurrentEntityStatisticsImpl es = (ConcurrentEntityStatisticsImpl)getEntityStatistics(entityName);
/* 180:231 */     es.incrementInsertCount();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void deleteEntity(String entityName)
/* 184:    */   {
/* 185:235 */     this.entityDeleteCount.getAndIncrement();
/* 186:236 */     ConcurrentEntityStatisticsImpl es = (ConcurrentEntityStatisticsImpl)getEntityStatistics(entityName);
/* 187:237 */     es.incrementDeleteCount();
/* 188:    */   }
/* 189:    */   
/* 190:    */   public CollectionStatistics getCollectionStatistics(String role)
/* 191:    */   {
/* 192:248 */     ConcurrentCollectionStatisticsImpl cs = (ConcurrentCollectionStatisticsImpl)this.collectionStatistics.get(role);
/* 193:249 */     if (cs == null)
/* 194:    */     {
/* 195:250 */       cs = new ConcurrentCollectionStatisticsImpl(role);
/* 196:    */       ConcurrentCollectionStatisticsImpl previous;
/* 197:252 */       if ((previous = (ConcurrentCollectionStatisticsImpl)this.collectionStatistics.putIfAbsent(role, cs)) != null) {
/* 198:255 */         cs = previous;
/* 199:    */       }
/* 200:    */     }
/* 201:258 */     return cs;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void loadCollection(String role)
/* 205:    */   {
/* 206:262 */     this.collectionLoadCount.getAndIncrement();
/* 207:263 */     ((ConcurrentCollectionStatisticsImpl)getCollectionStatistics(role)).incrementLoadCount();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void fetchCollection(String role)
/* 211:    */   {
/* 212:267 */     this.collectionFetchCount.getAndIncrement();
/* 213:268 */     ((ConcurrentCollectionStatisticsImpl)getCollectionStatistics(role)).incrementFetchCount();
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void updateCollection(String role)
/* 217:    */   {
/* 218:272 */     this.collectionUpdateCount.getAndIncrement();
/* 219:273 */     ((ConcurrentCollectionStatisticsImpl)getCollectionStatistics(role)).incrementUpdateCount();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void recreateCollection(String role)
/* 223:    */   {
/* 224:277 */     this.collectionRecreateCount.getAndIncrement();
/* 225:278 */     ((ConcurrentCollectionStatisticsImpl)getCollectionStatistics(role)).incrementRecreateCount();
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void removeCollection(String role)
/* 229:    */   {
/* 230:282 */     this.collectionRemoveCount.getAndIncrement();
/* 231:283 */     ((ConcurrentCollectionStatisticsImpl)getCollectionStatistics(role)).incrementRemoveCount();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public SecondLevelCacheStatistics getSecondLevelCacheStatistics(String regionName)
/* 235:    */   {
/* 236:294 */     ConcurrentSecondLevelCacheStatisticsImpl slcs = (ConcurrentSecondLevelCacheStatisticsImpl)this.secondLevelCacheStatistics.get(regionName);
/* 237:296 */     if (slcs == null)
/* 238:    */     {
/* 239:297 */       if (this.sessionFactory == null) {
/* 240:298 */         return null;
/* 241:    */       }
/* 242:300 */       Region region = this.sessionFactory.getSecondLevelCacheRegion(regionName);
/* 243:301 */       if (region == null) {
/* 244:302 */         return null;
/* 245:    */       }
/* 246:304 */       slcs = new ConcurrentSecondLevelCacheStatisticsImpl(region);
/* 247:    */       ConcurrentSecondLevelCacheStatisticsImpl previous;
/* 248:306 */       if ((previous = (ConcurrentSecondLevelCacheStatisticsImpl)this.secondLevelCacheStatistics.putIfAbsent(regionName, slcs)) != null) {
/* 249:309 */         slcs = previous;
/* 250:    */       }
/* 251:    */     }
/* 252:312 */     return slcs;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void secondLevelCachePut(String regionName)
/* 256:    */   {
/* 257:316 */     this.secondLevelCachePutCount.getAndIncrement();
/* 258:317 */     ((ConcurrentSecondLevelCacheStatisticsImpl)getSecondLevelCacheStatistics(regionName)).incrementPutCount();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void secondLevelCacheHit(String regionName)
/* 262:    */   {
/* 263:321 */     this.secondLevelCacheHitCount.getAndIncrement();
/* 264:322 */     ((ConcurrentSecondLevelCacheStatisticsImpl)getSecondLevelCacheStatistics(regionName)).incrementHitCount();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void secondLevelCacheMiss(String regionName)
/* 268:    */   {
/* 269:326 */     this.secondLevelCacheMissCount.getAndIncrement();
/* 270:327 */     ((ConcurrentSecondLevelCacheStatisticsImpl)getSecondLevelCacheStatistics(regionName)).incrementMissCount();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void queryExecuted(String hql, int rows, long time)
/* 274:    */   {
/* 275:332 */     LOG.hql(hql, Long.valueOf(time), Long.valueOf(rows));
/* 276:333 */     this.queryExecutionCount.getAndIncrement();
/* 277:334 */     boolean isLongestQuery = false;
/* 278:335 */     long old = this.queryExecutionMaxTime.get();
/* 279:336 */     while (((isLongestQuery = time > old ? 1 : 0) != 0) && (!this.queryExecutionMaxTime.compareAndSet(old, time))) {
/* 280:337 */       old = this.queryExecutionMaxTime.get();
/* 281:    */     }
/* 282:340 */     if (isLongestQuery) {
/* 283:341 */       this.queryExecutionMaxTimeQueryString = hql;
/* 284:    */     }
/* 285:343 */     if (hql != null)
/* 286:    */     {
/* 287:344 */       ConcurrentQueryStatisticsImpl qs = (ConcurrentQueryStatisticsImpl)getQueryStatistics(hql);
/* 288:345 */       qs.executed(rows, time);
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void queryCacheHit(String hql, String regionName)
/* 293:    */   {
/* 294:350 */     this.queryCacheHitCount.getAndIncrement();
/* 295:351 */     if (hql != null)
/* 296:    */     {
/* 297:352 */       ConcurrentQueryStatisticsImpl qs = (ConcurrentQueryStatisticsImpl)getQueryStatistics(hql);
/* 298:353 */       qs.incrementCacheHitCount();
/* 299:    */     }
/* 300:355 */     ConcurrentSecondLevelCacheStatisticsImpl slcs = (ConcurrentSecondLevelCacheStatisticsImpl)getSecondLevelCacheStatistics(regionName);
/* 301:    */     
/* 302:    */ 
/* 303:358 */     slcs.incrementHitCount();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void queryCacheMiss(String hql, String regionName)
/* 307:    */   {
/* 308:362 */     this.queryCacheMissCount.getAndIncrement();
/* 309:363 */     if (hql != null)
/* 310:    */     {
/* 311:364 */       ConcurrentQueryStatisticsImpl qs = (ConcurrentQueryStatisticsImpl)getQueryStatistics(hql);
/* 312:365 */       qs.incrementCacheMissCount();
/* 313:    */     }
/* 314:367 */     ConcurrentSecondLevelCacheStatisticsImpl slcs = (ConcurrentSecondLevelCacheStatisticsImpl)getSecondLevelCacheStatistics(regionName);
/* 315:    */     
/* 316:    */ 
/* 317:370 */     slcs.incrementMissCount();
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void queryCachePut(String hql, String regionName)
/* 321:    */   {
/* 322:374 */     this.queryCachePutCount.getAndIncrement();
/* 323:375 */     if (hql != null)
/* 324:    */     {
/* 325:376 */       ConcurrentQueryStatisticsImpl qs = (ConcurrentQueryStatisticsImpl)getQueryStatistics(hql);
/* 326:377 */       qs.incrementCachePutCount();
/* 327:    */     }
/* 328:379 */     ConcurrentSecondLevelCacheStatisticsImpl slcs = (ConcurrentSecondLevelCacheStatisticsImpl)getSecondLevelCacheStatistics(regionName);
/* 329:    */     
/* 330:    */ 
/* 331:382 */     slcs.incrementPutCount();
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void updateTimestampsCacheHit()
/* 335:    */   {
/* 336:387 */     this.updateTimestampsCacheHitCount.getAndIncrement();
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void updateTimestampsCacheMiss()
/* 340:    */   {
/* 341:392 */     this.updateTimestampsCacheMissCount.getAndIncrement();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void updateTimestampsCachePut()
/* 345:    */   {
/* 346:397 */     this.updateTimestampsCachePutCount.getAndIncrement();
/* 347:    */   }
/* 348:    */   
/* 349:    */   public QueryStatistics getQueryStatistics(String queryString)
/* 350:    */   {
/* 351:408 */     ConcurrentQueryStatisticsImpl qs = (ConcurrentQueryStatisticsImpl)this.queryStatistics.get(queryString);
/* 352:409 */     if (qs == null)
/* 353:    */     {
/* 354:410 */       qs = new ConcurrentQueryStatisticsImpl(queryString);
/* 355:    */       ConcurrentQueryStatisticsImpl previous;
/* 356:412 */       if ((previous = (ConcurrentQueryStatisticsImpl)this.queryStatistics.putIfAbsent(queryString, qs)) != null) {
/* 357:415 */         qs = previous;
/* 358:    */       }
/* 359:    */     }
/* 360:418 */     return qs;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public long getEntityDeleteCount()
/* 364:    */   {
/* 365:425 */     return this.entityDeleteCount.get();
/* 366:    */   }
/* 367:    */   
/* 368:    */   public long getEntityInsertCount()
/* 369:    */   {
/* 370:432 */     return this.entityInsertCount.get();
/* 371:    */   }
/* 372:    */   
/* 373:    */   public long getEntityLoadCount()
/* 374:    */   {
/* 375:439 */     return this.entityLoadCount.get();
/* 376:    */   }
/* 377:    */   
/* 378:    */   public long getEntityFetchCount()
/* 379:    */   {
/* 380:446 */     return this.entityFetchCount.get();
/* 381:    */   }
/* 382:    */   
/* 383:    */   public long getEntityUpdateCount()
/* 384:    */   {
/* 385:453 */     return this.entityUpdateCount.get();
/* 386:    */   }
/* 387:    */   
/* 388:    */   public long getQueryExecutionCount()
/* 389:    */   {
/* 390:457 */     return this.queryExecutionCount.get();
/* 391:    */   }
/* 392:    */   
/* 393:    */   public long getQueryCacheHitCount()
/* 394:    */   {
/* 395:461 */     return this.queryCacheHitCount.get();
/* 396:    */   }
/* 397:    */   
/* 398:    */   public long getQueryCacheMissCount()
/* 399:    */   {
/* 400:465 */     return this.queryCacheMissCount.get();
/* 401:    */   }
/* 402:    */   
/* 403:    */   public long getQueryCachePutCount()
/* 404:    */   {
/* 405:469 */     return this.queryCachePutCount.get();
/* 406:    */   }
/* 407:    */   
/* 408:    */   public long getUpdateTimestampsCacheHitCount()
/* 409:    */   {
/* 410:473 */     return this.updateTimestampsCacheHitCount.get();
/* 411:    */   }
/* 412:    */   
/* 413:    */   public long getUpdateTimestampsCacheMissCount()
/* 414:    */   {
/* 415:477 */     return this.updateTimestampsCacheMissCount.get();
/* 416:    */   }
/* 417:    */   
/* 418:    */   public long getUpdateTimestampsCachePutCount()
/* 419:    */   {
/* 420:481 */     return this.updateTimestampsCachePutCount.get();
/* 421:    */   }
/* 422:    */   
/* 423:    */   public long getFlushCount()
/* 424:    */   {
/* 425:488 */     return this.flushCount.get();
/* 426:    */   }
/* 427:    */   
/* 428:    */   public long getConnectCount()
/* 429:    */   {
/* 430:495 */     return this.connectCount.get();
/* 431:    */   }
/* 432:    */   
/* 433:    */   public long getSecondLevelCacheHitCount()
/* 434:    */   {
/* 435:502 */     return this.secondLevelCacheHitCount.get();
/* 436:    */   }
/* 437:    */   
/* 438:    */   public long getSecondLevelCacheMissCount()
/* 439:    */   {
/* 440:509 */     return this.secondLevelCacheMissCount.get();
/* 441:    */   }
/* 442:    */   
/* 443:    */   public long getSecondLevelCachePutCount()
/* 444:    */   {
/* 445:516 */     return this.secondLevelCachePutCount.get();
/* 446:    */   }
/* 447:    */   
/* 448:    */   public long getSessionCloseCount()
/* 449:    */   {
/* 450:523 */     return this.sessionCloseCount.get();
/* 451:    */   }
/* 452:    */   
/* 453:    */   public long getSessionOpenCount()
/* 454:    */   {
/* 455:530 */     return this.sessionOpenCount.get();
/* 456:    */   }
/* 457:    */   
/* 458:    */   public long getCollectionLoadCount()
/* 459:    */   {
/* 460:537 */     return this.collectionLoadCount.get();
/* 461:    */   }
/* 462:    */   
/* 463:    */   public long getCollectionFetchCount()
/* 464:    */   {
/* 465:544 */     return this.collectionFetchCount.get();
/* 466:    */   }
/* 467:    */   
/* 468:    */   public long getCollectionUpdateCount()
/* 469:    */   {
/* 470:551 */     return this.collectionUpdateCount.get();
/* 471:    */   }
/* 472:    */   
/* 473:    */   public long getCollectionRemoveCount()
/* 474:    */   {
/* 475:559 */     return this.collectionRemoveCount.get();
/* 476:    */   }
/* 477:    */   
/* 478:    */   public long getCollectionRecreateCount()
/* 479:    */   {
/* 480:566 */     return this.collectionRecreateCount.get();
/* 481:    */   }
/* 482:    */   
/* 483:    */   public long getStartTime()
/* 484:    */   {
/* 485:573 */     return this.startTime;
/* 486:    */   }
/* 487:    */   
/* 488:    */   public void logSummary()
/* 489:    */   {
/* 490:580 */     LOG.loggingStatistics();
/* 491:581 */     LOG.startTime(this.startTime);
/* 492:582 */     LOG.sessionsOpened(this.sessionOpenCount.get());
/* 493:583 */     LOG.sessionsClosed(this.sessionCloseCount.get());
/* 494:584 */     LOG.transactions(this.transactionCount.get());
/* 495:585 */     LOG.successfulTransactions(this.committedTransactionCount.get());
/* 496:586 */     LOG.optimisticLockFailures(this.optimisticFailureCount.get());
/* 497:587 */     LOG.flushes(this.flushCount.get());
/* 498:588 */     LOG.connectionsObtained(this.connectCount.get());
/* 499:589 */     LOG.statementsPrepared(this.prepareStatementCount.get());
/* 500:590 */     LOG.statementsClosed(this.closeStatementCount.get());
/* 501:591 */     LOG.secondLevelCachePuts(this.secondLevelCachePutCount.get());
/* 502:592 */     LOG.secondLevelCacheHits(this.secondLevelCacheHitCount.get());
/* 503:593 */     LOG.secondLevelCacheMisses(this.secondLevelCacheMissCount.get());
/* 504:594 */     LOG.entitiesLoaded(this.entityLoadCount.get());
/* 505:595 */     LOG.entitiesUpdated(this.entityUpdateCount.get());
/* 506:596 */     LOG.entitiesInserted(this.entityInsertCount.get());
/* 507:597 */     LOG.entitiesDeleted(this.entityDeleteCount.get());
/* 508:598 */     LOG.entitiesFetched(this.entityFetchCount.get());
/* 509:599 */     LOG.collectionsLoaded(this.collectionLoadCount.get());
/* 510:600 */     LOG.collectionsUpdated(this.collectionUpdateCount.get());
/* 511:601 */     LOG.collectionsRemoved(this.collectionRemoveCount.get());
/* 512:602 */     LOG.collectionsRecreated(this.collectionRecreateCount.get());
/* 513:603 */     LOG.collectionsFetched(this.collectionFetchCount.get());
/* 514:604 */     LOG.queriesExecuted(this.queryExecutionCount.get());
/* 515:605 */     LOG.queryCachePuts(this.queryCachePutCount.get());
/* 516:606 */     LOG.timestampCachePuts(this.updateTimestampsCachePutCount.get());
/* 517:607 */     LOG.timestampCacheHits(this.updateTimestampsCacheHitCount.get());
/* 518:608 */     LOG.timestampCacheMisses(this.updateTimestampsCacheMissCount.get());
/* 519:609 */     LOG.queryCacheHits(this.queryCacheHitCount.get());
/* 520:610 */     LOG.queryCacheMisses(this.queryCacheMissCount.get());
/* 521:611 */     LOG.maxQueryTime(this.queryExecutionMaxTime.get());
/* 522:    */   }
/* 523:    */   
/* 524:    */   public boolean isStatisticsEnabled()
/* 525:    */   {
/* 526:618 */     return this.isStatisticsEnabled;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void setStatisticsEnabled(boolean b)
/* 530:    */   {
/* 531:625 */     this.isStatisticsEnabled = b;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public long getQueryExecutionMaxTime()
/* 535:    */   {
/* 536:633 */     return this.queryExecutionMaxTime.get();
/* 537:    */   }
/* 538:    */   
/* 539:    */   public String[] getQueries()
/* 540:    */   {
/* 541:640 */     return ArrayHelper.toStringArray(this.queryStatistics.keySet());
/* 542:    */   }
/* 543:    */   
/* 544:    */   public String[] getEntityNames()
/* 545:    */   {
/* 546:647 */     if (this.sessionFactory == null) {
/* 547:648 */       return ArrayHelper.toStringArray(this.entityStatistics.keySet());
/* 548:    */     }
/* 549:651 */     return ArrayHelper.toStringArray(this.sessionFactory.getAllClassMetadata().keySet());
/* 550:    */   }
/* 551:    */   
/* 552:    */   public String[] getCollectionRoleNames()
/* 553:    */   {
/* 554:659 */     if (this.sessionFactory == null) {
/* 555:660 */       return ArrayHelper.toStringArray(this.collectionStatistics.keySet());
/* 556:    */     }
/* 557:663 */     return ArrayHelper.toStringArray(this.sessionFactory.getAllCollectionMetadata().keySet());
/* 558:    */   }
/* 559:    */   
/* 560:    */   public String[] getSecondLevelCacheRegionNames()
/* 561:    */   {
/* 562:671 */     if (this.sessionFactory == null) {
/* 563:672 */       return ArrayHelper.toStringArray(this.secondLevelCacheStatistics.keySet());
/* 564:    */     }
/* 565:675 */     return ArrayHelper.toStringArray(this.sessionFactory.getAllSecondLevelCacheRegions().keySet());
/* 566:    */   }
/* 567:    */   
/* 568:    */   public void endTransaction(boolean success)
/* 569:    */   {
/* 570:680 */     this.transactionCount.getAndIncrement();
/* 571:681 */     if (success) {
/* 572:682 */       this.committedTransactionCount.getAndIncrement();
/* 573:    */     }
/* 574:    */   }
/* 575:    */   
/* 576:    */   public long getSuccessfulTransactionCount()
/* 577:    */   {
/* 578:687 */     return this.committedTransactionCount.get();
/* 579:    */   }
/* 580:    */   
/* 581:    */   public long getTransactionCount()
/* 582:    */   {
/* 583:691 */     return this.transactionCount.get();
/* 584:    */   }
/* 585:    */   
/* 586:    */   public void closeStatement()
/* 587:    */   {
/* 588:695 */     this.closeStatementCount.getAndIncrement();
/* 589:    */   }
/* 590:    */   
/* 591:    */   public void prepareStatement()
/* 592:    */   {
/* 593:699 */     this.prepareStatementCount.getAndIncrement();
/* 594:    */   }
/* 595:    */   
/* 596:    */   public long getCloseStatementCount()
/* 597:    */   {
/* 598:703 */     return this.closeStatementCount.get();
/* 599:    */   }
/* 600:    */   
/* 601:    */   public long getPrepareStatementCount()
/* 602:    */   {
/* 603:707 */     return this.prepareStatementCount.get();
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void optimisticFailure(String entityName)
/* 607:    */   {
/* 608:711 */     this.optimisticFailureCount.getAndIncrement();
/* 609:712 */     ((ConcurrentEntityStatisticsImpl)getEntityStatistics(entityName)).incrementOptimisticFailureCount();
/* 610:    */   }
/* 611:    */   
/* 612:    */   public long getOptimisticFailureCount()
/* 613:    */   {
/* 614:716 */     return this.optimisticFailureCount.get();
/* 615:    */   }
/* 616:    */   
/* 617:    */   public String toString()
/* 618:    */   {
/* 619:721 */     return "Statistics[" + "start time=" + this.startTime + ",sessions opened=" + this.sessionOpenCount + ",sessions closed=" + this.sessionCloseCount + ",transactions=" + this.transactionCount + ",successful transactions=" + this.committedTransactionCount + ",optimistic lock failures=" + this.optimisticFailureCount + ",flushes=" + this.flushCount + ",connections obtained=" + this.connectCount + ",statements prepared=" + this.prepareStatementCount + ",statements closed=" + this.closeStatementCount + ",second level cache puts=" + this.secondLevelCachePutCount + ",second level cache hits=" + this.secondLevelCacheHitCount + ",second level cache misses=" + this.secondLevelCacheMissCount + ",entities loaded=" + this.entityLoadCount + ",entities updated=" + this.entityUpdateCount + ",entities inserted=" + this.entityInsertCount + ",entities deleted=" + this.entityDeleteCount + ",entities fetched=" + this.entityFetchCount + ",collections loaded=" + this.collectionLoadCount + ",collections updated=" + this.collectionUpdateCount + ",collections removed=" + this.collectionRemoveCount + ",collections recreated=" + this.collectionRecreateCount + ",collections fetched=" + this.collectionFetchCount + ",queries executed to database=" + this.queryExecutionCount + ",query cache puts=" + this.queryCachePutCount + ",query cache hits=" + this.queryCacheHitCount + ",query cache misses=" + this.queryCacheMissCount + ",update timestamps cache puts=" + this.updateTimestampsCachePutCount + ",update timestamps cache hits=" + this.updateTimestampsCacheHitCount + ",update timestamps cache misses=" + this.updateTimestampsCacheMissCount + ",max query time=" + this.queryExecutionMaxTime + ']';
/* 620:    */   }
/* 621:    */   
/* 622:    */   public String getQueryExecutionMaxTimeQueryString()
/* 623:    */   {
/* 624:759 */     return this.queryExecutionMaxTimeQueryString;
/* 625:    */   }
/* 626:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.internal.ConcurrentStatisticsImpl
 * JD-Core Version:    0.7.0.1
 */