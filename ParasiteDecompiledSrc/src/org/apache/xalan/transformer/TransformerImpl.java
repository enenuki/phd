/*    1:     */ package org.apache.xalan.transformer;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintStream;
/*    7:     */ import java.io.StringWriter;
/*    8:     */ import java.util.Enumeration;
/*    9:     */ import java.util.Hashtable;
/*   10:     */ import java.util.NoSuchElementException;
/*   11:     */ import java.util.Properties;
/*   12:     */ import java.util.Stack;
/*   13:     */ import java.util.StringTokenizer;
/*   14:     */ import java.util.Vector;
/*   15:     */ import javax.xml.parsers.DocumentBuilder;
/*   16:     */ import javax.xml.parsers.DocumentBuilderFactory;
/*   17:     */ import javax.xml.parsers.ParserConfigurationException;
/*   18:     */ import javax.xml.transform.ErrorListener;
/*   19:     */ import javax.xml.transform.Result;
/*   20:     */ import javax.xml.transform.Source;
/*   21:     */ import javax.xml.transform.SourceLocator;
/*   22:     */ import javax.xml.transform.Transformer;
/*   23:     */ import javax.xml.transform.TransformerException;
/*   24:     */ import javax.xml.transform.URIResolver;
/*   25:     */ import javax.xml.transform.dom.DOMResult;
/*   26:     */ import javax.xml.transform.dom.DOMSource;
/*   27:     */ import javax.xml.transform.sax.SAXResult;
/*   28:     */ import javax.xml.transform.sax.SAXSource;
/*   29:     */ import javax.xml.transform.stream.StreamResult;
/*   30:     */ import javax.xml.transform.stream.StreamSource;
/*   31:     */ import org.apache.xalan.extensions.ExtensionsTable;
/*   32:     */ import org.apache.xalan.res.XSLMessages;
/*   33:     */ import org.apache.xalan.templates.AVT;
/*   34:     */ import org.apache.xalan.templates.ElemAttributeSet;
/*   35:     */ import org.apache.xalan.templates.ElemForEach;
/*   36:     */ import org.apache.xalan.templates.ElemSort;
/*   37:     */ import org.apache.xalan.templates.ElemTemplate;
/*   38:     */ import org.apache.xalan.templates.ElemTemplateElement;
/*   39:     */ import org.apache.xalan.templates.ElemTextLiteral;
/*   40:     */ import org.apache.xalan.templates.ElemVariable;
/*   41:     */ import org.apache.xalan.templates.OutputProperties;
/*   42:     */ import org.apache.xalan.templates.Stylesheet;
/*   43:     */ import org.apache.xalan.templates.StylesheetComposed;
/*   44:     */ import org.apache.xalan.templates.StylesheetRoot;
/*   45:     */ import org.apache.xalan.templates.WhiteSpaceInfo;
/*   46:     */ import org.apache.xalan.templates.XUnresolvedVariable;
/*   47:     */ import org.apache.xalan.trace.GenerateEvent;
/*   48:     */ import org.apache.xalan.trace.TraceManager;
/*   49:     */ import org.apache.xml.dtm.DTM;
/*   50:     */ import org.apache.xml.dtm.DTMIterator;
/*   51:     */ import org.apache.xml.dtm.DTMManager;
/*   52:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   53:     */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   54:     */ import org.apache.xml.serializer.SerializationHandler;
/*   55:     */ import org.apache.xml.serializer.Serializer;
/*   56:     */ import org.apache.xml.serializer.SerializerBase;
/*   57:     */ import org.apache.xml.serializer.SerializerFactory;
/*   58:     */ import org.apache.xml.serializer.SerializerTrace;
/*   59:     */ import org.apache.xml.serializer.ToSAXHandler;
/*   60:     */ import org.apache.xml.serializer.ToTextStream;
/*   61:     */ import org.apache.xml.serializer.ToXMLSAXHandler;
/*   62:     */ import org.apache.xml.utils.BoolStack;
/*   63:     */ import org.apache.xml.utils.DOMBuilder;
/*   64:     */ import org.apache.xml.utils.DOMHelper;
/*   65:     */ import org.apache.xml.utils.DefaultErrorHandler;
/*   66:     */ import org.apache.xml.utils.NodeVector;
/*   67:     */ import org.apache.xml.utils.ObjectPool;
/*   68:     */ import org.apache.xml.utils.ObjectStack;
/*   69:     */ import org.apache.xml.utils.ObjectVector;
/*   70:     */ import org.apache.xml.utils.QName;
/*   71:     */ import org.apache.xml.utils.SAXSourceLocator;
/*   72:     */ import org.apache.xml.utils.ThreadControllerWrapper;
/*   73:     */ import org.apache.xml.utils.WrappedRuntimeException;
/*   74:     */ import org.apache.xpath.Arg;
/*   75:     */ import org.apache.xpath.ExtensionsProvider;
/*   76:     */ import org.apache.xpath.NodeSetDTM;
/*   77:     */ import org.apache.xpath.SourceTreeManager;
/*   78:     */ import org.apache.xpath.VariableStack;
/*   79:     */ import org.apache.xpath.XPathContext;
/*   80:     */ import org.apache.xpath.axes.SelfIteratorNoPredicate;
/*   81:     */ import org.apache.xpath.functions.FuncExtFunction;
/*   82:     */ import org.apache.xpath.objects.XObject;
/*   83:     */ import org.w3c.dom.Document;
/*   84:     */ import org.w3c.dom.DocumentFragment;
/*   85:     */ import org.w3c.dom.Node;
/*   86:     */ import org.xml.sax.Attributes;
/*   87:     */ import org.xml.sax.ContentHandler;
/*   88:     */ import org.xml.sax.ErrorHandler;
/*   89:     */ import org.xml.sax.SAXException;
/*   90:     */ import org.xml.sax.SAXNotRecognizedException;
/*   91:     */ import org.xml.sax.SAXNotSupportedException;
/*   92:     */ import org.xml.sax.SAXParseException;
/*   93:     */ import org.xml.sax.ext.DeclHandler;
/*   94:     */ import org.xml.sax.ext.LexicalHandler;
/*   95:     */ 
/*   96:     */ public class TransformerImpl
/*   97:     */   extends Transformer
/*   98:     */   implements Runnable, DTMWSFilter, ExtensionsProvider, SerializerTrace
/*   99:     */ {
/*  100: 115 */   private Boolean m_reentryGuard = new Boolean(true);
/*  101: 120 */   private FileOutputStream m_outputStream = null;
/*  102: 126 */   private boolean m_parserEventsOnMain = true;
/*  103:     */   private Thread m_transformThread;
/*  104: 132 */   private String m_urlOfSource = null;
/*  105: 135 */   private Result m_outputTarget = null;
/*  106:     */   private OutputProperties m_outputFormat;
/*  107:     */   ContentHandler m_inputContentHandler;
/*  108: 151 */   private ContentHandler m_outputContentHandler = null;
/*  109: 161 */   DocumentBuilder m_docBuilder = null;
/*  110: 167 */   private ObjectPool m_textResultHandlerObjectPool = new ObjectPool(ToTextStream.class);
/*  111: 175 */   private ObjectPool m_stringWriterObjectPool = new ObjectPool(StringWriter.class);
/*  112: 182 */   private OutputProperties m_textformat = new OutputProperties("text");
/*  113: 203 */   ObjectStack m_currentTemplateElements = new ObjectStack(4096);
/*  114: 216 */   Stack m_currentMatchTemplates = new Stack();
/*  115: 226 */   NodeVector m_currentMatchedNodes = new NodeVector();
/*  116: 231 */   private StylesheetRoot m_stylesheetRoot = null;
/*  117: 237 */   private boolean m_quietConflictWarnings = true;
/*  118:     */   private XPathContext m_xcontext;
/*  119:     */   private StackGuard m_stackGuard;
/*  120:     */   private SerializationHandler m_serializationHandler;
/*  121: 258 */   private KeyManager m_keyManager = new KeyManager();
/*  122: 264 */   Stack m_attrSetStack = null;
/*  123: 270 */   CountersTable m_countersTable = null;
/*  124: 275 */   BoolStack m_currentTemplateRuleIsNull = new BoolStack();
/*  125: 282 */   ObjectStack m_currentFuncResult = new ObjectStack();
/*  126:     */   private MsgMgr m_msgMgr;
/*  127: 296 */   private boolean m_optimizer = true;
/*  128: 304 */   private boolean m_incremental = false;
/*  129: 312 */   private boolean m_source_location = false;
/*  130: 318 */   private boolean m_debug = false;
/*  131: 323 */   private ErrorListener m_errorHandler = new DefaultErrorHandler(false);
/*  132: 329 */   private TraceManager m_traceManager = new TraceManager(this);
/*  133: 336 */   private Exception m_exceptionThrown = null;
/*  134:     */   private Source m_xmlSource;
/*  135:     */   private int m_doc;
/*  136: 357 */   private boolean m_isTransformDone = false;
/*  137: 360 */   private boolean m_hasBeenReset = false;
/*  138: 363 */   private boolean m_shouldReset = true;
/*  139:     */   
/*  140:     */   public void setShouldReset(boolean shouldReset)
/*  141:     */   {
/*  142: 373 */     this.m_shouldReset = shouldReset;
/*  143:     */   }
/*  144:     */   
/*  145: 379 */   private Stack m_modes = new Stack();
/*  146:     */   
/*  147:     */   public TransformerImpl(StylesheetRoot stylesheet)
/*  148:     */   {
/*  149: 393 */     this.m_optimizer = stylesheet.getOptimizer();
/*  150: 394 */     this.m_incremental = stylesheet.getIncremental();
/*  151: 395 */     this.m_source_location = stylesheet.getSource_location();
/*  152: 396 */     setStylesheet(stylesheet);
/*  153: 397 */     XPathContext xPath = new XPathContext(this);
/*  154: 398 */     xPath.setIncremental(this.m_incremental);
/*  155: 399 */     xPath.getDTMManager().setIncremental(this.m_incremental);
/*  156: 400 */     xPath.setSource_location(this.m_source_location);
/*  157: 401 */     xPath.getDTMManager().setSource_location(this.m_source_location);
/*  158: 403 */     if (stylesheet.isSecureProcessing()) {
/*  159: 404 */       xPath.setSecureProcessing(true);
/*  160:     */     }
/*  161: 406 */     setXPathContext(xPath);
/*  162: 407 */     getXPathContext().setNamespaceContext(stylesheet);
/*  163: 408 */     this.m_stackGuard = new StackGuard(this);
/*  164:     */   }
/*  165:     */   
/*  166: 416 */   private ExtensionsTable m_extensionsTable = null;
/*  167:     */   
/*  168:     */   public ExtensionsTable getExtensionsTable()
/*  169:     */   {
/*  170: 425 */     return this.m_extensionsTable;
/*  171:     */   }
/*  172:     */   
/*  173:     */   void setExtensionsTable(StylesheetRoot sroot)
/*  174:     */     throws TransformerException
/*  175:     */   {
/*  176:     */     try
/*  177:     */     {
/*  178: 440 */       if (sroot.getExtensions() != null) {
/*  179: 441 */         this.m_extensionsTable = new ExtensionsTable(sroot);
/*  180:     */       }
/*  181:     */     }
/*  182:     */     catch (TransformerException te)
/*  183:     */     {
/*  184: 444 */       te.printStackTrace();
/*  185:     */     }
/*  186:     */   }
/*  187:     */   
/*  188:     */   public boolean functionAvailable(String ns, String funcName)
/*  189:     */     throws TransformerException
/*  190:     */   {
/*  191: 452 */     return getExtensionsTable().functionAvailable(ns, funcName);
/*  192:     */   }
/*  193:     */   
/*  194:     */   public boolean elementAvailable(String ns, String elemName)
/*  195:     */     throws TransformerException
/*  196:     */   {
/*  197: 458 */     return getExtensionsTable().elementAvailable(ns, elemName);
/*  198:     */   }
/*  199:     */   
/*  200:     */   public Object extFunction(String ns, String funcName, Vector argVec, Object methodKey)
/*  201:     */     throws TransformerException
/*  202:     */   {
/*  203: 465 */     return getExtensionsTable().extFunction(ns, funcName, argVec, methodKey, getXPathContext().getExpressionContext());
/*  204:     */   }
/*  205:     */   
/*  206:     */   public Object extFunction(FuncExtFunction extFunction, Vector argVec)
/*  207:     */     throws TransformerException
/*  208:     */   {
/*  209: 473 */     return getExtensionsTable().extFunction(extFunction, argVec, getXPathContext().getExpressionContext());
/*  210:     */   }
/*  211:     */   
/*  212:     */   public void reset()
/*  213:     */   {
/*  214: 486 */     if ((!this.m_hasBeenReset) && (this.m_shouldReset))
/*  215:     */     {
/*  216: 488 */       this.m_hasBeenReset = true;
/*  217: 490 */       if (this.m_outputStream != null) {
/*  218:     */         try
/*  219:     */         {
/*  220: 494 */           this.m_outputStream.close();
/*  221:     */         }
/*  222:     */         catch (IOException ioe) {}
/*  223:     */       }
/*  224: 499 */       this.m_outputStream = null;
/*  225:     */       
/*  226:     */ 
/*  227:     */ 
/*  228: 503 */       this.m_countersTable = null;
/*  229:     */       
/*  230: 505 */       this.m_xcontext.reset();
/*  231:     */       
/*  232: 507 */       this.m_xcontext.getVarStack().reset();
/*  233: 508 */       resetUserParameters();
/*  234:     */       
/*  235:     */ 
/*  236: 511 */       this.m_currentTemplateElements.removeAllElements();
/*  237: 512 */       this.m_currentMatchTemplates.removeAllElements();
/*  238: 513 */       this.m_currentMatchedNodes.removeAllElements();
/*  239:     */       
/*  240: 515 */       this.m_serializationHandler = null;
/*  241: 516 */       this.m_outputTarget = null;
/*  242: 517 */       this.m_keyManager = new KeyManager();
/*  243: 518 */       this.m_attrSetStack = null;
/*  244: 519 */       this.m_countersTable = null;
/*  245: 520 */       this.m_currentTemplateRuleIsNull = new BoolStack();
/*  246: 521 */       this.m_xmlSource = null;
/*  247: 522 */       this.m_doc = -1;
/*  248: 523 */       this.m_isTransformDone = false;
/*  249: 524 */       this.m_transformThread = null;
/*  250:     */       
/*  251:     */ 
/*  252:     */ 
/*  253: 528 */       this.m_xcontext.getSourceTreeManager().reset();
/*  254:     */     }
/*  255:     */   }
/*  256:     */   
/*  257:     */   public boolean getProperty(String property)
/*  258:     */   {
/*  259: 545 */     return false;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public void setProperty(String property, Object value) {}
/*  263:     */   
/*  264:     */   public boolean isParserEventsOnMain()
/*  265:     */   {
/*  266: 572 */     return this.m_parserEventsOnMain;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public Thread getTransformThread()
/*  270:     */   {
/*  271: 583 */     return this.m_transformThread;
/*  272:     */   }
/*  273:     */   
/*  274:     */   public void setTransformThread(Thread t)
/*  275:     */   {
/*  276: 594 */     this.m_transformThread = t;
/*  277:     */   }
/*  278:     */   
/*  279: 598 */   private boolean m_hasTransformThreadErrorCatcher = false;
/*  280:     */   Vector m_userParams;
/*  281:     */   
/*  282:     */   public boolean hasTransformThreadErrorCatcher()
/*  283:     */   {
/*  284: 608 */     return this.m_hasTransformThreadErrorCatcher;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void transform(Source source)
/*  288:     */     throws TransformerException
/*  289:     */   {
/*  290: 619 */     transform(source, true);
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void transform(Source source, boolean shouldRelease)
/*  294:     */     throws TransformerException
/*  295:     */   {
/*  296:     */     try
/*  297:     */     {
/*  298: 639 */       if (getXPathContext().getNamespaceContext() == null) {
/*  299: 640 */         getXPathContext().setNamespaceContext(getStylesheet());
/*  300:     */       }
/*  301: 642 */       String base = source.getSystemId();
/*  302: 645 */       if (null == base) {
/*  303: 647 */         base = this.m_stylesheetRoot.getBaseIdentifier();
/*  304:     */       }
/*  305: 651 */       if (null == base)
/*  306:     */       {
/*  307: 653 */         String currentDir = "";
/*  308:     */         try
/*  309:     */         {
/*  310: 655 */           currentDir = System.getProperty("user.dir");
/*  311:     */         }
/*  312:     */         catch (SecurityException se) {}
/*  313: 659 */         if (currentDir.startsWith(File.separator)) {
/*  314: 660 */           base = "file://" + currentDir;
/*  315:     */         } else {
/*  316: 662 */           base = "file:///" + currentDir;
/*  317:     */         }
/*  318: 664 */         base = base + File.separatorChar + source.getClass().getName();
/*  319:     */       }
/*  320: 667 */       setBaseURLOfSource(base);
/*  321: 668 */       DTMManager mgr = this.m_xcontext.getDTMManager();
/*  322: 677 */       if ((((source instanceof StreamSource)) && (source.getSystemId() == null) && (((StreamSource)source).getInputStream() == null) && (((StreamSource)source).getReader() == null)) || (((source instanceof SAXSource)) && (((SAXSource)source).getInputSource() == null) && (((SAXSource)source).getXMLReader() == null)) || (((source instanceof DOMSource)) && (((DOMSource)source).getNode() == null))) {
/*  323:     */         try
/*  324:     */         {
/*  325: 685 */           DocumentBuilderFactory builderF = DocumentBuilderFactory.newInstance();
/*  326:     */           
/*  327: 687 */           DocumentBuilder builder = builderF.newDocumentBuilder();
/*  328: 688 */           String systemID = source.getSystemId();
/*  329: 689 */           source = new DOMSource(builder.newDocument());
/*  330: 692 */           if (systemID != null) {
/*  331: 693 */             source.setSystemId(systemID);
/*  332:     */           }
/*  333:     */         }
/*  334:     */         catch (ParserConfigurationException e)
/*  335:     */         {
/*  336: 696 */           fatalError(e);
/*  337:     */         }
/*  338:     */       }
/*  339: 699 */       DTM dtm = mgr.getDTM(source, false, this, true, true);
/*  340: 700 */       dtm.setDocumentBaseURI(base);
/*  341:     */       
/*  342: 702 */       boolean hardDelete = true;
/*  343:     */       try
/*  344:     */       {
/*  345: 709 */         transformNode(dtm.getDocument());
/*  346:     */       }
/*  347:     */       finally
/*  348:     */       {
/*  349: 713 */         if (shouldRelease) {
/*  350: 714 */           mgr.release(dtm, hardDelete);
/*  351:     */         }
/*  352:     */       }
/*  353: 722 */       Exception e = getExceptionThrown();
/*  354: 724 */       if (null != e)
/*  355:     */       {
/*  356: 726 */         if ((e instanceof TransformerException)) {
/*  357: 728 */           throw ((TransformerException)e);
/*  358:     */         }
/*  359: 730 */         if ((e instanceof WrappedRuntimeException)) {
/*  360: 732 */           fatalError(((WrappedRuntimeException)e).getException());
/*  361:     */         } else {
/*  362: 737 */           throw new TransformerException(e);
/*  363:     */         }
/*  364:     */       }
/*  365: 740 */       else if (null != this.m_serializationHandler)
/*  366:     */       {
/*  367: 742 */         this.m_serializationHandler.endDocument();
/*  368:     */       }
/*  369:     */     }
/*  370:     */     catch (WrappedRuntimeException wre)
/*  371:     */     {
/*  372: 747 */       Throwable throwable = wre.getException();
/*  373: 750 */       while ((throwable instanceof WrappedRuntimeException)) {
/*  374: 752 */         throwable = ((WrappedRuntimeException)throwable).getException();
/*  375:     */       }
/*  376: 756 */       fatalError(throwable);
/*  377:     */     }
/*  378:     */     catch (SAXParseException spe)
/*  379:     */     {
/*  380: 762 */       fatalError(spe);
/*  381:     */     }
/*  382:     */     catch (SAXException se)
/*  383:     */     {
/*  384: 766 */       this.m_errorHandler.fatalError(new TransformerException(se));
/*  385:     */     }
/*  386:     */     finally
/*  387:     */     {
/*  388: 770 */       this.m_hasTransformThreadErrorCatcher = false;
/*  389:     */       
/*  390:     */ 
/*  391: 773 */       reset();
/*  392:     */     }
/*  393:     */   }
/*  394:     */   
/*  395:     */   private void fatalError(Throwable throwable)
/*  396:     */     throws TransformerException
/*  397:     */   {
/*  398: 779 */     if ((throwable instanceof SAXParseException)) {
/*  399: 780 */       this.m_errorHandler.fatalError(new TransformerException(throwable.getMessage(), new SAXSourceLocator((SAXParseException)throwable)));
/*  400:     */     } else {
/*  401: 782 */       this.m_errorHandler.fatalError(new TransformerException(throwable));
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   public String getBaseURLOfSource()
/*  406:     */   {
/*  407: 793 */     return this.m_urlOfSource;
/*  408:     */   }
/*  409:     */   
/*  410:     */   public void setBaseURLOfSource(String base)
/*  411:     */   {
/*  412: 805 */     this.m_urlOfSource = base;
/*  413:     */   }
/*  414:     */   
/*  415:     */   public Result getOutputTarget()
/*  416:     */   {
/*  417: 815 */     return this.m_outputTarget;
/*  418:     */   }
/*  419:     */   
/*  420:     */   public void setOutputTarget(Result outputTarget)
/*  421:     */   {
/*  422: 828 */     this.m_outputTarget = outputTarget;
/*  423:     */   }
/*  424:     */   
/*  425:     */   public String getOutputProperty(String qnameString)
/*  426:     */     throws IllegalArgumentException
/*  427:     */   {
/*  428: 850 */     String value = null;
/*  429: 851 */     OutputProperties props = getOutputFormat();
/*  430:     */     
/*  431: 853 */     value = props.getProperty(qnameString);
/*  432: 855 */     if (null == value) {
/*  433: 857 */       if (!OutputProperties.isLegalPropertyKey(qnameString)) {
/*  434: 858 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { qnameString }));
/*  435:     */       }
/*  436:     */     }
/*  437: 862 */     return value;
/*  438:     */   }
/*  439:     */   
/*  440:     */   public String getOutputPropertyNoDefault(String qnameString)
/*  441:     */     throws IllegalArgumentException
/*  442:     */   {
/*  443: 881 */     String value = null;
/*  444: 882 */     OutputProperties props = getOutputFormat();
/*  445:     */     
/*  446: 884 */     value = (String)props.getProperties().get(qnameString);
/*  447: 886 */     if (null == value) {
/*  448: 888 */       if (!OutputProperties.isLegalPropertyKey(qnameString)) {
/*  449: 889 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { qnameString }));
/*  450:     */       }
/*  451:     */     }
/*  452: 893 */     return value;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public void setOutputProperty(String name, String value)
/*  456:     */     throws IllegalArgumentException
/*  457:     */   {
/*  458: 959 */     synchronized (this.m_reentryGuard)
/*  459:     */     {
/*  460: 964 */       if (null == this.m_outputFormat) {
/*  461: 966 */         this.m_outputFormat = ((OutputProperties)getStylesheet().getOutputComposed().clone());
/*  462:     */       }
/*  463: 970 */       if (!OutputProperties.isLegalPropertyKey(name)) {
/*  464: 971 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { name }));
/*  465:     */       }
/*  466: 974 */       this.m_outputFormat.setProperty(name, value);
/*  467:     */     }
/*  468:     */   }
/*  469:     */   
/*  470:     */   public void setOutputProperties(Properties oformat)
/*  471:     */     throws IllegalArgumentException
/*  472:     */   {
/*  473:1000 */     synchronized (this.m_reentryGuard)
/*  474:     */     {
/*  475:1002 */       if (null != oformat)
/*  476:     */       {
/*  477:1006 */         String method = (String)oformat.get("method");
/*  478:1008 */         if (null != method) {
/*  479:1009 */           this.m_outputFormat = new OutputProperties(method);
/*  480:1010 */         } else if (this.m_outputFormat == null) {
/*  481:1011 */           this.m_outputFormat = new OutputProperties();
/*  482:     */         }
/*  483:1013 */         this.m_outputFormat.copyFrom(oformat);
/*  484:     */         
/*  485:     */ 
/*  486:     */ 
/*  487:1017 */         this.m_outputFormat.copyFrom(this.m_stylesheetRoot.getOutputProperties());
/*  488:     */       }
/*  489:     */       else
/*  490:     */       {
/*  491:1022 */         this.m_outputFormat = null;
/*  492:     */       }
/*  493:     */     }
/*  494:     */   }
/*  495:     */   
/*  496:     */   public Properties getOutputProperties()
/*  497:     */   {
/*  498:1042 */     return (Properties)getOutputFormat().getProperties().clone();
/*  499:     */   }
/*  500:     */   
/*  501:     */   public SerializationHandler createSerializationHandler(Result outputTarget)
/*  502:     */     throws TransformerException
/*  503:     */   {
/*  504:1060 */     SerializationHandler xoh = createSerializationHandler(outputTarget, getOutputFormat());
/*  505:     */     
/*  506:1062 */     return xoh;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public SerializationHandler createSerializationHandler(Result outputTarget, OutputProperties format)
/*  510:     */     throws TransformerException
/*  511:     */   {
/*  512:1087 */     Node outputNode = null;
/*  513:     */     SerializationHandler xoh;
/*  514:1089 */     if ((outputTarget instanceof DOMResult))
/*  515:     */     {
/*  516:1091 */       outputNode = ((DOMResult)outputTarget).getNode();
/*  517:1092 */       Node nextSibling = ((DOMResult)outputTarget).getNextSibling();
/*  518:     */       short type;
/*  519:     */       Document doc;
/*  520:1097 */       if (null != outputNode)
/*  521:     */       {
/*  522:1099 */         type = outputNode.getNodeType();
/*  523:1100 */         doc = 9 == type ? (Document)outputNode : outputNode.getOwnerDocument();
/*  524:     */       }
/*  525:     */       else
/*  526:     */       {
/*  527:1106 */         boolean isSecureProcessing = this.m_stylesheetRoot.isSecureProcessing();
/*  528:1107 */         doc = DOMHelper.createDocument(isSecureProcessing);
/*  529:1108 */         outputNode = doc;
/*  530:1109 */         type = outputNode.getNodeType();
/*  531:     */         
/*  532:1111 */         ((DOMResult)outputTarget).setNode(outputNode);
/*  533:     */       }
/*  534:1114 */       DOMBuilder handler = 11 == type ? new DOMBuilder(doc, (DocumentFragment)outputNode) : new DOMBuilder(doc, outputNode);
/*  535:1119 */       if (nextSibling != null) {
/*  536:1120 */         handler.setNextSibling(nextSibling);
/*  537:     */       }
/*  538:1122 */       String encoding = format.getProperty("encoding");
/*  539:1123 */       xoh = new ToXMLSAXHandler(handler, handler, encoding);
/*  540:     */     }
/*  541:1125 */     else if ((outputTarget instanceof SAXResult))
/*  542:     */     {
/*  543:1127 */       ContentHandler handler = ((SAXResult)outputTarget).getHandler();
/*  544:1129 */       if (null == handler) {
/*  545:1130 */         throw new IllegalArgumentException("handler can not be null for a SAXResult");
/*  546:     */       }
/*  547:     */       LexicalHandler lexHandler;
/*  548:1134 */       if ((handler instanceof LexicalHandler)) {
/*  549:1135 */         lexHandler = (LexicalHandler)handler;
/*  550:     */       } else {
/*  551:1137 */         lexHandler = null;
/*  552:     */       }
/*  553:1139 */       String encoding = format.getProperty("encoding");
/*  554:1140 */       String method = format.getProperty("method");
/*  555:     */       
/*  556:1142 */       ToXMLSAXHandler toXMLSAXHandler = new ToXMLSAXHandler(handler, lexHandler, encoding);
/*  557:1143 */       toXMLSAXHandler.setShouldOutputNSAttr(false);
/*  558:1144 */       xoh = toXMLSAXHandler;
/*  559:     */       
/*  560:     */ 
/*  561:1147 */       String publicID = format.getProperty("doctype-public");
/*  562:1148 */       String systemID = format.getProperty("doctype-system");
/*  563:1149 */       if (systemID != null) {
/*  564:1150 */         xoh.setDoctypeSystem(systemID);
/*  565:     */       }
/*  566:1151 */       if (publicID != null) {
/*  567:1152 */         xoh.setDoctypePublic(publicID);
/*  568:     */       }
/*  569:1154 */       if ((handler instanceof TransformerClient))
/*  570:     */       {
/*  571:1155 */         XalanTransformState state = new XalanTransformState();
/*  572:1156 */         ((TransformerClient)handler).setTransformState(state);
/*  573:1157 */         ((ToSAXHandler)xoh).setTransformState(state);
/*  574:     */       }
/*  575:     */     }
/*  576:1165 */     else if ((outputTarget instanceof StreamResult))
/*  577:     */     {
/*  578:1167 */       StreamResult sresult = (StreamResult)outputTarget;
/*  579:     */       try
/*  580:     */       {
/*  581:1171 */         SerializationHandler serializer = (SerializationHandler)SerializerFactory.getSerializer(format.getProperties());
/*  582:1174 */         if (null != sresult.getWriter())
/*  583:     */         {
/*  584:1175 */           serializer.setWriter(sresult.getWriter());
/*  585:     */         }
/*  586:1176 */         else if (null != sresult.getOutputStream())
/*  587:     */         {
/*  588:1177 */           serializer.setOutputStream(sresult.getOutputStream());
/*  589:     */         }
/*  590:1178 */         else if (null != sresult.getSystemId())
/*  591:     */         {
/*  592:1180 */           String fileURL = sresult.getSystemId();
/*  593:1182 */           if (fileURL.startsWith("file:///"))
/*  594:     */           {
/*  595:1184 */             if (fileURL.substring(8).indexOf(":") > 0) {
/*  596:1185 */               fileURL = fileURL.substring(8);
/*  597:     */             } else {
/*  598:1187 */               fileURL = fileURL.substring(7);
/*  599:     */             }
/*  600:     */           }
/*  601:1189 */           else if (fileURL.startsWith("file:/")) {
/*  602:1191 */             if (fileURL.substring(6).indexOf(":") > 0) {
/*  603:1192 */               fileURL = fileURL.substring(6);
/*  604:     */             } else {
/*  605:1194 */               fileURL = fileURL.substring(5);
/*  606:     */             }
/*  607:     */           }
/*  608:1197 */           this.m_outputStream = new FileOutputStream(fileURL);
/*  609:     */           
/*  610:1199 */           serializer.setOutputStream(this.m_outputStream);
/*  611:     */           
/*  612:1201 */           xoh = serializer;
/*  613:     */         }
/*  614:     */         else
/*  615:     */         {
/*  616:1204 */           throw new TransformerException(XSLMessages.createMessage("ER_NO_OUTPUT_SPECIFIED", null));
/*  617:     */         }
/*  618:1210 */         xoh = serializer;
/*  619:     */       }
/*  620:     */       catch (IOException ioe)
/*  621:     */       {
/*  622:1218 */         throw new TransformerException(ioe);
/*  623:     */       }
/*  624:     */     }
/*  625:     */     else
/*  626:     */     {
/*  627:1223 */       throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", new Object[] { outputTarget.getClass().getName() }));
/*  628:     */     }
/*  629:1230 */     xoh.setTransformer(this);
/*  630:     */     
/*  631:1232 */     SourceLocator srcLocator = getStylesheet();
/*  632:1233 */     xoh.setSourceLocator(srcLocator);
/*  633:     */     
/*  634:     */ 
/*  635:1236 */     return xoh;
/*  636:     */   }
/*  637:     */   
/*  638:     */   public void transform(Source xmlSource, Result outputTarget)
/*  639:     */     throws TransformerException
/*  640:     */   {
/*  641:1251 */     transform(xmlSource, outputTarget, true);
/*  642:     */   }
/*  643:     */   
/*  644:     */   public void transform(Source xmlSource, Result outputTarget, boolean shouldRelease)
/*  645:     */     throws TransformerException
/*  646:     */   {
/*  647:1266 */     synchronized (this.m_reentryGuard)
/*  648:     */     {
/*  649:1268 */       SerializationHandler xoh = createSerializationHandler(outputTarget);
/*  650:1269 */       setSerializationHandler(xoh);
/*  651:     */       
/*  652:1271 */       this.m_outputTarget = outputTarget;
/*  653:     */       
/*  654:1273 */       transform(xmlSource, shouldRelease);
/*  655:     */     }
/*  656:     */   }
/*  657:     */   
/*  658:     */   public void transformNode(int node, Result outputTarget)
/*  659:     */     throws TransformerException
/*  660:     */   {
/*  661:1292 */     SerializationHandler xoh = createSerializationHandler(outputTarget);
/*  662:1293 */     setSerializationHandler(xoh);
/*  663:     */     
/*  664:1295 */     this.m_outputTarget = outputTarget;
/*  665:     */     
/*  666:1297 */     transformNode(node);
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void transformNode(int node)
/*  670:     */     throws TransformerException
/*  671:     */   {
/*  672:1312 */     setExtensionsTable(getStylesheet());
/*  673:1314 */     synchronized (this.m_serializationHandler)
/*  674:     */     {
/*  675:1316 */       this.m_hasBeenReset = false;
/*  676:     */       
/*  677:1318 */       XPathContext xctxt = getXPathContext();
/*  678:1319 */       DTM dtm = xctxt.getDTM(node);
/*  679:     */       try
/*  680:     */       {
/*  681:1323 */         pushGlobalVars(node);
/*  682:     */         
/*  683:     */ 
/*  684:     */ 
/*  685:     */ 
/*  686:1328 */         StylesheetRoot stylesheet = getStylesheet();
/*  687:1329 */         int n = stylesheet.getGlobalImportCount();
/*  688:1331 */         for (int i = 0; i < n; i++)
/*  689:     */         {
/*  690:1333 */           StylesheetComposed imported = stylesheet.getGlobalImport(i);
/*  691:1334 */           int includedCount = imported.getIncludeCountComposed();
/*  692:1336 */           for (int j = -1; j < includedCount; j++)
/*  693:     */           {
/*  694:1338 */             Stylesheet included = imported.getIncludeComposed(j);
/*  695:     */             
/*  696:1340 */             included.runtimeInit(this);
/*  697:1342 */             for (ElemTemplateElement child = included.getFirstChildElem(); child != null; child = child.getNextSiblingElem()) {
/*  698:1345 */               child.runtimeInit(this);
/*  699:     */             }
/*  700:     */           }
/*  701:     */         }
/*  702:1351 */         DTMIterator dtmIter = new SelfIteratorNoPredicate();
/*  703:1352 */         dtmIter.setRoot(node, xctxt);
/*  704:1353 */         xctxt.pushContextNodeList(dtmIter);
/*  705:     */         try
/*  706:     */         {
/*  707:1356 */           applyTemplateToNode(null, null, node);
/*  708:     */         }
/*  709:     */         finally
/*  710:     */         {
/*  711:1360 */           xctxt.popContextNodeList();
/*  712:     */         }
/*  713:1365 */         if (null != this.m_serializationHandler) {
/*  714:1367 */           this.m_serializationHandler.endDocument();
/*  715:     */         }
/*  716:     */       }
/*  717:     */       catch (Exception se)
/*  718:     */       {
/*  719:1380 */         while ((se instanceof WrappedRuntimeException))
/*  720:     */         {
/*  721:1382 */           Exception e = ((WrappedRuntimeException)se).getException();
/*  722:1383 */           if (null != e) {
/*  723:1384 */             se = e;
/*  724:     */           }
/*  725:     */         }
/*  726:1387 */         if (null != this.m_serializationHandler) {
/*  727:     */           try
/*  728:     */           {
/*  729:1391 */             if ((se instanceof SAXParseException))
/*  730:     */             {
/*  731:1392 */               this.m_serializationHandler.fatalError((SAXParseException)se);
/*  732:     */             }
/*  733:1393 */             else if ((se instanceof TransformerException))
/*  734:     */             {
/*  735:1395 */               TransformerException te = (TransformerException)se;
/*  736:1396 */               SAXSourceLocator sl = new SAXSourceLocator(te.getLocator());
/*  737:1397 */               this.m_serializationHandler.fatalError(new SAXParseException(te.getMessage(), sl, te));
/*  738:     */             }
/*  739:     */             else
/*  740:     */             {
/*  741:1401 */               this.m_serializationHandler.fatalError(new SAXParseException(se.getMessage(), new SAXSourceLocator(), se));
/*  742:     */             }
/*  743:     */           }
/*  744:     */           catch (Exception e) {}
/*  745:     */         }
/*  746:1407 */         if ((se instanceof TransformerException)) {
/*  747:1409 */           this.m_errorHandler.fatalError((TransformerException)se);
/*  748:1411 */         } else if ((se instanceof SAXParseException)) {
/*  749:1413 */           this.m_errorHandler.fatalError(new TransformerException(se.getMessage(), new SAXSourceLocator((SAXParseException)se), se));
/*  750:     */         } else {
/*  751:1419 */           this.m_errorHandler.fatalError(new TransformerException(se));
/*  752:     */         }
/*  753:     */       }
/*  754:     */       finally
/*  755:     */       {
/*  756:1425 */         reset();
/*  757:     */       }
/*  758:     */     }
/*  759:     */   }
/*  760:     */   
/*  761:     */   public ContentHandler getInputContentHandler()
/*  762:     */   {
/*  763:1439 */     return getInputContentHandler(false);
/*  764:     */   }
/*  765:     */   
/*  766:     */   public ContentHandler getInputContentHandler(boolean doDocFrag)
/*  767:     */   {
/*  768:1455 */     if (null == this.m_inputContentHandler) {
/*  769:1460 */       this.m_inputContentHandler = new TransformerHandlerImpl(this, doDocFrag, this.m_urlOfSource);
/*  770:     */     }
/*  771:1464 */     return this.m_inputContentHandler;
/*  772:     */   }
/*  773:     */   
/*  774:     */   public DeclHandler getInputDeclHandler()
/*  775:     */   {
/*  776:1476 */     if ((this.m_inputContentHandler instanceof DeclHandler)) {
/*  777:1477 */       return (DeclHandler)this.m_inputContentHandler;
/*  778:     */     }
/*  779:1479 */     return null;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public LexicalHandler getInputLexicalHandler()
/*  783:     */   {
/*  784:1491 */     if ((this.m_inputContentHandler instanceof LexicalHandler)) {
/*  785:1492 */       return (LexicalHandler)this.m_inputContentHandler;
/*  786:     */     }
/*  787:1494 */     return null;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void setOutputFormat(OutputProperties oformat)
/*  791:     */   {
/*  792:1507 */     this.m_outputFormat = oformat;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public OutputProperties getOutputFormat()
/*  796:     */   {
/*  797:1521 */     OutputProperties format = null == this.m_outputFormat ? getStylesheet().getOutputComposed() : this.m_outputFormat;
/*  798:     */     
/*  799:     */ 
/*  800:     */ 
/*  801:1525 */     return format;
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void setParameter(String name, String namespace, Object value)
/*  805:     */   {
/*  806:1541 */     VariableStack varstack = getXPathContext().getVarStack();
/*  807:1542 */     QName qname = new QName(namespace, name);
/*  808:1543 */     XObject xobject = XObject.create(value, getXPathContext());
/*  809:     */     
/*  810:1545 */     StylesheetRoot sroot = this.m_stylesheetRoot;
/*  811:1546 */     Vector vars = sroot.getVariablesAndParamsComposed();
/*  812:1547 */     int i = vars.size();
/*  813:     */     do
/*  814:     */     {
/*  815:1550 */       ElemVariable variable = (ElemVariable)vars.elementAt(i);
/*  816:1551 */       if ((variable.getXSLToken() == 41) && (variable.getName().equals(qname))) {
/*  817:1554 */         varstack.setGlobalVariable(i, xobject);
/*  818:     */       }
/*  819:1548 */       i--;
/*  820:1548 */     } while (i >= 0);
/*  821:     */   }
/*  822:     */   
/*  823:     */   public void setParameter(String name, Object value)
/*  824:     */   {
/*  825:1575 */     if (value == null) {
/*  826:1576 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_INVALID_SET_PARAM_VALUE", new Object[] { name }));
/*  827:     */     }
/*  828:1579 */     StringTokenizer tokenizer = new StringTokenizer(name, "{}", false);
/*  829:     */     try
/*  830:     */     {
/*  831:1586 */       String s1 = tokenizer.nextToken();
/*  832:1587 */       String s2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
/*  833:1589 */       if (null == this.m_userParams) {
/*  834:1590 */         this.m_userParams = new Vector();
/*  835:     */       }
/*  836:1592 */       if (null == s2)
/*  837:     */       {
/*  838:1594 */         replaceOrPushUserParam(new QName(s1), XObject.create(value, getXPathContext()));
/*  839:1595 */         setParameter(s1, null, value);
/*  840:     */       }
/*  841:     */       else
/*  842:     */       {
/*  843:1599 */         replaceOrPushUserParam(new QName(s1, s2), XObject.create(value, getXPathContext()));
/*  844:1600 */         setParameter(s2, s1, value);
/*  845:     */       }
/*  846:     */     }
/*  847:     */     catch (NoSuchElementException nsee) {}
/*  848:     */   }
/*  849:     */   
/*  850:     */   private void replaceOrPushUserParam(QName qname, XObject xval)
/*  851:     */   {
/*  852:1620 */     int n = this.m_userParams.size();
/*  853:1622 */     for (int i = n - 1; i >= 0; i--)
/*  854:     */     {
/*  855:1624 */       Arg arg = (Arg)this.m_userParams.elementAt(i);
/*  856:1626 */       if (arg.getQName().equals(qname))
/*  857:     */       {
/*  858:1628 */         this.m_userParams.setElementAt(new Arg(qname, xval, true), i);
/*  859:     */         
/*  860:1630 */         return;
/*  861:     */       }
/*  862:     */     }
/*  863:1634 */     this.m_userParams.addElement(new Arg(qname, xval, true));
/*  864:     */   }
/*  865:     */   
/*  866:     */   public Object getParameter(String name)
/*  867:     */   {
/*  868:     */     try
/*  869:     */     {
/*  870:1657 */       QName qname = QName.getQNameFromString(name);
/*  871:1659 */       if (null == this.m_userParams) {
/*  872:1660 */         return null;
/*  873:     */       }
/*  874:1662 */       int n = this.m_userParams.size();
/*  875:1664 */       for (int i = n - 1; i >= 0; i--)
/*  876:     */       {
/*  877:1666 */         Arg arg = (Arg)this.m_userParams.elementAt(i);
/*  878:1668 */         if (arg.getQName().equals(qname)) {
/*  879:1670 */           return arg.getVal().object();
/*  880:     */         }
/*  881:     */       }
/*  882:1674 */       return null;
/*  883:     */     }
/*  884:     */     catch (NoSuchElementException nsee) {}
/*  885:1680 */     return null;
/*  886:     */   }
/*  887:     */   
/*  888:     */   private void resetUserParameters()
/*  889:     */   {
/*  890:     */     try
/*  891:     */     {
/*  892:1696 */       if (null == this.m_userParams) {
/*  893:1697 */         return;
/*  894:     */       }
/*  895:1699 */       int n = this.m_userParams.size();
/*  896:1700 */       for (int i = n - 1; i >= 0; i--)
/*  897:     */       {
/*  898:1702 */         Arg arg = (Arg)this.m_userParams.elementAt(i);
/*  899:1703 */         QName name = arg.getQName();
/*  900:     */         
/*  901:     */ 
/*  902:1706 */         String s1 = name.getNamespace();
/*  903:1707 */         String s2 = name.getLocalPart();
/*  904:     */         
/*  905:1709 */         setParameter(s2, s1, arg.getVal().object());
/*  906:     */       }
/*  907:     */     }
/*  908:     */     catch (NoSuchElementException nsee) {}
/*  909:     */   }
/*  910:     */   
/*  911:     */   public void setParameters(Properties params)
/*  912:     */   {
/*  913:1731 */     clearParameters();
/*  914:     */     
/*  915:1733 */     Enumeration names = params.propertyNames();
/*  916:1735 */     while (names.hasMoreElements())
/*  917:     */     {
/*  918:1737 */       String name = params.getProperty((String)names.nextElement());
/*  919:1738 */       StringTokenizer tokenizer = new StringTokenizer(name, "{}", false);
/*  920:     */       try
/*  921:     */       {
/*  922:1745 */         String s1 = tokenizer.nextToken();
/*  923:1746 */         String s2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
/*  924:1748 */         if (null == s2) {
/*  925:1749 */           setParameter(s1, null, params.getProperty(name));
/*  926:     */         } else {
/*  927:1751 */           setParameter(s2, s1, params.getProperty(name));
/*  928:     */         }
/*  929:     */       }
/*  930:     */       catch (NoSuchElementException nsee) {}
/*  931:     */     }
/*  932:     */   }
/*  933:     */   
/*  934:     */   public void clearParameters()
/*  935:     */   {
/*  936:1767 */     synchronized (this.m_reentryGuard)
/*  937:     */     {
/*  938:1769 */       VariableStack varstack = new VariableStack();
/*  939:     */       
/*  940:1771 */       this.m_xcontext.setVarStack(varstack);
/*  941:     */       
/*  942:1773 */       this.m_userParams = null;
/*  943:     */     }
/*  944:     */   }
/*  945:     */   
/*  946:     */   protected void pushGlobalVars(int contextNode)
/*  947:     */     throws TransformerException
/*  948:     */   {
/*  949:1800 */     XPathContext xctxt = this.m_xcontext;
/*  950:1801 */     VariableStack vs = xctxt.getVarStack();
/*  951:1802 */     StylesheetRoot sr = getStylesheet();
/*  952:1803 */     Vector vars = sr.getVariablesAndParamsComposed();
/*  953:     */     
/*  954:1805 */     int i = vars.size();
/*  955:1806 */     vs.link(i);
/*  956:     */     do
/*  957:     */     {
/*  958:1810 */       ElemVariable v = (ElemVariable)vars.elementAt(i);
/*  959:     */       
/*  960:     */ 
/*  961:1813 */       XObject xobj = new XUnresolvedVariable(v, contextNode, this, vs.getStackFrame(), 0, true);
/*  962:1816 */       if (null == vs.elementAt(i)) {
/*  963:1817 */         vs.setGlobalVariable(i, xobj);
/*  964:     */       }
/*  965:1808 */       i--;
/*  966:1808 */     } while (i >= 0);
/*  967:     */   }
/*  968:     */   
/*  969:     */   public void setURIResolver(URIResolver resolver)
/*  970:     */   {
/*  971:1831 */     synchronized (this.m_reentryGuard)
/*  972:     */     {
/*  973:1833 */       this.m_xcontext.getSourceTreeManager().setURIResolver(resolver);
/*  974:     */     }
/*  975:     */   }
/*  976:     */   
/*  977:     */   public URIResolver getURIResolver()
/*  978:     */   {
/*  979:1846 */     return this.m_xcontext.getSourceTreeManager().getURIResolver();
/*  980:     */   }
/*  981:     */   
/*  982:     */   public void setContentHandler(ContentHandler handler)
/*  983:     */   {
/*  984:1862 */     if (handler == null) {
/*  985:1864 */       throw new NullPointerException(XSLMessages.createMessage("ER_NULL_CONTENT_HANDLER", null));
/*  986:     */     }
/*  987:1868 */     this.m_outputContentHandler = handler;
/*  988:1870 */     if (null == this.m_serializationHandler)
/*  989:     */     {
/*  990:1872 */       ToXMLSAXHandler h = new ToXMLSAXHandler();
/*  991:1873 */       h.setContentHandler(handler);
/*  992:1874 */       h.setTransformer(this);
/*  993:     */       
/*  994:1876 */       this.m_serializationHandler = h;
/*  995:     */     }
/*  996:     */     else
/*  997:     */     {
/*  998:1879 */       this.m_serializationHandler.setContentHandler(handler);
/*  999:     */     }
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public ContentHandler getContentHandler()
/* 1003:     */   {
/* 1004:1891 */     return this.m_outputContentHandler;
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public int transformToRTF(ElemTemplateElement templateParent)
/* 1008:     */     throws TransformerException
/* 1009:     */   {
/* 1010:1909 */     DTM dtmFrag = this.m_xcontext.getRTFDTM();
/* 1011:1910 */     return transformToRTF(templateParent, dtmFrag);
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public int transformToGlobalRTF(ElemTemplateElement templateParent)
/* 1015:     */     throws TransformerException
/* 1016:     */   {
/* 1017:1932 */     DTM dtmFrag = this.m_xcontext.getGlobalRTFDTM();
/* 1018:1933 */     return transformToRTF(templateParent, dtmFrag);
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   private int transformToRTF(ElemTemplateElement templateParent, DTM dtmFrag)
/* 1022:     */     throws TransformerException
/* 1023:     */   {
/* 1024:1950 */     XPathContext xctxt = this.m_xcontext;
/* 1025:     */     
/* 1026:1952 */     ContentHandler rtfHandler = dtmFrag.getContentHandler();
/* 1027:     */     
/* 1028:     */ 
/* 1029:     */ 
/* 1030:     */ 
/* 1031:     */ 
/* 1032:     */ 
/* 1033:     */ 
/* 1034:     */ 
/* 1035:1961 */     SerializationHandler savedRTreeHandler = this.m_serializationHandler;
/* 1036:     */     
/* 1037:     */ 
/* 1038:     */ 
/* 1039:1965 */     ToSAXHandler h = new ToXMLSAXHandler();
/* 1040:1966 */     h.setContentHandler(rtfHandler);
/* 1041:1967 */     h.setTransformer(this);
/* 1042:     */     
/* 1043:     */ 
/* 1044:1970 */     this.m_serializationHandler = h;
/* 1045:     */     
/* 1046:     */ 
/* 1047:1973 */     SerializationHandler rth = this.m_serializationHandler;
/* 1048:     */     int resultFragment;
/* 1049:     */     try
/* 1050:     */     {
/* 1051:1977 */       rth.startDocument();
/* 1052:     */       
/* 1053:     */ 
/* 1054:     */ 
/* 1055:     */ 
/* 1056:1982 */       rth.flushPending();
/* 1057:     */       try
/* 1058:     */       {
/* 1059:1988 */         executeChildTemplates(templateParent, true);
/* 1060:     */         
/* 1061:     */ 
/* 1062:1991 */         rth.flushPending();
/* 1063:     */         
/* 1064:     */ 
/* 1065:     */ 
/* 1066:     */ 
/* 1067:     */ 
/* 1068:1997 */         resultFragment = dtmFrag.getDocument();
/* 1069:     */       }
/* 1070:     */       finally
/* 1071:     */       {
/* 1072:2001 */         rth.endDocument();
/* 1073:     */       }
/* 1074:     */     }
/* 1075:     */     catch (SAXException se)
/* 1076:     */     {
/* 1077:2006 */       throw new TransformerException(se);
/* 1078:     */     }
/* 1079:     */     finally
/* 1080:     */     {
/* 1081:2012 */       this.m_serializationHandler = savedRTreeHandler;
/* 1082:     */     }
/* 1083:2015 */     return resultFragment;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public ObjectPool getStringWriterPool()
/* 1087:     */   {
/* 1088:2027 */     return this.m_stringWriterObjectPool;
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public String transformToString(ElemTemplateElement elem)
/* 1092:     */     throws TransformerException
/* 1093:     */   {
/* 1094:2045 */     ElemTemplateElement firstChild = elem.getFirstChildElem();
/* 1095:2046 */     if (null == firstChild) {
/* 1096:2047 */       return "";
/* 1097:     */     }
/* 1098:2048 */     if ((elem.hasTextLitOnly()) && (this.m_optimizer)) {
/* 1099:2050 */       return ((ElemTextLiteral)firstChild).getNodeValue();
/* 1100:     */     }
/* 1101:2054 */     SerializationHandler savedRTreeHandler = this.m_serializationHandler;
/* 1102:     */     
/* 1103:     */ 
/* 1104:     */ 
/* 1105:2058 */     StringWriter sw = (StringWriter)this.m_stringWriterObjectPool.getInstance();
/* 1106:     */     
/* 1107:2060 */     this.m_serializationHandler = ((ToTextStream)this.m_textResultHandlerObjectPool.getInstance());
/* 1108:2063 */     if (null == this.m_serializationHandler)
/* 1109:     */     {
/* 1110:2068 */       Serializer serializer = SerializerFactory.getSerializer(this.m_textformat.getProperties());
/* 1111:     */       
/* 1112:2070 */       this.m_serializationHandler = ((SerializationHandler)serializer);
/* 1113:     */     }
/* 1114:2073 */     this.m_serializationHandler.setTransformer(this);
/* 1115:2074 */     this.m_serializationHandler.setWriter(sw);
/* 1116:     */     String result;
/* 1117:     */     try
/* 1118:     */     {
/* 1119:2087 */       executeChildTemplates(elem, true);
/* 1120:2088 */       this.m_serializationHandler.endDocument();
/* 1121:     */       
/* 1122:2090 */       result = sw.toString();
/* 1123:     */     }
/* 1124:     */     catch (SAXException se)
/* 1125:     */     {
/* 1126:2094 */       throw new TransformerException(se);
/* 1127:     */     }
/* 1128:     */     finally
/* 1129:     */     {
/* 1130:2098 */       sw.getBuffer().setLength(0);
/* 1131:     */       try
/* 1132:     */       {
/* 1133:2102 */         sw.close();
/* 1134:     */       }
/* 1135:     */       catch (Exception ioe) {}
/* 1136:2106 */       this.m_stringWriterObjectPool.freeInstance(sw);
/* 1137:2107 */       this.m_serializationHandler.reset();
/* 1138:2108 */       this.m_textResultHandlerObjectPool.freeInstance(this.m_serializationHandler);
/* 1139:     */       
/* 1140:     */ 
/* 1141:2111 */       this.m_serializationHandler = savedRTreeHandler;
/* 1142:     */     }
/* 1143:2114 */     return result;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public boolean applyTemplateToNode(ElemTemplateElement xslInstruction, ElemTemplate template, int child)
/* 1147:     */     throws TransformerException
/* 1148:     */   {
/* 1149:2133 */     DTM dtm = this.m_xcontext.getDTM(child);
/* 1150:2134 */     short nodeType = dtm.getNodeType(child);
/* 1151:2135 */     boolean isDefaultTextRule = false;
/* 1152:2136 */     boolean isApplyImports = false;
/* 1153:     */     
/* 1154:2138 */     isApplyImports = xslInstruction != null;
/* 1155:2143 */     if ((null == template) || (isApplyImports))
/* 1156:     */     {
/* 1157:2145 */       int endImportLevel = 0;
/* 1158:     */       int maxImportLevel;
/* 1159:2147 */       if (isApplyImports)
/* 1160:     */       {
/* 1161:2149 */         maxImportLevel = template.getStylesheetComposed().getImportCountComposed() - 1;
/* 1162:     */         
/* 1163:2151 */         endImportLevel = template.getStylesheetComposed().getEndImportCountComposed();
/* 1164:     */       }
/* 1165:     */       else
/* 1166:     */       {
/* 1167:2156 */         maxImportLevel = -1;
/* 1168:     */       }
/* 1169:2165 */       if ((isApplyImports) && (maxImportLevel == -1))
/* 1170:     */       {
/* 1171:2167 */         template = null;
/* 1172:     */       }
/* 1173:     */       else
/* 1174:     */       {
/* 1175:2174 */         XPathContext xctxt = this.m_xcontext;
/* 1176:     */         try
/* 1177:     */         {
/* 1178:2178 */           xctxt.pushNamespaceContext(xslInstruction);
/* 1179:     */           
/* 1180:2180 */           QName mode = getMode();
/* 1181:2182 */           if (isApplyImports) {
/* 1182:2183 */             template = this.m_stylesheetRoot.getTemplateComposed(xctxt, child, mode, maxImportLevel, endImportLevel, this.m_quietConflictWarnings, dtm);
/* 1183:     */           } else {
/* 1184:2186 */             template = this.m_stylesheetRoot.getTemplateComposed(xctxt, child, mode, this.m_quietConflictWarnings, dtm);
/* 1185:     */           }
/* 1186:     */         }
/* 1187:     */         finally
/* 1188:     */         {
/* 1189:2192 */           xctxt.popNamespaceContext();
/* 1190:     */         }
/* 1191:     */       }
/* 1192:2198 */       if (null == template) {
/* 1193:2200 */         switch (nodeType)
/* 1194:     */         {
/* 1195:     */         case 1: 
/* 1196:     */         case 11: 
/* 1197:2204 */           template = this.m_stylesheetRoot.getDefaultRule();
/* 1198:2205 */           break;
/* 1199:     */         case 2: 
/* 1200:     */         case 3: 
/* 1201:     */         case 4: 
/* 1202:2209 */           template = this.m_stylesheetRoot.getDefaultTextRule();
/* 1203:2210 */           isDefaultTextRule = true;
/* 1204:2211 */           break;
/* 1205:     */         case 9: 
/* 1206:2213 */           template = this.m_stylesheetRoot.getDefaultRootRule();
/* 1207:2214 */           break;
/* 1208:     */         case 5: 
/* 1209:     */         case 6: 
/* 1210:     */         case 7: 
/* 1211:     */         case 8: 
/* 1212:     */         case 10: 
/* 1213:     */         default: 
/* 1214:2218 */           return false;
/* 1215:     */         }
/* 1216:     */       }
/* 1217:     */     }
/* 1218:     */     try
/* 1219:     */     {
/* 1220:2227 */       pushElemTemplateElement(template);
/* 1221:2228 */       this.m_xcontext.pushCurrentNode(child);
/* 1222:2229 */       pushPairCurrentMatched(template, child);
/* 1223:2232 */       if (!isApplyImports)
/* 1224:     */       {
/* 1225:2233 */         DTMIterator cnl = new NodeSetDTM(child, this.m_xcontext.getDTMManager());
/* 1226:2234 */         this.m_xcontext.pushContextNodeList(cnl);
/* 1227:     */       }
/* 1228:2237 */       if (isDefaultTextRule)
/* 1229:     */       {
/* 1230:2239 */         switch (nodeType)
/* 1231:     */         {
/* 1232:     */         case 3: 
/* 1233:     */         case 4: 
/* 1234:2243 */           ClonerToResultTree.cloneToResultTree(child, nodeType, dtm, getResultTreeHandler(), false);
/* 1235:     */           
/* 1236:2245 */           break;
/* 1237:     */         case 2: 
/* 1238:2247 */           dtm.dispatchCharactersEvents(child, getResultTreeHandler(), false);
/* 1239:     */         }
/* 1240:     */       }
/* 1241:     */       else
/* 1242:     */       {
/* 1243:2256 */         if (this.m_debug) {
/* 1244:2257 */           getTraceManager().fireTraceEvent(template);
/* 1245:     */         }
/* 1246:2267 */         this.m_xcontext.setSAXLocator(template);
/* 1247:     */         
/* 1248:2269 */         this.m_xcontext.getVarStack().link(template.m_frameSize);
/* 1249:2270 */         executeChildTemplates(template, true);
/* 1250:2272 */         if (this.m_debug) {
/* 1251:2273 */           getTraceManager().fireTraceEndEvent(template);
/* 1252:     */         }
/* 1253:     */       }
/* 1254:     */     }
/* 1255:     */     catch (SAXException se)
/* 1256:     */     {
/* 1257:2278 */       throw new TransformerException(se);
/* 1258:     */     }
/* 1259:     */     finally
/* 1260:     */     {
/* 1261:2282 */       if (!isDefaultTextRule) {
/* 1262:2283 */         this.m_xcontext.getVarStack().unlink();
/* 1263:     */       }
/* 1264:2284 */       this.m_xcontext.popCurrentNode();
/* 1265:2285 */       if (!isApplyImports) {
/* 1266:2286 */         this.m_xcontext.popContextNodeList();
/* 1267:     */       }
/* 1268:2288 */       popCurrentMatched();
/* 1269:     */       
/* 1270:2290 */       popElemTemplateElement();
/* 1271:     */     }
/* 1272:2293 */     return true;
/* 1273:     */   }
/* 1274:     */   
/* 1275:     */   public void executeChildTemplates(ElemTemplateElement elem, Node context, QName mode, ContentHandler handler)
/* 1276:     */     throws TransformerException
/* 1277:     */   {
/* 1278:2316 */     XPathContext xctxt = this.m_xcontext;
/* 1279:     */     try
/* 1280:     */     {
/* 1281:2320 */       if (null != mode) {
/* 1282:2321 */         pushMode(mode);
/* 1283:     */       }
/* 1284:2322 */       xctxt.pushCurrentNode(xctxt.getDTMHandleFromNode(context));
/* 1285:2323 */       executeChildTemplates(elem, handler);
/* 1286:     */     }
/* 1287:     */     finally
/* 1288:     */     {
/* 1289:2327 */       xctxt.popCurrentNode();
/* 1290:2331 */       if (null != mode) {
/* 1291:2332 */         popMode();
/* 1292:     */       }
/* 1293:     */     }
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   public void executeChildTemplates(ElemTemplateElement elem, boolean shouldAddAttrs)
/* 1297:     */     throws TransformerException
/* 1298:     */   {
/* 1299:2352 */     ElemTemplateElement t = elem.getFirstChildElem();
/* 1300:2354 */     if (null == t) {
/* 1301:2355 */       return;
/* 1302:     */     }
/* 1303:2357 */     if ((elem.hasTextLitOnly()) && (this.m_optimizer))
/* 1304:     */     {
/* 1305:2359 */       char[] chars = ((ElemTextLiteral)t).getChars();
/* 1306:     */       try
/* 1307:     */       {
/* 1308:2363 */         pushElemTemplateElement(t);
/* 1309:2364 */         this.m_serializationHandler.characters(chars, 0, chars.length);
/* 1310:     */       }
/* 1311:     */       catch (SAXException se)
/* 1312:     */       {
/* 1313:2368 */         throw new TransformerException(se);
/* 1314:     */       }
/* 1315:     */       finally
/* 1316:     */       {
/* 1317:2372 */         popElemTemplateElement();
/* 1318:     */       }
/* 1319:2374 */       return;
/* 1320:     */     }
/* 1321:2383 */     XPathContext xctxt = this.m_xcontext;
/* 1322:2384 */     xctxt.pushSAXLocatorNull();
/* 1323:2385 */     int currentTemplateElementsTop = this.m_currentTemplateElements.size();
/* 1324:2386 */     this.m_currentTemplateElements.push(null);
/* 1325:     */     try
/* 1326:     */     {
/* 1327:2392 */       for (; t != null; t = t.getNextSiblingElem()) {
/* 1328:2394 */         if ((shouldAddAttrs) || (t.getXSLToken() != 48))
/* 1329:     */         {
/* 1330:2398 */           xctxt.setSAXLocator(t);
/* 1331:2399 */           this.m_currentTemplateElements.setElementAt(t, currentTemplateElementsTop);
/* 1332:2400 */           t.execute(this);
/* 1333:     */         }
/* 1334:     */       }
/* 1335:     */     }
/* 1336:     */     catch (RuntimeException re)
/* 1337:     */     {
/* 1338:2405 */       TransformerException te = new TransformerException(re);
/* 1339:2406 */       te.setLocator(t);
/* 1340:2407 */       throw te;
/* 1341:     */     }
/* 1342:     */     finally
/* 1343:     */     {
/* 1344:2411 */       this.m_currentTemplateElements.pop();
/* 1345:2412 */       xctxt.popSAXLocator();
/* 1346:     */     }
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   public void executeChildTemplates(ElemTemplateElement elem, ContentHandler handler)
/* 1350:     */     throws TransformerException
/* 1351:     */   {
/* 1352:2435 */     SerializationHandler xoh = getSerializationHandler();
/* 1353:     */     
/* 1354:     */ 
/* 1355:     */ 
/* 1356:     */ 
/* 1357:2440 */     SerializationHandler savedHandler = xoh;
/* 1358:     */     try
/* 1359:     */     {
/* 1360:2444 */       xoh.flushPending();
/* 1361:     */       
/* 1362:     */ 
/* 1363:2447 */       LexicalHandler lex = null;
/* 1364:2448 */       if ((handler instanceof LexicalHandler)) {
/* 1365:2449 */         lex = (LexicalHandler)handler;
/* 1366:     */       }
/* 1367:2451 */       this.m_serializationHandler = new ToXMLSAXHandler(handler, lex, savedHandler.getEncoding());
/* 1368:2452 */       this.m_serializationHandler.setTransformer(this);
/* 1369:2453 */       executeChildTemplates(elem, true);
/* 1370:     */     }
/* 1371:     */     catch (TransformerException e)
/* 1372:     */     {
/* 1373:2457 */       throw e;
/* 1374:     */     }
/* 1375:     */     catch (SAXException se)
/* 1376:     */     {
/* 1377:2460 */       throw new TransformerException(se);
/* 1378:     */     }
/* 1379:     */     finally
/* 1380:     */     {
/* 1381:2464 */       this.m_serializationHandler = savedHandler;
/* 1382:     */     }
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   public Vector processSortKeys(ElemForEach foreach, int sourceNodeContext)
/* 1386:     */     throws TransformerException
/* 1387:     */   {
/* 1388:2485 */     Vector keys = null;
/* 1389:2486 */     XPathContext xctxt = this.m_xcontext;
/* 1390:2487 */     int nElems = foreach.getSortElemCount();
/* 1391:2489 */     if (nElems > 0) {
/* 1392:2490 */       keys = new Vector();
/* 1393:     */     }
/* 1394:2493 */     for (int i = 0; i < nElems; i++)
/* 1395:     */     {
/* 1396:2495 */       ElemSort sort = foreach.getSortElem(i);
/* 1397:2497 */       if (this.m_debug) {
/* 1398:2498 */         getTraceManager().fireTraceEvent(sort);
/* 1399:     */       }
/* 1400:2500 */       String langString = null != sort.getLang() ? sort.getLang().evaluate(xctxt, sourceNodeContext, foreach) : null;
/* 1401:     */       
/* 1402:     */ 
/* 1403:2503 */       String dataTypeString = sort.getDataType().evaluate(xctxt, sourceNodeContext, foreach);
/* 1404:2506 */       if (dataTypeString.indexOf(":") >= 0) {
/* 1405:2507 */         System.out.println("TODO: Need to write the hooks for QNAME sort data type");
/* 1406:2509 */       } else if ((!dataTypeString.equalsIgnoreCase("text")) && (!dataTypeString.equalsIgnoreCase("number"))) {
/* 1407:2512 */         foreach.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "data-type", dataTypeString });
/* 1408:     */       }
/* 1409:2516 */       boolean treatAsNumbers = (null != dataTypeString) && (dataTypeString.equals("number"));
/* 1410:     */       
/* 1411:     */ 
/* 1412:2519 */       String orderString = sort.getOrder().evaluate(xctxt, sourceNodeContext, foreach);
/* 1413:2522 */       if ((!orderString.equalsIgnoreCase("ascending")) && (!orderString.equalsIgnoreCase("descending"))) {
/* 1414:2525 */         foreach.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "order", orderString });
/* 1415:     */       }
/* 1416:2529 */       boolean descending = (null != orderString) && (orderString.equals("descending"));
/* 1417:     */       
/* 1418:     */ 
/* 1419:2532 */       AVT caseOrder = sort.getCaseOrder();
/* 1420:     */       boolean caseOrderUpper;
/* 1421:2535 */       if (null != caseOrder)
/* 1422:     */       {
/* 1423:2537 */         String caseOrderString = caseOrder.evaluate(xctxt, sourceNodeContext, foreach);
/* 1424:2540 */         if ((!caseOrderString.equalsIgnoreCase("upper-first")) && (!caseOrderString.equalsIgnoreCase("lower-first"))) {
/* 1425:2543 */           foreach.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "case-order", caseOrderString });
/* 1426:     */         }
/* 1427:2547 */         caseOrderUpper = (null != caseOrderString) && (caseOrderString.equals("upper-first"));
/* 1428:     */       }
/* 1429:     */       else
/* 1430:     */       {
/* 1431:2553 */         caseOrderUpper = false;
/* 1432:     */       }
/* 1433:2556 */       keys.addElement(new NodeSortKey(this, sort.getSelect(), treatAsNumbers, descending, langString, caseOrderUpper, foreach));
/* 1434:2559 */       if (this.m_debug) {
/* 1435:2560 */         getTraceManager().fireTraceEndEvent(sort);
/* 1436:     */       }
/* 1437:     */     }
/* 1438:2563 */     return keys;
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public Vector getElementCallstack()
/* 1442:     */   {
/* 1443:2578 */     Vector elems = new Vector();
/* 1444:2579 */     int nStackSize = this.m_currentTemplateElements.size();
/* 1445:2580 */     for (int i = 0; i < nStackSize; i++)
/* 1446:     */     {
/* 1447:2582 */       ElemTemplateElement elem = (ElemTemplateElement)this.m_currentTemplateElements.elementAt(i);
/* 1448:2583 */       if (null != elem) {
/* 1449:2585 */         elems.addElement(elem);
/* 1450:     */       }
/* 1451:     */     }
/* 1452:2588 */     return elems;
/* 1453:     */   }
/* 1454:     */   
/* 1455:     */   public int getCurrentTemplateElementsCount()
/* 1456:     */   {
/* 1457:2599 */     return this.m_currentTemplateElements.size();
/* 1458:     */   }
/* 1459:     */   
/* 1460:     */   public ObjectStack getCurrentTemplateElements()
/* 1461:     */   {
/* 1462:2611 */     return this.m_currentTemplateElements;
/* 1463:     */   }
/* 1464:     */   
/* 1465:     */   public void pushElemTemplateElement(ElemTemplateElement elem)
/* 1466:     */   {
/* 1467:2622 */     this.m_currentTemplateElements.push(elem);
/* 1468:     */   }
/* 1469:     */   
/* 1470:     */   public void popElemTemplateElement()
/* 1471:     */   {
/* 1472:2630 */     this.m_currentTemplateElements.pop();
/* 1473:     */   }
/* 1474:     */   
/* 1475:     */   public void setCurrentElement(ElemTemplateElement e)
/* 1476:     */   {
/* 1477:2642 */     this.m_currentTemplateElements.setTop(e);
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public ElemTemplateElement getCurrentElement()
/* 1481:     */   {
/* 1482:2654 */     return this.m_currentTemplateElements.size() > 0 ? (ElemTemplateElement)this.m_currentTemplateElements.peek() : null;
/* 1483:     */   }
/* 1484:     */   
/* 1485:     */   public int getCurrentNode()
/* 1486:     */   {
/* 1487:2666 */     return this.m_xcontext.getCurrentNode();
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   public Vector getTemplateCallstack()
/* 1491:     */   {
/* 1492:2678 */     Vector elems = new Vector();
/* 1493:2679 */     int nStackSize = this.m_currentTemplateElements.size();
/* 1494:2680 */     for (int i = 0; i < nStackSize; i++)
/* 1495:     */     {
/* 1496:2682 */       ElemTemplateElement elem = (ElemTemplateElement)this.m_currentTemplateElements.elementAt(i);
/* 1497:2683 */       if ((null != elem) && (elem.getXSLToken() != 19)) {
/* 1498:2685 */         elems.addElement(elem);
/* 1499:     */       }
/* 1500:     */     }
/* 1501:2688 */     return elems;
/* 1502:     */   }
/* 1503:     */   
/* 1504:     */   public ElemTemplate getCurrentTemplate()
/* 1505:     */   {
/* 1506:2706 */     ElemTemplateElement elem = getCurrentElement();
/* 1507:2709 */     while ((null != elem) && (elem.getXSLToken() != 19)) {
/* 1508:2711 */       elem = elem.getParentElem();
/* 1509:     */     }
/* 1510:2714 */     return (ElemTemplate)elem;
/* 1511:     */   }
/* 1512:     */   
/* 1513:     */   public void pushPairCurrentMatched(ElemTemplateElement template, int child)
/* 1514:     */   {
/* 1515:2727 */     this.m_currentMatchTemplates.push(template);
/* 1516:2728 */     this.m_currentMatchedNodes.push(child);
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   public void popCurrentMatched()
/* 1520:     */   {
/* 1521:2736 */     this.m_currentMatchTemplates.pop();
/* 1522:2737 */     this.m_currentMatchedNodes.pop();
/* 1523:     */   }
/* 1524:     */   
/* 1525:     */   public ElemTemplate getMatchedTemplate()
/* 1526:     */   {
/* 1527:2751 */     return (ElemTemplate)this.m_currentMatchTemplates.peek();
/* 1528:     */   }
/* 1529:     */   
/* 1530:     */   public int getMatchedNode()
/* 1531:     */   {
/* 1532:2763 */     return this.m_currentMatchedNodes.peepTail();
/* 1533:     */   }
/* 1534:     */   
/* 1535:     */   public DTMIterator getContextNodeList()
/* 1536:     */   {
/* 1537:     */     try
/* 1538:     */     {
/* 1539:2776 */       DTMIterator cnl = this.m_xcontext.getContextNodeList();
/* 1540:     */       
/* 1541:2778 */       return cnl == null ? null : cnl.cloneWithReset();
/* 1542:     */     }
/* 1543:     */     catch (CloneNotSupportedException cnse) {}
/* 1544:2784 */     return null;
/* 1545:     */   }
/* 1546:     */   
/* 1547:     */   public Transformer getTransformer()
/* 1548:     */   {
/* 1549:2795 */     return this;
/* 1550:     */   }
/* 1551:     */   
/* 1552:     */   public void setStylesheet(StylesheetRoot stylesheetRoot)
/* 1553:     */   {
/* 1554:2814 */     this.m_stylesheetRoot = stylesheetRoot;
/* 1555:     */   }
/* 1556:     */   
/* 1557:     */   public final StylesheetRoot getStylesheet()
/* 1558:     */   {
/* 1559:2825 */     return this.m_stylesheetRoot;
/* 1560:     */   }
/* 1561:     */   
/* 1562:     */   public boolean getQuietConflictWarnings()
/* 1563:     */   {
/* 1564:2838 */     return this.m_quietConflictWarnings;
/* 1565:     */   }
/* 1566:     */   
/* 1567:     */   public void setQuietConflictWarnings(boolean b)
/* 1568:     */   {
/* 1569:2852 */     this.m_quietConflictWarnings = b;
/* 1570:     */   }
/* 1571:     */   
/* 1572:     */   public void setXPathContext(XPathContext xcontext)
/* 1573:     */   {
/* 1574:2864 */     this.m_xcontext = xcontext;
/* 1575:     */   }
/* 1576:     */   
/* 1577:     */   public final XPathContext getXPathContext()
/* 1578:     */   {
/* 1579:2874 */     return this.m_xcontext;
/* 1580:     */   }
/* 1581:     */   
/* 1582:     */   public StackGuard getStackGuard()
/* 1583:     */   {
/* 1584:2886 */     return this.m_stackGuard;
/* 1585:     */   }
/* 1586:     */   
/* 1587:     */   public int getRecursionLimit()
/* 1588:     */   {
/* 1589:2903 */     return this.m_stackGuard.getRecursionLimit();
/* 1590:     */   }
/* 1591:     */   
/* 1592:     */   public void setRecursionLimit(int limit)
/* 1593:     */   {
/* 1594:2921 */     this.m_stackGuard.setRecursionLimit(limit);
/* 1595:     */   }
/* 1596:     */   
/* 1597:     */   public SerializationHandler getResultTreeHandler()
/* 1598:     */   {
/* 1599:2932 */     return this.m_serializationHandler;
/* 1600:     */   }
/* 1601:     */   
/* 1602:     */   public SerializationHandler getSerializationHandler()
/* 1603:     */   {
/* 1604:2943 */     return this.m_serializationHandler;
/* 1605:     */   }
/* 1606:     */   
/* 1607:     */   public KeyManager getKeyManager()
/* 1608:     */   {
/* 1609:2954 */     return this.m_keyManager;
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   public boolean isRecursiveAttrSet(ElemAttributeSet attrSet)
/* 1613:     */   {
/* 1614:2967 */     if (null == this.m_attrSetStack) {
/* 1615:2969 */       this.m_attrSetStack = new Stack();
/* 1616:     */     }
/* 1617:2972 */     if (!this.m_attrSetStack.empty())
/* 1618:     */     {
/* 1619:2974 */       int loc = this.m_attrSetStack.search(attrSet);
/* 1620:2976 */       if (loc > -1) {
/* 1621:2978 */         return true;
/* 1622:     */       }
/* 1623:     */     }
/* 1624:2982 */     return false;
/* 1625:     */   }
/* 1626:     */   
/* 1627:     */   public void pushElemAttributeSet(ElemAttributeSet attrSet)
/* 1628:     */   {
/* 1629:2993 */     this.m_attrSetStack.push(attrSet);
/* 1630:     */   }
/* 1631:     */   
/* 1632:     */   public void popElemAttributeSet()
/* 1633:     */   {
/* 1634:3001 */     this.m_attrSetStack.pop();
/* 1635:     */   }
/* 1636:     */   
/* 1637:     */   public CountersTable getCountersTable()
/* 1638:     */   {
/* 1639:3012 */     if (null == this.m_countersTable) {
/* 1640:3013 */       this.m_countersTable = new CountersTable();
/* 1641:     */     }
/* 1642:3015 */     return this.m_countersTable;
/* 1643:     */   }
/* 1644:     */   
/* 1645:     */   public boolean currentTemplateRuleIsNull()
/* 1646:     */   {
/* 1647:3026 */     return (!this.m_currentTemplateRuleIsNull.isEmpty()) && (this.m_currentTemplateRuleIsNull.peek() == true);
/* 1648:     */   }
/* 1649:     */   
/* 1650:     */   public void pushCurrentTemplateRuleIsNull(boolean b)
/* 1651:     */   {
/* 1652:3039 */     this.m_currentTemplateRuleIsNull.push(b);
/* 1653:     */   }
/* 1654:     */   
/* 1655:     */   public void popCurrentTemplateRuleIsNull()
/* 1656:     */   {
/* 1657:3048 */     this.m_currentTemplateRuleIsNull.pop();
/* 1658:     */   }
/* 1659:     */   
/* 1660:     */   public void pushCurrentFuncResult(Object val)
/* 1661:     */   {
/* 1662:3060 */     this.m_currentFuncResult.push(val);
/* 1663:     */   }
/* 1664:     */   
/* 1665:     */   public Object popCurrentFuncResult()
/* 1666:     */   {
/* 1667:3069 */     return this.m_currentFuncResult.pop();
/* 1668:     */   }
/* 1669:     */   
/* 1670:     */   public boolean currentFuncResultSeen()
/* 1671:     */   {
/* 1672:3080 */     return (!this.m_currentFuncResult.empty()) && (this.m_currentFuncResult.peek() != null);
/* 1673:     */   }
/* 1674:     */   
/* 1675:     */   public MsgMgr getMsgMgr()
/* 1676:     */   {
/* 1677:3092 */     if (null == this.m_msgMgr) {
/* 1678:3093 */       this.m_msgMgr = new MsgMgr(this);
/* 1679:     */     }
/* 1680:3095 */     return this.m_msgMgr;
/* 1681:     */   }
/* 1682:     */   
/* 1683:     */   public void setErrorListener(ErrorListener listener)
/* 1684:     */     throws IllegalArgumentException
/* 1685:     */   {
/* 1686:3108 */     synchronized (this.m_reentryGuard)
/* 1687:     */     {
/* 1688:3110 */       if (listener == null) {
/* 1689:3111 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_NULL_ERROR_HANDLER", null));
/* 1690:     */       }
/* 1691:3113 */       this.m_errorHandler = listener;
/* 1692:     */     }
/* 1693:     */   }
/* 1694:     */   
/* 1695:     */   public ErrorListener getErrorListener()
/* 1696:     */   {
/* 1697:3124 */     return this.m_errorHandler;
/* 1698:     */   }
/* 1699:     */   
/* 1700:     */   public TraceManager getTraceManager()
/* 1701:     */   {
/* 1702:3136 */     return this.m_traceManager;
/* 1703:     */   }
/* 1704:     */   
/* 1705:     */   public boolean getFeature(String name)
/* 1706:     */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 1707:     */   {
/* 1708:3175 */     if ("http://xml.org/trax/features/sax/input".equals(name)) {
/* 1709:3176 */       return true;
/* 1710:     */     }
/* 1711:3177 */     if ("http://xml.org/trax/features/dom/input".equals(name)) {
/* 1712:3178 */       return true;
/* 1713:     */     }
/* 1714:3180 */     throw new SAXNotRecognizedException(name);
/* 1715:     */   }
/* 1716:     */   
/* 1717:     */   public QName getMode()
/* 1718:     */   {
/* 1719:3193 */     return this.m_modes.isEmpty() ? null : (QName)this.m_modes.peek();
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   public void pushMode(QName mode)
/* 1723:     */   {
/* 1724:3206 */     this.m_modes.push(mode);
/* 1725:     */   }
/* 1726:     */   
/* 1727:     */   public void popMode()
/* 1728:     */   {
/* 1729:3217 */     this.m_modes.pop();
/* 1730:     */   }
/* 1731:     */   
/* 1732:     */   public void runTransformThread(int priority)
/* 1733:     */   {
/* 1734:3230 */     Thread t = ThreadControllerWrapper.runThread(this, priority);
/* 1735:3231 */     setTransformThread(t);
/* 1736:     */   }
/* 1737:     */   
/* 1738:     */   public void runTransformThread()
/* 1739:     */   {
/* 1740:3241 */     ThreadControllerWrapper.runThread(this, -1);
/* 1741:     */   }
/* 1742:     */   
/* 1743:     */   public static void runTransformThread(Runnable runnable)
/* 1744:     */   {
/* 1745:3252 */     ThreadControllerWrapper.runThread(runnable, -1);
/* 1746:     */   }
/* 1747:     */   
/* 1748:     */   public void waitTransformThread()
/* 1749:     */     throws SAXException
/* 1750:     */   {
/* 1751:3269 */     Thread transformThread = getTransformThread();
/* 1752:3271 */     if (null != transformThread) {
/* 1753:     */       try
/* 1754:     */       {
/* 1755:3275 */         ThreadControllerWrapper.waitThread(transformThread, this);
/* 1756:3277 */         if (!hasTransformThreadErrorCatcher())
/* 1757:     */         {
/* 1758:3279 */           Exception e = getExceptionThrown();
/* 1759:3281 */           if (null != e)
/* 1760:     */           {
/* 1761:3283 */             e.printStackTrace();
/* 1762:3284 */             throw new SAXException(e);
/* 1763:     */           }
/* 1764:     */         }
/* 1765:3288 */         setTransformThread(null);
/* 1766:     */       }
/* 1767:     */       catch (InterruptedException ie) {}
/* 1768:     */     }
/* 1769:     */   }
/* 1770:     */   
/* 1771:     */   public Exception getExceptionThrown()
/* 1772:     */   {
/* 1773:3303 */     return this.m_exceptionThrown;
/* 1774:     */   }
/* 1775:     */   
/* 1776:     */   public void setExceptionThrown(Exception e)
/* 1777:     */   {
/* 1778:3315 */     this.m_exceptionThrown = e;
/* 1779:     */   }
/* 1780:     */   
/* 1781:     */   public void setSourceTreeDocForThread(int doc)
/* 1782:     */   {
/* 1783:3326 */     this.m_doc = doc;
/* 1784:     */   }
/* 1785:     */   
/* 1786:     */   public void setXMLSource(Source source)
/* 1787:     */   {
/* 1788:3338 */     this.m_xmlSource = source;
/* 1789:     */   }
/* 1790:     */   
/* 1791:     */   public boolean isTransformDone()
/* 1792:     */   {
/* 1793:3350 */     synchronized (this)
/* 1794:     */     {
/* 1795:3352 */       boolean bool = this.m_isTransformDone;return bool;
/* 1796:     */     }
/* 1797:     */   }
/* 1798:     */   
/* 1799:     */   public void setIsTransformDone(boolean done)
/* 1800:     */   {
/* 1801:3365 */     synchronized (this)
/* 1802:     */     {
/* 1803:3367 */       this.m_isTransformDone = done;
/* 1804:     */     }
/* 1805:     */   }
/* 1806:     */   
/* 1807:     */   void postExceptionFromThread(Exception e)
/* 1808:     */   {
/* 1809:3400 */     this.m_isTransformDone = true;
/* 1810:3401 */     this.m_exceptionThrown = e;
/* 1811:3404 */     synchronized (this)
/* 1812:     */     {
/* 1813:3411 */       notifyAll();
/* 1814:     */     }
/* 1815:     */   }
/* 1816:     */   
/* 1817:     */   public void run()
/* 1818:     */   {
/* 1819:3429 */     this.m_hasBeenReset = false;
/* 1820:     */     try
/* 1821:     */     {
/* 1822:     */       try
/* 1823:     */       {
/* 1824:3438 */         this.m_isTransformDone = false;
/* 1825:     */         
/* 1826:     */ 
/* 1827:     */ 
/* 1828:     */ 
/* 1829:     */ 
/* 1830:     */ 
/* 1831:     */ 
/* 1832:     */ 
/* 1833:3447 */         transformNode(this.m_doc);
/* 1834:     */       }
/* 1835:     */       catch (Exception e)
/* 1836:     */       {
/* 1837:3455 */         if (null != this.m_transformThread) {
/* 1838:3456 */           postExceptionFromThread(e);
/* 1839:     */         } else {
/* 1840:3458 */           throw new RuntimeException(e.getMessage());
/* 1841:     */         }
/* 1842:     */       }
/* 1843:     */       finally
/* 1844:     */       {
/* 1845:3462 */         this.m_isTransformDone = true;
/* 1846:3464 */         if ((this.m_inputContentHandler instanceof TransformerHandlerImpl)) {
/* 1847:3466 */           ((TransformerHandlerImpl)this.m_inputContentHandler).clearCoRoutine();
/* 1848:     */         }
/* 1849:     */       }
/* 1850:     */     }
/* 1851:     */     catch (Exception e)
/* 1852:     */     {
/* 1853:3479 */       if (null != this.m_transformThread) {
/* 1854:3480 */         postExceptionFromThread(e);
/* 1855:     */       } else {
/* 1856:3482 */         throw new RuntimeException(e.getMessage());
/* 1857:     */       }
/* 1858:     */     }
/* 1859:     */   }
/* 1860:     */   
/* 1861:     */   /**
/* 1862:     */    * @deprecated
/* 1863:     */    */
/* 1864:     */   public TransformSnapshot getSnapshot()
/* 1865:     */   {
/* 1866:3497 */     return new TransformSnapshotImpl(this);
/* 1867:     */   }
/* 1868:     */   
/* 1869:     */   /**
/* 1870:     */    * @deprecated
/* 1871:     */    */
/* 1872:     */   public void executeFromSnapshot(TransformSnapshot ts)
/* 1873:     */     throws TransformerException
/* 1874:     */   {
/* 1875:3514 */     ElemTemplateElement template = getMatchedTemplate();
/* 1876:3515 */     int child = getMatchedNode();
/* 1877:     */     
/* 1878:3517 */     pushElemTemplateElement(template);
/* 1879:3518 */     this.m_xcontext.pushCurrentNode(child);
/* 1880:3519 */     executeChildTemplates(template, true);
/* 1881:     */   }
/* 1882:     */   
/* 1883:     */   /**
/* 1884:     */    * @deprecated
/* 1885:     */    */
/* 1886:     */   public void resetToStylesheet(TransformSnapshot ts)
/* 1887:     */   {
/* 1888:3531 */     ((TransformSnapshotImpl)ts).apply(this);
/* 1889:     */   }
/* 1890:     */   
/* 1891:     */   public void stopTransformation() {}
/* 1892:     */   
/* 1893:     */   public short getShouldStripSpace(int elementHandle, DTM dtm)
/* 1894:     */   {
/* 1895:     */     try
/* 1896:     */     {
/* 1897:3555 */       WhiteSpaceInfo info = this.m_stylesheetRoot.getWhiteSpaceInfo(this.m_xcontext, elementHandle, dtm);
/* 1898:3558 */       if (null == info) {
/* 1899:3560 */         return 3;
/* 1900:     */       }
/* 1901:3566 */       return info.getShouldStripSpace() ? 2 : 1;
/* 1902:     */     }
/* 1903:     */     catch (TransformerException se) {}
/* 1904:3572 */     return 3;
/* 1905:     */   }
/* 1906:     */   
/* 1907:     */   public void init(ToXMLSAXHandler h, Transformer transformer, ContentHandler realHandler)
/* 1908:     */   {
/* 1909:3583 */     h.setTransformer(transformer);
/* 1910:3584 */     h.setContentHandler(realHandler);
/* 1911:     */   }
/* 1912:     */   
/* 1913:     */   public void setSerializationHandler(SerializationHandler xoh)
/* 1914:     */   {
/* 1915:3589 */     this.m_serializationHandler = xoh;
/* 1916:     */   }
/* 1917:     */   
/* 1918:     */   public void fireGenerateEvent(int eventType, char[] ch, int start, int length)
/* 1919:     */   {
/* 1920:3604 */     GenerateEvent ge = new GenerateEvent(this, eventType, ch, start, length);
/* 1921:3605 */     this.m_traceManager.fireGenerateEvent(ge);
/* 1922:     */   }
/* 1923:     */   
/* 1924:     */   public void fireGenerateEvent(int eventType, String name, Attributes atts)
/* 1925:     */   {
/* 1926:3617 */     GenerateEvent ge = new GenerateEvent(this, eventType, name, atts);
/* 1927:3618 */     this.m_traceManager.fireGenerateEvent(ge);
/* 1928:     */   }
/* 1929:     */   
/* 1930:     */   public void fireGenerateEvent(int eventType, String name, String data)
/* 1931:     */   {
/* 1932:3626 */     GenerateEvent ge = new GenerateEvent(this, eventType, name, data);
/* 1933:3627 */     this.m_traceManager.fireGenerateEvent(ge);
/* 1934:     */   }
/* 1935:     */   
/* 1936:     */   public void fireGenerateEvent(int eventType, String data)
/* 1937:     */   {
/* 1938:3635 */     GenerateEvent ge = new GenerateEvent(this, eventType, data);
/* 1939:3636 */     this.m_traceManager.fireGenerateEvent(ge);
/* 1940:     */   }
/* 1941:     */   
/* 1942:     */   public void fireGenerateEvent(int eventType)
/* 1943:     */   {
/* 1944:3644 */     GenerateEvent ge = new GenerateEvent(this, eventType);
/* 1945:3645 */     this.m_traceManager.fireGenerateEvent(ge);
/* 1946:     */   }
/* 1947:     */   
/* 1948:     */   public boolean hasTraceListeners()
/* 1949:     */   {
/* 1950:3652 */     return this.m_traceManager.hasTraceListeners();
/* 1951:     */   }
/* 1952:     */   
/* 1953:     */   public boolean getDebug()
/* 1954:     */   {
/* 1955:3656 */     return this.m_debug;
/* 1956:     */   }
/* 1957:     */   
/* 1958:     */   public void setDebug(boolean b)
/* 1959:     */   {
/* 1960:3660 */     this.m_debug = b;
/* 1961:     */   }
/* 1962:     */   
/* 1963:     */   public boolean getIncremental()
/* 1964:     */   {
/* 1965:3667 */     return this.m_incremental;
/* 1966:     */   }
/* 1967:     */   
/* 1968:     */   public boolean getOptimize()
/* 1969:     */   {
/* 1970:3674 */     return this.m_optimizer;
/* 1971:     */   }
/* 1972:     */   
/* 1973:     */   public boolean getSource_location()
/* 1974:     */   {
/* 1975:3681 */     return this.m_source_location;
/* 1976:     */   }
/* 1977:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.TransformerImpl
 * JD-Core Version:    0.7.0.1
 */