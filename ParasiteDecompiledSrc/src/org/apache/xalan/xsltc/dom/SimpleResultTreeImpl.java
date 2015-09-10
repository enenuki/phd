/*    1:     */ package org.apache.xalan.xsltc.dom;
/*    2:     */ 
/*    3:     */ import javax.xml.transform.SourceLocator;
/*    4:     */ import org.apache.xalan.xsltc.DOM;
/*    5:     */ import org.apache.xalan.xsltc.StripFilter;
/*    6:     */ import org.apache.xalan.xsltc.TransletException;
/*    7:     */ import org.apache.xalan.xsltc.runtime.Hashtable;
/*    8:     */ import org.apache.xml.dtm.DTM;
/*    9:     */ import org.apache.xml.dtm.DTMAxisIterator;
/*   10:     */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   11:     */ import org.apache.xml.dtm.DTMManager;
/*   12:     */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*   13:     */ import org.apache.xml.dtm.ref.DTMManagerDefault;
/*   14:     */ import org.apache.xml.serializer.EmptySerializer;
/*   15:     */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   16:     */ import org.apache.xml.serializer.SerializationHandler;
/*   17:     */ import org.apache.xml.utils.XMLString;
/*   18:     */ import org.apache.xml.utils.XMLStringDefault;
/*   19:     */ import org.w3c.dom.Node;
/*   20:     */ import org.w3c.dom.NodeList;
/*   21:     */ import org.xml.sax.ContentHandler;
/*   22:     */ import org.xml.sax.DTDHandler;
/*   23:     */ import org.xml.sax.EntityResolver;
/*   24:     */ import org.xml.sax.ErrorHandler;
/*   25:     */ import org.xml.sax.SAXException;
/*   26:     */ import org.xml.sax.ext.DeclHandler;
/*   27:     */ import org.xml.sax.ext.LexicalHandler;
/*   28:     */ 
/*   29:     */ public class SimpleResultTreeImpl
/*   30:     */   extends EmptySerializer
/*   31:     */   implements DOM, DTM
/*   32:     */ {
/*   33:     */   public final class SimpleIterator
/*   34:     */     extends DTMAxisIteratorBase
/*   35:     */   {
/*   36:     */     static final int DIRECTION_UP = 0;
/*   37:     */     static final int DIRECTION_DOWN = 1;
/*   38:     */     static final int NO_TYPE = -1;
/*   39:  81 */     int _direction = 1;
/*   40:  83 */     int _type = -1;
/*   41:     */     int _currentNode;
/*   42:     */     
/*   43:     */     public SimpleIterator() {}
/*   44:     */     
/*   45:     */     public SimpleIterator(int direction)
/*   46:     */     {
/*   47:  92 */       this._direction = direction;
/*   48:     */     }
/*   49:     */     
/*   50:     */     public SimpleIterator(int direction, int type)
/*   51:     */     {
/*   52:  97 */       this._direction = direction;
/*   53:  98 */       this._type = type;
/*   54:     */     }
/*   55:     */     
/*   56:     */     public int next()
/*   57:     */     {
/*   58: 105 */       if (this._direction == 1)
/*   59:     */       {
/*   60: 106 */         while (this._currentNode < 2) {
/*   61: 107 */           if (this._type != -1)
/*   62:     */           {
/*   63: 108 */             if (((this._currentNode == 0) && (this._type == 0)) || ((this._currentNode == 1) && (this._type == 3))) {
/*   64: 110 */               return returnNode(SimpleResultTreeImpl.this.getNodeHandle(this._currentNode++));
/*   65:     */             }
/*   66: 112 */             this._currentNode += 1;
/*   67:     */           }
/*   68:     */           else
/*   69:     */           {
/*   70: 115 */             return returnNode(SimpleResultTreeImpl.this.getNodeHandle(this._currentNode++));
/*   71:     */           }
/*   72:     */         }
/*   73: 118 */         return -1;
/*   74:     */       }
/*   75: 122 */       while (this._currentNode >= 0) {
/*   76: 123 */         if (this._type != -1)
/*   77:     */         {
/*   78: 124 */           if (((this._currentNode == 0) && (this._type == 0)) || ((this._currentNode == 1) && (this._type == 3))) {
/*   79: 126 */             return returnNode(SimpleResultTreeImpl.this.getNodeHandle(this._currentNode--));
/*   80:     */           }
/*   81: 128 */           this._currentNode -= 1;
/*   82:     */         }
/*   83:     */         else
/*   84:     */         {
/*   85: 131 */           return returnNode(SimpleResultTreeImpl.this.getNodeHandle(this._currentNode--));
/*   86:     */         }
/*   87:     */       }
/*   88: 134 */       return -1;
/*   89:     */     }
/*   90:     */     
/*   91:     */     public DTMAxisIterator setStartNode(int nodeHandle)
/*   92:     */     {
/*   93: 140 */       int nodeID = SimpleResultTreeImpl.this.getNodeIdent(nodeHandle);
/*   94: 141 */       this._startNode = nodeID;
/*   95: 144 */       if ((!this._includeSelf) && (nodeID != -1)) {
/*   96: 145 */         if (this._direction == 1) {
/*   97: 146 */           nodeID++;
/*   98: 147 */         } else if (this._direction == 0) {
/*   99: 148 */           nodeID--;
/*  100:     */         }
/*  101:     */       }
/*  102: 151 */       this._currentNode = nodeID;
/*  103: 152 */       return this;
/*  104:     */     }
/*  105:     */     
/*  106:     */     public void setMark()
/*  107:     */     {
/*  108: 157 */       this._markedNode = this._currentNode;
/*  109:     */     }
/*  110:     */     
/*  111:     */     public void gotoMark()
/*  112:     */     {
/*  113: 162 */       this._currentNode = this._markedNode;
/*  114:     */     }
/*  115:     */   }
/*  116:     */   
/*  117:     */   public final class SingletonIterator
/*  118:     */     extends DTMAxisIteratorBase
/*  119:     */   {
/*  120:     */     static final int NO_TYPE = -1;
/*  121: 173 */     int _type = -1;
/*  122:     */     int _currentNode;
/*  123:     */     
/*  124:     */     public SingletonIterator() {}
/*  125:     */     
/*  126:     */     public SingletonIterator(int type)
/*  127:     */     {
/*  128: 182 */       this._type = type;
/*  129:     */     }
/*  130:     */     
/*  131:     */     public void setMark()
/*  132:     */     {
/*  133: 187 */       this._markedNode = this._currentNode;
/*  134:     */     }
/*  135:     */     
/*  136:     */     public void gotoMark()
/*  137:     */     {
/*  138: 192 */       this._currentNode = this._markedNode;
/*  139:     */     }
/*  140:     */     
/*  141:     */     public DTMAxisIterator setStartNode(int nodeHandle)
/*  142:     */     {
/*  143: 197 */       this._currentNode = (this._startNode = SimpleResultTreeImpl.this.getNodeIdent(nodeHandle));
/*  144: 198 */       return this;
/*  145:     */     }
/*  146:     */     
/*  147:     */     public int next()
/*  148:     */     {
/*  149: 203 */       if (this._currentNode == -1) {
/*  150: 204 */         return -1;
/*  151:     */       }
/*  152: 206 */       this._currentNode = -1;
/*  153: 208 */       if (this._type != -1)
/*  154:     */       {
/*  155: 209 */         if (((this._currentNode == 0) && (this._type == 0)) || ((this._currentNode == 1) && (this._type == 3))) {
/*  156: 211 */           return SimpleResultTreeImpl.this.getNodeHandle(this._currentNode);
/*  157:     */         }
/*  158:     */       }
/*  159:     */       else {
/*  160: 214 */         return SimpleResultTreeImpl.this.getNodeHandle(this._currentNode);
/*  161:     */       }
/*  162: 216 */       return -1;
/*  163:     */     }
/*  164:     */   }
/*  165:     */   
/*  166: 222 */   private static final DTMAxisIterator EMPTY_ITERATOR = new DTMAxisIteratorBase()
/*  167:     */   {
/*  168:     */     public DTMAxisIterator reset()
/*  169:     */     {
/*  170: 224 */       return this;
/*  171:     */     }
/*  172:     */     
/*  173:     */     public DTMAxisIterator setStartNode(int node)
/*  174:     */     {
/*  175: 225 */       return this;
/*  176:     */     }
/*  177:     */     
/*  178:     */     public int next()
/*  179:     */     {
/*  180: 226 */       return -1;
/*  181:     */     }
/*  182:     */     
/*  183:     */     public void setMark() {}
/*  184:     */     
/*  185:     */     public void gotoMark() {}
/*  186:     */     
/*  187:     */     public int getLast()
/*  188:     */     {
/*  189: 229 */       return 0;
/*  190:     */     }
/*  191:     */     
/*  192:     */     public int getPosition()
/*  193:     */     {
/*  194: 230 */       return 0;
/*  195:     */     }
/*  196:     */     
/*  197:     */     public DTMAxisIterator cloneIterator()
/*  198:     */     {
/*  199: 231 */       return this;
/*  200:     */     }
/*  201:     */     
/*  202:     */     public void setRestartable(boolean isRestartable) {}
/*  203:     */   };
/*  204:     */   public static final int RTF_ROOT = 0;
/*  205:     */   public static final int RTF_TEXT = 1;
/*  206:     */   public static final int NUMBER_OF_NODES = 2;
/*  207: 246 */   private static int _documentURIIndex = 0;
/*  208:     */   private static final String EMPTY_STR = "";
/*  209:     */   private String _text;
/*  210:     */   protected String[] _textArray;
/*  211:     */   protected XSLTCDTMManager _dtmManager;
/*  212: 264 */   protected int _size = 0;
/*  213:     */   private int _documentID;
/*  214: 270 */   private BitArray _dontEscape = null;
/*  215: 273 */   private boolean _escaping = true;
/*  216:     */   
/*  217:     */   public SimpleResultTreeImpl(XSLTCDTMManager dtmManager, int documentID)
/*  218:     */   {
/*  219: 278 */     this._dtmManager = dtmManager;
/*  220: 279 */     this._documentID = documentID;
/*  221: 280 */     this._textArray = new String[4];
/*  222:     */   }
/*  223:     */   
/*  224:     */   public DTMManagerDefault getDTMManager()
/*  225:     */   {
/*  226: 285 */     return this._dtmManager;
/*  227:     */   }
/*  228:     */   
/*  229:     */   public int getDocument()
/*  230:     */   {
/*  231: 291 */     return this._documentID;
/*  232:     */   }
/*  233:     */   
/*  234:     */   public String getStringValue()
/*  235:     */   {
/*  236: 297 */     return this._text;
/*  237:     */   }
/*  238:     */   
/*  239:     */   public DTMAxisIterator getIterator()
/*  240:     */   {
/*  241: 302 */     return new SingletonIterator(getDocument());
/*  242:     */   }
/*  243:     */   
/*  244:     */   public DTMAxisIterator getChildren(int node)
/*  245:     */   {
/*  246: 307 */     return new SimpleIterator().setStartNode(node);
/*  247:     */   }
/*  248:     */   
/*  249:     */   public DTMAxisIterator getTypedChildren(int type)
/*  250:     */   {
/*  251: 312 */     return new SimpleIterator(1, type);
/*  252:     */   }
/*  253:     */   
/*  254:     */   public DTMAxisIterator getAxisIterator(int axis)
/*  255:     */   {
/*  256: 319 */     switch (axis)
/*  257:     */     {
/*  258:     */     case 3: 
/*  259:     */     case 4: 
/*  260: 323 */       return new SimpleIterator(1);
/*  261:     */     case 0: 
/*  262:     */     case 10: 
/*  263: 326 */       return new SimpleIterator(0);
/*  264:     */     case 1: 
/*  265: 328 */       return new SimpleIterator(0).includeSelf();
/*  266:     */     case 5: 
/*  267: 330 */       return new SimpleIterator(1).includeSelf();
/*  268:     */     case 13: 
/*  269: 332 */       return new SingletonIterator();
/*  270:     */     }
/*  271: 334 */     return EMPTY_ITERATOR;
/*  272:     */   }
/*  273:     */   
/*  274:     */   public DTMAxisIterator getTypedAxisIterator(int axis, int type)
/*  275:     */   {
/*  276: 340 */     switch (axis)
/*  277:     */     {
/*  278:     */     case 3: 
/*  279:     */     case 4: 
/*  280: 344 */       return new SimpleIterator(1, type);
/*  281:     */     case 0: 
/*  282:     */     case 10: 
/*  283: 347 */       return new SimpleIterator(0, type);
/*  284:     */     case 1: 
/*  285: 349 */       return new SimpleIterator(0, type).includeSelf();
/*  286:     */     case 5: 
/*  287: 351 */       return new SimpleIterator(1, type).includeSelf();
/*  288:     */     case 13: 
/*  289: 353 */       return new SingletonIterator(type);
/*  290:     */     }
/*  291: 355 */     return EMPTY_ITERATOR;
/*  292:     */   }
/*  293:     */   
/*  294:     */   public DTMAxisIterator getNthDescendant(int node, int n, boolean includeself)
/*  295:     */   {
/*  296: 362 */     return null;
/*  297:     */   }
/*  298:     */   
/*  299:     */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns)
/*  300:     */   {
/*  301: 367 */     return null;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iter, int returnType, String value, boolean op)
/*  305:     */   {
/*  306: 374 */     return null;
/*  307:     */   }
/*  308:     */   
/*  309:     */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node)
/*  310:     */   {
/*  311: 379 */     return source;
/*  312:     */   }
/*  313:     */   
/*  314:     */   public String getNodeName(int node)
/*  315:     */   {
/*  316: 384 */     if (getNodeIdent(node) == 1) {
/*  317: 385 */       return "#text";
/*  318:     */     }
/*  319: 387 */     return "";
/*  320:     */   }
/*  321:     */   
/*  322:     */   public String getNodeNameX(int node)
/*  323:     */   {
/*  324: 392 */     return "";
/*  325:     */   }
/*  326:     */   
/*  327:     */   public String getNamespaceName(int node)
/*  328:     */   {
/*  329: 397 */     return "";
/*  330:     */   }
/*  331:     */   
/*  332:     */   public int getExpandedTypeID(int nodeHandle)
/*  333:     */   {
/*  334: 403 */     int nodeID = getNodeIdent(nodeHandle);
/*  335: 404 */     if (nodeID == 1) {
/*  336: 405 */       return 3;
/*  337:     */     }
/*  338: 406 */     if (nodeID == 0) {
/*  339: 407 */       return 0;
/*  340:     */     }
/*  341: 409 */     return -1;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public int getNamespaceType(int node)
/*  345:     */   {
/*  346: 414 */     return 0;
/*  347:     */   }
/*  348:     */   
/*  349:     */   public int getParent(int nodeHandle)
/*  350:     */   {
/*  351: 419 */     int nodeID = getNodeIdent(nodeHandle);
/*  352: 420 */     return nodeID == 1 ? getNodeHandle(0) : -1;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public int getAttributeNode(int gType, int element)
/*  356:     */   {
/*  357: 425 */     return -1;
/*  358:     */   }
/*  359:     */   
/*  360:     */   public String getStringValueX(int nodeHandle)
/*  361:     */   {
/*  362: 430 */     int nodeID = getNodeIdent(nodeHandle);
/*  363: 431 */     if ((nodeID == 0) || (nodeID == 1)) {
/*  364: 432 */       return this._text;
/*  365:     */     }
/*  366: 434 */     return "";
/*  367:     */   }
/*  368:     */   
/*  369:     */   public void copy(int node, SerializationHandler handler)
/*  370:     */     throws TransletException
/*  371:     */   {
/*  372: 440 */     characters(node, handler);
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void copy(DTMAxisIterator nodes, SerializationHandler handler)
/*  376:     */     throws TransletException
/*  377:     */   {
/*  378:     */     int node;
/*  379: 447 */     while ((node = nodes.next()) != -1)
/*  380:     */     {
/*  381:     */       int i;
/*  382: 449 */       copy(i, handler);
/*  383:     */     }
/*  384:     */   }
/*  385:     */   
/*  386:     */   public String shallowCopy(int node, SerializationHandler handler)
/*  387:     */     throws TransletException
/*  388:     */   {
/*  389: 456 */     characters(node, handler);
/*  390: 457 */     return null;
/*  391:     */   }
/*  392:     */   
/*  393:     */   public boolean lessThan(int node1, int node2)
/*  394:     */   {
/*  395: 462 */     if (node1 == -1) {
/*  396: 463 */       return false;
/*  397:     */     }
/*  398: 465 */     if (node2 == -1) {
/*  399: 466 */       return true;
/*  400:     */     }
/*  401: 469 */     return node1 < node2;
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void characters(int node, SerializationHandler handler)
/*  405:     */     throws TransletException
/*  406:     */   {
/*  407: 481 */     int nodeID = getNodeIdent(node);
/*  408: 482 */     if ((nodeID == 0) || (nodeID == 1))
/*  409:     */     {
/*  410: 483 */       boolean escapeBit = false;
/*  411: 484 */       boolean oldEscapeSetting = false;
/*  412:     */       try
/*  413:     */       {
/*  414: 487 */         for (int i = 0; i < this._size; i++)
/*  415:     */         {
/*  416: 489 */           if (this._dontEscape != null)
/*  417:     */           {
/*  418: 490 */             escapeBit = this._dontEscape.getBit(i);
/*  419: 491 */             if (escapeBit) {
/*  420: 492 */               oldEscapeSetting = handler.setEscaping(false);
/*  421:     */             }
/*  422:     */           }
/*  423: 496 */           handler.characters(this._textArray[i]);
/*  424: 498 */           if (escapeBit) {
/*  425: 499 */             handler.setEscaping(oldEscapeSetting);
/*  426:     */           }
/*  427:     */         }
/*  428:     */       }
/*  429:     */       catch (SAXException e)
/*  430:     */       {
/*  431: 503 */         throw new TransletException(e);
/*  432:     */       }
/*  433:     */     }
/*  434:     */   }
/*  435:     */   
/*  436:     */   public Node makeNode(int index)
/*  437:     */   {
/*  438: 511 */     return null;
/*  439:     */   }
/*  440:     */   
/*  441:     */   public Node makeNode(DTMAxisIterator iter)
/*  442:     */   {
/*  443: 516 */     return null;
/*  444:     */   }
/*  445:     */   
/*  446:     */   public NodeList makeNodeList(int index)
/*  447:     */   {
/*  448: 521 */     return null;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public NodeList makeNodeList(DTMAxisIterator iter)
/*  452:     */   {
/*  453: 526 */     return null;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public String getLanguage(int node)
/*  457:     */   {
/*  458: 531 */     return null;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public int getSize()
/*  462:     */   {
/*  463: 536 */     return 2;
/*  464:     */   }
/*  465:     */   
/*  466:     */   public String getDocumentURI(int node)
/*  467:     */   {
/*  468: 541 */     return "simple_rtf" + _documentURIIndex++;
/*  469:     */   }
/*  470:     */   
/*  471:     */   public void setFilter(StripFilter filter) {}
/*  472:     */   
/*  473:     */   public void setupMapping(String[] names, String[] uris, int[] types, String[] namespaces) {}
/*  474:     */   
/*  475:     */   public boolean isElement(int node)
/*  476:     */   {
/*  477: 554 */     return false;
/*  478:     */   }
/*  479:     */   
/*  480:     */   public boolean isAttribute(int node)
/*  481:     */   {
/*  482: 559 */     return false;
/*  483:     */   }
/*  484:     */   
/*  485:     */   public String lookupNamespace(int node, String prefix)
/*  486:     */     throws TransletException
/*  487:     */   {
/*  488: 565 */     return null;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public int getNodeIdent(int nodehandle)
/*  492:     */   {
/*  493: 573 */     return nodehandle != -1 ? nodehandle - this._documentID : -1;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public int getNodeHandle(int nodeId)
/*  497:     */   {
/*  498: 581 */     return nodeId != -1 ? nodeId + this._documentID : -1;
/*  499:     */   }
/*  500:     */   
/*  501:     */   public DOM getResultTreeFrag(int initialSize, int rtfType)
/*  502:     */   {
/*  503: 586 */     return null;
/*  504:     */   }
/*  505:     */   
/*  506:     */   public DOM getResultTreeFrag(int initialSize, int rtfType, boolean addToManager)
/*  507:     */   {
/*  508: 591 */     return null;
/*  509:     */   }
/*  510:     */   
/*  511:     */   public SerializationHandler getOutputDomBuilder()
/*  512:     */   {
/*  513: 596 */     return this;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public int getNSType(int node)
/*  517:     */   {
/*  518: 601 */     return 0;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public String getUnparsedEntityURI(String name)
/*  522:     */   {
/*  523: 606 */     return null;
/*  524:     */   }
/*  525:     */   
/*  526:     */   public Hashtable getElementsWithIDs()
/*  527:     */   {
/*  528: 611 */     return null;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public void startDocument()
/*  532:     */     throws SAXException
/*  533:     */   {}
/*  534:     */   
/*  535:     */   public void endDocument()
/*  536:     */     throws SAXException
/*  537:     */   {
/*  538: 630 */     if (this._size == 1)
/*  539:     */     {
/*  540: 631 */       this._text = this._textArray[0];
/*  541:     */     }
/*  542:     */     else
/*  543:     */     {
/*  544: 633 */       StringBuffer buffer = new StringBuffer();
/*  545: 634 */       for (int i = 0; i < this._size; i++) {
/*  546: 635 */         buffer.append(this._textArray[i]);
/*  547:     */       }
/*  548: 637 */       this._text = buffer.toString();
/*  549:     */     }
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void characters(String str)
/*  553:     */     throws SAXException
/*  554:     */   {
/*  555: 644 */     if (this._size >= this._textArray.length)
/*  556:     */     {
/*  557: 645 */       String[] newTextArray = new String[this._textArray.length * 2];
/*  558: 646 */       System.arraycopy(this._textArray, 0, newTextArray, 0, this._textArray.length);
/*  559: 647 */       this._textArray = newTextArray;
/*  560:     */     }
/*  561: 652 */     if (!this._escaping)
/*  562:     */     {
/*  563: 654 */       if (this._dontEscape == null) {
/*  564: 655 */         this._dontEscape = new BitArray(8);
/*  565:     */       }
/*  566: 659 */       if (this._size >= this._dontEscape.size()) {
/*  567: 660 */         this._dontEscape.resize(this._dontEscape.size() * 2);
/*  568:     */       }
/*  569: 662 */       this._dontEscape.setBit(this._size);
/*  570:     */     }
/*  571: 665 */     this._textArray[(this._size++)] = str;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public void characters(char[] ch, int offset, int length)
/*  575:     */     throws SAXException
/*  576:     */   {
/*  577: 671 */     if (this._size >= this._textArray.length)
/*  578:     */     {
/*  579: 672 */       String[] newTextArray = new String[this._textArray.length * 2];
/*  580: 673 */       System.arraycopy(this._textArray, 0, newTextArray, 0, this._textArray.length);
/*  581: 674 */       this._textArray = newTextArray;
/*  582:     */     }
/*  583: 677 */     if (!this._escaping)
/*  584:     */     {
/*  585: 678 */       if (this._dontEscape == null) {
/*  586: 679 */         this._dontEscape = new BitArray(8);
/*  587:     */       }
/*  588: 682 */       if (this._size >= this._dontEscape.size()) {
/*  589: 683 */         this._dontEscape.resize(this._dontEscape.size() * 2);
/*  590:     */       }
/*  591: 685 */       this._dontEscape.setBit(this._size);
/*  592:     */     }
/*  593: 688 */     this._textArray[(this._size++)] = new String(ch, offset, length);
/*  594:     */   }
/*  595:     */   
/*  596:     */   public boolean setEscaping(boolean escape)
/*  597:     */     throws SAXException
/*  598:     */   {
/*  599: 694 */     boolean temp = this._escaping;
/*  600: 695 */     this._escaping = escape;
/*  601: 696 */     return temp;
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void setFeature(String featureId, boolean state) {}
/*  605:     */   
/*  606:     */   public void setProperty(String property, Object value) {}
/*  607:     */   
/*  608:     */   public DTMAxisTraverser getAxisTraverser(int axis)
/*  609:     */   {
/*  610: 720 */     return null;
/*  611:     */   }
/*  612:     */   
/*  613:     */   public boolean hasChildNodes(int nodeHandle)
/*  614:     */   {
/*  615: 725 */     return getNodeIdent(nodeHandle) == 0;
/*  616:     */   }
/*  617:     */   
/*  618:     */   public int getFirstChild(int nodeHandle)
/*  619:     */   {
/*  620: 730 */     int nodeID = getNodeIdent(nodeHandle);
/*  621: 731 */     if (nodeID == 0) {
/*  622: 732 */       return getNodeHandle(1);
/*  623:     */     }
/*  624: 734 */     return -1;
/*  625:     */   }
/*  626:     */   
/*  627:     */   public int getLastChild(int nodeHandle)
/*  628:     */   {
/*  629: 739 */     return getFirstChild(nodeHandle);
/*  630:     */   }
/*  631:     */   
/*  632:     */   public int getAttributeNode(int elementHandle, String namespaceURI, String name)
/*  633:     */   {
/*  634: 744 */     return -1;
/*  635:     */   }
/*  636:     */   
/*  637:     */   public int getFirstAttribute(int nodeHandle)
/*  638:     */   {
/*  639: 749 */     return -1;
/*  640:     */   }
/*  641:     */   
/*  642:     */   public int getFirstNamespaceNode(int nodeHandle, boolean inScope)
/*  643:     */   {
/*  644: 754 */     return -1;
/*  645:     */   }
/*  646:     */   
/*  647:     */   public int getNextSibling(int nodeHandle)
/*  648:     */   {
/*  649: 759 */     return -1;
/*  650:     */   }
/*  651:     */   
/*  652:     */   public int getPreviousSibling(int nodeHandle)
/*  653:     */   {
/*  654: 764 */     return -1;
/*  655:     */   }
/*  656:     */   
/*  657:     */   public int getNextAttribute(int nodeHandle)
/*  658:     */   {
/*  659: 769 */     return -1;
/*  660:     */   }
/*  661:     */   
/*  662:     */   public int getNextNamespaceNode(int baseHandle, int namespaceHandle, boolean inScope)
/*  663:     */   {
/*  664: 775 */     return -1;
/*  665:     */   }
/*  666:     */   
/*  667:     */   public int getOwnerDocument(int nodeHandle)
/*  668:     */   {
/*  669: 780 */     return getDocument();
/*  670:     */   }
/*  671:     */   
/*  672:     */   public int getDocumentRoot(int nodeHandle)
/*  673:     */   {
/*  674: 785 */     return getDocument();
/*  675:     */   }
/*  676:     */   
/*  677:     */   public XMLString getStringValue(int nodeHandle)
/*  678:     */   {
/*  679: 790 */     return new XMLStringDefault(getStringValueX(nodeHandle));
/*  680:     */   }
/*  681:     */   
/*  682:     */   public int getStringValueChunkCount(int nodeHandle)
/*  683:     */   {
/*  684: 795 */     return 0;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public char[] getStringValueChunk(int nodeHandle, int chunkIndex, int[] startAndLen)
/*  688:     */   {
/*  689: 801 */     return null;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public int getExpandedTypeID(String namespace, String localName, int type)
/*  693:     */   {
/*  694: 806 */     return -1;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public String getLocalNameFromExpandedNameID(int ExpandedNameID)
/*  698:     */   {
/*  699: 811 */     return "";
/*  700:     */   }
/*  701:     */   
/*  702:     */   public String getNamespaceFromExpandedNameID(int ExpandedNameID)
/*  703:     */   {
/*  704: 816 */     return "";
/*  705:     */   }
/*  706:     */   
/*  707:     */   public String getLocalName(int nodeHandle)
/*  708:     */   {
/*  709: 821 */     return "";
/*  710:     */   }
/*  711:     */   
/*  712:     */   public String getPrefix(int nodeHandle)
/*  713:     */   {
/*  714: 826 */     return null;
/*  715:     */   }
/*  716:     */   
/*  717:     */   public String getNamespaceURI(int nodeHandle)
/*  718:     */   {
/*  719: 831 */     return "";
/*  720:     */   }
/*  721:     */   
/*  722:     */   public String getNodeValue(int nodeHandle)
/*  723:     */   {
/*  724: 836 */     return getNodeIdent(nodeHandle) == 1 ? this._text : null;
/*  725:     */   }
/*  726:     */   
/*  727:     */   public short getNodeType(int nodeHandle)
/*  728:     */   {
/*  729: 841 */     int nodeID = getNodeIdent(nodeHandle);
/*  730: 842 */     if (nodeID == 1) {
/*  731: 843 */       return 3;
/*  732:     */     }
/*  733: 844 */     if (nodeID == 0) {
/*  734: 845 */       return 0;
/*  735:     */     }
/*  736: 847 */     return -1;
/*  737:     */   }
/*  738:     */   
/*  739:     */   public short getLevel(int nodeHandle)
/*  740:     */   {
/*  741: 853 */     int nodeID = getNodeIdent(nodeHandle);
/*  742: 854 */     if (nodeID == 1) {
/*  743: 855 */       return 2;
/*  744:     */     }
/*  745: 856 */     if (nodeID == 0) {
/*  746: 857 */       return 1;
/*  747:     */     }
/*  748: 859 */     return -1;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public boolean isSupported(String feature, String version)
/*  752:     */   {
/*  753: 864 */     return false;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public String getDocumentBaseURI()
/*  757:     */   {
/*  758: 869 */     return "";
/*  759:     */   }
/*  760:     */   
/*  761:     */   public void setDocumentBaseURI(String baseURI) {}
/*  762:     */   
/*  763:     */   public String getDocumentSystemIdentifier(int nodeHandle)
/*  764:     */   {
/*  765: 878 */     return null;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public String getDocumentEncoding(int nodeHandle)
/*  769:     */   {
/*  770: 883 */     return null;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public String getDocumentStandalone(int nodeHandle)
/*  774:     */   {
/*  775: 888 */     return null;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public String getDocumentVersion(int documentHandle)
/*  779:     */   {
/*  780: 893 */     return null;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public boolean getDocumentAllDeclarationsProcessed()
/*  784:     */   {
/*  785: 898 */     return false;
/*  786:     */   }
/*  787:     */   
/*  788:     */   public String getDocumentTypeDeclarationSystemIdentifier()
/*  789:     */   {
/*  790: 903 */     return null;
/*  791:     */   }
/*  792:     */   
/*  793:     */   public String getDocumentTypeDeclarationPublicIdentifier()
/*  794:     */   {
/*  795: 908 */     return null;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public int getElementById(String elementId)
/*  799:     */   {
/*  800: 913 */     return -1;
/*  801:     */   }
/*  802:     */   
/*  803:     */   public boolean supportsPreStripping()
/*  804:     */   {
/*  805: 918 */     return false;
/*  806:     */   }
/*  807:     */   
/*  808:     */   public boolean isNodeAfter(int firstNodeHandle, int secondNodeHandle)
/*  809:     */   {
/*  810: 923 */     return lessThan(firstNodeHandle, secondNodeHandle);
/*  811:     */   }
/*  812:     */   
/*  813:     */   public boolean isCharacterElementContentWhitespace(int nodeHandle)
/*  814:     */   {
/*  815: 928 */     return false;
/*  816:     */   }
/*  817:     */   
/*  818:     */   public boolean isDocumentAllDeclarationsProcessed(int documentHandle)
/*  819:     */   {
/*  820: 933 */     return false;
/*  821:     */   }
/*  822:     */   
/*  823:     */   public boolean isAttributeSpecified(int attributeHandle)
/*  824:     */   {
/*  825: 938 */     return false;
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize)
/*  829:     */     throws SAXException
/*  830:     */   {}
/*  831:     */   
/*  832:     */   public void dispatchToEvents(int nodeHandle, ContentHandler ch)
/*  833:     */     throws SAXException
/*  834:     */   {}
/*  835:     */   
/*  836:     */   public Node getNode(int nodeHandle)
/*  837:     */   {
/*  838: 956 */     return makeNode(nodeHandle);
/*  839:     */   }
/*  840:     */   
/*  841:     */   public boolean needsTwoThreads()
/*  842:     */   {
/*  843: 961 */     return false;
/*  844:     */   }
/*  845:     */   
/*  846:     */   public ContentHandler getContentHandler()
/*  847:     */   {
/*  848: 966 */     return null;
/*  849:     */   }
/*  850:     */   
/*  851:     */   public LexicalHandler getLexicalHandler()
/*  852:     */   {
/*  853: 971 */     return null;
/*  854:     */   }
/*  855:     */   
/*  856:     */   public EntityResolver getEntityResolver()
/*  857:     */   {
/*  858: 976 */     return null;
/*  859:     */   }
/*  860:     */   
/*  861:     */   public DTDHandler getDTDHandler()
/*  862:     */   {
/*  863: 981 */     return null;
/*  864:     */   }
/*  865:     */   
/*  866:     */   public ErrorHandler getErrorHandler()
/*  867:     */   {
/*  868: 986 */     return null;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public DeclHandler getDeclHandler()
/*  872:     */   {
/*  873: 991 */     return null;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public void appendChild(int newChild, boolean clone, boolean cloneDepth) {}
/*  877:     */   
/*  878:     */   public void appendTextChild(String str) {}
/*  879:     */   
/*  880:     */   public SourceLocator getSourceLocatorFor(int node)
/*  881:     */   {
/*  882:1004 */     return null;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public void documentRegistration() {}
/*  886:     */   
/*  887:     */   public void documentRelease() {}
/*  888:     */   
/*  889:     */   public void migrateTo(DTMManager manager) {}
/*  890:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.SimpleResultTreeImpl
 * JD-Core Version:    0.7.0.1
 */