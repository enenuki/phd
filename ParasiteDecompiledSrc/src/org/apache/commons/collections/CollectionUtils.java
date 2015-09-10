/*    1:     */ package org.apache.commons.collections;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.Array;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.Enumeration;
/*    7:     */ import java.util.HashMap;
/*    8:     */ import java.util.HashSet;
/*    9:     */ import java.util.Iterator;
/*   10:     */ import java.util.List;
/*   11:     */ import java.util.ListIterator;
/*   12:     */ import java.util.Map;
/*   13:     */ import java.util.Set;
/*   14:     */ import org.apache.commons.collections.collection.PredicatedCollection;
/*   15:     */ import org.apache.commons.collections.collection.SynchronizedCollection;
/*   16:     */ import org.apache.commons.collections.collection.TransformedCollection;
/*   17:     */ import org.apache.commons.collections.collection.TypedCollection;
/*   18:     */ import org.apache.commons.collections.collection.UnmodifiableBoundedCollection;
/*   19:     */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*   20:     */ 
/*   21:     */ public class CollectionUtils
/*   22:     */ {
/*   23:  61 */   private static Integer INTEGER_ONE = new Integer(1);
/*   24:  69 */   public static final Collection EMPTY_COLLECTION = UnmodifiableCollection.decorate(new ArrayList());
/*   25:     */   
/*   26:     */   public static Collection union(Collection a, Collection b)
/*   27:     */   {
/*   28:  91 */     ArrayList list = new ArrayList();
/*   29:  92 */     Map mapa = getCardinalityMap(a);
/*   30:  93 */     Map mapb = getCardinalityMap(b);
/*   31:  94 */     Set elts = new HashSet(a);
/*   32:  95 */     elts.addAll(b);
/*   33:  96 */     Iterator it = elts.iterator();
/*   34:  97 */     while (it.hasNext())
/*   35:     */     {
/*   36:  98 */       Object obj = it.next();
/*   37:  99 */       int i = 0;
/*   38:  99 */       for (int m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++) {
/*   39: 100 */         list.add(obj);
/*   40:     */       }
/*   41:     */     }
/*   42: 103 */     return list;
/*   43:     */   }
/*   44:     */   
/*   45:     */   public static Collection intersection(Collection a, Collection b)
/*   46:     */   {
/*   47: 121 */     ArrayList list = new ArrayList();
/*   48: 122 */     Map mapa = getCardinalityMap(a);
/*   49: 123 */     Map mapb = getCardinalityMap(b);
/*   50: 124 */     Set elts = new HashSet(a);
/*   51: 125 */     elts.addAll(b);
/*   52: 126 */     Iterator it = elts.iterator();
/*   53: 127 */     while (it.hasNext())
/*   54:     */     {
/*   55: 128 */       Object obj = it.next();
/*   56: 129 */       int i = 0;
/*   57: 129 */       for (int m = Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++) {
/*   58: 130 */         list.add(obj);
/*   59:     */       }
/*   60:     */     }
/*   61: 133 */     return list;
/*   62:     */   }
/*   63:     */   
/*   64:     */   public static Collection disjunction(Collection a, Collection b)
/*   65:     */   {
/*   66: 154 */     ArrayList list = new ArrayList();
/*   67: 155 */     Map mapa = getCardinalityMap(a);
/*   68: 156 */     Map mapb = getCardinalityMap(b);
/*   69: 157 */     Set elts = new HashSet(a);
/*   70: 158 */     elts.addAll(b);
/*   71: 159 */     Iterator it = elts.iterator();
/*   72: 160 */     while (it.hasNext())
/*   73:     */     {
/*   74: 161 */       Object obj = it.next();
/*   75: 162 */       int i = 0;
/*   76: 162 */       for (int m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)) - Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++) {
/*   77: 163 */         list.add(obj);
/*   78:     */       }
/*   79:     */     }
/*   80: 166 */     return list;
/*   81:     */   }
/*   82:     */   
/*   83:     */   public static Collection subtract(Collection a, Collection b)
/*   84:     */   {
/*   85: 181 */     ArrayList list = new ArrayList(a);
/*   86: 182 */     for (Iterator it = b.iterator(); it.hasNext();) {
/*   87: 183 */       list.remove(it.next());
/*   88:     */     }
/*   89: 185 */     return list;
/*   90:     */   }
/*   91:     */   
/*   92:     */   public static boolean containsAny(Collection coll1, Collection coll2)
/*   93:     */   {
/*   94:     */     Iterator it;
/*   95:     */     Iterator it;
/*   96: 201 */     if (coll1.size() < coll2.size()) {
/*   97: 202 */       for (it = coll1.iterator(); it.hasNext();) {
/*   98: 203 */         if (coll2.contains(it.next())) {
/*   99: 204 */           return true;
/*  100:     */         }
/*  101:     */       }
/*  102:     */     } else {
/*  103: 208 */       for (it = coll2.iterator(); it.hasNext();) {
/*  104: 209 */         if (coll1.contains(it.next())) {
/*  105: 210 */           return true;
/*  106:     */         }
/*  107:     */       }
/*  108:     */     }
/*  109: 214 */     return false;
/*  110:     */   }
/*  111:     */   
/*  112:     */   public static Map getCardinalityMap(Collection coll)
/*  113:     */   {
/*  114: 229 */     Map count = new HashMap();
/*  115: 230 */     for (Iterator it = coll.iterator(); it.hasNext();)
/*  116:     */     {
/*  117: 231 */       Object obj = it.next();
/*  118: 232 */       Integer c = (Integer)count.get(obj);
/*  119: 233 */       if (c == null) {
/*  120: 234 */         count.put(obj, INTEGER_ONE);
/*  121:     */       } else {
/*  122: 236 */         count.put(obj, new Integer(c.intValue() + 1));
/*  123:     */       }
/*  124:     */     }
/*  125: 239 */     return count;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public static boolean isSubCollection(Collection a, Collection b)
/*  129:     */   {
/*  130: 255 */     Map mapa = getCardinalityMap(a);
/*  131: 256 */     Map mapb = getCardinalityMap(b);
/*  132: 257 */     Iterator it = a.iterator();
/*  133: 258 */     while (it.hasNext())
/*  134:     */     {
/*  135: 259 */       Object obj = it.next();
/*  136: 260 */       if (getFreq(obj, mapa) > getFreq(obj, mapb)) {
/*  137: 261 */         return false;
/*  138:     */       }
/*  139:     */     }
/*  140: 264 */     return true;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public static boolean isProperSubCollection(Collection a, Collection b)
/*  144:     */   {
/*  145: 289 */     return (a.size() < b.size()) && (isSubCollection(a, b));
/*  146:     */   }
/*  147:     */   
/*  148:     */   public static boolean isEqualCollection(Collection a, Collection b)
/*  149:     */   {
/*  150: 305 */     if (a.size() != b.size()) {
/*  151: 306 */       return false;
/*  152:     */     }
/*  153: 308 */     Map mapa = getCardinalityMap(a);
/*  154: 309 */     Map mapb = getCardinalityMap(b);
/*  155: 310 */     if (mapa.size() != mapb.size()) {
/*  156: 311 */       return false;
/*  157:     */     }
/*  158: 313 */     Iterator it = mapa.keySet().iterator();
/*  159: 314 */     while (it.hasNext())
/*  160:     */     {
/*  161: 315 */       Object obj = it.next();
/*  162: 316 */       if (getFreq(obj, mapa) != getFreq(obj, mapb)) {
/*  163: 317 */         return false;
/*  164:     */       }
/*  165:     */     }
/*  166: 320 */     return true;
/*  167:     */   }
/*  168:     */   
/*  169:     */   public static int cardinality(Object obj, Collection coll)
/*  170:     */   {
/*  171: 333 */     if ((coll instanceof Set)) {
/*  172: 334 */       return coll.contains(obj) ? 1 : 0;
/*  173:     */     }
/*  174: 336 */     if ((coll instanceof Bag)) {
/*  175: 337 */       return ((Bag)coll).getCount(obj);
/*  176:     */     }
/*  177: 339 */     int count = 0;
/*  178:     */     Iterator it;
/*  179:     */     Iterator it;
/*  180: 340 */     if (obj == null) {
/*  181: 341 */       for (it = coll.iterator(); it.hasNext();) {
/*  182: 342 */         if (it.next() == null) {
/*  183: 343 */           count++;
/*  184:     */         }
/*  185:     */       }
/*  186:     */     } else {
/*  187: 347 */       for (it = coll.iterator(); it.hasNext();) {
/*  188: 348 */         if (obj.equals(it.next())) {
/*  189: 349 */           count++;
/*  190:     */         }
/*  191:     */       }
/*  192:     */     }
/*  193: 353 */     return count;
/*  194:     */   }
/*  195:     */   
/*  196:     */   public static Object find(Collection collection, Predicate predicate)
/*  197:     */   {
/*  198:     */     Iterator iter;
/*  199: 367 */     if ((collection != null) && (predicate != null)) {
/*  200: 368 */       for (iter = collection.iterator(); iter.hasNext();)
/*  201:     */       {
/*  202: 369 */         Object item = iter.next();
/*  203: 370 */         if (predicate.evaluate(item)) {
/*  204: 371 */           return item;
/*  205:     */         }
/*  206:     */       }
/*  207:     */     }
/*  208: 375 */     return null;
/*  209:     */   }
/*  210:     */   
/*  211:     */   public static void forAllDo(Collection collection, Closure closure)
/*  212:     */   {
/*  213:     */     Iterator it;
/*  214: 387 */     if ((collection != null) && (closure != null)) {
/*  215: 388 */       for (it = collection.iterator(); it.hasNext();) {
/*  216: 389 */         closure.execute(it.next());
/*  217:     */       }
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   public static void filter(Collection collection, Predicate predicate)
/*  222:     */   {
/*  223:     */     Iterator it;
/*  224: 404 */     if ((collection != null) && (predicate != null)) {
/*  225: 405 */       for (it = collection.iterator(); it.hasNext();) {
/*  226: 406 */         if (!predicate.evaluate(it.next())) {
/*  227: 407 */           it.remove();
/*  228:     */         }
/*  229:     */       }
/*  230:     */     }
/*  231:     */   }
/*  232:     */   
/*  233:     */   public static void transform(Collection collection, Transformer transformer)
/*  234:     */   {
/*  235: 430 */     if ((collection != null) && (transformer != null))
/*  236:     */     {
/*  237:     */       ListIterator it;
/*  238: 431 */       if ((collection instanceof List))
/*  239:     */       {
/*  240: 432 */         List list = (List)collection;
/*  241: 433 */         for (it = list.listIterator(); it.hasNext();) {
/*  242: 434 */           it.set(transformer.transform(it.next()));
/*  243:     */         }
/*  244:     */       }
/*  245:     */       else
/*  246:     */       {
/*  247: 437 */         Collection resultCollection = collect(collection, transformer);
/*  248: 438 */         collection.clear();
/*  249: 439 */         collection.addAll(resultCollection);
/*  250:     */       }
/*  251:     */     }
/*  252:     */   }
/*  253:     */   
/*  254:     */   public static int countMatches(Collection inputCollection, Predicate predicate)
/*  255:     */   {
/*  256: 454 */     int count = 0;
/*  257:     */     Iterator it;
/*  258: 455 */     if ((inputCollection != null) && (predicate != null)) {
/*  259: 456 */       for (it = inputCollection.iterator(); it.hasNext();) {
/*  260: 457 */         if (predicate.evaluate(it.next())) {
/*  261: 458 */           count++;
/*  262:     */         }
/*  263:     */       }
/*  264:     */     }
/*  265: 462 */     return count;
/*  266:     */   }
/*  267:     */   
/*  268:     */   public static boolean exists(Collection collection, Predicate predicate)
/*  269:     */   {
/*  270:     */     Iterator it;
/*  271: 475 */     if ((collection != null) && (predicate != null)) {
/*  272: 476 */       for (it = collection.iterator(); it.hasNext();) {
/*  273: 477 */         if (predicate.evaluate(it.next())) {
/*  274: 478 */           return true;
/*  275:     */         }
/*  276:     */       }
/*  277:     */     }
/*  278: 482 */     return false;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public static Collection select(Collection inputCollection, Predicate predicate)
/*  282:     */   {
/*  283: 497 */     ArrayList answer = new ArrayList(inputCollection.size());
/*  284: 498 */     select(inputCollection, predicate, answer);
/*  285: 499 */     return answer;
/*  286:     */   }
/*  287:     */   
/*  288:     */   public static void select(Collection inputCollection, Predicate predicate, Collection outputCollection)
/*  289:     */   {
/*  290:     */     Iterator iter;
/*  291: 514 */     if ((inputCollection != null) && (predicate != null)) {
/*  292: 515 */       for (iter = inputCollection.iterator(); iter.hasNext();)
/*  293:     */       {
/*  294: 516 */         Object item = iter.next();
/*  295: 517 */         if (predicate.evaluate(item)) {
/*  296: 518 */           outputCollection.add(item);
/*  297:     */         }
/*  298:     */       }
/*  299:     */     }
/*  300:     */   }
/*  301:     */   
/*  302:     */   public static Collection selectRejected(Collection inputCollection, Predicate predicate)
/*  303:     */   {
/*  304: 536 */     ArrayList answer = new ArrayList(inputCollection.size());
/*  305: 537 */     selectRejected(inputCollection, predicate, answer);
/*  306: 538 */     return answer;
/*  307:     */   }
/*  308:     */   
/*  309:     */   public static void selectRejected(Collection inputCollection, Predicate predicate, Collection outputCollection)
/*  310:     */   {
/*  311:     */     Iterator iter;
/*  312: 552 */     if ((inputCollection != null) && (predicate != null)) {
/*  313: 553 */       for (iter = inputCollection.iterator(); iter.hasNext();)
/*  314:     */       {
/*  315: 554 */         Object item = iter.next();
/*  316: 555 */         if (!predicate.evaluate(item)) {
/*  317: 556 */           outputCollection.add(item);
/*  318:     */         }
/*  319:     */       }
/*  320:     */     }
/*  321:     */   }
/*  322:     */   
/*  323:     */   public static Collection collect(Collection inputCollection, Transformer transformer)
/*  324:     */   {
/*  325: 574 */     ArrayList answer = new ArrayList(inputCollection.size());
/*  326: 575 */     collect(inputCollection, transformer, answer);
/*  327: 576 */     return answer;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public static Collection collect(Iterator inputIterator, Transformer transformer)
/*  331:     */   {
/*  332: 590 */     ArrayList answer = new ArrayList();
/*  333: 591 */     collect(inputIterator, transformer, answer);
/*  334: 592 */     return answer;
/*  335:     */   }
/*  336:     */   
/*  337:     */   public static Collection collect(Collection inputCollection, Transformer transformer, Collection outputCollection)
/*  338:     */   {
/*  339: 609 */     if (inputCollection != null) {
/*  340: 610 */       return collect(inputCollection.iterator(), transformer, outputCollection);
/*  341:     */     }
/*  342: 612 */     return outputCollection;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public static Collection collect(Iterator inputIterator, Transformer transformer, Collection outputCollection)
/*  346:     */   {
/*  347: 629 */     if ((inputIterator != null) && (transformer != null)) {
/*  348: 630 */       while (inputIterator.hasNext())
/*  349:     */       {
/*  350: 631 */         Object item = inputIterator.next();
/*  351: 632 */         Object value = transformer.transform(item);
/*  352: 633 */         outputCollection.add(value);
/*  353:     */       }
/*  354:     */     }
/*  355: 636 */     return outputCollection;
/*  356:     */   }
/*  357:     */   
/*  358:     */   public static boolean addIgnoreNull(Collection collection, Object object)
/*  359:     */   {
/*  360: 650 */     return object == null ? false : collection.add(object);
/*  361:     */   }
/*  362:     */   
/*  363:     */   public static void addAll(Collection collection, Iterator iterator)
/*  364:     */   {
/*  365: 661 */     while (iterator.hasNext()) {
/*  366: 662 */       collection.add(iterator.next());
/*  367:     */     }
/*  368:     */   }
/*  369:     */   
/*  370:     */   public static void addAll(Collection collection, Enumeration enumeration)
/*  371:     */   {
/*  372: 674 */     while (enumeration.hasMoreElements()) {
/*  373: 675 */       collection.add(enumeration.nextElement());
/*  374:     */     }
/*  375:     */   }
/*  376:     */   
/*  377:     */   public static void addAll(Collection collection, Object[] elements)
/*  378:     */   {
/*  379: 687 */     int i = 0;
/*  380: 687 */     for (int size = elements.length; i < size; i++) {
/*  381: 688 */       collection.add(elements[i]);
/*  382:     */     }
/*  383:     */   }
/*  384:     */   
/*  385:     */   /**
/*  386:     */    * @deprecated
/*  387:     */    */
/*  388:     */   public static Object index(Object obj, int idx)
/*  389:     */   {
/*  390: 715 */     return index(obj, new Integer(idx));
/*  391:     */   }
/*  392:     */   
/*  393:     */   /**
/*  394:     */    * @deprecated
/*  395:     */    */
/*  396:     */   public static Object index(Object obj, Object index)
/*  397:     */   {
/*  398: 742 */     if ((obj instanceof Map))
/*  399:     */     {
/*  400: 743 */       Map map = (Map)obj;
/*  401: 744 */       if (map.containsKey(index)) {
/*  402: 745 */         return map.get(index);
/*  403:     */       }
/*  404:     */     }
/*  405: 748 */     int idx = -1;
/*  406: 749 */     if ((index instanceof Integer)) {
/*  407: 750 */       idx = ((Integer)index).intValue();
/*  408:     */     }
/*  409: 752 */     if (idx < 0) {
/*  410: 753 */       return obj;
/*  411:     */     }
/*  412: 755 */     if ((obj instanceof Map))
/*  413:     */     {
/*  414: 756 */       Map map = (Map)obj;
/*  415: 757 */       Iterator iterator = map.keySet().iterator();
/*  416: 758 */       return index(iterator, idx);
/*  417:     */     }
/*  418: 760 */     if ((obj instanceof List)) {
/*  419: 761 */       return ((List)obj).get(idx);
/*  420:     */     }
/*  421: 763 */     if ((obj instanceof Object[])) {
/*  422: 764 */       return ((Object[])(Object[])obj)[idx];
/*  423:     */     }
/*  424: 766 */     if ((obj instanceof Enumeration))
/*  425:     */     {
/*  426: 767 */       Enumeration it = (Enumeration)obj;
/*  427: 768 */       while (it.hasMoreElements())
/*  428:     */       {
/*  429: 769 */         idx--;
/*  430: 770 */         if (idx == -1) {
/*  431: 771 */           return it.nextElement();
/*  432:     */         }
/*  433: 773 */         it.nextElement();
/*  434:     */       }
/*  435:     */     }
/*  436:     */     else
/*  437:     */     {
/*  438: 777 */       if ((obj instanceof Iterator)) {
/*  439: 778 */         return index((Iterator)obj, idx);
/*  440:     */       }
/*  441: 780 */       if ((obj instanceof Collection))
/*  442:     */       {
/*  443: 781 */         Iterator iterator = ((Collection)obj).iterator();
/*  444: 782 */         return index(iterator, idx);
/*  445:     */       }
/*  446:     */     }
/*  447: 784 */     return obj;
/*  448:     */   }
/*  449:     */   
/*  450:     */   private static Object index(Iterator iterator, int idx)
/*  451:     */   {
/*  452: 788 */     while (iterator.hasNext())
/*  453:     */     {
/*  454: 789 */       idx--;
/*  455: 790 */       if (idx == -1) {
/*  456: 791 */         return iterator.next();
/*  457:     */       }
/*  458: 793 */       iterator.next();
/*  459:     */     }
/*  460: 796 */     return iterator;
/*  461:     */   }
/*  462:     */   
/*  463:     */   public static Object get(Object object, int index)
/*  464:     */   {
/*  465: 830 */     if (index < 0) {
/*  466: 831 */       throw new IndexOutOfBoundsException("Index cannot be negative: " + index);
/*  467:     */     }
/*  468: 833 */     if ((object instanceof Map))
/*  469:     */     {
/*  470: 834 */       Map map = (Map)object;
/*  471: 835 */       Iterator iterator = map.entrySet().iterator();
/*  472: 836 */       return get(iterator, index);
/*  473:     */     }
/*  474: 837 */     if ((object instanceof List)) {
/*  475: 838 */       return ((List)object).get(index);
/*  476:     */     }
/*  477: 839 */     if ((object instanceof Object[])) {
/*  478: 840 */       return ((Object[])(Object[])object)[index];
/*  479:     */     }
/*  480: 841 */     if ((object instanceof Iterator))
/*  481:     */     {
/*  482: 842 */       Iterator it = (Iterator)object;
/*  483: 843 */       while (it.hasNext())
/*  484:     */       {
/*  485: 844 */         index--;
/*  486: 845 */         if (index == -1) {
/*  487: 846 */           return it.next();
/*  488:     */         }
/*  489: 848 */         it.next();
/*  490:     */       }
/*  491: 851 */       throw new IndexOutOfBoundsException("Entry does not exist: " + index);
/*  492:     */     }
/*  493: 852 */     if ((object instanceof Collection))
/*  494:     */     {
/*  495: 853 */       Iterator iterator = ((Collection)object).iterator();
/*  496: 854 */       return get(iterator, index);
/*  497:     */     }
/*  498: 855 */     if ((object instanceof Enumeration))
/*  499:     */     {
/*  500: 856 */       Enumeration it = (Enumeration)object;
/*  501: 857 */       while (it.hasMoreElements())
/*  502:     */       {
/*  503: 858 */         index--;
/*  504: 859 */         if (index == -1) {
/*  505: 860 */           return it.nextElement();
/*  506:     */         }
/*  507: 862 */         it.nextElement();
/*  508:     */       }
/*  509: 865 */       throw new IndexOutOfBoundsException("Entry does not exist: " + index);
/*  510:     */     }
/*  511: 866 */     if (object == null) {
/*  512: 867 */       throw new IllegalArgumentException("Unsupported object type: null");
/*  513:     */     }
/*  514:     */     try
/*  515:     */     {
/*  516: 870 */       return Array.get(object, index);
/*  517:     */     }
/*  518:     */     catch (IllegalArgumentException ex)
/*  519:     */     {
/*  520: 872 */       throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
/*  521:     */     }
/*  522:     */   }
/*  523:     */   
/*  524:     */   public static int size(Object object)
/*  525:     */   {
/*  526: 895 */     int total = 0;
/*  527: 896 */     if ((object instanceof Map))
/*  528:     */     {
/*  529: 897 */       total = ((Map)object).size();
/*  530:     */     }
/*  531: 898 */     else if ((object instanceof Collection))
/*  532:     */     {
/*  533: 899 */       total = ((Collection)object).size();
/*  534:     */     }
/*  535: 900 */     else if ((object instanceof Object[]))
/*  536:     */     {
/*  537: 901 */       total = ((Object[])object).length;
/*  538:     */     }
/*  539: 902 */     else if ((object instanceof Iterator))
/*  540:     */     {
/*  541: 903 */       Iterator it = (Iterator)object;
/*  542: 904 */       while (it.hasNext())
/*  543:     */       {
/*  544: 905 */         total++;
/*  545: 906 */         it.next();
/*  546:     */       }
/*  547:     */     }
/*  548: 908 */     else if ((object instanceof Enumeration))
/*  549:     */     {
/*  550: 909 */       Enumeration it = (Enumeration)object;
/*  551: 910 */       while (it.hasMoreElements())
/*  552:     */       {
/*  553: 911 */         total++;
/*  554: 912 */         it.nextElement();
/*  555:     */       }
/*  556:     */     }
/*  557:     */     else
/*  558:     */     {
/*  559: 914 */       if (object == null) {
/*  560: 915 */         throw new IllegalArgumentException("Unsupported object type: null");
/*  561:     */       }
/*  562:     */       try
/*  563:     */       {
/*  564: 918 */         total = Array.getLength(object);
/*  565:     */       }
/*  566:     */       catch (IllegalArgumentException ex)
/*  567:     */       {
/*  568: 920 */         throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
/*  569:     */       }
/*  570:     */     }
/*  571: 923 */     return total;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public static boolean sizeIsEmpty(Object object)
/*  575:     */   {
/*  576: 947 */     if ((object instanceof Collection)) {
/*  577: 948 */       return ((Collection)object).isEmpty();
/*  578:     */     }
/*  579: 949 */     if ((object instanceof Map)) {
/*  580: 950 */       return ((Map)object).isEmpty();
/*  581:     */     }
/*  582: 951 */     if ((object instanceof Object[])) {
/*  583: 952 */       return ((Object[])object).length == 0;
/*  584:     */     }
/*  585: 953 */     if ((object instanceof Iterator)) {
/*  586: 954 */       return !((Iterator)object).hasNext();
/*  587:     */     }
/*  588: 955 */     if ((object instanceof Enumeration)) {
/*  589: 956 */       return !((Enumeration)object).hasMoreElements();
/*  590:     */     }
/*  591: 957 */     if (object == null) {
/*  592: 958 */       throw new IllegalArgumentException("Unsupported object type: null");
/*  593:     */     }
/*  594:     */     try
/*  595:     */     {
/*  596: 961 */       return Array.getLength(object) == 0;
/*  597:     */     }
/*  598:     */     catch (IllegalArgumentException ex)
/*  599:     */     {
/*  600: 963 */       throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
/*  601:     */     }
/*  602:     */   }
/*  603:     */   
/*  604:     */   public static boolean isEmpty(Collection coll)
/*  605:     */   {
/*  606: 979 */     return (coll == null) || (coll.isEmpty());
/*  607:     */   }
/*  608:     */   
/*  609:     */   public static boolean isNotEmpty(Collection coll)
/*  610:     */   {
/*  611: 992 */     return !isEmpty(coll);
/*  612:     */   }
/*  613:     */   
/*  614:     */   public static void reverseArray(Object[] array)
/*  615:     */   {
/*  616:1002 */     int i = 0;
/*  617:1003 */     int j = array.length - 1;
/*  618:1006 */     while (j > i)
/*  619:     */     {
/*  620:1007 */       Object tmp = array[j];
/*  621:1008 */       array[j] = array[i];
/*  622:1009 */       array[i] = tmp;
/*  623:1010 */       j--;
/*  624:1011 */       i++;
/*  625:     */     }
/*  626:     */   }
/*  627:     */   
/*  628:     */   private static final int getFreq(Object obj, Map freqMap)
/*  629:     */   {
/*  630:1016 */     Integer count = (Integer)freqMap.get(obj);
/*  631:1017 */     if (count != null) {
/*  632:1018 */       return count.intValue();
/*  633:     */     }
/*  634:1020 */     return 0;
/*  635:     */   }
/*  636:     */   
/*  637:     */   public static boolean isFull(Collection coll)
/*  638:     */   {
/*  639:1039 */     if (coll == null) {
/*  640:1040 */       throw new NullPointerException("The collection must not be null");
/*  641:     */     }
/*  642:1042 */     if ((coll instanceof BoundedCollection)) {
/*  643:1043 */       return ((BoundedCollection)coll).isFull();
/*  644:     */     }
/*  645:     */     try
/*  646:     */     {
/*  647:1046 */       BoundedCollection bcoll = UnmodifiableBoundedCollection.decorateUsing(coll);
/*  648:1047 */       return bcoll.isFull();
/*  649:     */     }
/*  650:     */     catch (IllegalArgumentException ex) {}
/*  651:1050 */     return false;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public static int maxSize(Collection coll)
/*  655:     */   {
/*  656:1070 */     if (coll == null) {
/*  657:1071 */       throw new NullPointerException("The collection must not be null");
/*  658:     */     }
/*  659:1073 */     if ((coll instanceof BoundedCollection)) {
/*  660:1074 */       return ((BoundedCollection)coll).maxSize();
/*  661:     */     }
/*  662:     */     try
/*  663:     */     {
/*  664:1077 */       BoundedCollection bcoll = UnmodifiableBoundedCollection.decorateUsing(coll);
/*  665:1078 */       return bcoll.maxSize();
/*  666:     */     }
/*  667:     */     catch (IllegalArgumentException ex) {}
/*  668:1081 */     return -1;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public static Collection retainAll(Collection collection, Collection retain)
/*  672:     */   {
/*  673:1102 */     return ListUtils.retainAll(collection, retain);
/*  674:     */   }
/*  675:     */   
/*  676:     */   public static Collection removeAll(Collection collection, Collection remove)
/*  677:     */   {
/*  678:1122 */     return ListUtils.retainAll(collection, remove);
/*  679:     */   }
/*  680:     */   
/*  681:     */   public static Collection synchronizedCollection(Collection collection)
/*  682:     */   {
/*  683:1149 */     return SynchronizedCollection.decorate(collection);
/*  684:     */   }
/*  685:     */   
/*  686:     */   public static Collection unmodifiableCollection(Collection collection)
/*  687:     */   {
/*  688:1162 */     return UnmodifiableCollection.decorate(collection);
/*  689:     */   }
/*  690:     */   
/*  691:     */   public static Collection predicatedCollection(Collection collection, Predicate predicate)
/*  692:     */   {
/*  693:1179 */     return PredicatedCollection.decorate(collection, predicate);
/*  694:     */   }
/*  695:     */   
/*  696:     */   public static Collection typedCollection(Collection collection, Class type)
/*  697:     */   {
/*  698:1192 */     return TypedCollection.decorate(collection, type);
/*  699:     */   }
/*  700:     */   
/*  701:     */   public static Collection transformedCollection(Collection collection, Transformer transformer)
/*  702:     */   {
/*  703:1208 */     return TransformedCollection.decorate(collection, transformer);
/*  704:     */   }
/*  705:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.CollectionUtils
 * JD-Core Version:    0.7.0.1
 */