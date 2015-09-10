/*    1:     */ package org.apache.xml.dtm.ref.sax2dtm;
/*    2:     */ 
/*    3:     */ import java.util.Vector;
/*    4:     */ import javax.xml.transform.Source;
/*    5:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*    6:     */ import org.apache.xml.dtm.DTMException;
/*    7:     */ import org.apache.xml.dtm.DTMManager;
/*    8:     */ import org.apache.xml.dtm.DTMWSFilter;
/*    9:     */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   10:     */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   11:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.InternalAxisIteratorBase;
/*   12:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.RootIterator;
/*   13:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.SingletonIterator;
/*   14:     */ import org.apache.xml.dtm.ref.DTMStringPool;
/*   15:     */ import org.apache.xml.dtm.ref.ExpandedNameTable;
/*   16:     */ import org.apache.xml.dtm.ref.ExtendedType;
/*   17:     */ import org.apache.xml.res.XMLMessages;
/*   18:     */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   19:     */ import org.apache.xml.serializer.SerializationHandler;
/*   20:     */ import org.apache.xml.utils.FastStringBuffer;
/*   21:     */ import org.apache.xml.utils.IntStack;
/*   22:     */ import org.apache.xml.utils.SuballocatedIntVector;
/*   23:     */ import org.apache.xml.utils.XMLString;
/*   24:     */ import org.apache.xml.utils.XMLStringDefault;
/*   25:     */ import org.apache.xml.utils.XMLStringFactory;
/*   26:     */ import org.xml.sax.Attributes;
/*   27:     */ import org.xml.sax.ContentHandler;
/*   28:     */ import org.xml.sax.SAXException;
/*   29:     */ 
/*   30:     */ public class SAX2DTM2
/*   31:     */   extends SAX2DTM
/*   32:     */ {
/*   33:     */   private int[] m_exptype_map0;
/*   34:     */   private int[] m_nextsib_map0;
/*   35:     */   private int[] m_firstch_map0;
/*   36:     */   private int[] m_parent_map0;
/*   37:     */   private int[][] m_exptype_map;
/*   38:     */   private int[][] m_nextsib_map;
/*   39:     */   private int[][] m_firstch_map;
/*   40:     */   private int[][] m_parent_map;
/*   41:     */   protected ExtendedType[] m_extendedTypes;
/*   42:     */   protected Vector m_values;
/*   43:     */   
/*   44:     */   public final class ChildrenIterator
/*   45:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*   46:     */   {
/*   47:     */     public ChildrenIterator()
/*   48:     */     {
/*   49:  71 */       super();
/*   50:     */     }
/*   51:     */     
/*   52:     */     public DTMAxisIterator setStartNode(int node)
/*   53:     */     {
/*   54:  89 */       if (node == 0) {
/*   55:  90 */         node = SAX2DTM2.this.getDocument();
/*   56:     */       }
/*   57:  91 */       if (this._isRestartable)
/*   58:     */       {
/*   59:  93 */         this._startNode = node;
/*   60:  94 */         this._currentNode = (node == -1 ? -1 : SAX2DTM2.this._firstch2(SAX2DTM2.this.makeNodeIdentity(node)));
/*   61:     */         
/*   62:     */ 
/*   63:  97 */         return resetPosition();
/*   64:     */       }
/*   65: 100 */       return this;
/*   66:     */     }
/*   67:     */     
/*   68:     */     public int next()
/*   69:     */     {
/*   70: 111 */       if (this._currentNode != -1)
/*   71:     */       {
/*   72: 112 */         int node = this._currentNode;
/*   73: 113 */         this._currentNode = SAX2DTM2.this._nextsib2(node);
/*   74: 114 */         return returnNode(SAX2DTM2.this.makeNodeHandle(node));
/*   75:     */       }
/*   76: 117 */       return -1;
/*   77:     */     }
/*   78:     */   }
/*   79:     */   
/*   80:     */   public final class ParentIterator
/*   81:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*   82:     */   {
/*   83:     */     public ParentIterator()
/*   84:     */     {
/*   85: 126 */       super();
/*   86:     */     }
/*   87:     */     
/*   88: 130 */     private int _nodeType = -1;
/*   89:     */     
/*   90:     */     public DTMAxisIterator setStartNode(int node)
/*   91:     */     {
/*   92: 143 */       if (node == 0) {
/*   93: 144 */         node = SAX2DTM2.this.getDocument();
/*   94:     */       }
/*   95: 145 */       if (this._isRestartable)
/*   96:     */       {
/*   97: 147 */         this._startNode = node;
/*   98: 149 */         if (node != -1) {
/*   99: 150 */           this._currentNode = SAX2DTM2.this._parent2(SAX2DTM2.this.makeNodeIdentity(node));
/*  100:     */         } else {
/*  101: 152 */           this._currentNode = -1;
/*  102:     */         }
/*  103: 154 */         return resetPosition();
/*  104:     */       }
/*  105: 157 */       return this;
/*  106:     */     }
/*  107:     */     
/*  108:     */     public DTMAxisIterator setNodeType(int type)
/*  109:     */     {
/*  110: 173 */       this._nodeType = type;
/*  111:     */       
/*  112: 175 */       return this;
/*  113:     */     }
/*  114:     */     
/*  115:     */     public int next()
/*  116:     */     {
/*  117: 186 */       int result = this._currentNode;
/*  118: 187 */       if (result == -1) {
/*  119: 188 */         return -1;
/*  120:     */       }
/*  121: 191 */       if (this._nodeType == -1)
/*  122:     */       {
/*  123: 192 */         this._currentNode = -1;
/*  124: 193 */         return returnNode(SAX2DTM2.this.makeNodeHandle(result));
/*  125:     */       }
/*  126: 195 */       if (this._nodeType >= 14)
/*  127:     */       {
/*  128: 196 */         if (this._nodeType == SAX2DTM2.this._exptype2(result))
/*  129:     */         {
/*  130: 197 */           this._currentNode = -1;
/*  131: 198 */           return returnNode(SAX2DTM2.this.makeNodeHandle(result));
/*  132:     */         }
/*  133:     */       }
/*  134: 202 */       else if (this._nodeType == SAX2DTM2.this._type2(result))
/*  135:     */       {
/*  136: 203 */         this._currentNode = -1;
/*  137: 204 */         return returnNode(SAX2DTM2.this.makeNodeHandle(result));
/*  138:     */       }
/*  139: 208 */       return -1;
/*  140:     */     }
/*  141:     */   }
/*  142:     */   
/*  143:     */   public final class TypedChildrenIterator
/*  144:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  145:     */   {
/*  146:     */     private final int _nodeType;
/*  147:     */     
/*  148:     */     public TypedChildrenIterator(int nodeType)
/*  149:     */     {
/*  150: 231 */       super();
/*  151: 232 */       this._nodeType = nodeType;
/*  152:     */     }
/*  153:     */     
/*  154:     */     public DTMAxisIterator setStartNode(int node)
/*  155:     */     {
/*  156: 246 */       if (node == 0) {
/*  157: 247 */         node = SAX2DTM2.this.getDocument();
/*  158:     */       }
/*  159: 248 */       if (this._isRestartable)
/*  160:     */       {
/*  161: 250 */         this._startNode = node;
/*  162: 251 */         this._currentNode = (node == -1 ? -1 : SAX2DTM2.this._firstch2(SAX2DTM2.this.makeNodeIdentity(this._startNode)));
/*  163:     */         
/*  164:     */ 
/*  165:     */ 
/*  166: 255 */         return resetPosition();
/*  167:     */       }
/*  168: 258 */       return this;
/*  169:     */     }
/*  170:     */     
/*  171:     */     public int next()
/*  172:     */     {
/*  173: 268 */       int node = this._currentNode;
/*  174: 269 */       if (node == -1) {
/*  175: 270 */         return -1;
/*  176:     */       }
/*  177: 272 */       int nodeType = this._nodeType;
/*  178: 274 */       if (nodeType != 1) {
/*  179:     */         do
/*  180:     */         {
/*  181: 276 */           node = SAX2DTM2.this._nextsib2(node);
/*  182: 275 */           if (node == -1) {
/*  183:     */             break;
/*  184:     */           }
/*  185: 275 */         } while (SAX2DTM2.this._exptype2(node) != nodeType);
/*  186:     */       } else {
/*  187: 286 */         while (node != -1)
/*  188:     */         {
/*  189: 287 */           int eType = SAX2DTM2.this._exptype2(node);
/*  190: 288 */           if (eType >= 14) {
/*  191:     */             break;
/*  192:     */           }
/*  193: 291 */           node = SAX2DTM2.this._nextsib2(node);
/*  194:     */         }
/*  195:     */       }
/*  196: 295 */       if (node == -1)
/*  197:     */       {
/*  198: 296 */         this._currentNode = -1;
/*  199: 297 */         return -1;
/*  200:     */       }
/*  201: 299 */       this._currentNode = SAX2DTM2.this._nextsib2(node);
/*  202: 300 */       return returnNode(SAX2DTM2.this.makeNodeHandle(node));
/*  203:     */     }
/*  204:     */     
/*  205:     */     public int getNodeByPosition(int position)
/*  206:     */     {
/*  207: 310 */       if (position <= 0) {
/*  208: 311 */         return -1;
/*  209:     */       }
/*  210: 313 */       int node = this._currentNode;
/*  211: 314 */       int pos = 0;
/*  212:     */       
/*  213: 316 */       int nodeType = this._nodeType;
/*  214: 317 */       if (nodeType != 1)
/*  215:     */       {
/*  216: 318 */         while (node != -1)
/*  217:     */         {
/*  218: 319 */           if (SAX2DTM2.this._exptype2(node) == nodeType)
/*  219:     */           {
/*  220: 320 */             pos++;
/*  221: 321 */             if (pos == position) {
/*  222: 322 */               return SAX2DTM2.this.makeNodeHandle(node);
/*  223:     */             }
/*  224:     */           }
/*  225: 325 */           node = SAX2DTM2.this._nextsib2(node);
/*  226:     */         }
/*  227: 327 */         return -1;
/*  228:     */       }
/*  229: 330 */       while (node != -1)
/*  230:     */       {
/*  231: 331 */         if (SAX2DTM2.this._exptype2(node) >= 14)
/*  232:     */         {
/*  233: 332 */           pos++;
/*  234: 333 */           if (pos == position) {
/*  235: 334 */             return SAX2DTM2.this.makeNodeHandle(node);
/*  236:     */           }
/*  237:     */         }
/*  238: 336 */         node = SAX2DTM2.this._nextsib2(node);
/*  239:     */       }
/*  240: 338 */       return -1;
/*  241:     */     }
/*  242:     */   }
/*  243:     */   
/*  244:     */   public class TypedRootIterator
/*  245:     */     extends DTMDefaultBaseIterators.RootIterator
/*  246:     */   {
/*  247:     */     private final int _nodeType;
/*  248:     */     
/*  249:     */     public TypedRootIterator(int nodeType)
/*  250:     */     {
/*  251: 361 */       super();
/*  252: 362 */       this._nodeType = nodeType;
/*  253:     */     }
/*  254:     */     
/*  255:     */     public int next()
/*  256:     */     {
/*  257: 372 */       if (this._startNode == this._currentNode) {
/*  258: 373 */         return -1;
/*  259:     */       }
/*  260: 375 */       int node = this._startNode;
/*  261: 376 */       int expType = SAX2DTM2.this._exptype2(SAX2DTM2.this.makeNodeIdentity(node));
/*  262:     */       
/*  263: 378 */       this._currentNode = node;
/*  264: 380 */       if (this._nodeType >= 14)
/*  265:     */       {
/*  266: 381 */         if (this._nodeType == expType) {
/*  267: 382 */           return returnNode(node);
/*  268:     */         }
/*  269:     */       }
/*  270: 386 */       else if (expType < 14)
/*  271:     */       {
/*  272: 387 */         if (expType == this._nodeType) {
/*  273: 388 */           return returnNode(node);
/*  274:     */         }
/*  275:     */       }
/*  276: 392 */       else if (SAX2DTM2.this.m_extendedTypes[expType].getNodeType() == this._nodeType) {
/*  277: 393 */         return returnNode(node);
/*  278:     */       }
/*  279: 398 */       return -1;
/*  280:     */     }
/*  281:     */   }
/*  282:     */   
/*  283:     */   public class FollowingSiblingIterator
/*  284:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  285:     */   {
/*  286:     */     public FollowingSiblingIterator()
/*  287:     */     {
/*  288: 405 */       super();
/*  289:     */     }
/*  290:     */     
/*  291:     */     public DTMAxisIterator setStartNode(int node)
/*  292:     */     {
/*  293: 419 */       if (node == 0) {
/*  294: 420 */         node = SAX2DTM2.this.getDocument();
/*  295:     */       }
/*  296: 421 */       if (this._isRestartable)
/*  297:     */       {
/*  298: 423 */         this._startNode = node;
/*  299: 424 */         this._currentNode = SAX2DTM2.this.makeNodeIdentity(node);
/*  300:     */         
/*  301: 426 */         return resetPosition();
/*  302:     */       }
/*  303: 429 */       return this;
/*  304:     */     }
/*  305:     */     
/*  306:     */     public int next()
/*  307:     */     {
/*  308: 439 */       this._currentNode = (this._currentNode == -1 ? -1 : SAX2DTM2.this._nextsib2(this._currentNode));
/*  309:     */       
/*  310: 441 */       return returnNode(SAX2DTM2.this.makeNodeHandle(this._currentNode));
/*  311:     */     }
/*  312:     */   }
/*  313:     */   
/*  314:     */   public final class TypedFollowingSiblingIterator
/*  315:     */     extends SAX2DTM2.FollowingSiblingIterator
/*  316:     */   {
/*  317:     */     private final int _nodeType;
/*  318:     */     
/*  319:     */     public TypedFollowingSiblingIterator(int type)
/*  320:     */     {
/*  321: 462 */       super();
/*  322: 463 */       this._nodeType = type;
/*  323:     */     }
/*  324:     */     
/*  325:     */     public int next()
/*  326:     */     {
/*  327: 473 */       if (this._currentNode == -1) {
/*  328: 474 */         return -1;
/*  329:     */       }
/*  330: 477 */       int node = this._currentNode;
/*  331: 478 */       int nodeType = this._nodeType;
/*  332: 480 */       if (nodeType != 1) {
/*  333:     */         do
/*  334:     */         {
/*  335: 481 */           if ((node = SAX2DTM2.this._nextsib2(node)) == -1) {
/*  336:     */             break;
/*  337:     */           }
/*  338: 481 */         } while (SAX2DTM2.this._exptype2(node) != nodeType);
/*  339:     */       }
/*  340: 484 */       while (((node = SAX2DTM2.this._nextsib2(node)) != -1) && (SAX2DTM2.this._exptype2(node) < 14)) {}
/*  341: 487 */       this._currentNode = node;
/*  342:     */       
/*  343: 489 */       return node == -1 ? -1 : returnNode(SAX2DTM2.this.makeNodeHandle(node));
/*  344:     */     }
/*  345:     */   }
/*  346:     */   
/*  347:     */   public final class AttributeIterator
/*  348:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  349:     */   {
/*  350:     */     public AttributeIterator()
/*  351:     */     {
/*  352: 499 */       super();
/*  353:     */     }
/*  354:     */     
/*  355:     */     public DTMAxisIterator setStartNode(int node)
/*  356:     */     {
/*  357: 515 */       if (node == 0) {
/*  358: 516 */         node = SAX2DTM2.this.getDocument();
/*  359:     */       }
/*  360: 517 */       if (this._isRestartable)
/*  361:     */       {
/*  362: 519 */         this._startNode = node;
/*  363: 520 */         this._currentNode = SAX2DTM2.this.getFirstAttributeIdentity(SAX2DTM2.this.makeNodeIdentity(node));
/*  364:     */         
/*  365: 522 */         return resetPosition();
/*  366:     */       }
/*  367: 525 */       return this;
/*  368:     */     }
/*  369:     */     
/*  370:     */     public int next()
/*  371:     */     {
/*  372: 536 */       int node = this._currentNode;
/*  373: 538 */       if (node != -1)
/*  374:     */       {
/*  375: 539 */         this._currentNode = SAX2DTM2.this.getNextAttributeIdentity(node);
/*  376: 540 */         return returnNode(SAX2DTM2.this.makeNodeHandle(node));
/*  377:     */       }
/*  378: 543 */       return -1;
/*  379:     */     }
/*  380:     */   }
/*  381:     */   
/*  382:     */   public final class TypedAttributeIterator
/*  383:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  384:     */   {
/*  385:     */     private final int _nodeType;
/*  386:     */     
/*  387:     */     public TypedAttributeIterator(int nodeType)
/*  388:     */     {
/*  389: 563 */       super();
/*  390: 564 */       this._nodeType = nodeType;
/*  391:     */     }
/*  392:     */     
/*  393:     */     public DTMAxisIterator setStartNode(int node)
/*  394:     */     {
/*  395: 579 */       if (this._isRestartable)
/*  396:     */       {
/*  397: 581 */         this._startNode = node;
/*  398:     */         
/*  399: 583 */         this._currentNode = SAX2DTM2.this.getTypedAttribute(node, this._nodeType);
/*  400:     */         
/*  401: 585 */         return resetPosition();
/*  402:     */       }
/*  403: 588 */       return this;
/*  404:     */     }
/*  405:     */     
/*  406:     */     public int next()
/*  407:     */     {
/*  408: 599 */       int node = this._currentNode;
/*  409:     */       
/*  410:     */ 
/*  411:     */ 
/*  412: 603 */       this._currentNode = -1;
/*  413:     */       
/*  414: 605 */       return returnNode(node);
/*  415:     */     }
/*  416:     */   }
/*  417:     */   
/*  418:     */   public class PrecedingSiblingIterator
/*  419:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  420:     */   {
/*  421:     */     protected int _startNodeID;
/*  422:     */     
/*  423:     */     public PrecedingSiblingIterator()
/*  424:     */     {
/*  425: 612 */       super();
/*  426:     */     }
/*  427:     */     
/*  428:     */     public boolean isReverse()
/*  429:     */     {
/*  430: 627 */       return true;
/*  431:     */     }
/*  432:     */     
/*  433:     */     public DTMAxisIterator setStartNode(int node)
/*  434:     */     {
/*  435: 641 */       if (node == 0) {
/*  436: 642 */         node = SAX2DTM2.this.getDocument();
/*  437:     */       }
/*  438: 643 */       if (this._isRestartable)
/*  439:     */       {
/*  440: 645 */         this._startNode = node;
/*  441: 646 */         node = this._startNodeID = SAX2DTM2.this.makeNodeIdentity(node);
/*  442: 648 */         if (node == -1)
/*  443:     */         {
/*  444: 650 */           this._currentNode = node;
/*  445: 651 */           return resetPosition();
/*  446:     */         }
/*  447: 654 */         int type = SAX2DTM2.this._type2(node);
/*  448: 655 */         if ((2 == type) || (13 == type))
/*  449:     */         {
/*  450: 658 */           this._currentNode = node;
/*  451:     */         }
/*  452:     */         else
/*  453:     */         {
/*  454: 663 */           this._currentNode = SAX2DTM2.this._parent2(node);
/*  455: 664 */           if (-1 != this._currentNode) {
/*  456: 665 */             this._currentNode = SAX2DTM2.this._firstch2(this._currentNode);
/*  457:     */           } else {
/*  458: 667 */             this._currentNode = node;
/*  459:     */           }
/*  460:     */         }
/*  461: 670 */         return resetPosition();
/*  462:     */       }
/*  463: 673 */       return this;
/*  464:     */     }
/*  465:     */     
/*  466:     */     public int next()
/*  467:     */     {
/*  468: 684 */       if ((this._currentNode == this._startNodeID) || (this._currentNode == -1)) {
/*  469: 686 */         return -1;
/*  470:     */       }
/*  471: 690 */       int node = this._currentNode;
/*  472: 691 */       this._currentNode = SAX2DTM2.this._nextsib2(node);
/*  473:     */       
/*  474: 693 */       return returnNode(SAX2DTM2.this.makeNodeHandle(node));
/*  475:     */     }
/*  476:     */   }
/*  477:     */   
/*  478:     */   public final class TypedPrecedingSiblingIterator
/*  479:     */     extends SAX2DTM2.PrecedingSiblingIterator
/*  480:     */   {
/*  481:     */     private final int _nodeType;
/*  482:     */     
/*  483:     */     public TypedPrecedingSiblingIterator(int type)
/*  484:     */     {
/*  485: 716 */       super();
/*  486: 717 */       this._nodeType = type;
/*  487:     */     }
/*  488:     */     
/*  489:     */     public int next()
/*  490:     */     {
/*  491: 727 */       int node = this._currentNode;
/*  492:     */       
/*  493: 729 */       int nodeType = this._nodeType;
/*  494: 730 */       int startNodeID = this._startNodeID;
/*  495: 732 */       if (nodeType != 1) {
/*  496:     */         do
/*  497:     */         {
/*  498: 734 */           node = SAX2DTM2.this._nextsib2(node);
/*  499: 733 */           if ((node == -1) || (node == startNodeID)) {
/*  500:     */             break;
/*  501:     */           }
/*  502: 733 */         } while (SAX2DTM2.this._exptype2(node) != nodeType);
/*  503:     */       } else {
/*  504: 738 */         while ((node != -1) && (node != startNodeID) && (SAX2DTM2.this._exptype2(node) < 14)) {
/*  505: 739 */           node = SAX2DTM2.this._nextsib2(node);
/*  506:     */         }
/*  507:     */       }
/*  508: 743 */       if ((node == -1) || (node == startNodeID))
/*  509:     */       {
/*  510: 744 */         this._currentNode = -1;
/*  511: 745 */         return -1;
/*  512:     */       }
/*  513: 748 */       this._currentNode = SAX2DTM2.this._nextsib2(node);
/*  514: 749 */       return returnNode(SAX2DTM2.this.makeNodeHandle(node));
/*  515:     */     }
/*  516:     */     
/*  517:     */     public int getLast()
/*  518:     */     {
/*  519: 758 */       if (this._last != -1) {
/*  520: 759 */         return this._last;
/*  521:     */       }
/*  522: 761 */       setMark();
/*  523:     */       
/*  524: 763 */       int node = this._currentNode;
/*  525: 764 */       int nodeType = this._nodeType;
/*  526: 765 */       int startNodeID = this._startNodeID;
/*  527:     */       
/*  528: 767 */       int last = 0;
/*  529: 768 */       if (nodeType != 1) {
/*  530:     */         do
/*  531:     */         {
/*  532: 770 */           if (SAX2DTM2.this._exptype2(node) == nodeType) {
/*  533: 771 */             last++;
/*  534:     */           }
/*  535: 773 */           node = SAX2DTM2.this._nextsib2(node);
/*  536: 769 */           if (node == -1) {
/*  537:     */             break;
/*  538:     */           }
/*  539: 769 */         } while (node != startNodeID);
/*  540:     */       } else {
/*  541: 777 */         while ((node != -1) && (node != startNodeID))
/*  542:     */         {
/*  543: 778 */           if (SAX2DTM2.this._exptype2(node) >= 14) {
/*  544: 779 */             last++;
/*  545:     */           }
/*  546: 781 */           node = SAX2DTM2.this._nextsib2(node);
/*  547:     */         }
/*  548:     */       }
/*  549: 785 */       gotoMark();
/*  550:     */       
/*  551: 787 */       return this._last = last;
/*  552:     */     }
/*  553:     */   }
/*  554:     */   
/*  555:     */   public class PrecedingIterator
/*  556:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  557:     */   {
/*  558:     */     public PrecedingIterator()
/*  559:     */     {
/*  560: 796 */       super();
/*  561:     */     }
/*  562:     */     
/*  563: 800 */     private final int _maxAncestors = 8;
/*  564: 806 */     protected int[] _stack = new int[8];
/*  565:     */     protected int _sp;
/*  566:     */     protected int _oldsp;
/*  567:     */     protected int _markedsp;
/*  568:     */     protected int _markedNode;
/*  569:     */     protected int _markedDescendant;
/*  570:     */     
/*  571:     */     public boolean isReverse()
/*  572:     */     {
/*  573: 822 */       return true;
/*  574:     */     }
/*  575:     */     
/*  576:     */     public DTMAxisIterator cloneIterator()
/*  577:     */     {
/*  578: 832 */       this._isRestartable = false;
/*  579:     */       try
/*  580:     */       {
/*  581: 836 */         PrecedingIterator clone = (PrecedingIterator)super.clone();
/*  582: 837 */         int[] stackCopy = new int[this._stack.length];
/*  583: 838 */         System.arraycopy(this._stack, 0, stackCopy, 0, this._stack.length);
/*  584:     */         
/*  585: 840 */         clone._stack = stackCopy;
/*  586:     */         
/*  587:     */ 
/*  588: 843 */         return clone;
/*  589:     */       }
/*  590:     */       catch (CloneNotSupportedException e)
/*  591:     */       {
/*  592: 847 */         throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
/*  593:     */       }
/*  594:     */     }
/*  595:     */     
/*  596:     */     public DTMAxisIterator setStartNode(int node)
/*  597:     */     {
/*  598: 862 */       if (node == 0) {
/*  599: 863 */         node = SAX2DTM2.this.getDocument();
/*  600:     */       }
/*  601: 864 */       if (this._isRestartable)
/*  602:     */       {
/*  603: 866 */         node = SAX2DTM2.this.makeNodeIdentity(node);
/*  604: 871 */         if (SAX2DTM2.this._type2(node) == 2) {
/*  605: 872 */           node = SAX2DTM2.this._parent2(node);
/*  606:     */         }
/*  607: 874 */         this._startNode = node; int 
/*  608: 875 */           tmp59_58 = 0;int index = tmp59_58;this._stack[tmp59_58] = node;
/*  609:     */         
/*  610: 877 */         int parent = node;
/*  611: 878 */         while ((parent = SAX2DTM2.this._parent2(parent)) != -1)
/*  612:     */         {
/*  613: 880 */           index++;
/*  614: 880 */           if (index == this._stack.length)
/*  615:     */           {
/*  616: 882 */             int[] stack = new int[index * 2];
/*  617: 883 */             System.arraycopy(this._stack, 0, stack, 0, index);
/*  618: 884 */             this._stack = stack;
/*  619:     */           }
/*  620: 886 */           this._stack[index] = parent;
/*  621:     */         }
/*  622: 889 */         if (index > 0) {
/*  623: 890 */           index--;
/*  624:     */         }
/*  625: 892 */         this._currentNode = this._stack[index];
/*  626:     */         
/*  627: 894 */         this._oldsp = (this._sp = index);
/*  628:     */         
/*  629: 896 */         return resetPosition();
/*  630:     */       }
/*  631: 899 */       return this;
/*  632:     */     }
/*  633:     */     
/*  634:     */     public int next()
/*  635:     */     {
/*  636: 912 */       for (this._currentNode += 1; this._sp >= 0; this._currentNode += 1) {
/*  637: 914 */         if (this._currentNode < this._stack[this._sp])
/*  638:     */         {
/*  639: 916 */           int type = SAX2DTM2.this._type2(this._currentNode);
/*  640: 917 */           if ((type != 2) && (type != 13)) {
/*  641: 918 */             return returnNode(SAX2DTM2.this.makeNodeHandle(this._currentNode));
/*  642:     */           }
/*  643:     */         }
/*  644:     */         else
/*  645:     */         {
/*  646: 921 */           this._sp -= 1;
/*  647:     */         }
/*  648:     */       }
/*  649: 923 */       return -1;
/*  650:     */     }
/*  651:     */     
/*  652:     */     public DTMAxisIterator reset()
/*  653:     */     {
/*  654: 937 */       this._sp = this._oldsp;
/*  655:     */       
/*  656: 939 */       return resetPosition();
/*  657:     */     }
/*  658:     */     
/*  659:     */     public void setMark()
/*  660:     */     {
/*  661: 943 */       this._markedsp = this._sp;
/*  662: 944 */       this._markedNode = this._currentNode;
/*  663: 945 */       this._markedDescendant = this._stack[0];
/*  664:     */     }
/*  665:     */     
/*  666:     */     public void gotoMark()
/*  667:     */     {
/*  668: 949 */       this._sp = this._markedsp;
/*  669: 950 */       this._currentNode = this._markedNode;
/*  670:     */     }
/*  671:     */   }
/*  672:     */   
/*  673:     */   public final class TypedPrecedingIterator
/*  674:     */     extends SAX2DTM2.PrecedingIterator
/*  675:     */   {
/*  676:     */     private final int _nodeType;
/*  677:     */     
/*  678:     */     public TypedPrecedingIterator(int type)
/*  679:     */     {
/*  680: 972 */       super();
/*  681: 973 */       this._nodeType = type;
/*  682:     */     }
/*  683:     */     
/*  684:     */     public int next()
/*  685:     */     {
/*  686: 983 */       int node = this._currentNode;
/*  687: 984 */       int nodeType = this._nodeType;
/*  688: 986 */       if (nodeType >= 14) {
/*  689:     */         do
/*  690:     */         {
/*  691:     */           do
/*  692:     */           {
/*  693: 988 */             node++;
/*  694: 990 */             if (this._sp < 0)
/*  695:     */             {
/*  696: 991 */               node = -1;
/*  697:     */               break label170;
/*  698:     */             }
/*  699: 994 */             if (node < this._stack[this._sp]) {
/*  700:     */               break;
/*  701:     */             }
/*  702: 995 */           } while (--this._sp >= 0);
/*  703: 996 */           node = -1;
/*  704: 997 */           break;
/*  705:1000 */         } while (SAX2DTM2.this._exptype2(node) != nodeType);
/*  706:     */       } else {
/*  707:     */         for (;;)
/*  708:     */         {
/*  709:1009 */           node++;
/*  710:1011 */           if (this._sp < 0)
/*  711:     */           {
/*  712:1012 */             node = -1;
/*  713:     */           }
/*  714:1015 */           else if (node >= this._stack[this._sp])
/*  715:     */           {
/*  716:1016 */             if (--this._sp < 0) {
/*  717:1017 */               node = -1;
/*  718:     */             }
/*  719:     */           }
/*  720:     */           else
/*  721:     */           {
/*  722:1022 */             int expType = SAX2DTM2.this._exptype2(node);
/*  723:1023 */             if (expType < 14)
/*  724:     */             {
/*  725:1024 */               if (expType == nodeType) {
/*  726:     */                 break;
/*  727:     */               }
/*  728:     */             }
/*  729:1029 */             else if (SAX2DTM2.this.m_extendedTypes[expType].getNodeType() == nodeType) {
/*  730:     */               break;
/*  731:     */             }
/*  732:     */           }
/*  733:     */         }
/*  734:     */       }
/*  735:     */       label170:
/*  736:1037 */       this._currentNode = node;
/*  737:1039 */       return node == -1 ? -1 : returnNode(SAX2DTM2.this.makeNodeHandle(node));
/*  738:     */     }
/*  739:     */   }
/*  740:     */   
/*  741:     */   public class FollowingIterator
/*  742:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  743:     */   {
/*  744:     */     public FollowingIterator()
/*  745:     */     {
/*  746:1051 */       super();
/*  747:     */     }
/*  748:     */     
/*  749:     */     public DTMAxisIterator setStartNode(int node)
/*  750:     */     {
/*  751:1066 */       if (node == 0) {
/*  752:1067 */         node = SAX2DTM2.this.getDocument();
/*  753:     */       }
/*  754:1068 */       if (this._isRestartable)
/*  755:     */       {
/*  756:1070 */         this._startNode = node;
/*  757:     */         
/*  758:     */ 
/*  759:     */ 
/*  760:1074 */         node = SAX2DTM2.this.makeNodeIdentity(node);
/*  761:     */         
/*  762:     */ 
/*  763:1077 */         int type = SAX2DTM2.this._type2(node);
/*  764:     */         int first;
/*  765:1079 */         if ((2 == type) || (13 == type))
/*  766:     */         {
/*  767:1081 */           node = SAX2DTM2.this._parent2(node);
/*  768:1082 */           first = SAX2DTM2.this._firstch2(node);
/*  769:1084 */           if (-1 != first)
/*  770:     */           {
/*  771:1085 */             this._currentNode = SAX2DTM2.this.makeNodeHandle(first);
/*  772:1086 */             return resetPosition();
/*  773:     */           }
/*  774:     */         }
/*  775:     */         do
/*  776:     */         {
/*  777:1092 */           first = SAX2DTM2.this._nextsib2(node);
/*  778:1094 */           if (-1 == first) {
/*  779:1095 */             node = SAX2DTM2.this._parent2(node);
/*  780:     */           }
/*  781:1097 */         } while ((-1 == first) && (-1 != node));
/*  782:1099 */         this._currentNode = SAX2DTM2.this.makeNodeHandle(first);
/*  783:     */         
/*  784:     */ 
/*  785:1102 */         return resetPosition();
/*  786:     */       }
/*  787:1105 */       return this;
/*  788:     */     }
/*  789:     */     
/*  790:     */     public int next()
/*  791:     */     {
/*  792:1116 */       int node = this._currentNode;
/*  793:     */       
/*  794:     */ 
/*  795:1119 */       int current = SAX2DTM2.this.makeNodeIdentity(node);
/*  796:     */       int type;
/*  797:     */       do
/*  798:     */       {
/*  799:1123 */         current++;
/*  800:     */         
/*  801:1125 */         type = SAX2DTM2.this._type2(current);
/*  802:1126 */         if (-1 == type)
/*  803:     */         {
/*  804:1127 */           this._currentNode = -1;
/*  805:1128 */           return returnNode(node);
/*  806:     */         }
/*  807:1131 */       } while ((2 == type) || (13 == type));
/*  808:1134 */       this._currentNode = SAX2DTM2.this.makeNodeHandle(current);
/*  809:1135 */       return returnNode(node);
/*  810:     */     }
/*  811:     */   }
/*  812:     */   
/*  813:     */   public final class TypedFollowingIterator
/*  814:     */     extends SAX2DTM2.FollowingIterator
/*  815:     */   {
/*  816:     */     private final int _nodeType;
/*  817:     */     
/*  818:     */     public TypedFollowingIterator(int type)
/*  819:     */     {
/*  820:1157 */       super();
/*  821:1158 */       this._nodeType = type;
/*  822:     */     }
/*  823:     */     
/*  824:     */     public int next()
/*  825:     */     {
/*  826:1172 */       int nodeType = this._nodeType;
/*  827:1173 */       int currentNodeID = SAX2DTM2.this.makeNodeIdentity(this._currentNode);
/*  828:     */       int node;
/*  829:     */       int current;
/*  830:     */       int type;
/*  831:1175 */       if (nodeType >= 14) {
/*  832:     */         do
/*  833:     */         {
/*  834:1177 */           node = currentNodeID;
/*  835:1178 */           current = node;
/*  836:     */           do
/*  837:     */           {
/*  838:1181 */             current++;
/*  839:1182 */             type = SAX2DTM2.this._type2(current);
/*  840:1184 */           } while ((type != -1) && ((2 == type) || (13 == type)));
/*  841:1186 */           currentNodeID = type != -1 ? current : -1;
/*  842:1188 */           if (node == -1) {
/*  843:     */             break;
/*  844:     */           }
/*  845:1188 */         } while (SAX2DTM2.this._exptype2(node) != nodeType);
/*  846:     */       } else {
/*  847:     */         do
/*  848:     */         {
/*  849:1192 */           node = currentNodeID;
/*  850:1193 */           current = node;
/*  851:     */           do
/*  852:     */           {
/*  853:1196 */             current++;
/*  854:1197 */             type = SAX2DTM2.this._type2(current);
/*  855:1199 */           } while ((type != -1) && ((2 == type) || (13 == type)));
/*  856:1201 */           currentNodeID = type != -1 ? current : -1;
/*  857:1204 */         } while ((node != -1) && (SAX2DTM2.this._exptype2(node) != nodeType) && (SAX2DTM2.this._type2(node) != nodeType));
/*  858:     */       }
/*  859:1207 */       this._currentNode = SAX2DTM2.this.makeNodeHandle(currentNodeID);
/*  860:1208 */       return node == -1 ? -1 : returnNode(SAX2DTM2.this.makeNodeHandle(node));
/*  861:     */     }
/*  862:     */   }
/*  863:     */   
/*  864:     */   public class AncestorIterator
/*  865:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  866:     */   {
/*  867:     */     private static final int m_blocksize = 32;
/*  868:     */     
/*  869:     */     public AncestorIterator()
/*  870:     */     {
/*  871:1216 */       super();
/*  872:     */     }
/*  873:     */     
/*  874:1222 */     int[] m_ancestors = new int[32];
/*  875:1225 */     int m_size = 0;
/*  876:     */     int m_ancestorsPos;
/*  877:     */     int m_markedPos;
/*  878:     */     int m_realStartNode;
/*  879:     */     
/*  880:     */     public int getStartNode()
/*  881:     */     {
/*  882:1242 */       return this.m_realStartNode;
/*  883:     */     }
/*  884:     */     
/*  885:     */     public final boolean isReverse()
/*  886:     */     {
/*  887:1252 */       return true;
/*  888:     */     }
/*  889:     */     
/*  890:     */     public DTMAxisIterator cloneIterator()
/*  891:     */     {
/*  892:1262 */       this._isRestartable = false;
/*  893:     */       try
/*  894:     */       {
/*  895:1266 */         AncestorIterator clone = (AncestorIterator)super.clone();
/*  896:     */         
/*  897:1268 */         clone._startNode = this._startNode;
/*  898:     */         
/*  899:     */ 
/*  900:1271 */         return clone;
/*  901:     */       }
/*  902:     */       catch (CloneNotSupportedException e)
/*  903:     */       {
/*  904:1275 */         throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
/*  905:     */       }
/*  906:     */     }
/*  907:     */     
/*  908:     */     public DTMAxisIterator setStartNode(int node)
/*  909:     */     {
/*  910:1290 */       if (node == 0) {
/*  911:1291 */         node = SAX2DTM2.this.getDocument();
/*  912:     */       }
/*  913:1292 */       this.m_realStartNode = node;
/*  914:1294 */       if (this._isRestartable)
/*  915:     */       {
/*  916:1296 */         int nodeID = SAX2DTM2.this.makeNodeIdentity(node);
/*  917:1297 */         this.m_size = 0;
/*  918:1299 */         if (nodeID == -1)
/*  919:     */         {
/*  920:1300 */           this._currentNode = -1;
/*  921:1301 */           this.m_ancestorsPos = 0;
/*  922:1302 */           return this;
/*  923:     */         }
/*  924:1307 */         if (!this._includeSelf)
/*  925:     */         {
/*  926:1308 */           nodeID = SAX2DTM2.this._parent2(nodeID);
/*  927:1309 */           node = SAX2DTM2.this.makeNodeHandle(nodeID);
/*  928:     */         }
/*  929:1312 */         this._startNode = node;
/*  930:1314 */         while (nodeID != -1)
/*  931:     */         {
/*  932:1316 */           if (this.m_size >= this.m_ancestors.length)
/*  933:     */           {
/*  934:1318 */             int[] newAncestors = new int[this.m_size * 2];
/*  935:1319 */             System.arraycopy(this.m_ancestors, 0, newAncestors, 0, this.m_ancestors.length);
/*  936:1320 */             this.m_ancestors = newAncestors;
/*  937:     */           }
/*  938:1323 */           this.m_ancestors[(this.m_size++)] = node;
/*  939:1324 */           nodeID = SAX2DTM2.this._parent2(nodeID);
/*  940:1325 */           node = SAX2DTM2.this.makeNodeHandle(nodeID);
/*  941:     */         }
/*  942:1328 */         this.m_ancestorsPos = (this.m_size - 1);
/*  943:     */         
/*  944:1330 */         this._currentNode = (this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1);
/*  945:     */         
/*  946:     */ 
/*  947:     */ 
/*  948:1334 */         return resetPosition();
/*  949:     */       }
/*  950:1337 */       return this;
/*  951:     */     }
/*  952:     */     
/*  953:     */     public DTMAxisIterator reset()
/*  954:     */     {
/*  955:1349 */       this.m_ancestorsPos = (this.m_size - 1);
/*  956:     */       
/*  957:1351 */       this._currentNode = (this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1);
/*  958:     */       
/*  959:     */ 
/*  960:1354 */       return resetPosition();
/*  961:     */     }
/*  962:     */     
/*  963:     */     public int next()
/*  964:     */     {
/*  965:1365 */       int next = this._currentNode;
/*  966:     */       
/*  967:1367 */       int pos = --this.m_ancestorsPos;
/*  968:     */       
/*  969:1369 */       this._currentNode = (pos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1);
/*  970:     */       
/*  971:     */ 
/*  972:1372 */       return returnNode(next);
/*  973:     */     }
/*  974:     */     
/*  975:     */     public void setMark()
/*  976:     */     {
/*  977:1376 */       this.m_markedPos = this.m_ancestorsPos;
/*  978:     */     }
/*  979:     */     
/*  980:     */     public void gotoMark()
/*  981:     */     {
/*  982:1380 */       this.m_ancestorsPos = this.m_markedPos;
/*  983:1381 */       this._currentNode = (this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1);
/*  984:     */     }
/*  985:     */   }
/*  986:     */   
/*  987:     */   public final class TypedAncestorIterator
/*  988:     */     extends SAX2DTM2.AncestorIterator
/*  989:     */   {
/*  990:     */     private final int _nodeType;
/*  991:     */     
/*  992:     */     public TypedAncestorIterator(int type)
/*  993:     */     {
/*  994:1402 */       super();
/*  995:1403 */       this._nodeType = type;
/*  996:     */     }
/*  997:     */     
/*  998:     */     public DTMAxisIterator setStartNode(int node)
/*  999:     */     {
/* 1000:1417 */       if (node == 0) {
/* 1001:1418 */         node = SAX2DTM2.this.getDocument();
/* 1002:     */       }
/* 1003:1419 */       this.m_realStartNode = node;
/* 1004:1421 */       if (this._isRestartable)
/* 1005:     */       {
/* 1006:1423 */         int nodeID = SAX2DTM2.this.makeNodeIdentity(node);
/* 1007:1424 */         this.m_size = 0;
/* 1008:1426 */         if (nodeID == -1)
/* 1009:     */         {
/* 1010:1427 */           this._currentNode = -1;
/* 1011:1428 */           this.m_ancestorsPos = 0;
/* 1012:1429 */           return this;
/* 1013:     */         }
/* 1014:1432 */         int nodeType = this._nodeType;
/* 1015:1434 */         if (!this._includeSelf)
/* 1016:     */         {
/* 1017:1435 */           nodeID = SAX2DTM2.this._parent2(nodeID);
/* 1018:1436 */           node = SAX2DTM2.this.makeNodeHandle(nodeID);
/* 1019:     */         }
/* 1020:1439 */         this._startNode = node;
/* 1021:1441 */         if (nodeType >= 14) {
/* 1022:1442 */           while (nodeID != -1)
/* 1023:     */           {
/* 1024:1443 */             eType = SAX2DTM2.this._exptype2(nodeID);
/* 1025:1445 */             if (eType == nodeType)
/* 1026:     */             {
/* 1027:1446 */               if (this.m_size >= this.m_ancestors.length)
/* 1028:     */               {
/* 1029:1448 */                 newAncestors = new int[this.m_size * 2];
/* 1030:1449 */                 System.arraycopy(this.m_ancestors, 0, newAncestors, 0, this.m_ancestors.length);
/* 1031:1450 */                 this.m_ancestors = newAncestors;
/* 1032:     */               }
/* 1033:1452 */               this.m_ancestors[(this.m_size++)] = SAX2DTM2.this.makeNodeHandle(nodeID);
/* 1034:     */             }
/* 1035:1454 */             nodeID = SAX2DTM2.this._parent2(nodeID);
/* 1036:     */           }
/* 1037:     */         } else {
/* 1038:1458 */           while (nodeID != -1)
/* 1039:     */           {
/* 1040:     */             int eType;
/* 1041:     */             int[] newAncestors;
/* 1042:1459 */             int eType = SAX2DTM2.this._exptype2(nodeID);
/* 1043:1461 */             if (((eType < 14) && (eType == nodeType)) || ((eType >= 14) && (SAX2DTM2.this.m_extendedTypes[eType].getNodeType() == nodeType)))
/* 1044:     */             {
/* 1045:1464 */               if (this.m_size >= this.m_ancestors.length)
/* 1046:     */               {
/* 1047:1466 */                 int[] newAncestors = new int[this.m_size * 2];
/* 1048:1467 */                 System.arraycopy(this.m_ancestors, 0, newAncestors, 0, this.m_ancestors.length);
/* 1049:1468 */                 this.m_ancestors = newAncestors;
/* 1050:     */               }
/* 1051:1470 */               this.m_ancestors[(this.m_size++)] = SAX2DTM2.this.makeNodeHandle(nodeID);
/* 1052:     */             }
/* 1053:1472 */             nodeID = SAX2DTM2.this._parent2(nodeID);
/* 1054:     */           }
/* 1055:     */         }
/* 1056:1475 */         this.m_ancestorsPos = (this.m_size - 1);
/* 1057:     */         
/* 1058:1477 */         this._currentNode = (this.m_ancestorsPos >= 0 ? this.m_ancestors[this.m_ancestorsPos] : -1);
/* 1059:     */         
/* 1060:     */ 
/* 1061:     */ 
/* 1062:1481 */         return resetPosition();
/* 1063:     */       }
/* 1064:1484 */       return this;
/* 1065:     */     }
/* 1066:     */     
/* 1067:     */     public int getNodeByPosition(int position)
/* 1068:     */     {
/* 1069:1492 */       if ((position > 0) && (position <= this.m_size)) {
/* 1070:1493 */         return this.m_ancestors[(position - 1)];
/* 1071:     */       }
/* 1072:1496 */       return -1;
/* 1073:     */     }
/* 1074:     */     
/* 1075:     */     public int getLast()
/* 1076:     */     {
/* 1077:1504 */       return this.m_size;
/* 1078:     */     }
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public class DescendantIterator
/* 1082:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/* 1083:     */   {
/* 1084:     */     public DescendantIterator()
/* 1085:     */     {
/* 1086:1511 */       super();
/* 1087:     */     }
/* 1088:     */     
/* 1089:     */     public DTMAxisIterator setStartNode(int node)
/* 1090:     */     {
/* 1091:1525 */       if (node == 0) {
/* 1092:1526 */         node = SAX2DTM2.this.getDocument();
/* 1093:     */       }
/* 1094:1527 */       if (this._isRestartable)
/* 1095:     */       {
/* 1096:1529 */         node = SAX2DTM2.this.makeNodeIdentity(node);
/* 1097:1530 */         this._startNode = node;
/* 1098:1532 */         if (this._includeSelf) {
/* 1099:1533 */           node--;
/* 1100:     */         }
/* 1101:1535 */         this._currentNode = node;
/* 1102:     */         
/* 1103:1537 */         return resetPosition();
/* 1104:     */       }
/* 1105:1540 */       return this;
/* 1106:     */     }
/* 1107:     */     
/* 1108:     */     protected final boolean isDescendant(int identity)
/* 1109:     */     {
/* 1110:1559 */       return (SAX2DTM2.this._parent2(identity) >= this._startNode) || (this._startNode == identity);
/* 1111:     */     }
/* 1112:     */     
/* 1113:     */     public int next()
/* 1114:     */     {
/* 1115:1569 */       int startNode = this._startNode;
/* 1116:1570 */       if (startNode == -1) {
/* 1117:1571 */         return -1;
/* 1118:     */       }
/* 1119:1574 */       if ((this._includeSelf) && (this._currentNode + 1 == startNode)) {
/* 1120:1575 */         return returnNode(SAX2DTM2.this.makeNodeHandle(++this._currentNode));
/* 1121:     */       }
/* 1122:1577 */       int node = this._currentNode;
/* 1123:     */       int type;
/* 1124:1582 */       if (startNode == 0)
/* 1125:     */       {
/* 1126:     */         int eType;
/* 1127:     */         do
/* 1128:     */         {
/* 1129:1585 */           node++;
/* 1130:1586 */           eType = SAX2DTM2.this._exptype2(node);
/* 1131:1588 */           if (-1 == eType)
/* 1132:     */           {
/* 1133:1589 */             this._currentNode = -1;
/* 1134:1590 */             return -1;
/* 1135:     */           }
/* 1136:1594 */         } while ((eType == 3) || ((type = SAX2DTM2.this.m_extendedTypes[eType].getNodeType()) == 2) || (type == 13));
/* 1137:     */       }
/* 1138:     */       else
/* 1139:     */       {
/* 1140:     */         do
/* 1141:     */         {
/* 1142:1598 */           node++;
/* 1143:1599 */           type = SAX2DTM2.this._type2(node);
/* 1144:1601 */           if ((-1 == type) || (!isDescendant(node)))
/* 1145:     */           {
/* 1146:1602 */             this._currentNode = -1;
/* 1147:1603 */             return -1;
/* 1148:     */           }
/* 1149:1606 */         } while ((2 == type) || (3 == type) || (13 == type));
/* 1150:     */       }
/* 1151:1609 */       this._currentNode = node;
/* 1152:1610 */       return returnNode(SAX2DTM2.this.makeNodeHandle(node));
/* 1153:     */     }
/* 1154:     */     
/* 1155:     */     public DTMAxisIterator reset()
/* 1156:     */     {
/* 1157:1620 */       boolean temp = this._isRestartable;
/* 1158:     */       
/* 1159:1622 */       this._isRestartable = true;
/* 1160:     */       
/* 1161:1624 */       setStartNode(SAX2DTM2.this.makeNodeHandle(this._startNode));
/* 1162:     */       
/* 1163:1626 */       this._isRestartable = temp;
/* 1164:     */       
/* 1165:1628 */       return this;
/* 1166:     */     }
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public final class TypedDescendantIterator
/* 1170:     */     extends SAX2DTM2.DescendantIterator
/* 1171:     */   {
/* 1172:     */     private final int _nodeType;
/* 1173:     */     
/* 1174:     */     public TypedDescendantIterator(int nodeType)
/* 1175:     */     {
/* 1176:1649 */       super();
/* 1177:1650 */       this._nodeType = nodeType;
/* 1178:     */     }
/* 1179:     */     
/* 1180:     */     public int next()
/* 1181:     */     {
/* 1182:1660 */       int startNode = this._startNode;
/* 1183:1661 */       if (this._startNode == -1) {
/* 1184:1662 */         return -1;
/* 1185:     */       }
/* 1186:1665 */       int node = this._currentNode;
/* 1187:     */       
/* 1188:     */ 
/* 1189:1668 */       int nodeType = this._nodeType;
/* 1190:     */       int expType;
/* 1191:1670 */       if (nodeType != 1) {
/* 1192:     */         do
/* 1193:     */         {
/* 1194:1674 */           node++;
/* 1195:1675 */           expType = SAX2DTM2.this._exptype2(node);
/* 1196:1677 */           if ((-1 == expType) || ((SAX2DTM2.this._parent2(node) < startNode) && (startNode != node)))
/* 1197:     */           {
/* 1198:1678 */             this._currentNode = -1;
/* 1199:1679 */             return -1;
/* 1200:     */           }
/* 1201:1682 */         } while (expType != nodeType);
/* 1202:1687 */       } else if (startNode == 0) {
/* 1203:     */         do
/* 1204:     */         {
/* 1205:1691 */           node++;
/* 1206:1692 */           expType = SAX2DTM2.this._exptype2(node);
/* 1207:1694 */           if (-1 == expType)
/* 1208:     */           {
/* 1209:1695 */             this._currentNode = -1;
/* 1210:1696 */             return -1;
/* 1211:     */           }
/* 1212:1699 */         } while ((expType < 14) || (SAX2DTM2.this.m_extendedTypes[expType].getNodeType() != 1));
/* 1213:     */       } else {
/* 1214:     */         do
/* 1215:     */         {
/* 1216:1705 */           node++;
/* 1217:1706 */           expType = SAX2DTM2.this._exptype2(node);
/* 1218:1708 */           if ((-1 == expType) || ((SAX2DTM2.this._parent2(node) < startNode) && (startNode != node)))
/* 1219:     */           {
/* 1220:1709 */             this._currentNode = -1;
/* 1221:1710 */             return -1;
/* 1222:     */           }
/* 1223:1714 */         } while ((expType < 14) || (SAX2DTM2.this.m_extendedTypes[expType].getNodeType() != 1));
/* 1224:     */       }
/* 1225:1717 */       this._currentNode = node;
/* 1226:1718 */       return returnNode(SAX2DTM2.this.makeNodeHandle(node));
/* 1227:     */     }
/* 1228:     */   }
/* 1229:     */   
/* 1230:     */   public final class TypedSingletonIterator
/* 1231:     */     extends DTMDefaultBaseIterators.SingletonIterator
/* 1232:     */   {
/* 1233:     */     private final int _nodeType;
/* 1234:     */     
/* 1235:     */     public TypedSingletonIterator(int nodeType)
/* 1236:     */     {
/* 1237:1738 */       super();
/* 1238:1739 */       this._nodeType = nodeType;
/* 1239:     */     }
/* 1240:     */     
/* 1241:     */     public int next()
/* 1242:     */     {
/* 1243:1750 */       int result = this._currentNode;
/* 1244:1751 */       if (result == -1) {
/* 1245:1752 */         return -1;
/* 1246:     */       }
/* 1247:1754 */       this._currentNode = -1;
/* 1248:1756 */       if (this._nodeType >= 14)
/* 1249:     */       {
/* 1250:1757 */         if (SAX2DTM2.this._exptype2(SAX2DTM2.this.makeNodeIdentity(result)) == this._nodeType) {
/* 1251:1758 */           return returnNode(result);
/* 1252:     */         }
/* 1253:     */       }
/* 1254:1762 */       else if (SAX2DTM2.this._type2(SAX2DTM2.this.makeNodeIdentity(result)) == this._nodeType) {
/* 1255:1763 */         return returnNode(result);
/* 1256:     */       }
/* 1257:1767 */       return -1;
/* 1258:     */     }
/* 1259:     */   }
/* 1260:     */   
/* 1261:1805 */   private int m_valueIndex = 0;
/* 1262:     */   private int m_maxNodeIndex;
/* 1263:     */   protected int m_SHIFT;
/* 1264:     */   protected int m_MASK;
/* 1265:     */   protected int m_blocksize;
/* 1266:     */   protected static final int TEXT_LENGTH_BITS = 10;
/* 1267:     */   protected static final int TEXT_OFFSET_BITS = 21;
/* 1268:     */   protected static final int TEXT_LENGTH_MAX = 1023;
/* 1269:     */   protected static final int TEXT_OFFSET_MAX = 2097151;
/* 1270:1834 */   protected boolean m_buildIdIndex = true;
/* 1271:     */   private static final String EMPTY_STR = "";
/* 1272:1840 */   private static final XMLString EMPTY_XML_STR = new XMLStringDefault("");
/* 1273:     */   
/* 1274:     */   public SAX2DTM2(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing)
/* 1275:     */   {
/* 1276:1851 */     this(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, 512, true, true, false);
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public SAX2DTM2(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean usePrevsib, boolean buildIdIndex, boolean newNameTable)
/* 1280:     */   {
/* 1281:1868 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, blocksize, usePrevsib, newNameTable);
/* 1282:1873 */     for (int shift = 0; blocksize >>>= 1 != 0; shift++) {}
/* 1283:1875 */     this.m_blocksize = (1 << shift);
/* 1284:1876 */     this.m_SHIFT = shift;
/* 1285:1877 */     this.m_MASK = (this.m_blocksize - 1);
/* 1286:     */     
/* 1287:1879 */     this.m_buildIdIndex = buildIdIndex;
/* 1288:     */     
/* 1289:     */ 
/* 1290:     */ 
/* 1291:     */ 
/* 1292:1884 */     this.m_values = new Vector(32, 512);
/* 1293:     */     
/* 1294:1886 */     this.m_maxNodeIndex = 65536;
/* 1295:     */     
/* 1296:     */ 
/* 1297:1889 */     this.m_exptype_map0 = this.m_exptype.getMap0();
/* 1298:1890 */     this.m_nextsib_map0 = this.m_nextsib.getMap0();
/* 1299:1891 */     this.m_firstch_map0 = this.m_firstch.getMap0();
/* 1300:1892 */     this.m_parent_map0 = this.m_parent.getMap0();
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   public final int _exptype(int identity)
/* 1304:     */   {
/* 1305:1903 */     return this.m_exptype.elementAt(identity);
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public final int _exptype2(int identity)
/* 1309:     */   {
/* 1310:1926 */     if (identity < this.m_blocksize) {
/* 1311:1927 */       return this.m_exptype_map0[identity];
/* 1312:     */     }
/* 1313:1929 */     return this.m_exptype_map[(identity >>> this.m_SHIFT)][(identity & this.m_MASK)];
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   public final int _nextsib2(int identity)
/* 1317:     */   {
/* 1318:1942 */     if (identity < this.m_blocksize) {
/* 1319:1943 */       return this.m_nextsib_map0[identity];
/* 1320:     */     }
/* 1321:1945 */     return this.m_nextsib_map[(identity >>> this.m_SHIFT)][(identity & this.m_MASK)];
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public final int _firstch2(int identity)
/* 1325:     */   {
/* 1326:1958 */     if (identity < this.m_blocksize) {
/* 1327:1959 */       return this.m_firstch_map0[identity];
/* 1328:     */     }
/* 1329:1961 */     return this.m_firstch_map[(identity >>> this.m_SHIFT)][(identity & this.m_MASK)];
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   public final int _parent2(int identity)
/* 1333:     */   {
/* 1334:1974 */     if (identity < this.m_blocksize) {
/* 1335:1975 */       return this.m_parent_map0[identity];
/* 1336:     */     }
/* 1337:1977 */     return this.m_parent_map[(identity >>> this.m_SHIFT)][(identity & this.m_MASK)];
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public final int _type2(int identity)
/* 1341:     */   {
/* 1342:     */     int eType;
/* 1343:1990 */     if (identity < this.m_blocksize) {
/* 1344:1991 */       eType = this.m_exptype_map0[identity];
/* 1345:     */     } else {
/* 1346:1993 */       eType = this.m_exptype_map[(identity >>> this.m_SHIFT)][(identity & this.m_MASK)];
/* 1347:     */     }
/* 1348:1995 */     if (-1 != eType) {
/* 1349:1996 */       return this.m_extendedTypes[eType].getNodeType();
/* 1350:     */     }
/* 1351:1998 */     return -1;
/* 1352:     */   }
/* 1353:     */   
/* 1354:     */   public final int getExpandedTypeID2(int nodeHandle)
/* 1355:     */   {
/* 1356:2009 */     int nodeID = makeNodeIdentity(nodeHandle);
/* 1357:2013 */     if (nodeID != -1)
/* 1358:     */     {
/* 1359:2014 */       if (nodeID < this.m_blocksize) {
/* 1360:2015 */         return this.m_exptype_map0[nodeID];
/* 1361:     */       }
/* 1362:2017 */       return this.m_exptype_map[(nodeID >>> this.m_SHIFT)][(nodeID & this.m_MASK)];
/* 1363:     */     }
/* 1364:2020 */     return -1;
/* 1365:     */   }
/* 1366:     */   
/* 1367:     */   public final int _exptype2Type(int exptype)
/* 1368:     */   {
/* 1369:2033 */     if (-1 != exptype) {
/* 1370:2034 */       return this.m_extendedTypes[exptype].getNodeType();
/* 1371:     */     }
/* 1372:2036 */     return -1;
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   public int getIdForNamespace(String uri)
/* 1376:     */   {
/* 1377:2049 */     int index = this.m_values.indexOf(uri);
/* 1378:2050 */     if (index < 0)
/* 1379:     */     {
/* 1380:2052 */       this.m_values.addElement(uri);
/* 1381:2053 */       return this.m_valueIndex++;
/* 1382:     */     }
/* 1383:2056 */     return index;
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   public void startElement(String uri, String localName, String qName, Attributes attributes)
/* 1387:     */     throws SAXException
/* 1388:     */   {
/* 1389:2086 */     charactersFlush();
/* 1390:     */     
/* 1391:2088 */     int exName = this.m_expandedNameTable.getExpandedTypeID(uri, localName, 1);
/* 1392:     */     
/* 1393:2090 */     int prefixIndex = qName.length() != localName.length() ? this.m_valuesOrPrefixes.stringToIndex(qName) : 0;
/* 1394:     */     
/* 1395:     */ 
/* 1396:2093 */     int elemNode = addNode(1, exName, this.m_parents.peek(), this.m_previous, prefixIndex, true);
/* 1397:2096 */     if (this.m_indexing) {
/* 1398:2097 */       indexNode(exName, elemNode);
/* 1399:     */     }
/* 1400:2099 */     this.m_parents.push(elemNode);
/* 1401:     */     
/* 1402:2101 */     int startDecls = this.m_contextIndexes.peek();
/* 1403:2102 */     int nDecls = this.m_prefixMappings.size();
/* 1404:     */     String prefix;
/* 1405:2105 */     if (!this.m_pastFirstElement)
/* 1406:     */     {
/* 1407:2108 */       prefix = "xml";
/* 1408:2109 */       String declURL = "http://www.w3.org/XML/1998/namespace";
/* 1409:2110 */       exName = this.m_expandedNameTable.getExpandedTypeID(null, prefix, 13);
/* 1410:2111 */       this.m_values.addElement(declURL);
/* 1411:2112 */       int val = this.m_valueIndex++;
/* 1412:2113 */       addNode(13, exName, elemNode, -1, val, false);
/* 1413:     */       
/* 1414:2115 */       this.m_pastFirstElement = true;
/* 1415:     */     }
/* 1416:2118 */     for (int i = startDecls; i < nDecls; i += 2)
/* 1417:     */     {
/* 1418:2120 */       prefix = (String)this.m_prefixMappings.elementAt(i);
/* 1419:2122 */       if (prefix != null)
/* 1420:     */       {
/* 1421:2125 */         String declURL = (String)this.m_prefixMappings.elementAt(i + 1);
/* 1422:     */         
/* 1423:2127 */         exName = this.m_expandedNameTable.getExpandedTypeID(null, prefix, 13);
/* 1424:     */         
/* 1425:2129 */         this.m_values.addElement(declURL);
/* 1426:2130 */         int val = this.m_valueIndex++;
/* 1427:     */         
/* 1428:2132 */         addNode(13, exName, elemNode, -1, val, false);
/* 1429:     */       }
/* 1430:     */     }
/* 1431:2135 */     int n = attributes.getLength();
/* 1432:2137 */     for (int i = 0; i < n; i++)
/* 1433:     */     {
/* 1434:2139 */       String attrUri = attributes.getURI(i);
/* 1435:2140 */       String attrQName = attributes.getQName(i);
/* 1436:2141 */       String valString = attributes.getValue(i);
/* 1437:     */       
/* 1438:     */ 
/* 1439:     */ 
/* 1440:2145 */       String attrLocalName = attributes.getLocalName(i);
/* 1441:     */       int nodeType;
/* 1442:2147 */       if ((null != attrQName) && ((attrQName.equals("xmlns")) || (attrQName.startsWith("xmlns:"))))
/* 1443:     */       {
/* 1444:2151 */         prefix = getPrefix(attrQName, attrUri);
/* 1445:2152 */         if (declAlreadyDeclared(prefix)) {
/* 1446:     */           continue;
/* 1447:     */         }
/* 1448:2155 */         nodeType = 13;
/* 1449:     */       }
/* 1450:     */       else
/* 1451:     */       {
/* 1452:2159 */         nodeType = 2;
/* 1453:2161 */         if ((this.m_buildIdIndex) && (attributes.getType(i).equalsIgnoreCase("ID"))) {
/* 1454:2162 */           setIDAttribute(valString, elemNode);
/* 1455:     */         }
/* 1456:     */       }
/* 1457:2167 */       if (null == valString) {
/* 1458:2168 */         valString = "";
/* 1459:     */       }
/* 1460:2170 */       this.m_values.addElement(valString);
/* 1461:2171 */       int val = this.m_valueIndex++;
/* 1462:2173 */       if (attrLocalName.length() != attrQName.length())
/* 1463:     */       {
/* 1464:2176 */         prefixIndex = this.m_valuesOrPrefixes.stringToIndex(attrQName);
/* 1465:     */         
/* 1466:2178 */         int dataIndex = this.m_data.size();
/* 1467:     */         
/* 1468:2180 */         this.m_data.addElement(prefixIndex);
/* 1469:2181 */         this.m_data.addElement(val);
/* 1470:     */         
/* 1471:2183 */         val = -dataIndex;
/* 1472:     */       }
/* 1473:2186 */       exName = this.m_expandedNameTable.getExpandedTypeID(attrUri, attrLocalName, nodeType);
/* 1474:2187 */       addNode(nodeType, exName, elemNode, -1, val, false);
/* 1475:     */     }
/* 1476:2191 */     if (null != this.m_wsfilter)
/* 1477:     */     {
/* 1478:2193 */       short wsv = this.m_wsfilter.getShouldStripSpace(makeNodeHandle(elemNode), this);
/* 1479:2194 */       boolean shouldStrip = 2 == wsv ? true : 3 == wsv ? getShouldStripWhitespace() : false;
/* 1480:     */       
/* 1481:     */ 
/* 1482:     */ 
/* 1483:2198 */       pushShouldStripWhitespace(shouldStrip);
/* 1484:     */     }
/* 1485:2201 */     this.m_previous = -1;
/* 1486:     */     
/* 1487:2203 */     this.m_contextIndexes.push(this.m_prefixMappings.size());
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   public void endElement(String uri, String localName, String qName)
/* 1491:     */     throws SAXException
/* 1492:     */   {
/* 1493:2229 */     charactersFlush();
/* 1494:     */     
/* 1495:     */ 
/* 1496:     */ 
/* 1497:2233 */     this.m_contextIndexes.quickPop(1);
/* 1498:     */     
/* 1499:     */ 
/* 1500:2236 */     int topContextIndex = this.m_contextIndexes.peek();
/* 1501:2237 */     if (topContextIndex != this.m_prefixMappings.size()) {
/* 1502:2238 */       this.m_prefixMappings.setSize(topContextIndex);
/* 1503:     */     }
/* 1504:2241 */     this.m_previous = this.m_parents.pop();
/* 1505:     */     
/* 1506:2243 */     popShouldStripWhitespace();
/* 1507:     */   }
/* 1508:     */   
/* 1509:     */   public void comment(char[] ch, int start, int length)
/* 1510:     */     throws SAXException
/* 1511:     */   {
/* 1512:2261 */     if (this.m_insideDTD) {
/* 1513:2262 */       return;
/* 1514:     */     }
/* 1515:2264 */     charactersFlush();
/* 1516:     */     
/* 1517:     */ 
/* 1518:     */ 
/* 1519:2268 */     this.m_values.addElement(new String(ch, start, length));
/* 1520:2269 */     int dataIndex = this.m_valueIndex++;
/* 1521:     */     
/* 1522:2271 */     this.m_previous = addNode(8, 8, this.m_parents.peek(), this.m_previous, dataIndex, false);
/* 1523:     */   }
/* 1524:     */   
/* 1525:     */   public void startDocument()
/* 1526:     */     throws SAXException
/* 1527:     */   {
/* 1528:2285 */     int doc = addNode(9, 9, -1, -1, 0, true);
/* 1529:     */     
/* 1530:     */ 
/* 1531:     */ 
/* 1532:2289 */     this.m_parents.push(doc);
/* 1533:2290 */     this.m_previous = -1;
/* 1534:     */     
/* 1535:2292 */     this.m_contextIndexes.push(this.m_prefixMappings.size());
/* 1536:     */   }
/* 1537:     */   
/* 1538:     */   public void endDocument()
/* 1539:     */     throws SAXException
/* 1540:     */   {
/* 1541:2304 */     super.endDocument();
/* 1542:     */     
/* 1543:     */ 
/* 1544:     */ 
/* 1545:2308 */     this.m_exptype.addElement(-1);
/* 1546:2309 */     this.m_parent.addElement(-1);
/* 1547:2310 */     this.m_nextsib.addElement(-1);
/* 1548:2311 */     this.m_firstch.addElement(-1);
/* 1549:     */     
/* 1550:     */ 
/* 1551:2314 */     this.m_extendedTypes = this.m_expandedNameTable.getExtendedTypes();
/* 1552:2315 */     this.m_exptype_map = this.m_exptype.getMap();
/* 1553:2316 */     this.m_nextsib_map = this.m_nextsib.getMap();
/* 1554:2317 */     this.m_firstch_map = this.m_firstch.getMap();
/* 1555:2318 */     this.m_parent_map = this.m_parent.getMap();
/* 1556:     */   }
/* 1557:     */   
/* 1558:     */   protected final int addNode(int type, int expandedTypeID, int parentIndex, int previousSibling, int dataOrPrefix, boolean canHaveFirstChild)
/* 1559:     */   {
/* 1560:2339 */     int nodeIndex = this.m_size++;
/* 1561:2343 */     if (nodeIndex == this.m_maxNodeIndex)
/* 1562:     */     {
/* 1563:2345 */       addNewDTMID(nodeIndex);
/* 1564:2346 */       this.m_maxNodeIndex += 65536;
/* 1565:     */     }
/* 1566:2349 */     this.m_firstch.addElement(-1);
/* 1567:2350 */     this.m_nextsib.addElement(-1);
/* 1568:2351 */     this.m_parent.addElement(parentIndex);
/* 1569:2352 */     this.m_exptype.addElement(expandedTypeID);
/* 1570:2353 */     this.m_dataOrQName.addElement(dataOrPrefix);
/* 1571:2355 */     if (this.m_prevsib != null) {
/* 1572:2356 */       this.m_prevsib.addElement(previousSibling);
/* 1573:     */     }
/* 1574:2359 */     if ((this.m_locator != null) && (this.m_useSourceLocationProperty)) {
/* 1575:2360 */       setSourceLocation();
/* 1576:     */     }
/* 1577:2367 */     switch (type)
/* 1578:     */     {
/* 1579:     */     case 13: 
/* 1580:2370 */       declareNamespaceInContext(parentIndex, nodeIndex);
/* 1581:2371 */       break;
/* 1582:     */     case 2: 
/* 1583:     */       break;
/* 1584:     */     default: 
/* 1585:2375 */       if (-1 != previousSibling) {
/* 1586:2376 */         this.m_nextsib.setElementAt(nodeIndex, previousSibling);
/* 1587:2378 */       } else if (-1 != parentIndex) {
/* 1588:2379 */         this.m_firstch.setElementAt(nodeIndex, parentIndex);
/* 1589:     */       }
/* 1590:     */       break;
/* 1591:     */     }
/* 1592:2384 */     return nodeIndex;
/* 1593:     */   }
/* 1594:     */   
/* 1595:     */   protected final void charactersFlush()
/* 1596:     */   {
/* 1597:2394 */     if (this.m_textPendingStart >= 0)
/* 1598:     */     {
/* 1599:2396 */       int length = this.m_chars.size() - this.m_textPendingStart;
/* 1600:2397 */       boolean doStrip = false;
/* 1601:2399 */       if (getShouldStripWhitespace()) {
/* 1602:2401 */         doStrip = this.m_chars.isWhitespace(this.m_textPendingStart, length);
/* 1603:     */       }
/* 1604:2404 */       if (doStrip) {
/* 1605:2405 */         this.m_chars.setLength(this.m_textPendingStart);
/* 1606:2409 */       } else if (length > 0) {
/* 1607:2413 */         if ((length <= 1023) && (this.m_textPendingStart <= 2097151))
/* 1608:     */         {
/* 1609:2415 */           this.m_previous = addNode(this.m_coalescedTextType, 3, this.m_parents.peek(), this.m_previous, length + (this.m_textPendingStart << 10), false);
/* 1610:     */         }
/* 1611:     */         else
/* 1612:     */         {
/* 1613:2423 */           int dataIndex = this.m_data.size();
/* 1614:2424 */           this.m_previous = addNode(this.m_coalescedTextType, 3, this.m_parents.peek(), this.m_previous, -dataIndex, false);
/* 1615:     */           
/* 1616:     */ 
/* 1617:2427 */           this.m_data.addElement(this.m_textPendingStart);
/* 1618:2428 */           this.m_data.addElement(length);
/* 1619:     */         }
/* 1620:     */       }
/* 1621:2434 */       this.m_textPendingStart = -1;
/* 1622:2435 */       this.m_textType = (this.m_coalescedTextType = 3);
/* 1623:     */     }
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   public void processingInstruction(String target, String data)
/* 1627:     */     throws SAXException
/* 1628:     */   {
/* 1629:2459 */     charactersFlush();
/* 1630:     */     
/* 1631:2461 */     int dataIndex = this.m_data.size();
/* 1632:2462 */     this.m_previous = addNode(7, 7, this.m_parents.peek(), this.m_previous, -dataIndex, false);
/* 1633:     */     
/* 1634:     */ 
/* 1635:     */ 
/* 1636:     */ 
/* 1637:2467 */     this.m_data.addElement(this.m_valuesOrPrefixes.stringToIndex(target));
/* 1638:2468 */     this.m_values.addElement(data);
/* 1639:2469 */     this.m_data.addElement(this.m_valueIndex++);
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public final int getFirstAttribute(int nodeHandle)
/* 1643:     */   {
/* 1644:2483 */     int nodeID = makeNodeIdentity(nodeHandle);
/* 1645:2485 */     if (nodeID == -1) {
/* 1646:2486 */       return -1;
/* 1647:     */     }
/* 1648:2488 */     int type = _type2(nodeID);
/* 1649:2490 */     if (1 == type) {
/* 1650:     */       for (;;)
/* 1651:     */       {
/* 1652:2495 */         nodeID++;
/* 1653:     */         
/* 1654:2497 */         type = _type2(nodeID);
/* 1655:2499 */         if (type == 2) {
/* 1656:2501 */           return makeNodeHandle(nodeID);
/* 1657:     */         }
/* 1658:2503 */         if (13 != type) {
/* 1659:     */           break;
/* 1660:     */         }
/* 1661:     */       }
/* 1662:     */     }
/* 1663:2510 */     return -1;
/* 1664:     */   }
/* 1665:     */   
/* 1666:     */   protected int getFirstAttributeIdentity(int identity)
/* 1667:     */   {
/* 1668:2522 */     if (identity == -1) {
/* 1669:2523 */       return -1;
/* 1670:     */     }
/* 1671:2525 */     int type = _type2(identity);
/* 1672:2527 */     if (1 == type) {
/* 1673:     */       for (;;)
/* 1674:     */       {
/* 1675:2532 */         identity++;
/* 1676:     */         
/* 1677:     */ 
/* 1678:2535 */         type = _type2(identity);
/* 1679:2537 */         if (type == 2) {
/* 1680:2539 */           return identity;
/* 1681:     */         }
/* 1682:2541 */         if (13 != type) {
/* 1683:     */           break;
/* 1684:     */         }
/* 1685:     */       }
/* 1686:     */     }
/* 1687:2548 */     return -1;
/* 1688:     */   }
/* 1689:     */   
/* 1690:     */   protected int getNextAttributeIdentity(int identity)
/* 1691:     */   {
/* 1692:     */     for (;;)
/* 1693:     */     {
/* 1694:2566 */       identity++;
/* 1695:2567 */       int type = _type2(identity);
/* 1696:2569 */       if (type == 2) {
/* 1697:2570 */         return identity;
/* 1698:     */       }
/* 1699:2571 */       if (type != 13) {
/* 1700:     */         break;
/* 1701:     */       }
/* 1702:     */     }
/* 1703:2576 */     return -1;
/* 1704:     */   }
/* 1705:     */   
/* 1706:     */   protected final int getTypedAttribute(int nodeHandle, int attType)
/* 1707:     */   {
/* 1708:2593 */     int nodeID = makeNodeIdentity(nodeHandle);
/* 1709:2595 */     if (nodeID == -1) {
/* 1710:2596 */       return -1;
/* 1711:     */     }
/* 1712:2598 */     int type = _type2(nodeID);
/* 1713:2600 */     if (1 == type) {
/* 1714:     */       for (;;)
/* 1715:     */       {
/* 1716:2605 */         nodeID++;
/* 1717:2606 */         int expType = _exptype2(nodeID);
/* 1718:2608 */         if (expType != -1) {
/* 1719:2609 */           type = this.m_extendedTypes[expType].getNodeType();
/* 1720:     */         } else {
/* 1721:2611 */           return -1;
/* 1722:     */         }
/* 1723:2613 */         if (type == 2)
/* 1724:     */         {
/* 1725:2615 */           if (expType == attType) {
/* 1726:2615 */             return makeNodeHandle(nodeID);
/* 1727:     */           }
/* 1728:     */         }
/* 1729:2617 */         else if (13 != type) {
/* 1730:     */           break;
/* 1731:     */         }
/* 1732:     */       }
/* 1733:     */     }
/* 1734:2624 */     return -1;
/* 1735:     */   }
/* 1736:     */   
/* 1737:     */   public String getLocalName(int nodeHandle)
/* 1738:     */   {
/* 1739:2639 */     int expType = _exptype(makeNodeIdentity(nodeHandle));
/* 1740:2641 */     if (expType == 7)
/* 1741:     */     {
/* 1742:2643 */       int dataIndex = _dataOrQName(makeNodeIdentity(nodeHandle));
/* 1743:2644 */       dataIndex = this.m_data.elementAt(-dataIndex);
/* 1744:2645 */       return this.m_valuesOrPrefixes.indexToString(dataIndex);
/* 1745:     */     }
/* 1746:2648 */     return this.m_expandedNameTable.getLocalName(expType);
/* 1747:     */   }
/* 1748:     */   
/* 1749:     */   public final String getNodeNameX(int nodeHandle)
/* 1750:     */   {
/* 1751:2663 */     int nodeID = makeNodeIdentity(nodeHandle);
/* 1752:2664 */     int eType = _exptype2(nodeID);
/* 1753:2666 */     if (eType == 7)
/* 1754:     */     {
/* 1755:2668 */       int dataIndex = _dataOrQName(nodeID);
/* 1756:2669 */       dataIndex = this.m_data.elementAt(-dataIndex);
/* 1757:2670 */       return this.m_valuesOrPrefixes.indexToString(dataIndex);
/* 1758:     */     }
/* 1759:2673 */     ExtendedType extType = this.m_extendedTypes[eType];
/* 1760:2675 */     if (extType.getNamespace().length() == 0) {
/* 1761:2677 */       return extType.getLocalName();
/* 1762:     */     }
/* 1763:2681 */     int qnameIndex = this.m_dataOrQName.elementAt(nodeID);
/* 1764:2683 */     if (qnameIndex == 0) {
/* 1765:2684 */       return extType.getLocalName();
/* 1766:     */     }
/* 1767:2686 */     if (qnameIndex < 0)
/* 1768:     */     {
/* 1769:2688 */       qnameIndex = -qnameIndex;
/* 1770:2689 */       qnameIndex = this.m_data.elementAt(qnameIndex);
/* 1771:     */     }
/* 1772:2692 */     return this.m_valuesOrPrefixes.indexToString(qnameIndex);
/* 1773:     */   }
/* 1774:     */   
/* 1775:     */   public String getNodeName(int nodeHandle)
/* 1776:     */   {
/* 1777:2710 */     int nodeID = makeNodeIdentity(nodeHandle);
/* 1778:2711 */     int eType = _exptype2(nodeID);
/* 1779:     */     
/* 1780:2713 */     ExtendedType extType = this.m_extendedTypes[eType];
/* 1781:2714 */     if (extType.getNamespace().length() == 0)
/* 1782:     */     {
/* 1783:2716 */       int type = extType.getNodeType();
/* 1784:     */       
/* 1785:2718 */       String localName = extType.getLocalName();
/* 1786:2719 */       if (type == 13)
/* 1787:     */       {
/* 1788:2721 */         if (localName.length() == 0) {
/* 1789:2722 */           return "xmlns";
/* 1790:     */         }
/* 1791:2724 */         return "xmlns:" + localName;
/* 1792:     */       }
/* 1793:2726 */       if (type == 7)
/* 1794:     */       {
/* 1795:2728 */         int dataIndex = _dataOrQName(nodeID);
/* 1796:2729 */         dataIndex = this.m_data.elementAt(-dataIndex);
/* 1797:2730 */         return this.m_valuesOrPrefixes.indexToString(dataIndex);
/* 1798:     */       }
/* 1799:2732 */       if (localName.length() == 0) {
/* 1800:2734 */         return getFixedNames(type);
/* 1801:     */       }
/* 1802:2737 */       return localName;
/* 1803:     */     }
/* 1804:2741 */     int qnameIndex = this.m_dataOrQName.elementAt(nodeID);
/* 1805:2743 */     if (qnameIndex == 0) {
/* 1806:2744 */       return extType.getLocalName();
/* 1807:     */     }
/* 1808:2746 */     if (qnameIndex < 0)
/* 1809:     */     {
/* 1810:2748 */       qnameIndex = -qnameIndex;
/* 1811:2749 */       qnameIndex = this.m_data.elementAt(qnameIndex);
/* 1812:     */     }
/* 1813:2752 */     return this.m_valuesOrPrefixes.indexToString(qnameIndex);
/* 1814:     */   }
/* 1815:     */   
/* 1816:     */   public XMLString getStringValue(int nodeHandle)
/* 1817:     */   {
/* 1818:2775 */     int identity = makeNodeIdentity(nodeHandle);
/* 1819:2776 */     if (identity == -1) {
/* 1820:2777 */       return EMPTY_XML_STR;
/* 1821:     */     }
/* 1822:2779 */     int type = _type2(identity);
/* 1823:2781 */     if ((type == 1) || (type == 9))
/* 1824:     */     {
/* 1825:2783 */       int startNode = identity;
/* 1826:2784 */       identity = _firstch2(identity);
/* 1827:2785 */       if (-1 != identity)
/* 1828:     */       {
/* 1829:2787 */         int offset = -1;
/* 1830:2788 */         int length = 0;
/* 1831:     */         do
/* 1832:     */         {
/* 1833:2792 */           type = _exptype2(identity);
/* 1834:2794 */           if ((type == 3) || (type == 4))
/* 1835:     */           {
/* 1836:2796 */             int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 1837:2797 */             if (dataIndex >= 0)
/* 1838:     */             {
/* 1839:2799 */               if (-1 == offset) {
/* 1840:2801 */                 offset = dataIndex >>> 10;
/* 1841:     */               }
/* 1842:2804 */               length += (dataIndex & 0x3FF);
/* 1843:     */             }
/* 1844:     */             else
/* 1845:     */             {
/* 1846:2808 */               if (-1 == offset) {
/* 1847:2810 */                 offset = this.m_data.elementAt(-dataIndex);
/* 1848:     */               }
/* 1849:2813 */               length += this.m_data.elementAt(-dataIndex + 1);
/* 1850:     */             }
/* 1851:     */           }
/* 1852:2817 */           identity++;
/* 1853:2818 */         } while (_parent2(identity) >= startNode);
/* 1854:2820 */         if (length > 0)
/* 1855:     */         {
/* 1856:2822 */           if (this.m_xstrf != null) {
/* 1857:2823 */             return this.m_xstrf.newstr(this.m_chars, offset, length);
/* 1858:     */           }
/* 1859:2825 */           return new XMLStringDefault(this.m_chars.getString(offset, length));
/* 1860:     */         }
/* 1861:2828 */         return EMPTY_XML_STR;
/* 1862:     */       }
/* 1863:2831 */       return EMPTY_XML_STR;
/* 1864:     */     }
/* 1865:2833 */     if ((3 == type) || (4 == type))
/* 1866:     */     {
/* 1867:2835 */       int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 1868:2836 */       if (dataIndex >= 0)
/* 1869:     */       {
/* 1870:2838 */         if (this.m_xstrf != null) {
/* 1871:2839 */           return this.m_xstrf.newstr(this.m_chars, dataIndex >>> 10, dataIndex & 0x3FF);
/* 1872:     */         }
/* 1873:2842 */         return new XMLStringDefault(this.m_chars.getString(dataIndex >>> 10, dataIndex & 0x3FF));
/* 1874:     */       }
/* 1875:2847 */       if (this.m_xstrf != null) {
/* 1876:2848 */         return this.m_xstrf.newstr(this.m_chars, this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
/* 1877:     */       }
/* 1878:2851 */       return new XMLStringDefault(this.m_chars.getString(this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1)));
/* 1879:     */     }
/* 1880:2857 */     int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 1881:2859 */     if (dataIndex < 0)
/* 1882:     */     {
/* 1883:2861 */       dataIndex = -dataIndex;
/* 1884:2862 */       dataIndex = this.m_data.elementAt(dataIndex + 1);
/* 1885:     */     }
/* 1886:2865 */     if (this.m_xstrf != null) {
/* 1887:2866 */       return this.m_xstrf.newstr((String)this.m_values.elementAt(dataIndex));
/* 1888:     */     }
/* 1889:2868 */     return new XMLStringDefault((String)this.m_values.elementAt(dataIndex));
/* 1890:     */   }
/* 1891:     */   
/* 1892:     */   public final String getStringValueX(int nodeHandle)
/* 1893:     */   {
/* 1894:2888 */     int identity = makeNodeIdentity(nodeHandle);
/* 1895:2889 */     if (identity == -1) {
/* 1896:2890 */       return "";
/* 1897:     */     }
/* 1898:2892 */     int type = _type2(identity);
/* 1899:2894 */     if ((type == 1) || (type == 9))
/* 1900:     */     {
/* 1901:2896 */       int startNode = identity;
/* 1902:2897 */       identity = _firstch2(identity);
/* 1903:2898 */       if (-1 != identity)
/* 1904:     */       {
/* 1905:2900 */         int offset = -1;
/* 1906:2901 */         int length = 0;
/* 1907:     */         do
/* 1908:     */         {
/* 1909:2905 */           type = _exptype2(identity);
/* 1910:2907 */           if ((type == 3) || (type == 4))
/* 1911:     */           {
/* 1912:2909 */             int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 1913:2910 */             if (dataIndex >= 0)
/* 1914:     */             {
/* 1915:2912 */               if (-1 == offset) {
/* 1916:2914 */                 offset = dataIndex >>> 10;
/* 1917:     */               }
/* 1918:2917 */               length += (dataIndex & 0x3FF);
/* 1919:     */             }
/* 1920:     */             else
/* 1921:     */             {
/* 1922:2921 */               if (-1 == offset) {
/* 1923:2923 */                 offset = this.m_data.elementAt(-dataIndex);
/* 1924:     */               }
/* 1925:2926 */               length += this.m_data.elementAt(-dataIndex + 1);
/* 1926:     */             }
/* 1927:     */           }
/* 1928:2930 */           identity++;
/* 1929:2931 */         } while (_parent2(identity) >= startNode);
/* 1930:2933 */         if (length > 0) {
/* 1931:2935 */           return this.m_chars.getString(offset, length);
/* 1932:     */         }
/* 1933:2938 */         return "";
/* 1934:     */       }
/* 1935:2941 */       return "";
/* 1936:     */     }
/* 1937:2943 */     if ((3 == type) || (4 == type))
/* 1938:     */     {
/* 1939:2945 */       int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 1940:2946 */       if (dataIndex >= 0) {
/* 1941:2948 */         return this.m_chars.getString(dataIndex >>> 10, dataIndex & 0x3FF);
/* 1942:     */       }
/* 1943:2953 */       return this.m_chars.getString(this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
/* 1944:     */     }
/* 1945:2959 */     int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 1946:2961 */     if (dataIndex < 0)
/* 1947:     */     {
/* 1948:2963 */       dataIndex = -dataIndex;
/* 1949:2964 */       dataIndex = this.m_data.elementAt(dataIndex + 1);
/* 1950:     */     }
/* 1951:2967 */     return (String)this.m_values.elementAt(dataIndex);
/* 1952:     */   }
/* 1953:     */   
/* 1954:     */   public String getStringValue()
/* 1955:     */   {
/* 1956:2976 */     int child = _firstch2(0);
/* 1957:2977 */     if (child == -1) {
/* 1958:2977 */       return "";
/* 1959:     */     }
/* 1960:2980 */     if ((_exptype2(child) == 3) && (_nextsib2(child) == -1))
/* 1961:     */     {
/* 1962:2982 */       int dataIndex = this.m_dataOrQName.elementAt(child);
/* 1963:2983 */       if (dataIndex >= 0) {
/* 1964:2984 */         return this.m_chars.getString(dataIndex >>> 10, dataIndex & 0x3FF);
/* 1965:     */       }
/* 1966:2986 */       return this.m_chars.getString(this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
/* 1967:     */     }
/* 1968:2990 */     return getStringValueX(getDocument());
/* 1969:     */   }
/* 1970:     */   
/* 1971:     */   public final void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize)
/* 1972:     */     throws SAXException
/* 1973:     */   {
/* 1974:3018 */     int identity = makeNodeIdentity(nodeHandle);
/* 1975:3020 */     if (identity == -1) {
/* 1976:3021 */       return;
/* 1977:     */     }
/* 1978:3023 */     int type = _type2(identity);
/* 1979:3025 */     if ((type == 1) || (type == 9))
/* 1980:     */     {
/* 1981:3027 */       int startNode = identity;
/* 1982:3028 */       identity = _firstch2(identity);
/* 1983:3029 */       if (-1 != identity)
/* 1984:     */       {
/* 1985:3031 */         int offset = -1;
/* 1986:3032 */         int length = 0;
/* 1987:     */         do
/* 1988:     */         {
/* 1989:3036 */           type = _exptype2(identity);
/* 1990:3038 */           if ((type == 3) || (type == 4))
/* 1991:     */           {
/* 1992:3040 */             int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 1993:3042 */             if (dataIndex >= 0)
/* 1994:     */             {
/* 1995:3044 */               if (-1 == offset) {
/* 1996:3046 */                 offset = dataIndex >>> 10;
/* 1997:     */               }
/* 1998:3049 */               length += (dataIndex & 0x3FF);
/* 1999:     */             }
/* 2000:     */             else
/* 2001:     */             {
/* 2002:3053 */               if (-1 == offset) {
/* 2003:3055 */                 offset = this.m_data.elementAt(-dataIndex);
/* 2004:     */               }
/* 2005:3058 */               length += this.m_data.elementAt(-dataIndex + 1);
/* 2006:     */             }
/* 2007:     */           }
/* 2008:3062 */           identity++;
/* 2009:3063 */         } while (_parent2(identity) >= startNode);
/* 2010:3065 */         if (length > 0) {
/* 2011:3067 */           if (normalize) {
/* 2012:3068 */             this.m_chars.sendNormalizedSAXcharacters(ch, offset, length);
/* 2013:     */           } else {
/* 2014:3070 */             this.m_chars.sendSAXcharacters(ch, offset, length);
/* 2015:     */           }
/* 2016:     */         }
/* 2017:     */       }
/* 2018:     */     }
/* 2019:3074 */     else if ((3 == type) || (4 == type))
/* 2020:     */     {
/* 2021:3076 */       int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 2022:3078 */       if (dataIndex >= 0)
/* 2023:     */       {
/* 2024:3080 */         if (normalize) {
/* 2025:3081 */           this.m_chars.sendNormalizedSAXcharacters(ch, dataIndex >>> 10, dataIndex & 0x3FF);
/* 2026:     */         } else {
/* 2027:3084 */           this.m_chars.sendSAXcharacters(ch, dataIndex >>> 10, dataIndex & 0x3FF);
/* 2028:     */         }
/* 2029:     */       }
/* 2030:3089 */       else if (normalize) {
/* 2031:3090 */         this.m_chars.sendNormalizedSAXcharacters(ch, this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
/* 2032:     */       } else {
/* 2033:3093 */         this.m_chars.sendSAXcharacters(ch, this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
/* 2034:     */       }
/* 2035:     */     }
/* 2036:     */     else
/* 2037:     */     {
/* 2038:3099 */       int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 2039:3101 */       if (dataIndex < 0)
/* 2040:     */       {
/* 2041:3103 */         dataIndex = -dataIndex;
/* 2042:3104 */         dataIndex = this.m_data.elementAt(dataIndex + 1);
/* 2043:     */       }
/* 2044:3107 */       String str = (String)this.m_values.elementAt(dataIndex);
/* 2045:3109 */       if (normalize) {
/* 2046:3110 */         FastStringBuffer.sendNormalizedSAXcharacters(str.toCharArray(), 0, str.length(), ch);
/* 2047:     */       } else {
/* 2048:3113 */         ch.characters(str.toCharArray(), 0, str.length());
/* 2049:     */       }
/* 2050:     */     }
/* 2051:     */   }
/* 2052:     */   
/* 2053:     */   public String getNodeValue(int nodeHandle)
/* 2054:     */   {
/* 2055:3129 */     int identity = makeNodeIdentity(nodeHandle);
/* 2056:3130 */     int type = _type2(identity);
/* 2057:3132 */     if ((type == 3) || (type == 4))
/* 2058:     */     {
/* 2059:3134 */       int dataIndex = _dataOrQName(identity);
/* 2060:3135 */       if (dataIndex > 0) {
/* 2061:3137 */         return this.m_chars.getString(dataIndex >>> 10, dataIndex & 0x3FF);
/* 2062:     */       }
/* 2063:3142 */       return this.m_chars.getString(this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
/* 2064:     */     }
/* 2065:3146 */     if ((1 == type) || (11 == type) || (9 == type)) {
/* 2066:3149 */       return null;
/* 2067:     */     }
/* 2068:3153 */     int dataIndex = this.m_dataOrQName.elementAt(identity);
/* 2069:3155 */     if (dataIndex < 0)
/* 2070:     */     {
/* 2071:3157 */       dataIndex = -dataIndex;
/* 2072:3158 */       dataIndex = this.m_data.elementAt(dataIndex + 1);
/* 2073:     */     }
/* 2074:3161 */     return (String)this.m_values.elementAt(dataIndex);
/* 2075:     */   }
/* 2076:     */   
/* 2077:     */   protected final void copyTextNode(int nodeID, SerializationHandler handler)
/* 2078:     */     throws SAXException
/* 2079:     */   {
/* 2080:3171 */     if (nodeID != -1)
/* 2081:     */     {
/* 2082:3172 */       int dataIndex = this.m_dataOrQName.elementAt(nodeID);
/* 2083:3173 */       if (dataIndex >= 0) {
/* 2084:3174 */         this.m_chars.sendSAXcharacters(handler, dataIndex >>> 10, dataIndex & 0x3FF);
/* 2085:     */       } else {
/* 2086:3178 */         this.m_chars.sendSAXcharacters(handler, this.m_data.elementAt(-dataIndex), this.m_data.elementAt(-dataIndex + 1));
/* 2087:     */       }
/* 2088:     */     }
/* 2089:     */   }
/* 2090:     */   
/* 2091:     */   protected final String copyElement(int nodeID, int exptype, SerializationHandler handler)
/* 2092:     */     throws SAXException
/* 2093:     */   {
/* 2094:3196 */     ExtendedType extType = this.m_extendedTypes[exptype];
/* 2095:3197 */     String uri = extType.getNamespace();
/* 2096:3198 */     String name = extType.getLocalName();
/* 2097:3200 */     if (uri.length() == 0)
/* 2098:     */     {
/* 2099:3201 */       handler.startElement(name);
/* 2100:3202 */       return name;
/* 2101:     */     }
/* 2102:3205 */     int qnameIndex = this.m_dataOrQName.elementAt(nodeID);
/* 2103:3207 */     if (qnameIndex == 0)
/* 2104:     */     {
/* 2105:3208 */       handler.startElement(name);
/* 2106:3209 */       handler.namespaceAfterStartElement("", uri);
/* 2107:3210 */       return name;
/* 2108:     */     }
/* 2109:3213 */     if (qnameIndex < 0)
/* 2110:     */     {
/* 2111:3214 */       qnameIndex = -qnameIndex;
/* 2112:3215 */       qnameIndex = this.m_data.elementAt(qnameIndex);
/* 2113:     */     }
/* 2114:3218 */     String qName = this.m_valuesOrPrefixes.indexToString(qnameIndex);
/* 2115:3219 */     handler.startElement(qName);
/* 2116:3220 */     int prefixIndex = qName.indexOf(':');
/* 2117:     */     String prefix;
/* 2118:3222 */     if (prefixIndex > 0) {
/* 2119:3223 */       prefix = qName.substring(0, prefixIndex);
/* 2120:     */     } else {
/* 2121:3226 */       prefix = null;
/* 2122:     */     }
/* 2123:3228 */     handler.namespaceAfterStartElement(prefix, uri);
/* 2124:3229 */     return qName;
/* 2125:     */   }
/* 2126:     */   
/* 2127:     */   protected final void copyNS(int nodeID, SerializationHandler handler, boolean inScope)
/* 2128:     */     throws SAXException
/* 2129:     */   {
/* 2130:3251 */     if ((this.m_namespaceDeclSetElements != null) && (this.m_namespaceDeclSetElements.size() == 1) && (this.m_namespaceDeclSets != null) && (((SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(0)).size() == 1)) {
/* 2131:3256 */       return;
/* 2132:     */     }
/* 2133:3258 */     SuballocatedIntVector nsContext = null;
/* 2134:     */     int nextNSNode;
/* 2135:3262 */     if (inScope)
/* 2136:     */     {
/* 2137:3263 */       nsContext = findNamespaceContext(nodeID);
/* 2138:3264 */       if ((nsContext == null) || (nsContext.size() < 1)) {
/* 2139:3265 */         return;
/* 2140:     */       }
/* 2141:3267 */       nextNSNode = makeNodeIdentity(nsContext.elementAt(0));
/* 2142:     */     }
/* 2143:     */     else
/* 2144:     */     {
/* 2145:3270 */       nextNSNode = getNextNamespaceNode2(nodeID);
/* 2146:     */     }
/* 2147:3272 */     int nsIndex = 1;
/* 2148:3273 */     while (nextNSNode != -1)
/* 2149:     */     {
/* 2150:3275 */       int eType = _exptype2(nextNSNode);
/* 2151:3276 */       String nodeName = this.m_extendedTypes[eType].getLocalName();
/* 2152:     */       
/* 2153:     */ 
/* 2154:3279 */       int dataIndex = this.m_dataOrQName.elementAt(nextNSNode);
/* 2155:3281 */       if (dataIndex < 0)
/* 2156:     */       {
/* 2157:3282 */         dataIndex = -dataIndex;
/* 2158:3283 */         dataIndex = this.m_data.elementAt(dataIndex + 1);
/* 2159:     */       }
/* 2160:3286 */       String nodeValue = (String)this.m_values.elementAt(dataIndex);
/* 2161:     */       
/* 2162:3288 */       handler.namespaceAfterStartElement(nodeName, nodeValue);
/* 2163:3290 */       if (inScope)
/* 2164:     */       {
/* 2165:3291 */         if (nsIndex < nsContext.size())
/* 2166:     */         {
/* 2167:3292 */           nextNSNode = makeNodeIdentity(nsContext.elementAt(nsIndex));
/* 2168:3293 */           nsIndex++;
/* 2169:     */         }
/* 2170:     */       }
/* 2171:     */       else {
/* 2172:3299 */         nextNSNode = getNextNamespaceNode2(nextNSNode);
/* 2173:     */       }
/* 2174:     */     }
/* 2175:     */   }
/* 2176:     */   
/* 2177:     */   protected final int getNextNamespaceNode2(int baseID)
/* 2178:     */   {
/* 2179:     */     int type;
/* 2180:3312 */     while ((type = _type2(++baseID)) == 2) {}
/* 2181:3314 */     if (type == 13) {
/* 2182:3315 */       return baseID;
/* 2183:     */     }
/* 2184:3317 */     return -1;
/* 2185:     */   }
/* 2186:     */   
/* 2187:     */   protected final void copyAttributes(int nodeID, SerializationHandler handler)
/* 2188:     */     throws SAXException
/* 2189:     */   {
/* 2190:3329 */     for (int current = getFirstAttributeIdentity(nodeID); current != -1; current = getNextAttributeIdentity(current))
/* 2191:     */     {
/* 2192:3330 */       int eType = _exptype2(current);
/* 2193:3331 */       copyAttribute(current, eType, handler);
/* 2194:     */     }
/* 2195:     */   }
/* 2196:     */   
/* 2197:     */   protected final void copyAttribute(int nodeID, int exptype, SerializationHandler handler)
/* 2198:     */     throws SAXException
/* 2199:     */   {
/* 2200:3356 */     ExtendedType extType = this.m_extendedTypes[exptype];
/* 2201:3357 */     String uri = extType.getNamespace();
/* 2202:3358 */     String localName = extType.getLocalName();
/* 2203:     */     
/* 2204:3360 */     String prefix = null;
/* 2205:3361 */     String qname = null;
/* 2206:3362 */     int dataIndex = _dataOrQName(nodeID);
/* 2207:3363 */     int valueIndex = dataIndex;
/* 2208:3364 */     if (dataIndex <= 0)
/* 2209:     */     {
/* 2210:3365 */       int prefixIndex = this.m_data.elementAt(-dataIndex);
/* 2211:3366 */       valueIndex = this.m_data.elementAt(-dataIndex + 1);
/* 2212:3367 */       qname = this.m_valuesOrPrefixes.indexToString(prefixIndex);
/* 2213:3368 */       int colonIndex = qname.indexOf(':');
/* 2214:3369 */       if (colonIndex > 0) {
/* 2215:3370 */         prefix = qname.substring(0, colonIndex);
/* 2216:     */       }
/* 2217:     */     }
/* 2218:3373 */     if (uri.length() != 0) {
/* 2219:3374 */       handler.namespaceAfterStartElement(prefix, uri);
/* 2220:     */     }
/* 2221:3377 */     String nodeName = prefix != null ? qname : localName;
/* 2222:3378 */     String nodeValue = (String)this.m_values.elementAt(valueIndex);
/* 2223:     */     
/* 2224:3380 */     handler.addAttribute(nodeName, nodeValue);
/* 2225:     */   }
/* 2226:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2
 * JD-Core Version:    0.7.0.1
 */