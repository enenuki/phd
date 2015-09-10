/*    1:     */ package org.apache.commons.collections.bidimap;
/*    2:     */ 
/*    3:     */ import java.util.AbstractSet;
/*    4:     */ import java.util.Collection;
/*    5:     */ import java.util.ConcurrentModificationException;
/*    6:     */ import java.util.Iterator;
/*    7:     */ import java.util.Map;
/*    8:     */ import java.util.Map.Entry;
/*    9:     */ import java.util.NoSuchElementException;
/*   10:     */ import java.util.Set;
/*   11:     */ import org.apache.commons.collections.BidiMap;
/*   12:     */ import org.apache.commons.collections.KeyValue;
/*   13:     */ import org.apache.commons.collections.MapIterator;
/*   14:     */ import org.apache.commons.collections.OrderedBidiMap;
/*   15:     */ import org.apache.commons.collections.OrderedIterator;
/*   16:     */ import org.apache.commons.collections.OrderedMapIterator;
/*   17:     */ import org.apache.commons.collections.iterators.EmptyOrderedMapIterator;
/*   18:     */ import org.apache.commons.collections.keyvalue.UnmodifiableMapEntry;
/*   19:     */ 
/*   20:     */ public class TreeBidiMap
/*   21:     */   implements OrderedBidiMap
/*   22:     */ {
/*   23:     */   private static final int KEY = 0;
/*   24:     */   private static final int VALUE = 1;
/*   25:     */   private static final int MAPENTRY = 2;
/*   26:     */   private static final int INVERSEMAPENTRY = 3;
/*   27:     */   private static final int SUM_OF_INDICES = 1;
/*   28:     */   private static final int FIRST_INDEX = 0;
/*   29:     */   private static final int NUMBER_OF_INDICES = 2;
/*   30:  85 */   private static final String[] dataName = { "key", "value" };
/*   31:  87 */   private Node[] rootNode = new Node[2];
/*   32:  88 */   private int nodeCount = 0;
/*   33:  89 */   private int modifications = 0;
/*   34:     */   private Set keySet;
/*   35:     */   private Set valuesSet;
/*   36:     */   private Set entrySet;
/*   37:  93 */   private Inverse inverse = null;
/*   38:     */   
/*   39:     */   public TreeBidiMap() {}
/*   40:     */   
/*   41:     */   public TreeBidiMap(Map map)
/*   42:     */   {
/*   43: 113 */     putAll(map);
/*   44:     */   }
/*   45:     */   
/*   46:     */   public int size()
/*   47:     */   {
/*   48: 123 */     return this.nodeCount;
/*   49:     */   }
/*   50:     */   
/*   51:     */   public boolean isEmpty()
/*   52:     */   {
/*   53: 132 */     return this.nodeCount == 0;
/*   54:     */   }
/*   55:     */   
/*   56:     */   public boolean containsKey(Object key)
/*   57:     */   {
/*   58: 146 */     checkKey(key);
/*   59: 147 */     return lookup((Comparable)key, 0) != null;
/*   60:     */   }
/*   61:     */   
/*   62:     */   public boolean containsValue(Object value)
/*   63:     */   {
/*   64: 161 */     checkValue(value);
/*   65: 162 */     return lookup((Comparable)value, 1) != null;
/*   66:     */   }
/*   67:     */   
/*   68:     */   public Object get(Object key)
/*   69:     */   {
/*   70: 178 */     return doGet((Comparable)key, 0);
/*   71:     */   }
/*   72:     */   
/*   73:     */   public Object put(Object key, Object value)
/*   74:     */   {
/*   75: 206 */     return doPut((Comparable)key, (Comparable)value, 0);
/*   76:     */   }
/*   77:     */   
/*   78:     */   public void putAll(Map map)
/*   79:     */   {
/*   80: 217 */     Iterator it = map.entrySet().iterator();
/*   81: 218 */     while (it.hasNext())
/*   82:     */     {
/*   83: 219 */       Map.Entry entry = (Map.Entry)it.next();
/*   84: 220 */       put(entry.getKey(), entry.getValue());
/*   85:     */     }
/*   86:     */   }
/*   87:     */   
/*   88:     */   public Object remove(Object key)
/*   89:     */   {
/*   90: 236 */     return doRemove((Comparable)key, 0);
/*   91:     */   }
/*   92:     */   
/*   93:     */   public void clear()
/*   94:     */   {
/*   95: 243 */     modify();
/*   96:     */     
/*   97: 245 */     this.nodeCount = 0;
/*   98: 246 */     this.rootNode[0] = null;
/*   99: 247 */     this.rootNode[1] = null;
/*  100:     */   }
/*  101:     */   
/*  102:     */   public Object getKey(Object value)
/*  103:     */   {
/*  104: 264 */     return doGet((Comparable)value, 1);
/*  105:     */   }
/*  106:     */   
/*  107:     */   public Object removeValue(Object value)
/*  108:     */   {
/*  109: 279 */     return doRemove((Comparable)value, 1);
/*  110:     */   }
/*  111:     */   
/*  112:     */   public Object firstKey()
/*  113:     */   {
/*  114: 290 */     if (this.nodeCount == 0) {
/*  115: 291 */       throw new NoSuchElementException("Map is empty");
/*  116:     */     }
/*  117: 293 */     return leastNode(this.rootNode[0], 0).getKey();
/*  118:     */   }
/*  119:     */   
/*  120:     */   public Object lastKey()
/*  121:     */   {
/*  122: 303 */     if (this.nodeCount == 0) {
/*  123: 304 */       throw new NoSuchElementException("Map is empty");
/*  124:     */     }
/*  125: 306 */     return greatestNode(this.rootNode[0], 0).getKey();
/*  126:     */   }
/*  127:     */   
/*  128:     */   public Object nextKey(Object key)
/*  129:     */   {
/*  130: 318 */     checkKey(key);
/*  131: 319 */     Node node = nextGreater(lookup((Comparable)key, 0), 0);
/*  132: 320 */     return node == null ? null : node.getKey();
/*  133:     */   }
/*  134:     */   
/*  135:     */   public Object previousKey(Object key)
/*  136:     */   {
/*  137: 332 */     checkKey(key);
/*  138: 333 */     Node node = nextSmaller(lookup((Comparable)key, 0), 0);
/*  139: 334 */     return node == null ? null : node.getKey();
/*  140:     */   }
/*  141:     */   
/*  142:     */   public Set keySet()
/*  143:     */   {
/*  144: 351 */     if (this.keySet == null) {
/*  145: 352 */       this.keySet = new View(this, 0, 0);
/*  146:     */     }
/*  147: 354 */     return this.keySet;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public Collection values()
/*  151:     */   {
/*  152: 372 */     if (this.valuesSet == null) {
/*  153: 373 */       this.valuesSet = new View(this, 0, 1);
/*  154:     */     }
/*  155: 375 */     return this.valuesSet;
/*  156:     */   }
/*  157:     */   
/*  158:     */   public Set entrySet()
/*  159:     */   {
/*  160: 394 */     if (this.entrySet == null) {
/*  161: 395 */       return new EntryView(this, 0, 2);
/*  162:     */     }
/*  163: 397 */     return this.entrySet;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public MapIterator mapIterator()
/*  167:     */   {
/*  168: 409 */     if (isEmpty()) {
/*  169: 410 */       return EmptyOrderedMapIterator.INSTANCE;
/*  170:     */     }
/*  171: 412 */     return new ViewMapIterator(this, 0);
/*  172:     */   }
/*  173:     */   
/*  174:     */   public OrderedMapIterator orderedMapIterator()
/*  175:     */   {
/*  176: 423 */     if (isEmpty()) {
/*  177: 424 */       return EmptyOrderedMapIterator.INSTANCE;
/*  178:     */     }
/*  179: 426 */     return new ViewMapIterator(this, 0);
/*  180:     */   }
/*  181:     */   
/*  182:     */   public BidiMap inverseBidiMap()
/*  183:     */   {
/*  184: 436 */     return inverseOrderedBidiMap();
/*  185:     */   }
/*  186:     */   
/*  187:     */   public OrderedBidiMap inverseOrderedBidiMap()
/*  188:     */   {
/*  189: 445 */     if (this.inverse == null) {
/*  190: 446 */       this.inverse = new Inverse(this);
/*  191:     */     }
/*  192: 448 */     return this.inverse;
/*  193:     */   }
/*  194:     */   
/*  195:     */   public boolean equals(Object obj)
/*  196:     */   {
/*  197: 459 */     return doEquals(obj, 0);
/*  198:     */   }
/*  199:     */   
/*  200:     */   public int hashCode()
/*  201:     */   {
/*  202: 468 */     return doHashCode(0);
/*  203:     */   }
/*  204:     */   
/*  205:     */   public String toString()
/*  206:     */   {
/*  207: 477 */     return doToString(0);
/*  208:     */   }
/*  209:     */   
/*  210:     */   private Object doGet(Comparable obj, int index)
/*  211:     */   {
/*  212: 491 */     checkNonNullComparable(obj, index);
/*  213: 492 */     Node node = lookup(obj, index);
/*  214: 493 */     return node == null ? null : node.getData(oppositeIndex(index));
/*  215:     */   }
/*  216:     */   
/*  217:     */   private Object doPut(Comparable key, Comparable value, int index)
/*  218:     */   {
/*  219: 505 */     checkKeyAndValue(key, value);
/*  220:     */     
/*  221:     */ 
/*  222: 508 */     Object prev = index == 0 ? doGet(key, 0) : doGet(value, 1);
/*  223: 509 */     doRemove(key, 0);
/*  224: 510 */     doRemove(value, 1);
/*  225:     */     
/*  226: 512 */     Node node = this.rootNode[0];
/*  227: 513 */     if (node == null)
/*  228:     */     {
/*  229: 515 */       Node root = new Node(key, value);
/*  230: 516 */       this.rootNode[0] = root;
/*  231: 517 */       this.rootNode[1] = root;
/*  232: 518 */       grow();
/*  233:     */     }
/*  234:     */     else
/*  235:     */     {
/*  236:     */       for (;;)
/*  237:     */       {
/*  238: 523 */         int cmp = compare(key, node.getData(0));
/*  239: 525 */         if (cmp == 0) {
/*  240: 527 */           throw new IllegalArgumentException("Cannot store a duplicate key (\"" + key + "\") in this Map");
/*  241:     */         }
/*  242: 528 */         if (cmp < 0)
/*  243:     */         {
/*  244: 529 */           if (node.getLeft(0) != null)
/*  245:     */           {
/*  246: 530 */             node = node.getLeft(0);
/*  247:     */           }
/*  248:     */           else
/*  249:     */           {
/*  250: 532 */             Node newNode = new Node(key, value);
/*  251:     */             
/*  252: 534 */             insertValue(newNode);
/*  253: 535 */             node.setLeft(newNode, 0);
/*  254: 536 */             newNode.setParent(node, 0);
/*  255: 537 */             doRedBlackInsert(newNode, 0);
/*  256: 538 */             grow();
/*  257:     */             
/*  258: 540 */             break;
/*  259:     */           }
/*  260:     */         }
/*  261: 543 */         else if (node.getRight(0) != null)
/*  262:     */         {
/*  263: 544 */           node = node.getRight(0);
/*  264:     */         }
/*  265:     */         else
/*  266:     */         {
/*  267: 546 */           Node newNode = new Node(key, value);
/*  268:     */           
/*  269: 548 */           insertValue(newNode);
/*  270: 549 */           node.setRight(newNode, 0);
/*  271: 550 */           newNode.setParent(node, 0);
/*  272: 551 */           doRedBlackInsert(newNode, 0);
/*  273: 552 */           grow();
/*  274:     */           
/*  275: 554 */           break;
/*  276:     */         }
/*  277:     */       }
/*  278:     */     }
/*  279: 559 */     return prev;
/*  280:     */   }
/*  281:     */   
/*  282:     */   private Object doRemove(Comparable o, int index)
/*  283:     */   {
/*  284: 573 */     Node node = lookup(o, index);
/*  285: 574 */     Object rval = null;
/*  286: 575 */     if (node != null)
/*  287:     */     {
/*  288: 576 */       rval = node.getData(oppositeIndex(index));
/*  289: 577 */       doRedBlackDelete(node);
/*  290:     */     }
/*  291: 579 */     return rval;
/*  292:     */   }
/*  293:     */   
/*  294:     */   private Node lookup(Comparable data, int index)
/*  295:     */   {
/*  296: 591 */     Node rval = null;
/*  297: 592 */     Node node = this.rootNode[index];
/*  298: 594 */     while (node != null)
/*  299:     */     {
/*  300: 595 */       int cmp = compare(data, node.getData(index));
/*  301: 596 */       if (cmp == 0)
/*  302:     */       {
/*  303: 597 */         rval = node;
/*  304: 598 */         break;
/*  305:     */       }
/*  306: 600 */       node = cmp < 0 ? node.getLeft(index) : node.getRight(index);
/*  307:     */     }
/*  308: 604 */     return rval;
/*  309:     */   }
/*  310:     */   
/*  311:     */   private Node nextGreater(Node node, int index)
/*  312:     */   {
/*  313: 615 */     Node rval = null;
/*  314: 616 */     if (node == null)
/*  315:     */     {
/*  316: 617 */       rval = null;
/*  317:     */     }
/*  318: 618 */     else if (node.getRight(index) != null)
/*  319:     */     {
/*  320: 621 */       rval = leastNode(node.getRight(index), index);
/*  321:     */     }
/*  322:     */     else
/*  323:     */     {
/*  324: 629 */       Node parent = node.getParent(index);
/*  325: 630 */       Node child = node;
/*  326: 632 */       while ((parent != null) && (child == parent.getRight(index)))
/*  327:     */       {
/*  328: 633 */         child = parent;
/*  329: 634 */         parent = parent.getParent(index);
/*  330:     */       }
/*  331: 636 */       rval = parent;
/*  332:     */     }
/*  333: 638 */     return rval;
/*  334:     */   }
/*  335:     */   
/*  336:     */   private Node nextSmaller(Node node, int index)
/*  337:     */   {
/*  338: 649 */     Node rval = null;
/*  339: 650 */     if (node == null)
/*  340:     */     {
/*  341: 651 */       rval = null;
/*  342:     */     }
/*  343: 652 */     else if (node.getLeft(index) != null)
/*  344:     */     {
/*  345: 655 */       rval = greatestNode(node.getLeft(index), index);
/*  346:     */     }
/*  347:     */     else
/*  348:     */     {
/*  349: 663 */       Node parent = node.getParent(index);
/*  350: 664 */       Node child = node;
/*  351: 666 */       while ((parent != null) && (child == parent.getLeft(index)))
/*  352:     */       {
/*  353: 667 */         child = parent;
/*  354: 668 */         parent = parent.getParent(index);
/*  355:     */       }
/*  356: 670 */       rval = parent;
/*  357:     */     }
/*  358: 672 */     return rval;
/*  359:     */   }
/*  360:     */   
/*  361:     */   private static int oppositeIndex(int index)
/*  362:     */   {
/*  363: 686 */     return 1 - index;
/*  364:     */   }
/*  365:     */   
/*  366:     */   private static int compare(Comparable o1, Comparable o2)
/*  367:     */   {
/*  368: 699 */     return o1.compareTo(o2);
/*  369:     */   }
/*  370:     */   
/*  371:     */   private static Node leastNode(Node node, int index)
/*  372:     */   {
/*  373: 711 */     Node rval = node;
/*  374: 712 */     if (rval != null) {
/*  375: 713 */       while (rval.getLeft(index) != null) {
/*  376: 714 */         rval = rval.getLeft(index);
/*  377:     */       }
/*  378:     */     }
/*  379: 717 */     return rval;
/*  380:     */   }
/*  381:     */   
/*  382:     */   private static Node greatestNode(Node node, int index)
/*  383:     */   {
/*  384: 728 */     Node rval = node;
/*  385: 729 */     if (rval != null) {
/*  386: 730 */       while (rval.getRight(index) != null) {
/*  387: 731 */         rval = rval.getRight(index);
/*  388:     */       }
/*  389:     */     }
/*  390: 734 */     return rval;
/*  391:     */   }
/*  392:     */   
/*  393:     */   private static void copyColor(Node from, Node to, int index)
/*  394:     */   {
/*  395: 746 */     if (to != null) {
/*  396: 747 */       if (from == null) {
/*  397: 749 */         to.setBlack(index);
/*  398:     */       } else {
/*  399: 751 */         to.copyColor(from, index);
/*  400:     */       }
/*  401:     */     }
/*  402:     */   }
/*  403:     */   
/*  404:     */   private static boolean isRed(Node node, int index)
/*  405:     */   {
/*  406: 764 */     return node == null ? false : node.isRed(index);
/*  407:     */   }
/*  408:     */   
/*  409:     */   private static boolean isBlack(Node node, int index)
/*  410:     */   {
/*  411: 775 */     return node == null ? true : node.isBlack(index);
/*  412:     */   }
/*  413:     */   
/*  414:     */   private static void makeRed(Node node, int index)
/*  415:     */   {
/*  416: 785 */     if (node != null) {
/*  417: 786 */       node.setRed(index);
/*  418:     */     }
/*  419:     */   }
/*  420:     */   
/*  421:     */   private static void makeBlack(Node node, int index)
/*  422:     */   {
/*  423: 797 */     if (node != null) {
/*  424: 798 */       node.setBlack(index);
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   private static Node getGrandParent(Node node, int index)
/*  429:     */   {
/*  430: 810 */     return getParent(getParent(node, index), index);
/*  431:     */   }
/*  432:     */   
/*  433:     */   private static Node getParent(Node node, int index)
/*  434:     */   {
/*  435: 821 */     return node == null ? null : node.getParent(index);
/*  436:     */   }
/*  437:     */   
/*  438:     */   private static Node getRightChild(Node node, int index)
/*  439:     */   {
/*  440: 832 */     return node == null ? null : node.getRight(index);
/*  441:     */   }
/*  442:     */   
/*  443:     */   private static Node getLeftChild(Node node, int index)
/*  444:     */   {
/*  445: 843 */     return node == null ? null : node.getLeft(index);
/*  446:     */   }
/*  447:     */   
/*  448:     */   private static boolean isLeftChild(Node node, int index)
/*  449:     */   {
/*  450: 858 */     return node == null;
/*  451:     */   }
/*  452:     */   
/*  453:     */   private static boolean isRightChild(Node node, int index)
/*  454:     */   {
/*  455: 876 */     return node == null;
/*  456:     */   }
/*  457:     */   
/*  458:     */   private void rotateLeft(Node node, int index)
/*  459:     */   {
/*  460: 889 */     Node rightChild = node.getRight(index);
/*  461: 890 */     node.setRight(Node.access$100(rightChild, index), index);
/*  462: 892 */     if (rightChild.getLeft(index) != null) {
/*  463: 893 */       Node.access$100(rightChild, index).setParent(node, index);
/*  464:     */     }
/*  465: 895 */     rightChild.setParent(Node.access$600(node, index), index);
/*  466: 897 */     if (node.getParent(index) == null) {
/*  467: 899 */       this.rootNode[index] = rightChild;
/*  468: 900 */     } else if (Node.access$600(node, index).getLeft(index) == node) {
/*  469: 901 */       Node.access$600(node, index).setLeft(rightChild, index);
/*  470:     */     } else {
/*  471: 903 */       Node.access$600(node, index).setRight(rightChild, index);
/*  472:     */     }
/*  473: 906 */     rightChild.setLeft(node, index);
/*  474: 907 */     node.setParent(rightChild, index);
/*  475:     */   }
/*  476:     */   
/*  477:     */   private void rotateRight(Node node, int index)
/*  478:     */   {
/*  479: 917 */     Node leftChild = node.getLeft(index);
/*  480: 918 */     node.setLeft(Node.access$400(leftChild, index), index);
/*  481: 919 */     if (leftChild.getRight(index) != null) {
/*  482: 920 */       Node.access$400(leftChild, index).setParent(node, index);
/*  483:     */     }
/*  484: 922 */     leftChild.setParent(Node.access$600(node, index), index);
/*  485: 924 */     if (node.getParent(index) == null) {
/*  486: 926 */       this.rootNode[index] = leftChild;
/*  487: 927 */     } else if (Node.access$600(node, index).getRight(index) == node) {
/*  488: 928 */       Node.access$600(node, index).setRight(leftChild, index);
/*  489:     */     } else {
/*  490: 930 */       Node.access$600(node, index).setLeft(leftChild, index);
/*  491:     */     }
/*  492: 933 */     leftChild.setRight(node, index);
/*  493: 934 */     node.setParent(leftChild, index);
/*  494:     */   }
/*  495:     */   
/*  496:     */   private void doRedBlackInsert(Node insertedNode, int index)
/*  497:     */   {
/*  498: 945 */     Node currentNode = insertedNode;
/*  499: 946 */     makeRed(currentNode, index);
/*  500: 950 */     while ((currentNode != null) && (currentNode != this.rootNode[index]) && (isRed(currentNode.getParent(index), index))) {
/*  501: 951 */       if (isLeftChild(getParent(currentNode, index), index))
/*  502:     */       {
/*  503: 952 */         Node y = getRightChild(getGrandParent(currentNode, index), index);
/*  504: 954 */         if (isRed(y, index))
/*  505:     */         {
/*  506: 955 */           makeBlack(getParent(currentNode, index), index);
/*  507: 956 */           makeBlack(y, index);
/*  508: 957 */           makeRed(getGrandParent(currentNode, index), index);
/*  509:     */           
/*  510: 959 */           currentNode = getGrandParent(currentNode, index);
/*  511:     */         }
/*  512:     */         else
/*  513:     */         {
/*  514: 961 */           if (isRightChild(currentNode, index))
/*  515:     */           {
/*  516: 962 */             currentNode = getParent(currentNode, index);
/*  517:     */             
/*  518: 964 */             rotateLeft(currentNode, index);
/*  519:     */           }
/*  520: 967 */           makeBlack(getParent(currentNode, index), index);
/*  521: 968 */           makeRed(getGrandParent(currentNode, index), index);
/*  522: 970 */           if (getGrandParent(currentNode, index) != null) {
/*  523: 971 */             rotateRight(getGrandParent(currentNode, index), index);
/*  524:     */           }
/*  525:     */         }
/*  526:     */       }
/*  527:     */       else
/*  528:     */       {
/*  529: 977 */         Node y = getLeftChild(getGrandParent(currentNode, index), index);
/*  530: 979 */         if (isRed(y, index))
/*  531:     */         {
/*  532: 980 */           makeBlack(getParent(currentNode, index), index);
/*  533: 981 */           makeBlack(y, index);
/*  534: 982 */           makeRed(getGrandParent(currentNode, index), index);
/*  535:     */           
/*  536: 984 */           currentNode = getGrandParent(currentNode, index);
/*  537:     */         }
/*  538:     */         else
/*  539:     */         {
/*  540: 986 */           if (isLeftChild(currentNode, index))
/*  541:     */           {
/*  542: 987 */             currentNode = getParent(currentNode, index);
/*  543:     */             
/*  544: 989 */             rotateRight(currentNode, index);
/*  545:     */           }
/*  546: 992 */           makeBlack(getParent(currentNode, index), index);
/*  547: 993 */           makeRed(getGrandParent(currentNode, index), index);
/*  548: 995 */           if (getGrandParent(currentNode, index) != null) {
/*  549: 996 */             rotateLeft(getGrandParent(currentNode, index), index);
/*  550:     */           }
/*  551:     */         }
/*  552:     */       }
/*  553:     */     }
/*  554:1002 */     makeBlack(this.rootNode[index], index);
/*  555:     */   }
/*  556:     */   
/*  557:     */   private void doRedBlackDelete(Node deletedNode)
/*  558:     */   {
/*  559:1012 */     for (int index = 0; index < 2; index++)
/*  560:     */     {
/*  561:1015 */       if ((deletedNode.getLeft(index) != null) && (deletedNode.getRight(index) != null)) {
/*  562:1016 */         swapPosition(nextGreater(deletedNode, index), deletedNode, index);
/*  563:     */       }
/*  564:1019 */       Node replacement = deletedNode.getLeft(index) != null ? deletedNode.getLeft(index) : deletedNode.getRight(index);
/*  565:1022 */       if (replacement != null)
/*  566:     */       {
/*  567:1023 */         replacement.setParent(Node.access$600(deletedNode, index), index);
/*  568:1025 */         if (deletedNode.getParent(index) == null) {
/*  569:1026 */           this.rootNode[index] = replacement;
/*  570:1027 */         } else if (deletedNode == Node.access$600(deletedNode, index).getLeft(index)) {
/*  571:1028 */           Node.access$600(deletedNode, index).setLeft(replacement, index);
/*  572:     */         } else {
/*  573:1030 */           Node.access$600(deletedNode, index).setRight(replacement, index);
/*  574:     */         }
/*  575:1033 */         deletedNode.setLeft(null, index);
/*  576:1034 */         deletedNode.setRight(null, index);
/*  577:1035 */         deletedNode.setParent(null, index);
/*  578:1037 */         if (isBlack(deletedNode, index)) {
/*  579:1038 */           doRedBlackDeleteFixup(replacement, index);
/*  580:     */         }
/*  581:     */       }
/*  582:1043 */       else if (deletedNode.getParent(index) == null)
/*  583:     */       {
/*  584:1046 */         this.rootNode[index] = null;
/*  585:     */       }
/*  586:     */       else
/*  587:     */       {
/*  588:1050 */         if (isBlack(deletedNode, index)) {
/*  589:1051 */           doRedBlackDeleteFixup(deletedNode, index);
/*  590:     */         }
/*  591:1054 */         if (deletedNode.getParent(index) != null)
/*  592:     */         {
/*  593:1055 */           if (deletedNode == Node.access$600(deletedNode, index).getLeft(index)) {
/*  594:1056 */             Node.access$600(deletedNode, index).setLeft(null, index);
/*  595:     */           } else {
/*  596:1058 */             Node.access$600(deletedNode, index).setRight(null, index);
/*  597:     */           }
/*  598:1061 */           deletedNode.setParent(null, index);
/*  599:     */         }
/*  600:     */       }
/*  601:     */     }
/*  602:1066 */     shrink();
/*  603:     */   }
/*  604:     */   
/*  605:     */   private void doRedBlackDeleteFixup(Node replacementNode, int index)
/*  606:     */   {
/*  607:1079 */     Node currentNode = replacementNode;
/*  608:1081 */     while ((currentNode != this.rootNode[index]) && (isBlack(currentNode, index))) {
/*  609:1082 */       if (isLeftChild(currentNode, index))
/*  610:     */       {
/*  611:1083 */         Node siblingNode = getRightChild(getParent(currentNode, index), index);
/*  612:1085 */         if (isRed(siblingNode, index))
/*  613:     */         {
/*  614:1086 */           makeBlack(siblingNode, index);
/*  615:1087 */           makeRed(getParent(currentNode, index), index);
/*  616:1088 */           rotateLeft(getParent(currentNode, index), index);
/*  617:     */           
/*  618:1090 */           siblingNode = getRightChild(getParent(currentNode, index), index);
/*  619:     */         }
/*  620:1093 */         if ((isBlack(getLeftChild(siblingNode, index), index)) && (isBlack(getRightChild(siblingNode, index), index)))
/*  621:     */         {
/*  622:1095 */           makeRed(siblingNode, index);
/*  623:     */           
/*  624:1097 */           currentNode = getParent(currentNode, index);
/*  625:     */         }
/*  626:     */         else
/*  627:     */         {
/*  628:1099 */           if (isBlack(getRightChild(siblingNode, index), index))
/*  629:     */           {
/*  630:1100 */             makeBlack(getLeftChild(siblingNode, index), index);
/*  631:1101 */             makeRed(siblingNode, index);
/*  632:1102 */             rotateRight(siblingNode, index);
/*  633:     */             
/*  634:1104 */             siblingNode = getRightChild(getParent(currentNode, index), index);
/*  635:     */           }
/*  636:1107 */           copyColor(getParent(currentNode, index), siblingNode, index);
/*  637:1108 */           makeBlack(getParent(currentNode, index), index);
/*  638:1109 */           makeBlack(getRightChild(siblingNode, index), index);
/*  639:1110 */           rotateLeft(getParent(currentNode, index), index);
/*  640:     */           
/*  641:1112 */           currentNode = this.rootNode[index];
/*  642:     */         }
/*  643:     */       }
/*  644:     */       else
/*  645:     */       {
/*  646:1115 */         Node siblingNode = getLeftChild(getParent(currentNode, index), index);
/*  647:1117 */         if (isRed(siblingNode, index))
/*  648:     */         {
/*  649:1118 */           makeBlack(siblingNode, index);
/*  650:1119 */           makeRed(getParent(currentNode, index), index);
/*  651:1120 */           rotateRight(getParent(currentNode, index), index);
/*  652:     */           
/*  653:1122 */           siblingNode = getLeftChild(getParent(currentNode, index), index);
/*  654:     */         }
/*  655:1125 */         if ((isBlack(getRightChild(siblingNode, index), index)) && (isBlack(getLeftChild(siblingNode, index), index)))
/*  656:     */         {
/*  657:1127 */           makeRed(siblingNode, index);
/*  658:     */           
/*  659:1129 */           currentNode = getParent(currentNode, index);
/*  660:     */         }
/*  661:     */         else
/*  662:     */         {
/*  663:1131 */           if (isBlack(getLeftChild(siblingNode, index), index))
/*  664:     */           {
/*  665:1132 */             makeBlack(getRightChild(siblingNode, index), index);
/*  666:1133 */             makeRed(siblingNode, index);
/*  667:1134 */             rotateLeft(siblingNode, index);
/*  668:     */             
/*  669:1136 */             siblingNode = getLeftChild(getParent(currentNode, index), index);
/*  670:     */           }
/*  671:1139 */           copyColor(getParent(currentNode, index), siblingNode, index);
/*  672:1140 */           makeBlack(getParent(currentNode, index), index);
/*  673:1141 */           makeBlack(getLeftChild(siblingNode, index), index);
/*  674:1142 */           rotateRight(getParent(currentNode, index), index);
/*  675:     */           
/*  676:1144 */           currentNode = this.rootNode[index];
/*  677:     */         }
/*  678:     */       }
/*  679:     */     }
/*  680:1149 */     makeBlack(currentNode, index);
/*  681:     */   }
/*  682:     */   
/*  683:     */   private void swapPosition(Node x, Node y, int index)
/*  684:     */   {
/*  685:1163 */     Node xFormerParent = x.getParent(index);
/*  686:1164 */     Node xFormerLeftChild = x.getLeft(index);
/*  687:1165 */     Node xFormerRightChild = x.getRight(index);
/*  688:1166 */     Node yFormerParent = y.getParent(index);
/*  689:1167 */     Node yFormerLeftChild = y.getLeft(index);
/*  690:1168 */     Node yFormerRightChild = y.getRight(index);
/*  691:1169 */     boolean xWasLeftChild = (x.getParent(index) != null) && (x == Node.access$600(x, index).getLeft(index));
/*  692:1170 */     boolean yWasLeftChild = (y.getParent(index) != null) && (y == Node.access$600(y, index).getLeft(index));
/*  693:1173 */     if (x == yFormerParent)
/*  694:     */     {
/*  695:1174 */       x.setParent(y, index);
/*  696:1176 */       if (yWasLeftChild)
/*  697:     */       {
/*  698:1177 */         y.setLeft(x, index);
/*  699:1178 */         y.setRight(xFormerRightChild, index);
/*  700:     */       }
/*  701:     */       else
/*  702:     */       {
/*  703:1180 */         y.setRight(x, index);
/*  704:1181 */         y.setLeft(xFormerLeftChild, index);
/*  705:     */       }
/*  706:     */     }
/*  707:     */     else
/*  708:     */     {
/*  709:1184 */       x.setParent(yFormerParent, index);
/*  710:1186 */       if (yFormerParent != null) {
/*  711:1187 */         if (yWasLeftChild) {
/*  712:1188 */           yFormerParent.setLeft(x, index);
/*  713:     */         } else {
/*  714:1190 */           yFormerParent.setRight(x, index);
/*  715:     */         }
/*  716:     */       }
/*  717:1194 */       y.setLeft(xFormerLeftChild, index);
/*  718:1195 */       y.setRight(xFormerRightChild, index);
/*  719:     */     }
/*  720:1198 */     if (y == xFormerParent)
/*  721:     */     {
/*  722:1199 */       y.setParent(x, index);
/*  723:1201 */       if (xWasLeftChild)
/*  724:     */       {
/*  725:1202 */         x.setLeft(y, index);
/*  726:1203 */         x.setRight(yFormerRightChild, index);
/*  727:     */       }
/*  728:     */       else
/*  729:     */       {
/*  730:1205 */         x.setRight(y, index);
/*  731:1206 */         x.setLeft(yFormerLeftChild, index);
/*  732:     */       }
/*  733:     */     }
/*  734:     */     else
/*  735:     */     {
/*  736:1209 */       y.setParent(xFormerParent, index);
/*  737:1211 */       if (xFormerParent != null) {
/*  738:1212 */         if (xWasLeftChild) {
/*  739:1213 */           xFormerParent.setLeft(y, index);
/*  740:     */         } else {
/*  741:1215 */           xFormerParent.setRight(y, index);
/*  742:     */         }
/*  743:     */       }
/*  744:1219 */       x.setLeft(yFormerLeftChild, index);
/*  745:1220 */       x.setRight(yFormerRightChild, index);
/*  746:     */     }
/*  747:1224 */     if (x.getLeft(index) != null) {
/*  748:1225 */       Node.access$100(x, index).setParent(x, index);
/*  749:     */     }
/*  750:1228 */     if (x.getRight(index) != null) {
/*  751:1229 */       Node.access$400(x, index).setParent(x, index);
/*  752:     */     }
/*  753:1232 */     if (y.getLeft(index) != null) {
/*  754:1233 */       Node.access$100(y, index).setParent(y, index);
/*  755:     */     }
/*  756:1236 */     if (y.getRight(index) != null) {
/*  757:1237 */       Node.access$400(y, index).setParent(y, index);
/*  758:     */     }
/*  759:1240 */     x.swapColors(y, index);
/*  760:1243 */     if (this.rootNode[index] == x) {
/*  761:1244 */       this.rootNode[index] = y;
/*  762:1245 */     } else if (this.rootNode[index] == y) {
/*  763:1246 */       this.rootNode[index] = x;
/*  764:     */     }
/*  765:     */   }
/*  766:     */   
/*  767:     */   private static void checkNonNullComparable(Object o, int index)
/*  768:     */   {
/*  769:1262 */     if (o == null) {
/*  770:1263 */       throw new NullPointerException(dataName[index] + " cannot be null");
/*  771:     */     }
/*  772:1265 */     if (!(o instanceof Comparable)) {
/*  773:1266 */       throw new ClassCastException(dataName[index] + " must be Comparable");
/*  774:     */     }
/*  775:     */   }
/*  776:     */   
/*  777:     */   private static void checkKey(Object key)
/*  778:     */   {
/*  779:1279 */     checkNonNullComparable(key, 0);
/*  780:     */   }
/*  781:     */   
/*  782:     */   private static void checkValue(Object value)
/*  783:     */   {
/*  784:1291 */     checkNonNullComparable(value, 1);
/*  785:     */   }
/*  786:     */   
/*  787:     */   private static void checkKeyAndValue(Object key, Object value)
/*  788:     */   {
/*  789:1305 */     checkKey(key);
/*  790:1306 */     checkValue(value);
/*  791:     */   }
/*  792:     */   
/*  793:     */   private void modify()
/*  794:     */   {
/*  795:1315 */     this.modifications += 1;
/*  796:     */   }
/*  797:     */   
/*  798:     */   private void grow()
/*  799:     */   {
/*  800:1322 */     modify();
/*  801:1323 */     this.nodeCount += 1;
/*  802:     */   }
/*  803:     */   
/*  804:     */   private void shrink()
/*  805:     */   {
/*  806:1330 */     modify();
/*  807:1331 */     this.nodeCount -= 1;
/*  808:     */   }
/*  809:     */   
/*  810:     */   private void insertValue(Node newNode)
/*  811:     */     throws IllegalArgumentException
/*  812:     */   {
/*  813:1343 */     Node node = this.rootNode[1];
/*  814:     */     for (;;)
/*  815:     */     {
/*  816:1346 */       int cmp = compare(newNode.getData(1), node.getData(1));
/*  817:1348 */       if (cmp == 0) {
/*  818:1349 */         throw new IllegalArgumentException("Cannot store a duplicate value (\"" + newNode.getData(1) + "\") in this Map");
/*  819:     */       }
/*  820:1351 */       if (cmp < 0)
/*  821:     */       {
/*  822:1352 */         if (node.getLeft(1) != null)
/*  823:     */         {
/*  824:1353 */           node = node.getLeft(1);
/*  825:     */         }
/*  826:     */         else
/*  827:     */         {
/*  828:1355 */           node.setLeft(newNode, 1);
/*  829:1356 */           newNode.setParent(node, 1);
/*  830:1357 */           doRedBlackInsert(newNode, 1);
/*  831:     */           
/*  832:1359 */           break;
/*  833:     */         }
/*  834:     */       }
/*  835:1362 */       else if (node.getRight(1) != null)
/*  836:     */       {
/*  837:1363 */         node = node.getRight(1);
/*  838:     */       }
/*  839:     */       else
/*  840:     */       {
/*  841:1365 */         node.setRight(newNode, 1);
/*  842:1366 */         newNode.setParent(node, 1);
/*  843:1367 */         doRedBlackInsert(newNode, 1);
/*  844:     */         
/*  845:1369 */         break;
/*  846:     */       }
/*  847:     */     }
/*  848:     */   }
/*  849:     */   
/*  850:     */   private boolean doEquals(Object obj, int type)
/*  851:     */   {
/*  852:1384 */     if (obj == this) {
/*  853:1385 */       return true;
/*  854:     */     }
/*  855:1387 */     if (!(obj instanceof Map)) {
/*  856:1388 */       return false;
/*  857:     */     }
/*  858:1390 */     Map other = (Map)obj;
/*  859:1391 */     if (other.size() != size()) {
/*  860:1392 */       return false;
/*  861:     */     }
/*  862:1395 */     if (this.nodeCount > 0) {
/*  863:     */       try
/*  864:     */       {
/*  865:1397 */         for (it = new ViewMapIterator(this, type); it.hasNext();)
/*  866:     */         {
/*  867:1398 */           Object key = it.next();
/*  868:1399 */           Object value = it.getValue();
/*  869:1400 */           if (!value.equals(other.get(key))) {
/*  870:1401 */             return false;
/*  871:     */           }
/*  872:     */         }
/*  873:     */       }
/*  874:     */       catch (ClassCastException ex)
/*  875:     */       {
/*  876:     */         MapIterator it;
/*  877:1405 */         return false;
/*  878:     */       }
/*  879:     */       catch (NullPointerException ex)
/*  880:     */       {
/*  881:1407 */         return false;
/*  882:     */       }
/*  883:     */     }
/*  884:1410 */     return true;
/*  885:     */   }
/*  886:     */   
/*  887:     */   private int doHashCode(int type)
/*  888:     */   {
/*  889:1420 */     int total = 0;
/*  890:     */     MapIterator it;
/*  891:1421 */     if (this.nodeCount > 0) {
/*  892:1422 */       for (it = new ViewMapIterator(this, type); it.hasNext();)
/*  893:     */       {
/*  894:1423 */         Object key = it.next();
/*  895:1424 */         Object value = it.getValue();
/*  896:1425 */         total += (key.hashCode() ^ value.hashCode());
/*  897:     */       }
/*  898:     */     }
/*  899:1428 */     return total;
/*  900:     */   }
/*  901:     */   
/*  902:     */   private String doToString(int type)
/*  903:     */   {
/*  904:1438 */     if (this.nodeCount == 0) {
/*  905:1439 */       return "{}";
/*  906:     */     }
/*  907:1441 */     StringBuffer buf = new StringBuffer(this.nodeCount * 32);
/*  908:1442 */     buf.append('{');
/*  909:1443 */     MapIterator it = new ViewMapIterator(this, type);
/*  910:1444 */     boolean hasNext = it.hasNext();
/*  911:1445 */     while (hasNext)
/*  912:     */     {
/*  913:1446 */       Object key = it.next();
/*  914:1447 */       Object value = it.getValue();
/*  915:1448 */       buf.append(key == this ? "(this Map)" : key).append('=').append(value == this ? "(this Map)" : value);
/*  916:     */       
/*  917:     */ 
/*  918:     */ 
/*  919:1452 */       hasNext = it.hasNext();
/*  920:1453 */       if (hasNext) {
/*  921:1454 */         buf.append(", ");
/*  922:     */       }
/*  923:     */     }
/*  924:1458 */     buf.append('}');
/*  925:1459 */     return buf.toString();
/*  926:     */   }
/*  927:     */   
/*  928:     */   static class View
/*  929:     */     extends AbstractSet
/*  930:     */   {
/*  931:     */     protected final TreeBidiMap main;
/*  932:     */     protected final int orderType;
/*  933:     */     protected final int dataType;
/*  934:     */     
/*  935:     */     View(TreeBidiMap main, int orderType, int dataType)
/*  936:     */     {
/*  937:1484 */       this.main = main;
/*  938:1485 */       this.orderType = orderType;
/*  939:1486 */       this.dataType = dataType;
/*  940:     */     }
/*  941:     */     
/*  942:     */     public Iterator iterator()
/*  943:     */     {
/*  944:1490 */       return new TreeBidiMap.ViewIterator(this.main, this.orderType, this.dataType);
/*  945:     */     }
/*  946:     */     
/*  947:     */     public int size()
/*  948:     */     {
/*  949:1494 */       return this.main.size();
/*  950:     */     }
/*  951:     */     
/*  952:     */     public boolean contains(Object obj)
/*  953:     */     {
/*  954:1498 */       TreeBidiMap.checkNonNullComparable(obj, this.dataType);
/*  955:1499 */       return this.main.lookup((Comparable)obj, this.dataType) != null;
/*  956:     */     }
/*  957:     */     
/*  958:     */     public boolean remove(Object obj)
/*  959:     */     {
/*  960:1503 */       return this.main.doRemove((Comparable)obj, this.dataType) != null;
/*  961:     */     }
/*  962:     */     
/*  963:     */     public void clear()
/*  964:     */     {
/*  965:1507 */       this.main.clear();
/*  966:     */     }
/*  967:     */   }
/*  968:     */   
/*  969:     */   static class ViewIterator
/*  970:     */     implements OrderedIterator
/*  971:     */   {
/*  972:     */     protected final TreeBidiMap main;
/*  973:     */     protected final int orderType;
/*  974:     */     protected final int dataType;
/*  975:     */     protected TreeBidiMap.Node lastReturnedNode;
/*  976:     */     protected TreeBidiMap.Node nextNode;
/*  977:     */     protected TreeBidiMap.Node previousNode;
/*  978:     */     private int expectedModifications;
/*  979:     */     
/*  980:     */     ViewIterator(TreeBidiMap main, int orderType, int dataType)
/*  981:     */     {
/*  982:1541 */       this.main = main;
/*  983:1542 */       this.orderType = orderType;
/*  984:1543 */       this.dataType = dataType;
/*  985:1544 */       this.expectedModifications = main.modifications;
/*  986:1545 */       this.nextNode = TreeBidiMap.leastNode(main.rootNode[orderType], orderType);
/*  987:1546 */       this.lastReturnedNode = null;
/*  988:1547 */       this.previousNode = null;
/*  989:     */     }
/*  990:     */     
/*  991:     */     public final boolean hasNext()
/*  992:     */     {
/*  993:1551 */       return this.nextNode != null;
/*  994:     */     }
/*  995:     */     
/*  996:     */     public final Object next()
/*  997:     */     {
/*  998:1555 */       if (this.nextNode == null) {
/*  999:1556 */         throw new NoSuchElementException();
/* 1000:     */       }
/* 1001:1558 */       if (this.main.modifications != this.expectedModifications) {
/* 1002:1559 */         throw new ConcurrentModificationException();
/* 1003:     */       }
/* 1004:1561 */       this.lastReturnedNode = this.nextNode;
/* 1005:1562 */       this.previousNode = this.nextNode;
/* 1006:1563 */       this.nextNode = this.main.nextGreater(this.nextNode, this.orderType);
/* 1007:1564 */       return doGetData();
/* 1008:     */     }
/* 1009:     */     
/* 1010:     */     public boolean hasPrevious()
/* 1011:     */     {
/* 1012:1568 */       return this.previousNode != null;
/* 1013:     */     }
/* 1014:     */     
/* 1015:     */     public Object previous()
/* 1016:     */     {
/* 1017:1572 */       if (this.previousNode == null) {
/* 1018:1573 */         throw new NoSuchElementException();
/* 1019:     */       }
/* 1020:1575 */       if (this.main.modifications != this.expectedModifications) {
/* 1021:1576 */         throw new ConcurrentModificationException();
/* 1022:     */       }
/* 1023:1578 */       this.nextNode = this.lastReturnedNode;
/* 1024:1579 */       if (this.nextNode == null) {
/* 1025:1580 */         this.nextNode = this.main.nextGreater(this.previousNode, this.orderType);
/* 1026:     */       }
/* 1027:1582 */       this.lastReturnedNode = this.previousNode;
/* 1028:1583 */       this.previousNode = this.main.nextSmaller(this.previousNode, this.orderType);
/* 1029:1584 */       return doGetData();
/* 1030:     */     }
/* 1031:     */     
/* 1032:     */     protected Object doGetData()
/* 1033:     */     {
/* 1034:1592 */       switch (this.dataType)
/* 1035:     */       {
/* 1036:     */       case 0: 
/* 1037:1594 */         return this.lastReturnedNode.getKey();
/* 1038:     */       case 1: 
/* 1039:1596 */         return this.lastReturnedNode.getValue();
/* 1040:     */       case 2: 
/* 1041:1598 */         return this.lastReturnedNode;
/* 1042:     */       case 3: 
/* 1043:1600 */         return new UnmodifiableMapEntry(this.lastReturnedNode.getValue(), this.lastReturnedNode.getKey());
/* 1044:     */       }
/* 1045:1602 */       return null;
/* 1046:     */     }
/* 1047:     */     
/* 1048:     */     public final void remove()
/* 1049:     */     {
/* 1050:1606 */       if (this.lastReturnedNode == null) {
/* 1051:1607 */         throw new IllegalStateException();
/* 1052:     */       }
/* 1053:1609 */       if (this.main.modifications != this.expectedModifications) {
/* 1054:1610 */         throw new ConcurrentModificationException();
/* 1055:     */       }
/* 1056:1612 */       this.main.doRedBlackDelete(this.lastReturnedNode);
/* 1057:1613 */       this.expectedModifications += 1;
/* 1058:1614 */       this.lastReturnedNode = null;
/* 1059:1615 */       if (this.nextNode == null) {
/* 1060:1616 */         this.previousNode = TreeBidiMap.greatestNode(this.main.rootNode[this.orderType], this.orderType);
/* 1061:     */       } else {
/* 1062:1618 */         this.previousNode = this.main.nextSmaller(this.nextNode, this.orderType);
/* 1063:     */       }
/* 1064:     */     }
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   static class ViewMapIterator
/* 1068:     */     extends TreeBidiMap.ViewIterator
/* 1069:     */     implements OrderedMapIterator
/* 1070:     */   {
/* 1071:     */     private final int oppositeType;
/* 1072:     */     
/* 1073:     */     ViewMapIterator(TreeBidiMap main, int orderType)
/* 1074:     */     {
/* 1075:1638 */       super(orderType, orderType);
/* 1076:1639 */       this.oppositeType = TreeBidiMap.oppositeIndex(this.dataType);
/* 1077:     */     }
/* 1078:     */     
/* 1079:     */     public Object getKey()
/* 1080:     */     {
/* 1081:1643 */       if (this.lastReturnedNode == null) {
/* 1082:1644 */         throw new IllegalStateException("Iterator getKey() can only be called after next() and before remove()");
/* 1083:     */       }
/* 1084:1646 */       return this.lastReturnedNode.getData(this.dataType);
/* 1085:     */     }
/* 1086:     */     
/* 1087:     */     public Object getValue()
/* 1088:     */     {
/* 1089:1650 */       if (this.lastReturnedNode == null) {
/* 1090:1651 */         throw new IllegalStateException("Iterator getValue() can only be called after next() and before remove()");
/* 1091:     */       }
/* 1092:1653 */       return this.lastReturnedNode.getData(this.oppositeType);
/* 1093:     */     }
/* 1094:     */     
/* 1095:     */     public Object setValue(Object obj)
/* 1096:     */     {
/* 1097:1657 */       throw new UnsupportedOperationException();
/* 1098:     */     }
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   static class EntryView
/* 1102:     */     extends TreeBidiMap.View
/* 1103:     */   {
/* 1104:     */     private final int oppositeType;
/* 1105:     */     
/* 1106:     */     EntryView(TreeBidiMap main, int orderType, int dataType)
/* 1107:     */     {
/* 1108:1677 */       super(orderType, dataType);
/* 1109:1678 */       this.oppositeType = TreeBidiMap.oppositeIndex(orderType);
/* 1110:     */     }
/* 1111:     */     
/* 1112:     */     public boolean contains(Object obj)
/* 1113:     */     {
/* 1114:1682 */       if (!(obj instanceof Map.Entry)) {
/* 1115:1683 */         return false;
/* 1116:     */       }
/* 1117:1685 */       Map.Entry entry = (Map.Entry)obj;
/* 1118:1686 */       Object value = entry.getValue();
/* 1119:1687 */       TreeBidiMap.Node node = this.main.lookup((Comparable)entry.getKey(), this.orderType);
/* 1120:1688 */       return (node != null) && (node.getData(this.oppositeType).equals(value));
/* 1121:     */     }
/* 1122:     */     
/* 1123:     */     public boolean remove(Object obj)
/* 1124:     */     {
/* 1125:1692 */       if (!(obj instanceof Map.Entry)) {
/* 1126:1693 */         return false;
/* 1127:     */       }
/* 1128:1695 */       Map.Entry entry = (Map.Entry)obj;
/* 1129:1696 */       Object value = entry.getValue();
/* 1130:1697 */       TreeBidiMap.Node node = this.main.lookup((Comparable)entry.getKey(), this.orderType);
/* 1131:1698 */       if ((node != null) && (node.getData(this.oppositeType).equals(value)))
/* 1132:     */       {
/* 1133:1699 */         this.main.doRedBlackDelete(node);
/* 1134:1700 */         return true;
/* 1135:     */       }
/* 1136:1702 */       return false;
/* 1137:     */     }
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   static class Node
/* 1141:     */     implements Map.Entry, KeyValue
/* 1142:     */   {
/* 1143:     */     private Comparable[] data;
/* 1144:     */     private Node[] leftNode;
/* 1145:     */     private Node[] rightNode;
/* 1146:     */     private Node[] parentNode;
/* 1147:     */     private boolean[] blackColor;
/* 1148:     */     private int hashcodeValue;
/* 1149:     */     private boolean calculatedHashCode;
/* 1150:     */     
/* 1151:     */     Node(Comparable key, Comparable value)
/* 1152:     */     {
/* 1153:1729 */       this.data = new Comparable[] { key, value };
/* 1154:1730 */       this.leftNode = new Node[2];
/* 1155:1731 */       this.rightNode = new Node[2];
/* 1156:1732 */       this.parentNode = new Node[2];
/* 1157:1733 */       this.blackColor = new boolean[] { true, true };
/* 1158:1734 */       this.calculatedHashCode = false;
/* 1159:     */     }
/* 1160:     */     
/* 1161:     */     private Comparable getData(int index)
/* 1162:     */     {
/* 1163:1744 */       return this.data[index];
/* 1164:     */     }
/* 1165:     */     
/* 1166:     */     private void setLeft(Node node, int index)
/* 1167:     */     {
/* 1168:1754 */       this.leftNode[index] = node;
/* 1169:     */     }
/* 1170:     */     
/* 1171:     */     private Node getLeft(int index)
/* 1172:     */     {
/* 1173:1764 */       return this.leftNode[index];
/* 1174:     */     }
/* 1175:     */     
/* 1176:     */     private void setRight(Node node, int index)
/* 1177:     */     {
/* 1178:1774 */       this.rightNode[index] = node;
/* 1179:     */     }
/* 1180:     */     
/* 1181:     */     private Node getRight(int index)
/* 1182:     */     {
/* 1183:1784 */       return this.rightNode[index];
/* 1184:     */     }
/* 1185:     */     
/* 1186:     */     private void setParent(Node node, int index)
/* 1187:     */     {
/* 1188:1794 */       this.parentNode[index] = node;
/* 1189:     */     }
/* 1190:     */     
/* 1191:     */     private Node getParent(int index)
/* 1192:     */     {
/* 1193:1804 */       return this.parentNode[index];
/* 1194:     */     }
/* 1195:     */     
/* 1196:     */     private void swapColors(Node node, int index)
/* 1197:     */     {
/* 1198:1815 */       this.blackColor[index] ^= node.blackColor[index];
/* 1199:1816 */       node.blackColor[index] ^= this.blackColor[index];
/* 1200:1817 */       this.blackColor[index] ^= node.blackColor[index];
/* 1201:     */     }
/* 1202:     */     
/* 1203:     */     private boolean isBlack(int index)
/* 1204:     */     {
/* 1205:1827 */       return this.blackColor[index];
/* 1206:     */     }
/* 1207:     */     
/* 1208:     */     private boolean isRed(int index)
/* 1209:     */     {
/* 1210:1837 */       return this.blackColor[index] == 0;
/* 1211:     */     }
/* 1212:     */     
/* 1213:     */     private void setBlack(int index)
/* 1214:     */     {
/* 1215:1846 */       this.blackColor[index] = true;
/* 1216:     */     }
/* 1217:     */     
/* 1218:     */     private void setRed(int index)
/* 1219:     */     {
/* 1220:1855 */       this.blackColor[index] = false;
/* 1221:     */     }
/* 1222:     */     
/* 1223:     */     private void copyColor(Node node, int index)
/* 1224:     */     {
/* 1225:1865 */       this.blackColor[index] = node.blackColor[index];
/* 1226:     */     }
/* 1227:     */     
/* 1228:     */     public Object getKey()
/* 1229:     */     {
/* 1230:1875 */       return this.data[0];
/* 1231:     */     }
/* 1232:     */     
/* 1233:     */     public Object getValue()
/* 1234:     */     {
/* 1235:1884 */       return this.data[1];
/* 1236:     */     }
/* 1237:     */     
/* 1238:     */     public Object setValue(Object ignored)
/* 1239:     */       throws UnsupportedOperationException
/* 1240:     */     {
/* 1241:1896 */       throw new UnsupportedOperationException("Map.Entry.setValue is not supported");
/* 1242:     */     }
/* 1243:     */     
/* 1244:     */     public boolean equals(Object obj)
/* 1245:     */     {
/* 1246:1909 */       if (obj == this) {
/* 1247:1910 */         return true;
/* 1248:     */       }
/* 1249:1912 */       if (!(obj instanceof Map.Entry)) {
/* 1250:1913 */         return false;
/* 1251:     */       }
/* 1252:1915 */       Map.Entry e = (Map.Entry)obj;
/* 1253:1916 */       return (this.data[0].equals(e.getKey())) && (this.data[1].equals(e.getValue()));
/* 1254:     */     }
/* 1255:     */     
/* 1256:     */     public int hashCode()
/* 1257:     */     {
/* 1258:1923 */       if (!this.calculatedHashCode)
/* 1259:     */       {
/* 1260:1924 */         this.hashcodeValue = (this.data[0].hashCode() ^ this.data[1].hashCode());
/* 1261:1925 */         this.calculatedHashCode = true;
/* 1262:     */       }
/* 1263:1927 */       return this.hashcodeValue;
/* 1264:     */     }
/* 1265:     */   }
/* 1266:     */   
/* 1267:     */   static class Inverse
/* 1268:     */     implements OrderedBidiMap
/* 1269:     */   {
/* 1270:     */     private final TreeBidiMap main;
/* 1271:     */     private Set keySet;
/* 1272:     */     private Set valuesSet;
/* 1273:     */     private Set entrySet;
/* 1274:     */     
/* 1275:     */     Inverse(TreeBidiMap main)
/* 1276:     */     {
/* 1277:1952 */       this.main = main;
/* 1278:     */     }
/* 1279:     */     
/* 1280:     */     public int size()
/* 1281:     */     {
/* 1282:1956 */       return this.main.size();
/* 1283:     */     }
/* 1284:     */     
/* 1285:     */     public boolean isEmpty()
/* 1286:     */     {
/* 1287:1960 */       return this.main.isEmpty();
/* 1288:     */     }
/* 1289:     */     
/* 1290:     */     public Object get(Object key)
/* 1291:     */     {
/* 1292:1964 */       return this.main.getKey(key);
/* 1293:     */     }
/* 1294:     */     
/* 1295:     */     public Object getKey(Object value)
/* 1296:     */     {
/* 1297:1968 */       return this.main.get(value);
/* 1298:     */     }
/* 1299:     */     
/* 1300:     */     public boolean containsKey(Object key)
/* 1301:     */     {
/* 1302:1972 */       return this.main.containsValue(key);
/* 1303:     */     }
/* 1304:     */     
/* 1305:     */     public boolean containsValue(Object value)
/* 1306:     */     {
/* 1307:1976 */       return this.main.containsKey(value);
/* 1308:     */     }
/* 1309:     */     
/* 1310:     */     public Object firstKey()
/* 1311:     */     {
/* 1312:1980 */       if (this.main.nodeCount == 0) {
/* 1313:1981 */         throw new NoSuchElementException("Map is empty");
/* 1314:     */       }
/* 1315:1983 */       return TreeBidiMap.leastNode(this.main.rootNode[1], 1).getValue();
/* 1316:     */     }
/* 1317:     */     
/* 1318:     */     public Object lastKey()
/* 1319:     */     {
/* 1320:1987 */       if (this.main.nodeCount == 0) {
/* 1321:1988 */         throw new NoSuchElementException("Map is empty");
/* 1322:     */       }
/* 1323:1990 */       return TreeBidiMap.greatestNode(this.main.rootNode[1], 1).getValue();
/* 1324:     */     }
/* 1325:     */     
/* 1326:     */     public Object nextKey(Object key)
/* 1327:     */     {
/* 1328:1994 */       TreeBidiMap.checkKey(key);
/* 1329:1995 */       TreeBidiMap.Node node = this.main.nextGreater(TreeBidiMap.access$1400(this.main, (Comparable)key, 1), 1);
/* 1330:1996 */       return node == null ? null : node.getValue();
/* 1331:     */     }
/* 1332:     */     
/* 1333:     */     public Object previousKey(Object key)
/* 1334:     */     {
/* 1335:2000 */       TreeBidiMap.checkKey(key);
/* 1336:2001 */       TreeBidiMap.Node node = this.main.nextSmaller(TreeBidiMap.access$1400(this.main, (Comparable)key, 1), 1);
/* 1337:2002 */       return node == null ? null : node.getValue();
/* 1338:     */     }
/* 1339:     */     
/* 1340:     */     public Object put(Object key, Object value)
/* 1341:     */     {
/* 1342:2006 */       return this.main.doPut((Comparable)value, (Comparable)key, 1);
/* 1343:     */     }
/* 1344:     */     
/* 1345:     */     public void putAll(Map map)
/* 1346:     */     {
/* 1347:2010 */       Iterator it = map.entrySet().iterator();
/* 1348:2011 */       while (it.hasNext())
/* 1349:     */       {
/* 1350:2012 */         Map.Entry entry = (Map.Entry)it.next();
/* 1351:2013 */         put(entry.getKey(), entry.getValue());
/* 1352:     */       }
/* 1353:     */     }
/* 1354:     */     
/* 1355:     */     public Object remove(Object key)
/* 1356:     */     {
/* 1357:2018 */       return this.main.removeValue(key);
/* 1358:     */     }
/* 1359:     */     
/* 1360:     */     public Object removeValue(Object value)
/* 1361:     */     {
/* 1362:2022 */       return this.main.remove(value);
/* 1363:     */     }
/* 1364:     */     
/* 1365:     */     public void clear()
/* 1366:     */     {
/* 1367:2026 */       this.main.clear();
/* 1368:     */     }
/* 1369:     */     
/* 1370:     */     public Set keySet()
/* 1371:     */     {
/* 1372:2030 */       if (this.keySet == null) {
/* 1373:2031 */         this.keySet = new TreeBidiMap.View(this.main, 1, 1);
/* 1374:     */       }
/* 1375:2033 */       return this.keySet;
/* 1376:     */     }
/* 1377:     */     
/* 1378:     */     public Collection values()
/* 1379:     */     {
/* 1380:2037 */       if (this.valuesSet == null) {
/* 1381:2038 */         this.valuesSet = new TreeBidiMap.View(this.main, 1, 0);
/* 1382:     */       }
/* 1383:2040 */       return this.valuesSet;
/* 1384:     */     }
/* 1385:     */     
/* 1386:     */     public Set entrySet()
/* 1387:     */     {
/* 1388:2044 */       if (this.entrySet == null) {
/* 1389:2045 */         return new TreeBidiMap.EntryView(this.main, 1, 3);
/* 1390:     */       }
/* 1391:2047 */       return this.entrySet;
/* 1392:     */     }
/* 1393:     */     
/* 1394:     */     public MapIterator mapIterator()
/* 1395:     */     {
/* 1396:2051 */       if (isEmpty()) {
/* 1397:2052 */         return EmptyOrderedMapIterator.INSTANCE;
/* 1398:     */       }
/* 1399:2054 */       return new TreeBidiMap.ViewMapIterator(this.main, 1);
/* 1400:     */     }
/* 1401:     */     
/* 1402:     */     public OrderedMapIterator orderedMapIterator()
/* 1403:     */     {
/* 1404:2058 */       if (isEmpty()) {
/* 1405:2059 */         return EmptyOrderedMapIterator.INSTANCE;
/* 1406:     */       }
/* 1407:2061 */       return new TreeBidiMap.ViewMapIterator(this.main, 1);
/* 1408:     */     }
/* 1409:     */     
/* 1410:     */     public BidiMap inverseBidiMap()
/* 1411:     */     {
/* 1412:2065 */       return this.main;
/* 1413:     */     }
/* 1414:     */     
/* 1415:     */     public OrderedBidiMap inverseOrderedBidiMap()
/* 1416:     */     {
/* 1417:2069 */       return this.main;
/* 1418:     */     }
/* 1419:     */     
/* 1420:     */     public boolean equals(Object obj)
/* 1421:     */     {
/* 1422:2073 */       return this.main.doEquals(obj, 1);
/* 1423:     */     }
/* 1424:     */     
/* 1425:     */     public int hashCode()
/* 1426:     */     {
/* 1427:2077 */       return this.main.doHashCode(1);
/* 1428:     */     }
/* 1429:     */     
/* 1430:     */     public String toString()
/* 1431:     */     {
/* 1432:2081 */       return this.main.doToString(1);
/* 1433:     */     }
/* 1434:     */   }
/* 1435:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.TreeBidiMap
 * JD-Core Version:    0.7.0.1
 */