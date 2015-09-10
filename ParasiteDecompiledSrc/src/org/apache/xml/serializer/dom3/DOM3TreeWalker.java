/*    1:     */ package org.apache.xml.serializer.dom3;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.Writer;
/*    6:     */ import java.util.Enumeration;
/*    7:     */ import java.util.Hashtable;
/*    8:     */ import java.util.Properties;
/*    9:     */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   10:     */ import org.apache.xml.serializer.SerializationHandler;
/*   11:     */ import org.apache.xml.serializer.Serializer;
/*   12:     */ import org.apache.xml.serializer.utils.Messages;
/*   13:     */ import org.apache.xml.serializer.utils.Utils;
/*   14:     */ import org.apache.xml.serializer.utils.XML11Char;
/*   15:     */ import org.apache.xml.serializer.utils.XMLChar;
/*   16:     */ import org.w3c.dom.Attr;
/*   17:     */ import org.w3c.dom.CDATASection;
/*   18:     */ import org.w3c.dom.CharacterData;
/*   19:     */ import org.w3c.dom.Comment;
/*   20:     */ import org.w3c.dom.DOMErrorHandler;
/*   21:     */ import org.w3c.dom.DOMImplementation;
/*   22:     */ import org.w3c.dom.Document;
/*   23:     */ import org.w3c.dom.DocumentType;
/*   24:     */ import org.w3c.dom.Element;
/*   25:     */ import org.w3c.dom.Entity;
/*   26:     */ import org.w3c.dom.EntityReference;
/*   27:     */ import org.w3c.dom.NamedNodeMap;
/*   28:     */ import org.w3c.dom.Node;
/*   29:     */ import org.w3c.dom.NodeList;
/*   30:     */ import org.w3c.dom.ProcessingInstruction;
/*   31:     */ import org.w3c.dom.Text;
/*   32:     */ import org.w3c.dom.TypeInfo;
/*   33:     */ import org.w3c.dom.ls.LSSerializerFilter;
/*   34:     */ import org.w3c.dom.traversal.NodeFilter;
/*   35:     */ import org.xml.sax.ContentHandler;
/*   36:     */ import org.xml.sax.Locator;
/*   37:     */ import org.xml.sax.SAXException;
/*   38:     */ import org.xml.sax.ext.LexicalHandler;
/*   39:     */ import org.xml.sax.helpers.LocatorImpl;
/*   40:     */ 
/*   41:     */ final class DOM3TreeWalker
/*   42:     */ {
/*   43:  75 */   private SerializationHandler fSerializer = null;
/*   44:  80 */   private LocatorImpl fLocator = new LocatorImpl();
/*   45:  83 */   private DOMErrorHandler fErrorHandler = null;
/*   46:  86 */   private LSSerializerFilter fFilter = null;
/*   47:  89 */   private LexicalHandler fLexicalHandler = null;
/*   48:     */   private int fWhatToShowFilter;
/*   49:  94 */   private String fNewLine = null;
/*   50:  97 */   private Properties fDOMConfigProperties = null;
/*   51: 100 */   private boolean fInEntityRef = false;
/*   52: 103 */   private String fXMLVersion = null;
/*   53: 106 */   private boolean fIsXMLVersion11 = false;
/*   54: 109 */   private boolean fIsLevel3DOM = false;
/*   55: 112 */   private int fFeatures = 0;
/*   56: 115 */   boolean fNextIsRaw = false;
/*   57:     */   private static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
/*   58:     */   private static final String XMLNS_PREFIX = "xmlns";
/*   59:     */   private static final String XML_URI = "http://www.w3.org/XML/1998/namespace";
/*   60:     */   private static final String XML_PREFIX = "xml";
/*   61:     */   protected NamespaceSupport fNSBinder;
/*   62:     */   protected NamespaceSupport fLocalNSBinder;
/*   63: 136 */   private int fElementDepth = 0;
/*   64:     */   private static final int CANONICAL = 1;
/*   65:     */   private static final int CDATA = 2;
/*   66:     */   private static final int CHARNORMALIZE = 4;
/*   67:     */   private static final int COMMENTS = 8;
/*   68:     */   private static final int DTNORMALIZE = 16;
/*   69:     */   private static final int ELEM_CONTENT_WHITESPACE = 32;
/*   70:     */   private static final int ENTITIES = 64;
/*   71:     */   private static final int INFOSET = 128;
/*   72:     */   private static final int NAMESPACES = 256;
/*   73:     */   private static final int NAMESPACEDECLS = 512;
/*   74:     */   private static final int NORMALIZECHARS = 1024;
/*   75:     */   private static final int SPLITCDATA = 2048;
/*   76:     */   private static final int VALIDATE = 4096;
/*   77:     */   private static final int SCHEMAVALIDATE = 8192;
/*   78:     */   private static final int WELLFORMED = 16384;
/*   79:     */   private static final int DISCARDDEFAULT = 32768;
/*   80:     */   private static final int PRETTY_PRINT = 65536;
/*   81:     */   private static final int IGNORE_CHAR_DENORMALIZE = 131072;
/*   82:     */   private static final int XMLDECL = 262144;
/*   83:     */   
/*   84:     */   DOM3TreeWalker(SerializationHandler serialHandler, DOMErrorHandler errHandler, LSSerializerFilter filter, String newLine)
/*   85:     */   {
/*   86: 209 */     this.fSerializer = serialHandler;
/*   87:     */     
/*   88: 211 */     this.fErrorHandler = errHandler;
/*   89: 212 */     this.fFilter = filter;
/*   90: 213 */     this.fLexicalHandler = null;
/*   91: 214 */     this.fNewLine = newLine;
/*   92:     */     
/*   93: 216 */     this.fNSBinder = new NamespaceSupport();
/*   94: 217 */     this.fLocalNSBinder = new NamespaceSupport();
/*   95:     */     
/*   96: 219 */     this.fDOMConfigProperties = this.fSerializer.getOutputFormat();
/*   97: 220 */     this.fSerializer.setDocumentLocator(this.fLocator);
/*   98: 221 */     initProperties(this.fDOMConfigProperties);
/*   99:     */     try
/*  100:     */     {
/*  101: 225 */       this.fLocator.setSystemId(System.getProperty("user.dir") + File.separator + "dummy.xsl");
/*  102:     */     }
/*  103:     */     catch (SecurityException se) {}
/*  104:     */   }
/*  105:     */   
/*  106:     */   public void traverse(Node pos)
/*  107:     */     throws SAXException
/*  108:     */   {
/*  109: 245 */     this.fSerializer.startDocument();
/*  110: 248 */     if (pos.getNodeType() != 9)
/*  111:     */     {
/*  112: 249 */       Document ownerDoc = pos.getOwnerDocument();
/*  113: 250 */       if ((ownerDoc != null) && (ownerDoc.getImplementation().hasFeature("Core", "3.0"))) {
/*  114: 252 */         this.fIsLevel3DOM = true;
/*  115:     */       }
/*  116:     */     }
/*  117: 255 */     else if (((Document)pos).getImplementation().hasFeature("Core", "3.0"))
/*  118:     */     {
/*  119: 258 */       this.fIsLevel3DOM = true;
/*  120:     */     }
/*  121: 262 */     if ((this.fSerializer instanceof LexicalHandler)) {
/*  122: 263 */       this.fLexicalHandler = this.fSerializer;
/*  123:     */     }
/*  124: 266 */     if (this.fFilter != null) {
/*  125: 267 */       this.fWhatToShowFilter = this.fFilter.getWhatToShow();
/*  126:     */     }
/*  127: 269 */     Node top = pos;
/*  128: 271 */     while (null != pos)
/*  129:     */     {
/*  130: 272 */       startNode(pos);
/*  131:     */       
/*  132: 274 */       Node nextNode = null;
/*  133:     */       
/*  134: 276 */       nextNode = pos.getFirstChild();
/*  135: 278 */       while (null == nextNode)
/*  136:     */       {
/*  137: 279 */         endNode(pos);
/*  138: 281 */         if (top.equals(pos)) {
/*  139:     */           break;
/*  140:     */         }
/*  141: 284 */         nextNode = pos.getNextSibling();
/*  142: 286 */         if (null == nextNode)
/*  143:     */         {
/*  144: 287 */           pos = pos.getParentNode();
/*  145: 289 */           if ((null == pos) || (top.equals(pos)))
/*  146:     */           {
/*  147: 290 */             if (null != pos) {
/*  148: 291 */               endNode(pos);
/*  149:     */             }
/*  150: 293 */             nextNode = null;
/*  151:     */             
/*  152: 295 */             break;
/*  153:     */           }
/*  154:     */         }
/*  155:     */       }
/*  156: 300 */       pos = nextNode;
/*  157:     */     }
/*  158: 302 */     this.fSerializer.endDocument();
/*  159:     */   }
/*  160:     */   
/*  161:     */   public void traverse(Node pos, Node top)
/*  162:     */     throws SAXException
/*  163:     */   {
/*  164: 320 */     this.fSerializer.startDocument();
/*  165: 323 */     if (pos.getNodeType() != 9)
/*  166:     */     {
/*  167: 324 */       Document ownerDoc = pos.getOwnerDocument();
/*  168: 325 */       if ((ownerDoc != null) && (ownerDoc.getImplementation().hasFeature("Core", "3.0"))) {
/*  169: 327 */         this.fIsLevel3DOM = true;
/*  170:     */       }
/*  171:     */     }
/*  172: 330 */     else if (((Document)pos).getImplementation().hasFeature("Core", "3.0"))
/*  173:     */     {
/*  174: 333 */       this.fIsLevel3DOM = true;
/*  175:     */     }
/*  176: 337 */     if ((this.fSerializer instanceof LexicalHandler)) {
/*  177: 338 */       this.fLexicalHandler = this.fSerializer;
/*  178:     */     }
/*  179: 341 */     if (this.fFilter != null) {
/*  180: 342 */       this.fWhatToShowFilter = this.fFilter.getWhatToShow();
/*  181:     */     }
/*  182: 344 */     while (null != pos)
/*  183:     */     {
/*  184: 345 */       startNode(pos);
/*  185:     */       
/*  186: 347 */       Node nextNode = null;
/*  187:     */       
/*  188: 349 */       nextNode = pos.getFirstChild();
/*  189: 351 */       while (null == nextNode)
/*  190:     */       {
/*  191: 352 */         endNode(pos);
/*  192: 354 */         if ((null != top) && (top.equals(pos))) {
/*  193:     */           break;
/*  194:     */         }
/*  195: 357 */         nextNode = pos.getNextSibling();
/*  196: 359 */         if (null == nextNode)
/*  197:     */         {
/*  198: 360 */           pos = pos.getParentNode();
/*  199: 362 */           if ((null == pos) || ((null != top) && (top.equals(pos))))
/*  200:     */           {
/*  201: 363 */             nextNode = null;
/*  202:     */             
/*  203: 365 */             break;
/*  204:     */           }
/*  205:     */         }
/*  206:     */       }
/*  207: 370 */       pos = nextNode;
/*  208:     */     }
/*  209: 372 */     this.fSerializer.endDocument();
/*  210:     */   }
/*  211:     */   
/*  212:     */   private final void dispatachChars(Node node)
/*  213:     */     throws SAXException
/*  214:     */   {
/*  215: 380 */     if (this.fSerializer != null)
/*  216:     */     {
/*  217: 381 */       this.fSerializer.characters(node);
/*  218:     */     }
/*  219:     */     else
/*  220:     */     {
/*  221: 383 */       String data = ((Text)node).getData();
/*  222: 384 */       this.fSerializer.characters(data.toCharArray(), 0, data.length());
/*  223:     */     }
/*  224:     */   }
/*  225:     */   
/*  226:     */   protected void startNode(Node node)
/*  227:     */     throws SAXException
/*  228:     */   {
/*  229: 396 */     if ((node instanceof Locator))
/*  230:     */     {
/*  231: 397 */       Locator loc = (Locator)node;
/*  232: 398 */       this.fLocator.setColumnNumber(loc.getColumnNumber());
/*  233: 399 */       this.fLocator.setLineNumber(loc.getLineNumber());
/*  234: 400 */       this.fLocator.setPublicId(loc.getPublicId());
/*  235: 401 */       this.fLocator.setSystemId(loc.getSystemId());
/*  236:     */     }
/*  237:     */     else
/*  238:     */     {
/*  239: 403 */       this.fLocator.setColumnNumber(0);
/*  240: 404 */       this.fLocator.setLineNumber(0);
/*  241:     */     }
/*  242: 407 */     switch (node.getNodeType())
/*  243:     */     {
/*  244:     */     case 10: 
/*  245: 409 */       serializeDocType((DocumentType)node, true);
/*  246: 410 */       break;
/*  247:     */     case 8: 
/*  248: 412 */       serializeComment((Comment)node);
/*  249: 413 */       break;
/*  250:     */     case 11: 
/*  251:     */       break;
/*  252:     */     case 9: 
/*  253:     */       break;
/*  254:     */     case 1: 
/*  255: 420 */       serializeElement((Element)node, true);
/*  256: 421 */       break;
/*  257:     */     case 7: 
/*  258: 423 */       serializePI((ProcessingInstruction)node);
/*  259: 424 */       break;
/*  260:     */     case 4: 
/*  261: 426 */       serializeCDATASection((CDATASection)node);
/*  262: 427 */       break;
/*  263:     */     case 3: 
/*  264: 429 */       serializeText((Text)node);
/*  265: 430 */       break;
/*  266:     */     case 5: 
/*  267: 432 */       serializeEntityReference((EntityReference)node, true);
/*  268: 433 */       break;
/*  269:     */     }
/*  270:     */   }
/*  271:     */   
/*  272:     */   protected void endNode(Node node)
/*  273:     */     throws SAXException
/*  274:     */   {
/*  275: 448 */     switch (node.getNodeType())
/*  276:     */     {
/*  277:     */     case 9: 
/*  278:     */       break;
/*  279:     */     case 10: 
/*  280: 452 */       serializeDocType((DocumentType)node, false);
/*  281: 453 */       break;
/*  282:     */     case 1: 
/*  283: 455 */       serializeElement((Element)node, false);
/*  284: 456 */       break;
/*  285:     */     case 4: 
/*  286:     */       break;
/*  287:     */     case 5: 
/*  288: 460 */       serializeEntityReference((EntityReference)node, false);
/*  289: 461 */       break;
/*  290:     */     }
/*  291:     */   }
/*  292:     */   
/*  293:     */   protected boolean applyFilter(Node node, int nodeType)
/*  294:     */   {
/*  295: 477 */     if ((this.fFilter != null) && ((this.fWhatToShowFilter & nodeType) != 0))
/*  296:     */     {
/*  297: 479 */       short code = this.fFilter.acceptNode(node);
/*  298: 480 */       switch (code)
/*  299:     */       {
/*  300:     */       case 2: 
/*  301:     */       case 3: 
/*  302: 483 */         return false;
/*  303:     */       }
/*  304:     */     }
/*  305: 487 */     return true;
/*  306:     */   }
/*  307:     */   
/*  308:     */   protected void serializeDocType(DocumentType node, boolean bStart)
/*  309:     */     throws SAXException
/*  310:     */   {
/*  311: 500 */     String docTypeName = node.getNodeName();
/*  312: 501 */     String publicId = node.getPublicId();
/*  313: 502 */     String systemId = node.getSystemId();
/*  314: 503 */     String internalSubset = node.getInternalSubset();
/*  315: 507 */     if ((internalSubset != null) && (!"".equals(internalSubset)))
/*  316:     */     {
/*  317: 509 */       if (bStart) {
/*  318:     */         try
/*  319:     */         {
/*  320: 514 */           Writer writer = this.fSerializer.getWriter();
/*  321: 515 */           StringBuffer dtd = new StringBuffer();
/*  322:     */           
/*  323: 517 */           dtd.append("<!DOCTYPE ");
/*  324: 518 */           dtd.append(docTypeName);
/*  325: 519 */           if (null != publicId)
/*  326:     */           {
/*  327: 520 */             dtd.append(" PUBLIC \"");
/*  328: 521 */             dtd.append(publicId);
/*  329: 522 */             dtd.append('"');
/*  330:     */           }
/*  331: 525 */           if (null != systemId)
/*  332:     */           {
/*  333: 526 */             if (null == publicId) {
/*  334: 527 */               dtd.append(" SYSTEM \"");
/*  335:     */             } else {
/*  336: 529 */               dtd.append(" \"");
/*  337:     */             }
/*  338: 531 */             dtd.append(systemId);
/*  339: 532 */             dtd.append('"');
/*  340:     */           }
/*  341: 535 */           dtd.append(" [ ");
/*  342:     */           
/*  343: 537 */           dtd.append(this.fNewLine);
/*  344: 538 */           dtd.append(internalSubset);
/*  345: 539 */           dtd.append("]>");
/*  346: 540 */           dtd.append(new String(this.fNewLine));
/*  347:     */           
/*  348: 542 */           writer.write(dtd.toString());
/*  349: 543 */           writer.flush();
/*  350:     */         }
/*  351:     */         catch (IOException e)
/*  352:     */         {
/*  353: 546 */           throw new SAXException(Utils.messages.createMessage("ER_WRITING_INTERNAL_SUBSET", null), e);
/*  354:     */         }
/*  355:     */       }
/*  356:     */     }
/*  357: 553 */     else if (bStart)
/*  358:     */     {
/*  359: 554 */       if (this.fLexicalHandler != null) {
/*  360: 555 */         this.fLexicalHandler.startDTD(docTypeName, publicId, systemId);
/*  361:     */       }
/*  362:     */     }
/*  363: 558 */     else if (this.fLexicalHandler != null) {
/*  364: 559 */       this.fLexicalHandler.endDTD();
/*  365:     */     }
/*  366:     */   }
/*  367:     */   
/*  368:     */   protected void serializeComment(Comment node)
/*  369:     */     throws SAXException
/*  370:     */   {
/*  371: 572 */     if ((this.fFeatures & 0x8) != 0)
/*  372:     */     {
/*  373: 573 */       String data = node.getData();
/*  374: 576 */       if ((this.fFeatures & 0x4000) != 0) {
/*  375: 577 */         isCommentWellFormed(data);
/*  376:     */       }
/*  377: 580 */       if (this.fLexicalHandler != null)
/*  378:     */       {
/*  379: 583 */         if (!applyFilter(node, 128)) {
/*  380: 584 */           return;
/*  381:     */         }
/*  382: 587 */         this.fLexicalHandler.comment(data.toCharArray(), 0, data.length());
/*  383:     */       }
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   protected void serializeElement(Element node, boolean bStart)
/*  388:     */     throws SAXException
/*  389:     */   {
/*  390: 600 */     if (bStart)
/*  391:     */     {
/*  392: 601 */       this.fElementDepth += 1;
/*  393: 609 */       if ((this.fFeatures & 0x4000) != 0) {
/*  394: 610 */         isElementWellFormed(node);
/*  395:     */       }
/*  396: 615 */       if (!applyFilter(node, 1)) {
/*  397: 616 */         return;
/*  398:     */       }
/*  399: 620 */       if ((this.fFeatures & 0x100) != 0)
/*  400:     */       {
/*  401: 621 */         this.fNSBinder.pushContext();
/*  402: 622 */         this.fLocalNSBinder.reset();
/*  403:     */         
/*  404: 624 */         recordLocalNSDecl(node);
/*  405: 625 */         fixupElementNS(node);
/*  406:     */       }
/*  407: 629 */       this.fSerializer.startElement(node.getNamespaceURI(), node.getLocalName(), node.getNodeName());
/*  408:     */       
/*  409:     */ 
/*  410:     */ 
/*  411:     */ 
/*  412: 634 */       serializeAttList(node);
/*  413:     */     }
/*  414:     */     else
/*  415:     */     {
/*  416: 637 */       this.fElementDepth -= 1;
/*  417: 640 */       if (!applyFilter(node, 1)) {
/*  418: 641 */         return;
/*  419:     */       }
/*  420: 644 */       this.fSerializer.endElement(node.getNamespaceURI(), node.getLocalName(), node.getNodeName());
/*  421: 651 */       if ((this.fFeatures & 0x100) != 0) {
/*  422: 652 */         this.fNSBinder.popContext();
/*  423:     */       }
/*  424:     */     }
/*  425:     */   }
/*  426:     */   
/*  427:     */   protected void serializeAttList(Element node)
/*  428:     */     throws SAXException
/*  429:     */   {
/*  430: 664 */     NamedNodeMap atts = node.getAttributes();
/*  431: 665 */     int nAttrs = atts.getLength();
/*  432: 667 */     for (int i = 0; i < nAttrs; i++)
/*  433:     */     {
/*  434: 668 */       Node attr = atts.item(i);
/*  435:     */       
/*  436: 670 */       String localName = attr.getLocalName();
/*  437: 671 */       String attrName = attr.getNodeName();
/*  438: 672 */       String attrPrefix = attr.getPrefix() == null ? "" : attr.getPrefix();
/*  439: 673 */       String attrValue = attr.getNodeValue();
/*  440:     */       
/*  441:     */ 
/*  442: 676 */       String type = null;
/*  443: 677 */       if (this.fIsLevel3DOM) {
/*  444: 678 */         type = ((Attr)attr).getSchemaTypeInfo().getTypeName();
/*  445:     */       }
/*  446: 680 */       type = type == null ? "CDATA" : type;
/*  447:     */       
/*  448: 682 */       String attrNS = attr.getNamespaceURI();
/*  449: 683 */       if ((attrNS != null) && (attrNS.length() == 0))
/*  450:     */       {
/*  451: 684 */         attrNS = null;
/*  452:     */         
/*  453: 686 */         attrName = attr.getLocalName();
/*  454:     */       }
/*  455: 689 */       boolean isSpecified = ((Attr)attr).getSpecified();
/*  456: 690 */       boolean addAttr = true;
/*  457: 691 */       boolean applyFilter = false;
/*  458: 692 */       boolean xmlnsAttr = (attrName.equals("xmlns")) || (attrName.startsWith("xmlns:"));
/*  459: 696 */       if ((this.fFeatures & 0x4000) != 0) {
/*  460: 697 */         isAttributeWellFormed(attr);
/*  461:     */       }
/*  462: 705 */       if (((this.fFeatures & 0x100) != 0) && (!xmlnsAttr)) {
/*  463: 708 */         if (attrNS != null)
/*  464:     */         {
/*  465: 709 */           attrPrefix = attrPrefix == null ? "" : attrPrefix;
/*  466:     */           
/*  467: 711 */           String declAttrPrefix = this.fNSBinder.getPrefix(attrNS);
/*  468: 712 */           String declAttrNS = this.fNSBinder.getURI(attrPrefix);
/*  469: 720 */           if (("".equals(attrPrefix)) || ("".equals(declAttrPrefix)) || (!attrPrefix.equals(declAttrPrefix))) {
/*  470: 725 */             if ((declAttrPrefix != null) && (!"".equals(declAttrPrefix)))
/*  471:     */             {
/*  472: 728 */               attrPrefix = declAttrPrefix;
/*  473: 730 */               if (declAttrPrefix.length() > 0) {
/*  474: 731 */                 attrName = declAttrPrefix + ":" + localName;
/*  475:     */               } else {
/*  476: 733 */                 attrName = localName;
/*  477:     */               }
/*  478:     */             }
/*  479: 738 */             else if ((attrPrefix != null) && (!"".equals(attrPrefix)) && (declAttrNS == null))
/*  480:     */             {
/*  481: 741 */               if ((this.fFeatures & 0x200) != 0)
/*  482:     */               {
/*  483: 742 */                 this.fSerializer.addAttribute("http://www.w3.org/2000/xmlns/", attrPrefix, "xmlns:" + attrPrefix, "CDATA", attrNS);
/*  484:     */                 
/*  485:     */ 
/*  486: 745 */                 this.fNSBinder.declarePrefix(attrPrefix, attrNS);
/*  487: 746 */                 this.fLocalNSBinder.declarePrefix(attrPrefix, attrNS);
/*  488:     */               }
/*  489:     */             }
/*  490:     */             else
/*  491:     */             {
/*  492: 753 */               int counter = 1;
/*  493: 754 */               attrPrefix = "NS" + counter++;
/*  494: 756 */               while (this.fLocalNSBinder.getURI(attrPrefix) != null) {
/*  495: 757 */                 attrPrefix = "NS" + counter++;
/*  496:     */               }
/*  497: 760 */               attrName = attrPrefix + ":" + localName;
/*  498: 764 */               if ((this.fFeatures & 0x200) != 0)
/*  499:     */               {
/*  500: 766 */                 this.fSerializer.addAttribute("http://www.w3.org/2000/xmlns/", attrPrefix, "xmlns:" + attrPrefix, "CDATA", attrNS);
/*  501:     */                 
/*  502:     */ 
/*  503: 769 */                 this.fNSBinder.declarePrefix(attrPrefix, attrNS);
/*  504: 770 */                 this.fLocalNSBinder.declarePrefix(attrPrefix, attrNS);
/*  505:     */               }
/*  506:     */             }
/*  507:     */           }
/*  508:     */         }
/*  509: 778 */         else if (localName == null)
/*  510:     */         {
/*  511: 780 */           String msg = Utils.messages.createMessage("ER_NULL_LOCAL_ELEMENT_NAME", new Object[] { attrName });
/*  512: 784 */           if (this.fErrorHandler != null) {
/*  513: 785 */             this.fErrorHandler.handleError(new DOMErrorImpl((short)2, msg, "ER_NULL_LOCAL_ELEMENT_NAME", null, null, null));
/*  514:     */           }
/*  515:     */         }
/*  516:     */       }
/*  517: 805 */       if ((((this.fFeatures & 0x8000) != 0) && (isSpecified)) || ((this.fFeatures & 0x8000) == 0)) {
/*  518: 807 */         applyFilter = true;
/*  519:     */       } else {
/*  520: 809 */         addAttr = false;
/*  521:     */       }
/*  522: 812 */       if (applyFilter) {
/*  523: 815 */         if ((this.fFilter != null) && ((this.fFilter.getWhatToShow() & 0x2) != 0)) {
/*  524: 819 */           if (!xmlnsAttr)
/*  525:     */           {
/*  526: 820 */             short code = this.fFilter.acceptNode(attr);
/*  527: 821 */             switch (code)
/*  528:     */             {
/*  529:     */             case 2: 
/*  530:     */             case 3: 
/*  531: 824 */               addAttr = false;
/*  532: 825 */               break;
/*  533:     */             }
/*  534:     */           }
/*  535:     */         }
/*  536:     */       }
/*  537: 833 */       if ((addAttr) && (xmlnsAttr))
/*  538:     */       {
/*  539: 835 */         if ((this.fFeatures & 0x200) != 0) {
/*  540: 837 */           if ((localName != null) && (!"".equals(localName))) {
/*  541: 838 */             this.fSerializer.addAttribute(attrNS, localName, attrName, type, attrValue);
/*  542:     */           }
/*  543:     */         }
/*  544:     */       }
/*  545: 841 */       else if ((addAttr) && (!xmlnsAttr)) {
/*  546: 846 */         if (((this.fFeatures & 0x200) != 0) && (attrNS != null)) {
/*  547: 847 */           this.fSerializer.addAttribute(attrNS, localName, attrName, type, attrValue);
/*  548:     */         } else {
/*  549: 854 */           this.fSerializer.addAttribute("", localName, attrName, type, attrValue);
/*  550:     */         }
/*  551:     */       }
/*  552: 864 */       if ((xmlnsAttr) && ((this.fFeatures & 0x200) != 0))
/*  553:     */       {
/*  554:     */         int index;
/*  555: 869 */         String prefix = (index = attrName.indexOf(":")) < 0 ? "" : attrName.substring(index + 1);
/*  556: 874 */         if (!"".equals(prefix)) {
/*  557: 875 */           this.fSerializer.namespaceAfterStartElement(prefix, attrValue);
/*  558:     */         }
/*  559:     */       }
/*  560:     */     }
/*  561:     */   }
/*  562:     */   
/*  563:     */   protected void serializePI(ProcessingInstruction node)
/*  564:     */     throws SAXException
/*  565:     */   {
/*  566: 889 */     ProcessingInstruction pi = node;
/*  567: 890 */     String name = pi.getNodeName();
/*  568: 893 */     if ((this.fFeatures & 0x4000) != 0) {
/*  569: 894 */       isPIWellFormed(node);
/*  570:     */     }
/*  571: 898 */     if (!applyFilter(node, 64)) {
/*  572: 899 */       return;
/*  573:     */     }
/*  574: 903 */     if (name.equals("xslt-next-is-raw")) {
/*  575: 904 */       this.fNextIsRaw = true;
/*  576:     */     } else {
/*  577: 906 */       this.fSerializer.processingInstruction(name, pi.getData());
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   protected void serializeCDATASection(CDATASection node)
/*  582:     */     throws SAXException
/*  583:     */   {
/*  584: 918 */     if ((this.fFeatures & 0x4000) != 0) {
/*  585: 919 */       isCDATASectionWellFormed(node);
/*  586:     */     }
/*  587: 923 */     if ((this.fFeatures & 0x2) != 0)
/*  588:     */     {
/*  589: 930 */       String nodeValue = node.getNodeValue();
/*  590: 931 */       int endIndex = nodeValue.indexOf("]]>");
/*  591: 932 */       if ((this.fFeatures & 0x800) != 0)
/*  592:     */       {
/*  593: 933 */         if (endIndex >= 0)
/*  594:     */         {
/*  595: 935 */           String relatedData = nodeValue.substring(0, endIndex + 2);
/*  596:     */           
/*  597: 937 */           String msg = Utils.messages.createMessage("cdata-sections-splitted", null);
/*  598: 942 */           if (this.fErrorHandler != null) {
/*  599: 943 */             this.fErrorHandler.handleError(new DOMErrorImpl((short)1, msg, "cdata-sections-splitted", null, relatedData, null));
/*  600:     */           }
/*  601:     */         }
/*  602:     */       }
/*  603: 954 */       else if (endIndex >= 0)
/*  604:     */       {
/*  605: 956 */         String relatedData = nodeValue.substring(0, endIndex + 2);
/*  606:     */         
/*  607: 958 */         String msg = Utils.messages.createMessage("cdata-sections-splitted", null);
/*  608: 963 */         if (this.fErrorHandler != null) {
/*  609: 964 */           this.fErrorHandler.handleError(new DOMErrorImpl((short)2, msg, "cdata-sections-splitted"));
/*  610:     */         }
/*  611: 971 */         return;
/*  612:     */       }
/*  613: 976 */       if (!applyFilter(node, 8)) {
/*  614: 977 */         return;
/*  615:     */       }
/*  616: 981 */       if (this.fLexicalHandler != null) {
/*  617: 982 */         this.fLexicalHandler.startCDATA();
/*  618:     */       }
/*  619: 984 */       dispatachChars(node);
/*  620: 985 */       if (this.fLexicalHandler != null) {
/*  621: 986 */         this.fLexicalHandler.endCDATA();
/*  622:     */       }
/*  623:     */     }
/*  624:     */     else
/*  625:     */     {
/*  626: 989 */       dispatachChars(node);
/*  627:     */     }
/*  628:     */   }
/*  629:     */   
/*  630:     */   protected void serializeText(Text node)
/*  631:     */     throws SAXException
/*  632:     */   {
/*  633: 999 */     if (this.fNextIsRaw)
/*  634:     */     {
/*  635:1000 */       this.fNextIsRaw = false;
/*  636:1001 */       this.fSerializer.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/*  637:     */       
/*  638:     */ 
/*  639:1004 */       dispatachChars(node);
/*  640:1005 */       this.fSerializer.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/*  641:     */     }
/*  642:     */     else
/*  643:     */     {
/*  644:1010 */       boolean bDispatch = false;
/*  645:1013 */       if ((this.fFeatures & 0x4000) != 0) {
/*  646:1014 */         isTextWellFormed(node);
/*  647:     */       }
/*  648:1019 */       boolean isElementContentWhitespace = false;
/*  649:1020 */       if (this.fIsLevel3DOM) {
/*  650:1021 */         isElementContentWhitespace = node.isElementContentWhitespace();
/*  651:     */       }
/*  652:1025 */       if (isElementContentWhitespace)
/*  653:     */       {
/*  654:1027 */         if ((this.fFeatures & 0x20) != 0) {
/*  655:1028 */           bDispatch = true;
/*  656:     */         }
/*  657:     */       }
/*  658:     */       else {
/*  659:1031 */         bDispatch = true;
/*  660:     */       }
/*  661:1035 */       if (!applyFilter(node, 4)) {
/*  662:1036 */         return;
/*  663:     */       }
/*  664:1039 */       if (bDispatch) {
/*  665:1040 */         dispatachChars(node);
/*  666:     */       }
/*  667:     */     }
/*  668:     */   }
/*  669:     */   
/*  670:     */   protected void serializeEntityReference(EntityReference node, boolean bStart)
/*  671:     */     throws SAXException
/*  672:     */   {
/*  673:1055 */     if (bStart)
/*  674:     */     {
/*  675:1056 */       EntityReference eref = node;
/*  676:1058 */       if ((this.fFeatures & 0x40) != 0)
/*  677:     */       {
/*  678:1064 */         if ((this.fFeatures & 0x4000) != 0) {
/*  679:1065 */           isEntityReferneceWellFormed(node);
/*  680:     */         }
/*  681:1070 */         if ((this.fFeatures & 0x100) != 0) {
/*  682:1071 */           checkUnboundPrefixInEntRef(node);
/*  683:     */         }
/*  684:     */       }
/*  685:1079 */       if (this.fLexicalHandler != null) {
/*  686:1085 */         this.fLexicalHandler.startEntity(eref.getNodeName());
/*  687:     */       }
/*  688:     */     }
/*  689:     */     else
/*  690:     */     {
/*  691:1089 */       EntityReference eref = node;
/*  692:1091 */       if (this.fLexicalHandler != null) {
/*  693:1092 */         this.fLexicalHandler.endEntity(eref.getNodeName());
/*  694:     */       }
/*  695:     */     }
/*  696:     */   }
/*  697:     */   
/*  698:     */   protected boolean isXMLName(String s, boolean xml11Version)
/*  699:     */   {
/*  700:1110 */     if (s == null) {
/*  701:1111 */       return false;
/*  702:     */     }
/*  703:1113 */     if (!xml11Version) {
/*  704:1114 */       return XMLChar.isValidName(s);
/*  705:     */     }
/*  706:1116 */     return XML11Char.isXML11ValidName(s);
/*  707:     */   }
/*  708:     */   
/*  709:     */   protected boolean isValidQName(String prefix, String local, boolean xml11Version)
/*  710:     */   {
/*  711:1134 */     if (local == null) {
/*  712:1135 */       return false;
/*  713:     */     }
/*  714:1136 */     boolean validNCName = false;
/*  715:1138 */     if (!xml11Version) {
/*  716:1139 */       validNCName = ((prefix == null) || (XMLChar.isValidNCName(prefix))) && (XMLChar.isValidNCName(local));
/*  717:     */     } else {
/*  718:1143 */       validNCName = ((prefix == null) || (XML11Char.isXML11ValidNCName(prefix))) && (XML11Char.isXML11ValidNCName(local));
/*  719:     */     }
/*  720:1148 */     return validNCName;
/*  721:     */   }
/*  722:     */   
/*  723:     */   protected boolean isWFXMLChar(String chardata, Character refInvalidChar)
/*  724:     */   {
/*  725:1158 */     if ((chardata == null) || (chardata.length() == 0)) {
/*  726:1159 */       return true;
/*  727:     */     }
/*  728:1162 */     char[] dataarray = chardata.toCharArray();
/*  729:1163 */     int datalength = dataarray.length;
/*  730:1166 */     if (this.fIsXMLVersion11)
/*  731:     */     {
/*  732:1168 */       int i = 0;
/*  733:1169 */       while (i < datalength) {
/*  734:1170 */         if (XML11Char.isXML11Invalid(dataarray[(i++)]))
/*  735:     */         {
/*  736:1172 */           char ch = dataarray[(i - 1)];
/*  737:1173 */           if ((XMLChar.isHighSurrogate(ch)) && (i < datalength))
/*  738:     */           {
/*  739:1174 */             char ch2 = dataarray[(i++)];
/*  740:1175 */             if ((XMLChar.isLowSurrogate(ch2)) && (XMLChar.isSupplemental(XMLChar.supplemental(ch, ch2)))) {}
/*  741:     */           }
/*  742:     */           else
/*  743:     */           {
/*  744:1182 */             refInvalidChar = new Character(ch);
/*  745:1183 */             return false;
/*  746:     */           }
/*  747:     */         }
/*  748:     */       }
/*  749:     */     }
/*  750:     */     else
/*  751:     */     {
/*  752:1189 */       int i = 0;
/*  753:1190 */       while (i < datalength) {
/*  754:1191 */         if (XMLChar.isInvalid(dataarray[(i++)]))
/*  755:     */         {
/*  756:1193 */           char ch = dataarray[(i - 1)];
/*  757:1194 */           if ((XMLChar.isHighSurrogate(ch)) && (i < datalength))
/*  758:     */           {
/*  759:1195 */             char ch2 = dataarray[(i++)];
/*  760:1196 */             if ((XMLChar.isLowSurrogate(ch2)) && (XMLChar.isSupplemental(XMLChar.supplemental(ch, ch2)))) {}
/*  761:     */           }
/*  762:     */           else
/*  763:     */           {
/*  764:1203 */             refInvalidChar = new Character(ch);
/*  765:1204 */             return false;
/*  766:     */           }
/*  767:     */         }
/*  768:     */       }
/*  769:     */     }
/*  770:1209 */     return true;
/*  771:     */   }
/*  772:     */   
/*  773:     */   protected Character isWFXMLChar(String chardata)
/*  774:     */   {
/*  775:1221 */     if ((chardata == null) || (chardata.length() == 0)) {
/*  776:1222 */       return null;
/*  777:     */     }
/*  778:1225 */     char[] dataarray = chardata.toCharArray();
/*  779:1226 */     int datalength = dataarray.length;
/*  780:     */     Character refInvalidChar;
/*  781:1229 */     if (this.fIsXMLVersion11)
/*  782:     */     {
/*  783:1231 */       int i = 0;
/*  784:1232 */       while (i < datalength) {
/*  785:1233 */         if (XML11Char.isXML11Invalid(dataarray[(i++)]))
/*  786:     */         {
/*  787:1235 */           char ch = dataarray[(i - 1)];
/*  788:1236 */           if ((XMLChar.isHighSurrogate(ch)) && (i < datalength))
/*  789:     */           {
/*  790:1237 */             char ch2 = dataarray[(i++)];
/*  791:1238 */             if ((XMLChar.isLowSurrogate(ch2)) && (XMLChar.isSupplemental(XMLChar.supplemental(ch, ch2)))) {}
/*  792:     */           }
/*  793:     */           else
/*  794:     */           {
/*  795:1245 */             refInvalidChar = new Character(ch);
/*  796:1246 */             return refInvalidChar;
/*  797:     */           }
/*  798:     */         }
/*  799:     */       }
/*  800:     */     }
/*  801:     */     else
/*  802:     */     {
/*  803:1252 */       int i = 0;
/*  804:1253 */       while (i < datalength) {
/*  805:1254 */         if (XMLChar.isInvalid(dataarray[(i++)]))
/*  806:     */         {
/*  807:1256 */           char ch = dataarray[(i - 1)];
/*  808:1257 */           if ((XMLChar.isHighSurrogate(ch)) && (i < datalength))
/*  809:     */           {
/*  810:1258 */             char ch2 = dataarray[(i++)];
/*  811:1259 */             if ((XMLChar.isLowSurrogate(ch2)) && (XMLChar.isSupplemental(XMLChar.supplemental(ch, ch2)))) {}
/*  812:     */           }
/*  813:     */           else
/*  814:     */           {
/*  815:1266 */             refInvalidChar = new Character(ch);
/*  816:1267 */             return refInvalidChar;
/*  817:     */           }
/*  818:     */         }
/*  819:     */       }
/*  820:     */     }
/*  821:1272 */     return null;
/*  822:     */   }
/*  823:     */   
/*  824:     */   protected void isCommentWellFormed(String data)
/*  825:     */   {
/*  826:1282 */     if ((data == null) || (data.length() == 0)) {
/*  827:1283 */       return;
/*  828:     */     }
/*  829:1286 */     char[] dataarray = data.toCharArray();
/*  830:1287 */     int datalength = dataarray.length;
/*  831:1290 */     if (this.fIsXMLVersion11)
/*  832:     */     {
/*  833:1292 */       int i = 0;
/*  834:1293 */       while (i < datalength)
/*  835:     */       {
/*  836:1294 */         char c = dataarray[(i++)];
/*  837:1295 */         if (XML11Char.isXML11Invalid(c))
/*  838:     */         {
/*  839:1297 */           if ((XMLChar.isHighSurrogate(c)) && (i < datalength))
/*  840:     */           {
/*  841:1298 */             char c2 = dataarray[(i++)];
/*  842:1299 */             if ((XMLChar.isLowSurrogate(c2)) && (XMLChar.isSupplemental(XMLChar.supplemental(c, c2)))) {}
/*  843:     */           }
/*  844:     */           else
/*  845:     */           {
/*  846:1305 */             String msg = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_COMMENT", new Object[] { new Character(c) });
/*  847:1310 */             if (this.fErrorHandler != null) {
/*  848:1311 */               this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character", null, null, null));
/*  849:     */             }
/*  850:     */           }
/*  851:     */         }
/*  852:1320 */         else if ((c == '-') && (i < datalength) && (dataarray[i] == '-'))
/*  853:     */         {
/*  854:1321 */           String msg = Utils.messages.createMessage("ER_WF_DASH_IN_COMMENT", null);
/*  855:1326 */           if (this.fErrorHandler != null) {
/*  856:1327 */             this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character", null, null, null));
/*  857:     */           }
/*  858:     */         }
/*  859:     */       }
/*  860:     */     }
/*  861:     */     else
/*  862:     */     {
/*  863:1341 */       int i = 0;
/*  864:1342 */       while (i < datalength)
/*  865:     */       {
/*  866:1343 */         char c = dataarray[(i++)];
/*  867:1344 */         if (XMLChar.isInvalid(c))
/*  868:     */         {
/*  869:1346 */           if ((XMLChar.isHighSurrogate(c)) && (i < datalength))
/*  870:     */           {
/*  871:1347 */             char c2 = dataarray[(i++)];
/*  872:1348 */             if ((XMLChar.isLowSurrogate(c2)) && (XMLChar.isSupplemental(XMLChar.supplemental(c, c2)))) {}
/*  873:     */           }
/*  874:     */           else
/*  875:     */           {
/*  876:1354 */             String msg = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_COMMENT", new Object[] { new Character(c) });
/*  877:1359 */             if (this.fErrorHandler != null) {
/*  878:1360 */               this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character", null, null, null));
/*  879:     */             }
/*  880:     */           }
/*  881:     */         }
/*  882:1369 */         else if ((c == '-') && (i < datalength) && (dataarray[i] == '-'))
/*  883:     */         {
/*  884:1370 */           String msg = Utils.messages.createMessage("ER_WF_DASH_IN_COMMENT", null);
/*  885:1375 */           if (this.fErrorHandler != null) {
/*  886:1376 */             this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character", null, null, null));
/*  887:     */           }
/*  888:     */         }
/*  889:     */       }
/*  890:     */     }
/*  891:     */   }
/*  892:     */   
/*  893:     */   protected void isElementWellFormed(Node node)
/*  894:     */   {
/*  895:1398 */     boolean isNameWF = false;
/*  896:1399 */     if ((this.fFeatures & 0x100) != 0) {
/*  897:1400 */       isNameWF = isValidQName(node.getPrefix(), node.getLocalName(), this.fIsXMLVersion11);
/*  898:     */     } else {
/*  899:1406 */       isNameWF = isXMLName(node.getNodeName(), this.fIsXMLVersion11);
/*  900:     */     }
/*  901:1409 */     if (!isNameWF)
/*  902:     */     {
/*  903:1410 */       String msg = Utils.messages.createMessage("wf-invalid-character-in-node-name", new Object[] { "Element", node.getNodeName() });
/*  904:1415 */       if (this.fErrorHandler != null) {
/*  905:1416 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character-in-node-name", null, null, null));
/*  906:     */       }
/*  907:     */     }
/*  908:     */   }
/*  909:     */   
/*  910:     */   protected void isAttributeWellFormed(Node node)
/*  911:     */   {
/*  912:1436 */     boolean isNameWF = false;
/*  913:1437 */     if ((this.fFeatures & 0x100) != 0) {
/*  914:1438 */       isNameWF = isValidQName(node.getPrefix(), node.getLocalName(), this.fIsXMLVersion11);
/*  915:     */     } else {
/*  916:1444 */       isNameWF = isXMLName(node.getNodeName(), this.fIsXMLVersion11);
/*  917:     */     }
/*  918:1447 */     if (!isNameWF)
/*  919:     */     {
/*  920:1448 */       String msg = Utils.messages.createMessage("wf-invalid-character-in-node-name", new Object[] { "Attr", node.getNodeName() });
/*  921:1453 */       if (this.fErrorHandler != null) {
/*  922:1454 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character-in-node-name", null, null, null));
/*  923:     */       }
/*  924:     */     }
/*  925:1467 */     String value = node.getNodeValue();
/*  926:1468 */     if (value.indexOf('<') >= 0)
/*  927:     */     {
/*  928:1469 */       String msg = Utils.messages.createMessage("ER_WF_LT_IN_ATTVAL", new Object[] { ((Attr)node).getOwnerElement().getNodeName(), node.getNodeName() });
/*  929:1476 */       if (this.fErrorHandler != null) {
/*  930:1477 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "ER_WF_LT_IN_ATTVAL", null, null, null));
/*  931:     */       }
/*  932:     */     }
/*  933:1490 */     NodeList children = node.getChildNodes();
/*  934:1491 */     for (int i = 0; i < children.getLength(); i++)
/*  935:     */     {
/*  936:1492 */       Node child = children.item(i);
/*  937:1500 */       if (child != null) {
/*  938:1504 */         switch (child.getNodeType())
/*  939:     */         {
/*  940:     */         case 3: 
/*  941:1506 */           isTextWellFormed((Text)child);
/*  942:1507 */           break;
/*  943:     */         case 5: 
/*  944:1509 */           isEntityReferneceWellFormed((EntityReference)child);
/*  945:     */         }
/*  946:     */       }
/*  947:     */     }
/*  948:     */   }
/*  949:     */   
/*  950:     */   protected void isPIWellFormed(ProcessingInstruction node)
/*  951:     */   {
/*  952:1532 */     if (!isXMLName(node.getNodeName(), this.fIsXMLVersion11))
/*  953:     */     {
/*  954:1533 */       String msg = Utils.messages.createMessage("wf-invalid-character-in-node-name", new Object[] { "ProcessingInstruction", node.getTarget() });
/*  955:1538 */       if (this.fErrorHandler != null) {
/*  956:1539 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character-in-node-name", null, null, null));
/*  957:     */       }
/*  958:     */     }
/*  959:1553 */     Character invalidChar = isWFXMLChar(node.getData());
/*  960:1554 */     if (invalidChar != null)
/*  961:     */     {
/*  962:1555 */       String msg = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_PI", new Object[] { Integer.toHexString(Character.getNumericValue(invalidChar.charValue())) });
/*  963:1560 */       if (this.fErrorHandler != null) {
/*  964:1561 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character", null, null, null));
/*  965:     */       }
/*  966:     */     }
/*  967:     */   }
/*  968:     */   
/*  969:     */   protected void isCDATASectionWellFormed(CDATASection node)
/*  970:     */   {
/*  971:1583 */     Character invalidChar = isWFXMLChar(node.getData());
/*  972:1585 */     if (invalidChar != null)
/*  973:     */     {
/*  974:1586 */       String msg = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_CDATA", new Object[] { Integer.toHexString(Character.getNumericValue(invalidChar.charValue())) });
/*  975:1591 */       if (this.fErrorHandler != null) {
/*  976:1592 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character", null, null, null));
/*  977:     */       }
/*  978:     */     }
/*  979:     */   }
/*  980:     */   
/*  981:     */   protected void isTextWellFormed(Text node)
/*  982:     */   {
/*  983:1612 */     Character invalidChar = isWFXMLChar(node.getData());
/*  984:1613 */     if (invalidChar != null)
/*  985:     */     {
/*  986:1614 */       String msg = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_TEXT", new Object[] { Integer.toHexString(Character.getNumericValue(invalidChar.charValue())) });
/*  987:1619 */       if (this.fErrorHandler != null) {
/*  988:1620 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character", null, null, null));
/*  989:     */       }
/*  990:     */     }
/*  991:     */   }
/*  992:     */   
/*  993:     */   protected void isEntityReferneceWellFormed(EntityReference node)
/*  994:     */   {
/*  995:1643 */     if (!isXMLName(node.getNodeName(), this.fIsXMLVersion11))
/*  996:     */     {
/*  997:1644 */       String msg = Utils.messages.createMessage("wf-invalid-character-in-node-name", new Object[] { "EntityReference", node.getNodeName() });
/*  998:1649 */       if (this.fErrorHandler != null) {
/*  999:1650 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "wf-invalid-character-in-node-name", null, null, null));
/* 1000:     */       }
/* 1001:     */     }
/* 1002:1662 */     Node parent = node.getParentNode();
/* 1003:     */     
/* 1004:     */ 
/* 1005:     */ 
/* 1006:     */ 
/* 1007:1667 */     DocumentType docType = node.getOwnerDocument().getDoctype();
/* 1008:1668 */     if (docType != null)
/* 1009:     */     {
/* 1010:1669 */       NamedNodeMap entities = docType.getEntities();
/* 1011:1670 */       for (int i = 0; i < entities.getLength(); i++)
/* 1012:     */       {
/* 1013:1671 */         Entity ent = (Entity)entities.item(i);
/* 1014:     */         
/* 1015:1673 */         String nodeName = node.getNodeName() == null ? "" : node.getNodeName();
/* 1016:     */         
/* 1017:1675 */         String nodeNamespaceURI = node.getNamespaceURI() == null ? "" : node.getNamespaceURI();
/* 1018:     */         
/* 1019:     */ 
/* 1020:     */ 
/* 1021:1679 */         String entName = ent.getNodeName() == null ? "" : ent.getNodeName();
/* 1022:     */         
/* 1023:1681 */         String entNamespaceURI = ent.getNamespaceURI() == null ? "" : ent.getNamespaceURI();
/* 1024:1685 */         if ((parent.getNodeType() == 1) && 
/* 1025:1686 */           (entNamespaceURI.equals(nodeNamespaceURI)) && (entName.equals(nodeName))) {
/* 1026:1689 */           if (ent.getNotationName() != null)
/* 1027:     */           {
/* 1028:1690 */             String msg = Utils.messages.createMessage("ER_WF_REF_TO_UNPARSED_ENT", new Object[] { node.getNodeName() });
/* 1029:1695 */             if (this.fErrorHandler != null) {
/* 1030:1696 */               this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "ER_WF_REF_TO_UNPARSED_ENT", null, null, null));
/* 1031:     */             }
/* 1032:     */           }
/* 1033:     */         }
/* 1034:1711 */         if ((parent.getNodeType() == 2) && 
/* 1035:1712 */           (entNamespaceURI.equals(nodeNamespaceURI)) && (entName.equals(nodeName))) {
/* 1036:1715 */           if ((ent.getPublicId() != null) || (ent.getSystemId() != null) || (ent.getNotationName() != null))
/* 1037:     */           {
/* 1038:1718 */             String msg = Utils.messages.createMessage("ER_WF_REF_TO_EXTERNAL_ENT", new Object[] { node.getNodeName() });
/* 1039:1723 */             if (this.fErrorHandler != null) {
/* 1040:1724 */               this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "ER_WF_REF_TO_EXTERNAL_ENT", null, null, null));
/* 1041:     */             }
/* 1042:     */           }
/* 1043:     */         }
/* 1044:     */       }
/* 1045:     */     }
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   protected void checkUnboundPrefixInEntRef(Node node)
/* 1049:     */   {
/* 1050:     */     Node next;
/* 1051:1751 */     for (Node child = node.getFirstChild(); child != null; child = next)
/* 1052:     */     {
/* 1053:1752 */       next = child.getNextSibling();
/* 1054:1754 */       if (child.getNodeType() == 1)
/* 1055:     */       {
/* 1056:1758 */         String prefix = child.getPrefix();
/* 1057:1759 */         if ((prefix != null) && (this.fNSBinder.getURI(prefix) == null))
/* 1058:     */         {
/* 1059:1761 */           String msg = Utils.messages.createMessage("unbound-prefix-in-entity-reference", new Object[] { node.getNodeName(), child.getNodeName(), prefix });
/* 1060:1769 */           if (this.fErrorHandler != null) {
/* 1061:1770 */             this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "unbound-prefix-in-entity-reference", null, null, null));
/* 1062:     */           }
/* 1063:     */         }
/* 1064:1781 */         NamedNodeMap attrs = child.getAttributes();
/* 1065:1783 */         for (int i = 0; i < attrs.getLength(); i++)
/* 1066:     */         {
/* 1067:1784 */           String attrPrefix = attrs.item(i).getPrefix();
/* 1068:1785 */           if ((attrPrefix != null) && (this.fNSBinder.getURI(attrPrefix) == null))
/* 1069:     */           {
/* 1070:1787 */             String msg = Utils.messages.createMessage("unbound-prefix-in-entity-reference", new Object[] { node.getNodeName(), child.getNodeName(), attrs.item(i) });
/* 1071:1795 */             if (this.fErrorHandler != null) {
/* 1072:1796 */               this.fErrorHandler.handleError(new DOMErrorImpl((short)3, msg, "unbound-prefix-in-entity-reference", null, null, null));
/* 1073:     */             }
/* 1074:     */           }
/* 1075:     */         }
/* 1076:     */       }
/* 1077:1809 */       if (child.hasChildNodes()) {
/* 1078:1810 */         checkUnboundPrefixInEntRef(child);
/* 1079:     */       }
/* 1080:     */     }
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   protected void recordLocalNSDecl(Node node)
/* 1084:     */   {
/* 1085:1824 */     NamedNodeMap atts = ((Element)node).getAttributes();
/* 1086:1825 */     int length = atts.getLength();
/* 1087:1827 */     for (int i = 0; i < length; i++)
/* 1088:     */     {
/* 1089:1828 */       Node attr = atts.item(i);
/* 1090:     */       
/* 1091:1830 */       String localName = attr.getLocalName();
/* 1092:1831 */       String attrPrefix = attr.getPrefix();
/* 1093:1832 */       String attrValue = attr.getNodeValue();
/* 1094:1833 */       String attrNS = attr.getNamespaceURI();
/* 1095:     */       
/* 1096:1835 */       localName = (localName == null) || ("xmlns".equals(localName)) ? "" : localName;
/* 1097:     */       
/* 1098:     */ 
/* 1099:1838 */       attrPrefix = attrPrefix == null ? "" : attrPrefix;
/* 1100:1839 */       attrValue = attrValue == null ? "" : attrValue;
/* 1101:1840 */       attrNS = attrNS == null ? "" : attrNS;
/* 1102:1843 */       if ("http://www.w3.org/2000/xmlns/".equals(attrNS)) {
/* 1103:1846 */         if ("http://www.w3.org/2000/xmlns/".equals(attrValue))
/* 1104:     */         {
/* 1105:1847 */           String msg = Utils.messages.createMessage("ER_NS_PREFIX_CANNOT_BE_BOUND", new Object[] { attrPrefix, "http://www.w3.org/2000/xmlns/" });
/* 1106:1852 */           if (this.fErrorHandler != null) {
/* 1107:1853 */             this.fErrorHandler.handleError(new DOMErrorImpl((short)2, msg, "ER_NS_PREFIX_CANNOT_BE_BOUND", null, null, null));
/* 1108:     */           }
/* 1109:     */         }
/* 1110:1864 */         else if ("xmlns".equals(attrPrefix))
/* 1111:     */         {
/* 1112:1866 */           if (attrValue.length() != 0) {
/* 1113:1867 */             this.fNSBinder.declarePrefix(localName, attrValue);
/* 1114:     */           }
/* 1115:     */         }
/* 1116:     */         else
/* 1117:     */         {
/* 1118:1873 */           this.fNSBinder.declarePrefix("", attrValue);
/* 1119:     */         }
/* 1120:     */       }
/* 1121:     */     }
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   protected void fixupElementNS(Node node)
/* 1125:     */     throws SAXException
/* 1126:     */   {
/* 1127:1887 */     String namespaceURI = ((Element)node).getNamespaceURI();
/* 1128:1888 */     String prefix = ((Element)node).getPrefix();
/* 1129:1889 */     String localName = ((Element)node).getLocalName();
/* 1130:1891 */     if (namespaceURI != null)
/* 1131:     */     {
/* 1132:1894 */       prefix = prefix == null ? "" : prefix;
/* 1133:1895 */       String inScopeNamespaceURI = this.fNSBinder.getURI(prefix);
/* 1134:1897 */       if ((inScopeNamespaceURI == null) || (!inScopeNamespaceURI.equals(namespaceURI)))
/* 1135:     */       {
/* 1136:1909 */         if ((this.fFeatures & 0x200) != 0) {
/* 1137:1910 */           if (("".equals(prefix)) || ("".equals(namespaceURI))) {
/* 1138:1911 */             ((Element)node).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespaceURI);
/* 1139:     */           } else {
/* 1140:1913 */             ((Element)node).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, namespaceURI);
/* 1141:     */           }
/* 1142:     */         }
/* 1143:1916 */         this.fLocalNSBinder.declarePrefix(prefix, namespaceURI);
/* 1144:1917 */         this.fNSBinder.declarePrefix(prefix, namespaceURI);
/* 1145:     */       }
/* 1146:     */     }
/* 1147:1923 */     else if ((localName == null) || ("".equals(localName)))
/* 1148:     */     {
/* 1149:1925 */       String msg = Utils.messages.createMessage("ER_NULL_LOCAL_ELEMENT_NAME", new Object[] { node.getNodeName() });
/* 1150:1930 */       if (this.fErrorHandler != null) {
/* 1151:1931 */         this.fErrorHandler.handleError(new DOMErrorImpl((short)2, msg, "ER_NULL_LOCAL_ELEMENT_NAME", null, null, null));
/* 1152:     */       }
/* 1153:     */     }
/* 1154:     */     else
/* 1155:     */     {
/* 1156:1941 */       namespaceURI = this.fNSBinder.getURI("");
/* 1157:1942 */       if ((namespaceURI != null) && (namespaceURI.length() > 0))
/* 1158:     */       {
/* 1159:1943 */         ((Element)node).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "");
/* 1160:1944 */         this.fLocalNSBinder.declarePrefix("", "");
/* 1161:1945 */         this.fNSBinder.declarePrefix("", "");
/* 1162:     */       }
/* 1163:     */     }
/* 1164:     */   }
/* 1165:     */   
/* 1166:1956 */   private static final Hashtable s_propKeys = new Hashtable();
/* 1167:     */   
/* 1168:     */   static
/* 1169:     */   {
/* 1170:1964 */     int i = 2;
/* 1171:1965 */     Integer val = new Integer(i);
/* 1172:1966 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", val);
/* 1173:     */     
/* 1174:     */ 
/* 1175:     */ 
/* 1176:     */ 
/* 1177:1971 */     int i1 = 8;
/* 1178:1972 */     val = new Integer(i1);
/* 1179:1973 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}comments", val);
/* 1180:     */     
/* 1181:     */ 
/* 1182:     */ 
/* 1183:     */ 
/* 1184:1978 */     int i2 = 32;
/* 1185:1979 */     val = new Integer(i2);
/* 1186:1980 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", val);
/* 1187:     */     
/* 1188:     */ 
/* 1189:     */ 
/* 1190:1984 */     int i3 = 64;
/* 1191:     */     
/* 1192:     */ 
/* 1193:1987 */     val = new Integer(i3);
/* 1194:1988 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}entities", val);
/* 1195:     */     
/* 1196:     */ 
/* 1197:     */ 
/* 1198:     */ 
/* 1199:1993 */     int i4 = 256;
/* 1200:1994 */     val = new Integer(i4);
/* 1201:1995 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", val);
/* 1202:     */     
/* 1203:     */ 
/* 1204:     */ 
/* 1205:     */ 
/* 1206:2000 */     int i5 = 512;
/* 1207:2001 */     val = new Integer(i5);
/* 1208:2002 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", val);
/* 1209:     */     
/* 1210:     */ 
/* 1211:     */ 
/* 1212:     */ 
/* 1213:     */ 
/* 1214:2008 */     int i6 = 2048;
/* 1215:2009 */     val = new Integer(i6);
/* 1216:2010 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}split-cdata-sections", val);
/* 1217:     */     
/* 1218:     */ 
/* 1219:     */ 
/* 1220:     */ 
/* 1221:2015 */     int i7 = 16384;
/* 1222:2016 */     val = new Integer(i7);
/* 1223:2017 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", val);
/* 1224:     */     
/* 1225:     */ 
/* 1226:     */ 
/* 1227:     */ 
/* 1228:2022 */     int i8 = 32768;
/* 1229:2023 */     val = new Integer(i8);
/* 1230:2024 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}discard-default-content", val);
/* 1231:     */     
/* 1232:     */ 
/* 1233:     */ 
/* 1234:     */ 
/* 1235:     */ 
/* 1236:     */ 
/* 1237:2031 */     s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}format-pretty-print", "");
/* 1238:     */     
/* 1239:     */ 
/* 1240:     */ 
/* 1241:2035 */     s_propKeys.put("omit-xml-declaration", "");
/* 1242:2036 */     s_propKeys.put("{http://xml.apache.org/xerces-2j}xml-version", "");
/* 1243:     */     
/* 1244:     */ 
/* 1245:2039 */     s_propKeys.put("encoding", "");
/* 1246:2040 */     s_propKeys.put("{http://xml.apache.org/xerces-2j}entities", "");
/* 1247:     */   }
/* 1248:     */   
/* 1249:     */   protected void initProperties(Properties properties)
/* 1250:     */   {
/* 1251:2051 */     for (Enumeration keys = properties.keys(); keys.hasMoreElements();)
/* 1252:     */     {
/* 1253:2053 */       String key = (String)keys.nextElement();
/* 1254:     */       
/* 1255:     */ 
/* 1256:     */ 
/* 1257:     */ 
/* 1258:     */ 
/* 1259:     */ 
/* 1260:     */ 
/* 1261:     */ 
/* 1262:     */ 
/* 1263:     */ 
/* 1264:2064 */       Object iobj = s_propKeys.get(key);
/* 1265:2065 */       if (iobj != null) {
/* 1266:2066 */         if ((iobj instanceof Integer))
/* 1267:     */         {
/* 1268:2079 */           int BITFLAG = ((Integer)iobj).intValue();
/* 1269:2080 */           if (properties.getProperty(key).endsWith("yes")) {
/* 1270:2081 */             this.fFeatures |= BITFLAG;
/* 1271:     */           } else {
/* 1272:2083 */             this.fFeatures &= (BITFLAG ^ 0xFFFFFFFF);
/* 1273:     */           }
/* 1274:     */         }
/* 1275:2089 */         else if ("{http://www.w3.org/TR/DOM-Level-3-LS}format-pretty-print".equals(key))
/* 1276:     */         {
/* 1277:2093 */           if (properties.getProperty(key).endsWith("yes"))
/* 1278:     */           {
/* 1279:2094 */             this.fSerializer.setIndent(true);
/* 1280:2095 */             this.fSerializer.setIndentAmount(3);
/* 1281:     */           }
/* 1282:     */           else
/* 1283:     */           {
/* 1284:2097 */             this.fSerializer.setIndent(false);
/* 1285:     */           }
/* 1286:     */         }
/* 1287:2099 */         else if ("omit-xml-declaration".equals(key))
/* 1288:     */         {
/* 1289:2103 */           if (properties.getProperty(key).endsWith("yes")) {
/* 1290:2104 */             this.fSerializer.setOmitXMLDeclaration(true);
/* 1291:     */           } else {
/* 1292:2106 */             this.fSerializer.setOmitXMLDeclaration(false);
/* 1293:     */           }
/* 1294:     */         }
/* 1295:2108 */         else if ("{http://xml.apache.org/xerces-2j}xml-version".equals(key))
/* 1296:     */         {
/* 1297:2114 */           String version = properties.getProperty(key);
/* 1298:2115 */           if ("1.1".equals(version))
/* 1299:     */           {
/* 1300:2116 */             this.fIsXMLVersion11 = true;
/* 1301:2117 */             this.fSerializer.setVersion(version);
/* 1302:     */           }
/* 1303:     */           else
/* 1304:     */           {
/* 1305:2119 */             this.fSerializer.setVersion("1.0");
/* 1306:     */           }
/* 1307:     */         }
/* 1308:2121 */         else if ("encoding".equals(key))
/* 1309:     */         {
/* 1310:2124 */           String encoding = properties.getProperty(key);
/* 1311:2125 */           if (encoding != null) {
/* 1312:2126 */             this.fSerializer.setEncoding(encoding);
/* 1313:     */           }
/* 1314:     */         }
/* 1315:2128 */         else if ("{http://xml.apache.org/xerces-2j}entities".equals(key))
/* 1316:     */         {
/* 1317:2131 */           if (properties.getProperty(key).endsWith("yes")) {
/* 1318:2132 */             this.fSerializer.setDTDEntityExpansion(false);
/* 1319:     */           } else {
/* 1320:2135 */             this.fSerializer.setDTDEntityExpansion(true);
/* 1321:     */           }
/* 1322:     */         }
/* 1323:     */       }
/* 1324:     */     }
/* 1325:2144 */     if (this.fNewLine != null) {
/* 1326:2145 */       this.fSerializer.setOutputProperty("{http://xml.apache.org/xalan}line-separator", this.fNewLine);
/* 1327:     */     }
/* 1328:     */   }
/* 1329:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.DOM3TreeWalker
 * JD-Core Version:    0.7.0.1
 */