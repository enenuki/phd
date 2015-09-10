/*    1:     */ package org.dom4j.io;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileInputStream;
/*    5:     */ import java.io.FileNotFoundException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.io.Reader;
/*    8:     */ import java.io.Serializable;
/*    9:     */ import java.net.URL;
/*   10:     */ import org.dom4j.Document;
/*   11:     */ import org.dom4j.DocumentException;
/*   12:     */ import org.dom4j.DocumentFactory;
/*   13:     */ import org.dom4j.ElementHandler;
/*   14:     */ import org.xml.sax.EntityResolver;
/*   15:     */ import org.xml.sax.ErrorHandler;
/*   16:     */ import org.xml.sax.InputSource;
/*   17:     */ import org.xml.sax.SAXException;
/*   18:     */ import org.xml.sax.SAXParseException;
/*   19:     */ import org.xml.sax.XMLFilter;
/*   20:     */ import org.xml.sax.XMLReader;
/*   21:     */ import org.xml.sax.helpers.DefaultHandler;
/*   22:     */ import org.xml.sax.helpers.XMLReaderFactory;
/*   23:     */ 
/*   24:     */ public class SAXReader
/*   25:     */ {
/*   26:     */   private static final String SAX_STRING_INTERNING = "http://xml.org/sax/features/string-interning";
/*   27:     */   private static final String SAX_NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
/*   28:     */   private static final String SAX_NAMESPACES = "http://xml.org/sax/features/namespaces";
/*   29:     */   private static final String SAX_DECL_HANDLER = "http://xml.org/sax/properties/declaration-handler";
/*   30:     */   private static final String SAX_LEXICAL_HANDLER = "http://xml.org/sax/properties/lexical-handler";
/*   31:     */   private static final String SAX_LEXICALHANDLER = "http://xml.org/sax/handlers/LexicalHandler";
/*   32:     */   private DocumentFactory factory;
/*   33:     */   private XMLReader xmlReader;
/*   34:     */   private boolean validating;
/*   35:     */   private DispatchHandler dispatchHandler;
/*   36:     */   private ErrorHandler errorHandler;
/*   37:     */   private EntityResolver entityResolver;
/*   38: 112 */   private boolean stringInternEnabled = true;
/*   39: 115 */   private boolean includeInternalDTDDeclarations = false;
/*   40: 118 */   private boolean includeExternalDTDDeclarations = false;
/*   41: 121 */   private boolean mergeAdjacentText = false;
/*   42: 124 */   private boolean stripWhitespaceText = false;
/*   43: 127 */   private boolean ignoreComments = false;
/*   44: 130 */   private String encoding = null;
/*   45:     */   private XMLFilter xmlFilter;
/*   46:     */   
/*   47:     */   public SAXReader() {}
/*   48:     */   
/*   49:     */   public SAXReader(boolean validating)
/*   50:     */   {
/*   51: 142 */     this.validating = validating;
/*   52:     */   }
/*   53:     */   
/*   54:     */   public SAXReader(DocumentFactory factory)
/*   55:     */   {
/*   56: 146 */     this.factory = factory;
/*   57:     */   }
/*   58:     */   
/*   59:     */   public SAXReader(DocumentFactory factory, boolean validating)
/*   60:     */   {
/*   61: 150 */     this.factory = factory;
/*   62: 151 */     this.validating = validating;
/*   63:     */   }
/*   64:     */   
/*   65:     */   public SAXReader(XMLReader xmlReader)
/*   66:     */   {
/*   67: 155 */     this.xmlReader = xmlReader;
/*   68:     */   }
/*   69:     */   
/*   70:     */   public SAXReader(XMLReader xmlReader, boolean validating)
/*   71:     */   {
/*   72: 159 */     this.xmlReader = xmlReader;
/*   73: 160 */     this.validating = validating;
/*   74:     */   }
/*   75:     */   
/*   76:     */   public SAXReader(String xmlReaderClassName)
/*   77:     */     throws SAXException
/*   78:     */   {
/*   79: 164 */     if (xmlReaderClassName != null) {
/*   80: 165 */       this.xmlReader = XMLReaderFactory.createXMLReader(xmlReaderClassName);
/*   81:     */     }
/*   82:     */   }
/*   83:     */   
/*   84:     */   public SAXReader(String xmlReaderClassName, boolean validating)
/*   85:     */     throws SAXException
/*   86:     */   {
/*   87: 172 */     if (xmlReaderClassName != null) {
/*   88: 173 */       this.xmlReader = XMLReaderFactory.createXMLReader(xmlReaderClassName);
/*   89:     */     }
/*   90: 177 */     this.validating = validating;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public void setProperty(String name, Object value)
/*   94:     */     throws SAXException
/*   95:     */   {
/*   96: 198 */     getXMLReader().setProperty(name, value);
/*   97:     */   }
/*   98:     */   
/*   99:     */   public void setFeature(String name, boolean value)
/*  100:     */     throws SAXException
/*  101:     */   {
/*  102: 218 */     getXMLReader().setFeature(name, value);
/*  103:     */   }
/*  104:     */   
/*  105:     */   public Document read(File file)
/*  106:     */     throws DocumentException
/*  107:     */   {
/*  108:     */     try
/*  109:     */     {
/*  110: 243 */       InputSource source = new InputSource(new FileInputStream(file));
/*  111: 244 */       if (this.encoding != null) {
/*  112: 245 */         source.setEncoding(this.encoding);
/*  113:     */       }
/*  114: 247 */       String path = file.getAbsolutePath();
/*  115: 249 */       if (path != null)
/*  116:     */       {
/*  117: 251 */         StringBuffer sb = new StringBuffer("file://");
/*  118: 254 */         if (!path.startsWith(File.separator)) {
/*  119: 255 */           sb.append("/");
/*  120:     */         }
/*  121: 258 */         path = path.replace('\\', '/');
/*  122: 259 */         sb.append(path);
/*  123:     */         
/*  124: 261 */         source.setSystemId(sb.toString());
/*  125:     */       }
/*  126: 264 */       return read(source);
/*  127:     */     }
/*  128:     */     catch (FileNotFoundException e)
/*  129:     */     {
/*  130: 266 */       throw new DocumentException(e.getMessage(), e);
/*  131:     */     }
/*  132:     */   }
/*  133:     */   
/*  134:     */   public Document read(URL url)
/*  135:     */     throws DocumentException
/*  136:     */   {
/*  137: 284 */     String systemID = url.toExternalForm();
/*  138:     */     
/*  139: 286 */     InputSource source = new InputSource(systemID);
/*  140: 287 */     if (this.encoding != null) {
/*  141: 288 */       source.setEncoding(this.encoding);
/*  142:     */     }
/*  143: 291 */     return read(source);
/*  144:     */   }
/*  145:     */   
/*  146:     */   public Document read(String systemId)
/*  147:     */     throws DocumentException
/*  148:     */   {
/*  149: 316 */     InputSource source = new InputSource(systemId);
/*  150: 317 */     if (this.encoding != null) {
/*  151: 318 */       source.setEncoding(this.encoding);
/*  152:     */     }
/*  153: 321 */     return read(source);
/*  154:     */   }
/*  155:     */   
/*  156:     */   public Document read(InputStream in)
/*  157:     */     throws DocumentException
/*  158:     */   {
/*  159: 338 */     InputSource source = new InputSource(in);
/*  160: 339 */     if (this.encoding != null) {
/*  161: 340 */       source.setEncoding(this.encoding);
/*  162:     */     }
/*  163: 343 */     return read(source);
/*  164:     */   }
/*  165:     */   
/*  166:     */   public Document read(Reader reader)
/*  167:     */     throws DocumentException
/*  168:     */   {
/*  169: 360 */     InputSource source = new InputSource(reader);
/*  170: 361 */     if (this.encoding != null) {
/*  171: 362 */       source.setEncoding(this.encoding);
/*  172:     */     }
/*  173: 365 */     return read(source);
/*  174:     */   }
/*  175:     */   
/*  176:     */   public Document read(InputStream in, String systemId)
/*  177:     */     throws DocumentException
/*  178:     */   {
/*  179: 385 */     InputSource source = new InputSource(in);
/*  180: 386 */     source.setSystemId(systemId);
/*  181: 387 */     if (this.encoding != null) {
/*  182: 388 */       source.setEncoding(this.encoding);
/*  183:     */     }
/*  184: 391 */     return read(source);
/*  185:     */   }
/*  186:     */   
/*  187:     */   public Document read(Reader reader, String systemId)
/*  188:     */     throws DocumentException
/*  189:     */   {
/*  190: 411 */     InputSource source = new InputSource(reader);
/*  191: 412 */     source.setSystemId(systemId);
/*  192: 413 */     if (this.encoding != null) {
/*  193: 414 */       source.setEncoding(this.encoding);
/*  194:     */     }
/*  195: 417 */     return read(source);
/*  196:     */   }
/*  197:     */   
/*  198:     */   public Document read(InputSource in)
/*  199:     */     throws DocumentException
/*  200:     */   {
/*  201:     */     try
/*  202:     */     {
/*  203: 435 */       XMLReader reader = getXMLReader();
/*  204:     */       
/*  205: 437 */       reader = installXMLFilter(reader);
/*  206:     */       
/*  207: 439 */       EntityResolver thatEntityResolver = this.entityResolver;
/*  208: 441 */       if (thatEntityResolver == null)
/*  209:     */       {
/*  210: 442 */         thatEntityResolver = createDefaultEntityResolver(in.getSystemId());
/*  211:     */         
/*  212: 444 */         this.entityResolver = thatEntityResolver;
/*  213:     */       }
/*  214: 447 */       reader.setEntityResolver(thatEntityResolver);
/*  215:     */       
/*  216: 449 */       SAXContentHandler contentHandler = createContentHandler(reader);
/*  217: 450 */       contentHandler.setEntityResolver(thatEntityResolver);
/*  218: 451 */       contentHandler.setInputSource(in);
/*  219:     */       
/*  220: 453 */       boolean internal = isIncludeInternalDTDDeclarations();
/*  221: 454 */       boolean external = isIncludeExternalDTDDeclarations();
/*  222:     */       
/*  223: 456 */       contentHandler.setIncludeInternalDTDDeclarations(internal);
/*  224: 457 */       contentHandler.setIncludeExternalDTDDeclarations(external);
/*  225: 458 */       contentHandler.setMergeAdjacentText(isMergeAdjacentText());
/*  226: 459 */       contentHandler.setStripWhitespaceText(isStripWhitespaceText());
/*  227: 460 */       contentHandler.setIgnoreComments(isIgnoreComments());
/*  228: 461 */       reader.setContentHandler(contentHandler);
/*  229:     */       
/*  230: 463 */       configureReader(reader, contentHandler);
/*  231:     */       
/*  232: 465 */       reader.parse(in);
/*  233:     */       
/*  234: 467 */       return contentHandler.getDocument();
/*  235:     */     }
/*  236:     */     catch (Exception e)
/*  237:     */     {
/*  238: 469 */       if ((e instanceof SAXParseException))
/*  239:     */       {
/*  240: 471 */         SAXParseException parseException = (SAXParseException)e;
/*  241: 472 */         String systemId = parseException.getSystemId();
/*  242: 474 */         if (systemId == null) {
/*  243: 475 */           systemId = "";
/*  244:     */         }
/*  245: 478 */         String message = "Error on line " + parseException.getLineNumber() + " of document " + systemId + " : " + parseException.getMessage();
/*  246:     */         
/*  247:     */ 
/*  248:     */ 
/*  249: 482 */         throw new DocumentException(message, e);
/*  250:     */       }
/*  251: 484 */       throw new DocumentException(e.getMessage(), e);
/*  252:     */     }
/*  253:     */   }
/*  254:     */   
/*  255:     */   public boolean isValidating()
/*  256:     */   {
/*  257: 499 */     return this.validating;
/*  258:     */   }
/*  259:     */   
/*  260:     */   public void setValidation(boolean validation)
/*  261:     */   {
/*  262: 509 */     this.validating = validation;
/*  263:     */   }
/*  264:     */   
/*  265:     */   public boolean isIncludeInternalDTDDeclarations()
/*  266:     */   {
/*  267: 519 */     return this.includeInternalDTDDeclarations;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public void setIncludeInternalDTDDeclarations(boolean include)
/*  271:     */   {
/*  272: 531 */     this.includeInternalDTDDeclarations = include;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public boolean isIncludeExternalDTDDeclarations()
/*  276:     */   {
/*  277: 541 */     return this.includeExternalDTDDeclarations;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void setIncludeExternalDTDDeclarations(boolean include)
/*  281:     */   {
/*  282: 553 */     this.includeExternalDTDDeclarations = include;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public boolean isStringInternEnabled()
/*  286:     */   {
/*  287: 563 */     return this.stringInternEnabled;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public void setStringInternEnabled(boolean stringInternEnabled)
/*  291:     */   {
/*  292: 574 */     this.stringInternEnabled = stringInternEnabled;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public boolean isMergeAdjacentText()
/*  296:     */   {
/*  297: 583 */     return this.mergeAdjacentText;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void setMergeAdjacentText(boolean mergeAdjacentText)
/*  301:     */   {
/*  302: 594 */     this.mergeAdjacentText = mergeAdjacentText;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public boolean isStripWhitespaceText()
/*  306:     */   {
/*  307: 604 */     return this.stripWhitespaceText;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public void setStripWhitespaceText(boolean stripWhitespaceText)
/*  311:     */   {
/*  312: 615 */     this.stripWhitespaceText = stripWhitespaceText;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public boolean isIgnoreComments()
/*  316:     */   {
/*  317: 624 */     return this.ignoreComments;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void setIgnoreComments(boolean ignoreComments)
/*  321:     */   {
/*  322: 634 */     this.ignoreComments = ignoreComments;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public DocumentFactory getDocumentFactory()
/*  326:     */   {
/*  327: 644 */     if (this.factory == null) {
/*  328: 645 */       this.factory = DocumentFactory.getInstance();
/*  329:     */     }
/*  330: 648 */     return this.factory;
/*  331:     */   }
/*  332:     */   
/*  333:     */   public void setDocumentFactory(DocumentFactory documentFactory)
/*  334:     */   {
/*  335: 663 */     this.factory = documentFactory;
/*  336:     */   }
/*  337:     */   
/*  338:     */   public ErrorHandler getErrorHandler()
/*  339:     */   {
/*  340: 672 */     return this.errorHandler;
/*  341:     */   }
/*  342:     */   
/*  343:     */   public void setErrorHandler(ErrorHandler errorHandler)
/*  344:     */   {
/*  345: 683 */     this.errorHandler = errorHandler;
/*  346:     */   }
/*  347:     */   
/*  348:     */   public EntityResolver getEntityResolver()
/*  349:     */   {
/*  350: 692 */     return this.entityResolver;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public void setEntityResolver(EntityResolver entityResolver)
/*  354:     */   {
/*  355: 702 */     this.entityResolver = entityResolver;
/*  356:     */   }
/*  357:     */   
/*  358:     */   public XMLReader getXMLReader()
/*  359:     */     throws SAXException
/*  360:     */   {
/*  361: 714 */     if (this.xmlReader == null) {
/*  362: 715 */       this.xmlReader = createXMLReader();
/*  363:     */     }
/*  364: 718 */     return this.xmlReader;
/*  365:     */   }
/*  366:     */   
/*  367:     */   public void setXMLReader(XMLReader reader)
/*  368:     */   {
/*  369: 728 */     this.xmlReader = reader;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public String getEncoding()
/*  373:     */   {
/*  374: 739 */     return this.encoding;
/*  375:     */   }
/*  376:     */   
/*  377:     */   public void setEncoding(String encoding)
/*  378:     */   {
/*  379: 749 */     this.encoding = encoding;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void setXMLReaderClassName(String xmlReaderClassName)
/*  383:     */     throws SAXException
/*  384:     */   {
/*  385: 765 */     setXMLReader(XMLReaderFactory.createXMLReader(xmlReaderClassName));
/*  386:     */   }
/*  387:     */   
/*  388:     */   public void addHandler(String path, ElementHandler handler)
/*  389:     */   {
/*  390: 779 */     getDispatchHandler().addHandler(path, handler);
/*  391:     */   }
/*  392:     */   
/*  393:     */   public void removeHandler(String path)
/*  394:     */   {
/*  395: 790 */     getDispatchHandler().removeHandler(path);
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void setDefaultHandler(ElementHandler handler)
/*  399:     */   {
/*  400: 803 */     getDispatchHandler().setDefaultHandler(handler);
/*  401:     */   }
/*  402:     */   
/*  403:     */   public void resetHandlers()
/*  404:     */   {
/*  405: 812 */     getDispatchHandler().resetHandlers();
/*  406:     */   }
/*  407:     */   
/*  408:     */   public XMLFilter getXMLFilter()
/*  409:     */   {
/*  410: 821 */     return this.xmlFilter;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public void setXMLFilter(XMLFilter filter)
/*  414:     */   {
/*  415: 831 */     this.xmlFilter = filter;
/*  416:     */   }
/*  417:     */   
/*  418:     */   protected XMLReader installXMLFilter(XMLReader reader)
/*  419:     */   {
/*  420: 848 */     XMLFilter filter = getXMLFilter();
/*  421: 850 */     if (filter != null)
/*  422:     */     {
/*  423: 852 */       XMLFilter root = filter;
/*  424:     */       for (;;)
/*  425:     */       {
/*  426: 855 */         XMLReader parent = root.getParent();
/*  427: 857 */         if (!(parent instanceof XMLFilter)) {
/*  428:     */           break;
/*  429:     */         }
/*  430: 858 */         root = (XMLFilter)parent;
/*  431:     */       }
/*  432: 864 */       root.setParent(reader);
/*  433:     */       
/*  434: 866 */       return filter;
/*  435:     */     }
/*  436: 869 */     return reader;
/*  437:     */   }
/*  438:     */   
/*  439:     */   protected DispatchHandler getDispatchHandler()
/*  440:     */   {
/*  441: 873 */     if (this.dispatchHandler == null) {
/*  442: 874 */       this.dispatchHandler = new DispatchHandler();
/*  443:     */     }
/*  444: 877 */     return this.dispatchHandler;
/*  445:     */   }
/*  446:     */   
/*  447:     */   protected void setDispatchHandler(DispatchHandler dispatchHandler)
/*  448:     */   {
/*  449: 881 */     this.dispatchHandler = dispatchHandler;
/*  450:     */   }
/*  451:     */   
/*  452:     */   protected XMLReader createXMLReader()
/*  453:     */     throws SAXException
/*  454:     */   {
/*  455: 894 */     return SAXHelper.createXMLReader(isValidating());
/*  456:     */   }
/*  457:     */   
/*  458:     */   protected void configureReader(XMLReader reader, DefaultHandler handler)
/*  459:     */     throws DocumentException
/*  460:     */   {
/*  461: 911 */     SAXHelper.setParserProperty(reader, "http://xml.org/sax/handlers/LexicalHandler", handler);
/*  462:     */     
/*  463:     */ 
/*  464: 914 */     SAXHelper.setParserProperty(reader, "http://xml.org/sax/properties/lexical-handler", handler);
/*  465: 917 */     if ((this.includeInternalDTDDeclarations) || (this.includeExternalDTDDeclarations)) {
/*  466: 918 */       SAXHelper.setParserProperty(reader, "http://xml.org/sax/properties/declaration-handler", handler);
/*  467:     */     }
/*  468: 922 */     SAXHelper.setParserFeature(reader, "http://xml.org/sax/features/namespaces", true);
/*  469:     */     
/*  470: 924 */     SAXHelper.setParserFeature(reader, "http://xml.org/sax/features/namespace-prefixes", false);
/*  471:     */     
/*  472:     */ 
/*  473: 927 */     SAXHelper.setParserFeature(reader, "http://xml.org/sax/features/string-interning", isStringInternEnabled());
/*  474:     */     
/*  475:     */ 
/*  476:     */ 
/*  477:     */ 
/*  478:     */ 
/*  479:     */ 
/*  480:     */ 
/*  481:     */ 
/*  482:     */ 
/*  483:     */ 
/*  484:     */ 
/*  485: 939 */     SAXHelper.setParserFeature(reader, "http://xml.org/sax/features/use-locator2", true);
/*  486:     */     try
/*  487:     */     {
/*  488: 944 */       reader.setFeature("http://xml.org/sax/features/validation", isValidating());
/*  489: 947 */       if (this.errorHandler != null) {
/*  490: 948 */         reader.setErrorHandler(this.errorHandler);
/*  491:     */       } else {
/*  492: 950 */         reader.setErrorHandler(handler);
/*  493:     */       }
/*  494:     */     }
/*  495:     */     catch (Exception e)
/*  496:     */     {
/*  497: 953 */       if (isValidating()) {
/*  498: 954 */         throw new DocumentException("Validation not supported for XMLReader: " + reader, e);
/*  499:     */       }
/*  500:     */     }
/*  501:     */   }
/*  502:     */   
/*  503:     */   protected SAXContentHandler createContentHandler(XMLReader reader)
/*  504:     */   {
/*  505: 969 */     return new SAXContentHandler(getDocumentFactory(), this.dispatchHandler);
/*  506:     */   }
/*  507:     */   
/*  508:     */   protected EntityResolver createDefaultEntityResolver(String systemId)
/*  509:     */   {
/*  510: 973 */     String prefix = null;
/*  511: 975 */     if ((systemId != null) && (systemId.length() > 0))
/*  512:     */     {
/*  513: 976 */       int idx = systemId.lastIndexOf('/');
/*  514: 978 */       if (idx > 0) {
/*  515: 979 */         prefix = systemId.substring(0, idx + 1);
/*  516:     */       }
/*  517:     */     }
/*  518: 983 */     return new SAXEntityResolver(prefix);
/*  519:     */   }
/*  520:     */   
/*  521:     */   protected static class SAXEntityResolver
/*  522:     */     implements EntityResolver, Serializable
/*  523:     */   {
/*  524:     */     protected String uriPrefix;
/*  525:     */     
/*  526:     */     public SAXEntityResolver(String uriPrefix)
/*  527:     */     {
/*  528: 991 */       this.uriPrefix = uriPrefix;
/*  529:     */     }
/*  530:     */     
/*  531:     */     public InputSource resolveEntity(String publicId, String systemId)
/*  532:     */     {
/*  533: 996 */       if ((systemId != null) && (systemId.length() > 0) && 
/*  534: 997 */         (this.uriPrefix != null) && (systemId.indexOf(':') <= 0)) {
/*  535: 998 */         systemId = this.uriPrefix + systemId;
/*  536:     */       }
/*  537:1002 */       return new InputSource(systemId);
/*  538:     */     }
/*  539:     */   }
/*  540:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXReader
 * JD-Core Version:    0.7.0.1
 */