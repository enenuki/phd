/*    1:     */ package org.apache.xalan.processor;
/*    2:     */ 
/*    3:     */ import java.util.EmptyStackException;
/*    4:     */ import java.util.Stack;
/*    5:     */ import java.util.Vector;
/*    6:     */ import javax.xml.transform.ErrorListener;
/*    7:     */ import javax.xml.transform.Source;
/*    8:     */ import javax.xml.transform.SourceLocator;
/*    9:     */ import javax.xml.transform.Templates;
/*   10:     */ import javax.xml.transform.TransformerConfigurationException;
/*   11:     */ import javax.xml.transform.TransformerException;
/*   12:     */ import javax.xml.transform.sax.TemplatesHandler;
/*   13:     */ import org.apache.xalan.extensions.ExpressionVisitor;
/*   14:     */ import org.apache.xalan.res.XSLMessages;
/*   15:     */ import org.apache.xalan.templates.ElemForEach;
/*   16:     */ import org.apache.xalan.templates.ElemTemplateElement;
/*   17:     */ import org.apache.xalan.templates.FuncDocument;
/*   18:     */ import org.apache.xalan.templates.FuncFormatNumb;
/*   19:     */ import org.apache.xalan.templates.Stylesheet;
/*   20:     */ import org.apache.xalan.templates.StylesheetRoot;
/*   21:     */ import org.apache.xml.utils.BoolStack;
/*   22:     */ import org.apache.xml.utils.NamespaceSupport2;
/*   23:     */ import org.apache.xml.utils.NodeConsumer;
/*   24:     */ import org.apache.xml.utils.PrefixResolver;
/*   25:     */ import org.apache.xml.utils.SAXSourceLocator;
/*   26:     */ import org.apache.xml.utils.UnImplNode;
/*   27:     */ import org.apache.xml.utils.XMLCharacterRecognizer;
/*   28:     */ import org.apache.xpath.XPath;
/*   29:     */ import org.apache.xpath.compiler.FunctionTable;
/*   30:     */ import org.w3c.dom.Node;
/*   31:     */ import org.xml.sax.Attributes;
/*   32:     */ import org.xml.sax.InputSource;
/*   33:     */ import org.xml.sax.Locator;
/*   34:     */ import org.xml.sax.SAXException;
/*   35:     */ import org.xml.sax.SAXParseException;
/*   36:     */ import org.xml.sax.helpers.DefaultHandler;
/*   37:     */ import org.xml.sax.helpers.LocatorImpl;
/*   38:     */ import org.xml.sax.helpers.NamespaceSupport;
/*   39:     */ 
/*   40:     */ public class StylesheetHandler
/*   41:     */   extends DefaultHandler
/*   42:     */   implements TemplatesHandler, PrefixResolver, NodeConsumer
/*   43:     */ {
/*   44:  74 */   private FunctionTable m_funcTable = new FunctionTable();
/*   45:  79 */   private boolean m_optimize = true;
/*   46:  84 */   private boolean m_incremental = false;
/*   47:  89 */   private boolean m_source_location = false;
/*   48:     */   
/*   49:     */   public StylesheetHandler(TransformerFactoryImpl processor)
/*   50:     */     throws TransformerConfigurationException
/*   51:     */   {
/*   52: 103 */     Class func = FuncDocument.class;
/*   53: 104 */     this.m_funcTable.installFunction("document", func);
/*   54:     */     
/*   55:     */ 
/*   56:     */ 
/*   57: 108 */     func = FuncFormatNumb.class;
/*   58:     */     
/*   59: 110 */     this.m_funcTable.installFunction("format-number", func);
/*   60:     */     
/*   61: 112 */     this.m_optimize = ((Boolean)processor.getAttribute("http://xml.apache.org/xalan/features/optimize")).booleanValue();
/*   62:     */     
/*   63: 114 */     this.m_incremental = ((Boolean)processor.getAttribute("http://xml.apache.org/xalan/features/incremental")).booleanValue();
/*   64:     */     
/*   65: 116 */     this.m_source_location = ((Boolean)processor.getAttribute("http://xml.apache.org/xalan/properties/source-location")).booleanValue();
/*   66:     */     
/*   67:     */ 
/*   68: 119 */     init(processor);
/*   69:     */   }
/*   70:     */   
/*   71:     */   void init(TransformerFactoryImpl processor)
/*   72:     */   {
/*   73: 130 */     this.m_stylesheetProcessor = processor;
/*   74:     */     
/*   75:     */ 
/*   76: 133 */     this.m_processors.push(this.m_schema.getElementProcessor());
/*   77: 134 */     pushNewNamespaceSupport();
/*   78:     */   }
/*   79:     */   
/*   80:     */   public XPath createXPath(String str, ElemTemplateElement owningTemplate)
/*   81:     */     throws TransformerException
/*   82:     */   {
/*   83: 154 */     ErrorListener handler = this.m_stylesheetProcessor.getErrorListener();
/*   84: 155 */     XPath xpath = new XPath(str, owningTemplate, this, 0, handler, this.m_funcTable);
/*   85:     */     
/*   86:     */ 
/*   87: 158 */     xpath.callVisitors(xpath, new ExpressionVisitor(getStylesheetRoot()));
/*   88: 159 */     return xpath;
/*   89:     */   }
/*   90:     */   
/*   91:     */   XPath createMatchPatternXPath(String str, ElemTemplateElement owningTemplate)
/*   92:     */     throws TransformerException
/*   93:     */   {
/*   94: 175 */     ErrorListener handler = this.m_stylesheetProcessor.getErrorListener();
/*   95: 176 */     XPath xpath = new XPath(str, owningTemplate, this, 1, handler, this.m_funcTable);
/*   96:     */     
/*   97:     */ 
/*   98: 179 */     xpath.callVisitors(xpath, new ExpressionVisitor(getStylesheetRoot()));
/*   99: 180 */     return xpath;
/*  100:     */   }
/*  101:     */   
/*  102:     */   public String getNamespaceForPrefix(String prefix)
/*  103:     */   {
/*  104: 194 */     return getNamespaceSupport().getURI(prefix);
/*  105:     */   }
/*  106:     */   
/*  107:     */   public String getNamespaceForPrefix(String prefix, Node context)
/*  108:     */   {
/*  109: 213 */     assertion(true, "can't process a context node in StylesheetHandler!");
/*  110:     */     
/*  111: 215 */     return null;
/*  112:     */   }
/*  113:     */   
/*  114:     */   private boolean stackContains(Stack stack, String url)
/*  115:     */   {
/*  116: 229 */     int n = stack.size();
/*  117: 230 */     boolean contains = false;
/*  118: 232 */     for (int i = 0; i < n; i++)
/*  119:     */     {
/*  120: 234 */       String url2 = (String)stack.elementAt(i);
/*  121: 236 */       if (url2.equals(url))
/*  122:     */       {
/*  123: 238 */         contains = true;
/*  124:     */         
/*  125: 240 */         break;
/*  126:     */       }
/*  127:     */     }
/*  128: 244 */     return contains;
/*  129:     */   }
/*  130:     */   
/*  131:     */   public Templates getTemplates()
/*  132:     */   {
/*  133: 265 */     return getStylesheetRoot();
/*  134:     */   }
/*  135:     */   
/*  136:     */   public void setSystemId(String baseID)
/*  137:     */   {
/*  138: 277 */     pushBaseIndentifier(baseID);
/*  139:     */   }
/*  140:     */   
/*  141:     */   public String getSystemId()
/*  142:     */   {
/*  143: 288 */     return getBaseIdentifier();
/*  144:     */   }
/*  145:     */   
/*  146:     */   public InputSource resolveEntity(String publicId, String systemId)
/*  147:     */     throws SAXException
/*  148:     */   {
/*  149: 310 */     return getCurrentProcessor().resolveEntity(this, publicId, systemId);
/*  150:     */   }
/*  151:     */   
/*  152:     */   public void notationDecl(String name, String publicId, String systemId)
/*  153:     */   {
/*  154: 332 */     getCurrentProcessor().notationDecl(this, name, publicId, systemId);
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
/*  158:     */   {
/*  159: 348 */     getCurrentProcessor().unparsedEntityDecl(this, name, publicId, systemId, notationName);
/*  160:     */   }
/*  161:     */   
/*  162:     */   XSLTElementProcessor getProcessorFor(String uri, String localName, String rawName)
/*  163:     */     throws SAXException
/*  164:     */   {
/*  165: 370 */     XSLTElementProcessor currentProcessor = getCurrentProcessor();
/*  166: 371 */     XSLTElementDef def = currentProcessor.getElemDef();
/*  167: 372 */     XSLTElementProcessor elemProcessor = def.getProcessorFor(uri, localName);
/*  168: 374 */     if ((null == elemProcessor) && (!(currentProcessor instanceof ProcessorStylesheetDoc)) && ((null == getStylesheet()) || (Double.valueOf(getStylesheet().getVersion()).doubleValue() > 1.0D) || ((!uri.equals("http://www.w3.org/1999/XSL/Transform")) && ((currentProcessor instanceof ProcessorStylesheetElement))) || (getElemVersion() > 1.0D))) {
/*  169: 384 */       elemProcessor = def.getProcessorForUnknown(uri, localName);
/*  170:     */     }
/*  171: 387 */     if (null == elemProcessor) {
/*  172: 388 */       error(XSLMessages.createMessage("ER_NOT_ALLOWED_IN_POSITION", new Object[] { rawName }), null);
/*  173:     */     }
/*  174: 391 */     return elemProcessor;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public void setDocumentLocator(Locator locator)
/*  178:     */   {
/*  179: 413 */     this.m_stylesheetLocatorStack.push(new SAXSourceLocator(locator));
/*  180:     */   }
/*  181:     */   
/*  182: 419 */   private int m_stylesheetLevel = -1;
/*  183:     */   
/*  184:     */   public void startDocument()
/*  185:     */     throws SAXException
/*  186:     */   {
/*  187: 431 */     this.m_stylesheetLevel += 1;
/*  188: 432 */     pushSpaceHandling(false);
/*  189:     */   }
/*  190:     */   
/*  191: 439 */   private boolean m_parsingComplete = false;
/*  192:     */   
/*  193:     */   public boolean isStylesheetParsingComplete()
/*  194:     */   {
/*  195: 452 */     return this.m_parsingComplete;
/*  196:     */   }
/*  197:     */   
/*  198:     */   public void endDocument()
/*  199:     */     throws SAXException
/*  200:     */   {
/*  201:     */     try
/*  202:     */     {
/*  203: 468 */       if (null != getStylesheetRoot())
/*  204:     */       {
/*  205: 470 */         if (0 == this.m_stylesheetLevel) {
/*  206: 471 */           getStylesheetRoot().recompose();
/*  207:     */         }
/*  208:     */       }
/*  209:     */       else {
/*  210: 474 */         throw new TransformerException(XSLMessages.createMessage("ER_NO_STYLESHEETROOT", null));
/*  211:     */       }
/*  212: 476 */       XSLTElementProcessor elemProcessor = getCurrentProcessor();
/*  213: 478 */       if (null != elemProcessor) {
/*  214: 479 */         elemProcessor.startNonText(this);
/*  215:     */       }
/*  216: 481 */       this.m_stylesheetLevel -= 1;
/*  217:     */       
/*  218: 483 */       popSpaceHandling();
/*  219:     */       
/*  220:     */ 
/*  221:     */ 
/*  222:     */ 
/*  223:     */ 
/*  224:     */ 
/*  225: 490 */       this.m_parsingComplete = (this.m_stylesheetLevel < 0);
/*  226:     */     }
/*  227:     */     catch (TransformerException te)
/*  228:     */     {
/*  229: 494 */       throw new SAXException(te);
/*  230:     */     }
/*  231:     */   }
/*  232:     */   
/*  233: 498 */   private Vector m_prefixMappings = new Vector();
/*  234:     */   
/*  235:     */   public void startPrefixMapping(String prefix, String uri)
/*  236:     */     throws SAXException
/*  237:     */   {
/*  238: 523 */     this.m_prefixMappings.addElement(prefix);
/*  239: 524 */     this.m_prefixMappings.addElement(uri);
/*  240:     */   }
/*  241:     */   
/*  242:     */   public void endPrefixMapping(String prefix)
/*  243:     */     throws SAXException
/*  244:     */   {}
/*  245:     */   
/*  246:     */   private void flushCharacters()
/*  247:     */     throws SAXException
/*  248:     */   {
/*  249: 555 */     XSLTElementProcessor elemProcessor = getCurrentProcessor();
/*  250: 557 */     if (null != elemProcessor) {
/*  251: 558 */       elemProcessor.startNonText(this);
/*  252:     */     }
/*  253:     */   }
/*  254:     */   
/*  255:     */   public void startElement(String uri, String localName, String rawName, Attributes attributes)
/*  256:     */     throws SAXException
/*  257:     */   {
/*  258: 575 */     NamespaceSupport nssupport = getNamespaceSupport();
/*  259: 576 */     nssupport.pushContext();
/*  260:     */     
/*  261: 578 */     int n = this.m_prefixMappings.size();
/*  262: 580 */     for (int i = 0; i < n; i++)
/*  263:     */     {
/*  264: 582 */       String prefix = (String)this.m_prefixMappings.elementAt(i++);
/*  265: 583 */       String nsURI = (String)this.m_prefixMappings.elementAt(i);
/*  266: 584 */       nssupport.declarePrefix(prefix, nsURI);
/*  267:     */     }
/*  268: 587 */     this.m_prefixMappings.removeAllElements();
/*  269:     */     
/*  270: 589 */     this.m_elementID += 1;
/*  271:     */     
/*  272:     */ 
/*  273:     */ 
/*  274:     */ 
/*  275:     */ 
/*  276:     */ 
/*  277:     */ 
/*  278:     */ 
/*  279:     */ 
/*  280:     */ 
/*  281:     */ 
/*  282:     */ 
/*  283:     */ 
/*  284:     */ 
/*  285:     */ 
/*  286:     */ 
/*  287:     */ 
/*  288:     */ 
/*  289:     */ 
/*  290:     */ 
/*  291:     */ 
/*  292: 611 */     checkForFragmentID(attributes);
/*  293: 613 */     if (!this.m_shouldProcess) {
/*  294: 614 */       return;
/*  295:     */     }
/*  296: 616 */     flushCharacters();
/*  297:     */     
/*  298: 618 */     pushSpaceHandling(attributes);
/*  299:     */     
/*  300: 620 */     XSLTElementProcessor elemProcessor = getProcessorFor(uri, localName, rawName);
/*  301: 623 */     if (null != elemProcessor)
/*  302:     */     {
/*  303: 625 */       pushProcessor(elemProcessor);
/*  304: 626 */       elemProcessor.startElement(this, uri, localName, rawName, attributes);
/*  305:     */     }
/*  306:     */     else
/*  307:     */     {
/*  308: 630 */       this.m_shouldProcess = false;
/*  309: 631 */       popSpaceHandling();
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void endElement(String uri, String localName, String rawName)
/*  314:     */     throws SAXException
/*  315:     */   {
/*  316: 651 */     this.m_elementID -= 1;
/*  317: 653 */     if (!this.m_shouldProcess) {
/*  318: 654 */       return;
/*  319:     */     }
/*  320: 656 */     if (this.m_elementID + 1 == this.m_fragmentID) {
/*  321: 657 */       this.m_shouldProcess = false;
/*  322:     */     }
/*  323: 659 */     flushCharacters();
/*  324:     */     
/*  325: 661 */     popSpaceHandling();
/*  326:     */     
/*  327: 663 */     XSLTElementProcessor p = getCurrentProcessor();
/*  328:     */     
/*  329: 665 */     p.endElement(this, uri, localName, rawName);
/*  330: 666 */     popProcessor();
/*  331: 667 */     getNamespaceSupport().popContext();
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void characters(char[] ch, int start, int length)
/*  335:     */     throws SAXException
/*  336:     */   {
/*  337: 686 */     if (!this.m_shouldProcess) {
/*  338: 687 */       return;
/*  339:     */     }
/*  340: 689 */     XSLTElementProcessor elemProcessor = getCurrentProcessor();
/*  341: 690 */     XSLTElementDef def = elemProcessor.getElemDef();
/*  342: 692 */     if (def.getType() != 2) {
/*  343: 693 */       elemProcessor = def.getProcessorFor(null, "text()");
/*  344:     */     }
/*  345: 695 */     if (null == elemProcessor)
/*  346:     */     {
/*  347: 699 */       if (!XMLCharacterRecognizer.isWhiteSpace(ch, start, length)) {
/*  348: 700 */         error(XSLMessages.createMessage("ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", null), null);
/*  349:     */       }
/*  350:     */     }
/*  351:     */     else {
/*  352: 705 */       elemProcessor.characters(this, ch, start, length);
/*  353:     */     }
/*  354:     */   }
/*  355:     */   
/*  356:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/*  357:     */     throws SAXException
/*  358:     */   {
/*  359: 724 */     if (!this.m_shouldProcess) {
/*  360: 725 */       return;
/*  361:     */     }
/*  362: 727 */     getCurrentProcessor().ignorableWhitespace(this, ch, start, length);
/*  363:     */   }
/*  364:     */   
/*  365:     */   public void processingInstruction(String target, String data)
/*  366:     */     throws SAXException
/*  367:     */   {
/*  368: 757 */     if (!this.m_shouldProcess) {
/*  369: 758 */       return;
/*  370:     */     }
/*  371: 769 */     String prefix = "";String ns = "";String localName = target;
/*  372: 770 */     int colon = target.indexOf(':');
/*  373: 771 */     if (colon >= 0)
/*  374:     */     {
/*  375: 773 */       ns = getNamespaceForPrefix(prefix = target.substring(0, colon));
/*  376: 774 */       localName = target.substring(colon + 1);
/*  377:     */     }
/*  378:     */     try
/*  379:     */     {
/*  380: 786 */       if (("xalan-doc-cache-off".equals(target)) || ("xalan:doc-cache-off".equals(target)) || (("doc-cache-off".equals(localName)) && (ns.equals("org.apache.xalan.xslt.extensions.Redirect"))))
/*  381:     */       {
/*  382: 793 */         if (!(this.m_elems.peek() instanceof ElemForEach)) {
/*  383: 794 */           throw new TransformerException("xalan:doc-cache-off not allowed here!", getLocator());
/*  384:     */         }
/*  385: 797 */         ElemForEach elem = (ElemForEach)this.m_elems.peek();
/*  386:     */         
/*  387: 799 */         elem.m_doc_cache_off = true;
/*  388:     */       }
/*  389:     */     }
/*  390:     */     catch (Exception e) {}
/*  391: 811 */     flushCharacters();
/*  392: 812 */     getCurrentProcessor().processingInstruction(this, target, data);
/*  393:     */   }
/*  394:     */   
/*  395:     */   public void skippedEntity(String name)
/*  396:     */     throws SAXException
/*  397:     */   {
/*  398: 832 */     if (!this.m_shouldProcess) {
/*  399: 833 */       return;
/*  400:     */     }
/*  401: 835 */     getCurrentProcessor().skippedEntity(this, name);
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void warn(String msg, Object[] args)
/*  405:     */     throws SAXException
/*  406:     */   {
/*  407: 854 */     String formattedMsg = XSLMessages.createWarning(msg, args);
/*  408: 855 */     SAXSourceLocator locator = getLocator();
/*  409: 856 */     ErrorListener handler = this.m_stylesheetProcessor.getErrorListener();
/*  410:     */     try
/*  411:     */     {
/*  412: 860 */       if (null != handler) {
/*  413: 861 */         handler.warning(new TransformerException(formattedMsg, locator));
/*  414:     */       }
/*  415:     */     }
/*  416:     */     catch (TransformerException te)
/*  417:     */     {
/*  418: 865 */       throw new SAXException(te);
/*  419:     */     }
/*  420:     */   }
/*  421:     */   
/*  422:     */   private void assertion(boolean condition, String msg)
/*  423:     */     throws RuntimeException
/*  424:     */   {
/*  425: 880 */     if (!condition) {
/*  426: 881 */       throw new RuntimeException(msg);
/*  427:     */     }
/*  428:     */   }
/*  429:     */   
/*  430:     */   protected void error(String msg, Exception e)
/*  431:     */     throws SAXException
/*  432:     */   {
/*  433: 901 */     SAXSourceLocator locator = getLocator();
/*  434: 902 */     ErrorListener handler = this.m_stylesheetProcessor.getErrorListener();
/*  435:     */     TransformerException pe;
/*  436: 905 */     if (!(e instanceof TransformerException)) {
/*  437: 907 */       pe = null == e ? new TransformerException(msg, locator) : new TransformerException(msg, locator, e);
/*  438:     */     } else {
/*  439: 912 */       pe = (TransformerException)e;
/*  440:     */     }
/*  441: 914 */     if (null != handler) {
/*  442:     */       try
/*  443:     */       {
/*  444: 918 */         handler.error(pe);
/*  445:     */       }
/*  446:     */       catch (TransformerException te)
/*  447:     */       {
/*  448: 922 */         throw new SAXException(te);
/*  449:     */       }
/*  450:     */     } else {
/*  451: 926 */       throw new SAXException(pe);
/*  452:     */     }
/*  453:     */   }
/*  454:     */   
/*  455:     */   protected void error(String msg, Object[] args, Exception e)
/*  456:     */     throws SAXException
/*  457:     */   {
/*  458: 948 */     String formattedMsg = XSLMessages.createMessage(msg, args);
/*  459:     */     
/*  460: 950 */     error(formattedMsg, e);
/*  461:     */   }
/*  462:     */   
/*  463:     */   public void warning(SAXParseException e)
/*  464:     */     throws SAXException
/*  465:     */   {
/*  466: 967 */     String formattedMsg = e.getMessage();
/*  467: 968 */     SAXSourceLocator locator = getLocator();
/*  468: 969 */     ErrorListener handler = this.m_stylesheetProcessor.getErrorListener();
/*  469:     */     try
/*  470:     */     {
/*  471: 973 */       handler.warning(new TransformerException(formattedMsg, locator));
/*  472:     */     }
/*  473:     */     catch (TransformerException te)
/*  474:     */     {
/*  475: 977 */       throw new SAXException(te);
/*  476:     */     }
/*  477:     */   }
/*  478:     */   
/*  479:     */   public void error(SAXParseException e)
/*  480:     */     throws SAXException
/*  481:     */   {
/*  482: 995 */     String formattedMsg = e.getMessage();
/*  483: 996 */     SAXSourceLocator locator = getLocator();
/*  484: 997 */     ErrorListener handler = this.m_stylesheetProcessor.getErrorListener();
/*  485:     */     try
/*  486:     */     {
/*  487:1001 */       handler.error(new TransformerException(formattedMsg, locator));
/*  488:     */     }
/*  489:     */     catch (TransformerException te)
/*  490:     */     {
/*  491:1005 */       throw new SAXException(te);
/*  492:     */     }
/*  493:     */   }
/*  494:     */   
/*  495:     */   public void fatalError(SAXParseException e)
/*  496:     */     throws SAXException
/*  497:     */   {
/*  498:1023 */     String formattedMsg = e.getMessage();
/*  499:1024 */     SAXSourceLocator locator = getLocator();
/*  500:1025 */     ErrorListener handler = this.m_stylesheetProcessor.getErrorListener();
/*  501:     */     try
/*  502:     */     {
/*  503:1029 */       handler.fatalError(new TransformerException(formattedMsg, locator));
/*  504:     */     }
/*  505:     */     catch (TransformerException te)
/*  506:     */     {
/*  507:1033 */       throw new SAXException(te);
/*  508:     */     }
/*  509:     */   }
/*  510:     */   
/*  511:1042 */   private boolean m_shouldProcess = true;
/*  512:     */   private String m_fragmentIDString;
/*  513:1059 */   private int m_elementID = 0;
/*  514:1065 */   private int m_fragmentID = 0;
/*  515:     */   private TransformerFactoryImpl m_stylesheetProcessor;
/*  516:     */   public static final int STYPE_ROOT = 1;
/*  517:     */   public static final int STYPE_INCLUDE = 2;
/*  518:     */   public static final int STYPE_IMPORT = 3;
/*  519:     */   
/*  520:     */   private void checkForFragmentID(Attributes attributes)
/*  521:     */   {
/*  522:1076 */     if (!this.m_shouldProcess) {
/*  523:1078 */       if ((null != attributes) && (null != this.m_fragmentIDString))
/*  524:     */       {
/*  525:1080 */         int n = attributes.getLength();
/*  526:1082 */         for (int i = 0; i < n; i++)
/*  527:     */         {
/*  528:1084 */           String name = attributes.getQName(i);
/*  529:1086 */           if (name.equals("id"))
/*  530:     */           {
/*  531:1088 */             String val = attributes.getValue(i);
/*  532:1090 */             if (val.equalsIgnoreCase(this.m_fragmentIDString))
/*  533:     */             {
/*  534:1092 */               this.m_shouldProcess = true;
/*  535:1093 */               this.m_fragmentID = this.m_elementID;
/*  536:     */             }
/*  537:     */           }
/*  538:     */         }
/*  539:     */       }
/*  540:     */     }
/*  541:     */   }
/*  542:     */   
/*  543:     */   public TransformerFactoryImpl getStylesheetProcessor()
/*  544:     */   {
/*  545:1114 */     return this.m_stylesheetProcessor;
/*  546:     */   }
/*  547:     */   
/*  548:1139 */   private int m_stylesheetType = 1;
/*  549:     */   
/*  550:     */   int getStylesheetType()
/*  551:     */   {
/*  552:1149 */     return this.m_stylesheetType;
/*  553:     */   }
/*  554:     */   
/*  555:     */   void setStylesheetType(int type)
/*  556:     */   {
/*  557:1160 */     this.m_stylesheetType = type;
/*  558:     */   }
/*  559:     */   
/*  560:1166 */   private Stack m_stylesheets = new Stack();
/*  561:     */   StylesheetRoot m_stylesheetRoot;
/*  562:     */   Stylesheet m_lastPoppedStylesheet;
/*  563:     */   
/*  564:     */   Stylesheet getStylesheet()
/*  565:     */   {
/*  566:1176 */     return this.m_stylesheets.size() == 0 ? null : (Stylesheet)this.m_stylesheets.peek();
/*  567:     */   }
/*  568:     */   
/*  569:     */   Stylesheet getLastPoppedStylesheet()
/*  570:     */   {
/*  571:1187 */     return this.m_lastPoppedStylesheet;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public StylesheetRoot getStylesheetRoot()
/*  575:     */   {
/*  576:1197 */     if (this.m_stylesheetRoot != null)
/*  577:     */     {
/*  578:1198 */       this.m_stylesheetRoot.setOptimizer(this.m_optimize);
/*  579:1199 */       this.m_stylesheetRoot.setIncremental(this.m_incremental);
/*  580:1200 */       this.m_stylesheetRoot.setSource_location(this.m_source_location);
/*  581:     */     }
/*  582:1202 */     return this.m_stylesheetRoot;
/*  583:     */   }
/*  584:     */   
/*  585:     */   public void pushStylesheet(Stylesheet s)
/*  586:     */   {
/*  587:1221 */     if (this.m_stylesheets.size() == 0) {
/*  588:1222 */       this.m_stylesheetRoot = ((StylesheetRoot)s);
/*  589:     */     }
/*  590:1224 */     this.m_stylesheets.push(s);
/*  591:     */   }
/*  592:     */   
/*  593:     */   Stylesheet popStylesheet()
/*  594:     */   {
/*  595:1240 */     if (!this.m_stylesheetLocatorStack.isEmpty()) {
/*  596:1241 */       this.m_stylesheetLocatorStack.pop();
/*  597:     */     }
/*  598:1243 */     if (!this.m_stylesheets.isEmpty()) {
/*  599:1244 */       this.m_lastPoppedStylesheet = ((Stylesheet)this.m_stylesheets.pop());
/*  600:     */     }
/*  601:1247 */     return this.m_lastPoppedStylesheet;
/*  602:     */   }
/*  603:     */   
/*  604:1253 */   private Stack m_processors = new Stack();
/*  605:     */   
/*  606:     */   XSLTElementProcessor getCurrentProcessor()
/*  607:     */   {
/*  608:1262 */     return (XSLTElementProcessor)this.m_processors.peek();
/*  609:     */   }
/*  610:     */   
/*  611:     */   void pushProcessor(XSLTElementProcessor processor)
/*  612:     */   {
/*  613:1272 */     this.m_processors.push(processor);
/*  614:     */   }
/*  615:     */   
/*  616:     */   XSLTElementProcessor popProcessor()
/*  617:     */   {
/*  618:1281 */     return (XSLTElementProcessor)this.m_processors.pop();
/*  619:     */   }
/*  620:     */   
/*  621:1290 */   private XSLTSchema m_schema = new XSLTSchema();
/*  622:     */   
/*  623:     */   public XSLTSchema getSchema()
/*  624:     */   {
/*  625:1301 */     return this.m_schema;
/*  626:     */   }
/*  627:     */   
/*  628:1307 */   private Stack m_elems = new Stack();
/*  629:     */   
/*  630:     */   ElemTemplateElement getElemTemplateElement()
/*  631:     */   {
/*  632:     */     try
/*  633:     */     {
/*  634:1318 */       return (ElemTemplateElement)this.m_elems.peek();
/*  635:     */     }
/*  636:     */     catch (EmptyStackException ese) {}
/*  637:1322 */     return null;
/*  638:     */   }
/*  639:     */   
/*  640:1329 */   private int m_docOrderCount = 0;
/*  641:     */   
/*  642:     */   int nextUid()
/*  643:     */   {
/*  644:1336 */     return this.m_docOrderCount++;
/*  645:     */   }
/*  646:     */   
/*  647:     */   void pushElemTemplateElement(ElemTemplateElement elem)
/*  648:     */   {
/*  649:1350 */     if (elem.getUid() == -1) {
/*  650:1351 */       elem.setUid(nextUid());
/*  651:     */     }
/*  652:1353 */     this.m_elems.push(elem);
/*  653:     */   }
/*  654:     */   
/*  655:     */   ElemTemplateElement popElemTemplateElement()
/*  656:     */   {
/*  657:1362 */     return (ElemTemplateElement)this.m_elems.pop();
/*  658:     */   }
/*  659:     */   
/*  660:1369 */   Stack m_baseIdentifiers = new Stack();
/*  661:     */   
/*  662:     */   void pushBaseIndentifier(String baseID)
/*  663:     */   {
/*  664:1382 */     if (null != baseID)
/*  665:     */     {
/*  666:1384 */       int posOfHash = baseID.indexOf('#');
/*  667:1386 */       if (posOfHash > -1)
/*  668:     */       {
/*  669:1388 */         this.m_fragmentIDString = baseID.substring(posOfHash + 1);
/*  670:1389 */         this.m_shouldProcess = false;
/*  671:     */       }
/*  672:     */       else
/*  673:     */       {
/*  674:1392 */         this.m_shouldProcess = true;
/*  675:     */       }
/*  676:     */     }
/*  677:     */     else
/*  678:     */     {
/*  679:1395 */       this.m_shouldProcess = true;
/*  680:     */     }
/*  681:1397 */     this.m_baseIdentifiers.push(baseID);
/*  682:     */   }
/*  683:     */   
/*  684:     */   String popBaseIndentifier()
/*  685:     */   {
/*  686:1406 */     return (String)this.m_baseIdentifiers.pop();
/*  687:     */   }
/*  688:     */   
/*  689:     */   public String getBaseIdentifier()
/*  690:     */   {
/*  691:1420 */     String base = (String)(this.m_baseIdentifiers.isEmpty() ? null : this.m_baseIdentifiers.peek());
/*  692:1424 */     if (null == base)
/*  693:     */     {
/*  694:1426 */       SourceLocator locator = getLocator();
/*  695:     */       
/*  696:1428 */       base = null == locator ? "" : locator.getSystemId();
/*  697:     */     }
/*  698:1431 */     return base;
/*  699:     */   }
/*  700:     */   
/*  701:1438 */   private Stack m_stylesheetLocatorStack = new Stack();
/*  702:     */   
/*  703:     */   public SAXSourceLocator getLocator()
/*  704:     */   {
/*  705:1448 */     if (this.m_stylesheetLocatorStack.isEmpty())
/*  706:     */     {
/*  707:1450 */       SAXSourceLocator locator = new SAXSourceLocator();
/*  708:     */       
/*  709:1452 */       locator.setSystemId(getStylesheetProcessor().getDOMsystemID());
/*  710:     */       
/*  711:1454 */       return locator;
/*  712:     */     }
/*  713:1459 */     return (SAXSourceLocator)this.m_stylesheetLocatorStack.peek();
/*  714:     */   }
/*  715:     */   
/*  716:1466 */   private Stack m_importStack = new Stack();
/*  717:1473 */   private Stack m_importSourceStack = new Stack();
/*  718:     */   
/*  719:     */   void pushImportURL(String hrefUrl)
/*  720:     */   {
/*  721:1483 */     this.m_importStack.push(hrefUrl);
/*  722:     */   }
/*  723:     */   
/*  724:     */   void pushImportSource(Source sourceFromURIResolver)
/*  725:     */   {
/*  726:1493 */     this.m_importSourceStack.push(sourceFromURIResolver);
/*  727:     */   }
/*  728:     */   
/*  729:     */   boolean importStackContains(String hrefUrl)
/*  730:     */   {
/*  731:1506 */     return stackContains(this.m_importStack, hrefUrl);
/*  732:     */   }
/*  733:     */   
/*  734:     */   String popImportURL()
/*  735:     */   {
/*  736:1516 */     return (String)this.m_importStack.pop();
/*  737:     */   }
/*  738:     */   
/*  739:     */   String peekImportURL()
/*  740:     */   {
/*  741:1521 */     return (String)this.m_importStack.peek();
/*  742:     */   }
/*  743:     */   
/*  744:     */   Source peekSourceFromURIResolver()
/*  745:     */   {
/*  746:1526 */     return (Source)this.m_importSourceStack.peek();
/*  747:     */   }
/*  748:     */   
/*  749:     */   Source popImportSource()
/*  750:     */   {
/*  751:1535 */     return (Source)this.m_importSourceStack.pop();
/*  752:     */   }
/*  753:     */   
/*  754:1542 */   private boolean warnedAboutOldXSLTNamespace = false;
/*  755:1545 */   Stack m_nsSupportStack = new Stack();
/*  756:     */   private Node m_originatingNode;
/*  757:     */   
/*  758:     */   void pushNewNamespaceSupport()
/*  759:     */   {
/*  760:1552 */     this.m_nsSupportStack.push(new NamespaceSupport2());
/*  761:     */   }
/*  762:     */   
/*  763:     */   void popNamespaceSupport()
/*  764:     */   {
/*  765:1561 */     this.m_nsSupportStack.pop();
/*  766:     */   }
/*  767:     */   
/*  768:     */   NamespaceSupport getNamespaceSupport()
/*  769:     */   {
/*  770:1572 */     return (NamespaceSupport)this.m_nsSupportStack.peek();
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setOriginatingNode(Node n)
/*  774:     */   {
/*  775:1590 */     this.m_originatingNode = n;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public Node getOriginatingNode()
/*  779:     */   {
/*  780:1601 */     return this.m_originatingNode;
/*  781:     */   }
/*  782:     */   
/*  783:1608 */   private BoolStack m_spacePreserveStack = new BoolStack();
/*  784:     */   
/*  785:     */   boolean isSpacePreserve()
/*  786:     */   {
/*  787:1618 */     return this.m_spacePreserveStack.peek();
/*  788:     */   }
/*  789:     */   
/*  790:     */   void popSpaceHandling()
/*  791:     */   {
/*  792:1626 */     this.m_spacePreserveStack.pop();
/*  793:     */   }
/*  794:     */   
/*  795:     */   void pushSpaceHandling(boolean b)
/*  796:     */     throws SAXParseException
/*  797:     */   {
/*  798:1637 */     this.m_spacePreserveStack.push(b);
/*  799:     */   }
/*  800:     */   
/*  801:     */   void pushSpaceHandling(Attributes attrs)
/*  802:     */     throws SAXParseException
/*  803:     */   {
/*  804:1649 */     String value = attrs.getValue("xml:space");
/*  805:1650 */     if (null == value)
/*  806:     */     {
/*  807:1652 */       this.m_spacePreserveStack.push(this.m_spacePreserveStack.peekOrFalse());
/*  808:     */     }
/*  809:1654 */     else if (value.equals("preserve"))
/*  810:     */     {
/*  811:1656 */       this.m_spacePreserveStack.push(true);
/*  812:     */     }
/*  813:1658 */     else if (value.equals("default"))
/*  814:     */     {
/*  815:1660 */       this.m_spacePreserveStack.push(false);
/*  816:     */     }
/*  817:     */     else
/*  818:     */     {
/*  819:1664 */       SAXSourceLocator locator = getLocator();
/*  820:1665 */       ErrorListener handler = this.m_stylesheetProcessor.getErrorListener();
/*  821:     */       try
/*  822:     */       {
/*  823:1669 */         handler.error(new TransformerException(XSLMessages.createMessage("ER_ILLEGAL_XMLSPACE_VALUE", null), locator));
/*  824:     */       }
/*  825:     */       catch (TransformerException te)
/*  826:     */       {
/*  827:1673 */         throw new SAXParseException(te.getMessage(), locator, te);
/*  828:     */       }
/*  829:1675 */       this.m_spacePreserveStack.push(this.m_spacePreserveStack.peek());
/*  830:     */     }
/*  831:     */   }
/*  832:     */   
/*  833:     */   private double getElemVersion()
/*  834:     */   {
/*  835:1681 */     ElemTemplateElement elem = getElemTemplateElement();
/*  836:1682 */     double version = -1.0D;
/*  837:1683 */     while (((version == -1.0D) || (version == 1.0D)) && (elem != null))
/*  838:     */     {
/*  839:     */       try
/*  840:     */       {
/*  841:1686 */         version = Double.valueOf(elem.getXmlVersion()).doubleValue();
/*  842:     */       }
/*  843:     */       catch (Exception ex)
/*  844:     */       {
/*  845:1690 */         version = -1.0D;
/*  846:     */       }
/*  847:1692 */       elem = elem.getParentElem();
/*  848:     */     }
/*  849:1694 */     return version == -1.0D ? 1.0D : version;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public boolean handlesNullPrefixes()
/*  853:     */   {
/*  854:1700 */     return false;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public boolean getOptimize()
/*  858:     */   {
/*  859:1707 */     return this.m_optimize;
/*  860:     */   }
/*  861:     */   
/*  862:     */   public boolean getIncremental()
/*  863:     */   {
/*  864:1714 */     return this.m_incremental;
/*  865:     */   }
/*  866:     */   
/*  867:     */   public boolean getSource_location()
/*  868:     */   {
/*  869:1721 */     return this.m_source_location;
/*  870:     */   }
/*  871:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.StylesheetHandler
 * JD-Core Version:    0.7.0.1
 */