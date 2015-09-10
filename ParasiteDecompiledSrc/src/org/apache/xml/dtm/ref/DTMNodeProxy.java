/*    1:     */ package org.apache.xml.dtm.ref;
/*    2:     */ 
/*    3:     */ import java.util.Vector;
/*    4:     */ import org.apache.xml.dtm.DTM;
/*    5:     */ import org.apache.xml.dtm.DTMDOMException;
/*    6:     */ import org.apache.xml.utils.XMLString;
/*    7:     */ import org.apache.xpath.NodeSet;
/*    8:     */ import org.w3c.dom.Attr;
/*    9:     */ import org.w3c.dom.CDATASection;
/*   10:     */ import org.w3c.dom.Comment;
/*   11:     */ import org.w3c.dom.DOMConfiguration;
/*   12:     */ import org.w3c.dom.DOMException;
/*   13:     */ import org.w3c.dom.DOMImplementation;
/*   14:     */ import org.w3c.dom.Document;
/*   15:     */ import org.w3c.dom.DocumentFragment;
/*   16:     */ import org.w3c.dom.DocumentType;
/*   17:     */ import org.w3c.dom.Element;
/*   18:     */ import org.w3c.dom.EntityReference;
/*   19:     */ import org.w3c.dom.NamedNodeMap;
/*   20:     */ import org.w3c.dom.Node;
/*   21:     */ import org.w3c.dom.NodeList;
/*   22:     */ import org.w3c.dom.ProcessingInstruction;
/*   23:     */ import org.w3c.dom.Text;
/*   24:     */ import org.w3c.dom.TypeInfo;
/*   25:     */ import org.w3c.dom.UserDataHandler;
/*   26:     */ 
/*   27:     */ public class DTMNodeProxy
/*   28:     */   implements Node, Document, Text, Element, Attr, ProcessingInstruction, Comment, DocumentFragment
/*   29:     */ {
/*   30:     */   public DTM dtm;
/*   31:     */   int node;
/*   32:     */   private static final String EMPTYSTRING = "";
/*   33:  78 */   static final DOMImplementation implementation = new DTMNodeProxyImplementation();
/*   34:     */   protected String fDocumentURI;
/*   35:     */   protected String actualEncoding;
/*   36:     */   private String xmlEncoding;
/*   37:     */   private boolean xmlStandalone;
/*   38:     */   private String xmlVersion;
/*   39:     */   
/*   40:     */   public DTMNodeProxy(DTM dtm, int node)
/*   41:     */   {
/*   42:  88 */     this.dtm = dtm;
/*   43:  89 */     this.node = node;
/*   44:     */   }
/*   45:     */   
/*   46:     */   public final DTM getDTM()
/*   47:     */   {
/*   48:  99 */     return this.dtm;
/*   49:     */   }
/*   50:     */   
/*   51:     */   public final int getDTMNodeNumber()
/*   52:     */   {
/*   53: 109 */     return this.node;
/*   54:     */   }
/*   55:     */   
/*   56:     */   public final boolean equals(Node node)
/*   57:     */   {
/*   58:     */     try
/*   59:     */     {
/*   60: 124 */       DTMNodeProxy dtmp = (DTMNodeProxy)node;
/*   61:     */       
/*   62:     */ 
/*   63:     */ 
/*   64: 128 */       return (dtmp.node == this.node) && (dtmp.dtm == this.dtm);
/*   65:     */     }
/*   66:     */     catch (ClassCastException cce) {}
/*   67: 132 */     return false;
/*   68:     */   }
/*   69:     */   
/*   70:     */   public final boolean equals(Object node)
/*   71:     */   {
/*   72:     */     try
/*   73:     */     {
/*   74: 152 */       return equals((Node)node);
/*   75:     */     }
/*   76:     */     catch (ClassCastException cce) {}
/*   77: 156 */     return false;
/*   78:     */   }
/*   79:     */   
/*   80:     */   public final boolean sameNodeAs(Node other)
/*   81:     */   {
/*   82: 170 */     if (!(other instanceof DTMNodeProxy)) {
/*   83: 171 */       return false;
/*   84:     */     }
/*   85: 173 */     DTMNodeProxy that = (DTMNodeProxy)other;
/*   86:     */     
/*   87: 175 */     return (this.dtm == that.dtm) && (this.node == that.node);
/*   88:     */   }
/*   89:     */   
/*   90:     */   public final String getNodeName()
/*   91:     */   {
/*   92: 185 */     return this.dtm.getNodeName(this.node);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public final String getTarget()
/*   96:     */   {
/*   97: 203 */     return this.dtm.getNodeName(this.node);
/*   98:     */   }
/*   99:     */   
/*  100:     */   public final String getLocalName()
/*  101:     */   {
/*  102: 213 */     return this.dtm.getLocalName(this.node);
/*  103:     */   }
/*  104:     */   
/*  105:     */   public final String getPrefix()
/*  106:     */   {
/*  107: 222 */     return this.dtm.getPrefix(this.node);
/*  108:     */   }
/*  109:     */   
/*  110:     */   public final void setPrefix(String prefix)
/*  111:     */     throws DOMException
/*  112:     */   {
/*  113: 234 */     throw new DTMDOMException((short)7);
/*  114:     */   }
/*  115:     */   
/*  116:     */   public final String getNamespaceURI()
/*  117:     */   {
/*  118: 244 */     return this.dtm.getNamespaceURI(this.node);
/*  119:     */   }
/*  120:     */   
/*  121:     */   public final boolean supports(String feature, String version)
/*  122:     */   {
/*  123: 265 */     return implementation.hasFeature(feature, version);
/*  124:     */   }
/*  125:     */   
/*  126:     */   public final boolean isSupported(String feature, String version)
/*  127:     */   {
/*  128: 281 */     return implementation.hasFeature(feature, version);
/*  129:     */   }
/*  130:     */   
/*  131:     */   public final String getNodeValue()
/*  132:     */     throws DOMException
/*  133:     */   {
/*  134: 294 */     return this.dtm.getNodeValue(this.node);
/*  135:     */   }
/*  136:     */   
/*  137:     */   public final String getStringValue()
/*  138:     */     throws DOMException
/*  139:     */   {
/*  140: 304 */     return this.dtm.getStringValue(this.node).toString();
/*  141:     */   }
/*  142:     */   
/*  143:     */   public final void setNodeValue(String nodeValue)
/*  144:     */     throws DOMException
/*  145:     */   {
/*  146: 316 */     throw new DTMDOMException((short)7);
/*  147:     */   }
/*  148:     */   
/*  149:     */   public final short getNodeType()
/*  150:     */   {
/*  151: 326 */     return this.dtm.getNodeType(this.node);
/*  152:     */   }
/*  153:     */   
/*  154:     */   public final Node getParentNode()
/*  155:     */   {
/*  156: 337 */     if (getNodeType() == 2) {
/*  157: 338 */       return null;
/*  158:     */     }
/*  159: 340 */     int newnode = this.dtm.getParent(this.node);
/*  160:     */     
/*  161: 342 */     return newnode == -1 ? null : this.dtm.getNode(newnode);
/*  162:     */   }
/*  163:     */   
/*  164:     */   public final Node getOwnerNode()
/*  165:     */   {
/*  166: 353 */     int newnode = this.dtm.getParent(this.node);
/*  167:     */     
/*  168: 355 */     return newnode == -1 ? null : this.dtm.getNode(newnode);
/*  169:     */   }
/*  170:     */   
/*  171:     */   public final NodeList getChildNodes()
/*  172:     */   {
/*  173: 369 */     return new DTMChildIterNodeList(this.dtm, this.node);
/*  174:     */   }
/*  175:     */   
/*  176:     */   public final Node getFirstChild()
/*  177:     */   {
/*  178: 382 */     int newnode = this.dtm.getFirstChild(this.node);
/*  179:     */     
/*  180: 384 */     return newnode == -1 ? null : this.dtm.getNode(newnode);
/*  181:     */   }
/*  182:     */   
/*  183:     */   public final Node getLastChild()
/*  184:     */   {
/*  185: 395 */     int newnode = this.dtm.getLastChild(this.node);
/*  186:     */     
/*  187: 397 */     return newnode == -1 ? null : this.dtm.getNode(newnode);
/*  188:     */   }
/*  189:     */   
/*  190:     */   public final Node getPreviousSibling()
/*  191:     */   {
/*  192: 408 */     int newnode = this.dtm.getPreviousSibling(this.node);
/*  193:     */     
/*  194: 410 */     return newnode == -1 ? null : this.dtm.getNode(newnode);
/*  195:     */   }
/*  196:     */   
/*  197:     */   public final Node getNextSibling()
/*  198:     */   {
/*  199: 422 */     if (this.dtm.getNodeType(this.node) == 2) {
/*  200: 423 */       return null;
/*  201:     */     }
/*  202: 425 */     int newnode = this.dtm.getNextSibling(this.node);
/*  203:     */     
/*  204: 427 */     return newnode == -1 ? null : this.dtm.getNode(newnode);
/*  205:     */   }
/*  206:     */   
/*  207:     */   public final NamedNodeMap getAttributes()
/*  208:     */   {
/*  209: 440 */     return new DTMNamedNodeMap(this.dtm, this.node);
/*  210:     */   }
/*  211:     */   
/*  212:     */   public boolean hasAttribute(String name)
/*  213:     */   {
/*  214: 453 */     return -1 != this.dtm.getAttributeNode(this.node, null, name);
/*  215:     */   }
/*  216:     */   
/*  217:     */   public boolean hasAttributeNS(String namespaceURI, String localName)
/*  218:     */   {
/*  219: 467 */     return -1 != this.dtm.getAttributeNode(this.node, namespaceURI, localName);
/*  220:     */   }
/*  221:     */   
/*  222:     */   public final Document getOwnerDocument()
/*  223:     */   {
/*  224: 478 */     return (Document)this.dtm.getNode(this.dtm.getOwnerDocument(this.node));
/*  225:     */   }
/*  226:     */   
/*  227:     */   public final Node insertBefore(Node newChild, Node refChild)
/*  228:     */     throws DOMException
/*  229:     */   {
/*  230: 494 */     throw new DTMDOMException((short)7);
/*  231:     */   }
/*  232:     */   
/*  233:     */   public final Node replaceChild(Node newChild, Node oldChild)
/*  234:     */     throws DOMException
/*  235:     */   {
/*  236: 510 */     throw new DTMDOMException((short)7);
/*  237:     */   }
/*  238:     */   
/*  239:     */   public final Node removeChild(Node oldChild)
/*  240:     */     throws DOMException
/*  241:     */   {
/*  242: 524 */     throw new DTMDOMException((short)7);
/*  243:     */   }
/*  244:     */   
/*  245:     */   public final Node appendChild(Node newChild)
/*  246:     */     throws DOMException
/*  247:     */   {
/*  248: 538 */     throw new DTMDOMException((short)7);
/*  249:     */   }
/*  250:     */   
/*  251:     */   public final boolean hasChildNodes()
/*  252:     */   {
/*  253: 548 */     return -1 != this.dtm.getFirstChild(this.node);
/*  254:     */   }
/*  255:     */   
/*  256:     */   public final Node cloneNode(boolean deep)
/*  257:     */   {
/*  258: 560 */     throw new DTMDOMException((short)9);
/*  259:     */   }
/*  260:     */   
/*  261:     */   public final DocumentType getDoctype()
/*  262:     */   {
/*  263: 570 */     return null;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public final DOMImplementation getImplementation()
/*  267:     */   {
/*  268: 580 */     return implementation;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public final Element getDocumentElement()
/*  272:     */   {
/*  273: 592 */     int dochandle = this.dtm.getDocument();
/*  274: 593 */     int elementhandle = -1;
/*  275: 594 */     for (int kidhandle = this.dtm.getFirstChild(dochandle); kidhandle != -1; kidhandle = this.dtm.getNextSibling(kidhandle)) {
/*  276: 598 */       switch (this.dtm.getNodeType(kidhandle))
/*  277:     */       {
/*  278:     */       case 1: 
/*  279: 601 */         if (elementhandle != -1)
/*  280:     */         {
/*  281: 603 */           elementhandle = -1;
/*  282: 604 */           kidhandle = this.dtm.getLastChild(dochandle);
/*  283:     */         }
/*  284:     */         else
/*  285:     */         {
/*  286: 607 */           elementhandle = kidhandle;
/*  287:     */         }
/*  288: 608 */         break;
/*  289:     */       case 7: 
/*  290:     */       case 8: 
/*  291:     */       case 10: 
/*  292:     */         break;
/*  293:     */       case 2: 
/*  294:     */       case 3: 
/*  295:     */       case 4: 
/*  296:     */       case 5: 
/*  297:     */       case 6: 
/*  298:     */       case 9: 
/*  299:     */       default: 
/*  300: 617 */         elementhandle = -1;
/*  301: 618 */         kidhandle = this.dtm.getLastChild(dochandle);
/*  302:     */       }
/*  303:     */     }
/*  304: 622 */     if (elementhandle == -1) {
/*  305: 623 */       throw new DTMDOMException((short)9);
/*  306:     */     }
/*  307: 625 */     return (Element)this.dtm.getNode(elementhandle);
/*  308:     */   }
/*  309:     */   
/*  310:     */   public final Element createElement(String tagName)
/*  311:     */     throws DOMException
/*  312:     */   {
/*  313: 639 */     throw new DTMDOMException((short)9);
/*  314:     */   }
/*  315:     */   
/*  316:     */   public final DocumentFragment createDocumentFragment()
/*  317:     */   {
/*  318: 649 */     throw new DTMDOMException((short)9);
/*  319:     */   }
/*  320:     */   
/*  321:     */   public final Text createTextNode(String data)
/*  322:     */   {
/*  323: 661 */     throw new DTMDOMException((short)9);
/*  324:     */   }
/*  325:     */   
/*  326:     */   public final Comment createComment(String data)
/*  327:     */   {
/*  328: 673 */     throw new DTMDOMException((short)9);
/*  329:     */   }
/*  330:     */   
/*  331:     */   public final CDATASection createCDATASection(String data)
/*  332:     */     throws DOMException
/*  333:     */   {
/*  334: 688 */     throw new DTMDOMException((short)9);
/*  335:     */   }
/*  336:     */   
/*  337:     */   public final ProcessingInstruction createProcessingInstruction(String target, String data)
/*  338:     */     throws DOMException
/*  339:     */   {
/*  340: 704 */     throw new DTMDOMException((short)9);
/*  341:     */   }
/*  342:     */   
/*  343:     */   public final Attr createAttribute(String name)
/*  344:     */     throws DOMException
/*  345:     */   {
/*  346: 718 */     throw new DTMDOMException((short)9);
/*  347:     */   }
/*  348:     */   
/*  349:     */   public final EntityReference createEntityReference(String name)
/*  350:     */     throws DOMException
/*  351:     */   {
/*  352: 733 */     throw new DTMDOMException((short)9);
/*  353:     */   }
/*  354:     */   
/*  355:     */   public final NodeList getElementsByTagName(String tagname)
/*  356:     */   {
/*  357: 745 */     Vector listVector = new Vector();
/*  358: 746 */     Node retNode = this.dtm.getNode(this.node);
/*  359: 747 */     if (retNode != null)
/*  360:     */     {
/*  361: 749 */       boolean isTagNameWildCard = "*".equals(tagname);
/*  362: 750 */       if (1 == retNode.getNodeType())
/*  363:     */       {
/*  364: 752 */         NodeList nodeList = retNode.getChildNodes();
/*  365: 753 */         for (int i = 0; i < nodeList.getLength(); i++) {
/*  366: 755 */           traverseChildren(listVector, nodeList.item(i), tagname, isTagNameWildCard);
/*  367:     */         }
/*  368:     */       }
/*  369: 758 */       else if (9 == retNode.getNodeType())
/*  370:     */       {
/*  371: 759 */         traverseChildren(listVector, this.dtm.getNode(this.node), tagname, isTagNameWildCard);
/*  372:     */       }
/*  373:     */     }
/*  374: 763 */     int size = listVector.size();
/*  375: 764 */     NodeSet nodeSet = new NodeSet(size);
/*  376: 765 */     for (int i = 0; i < size; i++) {
/*  377: 767 */       nodeSet.addNode((Node)listVector.elementAt(i));
/*  378:     */     }
/*  379: 769 */     return nodeSet;
/*  380:     */   }
/*  381:     */   
/*  382:     */   private final void traverseChildren(Vector listVector, Node tempNode, String tagname, boolean isTagNameWildCard)
/*  383:     */   {
/*  384: 787 */     if (tempNode == null) {
/*  385: 789 */       return;
/*  386:     */     }
/*  387: 793 */     if ((tempNode.getNodeType() == 1) && ((isTagNameWildCard) || (tempNode.getNodeName().equals(tagname)))) {
/*  388: 796 */       listVector.add(tempNode);
/*  389:     */     }
/*  390: 798 */     if (tempNode.hasChildNodes())
/*  391:     */     {
/*  392: 800 */       NodeList nodeList = tempNode.getChildNodes();
/*  393: 801 */       for (int i = 0; i < nodeList.getLength(); i++) {
/*  394: 803 */         traverseChildren(listVector, nodeList.item(i), tagname, isTagNameWildCard);
/*  395:     */       }
/*  396:     */     }
/*  397:     */   }
/*  398:     */   
/*  399:     */   public final Node importNode(Node importedNode, boolean deep)
/*  400:     */     throws DOMException
/*  401:     */   {
/*  402: 823 */     throw new DTMDOMException((short)7);
/*  403:     */   }
/*  404:     */   
/*  405:     */   public final Element createElementNS(String namespaceURI, String qualifiedName)
/*  406:     */     throws DOMException
/*  407:     */   {
/*  408: 839 */     throw new DTMDOMException((short)9);
/*  409:     */   }
/*  410:     */   
/*  411:     */   public final Attr createAttributeNS(String namespaceURI, String qualifiedName)
/*  412:     */     throws DOMException
/*  413:     */   {
/*  414: 855 */     throw new DTMDOMException((short)9);
/*  415:     */   }
/*  416:     */   
/*  417:     */   public final NodeList getElementsByTagNameNS(String namespaceURI, String localName)
/*  418:     */   {
/*  419: 869 */     Vector listVector = new Vector();
/*  420: 870 */     Node retNode = this.dtm.getNode(this.node);
/*  421: 871 */     if (retNode != null)
/*  422:     */     {
/*  423: 873 */       boolean isNamespaceURIWildCard = "*".equals(namespaceURI);
/*  424: 874 */       boolean isLocalNameWildCard = "*".equals(localName);
/*  425: 875 */       if (1 == retNode.getNodeType())
/*  426:     */       {
/*  427: 877 */         NodeList nodeList = retNode.getChildNodes();
/*  428: 878 */         for (int i = 0; i < nodeList.getLength(); i++) {
/*  429: 880 */           traverseChildren(listVector, nodeList.item(i), namespaceURI, localName, isNamespaceURIWildCard, isLocalNameWildCard);
/*  430:     */         }
/*  431:     */       }
/*  432: 883 */       else if (9 == retNode.getNodeType())
/*  433:     */       {
/*  434: 885 */         traverseChildren(listVector, this.dtm.getNode(this.node), namespaceURI, localName, isNamespaceURIWildCard, isLocalNameWildCard);
/*  435:     */       }
/*  436:     */     }
/*  437: 888 */     int size = listVector.size();
/*  438: 889 */     NodeSet nodeSet = new NodeSet(size);
/*  439: 890 */     for (int i = 0; i < size; i++) {
/*  440: 892 */       nodeSet.addNode((Node)listVector.elementAt(i));
/*  441:     */     }
/*  442: 894 */     return nodeSet;
/*  443:     */   }
/*  444:     */   
/*  445:     */   private final void traverseChildren(Vector listVector, Node tempNode, String namespaceURI, String localname, boolean isNamespaceURIWildCard, boolean isLocalNameWildCard)
/*  446:     */   {
/*  447: 917 */     if (tempNode == null) {
/*  448: 919 */       return;
/*  449:     */     }
/*  450: 923 */     if ((tempNode.getNodeType() == 1) && ((isLocalNameWildCard) || (tempNode.getLocalName().equals(localname))))
/*  451:     */     {
/*  452: 927 */       String nsURI = tempNode.getNamespaceURI();
/*  453: 928 */       if (((namespaceURI == null) && (nsURI == null)) || (isNamespaceURIWildCard) || ((namespaceURI != null) && (namespaceURI.equals(nsURI)))) {
/*  454: 932 */         listVector.add(tempNode);
/*  455:     */       }
/*  456:     */     }
/*  457: 935 */     if (tempNode.hasChildNodes())
/*  458:     */     {
/*  459: 937 */       NodeList nl = tempNode.getChildNodes();
/*  460: 938 */       for (int i = 0; i < nl.getLength(); i++) {
/*  461: 940 */         traverseChildren(listVector, nl.item(i), namespaceURI, localname, isNamespaceURIWildCard, isLocalNameWildCard);
/*  462:     */       }
/*  463:     */     }
/*  464:     */   }
/*  465:     */   
/*  466:     */   public final Element getElementById(String elementId)
/*  467:     */   {
/*  468: 955 */     return (Element)this.dtm.getNode(this.dtm.getElementById(elementId));
/*  469:     */   }
/*  470:     */   
/*  471:     */   public final Text splitText(int offset)
/*  472:     */     throws DOMException
/*  473:     */   {
/*  474: 969 */     throw new DTMDOMException((short)9);
/*  475:     */   }
/*  476:     */   
/*  477:     */   public final String getData()
/*  478:     */     throws DOMException
/*  479:     */   {
/*  480: 981 */     return this.dtm.getNodeValue(this.node);
/*  481:     */   }
/*  482:     */   
/*  483:     */   public final void setData(String data)
/*  484:     */     throws DOMException
/*  485:     */   {
/*  486: 993 */     throw new DTMDOMException((short)9);
/*  487:     */   }
/*  488:     */   
/*  489:     */   public final int getLength()
/*  490:     */   {
/*  491:1004 */     return this.dtm.getNodeValue(this.node).length();
/*  492:     */   }
/*  493:     */   
/*  494:     */   public final String substringData(int offset, int count)
/*  495:     */     throws DOMException
/*  496:     */   {
/*  497:1019 */     return getData().substring(offset, offset + count);
/*  498:     */   }
/*  499:     */   
/*  500:     */   public final void appendData(String arg)
/*  501:     */     throws DOMException
/*  502:     */   {
/*  503:1031 */     throw new DTMDOMException((short)9);
/*  504:     */   }
/*  505:     */   
/*  506:     */   public final void insertData(int offset, String arg)
/*  507:     */     throws DOMException
/*  508:     */   {
/*  509:1044 */     throw new DTMDOMException((short)9);
/*  510:     */   }
/*  511:     */   
/*  512:     */   public final void deleteData(int offset, int count)
/*  513:     */     throws DOMException
/*  514:     */   {
/*  515:1057 */     throw new DTMDOMException((short)9);
/*  516:     */   }
/*  517:     */   
/*  518:     */   public final void replaceData(int offset, int count, String arg)
/*  519:     */     throws DOMException
/*  520:     */   {
/*  521:1072 */     throw new DTMDOMException((short)9);
/*  522:     */   }
/*  523:     */   
/*  524:     */   public final String getTagName()
/*  525:     */   {
/*  526:1082 */     return this.dtm.getNodeName(this.node);
/*  527:     */   }
/*  528:     */   
/*  529:     */   public final String getAttribute(String name)
/*  530:     */   {
/*  531:1095 */     DTMNamedNodeMap map = new DTMNamedNodeMap(this.dtm, this.node);
/*  532:1096 */     Node node = map.getNamedItem(name);
/*  533:1097 */     return null == node ? "" : node.getNodeValue();
/*  534:     */   }
/*  535:     */   
/*  536:     */   public final void setAttribute(String name, String value)
/*  537:     */     throws DOMException
/*  538:     */   {
/*  539:1111 */     throw new DTMDOMException((short)9);
/*  540:     */   }
/*  541:     */   
/*  542:     */   public final void removeAttribute(String name)
/*  543:     */     throws DOMException
/*  544:     */   {
/*  545:1123 */     throw new DTMDOMException((short)9);
/*  546:     */   }
/*  547:     */   
/*  548:     */   public final Attr getAttributeNode(String name)
/*  549:     */   {
/*  550:1136 */     DTMNamedNodeMap map = new DTMNamedNodeMap(this.dtm, this.node);
/*  551:1137 */     return (Attr)map.getNamedItem(name);
/*  552:     */   }
/*  553:     */   
/*  554:     */   public final Attr setAttributeNode(Attr newAttr)
/*  555:     */     throws DOMException
/*  556:     */   {
/*  557:1151 */     throw new DTMDOMException((short)9);
/*  558:     */   }
/*  559:     */   
/*  560:     */   public final Attr removeAttributeNode(Attr oldAttr)
/*  561:     */     throws DOMException
/*  562:     */   {
/*  563:1165 */     throw new DTMDOMException((short)9);
/*  564:     */   }
/*  565:     */   
/*  566:     */   public boolean hasAttributes()
/*  567:     */   {
/*  568:1175 */     return -1 != this.dtm.getFirstAttribute(this.node);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public final void normalize()
/*  572:     */   {
/*  573:1181 */     throw new DTMDOMException((short)9);
/*  574:     */   }
/*  575:     */   
/*  576:     */   public final String getAttributeNS(String namespaceURI, String localName)
/*  577:     */   {
/*  578:1194 */     Node retNode = null;
/*  579:1195 */     int n = this.dtm.getAttributeNode(this.node, namespaceURI, localName);
/*  580:1196 */     if (n != -1) {
/*  581:1197 */       retNode = this.dtm.getNode(n);
/*  582:     */     }
/*  583:1198 */     return null == retNode ? "" : retNode.getNodeValue();
/*  584:     */   }
/*  585:     */   
/*  586:     */   public final void setAttributeNS(String namespaceURI, String qualifiedName, String value)
/*  587:     */     throws DOMException
/*  588:     */   {
/*  589:1214 */     throw new DTMDOMException((short)9);
/*  590:     */   }
/*  591:     */   
/*  592:     */   public final void removeAttributeNS(String namespaceURI, String localName)
/*  593:     */     throws DOMException
/*  594:     */   {
/*  595:1228 */     throw new DTMDOMException((short)9);
/*  596:     */   }
/*  597:     */   
/*  598:     */   public final Attr getAttributeNodeNS(String namespaceURI, String localName)
/*  599:     */   {
/*  600:1241 */     Attr retAttr = null;
/*  601:1242 */     int n = this.dtm.getAttributeNode(this.node, namespaceURI, localName);
/*  602:1243 */     if (n != -1) {
/*  603:1244 */       retAttr = (Attr)this.dtm.getNode(n);
/*  604:     */     }
/*  605:1245 */     return retAttr;
/*  606:     */   }
/*  607:     */   
/*  608:     */   public final Attr setAttributeNodeNS(Attr newAttr)
/*  609:     */     throws DOMException
/*  610:     */   {
/*  611:1259 */     throw new DTMDOMException((short)9);
/*  612:     */   }
/*  613:     */   
/*  614:     */   public final String getName()
/*  615:     */   {
/*  616:1269 */     return this.dtm.getNodeName(this.node);
/*  617:     */   }
/*  618:     */   
/*  619:     */   public final boolean getSpecified()
/*  620:     */   {
/*  621:1283 */     return true;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public final String getValue()
/*  625:     */   {
/*  626:1293 */     return this.dtm.getNodeValue(this.node);
/*  627:     */   }
/*  628:     */   
/*  629:     */   public final void setValue(String value)
/*  630:     */   {
/*  631:1303 */     throw new DTMDOMException((short)9);
/*  632:     */   }
/*  633:     */   
/*  634:     */   public final Element getOwnerElement()
/*  635:     */   {
/*  636:1314 */     if (getNodeType() != 2) {
/*  637:1315 */       return null;
/*  638:     */     }
/*  639:1318 */     int newnode = this.dtm.getParent(this.node);
/*  640:1319 */     return newnode == -1 ? null : (Element)this.dtm.getNode(newnode);
/*  641:     */   }
/*  642:     */   
/*  643:     */   public Node adoptNode(Node source)
/*  644:     */     throws DOMException
/*  645:     */   {
/*  646:1335 */     throw new DTMDOMException((short)9);
/*  647:     */   }
/*  648:     */   
/*  649:     */   public String getInputEncoding()
/*  650:     */   {
/*  651:1352 */     throw new DTMDOMException((short)9);
/*  652:     */   }
/*  653:     */   
/*  654:     */   public boolean getStrictErrorChecking()
/*  655:     */   {
/*  656:1373 */     throw new DTMDOMException((short)9);
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void setStrictErrorChecking(boolean strictErrorChecking)
/*  660:     */   {
/*  661:1393 */     throw new DTMDOMException((short)9);
/*  662:     */   }
/*  663:     */   
/*  664:     */   static class DTMNodeProxyImplementation
/*  665:     */     implements DOMImplementation
/*  666:     */   {
/*  667:     */     public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId)
/*  668:     */     {
/*  669:1402 */       throw new DTMDOMException((short)9);
/*  670:     */     }
/*  671:     */     
/*  672:     */     public Document createDocument(String namespaceURI, String qualfiedName, DocumentType doctype)
/*  673:     */     {
/*  674:1407 */       throw new DTMDOMException((short)9);
/*  675:     */     }
/*  676:     */     
/*  677:     */     public boolean hasFeature(String feature, String version)
/*  678:     */     {
/*  679:1420 */       if ((("CORE".equals(feature.toUpperCase())) || ("XML".equals(feature.toUpperCase()))) && (("1.0".equals(version)) || ("2.0".equals(version)))) {
/*  680:1424 */         return true;
/*  681:     */       }
/*  682:1425 */       return false;
/*  683:     */     }
/*  684:     */     
/*  685:     */     public Object getFeature(String feature, String version)
/*  686:     */     {
/*  687:1453 */       return null;
/*  688:     */     }
/*  689:     */   }
/*  690:     */   
/*  691:     */   public Object setUserData(String key, Object data, UserDataHandler handler)
/*  692:     */   {
/*  693:1464 */     return getOwnerDocument().setUserData(key, data, handler);
/*  694:     */   }
/*  695:     */   
/*  696:     */   public Object getUserData(String key)
/*  697:     */   {
/*  698:1477 */     return getOwnerDocument().getUserData(key);
/*  699:     */   }
/*  700:     */   
/*  701:     */   public Object getFeature(String feature, String version)
/*  702:     */   {
/*  703:1503 */     return isSupported(feature, version) ? this : null;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public boolean isEqualNode(Node arg)
/*  707:     */   {
/*  708:1549 */     if (arg == this) {
/*  709:1550 */       return true;
/*  710:     */     }
/*  711:1552 */     if (arg.getNodeType() != getNodeType()) {
/*  712:1553 */       return false;
/*  713:     */     }
/*  714:1557 */     if (getNodeName() == null)
/*  715:     */     {
/*  716:1558 */       if (arg.getNodeName() != null) {
/*  717:1559 */         return false;
/*  718:     */       }
/*  719:     */     }
/*  720:1562 */     else if (!getNodeName().equals(arg.getNodeName())) {
/*  721:1563 */       return false;
/*  722:     */     }
/*  723:1566 */     if (getLocalName() == null)
/*  724:     */     {
/*  725:1567 */       if (arg.getLocalName() != null) {
/*  726:1568 */         return false;
/*  727:     */       }
/*  728:     */     }
/*  729:1571 */     else if (!getLocalName().equals(arg.getLocalName())) {
/*  730:1572 */       return false;
/*  731:     */     }
/*  732:1575 */     if (getNamespaceURI() == null)
/*  733:     */     {
/*  734:1576 */       if (arg.getNamespaceURI() != null) {
/*  735:1577 */         return false;
/*  736:     */       }
/*  737:     */     }
/*  738:1580 */     else if (!getNamespaceURI().equals(arg.getNamespaceURI())) {
/*  739:1581 */       return false;
/*  740:     */     }
/*  741:1584 */     if (getPrefix() == null)
/*  742:     */     {
/*  743:1585 */       if (arg.getPrefix() != null) {
/*  744:1586 */         return false;
/*  745:     */       }
/*  746:     */     }
/*  747:1589 */     else if (!getPrefix().equals(arg.getPrefix())) {
/*  748:1590 */       return false;
/*  749:     */     }
/*  750:1593 */     if (getNodeValue() == null)
/*  751:     */     {
/*  752:1594 */       if (arg.getNodeValue() != null) {
/*  753:1595 */         return false;
/*  754:     */       }
/*  755:     */     }
/*  756:1598 */     else if (!getNodeValue().equals(arg.getNodeValue())) {
/*  757:1599 */       return false;
/*  758:     */     }
/*  759:1611 */     return true;
/*  760:     */   }
/*  761:     */   
/*  762:     */   public String lookupNamespaceURI(String specifiedPrefix)
/*  763:     */   {
/*  764:1624 */     short type = getNodeType();
/*  765:1625 */     switch (type)
/*  766:     */     {
/*  767:     */     case 1: 
/*  768:1628 */       String namespace = getNamespaceURI();
/*  769:1629 */       String prefix = getPrefix();
/*  770:1630 */       if (namespace != null)
/*  771:     */       {
/*  772:1632 */         if ((specifiedPrefix == null) && (prefix == specifiedPrefix)) {
/*  773:1634 */           return namespace;
/*  774:     */         }
/*  775:1635 */         if ((prefix != null) && (prefix.equals(specifiedPrefix))) {
/*  776:1637 */           return namespace;
/*  777:     */         }
/*  778:     */       }
/*  779:1640 */       if (hasAttributes())
/*  780:     */       {
/*  781:1641 */         NamedNodeMap map = getAttributes();
/*  782:1642 */         int length = map.getLength();
/*  783:1643 */         for (int i = 0; i < length; i++)
/*  784:     */         {
/*  785:1644 */           Node attr = map.item(i);
/*  786:1645 */           String attrPrefix = attr.getPrefix();
/*  787:1646 */           String value = attr.getNodeValue();
/*  788:1647 */           namespace = attr.getNamespaceURI();
/*  789:1648 */           if ((namespace != null) && (namespace.equals("http://www.w3.org/2000/xmlns/")))
/*  790:     */           {
/*  791:1650 */             if ((specifiedPrefix == null) && (attr.getNodeName().equals("xmlns"))) {
/*  792:1653 */               return value;
/*  793:     */             }
/*  794:1654 */             if ((attrPrefix != null) && (attrPrefix.equals("xmlns")) && (attr.getLocalName().equals(specifiedPrefix))) {
/*  795:1658 */               return value;
/*  796:     */             }
/*  797:     */           }
/*  798:     */         }
/*  799:     */       }
/*  800:1669 */       return null;
/*  801:     */     case 6: 
/*  802:     */     case 10: 
/*  803:     */     case 11: 
/*  804:     */     case 12: 
/*  805:1681 */       return null;
/*  806:     */     case 2: 
/*  807:1683 */       if (getOwnerElement().getNodeType() == 1) {
/*  808:1684 */         return getOwnerElement().lookupNamespaceURI(specifiedPrefix);
/*  809:     */       }
/*  810:1686 */       return null;
/*  811:     */     }
/*  812:1695 */     return null;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public boolean isDefaultNamespace(String namespaceURI)
/*  816:     */   {
/*  817:1771 */     return false;
/*  818:     */   }
/*  819:     */   
/*  820:     */   public String lookupPrefix(String namespaceURI)
/*  821:     */   {
/*  822:1785 */     if (namespaceURI == null) {
/*  823:1786 */       return null;
/*  824:     */     }
/*  825:1789 */     short type = getNodeType();
/*  826:1791 */     switch (type)
/*  827:     */     {
/*  828:     */     case 6: 
/*  829:     */     case 10: 
/*  830:     */     case 11: 
/*  831:     */     case 12: 
/*  832:1808 */       return null;
/*  833:     */     case 2: 
/*  834:1810 */       if (getOwnerElement().getNodeType() == 1) {
/*  835:1811 */         return getOwnerElement().lookupPrefix(namespaceURI);
/*  836:     */       }
/*  837:1814 */       return null;
/*  838:     */     }
/*  839:1823 */     return null;
/*  840:     */   }
/*  841:     */   
/*  842:     */   public boolean isSameNode(Node other)
/*  843:     */   {
/*  844:1844 */     return this == other;
/*  845:     */   }
/*  846:     */   
/*  847:     */   public void setTextContent(String textContent)
/*  848:     */     throws DOMException
/*  849:     */   {
/*  850:1894 */     setNodeValue(textContent);
/*  851:     */   }
/*  852:     */   
/*  853:     */   public String getTextContent()
/*  854:     */     throws DOMException
/*  855:     */   {
/*  856:1943 */     return getNodeValue();
/*  857:     */   }
/*  858:     */   
/*  859:     */   public short compareDocumentPosition(Node other)
/*  860:     */     throws DOMException
/*  861:     */   {
/*  862:1955 */     return 0;
/*  863:     */   }
/*  864:     */   
/*  865:     */   public String getBaseURI()
/*  866:     */   {
/*  867:1983 */     return null;
/*  868:     */   }
/*  869:     */   
/*  870:     */   public Node renameNode(Node n, String namespaceURI, String name)
/*  871:     */     throws DOMException
/*  872:     */   {
/*  873:1994 */     return n;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public void normalizeDocument() {}
/*  877:     */   
/*  878:     */   public DOMConfiguration getDomConfig()
/*  879:     */   {
/*  880:2011 */     return null;
/*  881:     */   }
/*  882:     */   
/*  883:     */   public void setDocumentURI(String documentURI)
/*  884:     */   {
/*  885:2022 */     this.fDocumentURI = documentURI;
/*  886:     */   }
/*  887:     */   
/*  888:     */   public String getDocumentURI()
/*  889:     */   {
/*  890:2034 */     return this.fDocumentURI;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public String getActualEncoding()
/*  894:     */   {
/*  895:2049 */     return this.actualEncoding;
/*  896:     */   }
/*  897:     */   
/*  898:     */   public void setActualEncoding(String value)
/*  899:     */   {
/*  900:2061 */     this.actualEncoding = value;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public Text replaceWholeText(String content)
/*  904:     */     throws DOMException
/*  905:     */   {
/*  906:2111 */     return null;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public String getWholeText()
/*  910:     */   {
/*  911:2136 */     return null;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public boolean isElementContentWhitespace()
/*  915:     */   {
/*  916:2145 */     return false;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public void setIdAttribute(boolean id) {}
/*  920:     */   
/*  921:     */   public void setIdAttribute(String name, boolean makeId) {}
/*  922:     */   
/*  923:     */   public void setIdAttributeNode(Attr at, boolean makeId) {}
/*  924:     */   
/*  925:     */   public void setIdAttributeNS(String namespaceURI, String localName, boolean makeId) {}
/*  926:     */   
/*  927:     */   public TypeInfo getSchemaTypeInfo()
/*  928:     */   {
/*  929:2181 */     return null;
/*  930:     */   }
/*  931:     */   
/*  932:     */   public boolean isId()
/*  933:     */   {
/*  934:2185 */     return false;
/*  935:     */   }
/*  936:     */   
/*  937:     */   public String getXmlEncoding()
/*  938:     */   {
/*  939:2192 */     return this.xmlEncoding;
/*  940:     */   }
/*  941:     */   
/*  942:     */   public void setXmlEncoding(String xmlEncoding)
/*  943:     */   {
/*  944:2196 */     this.xmlEncoding = xmlEncoding;
/*  945:     */   }
/*  946:     */   
/*  947:     */   public boolean getXmlStandalone()
/*  948:     */   {
/*  949:2202 */     return this.xmlStandalone;
/*  950:     */   }
/*  951:     */   
/*  952:     */   public void setXmlStandalone(boolean xmlStandalone)
/*  953:     */     throws DOMException
/*  954:     */   {
/*  955:2206 */     this.xmlStandalone = xmlStandalone;
/*  956:     */   }
/*  957:     */   
/*  958:     */   public String getXmlVersion()
/*  959:     */   {
/*  960:2212 */     return this.xmlVersion;
/*  961:     */   }
/*  962:     */   
/*  963:     */   public void setXmlVersion(String xmlVersion)
/*  964:     */     throws DOMException
/*  965:     */   {
/*  966:2216 */     this.xmlVersion = xmlVersion;
/*  967:     */   }
/*  968:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMNodeProxy
 * JD-Core Version:    0.7.0.1
 */