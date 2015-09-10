/*    1:     */ package org.apache.xml.dtm.ref;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import javax.xml.transform.SourceLocator;
/*    5:     */ import org.apache.xml.dtm.DTM;
/*    6:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*    7:     */ import org.apache.xml.dtm.DTMAxisTraverser;
/*    8:     */ import org.apache.xml.dtm.DTMManager;
/*    9:     */ import org.apache.xml.dtm.DTMWSFilter;
/*   10:     */ import org.apache.xml.utils.FastStringBuffer;
/*   11:     */ import org.apache.xml.utils.XMLString;
/*   12:     */ import org.apache.xml.utils.XMLStringFactory;
/*   13:     */ import org.w3c.dom.Node;
/*   14:     */ import org.xml.sax.Attributes;
/*   15:     */ import org.xml.sax.ContentHandler;
/*   16:     */ import org.xml.sax.DTDHandler;
/*   17:     */ import org.xml.sax.EntityResolver;
/*   18:     */ import org.xml.sax.ErrorHandler;
/*   19:     */ import org.xml.sax.Locator;
/*   20:     */ import org.xml.sax.SAXException;
/*   21:     */ import org.xml.sax.ext.DeclHandler;
/*   22:     */ import org.xml.sax.ext.LexicalHandler;
/*   23:     */ 
/*   24:     */ public class DTMDocumentImpl
/*   25:     */   implements DTM, ContentHandler, LexicalHandler
/*   26:     */ {
/*   27:     */   protected static final byte DOCHANDLE_SHIFT = 22;
/*   28:     */   protected static final int NODEHANDLE_MASK = 8388607;
/*   29:     */   protected static final int DOCHANDLE_MASK = -8388608;
/*   30:  76 */   int m_docHandle = -1;
/*   31:  77 */   int m_docElement = -1;
/*   32:  80 */   int currentParent = 0;
/*   33:  81 */   int previousSibling = 0;
/*   34:  82 */   protected int m_currentNode = -1;
/*   35:  88 */   private boolean previousSiblingWasParent = false;
/*   36:  90 */   int[] gotslot = new int[4];
/*   37:  93 */   private boolean done = false;
/*   38:  94 */   boolean m_isError = false;
/*   39:  96 */   private final boolean DEBUG = false;
/*   40:     */   protected String m_documentBaseURI;
/*   41: 112 */   private IncrementalSAXSource m_incrSAXSource = null;
/*   42: 121 */   ChunkedIntArray nodes = new ChunkedIntArray(4);
/*   43: 125 */   private FastStringBuffer m_char = new FastStringBuffer();
/*   44: 128 */   private int m_char_current_start = 0;
/*   45: 135 */   private DTMStringPool m_localNames = new DTMStringPool();
/*   46: 136 */   private DTMStringPool m_nsNames = new DTMStringPool();
/*   47: 137 */   private DTMStringPool m_prefixNames = new DTMStringPool();
/*   48: 145 */   private ExpandedNameTable m_expandedNames = new ExpandedNameTable();
/*   49:     */   private XMLStringFactory m_xsf;
/*   50:     */   
/*   51:     */   public DTMDocumentImpl(DTMManager mgr, int documentNumber, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory)
/*   52:     */   {
/*   53: 164 */     initDocument(documentNumber);
/*   54: 165 */     this.m_xsf = xstringfactory;
/*   55:     */   }
/*   56:     */   
/*   57:     */   public void setIncrementalSAXSource(IncrementalSAXSource source)
/*   58:     */   {
/*   59: 181 */     this.m_incrSAXSource = source;
/*   60:     */     
/*   61:     */ 
/*   62: 184 */     source.setContentHandler(this);
/*   63: 185 */     source.setLexicalHandler(this);
/*   64:     */   }
/*   65:     */   
/*   66:     */   private final int appendNode(int w0, int w1, int w2, int w3)
/*   67:     */   {
/*   68: 209 */     int slotnumber = this.nodes.appendSlot(w0, w1, w2, w3);
/*   69: 213 */     if (this.previousSiblingWasParent) {
/*   70: 214 */       this.nodes.writeEntry(this.previousSibling, 2, slotnumber);
/*   71:     */     }
/*   72: 216 */     this.previousSiblingWasParent = false;
/*   73:     */     
/*   74: 218 */     return slotnumber;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public void setFeature(String featureId, boolean state) {}
/*   78:     */   
/*   79:     */   public void setLocalNameTable(DTMStringPool poolRef)
/*   80:     */   {
/*   81: 241 */     this.m_localNames = poolRef;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public DTMStringPool getLocalNameTable()
/*   85:     */   {
/*   86: 250 */     return this.m_localNames;
/*   87:     */   }
/*   88:     */   
/*   89:     */   public void setNsNameTable(DTMStringPool poolRef)
/*   90:     */   {
/*   91: 261 */     this.m_nsNames = poolRef;
/*   92:     */   }
/*   93:     */   
/*   94:     */   public DTMStringPool getNsNameTable()
/*   95:     */   {
/*   96: 270 */     return this.m_nsNames;
/*   97:     */   }
/*   98:     */   
/*   99:     */   public void setPrefixNameTable(DTMStringPool poolRef)
/*  100:     */   {
/*  101: 281 */     this.m_prefixNames = poolRef;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public DTMStringPool getPrefixNameTable()
/*  105:     */   {
/*  106: 290 */     return this.m_prefixNames;
/*  107:     */   }
/*  108:     */   
/*  109:     */   void setContentBuffer(FastStringBuffer buffer)
/*  110:     */   {
/*  111: 300 */     this.m_char = buffer;
/*  112:     */   }
/*  113:     */   
/*  114:     */   FastStringBuffer getContentBuffer()
/*  115:     */   {
/*  116: 309 */     return this.m_char;
/*  117:     */   }
/*  118:     */   
/*  119:     */   public ContentHandler getContentHandler()
/*  120:     */   {
/*  121: 323 */     if ((this.m_incrSAXSource instanceof IncrementalSAXSource_Filter)) {
/*  122: 324 */       return (ContentHandler)this.m_incrSAXSource;
/*  123:     */     }
/*  124: 326 */     return this;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public LexicalHandler getLexicalHandler()
/*  128:     */   {
/*  129: 342 */     if ((this.m_incrSAXSource instanceof IncrementalSAXSource_Filter)) {
/*  130: 343 */       return (LexicalHandler)this.m_incrSAXSource;
/*  131:     */     }
/*  132: 345 */     return this;
/*  133:     */   }
/*  134:     */   
/*  135:     */   public EntityResolver getEntityResolver()
/*  136:     */   {
/*  137: 356 */     return null;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public DTDHandler getDTDHandler()
/*  141:     */   {
/*  142: 367 */     return null;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public ErrorHandler getErrorHandler()
/*  146:     */   {
/*  147: 378 */     return null;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public DeclHandler getDeclHandler()
/*  151:     */   {
/*  152: 389 */     return null;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public boolean needsTwoThreads()
/*  156:     */   {
/*  157: 399 */     return null != this.m_incrSAXSource;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void characters(char[] ch, int start, int length)
/*  161:     */     throws SAXException
/*  162:     */   {
/*  163: 413 */     this.m_char.append(ch, start, length);
/*  164:     */   }
/*  165:     */   
/*  166:     */   private void processAccumulatedText()
/*  167:     */   {
/*  168: 419 */     int len = this.m_char.length();
/*  169: 420 */     if (len != this.m_char_current_start)
/*  170:     */     {
/*  171: 423 */       appendTextChild(this.m_char_current_start, len - this.m_char_current_start);
/*  172: 424 */       this.m_char_current_start = len;
/*  173:     */     }
/*  174:     */   }
/*  175:     */   
/*  176:     */   public void endDocument()
/*  177:     */     throws SAXException
/*  178:     */   {
/*  179: 432 */     appendEndDocument();
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void endElement(String namespaceURI, String localName, String qName)
/*  183:     */     throws SAXException
/*  184:     */   {
/*  185: 438 */     processAccumulatedText();
/*  186:     */     
/*  187:     */ 
/*  188: 441 */     appendEndElement();
/*  189:     */   }
/*  190:     */   
/*  191:     */   public void endPrefixMapping(String prefix)
/*  192:     */     throws SAXException
/*  193:     */   {}
/*  194:     */   
/*  195:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/*  196:     */     throws SAXException
/*  197:     */   {}
/*  198:     */   
/*  199:     */   public void processingInstruction(String target, String data)
/*  200:     */     throws SAXException
/*  201:     */   {
/*  202: 456 */     processAccumulatedText();
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void setDocumentLocator(Locator locator) {}
/*  206:     */   
/*  207:     */   public void skippedEntity(String name)
/*  208:     */     throws SAXException
/*  209:     */   {
/*  210: 466 */     processAccumulatedText();
/*  211:     */   }
/*  212:     */   
/*  213:     */   public void startDocument()
/*  214:     */     throws SAXException
/*  215:     */   {
/*  216: 472 */     appendStartDocument();
/*  217:     */   }
/*  218:     */   
/*  219:     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
/*  220:     */     throws SAXException
/*  221:     */   {
/*  222: 478 */     processAccumulatedText();
/*  223:     */     
/*  224:     */ 
/*  225: 481 */     String prefix = null;
/*  226: 482 */     int colon = qName.indexOf(':');
/*  227: 483 */     if (colon > 0) {
/*  228: 484 */       prefix = qName.substring(0, colon);
/*  229:     */     }
/*  230: 487 */     System.out.println("Prefix=" + prefix + " index=" + this.m_prefixNames.stringToIndex(prefix));
/*  231: 488 */     appendStartElement(this.m_nsNames.stringToIndex(namespaceURI), this.m_localNames.stringToIndex(localName), this.m_prefixNames.stringToIndex(prefix));
/*  232:     */     
/*  233:     */ 
/*  234:     */ 
/*  235:     */ 
/*  236:     */ 
/*  237:     */ 
/*  238: 495 */     int nAtts = atts == null ? 0 : atts.getLength();
/*  239: 497 */     for (int i = nAtts - 1; i >= 0; i--)
/*  240:     */     {
/*  241: 499 */       qName = atts.getQName(i);
/*  242: 500 */       if ((qName.startsWith("xmlns:")) || ("xmlns".equals(qName)))
/*  243:     */       {
/*  244: 502 */         prefix = null;
/*  245: 503 */         colon = qName.indexOf(':');
/*  246: 504 */         if (colon > 0) {
/*  247: 506 */           prefix = qName.substring(0, colon);
/*  248:     */         } else {
/*  249: 511 */           prefix = null;
/*  250:     */         }
/*  251: 515 */         appendNSDeclaration(this.m_prefixNames.stringToIndex(prefix), this.m_nsNames.stringToIndex(atts.getValue(i)), atts.getType(i).equalsIgnoreCase("ID"));
/*  252:     */       }
/*  253:     */     }
/*  254: 522 */     for (int i = nAtts - 1; i >= 0; i--)
/*  255:     */     {
/*  256: 524 */       qName = atts.getQName(i);
/*  257: 525 */       if ((!qName.startsWith("xmlns:")) && (!"xmlns".equals(qName)))
/*  258:     */       {
/*  259: 530 */         prefix = null;
/*  260: 531 */         colon = qName.indexOf(':');
/*  261: 532 */         if (colon > 0)
/*  262:     */         {
/*  263: 534 */           prefix = qName.substring(0, colon);
/*  264: 535 */           localName = qName.substring(colon + 1);
/*  265:     */         }
/*  266:     */         else
/*  267:     */         {
/*  268: 539 */           prefix = "";
/*  269: 540 */           localName = qName;
/*  270:     */         }
/*  271: 544 */         this.m_char.append(atts.getValue(i));
/*  272: 545 */         int contentEnd = this.m_char.length();
/*  273: 547 */         if ((!"xmlns".equals(prefix)) && (!"xmlns".equals(qName))) {
/*  274: 548 */           appendAttribute(this.m_nsNames.stringToIndex(atts.getURI(i)), this.m_localNames.stringToIndex(localName), this.m_prefixNames.stringToIndex(prefix), atts.getType(i).equalsIgnoreCase("ID"), this.m_char_current_start, contentEnd - this.m_char_current_start);
/*  275:     */         }
/*  276: 553 */         this.m_char_current_start = contentEnd;
/*  277:     */       }
/*  278:     */     }
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void startPrefixMapping(String prefix, String uri)
/*  282:     */     throws SAXException
/*  283:     */   {}
/*  284:     */   
/*  285:     */   public void comment(char[] ch, int start, int length)
/*  286:     */     throws SAXException
/*  287:     */   {
/*  288: 570 */     processAccumulatedText();
/*  289:     */     
/*  290: 572 */     this.m_char.append(ch, start, length);
/*  291: 573 */     appendComment(this.m_char_current_start, length);
/*  292: 574 */     this.m_char_current_start += length;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public void endCDATA()
/*  296:     */     throws SAXException
/*  297:     */   {}
/*  298:     */   
/*  299:     */   public void endDTD()
/*  300:     */     throws SAXException
/*  301:     */   {}
/*  302:     */   
/*  303:     */   public void endEntity(String name)
/*  304:     */     throws SAXException
/*  305:     */   {}
/*  306:     */   
/*  307:     */   public void startCDATA()
/*  308:     */     throws SAXException
/*  309:     */   {}
/*  310:     */   
/*  311:     */   public void startDTD(String name, String publicId, String systemId)
/*  312:     */     throws SAXException
/*  313:     */   {}
/*  314:     */   
/*  315:     */   public void startEntity(String name)
/*  316:     */     throws SAXException
/*  317:     */   {}
/*  318:     */   
/*  319:     */   final void initDocument(int documentNumber)
/*  320:     */   {
/*  321: 625 */     this.m_docHandle = (documentNumber << 22);
/*  322:     */     
/*  323:     */ 
/*  324: 628 */     this.nodes.writeSlot(0, 9, -1, -1, 0);
/*  325:     */     
/*  326: 630 */     this.done = false;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public boolean hasChildNodes(int nodeHandle)
/*  330:     */   {
/*  331: 999 */     return getFirstChild(nodeHandle) != -1;
/*  332:     */   }
/*  333:     */   
/*  334:     */   public int getFirstChild(int nodeHandle)
/*  335:     */   {
/*  336:1013 */     nodeHandle &= 0x7FFFFF;
/*  337:     */     
/*  338:1015 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  339:     */     
/*  340:     */ 
/*  341:1018 */     short type = (short)(this.gotslot[0] & 0xFFFF);
/*  342:1021 */     if ((type == 1) || (type == 9) || (type == 5))
/*  343:     */     {
/*  344:1031 */       int kid = nodeHandle + 1;
/*  345:1032 */       this.nodes.readSlot(kid, this.gotslot);
/*  346:1033 */       while (2 == (this.gotslot[0] & 0xFFFF))
/*  347:     */       {
/*  348:1035 */         kid = this.gotslot[2];
/*  349:1037 */         if (kid == -1) {
/*  350:1037 */           return -1;
/*  351:     */         }
/*  352:1038 */         this.nodes.readSlot(kid, this.gotslot);
/*  353:     */       }
/*  354:1041 */       if (this.gotslot[1] == nodeHandle)
/*  355:     */       {
/*  356:1043 */         int firstChild = kid | this.m_docHandle;
/*  357:     */         
/*  358:1045 */         return firstChild;
/*  359:     */       }
/*  360:     */     }
/*  361:1050 */     return -1;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public int getLastChild(int nodeHandle)
/*  365:     */   {
/*  366:1064 */     nodeHandle &= 0x7FFFFF;
/*  367:     */     
/*  368:1066 */     int lastChild = -1;
/*  369:1067 */     for (int nextkid = getFirstChild(nodeHandle); nextkid != -1; nextkid = getNextSibling(nextkid)) {
/*  370:1069 */       lastChild = nextkid;
/*  371:     */     }
/*  372:1071 */     return lastChild | this.m_docHandle;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public int getAttributeNode(int nodeHandle, String namespaceURI, String name)
/*  376:     */   {
/*  377:1087 */     int nsIndex = this.m_nsNames.stringToIndex(namespaceURI);
/*  378:1088 */     int nameIndex = this.m_localNames.stringToIndex(name);
/*  379:1089 */     nodeHandle &= 0x7FFFFF;
/*  380:1090 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  381:1091 */     short type = (short)(this.gotslot[0] & 0xFFFF);
/*  382:1093 */     if (type == 1) {
/*  383:1094 */       nodeHandle++;
/*  384:     */     }
/*  385:1096 */     while (type == 2)
/*  386:     */     {
/*  387:1097 */       if ((nsIndex == this.gotslot[0] << 16) && (this.gotslot[3] == nameIndex)) {
/*  388:1098 */         return nodeHandle | this.m_docHandle;
/*  389:     */       }
/*  390:1100 */       nodeHandle = this.gotslot[2];
/*  391:1101 */       this.nodes.readSlot(nodeHandle, this.gotslot);
/*  392:     */     }
/*  393:1103 */     return -1;
/*  394:     */   }
/*  395:     */   
/*  396:     */   public int getFirstAttribute(int nodeHandle)
/*  397:     */   {
/*  398:1113 */     nodeHandle &= 0x7FFFFF;
/*  399:1121 */     if (1 != (this.nodes.readEntry(nodeHandle, 0) & 0xFFFF)) {
/*  400:1122 */       return -1;
/*  401:     */     }
/*  402:1124 */     nodeHandle++;
/*  403:1125 */     return 2 == (this.nodes.readEntry(nodeHandle, 0) & 0xFFFF) ? nodeHandle | this.m_docHandle : -1;
/*  404:     */   }
/*  405:     */   
/*  406:     */   public int getFirstNamespaceNode(int nodeHandle, boolean inScope)
/*  407:     */   {
/*  408:1144 */     return -1;
/*  409:     */   }
/*  410:     */   
/*  411:     */   public int getNextSibling(int nodeHandle)
/*  412:     */   {
/*  413:1165 */     nodeHandle &= 0x7FFFFF;
/*  414:1167 */     if (nodeHandle == 0) {
/*  415:1168 */       return -1;
/*  416:     */     }
/*  417:1170 */     short type = (short)(this.nodes.readEntry(nodeHandle, 0) & 0xFFFF);
/*  418:1171 */     if ((type == 1) || (type == 2) || (type == 5))
/*  419:     */     {
/*  420:1173 */       int nextSib = this.nodes.readEntry(nodeHandle, 2);
/*  421:1174 */       if (nextSib == -1) {
/*  422:1175 */         return -1;
/*  423:     */       }
/*  424:1176 */       if (nextSib != 0) {
/*  425:1177 */         return this.m_docHandle | nextSib;
/*  426:     */       }
/*  427:     */     }
/*  428:1181 */     int thisParent = this.nodes.readEntry(nodeHandle, 1);
/*  429:1183 */     if (this.nodes.readEntry(++nodeHandle, 1) == thisParent) {
/*  430:1184 */       return this.m_docHandle | nodeHandle;
/*  431:     */     }
/*  432:1186 */     return -1;
/*  433:     */   }
/*  434:     */   
/*  435:     */   public int getPreviousSibling(int nodeHandle)
/*  436:     */   {
/*  437:1199 */     nodeHandle &= 0x7FFFFF;
/*  438:1201 */     if (nodeHandle == 0) {
/*  439:1202 */       return -1;
/*  440:     */     }
/*  441:1204 */     int parent = this.nodes.readEntry(nodeHandle, 1);
/*  442:1205 */     int kid = -1;
/*  443:1206 */     for (int nextkid = getFirstChild(parent); nextkid != nodeHandle; nextkid = getNextSibling(nextkid)) {
/*  444:1208 */       kid = nextkid;
/*  445:     */     }
/*  446:1210 */     return kid | this.m_docHandle;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public int getNextAttribute(int nodeHandle)
/*  450:     */   {
/*  451:1223 */     nodeHandle &= 0x7FFFFF;
/*  452:1224 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  453:     */     
/*  454:     */ 
/*  455:     */ 
/*  456:     */ 
/*  457:     */ 
/*  458:1230 */     short type = (short)(this.gotslot[0] & 0xFFFF);
/*  459:1232 */     if (type == 1) {
/*  460:1233 */       return getFirstAttribute(nodeHandle);
/*  461:     */     }
/*  462:1234 */     if ((type == 2) && 
/*  463:1235 */       (this.gotslot[2] != -1)) {
/*  464:1236 */       return this.m_docHandle | this.gotslot[2];
/*  465:     */     }
/*  466:1238 */     return -1;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public int getNextNamespaceNode(int baseHandle, int namespaceHandle, boolean inScope)
/*  470:     */   {
/*  471:1253 */     return -1;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public int getNextDescendant(int subtreeRootHandle, int nodeHandle)
/*  475:     */   {
/*  476:1267 */     subtreeRootHandle &= 0x7FFFFF;
/*  477:1268 */     nodeHandle &= 0x7FFFFF;
/*  478:1270 */     if (nodeHandle == 0) {
/*  479:1271 */       return -1;
/*  480:     */     }
/*  481:1272 */     while (!this.m_isError)
/*  482:     */     {
/*  483:1274 */       if ((this.done) && (nodeHandle > this.nodes.slotsUsed())) {
/*  484:     */         break;
/*  485:     */       }
/*  486:1276 */       if (nodeHandle > subtreeRootHandle)
/*  487:     */       {
/*  488:1277 */         this.nodes.readSlot(nodeHandle + 1, this.gotslot);
/*  489:1278 */         if (this.gotslot[2] != 0)
/*  490:     */         {
/*  491:1279 */           short type = (short)(this.gotslot[0] & 0xFFFF);
/*  492:1280 */           if (type == 2)
/*  493:     */           {
/*  494:1281 */             nodeHandle += 2;
/*  495:     */           }
/*  496:     */           else
/*  497:     */           {
/*  498:1283 */             int nextParentPos = this.gotslot[1];
/*  499:1284 */             if (nextParentPos < subtreeRootHandle) {
/*  500:     */               break;
/*  501:     */             }
/*  502:1285 */             return this.m_docHandle | nodeHandle + 1;
/*  503:     */           }
/*  504:     */         }
/*  505:     */         else
/*  506:     */         {
/*  507:1289 */           if (this.done) {
/*  508:     */             break;
/*  509:     */           }
/*  510:     */         }
/*  511:     */       }
/*  512:     */       else
/*  513:     */       {
/*  514:1294 */         nodeHandle++;
/*  515:     */       }
/*  516:     */     }
/*  517:1298 */     return -1;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public int getNextFollowing(int axisContextHandle, int nodeHandle)
/*  521:     */   {
/*  522:1311 */     return -1;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public int getNextPreceding(int axisContextHandle, int nodeHandle)
/*  526:     */   {
/*  527:1324 */     nodeHandle &= 0x7FFFFF;
/*  528:1325 */     while (nodeHandle > 1)
/*  529:     */     {
/*  530:1326 */       nodeHandle--;
/*  531:1327 */       if (2 != (this.nodes.readEntry(nodeHandle, 0) & 0xFFFF)) {
/*  532:1338 */         return this.m_docHandle | this.nodes.specialFind(axisContextHandle, nodeHandle);
/*  533:     */       }
/*  534:     */     }
/*  535:1340 */     return -1;
/*  536:     */   }
/*  537:     */   
/*  538:     */   public int getParent(int nodeHandle)
/*  539:     */   {
/*  540:1354 */     return this.m_docHandle | this.nodes.readEntry(nodeHandle, 1);
/*  541:     */   }
/*  542:     */   
/*  543:     */   public int getDocumentRoot()
/*  544:     */   {
/*  545:1362 */     return this.m_docHandle | this.m_docElement;
/*  546:     */   }
/*  547:     */   
/*  548:     */   public int getDocument()
/*  549:     */   {
/*  550:1371 */     return this.m_docHandle;
/*  551:     */   }
/*  552:     */   
/*  553:     */   public int getOwnerDocument(int nodeHandle)
/*  554:     */   {
/*  555:1389 */     if ((nodeHandle & 0x7FFFFF) == 0) {
/*  556:1390 */       return -1;
/*  557:     */     }
/*  558:1391 */     return nodeHandle & 0xFF800000;
/*  559:     */   }
/*  560:     */   
/*  561:     */   public int getDocumentRoot(int nodeHandle)
/*  562:     */   {
/*  563:1408 */     if ((nodeHandle & 0x7FFFFF) == 0) {
/*  564:1409 */       return -1;
/*  565:     */     }
/*  566:1410 */     return nodeHandle & 0xFF800000;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public XMLString getStringValue(int nodeHandle)
/*  570:     */   {
/*  571:1424 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  572:1425 */     int nodetype = this.gotslot[0] & 0xFF;
/*  573:1426 */     String value = null;
/*  574:1428 */     switch (nodetype)
/*  575:     */     {
/*  576:     */     case 3: 
/*  577:     */     case 4: 
/*  578:     */     case 8: 
/*  579:1432 */       value = this.m_char.getString(this.gotslot[2], this.gotslot[3]);
/*  580:1433 */       break;
/*  581:     */     }
/*  582:1441 */     return this.m_xsf.newstr(value);
/*  583:     */   }
/*  584:     */   
/*  585:     */   public int getStringValueChunkCount(int nodeHandle)
/*  586:     */   {
/*  587:1472 */     return 0;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public char[] getStringValueChunk(int nodeHandle, int chunkIndex, int[] startAndLen)
/*  591:     */   {
/*  592:1501 */     return new char[0];
/*  593:     */   }
/*  594:     */   
/*  595:     */   public int getExpandedTypeID(int nodeHandle)
/*  596:     */   {
/*  597:1511 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  598:1512 */     String qName = this.m_localNames.indexToString(this.gotslot[3]);
/*  599:     */     
/*  600:     */ 
/*  601:1515 */     int colonpos = qName.indexOf(":");
/*  602:1516 */     String localName = qName.substring(colonpos + 1);
/*  603:     */     
/*  604:1518 */     String namespace = this.m_nsNames.indexToString(this.gotslot[0] << 16);
/*  605:     */     
/*  606:1520 */     String expandedName = namespace + ":" + localName;
/*  607:1521 */     int expandedNameID = this.m_nsNames.stringToIndex(expandedName);
/*  608:     */     
/*  609:1523 */     return expandedNameID;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public int getExpandedTypeID(String namespace, String localName, int type)
/*  613:     */   {
/*  614:1541 */     String expandedName = namespace + ":" + localName;
/*  615:1542 */     int expandedNameID = this.m_nsNames.stringToIndex(expandedName);
/*  616:     */     
/*  617:1544 */     return expandedNameID;
/*  618:     */   }
/*  619:     */   
/*  620:     */   public String getLocalNameFromExpandedNameID(int ExpandedNameID)
/*  621:     */   {
/*  622:1557 */     String expandedName = this.m_localNames.indexToString(ExpandedNameID);
/*  623:     */     
/*  624:1559 */     int colonpos = expandedName.indexOf(":");
/*  625:1560 */     String localName = expandedName.substring(colonpos + 1);
/*  626:1561 */     return localName;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public String getNamespaceFromExpandedNameID(int ExpandedNameID)
/*  630:     */   {
/*  631:1574 */     String expandedName = this.m_localNames.indexToString(ExpandedNameID);
/*  632:     */     
/*  633:1576 */     int colonpos = expandedName.indexOf(":");
/*  634:1577 */     String nsName = expandedName.substring(0, colonpos);
/*  635:     */     
/*  636:1579 */     return nsName;
/*  637:     */   }
/*  638:     */   
/*  639:1586 */   private static final String[] fixednames = { null, null, null, "#text", "#cdata_section", null, null, null, "#comment", "#document", null, "#document-fragment", null };
/*  640:     */   
/*  641:     */   public String getNodeName(int nodeHandle)
/*  642:     */   {
/*  643:1605 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  644:1606 */     short type = (short)(this.gotslot[0] & 0xFFFF);
/*  645:1607 */     String name = fixednames[type];
/*  646:1608 */     if (null == name)
/*  647:     */     {
/*  648:1609 */       int i = this.gotslot[3];
/*  649:1610 */       System.out.println("got i=" + i + " " + (i >> 16) + "/" + (i & 0xFFFF));
/*  650:     */       
/*  651:1612 */       name = this.m_localNames.indexToString(i & 0xFFFF);
/*  652:1613 */       String prefix = this.m_prefixNames.indexToString(i >> 16);
/*  653:1614 */       if ((prefix != null) && (prefix.length() > 0)) {
/*  654:1615 */         name = prefix + ":" + name;
/*  655:     */       }
/*  656:     */     }
/*  657:1617 */     return name;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public String getNodeNameX(int nodeHandle)
/*  661:     */   {
/*  662:1628 */     return null;
/*  663:     */   }
/*  664:     */   
/*  665:     */   public String getLocalName(int nodeHandle)
/*  666:     */   {
/*  667:1642 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  668:1643 */     short type = (short)(this.gotslot[0] & 0xFFFF);
/*  669:1644 */     String name = "";
/*  670:1645 */     if ((type == 1) || (type == 2))
/*  671:     */     {
/*  672:1646 */       int i = this.gotslot[3];
/*  673:1647 */       name = this.m_localNames.indexToString(i & 0xFFFF);
/*  674:1648 */       if (name == null) {
/*  675:1648 */         name = "";
/*  676:     */       }
/*  677:     */     }
/*  678:1650 */     return name;
/*  679:     */   }
/*  680:     */   
/*  681:     */   public String getPrefix(int nodeHandle)
/*  682:     */   {
/*  683:1668 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  684:1669 */     short type = (short)(this.gotslot[0] & 0xFFFF);
/*  685:1670 */     String name = "";
/*  686:1671 */     if ((type == 1) || (type == 2))
/*  687:     */     {
/*  688:1672 */       int i = this.gotslot[3];
/*  689:1673 */       name = this.m_prefixNames.indexToString(i >> 16);
/*  690:1674 */       if (name == null) {
/*  691:1674 */         name = "";
/*  692:     */       }
/*  693:     */     }
/*  694:1676 */     return name;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public String getNamespaceURI(int nodeHandle)
/*  698:     */   {
/*  699:1688 */     return null;
/*  700:     */   }
/*  701:     */   
/*  702:     */   public String getNodeValue(int nodeHandle)
/*  703:     */   {
/*  704:1701 */     this.nodes.readSlot(nodeHandle, this.gotslot);
/*  705:1702 */     int nodetype = this.gotslot[0] & 0xFF;
/*  706:1703 */     String value = null;
/*  707:1705 */     switch (nodetype)
/*  708:     */     {
/*  709:     */     case 2: 
/*  710:1707 */       this.nodes.readSlot(nodeHandle + 1, this.gotslot);
/*  711:     */     case 3: 
/*  712:     */     case 4: 
/*  713:     */     case 8: 
/*  714:1711 */       value = this.m_char.getString(this.gotslot[2], this.gotslot[3]);
/*  715:1712 */       break;
/*  716:     */     }
/*  717:1719 */     return value;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public short getNodeType(int nodeHandle)
/*  721:     */   {
/*  722:1731 */     return (short)(this.nodes.readEntry(nodeHandle, 0) & 0xFFFF);
/*  723:     */   }
/*  724:     */   
/*  725:     */   public short getLevel(int nodeHandle)
/*  726:     */   {
/*  727:1743 */     short count = 0;
/*  728:1744 */     while (nodeHandle != 0)
/*  729:     */     {
/*  730:1745 */       count = (short)(count + 1);
/*  731:1746 */       nodeHandle = this.nodes.readEntry(nodeHandle, 1);
/*  732:     */     }
/*  733:1748 */     return count;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public boolean isSupported(String feature, String version)
/*  737:     */   {
/*  738:1765 */     return false;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public String getDocumentBaseURI()
/*  742:     */   {
/*  743:1777 */     return this.m_documentBaseURI;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public void setDocumentBaseURI(String baseURI)
/*  747:     */   {
/*  748:1788 */     this.m_documentBaseURI = baseURI;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public String getDocumentSystemIdentifier(int nodeHandle)
/*  752:     */   {
/*  753:1798 */     return null;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public String getDocumentEncoding(int nodeHandle)
/*  757:     */   {
/*  758:1807 */     return null;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public String getDocumentStandalone(int nodeHandle)
/*  762:     */   {
/*  763:1819 */     return null;
/*  764:     */   }
/*  765:     */   
/*  766:     */   public String getDocumentVersion(int documentHandle)
/*  767:     */   {
/*  768:1831 */     return null;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public boolean getDocumentAllDeclarationsProcessed()
/*  772:     */   {
/*  773:1843 */     return false;
/*  774:     */   }
/*  775:     */   
/*  776:     */   public String getDocumentTypeDeclarationSystemIdentifier()
/*  777:     */   {
/*  778:1853 */     return null;
/*  779:     */   }
/*  780:     */   
/*  781:     */   public String getDocumentTypeDeclarationPublicIdentifier()
/*  782:     */   {
/*  783:1863 */     return null;
/*  784:     */   }
/*  785:     */   
/*  786:     */   public int getElementById(String elementId)
/*  787:     */   {
/*  788:1882 */     return 0;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public String getUnparsedEntityURI(String name)
/*  792:     */   {
/*  793:1918 */     return null;
/*  794:     */   }
/*  795:     */   
/*  796:     */   public boolean supportsPreStripping()
/*  797:     */   {
/*  798:1930 */     return false;
/*  799:     */   }
/*  800:     */   
/*  801:     */   public boolean isNodeAfter(int nodeHandle1, int nodeHandle2)
/*  802:     */   {
/*  803:1950 */     return false;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public boolean isCharacterElementContentWhitespace(int nodeHandle)
/*  807:     */   {
/*  808:1968 */     return false;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public boolean isDocumentAllDeclarationsProcessed(int documentHandle)
/*  812:     */   {
/*  813:1982 */     return false;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public boolean isAttributeSpecified(int attributeHandle)
/*  817:     */   {
/*  818:1993 */     return false;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize)
/*  822:     */     throws SAXException
/*  823:     */   {}
/*  824:     */   
/*  825:     */   public void dispatchToEvents(int nodeHandle, ContentHandler ch)
/*  826:     */     throws SAXException
/*  827:     */   {}
/*  828:     */   
/*  829:     */   public Node getNode(int nodeHandle)
/*  830:     */   {
/*  831:2035 */     return null;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public void appendChild(int newChild, boolean clone, boolean cloneDepth)
/*  835:     */   {
/*  836:2056 */     boolean sameDoc = (newChild & 0xFF800000) == this.m_docHandle;
/*  837:2057 */     if ((!clone) && (!sameDoc)) {}
/*  838:     */   }
/*  839:     */   
/*  840:     */   public void appendTextChild(String str) {}
/*  841:     */   
/*  842:     */   void appendTextChild(int m_char_current_start, int contentLength)
/*  843:     */   {
/*  844:2095 */     int w0 = 3;
/*  845:     */     
/*  846:2097 */     int w1 = this.currentParent;
/*  847:     */     
/*  848:2099 */     int w2 = m_char_current_start;
/*  849:     */     
/*  850:2101 */     int w3 = contentLength;
/*  851:     */     
/*  852:2103 */     int ourslot = appendNode(w0, w1, w2, w3);
/*  853:2104 */     this.previousSibling = ourslot;
/*  854:     */   }
/*  855:     */   
/*  856:     */   void appendComment(int m_char_current_start, int contentLength)
/*  857:     */   {
/*  858:2118 */     int w0 = 8;
/*  859:     */     
/*  860:2120 */     int w1 = this.currentParent;
/*  861:     */     
/*  862:2122 */     int w2 = m_char_current_start;
/*  863:     */     
/*  864:2124 */     int w3 = contentLength;
/*  865:     */     
/*  866:2126 */     int ourslot = appendNode(w0, w1, w2, w3);
/*  867:2127 */     this.previousSibling = ourslot;
/*  868:     */   }
/*  869:     */   
/*  870:     */   void appendStartElement(int namespaceIndex, int localNameIndex, int prefixIndex)
/*  871:     */   {
/*  872:2154 */     int w0 = namespaceIndex << 16 | 0x1;
/*  873:     */     
/*  874:2156 */     int w1 = this.currentParent;
/*  875:     */     
/*  876:2158 */     int w2 = 0;
/*  877:     */     
/*  878:2160 */     int w3 = localNameIndex | prefixIndex << 16;
/*  879:2161 */     System.out.println("set w3=" + w3 + " " + (w3 >> 16) + "/" + (w3 & 0xFFFF));
/*  880:     */     
/*  881:     */ 
/*  882:2164 */     int ourslot = appendNode(w0, w1, w2, w3);
/*  883:2165 */     this.currentParent = ourslot;
/*  884:2166 */     this.previousSibling = 0;
/*  885:2169 */     if (this.m_docElement == -1) {
/*  886:2170 */       this.m_docElement = ourslot;
/*  887:     */     }
/*  888:     */   }
/*  889:     */   
/*  890:     */   void appendNSDeclaration(int prefixIndex, int namespaceIndex, boolean isID)
/*  891:     */   {
/*  892:2197 */     int namespaceForNamespaces = this.m_nsNames.stringToIndex("http://www.w3.org/2000/xmlns/");
/*  893:     */     
/*  894:     */ 
/*  895:2200 */     int w0 = 0xD | this.m_nsNames.stringToIndex("http://www.w3.org/2000/xmlns/") << 16;
/*  896:     */     
/*  897:     */ 
/*  898:2203 */     int w1 = this.currentParent;
/*  899:     */     
/*  900:2205 */     int w2 = 0;
/*  901:     */     
/*  902:2207 */     int w3 = namespaceIndex;
/*  903:     */     
/*  904:2209 */     int ourslot = appendNode(w0, w1, w2, w3);
/*  905:2210 */     this.previousSibling = ourslot;
/*  906:2211 */     this.previousSiblingWasParent = false;
/*  907:     */   }
/*  908:     */   
/*  909:     */   void appendAttribute(int namespaceIndex, int localNameIndex, int prefixIndex, boolean isID, int m_char_current_start, int contentLength)
/*  910:     */   {
/*  911:2237 */     int w0 = 0x2 | namespaceIndex << 16;
/*  912:     */     
/*  913:     */ 
/*  914:2240 */     int w1 = this.currentParent;
/*  915:     */     
/*  916:2242 */     int w2 = 0;
/*  917:     */     
/*  918:2244 */     int w3 = localNameIndex | prefixIndex << 16;
/*  919:2245 */     System.out.println("set w3=" + w3 + " " + (w3 >> 16) + "/" + (w3 & 0xFFFF));
/*  920:     */     
/*  921:2247 */     int ourslot = appendNode(w0, w1, w2, w3);
/*  922:2248 */     this.previousSibling = ourslot;
/*  923:     */     
/*  924:     */ 
/*  925:     */ 
/*  926:     */ 
/*  927:2253 */     w0 = 3;
/*  928:     */     
/*  929:2255 */     w1 = ourslot;
/*  930:     */     
/*  931:2257 */     w2 = m_char_current_start;
/*  932:     */     
/*  933:2259 */     w3 = contentLength;
/*  934:2260 */     appendNode(w0, w1, w2, w3);
/*  935:     */     
/*  936:     */ 
/*  937:2263 */     this.previousSiblingWasParent = true;
/*  938:     */   }
/*  939:     */   
/*  940:     */   public DTMAxisTraverser getAxisTraverser(int axis)
/*  941:     */   {
/*  942:2277 */     return null;
/*  943:     */   }
/*  944:     */   
/*  945:     */   public DTMAxisIterator getAxisIterator(int axis)
/*  946:     */   {
/*  947:2293 */     return null;
/*  948:     */   }
/*  949:     */   
/*  950:     */   public DTMAxisIterator getTypedAxisIterator(int axis, int type)
/*  951:     */   {
/*  952:2309 */     return null;
/*  953:     */   }
/*  954:     */   
/*  955:     */   void appendEndElement()
/*  956:     */   {
/*  957:2320 */     if (this.previousSiblingWasParent) {
/*  958:2321 */       this.nodes.writeEntry(this.previousSibling, 2, -1);
/*  959:     */     }
/*  960:2324 */     this.previousSibling = this.currentParent;
/*  961:2325 */     this.nodes.readSlot(this.currentParent, this.gotslot);
/*  962:2326 */     this.currentParent = (this.gotslot[1] & 0xFFFF);
/*  963:     */     
/*  964:     */ 
/*  965:     */ 
/*  966:2330 */     this.previousSiblingWasParent = true;
/*  967:     */   }
/*  968:     */   
/*  969:     */   void appendStartDocument()
/*  970:     */   {
/*  971:2344 */     this.m_docElement = -1;
/*  972:2345 */     initDocument(0);
/*  973:     */   }
/*  974:     */   
/*  975:     */   void appendEndDocument()
/*  976:     */   {
/*  977:2353 */     this.done = true;
/*  978:     */   }
/*  979:     */   
/*  980:     */   public void setProperty(String property, Object value) {}
/*  981:     */   
/*  982:     */   public SourceLocator getSourceLocatorFor(int node)
/*  983:     */   {
/*  984:2378 */     return null;
/*  985:     */   }
/*  986:     */   
/*  987:     */   public void documentRegistration() {}
/*  988:     */   
/*  989:     */   public void documentRelease() {}
/*  990:     */   
/*  991:     */   public void migrateTo(DTMManager manager) {}
/*  992:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMDocumentImpl
 * JD-Core Version:    0.7.0.1
 */