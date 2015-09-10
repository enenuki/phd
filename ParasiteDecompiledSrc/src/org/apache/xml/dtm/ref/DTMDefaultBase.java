/*    1:     */ package org.apache.xml.dtm.ref;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.OutputStream;
/*    7:     */ import java.io.PrintStream;
/*    8:     */ import java.util.Vector;
/*    9:     */ import javax.xml.transform.Source;
/*   10:     */ import javax.xml.transform.SourceLocator;
/*   11:     */ import org.apache.xml.dtm.DTM;
/*   12:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*   13:     */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   14:     */ import org.apache.xml.dtm.DTMException;
/*   15:     */ import org.apache.xml.dtm.DTMManager;
/*   16:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   17:     */ import org.apache.xml.res.XMLMessages;
/*   18:     */ import org.apache.xml.utils.BoolStack;
/*   19:     */ import org.apache.xml.utils.SuballocatedIntVector;
/*   20:     */ import org.apache.xml.utils.XMLString;
/*   21:     */ import org.apache.xml.utils.XMLStringFactory;
/*   22:     */ import org.w3c.dom.Node;
/*   23:     */ import org.xml.sax.ContentHandler;
/*   24:     */ import org.xml.sax.DTDHandler;
/*   25:     */ import org.xml.sax.EntityResolver;
/*   26:     */ import org.xml.sax.ErrorHandler;
/*   27:     */ import org.xml.sax.SAXException;
/*   28:     */ import org.xml.sax.ext.DeclHandler;
/*   29:     */ import org.xml.sax.ext.LexicalHandler;
/*   30:     */ 
/*   31:     */ public abstract class DTMDefaultBase
/*   32:     */   implements DTM
/*   33:     */ {
/*   34:     */   static final boolean JJK_DEBUG = false;
/*   35:     */   public static final int ROOTNODE = 0;
/*   36:  58 */   protected int m_size = 0;
/*   37:     */   protected SuballocatedIntVector m_exptype;
/*   38:     */   protected SuballocatedIntVector m_firstch;
/*   39:     */   protected SuballocatedIntVector m_nextsib;
/*   40:     */   protected SuballocatedIntVector m_prevsib;
/*   41:     */   protected SuballocatedIntVector m_parent;
/*   42:  76 */   protected Vector m_namespaceDeclSets = null;
/*   43:  80 */   protected SuballocatedIntVector m_namespaceDeclSetElements = null;
/*   44:     */   protected int[][][] m_elemIndexes;
/*   45:     */   public static final int DEFAULT_BLOCKSIZE = 512;
/*   46:     */   public static final int DEFAULT_NUMBLOCKS = 32;
/*   47:     */   public static final int DEFAULT_NUMBLOCKS_SMALL = 4;
/*   48:     */   protected static final int NOTPROCESSED = -2;
/*   49:     */   public DTMManager m_mgr;
/*   50: 117 */   protected DTMManagerDefault m_mgrDefault = null;
/*   51:     */   protected SuballocatedIntVector m_dtmIdent;
/*   52:     */   protected String m_documentBaseURI;
/*   53:     */   protected DTMWSFilter m_wsfilter;
/*   54: 138 */   protected boolean m_shouldStripWS = false;
/*   55:     */   protected BoolStack m_shouldStripWhitespaceStack;
/*   56:     */   protected XMLStringFactory m_xstrf;
/*   57:     */   protected ExpandedNameTable m_expandedNameTable;
/*   58:     */   protected boolean m_indexing;
/*   59:     */   protected DTMAxisTraverser[] m_traversers;
/*   60:     */   
/*   61:     */   public DTMDefaultBase(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing)
/*   62:     */   {
/*   63: 171 */     this(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, 512, true, false);
/*   64:     */   }
/*   65:     */   
/*   66:     */   public DTMDefaultBase(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean usePrevsib, boolean newNameTable)
/*   67:     */   {
/*   68:     */     int numblocks;
/*   69: 199 */     if (blocksize <= 64)
/*   70:     */     {
/*   71: 201 */       numblocks = 4;
/*   72: 202 */       this.m_dtmIdent = new SuballocatedIntVector(4, 1);
/*   73:     */     }
/*   74:     */     else
/*   75:     */     {
/*   76: 206 */       numblocks = 32;
/*   77: 207 */       this.m_dtmIdent = new SuballocatedIntVector(32);
/*   78:     */     }
/*   79: 210 */     this.m_exptype = new SuballocatedIntVector(blocksize, numblocks);
/*   80: 211 */     this.m_firstch = new SuballocatedIntVector(blocksize, numblocks);
/*   81: 212 */     this.m_nextsib = new SuballocatedIntVector(blocksize, numblocks);
/*   82: 213 */     this.m_parent = new SuballocatedIntVector(blocksize, numblocks);
/*   83: 218 */     if (usePrevsib) {
/*   84: 219 */       this.m_prevsib = new SuballocatedIntVector(blocksize, numblocks);
/*   85:     */     }
/*   86: 221 */     this.m_mgr = mgr;
/*   87: 222 */     if ((mgr instanceof DTMManagerDefault)) {
/*   88: 223 */       this.m_mgrDefault = ((DTMManagerDefault)mgr);
/*   89:     */     }
/*   90: 225 */     this.m_documentBaseURI = (null != source ? source.getSystemId() : null);
/*   91: 226 */     this.m_dtmIdent.setElementAt(dtmIdentity, 0);
/*   92: 227 */     this.m_wsfilter = whiteSpaceFilter;
/*   93: 228 */     this.m_xstrf = xstringfactory;
/*   94: 229 */     this.m_indexing = doIndexing;
/*   95: 231 */     if (doIndexing) {
/*   96: 233 */       this.m_expandedNameTable = new ExpandedNameTable();
/*   97:     */     } else {
/*   98: 239 */       this.m_expandedNameTable = this.m_mgrDefault.getExpandedNameTable(this);
/*   99:     */     }
/*  100: 242 */     if (null != whiteSpaceFilter)
/*  101:     */     {
/*  102: 244 */       this.m_shouldStripWhitespaceStack = new BoolStack();
/*  103:     */       
/*  104: 246 */       pushShouldStripWhitespace(false);
/*  105:     */     }
/*  106:     */   }
/*  107:     */   
/*  108:     */   protected void ensureSizeOfIndex(int namespaceID, int LocalNameID)
/*  109:     */   {
/*  110: 259 */     if (null == this.m_elemIndexes)
/*  111:     */     {
/*  112: 261 */       this.m_elemIndexes = new int[namespaceID + 20][][];
/*  113:     */     }
/*  114: 263 */     else if (this.m_elemIndexes.length <= namespaceID)
/*  115:     */     {
/*  116: 265 */       int[][][] indexes = this.m_elemIndexes;
/*  117:     */       
/*  118: 267 */       this.m_elemIndexes = new int[namespaceID + 20][][];
/*  119:     */       
/*  120: 269 */       System.arraycopy(indexes, 0, this.m_elemIndexes, 0, indexes.length);
/*  121:     */     }
/*  122: 272 */     int[][] localNameIndex = this.m_elemIndexes[namespaceID];
/*  123: 274 */     if (null == localNameIndex)
/*  124:     */     {
/*  125: 276 */       localNameIndex = new int[LocalNameID + 100][];
/*  126: 277 */       this.m_elemIndexes[namespaceID] = localNameIndex;
/*  127:     */     }
/*  128: 279 */     else if (localNameIndex.length <= LocalNameID)
/*  129:     */     {
/*  130: 281 */       int[][] indexes = localNameIndex;
/*  131:     */       
/*  132: 283 */       localNameIndex = new int[LocalNameID + 100][];
/*  133:     */       
/*  134: 285 */       System.arraycopy(indexes, 0, localNameIndex, 0, indexes.length);
/*  135:     */       
/*  136: 287 */       this.m_elemIndexes[namespaceID] = localNameIndex;
/*  137:     */     }
/*  138: 290 */     int[] elemHandles = localNameIndex[LocalNameID];
/*  139: 292 */     if (null == elemHandles)
/*  140:     */     {
/*  141: 294 */       elemHandles = new int[''];
/*  142: 295 */       localNameIndex[LocalNameID] = elemHandles;
/*  143: 296 */       elemHandles[0] = 1;
/*  144:     */     }
/*  145: 298 */     else if (elemHandles.length <= elemHandles[0] + 1)
/*  146:     */     {
/*  147: 300 */       int[] indexes = elemHandles;
/*  148:     */       
/*  149: 302 */       elemHandles = new int[elemHandles[0] + 1024];
/*  150:     */       
/*  151: 304 */       System.arraycopy(indexes, 0, elemHandles, 0, indexes.length);
/*  152:     */       
/*  153: 306 */       localNameIndex[LocalNameID] = elemHandles;
/*  154:     */     }
/*  155:     */   }
/*  156:     */   
/*  157:     */   protected void indexNode(int expandedTypeID, int identity)
/*  158:     */   {
/*  159: 320 */     ExpandedNameTable ent = this.m_expandedNameTable;
/*  160: 321 */     short type = ent.getType(expandedTypeID);
/*  161: 323 */     if (1 == type)
/*  162:     */     {
/*  163: 325 */       int namespaceID = ent.getNamespaceID(expandedTypeID);
/*  164: 326 */       int localNameID = ent.getLocalNameID(expandedTypeID);
/*  165:     */       
/*  166: 328 */       ensureSizeOfIndex(namespaceID, localNameID);
/*  167:     */       
/*  168: 330 */       int[] index = this.m_elemIndexes[namespaceID][localNameID];
/*  169:     */       
/*  170: 332 */       index[index[0]] = identity;
/*  171:     */       
/*  172: 334 */       index[0] += 1;
/*  173:     */     }
/*  174:     */   }
/*  175:     */   
/*  176:     */   protected int findGTE(int[] list, int start, int len, int value)
/*  177:     */   {
/*  178: 354 */     int low = start;
/*  179: 355 */     int high = start + (len - 1);
/*  180: 356 */     int end = high;
/*  181: 358 */     while (low <= high)
/*  182:     */     {
/*  183: 360 */       int mid = (low + high) / 2;
/*  184: 361 */       int c = list[mid];
/*  185: 363 */       if (c > value) {
/*  186: 364 */         high = mid - 1;
/*  187: 365 */       } else if (c < value) {
/*  188: 366 */         low = mid + 1;
/*  189:     */       } else {
/*  190: 368 */         return mid;
/*  191:     */       }
/*  192:     */     }
/*  193: 371 */     return (low <= end) && (list[low] > value) ? low : -1;
/*  194:     */   }
/*  195:     */   
/*  196:     */   int findElementFromIndex(int nsIndex, int lnIndex, int firstPotential)
/*  197:     */   {
/*  198: 388 */     int[][][] indexes = this.m_elemIndexes;
/*  199: 390 */     if ((null != indexes) && (nsIndex < indexes.length))
/*  200:     */     {
/*  201: 392 */       int[][] lnIndexs = indexes[nsIndex];
/*  202: 394 */       if ((null != lnIndexs) && (lnIndex < lnIndexs.length))
/*  203:     */       {
/*  204: 396 */         int[] elems = lnIndexs[lnIndex];
/*  205: 398 */         if (null != elems)
/*  206:     */         {
/*  207: 400 */           int pos = findGTE(elems, 1, elems[0], firstPotential);
/*  208: 402 */           if (pos > -1) {
/*  209: 404 */             return elems[pos];
/*  210:     */           }
/*  211:     */         }
/*  212:     */       }
/*  213:     */     }
/*  214: 410 */     return -2;
/*  215:     */   }
/*  216:     */   
/*  217:     */   protected abstract int getNextNodeIdentity(int paramInt);
/*  218:     */   
/*  219:     */   protected abstract boolean nextNode();
/*  220:     */   
/*  221:     */   protected abstract int getNumberOfNodes();
/*  222:     */   
/*  223:     */   protected short _type(int identity)
/*  224:     */   {
/*  225: 462 */     int info = _exptype(identity);
/*  226: 464 */     if (-1 != info) {
/*  227: 465 */       return this.m_expandedNameTable.getType(info);
/*  228:     */     }
/*  229: 467 */     return -1;
/*  230:     */   }
/*  231:     */   
/*  232:     */   protected int _exptype(int identity)
/*  233:     */   {
/*  234: 479 */     if (identity == -1) {
/*  235: 480 */       return -1;
/*  236:     */     }
/*  237: 484 */     while (identity >= this.m_size) {
/*  238: 486 */       if ((!nextNode()) && (identity >= this.m_size)) {
/*  239: 487 */         return -1;
/*  240:     */       }
/*  241:     */     }
/*  242: 489 */     return this.m_exptype.elementAt(identity);
/*  243:     */   }
/*  244:     */   
/*  245:     */   protected int _level(int identity)
/*  246:     */   {
/*  247: 502 */     while (identity >= this.m_size)
/*  248:     */     {
/*  249: 504 */       boolean isMore = nextNode();
/*  250: 505 */       if ((!isMore) && (identity >= this.m_size)) {
/*  251: 506 */         return -1;
/*  252:     */       }
/*  253:     */     }
/*  254: 509 */     int i = 0;
/*  255: 510 */     while (-1 != (identity = _parent(identity))) {
/*  256: 511 */       i++;
/*  257:     */     }
/*  258: 512 */     return i;
/*  259:     */   }
/*  260:     */   
/*  261:     */   protected int _firstch(int identity)
/*  262:     */   {
/*  263: 526 */     int info = identity >= this.m_size ? -2 : this.m_firstch.elementAt(identity);
/*  264: 531 */     while (info == -2)
/*  265:     */     {
/*  266: 533 */       boolean isMore = nextNode();
/*  267: 535 */       if ((identity >= this.m_size) && (!isMore)) {
/*  268: 536 */         return -1;
/*  269:     */       }
/*  270: 539 */       info = this.m_firstch.elementAt(identity);
/*  271: 540 */       if ((info == -2) && (!isMore)) {
/*  272: 541 */         return -1;
/*  273:     */       }
/*  274:     */     }
/*  275: 545 */     return info;
/*  276:     */   }
/*  277:     */   
/*  278:     */   protected int _nextsib(int identity)
/*  279:     */   {
/*  280: 558 */     int info = identity >= this.m_size ? -2 : this.m_nextsib.elementAt(identity);
/*  281: 563 */     while (info == -2)
/*  282:     */     {
/*  283: 565 */       boolean isMore = nextNode();
/*  284: 567 */       if ((identity >= this.m_size) && (!isMore)) {
/*  285: 568 */         return -1;
/*  286:     */       }
/*  287: 571 */       info = this.m_nextsib.elementAt(identity);
/*  288: 572 */       if ((info == -2) && (!isMore)) {
/*  289: 573 */         return -1;
/*  290:     */       }
/*  291:     */     }
/*  292: 577 */     return info;
/*  293:     */   }
/*  294:     */   
/*  295:     */   protected int _prevsib(int identity)
/*  296:     */   {
/*  297: 590 */     if (identity < this.m_size) {
/*  298: 591 */       return this.m_prevsib.elementAt(identity);
/*  299:     */     }
/*  300:     */     for (;;)
/*  301:     */     {
/*  302: 598 */       boolean isMore = nextNode();
/*  303: 600 */       if ((identity >= this.m_size) && (!isMore)) {
/*  304: 601 */         return -1;
/*  305:     */       }
/*  306: 602 */       if (identity < this.m_size) {
/*  307: 603 */         return this.m_prevsib.elementAt(identity);
/*  308:     */       }
/*  309:     */     }
/*  310:     */   }
/*  311:     */   
/*  312:     */   protected int _parent(int identity)
/*  313:     */   {
/*  314: 617 */     if (identity < this.m_size) {
/*  315: 618 */       return this.m_parent.elementAt(identity);
/*  316:     */     }
/*  317:     */     for (;;)
/*  318:     */     {
/*  319: 625 */       boolean isMore = nextNode();
/*  320: 627 */       if ((identity >= this.m_size) && (!isMore)) {
/*  321: 628 */         return -1;
/*  322:     */       }
/*  323: 629 */       if (identity < this.m_size) {
/*  324: 630 */         return this.m_parent.elementAt(identity);
/*  325:     */       }
/*  326:     */     }
/*  327:     */   }
/*  328:     */   
/*  329:     */   public void dumpDTM(OutputStream os)
/*  330:     */   {
/*  331:     */     try
/*  332:     */     {
/*  333: 641 */       if (os == null)
/*  334:     */       {
/*  335: 643 */         File f = new File("DTMDump" + hashCode() + ".txt");
/*  336: 644 */         System.err.println("Dumping... " + f.getAbsolutePath());
/*  337: 645 */         os = new FileOutputStream(f);
/*  338:     */       }
/*  339: 647 */       PrintStream ps = new PrintStream(os);
/*  340: 649 */       while (nextNode()) {}
/*  341: 651 */       int nRecords = this.m_size;
/*  342:     */       
/*  343: 653 */       ps.println("Total nodes: " + nRecords);
/*  344: 655 */       for (int index = 0; index < nRecords; index++)
/*  345:     */       {
/*  346: 657 */         int i = makeNodeHandle(index);
/*  347: 658 */         ps.println("=========== index=" + index + " handle=" + i + " ===========");
/*  348: 659 */         ps.println("NodeName: " + getNodeName(i));
/*  349: 660 */         ps.println("NodeNameX: " + getNodeNameX(i));
/*  350: 661 */         ps.println("LocalName: " + getLocalName(i));
/*  351: 662 */         ps.println("NamespaceURI: " + getNamespaceURI(i));
/*  352: 663 */         ps.println("Prefix: " + getPrefix(i));
/*  353:     */         
/*  354: 665 */         int exTypeID = _exptype(index);
/*  355:     */         
/*  356: 667 */         ps.println("Expanded Type ID: " + Integer.toHexString(exTypeID));
/*  357:     */         
/*  358:     */ 
/*  359: 670 */         int type = _type(index);
/*  360:     */         String typestring;
/*  361: 673 */         switch (type)
/*  362:     */         {
/*  363:     */         case 2: 
/*  364: 676 */           typestring = "ATTRIBUTE_NODE";
/*  365: 677 */           break;
/*  366:     */         case 4: 
/*  367: 679 */           typestring = "CDATA_SECTION_NODE";
/*  368: 680 */           break;
/*  369:     */         case 8: 
/*  370: 682 */           typestring = "COMMENT_NODE";
/*  371: 683 */           break;
/*  372:     */         case 11: 
/*  373: 685 */           typestring = "DOCUMENT_FRAGMENT_NODE";
/*  374: 686 */           break;
/*  375:     */         case 9: 
/*  376: 688 */           typestring = "DOCUMENT_NODE";
/*  377: 689 */           break;
/*  378:     */         case 10: 
/*  379: 691 */           typestring = "DOCUMENT_NODE";
/*  380: 692 */           break;
/*  381:     */         case 1: 
/*  382: 694 */           typestring = "ELEMENT_NODE";
/*  383: 695 */           break;
/*  384:     */         case 6: 
/*  385: 697 */           typestring = "ENTITY_NODE";
/*  386: 698 */           break;
/*  387:     */         case 5: 
/*  388: 700 */           typestring = "ENTITY_REFERENCE_NODE";
/*  389: 701 */           break;
/*  390:     */         case 13: 
/*  391: 703 */           typestring = "NAMESPACE_NODE";
/*  392: 704 */           break;
/*  393:     */         case 12: 
/*  394: 706 */           typestring = "NOTATION_NODE";
/*  395: 707 */           break;
/*  396:     */         case -1: 
/*  397: 709 */           typestring = "NULL";
/*  398: 710 */           break;
/*  399:     */         case 7: 
/*  400: 712 */           typestring = "PROCESSING_INSTRUCTION_NODE";
/*  401: 713 */           break;
/*  402:     */         case 3: 
/*  403: 715 */           typestring = "TEXT_NODE";
/*  404: 716 */           break;
/*  405:     */         case 0: 
/*  406:     */         default: 
/*  407: 718 */           typestring = "Unknown!";
/*  408:     */         }
/*  409: 722 */         ps.println("Type: " + typestring);
/*  410:     */         
/*  411: 724 */         int firstChild = _firstch(index);
/*  412: 726 */         if (-1 == firstChild) {
/*  413: 727 */           ps.println("First child: DTM.NULL");
/*  414: 728 */         } else if (-2 == firstChild) {
/*  415: 729 */           ps.println("First child: NOTPROCESSED");
/*  416:     */         } else {
/*  417: 731 */           ps.println("First child: " + firstChild);
/*  418:     */         }
/*  419: 733 */         if (this.m_prevsib != null)
/*  420:     */         {
/*  421: 735 */           int prevSibling = _prevsib(index);
/*  422: 737 */           if (-1 == prevSibling) {
/*  423: 738 */             ps.println("Prev sibling: DTM.NULL");
/*  424: 739 */           } else if (-2 == prevSibling) {
/*  425: 740 */             ps.println("Prev sibling: NOTPROCESSED");
/*  426:     */           } else {
/*  427: 742 */             ps.println("Prev sibling: " + prevSibling);
/*  428:     */           }
/*  429:     */         }
/*  430: 745 */         int nextSibling = _nextsib(index);
/*  431: 747 */         if (-1 == nextSibling) {
/*  432: 748 */           ps.println("Next sibling: DTM.NULL");
/*  433: 749 */         } else if (-2 == nextSibling) {
/*  434: 750 */           ps.println("Next sibling: NOTPROCESSED");
/*  435:     */         } else {
/*  436: 752 */           ps.println("Next sibling: " + nextSibling);
/*  437:     */         }
/*  438: 754 */         int parent = _parent(index);
/*  439: 756 */         if (-1 == parent) {
/*  440: 757 */           ps.println("Parent: DTM.NULL");
/*  441: 758 */         } else if (-2 == parent) {
/*  442: 759 */           ps.println("Parent: NOTPROCESSED");
/*  443:     */         } else {
/*  444: 761 */           ps.println("Parent: " + parent);
/*  445:     */         }
/*  446: 763 */         int level = _level(index);
/*  447:     */         
/*  448: 765 */         ps.println("Level: " + level);
/*  449: 766 */         ps.println("Node Value: " + getNodeValue(i));
/*  450: 767 */         ps.println("String Value: " + getStringValue(i));
/*  451:     */       }
/*  452:     */     }
/*  453:     */     catch (IOException ioe)
/*  454:     */     {
/*  455: 772 */       ioe.printStackTrace(System.err);
/*  456: 773 */       throw new RuntimeException(ioe.getMessage());
/*  457:     */     }
/*  458:     */   }
/*  459:     */   
/*  460:     */   public String dumpNode(int nodeHandle)
/*  461:     */   {
/*  462: 792 */     if (nodeHandle == -1) {
/*  463: 793 */       return "[null]";
/*  464:     */     }
/*  465:     */     String typestring;
/*  466: 796 */     switch (getNodeType(nodeHandle))
/*  467:     */     {
/*  468:     */     case 2: 
/*  469: 799 */       typestring = "ATTR";
/*  470: 800 */       break;
/*  471:     */     case 4: 
/*  472: 802 */       typestring = "CDATA";
/*  473: 803 */       break;
/*  474:     */     case 8: 
/*  475: 805 */       typestring = "COMMENT";
/*  476: 806 */       break;
/*  477:     */     case 11: 
/*  478: 808 */       typestring = "DOC_FRAG";
/*  479: 809 */       break;
/*  480:     */     case 9: 
/*  481: 811 */       typestring = "DOC";
/*  482: 812 */       break;
/*  483:     */     case 10: 
/*  484: 814 */       typestring = "DOC_TYPE";
/*  485: 815 */       break;
/*  486:     */     case 1: 
/*  487: 817 */       typestring = "ELEMENT";
/*  488: 818 */       break;
/*  489:     */     case 6: 
/*  490: 820 */       typestring = "ENTITY";
/*  491: 821 */       break;
/*  492:     */     case 5: 
/*  493: 823 */       typestring = "ENT_REF";
/*  494: 824 */       break;
/*  495:     */     case 13: 
/*  496: 826 */       typestring = "NAMESPACE";
/*  497: 827 */       break;
/*  498:     */     case 12: 
/*  499: 829 */       typestring = "NOTATION";
/*  500: 830 */       break;
/*  501:     */     case -1: 
/*  502: 832 */       typestring = "null";
/*  503: 833 */       break;
/*  504:     */     case 7: 
/*  505: 835 */       typestring = "PI";
/*  506: 836 */       break;
/*  507:     */     case 3: 
/*  508: 838 */       typestring = "TEXT";
/*  509: 839 */       break;
/*  510:     */     case 0: 
/*  511:     */     default: 
/*  512: 841 */       typestring = "Unknown!";
/*  513:     */     }
/*  514: 845 */     StringBuffer sb = new StringBuffer();
/*  515: 846 */     sb.append("[" + nodeHandle + ": " + typestring + "(0x" + Integer.toHexString(getExpandedTypeID(nodeHandle)) + ") " + getNodeNameX(nodeHandle) + " {" + getNamespaceURI(nodeHandle) + "}" + "=\"" + getNodeValue(nodeHandle) + "\"]");
/*  516:     */     
/*  517:     */ 
/*  518:     */ 
/*  519: 850 */     return sb.toString();
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void setFeature(String featureId, boolean state) {}
/*  523:     */   
/*  524:     */   public boolean hasChildNodes(int nodeHandle)
/*  525:     */   {
/*  526: 882 */     int identity = makeNodeIdentity(nodeHandle);
/*  527: 883 */     int firstChild = _firstch(identity);
/*  528:     */     
/*  529: 885 */     return firstChild != -1;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public final int makeNodeHandle(int nodeIdentity)
/*  533:     */   {
/*  534: 904 */     if (-1 == nodeIdentity) {
/*  535: 904 */       return -1;
/*  536:     */     }
/*  537: 909 */     return this.m_dtmIdent.elementAt(nodeIdentity >>> 16) + (nodeIdentity & 0xFFFF);
/*  538:     */   }
/*  539:     */   
/*  540:     */   public final int makeNodeIdentity(int nodeHandle)
/*  541:     */   {
/*  542: 931 */     if (-1 == nodeHandle) {
/*  543: 931 */       return -1;
/*  544:     */     }
/*  545: 933 */     if (this.m_mgrDefault != null)
/*  546:     */     {
/*  547: 939 */       int whichDTMindex = nodeHandle >>> 16;
/*  548: 945 */       if (this.m_mgrDefault.m_dtms[whichDTMindex] != this) {
/*  549: 946 */         return -1;
/*  550:     */       }
/*  551: 948 */       return this.m_mgrDefault.m_dtm_offsets[whichDTMindex] | nodeHandle & 0xFFFF;
/*  552:     */     }
/*  553: 953 */     int whichDTMid = this.m_dtmIdent.indexOf(nodeHandle & 0xFFFF0000);
/*  554: 954 */     return whichDTMid == -1 ? -1 : (whichDTMid << 16) + (nodeHandle & 0xFFFF);
/*  555:     */   }
/*  556:     */   
/*  557:     */   public int getFirstChild(int nodeHandle)
/*  558:     */   {
/*  559: 972 */     int identity = makeNodeIdentity(nodeHandle);
/*  560: 973 */     int firstChild = _firstch(identity);
/*  561:     */     
/*  562: 975 */     return makeNodeHandle(firstChild);
/*  563:     */   }
/*  564:     */   
/*  565:     */   public int getTypedFirstChild(int nodeHandle, int nodeType)
/*  566:     */   {
/*  567:     */     int firstChild;
/*  568: 990 */     if (nodeType < 14) {
/*  569: 991 */       for (firstChild = _firstch(makeNodeIdentity(nodeHandle)); firstChild != -1; firstChild = _nextsib(firstChild))
/*  570:     */       {
/*  571: 994 */         int eType = _exptype(firstChild);
/*  572: 995 */         if ((eType == nodeType) || ((eType >= 14) && (this.m_expandedNameTable.getType(eType) == nodeType))) {
/*  573: 998 */           return makeNodeHandle(firstChild);
/*  574:     */         }
/*  575:     */       }
/*  576:     */     } else {
/*  577:1002 */       for (firstChild = _firstch(makeNodeIdentity(nodeHandle)); firstChild != -1; firstChild = _nextsib(firstChild)) {
/*  578:1005 */         if (_exptype(firstChild) == nodeType) {
/*  579:1006 */           return makeNodeHandle(firstChild);
/*  580:     */         }
/*  581:     */       }
/*  582:     */     }
/*  583:1010 */     return -1;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public int getLastChild(int nodeHandle)
/*  587:     */   {
/*  588:1025 */     int identity = makeNodeIdentity(nodeHandle);
/*  589:1026 */     int child = _firstch(identity);
/*  590:1027 */     int lastChild = -1;
/*  591:1029 */     while (child != -1)
/*  592:     */     {
/*  593:1031 */       lastChild = child;
/*  594:1032 */       child = _nextsib(child);
/*  595:     */     }
/*  596:1035 */     return makeNodeHandle(lastChild);
/*  597:     */   }
/*  598:     */   
/*  599:     */   public abstract int getAttributeNode(int paramInt, String paramString1, String paramString2);
/*  600:     */   
/*  601:     */   public int getFirstAttribute(int nodeHandle)
/*  602:     */   {
/*  603:1061 */     int nodeID = makeNodeIdentity(nodeHandle);
/*  604:     */     
/*  605:1063 */     return makeNodeHandle(getFirstAttributeIdentity(nodeID));
/*  606:     */   }
/*  607:     */   
/*  608:     */   protected int getFirstAttributeIdentity(int identity)
/*  609:     */   {
/*  610:1073 */     int type = _type(identity);
/*  611:1075 */     if (1 == type) {
/*  612:1078 */       while (-1 != (identity = getNextNodeIdentity(identity)))
/*  613:     */       {
/*  614:1082 */         type = _type(identity);
/*  615:1084 */         if (type == 2) {
/*  616:1086 */           return identity;
/*  617:     */         }
/*  618:1088 */         if (13 != type) {
/*  619:     */           break;
/*  620:     */         }
/*  621:     */       }
/*  622:     */     }
/*  623:1095 */     return -1;
/*  624:     */   }
/*  625:     */   
/*  626:     */   protected int getTypedAttribute(int nodeHandle, int attType)
/*  627:     */   {
/*  628:1108 */     int type = getNodeType(nodeHandle);
/*  629:1109 */     if (1 == type)
/*  630:     */     {
/*  631:1110 */       int identity = makeNodeIdentity(nodeHandle);
/*  632:1112 */       while (-1 != (identity = getNextNodeIdentity(identity)))
/*  633:     */       {
/*  634:1114 */         type = _type(identity);
/*  635:1116 */         if (type == 2)
/*  636:     */         {
/*  637:1118 */           if (_exptype(identity) == attType) {
/*  638:1118 */             return makeNodeHandle(identity);
/*  639:     */           }
/*  640:     */         }
/*  641:     */         else {
/*  642:1120 */           if (13 != type) {
/*  643:     */             break;
/*  644:     */           }
/*  645:     */         }
/*  646:     */       }
/*  647:     */     }
/*  648:1127 */     return -1;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public int getNextSibling(int nodeHandle)
/*  652:     */   {
/*  653:1140 */     if (nodeHandle == -1) {
/*  654:1141 */       return -1;
/*  655:     */     }
/*  656:1142 */     return makeNodeHandle(_nextsib(makeNodeIdentity(nodeHandle)));
/*  657:     */   }
/*  658:     */   
/*  659:     */   public int getTypedNextSibling(int nodeHandle, int nodeType)
/*  660:     */   {
/*  661:1155 */     if (nodeHandle == -1) {
/*  662:1156 */       return -1;
/*  663:     */     }
/*  664:1157 */     int node = makeNodeIdentity(nodeHandle);
/*  665:     */     int eType;
/*  666:1159 */     while (((node = _nextsib(node)) != -1) && ((eType = _exptype(node)) != nodeType) && (this.m_expandedNameTable.getType(eType) != nodeType)) {}
/*  667:1164 */     return node == -1 ? -1 : makeNodeHandle(node);
/*  668:     */   }
/*  669:     */   
/*  670:     */   public int getPreviousSibling(int nodeHandle)
/*  671:     */   {
/*  672:1178 */     if (nodeHandle == -1) {
/*  673:1179 */       return -1;
/*  674:     */     }
/*  675:1181 */     if (this.m_prevsib != null) {
/*  676:1182 */       return makeNodeHandle(_prevsib(makeNodeIdentity(nodeHandle)));
/*  677:     */     }
/*  678:1188 */     int nodeID = makeNodeIdentity(nodeHandle);
/*  679:1189 */     int parent = _parent(nodeID);
/*  680:1190 */     int node = _firstch(parent);
/*  681:1191 */     int result = -1;
/*  682:1192 */     while (node != nodeID)
/*  683:     */     {
/*  684:1194 */       result = node;
/*  685:1195 */       node = _nextsib(node);
/*  686:     */     }
/*  687:1197 */     return makeNodeHandle(result);
/*  688:     */   }
/*  689:     */   
/*  690:     */   public int getNextAttribute(int nodeHandle)
/*  691:     */   {
/*  692:1211 */     int nodeID = makeNodeIdentity(nodeHandle);
/*  693:1213 */     if (_type(nodeID) == 2) {
/*  694:1214 */       return makeNodeHandle(getNextAttributeIdentity(nodeID));
/*  695:     */     }
/*  696:1217 */     return -1;
/*  697:     */   }
/*  698:     */   
/*  699:     */   protected int getNextAttributeIdentity(int identity)
/*  700:     */   {
/*  701:1232 */     while (-1 != (identity = getNextNodeIdentity(identity)))
/*  702:     */     {
/*  703:1233 */       int type = _type(identity);
/*  704:1235 */       if (type == 2) {
/*  705:1236 */         return identity;
/*  706:     */       }
/*  707:1237 */       if (type != 13) {
/*  708:     */         break;
/*  709:     */       }
/*  710:     */     }
/*  711:1242 */     return -1;
/*  712:     */   }
/*  713:     */   
/*  714:1246 */   private Vector m_namespaceLists = null;
/*  715:     */   
/*  716:     */   protected void declareNamespaceInContext(int elementNodeIndex, int namespaceNodeIndex)
/*  717:     */   {
/*  718:1264 */     SuballocatedIntVector nsList = null;
/*  719:1265 */     if (this.m_namespaceDeclSets == null)
/*  720:     */     {
/*  721:1269 */       this.m_namespaceDeclSetElements = new SuballocatedIntVector(32);
/*  722:1270 */       this.m_namespaceDeclSetElements.addElement(elementNodeIndex);
/*  723:1271 */       this.m_namespaceDeclSets = new Vector();
/*  724:1272 */       nsList = new SuballocatedIntVector(32);
/*  725:1273 */       this.m_namespaceDeclSets.addElement(nsList);
/*  726:     */     }
/*  727:     */     else
/*  728:     */     {
/*  729:1279 */       int last = this.m_namespaceDeclSetElements.size() - 1;
/*  730:1281 */       if ((last >= 0) && (elementNodeIndex == this.m_namespaceDeclSetElements.elementAt(last))) {
/*  731:1283 */         nsList = (SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(last);
/*  732:     */       }
/*  733:     */     }
/*  734:1286 */     if (nsList == null)
/*  735:     */     {
/*  736:1288 */       this.m_namespaceDeclSetElements.addElement(elementNodeIndex);
/*  737:     */       
/*  738:1290 */       SuballocatedIntVector inherited = findNamespaceContext(_parent(elementNodeIndex));
/*  739:1293 */       if (inherited != null)
/*  740:     */       {
/*  741:1297 */         int isize = inherited.size();
/*  742:     */         
/*  743:     */ 
/*  744:     */ 
/*  745:1301 */         nsList = new SuballocatedIntVector(Math.max(Math.min(isize + 16, 2048), 32));
/*  746:1304 */         for (int i = 0; i < isize; i++) {
/*  747:1306 */           nsList.addElement(inherited.elementAt(i));
/*  748:     */         }
/*  749:     */       }
/*  750:     */       else
/*  751:     */       {
/*  752:1309 */         nsList = new SuballocatedIntVector(32);
/*  753:     */       }
/*  754:1312 */       this.m_namespaceDeclSets.addElement(nsList);
/*  755:     */     }
/*  756:1319 */     int newEType = _exptype(namespaceNodeIndex);
/*  757:1321 */     for (int i = nsList.size() - 1; i >= 0; i--) {
/*  758:1323 */       if (newEType == getExpandedTypeID(nsList.elementAt(i)))
/*  759:     */       {
/*  760:1325 */         nsList.setElementAt(makeNodeHandle(namespaceNodeIndex), i);
/*  761:1326 */         return;
/*  762:     */       }
/*  763:     */     }
/*  764:1329 */     nsList.addElement(makeNodeHandle(namespaceNodeIndex));
/*  765:     */   }
/*  766:     */   
/*  767:     */   protected SuballocatedIntVector findNamespaceContext(int elementNodeIndex)
/*  768:     */   {
/*  769:1341 */     if (null != this.m_namespaceDeclSetElements)
/*  770:     */     {
/*  771:1345 */       int wouldBeAt = findInSortedSuballocatedIntVector(this.m_namespaceDeclSetElements, elementNodeIndex);
/*  772:1347 */       if (wouldBeAt >= 0) {
/*  773:1348 */         return (SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(wouldBeAt);
/*  774:     */       }
/*  775:1349 */       if (wouldBeAt == -1) {
/*  776:1350 */         return null;
/*  777:     */       }
/*  778:1354 */       wouldBeAt = -1 - wouldBeAt;
/*  779:     */       
/*  780:     */ 
/*  781:1357 */       int candidate = this.m_namespaceDeclSetElements.elementAt(--wouldBeAt);
/*  782:1358 */       int ancestor = _parent(elementNodeIndex);
/*  783:1363 */       if ((wouldBeAt == 0) && (candidate < ancestor))
/*  784:     */       {
/*  785:1364 */         int rootHandle = getDocumentRoot(makeNodeHandle(elementNodeIndex));
/*  786:1365 */         int rootID = makeNodeIdentity(rootHandle);
/*  787:     */         int uppermostNSCandidateID;
/*  788:1368 */         if (getNodeType(rootHandle) == 9)
/*  789:     */         {
/*  790:1369 */           int ch = _firstch(rootID);
/*  791:1370 */           uppermostNSCandidateID = ch != -1 ? ch : rootID;
/*  792:     */         }
/*  793:     */         else
/*  794:     */         {
/*  795:1372 */           uppermostNSCandidateID = rootID;
/*  796:     */         }
/*  797:1375 */         if (candidate == uppermostNSCandidateID) {
/*  798:1376 */           return (SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(wouldBeAt);
/*  799:     */         }
/*  800:     */       }
/*  801:1380 */       while ((wouldBeAt >= 0) && (ancestor > 0))
/*  802:     */       {
/*  803:1382 */         if (candidate == ancestor) {
/*  804:1384 */           return (SuballocatedIntVector)this.m_namespaceDeclSets.elementAt(wouldBeAt);
/*  805:     */         }
/*  806:1385 */         if (candidate < ancestor)
/*  807:     */         {
/*  808:     */           do
/*  809:     */           {
/*  810:1388 */             ancestor = _parent(ancestor);
/*  811:1389 */           } while (candidate < ancestor);
/*  812:     */         }
/*  813:     */         else
/*  814:     */         {
/*  815:1390 */           if (wouldBeAt <= 0) {
/*  816:     */             break;
/*  817:     */           }
/*  818:1392 */           candidate = this.m_namespaceDeclSetElements.elementAt(--wouldBeAt);
/*  819:     */         }
/*  820:     */       }
/*  821:     */     }
/*  822:1399 */     return null;
/*  823:     */   }
/*  824:     */   
/*  825:     */   protected int findInSortedSuballocatedIntVector(SuballocatedIntVector vector, int lookfor)
/*  826:     */   {
/*  827:1418 */     int i = 0;
/*  828:1419 */     if (vector != null)
/*  829:     */     {
/*  830:1420 */       int first = 0;
/*  831:1421 */       int last = vector.size() - 1;
/*  832:1423 */       while (first <= last)
/*  833:     */       {
/*  834:1424 */         i = (first + last) / 2;
/*  835:1425 */         int test = lookfor - vector.elementAt(i);
/*  836:1426 */         if (test == 0) {
/*  837:1427 */           return i;
/*  838:     */         }
/*  839:1429 */         if (test < 0) {
/*  840:1430 */           last = i - 1;
/*  841:     */         } else {
/*  842:1433 */           first = i + 1;
/*  843:     */         }
/*  844:     */       }
/*  845:1437 */       if (first > i) {
/*  846:1438 */         i = first;
/*  847:     */       }
/*  848:     */     }
/*  849:1442 */     return -1 - i;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public int getFirstNamespaceNode(int nodeHandle, boolean inScope)
/*  853:     */   {
/*  854:1461 */     if (inScope)
/*  855:     */     {
/*  856:1463 */       int identity = makeNodeIdentity(nodeHandle);
/*  857:1464 */       if (_type(identity) == 1)
/*  858:     */       {
/*  859:1466 */         SuballocatedIntVector nsContext = findNamespaceContext(identity);
/*  860:1467 */         if ((nsContext == null) || (nsContext.size() < 1)) {
/*  861:1468 */           return -1;
/*  862:     */         }
/*  863:1470 */         return nsContext.elementAt(0);
/*  864:     */       }
/*  865:1473 */       return -1;
/*  866:     */     }
/*  867:1483 */     int identity = makeNodeIdentity(nodeHandle);
/*  868:1484 */     if (_type(identity) == 1)
/*  869:     */     {
/*  870:1486 */       while (-1 != (identity = getNextNodeIdentity(identity)))
/*  871:     */       {
/*  872:1488 */         int type = _type(identity);
/*  873:1489 */         if (type == 13) {
/*  874:1490 */           return makeNodeHandle(identity);
/*  875:     */         }
/*  876:1491 */         if (2 != type) {
/*  877:     */           break;
/*  878:     */         }
/*  879:     */       }
/*  880:1494 */       return -1;
/*  881:     */     }
/*  882:1497 */     return -1;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public int getNextNamespaceNode(int baseHandle, int nodeHandle, boolean inScope)
/*  886:     */   {
/*  887:1514 */     if (inScope)
/*  888:     */     {
/*  889:1521 */       SuballocatedIntVector nsContext = findNamespaceContext(makeNodeIdentity(baseHandle));
/*  890:1523 */       if (nsContext == null) {
/*  891:1524 */         return -1;
/*  892:     */       }
/*  893:1525 */       int i = 1 + nsContext.indexOf(nodeHandle);
/*  894:1526 */       if ((i <= 0) || (i == nsContext.size())) {
/*  895:1527 */         return -1;
/*  896:     */       }
/*  897:1529 */       return nsContext.elementAt(i);
/*  898:     */     }
/*  899:1534 */     int identity = makeNodeIdentity(nodeHandle);
/*  900:1535 */     while (-1 != (identity = getNextNodeIdentity(identity)))
/*  901:     */     {
/*  902:1537 */       int type = _type(identity);
/*  903:1538 */       if (type == 13) {
/*  904:1540 */         return makeNodeHandle(identity);
/*  905:     */       }
/*  906:1542 */       if (type != 2) {
/*  907:     */         break;
/*  908:     */       }
/*  909:     */     }
/*  910:1548 */     return -1;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public int getParent(int nodeHandle)
/*  914:     */   {
/*  915:1561 */     int identity = makeNodeIdentity(nodeHandle);
/*  916:1563 */     if (identity > 0) {
/*  917:1564 */       return makeNodeHandle(_parent(identity));
/*  918:     */     }
/*  919:1566 */     return -1;
/*  920:     */   }
/*  921:     */   
/*  922:     */   public int getDocument()
/*  923:     */   {
/*  924:1579 */     return this.m_dtmIdent.elementAt(0);
/*  925:     */   }
/*  926:     */   
/*  927:     */   public int getOwnerDocument(int nodeHandle)
/*  928:     */   {
/*  929:1597 */     if (9 == getNodeType(nodeHandle)) {
/*  930:1598 */       return -1;
/*  931:     */     }
/*  932:1600 */     return getDocumentRoot(nodeHandle);
/*  933:     */   }
/*  934:     */   
/*  935:     */   public int getDocumentRoot(int nodeHandle)
/*  936:     */   {
/*  937:1613 */     return getManager().getDTM(nodeHandle).getDocument();
/*  938:     */   }
/*  939:     */   
/*  940:     */   public abstract XMLString getStringValue(int paramInt);
/*  941:     */   
/*  942:     */   public int getStringValueChunkCount(int nodeHandle)
/*  943:     */   {
/*  944:1643 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*  945:     */     
/*  946:1645 */     return 0;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public char[] getStringValueChunk(int nodeHandle, int chunkIndex, int[] startAndLen)
/*  950:     */   {
/*  951:1666 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*  952:     */     
/*  953:1668 */     return null;
/*  954:     */   }
/*  955:     */   
/*  956:     */   public int getExpandedTypeID(int nodeHandle)
/*  957:     */   {
/*  958:1682 */     int id = makeNodeIdentity(nodeHandle);
/*  959:1683 */     if (id == -1) {
/*  960:1684 */       return -1;
/*  961:     */     }
/*  962:1685 */     return _exptype(id);
/*  963:     */   }
/*  964:     */   
/*  965:     */   public int getExpandedTypeID(String namespace, String localName, int type)
/*  966:     */   {
/*  967:1707 */     ExpandedNameTable ent = this.m_expandedNameTable;
/*  968:     */     
/*  969:1709 */     return ent.getExpandedTypeID(namespace, localName, type);
/*  970:     */   }
/*  971:     */   
/*  972:     */   public String getLocalNameFromExpandedNameID(int expandedNameID)
/*  973:     */   {
/*  974:1720 */     return this.m_expandedNameTable.getLocalName(expandedNameID);
/*  975:     */   }
/*  976:     */   
/*  977:     */   public String getNamespaceFromExpandedNameID(int expandedNameID)
/*  978:     */   {
/*  979:1732 */     return this.m_expandedNameTable.getNamespace(expandedNameID);
/*  980:     */   }
/*  981:     */   
/*  982:     */   public int getNamespaceType(int nodeHandle)
/*  983:     */   {
/*  984:1743 */     int identity = makeNodeIdentity(nodeHandle);
/*  985:1744 */     int expandedNameID = _exptype(identity);
/*  986:     */     
/*  987:1746 */     return this.m_expandedNameTable.getNamespaceID(expandedNameID);
/*  988:     */   }
/*  989:     */   
/*  990:     */   public abstract String getNodeName(int paramInt);
/*  991:     */   
/*  992:     */   public String getNodeNameX(int nodeHandle)
/*  993:     */   {
/*  994:1772 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*  995:     */     
/*  996:1774 */     return null;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public abstract String getLocalName(int paramInt);
/* 1000:     */   
/* 1001:     */   public abstract String getPrefix(int paramInt);
/* 1002:     */   
/* 1003:     */   public abstract String getNamespaceURI(int paramInt);
/* 1004:     */   
/* 1005:     */   public abstract String getNodeValue(int paramInt);
/* 1006:     */   
/* 1007:     */   public short getNodeType(int nodeHandle)
/* 1008:     */   {
/* 1009:1836 */     if (nodeHandle == -1) {
/* 1010:1837 */       return -1;
/* 1011:     */     }
/* 1012:1838 */     return this.m_expandedNameTable.getType(_exptype(makeNodeIdentity(nodeHandle)));
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public short getLevel(int nodeHandle)
/* 1016:     */   {
/* 1017:1852 */     int identity = makeNodeIdentity(nodeHandle);
/* 1018:1853 */     return (short)(_level(identity) + 1);
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public int getNodeIdent(int nodeHandle)
/* 1022:     */   {
/* 1023:1870 */     return makeNodeIdentity(nodeHandle);
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public int getNodeHandle(int nodeId)
/* 1027:     */   {
/* 1028:1887 */     return makeNodeHandle(nodeId);
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public boolean isSupported(String feature, String version)
/* 1032:     */   {
/* 1033:1908 */     return false;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public String getDocumentBaseURI()
/* 1037:     */   {
/* 1038:1920 */     return this.m_documentBaseURI;
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public void setDocumentBaseURI(String baseURI)
/* 1042:     */   {
/* 1043:1930 */     this.m_documentBaseURI = baseURI;
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public String getDocumentSystemIdentifier(int nodeHandle)
/* 1047:     */   {
/* 1048:1944 */     return this.m_documentBaseURI;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public String getDocumentEncoding(int nodeHandle)
/* 1052:     */   {
/* 1053:1959 */     return "UTF-8";
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public String getDocumentStandalone(int nodeHandle)
/* 1057:     */   {
/* 1058:1974 */     return null;
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public String getDocumentVersion(int documentHandle)
/* 1062:     */   {
/* 1063:1989 */     return null;
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public boolean getDocumentAllDeclarationsProcessed()
/* 1067:     */   {
/* 1068:2006 */     return true;
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public abstract String getDocumentTypeDeclarationSystemIdentifier();
/* 1072:     */   
/* 1073:     */   public abstract String getDocumentTypeDeclarationPublicIdentifier();
/* 1074:     */   
/* 1075:     */   public abstract int getElementById(String paramString);
/* 1076:     */   
/* 1077:     */   public abstract String getUnparsedEntityURI(String paramString);
/* 1078:     */   
/* 1079:     */   public boolean supportsPreStripping()
/* 1080:     */   {
/* 1081:2094 */     return true;
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public boolean isNodeAfter(int nodeHandle1, int nodeHandle2)
/* 1085:     */   {
/* 1086:2116 */     int index1 = makeNodeIdentity(nodeHandle1);
/* 1087:2117 */     int index2 = makeNodeIdentity(nodeHandle2);
/* 1088:     */     
/* 1089:2119 */     return (index1 != -1) && (index2 != -1) && (index1 <= index2);
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public boolean isCharacterElementContentWhitespace(int nodeHandle)
/* 1093:     */   {
/* 1094:2142 */     return false;
/* 1095:     */   }
/* 1096:     */   
/* 1097:     */   public boolean isDocumentAllDeclarationsProcessed(int documentHandle)
/* 1098:     */   {
/* 1099:2159 */     return true;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public abstract boolean isAttributeSpecified(int paramInt);
/* 1103:     */   
/* 1104:     */   public abstract void dispatchCharactersEvents(int paramInt, ContentHandler paramContentHandler, boolean paramBoolean)
/* 1105:     */     throws SAXException;
/* 1106:     */   
/* 1107:     */   public abstract void dispatchToEvents(int paramInt, ContentHandler paramContentHandler)
/* 1108:     */     throws SAXException;
/* 1109:     */   
/* 1110:     */   public Node getNode(int nodeHandle)
/* 1111:     */   {
/* 1112:2218 */     return new DTMNodeProxy(this, nodeHandle);
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public void appendChild(int newChild, boolean clone, boolean cloneDepth)
/* 1116:     */   {
/* 1117:2237 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public void appendTextChild(String str)
/* 1121:     */   {
/* 1122:2251 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/* 1123:     */   }
/* 1124:     */   
/* 1125:     */   protected void error(String msg)
/* 1126:     */   {
/* 1127:2261 */     throw new DTMException(msg);
/* 1128:     */   }
/* 1129:     */   
/* 1130:     */   protected boolean getShouldStripWhitespace()
/* 1131:     */   {
/* 1132:2272 */     return this.m_shouldStripWS;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   protected void pushShouldStripWhitespace(boolean shouldStrip)
/* 1136:     */   {
/* 1137:2284 */     this.m_shouldStripWS = shouldStrip;
/* 1138:2286 */     if (null != this.m_shouldStripWhitespaceStack) {
/* 1139:2287 */       this.m_shouldStripWhitespaceStack.push(shouldStrip);
/* 1140:     */     }
/* 1141:     */   }
/* 1142:     */   
/* 1143:     */   protected void popShouldStripWhitespace()
/* 1144:     */   {
/* 1145:2297 */     if (null != this.m_shouldStripWhitespaceStack) {
/* 1146:2298 */       this.m_shouldStripWS = this.m_shouldStripWhitespaceStack.popAndTop();
/* 1147:     */     }
/* 1148:     */   }
/* 1149:     */   
/* 1150:     */   protected void setShouldStripWhitespace(boolean shouldStrip)
/* 1151:     */   {
/* 1152:2311 */     this.m_shouldStripWS = shouldStrip;
/* 1153:2313 */     if (null != this.m_shouldStripWhitespaceStack) {
/* 1154:2314 */       this.m_shouldStripWhitespaceStack.setTop(shouldStrip);
/* 1155:     */     }
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public void documentRegistration() {}
/* 1159:     */   
/* 1160:     */   public void documentRelease() {}
/* 1161:     */   
/* 1162:     */   public void migrateTo(DTMManager mgr)
/* 1163:     */   {
/* 1164:2344 */     this.m_mgr = mgr;
/* 1165:2345 */     if ((mgr instanceof DTMManagerDefault)) {
/* 1166:2346 */       this.m_mgrDefault = ((DTMManagerDefault)mgr);
/* 1167:     */     }
/* 1168:     */   }
/* 1169:     */   
/* 1170:     */   public DTMManager getManager()
/* 1171:     */   {
/* 1172:2357 */     return this.m_mgr;
/* 1173:     */   }
/* 1174:     */   
/* 1175:     */   public SuballocatedIntVector getDTMIDs()
/* 1176:     */   {
/* 1177:2368 */     if (this.m_mgr == null) {
/* 1178:2368 */       return null;
/* 1179:     */     }
/* 1180:2369 */     return this.m_dtmIdent;
/* 1181:     */   }
/* 1182:     */   
/* 1183:     */   public abstract SourceLocator getSourceLocatorFor(int paramInt);
/* 1184:     */   
/* 1185:     */   public abstract DeclHandler getDeclHandler();
/* 1186:     */   
/* 1187:     */   public abstract ErrorHandler getErrorHandler();
/* 1188:     */   
/* 1189:     */   public abstract DTDHandler getDTDHandler();
/* 1190:     */   
/* 1191:     */   public abstract EntityResolver getEntityResolver();
/* 1192:     */   
/* 1193:     */   public abstract LexicalHandler getLexicalHandler();
/* 1194:     */   
/* 1195:     */   public abstract ContentHandler getContentHandler();
/* 1196:     */   
/* 1197:     */   public abstract boolean needsTwoThreads();
/* 1198:     */   
/* 1199:     */   public abstract DTMAxisIterator getTypedAxisIterator(int paramInt1, int paramInt2);
/* 1200:     */   
/* 1201:     */   public abstract DTMAxisIterator getAxisIterator(int paramInt);
/* 1202:     */   
/* 1203:     */   public abstract DTMAxisTraverser getAxisTraverser(int paramInt);
/* 1204:     */   
/* 1205:     */   public abstract void setProperty(String paramString, Object paramObject);
/* 1206:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMDefaultBase
 * JD-Core Version:    0.7.0.1
 */