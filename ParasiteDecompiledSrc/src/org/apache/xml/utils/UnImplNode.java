/*    1:     */ package org.apache.xml.utils;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import org.apache.xml.res.XMLMessages;
/*    5:     */ import org.w3c.dom.Attr;
/*    6:     */ import org.w3c.dom.CDATASection;
/*    7:     */ import org.w3c.dom.Comment;
/*    8:     */ import org.w3c.dom.DOMConfiguration;
/*    9:     */ import org.w3c.dom.DOMException;
/*   10:     */ import org.w3c.dom.DOMImplementation;
/*   11:     */ import org.w3c.dom.Document;
/*   12:     */ import org.w3c.dom.DocumentFragment;
/*   13:     */ import org.w3c.dom.DocumentType;
/*   14:     */ import org.w3c.dom.Element;
/*   15:     */ import org.w3c.dom.EntityReference;
/*   16:     */ import org.w3c.dom.NamedNodeMap;
/*   17:     */ import org.w3c.dom.Node;
/*   18:     */ import org.w3c.dom.NodeList;
/*   19:     */ import org.w3c.dom.ProcessingInstruction;
/*   20:     */ import org.w3c.dom.Text;
/*   21:     */ import org.w3c.dom.TypeInfo;
/*   22:     */ import org.w3c.dom.UserDataHandler;
/*   23:     */ 
/*   24:     */ public class UnImplNode
/*   25:     */   implements Node, Element, NodeList, Document
/*   26:     */ {
/*   27:     */   protected String fDocumentURI;
/*   28:     */   protected String actualEncoding;
/*   29:     */   private String xmlEncoding;
/*   30:     */   private boolean xmlStandalone;
/*   31:     */   private String xmlVersion;
/*   32:     */   
/*   33:     */   public void error(String msg)
/*   34:     */   {
/*   35:  66 */     System.out.println("DOM ERROR! class: " + getClass().getName());
/*   36:     */     
/*   37:  68 */     throw new RuntimeException(XMLMessages.createXMLMessage(msg, null));
/*   38:     */   }
/*   39:     */   
/*   40:     */   public void error(String msg, Object[] args)
/*   41:     */   {
/*   42:  80 */     System.out.println("DOM ERROR! class: " + getClass().getName());
/*   43:     */     
/*   44:  82 */     throw new RuntimeException(XMLMessages.createXMLMessage(msg, args));
/*   45:     */   }
/*   46:     */   
/*   47:     */   public Node appendChild(Node newChild)
/*   48:     */     throws DOMException
/*   49:     */   {
/*   50:  97 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*   51:     */     
/*   52:  99 */     return null;
/*   53:     */   }
/*   54:     */   
/*   55:     */   public boolean hasChildNodes()
/*   56:     */   {
/*   57: 110 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*   58:     */     
/*   59: 112 */     return false;
/*   60:     */   }
/*   61:     */   
/*   62:     */   public short getNodeType()
/*   63:     */   {
/*   64: 123 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*   65:     */     
/*   66: 125 */     return 0;
/*   67:     */   }
/*   68:     */   
/*   69:     */   public Node getParentNode()
/*   70:     */   {
/*   71: 136 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*   72:     */     
/*   73: 138 */     return null;
/*   74:     */   }
/*   75:     */   
/*   76:     */   public NodeList getChildNodes()
/*   77:     */   {
/*   78: 149 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*   79:     */     
/*   80: 151 */     return null;
/*   81:     */   }
/*   82:     */   
/*   83:     */   public Node getFirstChild()
/*   84:     */   {
/*   85: 162 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*   86:     */     
/*   87: 164 */     return null;
/*   88:     */   }
/*   89:     */   
/*   90:     */   public Node getLastChild()
/*   91:     */   {
/*   92: 175 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*   93:     */     
/*   94: 177 */     return null;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public Node getNextSibling()
/*   98:     */   {
/*   99: 188 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  100:     */     
/*  101: 190 */     return null;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public int getLength()
/*  105:     */   {
/*  106: 201 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  107:     */     
/*  108: 203 */     return 0;
/*  109:     */   }
/*  110:     */   
/*  111:     */   public Node item(int index)
/*  112:     */   {
/*  113: 216 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  114:     */     
/*  115: 218 */     return null;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public Document getOwnerDocument()
/*  119:     */   {
/*  120: 229 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  121:     */     
/*  122: 231 */     return null;
/*  123:     */   }
/*  124:     */   
/*  125:     */   public String getTagName()
/*  126:     */   {
/*  127: 242 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  128:     */     
/*  129: 244 */     return null;
/*  130:     */   }
/*  131:     */   
/*  132:     */   public String getNodeName()
/*  133:     */   {
/*  134: 255 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  135:     */     
/*  136: 257 */     return null;
/*  137:     */   }
/*  138:     */   
/*  139:     */   public void normalize()
/*  140:     */   {
/*  141: 263 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  142:     */   }
/*  143:     */   
/*  144:     */   public NodeList getElementsByTagName(String name)
/*  145:     */   {
/*  146: 276 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  147:     */     
/*  148: 278 */     return null;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public Attr removeAttributeNode(Attr oldAttr)
/*  152:     */     throws DOMException
/*  153:     */   {
/*  154: 293 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  155:     */     
/*  156: 295 */     return null;
/*  157:     */   }
/*  158:     */   
/*  159:     */   public Attr setAttributeNode(Attr newAttr)
/*  160:     */     throws DOMException
/*  161:     */   {
/*  162: 310 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  163:     */     
/*  164: 312 */     return null;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public boolean hasAttribute(String name)
/*  168:     */   {
/*  169: 326 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  170:     */     
/*  171: 328 */     return false;
/*  172:     */   }
/*  173:     */   
/*  174:     */   public boolean hasAttributeNS(String name, String x)
/*  175:     */   {
/*  176: 343 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  177:     */     
/*  178: 345 */     return false;
/*  179:     */   }
/*  180:     */   
/*  181:     */   public Attr getAttributeNode(String name)
/*  182:     */   {
/*  183: 359 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  184:     */     
/*  185: 361 */     return null;
/*  186:     */   }
/*  187:     */   
/*  188:     */   public void removeAttribute(String name)
/*  189:     */     throws DOMException
/*  190:     */   {
/*  191: 373 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void setAttribute(String name, String value)
/*  195:     */     throws DOMException
/*  196:     */   {
/*  197: 386 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  198:     */   }
/*  199:     */   
/*  200:     */   public String getAttribute(String name)
/*  201:     */   {
/*  202: 399 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  203:     */     
/*  204: 401 */     return null;
/*  205:     */   }
/*  206:     */   
/*  207:     */   public boolean hasAttributes()
/*  208:     */   {
/*  209: 412 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  210:     */     
/*  211: 414 */     return false;
/*  212:     */   }
/*  213:     */   
/*  214:     */   public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
/*  215:     */   {
/*  216: 429 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  217:     */     
/*  218: 431 */     return null;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public Attr setAttributeNodeNS(Attr newAttr)
/*  222:     */     throws DOMException
/*  223:     */   {
/*  224: 446 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  225:     */     
/*  226: 448 */     return null;
/*  227:     */   }
/*  228:     */   
/*  229:     */   public Attr getAttributeNodeNS(String namespaceURI, String localName)
/*  230:     */   {
/*  231: 462 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  232:     */     
/*  233: 464 */     return null;
/*  234:     */   }
/*  235:     */   
/*  236:     */   public void removeAttributeNS(String namespaceURI, String localName)
/*  237:     */     throws DOMException
/*  238:     */   {
/*  239: 478 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  240:     */   }
/*  241:     */   
/*  242:     */   public void setAttributeNS(String namespaceURI, String qualifiedName, String value)
/*  243:     */     throws DOMException
/*  244:     */   {
/*  245: 494 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  246:     */   }
/*  247:     */   
/*  248:     */   public String getAttributeNS(String namespaceURI, String localName)
/*  249:     */   {
/*  250: 508 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  251:     */     
/*  252: 510 */     return null;
/*  253:     */   }
/*  254:     */   
/*  255:     */   public Node getPreviousSibling()
/*  256:     */   {
/*  257: 521 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  258:     */     
/*  259: 523 */     return null;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public Node cloneNode(boolean deep)
/*  263:     */   {
/*  264: 536 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  265:     */     
/*  266: 538 */     return null;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public String getNodeValue()
/*  270:     */     throws DOMException
/*  271:     */   {
/*  272: 551 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  273:     */     
/*  274: 553 */     return null;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public void setNodeValue(String nodeValue)
/*  278:     */     throws DOMException
/*  279:     */   {
/*  280: 565 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  281:     */   }
/*  282:     */   
/*  283:     */   public void setValue(String value)
/*  284:     */     throws DOMException
/*  285:     */   {
/*  286: 593 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  287:     */   }
/*  288:     */   
/*  289:     */   public Element getOwnerElement()
/*  290:     */   {
/*  291: 615 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  292:     */     
/*  293: 617 */     return null;
/*  294:     */   }
/*  295:     */   
/*  296:     */   public boolean getSpecified()
/*  297:     */   {
/*  298: 628 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  299:     */     
/*  300: 630 */     return false;
/*  301:     */   }
/*  302:     */   
/*  303:     */   public NamedNodeMap getAttributes()
/*  304:     */   {
/*  305: 641 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  306:     */     
/*  307: 643 */     return null;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public Node insertBefore(Node newChild, Node refChild)
/*  311:     */     throws DOMException
/*  312:     */   {
/*  313: 659 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  314:     */     
/*  315: 661 */     return null;
/*  316:     */   }
/*  317:     */   
/*  318:     */   public Node replaceChild(Node newChild, Node oldChild)
/*  319:     */     throws DOMException
/*  320:     */   {
/*  321: 677 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  322:     */     
/*  323: 679 */     return null;
/*  324:     */   }
/*  325:     */   
/*  326:     */   public Node removeChild(Node oldChild)
/*  327:     */     throws DOMException
/*  328:     */   {
/*  329: 694 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  330:     */     
/*  331: 696 */     return null;
/*  332:     */   }
/*  333:     */   
/*  334:     */   public boolean isSupported(String feature, String version)
/*  335:     */   {
/*  336: 715 */     return false;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public String getNamespaceURI()
/*  340:     */   {
/*  341: 726 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  342:     */     
/*  343: 728 */     return null;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public String getPrefix()
/*  347:     */   {
/*  348: 739 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  349:     */     
/*  350: 741 */     return null;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public void setPrefix(String prefix)
/*  354:     */     throws DOMException
/*  355:     */   {
/*  356: 753 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  357:     */   }
/*  358:     */   
/*  359:     */   public String getLocalName()
/*  360:     */   {
/*  361: 764 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  362:     */     
/*  363: 766 */     return null;
/*  364:     */   }
/*  365:     */   
/*  366:     */   public DocumentType getDoctype()
/*  367:     */   {
/*  368: 777 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  369:     */     
/*  370: 779 */     return null;
/*  371:     */   }
/*  372:     */   
/*  373:     */   public DOMImplementation getImplementation()
/*  374:     */   {
/*  375: 790 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  376:     */     
/*  377: 792 */     return null;
/*  378:     */   }
/*  379:     */   
/*  380:     */   public Element getDocumentElement()
/*  381:     */   {
/*  382: 803 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  383:     */     
/*  384: 805 */     return null;
/*  385:     */   }
/*  386:     */   
/*  387:     */   public Element createElement(String tagName)
/*  388:     */     throws DOMException
/*  389:     */   {
/*  390: 820 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  391:     */     
/*  392: 822 */     return null;
/*  393:     */   }
/*  394:     */   
/*  395:     */   public DocumentFragment createDocumentFragment()
/*  396:     */   {
/*  397: 833 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  398:     */     
/*  399: 835 */     return null;
/*  400:     */   }
/*  401:     */   
/*  402:     */   public Text createTextNode(String data)
/*  403:     */   {
/*  404: 848 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  405:     */     
/*  406: 850 */     return null;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public Comment createComment(String data)
/*  410:     */   {
/*  411: 863 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  412:     */     
/*  413: 865 */     return null;
/*  414:     */   }
/*  415:     */   
/*  416:     */   public CDATASection createCDATASection(String data)
/*  417:     */     throws DOMException
/*  418:     */   {
/*  419: 880 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  420:     */     
/*  421: 882 */     return null;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public ProcessingInstruction createProcessingInstruction(String target, String data)
/*  425:     */     throws DOMException
/*  426:     */   {
/*  427: 899 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  428:     */     
/*  429: 901 */     return null;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public Attr createAttribute(String name)
/*  433:     */     throws DOMException
/*  434:     */   {
/*  435: 916 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  436:     */     
/*  437: 918 */     return null;
/*  438:     */   }
/*  439:     */   
/*  440:     */   public EntityReference createEntityReference(String name)
/*  441:     */     throws DOMException
/*  442:     */   {
/*  443: 934 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  444:     */     
/*  445: 936 */     return null;
/*  446:     */   }
/*  447:     */   
/*  448:     */   public Node importNode(Node importedNode, boolean deep)
/*  449:     */     throws DOMException
/*  450:     */   {
/*  451: 955 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  452:     */     
/*  453: 957 */     return null;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public Element createElementNS(String namespaceURI, String qualifiedName)
/*  457:     */     throws DOMException
/*  458:     */   {
/*  459: 974 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  460:     */     
/*  461: 976 */     return null;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public Attr createAttributeNS(String namespaceURI, String qualifiedName)
/*  465:     */     throws DOMException
/*  466:     */   {
/*  467: 993 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  468:     */     
/*  469: 995 */     return null;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public Element getElementById(String elementId)
/*  473:     */   {
/*  474:1008 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  475:     */     
/*  476:1010 */     return null;
/*  477:     */   }
/*  478:     */   
/*  479:     */   public void setData(String data)
/*  480:     */     throws DOMException
/*  481:     */   {
/*  482:1023 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  483:     */   }
/*  484:     */   
/*  485:     */   public String substringData(int offset, int count)
/*  486:     */     throws DOMException
/*  487:     */   {
/*  488:1039 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  489:     */     
/*  490:1041 */     return null;
/*  491:     */   }
/*  492:     */   
/*  493:     */   public void appendData(String arg)
/*  494:     */     throws DOMException
/*  495:     */   {
/*  496:1053 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void insertData(int offset, String arg)
/*  500:     */     throws DOMException
/*  501:     */   {
/*  502:1066 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void deleteData(int offset, int count)
/*  506:     */     throws DOMException
/*  507:     */   {
/*  508:1079 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  509:     */   }
/*  510:     */   
/*  511:     */   public void replaceData(int offset, int count, String arg)
/*  512:     */     throws DOMException
/*  513:     */   {
/*  514:1094 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  515:     */   }
/*  516:     */   
/*  517:     */   public Text splitText(int offset)
/*  518:     */     throws DOMException
/*  519:     */   {
/*  520:1109 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  521:     */     
/*  522:1111 */     return null;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public Node adoptNode(Node source)
/*  526:     */     throws DOMException
/*  527:     */   {
/*  528:1127 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  529:     */     
/*  530:1129 */     return null;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public String getInputEncoding()
/*  534:     */   {
/*  535:1146 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  536:     */     
/*  537:1148 */     return null;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void setInputEncoding(String encoding)
/*  541:     */   {
/*  542:1164 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  543:     */   }
/*  544:     */   
/*  545:     */   public boolean getStrictErrorChecking()
/*  546:     */   {
/*  547:1185 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  548:     */     
/*  549:1187 */     return false;
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void setStrictErrorChecking(boolean strictErrorChecking)
/*  553:     */   {
/*  554:1207 */     error("ER_FUNCTION_NOT_SUPPORTED");
/*  555:     */   }
/*  556:     */   
/*  557:     */   public Object setUserData(String key, Object data, UserDataHandler handler)
/*  558:     */   {
/*  559:1214 */     return getOwnerDocument().setUserData(key, data, handler);
/*  560:     */   }
/*  561:     */   
/*  562:     */   public Object getUserData(String key)
/*  563:     */   {
/*  564:1227 */     return getOwnerDocument().getUserData(key);
/*  565:     */   }
/*  566:     */   
/*  567:     */   public Object getFeature(String feature, String version)
/*  568:     */   {
/*  569:1253 */     return isSupported(feature, version) ? this : null;
/*  570:     */   }
/*  571:     */   
/*  572:     */   public boolean isEqualNode(Node arg)
/*  573:     */   {
/*  574:1299 */     if (arg == this) {
/*  575:1300 */       return true;
/*  576:     */     }
/*  577:1302 */     if (arg.getNodeType() != getNodeType()) {
/*  578:1303 */       return false;
/*  579:     */     }
/*  580:1307 */     if (getNodeName() == null)
/*  581:     */     {
/*  582:1308 */       if (arg.getNodeName() != null) {
/*  583:1309 */         return false;
/*  584:     */       }
/*  585:     */     }
/*  586:1312 */     else if (!getNodeName().equals(arg.getNodeName())) {
/*  587:1313 */       return false;
/*  588:     */     }
/*  589:1316 */     if (getLocalName() == null)
/*  590:     */     {
/*  591:1317 */       if (arg.getLocalName() != null) {
/*  592:1318 */         return false;
/*  593:     */       }
/*  594:     */     }
/*  595:1321 */     else if (!getLocalName().equals(arg.getLocalName())) {
/*  596:1322 */       return false;
/*  597:     */     }
/*  598:1325 */     if (getNamespaceURI() == null)
/*  599:     */     {
/*  600:1326 */       if (arg.getNamespaceURI() != null) {
/*  601:1327 */         return false;
/*  602:     */       }
/*  603:     */     }
/*  604:1330 */     else if (!getNamespaceURI().equals(arg.getNamespaceURI())) {
/*  605:1331 */       return false;
/*  606:     */     }
/*  607:1334 */     if (getPrefix() == null)
/*  608:     */     {
/*  609:1335 */       if (arg.getPrefix() != null) {
/*  610:1336 */         return false;
/*  611:     */       }
/*  612:     */     }
/*  613:1339 */     else if (!getPrefix().equals(arg.getPrefix())) {
/*  614:1340 */       return false;
/*  615:     */     }
/*  616:1343 */     if (getNodeValue() == null)
/*  617:     */     {
/*  618:1344 */       if (arg.getNodeValue() != null) {
/*  619:1345 */         return false;
/*  620:     */       }
/*  621:     */     }
/*  622:1348 */     else if (!getNodeValue().equals(arg.getNodeValue())) {
/*  623:1349 */       return false;
/*  624:     */     }
/*  625:1362 */     return true;
/*  626:     */   }
/*  627:     */   
/*  628:     */   public String lookupNamespaceURI(String specifiedPrefix)
/*  629:     */   {
/*  630:1375 */     short type = getNodeType();
/*  631:1376 */     switch (type)
/*  632:     */     {
/*  633:     */     case 1: 
/*  634:1379 */       String namespace = getNamespaceURI();
/*  635:1380 */       String prefix = getPrefix();
/*  636:1381 */       if (namespace != null)
/*  637:     */       {
/*  638:1383 */         if ((specifiedPrefix == null) && (prefix == specifiedPrefix)) {
/*  639:1385 */           return namespace;
/*  640:     */         }
/*  641:1386 */         if ((prefix != null) && (prefix.equals(specifiedPrefix))) {
/*  642:1388 */           return namespace;
/*  643:     */         }
/*  644:     */       }
/*  645:1391 */       if (hasAttributes())
/*  646:     */       {
/*  647:1392 */         NamedNodeMap map = getAttributes();
/*  648:1393 */         int length = map.getLength();
/*  649:1394 */         for (int i = 0; i < length; i++)
/*  650:     */         {
/*  651:1395 */           Node attr = map.item(i);
/*  652:1396 */           String attrPrefix = attr.getPrefix();
/*  653:1397 */           String value = attr.getNodeValue();
/*  654:1398 */           namespace = attr.getNamespaceURI();
/*  655:1399 */           if ((namespace != null) && (namespace.equals("http://www.w3.org/2000/xmlns/")))
/*  656:     */           {
/*  657:1401 */             if ((specifiedPrefix == null) && (attr.getNodeName().equals("xmlns"))) {
/*  658:1404 */               return value;
/*  659:     */             }
/*  660:1405 */             if ((attrPrefix != null) && (attrPrefix.equals("xmlns")) && (attr.getLocalName().equals(specifiedPrefix))) {
/*  661:1409 */               return value;
/*  662:     */             }
/*  663:     */           }
/*  664:     */         }
/*  665:     */       }
/*  666:1421 */       return null;
/*  667:     */     case 6: 
/*  668:     */     case 10: 
/*  669:     */     case 11: 
/*  670:     */     case 12: 
/*  671:1435 */       return null;
/*  672:     */     case 2: 
/*  673:1437 */       if (getOwnerElement().getNodeType() == 1) {
/*  674:1438 */         return getOwnerElement().lookupNamespaceURI(specifiedPrefix);
/*  675:     */       }
/*  676:1441 */       return null;
/*  677:     */     }
/*  678:1450 */     return null;
/*  679:     */   }
/*  680:     */   
/*  681:     */   public boolean isDefaultNamespace(String namespaceURI)
/*  682:     */   {
/*  683:1526 */     return false;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public String lookupPrefix(String namespaceURI)
/*  687:     */   {
/*  688:1543 */     if (namespaceURI == null) {
/*  689:1544 */       return null;
/*  690:     */     }
/*  691:1547 */     short type = getNodeType();
/*  692:1549 */     switch (type)
/*  693:     */     {
/*  694:     */     case 6: 
/*  695:     */     case 10: 
/*  696:     */     case 11: 
/*  697:     */     case 12: 
/*  698:1566 */       return null;
/*  699:     */     case 2: 
/*  700:1568 */       if (getOwnerElement().getNodeType() == 1) {
/*  701:1569 */         return getOwnerElement().lookupPrefix(namespaceURI);
/*  702:     */       }
/*  703:1572 */       return null;
/*  704:     */     }
/*  705:1581 */     return null;
/*  706:     */   }
/*  707:     */   
/*  708:     */   public boolean isSameNode(Node other)
/*  709:     */   {
/*  710:1602 */     return this == other;
/*  711:     */   }
/*  712:     */   
/*  713:     */   public void setTextContent(String textContent)
/*  714:     */     throws DOMException
/*  715:     */   {
/*  716:1652 */     setNodeValue(textContent);
/*  717:     */   }
/*  718:     */   
/*  719:     */   public String getTextContent()
/*  720:     */     throws DOMException
/*  721:     */   {
/*  722:1701 */     return getNodeValue();
/*  723:     */   }
/*  724:     */   
/*  725:     */   public short compareDocumentPosition(Node other)
/*  726:     */     throws DOMException
/*  727:     */   {
/*  728:1713 */     return 0;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public String getBaseURI()
/*  732:     */   {
/*  733:1741 */     return null;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public Node renameNode(Node n, String namespaceURI, String name)
/*  737:     */     throws DOMException
/*  738:     */   {
/*  739:1752 */     return n;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public void normalizeDocument() {}
/*  743:     */   
/*  744:     */   public DOMConfiguration getDomConfig()
/*  745:     */   {
/*  746:1769 */     return null;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public void setDocumentURI(String documentURI)
/*  750:     */   {
/*  751:1781 */     this.fDocumentURI = documentURI;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public String getDocumentURI()
/*  755:     */   {
/*  756:1793 */     return this.fDocumentURI;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public String getActualEncoding()
/*  760:     */   {
/*  761:1808 */     return this.actualEncoding;
/*  762:     */   }
/*  763:     */   
/*  764:     */   public void setActualEncoding(String value)
/*  765:     */   {
/*  766:1820 */     this.actualEncoding = value;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public Text replaceWholeText(String content)
/*  770:     */     throws DOMException
/*  771:     */   {
/*  772:1870 */     return null;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public String getWholeText()
/*  776:     */   {
/*  777:1895 */     return null;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public boolean isWhitespaceInElementContent()
/*  781:     */   {
/*  782:1905 */     return false;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public void setIdAttribute(boolean id) {}
/*  786:     */   
/*  787:     */   public void setIdAttribute(String name, boolean makeId) {}
/*  788:     */   
/*  789:     */   public void setIdAttributeNode(Attr at, boolean makeId) {}
/*  790:     */   
/*  791:     */   public void setIdAttributeNS(String namespaceURI, String localName, boolean makeId) {}
/*  792:     */   
/*  793:     */   public TypeInfo getSchemaTypeInfo()
/*  794:     */   {
/*  795:1944 */     return null;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public boolean isId()
/*  799:     */   {
/*  800:1948 */     return false;
/*  801:     */   }
/*  802:     */   
/*  803:     */   public String getXmlEncoding()
/*  804:     */   {
/*  805:1953 */     return this.xmlEncoding;
/*  806:     */   }
/*  807:     */   
/*  808:     */   public void setXmlEncoding(String xmlEncoding)
/*  809:     */   {
/*  810:1956 */     this.xmlEncoding = xmlEncoding;
/*  811:     */   }
/*  812:     */   
/*  813:     */   public boolean getXmlStandalone()
/*  814:     */   {
/*  815:1961 */     return this.xmlStandalone;
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void setXmlStandalone(boolean xmlStandalone)
/*  819:     */     throws DOMException
/*  820:     */   {
/*  821:1965 */     this.xmlStandalone = xmlStandalone;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public String getXmlVersion()
/*  825:     */   {
/*  826:1970 */     return this.xmlVersion;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public void setXmlVersion(String xmlVersion)
/*  830:     */     throws DOMException
/*  831:     */   {
/*  832:1974 */     this.xmlVersion = xmlVersion;
/*  833:     */   }
/*  834:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.UnImplNode
 * JD-Core Version:    0.7.0.1
 */