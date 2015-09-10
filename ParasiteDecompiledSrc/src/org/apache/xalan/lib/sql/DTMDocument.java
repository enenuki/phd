/*    1:     */ package org.apache.xalan.lib.sql;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintStream;
/*    7:     */ import javax.xml.transform.SourceLocator;
/*    8:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*    9:     */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   10:     */ import org.apache.xml.dtm.DTMManager;
/*   11:     */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   12:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators;
/*   13:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseTraversers;
/*   14:     */ import org.apache.xml.dtm.ref.ExpandedNameTable;
/*   15:     */ import org.apache.xml.utils.FastStringBuffer;
/*   16:     */ import org.apache.xml.utils.StringBufferPool;
/*   17:     */ import org.apache.xml.utils.SuballocatedIntVector;
/*   18:     */ import org.apache.xml.utils.XMLString;
/*   19:     */ import org.apache.xml.utils.XMLStringFactory;
/*   20:     */ import org.w3c.dom.Node;
/*   21:     */ import org.xml.sax.ContentHandler;
/*   22:     */ import org.xml.sax.DTDHandler;
/*   23:     */ import org.xml.sax.EntityResolver;
/*   24:     */ import org.xml.sax.ErrorHandler;
/*   25:     */ import org.xml.sax.SAXException;
/*   26:     */ import org.xml.sax.ext.DeclHandler;
/*   27:     */ import org.xml.sax.ext.LexicalHandler;
/*   28:     */ 
/*   29:     */ public class DTMDocument
/*   30:     */   extends DTMDefaultBaseIterators
/*   31:     */ {
/*   32:  70 */   private boolean DEBUG = false;
/*   33:     */   protected static final String S_NAMESPACE = "http://xml.apache.org/xalan/SQLExtension";
/*   34:     */   protected static final String S_ATTRIB_NOT_SUPPORTED = "Not Supported";
/*   35:     */   protected static final String S_ISTRUE = "true";
/*   36:     */   protected static final String S_ISFALSE = "false";
/*   37:     */   protected static final String S_DOCUMENT = "#root";
/*   38:     */   protected static final String S_TEXT_NODE = "#text";
/*   39:     */   protected static final String S_ELEMENT_NODE = "#element";
/*   40:  98 */   protected int m_Document_TypeID = 0;
/*   41: 101 */   protected int m_TextNode_TypeID = 0;
/*   42: 107 */   protected ObjectArray m_ObjectArray = new ObjectArray();
/*   43:     */   protected SuballocatedIntVector m_attribute;
/*   44:     */   protected int m_DocumentIdx;
/*   45:     */   
/*   46:     */   public DTMDocument(DTMManager mgr, int ident)
/*   47:     */   {
/*   48: 132 */     super(mgr, null, ident, null, mgr.getXMLStringFactory(), true);
/*   49:     */     
/*   50:     */ 
/*   51: 135 */     this.m_attribute = new SuballocatedIntVector(512);
/*   52:     */   }
/*   53:     */   
/*   54:     */   private int allocateNodeObject(Object o)
/*   55:     */   {
/*   56: 148 */     this.m_size += 1;
/*   57: 149 */     return this.m_ObjectArray.append(o);
/*   58:     */   }
/*   59:     */   
/*   60:     */   protected int addElementWithData(Object o, int level, int extendedType, int parent, int prevsib)
/*   61:     */   {
/*   62: 162 */     int elementIdx = addElement(level, extendedType, parent, prevsib);
/*   63:     */     
/*   64: 164 */     int data = allocateNodeObject(o);
/*   65: 165 */     this.m_firstch.setElementAt(data, elementIdx);
/*   66:     */     
/*   67: 167 */     this.m_exptype.setElementAt(this.m_TextNode_TypeID, data);
/*   68:     */     
/*   69: 169 */     this.m_parent.setElementAt(elementIdx, data);
/*   70:     */     
/*   71: 171 */     this.m_prevsib.setElementAt(-1, data);
/*   72: 172 */     this.m_nextsib.setElementAt(-1, data);
/*   73: 173 */     this.m_attribute.setElementAt(-1, data);
/*   74: 174 */     this.m_firstch.setElementAt(-1, data);
/*   75:     */     
/*   76: 176 */     return elementIdx;
/*   77:     */   }
/*   78:     */   
/*   79:     */   protected int addElement(int level, int extendedType, int parent, int prevsib)
/*   80:     */   {
/*   81: 188 */     int node = -1;
/*   82:     */     try
/*   83:     */     {
/*   84: 193 */       node = allocateNodeObject("#element");
/*   85:     */       
/*   86: 195 */       this.m_exptype.setElementAt(extendedType, node);
/*   87: 196 */       this.m_nextsib.setElementAt(-1, node);
/*   88: 197 */       this.m_prevsib.setElementAt(prevsib, node);
/*   89:     */       
/*   90: 199 */       this.m_parent.setElementAt(parent, node);
/*   91: 200 */       this.m_firstch.setElementAt(-1, node);
/*   92:     */       
/*   93: 202 */       this.m_attribute.setElementAt(-1, node);
/*   94: 204 */       if (prevsib != -1)
/*   95:     */       {
/*   96: 208 */         if (this.m_nextsib.elementAt(prevsib) != -1) {
/*   97: 209 */           this.m_nextsib.setElementAt(this.m_nextsib.elementAt(prevsib), node);
/*   98:     */         }
/*   99: 212 */         this.m_nextsib.setElementAt(node, prevsib);
/*  100:     */       }
/*  101: 220 */       if ((parent != -1) && (this.m_prevsib.elementAt(node) == -1)) {
/*  102: 222 */         this.m_firstch.setElementAt(node, parent);
/*  103:     */       }
/*  104:     */     }
/*  105:     */     catch (Exception e)
/*  106:     */     {
/*  107: 227 */       error("Error in addElement: " + e.getMessage());
/*  108:     */     }
/*  109: 230 */     return node;
/*  110:     */   }
/*  111:     */   
/*  112:     */   protected int addAttributeToNode(Object o, int extendedType, int pnode)
/*  113:     */   {
/*  114: 246 */     int attrib = -1;
/*  115:     */     
/*  116: 248 */     int lastattrib = -1;
/*  117:     */     try
/*  118:     */     {
/*  119: 254 */       attrib = allocateNodeObject(o);
/*  120:     */       
/*  121: 256 */       this.m_attribute.setElementAt(-1, attrib);
/*  122: 257 */       this.m_exptype.setElementAt(extendedType, attrib);
/*  123:     */       
/*  124:     */ 
/*  125:     */ 
/*  126: 261 */       this.m_nextsib.setElementAt(-1, attrib);
/*  127: 262 */       this.m_prevsib.setElementAt(-1, attrib);
/*  128:     */       
/*  129:     */ 
/*  130:     */ 
/*  131: 266 */       this.m_parent.setElementAt(pnode, attrib);
/*  132: 267 */       this.m_firstch.setElementAt(-1, attrib);
/*  133: 269 */       if (this.m_attribute.elementAt(pnode) != -1)
/*  134:     */       {
/*  135: 273 */         lastattrib = this.m_attribute.elementAt(pnode);
/*  136: 274 */         this.m_nextsib.setElementAt(lastattrib, attrib);
/*  137: 275 */         this.m_prevsib.setElementAt(attrib, lastattrib);
/*  138:     */       }
/*  139: 279 */       this.m_attribute.setElementAt(attrib, pnode);
/*  140:     */     }
/*  141:     */     catch (Exception e)
/*  142:     */     {
/*  143: 283 */       error("Error in addAttributeToNode: " + e.getMessage());
/*  144:     */     }
/*  145: 286 */     return attrib;
/*  146:     */   }
/*  147:     */   
/*  148:     */   protected void cloneAttributeFromNode(int toNode, int fromNode)
/*  149:     */   {
/*  150:     */     try
/*  151:     */     {
/*  152: 302 */       if (this.m_attribute.elementAt(toNode) != -1) {
/*  153: 304 */         error("Cloneing Attributes, where from Node already had addtibures assigned");
/*  154:     */       }
/*  155: 307 */       this.m_attribute.setElementAt(this.m_attribute.elementAt(fromNode), toNode);
/*  156:     */     }
/*  157:     */     catch (Exception e)
/*  158:     */     {
/*  159: 311 */       error("Cloning attributes");
/*  160:     */     }
/*  161:     */   }
/*  162:     */   
/*  163:     */   public int getFirstAttribute(int parm1)
/*  164:     */   {
/*  165: 322 */     if (this.DEBUG) {
/*  166: 322 */       System.out.println("getFirstAttribute(" + parm1 + ")");
/*  167:     */     }
/*  168: 323 */     int nodeIdx = makeNodeIdentity(parm1);
/*  169: 324 */     if (nodeIdx != -1)
/*  170:     */     {
/*  171: 326 */       int attribIdx = this.m_attribute.elementAt(nodeIdx);
/*  172: 327 */       return makeNodeHandle(attribIdx);
/*  173:     */     }
/*  174: 329 */     return -1;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public String getNodeValue(int parm1)
/*  178:     */   {
/*  179: 338 */     if (this.DEBUG) {
/*  180: 338 */       System.out.println("getNodeValue(" + parm1 + ")");
/*  181:     */     }
/*  182:     */     try
/*  183:     */     {
/*  184: 341 */       Object o = this.m_ObjectArray.getAt(makeNodeIdentity(parm1));
/*  185: 342 */       if ((o != null) && (o != "#element")) {
/*  186: 344 */         return o.toString();
/*  187:     */       }
/*  188: 348 */       return "";
/*  189:     */     }
/*  190:     */     catch (Exception e)
/*  191:     */     {
/*  192: 353 */       error("Getting String Value");
/*  193:     */     }
/*  194: 354 */     return null;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public XMLString getStringValue(int nodeHandle)
/*  198:     */   {
/*  199: 370 */     int nodeIdx = makeNodeIdentity(nodeHandle);
/*  200: 371 */     if (this.DEBUG) {
/*  201: 371 */       System.out.println("getStringValue(" + nodeIdx + ")");
/*  202:     */     }
/*  203: 373 */     Object o = this.m_ObjectArray.getAt(nodeIdx);
/*  204: 374 */     if (o == "#element")
/*  205:     */     {
/*  206: 376 */       FastStringBuffer buf = StringBufferPool.get();
/*  207:     */       String s;
/*  208:     */       try
/*  209:     */       {
/*  210: 381 */         getNodeData(nodeIdx, buf);
/*  211:     */         
/*  212: 383 */         s = buf.length() > 0 ? buf.toString() : "";
/*  213:     */       }
/*  214:     */       finally
/*  215:     */       {
/*  216: 387 */         StringBufferPool.free(buf);
/*  217:     */       }
/*  218: 390 */       return this.m_xstrf.newstr(s);
/*  219:     */     }
/*  220: 392 */     if (o != null) {
/*  221: 394 */       return this.m_xstrf.newstr(o.toString());
/*  222:     */     }
/*  223: 397 */     return this.m_xstrf.emptystr();
/*  224:     */   }
/*  225:     */   
/*  226:     */   protected void getNodeData(int nodeIdx, FastStringBuffer buf)
/*  227:     */   {
/*  228: 424 */     for (int child = _firstch(nodeIdx); child != -1; child = _nextsib(child))
/*  229:     */     {
/*  230: 426 */       Object o = this.m_ObjectArray.getAt(child);
/*  231: 427 */       if (o == "#element") {
/*  232: 428 */         getNodeData(child, buf);
/*  233: 429 */       } else if (o != null) {
/*  234: 430 */         buf.append(o.toString());
/*  235:     */       }
/*  236:     */     }
/*  237:     */   }
/*  238:     */   
/*  239:     */   public int getNextAttribute(int parm1)
/*  240:     */   {
/*  241: 443 */     int nodeIdx = makeNodeIdentity(parm1);
/*  242: 444 */     if (this.DEBUG) {
/*  243: 444 */       System.out.println("getNextAttribute(" + nodeIdx + ")");
/*  244:     */     }
/*  245: 445 */     if (nodeIdx != -1) {
/*  246: 445 */       return makeNodeHandle(this.m_nextsib.elementAt(nodeIdx));
/*  247:     */     }
/*  248: 446 */     return -1;
/*  249:     */   }
/*  250:     */   
/*  251:     */   protected int getNumberOfNodes()
/*  252:     */   {
/*  253: 455 */     if (this.DEBUG) {
/*  254: 455 */       System.out.println("getNumberOfNodes()");
/*  255:     */     }
/*  256: 456 */     return this.m_size;
/*  257:     */   }
/*  258:     */   
/*  259:     */   protected boolean nextNode()
/*  260:     */   {
/*  261: 464 */     if (this.DEBUG) {
/*  262: 464 */       System.out.println("nextNode()");
/*  263:     */     }
/*  264: 465 */     return false;
/*  265:     */   }
/*  266:     */   
/*  267:     */   protected void createExpandedNameTable()
/*  268:     */   {
/*  269: 477 */     this.m_Document_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "#root", 9);
/*  270:     */     
/*  271:     */ 
/*  272: 480 */     this.m_TextNode_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "#text", 3);
/*  273:     */   }
/*  274:     */   
/*  275:     */   public void dumpDTM()
/*  276:     */   {
/*  277:     */     try
/*  278:     */     {
/*  279: 493 */       File f = new File("DTMDump.txt");
/*  280: 494 */       System.err.println("Dumping... " + f.getAbsolutePath());
/*  281: 495 */       PrintStream ps = new PrintStream(new FileOutputStream(f));
/*  282: 497 */       while (nextNode()) {}
/*  283: 499 */       int nRecords = this.m_size;
/*  284:     */       
/*  285: 501 */       ps.println("Total nodes: " + nRecords);
/*  286: 503 */       for (int i = 0; i < nRecords; i++)
/*  287:     */       {
/*  288: 505 */         ps.println("=========== " + i + " ===========");
/*  289: 506 */         ps.println("NodeName: " + getNodeName(makeNodeHandle(i)));
/*  290: 507 */         ps.println("NodeNameX: " + getNodeNameX(makeNodeHandle(i)));
/*  291: 508 */         ps.println("LocalName: " + getLocalName(makeNodeHandle(i)));
/*  292: 509 */         ps.println("NamespaceURI: " + getNamespaceURI(makeNodeHandle(i)));
/*  293: 510 */         ps.println("Prefix: " + getPrefix(makeNodeHandle(i)));
/*  294:     */         
/*  295: 512 */         int exTypeID = getExpandedTypeID(makeNodeHandle(i));
/*  296:     */         
/*  297: 514 */         ps.println("Expanded Type ID: " + Integer.toHexString(exTypeID));
/*  298:     */         
/*  299:     */ 
/*  300: 517 */         int type = getNodeType(makeNodeHandle(i));
/*  301:     */         String typestring;
/*  302: 520 */         switch (type)
/*  303:     */         {
/*  304:     */         case 2: 
/*  305: 523 */           typestring = "ATTRIBUTE_NODE";
/*  306: 524 */           break;
/*  307:     */         case 4: 
/*  308: 526 */           typestring = "CDATA_SECTION_NODE";
/*  309: 527 */           break;
/*  310:     */         case 8: 
/*  311: 529 */           typestring = "COMMENT_NODE";
/*  312: 530 */           break;
/*  313:     */         case 11: 
/*  314: 532 */           typestring = "DOCUMENT_FRAGMENT_NODE";
/*  315: 533 */           break;
/*  316:     */         case 9: 
/*  317: 535 */           typestring = "DOCUMENT_NODE";
/*  318: 536 */           break;
/*  319:     */         case 10: 
/*  320: 538 */           typestring = "DOCUMENT_NODE";
/*  321: 539 */           break;
/*  322:     */         case 1: 
/*  323: 541 */           typestring = "ELEMENT_NODE";
/*  324: 542 */           break;
/*  325:     */         case 6: 
/*  326: 544 */           typestring = "ENTITY_NODE";
/*  327: 545 */           break;
/*  328:     */         case 5: 
/*  329: 547 */           typestring = "ENTITY_REFERENCE_NODE";
/*  330: 548 */           break;
/*  331:     */         case 13: 
/*  332: 550 */           typestring = "NAMESPACE_NODE";
/*  333: 551 */           break;
/*  334:     */         case 12: 
/*  335: 553 */           typestring = "NOTATION_NODE";
/*  336: 554 */           break;
/*  337:     */         case -1: 
/*  338: 556 */           typestring = "NULL";
/*  339: 557 */           break;
/*  340:     */         case 7: 
/*  341: 559 */           typestring = "PROCESSING_INSTRUCTION_NODE";
/*  342: 560 */           break;
/*  343:     */         case 3: 
/*  344: 562 */           typestring = "TEXT_NODE";
/*  345: 563 */           break;
/*  346:     */         case 0: 
/*  347:     */         default: 
/*  348: 565 */           typestring = "Unknown!";
/*  349:     */         }
/*  350: 569 */         ps.println("Type: " + typestring);
/*  351:     */         
/*  352: 571 */         int firstChild = _firstch(i);
/*  353: 573 */         if (-1 == firstChild) {
/*  354: 574 */           ps.println("First child: DTM.NULL");
/*  355: 575 */         } else if (-2 == firstChild) {
/*  356: 576 */           ps.println("First child: NOTPROCESSED");
/*  357:     */         } else {
/*  358: 578 */           ps.println("First child: " + firstChild);
/*  359:     */         }
/*  360: 580 */         int prevSibling = _prevsib(i);
/*  361: 582 */         if (-1 == prevSibling) {
/*  362: 583 */           ps.println("Prev sibling: DTM.NULL");
/*  363: 584 */         } else if (-2 == prevSibling) {
/*  364: 585 */           ps.println("Prev sibling: NOTPROCESSED");
/*  365:     */         } else {
/*  366: 587 */           ps.println("Prev sibling: " + prevSibling);
/*  367:     */         }
/*  368: 589 */         int nextSibling = _nextsib(i);
/*  369: 591 */         if (-1 == nextSibling) {
/*  370: 592 */           ps.println("Next sibling: DTM.NULL");
/*  371: 593 */         } else if (-2 == nextSibling) {
/*  372: 594 */           ps.println("Next sibling: NOTPROCESSED");
/*  373:     */         } else {
/*  374: 596 */           ps.println("Next sibling: " + nextSibling);
/*  375:     */         }
/*  376: 598 */         int parent = _parent(i);
/*  377: 600 */         if (-1 == parent) {
/*  378: 601 */           ps.println("Parent: DTM.NULL");
/*  379: 602 */         } else if (-2 == parent) {
/*  380: 603 */           ps.println("Parent: NOTPROCESSED");
/*  381:     */         } else {
/*  382: 605 */           ps.println("Parent: " + parent);
/*  383:     */         }
/*  384: 607 */         int level = _level(i);
/*  385:     */         
/*  386: 609 */         ps.println("Level: " + level);
/*  387: 610 */         ps.println("Node Value: " + getNodeValue(i));
/*  388: 611 */         ps.println("String Value: " + getStringValue(i));
/*  389:     */         
/*  390: 613 */         ps.println("First Attribute Node: " + this.m_attribute.elementAt(i));
/*  391:     */       }
/*  392:     */     }
/*  393:     */     catch (IOException ioe)
/*  394:     */     {
/*  395: 619 */       ioe.printStackTrace(System.err);
/*  396: 620 */       throw new RuntimeException(ioe.getMessage());
/*  397:     */     }
/*  398:     */   }
/*  399:     */   
/*  400:     */   protected static void dispatchNodeData(Node node, ContentHandler ch, int depth)
/*  401:     */     throws SAXException
/*  402:     */   {
/*  403: 651 */     switch (node.getNodeType())
/*  404:     */     {
/*  405:     */     case 1: 
/*  406:     */     case 9: 
/*  407:     */     case 11: 
/*  408: 657 */       for (Node child = node.getFirstChild(); null != child; child = child.getNextSibling()) {
/*  409: 660 */         dispatchNodeData(child, ch, depth + 1);
/*  410:     */       }
/*  411:     */     case 7: 
/*  412:     */     case 8: 
/*  413: 663 */       if ((goto 150) || 
/*  414:     */       
/*  415:     */ 
/*  416: 666 */         (0 != depth)) {
/*  417:     */         break;
/*  418:     */       }
/*  419:     */     case 2: 
/*  420:     */     case 3: 
/*  421:     */     case 4: 
/*  422: 673 */       String str = node.getNodeValue();
/*  423: 674 */       if ((ch instanceof CharacterNodeHandler)) {
/*  424: 676 */         ((CharacterNodeHandler)ch).characters(node);
/*  425:     */       } else {
/*  426: 680 */         ch.characters(str.toCharArray(), 0, str.length());
/*  427:     */       }
/*  428: 682 */       break;
/*  429:     */     }
/*  430:     */   }
/*  431:     */   
/*  432:     */   public void setProperty(String property, Object value) {}
/*  433:     */   
/*  434:     */   public SourceLocator getSourceLocatorFor(int node)
/*  435:     */   {
/*  436: 718 */     return null;
/*  437:     */   }
/*  438:     */   
/*  439:     */   protected int getNextNodeIdentity(int parm1)
/*  440:     */   {
/*  441: 727 */     if (this.DEBUG) {
/*  442: 727 */       System.out.println("getNextNodeIdenty(" + parm1 + ")");
/*  443:     */     }
/*  444: 728 */     return -1;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public int getAttributeNode(int parm1, String parm2, String parm3)
/*  448:     */   {
/*  449: 739 */     if (this.DEBUG) {
/*  450: 741 */       System.out.println("getAttributeNode(" + parm1 + "," + parm2 + "," + parm3 + ")");
/*  451:     */     }
/*  452: 747 */     return -1;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public String getLocalName(int parm1)
/*  456:     */   {
/*  457: 757 */     int exID = getExpandedTypeID(parm1);
/*  458: 759 */     if (this.DEBUG)
/*  459:     */     {
/*  460: 761 */       this.DEBUG = false;
/*  461: 762 */       System.out.print("getLocalName(" + parm1 + ") -> ");
/*  462: 763 */       System.out.println("..." + getLocalNameFromExpandedNameID(exID));
/*  463: 764 */       this.DEBUG = true;
/*  464:     */     }
/*  465: 767 */     return getLocalNameFromExpandedNameID(exID);
/*  466:     */   }
/*  467:     */   
/*  468:     */   public String getNodeName(int parm1)
/*  469:     */   {
/*  470: 777 */     int exID = getExpandedTypeID(parm1);
/*  471: 778 */     if (this.DEBUG)
/*  472:     */     {
/*  473: 780 */       this.DEBUG = false;
/*  474: 781 */       System.out.print("getLocalName(" + parm1 + ") -> ");
/*  475: 782 */       System.out.println("..." + getLocalNameFromExpandedNameID(exID));
/*  476: 783 */       this.DEBUG = true;
/*  477:     */     }
/*  478: 785 */     return getLocalNameFromExpandedNameID(exID);
/*  479:     */   }
/*  480:     */   
/*  481:     */   public boolean isAttributeSpecified(int parm1)
/*  482:     */   {
/*  483: 794 */     if (this.DEBUG) {
/*  484: 794 */       System.out.println("isAttributeSpecified(" + parm1 + ")");
/*  485:     */     }
/*  486: 795 */     return false;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public String getUnparsedEntityURI(String parm1)
/*  490:     */   {
/*  491: 804 */     if (this.DEBUG) {
/*  492: 804 */       System.out.println("getUnparsedEntityURI(" + parm1 + ")");
/*  493:     */     }
/*  494: 805 */     return "";
/*  495:     */   }
/*  496:     */   
/*  497:     */   public DTDHandler getDTDHandler()
/*  498:     */   {
/*  499: 813 */     if (this.DEBUG) {
/*  500: 813 */       System.out.println("getDTDHandler()");
/*  501:     */     }
/*  502: 814 */     return null;
/*  503:     */   }
/*  504:     */   
/*  505:     */   public String getPrefix(int parm1)
/*  506:     */   {
/*  507: 823 */     if (this.DEBUG) {
/*  508: 823 */       System.out.println("getPrefix(" + parm1 + ")");
/*  509:     */     }
/*  510: 824 */     return "";
/*  511:     */   }
/*  512:     */   
/*  513:     */   public EntityResolver getEntityResolver()
/*  514:     */   {
/*  515: 832 */     if (this.DEBUG) {
/*  516: 832 */       System.out.println("getEntityResolver()");
/*  517:     */     }
/*  518: 833 */     return null;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public String getDocumentTypeDeclarationPublicIdentifier()
/*  522:     */   {
/*  523: 841 */     if (this.DEBUG) {
/*  524: 841 */       System.out.println("get_DTD_PubId()");
/*  525:     */     }
/*  526: 842 */     return "";
/*  527:     */   }
/*  528:     */   
/*  529:     */   public LexicalHandler getLexicalHandler()
/*  530:     */   {
/*  531: 850 */     if (this.DEBUG) {
/*  532: 850 */       System.out.println("getLexicalHandler()");
/*  533:     */     }
/*  534: 851 */     return null;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public boolean needsTwoThreads()
/*  538:     */   {
/*  539: 858 */     if (this.DEBUG) {
/*  540: 858 */       System.out.println("needsTwoThreads()");
/*  541:     */     }
/*  542: 859 */     return false;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public ContentHandler getContentHandler()
/*  546:     */   {
/*  547: 867 */     if (this.DEBUG) {
/*  548: 867 */       System.out.println("getContentHandler()");
/*  549:     */     }
/*  550: 868 */     return null;
/*  551:     */   }
/*  552:     */   
/*  553:     */   public void dispatchToEvents(int parm1, ContentHandler parm2)
/*  554:     */     throws SAXException
/*  555:     */   {
/*  556: 879 */     if (this.DEBUG) {
/*  557: 881 */       System.out.println("dispathcToEvents(" + parm1 + "," + parm2 + ")");
/*  558:     */     }
/*  559:     */   }
/*  560:     */   
/*  561:     */   public String getNamespaceURI(int parm1)
/*  562:     */   {
/*  563: 895 */     if (this.DEBUG) {
/*  564: 895 */       System.out.println("getNamespaceURI(" + parm1 + ")");
/*  565:     */     }
/*  566: 896 */     return "";
/*  567:     */   }
/*  568:     */   
/*  569:     */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize)
/*  570:     */     throws SAXException
/*  571:     */   {
/*  572: 908 */     if (this.DEBUG) {
/*  573: 910 */       System.out.println("dispatchCharacterEvents(" + nodeHandle + "," + ch + "," + normalize + ")");
/*  574:     */     }
/*  575: 916 */     if (normalize)
/*  576:     */     {
/*  577: 918 */       XMLString str = getStringValue(nodeHandle);
/*  578: 919 */       str = str.fixWhiteSpace(true, true, false);
/*  579: 920 */       str.dispatchCharactersEvents(ch);
/*  580:     */     }
/*  581:     */     else
/*  582:     */     {
/*  583: 924 */       Node node = getNode(nodeHandle);
/*  584: 925 */       dispatchNodeData(node, ch, 0);
/*  585:     */     }
/*  586:     */   }
/*  587:     */   
/*  588:     */   public boolean supportsPreStripping()
/*  589:     */   {
/*  590: 935 */     if (this.DEBUG) {
/*  591: 935 */       System.out.println("supportsPreStripping()");
/*  592:     */     }
/*  593: 936 */     return super.supportsPreStripping();
/*  594:     */   }
/*  595:     */   
/*  596:     */   protected int _exptype(int parm1)
/*  597:     */   {
/*  598: 945 */     if (this.DEBUG) {
/*  599: 945 */       System.out.println("_exptype(" + parm1 + ")");
/*  600:     */     }
/*  601: 946 */     return super._exptype(parm1);
/*  602:     */   }
/*  603:     */   
/*  604:     */   protected SuballocatedIntVector findNamespaceContext(int parm1)
/*  605:     */   {
/*  606: 955 */     if (this.DEBUG) {
/*  607: 955 */       System.out.println("SuballocatedIntVector(" + parm1 + ")");
/*  608:     */     }
/*  609: 956 */     return super.findNamespaceContext(parm1);
/*  610:     */   }
/*  611:     */   
/*  612:     */   protected int _prevsib(int parm1)
/*  613:     */   {
/*  614: 965 */     if (this.DEBUG) {
/*  615: 965 */       System.out.println("_prevsib(" + parm1 + ")");
/*  616:     */     }
/*  617: 966 */     return super._prevsib(parm1);
/*  618:     */   }
/*  619:     */   
/*  620:     */   protected short _type(int parm1)
/*  621:     */   {
/*  622: 976 */     if (this.DEBUG) {
/*  623: 976 */       System.out.println("_type(" + parm1 + ")");
/*  624:     */     }
/*  625: 977 */     return super._type(parm1);
/*  626:     */   }
/*  627:     */   
/*  628:     */   public Node getNode(int parm1)
/*  629:     */   {
/*  630: 986 */     if (this.DEBUG) {
/*  631: 986 */       System.out.println("getNode(" + parm1 + ")");
/*  632:     */     }
/*  633: 987 */     return super.getNode(parm1);
/*  634:     */   }
/*  635:     */   
/*  636:     */   public int getPreviousSibling(int parm1)
/*  637:     */   {
/*  638: 996 */     if (this.DEBUG) {
/*  639: 996 */       System.out.println("getPrevSib(" + parm1 + ")");
/*  640:     */     }
/*  641: 997 */     return super.getPreviousSibling(parm1);
/*  642:     */   }
/*  643:     */   
/*  644:     */   public String getDocumentStandalone(int parm1)
/*  645:     */   {
/*  646:1006 */     if (this.DEBUG) {
/*  647:1006 */       System.out.println("getDOcStandAlone(" + parm1 + ")");
/*  648:     */     }
/*  649:1007 */     return super.getDocumentStandalone(parm1);
/*  650:     */   }
/*  651:     */   
/*  652:     */   public String getNodeNameX(int parm1)
/*  653:     */   {
/*  654:1016 */     if (this.DEBUG) {
/*  655:1016 */       System.out.println("getNodeNameX(" + parm1 + ")");
/*  656:     */     }
/*  657:1018 */     return getNodeName(parm1);
/*  658:     */   }
/*  659:     */   
/*  660:     */   public void setFeature(String parm1, boolean parm2)
/*  661:     */   {
/*  662:1029 */     if (this.DEBUG) {
/*  663:1031 */       System.out.println("setFeature(" + parm1 + "," + parm2 + ")");
/*  664:     */     }
/*  665:1036 */     super.setFeature(parm1, parm2);
/*  666:     */   }
/*  667:     */   
/*  668:     */   protected int _parent(int parm1)
/*  669:     */   {
/*  670:1045 */     if (this.DEBUG) {
/*  671:1045 */       System.out.println("_parent(" + parm1 + ")");
/*  672:     */     }
/*  673:1046 */     return super._parent(parm1);
/*  674:     */   }
/*  675:     */   
/*  676:     */   protected void indexNode(int parm1, int parm2)
/*  677:     */   {
/*  678:1056 */     if (this.DEBUG) {
/*  679:1056 */       System.out.println("indexNode(" + parm1 + "," + parm2 + ")");
/*  680:     */     }
/*  681:1057 */     super.indexNode(parm1, parm2);
/*  682:     */   }
/*  683:     */   
/*  684:     */   protected boolean getShouldStripWhitespace()
/*  685:     */   {
/*  686:1065 */     if (this.DEBUG) {
/*  687:1065 */       System.out.println("getShouldStripWS()");
/*  688:     */     }
/*  689:1066 */     return super.getShouldStripWhitespace();
/*  690:     */   }
/*  691:     */   
/*  692:     */   protected void popShouldStripWhitespace()
/*  693:     */   {
/*  694:1074 */     if (this.DEBUG) {
/*  695:1074 */       System.out.println("popShouldStripWS()");
/*  696:     */     }
/*  697:1075 */     super.popShouldStripWhitespace();
/*  698:     */   }
/*  699:     */   
/*  700:     */   public boolean isNodeAfter(int parm1, int parm2)
/*  701:     */   {
/*  702:1085 */     if (this.DEBUG) {
/*  703:1085 */       System.out.println("isNodeAfter(" + parm1 + "," + parm2 + ")");
/*  704:     */     }
/*  705:1086 */     return super.isNodeAfter(parm1, parm2);
/*  706:     */   }
/*  707:     */   
/*  708:     */   public int getNamespaceType(int parm1)
/*  709:     */   {
/*  710:1095 */     if (this.DEBUG) {
/*  711:1095 */       System.out.println("getNamespaceType(" + parm1 + ")");
/*  712:     */     }
/*  713:1096 */     return super.getNamespaceType(parm1);
/*  714:     */   }
/*  715:     */   
/*  716:     */   protected int _level(int parm1)
/*  717:     */   {
/*  718:1105 */     if (this.DEBUG) {
/*  719:1105 */       System.out.println("_level(" + parm1 + ")");
/*  720:     */     }
/*  721:1106 */     return super._level(parm1);
/*  722:     */   }
/*  723:     */   
/*  724:     */   protected void pushShouldStripWhitespace(boolean parm1)
/*  725:     */   {
/*  726:1116 */     if (this.DEBUG) {
/*  727:1116 */       System.out.println("push_ShouldStripWS(" + parm1 + ")");
/*  728:     */     }
/*  729:1117 */     super.pushShouldStripWhitespace(parm1);
/*  730:     */   }
/*  731:     */   
/*  732:     */   public String getDocumentVersion(int parm1)
/*  733:     */   {
/*  734:1126 */     if (this.DEBUG) {
/*  735:1126 */       System.out.println("getDocVer(" + parm1 + ")");
/*  736:     */     }
/*  737:1127 */     return super.getDocumentVersion(parm1);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public boolean isSupported(String parm1, String parm2)
/*  741:     */   {
/*  742:1137 */     if (this.DEBUG) {
/*  743:1137 */       System.out.println("isSupported(" + parm1 + "," + parm2 + ")");
/*  744:     */     }
/*  745:1138 */     return super.isSupported(parm1, parm2);
/*  746:     */   }
/*  747:     */   
/*  748:     */   protected void setShouldStripWhitespace(boolean parm1)
/*  749:     */   {
/*  750:1148 */     if (this.DEBUG) {
/*  751:1148 */       System.out.println("set_ShouldStripWS(" + parm1 + ")");
/*  752:     */     }
/*  753:1149 */     super.setShouldStripWhitespace(parm1);
/*  754:     */   }
/*  755:     */   
/*  756:     */   protected void ensureSizeOfIndex(int parm1, int parm2)
/*  757:     */   {
/*  758:1160 */     if (this.DEBUG) {
/*  759:1160 */       System.out.println("ensureSizeOfIndex(" + parm1 + "," + parm2 + ")");
/*  760:     */     }
/*  761:1161 */     super.ensureSizeOfIndex(parm1, parm2);
/*  762:     */   }
/*  763:     */   
/*  764:     */   protected void ensureSize(int parm1)
/*  765:     */   {
/*  766:1170 */     if (this.DEBUG) {
/*  767:1170 */       System.out.println("ensureSize(" + parm1 + ")");
/*  768:     */     }
/*  769:     */   }
/*  770:     */   
/*  771:     */   public String getDocumentEncoding(int parm1)
/*  772:     */   {
/*  773:1183 */     if (this.DEBUG) {
/*  774:1183 */       System.out.println("getDocumentEncoding(" + parm1 + ")");
/*  775:     */     }
/*  776:1184 */     return super.getDocumentEncoding(parm1);
/*  777:     */   }
/*  778:     */   
/*  779:     */   public void appendChild(int parm1, boolean parm2, boolean parm3)
/*  780:     */   {
/*  781:1195 */     if (this.DEBUG) {
/*  782:1197 */       System.out.println("appendChild(" + parm1 + "," + parm2 + "," + parm3 + ")");
/*  783:     */     }
/*  784:1203 */     super.appendChild(parm1, parm2, parm3);
/*  785:     */   }
/*  786:     */   
/*  787:     */   public short getLevel(int parm1)
/*  788:     */   {
/*  789:1212 */     if (this.DEBUG) {
/*  790:1212 */       System.out.println("getLevel(" + parm1 + ")");
/*  791:     */     }
/*  792:1213 */     return super.getLevel(parm1);
/*  793:     */   }
/*  794:     */   
/*  795:     */   public String getDocumentBaseURI()
/*  796:     */   {
/*  797:1221 */     if (this.DEBUG) {
/*  798:1221 */       System.out.println("getDocBaseURI()");
/*  799:     */     }
/*  800:1222 */     return super.getDocumentBaseURI();
/*  801:     */   }
/*  802:     */   
/*  803:     */   public int getNextNamespaceNode(int parm1, int parm2, boolean parm3)
/*  804:     */   {
/*  805:1233 */     if (this.DEBUG) {
/*  806:1235 */       System.out.println("getNextNamesapceNode(" + parm1 + "," + parm2 + "," + parm3 + ")");
/*  807:     */     }
/*  808:1241 */     return super.getNextNamespaceNode(parm1, parm2, parm3);
/*  809:     */   }
/*  810:     */   
/*  811:     */   public void appendTextChild(String parm1)
/*  812:     */   {
/*  813:1250 */     if (this.DEBUG) {
/*  814:1250 */       System.out.println("appendTextChild(" + parm1 + ")");
/*  815:     */     }
/*  816:1251 */     super.appendTextChild(parm1);
/*  817:     */   }
/*  818:     */   
/*  819:     */   protected int findGTE(int[] parm1, int parm2, int parm3, int parm4)
/*  820:     */   {
/*  821:1263 */     if (this.DEBUG) {
/*  822:1265 */       System.out.println("findGTE(" + parm1 + "," + parm2 + "," + parm3 + ")");
/*  823:     */     }
/*  824:1271 */     return super.findGTE(parm1, parm2, parm3, parm4);
/*  825:     */   }
/*  826:     */   
/*  827:     */   public int getFirstNamespaceNode(int parm1, boolean parm2)
/*  828:     */   {
/*  829:1281 */     if (this.DEBUG) {
/*  830:1281 */       System.out.println("getFirstNamespaceNode()");
/*  831:     */     }
/*  832:1282 */     return super.getFirstNamespaceNode(parm1, parm2);
/*  833:     */   }
/*  834:     */   
/*  835:     */   public int getStringValueChunkCount(int parm1)
/*  836:     */   {
/*  837:1291 */     if (this.DEBUG) {
/*  838:1291 */       System.out.println("getStringChunkCount(" + parm1 + ")");
/*  839:     */     }
/*  840:1292 */     return super.getStringValueChunkCount(parm1);
/*  841:     */   }
/*  842:     */   
/*  843:     */   public int getLastChild(int parm1)
/*  844:     */   {
/*  845:1301 */     if (this.DEBUG) {
/*  846:1301 */       System.out.println("getLastChild(" + parm1 + ")");
/*  847:     */     }
/*  848:1302 */     return super.getLastChild(parm1);
/*  849:     */   }
/*  850:     */   
/*  851:     */   public boolean hasChildNodes(int parm1)
/*  852:     */   {
/*  853:1311 */     if (this.DEBUG) {
/*  854:1311 */       System.out.println("hasChildNodes(" + parm1 + ")");
/*  855:     */     }
/*  856:1312 */     return super.hasChildNodes(parm1);
/*  857:     */   }
/*  858:     */   
/*  859:     */   public short getNodeType(int parm1)
/*  860:     */   {
/*  861:1321 */     if (this.DEBUG)
/*  862:     */     {
/*  863:1323 */       this.DEBUG = false;
/*  864:1324 */       System.out.print("getNodeType(" + parm1 + ") ");
/*  865:1325 */       int exID = getExpandedTypeID(parm1);
/*  866:1326 */       String name = getLocalNameFromExpandedNameID(exID);
/*  867:1327 */       System.out.println(".. Node name [" + name + "]" + "[" + getNodeType(parm1) + "]");
/*  868:     */       
/*  869:     */ 
/*  870:     */ 
/*  871:1331 */       this.DEBUG = true;
/*  872:     */     }
/*  873:1334 */     return super.getNodeType(parm1);
/*  874:     */   }
/*  875:     */   
/*  876:     */   public boolean isCharacterElementContentWhitespace(int parm1)
/*  877:     */   {
/*  878:1343 */     if (this.DEBUG) {
/*  879:1343 */       System.out.println("isCharacterElementContentWhitespace(" + parm1 + ")");
/*  880:     */     }
/*  881:1344 */     return super.isCharacterElementContentWhitespace(parm1);
/*  882:     */   }
/*  883:     */   
/*  884:     */   public int getFirstChild(int parm1)
/*  885:     */   {
/*  886:1353 */     if (this.DEBUG) {
/*  887:1353 */       System.out.println("getFirstChild(" + parm1 + ")");
/*  888:     */     }
/*  889:1354 */     return super.getFirstChild(parm1);
/*  890:     */   }
/*  891:     */   
/*  892:     */   public String getDocumentSystemIdentifier(int parm1)
/*  893:     */   {
/*  894:1363 */     if (this.DEBUG) {
/*  895:1363 */       System.out.println("getDocSysID(" + parm1 + ")");
/*  896:     */     }
/*  897:1364 */     return super.getDocumentSystemIdentifier(parm1);
/*  898:     */   }
/*  899:     */   
/*  900:     */   protected void declareNamespaceInContext(int parm1, int parm2)
/*  901:     */   {
/*  902:1374 */     if (this.DEBUG) {
/*  903:1374 */       System.out.println("declareNamespaceContext(" + parm1 + "," + parm2 + ")");
/*  904:     */     }
/*  905:1375 */     super.declareNamespaceInContext(parm1, parm2);
/*  906:     */   }
/*  907:     */   
/*  908:     */   public String getNamespaceFromExpandedNameID(int parm1)
/*  909:     */   {
/*  910:1384 */     if (this.DEBUG)
/*  911:     */     {
/*  912:1386 */       this.DEBUG = false;
/*  913:1387 */       System.out.print("getNamespaceFromExpandedNameID(" + parm1 + ")");
/*  914:1388 */       System.out.println("..." + super.getNamespaceFromExpandedNameID(parm1));
/*  915:1389 */       this.DEBUG = true;
/*  916:     */     }
/*  917:1391 */     return super.getNamespaceFromExpandedNameID(parm1);
/*  918:     */   }
/*  919:     */   
/*  920:     */   public String getLocalNameFromExpandedNameID(int parm1)
/*  921:     */   {
/*  922:1400 */     if (this.DEBUG)
/*  923:     */     {
/*  924:1402 */       this.DEBUG = false;
/*  925:1403 */       System.out.print("getLocalNameFromExpandedNameID(" + parm1 + ")");
/*  926:1404 */       System.out.println("..." + super.getLocalNameFromExpandedNameID(parm1));
/*  927:1405 */       this.DEBUG = true;
/*  928:     */     }
/*  929:1407 */     return super.getLocalNameFromExpandedNameID(parm1);
/*  930:     */   }
/*  931:     */   
/*  932:     */   public int getExpandedTypeID(int parm1)
/*  933:     */   {
/*  934:1416 */     if (this.DEBUG) {
/*  935:1416 */       System.out.println("getExpandedTypeID(" + parm1 + ")");
/*  936:     */     }
/*  937:1417 */     return super.getExpandedTypeID(parm1);
/*  938:     */   }
/*  939:     */   
/*  940:     */   public int getDocument()
/*  941:     */   {
/*  942:1425 */     if (this.DEBUG) {
/*  943:1425 */       System.out.println("getDocument()");
/*  944:     */     }
/*  945:1426 */     return super.getDocument();
/*  946:     */   }
/*  947:     */   
/*  948:     */   protected int findInSortedSuballocatedIntVector(SuballocatedIntVector parm1, int parm2)
/*  949:     */   {
/*  950:1437 */     if (this.DEBUG) {
/*  951:1439 */       System.out.println("findInSortedSubAlloctedVector(" + parm1 + "," + parm2 + ")");
/*  952:     */     }
/*  953:1444 */     return super.findInSortedSuballocatedIntVector(parm1, parm2);
/*  954:     */   }
/*  955:     */   
/*  956:     */   public boolean isDocumentAllDeclarationsProcessed(int parm1)
/*  957:     */   {
/*  958:1453 */     if (this.DEBUG) {
/*  959:1453 */       System.out.println("isDocumentAllDeclProc(" + parm1 + ")");
/*  960:     */     }
/*  961:1454 */     return super.isDocumentAllDeclarationsProcessed(parm1);
/*  962:     */   }
/*  963:     */   
/*  964:     */   protected void error(String parm1)
/*  965:     */   {
/*  966:1463 */     if (this.DEBUG) {
/*  967:1463 */       System.out.println("error(" + parm1 + ")");
/*  968:     */     }
/*  969:1464 */     super.error(parm1);
/*  970:     */   }
/*  971:     */   
/*  972:     */   protected int _firstch(int parm1)
/*  973:     */   {
/*  974:1474 */     if (this.DEBUG) {
/*  975:1474 */       System.out.println("_firstch(" + parm1 + ")");
/*  976:     */     }
/*  977:1475 */     return super._firstch(parm1);
/*  978:     */   }
/*  979:     */   
/*  980:     */   public int getOwnerDocument(int parm1)
/*  981:     */   {
/*  982:1484 */     if (this.DEBUG) {
/*  983:1484 */       System.out.println("getOwnerDoc(" + parm1 + ")");
/*  984:     */     }
/*  985:1485 */     return super.getOwnerDocument(parm1);
/*  986:     */   }
/*  987:     */   
/*  988:     */   protected int _nextsib(int parm1)
/*  989:     */   {
/*  990:1494 */     if (this.DEBUG) {
/*  991:1494 */       System.out.println("_nextSib(" + parm1 + ")");
/*  992:     */     }
/*  993:1495 */     return super._nextsib(parm1);
/*  994:     */   }
/*  995:     */   
/*  996:     */   public int getNextSibling(int parm1)
/*  997:     */   {
/*  998:1504 */     if (this.DEBUG) {
/*  999:1504 */       System.out.println("getNextSibling(" + parm1 + ")");
/* 1000:     */     }
/* 1001:1505 */     return super.getNextSibling(parm1);
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public boolean getDocumentAllDeclarationsProcessed()
/* 1005:     */   {
/* 1006:1514 */     if (this.DEBUG) {
/* 1007:1514 */       System.out.println("getDocAllDeclProc()");
/* 1008:     */     }
/* 1009:1515 */     return super.getDocumentAllDeclarationsProcessed();
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public int getParent(int parm1)
/* 1013:     */   {
/* 1014:1524 */     if (this.DEBUG) {
/* 1015:1524 */       System.out.println("getParent(" + parm1 + ")");
/* 1016:     */     }
/* 1017:1525 */     return super.getParent(parm1);
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public int getExpandedTypeID(String parm1, String parm2, int parm3)
/* 1021:     */   {
/* 1022:1536 */     if (this.DEBUG) {
/* 1023:1536 */       System.out.println("getExpandedTypeID()");
/* 1024:     */     }
/* 1025:1537 */     return super.getExpandedTypeID(parm1, parm2, parm3);
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public void setDocumentBaseURI(String parm1)
/* 1029:     */   {
/* 1030:1546 */     if (this.DEBUG) {
/* 1031:1546 */       System.out.println("setDocBaseURI()");
/* 1032:     */     }
/* 1033:1547 */     super.setDocumentBaseURI(parm1);
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public char[] getStringValueChunk(int parm1, int parm2, int[] parm3)
/* 1037:     */   {
/* 1038:1558 */     if (this.DEBUG) {
/* 1039:1560 */       System.out.println("getStringChunkValue(" + parm1 + "," + parm2 + ")");
/* 1040:     */     }
/* 1041:1564 */     return super.getStringValueChunk(parm1, parm2, parm3);
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public DTMAxisTraverser getAxisTraverser(int parm1)
/* 1045:     */   {
/* 1046:1573 */     if (this.DEBUG) {
/* 1047:1573 */       System.out.println("getAxixTraverser(" + parm1 + ")");
/* 1048:     */     }
/* 1049:1574 */     return super.getAxisTraverser(parm1);
/* 1050:     */   }
/* 1051:     */   
/* 1052:     */   public DTMAxisIterator getTypedAxisIterator(int parm1, int parm2)
/* 1053:     */   {
/* 1054:1584 */     if (this.DEBUG) {
/* 1055:1584 */       System.out.println("getTypedAxisIterator(" + parm1 + "," + parm2 + ")");
/* 1056:     */     }
/* 1057:1585 */     return super.getTypedAxisIterator(parm1, parm2);
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public DTMAxisIterator getAxisIterator(int parm1)
/* 1061:     */   {
/* 1062:1594 */     if (this.DEBUG) {
/* 1063:1594 */       System.out.println("getAxisIterator(" + parm1 + ")");
/* 1064:     */     }
/* 1065:1595 */     return super.getAxisIterator(parm1);
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public int getElementById(String parm1)
/* 1069:     */   {
/* 1070:1603 */     if (this.DEBUG) {
/* 1071:1603 */       System.out.println("getElementByID(" + parm1 + ")");
/* 1072:     */     }
/* 1073:1604 */     return -1;
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public DeclHandler getDeclHandler()
/* 1077:     */   {
/* 1078:1612 */     if (this.DEBUG) {
/* 1079:1612 */       System.out.println("getDeclHandler()");
/* 1080:     */     }
/* 1081:1613 */     return null;
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public ErrorHandler getErrorHandler()
/* 1085:     */   {
/* 1086:1621 */     if (this.DEBUG) {
/* 1087:1621 */       System.out.println("getErrorHandler()");
/* 1088:     */     }
/* 1089:1622 */     return null;
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public String getDocumentTypeDeclarationSystemIdentifier()
/* 1093:     */   {
/* 1094:1630 */     if (this.DEBUG) {
/* 1095:1630 */       System.out.println("get_DTD-SID()");
/* 1096:     */     }
/* 1097:1631 */     return null;
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public static abstract interface CharacterNodeHandler
/* 1101:     */   {
/* 1102:     */     public abstract void characters(Node paramNode)
/* 1103:     */       throws SAXException;
/* 1104:     */   }
/* 1105:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.DTMDocument
 * JD-Core Version:    0.7.0.1
 */