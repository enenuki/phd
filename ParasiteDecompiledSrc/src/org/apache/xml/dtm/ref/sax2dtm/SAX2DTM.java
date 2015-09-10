/*    1:     */ package org.apache.xml.dtm.ref.sax2dtm;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.util.Hashtable;
/*    5:     */ import java.util.Vector;
/*    6:     */ import javax.xml.transform.Source;
/*    7:     */ import javax.xml.transform.SourceLocator;
/*    8:     */ import org.apache.xml.dtm.DTMManager;
/*    9:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   10:     */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   11:     */ import org.apache.xml.dtm.ref.DTMDefaultBaseIterators;
/*   12:     */ import org.apache.xml.dtm.ref.DTMManagerDefault;
/*   13:     */ import org.apache.xml.dtm.ref.DTMStringPool;
/*   14:     */ import org.apache.xml.dtm.ref.DTMTreeWalker;
/*   15:     */ import org.apache.xml.dtm.ref.ExpandedNameTable;
/*   16:     */ import org.apache.xml.dtm.ref.IncrementalSAXSource;
/*   17:     */ import org.apache.xml.dtm.ref.IncrementalSAXSource_Filter;
/*   18:     */ import org.apache.xml.dtm.ref.NodeLocator;
/*   19:     */ import org.apache.xml.res.XMLMessages;
/*   20:     */ import org.apache.xml.utils.FastStringBuffer;
/*   21:     */ import org.apache.xml.utils.IntStack;
/*   22:     */ import org.apache.xml.utils.IntVector;
/*   23:     */ import org.apache.xml.utils.StringVector;
/*   24:     */ import org.apache.xml.utils.SuballocatedIntVector;
/*   25:     */ import org.apache.xml.utils.SystemIDResolver;
/*   26:     */ import org.apache.xml.utils.WrappedRuntimeException;
/*   27:     */ import org.apache.xml.utils.XMLString;
/*   28:     */ import org.apache.xml.utils.XMLStringFactory;
/*   29:     */ import org.xml.sax.Attributes;
/*   30:     */ import org.xml.sax.ContentHandler;
/*   31:     */ import org.xml.sax.DTDHandler;
/*   32:     */ import org.xml.sax.EntityResolver;
/*   33:     */ import org.xml.sax.ErrorHandler;
/*   34:     */ import org.xml.sax.InputSource;
/*   35:     */ import org.xml.sax.Locator;
/*   36:     */ import org.xml.sax.SAXException;
/*   37:     */ import org.xml.sax.SAXParseException;
/*   38:     */ import org.xml.sax.ext.DeclHandler;
/*   39:     */ import org.xml.sax.ext.LexicalHandler;
/*   40:     */ 
/*   41:     */ public class SAX2DTM
/*   42:     */   extends DTMDefaultBaseIterators
/*   43:     */   implements EntityResolver, DTDHandler, ContentHandler, ErrorHandler, DeclHandler, LexicalHandler
/*   44:     */ {
/*   45:     */   private static final boolean DEBUG = false;
/*   46:  67 */   private IncrementalSAXSource m_incrementalSAXSource = null;
/*   47:     */   protected FastStringBuffer m_chars;
/*   48:     */   protected SuballocatedIntVector m_data;
/*   49:     */   protected transient IntStack m_parents;
/*   50:  97 */   protected transient int m_previous = 0;
/*   51: 102 */   protected transient Vector m_prefixMappings = new Vector();
/*   52:     */   protected transient IntStack m_contextIndexes;
/*   53: 111 */   protected transient int m_textType = 3;
/*   54: 117 */   protected transient int m_coalescedTextType = 3;
/*   55: 120 */   protected transient Locator m_locator = null;
/*   56: 123 */   private transient String m_systemId = null;
/*   57: 126 */   protected transient boolean m_insideDTD = false;
/*   58: 129 */   protected DTMTreeWalker m_walker = new DTMTreeWalker();
/*   59:     */   protected DTMStringPool m_valuesOrPrefixes;
/*   60: 137 */   protected boolean m_endDocumentOccured = false;
/*   61:     */   protected SuballocatedIntVector m_dataOrQName;
/*   62: 146 */   protected Hashtable m_idAttributes = new Hashtable();
/*   63: 151 */   private static final String[] m_fixednames = { null, null, null, "#text", "#cdata_section", null, null, null, "#comment", "#document", null, "#document-fragment", null };
/*   64: 164 */   private Vector m_entities = null;
/*   65:     */   private static final int ENTITY_FIELD_PUBLICID = 0;
/*   66:     */   private static final int ENTITY_FIELD_SYSTEMID = 1;
/*   67:     */   private static final int ENTITY_FIELD_NOTATIONNAME = 2;
/*   68:     */   private static final int ENTITY_FIELD_NAME = 3;
/*   69:     */   private static final int ENTITY_FIELDS_PER = 4;
/*   70: 186 */   protected int m_textPendingStart = -1;
/*   71: 194 */   protected boolean m_useSourceLocationProperty = false;
/*   72:     */   protected StringVector m_sourceSystemId;
/*   73:     */   protected IntVector m_sourceLine;
/*   74:     */   protected IntVector m_sourceColumn;
/*   75:     */   
/*   76:     */   public SAX2DTM(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing)
/*   77:     */   {
/*   78: 224 */     this(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, 512, true, false);
/*   79:     */   }
/*   80:     */   
/*   81:     */   public SAX2DTM(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean usePrevsib, boolean newNameTable)
/*   82:     */   {
/*   83: 253 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, blocksize, usePrevsib, newNameTable);
/*   84: 258 */     if (blocksize <= 64)
/*   85:     */     {
/*   86: 260 */       this.m_data = new SuballocatedIntVector(blocksize, 4);
/*   87: 261 */       this.m_dataOrQName = new SuballocatedIntVector(blocksize, 4);
/*   88: 262 */       this.m_valuesOrPrefixes = new DTMStringPool(16);
/*   89: 263 */       this.m_chars = new FastStringBuffer(7, 10);
/*   90: 264 */       this.m_contextIndexes = new IntStack(4);
/*   91: 265 */       this.m_parents = new IntStack(4);
/*   92:     */     }
/*   93:     */     else
/*   94:     */     {
/*   95: 269 */       this.m_data = new SuballocatedIntVector(blocksize, 32);
/*   96: 270 */       this.m_dataOrQName = new SuballocatedIntVector(blocksize, 32);
/*   97: 271 */       this.m_valuesOrPrefixes = new DTMStringPool();
/*   98: 272 */       this.m_chars = new FastStringBuffer(10, 13);
/*   99: 273 */       this.m_contextIndexes = new IntStack();
/*  100: 274 */       this.m_parents = new IntStack();
/*  101:     */     }
/*  102: 282 */     this.m_data.addElement(0);
/*  103:     */     
/*  104:     */ 
/*  105:     */ 
/*  106:     */ 
/*  107: 287 */     this.m_useSourceLocationProperty = mgr.getSource_location();
/*  108: 288 */     this.m_sourceSystemId = (this.m_useSourceLocationProperty ? new StringVector() : null);
/*  109: 289 */     this.m_sourceLine = (this.m_useSourceLocationProperty ? new IntVector() : null);
/*  110: 290 */     this.m_sourceColumn = (this.m_useSourceLocationProperty ? new IntVector() : null);
/*  111:     */   }
/*  112:     */   
/*  113:     */   public void setUseSourceLocation(boolean useSourceLocation)
/*  114:     */   {
/*  115: 299 */     this.m_useSourceLocationProperty = useSourceLocation;
/*  116:     */   }
/*  117:     */   
/*  118:     */   protected int _dataOrQName(int identity)
/*  119:     */   {
/*  120: 312 */     if (identity < this.m_size) {
/*  121: 313 */       return this.m_dataOrQName.elementAt(identity);
/*  122:     */     }
/*  123:     */     for (;;)
/*  124:     */     {
/*  125: 320 */       boolean isMore = nextNode();
/*  126: 322 */       if (!isMore) {
/*  127: 323 */         return -1;
/*  128:     */       }
/*  129: 324 */       if (identity < this.m_size) {
/*  130: 325 */         return this.m_dataOrQName.elementAt(identity);
/*  131:     */       }
/*  132:     */     }
/*  133:     */   }
/*  134:     */   
/*  135:     */   public void clearCoRoutine()
/*  136:     */   {
/*  137: 334 */     clearCoRoutine(true);
/*  138:     */   }
/*  139:     */   
/*  140:     */   public void clearCoRoutine(boolean callDoTerminate)
/*  141:     */   {
/*  142: 347 */     if (null != this.m_incrementalSAXSource)
/*  143:     */     {
/*  144: 349 */       if (callDoTerminate) {
/*  145: 350 */         this.m_incrementalSAXSource.deliverMoreNodes(false);
/*  146:     */       }
/*  147: 352 */       this.m_incrementalSAXSource = null;
/*  148:     */     }
/*  149:     */   }
/*  150:     */   
/*  151:     */   public void setIncrementalSAXSource(IncrementalSAXSource incrementalSAXSource)
/*  152:     */   {
/*  153: 378 */     this.m_incrementalSAXSource = incrementalSAXSource;
/*  154:     */     
/*  155:     */ 
/*  156: 381 */     incrementalSAXSource.setContentHandler(this);
/*  157: 382 */     incrementalSAXSource.setLexicalHandler(this);
/*  158: 383 */     incrementalSAXSource.setDTDHandler(this);
/*  159:     */   }
/*  160:     */   
/*  161:     */   public ContentHandler getContentHandler()
/*  162:     */   {
/*  163: 406 */     if ((this.m_incrementalSAXSource instanceof IncrementalSAXSource_Filter)) {
/*  164: 407 */       return (ContentHandler)this.m_incrementalSAXSource;
/*  165:     */     }
/*  166: 409 */     return this;
/*  167:     */   }
/*  168:     */   
/*  169:     */   public LexicalHandler getLexicalHandler()
/*  170:     */   {
/*  171: 425 */     if ((this.m_incrementalSAXSource instanceof IncrementalSAXSource_Filter)) {
/*  172: 426 */       return (LexicalHandler)this.m_incrementalSAXSource;
/*  173:     */     }
/*  174: 428 */     return this;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public EntityResolver getEntityResolver()
/*  178:     */   {
/*  179: 438 */     return this;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public DTDHandler getDTDHandler()
/*  183:     */   {
/*  184: 448 */     return this;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public ErrorHandler getErrorHandler()
/*  188:     */   {
/*  189: 458 */     return this;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public DeclHandler getDeclHandler()
/*  193:     */   {
/*  194: 468 */     return this;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public boolean needsTwoThreads()
/*  198:     */   {
/*  199: 479 */     return null != this.m_incrementalSAXSource;
/*  200:     */   }
/*  201:     */   
/*  202:     */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize)
/*  203:     */     throws SAXException
/*  204:     */   {
/*  205: 504 */     int identity = makeNodeIdentity(nodeHandle);
/*  206: 506 */     if (identity == -1) {
/*  207: 507 */       return;
/*  208:     */     }
/*  209: 509 */     int type = _type(identity);
/*  210: 511 */     if (isTextType(type))
/*  211:     */     {
/*  212: 513 */       int dataIndex = this.m_dataOrQName.elementAt(identity);
/*  213: 514 */       int offset = this.m_data.elementAt(dataIndex);
/*  214: 515 */       int length = this.m_data.elementAt(dataIndex + 1);
/*  215: 517 */       if (normalize) {
/*  216: 518 */         this.m_chars.sendNormalizedSAXcharacters(ch, offset, length);
/*  217:     */       } else {
/*  218: 520 */         this.m_chars.sendSAXcharacters(ch, offset, length);
/*  219:     */       }
/*  220:     */     }
/*  221:     */     else
/*  222:     */     {
/*  223: 524 */       int firstChild = _firstch(identity);
/*  224: 526 */       if (-1 != firstChild)
/*  225:     */       {
/*  226: 528 */         int offset = -1;
/*  227: 529 */         int length = 0;
/*  228: 530 */         int startNode = identity;
/*  229:     */         
/*  230: 532 */         identity = firstChild;
/*  231:     */         do
/*  232:     */         {
/*  233: 535 */           type = _type(identity);
/*  234: 537 */           if (isTextType(type))
/*  235:     */           {
/*  236: 539 */             int dataIndex = _dataOrQName(identity);
/*  237: 541 */             if (-1 == offset) {
/*  238: 543 */               offset = this.m_data.elementAt(dataIndex);
/*  239:     */             }
/*  240: 546 */             length += this.m_data.elementAt(dataIndex + 1);
/*  241:     */           }
/*  242: 549 */           identity = getNextNodeIdentity(identity);
/*  243: 550 */         } while ((-1 != identity) && (_parent(identity) >= startNode));
/*  244: 552 */         if (length > 0) {
/*  245: 554 */           if (normalize) {
/*  246: 555 */             this.m_chars.sendNormalizedSAXcharacters(ch, offset, length);
/*  247:     */           } else {
/*  248: 557 */             this.m_chars.sendSAXcharacters(ch, offset, length);
/*  249:     */           }
/*  250:     */         }
/*  251:     */       }
/*  252: 560 */       else if (type != 1)
/*  253:     */       {
/*  254: 562 */         int dataIndex = _dataOrQName(identity);
/*  255: 564 */         if (dataIndex < 0)
/*  256:     */         {
/*  257: 566 */           dataIndex = -dataIndex;
/*  258: 567 */           dataIndex = this.m_data.elementAt(dataIndex + 1);
/*  259:     */         }
/*  260: 570 */         String str = this.m_valuesOrPrefixes.indexToString(dataIndex);
/*  261: 572 */         if (normalize) {
/*  262: 573 */           FastStringBuffer.sendNormalizedSAXcharacters(str.toCharArray(), 0, str.length(), ch);
/*  263:     */         } else {
/*  264: 576 */           ch.characters(str.toCharArray(), 0, str.length());
/*  265:     */         }
/*  266:     */       }
/*  267:     */     }
/*  268:     */   }
/*  269:     */   
/*  270:     */   public String getNodeName(int nodeHandle)
/*  271:     */   {
/*  272: 594 */     int expandedTypeID = getExpandedTypeID(nodeHandle);
/*  273:     */     
/*  274: 596 */     int namespaceID = this.m_expandedNameTable.getNamespaceID(expandedTypeID);
/*  275: 598 */     if (0 == namespaceID)
/*  276:     */     {
/*  277: 602 */       int type = getNodeType(nodeHandle);
/*  278: 604 */       if (type == 13)
/*  279:     */       {
/*  280: 606 */         if (null == this.m_expandedNameTable.getLocalName(expandedTypeID)) {
/*  281: 607 */           return "xmlns";
/*  282:     */         }
/*  283: 609 */         return "xmlns:" + this.m_expandedNameTable.getLocalName(expandedTypeID);
/*  284:     */       }
/*  285: 611 */       if (0 == this.m_expandedNameTable.getLocalNameID(expandedTypeID)) {
/*  286: 613 */         return m_fixednames[type];
/*  287:     */       }
/*  288: 616 */       return this.m_expandedNameTable.getLocalName(expandedTypeID);
/*  289:     */     }
/*  290: 620 */     int qnameIndex = this.m_dataOrQName.elementAt(makeNodeIdentity(nodeHandle));
/*  291: 622 */     if (qnameIndex < 0)
/*  292:     */     {
/*  293: 624 */       qnameIndex = -qnameIndex;
/*  294: 625 */       qnameIndex = this.m_data.elementAt(qnameIndex);
/*  295:     */     }
/*  296: 628 */     return this.m_valuesOrPrefixes.indexToString(qnameIndex);
/*  297:     */   }
/*  298:     */   
/*  299:     */   public String getNodeNameX(int nodeHandle)
/*  300:     */   {
/*  301: 643 */     int expandedTypeID = getExpandedTypeID(nodeHandle);
/*  302: 644 */     int namespaceID = this.m_expandedNameTable.getNamespaceID(expandedTypeID);
/*  303: 646 */     if (0 == namespaceID)
/*  304:     */     {
/*  305: 648 */       String name = this.m_expandedNameTable.getLocalName(expandedTypeID);
/*  306: 650 */       if (name == null) {
/*  307: 651 */         return "";
/*  308:     */       }
/*  309: 653 */       return name;
/*  310:     */     }
/*  311: 657 */     int qnameIndex = this.m_dataOrQName.elementAt(makeNodeIdentity(nodeHandle));
/*  312: 659 */     if (qnameIndex < 0)
/*  313:     */     {
/*  314: 661 */       qnameIndex = -qnameIndex;
/*  315: 662 */       qnameIndex = this.m_data.elementAt(qnameIndex);
/*  316:     */     }
/*  317: 665 */     return this.m_valuesOrPrefixes.indexToString(qnameIndex);
/*  318:     */   }
/*  319:     */   
/*  320:     */   public boolean isAttributeSpecified(int attributeHandle)
/*  321:     */   {
/*  322: 682 */     return true;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public String getDocumentTypeDeclarationSystemIdentifier()
/*  326:     */   {
/*  327: 697 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*  328:     */     
/*  329: 699 */     return null;
/*  330:     */   }
/*  331:     */   
/*  332:     */   protected int getNextNodeIdentity(int identity)
/*  333:     */   {
/*  334:     */     
/*  335: 714 */     while (identity >= this.m_size)
/*  336:     */     {
/*  337: 716 */       if (null == this.m_incrementalSAXSource) {
/*  338: 717 */         return -1;
/*  339:     */       }
/*  340: 719 */       nextNode();
/*  341:     */     }
/*  342: 722 */     return identity;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public void dispatchToEvents(int nodeHandle, ContentHandler ch)
/*  346:     */     throws SAXException
/*  347:     */   {
/*  348: 737 */     DTMTreeWalker treeWalker = this.m_walker;
/*  349: 738 */     ContentHandler prevCH = treeWalker.getcontentHandler();
/*  350: 740 */     if (null != prevCH) {
/*  351: 742 */       treeWalker = new DTMTreeWalker();
/*  352:     */     }
/*  353: 745 */     treeWalker.setcontentHandler(ch);
/*  354: 746 */     treeWalker.setDTM(this);
/*  355:     */     try
/*  356:     */     {
/*  357: 750 */       treeWalker.traverse(nodeHandle);
/*  358:     */     }
/*  359:     */     finally
/*  360:     */     {
/*  361: 754 */       treeWalker.setcontentHandler(null);
/*  362:     */     }
/*  363:     */   }
/*  364:     */   
/*  365:     */   public int getNumberOfNodes()
/*  366:     */   {
/*  367: 765 */     return this.m_size;
/*  368:     */   }
/*  369:     */   
/*  370:     */   protected boolean nextNode()
/*  371:     */   {
/*  372: 777 */     if (null == this.m_incrementalSAXSource) {
/*  373: 778 */       return false;
/*  374:     */     }
/*  375: 780 */     if (this.m_endDocumentOccured)
/*  376:     */     {
/*  377: 782 */       clearCoRoutine();
/*  378:     */       
/*  379: 784 */       return false;
/*  380:     */     }
/*  381: 787 */     Object gotMore = this.m_incrementalSAXSource.deliverMoreNodes(true);
/*  382: 796 */     if (!(gotMore instanceof Boolean))
/*  383:     */     {
/*  384: 798 */       if ((gotMore instanceof RuntimeException)) {
/*  385: 800 */         throw ((RuntimeException)gotMore);
/*  386:     */       }
/*  387: 802 */       if ((gotMore instanceof Exception)) {
/*  388: 804 */         throw new WrappedRuntimeException((Exception)gotMore);
/*  389:     */       }
/*  390: 807 */       clearCoRoutine();
/*  391:     */       
/*  392: 809 */       return false;
/*  393:     */     }
/*  394: 814 */     if (gotMore != Boolean.TRUE) {
/*  395: 818 */       clearCoRoutine();
/*  396:     */     }
/*  397: 823 */     return true;
/*  398:     */   }
/*  399:     */   
/*  400:     */   private final boolean isTextType(int type)
/*  401:     */   {
/*  402: 835 */     return (3 == type) || (4 == type);
/*  403:     */   }
/*  404:     */   
/*  405:     */   protected int addNode(int type, int expandedTypeID, int parentIndex, int previousSibling, int dataOrPrefix, boolean canHaveFirstChild)
/*  406:     */   {
/*  407: 872 */     int nodeIndex = this.m_size++;
/*  408: 875 */     if (this.m_dtmIdent.size() == nodeIndex >>> 16) {
/*  409: 877 */       addNewDTMID(nodeIndex);
/*  410:     */     }
/*  411: 880 */     this.m_firstch.addElement(canHaveFirstChild ? -2 : -1);
/*  412: 881 */     this.m_nextsib.addElement(-2);
/*  413: 882 */     this.m_parent.addElement(parentIndex);
/*  414: 883 */     this.m_exptype.addElement(expandedTypeID);
/*  415: 884 */     this.m_dataOrQName.addElement(dataOrPrefix);
/*  416: 886 */     if (this.m_prevsib != null) {
/*  417: 887 */       this.m_prevsib.addElement(previousSibling);
/*  418:     */     }
/*  419: 890 */     if (-1 != previousSibling) {
/*  420: 891 */       this.m_nextsib.setElementAt(nodeIndex, previousSibling);
/*  421:     */     }
/*  422: 894 */     if ((this.m_locator != null) && (this.m_useSourceLocationProperty)) {
/*  423: 895 */       setSourceLocation();
/*  424:     */     }
/*  425: 902 */     switch (type)
/*  426:     */     {
/*  427:     */     case 13: 
/*  428: 905 */       declareNamespaceInContext(parentIndex, nodeIndex);
/*  429: 906 */       break;
/*  430:     */     case 2: 
/*  431:     */       break;
/*  432:     */     default: 
/*  433: 910 */       if ((-1 == previousSibling) && (-1 != parentIndex)) {
/*  434: 911 */         this.m_firstch.setElementAt(nodeIndex, parentIndex);
/*  435:     */       }
/*  436:     */       break;
/*  437:     */     }
/*  438: 916 */     return nodeIndex;
/*  439:     */   }
/*  440:     */   
/*  441:     */   protected void addNewDTMID(int nodeIndex)
/*  442:     */   {
/*  443:     */     try
/*  444:     */     {
/*  445: 927 */       if (this.m_mgr == null) {
/*  446: 928 */         throw new ClassCastException();
/*  447:     */       }
/*  448: 931 */       DTMManagerDefault mgrD = (DTMManagerDefault)this.m_mgr;
/*  449: 932 */       int id = mgrD.getFirstFreeDTMID();
/*  450: 933 */       mgrD.addDTM(this, id, nodeIndex);
/*  451: 934 */       this.m_dtmIdent.addElement(id << 16);
/*  452:     */     }
/*  453:     */     catch (ClassCastException e)
/*  454:     */     {
/*  455: 941 */       error(XMLMessages.createXMLMessage("ER_NO_DTMIDS_AVAIL", null));
/*  456:     */     }
/*  457:     */   }
/*  458:     */   
/*  459:     */   public void migrateTo(DTMManager manager)
/*  460:     */   {
/*  461: 953 */     super.migrateTo(manager);
/*  462:     */     
/*  463:     */ 
/*  464:     */ 
/*  465: 957 */     int numDTMs = this.m_dtmIdent.size();
/*  466: 958 */     int dtmId = this.m_mgrDefault.getFirstFreeDTMID();
/*  467: 959 */     int nodeIndex = 0;
/*  468: 960 */     for (int i = 0; i < numDTMs; i++)
/*  469:     */     {
/*  470: 962 */       this.m_dtmIdent.setElementAt(dtmId << 16, i);
/*  471: 963 */       this.m_mgrDefault.addDTM(this, dtmId, nodeIndex);
/*  472: 964 */       dtmId++;
/*  473: 965 */       nodeIndex += 65536;
/*  474:     */     }
/*  475:     */   }
/*  476:     */   
/*  477:     */   protected void setSourceLocation()
/*  478:     */   {
/*  479: 974 */     this.m_sourceSystemId.addElement(this.m_locator.getSystemId());
/*  480: 975 */     this.m_sourceLine.addElement(this.m_locator.getLineNumber());
/*  481: 976 */     this.m_sourceColumn.addElement(this.m_locator.getColumnNumber());
/*  482: 981 */     if (this.m_sourceSystemId.size() != this.m_size)
/*  483:     */     {
/*  484: 982 */       String msg = "CODING ERROR in Source Location: " + this.m_size + " != " + this.m_sourceSystemId.size();
/*  485:     */       
/*  486: 984 */       System.err.println(msg);
/*  487: 985 */       throw new RuntimeException(msg);
/*  488:     */     }
/*  489:     */   }
/*  490:     */   
/*  491:     */   public String getNodeValue(int nodeHandle)
/*  492:     */   {
/*  493:1001 */     int identity = makeNodeIdentity(nodeHandle);
/*  494:1002 */     int type = _type(identity);
/*  495:1004 */     if (isTextType(type))
/*  496:     */     {
/*  497:1006 */       int dataIndex = _dataOrQName(identity);
/*  498:1007 */       int offset = this.m_data.elementAt(dataIndex);
/*  499:1008 */       int length = this.m_data.elementAt(dataIndex + 1);
/*  500:     */       
/*  501:     */ 
/*  502:1011 */       return this.m_chars.getString(offset, length);
/*  503:     */     }
/*  504:1013 */     if ((1 == type) || (11 == type) || (9 == type)) {
/*  505:1016 */       return null;
/*  506:     */     }
/*  507:1020 */     int dataIndex = _dataOrQName(identity);
/*  508:1022 */     if (dataIndex < 0)
/*  509:     */     {
/*  510:1024 */       dataIndex = -dataIndex;
/*  511:1025 */       dataIndex = this.m_data.elementAt(dataIndex + 1);
/*  512:     */     }
/*  513:1028 */     return this.m_valuesOrPrefixes.indexToString(dataIndex);
/*  514:     */   }
/*  515:     */   
/*  516:     */   public String getLocalName(int nodeHandle)
/*  517:     */   {
/*  518:1042 */     return this.m_expandedNameTable.getLocalName(_exptype(makeNodeIdentity(nodeHandle)));
/*  519:     */   }
/*  520:     */   
/*  521:     */   public String getUnparsedEntityURI(String name)
/*  522:     */   {
/*  523:1082 */     String url = "";
/*  524:1084 */     if (null == this.m_entities) {
/*  525:1085 */       return url;
/*  526:     */     }
/*  527:1087 */     int n = this.m_entities.size();
/*  528:1089 */     for (int i = 0; i < n; i += 4)
/*  529:     */     {
/*  530:1091 */       String ename = (String)this.m_entities.elementAt(i + 3);
/*  531:1093 */       if ((null != ename) && (ename.equals(name)))
/*  532:     */       {
/*  533:1095 */         String nname = (String)this.m_entities.elementAt(i + 2);
/*  534:1098 */         if (null == nname) {
/*  535:     */           break;
/*  536:     */         }
/*  537:1110 */         url = (String)this.m_entities.elementAt(i + 1);
/*  538:1112 */         if (null != url) {
/*  539:     */           break;
/*  540:     */         }
/*  541:1114 */         url = (String)this.m_entities.elementAt(i + 0); break;
/*  542:     */       }
/*  543:     */     }
/*  544:1122 */     return url;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public String getPrefix(int nodeHandle)
/*  548:     */   {
/*  549:1140 */     int identity = makeNodeIdentity(nodeHandle);
/*  550:1141 */     int type = _type(identity);
/*  551:1143 */     if (1 == type)
/*  552:     */     {
/*  553:1145 */       int prefixIndex = _dataOrQName(identity);
/*  554:1147 */       if (0 == prefixIndex) {
/*  555:1148 */         return "";
/*  556:     */       }
/*  557:1151 */       String qname = this.m_valuesOrPrefixes.indexToString(prefixIndex);
/*  558:     */       
/*  559:1153 */       return getPrefix(qname, null);
/*  560:     */     }
/*  561:1156 */     if (2 == type)
/*  562:     */     {
/*  563:1158 */       int prefixIndex = _dataOrQName(identity);
/*  564:1160 */       if (prefixIndex < 0)
/*  565:     */       {
/*  566:1162 */         prefixIndex = this.m_data.elementAt(-prefixIndex);
/*  567:     */         
/*  568:1164 */         String qname = this.m_valuesOrPrefixes.indexToString(prefixIndex);
/*  569:     */         
/*  570:1166 */         return getPrefix(qname, null);
/*  571:     */       }
/*  572:     */     }
/*  573:1170 */     return "";
/*  574:     */   }
/*  575:     */   
/*  576:     */   public int getAttributeNode(int nodeHandle, String namespaceURI, String name)
/*  577:     */   {
/*  578:1189 */     for (int attrH = getFirstAttribute(nodeHandle); -1 != attrH; attrH = getNextAttribute(attrH))
/*  579:     */     {
/*  580:1192 */       String attrNS = getNamespaceURI(attrH);
/*  581:1193 */       String attrName = getLocalName(attrH);
/*  582:1194 */       boolean nsMatch = (namespaceURI == attrNS) || ((namespaceURI != null) && (namespaceURI.equals(attrNS)));
/*  583:1198 */       if ((nsMatch) && (name.equals(attrName))) {
/*  584:1199 */         return attrH;
/*  585:     */       }
/*  586:     */     }
/*  587:1202 */     return -1;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public String getDocumentTypeDeclarationPublicIdentifier()
/*  591:     */   {
/*  592:1217 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*  593:     */     
/*  594:1219 */     return null;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public String getNamespaceURI(int nodeHandle)
/*  598:     */   {
/*  599:1236 */     return this.m_expandedNameTable.getNamespace(_exptype(makeNodeIdentity(nodeHandle)));
/*  600:     */   }
/*  601:     */   
/*  602:     */   public XMLString getStringValue(int nodeHandle)
/*  603:     */   {
/*  604:1250 */     int identity = makeNodeIdentity(nodeHandle);
/*  605:     */     int type;
/*  606:1252 */     if (identity == -1) {
/*  607:1253 */       type = -1;
/*  608:     */     } else {
/*  609:1255 */       type = _type(identity);
/*  610:     */     }
/*  611:1257 */     if (isTextType(type))
/*  612:     */     {
/*  613:1259 */       int dataIndex = _dataOrQName(identity);
/*  614:1260 */       int offset = this.m_data.elementAt(dataIndex);
/*  615:1261 */       int length = this.m_data.elementAt(dataIndex + 1);
/*  616:     */       
/*  617:1263 */       return this.m_xstrf.newstr(this.m_chars, offset, length);
/*  618:     */     }
/*  619:1267 */     int firstChild = _firstch(identity);
/*  620:1269 */     if (-1 != firstChild)
/*  621:     */     {
/*  622:1271 */       int offset = -1;
/*  623:1272 */       int length = 0;
/*  624:1273 */       int startNode = identity;
/*  625:     */       
/*  626:1275 */       identity = firstChild;
/*  627:     */       do
/*  628:     */       {
/*  629:1278 */         type = _type(identity);
/*  630:1280 */         if (isTextType(type))
/*  631:     */         {
/*  632:1282 */           int dataIndex = _dataOrQName(identity);
/*  633:1284 */           if (-1 == offset) {
/*  634:1286 */             offset = this.m_data.elementAt(dataIndex);
/*  635:     */           }
/*  636:1289 */           length += this.m_data.elementAt(dataIndex + 1);
/*  637:     */         }
/*  638:1292 */         identity = getNextNodeIdentity(identity);
/*  639:1293 */       } while ((-1 != identity) && (_parent(identity) >= startNode));
/*  640:1295 */       if (length > 0) {
/*  641:1297 */         return this.m_xstrf.newstr(this.m_chars, offset, length);
/*  642:     */       }
/*  643:     */     }
/*  644:1300 */     else if (type != 1)
/*  645:     */     {
/*  646:1302 */       int dataIndex = _dataOrQName(identity);
/*  647:1304 */       if (dataIndex < 0)
/*  648:     */       {
/*  649:1306 */         dataIndex = -dataIndex;
/*  650:1307 */         dataIndex = this.m_data.elementAt(dataIndex + 1);
/*  651:     */       }
/*  652:1309 */       return this.m_xstrf.newstr(this.m_valuesOrPrefixes.indexToString(dataIndex));
/*  653:     */     }
/*  654:1313 */     return this.m_xstrf.emptystr();
/*  655:     */   }
/*  656:     */   
/*  657:     */   public boolean isWhitespace(int nodeHandle)
/*  658:     */   {
/*  659:1325 */     int identity = makeNodeIdentity(nodeHandle);
/*  660:     */     int type;
/*  661:1327 */     if (identity == -1) {
/*  662:1328 */       type = -1;
/*  663:     */     } else {
/*  664:1330 */       type = _type(identity);
/*  665:     */     }
/*  666:1332 */     if (isTextType(type))
/*  667:     */     {
/*  668:1334 */       int dataIndex = _dataOrQName(identity);
/*  669:1335 */       int offset = this.m_data.elementAt(dataIndex);
/*  670:1336 */       int length = this.m_data.elementAt(dataIndex + 1);
/*  671:     */       
/*  672:1338 */       return this.m_chars.isWhitespace(offset, length);
/*  673:     */     }
/*  674:1340 */     return false;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public int getElementById(String elementId)
/*  678:     */   {
/*  679:1364 */     boolean isMore = true;
/*  680:     */     Integer intObj;
/*  681:     */     do
/*  682:     */     {
/*  683:1368 */       intObj = (Integer)this.m_idAttributes.get(elementId);
/*  684:1370 */       if (null != intObj) {
/*  685:1371 */         return makeNodeHandle(intObj.intValue());
/*  686:     */       }
/*  687:1373 */       if ((!isMore) || (this.m_endDocumentOccured)) {
/*  688:     */         break;
/*  689:     */       }
/*  690:1376 */       isMore = nextNode();
/*  691:1378 */     } while (null == intObj);
/*  692:1380 */     return -1;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public String getPrefix(String qname, String uri)
/*  696:     */   {
/*  697:1396 */     int uriIndex = -1;
/*  698:     */     String prefix;
/*  699:1398 */     if ((null != uri) && (uri.length() > 0))
/*  700:     */     {
/*  701:     */       do
/*  702:     */       {
/*  703:1403 */         uriIndex = this.m_prefixMappings.indexOf(uri, ++uriIndex);
/*  704:1404 */       } while ((uriIndex & 0x1) == 0);
/*  705:1406 */       if (uriIndex >= 0)
/*  706:     */       {
/*  707:1408 */         prefix = (String)this.m_prefixMappings.elementAt(uriIndex - 1);
/*  708:     */       }
/*  709:1410 */       else if (null != qname)
/*  710:     */       {
/*  711:1412 */         int indexOfNSSep = qname.indexOf(':');
/*  712:1414 */         if (qname.equals("xmlns")) {
/*  713:1415 */           prefix = "";
/*  714:1416 */         } else if (qname.startsWith("xmlns:")) {
/*  715:1417 */           prefix = qname.substring(indexOfNSSep + 1);
/*  716:     */         } else {
/*  717:1419 */           prefix = indexOfNSSep > 0 ? qname.substring(0, indexOfNSSep) : null;
/*  718:     */         }
/*  719:     */       }
/*  720:     */       else
/*  721:     */       {
/*  722:1424 */         prefix = null;
/*  723:     */       }
/*  724:     */     }
/*  725:1427 */     else if (null != qname)
/*  726:     */     {
/*  727:1429 */       int indexOfNSSep = qname.indexOf(':');
/*  728:1431 */       if (indexOfNSSep > 0)
/*  729:     */       {
/*  730:1433 */         if (qname.startsWith("xmlns:")) {
/*  731:1434 */           prefix = qname.substring(indexOfNSSep + 1);
/*  732:     */         } else {
/*  733:1436 */           prefix = qname.substring(0, indexOfNSSep);
/*  734:     */         }
/*  735:     */       }
/*  736:1440 */       else if (qname.equals("xmlns")) {
/*  737:1441 */         prefix = "";
/*  738:     */       } else {
/*  739:1443 */         prefix = null;
/*  740:     */       }
/*  741:     */     }
/*  742:     */     else
/*  743:     */     {
/*  744:1448 */       prefix = null;
/*  745:     */     }
/*  746:1451 */     return prefix;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public int getIdForNamespace(String uri)
/*  750:     */   {
/*  751:1465 */     return this.m_valuesOrPrefixes.stringToIndex(uri);
/*  752:     */   }
/*  753:     */   
/*  754:     */   public String getNamespaceURI(String prefix)
/*  755:     */   {
/*  756:1478 */     String uri = "";
/*  757:1479 */     int prefixIndex = this.m_contextIndexes.peek() - 1;
/*  758:1481 */     if (null == prefix) {
/*  759:1482 */       prefix = "";
/*  760:     */     }
/*  761:     */     do
/*  762:     */     {
/*  763:1486 */       prefixIndex = this.m_prefixMappings.indexOf(prefix, ++prefixIndex);
/*  764:1487 */     } while ((prefixIndex >= 0) && ((prefixIndex & 0x1) == 1));
/*  765:1489 */     if (prefixIndex > -1) {
/*  766:1491 */       uri = (String)this.m_prefixMappings.elementAt(prefixIndex + 1);
/*  767:     */     }
/*  768:1495 */     return uri;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public void setIDAttribute(String id, int elem)
/*  772:     */   {
/*  773:1506 */     this.m_idAttributes.put(id, new Integer(elem));
/*  774:     */   }
/*  775:     */   
/*  776:     */   protected void charactersFlush()
/*  777:     */   {
/*  778:1516 */     if (this.m_textPendingStart >= 0)
/*  779:     */     {
/*  780:1518 */       int length = this.m_chars.size() - this.m_textPendingStart;
/*  781:1519 */       boolean doStrip = false;
/*  782:1521 */       if (getShouldStripWhitespace()) {
/*  783:1523 */         doStrip = this.m_chars.isWhitespace(this.m_textPendingStart, length);
/*  784:     */       }
/*  785:1526 */       if (doStrip)
/*  786:     */       {
/*  787:1527 */         this.m_chars.setLength(this.m_textPendingStart);
/*  788:     */       }
/*  789:1531 */       else if (length > 0)
/*  790:     */       {
/*  791:1532 */         int exName = this.m_expandedNameTable.getExpandedTypeID(3);
/*  792:1533 */         int dataIndex = this.m_data.size();
/*  793:     */         
/*  794:1535 */         this.m_previous = addNode(this.m_coalescedTextType, exName, this.m_parents.peek(), this.m_previous, dataIndex, false);
/*  795:     */         
/*  796:     */ 
/*  797:1538 */         this.m_data.addElement(this.m_textPendingStart);
/*  798:1539 */         this.m_data.addElement(length);
/*  799:     */       }
/*  800:1544 */       this.m_textPendingStart = -1;
/*  801:1545 */       this.m_textType = (this.m_coalescedTextType = 3);
/*  802:     */     }
/*  803:     */   }
/*  804:     */   
/*  805:     */   public InputSource resolveEntity(String publicId, String systemId)
/*  806:     */     throws SAXException
/*  807:     */   {
/*  808:1577 */     return null;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public void notationDecl(String name, String publicId, String systemId)
/*  812:     */     throws SAXException
/*  813:     */   {}
/*  814:     */   
/*  815:     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
/*  816:     */     throws SAXException
/*  817:     */   {
/*  818:1631 */     if (null == this.m_entities) {
/*  819:1633 */       this.m_entities = new Vector();
/*  820:     */     }
/*  821:     */     try
/*  822:     */     {
/*  823:1638 */       systemId = SystemIDResolver.getAbsoluteURI(systemId, getDocumentBaseURI());
/*  824:     */     }
/*  825:     */     catch (Exception e)
/*  826:     */     {
/*  827:1643 */       throw new SAXException(e);
/*  828:     */     }
/*  829:1647 */     this.m_entities.addElement(publicId);
/*  830:     */     
/*  831:     */ 
/*  832:1650 */     this.m_entities.addElement(systemId);
/*  833:     */     
/*  834:     */ 
/*  835:1653 */     this.m_entities.addElement(notationName);
/*  836:     */     
/*  837:     */ 
/*  838:1656 */     this.m_entities.addElement(name);
/*  839:     */   }
/*  840:     */   
/*  841:     */   public void setDocumentLocator(Locator locator)
/*  842:     */   {
/*  843:1676 */     this.m_locator = locator;
/*  844:1677 */     this.m_systemId = locator.getSystemId();
/*  845:     */   }
/*  846:     */   
/*  847:     */   public void startDocument()
/*  848:     */     throws SAXException
/*  849:     */   {
/*  850:1693 */     int doc = addNode(9, this.m_expandedNameTable.getExpandedTypeID(9), -1, -1, 0, true);
/*  851:     */     
/*  852:     */ 
/*  853:     */ 
/*  854:1697 */     this.m_parents.push(doc);
/*  855:1698 */     this.m_previous = -1;
/*  856:     */     
/*  857:1700 */     this.m_contextIndexes.push(this.m_prefixMappings.size());
/*  858:     */   }
/*  859:     */   
/*  860:     */   public void endDocument()
/*  861:     */     throws SAXException
/*  862:     */   {
/*  863:1715 */     charactersFlush();
/*  864:     */     
/*  865:1717 */     this.m_nextsib.setElementAt(-1, 0);
/*  866:1719 */     if (this.m_firstch.elementAt(0) == -2) {
/*  867:1720 */       this.m_firstch.setElementAt(-1, 0);
/*  868:     */     }
/*  869:1722 */     if (-1 != this.m_previous) {
/*  870:1723 */       this.m_nextsib.setElementAt(-1, this.m_previous);
/*  871:     */     }
/*  872:1725 */     this.m_parents = null;
/*  873:1726 */     this.m_prefixMappings = null;
/*  874:1727 */     this.m_contextIndexes = null;
/*  875:     */     
/*  876:1729 */     this.m_endDocumentOccured = true;
/*  877:     */     
/*  878:     */ 
/*  879:1732 */     this.m_locator = null;
/*  880:     */   }
/*  881:     */   
/*  882:     */   public void startPrefixMapping(String prefix, String uri)
/*  883:     */     throws SAXException
/*  884:     */   {
/*  885:1756 */     if (null == prefix) {
/*  886:1757 */       prefix = "";
/*  887:     */     }
/*  888:1758 */     this.m_prefixMappings.addElement(prefix);
/*  889:1759 */     this.m_prefixMappings.addElement(uri);
/*  890:     */   }
/*  891:     */   
/*  892:     */   public void endPrefixMapping(String prefix)
/*  893:     */     throws SAXException
/*  894:     */   {
/*  895:1779 */     if (null == prefix) {
/*  896:1780 */       prefix = "";
/*  897:     */     }
/*  898:1782 */     int index = this.m_contextIndexes.peek() - 1;
/*  899:     */     do
/*  900:     */     {
/*  901:1786 */       index = this.m_prefixMappings.indexOf(prefix, ++index);
/*  902:1787 */     } while ((index >= 0) && ((index & 0x1) == 1));
/*  903:1790 */     if (index > -1)
/*  904:     */     {
/*  905:1792 */       this.m_prefixMappings.setElementAt("%@$#^@#", index);
/*  906:1793 */       this.m_prefixMappings.setElementAt("%@$#^@#", index + 1);
/*  907:     */     }
/*  908:     */   }
/*  909:     */   
/*  910:     */   protected boolean declAlreadyDeclared(String prefix)
/*  911:     */   {
/*  912:1810 */     int startDecls = this.m_contextIndexes.peek();
/*  913:1811 */     Vector prefixMappings = this.m_prefixMappings;
/*  914:1812 */     int nDecls = prefixMappings.size();
/*  915:1814 */     for (int i = startDecls; i < nDecls; i += 2)
/*  916:     */     {
/*  917:1816 */       String prefixDecl = (String)prefixMappings.elementAt(i);
/*  918:1818 */       if (prefixDecl != null) {
/*  919:1821 */         if (prefixDecl.equals(prefix)) {
/*  920:1822 */           return true;
/*  921:     */         }
/*  922:     */       }
/*  923:     */     }
/*  924:1825 */     return false;
/*  925:     */   }
/*  926:     */   
/*  927:1828 */   boolean m_pastFirstElement = false;
/*  928:     */   
/*  929:     */   public void startElement(String uri, String localName, String qName, Attributes attributes)
/*  930:     */     throws SAXException
/*  931:     */   {
/*  932:1876 */     charactersFlush();
/*  933:     */     
/*  934:1878 */     int exName = this.m_expandedNameTable.getExpandedTypeID(uri, localName, 1);
/*  935:1879 */     String prefix = getPrefix(qName, uri);
/*  936:1880 */     int prefixIndex = null != prefix ? this.m_valuesOrPrefixes.stringToIndex(qName) : 0;
/*  937:     */     
/*  938:     */ 
/*  939:1883 */     int elemNode = addNode(1, exName, this.m_parents.peek(), this.m_previous, prefixIndex, true);
/*  940:1886 */     if (this.m_indexing) {
/*  941:1887 */       indexNode(exName, elemNode);
/*  942:     */     }
/*  943:1890 */     this.m_parents.push(elemNode);
/*  944:     */     
/*  945:1892 */     int startDecls = this.m_contextIndexes.peek();
/*  946:1893 */     int nDecls = this.m_prefixMappings.size();
/*  947:1894 */     int prev = -1;
/*  948:1896 */     if (!this.m_pastFirstElement)
/*  949:     */     {
/*  950:1899 */       prefix = "xml";
/*  951:1900 */       String declURL = "http://www.w3.org/XML/1998/namespace";
/*  952:1901 */       exName = this.m_expandedNameTable.getExpandedTypeID(null, prefix, 13);
/*  953:1902 */       int val = this.m_valuesOrPrefixes.stringToIndex(declURL);
/*  954:1903 */       prev = addNode(13, exName, elemNode, prev, val, false);
/*  955:     */       
/*  956:1905 */       this.m_pastFirstElement = true;
/*  957:     */     }
/*  958:1908 */     for (int i = startDecls; i < nDecls; i += 2)
/*  959:     */     {
/*  960:1910 */       prefix = (String)this.m_prefixMappings.elementAt(i);
/*  961:1912 */       if (prefix != null)
/*  962:     */       {
/*  963:1915 */         String declURL = (String)this.m_prefixMappings.elementAt(i + 1);
/*  964:     */         
/*  965:1917 */         exName = this.m_expandedNameTable.getExpandedTypeID(null, prefix, 13);
/*  966:     */         
/*  967:1919 */         int val = this.m_valuesOrPrefixes.stringToIndex(declURL);
/*  968:     */         
/*  969:1921 */         prev = addNode(13, exName, elemNode, prev, val, false);
/*  970:     */       }
/*  971:     */     }
/*  972:1925 */     int n = attributes.getLength();
/*  973:1927 */     for (int i = 0; i < n; i++)
/*  974:     */     {
/*  975:1929 */       String attrUri = attributes.getURI(i);
/*  976:1930 */       String attrQName = attributes.getQName(i);
/*  977:1931 */       String valString = attributes.getValue(i);
/*  978:     */       
/*  979:1933 */       prefix = getPrefix(attrQName, attrUri);
/*  980:     */       
/*  981:     */ 
/*  982:     */ 
/*  983:1937 */       String attrLocalName = attributes.getLocalName(i);
/*  984:     */       int nodeType;
/*  985:1939 */       if ((null != attrQName) && ((attrQName.equals("xmlns")) || (attrQName.startsWith("xmlns:"))))
/*  986:     */       {
/*  987:1943 */         if (declAlreadyDeclared(prefix)) {
/*  988:     */           continue;
/*  989:     */         }
/*  990:1946 */         nodeType = 13;
/*  991:     */       }
/*  992:     */       else
/*  993:     */       {
/*  994:1950 */         nodeType = 2;
/*  995:1952 */         if (attributes.getType(i).equalsIgnoreCase("ID")) {
/*  996:1953 */           setIDAttribute(valString, elemNode);
/*  997:     */         }
/*  998:     */       }
/*  999:1958 */       if (null == valString) {
/* 1000:1959 */         valString = "";
/* 1001:     */       }
/* 1002:1961 */       int val = this.m_valuesOrPrefixes.stringToIndex(valString);
/* 1003:1964 */       if (null != prefix)
/* 1004:     */       {
/* 1005:1967 */         prefixIndex = this.m_valuesOrPrefixes.stringToIndex(attrQName);
/* 1006:     */         
/* 1007:1969 */         int dataIndex = this.m_data.size();
/* 1008:     */         
/* 1009:1971 */         this.m_data.addElement(prefixIndex);
/* 1010:1972 */         this.m_data.addElement(val);
/* 1011:     */         
/* 1012:1974 */         val = -dataIndex;
/* 1013:     */       }
/* 1014:1977 */       exName = this.m_expandedNameTable.getExpandedTypeID(attrUri, attrLocalName, nodeType);
/* 1015:1978 */       prev = addNode(nodeType, exName, elemNode, prev, val, false);
/* 1016:     */     }
/* 1017:1982 */     if (-1 != prev) {
/* 1018:1983 */       this.m_nextsib.setElementAt(-1, prev);
/* 1019:     */     }
/* 1020:1985 */     if (null != this.m_wsfilter)
/* 1021:     */     {
/* 1022:1987 */       short wsv = this.m_wsfilter.getShouldStripSpace(makeNodeHandle(elemNode), this);
/* 1023:1988 */       boolean shouldStrip = 2 == wsv ? true : 3 == wsv ? getShouldStripWhitespace() : false;
/* 1024:     */       
/* 1025:     */ 
/* 1026:     */ 
/* 1027:1992 */       pushShouldStripWhitespace(shouldStrip);
/* 1028:     */     }
/* 1029:1995 */     this.m_previous = -1;
/* 1030:     */     
/* 1031:1997 */     this.m_contextIndexes.push(this.m_prefixMappings.size());
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public void endElement(String uri, String localName, String qName)
/* 1035:     */     throws SAXException
/* 1036:     */   {
/* 1037:2027 */     charactersFlush();
/* 1038:     */     
/* 1039:     */ 
/* 1040:     */ 
/* 1041:2031 */     this.m_contextIndexes.quickPop(1);
/* 1042:     */     
/* 1043:     */ 
/* 1044:2034 */     int topContextIndex = this.m_contextIndexes.peek();
/* 1045:2035 */     if (topContextIndex != this.m_prefixMappings.size()) {
/* 1046:2036 */       this.m_prefixMappings.setSize(topContextIndex);
/* 1047:     */     }
/* 1048:2039 */     int lastNode = this.m_previous;
/* 1049:     */     
/* 1050:2041 */     this.m_previous = this.m_parents.pop();
/* 1051:2044 */     if (-1 == lastNode) {
/* 1052:2045 */       this.m_firstch.setElementAt(-1, this.m_previous);
/* 1053:     */     } else {
/* 1054:2047 */       this.m_nextsib.setElementAt(-1, lastNode);
/* 1055:     */     }
/* 1056:2049 */     popShouldStripWhitespace();
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public void characters(char[] ch, int start, int length)
/* 1060:     */     throws SAXException
/* 1061:     */   {
/* 1062:2070 */     if (this.m_textPendingStart == -1)
/* 1063:     */     {
/* 1064:2072 */       this.m_textPendingStart = this.m_chars.size();
/* 1065:2073 */       this.m_coalescedTextType = this.m_textType;
/* 1066:     */     }
/* 1067:2079 */     else if (this.m_textType == 3)
/* 1068:     */     {
/* 1069:2081 */       this.m_coalescedTextType = 3;
/* 1070:     */     }
/* 1071:2084 */     this.m_chars.append(ch, start, length);
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/* 1075:     */     throws SAXException
/* 1076:     */   {
/* 1077:2109 */     characters(ch, start, length);
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public void processingInstruction(String target, String data)
/* 1081:     */     throws SAXException
/* 1082:     */   {
/* 1083:2133 */     charactersFlush();
/* 1084:     */     
/* 1085:2135 */     int exName = this.m_expandedNameTable.getExpandedTypeID(null, target, 7);
/* 1086:     */     
/* 1087:2137 */     int dataIndex = this.m_valuesOrPrefixes.stringToIndex(data);
/* 1088:     */     
/* 1089:2139 */     this.m_previous = addNode(7, exName, this.m_parents.peek(), this.m_previous, dataIndex, false);
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public void skippedEntity(String name)
/* 1093:     */     throws SAXException
/* 1094:     */   {}
/* 1095:     */   
/* 1096:     */   public void warning(SAXParseException e)
/* 1097:     */     throws SAXException
/* 1098:     */   {
/* 1099:2186 */     System.err.println(e.getMessage());
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public void error(SAXParseException e)
/* 1103:     */     throws SAXException
/* 1104:     */   {
/* 1105:2205 */     throw e;
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public void fatalError(SAXParseException e)
/* 1109:     */     throws SAXException
/* 1110:     */   {
/* 1111:2227 */     throw e;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public void elementDecl(String name, String model)
/* 1115:     */     throws SAXException
/* 1116:     */   {}
/* 1117:     */   
/* 1118:     */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value)
/* 1119:     */     throws SAXException
/* 1120:     */   {}
/* 1121:     */   
/* 1122:     */   public void internalEntityDecl(String name, String value)
/* 1123:     */     throws SAXException
/* 1124:     */   {}
/* 1125:     */   
/* 1126:     */   public void externalEntityDecl(String name, String publicId, String systemId)
/* 1127:     */     throws SAXException
/* 1128:     */   {}
/* 1129:     */   
/* 1130:     */   public void startDTD(String name, String publicId, String systemId)
/* 1131:     */     throws SAXException
/* 1132:     */   {
/* 1133:2351 */     this.m_insideDTD = true;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public void endDTD()
/* 1137:     */     throws SAXException
/* 1138:     */   {
/* 1139:2363 */     this.m_insideDTD = false;
/* 1140:     */   }
/* 1141:     */   
/* 1142:     */   public void startEntity(String name)
/* 1143:     */     throws SAXException
/* 1144:     */   {}
/* 1145:     */   
/* 1146:     */   public void endEntity(String name)
/* 1147:     */     throws SAXException
/* 1148:     */   {}
/* 1149:     */   
/* 1150:     */   public void startCDATA()
/* 1151:     */     throws SAXException
/* 1152:     */   {
/* 1153:2419 */     this.m_textType = 4;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public void endCDATA()
/* 1157:     */     throws SAXException
/* 1158:     */   {
/* 1159:2430 */     this.m_textType = 3;
/* 1160:     */   }
/* 1161:     */   
/* 1162:     */   public void comment(char[] ch, int start, int length)
/* 1163:     */     throws SAXException
/* 1164:     */   {
/* 1165:2448 */     if (this.m_insideDTD) {
/* 1166:2449 */       return;
/* 1167:     */     }
/* 1168:2451 */     charactersFlush();
/* 1169:     */     
/* 1170:2453 */     int exName = this.m_expandedNameTable.getExpandedTypeID(8);
/* 1171:     */     
/* 1172:     */ 
/* 1173:     */ 
/* 1174:2457 */     int dataIndex = this.m_valuesOrPrefixes.stringToIndex(new String(ch, start, length));
/* 1175:     */     
/* 1176:     */ 
/* 1177:     */ 
/* 1178:2461 */     this.m_previous = addNode(8, exName, this.m_parents.peek(), this.m_previous, dataIndex, false);
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   public void setProperty(String property, Object value) {}
/* 1182:     */   
/* 1183:     */   public SourceLocator getSourceLocatorFor(int node)
/* 1184:     */   {
/* 1185:2488 */     if (this.m_useSourceLocationProperty)
/* 1186:     */     {
/* 1187:2491 */       node = makeNodeIdentity(node);
/* 1188:     */       
/* 1189:     */ 
/* 1190:2494 */       return new NodeLocator(null, this.m_sourceSystemId.elementAt(node), this.m_sourceLine.elementAt(node), this.m_sourceColumn.elementAt(node));
/* 1191:     */     }
/* 1192:2499 */     if (this.m_locator != null) {
/* 1193:2501 */       return new NodeLocator(null, this.m_locator.getSystemId(), -1, -1);
/* 1194:     */     }
/* 1195:2503 */     if (this.m_systemId != null) {
/* 1196:2505 */       return new NodeLocator(null, this.m_systemId, -1, -1);
/* 1197:     */     }
/* 1198:2507 */     return null;
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   public String getFixedNames(int type)
/* 1202:     */   {
/* 1203:2511 */     return m_fixednames[type];
/* 1204:     */   }
/* 1205:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.sax2dtm.SAX2DTM
 * JD-Core Version:    0.7.0.1
 */