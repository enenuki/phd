/*    1:     */ package org.apache.xalan.xsltc.dom;
/*    2:     */ 
/*    3:     */ import javax.xml.transform.SourceLocator;
/*    4:     */ import org.apache.xalan.xsltc.DOM;
/*    5:     */ import org.apache.xalan.xsltc.StripFilter;
/*    6:     */ import org.apache.xalan.xsltc.TransletException;
/*    7:     */ import org.apache.xalan.xsltc.runtime.AttributeList;
/*    8:     */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*    9:     */ import org.apache.xalan.xsltc.runtime.Hashtable;
/*   10:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*   11:     */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   12:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   13:     */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   14:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseTraversers;
/*   15:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM;
/*   16:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2;
/*   17:     */ import org.apache.xml.serializer.SerializationHandler;
/*   18:     */ import org.apache.xml.utils.XMLString;
/*   19:     */ import org.w3c.dom.Node;
/*   20:     */ import org.w3c.dom.NodeList;
/*   21:     */ import org.xml.sax.Attributes;
/*   22:     */ import org.xml.sax.ContentHandler;
/*   23:     */ import org.xml.sax.DTDHandler;
/*   24:     */ import org.xml.sax.EntityResolver;
/*   25:     */ import org.xml.sax.ErrorHandler;
/*   26:     */ import org.xml.sax.SAXException;
/*   27:     */ import org.xml.sax.ext.DeclHandler;
/*   28:     */ import org.xml.sax.ext.LexicalHandler;
/*   29:     */ 
/*   30:     */ public class AdaptiveResultTreeImpl
/*   31:     */   extends SimpleResultTreeImpl
/*   32:     */ {
/*   33:  78 */   private static int _documentURIIndex = 0;
/*   34:     */   private SAXImpl _dom;
/*   35:     */   private DTMWSFilter _wsfilter;
/*   36:     */   private int _initSize;
/*   37:     */   private boolean _buildIdIndex;
/*   38:  95 */   private final AttributeList _attributes = new AttributeList();
/*   39:     */   private String _openElementName;
/*   40:     */   
/*   41:     */   public AdaptiveResultTreeImpl(XSLTCDTMManager dtmManager, int documentID, DTMWSFilter wsfilter, int initSize, boolean buildIdIndex)
/*   42:     */   {
/*   43: 106 */     super(dtmManager, documentID);
/*   44:     */     
/*   45: 108 */     this._wsfilter = wsfilter;
/*   46: 109 */     this._initSize = initSize;
/*   47: 110 */     this._buildIdIndex = buildIdIndex;
/*   48:     */   }
/*   49:     */   
/*   50:     */   public DOM getNestedDOM()
/*   51:     */   {
/*   52: 116 */     return this._dom;
/*   53:     */   }
/*   54:     */   
/*   55:     */   public int getDocument()
/*   56:     */   {
/*   57: 122 */     if (this._dom != null) {
/*   58: 123 */       return this._dom.getDocument();
/*   59:     */     }
/*   60: 126 */     return super.getDocument();
/*   61:     */   }
/*   62:     */   
/*   63:     */   public String getStringValue()
/*   64:     */   {
/*   65: 133 */     if (this._dom != null) {
/*   66: 134 */       return this._dom.getStringValue();
/*   67:     */     }
/*   68: 137 */     return super.getStringValue();
/*   69:     */   }
/*   70:     */   
/*   71:     */   public DTMAxisIterator getIterator()
/*   72:     */   {
/*   73: 143 */     if (this._dom != null) {
/*   74: 144 */       return this._dom.getIterator();
/*   75:     */     }
/*   76: 147 */     return super.getIterator();
/*   77:     */   }
/*   78:     */   
/*   79:     */   public DTMAxisIterator getChildren(int node)
/*   80:     */   {
/*   81: 153 */     if (this._dom != null) {
/*   82: 154 */       return this._dom.getChildren(node);
/*   83:     */     }
/*   84: 157 */     return super.getChildren(node);
/*   85:     */   }
/*   86:     */   
/*   87:     */   public DTMAxisIterator getTypedChildren(int type)
/*   88:     */   {
/*   89: 163 */     if (this._dom != null) {
/*   90: 164 */       return this._dom.getTypedChildren(type);
/*   91:     */     }
/*   92: 167 */     return super.getTypedChildren(type);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public DTMAxisIterator getAxisIterator(int axis)
/*   96:     */   {
/*   97: 173 */     if (this._dom != null) {
/*   98: 174 */       return this._dom.getAxisIterator(axis);
/*   99:     */     }
/*  100: 177 */     return super.getAxisIterator(axis);
/*  101:     */   }
/*  102:     */   
/*  103:     */   public DTMAxisIterator getTypedAxisIterator(int axis, int type)
/*  104:     */   {
/*  105: 183 */     if (this._dom != null) {
/*  106: 184 */       return this._dom.getTypedAxisIterator(axis, type);
/*  107:     */     }
/*  108: 187 */     return super.getTypedAxisIterator(axis, type);
/*  109:     */   }
/*  110:     */   
/*  111:     */   public DTMAxisIterator getNthDescendant(int node, int n, boolean includeself)
/*  112:     */   {
/*  113: 193 */     if (this._dom != null) {
/*  114: 194 */       return this._dom.getNthDescendant(node, n, includeself);
/*  115:     */     }
/*  116: 197 */     return super.getNthDescendant(node, n, includeself);
/*  117:     */   }
/*  118:     */   
/*  119:     */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns)
/*  120:     */   {
/*  121: 203 */     if (this._dom != null) {
/*  122: 204 */       return this._dom.getNamespaceAxisIterator(axis, ns);
/*  123:     */     }
/*  124: 207 */     return super.getNamespaceAxisIterator(axis, ns);
/*  125:     */   }
/*  126:     */   
/*  127:     */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iter, int returnType, String value, boolean op)
/*  128:     */   {
/*  129: 214 */     if (this._dom != null) {
/*  130: 215 */       return this._dom.getNodeValueIterator(iter, returnType, value, op);
/*  131:     */     }
/*  132: 218 */     return super.getNodeValueIterator(iter, returnType, value, op);
/*  133:     */   }
/*  134:     */   
/*  135:     */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node)
/*  136:     */   {
/*  137: 224 */     if (this._dom != null) {
/*  138: 225 */       return this._dom.orderNodes(source, node);
/*  139:     */     }
/*  140: 228 */     return super.orderNodes(source, node);
/*  141:     */   }
/*  142:     */   
/*  143:     */   public String getNodeName(int node)
/*  144:     */   {
/*  145: 234 */     if (this._dom != null) {
/*  146: 235 */       return this._dom.getNodeName(node);
/*  147:     */     }
/*  148: 238 */     return super.getNodeName(node);
/*  149:     */   }
/*  150:     */   
/*  151:     */   public String getNodeNameX(int node)
/*  152:     */   {
/*  153: 244 */     if (this._dom != null) {
/*  154: 245 */       return this._dom.getNodeNameX(node);
/*  155:     */     }
/*  156: 248 */     return super.getNodeNameX(node);
/*  157:     */   }
/*  158:     */   
/*  159:     */   public String getNamespaceName(int node)
/*  160:     */   {
/*  161: 254 */     if (this._dom != null) {
/*  162: 255 */       return this._dom.getNamespaceName(node);
/*  163:     */     }
/*  164: 258 */     return super.getNamespaceName(node);
/*  165:     */   }
/*  166:     */   
/*  167:     */   public int getExpandedTypeID(int nodeHandle)
/*  168:     */   {
/*  169: 265 */     if (this._dom != null) {
/*  170: 266 */       return this._dom.getExpandedTypeID(nodeHandle);
/*  171:     */     }
/*  172: 269 */     return super.getExpandedTypeID(nodeHandle);
/*  173:     */   }
/*  174:     */   
/*  175:     */   public int getNamespaceType(int node)
/*  176:     */   {
/*  177: 275 */     if (this._dom != null) {
/*  178: 276 */       return this._dom.getNamespaceType(node);
/*  179:     */     }
/*  180: 279 */     return super.getNamespaceType(node);
/*  181:     */   }
/*  182:     */   
/*  183:     */   public int getParent(int nodeHandle)
/*  184:     */   {
/*  185: 285 */     if (this._dom != null) {
/*  186: 286 */       return this._dom.getParent(nodeHandle);
/*  187:     */     }
/*  188: 289 */     return super.getParent(nodeHandle);
/*  189:     */   }
/*  190:     */   
/*  191:     */   public int getAttributeNode(int gType, int element)
/*  192:     */   {
/*  193: 295 */     if (this._dom != null) {
/*  194: 296 */       return this._dom.getAttributeNode(gType, element);
/*  195:     */     }
/*  196: 299 */     return super.getAttributeNode(gType, element);
/*  197:     */   }
/*  198:     */   
/*  199:     */   public String getStringValueX(int nodeHandle)
/*  200:     */   {
/*  201: 305 */     if (this._dom != null) {
/*  202: 306 */       return this._dom.getStringValueX(nodeHandle);
/*  203:     */     }
/*  204: 309 */     return super.getStringValueX(nodeHandle);
/*  205:     */   }
/*  206:     */   
/*  207:     */   public void copy(int node, SerializationHandler handler)
/*  208:     */     throws TransletException
/*  209:     */   {
/*  210: 316 */     if (this._dom != null) {
/*  211: 317 */       this._dom.copy(node, handler);
/*  212:     */     } else {
/*  213: 320 */       super.copy(node, handler);
/*  214:     */     }
/*  215:     */   }
/*  216:     */   
/*  217:     */   public void copy(DTMAxisIterator nodes, SerializationHandler handler)
/*  218:     */     throws TransletException
/*  219:     */   {
/*  220: 327 */     if (this._dom != null) {
/*  221: 328 */       this._dom.copy(nodes, handler);
/*  222:     */     } else {
/*  223: 331 */       super.copy(nodes, handler);
/*  224:     */     }
/*  225:     */   }
/*  226:     */   
/*  227:     */   public String shallowCopy(int node, SerializationHandler handler)
/*  228:     */     throws TransletException
/*  229:     */   {
/*  230: 338 */     if (this._dom != null) {
/*  231: 339 */       return this._dom.shallowCopy(node, handler);
/*  232:     */     }
/*  233: 342 */     return super.shallowCopy(node, handler);
/*  234:     */   }
/*  235:     */   
/*  236:     */   public boolean lessThan(int node1, int node2)
/*  237:     */   {
/*  238: 348 */     if (this._dom != null) {
/*  239: 349 */       return this._dom.lessThan(node1, node2);
/*  240:     */     }
/*  241: 352 */     return super.lessThan(node1, node2);
/*  242:     */   }
/*  243:     */   
/*  244:     */   public void characters(int node, SerializationHandler handler)
/*  245:     */     throws TransletException
/*  246:     */   {
/*  247: 365 */     if (this._dom != null) {
/*  248: 366 */       this._dom.characters(node, handler);
/*  249:     */     } else {
/*  250: 369 */       super.characters(node, handler);
/*  251:     */     }
/*  252:     */   }
/*  253:     */   
/*  254:     */   public Node makeNode(int index)
/*  255:     */   {
/*  256: 375 */     if (this._dom != null) {
/*  257: 376 */       return this._dom.makeNode(index);
/*  258:     */     }
/*  259: 379 */     return super.makeNode(index);
/*  260:     */   }
/*  261:     */   
/*  262:     */   public Node makeNode(DTMAxisIterator iter)
/*  263:     */   {
/*  264: 385 */     if (this._dom != null) {
/*  265: 386 */       return this._dom.makeNode(iter);
/*  266:     */     }
/*  267: 389 */     return super.makeNode(iter);
/*  268:     */   }
/*  269:     */   
/*  270:     */   public NodeList makeNodeList(int index)
/*  271:     */   {
/*  272: 395 */     if (this._dom != null) {
/*  273: 396 */       return this._dom.makeNodeList(index);
/*  274:     */     }
/*  275: 399 */     return super.makeNodeList(index);
/*  276:     */   }
/*  277:     */   
/*  278:     */   public NodeList makeNodeList(DTMAxisIterator iter)
/*  279:     */   {
/*  280: 405 */     if (this._dom != null) {
/*  281: 406 */       return this._dom.makeNodeList(iter);
/*  282:     */     }
/*  283: 409 */     return super.makeNodeList(iter);
/*  284:     */   }
/*  285:     */   
/*  286:     */   public String getLanguage(int node)
/*  287:     */   {
/*  288: 415 */     if (this._dom != null) {
/*  289: 416 */       return this._dom.getLanguage(node);
/*  290:     */     }
/*  291: 419 */     return super.getLanguage(node);
/*  292:     */   }
/*  293:     */   
/*  294:     */   public int getSize()
/*  295:     */   {
/*  296: 425 */     if (this._dom != null) {
/*  297: 426 */       return this._dom.getSize();
/*  298:     */     }
/*  299: 429 */     return super.getSize();
/*  300:     */   }
/*  301:     */   
/*  302:     */   public String getDocumentURI(int node)
/*  303:     */   {
/*  304: 435 */     if (this._dom != null) {
/*  305: 436 */       return this._dom.getDocumentURI(node);
/*  306:     */     }
/*  307: 439 */     return "adaptive_rtf" + _documentURIIndex++;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public void setFilter(StripFilter filter)
/*  311:     */   {
/*  312: 445 */     if (this._dom != null) {
/*  313: 446 */       this._dom.setFilter(filter);
/*  314:     */     } else {
/*  315: 449 */       super.setFilter(filter);
/*  316:     */     }
/*  317:     */   }
/*  318:     */   
/*  319:     */   public void setupMapping(String[] names, String[] uris, int[] types, String[] namespaces)
/*  320:     */   {
/*  321: 455 */     if (this._dom != null) {
/*  322: 456 */       this._dom.setupMapping(names, uris, types, namespaces);
/*  323:     */     } else {
/*  324: 459 */       super.setupMapping(names, uris, types, namespaces);
/*  325:     */     }
/*  326:     */   }
/*  327:     */   
/*  328:     */   public boolean isElement(int node)
/*  329:     */   {
/*  330: 465 */     if (this._dom != null) {
/*  331: 466 */       return this._dom.isElement(node);
/*  332:     */     }
/*  333: 469 */     return super.isElement(node);
/*  334:     */   }
/*  335:     */   
/*  336:     */   public boolean isAttribute(int node)
/*  337:     */   {
/*  338: 475 */     if (this._dom != null) {
/*  339: 476 */       return this._dom.isAttribute(node);
/*  340:     */     }
/*  341: 479 */     return super.isAttribute(node);
/*  342:     */   }
/*  343:     */   
/*  344:     */   public String lookupNamespace(int node, String prefix)
/*  345:     */     throws TransletException
/*  346:     */   {
/*  347: 486 */     if (this._dom != null) {
/*  348: 487 */       return this._dom.lookupNamespace(node, prefix);
/*  349:     */     }
/*  350: 490 */     return super.lookupNamespace(node, prefix);
/*  351:     */   }
/*  352:     */   
/*  353:     */   public final int getNodeIdent(int nodehandle)
/*  354:     */   {
/*  355: 499 */     if (this._dom != null) {
/*  356: 500 */       return this._dom.getNodeIdent(nodehandle);
/*  357:     */     }
/*  358: 503 */     return super.getNodeIdent(nodehandle);
/*  359:     */   }
/*  360:     */   
/*  361:     */   public final int getNodeHandle(int nodeId)
/*  362:     */   {
/*  363: 512 */     if (this._dom != null) {
/*  364: 513 */       return this._dom.getNodeHandle(nodeId);
/*  365:     */     }
/*  366: 516 */     return super.getNodeHandle(nodeId);
/*  367:     */   }
/*  368:     */   
/*  369:     */   public DOM getResultTreeFrag(int initialSize, int rtfType)
/*  370:     */   {
/*  371: 522 */     if (this._dom != null) {
/*  372: 523 */       return this._dom.getResultTreeFrag(initialSize, rtfType);
/*  373:     */     }
/*  374: 526 */     return super.getResultTreeFrag(initialSize, rtfType);
/*  375:     */   }
/*  376:     */   
/*  377:     */   public SerializationHandler getOutputDomBuilder()
/*  378:     */   {
/*  379: 532 */     return this;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public int getNSType(int node)
/*  383:     */   {
/*  384: 537 */     if (this._dom != null) {
/*  385: 538 */       return this._dom.getNSType(node);
/*  386:     */     }
/*  387: 541 */     return super.getNSType(node);
/*  388:     */   }
/*  389:     */   
/*  390:     */   public String getUnparsedEntityURI(String name)
/*  391:     */   {
/*  392: 547 */     if (this._dom != null) {
/*  393: 548 */       return this._dom.getUnparsedEntityURI(name);
/*  394:     */     }
/*  395: 551 */     return super.getUnparsedEntityURI(name);
/*  396:     */   }
/*  397:     */   
/*  398:     */   public Hashtable getElementsWithIDs()
/*  399:     */   {
/*  400: 557 */     if (this._dom != null) {
/*  401: 558 */       return this._dom.getElementsWithIDs();
/*  402:     */     }
/*  403: 561 */     return super.getElementsWithIDs();
/*  404:     */   }
/*  405:     */   
/*  406:     */   private void maybeEmitStartElement()
/*  407:     */     throws SAXException
/*  408:     */   {
/*  409: 571 */     if (this._openElementName != null)
/*  410:     */     {
/*  411:     */       int index;
/*  412: 574 */       if ((index = this._openElementName.indexOf(":")) < 0) {
/*  413: 575 */         this._dom.startElement(null, this._openElementName, this._openElementName, this._attributes);
/*  414:     */       } else {
/*  415: 577 */         this._dom.startElement(null, this._openElementName.substring(index + 1), this._openElementName, this._attributes);
/*  416:     */       }
/*  417: 580 */       this._openElementName = null;
/*  418:     */     }
/*  419:     */   }
/*  420:     */   
/*  421:     */   private void prepareNewDOM()
/*  422:     */     throws SAXException
/*  423:     */   {
/*  424: 587 */     this._dom = ((SAXImpl)this._dtmManager.getDTM(null, true, this._wsfilter, true, false, false, this._initSize, this._buildIdIndex));
/*  425:     */     
/*  426:     */ 
/*  427: 590 */     this._dom.startDocument();
/*  428: 592 */     for (int i = 0; i < this._size; i++)
/*  429:     */     {
/*  430: 593 */       String str = this._textArray[i];
/*  431: 594 */       this._dom.characters(str.toCharArray(), 0, str.length());
/*  432:     */     }
/*  433: 596 */     this._size = 0;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void startDocument()
/*  437:     */     throws SAXException
/*  438:     */   {}
/*  439:     */   
/*  440:     */   public void endDocument()
/*  441:     */     throws SAXException
/*  442:     */   {
/*  443: 605 */     if (this._dom != null) {
/*  444: 606 */       this._dom.endDocument();
/*  445:     */     } else {
/*  446: 609 */       super.endDocument();
/*  447:     */     }
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void characters(String str)
/*  451:     */     throws SAXException
/*  452:     */   {
/*  453: 615 */     if (this._dom != null) {
/*  454: 616 */       characters(str.toCharArray(), 0, str.length());
/*  455:     */     } else {
/*  456: 619 */       super.characters(str);
/*  457:     */     }
/*  458:     */   }
/*  459:     */   
/*  460:     */   public void characters(char[] ch, int offset, int length)
/*  461:     */     throws SAXException
/*  462:     */   {
/*  463: 626 */     if (this._dom != null)
/*  464:     */     {
/*  465: 627 */       maybeEmitStartElement();
/*  466: 628 */       this._dom.characters(ch, offset, length);
/*  467:     */     }
/*  468:     */     else
/*  469:     */     {
/*  470: 631 */       super.characters(ch, offset, length);
/*  471:     */     }
/*  472:     */   }
/*  473:     */   
/*  474:     */   public boolean setEscaping(boolean escape)
/*  475:     */     throws SAXException
/*  476:     */   {
/*  477: 637 */     if (this._dom != null) {
/*  478: 638 */       return this._dom.setEscaping(escape);
/*  479:     */     }
/*  480: 641 */     return super.setEscaping(escape);
/*  481:     */   }
/*  482:     */   
/*  483:     */   public void startElement(String elementName)
/*  484:     */     throws SAXException
/*  485:     */   {
/*  486: 647 */     if (this._dom == null) {
/*  487: 648 */       prepareNewDOM();
/*  488:     */     }
/*  489: 651 */     maybeEmitStartElement();
/*  490: 652 */     this._openElementName = elementName;
/*  491: 653 */     this._attributes.clear();
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void startElement(String uri, String localName, String qName)
/*  495:     */     throws SAXException
/*  496:     */   {
/*  497: 659 */     startElement(qName);
/*  498:     */   }
/*  499:     */   
/*  500:     */   public void startElement(String uri, String localName, String qName, Attributes attributes)
/*  501:     */     throws SAXException
/*  502:     */   {
/*  503: 665 */     startElement(qName);
/*  504:     */   }
/*  505:     */   
/*  506:     */   public void endElement(String elementName)
/*  507:     */     throws SAXException
/*  508:     */   {
/*  509: 670 */     maybeEmitStartElement();
/*  510: 671 */     this._dom.endElement(null, null, elementName);
/*  511:     */   }
/*  512:     */   
/*  513:     */   public void endElement(String uri, String localName, String qName)
/*  514:     */     throws SAXException
/*  515:     */   {
/*  516: 677 */     endElement(qName);
/*  517:     */   }
/*  518:     */   
/*  519:     */   public void addUniqueAttribute(String qName, String value, int flags)
/*  520:     */     throws SAXException
/*  521:     */   {
/*  522: 683 */     addAttribute(qName, value);
/*  523:     */   }
/*  524:     */   
/*  525:     */   public void addAttribute(String name, String value)
/*  526:     */   {
/*  527: 688 */     if (this._openElementName != null) {
/*  528: 689 */       this._attributes.add(name, value);
/*  529:     */     } else {
/*  530: 692 */       BasisLibrary.runTimeError("STRAY_ATTRIBUTE_ERR", name);
/*  531:     */     }
/*  532:     */   }
/*  533:     */   
/*  534:     */   public void namespaceAfterStartElement(String prefix, String uri)
/*  535:     */     throws SAXException
/*  536:     */   {
/*  537: 699 */     if (this._dom == null) {
/*  538: 700 */       prepareNewDOM();
/*  539:     */     }
/*  540: 703 */     this._dom.startPrefixMapping(prefix, uri);
/*  541:     */   }
/*  542:     */   
/*  543:     */   public void comment(String comment)
/*  544:     */     throws SAXException
/*  545:     */   {
/*  546: 708 */     if (this._dom == null) {
/*  547: 709 */       prepareNewDOM();
/*  548:     */     }
/*  549: 712 */     maybeEmitStartElement();
/*  550: 713 */     char[] chars = comment.toCharArray();
/*  551: 714 */     this._dom.comment(chars, 0, chars.length);
/*  552:     */   }
/*  553:     */   
/*  554:     */   public void comment(char[] chars, int offset, int length)
/*  555:     */     throws SAXException
/*  556:     */   {
/*  557: 720 */     if (this._dom == null) {
/*  558: 721 */       prepareNewDOM();
/*  559:     */     }
/*  560: 724 */     maybeEmitStartElement();
/*  561: 725 */     this._dom.comment(chars, offset, length);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public void processingInstruction(String target, String data)
/*  565:     */     throws SAXException
/*  566:     */   {
/*  567: 731 */     if (this._dom == null) {
/*  568: 732 */       prepareNewDOM();
/*  569:     */     }
/*  570: 735 */     maybeEmitStartElement();
/*  571: 736 */     this._dom.processingInstruction(target, data);
/*  572:     */   }
/*  573:     */   
/*  574:     */   public void setFeature(String featureId, boolean state)
/*  575:     */   {
/*  576: 743 */     if (this._dom != null) {
/*  577: 744 */       this._dom.setFeature(featureId, state);
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void setProperty(String property, Object value)
/*  582:     */   {
/*  583: 750 */     if (this._dom != null) {
/*  584: 751 */       this._dom.setProperty(property, value);
/*  585:     */     }
/*  586:     */   }
/*  587:     */   
/*  588:     */   public DTMAxisTraverser getAxisTraverser(int axis)
/*  589:     */   {
/*  590: 757 */     if (this._dom != null) {
/*  591: 758 */       return this._dom.getAxisTraverser(axis);
/*  592:     */     }
/*  593: 761 */     return super.getAxisTraverser(axis);
/*  594:     */   }
/*  595:     */   
/*  596:     */   public boolean hasChildNodes(int nodeHandle)
/*  597:     */   {
/*  598: 767 */     if (this._dom != null) {
/*  599: 768 */       return this._dom.hasChildNodes(nodeHandle);
/*  600:     */     }
/*  601: 771 */     return super.hasChildNodes(nodeHandle);
/*  602:     */   }
/*  603:     */   
/*  604:     */   public int getFirstChild(int nodeHandle)
/*  605:     */   {
/*  606: 777 */     if (this._dom != null) {
/*  607: 778 */       return this._dom.getFirstChild(nodeHandle);
/*  608:     */     }
/*  609: 781 */     return super.getFirstChild(nodeHandle);
/*  610:     */   }
/*  611:     */   
/*  612:     */   public int getLastChild(int nodeHandle)
/*  613:     */   {
/*  614: 787 */     if (this._dom != null) {
/*  615: 788 */       return this._dom.getLastChild(nodeHandle);
/*  616:     */     }
/*  617: 791 */     return super.getLastChild(nodeHandle);
/*  618:     */   }
/*  619:     */   
/*  620:     */   public int getAttributeNode(int elementHandle, String namespaceURI, String name)
/*  621:     */   {
/*  622: 797 */     if (this._dom != null) {
/*  623: 798 */       return this._dom.getAttributeNode(elementHandle, namespaceURI, name);
/*  624:     */     }
/*  625: 801 */     return super.getAttributeNode(elementHandle, namespaceURI, name);
/*  626:     */   }
/*  627:     */   
/*  628:     */   public int getFirstAttribute(int nodeHandle)
/*  629:     */   {
/*  630: 807 */     if (this._dom != null) {
/*  631: 808 */       return this._dom.getFirstAttribute(nodeHandle);
/*  632:     */     }
/*  633: 811 */     return super.getFirstAttribute(nodeHandle);
/*  634:     */   }
/*  635:     */   
/*  636:     */   public int getFirstNamespaceNode(int nodeHandle, boolean inScope)
/*  637:     */   {
/*  638: 817 */     if (this._dom != null) {
/*  639: 818 */       return this._dom.getFirstNamespaceNode(nodeHandle, inScope);
/*  640:     */     }
/*  641: 821 */     return super.getFirstNamespaceNode(nodeHandle, inScope);
/*  642:     */   }
/*  643:     */   
/*  644:     */   public int getNextSibling(int nodeHandle)
/*  645:     */   {
/*  646: 827 */     if (this._dom != null) {
/*  647: 828 */       return this._dom.getNextSibling(nodeHandle);
/*  648:     */     }
/*  649: 831 */     return super.getNextSibling(nodeHandle);
/*  650:     */   }
/*  651:     */   
/*  652:     */   public int getPreviousSibling(int nodeHandle)
/*  653:     */   {
/*  654: 837 */     if (this._dom != null) {
/*  655: 838 */       return this._dom.getPreviousSibling(nodeHandle);
/*  656:     */     }
/*  657: 841 */     return super.getPreviousSibling(nodeHandle);
/*  658:     */   }
/*  659:     */   
/*  660:     */   public int getNextAttribute(int nodeHandle)
/*  661:     */   {
/*  662: 847 */     if (this._dom != null) {
/*  663: 848 */       return this._dom.getNextAttribute(nodeHandle);
/*  664:     */     }
/*  665: 851 */     return super.getNextAttribute(nodeHandle);
/*  666:     */   }
/*  667:     */   
/*  668:     */   public int getNextNamespaceNode(int baseHandle, int namespaceHandle, boolean inScope)
/*  669:     */   {
/*  670: 858 */     if (this._dom != null) {
/*  671: 859 */       return this._dom.getNextNamespaceNode(baseHandle, namespaceHandle, inScope);
/*  672:     */     }
/*  673: 862 */     return super.getNextNamespaceNode(baseHandle, namespaceHandle, inScope);
/*  674:     */   }
/*  675:     */   
/*  676:     */   public int getOwnerDocument(int nodeHandle)
/*  677:     */   {
/*  678: 868 */     if (this._dom != null) {
/*  679: 869 */       return this._dom.getOwnerDocument(nodeHandle);
/*  680:     */     }
/*  681: 872 */     return super.getOwnerDocument(nodeHandle);
/*  682:     */   }
/*  683:     */   
/*  684:     */   public int getDocumentRoot(int nodeHandle)
/*  685:     */   {
/*  686: 878 */     if (this._dom != null) {
/*  687: 879 */       return this._dom.getDocumentRoot(nodeHandle);
/*  688:     */     }
/*  689: 882 */     return super.getDocumentRoot(nodeHandle);
/*  690:     */   }
/*  691:     */   
/*  692:     */   public XMLString getStringValue(int nodeHandle)
/*  693:     */   {
/*  694: 888 */     if (this._dom != null) {
/*  695: 889 */       return this._dom.getStringValue(nodeHandle);
/*  696:     */     }
/*  697: 892 */     return super.getStringValue(nodeHandle);
/*  698:     */   }
/*  699:     */   
/*  700:     */   public int getStringValueChunkCount(int nodeHandle)
/*  701:     */   {
/*  702: 898 */     if (this._dom != null) {
/*  703: 899 */       return this._dom.getStringValueChunkCount(nodeHandle);
/*  704:     */     }
/*  705: 902 */     return super.getStringValueChunkCount(nodeHandle);
/*  706:     */   }
/*  707:     */   
/*  708:     */   public char[] getStringValueChunk(int nodeHandle, int chunkIndex, int[] startAndLen)
/*  709:     */   {
/*  710: 909 */     if (this._dom != null) {
/*  711: 910 */       return this._dom.getStringValueChunk(nodeHandle, chunkIndex, startAndLen);
/*  712:     */     }
/*  713: 913 */     return super.getStringValueChunk(nodeHandle, chunkIndex, startAndLen);
/*  714:     */   }
/*  715:     */   
/*  716:     */   public int getExpandedTypeID(String namespace, String localName, int type)
/*  717:     */   {
/*  718: 919 */     if (this._dom != null) {
/*  719: 920 */       return this._dom.getExpandedTypeID(namespace, localName, type);
/*  720:     */     }
/*  721: 923 */     return super.getExpandedTypeID(namespace, localName, type);
/*  722:     */   }
/*  723:     */   
/*  724:     */   public String getLocalNameFromExpandedNameID(int ExpandedNameID)
/*  725:     */   {
/*  726: 929 */     if (this._dom != null) {
/*  727: 930 */       return this._dom.getLocalNameFromExpandedNameID(ExpandedNameID);
/*  728:     */     }
/*  729: 933 */     return super.getLocalNameFromExpandedNameID(ExpandedNameID);
/*  730:     */   }
/*  731:     */   
/*  732:     */   public String getNamespaceFromExpandedNameID(int ExpandedNameID)
/*  733:     */   {
/*  734: 939 */     if (this._dom != null) {
/*  735: 940 */       return this._dom.getNamespaceFromExpandedNameID(ExpandedNameID);
/*  736:     */     }
/*  737: 943 */     return super.getNamespaceFromExpandedNameID(ExpandedNameID);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public String getLocalName(int nodeHandle)
/*  741:     */   {
/*  742: 949 */     if (this._dom != null) {
/*  743: 950 */       return this._dom.getLocalName(nodeHandle);
/*  744:     */     }
/*  745: 953 */     return super.getLocalName(nodeHandle);
/*  746:     */   }
/*  747:     */   
/*  748:     */   public String getPrefix(int nodeHandle)
/*  749:     */   {
/*  750: 959 */     if (this._dom != null) {
/*  751: 960 */       return this._dom.getPrefix(nodeHandle);
/*  752:     */     }
/*  753: 963 */     return super.getPrefix(nodeHandle);
/*  754:     */   }
/*  755:     */   
/*  756:     */   public String getNamespaceURI(int nodeHandle)
/*  757:     */   {
/*  758: 969 */     if (this._dom != null) {
/*  759: 970 */       return this._dom.getNamespaceURI(nodeHandle);
/*  760:     */     }
/*  761: 973 */     return super.getNamespaceURI(nodeHandle);
/*  762:     */   }
/*  763:     */   
/*  764:     */   public String getNodeValue(int nodeHandle)
/*  765:     */   {
/*  766: 979 */     if (this._dom != null) {
/*  767: 980 */       return this._dom.getNodeValue(nodeHandle);
/*  768:     */     }
/*  769: 983 */     return super.getNodeValue(nodeHandle);
/*  770:     */   }
/*  771:     */   
/*  772:     */   public short getNodeType(int nodeHandle)
/*  773:     */   {
/*  774: 989 */     if (this._dom != null) {
/*  775: 990 */       return this._dom.getNodeType(nodeHandle);
/*  776:     */     }
/*  777: 993 */     return super.getNodeType(nodeHandle);
/*  778:     */   }
/*  779:     */   
/*  780:     */   public short getLevel(int nodeHandle)
/*  781:     */   {
/*  782: 999 */     if (this._dom != null) {
/*  783:1000 */       return this._dom.getLevel(nodeHandle);
/*  784:     */     }
/*  785:1003 */     return super.getLevel(nodeHandle);
/*  786:     */   }
/*  787:     */   
/*  788:     */   public boolean isSupported(String feature, String version)
/*  789:     */   {
/*  790:1009 */     if (this._dom != null) {
/*  791:1010 */       return this._dom.isSupported(feature, version);
/*  792:     */     }
/*  793:1013 */     return super.isSupported(feature, version);
/*  794:     */   }
/*  795:     */   
/*  796:     */   public String getDocumentBaseURI()
/*  797:     */   {
/*  798:1019 */     if (this._dom != null) {
/*  799:1020 */       return this._dom.getDocumentBaseURI();
/*  800:     */     }
/*  801:1023 */     return super.getDocumentBaseURI();
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void setDocumentBaseURI(String baseURI)
/*  805:     */   {
/*  806:1029 */     if (this._dom != null) {
/*  807:1030 */       this._dom.setDocumentBaseURI(baseURI);
/*  808:     */     } else {
/*  809:1033 */       super.setDocumentBaseURI(baseURI);
/*  810:     */     }
/*  811:     */   }
/*  812:     */   
/*  813:     */   public String getDocumentSystemIdentifier(int nodeHandle)
/*  814:     */   {
/*  815:1039 */     if (this._dom != null) {
/*  816:1040 */       return this._dom.getDocumentSystemIdentifier(nodeHandle);
/*  817:     */     }
/*  818:1043 */     return super.getDocumentSystemIdentifier(nodeHandle);
/*  819:     */   }
/*  820:     */   
/*  821:     */   public String getDocumentEncoding(int nodeHandle)
/*  822:     */   {
/*  823:1049 */     if (this._dom != null) {
/*  824:1050 */       return this._dom.getDocumentEncoding(nodeHandle);
/*  825:     */     }
/*  826:1053 */     return super.getDocumentEncoding(nodeHandle);
/*  827:     */   }
/*  828:     */   
/*  829:     */   public String getDocumentStandalone(int nodeHandle)
/*  830:     */   {
/*  831:1059 */     if (this._dom != null) {
/*  832:1060 */       return this._dom.getDocumentStandalone(nodeHandle);
/*  833:     */     }
/*  834:1063 */     return super.getDocumentStandalone(nodeHandle);
/*  835:     */   }
/*  836:     */   
/*  837:     */   public String getDocumentVersion(int documentHandle)
/*  838:     */   {
/*  839:1069 */     if (this._dom != null) {
/*  840:1070 */       return this._dom.getDocumentVersion(documentHandle);
/*  841:     */     }
/*  842:1073 */     return super.getDocumentVersion(documentHandle);
/*  843:     */   }
/*  844:     */   
/*  845:     */   public boolean getDocumentAllDeclarationsProcessed()
/*  846:     */   {
/*  847:1079 */     if (this._dom != null) {
/*  848:1080 */       return this._dom.getDocumentAllDeclarationsProcessed();
/*  849:     */     }
/*  850:1083 */     return super.getDocumentAllDeclarationsProcessed();
/*  851:     */   }
/*  852:     */   
/*  853:     */   public String getDocumentTypeDeclarationSystemIdentifier()
/*  854:     */   {
/*  855:1089 */     if (this._dom != null) {
/*  856:1090 */       return this._dom.getDocumentTypeDeclarationSystemIdentifier();
/*  857:     */     }
/*  858:1093 */     return super.getDocumentTypeDeclarationSystemIdentifier();
/*  859:     */   }
/*  860:     */   
/*  861:     */   public String getDocumentTypeDeclarationPublicIdentifier()
/*  862:     */   {
/*  863:1099 */     if (this._dom != null) {
/*  864:1100 */       return this._dom.getDocumentTypeDeclarationPublicIdentifier();
/*  865:     */     }
/*  866:1103 */     return super.getDocumentTypeDeclarationPublicIdentifier();
/*  867:     */   }
/*  868:     */   
/*  869:     */   public int getElementById(String elementId)
/*  870:     */   {
/*  871:1109 */     if (this._dom != null) {
/*  872:1110 */       return this._dom.getElementById(elementId);
/*  873:     */     }
/*  874:1113 */     return super.getElementById(elementId);
/*  875:     */   }
/*  876:     */   
/*  877:     */   public boolean supportsPreStripping()
/*  878:     */   {
/*  879:1119 */     if (this._dom != null) {
/*  880:1120 */       return this._dom.supportsPreStripping();
/*  881:     */     }
/*  882:1123 */     return super.supportsPreStripping();
/*  883:     */   }
/*  884:     */   
/*  885:     */   public boolean isNodeAfter(int firstNodeHandle, int secondNodeHandle)
/*  886:     */   {
/*  887:1129 */     if (this._dom != null) {
/*  888:1130 */       return this._dom.isNodeAfter(firstNodeHandle, secondNodeHandle);
/*  889:     */     }
/*  890:1133 */     return super.isNodeAfter(firstNodeHandle, secondNodeHandle);
/*  891:     */   }
/*  892:     */   
/*  893:     */   public boolean isCharacterElementContentWhitespace(int nodeHandle)
/*  894:     */   {
/*  895:1139 */     if (this._dom != null) {
/*  896:1140 */       return this._dom.isCharacterElementContentWhitespace(nodeHandle);
/*  897:     */     }
/*  898:1143 */     return super.isCharacterElementContentWhitespace(nodeHandle);
/*  899:     */   }
/*  900:     */   
/*  901:     */   public boolean isDocumentAllDeclarationsProcessed(int documentHandle)
/*  902:     */   {
/*  903:1149 */     if (this._dom != null) {
/*  904:1150 */       return this._dom.isDocumentAllDeclarationsProcessed(documentHandle);
/*  905:     */     }
/*  906:1153 */     return super.isDocumentAllDeclarationsProcessed(documentHandle);
/*  907:     */   }
/*  908:     */   
/*  909:     */   public boolean isAttributeSpecified(int attributeHandle)
/*  910:     */   {
/*  911:1159 */     if (this._dom != null) {
/*  912:1160 */       return this._dom.isAttributeSpecified(attributeHandle);
/*  913:     */     }
/*  914:1163 */     return super.isAttributeSpecified(attributeHandle);
/*  915:     */   }
/*  916:     */   
/*  917:     */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize)
/*  918:     */     throws SAXException
/*  919:     */   {
/*  920:1171 */     if (this._dom != null) {
/*  921:1172 */       this._dom.dispatchCharactersEvents(nodeHandle, ch, normalize);
/*  922:     */     } else {
/*  923:1175 */       super.dispatchCharactersEvents(nodeHandle, ch, normalize);
/*  924:     */     }
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void dispatchToEvents(int nodeHandle, ContentHandler ch)
/*  928:     */     throws SAXException
/*  929:     */   {
/*  930:1182 */     if (this._dom != null) {
/*  931:1183 */       this._dom.dispatchToEvents(nodeHandle, ch);
/*  932:     */     } else {
/*  933:1186 */       super.dispatchToEvents(nodeHandle, ch);
/*  934:     */     }
/*  935:     */   }
/*  936:     */   
/*  937:     */   public Node getNode(int nodeHandle)
/*  938:     */   {
/*  939:1192 */     if (this._dom != null) {
/*  940:1193 */       return this._dom.getNode(nodeHandle);
/*  941:     */     }
/*  942:1196 */     return super.getNode(nodeHandle);
/*  943:     */   }
/*  944:     */   
/*  945:     */   public boolean needsTwoThreads()
/*  946:     */   {
/*  947:1202 */     if (this._dom != null) {
/*  948:1203 */       return this._dom.needsTwoThreads();
/*  949:     */     }
/*  950:1206 */     return super.needsTwoThreads();
/*  951:     */   }
/*  952:     */   
/*  953:     */   public ContentHandler getContentHandler()
/*  954:     */   {
/*  955:1212 */     if (this._dom != null) {
/*  956:1213 */       return this._dom.getContentHandler();
/*  957:     */     }
/*  958:1216 */     return super.getContentHandler();
/*  959:     */   }
/*  960:     */   
/*  961:     */   public LexicalHandler getLexicalHandler()
/*  962:     */   {
/*  963:1222 */     if (this._dom != null) {
/*  964:1223 */       return this._dom.getLexicalHandler();
/*  965:     */     }
/*  966:1226 */     return super.getLexicalHandler();
/*  967:     */   }
/*  968:     */   
/*  969:     */   public EntityResolver getEntityResolver()
/*  970:     */   {
/*  971:1232 */     if (this._dom != null) {
/*  972:1233 */       return this._dom.getEntityResolver();
/*  973:     */     }
/*  974:1236 */     return super.getEntityResolver();
/*  975:     */   }
/*  976:     */   
/*  977:     */   public DTDHandler getDTDHandler()
/*  978:     */   {
/*  979:1242 */     if (this._dom != null) {
/*  980:1243 */       return this._dom.getDTDHandler();
/*  981:     */     }
/*  982:1246 */     return super.getDTDHandler();
/*  983:     */   }
/*  984:     */   
/*  985:     */   public ErrorHandler getErrorHandler()
/*  986:     */   {
/*  987:1252 */     if (this._dom != null) {
/*  988:1253 */       return this._dom.getErrorHandler();
/*  989:     */     }
/*  990:1256 */     return super.getErrorHandler();
/*  991:     */   }
/*  992:     */   
/*  993:     */   public DeclHandler getDeclHandler()
/*  994:     */   {
/*  995:1262 */     if (this._dom != null) {
/*  996:1263 */       return this._dom.getDeclHandler();
/*  997:     */     }
/*  998:1266 */     return super.getDeclHandler();
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public void appendChild(int newChild, boolean clone, boolean cloneDepth)
/* 1002:     */   {
/* 1003:1272 */     if (this._dom != null) {
/* 1004:1273 */       this._dom.appendChild(newChild, clone, cloneDepth);
/* 1005:     */     } else {
/* 1006:1276 */       super.appendChild(newChild, clone, cloneDepth);
/* 1007:     */     }
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public void appendTextChild(String str)
/* 1011:     */   {
/* 1012:1282 */     if (this._dom != null) {
/* 1013:1283 */       this._dom.appendTextChild(str);
/* 1014:     */     } else {
/* 1015:1286 */       super.appendTextChild(str);
/* 1016:     */     }
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public SourceLocator getSourceLocatorFor(int node)
/* 1020:     */   {
/* 1021:1292 */     if (this._dom != null) {
/* 1022:1293 */       return this._dom.getSourceLocatorFor(node);
/* 1023:     */     }
/* 1024:1296 */     return super.getSourceLocatorFor(node);
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public void documentRegistration()
/* 1028:     */   {
/* 1029:1302 */     if (this._dom != null) {
/* 1030:1303 */       this._dom.documentRegistration();
/* 1031:     */     } else {
/* 1032:1306 */       super.documentRegistration();
/* 1033:     */     }
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void documentRelease()
/* 1037:     */   {
/* 1038:1312 */     if (this._dom != null) {
/* 1039:1313 */       this._dom.documentRelease();
/* 1040:     */     } else {
/* 1041:1316 */       super.documentRelease();
/* 1042:     */     }
/* 1043:     */   }
/* 1044:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.AdaptiveResultTreeImpl
 * JD-Core Version:    0.7.0.1
 */