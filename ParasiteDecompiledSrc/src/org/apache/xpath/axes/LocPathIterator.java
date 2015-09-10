/*    1:     */ package org.apache.xpath.axes;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.Serializable;
/*    6:     */ import javax.xml.transform.TransformerException;
/*    7:     */ import org.apache.xml.dtm.DTM;
/*    8:     */ import org.apache.xml.dtm.DTMFilter;
/*    9:     */ import org.apache.xml.dtm.DTMIterator;
/*   10:     */ import org.apache.xml.dtm.DTMManager;
/*   11:     */ import org.apache.xml.utils.PrefixResolver;
/*   12:     */ import org.apache.xpath.Expression;
/*   13:     */ import org.apache.xpath.ExpressionOwner;
/*   14:     */ import org.apache.xpath.VariableStack;
/*   15:     */ import org.apache.xpath.XPathContext;
/*   16:     */ import org.apache.xpath.XPathVisitor;
/*   17:     */ import org.apache.xpath.compiler.Compiler;
/*   18:     */ import org.apache.xpath.objects.XNodeSet;
/*   19:     */ import org.apache.xpath.objects.XObject;
/*   20:     */ import org.apache.xpath.res.XPATHMessages;
/*   21:     */ import org.xml.sax.ContentHandler;
/*   22:     */ import org.xml.sax.SAXException;
/*   23:     */ 
/*   24:     */ public abstract class LocPathIterator
/*   25:     */   extends PredicatedNodeTest
/*   26:     */   implements Cloneable, DTMIterator, Serializable, PathComponent
/*   27:     */ {
/*   28:     */   static final long serialVersionUID = -4602476357268405754L;
/*   29:     */   
/*   30:     */   protected LocPathIterator() {}
/*   31:     */   
/*   32:     */   protected LocPathIterator(PrefixResolver nscontext)
/*   33:     */   {
/*   34:  72 */     setLocPathIterator(this);
/*   35:  73 */     this.m_prefixResolver = nscontext;
/*   36:     */   }
/*   37:     */   
/*   38:     */   protected LocPathIterator(Compiler compiler, int opPos, int analysis)
/*   39:     */     throws TransformerException
/*   40:     */   {
/*   41:  91 */     this(compiler, opPos, analysis, true);
/*   42:     */   }
/*   43:     */   
/*   44:     */   protected LocPathIterator(Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
/*   45:     */     throws TransformerException
/*   46:     */   {
/*   47: 113 */     setLocPathIterator(this);
/*   48:     */   }
/*   49:     */   
/*   50:     */   public int getAnalysisBits()
/*   51:     */   {
/*   52: 122 */     int axis = getAxis();
/*   53: 123 */     int bit = WalkerFactory.getAnalysisBitFromAxes(axis);
/*   54: 124 */     return bit;
/*   55:     */   }
/*   56:     */   
/*   57:     */   private void readObject(ObjectInputStream stream)
/*   58:     */     throws IOException, TransformerException
/*   59:     */   {
/*   60:     */     try
/*   61:     */     {
/*   62: 140 */       stream.defaultReadObject();
/*   63: 141 */       this.m_clones = new IteratorPool(this);
/*   64:     */     }
/*   65:     */     catch (ClassNotFoundException cnfe)
/*   66:     */     {
/*   67: 145 */       throw new TransformerException(cnfe);
/*   68:     */     }
/*   69:     */   }
/*   70:     */   
/*   71:     */   public void setEnvironment(Object environment) {}
/*   72:     */   
/*   73:     */   public DTM getDTM(int nodeHandle)
/*   74:     */   {
/*   75: 179 */     return this.m_execContext.getDTM(nodeHandle);
/*   76:     */   }
/*   77:     */   
/*   78:     */   public DTMManager getDTMManager()
/*   79:     */   {
/*   80: 191 */     return this.m_execContext.getDTMManager();
/*   81:     */   }
/*   82:     */   
/*   83:     */   public XObject execute(XPathContext xctxt)
/*   84:     */     throws TransformerException
/*   85:     */   {
/*   86: 210 */     XNodeSet iter = new XNodeSet((LocPathIterator)this.m_clones.getInstance());
/*   87:     */     
/*   88: 212 */     iter.setRoot(xctxt.getCurrentNode(), xctxt);
/*   89:     */     
/*   90: 214 */     return iter;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public void executeCharsToContentHandler(XPathContext xctxt, ContentHandler handler)
/*   94:     */     throws TransformerException, SAXException
/*   95:     */   {
/*   96: 236 */     LocPathIterator clone = (LocPathIterator)this.m_clones.getInstance();
/*   97:     */     
/*   98: 238 */     int current = xctxt.getCurrentNode();
/*   99: 239 */     clone.setRoot(current, xctxt);
/*  100:     */     
/*  101: 241 */     int node = clone.nextNode();
/*  102: 242 */     DTM dtm = clone.getDTM(node);
/*  103: 243 */     clone.detach();
/*  104: 245 */     if (node != -1) {
/*  105: 247 */       dtm.dispatchCharactersEvents(node, handler, false);
/*  106:     */     }
/*  107:     */   }
/*  108:     */   
/*  109:     */   public DTMIterator asIterator(XPathContext xctxt, int contextNode)
/*  110:     */     throws TransformerException
/*  111:     */   {
/*  112: 267 */     XNodeSet iter = new XNodeSet((LocPathIterator)this.m_clones.getInstance());
/*  113:     */     
/*  114: 269 */     iter.setRoot(contextNode, xctxt);
/*  115:     */     
/*  116: 271 */     return iter;
/*  117:     */   }
/*  118:     */   
/*  119:     */   public boolean isNodesetExpr()
/*  120:     */   {
/*  121: 282 */     return true;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public int asNode(XPathContext xctxt)
/*  125:     */     throws TransformerException
/*  126:     */   {
/*  127: 296 */     DTMIterator iter = this.m_clones.getInstance();
/*  128:     */     
/*  129: 298 */     int current = xctxt.getCurrentNode();
/*  130:     */     
/*  131: 300 */     iter.setRoot(current, xctxt);
/*  132:     */     
/*  133: 302 */     int next = iter.nextNode();
/*  134:     */     
/*  135: 304 */     iter.detach();
/*  136: 305 */     return next;
/*  137:     */   }
/*  138:     */   
/*  139:     */   public boolean bool(XPathContext xctxt)
/*  140:     */     throws TransformerException
/*  141:     */   {
/*  142: 320 */     return asNode(xctxt) != -1;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public void setIsTopLevel(boolean b)
/*  146:     */   {
/*  147: 334 */     this.m_isTopLevel = b;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public boolean getIsTopLevel()
/*  151:     */   {
/*  152: 347 */     return this.m_isTopLevel;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void setRoot(int context, Object environment)
/*  156:     */   {
/*  157: 360 */     this.m_context = context;
/*  158:     */     
/*  159: 362 */     XPathContext xctxt = (XPathContext)environment;
/*  160: 363 */     this.m_execContext = xctxt;
/*  161: 364 */     this.m_cdtm = xctxt.getDTM(context);
/*  162:     */     
/*  163: 366 */     this.m_currentContextNode = context;
/*  164: 369 */     if (null == this.m_prefixResolver) {
/*  165: 370 */       this.m_prefixResolver = xctxt.getNamespaceContext();
/*  166:     */     }
/*  167: 372 */     this.m_lastFetched = -1;
/*  168: 373 */     this.m_foundLast = false;
/*  169: 374 */     this.m_pos = 0;
/*  170: 375 */     this.m_length = -1;
/*  171: 377 */     if (this.m_isTopLevel) {
/*  172: 378 */       this.m_stackFrame = xctxt.getVarStack().getStackFrame();
/*  173:     */     }
/*  174:     */   }
/*  175:     */   
/*  176:     */   protected void setNextPosition(int next)
/*  177:     */   {
/*  178: 391 */     assertion(false, "setNextPosition not supported in this iterator!");
/*  179:     */   }
/*  180:     */   
/*  181:     */   public final int getCurrentPos()
/*  182:     */   {
/*  183: 405 */     return this.m_pos;
/*  184:     */   }
/*  185:     */   
/*  186:     */   public void setShouldCacheNodes(boolean b)
/*  187:     */   {
/*  188: 418 */     assertion(false, "setShouldCacheNodes not supported by this iterater!");
/*  189:     */   }
/*  190:     */   
/*  191:     */   public boolean isMutable()
/*  192:     */   {
/*  193: 429 */     return false;
/*  194:     */   }
/*  195:     */   
/*  196:     */   public void setCurrentPos(int i)
/*  197:     */   {
/*  198: 440 */     assertion(false, "setCurrentPos not supported by this iterator!");
/*  199:     */   }
/*  200:     */   
/*  201:     */   public void incrementCurrentPos()
/*  202:     */   {
/*  203: 448 */     this.m_pos += 1;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public int size()
/*  207:     */   {
/*  208: 464 */     assertion(false, "size() not supported by this iterator!");
/*  209: 465 */     return 0;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public int item(int index)
/*  213:     */   {
/*  214: 479 */     assertion(false, "item(int index) not supported by this iterator!");
/*  215: 480 */     return 0;
/*  216:     */   }
/*  217:     */   
/*  218:     */   public void setItem(int node, int index)
/*  219:     */   {
/*  220: 498 */     assertion(false, "setItem not supported by this iterator!");
/*  221:     */   }
/*  222:     */   
/*  223:     */   public int getLength()
/*  224:     */   {
/*  225: 510 */     boolean isPredicateTest = this == this.m_execContext.getSubContextList();
/*  226:     */     
/*  227:     */ 
/*  228: 513 */     int predCount = getPredicateCount();
/*  229: 518 */     if ((-1 != this.m_length) && (isPredicateTest) && (this.m_predicateIndex < 1)) {
/*  230: 519 */       return this.m_length;
/*  231:     */     }
/*  232: 523 */     if (this.m_foundLast) {
/*  233: 524 */       return this.m_pos;
/*  234:     */     }
/*  235: 529 */     int pos = this.m_predicateIndex >= 0 ? getProximityPosition() : this.m_pos;
/*  236:     */     LocPathIterator clone;
/*  237:     */     try
/*  238:     */     {
/*  239: 535 */       clone = (LocPathIterator)clone();
/*  240:     */     }
/*  241:     */     catch (CloneNotSupportedException cnse)
/*  242:     */     {
/*  243: 539 */       return -1;
/*  244:     */     }
/*  245: 545 */     if ((predCount > 0) && (isPredicateTest)) {
/*  246: 548 */       clone.m_predCount = this.m_predicateIndex;
/*  247:     */     }
/*  248:     */     int next;
/*  249: 556 */     while (-1 != (next = clone.nextNode())) {
/*  250: 558 */       pos++;
/*  251:     */     }
/*  252: 561 */     if ((isPredicateTest) && (this.m_predicateIndex < 1)) {
/*  253: 562 */       this.m_length = pos;
/*  254:     */     }
/*  255: 564 */     return pos;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public boolean isFresh()
/*  259:     */   {
/*  260: 576 */     return this.m_pos == 0;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public int previousNode()
/*  264:     */   {
/*  265: 587 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_CANNOT_ITERATE", null));
/*  266:     */   }
/*  267:     */   
/*  268:     */   public int getWhatToShow()
/*  269:     */   {
/*  270: 608 */     return -17;
/*  271:     */   }
/*  272:     */   
/*  273:     */   public DTMFilter getFilter()
/*  274:     */   {
/*  275: 621 */     return null;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public int getRoot()
/*  279:     */   {
/*  280: 632 */     return this.m_context;
/*  281:     */   }
/*  282:     */   
/*  283:     */   public boolean getExpandEntityReferences()
/*  284:     */   {
/*  285: 652 */     return true;
/*  286:     */   }
/*  287:     */   
/*  288: 656 */   protected boolean m_allowDetach = true;
/*  289:     */   
/*  290:     */   public void allowDetachToRelease(boolean allowRelease)
/*  291:     */   {
/*  292: 666 */     this.m_allowDetach = allowRelease;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public void detach()
/*  296:     */   {
/*  297: 678 */     if (this.m_allowDetach)
/*  298:     */     {
/*  299: 682 */       this.m_execContext = null;
/*  300:     */       
/*  301: 684 */       this.m_cdtm = null;
/*  302: 685 */       this.m_length = -1;
/*  303: 686 */       this.m_pos = 0;
/*  304: 687 */       this.m_lastFetched = -1;
/*  305: 688 */       this.m_context = -1;
/*  306: 689 */       this.m_currentContextNode = -1;
/*  307:     */       
/*  308: 691 */       this.m_clones.freeInstance(this);
/*  309:     */     }
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void reset()
/*  313:     */   {
/*  314: 700 */     assertion(false, "This iterator can not reset!");
/*  315:     */   }
/*  316:     */   
/*  317:     */   public DTMIterator cloneWithReset()
/*  318:     */     throws CloneNotSupportedException
/*  319:     */   {
/*  320: 715 */     LocPathIterator clone = (LocPathIterator)this.m_clones.getInstanceOrThrow();
/*  321: 716 */     clone.m_execContext = this.m_execContext;
/*  322: 717 */     clone.m_cdtm = this.m_cdtm;
/*  323:     */     
/*  324: 719 */     clone.m_context = this.m_context;
/*  325: 720 */     clone.m_currentContextNode = this.m_currentContextNode;
/*  326: 721 */     clone.m_stackFrame = this.m_stackFrame;
/*  327:     */     
/*  328:     */ 
/*  329:     */ 
/*  330: 725 */     return clone;
/*  331:     */   }
/*  332:     */   
/*  333:     */   public abstract int nextNode();
/*  334:     */   
/*  335:     */   protected int returnNextNode(int nextNode)
/*  336:     */   {
/*  337: 764 */     if (-1 != nextNode) {
/*  338: 766 */       this.m_pos += 1;
/*  339:     */     }
/*  340: 769 */     this.m_lastFetched = nextNode;
/*  341: 771 */     if (-1 == nextNode) {
/*  342: 772 */       this.m_foundLast = true;
/*  343:     */     }
/*  344: 774 */     return nextNode;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public int getCurrentNode()
/*  348:     */   {
/*  349: 784 */     return this.m_lastFetched;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public void runTo(int index)
/*  353:     */   {
/*  354: 799 */     if ((this.m_foundLast) || ((index >= 0) && (index <= getCurrentPos()))) {
/*  355:     */       return;
/*  356:     */     }
/*  357:     */     int n;
/*  358: 804 */     if (-1 == index) {
/*  359: 806 */       while ((goto 28) || (-1 != (n = nextNode()))) {}
/*  360:     */     } else {
/*  361: 810 */       while (-1 != (n = nextNode())) {
/*  362: 812 */         if (getCurrentPos() >= index) {
/*  363:     */           break;
/*  364:     */         }
/*  365:     */       }
/*  366:     */     }
/*  367:     */   }
/*  368:     */   
/*  369:     */   public final boolean getFoundLast()
/*  370:     */   {
/*  371: 825 */     return this.m_foundLast;
/*  372:     */   }
/*  373:     */   
/*  374:     */   public final XPathContext getXPathContext()
/*  375:     */   {
/*  376: 836 */     return this.m_execContext;
/*  377:     */   }
/*  378:     */   
/*  379:     */   public final int getContext()
/*  380:     */   {
/*  381: 846 */     return this.m_context;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public final int getCurrentContextNode()
/*  385:     */   {
/*  386: 857 */     return this.m_currentContextNode;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public final void setCurrentContextNode(int n)
/*  390:     */   {
/*  391: 867 */     this.m_currentContextNode = n;
/*  392:     */   }
/*  393:     */   
/*  394:     */   public final PrefixResolver getPrefixResolver()
/*  395:     */   {
/*  396: 889 */     if (null == this.m_prefixResolver) {
/*  397: 891 */       this.m_prefixResolver = ((PrefixResolver)getExpressionOwner());
/*  398:     */     }
/*  399: 894 */     return this.m_prefixResolver;
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/*  403:     */   {
/*  404: 922 */     if (visitor.visitLocationPath(owner, this))
/*  405:     */     {
/*  406: 924 */       visitor.visitStep(owner, this);
/*  407: 925 */       callPredicateVisitors(visitor);
/*  408:     */     }
/*  409:     */   }
/*  410:     */   
/*  411: 937 */   protected transient IteratorPool m_clones = new IteratorPool(this);
/*  412:     */   protected transient DTM m_cdtm;
/*  413: 948 */   transient int m_stackFrame = -1;
/*  414: 956 */   private boolean m_isTopLevel = false;
/*  415: 959 */   public transient int m_lastFetched = -1;
/*  416: 965 */   protected transient int m_context = -1;
/*  417: 973 */   protected transient int m_currentContextNode = -1;
/*  418: 978 */   protected transient int m_pos = 0;
/*  419: 980 */   protected transient int m_length = -1;
/*  420:     */   private PrefixResolver m_prefixResolver;
/*  421:     */   protected transient XPathContext m_execContext;
/*  422:     */   
/*  423:     */   public boolean isDocOrdered()
/*  424:     */   {
/*  425:1003 */     return true;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public int getAxis()
/*  429:     */   {
/*  430:1014 */     return -1;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public int getLastPos(XPathContext xctxt)
/*  434:     */   {
/*  435:1030 */     return getLength();
/*  436:     */   }
/*  437:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.LocPathIterator
 * JD-Core Version:    0.7.0.1
 */