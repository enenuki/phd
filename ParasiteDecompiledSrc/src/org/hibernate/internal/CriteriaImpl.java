/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.hibernate.CacheMode;
/*  10:    */ import org.hibernate.Criteria;
/*  11:    */ import org.hibernate.FetchMode;
/*  12:    */ import org.hibernate.FlushMode;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.LockMode;
/*  15:    */ import org.hibernate.ScrollMode;
/*  16:    */ import org.hibernate.ScrollableResults;
/*  17:    */ import org.hibernate.criterion.Criterion;
/*  18:    */ import org.hibernate.criterion.NaturalIdentifier;
/*  19:    */ import org.hibernate.criterion.Order;
/*  20:    */ import org.hibernate.criterion.Projection;
/*  21:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  22:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  23:    */ import org.hibernate.internal.util.StringHelper;
/*  24:    */ import org.hibernate.sql.JoinType;
/*  25:    */ import org.hibernate.transform.ResultTransformer;
/*  26:    */ 
/*  27:    */ public class CriteriaImpl
/*  28:    */   implements Criteria, Serializable
/*  29:    */ {
/*  30:    */   private final String entityOrClassName;
/*  31:    */   private transient SessionImplementor session;
/*  32:    */   private final String rootAlias;
/*  33: 60 */   private List criterionEntries = new ArrayList();
/*  34: 61 */   private List orderEntries = new ArrayList();
/*  35:    */   private Projection projection;
/*  36:    */   private Criteria projectionCriteria;
/*  37: 65 */   private List subcriteriaList = new ArrayList();
/*  38: 67 */   private Map fetchModes = new HashMap();
/*  39: 68 */   private Map lockModes = new HashMap();
/*  40:    */   private Integer maxResults;
/*  41:    */   private Integer firstResult;
/*  42:    */   private Integer timeout;
/*  43:    */   private Integer fetchSize;
/*  44:    */   private boolean cacheable;
/*  45:    */   private String cacheRegion;
/*  46:    */   private String comment;
/*  47:    */   private FlushMode flushMode;
/*  48:    */   private CacheMode cacheMode;
/*  49:    */   private FlushMode sessionFlushMode;
/*  50:    */   private CacheMode sessionCacheMode;
/*  51:    */   private Boolean readOnly;
/*  52: 86 */   private ResultTransformer resultTransformer = Criteria.ROOT_ENTITY;
/*  53:    */   
/*  54:    */   public CriteriaImpl(String entityOrClassName, SessionImplementor session)
/*  55:    */   {
/*  56: 92 */     this(entityOrClassName, "this", session);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public CriteriaImpl(String entityOrClassName, String alias, SessionImplementor session)
/*  60:    */   {
/*  61: 96 */     this.session = session;
/*  62: 97 */     this.entityOrClassName = entityOrClassName;
/*  63: 98 */     this.cacheable = false;
/*  64: 99 */     this.rootAlias = alias;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String toString()
/*  68:    */   {
/*  69:103 */     return "CriteriaImpl(" + this.entityOrClassName + ":" + (this.rootAlias == null ? "" : this.rootAlias) + this.subcriteriaList.toString() + this.criterionEntries.toString() + (this.projection == null ? "" : this.projection.toString()) + ')';
/*  70:    */   }
/*  71:    */   
/*  72:    */   public SessionImplementor getSession()
/*  73:    */   {
/*  74:116 */     return this.session;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setSession(SessionImplementor session)
/*  78:    */   {
/*  79:120 */     this.session = session;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getEntityOrClassName()
/*  83:    */   {
/*  84:124 */     return this.entityOrClassName;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Map getLockModes()
/*  88:    */   {
/*  89:128 */     return this.lockModes;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Criteria getProjectionCriteria()
/*  93:    */   {
/*  94:132 */     return this.projectionCriteria;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Iterator iterateSubcriteria()
/*  98:    */   {
/*  99:136 */     return this.subcriteriaList.iterator();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Iterator iterateExpressionEntries()
/* 103:    */   {
/* 104:140 */     return this.criterionEntries.iterator();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Iterator iterateOrderings()
/* 108:    */   {
/* 109:144 */     return this.orderEntries.iterator();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Criteria add(Criteria criteriaInst, Criterion expression)
/* 113:    */   {
/* 114:148 */     this.criterionEntries.add(new CriterionEntry(expression, criteriaInst, null));
/* 115:149 */     return this;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getAlias()
/* 119:    */   {
/* 120:156 */     return this.rootAlias;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Projection getProjection()
/* 124:    */   {
/* 125:160 */     return this.projection;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Criteria setProjection(Projection projection)
/* 129:    */   {
/* 130:164 */     this.projection = projection;
/* 131:165 */     this.projectionCriteria = this;
/* 132:166 */     setResultTransformer(PROJECTION);
/* 133:167 */     return this;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Criteria add(Criterion expression)
/* 137:    */   {
/* 138:171 */     add(this, expression);
/* 139:172 */     return this;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Criteria addOrder(Order ordering)
/* 143:    */   {
/* 144:176 */     this.orderEntries.add(new OrderEntry(ordering, this, null));
/* 145:177 */     return this;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public FetchMode getFetchMode(String path)
/* 149:    */   {
/* 150:181 */     return (FetchMode)this.fetchModes.get(path);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Criteria setFetchMode(String associationPath, FetchMode mode)
/* 154:    */   {
/* 155:185 */     this.fetchModes.put(associationPath, mode);
/* 156:186 */     return this;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Criteria setLockMode(LockMode lockMode)
/* 160:    */   {
/* 161:190 */     return setLockMode(getAlias(), lockMode);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Criteria setLockMode(String alias, LockMode lockMode)
/* 165:    */   {
/* 166:194 */     this.lockModes.put(alias, lockMode);
/* 167:195 */     return this;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Criteria createAlias(String associationPath, String alias)
/* 171:    */   {
/* 172:199 */     return createAlias(associationPath, alias, JoinType.INNER_JOIN);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Criteria createAlias(String associationPath, String alias, JoinType joinType)
/* 176:    */   {
/* 177:203 */     new Subcriteria(this, associationPath, alias, joinType, null);
/* 178:204 */     return this;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Criteria createAlias(String associationPath, String alias, int joinType)
/* 182:    */     throws HibernateException
/* 183:    */   {
/* 184:209 */     return createAlias(associationPath, alias, JoinType.parse(joinType));
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Criteria createAlias(String associationPath, String alias, JoinType joinType, Criterion withClause)
/* 188:    */   {
/* 189:213 */     new Subcriteria(this, associationPath, alias, joinType, withClause, null);
/* 190:214 */     return this;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Criteria createAlias(String associationPath, String alias, int joinType, Criterion withClause)
/* 194:    */     throws HibernateException
/* 195:    */   {
/* 196:220 */     return createAlias(associationPath, alias, JoinType.parse(joinType), withClause);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Criteria createCriteria(String associationPath)
/* 200:    */   {
/* 201:224 */     return createCriteria(associationPath, JoinType.INNER_JOIN);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public Criteria createCriteria(String associationPath, JoinType joinType)
/* 205:    */   {
/* 206:228 */     return new Subcriteria(this, associationPath, joinType, null);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Criteria createCriteria(String associationPath, int joinType)
/* 210:    */     throws HibernateException
/* 211:    */   {
/* 212:233 */     return createCriteria(associationPath, JoinType.parse(joinType));
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Criteria createCriteria(String associationPath, String alias)
/* 216:    */   {
/* 217:237 */     return createCriteria(associationPath, alias, JoinType.INNER_JOIN);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Criteria createCriteria(String associationPath, String alias, JoinType joinType)
/* 221:    */   {
/* 222:241 */     return new Subcriteria(this, associationPath, alias, joinType, null);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Criteria createCriteria(String associationPath, String alias, int joinType)
/* 226:    */     throws HibernateException
/* 227:    */   {
/* 228:246 */     return createCriteria(associationPath, alias, JoinType.parse(joinType));
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Criteria createCriteria(String associationPath, String alias, JoinType joinType, Criterion withClause)
/* 232:    */   {
/* 233:250 */     return new Subcriteria(this, associationPath, alias, joinType, withClause, null);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Criteria createCriteria(String associationPath, String alias, int joinType, Criterion withClause)
/* 237:    */     throws HibernateException
/* 238:    */   {
/* 239:256 */     return createCriteria(associationPath, alias, JoinType.parse(joinType), withClause);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public ResultTransformer getResultTransformer()
/* 243:    */   {
/* 244:260 */     return this.resultTransformer;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Criteria setResultTransformer(ResultTransformer tupleMapper)
/* 248:    */   {
/* 249:264 */     this.resultTransformer = tupleMapper;
/* 250:265 */     return this;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Integer getMaxResults()
/* 254:    */   {
/* 255:269 */     return this.maxResults;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Criteria setMaxResults(int maxResults)
/* 259:    */   {
/* 260:273 */     this.maxResults = Integer.valueOf(maxResults);
/* 261:274 */     return this;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public Integer getFirstResult()
/* 265:    */   {
/* 266:278 */     return this.firstResult;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public Criteria setFirstResult(int firstResult)
/* 270:    */   {
/* 271:282 */     this.firstResult = Integer.valueOf(firstResult);
/* 272:283 */     return this;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public Integer getFetchSize()
/* 276:    */   {
/* 277:287 */     return this.fetchSize;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Criteria setFetchSize(int fetchSize)
/* 281:    */   {
/* 282:291 */     this.fetchSize = Integer.valueOf(fetchSize);
/* 283:292 */     return this;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public Integer getTimeout()
/* 287:    */   {
/* 288:296 */     return this.timeout;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Criteria setTimeout(int timeout)
/* 292:    */   {
/* 293:300 */     this.timeout = Integer.valueOf(timeout);
/* 294:301 */     return this;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public boolean isReadOnlyInitialized()
/* 298:    */   {
/* 299:308 */     return this.readOnly != null;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public boolean isReadOnly()
/* 303:    */   {
/* 304:315 */     if ((!isReadOnlyInitialized()) && (getSession() == null)) {
/* 305:316 */       throw new IllegalStateException("cannot determine readOnly/modifiable setting when it is not initialized and is not initialized and getSession() == null");
/* 306:    */     }
/* 307:320 */     return isReadOnlyInitialized() ? this.readOnly.booleanValue() : getSession().getPersistenceContext().isDefaultReadOnly();
/* 308:    */   }
/* 309:    */   
/* 310:    */   public Criteria setReadOnly(boolean readOnly)
/* 311:    */   {
/* 312:330 */     this.readOnly = Boolean.valueOf(readOnly);
/* 313:331 */     return this;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public boolean getCacheable()
/* 317:    */   {
/* 318:335 */     return this.cacheable;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public Criteria setCacheable(boolean cacheable)
/* 322:    */   {
/* 323:339 */     this.cacheable = cacheable;
/* 324:340 */     return this;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public String getCacheRegion()
/* 328:    */   {
/* 329:344 */     return this.cacheRegion;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public Criteria setCacheRegion(String cacheRegion)
/* 333:    */   {
/* 334:348 */     this.cacheRegion = cacheRegion.trim();
/* 335:349 */     return this;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String getComment()
/* 339:    */   {
/* 340:353 */     return this.comment;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public Criteria setComment(String comment)
/* 344:    */   {
/* 345:357 */     this.comment = comment;
/* 346:358 */     return this;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public Criteria setFlushMode(FlushMode flushMode)
/* 350:    */   {
/* 351:362 */     this.flushMode = flushMode;
/* 352:363 */     return this;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public Criteria setCacheMode(CacheMode cacheMode)
/* 356:    */   {
/* 357:367 */     this.cacheMode = cacheMode;
/* 358:368 */     return this;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public List list()
/* 362:    */     throws HibernateException
/* 363:    */   {
/* 364:372 */     before();
/* 365:    */     try
/* 366:    */     {
/* 367:374 */       return this.session.list(this);
/* 368:    */     }
/* 369:    */     finally
/* 370:    */     {
/* 371:377 */       after();
/* 372:    */     }
/* 373:    */   }
/* 374:    */   
/* 375:    */   public ScrollableResults scroll()
/* 376:    */   {
/* 377:382 */     return scroll(ScrollMode.SCROLL_INSENSITIVE);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public ScrollableResults scroll(ScrollMode scrollMode)
/* 381:    */   {
/* 382:386 */     before();
/* 383:    */     try
/* 384:    */     {
/* 385:388 */       return this.session.scroll(this, scrollMode);
/* 386:    */     }
/* 387:    */     finally
/* 388:    */     {
/* 389:391 */       after();
/* 390:    */     }
/* 391:    */   }
/* 392:    */   
/* 393:    */   public Object uniqueResult()
/* 394:    */     throws HibernateException
/* 395:    */   {
/* 396:396 */     return AbstractQueryImpl.uniqueElement(list());
/* 397:    */   }
/* 398:    */   
/* 399:    */   protected void before()
/* 400:    */   {
/* 401:400 */     if (this.flushMode != null)
/* 402:    */     {
/* 403:401 */       this.sessionFlushMode = getSession().getFlushMode();
/* 404:402 */       getSession().setFlushMode(this.flushMode);
/* 405:    */     }
/* 406:404 */     if (this.cacheMode != null)
/* 407:    */     {
/* 408:405 */       this.sessionCacheMode = getSession().getCacheMode();
/* 409:406 */       getSession().setCacheMode(this.cacheMode);
/* 410:    */     }
/* 411:    */   }
/* 412:    */   
/* 413:    */   protected void after()
/* 414:    */   {
/* 415:411 */     if (this.sessionFlushMode != null)
/* 416:    */     {
/* 417:412 */       getSession().setFlushMode(this.sessionFlushMode);
/* 418:413 */       this.sessionFlushMode = null;
/* 419:    */     }
/* 420:415 */     if (this.sessionCacheMode != null)
/* 421:    */     {
/* 422:416 */       getSession().setCacheMode(this.sessionCacheMode);
/* 423:417 */       this.sessionCacheMode = null;
/* 424:    */     }
/* 425:    */   }
/* 426:    */   
/* 427:    */   public boolean isLookupByNaturalKey()
/* 428:    */   {
/* 429:422 */     if (this.projection != null) {
/* 430:423 */       return false;
/* 431:    */     }
/* 432:425 */     if (this.subcriteriaList.size() > 0) {
/* 433:426 */       return false;
/* 434:    */     }
/* 435:428 */     if (this.criterionEntries.size() != 1) {
/* 436:429 */       return false;
/* 437:    */     }
/* 438:431 */     CriterionEntry ce = (CriterionEntry)this.criterionEntries.get(0);
/* 439:432 */     return ce.getCriterion() instanceof NaturalIdentifier;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public final class Subcriteria
/* 443:    */     implements Criteria, Serializable
/* 444:    */   {
/* 445:    */     private String alias;
/* 446:    */     private String path;
/* 447:    */     private Criteria parent;
/* 448:    */     private LockMode lockMode;
/* 449:444 */     private JoinType joinType = JoinType.INNER_JOIN;
/* 450:    */     private Criterion withClause;
/* 451:    */     private boolean hasRestriction;
/* 452:    */     
/* 453:    */     private Subcriteria(Criteria parent, String path, String alias, JoinType joinType, Criterion withClause)
/* 454:    */     {
/* 455:451 */       this.alias = alias;
/* 456:452 */       this.path = path;
/* 457:453 */       this.parent = parent;
/* 458:454 */       this.joinType = joinType;
/* 459:455 */       this.withClause = withClause;
/* 460:456 */       this.hasRestriction = (withClause != null);
/* 461:457 */       CriteriaImpl.this.subcriteriaList.add(this);
/* 462:    */     }
/* 463:    */     
/* 464:    */     private Subcriteria(Criteria parent, String path, String alias, JoinType joinType)
/* 465:    */     {
/* 466:461 */       this(parent, path, alias, joinType, null);
/* 467:    */     }
/* 468:    */     
/* 469:    */     private Subcriteria(Criteria parent, String path, JoinType joinType)
/* 470:    */     {
/* 471:465 */       this(parent, path, null, joinType);
/* 472:    */     }
/* 473:    */     
/* 474:    */     public String toString()
/* 475:    */     {
/* 476:469 */       return "Subcriteria(" + this.path + ":" + (this.alias == null ? "" : this.alias) + ')';
/* 477:    */     }
/* 478:    */     
/* 479:    */     public String getAlias()
/* 480:    */     {
/* 481:479 */       return this.alias;
/* 482:    */     }
/* 483:    */     
/* 484:    */     public void setAlias(String alias)
/* 485:    */     {
/* 486:483 */       this.alias = alias;
/* 487:    */     }
/* 488:    */     
/* 489:    */     public String getPath()
/* 490:    */     {
/* 491:487 */       return this.path;
/* 492:    */     }
/* 493:    */     
/* 494:    */     public Criteria getParent()
/* 495:    */     {
/* 496:491 */       return this.parent;
/* 497:    */     }
/* 498:    */     
/* 499:    */     public LockMode getLockMode()
/* 500:    */     {
/* 501:495 */       return this.lockMode;
/* 502:    */     }
/* 503:    */     
/* 504:    */     public Criteria setLockMode(LockMode lockMode)
/* 505:    */     {
/* 506:499 */       this.lockMode = lockMode;
/* 507:500 */       return this;
/* 508:    */     }
/* 509:    */     
/* 510:    */     public JoinType getJoinType()
/* 511:    */     {
/* 512:504 */       return this.joinType;
/* 513:    */     }
/* 514:    */     
/* 515:    */     public Criterion getWithClause()
/* 516:    */     {
/* 517:508 */       return this.withClause;
/* 518:    */     }
/* 519:    */     
/* 520:    */     public boolean hasRestriction()
/* 521:    */     {
/* 522:512 */       return this.hasRestriction;
/* 523:    */     }
/* 524:    */     
/* 525:    */     public Criteria add(Criterion expression)
/* 526:    */     {
/* 527:518 */       this.hasRestriction = true;
/* 528:519 */       CriteriaImpl.this.add(this, expression);
/* 529:520 */       return this;
/* 530:    */     }
/* 531:    */     
/* 532:    */     public Criteria addOrder(Order order)
/* 533:    */     {
/* 534:524 */       CriteriaImpl.this.orderEntries.add(new CriteriaImpl.OrderEntry(order, this, null));
/* 535:525 */       return this;
/* 536:    */     }
/* 537:    */     
/* 538:    */     public Criteria createAlias(String associationPath, String alias)
/* 539:    */     {
/* 540:529 */       return createAlias(associationPath, alias, JoinType.INNER_JOIN);
/* 541:    */     }
/* 542:    */     
/* 543:    */     public Criteria createAlias(String associationPath, String alias, JoinType joinType)
/* 544:    */       throws HibernateException
/* 545:    */     {
/* 546:533 */       new Subcriteria(CriteriaImpl.this, this, associationPath, alias, joinType);
/* 547:534 */       return this;
/* 548:    */     }
/* 549:    */     
/* 550:    */     public Criteria createAlias(String associationPath, String alias, int joinType)
/* 551:    */       throws HibernateException
/* 552:    */     {
/* 553:539 */       return createAlias(associationPath, alias, JoinType.parse(joinType));
/* 554:    */     }
/* 555:    */     
/* 556:    */     public Criteria createAlias(String associationPath, String alias, JoinType joinType, Criterion withClause)
/* 557:    */       throws HibernateException
/* 558:    */     {
/* 559:543 */       new Subcriteria(CriteriaImpl.this, this, associationPath, alias, joinType, withClause);
/* 560:544 */       return this;
/* 561:    */     }
/* 562:    */     
/* 563:    */     public Criteria createAlias(String associationPath, String alias, int joinType, Criterion withClause)
/* 564:    */       throws HibernateException
/* 565:    */     {
/* 566:550 */       return createAlias(associationPath, alias, JoinType.parse(joinType), withClause);
/* 567:    */     }
/* 568:    */     
/* 569:    */     public Criteria createCriteria(String associationPath)
/* 570:    */     {
/* 571:554 */       return createCriteria(associationPath, JoinType.INNER_JOIN);
/* 572:    */     }
/* 573:    */     
/* 574:    */     public Criteria createCriteria(String associationPath, JoinType joinType)
/* 575:    */       throws HibernateException
/* 576:    */     {
/* 577:558 */       return new Subcriteria(CriteriaImpl.this, this, associationPath, joinType);
/* 578:    */     }
/* 579:    */     
/* 580:    */     public Criteria createCriteria(String associationPath, int joinType)
/* 581:    */       throws HibernateException
/* 582:    */     {
/* 583:563 */       return createCriteria(associationPath, JoinType.parse(joinType));
/* 584:    */     }
/* 585:    */     
/* 586:    */     public Criteria createCriteria(String associationPath, String alias)
/* 587:    */     {
/* 588:567 */       return createCriteria(associationPath, alias, JoinType.INNER_JOIN);
/* 589:    */     }
/* 590:    */     
/* 591:    */     public Criteria createCriteria(String associationPath, String alias, JoinType joinType)
/* 592:    */       throws HibernateException
/* 593:    */     {
/* 594:571 */       return new Subcriteria(CriteriaImpl.this, this, associationPath, alias, joinType);
/* 595:    */     }
/* 596:    */     
/* 597:    */     public Criteria createCriteria(String associationPath, String alias, int joinType)
/* 598:    */       throws HibernateException
/* 599:    */     {
/* 600:576 */       return createCriteria(associationPath, alias, JoinType.parse(joinType));
/* 601:    */     }
/* 602:    */     
/* 603:    */     public Criteria createCriteria(String associationPath, String alias, JoinType joinType, Criterion withClause)
/* 604:    */       throws HibernateException
/* 605:    */     {
/* 606:580 */       return new Subcriteria(CriteriaImpl.this, this, associationPath, alias, joinType, withClause);
/* 607:    */     }
/* 608:    */     
/* 609:    */     public Criteria createCriteria(String associationPath, String alias, int joinType, Criterion withClause)
/* 610:    */       throws HibernateException
/* 611:    */     {
/* 612:586 */       return createCriteria(associationPath, alias, JoinType.parse(joinType), withClause);
/* 613:    */     }
/* 614:    */     
/* 615:    */     public boolean isReadOnly()
/* 616:    */     {
/* 617:590 */       return CriteriaImpl.this.isReadOnly();
/* 618:    */     }
/* 619:    */     
/* 620:    */     public boolean isReadOnlyInitialized()
/* 621:    */     {
/* 622:594 */       return CriteriaImpl.this.isReadOnlyInitialized();
/* 623:    */     }
/* 624:    */     
/* 625:    */     public Criteria setReadOnly(boolean readOnly)
/* 626:    */     {
/* 627:598 */       CriteriaImpl.this.setReadOnly(readOnly);
/* 628:599 */       return this;
/* 629:    */     }
/* 630:    */     
/* 631:    */     public Criteria setCacheable(boolean cacheable)
/* 632:    */     {
/* 633:603 */       CriteriaImpl.this.setCacheable(cacheable);
/* 634:604 */       return this;
/* 635:    */     }
/* 636:    */     
/* 637:    */     public Criteria setCacheRegion(String cacheRegion)
/* 638:    */     {
/* 639:608 */       CriteriaImpl.this.setCacheRegion(cacheRegion);
/* 640:609 */       return this;
/* 641:    */     }
/* 642:    */     
/* 643:    */     public List list()
/* 644:    */       throws HibernateException
/* 645:    */     {
/* 646:613 */       return CriteriaImpl.this.list();
/* 647:    */     }
/* 648:    */     
/* 649:    */     public ScrollableResults scroll()
/* 650:    */       throws HibernateException
/* 651:    */     {
/* 652:617 */       return CriteriaImpl.this.scroll();
/* 653:    */     }
/* 654:    */     
/* 655:    */     public ScrollableResults scroll(ScrollMode scrollMode)
/* 656:    */       throws HibernateException
/* 657:    */     {
/* 658:621 */       return CriteriaImpl.this.scroll(scrollMode);
/* 659:    */     }
/* 660:    */     
/* 661:    */     public Object uniqueResult()
/* 662:    */       throws HibernateException
/* 663:    */     {
/* 664:625 */       return CriteriaImpl.this.uniqueResult();
/* 665:    */     }
/* 666:    */     
/* 667:    */     public Criteria setFetchMode(String associationPath, FetchMode mode)
/* 668:    */     {
/* 669:629 */       CriteriaImpl.this.setFetchMode(StringHelper.qualify(this.path, associationPath), mode);
/* 670:630 */       return this;
/* 671:    */     }
/* 672:    */     
/* 673:    */     public Criteria setFlushMode(FlushMode flushMode)
/* 674:    */     {
/* 675:634 */       CriteriaImpl.this.setFlushMode(flushMode);
/* 676:635 */       return this;
/* 677:    */     }
/* 678:    */     
/* 679:    */     public Criteria setCacheMode(CacheMode cacheMode)
/* 680:    */     {
/* 681:639 */       CriteriaImpl.this.setCacheMode(cacheMode);
/* 682:640 */       return this;
/* 683:    */     }
/* 684:    */     
/* 685:    */     public Criteria setFirstResult(int firstResult)
/* 686:    */     {
/* 687:644 */       CriteriaImpl.this.setFirstResult(firstResult);
/* 688:645 */       return this;
/* 689:    */     }
/* 690:    */     
/* 691:    */     public Criteria setMaxResults(int maxResults)
/* 692:    */     {
/* 693:649 */       CriteriaImpl.this.setMaxResults(maxResults);
/* 694:650 */       return this;
/* 695:    */     }
/* 696:    */     
/* 697:    */     public Criteria setTimeout(int timeout)
/* 698:    */     {
/* 699:654 */       CriteriaImpl.this.setTimeout(timeout);
/* 700:655 */       return this;
/* 701:    */     }
/* 702:    */     
/* 703:    */     public Criteria setFetchSize(int fetchSize)
/* 704:    */     {
/* 705:659 */       CriteriaImpl.this.setFetchSize(fetchSize);
/* 706:660 */       return this;
/* 707:    */     }
/* 708:    */     
/* 709:    */     public Criteria setLockMode(String alias, LockMode lockMode)
/* 710:    */     {
/* 711:664 */       CriteriaImpl.this.setLockMode(alias, lockMode);
/* 712:665 */       return this;
/* 713:    */     }
/* 714:    */     
/* 715:    */     public Criteria setResultTransformer(ResultTransformer resultProcessor)
/* 716:    */     {
/* 717:669 */       CriteriaImpl.this.setResultTransformer(resultProcessor);
/* 718:670 */       return this;
/* 719:    */     }
/* 720:    */     
/* 721:    */     public Criteria setComment(String comment)
/* 722:    */     {
/* 723:674 */       CriteriaImpl.this.setComment(comment);
/* 724:675 */       return this;
/* 725:    */     }
/* 726:    */     
/* 727:    */     public Criteria setProjection(Projection projection)
/* 728:    */     {
/* 729:679 */       CriteriaImpl.this.projection = projection;
/* 730:680 */       CriteriaImpl.this.projectionCriteria = this;
/* 731:681 */       setResultTransformer(PROJECTION);
/* 732:682 */       return this;
/* 733:    */     }
/* 734:    */   }
/* 735:    */   
/* 736:    */   public static final class CriterionEntry
/* 737:    */     implements Serializable
/* 738:    */   {
/* 739:    */     private final Criterion criterion;
/* 740:    */     private final Criteria criteria;
/* 741:    */     
/* 742:    */     private CriterionEntry(Criterion criterion, Criteria criteria)
/* 743:    */     {
/* 744:691 */       this.criteria = criteria;
/* 745:692 */       this.criterion = criterion;
/* 746:    */     }
/* 747:    */     
/* 748:    */     public Criterion getCriterion()
/* 749:    */     {
/* 750:696 */       return this.criterion;
/* 751:    */     }
/* 752:    */     
/* 753:    */     public Criteria getCriteria()
/* 754:    */     {
/* 755:700 */       return this.criteria;
/* 756:    */     }
/* 757:    */     
/* 758:    */     public String toString()
/* 759:    */     {
/* 760:704 */       return this.criterion.toString();
/* 761:    */     }
/* 762:    */   }
/* 763:    */   
/* 764:    */   public static final class OrderEntry
/* 765:    */     implements Serializable
/* 766:    */   {
/* 767:    */     private final Order order;
/* 768:    */     private final Criteria criteria;
/* 769:    */     
/* 770:    */     private OrderEntry(Order order, Criteria criteria)
/* 771:    */     {
/* 772:713 */       this.criteria = criteria;
/* 773:714 */       this.order = order;
/* 774:    */     }
/* 775:    */     
/* 776:    */     public Order getOrder()
/* 777:    */     {
/* 778:718 */       return this.order;
/* 779:    */     }
/* 780:    */     
/* 781:    */     public Criteria getCriteria()
/* 782:    */     {
/* 783:722 */       return this.criteria;
/* 784:    */     }
/* 785:    */     
/* 786:    */     public String toString()
/* 787:    */     {
/* 788:726 */       return this.order.toString();
/* 789:    */     }
/* 790:    */   }
/* 791:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.CriteriaImpl
 * JD-Core Version:    0.7.0.1
 */