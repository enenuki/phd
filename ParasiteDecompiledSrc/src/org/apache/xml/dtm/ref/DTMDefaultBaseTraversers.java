/*    1:     */ package org.apache.xml.dtm.ref;
/*    2:     */ 
/*    3:     */ import javax.xml.transform.Source;
/*    4:     */ import org.apache.xml.dtm.Axis;
/*    5:     */ import org.apache.xml.dtm.DTMAxisTraverser;
/*    6:     */ import org.apache.xml.dtm.DTMException;
/*    7:     */ import org.apache.xml.dtm.DTMManager;
/*    8:     */ import org.apache.xml.dtm.DTMWSFilter;
/*    9:     */ import org.apache.xml.res.XMLMessages;
/*   10:     */ import org.apache.xml.utils.SuballocatedIntVector;
/*   11:     */ import org.apache.xml.utils.XMLStringFactory;
/*   12:     */ 
/*   13:     */ public abstract class DTMDefaultBaseTraversers
/*   14:     */   extends DTMDefaultBase
/*   15:     */ {
/*   16:     */   public DTMDefaultBaseTraversers(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing)
/*   17:     */   {
/*   18:  62 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing);
/*   19:     */   }
/*   20:     */   
/*   21:     */   public DTMDefaultBaseTraversers(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean usePrevsib, boolean newNameTable)
/*   22:     */   {
/*   23:  90 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, blocksize, usePrevsib, newNameTable);
/*   24:     */   }
/*   25:     */   
/*   26:     */   public DTMAxisTraverser getAxisTraverser(int axis)
/*   27:     */   {
/*   28:     */     DTMAxisTraverser traverser;
/*   29: 107 */     if (null == this.m_traversers)
/*   30:     */     {
/*   31: 109 */       this.m_traversers = new DTMAxisTraverser[Axis.getNamesLength()];
/*   32: 110 */       traverser = null;
/*   33:     */     }
/*   34:     */     else
/*   35:     */     {
/*   36: 114 */       traverser = this.m_traversers[axis];
/*   37: 116 */       if (traverser != null) {
/*   38: 117 */         return traverser;
/*   39:     */       }
/*   40:     */     }
/*   41: 120 */     switch (axis)
/*   42:     */     {
/*   43:     */     case 0: 
/*   44: 123 */       traverser = new AncestorTraverser(null);
/*   45: 124 */       break;
/*   46:     */     case 1: 
/*   47: 126 */       traverser = new AncestorOrSelfTraverser(null);
/*   48: 127 */       break;
/*   49:     */     case 2: 
/*   50: 129 */       traverser = new AttributeTraverser(null);
/*   51: 130 */       break;
/*   52:     */     case 3: 
/*   53: 132 */       traverser = new ChildTraverser(null);
/*   54: 133 */       break;
/*   55:     */     case 4: 
/*   56: 135 */       traverser = new DescendantTraverser(null);
/*   57: 136 */       break;
/*   58:     */     case 5: 
/*   59: 138 */       traverser = new DescendantOrSelfTraverser(null);
/*   60: 139 */       break;
/*   61:     */     case 6: 
/*   62: 141 */       traverser = new FollowingTraverser(null);
/*   63: 142 */       break;
/*   64:     */     case 7: 
/*   65: 144 */       traverser = new FollowingSiblingTraverser(null);
/*   66: 145 */       break;
/*   67:     */     case 9: 
/*   68: 147 */       traverser = new NamespaceTraverser(null);
/*   69: 148 */       break;
/*   70:     */     case 8: 
/*   71: 150 */       traverser = new NamespaceDeclsTraverser(null);
/*   72: 151 */       break;
/*   73:     */     case 10: 
/*   74: 153 */       traverser = new ParentTraverser(null);
/*   75: 154 */       break;
/*   76:     */     case 11: 
/*   77: 156 */       traverser = new PrecedingTraverser(null);
/*   78: 157 */       break;
/*   79:     */     case 12: 
/*   80: 159 */       traverser = new PrecedingSiblingTraverser(null);
/*   81: 160 */       break;
/*   82:     */     case 13: 
/*   83: 162 */       traverser = new SelfTraverser(null);
/*   84: 163 */       break;
/*   85:     */     case 16: 
/*   86: 165 */       traverser = new AllFromRootTraverser(null);
/*   87: 166 */       break;
/*   88:     */     case 14: 
/*   89: 168 */       traverser = new AllFromNodeTraverser(null);
/*   90: 169 */       break;
/*   91:     */     case 15: 
/*   92: 171 */       traverser = new PrecedingAndAncestorTraverser(null);
/*   93: 172 */       break;
/*   94:     */     case 17: 
/*   95: 174 */       traverser = new DescendantFromRootTraverser(null);
/*   96: 175 */       break;
/*   97:     */     case 18: 
/*   98: 177 */       traverser = new DescendantOrSelfFromRootTraverser(null);
/*   99: 178 */       break;
/*  100:     */     case 19: 
/*  101: 180 */       traverser = new RootTraverser(null);
/*  102: 181 */       break;
/*  103:     */     case 20: 
/*  104: 183 */       return null;
/*  105:     */     default: 
/*  106: 185 */       throw new DTMException(XMLMessages.createXMLMessage("ER_UNKNOWN_AXIS_TYPE", new Object[] { Integer.toString(axis) }));
/*  107:     */     }
/*  108: 188 */     if (null == traverser) {
/*  109: 189 */       throw new DTMException(XMLMessages.createXMLMessage("ER_AXIS_TRAVERSER_NOT_SUPPORTED", new Object[] { Axis.getNames(axis) }));
/*  110:     */     }
/*  111: 193 */     this.m_traversers[axis] = traverser;
/*  112:     */     
/*  113: 195 */     return traverser;
/*  114:     */   }
/*  115:     */   
/*  116:     */   private class AncestorTraverser
/*  117:     */     extends DTMAxisTraverser
/*  118:     */   {
/*  119:     */     AncestorTraverser(DTMDefaultBaseTraversers.1 x1)
/*  120:     */     {
/*  121: 201 */       this();
/*  122:     */     }
/*  123:     */     
/*  124:     */     public int next(int context, int current)
/*  125:     */     {
/*  126: 214 */       return DTMDefaultBaseTraversers.this.getParent(current);
/*  127:     */     }
/*  128:     */     
/*  129:     */     public int next(int context, int current, int expandedTypeID)
/*  130:     */     {
/*  131: 230 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current);
/*  132: 232 */       while (-1 != (current = DTMDefaultBaseTraversers.this.m_parent.elementAt(current))) {
/*  133: 234 */         if (DTMDefaultBaseTraversers.this.m_exptype.elementAt(current) == expandedTypeID) {
/*  134: 235 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  135:     */         }
/*  136:     */       }
/*  137: 238 */       return -1;
/*  138:     */     }
/*  139:     */     
/*  140:     */     private AncestorTraverser() {}
/*  141:     */   }
/*  142:     */   
/*  143:     */   private class AncestorOrSelfTraverser
/*  144:     */     extends DTMDefaultBaseTraversers.AncestorTraverser
/*  145:     */   {
/*  146:     */     AncestorOrSelfTraverser(DTMDefaultBaseTraversers.1 x1)
/*  147:     */     {
/*  148: 245 */       this();
/*  149:     */     }
/*  150:     */     
/*  151:     */     private AncestorOrSelfTraverser()
/*  152:     */     {
/*  153: 245 */       super(null);
/*  154:     */     }
/*  155:     */     
/*  156:     */     public int first(int context)
/*  157:     */     {
/*  158: 259 */       return context;
/*  159:     */     }
/*  160:     */     
/*  161:     */     public int first(int context, int expandedTypeID)
/*  162:     */     {
/*  163: 276 */       return DTMDefaultBaseTraversers.this.getExpandedTypeID(context) == expandedTypeID ? context : next(context, context, expandedTypeID);
/*  164:     */     }
/*  165:     */   }
/*  166:     */   
/*  167:     */   private class AttributeTraverser
/*  168:     */     extends DTMAxisTraverser
/*  169:     */   {
/*  170:     */     AttributeTraverser(DTMDefaultBaseTraversers.1 x1)
/*  171:     */     {
/*  172: 284 */       this();
/*  173:     */     }
/*  174:     */     
/*  175:     */     public int next(int context, int current)
/*  176:     */     {
/*  177: 297 */       return context == current ? DTMDefaultBaseTraversers.this.getFirstAttribute(context) : DTMDefaultBaseTraversers.this.getNextAttribute(current);
/*  178:     */     }
/*  179:     */     
/*  180:     */     public int next(int context, int current, int expandedTypeID)
/*  181:     */     {
/*  182: 314 */       current = context == current ? DTMDefaultBaseTraversers.this.getFirstAttribute(context) : DTMDefaultBaseTraversers.this.getNextAttribute(current);
/*  183:     */       do
/*  184:     */       {
/*  185: 319 */         if (DTMDefaultBaseTraversers.this.getExpandedTypeID(current) == expandedTypeID) {
/*  186: 320 */           return current;
/*  187:     */         }
/*  188: 322 */       } while (-1 != (current = DTMDefaultBaseTraversers.this.getNextAttribute(current)));
/*  189: 324 */       return -1;
/*  190:     */     }
/*  191:     */     
/*  192:     */     private AttributeTraverser() {}
/*  193:     */   }
/*  194:     */   
/*  195:     */   private class ChildTraverser
/*  196:     */     extends DTMAxisTraverser
/*  197:     */   {
/*  198:     */     ChildTraverser(DTMDefaultBaseTraversers.1 x1)
/*  199:     */     {
/*  200: 331 */       this();
/*  201:     */     }
/*  202:     */     
/*  203:     */     protected int getNextIndexed(int axisRoot, int nextPotential, int expandedTypeID)
/*  204:     */     {
/*  205: 350 */       int nsIndex = DTMDefaultBaseTraversers.this.m_expandedNameTable.getNamespaceID(expandedTypeID);
/*  206: 351 */       int lnIndex = DTMDefaultBaseTraversers.this.m_expandedNameTable.getLocalNameID(expandedTypeID);
/*  207:     */       for (;;)
/*  208:     */       {
/*  209: 355 */         int nextID = DTMDefaultBaseTraversers.this.findElementFromIndex(nsIndex, lnIndex, nextPotential);
/*  210: 357 */         if (-2 != nextID)
/*  211:     */         {
/*  212: 359 */           int parentID = DTMDefaultBaseTraversers.this.m_parent.elementAt(nextID);
/*  213: 362 */           if (parentID == axisRoot) {
/*  214: 363 */             return nextID;
/*  215:     */           }
/*  216: 367 */           if (parentID < axisRoot) {
/*  217: 368 */             return -1;
/*  218:     */           }
/*  219:     */           do
/*  220:     */           {
/*  221: 377 */             parentID = DTMDefaultBaseTraversers.this.m_parent.elementAt(parentID);
/*  222: 378 */             if (parentID < axisRoot) {
/*  223: 379 */               return -1;
/*  224:     */             }
/*  225: 381 */           } while (parentID > axisRoot);
/*  226: 384 */           nextPotential = nextID + 1;
/*  227:     */         }
/*  228:     */         else
/*  229:     */         {
/*  230: 388 */           DTMDefaultBaseTraversers.this.nextNode();
/*  231: 390 */           if (DTMDefaultBaseTraversers.this.m_nextsib.elementAt(axisRoot) != -2) {
/*  232:     */             break;
/*  233:     */           }
/*  234:     */         }
/*  235:     */       }
/*  236: 394 */       return -1;
/*  237:     */     }
/*  238:     */     
/*  239:     */     public int first(int context)
/*  240:     */     {
/*  241: 411 */       return DTMDefaultBaseTraversers.this.getFirstChild(context);
/*  242:     */     }
/*  243:     */     
/*  244:     */     public int first(int context, int expandedTypeID)
/*  245:     */     {
/*  246: 432 */       int identity = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  247:     */       
/*  248: 434 */       int firstMatch = getNextIndexed(identity, DTMDefaultBaseTraversers.this._firstch(identity), expandedTypeID);
/*  249:     */       
/*  250:     */ 
/*  251: 437 */       return DTMDefaultBaseTraversers.this.makeNodeHandle(firstMatch);
/*  252:     */     }
/*  253:     */     
/*  254:     */     public int next(int context, int current)
/*  255:     */     {
/*  256: 463 */       return DTMDefaultBaseTraversers.this.getNextSibling(current);
/*  257:     */     }
/*  258:     */     
/*  259:     */     public int next(int context, int current, int expandedTypeID)
/*  260:     */     {
/*  261: 479 */       for (current = DTMDefaultBaseTraversers.this._nextsib(DTMDefaultBaseTraversers.this.makeNodeIdentity(current)); -1 != current; current = DTMDefaultBaseTraversers.this._nextsib(current)) {
/*  262: 483 */         if (DTMDefaultBaseTraversers.this.m_exptype.elementAt(current) == expandedTypeID) {
/*  263: 484 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  264:     */         }
/*  265:     */       }
/*  266: 487 */       return -1;
/*  267:     */     }
/*  268:     */     
/*  269:     */     private ChildTraverser() {}
/*  270:     */   }
/*  271:     */   
/*  272:     */   private abstract class IndexedDTMAxisTraverser
/*  273:     */     extends DTMAxisTraverser
/*  274:     */   {
/*  275:     */     IndexedDTMAxisTraverser(DTMDefaultBaseTraversers.1 x1)
/*  276:     */     {
/*  277: 495 */       this();
/*  278:     */     }
/*  279:     */     
/*  280:     */     protected final boolean isIndexed(int expandedTypeID)
/*  281:     */     {
/*  282: 510 */       return (DTMDefaultBaseTraversers.this.m_indexing) && (1 == DTMDefaultBaseTraversers.this.m_expandedNameTable.getType(expandedTypeID));
/*  283:     */     }
/*  284:     */     
/*  285:     */     protected int getNextIndexed(int axisRoot, int nextPotential, int expandedTypeID)
/*  286:     */     {
/*  287: 554 */       int nsIndex = DTMDefaultBaseTraversers.this.m_expandedNameTable.getNamespaceID(expandedTypeID);
/*  288: 555 */       int lnIndex = DTMDefaultBaseTraversers.this.m_expandedNameTable.getLocalNameID(expandedTypeID);
/*  289:     */       for (;;)
/*  290:     */       {
/*  291: 559 */         int next = DTMDefaultBaseTraversers.this.findElementFromIndex(nsIndex, lnIndex, nextPotential);
/*  292: 561 */         if (-2 != next)
/*  293:     */         {
/*  294: 563 */           if (isAfterAxis(axisRoot, next)) {
/*  295: 564 */             return -1;
/*  296:     */           }
/*  297: 567 */           return next;
/*  298:     */         }
/*  299: 569 */         if (axisHasBeenProcessed(axisRoot)) {
/*  300:     */           break;
/*  301:     */         }
/*  302: 572 */         DTMDefaultBaseTraversers.this.nextNode();
/*  303:     */       }
/*  304: 575 */       return -1;
/*  305:     */     }
/*  306:     */     
/*  307:     */     private IndexedDTMAxisTraverser() {}
/*  308:     */     
/*  309:     */     protected abstract boolean isAfterAxis(int paramInt1, int paramInt2);
/*  310:     */     
/*  311:     */     protected abstract boolean axisHasBeenProcessed(int paramInt);
/*  312:     */   }
/*  313:     */   
/*  314:     */   private class DescendantTraverser
/*  315:     */     extends DTMDefaultBaseTraversers.IndexedDTMAxisTraverser
/*  316:     */   {
/*  317:     */     private DescendantTraverser()
/*  318:     */     {
/*  319: 582 */       super(null);
/*  320:     */     }
/*  321:     */     
/*  322:     */     DescendantTraverser(DTMDefaultBaseTraversers.1 x1)
/*  323:     */     {
/*  324: 582 */       this();
/*  325:     */     }
/*  326:     */     
/*  327:     */     protected int getFirstPotential(int identity)
/*  328:     */     {
/*  329: 594 */       return identity + 1;
/*  330:     */     }
/*  331:     */     
/*  332:     */     protected boolean axisHasBeenProcessed(int axisRoot)
/*  333:     */     {
/*  334: 607 */       return DTMDefaultBaseTraversers.this.m_nextsib.elementAt(axisRoot) != -2;
/*  335:     */     }
/*  336:     */     
/*  337:     */     protected int getSubtreeRoot(int handle)
/*  338:     */     {
/*  339: 620 */       return DTMDefaultBaseTraversers.this.makeNodeIdentity(handle);
/*  340:     */     }
/*  341:     */     
/*  342:     */     protected boolean isDescendant(int subtreeRootIdentity, int identity)
/*  343:     */     {
/*  344: 637 */       return DTMDefaultBaseTraversers.this._parent(identity) >= subtreeRootIdentity;
/*  345:     */     }
/*  346:     */     
/*  347:     */     protected boolean isAfterAxis(int axisRoot, int identity)
/*  348:     */     {
/*  349:     */       do
/*  350:     */       {
/*  351: 657 */         if (identity == axisRoot) {
/*  352: 658 */           return false;
/*  353:     */         }
/*  354: 659 */         identity = DTMDefaultBaseTraversers.this.m_parent.elementAt(identity);
/*  355: 661 */       } while (identity >= axisRoot);
/*  356: 663 */       return true;
/*  357:     */     }
/*  358:     */     
/*  359:     */     public int first(int context, int expandedTypeID)
/*  360:     */     {
/*  361: 683 */       if (isIndexed(expandedTypeID))
/*  362:     */       {
/*  363: 685 */         int identity = getSubtreeRoot(context);
/*  364: 686 */         int firstPotential = getFirstPotential(identity);
/*  365:     */         
/*  366: 688 */         return DTMDefaultBaseTraversers.this.makeNodeHandle(getNextIndexed(identity, firstPotential, expandedTypeID));
/*  367:     */       }
/*  368: 691 */       return next(context, context, expandedTypeID);
/*  369:     */     }
/*  370:     */     
/*  371:     */     public int next(int context, int current)
/*  372:     */     {
/*  373: 705 */       int subtreeRootIdent = getSubtreeRoot(context);
/*  374:     */       
/*  375: 707 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) + 1;
/*  376: 707 */       for (goto 20;; current++)
/*  377:     */       {
/*  378: 709 */         int type = DTMDefaultBaseTraversers.this._type(current);
/*  379: 711 */         if (!isDescendant(subtreeRootIdent, current)) {
/*  380: 712 */           return -1;
/*  381:     */         }
/*  382: 714 */         if ((2 != type) && (13 != type)) {
/*  383: 717 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  384:     */         }
/*  385:     */       }
/*  386:     */     }
/*  387:     */     
/*  388:     */     public int next(int context, int current, int expandedTypeID)
/*  389:     */     {
/*  390: 734 */       int subtreeRootIdent = getSubtreeRoot(context);
/*  391:     */       
/*  392: 736 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) + 1;
/*  393: 738 */       if (isIndexed(expandedTypeID)) {
/*  394: 740 */         return DTMDefaultBaseTraversers.this.makeNodeHandle(getNextIndexed(subtreeRootIdent, current, expandedTypeID));
/*  395:     */       }
/*  396: 743 */       for (;; current++)
/*  397:     */       {
/*  398: 745 */         int exptype = DTMDefaultBaseTraversers.this._exptype(current);
/*  399: 747 */         if (!isDescendant(subtreeRootIdent, current)) {
/*  400: 748 */           return -1;
/*  401:     */         }
/*  402: 750 */         if (exptype == expandedTypeID) {
/*  403: 753 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  404:     */         }
/*  405:     */       }
/*  406:     */     }
/*  407:     */   }
/*  408:     */   
/*  409:     */   private class DescendantOrSelfTraverser
/*  410:     */     extends DTMDefaultBaseTraversers.DescendantTraverser
/*  411:     */   {
/*  412:     */     DescendantOrSelfTraverser(DTMDefaultBaseTraversers.1 x1)
/*  413:     */     {
/*  414: 761 */       this();
/*  415:     */     }
/*  416:     */     
/*  417:     */     private DescendantOrSelfTraverser()
/*  418:     */     {
/*  419: 761 */       super(null);
/*  420:     */     }
/*  421:     */     
/*  422:     */     protected int getFirstPotential(int identity)
/*  423:     */     {
/*  424: 774 */       return identity;
/*  425:     */     }
/*  426:     */     
/*  427:     */     public int first(int context)
/*  428:     */     {
/*  429: 788 */       return context;
/*  430:     */     }
/*  431:     */   }
/*  432:     */   
/*  433:     */   private class AllFromNodeTraverser
/*  434:     */     extends DTMDefaultBaseTraversers.DescendantOrSelfTraverser
/*  435:     */   {
/*  436:     */     AllFromNodeTraverser(DTMDefaultBaseTraversers.1 x1)
/*  437:     */     {
/*  438: 795 */       this();
/*  439:     */     }
/*  440:     */     
/*  441:     */     private AllFromNodeTraverser()
/*  442:     */     {
/*  443: 795 */       super(null);
/*  444:     */     }
/*  445:     */     
/*  446:     */     public int next(int context, int current)
/*  447:     */     {
/*  448: 809 */       int subtreeRootIdent = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  449:     */       
/*  450: 811 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) + 1;
/*  451:     */       
/*  452:     */ 
/*  453:     */ 
/*  454:     */ 
/*  455:     */ 
/*  456:     */ 
/*  457: 818 */       DTMDefaultBaseTraversers.this._exptype(current);
/*  458: 820 */       if (!isDescendant(subtreeRootIdent, current)) {
/*  459: 821 */         return -1;
/*  460:     */       }
/*  461: 823 */       return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  462:     */     }
/*  463:     */   }
/*  464:     */   
/*  465:     */   private class FollowingTraverser
/*  466:     */     extends DTMDefaultBaseTraversers.DescendantTraverser
/*  467:     */   {
/*  468:     */     FollowingTraverser(DTMDefaultBaseTraversers.1 x1)
/*  469:     */     {
/*  470: 831 */       this();
/*  471:     */     }
/*  472:     */     
/*  473:     */     private FollowingTraverser()
/*  474:     */     {
/*  475: 831 */       super(null);
/*  476:     */     }
/*  477:     */     
/*  478:     */     public int first(int context)
/*  479:     */     {
/*  480: 844 */       context = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  481:     */       
/*  482:     */ 
/*  483: 847 */       int type = DTMDefaultBaseTraversers.this._type(context);
/*  484:     */       int first;
/*  485: 849 */       if ((2 == type) || (13 == type))
/*  486:     */       {
/*  487: 851 */         context = DTMDefaultBaseTraversers.this._parent(context);
/*  488: 852 */         first = DTMDefaultBaseTraversers.this._firstch(context);
/*  489: 854 */         if (-1 != first) {
/*  490: 855 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(first);
/*  491:     */         }
/*  492:     */       }
/*  493:     */       do
/*  494:     */       {
/*  495: 860 */         first = DTMDefaultBaseTraversers.this._nextsib(context);
/*  496: 862 */         if (-1 == first) {
/*  497: 863 */           context = DTMDefaultBaseTraversers.this._parent(context);
/*  498:     */         }
/*  499: 865 */       } while ((-1 == first) && (-1 != context));
/*  500: 867 */       return DTMDefaultBaseTraversers.this.makeNodeHandle(first);
/*  501:     */     }
/*  502:     */     
/*  503:     */     public int first(int context, int expandedTypeID)
/*  504:     */     {
/*  505: 884 */       int type = DTMDefaultBaseTraversers.this.getNodeType(context);
/*  506:     */       int first;
/*  507: 886 */       if ((2 == type) || (13 == type))
/*  508:     */       {
/*  509: 888 */         context = DTMDefaultBaseTraversers.this.getParent(context);
/*  510: 889 */         first = DTMDefaultBaseTraversers.this.getFirstChild(context);
/*  511: 891 */         if (-1 != first)
/*  512:     */         {
/*  513: 893 */           if (DTMDefaultBaseTraversers.this.getExpandedTypeID(first) == expandedTypeID) {
/*  514: 894 */             return first;
/*  515:     */           }
/*  516: 896 */           return next(context, first, expandedTypeID);
/*  517:     */         }
/*  518:     */       }
/*  519:     */       do
/*  520:     */       {
/*  521: 902 */         first = DTMDefaultBaseTraversers.this.getNextSibling(context);
/*  522: 904 */         if (-1 == first)
/*  523:     */         {
/*  524: 905 */           context = DTMDefaultBaseTraversers.this.getParent(context);
/*  525:     */         }
/*  526:     */         else
/*  527:     */         {
/*  528: 908 */           if (DTMDefaultBaseTraversers.this.getExpandedTypeID(first) == expandedTypeID) {
/*  529: 909 */             return first;
/*  530:     */           }
/*  531: 911 */           return next(context, first, expandedTypeID);
/*  532:     */         }
/*  533: 914 */       } while ((-1 == first) && (-1 != context));
/*  534: 916 */       return first;
/*  535:     */     }
/*  536:     */     
/*  537:     */     public int next(int context, int current)
/*  538:     */     {
/*  539: 930 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current);
/*  540:     */       int type;
/*  541:     */       do
/*  542:     */       {
/*  543: 934 */         current++;
/*  544:     */         
/*  545:     */ 
/*  546: 937 */         type = DTMDefaultBaseTraversers.this._type(current);
/*  547: 939 */         if (-1 == type) {
/*  548: 940 */           return -1;
/*  549:     */         }
/*  550: 942 */       } while ((2 == type) || (13 == type));
/*  551: 945 */       return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  552:     */     }
/*  553:     */     
/*  554:     */     public int next(int context, int current, int expandedTypeID)
/*  555:     */     {
/*  556: 962 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current);
/*  557:     */       int etype;
/*  558:     */       do
/*  559:     */       {
/*  560: 966 */         current++;
/*  561:     */         
/*  562: 968 */         etype = DTMDefaultBaseTraversers.this._exptype(current);
/*  563: 970 */         if (-1 == etype) {
/*  564: 971 */           return -1;
/*  565:     */         }
/*  566: 973 */       } while (etype != expandedTypeID);
/*  567: 976 */       return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  568:     */     }
/*  569:     */   }
/*  570:     */   
/*  571:     */   private class FollowingSiblingTraverser
/*  572:     */     extends DTMAxisTraverser
/*  573:     */   {
/*  574:     */     FollowingSiblingTraverser(DTMDefaultBaseTraversers.1 x1)
/*  575:     */     {
/*  576: 984 */       this();
/*  577:     */     }
/*  578:     */     
/*  579:     */     public int next(int context, int current)
/*  580:     */     {
/*  581: 997 */       return DTMDefaultBaseTraversers.this.getNextSibling(current);
/*  582:     */     }
/*  583:     */     
/*  584:     */     public int next(int context, int current, int expandedTypeID)
/*  585:     */     {
/*  586:1013 */       while (-1 != (current = DTMDefaultBaseTraversers.this.getNextSibling(current))) {
/*  587:1015 */         if (DTMDefaultBaseTraversers.this.getExpandedTypeID(current) == expandedTypeID) {
/*  588:1016 */           return current;
/*  589:     */         }
/*  590:     */       }
/*  591:1019 */       return -1;
/*  592:     */     }
/*  593:     */     
/*  594:     */     private FollowingSiblingTraverser() {}
/*  595:     */   }
/*  596:     */   
/*  597:     */   private class NamespaceDeclsTraverser
/*  598:     */     extends DTMAxisTraverser
/*  599:     */   {
/*  600:     */     NamespaceDeclsTraverser(DTMDefaultBaseTraversers.1 x1)
/*  601:     */     {
/*  602:1026 */       this();
/*  603:     */     }
/*  604:     */     
/*  605:     */     public int next(int context, int current)
/*  606:     */     {
/*  607:1040 */       return context == current ? DTMDefaultBaseTraversers.this.getFirstNamespaceNode(context, false) : DTMDefaultBaseTraversers.this.getNextNamespaceNode(context, current, false);
/*  608:     */     }
/*  609:     */     
/*  610:     */     public int next(int context, int current, int expandedTypeID)
/*  611:     */     {
/*  612:1058 */       current = context == current ? DTMDefaultBaseTraversers.this.getFirstNamespaceNode(context, false) : DTMDefaultBaseTraversers.this.getNextNamespaceNode(context, current, false);
/*  613:     */       do
/*  614:     */       {
/*  615:1064 */         if (DTMDefaultBaseTraversers.this.getExpandedTypeID(current) == expandedTypeID) {
/*  616:1065 */           return current;
/*  617:     */         }
/*  618:1068 */       } while (-1 != (current = DTMDefaultBaseTraversers.this.getNextNamespaceNode(context, current, false)));
/*  619:1070 */       return -1;
/*  620:     */     }
/*  621:     */     
/*  622:     */     private NamespaceDeclsTraverser() {}
/*  623:     */   }
/*  624:     */   
/*  625:     */   private class NamespaceTraverser
/*  626:     */     extends DTMAxisTraverser
/*  627:     */   {
/*  628:     */     NamespaceTraverser(DTMDefaultBaseTraversers.1 x1)
/*  629:     */     {
/*  630:1077 */       this();
/*  631:     */     }
/*  632:     */     
/*  633:     */     public int next(int context, int current)
/*  634:     */     {
/*  635:1091 */       return context == current ? DTMDefaultBaseTraversers.this.getFirstNamespaceNode(context, true) : DTMDefaultBaseTraversers.this.getNextNamespaceNode(context, current, true);
/*  636:     */     }
/*  637:     */     
/*  638:     */     public int next(int context, int current, int expandedTypeID)
/*  639:     */     {
/*  640:1109 */       current = context == current ? DTMDefaultBaseTraversers.this.getFirstNamespaceNode(context, true) : DTMDefaultBaseTraversers.this.getNextNamespaceNode(context, current, true);
/*  641:     */       do
/*  642:     */       {
/*  643:1115 */         if (DTMDefaultBaseTraversers.this.getExpandedTypeID(current) == expandedTypeID) {
/*  644:1116 */           return current;
/*  645:     */         }
/*  646:1119 */       } while (-1 != (current = DTMDefaultBaseTraversers.this.getNextNamespaceNode(context, current, true)));
/*  647:1121 */       return -1;
/*  648:     */     }
/*  649:     */     
/*  650:     */     private NamespaceTraverser() {}
/*  651:     */   }
/*  652:     */   
/*  653:     */   private class ParentTraverser
/*  654:     */     extends DTMAxisTraverser
/*  655:     */   {
/*  656:     */     ParentTraverser(DTMDefaultBaseTraversers.1 x1)
/*  657:     */     {
/*  658:1128 */       this();
/*  659:     */     }
/*  660:     */     
/*  661:     */     public int first(int context)
/*  662:     */     {
/*  663:1144 */       return DTMDefaultBaseTraversers.this.getParent(context);
/*  664:     */     }
/*  665:     */     
/*  666:     */     public int first(int current, int expandedTypeID)
/*  667:     */     {
/*  668:1164 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current);
/*  669:1166 */       while (-1 != (current = DTMDefaultBaseTraversers.this.m_parent.elementAt(current))) {
/*  670:1168 */         if (DTMDefaultBaseTraversers.this.m_exptype.elementAt(current) == expandedTypeID) {
/*  671:1169 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  672:     */         }
/*  673:     */       }
/*  674:1172 */       return -1;
/*  675:     */     }
/*  676:     */     
/*  677:     */     public int next(int context, int current)
/*  678:     */     {
/*  679:1187 */       return -1;
/*  680:     */     }
/*  681:     */     
/*  682:     */     public int next(int context, int current, int expandedTypeID)
/*  683:     */     {
/*  684:1205 */       return -1;
/*  685:     */     }
/*  686:     */     
/*  687:     */     private ParentTraverser() {}
/*  688:     */   }
/*  689:     */   
/*  690:     */   private class PrecedingTraverser
/*  691:     */     extends DTMAxisTraverser
/*  692:     */   {
/*  693:     */     PrecedingTraverser(DTMDefaultBaseTraversers.1 x1)
/*  694:     */     {
/*  695:1212 */       this();
/*  696:     */     }
/*  697:     */     
/*  698:     */     protected boolean isAncestor(int contextIdent, int currentIdent)
/*  699:     */     {
/*  700:1228 */       for (contextIdent = DTMDefaultBaseTraversers.this.m_parent.elementAt(contextIdent); -1 != contextIdent; contextIdent = DTMDefaultBaseTraversers.this.m_parent.elementAt(contextIdent)) {
/*  701:1231 */         if (contextIdent == currentIdent) {
/*  702:1232 */           return true;
/*  703:     */         }
/*  704:     */       }
/*  705:1235 */       return false;
/*  706:     */     }
/*  707:     */     
/*  708:     */     public int next(int context, int current)
/*  709:     */     {
/*  710:1249 */       int subtreeRootIdent = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  711:1251 */       for (current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) - 1; current >= 0; current--)
/*  712:     */       {
/*  713:1253 */         short type = DTMDefaultBaseTraversers.this._type(current);
/*  714:1255 */         if ((2 != type) && (13 != type) && (!isAncestor(subtreeRootIdent, current))) {
/*  715:1259 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  716:     */         }
/*  717:     */       }
/*  718:1262 */       return -1;
/*  719:     */     }
/*  720:     */     
/*  721:     */     public int next(int context, int current, int expandedTypeID)
/*  722:     */     {
/*  723:1278 */       int subtreeRootIdent = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  724:1280 */       for (current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) - 1; current >= 0; current--)
/*  725:     */       {
/*  726:1282 */         int exptype = DTMDefaultBaseTraversers.this.m_exptype.elementAt(current);
/*  727:1284 */         if ((exptype == expandedTypeID) && (!isAncestor(subtreeRootIdent, current))) {
/*  728:1288 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  729:     */         }
/*  730:     */       }
/*  731:1291 */       return -1;
/*  732:     */     }
/*  733:     */     
/*  734:     */     private PrecedingTraverser() {}
/*  735:     */   }
/*  736:     */   
/*  737:     */   private class PrecedingAndAncestorTraverser
/*  738:     */     extends DTMAxisTraverser
/*  739:     */   {
/*  740:     */     PrecedingAndAncestorTraverser(DTMDefaultBaseTraversers.1 x1)
/*  741:     */     {
/*  742:1299 */       this();
/*  743:     */     }
/*  744:     */     
/*  745:     */     public int next(int context, int current)
/*  746:     */     {
/*  747:1313 */       int subtreeRootIdent = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  748:1315 */       for (current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) - 1; current >= 0; current--)
/*  749:     */       {
/*  750:1317 */         short type = DTMDefaultBaseTraversers.this._type(current);
/*  751:1319 */         if ((2 != type) && (13 != type)) {
/*  752:1322 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  753:     */         }
/*  754:     */       }
/*  755:1325 */       return -1;
/*  756:     */     }
/*  757:     */     
/*  758:     */     public int next(int context, int current, int expandedTypeID)
/*  759:     */     {
/*  760:1341 */       int subtreeRootIdent = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  761:1343 */       for (current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) - 1; current >= 0; current--)
/*  762:     */       {
/*  763:1345 */         int exptype = DTMDefaultBaseTraversers.this.m_exptype.elementAt(current);
/*  764:1347 */         if (exptype == expandedTypeID) {
/*  765:1350 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  766:     */         }
/*  767:     */       }
/*  768:1353 */       return -1;
/*  769:     */     }
/*  770:     */     
/*  771:     */     private PrecedingAndAncestorTraverser() {}
/*  772:     */   }
/*  773:     */   
/*  774:     */   private class PrecedingSiblingTraverser
/*  775:     */     extends DTMAxisTraverser
/*  776:     */   {
/*  777:     */     PrecedingSiblingTraverser(DTMDefaultBaseTraversers.1 x1)
/*  778:     */     {
/*  779:1360 */       this();
/*  780:     */     }
/*  781:     */     
/*  782:     */     public int next(int context, int current)
/*  783:     */     {
/*  784:1373 */       return DTMDefaultBaseTraversers.this.getPreviousSibling(current);
/*  785:     */     }
/*  786:     */     
/*  787:     */     public int next(int context, int current, int expandedTypeID)
/*  788:     */     {
/*  789:1389 */       while (-1 != (current = DTMDefaultBaseTraversers.this.getPreviousSibling(current))) {
/*  790:1391 */         if (DTMDefaultBaseTraversers.this.getExpandedTypeID(current) == expandedTypeID) {
/*  791:1392 */           return current;
/*  792:     */         }
/*  793:     */       }
/*  794:1395 */       return -1;
/*  795:     */     }
/*  796:     */     
/*  797:     */     private PrecedingSiblingTraverser() {}
/*  798:     */   }
/*  799:     */   
/*  800:     */   private class SelfTraverser
/*  801:     */     extends DTMAxisTraverser
/*  802:     */   {
/*  803:     */     SelfTraverser(DTMDefaultBaseTraversers.1 x1)
/*  804:     */     {
/*  805:1402 */       this();
/*  806:     */     }
/*  807:     */     
/*  808:     */     public int first(int context)
/*  809:     */     {
/*  810:1416 */       return context;
/*  811:     */     }
/*  812:     */     
/*  813:     */     public int first(int context, int expandedTypeID)
/*  814:     */     {
/*  815:1433 */       return DTMDefaultBaseTraversers.this.getExpandedTypeID(context) == expandedTypeID ? context : -1;
/*  816:     */     }
/*  817:     */     
/*  818:     */     public int next(int context, int current)
/*  819:     */     {
/*  820:1446 */       return -1;
/*  821:     */     }
/*  822:     */     
/*  823:     */     public int next(int context, int current, int expandedTypeID)
/*  824:     */     {
/*  825:1461 */       return -1;
/*  826:     */     }
/*  827:     */     
/*  828:     */     private SelfTraverser() {}
/*  829:     */   }
/*  830:     */   
/*  831:     */   private class AllFromRootTraverser
/*  832:     */     extends DTMDefaultBaseTraversers.AllFromNodeTraverser
/*  833:     */   {
/*  834:     */     AllFromRootTraverser(DTMDefaultBaseTraversers.1 x1)
/*  835:     */     {
/*  836:1468 */       this();
/*  837:     */     }
/*  838:     */     
/*  839:     */     private AllFromRootTraverser()
/*  840:     */     {
/*  841:1468 */       super(null);
/*  842:     */     }
/*  843:     */     
/*  844:     */     public int first(int context)
/*  845:     */     {
/*  846:1480 */       return DTMDefaultBaseTraversers.this.getDocumentRoot(context);
/*  847:     */     }
/*  848:     */     
/*  849:     */     public int first(int context, int expandedTypeID)
/*  850:     */     {
/*  851:1493 */       return DTMDefaultBaseTraversers.this.getExpandedTypeID(DTMDefaultBaseTraversers.this.getDocumentRoot(context)) == expandedTypeID ? context : next(context, context, expandedTypeID);
/*  852:     */     }
/*  853:     */     
/*  854:     */     public int next(int context, int current)
/*  855:     */     {
/*  856:1508 */       int subtreeRootIdent = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  857:     */       
/*  858:1510 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) + 1;
/*  859:     */       
/*  860:     */ 
/*  861:1513 */       int type = DTMDefaultBaseTraversers.this._type(current);
/*  862:1514 */       if (type == -1) {
/*  863:1515 */         return -1;
/*  864:     */       }
/*  865:1517 */       return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  866:     */     }
/*  867:     */     
/*  868:     */     public int next(int context, int current, int expandedTypeID)
/*  869:     */     {
/*  870:1534 */       int subtreeRootIdent = DTMDefaultBaseTraversers.this.makeNodeIdentity(context);
/*  871:     */       
/*  872:1536 */       current = DTMDefaultBaseTraversers.this.makeNodeIdentity(current) + 1;
/*  873:1536 */       for (goto 24;; current++)
/*  874:     */       {
/*  875:1538 */         int exptype = DTMDefaultBaseTraversers.this._exptype(current);
/*  876:1540 */         if (exptype == -1) {
/*  877:1541 */           return -1;
/*  878:     */         }
/*  879:1543 */         if (exptype == expandedTypeID) {
/*  880:1546 */           return DTMDefaultBaseTraversers.this.makeNodeHandle(current);
/*  881:     */         }
/*  882:     */       }
/*  883:     */     }
/*  884:     */   }
/*  885:     */   
/*  886:     */   private class RootTraverser
/*  887:     */     extends DTMDefaultBaseTraversers.AllFromRootTraverser
/*  888:     */   {
/*  889:     */     RootTraverser(DTMDefaultBaseTraversers.1 x1)
/*  890:     */     {
/*  891:1554 */       this();
/*  892:     */     }
/*  893:     */     
/*  894:     */     private RootTraverser()
/*  895:     */     {
/*  896:1554 */       super(null);
/*  897:     */     }
/*  898:     */     
/*  899:     */     public int first(int context, int expandedTypeID)
/*  900:     */     {
/*  901:1567 */       int root = DTMDefaultBaseTraversers.this.getDocumentRoot(context);
/*  902:1568 */       return DTMDefaultBaseTraversers.this.getExpandedTypeID(root) == expandedTypeID ? root : -1;
/*  903:     */     }
/*  904:     */     
/*  905:     */     public int next(int context, int current)
/*  906:     */     {
/*  907:1582 */       return -1;
/*  908:     */     }
/*  909:     */     
/*  910:     */     public int next(int context, int current, int expandedTypeID)
/*  911:     */     {
/*  912:1597 */       return -1;
/*  913:     */     }
/*  914:     */   }
/*  915:     */   
/*  916:     */   private class DescendantOrSelfFromRootTraverser
/*  917:     */     extends DTMDefaultBaseTraversers.DescendantTraverser
/*  918:     */   {
/*  919:     */     DescendantOrSelfFromRootTraverser(DTMDefaultBaseTraversers.1 x1)
/*  920:     */     {
/*  921:1605 */       this();
/*  922:     */     }
/*  923:     */     
/*  924:     */     private DescendantOrSelfFromRootTraverser()
/*  925:     */     {
/*  926:1605 */       super(null);
/*  927:     */     }
/*  928:     */     
/*  929:     */     protected int getFirstPotential(int identity)
/*  930:     */     {
/*  931:1618 */       return identity;
/*  932:     */     }
/*  933:     */     
/*  934:     */     protected int getSubtreeRoot(int handle)
/*  935:     */     {
/*  936:1629 */       return DTMDefaultBaseTraversers.this.makeNodeIdentity(DTMDefaultBaseTraversers.this.getDocument());
/*  937:     */     }
/*  938:     */     
/*  939:     */     public int first(int context)
/*  940:     */     {
/*  941:1641 */       return DTMDefaultBaseTraversers.this.getDocumentRoot(context);
/*  942:     */     }
/*  943:     */     
/*  944:     */     public int first(int context, int expandedTypeID)
/*  945:     */     {
/*  946:1660 */       if (isIndexed(expandedTypeID))
/*  947:     */       {
/*  948:1662 */         int identity = 0;
/*  949:1663 */         int firstPotential = getFirstPotential(identity);
/*  950:     */         
/*  951:1665 */         return DTMDefaultBaseTraversers.this.makeNodeHandle(getNextIndexed(identity, firstPotential, expandedTypeID));
/*  952:     */       }
/*  953:1668 */       int root = first(context);
/*  954:1669 */       return next(root, root, expandedTypeID);
/*  955:     */     }
/*  956:     */   }
/*  957:     */   
/*  958:     */   private class DescendantFromRootTraverser
/*  959:     */     extends DTMDefaultBaseTraversers.DescendantTraverser
/*  960:     */   {
/*  961:     */     DescendantFromRootTraverser(DTMDefaultBaseTraversers.1 x1)
/*  962:     */     {
/*  963:1677 */       this();
/*  964:     */     }
/*  965:     */     
/*  966:     */     private DescendantFromRootTraverser()
/*  967:     */     {
/*  968:1677 */       super(null);
/*  969:     */     }
/*  970:     */     
/*  971:     */     protected int getFirstPotential(int identity)
/*  972:     */     {
/*  973:1690 */       return DTMDefaultBaseTraversers.this._firstch(0);
/*  974:     */     }
/*  975:     */     
/*  976:     */     protected int getSubtreeRoot(int handle)
/*  977:     */     {
/*  978:1700 */       return 0;
/*  979:     */     }
/*  980:     */     
/*  981:     */     public int first(int context)
/*  982:     */     {
/*  983:1712 */       return DTMDefaultBaseTraversers.this.makeNodeHandle(DTMDefaultBaseTraversers.this._firstch(0));
/*  984:     */     }
/*  985:     */     
/*  986:     */     public int first(int context, int expandedTypeID)
/*  987:     */     {
/*  988:1731 */       if (isIndexed(expandedTypeID))
/*  989:     */       {
/*  990:1733 */         int identity = 0;
/*  991:1734 */         int firstPotential = getFirstPotential(identity);
/*  992:     */         
/*  993:1736 */         return DTMDefaultBaseTraversers.this.makeNodeHandle(getNextIndexed(identity, firstPotential, expandedTypeID));
/*  994:     */       }
/*  995:1739 */       int root = DTMDefaultBaseTraversers.this.getDocumentRoot(context);
/*  996:1740 */       return next(root, root, expandedTypeID);
/*  997:     */     }
/*  998:     */   }
/*  999:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMDefaultBaseTraversers
 * JD-Core Version:    0.7.0.1
 */