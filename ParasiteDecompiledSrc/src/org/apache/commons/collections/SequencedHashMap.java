/*    1:     */ package org.apache.commons.collections;
/*    2:     */ 
/*    3:     */ import java.io.Externalizable;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.ObjectInput;
/*    6:     */ import java.io.ObjectOutput;
/*    7:     */ import java.util.AbstractCollection;
/*    8:     */ import java.util.AbstractSet;
/*    9:     */ import java.util.ArrayList;
/*   10:     */ import java.util.Collection;
/*   11:     */ import java.util.ConcurrentModificationException;
/*   12:     */ import java.util.HashMap;
/*   13:     */ import java.util.Iterator;
/*   14:     */ import java.util.List;
/*   15:     */ import java.util.Map;
/*   16:     */ import java.util.Map.Entry;
/*   17:     */ import java.util.NoSuchElementException;
/*   18:     */ import java.util.Set;
/*   19:     */ import org.apache.commons.collections.list.UnmodifiableList;
/*   20:     */ 
/*   21:     */ /**
/*   22:     */  * @deprecated
/*   23:     */  */
/*   24:     */ public class SequencedHashMap
/*   25:     */   implements Map, Cloneable, Externalizable
/*   26:     */ {
/*   27:     */   private Entry sentinel;
/*   28:     */   private HashMap entries;
/*   29:     */   
/*   30:     */   private static class Entry
/*   31:     */     implements Map.Entry, KeyValue
/*   32:     */   {
/*   33:     */     private final Object key;
/*   34:     */     private Object value;
/*   35:  84 */     Entry next = null;
/*   36:  85 */     Entry prev = null;
/*   37:     */     
/*   38:     */     public Entry(Object key, Object value)
/*   39:     */     {
/*   40:  88 */       this.key = key;
/*   41:  89 */       this.value = value;
/*   42:     */     }
/*   43:     */     
/*   44:     */     public Object getKey()
/*   45:     */     {
/*   46:  94 */       return this.key;
/*   47:     */     }
/*   48:     */     
/*   49:     */     public Object getValue()
/*   50:     */     {
/*   51:  99 */       return this.value;
/*   52:     */     }
/*   53:     */     
/*   54:     */     public Object setValue(Object value)
/*   55:     */     {
/*   56: 104 */       Object oldValue = this.value;
/*   57: 105 */       this.value = value;
/*   58: 106 */       return oldValue;
/*   59:     */     }
/*   60:     */     
/*   61:     */     public int hashCode()
/*   62:     */     {
/*   63: 111 */       return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/*   64:     */     }
/*   65:     */     
/*   66:     */     public boolean equals(Object obj)
/*   67:     */     {
/*   68: 115 */       if (obj == null) {
/*   69: 116 */         return false;
/*   70:     */       }
/*   71: 117 */       if (obj == this) {
/*   72: 118 */         return true;
/*   73:     */       }
/*   74: 119 */       if (!(obj instanceof Map.Entry)) {
/*   75: 120 */         return false;
/*   76:     */       }
/*   77: 122 */       Map.Entry other = (Map.Entry)obj;
/*   78:     */       
/*   79:     */ 
/*   80: 125 */       return (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey())) && (getValue() == null ? other.getValue() == null : getValue().equals(other.getValue()));
/*   81:     */     }
/*   82:     */     
/*   83:     */     public String toString()
/*   84:     */     {
/*   85: 130 */       return "[" + getKey() + "=" + getValue() + "]";
/*   86:     */     }
/*   87:     */   }
/*   88:     */   
/*   89:     */   private static final Entry createSentinel()
/*   90:     */   {
/*   91: 140 */     Entry s = new Entry(null, null);
/*   92: 141 */     s.prev = s;
/*   93: 142 */     s.next = s;
/*   94: 143 */     return s;
/*   95:     */   }
/*   96:     */   
/*   97: 162 */   private transient long modCount = 0L;
/*   98:     */   private static final int KEY = 0;
/*   99:     */   private static final int VALUE = 1;
/*  100:     */   private static final int ENTRY = 2;
/*  101:     */   private static final int REMOVED_MASK = -2147483648;
/*  102:     */   private static final long serialVersionUID = 3380552487888102930L;
/*  103:     */   
/*  104:     */   public SequencedHashMap()
/*  105:     */   {
/*  106: 169 */     this.sentinel = createSentinel();
/*  107: 170 */     this.entries = new HashMap();
/*  108:     */   }
/*  109:     */   
/*  110:     */   public SequencedHashMap(int initialSize)
/*  111:     */   {
/*  112: 182 */     this.sentinel = createSentinel();
/*  113: 183 */     this.entries = new HashMap(initialSize);
/*  114:     */   }
/*  115:     */   
/*  116:     */   public SequencedHashMap(int initialSize, float loadFactor)
/*  117:     */   {
/*  118: 197 */     this.sentinel = createSentinel();
/*  119: 198 */     this.entries = new HashMap(initialSize, loadFactor);
/*  120:     */   }
/*  121:     */   
/*  122:     */   public SequencedHashMap(Map m)
/*  123:     */   {
/*  124: 207 */     this();
/*  125: 208 */     putAll(m);
/*  126:     */   }
/*  127:     */   
/*  128:     */   private void removeEntry(Entry entry)
/*  129:     */   {
/*  130: 216 */     entry.next.prev = entry.prev;
/*  131: 217 */     entry.prev.next = entry.next;
/*  132:     */   }
/*  133:     */   
/*  134:     */   private void insertEntry(Entry entry)
/*  135:     */   {
/*  136: 225 */     entry.next = this.sentinel;
/*  137: 226 */     entry.prev = this.sentinel.prev;
/*  138: 227 */     this.sentinel.prev.next = entry;
/*  139: 228 */     this.sentinel.prev = entry;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public int size()
/*  143:     */   {
/*  144: 238 */     return this.entries.size();
/*  145:     */   }
/*  146:     */   
/*  147:     */   public boolean isEmpty()
/*  148:     */   {
/*  149: 247 */     return this.sentinel.next == this.sentinel;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public boolean containsKey(Object key)
/*  153:     */   {
/*  154: 255 */     return this.entries.containsKey(key);
/*  155:     */   }
/*  156:     */   
/*  157:     */   public boolean containsValue(Object value)
/*  158:     */   {
/*  159: 270 */     if (value == null) {
/*  160: 271 */       for (Entry pos = this.sentinel.next; pos != this.sentinel; pos = pos.next) {
/*  161: 272 */         if (pos.getValue() == null) {
/*  162: 273 */           return true;
/*  163:     */         }
/*  164:     */       }
/*  165:     */     } else {
/*  166: 276 */       for (Entry pos = this.sentinel.next; pos != this.sentinel; pos = pos.next) {
/*  167: 277 */         if (value.equals(pos.getValue())) {
/*  168: 278 */           return true;
/*  169:     */         }
/*  170:     */       }
/*  171:     */     }
/*  172: 281 */     return false;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public Object get(Object o)
/*  176:     */   {
/*  177: 289 */     Entry entry = (Entry)this.entries.get(o);
/*  178: 290 */     if (entry == null) {
/*  179: 291 */       return null;
/*  180:     */     }
/*  181: 293 */     return entry.getValue();
/*  182:     */   }
/*  183:     */   
/*  184:     */   public Map.Entry getFirst()
/*  185:     */   {
/*  186: 310 */     return isEmpty() ? null : this.sentinel.next;
/*  187:     */   }
/*  188:     */   
/*  189:     */   public Object getFirstKey()
/*  190:     */   {
/*  191: 330 */     return this.sentinel.next.getKey();
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Object getFirstValue()
/*  195:     */   {
/*  196: 350 */     return this.sentinel.next.getValue();
/*  197:     */   }
/*  198:     */   
/*  199:     */   public Map.Entry getLast()
/*  200:     */   {
/*  201: 377 */     return isEmpty() ? null : this.sentinel.prev;
/*  202:     */   }
/*  203:     */   
/*  204:     */   public Object getLastKey()
/*  205:     */   {
/*  206: 397 */     return this.sentinel.prev.getKey();
/*  207:     */   }
/*  208:     */   
/*  209:     */   public Object getLastValue()
/*  210:     */   {
/*  211: 417 */     return this.sentinel.prev.getValue();
/*  212:     */   }
/*  213:     */   
/*  214:     */   public Object put(Object key, Object value)
/*  215:     */   {
/*  216: 424 */     this.modCount += 1L;
/*  217:     */     
/*  218: 426 */     Object oldValue = null;
/*  219:     */     
/*  220:     */ 
/*  221: 429 */     Entry e = (Entry)this.entries.get(key);
/*  222: 432 */     if (e != null)
/*  223:     */     {
/*  224: 434 */       removeEntry(e);
/*  225:     */       
/*  226:     */ 
/*  227: 437 */       oldValue = e.setValue(value);
/*  228:     */     }
/*  229:     */     else
/*  230:     */     {
/*  231: 446 */       e = new Entry(key, value);
/*  232: 447 */       this.entries.put(key, e);
/*  233:     */     }
/*  234: 452 */     insertEntry(e);
/*  235:     */     
/*  236: 454 */     return oldValue;
/*  237:     */   }
/*  238:     */   
/*  239:     */   public Object remove(Object key)
/*  240:     */   {
/*  241: 461 */     Entry e = removeImpl(key);
/*  242: 462 */     return e == null ? null : e.getValue();
/*  243:     */   }
/*  244:     */   
/*  245:     */   private Entry removeImpl(Object key)
/*  246:     */   {
/*  247: 470 */     Entry e = (Entry)this.entries.remove(key);
/*  248: 471 */     if (e == null) {
/*  249: 472 */       return null;
/*  250:     */     }
/*  251: 473 */     this.modCount += 1L;
/*  252: 474 */     removeEntry(e);
/*  253: 475 */     return e;
/*  254:     */   }
/*  255:     */   
/*  256:     */   public void putAll(Map t)
/*  257:     */   {
/*  258: 489 */     Iterator iter = t.entrySet().iterator();
/*  259: 490 */     while (iter.hasNext())
/*  260:     */     {
/*  261: 491 */       Map.Entry entry = (Map.Entry)iter.next();
/*  262: 492 */       put(entry.getKey(), entry.getValue());
/*  263:     */     }
/*  264:     */   }
/*  265:     */   
/*  266:     */   public void clear()
/*  267:     */   {
/*  268: 500 */     this.modCount += 1L;
/*  269:     */     
/*  270:     */ 
/*  271: 503 */     this.entries.clear();
/*  272:     */     
/*  273:     */ 
/*  274: 506 */     this.sentinel.next = this.sentinel;
/*  275: 507 */     this.sentinel.prev = this.sentinel;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public boolean equals(Object obj)
/*  279:     */   {
/*  280: 514 */     if (obj == null) {
/*  281: 515 */       return false;
/*  282:     */     }
/*  283: 516 */     if (obj == this) {
/*  284: 517 */       return true;
/*  285:     */     }
/*  286: 519 */     if (!(obj instanceof Map)) {
/*  287: 520 */       return false;
/*  288:     */     }
/*  289: 522 */     return entrySet().equals(((Map)obj).entrySet());
/*  290:     */   }
/*  291:     */   
/*  292:     */   public int hashCode()
/*  293:     */   {
/*  294: 529 */     return entrySet().hashCode();
/*  295:     */   }
/*  296:     */   
/*  297:     */   public String toString()
/*  298:     */   {
/*  299: 540 */     StringBuffer buf = new StringBuffer();
/*  300: 541 */     buf.append('[');
/*  301: 542 */     for (Entry pos = this.sentinel.next; pos != this.sentinel; pos = pos.next)
/*  302:     */     {
/*  303: 543 */       buf.append(pos.getKey());
/*  304: 544 */       buf.append('=');
/*  305: 545 */       buf.append(pos.getValue());
/*  306: 546 */       if (pos.next != this.sentinel) {
/*  307: 547 */         buf.append(',');
/*  308:     */       }
/*  309:     */     }
/*  310: 550 */     buf.append(']');
/*  311:     */     
/*  312: 552 */     return buf.toString();
/*  313:     */   }
/*  314:     */   
/*  315:     */   public Set keySet()
/*  316:     */   {
/*  317: 559 */     new AbstractSet()
/*  318:     */     {
/*  319:     */       public Iterator iterator()
/*  320:     */       {
/*  321: 563 */         return new SequencedHashMap.OrderedIterator(SequencedHashMap.this, 0);
/*  322:     */       }
/*  323:     */       
/*  324:     */       public boolean remove(Object o)
/*  325:     */       {
/*  326: 566 */         SequencedHashMap.Entry e = SequencedHashMap.this.removeImpl(o);
/*  327: 567 */         return e != null;
/*  328:     */       }
/*  329:     */       
/*  330:     */       public void clear()
/*  331:     */       {
/*  332: 572 */         SequencedHashMap.this.clear();
/*  333:     */       }
/*  334:     */       
/*  335:     */       public int size()
/*  336:     */       {
/*  337: 575 */         return SequencedHashMap.this.size();
/*  338:     */       }
/*  339:     */       
/*  340:     */       public boolean isEmpty()
/*  341:     */       {
/*  342: 578 */         return SequencedHashMap.this.isEmpty();
/*  343:     */       }
/*  344:     */       
/*  345:     */       public boolean contains(Object o)
/*  346:     */       {
/*  347: 581 */         return SequencedHashMap.this.containsKey(o);
/*  348:     */       }
/*  349:     */     };
/*  350:     */   }
/*  351:     */   
/*  352:     */   public Collection values()
/*  353:     */   {
/*  354: 591 */     new AbstractCollection()
/*  355:     */     {
/*  356:     */       public Iterator iterator()
/*  357:     */       {
/*  358: 594 */         return new SequencedHashMap.OrderedIterator(SequencedHashMap.this, 1);
/*  359:     */       }
/*  360:     */       
/*  361:     */       public boolean remove(Object value)
/*  362:     */       {
/*  363: 600 */         if (value == null) {
/*  364: 601 */           for (SequencedHashMap.Entry pos = SequencedHashMap.this.sentinel.next; pos != SequencedHashMap.this.sentinel; pos = pos.next) {
/*  365: 602 */             if (pos.getValue() == null)
/*  366:     */             {
/*  367: 603 */               SequencedHashMap.this.removeImpl(pos.getKey());
/*  368: 604 */               return true;
/*  369:     */             }
/*  370:     */           }
/*  371:     */         } else {
/*  372: 608 */           for (SequencedHashMap.Entry pos = SequencedHashMap.this.sentinel.next; pos != SequencedHashMap.this.sentinel; pos = pos.next) {
/*  373: 609 */             if (value.equals(pos.getValue()))
/*  374:     */             {
/*  375: 610 */               SequencedHashMap.this.removeImpl(pos.getKey());
/*  376: 611 */               return true;
/*  377:     */             }
/*  378:     */           }
/*  379:     */         }
/*  380: 616 */         return false;
/*  381:     */       }
/*  382:     */       
/*  383:     */       public void clear()
/*  384:     */       {
/*  385: 621 */         SequencedHashMap.this.clear();
/*  386:     */       }
/*  387:     */       
/*  388:     */       public int size()
/*  389:     */       {
/*  390: 624 */         return SequencedHashMap.this.size();
/*  391:     */       }
/*  392:     */       
/*  393:     */       public boolean isEmpty()
/*  394:     */       {
/*  395: 627 */         return SequencedHashMap.this.isEmpty();
/*  396:     */       }
/*  397:     */       
/*  398:     */       public boolean contains(Object o)
/*  399:     */       {
/*  400: 630 */         return SequencedHashMap.this.containsValue(o);
/*  401:     */       }
/*  402:     */     };
/*  403:     */   }
/*  404:     */   
/*  405:     */   public Set entrySet()
/*  406:     */   {
/*  407: 639 */     new AbstractSet()
/*  408:     */     {
/*  409:     */       private SequencedHashMap.Entry findEntry(Object o)
/*  410:     */       {
/*  411: 642 */         if (o == null) {
/*  412: 643 */           return null;
/*  413:     */         }
/*  414: 644 */         if (!(o instanceof Map.Entry)) {
/*  415: 645 */           return null;
/*  416:     */         }
/*  417: 647 */         Map.Entry e = (Map.Entry)o;
/*  418: 648 */         SequencedHashMap.Entry entry = (SequencedHashMap.Entry)SequencedHashMap.this.entries.get(e.getKey());
/*  419: 649 */         if ((entry != null) && (entry.equals(e))) {
/*  420: 650 */           return entry;
/*  421:     */         }
/*  422: 652 */         return null;
/*  423:     */       }
/*  424:     */       
/*  425:     */       public Iterator iterator()
/*  426:     */       {
/*  427: 657 */         return new SequencedHashMap.OrderedIterator(SequencedHashMap.this, 2);
/*  428:     */       }
/*  429:     */       
/*  430:     */       public boolean remove(Object o)
/*  431:     */       {
/*  432: 660 */         SequencedHashMap.Entry e = findEntry(o);
/*  433: 661 */         if (e == null) {
/*  434: 662 */           return false;
/*  435:     */         }
/*  436: 664 */         return SequencedHashMap.this.removeImpl(e.getKey()) != null;
/*  437:     */       }
/*  438:     */       
/*  439:     */       public void clear()
/*  440:     */       {
/*  441: 669 */         SequencedHashMap.this.clear();
/*  442:     */       }
/*  443:     */       
/*  444:     */       public int size()
/*  445:     */       {
/*  446: 672 */         return SequencedHashMap.this.size();
/*  447:     */       }
/*  448:     */       
/*  449:     */       public boolean isEmpty()
/*  450:     */       {
/*  451: 675 */         return SequencedHashMap.this.isEmpty();
/*  452:     */       }
/*  453:     */       
/*  454:     */       public boolean contains(Object o)
/*  455:     */       {
/*  456: 678 */         return findEntry(o) != null;
/*  457:     */       }
/*  458:     */     };
/*  459:     */   }
/*  460:     */   
/*  461:     */   private class OrderedIterator
/*  462:     */     implements Iterator
/*  463:     */   {
/*  464:     */     private int returnType;
/*  465: 705 */     private SequencedHashMap.Entry pos = SequencedHashMap.this.sentinel;
/*  466: 712 */     private transient long expectedModCount = SequencedHashMap.this.modCount;
/*  467:     */     
/*  468:     */     public OrderedIterator(int returnType)
/*  469:     */     {
/*  470: 731 */       this.returnType = (returnType | 0x80000000);
/*  471:     */     }
/*  472:     */     
/*  473:     */     public boolean hasNext()
/*  474:     */     {
/*  475: 742 */       return this.pos.next != SequencedHashMap.this.sentinel;
/*  476:     */     }
/*  477:     */     
/*  478:     */     public Object next()
/*  479:     */     {
/*  480: 757 */       if (SequencedHashMap.this.modCount != this.expectedModCount) {
/*  481: 758 */         throw new ConcurrentModificationException();
/*  482:     */       }
/*  483: 760 */       if (this.pos.next == SequencedHashMap.this.sentinel) {
/*  484: 761 */         throw new NoSuchElementException();
/*  485:     */       }
/*  486: 765 */       this.returnType &= 0x7FFFFFFF;
/*  487:     */       
/*  488: 767 */       this.pos = this.pos.next;
/*  489: 768 */       switch (this.returnType)
/*  490:     */       {
/*  491:     */       case 0: 
/*  492: 770 */         return this.pos.getKey();
/*  493:     */       case 1: 
/*  494: 772 */         return this.pos.getValue();
/*  495:     */       case 2: 
/*  496: 774 */         return this.pos;
/*  497:     */       }
/*  498: 777 */       throw new Error("bad iterator type: " + this.returnType);
/*  499:     */     }
/*  500:     */     
/*  501:     */     public void remove()
/*  502:     */     {
/*  503: 794 */       if ((this.returnType & 0x80000000) != 0) {
/*  504: 795 */         throw new IllegalStateException("remove() must follow next()");
/*  505:     */       }
/*  506: 797 */       if (SequencedHashMap.this.modCount != this.expectedModCount) {
/*  507: 798 */         throw new ConcurrentModificationException();
/*  508:     */       }
/*  509: 801 */       SequencedHashMap.this.removeImpl(this.pos.getKey());
/*  510:     */       
/*  511:     */ 
/*  512: 804 */       this.expectedModCount += 1L;
/*  513:     */       
/*  514:     */ 
/*  515: 807 */       this.returnType |= 0x80000000;
/*  516:     */     }
/*  517:     */   }
/*  518:     */   
/*  519:     */   public Object clone()
/*  520:     */     throws CloneNotSupportedException
/*  521:     */   {
/*  522: 829 */     SequencedHashMap map = (SequencedHashMap)super.clone();
/*  523:     */     
/*  524:     */ 
/*  525: 832 */     map.sentinel = createSentinel();
/*  526:     */     
/*  527:     */ 
/*  528:     */ 
/*  529: 836 */     map.entries = new HashMap();
/*  530:     */     
/*  531:     */ 
/*  532: 839 */     map.putAll(this);
/*  533:     */     
/*  534:     */ 
/*  535:     */ 
/*  536:     */ 
/*  537:     */ 
/*  538:     */ 
/*  539:     */ 
/*  540:     */ 
/*  541:     */ 
/*  542: 849 */     return map;
/*  543:     */   }
/*  544:     */   
/*  545:     */   private Map.Entry getEntry(int index)
/*  546:     */   {
/*  547: 859 */     Entry pos = this.sentinel;
/*  548: 861 */     if (index < 0) {
/*  549: 862 */       throw new ArrayIndexOutOfBoundsException(index + " < 0");
/*  550:     */     }
/*  551: 866 */     int i = -1;
/*  552: 867 */     while ((i < index - 1) && (pos.next != this.sentinel))
/*  553:     */     {
/*  554: 868 */       i++;
/*  555: 869 */       pos = pos.next;
/*  556:     */     }
/*  557: 874 */     if (pos.next == this.sentinel) {
/*  558: 875 */       throw new ArrayIndexOutOfBoundsException(index + " >= " + (i + 1));
/*  559:     */     }
/*  560: 878 */     return pos.next;
/*  561:     */   }
/*  562:     */   
/*  563:     */   public Object get(int index)
/*  564:     */   {
/*  565: 890 */     return getEntry(index).getKey();
/*  566:     */   }
/*  567:     */   
/*  568:     */   public Object getValue(int index)
/*  569:     */   {
/*  570: 902 */     return getEntry(index).getValue();
/*  571:     */   }
/*  572:     */   
/*  573:     */   public int indexOf(Object key)
/*  574:     */   {
/*  575: 912 */     Entry e = (Entry)this.entries.get(key);
/*  576: 913 */     if (e == null) {
/*  577: 914 */       return -1;
/*  578:     */     }
/*  579: 916 */     int pos = 0;
/*  580: 917 */     while (e.prev != this.sentinel)
/*  581:     */     {
/*  582: 918 */       pos++;
/*  583: 919 */       e = e.prev;
/*  584:     */     }
/*  585: 921 */     return pos;
/*  586:     */   }
/*  587:     */   
/*  588:     */   public Iterator iterator()
/*  589:     */   {
/*  590: 930 */     return keySet().iterator();
/*  591:     */   }
/*  592:     */   
/*  593:     */   public int lastIndexOf(Object key)
/*  594:     */   {
/*  595: 941 */     return indexOf(key);
/*  596:     */   }
/*  597:     */   
/*  598:     */   public List sequence()
/*  599:     */   {
/*  600: 959 */     List l = new ArrayList(size());
/*  601: 960 */     Iterator iter = keySet().iterator();
/*  602: 961 */     while (iter.hasNext()) {
/*  603: 962 */       l.add(iter.next());
/*  604:     */     }
/*  605: 965 */     return UnmodifiableList.decorate(l);
/*  606:     */   }
/*  607:     */   
/*  608:     */   public Object remove(int index)
/*  609:     */   {
/*  610: 979 */     return remove(get(index));
/*  611:     */   }
/*  612:     */   
/*  613:     */   public void readExternal(ObjectInput in)
/*  614:     */     throws IOException, ClassNotFoundException
/*  615:     */   {
/*  616: 992 */     int size = in.readInt();
/*  617: 993 */     for (int i = 0; i < size; i++)
/*  618:     */     {
/*  619: 994 */       Object key = in.readObject();
/*  620: 995 */       Object value = in.readObject();
/*  621: 996 */       put(key, value);
/*  622:     */     }
/*  623:     */   }
/*  624:     */   
/*  625:     */   public void writeExternal(ObjectOutput out)
/*  626:     */     throws IOException
/*  627:     */   {
/*  628:1007 */     out.writeInt(size());
/*  629:1008 */     for (Entry pos = this.sentinel.next; pos != this.sentinel; pos = pos.next)
/*  630:     */     {
/*  631:1009 */       out.writeObject(pos.getKey());
/*  632:1010 */       out.writeObject(pos.getValue());
/*  633:     */     }
/*  634:     */   }
/*  635:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.SequencedHashMap
 * JD-Core Version:    0.7.0.1
 */