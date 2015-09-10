/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.dtm.DTMIterator;
/*   6:    */ import org.apache.xml.dtm.DTMManager;
/*   7:    */ import org.apache.xml.utils.NodeVector;
/*   8:    */ import org.apache.xpath.Expression;
/*   9:    */ import org.apache.xpath.NodeSetDTM;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.apache.xpath.objects.XObject;
/*  12:    */ 
/*  13:    */ public class NodeSequence
/*  14:    */   extends XObject
/*  15:    */   implements DTMIterator, Cloneable, PathComponent
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = 3866261934726581044L;
/*  18: 43 */   protected int m_last = -1;
/*  19: 50 */   protected int m_next = 0;
/*  20:    */   private IteratorCache m_cache;
/*  21:    */   protected DTMIterator m_iter;
/*  22:    */   protected DTMManager m_dtmMgr;
/*  23:    */   
/*  24:    */   protected NodeVector getVector()
/*  25:    */   {
/*  26: 66 */     NodeVector nv = this.m_cache != null ? this.m_cache.getVector() : null;
/*  27: 67 */     return nv;
/*  28:    */   }
/*  29:    */   
/*  30:    */   private IteratorCache getCache()
/*  31:    */   {
/*  32: 77 */     return this.m_cache;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected void SetVector(NodeVector v)
/*  36:    */   {
/*  37: 85 */     setObject(v);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean hasCache()
/*  41:    */   {
/*  42: 95 */     NodeVector nv = getVector();
/*  43: 96 */     return nv != null;
/*  44:    */   }
/*  45:    */   
/*  46:    */   private boolean cacheComplete()
/*  47:    */   {
/*  48:    */     boolean complete;
/*  49:106 */     if (this.m_cache != null) {
/*  50:107 */       complete = this.m_cache.isComplete();
/*  51:    */     } else {
/*  52:109 */       complete = false;
/*  53:    */     }
/*  54:111 */     return complete;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void markCacheComplete()
/*  58:    */   {
/*  59:119 */     NodeVector nv = getVector();
/*  60:120 */     if (nv != null) {
/*  61:121 */       this.m_cache.setCacheComplete(true);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public final void setIter(DTMIterator iter)
/*  66:    */   {
/*  67:137 */     this.m_iter = iter;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final DTMIterator getContainedIter()
/*  71:    */   {
/*  72:146 */     return this.m_iter;
/*  73:    */   }
/*  74:    */   
/*  75:    */   private NodeSequence(DTMIterator iter, int context, XPathContext xctxt, boolean shouldCacheNodes)
/*  76:    */   {
/*  77:167 */     setIter(iter);
/*  78:168 */     setRoot(context, xctxt);
/*  79:169 */     setShouldCacheNodes(shouldCacheNodes);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public NodeSequence(Object nodeVector)
/*  83:    */   {
/*  84:179 */     super(nodeVector);
/*  85:180 */     if ((nodeVector instanceof NodeVector)) {
/*  86:181 */       SetVector((NodeVector)nodeVector);
/*  87:    */     }
/*  88:183 */     if (null != nodeVector)
/*  89:    */     {
/*  90:185 */       assertion(nodeVector instanceof NodeVector, "Must have a NodeVector as the object for NodeSequence!");
/*  91:187 */       if ((nodeVector instanceof DTMIterator))
/*  92:    */       {
/*  93:189 */         setIter((DTMIterator)nodeVector);
/*  94:190 */         this.m_last = ((DTMIterator)nodeVector).getLength();
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private NodeSequence(DTMManager dtmMgr)
/* 100:    */   {
/* 101:202 */     super(new NodeVector());
/* 102:203 */     this.m_last = 0;
/* 103:204 */     this.m_dtmMgr = dtmMgr;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public NodeSequence() {}
/* 107:    */   
/* 108:    */   public DTM getDTM(int nodeHandle)
/* 109:    */   {
/* 110:222 */     DTMManager mgr = getDTMManager();
/* 111:223 */     if (null != mgr) {
/* 112:224 */       return getDTMManager().getDTM(nodeHandle);
/* 113:    */     }
/* 114:227 */     assertion(false, "Can not get a DTM Unless a DTMManager has been set!");
/* 115:228 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public DTMManager getDTMManager()
/* 119:    */   {
/* 120:237 */     return this.m_dtmMgr;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getRoot()
/* 124:    */   {
/* 125:245 */     if (null != this.m_iter) {
/* 126:246 */       return this.m_iter.getRoot();
/* 127:    */     }
/* 128:252 */     return -1;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setRoot(int nodeHandle, Object environment)
/* 132:    */   {
/* 133:261 */     if (null != this.m_iter)
/* 134:    */     {
/* 135:263 */       XPathContext xctxt = (XPathContext)environment;
/* 136:264 */       this.m_dtmMgr = xctxt.getDTMManager();
/* 137:265 */       this.m_iter.setRoot(nodeHandle, environment);
/* 138:266 */       if (!this.m_iter.isDocOrdered())
/* 139:    */       {
/* 140:268 */         if (!hasCache()) {
/* 141:269 */           setShouldCacheNodes(true);
/* 142:    */         }
/* 143:270 */         runTo(-1);
/* 144:271 */         this.m_next = 0;
/* 145:    */       }
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:275 */       assertion(false, "Can not setRoot on a non-iterated NodeSequence!");
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void reset()
/* 154:    */   {
/* 155:283 */     this.m_next = 0;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getWhatToShow()
/* 159:    */   {
/* 160:292 */     return hasCache() ? -17 : this.m_iter.getWhatToShow();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean getExpandEntityReferences()
/* 164:    */   {
/* 165:301 */     if (null != this.m_iter) {
/* 166:302 */       return this.m_iter.getExpandEntityReferences();
/* 167:    */     }
/* 168:304 */     return true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int nextNode()
/* 172:    */   {
/* 173:314 */     NodeVector vec = getVector();
/* 174:315 */     if (null != vec)
/* 175:    */     {
/* 176:318 */       if (this.m_next < vec.size())
/* 177:    */       {
/* 178:321 */         int next = vec.elementAt(this.m_next);
/* 179:322 */         this.m_next += 1;
/* 180:323 */         return next;
/* 181:    */       }
/* 182:325 */       if ((cacheComplete()) || (-1 != this.m_last) || (null == this.m_iter))
/* 183:    */       {
/* 184:327 */         this.m_next += 1;
/* 185:328 */         return -1;
/* 186:    */       }
/* 187:    */     }
/* 188:332 */     if (null == this.m_iter) {
/* 189:333 */       return -1;
/* 190:    */     }
/* 191:335 */     int next = this.m_iter.nextNode();
/* 192:336 */     if (-1 != next)
/* 193:    */     {
/* 194:338 */       if (hasCache())
/* 195:    */       {
/* 196:340 */         if (this.m_iter.isDocOrdered())
/* 197:    */         {
/* 198:342 */           getVector().addElement(next);
/* 199:343 */           this.m_next += 1;
/* 200:    */         }
/* 201:    */         else
/* 202:    */         {
/* 203:347 */           int insertIndex = addNodeInDocOrder(next);
/* 204:348 */           if (insertIndex >= 0) {
/* 205:349 */             this.m_next += 1;
/* 206:    */           }
/* 207:    */         }
/* 208:    */       }
/* 209:    */       else {
/* 210:353 */         this.m_next += 1;
/* 211:    */       }
/* 212:    */     }
/* 213:    */     else
/* 214:    */     {
/* 215:360 */       markCacheComplete();
/* 216:    */       
/* 217:362 */       this.m_last = this.m_next;
/* 218:363 */       this.m_next += 1;
/* 219:    */     }
/* 220:366 */     return next;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public int previousNode()
/* 224:    */   {
/* 225:374 */     if (hasCache())
/* 226:    */     {
/* 227:376 */       if (this.m_next <= 0) {
/* 228:377 */         return -1;
/* 229:    */       }
/* 230:380 */       this.m_next -= 1;
/* 231:381 */       return item(this.m_next);
/* 232:    */     }
/* 233:386 */     int n = this.m_iter.previousNode();
/* 234:387 */     this.m_next = this.m_iter.getCurrentPos();
/* 235:388 */     return this.m_next;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void detach()
/* 239:    */   {
/* 240:397 */     if (null != this.m_iter) {
/* 241:398 */       this.m_iter.detach();
/* 242:    */     }
/* 243:399 */     super.detach();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void allowDetachToRelease(boolean allowRelease)
/* 247:    */   {
/* 248:409 */     if ((false == allowRelease) && (!hasCache())) {
/* 249:411 */       setShouldCacheNodes(true);
/* 250:    */     }
/* 251:414 */     if (null != this.m_iter) {
/* 252:415 */       this.m_iter.allowDetachToRelease(allowRelease);
/* 253:    */     }
/* 254:416 */     super.allowDetachToRelease(allowRelease);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public int getCurrentNode()
/* 258:    */   {
/* 259:424 */     if (hasCache())
/* 260:    */     {
/* 261:426 */       int currentIndex = this.m_next - 1;
/* 262:427 */       NodeVector vec = getVector();
/* 263:428 */       if ((currentIndex >= 0) && (currentIndex < vec.size())) {
/* 264:429 */         return vec.elementAt(currentIndex);
/* 265:    */       }
/* 266:431 */       return -1;
/* 267:    */     }
/* 268:434 */     if (null != this.m_iter) {
/* 269:436 */       return this.m_iter.getCurrentNode();
/* 270:    */     }
/* 271:439 */     return -1;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean isFresh()
/* 275:    */   {
/* 276:447 */     return 0 == this.m_next;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setShouldCacheNodes(boolean b)
/* 280:    */   {
/* 281:455 */     if (b)
/* 282:    */     {
/* 283:457 */       if (!hasCache()) {
/* 284:459 */         SetVector(new NodeVector());
/* 285:    */       }
/* 286:    */     }
/* 287:    */     else {
/* 288:465 */       SetVector(null);
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public boolean isMutable()
/* 293:    */   {
/* 294:473 */     return hasCache();
/* 295:    */   }
/* 296:    */   
/* 297:    */   public int getCurrentPos()
/* 298:    */   {
/* 299:481 */     return this.m_next;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void runTo(int index)
/* 303:    */   {
/* 304:    */     int n;
/* 305:491 */     if (-1 == index)
/* 306:    */     {
/* 307:493 */       int pos = this.m_next;
/* 308:494 */       while (-1 != (n = nextNode())) {}
/* 309:495 */       this.m_next = pos;
/* 310:    */     }
/* 311:    */     else
/* 312:    */     {
/* 313:497 */       if (this.m_next == index) {
/* 314:499 */         return;
/* 315:    */       }
/* 316:501 */       if ((hasCache()) && (this.m_next < getVector().size())) {
/* 317:503 */         this.m_next = index;
/* 318:505 */       } else if ((null == getVector()) && (index < this.m_next)) {
/* 319:507 */         for (goto 88; this.m_next >= index;) {
/* 320:507 */           if (-1 == (n = previousNode())) {
/* 321:511 */             while ((this.m_next < index) && (-1 != (n = nextNode()))) {}
/* 322:    */           }
/* 323:    */         }
/* 324:    */       }
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setCurrentPos(int i)
/* 329:    */   {
/* 330:521 */     runTo(i);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public int item(int index)
/* 334:    */   {
/* 335:529 */     setCurrentPos(index);
/* 336:530 */     int n = nextNode();
/* 337:531 */     this.m_next = index;
/* 338:532 */     return n;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setItem(int node, int index)
/* 342:    */   {
/* 343:540 */     NodeVector vec = getVector();
/* 344:541 */     if (null != vec)
/* 345:    */     {
/* 346:543 */       int oldNode = vec.elementAt(index);
/* 347:544 */       if ((oldNode != node) && (this.m_cache.useCount() > 1))
/* 348:    */       {
/* 349:552 */         IteratorCache newCache = new IteratorCache();
/* 350:    */         NodeVector nv;
/* 351:    */         try
/* 352:    */         {
/* 353:555 */           nv = (NodeVector)vec.clone();
/* 354:    */         }
/* 355:    */         catch (CloneNotSupportedException e)
/* 356:    */         {
/* 357:558 */           e.printStackTrace();
/* 358:559 */           RuntimeException rte = new RuntimeException(e.getMessage());
/* 359:560 */           throw rte;
/* 360:    */         }
/* 361:562 */         newCache.setVector(nv);
/* 362:563 */         newCache.setCacheComplete(true);
/* 363:564 */         this.m_cache = newCache;
/* 364:565 */         vec = nv;
/* 365:    */         
/* 366:    */ 
/* 367:568 */         super.setObject(nv);
/* 368:    */       }
/* 369:577 */       vec.setElementAt(node, index);
/* 370:578 */       this.m_last = vec.size();
/* 371:    */     }
/* 372:    */     else
/* 373:    */     {
/* 374:581 */       this.m_iter.setItem(node, index);
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   public int getLength()
/* 379:    */   {
/* 380:589 */     IteratorCache cache = getCache();
/* 381:591 */     if (cache != null)
/* 382:    */     {
/* 383:594 */       if (cache.isComplete())
/* 384:    */       {
/* 385:597 */         NodeVector nv = cache.getVector();
/* 386:598 */         return nv.size();
/* 387:    */       }
/* 388:604 */       if ((this.m_iter instanceof NodeSetDTM)) {
/* 389:606 */         return this.m_iter.getLength();
/* 390:    */       }
/* 391:609 */       if (-1 == this.m_last)
/* 392:    */       {
/* 393:611 */         int pos = this.m_next;
/* 394:612 */         runTo(-1);
/* 395:613 */         this.m_next = pos;
/* 396:    */       }
/* 397:615 */       return this.m_last;
/* 398:    */     }
/* 399:619 */     return -1 == this.m_last ? (this.m_last = this.m_iter.getLength()) : this.m_last;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public DTMIterator cloneWithReset()
/* 403:    */     throws CloneNotSupportedException
/* 404:    */   {
/* 405:629 */     NodeSequence seq = (NodeSequence)super.clone();
/* 406:630 */     seq.m_next = 0;
/* 407:631 */     if (this.m_cache != null) {
/* 408:637 */       this.m_cache.increaseUseCount();
/* 409:    */     }
/* 410:640 */     return seq;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public Object clone()
/* 414:    */     throws CloneNotSupportedException
/* 415:    */   {
/* 416:654 */     NodeSequence clone = (NodeSequence)super.clone();
/* 417:655 */     if (null != this.m_iter) {
/* 418:655 */       clone.m_iter = ((DTMIterator)this.m_iter.clone());
/* 419:    */     }
/* 420:656 */     if (this.m_cache != null) {
/* 421:662 */       this.m_cache.increaseUseCount();
/* 422:    */     }
/* 423:665 */     return clone;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public boolean isDocOrdered()
/* 427:    */   {
/* 428:674 */     if (null != this.m_iter) {
/* 429:675 */       return this.m_iter.isDocOrdered();
/* 430:    */     }
/* 431:677 */     return true;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public int getAxis()
/* 435:    */   {
/* 436:685 */     if (null != this.m_iter) {
/* 437:686 */       return this.m_iter.getAxis();
/* 438:    */     }
/* 439:689 */     assertion(false, "Can not getAxis from a non-iterated node sequence!");
/* 440:690 */     return 0;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public int getAnalysisBits()
/* 444:    */   {
/* 445:699 */     if ((null != this.m_iter) && ((this.m_iter instanceof PathComponent))) {
/* 446:700 */       return ((PathComponent)this.m_iter).getAnalysisBits();
/* 447:    */     }
/* 448:702 */     return 0;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void fixupVariables(Vector vars, int globalsSize)
/* 452:    */   {
/* 453:710 */     super.fixupVariables(vars, globalsSize);
/* 454:    */   }
/* 455:    */   
/* 456:    */   protected int addNodeInDocOrder(int node)
/* 457:    */   {
/* 458:723 */     assertion(hasCache(), "addNodeInDocOrder must be done on a mutable sequence!");
/* 459:    */     
/* 460:725 */     int insertIndex = -1;
/* 461:    */     
/* 462:727 */     NodeVector vec = getVector();
/* 463:    */     
/* 464:    */ 
/* 465:    */ 
/* 466:    */ 
/* 467:732 */     int size = vec.size();
/* 468:734 */     for (int i = size - 1; i >= 0; i--)
/* 469:    */     {
/* 470:736 */       int child = vec.elementAt(i);
/* 471:738 */       if (child == node)
/* 472:    */       {
/* 473:740 */         i = -2;
/* 474:    */       }
/* 475:    */       else
/* 476:    */       {
/* 477:745 */         DTM dtm = this.m_dtmMgr.getDTM(node);
/* 478:746 */         if (!dtm.isNodeAfter(node, child)) {
/* 479:    */           break;
/* 480:    */         }
/* 481:    */       }
/* 482:    */     }
/* 483:752 */     if (i != -2)
/* 484:    */     {
/* 485:754 */       insertIndex = i + 1;
/* 486:    */       
/* 487:756 */       vec.insertElementAt(node, insertIndex);
/* 488:    */     }
/* 489:760 */     return insertIndex;
/* 490:    */   }
/* 491:    */   
/* 492:    */   protected void setObject(Object obj)
/* 493:    */   {
/* 494:778 */     if ((obj instanceof NodeVector))
/* 495:    */     {
/* 496:781 */       super.setObject(obj);
/* 497:    */       
/* 498:    */ 
/* 499:784 */       NodeVector v = (NodeVector)obj;
/* 500:785 */       if (this.m_cache != null)
/* 501:    */       {
/* 502:786 */         this.m_cache.setVector(v);
/* 503:    */       }
/* 504:787 */       else if (v != null)
/* 505:    */       {
/* 506:788 */         this.m_cache = new IteratorCache();
/* 507:789 */         this.m_cache.setVector(v);
/* 508:    */       }
/* 509:    */     }
/* 510:791 */     else if ((obj instanceof IteratorCache))
/* 511:    */     {
/* 512:792 */       IteratorCache cache = (IteratorCache)obj;
/* 513:793 */       this.m_cache = cache;
/* 514:794 */       this.m_cache.increaseUseCount();
/* 515:    */       
/* 516:    */ 
/* 517:797 */       super.setObject(cache.getVector());
/* 518:    */     }
/* 519:    */     else
/* 520:    */     {
/* 521:799 */       super.setObject(obj);
/* 522:    */     }
/* 523:    */   }
/* 524:    */   
/* 525:    */   private static final class IteratorCache
/* 526:    */   {
/* 527:    */     private NodeVector m_vec2;
/* 528:    */     private boolean m_isComplete2;
/* 529:    */     private int m_useCount2;
/* 530:    */     
/* 531:    */     IteratorCache()
/* 532:    */     {
/* 533:880 */       this.m_vec2 = null;
/* 534:881 */       this.m_isComplete2 = false;
/* 535:882 */       this.m_useCount2 = 1;
/* 536:    */     }
/* 537:    */     
/* 538:    */     private int useCount()
/* 539:    */     {
/* 540:891 */       return this.m_useCount2;
/* 541:    */     }
/* 542:    */     
/* 543:    */     private void increaseUseCount()
/* 544:    */     {
/* 545:901 */       if (this.m_vec2 != null) {
/* 546:902 */         this.m_useCount2 += 1;
/* 547:    */       }
/* 548:    */     }
/* 549:    */     
/* 550:    */     private void setVector(NodeVector nv)
/* 551:    */     {
/* 552:912 */       this.m_vec2 = nv;
/* 553:913 */       this.m_useCount2 = 1;
/* 554:    */     }
/* 555:    */     
/* 556:    */     private NodeVector getVector()
/* 557:    */     {
/* 558:921 */       return this.m_vec2;
/* 559:    */     }
/* 560:    */     
/* 561:    */     private void setCacheComplete(boolean b)
/* 562:    */     {
/* 563:930 */       this.m_isComplete2 = b;
/* 564:    */     }
/* 565:    */     
/* 566:    */     private boolean isComplete()
/* 567:    */     {
/* 568:939 */       return this.m_isComplete2;
/* 569:    */     }
/* 570:    */   }
/* 571:    */   
/* 572:    */   protected IteratorCache getIteratorCache()
/* 573:    */   {
/* 574:950 */     return this.m_cache;
/* 575:    */   }
/* 576:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.NodeSequence
 * JD-Core Version:    0.7.0.1
 */