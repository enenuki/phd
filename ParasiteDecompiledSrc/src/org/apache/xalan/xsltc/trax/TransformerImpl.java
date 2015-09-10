/*    1:     */ package org.apache.xalan.xsltc.trax;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.io.OutputStream;
/*    8:     */ import java.io.PrintStream;
/*    9:     */ import java.io.Reader;
/*   10:     */ import java.io.Writer;
/*   11:     */ import java.net.URL;
/*   12:     */ import java.net.URLConnection;
/*   13:     */ import java.net.UnknownServiceException;
/*   14:     */ import java.util.Enumeration;
/*   15:     */ import java.util.Properties;
/*   16:     */ import java.util.StringTokenizer;
/*   17:     */ import java.util.Vector;
/*   18:     */ import javax.xml.parsers.DocumentBuilder;
/*   19:     */ import javax.xml.parsers.DocumentBuilderFactory;
/*   20:     */ import javax.xml.parsers.ParserConfigurationException;
/*   21:     */ import javax.xml.transform.ErrorListener;
/*   22:     */ import javax.xml.transform.Result;
/*   23:     */ import javax.xml.transform.Source;
/*   24:     */ import javax.xml.transform.Transformer;
/*   25:     */ import javax.xml.transform.TransformerException;
/*   26:     */ import javax.xml.transform.URIResolver;
/*   27:     */ import javax.xml.transform.dom.DOMResult;
/*   28:     */ import javax.xml.transform.dom.DOMSource;
/*   29:     */ import javax.xml.transform.sax.SAXResult;
/*   30:     */ import javax.xml.transform.sax.SAXSource;
/*   31:     */ import javax.xml.transform.stream.StreamResult;
/*   32:     */ import javax.xml.transform.stream.StreamSource;
/*   33:     */ import org.apache.xalan.xsltc.DOM;
/*   34:     */ import org.apache.xalan.xsltc.DOMCache;
/*   35:     */ import org.apache.xalan.xsltc.StripFilter;
/*   36:     */ import org.apache.xalan.xsltc.Translet;
/*   37:     */ import org.apache.xalan.xsltc.TransletException;
/*   38:     */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   39:     */ import org.apache.xalan.xsltc.dom.DOMWSFilter;
/*   40:     */ import org.apache.xalan.xsltc.dom.SAXImpl;
/*   41:     */ import org.apache.xalan.xsltc.dom.XSLTCDTMManager;
/*   42:     */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*   43:     */ import org.apache.xalan.xsltc.runtime.MessageHandler;
/*   44:     */ import org.apache.xalan.xsltc.runtime.output.TransletOutputHandlerFactory;
/*   45:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   46:     */ import org.apache.xml.serializer.OutputPropertiesFactory;
/*   47:     */ import org.apache.xml.serializer.SerializationHandler;
/*   48:     */ import org.apache.xml.utils.SystemIDResolver;
/*   49:     */ import org.apache.xml.utils.XMLReaderManager;
/*   50:     */ import org.xml.sax.ContentHandler;
/*   51:     */ import org.xml.sax.InputSource;
/*   52:     */ import org.xml.sax.SAXException;
/*   53:     */ import org.xml.sax.XMLReader;
/*   54:     */ import org.xml.sax.ext.LexicalHandler;
/*   55:     */ 
/*   56:     */ public final class TransformerImpl
/*   57:     */   extends Transformer
/*   58:     */   implements DOMCache, ErrorListener
/*   59:     */ {
/*   60:     */   private static final String EMPTY_STRING = "";
/*   61:     */   private static final String NO_STRING = "no";
/*   62:     */   private static final String YES_STRING = "yes";
/*   63:     */   private static final String XML_STRING = "xml";
/*   64:     */   private static final String LEXICAL_HANDLER_PROPERTY = "http://xml.org/sax/properties/lexical-handler";
/*   65:     */   private static final String NAMESPACE_FEATURE = "http://xml.org/sax/features/namespaces";
/*   66: 104 */   private AbstractTranslet _translet = null;
/*   67: 109 */   private String _method = null;
/*   68: 114 */   private String _encoding = null;
/*   69: 119 */   private String _sourceSystemId = null;
/*   70: 124 */   private ErrorListener _errorListener = this;
/*   71: 129 */   private URIResolver _uriResolver = null;
/*   72:     */   private Properties _properties;
/*   73:     */   private Properties _propertiesClone;
/*   74: 139 */   private TransletOutputHandlerFactory _tohFactory = null;
/*   75: 144 */   private DOM _dom = null;
/*   76:     */   private int _indentNumber;
/*   77: 155 */   private TransformerFactoryImpl _tfactory = null;
/*   78: 160 */   private OutputStream _ostream = null;
/*   79: 166 */   private XSLTCDTMManager _dtmManager = null;
/*   80: 171 */   private XMLReaderManager _readerManager = XMLReaderManager.getInstance();
/*   81: 182 */   private boolean _isIdentity = false;
/*   82: 187 */   private boolean _isSecureProcessing = false;
/*   83: 194 */   private org.apache.xalan.xsltc.runtime.Hashtable _parameters = null;
/*   84:     */   
/*   85:     */   static class MessageHandler
/*   86:     */     extends MessageHandler
/*   87:     */   {
/*   88:     */     private ErrorListener _errorListener;
/*   89:     */     
/*   90:     */     public MessageHandler(ErrorListener errorListener)
/*   91:     */     {
/*   92: 206 */       this._errorListener = errorListener;
/*   93:     */     }
/*   94:     */     
/*   95:     */     public void displayMessage(String msg)
/*   96:     */     {
/*   97: 210 */       if (this._errorListener == null) {
/*   98: 211 */         System.err.println(msg);
/*   99:     */       } else {
/*  100:     */         try
/*  101:     */         {
/*  102: 215 */           this._errorListener.warning(new TransformerException(msg));
/*  103:     */         }
/*  104:     */         catch (TransformerException e) {}
/*  105:     */       }
/*  106:     */     }
/*  107:     */   }
/*  108:     */   
/*  109:     */   protected TransformerImpl(Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory)
/*  110:     */   {
/*  111: 227 */     this(null, outputProperties, indentNumber, tfactory);
/*  112: 228 */     this._isIdentity = true;
/*  113:     */   }
/*  114:     */   
/*  115:     */   protected TransformerImpl(Translet translet, Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory)
/*  116:     */   {
/*  117: 235 */     this._translet = ((AbstractTranslet)translet);
/*  118: 236 */     this._properties = createOutputProperties(outputProperties);
/*  119: 237 */     this._propertiesClone = ((Properties)this._properties.clone());
/*  120: 238 */     this._indentNumber = indentNumber;
/*  121: 239 */     this._tfactory = tfactory;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public boolean isSecureProcessing()
/*  125:     */   {
/*  126: 247 */     return this._isSecureProcessing;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public void setSecureProcessing(boolean flag)
/*  130:     */   {
/*  131: 254 */     this._isSecureProcessing = flag;
/*  132:     */   }
/*  133:     */   
/*  134:     */   protected AbstractTranslet getTranslet()
/*  135:     */   {
/*  136: 262 */     return this._translet;
/*  137:     */   }
/*  138:     */   
/*  139:     */   public boolean isIdentity()
/*  140:     */   {
/*  141: 266 */     return this._isIdentity;
/*  142:     */   }
/*  143:     */   
/*  144:     */   public void transform(Source source, Result result)
/*  145:     */     throws TransformerException
/*  146:     */   {
/*  147: 279 */     if (!this._isIdentity)
/*  148:     */     {
/*  149: 280 */       if (this._translet == null)
/*  150:     */       {
/*  151: 281 */         ErrorMsg err = new ErrorMsg("JAXP_NO_TRANSLET_ERR");
/*  152: 282 */         throw new TransformerException(err.toString());
/*  153:     */       }
/*  154: 285 */       transferOutputProperties(this._translet);
/*  155:     */     }
/*  156: 288 */     SerializationHandler toHandler = getOutputHandler(result);
/*  157: 289 */     if (toHandler == null)
/*  158:     */     {
/*  159: 290 */       ErrorMsg err = new ErrorMsg("JAXP_NO_HANDLER_ERR");
/*  160: 291 */       throw new TransformerException(err.toString());
/*  161:     */     }
/*  162: 294 */     if ((this._uriResolver != null) && (!this._isIdentity)) {
/*  163: 295 */       this._translet.setDOMCache(this);
/*  164:     */     }
/*  165: 299 */     if (this._isIdentity) {
/*  166: 300 */       transferOutputProperties(toHandler);
/*  167:     */     }
/*  168: 303 */     transform(source, toHandler, this._encoding);
/*  169: 305 */     if ((result instanceof DOMResult)) {
/*  170: 306 */       ((DOMResult)result).setNode(this._tohFactory.getNode());
/*  171:     */     }
/*  172:     */   }
/*  173:     */   
/*  174:     */   public SerializationHandler getOutputHandler(Result result)
/*  175:     */     throws TransformerException
/*  176:     */   {
/*  177: 319 */     this._method = ((String)this._properties.get("method"));
/*  178:     */     
/*  179:     */ 
/*  180: 322 */     this._encoding = this._properties.getProperty("encoding");
/*  181:     */     
/*  182: 324 */     this._tohFactory = TransletOutputHandlerFactory.newInstance();
/*  183: 325 */     this._tohFactory.setEncoding(this._encoding);
/*  184: 326 */     if (this._method != null) {
/*  185: 327 */       this._tohFactory.setOutputMethod(this._method);
/*  186:     */     }
/*  187: 331 */     if (this._indentNumber >= 0) {
/*  188: 332 */       this._tohFactory.setIndentNumber(this._indentNumber);
/*  189:     */     }
/*  190:     */     try
/*  191:     */     {
/*  192: 338 */       if ((result instanceof SAXResult))
/*  193:     */       {
/*  194: 339 */         SAXResult target = (SAXResult)result;
/*  195: 340 */         ContentHandler handler = target.getHandler();
/*  196:     */         
/*  197: 342 */         this._tohFactory.setHandler(handler);
/*  198:     */         
/*  199:     */ 
/*  200:     */ 
/*  201:     */ 
/*  202:     */ 
/*  203:     */ 
/*  204: 349 */         LexicalHandler lexicalHandler = target.getLexicalHandler();
/*  205: 351 */         if (lexicalHandler != null) {
/*  206: 352 */           this._tohFactory.setLexicalHandler(lexicalHandler);
/*  207:     */         }
/*  208: 355 */         this._tohFactory.setOutputType(1);
/*  209: 356 */         return this._tohFactory.getSerializationHandler();
/*  210:     */       }
/*  211: 358 */       if ((result instanceof DOMResult))
/*  212:     */       {
/*  213: 359 */         this._tohFactory.setNode(((DOMResult)result).getNode());
/*  214: 360 */         this._tohFactory.setNextSibling(((DOMResult)result).getNextSibling());
/*  215: 361 */         this._tohFactory.setOutputType(2);
/*  216: 362 */         return this._tohFactory.getSerializationHandler();
/*  217:     */       }
/*  218: 364 */       if ((result instanceof StreamResult))
/*  219:     */       {
/*  220: 366 */         StreamResult target = (StreamResult)result;
/*  221:     */         
/*  222:     */ 
/*  223:     */ 
/*  224:     */ 
/*  225:     */ 
/*  226: 372 */         this._tohFactory.setOutputType(0);
/*  227:     */         
/*  228:     */ 
/*  229: 375 */         Writer writer = target.getWriter();
/*  230: 376 */         if (writer != null)
/*  231:     */         {
/*  232: 377 */           this._tohFactory.setWriter(writer);
/*  233: 378 */           return this._tohFactory.getSerializationHandler();
/*  234:     */         }
/*  235: 382 */         OutputStream ostream = target.getOutputStream();
/*  236: 383 */         if (ostream != null)
/*  237:     */         {
/*  238: 384 */           this._tohFactory.setOutputStream(ostream);
/*  239: 385 */           return this._tohFactory.getSerializationHandler();
/*  240:     */         }
/*  241: 389 */         String systemId = result.getSystemId();
/*  242: 390 */         if (systemId == null)
/*  243:     */         {
/*  244: 391 */           ErrorMsg err = new ErrorMsg("JAXP_NO_RESULT_ERR");
/*  245: 392 */           throw new TransformerException(err.toString());
/*  246:     */         }
/*  247: 398 */         URL url = null;
/*  248: 399 */         if (systemId.startsWith("file:"))
/*  249:     */         {
/*  250: 400 */           url = new URL(systemId);
/*  251: 401 */           this._tohFactory.setOutputStream(this._ostream = new FileOutputStream(url.getFile()));
/*  252:     */           
/*  253: 403 */           return this._tohFactory.getSerializationHandler();
/*  254:     */         }
/*  255: 405 */         if (systemId.startsWith("http:"))
/*  256:     */         {
/*  257: 406 */           url = new URL(systemId);
/*  258: 407 */           URLConnection connection = url.openConnection();
/*  259: 408 */           this._tohFactory.setOutputStream(this._ostream = connection.getOutputStream());
/*  260: 409 */           return this._tohFactory.getSerializationHandler();
/*  261:     */         }
/*  262: 413 */         url = new File(systemId).toURL();
/*  263: 414 */         this._tohFactory.setOutputStream(this._ostream = new FileOutputStream(url.getFile()));
/*  264:     */         
/*  265: 416 */         return this._tohFactory.getSerializationHandler();
/*  266:     */       }
/*  267:     */     }
/*  268:     */     catch (UnknownServiceException e)
/*  269:     */     {
/*  270: 422 */       throw new TransformerException(e);
/*  271:     */     }
/*  272:     */     catch (ParserConfigurationException e)
/*  273:     */     {
/*  274: 425 */       throw new TransformerException(e);
/*  275:     */     }
/*  276:     */     catch (IOException e)
/*  277:     */     {
/*  278: 429 */       throw new TransformerException(e);
/*  279:     */     }
/*  280: 431 */     return null;
/*  281:     */   }
/*  282:     */   
/*  283:     */   protected void setDOM(DOM dom)
/*  284:     */   {
/*  285: 438 */     this._dom = dom;
/*  286:     */   }
/*  287:     */   
/*  288:     */   private DOM getDOM(Source source)
/*  289:     */     throws TransformerException
/*  290:     */   {
/*  291:     */     try
/*  292:     */     {
/*  293: 446 */       DOM dom = null;
/*  294: 448 */       if (source != null)
/*  295:     */       {
/*  296:     */         DTMWSFilter wsfilter;
/*  297: 450 */         if ((this._translet != null) && ((this._translet instanceof StripFilter))) {
/*  298: 451 */           wsfilter = new DOMWSFilter(this._translet);
/*  299:     */         } else {
/*  300: 453 */           wsfilter = null;
/*  301:     */         }
/*  302: 456 */         boolean hasIdCall = this._translet != null ? this._translet.hasIdCall() : false;
/*  303: 459 */         if (this._dtmManager == null) {
/*  304: 460 */           this._dtmManager = ((XSLTCDTMManager)this._tfactory.getDTMManagerClass().newInstance());
/*  305:     */         }
/*  306: 464 */         dom = (DOM)this._dtmManager.getDTM(source, false, wsfilter, true, false, false, 0, hasIdCall);
/*  307:     */       }
/*  308: 466 */       else if (this._dom != null)
/*  309:     */       {
/*  310: 467 */         dom = this._dom;
/*  311: 468 */         this._dom = null;
/*  312:     */       }
/*  313:     */       else
/*  314:     */       {
/*  315: 470 */         return null;
/*  316:     */       }
/*  317: 473 */       if (!this._isIdentity) {
/*  318: 476 */         this._translet.prepassDocument(dom);
/*  319:     */       }
/*  320: 479 */       return dom;
/*  321:     */     }
/*  322:     */     catch (Exception e)
/*  323:     */     {
/*  324: 483 */       if (this._errorListener != null) {
/*  325: 484 */         postErrorToListener(e.getMessage());
/*  326:     */       }
/*  327: 486 */       throw new TransformerException(e);
/*  328:     */     }
/*  329:     */   }
/*  330:     */   
/*  331:     */   protected TransformerFactoryImpl getTransformerFactory()
/*  332:     */   {
/*  333: 495 */     return this._tfactory;
/*  334:     */   }
/*  335:     */   
/*  336:     */   protected TransletOutputHandlerFactory getTransletOutputHandlerFactory()
/*  337:     */   {
/*  338: 503 */     return this._tohFactory;
/*  339:     */   }
/*  340:     */   
/*  341:     */   private void transformIdentity(Source source, SerializationHandler handler)
/*  342:     */     throws Exception
/*  343:     */   {
/*  344: 510 */     if (source != null) {
/*  345: 511 */       this._sourceSystemId = source.getSystemId();
/*  346:     */     }
/*  347: 514 */     if ((source instanceof StreamSource))
/*  348:     */     {
/*  349: 515 */       StreamSource stream = (StreamSource)source;
/*  350: 516 */       InputStream streamInput = stream.getInputStream();
/*  351: 517 */       Reader streamReader = stream.getReader();
/*  352: 518 */       XMLReader reader = this._readerManager.getXMLReader();
/*  353:     */       try
/*  354:     */       {
/*  355:     */         try
/*  356:     */         {
/*  357: 523 */           reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
/*  358:     */         }
/*  359:     */         catch (SAXException e) {}
/*  360: 528 */         reader.setContentHandler(handler);
/*  361:     */         InputSource input;
/*  362: 532 */         if (streamInput != null)
/*  363:     */         {
/*  364: 533 */           input = new InputSource(streamInput);
/*  365: 534 */           input.setSystemId(this._sourceSystemId);
/*  366:     */         }
/*  367: 536 */         else if (streamReader != null)
/*  368:     */         {
/*  369: 537 */           input = new InputSource(streamReader);
/*  370: 538 */           input.setSystemId(this._sourceSystemId);
/*  371:     */         }
/*  372: 540 */         else if (this._sourceSystemId != null)
/*  373:     */         {
/*  374: 541 */           input = new InputSource(this._sourceSystemId);
/*  375:     */         }
/*  376:     */         else
/*  377:     */         {
/*  378: 544 */           ErrorMsg err = new ErrorMsg("JAXP_NO_SOURCE_ERR");
/*  379: 545 */           throw new TransformerException(err.toString());
/*  380:     */         }
/*  381: 549 */         reader.parse(input);
/*  382:     */       }
/*  383:     */       finally
/*  384:     */       {
/*  385: 551 */         this._readerManager.releaseXMLReader(reader);
/*  386:     */       }
/*  387:     */     }
/*  388: 553 */     else if ((source instanceof SAXSource))
/*  389:     */     {
/*  390: 554 */       SAXSource sax = (SAXSource)source;
/*  391: 555 */       XMLReader reader = sax.getXMLReader();
/*  392: 556 */       InputSource input = sax.getInputSource();
/*  393: 557 */       boolean userReader = true;
/*  394:     */       try
/*  395:     */       {
/*  396: 561 */         if (reader == null)
/*  397:     */         {
/*  398: 562 */           reader = this._readerManager.getXMLReader();
/*  399: 563 */           userReader = false;
/*  400:     */         }
/*  401:     */         try
/*  402:     */         {
/*  403: 568 */           reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
/*  404:     */         }
/*  405:     */         catch (SAXException e) {}
/*  406: 573 */         reader.setContentHandler(handler);
/*  407:     */         
/*  408:     */ 
/*  409: 576 */         reader.parse(input);
/*  410:     */       }
/*  411:     */       finally
/*  412:     */       {
/*  413: 578 */         if (!userReader) {
/*  414: 579 */           this._readerManager.releaseXMLReader(reader);
/*  415:     */         }
/*  416:     */       }
/*  417:     */     }
/*  418: 582 */     else if ((source instanceof DOMSource))
/*  419:     */     {
/*  420: 583 */       DOMSource domsrc = (DOMSource)source;
/*  421: 584 */       new DOM2TO(domsrc.getNode(), handler).parse();
/*  422:     */     }
/*  423: 585 */     else if ((source instanceof XSLTCSource))
/*  424:     */     {
/*  425: 586 */       DOM dom = ((XSLTCSource)source).getDOM(null, this._translet);
/*  426: 587 */       ((SAXImpl)dom).copy(handler);
/*  427:     */     }
/*  428:     */     else
/*  429:     */     {
/*  430: 589 */       ErrorMsg err = new ErrorMsg("JAXP_NO_SOURCE_ERR");
/*  431: 590 */       throw new TransformerException(err.toString());
/*  432:     */     }
/*  433:     */   }
/*  434:     */   
/*  435:     */   private void transform(Source source, SerializationHandler handler, String encoding)
/*  436:     */     throws TransformerException
/*  437:     */   {
/*  438:     */     try
/*  439:     */     {
/*  440: 609 */       if ((((source instanceof StreamSource)) && (source.getSystemId() == null) && (((StreamSource)source).getInputStream() == null) && (((StreamSource)source).getReader() == null)) || (((source instanceof SAXSource)) && (((SAXSource)source).getInputSource() == null) && (((SAXSource)source).getXMLReader() == null)) || (((source instanceof DOMSource)) && (((DOMSource)source).getNode() == null)))
/*  441:     */       {
/*  442: 617 */         DocumentBuilderFactory builderF = DocumentBuilderFactory.newInstance();
/*  443:     */         
/*  444: 619 */         DocumentBuilder builder = builderF.newDocumentBuilder();
/*  445:     */         
/*  446: 621 */         String systemID = source.getSystemId();
/*  447: 622 */         source = new DOMSource(builder.newDocument());
/*  448: 625 */         if (systemID != null) {
/*  449: 626 */           source.setSystemId(systemID);
/*  450:     */         }
/*  451:     */       }
/*  452: 629 */       if (this._isIdentity) {
/*  453: 630 */         transformIdentity(source, handler);
/*  454:     */       } else {
/*  455: 632 */         this._translet.transform(getDOM(source), handler);
/*  456:     */       }
/*  457:     */     }
/*  458:     */     catch (TransletException e)
/*  459:     */     {
/*  460: 635 */       if (this._errorListener != null) {
/*  461: 635 */         postErrorToListener(e.getMessage());
/*  462:     */       }
/*  463: 636 */       throw new TransformerException(e);
/*  464:     */     }
/*  465:     */     catch (RuntimeException e)
/*  466:     */     {
/*  467: 638 */       if (this._errorListener != null) {
/*  468: 638 */         postErrorToListener(e.getMessage());
/*  469:     */       }
/*  470: 639 */       throw new TransformerException(e);
/*  471:     */     }
/*  472:     */     catch (Exception e)
/*  473:     */     {
/*  474: 641 */       if (this._errorListener != null) {
/*  475: 641 */         postErrorToListener(e.getMessage());
/*  476:     */       }
/*  477: 642 */       throw new TransformerException(e);
/*  478:     */     }
/*  479:     */     finally
/*  480:     */     {
/*  481: 644 */       this._dtmManager = null;
/*  482:     */     }
/*  483: 648 */     if (this._ostream != null)
/*  484:     */     {
/*  485:     */       try
/*  486:     */       {
/*  487: 650 */         this._ostream.close();
/*  488:     */       }
/*  489:     */       catch (IOException e) {}
/*  490: 653 */       this._ostream = null;
/*  491:     */     }
/*  492:     */   }
/*  493:     */   
/*  494:     */   public ErrorListener getErrorListener()
/*  495:     */   {
/*  496: 664 */     return this._errorListener;
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void setErrorListener(ErrorListener listener)
/*  500:     */     throws IllegalArgumentException
/*  501:     */   {
/*  502: 678 */     if (listener == null)
/*  503:     */     {
/*  504: 679 */       ErrorMsg err = new ErrorMsg("ERROR_LISTENER_NULL_ERR", "Transformer");
/*  505:     */       
/*  506: 681 */       throw new IllegalArgumentException(err.toString());
/*  507:     */     }
/*  508: 683 */     this._errorListener = listener;
/*  509: 686 */     if (this._translet != null) {
/*  510: 687 */       this._translet.setMessageHandler(new MessageHandler(this._errorListener));
/*  511:     */     }
/*  512:     */   }
/*  513:     */   
/*  514:     */   private void postErrorToListener(String message)
/*  515:     */   {
/*  516:     */     try
/*  517:     */     {
/*  518: 695 */       this._errorListener.error(new TransformerException(message));
/*  519:     */     }
/*  520:     */     catch (TransformerException e) {}
/*  521:     */   }
/*  522:     */   
/*  523:     */   private void postWarningToListener(String message)
/*  524:     */   {
/*  525:     */     try
/*  526:     */     {
/*  527: 707 */       this._errorListener.warning(new TransformerException(message));
/*  528:     */     }
/*  529:     */     catch (TransformerException e) {}
/*  530:     */   }
/*  531:     */   
/*  532:     */   private String makeCDATAString(org.apache.xalan.xsltc.runtime.Hashtable cdata)
/*  533:     */   {
/*  534: 721 */     if (cdata == null) {
/*  535: 721 */       return null;
/*  536:     */     }
/*  537: 723 */     StringBuffer result = new StringBuffer();
/*  538:     */     
/*  539:     */ 
/*  540: 726 */     Enumeration elements = cdata.keys();
/*  541: 727 */     if (elements.hasMoreElements())
/*  542:     */     {
/*  543: 728 */       result.append((String)elements.nextElement());
/*  544: 729 */       while (elements.hasMoreElements())
/*  545:     */       {
/*  546: 730 */         String element = (String)elements.nextElement();
/*  547: 731 */         result.append(' ');
/*  548: 732 */         result.append(element);
/*  549:     */       }
/*  550:     */     }
/*  551: 736 */     return result.toString();
/*  552:     */   }
/*  553:     */   
/*  554:     */   public Properties getOutputProperties()
/*  555:     */   {
/*  556: 751 */     return (Properties)this._properties.clone();
/*  557:     */   }
/*  558:     */   
/*  559:     */   public String getOutputProperty(String name)
/*  560:     */     throws IllegalArgumentException
/*  561:     */   {
/*  562: 766 */     if (!validOutputProperty(name))
/*  563:     */     {
/*  564: 767 */       ErrorMsg err = new ErrorMsg("JAXP_UNKNOWN_PROP_ERR", name);
/*  565: 768 */       throw new IllegalArgumentException(err.toString());
/*  566:     */     }
/*  567: 770 */     return this._properties.getProperty(name);
/*  568:     */   }
/*  569:     */   
/*  570:     */   public void setOutputProperties(Properties properties)
/*  571:     */     throws IllegalArgumentException
/*  572:     */   {
/*  573: 785 */     if (properties != null)
/*  574:     */     {
/*  575: 786 */       Enumeration names = properties.propertyNames();
/*  576: 788 */       while (names.hasMoreElements())
/*  577:     */       {
/*  578: 789 */         String name = (String)names.nextElement();
/*  579: 792 */         if (!isDefaultProperty(name, properties)) {
/*  580: 794 */           if (validOutputProperty(name))
/*  581:     */           {
/*  582: 795 */             this._properties.setProperty(name, properties.getProperty(name));
/*  583:     */           }
/*  584:     */           else
/*  585:     */           {
/*  586: 798 */             ErrorMsg err = new ErrorMsg("JAXP_UNKNOWN_PROP_ERR", name);
/*  587: 799 */             throw new IllegalArgumentException(err.toString());
/*  588:     */           }
/*  589:     */         }
/*  590:     */       }
/*  591:     */     }
/*  592:     */     else
/*  593:     */     {
/*  594: 804 */       this._properties = this._propertiesClone;
/*  595:     */     }
/*  596:     */   }
/*  597:     */   
/*  598:     */   public void setOutputProperty(String name, String value)
/*  599:     */     throws IllegalArgumentException
/*  600:     */   {
/*  601: 821 */     if (!validOutputProperty(name))
/*  602:     */     {
/*  603: 822 */       ErrorMsg err = new ErrorMsg("JAXP_UNKNOWN_PROP_ERR", name);
/*  604: 823 */       throw new IllegalArgumentException(err.toString());
/*  605:     */     }
/*  606: 825 */     this._properties.setProperty(name, value);
/*  607:     */   }
/*  608:     */   
/*  609:     */   private void transferOutputProperties(AbstractTranslet translet)
/*  610:     */   {
/*  611: 835 */     if (this._properties == null) {
/*  612: 835 */       return;
/*  613:     */     }
/*  614: 838 */     Enumeration names = this._properties.propertyNames();
/*  615: 839 */     while (names.hasMoreElements())
/*  616:     */     {
/*  617: 841 */       String name = (String)names.nextElement();
/*  618: 842 */       String value = (String)this._properties.get(name);
/*  619: 845 */       if (value != null) {
/*  620: 848 */         if (name.equals("encoding"))
/*  621:     */         {
/*  622: 849 */           translet._encoding = value;
/*  623:     */         }
/*  624: 851 */         else if (name.equals("method"))
/*  625:     */         {
/*  626: 852 */           translet._method = value;
/*  627:     */         }
/*  628: 854 */         else if (name.equals("doctype-public"))
/*  629:     */         {
/*  630: 855 */           translet._doctypePublic = value;
/*  631:     */         }
/*  632: 857 */         else if (name.equals("doctype-system"))
/*  633:     */         {
/*  634: 858 */           translet._doctypeSystem = value;
/*  635:     */         }
/*  636: 860 */         else if (name.equals("media-type"))
/*  637:     */         {
/*  638: 861 */           translet._mediaType = value;
/*  639:     */         }
/*  640: 863 */         else if (name.equals("standalone"))
/*  641:     */         {
/*  642: 864 */           translet._standalone = value;
/*  643:     */         }
/*  644: 866 */         else if (name.equals("version"))
/*  645:     */         {
/*  646: 867 */           translet._version = value;
/*  647:     */         }
/*  648: 869 */         else if (name.equals("omit-xml-declaration"))
/*  649:     */         {
/*  650: 870 */           translet._omitHeader = ((value != null) && (value.toLowerCase().equals("yes")));
/*  651:     */         }
/*  652: 873 */         else if (name.equals("indent"))
/*  653:     */         {
/*  654: 874 */           translet._indent = ((value != null) && (value.toLowerCase().equals("yes")));
/*  655:     */         }
/*  656: 877 */         else if ((name.equals("cdata-section-elements")) && 
/*  657: 878 */           (value != null))
/*  658:     */         {
/*  659: 879 */           translet._cdata = null;
/*  660: 880 */           StringTokenizer e = new StringTokenizer(value);
/*  661: 881 */           while (e.hasMoreTokens()) {
/*  662: 882 */             translet.addCdataElement(e.nextToken());
/*  663:     */           }
/*  664:     */         }
/*  665:     */       }
/*  666:     */     }
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void transferOutputProperties(SerializationHandler handler)
/*  670:     */   {
/*  671: 896 */     if (this._properties == null) {
/*  672: 896 */       return;
/*  673:     */     }
/*  674: 898 */     String doctypePublic = null;
/*  675: 899 */     String doctypeSystem = null;
/*  676:     */     
/*  677:     */ 
/*  678: 902 */     Enumeration names = this._properties.propertyNames();
/*  679: 903 */     while (names.hasMoreElements())
/*  680:     */     {
/*  681: 905 */       String name = (String)names.nextElement();
/*  682: 906 */       String value = (String)this._properties.get(name);
/*  683: 909 */       if (value != null) {
/*  684: 912 */         if (name.equals("doctype-public"))
/*  685:     */         {
/*  686: 913 */           doctypePublic = value;
/*  687:     */         }
/*  688: 915 */         else if (name.equals("doctype-system"))
/*  689:     */         {
/*  690: 916 */           doctypeSystem = value;
/*  691:     */         }
/*  692: 918 */         else if (name.equals("media-type"))
/*  693:     */         {
/*  694: 919 */           handler.setMediaType(value);
/*  695:     */         }
/*  696: 921 */         else if (name.equals("standalone"))
/*  697:     */         {
/*  698: 922 */           handler.setStandalone(value);
/*  699:     */         }
/*  700: 924 */         else if (name.equals("version"))
/*  701:     */         {
/*  702: 925 */           handler.setVersion(value);
/*  703:     */         }
/*  704: 927 */         else if (name.equals("omit-xml-declaration"))
/*  705:     */         {
/*  706: 928 */           handler.setOmitXMLDeclaration((value != null) && (value.toLowerCase().equals("yes")));
/*  707:     */         }
/*  708: 931 */         else if (name.equals("indent"))
/*  709:     */         {
/*  710: 932 */           handler.setIndent((value != null) && (value.toLowerCase().equals("yes")));
/*  711:     */         }
/*  712: 935 */         else if ((name.equals("cdata-section-elements")) && 
/*  713: 936 */           (value != null))
/*  714:     */         {
/*  715: 937 */           StringTokenizer e = new StringTokenizer(value);
/*  716: 938 */           Vector uriAndLocalNames = null;
/*  717: 939 */           while (e.hasMoreTokens())
/*  718:     */           {
/*  719: 940 */             String token = e.nextToken();
/*  720:     */             
/*  721:     */ 
/*  722:     */ 
/*  723: 944 */             int lastcolon = token.lastIndexOf(':');
/*  724:     */             String uri;
/*  725:     */             String localName;
/*  726: 947 */             if (lastcolon > 0)
/*  727:     */             {
/*  728: 948 */               uri = token.substring(0, lastcolon);
/*  729: 949 */               localName = token.substring(lastcolon + 1);
/*  730:     */             }
/*  731:     */             else
/*  732:     */             {
/*  733: 953 */               uri = null;
/*  734: 954 */               localName = token;
/*  735:     */             }
/*  736: 957 */             if (uriAndLocalNames == null) {
/*  737: 958 */               uriAndLocalNames = new Vector();
/*  738:     */             }
/*  739: 961 */             uriAndLocalNames.addElement(uri);
/*  740: 962 */             uriAndLocalNames.addElement(localName);
/*  741:     */           }
/*  742: 964 */           handler.setCdataSectionElements(uriAndLocalNames);
/*  743:     */         }
/*  744:     */       }
/*  745:     */     }
/*  746: 970 */     if ((doctypePublic != null) || (doctypeSystem != null)) {
/*  747: 971 */       handler.setDoctype(doctypeSystem, doctypePublic);
/*  748:     */     }
/*  749:     */   }
/*  750:     */   
/*  751:     */   private Properties createOutputProperties(Properties outputProperties)
/*  752:     */   {
/*  753: 982 */     Properties defaults = new Properties();
/*  754: 983 */     setDefaults(defaults, "xml");
/*  755:     */     
/*  756:     */ 
/*  757: 986 */     Properties base = new Properties(defaults);
/*  758: 987 */     if (outputProperties != null)
/*  759:     */     {
/*  760: 988 */       Enumeration names = outputProperties.propertyNames();
/*  761: 989 */       while (names.hasMoreElements())
/*  762:     */       {
/*  763: 990 */         String name = (String)names.nextElement();
/*  764: 991 */         base.setProperty(name, outputProperties.getProperty(name));
/*  765:     */       }
/*  766:     */     }
/*  767:     */     else
/*  768:     */     {
/*  769: 995 */       base.setProperty("encoding", this._translet._encoding);
/*  770: 996 */       if (this._translet._method != null) {
/*  771: 997 */         base.setProperty("method", this._translet._method);
/*  772:     */       }
/*  773:     */     }
/*  774:1001 */     String method = base.getProperty("method");
/*  775:1002 */     if (method != null) {
/*  776:1003 */       if (method.equals("html")) {
/*  777:1004 */         setDefaults(defaults, "html");
/*  778:1006 */       } else if (method.equals("text")) {
/*  779:1007 */         setDefaults(defaults, "text");
/*  780:     */       }
/*  781:     */     }
/*  782:1011 */     return base;
/*  783:     */   }
/*  784:     */   
/*  785:     */   private void setDefaults(Properties props, String method)
/*  786:     */   {
/*  787:1022 */     Properties method_props = OutputPropertiesFactory.getDefaultMethodProperties(method);
/*  788:     */     
/*  789:     */ 
/*  790:1025 */     Enumeration names = method_props.propertyNames();
/*  791:1026 */     while (names.hasMoreElements())
/*  792:     */     {
/*  793:1028 */       String name = (String)names.nextElement();
/*  794:1029 */       props.setProperty(name, method_props.getProperty(name));
/*  795:     */     }
/*  796:     */   }
/*  797:     */   
/*  798:     */   private boolean validOutputProperty(String name)
/*  799:     */   {
/*  800:1038 */     return (name.equals("encoding")) || (name.equals("method")) || (name.equals("indent")) || (name.equals("doctype-public")) || (name.equals("doctype-system")) || (name.equals("cdata-section-elements")) || (name.equals("media-type")) || (name.equals("omit-xml-declaration")) || (name.equals("standalone")) || (name.equals("version")) || (name.charAt(0) == '{');
/*  801:     */   }
/*  802:     */   
/*  803:     */   private boolean isDefaultProperty(String name, Properties properties)
/*  804:     */   {
/*  805:1055 */     return properties.get(name) == null;
/*  806:     */   }
/*  807:     */   
/*  808:     */   public void setParameter(String name, Object value)
/*  809:     */   {
/*  810:1069 */     if (value == null)
/*  811:     */     {
/*  812:1070 */       ErrorMsg err = new ErrorMsg("JAXP_INVALID_SET_PARAM_VALUE", name);
/*  813:1071 */       throw new IllegalArgumentException(err.toString());
/*  814:     */     }
/*  815:1074 */     if (this._isIdentity)
/*  816:     */     {
/*  817:1075 */       if (this._parameters == null) {
/*  818:1076 */         this._parameters = new org.apache.xalan.xsltc.runtime.Hashtable();
/*  819:     */       }
/*  820:1078 */       this._parameters.put(name, value);
/*  821:     */     }
/*  822:     */     else
/*  823:     */     {
/*  824:1081 */       this._translet.addParameter(name, value);
/*  825:     */     }
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void clearParameters()
/*  829:     */   {
/*  830:1091 */     if ((this._isIdentity) && (this._parameters != null)) {
/*  831:1092 */       this._parameters.clear();
/*  832:     */     } else {
/*  833:1095 */       this._translet.clearParameters();
/*  834:     */     }
/*  835:     */   }
/*  836:     */   
/*  837:     */   public final Object getParameter(String name)
/*  838:     */   {
/*  839:1108 */     if (this._isIdentity) {
/*  840:1109 */       return this._parameters != null ? this._parameters.get(name) : null;
/*  841:     */     }
/*  842:1112 */     return this._translet.getParameter(name);
/*  843:     */   }
/*  844:     */   
/*  845:     */   public URIResolver getURIResolver()
/*  846:     */   {
/*  847:1123 */     return this._uriResolver;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public void setURIResolver(URIResolver resolver)
/*  851:     */   {
/*  852:1133 */     this._uriResolver = resolver;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public DOM retrieveDocument(String baseURI, String href, Translet translet)
/*  856:     */   {
/*  857:     */     try
/*  858:     */     {
/*  859:1153 */       if (href.length() == 0) {
/*  860:1154 */         href = new String(baseURI);
/*  861:     */       }
/*  862:1165 */       Source resolvedSource = this._uriResolver.resolve(href, baseURI);
/*  863:1166 */       if (resolvedSource == null)
/*  864:     */       {
/*  865:1167 */         StreamSource streamSource = new StreamSource(SystemIDResolver.getAbsoluteURI(href, baseURI));
/*  866:     */         
/*  867:1169 */         return getDOM(streamSource);
/*  868:     */       }
/*  869:1172 */       return getDOM(resolvedSource);
/*  870:     */     }
/*  871:     */     catch (TransformerException e)
/*  872:     */     {
/*  873:1175 */       if (this._errorListener != null) {
/*  874:1176 */         postErrorToListener("File not found: " + e.getMessage());
/*  875:     */       }
/*  876:     */     }
/*  877:1177 */     return null;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public void error(TransformerException e)
/*  881:     */     throws TransformerException
/*  882:     */   {
/*  883:1195 */     Throwable wrapped = e.getException();
/*  884:1196 */     if (wrapped != null) {
/*  885:1197 */       System.err.println(new ErrorMsg("ERROR_PLUS_WRAPPED_MSG", e.getMessageAndLocation(), wrapped.getMessage()));
/*  886:     */     } else {
/*  887:1201 */       System.err.println(new ErrorMsg("ERROR_MSG", e.getMessageAndLocation()));
/*  888:     */     }
/*  889:1204 */     throw e;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public void fatalError(TransformerException e)
/*  893:     */     throws TransformerException
/*  894:     */   {
/*  895:1223 */     Throwable wrapped = e.getException();
/*  896:1224 */     if (wrapped != null) {
/*  897:1225 */       System.err.println(new ErrorMsg("FATAL_ERR_PLUS_WRAPPED_MSG", e.getMessageAndLocation(), wrapped.getMessage()));
/*  898:     */     } else {
/*  899:1229 */       System.err.println(new ErrorMsg("FATAL_ERR_MSG", e.getMessageAndLocation()));
/*  900:     */     }
/*  901:1232 */     throw e;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public void warning(TransformerException e)
/*  905:     */     throws TransformerException
/*  906:     */   {
/*  907:1251 */     Throwable wrapped = e.getException();
/*  908:1252 */     if (wrapped != null) {
/*  909:1253 */       System.err.println(new ErrorMsg("WARNING_PLUS_WRAPPED_MSG", e.getMessageAndLocation(), wrapped.getMessage()));
/*  910:     */     } else {
/*  911:1257 */       System.err.println(new ErrorMsg("WARNING_MSG", e.getMessageAndLocation()));
/*  912:     */     }
/*  913:     */   }
/*  914:     */   
/*  915:     */   public void reset()
/*  916:     */   {
/*  917:1270 */     this._method = null;
/*  918:1271 */     this._encoding = null;
/*  919:1272 */     this._sourceSystemId = null;
/*  920:1273 */     this._errorListener = this;
/*  921:1274 */     this._uriResolver = null;
/*  922:1275 */     this._dom = null;
/*  923:1276 */     this._parameters = null;
/*  924:1277 */     this._indentNumber = 0;
/*  925:1278 */     setOutputProperties(null);
/*  926:     */   }
/*  927:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.TransformerImpl
 * JD-Core Version:    0.7.0.1
 */