/*    1:     */ package org.apache.xml.dtm.ref.dom2dtm;
/*    2:     */ 
/*    3:     */ import java.util.Vector;
/*    4:     */ import javax.xml.transform.SourceLocator;
/*    5:     */ import javax.xml.transform.dom.DOMSource;
/*    6:     */ import org.apache.xml.dtm.DTMManager;
/*    7:     */ import org.apache.xml.dtm.DTMWSFilter;
/*    8:     */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*    9:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators;
/*   10:     */ import org.apache.xml.dtm.ref.DTMManagerDefault;
/*   11:     */ import org.apache.xml.dtm.ref.ExpandedNameTable;
/*   12:     */ import org.apache.xml.dtm.ref.IncrementalSAXSource;
/*   13:     */ import org.apache.xml.res.XMLMessages;
/*   14:     */ import org.apache.xml.utils.FastStringBuffer;
/*   15:     */ import org.apache.xml.utils.QName;
/*   16:     */ import org.apache.xml.utils.StringBufferPool;
/*   17:     */ import org.apache.xml.utils.SuballocatedIntVector;
/*   18:     */ import org.apache.xml.utils.TreeWalker;
/*   19:     */ import org.apache.xml.utils.XMLCharacterRecognizer;
/*   20:     */ import org.apache.xml.utils.XMLString;
/*   21:     */ import org.apache.xml.utils.XMLStringFactory;
/*   22:     */ import org.w3c.dom.Attr;
/*   23:     */ import org.w3c.dom.Document;
/*   24:     */ import org.w3c.dom.DocumentType;
/*   25:     */ import org.w3c.dom.Element;
/*   26:     */ import org.w3c.dom.Entity;
/*   27:     */ import org.w3c.dom.NamedNodeMap;
/*   28:     */ import org.w3c.dom.Node;
/*   29:     */ import org.xml.sax.ContentHandler;
/*   30:     */ import org.xml.sax.DTDHandler;
/*   31:     */ import org.xml.sax.EntityResolver;
/*   32:     */ import org.xml.sax.ErrorHandler;
/*   33:     */ import org.xml.sax.SAXException;
/*   34:     */ import org.xml.sax.ext.DeclHandler;
/*   35:     */ import org.xml.sax.ext.LexicalHandler;
/*   36:     */ 
/*   37:     */ public class DOM2DTM
/*   38:     */   extends DTMDefaultBaseIterators
/*   39:     */ {
/*   40:     */   static final boolean JJK_DEBUG = false;
/*   41:     */   static final boolean JJK_NEWCODE = true;
/*   42:     */   static final String NAMESPACE_DECL_NS = "http://www.w3.org/XML/1998/namespace";
/*   43:     */   private transient Node m_pos;
/*   44:  79 */   private int m_last_parent = 0;
/*   45:  82 */   private int m_last_kid = -1;
/*   46:     */   private transient Node m_root;
/*   47:  91 */   boolean m_processedFirstElement = false;
/*   48:     */   private transient boolean m_nodesAreProcessed;
/*   49: 104 */   protected Vector m_nodes = new Vector();
/*   50:     */   
/*   51:     */   public DOM2DTM(DTMManager mgr, DOMSource domSource, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing)
/*   52:     */   {
/*   53: 123 */     super(mgr, domSource, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing);
/*   54:     */     
/*   55:     */ 
/*   56:     */ 
/*   57: 127 */     this.m_pos = (this.m_root = domSource.getNode());
/*   58:     */     
/*   59: 129 */     this.m_last_parent = (this.m_last_kid = -1);
/*   60: 130 */     this.m_last_kid = addNode(this.m_root, this.m_last_parent, this.m_last_kid, -1);
/*   61: 143 */     if (1 == this.m_root.getNodeType())
/*   62:     */     {
/*   63: 145 */       NamedNodeMap attrs = this.m_root.getAttributes();
/*   64: 146 */       int attrsize = attrs == null ? 0 : attrs.getLength();
/*   65: 147 */       if (attrsize > 0)
/*   66:     */       {
/*   67: 149 */         int attrIndex = -1;
/*   68: 150 */         for (int i = 0; i < attrsize; i++)
/*   69:     */         {
/*   70: 155 */           attrIndex = addNode(attrs.item(i), 0, attrIndex, -1);
/*   71: 156 */           this.m_firstch.setElementAt(-1, attrIndex);
/*   72:     */         }
/*   73: 160 */         this.m_nextsib.setElementAt(-1, attrIndex);
/*   74:     */       }
/*   75:     */     }
/*   76: 167 */     this.m_nodesAreProcessed = false;
/*   77:     */   }
/*   78:     */   
/*   79:     */   protected int addNode(Node node, int parentIndex, int previousSibling, int forceNodeType)
/*   80:     */   {
/*   81: 185 */     int nodeIndex = this.m_nodes.size();
/*   82: 188 */     if (this.m_dtmIdent.size() == nodeIndex >>> 16) {
/*   83:     */       try
/*   84:     */       {
/*   85: 192 */         if (this.m_mgr == null) {
/*   86: 193 */           throw new ClassCastException();
/*   87:     */         }
/*   88: 196 */         DTMManagerDefault mgrD = (DTMManagerDefault)this.m_mgr;
/*   89: 197 */         int id = mgrD.getFirstFreeDTMID();
/*   90: 198 */         mgrD.addDTM(this, id, nodeIndex);
/*   91: 199 */         this.m_dtmIdent.addElement(id << 16);
/*   92:     */       }
/*   93:     */       catch (ClassCastException e)
/*   94:     */       {
/*   95: 206 */         error(XMLMessages.createXMLMessage("ER_NO_DTMIDS_AVAIL", null));
/*   96:     */       }
/*   97:     */     }
/*   98: 210 */     this.m_size += 1;
/*   99:     */     int type;
/*  100: 214 */     if (-1 == forceNodeType) {
/*  101: 215 */       type = node.getNodeType();
/*  102:     */     } else {
/*  103: 217 */       type = forceNodeType;
/*  104:     */     }
/*  105: 236 */     if (2 == type)
/*  106:     */     {
/*  107: 238 */       String name = node.getNodeName();
/*  108: 240 */       if ((name.startsWith("xmlns:")) || (name.equals("xmlns"))) {
/*  109: 242 */         type = 13;
/*  110:     */       }
/*  111:     */     }
/*  112: 246 */     this.m_nodes.addElement(node);
/*  113:     */     
/*  114: 248 */     this.m_firstch.setElementAt(-2, nodeIndex);
/*  115: 249 */     this.m_nextsib.setElementAt(-2, nodeIndex);
/*  116: 250 */     this.m_prevsib.setElementAt(previousSibling, nodeIndex);
/*  117: 251 */     this.m_parent.setElementAt(parentIndex, nodeIndex);
/*  118: 253 */     if ((-1 != parentIndex) && (type != 2) && (type != 13)) {
/*  119: 258 */       if (-2 == this.m_firstch.elementAt(parentIndex)) {
/*  120: 259 */         this.m_firstch.setElementAt(nodeIndex, parentIndex);
/*  121:     */       }
/*  122:     */     }
/*  123: 262 */     String nsURI = node.getNamespaceURI();
/*  124:     */     
/*  125:     */ 
/*  126:     */ 
/*  127:     */ 
/*  128: 267 */     String localName = type == 7 ? node.getNodeName() : node.getLocalName();
/*  129: 272 */     if (((type == 1) || (type == 2)) && (null == localName)) {
/*  130: 274 */       localName = node.getNodeName();
/*  131:     */     }
/*  132: 276 */     ExpandedNameTable exnt = this.m_expandedNameTable;
/*  133:     */     
/*  134:     */ 
/*  135:     */ 
/*  136:     */ 
/*  137:     */ 
/*  138:     */ 
/*  139:     */ 
/*  140:     */ 
/*  141:     */ 
/*  142:     */ 
/*  143:     */ 
/*  144:     */ 
/*  145:     */ 
/*  146:     */ 
/*  147:     */ 
/*  148: 292 */     int expandedNameID = ((node.getLocalName() != null) || (type == 1) || (type != 2)) || (null != localName) ? exnt.getExpandedTypeID(nsURI, localName, type) : exnt.getExpandedTypeID(type);
/*  149:     */     
/*  150:     */ 
/*  151:     */ 
/*  152: 296 */     this.m_exptype.setElementAt(expandedNameID, nodeIndex);
/*  153:     */     
/*  154: 298 */     indexNode(expandedNameID, nodeIndex);
/*  155: 300 */     if (-1 != previousSibling) {
/*  156: 301 */       this.m_nextsib.setElementAt(nodeIndex, previousSibling);
/*  157:     */     }
/*  158: 305 */     if (type == 13) {
/*  159: 306 */       declareNamespaceInContext(parentIndex, nodeIndex);
/*  160:     */     }
/*  161: 308 */     return nodeIndex;
/*  162:     */   }
/*  163:     */   
/*  164:     */   public int getNumberOfNodes()
/*  165:     */   {
/*  166: 316 */     return this.m_nodes.size();
/*  167:     */   }
/*  168:     */   
/*  169:     */   protected boolean nextNode()
/*  170:     */   {
/*  171: 334 */     if (this.m_nodesAreProcessed) {
/*  172: 335 */       return false;
/*  173:     */     }
/*  174: 339 */     Node pos = this.m_pos;
/*  175: 340 */     Node next = null;
/*  176: 341 */     int nexttype = -1;
/*  177:     */     do
/*  178:     */     {
/*  179: 347 */       if (pos.hasChildNodes())
/*  180:     */       {
/*  181: 349 */         next = pos.getFirstChild();
/*  182: 353 */         if ((next != null) && (10 == next.getNodeType())) {
/*  183: 354 */           next = next.getNextSibling();
/*  184:     */         }
/*  185: 358 */         if (5 != pos.getNodeType())
/*  186:     */         {
/*  187: 360 */           this.m_last_parent = this.m_last_kid;
/*  188: 361 */           this.m_last_kid = -1;
/*  189: 363 */           if (null != this.m_wsfilter)
/*  190:     */           {
/*  191: 365 */             short wsv = this.m_wsfilter.getShouldStripSpace(makeNodeHandle(this.m_last_parent), this);
/*  192:     */             
/*  193: 367 */             boolean shouldStrip = 2 == wsv ? true : 3 == wsv ? getShouldStripWhitespace() : false;
/*  194:     */             
/*  195:     */ 
/*  196: 370 */             pushShouldStripWhitespace(shouldStrip);
/*  197:     */           }
/*  198:     */         }
/*  199:     */       }
/*  200:     */       else
/*  201:     */       {
/*  202: 378 */         if (this.m_last_kid != -1) {
/*  203: 382 */           if (this.m_firstch.elementAt(this.m_last_kid) == -2) {
/*  204: 383 */             this.m_firstch.setElementAt(-1, this.m_last_kid);
/*  205:     */           }
/*  206:     */         }
/*  207: 386 */         while (this.m_last_parent != -1)
/*  208:     */         {
/*  209: 390 */           next = pos.getNextSibling();
/*  210: 391 */           if ((next != null) && (10 == next.getNodeType())) {
/*  211: 392 */             next = next.getNextSibling();
/*  212:     */           }
/*  213: 394 */           if (next != null) {
/*  214:     */             break;
/*  215:     */           }
/*  216: 398 */           pos = pos.getParentNode();
/*  217: 399 */           if ((pos != null) || (
/*  218:     */           
/*  219:     */ 
/*  220:     */ 
/*  221:     */ 
/*  222:     */ 
/*  223:     */ 
/*  224:     */ 
/*  225:     */ 
/*  226:     */ 
/*  227:     */ 
/*  228:     */ 
/*  229:     */ 
/*  230: 412 */             (pos == null) || (5 != pos.getNodeType())))
/*  231:     */           {
/*  232: 420 */             popShouldStripWhitespace();
/*  233: 422 */             if (this.m_last_kid == -1) {
/*  234: 423 */               this.m_firstch.setElementAt(-1, this.m_last_parent);
/*  235:     */             } else {
/*  236: 425 */               this.m_nextsib.setElementAt(-1, this.m_last_kid);
/*  237:     */             }
/*  238: 426 */             this.m_last_parent = this.m_parent.elementAt(this.m_last_kid = this.m_last_parent);
/*  239:     */           }
/*  240:     */         }
/*  241: 429 */         if (this.m_last_parent == -1) {
/*  242: 430 */           next = null;
/*  243:     */         }
/*  244:     */       }
/*  245: 433 */       if (next != null) {
/*  246: 434 */         nexttype = next.getNodeType();
/*  247:     */       }
/*  248: 441 */       if (5 == nexttype) {
/*  249: 442 */         pos = next;
/*  250:     */       }
/*  251: 444 */     } while (5 == nexttype);
/*  252: 447 */     if (next == null)
/*  253:     */     {
/*  254: 449 */       this.m_nextsib.setElementAt(-1, 0);
/*  255: 450 */       this.m_nodesAreProcessed = true;
/*  256: 451 */       this.m_pos = null;
/*  257:     */       
/*  258:     */ 
/*  259:     */ 
/*  260:     */ 
/*  261:     */ 
/*  262:     */ 
/*  263:     */ 
/*  264:     */ 
/*  265: 460 */       return false;
/*  266:     */     }
/*  267: 478 */     boolean suppressNode = false;
/*  268: 479 */     Node lastTextNode = null;
/*  269:     */     
/*  270: 481 */     nexttype = next.getNodeType();
/*  271: 484 */     if ((3 == nexttype) || (4 == nexttype))
/*  272:     */     {
/*  273: 487 */       suppressNode = (null != this.m_wsfilter) && (getShouldStripWhitespace());
/*  274:     */       
/*  275:     */ 
/*  276:     */ 
/*  277: 491 */       Node n = next;
/*  278: 492 */       while (n != null)
/*  279:     */       {
/*  280: 494 */         lastTextNode = n;
/*  281: 496 */         if (3 == n.getNodeType()) {
/*  282: 497 */           nexttype = 3;
/*  283:     */         }
/*  284: 500 */         suppressNode &= XMLCharacterRecognizer.isWhiteSpace(n.getNodeValue());
/*  285:     */         
/*  286:     */ 
/*  287: 503 */         n = logicalNextDOMTextNode(n);
/*  288:     */       }
/*  289:     */     }
/*  290: 512 */     else if (7 == nexttype)
/*  291:     */     {
/*  292: 514 */       suppressNode = pos.getNodeName().toLowerCase().equals("xml");
/*  293:     */     }
/*  294: 518 */     if (!suppressNode)
/*  295:     */     {
/*  296: 523 */       int nextindex = addNode(next, this.m_last_parent, this.m_last_kid, nexttype);
/*  297:     */       
/*  298:     */ 
/*  299: 526 */       this.m_last_kid = nextindex;
/*  300: 528 */       if (1 == nexttype)
/*  301:     */       {
/*  302: 530 */         int attrIndex = -1;
/*  303:     */         
/*  304:     */ 
/*  305: 533 */         NamedNodeMap attrs = next.getAttributes();
/*  306: 534 */         int attrsize = attrs == null ? 0 : attrs.getLength();
/*  307: 535 */         if (attrsize > 0) {
/*  308: 537 */           for (int i = 0; i < attrsize; i++)
/*  309:     */           {
/*  310: 542 */             attrIndex = addNode(attrs.item(i), nextindex, attrIndex, -1);
/*  311:     */             
/*  312: 544 */             this.m_firstch.setElementAt(-1, attrIndex);
/*  313: 555 */             if ((!this.m_processedFirstElement) && ("xmlns:xml".equals(attrs.item(i).getNodeName()))) {
/*  314: 557 */               this.m_processedFirstElement = true;
/*  315:     */             }
/*  316:     */           }
/*  317:     */         }
/*  318: 562 */         if (!this.m_processedFirstElement)
/*  319:     */         {
/*  320: 570 */           attrIndex = addNode(new DOM2DTMdefaultNamespaceDeclarationNode((Element)next, "xml", "http://www.w3.org/XML/1998/namespace", makeNodeHandle((attrIndex == -1 ? nextindex : attrIndex) + 1)), nextindex, attrIndex, -1);
/*  321:     */           
/*  322:     */ 
/*  323:     */ 
/*  324:     */ 
/*  325: 575 */           this.m_firstch.setElementAt(-1, attrIndex);
/*  326: 576 */           this.m_processedFirstElement = true;
/*  327:     */         }
/*  328: 578 */         if (attrIndex != -1) {
/*  329: 579 */           this.m_nextsib.setElementAt(-1, attrIndex);
/*  330:     */         }
/*  331:     */       }
/*  332:     */     }
/*  333: 584 */     if ((3 == nexttype) || (4 == nexttype)) {
/*  334: 588 */       next = lastTextNode;
/*  335:     */     }
/*  336: 592 */     this.m_pos = next;
/*  337: 593 */     return true;
/*  338:     */   }
/*  339:     */   
/*  340:     */   public Node getNode(int nodeHandle)
/*  341:     */   {
/*  342: 607 */     int identity = makeNodeIdentity(nodeHandle);
/*  343:     */     
/*  344: 609 */     return (Node)this.m_nodes.elementAt(identity);
/*  345:     */   }
/*  346:     */   
/*  347:     */   protected Node lookupNode(int nodeIdentity)
/*  348:     */   {
/*  349: 621 */     return (Node)this.m_nodes.elementAt(nodeIdentity);
/*  350:     */   }
/*  351:     */   
/*  352:     */   protected int getNextNodeIdentity(int identity)
/*  353:     */   {
/*  354:     */     
/*  355: 636 */     if (identity >= this.m_nodes.size()) {
/*  356: 638 */       if (!nextNode()) {
/*  357: 639 */         identity = -1;
/*  358:     */       }
/*  359:     */     }
/*  360: 642 */     return identity;
/*  361:     */   }
/*  362:     */   
/*  363:     */   private int getHandleFromNode(Node node)
/*  364:     */   {
/*  365: 666 */     if (null != node)
/*  366:     */     {
/*  367: 668 */       int len = this.m_nodes.size();
/*  368:     */       
/*  369: 670 */       int i = 0;
/*  370:     */       boolean isMore;
/*  371:     */       do
/*  372:     */       {
/*  373: 673 */         for (; i < len; i++) {
/*  374: 675 */           if (this.m_nodes.elementAt(i) == node) {
/*  375: 676 */             return makeNodeHandle(i);
/*  376:     */           }
/*  377:     */         }
/*  378: 679 */         isMore = nextNode();
/*  379:     */         
/*  380: 681 */         len = this.m_nodes.size();
/*  381: 684 */       } while ((isMore) || (i < len));
/*  382:     */     }
/*  383: 687 */     return -1;
/*  384:     */   }
/*  385:     */   
/*  386:     */   public int getHandleOfNode(Node node)
/*  387:     */   {
/*  388: 705 */     if (null != node) {
/*  389: 710 */       if ((this.m_root == node) || ((this.m_root.getNodeType() == 9) && (this.m_root == node.getOwnerDocument())) || ((this.m_root.getNodeType() != 9) && (this.m_root.getOwnerDocument() == node.getOwnerDocument()))) {
/*  390: 722 */         for (Node cursor = node; cursor != null; cursor = cursor.getNodeType() != 2 ? cursor.getParentNode() : ((Attr)cursor).getOwnerElement()) {
/*  391: 729 */           if (cursor == this.m_root) {
/*  392: 731 */             return getHandleFromNode(node);
/*  393:     */           }
/*  394:     */         }
/*  395:     */       }
/*  396:     */     }
/*  397: 736 */     return -1;
/*  398:     */   }
/*  399:     */   
/*  400:     */   public int getAttributeNode(int nodeHandle, String namespaceURI, String name)
/*  401:     */   {
/*  402: 756 */     if (null == namespaceURI) {
/*  403: 757 */       namespaceURI = "";
/*  404:     */     }
/*  405: 759 */     int type = getNodeType(nodeHandle);
/*  406: 761 */     if (1 == type)
/*  407:     */     {
/*  408: 765 */       int identity = makeNodeIdentity(nodeHandle);
/*  409: 767 */       while (-1 != (identity = getNextNodeIdentity(identity)))
/*  410:     */       {
/*  411: 770 */         type = _type(identity);
/*  412: 779 */         if ((type != 2) && (type != 13)) {
/*  413:     */           break;
/*  414:     */         }
/*  415: 781 */         Node node = lookupNode(identity);
/*  416: 782 */         String nodeuri = node.getNamespaceURI();
/*  417: 784 */         if (null == nodeuri) {
/*  418: 785 */           nodeuri = "";
/*  419:     */         }
/*  420: 787 */         String nodelocalname = node.getLocalName();
/*  421: 789 */         if ((nodeuri.equals(namespaceURI)) && (name.equals(nodelocalname))) {
/*  422: 790 */           return makeNodeHandle(identity);
/*  423:     */         }
/*  424:     */       }
/*  425:     */     }
/*  426: 800 */     return -1;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public XMLString getStringValue(int nodeHandle)
/*  430:     */   {
/*  431: 815 */     int type = getNodeType(nodeHandle);
/*  432: 816 */     Node node = getNode(nodeHandle);
/*  433: 819 */     if ((1 == type) || (9 == type) || (11 == type))
/*  434:     */     {
/*  435: 822 */       FastStringBuffer buf = StringBufferPool.get();
/*  436:     */       String s;
/*  437:     */       try
/*  438:     */       {
/*  439: 827 */         getNodeData(node, buf);
/*  440:     */         
/*  441: 829 */         s = buf.length() > 0 ? buf.toString() : "";
/*  442:     */       }
/*  443:     */       finally
/*  444:     */       {
/*  445: 833 */         StringBufferPool.free(buf);
/*  446:     */       }
/*  447: 836 */       return this.m_xstrf.newstr(s);
/*  448:     */     }
/*  449: 838 */     if ((3 == type) || (4 == type))
/*  450:     */     {
/*  451: 847 */       FastStringBuffer buf = StringBufferPool.get();
/*  452: 848 */       while (node != null)
/*  453:     */       {
/*  454: 850 */         buf.append(node.getNodeValue());
/*  455: 851 */         node = logicalNextDOMTextNode(node);
/*  456:     */       }
/*  457: 853 */       String s = buf.length() > 0 ? buf.toString() : "";
/*  458: 854 */       StringBufferPool.free(buf);
/*  459: 855 */       return this.m_xstrf.newstr(s);
/*  460:     */     }
/*  461: 858 */     return this.m_xstrf.newstr(node.getNodeValue());
/*  462:     */   }
/*  463:     */   
/*  464:     */   public boolean isWhitespace(int nodeHandle)
/*  465:     */   {
/*  466: 870 */     int type = getNodeType(nodeHandle);
/*  467: 871 */     Node node = getNode(nodeHandle);
/*  468: 872 */     if ((3 == type) || (4 == type))
/*  469:     */     {
/*  470: 881 */       FastStringBuffer buf = StringBufferPool.get();
/*  471: 882 */       while (node != null)
/*  472:     */       {
/*  473: 884 */         buf.append(node.getNodeValue());
/*  474: 885 */         node = logicalNextDOMTextNode(node);
/*  475:     */       }
/*  476: 887 */       boolean b = buf.isWhitespace(0, buf.length());
/*  477: 888 */       StringBufferPool.free(buf);
/*  478: 889 */       return b;
/*  479:     */     }
/*  480: 891 */     return false;
/*  481:     */   }
/*  482:     */   
/*  483:     */   protected static void getNodeData(Node node, FastStringBuffer buf)
/*  484:     */   {
/*  485: 919 */     switch (node.getNodeType())
/*  486:     */     {
/*  487:     */     case 1: 
/*  488:     */     case 9: 
/*  489:     */     case 11: 
/*  490: 925 */       for (Node child = node.getFirstChild(); null != child; child = child.getNextSibling()) {
/*  491: 928 */         getNodeData(child, buf);
/*  492:     */       }
/*  493: 931 */       break;
/*  494:     */     case 2: 
/*  495:     */     case 3: 
/*  496:     */     case 4: 
/*  497: 935 */       buf.append(node.getNodeValue());
/*  498: 936 */       break;
/*  499:     */     case 7: 
/*  500:     */       break;
/*  501:     */     }
/*  502:     */   }
/*  503:     */   
/*  504:     */   public String getNodeName(int nodeHandle)
/*  505:     */   {
/*  506: 958 */     Node node = getNode(nodeHandle);
/*  507:     */     
/*  508:     */ 
/*  509: 961 */     return node.getNodeName();
/*  510:     */   }
/*  511:     */   
/*  512:     */   public String getNodeNameX(int nodeHandle)
/*  513:     */   {
/*  514: 976 */     short type = getNodeType(nodeHandle);
/*  515:     */     String name;
/*  516: 978 */     switch (type)
/*  517:     */     {
/*  518:     */     case 13: 
/*  519: 982 */       Node node = getNode(nodeHandle);
/*  520:     */       
/*  521:     */ 
/*  522: 985 */       name = node.getNodeName();
/*  523: 986 */       if (name.startsWith("xmlns:")) {
/*  524: 988 */         name = QName.getLocalPart(name);
/*  525: 990 */       } else if (name.equals("xmlns")) {
/*  526: 992 */         name = "";
/*  527:     */       }
/*  528: 995 */       break;
/*  529:     */     case 1: 
/*  530:     */     case 2: 
/*  531:     */     case 5: 
/*  532:     */     case 7: 
/*  533:1001 */       Node node = getNode(nodeHandle);
/*  534:     */       
/*  535:     */ 
/*  536:1004 */       name = node.getNodeName();
/*  537:     */       
/*  538:1006 */       break;
/*  539:     */     case 3: 
/*  540:     */     case 4: 
/*  541:     */     case 6: 
/*  542:     */     case 8: 
/*  543:     */     case 9: 
/*  544:     */     case 10: 
/*  545:     */     case 11: 
/*  546:     */     case 12: 
/*  547:     */     default: 
/*  548:1008 */       name = "";
/*  549:     */     }
/*  550:1011 */     return name;
/*  551:     */   }
/*  552:     */   
/*  553:     */   public String getLocalName(int nodeHandle)
/*  554:     */   {
/*  555:1026 */     int id = makeNodeIdentity(nodeHandle);
/*  556:1027 */     if (-1 == id) {
/*  557:1027 */       return null;
/*  558:     */     }
/*  559:1028 */     Node newnode = (Node)this.m_nodes.elementAt(id);
/*  560:1029 */     String newname = newnode.getLocalName();
/*  561:1030 */     if (null == newname)
/*  562:     */     {
/*  563:1033 */       String qname = newnode.getNodeName();
/*  564:1034 */       if ('#' == qname.charAt(0))
/*  565:     */       {
/*  566:1038 */         newname = "";
/*  567:     */       }
/*  568:     */       else
/*  569:     */       {
/*  570:1042 */         int index = qname.indexOf(':');
/*  571:1043 */         newname = index < 0 ? qname : qname.substring(index + 1);
/*  572:     */       }
/*  573:     */     }
/*  574:1046 */     return newname;
/*  575:     */   }
/*  576:     */   
/*  577:     */   public String getPrefix(int nodeHandle)
/*  578:     */   {
/*  579:1097 */     short type = getNodeType(nodeHandle);
/*  580:     */     String prefix;
/*  581:1099 */     switch (type)
/*  582:     */     {
/*  583:     */     case 13: 
/*  584:1103 */       Node node = getNode(nodeHandle);
/*  585:     */       
/*  586:     */ 
/*  587:1106 */       String qname = node.getNodeName();
/*  588:1107 */       int index = qname.indexOf(':');
/*  589:     */       
/*  590:1109 */       prefix = index < 0 ? "" : qname.substring(index + 1);
/*  591:     */       
/*  592:1111 */       break;
/*  593:     */     case 1: 
/*  594:     */     case 2: 
/*  595:1115 */       Node node = getNode(nodeHandle);
/*  596:     */       
/*  597:     */ 
/*  598:1118 */       String qname = node.getNodeName();
/*  599:1119 */       int index = qname.indexOf(':');
/*  600:     */       
/*  601:1121 */       prefix = index < 0 ? "" : qname.substring(0, index);
/*  602:     */       
/*  603:1123 */       break;
/*  604:     */     default: 
/*  605:1125 */       prefix = "";
/*  606:     */     }
/*  607:1128 */     return prefix;
/*  608:     */   }
/*  609:     */   
/*  610:     */   public String getNamespaceURI(int nodeHandle)
/*  611:     */   {
/*  612:1146 */     int id = makeNodeIdentity(nodeHandle);
/*  613:1147 */     if (id == -1) {
/*  614:1147 */       return null;
/*  615:     */     }
/*  616:1148 */     Node node = (Node)this.m_nodes.elementAt(id);
/*  617:1149 */     return node.getNamespaceURI();
/*  618:     */   }
/*  619:     */   
/*  620:     */   private Node logicalNextDOMTextNode(Node n)
/*  621:     */   {
/*  622:1190 */     Node p = n.getNextSibling();
/*  623:1191 */     if (p == null) {
/*  624:1194 */       for (n = n.getParentNode(); (n != null) && (5 == n.getNodeType()); n = n.getParentNode())
/*  625:     */       {
/*  626:1198 */         p = n.getNextSibling();
/*  627:1199 */         if (p != null) {
/*  628:     */           break;
/*  629:     */         }
/*  630:     */       }
/*  631:     */     }
/*  632:1203 */     n = p;
/*  633:1204 */     while ((n != null) && (5 == n.getNodeType())) {
/*  634:1207 */       if (n.hasChildNodes()) {
/*  635:1208 */         n = n.getFirstChild();
/*  636:     */       } else {
/*  637:1210 */         n = n.getNextSibling();
/*  638:     */       }
/*  639:     */     }
/*  640:1212 */     if (n != null)
/*  641:     */     {
/*  642:1215 */       int ntype = n.getNodeType();
/*  643:1216 */       if ((3 != ntype) && (4 != ntype)) {
/*  644:1217 */         n = null;
/*  645:     */       }
/*  646:     */     }
/*  647:1219 */     return n;
/*  648:     */   }
/*  649:     */   
/*  650:     */   public String getNodeValue(int nodeHandle)
/*  651:     */   {
/*  652:1236 */     int type = _exptype(makeNodeIdentity(nodeHandle));
/*  653:1237 */     type = -1 != type ? getNodeType(nodeHandle) : -1;
/*  654:1239 */     if ((3 != type) && (4 != type)) {
/*  655:1240 */       return getNode(nodeHandle).getNodeValue();
/*  656:     */     }
/*  657:1249 */     Node node = getNode(nodeHandle);
/*  658:1250 */     Node n = logicalNextDOMTextNode(node);
/*  659:1251 */     if (n == null) {
/*  660:1252 */       return node.getNodeValue();
/*  661:     */     }
/*  662:1254 */     FastStringBuffer buf = StringBufferPool.get();
/*  663:1255 */     buf.append(node.getNodeValue());
/*  664:1256 */     while (n != null)
/*  665:     */     {
/*  666:1258 */       buf.append(n.getNodeValue());
/*  667:1259 */       n = logicalNextDOMTextNode(n);
/*  668:     */     }
/*  669:1261 */     String s = buf.length() > 0 ? buf.toString() : "";
/*  670:1262 */     StringBufferPool.free(buf);
/*  671:1263 */     return s;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public String getDocumentTypeDeclarationSystemIdentifier()
/*  675:     */   {
/*  676:     */     Document doc;
/*  677:1279 */     if (this.m_root.getNodeType() == 9) {
/*  678:1280 */       doc = (Document)this.m_root;
/*  679:     */     } else {
/*  680:1282 */       doc = this.m_root.getOwnerDocument();
/*  681:     */     }
/*  682:1284 */     if (null != doc)
/*  683:     */     {
/*  684:1286 */       DocumentType dtd = doc.getDoctype();
/*  685:1288 */       if (null != dtd) {
/*  686:1290 */         return dtd.getSystemId();
/*  687:     */       }
/*  688:     */     }
/*  689:1294 */     return null;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public String getDocumentTypeDeclarationPublicIdentifier()
/*  693:     */   {
/*  694:     */     Document doc;
/*  695:1310 */     if (this.m_root.getNodeType() == 9) {
/*  696:1311 */       doc = (Document)this.m_root;
/*  697:     */     } else {
/*  698:1313 */       doc = this.m_root.getOwnerDocument();
/*  699:     */     }
/*  700:1315 */     if (null != doc)
/*  701:     */     {
/*  702:1317 */       DocumentType dtd = doc.getDoctype();
/*  703:1319 */       if (null != dtd) {
/*  704:1321 */         return dtd.getPublicId();
/*  705:     */       }
/*  706:     */     }
/*  707:1325 */     return null;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public int getElementById(String elementId)
/*  711:     */   {
/*  712:1348 */     Document doc = this.m_root.getNodeType() == 9 ? (Document)this.m_root : this.m_root.getOwnerDocument();
/*  713:1351 */     if (null != doc)
/*  714:     */     {
/*  715:1353 */       Node elem = doc.getElementById(elementId);
/*  716:1354 */       if (null != elem)
/*  717:     */       {
/*  718:1356 */         int elemHandle = getHandleFromNode(elem);
/*  719:1358 */         if (-1 == elemHandle)
/*  720:     */         {
/*  721:1360 */           int identity = this.m_nodes.size() - 1;
/*  722:1361 */           while (-1 != (identity = getNextNodeIdentity(identity)))
/*  723:     */           {
/*  724:1363 */             Node node = getNode(identity);
/*  725:1364 */             if (node == elem)
/*  726:     */             {
/*  727:1366 */               elemHandle = getHandleFromNode(elem);
/*  728:1367 */               break;
/*  729:     */             }
/*  730:     */           }
/*  731:     */         }
/*  732:1372 */         return elemHandle;
/*  733:     */       }
/*  734:     */     }
/*  735:1376 */     return -1;
/*  736:     */   }
/*  737:     */   
/*  738:     */   public String getUnparsedEntityURI(String name)
/*  739:     */   {
/*  740:1416 */     String url = "";
/*  741:1417 */     Document doc = this.m_root.getNodeType() == 9 ? (Document)this.m_root : this.m_root.getOwnerDocument();
/*  742:1420 */     if (null != doc)
/*  743:     */     {
/*  744:1422 */       DocumentType doctype = doc.getDoctype();
/*  745:1424 */       if (null != doctype)
/*  746:     */       {
/*  747:1426 */         NamedNodeMap entities = doctype.getEntities();
/*  748:1427 */         if (null == entities) {
/*  749:1428 */           return url;
/*  750:     */         }
/*  751:1429 */         Entity entity = (Entity)entities.getNamedItem(name);
/*  752:1430 */         if (null == entity) {
/*  753:1431 */           return url;
/*  754:     */         }
/*  755:1433 */         String notationName = entity.getNotationName();
/*  756:1435 */         if (null != notationName)
/*  757:     */         {
/*  758:1446 */           url = entity.getSystemId();
/*  759:1448 */           if (null == url) {
/*  760:1450 */             url = entity.getPublicId();
/*  761:     */           }
/*  762:     */         }
/*  763:     */       }
/*  764:     */     }
/*  765:1461 */     return url;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public boolean isAttributeSpecified(int attributeHandle)
/*  769:     */   {
/*  770:1475 */     int type = getNodeType(attributeHandle);
/*  771:1477 */     if (2 == type)
/*  772:     */     {
/*  773:1479 */       Attr attr = (Attr)getNode(attributeHandle);
/*  774:1480 */       return attr.getSpecified();
/*  775:     */     }
/*  776:1482 */     return false;
/*  777:     */   }
/*  778:     */   
/*  779:     */   public void setIncrementalSAXSource(IncrementalSAXSource source) {}
/*  780:     */   
/*  781:     */   public ContentHandler getContentHandler()
/*  782:     */   {
/*  783:1506 */     return null;
/*  784:     */   }
/*  785:     */   
/*  786:     */   public LexicalHandler getLexicalHandler()
/*  787:     */   {
/*  788:1522 */     return null;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public EntityResolver getEntityResolver()
/*  792:     */   {
/*  793:1534 */     return null;
/*  794:     */   }
/*  795:     */   
/*  796:     */   public DTDHandler getDTDHandler()
/*  797:     */   {
/*  798:1545 */     return null;
/*  799:     */   }
/*  800:     */   
/*  801:     */   public ErrorHandler getErrorHandler()
/*  802:     */   {
/*  803:1556 */     return null;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public DeclHandler getDeclHandler()
/*  807:     */   {
/*  808:1567 */     return null;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public boolean needsTwoThreads()
/*  812:     */   {
/*  813:1577 */     return false;
/*  814:     */   }
/*  815:     */   
/*  816:     */   private static boolean isSpace(char ch)
/*  817:     */   {
/*  818:1591 */     return XMLCharacterRecognizer.isWhiteSpace(ch);
/*  819:     */   }
/*  820:     */   
/*  821:     */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize)
/*  822:     */     throws SAXException
/*  823:     */   {
/*  824:1612 */     if (normalize)
/*  825:     */     {
/*  826:1614 */       XMLString str = getStringValue(nodeHandle);
/*  827:1615 */       str = str.fixWhiteSpace(true, true, false);
/*  828:1616 */       str.dispatchCharactersEvents(ch);
/*  829:     */     }
/*  830:     */     else
/*  831:     */     {
/*  832:1620 */       int type = getNodeType(nodeHandle);
/*  833:1621 */       Node node = getNode(nodeHandle);
/*  834:1622 */       dispatchNodeData(node, ch, 0);
/*  835:1625 */       if (3 != type)
/*  836:     */       {
/*  837:1625 */         if (4 != type) {}
/*  838:     */       }
/*  839:     */       else {
/*  840:1627 */         while (null != (node = logicalNextDOMTextNode(node))) {
/*  841:1629 */           dispatchNodeData(node, ch, 0);
/*  842:     */         }
/*  843:     */       }
/*  844:     */     }
/*  845:     */   }
/*  846:     */   
/*  847:     */   protected static void dispatchNodeData(Node node, ContentHandler ch, int depth)
/*  848:     */     throws SAXException
/*  849:     */   {
/*  850:1661 */     switch (node.getNodeType())
/*  851:     */     {
/*  852:     */     case 1: 
/*  853:     */     case 9: 
/*  854:     */     case 11: 
/*  855:1667 */       for (Node child = node.getFirstChild(); null != child; child = child.getNextSibling()) {
/*  856:1670 */         dispatchNodeData(child, ch, depth + 1);
/*  857:     */       }
/*  858:     */     case 7: 
/*  859:     */     case 8: 
/*  860:1673 */       if ((goto 150) || 
/*  861:     */       
/*  862:     */ 
/*  863:1676 */         (0 != depth)) {
/*  864:     */         break;
/*  865:     */       }
/*  866:     */     case 2: 
/*  867:     */     case 3: 
/*  868:     */     case 4: 
/*  869:1683 */       String str = node.getNodeValue();
/*  870:1684 */       if ((ch instanceof CharacterNodeHandler)) {
/*  871:1686 */         ((CharacterNodeHandler)ch).characters(node);
/*  872:     */       } else {
/*  873:1690 */         ch.characters(str.toCharArray(), 0, str.length());
/*  874:     */       }
/*  875:1692 */       break;
/*  876:     */     }
/*  877:     */   }
/*  878:     */   
/*  879:1702 */   TreeWalker m_walker = new TreeWalker(null);
/*  880:     */   
/*  881:     */   public void dispatchToEvents(int nodeHandle, ContentHandler ch)
/*  882:     */     throws SAXException
/*  883:     */   {
/*  884:1715 */     TreeWalker treeWalker = this.m_walker;
/*  885:1716 */     ContentHandler prevCH = treeWalker.getContentHandler();
/*  886:1718 */     if (null != prevCH) {
/*  887:1720 */       treeWalker = new TreeWalker(null);
/*  888:     */     }
/*  889:1722 */     treeWalker.setContentHandler(ch);
/*  890:     */     try
/*  891:     */     {
/*  892:1726 */       Node node = getNode(nodeHandle);
/*  893:1727 */       treeWalker.traverseFragment(node);
/*  894:     */     }
/*  895:     */     finally
/*  896:     */     {
/*  897:1731 */       treeWalker.setContentHandler(null);
/*  898:     */     }
/*  899:     */   }
/*  900:     */   
/*  901:     */   public void setProperty(String property, Object value) {}
/*  902:     */   
/*  903:     */   public SourceLocator getSourceLocatorFor(int node)
/*  904:     */   {
/*  905:1761 */     return null;
/*  906:     */   }
/*  907:     */   
/*  908:     */   public static abstract interface CharacterNodeHandler
/*  909:     */   {
/*  910:     */     public abstract void characters(Node paramNode)
/*  911:     */       throws SAXException;
/*  912:     */   }
/*  913:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.dom2dtm.DOM2DTM
 * JD-Core Version:    0.7.0.1
 */