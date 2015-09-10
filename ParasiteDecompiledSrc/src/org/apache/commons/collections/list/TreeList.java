/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.util.AbstractList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.ConcurrentModificationException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.ListIterator;
/*   8:    */ import java.util.NoSuchElementException;
/*   9:    */ import org.apache.commons.collections.OrderedIterator;
/*  10:    */ 
/*  11:    */ public class TreeList
/*  12:    */   extends AbstractList
/*  13:    */ {
/*  14:    */   private AVLNode root;
/*  15:    */   private int size;
/*  16:    */   
/*  17:    */   public TreeList() {}
/*  18:    */   
/*  19:    */   public TreeList(Collection coll)
/*  20:    */   {
/*  21: 87 */     addAll(coll);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Object get(int index)
/*  25:    */   {
/*  26: 98 */     checkInterval(index, 0, size() - 1);
/*  27: 99 */     return this.root.get(index).getValue();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int size()
/*  31:    */   {
/*  32:108 */     return this.size;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Iterator iterator()
/*  36:    */   {
/*  37:118 */     return listIterator(0);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ListIterator listIterator()
/*  41:    */   {
/*  42:128 */     return listIterator(0);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ListIterator listIterator(int fromIndex)
/*  46:    */   {
/*  47:140 */     checkInterval(fromIndex, 0, size());
/*  48:141 */     return new TreeListIterator(this, fromIndex);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int indexOf(Object object)
/*  52:    */   {
/*  53:151 */     if (this.root == null) {
/*  54:152 */       return -1;
/*  55:    */     }
/*  56:154 */     return this.root.indexOf(object, this.root.relativePosition);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean contains(Object object)
/*  60:    */   {
/*  61:163 */     return indexOf(object) >= 0;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object[] toArray()
/*  65:    */   {
/*  66:173 */     Object[] array = new Object[size()];
/*  67:174 */     if (this.root != null) {
/*  68:175 */       this.root.toArray(array, this.root.relativePosition);
/*  69:    */     }
/*  70:177 */     return array;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void add(int index, Object obj)
/*  74:    */   {
/*  75:188 */     this.modCount += 1;
/*  76:189 */     checkInterval(index, 0, size());
/*  77:190 */     if (this.root == null) {
/*  78:191 */       this.root = new AVLNode(index, obj, null, null, null);
/*  79:    */     } else {
/*  80:193 */       this.root = this.root.insert(index, obj);
/*  81:    */     }
/*  82:195 */     this.size += 1;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Object set(int index, Object obj)
/*  86:    */   {
/*  87:207 */     checkInterval(index, 0, size() - 1);
/*  88:208 */     AVLNode node = this.root.get(index);
/*  89:209 */     Object result = node.value;
/*  90:210 */     node.setValue(obj);
/*  91:211 */     return result;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Object remove(int index)
/*  95:    */   {
/*  96:221 */     this.modCount += 1;
/*  97:222 */     checkInterval(index, 0, size() - 1);
/*  98:223 */     Object result = get(index);
/*  99:224 */     this.root = this.root.remove(index);
/* 100:225 */     this.size -= 1;
/* 101:226 */     return result;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void clear()
/* 105:    */   {
/* 106:233 */     this.modCount += 1;
/* 107:234 */     this.root = null;
/* 108:235 */     this.size = 0;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void checkInterval(int index, int startIndex, int endIndex)
/* 112:    */   {
/* 113:248 */     if ((index < startIndex) || (index > endIndex)) {
/* 114:249 */       throw new IndexOutOfBoundsException("Invalid index:" + index + ", size=" + size());
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   static class AVLNode
/* 119:    */   {
/* 120:    */     private AVLNode left;
/* 121:    */     private boolean leftIsPrevious;
/* 122:    */     private AVLNode right;
/* 123:    */     private boolean rightIsNext;
/* 124:    */     private int height;
/* 125:    */     private int relativePosition;
/* 126:    */     private Object value;
/* 127:    */     
/* 128:    */     AVLNode(int x0, Object x1, AVLNode x2, AVLNode x3, TreeList.1 x4)
/* 129:    */     {
/* 130:266 */       this(x0, x1, x2, x3);
/* 131:    */     }
/* 132:    */     
/* 133:    */     private AVLNode(int relativePosition, Object obj, AVLNode rightFollower, AVLNode leftFollower)
/* 134:    */     {
/* 135:291 */       this.relativePosition = relativePosition;
/* 136:292 */       this.value = obj;
/* 137:293 */       this.rightIsNext = true;
/* 138:294 */       this.leftIsPrevious = true;
/* 139:295 */       this.right = rightFollower;
/* 140:296 */       this.left = leftFollower;
/* 141:    */     }
/* 142:    */     
/* 143:    */     Object getValue()
/* 144:    */     {
/* 145:305 */       return this.value;
/* 146:    */     }
/* 147:    */     
/* 148:    */     void setValue(Object obj)
/* 149:    */     {
/* 150:314 */       this.value = obj;
/* 151:    */     }
/* 152:    */     
/* 153:    */     AVLNode get(int index)
/* 154:    */     {
/* 155:322 */       int indexRelativeToMe = index - this.relativePosition;
/* 156:324 */       if (indexRelativeToMe == 0) {
/* 157:325 */         return this;
/* 158:    */       }
/* 159:328 */       AVLNode nextNode = indexRelativeToMe < 0 ? getLeftSubTree() : getRightSubTree();
/* 160:329 */       if (nextNode == null) {
/* 161:330 */         return null;
/* 162:    */       }
/* 163:332 */       return nextNode.get(indexRelativeToMe);
/* 164:    */     }
/* 165:    */     
/* 166:    */     int indexOf(Object object, int index)
/* 167:    */     {
/* 168:339 */       if (getLeftSubTree() != null)
/* 169:    */       {
/* 170:340 */         int result = this.left.indexOf(object, index + this.left.relativePosition);
/* 171:341 */         if (result != -1) {
/* 172:342 */           return result;
/* 173:    */         }
/* 174:    */       }
/* 175:345 */       if (this.value == null ? this.value == object : this.value.equals(object)) {
/* 176:346 */         return index;
/* 177:    */       }
/* 178:348 */       if (getRightSubTree() != null) {
/* 179:349 */         return this.right.indexOf(object, index + this.right.relativePosition);
/* 180:    */       }
/* 181:351 */       return -1;
/* 182:    */     }
/* 183:    */     
/* 184:    */     void toArray(Object[] array, int index)
/* 185:    */     {
/* 186:361 */       array[index] = this.value;
/* 187:362 */       if (getLeftSubTree() != null) {
/* 188:363 */         this.left.toArray(array, index + this.left.relativePosition);
/* 189:    */       }
/* 190:365 */       if (getRightSubTree() != null) {
/* 191:366 */         this.right.toArray(array, index + this.right.relativePosition);
/* 192:    */       }
/* 193:    */     }
/* 194:    */     
/* 195:    */     AVLNode next()
/* 196:    */     {
/* 197:376 */       if ((this.rightIsNext) || (this.right == null)) {
/* 198:377 */         return this.right;
/* 199:    */       }
/* 200:379 */       return this.right.min();
/* 201:    */     }
/* 202:    */     
/* 203:    */     AVLNode previous()
/* 204:    */     {
/* 205:388 */       if ((this.leftIsPrevious) || (this.left == null)) {
/* 206:389 */         return this.left;
/* 207:    */       }
/* 208:391 */       return this.left.max();
/* 209:    */     }
/* 210:    */     
/* 211:    */     AVLNode insert(int index, Object obj)
/* 212:    */     {
/* 213:402 */       int indexRelativeToMe = index - this.relativePosition;
/* 214:404 */       if (indexRelativeToMe <= 0) {
/* 215:405 */         return insertOnLeft(indexRelativeToMe, obj);
/* 216:    */       }
/* 217:407 */       return insertOnRight(indexRelativeToMe, obj);
/* 218:    */     }
/* 219:    */     
/* 220:    */     private AVLNode insertOnLeft(int indexRelativeToMe, Object obj)
/* 221:    */     {
/* 222:412 */       AVLNode ret = this;
/* 223:414 */       if (getLeftSubTree() == null) {
/* 224:415 */         setLeft(new AVLNode(-1, obj, this, this.left), null);
/* 225:    */       } else {
/* 226:417 */         setLeft(this.left.insert(indexRelativeToMe, obj), null);
/* 227:    */       }
/* 228:420 */       if (this.relativePosition >= 0) {
/* 229:421 */         this.relativePosition += 1;
/* 230:    */       }
/* 231:423 */       ret = balance();
/* 232:424 */       recalcHeight();
/* 233:425 */       return ret;
/* 234:    */     }
/* 235:    */     
/* 236:    */     private AVLNode insertOnRight(int indexRelativeToMe, Object obj)
/* 237:    */     {
/* 238:429 */       AVLNode ret = this;
/* 239:431 */       if (getRightSubTree() == null) {
/* 240:432 */         setRight(new AVLNode(1, obj, this.right, this), null);
/* 241:    */       } else {
/* 242:434 */         setRight(this.right.insert(indexRelativeToMe, obj), null);
/* 243:    */       }
/* 244:436 */       if (this.relativePosition < 0) {
/* 245:437 */         this.relativePosition -= 1;
/* 246:    */       }
/* 247:439 */       ret = balance();
/* 248:440 */       recalcHeight();
/* 249:441 */       return ret;
/* 250:    */     }
/* 251:    */     
/* 252:    */     private AVLNode getLeftSubTree()
/* 253:    */     {
/* 254:449 */       return this.leftIsPrevious ? null : this.left;
/* 255:    */     }
/* 256:    */     
/* 257:    */     private AVLNode getRightSubTree()
/* 258:    */     {
/* 259:456 */       return this.rightIsNext ? null : this.right;
/* 260:    */     }
/* 261:    */     
/* 262:    */     private AVLNode max()
/* 263:    */     {
/* 264:465 */       return getRightSubTree() == null ? this : this.right.max();
/* 265:    */     }
/* 266:    */     
/* 267:    */     private AVLNode min()
/* 268:    */     {
/* 269:474 */       return getLeftSubTree() == null ? this : this.left.min();
/* 270:    */     }
/* 271:    */     
/* 272:    */     AVLNode remove(int index)
/* 273:    */     {
/* 274:484 */       int indexRelativeToMe = index - this.relativePosition;
/* 275:486 */       if (indexRelativeToMe == 0) {
/* 276:487 */         return removeSelf();
/* 277:    */       }
/* 278:489 */       if (indexRelativeToMe > 0)
/* 279:    */       {
/* 280:490 */         setRight(this.right.remove(indexRelativeToMe), this.right.right);
/* 281:491 */         if (this.relativePosition < 0) {
/* 282:492 */           this.relativePosition += 1;
/* 283:    */         }
/* 284:    */       }
/* 285:    */       else
/* 286:    */       {
/* 287:495 */         setLeft(this.left.remove(indexRelativeToMe), this.left.left);
/* 288:496 */         if (this.relativePosition > 0) {
/* 289:497 */           this.relativePosition -= 1;
/* 290:    */         }
/* 291:    */       }
/* 292:500 */       recalcHeight();
/* 293:501 */       return balance();
/* 294:    */     }
/* 295:    */     
/* 296:    */     private AVLNode removeMax()
/* 297:    */     {
/* 298:505 */       if (getRightSubTree() == null) {
/* 299:506 */         return removeSelf();
/* 300:    */       }
/* 301:508 */       setRight(this.right.removeMax(), this.right.right);
/* 302:509 */       if (this.relativePosition < 0) {
/* 303:510 */         this.relativePosition += 1;
/* 304:    */       }
/* 305:512 */       recalcHeight();
/* 306:513 */       return balance();
/* 307:    */     }
/* 308:    */     
/* 309:    */     private AVLNode removeMin()
/* 310:    */     {
/* 311:517 */       if (getLeftSubTree() == null) {
/* 312:518 */         return removeSelf();
/* 313:    */       }
/* 314:520 */       setLeft(this.left.removeMin(), this.left.left);
/* 315:521 */       if (this.relativePosition > 0) {
/* 316:522 */         this.relativePosition -= 1;
/* 317:    */       }
/* 318:524 */       recalcHeight();
/* 319:525 */       return balance();
/* 320:    */     }
/* 321:    */     
/* 322:    */     private AVLNode removeSelf()
/* 323:    */     {
/* 324:534 */       if ((getRightSubTree() == null) && (getLeftSubTree() == null)) {
/* 325:535 */         return null;
/* 326:    */       }
/* 327:537 */       if (getRightSubTree() == null)
/* 328:    */       {
/* 329:538 */         if (this.relativePosition > 0) {
/* 330:539 */           this.left.relativePosition += this.relativePosition + (this.relativePosition > 0 ? 0 : 1);
/* 331:    */         }
/* 332:541 */         this.left.max().setRight(null, this.right);
/* 333:542 */         return this.left;
/* 334:    */       }
/* 335:544 */       if (getLeftSubTree() == null)
/* 336:    */       {
/* 337:545 */         this.right.relativePosition += this.relativePosition - (this.relativePosition < 0 ? 0 : 1);
/* 338:546 */         this.right.min().setLeft(null, this.left);
/* 339:547 */         return this.right;
/* 340:    */       }
/* 341:550 */       if (heightRightMinusLeft() > 0)
/* 342:    */       {
/* 343:552 */         AVLNode rightMin = this.right.min();
/* 344:553 */         this.value = rightMin.value;
/* 345:554 */         if (this.leftIsPrevious) {
/* 346:555 */           this.left = rightMin.left;
/* 347:    */         }
/* 348:557 */         this.right = this.right.removeMin();
/* 349:558 */         if (this.relativePosition < 0) {
/* 350:559 */           this.relativePosition += 1;
/* 351:    */         }
/* 352:    */       }
/* 353:    */       else
/* 354:    */       {
/* 355:563 */         AVLNode leftMax = this.left.max();
/* 356:564 */         this.value = leftMax.value;
/* 357:565 */         if (this.rightIsNext) {
/* 358:566 */           this.right = leftMax.right;
/* 359:    */         }
/* 360:568 */         AVLNode leftPrevious = this.left.left;
/* 361:569 */         this.left = this.left.removeMax();
/* 362:570 */         if (this.left == null)
/* 363:    */         {
/* 364:573 */           this.left = leftPrevious;
/* 365:574 */           this.leftIsPrevious = true;
/* 366:    */         }
/* 367:576 */         if (this.relativePosition > 0) {
/* 368:577 */           this.relativePosition -= 1;
/* 369:    */         }
/* 370:    */       }
/* 371:580 */       recalcHeight();
/* 372:581 */       return this;
/* 373:    */     }
/* 374:    */     
/* 375:    */     private AVLNode balance()
/* 376:    */     {
/* 377:589 */       switch (heightRightMinusLeft())
/* 378:    */       {
/* 379:    */       case -1: 
/* 380:    */       case 0: 
/* 381:    */       case 1: 
/* 382:593 */         return this;
/* 383:    */       case -2: 
/* 384:595 */         if (this.left.heightRightMinusLeft() > 0) {
/* 385:596 */           setLeft(this.left.rotateLeft(), null);
/* 386:    */         }
/* 387:598 */         return rotateRight();
/* 388:    */       case 2: 
/* 389:600 */         if (this.right.heightRightMinusLeft() < 0) {
/* 390:601 */           setRight(this.right.rotateRight(), null);
/* 391:    */         }
/* 392:603 */         return rotateLeft();
/* 393:    */       }
/* 394:605 */       throw new RuntimeException("tree inconsistent!");
/* 395:    */     }
/* 396:    */     
/* 397:    */     private int getOffset(AVLNode node)
/* 398:    */     {
/* 399:613 */       if (node == null) {
/* 400:614 */         return 0;
/* 401:    */       }
/* 402:616 */       return node.relativePosition;
/* 403:    */     }
/* 404:    */     
/* 405:    */     private int setOffset(AVLNode node, int newOffest)
/* 406:    */     {
/* 407:623 */       if (node == null) {
/* 408:624 */         return 0;
/* 409:    */       }
/* 410:626 */       int oldOffset = getOffset(node);
/* 411:627 */       node.relativePosition = newOffest;
/* 412:628 */       return oldOffset;
/* 413:    */     }
/* 414:    */     
/* 415:    */     private void recalcHeight()
/* 416:    */     {
/* 417:635 */       this.height = (Math.max(getLeftSubTree() == null ? -1 : getLeftSubTree().height, getRightSubTree() == null ? -1 : getRightSubTree().height) + 1);
/* 418:    */     }
/* 419:    */     
/* 420:    */     private int getHeight(AVLNode node)
/* 421:    */     {
/* 422:644 */       return node == null ? -1 : node.height;
/* 423:    */     }
/* 424:    */     
/* 425:    */     private int heightRightMinusLeft()
/* 426:    */     {
/* 427:651 */       return getHeight(getRightSubTree()) - getHeight(getLeftSubTree());
/* 428:    */     }
/* 429:    */     
/* 430:    */     private AVLNode rotateLeft()
/* 431:    */     {
/* 432:655 */       AVLNode newTop = this.right;
/* 433:656 */       AVLNode movedNode = getRightSubTree().getLeftSubTree();
/* 434:    */       
/* 435:658 */       int newTopPosition = this.relativePosition + getOffset(newTop);
/* 436:659 */       int myNewPosition = -newTop.relativePosition;
/* 437:660 */       int movedPosition = getOffset(newTop) + getOffset(movedNode);
/* 438:    */       
/* 439:662 */       setRight(movedNode, newTop);
/* 440:663 */       newTop.setLeft(this, null);
/* 441:    */       
/* 442:665 */       setOffset(newTop, newTopPosition);
/* 443:666 */       setOffset(this, myNewPosition);
/* 444:667 */       setOffset(movedNode, movedPosition);
/* 445:668 */       return newTop;
/* 446:    */     }
/* 447:    */     
/* 448:    */     private AVLNode rotateRight()
/* 449:    */     {
/* 450:672 */       AVLNode newTop = this.left;
/* 451:673 */       AVLNode movedNode = getLeftSubTree().getRightSubTree();
/* 452:    */       
/* 453:675 */       int newTopPosition = this.relativePosition + getOffset(newTop);
/* 454:676 */       int myNewPosition = -newTop.relativePosition;
/* 455:677 */       int movedPosition = getOffset(newTop) + getOffset(movedNode);
/* 456:    */       
/* 457:679 */       setLeft(movedNode, newTop);
/* 458:680 */       newTop.setRight(this, null);
/* 459:    */       
/* 460:682 */       setOffset(newTop, newTopPosition);
/* 461:683 */       setOffset(this, myNewPosition);
/* 462:684 */       setOffset(movedNode, movedPosition);
/* 463:685 */       return newTop;
/* 464:    */     }
/* 465:    */     
/* 466:    */     private void setLeft(AVLNode node, AVLNode previous)
/* 467:    */     {
/* 468:695 */       this.leftIsPrevious = (node == null);
/* 469:696 */       this.left = (this.leftIsPrevious ? previous : node);
/* 470:697 */       recalcHeight();
/* 471:    */     }
/* 472:    */     
/* 473:    */     private void setRight(AVLNode node, AVLNode next)
/* 474:    */     {
/* 475:707 */       this.rightIsNext = (node == null);
/* 476:708 */       this.right = (this.rightIsNext ? next : node);
/* 477:709 */       recalcHeight();
/* 478:    */     }
/* 479:    */     
/* 480:    */     public String toString()
/* 481:    */     {
/* 482:768 */       return "AVLNode(" + this.relativePosition + "," + (this.left != null) + "," + this.value + "," + (getRightSubTree() != null) + ", faedelung " + this.rightIsNext + " )";
/* 483:    */     }
/* 484:    */   }
/* 485:    */   
/* 486:    */   static class TreeListIterator
/* 487:    */     implements ListIterator, OrderedIterator
/* 488:    */   {
/* 489:    */     protected final TreeList parent;
/* 490:    */     protected TreeList.AVLNode next;
/* 491:    */     protected int nextIndex;
/* 492:    */     protected TreeList.AVLNode current;
/* 493:    */     protected int currentIndex;
/* 494:    */     protected int expectedModCount;
/* 495:    */     
/* 496:    */     protected TreeListIterator(TreeList parent, int fromIndex)
/* 497:    */       throws IndexOutOfBoundsException
/* 498:    */     {
/* 499:812 */       this.parent = parent;
/* 500:813 */       this.expectedModCount = parent.modCount;
/* 501:814 */       this.next = (parent.root == null ? null : parent.root.get(fromIndex));
/* 502:815 */       this.nextIndex = fromIndex;
/* 503:816 */       this.currentIndex = -1;
/* 504:    */     }
/* 505:    */     
/* 506:    */     protected void checkModCount()
/* 507:    */     {
/* 508:827 */       if (this.parent.modCount != this.expectedModCount) {
/* 509:828 */         throw new ConcurrentModificationException();
/* 510:    */       }
/* 511:    */     }
/* 512:    */     
/* 513:    */     public boolean hasNext()
/* 514:    */     {
/* 515:833 */       return this.nextIndex < this.parent.size();
/* 516:    */     }
/* 517:    */     
/* 518:    */     public Object next()
/* 519:    */     {
/* 520:837 */       checkModCount();
/* 521:838 */       if (!hasNext()) {
/* 522:839 */         throw new NoSuchElementException("No element at index " + this.nextIndex + ".");
/* 523:    */       }
/* 524:841 */       if (this.next == null) {
/* 525:842 */         this.next = this.parent.root.get(this.nextIndex);
/* 526:    */       }
/* 527:844 */       Object value = this.next.getValue();
/* 528:845 */       this.current = this.next;
/* 529:846 */       this.currentIndex = (this.nextIndex++);
/* 530:847 */       this.next = this.next.next();
/* 531:848 */       return value;
/* 532:    */     }
/* 533:    */     
/* 534:    */     public boolean hasPrevious()
/* 535:    */     {
/* 536:852 */       return this.nextIndex > 0;
/* 537:    */     }
/* 538:    */     
/* 539:    */     public Object previous()
/* 540:    */     {
/* 541:856 */       checkModCount();
/* 542:857 */       if (!hasPrevious()) {
/* 543:858 */         throw new NoSuchElementException("Already at start of list.");
/* 544:    */       }
/* 545:860 */       if (this.next == null) {
/* 546:861 */         this.next = this.parent.root.get(this.nextIndex - 1);
/* 547:    */       } else {
/* 548:863 */         this.next = this.next.previous();
/* 549:    */       }
/* 550:865 */       Object value = this.next.getValue();
/* 551:866 */       this.current = this.next;
/* 552:867 */       this.currentIndex = (--this.nextIndex);
/* 553:868 */       return value;
/* 554:    */     }
/* 555:    */     
/* 556:    */     public int nextIndex()
/* 557:    */     {
/* 558:872 */       return this.nextIndex;
/* 559:    */     }
/* 560:    */     
/* 561:    */     public int previousIndex()
/* 562:    */     {
/* 563:876 */       return nextIndex() - 1;
/* 564:    */     }
/* 565:    */     
/* 566:    */     public void remove()
/* 567:    */     {
/* 568:880 */       checkModCount();
/* 569:881 */       if (this.currentIndex == -1) {
/* 570:882 */         throw new IllegalStateException();
/* 571:    */       }
/* 572:884 */       if (this.nextIndex == this.currentIndex)
/* 573:    */       {
/* 574:886 */         this.next = this.next.next();
/* 575:887 */         this.parent.remove(this.currentIndex);
/* 576:    */       }
/* 577:    */       else
/* 578:    */       {
/* 579:890 */         this.parent.remove(this.currentIndex);
/* 580:891 */         this.nextIndex -= 1;
/* 581:    */       }
/* 582:893 */       this.current = null;
/* 583:894 */       this.currentIndex = -1;
/* 584:895 */       this.expectedModCount += 1;
/* 585:    */     }
/* 586:    */     
/* 587:    */     public void set(Object obj)
/* 588:    */     {
/* 589:899 */       checkModCount();
/* 590:900 */       if (this.current == null) {
/* 591:901 */         throw new IllegalStateException();
/* 592:    */       }
/* 593:903 */       this.current.setValue(obj);
/* 594:    */     }
/* 595:    */     
/* 596:    */     public void add(Object obj)
/* 597:    */     {
/* 598:907 */       checkModCount();
/* 599:908 */       this.parent.add(this.nextIndex, obj);
/* 600:909 */       this.current = null;
/* 601:910 */       this.currentIndex = -1;
/* 602:911 */       this.nextIndex += 1;
/* 603:912 */       this.expectedModCount += 1;
/* 604:    */     }
/* 605:    */   }
/* 606:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.TreeList
 * JD-Core Version:    0.7.0.1
 */