/*    1:     */ package org.apache.xalan.transformer;
/*    2:     */ 
/*    3:     */ import java.io.FileOutputStream;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.util.Hashtable;
/*    6:     */ import java.util.Properties;
/*    7:     */ import javax.xml.parsers.DocumentBuilder;
/*    8:     */ import javax.xml.parsers.DocumentBuilderFactory;
/*    9:     */ import javax.xml.parsers.ParserConfigurationException;
/*   10:     */ import javax.xml.transform.ErrorListener;
/*   11:     */ import javax.xml.transform.Result;
/*   12:     */ import javax.xml.transform.Source;
/*   13:     */ import javax.xml.transform.Transformer;
/*   14:     */ import javax.xml.transform.TransformerException;
/*   15:     */ import javax.xml.transform.URIResolver;
/*   16:     */ import javax.xml.transform.dom.DOMResult;
/*   17:     */ import javax.xml.transform.dom.DOMSource;
/*   18:     */ import javax.xml.transform.sax.SAXResult;
/*   19:     */ import javax.xml.transform.sax.SAXSource;
/*   20:     */ import javax.xml.transform.sax.TransformerHandler;
/*   21:     */ import javax.xml.transform.stream.StreamResult;
/*   22:     */ import javax.xml.transform.stream.StreamSource;
/*   23:     */ import org.apache.xalan.res.XSLMessages;
/*   24:     */ import org.apache.xalan.templates.OutputProperties;
/*   25:     */ import org.apache.xml.serializer.Serializer;
/*   26:     */ import org.apache.xml.serializer.SerializerFactory;
/*   27:     */ import org.apache.xml.serializer.TreeWalker;
/*   28:     */ import org.apache.xml.utils.DOMBuilder;
/*   29:     */ import org.apache.xml.utils.DefaultErrorHandler;
/*   30:     */ import org.apache.xml.utils.WrappedRuntimeException;
/*   31:     */ import org.apache.xml.utils.XMLReaderManager;
/*   32:     */ import org.w3c.dom.Document;
/*   33:     */ import org.w3c.dom.DocumentFragment;
/*   34:     */ import org.w3c.dom.Node;
/*   35:     */ import org.xml.sax.Attributes;
/*   36:     */ import org.xml.sax.ContentHandler;
/*   37:     */ import org.xml.sax.DTDHandler;
/*   38:     */ import org.xml.sax.InputSource;
/*   39:     */ import org.xml.sax.Locator;
/*   40:     */ import org.xml.sax.SAXException;
/*   41:     */ import org.xml.sax.SAXNotRecognizedException;
/*   42:     */ import org.xml.sax.XMLReader;
/*   43:     */ import org.xml.sax.ext.DeclHandler;
/*   44:     */ import org.xml.sax.ext.LexicalHandler;
/*   45:     */ 
/*   46:     */ public class TransformerIdentityImpl
/*   47:     */   extends Transformer
/*   48:     */   implements TransformerHandler, DeclHandler
/*   49:     */ {
/*   50:     */   public TransformerIdentityImpl(boolean isSecureProcessing)
/*   51:     */   {
/*   52:  88 */     this.m_outputFormat = new OutputProperties("xml");
/*   53:  89 */     this.m_isSecureProcessing = isSecureProcessing;
/*   54:     */   }
/*   55:     */   
/*   56:     */   public TransformerIdentityImpl()
/*   57:     */   {
/*   58:  98 */     this(false);
/*   59:     */   }
/*   60:     */   
/*   61:     */   public void setResult(Result result)
/*   62:     */     throws IllegalArgumentException
/*   63:     */   {
/*   64: 111 */     if (null == result) {
/*   65: 112 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_RESULT_NULL", null));
/*   66:     */     }
/*   67: 113 */     this.m_result = result;
/*   68:     */   }
/*   69:     */   
/*   70:     */   public void setSystemId(String systemID)
/*   71:     */   {
/*   72: 123 */     this.m_systemID = systemID;
/*   73:     */   }
/*   74:     */   
/*   75:     */   public String getSystemId()
/*   76:     */   {
/*   77: 133 */     return this.m_systemID;
/*   78:     */   }
/*   79:     */   
/*   80:     */   public Transformer getTransformer()
/*   81:     */   {
/*   82: 144 */     return this;
/*   83:     */   }
/*   84:     */   
/*   85:     */   public void reset()
/*   86:     */   {
/*   87: 152 */     this.m_flushedStartDoc = false;
/*   88: 153 */     this.m_foundFirstElement = false;
/*   89: 154 */     this.m_outputStream = null;
/*   90: 155 */     clearParameters();
/*   91: 156 */     this.m_result = null;
/*   92: 157 */     this.m_resultContentHandler = null;
/*   93: 158 */     this.m_resultDeclHandler = null;
/*   94: 159 */     this.m_resultDTDHandler = null;
/*   95: 160 */     this.m_resultLexicalHandler = null;
/*   96: 161 */     this.m_serializer = null;
/*   97: 162 */     this.m_systemID = null;
/*   98: 163 */     this.m_URIResolver = null;
/*   99: 164 */     this.m_outputFormat = new OutputProperties("xml");
/*  100:     */   }
/*  101:     */   
/*  102:     */   private void createResultContentHandler(Result outputTarget)
/*  103:     */     throws TransformerException
/*  104:     */   {
/*  105: 183 */     if ((outputTarget instanceof SAXResult))
/*  106:     */     {
/*  107: 185 */       SAXResult saxResult = (SAXResult)outputTarget;
/*  108:     */       
/*  109: 187 */       this.m_resultContentHandler = saxResult.getHandler();
/*  110: 188 */       this.m_resultLexicalHandler = saxResult.getLexicalHandler();
/*  111: 190 */       if ((this.m_resultContentHandler instanceof Serializer)) {
/*  112: 194 */         this.m_serializer = ((Serializer)this.m_resultContentHandler);
/*  113:     */       }
/*  114:     */     }
/*  115: 197 */     else if ((outputTarget instanceof DOMResult))
/*  116:     */     {
/*  117: 199 */       DOMResult domResult = (DOMResult)outputTarget;
/*  118: 200 */       Node outputNode = domResult.getNode();
/*  119: 201 */       Node nextSibling = domResult.getNextSibling();
/*  120:     */       short type;
/*  121:     */       Document doc;
/*  122: 205 */       if (null != outputNode)
/*  123:     */       {
/*  124: 207 */         type = outputNode.getNodeType();
/*  125: 208 */         doc = 9 == type ? (Document)outputNode : outputNode.getOwnerDocument();
/*  126:     */       }
/*  127:     */       else
/*  128:     */       {
/*  129:     */         try
/*  130:     */         {
/*  131: 215 */           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*  132:     */           
/*  133: 217 */           dbf.setNamespaceAware(true);
/*  134: 219 */           if (this.m_isSecureProcessing) {
/*  135:     */             try
/*  136:     */             {
/*  137: 223 */               dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  138:     */             }
/*  139:     */             catch (ParserConfigurationException pce) {}
/*  140:     */           }
/*  141: 228 */           DocumentBuilder db = dbf.newDocumentBuilder();
/*  142:     */           
/*  143: 230 */           doc = db.newDocument();
/*  144:     */         }
/*  145:     */         catch (ParserConfigurationException pce)
/*  146:     */         {
/*  147: 234 */           throw new TransformerException(pce);
/*  148:     */         }
/*  149: 237 */         outputNode = doc;
/*  150: 238 */         type = outputNode.getNodeType();
/*  151:     */         
/*  152: 240 */         ((DOMResult)outputTarget).setNode(outputNode);
/*  153:     */       }
/*  154: 243 */       DOMBuilder domBuilder = 11 == type ? new DOMBuilder(doc, (DocumentFragment)outputNode) : new DOMBuilder(doc, outputNode);
/*  155: 248 */       if (nextSibling != null) {
/*  156: 249 */         domBuilder.setNextSibling(nextSibling);
/*  157:     */       }
/*  158: 251 */       this.m_resultContentHandler = domBuilder;
/*  159: 252 */       this.m_resultLexicalHandler = domBuilder;
/*  160:     */     }
/*  161: 254 */     else if ((outputTarget instanceof StreamResult))
/*  162:     */     {
/*  163: 256 */       StreamResult sresult = (StreamResult)outputTarget;
/*  164:     */       try
/*  165:     */       {
/*  166: 260 */         Serializer serializer = SerializerFactory.getSerializer(this.m_outputFormat.getProperties());
/*  167:     */         
/*  168:     */ 
/*  169: 263 */         this.m_serializer = serializer;
/*  170: 265 */         if (null != sresult.getWriter())
/*  171:     */         {
/*  172: 266 */           serializer.setWriter(sresult.getWriter());
/*  173:     */         }
/*  174: 267 */         else if (null != sresult.getOutputStream())
/*  175:     */         {
/*  176: 268 */           serializer.setOutputStream(sresult.getOutputStream());
/*  177:     */         }
/*  178: 269 */         else if (null != sresult.getSystemId())
/*  179:     */         {
/*  180: 271 */           String fileURL = sresult.getSystemId();
/*  181: 273 */           if (fileURL.startsWith("file:///"))
/*  182:     */           {
/*  183: 274 */             if (fileURL.substring(8).indexOf(":") > 0) {
/*  184: 275 */               fileURL = fileURL.substring(8);
/*  185:     */             } else {
/*  186: 277 */               fileURL = fileURL.substring(7);
/*  187:     */             }
/*  188:     */           }
/*  189: 279 */           else if (fileURL.startsWith("file:/")) {
/*  190: 280 */             if (fileURL.substring(6).indexOf(":") > 0) {
/*  191: 281 */               fileURL = fileURL.substring(6);
/*  192:     */             } else {
/*  193: 283 */               fileURL = fileURL.substring(5);
/*  194:     */             }
/*  195:     */           }
/*  196: 287 */           this.m_outputStream = new FileOutputStream(fileURL);
/*  197: 288 */           serializer.setOutputStream(this.m_outputStream);
/*  198:     */         }
/*  199:     */         else
/*  200:     */         {
/*  201: 291 */           throw new TransformerException(XSLMessages.createMessage("ER_NO_OUTPUT_SPECIFIED", null));
/*  202:     */         }
/*  203: 293 */         this.m_resultContentHandler = serializer.asContentHandler();
/*  204:     */       }
/*  205:     */       catch (IOException ioe)
/*  206:     */       {
/*  207: 297 */         throw new TransformerException(ioe);
/*  208:     */       }
/*  209:     */     }
/*  210:     */     else
/*  211:     */     {
/*  212: 302 */       throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", new Object[] { outputTarget.getClass().getName() }));
/*  213:     */     }
/*  214: 307 */     if ((this.m_resultContentHandler instanceof DTDHandler)) {
/*  215: 308 */       this.m_resultDTDHandler = ((DTDHandler)this.m_resultContentHandler);
/*  216:     */     }
/*  217: 310 */     if ((this.m_resultContentHandler instanceof DeclHandler)) {
/*  218: 311 */       this.m_resultDeclHandler = ((DeclHandler)this.m_resultContentHandler);
/*  219:     */     }
/*  220: 313 */     if ((this.m_resultContentHandler instanceof LexicalHandler)) {
/*  221: 314 */       this.m_resultLexicalHandler = ((LexicalHandler)this.m_resultContentHandler);
/*  222:     */     }
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void transform(Source source, Result outputTarget)
/*  226:     */     throws TransformerException
/*  227:     */   {
/*  228: 330 */     createResultContentHandler(outputTarget);
/*  229: 340 */     if ((((source instanceof StreamSource)) && (source.getSystemId() == null) && (((StreamSource)source).getInputStream() == null) && (((StreamSource)source).getReader() == null)) || (((source instanceof SAXSource)) && (((SAXSource)source).getInputSource() == null) && (((SAXSource)source).getXMLReader() == null)) || (((source instanceof DOMSource)) && (((DOMSource)source).getNode() == null))) {
/*  230:     */       try
/*  231:     */       {
/*  232: 348 */         DocumentBuilderFactory builderF = DocumentBuilderFactory.newInstance();
/*  233: 349 */         DocumentBuilder builder = builderF.newDocumentBuilder();
/*  234: 350 */         String systemID = source.getSystemId();
/*  235: 351 */         source = new DOMSource(builder.newDocument());
/*  236: 354 */         if (systemID != null) {
/*  237: 355 */           source.setSystemId(systemID);
/*  238:     */         }
/*  239:     */       }
/*  240:     */       catch (ParserConfigurationException e)
/*  241:     */       {
/*  242: 358 */         throw new TransformerException(e.getMessage());
/*  243:     */       }
/*  244:     */     }
/*  245:     */     try
/*  246:     */     {
/*  247: 364 */       if ((source instanceof DOMSource))
/*  248:     */       {
/*  249: 366 */         DOMSource dsource = (DOMSource)source;
/*  250:     */         
/*  251: 368 */         this.m_systemID = dsource.getSystemId();
/*  252:     */         
/*  253: 370 */         Node dNode = dsource.getNode();
/*  254: 372 */         if (null != dNode)
/*  255:     */         {
/*  256:     */           try
/*  257:     */           {
/*  258: 376 */             if (dNode.getNodeType() == 2) {
/*  259: 377 */               startDocument();
/*  260:     */             }
/*  261:     */             try
/*  262:     */             {
/*  263: 380 */               if (dNode.getNodeType() == 2)
/*  264:     */               {
/*  265: 382 */                 String data = dNode.getNodeValue();
/*  266: 383 */                 char[] chars = data.toCharArray();
/*  267: 384 */                 characters(chars, 0, chars.length);
/*  268:     */               }
/*  269:     */               else
/*  270:     */               {
/*  271: 389 */                 TreeWalker walker = new TreeWalker(this, this.m_systemID);
/*  272: 390 */                 walker.traverse(dNode);
/*  273:     */               }
/*  274:     */             }
/*  275:     */             finally
/*  276:     */             {
/*  277: 395 */               if (dNode.getNodeType() == 2) {
/*  278: 396 */                 endDocument();
/*  279:     */               }
/*  280:     */             }
/*  281:     */           }
/*  282:     */           catch (SAXException se)
/*  283:     */           {
/*  284: 401 */             throw new TransformerException(se);
/*  285:     */           }
/*  286: 404 */           return;
/*  287:     */         }
/*  288: 408 */         String messageStr = XSLMessages.createMessage("ER_ILLEGAL_DOMSOURCE_INPUT", null);
/*  289:     */         
/*  290:     */ 
/*  291: 411 */         throw new IllegalArgumentException(messageStr);
/*  292:     */       }
/*  293: 415 */       InputSource xmlSource = SAXSource.sourceToInputSource(source);
/*  294: 417 */       if (null == xmlSource) {
/*  295: 419 */         throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_TRANSFORM_SOURCE_TYPE", new Object[] { source.getClass().getName() }));
/*  296:     */       }
/*  297: 423 */       if (null != xmlSource.getSystemId()) {
/*  298: 424 */         this.m_systemID = xmlSource.getSystemId();
/*  299:     */       }
/*  300: 426 */       XMLReader reader = null;
/*  301: 427 */       boolean managedReader = false;
/*  302:     */       try
/*  303:     */       {
/*  304: 431 */         if ((source instanceof SAXSource)) {
/*  305: 432 */           reader = ((SAXSource)source).getXMLReader();
/*  306:     */         }
/*  307: 435 */         if (null == reader) {
/*  308:     */           try
/*  309:     */           {
/*  310: 437 */             reader = XMLReaderManager.getInstance().getXMLReader();
/*  311: 438 */             managedReader = true;
/*  312:     */           }
/*  313:     */           catch (SAXException se)
/*  314:     */           {
/*  315: 440 */             throw new TransformerException(se);
/*  316:     */           }
/*  317:     */         } else {
/*  318:     */           try
/*  319:     */           {
/*  320: 444 */             reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/*  321:     */           }
/*  322:     */           catch (SAXException se) {}
/*  323:     */         }
/*  324: 453 */         ContentHandler inputHandler = this;
/*  325:     */         
/*  326: 455 */         reader.setContentHandler(inputHandler);
/*  327: 457 */         if ((inputHandler instanceof DTDHandler)) {
/*  328: 458 */           reader.setDTDHandler((DTDHandler)inputHandler);
/*  329:     */         }
/*  330:     */         try
/*  331:     */         {
/*  332: 462 */           if ((inputHandler instanceof LexicalHandler)) {
/*  333: 463 */             reader.setProperty("http://xml.org/sax/properties/lexical-handler", inputHandler);
/*  334:     */           }
/*  335: 466 */           if ((inputHandler instanceof DeclHandler)) {
/*  336: 467 */             reader.setProperty("http://xml.org/sax/properties/declaration-handler", inputHandler);
/*  337:     */           }
/*  338:     */         }
/*  339:     */         catch (SAXException se) {}
/*  340:     */         try
/*  341:     */         {
/*  342: 475 */           if ((inputHandler instanceof LexicalHandler)) {
/*  343: 476 */             reader.setProperty("http://xml.org/sax/handlers/LexicalHandler", inputHandler);
/*  344:     */           }
/*  345: 479 */           if ((inputHandler instanceof DeclHandler)) {
/*  346: 480 */             reader.setProperty("http://xml.org/sax/handlers/DeclHandler", inputHandler);
/*  347:     */           }
/*  348:     */         }
/*  349:     */         catch (SAXNotRecognizedException snre) {}
/*  350: 485 */         reader.parse(xmlSource);
/*  351:     */       }
/*  352:     */       catch (WrappedRuntimeException wre)
/*  353:     */       {
/*  354: 489 */         Object throwable = wre.getException();
/*  355: 492 */         while ((throwable instanceof WrappedRuntimeException)) {
/*  356: 494 */           throwable = ((WrappedRuntimeException)throwable).getException();
/*  357:     */         }
/*  358: 498 */         throw new TransformerException(wre.getException());
/*  359:     */       }
/*  360:     */       catch (SAXException se)
/*  361:     */       {
/*  362: 502 */         throw new TransformerException(se);
/*  363:     */       }
/*  364:     */       catch (IOException ioe)
/*  365:     */       {
/*  366: 506 */         throw new TransformerException(ioe);
/*  367:     */       }
/*  368:     */       finally
/*  369:     */       {
/*  370: 508 */         if (managedReader) {
/*  371: 509 */           XMLReaderManager.getInstance().releaseXMLReader(reader);
/*  372:     */         }
/*  373:     */       }
/*  374:     */     }
/*  375:     */     finally
/*  376:     */     {
/*  377: 515 */       if (null != this.m_outputStream)
/*  378:     */       {
/*  379:     */         try
/*  380:     */         {
/*  381: 519 */           this.m_outputStream.close();
/*  382:     */         }
/*  383:     */         catch (IOException ioe) {}
/*  384: 522 */         this.m_outputStream = null;
/*  385:     */       }
/*  386:     */     }
/*  387:     */   }
/*  388:     */   
/*  389:     */   public void setParameter(String name, Object value)
/*  390:     */   {
/*  391: 548 */     if (value == null) {
/*  392: 549 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_INVALID_SET_PARAM_VALUE", new Object[] { name }));
/*  393:     */     }
/*  394: 552 */     if (null == this.m_params) {
/*  395: 554 */       this.m_params = new Hashtable();
/*  396:     */     }
/*  397: 557 */     this.m_params.put(name, value);
/*  398:     */   }
/*  399:     */   
/*  400:     */   public Object getParameter(String name)
/*  401:     */   {
/*  402: 575 */     if (null == this.m_params) {
/*  403: 576 */       return null;
/*  404:     */     }
/*  405: 578 */     return this.m_params.get(name);
/*  406:     */   }
/*  407:     */   
/*  408:     */   public void clearParameters()
/*  409:     */   {
/*  410: 587 */     if (null == this.m_params) {
/*  411: 588 */       return;
/*  412:     */     }
/*  413: 590 */     this.m_params.clear();
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void setURIResolver(URIResolver resolver)
/*  417:     */   {
/*  418: 605 */     this.m_URIResolver = resolver;
/*  419:     */   }
/*  420:     */   
/*  421:     */   public URIResolver getURIResolver()
/*  422:     */   {
/*  423: 617 */     return this.m_URIResolver;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void setOutputProperties(Properties oformat)
/*  427:     */     throws IllegalArgumentException
/*  428:     */   {
/*  429: 653 */     if (null != oformat)
/*  430:     */     {
/*  431: 657 */       String method = (String)oformat.get("method");
/*  432: 659 */       if (null != method) {
/*  433: 660 */         this.m_outputFormat = new OutputProperties(method);
/*  434:     */       } else {
/*  435: 662 */         this.m_outputFormat = new OutputProperties();
/*  436:     */       }
/*  437: 664 */       this.m_outputFormat.copyFrom(oformat);
/*  438:     */     }
/*  439:     */     else
/*  440:     */     {
/*  441: 669 */       this.m_outputFormat = null;
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   public Properties getOutputProperties()
/*  446:     */   {
/*  447: 706 */     return (Properties)this.m_outputFormat.getProperties().clone();
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void setOutputProperty(String name, String value)
/*  451:     */     throws IllegalArgumentException
/*  452:     */   {
/*  453: 739 */     if (!OutputProperties.isLegalPropertyKey(name)) {
/*  454: 740 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { name }));
/*  455:     */     }
/*  456: 743 */     this.m_outputFormat.setProperty(name, value);
/*  457:     */   }
/*  458:     */   
/*  459:     */   public String getOutputProperty(String name)
/*  460:     */     throws IllegalArgumentException
/*  461:     */   {
/*  462: 765 */     String value = null;
/*  463: 766 */     OutputProperties props = this.m_outputFormat;
/*  464:     */     
/*  465: 768 */     value = props.getProperty(name);
/*  466: 770 */     if (null == value) {
/*  467: 772 */       if (!OutputProperties.isLegalPropertyKey(name)) {
/*  468: 773 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { name }));
/*  469:     */       }
/*  470:     */     }
/*  471: 777 */     return value;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void setErrorListener(ErrorListener listener)
/*  475:     */     throws IllegalArgumentException
/*  476:     */   {
/*  477: 789 */     if (listener == null) {
/*  478: 790 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_NULL_ERROR_HANDLER", null));
/*  479:     */     }
/*  480: 792 */     this.m_errorListener = listener;
/*  481:     */   }
/*  482:     */   
/*  483:     */   public ErrorListener getErrorListener()
/*  484:     */   {
/*  485: 802 */     return this.m_errorListener;
/*  486:     */   }
/*  487:     */   
/*  488:     */   public void notationDecl(String name, String publicId, String systemId)
/*  489:     */     throws SAXException
/*  490:     */   {
/*  491: 829 */     if (null != this.m_resultDTDHandler) {
/*  492: 830 */       this.m_resultDTDHandler.notationDecl(name, publicId, systemId);
/*  493:     */     }
/*  494:     */   }
/*  495:     */   
/*  496:     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
/*  497:     */     throws SAXException
/*  498:     */   {
/*  499: 856 */     if (null != this.m_resultDTDHandler) {
/*  500: 857 */       this.m_resultDTDHandler.unparsedEntityDecl(name, publicId, systemId, notationName);
/*  501:     */     }
/*  502:     */   }
/*  503:     */   
/*  504:     */   public void setDocumentLocator(Locator locator)
/*  505:     */   {
/*  506:     */     try
/*  507:     */     {
/*  508: 880 */       if (null == this.m_resultContentHandler) {
/*  509: 881 */         createResultContentHandler(this.m_result);
/*  510:     */       }
/*  511:     */     }
/*  512:     */     catch (TransformerException te)
/*  513:     */     {
/*  514: 885 */       throw new WrappedRuntimeException(te);
/*  515:     */     }
/*  516: 888 */     this.m_resultContentHandler.setDocumentLocator(locator);
/*  517:     */   }
/*  518:     */   
/*  519:     */   public void startDocument()
/*  520:     */     throws SAXException
/*  521:     */   {
/*  522:     */     try
/*  523:     */     {
/*  524: 910 */       if (null == this.m_resultContentHandler) {
/*  525: 911 */         createResultContentHandler(this.m_result);
/*  526:     */       }
/*  527:     */     }
/*  528:     */     catch (TransformerException te)
/*  529:     */     {
/*  530: 915 */       throw new SAXException(te.getMessage(), te);
/*  531:     */     }
/*  532: 919 */     this.m_flushedStartDoc = false;
/*  533: 920 */     this.m_foundFirstElement = false;
/*  534:     */   }
/*  535:     */   
/*  536: 923 */   boolean m_flushedStartDoc = false;
/*  537:     */   
/*  538:     */   protected final void flushStartDoc()
/*  539:     */     throws SAXException
/*  540:     */   {
/*  541: 928 */     if (!this.m_flushedStartDoc)
/*  542:     */     {
/*  543: 930 */       if (this.m_resultContentHandler == null) {
/*  544:     */         try
/*  545:     */         {
/*  546: 934 */           createResultContentHandler(this.m_result);
/*  547:     */         }
/*  548:     */         catch (TransformerException te)
/*  549:     */         {
/*  550: 938 */           throw new SAXException(te);
/*  551:     */         }
/*  552:     */       }
/*  553: 941 */       this.m_resultContentHandler.startDocument();
/*  554: 942 */       this.m_flushedStartDoc = true;
/*  555:     */     }
/*  556:     */   }
/*  557:     */   
/*  558:     */   public void endDocument()
/*  559:     */     throws SAXException
/*  560:     */   {
/*  561: 962 */     flushStartDoc();
/*  562: 963 */     this.m_resultContentHandler.endDocument();
/*  563:     */   }
/*  564:     */   
/*  565:     */   public void startPrefixMapping(String prefix, String uri)
/*  566:     */     throws SAXException
/*  567:     */   {
/*  568: 984 */     flushStartDoc();
/*  569: 985 */     this.m_resultContentHandler.startPrefixMapping(prefix, uri);
/*  570:     */   }
/*  571:     */   
/*  572:     */   public void endPrefixMapping(String prefix)
/*  573:     */     throws SAXException
/*  574:     */   {
/*  575:1004 */     flushStartDoc();
/*  576:1005 */     this.m_resultContentHandler.endPrefixMapping(prefix);
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void startElement(String uri, String localName, String qName, Attributes attributes)
/*  580:     */     throws SAXException
/*  581:     */   {
/*  582:1036 */     if ((!this.m_foundFirstElement) && (null != this.m_serializer))
/*  583:     */     {
/*  584:1038 */       this.m_foundFirstElement = true;
/*  585:     */       Serializer newSerializer;
/*  586:     */       try
/*  587:     */       {
/*  588:1044 */         newSerializer = SerializerSwitcher.switchSerializerIfHTML(uri, localName, this.m_outputFormat.getProperties(), this.m_serializer);
/*  589:     */       }
/*  590:     */       catch (TransformerException te)
/*  591:     */       {
/*  592:1049 */         throw new SAXException(te);
/*  593:     */       }
/*  594:1052 */       if (newSerializer != this.m_serializer)
/*  595:     */       {
/*  596:     */         try
/*  597:     */         {
/*  598:1056 */           this.m_resultContentHandler = newSerializer.asContentHandler();
/*  599:     */         }
/*  600:     */         catch (IOException ioe)
/*  601:     */         {
/*  602:1060 */           throw new SAXException(ioe);
/*  603:     */         }
/*  604:1063 */         if ((this.m_resultContentHandler instanceof DTDHandler)) {
/*  605:1064 */           this.m_resultDTDHandler = ((DTDHandler)this.m_resultContentHandler);
/*  606:     */         }
/*  607:1066 */         if ((this.m_resultContentHandler instanceof LexicalHandler)) {
/*  608:1067 */           this.m_resultLexicalHandler = ((LexicalHandler)this.m_resultContentHandler);
/*  609:     */         }
/*  610:1069 */         this.m_serializer = newSerializer;
/*  611:     */       }
/*  612:     */     }
/*  613:1072 */     flushStartDoc();
/*  614:1073 */     this.m_resultContentHandler.startElement(uri, localName, qName, attributes);
/*  615:     */   }
/*  616:     */   
/*  617:     */   public void endElement(String uri, String localName, String qName)
/*  618:     */     throws SAXException
/*  619:     */   {
/*  620:1102 */     this.m_resultContentHandler.endElement(uri, localName, qName);
/*  621:     */   }
/*  622:     */   
/*  623:     */   public void characters(char[] ch, int start, int length)
/*  624:     */     throws SAXException
/*  625:     */   {
/*  626:1125 */     flushStartDoc();
/*  627:1126 */     this.m_resultContentHandler.characters(ch, start, length);
/*  628:     */   }
/*  629:     */   
/*  630:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/*  631:     */     throws SAXException
/*  632:     */   {
/*  633:1150 */     this.m_resultContentHandler.ignorableWhitespace(ch, start, length);
/*  634:     */   }
/*  635:     */   
/*  636:     */   public void processingInstruction(String target, String data)
/*  637:     */     throws SAXException
/*  638:     */   {
/*  639:1173 */     flushStartDoc();
/*  640:1174 */     this.m_resultContentHandler.processingInstruction(target, data);
/*  641:     */   }
/*  642:     */   
/*  643:     */   public void skippedEntity(String name)
/*  644:     */     throws SAXException
/*  645:     */   {
/*  646:1194 */     flushStartDoc();
/*  647:1195 */     this.m_resultContentHandler.skippedEntity(name);
/*  648:     */   }
/*  649:     */   
/*  650:     */   public void startDTD(String name, String publicId, String systemId)
/*  651:     */     throws SAXException
/*  652:     */   {
/*  653:1222 */     flushStartDoc();
/*  654:1223 */     if (null != this.m_resultLexicalHandler) {
/*  655:1224 */       this.m_resultLexicalHandler.startDTD(name, publicId, systemId);
/*  656:     */     }
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void endDTD()
/*  660:     */     throws SAXException
/*  661:     */   {
/*  662:1235 */     if (null != this.m_resultLexicalHandler) {
/*  663:1236 */       this.m_resultLexicalHandler.endDTD();
/*  664:     */     }
/*  665:     */   }
/*  666:     */   
/*  667:     */   public void startEntity(String name)
/*  668:     */     throws SAXException
/*  669:     */   {
/*  670:1263 */     if (null != this.m_resultLexicalHandler) {
/*  671:1264 */       this.m_resultLexicalHandler.startEntity(name);
/*  672:     */     }
/*  673:     */   }
/*  674:     */   
/*  675:     */   public void endEntity(String name)
/*  676:     */     throws SAXException
/*  677:     */   {
/*  678:1276 */     if (null != this.m_resultLexicalHandler) {
/*  679:1277 */       this.m_resultLexicalHandler.endEntity(name);
/*  680:     */     }
/*  681:     */   }
/*  682:     */   
/*  683:     */   public void startCDATA()
/*  684:     */     throws SAXException
/*  685:     */   {
/*  686:1292 */     if (null != this.m_resultLexicalHandler) {
/*  687:1293 */       this.m_resultLexicalHandler.startCDATA();
/*  688:     */     }
/*  689:     */   }
/*  690:     */   
/*  691:     */   public void endCDATA()
/*  692:     */     throws SAXException
/*  693:     */   {
/*  694:1304 */     if (null != this.m_resultLexicalHandler) {
/*  695:1305 */       this.m_resultLexicalHandler.endCDATA();
/*  696:     */     }
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void comment(char[] ch, int start, int length)
/*  700:     */     throws SAXException
/*  701:     */   {
/*  702:1322 */     flushStartDoc();
/*  703:1323 */     if (null != this.m_resultLexicalHandler) {
/*  704:1324 */       this.m_resultLexicalHandler.comment(ch, start, length);
/*  705:     */     }
/*  706:     */   }
/*  707:     */   
/*  708:     */   public void elementDecl(String name, String model)
/*  709:     */     throws SAXException
/*  710:     */   {
/*  711:1345 */     if (null != this.m_resultDeclHandler) {
/*  712:1346 */       this.m_resultDeclHandler.elementDecl(name, model);
/*  713:     */     }
/*  714:     */   }
/*  715:     */   
/*  716:     */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value)
/*  717:     */     throws SAXException
/*  718:     */   {
/*  719:1376 */     if (null != this.m_resultDeclHandler) {
/*  720:1377 */       this.m_resultDeclHandler.attributeDecl(eName, aName, type, valueDefault, value);
/*  721:     */     }
/*  722:     */   }
/*  723:     */   
/*  724:     */   public void internalEntityDecl(String name, String value)
/*  725:     */     throws SAXException
/*  726:     */   {
/*  727:1397 */     if (null != this.m_resultDeclHandler) {
/*  728:1398 */       this.m_resultDeclHandler.internalEntityDecl(name, value);
/*  729:     */     }
/*  730:     */   }
/*  731:     */   
/*  732:     */   public void externalEntityDecl(String name, String publicId, String systemId)
/*  733:     */     throws SAXException
/*  734:     */   {
/*  735:1421 */     if (null != this.m_resultDeclHandler) {
/*  736:1422 */       this.m_resultDeclHandler.externalEntityDecl(name, publicId, systemId);
/*  737:     */     }
/*  738:     */   }
/*  739:     */   
/*  740:1428 */   private FileOutputStream m_outputStream = null;
/*  741:     */   private ContentHandler m_resultContentHandler;
/*  742:     */   private LexicalHandler m_resultLexicalHandler;
/*  743:     */   private DTDHandler m_resultDTDHandler;
/*  744:     */   private DeclHandler m_resultDeclHandler;
/*  745:     */   private Serializer m_serializer;
/*  746:     */   private Result m_result;
/*  747:     */   private String m_systemID;
/*  748:     */   private Hashtable m_params;
/*  749:1461 */   private ErrorListener m_errorListener = new DefaultErrorHandler(false);
/*  750:     */   URIResolver m_URIResolver;
/*  751:     */   private OutputProperties m_outputFormat;
/*  752:     */   boolean m_foundFirstElement;
/*  753:1480 */   private boolean m_isSecureProcessing = false;
/*  754:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.TransformerIdentityImpl
 * JD-Core Version:    0.7.0.1
 */