/*    1:     */ package org.dom4j.tree;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.ObjectOutputStream;
/*    6:     */ import java.io.Serializable;
/*    7:     */ import java.util.AbstractCollection;
/*    8:     */ import java.util.AbstractMap;
/*    9:     */ import java.util.AbstractSet;
/*   10:     */ import java.util.Collection;
/*   11:     */ import java.util.Enumeration;
/*   12:     */ import java.util.Iterator;
/*   13:     */ import java.util.Map;
/*   14:     */ import java.util.Map.Entry;
/*   15:     */ import java.util.NoSuchElementException;
/*   16:     */ import java.util.Set;
/*   17:     */ 
/*   18:     */ class ConcurrentReaderHashMap
/*   19:     */   extends AbstractMap
/*   20:     */   implements Map, Cloneable, Serializable
/*   21:     */ {
/*   22: 187 */   protected final BarrierLock barrierLock = new BarrierLock();
/*   23:     */   protected transient Object lastWrite;
/*   24:     */   
/*   25:     */   protected final void recordModification(Object x)
/*   26:     */   {
/*   27: 200 */     synchronized (this.barrierLock)
/*   28:     */     {
/*   29: 201 */       this.lastWrite = x;
/*   30:     */     }
/*   31:     */   }
/*   32:     */   
/*   33:     */   protected final Entry[] getTableForReading()
/*   34:     */   {
/*   35: 210 */     synchronized (this.barrierLock)
/*   36:     */     {
/*   37: 211 */       return this.table;
/*   38:     */     }
/*   39:     */   }
/*   40:     */   
/*   41: 219 */   public static int DEFAULT_INITIAL_CAPACITY = 32;
/*   42:     */   private static final int MINIMUM_CAPACITY = 4;
/*   43:     */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*   44:     */   public static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*   45:     */   protected transient Entry[] table;
/*   46:     */   protected transient int count;
/*   47:     */   protected int threshold;
/*   48:     */   protected float loadFactor;
/*   49:     */   
/*   50:     */   private int p2capacity(int initialCapacity)
/*   51:     */   {
/*   52: 271 */     int cap = initialCapacity;
/*   53:     */     int result;
/*   54:     */     int result;
/*   55: 275 */     if ((cap > 1073741824) || (cap < 0))
/*   56:     */     {
/*   57: 276 */       result = 1073741824;
/*   58:     */     }
/*   59:     */     else
/*   60:     */     {
/*   61: 278 */       result = 4;
/*   62: 279 */       while (result < cap) {
/*   63: 280 */         result <<= 1;
/*   64:     */       }
/*   65:     */     }
/*   66: 282 */     return result;
/*   67:     */   }
/*   68:     */   
/*   69:     */   private static int hash(Object x)
/*   70:     */   {
/*   71: 291 */     int h = x.hashCode();
/*   72:     */     
/*   73:     */ 
/*   74:     */ 
/*   75: 295 */     return (h << 7) - h + (h >>> 9) + (h >>> 17);
/*   76:     */   }
/*   77:     */   
/*   78:     */   protected boolean eq(Object x, Object y)
/*   79:     */   {
/*   80: 302 */     return (x == y) || (x.equals(y));
/*   81:     */   }
/*   82:     */   
/*   83:     */   public ConcurrentReaderHashMap(int initialCapacity, float loadFactor)
/*   84:     */   {
/*   85: 320 */     if (loadFactor <= 0.0F) {
/*   86: 321 */       throw new IllegalArgumentException("Illegal Load factor: " + loadFactor);
/*   87:     */     }
/*   88: 323 */     this.loadFactor = loadFactor;
/*   89:     */     
/*   90: 325 */     int cap = p2capacity(initialCapacity);
/*   91:     */     
/*   92: 327 */     this.table = new Entry[cap];
/*   93: 328 */     this.threshold = ((int)(cap * loadFactor));
/*   94:     */   }
/*   95:     */   
/*   96:     */   public ConcurrentReaderHashMap(int initialCapacity)
/*   97:     */   {
/*   98: 342 */     this(initialCapacity, 0.75F);
/*   99:     */   }
/*  100:     */   
/*  101:     */   public ConcurrentReaderHashMap()
/*  102:     */   {
/*  103: 351 */     this(DEFAULT_INITIAL_CAPACITY, 0.75F);
/*  104:     */   }
/*  105:     */   
/*  106:     */   public ConcurrentReaderHashMap(Map t)
/*  107:     */   {
/*  108: 361 */     this(Math.max((int)(t.size() / 0.75F) + 1, 16), 0.75F);
/*  109:     */     
/*  110: 363 */     putAll(t);
/*  111:     */   }
/*  112:     */   
/*  113:     */   public synchronized int size()
/*  114:     */   {
/*  115: 373 */     return this.count;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public synchronized boolean isEmpty()
/*  119:     */   {
/*  120: 383 */     return this.count == 0;
/*  121:     */   }
/*  122:     */   
/*  123:     */   public Object get(Object key)
/*  124:     */   {
/*  125: 402 */     int hash = hash(key);
/*  126:     */     
/*  127:     */ 
/*  128:     */ 
/*  129:     */ 
/*  130:     */ 
/*  131:     */ 
/*  132:     */ 
/*  133:     */ 
/*  134:     */ 
/*  135: 412 */     Entry[] tab = this.table;
/*  136: 413 */     int index = hash & tab.length - 1;
/*  137: 414 */     Entry first = tab[index];
/*  138: 415 */     Entry e = first;
/*  139:     */     for (;;)
/*  140:     */     {
/*  141: 418 */       if (e == null)
/*  142:     */       {
/*  143: 423 */         Entry[] reread = getTableForReading();
/*  144: 424 */         if ((tab == reread) && (first == tab[index])) {
/*  145: 425 */           return null;
/*  146:     */         }
/*  147: 428 */         tab = reread;
/*  148: 429 */         e = first = tab[(index = hash & tab.length - 1)];
/*  149:     */       }
/*  150: 434 */       else if ((e.hash == hash) && (eq(key, e.key)))
/*  151:     */       {
/*  152: 435 */         Object value = e.value;
/*  153: 436 */         if (value != null) {
/*  154: 437 */           return value;
/*  155:     */         }
/*  156: 445 */         synchronized (this)
/*  157:     */         {
/*  158: 446 */           tab = this.table;
/*  159:     */         }
/*  160: 448 */         e = first = tab[(index = hash & tab.length - 1)];
/*  161:     */       }
/*  162:     */       else
/*  163:     */       {
/*  164: 451 */         e = e.next;
/*  165:     */       }
/*  166:     */     }
/*  167:     */   }
/*  168:     */   
/*  169:     */   public boolean containsKey(Object key)
/*  170:     */   {
/*  171: 469 */     return get(key) != null;
/*  172:     */   }
/*  173:     */   
/*  174:     */   public Object put(Object key, Object value)
/*  175:     */   {
/*  176: 493 */     if (value == null) {
/*  177: 494 */       throw new NullPointerException();
/*  178:     */     }
/*  179: 496 */     int hash = hash(key);
/*  180: 497 */     Entry[] tab = this.table;
/*  181: 498 */     int index = hash & tab.length - 1;
/*  182: 499 */     Entry first = tab[index];
/*  183: 502 */     for (Entry e = first; (e != null) && (
/*  184: 503 */           (e.hash != hash) || (!eq(key, e.key))); e = e.next) {}
/*  185: 506 */     synchronized (this)
/*  186:     */     {
/*  187: 507 */       if (tab == this.table) {
/*  188: 508 */         if (e == null)
/*  189:     */         {
/*  190: 510 */           if (first == tab[index])
/*  191:     */           {
/*  192: 512 */             Entry newEntry = new Entry(hash, key, value, first);
/*  193: 513 */             tab[index] = newEntry;
/*  194: 514 */             if (++this.count >= this.threshold) {
/*  195: 515 */               rehash();
/*  196:     */             } else {
/*  197: 517 */               recordModification(newEntry);
/*  198:     */             }
/*  199: 518 */             return null;
/*  200:     */           }
/*  201:     */         }
/*  202:     */         else
/*  203:     */         {
/*  204: 521 */           Object oldValue = e.value;
/*  205: 522 */           if ((first == tab[index]) && (oldValue != null))
/*  206:     */           {
/*  207: 523 */             e.value = value;
/*  208: 524 */             return oldValue;
/*  209:     */           }
/*  210:     */         }
/*  211:     */       }
/*  212: 530 */       return sput(key, value, hash);
/*  213:     */     }
/*  214:     */   }
/*  215:     */   
/*  216:     */   protected Object sput(Object key, Object value, int hash)
/*  217:     */   {
/*  218: 540 */     Entry[] tab = this.table;
/*  219: 541 */     int index = hash & tab.length - 1;
/*  220: 542 */     Entry first = tab[index];
/*  221: 543 */     Entry e = first;
/*  222:     */     for (;;)
/*  223:     */     {
/*  224: 546 */       if (e == null)
/*  225:     */       {
/*  226: 547 */         Entry newEntry = new Entry(hash, key, value, first);
/*  227: 548 */         tab[index] = newEntry;
/*  228: 549 */         if (++this.count >= this.threshold) {
/*  229: 550 */           rehash();
/*  230:     */         } else {
/*  231: 552 */           recordModification(newEntry);
/*  232:     */         }
/*  233: 553 */         return null;
/*  234:     */       }
/*  235: 554 */       if ((e.hash == hash) && (eq(key, e.key)))
/*  236:     */       {
/*  237: 555 */         Object oldValue = e.value;
/*  238: 556 */         e.value = value;
/*  239: 557 */         return oldValue;
/*  240:     */       }
/*  241: 559 */       e = e.next;
/*  242:     */     }
/*  243:     */   }
/*  244:     */   
/*  245:     */   protected void rehash()
/*  246:     */   {
/*  247: 569 */     Entry[] oldTable = this.table;
/*  248: 570 */     int oldCapacity = oldTable.length;
/*  249: 571 */     if (oldCapacity >= 1073741824)
/*  250:     */     {
/*  251: 572 */       this.threshold = 2147483647;
/*  252: 573 */       return;
/*  253:     */     }
/*  254: 576 */     int newCapacity = oldCapacity << 1;
/*  255: 577 */     int mask = newCapacity - 1;
/*  256: 578 */     this.threshold = ((int)(newCapacity * this.loadFactor));
/*  257:     */     
/*  258: 580 */     Entry[] newTable = new Entry[newCapacity];
/*  259: 593 */     for (int i = 0; i < oldCapacity; i++)
/*  260:     */     {
/*  261: 596 */       Entry e = oldTable[i];
/*  262: 598 */       if (e != null)
/*  263:     */       {
/*  264: 599 */         int idx = e.hash & mask;
/*  265: 600 */         Entry next = e.next;
/*  266: 603 */         if (next == null)
/*  267:     */         {
/*  268: 604 */           newTable[idx] = e;
/*  269:     */         }
/*  270:     */         else
/*  271:     */         {
/*  272: 608 */           Entry lastRun = e;
/*  273: 609 */           int lastIdx = idx;
/*  274: 610 */           for (Entry last = next; last != null; last = last.next)
/*  275:     */           {
/*  276: 611 */             int k = last.hash & mask;
/*  277: 612 */             if (k != lastIdx)
/*  278:     */             {
/*  279: 613 */               lastIdx = k;
/*  280: 614 */               lastRun = last;
/*  281:     */             }
/*  282:     */           }
/*  283: 617 */           newTable[lastIdx] = lastRun;
/*  284: 620 */           for (Entry p = e; p != lastRun; p = p.next)
/*  285:     */           {
/*  286: 621 */             int k = p.hash & mask;
/*  287: 622 */             newTable[k] = new Entry(p.hash, p.key, p.value, newTable[k]);
/*  288:     */           }
/*  289:     */         }
/*  290:     */       }
/*  291:     */     }
/*  292: 629 */     this.table = newTable;
/*  293: 630 */     recordModification(newTable);
/*  294:     */   }
/*  295:     */   
/*  296:     */   public Object remove(Object key)
/*  297:     */   {
/*  298: 654 */     int hash = hash(key);
/*  299: 655 */     Entry[] tab = this.table;
/*  300: 656 */     int index = hash & tab.length - 1;
/*  301: 657 */     Entry first = tab[index];
/*  302: 658 */     Entry e = first;
/*  303: 660 */     for (e = first; (e != null) && (
/*  304: 661 */           (e.hash != hash) || (!eq(key, e.key))); e = e.next) {}
/*  305: 664 */     synchronized (this)
/*  306:     */     {
/*  307: 665 */       if (tab == this.table) {
/*  308: 666 */         if (e == null)
/*  309:     */         {
/*  310: 667 */           if (first == tab[index]) {
/*  311: 668 */             return null;
/*  312:     */           }
/*  313:     */         }
/*  314:     */         else
/*  315:     */         {
/*  316: 670 */           Object oldValue = e.value;
/*  317: 671 */           if ((first == tab[index]) && (oldValue != null))
/*  318:     */           {
/*  319: 672 */             e.value = null;
/*  320: 673 */             this.count -= 1;
/*  321:     */             
/*  322: 675 */             Entry head = e.next;
/*  323: 676 */             for (Entry p = first; p != e; p = p.next) {
/*  324: 677 */               head = new Entry(p.hash, p.key, p.value, head);
/*  325:     */             }
/*  326: 679 */             tab[index] = head;
/*  327: 680 */             recordModification(head);
/*  328: 681 */             return oldValue;
/*  329:     */           }
/*  330:     */         }
/*  331:     */       }
/*  332: 687 */       return sremove(key, hash);
/*  333:     */     }
/*  334:     */   }
/*  335:     */   
/*  336:     */   protected Object sremove(Object key, int hash)
/*  337:     */   {
/*  338: 697 */     Entry[] tab = this.table;
/*  339: 698 */     int index = hash & tab.length - 1;
/*  340: 699 */     Entry first = tab[index];
/*  341: 701 */     for (Entry e = first; e != null; e = e.next) {
/*  342: 702 */       if ((e.hash == hash) && (eq(key, e.key)))
/*  343:     */       {
/*  344: 703 */         Object oldValue = e.value;
/*  345: 704 */         e.value = null;
/*  346: 705 */         this.count -= 1;
/*  347: 706 */         Entry head = e.next;
/*  348: 707 */         for (Entry p = first; p != e; p = p.next) {
/*  349: 708 */           head = new Entry(p.hash, p.key, p.value, head);
/*  350:     */         }
/*  351: 710 */         tab[index] = head;
/*  352: 711 */         recordModification(head);
/*  353: 712 */         return oldValue;
/*  354:     */       }
/*  355:     */     }
/*  356: 715 */     return null;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public boolean containsValue(Object value)
/*  360:     */   {
/*  361: 732 */     if (value == null) {
/*  362: 733 */       throw new NullPointerException();
/*  363:     */     }
/*  364: 735 */     Entry[] tab = getTableForReading();
/*  365: 737 */     for (int i = 0; i < tab.length; i++) {
/*  366: 738 */       for (Entry e = tab[i]; e != null; e = e.next) {
/*  367: 739 */         if (value.equals(e.value)) {
/*  368: 740 */           return true;
/*  369:     */         }
/*  370:     */       }
/*  371:     */     }
/*  372: 743 */     return false;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public boolean contains(Object value)
/*  376:     */   {
/*  377: 767 */     return containsValue(value);
/*  378:     */   }
/*  379:     */   
/*  380:     */   public synchronized void putAll(Map t)
/*  381:     */   {
/*  382: 781 */     int n = t.size();
/*  383: 782 */     if (n == 0) {
/*  384: 783 */       return;
/*  385:     */     }
/*  386: 788 */     while (n >= this.threshold) {
/*  387: 789 */       rehash();
/*  388:     */     }
/*  389: 791 */     for (Iterator it = t.entrySet().iterator(); it.hasNext();)
/*  390:     */     {
/*  391: 792 */       Map.Entry entry = (Map.Entry)it.next();
/*  392: 793 */       Object key = entry.getKey();
/*  393: 794 */       Object value = entry.getValue();
/*  394: 795 */       put(key, value);
/*  395:     */     }
/*  396:     */   }
/*  397:     */   
/*  398:     */   public synchronized void clear()
/*  399:     */   {
/*  400: 803 */     Entry[] tab = this.table;
/*  401: 804 */     for (int i = 0; i < tab.length; i++)
/*  402:     */     {
/*  403: 808 */       for (Entry e = tab[i]; e != null; e = e.next) {
/*  404: 809 */         e.value = null;
/*  405:     */       }
/*  406: 811 */       tab[i] = null;
/*  407:     */     }
/*  408: 813 */     this.count = 0;
/*  409: 814 */     recordModification(tab);
/*  410:     */   }
/*  411:     */   
/*  412:     */   public synchronized Object clone()
/*  413:     */   {
/*  414:     */     try
/*  415:     */     {
/*  416: 826 */       ConcurrentReaderHashMap t = (ConcurrentReaderHashMap)super.clone();
/*  417:     */       
/*  418: 828 */       t.keySet = null;
/*  419: 829 */       t.entrySet = null;
/*  420: 830 */       t.values = null;
/*  421:     */       
/*  422: 832 */       Entry[] tab = this.table;
/*  423: 833 */       t.table = new Entry[tab.length];
/*  424: 834 */       Entry[] ttab = t.table;
/*  425: 836 */       for (int i = 0; i < tab.length; i++)
/*  426:     */       {
/*  427: 837 */         Entry first = null;
/*  428: 838 */         for (Entry e = tab[i]; e != null; e = e.next) {
/*  429: 839 */           first = new Entry(e.hash, e.key, e.value, first);
/*  430:     */         }
/*  431: 840 */         ttab[i] = first;
/*  432:     */       }
/*  433: 843 */       return t;
/*  434:     */     }
/*  435:     */     catch (CloneNotSupportedException e)
/*  436:     */     {
/*  437: 846 */       throw new InternalError();
/*  438:     */     }
/*  439:     */   }
/*  440:     */   
/*  441: 852 */   protected transient Set keySet = null;
/*  442: 854 */   protected transient Set entrySet = null;
/*  443: 856 */   protected transient Collection values = null;
/*  444:     */   
/*  445:     */   public Set keySet()
/*  446:     */   {
/*  447: 871 */     Set ks = this.keySet;
/*  448: 872 */     return this.keySet = new KeySet(null);
/*  449:     */   }
/*  450:     */   
/*  451:     */   protected static class BarrierLock
/*  452:     */     implements Serializable
/*  453:     */   {}
/*  454:     */   
/*  455:     */   private class KeySet
/*  456:     */     extends AbstractSet
/*  457:     */   {
/*  458:     */     KeySet(ConcurrentReaderHashMap.1 x1)
/*  459:     */     {
/*  460: 875 */       this();
/*  461:     */     }
/*  462:     */     
/*  463:     */     public Iterator iterator()
/*  464:     */     {
/*  465: 877 */       return new ConcurrentReaderHashMap.KeyIterator(ConcurrentReaderHashMap.this);
/*  466:     */     }
/*  467:     */     
/*  468:     */     public int size()
/*  469:     */     {
/*  470: 881 */       return ConcurrentReaderHashMap.this.size();
/*  471:     */     }
/*  472:     */     
/*  473:     */     public boolean contains(Object o)
/*  474:     */     {
/*  475: 885 */       return ConcurrentReaderHashMap.this.containsKey(o);
/*  476:     */     }
/*  477:     */     
/*  478:     */     public boolean remove(Object o)
/*  479:     */     {
/*  480: 889 */       return ConcurrentReaderHashMap.this.remove(o) != null;
/*  481:     */     }
/*  482:     */     
/*  483:     */     public void clear()
/*  484:     */     {
/*  485: 893 */       ConcurrentReaderHashMap.this.clear();
/*  486:     */     }
/*  487:     */     
/*  488:     */     private KeySet() {}
/*  489:     */   }
/*  490:     */   
/*  491:     */   public Collection values()
/*  492:     */   {
/*  493: 911 */     Collection vs = this.values;
/*  494: 912 */     return this.values = new Values(null);
/*  495:     */   }
/*  496:     */   
/*  497:     */   private class Values
/*  498:     */     extends AbstractCollection
/*  499:     */   {
/*  500:     */     Values(ConcurrentReaderHashMap.1 x1)
/*  501:     */     {
/*  502: 915 */       this();
/*  503:     */     }
/*  504:     */     
/*  505:     */     public Iterator iterator()
/*  506:     */     {
/*  507: 917 */       return new ConcurrentReaderHashMap.ValueIterator(ConcurrentReaderHashMap.this);
/*  508:     */     }
/*  509:     */     
/*  510:     */     public int size()
/*  511:     */     {
/*  512: 921 */       return ConcurrentReaderHashMap.this.size();
/*  513:     */     }
/*  514:     */     
/*  515:     */     public boolean contains(Object o)
/*  516:     */     {
/*  517: 925 */       return ConcurrentReaderHashMap.this.containsValue(o);
/*  518:     */     }
/*  519:     */     
/*  520:     */     public void clear()
/*  521:     */     {
/*  522: 929 */       ConcurrentReaderHashMap.this.clear();
/*  523:     */     }
/*  524:     */     
/*  525:     */     private Values() {}
/*  526:     */   }
/*  527:     */   
/*  528:     */   public Set entrySet()
/*  529:     */   {
/*  530: 948 */     Set es = this.entrySet;
/*  531: 949 */     return this.entrySet = new EntrySet(null);
/*  532:     */   }
/*  533:     */   
/*  534:     */   private class EntrySet
/*  535:     */     extends AbstractSet
/*  536:     */   {
/*  537:     */     EntrySet(ConcurrentReaderHashMap.1 x1)
/*  538:     */     {
/*  539: 952 */       this();
/*  540:     */     }
/*  541:     */     
/*  542:     */     public Iterator iterator()
/*  543:     */     {
/*  544: 954 */       return new ConcurrentReaderHashMap.HashIterator(ConcurrentReaderHashMap.this);
/*  545:     */     }
/*  546:     */     
/*  547:     */     public boolean contains(Object o)
/*  548:     */     {
/*  549: 958 */       if (!(o instanceof Map.Entry)) {
/*  550: 959 */         return false;
/*  551:     */       }
/*  552: 960 */       Map.Entry entry = (Map.Entry)o;
/*  553: 961 */       Object v = ConcurrentReaderHashMap.this.get(entry.getKey());
/*  554: 962 */       return (v != null) && (v.equals(entry.getValue()));
/*  555:     */     }
/*  556:     */     
/*  557:     */     public boolean remove(Object o)
/*  558:     */     {
/*  559: 966 */       if (!(o instanceof Map.Entry)) {
/*  560: 967 */         return false;
/*  561:     */       }
/*  562: 968 */       return ConcurrentReaderHashMap.this.findAndRemoveEntry((Map.Entry)o);
/*  563:     */     }
/*  564:     */     
/*  565:     */     public int size()
/*  566:     */     {
/*  567: 973 */       return ConcurrentReaderHashMap.this.size();
/*  568:     */     }
/*  569:     */     
/*  570:     */     public void clear()
/*  571:     */     {
/*  572: 977 */       ConcurrentReaderHashMap.this.clear();
/*  573:     */     }
/*  574:     */     
/*  575:     */     private EntrySet() {}
/*  576:     */   }
/*  577:     */   
/*  578:     */   protected synchronized boolean findAndRemoveEntry(Map.Entry entry)
/*  579:     */   {
/*  580: 985 */     Object key = entry.getKey();
/*  581: 986 */     Object v = get(key);
/*  582: 987 */     if ((v != null) && (v.equals(entry.getValue())))
/*  583:     */     {
/*  584: 988 */       remove(key);
/*  585: 989 */       return true;
/*  586:     */     }
/*  587: 991 */     return false;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public Enumeration keys()
/*  591:     */   {
/*  592:1004 */     return new KeyIterator();
/*  593:     */   }
/*  594:     */   
/*  595:     */   public Enumeration elements()
/*  596:     */   {
/*  597:1019 */     return new ValueIterator();
/*  598:     */   }
/*  599:     */   
/*  600:     */   protected static class Entry
/*  601:     */     implements Map.Entry
/*  602:     */   {
/*  603:     */     protected final int hash;
/*  604:     */     protected final Object key;
/*  605:     */     protected final Entry next;
/*  606:     */     protected volatile Object value;
/*  607:     */     
/*  608:     */     Entry(int hash, Object key, Object value, Entry next)
/*  609:     */     {
/*  610:1043 */       this.hash = hash;
/*  611:1044 */       this.key = key;
/*  612:1045 */       this.next = next;
/*  613:1046 */       this.value = value;
/*  614:     */     }
/*  615:     */     
/*  616:     */     public Object getKey()
/*  617:     */     {
/*  618:1052 */       return this.key;
/*  619:     */     }
/*  620:     */     
/*  621:     */     public Object getValue()
/*  622:     */     {
/*  623:1068 */       return this.value;
/*  624:     */     }
/*  625:     */     
/*  626:     */     public Object setValue(Object value)
/*  627:     */     {
/*  628:1094 */       if (value == null) {
/*  629:1095 */         throw new NullPointerException();
/*  630:     */       }
/*  631:1096 */       Object oldValue = this.value;
/*  632:1097 */       this.value = value;
/*  633:1098 */       return oldValue;
/*  634:     */     }
/*  635:     */     
/*  636:     */     public boolean equals(Object o)
/*  637:     */     {
/*  638:1102 */       if (!(o instanceof Map.Entry)) {
/*  639:1103 */         return false;
/*  640:     */       }
/*  641:1104 */       Map.Entry e = (Map.Entry)o;
/*  642:1105 */       return (this.key.equals(e.getKey())) && (this.value.equals(e.getValue()));
/*  643:     */     }
/*  644:     */     
/*  645:     */     public int hashCode()
/*  646:     */     {
/*  647:1109 */       return this.key.hashCode() ^ this.value.hashCode();
/*  648:     */     }
/*  649:     */     
/*  650:     */     public String toString()
/*  651:     */     {
/*  652:1113 */       return this.key + "=" + this.value;
/*  653:     */     }
/*  654:     */   }
/*  655:     */   
/*  656:     */   protected class HashIterator
/*  657:     */     implements Iterator, Enumeration
/*  658:     */   {
/*  659:     */     protected final ConcurrentReaderHashMap.Entry[] tab;
/*  660:     */     protected int index;
/*  661:1123 */     protected ConcurrentReaderHashMap.Entry entry = null;
/*  662:     */     protected Object currentKey;
/*  663:     */     protected Object currentValue;
/*  664:1129 */     protected ConcurrentReaderHashMap.Entry lastReturned = null;
/*  665:     */     
/*  666:     */     protected HashIterator()
/*  667:     */     {
/*  668:1132 */       this.tab = ConcurrentReaderHashMap.this.getTableForReading();
/*  669:1133 */       this.index = (this.tab.length - 1);
/*  670:     */     }
/*  671:     */     
/*  672:     */     public boolean hasMoreElements()
/*  673:     */     {
/*  674:1137 */       return hasNext();
/*  675:     */     }
/*  676:     */     
/*  677:     */     public Object nextElement()
/*  678:     */     {
/*  679:1141 */       return next();
/*  680:     */     }
/*  681:     */     
/*  682:     */     public boolean hasNext()
/*  683:     */     {
/*  684:     */       do
/*  685:     */       {
/*  686:1154 */         if (this.entry != null)
/*  687:     */         {
/*  688:1155 */           Object v = this.entry.value;
/*  689:1156 */           if (v != null)
/*  690:     */           {
/*  691:1157 */             this.currentKey = this.entry.key;
/*  692:1158 */             this.currentValue = v;
/*  693:1159 */             return true;
/*  694:     */           }
/*  695:1161 */           this.entry = this.entry.next;
/*  696:     */         }
/*  697:1164 */         while ((this.entry == null) && (this.index >= 0)) {
/*  698:1165 */           this.entry = this.tab[(this.index--)];
/*  699:     */         }
/*  700:1167 */       } while (this.entry != null);
/*  701:1168 */       this.currentKey = (this.currentValue = null);
/*  702:1169 */       return false;
/*  703:     */     }
/*  704:     */     
/*  705:     */     protected Object returnValueOfNext()
/*  706:     */     {
/*  707:1175 */       return this.entry;
/*  708:     */     }
/*  709:     */     
/*  710:     */     public Object next()
/*  711:     */     {
/*  712:1179 */       if ((this.currentKey == null) && (!hasNext())) {
/*  713:1180 */         throw new NoSuchElementException();
/*  714:     */       }
/*  715:1182 */       Object result = returnValueOfNext();
/*  716:1183 */       this.lastReturned = this.entry;
/*  717:1184 */       this.currentKey = (this.currentValue = null);
/*  718:1185 */       this.entry = this.entry.next;
/*  719:1186 */       return result;
/*  720:     */     }
/*  721:     */     
/*  722:     */     public void remove()
/*  723:     */     {
/*  724:1190 */       if (this.lastReturned == null) {
/*  725:1191 */         throw new IllegalStateException();
/*  726:     */       }
/*  727:1192 */       ConcurrentReaderHashMap.this.remove(this.lastReturned.key);
/*  728:1193 */       this.lastReturned = null;
/*  729:     */     }
/*  730:     */   }
/*  731:     */   
/*  732:     */   protected class KeyIterator
/*  733:     */     extends ConcurrentReaderHashMap.HashIterator
/*  734:     */   {
/*  735:     */     protected KeyIterator()
/*  736:     */     {
/*  737:1198 */       super();
/*  738:     */     }
/*  739:     */     
/*  740:     */     protected Object returnValueOfNext()
/*  741:     */     {
/*  742:1200 */       return this.currentKey;
/*  743:     */     }
/*  744:     */   }
/*  745:     */   
/*  746:     */   protected class ValueIterator
/*  747:     */     extends ConcurrentReaderHashMap.HashIterator
/*  748:     */   {
/*  749:     */     protected ValueIterator()
/*  750:     */     {
/*  751:1204 */       super();
/*  752:     */     }
/*  753:     */     
/*  754:     */     protected Object returnValueOfNext()
/*  755:     */     {
/*  756:1206 */       return this.currentValue;
/*  757:     */     }
/*  758:     */   }
/*  759:     */   
/*  760:     */   private synchronized void writeObject(ObjectOutputStream s)
/*  761:     */     throws IOException
/*  762:     */   {
/*  763:1226 */     s.defaultWriteObject();
/*  764:     */     
/*  765:     */ 
/*  766:1229 */     s.writeInt(this.table.length);
/*  767:     */     
/*  768:     */ 
/*  769:1232 */     s.writeInt(this.count);
/*  770:1235 */     for (int index = this.table.length - 1; index >= 0; index--)
/*  771:     */     {
/*  772:1236 */       Entry entry = this.table[index];
/*  773:1238 */       while (entry != null)
/*  774:     */       {
/*  775:1239 */         s.writeObject(entry.key);
/*  776:1240 */         s.writeObject(entry.value);
/*  777:1241 */         entry = entry.next;
/*  778:     */       }
/*  779:     */     }
/*  780:     */   }
/*  781:     */   
/*  782:     */   private synchronized void readObject(ObjectInputStream s)
/*  783:     */     throws IOException, ClassNotFoundException
/*  784:     */   {
/*  785:1253 */     s.defaultReadObject();
/*  786:     */     
/*  787:     */ 
/*  788:1256 */     int numBuckets = s.readInt();
/*  789:1257 */     this.table = new Entry[numBuckets];
/*  790:     */     
/*  791:     */ 
/*  792:1260 */     int size = s.readInt();
/*  793:1263 */     for (int i = 0; i < size; i++)
/*  794:     */     {
/*  795:1264 */       Object key = s.readObject();
/*  796:1265 */       Object value = s.readObject();
/*  797:1266 */       put(key, value);
/*  798:     */     }
/*  799:     */   }
/*  800:     */   
/*  801:     */   public synchronized int capacity()
/*  802:     */   {
/*  803:1275 */     return this.table.length;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public float loadFactor()
/*  807:     */   {
/*  808:1282 */     return this.loadFactor;
/*  809:     */   }
/*  810:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.ConcurrentReaderHashMap
 * JD-Core Version:    0.7.0.1
 */