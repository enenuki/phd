/*    1:     */ package org.apache.xpath;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.lang.reflect.Method;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.Enumeration;
/*    7:     */ import java.util.HashMap;
/*    8:     */ import java.util.Iterator;
/*    9:     */ import java.util.Stack;
/*   10:     */ import java.util.Vector;
/*   11:     */ import javax.xml.transform.ErrorListener;
/*   12:     */ import javax.xml.transform.Source;
/*   13:     */ import javax.xml.transform.SourceLocator;
/*   14:     */ import javax.xml.transform.TransformerException;
/*   15:     */ import javax.xml.transform.URIResolver;
/*   16:     */ import org.apache.xalan.extensions.ExpressionContext;
/*   17:     */ import org.apache.xalan.res.XSLMessages;
/*   18:     */ import org.apache.xml.dtm.DTM;
/*   19:     */ import org.apache.xml.dtm.DTMFilter;
/*   20:     */ import org.apache.xml.dtm.DTMIterator;
/*   21:     */ import org.apache.xml.dtm.DTMManager;
/*   22:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   23:     */ import org.apache.xml.dtm.ref.DTMNodeIterator;
/*   24:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2RTFDTM;
/*   25:     */ import org.apache.xml.utils.DefaultErrorHandler;
/*   26:     */ import org.apache.xml.utils.IntStack;
/*   27:     */ import org.apache.xml.utils.IntVector;
/*   28:     */ import org.apache.xml.utils.NodeVector;
/*   29:     */ import org.apache.xml.utils.ObjectStack;
/*   30:     */ import org.apache.xml.utils.ObjectVector;
/*   31:     */ import org.apache.xml.utils.PrefixResolver;
/*   32:     */ import org.apache.xml.utils.QName;
/*   33:     */ import org.apache.xml.utils.SAXSourceLocator;
/*   34:     */ import org.apache.xml.utils.XMLString;
/*   35:     */ import org.apache.xpath.axes.OneStepIteratorForward;
/*   36:     */ import org.apache.xpath.axes.SubContextList;
/*   37:     */ import org.apache.xpath.objects.DTMXRTreeFrag;
/*   38:     */ import org.apache.xpath.objects.XMLStringFactoryImpl;
/*   39:     */ import org.apache.xpath.objects.XObject;
/*   40:     */ import org.apache.xpath.objects.XString;
/*   41:     */ import org.apache.xpath.res.XPATHMessages;
/*   42:     */ import org.w3c.dom.Node;
/*   43:     */ import org.w3c.dom.traversal.NodeIterator;
/*   44:     */ import org.xml.sax.XMLReader;
/*   45:     */ 
/*   46:     */ public class XPathContext
/*   47:     */   extends DTMManager
/*   48:     */ {
/*   49:  65 */   IntStack m_last_pushed_rtfdtm = new IntStack();
/*   50:  76 */   private Vector m_rtfdtm_stack = null;
/*   51:  78 */   private int m_which_rtfdtm = -1;
/*   52:  84 */   private SAX2RTFDTM m_global_rtfdtm = null;
/*   53:  90 */   private HashMap m_DTMXRTreeFrags = null;
/*   54:  95 */   private boolean m_isSecureProcessing = false;
/*   55: 102 */   protected DTMManager m_dtmManager = DTMManager.newInstance(XMLStringFactoryImpl.getFactory());
/*   56:     */   
/*   57:     */   public DTMManager getDTMManager()
/*   58:     */   {
/*   59: 113 */     return this.m_dtmManager;
/*   60:     */   }
/*   61:     */   
/*   62:     */   public void setSecureProcessing(boolean flag)
/*   63:     */   {
/*   64: 121 */     this.m_isSecureProcessing = flag;
/*   65:     */   }
/*   66:     */   
/*   67:     */   public boolean isSecureProcessing()
/*   68:     */   {
/*   69: 129 */     return this.m_isSecureProcessing;
/*   70:     */   }
/*   71:     */   
/*   72:     */   public DTM getDTM(Source source, boolean unique, DTMWSFilter wsfilter, boolean incremental, boolean doIndexing)
/*   73:     */   {
/*   74: 158 */     return this.m_dtmManager.getDTM(source, unique, wsfilter, incremental, doIndexing);
/*   75:     */   }
/*   76:     */   
/*   77:     */   public DTM getDTM(int nodeHandle)
/*   78:     */   {
/*   79: 171 */     return this.m_dtmManager.getDTM(nodeHandle);
/*   80:     */   }
/*   81:     */   
/*   82:     */   public int getDTMHandleFromNode(Node node)
/*   83:     */   {
/*   84: 184 */     return this.m_dtmManager.getDTMHandleFromNode(node);
/*   85:     */   }
/*   86:     */   
/*   87:     */   public int getDTMIdentity(DTM dtm)
/*   88:     */   {
/*   89: 193 */     return this.m_dtmManager.getDTMIdentity(dtm);
/*   90:     */   }
/*   91:     */   
/*   92:     */   public DTM createDocumentFragment()
/*   93:     */   {
/*   94: 202 */     return this.m_dtmManager.createDocumentFragment();
/*   95:     */   }
/*   96:     */   
/*   97:     */   public boolean release(DTM dtm, boolean shouldHardDelete)
/*   98:     */   {
/*   99: 221 */     if ((this.m_rtfdtm_stack != null) && (this.m_rtfdtm_stack.contains(dtm))) {
/*  100: 223 */       return false;
/*  101:     */     }
/*  102: 226 */     return this.m_dtmManager.release(dtm, shouldHardDelete);
/*  103:     */   }
/*  104:     */   
/*  105:     */   public DTMIterator createDTMIterator(Object xpathCompiler, int pos)
/*  106:     */   {
/*  107: 243 */     return this.m_dtmManager.createDTMIterator(xpathCompiler, pos);
/*  108:     */   }
/*  109:     */   
/*  110:     */   public DTMIterator createDTMIterator(String xpathString, PrefixResolver presolver)
/*  111:     */   {
/*  112: 262 */     return this.m_dtmManager.createDTMIterator(xpathString, presolver);
/*  113:     */   }
/*  114:     */   
/*  115:     */   public DTMIterator createDTMIterator(int whatToShow, DTMFilter filter, boolean entityReferenceExpansion)
/*  116:     */   {
/*  117: 285 */     return this.m_dtmManager.createDTMIterator(whatToShow, filter, entityReferenceExpansion);
/*  118:     */   }
/*  119:     */   
/*  120:     */   public DTMIterator createDTMIterator(int node)
/*  121:     */   {
/*  122: 298 */     DTMIterator iter = new OneStepIteratorForward(13);
/*  123: 299 */     iter.setRoot(node, this);
/*  124: 300 */     return iter;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public XPathContext()
/*  128:     */   {
/*  129: 310 */     this(true);
/*  130:     */   }
/*  131:     */   
/*  132:     */   public XPathContext(boolean recursiveVarContext)
/*  133:     */   {
/*  134: 320 */     this.m_prefixResolvers.push(null);
/*  135: 321 */     this.m_currentNodes.push(-1);
/*  136: 322 */     this.m_currentExpressionNodes.push(-1);
/*  137: 323 */     this.m_saxLocations.push(null);
/*  138: 324 */     this.m_variableStacks = (recursiveVarContext ? new VariableStack() : new VariableStack(1));
/*  139:     */   }
/*  140:     */   
/*  141:     */   public XPathContext(Object owner)
/*  142:     */   {
/*  143: 337 */     this(owner, true);
/*  144:     */   }
/*  145:     */   
/*  146:     */   public XPathContext(Object owner, boolean recursiveVarContext)
/*  147:     */   {
/*  148: 349 */     this(recursiveVarContext);
/*  149: 350 */     this.m_owner = owner;
/*  150:     */     try
/*  151:     */     {
/*  152: 352 */       this.m_ownerGetErrorListener = this.m_owner.getClass().getMethod("getErrorListener", new Class[0]);
/*  153:     */     }
/*  154:     */     catch (NoSuchMethodException nsme) {}
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void reset()
/*  158:     */   {
/*  159: 362 */     releaseDTMXRTreeFrags();
/*  160: 364 */     if (this.m_rtfdtm_stack != null) {
/*  161: 365 */       for (Enumeration e = this.m_rtfdtm_stack.elements(); e.hasMoreElements();) {
/*  162: 366 */         this.m_dtmManager.release((DTM)e.nextElement(), true);
/*  163:     */       }
/*  164:     */     }
/*  165: 368 */     this.m_rtfdtm_stack = null;
/*  166: 369 */     this.m_which_rtfdtm = -1;
/*  167: 371 */     if (this.m_global_rtfdtm != null) {
/*  168: 372 */       this.m_dtmManager.release(this.m_global_rtfdtm, true);
/*  169:     */     }
/*  170: 373 */     this.m_global_rtfdtm = null;
/*  171:     */     
/*  172:     */ 
/*  173: 376 */     this.m_dtmManager = DTMManager.newInstance(XMLStringFactoryImpl.getFactory());
/*  174:     */     
/*  175:     */ 
/*  176: 379 */     this.m_saxLocations.removeAllElements();
/*  177: 380 */     this.m_axesIteratorStack.removeAllElements();
/*  178: 381 */     this.m_contextNodeLists.removeAllElements();
/*  179: 382 */     this.m_currentExpressionNodes.removeAllElements();
/*  180: 383 */     this.m_currentNodes.removeAllElements();
/*  181: 384 */     this.m_iteratorRoots.RemoveAllNoClear();
/*  182: 385 */     this.m_predicatePos.removeAllElements();
/*  183: 386 */     this.m_predicateRoots.RemoveAllNoClear();
/*  184: 387 */     this.m_prefixResolvers.removeAllElements();
/*  185:     */     
/*  186: 389 */     this.m_prefixResolvers.push(null);
/*  187: 390 */     this.m_currentNodes.push(-1);
/*  188: 391 */     this.m_currentExpressionNodes.push(-1);
/*  189: 392 */     this.m_saxLocations.push(null);
/*  190:     */   }
/*  191:     */   
/*  192: 396 */   ObjectStack m_saxLocations = new ObjectStack(4096);
/*  193:     */   private Object m_owner;
/*  194:     */   private Method m_ownerGetErrorListener;
/*  195:     */   private VariableStack m_variableStacks;
/*  196:     */   
/*  197:     */   public void setSAXLocator(SourceLocator location)
/*  198:     */   {
/*  199: 405 */     this.m_saxLocations.setTop(location);
/*  200:     */   }
/*  201:     */   
/*  202:     */   public void pushSAXLocator(SourceLocator location)
/*  203:     */   {
/*  204: 415 */     this.m_saxLocations.push(location);
/*  205:     */   }
/*  206:     */   
/*  207:     */   public void pushSAXLocatorNull()
/*  208:     */   {
/*  209: 425 */     this.m_saxLocations.push(null);
/*  210:     */   }
/*  211:     */   
/*  212:     */   public void popSAXLocator()
/*  213:     */   {
/*  214: 434 */     this.m_saxLocations.pop();
/*  215:     */   }
/*  216:     */   
/*  217:     */   public SourceLocator getSAXLocator()
/*  218:     */   {
/*  219: 444 */     return (SourceLocator)this.m_saxLocations.peek();
/*  220:     */   }
/*  221:     */   
/*  222:     */   public Object getOwnerObject()
/*  223:     */   {
/*  224: 465 */     return this.m_owner;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public final VariableStack getVarStack()
/*  228:     */   {
/*  229: 484 */     return this.m_variableStacks;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public final void setVarStack(VariableStack varStack)
/*  233:     */   {
/*  234: 495 */     this.m_variableStacks = varStack;
/*  235:     */   }
/*  236:     */   
/*  237: 502 */   private SourceTreeManager m_sourceTreeManager = new SourceTreeManager();
/*  238:     */   private ErrorListener m_errorListener;
/*  239:     */   private ErrorListener m_defaultErrorListener;
/*  240:     */   private URIResolver m_uriResolver;
/*  241:     */   public XMLReader m_primaryReader;
/*  242:     */   
/*  243:     */   public final SourceTreeManager getSourceTreeManager()
/*  244:     */   {
/*  245: 511 */     return this.m_sourceTreeManager;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public void setSourceTreeManager(SourceTreeManager mgr)
/*  249:     */   {
/*  250: 522 */     this.m_sourceTreeManager = mgr;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public final ErrorListener getErrorListener()
/*  254:     */   {
/*  255: 543 */     if (null != this.m_errorListener) {
/*  256: 544 */       return this.m_errorListener;
/*  257:     */     }
/*  258: 546 */     ErrorListener retval = null;
/*  259:     */     try
/*  260:     */     {
/*  261: 549 */       if (null != this.m_ownerGetErrorListener) {
/*  262: 550 */         retval = (ErrorListener)this.m_ownerGetErrorListener.invoke(this.m_owner, new Object[0]);
/*  263:     */       }
/*  264:     */     }
/*  265:     */     catch (Exception e) {}
/*  266: 554 */     if (null == retval)
/*  267:     */     {
/*  268: 556 */       if (null == this.m_defaultErrorListener) {
/*  269: 557 */         this.m_defaultErrorListener = new DefaultErrorHandler();
/*  270:     */       }
/*  271: 558 */       retval = this.m_defaultErrorListener;
/*  272:     */     }
/*  273: 561 */     return retval;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public void setErrorListener(ErrorListener listener)
/*  277:     */     throws IllegalArgumentException
/*  278:     */   {
/*  279: 571 */     if (listener == null) {
/*  280: 572 */       throw new IllegalArgumentException(XPATHMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", null));
/*  281:     */     }
/*  282: 573 */     this.m_errorListener = listener;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public final URIResolver getURIResolver()
/*  286:     */   {
/*  287: 590 */     return this.m_uriResolver;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public void setURIResolver(URIResolver resolver)
/*  291:     */   {
/*  292: 601 */     this.m_uriResolver = resolver;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public final XMLReader getPrimaryReader()
/*  296:     */   {
/*  297: 616 */     return this.m_primaryReader;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void setPrimaryReader(XMLReader reader)
/*  301:     */   {
/*  302: 626 */     this.m_primaryReader = reader;
/*  303:     */   }
/*  304:     */   
/*  305:     */   private void assertion(boolean b, String msg)
/*  306:     */     throws TransformerException
/*  307:     */   {
/*  308: 646 */     if (!b)
/*  309:     */     {
/*  310: 648 */       ErrorListener errorHandler = getErrorListener();
/*  311: 650 */       if (errorHandler != null) {
/*  312: 652 */         errorHandler.fatalError(new TransformerException(XSLMessages.createMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { msg }), (SAXSourceLocator)getSAXLocator()));
/*  313:     */       }
/*  314:     */     }
/*  315:     */   }
/*  316:     */   
/*  317: 668 */   private Stack m_contextNodeLists = new Stack();
/*  318:     */   public static final int RECURSIONLIMIT = 4096;
/*  319:     */   
/*  320:     */   public Stack getContextNodeListsStack()
/*  321:     */   {
/*  322: 670 */     return this.m_contextNodeLists;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public void setContextNodeListsStack(Stack s)
/*  326:     */   {
/*  327: 671 */     this.m_contextNodeLists = s;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public final DTMIterator getContextNodeList()
/*  331:     */   {
/*  332: 682 */     if (this.m_contextNodeLists.size() > 0) {
/*  333: 683 */       return (DTMIterator)this.m_contextNodeLists.peek();
/*  334:     */     }
/*  335: 685 */     return null;
/*  336:     */   }
/*  337:     */   
/*  338:     */   public final void pushContextNodeList(DTMIterator nl)
/*  339:     */   {
/*  340: 697 */     this.m_contextNodeLists.push(nl);
/*  341:     */   }
/*  342:     */   
/*  343:     */   public final void popContextNodeList()
/*  344:     */   {
/*  345: 706 */     if (this.m_contextNodeLists.isEmpty()) {
/*  346: 707 */       System.err.println("Warning: popContextNodeList when stack is empty!");
/*  347:     */     } else {
/*  348: 709 */       this.m_contextNodeLists.pop();
/*  349:     */     }
/*  350:     */   }
/*  351:     */   
/*  352: 722 */   private IntStack m_currentNodes = new IntStack(4096);
/*  353:     */   
/*  354:     */   public IntStack getCurrentNodeStack()
/*  355:     */   {
/*  356: 726 */     return this.m_currentNodes;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public void setCurrentNodeStack(IntStack nv)
/*  360:     */   {
/*  361: 727 */     this.m_currentNodes = nv;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public final int getCurrentNode()
/*  365:     */   {
/*  366: 736 */     return this.m_currentNodes.peek();
/*  367:     */   }
/*  368:     */   
/*  369:     */   public final void pushCurrentNodeAndExpression(int cn, int en)
/*  370:     */   {
/*  371: 747 */     this.m_currentNodes.push(cn);
/*  372: 748 */     this.m_currentExpressionNodes.push(cn);
/*  373:     */   }
/*  374:     */   
/*  375:     */   public final void popCurrentNodeAndExpression()
/*  376:     */   {
/*  377: 756 */     this.m_currentNodes.quickPop(1);
/*  378: 757 */     this.m_currentExpressionNodes.quickPop(1);
/*  379:     */   }
/*  380:     */   
/*  381:     */   public final void pushExpressionState(int cn, int en, PrefixResolver nc)
/*  382:     */   {
/*  383: 769 */     this.m_currentNodes.push(cn);
/*  384: 770 */     this.m_currentExpressionNodes.push(cn);
/*  385: 771 */     this.m_prefixResolvers.push(nc);
/*  386:     */   }
/*  387:     */   
/*  388:     */   public final void popExpressionState()
/*  389:     */   {
/*  390: 779 */     this.m_currentNodes.quickPop(1);
/*  391: 780 */     this.m_currentExpressionNodes.quickPop(1);
/*  392: 781 */     this.m_prefixResolvers.pop();
/*  393:     */   }
/*  394:     */   
/*  395:     */   public final void pushCurrentNode(int n)
/*  396:     */   {
/*  397: 793 */     this.m_currentNodes.push(n);
/*  398:     */   }
/*  399:     */   
/*  400:     */   public final void popCurrentNode()
/*  401:     */   {
/*  402: 801 */     this.m_currentNodes.quickPop(1);
/*  403:     */   }
/*  404:     */   
/*  405:     */   public final void pushPredicateRoot(int n)
/*  406:     */   {
/*  407: 809 */     this.m_predicateRoots.push(n);
/*  408:     */   }
/*  409:     */   
/*  410:     */   public final void popPredicateRoot()
/*  411:     */   {
/*  412: 817 */     this.m_predicateRoots.popQuick();
/*  413:     */   }
/*  414:     */   
/*  415:     */   public final int getPredicateRoot()
/*  416:     */   {
/*  417: 825 */     return this.m_predicateRoots.peepOrNull();
/*  418:     */   }
/*  419:     */   
/*  420:     */   public final void pushIteratorRoot(int n)
/*  421:     */   {
/*  422: 833 */     this.m_iteratorRoots.push(n);
/*  423:     */   }
/*  424:     */   
/*  425:     */   public final void popIteratorRoot()
/*  426:     */   {
/*  427: 841 */     this.m_iteratorRoots.popQuick();
/*  428:     */   }
/*  429:     */   
/*  430:     */   public final int getIteratorRoot()
/*  431:     */   {
/*  432: 849 */     return this.m_iteratorRoots.peepOrNull();
/*  433:     */   }
/*  434:     */   
/*  435: 853 */   private NodeVector m_iteratorRoots = new NodeVector();
/*  436: 856 */   private NodeVector m_predicateRoots = new NodeVector();
/*  437: 859 */   private IntStack m_currentExpressionNodes = new IntStack(4096);
/*  438:     */   
/*  439:     */   public IntStack getCurrentExpressionNodeStack()
/*  440:     */   {
/*  441: 862 */     return this.m_currentExpressionNodes;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void setCurrentExpressionNodeStack(IntStack nv)
/*  445:     */   {
/*  446: 863 */     this.m_currentExpressionNodes = nv;
/*  447:     */   }
/*  448:     */   
/*  449: 865 */   private IntStack m_predicatePos = new IntStack();
/*  450:     */   
/*  451:     */   public final int getPredicatePos()
/*  452:     */   {
/*  453: 869 */     return this.m_predicatePos.peek();
/*  454:     */   }
/*  455:     */   
/*  456:     */   public final void pushPredicatePos(int n)
/*  457:     */   {
/*  458: 874 */     this.m_predicatePos.push(n);
/*  459:     */   }
/*  460:     */   
/*  461:     */   public final void popPredicatePos()
/*  462:     */   {
/*  463: 879 */     this.m_predicatePos.pop();
/*  464:     */   }
/*  465:     */   
/*  466:     */   public final int getCurrentExpressionNode()
/*  467:     */   {
/*  468: 889 */     return this.m_currentExpressionNodes.peek();
/*  469:     */   }
/*  470:     */   
/*  471:     */   public final void pushCurrentExpressionNode(int n)
/*  472:     */   {
/*  473: 899 */     this.m_currentExpressionNodes.push(n);
/*  474:     */   }
/*  475:     */   
/*  476:     */   public final void popCurrentExpressionNode()
/*  477:     */   {
/*  478: 908 */     this.m_currentExpressionNodes.quickPop(1);
/*  479:     */   }
/*  480:     */   
/*  481: 911 */   private ObjectStack m_prefixResolvers = new ObjectStack(4096);
/*  482:     */   
/*  483:     */   public final PrefixResolver getNamespaceContext()
/*  484:     */   {
/*  485: 922 */     return (PrefixResolver)this.m_prefixResolvers.peek();
/*  486:     */   }
/*  487:     */   
/*  488:     */   public final void setNamespaceContext(PrefixResolver pr)
/*  489:     */   {
/*  490: 933 */     this.m_prefixResolvers.setTop(pr);
/*  491:     */   }
/*  492:     */   
/*  493:     */   public final void pushNamespaceContext(PrefixResolver pr)
/*  494:     */   {
/*  495: 944 */     this.m_prefixResolvers.push(pr);
/*  496:     */   }
/*  497:     */   
/*  498:     */   public final void pushNamespaceContextNull()
/*  499:     */   {
/*  500: 953 */     this.m_prefixResolvers.push(null);
/*  501:     */   }
/*  502:     */   
/*  503:     */   public final void popNamespaceContext()
/*  504:     */   {
/*  505: 961 */     this.m_prefixResolvers.pop();
/*  506:     */   }
/*  507:     */   
/*  508: 971 */   private Stack m_axesIteratorStack = new Stack();
/*  509:     */   
/*  510:     */   public Stack getAxesIteratorStackStacks()
/*  511:     */   {
/*  512: 973 */     return this.m_axesIteratorStack;
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void setAxesIteratorStackStacks(Stack s)
/*  516:     */   {
/*  517: 974 */     this.m_axesIteratorStack = s;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public final void pushSubContextList(SubContextList iter)
/*  521:     */   {
/*  522: 984 */     this.m_axesIteratorStack.push(iter);
/*  523:     */   }
/*  524:     */   
/*  525:     */   public final void popSubContextList()
/*  526:     */   {
/*  527: 993 */     this.m_axesIteratorStack.pop();
/*  528:     */   }
/*  529:     */   
/*  530:     */   public SubContextList getSubContextList()
/*  531:     */   {
/*  532:1004 */     return this.m_axesIteratorStack.isEmpty() ? null : (SubContextList)this.m_axesIteratorStack.peek();
/*  533:     */   }
/*  534:     */   
/*  535:     */   public SubContextList getCurrentNodeList()
/*  536:     */   {
/*  537:1018 */     return this.m_axesIteratorStack.isEmpty() ? null : (SubContextList)this.m_axesIteratorStack.elementAt(0);
/*  538:     */   }
/*  539:     */   
/*  540:     */   public final int getContextNode()
/*  541:     */   {
/*  542:1031 */     return getCurrentNode();
/*  543:     */   }
/*  544:     */   
/*  545:     */   public final DTMIterator getContextNodes()
/*  546:     */   {
/*  547:     */     try
/*  548:     */     {
/*  549:1044 */       DTMIterator cnl = getContextNodeList();
/*  550:1046 */       if (null != cnl) {
/*  551:1047 */         return cnl.cloneWithReset();
/*  552:     */       }
/*  553:1049 */       return null;
/*  554:     */     }
/*  555:     */     catch (CloneNotSupportedException cnse) {}
/*  556:1053 */     return null;
/*  557:     */   }
/*  558:     */   
/*  559:1057 */   XPathExpressionContext expressionContext = new XPathExpressionContext();
/*  560:     */   
/*  561:     */   public ExpressionContext getExpressionContext()
/*  562:     */   {
/*  563:1066 */     return this.expressionContext;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public class XPathExpressionContext
/*  567:     */     implements ExpressionContext
/*  568:     */   {
/*  569:     */     public XPathExpressionContext() {}
/*  570:     */     
/*  571:     */     public XPathContext getXPathContext()
/*  572:     */     {
/*  573:1080 */       return XPathContext.this;
/*  574:     */     }
/*  575:     */     
/*  576:     */     public DTMManager getDTMManager()
/*  577:     */     {
/*  578:1091 */       return XPathContext.this.m_dtmManager;
/*  579:     */     }
/*  580:     */     
/*  581:     */     public Node getContextNode()
/*  582:     */     {
/*  583:1100 */       int context = XPathContext.this.getCurrentNode();
/*  584:     */       
/*  585:1102 */       return XPathContext.this.getDTM(context).getNode(context);
/*  586:     */     }
/*  587:     */     
/*  588:     */     public NodeIterator getContextNodes()
/*  589:     */     {
/*  590:1112 */       return new DTMNodeIterator(XPathContext.this.getContextNodeList());
/*  591:     */     }
/*  592:     */     
/*  593:     */     public ErrorListener getErrorListener()
/*  594:     */     {
/*  595:1121 */       return XPathContext.this.getErrorListener();
/*  596:     */     }
/*  597:     */     
/*  598:     */     public double toNumber(Node n)
/*  599:     */     {
/*  600:1132 */       int nodeHandle = XPathContext.this.getDTMHandleFromNode(n);
/*  601:1133 */       DTM dtm = XPathContext.this.getDTM(nodeHandle);
/*  602:1134 */       XString xobj = (XString)dtm.getStringValue(nodeHandle);
/*  603:1135 */       return xobj.num();
/*  604:     */     }
/*  605:     */     
/*  606:     */     public String toString(Node n)
/*  607:     */     {
/*  608:1146 */       int nodeHandle = XPathContext.this.getDTMHandleFromNode(n);
/*  609:1147 */       DTM dtm = XPathContext.this.getDTM(nodeHandle);
/*  610:1148 */       XMLString strVal = dtm.getStringValue(nodeHandle);
/*  611:1149 */       return strVal.toString();
/*  612:     */     }
/*  613:     */     
/*  614:     */     public final XObject getVariableOrParam(QName qname)
/*  615:     */       throws TransformerException
/*  616:     */     {
/*  617:1162 */       return XPathContext.this.m_variableStacks.getVariableOrParam(XPathContext.this, qname);
/*  618:     */     }
/*  619:     */   }
/*  620:     */   
/*  621:     */   public DTM getGlobalRTFDTM()
/*  622:     */   {
/*  623:1198 */     if ((this.m_global_rtfdtm == null) || (this.m_global_rtfdtm.isTreeIncomplete())) {
/*  624:1200 */       this.m_global_rtfdtm = ((SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false));
/*  625:     */     }
/*  626:1202 */     return this.m_global_rtfdtm;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public DTM getRTFDTM()
/*  630:     */   {
/*  631:     */     SAX2RTFDTM rtfdtm;
/*  632:1231 */     if (this.m_rtfdtm_stack == null)
/*  633:     */     {
/*  634:1233 */       this.m_rtfdtm_stack = new Vector();
/*  635:1234 */       rtfdtm = (SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false);
/*  636:1235 */       this.m_rtfdtm_stack.addElement(rtfdtm);
/*  637:1236 */       this.m_which_rtfdtm += 1;
/*  638:     */     }
/*  639:1238 */     else if (this.m_which_rtfdtm < 0)
/*  640:     */     {
/*  641:1240 */       rtfdtm = (SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(++this.m_which_rtfdtm);
/*  642:     */     }
/*  643:     */     else
/*  644:     */     {
/*  645:1244 */       rtfdtm = (SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(this.m_which_rtfdtm);
/*  646:1252 */       if (rtfdtm.isTreeIncomplete()) {
/*  647:1254 */         if (++this.m_which_rtfdtm < this.m_rtfdtm_stack.size())
/*  648:     */         {
/*  649:1255 */           rtfdtm = (SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(this.m_which_rtfdtm);
/*  650:     */         }
/*  651:     */         else
/*  652:     */         {
/*  653:1258 */           rtfdtm = (SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false);
/*  654:1259 */           this.m_rtfdtm_stack.addElement(rtfdtm);
/*  655:     */         }
/*  656:     */       }
/*  657:     */     }
/*  658:1264 */     return rtfdtm;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public void pushRTFContext()
/*  662:     */   {
/*  663:1273 */     this.m_last_pushed_rtfdtm.push(this.m_which_rtfdtm);
/*  664:1274 */     if (null != this.m_rtfdtm_stack) {
/*  665:1275 */       ((SAX2RTFDTM)getRTFDTM()).pushRewindMark();
/*  666:     */     }
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void popRTFContext()
/*  670:     */   {
/*  671:1294 */     int previous = this.m_last_pushed_rtfdtm.pop();
/*  672:1295 */     if (null == this.m_rtfdtm_stack) {
/*  673:1296 */       return;
/*  674:     */     }
/*  675:1298 */     if (this.m_which_rtfdtm == previous)
/*  676:     */     {
/*  677:1300 */       if (previous >= 0) {
/*  678:1302 */         isEmpty = ((SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(previous)).popRewindMark();
/*  679:     */       }
/*  680:     */     }
/*  681:     */     else {
/*  682:1305 */       while (this.m_which_rtfdtm != previous)
/*  683:     */       {
/*  684:     */         boolean isEmpty;
/*  685:1310 */         boolean isEmpty = ((SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(this.m_which_rtfdtm)).popRewindMark();
/*  686:1311 */         this.m_which_rtfdtm -= 1;
/*  687:     */       }
/*  688:     */     }
/*  689:     */   }
/*  690:     */   
/*  691:     */   public DTMXRTreeFrag getDTMXRTreeFrag(int dtmIdentity)
/*  692:     */   {
/*  693:1323 */     if (this.m_DTMXRTreeFrags == null) {
/*  694:1324 */       this.m_DTMXRTreeFrags = new HashMap();
/*  695:     */     }
/*  696:1327 */     if (this.m_DTMXRTreeFrags.containsKey(new Integer(dtmIdentity))) {
/*  697:1328 */       return (DTMXRTreeFrag)this.m_DTMXRTreeFrags.get(new Integer(dtmIdentity));
/*  698:     */     }
/*  699:1330 */     DTMXRTreeFrag frag = new DTMXRTreeFrag(dtmIdentity, this);
/*  700:1331 */     this.m_DTMXRTreeFrags.put(new Integer(dtmIdentity), frag);
/*  701:1332 */     return frag;
/*  702:     */   }
/*  703:     */   
/*  704:     */   private final void releaseDTMXRTreeFrags()
/*  705:     */   {
/*  706:1341 */     if (this.m_DTMXRTreeFrags == null) {
/*  707:1342 */       return;
/*  708:     */     }
/*  709:1344 */     Iterator iter = this.m_DTMXRTreeFrags.values().iterator();
/*  710:1345 */     while (iter.hasNext())
/*  711:     */     {
/*  712:1346 */       DTMXRTreeFrag frag = (DTMXRTreeFrag)iter.next();
/*  713:1347 */       frag.destruct();
/*  714:1348 */       iter.remove();
/*  715:     */     }
/*  716:1350 */     this.m_DTMXRTreeFrags = null;
/*  717:     */   }
/*  718:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.XPathContext
 * JD-Core Version:    0.7.0.1
 */