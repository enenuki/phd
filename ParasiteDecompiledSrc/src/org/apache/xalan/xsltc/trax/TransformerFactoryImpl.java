/*    1:     */ package org.apache.xalan.xsltc.trax;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileInputStream;
/*    5:     */ import java.io.FileNotFoundException;
/*    6:     */ import java.io.FilenameFilter;
/*    7:     */ import java.io.IOException;
/*    8:     */ import java.io.InputStream;
/*    9:     */ import java.io.PrintStream;
/*   10:     */ import java.net.MalformedURLException;
/*   11:     */ import java.net.URL;
/*   12:     */ import java.util.ArrayList;
/*   13:     */ import java.util.Enumeration;
/*   14:     */ import java.util.Hashtable;
/*   15:     */ import java.util.List;
/*   16:     */ import java.util.Properties;
/*   17:     */ import java.util.Vector;
/*   18:     */ import java.util.zip.ZipEntry;
/*   19:     */ import java.util.zip.ZipFile;
/*   20:     */ import javax.xml.parsers.ParserConfigurationException;
/*   21:     */ import javax.xml.parsers.SAXParser;
/*   22:     */ import javax.xml.parsers.SAXParserFactory;
/*   23:     */ import javax.xml.transform.ErrorListener;
/*   24:     */ import javax.xml.transform.Source;
/*   25:     */ import javax.xml.transform.Templates;
/*   26:     */ import javax.xml.transform.Transformer;
/*   27:     */ import javax.xml.transform.TransformerConfigurationException;
/*   28:     */ import javax.xml.transform.TransformerException;
/*   29:     */ import javax.xml.transform.URIResolver;
/*   30:     */ import javax.xml.transform.dom.DOMSource;
/*   31:     */ import javax.xml.transform.sax.SAXSource;
/*   32:     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*   33:     */ import javax.xml.transform.sax.TemplatesHandler;
/*   34:     */ import javax.xml.transform.sax.TransformerHandler;
/*   35:     */ import org.apache.xalan.xsltc.compiler.SourceLoader;
/*   36:     */ import org.apache.xalan.xsltc.compiler.XSLTC;
/*   37:     */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   38:     */ import org.apache.xalan.xsltc.dom.XSLTCDTMManager;
/*   39:     */ import org.apache.xml.utils.StopParseException;
/*   40:     */ import org.apache.xml.utils.StylesheetPIHandler;
/*   41:     */ import org.w3c.dom.Node;
/*   42:     */ import org.xml.sax.InputSource;
/*   43:     */ import org.xml.sax.SAXException;
/*   44:     */ import org.xml.sax.XMLFilter;
/*   45:     */ import org.xml.sax.XMLReader;
/*   46:     */ import org.xml.sax.helpers.XMLReaderFactory;
/*   47:     */ 
/*   48:     */ public class TransformerFactoryImpl
/*   49:     */   extends SAXTransformerFactory
/*   50:     */   implements SourceLoader, ErrorListener
/*   51:     */ {
/*   52:     */   public static final String TRANSLET_NAME = "translet-name";
/*   53:     */   public static final String DESTINATION_DIRECTORY = "destination-directory";
/*   54:     */   public static final String PACKAGE_NAME = "package-name";
/*   55:     */   public static final String JAR_NAME = "jar-name";
/*   56:     */   public static final String GENERATE_TRANSLET = "generate-translet";
/*   57:     */   public static final String AUTO_TRANSLET = "auto-translet";
/*   58:     */   public static final String USE_CLASSPATH = "use-classpath";
/*   59:     */   public static final String DEBUG = "debug";
/*   60:     */   public static final String ENABLE_INLINING = "enable-inlining";
/*   61:     */   public static final String INDENT_NUMBER = "indent-number";
/*   62:  97 */   private ErrorListener _errorListener = this;
/*   63: 102 */   private URIResolver _uriResolver = null;
/*   64:     */   protected static final String DEFAULT_TRANSLET_NAME = "GregorSamsa";
/*   65: 119 */   private String _transletName = "GregorSamsa";
/*   66: 124 */   private String _destinationDirectory = null;
/*   67: 129 */   private String _packageName = null;
/*   68: 134 */   private String _jarFileName = null;
/*   69: 140 */   private Hashtable _piParams = null;
/*   70:     */   
/*   71:     */   private static class PIParamWrapper
/*   72:     */   {
/*   73: 146 */     public String _media = null;
/*   74: 147 */     public String _title = null;
/*   75: 148 */     public String _charset = null;
/*   76:     */     
/*   77:     */     public PIParamWrapper(String media, String title, String charset)
/*   78:     */     {
/*   79: 151 */       this._media = media;
/*   80: 152 */       this._title = title;
/*   81: 153 */       this._charset = charset;
/*   82:     */     }
/*   83:     */   }
/*   84:     */   
/*   85: 160 */   private boolean _debug = false;
/*   86: 165 */   private boolean _enableInlining = false;
/*   87: 171 */   private boolean _generateTranslet = false;
/*   88: 179 */   private boolean _autoTranslet = false;
/*   89: 185 */   private boolean _useClasspath = false;
/*   90: 190 */   private int _indentNumber = -1;
/*   91:     */   private Class m_DTMManagerClass;
/*   92: 203 */   private boolean _isSecureProcessing = false;
/*   93:     */   
/*   94:     */   public TransformerFactoryImpl()
/*   95:     */   {
/*   96: 209 */     this.m_DTMManagerClass = XSLTCDTMManager.getDTMManagerClass();
/*   97:     */   }
/*   98:     */   
/*   99:     */   public void setErrorListener(ErrorListener listener)
/*  100:     */     throws IllegalArgumentException
/*  101:     */   {
/*  102: 224 */     if (listener == null)
/*  103:     */     {
/*  104: 225 */       ErrorMsg err = new ErrorMsg("ERROR_LISTENER_NULL_ERR", "TransformerFactory");
/*  105:     */       
/*  106: 227 */       throw new IllegalArgumentException(err.toString());
/*  107:     */     }
/*  108: 229 */     this._errorListener = listener;
/*  109:     */   }
/*  110:     */   
/*  111:     */   public ErrorListener getErrorListener()
/*  112:     */   {
/*  113: 239 */     return this._errorListener;
/*  114:     */   }
/*  115:     */   
/*  116:     */   public Object getAttribute(String name)
/*  117:     */     throws IllegalArgumentException
/*  118:     */   {
/*  119: 254 */     if (name.equals("translet-name")) {
/*  120: 255 */       return this._transletName;
/*  121:     */     }
/*  122: 257 */     if (name.equals("generate-translet")) {
/*  123: 258 */       return new Boolean(this._generateTranslet);
/*  124:     */     }
/*  125: 260 */     if (name.equals("auto-translet")) {
/*  126: 261 */       return new Boolean(this._autoTranslet);
/*  127:     */     }
/*  128: 263 */     if (name.equals("enable-inlining"))
/*  129:     */     {
/*  130: 264 */       if (this._enableInlining) {
/*  131: 265 */         return Boolean.TRUE;
/*  132:     */       }
/*  133: 267 */       return Boolean.FALSE;
/*  134:     */     }
/*  135: 271 */     ErrorMsg err = new ErrorMsg("JAXP_INVALID_ATTR_ERR", name);
/*  136: 272 */     throw new IllegalArgumentException(err.toString());
/*  137:     */   }
/*  138:     */   
/*  139:     */   public void setAttribute(String name, Object value)
/*  140:     */     throws IllegalArgumentException
/*  141:     */   {
/*  142: 288 */     if ((name.equals("translet-name")) && ((value instanceof String)))
/*  143:     */     {
/*  144: 289 */       this._transletName = ((String)value);
/*  145: 290 */       return;
/*  146:     */     }
/*  147: 292 */     if ((name.equals("destination-directory")) && ((value instanceof String)))
/*  148:     */     {
/*  149: 293 */       this._destinationDirectory = ((String)value);
/*  150: 294 */       return;
/*  151:     */     }
/*  152: 296 */     if ((name.equals("package-name")) && ((value instanceof String)))
/*  153:     */     {
/*  154: 297 */       this._packageName = ((String)value);
/*  155: 298 */       return;
/*  156:     */     }
/*  157: 300 */     if ((name.equals("jar-name")) && ((value instanceof String)))
/*  158:     */     {
/*  159: 301 */       this._jarFileName = ((String)value);
/*  160: 302 */       return;
/*  161:     */     }
/*  162: 304 */     if (name.equals("generate-translet"))
/*  163:     */     {
/*  164: 305 */       if ((value instanceof Boolean))
/*  165:     */       {
/*  166: 306 */         this._generateTranslet = ((Boolean)value).booleanValue();
/*  167: 307 */         return;
/*  168:     */       }
/*  169: 309 */       if ((value instanceof String)) {
/*  170: 310 */         this._generateTranslet = ((String)value).equalsIgnoreCase("true");
/*  171:     */       }
/*  172:     */     }
/*  173: 314 */     else if (name.equals("auto-translet"))
/*  174:     */     {
/*  175: 315 */       if ((value instanceof Boolean))
/*  176:     */       {
/*  177: 316 */         this._autoTranslet = ((Boolean)value).booleanValue();
/*  178: 317 */         return;
/*  179:     */       }
/*  180: 319 */       if ((value instanceof String)) {
/*  181: 320 */         this._autoTranslet = ((String)value).equalsIgnoreCase("true");
/*  182:     */       }
/*  183:     */     }
/*  184: 324 */     else if (name.equals("use-classpath"))
/*  185:     */     {
/*  186: 325 */       if ((value instanceof Boolean))
/*  187:     */       {
/*  188: 326 */         this._useClasspath = ((Boolean)value).booleanValue();
/*  189: 327 */         return;
/*  190:     */       }
/*  191: 329 */       if ((value instanceof String)) {
/*  192: 330 */         this._useClasspath = ((String)value).equalsIgnoreCase("true");
/*  193:     */       }
/*  194:     */     }
/*  195: 334 */     else if (name.equals("debug"))
/*  196:     */     {
/*  197: 335 */       if ((value instanceof Boolean))
/*  198:     */       {
/*  199: 336 */         this._debug = ((Boolean)value).booleanValue();
/*  200: 337 */         return;
/*  201:     */       }
/*  202: 339 */       if ((value instanceof String)) {
/*  203: 340 */         this._debug = ((String)value).equalsIgnoreCase("true");
/*  204:     */       }
/*  205:     */     }
/*  206: 344 */     else if (name.equals("enable-inlining"))
/*  207:     */     {
/*  208: 345 */       if ((value instanceof Boolean))
/*  209:     */       {
/*  210: 346 */         this._enableInlining = ((Boolean)value).booleanValue();
/*  211: 347 */         return;
/*  212:     */       }
/*  213: 349 */       if ((value instanceof String)) {
/*  214: 350 */         this._enableInlining = ((String)value).equalsIgnoreCase("true");
/*  215:     */       }
/*  216:     */     }
/*  217: 354 */     else if (name.equals("indent-number"))
/*  218:     */     {
/*  219: 355 */       if ((value instanceof String))
/*  220:     */       {
/*  221:     */         try
/*  222:     */         {
/*  223: 357 */           this._indentNumber = Integer.parseInt((String)value);
/*  224: 358 */           return;
/*  225:     */         }
/*  226:     */         catch (NumberFormatException e) {}
/*  227:     */       }
/*  228: 364 */       else if ((value instanceof Integer))
/*  229:     */       {
/*  230: 365 */         this._indentNumber = ((Integer)value).intValue();
/*  231: 366 */         return;
/*  232:     */       }
/*  233:     */     }
/*  234: 371 */     ErrorMsg err = new ErrorMsg("JAXP_INVALID_ATTR_ERR", name);
/*  235:     */     
/*  236: 373 */     throw new IllegalArgumentException(err.toString());
/*  237:     */   }
/*  238:     */   
/*  239:     */   public void setFeature(String name, boolean value)
/*  240:     */     throws TransformerConfigurationException
/*  241:     */   {
/*  242: 401 */     if (name == null)
/*  243:     */     {
/*  244: 402 */       ErrorMsg err = new ErrorMsg("JAXP_SET_FEATURE_NULL_NAME");
/*  245: 403 */       throw new NullPointerException(err.toString());
/*  246:     */     }
/*  247: 406 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing"))
/*  248:     */     {
/*  249: 407 */       this._isSecureProcessing = value;
/*  250:     */       
/*  251: 409 */       return;
/*  252:     */     }
/*  253: 413 */     ErrorMsg err = new ErrorMsg("JAXP_UNSUPPORTED_FEATURE", name);
/*  254: 414 */     throw new TransformerConfigurationException(err.toString());
/*  255:     */   }
/*  256:     */   
/*  257:     */   public boolean getFeature(String name)
/*  258:     */   {
/*  259: 429 */     String[] features = { "http://javax.xml.transform.dom.DOMSource/feature", "http://javax.xml.transform.dom.DOMResult/feature", "http://javax.xml.transform.sax.SAXSource/feature", "http://javax.xml.transform.sax.SAXResult/feature", "http://javax.xml.transform.stream.StreamSource/feature", "http://javax.xml.transform.stream.StreamResult/feature", "http://javax.xml.transform.sax.SAXTransformerFactory/feature", "http://javax.xml.transform.sax.SAXTransformerFactory/feature/xmlfilter" };
/*  260: 441 */     if (name == null)
/*  261:     */     {
/*  262: 442 */       ErrorMsg err = new ErrorMsg("JAXP_GET_FEATURE_NULL_NAME");
/*  263: 443 */       throw new NullPointerException(err.toString());
/*  264:     */     }
/*  265: 447 */     for (int i = 0; i < features.length; i++) {
/*  266: 448 */       if (name.equals(features[i])) {
/*  267: 449 */         return true;
/*  268:     */       }
/*  269:     */     }
/*  270: 453 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*  271: 454 */       return this._isSecureProcessing;
/*  272:     */     }
/*  273: 458 */     return false;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public URIResolver getURIResolver()
/*  277:     */   {
/*  278: 470 */     return this._uriResolver;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void setURIResolver(URIResolver resolver)
/*  282:     */   {
/*  283: 484 */     this._uriResolver = resolver;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public Source getAssociatedStylesheet(Source source, String media, String title, String charset)
/*  287:     */     throws TransformerConfigurationException
/*  288:     */   {
/*  289: 507 */     XMLReader reader = null;
/*  290: 508 */     InputSource isource = null;
/*  291:     */     
/*  292:     */ 
/*  293:     */ 
/*  294:     */ 
/*  295:     */ 
/*  296: 514 */     StylesheetPIHandler _stylesheetPIHandler = new StylesheetPIHandler(null, media, title, charset);
/*  297:     */     try
/*  298:     */     {
/*  299:     */       String baseId;
/*  300: 518 */       if ((source instanceof DOMSource))
/*  301:     */       {
/*  302: 519 */         DOMSource domsrc = (DOMSource)source;
/*  303: 520 */         baseId = domsrc.getSystemId();
/*  304: 521 */         Node node = domsrc.getNode();
/*  305: 522 */         DOM2SAX dom2sax = new DOM2SAX(node);
/*  306:     */         
/*  307: 524 */         _stylesheetPIHandler.setBaseId(baseId);
/*  308:     */         
/*  309: 526 */         dom2sax.setContentHandler(_stylesheetPIHandler);
/*  310: 527 */         dom2sax.parse();
/*  311:     */       }
/*  312:     */       else
/*  313:     */       {
/*  314: 529 */         isource = SAXSource.sourceToInputSource(source);
/*  315: 530 */         baseId = isource.getSystemId();
/*  316:     */         
/*  317: 532 */         SAXParserFactory factory = SAXParserFactory.newInstance();
/*  318: 533 */         factory.setNamespaceAware(true);
/*  319: 535 */         if (this._isSecureProcessing) {
/*  320:     */           try
/*  321:     */           {
/*  322: 537 */             factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  323:     */           }
/*  324:     */           catch (SAXException e) {}
/*  325:     */         }
/*  326: 542 */         SAXParser jaxpParser = factory.newSAXParser();
/*  327:     */         
/*  328: 544 */         reader = jaxpParser.getXMLReader();
/*  329: 545 */         if (reader == null) {
/*  330: 546 */           reader = XMLReaderFactory.createXMLReader();
/*  331:     */         }
/*  332: 549 */         _stylesheetPIHandler.setBaseId(baseId);
/*  333: 550 */         reader.setContentHandler(_stylesheetPIHandler);
/*  334: 551 */         reader.parse(isource);
/*  335:     */       }
/*  336: 555 */       if (this._uriResolver != null) {
/*  337: 556 */         _stylesheetPIHandler.setURIResolver(this._uriResolver);
/*  338:     */       }
/*  339:     */     }
/*  340:     */     catch (StopParseException e) {}catch (ParserConfigurationException e)
/*  341:     */     {
/*  342: 564 */       throw new TransformerConfigurationException("getAssociatedStylesheets failed", e);
/*  343:     */     }
/*  344:     */     catch (SAXException se)
/*  345:     */     {
/*  346: 569 */       throw new TransformerConfigurationException("getAssociatedStylesheets failed", se);
/*  347:     */     }
/*  348:     */     catch (IOException ioe)
/*  349:     */     {
/*  350: 574 */       throw new TransformerConfigurationException("getAssociatedStylesheets failed", ioe);
/*  351:     */     }
/*  352: 579 */     return _stylesheetPIHandler.getAssociatedStylesheet();
/*  353:     */   }
/*  354:     */   
/*  355:     */   public Transformer newTransformer()
/*  356:     */     throws TransformerConfigurationException
/*  357:     */   {
/*  358: 593 */     TransformerImpl result = new TransformerImpl(new Properties(), this._indentNumber, this);
/*  359: 595 */     if (this._uriResolver != null) {
/*  360: 596 */       result.setURIResolver(this._uriResolver);
/*  361:     */     }
/*  362: 599 */     if (this._isSecureProcessing) {
/*  363: 600 */       result.setSecureProcessing(true);
/*  364:     */     }
/*  365: 602 */     return result;
/*  366:     */   }
/*  367:     */   
/*  368:     */   public Transformer newTransformer(Source source)
/*  369:     */     throws TransformerConfigurationException
/*  370:     */   {
/*  371: 618 */     Templates templates = newTemplates(source);
/*  372: 619 */     Transformer transformer = templates.newTransformer();
/*  373: 620 */     if (this._uriResolver != null) {
/*  374: 621 */       transformer.setURIResolver(this._uriResolver);
/*  375:     */     }
/*  376: 623 */     return transformer;
/*  377:     */   }
/*  378:     */   
/*  379:     */   private void passWarningsToListener(Vector messages)
/*  380:     */     throws TransformerException
/*  381:     */   {
/*  382: 632 */     if ((this._errorListener == null) || (messages == null)) {
/*  383: 633 */       return;
/*  384:     */     }
/*  385: 636 */     int count = messages.size();
/*  386: 637 */     for (int pos = 0; pos < count; pos++)
/*  387:     */     {
/*  388: 638 */       ErrorMsg msg = (ErrorMsg)messages.elementAt(pos);
/*  389: 640 */       if (msg.isWarningError()) {
/*  390: 641 */         this._errorListener.error(new TransformerConfigurationException(msg.toString()));
/*  391:     */       } else {
/*  392: 644 */         this._errorListener.warning(new TransformerConfigurationException(msg.toString()));
/*  393:     */       }
/*  394:     */     }
/*  395:     */   }
/*  396:     */   
/*  397:     */   private void passErrorsToListener(Vector messages)
/*  398:     */   {
/*  399:     */     try
/*  400:     */     {
/*  401: 654 */       if ((this._errorListener == null) || (messages == null)) {
/*  402: 655 */         return;
/*  403:     */       }
/*  404: 658 */       int count = messages.size();
/*  405: 659 */       for (int pos = 0; pos < count; pos++)
/*  406:     */       {
/*  407: 660 */         String message = messages.elementAt(pos).toString();
/*  408: 661 */         this._errorListener.error(new TransformerException(message));
/*  409:     */       }
/*  410:     */     }
/*  411:     */     catch (TransformerException e) {}
/*  412:     */   }
/*  413:     */   
/*  414:     */   public Templates newTemplates(Source source)
/*  415:     */     throws TransformerConfigurationException
/*  416:     */   {
/*  417: 684 */     if (this._useClasspath)
/*  418:     */     {
/*  419: 685 */       String transletName = getTransletBaseName(source);
/*  420: 687 */       if (this._packageName != null) {
/*  421: 688 */         transletName = this._packageName + "." + transletName;
/*  422:     */       }
/*  423:     */       try
/*  424:     */       {
/*  425: 691 */         Class clazz = ObjectFactory.findProviderClass(transletName, ObjectFactory.findClassLoader(), true);
/*  426:     */         
/*  427: 693 */         resetTransientAttributes();
/*  428:     */         
/*  429: 695 */         return new TemplatesImpl(new Class[] { clazz }, transletName, null, this._indentNumber, this);
/*  430:     */       }
/*  431:     */       catch (ClassNotFoundException cnfe)
/*  432:     */       {
/*  433: 698 */         ErrorMsg err = new ErrorMsg("CLASS_NOT_FOUND_ERR", transletName);
/*  434: 699 */         throw new TransformerConfigurationException(err.toString());
/*  435:     */       }
/*  436:     */       catch (Exception e)
/*  437:     */       {
/*  438: 702 */         ErrorMsg err = new ErrorMsg(new ErrorMsg("RUNTIME_ERROR_KEY") + e.getMessage());
/*  439:     */         
/*  440:     */ 
/*  441: 705 */         throw new TransformerConfigurationException(err.toString());
/*  442:     */       }
/*  443:     */     }
/*  444: 711 */     if (this._autoTranslet)
/*  445:     */     {
/*  446: 712 */       byte[][] bytecodes = null;
/*  447: 713 */       String transletClassName = getTransletBaseName(source);
/*  448: 715 */       if (this._packageName != null) {
/*  449: 716 */         transletClassName = this._packageName + "." + transletClassName;
/*  450:     */       }
/*  451: 718 */       if (this._jarFileName != null) {
/*  452: 719 */         bytecodes = getBytecodesFromJar(source, transletClassName);
/*  453:     */       } else {
/*  454: 721 */         bytecodes = getBytecodesFromClasses(source, transletClassName);
/*  455:     */       }
/*  456: 723 */       if (bytecodes != null)
/*  457:     */       {
/*  458: 724 */         if (this._debug) {
/*  459: 725 */           if (this._jarFileName != null) {
/*  460: 726 */             System.err.println(new ErrorMsg("TRANSFORM_WITH_JAR_STR", transletClassName, this._jarFileName));
/*  461:     */           } else {
/*  462: 729 */             System.err.println(new ErrorMsg("TRANSFORM_WITH_TRANSLET_STR", transletClassName));
/*  463:     */           }
/*  464:     */         }
/*  465: 735 */         resetTransientAttributes();
/*  466:     */         
/*  467: 737 */         return new TemplatesImpl(bytecodes, transletClassName, null, this._indentNumber, this);
/*  468:     */       }
/*  469:     */     }
/*  470: 742 */     XSLTC xsltc = new XSLTC();
/*  471: 743 */     if (this._debug) {
/*  472: 743 */       xsltc.setDebug(true);
/*  473:     */     }
/*  474: 744 */     if (this._enableInlining) {
/*  475: 745 */       xsltc.setTemplateInlining(true);
/*  476:     */     } else {
/*  477: 747 */       xsltc.setTemplateInlining(false);
/*  478:     */     }
/*  479: 748 */     if (this._isSecureProcessing) {
/*  480: 748 */       xsltc.setSecureProcessing(true);
/*  481:     */     }
/*  482: 749 */     xsltc.init();
/*  483: 752 */     if (this._uriResolver != null) {
/*  484: 753 */       xsltc.setSourceLoader(this);
/*  485:     */     }
/*  486: 758 */     if ((this._piParams != null) && (this._piParams.get(source) != null))
/*  487:     */     {
/*  488: 760 */       PIParamWrapper p = (PIParamWrapper)this._piParams.get(source);
/*  489: 762 */       if (p != null) {
/*  490: 763 */         xsltc.setPIParameters(p._media, p._title, p._charset);
/*  491:     */       }
/*  492:     */     }
/*  493: 768 */     int outputType = 2;
/*  494: 769 */     if ((this._generateTranslet) || (this._autoTranslet))
/*  495:     */     {
/*  496: 771 */       xsltc.setClassName(getTransletBaseName(source));
/*  497: 773 */       if (this._destinationDirectory != null)
/*  498:     */       {
/*  499: 774 */         xsltc.setDestDirectory(this._destinationDirectory);
/*  500:     */       }
/*  501:     */       else
/*  502:     */       {
/*  503: 776 */         String xslName = getStylesheetFileName(source);
/*  504: 777 */         if (xslName != null)
/*  505:     */         {
/*  506: 778 */           File xslFile = new File(xslName);
/*  507: 779 */           String xslDir = xslFile.getParent();
/*  508: 781 */           if (xslDir != null) {
/*  509: 782 */             xsltc.setDestDirectory(xslDir);
/*  510:     */           }
/*  511:     */         }
/*  512:     */       }
/*  513: 786 */       if (this._packageName != null) {
/*  514: 787 */         xsltc.setPackageName(this._packageName);
/*  515:     */       }
/*  516: 789 */       if (this._jarFileName != null)
/*  517:     */       {
/*  518: 790 */         xsltc.setJarFileName(this._jarFileName);
/*  519: 791 */         outputType = 5;
/*  520:     */       }
/*  521:     */       else
/*  522:     */       {
/*  523: 794 */         outputType = 4;
/*  524:     */       }
/*  525:     */     }
/*  526: 798 */     InputSource input = Util.getInputSource(xsltc, source);
/*  527: 799 */     byte[][] bytecodes = xsltc.compile(null, input, outputType);
/*  528: 800 */     String transletName = xsltc.getClassName();
/*  529: 803 */     if (((this._generateTranslet) || (this._autoTranslet)) && (bytecodes != null) && (this._jarFileName != null)) {
/*  530:     */       try
/*  531:     */       {
/*  532: 806 */         xsltc.outputToJar();
/*  533:     */       }
/*  534:     */       catch (IOException e) {}
/*  535:     */     }
/*  536: 813 */     resetTransientAttributes();
/*  537: 816 */     if (this._errorListener != this) {
/*  538:     */       try
/*  539:     */       {
/*  540: 818 */         passWarningsToListener(xsltc.getWarnings());
/*  541:     */       }
/*  542:     */       catch (TransformerException e)
/*  543:     */       {
/*  544: 821 */         throw new TransformerConfigurationException(e);
/*  545:     */       }
/*  546:     */     } else {
/*  547: 825 */       xsltc.printWarnings();
/*  548:     */     }
/*  549: 829 */     if (bytecodes == null)
/*  550:     */     {
/*  551: 831 */       ErrorMsg err = new ErrorMsg("JAXP_COMPILE_ERR");
/*  552: 832 */       TransformerConfigurationException exc = new TransformerConfigurationException(err.toString());
/*  553: 835 */       if (this._errorListener != null)
/*  554:     */       {
/*  555: 836 */         passErrorsToListener(xsltc.getErrors());
/*  556:     */         try
/*  557:     */         {
/*  558: 842 */           this._errorListener.fatalError(exc);
/*  559:     */         }
/*  560:     */         catch (TransformerException te) {}
/*  561:     */       }
/*  562:     */       else
/*  563:     */       {
/*  564: 848 */         xsltc.printErrors();
/*  565:     */       }
/*  566: 850 */       throw exc;
/*  567:     */     }
/*  568: 853 */     return new TemplatesImpl(bytecodes, transletName, xsltc.getOutputProperties(), this._indentNumber, this);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public TemplatesHandler newTemplatesHandler()
/*  572:     */     throws TransformerConfigurationException
/*  573:     */   {
/*  574: 868 */     TemplatesHandlerImpl handler = new TemplatesHandlerImpl(this._indentNumber, this);
/*  575: 870 */     if (this._uriResolver != null) {
/*  576: 871 */       handler.setURIResolver(this._uriResolver);
/*  577:     */     }
/*  578: 873 */     return handler;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public TransformerHandler newTransformerHandler()
/*  582:     */     throws TransformerConfigurationException
/*  583:     */   {
/*  584: 887 */     Transformer transformer = newTransformer();
/*  585: 888 */     if (this._uriResolver != null) {
/*  586: 889 */       transformer.setURIResolver(this._uriResolver);
/*  587:     */     }
/*  588: 891 */     return new TransformerHandlerImpl((TransformerImpl)transformer);
/*  589:     */   }
/*  590:     */   
/*  591:     */   public TransformerHandler newTransformerHandler(Source src)
/*  592:     */     throws TransformerConfigurationException
/*  593:     */   {
/*  594: 907 */     Transformer transformer = newTransformer(src);
/*  595: 908 */     if (this._uriResolver != null) {
/*  596: 909 */       transformer.setURIResolver(this._uriResolver);
/*  597:     */     }
/*  598: 911 */     return new TransformerHandlerImpl((TransformerImpl)transformer);
/*  599:     */   }
/*  600:     */   
/*  601:     */   public TransformerHandler newTransformerHandler(Templates templates)
/*  602:     */     throws TransformerConfigurationException
/*  603:     */   {
/*  604: 927 */     Transformer transformer = templates.newTransformer();
/*  605: 928 */     TransformerImpl internal = (TransformerImpl)transformer;
/*  606: 929 */     return new TransformerHandlerImpl(internal);
/*  607:     */   }
/*  608:     */   
/*  609:     */   public XMLFilter newXMLFilter(Source src)
/*  610:     */     throws TransformerConfigurationException
/*  611:     */   {
/*  612: 944 */     Templates templates = newTemplates(src);
/*  613: 945 */     if (templates == null) {
/*  614: 945 */       return null;
/*  615:     */     }
/*  616: 946 */     return newXMLFilter(templates);
/*  617:     */   }
/*  618:     */   
/*  619:     */   public XMLFilter newXMLFilter(Templates templates)
/*  620:     */     throws TransformerConfigurationException
/*  621:     */   {
/*  622:     */     try
/*  623:     */     {
/*  624: 962 */       return new TrAXFilter(templates);
/*  625:     */     }
/*  626:     */     catch (TransformerConfigurationException e1)
/*  627:     */     {
/*  628: 965 */       if (this._errorListener != null) {
/*  629:     */         try
/*  630:     */         {
/*  631: 967 */           this._errorListener.fatalError(e1);
/*  632: 968 */           return null;
/*  633:     */         }
/*  634:     */         catch (TransformerException e2)
/*  635:     */         {
/*  636: 971 */           new TransformerConfigurationException(e2);
/*  637:     */         }
/*  638:     */       }
/*  639: 974 */       throw e1;
/*  640:     */     }
/*  641:     */   }
/*  642:     */   
/*  643:     */   public void error(TransformerException e)
/*  644:     */     throws TransformerException
/*  645:     */   {
/*  646: 992 */     Throwable wrapped = e.getException();
/*  647: 993 */     if (wrapped != null) {
/*  648: 994 */       System.err.println(new ErrorMsg("ERROR_PLUS_WRAPPED_MSG", e.getMessageAndLocation(), wrapped.getMessage()));
/*  649:     */     } else {
/*  650: 998 */       System.err.println(new ErrorMsg("ERROR_MSG", e.getMessageAndLocation()));
/*  651:     */     }
/*  652:1001 */     throw e;
/*  653:     */   }
/*  654:     */   
/*  655:     */   public void fatalError(TransformerException e)
/*  656:     */     throws TransformerException
/*  657:     */   {
/*  658:1020 */     Throwable wrapped = e.getException();
/*  659:1021 */     if (wrapped != null) {
/*  660:1022 */       System.err.println(new ErrorMsg("FATAL_ERR_PLUS_WRAPPED_MSG", e.getMessageAndLocation(), wrapped.getMessage()));
/*  661:     */     } else {
/*  662:1026 */       System.err.println(new ErrorMsg("FATAL_ERR_MSG", e.getMessageAndLocation()));
/*  663:     */     }
/*  664:1029 */     throw e;
/*  665:     */   }
/*  666:     */   
/*  667:     */   public void warning(TransformerException e)
/*  668:     */     throws TransformerException
/*  669:     */   {
/*  670:1048 */     Throwable wrapped = e.getException();
/*  671:1049 */     if (wrapped != null) {
/*  672:1050 */       System.err.println(new ErrorMsg("WARNING_PLUS_WRAPPED_MSG", e.getMessageAndLocation(), wrapped.getMessage()));
/*  673:     */     } else {
/*  674:1054 */       System.err.println(new ErrorMsg("WARNING_MSG", e.getMessageAndLocation()));
/*  675:     */     }
/*  676:     */   }
/*  677:     */   
/*  678:     */   public InputSource loadSource(String href, String context, XSLTC xsltc)
/*  679:     */   {
/*  680:     */     try
/*  681:     */     {
/*  682:1070 */       if (this._uriResolver != null)
/*  683:     */       {
/*  684:1071 */         Source source = this._uriResolver.resolve(href, context);
/*  685:1072 */         if (source != null) {
/*  686:1073 */           return Util.getInputSource(xsltc, source);
/*  687:     */         }
/*  688:     */       }
/*  689:     */     }
/*  690:     */     catch (TransformerException e) {}
/*  691:1080 */     return null;
/*  692:     */   }
/*  693:     */   
/*  694:     */   private void resetTransientAttributes()
/*  695:     */   {
/*  696:1087 */     this._transletName = "GregorSamsa";
/*  697:1088 */     this._destinationDirectory = null;
/*  698:1089 */     this._packageName = null;
/*  699:1090 */     this._jarFileName = null;
/*  700:     */   }
/*  701:     */   
/*  702:     */   private byte[][] getBytecodesFromClasses(Source source, String fullClassName)
/*  703:     */   {
/*  704:1103 */     if (fullClassName == null) {
/*  705:1104 */       return null;
/*  706:     */     }
/*  707:1106 */     String xslFileName = getStylesheetFileName(source);
/*  708:1107 */     File xslFile = null;
/*  709:1108 */     if (xslFileName != null) {
/*  710:1109 */       xslFile = new File(xslFileName);
/*  711:     */     }
/*  712:1113 */     int lastDotIndex = fullClassName.lastIndexOf('.');
/*  713:     */     String transletName;
/*  714:1114 */     if (lastDotIndex > 0) {
/*  715:1115 */       transletName = fullClassName.substring(lastDotIndex + 1);
/*  716:     */     } else {
/*  717:1117 */       transletName = fullClassName;
/*  718:     */     }
/*  719:1120 */     String transletPath = fullClassName.replace('.', '/');
/*  720:1121 */     if (this._destinationDirectory != null) {
/*  721:1122 */       transletPath = this._destinationDirectory + "/" + transletPath + ".class";
/*  722:1125 */     } else if ((xslFile != null) && (xslFile.getParent() != null)) {
/*  723:1126 */       transletPath = xslFile.getParent() + "/" + transletPath + ".class";
/*  724:     */     } else {
/*  725:1128 */       transletPath = transletPath + ".class";
/*  726:     */     }
/*  727:1132 */     File transletFile = new File(transletPath);
/*  728:1133 */     if (!transletFile.exists()) {
/*  729:1134 */       return null;
/*  730:     */     }
/*  731:1140 */     if ((xslFile != null) && (xslFile.exists()))
/*  732:     */     {
/*  733:1141 */       long xslTimestamp = xslFile.lastModified();
/*  734:1142 */       long transletTimestamp = transletFile.lastModified();
/*  735:1143 */       if (transletTimestamp < xslTimestamp) {
/*  736:1144 */         return null;
/*  737:     */       }
/*  738:     */     }
/*  739:1148 */     List bytecodes = new ArrayList();
/*  740:1149 */     int fileLength = (int)transletFile.length();
/*  741:1150 */     if (fileLength > 0)
/*  742:     */     {
/*  743:1151 */       FileInputStream input = null;
/*  744:     */       try
/*  745:     */       {
/*  746:1153 */         input = new FileInputStream(transletFile);
/*  747:     */       }
/*  748:     */       catch (FileNotFoundException e)
/*  749:     */       {
/*  750:1156 */         return null;
/*  751:     */       }
/*  752:1159 */       byte[] bytes = new byte[fileLength];
/*  753:     */       try
/*  754:     */       {
/*  755:1161 */         readFromInputStream(bytes, input, fileLength);
/*  756:1162 */         input.close();
/*  757:     */       }
/*  758:     */       catch (IOException e)
/*  759:     */       {
/*  760:1165 */         return null;
/*  761:     */       }
/*  762:1168 */       bytecodes.add(bytes);
/*  763:     */     }
/*  764:     */     else
/*  765:     */     {
/*  766:1171 */       return null;
/*  767:     */     }
/*  768:1174 */     String transletParentDir = transletFile.getParent();
/*  769:1175 */     if (transletParentDir == null) {
/*  770:1176 */       transletParentDir = System.getProperty("user.dir");
/*  771:     */     }
/*  772:1178 */     File transletParentFile = new File(transletParentDir);
/*  773:     */     
/*  774:     */ 
/*  775:1181 */     String transletAuxPrefix = transletName + "$";
/*  776:1182 */     File[] auxfiles = transletParentFile.listFiles(new FilenameFilter()
/*  777:     */     {
/*  778:     */       private final String val$transletAuxPrefix;
/*  779:     */       
/*  780:     */       public boolean accept(File dir, String name)
/*  781:     */       {
/*  782:1185 */         return (name.endsWith(".class")) && (name.startsWith(this.val$transletAuxPrefix));
/*  783:     */       }
/*  784:     */     });
/*  785:1190 */     for (int i = 0; i < auxfiles.length; i++)
/*  786:     */     {
/*  787:1192 */       File auxfile = auxfiles[i];
/*  788:1193 */       int auxlength = (int)auxfile.length();
/*  789:1194 */       if (auxlength > 0)
/*  790:     */       {
/*  791:1195 */         FileInputStream auxinput = null;
/*  792:     */         try
/*  793:     */         {
/*  794:1197 */           auxinput = new FileInputStream(auxfile);
/*  795:     */         }
/*  796:     */         catch (FileNotFoundException e)
/*  797:     */         {
/*  798:     */           continue;
/*  799:     */         }
/*  800:1203 */         byte[] bytes = new byte[auxlength];
/*  801:     */         try
/*  802:     */         {
/*  803:1206 */           readFromInputStream(bytes, auxinput, auxlength);
/*  804:1207 */           auxinput.close();
/*  805:     */         }
/*  806:     */         catch (IOException e)
/*  807:     */         {
/*  808:     */           continue;
/*  809:     */         }
/*  810:1213 */         bytecodes.add(bytes);
/*  811:     */       }
/*  812:     */     }
/*  813:1218 */     int count = bytecodes.size();
/*  814:1219 */     if (count > 0)
/*  815:     */     {
/*  816:1220 */       byte[][] result = new byte[count][1];
/*  817:1221 */       for (int i = 0; i < count; i++) {
/*  818:1222 */         result[i] = ((byte[])bytecodes.get(i));
/*  819:     */       }
/*  820:1225 */       return result;
/*  821:     */     }
/*  822:1228 */     return null;
/*  823:     */   }
/*  824:     */   
/*  825:     */   private byte[][] getBytecodesFromJar(Source source, String fullClassName)
/*  826:     */   {
/*  827:1240 */     String xslFileName = getStylesheetFileName(source);
/*  828:1241 */     File xslFile = null;
/*  829:1242 */     if (xslFileName != null) {
/*  830:1243 */       xslFile = new File(xslFileName);
/*  831:     */     }
/*  832:1246 */     String jarPath = null;
/*  833:1247 */     if (this._destinationDirectory != null) {
/*  834:1248 */       jarPath = this._destinationDirectory + "/" + this._jarFileName;
/*  835:1250 */     } else if ((xslFile != null) && (xslFile.getParent() != null)) {
/*  836:1251 */       jarPath = xslFile.getParent() + "/" + this._jarFileName;
/*  837:     */     } else {
/*  838:1253 */       jarPath = this._jarFileName;
/*  839:     */     }
/*  840:1257 */     File file = new File(jarPath);
/*  841:1258 */     if (!file.exists()) {
/*  842:1259 */       return null;
/*  843:     */     }
/*  844:1263 */     if ((xslFile != null) && (xslFile.exists()))
/*  845:     */     {
/*  846:1264 */       long xslTimestamp = xslFile.lastModified();
/*  847:1265 */       long transletTimestamp = file.lastModified();
/*  848:1266 */       if (transletTimestamp < xslTimestamp) {
/*  849:1267 */         return null;
/*  850:     */       }
/*  851:     */     }
/*  852:1271 */     ZipFile jarFile = null;
/*  853:     */     try
/*  854:     */     {
/*  855:1273 */       jarFile = new ZipFile(file);
/*  856:     */     }
/*  857:     */     catch (IOException e)
/*  858:     */     {
/*  859:1276 */       return null;
/*  860:     */     }
/*  861:1279 */     String transletPath = fullClassName.replace('.', '/');
/*  862:1280 */     String transletAuxPrefix = transletPath + "$";
/*  863:1281 */     String transletFullName = transletPath + ".class";
/*  864:     */     
/*  865:1283 */     List bytecodes = new ArrayList();
/*  866:     */     
/*  867:     */ 
/*  868:     */ 
/*  869:1287 */     Enumeration entries = jarFile.entries();
/*  870:1288 */     while (entries.hasMoreElements())
/*  871:     */     {
/*  872:1290 */       ZipEntry entry = (ZipEntry)entries.nextElement();
/*  873:1291 */       String entryName = entry.getName();
/*  874:1292 */       if ((entry.getSize() > 0L) && ((entryName.equals(transletFullName)) || ((entryName.endsWith(".class")) && (entryName.startsWith(transletAuxPrefix))))) {
/*  875:     */         try
/*  876:     */         {
/*  877:1298 */           InputStream input = jarFile.getInputStream(entry);
/*  878:1299 */           int size = (int)entry.getSize();
/*  879:1300 */           byte[] bytes = new byte[size];
/*  880:1301 */           readFromInputStream(bytes, input, size);
/*  881:1302 */           input.close();
/*  882:1303 */           bytecodes.add(bytes);
/*  883:     */         }
/*  884:     */         catch (IOException e)
/*  885:     */         {
/*  886:1306 */           return null;
/*  887:     */         }
/*  888:     */       }
/*  889:     */     }
/*  890:1312 */     int count = bytecodes.size();
/*  891:1313 */     if (count > 0)
/*  892:     */     {
/*  893:1314 */       byte[][] result = new byte[count][1];
/*  894:1315 */       for (int i = 0; i < count; i++) {
/*  895:1316 */         result[i] = ((byte[])bytecodes.get(i));
/*  896:     */       }
/*  897:1319 */       return result;
/*  898:     */     }
/*  899:1322 */     return null;
/*  900:     */   }
/*  901:     */   
/*  902:     */   private void readFromInputStream(byte[] bytes, InputStream input, int size)
/*  903:     */     throws IOException
/*  904:     */   {
/*  905:1335 */     int n = 0;
/*  906:1336 */     int offset = 0;
/*  907:1337 */     int length = size;
/*  908:1338 */     while ((length > 0) && ((n = input.read(bytes, offset, length)) > 0))
/*  909:     */     {
/*  910:1339 */       offset += n;
/*  911:1340 */       length -= n;
/*  912:     */     }
/*  913:     */   }
/*  914:     */   
/*  915:     */   private String getTransletBaseName(Source source)
/*  916:     */   {
/*  917:1357 */     String transletBaseName = null;
/*  918:1358 */     if (!this._transletName.equals("GregorSamsa")) {
/*  919:1359 */       return this._transletName;
/*  920:     */     }
/*  921:1361 */     String systemId = source.getSystemId();
/*  922:1362 */     if (systemId != null)
/*  923:     */     {
/*  924:1363 */       String baseName = Util.baseName(systemId);
/*  925:1364 */       if (baseName != null)
/*  926:     */       {
/*  927:1365 */         baseName = Util.noExtName(baseName);
/*  928:1366 */         transletBaseName = Util.toJavaName(baseName);
/*  929:     */       }
/*  930:     */     }
/*  931:1371 */     return transletBaseName != null ? transletBaseName : "GregorSamsa";
/*  932:     */   }
/*  933:     */   
/*  934:     */   private String getStylesheetFileName(Source source)
/*  935:     */   {
/*  936:1383 */     String systemId = source.getSystemId();
/*  937:1384 */     if (systemId != null)
/*  938:     */     {
/*  939:1385 */       File file = new File(systemId);
/*  940:1386 */       if (file.exists()) {
/*  941:1387 */         return systemId;
/*  942:     */       }
/*  943:1389 */       URL url = null;
/*  944:     */       try
/*  945:     */       {
/*  946:1391 */         url = new URL(systemId);
/*  947:     */       }
/*  948:     */       catch (MalformedURLException e)
/*  949:     */       {
/*  950:1394 */         return null;
/*  951:     */       }
/*  952:1397 */       if ("file".equals(url.getProtocol())) {
/*  953:1398 */         return url.getFile();
/*  954:     */       }
/*  955:1400 */       return null;
/*  956:     */     }
/*  957:1404 */     return null;
/*  958:     */   }
/*  959:     */   
/*  960:     */   protected Class getDTMManagerClass()
/*  961:     */   {
/*  962:1411 */     return this.m_DTMManagerClass;
/*  963:     */   }
/*  964:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.TransformerFactoryImpl
 * JD-Core Version:    0.7.0.1
 */