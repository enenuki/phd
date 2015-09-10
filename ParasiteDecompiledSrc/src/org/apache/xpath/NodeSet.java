/*    1:     */ package org.apache.xpath;
/*    2:     */ 
/*    3:     */ import org.apache.xml.utils.DOM2Helper;
/*    4:     */ import org.apache.xpath.axes.ContextNodeList;
/*    5:     */ import org.apache.xpath.res.XPATHMessages;
/*    6:     */ import org.w3c.dom.DOMException;
/*    7:     */ import org.w3c.dom.Node;
/*    8:     */ import org.w3c.dom.NodeList;
/*    9:     */ import org.w3c.dom.traversal.NodeFilter;
/*   10:     */ import org.w3c.dom.traversal.NodeIterator;
/*   11:     */ 
/*   12:     */ public class NodeSet
/*   13:     */   implements NodeList, NodeIterator, Cloneable, ContextNodeList
/*   14:     */ {
/*   15:     */   public NodeSet()
/*   16:     */   {
/*   17:  68 */     this.m_blocksize = 32;
/*   18:  69 */     this.m_mapSize = 0;
/*   19:     */   }
/*   20:     */   
/*   21:     */   public NodeSet(int blocksize)
/*   22:     */   {
/*   23:  79 */     this.m_blocksize = blocksize;
/*   24:  80 */     this.m_mapSize = 0;
/*   25:     */   }
/*   26:     */   
/*   27:     */   public NodeSet(NodeList nodelist)
/*   28:     */   {
/*   29:  92 */     this(32);
/*   30:     */     
/*   31:  94 */     addNodes(nodelist);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public NodeSet(NodeSet nodelist)
/*   35:     */   {
/*   36: 106 */     this(32);
/*   37:     */     
/*   38: 108 */     addNodes(nodelist);
/*   39:     */   }
/*   40:     */   
/*   41:     */   public NodeSet(NodeIterator ni)
/*   42:     */   {
/*   43: 120 */     this(32);
/*   44:     */     
/*   45: 122 */     addNodes(ni);
/*   46:     */   }
/*   47:     */   
/*   48:     */   public NodeSet(Node node)
/*   49:     */   {
/*   50: 133 */     this(32);
/*   51:     */     
/*   52: 135 */     addNode(node);
/*   53:     */   }
/*   54:     */   
/*   55:     */   public Node getRoot()
/*   56:     */   {
/*   57: 144 */     return null;
/*   58:     */   }
/*   59:     */   
/*   60:     */   public NodeIterator cloneWithReset()
/*   61:     */     throws CloneNotSupportedException
/*   62:     */   {
/*   63: 160 */     NodeSet clone = (NodeSet)clone();
/*   64:     */     
/*   65: 162 */     clone.reset();
/*   66:     */     
/*   67: 164 */     return clone;
/*   68:     */   }
/*   69:     */   
/*   70:     */   public void reset()
/*   71:     */   {
/*   72: 172 */     this.m_next = 0;
/*   73:     */   }
/*   74:     */   
/*   75:     */   public int getWhatToShow()
/*   76:     */   {
/*   77: 189 */     return -17;
/*   78:     */   }
/*   79:     */   
/*   80:     */   public NodeFilter getFilter()
/*   81:     */   {
/*   82: 207 */     return null;
/*   83:     */   }
/*   84:     */   
/*   85:     */   public boolean getExpandEntityReferences()
/*   86:     */   {
/*   87: 228 */     return true;
/*   88:     */   }
/*   89:     */   
/*   90:     */   public Node nextNode()
/*   91:     */     throws DOMException
/*   92:     */   {
/*   93: 244 */     if (this.m_next < size())
/*   94:     */     {
/*   95: 246 */       Node next = elementAt(this.m_next);
/*   96:     */       
/*   97: 248 */       this.m_next += 1;
/*   98:     */       
/*   99: 250 */       return next;
/*  100:     */     }
/*  101: 253 */     return null;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public Node previousNode()
/*  105:     */     throws DOMException
/*  106:     */   {
/*  107: 270 */     if (!this.m_cacheNodes) {
/*  108: 271 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_CANNOT_ITERATE", null));
/*  109:     */     }
/*  110: 274 */     if (this.m_next - 1 > 0)
/*  111:     */     {
/*  112: 276 */       this.m_next -= 1;
/*  113:     */       
/*  114: 278 */       return elementAt(this.m_next);
/*  115:     */     }
/*  116: 281 */     return null;
/*  117:     */   }
/*  118:     */   
/*  119:     */   public void detach() {}
/*  120:     */   
/*  121:     */   public boolean isFresh()
/*  122:     */   {
/*  123: 307 */     return this.m_next == 0;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public void runTo(int index)
/*  127:     */   {
/*  128: 325 */     if (!this.m_cacheNodes) {
/*  129: 326 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_CANNOT_INDEX", null));
/*  130:     */     }
/*  131: 329 */     if ((index >= 0) && (this.m_next < this.m_firstFree)) {
/*  132: 330 */       this.m_next = index;
/*  133:     */     } else {
/*  134: 332 */       this.m_next = (this.m_firstFree - 1);
/*  135:     */     }
/*  136:     */   }
/*  137:     */   
/*  138:     */   public Node item(int index)
/*  139:     */   {
/*  140: 350 */     runTo(index);
/*  141:     */     
/*  142: 352 */     return elementAt(index);
/*  143:     */   }
/*  144:     */   
/*  145:     */   public int getLength()
/*  146:     */   {
/*  147: 366 */     runTo(-1);
/*  148:     */     
/*  149: 368 */     return size();
/*  150:     */   }
/*  151:     */   
/*  152:     */   public void addNode(Node n)
/*  153:     */   {
/*  154: 382 */     if (!this.m_mutable) {
/*  155: 383 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  156:     */     }
/*  157: 385 */     addElement(n);
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void insertNode(Node n, int pos)
/*  161:     */   {
/*  162: 400 */     if (!this.m_mutable) {
/*  163: 401 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  164:     */     }
/*  165: 403 */     insertElementAt(n, pos);
/*  166:     */   }
/*  167:     */   
/*  168:     */   public void removeNode(Node n)
/*  169:     */   {
/*  170: 416 */     if (!this.m_mutable) {
/*  171: 417 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  172:     */     }
/*  173: 419 */     removeElement(n);
/*  174:     */   }
/*  175:     */   
/*  176:     */   public void addNodes(NodeList nodelist)
/*  177:     */   {
/*  178: 434 */     if (!this.m_mutable) {
/*  179: 435 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  180:     */     }
/*  181: 437 */     if (null != nodelist)
/*  182:     */     {
/*  183: 439 */       int nChildren = nodelist.getLength();
/*  184: 441 */       for (int i = 0; i < nChildren; i++)
/*  185:     */       {
/*  186: 443 */         Node obj = nodelist.item(i);
/*  187: 445 */         if (null != obj) {
/*  188: 447 */           addElement(obj);
/*  189:     */         }
/*  190:     */       }
/*  191:     */     }
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void addNodes(NodeSet ns)
/*  195:     */   {
/*  196: 474 */     if (!this.m_mutable) {
/*  197: 475 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  198:     */     }
/*  199: 477 */     addNodes(ns);
/*  200:     */   }
/*  201:     */   
/*  202:     */   public void addNodes(NodeIterator iterator)
/*  203:     */   {
/*  204: 491 */     if (!this.m_mutable) {
/*  205: 492 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  206:     */     }
/*  207: 494 */     if (null != iterator)
/*  208:     */     {
/*  209:     */       Node obj;
/*  210: 498 */       while (null != (obj = iterator.nextNode()))
/*  211:     */       {
/*  212:     */         Node localNode1;
/*  213: 500 */         addElement(localNode1);
/*  214:     */       }
/*  215:     */     }
/*  216:     */   }
/*  217:     */   
/*  218:     */   public void addNodesInDocOrder(NodeList nodelist, XPathContext support)
/*  219:     */   {
/*  220: 519 */     if (!this.m_mutable) {
/*  221: 520 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  222:     */     }
/*  223: 522 */     int nChildren = nodelist.getLength();
/*  224: 524 */     for (int i = 0; i < nChildren; i++)
/*  225:     */     {
/*  226: 526 */       Node node = nodelist.item(i);
/*  227: 528 */       if (null != node) {
/*  228: 530 */         addNodeInDocOrder(node, support);
/*  229:     */       }
/*  230:     */     }
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void addNodesInDocOrder(NodeIterator iterator, XPathContext support)
/*  234:     */   {
/*  235: 547 */     if (!this.m_mutable) {
/*  236: 548 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  237:     */     }
/*  238:     */     Node node;
/*  239: 552 */     while (null != (node = iterator.nextNode()))
/*  240:     */     {
/*  241:     */       Node localNode1;
/*  242: 554 */       addNodeInDocOrder(localNode1, support);
/*  243:     */     }
/*  244:     */   }
/*  245:     */   
/*  246:     */   private boolean addNodesInDocOrder(int start, int end, int testIndex, NodeList nodelist, XPathContext support)
/*  247:     */   {
/*  248: 575 */     if (!this.m_mutable) {
/*  249: 576 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  250:     */     }
/*  251: 578 */     boolean foundit = false;
/*  252:     */     
/*  253: 580 */     Node node = nodelist.item(testIndex);
/*  254: 582 */     for (int i = end; i >= start; i--)
/*  255:     */     {
/*  256: 584 */       Node child = elementAt(i);
/*  257: 586 */       if (child == node)
/*  258:     */       {
/*  259: 588 */         i = -2;
/*  260:     */         
/*  261: 590 */         break;
/*  262:     */       }
/*  263: 593 */       if (!DOM2Helper.isNodeAfter(node, child))
/*  264:     */       {
/*  265: 595 */         insertElementAt(node, i + 1);
/*  266:     */         
/*  267: 597 */         testIndex--;
/*  268: 599 */         if (testIndex <= 0) {
/*  269:     */           break;
/*  270:     */         }
/*  271: 601 */         boolean foundPrev = addNodesInDocOrder(0, i, testIndex, nodelist, support);
/*  272: 604 */         if (!foundPrev) {
/*  273: 606 */           addNodesInDocOrder(i, size() - 1, testIndex, nodelist, support);
/*  274:     */         }
/*  275: 606 */         break;
/*  276:     */       }
/*  277:     */     }
/*  278: 614 */     if (i == -1) {
/*  279: 616 */       insertElementAt(node, 0);
/*  280:     */     }
/*  281: 619 */     return foundit;
/*  282:     */   }
/*  283:     */   
/*  284:     */   public int addNodeInDocOrder(Node node, boolean test, XPathContext support)
/*  285:     */   {
/*  286: 635 */     if (!this.m_mutable) {
/*  287: 636 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  288:     */     }
/*  289: 638 */     int insertIndex = -1;
/*  290: 640 */     if (test)
/*  291:     */     {
/*  292: 646 */       int size = size();
/*  293: 648 */       for (int i = size - 1; i >= 0; i--)
/*  294:     */       {
/*  295: 650 */         Node child = elementAt(i);
/*  296: 652 */         if (child == node) {
/*  297: 654 */           i = -2;
/*  298:     */         } else {
/*  299: 659 */           if (!DOM2Helper.isNodeAfter(node, child)) {
/*  300:     */             break;
/*  301:     */           }
/*  302:     */         }
/*  303:     */       }
/*  304: 665 */       if (i != -2)
/*  305:     */       {
/*  306: 667 */         insertIndex = i + 1;
/*  307:     */         
/*  308: 669 */         insertElementAt(node, insertIndex);
/*  309:     */       }
/*  310:     */     }
/*  311:     */     else
/*  312:     */     {
/*  313: 674 */       insertIndex = size();
/*  314:     */       
/*  315: 676 */       boolean foundit = false;
/*  316: 678 */       for (int i = 0; i < insertIndex; i++) {
/*  317: 680 */         if (item(i).equals(node))
/*  318:     */         {
/*  319: 682 */           foundit = true;
/*  320:     */           
/*  321: 684 */           break;
/*  322:     */         }
/*  323:     */       }
/*  324: 688 */       if (!foundit) {
/*  325: 689 */         addElement(node);
/*  326:     */       }
/*  327:     */     }
/*  328: 693 */     return insertIndex;
/*  329:     */   }
/*  330:     */   
/*  331:     */   public int addNodeInDocOrder(Node node, XPathContext support)
/*  332:     */   {
/*  333: 709 */     if (!this.m_mutable) {
/*  334: 710 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  335:     */     }
/*  336: 712 */     return addNodeInDocOrder(node, true, support);
/*  337:     */   }
/*  338:     */   
/*  339: 718 */   protected transient int m_next = 0;
/*  340:     */   
/*  341:     */   public int getCurrentPos()
/*  342:     */   {
/*  343: 730 */     return this.m_next;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public void setCurrentPos(int i)
/*  347:     */   {
/*  348: 742 */     if (!this.m_cacheNodes) {
/*  349: 743 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_CANNOT_INDEX", null));
/*  350:     */     }
/*  351: 746 */     this.m_next = i;
/*  352:     */   }
/*  353:     */   
/*  354:     */   public Node getCurrentNode()
/*  355:     */   {
/*  356: 759 */     if (!this.m_cacheNodes) {
/*  357: 760 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_CANNOT_INDEX", null));
/*  358:     */     }
/*  359: 763 */     int saved = this.m_next;
/*  360: 764 */     Node n = this.m_next < this.m_firstFree ? elementAt(this.m_next) : null;
/*  361: 765 */     this.m_next = saved;
/*  362: 766 */     return n;
/*  363:     */   }
/*  364:     */   
/*  365: 770 */   protected transient boolean m_mutable = true;
/*  366: 774 */   protected transient boolean m_cacheNodes = true;
/*  367:     */   
/*  368:     */   public boolean getShouldCacheNodes()
/*  369:     */   {
/*  370: 784 */     return this.m_cacheNodes;
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void setShouldCacheNodes(boolean b)
/*  374:     */   {
/*  375: 801 */     if (!isFresh()) {
/*  376: 802 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_CANNOT_CALL_SETSHOULDCACHENODE", null));
/*  377:     */     }
/*  378: 805 */     this.m_cacheNodes = b;
/*  379: 806 */     this.m_mutable = true;
/*  380:     */   }
/*  381:     */   
/*  382: 810 */   private transient int m_last = 0;
/*  383:     */   private int m_blocksize;
/*  384:     */   Node[] m_map;
/*  385:     */   
/*  386:     */   public int getLast()
/*  387:     */   {
/*  388: 814 */     return this.m_last;
/*  389:     */   }
/*  390:     */   
/*  391:     */   public void setLast(int last)
/*  392:     */   {
/*  393: 819 */     this.m_last = last;
/*  394:     */   }
/*  395:     */   
/*  396: 832 */   protected int m_firstFree = 0;
/*  397:     */   private int m_mapSize;
/*  398:     */   
/*  399:     */   public Object clone()
/*  400:     */     throws CloneNotSupportedException
/*  401:     */   {
/*  402: 848 */     NodeSet clone = (NodeSet)super.clone();
/*  403: 850 */     if ((null != this.m_map) && (this.m_map == clone.m_map))
/*  404:     */     {
/*  405: 852 */       clone.m_map = new Node[this.m_map.length];
/*  406:     */       
/*  407: 854 */       System.arraycopy(this.m_map, 0, clone.m_map, 0, this.m_map.length);
/*  408:     */     }
/*  409: 857 */     return clone;
/*  410:     */   }
/*  411:     */   
/*  412:     */   public int size()
/*  413:     */   {
/*  414: 867 */     return this.m_firstFree;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public void addElement(Node value)
/*  418:     */   {
/*  419: 877 */     if (!this.m_mutable) {
/*  420: 878 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  421:     */     }
/*  422: 880 */     if (this.m_firstFree + 1 >= this.m_mapSize) {
/*  423: 882 */       if (null == this.m_map)
/*  424:     */       {
/*  425: 884 */         this.m_map = new Node[this.m_blocksize];
/*  426: 885 */         this.m_mapSize = this.m_blocksize;
/*  427:     */       }
/*  428:     */       else
/*  429:     */       {
/*  430: 889 */         this.m_mapSize += this.m_blocksize;
/*  431:     */         
/*  432: 891 */         Node[] newMap = new Node[this.m_mapSize];
/*  433:     */         
/*  434: 893 */         System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  435:     */         
/*  436: 895 */         this.m_map = newMap;
/*  437:     */       }
/*  438:     */     }
/*  439: 899 */     this.m_map[this.m_firstFree] = value;
/*  440:     */     
/*  441: 901 */     this.m_firstFree += 1;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public final void push(Node value)
/*  445:     */   {
/*  446: 912 */     int ff = this.m_firstFree;
/*  447: 914 */     if (ff + 1 >= this.m_mapSize) {
/*  448: 916 */       if (null == this.m_map)
/*  449:     */       {
/*  450: 918 */         this.m_map = new Node[this.m_blocksize];
/*  451: 919 */         this.m_mapSize = this.m_blocksize;
/*  452:     */       }
/*  453:     */       else
/*  454:     */       {
/*  455: 923 */         this.m_mapSize += this.m_blocksize;
/*  456:     */         
/*  457: 925 */         Node[] newMap = new Node[this.m_mapSize];
/*  458:     */         
/*  459: 927 */         System.arraycopy(this.m_map, 0, newMap, 0, ff + 1);
/*  460:     */         
/*  461: 929 */         this.m_map = newMap;
/*  462:     */       }
/*  463:     */     }
/*  464: 933 */     this.m_map[ff] = value;
/*  465:     */     
/*  466: 935 */     ff++;
/*  467:     */     
/*  468: 937 */     this.m_firstFree = ff;
/*  469:     */   }
/*  470:     */   
/*  471:     */   public final Node pop()
/*  472:     */   {
/*  473: 948 */     this.m_firstFree -= 1;
/*  474:     */     
/*  475: 950 */     Node n = this.m_map[this.m_firstFree];
/*  476:     */     
/*  477: 952 */     this.m_map[this.m_firstFree] = null;
/*  478:     */     
/*  479: 954 */     return n;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public final Node popAndTop()
/*  483:     */   {
/*  484: 966 */     this.m_firstFree -= 1;
/*  485:     */     
/*  486: 968 */     this.m_map[this.m_firstFree] = null;
/*  487:     */     
/*  488: 970 */     return this.m_firstFree == 0 ? null : this.m_map[(this.m_firstFree - 1)];
/*  489:     */   }
/*  490:     */   
/*  491:     */   public final void popQuick()
/*  492:     */   {
/*  493: 979 */     this.m_firstFree -= 1;
/*  494:     */     
/*  495: 981 */     this.m_map[this.m_firstFree] = null;
/*  496:     */   }
/*  497:     */   
/*  498:     */   public final Node peepOrNull()
/*  499:     */   {
/*  500: 993 */     return (null != this.m_map) && (this.m_firstFree > 0) ? this.m_map[(this.m_firstFree - 1)] : null;
/*  501:     */   }
/*  502:     */   
/*  503:     */   public final void pushPair(Node v1, Node v2)
/*  504:     */   {
/*  505:1008 */     if (null == this.m_map)
/*  506:     */     {
/*  507:1010 */       this.m_map = new Node[this.m_blocksize];
/*  508:1011 */       this.m_mapSize = this.m_blocksize;
/*  509:     */     }
/*  510:1015 */     else if (this.m_firstFree + 2 >= this.m_mapSize)
/*  511:     */     {
/*  512:1017 */       this.m_mapSize += this.m_blocksize;
/*  513:     */       
/*  514:1019 */       Node[] newMap = new Node[this.m_mapSize];
/*  515:     */       
/*  516:1021 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree);
/*  517:     */       
/*  518:1023 */       this.m_map = newMap;
/*  519:     */     }
/*  520:1027 */     this.m_map[this.m_firstFree] = v1;
/*  521:1028 */     this.m_map[(this.m_firstFree + 1)] = v2;
/*  522:1029 */     this.m_firstFree += 2;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public final void popPair()
/*  526:     */   {
/*  527:1040 */     this.m_firstFree -= 2;
/*  528:1041 */     this.m_map[this.m_firstFree] = null;
/*  529:1042 */     this.m_map[(this.m_firstFree + 1)] = null;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public final void setTail(Node n)
/*  533:     */   {
/*  534:1054 */     this.m_map[(this.m_firstFree - 1)] = n;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public final void setTailSub1(Node n)
/*  538:     */   {
/*  539:1066 */     this.m_map[(this.m_firstFree - 2)] = n;
/*  540:     */   }
/*  541:     */   
/*  542:     */   public final Node peepTail()
/*  543:     */   {
/*  544:1078 */     return this.m_map[(this.m_firstFree - 1)];
/*  545:     */   }
/*  546:     */   
/*  547:     */   public final Node peepTailSub1()
/*  548:     */   {
/*  549:1090 */     return this.m_map[(this.m_firstFree - 2)];
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void insertElementAt(Node value, int at)
/*  553:     */   {
/*  554:1104 */     if (!this.m_mutable) {
/*  555:1105 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  556:     */     }
/*  557:1107 */     if (null == this.m_map)
/*  558:     */     {
/*  559:1109 */       this.m_map = new Node[this.m_blocksize];
/*  560:1110 */       this.m_mapSize = this.m_blocksize;
/*  561:     */     }
/*  562:1112 */     else if (this.m_firstFree + 1 >= this.m_mapSize)
/*  563:     */     {
/*  564:1114 */       this.m_mapSize += this.m_blocksize;
/*  565:     */       
/*  566:1116 */       Node[] newMap = new Node[this.m_mapSize];
/*  567:     */       
/*  568:1118 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  569:     */       
/*  570:1120 */       this.m_map = newMap;
/*  571:     */     }
/*  572:1123 */     if (at <= this.m_firstFree - 1) {
/*  573:1125 */       System.arraycopy(this.m_map, at, this.m_map, at + 1, this.m_firstFree - at);
/*  574:     */     }
/*  575:1128 */     this.m_map[at] = value;
/*  576:     */     
/*  577:1130 */     this.m_firstFree += 1;
/*  578:     */   }
/*  579:     */   
/*  580:     */   public void appendNodes(NodeSet nodes)
/*  581:     */   {
/*  582:1141 */     int nNodes = nodes.size();
/*  583:1143 */     if (null == this.m_map)
/*  584:     */     {
/*  585:1145 */       this.m_mapSize = (nNodes + this.m_blocksize);
/*  586:1146 */       this.m_map = new Node[this.m_mapSize];
/*  587:     */     }
/*  588:1148 */     else if (this.m_firstFree + nNodes >= this.m_mapSize)
/*  589:     */     {
/*  590:1150 */       this.m_mapSize += nNodes + this.m_blocksize;
/*  591:     */       
/*  592:1152 */       Node[] newMap = new Node[this.m_mapSize];
/*  593:     */       
/*  594:1154 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + nNodes);
/*  595:     */       
/*  596:1156 */       this.m_map = newMap;
/*  597:     */     }
/*  598:1159 */     System.arraycopy(nodes.m_map, 0, this.m_map, this.m_firstFree, nNodes);
/*  599:     */     
/*  600:1161 */     this.m_firstFree += nNodes;
/*  601:     */   }
/*  602:     */   
/*  603:     */   public void removeAllElements()
/*  604:     */   {
/*  605:1173 */     if (null == this.m_map) {
/*  606:1174 */       return;
/*  607:     */     }
/*  608:1176 */     for (int i = 0; i < this.m_firstFree; i++) {
/*  609:1178 */       this.m_map[i] = null;
/*  610:     */     }
/*  611:1181 */     this.m_firstFree = 0;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public boolean removeElement(Node s)
/*  615:     */   {
/*  616:1197 */     if (!this.m_mutable) {
/*  617:1198 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  618:     */     }
/*  619:1200 */     if (null == this.m_map) {
/*  620:1201 */       return false;
/*  621:     */     }
/*  622:1203 */     for (int i = 0; i < this.m_firstFree; i++)
/*  623:     */     {
/*  624:1205 */       Node node = this.m_map[i];
/*  625:1207 */       if ((null != node) && (node.equals(s)))
/*  626:     */       {
/*  627:1209 */         if (i < this.m_firstFree - 1) {
/*  628:1210 */           System.arraycopy(this.m_map, i + 1, this.m_map, i, this.m_firstFree - i - 1);
/*  629:     */         }
/*  630:1212 */         this.m_firstFree -= 1;
/*  631:1213 */         this.m_map[this.m_firstFree] = null;
/*  632:     */         
/*  633:1215 */         return true;
/*  634:     */       }
/*  635:     */     }
/*  636:1219 */     return false;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void removeElementAt(int i)
/*  640:     */   {
/*  641:1233 */     if (null == this.m_map) {
/*  642:1234 */       return;
/*  643:     */     }
/*  644:1236 */     if (i >= this.m_firstFree) {
/*  645:1237 */       throw new ArrayIndexOutOfBoundsException(i + " >= " + this.m_firstFree);
/*  646:     */     }
/*  647:1238 */     if (i < 0) {
/*  648:1239 */       throw new ArrayIndexOutOfBoundsException(i);
/*  649:     */     }
/*  650:1241 */     if (i < this.m_firstFree - 1) {
/*  651:1242 */       System.arraycopy(this.m_map, i + 1, this.m_map, i, this.m_firstFree - i - 1);
/*  652:     */     }
/*  653:1244 */     this.m_firstFree -= 1;
/*  654:1245 */     this.m_map[this.m_firstFree] = null;
/*  655:     */   }
/*  656:     */   
/*  657:     */   public void setElementAt(Node node, int index)
/*  658:     */   {
/*  659:1260 */     if (!this.m_mutable) {
/*  660:1261 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
/*  661:     */     }
/*  662:1263 */     if (null == this.m_map)
/*  663:     */     {
/*  664:1265 */       this.m_map = new Node[this.m_blocksize];
/*  665:1266 */       this.m_mapSize = this.m_blocksize;
/*  666:     */     }
/*  667:1269 */     this.m_map[index] = node;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public Node elementAt(int i)
/*  671:     */   {
/*  672:1282 */     if (null == this.m_map) {
/*  673:1283 */       return null;
/*  674:     */     }
/*  675:1285 */     return this.m_map[i];
/*  676:     */   }
/*  677:     */   
/*  678:     */   public boolean contains(Node s)
/*  679:     */   {
/*  680:1297 */     runTo(-1);
/*  681:1299 */     if (null == this.m_map) {
/*  682:1300 */       return false;
/*  683:     */     }
/*  684:1302 */     for (int i = 0; i < this.m_firstFree; i++)
/*  685:     */     {
/*  686:1304 */       Node node = this.m_map[i];
/*  687:1306 */       if ((null != node) && (node.equals(s))) {
/*  688:1307 */         return true;
/*  689:     */       }
/*  690:     */     }
/*  691:1310 */     return false;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public int indexOf(Node elem, int index)
/*  695:     */   {
/*  696:1326 */     runTo(-1);
/*  697:1328 */     if (null == this.m_map) {
/*  698:1329 */       return -1;
/*  699:     */     }
/*  700:1331 */     for (int i = index; i < this.m_firstFree; i++)
/*  701:     */     {
/*  702:1333 */       Node node = this.m_map[i];
/*  703:1335 */       if ((null != node) && (node.equals(elem))) {
/*  704:1336 */         return i;
/*  705:     */       }
/*  706:     */     }
/*  707:1339 */     return -1;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public int indexOf(Node elem)
/*  711:     */   {
/*  712:1354 */     runTo(-1);
/*  713:1356 */     if (null == this.m_map) {
/*  714:1357 */       return -1;
/*  715:     */     }
/*  716:1359 */     for (int i = 0; i < this.m_firstFree; i++)
/*  717:     */     {
/*  718:1361 */       Node node = this.m_map[i];
/*  719:1363 */       if ((null != node) && (node.equals(elem))) {
/*  720:1364 */         return i;
/*  721:     */       }
/*  722:     */     }
/*  723:1367 */     return -1;
/*  724:     */   }
/*  725:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.NodeSet
 * JD-Core Version:    0.7.0.1
 */