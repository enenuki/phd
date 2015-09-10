/*    1:     */ package org.apache.xml.dtm.ref;
/*    2:     */ 
/*    3:     */ import javax.xml.transform.Source;
/*    4:     */ import org.apache.xml.dtm.Axis;
/*    5:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*    6:     */ import org.apache.xml.dtm.DTMAxisTraverser;
/*    7:     */ import org.apache.xml.dtm.DTMException;
/*    8:     */ import org.apache.xml.dtm.DTMManager;
/*    9:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   10:     */ import org.apache.xml.res.XMLMessages;
/*   11:     */ import org.apache.xml.utils.NodeVector;
/*   12:     */ import org.apache.xml.utils.XMLStringFactory;
/*   13:     */ 
/*   14:     */ public abstract class DTMDefaultBaseIterators
/*   15:     */   extends DTMDefaultBaseTraversers
/*   16:     */ {
/*   17:     */   public DTMDefaultBaseIterators(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing)
/*   18:     */   {
/*   19:  57 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing);
/*   20:     */   }
/*   21:     */   
/*   22:     */   public DTMDefaultBaseIterators(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean usePrevsib, boolean newNameTable)
/*   23:     */   {
/*   24:  85 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, blocksize, usePrevsib, newNameTable);
/*   25:     */   }
/*   26:     */   
/*   27:     */   public DTMAxisIterator getTypedAxisIterator(int axis, int type)
/*   28:     */   {
/*   29: 103 */     DTMAxisIterator iterator = null;
/*   30: 119 */     switch (axis)
/*   31:     */     {
/*   32:     */     case 13: 
/*   33: 122 */       iterator = new TypedSingletonIterator(type);
/*   34: 123 */       break;
/*   35:     */     case 3: 
/*   36: 125 */       iterator = new TypedChildrenIterator(type);
/*   37: 126 */       break;
/*   38:     */     case 10: 
/*   39: 128 */       return new ParentIterator().setNodeType(type);
/*   40:     */     case 0: 
/*   41: 130 */       return new TypedAncestorIterator(type);
/*   42:     */     case 1: 
/*   43: 132 */       return new TypedAncestorIterator(type).includeSelf();
/*   44:     */     case 2: 
/*   45: 134 */       return new TypedAttributeIterator(type);
/*   46:     */     case 4: 
/*   47: 136 */       iterator = new TypedDescendantIterator(type);
/*   48: 137 */       break;
/*   49:     */     case 5: 
/*   50: 139 */       iterator = new TypedDescendantIterator(type).includeSelf();
/*   51: 140 */       break;
/*   52:     */     case 6: 
/*   53: 142 */       iterator = new TypedFollowingIterator(type);
/*   54: 143 */       break;
/*   55:     */     case 11: 
/*   56: 145 */       iterator = new TypedPrecedingIterator(type);
/*   57: 146 */       break;
/*   58:     */     case 7: 
/*   59: 148 */       iterator = new TypedFollowingSiblingIterator(type);
/*   60: 149 */       break;
/*   61:     */     case 12: 
/*   62: 151 */       iterator = new TypedPrecedingSiblingIterator(type);
/*   63: 152 */       break;
/*   64:     */     case 9: 
/*   65: 154 */       iterator = new TypedNamespaceIterator(type);
/*   66: 155 */       break;
/*   67:     */     case 19: 
/*   68: 157 */       iterator = new TypedRootIterator(type);
/*   69: 158 */       break;
/*   70:     */     case 8: 
/*   71:     */     case 14: 
/*   72:     */     case 15: 
/*   73:     */     case 16: 
/*   74:     */     case 17: 
/*   75:     */     case 18: 
/*   76:     */     default: 
/*   77: 160 */       throw new DTMException(XMLMessages.createXMLMessage("ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", new Object[] { Axis.getNames(axis) }));
/*   78:     */     }
/*   79: 168 */     return iterator;
/*   80:     */   }
/*   81:     */   
/*   82:     */   public DTMAxisIterator getAxisIterator(int axis)
/*   83:     */   {
/*   84: 184 */     DTMAxisIterator iterator = null;
/*   85: 186 */     switch (axis)
/*   86:     */     {
/*   87:     */     case 13: 
/*   88: 189 */       iterator = new SingletonIterator();
/*   89: 190 */       break;
/*   90:     */     case 3: 
/*   91: 192 */       iterator = new ChildrenIterator();
/*   92: 193 */       break;
/*   93:     */     case 10: 
/*   94: 195 */       return new ParentIterator();
/*   95:     */     case 0: 
/*   96: 197 */       return new AncestorIterator();
/*   97:     */     case 1: 
/*   98: 199 */       return new AncestorIterator().includeSelf();
/*   99:     */     case 2: 
/*  100: 201 */       return new AttributeIterator();
/*  101:     */     case 4: 
/*  102: 203 */       iterator = new DescendantIterator();
/*  103: 204 */       break;
/*  104:     */     case 5: 
/*  105: 206 */       iterator = new DescendantIterator().includeSelf();
/*  106: 207 */       break;
/*  107:     */     case 6: 
/*  108: 209 */       iterator = new FollowingIterator();
/*  109: 210 */       break;
/*  110:     */     case 11: 
/*  111: 212 */       iterator = new PrecedingIterator();
/*  112: 213 */       break;
/*  113:     */     case 7: 
/*  114: 215 */       iterator = new FollowingSiblingIterator();
/*  115: 216 */       break;
/*  116:     */     case 12: 
/*  117: 218 */       iterator = new PrecedingSiblingIterator();
/*  118: 219 */       break;
/*  119:     */     case 9: 
/*  120: 221 */       iterator = new NamespaceIterator();
/*  121: 222 */       break;
/*  122:     */     case 19: 
/*  123: 224 */       iterator = new RootIterator();
/*  124: 225 */       break;
/*  125:     */     case 8: 
/*  126:     */     case 14: 
/*  127:     */     case 15: 
/*  128:     */     case 16: 
/*  129:     */     case 17: 
/*  130:     */     case 18: 
/*  131:     */     default: 
/*  132: 227 */       throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_AXIS_NOT_IMPLEMENTED", new Object[] { Axis.getNames(axis) }));
/*  133:     */     }
/*  134: 234 */     return iterator;
/*  135:     */   }
/*  136:     */   
/*  137:     */   public abstract class InternalAxisIteratorBase
/*  138:     */     extends DTMAxisIteratorBase
/*  139:     */   {
/*  140:     */     protected int _currentNode;
/*  141:     */     
/*  142:     */     public InternalAxisIteratorBase() {}
/*  143:     */     
/*  144:     */     public void setMark()
/*  145:     */     {
/*  146: 270 */       this._markedNode = this._currentNode;
/*  147:     */     }
/*  148:     */     
/*  149:     */     public void gotoMark()
/*  150:     */     {
/*  151: 280 */       this._currentNode = this._markedNode;
/*  152:     */     }
/*  153:     */   }
/*  154:     */   
/*  155:     */   public final class ChildrenIterator
/*  156:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  157:     */   {
/*  158:     */     public ChildrenIterator()
/*  159:     */     {
/*  160: 288 */       super();
/*  161:     */     }
/*  162:     */     
/*  163:     */     public DTMAxisIterator setStartNode(int node)
/*  164:     */     {
/*  165: 306 */       if (node == 0) {
/*  166: 307 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  167:     */       }
/*  168: 308 */       if (this._isRestartable)
/*  169:     */       {
/*  170: 310 */         this._startNode = node;
/*  171: 311 */         this._currentNode = (node == -1 ? -1 : DTMDefaultBaseIterators.this._firstch(DTMDefaultBaseIterators.this.makeNodeIdentity(node)));
/*  172:     */         
/*  173:     */ 
/*  174: 314 */         return resetPosition();
/*  175:     */       }
/*  176: 317 */       return this;
/*  177:     */     }
/*  178:     */     
/*  179:     */     public int next()
/*  180:     */     {
/*  181: 328 */       if (this._currentNode != -1)
/*  182:     */       {
/*  183: 329 */         int node = this._currentNode;
/*  184: 330 */         this._currentNode = DTMDefaultBaseIterators.this._nextsib(node);
/*  185: 331 */         return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*  186:     */       }
/*  187: 334 */       return -1;
/*  188:     */     }
/*  189:     */   }
/*  190:     */   
/*  191:     */   public final class ParentIterator
/*  192:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  193:     */   {
/*  194:     */     public ParentIterator()
/*  195:     */     {
/*  196: 343 */       super();
/*  197:     */     }
/*  198:     */     
/*  199: 347 */     private int _nodeType = -1;
/*  200:     */     
/*  201:     */     public DTMAxisIterator setStartNode(int node)
/*  202:     */     {
/*  203: 360 */       if (node == 0) {
/*  204: 361 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  205:     */       }
/*  206: 362 */       if (this._isRestartable)
/*  207:     */       {
/*  208: 364 */         this._startNode = node;
/*  209: 365 */         this._currentNode = DTMDefaultBaseIterators.this.getParent(node);
/*  210:     */         
/*  211: 367 */         return resetPosition();
/*  212:     */       }
/*  213: 370 */       return this;
/*  214:     */     }
/*  215:     */     
/*  216:     */     public DTMAxisIterator setNodeType(int type)
/*  217:     */     {
/*  218: 386 */       this._nodeType = type;
/*  219:     */       
/*  220: 388 */       return this;
/*  221:     */     }
/*  222:     */     
/*  223:     */     public int next()
/*  224:     */     {
/*  225: 399 */       int result = this._currentNode;
/*  226: 401 */       if (this._nodeType >= 14)
/*  227:     */       {
/*  228: 402 */         if (this._nodeType != DTMDefaultBaseIterators.this.getExpandedTypeID(this._currentNode)) {
/*  229: 403 */           result = -1;
/*  230:     */         }
/*  231:     */       }
/*  232: 405 */       else if ((this._nodeType != -1) && 
/*  233: 406 */         (this._nodeType != DTMDefaultBaseIterators.this.getNodeType(this._currentNode))) {
/*  234: 407 */         result = -1;
/*  235:     */       }
/*  236: 411 */       this._currentNode = -1;
/*  237:     */       
/*  238: 413 */       return returnNode(result);
/*  239:     */     }
/*  240:     */   }
/*  241:     */   
/*  242:     */   public final class TypedChildrenIterator
/*  243:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  244:     */   {
/*  245:     */     private final int _nodeType;
/*  246:     */     
/*  247:     */     public TypedChildrenIterator(int nodeType)
/*  248:     */     {
/*  249: 436 */       super();
/*  250: 437 */       this._nodeType = nodeType;
/*  251:     */     }
/*  252:     */     
/*  253:     */     public DTMAxisIterator setStartNode(int node)
/*  254:     */     {
/*  255: 451 */       if (node == 0) {
/*  256: 452 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  257:     */       }
/*  258: 453 */       if (this._isRestartable)
/*  259:     */       {
/*  260: 455 */         this._startNode = node;
/*  261: 456 */         this._currentNode = (node == -1 ? -1 : DTMDefaultBaseIterators.this._firstch(DTMDefaultBaseIterators.this.makeNodeIdentity(this._startNode)));
/*  262:     */         
/*  263:     */ 
/*  264:     */ 
/*  265: 460 */         return resetPosition();
/*  266:     */       }
/*  267: 463 */       return this;
/*  268:     */     }
/*  269:     */     
/*  270:     */     public int next()
/*  271:     */     {
/*  272: 474 */       int node = this._currentNode;
/*  273:     */       
/*  274: 476 */       int nodeType = this._nodeType;
/*  275: 478 */       if (nodeType >= 14) {
/*  276:     */         do
/*  277:     */         {
/*  278: 480 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*  279: 479 */           if (node == -1) {
/*  280:     */             break;
/*  281:     */           }
/*  282: 479 */         } while (DTMDefaultBaseIterators.this._exptype(node) != nodeType);
/*  283:     */       } else {
/*  284: 483 */         while (node != -1)
/*  285:     */         {
/*  286: 484 */           int eType = DTMDefaultBaseIterators.this._exptype(node);
/*  287: 485 */           if (eType < 14 ? 
/*  288: 486 */             eType == nodeType : 
/*  289:     */             
/*  290:     */ 
/*  291: 489 */             DTMDefaultBaseIterators.this.m_expandedNameTable.getType(eType) == nodeType) {
/*  292:     */             break;
/*  293:     */           }
/*  294: 492 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*  295:     */         }
/*  296:     */       }
/*  297: 496 */       if (node == -1)
/*  298:     */       {
/*  299: 497 */         this._currentNode = -1;
/*  300: 498 */         return -1;
/*  301:     */       }
/*  302: 500 */       this._currentNode = DTMDefaultBaseIterators.this._nextsib(node);
/*  303: 501 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*  304:     */     }
/*  305:     */   }
/*  306:     */   
/*  307:     */   public final class NamespaceChildrenIterator
/*  308:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  309:     */   {
/*  310:     */     private final int _nsType;
/*  311:     */     
/*  312:     */     public NamespaceChildrenIterator(int type)
/*  313:     */     {
/*  314: 527 */       super();
/*  315: 528 */       this._nsType = type;
/*  316:     */     }
/*  317:     */     
/*  318:     */     public DTMAxisIterator setStartNode(int node)
/*  319:     */     {
/*  320: 542 */       if (node == 0) {
/*  321: 543 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  322:     */       }
/*  323: 544 */       if (this._isRestartable)
/*  324:     */       {
/*  325: 546 */         this._startNode = node;
/*  326: 547 */         this._currentNode = (node == -1 ? -1 : -2);
/*  327:     */         
/*  328: 549 */         return resetPosition();
/*  329:     */       }
/*  330: 552 */       return this;
/*  331:     */     }
/*  332:     */     
/*  333:     */     public int next()
/*  334:     */     {
/*  335: 562 */       if (this._currentNode != -1) {
/*  336: 563 */         for (int node = -2 == this._currentNode ? DTMDefaultBaseIterators.this._firstch(DTMDefaultBaseIterators.this.makeNodeIdentity(this._startNode)) : DTMDefaultBaseIterators.this._nextsib(this._currentNode); node != -1; node = DTMDefaultBaseIterators.this._nextsib(node)) {
/*  337: 568 */           if (DTMDefaultBaseIterators.this.m_expandedNameTable.getNamespaceID(DTMDefaultBaseIterators.this._exptype(node)) == this._nsType)
/*  338:     */           {
/*  339: 569 */             this._currentNode = node;
/*  340:     */             
/*  341: 571 */             return returnNode(node);
/*  342:     */           }
/*  343:     */         }
/*  344:     */       }
/*  345: 576 */       return -1;
/*  346:     */     }
/*  347:     */   }
/*  348:     */   
/*  349:     */   public class NamespaceIterator
/*  350:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  351:     */   {
/*  352:     */     public NamespaceIterator()
/*  353:     */     {
/*  354: 594 */       super();
/*  355:     */     }
/*  356:     */     
/*  357:     */     public DTMAxisIterator setStartNode(int node)
/*  358:     */     {
/*  359: 608 */       if (node == 0) {
/*  360: 609 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  361:     */       }
/*  362: 610 */       if (this._isRestartable)
/*  363:     */       {
/*  364: 612 */         this._startNode = node;
/*  365: 613 */         this._currentNode = DTMDefaultBaseIterators.this.getFirstNamespaceNode(node, true);
/*  366:     */         
/*  367: 615 */         return resetPosition();
/*  368:     */       }
/*  369: 618 */       return this;
/*  370:     */     }
/*  371:     */     
/*  372:     */     public int next()
/*  373:     */     {
/*  374: 629 */       int node = this._currentNode;
/*  375: 631 */       if (-1 != node) {
/*  376: 632 */         this._currentNode = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, node, true);
/*  377:     */       }
/*  378: 634 */       return returnNode(node);
/*  379:     */     }
/*  380:     */   }
/*  381:     */   
/*  382:     */   public class TypedNamespaceIterator
/*  383:     */     extends DTMDefaultBaseIterators.NamespaceIterator
/*  384:     */   {
/*  385:     */     private final int _nodeType;
/*  386:     */     
/*  387:     */     public TypedNamespaceIterator(int nodeType)
/*  388:     */     {
/*  389: 656 */       super();
/*  390: 657 */       this._nodeType = nodeType;
/*  391:     */     }
/*  392:     */     
/*  393:     */     public int next()
/*  394:     */     {
/*  395: 669 */       for (int node = this._currentNode; node != -1; node = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, node, true)) {
/*  396: 672 */         if ((DTMDefaultBaseIterators.this.getExpandedTypeID(node) == this._nodeType) || (DTMDefaultBaseIterators.this.getNodeType(node) == this._nodeType) || (DTMDefaultBaseIterators.this.getNamespaceType(node) == this._nodeType))
/*  397:     */         {
/*  398: 675 */           this._currentNode = node;
/*  399:     */           
/*  400: 677 */           return returnNode(node);
/*  401:     */         }
/*  402:     */       }
/*  403: 681 */       return this._currentNode = -1;
/*  404:     */     }
/*  405:     */   }
/*  406:     */   
/*  407:     */   public class RootIterator
/*  408:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  409:     */   {
/*  410:     */     public RootIterator()
/*  411:     */     {
/*  412: 699 */       super();
/*  413:     */     }
/*  414:     */     
/*  415:     */     public DTMAxisIterator setStartNode(int node)
/*  416:     */     {
/*  417: 713 */       if (this._isRestartable)
/*  418:     */       {
/*  419: 715 */         this._startNode = DTMDefaultBaseIterators.this.getDocumentRoot(node);
/*  420: 716 */         this._currentNode = -1;
/*  421:     */         
/*  422: 718 */         return resetPosition();
/*  423:     */       }
/*  424: 721 */       return this;
/*  425:     */     }
/*  426:     */     
/*  427:     */     public int next()
/*  428:     */     {
/*  429: 731 */       if (this._startNode == this._currentNode) {
/*  430: 732 */         return -1;
/*  431:     */       }
/*  432: 734 */       this._currentNode = this._startNode;
/*  433:     */       
/*  434: 736 */       return returnNode(this._startNode);
/*  435:     */     }
/*  436:     */   }
/*  437:     */   
/*  438:     */   public class TypedRootIterator
/*  439:     */     extends DTMDefaultBaseIterators.RootIterator
/*  440:     */   {
/*  441:     */     private final int _nodeType;
/*  442:     */     
/*  443:     */     public TypedRootIterator(int nodeType)
/*  444:     */     {
/*  445: 757 */       super();
/*  446: 758 */       this._nodeType = nodeType;
/*  447:     */     }
/*  448:     */     
/*  449:     */     public int next()
/*  450:     */     {
/*  451: 768 */       if (this._startNode == this._currentNode) {
/*  452: 769 */         return -1;
/*  453:     */       }
/*  454: 771 */       int nodeType = this._nodeType;
/*  455: 772 */       int node = this._startNode;
/*  456: 773 */       int expType = DTMDefaultBaseIterators.this.getExpandedTypeID(node);
/*  457:     */       
/*  458: 775 */       this._currentNode = node;
/*  459: 777 */       if (nodeType >= 14)
/*  460:     */       {
/*  461: 778 */         if (nodeType == expType) {
/*  462: 779 */           return returnNode(node);
/*  463:     */         }
/*  464:     */       }
/*  465: 782 */       else if (expType < 14)
/*  466:     */       {
/*  467: 783 */         if (expType == nodeType) {
/*  468: 784 */           return returnNode(node);
/*  469:     */         }
/*  470:     */       }
/*  471: 787 */       else if (DTMDefaultBaseIterators.this.m_expandedNameTable.getType(expType) == nodeType) {
/*  472: 788 */         return returnNode(node);
/*  473:     */       }
/*  474: 793 */       return -1;
/*  475:     */     }
/*  476:     */   }
/*  477:     */   
/*  478:     */   public final class NamespaceAttributeIterator
/*  479:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  480:     */   {
/*  481:     */     private final int _nsType;
/*  482:     */     
/*  483:     */     public NamespaceAttributeIterator(int nsType)
/*  484:     */     {
/*  485: 816 */       super();
/*  486:     */       
/*  487: 818 */       this._nsType = nsType;
/*  488:     */     }
/*  489:     */     
/*  490:     */     public DTMAxisIterator setStartNode(int node)
/*  491:     */     {
/*  492: 832 */       if (node == 0) {
/*  493: 833 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  494:     */       }
/*  495: 834 */       if (this._isRestartable)
/*  496:     */       {
/*  497: 836 */         this._startNode = node;
/*  498: 837 */         this._currentNode = DTMDefaultBaseIterators.this.getFirstNamespaceNode(node, false);
/*  499:     */         
/*  500: 839 */         return resetPosition();
/*  501:     */       }
/*  502: 842 */       return this;
/*  503:     */     }
/*  504:     */     
/*  505:     */     public int next()
/*  506:     */     {
/*  507: 853 */       int node = this._currentNode;
/*  508: 855 */       if (-1 != node) {
/*  509: 856 */         this._currentNode = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, node, false);
/*  510:     */       }
/*  511: 858 */       return returnNode(node);
/*  512:     */     }
/*  513:     */   }
/*  514:     */   
/*  515:     */   public class FollowingSiblingIterator
/*  516:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  517:     */   {
/*  518:     */     public FollowingSiblingIterator()
/*  519:     */     {
/*  520: 865 */       super();
/*  521:     */     }
/*  522:     */     
/*  523:     */     public DTMAxisIterator setStartNode(int node)
/*  524:     */     {
/*  525: 879 */       if (node == 0) {
/*  526: 880 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  527:     */       }
/*  528: 881 */       if (this._isRestartable)
/*  529:     */       {
/*  530: 883 */         this._startNode = node;
/*  531: 884 */         this._currentNode = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/*  532:     */         
/*  533: 886 */         return resetPosition();
/*  534:     */       }
/*  535: 889 */       return this;
/*  536:     */     }
/*  537:     */     
/*  538:     */     public int next()
/*  539:     */     {
/*  540: 899 */       this._currentNode = (this._currentNode == -1 ? -1 : DTMDefaultBaseIterators.this._nextsib(this._currentNode));
/*  541:     */       
/*  542: 901 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._currentNode));
/*  543:     */     }
/*  544:     */   }
/*  545:     */   
/*  546:     */   public final class TypedFollowingSiblingIterator
/*  547:     */     extends DTMDefaultBaseIterators.FollowingSiblingIterator
/*  548:     */   {
/*  549:     */     private final int _nodeType;
/*  550:     */     
/*  551:     */     public TypedFollowingSiblingIterator(int type)
/*  552:     */     {
/*  553: 922 */       super();
/*  554: 923 */       this._nodeType = type;
/*  555:     */     }
/*  556:     */     
/*  557:     */     public int next()
/*  558:     */     {
/*  559: 933 */       if (this._currentNode == -1) {
/*  560: 934 */         return -1;
/*  561:     */       }
/*  562: 937 */       int node = this._currentNode;
/*  563:     */       
/*  564: 939 */       int nodeType = this._nodeType;
/*  565: 941 */       if (nodeType >= 14) {
/*  566:     */         do
/*  567:     */         {
/*  568: 943 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*  569: 944 */           if (node == -1) {
/*  570:     */             break;
/*  571:     */           }
/*  572: 944 */         } while (DTMDefaultBaseIterators.this._exptype(node) != nodeType);
/*  573:     */       } else {
/*  574: 946 */         while ((node = DTMDefaultBaseIterators.this._nextsib(node)) != -1)
/*  575:     */         {
/*  576: 947 */           int eType = DTMDefaultBaseIterators.this._exptype(node);
/*  577: 948 */           if (eType < 14 ? 
/*  578: 949 */             eType == nodeType : 
/*  579:     */             
/*  580:     */ 
/*  581: 952 */             DTMDefaultBaseIterators.this.m_expandedNameTable.getType(eType) == nodeType) {
/*  582:     */             break;
/*  583:     */           }
/*  584:     */         }
/*  585:     */       }
/*  586: 958 */       this._currentNode = node;
/*  587:     */       
/*  588: 960 */       return this._currentNode == -1 ? -1 : returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._currentNode));
/*  589:     */     }
/*  590:     */   }
/*  591:     */   
/*  592:     */   public final class AttributeIterator
/*  593:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  594:     */   {
/*  595:     */     public AttributeIterator()
/*  596:     */     {
/*  597: 969 */       super();
/*  598:     */     }
/*  599:     */     
/*  600:     */     public DTMAxisIterator setStartNode(int node)
/*  601:     */     {
/*  602: 985 */       if (node == 0) {
/*  603: 986 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  604:     */       }
/*  605: 987 */       if (this._isRestartable)
/*  606:     */       {
/*  607: 989 */         this._startNode = node;
/*  608: 990 */         this._currentNode = DTMDefaultBaseIterators.this.getFirstAttributeIdentity(DTMDefaultBaseIterators.this.makeNodeIdentity(node));
/*  609:     */         
/*  610: 992 */         return resetPosition();
/*  611:     */       }
/*  612: 995 */       return this;
/*  613:     */     }
/*  614:     */     
/*  615:     */     public int next()
/*  616:     */     {
/*  617:1006 */       int node = this._currentNode;
/*  618:1008 */       if (node != -1)
/*  619:     */       {
/*  620:1009 */         this._currentNode = DTMDefaultBaseIterators.this.getNextAttributeIdentity(node);
/*  621:1010 */         return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*  622:     */       }
/*  623:1013 */       return -1;
/*  624:     */     }
/*  625:     */   }
/*  626:     */   
/*  627:     */   public final class TypedAttributeIterator
/*  628:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  629:     */   {
/*  630:     */     private final int _nodeType;
/*  631:     */     
/*  632:     */     public TypedAttributeIterator(int nodeType)
/*  633:     */     {
/*  634:1033 */       super();
/*  635:1034 */       this._nodeType = nodeType;
/*  636:     */     }
/*  637:     */     
/*  638:     */     public DTMAxisIterator setStartNode(int node)
/*  639:     */     {
/*  640:1049 */       if (this._isRestartable)
/*  641:     */       {
/*  642:1051 */         this._startNode = node;
/*  643:     */         
/*  644:1053 */         this._currentNode = DTMDefaultBaseIterators.this.getTypedAttribute(node, this._nodeType);
/*  645:     */         
/*  646:1055 */         return resetPosition();
/*  647:     */       }
/*  648:1058 */       return this;
/*  649:     */     }
/*  650:     */     
/*  651:     */     public int next()
/*  652:     */     {
/*  653:1069 */       int node = this._currentNode;
/*  654:     */       
/*  655:     */ 
/*  656:     */ 
/*  657:1073 */       this._currentNode = -1;
/*  658:     */       
/*  659:1075 */       return returnNode(node);
/*  660:     */     }
/*  661:     */   }
/*  662:     */   
/*  663:     */   public class PrecedingSiblingIterator
/*  664:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  665:     */   {
/*  666:     */     protected int _startNodeID;
/*  667:     */     
/*  668:     */     public PrecedingSiblingIterator()
/*  669:     */     {
/*  670:1082 */       super();
/*  671:     */     }
/*  672:     */     
/*  673:     */     public boolean isReverse()
/*  674:     */     {
/*  675:1097 */       return true;
/*  676:     */     }
/*  677:     */     
/*  678:     */     public DTMAxisIterator setStartNode(int node)
/*  679:     */     {
/*  680:1111 */       if (node == 0) {
/*  681:1112 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  682:     */       }
/*  683:1113 */       if (this._isRestartable)
/*  684:     */       {
/*  685:1115 */         this._startNode = node;
/*  686:1116 */         node = this._startNodeID = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/*  687:1118 */         if (node == -1)
/*  688:     */         {
/*  689:1120 */           this._currentNode = node;
/*  690:1121 */           return resetPosition();
/*  691:     */         }
/*  692:1124 */         int type = DTMDefaultBaseIterators.this.m_expandedNameTable.getType(DTMDefaultBaseIterators.this._exptype(node));
/*  693:1125 */         if ((2 == type) || (13 == type))
/*  694:     */         {
/*  695:1128 */           this._currentNode = node;
/*  696:     */         }
/*  697:     */         else
/*  698:     */         {
/*  699:1133 */           this._currentNode = DTMDefaultBaseIterators.this._parent(node);
/*  700:1134 */           if (-1 != this._currentNode) {
/*  701:1135 */             this._currentNode = DTMDefaultBaseIterators.this._firstch(this._currentNode);
/*  702:     */           } else {
/*  703:1137 */             this._currentNode = node;
/*  704:     */           }
/*  705:     */         }
/*  706:1140 */         return resetPosition();
/*  707:     */       }
/*  708:1143 */       return this;
/*  709:     */     }
/*  710:     */     
/*  711:     */     public int next()
/*  712:     */     {
/*  713:1154 */       if ((this._currentNode == this._startNodeID) || (this._currentNode == -1)) {
/*  714:1156 */         return -1;
/*  715:     */       }
/*  716:1160 */       int node = this._currentNode;
/*  717:1161 */       this._currentNode = DTMDefaultBaseIterators.this._nextsib(node);
/*  718:     */       
/*  719:1163 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*  720:     */     }
/*  721:     */   }
/*  722:     */   
/*  723:     */   public final class TypedPrecedingSiblingIterator
/*  724:     */     extends DTMDefaultBaseIterators.PrecedingSiblingIterator
/*  725:     */   {
/*  726:     */     private final int _nodeType;
/*  727:     */     
/*  728:     */     public TypedPrecedingSiblingIterator(int type)
/*  729:     */     {
/*  730:1186 */       super();
/*  731:1187 */       this._nodeType = type;
/*  732:     */     }
/*  733:     */     
/*  734:     */     public int next()
/*  735:     */     {
/*  736:1197 */       int node = this._currentNode;
/*  737:     */       
/*  738:     */ 
/*  739:1200 */       int nodeType = this._nodeType;
/*  740:1201 */       int startID = this._startNodeID;
/*  741:1203 */       if (nodeType >= 14) {
/*  742:     */         do
/*  743:     */         {
/*  744:1205 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*  745:1204 */           if ((node == -1) || (node == startID)) {
/*  746:     */             break;
/*  747:     */           }
/*  748:1204 */         } while (DTMDefaultBaseIterators.this._exptype(node) != nodeType);
/*  749:     */       } else {
/*  750:1208 */         while ((node != -1) && (node != startID))
/*  751:     */         {
/*  752:1209 */           int expType = DTMDefaultBaseIterators.this._exptype(node);
/*  753:1210 */           if (expType < 14 ? 
/*  754:1211 */             expType == nodeType : 
/*  755:     */             
/*  756:     */ 
/*  757:     */ 
/*  758:1215 */             DTMDefaultBaseIterators.this.m_expandedNameTable.getType(expType) == nodeType) {
/*  759:     */             break;
/*  760:     */           }
/*  761:1219 */           node = DTMDefaultBaseIterators.this._nextsib(node);
/*  762:     */         }
/*  763:     */       }
/*  764:1223 */       if ((node == -1) || (node == this._startNodeID))
/*  765:     */       {
/*  766:1224 */         this._currentNode = -1;
/*  767:1225 */         return -1;
/*  768:     */       }
/*  769:1227 */       this._currentNode = DTMDefaultBaseIterators.this._nextsib(node);
/*  770:1228 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*  771:     */     }
/*  772:     */   }
/*  773:     */   
/*  774:     */   public class PrecedingIterator
/*  775:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  776:     */   {
/*  777:     */     public PrecedingIterator()
/*  778:     */     {
/*  779:1238 */       super();
/*  780:     */     }
/*  781:     */     
/*  782:1242 */     private final int _maxAncestors = 8;
/*  783:1248 */     protected int[] _stack = new int[8];
/*  784:     */     protected int _sp;
/*  785:     */     protected int _oldsp;
/*  786:     */     protected int _markedsp;
/*  787:     */     protected int _markedNode;
/*  788:     */     protected int _markedDescendant;
/*  789:     */     
/*  790:     */     public boolean isReverse()
/*  791:     */     {
/*  792:1264 */       return true;
/*  793:     */     }
/*  794:     */     
/*  795:     */     public DTMAxisIterator cloneIterator()
/*  796:     */     {
/*  797:1274 */       this._isRestartable = false;
/*  798:     */       try
/*  799:     */       {
/*  800:1278 */         PrecedingIterator clone = (PrecedingIterator)super.clone();
/*  801:1279 */         int[] stackCopy = new int[this._stack.length];
/*  802:1280 */         System.arraycopy(this._stack, 0, stackCopy, 0, this._stack.length);
/*  803:     */         
/*  804:1282 */         clone._stack = stackCopy;
/*  805:     */         
/*  806:     */ 
/*  807:1285 */         return clone;
/*  808:     */       }
/*  809:     */       catch (CloneNotSupportedException e)
/*  810:     */       {
/*  811:1289 */         throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
/*  812:     */       }
/*  813:     */     }
/*  814:     */     
/*  815:     */     public DTMAxisIterator setStartNode(int node)
/*  816:     */     {
/*  817:1304 */       if (node == 0) {
/*  818:1305 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  819:     */       }
/*  820:1306 */       if (this._isRestartable)
/*  821:     */       {
/*  822:1308 */         node = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/*  823:1313 */         if (DTMDefaultBaseIterators.this._type(node) == 2) {
/*  824:1314 */           node = DTMDefaultBaseIterators.this._parent(node);
/*  825:     */         }
/*  826:1316 */         this._startNode = node; int 
/*  827:1317 */           tmp59_58 = 0;int index = tmp59_58;this._stack[tmp59_58] = node;
/*  828:     */         
/*  829:     */ 
/*  830:     */ 
/*  831:1321 */         int parent = node;
/*  832:1322 */         while ((parent = DTMDefaultBaseIterators.this._parent(parent)) != -1)
/*  833:     */         {
/*  834:1324 */           index++;
/*  835:1324 */           if (index == this._stack.length)
/*  836:     */           {
/*  837:1326 */             int[] stack = new int[index + 4];
/*  838:1327 */             System.arraycopy(this._stack, 0, stack, 0, index);
/*  839:1328 */             this._stack = stack;
/*  840:     */           }
/*  841:1330 */           this._stack[index] = parent;
/*  842:     */         }
/*  843:1332 */         if (index > 0) {
/*  844:1333 */           index--;
/*  845:     */         }
/*  846:1335 */         this._currentNode = this._stack[index];
/*  847:     */         
/*  848:1337 */         this._oldsp = (this._sp = index);
/*  849:     */         
/*  850:1339 */         return resetPosition();
/*  851:     */       }
/*  852:1342 */       return this;
/*  853:     */     }
/*  854:     */     
/*  855:     */     public int next()
/*  856:     */     {
/*  857:1355 */       for (this._currentNode += 1; this._sp >= 0; this._currentNode += 1) {
/*  858:1359 */         if (this._currentNode < this._stack[this._sp])
/*  859:     */         {
/*  860:1361 */           if ((DTMDefaultBaseIterators.this._type(this._currentNode) != 2) && (DTMDefaultBaseIterators.this._type(this._currentNode) != 13)) {
/*  861:1363 */             return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._currentNode));
/*  862:     */           }
/*  863:     */         }
/*  864:     */         else {
/*  865:1366 */           this._sp -= 1;
/*  866:     */         }
/*  867:     */       }
/*  868:1368 */       return -1;
/*  869:     */     }
/*  870:     */     
/*  871:     */     public DTMAxisIterator reset()
/*  872:     */     {
/*  873:1382 */       this._sp = this._oldsp;
/*  874:     */       
/*  875:1384 */       return resetPosition();
/*  876:     */     }
/*  877:     */     
/*  878:     */     public void setMark()
/*  879:     */     {
/*  880:1388 */       this._markedsp = this._sp;
/*  881:1389 */       this._markedNode = this._currentNode;
/*  882:1390 */       this._markedDescendant = this._stack[0];
/*  883:     */     }
/*  884:     */     
/*  885:     */     public void gotoMark()
/*  886:     */     {
/*  887:1394 */       this._sp = this._markedsp;
/*  888:1395 */       this._currentNode = this._markedNode;
/*  889:     */     }
/*  890:     */   }
/*  891:     */   
/*  892:     */   public final class TypedPrecedingIterator
/*  893:     */     extends DTMDefaultBaseIterators.PrecedingIterator
/*  894:     */   {
/*  895:     */     private final int _nodeType;
/*  896:     */     
/*  897:     */     public TypedPrecedingIterator(int type)
/*  898:     */     {
/*  899:1417 */       super();
/*  900:1418 */       this._nodeType = type;
/*  901:     */     }
/*  902:     */     
/*  903:     */     public int next()
/*  904:     */     {
/*  905:1428 */       int node = this._currentNode;
/*  906:1429 */       int nodeType = this._nodeType;
/*  907:1431 */       if (nodeType >= 14) {
/*  908:     */         do
/*  909:     */         {
/*  910:     */           do
/*  911:     */           {
/*  912:1433 */             node += 1;
/*  913:1435 */             if (this._sp < 0)
/*  914:     */             {
/*  915:1436 */               node = -1;
/*  916:     */               break label171;
/*  917:     */             }
/*  918:1438 */             if (node < this._stack[this._sp]) {
/*  919:     */               break;
/*  920:     */             }
/*  921:1439 */           } while (--this._sp >= 0);
/*  922:1440 */           node = -1;
/*  923:1441 */           break;
/*  924:1443 */         } while (DTMDefaultBaseIterators.this._exptype(node) != nodeType);
/*  925:     */       } else {
/*  926:     */         for (;;)
/*  927:     */         {
/*  928:1451 */           node += 1;
/*  929:1453 */           if (this._sp < 0)
/*  930:     */           {
/*  931:1454 */             node = -1;
/*  932:     */           }
/*  933:1456 */           else if (node >= this._stack[this._sp])
/*  934:     */           {
/*  935:1457 */             if (--this._sp < 0) {
/*  936:1458 */               node = -1;
/*  937:     */             }
/*  938:     */           }
/*  939:     */           else
/*  940:     */           {
/*  941:1462 */             int expType = DTMDefaultBaseIterators.this._exptype(node);
/*  942:1463 */             if (expType < 14)
/*  943:     */             {
/*  944:1464 */               if (expType == nodeType) {
/*  945:     */                 break;
/*  946:     */               }
/*  947:     */             }
/*  948:1468 */             else if (DTMDefaultBaseIterators.this.m_expandedNameTable.getType(expType) == nodeType) {
/*  949:     */               break;
/*  950:     */             }
/*  951:     */           }
/*  952:     */         }
/*  953:     */       }
/*  954:     */       label171:
/*  955:1476 */       this._currentNode = node;
/*  956:1478 */       return node == -1 ? -1 : returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/*  957:     */     }
/*  958:     */   }
/*  959:     */   
/*  960:     */   public class FollowingIterator
/*  961:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  962:     */   {
/*  963:     */     DTMAxisTraverser m_traverser;
/*  964:     */     
/*  965:     */     public FollowingIterator()
/*  966:     */     {
/*  967:1490 */       super();
/*  968:1491 */       this.m_traverser = DTMDefaultBaseIterators.this.getAxisTraverser(6);
/*  969:     */     }
/*  970:     */     
/*  971:     */     public DTMAxisIterator setStartNode(int node)
/*  972:     */     {
/*  973:1505 */       if (node == 0) {
/*  974:1506 */         node = DTMDefaultBaseIterators.this.getDocument();
/*  975:     */       }
/*  976:1507 */       if (this._isRestartable)
/*  977:     */       {
/*  978:1509 */         this._startNode = node;
/*  979:     */         
/*  980:     */ 
/*  981:     */ 
/*  982:     */ 
/*  983:     */ 
/*  984:     */ 
/*  985:1516 */         this._currentNode = this.m_traverser.first(node);
/*  986:     */         
/*  987:     */ 
/*  988:1519 */         return resetPosition();
/*  989:     */       }
/*  990:1522 */       return this;
/*  991:     */     }
/*  992:     */     
/*  993:     */     public int next()
/*  994:     */     {
/*  995:1533 */       int node = this._currentNode;
/*  996:     */       
/*  997:1535 */       this._currentNode = this.m_traverser.next(this._startNode, this._currentNode);
/*  998:     */       
/*  999:1537 */       return returnNode(node);
/* 1000:     */     }
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public final class TypedFollowingIterator
/* 1004:     */     extends DTMDefaultBaseIterators.FollowingIterator
/* 1005:     */   {
/* 1006:     */     private final int _nodeType;
/* 1007:     */     
/* 1008:     */     public TypedFollowingIterator(int type)
/* 1009:     */     {
/* 1010:1557 */       super();
/* 1011:1558 */       this._nodeType = type;
/* 1012:     */     }
/* 1013:     */     
/* 1014:     */     public int next()
/* 1015:     */     {
/* 1016:     */       int node;
/* 1017:     */       do
/* 1018:     */       {
/* 1019:1572 */         node = this._currentNode;
/* 1020:     */         
/* 1021:1574 */         this._currentNode = this.m_traverser.next(this._startNode, this._currentNode);
/* 1022:1578 */       } while ((node != -1) && (DTMDefaultBaseIterators.this.getExpandedTypeID(node) != this._nodeType) && (DTMDefaultBaseIterators.this.getNodeType(node) != this._nodeType));
/* 1023:1580 */       return node == -1 ? -1 : returnNode(node);
/* 1024:     */     }
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public class AncestorIterator
/* 1028:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/* 1029:     */   {
/* 1030:     */     public AncestorIterator()
/* 1031:     */     {
/* 1032:1588 */       super();
/* 1033:     */     }
/* 1034:     */     
/* 1035:1590 */     NodeVector m_ancestors = new NodeVector();
/* 1036:     */     int m_ancestorsPos;
/* 1037:     */     int m_markedPos;
/* 1038:     */     int m_realStartNode;
/* 1039:     */     
/* 1040:     */     public int getStartNode()
/* 1041:     */     {
/* 1042:1608 */       return this.m_realStartNode;
/* 1043:     */     }
/* 1044:     */     
/* 1045:     */     public final boolean isReverse()
/* 1046:     */     {
/* 1047:1618 */       return true;
/* 1048:     */     }
/* 1049:     */     
/* 1050:     */     public DTMAxisIterator cloneIterator()
/* 1051:     */     {
/* 1052:1628 */       this._isRestartable = false;
/* 1053:     */       try
/* 1054:     */       {
/* 1055:1632 */         AncestorIterator clone = (AncestorIterator)super.clone();
/* 1056:     */         
/* 1057:1634 */         clone._startNode = this._startNode;
/* 1058:     */         
/* 1059:     */ 
/* 1060:1637 */         return clone;
/* 1061:     */       }
/* 1062:     */       catch (CloneNotSupportedException e)
/* 1063:     */       {
/* 1064:1641 */         throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
/* 1065:     */       }
/* 1066:     */     }
/* 1067:     */     
/* 1068:     */     public DTMAxisIterator setStartNode(int node)
/* 1069:     */     {
/* 1070:1656 */       if (node == 0) {
/* 1071:1657 */         node = DTMDefaultBaseIterators.this.getDocument();
/* 1072:     */       }
/* 1073:1658 */       this.m_realStartNode = node;
/* 1074:1660 */       if (this._isRestartable)
/* 1075:     */       {
/* 1076:1662 */         int nodeID = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/* 1077:1664 */         if ((!this._includeSelf) && (node != -1))
/* 1078:     */         {
/* 1079:1665 */           nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/* 1080:1666 */           node = DTMDefaultBaseIterators.this.makeNodeHandle(nodeID);
/* 1081:     */         }
/* 1082:1669 */         this._startNode = node;
/* 1083:1671 */         while (nodeID != -1)
/* 1084:     */         {
/* 1085:1672 */           this.m_ancestors.addElement(node);
/* 1086:1673 */           nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/* 1087:1674 */           node = DTMDefaultBaseIterators.this.makeNodeHandle(nodeID);
/* 1088:     */         }
/* 1089:1676 */         this.m_ancestorsPos = (this.m_ancestors.size() - 1);
/* 1090:     */         
/* 1091:1678 */         this._currentNode = (this.m_ancestorsPos >= 0 ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1);
/* 1092:     */         
/* 1093:     */ 
/* 1094:     */ 
/* 1095:1682 */         return resetPosition();
/* 1096:     */       }
/* 1097:1685 */       return this;
/* 1098:     */     }
/* 1099:     */     
/* 1100:     */     public DTMAxisIterator reset()
/* 1101:     */     {
/* 1102:1697 */       this.m_ancestorsPos = (this.m_ancestors.size() - 1);
/* 1103:     */       
/* 1104:1699 */       this._currentNode = (this.m_ancestorsPos >= 0 ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1);
/* 1105:     */       
/* 1106:     */ 
/* 1107:1702 */       return resetPosition();
/* 1108:     */     }
/* 1109:     */     
/* 1110:     */     public int next()
/* 1111:     */     {
/* 1112:1713 */       int next = this._currentNode;
/* 1113:     */       
/* 1114:1715 */       int pos = --this.m_ancestorsPos;
/* 1115:     */       
/* 1116:1717 */       this._currentNode = (pos >= 0 ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1);
/* 1117:     */       
/* 1118:     */ 
/* 1119:1720 */       return returnNode(next);
/* 1120:     */     }
/* 1121:     */     
/* 1122:     */     public void setMark()
/* 1123:     */     {
/* 1124:1724 */       this.m_markedPos = this.m_ancestorsPos;
/* 1125:     */     }
/* 1126:     */     
/* 1127:     */     public void gotoMark()
/* 1128:     */     {
/* 1129:1728 */       this.m_ancestorsPos = this.m_markedPos;
/* 1130:1729 */       this._currentNode = (this.m_ancestorsPos >= 0 ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1);
/* 1131:     */     }
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public final class TypedAncestorIterator
/* 1135:     */     extends DTMDefaultBaseIterators.AncestorIterator
/* 1136:     */   {
/* 1137:     */     private final int _nodeType;
/* 1138:     */     
/* 1139:     */     public TypedAncestorIterator(int type)
/* 1140:     */     {
/* 1141:1750 */       super();
/* 1142:1751 */       this._nodeType = type;
/* 1143:     */     }
/* 1144:     */     
/* 1145:     */     public DTMAxisIterator setStartNode(int node)
/* 1146:     */     {
/* 1147:1765 */       if (node == 0) {
/* 1148:1766 */         node = DTMDefaultBaseIterators.this.getDocument();
/* 1149:     */       }
/* 1150:1767 */       this.m_realStartNode = node;
/* 1151:1769 */       if (this._isRestartable)
/* 1152:     */       {
/* 1153:1771 */         int nodeID = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/* 1154:1772 */         int nodeType = this._nodeType;
/* 1155:1774 */         if ((!this._includeSelf) && (node != -1)) {
/* 1156:1775 */           nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/* 1157:     */         }
/* 1158:1778 */         this._startNode = node;
/* 1159:1780 */         if (nodeType >= 14) {
/* 1160:1781 */           while (nodeID != -1)
/* 1161:     */           {
/* 1162:1782 */             eType = DTMDefaultBaseIterators.this._exptype(nodeID);
/* 1163:1784 */             if (eType == nodeType) {
/* 1164:1785 */               this.m_ancestors.addElement(DTMDefaultBaseIterators.this.makeNodeHandle(nodeID));
/* 1165:     */             }
/* 1166:1787 */             nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/* 1167:     */           }
/* 1168:     */         } else {
/* 1169:1790 */           while (nodeID != -1)
/* 1170:     */           {
/* 1171:     */             int eType;
/* 1172:1791 */             int eType = DTMDefaultBaseIterators.this._exptype(nodeID);
/* 1173:1793 */             if (((eType >= 14) && (DTMDefaultBaseIterators.this.m_expandedNameTable.getType(eType) == nodeType)) || ((eType < 14) && (eType == nodeType))) {
/* 1174:1796 */               this.m_ancestors.addElement(DTMDefaultBaseIterators.this.makeNodeHandle(nodeID));
/* 1175:     */             }
/* 1176:1798 */             nodeID = DTMDefaultBaseIterators.this._parent(nodeID);
/* 1177:     */           }
/* 1178:     */         }
/* 1179:1801 */         this.m_ancestorsPos = (this.m_ancestors.size() - 1);
/* 1180:     */         
/* 1181:1803 */         this._currentNode = (this.m_ancestorsPos >= 0 ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1);
/* 1182:     */         
/* 1183:     */ 
/* 1184:     */ 
/* 1185:1807 */         return resetPosition();
/* 1186:     */       }
/* 1187:1810 */       return this;
/* 1188:     */     }
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public class DescendantIterator
/* 1192:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/* 1193:     */   {
/* 1194:     */     public DescendantIterator()
/* 1195:     */     {
/* 1196:1817 */       super();
/* 1197:     */     }
/* 1198:     */     
/* 1199:     */     public DTMAxisIterator setStartNode(int node)
/* 1200:     */     {
/* 1201:1831 */       if (node == 0) {
/* 1202:1832 */         node = DTMDefaultBaseIterators.this.getDocument();
/* 1203:     */       }
/* 1204:1833 */       if (this._isRestartable)
/* 1205:     */       {
/* 1206:1835 */         node = DTMDefaultBaseIterators.this.makeNodeIdentity(node);
/* 1207:1836 */         this._startNode = node;
/* 1208:1838 */         if (this._includeSelf) {
/* 1209:1839 */           node--;
/* 1210:     */         }
/* 1211:1841 */         this._currentNode = node;
/* 1212:     */         
/* 1213:1843 */         return resetPosition();
/* 1214:     */       }
/* 1215:1846 */       return this;
/* 1216:     */     }
/* 1217:     */     
/* 1218:     */     protected boolean isDescendant(int identity)
/* 1219:     */     {
/* 1220:1865 */       return (DTMDefaultBaseIterators.this._parent(identity) >= this._startNode) || (this._startNode == identity);
/* 1221:     */     }
/* 1222:     */     
/* 1223:     */     public int next()
/* 1224:     */     {
/* 1225:1875 */       if (this._startNode == -1) {
/* 1226:1876 */         return -1;
/* 1227:     */       }
/* 1228:1879 */       if ((this._includeSelf) && (this._currentNode + 1 == this._startNode)) {
/* 1229:1880 */         return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(++this._currentNode));
/* 1230:     */       }
/* 1231:1882 */       int node = this._currentNode;
/* 1232:     */       int type;
/* 1233:     */       do
/* 1234:     */       {
/* 1235:1886 */         node++;
/* 1236:1887 */         type = DTMDefaultBaseIterators.this._type(node);
/* 1237:1889 */         if ((-1 == type) || (!isDescendant(node)))
/* 1238:     */         {
/* 1239:1890 */           this._currentNode = -1;
/* 1240:1891 */           return -1;
/* 1241:     */         }
/* 1242:1894 */       } while ((2 == type) || (3 == type) || (13 == type));
/* 1243:1896 */       this._currentNode = node;
/* 1244:1897 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/* 1245:     */     }
/* 1246:     */     
/* 1247:     */     public DTMAxisIterator reset()
/* 1248:     */     {
/* 1249:1907 */       boolean temp = this._isRestartable;
/* 1250:     */       
/* 1251:1909 */       this._isRestartable = true;
/* 1252:     */       
/* 1253:1911 */       setStartNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._startNode));
/* 1254:     */       
/* 1255:1913 */       this._isRestartable = temp;
/* 1256:     */       
/* 1257:1915 */       return this;
/* 1258:     */     }
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   public final class TypedDescendantIterator
/* 1262:     */     extends DTMDefaultBaseIterators.DescendantIterator
/* 1263:     */   {
/* 1264:     */     private final int _nodeType;
/* 1265:     */     
/* 1266:     */     public TypedDescendantIterator(int nodeType)
/* 1267:     */     {
/* 1268:1936 */       super();
/* 1269:1937 */       this._nodeType = nodeType;
/* 1270:     */     }
/* 1271:     */     
/* 1272:     */     public int next()
/* 1273:     */     {
/* 1274:1950 */       if (this._startNode == -1) {
/* 1275:1951 */         return -1;
/* 1276:     */       }
/* 1277:1954 */       int node = this._currentNode;
/* 1278:     */       int type;
/* 1279:     */       do
/* 1280:     */       {
/* 1281:1958 */         node++;
/* 1282:1959 */         type = DTMDefaultBaseIterators.this._type(node);
/* 1283:1961 */         if ((-1 == type) || (!isDescendant(node)))
/* 1284:     */         {
/* 1285:1962 */           this._currentNode = -1;
/* 1286:1963 */           return -1;
/* 1287:     */         }
/* 1288:1966 */       } while ((type != this._nodeType) && (DTMDefaultBaseIterators.this._exptype(node) != this._nodeType));
/* 1289:1968 */       this._currentNode = node;
/* 1290:1969 */       return returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(node));
/* 1291:     */     }
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   public class NthDescendantIterator
/* 1295:     */     extends DTMDefaultBaseIterators.DescendantIterator
/* 1296:     */   {
/* 1297:     */     int _pos;
/* 1298:     */     
/* 1299:     */     public NthDescendantIterator(int pos)
/* 1300:     */     {
/* 1301:1990 */       super();
/* 1302:1991 */       this._pos = pos;
/* 1303:     */     }
/* 1304:     */     
/* 1305:     */     public int next()
/* 1306:     */     {
/* 1307:     */       int node;
/* 1308:2005 */       while ((node = super.next()) != -1)
/* 1309:     */       {
/* 1310:     */         int i;
/* 1311:2007 */         node = DTMDefaultBaseIterators.this.makeNodeIdentity(i);
/* 1312:     */         
/* 1313:2009 */         int parent = DTMDefaultBaseIterators.this._parent(node);
/* 1314:2010 */         int child = DTMDefaultBaseIterators.this._firstch(parent);
/* 1315:2011 */         int pos = 0;
/* 1316:     */         do
/* 1317:     */         {
/* 1318:2015 */           int type = DTMDefaultBaseIterators.this._type(child);
/* 1319:2017 */           if (1 == type) {
/* 1320:2018 */             pos++;
/* 1321:     */           }
/* 1322:2020 */         } while ((pos < this._pos) && ((child = DTMDefaultBaseIterators.this._nextsib(child)) != -1));
/* 1323:2022 */         if (node == child) {
/* 1324:2023 */           return node;
/* 1325:     */         }
/* 1326:     */       }
/* 1327:2026 */       return -1;
/* 1328:     */     }
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   public class SingletonIterator
/* 1332:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/* 1333:     */   {
/* 1334:     */     private boolean _isConstant;
/* 1335:     */     
/* 1336:     */     public SingletonIterator()
/* 1337:     */     {
/* 1338:2045 */       this(-2147483648, false);
/* 1339:     */     }
/* 1340:     */     
/* 1341:     */     public SingletonIterator(int node)
/* 1342:     */     {
/* 1343:2056 */       this(node, false);
/* 1344:     */     }
/* 1345:     */     
/* 1346:     */     public SingletonIterator(int node, boolean constant)
/* 1347:     */     {
/* 1348:2067 */       super();
/* 1349:2068 */       this._currentNode = (this._startNode = node);
/* 1350:2069 */       this._isConstant = constant;
/* 1351:     */     }
/* 1352:     */     
/* 1353:     */     public DTMAxisIterator setStartNode(int node)
/* 1354:     */     {
/* 1355:2083 */       if (node == 0) {
/* 1356:2084 */         node = DTMDefaultBaseIterators.this.getDocument();
/* 1357:     */       }
/* 1358:2085 */       if (this._isConstant)
/* 1359:     */       {
/* 1360:2087 */         this._currentNode = this._startNode;
/* 1361:     */         
/* 1362:2089 */         return resetPosition();
/* 1363:     */       }
/* 1364:2091 */       if (this._isRestartable)
/* 1365:     */       {
/* 1366:2093 */         this._currentNode = (this._startNode = node);
/* 1367:     */         
/* 1368:2095 */         return resetPosition();
/* 1369:     */       }
/* 1370:2098 */       return this;
/* 1371:     */     }
/* 1372:     */     
/* 1373:     */     public DTMAxisIterator reset()
/* 1374:     */     {
/* 1375:2110 */       if (this._isConstant)
/* 1376:     */       {
/* 1377:2112 */         this._currentNode = this._startNode;
/* 1378:     */         
/* 1379:2114 */         return resetPosition();
/* 1380:     */       }
/* 1381:2118 */       boolean temp = this._isRestartable;
/* 1382:     */       
/* 1383:2120 */       this._isRestartable = true;
/* 1384:     */       
/* 1385:2122 */       setStartNode(this._startNode);
/* 1386:     */       
/* 1387:2124 */       this._isRestartable = temp;
/* 1388:     */       
/* 1389:     */ 
/* 1390:2127 */       return this;
/* 1391:     */     }
/* 1392:     */     
/* 1393:     */     public int next()
/* 1394:     */     {
/* 1395:2138 */       int result = this._currentNode;
/* 1396:     */       
/* 1397:2140 */       this._currentNode = -1;
/* 1398:     */       
/* 1399:2142 */       return returnNode(result);
/* 1400:     */     }
/* 1401:     */   }
/* 1402:     */   
/* 1403:     */   public final class TypedSingletonIterator
/* 1404:     */     extends DTMDefaultBaseIterators.SingletonIterator
/* 1405:     */   {
/* 1406:     */     private final int _nodeType;
/* 1407:     */     
/* 1408:     */     public TypedSingletonIterator(int nodeType)
/* 1409:     */     {
/* 1410:2162 */       super();
/* 1411:2163 */       this._nodeType = nodeType;
/* 1412:     */     }
/* 1413:     */     
/* 1414:     */     public int next()
/* 1415:     */     {
/* 1416:2175 */       int result = this._currentNode;
/* 1417:2176 */       int nodeType = this._nodeType;
/* 1418:     */       
/* 1419:2178 */       this._currentNode = -1;
/* 1420:2180 */       if (nodeType >= 14)
/* 1421:     */       {
/* 1422:2181 */         if (DTMDefaultBaseIterators.this.getExpandedTypeID(result) == nodeType) {
/* 1423:2182 */           return returnNode(result);
/* 1424:     */         }
/* 1425:     */       }
/* 1426:2185 */       else if (DTMDefaultBaseIterators.this.getNodeType(result) == nodeType) {
/* 1427:2186 */         return returnNode(result);
/* 1428:     */       }
/* 1429:2190 */       return -1;
/* 1430:     */     }
/* 1431:     */   }
/* 1432:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMDefaultBaseIterators
 * JD-Core Version:    0.7.0.1
 */