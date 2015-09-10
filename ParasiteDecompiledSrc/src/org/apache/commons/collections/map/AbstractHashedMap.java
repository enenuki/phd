/*    1:     */ package org.apache.commons.collections.map;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.ObjectOutputStream;
/*    6:     */ import java.util.AbstractCollection;
/*    7:     */ import java.util.AbstractMap;
/*    8:     */ import java.util.AbstractSet;
/*    9:     */ import java.util.Collection;
/*   10:     */ import java.util.ConcurrentModificationException;
/*   11:     */ import java.util.Iterator;
/*   12:     */ import java.util.Map;
/*   13:     */ import java.util.Map.Entry;
/*   14:     */ import java.util.NoSuchElementException;
/*   15:     */ import java.util.Set;
/*   16:     */ import org.apache.commons.collections.IterableMap;
/*   17:     */ import org.apache.commons.collections.KeyValue;
/*   18:     */ import org.apache.commons.collections.MapIterator;
/*   19:     */ import org.apache.commons.collections.iterators.EmptyIterator;
/*   20:     */ import org.apache.commons.collections.iterators.EmptyMapIterator;
/*   21:     */ 
/*   22:     */ public class AbstractHashedMap
/*   23:     */   extends AbstractMap
/*   24:     */   implements IterableMap
/*   25:     */ {
/*   26:     */   protected static final String NO_NEXT_ENTRY = "No next() entry in the iteration";
/*   27:     */   protected static final String NO_PREVIOUS_ENTRY = "No previous() entry in the iteration";
/*   28:     */   protected static final String REMOVE_INVALID = "remove() can only be called once after next()";
/*   29:     */   protected static final String GETKEY_INVALID = "getKey() can only be called after next() and before remove()";
/*   30:     */   protected static final String GETVALUE_INVALID = "getValue() can only be called after next() and before remove()";
/*   31:     */   protected static final String SETVALUE_INVALID = "setValue() can only be called after next() and before remove()";
/*   32:     */   protected static final int DEFAULT_CAPACITY = 16;
/*   33:     */   protected static final int DEFAULT_THRESHOLD = 12;
/*   34:     */   protected static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*   35:     */   protected static final int MAXIMUM_CAPACITY = 1073741824;
/*   36:  80 */   protected static final Object NULL = new Object();
/*   37:     */   protected transient float loadFactor;
/*   38:     */   protected transient int size;
/*   39:     */   protected transient HashEntry[] data;
/*   40:     */   protected transient int threshold;
/*   41:     */   protected transient int modCount;
/*   42:     */   protected transient EntrySet entrySet;
/*   43:     */   protected transient KeySet keySet;
/*   44:     */   protected transient Values values;
/*   45:     */   
/*   46:     */   protected AbstractHashedMap() {}
/*   47:     */   
/*   48:     */   protected AbstractHashedMap(int initialCapacity, float loadFactor, int threshold)
/*   49:     */   {
/*   50: 115 */     this.loadFactor = loadFactor;
/*   51: 116 */     this.data = new HashEntry[initialCapacity];
/*   52: 117 */     this.threshold = threshold;
/*   53: 118 */     init();
/*   54:     */   }
/*   55:     */   
/*   56:     */   protected AbstractHashedMap(int initialCapacity)
/*   57:     */   {
/*   58: 129 */     this(initialCapacity, 0.75F);
/*   59:     */   }
/*   60:     */   
/*   61:     */   protected AbstractHashedMap(int initialCapacity, float loadFactor)
/*   62:     */   {
/*   63: 143 */     if (initialCapacity < 1) {
/*   64: 144 */       throw new IllegalArgumentException("Initial capacity must be greater than 0");
/*   65:     */     }
/*   66: 146 */     if ((loadFactor <= 0.0F) || (Float.isNaN(loadFactor))) {
/*   67: 147 */       throw new IllegalArgumentException("Load factor must be greater than 0");
/*   68:     */     }
/*   69: 149 */     this.loadFactor = loadFactor;
/*   70: 150 */     initialCapacity = calculateNewCapacity(initialCapacity);
/*   71: 151 */     this.threshold = calculateThreshold(initialCapacity, loadFactor);
/*   72: 152 */     this.data = new HashEntry[initialCapacity];
/*   73: 153 */     init();
/*   74:     */   }
/*   75:     */   
/*   76:     */   protected AbstractHashedMap(Map map)
/*   77:     */   {
/*   78: 163 */     this(Math.max(2 * map.size(), 16), 0.75F);
/*   79: 164 */     putAll(map);
/*   80:     */   }
/*   81:     */   
/*   82:     */   protected void init() {}
/*   83:     */   
/*   84:     */   public Object get(Object key)
/*   85:     */   {
/*   86: 181 */     key = convertKey(key);
/*   87: 182 */     int hashCode = hash(key);
/*   88: 183 */     HashEntry entry = this.data[hashIndex(hashCode, this.data.length)];
/*   89: 184 */     while (entry != null)
/*   90:     */     {
/*   91: 185 */       if ((entry.hashCode == hashCode) && (isEqualKey(key, entry.key))) {
/*   92: 186 */         return entry.getValue();
/*   93:     */       }
/*   94: 188 */       entry = entry.next;
/*   95:     */     }
/*   96: 190 */     return null;
/*   97:     */   }
/*   98:     */   
/*   99:     */   public int size()
/*  100:     */   {
/*  101: 199 */     return this.size;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public boolean isEmpty()
/*  105:     */   {
/*  106: 208 */     return this.size == 0;
/*  107:     */   }
/*  108:     */   
/*  109:     */   public boolean containsKey(Object key)
/*  110:     */   {
/*  111: 219 */     key = convertKey(key);
/*  112: 220 */     int hashCode = hash(key);
/*  113: 221 */     HashEntry entry = this.data[hashIndex(hashCode, this.data.length)];
/*  114: 222 */     while (entry != null)
/*  115:     */     {
/*  116: 223 */       if ((entry.hashCode == hashCode) && (isEqualKey(key, entry.key))) {
/*  117: 224 */         return true;
/*  118:     */       }
/*  119: 226 */       entry = entry.next;
/*  120:     */     }
/*  121: 228 */     return false;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public boolean containsValue(Object value)
/*  125:     */   {
/*  126: 238 */     if (value == null)
/*  127:     */     {
/*  128: 239 */       int i = 0;
/*  129: 239 */       for (int isize = this.data.length; i < isize; i++)
/*  130:     */       {
/*  131: 240 */         HashEntry entry = this.data[i];
/*  132: 241 */         while (entry != null)
/*  133:     */         {
/*  134: 242 */           if (entry.getValue() == null) {
/*  135: 243 */             return true;
/*  136:     */           }
/*  137: 245 */           entry = entry.next;
/*  138:     */         }
/*  139:     */       }
/*  140:     */     }
/*  141:     */     else
/*  142:     */     {
/*  143: 249 */       int i = 0;
/*  144: 249 */       for (int isize = this.data.length; i < isize; i++)
/*  145:     */       {
/*  146: 250 */         HashEntry entry = this.data[i];
/*  147: 251 */         while (entry != null)
/*  148:     */         {
/*  149: 252 */           if (isEqualValue(value, entry.getValue())) {
/*  150: 253 */             return true;
/*  151:     */           }
/*  152: 255 */           entry = entry.next;
/*  153:     */         }
/*  154:     */       }
/*  155:     */     }
/*  156: 259 */     return false;
/*  157:     */   }
/*  158:     */   
/*  159:     */   public Object put(Object key, Object value)
/*  160:     */   {
/*  161: 271 */     key = convertKey(key);
/*  162: 272 */     int hashCode = hash(key);
/*  163: 273 */     int index = hashIndex(hashCode, this.data.length);
/*  164: 274 */     HashEntry entry = this.data[index];
/*  165: 275 */     while (entry != null)
/*  166:     */     {
/*  167: 276 */       if ((entry.hashCode == hashCode) && (isEqualKey(key, entry.key)))
/*  168:     */       {
/*  169: 277 */         Object oldValue = entry.getValue();
/*  170: 278 */         updateEntry(entry, value);
/*  171: 279 */         return oldValue;
/*  172:     */       }
/*  173: 281 */       entry = entry.next;
/*  174:     */     }
/*  175: 284 */     addMapping(index, hashCode, key, value);
/*  176: 285 */     return null;
/*  177:     */   }
/*  178:     */   
/*  179:     */   public void putAll(Map map)
/*  180:     */   {
/*  181: 298 */     int mapSize = map.size();
/*  182: 299 */     if (mapSize == 0) {
/*  183: 300 */       return;
/*  184:     */     }
/*  185: 302 */     int newSize = (int)((this.size + mapSize) / this.loadFactor + 1.0F);
/*  186: 303 */     ensureCapacity(calculateNewCapacity(newSize));
/*  187: 304 */     for (Iterator it = map.entrySet().iterator(); it.hasNext();)
/*  188:     */     {
/*  189: 305 */       Map.Entry entry = (Map.Entry)it.next();
/*  190: 306 */       put(entry.getKey(), entry.getValue());
/*  191:     */     }
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Object remove(Object key)
/*  195:     */   {
/*  196: 317 */     key = convertKey(key);
/*  197: 318 */     int hashCode = hash(key);
/*  198: 319 */     int index = hashIndex(hashCode, this.data.length);
/*  199: 320 */     HashEntry entry = this.data[index];
/*  200: 321 */     HashEntry previous = null;
/*  201: 322 */     while (entry != null)
/*  202:     */     {
/*  203: 323 */       if ((entry.hashCode == hashCode) && (isEqualKey(key, entry.key)))
/*  204:     */       {
/*  205: 324 */         Object oldValue = entry.getValue();
/*  206: 325 */         removeMapping(entry, index, previous);
/*  207: 326 */         return oldValue;
/*  208:     */       }
/*  209: 328 */       previous = entry;
/*  210: 329 */       entry = entry.next;
/*  211:     */     }
/*  212: 331 */     return null;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void clear()
/*  216:     */   {
/*  217: 339 */     this.modCount += 1;
/*  218: 340 */     HashEntry[] data = this.data;
/*  219: 341 */     for (int i = data.length - 1; i >= 0; i--) {
/*  220: 342 */       data[i] = null;
/*  221:     */     }
/*  222: 344 */     this.size = 0;
/*  223:     */   }
/*  224:     */   
/*  225:     */   protected Object convertKey(Object key)
/*  226:     */   {
/*  227: 360 */     return key == null ? NULL : key;
/*  228:     */   }
/*  229:     */   
/*  230:     */   protected int hash(Object key)
/*  231:     */   {
/*  232: 373 */     int h = key.hashCode();
/*  233: 374 */     h += (h << 9 ^ 0xFFFFFFFF);
/*  234: 375 */     h ^= h >>> 14;
/*  235: 376 */     h += (h << 4);
/*  236: 377 */     h ^= h >>> 10;
/*  237: 378 */     return h;
/*  238:     */   }
/*  239:     */   
/*  240:     */   protected boolean isEqualKey(Object key1, Object key2)
/*  241:     */   {
/*  242: 391 */     return (key1 == key2) || (key1.equals(key2));
/*  243:     */   }
/*  244:     */   
/*  245:     */   protected boolean isEqualValue(Object value1, Object value2)
/*  246:     */   {
/*  247: 404 */     return (value1 == value2) || (value1.equals(value2));
/*  248:     */   }
/*  249:     */   
/*  250:     */   protected int hashIndex(int hashCode, int dataSize)
/*  251:     */   {
/*  252: 417 */     return hashCode & dataSize - 1;
/*  253:     */   }
/*  254:     */   
/*  255:     */   protected HashEntry getEntry(Object key)
/*  256:     */   {
/*  257: 432 */     key = convertKey(key);
/*  258: 433 */     int hashCode = hash(key);
/*  259: 434 */     HashEntry entry = this.data[hashIndex(hashCode, this.data.length)];
/*  260: 435 */     while (entry != null)
/*  261:     */     {
/*  262: 436 */       if ((entry.hashCode == hashCode) && (isEqualKey(key, entry.key))) {
/*  263: 437 */         return entry;
/*  264:     */       }
/*  265: 439 */       entry = entry.next;
/*  266:     */     }
/*  267: 441 */     return null;
/*  268:     */   }
/*  269:     */   
/*  270:     */   protected void updateEntry(HashEntry entry, Object newValue)
/*  271:     */   {
/*  272: 455 */     entry.setValue(newValue);
/*  273:     */   }
/*  274:     */   
/*  275:     */   protected void reuseEntry(HashEntry entry, int hashIndex, int hashCode, Object key, Object value)
/*  276:     */   {
/*  277: 471 */     entry.next = this.data[hashIndex];
/*  278: 472 */     entry.hashCode = hashCode;
/*  279: 473 */     entry.key = key;
/*  280: 474 */     entry.value = value;
/*  281:     */   }
/*  282:     */   
/*  283:     */   protected void addMapping(int hashIndex, int hashCode, Object key, Object value)
/*  284:     */   {
/*  285: 492 */     this.modCount += 1;
/*  286: 493 */     HashEntry entry = createEntry(this.data[hashIndex], hashCode, key, value);
/*  287: 494 */     addEntry(entry, hashIndex);
/*  288: 495 */     this.size += 1;
/*  289: 496 */     checkCapacity();
/*  290:     */   }
/*  291:     */   
/*  292:     */   protected HashEntry createEntry(HashEntry next, int hashCode, Object key, Object value)
/*  293:     */   {
/*  294: 513 */     return new HashEntry(next, hashCode, key, value);
/*  295:     */   }
/*  296:     */   
/*  297:     */   protected void addEntry(HashEntry entry, int hashIndex)
/*  298:     */   {
/*  299: 526 */     this.data[hashIndex] = entry;
/*  300:     */   }
/*  301:     */   
/*  302:     */   protected void removeMapping(HashEntry entry, int hashIndex, HashEntry previous)
/*  303:     */   {
/*  304: 542 */     this.modCount += 1;
/*  305: 543 */     removeEntry(entry, hashIndex, previous);
/*  306: 544 */     this.size -= 1;
/*  307: 545 */     destroyEntry(entry);
/*  308:     */   }
/*  309:     */   
/*  310:     */   protected void removeEntry(HashEntry entry, int hashIndex, HashEntry previous)
/*  311:     */   {
/*  312: 560 */     if (previous == null) {
/*  313: 561 */       this.data[hashIndex] = entry.next;
/*  314:     */     } else {
/*  315: 563 */       previous.next = entry.next;
/*  316:     */     }
/*  317:     */   }
/*  318:     */   
/*  319:     */   protected void destroyEntry(HashEntry entry)
/*  320:     */   {
/*  321: 576 */     entry.next = null;
/*  322: 577 */     entry.key = null;
/*  323: 578 */     entry.value = null;
/*  324:     */   }
/*  325:     */   
/*  326:     */   protected void checkCapacity()
/*  327:     */   {
/*  328: 588 */     if (this.size >= this.threshold)
/*  329:     */     {
/*  330: 589 */       int newCapacity = this.data.length * 2;
/*  331: 590 */       if (newCapacity <= 1073741824) {
/*  332: 591 */         ensureCapacity(newCapacity);
/*  333:     */       }
/*  334:     */     }
/*  335:     */   }
/*  336:     */   
/*  337:     */   protected void ensureCapacity(int newCapacity)
/*  338:     */   {
/*  339: 602 */     int oldCapacity = this.data.length;
/*  340: 603 */     if (newCapacity <= oldCapacity) {
/*  341: 604 */       return;
/*  342:     */     }
/*  343: 606 */     if (this.size == 0)
/*  344:     */     {
/*  345: 607 */       this.threshold = calculateThreshold(newCapacity, this.loadFactor);
/*  346: 608 */       this.data = new HashEntry[newCapacity];
/*  347:     */     }
/*  348:     */     else
/*  349:     */     {
/*  350: 610 */       HashEntry[] oldEntries = this.data;
/*  351: 611 */       HashEntry[] newEntries = new HashEntry[newCapacity];
/*  352:     */       
/*  353: 613 */       this.modCount += 1;
/*  354: 614 */       for (int i = oldCapacity - 1; i >= 0; i--)
/*  355:     */       {
/*  356: 615 */         HashEntry entry = oldEntries[i];
/*  357: 616 */         if (entry != null)
/*  358:     */         {
/*  359: 617 */           oldEntries[i] = null;
/*  360:     */           do
/*  361:     */           {
/*  362: 619 */             HashEntry next = entry.next;
/*  363: 620 */             int index = hashIndex(entry.hashCode, newCapacity);
/*  364: 621 */             entry.next = newEntries[index];
/*  365: 622 */             newEntries[index] = entry;
/*  366: 623 */             entry = next;
/*  367: 624 */           } while (entry != null);
/*  368:     */         }
/*  369:     */       }
/*  370: 627 */       this.threshold = calculateThreshold(newCapacity, this.loadFactor);
/*  371: 628 */       this.data = newEntries;
/*  372:     */     }
/*  373:     */   }
/*  374:     */   
/*  375:     */   protected int calculateNewCapacity(int proposedCapacity)
/*  376:     */   {
/*  377: 640 */     int newCapacity = 1;
/*  378: 641 */     if (proposedCapacity > 1073741824)
/*  379:     */     {
/*  380: 642 */       newCapacity = 1073741824;
/*  381:     */     }
/*  382:     */     else
/*  383:     */     {
/*  384: 644 */       while (newCapacity < proposedCapacity) {
/*  385: 645 */         newCapacity <<= 1;
/*  386:     */       }
/*  387: 647 */       if (newCapacity > 1073741824) {
/*  388: 648 */         newCapacity = 1073741824;
/*  389:     */       }
/*  390:     */     }
/*  391: 651 */     return newCapacity;
/*  392:     */   }
/*  393:     */   
/*  394:     */   protected int calculateThreshold(int newCapacity, float factor)
/*  395:     */   {
/*  396: 663 */     return (int)(newCapacity * factor);
/*  397:     */   }
/*  398:     */   
/*  399:     */   protected HashEntry entryNext(HashEntry entry)
/*  400:     */   {
/*  401: 677 */     return entry.next;
/*  402:     */   }
/*  403:     */   
/*  404:     */   protected int entryHashCode(HashEntry entry)
/*  405:     */   {
/*  406: 690 */     return entry.hashCode;
/*  407:     */   }
/*  408:     */   
/*  409:     */   protected Object entryKey(HashEntry entry)
/*  410:     */   {
/*  411: 703 */     return entry.key;
/*  412:     */   }
/*  413:     */   
/*  414:     */   protected Object entryValue(HashEntry entry)
/*  415:     */   {
/*  416: 716 */     return entry.value;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public MapIterator mapIterator()
/*  420:     */   {
/*  421: 732 */     if (this.size == 0) {
/*  422: 733 */       return EmptyMapIterator.INSTANCE;
/*  423:     */     }
/*  424: 735 */     return new HashMapIterator(this);
/*  425:     */   }
/*  426:     */   
/*  427:     */   protected static class HashMapIterator
/*  428:     */     extends AbstractHashedMap.HashIterator
/*  429:     */     implements MapIterator
/*  430:     */   {
/*  431:     */     protected HashMapIterator(AbstractHashedMap parent)
/*  432:     */     {
/*  433: 744 */       super();
/*  434:     */     }
/*  435:     */     
/*  436:     */     public Object next()
/*  437:     */     {
/*  438: 748 */       return super.nextEntry().getKey();
/*  439:     */     }
/*  440:     */     
/*  441:     */     public Object getKey()
/*  442:     */     {
/*  443: 752 */       AbstractHashedMap.HashEntry current = currentEntry();
/*  444: 753 */       if (current == null) {
/*  445: 754 */         throw new IllegalStateException("getKey() can only be called after next() and before remove()");
/*  446:     */       }
/*  447: 756 */       return current.getKey();
/*  448:     */     }
/*  449:     */     
/*  450:     */     public Object getValue()
/*  451:     */     {
/*  452: 760 */       AbstractHashedMap.HashEntry current = currentEntry();
/*  453: 761 */       if (current == null) {
/*  454: 762 */         throw new IllegalStateException("getValue() can only be called after next() and before remove()");
/*  455:     */       }
/*  456: 764 */       return current.getValue();
/*  457:     */     }
/*  458:     */     
/*  459:     */     public Object setValue(Object value)
/*  460:     */     {
/*  461: 768 */       AbstractHashedMap.HashEntry current = currentEntry();
/*  462: 769 */       if (current == null) {
/*  463: 770 */         throw new IllegalStateException("setValue() can only be called after next() and before remove()");
/*  464:     */       }
/*  465: 772 */       return current.setValue(value);
/*  466:     */     }
/*  467:     */   }
/*  468:     */   
/*  469:     */   public Set entrySet()
/*  470:     */   {
/*  471: 785 */     if (this.entrySet == null) {
/*  472: 786 */       this.entrySet = new EntrySet(this);
/*  473:     */     }
/*  474: 788 */     return this.entrySet;
/*  475:     */   }
/*  476:     */   
/*  477:     */   protected Iterator createEntrySetIterator()
/*  478:     */   {
/*  479: 798 */     if (size() == 0) {
/*  480: 799 */       return EmptyIterator.INSTANCE;
/*  481:     */     }
/*  482: 801 */     return new EntrySetIterator(this);
/*  483:     */   }
/*  484:     */   
/*  485:     */   protected static class EntrySet
/*  486:     */     extends AbstractSet
/*  487:     */   {
/*  488:     */     protected final AbstractHashedMap parent;
/*  489:     */     
/*  490:     */     protected EntrySet(AbstractHashedMap parent)
/*  491:     */     {
/*  492: 813 */       this.parent = parent;
/*  493:     */     }
/*  494:     */     
/*  495:     */     public int size()
/*  496:     */     {
/*  497: 817 */       return this.parent.size();
/*  498:     */     }
/*  499:     */     
/*  500:     */     public void clear()
/*  501:     */     {
/*  502: 821 */       this.parent.clear();
/*  503:     */     }
/*  504:     */     
/*  505:     */     public boolean contains(Object entry)
/*  506:     */     {
/*  507: 825 */       if ((entry instanceof Map.Entry))
/*  508:     */       {
/*  509: 826 */         Map.Entry e = (Map.Entry)entry;
/*  510: 827 */         Map.Entry match = this.parent.getEntry(e.getKey());
/*  511: 828 */         return (match != null) && (match.equals(e));
/*  512:     */       }
/*  513: 830 */       return false;
/*  514:     */     }
/*  515:     */     
/*  516:     */     public boolean remove(Object obj)
/*  517:     */     {
/*  518: 834 */       if (!(obj instanceof Map.Entry)) {
/*  519: 835 */         return false;
/*  520:     */       }
/*  521: 837 */       if (!contains(obj)) {
/*  522: 838 */         return false;
/*  523:     */       }
/*  524: 840 */       Map.Entry entry = (Map.Entry)obj;
/*  525: 841 */       Object key = entry.getKey();
/*  526: 842 */       this.parent.remove(key);
/*  527: 843 */       return true;
/*  528:     */     }
/*  529:     */     
/*  530:     */     public Iterator iterator()
/*  531:     */     {
/*  532: 847 */       return this.parent.createEntrySetIterator();
/*  533:     */     }
/*  534:     */   }
/*  535:     */   
/*  536:     */   protected static class EntrySetIterator
/*  537:     */     extends AbstractHashedMap.HashIterator
/*  538:     */   {
/*  539:     */     protected EntrySetIterator(AbstractHashedMap parent)
/*  540:     */     {
/*  541: 857 */       super();
/*  542:     */     }
/*  543:     */     
/*  544:     */     public Object next()
/*  545:     */     {
/*  546: 861 */       return super.nextEntry();
/*  547:     */     }
/*  548:     */   }
/*  549:     */   
/*  550:     */   public Set keySet()
/*  551:     */   {
/*  552: 874 */     if (this.keySet == null) {
/*  553: 875 */       this.keySet = new KeySet(this);
/*  554:     */     }
/*  555: 877 */     return this.keySet;
/*  556:     */   }
/*  557:     */   
/*  558:     */   protected Iterator createKeySetIterator()
/*  559:     */   {
/*  560: 887 */     if (size() == 0) {
/*  561: 888 */       return EmptyIterator.INSTANCE;
/*  562:     */     }
/*  563: 890 */     return new KeySetIterator(this);
/*  564:     */   }
/*  565:     */   
/*  566:     */   protected static class KeySet
/*  567:     */     extends AbstractSet
/*  568:     */   {
/*  569:     */     protected final AbstractHashedMap parent;
/*  570:     */     
/*  571:     */     protected KeySet(AbstractHashedMap parent)
/*  572:     */     {
/*  573: 902 */       this.parent = parent;
/*  574:     */     }
/*  575:     */     
/*  576:     */     public int size()
/*  577:     */     {
/*  578: 906 */       return this.parent.size();
/*  579:     */     }
/*  580:     */     
/*  581:     */     public void clear()
/*  582:     */     {
/*  583: 910 */       this.parent.clear();
/*  584:     */     }
/*  585:     */     
/*  586:     */     public boolean contains(Object key)
/*  587:     */     {
/*  588: 914 */       return this.parent.containsKey(key);
/*  589:     */     }
/*  590:     */     
/*  591:     */     public boolean remove(Object key)
/*  592:     */     {
/*  593: 918 */       boolean result = this.parent.containsKey(key);
/*  594: 919 */       this.parent.remove(key);
/*  595: 920 */       return result;
/*  596:     */     }
/*  597:     */     
/*  598:     */     public Iterator iterator()
/*  599:     */     {
/*  600: 924 */       return this.parent.createKeySetIterator();
/*  601:     */     }
/*  602:     */   }
/*  603:     */   
/*  604:     */   protected static class KeySetIterator
/*  605:     */     extends AbstractHashedMap.EntrySetIterator
/*  606:     */   {
/*  607:     */     protected KeySetIterator(AbstractHashedMap parent)
/*  608:     */     {
/*  609: 934 */       super();
/*  610:     */     }
/*  611:     */     
/*  612:     */     public Object next()
/*  613:     */     {
/*  614: 938 */       return super.nextEntry().getKey();
/*  615:     */     }
/*  616:     */   }
/*  617:     */   
/*  618:     */   public Collection values()
/*  619:     */   {
/*  620: 951 */     if (this.values == null) {
/*  621: 952 */       this.values = new Values(this);
/*  622:     */     }
/*  623: 954 */     return this.values;
/*  624:     */   }
/*  625:     */   
/*  626:     */   protected Iterator createValuesIterator()
/*  627:     */   {
/*  628: 964 */     if (size() == 0) {
/*  629: 965 */       return EmptyIterator.INSTANCE;
/*  630:     */     }
/*  631: 967 */     return new ValuesIterator(this);
/*  632:     */   }
/*  633:     */   
/*  634:     */   protected static class Values
/*  635:     */     extends AbstractCollection
/*  636:     */   {
/*  637:     */     protected final AbstractHashedMap parent;
/*  638:     */     
/*  639:     */     protected Values(AbstractHashedMap parent)
/*  640:     */     {
/*  641: 979 */       this.parent = parent;
/*  642:     */     }
/*  643:     */     
/*  644:     */     public int size()
/*  645:     */     {
/*  646: 983 */       return this.parent.size();
/*  647:     */     }
/*  648:     */     
/*  649:     */     public void clear()
/*  650:     */     {
/*  651: 987 */       this.parent.clear();
/*  652:     */     }
/*  653:     */     
/*  654:     */     public boolean contains(Object value)
/*  655:     */     {
/*  656: 991 */       return this.parent.containsValue(value);
/*  657:     */     }
/*  658:     */     
/*  659:     */     public Iterator iterator()
/*  660:     */     {
/*  661: 995 */       return this.parent.createValuesIterator();
/*  662:     */     }
/*  663:     */   }
/*  664:     */   
/*  665:     */   protected static class ValuesIterator
/*  666:     */     extends AbstractHashedMap.HashIterator
/*  667:     */   {
/*  668:     */     protected ValuesIterator(AbstractHashedMap parent)
/*  669:     */     {
/*  670:1005 */       super();
/*  671:     */     }
/*  672:     */     
/*  673:     */     public Object next()
/*  674:     */     {
/*  675:1009 */       return super.nextEntry().getValue();
/*  676:     */     }
/*  677:     */   }
/*  678:     */   
/*  679:     */   protected static class HashEntry
/*  680:     */     implements Map.Entry, KeyValue
/*  681:     */   {
/*  682:     */     protected HashEntry next;
/*  683:     */     protected int hashCode;
/*  684:     */     protected Object key;
/*  685:     */     protected Object value;
/*  686:     */     
/*  687:     */     protected HashEntry(HashEntry next, int hashCode, Object key, Object value)
/*  688:     */     {
/*  689:1034 */       this.next = next;
/*  690:1035 */       this.hashCode = hashCode;
/*  691:1036 */       this.key = key;
/*  692:1037 */       this.value = value;
/*  693:     */     }
/*  694:     */     
/*  695:     */     public Object getKey()
/*  696:     */     {
/*  697:1041 */       return this.key == AbstractHashedMap.NULL ? null : this.key;
/*  698:     */     }
/*  699:     */     
/*  700:     */     public Object getValue()
/*  701:     */     {
/*  702:1045 */       return this.value;
/*  703:     */     }
/*  704:     */     
/*  705:     */     public Object setValue(Object value)
/*  706:     */     {
/*  707:1049 */       Object old = this.value;
/*  708:1050 */       this.value = value;
/*  709:1051 */       return old;
/*  710:     */     }
/*  711:     */     
/*  712:     */     public boolean equals(Object obj)
/*  713:     */     {
/*  714:1055 */       if (obj == this) {
/*  715:1056 */         return true;
/*  716:     */       }
/*  717:1058 */       if (!(obj instanceof Map.Entry)) {
/*  718:1059 */         return false;
/*  719:     */       }
/*  720:1061 */       Map.Entry other = (Map.Entry)obj;
/*  721:1062 */       return (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey())) && (getValue() == null ? other.getValue() == null : getValue().equals(other.getValue()));
/*  722:     */     }
/*  723:     */     
/*  724:     */     public int hashCode()
/*  725:     */     {
/*  726:1068 */       return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/*  727:     */     }
/*  728:     */     
/*  729:     */     public String toString()
/*  730:     */     {
/*  731:1073 */       return getKey() + '=' + getValue();
/*  732:     */     }
/*  733:     */   }
/*  734:     */   
/*  735:     */   protected static abstract class HashIterator
/*  736:     */     implements Iterator
/*  737:     */   {
/*  738:     */     protected final AbstractHashedMap parent;
/*  739:     */     protected int hashIndex;
/*  740:     */     protected AbstractHashedMap.HashEntry last;
/*  741:     */     protected AbstractHashedMap.HashEntry next;
/*  742:     */     protected int expectedModCount;
/*  743:     */     
/*  744:     */     protected HashIterator(AbstractHashedMap parent)
/*  745:     */     {
/*  746:1095 */       this.parent = parent;
/*  747:1096 */       AbstractHashedMap.HashEntry[] data = parent.data;
/*  748:1097 */       int i = data.length;
/*  749:1098 */       AbstractHashedMap.HashEntry next = null;
/*  750:1099 */       while ((i > 0) && (next == null)) {
/*  751:1100 */         next = data[(--i)];
/*  752:     */       }
/*  753:1102 */       this.next = next;
/*  754:1103 */       this.hashIndex = i;
/*  755:1104 */       this.expectedModCount = parent.modCount;
/*  756:     */     }
/*  757:     */     
/*  758:     */     public boolean hasNext()
/*  759:     */     {
/*  760:1108 */       return this.next != null;
/*  761:     */     }
/*  762:     */     
/*  763:     */     protected AbstractHashedMap.HashEntry nextEntry()
/*  764:     */     {
/*  765:1112 */       if (this.parent.modCount != this.expectedModCount) {
/*  766:1113 */         throw new ConcurrentModificationException();
/*  767:     */       }
/*  768:1115 */       AbstractHashedMap.HashEntry newCurrent = this.next;
/*  769:1116 */       if (newCurrent == null) {
/*  770:1117 */         throw new NoSuchElementException("No next() entry in the iteration");
/*  771:     */       }
/*  772:1119 */       AbstractHashedMap.HashEntry[] data = this.parent.data;
/*  773:1120 */       int i = this.hashIndex;
/*  774:1121 */       AbstractHashedMap.HashEntry n = newCurrent.next;
/*  775:1122 */       while ((n == null) && (i > 0)) {
/*  776:1123 */         n = data[(--i)];
/*  777:     */       }
/*  778:1125 */       this.next = n;
/*  779:1126 */       this.hashIndex = i;
/*  780:1127 */       this.last = newCurrent;
/*  781:1128 */       return newCurrent;
/*  782:     */     }
/*  783:     */     
/*  784:     */     protected AbstractHashedMap.HashEntry currentEntry()
/*  785:     */     {
/*  786:1132 */       return this.last;
/*  787:     */     }
/*  788:     */     
/*  789:     */     public void remove()
/*  790:     */     {
/*  791:1136 */       if (this.last == null) {
/*  792:1137 */         throw new IllegalStateException("remove() can only be called once after next()");
/*  793:     */       }
/*  794:1139 */       if (this.parent.modCount != this.expectedModCount) {
/*  795:1140 */         throw new ConcurrentModificationException();
/*  796:     */       }
/*  797:1142 */       this.parent.remove(this.last.getKey());
/*  798:1143 */       this.last = null;
/*  799:1144 */       this.expectedModCount = this.parent.modCount;
/*  800:     */     }
/*  801:     */     
/*  802:     */     public String toString()
/*  803:     */     {
/*  804:1148 */       if (this.last != null) {
/*  805:1149 */         return "Iterator[" + this.last.getKey() + "=" + this.last.getValue() + "]";
/*  806:     */       }
/*  807:1151 */       return "Iterator[]";
/*  808:     */     }
/*  809:     */   }
/*  810:     */   
/*  811:     */   protected void doWriteObject(ObjectOutputStream out)
/*  812:     */     throws IOException
/*  813:     */   {
/*  814:1177 */     out.writeFloat(this.loadFactor);
/*  815:1178 */     out.writeInt(this.data.length);
/*  816:1179 */     out.writeInt(this.size);
/*  817:1180 */     for (MapIterator it = mapIterator(); it.hasNext();)
/*  818:     */     {
/*  819:1181 */       out.writeObject(it.next());
/*  820:1182 */       out.writeObject(it.getValue());
/*  821:     */     }
/*  822:     */   }
/*  823:     */   
/*  824:     */   protected void doReadObject(ObjectInputStream in)
/*  825:     */     throws IOException, ClassNotFoundException
/*  826:     */   {
/*  827:1205 */     this.loadFactor = in.readFloat();
/*  828:1206 */     int capacity = in.readInt();
/*  829:1207 */     int size = in.readInt();
/*  830:1208 */     init();
/*  831:1209 */     this.threshold = calculateThreshold(capacity, this.loadFactor);
/*  832:1210 */     this.data = new HashEntry[capacity];
/*  833:1211 */     for (int i = 0; i < size; i++)
/*  834:     */     {
/*  835:1212 */       Object key = in.readObject();
/*  836:1213 */       Object value = in.readObject();
/*  837:1214 */       put(key, value);
/*  838:     */     }
/*  839:     */   }
/*  840:     */   
/*  841:     */   protected Object clone()
/*  842:     */   {
/*  843:     */     try
/*  844:     */     {
/*  845:1229 */       AbstractHashedMap cloned = (AbstractHashedMap)super.clone();
/*  846:1230 */       cloned.data = new HashEntry[this.data.length];
/*  847:1231 */       cloned.entrySet = null;
/*  848:1232 */       cloned.keySet = null;
/*  849:1233 */       cloned.values = null;
/*  850:1234 */       cloned.modCount = 0;
/*  851:1235 */       cloned.size = 0;
/*  852:1236 */       cloned.init();
/*  853:1237 */       cloned.putAll(this);
/*  854:1238 */       return cloned;
/*  855:     */     }
/*  856:     */     catch (CloneNotSupportedException ex) {}
/*  857:1241 */     return null;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public boolean equals(Object obj)
/*  861:     */   {
/*  862:1252 */     if (obj == this) {
/*  863:1253 */       return true;
/*  864:     */     }
/*  865:1255 */     if (!(obj instanceof Map)) {
/*  866:1256 */       return false;
/*  867:     */     }
/*  868:1258 */     Map map = (Map)obj;
/*  869:1259 */     if (map.size() != size()) {
/*  870:1260 */       return false;
/*  871:     */     }
/*  872:1262 */     MapIterator it = mapIterator();
/*  873:     */     try
/*  874:     */     {
/*  875:1264 */       while (it.hasNext())
/*  876:     */       {
/*  877:1265 */         Object key = it.next();
/*  878:1266 */         Object value = it.getValue();
/*  879:1267 */         if (value == null)
/*  880:     */         {
/*  881:1268 */           if ((map.get(key) != null) || (!map.containsKey(key))) {
/*  882:1269 */             return false;
/*  883:     */           }
/*  884:     */         }
/*  885:1272 */         else if (!value.equals(map.get(key))) {
/*  886:1273 */           return false;
/*  887:     */         }
/*  888:     */       }
/*  889:     */     }
/*  890:     */     catch (ClassCastException ignored)
/*  891:     */     {
/*  892:1278 */       return false;
/*  893:     */     }
/*  894:     */     catch (NullPointerException ignored)
/*  895:     */     {
/*  896:1280 */       return false;
/*  897:     */     }
/*  898:1282 */     return true;
/*  899:     */   }
/*  900:     */   
/*  901:     */   public int hashCode()
/*  902:     */   {
/*  903:1291 */     int total = 0;
/*  904:1292 */     Iterator it = createEntrySetIterator();
/*  905:1293 */     while (it.hasNext()) {
/*  906:1294 */       total += it.next().hashCode();
/*  907:     */     }
/*  908:1296 */     return total;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public String toString()
/*  912:     */   {
/*  913:1305 */     if (size() == 0) {
/*  914:1306 */       return "{}";
/*  915:     */     }
/*  916:1308 */     StringBuffer buf = new StringBuffer(32 * size());
/*  917:1309 */     buf.append('{');
/*  918:     */     
/*  919:1311 */     MapIterator it = mapIterator();
/*  920:1312 */     boolean hasNext = it.hasNext();
/*  921:1313 */     while (hasNext)
/*  922:     */     {
/*  923:1314 */       Object key = it.next();
/*  924:1315 */       Object value = it.getValue();
/*  925:1316 */       buf.append(key == this ? "(this Map)" : key).append('=').append(value == this ? "(this Map)" : value);
/*  926:     */       
/*  927:     */ 
/*  928:     */ 
/*  929:1320 */       hasNext = it.hasNext();
/*  930:1321 */       if (hasNext) {
/*  931:1322 */         buf.append(',').append(' ');
/*  932:     */       }
/*  933:     */     }
/*  934:1326 */     buf.append('}');
/*  935:1327 */     return buf.toString();
/*  936:     */   }
/*  937:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.AbstractHashedMap
 * JD-Core Version:    0.7.0.1
 */