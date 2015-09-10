/*   1:    */ package org.apache.http.impl.conn.tsccm;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Queue;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.concurrent.TimeUnit;
/*  12:    */ import java.util.concurrent.locks.Condition;
/*  13:    */ import java.util.concurrent.locks.Lock;
/*  14:    */ import org.apache.commons.logging.Log;
/*  15:    */ import org.apache.commons.logging.LogFactory;
/*  16:    */ import org.apache.http.annotation.ThreadSafe;
/*  17:    */ import org.apache.http.conn.ClientConnectionOperator;
/*  18:    */ import org.apache.http.conn.ConnectionPoolTimeoutException;
/*  19:    */ import org.apache.http.conn.OperatedClientConnection;
/*  20:    */ import org.apache.http.conn.params.ConnManagerParams;
/*  21:    */ import org.apache.http.conn.params.ConnPerRoute;
/*  22:    */ import org.apache.http.conn.routing.HttpRoute;
/*  23:    */ import org.apache.http.params.HttpParams;
/*  24:    */ 
/*  25:    */ @ThreadSafe
/*  26:    */ public class ConnPoolByRoute
/*  27:    */   extends AbstractConnPool
/*  28:    */ {
/*  29: 71 */   private final Log log = LogFactory.getLog(getClass());
/*  30:    */   private final Lock poolLock;
/*  31:    */   protected final ClientConnectionOperator operator;
/*  32:    */   protected final ConnPerRoute connPerRoute;
/*  33:    */   protected final Set<BasicPoolEntry> leasedConnections;
/*  34:    */   protected final Queue<BasicPoolEntry> freeConnections;
/*  35:    */   protected final Queue<WaitingThread> waitingThreads;
/*  36:    */   protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
/*  37:    */   private final long connTTL;
/*  38:    */   private final TimeUnit connTTLTimeUnit;
/*  39:    */   protected volatile boolean shutdown;
/*  40:    */   protected volatile int maxTotalConnections;
/*  41:    */   protected volatile int numConnections;
/*  42:    */   
/*  43:    */   public ConnPoolByRoute(ClientConnectionOperator operator, ConnPerRoute connPerRoute, int maxTotalConnections)
/*  44:    */   {
/*  45:112 */     this(operator, connPerRoute, maxTotalConnections, -1L, TimeUnit.MILLISECONDS);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ConnPoolByRoute(ClientConnectionOperator operator, ConnPerRoute connPerRoute, int maxTotalConnections, long connTTL, TimeUnit connTTLTimeUnit)
/*  49:    */   {
/*  50:125 */     if (operator == null) {
/*  51:126 */       throw new IllegalArgumentException("Connection operator may not be null");
/*  52:    */     }
/*  53:128 */     if (connPerRoute == null) {
/*  54:129 */       throw new IllegalArgumentException("Connections per route may not be null");
/*  55:    */     }
/*  56:131 */     this.poolLock = this.poolLock;
/*  57:132 */     this.leasedConnections = this.leasedConnections;
/*  58:133 */     this.operator = operator;
/*  59:134 */     this.connPerRoute = connPerRoute;
/*  60:135 */     this.maxTotalConnections = maxTotalConnections;
/*  61:136 */     this.freeConnections = createFreeConnQueue();
/*  62:137 */     this.waitingThreads = createWaitingThreadQueue();
/*  63:138 */     this.routeToPool = createRouteToPoolMap();
/*  64:139 */     this.connTTL = connTTL;
/*  65:140 */     this.connTTLTimeUnit = connTTLTimeUnit;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected Lock getLock()
/*  69:    */   {
/*  70:144 */     return this.poolLock;
/*  71:    */   }
/*  72:    */   
/*  73:    */   @Deprecated
/*  74:    */   public ConnPoolByRoute(ClientConnectionOperator operator, HttpParams params)
/*  75:    */   {
/*  76:154 */     this(operator, ConnManagerParams.getMaxConnectionsPerRoute(params), ConnManagerParams.getMaxTotalConnections(params));
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected Queue<BasicPoolEntry> createFreeConnQueue()
/*  80:    */   {
/*  81:166 */     return new LinkedList();
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected Queue<WaitingThread> createWaitingThreadQueue()
/*  85:    */   {
/*  86:176 */     return new LinkedList();
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap()
/*  90:    */   {
/*  91:186 */     return new HashMap();
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected RouteSpecificPool newRouteSpecificPool(HttpRoute route)
/*  95:    */   {
/*  96:199 */     return new RouteSpecificPool(route, this.connPerRoute);
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected WaitingThread newWaitingThread(Condition cond, RouteSpecificPool rospl)
/* 100:    */   {
/* 101:214 */     return new WaitingThread(cond, rospl);
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void closeConnection(BasicPoolEntry entry)
/* 105:    */   {
/* 106:218 */     OperatedClientConnection conn = entry.getConnection();
/* 107:219 */     if (conn != null) {
/* 108:    */       try
/* 109:    */       {
/* 110:221 */         conn.close();
/* 111:    */       }
/* 112:    */       catch (IOException ex)
/* 113:    */       {
/* 114:223 */         this.log.debug("I/O error closing connection", ex);
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected RouteSpecificPool getRoutePool(HttpRoute route, boolean create)
/* 120:    */   {
/* 121:239 */     RouteSpecificPool rospl = null;
/* 122:240 */     this.poolLock.lock();
/* 123:    */     try
/* 124:    */     {
/* 125:243 */       rospl = (RouteSpecificPool)this.routeToPool.get(route);
/* 126:244 */       if ((rospl == null) && (create))
/* 127:    */       {
/* 128:246 */         rospl = newRouteSpecificPool(route);
/* 129:247 */         this.routeToPool.put(route, rospl);
/* 130:    */       }
/* 131:    */     }
/* 132:    */     finally
/* 133:    */     {
/* 134:251 */       this.poolLock.unlock();
/* 135:    */     }
/* 136:254 */     return rospl;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int getConnectionsInPool(HttpRoute route)
/* 140:    */   {
/* 141:258 */     this.poolLock.lock();
/* 142:    */     try
/* 143:    */     {
/* 144:261 */       RouteSpecificPool rospl = getRoutePool(route, false);
/* 145:262 */       return rospl != null ? rospl.getEntryCount() : 0;
/* 146:    */     }
/* 147:    */     finally
/* 148:    */     {
/* 149:265 */       this.poolLock.unlock();
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public int getConnectionsInPool()
/* 154:    */   {
/* 155:270 */     this.poolLock.lock();
/* 156:    */     try
/* 157:    */     {
/* 158:272 */       return this.numConnections;
/* 159:    */     }
/* 160:    */     finally
/* 161:    */     {
/* 162:274 */       this.poolLock.unlock();
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public PoolEntryRequest requestPoolEntry(final HttpRoute route, final Object state)
/* 167:    */   {
/* 168:283 */     final WaitingThreadAborter aborter = new WaitingThreadAborter();
/* 169:    */     
/* 170:285 */     new PoolEntryRequest()
/* 171:    */     {
/* 172:    */       public void abortRequest()
/* 173:    */       {
/* 174:288 */         ConnPoolByRoute.this.poolLock.lock();
/* 175:    */         try
/* 176:    */         {
/* 177:290 */           aborter.abort();
/* 178:    */         }
/* 179:    */         finally
/* 180:    */         {
/* 181:292 */           ConnPoolByRoute.this.poolLock.unlock();
/* 182:    */         }
/* 183:    */       }
/* 184:    */       
/* 185:    */       public BasicPoolEntry getPoolEntry(long timeout, TimeUnit tunit)
/* 186:    */         throws InterruptedException, ConnectionPoolTimeoutException
/* 187:    */       {
/* 188:300 */         return ConnPoolByRoute.this.getEntryBlocking(route, state, timeout, tunit, aborter);
/* 189:    */       }
/* 190:    */     };
/* 191:    */   }
/* 192:    */   
/* 193:    */   protected BasicPoolEntry getEntryBlocking(HttpRoute route, Object state, long timeout, TimeUnit tunit, WaitingThreadAborter aborter)
/* 194:    */     throws ConnectionPoolTimeoutException, InterruptedException
/* 195:    */   {
/* 196:330 */     Date deadline = null;
/* 197:331 */     if (timeout > 0L) {
/* 198:332 */       deadline = new Date(System.currentTimeMillis() + tunit.toMillis(timeout));
/* 199:    */     }
/* 200:336 */     BasicPoolEntry entry = null;
/* 201:337 */     this.poolLock.lock();
/* 202:    */     try
/* 203:    */     {
/* 204:340 */       RouteSpecificPool rospl = getRoutePool(route, true);
/* 205:341 */       WaitingThread waitingThread = null;
/* 206:343 */       while (entry == null)
/* 207:    */       {
/* 208:345 */         if (this.shutdown) {
/* 209:346 */           throw new IllegalStateException("Connection pool shut down");
/* 210:    */         }
/* 211:349 */         if (this.log.isDebugEnabled()) {
/* 212:350 */           this.log.debug("[" + route + "] total kept alive: " + this.freeConnections.size() + ", total issued: " + this.leasedConnections.size() + ", total allocated: " + this.numConnections + " out of " + this.maxTotalConnections);
/* 213:    */         }
/* 214:361 */         entry = getFreeEntry(rospl, state);
/* 215:362 */         if (entry != null) {
/* 216:    */           break;
/* 217:    */         }
/* 218:366 */         boolean hasCapacity = rospl.getCapacity() > 0;
/* 219:368 */         if (this.log.isDebugEnabled()) {
/* 220:369 */           this.log.debug("Available capacity: " + rospl.getCapacity() + " out of " + rospl.getMaxEntries() + " [" + route + "][" + state + "]");
/* 221:    */         }
/* 222:374 */         if ((hasCapacity) && (this.numConnections < this.maxTotalConnections))
/* 223:    */         {
/* 224:376 */           entry = createEntry(rospl, this.operator);
/* 225:    */         }
/* 226:378 */         else if ((hasCapacity) && (!this.freeConnections.isEmpty()))
/* 227:    */         {
/* 228:380 */           deleteLeastUsedEntry();
/* 229:    */           
/* 230:    */ 
/* 231:383 */           rospl = getRoutePool(route, true);
/* 232:384 */           entry = createEntry(rospl, this.operator);
/* 233:    */         }
/* 234:    */         else
/* 235:    */         {
/* 236:388 */           if (this.log.isDebugEnabled()) {
/* 237:389 */             this.log.debug("Need to wait for connection [" + route + "][" + state + "]");
/* 238:    */           }
/* 239:393 */           if (waitingThread == null)
/* 240:    */           {
/* 241:394 */             waitingThread = newWaitingThread(this.poolLock.newCondition(), rospl);
/* 242:    */             
/* 243:396 */             aborter.setWaitingThread(waitingThread);
/* 244:    */           }
/* 245:399 */           boolean success = false;
/* 246:    */           try
/* 247:    */           {
/* 248:401 */             rospl.queueThread(waitingThread);
/* 249:402 */             this.waitingThreads.add(waitingThread);
/* 250:403 */             success = waitingThread.await(deadline);
/* 251:    */           }
/* 252:    */           finally
/* 253:    */           {
/* 254:410 */             rospl.removeThread(waitingThread);
/* 255:411 */             this.waitingThreads.remove(waitingThread);
/* 256:    */           }
/* 257:415 */           if ((!success) && (deadline != null) && (deadline.getTime() <= System.currentTimeMillis())) {
/* 258:417 */             throw new ConnectionPoolTimeoutException("Timeout waiting for connection");
/* 259:    */           }
/* 260:    */         }
/* 261:    */       }
/* 262:    */     }
/* 263:    */     finally
/* 264:    */     {
/* 265:424 */       this.poolLock.unlock();
/* 266:    */     }
/* 267:426 */     return entry;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void freeEntry(BasicPoolEntry entry, boolean reusable, long validDuration, TimeUnit timeUnit)
/* 271:    */   {
/* 272:432 */     HttpRoute route = entry.getPlannedRoute();
/* 273:433 */     if (this.log.isDebugEnabled()) {
/* 274:434 */       this.log.debug("Releasing connection [" + route + "][" + entry.getState() + "]");
/* 275:    */     }
/* 276:438 */     this.poolLock.lock();
/* 277:    */     try
/* 278:    */     {
/* 279:440 */       if (this.shutdown)
/* 280:    */       {
/* 281:443 */         closeConnection(entry);
/* 282:    */       }
/* 283:    */       else
/* 284:    */       {
/* 285:448 */         this.leasedConnections.remove(entry);
/* 286:    */         
/* 287:450 */         RouteSpecificPool rospl = getRoutePool(route, true);
/* 288:452 */         if (reusable)
/* 289:    */         {
/* 290:453 */           if (this.log.isDebugEnabled())
/* 291:    */           {
/* 292:    */             String s;
/* 293:    */             String s;
/* 294:455 */             if (validDuration > 0L) {
/* 295:456 */               s = "for " + validDuration + " " + timeUnit;
/* 296:    */             } else {
/* 297:458 */               s = "indefinitely";
/* 298:    */             }
/* 299:460 */             this.log.debug("Pooling connection [" + route + "][" + entry.getState() + "]; keep alive " + s);
/* 300:    */           }
/* 301:463 */           rospl.freeEntry(entry);
/* 302:464 */           entry.updateExpiry(validDuration, timeUnit);
/* 303:465 */           this.freeConnections.add(entry);
/* 304:    */         }
/* 305:    */         else
/* 306:    */         {
/* 307:467 */           rospl.dropEntry();
/* 308:468 */           this.numConnections -= 1;
/* 309:    */         }
/* 310:471 */         notifyWaitingThread(rospl);
/* 311:    */       }
/* 312:    */     }
/* 313:    */     finally
/* 314:    */     {
/* 315:474 */       this.poolLock.unlock();
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   protected BasicPoolEntry getFreeEntry(RouteSpecificPool rospl, Object state)
/* 320:    */   {
/* 321:488 */     BasicPoolEntry entry = null;
/* 322:489 */     this.poolLock.lock();
/* 323:    */     try
/* 324:    */     {
/* 325:491 */       boolean done = false;
/* 326:492 */       while (!done)
/* 327:    */       {
/* 328:494 */         entry = rospl.allocEntry(state);
/* 329:496 */         if (entry != null)
/* 330:    */         {
/* 331:497 */           if (this.log.isDebugEnabled()) {
/* 332:498 */             this.log.debug("Getting free connection [" + rospl.getRoute() + "][" + state + "]");
/* 333:    */           }
/* 334:502 */           this.freeConnections.remove(entry);
/* 335:503 */           if (entry.isExpired(System.currentTimeMillis()))
/* 336:    */           {
/* 337:506 */             if (this.log.isDebugEnabled()) {
/* 338:507 */               this.log.debug("Closing expired free connection [" + rospl.getRoute() + "][" + state + "]");
/* 339:    */             }
/* 340:509 */             closeConnection(entry);
/* 341:    */             
/* 342:    */ 
/* 343:    */ 
/* 344:513 */             rospl.dropEntry();
/* 345:514 */             this.numConnections -= 1;
/* 346:    */           }
/* 347:    */           else
/* 348:    */           {
/* 349:516 */             this.leasedConnections.add(entry);
/* 350:517 */             done = true;
/* 351:    */           }
/* 352:    */         }
/* 353:    */         else
/* 354:    */         {
/* 355:521 */           done = true;
/* 356:522 */           if (this.log.isDebugEnabled()) {
/* 357:523 */             this.log.debug("No free connections [" + rospl.getRoute() + "][" + state + "]");
/* 358:    */           }
/* 359:    */         }
/* 360:    */       }
/* 361:    */     }
/* 362:    */     finally
/* 363:    */     {
/* 364:529 */       this.poolLock.unlock();
/* 365:    */     }
/* 366:531 */     return entry;
/* 367:    */   }
/* 368:    */   
/* 369:    */   protected BasicPoolEntry createEntry(RouteSpecificPool rospl, ClientConnectionOperator op)
/* 370:    */   {
/* 371:548 */     if (this.log.isDebugEnabled()) {
/* 372:549 */       this.log.debug("Creating new connection [" + rospl.getRoute() + "]");
/* 373:    */     }
/* 374:553 */     BasicPoolEntry entry = new BasicPoolEntry(op, rospl.getRoute(), this.connTTL, this.connTTLTimeUnit);
/* 375:    */     
/* 376:555 */     this.poolLock.lock();
/* 377:    */     try
/* 378:    */     {
/* 379:557 */       rospl.createdEntry(entry);
/* 380:558 */       this.numConnections += 1;
/* 381:559 */       this.leasedConnections.add(entry);
/* 382:    */     }
/* 383:    */     finally
/* 384:    */     {
/* 385:561 */       this.poolLock.unlock();
/* 386:    */     }
/* 387:564 */     return entry;
/* 388:    */   }
/* 389:    */   
/* 390:    */   protected void deleteEntry(BasicPoolEntry entry)
/* 391:    */   {
/* 392:581 */     HttpRoute route = entry.getPlannedRoute();
/* 393:583 */     if (this.log.isDebugEnabled()) {
/* 394:584 */       this.log.debug("Deleting connection [" + route + "][" + entry.getState() + "]");
/* 395:    */     }
/* 396:588 */     this.poolLock.lock();
/* 397:    */     try
/* 398:    */     {
/* 399:591 */       closeConnection(entry);
/* 400:    */       
/* 401:593 */       RouteSpecificPool rospl = getRoutePool(route, true);
/* 402:594 */       rospl.deleteEntry(entry);
/* 403:595 */       this.numConnections -= 1;
/* 404:596 */       if (rospl.isUnused()) {
/* 405:597 */         this.routeToPool.remove(route);
/* 406:    */       }
/* 407:    */     }
/* 408:    */     finally
/* 409:    */     {
/* 410:601 */       this.poolLock.unlock();
/* 411:    */     }
/* 412:    */   }
/* 413:    */   
/* 414:    */   protected void deleteLeastUsedEntry()
/* 415:    */   {
/* 416:611 */     this.poolLock.lock();
/* 417:    */     try
/* 418:    */     {
/* 419:614 */       BasicPoolEntry entry = (BasicPoolEntry)this.freeConnections.remove();
/* 420:616 */       if (entry != null) {
/* 421:617 */         deleteEntry(entry);
/* 422:618 */       } else if (this.log.isDebugEnabled()) {
/* 423:619 */         this.log.debug("No free connection to delete");
/* 424:    */       }
/* 425:    */     }
/* 426:    */     finally
/* 427:    */     {
/* 428:623 */       this.poolLock.unlock();
/* 429:    */     }
/* 430:    */   }
/* 431:    */   
/* 432:    */   protected void handleLostEntry(HttpRoute route)
/* 433:    */   {
/* 434:630 */     this.poolLock.lock();
/* 435:    */     try
/* 436:    */     {
/* 437:633 */       RouteSpecificPool rospl = getRoutePool(route, true);
/* 438:634 */       rospl.dropEntry();
/* 439:635 */       if (rospl.isUnused()) {
/* 440:636 */         this.routeToPool.remove(route);
/* 441:    */       }
/* 442:639 */       this.numConnections -= 1;
/* 443:640 */       notifyWaitingThread(rospl);
/* 444:    */     }
/* 445:    */     finally
/* 446:    */     {
/* 447:643 */       this.poolLock.unlock();
/* 448:    */     }
/* 449:    */   }
/* 450:    */   
/* 451:    */   protected void notifyWaitingThread(RouteSpecificPool rospl)
/* 452:    */   {
/* 453:662 */     WaitingThread waitingThread = null;
/* 454:    */     
/* 455:664 */     this.poolLock.lock();
/* 456:    */     try
/* 457:    */     {
/* 458:667 */       if ((rospl != null) && (rospl.hasThread()))
/* 459:    */       {
/* 460:668 */         if (this.log.isDebugEnabled()) {
/* 461:669 */           this.log.debug("Notifying thread waiting on pool [" + rospl.getRoute() + "]");
/* 462:    */         }
/* 463:672 */         waitingThread = rospl.nextThread();
/* 464:    */       }
/* 465:673 */       else if (!this.waitingThreads.isEmpty())
/* 466:    */       {
/* 467:674 */         if (this.log.isDebugEnabled()) {
/* 468:675 */           this.log.debug("Notifying thread waiting on any pool");
/* 469:    */         }
/* 470:677 */         waitingThread = (WaitingThread)this.waitingThreads.remove();
/* 471:    */       }
/* 472:678 */       else if (this.log.isDebugEnabled())
/* 473:    */       {
/* 474:679 */         this.log.debug("Notifying no-one, there are no waiting threads");
/* 475:    */       }
/* 476:682 */       if (waitingThread != null) {
/* 477:683 */         waitingThread.wakeup();
/* 478:    */       }
/* 479:    */     }
/* 480:    */     finally
/* 481:    */     {
/* 482:687 */       this.poolLock.unlock();
/* 483:    */     }
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void deleteClosedConnections()
/* 487:    */   {
/* 488:694 */     this.poolLock.lock();
/* 489:    */     try
/* 490:    */     {
/* 491:696 */       Iterator<BasicPoolEntry> iter = this.freeConnections.iterator();
/* 492:697 */       while (iter.hasNext())
/* 493:    */       {
/* 494:698 */         BasicPoolEntry entry = (BasicPoolEntry)iter.next();
/* 495:699 */         if (!entry.getConnection().isOpen())
/* 496:    */         {
/* 497:700 */           iter.remove();
/* 498:701 */           deleteEntry(entry);
/* 499:    */         }
/* 500:    */       }
/* 501:    */     }
/* 502:    */     finally
/* 503:    */     {
/* 504:705 */       this.poolLock.unlock();
/* 505:    */     }
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void closeIdleConnections(long idletime, TimeUnit tunit)
/* 509:    */   {
/* 510:718 */     if (tunit == null) {
/* 511:719 */       throw new IllegalArgumentException("Time unit must not be null.");
/* 512:    */     }
/* 513:721 */     if (idletime < 0L) {
/* 514:722 */       idletime = 0L;
/* 515:    */     }
/* 516:724 */     if (this.log.isDebugEnabled()) {
/* 517:725 */       this.log.debug("Closing connections idle longer than " + idletime + " " + tunit);
/* 518:    */     }
/* 519:728 */     long deadline = System.currentTimeMillis() - tunit.toMillis(idletime);
/* 520:729 */     this.poolLock.lock();
/* 521:    */     try
/* 522:    */     {
/* 523:731 */       Iterator<BasicPoolEntry> iter = this.freeConnections.iterator();
/* 524:732 */       while (iter.hasNext())
/* 525:    */       {
/* 526:733 */         BasicPoolEntry entry = (BasicPoolEntry)iter.next();
/* 527:734 */         if (entry.getUpdated() <= deadline)
/* 528:    */         {
/* 529:735 */           if (this.log.isDebugEnabled()) {
/* 530:736 */             this.log.debug("Closing connection last used @ " + new Date(entry.getUpdated()));
/* 531:    */           }
/* 532:738 */           iter.remove();
/* 533:739 */           deleteEntry(entry);
/* 534:    */         }
/* 535:    */       }
/* 536:    */     }
/* 537:    */     finally
/* 538:    */     {
/* 539:743 */       this.poolLock.unlock();
/* 540:    */     }
/* 541:    */   }
/* 542:    */   
/* 543:    */   public void closeExpiredConnections()
/* 544:    */   {
/* 545:749 */     this.log.debug("Closing expired connections");
/* 546:750 */     long now = System.currentTimeMillis();
/* 547:    */     
/* 548:752 */     this.poolLock.lock();
/* 549:    */     try
/* 550:    */     {
/* 551:754 */       Iterator<BasicPoolEntry> iter = this.freeConnections.iterator();
/* 552:755 */       while (iter.hasNext())
/* 553:    */       {
/* 554:756 */         BasicPoolEntry entry = (BasicPoolEntry)iter.next();
/* 555:757 */         if (entry.isExpired(now))
/* 556:    */         {
/* 557:758 */           if (this.log.isDebugEnabled()) {
/* 558:759 */             this.log.debug("Closing connection expired @ " + new Date(entry.getExpiry()));
/* 559:    */           }
/* 560:761 */           iter.remove();
/* 561:762 */           deleteEntry(entry);
/* 562:    */         }
/* 563:    */       }
/* 564:    */     }
/* 565:    */     finally
/* 566:    */     {
/* 567:766 */       this.poolLock.unlock();
/* 568:    */     }
/* 569:    */   }
/* 570:    */   
/* 571:    */   public void shutdown()
/* 572:    */   {
/* 573:772 */     this.poolLock.lock();
/* 574:    */     try
/* 575:    */     {
/* 576:774 */       if (this.shutdown) {
/* 577:    */         return;
/* 578:    */       }
/* 579:777 */       this.shutdown = true;
/* 580:    */       
/* 581:    */ 
/* 582:780 */       Iterator<BasicPoolEntry> iter1 = this.leasedConnections.iterator();
/* 583:781 */       while (iter1.hasNext())
/* 584:    */       {
/* 585:782 */         BasicPoolEntry entry = (BasicPoolEntry)iter1.next();
/* 586:783 */         iter1.remove();
/* 587:784 */         closeConnection(entry);
/* 588:    */       }
/* 589:788 */       Iterator<BasicPoolEntry> iter2 = this.freeConnections.iterator();
/* 590:789 */       while (iter2.hasNext())
/* 591:    */       {
/* 592:790 */         BasicPoolEntry entry = (BasicPoolEntry)iter2.next();
/* 593:791 */         iter2.remove();
/* 594:793 */         if (this.log.isDebugEnabled()) {
/* 595:794 */           this.log.debug("Closing connection [" + entry.getPlannedRoute() + "][" + entry.getState() + "]");
/* 596:    */         }
/* 597:797 */         closeConnection(entry);
/* 598:    */       }
/* 599:801 */       Iterator<WaitingThread> iwth = this.waitingThreads.iterator();
/* 600:802 */       while (iwth.hasNext())
/* 601:    */       {
/* 602:803 */         WaitingThread waiter = (WaitingThread)iwth.next();
/* 603:804 */         iwth.remove();
/* 604:805 */         waiter.wakeup();
/* 605:    */       }
/* 606:808 */       this.routeToPool.clear();
/* 607:    */     }
/* 608:    */     finally
/* 609:    */     {
/* 610:811 */       this.poolLock.unlock();
/* 611:    */     }
/* 612:    */   }
/* 613:    */   
/* 614:    */   public void setMaxTotalConnections(int max)
/* 615:    */   {
/* 616:819 */     this.poolLock.lock();
/* 617:    */     try
/* 618:    */     {
/* 619:821 */       this.maxTotalConnections = max;
/* 620:    */     }
/* 621:    */     finally
/* 622:    */     {
/* 623:823 */       this.poolLock.unlock();
/* 624:    */     }
/* 625:    */   }
/* 626:    */   
/* 627:    */   public int getMaxTotalConnections()
/* 628:    */   {
/* 629:832 */     return this.maxTotalConnections;
/* 630:    */   }
/* 631:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.ConnPoolByRoute
 * JD-Core Version:    0.7.0.1
 */