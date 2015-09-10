/*    1:     */ package org.apache.xml.serializer;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.OutputStream;
/*    5:     */ import java.io.Writer;
/*    6:     */ import java.util.Properties;
/*    7:     */ import java.util.Vector;
/*    8:     */ import javax.xml.transform.SourceLocator;
/*    9:     */ import javax.xml.transform.Transformer;
/*   10:     */ import org.w3c.dom.Node;
/*   11:     */ import org.xml.sax.Attributes;
/*   12:     */ import org.xml.sax.ContentHandler;
/*   13:     */ import org.xml.sax.Locator;
/*   14:     */ import org.xml.sax.SAXException;
/*   15:     */ import org.xml.sax.ext.DeclHandler;
/*   16:     */ import org.xml.sax.ext.LexicalHandler;
/*   17:     */ 
/*   18:     */ public final class ToUnknownStream
/*   19:     */   extends SerializerBase
/*   20:     */ {
/*   21:     */   private SerializationHandler m_handler;
/*   22:     */   private static final String EMPTYSTRING = "";
/*   23:  70 */   private boolean m_wrapped_handler_not_initialized = false;
/*   24:     */   private String m_firstElementPrefix;
/*   25:     */   private String m_firstElementName;
/*   26:     */   private String m_firstElementURI;
/*   27:  90 */   private String m_firstElementLocalName = null;
/*   28:  95 */   private boolean m_firstTagNotEmitted = true;
/*   29: 101 */   private Vector m_namespaceURI = null;
/*   30: 106 */   private Vector m_namespacePrefix = null;
/*   31: 112 */   private boolean m_needToCallStartDocument = false;
/*   32: 117 */   private boolean m_setVersion_called = false;
/*   33: 122 */   private boolean m_setDoctypeSystem_called = false;
/*   34: 127 */   private boolean m_setDoctypePublic_called = false;
/*   35: 132 */   private boolean m_setMediaType_called = false;
/*   36:     */   
/*   37:     */   public ToUnknownStream()
/*   38:     */   {
/*   39: 141 */     this.m_handler = new ToXMLStream();
/*   40:     */   }
/*   41:     */   
/*   42:     */   public ContentHandler asContentHandler()
/*   43:     */     throws IOException
/*   44:     */   {
/*   45: 155 */     return this;
/*   46:     */   }
/*   47:     */   
/*   48:     */   public void close()
/*   49:     */   {
/*   50: 163 */     this.m_handler.close();
/*   51:     */   }
/*   52:     */   
/*   53:     */   public Properties getOutputFormat()
/*   54:     */   {
/*   55: 172 */     return this.m_handler.getOutputFormat();
/*   56:     */   }
/*   57:     */   
/*   58:     */   public OutputStream getOutputStream()
/*   59:     */   {
/*   60: 181 */     return this.m_handler.getOutputStream();
/*   61:     */   }
/*   62:     */   
/*   63:     */   public Writer getWriter()
/*   64:     */   {
/*   65: 190 */     return this.m_handler.getWriter();
/*   66:     */   }
/*   67:     */   
/*   68:     */   public boolean reset()
/*   69:     */   {
/*   70: 200 */     return this.m_handler.reset();
/*   71:     */   }
/*   72:     */   
/*   73:     */   public void serialize(Node node)
/*   74:     */     throws IOException
/*   75:     */   {
/*   76: 211 */     if (this.m_firstTagNotEmitted) {
/*   77: 213 */       flush();
/*   78:     */     }
/*   79: 215 */     this.m_handler.serialize(node);
/*   80:     */   }
/*   81:     */   
/*   82:     */   public boolean setEscaping(boolean escape)
/*   83:     */     throws SAXException
/*   84:     */   {
/*   85: 223 */     return this.m_handler.setEscaping(escape);
/*   86:     */   }
/*   87:     */   
/*   88:     */   public void setOutputFormat(Properties format)
/*   89:     */   {
/*   90: 233 */     this.m_handler.setOutputFormat(format);
/*   91:     */   }
/*   92:     */   
/*   93:     */   public void setOutputStream(OutputStream output)
/*   94:     */   {
/*   95: 243 */     this.m_handler.setOutputStream(output);
/*   96:     */   }
/*   97:     */   
/*   98:     */   public void setWriter(Writer writer)
/*   99:     */   {
/*  100: 253 */     this.m_handler.setWriter(writer);
/*  101:     */   }
/*  102:     */   
/*  103:     */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute)
/*  104:     */     throws SAXException
/*  105:     */   {
/*  106: 275 */     if (this.m_firstTagNotEmitted) {
/*  107: 277 */       flush();
/*  108:     */     }
/*  109: 279 */     this.m_handler.addAttribute(uri, localName, rawName, type, value, XSLAttribute);
/*  110:     */   }
/*  111:     */   
/*  112:     */   public void addAttribute(String rawName, String value)
/*  113:     */   {
/*  114: 289 */     if (this.m_firstTagNotEmitted) {
/*  115: 291 */       flush();
/*  116:     */     }
/*  117: 293 */     this.m_handler.addAttribute(rawName, value);
/*  118:     */   }
/*  119:     */   
/*  120:     */   public void addUniqueAttribute(String rawName, String value, int flags)
/*  121:     */     throws SAXException
/*  122:     */   {
/*  123: 303 */     if (this.m_firstTagNotEmitted) {
/*  124: 305 */       flush();
/*  125:     */     }
/*  126: 307 */     this.m_handler.addUniqueAttribute(rawName, value, flags);
/*  127:     */   }
/*  128:     */   
/*  129:     */   public void characters(String chars)
/*  130:     */     throws SAXException
/*  131:     */   {
/*  132: 319 */     int length = chars.length();
/*  133: 320 */     if (length > this.m_charsBuff.length) {
/*  134: 322 */       this.m_charsBuff = new char[length * 2 + 1];
/*  135:     */     }
/*  136: 324 */     chars.getChars(0, length, this.m_charsBuff, 0);
/*  137: 325 */     characters(this.m_charsBuff, 0, length);
/*  138:     */   }
/*  139:     */   
/*  140:     */   public void endElement(String elementName)
/*  141:     */     throws SAXException
/*  142:     */   {
/*  143: 334 */     if (this.m_firstTagNotEmitted) {
/*  144: 336 */       flush();
/*  145:     */     }
/*  146: 338 */     this.m_handler.endElement(elementName);
/*  147:     */   }
/*  148:     */   
/*  149:     */   public void startPrefixMapping(String prefix, String uri)
/*  150:     */     throws SAXException
/*  151:     */   {
/*  152: 349 */     startPrefixMapping(prefix, uri, true);
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void namespaceAfterStartElement(String prefix, String uri)
/*  156:     */     throws SAXException
/*  157:     */   {
/*  158: 367 */     if ((this.m_firstTagNotEmitted) && (this.m_firstElementURI == null) && (this.m_firstElementName != null))
/*  159:     */     {
/*  160: 369 */       String prefix1 = SerializerBase.getPrefixPart(this.m_firstElementName);
/*  161: 370 */       if ((prefix1 == null) && ("".equals(prefix))) {
/*  162: 376 */         this.m_firstElementURI = uri;
/*  163:     */       }
/*  164:     */     }
/*  165: 379 */     startPrefixMapping(prefix, uri, false);
/*  166:     */   }
/*  167:     */   
/*  168:     */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush)
/*  169:     */     throws SAXException
/*  170:     */   {
/*  171: 385 */     boolean pushed = false;
/*  172: 386 */     if (this.m_firstTagNotEmitted)
/*  173:     */     {
/*  174: 388 */       if ((this.m_firstElementName != null) && (shouldFlush))
/*  175:     */       {
/*  176: 394 */         flush();
/*  177: 395 */         pushed = this.m_handler.startPrefixMapping(prefix, uri, shouldFlush);
/*  178:     */       }
/*  179:     */       else
/*  180:     */       {
/*  181: 399 */         if (this.m_namespacePrefix == null)
/*  182:     */         {
/*  183: 401 */           this.m_namespacePrefix = new Vector();
/*  184: 402 */           this.m_namespaceURI = new Vector();
/*  185:     */         }
/*  186: 404 */         this.m_namespacePrefix.addElement(prefix);
/*  187: 405 */         this.m_namespaceURI.addElement(uri);
/*  188: 407 */         if (this.m_firstElementURI == null) {
/*  189: 409 */           if (prefix.equals(this.m_firstElementPrefix)) {
/*  190: 410 */             this.m_firstElementURI = uri;
/*  191:     */           }
/*  192:     */         }
/*  193:     */       }
/*  194:     */     }
/*  195:     */     else {
/*  196: 417 */       pushed = this.m_handler.startPrefixMapping(prefix, uri, shouldFlush);
/*  197:     */     }
/*  198: 419 */     return pushed;
/*  199:     */   }
/*  200:     */   
/*  201:     */   public void setVersion(String version)
/*  202:     */   {
/*  203: 429 */     this.m_handler.setVersion(version);
/*  204:     */     
/*  205:     */ 
/*  206:     */ 
/*  207: 433 */     this.m_setVersion_called = true;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public void startDocument()
/*  211:     */     throws SAXException
/*  212:     */   {
/*  213: 441 */     this.m_needToCallStartDocument = true;
/*  214:     */   }
/*  215:     */   
/*  216:     */   public void startElement(String qName)
/*  217:     */     throws SAXException
/*  218:     */   {
/*  219: 448 */     startElement(null, null, qName, null);
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void startElement(String namespaceURI, String localName, String qName)
/*  223:     */     throws SAXException
/*  224:     */   {
/*  225: 453 */     startElement(namespaceURI, localName, qName, null);
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void startElement(String namespaceURI, String localName, String elementName, Attributes atts)
/*  229:     */     throws SAXException
/*  230:     */   {
/*  231: 463 */     if (this.m_firstTagNotEmitted)
/*  232:     */     {
/*  233: 466 */       if (this.m_firstElementName != null)
/*  234:     */       {
/*  235: 472 */         flush();
/*  236: 473 */         this.m_handler.startElement(namespaceURI, localName, elementName, atts);
/*  237:     */       }
/*  238:     */       else
/*  239:     */       {
/*  240: 482 */         this.m_wrapped_handler_not_initialized = true;
/*  241: 483 */         this.m_firstElementName = elementName;
/*  242:     */         
/*  243:     */ 
/*  244: 486 */         this.m_firstElementPrefix = getPrefixPartUnknown(elementName);
/*  245:     */         
/*  246:     */ 
/*  247: 489 */         this.m_firstElementURI = namespaceURI;
/*  248:     */         
/*  249:     */ 
/*  250: 492 */         this.m_firstElementLocalName = localName;
/*  251: 494 */         if (this.m_tracer != null) {
/*  252: 495 */           firePseudoElement(elementName);
/*  253:     */         }
/*  254: 504 */         if (atts != null) {
/*  255: 505 */           super.addAttributes(atts);
/*  256:     */         }
/*  257: 510 */         if (atts != null) {
/*  258: 511 */           flush();
/*  259:     */         }
/*  260:     */       }
/*  261:     */     }
/*  262:     */     else {
/*  263: 519 */       this.m_handler.startElement(namespaceURI, localName, elementName, atts);
/*  264:     */     }
/*  265:     */   }
/*  266:     */   
/*  267:     */   public void comment(String comment)
/*  268:     */     throws SAXException
/*  269:     */   {
/*  270: 529 */     if ((this.m_firstTagNotEmitted) && (this.m_firstElementName != null))
/*  271:     */     {
/*  272: 531 */       emitFirstTag();
/*  273:     */     }
/*  274: 533 */     else if (this.m_needToCallStartDocument)
/*  275:     */     {
/*  276: 535 */       this.m_handler.startDocument();
/*  277: 536 */       this.m_needToCallStartDocument = false;
/*  278:     */     }
/*  279: 539 */     this.m_handler.comment(comment);
/*  280:     */   }
/*  281:     */   
/*  282:     */   public String getDoctypePublic()
/*  283:     */   {
/*  284: 549 */     return this.m_handler.getDoctypePublic();
/*  285:     */   }
/*  286:     */   
/*  287:     */   public String getDoctypeSystem()
/*  288:     */   {
/*  289: 558 */     return this.m_handler.getDoctypeSystem();
/*  290:     */   }
/*  291:     */   
/*  292:     */   public String getEncoding()
/*  293:     */   {
/*  294: 567 */     return this.m_handler.getEncoding();
/*  295:     */   }
/*  296:     */   
/*  297:     */   public boolean getIndent()
/*  298:     */   {
/*  299: 576 */     return this.m_handler.getIndent();
/*  300:     */   }
/*  301:     */   
/*  302:     */   public int getIndentAmount()
/*  303:     */   {
/*  304: 585 */     return this.m_handler.getIndentAmount();
/*  305:     */   }
/*  306:     */   
/*  307:     */   public String getMediaType()
/*  308:     */   {
/*  309: 594 */     return this.m_handler.getMediaType();
/*  310:     */   }
/*  311:     */   
/*  312:     */   public boolean getOmitXMLDeclaration()
/*  313:     */   {
/*  314: 603 */     return this.m_handler.getOmitXMLDeclaration();
/*  315:     */   }
/*  316:     */   
/*  317:     */   public String getStandalone()
/*  318:     */   {
/*  319: 612 */     return this.m_handler.getStandalone();
/*  320:     */   }
/*  321:     */   
/*  322:     */   public String getVersion()
/*  323:     */   {
/*  324: 621 */     return this.m_handler.getVersion();
/*  325:     */   }
/*  326:     */   
/*  327:     */   public void setDoctype(String system, String pub)
/*  328:     */   {
/*  329: 629 */     this.m_handler.setDoctypePublic(pub);
/*  330: 630 */     this.m_handler.setDoctypeSystem(system);
/*  331:     */   }
/*  332:     */   
/*  333:     */   public void setDoctypePublic(String doctype)
/*  334:     */   {
/*  335: 641 */     this.m_handler.setDoctypePublic(doctype);
/*  336: 642 */     this.m_setDoctypePublic_called = true;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public void setDoctypeSystem(String doctype)
/*  340:     */   {
/*  341: 653 */     this.m_handler.setDoctypeSystem(doctype);
/*  342: 654 */     this.m_setDoctypeSystem_called = true;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public void setEncoding(String encoding)
/*  346:     */   {
/*  347: 663 */     this.m_handler.setEncoding(encoding);
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void setIndent(boolean indent)
/*  351:     */   {
/*  352: 672 */     this.m_handler.setIndent(indent);
/*  353:     */   }
/*  354:     */   
/*  355:     */   public void setIndentAmount(int value)
/*  356:     */   {
/*  357: 680 */     this.m_handler.setIndentAmount(value);
/*  358:     */   }
/*  359:     */   
/*  360:     */   public void setMediaType(String mediaType)
/*  361:     */   {
/*  362: 688 */     this.m_handler.setMediaType(mediaType);
/*  363: 689 */     this.m_setMediaType_called = true;
/*  364:     */   }
/*  365:     */   
/*  366:     */   public void setOmitXMLDeclaration(boolean b)
/*  367:     */   {
/*  368: 698 */     this.m_handler.setOmitXMLDeclaration(b);
/*  369:     */   }
/*  370:     */   
/*  371:     */   public void setStandalone(String standalone)
/*  372:     */   {
/*  373: 707 */     this.m_handler.setStandalone(standalone);
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void attributeDecl(String arg0, String arg1, String arg2, String arg3, String arg4)
/*  377:     */     throws SAXException
/*  378:     */   {
/*  379: 726 */     this.m_handler.attributeDecl(arg0, arg1, arg2, arg3, arg4);
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void elementDecl(String arg0, String arg1)
/*  383:     */     throws SAXException
/*  384:     */   {
/*  385: 735 */     if (this.m_firstTagNotEmitted) {
/*  386: 737 */       emitFirstTag();
/*  387:     */     }
/*  388: 739 */     this.m_handler.elementDecl(arg0, arg1);
/*  389:     */   }
/*  390:     */   
/*  391:     */   public void externalEntityDecl(String name, String publicId, String systemId)
/*  392:     */     throws SAXException
/*  393:     */   {
/*  394: 752 */     if (this.m_firstTagNotEmitted) {
/*  395: 754 */       flush();
/*  396:     */     }
/*  397: 756 */     this.m_handler.externalEntityDecl(name, publicId, systemId);
/*  398:     */   }
/*  399:     */   
/*  400:     */   public void internalEntityDecl(String arg0, String arg1)
/*  401:     */     throws SAXException
/*  402:     */   {
/*  403: 766 */     if (this.m_firstTagNotEmitted) {
/*  404: 768 */       flush();
/*  405:     */     }
/*  406: 770 */     this.m_handler.internalEntityDecl(arg0, arg1);
/*  407:     */   }
/*  408:     */   
/*  409:     */   public void characters(char[] characters, int offset, int length)
/*  410:     */     throws SAXException
/*  411:     */   {
/*  412: 780 */     if (this.m_firstTagNotEmitted) {
/*  413: 782 */       flush();
/*  414:     */     }
/*  415: 785 */     this.m_handler.characters(characters, offset, length);
/*  416:     */   }
/*  417:     */   
/*  418:     */   public void endDocument()
/*  419:     */     throws SAXException
/*  420:     */   {
/*  421: 795 */     if (this.m_firstTagNotEmitted) {
/*  422: 797 */       flush();
/*  423:     */     }
/*  424: 800 */     this.m_handler.endDocument();
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void endElement(String namespaceURI, String localName, String qName)
/*  428:     */     throws SAXException
/*  429:     */   {
/*  430: 812 */     if (this.m_firstTagNotEmitted)
/*  431:     */     {
/*  432: 814 */       flush();
/*  433: 815 */       if ((namespaceURI == null) && (this.m_firstElementURI != null)) {
/*  434: 816 */         namespaceURI = this.m_firstElementURI;
/*  435:     */       }
/*  436: 819 */       if ((localName == null) && (this.m_firstElementLocalName != null)) {
/*  437: 820 */         localName = this.m_firstElementLocalName;
/*  438:     */       }
/*  439:     */     }
/*  440: 823 */     this.m_handler.endElement(namespaceURI, localName, qName);
/*  441:     */   }
/*  442:     */   
/*  443:     */   public void endPrefixMapping(String prefix)
/*  444:     */     throws SAXException
/*  445:     */   {
/*  446: 832 */     this.m_handler.endPrefixMapping(prefix);
/*  447:     */   }
/*  448:     */   
/*  449:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/*  450:     */     throws SAXException
/*  451:     */   {
/*  452: 842 */     if (this.m_firstTagNotEmitted) {
/*  453: 844 */       flush();
/*  454:     */     }
/*  455: 846 */     this.m_handler.ignorableWhitespace(ch, start, length);
/*  456:     */   }
/*  457:     */   
/*  458:     */   public void processingInstruction(String target, String data)
/*  459:     */     throws SAXException
/*  460:     */   {
/*  461: 856 */     if (this.m_firstTagNotEmitted) {
/*  462: 858 */       flush();
/*  463:     */     }
/*  464: 861 */     this.m_handler.processingInstruction(target, data);
/*  465:     */   }
/*  466:     */   
/*  467:     */   public void setDocumentLocator(Locator locator)
/*  468:     */   {
/*  469: 870 */     this.m_handler.setDocumentLocator(locator);
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void skippedEntity(String name)
/*  473:     */     throws SAXException
/*  474:     */   {
/*  475: 879 */     this.m_handler.skippedEntity(name);
/*  476:     */   }
/*  477:     */   
/*  478:     */   public void comment(char[] ch, int start, int length)
/*  479:     */     throws SAXException
/*  480:     */   {
/*  481: 890 */     if (this.m_firstTagNotEmitted) {
/*  482: 892 */       flush();
/*  483:     */     }
/*  484: 895 */     this.m_handler.comment(ch, start, length);
/*  485:     */   }
/*  486:     */   
/*  487:     */   public void endCDATA()
/*  488:     */     throws SAXException
/*  489:     */   {
/*  490: 905 */     this.m_handler.endCDATA();
/*  491:     */   }
/*  492:     */   
/*  493:     */   public void endDTD()
/*  494:     */     throws SAXException
/*  495:     */   {
/*  496: 915 */     this.m_handler.endDTD();
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void endEntity(String name)
/*  500:     */     throws SAXException
/*  501:     */   {
/*  502: 924 */     if (this.m_firstTagNotEmitted) {
/*  503: 926 */       emitFirstTag();
/*  504:     */     }
/*  505: 928 */     this.m_handler.endEntity(name);
/*  506:     */   }
/*  507:     */   
/*  508:     */   public void startCDATA()
/*  509:     */     throws SAXException
/*  510:     */   {
/*  511: 937 */     this.m_handler.startCDATA();
/*  512:     */   }
/*  513:     */   
/*  514:     */   public void startDTD(String name, String publicId, String systemId)
/*  515:     */     throws SAXException
/*  516:     */   {
/*  517: 947 */     this.m_handler.startDTD(name, publicId, systemId);
/*  518:     */   }
/*  519:     */   
/*  520:     */   public void startEntity(String name)
/*  521:     */     throws SAXException
/*  522:     */   {
/*  523: 956 */     this.m_handler.startEntity(name);
/*  524:     */   }
/*  525:     */   
/*  526:     */   private void initStreamOutput()
/*  527:     */     throws SAXException
/*  528:     */   {
/*  529: 970 */     boolean firstElementIsHTML = isFirstElemHTML();
/*  530: 972 */     if (firstElementIsHTML)
/*  531:     */     {
/*  532: 977 */       SerializationHandler oldHandler = this.m_handler;
/*  533:     */       
/*  534:     */ 
/*  535:     */ 
/*  536:     */ 
/*  537:     */ 
/*  538:     */ 
/*  539: 984 */       Properties htmlProperties = OutputPropertiesFactory.getDefaultMethodProperties("html");
/*  540:     */       
/*  541: 986 */       Serializer serializer = SerializerFactory.getSerializer(htmlProperties);
/*  542:     */       
/*  543:     */ 
/*  544:     */ 
/*  545:     */ 
/*  546:     */ 
/*  547:     */ 
/*  548: 993 */       this.m_handler = ((SerializationHandler)serializer);
/*  549:     */       
/*  550:     */ 
/*  551: 996 */       Writer writer = oldHandler.getWriter();
/*  552: 998 */       if (null != writer)
/*  553:     */       {
/*  554: 999 */         this.m_handler.setWriter(writer);
/*  555:     */       }
/*  556:     */       else
/*  557:     */       {
/*  558:1002 */         OutputStream os = oldHandler.getOutputStream();
/*  559:1004 */         if (null != os) {
/*  560:1005 */           this.m_handler.setOutputStream(os);
/*  561:     */         }
/*  562:     */       }
/*  563:1012 */       this.m_handler.setVersion(oldHandler.getVersion());
/*  564:     */       
/*  565:     */ 
/*  566:     */ 
/*  567:1016 */       this.m_handler.setDoctypeSystem(oldHandler.getDoctypeSystem());
/*  568:     */       
/*  569:     */ 
/*  570:     */ 
/*  571:1020 */       this.m_handler.setDoctypePublic(oldHandler.getDoctypePublic());
/*  572:     */       
/*  573:     */ 
/*  574:     */ 
/*  575:1024 */       this.m_handler.setMediaType(oldHandler.getMediaType());
/*  576:     */       
/*  577:     */ 
/*  578:1027 */       this.m_handler.setTransformer(oldHandler.getTransformer());
/*  579:     */     }
/*  580:1034 */     if (this.m_needToCallStartDocument)
/*  581:     */     {
/*  582:1036 */       this.m_handler.startDocument();
/*  583:1037 */       this.m_needToCallStartDocument = false;
/*  584:     */     }
/*  585:1041 */     this.m_wrapped_handler_not_initialized = false;
/*  586:     */   }
/*  587:     */   
/*  588:     */   private void emitFirstTag()
/*  589:     */     throws SAXException
/*  590:     */   {
/*  591:1046 */     if (this.m_firstElementName != null)
/*  592:     */     {
/*  593:1048 */       if (this.m_wrapped_handler_not_initialized)
/*  594:     */       {
/*  595:1050 */         initStreamOutput();
/*  596:1051 */         this.m_wrapped_handler_not_initialized = false;
/*  597:     */       }
/*  598:1054 */       this.m_handler.startElement(this.m_firstElementURI, null, this.m_firstElementName, this.m_attributes);
/*  599:     */       
/*  600:1056 */       this.m_attributes = null;
/*  601:1059 */       if (this.m_namespacePrefix != null)
/*  602:     */       {
/*  603:1061 */         int n = this.m_namespacePrefix.size();
/*  604:1062 */         for (int i = 0; i < n; i++)
/*  605:     */         {
/*  606:1064 */           String prefix = (String)this.m_namespacePrefix.elementAt(i);
/*  607:     */           
/*  608:1066 */           String uri = (String)this.m_namespaceURI.elementAt(i);
/*  609:1067 */           this.m_handler.startPrefixMapping(prefix, uri, false);
/*  610:     */         }
/*  611:1069 */         this.m_namespacePrefix = null;
/*  612:1070 */         this.m_namespaceURI = null;
/*  613:     */       }
/*  614:1072 */       this.m_firstTagNotEmitted = false;
/*  615:     */     }
/*  616:     */   }
/*  617:     */   
/*  618:     */   private String getLocalNameUnknown(String value)
/*  619:     */   {
/*  620:1084 */     int idx = value.lastIndexOf(':');
/*  621:1085 */     if (idx >= 0) {
/*  622:1086 */       value = value.substring(idx + 1);
/*  623:     */     }
/*  624:1087 */     idx = value.lastIndexOf('@');
/*  625:1088 */     if (idx >= 0) {
/*  626:1089 */       value = value.substring(idx + 1);
/*  627:     */     }
/*  628:1090 */     return value;
/*  629:     */   }
/*  630:     */   
/*  631:     */   private String getPrefixPartUnknown(String qname)
/*  632:     */   {
/*  633:1101 */     int index = qname.indexOf(':');
/*  634:1102 */     return index > 0 ? qname.substring(0, index) : "";
/*  635:     */   }
/*  636:     */   
/*  637:     */   private boolean isFirstElemHTML()
/*  638:     */   {
/*  639:1117 */     boolean isHTML = getLocalNameUnknown(this.m_firstElementName).equalsIgnoreCase("html");
/*  640:1121 */     if ((isHTML) && (this.m_firstElementURI != null) && (!"".equals(this.m_firstElementURI))) {
/*  641:1126 */       isHTML = false;
/*  642:     */     }
/*  643:1129 */     if ((isHTML) && (this.m_namespacePrefix != null))
/*  644:     */     {
/*  645:1135 */       int max = this.m_namespacePrefix.size();
/*  646:1136 */       for (int i = 0; i < max; i++)
/*  647:     */       {
/*  648:1138 */         String prefix = (String)this.m_namespacePrefix.elementAt(i);
/*  649:1139 */         String uri = (String)this.m_namespaceURI.elementAt(i);
/*  650:1141 */         if ((this.m_firstElementPrefix != null) && (this.m_firstElementPrefix.equals(prefix)) && (!"".equals(uri)))
/*  651:     */         {
/*  652:1146 */           isHTML = false;
/*  653:1147 */           break;
/*  654:     */         }
/*  655:     */       }
/*  656:     */     }
/*  657:1152 */     return isHTML;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public DOMSerializer asDOMSerializer()
/*  661:     */     throws IOException
/*  662:     */   {
/*  663:1159 */     return this.m_handler.asDOMSerializer();
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void setCdataSectionElements(Vector URI_and_localNames)
/*  667:     */   {
/*  668:1169 */     this.m_handler.setCdataSectionElements(URI_and_localNames);
/*  669:     */   }
/*  670:     */   
/*  671:     */   public void addAttributes(Attributes atts)
/*  672:     */     throws SAXException
/*  673:     */   {
/*  674:1176 */     this.m_handler.addAttributes(atts);
/*  675:     */   }
/*  676:     */   
/*  677:     */   public NamespaceMappings getNamespaceMappings()
/*  678:     */   {
/*  679:1186 */     NamespaceMappings mappings = null;
/*  680:1187 */     if (this.m_handler != null) {
/*  681:1189 */       mappings = this.m_handler.getNamespaceMappings();
/*  682:     */     }
/*  683:1191 */     return mappings;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void flushPending()
/*  687:     */     throws SAXException
/*  688:     */   {
/*  689:1199 */     flush();
/*  690:     */     
/*  691:1201 */     this.m_handler.flushPending();
/*  692:     */   }
/*  693:     */   
/*  694:     */   private void flush()
/*  695:     */   {
/*  696:     */     try
/*  697:     */     {
/*  698:1208 */       if (this.m_firstTagNotEmitted) {
/*  699:1210 */         emitFirstTag();
/*  700:     */       }
/*  701:1212 */       if (this.m_needToCallStartDocument)
/*  702:     */       {
/*  703:1214 */         this.m_handler.startDocument();
/*  704:1215 */         this.m_needToCallStartDocument = false;
/*  705:     */       }
/*  706:     */     }
/*  707:     */     catch (SAXException e)
/*  708:     */     {
/*  709:1220 */       throw new RuntimeException(e.toString());
/*  710:     */     }
/*  711:     */   }
/*  712:     */   
/*  713:     */   public String getPrefix(String namespaceURI)
/*  714:     */   {
/*  715:1231 */     return this.m_handler.getPrefix(namespaceURI);
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void entityReference(String entityName)
/*  719:     */     throws SAXException
/*  720:     */   {
/*  721:1238 */     this.m_handler.entityReference(entityName);
/*  722:     */   }
/*  723:     */   
/*  724:     */   public String getNamespaceURI(String qname, boolean isElement)
/*  725:     */   {
/*  726:1246 */     return this.m_handler.getNamespaceURI(qname, isElement);
/*  727:     */   }
/*  728:     */   
/*  729:     */   public String getNamespaceURIFromPrefix(String prefix)
/*  730:     */   {
/*  731:1251 */     return this.m_handler.getNamespaceURIFromPrefix(prefix);
/*  732:     */   }
/*  733:     */   
/*  734:     */   public void setTransformer(Transformer t)
/*  735:     */   {
/*  736:1256 */     this.m_handler.setTransformer(t);
/*  737:1257 */     if (((t instanceof SerializerTrace)) && (((SerializerTrace)t).hasTraceListeners())) {
/*  738:1259 */       this.m_tracer = ((SerializerTrace)t);
/*  739:     */     } else {
/*  740:1261 */       this.m_tracer = null;
/*  741:     */     }
/*  742:     */   }
/*  743:     */   
/*  744:     */   public Transformer getTransformer()
/*  745:     */   {
/*  746:1266 */     return this.m_handler.getTransformer();
/*  747:     */   }
/*  748:     */   
/*  749:     */   public void setContentHandler(ContentHandler ch)
/*  750:     */   {
/*  751:1274 */     this.m_handler.setContentHandler(ch);
/*  752:     */   }
/*  753:     */   
/*  754:     */   public void setSourceLocator(SourceLocator locator)
/*  755:     */   {
/*  756:1285 */     this.m_handler.setSourceLocator(locator);
/*  757:     */   }
/*  758:     */   
/*  759:     */   protected void firePseudoElement(String elementName)
/*  760:     */   {
/*  761:1291 */     if (this.m_tracer != null)
/*  762:     */     {
/*  763:1292 */       StringBuffer sb = new StringBuffer();
/*  764:     */       
/*  765:1294 */       sb.append('<');
/*  766:1295 */       sb.append(elementName);
/*  767:     */       
/*  768:     */ 
/*  769:     */ 
/*  770:     */ 
/*  771:1300 */       char[] ch = sb.toString().toCharArray();
/*  772:1301 */       this.m_tracer.fireGenerateEvent(11, ch, 0, ch.length);
/*  773:     */     }
/*  774:     */   }
/*  775:     */   
/*  776:     */   public Object asDOM3Serializer()
/*  777:     */     throws IOException
/*  778:     */   {
/*  779:1314 */     return this.m_handler.asDOM3Serializer();
/*  780:     */   }
/*  781:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToUnknownStream
 * JD-Core Version:    0.7.0.1
 */