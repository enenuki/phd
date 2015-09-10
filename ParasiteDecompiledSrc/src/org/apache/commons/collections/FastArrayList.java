/*    1:     */ package org.apache.commons.collections;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Collection;
/*    5:     */ import java.util.ConcurrentModificationException;
/*    6:     */ import java.util.Iterator;
/*    7:     */ import java.util.List;
/*    8:     */ import java.util.ListIterator;
/*    9:     */ 
/*   10:     */ public class FastArrayList
/*   11:     */   extends ArrayList
/*   12:     */ {
/*   13:     */   public FastArrayList()
/*   14:     */   {
/*   15:  78 */     this.list = new ArrayList();
/*   16:     */   }
/*   17:     */   
/*   18:     */   public FastArrayList(int capacity)
/*   19:     */   {
/*   20:  91 */     this.list = new ArrayList(capacity);
/*   21:     */   }
/*   22:     */   
/*   23:     */   public FastArrayList(Collection collection)
/*   24:     */   {
/*   25: 106 */     this.list = new ArrayList(collection);
/*   26:     */   }
/*   27:     */   
/*   28: 117 */   protected ArrayList list = null;
/*   29: 126 */   protected boolean fast = false;
/*   30:     */   
/*   31:     */   public boolean getFast()
/*   32:     */   {
/*   33: 135 */     return this.fast;
/*   34:     */   }
/*   35:     */   
/*   36:     */   public void setFast(boolean fast)
/*   37:     */   {
/*   38: 144 */     this.fast = fast;
/*   39:     */   }
/*   40:     */   
/*   41:     */   public boolean add(Object element)
/*   42:     */   {
/*   43: 158 */     if (this.fast) {
/*   44: 159 */       synchronized (this)
/*   45:     */       {
/*   46: 160 */         ArrayList temp = (ArrayList)this.list.clone();
/*   47: 161 */         boolean result = temp.add(element);
/*   48: 162 */         this.list = temp;
/*   49: 163 */         return result;
/*   50:     */       }
/*   51:     */     }
/*   52: 166 */     synchronized (this.list)
/*   53:     */     {
/*   54: 167 */       return this.list.add(element);
/*   55:     */     }
/*   56:     */   }
/*   57:     */   
/*   58:     */   public void add(int index, Object element)
/*   59:     */   {
/*   60: 185 */     if (this.fast) {
/*   61: 186 */       synchronized (this)
/*   62:     */       {
/*   63: 187 */         ArrayList temp = (ArrayList)this.list.clone();
/*   64: 188 */         temp.add(index, element);
/*   65: 189 */         this.list = temp;
/*   66:     */       }
/*   67:     */     } else {
/*   68: 192 */       synchronized (this.list)
/*   69:     */       {
/*   70: 193 */         this.list.add(index, element);
/*   71:     */       }
/*   72:     */     }
/*   73:     */   }
/*   74:     */   
/*   75:     */   public boolean addAll(Collection collection)
/*   76:     */   {
/*   77: 209 */     if (this.fast) {
/*   78: 210 */       synchronized (this)
/*   79:     */       {
/*   80: 211 */         ArrayList temp = (ArrayList)this.list.clone();
/*   81: 212 */         boolean result = temp.addAll(collection);
/*   82: 213 */         this.list = temp;
/*   83: 214 */         return result;
/*   84:     */       }
/*   85:     */     }
/*   86: 217 */     synchronized (this.list)
/*   87:     */     {
/*   88: 218 */       return this.list.addAll(collection);
/*   89:     */     }
/*   90:     */   }
/*   91:     */   
/*   92:     */   public boolean addAll(int index, Collection collection)
/*   93:     */   {
/*   94: 237 */     if (this.fast) {
/*   95: 238 */       synchronized (this)
/*   96:     */       {
/*   97: 239 */         ArrayList temp = (ArrayList)this.list.clone();
/*   98: 240 */         boolean result = temp.addAll(index, collection);
/*   99: 241 */         this.list = temp;
/*  100: 242 */         return result;
/*  101:     */       }
/*  102:     */     }
/*  103: 245 */     synchronized (this.list)
/*  104:     */     {
/*  105: 246 */       return this.list.addAll(index, collection);
/*  106:     */     }
/*  107:     */   }
/*  108:     */   
/*  109:     */   public void clear()
/*  110:     */   {
/*  111: 262 */     if (this.fast) {
/*  112: 263 */       synchronized (this)
/*  113:     */       {
/*  114: 264 */         ArrayList temp = (ArrayList)this.list.clone();
/*  115: 265 */         temp.clear();
/*  116: 266 */         this.list = temp;
/*  117:     */       }
/*  118:     */     } else {
/*  119: 269 */       synchronized (this.list)
/*  120:     */       {
/*  121: 270 */         this.list.clear();
/*  122:     */       }
/*  123:     */     }
/*  124:     */   }
/*  125:     */   
/*  126:     */   public Object clone()
/*  127:     */   {
/*  128: 283 */     FastArrayList results = null;
/*  129: 284 */     if (this.fast) {
/*  130: 285 */       results = new FastArrayList(this.list);
/*  131:     */     } else {
/*  132: 287 */       synchronized (this.list)
/*  133:     */       {
/*  134: 288 */         results = new FastArrayList(this.list);
/*  135:     */       }
/*  136:     */     }
/*  137: 291 */     results.setFast(getFast());
/*  138: 292 */     return results;
/*  139:     */   }
/*  140:     */   
/*  141:     */   public boolean contains(Object element)
/*  142:     */   {
/*  143: 304 */     if (this.fast) {
/*  144: 305 */       return this.list.contains(element);
/*  145:     */     }
/*  146: 307 */     synchronized (this.list)
/*  147:     */     {
/*  148: 308 */       return this.list.contains(element);
/*  149:     */     }
/*  150:     */   }
/*  151:     */   
/*  152:     */   public boolean containsAll(Collection collection)
/*  153:     */   {
/*  154: 323 */     if (this.fast) {
/*  155: 324 */       return this.list.containsAll(collection);
/*  156:     */     }
/*  157: 326 */     synchronized (this.list)
/*  158:     */     {
/*  159: 327 */       return this.list.containsAll(collection);
/*  160:     */     }
/*  161:     */   }
/*  162:     */   
/*  163:     */   public void ensureCapacity(int capacity)
/*  164:     */   {
/*  165: 343 */     if (this.fast) {
/*  166: 344 */       synchronized (this)
/*  167:     */       {
/*  168: 345 */         ArrayList temp = (ArrayList)this.list.clone();
/*  169: 346 */         temp.ensureCapacity(capacity);
/*  170: 347 */         this.list = temp;
/*  171:     */       }
/*  172:     */     } else {
/*  173: 350 */       synchronized (this.list)
/*  174:     */       {
/*  175: 351 */         this.list.ensureCapacity(capacity);
/*  176:     */       }
/*  177:     */     }
/*  178:     */   }
/*  179:     */   
/*  180:     */   public boolean equals(Object o)
/*  181:     */   {
/*  182: 369 */     if (o == this) {
/*  183: 370 */       return true;
/*  184:     */     }
/*  185: 371 */     if (!(o instanceof List)) {
/*  186: 372 */       return false;
/*  187:     */     }
/*  188: 373 */     List lo = (List)o;
/*  189: 376 */     if (this.fast)
/*  190:     */     {
/*  191: 377 */       ListIterator li1 = this.list.listIterator();
/*  192: 378 */       ListIterator li2 = lo.listIterator();
/*  193: 379 */       while ((li1.hasNext()) && (li2.hasNext()))
/*  194:     */       {
/*  195: 380 */         Object o1 = li1.next();
/*  196: 381 */         Object o2 = li2.next();
/*  197: 382 */         if (o1 == null ? o2 != null : !o1.equals(o2)) {
/*  198: 383 */           return false;
/*  199:     */         }
/*  200:     */       }
/*  201: 385 */       return (!li1.hasNext()) && (!li2.hasNext());
/*  202:     */     }
/*  203: 387 */     synchronized (this.list)
/*  204:     */     {
/*  205: 388 */       ListIterator li1 = this.list.listIterator();
/*  206: 389 */       ListIterator li2 = lo.listIterator();
/*  207: 390 */       while ((li1.hasNext()) && (li2.hasNext()))
/*  208:     */       {
/*  209: 391 */         Object o1 = li1.next();
/*  210: 392 */         Object o2 = li2.next();
/*  211: 393 */         if (o1 == null ? o2 != null : !o1.equals(o2)) {
/*  212: 394 */           return false;
/*  213:     */         }
/*  214:     */       }
/*  215: 396 */       return (!li1.hasNext()) && (!li2.hasNext());
/*  216:     */     }
/*  217:     */   }
/*  218:     */   
/*  219:     */   public Object get(int index)
/*  220:     */   {
/*  221: 412 */     if (this.fast) {
/*  222: 413 */       return this.list.get(index);
/*  223:     */     }
/*  224: 415 */     synchronized (this.list)
/*  225:     */     {
/*  226: 416 */       return this.list.get(index);
/*  227:     */     }
/*  228:     */   }
/*  229:     */   
/*  230:     */   public int hashCode()
/*  231:     */   {
/*  232: 430 */     if (this.fast)
/*  233:     */     {
/*  234: 431 */       int hashCode = 1;
/*  235: 432 */       Iterator i = this.list.iterator();
/*  236: 433 */       while (i.hasNext())
/*  237:     */       {
/*  238: 434 */         Object o = i.next();
/*  239: 435 */         hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());
/*  240:     */       }
/*  241: 437 */       return hashCode;
/*  242:     */     }
/*  243: 439 */     synchronized (this.list)
/*  244:     */     {
/*  245: 440 */       int hashCode = 1;
/*  246: 441 */       Iterator i = this.list.iterator();
/*  247: 442 */       while (i.hasNext())
/*  248:     */       {
/*  249: 443 */         Object o = i.next();
/*  250: 444 */         hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());
/*  251:     */       }
/*  252: 446 */       return hashCode;
/*  253:     */     }
/*  254:     */   }
/*  255:     */   
/*  256:     */   public int indexOf(Object element)
/*  257:     */   {
/*  258: 462 */     if (this.fast) {
/*  259: 463 */       return this.list.indexOf(element);
/*  260:     */     }
/*  261: 465 */     synchronized (this.list)
/*  262:     */     {
/*  263: 466 */       return this.list.indexOf(element);
/*  264:     */     }
/*  265:     */   }
/*  266:     */   
/*  267:     */   public boolean isEmpty()
/*  268:     */   {
/*  269: 478 */     if (this.fast) {
/*  270: 479 */       return this.list.isEmpty();
/*  271:     */     }
/*  272: 481 */     synchronized (this.list)
/*  273:     */     {
/*  274: 482 */       return this.list.isEmpty();
/*  275:     */     }
/*  276:     */   }
/*  277:     */   
/*  278:     */   public Iterator iterator()
/*  279:     */   {
/*  280: 507 */     if (this.fast) {
/*  281: 508 */       return new ListIter(0);
/*  282:     */     }
/*  283: 510 */     return this.list.iterator();
/*  284:     */   }
/*  285:     */   
/*  286:     */   public int lastIndexOf(Object element)
/*  287:     */   {
/*  288: 524 */     if (this.fast) {
/*  289: 525 */       return this.list.lastIndexOf(element);
/*  290:     */     }
/*  291: 527 */     synchronized (this.list)
/*  292:     */     {
/*  293: 528 */       return this.list.lastIndexOf(element);
/*  294:     */     }
/*  295:     */   }
/*  296:     */   
/*  297:     */   public ListIterator listIterator()
/*  298:     */   {
/*  299: 553 */     if (this.fast) {
/*  300: 554 */       return new ListIter(0);
/*  301:     */     }
/*  302: 556 */     return this.list.listIterator();
/*  303:     */   }
/*  304:     */   
/*  305:     */   public ListIterator listIterator(int index)
/*  306:     */   {
/*  307: 582 */     if (this.fast) {
/*  308: 583 */       return new ListIter(index);
/*  309:     */     }
/*  310: 585 */     return this.list.listIterator(index);
/*  311:     */   }
/*  312:     */   
/*  313:     */   public Object remove(int index)
/*  314:     */   {
/*  315: 600 */     if (this.fast) {
/*  316: 601 */       synchronized (this)
/*  317:     */       {
/*  318: 602 */         ArrayList temp = (ArrayList)this.list.clone();
/*  319: 603 */         Object result = temp.remove(index);
/*  320: 604 */         this.list = temp;
/*  321: 605 */         return result;
/*  322:     */       }
/*  323:     */     }
/*  324: 608 */     synchronized (this.list)
/*  325:     */     {
/*  326: 609 */       return this.list.remove(index);
/*  327:     */     }
/*  328:     */   }
/*  329:     */   
/*  330:     */   public boolean remove(Object element)
/*  331:     */   {
/*  332: 624 */     if (this.fast) {
/*  333: 625 */       synchronized (this)
/*  334:     */       {
/*  335: 626 */         ArrayList temp = (ArrayList)this.list.clone();
/*  336: 627 */         boolean result = temp.remove(element);
/*  337: 628 */         this.list = temp;
/*  338: 629 */         return result;
/*  339:     */       }
/*  340:     */     }
/*  341: 632 */     synchronized (this.list)
/*  342:     */     {
/*  343: 633 */       return this.list.remove(element);
/*  344:     */     }
/*  345:     */   }
/*  346:     */   
/*  347:     */   public boolean removeAll(Collection collection)
/*  348:     */   {
/*  349: 651 */     if (this.fast) {
/*  350: 652 */       synchronized (this)
/*  351:     */       {
/*  352: 653 */         ArrayList temp = (ArrayList)this.list.clone();
/*  353: 654 */         boolean result = temp.removeAll(collection);
/*  354: 655 */         this.list = temp;
/*  355: 656 */         return result;
/*  356:     */       }
/*  357:     */     }
/*  358: 659 */     synchronized (this.list)
/*  359:     */     {
/*  360: 660 */       return this.list.removeAll(collection);
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   public boolean retainAll(Collection collection)
/*  365:     */   {
/*  366: 678 */     if (this.fast) {
/*  367: 679 */       synchronized (this)
/*  368:     */       {
/*  369: 680 */         ArrayList temp = (ArrayList)this.list.clone();
/*  370: 681 */         boolean result = temp.retainAll(collection);
/*  371: 682 */         this.list = temp;
/*  372: 683 */         return result;
/*  373:     */       }
/*  374:     */     }
/*  375: 686 */     synchronized (this.list)
/*  376:     */     {
/*  377: 687 */       return this.list.retainAll(collection);
/*  378:     */     }
/*  379:     */   }
/*  380:     */   
/*  381:     */   public Object set(int index, Object element)
/*  382:     */   {
/*  383: 709 */     if (this.fast) {
/*  384: 710 */       return this.list.set(index, element);
/*  385:     */     }
/*  386: 712 */     synchronized (this.list)
/*  387:     */     {
/*  388: 713 */       return this.list.set(index, element);
/*  389:     */     }
/*  390:     */   }
/*  391:     */   
/*  392:     */   public int size()
/*  393:     */   {
/*  394: 725 */     if (this.fast) {
/*  395: 726 */       return this.list.size();
/*  396:     */     }
/*  397: 728 */     synchronized (this.list)
/*  398:     */     {
/*  399: 729 */       return this.list.size();
/*  400:     */     }
/*  401:     */   }
/*  402:     */   
/*  403:     */   public List subList(int fromIndex, int toIndex)
/*  404:     */   {
/*  405: 749 */     if (this.fast) {
/*  406: 750 */       return new SubList(fromIndex, toIndex);
/*  407:     */     }
/*  408: 752 */     return this.list.subList(fromIndex, toIndex);
/*  409:     */   }
/*  410:     */   
/*  411:     */   public Object[] toArray()
/*  412:     */   {
/*  413: 763 */     if (this.fast) {
/*  414: 764 */       return this.list.toArray();
/*  415:     */     }
/*  416: 766 */     synchronized (this.list)
/*  417:     */     {
/*  418: 767 */       return this.list.toArray();
/*  419:     */     }
/*  420:     */   }
/*  421:     */   
/*  422:     */   public Object[] toArray(Object[] array)
/*  423:     */   {
/*  424: 788 */     if (this.fast) {
/*  425: 789 */       return this.list.toArray(array);
/*  426:     */     }
/*  427: 791 */     synchronized (this.list)
/*  428:     */     {
/*  429: 792 */       return this.list.toArray(array);
/*  430:     */     }
/*  431:     */   }
/*  432:     */   
/*  433:     */   public String toString()
/*  434:     */   {
/*  435: 804 */     StringBuffer sb = new StringBuffer("FastArrayList[");
/*  436: 805 */     sb.append(this.list.toString());
/*  437: 806 */     sb.append("]");
/*  438: 807 */     return sb.toString();
/*  439:     */   }
/*  440:     */   
/*  441:     */   public void trimToSize()
/*  442:     */   {
/*  443: 819 */     if (this.fast) {
/*  444: 820 */       synchronized (this)
/*  445:     */       {
/*  446: 821 */         ArrayList temp = (ArrayList)this.list.clone();
/*  447: 822 */         temp.trimToSize();
/*  448: 823 */         this.list = temp;
/*  449:     */       }
/*  450:     */     } else {
/*  451: 826 */       synchronized (this.list)
/*  452:     */       {
/*  453: 827 */         this.list.trimToSize();
/*  454:     */       }
/*  455:     */     }
/*  456:     */   }
/*  457:     */   
/*  458:     */   private class SubList
/*  459:     */     implements List
/*  460:     */   {
/*  461:     */     private int first;
/*  462:     */     private int last;
/*  463:     */     private List expected;
/*  464:     */     
/*  465:     */     public SubList(int first, int last)
/*  466:     */     {
/*  467: 843 */       this.first = first;
/*  468: 844 */       this.last = last;
/*  469: 845 */       this.expected = FastArrayList.this.list;
/*  470:     */     }
/*  471:     */     
/*  472:     */     private List get(List l)
/*  473:     */     {
/*  474: 849 */       if (FastArrayList.this.list != this.expected) {
/*  475: 850 */         throw new ConcurrentModificationException();
/*  476:     */       }
/*  477: 852 */       return l.subList(this.first, this.last);
/*  478:     */     }
/*  479:     */     
/*  480:     */     public void clear()
/*  481:     */     {
/*  482: 856 */       if (FastArrayList.this.fast) {
/*  483: 857 */         synchronized (FastArrayList.this)
/*  484:     */         {
/*  485: 858 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  486: 859 */           get(temp).clear();
/*  487: 860 */           this.last = this.first;
/*  488: 861 */           FastArrayList.this.list = temp;
/*  489: 862 */           this.expected = temp;
/*  490:     */         }
/*  491:     */       } else {
/*  492: 865 */         synchronized (FastArrayList.this.list)
/*  493:     */         {
/*  494: 866 */           get(this.expected).clear();
/*  495:     */         }
/*  496:     */       }
/*  497:     */     }
/*  498:     */     
/*  499:     */     public boolean remove(Object o)
/*  500:     */     {
/*  501: 872 */       if (FastArrayList.this.fast) {
/*  502: 873 */         synchronized (FastArrayList.this)
/*  503:     */         {
/*  504: 874 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  505: 875 */           boolean r = get(temp).remove(o);
/*  506: 876 */           if (r) {
/*  507: 876 */             this.last -= 1;
/*  508:     */           }
/*  509: 877 */           FastArrayList.this.list = temp;
/*  510: 878 */           this.expected = temp;
/*  511: 879 */           return r;
/*  512:     */         }
/*  513:     */       }
/*  514: 882 */       synchronized (FastArrayList.this.list)
/*  515:     */       {
/*  516: 883 */         return get(this.expected).remove(o);
/*  517:     */       }
/*  518:     */     }
/*  519:     */     
/*  520:     */     public boolean removeAll(Collection o)
/*  521:     */     {
/*  522: 889 */       if (FastArrayList.this.fast) {
/*  523: 890 */         synchronized (FastArrayList.this)
/*  524:     */         {
/*  525: 891 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  526: 892 */           List sub = get(temp);
/*  527: 893 */           boolean r = sub.removeAll(o);
/*  528: 894 */           if (r) {
/*  529: 894 */             this.last = (this.first + sub.size());
/*  530:     */           }
/*  531: 895 */           FastArrayList.this.list = temp;
/*  532: 896 */           this.expected = temp;
/*  533: 897 */           return r;
/*  534:     */         }
/*  535:     */       }
/*  536: 900 */       synchronized (FastArrayList.this.list)
/*  537:     */       {
/*  538: 901 */         return get(this.expected).removeAll(o);
/*  539:     */       }
/*  540:     */     }
/*  541:     */     
/*  542:     */     public boolean retainAll(Collection o)
/*  543:     */     {
/*  544: 907 */       if (FastArrayList.this.fast) {
/*  545: 908 */         synchronized (FastArrayList.this)
/*  546:     */         {
/*  547: 909 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  548: 910 */           List sub = get(temp);
/*  549: 911 */           boolean r = sub.retainAll(o);
/*  550: 912 */           if (r) {
/*  551: 912 */             this.last = (this.first + sub.size());
/*  552:     */           }
/*  553: 913 */           FastArrayList.this.list = temp;
/*  554: 914 */           this.expected = temp;
/*  555: 915 */           return r;
/*  556:     */         }
/*  557:     */       }
/*  558: 918 */       synchronized (FastArrayList.this.list)
/*  559:     */       {
/*  560: 919 */         return get(this.expected).retainAll(o);
/*  561:     */       }
/*  562:     */     }
/*  563:     */     
/*  564:     */     public int size()
/*  565:     */     {
/*  566: 925 */       if (FastArrayList.this.fast) {
/*  567: 926 */         return get(this.expected).size();
/*  568:     */       }
/*  569: 928 */       synchronized (FastArrayList.this.list)
/*  570:     */       {
/*  571: 929 */         return get(this.expected).size();
/*  572:     */       }
/*  573:     */     }
/*  574:     */     
/*  575:     */     public boolean isEmpty()
/*  576:     */     {
/*  577: 936 */       if (FastArrayList.this.fast) {
/*  578: 937 */         return get(this.expected).isEmpty();
/*  579:     */       }
/*  580: 939 */       synchronized (FastArrayList.this.list)
/*  581:     */       {
/*  582: 940 */         return get(this.expected).isEmpty();
/*  583:     */       }
/*  584:     */     }
/*  585:     */     
/*  586:     */     public boolean contains(Object o)
/*  587:     */     {
/*  588: 946 */       if (FastArrayList.this.fast) {
/*  589: 947 */         return get(this.expected).contains(o);
/*  590:     */       }
/*  591: 949 */       synchronized (FastArrayList.this.list)
/*  592:     */       {
/*  593: 950 */         return get(this.expected).contains(o);
/*  594:     */       }
/*  595:     */     }
/*  596:     */     
/*  597:     */     public boolean containsAll(Collection o)
/*  598:     */     {
/*  599: 956 */       if (FastArrayList.this.fast) {
/*  600: 957 */         return get(this.expected).containsAll(o);
/*  601:     */       }
/*  602: 959 */       synchronized (FastArrayList.this.list)
/*  603:     */       {
/*  604: 960 */         return get(this.expected).containsAll(o);
/*  605:     */       }
/*  606:     */     }
/*  607:     */     
/*  608:     */     public Object[] toArray(Object[] o)
/*  609:     */     {
/*  610: 966 */       if (FastArrayList.this.fast) {
/*  611: 967 */         return get(this.expected).toArray(o);
/*  612:     */       }
/*  613: 969 */       synchronized (FastArrayList.this.list)
/*  614:     */       {
/*  615: 970 */         return get(this.expected).toArray(o);
/*  616:     */       }
/*  617:     */     }
/*  618:     */     
/*  619:     */     public Object[] toArray()
/*  620:     */     {
/*  621: 976 */       if (FastArrayList.this.fast) {
/*  622: 977 */         return get(this.expected).toArray();
/*  623:     */       }
/*  624: 979 */       synchronized (FastArrayList.this.list)
/*  625:     */       {
/*  626: 980 */         return get(this.expected).toArray();
/*  627:     */       }
/*  628:     */     }
/*  629:     */     
/*  630:     */     public boolean equals(Object o)
/*  631:     */     {
/*  632: 987 */       if (o == this) {
/*  633: 987 */         return true;
/*  634:     */       }
/*  635: 988 */       if (FastArrayList.this.fast) {
/*  636: 989 */         return get(this.expected).equals(o);
/*  637:     */       }
/*  638: 991 */       synchronized (FastArrayList.this.list)
/*  639:     */       {
/*  640: 992 */         return get(this.expected).equals(o);
/*  641:     */       }
/*  642:     */     }
/*  643:     */     
/*  644:     */     public int hashCode()
/*  645:     */     {
/*  646: 998 */       if (FastArrayList.this.fast) {
/*  647: 999 */         return get(this.expected).hashCode();
/*  648:     */       }
/*  649:1001 */       synchronized (FastArrayList.this.list)
/*  650:     */       {
/*  651:1002 */         return get(this.expected).hashCode();
/*  652:     */       }
/*  653:     */     }
/*  654:     */     
/*  655:     */     public boolean add(Object o)
/*  656:     */     {
/*  657:1008 */       if (FastArrayList.this.fast) {
/*  658:1009 */         synchronized (FastArrayList.this)
/*  659:     */         {
/*  660:1010 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  661:1011 */           boolean r = get(temp).add(o);
/*  662:1012 */           if (r) {
/*  663:1012 */             this.last += 1;
/*  664:     */           }
/*  665:1013 */           FastArrayList.this.list = temp;
/*  666:1014 */           this.expected = temp;
/*  667:1015 */           return r;
/*  668:     */         }
/*  669:     */       }
/*  670:1018 */       synchronized (FastArrayList.this.list)
/*  671:     */       {
/*  672:1019 */         return get(this.expected).add(o);
/*  673:     */       }
/*  674:     */     }
/*  675:     */     
/*  676:     */     public boolean addAll(Collection o)
/*  677:     */     {
/*  678:1025 */       if (FastArrayList.this.fast) {
/*  679:1026 */         synchronized (FastArrayList.this)
/*  680:     */         {
/*  681:1027 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  682:1028 */           boolean r = get(temp).addAll(o);
/*  683:1029 */           if (r) {
/*  684:1029 */             this.last += o.size();
/*  685:     */           }
/*  686:1030 */           FastArrayList.this.list = temp;
/*  687:1031 */           this.expected = temp;
/*  688:1032 */           return r;
/*  689:     */         }
/*  690:     */       }
/*  691:1035 */       synchronized (FastArrayList.this.list)
/*  692:     */       {
/*  693:1036 */         return get(this.expected).addAll(o);
/*  694:     */       }
/*  695:     */     }
/*  696:     */     
/*  697:     */     public void add(int i, Object o)
/*  698:     */     {
/*  699:1042 */       if (FastArrayList.this.fast) {
/*  700:1043 */         synchronized (FastArrayList.this)
/*  701:     */         {
/*  702:1044 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  703:1045 */           get(temp).add(i, o);
/*  704:1046 */           this.last += 1;
/*  705:1047 */           FastArrayList.this.list = temp;
/*  706:1048 */           this.expected = temp;
/*  707:     */         }
/*  708:     */       } else {
/*  709:1051 */         synchronized (FastArrayList.this.list)
/*  710:     */         {
/*  711:1052 */           get(this.expected).add(i, o);
/*  712:     */         }
/*  713:     */       }
/*  714:     */     }
/*  715:     */     
/*  716:     */     public boolean addAll(int i, Collection o)
/*  717:     */     {
/*  718:1058 */       if (FastArrayList.this.fast) {
/*  719:1059 */         synchronized (FastArrayList.this)
/*  720:     */         {
/*  721:1060 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  722:1061 */           boolean r = get(temp).addAll(i, o);
/*  723:1062 */           FastArrayList.this.list = temp;
/*  724:1063 */           if (r) {
/*  725:1063 */             this.last += o.size();
/*  726:     */           }
/*  727:1064 */           this.expected = temp;
/*  728:1065 */           return r;
/*  729:     */         }
/*  730:     */       }
/*  731:1068 */       synchronized (FastArrayList.this.list)
/*  732:     */       {
/*  733:1069 */         return get(this.expected).addAll(i, o);
/*  734:     */       }
/*  735:     */     }
/*  736:     */     
/*  737:     */     public Object remove(int i)
/*  738:     */     {
/*  739:1075 */       if (FastArrayList.this.fast) {
/*  740:1076 */         synchronized (FastArrayList.this)
/*  741:     */         {
/*  742:1077 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  743:1078 */           Object o = get(temp).remove(i);
/*  744:1079 */           this.last -= 1;
/*  745:1080 */           FastArrayList.this.list = temp;
/*  746:1081 */           this.expected = temp;
/*  747:1082 */           return o;
/*  748:     */         }
/*  749:     */       }
/*  750:1085 */       synchronized (FastArrayList.this.list)
/*  751:     */       {
/*  752:1086 */         return get(this.expected).remove(i);
/*  753:     */       }
/*  754:     */     }
/*  755:     */     
/*  756:     */     public Object set(int i, Object a)
/*  757:     */     {
/*  758:1092 */       if (FastArrayList.this.fast) {
/*  759:1093 */         synchronized (FastArrayList.this)
/*  760:     */         {
/*  761:1094 */           ArrayList temp = (ArrayList)FastArrayList.this.list.clone();
/*  762:1095 */           Object o = get(temp).set(i, a);
/*  763:1096 */           FastArrayList.this.list = temp;
/*  764:1097 */           this.expected = temp;
/*  765:1098 */           return o;
/*  766:     */         }
/*  767:     */       }
/*  768:1101 */       synchronized (FastArrayList.this.list)
/*  769:     */       {
/*  770:1102 */         return get(this.expected).set(i, a);
/*  771:     */       }
/*  772:     */     }
/*  773:     */     
/*  774:     */     public Iterator iterator()
/*  775:     */     {
/*  776:1109 */       return new SubListIter(0);
/*  777:     */     }
/*  778:     */     
/*  779:     */     public ListIterator listIterator()
/*  780:     */     {
/*  781:1113 */       return new SubListIter(0);
/*  782:     */     }
/*  783:     */     
/*  784:     */     public ListIterator listIterator(int i)
/*  785:     */     {
/*  786:1117 */       return new SubListIter(i);
/*  787:     */     }
/*  788:     */     
/*  789:     */     public Object get(int i)
/*  790:     */     {
/*  791:1122 */       if (FastArrayList.this.fast) {
/*  792:1123 */         return get(this.expected).get(i);
/*  793:     */       }
/*  794:1125 */       synchronized (FastArrayList.this.list)
/*  795:     */       {
/*  796:1126 */         return get(this.expected).get(i);
/*  797:     */       }
/*  798:     */     }
/*  799:     */     
/*  800:     */     public int indexOf(Object o)
/*  801:     */     {
/*  802:1132 */       if (FastArrayList.this.fast) {
/*  803:1133 */         return get(this.expected).indexOf(o);
/*  804:     */       }
/*  805:1135 */       synchronized (FastArrayList.this.list)
/*  806:     */       {
/*  807:1136 */         return get(this.expected).indexOf(o);
/*  808:     */       }
/*  809:     */     }
/*  810:     */     
/*  811:     */     public int lastIndexOf(Object o)
/*  812:     */     {
/*  813:1143 */       if (FastArrayList.this.fast) {
/*  814:1144 */         return get(this.expected).lastIndexOf(o);
/*  815:     */       }
/*  816:1146 */       synchronized (FastArrayList.this.list)
/*  817:     */       {
/*  818:1147 */         return get(this.expected).lastIndexOf(o);
/*  819:     */       }
/*  820:     */     }
/*  821:     */     
/*  822:     */     public List subList(int f, int l)
/*  823:     */     {
/*  824:1154 */       if (FastArrayList.this.list != this.expected) {
/*  825:1155 */         throw new ConcurrentModificationException();
/*  826:     */       }
/*  827:1157 */       return new SubList(FastArrayList.this, this.first + f, f + l);
/*  828:     */     }
/*  829:     */     
/*  830:     */     private class SubListIter
/*  831:     */       implements ListIterator
/*  832:     */     {
/*  833:     */       private List expected;
/*  834:     */       private ListIterator iter;
/*  835:1165 */       private int lastReturnedIndex = -1;
/*  836:     */       
/*  837:     */       public SubListIter(int i)
/*  838:     */       {
/*  839:1169 */         this.expected = FastArrayList.this.list;
/*  840:1170 */         this.iter = FastArrayList.SubList.this.get(this.expected).listIterator(i);
/*  841:     */       }
/*  842:     */       
/*  843:     */       private void checkMod()
/*  844:     */       {
/*  845:1174 */         if (FastArrayList.this.list != this.expected) {
/*  846:1175 */           throw new ConcurrentModificationException();
/*  847:     */         }
/*  848:     */       }
/*  849:     */       
/*  850:     */       List get()
/*  851:     */       {
/*  852:1180 */         return FastArrayList.SubList.this.get(this.expected);
/*  853:     */       }
/*  854:     */       
/*  855:     */       public boolean hasNext()
/*  856:     */       {
/*  857:1184 */         checkMod();
/*  858:1185 */         return this.iter.hasNext();
/*  859:     */       }
/*  860:     */       
/*  861:     */       public Object next()
/*  862:     */       {
/*  863:1189 */         checkMod();
/*  864:1190 */         this.lastReturnedIndex = this.iter.nextIndex();
/*  865:1191 */         return this.iter.next();
/*  866:     */       }
/*  867:     */       
/*  868:     */       public boolean hasPrevious()
/*  869:     */       {
/*  870:1195 */         checkMod();
/*  871:1196 */         return this.iter.hasPrevious();
/*  872:     */       }
/*  873:     */       
/*  874:     */       public Object previous()
/*  875:     */       {
/*  876:1200 */         checkMod();
/*  877:1201 */         this.lastReturnedIndex = this.iter.previousIndex();
/*  878:1202 */         return this.iter.previous();
/*  879:     */       }
/*  880:     */       
/*  881:     */       public int previousIndex()
/*  882:     */       {
/*  883:1206 */         checkMod();
/*  884:1207 */         return this.iter.previousIndex();
/*  885:     */       }
/*  886:     */       
/*  887:     */       public int nextIndex()
/*  888:     */       {
/*  889:1211 */         checkMod();
/*  890:1212 */         return this.iter.nextIndex();
/*  891:     */       }
/*  892:     */       
/*  893:     */       public void remove()
/*  894:     */       {
/*  895:1216 */         checkMod();
/*  896:1217 */         if (this.lastReturnedIndex < 0) {
/*  897:1218 */           throw new IllegalStateException();
/*  898:     */         }
/*  899:1220 */         get().remove(this.lastReturnedIndex);
/*  900:1221 */         FastArrayList.SubList.access$210(FastArrayList.SubList.this);
/*  901:1222 */         this.expected = FastArrayList.this.list;
/*  902:1223 */         this.iter = get().listIterator(this.lastReturnedIndex);
/*  903:1224 */         this.lastReturnedIndex = -1;
/*  904:     */       }
/*  905:     */       
/*  906:     */       public void set(Object o)
/*  907:     */       {
/*  908:1228 */         checkMod();
/*  909:1229 */         if (this.lastReturnedIndex < 0) {
/*  910:1230 */           throw new IllegalStateException();
/*  911:     */         }
/*  912:1232 */         get().set(this.lastReturnedIndex, o);
/*  913:1233 */         this.expected = FastArrayList.this.list;
/*  914:1234 */         this.iter = get().listIterator(previousIndex() + 1);
/*  915:     */       }
/*  916:     */       
/*  917:     */       public void add(Object o)
/*  918:     */       {
/*  919:1238 */         checkMod();
/*  920:1239 */         int i = nextIndex();
/*  921:1240 */         get().add(i, o);
/*  922:1241 */         FastArrayList.SubList.access$208(FastArrayList.SubList.this);
/*  923:1242 */         this.expected = FastArrayList.this.list;
/*  924:1243 */         this.iter = get().listIterator(i + 1);
/*  925:1244 */         this.lastReturnedIndex = -1;
/*  926:     */       }
/*  927:     */     }
/*  928:     */   }
/*  929:     */   
/*  930:     */   private class ListIter
/*  931:     */     implements ListIterator
/*  932:     */   {
/*  933:     */     private List expected;
/*  934:     */     private ListIterator iter;
/*  935:1258 */     private int lastReturnedIndex = -1;
/*  936:     */     
/*  937:     */     public ListIter(int i)
/*  938:     */     {
/*  939:1262 */       this.expected = FastArrayList.this.list;
/*  940:1263 */       this.iter = get().listIterator(i);
/*  941:     */     }
/*  942:     */     
/*  943:     */     private void checkMod()
/*  944:     */     {
/*  945:1267 */       if (FastArrayList.this.list != this.expected) {
/*  946:1268 */         throw new ConcurrentModificationException();
/*  947:     */       }
/*  948:     */     }
/*  949:     */     
/*  950:     */     List get()
/*  951:     */     {
/*  952:1273 */       return this.expected;
/*  953:     */     }
/*  954:     */     
/*  955:     */     public boolean hasNext()
/*  956:     */     {
/*  957:1277 */       return this.iter.hasNext();
/*  958:     */     }
/*  959:     */     
/*  960:     */     public Object next()
/*  961:     */     {
/*  962:1281 */       this.lastReturnedIndex = this.iter.nextIndex();
/*  963:1282 */       return this.iter.next();
/*  964:     */     }
/*  965:     */     
/*  966:     */     public boolean hasPrevious()
/*  967:     */     {
/*  968:1286 */       return this.iter.hasPrevious();
/*  969:     */     }
/*  970:     */     
/*  971:     */     public Object previous()
/*  972:     */     {
/*  973:1290 */       this.lastReturnedIndex = this.iter.previousIndex();
/*  974:1291 */       return this.iter.previous();
/*  975:     */     }
/*  976:     */     
/*  977:     */     public int previousIndex()
/*  978:     */     {
/*  979:1295 */       return this.iter.previousIndex();
/*  980:     */     }
/*  981:     */     
/*  982:     */     public int nextIndex()
/*  983:     */     {
/*  984:1299 */       return this.iter.nextIndex();
/*  985:     */     }
/*  986:     */     
/*  987:     */     public void remove()
/*  988:     */     {
/*  989:1303 */       checkMod();
/*  990:1304 */       if (this.lastReturnedIndex < 0) {
/*  991:1305 */         throw new IllegalStateException();
/*  992:     */       }
/*  993:1307 */       get().remove(this.lastReturnedIndex);
/*  994:1308 */       this.expected = FastArrayList.this.list;
/*  995:1309 */       this.iter = get().listIterator(this.lastReturnedIndex);
/*  996:1310 */       this.lastReturnedIndex = -1;
/*  997:     */     }
/*  998:     */     
/*  999:     */     public void set(Object o)
/* 1000:     */     {
/* 1001:1314 */       checkMod();
/* 1002:1315 */       if (this.lastReturnedIndex < 0) {
/* 1003:1316 */         throw new IllegalStateException();
/* 1004:     */       }
/* 1005:1318 */       get().set(this.lastReturnedIndex, o);
/* 1006:1319 */       this.expected = FastArrayList.this.list;
/* 1007:1320 */       this.iter = get().listIterator(previousIndex() + 1);
/* 1008:     */     }
/* 1009:     */     
/* 1010:     */     public void add(Object o)
/* 1011:     */     {
/* 1012:1324 */       checkMod();
/* 1013:1325 */       int i = nextIndex();
/* 1014:1326 */       get().add(i, o);
/* 1015:1327 */       this.expected = FastArrayList.this.list;
/* 1016:1328 */       this.iter = get().listIterator(i + 1);
/* 1017:1329 */       this.lastReturnedIndex = -1;
/* 1018:     */     }
/* 1019:     */   }
/* 1020:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.FastArrayList
 * JD-Core Version:    0.7.0.1
 */