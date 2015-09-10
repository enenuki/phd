/*    1:     */ package org.apache.commons.collections.list;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.ObjectOutputStream;
/*    6:     */ import java.lang.reflect.Array;
/*    7:     */ import java.util.AbstractList;
/*    8:     */ import java.util.Collection;
/*    9:     */ import java.util.ConcurrentModificationException;
/*   10:     */ import java.util.Iterator;
/*   11:     */ import java.util.List;
/*   12:     */ import java.util.ListIterator;
/*   13:     */ import java.util.NoSuchElementException;
/*   14:     */ import org.apache.commons.collections.OrderedIterator;
/*   15:     */ 
/*   16:     */ public abstract class AbstractLinkedList
/*   17:     */   implements List
/*   18:     */ {
/*   19:     */   protected transient Node header;
/*   20:     */   protected transient int size;
/*   21:     */   protected transient int modCount;
/*   22:     */   
/*   23:     */   protected AbstractLinkedList() {}
/*   24:     */   
/*   25:     */   protected AbstractLinkedList(Collection coll)
/*   26:     */   {
/*   27:  89 */     init();
/*   28:  90 */     addAll(coll);
/*   29:     */   }
/*   30:     */   
/*   31:     */   protected void init()
/*   32:     */   {
/*   33: 100 */     this.header = createHeaderNode();
/*   34:     */   }
/*   35:     */   
/*   36:     */   public int size()
/*   37:     */   {
/*   38: 105 */     return this.size;
/*   39:     */   }
/*   40:     */   
/*   41:     */   public boolean isEmpty()
/*   42:     */   {
/*   43: 109 */     return size() == 0;
/*   44:     */   }
/*   45:     */   
/*   46:     */   public Object get(int index)
/*   47:     */   {
/*   48: 113 */     Node node = getNode(index, false);
/*   49: 114 */     return node.getValue();
/*   50:     */   }
/*   51:     */   
/*   52:     */   public Iterator iterator()
/*   53:     */   {
/*   54: 119 */     return listIterator();
/*   55:     */   }
/*   56:     */   
/*   57:     */   public ListIterator listIterator()
/*   58:     */   {
/*   59: 123 */     return new LinkedListIterator(this, 0);
/*   60:     */   }
/*   61:     */   
/*   62:     */   public ListIterator listIterator(int fromIndex)
/*   63:     */   {
/*   64: 127 */     return new LinkedListIterator(this, fromIndex);
/*   65:     */   }
/*   66:     */   
/*   67:     */   public int indexOf(Object value)
/*   68:     */   {
/*   69: 132 */     int i = 0;
/*   70: 133 */     for (Node node = this.header.next; node != this.header; node = node.next)
/*   71:     */     {
/*   72: 134 */       if (isEqualValue(node.getValue(), value)) {
/*   73: 135 */         return i;
/*   74:     */       }
/*   75: 137 */       i++;
/*   76:     */     }
/*   77: 139 */     return -1;
/*   78:     */   }
/*   79:     */   
/*   80:     */   public int lastIndexOf(Object value)
/*   81:     */   {
/*   82: 143 */     int i = this.size - 1;
/*   83: 144 */     for (Node node = this.header.previous; node != this.header; node = node.previous)
/*   84:     */     {
/*   85: 145 */       if (isEqualValue(node.getValue(), value)) {
/*   86: 146 */         return i;
/*   87:     */       }
/*   88: 148 */       i--;
/*   89:     */     }
/*   90: 150 */     return -1;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public boolean contains(Object value)
/*   94:     */   {
/*   95: 154 */     return indexOf(value) != -1;
/*   96:     */   }
/*   97:     */   
/*   98:     */   public boolean containsAll(Collection coll)
/*   99:     */   {
/*  100: 158 */     Iterator it = coll.iterator();
/*  101: 159 */     while (it.hasNext()) {
/*  102: 160 */       if (!contains(it.next())) {
/*  103: 161 */         return false;
/*  104:     */       }
/*  105:     */     }
/*  106: 164 */     return true;
/*  107:     */   }
/*  108:     */   
/*  109:     */   public Object[] toArray()
/*  110:     */   {
/*  111: 169 */     return toArray(new Object[this.size]);
/*  112:     */   }
/*  113:     */   
/*  114:     */   public Object[] toArray(Object[] array)
/*  115:     */   {
/*  116: 174 */     if (array.length < this.size)
/*  117:     */     {
/*  118: 175 */       Class componentType = array.getClass().getComponentType();
/*  119: 176 */       array = (Object[])Array.newInstance(componentType, this.size);
/*  120:     */     }
/*  121: 179 */     int i = 0;
/*  122: 180 */     for (Node node = this.header.next; node != this.header; i++)
/*  123:     */     {
/*  124: 181 */       array[i] = node.getValue();node = node.next;
/*  125:     */     }
/*  126: 184 */     if (array.length > this.size) {
/*  127: 185 */       array[this.size] = null;
/*  128:     */     }
/*  129: 187 */     return array;
/*  130:     */   }
/*  131:     */   
/*  132:     */   public List subList(int fromIndexInclusive, int toIndexExclusive)
/*  133:     */   {
/*  134: 198 */     return new LinkedSubList(this, fromIndexInclusive, toIndexExclusive);
/*  135:     */   }
/*  136:     */   
/*  137:     */   public boolean add(Object value)
/*  138:     */   {
/*  139: 203 */     addLast(value);
/*  140: 204 */     return true;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public void add(int index, Object value)
/*  144:     */   {
/*  145: 208 */     Node node = getNode(index, true);
/*  146: 209 */     addNodeBefore(node, value);
/*  147:     */   }
/*  148:     */   
/*  149:     */   public boolean addAll(Collection coll)
/*  150:     */   {
/*  151: 213 */     return addAll(this.size, coll);
/*  152:     */   }
/*  153:     */   
/*  154:     */   public boolean addAll(int index, Collection coll)
/*  155:     */   {
/*  156: 217 */     Node node = getNode(index, true);
/*  157: 218 */     for (Iterator itr = coll.iterator(); itr.hasNext();)
/*  158:     */     {
/*  159: 219 */       Object value = itr.next();
/*  160: 220 */       addNodeBefore(node, value);
/*  161:     */     }
/*  162: 222 */     return true;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public Object remove(int index)
/*  166:     */   {
/*  167: 227 */     Node node = getNode(index, false);
/*  168: 228 */     Object oldValue = node.getValue();
/*  169: 229 */     removeNode(node);
/*  170: 230 */     return oldValue;
/*  171:     */   }
/*  172:     */   
/*  173:     */   public boolean remove(Object value)
/*  174:     */   {
/*  175: 234 */     for (Node node = this.header.next; node != this.header; node = node.next) {
/*  176: 235 */       if (isEqualValue(node.getValue(), value))
/*  177:     */       {
/*  178: 236 */         removeNode(node);
/*  179: 237 */         return true;
/*  180:     */       }
/*  181:     */     }
/*  182: 240 */     return false;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public boolean removeAll(Collection coll)
/*  186:     */   {
/*  187: 244 */     boolean modified = false;
/*  188: 245 */     Iterator it = iterator();
/*  189: 246 */     while (it.hasNext()) {
/*  190: 247 */       if (coll.contains(it.next()))
/*  191:     */       {
/*  192: 248 */         it.remove();
/*  193: 249 */         modified = true;
/*  194:     */       }
/*  195:     */     }
/*  196: 252 */     return modified;
/*  197:     */   }
/*  198:     */   
/*  199:     */   public boolean retainAll(Collection coll)
/*  200:     */   {
/*  201: 257 */     boolean modified = false;
/*  202: 258 */     Iterator it = iterator();
/*  203: 259 */     while (it.hasNext()) {
/*  204: 260 */       if (!coll.contains(it.next()))
/*  205:     */       {
/*  206: 261 */         it.remove();
/*  207: 262 */         modified = true;
/*  208:     */       }
/*  209:     */     }
/*  210: 265 */     return modified;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public Object set(int index, Object value)
/*  214:     */   {
/*  215: 269 */     Node node = getNode(index, false);
/*  216: 270 */     Object oldValue = node.getValue();
/*  217: 271 */     updateNode(node, value);
/*  218: 272 */     return oldValue;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void clear()
/*  222:     */   {
/*  223: 276 */     removeAllNodes();
/*  224:     */   }
/*  225:     */   
/*  226:     */   public Object getFirst()
/*  227:     */   {
/*  228: 281 */     Node node = this.header.next;
/*  229: 282 */     if (node == this.header) {
/*  230: 283 */       throw new NoSuchElementException();
/*  231:     */     }
/*  232: 285 */     return node.getValue();
/*  233:     */   }
/*  234:     */   
/*  235:     */   public Object getLast()
/*  236:     */   {
/*  237: 289 */     Node node = this.header.previous;
/*  238: 290 */     if (node == this.header) {
/*  239: 291 */       throw new NoSuchElementException();
/*  240:     */     }
/*  241: 293 */     return node.getValue();
/*  242:     */   }
/*  243:     */   
/*  244:     */   public boolean addFirst(Object o)
/*  245:     */   {
/*  246: 297 */     addNodeAfter(this.header, o);
/*  247: 298 */     return true;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public boolean addLast(Object o)
/*  251:     */   {
/*  252: 302 */     addNodeBefore(this.header, o);
/*  253: 303 */     return true;
/*  254:     */   }
/*  255:     */   
/*  256:     */   public Object removeFirst()
/*  257:     */   {
/*  258: 307 */     Node node = this.header.next;
/*  259: 308 */     if (node == this.header) {
/*  260: 309 */       throw new NoSuchElementException();
/*  261:     */     }
/*  262: 311 */     Object oldValue = node.getValue();
/*  263: 312 */     removeNode(node);
/*  264: 313 */     return oldValue;
/*  265:     */   }
/*  266:     */   
/*  267:     */   public Object removeLast()
/*  268:     */   {
/*  269: 317 */     Node node = this.header.previous;
/*  270: 318 */     if (node == this.header) {
/*  271: 319 */       throw new NoSuchElementException();
/*  272:     */     }
/*  273: 321 */     Object oldValue = node.getValue();
/*  274: 322 */     removeNode(node);
/*  275: 323 */     return oldValue;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public boolean equals(Object obj)
/*  279:     */   {
/*  280: 328 */     if (obj == this) {
/*  281: 329 */       return true;
/*  282:     */     }
/*  283: 331 */     if (!(obj instanceof List)) {
/*  284: 332 */       return false;
/*  285:     */     }
/*  286: 334 */     List other = (List)obj;
/*  287: 335 */     if (other.size() != size()) {
/*  288: 336 */       return false;
/*  289:     */     }
/*  290: 338 */     ListIterator it1 = listIterator();
/*  291: 339 */     ListIterator it2 = other.listIterator();
/*  292: 340 */     while ((it1.hasNext()) && (it2.hasNext()))
/*  293:     */     {
/*  294: 341 */       Object o1 = it1.next();
/*  295: 342 */       Object o2 = it2.next();
/*  296: 343 */       if (o1 == null ? o2 != null : !o1.equals(o2)) {
/*  297: 344 */         return false;
/*  298:     */       }
/*  299:     */     }
/*  300: 346 */     return (!it1.hasNext()) && (!it2.hasNext());
/*  301:     */   }
/*  302:     */   
/*  303:     */   public int hashCode()
/*  304:     */   {
/*  305: 350 */     int hashCode = 1;
/*  306: 351 */     Iterator it = iterator();
/*  307: 352 */     while (it.hasNext())
/*  308:     */     {
/*  309: 353 */       Object obj = it.next();
/*  310: 354 */       hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
/*  311:     */     }
/*  312: 356 */     return hashCode;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public String toString()
/*  316:     */   {
/*  317: 360 */     if (size() == 0) {
/*  318: 361 */       return "[]";
/*  319:     */     }
/*  320: 363 */     StringBuffer buf = new StringBuffer(16 * size());
/*  321: 364 */     buf.append("[");
/*  322:     */     
/*  323: 366 */     Iterator it = iterator();
/*  324: 367 */     boolean hasNext = it.hasNext();
/*  325: 368 */     while (hasNext)
/*  326:     */     {
/*  327: 369 */       Object value = it.next();
/*  328: 370 */       buf.append(value == this ? "(this Collection)" : value);
/*  329: 371 */       hasNext = it.hasNext();
/*  330: 372 */       if (hasNext) {
/*  331: 373 */         buf.append(", ");
/*  332:     */       }
/*  333:     */     }
/*  334: 376 */     buf.append("]");
/*  335: 377 */     return buf.toString();
/*  336:     */   }
/*  337:     */   
/*  338:     */   protected boolean isEqualValue(Object value1, Object value2)
/*  339:     */   {
/*  340: 391 */     return (value1 == value2) || ((value1 != null) && (value1.equals(value2)));
/*  341:     */   }
/*  342:     */   
/*  343:     */   protected void updateNode(Node node, Object value)
/*  344:     */   {
/*  345: 403 */     node.setValue(value);
/*  346:     */   }
/*  347:     */   
/*  348:     */   protected Node createHeaderNode()
/*  349:     */   {
/*  350: 414 */     return new Node();
/*  351:     */   }
/*  352:     */   
/*  353:     */   protected Node createNode(Object value)
/*  354:     */   {
/*  355: 425 */     return new Node(value);
/*  356:     */   }
/*  357:     */   
/*  358:     */   protected void addNodeBefore(Node node, Object value)
/*  359:     */   {
/*  360: 440 */     Node newNode = createNode(value);
/*  361: 441 */     addNode(newNode, node);
/*  362:     */   }
/*  363:     */   
/*  364:     */   protected void addNodeAfter(Node node, Object value)
/*  365:     */   {
/*  366: 456 */     Node newNode = createNode(value);
/*  367: 457 */     addNode(newNode, node.next);
/*  368:     */   }
/*  369:     */   
/*  370:     */   protected void addNode(Node nodeToInsert, Node insertBeforeNode)
/*  371:     */   {
/*  372: 468 */     nodeToInsert.next = insertBeforeNode;
/*  373: 469 */     nodeToInsert.previous = insertBeforeNode.previous;
/*  374: 470 */     insertBeforeNode.previous.next = nodeToInsert;
/*  375: 471 */     insertBeforeNode.previous = nodeToInsert;
/*  376: 472 */     this.size += 1;
/*  377: 473 */     this.modCount += 1;
/*  378:     */   }
/*  379:     */   
/*  380:     */   protected void removeNode(Node node)
/*  381:     */   {
/*  382: 483 */     node.previous.next = node.next;
/*  383: 484 */     node.next.previous = node.previous;
/*  384: 485 */     this.size -= 1;
/*  385: 486 */     this.modCount += 1;
/*  386:     */   }
/*  387:     */   
/*  388:     */   protected void removeAllNodes()
/*  389:     */   {
/*  390: 493 */     this.header.next = this.header;
/*  391: 494 */     this.header.previous = this.header;
/*  392: 495 */     this.size = 0;
/*  393: 496 */     this.modCount += 1;
/*  394:     */   }
/*  395:     */   
/*  396:     */   protected Node getNode(int index, boolean endMarkerAllowed)
/*  397:     */     throws IndexOutOfBoundsException
/*  398:     */   {
/*  399: 511 */     if (index < 0) {
/*  400: 512 */       throw new IndexOutOfBoundsException("Couldn't get the node: index (" + index + ") less than zero.");
/*  401:     */     }
/*  402: 515 */     if ((!endMarkerAllowed) && (index == this.size)) {
/*  403: 516 */       throw new IndexOutOfBoundsException("Couldn't get the node: index (" + index + ") is the size of the list.");
/*  404:     */     }
/*  405: 519 */     if (index > this.size) {
/*  406: 520 */       throw new IndexOutOfBoundsException("Couldn't get the node: index (" + index + ") greater than the size of the " + "list (" + this.size + ").");
/*  407:     */     }
/*  408:     */     Node node;
/*  409: 526 */     if (index < this.size / 2)
/*  410:     */     {
/*  411: 528 */       Node node = this.header.next;
/*  412: 529 */       for (int currentIndex = 0; currentIndex < index; currentIndex++) {
/*  413: 530 */         node = node.next;
/*  414:     */       }
/*  415:     */     }
/*  416:     */     else
/*  417:     */     {
/*  418: 534 */       node = this.header;
/*  419: 535 */       for (int currentIndex = this.size; currentIndex > index; currentIndex--) {
/*  420: 536 */         node = node.previous;
/*  421:     */       }
/*  422:     */     }
/*  423: 539 */     return node;
/*  424:     */   }
/*  425:     */   
/*  426:     */   protected Iterator createSubListIterator(LinkedSubList subList)
/*  427:     */   {
/*  428: 549 */     return createSubListListIterator(subList, 0);
/*  429:     */   }
/*  430:     */   
/*  431:     */   protected ListIterator createSubListListIterator(LinkedSubList subList, int fromIndex)
/*  432:     */   {
/*  433: 559 */     return new LinkedSubListIterator(subList, fromIndex);
/*  434:     */   }
/*  435:     */   
/*  436:     */   protected void doWriteObject(ObjectOutputStream outputStream)
/*  437:     */     throws IOException
/*  438:     */   {
/*  439: 571 */     outputStream.writeInt(size());
/*  440: 572 */     for (Iterator itr = iterator(); itr.hasNext();) {
/*  441: 573 */       outputStream.writeObject(itr.next());
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   protected void doReadObject(ObjectInputStream inputStream)
/*  446:     */     throws IOException, ClassNotFoundException
/*  447:     */   {
/*  448: 584 */     init();
/*  449: 585 */     int size = inputStream.readInt();
/*  450: 586 */     for (int i = 0; i < size; i++) {
/*  451: 587 */       add(inputStream.readObject());
/*  452:     */     }
/*  453:     */   }
/*  454:     */   
/*  455:     */   protected static class Node
/*  456:     */   {
/*  457:     */     protected Node previous;
/*  458:     */     protected Node next;
/*  459:     */     protected Object value;
/*  460:     */     
/*  461:     */     protected Node()
/*  462:     */     {
/*  463: 612 */       this.previous = this;
/*  464: 613 */       this.next = this;
/*  465:     */     }
/*  466:     */     
/*  467:     */     protected Node(Object value)
/*  468:     */     {
/*  469: 623 */       this.value = value;
/*  470:     */     }
/*  471:     */     
/*  472:     */     protected Node(Node previous, Node next, Object value)
/*  473:     */     {
/*  474: 635 */       this.previous = previous;
/*  475: 636 */       this.next = next;
/*  476: 637 */       this.value = value;
/*  477:     */     }
/*  478:     */     
/*  479:     */     protected Object getValue()
/*  480:     */     {
/*  481: 647 */       return this.value;
/*  482:     */     }
/*  483:     */     
/*  484:     */     protected void setValue(Object value)
/*  485:     */     {
/*  486: 657 */       this.value = value;
/*  487:     */     }
/*  488:     */     
/*  489:     */     protected Node getPreviousNode()
/*  490:     */     {
/*  491: 667 */       return this.previous;
/*  492:     */     }
/*  493:     */     
/*  494:     */     protected void setPreviousNode(Node previous)
/*  495:     */     {
/*  496: 677 */       this.previous = previous;
/*  497:     */     }
/*  498:     */     
/*  499:     */     protected Node getNextNode()
/*  500:     */     {
/*  501: 687 */       return this.next;
/*  502:     */     }
/*  503:     */     
/*  504:     */     protected void setNextNode(Node next)
/*  505:     */     {
/*  506: 697 */       this.next = next;
/*  507:     */     }
/*  508:     */   }
/*  509:     */   
/*  510:     */   protected static class LinkedListIterator
/*  511:     */     implements ListIterator, OrderedIterator
/*  512:     */   {
/*  513:     */     protected final AbstractLinkedList parent;
/*  514:     */     protected AbstractLinkedList.Node next;
/*  515:     */     protected int nextIndex;
/*  516:     */     protected AbstractLinkedList.Node current;
/*  517:     */     protected int expectedModCount;
/*  518:     */     
/*  519:     */     protected LinkedListIterator(AbstractLinkedList parent, int fromIndex)
/*  520:     */       throws IndexOutOfBoundsException
/*  521:     */     {
/*  522: 747 */       this.parent = parent;
/*  523: 748 */       this.expectedModCount = parent.modCount;
/*  524: 749 */       this.next = parent.getNode(fromIndex, true);
/*  525: 750 */       this.nextIndex = fromIndex;
/*  526:     */     }
/*  527:     */     
/*  528:     */     protected void checkModCount()
/*  529:     */     {
/*  530: 761 */       if (this.parent.modCount != this.expectedModCount) {
/*  531: 762 */         throw new ConcurrentModificationException();
/*  532:     */       }
/*  533:     */     }
/*  534:     */     
/*  535:     */     protected AbstractLinkedList.Node getLastNodeReturned()
/*  536:     */       throws IllegalStateException
/*  537:     */     {
/*  538: 774 */       if (this.current == null) {
/*  539: 775 */         throw new IllegalStateException();
/*  540:     */       }
/*  541: 777 */       return this.current;
/*  542:     */     }
/*  543:     */     
/*  544:     */     public boolean hasNext()
/*  545:     */     {
/*  546: 781 */       return this.next != this.parent.header;
/*  547:     */     }
/*  548:     */     
/*  549:     */     public Object next()
/*  550:     */     {
/*  551: 785 */       checkModCount();
/*  552: 786 */       if (!hasNext()) {
/*  553: 787 */         throw new NoSuchElementException("No element at index " + this.nextIndex + ".");
/*  554:     */       }
/*  555: 789 */       Object value = this.next.getValue();
/*  556: 790 */       this.current = this.next;
/*  557: 791 */       this.next = this.next.next;
/*  558: 792 */       this.nextIndex += 1;
/*  559: 793 */       return value;
/*  560:     */     }
/*  561:     */     
/*  562:     */     public boolean hasPrevious()
/*  563:     */     {
/*  564: 797 */       return this.next.previous != this.parent.header;
/*  565:     */     }
/*  566:     */     
/*  567:     */     public Object previous()
/*  568:     */     {
/*  569: 801 */       checkModCount();
/*  570: 802 */       if (!hasPrevious()) {
/*  571: 803 */         throw new NoSuchElementException("Already at start of list.");
/*  572:     */       }
/*  573: 805 */       this.next = this.next.previous;
/*  574: 806 */       Object value = this.next.getValue();
/*  575: 807 */       this.current = this.next;
/*  576: 808 */       this.nextIndex -= 1;
/*  577: 809 */       return value;
/*  578:     */     }
/*  579:     */     
/*  580:     */     public int nextIndex()
/*  581:     */     {
/*  582: 813 */       return this.nextIndex;
/*  583:     */     }
/*  584:     */     
/*  585:     */     public int previousIndex()
/*  586:     */     {
/*  587: 818 */       return nextIndex() - 1;
/*  588:     */     }
/*  589:     */     
/*  590:     */     public void remove()
/*  591:     */     {
/*  592: 822 */       checkModCount();
/*  593: 823 */       if (this.current == this.next)
/*  594:     */       {
/*  595: 825 */         this.next = this.next.next;
/*  596: 826 */         this.parent.removeNode(getLastNodeReturned());
/*  597:     */       }
/*  598:     */       else
/*  599:     */       {
/*  600: 829 */         this.parent.removeNode(getLastNodeReturned());
/*  601: 830 */         this.nextIndex -= 1;
/*  602:     */       }
/*  603: 832 */       this.current = null;
/*  604: 833 */       this.expectedModCount += 1;
/*  605:     */     }
/*  606:     */     
/*  607:     */     public void set(Object obj)
/*  608:     */     {
/*  609: 837 */       checkModCount();
/*  610: 838 */       getLastNodeReturned().setValue(obj);
/*  611:     */     }
/*  612:     */     
/*  613:     */     public void add(Object obj)
/*  614:     */     {
/*  615: 842 */       checkModCount();
/*  616: 843 */       this.parent.addNodeBefore(this.next, obj);
/*  617: 844 */       this.current = null;
/*  618: 845 */       this.nextIndex += 1;
/*  619: 846 */       this.expectedModCount += 1;
/*  620:     */     }
/*  621:     */   }
/*  622:     */   
/*  623:     */   protected static class LinkedSubListIterator
/*  624:     */     extends AbstractLinkedList.LinkedListIterator
/*  625:     */   {
/*  626:     */     protected final AbstractLinkedList.LinkedSubList sub;
/*  627:     */     
/*  628:     */     protected LinkedSubListIterator(AbstractLinkedList.LinkedSubList sub, int startIndex)
/*  629:     */     {
/*  630: 861 */       super(startIndex + sub.offset);
/*  631: 862 */       this.sub = sub;
/*  632:     */     }
/*  633:     */     
/*  634:     */     public boolean hasNext()
/*  635:     */     {
/*  636: 866 */       return nextIndex() < this.sub.size;
/*  637:     */     }
/*  638:     */     
/*  639:     */     public boolean hasPrevious()
/*  640:     */     {
/*  641: 870 */       return previousIndex() >= 0;
/*  642:     */     }
/*  643:     */     
/*  644:     */     public int nextIndex()
/*  645:     */     {
/*  646: 874 */       return super.nextIndex() - this.sub.offset;
/*  647:     */     }
/*  648:     */     
/*  649:     */     public void add(Object obj)
/*  650:     */     {
/*  651: 878 */       super.add(obj);
/*  652: 879 */       this.sub.expectedModCount = this.parent.modCount;
/*  653: 880 */       this.sub.size += 1;
/*  654:     */     }
/*  655:     */     
/*  656:     */     public void remove()
/*  657:     */     {
/*  658: 884 */       super.remove();
/*  659: 885 */       this.sub.expectedModCount = this.parent.modCount;
/*  660: 886 */       this.sub.size -= 1;
/*  661:     */     }
/*  662:     */   }
/*  663:     */   
/*  664:     */   protected static class LinkedSubList
/*  665:     */     extends AbstractList
/*  666:     */   {
/*  667:     */     AbstractLinkedList parent;
/*  668:     */     int offset;
/*  669:     */     int size;
/*  670:     */     int expectedModCount;
/*  671:     */     
/*  672:     */     protected LinkedSubList(AbstractLinkedList parent, int fromIndex, int toIndex)
/*  673:     */     {
/*  674: 905 */       if (fromIndex < 0) {
/*  675: 906 */         throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
/*  676:     */       }
/*  677: 908 */       if (toIndex > parent.size()) {
/*  678: 909 */         throw new IndexOutOfBoundsException("toIndex = " + toIndex);
/*  679:     */       }
/*  680: 911 */       if (fromIndex > toIndex) {
/*  681: 912 */         throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
/*  682:     */       }
/*  683: 914 */       this.parent = parent;
/*  684: 915 */       this.offset = fromIndex;
/*  685: 916 */       this.size = (toIndex - fromIndex);
/*  686: 917 */       this.expectedModCount = parent.modCount;
/*  687:     */     }
/*  688:     */     
/*  689:     */     public int size()
/*  690:     */     {
/*  691: 921 */       checkModCount();
/*  692: 922 */       return this.size;
/*  693:     */     }
/*  694:     */     
/*  695:     */     public Object get(int index)
/*  696:     */     {
/*  697: 926 */       rangeCheck(index, this.size);
/*  698: 927 */       checkModCount();
/*  699: 928 */       return this.parent.get(index + this.offset);
/*  700:     */     }
/*  701:     */     
/*  702:     */     public void add(int index, Object obj)
/*  703:     */     {
/*  704: 932 */       rangeCheck(index, this.size + 1);
/*  705: 933 */       checkModCount();
/*  706: 934 */       this.parent.add(index + this.offset, obj);
/*  707: 935 */       this.expectedModCount = this.parent.modCount;
/*  708: 936 */       this.size += 1;
/*  709: 937 */       this.modCount += 1;
/*  710:     */     }
/*  711:     */     
/*  712:     */     public Object remove(int index)
/*  713:     */     {
/*  714: 941 */       rangeCheck(index, this.size);
/*  715: 942 */       checkModCount();
/*  716: 943 */       Object result = this.parent.remove(index + this.offset);
/*  717: 944 */       this.expectedModCount = this.parent.modCount;
/*  718: 945 */       this.size -= 1;
/*  719: 946 */       this.modCount += 1;
/*  720: 947 */       return result;
/*  721:     */     }
/*  722:     */     
/*  723:     */     public boolean addAll(Collection coll)
/*  724:     */     {
/*  725: 951 */       return addAll(this.size, coll);
/*  726:     */     }
/*  727:     */     
/*  728:     */     public boolean addAll(int index, Collection coll)
/*  729:     */     {
/*  730: 955 */       rangeCheck(index, this.size + 1);
/*  731: 956 */       int cSize = coll.size();
/*  732: 957 */       if (cSize == 0) {
/*  733: 958 */         return false;
/*  734:     */       }
/*  735: 961 */       checkModCount();
/*  736: 962 */       this.parent.addAll(this.offset + index, coll);
/*  737: 963 */       this.expectedModCount = this.parent.modCount;
/*  738: 964 */       this.size += cSize;
/*  739: 965 */       this.modCount += 1;
/*  740: 966 */       return true;
/*  741:     */     }
/*  742:     */     
/*  743:     */     public Object set(int index, Object obj)
/*  744:     */     {
/*  745: 970 */       rangeCheck(index, this.size);
/*  746: 971 */       checkModCount();
/*  747: 972 */       return this.parent.set(index + this.offset, obj);
/*  748:     */     }
/*  749:     */     
/*  750:     */     public void clear()
/*  751:     */     {
/*  752: 976 */       checkModCount();
/*  753: 977 */       Iterator it = iterator();
/*  754: 978 */       while (it.hasNext())
/*  755:     */       {
/*  756: 979 */         it.next();
/*  757: 980 */         it.remove();
/*  758:     */       }
/*  759:     */     }
/*  760:     */     
/*  761:     */     public Iterator iterator()
/*  762:     */     {
/*  763: 985 */       checkModCount();
/*  764: 986 */       return this.parent.createSubListIterator(this);
/*  765:     */     }
/*  766:     */     
/*  767:     */     public ListIterator listIterator(int index)
/*  768:     */     {
/*  769: 990 */       rangeCheck(index, this.size + 1);
/*  770: 991 */       checkModCount();
/*  771: 992 */       return this.parent.createSubListListIterator(this, index);
/*  772:     */     }
/*  773:     */     
/*  774:     */     public List subList(int fromIndexInclusive, int toIndexExclusive)
/*  775:     */     {
/*  776: 996 */       return new LinkedSubList(this.parent, fromIndexInclusive + this.offset, toIndexExclusive + this.offset);
/*  777:     */     }
/*  778:     */     
/*  779:     */     protected void rangeCheck(int index, int beyond)
/*  780:     */     {
/*  781:1000 */       if ((index < 0) || (index >= beyond)) {
/*  782:1001 */         throw new IndexOutOfBoundsException("Index '" + index + "' out of bounds for size '" + this.size + "'");
/*  783:     */       }
/*  784:     */     }
/*  785:     */     
/*  786:     */     protected void checkModCount()
/*  787:     */     {
/*  788:1006 */       if (this.parent.modCount != this.expectedModCount) {
/*  789:1007 */         throw new ConcurrentModificationException();
/*  790:     */       }
/*  791:     */     }
/*  792:     */   }
/*  793:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.AbstractLinkedList
 * JD-Core Version:    0.7.0.1
 */