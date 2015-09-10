/*    1:     */ package org.apache.xalan.transformer;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ import javax.xml.transform.ErrorListener;
/*    6:     */ import javax.xml.transform.Result;
/*    7:     */ import javax.xml.transform.Transformer;
/*    8:     */ import javax.xml.transform.TransformerException;
/*    9:     */ import javax.xml.transform.sax.TransformerHandler;
/*   10:     */ import org.apache.xalan.res.XSLMessages;
/*   11:     */ import org.apache.xml.dtm.DTM;
/*   12:     */ import org.apache.xml.dtm.ref.IncrementalSAXSource_Filter;
/*   13:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM;
/*   14:     */ import org.apache.xml.serializer.SerializationHandler;
/*   15:     */ import org.apache.xpath.XPathContext;
/*   16:     */ import org.xml.sax.Attributes;
/*   17:     */ import org.xml.sax.ContentHandler;
/*   18:     */ import org.xml.sax.DTDHandler;
/*   19:     */ import org.xml.sax.EntityResolver;
/*   20:     */ import org.xml.sax.ErrorHandler;
/*   21:     */ import org.xml.sax.InputSource;
/*   22:     */ import org.xml.sax.Locator;
/*   23:     */ import org.xml.sax.SAXException;
/*   24:     */ import org.xml.sax.SAXParseException;
/*   25:     */ import org.xml.sax.ext.DeclHandler;
/*   26:     */ import org.xml.sax.ext.LexicalHandler;
/*   27:     */ 
/*   28:     */ public class TransformerHandlerImpl
/*   29:     */   implements EntityResolver, DTDHandler, ContentHandler, ErrorHandler, LexicalHandler, TransformerHandler, DeclHandler
/*   30:     */ {
/*   31:     */   private final boolean m_optimizer;
/*   32:     */   private final boolean m_incremental;
/*   33:     */   private final boolean m_source_location;
/*   34:  75 */   private boolean m_insideParse = false;
/*   35:     */   
/*   36:     */   public TransformerHandlerImpl(TransformerImpl transformer, boolean doFragment, String baseSystemID)
/*   37:     */   {
/*   38:  94 */     this.m_transformer = transformer;
/*   39:  95 */     this.m_baseSystemID = baseSystemID;
/*   40:     */     
/*   41:  97 */     XPathContext xctxt = transformer.getXPathContext();
/*   42:  98 */     DTM dtm = xctxt.getDTM(null, true, transformer, true, true);
/*   43:     */     
/*   44: 100 */     this.m_dtm = dtm;
/*   45: 101 */     dtm.setDocumentBaseURI(baseSystemID);
/*   46:     */     
/*   47: 103 */     this.m_contentHandler = dtm.getContentHandler();
/*   48: 104 */     this.m_dtdHandler = dtm.getDTDHandler();
/*   49: 105 */     this.m_entityResolver = dtm.getEntityResolver();
/*   50: 106 */     this.m_errorHandler = dtm.getErrorHandler();
/*   51: 107 */     this.m_lexicalHandler = dtm.getLexicalHandler();
/*   52: 108 */     this.m_incremental = transformer.getIncremental();
/*   53: 109 */     this.m_optimizer = transformer.getOptimize();
/*   54: 110 */     this.m_source_location = transformer.getSource_location();
/*   55:     */   }
/*   56:     */   
/*   57:     */   protected void clearCoRoutine()
/*   58:     */   {
/*   59: 118 */     clearCoRoutine(null);
/*   60:     */   }
/*   61:     */   
/*   62:     */   protected void clearCoRoutine(SAXException ex)
/*   63:     */   {
/*   64: 126 */     if (null != ex) {
/*   65: 127 */       this.m_transformer.setExceptionThrown(ex);
/*   66:     */     }
/*   67: 129 */     if ((this.m_dtm instanceof SAX2DTM))
/*   68:     */     {
/*   69: 131 */       if (DEBUG) {
/*   70: 132 */         System.err.println("In clearCoRoutine...");
/*   71:     */       }
/*   72:     */       try
/*   73:     */       {
/*   74: 135 */         SAX2DTM sax2dtm = (SAX2DTM)this.m_dtm;
/*   75: 136 */         if ((null != this.m_contentHandler) && ((this.m_contentHandler instanceof IncrementalSAXSource_Filter)))
/*   76:     */         {
/*   77: 139 */           IncrementalSAXSource_Filter sp = (IncrementalSAXSource_Filter)this.m_contentHandler;
/*   78:     */           
/*   79:     */ 
/*   80: 142 */           sp.deliverMoreNodes(false);
/*   81:     */         }
/*   82: 145 */         sax2dtm.clearCoRoutine(true);
/*   83: 146 */         this.m_contentHandler = null;
/*   84: 147 */         this.m_dtdHandler = null;
/*   85: 148 */         this.m_entityResolver = null;
/*   86: 149 */         this.m_errorHandler = null;
/*   87: 150 */         this.m_lexicalHandler = null;
/*   88:     */       }
/*   89:     */       catch (Throwable throwable)
/*   90:     */       {
/*   91: 154 */         throwable.printStackTrace();
/*   92:     */       }
/*   93: 157 */       if (DEBUG) {
/*   94: 158 */         System.err.println("...exiting clearCoRoutine");
/*   95:     */       }
/*   96:     */     }
/*   97:     */   }
/*   98:     */   
/*   99:     */   public void setResult(Result result)
/*  100:     */     throws IllegalArgumentException
/*  101:     */   {
/*  102: 177 */     if (null == result) {
/*  103: 178 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_RESULT_NULL", null));
/*  104:     */     }
/*  105:     */     try
/*  106:     */     {
/*  107: 185 */       SerializationHandler xoh = this.m_transformer.createSerializationHandler(result);
/*  108:     */       
/*  109: 187 */       this.m_transformer.setSerializationHandler(xoh);
/*  110:     */     }
/*  111:     */     catch (TransformerException te)
/*  112:     */     {
/*  113: 191 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_RESULT_COULD_NOT_BE_SET", null));
/*  114:     */     }
/*  115: 194 */     this.m_result = result;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public void setSystemId(String systemID)
/*  119:     */   {
/*  120: 204 */     this.m_baseSystemID = systemID;
/*  121: 205 */     this.m_dtm.setDocumentBaseURI(systemID);
/*  122:     */   }
/*  123:     */   
/*  124:     */   public String getSystemId()
/*  125:     */   {
/*  126: 215 */     return this.m_baseSystemID;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public Transformer getTransformer()
/*  130:     */   {
/*  131: 226 */     return this.m_transformer;
/*  132:     */   }
/*  133:     */   
/*  134:     */   public InputSource resolveEntity(String publicId, String systemId)
/*  135:     */     throws SAXException, IOException
/*  136:     */   {
/*  137: 252 */     if (this.m_entityResolver != null) {
/*  138: 254 */       return this.m_entityResolver.resolveEntity(publicId, systemId);
/*  139:     */     }
/*  140: 258 */     return null;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public void notationDecl(String name, String publicId, String systemId)
/*  144:     */     throws SAXException
/*  145:     */   {
/*  146: 280 */     if (this.m_dtdHandler != null) {
/*  147: 282 */       this.m_dtdHandler.notationDecl(name, publicId, systemId);
/*  148:     */     }
/*  149:     */   }
/*  150:     */   
/*  151:     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
/*  152:     */     throws SAXException
/*  153:     */   {
/*  154: 302 */     if (this.m_dtdHandler != null) {
/*  155: 304 */       this.m_dtdHandler.unparsedEntityDecl(name, publicId, systemId, notationName);
/*  156:     */     }
/*  157:     */   }
/*  158:     */   
/*  159:     */   public void setDocumentLocator(Locator locator)
/*  160:     */   {
/*  161: 321 */     if (DEBUG) {
/*  162: 322 */       System.out.println("TransformerHandlerImpl#setDocumentLocator: " + locator.getSystemId());
/*  163:     */     }
/*  164: 325 */     this.m_locator = locator;
/*  165: 327 */     if (null == this.m_baseSystemID) {
/*  166: 329 */       setSystemId(locator.getSystemId());
/*  167:     */     }
/*  168: 332 */     if (this.m_contentHandler != null) {
/*  169: 334 */       this.m_contentHandler.setDocumentLocator(locator);
/*  170:     */     }
/*  171:     */   }
/*  172:     */   
/*  173:     */   public void startDocument()
/*  174:     */     throws SAXException
/*  175:     */   {
/*  176: 348 */     if (DEBUG) {
/*  177: 349 */       System.out.println("TransformerHandlerImpl#startDocument");
/*  178:     */     }
/*  179: 351 */     this.m_insideParse = true;
/*  180: 355 */     if (this.m_contentHandler != null)
/*  181:     */     {
/*  182: 358 */       if (this.m_incremental)
/*  183:     */       {
/*  184: 360 */         this.m_transformer.setSourceTreeDocForThread(this.m_dtm.getDocument());
/*  185:     */         
/*  186: 362 */         int cpriority = Thread.currentThread().getPriority();
/*  187:     */         
/*  188:     */ 
/*  189:     */ 
/*  190: 366 */         this.m_transformer.runTransformThread(cpriority);
/*  191:     */       }
/*  192: 373 */       this.m_contentHandler.startDocument();
/*  193:     */     }
/*  194:     */   }
/*  195:     */   
/*  196:     */   public void endDocument()
/*  197:     */     throws SAXException
/*  198:     */   {
/*  199: 391 */     if (DEBUG) {
/*  200: 392 */       System.out.println("TransformerHandlerImpl#endDocument");
/*  201:     */     }
/*  202: 394 */     this.m_insideParse = false;
/*  203: 396 */     if (this.m_contentHandler != null) {
/*  204: 398 */       this.m_contentHandler.endDocument();
/*  205:     */     }
/*  206: 401 */     if (this.m_incremental)
/*  207:     */     {
/*  208: 403 */       this.m_transformer.waitTransformThread();
/*  209:     */     }
/*  210:     */     else
/*  211:     */     {
/*  212: 407 */       this.m_transformer.setSourceTreeDocForThread(this.m_dtm.getDocument());
/*  213: 408 */       this.m_transformer.run();
/*  214:     */     }
/*  215:     */   }
/*  216:     */   
/*  217:     */   public void startPrefixMapping(String prefix, String uri)
/*  218:     */     throws SAXException
/*  219:     */   {
/*  220: 447 */     if (DEBUG) {
/*  221: 448 */       System.out.println("TransformerHandlerImpl#startPrefixMapping: " + prefix + ", " + uri);
/*  222:     */     }
/*  223: 451 */     if (this.m_contentHandler != null) {
/*  224: 453 */       this.m_contentHandler.startPrefixMapping(prefix, uri);
/*  225:     */     }
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void endPrefixMapping(String prefix)
/*  229:     */     throws SAXException
/*  230:     */   {
/*  231: 468 */     if (DEBUG) {
/*  232: 469 */       System.out.println("TransformerHandlerImpl#endPrefixMapping: " + prefix);
/*  233:     */     }
/*  234: 472 */     if (this.m_contentHandler != null) {
/*  235: 474 */       this.m_contentHandler.endPrefixMapping(prefix);
/*  236:     */     }
/*  237:     */   }
/*  238:     */   
/*  239:     */   public void startElement(String uri, String localName, String qName, Attributes atts)
/*  240:     */     throws SAXException
/*  241:     */   {
/*  242: 495 */     if (DEBUG) {
/*  243: 496 */       System.out.println("TransformerHandlerImpl#startElement: " + qName);
/*  244:     */     }
/*  245: 498 */     if (this.m_contentHandler != null) {
/*  246: 500 */       this.m_contentHandler.startElement(uri, localName, qName, atts);
/*  247:     */     }
/*  248:     */   }
/*  249:     */   
/*  250:     */   public void endElement(String uri, String localName, String qName)
/*  251:     */     throws SAXException
/*  252:     */   {
/*  253: 519 */     if (DEBUG) {
/*  254: 520 */       System.out.println("TransformerHandlerImpl#endElement: " + qName);
/*  255:     */     }
/*  256: 522 */     if (this.m_contentHandler != null) {
/*  257: 524 */       this.m_contentHandler.endElement(uri, localName, qName);
/*  258:     */     }
/*  259:     */   }
/*  260:     */   
/*  261:     */   public void characters(char[] ch, int start, int length)
/*  262:     */     throws SAXException
/*  263:     */   {
/*  264: 541 */     if (DEBUG) {
/*  265: 542 */       System.out.println("TransformerHandlerImpl#characters: " + start + ", " + length);
/*  266:     */     }
/*  267: 545 */     if (this.m_contentHandler != null) {
/*  268: 547 */       this.m_contentHandler.characters(ch, start, length);
/*  269:     */     }
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/*  273:     */     throws SAXException
/*  274:     */   {
/*  275: 565 */     if (DEBUG) {
/*  276: 566 */       System.out.println("TransformerHandlerImpl#ignorableWhitespace: " + start + ", " + length);
/*  277:     */     }
/*  278: 569 */     if (this.m_contentHandler != null) {
/*  279: 571 */       this.m_contentHandler.ignorableWhitespace(ch, start, length);
/*  280:     */     }
/*  281:     */   }
/*  282:     */   
/*  283:     */   public void processingInstruction(String target, String data)
/*  284:     */     throws SAXException
/*  285:     */   {
/*  286: 588 */     if (DEBUG) {
/*  287: 589 */       System.out.println("TransformerHandlerImpl#processingInstruction: " + target + ", " + data);
/*  288:     */     }
/*  289: 592 */     if (this.m_contentHandler != null) {
/*  290: 594 */       this.m_contentHandler.processingInstruction(target, data);
/*  291:     */     }
/*  292:     */   }
/*  293:     */   
/*  294:     */   public void skippedEntity(String name)
/*  295:     */     throws SAXException
/*  296:     */   {
/*  297: 609 */     if (DEBUG) {
/*  298: 610 */       System.out.println("TransformerHandlerImpl#skippedEntity: " + name);
/*  299:     */     }
/*  300: 612 */     if (this.m_contentHandler != null) {
/*  301: 614 */       this.m_contentHandler.skippedEntity(name);
/*  302:     */     }
/*  303:     */   }
/*  304:     */   
/*  305:     */   public void warning(SAXParseException e)
/*  306:     */     throws SAXException
/*  307:     */   {
/*  308: 636 */     ErrorListener errorListener = this.m_transformer.getErrorListener();
/*  309: 637 */     if ((errorListener instanceof ErrorHandler)) {
/*  310: 639 */       ((ErrorHandler)errorListener).warning(e);
/*  311:     */     } else {
/*  312:     */       try
/*  313:     */       {
/*  314: 645 */         errorListener.warning(new TransformerException(e));
/*  315:     */       }
/*  316:     */       catch (TransformerException te)
/*  317:     */       {
/*  318: 649 */         throw e;
/*  319:     */       }
/*  320:     */     }
/*  321:     */   }
/*  322:     */   
/*  323:     */   public void error(SAXParseException e)
/*  324:     */     throws SAXException
/*  325:     */   {
/*  326: 671 */     ErrorListener errorListener = this.m_transformer.getErrorListener();
/*  327: 672 */     if ((errorListener instanceof ErrorHandler))
/*  328:     */     {
/*  329: 674 */       ((ErrorHandler)errorListener).error(e);
/*  330: 675 */       if (null != this.m_errorHandler) {
/*  331: 676 */         this.m_errorHandler.error(e);
/*  332:     */       }
/*  333:     */     }
/*  334:     */     else
/*  335:     */     {
/*  336:     */       try
/*  337:     */       {
/*  338: 682 */         errorListener.error(new TransformerException(e));
/*  339: 683 */         if (null != this.m_errorHandler) {
/*  340: 684 */           this.m_errorHandler.error(e);
/*  341:     */         }
/*  342:     */       }
/*  343:     */       catch (TransformerException te)
/*  344:     */       {
/*  345: 688 */         throw e;
/*  346:     */       }
/*  347:     */     }
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void fatalError(SAXParseException e)
/*  351:     */     throws SAXException
/*  352:     */   {
/*  353: 703 */     if (null != this.m_errorHandler) {
/*  354:     */       try
/*  355:     */       {
/*  356: 707 */         this.m_errorHandler.fatalError(e);
/*  357:     */       }
/*  358:     */       catch (SAXParseException se) {}
/*  359:     */     }
/*  360: 720 */     ErrorListener errorListener = this.m_transformer.getErrorListener();
/*  361: 722 */     if ((errorListener instanceof ErrorHandler))
/*  362:     */     {
/*  363: 724 */       ((ErrorHandler)errorListener).fatalError(e);
/*  364: 725 */       if (null != this.m_errorHandler) {
/*  365: 726 */         this.m_errorHandler.fatalError(e);
/*  366:     */       }
/*  367:     */     }
/*  368:     */     else
/*  369:     */     {
/*  370:     */       try
/*  371:     */       {
/*  372: 732 */         errorListener.fatalError(new TransformerException(e));
/*  373: 733 */         if (null != this.m_errorHandler) {
/*  374: 734 */           this.m_errorHandler.fatalError(e);
/*  375:     */         }
/*  376:     */       }
/*  377:     */       catch (TransformerException te)
/*  378:     */       {
/*  379: 738 */         throw e;
/*  380:     */       }
/*  381:     */     }
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void startDTD(String name, String publicId, String systemId)
/*  385:     */     throws SAXException
/*  386:     */   {
/*  387: 772 */     if (DEBUG) {
/*  388: 773 */       System.out.println("TransformerHandlerImpl#startDTD: " + name + ", " + publicId + ", " + systemId);
/*  389:     */     }
/*  390: 776 */     if (null != this.m_lexicalHandler) {
/*  391: 778 */       this.m_lexicalHandler.startDTD(name, publicId, systemId);
/*  392:     */     }
/*  393:     */   }
/*  394:     */   
/*  395:     */   public void endDTD()
/*  396:     */     throws SAXException
/*  397:     */   {
/*  398: 791 */     if (DEBUG) {
/*  399: 792 */       System.out.println("TransformerHandlerImpl#endDTD");
/*  400:     */     }
/*  401: 794 */     if (null != this.m_lexicalHandler) {
/*  402: 796 */       this.m_lexicalHandler.endDTD();
/*  403:     */     }
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void startEntity(String name)
/*  407:     */     throws SAXException
/*  408:     */   {
/*  409: 825 */     if (DEBUG) {
/*  410: 826 */       System.out.println("TransformerHandlerImpl#startEntity: " + name);
/*  411:     */     }
/*  412: 828 */     if (null != this.m_lexicalHandler) {
/*  413: 830 */       this.m_lexicalHandler.startEntity(name);
/*  414:     */     }
/*  415:     */   }
/*  416:     */   
/*  417:     */   public void endEntity(String name)
/*  418:     */     throws SAXException
/*  419:     */   {
/*  420: 844 */     if (DEBUG) {
/*  421: 845 */       System.out.println("TransformerHandlerImpl#endEntity: " + name);
/*  422:     */     }
/*  423: 847 */     if (null != this.m_lexicalHandler) {
/*  424: 849 */       this.m_lexicalHandler.endEntity(name);
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void startCDATA()
/*  429:     */     throws SAXException
/*  430:     */   {
/*  431: 866 */     if (DEBUG) {
/*  432: 867 */       System.out.println("TransformerHandlerImpl#startCDATA");
/*  433:     */     }
/*  434: 869 */     if (null != this.m_lexicalHandler) {
/*  435: 871 */       this.m_lexicalHandler.startCDATA();
/*  436:     */     }
/*  437:     */   }
/*  438:     */   
/*  439:     */   public void endCDATA()
/*  440:     */     throws SAXException
/*  441:     */   {
/*  442: 884 */     if (DEBUG) {
/*  443: 885 */       System.out.println("TransformerHandlerImpl#endCDATA");
/*  444:     */     }
/*  445: 887 */     if (null != this.m_lexicalHandler) {
/*  446: 889 */       this.m_lexicalHandler.endCDATA();
/*  447:     */     }
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void comment(char[] ch, int start, int length)
/*  451:     */     throws SAXException
/*  452:     */   {
/*  453: 908 */     if (DEBUG) {
/*  454: 909 */       System.out.println("TransformerHandlerImpl#comment: " + start + ", " + length);
/*  455:     */     }
/*  456: 912 */     if (null != this.m_lexicalHandler) {
/*  457: 914 */       this.m_lexicalHandler.comment(ch, start, length);
/*  458:     */     }
/*  459:     */   }
/*  460:     */   
/*  461:     */   public void elementDecl(String name, String model)
/*  462:     */     throws SAXException
/*  463:     */   {
/*  464: 938 */     if (DEBUG) {
/*  465: 939 */       System.out.println("TransformerHandlerImpl#elementDecl: " + name + ", " + model);
/*  466:     */     }
/*  467: 942 */     if (null != this.m_declHandler) {
/*  468: 944 */       this.m_declHandler.elementDecl(name, model);
/*  469:     */     }
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value)
/*  473:     */     throws SAXException
/*  474:     */   {
/*  475: 972 */     if (DEBUG) {
/*  476: 973 */       System.out.println("TransformerHandlerImpl#attributeDecl: " + eName + ", " + aName + ", etc...");
/*  477:     */     }
/*  478: 976 */     if (null != this.m_declHandler) {
/*  479: 978 */       this.m_declHandler.attributeDecl(eName, aName, type, valueDefault, value);
/*  480:     */     }
/*  481:     */   }
/*  482:     */   
/*  483:     */   public void internalEntityDecl(String name, String value)
/*  484:     */     throws SAXException
/*  485:     */   {
/*  486: 999 */     if (DEBUG) {
/*  487:1000 */       System.out.println("TransformerHandlerImpl#internalEntityDecl: " + name + ", " + value);
/*  488:     */     }
/*  489:1003 */     if (null != this.m_declHandler) {
/*  490:1005 */       this.m_declHandler.internalEntityDecl(name, value);
/*  491:     */     }
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void externalEntityDecl(String name, String publicId, String systemId)
/*  495:     */     throws SAXException
/*  496:     */   {
/*  497:1028 */     if (DEBUG) {
/*  498:1029 */       System.out.println("TransformerHandlerImpl#externalEntityDecl: " + name + ", " + publicId + ", " + systemId);
/*  499:     */     }
/*  500:1032 */     if (null != this.m_declHandler) {
/*  501:1034 */       this.m_declHandler.externalEntityDecl(name, publicId, systemId);
/*  502:     */     }
/*  503:     */   }
/*  504:     */   
/*  505:1043 */   private static boolean DEBUG = false;
/*  506:     */   private TransformerImpl m_transformer;
/*  507:     */   private String m_baseSystemID;
/*  508:1055 */   private Result m_result = null;
/*  509:1058 */   private Locator m_locator = null;
/*  510:1061 */   private EntityResolver m_entityResolver = null;
/*  511:1064 */   private DTDHandler m_dtdHandler = null;
/*  512:1067 */   private ContentHandler m_contentHandler = null;
/*  513:1070 */   private ErrorHandler m_errorHandler = null;
/*  514:1073 */   private LexicalHandler m_lexicalHandler = null;
/*  515:1076 */   private DeclHandler m_declHandler = null;
/*  516:     */   DTM m_dtm;
/*  517:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.TransformerHandlerImpl
 * JD-Core Version:    0.7.0.1
 */