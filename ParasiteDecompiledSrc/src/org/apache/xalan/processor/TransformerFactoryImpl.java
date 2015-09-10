/*    1:     */ package org.apache.xalan.processor;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import javax.xml.parsers.FactoryConfigurationError;
/*    5:     */ import javax.xml.parsers.ParserConfigurationException;
/*    6:     */ import javax.xml.parsers.SAXParser;
/*    7:     */ import javax.xml.parsers.SAXParserFactory;
/*    8:     */ import javax.xml.transform.ErrorListener;
/*    9:     */ import javax.xml.transform.Source;
/*   10:     */ import javax.xml.transform.Templates;
/*   11:     */ import javax.xml.transform.Transformer;
/*   12:     */ import javax.xml.transform.TransformerConfigurationException;
/*   13:     */ import javax.xml.transform.TransformerException;
/*   14:     */ import javax.xml.transform.URIResolver;
/*   15:     */ import javax.xml.transform.dom.DOMSource;
/*   16:     */ import javax.xml.transform.sax.SAXSource;
/*   17:     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*   18:     */ import javax.xml.transform.sax.TemplatesHandler;
/*   19:     */ import javax.xml.transform.sax.TransformerHandler;
/*   20:     */ import org.apache.xalan.res.XSLMessages;
/*   21:     */ import org.apache.xalan.transformer.TrAXFilter;
/*   22:     */ import org.apache.xalan.transformer.TransformerIdentityImpl;
/*   23:     */ import org.apache.xalan.transformer.TransformerImpl;
/*   24:     */ import org.apache.xml.utils.DOM2Helper;
/*   25:     */ import org.apache.xml.utils.DefaultErrorHandler;
/*   26:     */ import org.apache.xml.utils.StopParseException;
/*   27:     */ import org.apache.xml.utils.StylesheetPIHandler;
/*   28:     */ import org.apache.xml.utils.SystemIDResolver;
/*   29:     */ import org.apache.xml.utils.TreeWalker;
/*   30:     */ import org.w3c.dom.Node;
/*   31:     */ import org.xml.sax.InputSource;
/*   32:     */ import org.xml.sax.SAXException;
/*   33:     */ import org.xml.sax.XMLFilter;
/*   34:     */ import org.xml.sax.XMLReader;
/*   35:     */ import org.xml.sax.helpers.XMLReaderFactory;
/*   36:     */ 
/*   37:     */ public class TransformerFactoryImpl
/*   38:     */   extends SAXTransformerFactory
/*   39:     */ {
/*   40:     */   public static final String XSLT_PROPERTIES = "org/apache/xalan/res/XSLTInfo.properties";
/*   41:  86 */   private boolean m_isSecureProcessing = false;
/*   42:     */   public static final String FEATURE_INCREMENTAL = "http://xml.apache.org/xalan/features/incremental";
/*   43:     */   public static final String FEATURE_OPTIMIZE = "http://xml.apache.org/xalan/features/optimize";
/*   44:     */   public static final String FEATURE_SOURCE_LOCATION = "http://xml.apache.org/xalan/properties/source-location";
/*   45:     */   
/*   46:     */   public Templates processFromNode(Node node)
/*   47:     */     throws TransformerConfigurationException
/*   48:     */   {
/*   49:     */     try
/*   50:     */     {
/*   51: 114 */       TemplatesHandler builder = newTemplatesHandler();
/*   52: 115 */       TreeWalker walker = new TreeWalker(builder, new DOM2Helper(), builder.getSystemId());
/*   53:     */       
/*   54:     */ 
/*   55:     */ 
/*   56: 119 */       walker.traverse(node);
/*   57:     */       
/*   58: 121 */       return builder.getTemplates();
/*   59:     */     }
/*   60:     */     catch (SAXException se)
/*   61:     */     {
/*   62: 125 */       if (this.m_errorListener != null)
/*   63:     */       {
/*   64:     */         try
/*   65:     */         {
/*   66: 129 */           this.m_errorListener.fatalError(new TransformerException(se));
/*   67:     */         }
/*   68:     */         catch (TransformerConfigurationException ex)
/*   69:     */         {
/*   70: 133 */           throw ex;
/*   71:     */         }
/*   72:     */         catch (TransformerException ex)
/*   73:     */         {
/*   74: 137 */           throw new TransformerConfigurationException(ex);
/*   75:     */         }
/*   76: 140 */         return null;
/*   77:     */       }
/*   78: 148 */       throw new TransformerConfigurationException(XSLMessages.createMessage("ER_PROCESSFROMNODE_FAILED", null), se);
/*   79:     */     }
/*   80:     */     catch (TransformerConfigurationException tce)
/*   81:     */     {
/*   82: 155 */       throw tce;
/*   83:     */     }
/*   84:     */     catch (Exception e)
/*   85:     */     {
/*   86: 164 */       if (this.m_errorListener != null)
/*   87:     */       {
/*   88:     */         try
/*   89:     */         {
/*   90: 168 */           this.m_errorListener.fatalError(new TransformerException(e));
/*   91:     */         }
/*   92:     */         catch (TransformerConfigurationException ex)
/*   93:     */         {
/*   94: 172 */           throw ex;
/*   95:     */         }
/*   96:     */         catch (TransformerException ex)
/*   97:     */         {
/*   98: 176 */           throw new TransformerConfigurationException(ex);
/*   99:     */         }
/*  100: 179 */         return null;
/*  101:     */       }
/*  102: 186 */       throw new TransformerConfigurationException(XSLMessages.createMessage("ER_PROCESSFROMNODE_FAILED", null), e);
/*  103:     */     }
/*  104:     */   }
/*  105:     */   
/*  106: 196 */   private String m_DOMsystemID = null;
/*  107:     */   
/*  108:     */   String getDOMsystemID()
/*  109:     */   {
/*  110: 206 */     return this.m_DOMsystemID;
/*  111:     */   }
/*  112:     */   
/*  113:     */   Templates processFromNode(Node node, String systemID)
/*  114:     */     throws TransformerConfigurationException
/*  115:     */   {
/*  116: 227 */     this.m_DOMsystemID = systemID;
/*  117:     */     
/*  118: 229 */     return processFromNode(node);
/*  119:     */   }
/*  120:     */   
/*  121:     */   public Source getAssociatedStylesheet(Source source, String media, String title, String charset)
/*  122:     */     throws TransformerConfigurationException
/*  123:     */   {
/*  124: 261 */     InputSource isource = null;
/*  125: 262 */     Node node = null;
/*  126: 263 */     XMLReader reader = null;
/*  127:     */     String baseID;
/*  128: 265 */     if ((source instanceof DOMSource))
/*  129:     */     {
/*  130: 267 */       DOMSource dsource = (DOMSource)source;
/*  131:     */       
/*  132: 269 */       node = dsource.getNode();
/*  133: 270 */       baseID = dsource.getSystemId();
/*  134:     */     }
/*  135:     */     else
/*  136:     */     {
/*  137: 274 */       isource = SAXSource.sourceToInputSource(source);
/*  138: 275 */       baseID = isource.getSystemId();
/*  139:     */     }
/*  140: 281 */     StylesheetPIHandler handler = new StylesheetPIHandler(baseID, media, title, charset);
/*  141: 285 */     if (this.m_uriResolver != null) {
/*  142: 287 */       handler.setURIResolver(this.m_uriResolver);
/*  143:     */     }
/*  144:     */     try
/*  145:     */     {
/*  146: 292 */       if (null != node)
/*  147:     */       {
/*  148: 294 */         TreeWalker walker = new TreeWalker(handler, new DOM2Helper(), baseID);
/*  149:     */         
/*  150: 296 */         walker.traverse(node);
/*  151:     */       }
/*  152:     */       else
/*  153:     */       {
/*  154:     */         try
/*  155:     */         {
/*  156: 304 */           SAXParserFactory factory = SAXParserFactory.newInstance();
/*  157:     */           
/*  158:     */ 
/*  159: 307 */           factory.setNamespaceAware(true);
/*  160: 309 */           if (this.m_isSecureProcessing) {
/*  161:     */             try
/*  162:     */             {
/*  163: 313 */               factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  164:     */             }
/*  165:     */             catch (SAXException e) {}
/*  166:     */           }
/*  167: 318 */           SAXParser jaxpParser = factory.newSAXParser();
/*  168:     */           
/*  169: 320 */           reader = jaxpParser.getXMLReader();
/*  170:     */         }
/*  171:     */         catch (ParserConfigurationException ex)
/*  172:     */         {
/*  173: 324 */           throw new SAXException(ex);
/*  174:     */         }
/*  175:     */         catch (FactoryConfigurationError ex1)
/*  176:     */         {
/*  177: 328 */           throw new SAXException(ex1.toString());
/*  178:     */         }
/*  179:     */         catch (NoSuchMethodError ex2) {}catch (AbstractMethodError ame) {}
/*  180: 333 */         if (null == reader) {
/*  181: 335 */           reader = XMLReaderFactory.createXMLReader();
/*  182:     */         }
/*  183: 339 */         reader.setContentHandler(handler);
/*  184: 340 */         reader.parse(isource);
/*  185:     */       }
/*  186:     */     }
/*  187:     */     catch (StopParseException spe) {}catch (SAXException se)
/*  188:     */     {
/*  189: 350 */       throw new TransformerConfigurationException("getAssociatedStylesheets failed", se);
/*  190:     */     }
/*  191:     */     catch (IOException ioe)
/*  192:     */     {
/*  193: 355 */       throw new TransformerConfigurationException("getAssociatedStylesheets failed", ioe);
/*  194:     */     }
/*  195: 359 */     return handler.getAssociatedStylesheet();
/*  196:     */   }
/*  197:     */   
/*  198:     */   public TemplatesHandler newTemplatesHandler()
/*  199:     */     throws TransformerConfigurationException
/*  200:     */   {
/*  201: 376 */     return new StylesheetHandler(this);
/*  202:     */   }
/*  203:     */   
/*  204:     */   public void setFeature(String name, boolean value)
/*  205:     */     throws TransformerConfigurationException
/*  206:     */   {
/*  207: 404 */     if (name == null) {
/*  208: 405 */       throw new NullPointerException(XSLMessages.createMessage("ER_SET_FEATURE_NULL_NAME", null));
/*  209:     */     }
/*  210: 411 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*  211: 412 */       this.m_isSecureProcessing = value;
/*  212:     */     } else {
/*  213: 418 */       throw new TransformerConfigurationException(XSLMessages.createMessage("ER_UNSUPPORTED_FEATURE", new Object[] { name }));
/*  214:     */     }
/*  215:     */   }
/*  216:     */   
/*  217:     */   public boolean getFeature(String name)
/*  218:     */   {
/*  219: 440 */     if (name == null) {
/*  220: 442 */       throw new NullPointerException(XSLMessages.createMessage("ER_GET_FEATURE_NULL_NAME", null));
/*  221:     */     }
/*  222: 449 */     if (("http://javax.xml.transform.dom.DOMResult/feature" == name) || ("http://javax.xml.transform.dom.DOMSource/feature" == name) || ("http://javax.xml.transform.sax.SAXResult/feature" == name) || ("http://javax.xml.transform.sax.SAXSource/feature" == name) || ("http://javax.xml.transform.stream.StreamResult/feature" == name) || ("http://javax.xml.transform.stream.StreamSource/feature" == name) || ("http://javax.xml.transform.sax.SAXTransformerFactory/feature" == name) || ("http://javax.xml.transform.sax.SAXTransformerFactory/feature/xmlfilter" == name)) {
/*  223: 455 */       return true;
/*  224:     */     }
/*  225: 456 */     if (("http://javax.xml.transform.dom.DOMResult/feature".equals(name)) || ("http://javax.xml.transform.dom.DOMSource/feature".equals(name)) || ("http://javax.xml.transform.sax.SAXResult/feature".equals(name)) || ("http://javax.xml.transform.sax.SAXSource/feature".equals(name)) || ("http://javax.xml.transform.stream.StreamResult/feature".equals(name)) || ("http://javax.xml.transform.stream.StreamSource/feature".equals(name)) || ("http://javax.xml.transform.sax.SAXTransformerFactory/feature".equals(name)) || ("http://javax.xml.transform.sax.SAXTransformerFactory/feature/xmlfilter".equals(name))) {
/*  226: 464 */       return true;
/*  227:     */     }
/*  228: 466 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*  229: 467 */       return this.m_isSecureProcessing;
/*  230:     */     }
/*  231: 470 */     return false;
/*  232:     */   }
/*  233:     */   
/*  234: 478 */   private boolean m_optimize = true;
/*  235: 490 */   private boolean m_source_location = false;
/*  236: 498 */   private boolean m_incremental = false;
/*  237:     */   URIResolver m_uriResolver;
/*  238:     */   
/*  239:     */   public void setAttribute(String name, Object value)
/*  240:     */     throws IllegalArgumentException
/*  241:     */   {
/*  242: 513 */     if (name.equals("http://xml.apache.org/xalan/features/incremental"))
/*  243:     */     {
/*  244: 515 */       if ((value instanceof Boolean)) {
/*  245: 518 */         this.m_incremental = ((Boolean)value).booleanValue();
/*  246: 520 */       } else if ((value instanceof String)) {
/*  247: 523 */         this.m_incremental = new Boolean((String)value).booleanValue();
/*  248:     */       } else {
/*  249: 528 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_BAD_VALUE", new Object[] { name, value }));
/*  250:     */       }
/*  251:     */     }
/*  252: 531 */     else if (name.equals("http://xml.apache.org/xalan/features/optimize"))
/*  253:     */     {
/*  254: 533 */       if ((value instanceof Boolean)) {
/*  255: 536 */         this.m_optimize = ((Boolean)value).booleanValue();
/*  256: 538 */       } else if ((value instanceof String)) {
/*  257: 541 */         this.m_optimize = new Boolean((String)value).booleanValue();
/*  258:     */       } else {
/*  259: 546 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_BAD_VALUE", new Object[] { name, value }));
/*  260:     */       }
/*  261:     */     }
/*  262: 555 */     else if (name.equals("http://xml.apache.org/xalan/properties/source-location"))
/*  263:     */     {
/*  264: 557 */       if ((value instanceof Boolean)) {
/*  265: 560 */         this.m_source_location = ((Boolean)value).booleanValue();
/*  266: 562 */       } else if ((value instanceof String)) {
/*  267: 565 */         this.m_source_location = new Boolean((String)value).booleanValue();
/*  268:     */       } else {
/*  269: 570 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_BAD_VALUE", new Object[] { name, value }));
/*  270:     */       }
/*  271:     */     }
/*  272:     */     else {
/*  273: 576 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_NOT_SUPPORTED", new Object[] { name }));
/*  274:     */     }
/*  275:     */   }
/*  276:     */   
/*  277:     */   public Object getAttribute(String name)
/*  278:     */     throws IllegalArgumentException
/*  279:     */   {
/*  280: 592 */     if (name.equals("http://xml.apache.org/xalan/features/incremental")) {
/*  281: 594 */       return new Boolean(this.m_incremental);
/*  282:     */     }
/*  283: 596 */     if (name.equals("http://xml.apache.org/xalan/features/optimize")) {
/*  284: 598 */       return new Boolean(this.m_optimize);
/*  285:     */     }
/*  286: 600 */     if (name.equals("http://xml.apache.org/xalan/properties/source-location")) {
/*  287: 602 */       return new Boolean(this.m_source_location);
/*  288:     */     }
/*  289: 605 */     throw new IllegalArgumentException(XSLMessages.createMessage("ER_ATTRIB_VALUE_NOT_RECOGNIZED", new Object[] { name }));
/*  290:     */   }
/*  291:     */   
/*  292:     */   public XMLFilter newXMLFilter(Source src)
/*  293:     */     throws TransformerConfigurationException
/*  294:     */   {
/*  295: 622 */     Templates templates = newTemplates(src);
/*  296: 623 */     if (templates == null) {
/*  297: 623 */       return null;
/*  298:     */     }
/*  299: 625 */     return newXMLFilter(templates);
/*  300:     */   }
/*  301:     */   
/*  302:     */   public XMLFilter newXMLFilter(Templates templates)
/*  303:     */     throws TransformerConfigurationException
/*  304:     */   {
/*  305:     */     try
/*  306:     */     {
/*  307: 643 */       return new TrAXFilter(templates);
/*  308:     */     }
/*  309:     */     catch (TransformerConfigurationException ex)
/*  310:     */     {
/*  311: 647 */       if (this.m_errorListener != null) {
/*  312:     */         try
/*  313:     */         {
/*  314: 651 */           this.m_errorListener.fatalError(ex);
/*  315: 652 */           return null;
/*  316:     */         }
/*  317:     */         catch (TransformerConfigurationException ex1)
/*  318:     */         {
/*  319: 656 */           throw ex1;
/*  320:     */         }
/*  321:     */         catch (TransformerException ex1)
/*  322:     */         {
/*  323: 660 */           throw new TransformerConfigurationException(ex1);
/*  324:     */         }
/*  325:     */       }
/*  326: 663 */       throw ex;
/*  327:     */     }
/*  328:     */   }
/*  329:     */   
/*  330:     */   public TransformerHandler newTransformerHandler(Source src)
/*  331:     */     throws TransformerConfigurationException
/*  332:     */   {
/*  333: 682 */     Templates templates = newTemplates(src);
/*  334: 683 */     if (templates == null) {
/*  335: 683 */       return null;
/*  336:     */     }
/*  337: 685 */     return newTransformerHandler(templates);
/*  338:     */   }
/*  339:     */   
/*  340:     */   public TransformerHandler newTransformerHandler(Templates templates)
/*  341:     */     throws TransformerConfigurationException
/*  342:     */   {
/*  343:     */     try
/*  344:     */     {
/*  345: 701 */       TransformerImpl transformer = (TransformerImpl)templates.newTransformer();
/*  346:     */       
/*  347: 703 */       transformer.setURIResolver(this.m_uriResolver);
/*  348: 704 */       return (TransformerHandler)transformer.getInputContentHandler(true);
/*  349:     */     }
/*  350:     */     catch (TransformerConfigurationException ex)
/*  351:     */     {
/*  352: 711 */       if (this.m_errorListener != null) {
/*  353:     */         try
/*  354:     */         {
/*  355: 715 */           this.m_errorListener.fatalError(ex);
/*  356: 716 */           return null;
/*  357:     */         }
/*  358:     */         catch (TransformerConfigurationException ex1)
/*  359:     */         {
/*  360: 720 */           throw ex1;
/*  361:     */         }
/*  362:     */         catch (TransformerException ex1)
/*  363:     */         {
/*  364: 724 */           throw new TransformerConfigurationException(ex1);
/*  365:     */         }
/*  366:     */       }
/*  367: 728 */       throw ex;
/*  368:     */     }
/*  369:     */   }
/*  370:     */   
/*  371:     */   public TransformerHandler newTransformerHandler()
/*  372:     */     throws TransformerConfigurationException
/*  373:     */   {
/*  374: 755 */     return new TransformerIdentityImpl(this.m_isSecureProcessing);
/*  375:     */   }
/*  376:     */   
/*  377:     */   public Transformer newTransformer(Source source)
/*  378:     */     throws TransformerConfigurationException
/*  379:     */   {
/*  380:     */     try
/*  381:     */     {
/*  382: 776 */       Templates tmpl = newTemplates(source);
/*  383: 783 */       if (tmpl == null) {
/*  384: 783 */         return null;
/*  385:     */       }
/*  386: 784 */       Transformer transformer = tmpl.newTransformer();
/*  387: 785 */       transformer.setURIResolver(this.m_uriResolver);
/*  388: 786 */       return transformer;
/*  389:     */     }
/*  390:     */     catch (TransformerConfigurationException ex)
/*  391:     */     {
/*  392: 790 */       if (this.m_errorListener != null) {
/*  393:     */         try
/*  394:     */         {
/*  395: 794 */           this.m_errorListener.fatalError(ex);
/*  396: 795 */           return null;
/*  397:     */         }
/*  398:     */         catch (TransformerConfigurationException ex1)
/*  399:     */         {
/*  400: 799 */           throw ex1;
/*  401:     */         }
/*  402:     */         catch (TransformerException ex1)
/*  403:     */         {
/*  404: 803 */           throw new TransformerConfigurationException(ex1);
/*  405:     */         }
/*  406:     */       }
/*  407: 806 */       throw ex;
/*  408:     */     }
/*  409:     */   }
/*  410:     */   
/*  411:     */   public Transformer newTransformer()
/*  412:     */     throws TransformerConfigurationException
/*  413:     */   {
/*  414: 823 */     return new TransformerIdentityImpl(this.m_isSecureProcessing);
/*  415:     */   }
/*  416:     */   
/*  417:     */   public Templates newTemplates(Source source)
/*  418:     */     throws TransformerConfigurationException
/*  419:     */   {
/*  420: 844 */     String baseID = source.getSystemId();
/*  421: 846 */     if (null != baseID) {
/*  422: 847 */       baseID = SystemIDResolver.getAbsoluteURI(baseID);
/*  423:     */     }
/*  424: 851 */     if ((source instanceof DOMSource))
/*  425:     */     {
/*  426: 853 */       DOMSource dsource = (DOMSource)source;
/*  427: 854 */       Node node = dsource.getNode();
/*  428: 856 */       if (null != node) {
/*  429: 857 */         return processFromNode(node, baseID);
/*  430:     */       }
/*  431: 860 */       String messageStr = XSLMessages.createMessage("ER_ILLEGAL_DOMSOURCE_INPUT", null);
/*  432:     */       
/*  433:     */ 
/*  434: 863 */       throw new IllegalArgumentException(messageStr);
/*  435:     */     }
/*  436: 867 */     TemplatesHandler builder = newTemplatesHandler();
/*  437: 868 */     builder.setSystemId(baseID);
/*  438:     */     try
/*  439:     */     {
/*  440: 872 */       InputSource isource = SAXSource.sourceToInputSource(source);
/*  441: 873 */       isource.setSystemId(baseID);
/*  442: 874 */       XMLReader reader = null;
/*  443: 876 */       if ((source instanceof SAXSource)) {
/*  444: 877 */         reader = ((SAXSource)source).getXMLReader();
/*  445:     */       }
/*  446: 879 */       if (null == reader) {
/*  447:     */         try
/*  448:     */         {
/*  449: 885 */           SAXParserFactory factory = SAXParserFactory.newInstance();
/*  450:     */           
/*  451:     */ 
/*  452: 888 */           factory.setNamespaceAware(true);
/*  453: 890 */           if (this.m_isSecureProcessing) {
/*  454:     */             try
/*  455:     */             {
/*  456: 894 */               factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  457:     */             }
/*  458:     */             catch (SAXException se) {}
/*  459:     */           }
/*  460: 899 */           SAXParser jaxpParser = factory.newSAXParser();
/*  461:     */           
/*  462: 901 */           reader = jaxpParser.getXMLReader();
/*  463:     */         }
/*  464:     */         catch (ParserConfigurationException ex)
/*  465:     */         {
/*  466: 905 */           throw new SAXException(ex);
/*  467:     */         }
/*  468:     */         catch (FactoryConfigurationError ex1)
/*  469:     */         {
/*  470: 909 */           throw new SAXException(ex1.toString());
/*  471:     */         }
/*  472:     */         catch (NoSuchMethodError ex2) {}catch (AbstractMethodError ame) {}
/*  473:     */       }
/*  474: 915 */       if (null == reader) {
/*  475: 916 */         reader = XMLReaderFactory.createXMLReader();
/*  476:     */       }
/*  477: 921 */       reader.setContentHandler(builder);
/*  478: 922 */       reader.parse(isource);
/*  479:     */     }
/*  480:     */     catch (SAXException se)
/*  481:     */     {
/*  482: 926 */       if (this.m_errorListener != null) {
/*  483:     */         try
/*  484:     */         {
/*  485: 930 */           this.m_errorListener.fatalError(new TransformerException(se));
/*  486:     */         }
/*  487:     */         catch (TransformerConfigurationException ex1)
/*  488:     */         {
/*  489: 934 */           throw ex1;
/*  490:     */         }
/*  491:     */         catch (TransformerException ex1)
/*  492:     */         {
/*  493: 938 */           throw new TransformerConfigurationException(ex1);
/*  494:     */         }
/*  495:     */       } else {
/*  496: 943 */         throw new TransformerConfigurationException(se.getMessage(), se);
/*  497:     */       }
/*  498:     */     }
/*  499:     */     catch (Exception e)
/*  500:     */     {
/*  501: 948 */       if (this.m_errorListener != null) {
/*  502:     */         try
/*  503:     */         {
/*  504: 952 */           this.m_errorListener.fatalError(new TransformerException(e));
/*  505: 953 */           return null;
/*  506:     */         }
/*  507:     */         catch (TransformerConfigurationException ex1)
/*  508:     */         {
/*  509: 957 */           throw ex1;
/*  510:     */         }
/*  511:     */         catch (TransformerException ex1)
/*  512:     */         {
/*  513: 961 */           throw new TransformerConfigurationException(ex1);
/*  514:     */         }
/*  515:     */       }
/*  516: 966 */       throw new TransformerConfigurationException(e.getMessage(), e);
/*  517:     */     }
/*  518: 970 */     return builder.getTemplates();
/*  519:     */   }
/*  520:     */   
/*  521:     */   public void setURIResolver(URIResolver resolver)
/*  522:     */   {
/*  523: 988 */     this.m_uriResolver = resolver;
/*  524:     */   }
/*  525:     */   
/*  526:     */   public URIResolver getURIResolver()
/*  527:     */   {
/*  528:1000 */     return this.m_uriResolver;
/*  529:     */   }
/*  530:     */   
/*  531:1004 */   private ErrorListener m_errorListener = new DefaultErrorHandler(false);
/*  532:     */   
/*  533:     */   public ErrorListener getErrorListener()
/*  534:     */   {
/*  535:1013 */     return this.m_errorListener;
/*  536:     */   }
/*  537:     */   
/*  538:     */   public void setErrorListener(ErrorListener listener)
/*  539:     */     throws IllegalArgumentException
/*  540:     */   {
/*  541:1027 */     if (null == listener) {
/*  542:1028 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_ERRORLISTENER", null));
/*  543:     */     }
/*  544:1031 */     this.m_errorListener = listener;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public boolean isSecureProcessing()
/*  548:     */   {
/*  549:1041 */     return this.m_isSecureProcessing;
/*  550:     */   }
/*  551:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.TransformerFactoryImpl
 * JD-Core Version:    0.7.0.1
 */