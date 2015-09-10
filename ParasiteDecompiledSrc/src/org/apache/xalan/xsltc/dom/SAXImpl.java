/*    1:     */ package org.apache.xalan.xsltc.dom;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.util.Enumeration;
/*    5:     */ import javax.xml.transform.Source;
/*    6:     */ import javax.xml.transform.dom.DOMSource;
/*    7:     */ import org.apache.xalan.xsltc.DOM;
/*    8:     */ import org.apache.xalan.xsltc.DOMEnhancedForDTM;
/*    9:     */ import org.apache.xalan.xsltc.StripFilter;
/*   10:     */ import org.apache.xalan.xsltc.TransletException;
/*   11:     */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   12:     */ import org.apache.xml.dtm.Axis;
/*   13:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*   14:     */ import org.apache.xml.dtm.DTMManager;
/*   15:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   16:     */ import org.apache.xml.dtm.ref.DTMAxisIterNodeList;
/*   17:     */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   18:     */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   19:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.InternalAxisIteratorBase;
/*   20:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.NamespaceIterator;
/*   21:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.NthDescendantIterator;
/*   22:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.RootIterator;
/*   23:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.SingletonIterator;
/*   24:     */ import org.apache.xml.dtm.ref.DTMManagerDefault;
/*   25:     */ import org.apache.xml.dtm.ref.DTMNodeProxy;
/*   26:     */ import org.apache.xml.dtm.ref.EmptyIterator;
/*   27:     */ import org.apache.xml.dtm.ref.ExpandedNameTable;
/*   28:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM;
/*   29:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2;
/*   30:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.AncestorIterator;
/*   31:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.AttributeIterator;
/*   32:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.ChildrenIterator;
/*   33:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.DescendantIterator;
/*   34:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.FollowingIterator;
/*   35:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.FollowingSiblingIterator;
/*   36:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.ParentIterator;
/*   37:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.PrecedingIterator;
/*   38:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.PrecedingSiblingIterator;
/*   39:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedAncestorIterator;
/*   40:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedAttributeIterator;
/*   41:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedChildrenIterator;
/*   42:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedDescendantIterator;
/*   43:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedFollowingIterator;
/*   44:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedFollowingSiblingIterator;
/*   45:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedPrecedingIterator;
/*   46:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedPrecedingSiblingIterator;
/*   47:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedRootIterator;
/*   48:     */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM2.TypedSingletonIterator;
/*   49:     */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   50:     */ import org.apache.xml.serializer.ExtendedLexicalHandler;
/*   51:     */ import org.apache.xml.serializer.SerializationHandler;
/*   52:     */ import org.apache.xml.serializer.ToXMLSAXHandler;
/*   53:     */ import org.apache.xml.utils.IntStack;
/*   54:     */ import org.apache.xml.utils.SystemIDResolver;
/*   55:     */ import org.apache.xml.utils.XMLStringFactory;
/*   56:     */ import org.w3c.dom.Document;
/*   57:     */ import org.w3c.dom.DocumentType;
/*   58:     */ import org.w3c.dom.Entity;
/*   59:     */ import org.w3c.dom.NamedNodeMap;
/*   60:     */ import org.w3c.dom.Node;
/*   61:     */ import org.w3c.dom.NodeList;
/*   62:     */ import org.xml.sax.Attributes;
/*   63:     */ import org.xml.sax.ContentHandler;
/*   64:     */ import org.xml.sax.SAXException;
/*   65:     */ 
/*   66:     */ public final class SAXImpl
/*   67:     */   extends SAX2DTM2
/*   68:     */   implements DOMEnhancedForDTM, DOMBuilder
/*   69:     */ {
/*   70:  87 */   private int _uriCount = 0;
/*   71:  88 */   private int _prefixCount = 0;
/*   72:     */   private int[] _xmlSpaceStack;
/*   73:  93 */   private int _idx = 1;
/*   74:  94 */   private boolean _preserve = false;
/*   75:     */   private static final String XML_STRING = "xml:";
/*   76:     */   private static final String XML_PREFIX = "xml";
/*   77:     */   private static final String XMLSPACE_STRING = "xml:space";
/*   78:     */   private static final String PRESERVE_STRING = "preserve";
/*   79:     */   private static final String XMLNS_PREFIX = "xmlns";
/*   80:     */   private static final String XML_URI = "http://www.w3.org/XML/1998/namespace";
/*   81: 103 */   private boolean _escaping = true;
/*   82: 104 */   private boolean _disableEscaping = false;
/*   83: 105 */   private int _textNodeToProcess = -1;
/*   84:     */   private static final String EMPTYSTRING = "";
/*   85: 115 */   private static final DTMAxisIterator EMPTYITERATOR = ;
/*   86: 117 */   private int _namesSize = -1;
/*   87: 120 */   private org.apache.xalan.xsltc.runtime.Hashtable _nsIndex = new org.apache.xalan.xsltc.runtime.Hashtable();
/*   88: 123 */   private int _size = 0;
/*   89: 126 */   private BitArray _dontEscape = null;
/*   90: 129 */   private String _documentURI = null;
/*   91: 130 */   private static int _documentURIIndex = 0;
/*   92:     */   private Document _document;
/*   93: 138 */   private org.apache.xalan.xsltc.runtime.Hashtable _node2Ids = null;
/*   94: 141 */   private boolean _hasDOMSource = false;
/*   95:     */   private XSLTCDTMManager _dtmManager;
/*   96:     */   private Node[] _nodes;
/*   97:     */   private NodeList[] _nodeLists;
/*   98:     */   private static final String XML_LANG_ATTRIBUTE = "http://www.w3.org/XML/1998/namespace:@lang";
/*   99:     */   
/*  100:     */   public void setDocumentURI(String uri)
/*  101:     */   {
/*  102: 156 */     if (uri != null) {
/*  103: 157 */       setDocumentBaseURI(SystemIDResolver.getAbsoluteURI(uri));
/*  104:     */     }
/*  105:     */   }
/*  106:     */   
/*  107:     */   public String getDocumentURI()
/*  108:     */   {
/*  109: 165 */     String baseURI = getDocumentBaseURI();
/*  110: 166 */     return "rtf" + _documentURIIndex++;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public String getDocumentURI(int node)
/*  114:     */   {
/*  115: 170 */     return getDocumentURI();
/*  116:     */   }
/*  117:     */   
/*  118:     */   public void setupMapping(String[] names, String[] urisArray, int[] typesArray, String[] namespaces) {}
/*  119:     */   
/*  120:     */   public String lookupNamespace(int node, String prefix)
/*  121:     */     throws TransletException
/*  122:     */   {
/*  123: 187 */     SAX2DTM2.AncestorIterator ancestors = new SAX2DTM2.AncestorIterator(this);
/*  124: 189 */     if (isElement(node)) {
/*  125: 190 */       ancestors.includeSelf();
/*  126:     */     }
/*  127: 193 */     ancestors.setStartNode(node);
/*  128:     */     DTMDefaultBaseIterators.NamespaceIterator namespaces;
/*  129:     */     int nsnode;
/*  130:     */     int anode;
/*  131: 194 */     for (; (anode = ancestors.next()) != -1; (nsnode = namespaces.next()) != -1)
/*  132:     */     {
/*  133: 195 */       namespaces = new DTMDefaultBaseIterators.NamespaceIterator(this);
/*  134:     */       int i;
/*  135: 197 */       namespaces.setStartNode(i);
/*  136: 198 */       continue;
/*  137:     */       int j;
/*  138: 199 */       if (getLocalName(j).equals(prefix)) {
/*  139: 200 */         return getNodeValue(j);
/*  140:     */       }
/*  141:     */     }
/*  142: 205 */     BasisLibrary.runTimeError("NAMESPACE_PREFIX_ERR", prefix);
/*  143: 206 */     return null;
/*  144:     */   }
/*  145:     */   
/*  146:     */   public boolean isElement(int node)
/*  147:     */   {
/*  148: 213 */     return getNodeType(node) == 1;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public boolean isAttribute(int node)
/*  152:     */   {
/*  153: 220 */     return getNodeType(node) == 2;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public int getSize()
/*  157:     */   {
/*  158: 227 */     return getNumberOfNodes();
/*  159:     */   }
/*  160:     */   
/*  161:     */   public void setFilter(StripFilter filter) {}
/*  162:     */   
/*  163:     */   public boolean lessThan(int node1, int node2)
/*  164:     */   {
/*  165: 241 */     if (node1 == -1) {
/*  166: 242 */       return false;
/*  167:     */     }
/*  168: 245 */     if (node2 == -1) {
/*  169: 246 */       return true;
/*  170:     */     }
/*  171: 249 */     return node1 < node2;
/*  172:     */   }
/*  173:     */   
/*  174:     */   public Node makeNode(int index)
/*  175:     */   {
/*  176: 256 */     if (this._nodes == null) {
/*  177: 257 */       this._nodes = new Node[this._namesSize];
/*  178:     */     }
/*  179: 260 */     int nodeID = makeNodeIdentity(index);
/*  180: 261 */     if (nodeID < 0) {
/*  181: 262 */       return null;
/*  182:     */     }
/*  183: 264 */     if (nodeID < this._nodes.length) {
/*  184: 265 */       return this._nodes[nodeID] = (this._nodes[nodeID] != null ? this._nodes[nodeID] : ) = new DTMNodeProxy(this, index);
/*  185:     */     }
/*  186: 269 */     return new DTMNodeProxy(this, index);
/*  187:     */   }
/*  188:     */   
/*  189:     */   public Node makeNode(DTMAxisIterator iter)
/*  190:     */   {
/*  191: 278 */     return makeNode(iter.next());
/*  192:     */   }
/*  193:     */   
/*  194:     */   public NodeList makeNodeList(int index)
/*  195:     */   {
/*  196: 285 */     if (this._nodeLists == null) {
/*  197: 286 */       this._nodeLists = new NodeList[this._namesSize];
/*  198:     */     }
/*  199: 289 */     int nodeID = makeNodeIdentity(index);
/*  200: 290 */     if (nodeID < 0) {
/*  201: 291 */       return null;
/*  202:     */     }
/*  203: 293 */     if (nodeID < this._nodeLists.length) {
/*  204: 294 */       return this._nodeLists[nodeID] = (this._nodeLists[nodeID] != null ? this._nodeLists[nodeID] : ) = new DTMAxisIterNodeList(this, new DTMDefaultBaseIterators.SingletonIterator(this, index));
/*  205:     */     }
/*  206: 299 */     return new DTMAxisIterNodeList(this, new DTMDefaultBaseIterators.SingletonIterator(this, index));
/*  207:     */   }
/*  208:     */   
/*  209:     */   public NodeList makeNodeList(DTMAxisIterator iter)
/*  210:     */   {
/*  211: 308 */     return new DTMAxisIterNodeList(this, iter);
/*  212:     */   }
/*  213:     */   
/*  214:     */   public class TypedNamespaceIterator
/*  215:     */     extends DTMDefaultBaseIterators.NamespaceIterator
/*  216:     */   {
/*  217:     */     private String _nsPrefix;
/*  218:     */     
/*  219:     */     public TypedNamespaceIterator(int nodeType)
/*  220:     */     {
/*  221: 326 */       super();
/*  222: 327 */       if (SAXImpl.access$001(SAXImpl.this) != null) {
/*  223: 328 */         this._nsPrefix = SAXImpl.access$101(SAXImpl.this).getLocalName(nodeType);
/*  224:     */       }
/*  225:     */     }
/*  226:     */     
/*  227:     */     public int next()
/*  228:     */     {
/*  229: 338 */       if ((this._nsPrefix == null) || (this._nsPrefix.length() == 0)) {
/*  230: 339 */         return -1;
/*  231:     */       }
/*  232: 341 */       int node = -1;
/*  233: 342 */       for (node = super.next(); node != -1; node = super.next()) {
/*  234: 343 */         if (this._nsPrefix.compareTo(SAXImpl.this.getLocalName(node)) == 0) {
/*  235: 344 */           return returnNode(node);
/*  236:     */         }
/*  237:     */       }
/*  238: 347 */       return -1;
/*  239:     */     }
/*  240:     */   }
/*  241:     */   
/*  242:     */   private final class NodeValueIterator
/*  243:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  244:     */   {
/*  245:     */     private DTMAxisIterator _source;
/*  246:     */     private String _value;
/*  247:     */     private boolean _op;
/*  248:     */     private final boolean _isReverse;
/*  249: 364 */     private int _returnType = 1;
/*  250:     */     
/*  251:     */     public NodeValueIterator(DTMAxisIterator source, int returnType, String value, boolean op)
/*  252:     */     {
/*  253: 368 */       super();
/*  254: 369 */       this._source = source;
/*  255: 370 */       this._returnType = returnType;
/*  256: 371 */       this._value = value;
/*  257: 372 */       this._op = op;
/*  258: 373 */       this._isReverse = source.isReverse();
/*  259:     */     }
/*  260:     */     
/*  261:     */     public boolean isReverse()
/*  262:     */     {
/*  263: 378 */       return this._isReverse;
/*  264:     */     }
/*  265:     */     
/*  266:     */     public DTMAxisIterator cloneIterator()
/*  267:     */     {
/*  268:     */       try
/*  269:     */       {
/*  270: 384 */         NodeValueIterator clone = (NodeValueIterator)super.clone();
/*  271: 385 */         clone._isRestartable = false;
/*  272: 386 */         clone._source = this._source.cloneIterator();
/*  273: 387 */         clone._value = this._value;
/*  274: 388 */         clone._op = this._op;
/*  275: 389 */         return clone.reset();
/*  276:     */       }
/*  277:     */       catch (CloneNotSupportedException e)
/*  278:     */       {
/*  279: 392 */         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  280:     */       }
/*  281: 394 */       return null;
/*  282:     */     }
/*  283:     */     
/*  284:     */     public void setRestartable(boolean isRestartable)
/*  285:     */     {
/*  286: 400 */       this._isRestartable = isRestartable;
/*  287: 401 */       this._source.setRestartable(isRestartable);
/*  288:     */     }
/*  289:     */     
/*  290:     */     public DTMAxisIterator reset()
/*  291:     */     {
/*  292: 406 */       this._source.reset();
/*  293: 407 */       return resetPosition();
/*  294:     */     }
/*  295:     */     
/*  296:     */     public int next()
/*  297:     */     {
/*  298:     */       int node;
/*  299: 413 */       while ((node = this._source.next()) != -1)
/*  300:     */       {
/*  301:     */         int i;
/*  302: 414 */         String val = SAXImpl.this.getStringValueX(i);
/*  303: 415 */         if (this._value.equals(val) == this._op)
/*  304:     */         {
/*  305: 416 */           if (this._returnType == 0) {
/*  306: 417 */             return returnNode(i);
/*  307:     */           }
/*  308: 420 */           return returnNode(SAXImpl.this.getParent(i));
/*  309:     */         }
/*  310:     */       }
/*  311: 424 */       return -1;
/*  312:     */     }
/*  313:     */     
/*  314:     */     public DTMAxisIterator setStartNode(int node)
/*  315:     */     {
/*  316: 429 */       if (this._isRestartable)
/*  317:     */       {
/*  318: 430 */         this._source.setStartNode(this._startNode = node);
/*  319: 431 */         return resetPosition();
/*  320:     */       }
/*  321: 433 */       return this;
/*  322:     */     }
/*  323:     */     
/*  324:     */     public void setMark()
/*  325:     */     {
/*  326: 438 */       this._source.setMark();
/*  327:     */     }
/*  328:     */     
/*  329:     */     public void gotoMark()
/*  330:     */     {
/*  331: 443 */       this._source.gotoMark();
/*  332:     */     }
/*  333:     */   }
/*  334:     */   
/*  335:     */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iterator, int type, String value, boolean op)
/*  336:     */   {
/*  337: 450 */     return new NodeValueIterator(iterator, type, value, op);
/*  338:     */   }
/*  339:     */   
/*  340:     */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node)
/*  341:     */   {
/*  342: 458 */     return new DupFilterIterator(source);
/*  343:     */   }
/*  344:     */   
/*  345:     */   public DTMAxisIterator getIterator()
/*  346:     */   {
/*  347: 468 */     return new DTMDefaultBaseIterators.SingletonIterator(this, getDocument(), true);
/*  348:     */   }
/*  349:     */   
/*  350:     */   public int getNSType(int node)
/*  351:     */   {
/*  352: 476 */     String s = getNamespaceURI(node);
/*  353: 477 */     if (s == null) {
/*  354: 478 */       return 0;
/*  355:     */     }
/*  356: 480 */     int eType = getIdForNamespace(s);
/*  357: 481 */     return ((Integer)this._nsIndex.get(new Integer(eType))).intValue();
/*  358:     */   }
/*  359:     */   
/*  360:     */   public int getNamespaceType(int node)
/*  361:     */   {
/*  362: 491 */     return super.getNamespaceType(node);
/*  363:     */   }
/*  364:     */   
/*  365:     */   private int[] setupMapping(String[] names, String[] uris, int[] types, int nNames)
/*  366:     */   {
/*  367: 500 */     int[] result = new int[this.m_expandedNameTable.getSize()];
/*  368: 501 */     for (int i = 0; i < nNames; i++)
/*  369:     */     {
/*  370: 503 */       int type = this.m_expandedNameTable.getExpandedTypeID(uris[i], names[i], types[i], false);
/*  371: 504 */       result[type] = type;
/*  372:     */     }
/*  373: 506 */     return result;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public int getGeneralizedType(String name)
/*  377:     */   {
/*  378: 513 */     return getGeneralizedType(name, true);
/*  379:     */   }
/*  380:     */   
/*  381:     */   public int getGeneralizedType(String name, boolean searchOnly)
/*  382:     */   {
/*  383: 520 */     String ns = null;
/*  384: 521 */     int index = -1;
/*  385: 525 */     if ((index = name.lastIndexOf(":")) > -1) {
/*  386: 526 */       ns = name.substring(0, index);
/*  387:     */     }
/*  388: 531 */     int lNameStartIdx = index + 1;
/*  389:     */     int code;
/*  390: 535 */     if (name.charAt(lNameStartIdx) == '@')
/*  391:     */     {
/*  392: 536 */       code = 2;
/*  393: 537 */       lNameStartIdx++;
/*  394:     */     }
/*  395:     */     else
/*  396:     */     {
/*  397: 540 */       code = 1;
/*  398:     */     }
/*  399: 544 */     String lName = lNameStartIdx == 0 ? name : name.substring(lNameStartIdx);
/*  400:     */     
/*  401: 546 */     return this.m_expandedNameTable.getExpandedTypeID(ns, lName, code, searchOnly);
/*  402:     */   }
/*  403:     */   
/*  404:     */   public short[] getMapping(String[] names, String[] uris, int[] types)
/*  405:     */   {
/*  406: 556 */     if (this._namesSize < 0) {
/*  407: 557 */       return getMapping2(names, uris, types);
/*  408:     */     }
/*  409: 561 */     int namesLength = names.length;
/*  410: 562 */     int exLength = this.m_expandedNameTable.getSize();
/*  411:     */     
/*  412: 564 */     short[] result = new short[exLength];
/*  413: 567 */     for (int i = 0; i < 14; i++) {
/*  414: 568 */       result[i] = ((short)i);
/*  415:     */     }
/*  416: 571 */     for (i = 14; i < exLength; i++) {
/*  417: 572 */       result[i] = this.m_expandedNameTable.getType(i);
/*  418:     */     }
/*  419: 576 */     for (i = 0; i < namesLength; i++)
/*  420:     */     {
/*  421: 577 */       int genType = this.m_expandedNameTable.getExpandedTypeID(uris[i], names[i], types[i], true);
/*  422: 581 */       if ((genType >= 0) && (genType < exLength)) {
/*  423: 582 */         result[genType] = ((short)(i + 14));
/*  424:     */       }
/*  425:     */     }
/*  426: 586 */     return result;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public int[] getReverseMapping(String[] names, String[] uris, int[] types)
/*  430:     */   {
/*  431: 595 */     int[] result = new int[names.length + 14];
/*  432: 598 */     for (int i = 0; i < 14; i++) {
/*  433: 599 */       result[i] = i;
/*  434:     */     }
/*  435: 603 */     for (i = 0; i < names.length; i++)
/*  436:     */     {
/*  437: 604 */       int type = this.m_expandedNameTable.getExpandedTypeID(uris[i], names[i], types[i], true);
/*  438: 605 */       result[(i + 14)] = type;
/*  439:     */     }
/*  440: 607 */     return result;
/*  441:     */   }
/*  442:     */   
/*  443:     */   private short[] getMapping2(String[] names, String[] uris, int[] types)
/*  444:     */   {
/*  445: 617 */     int namesLength = names.length;
/*  446: 618 */     int exLength = this.m_expandedNameTable.getSize();
/*  447: 619 */     int[] generalizedTypes = null;
/*  448: 620 */     if (namesLength > 0) {
/*  449: 621 */       generalizedTypes = new int[namesLength];
/*  450:     */     }
/*  451: 624 */     int resultLength = exLength;
/*  452: 626 */     for (int i = 0; i < namesLength; i++)
/*  453:     */     {
/*  454: 631 */       generalizedTypes[i] = this.m_expandedNameTable.getExpandedTypeID(uris[i], names[i], types[i], false);
/*  455: 636 */       if ((this._namesSize < 0) && (generalizedTypes[i] >= resultLength)) {
/*  456: 637 */         resultLength = generalizedTypes[i] + 1;
/*  457:     */       }
/*  458:     */     }
/*  459: 641 */     short[] result = new short[resultLength];
/*  460: 644 */     for (i = 0; i < 14; i++) {
/*  461: 645 */       result[i] = ((short)i);
/*  462:     */     }
/*  463: 648 */     for (i = 14; i < exLength; i++) {
/*  464: 649 */       result[i] = this.m_expandedNameTable.getType(i);
/*  465:     */     }
/*  466: 653 */     for (i = 0; i < namesLength; i++)
/*  467:     */     {
/*  468: 654 */       int genType = generalizedTypes[i];
/*  469: 655 */       if ((genType >= 0) && (genType < resultLength)) {
/*  470: 656 */         result[genType] = ((short)(i + 14));
/*  471:     */       }
/*  472:     */     }
/*  473: 660 */     return result;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public short[] getNamespaceMapping(String[] namespaces)
/*  477:     */   {
/*  478: 668 */     int nsLength = namespaces.length;
/*  479: 669 */     int mappingLength = this._uriCount;
/*  480:     */     
/*  481: 671 */     short[] result = new short[mappingLength];
/*  482: 674 */     for (int i = 0; i < mappingLength; i++) {
/*  483: 675 */       result[i] = -1;
/*  484:     */     }
/*  485: 678 */     for (i = 0; i < nsLength; i++)
/*  486:     */     {
/*  487: 679 */       int eType = getIdForNamespace(namespaces[i]);
/*  488: 680 */       Integer type = (Integer)this._nsIndex.get(new Integer(eType));
/*  489: 681 */       if (type != null) {
/*  490: 682 */         result[type.intValue()] = ((short)i);
/*  491:     */       }
/*  492:     */     }
/*  493: 686 */     return result;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public short[] getReverseNamespaceMapping(String[] namespaces)
/*  497:     */   {
/*  498: 695 */     int length = namespaces.length;
/*  499: 696 */     short[] result = new short[length];
/*  500: 698 */     for (int i = 0; i < length; i++)
/*  501:     */     {
/*  502: 699 */       int eType = getIdForNamespace(namespaces[i]);
/*  503: 700 */       Integer type = (Integer)this._nsIndex.get(new Integer(eType));
/*  504: 701 */       result[i] = (type == null ? -1 : type.shortValue());
/*  505:     */     }
/*  506: 704 */     return result;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public SAXImpl(XSLTCDTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, boolean buildIdIndex)
/*  510:     */   {
/*  511: 715 */     this(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, 512, buildIdIndex, false);
/*  512:     */   }
/*  513:     */   
/*  514:     */   public SAXImpl(XSLTCDTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean buildIdIndex, boolean newNameTable)
/*  515:     */   {
/*  516: 729 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, blocksize, false, buildIdIndex, newNameTable);
/*  517:     */     
/*  518:     */ 
/*  519: 732 */     this._dtmManager = mgr;
/*  520: 733 */     this._size = blocksize;
/*  521:     */     
/*  522:     */ 
/*  523: 736 */     this._xmlSpaceStack = new int[blocksize <= 64 ? 4 : 64];
/*  524:     */     
/*  525:     */ 
/*  526: 739 */     this._xmlSpaceStack[0] = 0;
/*  527: 743 */     if ((source instanceof DOMSource))
/*  528:     */     {
/*  529: 744 */       this._hasDOMSource = true;
/*  530: 745 */       DOMSource domsrc = (DOMSource)source;
/*  531: 746 */       Node node = domsrc.getNode();
/*  532: 747 */       if ((node instanceof Document)) {
/*  533: 748 */         this._document = ((Document)node);
/*  534:     */       } else {
/*  535: 751 */         this._document = node.getOwnerDocument();
/*  536:     */       }
/*  537: 753 */       this._node2Ids = new org.apache.xalan.xsltc.runtime.Hashtable();
/*  538:     */     }
/*  539:     */   }
/*  540:     */   
/*  541:     */   public void migrateTo(DTMManager manager)
/*  542:     */   {
/*  543: 765 */     super.migrateTo(manager);
/*  544: 766 */     if ((manager instanceof XSLTCDTMManager)) {
/*  545: 767 */       this._dtmManager = ((XSLTCDTMManager)manager);
/*  546:     */     }
/*  547:     */   }
/*  548:     */   
/*  549:     */   public int getElementById(String idString)
/*  550:     */   {
/*  551: 779 */     Node node = this._document.getElementById(idString);
/*  552: 780 */     if (node != null)
/*  553:     */     {
/*  554: 781 */       Integer id = (Integer)this._node2Ids.get(node);
/*  555: 782 */       return id != null ? id.intValue() : -1;
/*  556:     */     }
/*  557: 785 */     return -1;
/*  558:     */   }
/*  559:     */   
/*  560:     */   public boolean hasDOMSource()
/*  561:     */   {
/*  562: 794 */     return this._hasDOMSource;
/*  563:     */   }
/*  564:     */   
/*  565:     */   private void xmlSpaceDefine(String val, int node)
/*  566:     */   {
/*  567: 807 */     boolean setting = val.equals("preserve");
/*  568: 808 */     if (setting != this._preserve)
/*  569:     */     {
/*  570: 809 */       this._xmlSpaceStack[(this._idx++)] = node;
/*  571: 810 */       this._preserve = setting;
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   private void xmlSpaceRevert(int node)
/*  576:     */   {
/*  577: 820 */     if (node == this._xmlSpaceStack[(this._idx - 1)])
/*  578:     */     {
/*  579: 821 */       this._idx -= 1;
/*  580: 822 */       this._preserve = (!this._preserve);
/*  581:     */     }
/*  582:     */   }
/*  583:     */   
/*  584:     */   protected boolean getShouldStripWhitespace()
/*  585:     */   {
/*  586: 834 */     return this._preserve ? false : super.getShouldStripWhitespace();
/*  587:     */   }
/*  588:     */   
/*  589:     */   private void handleTextEscaping()
/*  590:     */   {
/*  591: 841 */     if ((this._disableEscaping) && (this._textNodeToProcess != -1) && (_type(this._textNodeToProcess) == 3))
/*  592:     */     {
/*  593: 843 */       if (this._dontEscape == null) {
/*  594: 844 */         this._dontEscape = new BitArray(this._size);
/*  595:     */       }
/*  596: 848 */       if (this._textNodeToProcess >= this._dontEscape.size()) {
/*  597: 849 */         this._dontEscape.resize(this._dontEscape.size() * 2);
/*  598:     */       }
/*  599: 852 */       this._dontEscape.setBit(this._textNodeToProcess);
/*  600: 853 */       this._disableEscaping = false;
/*  601:     */     }
/*  602: 855 */     this._textNodeToProcess = -1;
/*  603:     */   }
/*  604:     */   
/*  605:     */   public void characters(char[] ch, int start, int length)
/*  606:     */     throws SAXException
/*  607:     */   {
/*  608: 868 */     super.characters(ch, start, length);
/*  609:     */     
/*  610: 870 */     this._disableEscaping = (!this._escaping);
/*  611: 871 */     this._textNodeToProcess = getNumberOfNodes();
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void startDocument()
/*  615:     */     throws SAXException
/*  616:     */   {
/*  617: 879 */     super.startDocument();
/*  618:     */     
/*  619: 881 */     this._nsIndex.put(new Integer(0), new Integer(this._uriCount++));
/*  620: 882 */     definePrefixAndUri("xml", "http://www.w3.org/XML/1998/namespace");
/*  621:     */   }
/*  622:     */   
/*  623:     */   public void endDocument()
/*  624:     */     throws SAXException
/*  625:     */   {
/*  626: 890 */     super.endDocument();
/*  627:     */     
/*  628: 892 */     handleTextEscaping();
/*  629: 893 */     this._namesSize = this.m_expandedNameTable.getSize();
/*  630:     */   }
/*  631:     */   
/*  632:     */   public void startElement(String uri, String localName, String qname, Attributes attributes, Node node)
/*  633:     */     throws SAXException
/*  634:     */   {
/*  635: 905 */     startElement(uri, localName, qname, attributes);
/*  636: 907 */     if (this.m_buildIdIndex) {
/*  637: 908 */       this._node2Ids.put(node, new Integer(this.m_parents.peek()));
/*  638:     */     }
/*  639:     */   }
/*  640:     */   
/*  641:     */   public void startElement(String uri, String localName, String qname, Attributes attributes)
/*  642:     */     throws SAXException
/*  643:     */   {
/*  644: 919 */     super.startElement(uri, localName, qname, attributes);
/*  645:     */     
/*  646: 921 */     handleTextEscaping();
/*  647: 923 */     if (this.m_wsfilter != null)
/*  648:     */     {
/*  649: 927 */       int index = attributes.getIndex("xml:space");
/*  650: 928 */       if (index >= 0) {
/*  651: 929 */         xmlSpaceDefine(attributes.getValue(index), this.m_parents.peek());
/*  652:     */       }
/*  653:     */     }
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void endElement(String namespaceURI, String localName, String qname)
/*  657:     */     throws SAXException
/*  658:     */   {
/*  659: 940 */     super.endElement(namespaceURI, localName, qname);
/*  660:     */     
/*  661: 942 */     handleTextEscaping();
/*  662: 945 */     if (this.m_wsfilter != null) {
/*  663: 946 */       xmlSpaceRevert(this.m_previous);
/*  664:     */     }
/*  665:     */   }
/*  666:     */   
/*  667:     */   public void processingInstruction(String target, String data)
/*  668:     */     throws SAXException
/*  669:     */   {
/*  670: 956 */     super.processingInstruction(target, data);
/*  671: 957 */     handleTextEscaping();
/*  672:     */   }
/*  673:     */   
/*  674:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/*  675:     */     throws SAXException
/*  676:     */   {
/*  677: 967 */     super.ignorableWhitespace(ch, start, length);
/*  678: 968 */     this._textNodeToProcess = getNumberOfNodes();
/*  679:     */   }
/*  680:     */   
/*  681:     */   public void startPrefixMapping(String prefix, String uri)
/*  682:     */     throws SAXException
/*  683:     */   {
/*  684: 977 */     super.startPrefixMapping(prefix, uri);
/*  685: 978 */     handleTextEscaping();
/*  686:     */     
/*  687: 980 */     definePrefixAndUri(prefix, uri);
/*  688:     */   }
/*  689:     */   
/*  690:     */   private void definePrefixAndUri(String prefix, String uri)
/*  691:     */     throws SAXException
/*  692:     */   {
/*  693: 987 */     Integer eType = new Integer(getIdForNamespace(uri));
/*  694: 988 */     if ((Integer)this._nsIndex.get(eType) == null) {
/*  695: 989 */       this._nsIndex.put(eType, new Integer(this._uriCount++));
/*  696:     */     }
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void comment(char[] ch, int start, int length)
/*  700:     */     throws SAXException
/*  701:     */   {
/*  702: 999 */     super.comment(ch, start, length);
/*  703:1000 */     handleTextEscaping();
/*  704:     */   }
/*  705:     */   
/*  706:     */   public boolean setEscaping(boolean value)
/*  707:     */   {
/*  708:1004 */     boolean temp = this._escaping;
/*  709:1005 */     this._escaping = value;
/*  710:1006 */     return temp;
/*  711:     */   }
/*  712:     */   
/*  713:     */   public void print(int node, int level)
/*  714:     */   {
/*  715:1018 */     switch (getNodeType(node))
/*  716:     */     {
/*  717:     */     case 0: 
/*  718:     */     case 9: 
/*  719:1022 */       print(getFirstChild(node), level);
/*  720:1023 */       break;
/*  721:     */     case 3: 
/*  722:     */     case 7: 
/*  723:     */     case 8: 
/*  724:1027 */       System.out.print(getStringValueX(node));
/*  725:1028 */       break;
/*  726:     */     case 1: 
/*  727:     */     case 2: 
/*  728:     */     case 4: 
/*  729:     */     case 5: 
/*  730:     */     case 6: 
/*  731:     */     default: 
/*  732:1030 */       String name = getNodeName(node);
/*  733:1031 */       System.out.print("<" + name);
/*  734:1032 */       for (int a = getFirstAttribute(node); a != -1; a = getNextAttribute(a)) {
/*  735:1034 */         System.out.print("\n" + getNodeName(a) + "=\"" + getStringValueX(a) + "\"");
/*  736:     */       }
/*  737:1036 */       System.out.print('>');
/*  738:1037 */       for (int child = getFirstChild(node); child != -1; child = getNextSibling(child)) {
/*  739:1039 */         print(child, level + 1);
/*  740:     */       }
/*  741:1041 */       System.out.println("</" + name + '>');
/*  742:     */     }
/*  743:     */   }
/*  744:     */   
/*  745:     */   public String getNodeName(int node)
/*  746:     */   {
/*  747:1052 */     int nodeh = node;
/*  748:1053 */     short type = getNodeType(nodeh);
/*  749:1054 */     switch (type)
/*  750:     */     {
/*  751:     */     case 0: 
/*  752:     */     case 3: 
/*  753:     */     case 8: 
/*  754:     */     case 9: 
/*  755:1060 */       return "";
/*  756:     */     case 13: 
/*  757:1062 */       return getLocalName(nodeh);
/*  758:     */     }
/*  759:1064 */     return super.getNodeName(nodeh);
/*  760:     */   }
/*  761:     */   
/*  762:     */   public String getNamespaceName(int node)
/*  763:     */   {
/*  764:1073 */     if (node == -1) {
/*  765:1074 */       return "";
/*  766:     */     }
/*  767:     */     String s;
/*  768:1078 */     return (s = getNamespaceURI(node)) == null ? "" : s;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public int getAttributeNode(int type, int element)
/*  772:     */   {
/*  773:1087 */     for (int attr = getFirstAttribute(element); attr != -1; attr = getNextAttribute(attr)) {
/*  774:1091 */       if (getExpandedTypeID(attr) == type) {
/*  775:1091 */         return attr;
/*  776:     */       }
/*  777:     */     }
/*  778:1093 */     return -1;
/*  779:     */   }
/*  780:     */   
/*  781:     */   public String getAttributeValue(int type, int element)
/*  782:     */   {
/*  783:1101 */     int attr = getAttributeNode(type, element);
/*  784:1102 */     return attr != -1 ? getStringValueX(attr) : "";
/*  785:     */   }
/*  786:     */   
/*  787:     */   public String getAttributeValue(String name, int element)
/*  788:     */   {
/*  789:1110 */     return getAttributeValue(getGeneralizedType(name), element);
/*  790:     */   }
/*  791:     */   
/*  792:     */   public DTMAxisIterator getChildren(int node)
/*  793:     */   {
/*  794:1118 */     return new SAX2DTM2.ChildrenIterator(this).setStartNode(node);
/*  795:     */   }
/*  796:     */   
/*  797:     */   public DTMAxisIterator getTypedChildren(int type)
/*  798:     */   {
/*  799:1127 */     return new SAX2DTM2.TypedChildrenIterator(this, type);
/*  800:     */   }
/*  801:     */   
/*  802:     */   public DTMAxisIterator getAxisIterator(int axis)
/*  803:     */   {
/*  804:1138 */     switch (axis)
/*  805:     */     {
/*  806:     */     case 13: 
/*  807:1141 */       return new DTMDefaultBaseIterators.SingletonIterator(this);
/*  808:     */     case 3: 
/*  809:1143 */       return new SAX2DTM2.ChildrenIterator(this);
/*  810:     */     case 10: 
/*  811:1145 */       return new SAX2DTM2.ParentIterator(this);
/*  812:     */     case 0: 
/*  813:1147 */       return new SAX2DTM2.AncestorIterator(this);
/*  814:     */     case 1: 
/*  815:1149 */       return new SAX2DTM2.AncestorIterator(this).includeSelf();
/*  816:     */     case 2: 
/*  817:1151 */       return new SAX2DTM2.AttributeIterator(this);
/*  818:     */     case 4: 
/*  819:1153 */       return new SAX2DTM2.DescendantIterator(this);
/*  820:     */     case 5: 
/*  821:1155 */       return new SAX2DTM2.DescendantIterator(this).includeSelf();
/*  822:     */     case 6: 
/*  823:1157 */       return new SAX2DTM2.FollowingIterator(this);
/*  824:     */     case 11: 
/*  825:1159 */       return new SAX2DTM2.PrecedingIterator(this);
/*  826:     */     case 7: 
/*  827:1161 */       return new SAX2DTM2.FollowingSiblingIterator(this);
/*  828:     */     case 12: 
/*  829:1163 */       return new SAX2DTM2.PrecedingSiblingIterator(this);
/*  830:     */     case 9: 
/*  831:1165 */       return new DTMDefaultBaseIterators.NamespaceIterator(this);
/*  832:     */     case 19: 
/*  833:1167 */       return new DTMDefaultBaseIterators.RootIterator(this);
/*  834:     */     }
/*  835:1169 */     BasisLibrary.runTimeError("AXIS_SUPPORT_ERR", Axis.getNames(axis));
/*  836:     */     
/*  837:     */ 
/*  838:1172 */     return null;
/*  839:     */   }
/*  840:     */   
/*  841:     */   public DTMAxisIterator getTypedAxisIterator(int axis, int type)
/*  842:     */   {
/*  843:1182 */     if (axis == 3) {
/*  844:1183 */       return new SAX2DTM2.TypedChildrenIterator(this, type);
/*  845:     */     }
/*  846:1186 */     if (type == -1) {
/*  847:1187 */       return EMPTYITERATOR;
/*  848:     */     }
/*  849:1190 */     switch (axis)
/*  850:     */     {
/*  851:     */     case 13: 
/*  852:1193 */       return new SAX2DTM2.TypedSingletonIterator(this, type);
/*  853:     */     case 3: 
/*  854:1195 */       return new SAX2DTM2.TypedChildrenIterator(this, type);
/*  855:     */     case 10: 
/*  856:1197 */       return new SAX2DTM2.ParentIterator(this).setNodeType(type);
/*  857:     */     case 0: 
/*  858:1199 */       return new SAX2DTM2.TypedAncestorIterator(this, type);
/*  859:     */     case 1: 
/*  860:1201 */       return new SAX2DTM2.TypedAncestorIterator(this, type).includeSelf();
/*  861:     */     case 2: 
/*  862:1203 */       return new SAX2DTM2.TypedAttributeIterator(this, type);
/*  863:     */     case 4: 
/*  864:1205 */       return new SAX2DTM2.TypedDescendantIterator(this, type);
/*  865:     */     case 5: 
/*  866:1207 */       return new SAX2DTM2.TypedDescendantIterator(this, type).includeSelf();
/*  867:     */     case 6: 
/*  868:1209 */       return new SAX2DTM2.TypedFollowingIterator(this, type);
/*  869:     */     case 11: 
/*  870:1211 */       return new SAX2DTM2.TypedPrecedingIterator(this, type);
/*  871:     */     case 7: 
/*  872:1213 */       return new SAX2DTM2.TypedFollowingSiblingIterator(this, type);
/*  873:     */     case 12: 
/*  874:1215 */       return new SAX2DTM2.TypedPrecedingSiblingIterator(this, type);
/*  875:     */     case 9: 
/*  876:1217 */       return new TypedNamespaceIterator(type);
/*  877:     */     case 19: 
/*  878:1219 */       return new SAX2DTM2.TypedRootIterator(this, type);
/*  879:     */     }
/*  880:1221 */     BasisLibrary.runTimeError("TYPED_AXIS_SUPPORT_ERR", Axis.getNames(axis));
/*  881:     */     
/*  882:     */ 
/*  883:1224 */     return null;
/*  884:     */   }
/*  885:     */   
/*  886:     */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns)
/*  887:     */   {
/*  888:1237 */     DTMAxisIterator iterator = null;
/*  889:1239 */     if (ns == -1) {
/*  890:1240 */       return EMPTYITERATOR;
/*  891:     */     }
/*  892:1243 */     switch (axis)
/*  893:     */     {
/*  894:     */     case 3: 
/*  895:1245 */       return new NamespaceChildrenIterator(ns);
/*  896:     */     case 2: 
/*  897:1247 */       return new NamespaceAttributeIterator(ns);
/*  898:     */     }
/*  899:1249 */     return new NamespaceWildcardIterator(axis, ns);
/*  900:     */   }
/*  901:     */   
/*  902:     */   public final class NamespaceWildcardIterator
/*  903:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  904:     */   {
/*  905:     */     protected int m_nsType;
/*  906:     */     protected DTMAxisIterator m_baseIterator;
/*  907:     */     
/*  908:     */     public NamespaceWildcardIterator(int axis, int nsType)
/*  909:     */     {
/*  910:1279 */       super();
/*  911:1280 */       this.m_nsType = nsType;
/*  912:1284 */       switch (axis)
/*  913:     */       {
/*  914:     */       case 2: 
/*  915:1288 */         this.m_baseIterator = SAXImpl.this.getAxisIterator(axis);
/*  916:     */       case 9: 
/*  917:1293 */         this.m_baseIterator = SAXImpl.this.getAxisIterator(axis);
/*  918:     */       }
/*  919:1298 */       this.m_baseIterator = SAXImpl.this.getTypedAxisIterator(axis, 1);
/*  920:     */     }
/*  921:     */     
/*  922:     */     public DTMAxisIterator setStartNode(int node)
/*  923:     */     {
/*  924:1313 */       if (this._isRestartable)
/*  925:     */       {
/*  926:1314 */         this._startNode = node;
/*  927:1315 */         this.m_baseIterator.setStartNode(node);
/*  928:1316 */         resetPosition();
/*  929:     */       }
/*  930:1318 */       return this;
/*  931:     */     }
/*  932:     */     
/*  933:     */     public int next()
/*  934:     */     {
/*  935:     */       int node;
/*  936:1329 */       while ((node = this.m_baseIterator.next()) != -1)
/*  937:     */       {
/*  938:     */         int i;
/*  939:1331 */         if (SAXImpl.this.getNSType(i) == this.m_nsType) {
/*  940:1332 */           return returnNode(i);
/*  941:     */         }
/*  942:     */       }
/*  943:1336 */       return -1;
/*  944:     */     }
/*  945:     */     
/*  946:     */     public DTMAxisIterator cloneIterator()
/*  947:     */     {
/*  948:     */       try
/*  949:     */       {
/*  950:1347 */         DTMAxisIterator nestedClone = this.m_baseIterator.cloneIterator();
/*  951:1348 */         NamespaceWildcardIterator clone = (NamespaceWildcardIterator)super.clone();
/*  952:     */         
/*  953:     */ 
/*  954:1351 */         clone.m_baseIterator = nestedClone;
/*  955:1352 */         clone.m_nsType = this.m_nsType;
/*  956:1353 */         clone._isRestartable = false;
/*  957:     */         
/*  958:1355 */         return clone;
/*  959:     */       }
/*  960:     */       catch (CloneNotSupportedException e)
/*  961:     */       {
/*  962:1357 */         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/*  963:     */       }
/*  964:1359 */       return null;
/*  965:     */     }
/*  966:     */     
/*  967:     */     public boolean isReverse()
/*  968:     */     {
/*  969:1369 */       return this.m_baseIterator.isReverse();
/*  970:     */     }
/*  971:     */     
/*  972:     */     public void setMark()
/*  973:     */     {
/*  974:1373 */       this.m_baseIterator.setMark();
/*  975:     */     }
/*  976:     */     
/*  977:     */     public void gotoMark()
/*  978:     */     {
/*  979:1377 */       this.m_baseIterator.gotoMark();
/*  980:     */     }
/*  981:     */   }
/*  982:     */   
/*  983:     */   public final class NamespaceChildrenIterator
/*  984:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/*  985:     */   {
/*  986:     */     private final int _nsType;
/*  987:     */     
/*  988:     */     public NamespaceChildrenIterator(int type)
/*  989:     */     {
/*  990:1400 */       super();
/*  991:1401 */       this._nsType = type;
/*  992:     */     }
/*  993:     */     
/*  994:     */     public DTMAxisIterator setStartNode(int node)
/*  995:     */     {
/*  996:1414 */       if (node == 0) {
/*  997:1415 */         node = SAXImpl.this.getDocument();
/*  998:     */       }
/*  999:1418 */       if (this._isRestartable)
/* 1000:     */       {
/* 1001:1419 */         this._startNode = node;
/* 1002:1420 */         this._currentNode = (node == -1 ? -1 : -2);
/* 1003:     */         
/* 1004:1422 */         return resetPosition();
/* 1005:     */       }
/* 1006:1425 */       return this;
/* 1007:     */     }
/* 1008:     */     
/* 1009:     */     public int next()
/* 1010:     */     {
/* 1011:1434 */       if (this._currentNode != -1) {
/* 1012:1435 */         for (int node = -2 == this._currentNode ? SAXImpl.this._firstch(SAXImpl.this.makeNodeIdentity(this._startNode)) : SAXImpl.this._nextsib(this._currentNode); node != -1; node = SAXImpl.this._nextsib(node))
/* 1013:     */         {
/* 1014:1440 */           int nodeHandle = SAXImpl.this.makeNodeHandle(node);
/* 1015:1442 */           if (SAXImpl.this.getNSType(nodeHandle) == this._nsType)
/* 1016:     */           {
/* 1017:1443 */             this._currentNode = node;
/* 1018:     */             
/* 1019:1445 */             return returnNode(nodeHandle);
/* 1020:     */           }
/* 1021:     */         }
/* 1022:     */       }
/* 1023:1450 */       return -1;
/* 1024:     */     }
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public final class NamespaceAttributeIterator
/* 1028:     */     extends DTMDefaultBaseIterators.InternalAxisIteratorBase
/* 1029:     */   {
/* 1030:     */     private final int _nsType;
/* 1031:     */     
/* 1032:     */     public NamespaceAttributeIterator(int nsType)
/* 1033:     */     {
/* 1034:1471 */       super();
/* 1035:     */       
/* 1036:1473 */       this._nsType = nsType;
/* 1037:     */     }
/* 1038:     */     
/* 1039:     */     public DTMAxisIterator setStartNode(int node)
/* 1040:     */     {
/* 1041:1486 */       if (node == 0) {
/* 1042:1487 */         node = SAXImpl.this.getDocument();
/* 1043:     */       }
/* 1044:1490 */       if (this._isRestartable)
/* 1045:     */       {
/* 1046:1491 */         int nsType = this._nsType;
/* 1047:     */         
/* 1048:1493 */         this._startNode = node;
/* 1049:1495 */         for (node = SAXImpl.this.getFirstAttribute(node); node != -1; node = SAXImpl.this.getNextAttribute(node)) {
/* 1050:1498 */           if (SAXImpl.this.getNSType(node) == nsType) {
/* 1051:     */             break;
/* 1052:     */           }
/* 1053:     */         }
/* 1054:1503 */         this._currentNode = node;
/* 1055:1504 */         return resetPosition();
/* 1056:     */       }
/* 1057:1507 */       return this;
/* 1058:     */     }
/* 1059:     */     
/* 1060:     */     public int next()
/* 1061:     */     {
/* 1062:1516 */       int node = this._currentNode;
/* 1063:1517 */       int nsType = this._nsType;
/* 1064:1520 */       if (node == -1) {
/* 1065:1521 */         return -1;
/* 1066:     */       }
/* 1067:1524 */       for (int nextNode = SAXImpl.this.getNextAttribute(node); nextNode != -1; nextNode = SAXImpl.this.getNextAttribute(nextNode)) {
/* 1068:1527 */         if (SAXImpl.this.getNSType(nextNode) == nsType) {
/* 1069:     */           break;
/* 1070:     */         }
/* 1071:     */       }
/* 1072:1532 */       this._currentNode = nextNode;
/* 1073:     */       
/* 1074:1534 */       return returnNode(node);
/* 1075:     */     }
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public DTMAxisIterator getTypedDescendantIterator(int type)
/* 1079:     */   {
/* 1080:1544 */     return new SAX2DTM2.TypedDescendantIterator(this, type);
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   public DTMAxisIterator getNthDescendant(int type, int n, boolean includeself)
/* 1084:     */   {
/* 1085:1552 */     DTMAxisIterator source = new SAX2DTM2.TypedDescendantIterator(this, type);
/* 1086:1553 */     return new DTMDefaultBaseIterators.NthDescendantIterator(this, n);
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   public void characters(int node, SerializationHandler handler)
/* 1090:     */     throws TransletException
/* 1091:     */   {
/* 1092:1562 */     if (node != -1) {
/* 1093:     */       try
/* 1094:     */       {
/* 1095:1564 */         dispatchCharactersEvents(node, handler, false);
/* 1096:     */       }
/* 1097:     */       catch (SAXException e)
/* 1098:     */       {
/* 1099:1566 */         throw new TransletException(e);
/* 1100:     */       }
/* 1101:     */     }
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public void copy(DTMAxisIterator nodes, SerializationHandler handler)
/* 1105:     */     throws TransletException
/* 1106:     */   {
/* 1107:     */     int node;
/* 1108:1578 */     while ((node = nodes.next()) != -1)
/* 1109:     */     {
/* 1110:     */       int i;
/* 1111:1579 */       copy(i, handler);
/* 1112:     */     }
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public void copy(SerializationHandler handler)
/* 1116:     */     throws TransletException
/* 1117:     */   {
/* 1118:1588 */     copy(getDocument(), handler);
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public void copy(int node, SerializationHandler handler)
/* 1122:     */     throws TransletException
/* 1123:     */   {
/* 1124:1601 */     copy(node, handler, false);
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   private final void copy(int node, SerializationHandler handler, boolean isChild)
/* 1128:     */     throws TransletException
/* 1129:     */   {
/* 1130:1608 */     int nodeID = makeNodeIdentity(node);
/* 1131:1609 */     int eType = _exptype2(nodeID);
/* 1132:1610 */     int type = _exptype2Type(eType);
/* 1133:     */     try
/* 1134:     */     {
/* 1135:1613 */       switch (type)
/* 1136:     */       {
/* 1137:     */       case 0: 
/* 1138:     */       case 9: 
/* 1139:1617 */         for (int c = _firstch2(nodeID); c != -1; c = _nextsib2(c)) {
/* 1140:1618 */           copy(makeNodeHandle(c), handler, true);
/* 1141:     */         }
/* 1142:1620 */         break;
/* 1143:     */       case 7: 
/* 1144:1622 */         copyPI(node, handler);
/* 1145:1623 */         break;
/* 1146:     */       case 8: 
/* 1147:1625 */         handler.comment(getStringValueX(node));
/* 1148:1626 */         break;
/* 1149:     */       case 3: 
/* 1150:1628 */         boolean oldEscapeSetting = false;
/* 1151:1629 */         boolean escapeBit = false;
/* 1152:1631 */         if (this._dontEscape != null)
/* 1153:     */         {
/* 1154:1632 */           escapeBit = this._dontEscape.getBit(getNodeIdent(node));
/* 1155:1633 */           if (escapeBit) {
/* 1156:1634 */             oldEscapeSetting = handler.setEscaping(false);
/* 1157:     */           }
/* 1158:     */         }
/* 1159:1638 */         copyTextNode(nodeID, handler);
/* 1160:1640 */         if (escapeBit) {
/* 1161:1641 */           handler.setEscaping(oldEscapeSetting);
/* 1162:     */         }
/* 1163:     */         break;
/* 1164:     */       case 2: 
/* 1165:1645 */         copyAttribute(nodeID, eType, handler);
/* 1166:1646 */         break;
/* 1167:     */       case 13: 
/* 1168:1648 */         handler.namespaceAfterStartElement(getNodeNameX(node), getNodeValue(node));
/* 1169:1649 */         break;
/* 1170:     */       case 1: 
/* 1171:     */       case 4: 
/* 1172:     */       case 5: 
/* 1173:     */       case 6: 
/* 1174:     */       case 10: 
/* 1175:     */       case 11: 
/* 1176:     */       case 12: 
/* 1177:     */       default: 
/* 1178:1651 */         if (type == 1)
/* 1179:     */         {
/* 1180:1654 */           String name = copyElement(nodeID, eType, handler);
/* 1181:     */           
/* 1182:     */ 
/* 1183:1657 */           copyNS(nodeID, handler, !isChild);
/* 1184:1658 */           copyAttributes(nodeID, handler);
/* 1185:1660 */           for (int c = _firstch2(nodeID); c != -1; c = _nextsib2(c)) {
/* 1186:1661 */             copy(makeNodeHandle(c), handler, true);
/* 1187:     */           }
/* 1188:1665 */           handler.endElement(name);
/* 1189:     */         }
/* 1190:     */         else
/* 1191:     */         {
/* 1192:1669 */           String uri = getNamespaceName(node);
/* 1193:1670 */           if (uri.length() != 0)
/* 1194:     */           {
/* 1195:1671 */             String prefix = getPrefix(node);
/* 1196:1672 */             handler.namespaceAfterStartElement(prefix, uri);
/* 1197:     */           }
/* 1198:1674 */           handler.addAttribute(getNodeName(node), getNodeValue(node));
/* 1199:     */         }
/* 1200:     */         break;
/* 1201:     */       }
/* 1202:     */     }
/* 1203:     */     catch (Exception e)
/* 1204:     */     {
/* 1205:1680 */       throw new TransletException(e);
/* 1206:     */     }
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   private void copyPI(int node, SerializationHandler handler)
/* 1210:     */     throws TransletException
/* 1211:     */   {
/* 1212:1690 */     String target = getNodeName(node);
/* 1213:1691 */     String value = getStringValueX(node);
/* 1214:     */     try
/* 1215:     */     {
/* 1216:1694 */       handler.processingInstruction(target, value);
/* 1217:     */     }
/* 1218:     */     catch (Exception e)
/* 1219:     */     {
/* 1220:1696 */       throw new TransletException(e);
/* 1221:     */     }
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   public String shallowCopy(int node, SerializationHandler handler)
/* 1225:     */     throws TransletException
/* 1226:     */   {
/* 1227:1706 */     int nodeID = makeNodeIdentity(node);
/* 1228:1707 */     int exptype = _exptype2(nodeID);
/* 1229:1708 */     int type = _exptype2Type(exptype);
/* 1230:     */     try
/* 1231:     */     {
/* 1232:1711 */       switch (type)
/* 1233:     */       {
/* 1234:     */       case 1: 
/* 1235:1714 */         String name = copyElement(nodeID, exptype, handler);
/* 1236:1715 */         copyNS(nodeID, handler, true);
/* 1237:1716 */         return name;
/* 1238:     */       case 0: 
/* 1239:     */       case 9: 
/* 1240:1719 */         return "";
/* 1241:     */       case 3: 
/* 1242:1721 */         copyTextNode(nodeID, handler);
/* 1243:1722 */         return null;
/* 1244:     */       case 7: 
/* 1245:1724 */         copyPI(node, handler);
/* 1246:1725 */         return null;
/* 1247:     */       case 8: 
/* 1248:1727 */         handler.comment(getStringValueX(node));
/* 1249:1728 */         return null;
/* 1250:     */       case 13: 
/* 1251:1730 */         handler.namespaceAfterStartElement(getNodeNameX(node), getNodeValue(node));
/* 1252:1731 */         return null;
/* 1253:     */       case 2: 
/* 1254:1733 */         copyAttribute(nodeID, exptype, handler);
/* 1255:1734 */         return null;
/* 1256:     */       }
/* 1257:1736 */       String uri1 = getNamespaceName(node);
/* 1258:1737 */       if (uri1.length() != 0)
/* 1259:     */       {
/* 1260:1738 */         String prefix = getPrefix(node);
/* 1261:1739 */         handler.namespaceAfterStartElement(prefix, uri1);
/* 1262:     */       }
/* 1263:1741 */       handler.addAttribute(getNodeName(node), getNodeValue(node));
/* 1264:1742 */       return null;
/* 1265:     */     }
/* 1266:     */     catch (Exception e)
/* 1267:     */     {
/* 1268:1745 */       throw new TransletException(e);
/* 1269:     */     }
/* 1270:     */   }
/* 1271:     */   
/* 1272:     */   public String getLanguage(int node)
/* 1273:     */   {
/* 1274:1754 */     int parent = node;
/* 1275:1755 */     while (-1 != parent)
/* 1276:     */     {
/* 1277:1756 */       if (1 == getNodeType(parent))
/* 1278:     */       {
/* 1279:1757 */         int langAttr = getAttributeNode(parent, "http://www.w3.org/XML/1998/namespace", "lang");
/* 1280:1759 */         if (-1 != langAttr) {
/* 1281:1760 */           return getNodeValue(langAttr);
/* 1282:     */         }
/* 1283:     */       }
/* 1284:1764 */       parent = getParent(parent);
/* 1285:     */     }
/* 1286:1766 */     return null;
/* 1287:     */   }
/* 1288:     */   
/* 1289:     */   public DOMBuilder getBuilder()
/* 1290:     */   {
/* 1291:1776 */     return this;
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   public SerializationHandler getOutputDomBuilder()
/* 1295:     */   {
/* 1296:1785 */     return new ToXMLSAXHandler(this, "UTF-8");
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public DOM getResultTreeFrag(int initSize, int rtfType)
/* 1300:     */   {
/* 1301:1793 */     return getResultTreeFrag(initSize, rtfType, true);
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public DOM getResultTreeFrag(int initSize, int rtfType, boolean addToManager)
/* 1305:     */   {
/* 1306:1806 */     if (rtfType == 0)
/* 1307:     */     {
/* 1308:1807 */       if (addToManager)
/* 1309:     */       {
/* 1310:1808 */         int dtmPos = this._dtmManager.getFirstFreeDTMID();
/* 1311:1809 */         SimpleResultTreeImpl rtf = new SimpleResultTreeImpl(this._dtmManager, dtmPos << 16);
/* 1312:     */         
/* 1313:1811 */         this._dtmManager.addDTM(rtf, dtmPos, 0);
/* 1314:1812 */         return rtf;
/* 1315:     */       }
/* 1316:1815 */       return new SimpleResultTreeImpl(this._dtmManager, 0);
/* 1317:     */     }
/* 1318:1818 */     if (rtfType == 1)
/* 1319:     */     {
/* 1320:1819 */       if (addToManager)
/* 1321:     */       {
/* 1322:1820 */         int dtmPos = this._dtmManager.getFirstFreeDTMID();
/* 1323:1821 */         AdaptiveResultTreeImpl rtf = new AdaptiveResultTreeImpl(this._dtmManager, dtmPos << 16, this.m_wsfilter, initSize, this.m_buildIdIndex);
/* 1324:     */         
/* 1325:     */ 
/* 1326:1824 */         this._dtmManager.addDTM(rtf, dtmPos, 0);
/* 1327:1825 */         return rtf;
/* 1328:     */       }
/* 1329:1829 */       return new AdaptiveResultTreeImpl(this._dtmManager, 0, this.m_wsfilter, initSize, this.m_buildIdIndex);
/* 1330:     */     }
/* 1331:1834 */     return (DOM)this._dtmManager.getDTM(null, true, this.m_wsfilter, true, false, false, initSize, this.m_buildIdIndex);
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public org.apache.xalan.xsltc.runtime.Hashtable getElementsWithIDs()
/* 1335:     */   {
/* 1336:1844 */     if (this.m_idAttributes == null) {
/* 1337:1845 */       return null;
/* 1338:     */     }
/* 1339:1849 */     Enumeration idValues = this.m_idAttributes.keys();
/* 1340:1850 */     if (!idValues.hasMoreElements()) {
/* 1341:1851 */       return null;
/* 1342:     */     }
/* 1343:1854 */     org.apache.xalan.xsltc.runtime.Hashtable idAttrsTable = new org.apache.xalan.xsltc.runtime.Hashtable();
/* 1344:1856 */     while (idValues.hasMoreElements())
/* 1345:     */     {
/* 1346:1857 */       Object idValue = idValues.nextElement();
/* 1347:     */       
/* 1348:1859 */       idAttrsTable.put(idValue, this.m_idAttributes.get(idValue));
/* 1349:     */     }
/* 1350:1862 */     return idAttrsTable;
/* 1351:     */   }
/* 1352:     */   
/* 1353:     */   public String getUnparsedEntityURI(String name)
/* 1354:     */   {
/* 1355:1874 */     if (this._document != null)
/* 1356:     */     {
/* 1357:1875 */       String uri = "";
/* 1358:1876 */       DocumentType doctype = this._document.getDoctype();
/* 1359:1877 */       if (doctype != null)
/* 1360:     */       {
/* 1361:1878 */         NamedNodeMap entities = doctype.getEntities();
/* 1362:1880 */         if (entities == null) {
/* 1363:1881 */           return uri;
/* 1364:     */         }
/* 1365:1884 */         Entity entity = (Entity)entities.getNamedItem(name);
/* 1366:1886 */         if (entity == null) {
/* 1367:1887 */           return uri;
/* 1368:     */         }
/* 1369:1890 */         String notationName = entity.getNotationName();
/* 1370:1891 */         if (notationName != null)
/* 1371:     */         {
/* 1372:1892 */           uri = entity.getSystemId();
/* 1373:1893 */           if (uri == null) {
/* 1374:1894 */             uri = entity.getPublicId();
/* 1375:     */           }
/* 1376:     */         }
/* 1377:     */       }
/* 1378:1898 */       return uri;
/* 1379:     */     }
/* 1380:1901 */     return super.getUnparsedEntityURI(name);
/* 1381:     */   }
/* 1382:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.SAXImpl
 * JD-Core Version:    0.7.0.1
 */