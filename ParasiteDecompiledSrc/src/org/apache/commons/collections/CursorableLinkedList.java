/*    1:     */ package org.apache.commons.collections;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.ObjectOutputStream;
/*    6:     */ import java.io.Serializable;
/*    7:     */ import java.lang.ref.WeakReference;
/*    8:     */ import java.lang.reflect.Array;
/*    9:     */ import java.util.ArrayList;
/*   10:     */ import java.util.Collection;
/*   11:     */ import java.util.ConcurrentModificationException;
/*   12:     */ import java.util.Iterator;
/*   13:     */ import java.util.List;
/*   14:     */ import java.util.ListIterator;
/*   15:     */ import java.util.NoSuchElementException;
/*   16:     */ 
/*   17:     */ /**
/*   18:     */  * @deprecated
/*   19:     */  */
/*   20:     */ public class CursorableLinkedList
/*   21:     */   implements List, Serializable
/*   22:     */ {
/*   23:     */   private static final long serialVersionUID = 8836393098519411393L;
/*   24:     */   protected transient int _size;
/*   25:     */   protected transient Listable _head;
/*   26:     */   protected transient int _modCount;
/*   27:     */   protected transient List _cursors;
/*   28:     */   
/*   29:     */   public boolean add(Object o)
/*   30:     */   {
/*   31:  68 */     insertListable(this._head.prev(), null, o);
/*   32:  69 */     return true;
/*   33:     */   }
/*   34:     */   
/*   35:     */   public void add(int index, Object element)
/*   36:     */   {
/*   37:  88 */     if (index == this._size)
/*   38:     */     {
/*   39:  89 */       add(element);
/*   40:     */     }
/*   41:     */     else
/*   42:     */     {
/*   43:  91 */       if ((index < 0) || (index > this._size)) {
/*   44:  92 */         throw new IndexOutOfBoundsException(String.valueOf(index) + " < 0 or " + String.valueOf(index) + " > " + this._size);
/*   45:     */       }
/*   46:  94 */       Listable succ = isEmpty() ? null : getListableAt(index);
/*   47:  95 */       Listable pred = null == succ ? null : succ.prev();
/*   48:  96 */       insertListable(pred, succ, element);
/*   49:     */     }
/*   50:     */   }
/*   51:     */   
/*   52:     */   public boolean addAll(Collection c)
/*   53:     */   {
/*   54: 118 */     if (c.isEmpty()) {
/*   55: 119 */       return false;
/*   56:     */     }
/*   57: 121 */     Iterator it = c.iterator();
/*   58: 122 */     while (it.hasNext()) {
/*   59: 123 */       insertListable(this._head.prev(), null, it.next());
/*   60:     */     }
/*   61: 125 */     return true;
/*   62:     */   }
/*   63:     */   
/*   64:     */   public boolean addAll(int index, Collection c)
/*   65:     */   {
/*   66: 154 */     if (c.isEmpty()) {
/*   67: 155 */       return false;
/*   68:     */     }
/*   69: 156 */     if ((this._size == index) || (this._size == 0)) {
/*   70: 157 */       return addAll(c);
/*   71:     */     }
/*   72: 159 */     Listable succ = getListableAt(index);
/*   73: 160 */     Listable pred = null == succ ? null : succ.prev();
/*   74: 161 */     Iterator it = c.iterator();
/*   75: 162 */     while (it.hasNext()) {
/*   76: 163 */       pred = insertListable(pred, succ, it.next());
/*   77:     */     }
/*   78: 165 */     return true;
/*   79:     */   }
/*   80:     */   
/*   81:     */   public boolean addFirst(Object o)
/*   82:     */   {
/*   83: 177 */     insertListable(null, this._head.next(), o);
/*   84: 178 */     return true;
/*   85:     */   }
/*   86:     */   
/*   87:     */   public boolean addLast(Object o)
/*   88:     */   {
/*   89: 189 */     insertListable(this._head.prev(), null, o);
/*   90: 190 */     return true;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public void clear()
/*   94:     */   {
/*   95: 207 */     Iterator it = iterator();
/*   96: 208 */     while (it.hasNext())
/*   97:     */     {
/*   98: 209 */       it.next();
/*   99: 210 */       it.remove();
/*  100:     */     }
/*  101:     */   }
/*  102:     */   
/*  103:     */   public boolean contains(Object o)
/*  104:     */   {
/*  105: 224 */     Listable elt = this._head.next();
/*  106: 224 */     for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next()) {
/*  107: 225 */       if (((null == o) && (null == elt.value())) || ((o != null) && (o.equals(elt.value())))) {
/*  108: 227 */         return true;
/*  109:     */       }
/*  110:     */     }
/*  111: 230 */     return false;
/*  112:     */   }
/*  113:     */   
/*  114:     */   public boolean containsAll(Collection c)
/*  115:     */   {
/*  116: 242 */     Iterator it = c.iterator();
/*  117: 243 */     while (it.hasNext()) {
/*  118: 244 */       if (!contains(it.next())) {
/*  119: 245 */         return false;
/*  120:     */       }
/*  121:     */     }
/*  122: 248 */     return true;
/*  123:     */   }
/*  124:     */   
/*  125:     */   public Cursor cursor()
/*  126:     */   {
/*  127: 277 */     return new Cursor(0);
/*  128:     */   }
/*  129:     */   
/*  130:     */   public Cursor cursor(int i)
/*  131:     */   {
/*  132: 297 */     return new Cursor(i);
/*  133:     */   }
/*  134:     */   
/*  135:     */   public boolean equals(Object o)
/*  136:     */   {
/*  137: 315 */     if (o == this) {
/*  138: 316 */       return true;
/*  139:     */     }
/*  140: 317 */     if (!(o instanceof List)) {
/*  141: 318 */       return false;
/*  142:     */     }
/*  143: 320 */     Iterator it = ((List)o).listIterator();
/*  144: 321 */     Listable elt = this._head.next();
/*  145: 321 */     for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next()) {
/*  146: 322 */       if ((!it.hasNext()) || (null == elt.value() ? null != it.next() : !elt.value().equals(it.next()))) {
/*  147: 323 */         return false;
/*  148:     */       }
/*  149:     */     }
/*  150: 326 */     return !it.hasNext();
/*  151:     */   }
/*  152:     */   
/*  153:     */   public Object get(int index)
/*  154:     */   {
/*  155: 339 */     return getListableAt(index).value();
/*  156:     */   }
/*  157:     */   
/*  158:     */   public Object getFirst()
/*  159:     */   {
/*  160:     */     try
/*  161:     */     {
/*  162: 347 */       return this._head.next().value();
/*  163:     */     }
/*  164:     */     catch (NullPointerException e)
/*  165:     */     {
/*  166: 349 */       throw new NoSuchElementException();
/*  167:     */     }
/*  168:     */   }
/*  169:     */   
/*  170:     */   public Object getLast()
/*  171:     */   {
/*  172:     */     try
/*  173:     */     {
/*  174: 358 */       return this._head.prev().value();
/*  175:     */     }
/*  176:     */     catch (NullPointerException e)
/*  177:     */     {
/*  178: 360 */       throw new NoSuchElementException();
/*  179:     */     }
/*  180:     */   }
/*  181:     */   
/*  182:     */   public int hashCode()
/*  183:     */   {
/*  184: 386 */     int hash = 1;
/*  185: 387 */     Listable elt = this._head.next();
/*  186: 387 */     for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next()) {
/*  187: 388 */       hash = 31 * hash + (null == elt.value() ? 0 : elt.value().hashCode());
/*  188:     */     }
/*  189: 390 */     return hash;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public int indexOf(Object o)
/*  193:     */   {
/*  194: 405 */     int ndx = 0;
/*  195: 409 */     if (null == o)
/*  196:     */     {
/*  197: 410 */       Listable elt = this._head.next();
/*  198: 410 */       for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next())
/*  199:     */       {
/*  200: 411 */         if (null == elt.value()) {
/*  201: 412 */           return ndx;
/*  202:     */         }
/*  203: 414 */         ndx++;
/*  204:     */       }
/*  205:     */     }
/*  206:     */     else
/*  207:     */     {
/*  208: 418 */       Listable elt = this._head.next();
/*  209: 418 */       for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next())
/*  210:     */       {
/*  211: 419 */         if (o.equals(elt.value())) {
/*  212: 420 */           return ndx;
/*  213:     */         }
/*  214: 422 */         ndx++;
/*  215:     */       }
/*  216:     */     }
/*  217: 425 */     return -1;
/*  218:     */   }
/*  219:     */   
/*  220:     */   public boolean isEmpty()
/*  221:     */   {
/*  222: 433 */     return 0 == this._size;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public Iterator iterator()
/*  226:     */   {
/*  227: 441 */     return listIterator(0);
/*  228:     */   }
/*  229:     */   
/*  230:     */   public int lastIndexOf(Object o)
/*  231:     */   {
/*  232: 456 */     int ndx = this._size - 1;
/*  233: 460 */     if (null == o)
/*  234:     */     {
/*  235: 461 */       Listable elt = this._head.prev();
/*  236: 461 */       for (Listable past = null; (null != elt) && (past != this._head.next()); elt = (past = elt).prev())
/*  237:     */       {
/*  238: 462 */         if (null == elt.value()) {
/*  239: 463 */           return ndx;
/*  240:     */         }
/*  241: 465 */         ndx--;
/*  242:     */       }
/*  243:     */     }
/*  244:     */     else
/*  245:     */     {
/*  246: 468 */       Listable elt = this._head.prev();
/*  247: 468 */       for (Listable past = null; (null != elt) && (past != this._head.next()); elt = (past = elt).prev())
/*  248:     */       {
/*  249: 469 */         if (o.equals(elt.value())) {
/*  250: 470 */           return ndx;
/*  251:     */         }
/*  252: 472 */         ndx--;
/*  253:     */       }
/*  254:     */     }
/*  255: 475 */     return -1;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public ListIterator listIterator()
/*  259:     */   {
/*  260: 483 */     return listIterator(0);
/*  261:     */   }
/*  262:     */   
/*  263:     */   public ListIterator listIterator(int index)
/*  264:     */   {
/*  265: 491 */     if ((index < 0) || (index > this._size)) {
/*  266: 492 */       throw new IndexOutOfBoundsException(index + " < 0 or > " + this._size);
/*  267:     */     }
/*  268: 494 */     return new ListIter(index);
/*  269:     */   }
/*  270:     */   
/*  271:     */   public boolean remove(Object o)
/*  272:     */   {
/*  273: 508 */     Listable elt = this._head.next();
/*  274: 508 */     for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next())
/*  275:     */     {
/*  276: 509 */       if ((null == o) && (null == elt.value()))
/*  277:     */       {
/*  278: 510 */         removeListable(elt);
/*  279: 511 */         return true;
/*  280:     */       }
/*  281: 512 */       if ((o != null) && (o.equals(elt.value())))
/*  282:     */       {
/*  283: 513 */         removeListable(elt);
/*  284: 514 */         return true;
/*  285:     */       }
/*  286:     */     }
/*  287: 517 */     return false;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public Object remove(int index)
/*  291:     */   {
/*  292: 533 */     Listable elt = getListableAt(index);
/*  293: 534 */     Object ret = elt.value();
/*  294: 535 */     removeListable(elt);
/*  295: 536 */     return ret;
/*  296:     */   }
/*  297:     */   
/*  298:     */   public boolean removeAll(Collection c)
/*  299:     */   {
/*  300: 548 */     if ((0 == c.size()) || (0 == this._size)) {
/*  301: 549 */       return false;
/*  302:     */     }
/*  303: 551 */     boolean changed = false;
/*  304: 552 */     Iterator it = iterator();
/*  305: 553 */     while (it.hasNext()) {
/*  306: 554 */       if (c.contains(it.next()))
/*  307:     */       {
/*  308: 555 */         it.remove();
/*  309: 556 */         changed = true;
/*  310:     */       }
/*  311:     */     }
/*  312: 559 */     return changed;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public Object removeFirst()
/*  316:     */   {
/*  317: 567 */     if (this._head.next() != null)
/*  318:     */     {
/*  319: 568 */       Object val = this._head.next().value();
/*  320: 569 */       removeListable(this._head.next());
/*  321: 570 */       return val;
/*  322:     */     }
/*  323: 572 */     throw new NoSuchElementException();
/*  324:     */   }
/*  325:     */   
/*  326:     */   public Object removeLast()
/*  327:     */   {
/*  328: 580 */     if (this._head.prev() != null)
/*  329:     */     {
/*  330: 581 */       Object val = this._head.prev().value();
/*  331: 582 */       removeListable(this._head.prev());
/*  332: 583 */       return val;
/*  333:     */     }
/*  334: 585 */     throw new NoSuchElementException();
/*  335:     */   }
/*  336:     */   
/*  337:     */   public boolean retainAll(Collection c)
/*  338:     */   {
/*  339: 600 */     boolean changed = false;
/*  340: 601 */     Iterator it = iterator();
/*  341: 602 */     while (it.hasNext()) {
/*  342: 603 */       if (!c.contains(it.next()))
/*  343:     */       {
/*  344: 604 */         it.remove();
/*  345: 605 */         changed = true;
/*  346:     */       }
/*  347:     */     }
/*  348: 608 */     return changed;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public Object set(int index, Object element)
/*  352:     */   {
/*  353: 627 */     Listable elt = getListableAt(index);
/*  354: 628 */     Object val = elt.setValue(element);
/*  355: 629 */     broadcastListableChanged(elt);
/*  356: 630 */     return val;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public int size()
/*  360:     */   {
/*  361: 638 */     return this._size;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public Object[] toArray()
/*  365:     */   {
/*  366: 649 */     Object[] array = new Object[this._size];
/*  367: 650 */     int i = 0;
/*  368: 651 */     Listable elt = this._head.next();
/*  369: 651 */     for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next()) {
/*  370: 652 */       array[(i++)] = elt.value();
/*  371:     */     }
/*  372: 654 */     return array;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public Object[] toArray(Object[] a)
/*  376:     */   {
/*  377: 673 */     if (a.length < this._size) {
/*  378: 674 */       a = (Object[])Array.newInstance(a.getClass().getComponentType(), this._size);
/*  379:     */     }
/*  380: 676 */     int i = 0;
/*  381: 677 */     Listable elt = this._head.next();
/*  382: 677 */     for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next()) {
/*  383: 678 */       a[(i++)] = elt.value();
/*  384:     */     }
/*  385: 680 */     if (a.length > this._size) {
/*  386: 681 */       a[this._size] = null;
/*  387:     */     }
/*  388: 683 */     return a;
/*  389:     */   }
/*  390:     */   
/*  391:     */   public String toString()
/*  392:     */   {
/*  393: 691 */     StringBuffer buf = new StringBuffer();
/*  394: 692 */     buf.append("[");
/*  395: 693 */     Listable elt = this._head.next();
/*  396: 693 */     for (Listable past = null; (null != elt) && (past != this._head.prev()); elt = (past = elt).next())
/*  397:     */     {
/*  398: 694 */       if (this._head.next() != elt) {
/*  399: 695 */         buf.append(", ");
/*  400:     */       }
/*  401: 697 */       buf.append(elt.value());
/*  402:     */     }
/*  403: 699 */     buf.append("]");
/*  404: 700 */     return buf.toString();
/*  405:     */   }
/*  406:     */   
/*  407:     */   public List subList(int i, int j)
/*  408:     */   {
/*  409: 708 */     if ((i < 0) || (j > this._size) || (i > j)) {
/*  410: 709 */       throw new IndexOutOfBoundsException();
/*  411:     */     }
/*  412: 710 */     if ((i == 0) && (j == this._size)) {
/*  413: 711 */       return this;
/*  414:     */     }
/*  415: 713 */     return new CursorableSubList(this, i, j);
/*  416:     */   }
/*  417:     */   
/*  418:     */   protected Listable insertListable(Listable before, Listable after, Object value)
/*  419:     */   {
/*  420: 728 */     this._modCount += 1;
/*  421: 729 */     this._size += 1;
/*  422: 730 */     Listable elt = new Listable(before, after, value);
/*  423: 731 */     if (null != before) {
/*  424: 732 */       before.setNext(elt);
/*  425:     */     } else {
/*  426: 734 */       this._head.setNext(elt);
/*  427:     */     }
/*  428: 737 */     if (null != after) {
/*  429: 738 */       after.setPrev(elt);
/*  430:     */     } else {
/*  431: 740 */       this._head.setPrev(elt);
/*  432:     */     }
/*  433: 742 */     broadcastListableInserted(elt);
/*  434: 743 */     return elt;
/*  435:     */   }
/*  436:     */   
/*  437:     */   protected void removeListable(Listable elt)
/*  438:     */   {
/*  439: 752 */     this._modCount += 1;
/*  440: 753 */     this._size -= 1;
/*  441: 754 */     if (this._head.next() == elt) {
/*  442: 755 */       this._head.setNext(elt.next());
/*  443:     */     }
/*  444: 757 */     if (null != elt.next()) {
/*  445: 758 */       elt.next().setPrev(elt.prev());
/*  446:     */     }
/*  447: 760 */     if (this._head.prev() == elt) {
/*  448: 761 */       this._head.setPrev(elt.prev());
/*  449:     */     }
/*  450: 763 */     if (null != elt.prev()) {
/*  451: 764 */       elt.prev().setNext(elt.next());
/*  452:     */     }
/*  453: 766 */     broadcastListableRemoved(elt);
/*  454:     */   }
/*  455:     */   
/*  456:     */   protected Listable getListableAt(int index)
/*  457:     */   {
/*  458: 778 */     if ((index < 0) || (index >= this._size)) {
/*  459: 779 */       throw new IndexOutOfBoundsException(String.valueOf(index) + " < 0 or " + String.valueOf(index) + " >= " + this._size);
/*  460:     */     }
/*  461: 781 */     if (index <= this._size / 2)
/*  462:     */     {
/*  463: 782 */       Listable elt = this._head.next();
/*  464: 783 */       for (int i = 0; i < index; i++) {
/*  465: 784 */         elt = elt.next();
/*  466:     */       }
/*  467: 786 */       return elt;
/*  468:     */     }
/*  469: 788 */     Listable elt = this._head.prev();
/*  470: 789 */     for (int i = this._size - 1; i > index; i--) {
/*  471: 790 */       elt = elt.prev();
/*  472:     */     }
/*  473: 792 */     return elt;
/*  474:     */   }
/*  475:     */   
/*  476:     */   protected void registerCursor(Cursor cur)
/*  477:     */   {
/*  478: 803 */     for (Iterator it = this._cursors.iterator(); it.hasNext();)
/*  479:     */     {
/*  480: 804 */       WeakReference ref = (WeakReference)it.next();
/*  481: 805 */       if (ref.get() == null) {
/*  482: 806 */         it.remove();
/*  483:     */       }
/*  484:     */     }
/*  485: 810 */     this._cursors.add(new WeakReference(cur));
/*  486:     */   }
/*  487:     */   
/*  488:     */   protected void unregisterCursor(Cursor cur)
/*  489:     */   {
/*  490: 818 */     for (Iterator it = this._cursors.iterator(); it.hasNext();)
/*  491:     */     {
/*  492: 819 */       WeakReference ref = (WeakReference)it.next();
/*  493: 820 */       Cursor cursor = (Cursor)ref.get();
/*  494: 821 */       if (cursor == null)
/*  495:     */       {
/*  496: 825 */         it.remove();
/*  497:     */       }
/*  498: 827 */       else if (cursor == cur)
/*  499:     */       {
/*  500: 828 */         ref.clear();
/*  501: 829 */         it.remove();
/*  502: 830 */         break;
/*  503:     */       }
/*  504:     */     }
/*  505:     */   }
/*  506:     */   
/*  507:     */   protected void invalidateCursors()
/*  508:     */   {
/*  509: 840 */     Iterator it = this._cursors.iterator();
/*  510: 841 */     while (it.hasNext())
/*  511:     */     {
/*  512: 842 */       WeakReference ref = (WeakReference)it.next();
/*  513: 843 */       Cursor cursor = (Cursor)ref.get();
/*  514: 844 */       if (cursor != null)
/*  515:     */       {
/*  516: 846 */         cursor.invalidate();
/*  517: 847 */         ref.clear();
/*  518:     */       }
/*  519: 849 */       it.remove();
/*  520:     */     }
/*  521:     */   }
/*  522:     */   
/*  523:     */   protected void broadcastListableChanged(Listable elt)
/*  524:     */   {
/*  525: 859 */     Iterator it = this._cursors.iterator();
/*  526: 860 */     while (it.hasNext())
/*  527:     */     {
/*  528: 861 */       WeakReference ref = (WeakReference)it.next();
/*  529: 862 */       Cursor cursor = (Cursor)ref.get();
/*  530: 863 */       if (cursor == null) {
/*  531: 864 */         it.remove();
/*  532:     */       } else {
/*  533: 866 */         cursor.listableChanged(elt);
/*  534:     */       }
/*  535:     */     }
/*  536:     */   }
/*  537:     */   
/*  538:     */   protected void broadcastListableRemoved(Listable elt)
/*  539:     */   {
/*  540: 876 */     Iterator it = this._cursors.iterator();
/*  541: 877 */     while (it.hasNext())
/*  542:     */     {
/*  543: 878 */       WeakReference ref = (WeakReference)it.next();
/*  544: 879 */       Cursor cursor = (Cursor)ref.get();
/*  545: 880 */       if (cursor == null) {
/*  546: 881 */         it.remove();
/*  547:     */       } else {
/*  548: 883 */         cursor.listableRemoved(elt);
/*  549:     */       }
/*  550:     */     }
/*  551:     */   }
/*  552:     */   
/*  553:     */   protected void broadcastListableInserted(Listable elt)
/*  554:     */   {
/*  555: 893 */     Iterator it = this._cursors.iterator();
/*  556: 894 */     while (it.hasNext())
/*  557:     */     {
/*  558: 895 */       WeakReference ref = (WeakReference)it.next();
/*  559: 896 */       Cursor cursor = (Cursor)ref.get();
/*  560: 897 */       if (cursor == null) {
/*  561: 898 */         it.remove();
/*  562:     */       } else {
/*  563: 900 */         cursor.listableInserted(elt);
/*  564:     */       }
/*  565:     */     }
/*  566:     */   }
/*  567:     */   
/*  568:     */   private void writeObject(ObjectOutputStream out)
/*  569:     */     throws IOException
/*  570:     */   {
/*  571: 906 */     out.defaultWriteObject();
/*  572: 907 */     out.writeInt(this._size);
/*  573: 908 */     Listable cur = this._head.next();
/*  574: 909 */     while (cur != null)
/*  575:     */     {
/*  576: 910 */       out.writeObject(cur.value());
/*  577: 911 */       cur = cur.next();
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   private void readObject(ObjectInputStream in)
/*  582:     */     throws IOException, ClassNotFoundException
/*  583:     */   {
/*  584: 916 */     in.defaultReadObject();
/*  585: 917 */     this._size = 0;
/*  586: 918 */     this._modCount = 0;
/*  587: 919 */     this._cursors = new ArrayList();
/*  588: 920 */     this._head = new Listable(null, null, null);
/*  589: 921 */     int size = in.readInt();
/*  590: 922 */     for (int i = 0; i < size; i++) {
/*  591: 923 */       add(in.readObject());
/*  592:     */     }
/*  593:     */   }
/*  594:     */   
/*  595:     */   public CursorableLinkedList()
/*  596:     */   {
/*  597: 930 */     this._size = 0;
/*  598:     */     
/*  599:     */ 
/*  600:     */ 
/*  601:     */ 
/*  602:     */ 
/*  603:     */ 
/*  604:     */ 
/*  605:     */ 
/*  606:     */ 
/*  607:     */ 
/*  608:     */ 
/*  609:     */ 
/*  610:     */ 
/*  611: 944 */     this._head = new Listable(null, null, null);
/*  612:     */     
/*  613:     */ 
/*  614: 947 */     this._modCount = 0;
/*  615:     */     
/*  616:     */ 
/*  617:     */ 
/*  618:     */ 
/*  619:     */ 
/*  620: 953 */     this._cursors = new ArrayList();
/*  621:     */   }
/*  622:     */   
/*  623:     */   static class Listable
/*  624:     */     implements Serializable
/*  625:     */   {
/*  626: 958 */     private Listable _prev = null;
/*  627: 959 */     private Listable _next = null;
/*  628: 960 */     private Object _val = null;
/*  629:     */     
/*  630:     */     Listable(Listable prev, Listable next, Object val)
/*  631:     */     {
/*  632: 963 */       this._prev = prev;
/*  633: 964 */       this._next = next;
/*  634: 965 */       this._val = val;
/*  635:     */     }
/*  636:     */     
/*  637:     */     Listable next()
/*  638:     */     {
/*  639: 969 */       return this._next;
/*  640:     */     }
/*  641:     */     
/*  642:     */     Listable prev()
/*  643:     */     {
/*  644: 973 */       return this._prev;
/*  645:     */     }
/*  646:     */     
/*  647:     */     Object value()
/*  648:     */     {
/*  649: 977 */       return this._val;
/*  650:     */     }
/*  651:     */     
/*  652:     */     void setNext(Listable next)
/*  653:     */     {
/*  654: 981 */       this._next = next;
/*  655:     */     }
/*  656:     */     
/*  657:     */     void setPrev(Listable prev)
/*  658:     */     {
/*  659: 985 */       this._prev = prev;
/*  660:     */     }
/*  661:     */     
/*  662:     */     Object setValue(Object val)
/*  663:     */     {
/*  664: 989 */       Object temp = this._val;
/*  665: 990 */       this._val = val;
/*  666: 991 */       return temp;
/*  667:     */     }
/*  668:     */   }
/*  669:     */   
/*  670:     */   class ListIter
/*  671:     */     implements ListIterator
/*  672:     */   {
/*  673: 996 */     CursorableLinkedList.Listable _cur = null;
/*  674: 997 */     CursorableLinkedList.Listable _lastReturned = null;
/*  675: 998 */     int _expectedModCount = CursorableLinkedList.this._modCount;
/*  676: 999 */     int _nextIndex = 0;
/*  677:     */     
/*  678:     */     ListIter(int index)
/*  679:     */     {
/*  680:     */       CursorableLinkedList.Listable temp;
/*  681:1002 */       if (index == 0)
/*  682:     */       {
/*  683:1003 */         this._cur = new CursorableLinkedList.Listable(null, CursorableLinkedList.this._head.next(), null);
/*  684:1004 */         this._nextIndex = 0;
/*  685:     */       }
/*  686:1005 */       else if (index == CursorableLinkedList.this._size)
/*  687:     */       {
/*  688:1006 */         this._cur = new CursorableLinkedList.Listable(CursorableLinkedList.this._head.prev(), null, null);
/*  689:1007 */         this._nextIndex = CursorableLinkedList.this._size;
/*  690:     */       }
/*  691:     */       else
/*  692:     */       {
/*  693:1009 */         temp = CursorableLinkedList.this.getListableAt(index);
/*  694:1010 */         this._cur = new CursorableLinkedList.Listable(temp.prev(), temp, null);
/*  695:1011 */         this._nextIndex = index;
/*  696:     */       }
/*  697:     */     }
/*  698:     */     
/*  699:     */     public Object previous()
/*  700:     */     {
/*  701:1016 */       checkForComod();
/*  702:1017 */       if (!hasPrevious()) {
/*  703:1018 */         throw new NoSuchElementException();
/*  704:     */       }
/*  705:1020 */       Object ret = this._cur.prev().value();
/*  706:1021 */       this._lastReturned = this._cur.prev();
/*  707:1022 */       this._cur.setNext(this._cur.prev());
/*  708:1023 */       this._cur.setPrev(this._cur.prev().prev());
/*  709:1024 */       this._nextIndex -= 1;
/*  710:1025 */       return ret;
/*  711:     */     }
/*  712:     */     
/*  713:     */     public boolean hasNext()
/*  714:     */     {
/*  715:1030 */       checkForComod();
/*  716:1031 */       return (null != this._cur.next()) && (this._cur.prev() != CursorableLinkedList.this._head.prev());
/*  717:     */     }
/*  718:     */     
/*  719:     */     public Object next()
/*  720:     */     {
/*  721:1035 */       checkForComod();
/*  722:1036 */       if (!hasNext()) {
/*  723:1037 */         throw new NoSuchElementException();
/*  724:     */       }
/*  725:1039 */       Object ret = this._cur.next().value();
/*  726:1040 */       this._lastReturned = this._cur.next();
/*  727:1041 */       this._cur.setPrev(this._cur.next());
/*  728:1042 */       this._cur.setNext(this._cur.next().next());
/*  729:1043 */       this._nextIndex += 1;
/*  730:1044 */       return ret;
/*  731:     */     }
/*  732:     */     
/*  733:     */     public int previousIndex()
/*  734:     */     {
/*  735:1049 */       checkForComod();
/*  736:1050 */       if (!hasPrevious()) {
/*  737:1051 */         return -1;
/*  738:     */       }
/*  739:1053 */       return this._nextIndex - 1;
/*  740:     */     }
/*  741:     */     
/*  742:     */     public boolean hasPrevious()
/*  743:     */     {
/*  744:1057 */       checkForComod();
/*  745:1058 */       return (null != this._cur.prev()) && (this._cur.next() != CursorableLinkedList.this._head.next());
/*  746:     */     }
/*  747:     */     
/*  748:     */     public void set(Object o)
/*  749:     */     {
/*  750:1062 */       checkForComod();
/*  751:     */       try
/*  752:     */       {
/*  753:1064 */         this._lastReturned.setValue(o);
/*  754:     */       }
/*  755:     */       catch (NullPointerException e)
/*  756:     */       {
/*  757:1066 */         throw new IllegalStateException();
/*  758:     */       }
/*  759:     */     }
/*  760:     */     
/*  761:     */     public int nextIndex()
/*  762:     */     {
/*  763:1071 */       checkForComod();
/*  764:1072 */       if (!hasNext()) {
/*  765:1073 */         return CursorableLinkedList.this.size();
/*  766:     */       }
/*  767:1075 */       return this._nextIndex;
/*  768:     */     }
/*  769:     */     
/*  770:     */     public void remove()
/*  771:     */     {
/*  772:1079 */       checkForComod();
/*  773:1080 */       if (null == this._lastReturned) {
/*  774:1081 */         throw new IllegalStateException();
/*  775:     */       }
/*  776:1083 */       this._cur.setNext(this._lastReturned == CursorableLinkedList.this._head.prev() ? null : this._lastReturned.next());
/*  777:1084 */       this._cur.setPrev(this._lastReturned == CursorableLinkedList.this._head.next() ? null : this._lastReturned.prev());
/*  778:1085 */       CursorableLinkedList.this.removeListable(this._lastReturned);
/*  779:1086 */       this._lastReturned = null;
/*  780:1087 */       this._nextIndex -= 1;
/*  781:1088 */       this._expectedModCount += 1;
/*  782:     */     }
/*  783:     */     
/*  784:     */     public void add(Object o)
/*  785:     */     {
/*  786:1093 */       checkForComod();
/*  787:1094 */       this._cur.setPrev(CursorableLinkedList.this.insertListable(this._cur.prev(), this._cur.next(), o));
/*  788:1095 */       this._lastReturned = null;
/*  789:1096 */       this._nextIndex += 1;
/*  790:1097 */       this._expectedModCount += 1;
/*  791:     */     }
/*  792:     */     
/*  793:     */     protected void checkForComod()
/*  794:     */     {
/*  795:1101 */       if (this._expectedModCount != CursorableLinkedList.this._modCount) {
/*  796:1102 */         throw new ConcurrentModificationException();
/*  797:     */       }
/*  798:     */     }
/*  799:     */   }
/*  800:     */   
/*  801:     */   public class Cursor
/*  802:     */     extends CursorableLinkedList.ListIter
/*  803:     */     implements ListIterator
/*  804:     */   {
/*  805:1108 */     boolean _valid = false;
/*  806:     */     
/*  807:     */     Cursor(int index)
/*  808:     */     {
/*  809:1111 */       super(index);
/*  810:1112 */       this._valid = true;
/*  811:1113 */       CursorableLinkedList.this.registerCursor(this);
/*  812:     */     }
/*  813:     */     
/*  814:     */     public int previousIndex()
/*  815:     */     {
/*  816:1117 */       throw new UnsupportedOperationException();
/*  817:     */     }
/*  818:     */     
/*  819:     */     public int nextIndex()
/*  820:     */     {
/*  821:1121 */       throw new UnsupportedOperationException();
/*  822:     */     }
/*  823:     */     
/*  824:     */     public void add(Object o)
/*  825:     */     {
/*  826:1125 */       checkForComod();
/*  827:1126 */       CursorableLinkedList.Listable elt = CursorableLinkedList.this.insertListable(this._cur.prev(), this._cur.next(), o);
/*  828:1127 */       this._cur.setPrev(elt);
/*  829:1128 */       this._cur.setNext(elt.next());
/*  830:1129 */       this._lastReturned = null;
/*  831:1130 */       this._nextIndex += 1;
/*  832:1131 */       this._expectedModCount += 1;
/*  833:     */     }
/*  834:     */     
/*  835:     */     protected void listableRemoved(CursorableLinkedList.Listable elt)
/*  836:     */     {
/*  837:1135 */       if (null == CursorableLinkedList.this._head.prev()) {
/*  838:1136 */         this._cur.setNext(null);
/*  839:1137 */       } else if (this._cur.next() == elt) {
/*  840:1138 */         this._cur.setNext(elt.next());
/*  841:     */       }
/*  842:1140 */       if (null == CursorableLinkedList.this._head.next()) {
/*  843:1141 */         this._cur.setPrev(null);
/*  844:1142 */       } else if (this._cur.prev() == elt) {
/*  845:1143 */         this._cur.setPrev(elt.prev());
/*  846:     */       }
/*  847:1145 */       if (this._lastReturned == elt) {
/*  848:1146 */         this._lastReturned = null;
/*  849:     */       }
/*  850:     */     }
/*  851:     */     
/*  852:     */     protected void listableInserted(CursorableLinkedList.Listable elt)
/*  853:     */     {
/*  854:1151 */       if ((null == this._cur.next()) && (null == this._cur.prev())) {
/*  855:1152 */         this._cur.setNext(elt);
/*  856:1153 */       } else if (this._cur.prev() == elt.prev()) {
/*  857:1154 */         this._cur.setNext(elt);
/*  858:     */       }
/*  859:1156 */       if (this._cur.next() == elt.next()) {
/*  860:1157 */         this._cur.setPrev(elt);
/*  861:     */       }
/*  862:1159 */       if (this._lastReturned == elt) {
/*  863:1160 */         this._lastReturned = null;
/*  864:     */       }
/*  865:     */     }
/*  866:     */     
/*  867:     */     protected void listableChanged(CursorableLinkedList.Listable elt)
/*  868:     */     {
/*  869:1165 */       if (this._lastReturned == elt) {
/*  870:1166 */         this._lastReturned = null;
/*  871:     */       }
/*  872:     */     }
/*  873:     */     
/*  874:     */     protected void checkForComod()
/*  875:     */     {
/*  876:1171 */       if (!this._valid) {
/*  877:1172 */         throw new ConcurrentModificationException();
/*  878:     */       }
/*  879:     */     }
/*  880:     */     
/*  881:     */     protected void invalidate()
/*  882:     */     {
/*  883:1177 */       this._valid = false;
/*  884:     */     }
/*  885:     */     
/*  886:     */     public void close()
/*  887:     */     {
/*  888:1189 */       if (this._valid)
/*  889:     */       {
/*  890:1190 */         this._valid = false;
/*  891:1191 */         CursorableLinkedList.this.unregisterCursor(this);
/*  892:     */       }
/*  893:     */     }
/*  894:     */   }
/*  895:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.CursorableLinkedList
 * JD-Core Version:    0.7.0.1
 */