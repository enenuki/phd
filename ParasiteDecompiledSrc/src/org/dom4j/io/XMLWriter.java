/*    1:     */ package org.dom4j.io;
/*    2:     */ 
/*    3:     */ import java.io.BufferedWriter;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.OutputStream;
/*    6:     */ import java.io.OutputStreamWriter;
/*    7:     */ import java.io.UnsupportedEncodingException;
/*    8:     */ import java.io.Writer;
/*    9:     */ import java.util.HashMap;
/*   10:     */ import java.util.Iterator;
/*   11:     */ import java.util.List;
/*   12:     */ import java.util.Map;
/*   13:     */ import java.util.Map.Entry;
/*   14:     */ import java.util.Set;
/*   15:     */ import java.util.StringTokenizer;
/*   16:     */ import org.dom4j.Attribute;
/*   17:     */ import org.dom4j.CDATA;
/*   18:     */ import org.dom4j.Comment;
/*   19:     */ import org.dom4j.Document;
/*   20:     */ import org.dom4j.DocumentType;
/*   21:     */ import org.dom4j.Element;
/*   22:     */ import org.dom4j.Entity;
/*   23:     */ import org.dom4j.Namespace;
/*   24:     */ import org.dom4j.Node;
/*   25:     */ import org.dom4j.ProcessingInstruction;
/*   26:     */ import org.dom4j.Text;
/*   27:     */ import org.dom4j.tree.NamespaceStack;
/*   28:     */ import org.xml.sax.Attributes;
/*   29:     */ import org.xml.sax.InputSource;
/*   30:     */ import org.xml.sax.Locator;
/*   31:     */ import org.xml.sax.SAXException;
/*   32:     */ import org.xml.sax.SAXNotRecognizedException;
/*   33:     */ import org.xml.sax.SAXNotSupportedException;
/*   34:     */ import org.xml.sax.XMLReader;
/*   35:     */ import org.xml.sax.ext.LexicalHandler;
/*   36:     */ import org.xml.sax.helpers.XMLFilterImpl;
/*   37:     */ 
/*   38:     */ public class XMLWriter
/*   39:     */   extends XMLFilterImpl
/*   40:     */   implements LexicalHandler
/*   41:     */ {
/*   42:     */   private static final String PAD_TEXT = " ";
/*   43:  74 */   protected static final String[] LEXICAL_HANDLER_NAMES = { "http://xml.org/sax/properties/lexical-handler", "http://xml.org/sax/handlers/LexicalHandler" };
/*   44:  78 */   protected static final OutputFormat DEFAULT_FORMAT = new OutputFormat();
/*   45:  81 */   private boolean resolveEntityRefs = true;
/*   46:     */   protected int lastOutputNodeType;
/*   47:  93 */   private boolean lastElementClosed = false;
/*   48:  96 */   protected boolean preserve = false;
/*   49:     */   protected Writer writer;
/*   50: 102 */   private NamespaceStack namespaceStack = new NamespaceStack();
/*   51:     */   private OutputFormat format;
/*   52: 108 */   private boolean escapeText = true;
/*   53: 114 */   private int indentLevel = 0;
/*   54: 117 */   private StringBuffer buffer = new StringBuffer();
/*   55: 122 */   private boolean charsAdded = false;
/*   56:     */   private char lastChar;
/*   57:     */   private boolean autoFlush;
/*   58:     */   private LexicalHandler lexicalHandler;
/*   59:     */   private boolean showCommentsInDTDs;
/*   60:     */   private boolean inDTD;
/*   61:     */   private Map namespacesMap;
/*   62:     */   private int maximumAllowedCharacter;
/*   63:     */   
/*   64:     */   public XMLWriter(Writer writer)
/*   65:     */   {
/*   66: 152 */     this(writer, DEFAULT_FORMAT);
/*   67:     */   }
/*   68:     */   
/*   69:     */   public XMLWriter(Writer writer, OutputFormat format)
/*   70:     */   {
/*   71: 156 */     this.writer = writer;
/*   72: 157 */     this.format = format;
/*   73: 158 */     this.namespaceStack.push(Namespace.NO_NAMESPACE);
/*   74:     */   }
/*   75:     */   
/*   76:     */   public XMLWriter()
/*   77:     */   {
/*   78: 162 */     this.format = DEFAULT_FORMAT;
/*   79: 163 */     this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
/*   80: 164 */     this.autoFlush = true;
/*   81: 165 */     this.namespaceStack.push(Namespace.NO_NAMESPACE);
/*   82:     */   }
/*   83:     */   
/*   84:     */   public XMLWriter(OutputStream out)
/*   85:     */     throws UnsupportedEncodingException
/*   86:     */   {
/*   87: 169 */     this.format = DEFAULT_FORMAT;
/*   88: 170 */     this.writer = createWriter(out, this.format.getEncoding());
/*   89: 171 */     this.autoFlush = true;
/*   90: 172 */     this.namespaceStack.push(Namespace.NO_NAMESPACE);
/*   91:     */   }
/*   92:     */   
/*   93:     */   public XMLWriter(OutputStream out, OutputFormat format)
/*   94:     */     throws UnsupportedEncodingException
/*   95:     */   {
/*   96: 177 */     this.format = format;
/*   97: 178 */     this.writer = createWriter(out, format.getEncoding());
/*   98: 179 */     this.autoFlush = true;
/*   99: 180 */     this.namespaceStack.push(Namespace.NO_NAMESPACE);
/*  100:     */   }
/*  101:     */   
/*  102:     */   public XMLWriter(OutputFormat format)
/*  103:     */     throws UnsupportedEncodingException
/*  104:     */   {
/*  105: 184 */     this.format = format;
/*  106: 185 */     this.writer = createWriter(System.out, format.getEncoding());
/*  107: 186 */     this.autoFlush = true;
/*  108: 187 */     this.namespaceStack.push(Namespace.NO_NAMESPACE);
/*  109:     */   }
/*  110:     */   
/*  111:     */   public void setWriter(Writer writer)
/*  112:     */   {
/*  113: 191 */     this.writer = writer;
/*  114: 192 */     this.autoFlush = false;
/*  115:     */   }
/*  116:     */   
/*  117:     */   public void setOutputStream(OutputStream out)
/*  118:     */     throws UnsupportedEncodingException
/*  119:     */   {
/*  120: 197 */     this.writer = createWriter(out, this.format.getEncoding());
/*  121: 198 */     this.autoFlush = true;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public boolean isEscapeText()
/*  125:     */   {
/*  126: 209 */     return this.escapeText;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public void setEscapeText(boolean escapeText)
/*  130:     */   {
/*  131: 221 */     this.escapeText = escapeText;
/*  132:     */   }
/*  133:     */   
/*  134:     */   public void setIndentLevel(int indentLevel)
/*  135:     */   {
/*  136: 233 */     this.indentLevel = indentLevel;
/*  137:     */   }
/*  138:     */   
/*  139:     */   public int getMaximumAllowedCharacter()
/*  140:     */   {
/*  141: 244 */     if (this.maximumAllowedCharacter == 0) {
/*  142: 245 */       this.maximumAllowedCharacter = defaultMaximumAllowedCharacter();
/*  143:     */     }
/*  144: 248 */     return this.maximumAllowedCharacter;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public void setMaximumAllowedCharacter(int maximumAllowedCharacter)
/*  148:     */   {
/*  149: 262 */     this.maximumAllowedCharacter = maximumAllowedCharacter;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public void flush()
/*  153:     */     throws IOException
/*  154:     */   {
/*  155: 272 */     this.writer.flush();
/*  156:     */   }
/*  157:     */   
/*  158:     */   public void close()
/*  159:     */     throws IOException
/*  160:     */   {
/*  161: 282 */     this.writer.close();
/*  162:     */   }
/*  163:     */   
/*  164:     */   public void println()
/*  165:     */     throws IOException
/*  166:     */   {
/*  167: 292 */     this.writer.write(this.format.getLineSeparator());
/*  168:     */   }
/*  169:     */   
/*  170:     */   public void write(Attribute attribute)
/*  171:     */     throws IOException
/*  172:     */   {
/*  173: 305 */     writeAttribute(attribute);
/*  174: 307 */     if (this.autoFlush) {
/*  175: 308 */       flush();
/*  176:     */     }
/*  177:     */   }
/*  178:     */   
/*  179:     */   public void write(Document doc)
/*  180:     */     throws IOException
/*  181:     */   {
/*  182: 335 */     writeDeclaration();
/*  183: 337 */     if (doc.getDocType() != null)
/*  184:     */     {
/*  185: 338 */       indent();
/*  186: 339 */       writeDocType(doc.getDocType());
/*  187:     */     }
/*  188: 342 */     int i = 0;
/*  189: 342 */     for (int size = doc.nodeCount(); i < size; i++)
/*  190:     */     {
/*  191: 343 */       Node node = doc.node(i);
/*  192: 344 */       writeNode(node);
/*  193:     */     }
/*  194: 347 */     writePrintln();
/*  195: 349 */     if (this.autoFlush) {
/*  196: 350 */       flush();
/*  197:     */     }
/*  198:     */   }
/*  199:     */   
/*  200:     */   public void write(Element element)
/*  201:     */     throws IOException
/*  202:     */   {
/*  203: 369 */     writeElement(element);
/*  204: 371 */     if (this.autoFlush) {
/*  205: 372 */       flush();
/*  206:     */     }
/*  207:     */   }
/*  208:     */   
/*  209:     */   public void write(CDATA cdata)
/*  210:     */     throws IOException
/*  211:     */   {
/*  212: 386 */     writeCDATA(cdata.getText());
/*  213: 388 */     if (this.autoFlush) {
/*  214: 389 */       flush();
/*  215:     */     }
/*  216:     */   }
/*  217:     */   
/*  218:     */   public void write(Comment comment)
/*  219:     */     throws IOException
/*  220:     */   {
/*  221: 403 */     writeComment(comment.getText());
/*  222: 405 */     if (this.autoFlush) {
/*  223: 406 */       flush();
/*  224:     */     }
/*  225:     */   }
/*  226:     */   
/*  227:     */   public void write(DocumentType docType)
/*  228:     */     throws IOException
/*  229:     */   {
/*  230: 420 */     writeDocType(docType);
/*  231: 422 */     if (this.autoFlush) {
/*  232: 423 */       flush();
/*  233:     */     }
/*  234:     */   }
/*  235:     */   
/*  236:     */   public void write(Entity entity)
/*  237:     */     throws IOException
/*  238:     */   {
/*  239: 437 */     writeEntity(entity);
/*  240: 439 */     if (this.autoFlush) {
/*  241: 440 */       flush();
/*  242:     */     }
/*  243:     */   }
/*  244:     */   
/*  245:     */   public void write(Namespace namespace)
/*  246:     */     throws IOException
/*  247:     */   {
/*  248: 454 */     writeNamespace(namespace);
/*  249: 456 */     if (this.autoFlush) {
/*  250: 457 */       flush();
/*  251:     */     }
/*  252:     */   }
/*  253:     */   
/*  254:     */   public void write(ProcessingInstruction processingInstruction)
/*  255:     */     throws IOException
/*  256:     */   {
/*  257: 472 */     writeProcessingInstruction(processingInstruction);
/*  258: 474 */     if (this.autoFlush) {
/*  259: 475 */       flush();
/*  260:     */     }
/*  261:     */   }
/*  262:     */   
/*  263:     */   public void write(String text)
/*  264:     */     throws IOException
/*  265:     */   {
/*  266: 492 */     writeString(text);
/*  267: 494 */     if (this.autoFlush) {
/*  268: 495 */       flush();
/*  269:     */     }
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void write(Text text)
/*  273:     */     throws IOException
/*  274:     */   {
/*  275: 509 */     writeString(text.getText());
/*  276: 511 */     if (this.autoFlush) {
/*  277: 512 */       flush();
/*  278:     */     }
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void write(Node node)
/*  282:     */     throws IOException
/*  283:     */   {
/*  284: 526 */     writeNode(node);
/*  285: 528 */     if (this.autoFlush) {
/*  286: 529 */       flush();
/*  287:     */     }
/*  288:     */   }
/*  289:     */   
/*  290:     */   public void write(Object object)
/*  291:     */     throws IOException
/*  292:     */   {
/*  293: 544 */     if ((object instanceof Node))
/*  294:     */     {
/*  295: 545 */       write((Node)object);
/*  296:     */     }
/*  297: 546 */     else if ((object instanceof String))
/*  298:     */     {
/*  299: 547 */       write((String)object);
/*  300:     */     }
/*  301: 548 */     else if ((object instanceof List))
/*  302:     */     {
/*  303: 549 */       List list = (List)object;
/*  304:     */       
/*  305: 551 */       int i = 0;
/*  306: 551 */       for (int size = list.size(); i < size; i++) {
/*  307: 552 */         write(list.get(i));
/*  308:     */       }
/*  309:     */     }
/*  310: 554 */     else if (object != null)
/*  311:     */     {
/*  312: 555 */       throw new IOException("Invalid object: " + object);
/*  313:     */     }
/*  314:     */   }
/*  315:     */   
/*  316:     */   public void writeOpen(Element element)
/*  317:     */     throws IOException
/*  318:     */   {
/*  319: 572 */     this.writer.write("<");
/*  320: 573 */     this.writer.write(element.getQualifiedName());
/*  321: 574 */     writeAttributes(element);
/*  322: 575 */     this.writer.write(">");
/*  323:     */   }
/*  324:     */   
/*  325:     */   public void writeClose(Element element)
/*  326:     */     throws IOException
/*  327:     */   {
/*  328: 590 */     writeClose(element.getQualifiedName());
/*  329:     */   }
/*  330:     */   
/*  331:     */   public void parse(InputSource source)
/*  332:     */     throws IOException, SAXException
/*  333:     */   {
/*  334: 596 */     installLexicalHandler();
/*  335: 597 */     super.parse(source);
/*  336:     */   }
/*  337:     */   
/*  338:     */   public void setProperty(String name, Object value)
/*  339:     */     throws SAXNotRecognizedException, SAXNotSupportedException
/*  340:     */   {
/*  341: 602 */     for (int i = 0; i < LEXICAL_HANDLER_NAMES.length; i++) {
/*  342: 603 */       if (LEXICAL_HANDLER_NAMES[i].equals(name))
/*  343:     */       {
/*  344: 604 */         setLexicalHandler((LexicalHandler)value);
/*  345:     */         
/*  346: 606 */         return;
/*  347:     */       }
/*  348:     */     }
/*  349: 610 */     super.setProperty(name, value);
/*  350:     */   }
/*  351:     */   
/*  352:     */   public Object getProperty(String name)
/*  353:     */     throws SAXNotRecognizedException, SAXNotSupportedException
/*  354:     */   {
/*  355: 615 */     for (int i = 0; i < LEXICAL_HANDLER_NAMES.length; i++) {
/*  356: 616 */       if (LEXICAL_HANDLER_NAMES[i].equals(name)) {
/*  357: 617 */         return getLexicalHandler();
/*  358:     */       }
/*  359:     */     }
/*  360: 621 */     return super.getProperty(name);
/*  361:     */   }
/*  362:     */   
/*  363:     */   public void setLexicalHandler(LexicalHandler handler)
/*  364:     */   {
/*  365: 625 */     if (handler == null) {
/*  366: 626 */       throw new NullPointerException("Null lexical handler");
/*  367:     */     }
/*  368: 628 */     this.lexicalHandler = handler;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public LexicalHandler getLexicalHandler()
/*  372:     */   {
/*  373: 633 */     return this.lexicalHandler;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void setDocumentLocator(Locator locator)
/*  377:     */   {
/*  378: 639 */     super.setDocumentLocator(locator);
/*  379:     */   }
/*  380:     */   
/*  381:     */   public void startDocument()
/*  382:     */     throws SAXException
/*  383:     */   {
/*  384:     */     try
/*  385:     */     {
/*  386: 644 */       writeDeclaration();
/*  387: 645 */       super.startDocument();
/*  388:     */     }
/*  389:     */     catch (IOException e)
/*  390:     */     {
/*  391: 647 */       handleException(e);
/*  392:     */     }
/*  393:     */   }
/*  394:     */   
/*  395:     */   public void endDocument()
/*  396:     */     throws SAXException
/*  397:     */   {
/*  398: 652 */     super.endDocument();
/*  399: 654 */     if (this.autoFlush) {
/*  400:     */       try
/*  401:     */       {
/*  402: 656 */         flush();
/*  403:     */       }
/*  404:     */       catch (IOException e) {}
/*  405:     */     }
/*  406:     */   }
/*  407:     */   
/*  408:     */   public void startPrefixMapping(String prefix, String uri)
/*  409:     */     throws SAXException
/*  410:     */   {
/*  411: 664 */     if (this.namespacesMap == null) {
/*  412: 665 */       this.namespacesMap = new HashMap();
/*  413:     */     }
/*  414: 668 */     this.namespacesMap.put(prefix, uri);
/*  415: 669 */     super.startPrefixMapping(prefix, uri);
/*  416:     */   }
/*  417:     */   
/*  418:     */   public void endPrefixMapping(String prefix)
/*  419:     */     throws SAXException
/*  420:     */   {
/*  421: 673 */     super.endPrefixMapping(prefix);
/*  422:     */   }
/*  423:     */   
/*  424:     */   public void startElement(String namespaceURI, String localName, String qName, Attributes attributes)
/*  425:     */     throws SAXException
/*  426:     */   {
/*  427:     */     try
/*  428:     */     {
/*  429: 679 */       this.charsAdded = false;
/*  430:     */       
/*  431: 681 */       writePrintln();
/*  432: 682 */       indent();
/*  433: 683 */       this.writer.write("<");
/*  434: 684 */       this.writer.write(qName);
/*  435: 685 */       writeNamespaces();
/*  436: 686 */       writeAttributes(attributes);
/*  437: 687 */       this.writer.write(">");
/*  438: 688 */       this.indentLevel += 1;
/*  439: 689 */       this.lastOutputNodeType = 1;
/*  440: 690 */       this.lastElementClosed = false;
/*  441:     */       
/*  442: 692 */       super.startElement(namespaceURI, localName, qName, attributes);
/*  443:     */     }
/*  444:     */     catch (IOException e)
/*  445:     */     {
/*  446: 694 */       handleException(e);
/*  447:     */     }
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void endElement(String namespaceURI, String localName, String qName)
/*  451:     */     throws SAXException
/*  452:     */   {
/*  453:     */     try
/*  454:     */     {
/*  455: 701 */       this.charsAdded = false;
/*  456: 702 */       this.indentLevel -= 1;
/*  457: 704 */       if (this.lastElementClosed)
/*  458:     */       {
/*  459: 705 */         writePrintln();
/*  460: 706 */         indent();
/*  461:     */       }
/*  462: 711 */       boolean hadContent = true;
/*  463: 713 */       if (hadContent) {
/*  464: 714 */         writeClose(qName);
/*  465:     */       } else {
/*  466: 716 */         writeEmptyElementClose(qName);
/*  467:     */       }
/*  468: 719 */       this.lastOutputNodeType = 1;
/*  469: 720 */       this.lastElementClosed = true;
/*  470:     */       
/*  471: 722 */       super.endElement(namespaceURI, localName, qName);
/*  472:     */     }
/*  473:     */     catch (IOException e)
/*  474:     */     {
/*  475: 724 */       handleException(e);
/*  476:     */     }
/*  477:     */   }
/*  478:     */   
/*  479:     */   public void characters(char[] ch, int start, int length)
/*  480:     */     throws SAXException
/*  481:     */   {
/*  482: 730 */     if ((ch == null) || (ch.length == 0) || (length <= 0)) {
/*  483: 731 */       return;
/*  484:     */     }
/*  485:     */     try
/*  486:     */     {
/*  487: 741 */       String string = String.valueOf(ch, start, length);
/*  488: 743 */       if (this.escapeText) {
/*  489: 744 */         string = escapeElementEntities(string);
/*  490:     */       }
/*  491: 747 */       if (this.format.isTrimText())
/*  492:     */       {
/*  493: 748 */         if ((this.lastOutputNodeType == 3) && (!this.charsAdded)) {
/*  494: 749 */           this.writer.write(32);
/*  495: 750 */         } else if ((this.charsAdded) && (Character.isWhitespace(this.lastChar))) {
/*  496: 751 */           this.writer.write(32);
/*  497: 752 */         } else if ((this.lastOutputNodeType == 1) && (this.format.isPadText()) && (this.lastElementClosed) && (Character.isWhitespace(ch[0]))) {
/*  498: 755 */           this.writer.write(" ");
/*  499:     */         }
/*  500: 758 */         String delim = "";
/*  501: 759 */         StringTokenizer tokens = new StringTokenizer(string);
/*  502: 761 */         while (tokens.hasMoreTokens())
/*  503:     */         {
/*  504: 762 */           this.writer.write(delim);
/*  505: 763 */           this.writer.write(tokens.nextToken());
/*  506: 764 */           delim = " ";
/*  507:     */         }
/*  508:     */       }
/*  509:     */       else
/*  510:     */       {
/*  511: 767 */         this.writer.write(string);
/*  512:     */       }
/*  513: 770 */       this.charsAdded = true;
/*  514: 771 */       this.lastChar = ch[(start + length - 1)];
/*  515: 772 */       this.lastOutputNodeType = 3;
/*  516:     */       
/*  517: 774 */       super.characters(ch, start, length);
/*  518:     */     }
/*  519:     */     catch (IOException e)
/*  520:     */     {
/*  521: 776 */       handleException(e);
/*  522:     */     }
/*  523:     */   }
/*  524:     */   
/*  525:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/*  526:     */     throws SAXException
/*  527:     */   {
/*  528: 782 */     super.ignorableWhitespace(ch, start, length);
/*  529:     */   }
/*  530:     */   
/*  531:     */   public void processingInstruction(String target, String data)
/*  532:     */     throws SAXException
/*  533:     */   {
/*  534:     */     try
/*  535:     */     {
/*  536: 788 */       indent();
/*  537: 789 */       this.writer.write("<?");
/*  538: 790 */       this.writer.write(target);
/*  539: 791 */       this.writer.write(" ");
/*  540: 792 */       this.writer.write(data);
/*  541: 793 */       this.writer.write("?>");
/*  542: 794 */       writePrintln();
/*  543: 795 */       this.lastOutputNodeType = 7;
/*  544:     */       
/*  545: 797 */       super.processingInstruction(target, data);
/*  546:     */     }
/*  547:     */     catch (IOException e)
/*  548:     */     {
/*  549: 799 */       handleException(e);
/*  550:     */     }
/*  551:     */   }
/*  552:     */   
/*  553:     */   public void notationDecl(String name, String publicID, String systemID)
/*  554:     */     throws SAXException
/*  555:     */   {
/*  556: 807 */     super.notationDecl(name, publicID, systemID);
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void unparsedEntityDecl(String name, String publicID, String systemID, String notationName)
/*  560:     */     throws SAXException
/*  561:     */   {
/*  562: 812 */     super.unparsedEntityDecl(name, publicID, systemID, notationName);
/*  563:     */   }
/*  564:     */   
/*  565:     */   public void startDTD(String name, String publicID, String systemID)
/*  566:     */     throws SAXException
/*  567:     */   {
/*  568: 819 */     this.inDTD = true;
/*  569:     */     try
/*  570:     */     {
/*  571: 822 */       writeDocType(name, publicID, systemID);
/*  572:     */     }
/*  573:     */     catch (IOException e)
/*  574:     */     {
/*  575: 824 */       handleException(e);
/*  576:     */     }
/*  577: 827 */     if (this.lexicalHandler != null) {
/*  578: 828 */       this.lexicalHandler.startDTD(name, publicID, systemID);
/*  579:     */     }
/*  580:     */   }
/*  581:     */   
/*  582:     */   public void endDTD()
/*  583:     */     throws SAXException
/*  584:     */   {
/*  585: 833 */     this.inDTD = false;
/*  586: 835 */     if (this.lexicalHandler != null) {
/*  587: 836 */       this.lexicalHandler.endDTD();
/*  588:     */     }
/*  589:     */   }
/*  590:     */   
/*  591:     */   public void startCDATA()
/*  592:     */     throws SAXException
/*  593:     */   {
/*  594:     */     try
/*  595:     */     {
/*  596: 842 */       this.writer.write("<![CDATA[");
/*  597:     */     }
/*  598:     */     catch (IOException e)
/*  599:     */     {
/*  600: 844 */       handleException(e);
/*  601:     */     }
/*  602: 847 */     if (this.lexicalHandler != null) {
/*  603: 848 */       this.lexicalHandler.startCDATA();
/*  604:     */     }
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void endCDATA()
/*  608:     */     throws SAXException
/*  609:     */   {
/*  610:     */     try
/*  611:     */     {
/*  612: 854 */       this.writer.write("]]>");
/*  613:     */     }
/*  614:     */     catch (IOException e)
/*  615:     */     {
/*  616: 856 */       handleException(e);
/*  617:     */     }
/*  618: 859 */     if (this.lexicalHandler != null) {
/*  619: 860 */       this.lexicalHandler.endCDATA();
/*  620:     */     }
/*  621:     */   }
/*  622:     */   
/*  623:     */   public void startEntity(String name)
/*  624:     */     throws SAXException
/*  625:     */   {
/*  626:     */     try
/*  627:     */     {
/*  628: 866 */       writeEntityRef(name);
/*  629:     */     }
/*  630:     */     catch (IOException e)
/*  631:     */     {
/*  632: 868 */       handleException(e);
/*  633:     */     }
/*  634: 871 */     if (this.lexicalHandler != null) {
/*  635: 872 */       this.lexicalHandler.startEntity(name);
/*  636:     */     }
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void endEntity(String name)
/*  640:     */     throws SAXException
/*  641:     */   {
/*  642: 877 */     if (this.lexicalHandler != null) {
/*  643: 878 */       this.lexicalHandler.endEntity(name);
/*  644:     */     }
/*  645:     */   }
/*  646:     */   
/*  647:     */   public void comment(char[] ch, int start, int length)
/*  648:     */     throws SAXException
/*  649:     */   {
/*  650: 883 */     if ((this.showCommentsInDTDs) || (!this.inDTD)) {
/*  651:     */       try
/*  652:     */       {
/*  653: 885 */         this.charsAdded = false;
/*  654: 886 */         writeComment(new String(ch, start, length));
/*  655:     */       }
/*  656:     */       catch (IOException e)
/*  657:     */       {
/*  658: 888 */         handleException(e);
/*  659:     */       }
/*  660:     */     }
/*  661: 892 */     if (this.lexicalHandler != null) {
/*  662: 893 */       this.lexicalHandler.comment(ch, start, length);
/*  663:     */     }
/*  664:     */   }
/*  665:     */   
/*  666:     */   protected void writeElement(Element element)
/*  667:     */     throws IOException
/*  668:     */   {
/*  669: 900 */     int size = element.nodeCount();
/*  670: 901 */     String qualifiedName = element.getQualifiedName();
/*  671:     */     
/*  672: 903 */     writePrintln();
/*  673: 904 */     indent();
/*  674:     */     
/*  675: 906 */     this.writer.write("<");
/*  676: 907 */     this.writer.write(qualifiedName);
/*  677:     */     
/*  678: 909 */     int previouslyDeclaredNamespaces = this.namespaceStack.size();
/*  679: 910 */     Namespace ns = element.getNamespace();
/*  680: 912 */     if (isNamespaceDeclaration(ns))
/*  681:     */     {
/*  682: 913 */       this.namespaceStack.push(ns);
/*  683: 914 */       writeNamespace(ns);
/*  684:     */     }
/*  685: 918 */     boolean textOnly = true;
/*  686: 920 */     for (int i = 0; i < size; i++)
/*  687:     */     {
/*  688: 921 */       Node node = element.node(i);
/*  689: 923 */       if ((node instanceof Namespace))
/*  690:     */       {
/*  691: 924 */         Namespace additional = (Namespace)node;
/*  692: 926 */         if (isNamespaceDeclaration(additional))
/*  693:     */         {
/*  694: 927 */           this.namespaceStack.push(additional);
/*  695: 928 */           writeNamespace(additional);
/*  696:     */         }
/*  697:     */       }
/*  698: 930 */       else if ((node instanceof Element))
/*  699:     */       {
/*  700: 931 */         textOnly = false;
/*  701:     */       }
/*  702: 932 */       else if ((node instanceof Comment))
/*  703:     */       {
/*  704: 933 */         textOnly = false;
/*  705:     */       }
/*  706:     */     }
/*  707: 937 */     writeAttributes(element);
/*  708:     */     
/*  709: 939 */     this.lastOutputNodeType = 1;
/*  710: 941 */     if (size <= 0)
/*  711:     */     {
/*  712: 942 */       writeEmptyElementClose(qualifiedName);
/*  713:     */     }
/*  714:     */     else
/*  715:     */     {
/*  716: 944 */       this.writer.write(">");
/*  717: 946 */       if (textOnly)
/*  718:     */       {
/*  719: 949 */         writeElementContent(element);
/*  720:     */       }
/*  721:     */       else
/*  722:     */       {
/*  723: 952 */         this.indentLevel += 1;
/*  724:     */         
/*  725: 954 */         writeElementContent(element);
/*  726:     */         
/*  727: 956 */         this.indentLevel -= 1;
/*  728:     */         
/*  729: 958 */         writePrintln();
/*  730: 959 */         indent();
/*  731:     */       }
/*  732: 962 */       this.writer.write("</");
/*  733: 963 */       this.writer.write(qualifiedName);
/*  734: 964 */       this.writer.write(">");
/*  735:     */     }
/*  736: 968 */     while (this.namespaceStack.size() > previouslyDeclaredNamespaces) {
/*  737: 969 */       this.namespaceStack.pop();
/*  738:     */     }
/*  739: 972 */     this.lastOutputNodeType = 1;
/*  740:     */   }
/*  741:     */   
/*  742:     */   protected final boolean isElementSpacePreserved(Element element)
/*  743:     */   {
/*  744: 985 */     Attribute attr = element.attribute("space");
/*  745: 986 */     boolean preserveFound = this.preserve;
/*  746: 988 */     if (attr != null) {
/*  747: 989 */       if (("xml".equals(attr.getNamespacePrefix())) && ("preserve".equals(attr.getText()))) {
/*  748: 991 */         preserveFound = true;
/*  749:     */       } else {
/*  750: 993 */         preserveFound = false;
/*  751:     */       }
/*  752:     */     }
/*  753: 997 */     return preserveFound;
/*  754:     */   }
/*  755:     */   
/*  756:     */   protected void writeElementContent(Element element)
/*  757:     */     throws IOException
/*  758:     */   {
/*  759:1014 */     boolean trim = this.format.isTrimText();
/*  760:1015 */     boolean oldPreserve = this.preserve;
/*  761:1017 */     if (trim)
/*  762:     */     {
/*  763:1018 */       this.preserve = isElementSpacePreserved(element);
/*  764:1019 */       trim = !this.preserve;
/*  765:     */     }
/*  766:1022 */     if (trim)
/*  767:     */     {
/*  768:1025 */       Text lastTextNode = null;
/*  769:1026 */       StringBuffer buff = null;
/*  770:1027 */       boolean textOnly = true;
/*  771:     */       
/*  772:1029 */       int i = 0;
/*  773:1029 */       for (int size = element.nodeCount(); i < size; i++)
/*  774:     */       {
/*  775:1030 */         Node node = element.node(i);
/*  776:1032 */         if ((node instanceof Text))
/*  777:     */         {
/*  778:1033 */           if (lastTextNode == null)
/*  779:     */           {
/*  780:1034 */             lastTextNode = (Text)node;
/*  781:     */           }
/*  782:     */           else
/*  783:     */           {
/*  784:1036 */             if (buff == null) {
/*  785:1037 */               buff = new StringBuffer(lastTextNode.getText());
/*  786:     */             }
/*  787:1040 */             buff.append(((Text)node).getText());
/*  788:     */           }
/*  789:     */         }
/*  790:     */         else
/*  791:     */         {
/*  792:1043 */           if ((!textOnly) && (this.format.isPadText()))
/*  793:     */           {
/*  794:1046 */             char firstChar = 'a';
/*  795:1047 */             if (buff != null) {
/*  796:1048 */               firstChar = buff.charAt(0);
/*  797:1049 */             } else if (lastTextNode != null) {
/*  798:1050 */               firstChar = lastTextNode.getText().charAt(0);
/*  799:     */             }
/*  800:1053 */             if (Character.isWhitespace(firstChar)) {
/*  801:1054 */               this.writer.write(" ");
/*  802:     */             }
/*  803:     */           }
/*  804:1058 */           if (lastTextNode != null)
/*  805:     */           {
/*  806:1059 */             if (buff != null)
/*  807:     */             {
/*  808:1060 */               writeString(buff.toString());
/*  809:1061 */               buff = null;
/*  810:     */             }
/*  811:     */             else
/*  812:     */             {
/*  813:1063 */               writeString(lastTextNode.getText());
/*  814:     */             }
/*  815:1066 */             if (this.format.isPadText())
/*  816:     */             {
/*  817:1069 */               char lastTextChar = 'a';
/*  818:1070 */               if (buff != null)
/*  819:     */               {
/*  820:1071 */                 lastTextChar = buff.charAt(buff.length() - 1);
/*  821:     */               }
/*  822:1072 */               else if (lastTextNode != null)
/*  823:     */               {
/*  824:1073 */                 String txt = lastTextNode.getText();
/*  825:1074 */                 lastTextChar = txt.charAt(txt.length() - 1);
/*  826:     */               }
/*  827:1077 */               if (Character.isWhitespace(lastTextChar)) {
/*  828:1078 */                 this.writer.write(" ");
/*  829:     */               }
/*  830:     */             }
/*  831:1082 */             lastTextNode = null;
/*  832:     */           }
/*  833:1085 */           textOnly = false;
/*  834:1086 */           writeNode(node);
/*  835:     */         }
/*  836:     */       }
/*  837:1090 */       if (lastTextNode != null)
/*  838:     */       {
/*  839:1091 */         if ((!textOnly) && (this.format.isPadText()))
/*  840:     */         {
/*  841:1094 */           char firstChar = 'a';
/*  842:1095 */           if (buff != null) {
/*  843:1096 */             firstChar = buff.charAt(0);
/*  844:     */           } else {
/*  845:1098 */             firstChar = lastTextNode.getText().charAt(0);
/*  846:     */           }
/*  847:1101 */           if (Character.isWhitespace(firstChar)) {
/*  848:1102 */             this.writer.write(" ");
/*  849:     */           }
/*  850:     */         }
/*  851:1106 */         if (buff != null)
/*  852:     */         {
/*  853:1107 */           writeString(buff.toString());
/*  854:1108 */           buff = null;
/*  855:     */         }
/*  856:     */         else
/*  857:     */         {
/*  858:1110 */           writeString(lastTextNode.getText());
/*  859:     */         }
/*  860:1113 */         lastTextNode = null;
/*  861:     */       }
/*  862:     */     }
/*  863:     */     else
/*  864:     */     {
/*  865:1116 */       Node lastTextNode = null;
/*  866:     */       
/*  867:1118 */       int i = 0;
/*  868:1118 */       for (int size = element.nodeCount(); i < size; i++)
/*  869:     */       {
/*  870:1119 */         Node node = element.node(i);
/*  871:1121 */         if ((node instanceof Text))
/*  872:     */         {
/*  873:1122 */           writeNode(node);
/*  874:1123 */           lastTextNode = node;
/*  875:     */         }
/*  876:     */         else
/*  877:     */         {
/*  878:1125 */           if ((lastTextNode != null) && (this.format.isPadText()))
/*  879:     */           {
/*  880:1128 */             String txt = lastTextNode.getText();
/*  881:1129 */             char lastTextChar = txt.charAt(txt.length() - 1);
/*  882:1131 */             if (Character.isWhitespace(lastTextChar)) {
/*  883:1132 */               this.writer.write(" ");
/*  884:     */             }
/*  885:     */           }
/*  886:1136 */           writeNode(node);
/*  887:     */           
/*  888:     */ 
/*  889:     */ 
/*  890:     */ 
/*  891:     */ 
/*  892:1142 */           lastTextNode = null;
/*  893:     */         }
/*  894:     */       }
/*  895:     */     }
/*  896:1147 */     this.preserve = oldPreserve;
/*  897:     */   }
/*  898:     */   
/*  899:     */   protected void writeCDATA(String text)
/*  900:     */     throws IOException
/*  901:     */   {
/*  902:1151 */     this.writer.write("<![CDATA[");
/*  903:1153 */     if (text != null) {
/*  904:1154 */       this.writer.write(text);
/*  905:     */     }
/*  906:1157 */     this.writer.write("]]>");
/*  907:     */     
/*  908:1159 */     this.lastOutputNodeType = 4;
/*  909:     */   }
/*  910:     */   
/*  911:     */   protected void writeDocType(DocumentType docType)
/*  912:     */     throws IOException
/*  913:     */   {
/*  914:1163 */     if (docType != null)
/*  915:     */     {
/*  916:1164 */       docType.write(this.writer);
/*  917:1165 */       writePrintln();
/*  918:     */     }
/*  919:     */   }
/*  920:     */   
/*  921:     */   protected void writeNamespace(Namespace namespace)
/*  922:     */     throws IOException
/*  923:     */   {
/*  924:1170 */     if (namespace != null) {
/*  925:1171 */       writeNamespace(namespace.getPrefix(), namespace.getURI());
/*  926:     */     }
/*  927:     */   }
/*  928:     */   
/*  929:     */   protected void writeNamespaces()
/*  930:     */     throws IOException
/*  931:     */   {
/*  932:1182 */     if (this.namespacesMap != null)
/*  933:     */     {
/*  934:1183 */       Iterator iter = this.namespacesMap.entrySet().iterator();
/*  935:1184 */       while (iter.hasNext())
/*  936:     */       {
/*  937:1185 */         Map.Entry entry = (Map.Entry)iter.next();
/*  938:1186 */         String prefix = (String)entry.getKey();
/*  939:1187 */         String uri = (String)entry.getValue();
/*  940:1188 */         writeNamespace(prefix, uri);
/*  941:     */       }
/*  942:1191 */       this.namespacesMap = null;
/*  943:     */     }
/*  944:     */   }
/*  945:     */   
/*  946:     */   protected void writeNamespace(String prefix, String uri)
/*  947:     */     throws IOException
/*  948:     */   {
/*  949:1207 */     if ((prefix != null) && (prefix.length() > 0))
/*  950:     */     {
/*  951:1208 */       this.writer.write(" xmlns:");
/*  952:1209 */       this.writer.write(prefix);
/*  953:1210 */       this.writer.write("=\"");
/*  954:     */     }
/*  955:     */     else
/*  956:     */     {
/*  957:1212 */       this.writer.write(" xmlns=\"");
/*  958:     */     }
/*  959:1215 */     this.writer.write(uri);
/*  960:1216 */     this.writer.write("\"");
/*  961:     */   }
/*  962:     */   
/*  963:     */   protected void writeProcessingInstruction(ProcessingInstruction pi)
/*  964:     */     throws IOException
/*  965:     */   {
/*  966:1222 */     this.writer.write("<?");
/*  967:1223 */     this.writer.write(pi.getName());
/*  968:1224 */     this.writer.write(" ");
/*  969:1225 */     this.writer.write(pi.getText());
/*  970:1226 */     this.writer.write("?>");
/*  971:1227 */     writePrintln();
/*  972:     */     
/*  973:1229 */     this.lastOutputNodeType = 7;
/*  974:     */   }
/*  975:     */   
/*  976:     */   protected void writeString(String text)
/*  977:     */     throws IOException
/*  978:     */   {
/*  979:1233 */     if ((text != null) && (text.length() > 0))
/*  980:     */     {
/*  981:1234 */       if (this.escapeText) {
/*  982:1235 */         text = escapeElementEntities(text);
/*  983:     */       }
/*  984:1243 */       if (this.format.isTrimText())
/*  985:     */       {
/*  986:1244 */         boolean first = true;
/*  987:1245 */         StringTokenizer tokenizer = new StringTokenizer(text);
/*  988:1247 */         while (tokenizer.hasMoreTokens())
/*  989:     */         {
/*  990:1248 */           String token = tokenizer.nextToken();
/*  991:1250 */           if (first)
/*  992:     */           {
/*  993:1251 */             first = false;
/*  994:1253 */             if (this.lastOutputNodeType == 3) {
/*  995:1254 */               this.writer.write(" ");
/*  996:     */             }
/*  997:     */           }
/*  998:     */           else
/*  999:     */           {
/* 1000:1257 */             this.writer.write(" ");
/* 1001:     */           }
/* 1002:1260 */           this.writer.write(token);
/* 1003:1261 */           this.lastOutputNodeType = 3;
/* 1004:1262 */           this.lastChar = token.charAt(token.length() - 1);
/* 1005:     */         }
/* 1006:     */       }
/* 1007:     */       else
/* 1008:     */       {
/* 1009:1265 */         this.lastOutputNodeType = 3;
/* 1010:1266 */         this.writer.write(text);
/* 1011:1267 */         this.lastChar = text.charAt(text.length() - 1);
/* 1012:     */       }
/* 1013:     */     }
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   protected void writeNodeText(Node node)
/* 1017:     */     throws IOException
/* 1018:     */   {
/* 1019:1283 */     String text = node.getText();
/* 1020:1285 */     if ((text != null) && (text.length() > 0))
/* 1021:     */     {
/* 1022:1286 */       if (this.escapeText) {
/* 1023:1287 */         text = escapeElementEntities(text);
/* 1024:     */       }
/* 1025:1290 */       this.lastOutputNodeType = 3;
/* 1026:1291 */       this.writer.write(text);
/* 1027:1292 */       this.lastChar = text.charAt(text.length() - 1);
/* 1028:     */     }
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   protected void writeNode(Node node)
/* 1032:     */     throws IOException
/* 1033:     */   {
/* 1034:1297 */     int nodeType = node.getNodeType();
/* 1035:1299 */     switch (nodeType)
/* 1036:     */     {
/* 1037:     */     case 1: 
/* 1038:1301 */       writeElement((Element)node);
/* 1039:     */       
/* 1040:1303 */       break;
/* 1041:     */     case 2: 
/* 1042:1306 */       writeAttribute((Attribute)node);
/* 1043:     */       
/* 1044:1308 */       break;
/* 1045:     */     case 3: 
/* 1046:1311 */       writeNodeText(node);
/* 1047:     */       
/* 1048:     */ 
/* 1049:1314 */       break;
/* 1050:     */     case 4: 
/* 1051:1317 */       writeCDATA(node.getText());
/* 1052:     */       
/* 1053:1319 */       break;
/* 1054:     */     case 5: 
/* 1055:1322 */       writeEntity((Entity)node);
/* 1056:     */       
/* 1057:1324 */       break;
/* 1058:     */     case 7: 
/* 1059:1327 */       writeProcessingInstruction((ProcessingInstruction)node);
/* 1060:     */       
/* 1061:1329 */       break;
/* 1062:     */     case 8: 
/* 1063:1332 */       writeComment(node.getText());
/* 1064:     */       
/* 1065:1334 */       break;
/* 1066:     */     case 9: 
/* 1067:1337 */       write((Document)node);
/* 1068:     */       
/* 1069:1339 */       break;
/* 1070:     */     case 10: 
/* 1071:1342 */       writeDocType((DocumentType)node);
/* 1072:     */       
/* 1073:1344 */       break;
/* 1074:     */     case 13: 
/* 1075:     */       break;
/* 1076:     */     case 6: 
/* 1077:     */     case 11: 
/* 1078:     */     case 12: 
/* 1079:     */     default: 
/* 1080:1353 */       throw new IOException("Invalid node type: " + node);
/* 1081:     */     }
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   protected void installLexicalHandler()
/* 1085:     */   {
/* 1086:1358 */     XMLReader parent = getParent();
/* 1087:1360 */     if (parent == null) {
/* 1088:1361 */       throw new NullPointerException("No parent for filter");
/* 1089:     */     }
/* 1090:1365 */     for (int i = 0; i < LEXICAL_HANDLER_NAMES.length; i++) {
/* 1091:     */       try
/* 1092:     */       {
/* 1093:1367 */         parent.setProperty(LEXICAL_HANDLER_NAMES[i], this);
/* 1094:     */       }
/* 1095:     */       catch (SAXNotRecognizedException ex) {}catch (SAXNotSupportedException ex) {}
/* 1096:     */     }
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   protected void writeDocType(String name, String publicID, String systemID)
/* 1100:     */     throws IOException
/* 1101:     */   {
/* 1102:1380 */     boolean hasPublic = false;
/* 1103:     */     
/* 1104:1382 */     this.writer.write("<!DOCTYPE ");
/* 1105:1383 */     this.writer.write(name);
/* 1106:1385 */     if ((publicID != null) && (!publicID.equals("")))
/* 1107:     */     {
/* 1108:1386 */       this.writer.write(" PUBLIC \"");
/* 1109:1387 */       this.writer.write(publicID);
/* 1110:1388 */       this.writer.write("\"");
/* 1111:1389 */       hasPublic = true;
/* 1112:     */     }
/* 1113:1392 */     if ((systemID != null) && (!systemID.equals("")))
/* 1114:     */     {
/* 1115:1393 */       if (!hasPublic) {
/* 1116:1394 */         this.writer.write(" SYSTEM");
/* 1117:     */       }
/* 1118:1397 */       this.writer.write(" \"");
/* 1119:1398 */       this.writer.write(systemID);
/* 1120:1399 */       this.writer.write("\"");
/* 1121:     */     }
/* 1122:1402 */     this.writer.write(">");
/* 1123:1403 */     writePrintln();
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   protected void writeEntity(Entity entity)
/* 1127:     */     throws IOException
/* 1128:     */   {
/* 1129:1407 */     if (!resolveEntityRefs()) {
/* 1130:1408 */       writeEntityRef(entity.getName());
/* 1131:     */     } else {
/* 1132:1410 */       this.writer.write(entity.getText());
/* 1133:     */     }
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   protected void writeEntityRef(String name)
/* 1137:     */     throws IOException
/* 1138:     */   {
/* 1139:1415 */     this.writer.write("&");
/* 1140:1416 */     this.writer.write(name);
/* 1141:1417 */     this.writer.write(";");
/* 1142:     */     
/* 1143:1419 */     this.lastOutputNodeType = 5;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   protected void writeComment(String text)
/* 1147:     */     throws IOException
/* 1148:     */   {
/* 1149:1423 */     if (this.format.isNewlines())
/* 1150:     */     {
/* 1151:1424 */       println();
/* 1152:1425 */       indent();
/* 1153:     */     }
/* 1154:1428 */     this.writer.write("<!--");
/* 1155:1429 */     this.writer.write(text);
/* 1156:1430 */     this.writer.write("-->");
/* 1157:     */     
/* 1158:1432 */     this.lastOutputNodeType = 8;
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   protected void writeAttributes(Element element)
/* 1162:     */     throws IOException
/* 1163:     */   {
/* 1164:1449 */     int i = 0;
/* 1165:1449 */     for (int size = element.attributeCount(); i < size; i++)
/* 1166:     */     {
/* 1167:1450 */       Attribute attribute = element.attribute(i);
/* 1168:1451 */       Namespace ns = attribute.getNamespace();
/* 1169:1453 */       if ((ns != null) && (ns != Namespace.NO_NAMESPACE) && (ns != Namespace.XML_NAMESPACE))
/* 1170:     */       {
/* 1171:1455 */         String prefix = ns.getPrefix();
/* 1172:1456 */         String uri = this.namespaceStack.getURI(prefix);
/* 1173:1458 */         if (!ns.getURI().equals(uri))
/* 1174:     */         {
/* 1175:1459 */           writeNamespace(ns);
/* 1176:1460 */           this.namespaceStack.push(ns);
/* 1177:     */         }
/* 1178:     */       }
/* 1179:1467 */       String attName = attribute.getName();
/* 1180:1469 */       if (attName.startsWith("xmlns:"))
/* 1181:     */       {
/* 1182:1470 */         String prefix = attName.substring(6);
/* 1183:1472 */         if (this.namespaceStack.getNamespaceForPrefix(prefix) == null)
/* 1184:     */         {
/* 1185:1473 */           String uri = attribute.getValue();
/* 1186:1474 */           this.namespaceStack.push(prefix, uri);
/* 1187:1475 */           writeNamespace(prefix, uri);
/* 1188:     */         }
/* 1189:     */       }
/* 1190:1477 */       else if (attName.equals("xmlns"))
/* 1191:     */       {
/* 1192:1478 */         if (this.namespaceStack.getDefaultNamespace() == null)
/* 1193:     */         {
/* 1194:1479 */           String uri = attribute.getValue();
/* 1195:1480 */           this.namespaceStack.push(null, uri);
/* 1196:1481 */           writeNamespace(null, uri);
/* 1197:     */         }
/* 1198:     */       }
/* 1199:     */       else
/* 1200:     */       {
/* 1201:1484 */         char quote = this.format.getAttributeQuoteCharacter();
/* 1202:1485 */         this.writer.write(" ");
/* 1203:1486 */         this.writer.write(attribute.getQualifiedName());
/* 1204:1487 */         this.writer.write("=");
/* 1205:1488 */         this.writer.write(quote);
/* 1206:1489 */         writeEscapeAttributeEntities(attribute.getValue());
/* 1207:1490 */         this.writer.write(quote);
/* 1208:     */       }
/* 1209:     */     }
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   protected void writeAttribute(Attribute attribute)
/* 1213:     */     throws IOException
/* 1214:     */   {
/* 1215:1496 */     this.writer.write(" ");
/* 1216:1497 */     this.writer.write(attribute.getQualifiedName());
/* 1217:1498 */     this.writer.write("=");
/* 1218:     */     
/* 1219:1500 */     char quote = this.format.getAttributeQuoteCharacter();
/* 1220:1501 */     this.writer.write(quote);
/* 1221:     */     
/* 1222:1503 */     writeEscapeAttributeEntities(attribute.getValue());
/* 1223:     */     
/* 1224:1505 */     this.writer.write(quote);
/* 1225:1506 */     this.lastOutputNodeType = 2;
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   protected void writeAttributes(Attributes attributes)
/* 1229:     */     throws IOException
/* 1230:     */   {
/* 1231:1510 */     int i = 0;
/* 1232:1510 */     for (int size = attributes.getLength(); i < size; i++) {
/* 1233:1511 */       writeAttribute(attributes, i);
/* 1234:     */     }
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   protected void writeAttribute(Attributes attributes, int index)
/* 1238:     */     throws IOException
/* 1239:     */   {
/* 1240:1517 */     char quote = this.format.getAttributeQuoteCharacter();
/* 1241:1518 */     this.writer.write(" ");
/* 1242:1519 */     this.writer.write(attributes.getQName(index));
/* 1243:1520 */     this.writer.write("=");
/* 1244:1521 */     this.writer.write(quote);
/* 1245:1522 */     writeEscapeAttributeEntities(attributes.getValue(index));
/* 1246:1523 */     this.writer.write(quote);
/* 1247:     */   }
/* 1248:     */   
/* 1249:     */   protected void indent()
/* 1250:     */     throws IOException
/* 1251:     */   {
/* 1252:1527 */     String indent = this.format.getIndent();
/* 1253:1529 */     if ((indent != null) && (indent.length() > 0)) {
/* 1254:1530 */       for (int i = 0; i < this.indentLevel; i++) {
/* 1255:1531 */         this.writer.write(indent);
/* 1256:     */       }
/* 1257:     */     }
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   protected void writePrintln()
/* 1261:     */     throws IOException
/* 1262:     */   {
/* 1263:1545 */     if (this.format.isNewlines())
/* 1264:     */     {
/* 1265:1546 */       String seperator = this.format.getLineSeparator();
/* 1266:1547 */       if (this.lastChar != seperator.charAt(seperator.length() - 1)) {
/* 1267:1548 */         this.writer.write(this.format.getLineSeparator());
/* 1268:     */       }
/* 1269:     */     }
/* 1270:     */   }
/* 1271:     */   
/* 1272:     */   protected Writer createWriter(OutputStream outStream, String encoding)
/* 1273:     */     throws UnsupportedEncodingException
/* 1274:     */   {
/* 1275:1568 */     return new BufferedWriter(new OutputStreamWriter(outStream, encoding));
/* 1276:     */   }
/* 1277:     */   
/* 1278:     */   protected void writeDeclaration()
/* 1279:     */     throws IOException
/* 1280:     */   {
/* 1281:1581 */     String encoding = this.format.getEncoding();
/* 1282:1584 */     if (!this.format.isSuppressDeclaration())
/* 1283:     */     {
/* 1284:1586 */       if (encoding.equals("UTF8"))
/* 1285:     */       {
/* 1286:1587 */         this.writer.write("<?xml version=\"1.0\"");
/* 1287:1589 */         if (!this.format.isOmitEncoding()) {
/* 1288:1590 */           this.writer.write(" encoding=\"UTF-8\"");
/* 1289:     */         }
/* 1290:1593 */         this.writer.write("?>");
/* 1291:     */       }
/* 1292:     */       else
/* 1293:     */       {
/* 1294:1595 */         this.writer.write("<?xml version=\"1.0\"");
/* 1295:1597 */         if (!this.format.isOmitEncoding()) {
/* 1296:1598 */           this.writer.write(" encoding=\"" + encoding + "\"");
/* 1297:     */         }
/* 1298:1601 */         this.writer.write("?>");
/* 1299:     */       }
/* 1300:1604 */       if (this.format.isNewLineAfterDeclaration()) {
/* 1301:1605 */         println();
/* 1302:     */       }
/* 1303:     */     }
/* 1304:     */   }
/* 1305:     */   
/* 1306:     */   protected void writeClose(String qualifiedName)
/* 1307:     */     throws IOException
/* 1308:     */   {
/* 1309:1611 */     this.writer.write("</");
/* 1310:1612 */     this.writer.write(qualifiedName);
/* 1311:1613 */     this.writer.write(">");
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   protected void writeEmptyElementClose(String qualifiedName)
/* 1315:     */     throws IOException
/* 1316:     */   {
/* 1317:1619 */     if (!this.format.isExpandEmptyElements())
/* 1318:     */     {
/* 1319:1620 */       this.writer.write("/>");
/* 1320:     */     }
/* 1321:     */     else
/* 1322:     */     {
/* 1323:1622 */       this.writer.write("></");
/* 1324:1623 */       this.writer.write(qualifiedName);
/* 1325:1624 */       this.writer.write(">");
/* 1326:     */     }
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   protected boolean isExpandEmptyElements()
/* 1330:     */   {
/* 1331:1629 */     return this.format.isExpandEmptyElements();
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   protected String escapeElementEntities(String text)
/* 1335:     */   {
/* 1336:1643 */     char[] block = null;
/* 1337:     */     
/* 1338:1645 */     int last = 0;
/* 1339:1646 */     int size = text.length();
/* 1340:1648 */     for (int i = 0; i < size; i++)
/* 1341:     */     {
/* 1342:1649 */       String entity = null;
/* 1343:1650 */       char c = text.charAt(i);
/* 1344:1652 */       switch (c)
/* 1345:     */       {
/* 1346:     */       case '<': 
/* 1347:1654 */         entity = "&lt;";
/* 1348:     */         
/* 1349:1656 */         break;
/* 1350:     */       case '>': 
/* 1351:1659 */         entity = "&gt;";
/* 1352:     */         
/* 1353:1661 */         break;
/* 1354:     */       case '&': 
/* 1355:1664 */         entity = "&amp;";
/* 1356:     */         
/* 1357:1666 */         break;
/* 1358:     */       case '\t': 
/* 1359:     */       case '\n': 
/* 1360:     */       case '\r': 
/* 1361:1673 */         if (this.preserve) {
/* 1362:1674 */           entity = String.valueOf(c);
/* 1363:     */         }
/* 1364:     */         break;
/* 1365:     */       default: 
/* 1366:1681 */         if ((c < ' ') || (shouldEncodeChar(c))) {
/* 1367:1682 */           entity = "&#" + c + ";";
/* 1368:     */         }
/* 1369:     */         break;
/* 1370:     */       }
/* 1371:1688 */       if (entity != null)
/* 1372:     */       {
/* 1373:1689 */         if (block == null) {
/* 1374:1690 */           block = text.toCharArray();
/* 1375:     */         }
/* 1376:1693 */         this.buffer.append(block, last, i - last);
/* 1377:1694 */         this.buffer.append(entity);
/* 1378:1695 */         last = i + 1;
/* 1379:     */       }
/* 1380:     */     }
/* 1381:1699 */     if (last == 0) {
/* 1382:1700 */       return text;
/* 1383:     */     }
/* 1384:1703 */     if (last < size)
/* 1385:     */     {
/* 1386:1704 */       if (block == null) {
/* 1387:1705 */         block = text.toCharArray();
/* 1388:     */       }
/* 1389:1708 */       this.buffer.append(block, last, i - last);
/* 1390:     */     }
/* 1391:1711 */     String answer = this.buffer.toString();
/* 1392:1712 */     this.buffer.setLength(0);
/* 1393:     */     
/* 1394:1714 */     return answer;
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   protected void writeEscapeAttributeEntities(String txt)
/* 1398:     */     throws IOException
/* 1399:     */   {
/* 1400:1718 */     if (txt != null)
/* 1401:     */     {
/* 1402:1719 */       String escapedText = escapeAttributeEntities(txt);
/* 1403:1720 */       this.writer.write(escapedText);
/* 1404:     */     }
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   protected String escapeAttributeEntities(String text)
/* 1408:     */   {
/* 1409:1735 */     char quote = this.format.getAttributeQuoteCharacter();
/* 1410:     */     
/* 1411:1737 */     char[] block = null;
/* 1412:     */     
/* 1413:1739 */     int last = 0;
/* 1414:1740 */     int size = text.length();
/* 1415:1742 */     for (int i = 0; i < size; i++)
/* 1416:     */     {
/* 1417:1743 */       String entity = null;
/* 1418:1744 */       char c = text.charAt(i);
/* 1419:1746 */       switch (c)
/* 1420:     */       {
/* 1421:     */       case '<': 
/* 1422:1748 */         entity = "&lt;";
/* 1423:     */         
/* 1424:1750 */         break;
/* 1425:     */       case '>': 
/* 1426:1753 */         entity = "&gt;";
/* 1427:     */         
/* 1428:1755 */         break;
/* 1429:     */       case '\'': 
/* 1430:1759 */         if (quote == '\'') {
/* 1431:1760 */           entity = "&apos;";
/* 1432:     */         }
/* 1433:     */         break;
/* 1434:     */       case '"': 
/* 1435:1767 */         if (quote == '"') {
/* 1436:1768 */           entity = "&quot;";
/* 1437:     */         }
/* 1438:     */         break;
/* 1439:     */       case '&': 
/* 1440:1774 */         entity = "&amp;";
/* 1441:     */         
/* 1442:1776 */         break;
/* 1443:     */       case '\t': 
/* 1444:     */       case '\n': 
/* 1445:     */       case '\r': 
/* 1446:     */         break;
/* 1447:     */       default: 
/* 1448:1787 */         if ((c < ' ') || (shouldEncodeChar(c))) {
/* 1449:1788 */           entity = "&#" + c + ";";
/* 1450:     */         }
/* 1451:     */         break;
/* 1452:     */       }
/* 1453:1794 */       if (entity != null)
/* 1454:     */       {
/* 1455:1795 */         if (block == null) {
/* 1456:1796 */           block = text.toCharArray();
/* 1457:     */         }
/* 1458:1799 */         this.buffer.append(block, last, i - last);
/* 1459:1800 */         this.buffer.append(entity);
/* 1460:1801 */         last = i + 1;
/* 1461:     */       }
/* 1462:     */     }
/* 1463:1805 */     if (last == 0) {
/* 1464:1806 */       return text;
/* 1465:     */     }
/* 1466:1809 */     if (last < size)
/* 1467:     */     {
/* 1468:1810 */       if (block == null) {
/* 1469:1811 */         block = text.toCharArray();
/* 1470:     */       }
/* 1471:1814 */       this.buffer.append(block, last, i - last);
/* 1472:     */     }
/* 1473:1817 */     String answer = this.buffer.toString();
/* 1474:1818 */     this.buffer.setLength(0);
/* 1475:     */     
/* 1476:1820 */     return answer;
/* 1477:     */   }
/* 1478:     */   
/* 1479:     */   protected boolean shouldEncodeChar(char c)
/* 1480:     */   {
/* 1481:1833 */     int max = getMaximumAllowedCharacter();
/* 1482:     */     
/* 1483:1835 */     return (max > 0) && (c > max);
/* 1484:     */   }
/* 1485:     */   
/* 1486:     */   protected int defaultMaximumAllowedCharacter()
/* 1487:     */   {
/* 1488:1846 */     String encoding = this.format.getEncoding();
/* 1489:1848 */     if ((encoding != null) && 
/* 1490:1849 */       (encoding.equals("US-ASCII"))) {
/* 1491:1850 */       return 127;
/* 1492:     */     }
/* 1493:1855 */     return -1;
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   protected boolean isNamespaceDeclaration(Namespace ns)
/* 1497:     */   {
/* 1498:1859 */     if ((ns != null) && (ns != Namespace.XML_NAMESPACE))
/* 1499:     */     {
/* 1500:1860 */       String uri = ns.getURI();
/* 1501:1862 */       if ((uri != null) && 
/* 1502:1863 */         (!this.namespaceStack.contains(ns))) {
/* 1503:1864 */         return true;
/* 1504:     */       }
/* 1505:     */     }
/* 1506:1869 */     return false;
/* 1507:     */   }
/* 1508:     */   
/* 1509:     */   protected void handleException(IOException e)
/* 1510:     */     throws SAXException
/* 1511:     */   {
/* 1512:1873 */     throw new SAXException(e);
/* 1513:     */   }
/* 1514:     */   
/* 1515:     */   protected OutputFormat getOutputFormat()
/* 1516:     */   {
/* 1517:1887 */     return this.format;
/* 1518:     */   }
/* 1519:     */   
/* 1520:     */   public boolean resolveEntityRefs()
/* 1521:     */   {
/* 1522:1891 */     return this.resolveEntityRefs;
/* 1523:     */   }
/* 1524:     */   
/* 1525:     */   public void setResolveEntityRefs(boolean resolve)
/* 1526:     */   {
/* 1527:1895 */     this.resolveEntityRefs = resolve;
/* 1528:     */   }
/* 1529:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.XMLWriter
 * JD-Core Version:    0.7.0.1
 */