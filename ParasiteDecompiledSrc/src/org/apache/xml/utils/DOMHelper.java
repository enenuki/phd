/*    1:     */ package org.apache.xml.utils;
/*    2:     */ 
/*    3:     */ import java.util.Hashtable;
/*    4:     */ import java.util.Vector;
/*    5:     */ import javax.xml.parsers.DocumentBuilder;
/*    6:     */ import javax.xml.parsers.DocumentBuilderFactory;
/*    7:     */ import javax.xml.parsers.ParserConfigurationException;
/*    8:     */ import javax.xml.transform.TransformerException;
/*    9:     */ import org.apache.xml.dtm.ref.DTMNodeProxy;
/*   10:     */ import org.apache.xml.res.XMLMessages;
/*   11:     */ import org.w3c.dom.Attr;
/*   12:     */ import org.w3c.dom.DOMImplementation;
/*   13:     */ import org.w3c.dom.Document;
/*   14:     */ import org.w3c.dom.DocumentType;
/*   15:     */ import org.w3c.dom.Element;
/*   16:     */ import org.w3c.dom.Entity;
/*   17:     */ import org.w3c.dom.NamedNodeMap;
/*   18:     */ import org.w3c.dom.Node;
/*   19:     */ import org.w3c.dom.Text;
/*   20:     */ 
/*   21:     */ /**
/*   22:     */  * @deprecated
/*   23:     */  */
/*   24:     */ public class DOMHelper
/*   25:     */ {
/*   26:     */   public static Document createDocument(boolean isSecureProcessing)
/*   27:     */   {
/*   28:     */     try
/*   29:     */     {
/*   30:  85 */       DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
/*   31:     */       
/*   32:  87 */       dfactory.setNamespaceAware(true);
/*   33:  88 */       dfactory.setValidating(true);
/*   34:  90 */       if (isSecureProcessing) {
/*   35:     */         try
/*   36:     */         {
/*   37:  94 */           dfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*   38:     */         }
/*   39:     */         catch (ParserConfigurationException pce) {}
/*   40:     */       }
/*   41:  99 */       DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
/*   42: 100 */       return docBuilder.newDocument();
/*   43:     */     }
/*   44:     */     catch (ParserConfigurationException pce)
/*   45:     */     {
/*   46: 106 */       throw new RuntimeException(XMLMessages.createXMLMessage("ER_CREATEDOCUMENT_NOT_SUPPORTED", null));
/*   47:     */     }
/*   48:     */   }
/*   49:     */   
/*   50:     */   public static Document createDocument()
/*   51:     */   {
/*   52: 126 */     return createDocument(false);
/*   53:     */   }
/*   54:     */   
/*   55:     */   public boolean shouldStripSourceNode(Node textNode)
/*   56:     */     throws TransformerException
/*   57:     */   {
/*   58: 146 */     return false;
/*   59:     */   }
/*   60:     */   
/*   61:     */   public String getUniqueID(Node node)
/*   62:     */   {
/*   63: 176 */     return "N" + Integer.toHexString(node.hashCode()).toUpperCase();
/*   64:     */   }
/*   65:     */   
/*   66:     */   public static boolean isNodeAfter(Node node1, Node node2)
/*   67:     */   {
/*   68: 199 */     if ((node1 == node2) || (isNodeTheSame(node1, node2))) {
/*   69: 200 */       return true;
/*   70:     */     }
/*   71: 203 */     boolean isNodeAfter = true;
/*   72:     */     
/*   73: 205 */     Node parent1 = getParentOfNode(node1);
/*   74: 206 */     Node parent2 = getParentOfNode(node2);
/*   75: 209 */     if ((parent1 == parent2) || (isNodeTheSame(parent1, parent2)))
/*   76:     */     {
/*   77: 211 */       if (null != parent1) {
/*   78: 212 */         isNodeAfter = isNodeAfterSibling(parent1, node1, node2);
/*   79:     */       }
/*   80:     */     }
/*   81:     */     else
/*   82:     */     {
/*   83: 238 */       int nParents1 = 2;int nParents2 = 2;
/*   84: 240 */       while (parent1 != null)
/*   85:     */       {
/*   86: 242 */         nParents1++;
/*   87:     */         
/*   88: 244 */         parent1 = getParentOfNode(parent1);
/*   89:     */       }
/*   90: 247 */       while (parent2 != null)
/*   91:     */       {
/*   92: 249 */         nParents2++;
/*   93:     */         
/*   94: 251 */         parent2 = getParentOfNode(parent2);
/*   95:     */       }
/*   96: 256 */       Node startNode1 = node1;Node startNode2 = node2;
/*   97: 260 */       if (nParents1 < nParents2)
/*   98:     */       {
/*   99: 263 */         int adjust = nParents2 - nParents1;
/*  100: 265 */         for (int i = 0; i < adjust; i++) {
/*  101: 267 */           startNode2 = getParentOfNode(startNode2);
/*  102:     */         }
/*  103:     */       }
/*  104: 270 */       else if (nParents1 > nParents2)
/*  105:     */       {
/*  106: 273 */         int adjust = nParents1 - nParents2;
/*  107: 275 */         for (int i = 0; i < adjust; i++) {
/*  108: 277 */           startNode1 = getParentOfNode(startNode1);
/*  109:     */         }
/*  110:     */       }
/*  111: 281 */       Node prevChild1 = null;Node prevChild2 = null;
/*  112: 284 */       while (null != startNode1)
/*  113:     */       {
/*  114: 286 */         if ((startNode1 == startNode2) || (isNodeTheSame(startNode1, startNode2)))
/*  115:     */         {
/*  116: 288 */           if (null == prevChild1)
/*  117:     */           {
/*  118: 292 */             isNodeAfter = nParents1 < nParents2;
/*  119:     */             
/*  120: 294 */             break;
/*  121:     */           }
/*  122: 299 */           isNodeAfter = isNodeAfterSibling(startNode1, prevChild1, prevChild2);
/*  123:     */           
/*  124:     */ 
/*  125: 302 */           break;
/*  126:     */         }
/*  127: 307 */         prevChild1 = startNode1;
/*  128: 308 */         startNode1 = getParentOfNode(startNode1);
/*  129: 309 */         prevChild2 = startNode2;
/*  130: 310 */         startNode2 = getParentOfNode(startNode2);
/*  131:     */       }
/*  132:     */     }
/*  133: 322 */     return isNodeAfter;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public static boolean isNodeTheSame(Node node1, Node node2)
/*  137:     */   {
/*  138: 334 */     if (((node1 instanceof DTMNodeProxy)) && ((node2 instanceof DTMNodeProxy))) {
/*  139: 335 */       return ((DTMNodeProxy)node1).equals((DTMNodeProxy)node2);
/*  140:     */     }
/*  141: 337 */     return node1 == node2;
/*  142:     */   }
/*  143:     */   
/*  144:     */   private static boolean isNodeAfterSibling(Node parent, Node child1, Node child2)
/*  145:     */   {
/*  146: 358 */     boolean isNodeAfterSibling = false;
/*  147: 359 */     short child1type = child1.getNodeType();
/*  148: 360 */     short child2type = child2.getNodeType();
/*  149: 362 */     if ((2 != child1type) && (2 == child2type))
/*  150:     */     {
/*  151: 367 */       isNodeAfterSibling = false;
/*  152:     */     }
/*  153: 369 */     else if ((2 == child1type) && (2 != child2type))
/*  154:     */     {
/*  155: 374 */       isNodeAfterSibling = true;
/*  156:     */     }
/*  157: 376 */     else if (2 == child1type)
/*  158:     */     {
/*  159: 378 */       NamedNodeMap children = parent.getAttributes();
/*  160: 379 */       int nNodes = children.getLength();
/*  161: 380 */       boolean found1 = false;boolean found2 = false;
/*  162: 383 */       for (int i = 0; i < nNodes; i++)
/*  163:     */       {
/*  164: 385 */         Node child = children.item(i);
/*  165: 387 */         if ((child1 == child) || (isNodeTheSame(child1, child)))
/*  166:     */         {
/*  167: 389 */           if (found2)
/*  168:     */           {
/*  169: 391 */             isNodeAfterSibling = false;
/*  170:     */             
/*  171: 393 */             break;
/*  172:     */           }
/*  173: 396 */           found1 = true;
/*  174:     */         }
/*  175: 398 */         else if ((child2 == child) || (isNodeTheSame(child2, child)))
/*  176:     */         {
/*  177: 400 */           if (found1)
/*  178:     */           {
/*  179: 402 */             isNodeAfterSibling = true;
/*  180:     */             
/*  181: 404 */             break;
/*  182:     */           }
/*  183: 407 */           found2 = true;
/*  184:     */         }
/*  185:     */       }
/*  186:     */     }
/*  187:     */     else
/*  188:     */     {
/*  189: 424 */       Node child = parent.getFirstChild();
/*  190: 425 */       boolean found1 = false;boolean found2 = false;
/*  191: 427 */       while (null != child)
/*  192:     */       {
/*  193: 431 */         if ((child1 == child) || (isNodeTheSame(child1, child)))
/*  194:     */         {
/*  195: 433 */           if (found2)
/*  196:     */           {
/*  197: 435 */             isNodeAfterSibling = false;
/*  198:     */             
/*  199: 437 */             break;
/*  200:     */           }
/*  201: 440 */           found1 = true;
/*  202:     */         }
/*  203: 442 */         else if ((child2 == child) || (isNodeTheSame(child2, child)))
/*  204:     */         {
/*  205: 444 */           if (found1)
/*  206:     */           {
/*  207: 446 */             isNodeAfterSibling = true;
/*  208:     */             
/*  209: 448 */             break;
/*  210:     */           }
/*  211: 451 */           found2 = true;
/*  212:     */         }
/*  213: 454 */         child = child.getNextSibling();
/*  214:     */       }
/*  215:     */     }
/*  216: 458 */     return isNodeAfterSibling;
/*  217:     */   }
/*  218:     */   
/*  219:     */   public short getLevel(Node n)
/*  220:     */   {
/*  221: 476 */     short level = 1;
/*  222: 478 */     while (null != (n = getParentOfNode(n))) {
/*  223: 480 */       level = (short)(level + 1);
/*  224:     */     }
/*  225: 483 */     return level;
/*  226:     */   }
/*  227:     */   
/*  228:     */   public String getNamespaceForPrefix(String prefix, Element namespaceContext)
/*  229:     */   {
/*  230: 509 */     Node parent = namespaceContext;
/*  231: 510 */     String namespace = null;
/*  232: 512 */     if (prefix.equals("xml"))
/*  233:     */     {
/*  234: 514 */       namespace = "http://www.w3.org/XML/1998/namespace";
/*  235:     */     }
/*  236: 516 */     else if (prefix.equals("xmlns"))
/*  237:     */     {
/*  238: 523 */       namespace = "http://www.w3.org/2000/xmlns/";
/*  239:     */     }
/*  240:     */     else
/*  241:     */     {
/*  242: 528 */       String declname = "xmlns:" + prefix;
/*  243:     */       int type;
/*  244: 534 */       while ((null != parent) && (null == namespace) && (((type = parent.getNodeType()) == 1) || (type == 5)))
/*  245:     */       {
/*  246:     */         int i;
/*  247: 537 */         if (i == 1)
/*  248:     */         {
/*  249: 548 */           Attr attr = ((Element)parent).getAttributeNode(declname);
/*  250: 549 */           if (attr != null)
/*  251:     */           {
/*  252: 551 */             namespace = attr.getNodeValue();
/*  253: 552 */             break;
/*  254:     */           }
/*  255:     */         }
/*  256: 556 */         parent = getParentOfNode(parent);
/*  257:     */       }
/*  258:     */     }
/*  259: 560 */     return namespace;
/*  260:     */   }
/*  261:     */   
/*  262: 566 */   Hashtable m_NSInfos = new Hashtable();
/*  263: 570 */   protected static final NSInfo m_NSInfoUnProcWithXMLNS = new NSInfo(false, true);
/*  264: 575 */   protected static final NSInfo m_NSInfoUnProcWithoutXMLNS = new NSInfo(false, false);
/*  265: 580 */   protected static final NSInfo m_NSInfoUnProcNoAncestorXMLNS = new NSInfo(false, false, 2);
/*  266: 585 */   protected static final NSInfo m_NSInfoNullWithXMLNS = new NSInfo(true, true);
/*  267: 590 */   protected static final NSInfo m_NSInfoNullWithoutXMLNS = new NSInfo(true, false);
/*  268: 595 */   protected static final NSInfo m_NSInfoNullNoAncestorXMLNS = new NSInfo(true, false, 2);
/*  269: 600 */   protected Vector m_candidateNoAncestorXMLNS = new Vector();
/*  270:     */   
/*  271:     */   public String getNamespaceOfNode(Node n)
/*  272:     */   {
/*  273: 620 */     short ntype = n.getNodeType();
/*  274:     */     NSInfo nsInfo;
/*  275:     */     boolean hasProcessedNS;
/*  276: 622 */     if (2 != ntype)
/*  277:     */     {
/*  278: 624 */       Object nsObj = this.m_NSInfos.get(n);
/*  279:     */       
/*  280: 626 */       nsInfo = nsObj == null ? null : (NSInfo)nsObj;
/*  281: 627 */       hasProcessedNS = nsInfo == null ? false : nsInfo.m_hasProcessedNS;
/*  282:     */     }
/*  283:     */     else
/*  284:     */     {
/*  285: 631 */       hasProcessedNS = false;
/*  286: 632 */       nsInfo = null;
/*  287:     */     }
/*  288:     */     String namespaceOfPrefix;
/*  289: 635 */     if (hasProcessedNS)
/*  290:     */     {
/*  291: 637 */       namespaceOfPrefix = nsInfo.m_namespace;
/*  292:     */     }
/*  293:     */     else
/*  294:     */     {
/*  295: 641 */       namespaceOfPrefix = null;
/*  296:     */       
/*  297: 643 */       String nodeName = n.getNodeName();
/*  298: 644 */       int indexOfNSSep = nodeName.indexOf(':');
/*  299:     */       String prefix;
/*  300: 647 */       if (2 == ntype)
/*  301:     */       {
/*  302: 649 */         if (indexOfNSSep > 0) {
/*  303: 651 */           prefix = nodeName.substring(0, indexOfNSSep);
/*  304:     */         } else {
/*  305: 658 */           return namespaceOfPrefix;
/*  306:     */         }
/*  307:     */       }
/*  308:     */       else {
/*  309: 663 */         prefix = indexOfNSSep >= 0 ? nodeName.substring(0, indexOfNSSep) : "";
/*  310:     */       }
/*  311: 667 */       boolean ancestorsHaveXMLNS = false;
/*  312: 668 */       boolean nHasXMLNS = false;
/*  313: 670 */       if (prefix.equals("xml"))
/*  314:     */       {
/*  315: 672 */         namespaceOfPrefix = "http://www.w3.org/XML/1998/namespace";
/*  316:     */       }
/*  317:     */       else
/*  318:     */       {
/*  319: 677 */         Node parent = n;
/*  320: 679 */         while ((null != parent) && (null == namespaceOfPrefix))
/*  321:     */         {
/*  322: 681 */           if ((null != nsInfo) && (nsInfo.m_ancestorHasXMLNSAttrs == 2)) {
/*  323:     */             break;
/*  324:     */           }
/*  325: 688 */           int parentType = parent.getNodeType();
/*  326: 690 */           if ((null == nsInfo) || (nsInfo.m_hasXMLNSAttrs))
/*  327:     */           {
/*  328: 692 */             boolean elementHasXMLNS = false;
/*  329: 694 */             if (parentType == 1)
/*  330:     */             {
/*  331: 696 */               NamedNodeMap nnm = parent.getAttributes();
/*  332: 698 */               for (int i = 0; i < nnm.getLength(); i++)
/*  333:     */               {
/*  334: 700 */                 Node attr = nnm.item(i);
/*  335: 701 */                 String aname = attr.getNodeName();
/*  336: 703 */                 if (aname.charAt(0) == 'x')
/*  337:     */                 {
/*  338: 705 */                   boolean isPrefix = aname.startsWith("xmlns:");
/*  339: 707 */                   if ((aname.equals("xmlns")) || (isPrefix))
/*  340:     */                   {
/*  341: 709 */                     if (n == parent) {
/*  342: 710 */                       nHasXMLNS = true;
/*  343:     */                     }
/*  344: 712 */                     elementHasXMLNS = true;
/*  345: 713 */                     ancestorsHaveXMLNS = true;
/*  346:     */                     
/*  347: 715 */                     String p = isPrefix ? aname.substring(6) : "";
/*  348: 717 */                     if (p.equals(prefix))
/*  349:     */                     {
/*  350: 719 */                       namespaceOfPrefix = attr.getNodeValue();
/*  351:     */                       
/*  352: 721 */                       break;
/*  353:     */                     }
/*  354:     */                   }
/*  355:     */                 }
/*  356:     */               }
/*  357:     */             }
/*  358: 728 */             if ((2 != parentType) && (null == nsInfo) && (n != parent))
/*  359:     */             {
/*  360: 731 */               nsInfo = elementHasXMLNS ? m_NSInfoUnProcWithXMLNS : m_NSInfoUnProcWithoutXMLNS;
/*  361:     */               
/*  362:     */ 
/*  363: 734 */               this.m_NSInfos.put(parent, nsInfo);
/*  364:     */             }
/*  365:     */           }
/*  366: 738 */           if (2 == parentType)
/*  367:     */           {
/*  368: 740 */             parent = getParentOfNode(parent);
/*  369:     */           }
/*  370:     */           else
/*  371:     */           {
/*  372: 744 */             this.m_candidateNoAncestorXMLNS.addElement(parent);
/*  373: 745 */             this.m_candidateNoAncestorXMLNS.addElement(nsInfo);
/*  374:     */             
/*  375: 747 */             parent = parent.getParentNode();
/*  376:     */           }
/*  377: 750 */           if (null != parent)
/*  378:     */           {
/*  379: 752 */             Object nsObj = this.m_NSInfos.get(parent);
/*  380:     */             
/*  381: 754 */             nsInfo = nsObj == null ? null : (NSInfo)nsObj;
/*  382:     */           }
/*  383:     */         }
/*  384: 758 */         int nCandidates = this.m_candidateNoAncestorXMLNS.size();
/*  385: 760 */         if (nCandidates > 0)
/*  386:     */         {
/*  387: 762 */           if ((false == ancestorsHaveXMLNS) && (null == parent)) {
/*  388: 764 */             for (int i = 0; i < nCandidates; i += 2)
/*  389:     */             {
/*  390: 766 */               Object candidateInfo = this.m_candidateNoAncestorXMLNS.elementAt(i + 1);
/*  391: 769 */               if (candidateInfo == m_NSInfoUnProcWithoutXMLNS) {
/*  392: 771 */                 this.m_NSInfos.put(this.m_candidateNoAncestorXMLNS.elementAt(i), m_NSInfoUnProcNoAncestorXMLNS);
/*  393: 774 */               } else if (candidateInfo == m_NSInfoNullWithoutXMLNS) {
/*  394: 776 */                 this.m_NSInfos.put(this.m_candidateNoAncestorXMLNS.elementAt(i), m_NSInfoNullNoAncestorXMLNS);
/*  395:     */               }
/*  396:     */             }
/*  397:     */           }
/*  398: 782 */           this.m_candidateNoAncestorXMLNS.removeAllElements();
/*  399:     */         }
/*  400:     */       }
/*  401: 786 */       if (2 != ntype) {
/*  402: 788 */         if (null == namespaceOfPrefix)
/*  403:     */         {
/*  404: 790 */           if (ancestorsHaveXMLNS)
/*  405:     */           {
/*  406: 792 */             if (nHasXMLNS) {
/*  407: 793 */               this.m_NSInfos.put(n, m_NSInfoNullWithXMLNS);
/*  408:     */             } else {
/*  409: 795 */               this.m_NSInfos.put(n, m_NSInfoNullWithoutXMLNS);
/*  410:     */             }
/*  411:     */           }
/*  412:     */           else {
/*  413: 799 */             this.m_NSInfos.put(n, m_NSInfoNullNoAncestorXMLNS);
/*  414:     */           }
/*  415:     */         }
/*  416:     */         else {
/*  417: 804 */           this.m_NSInfos.put(n, new NSInfo(namespaceOfPrefix, nHasXMLNS));
/*  418:     */         }
/*  419:     */       }
/*  420:     */     }
/*  421: 809 */     return namespaceOfPrefix;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public String getLocalNameOfNode(Node n)
/*  425:     */   {
/*  426: 824 */     String qname = n.getNodeName();
/*  427: 825 */     int index = qname.indexOf(':');
/*  428:     */     
/*  429: 827 */     return index < 0 ? qname : qname.substring(index + 1);
/*  430:     */   }
/*  431:     */   
/*  432:     */   public String getExpandedElementName(Element elem)
/*  433:     */   {
/*  434: 845 */     String namespace = getNamespaceOfNode(elem);
/*  435:     */     
/*  436: 847 */     return null != namespace ? namespace + ":" + getLocalNameOfNode(elem) : getLocalNameOfNode(elem);
/*  437:     */   }
/*  438:     */   
/*  439:     */   public String getExpandedAttributeName(Attr attr)
/*  440:     */   {
/*  441: 867 */     String namespace = getNamespaceOfNode(attr);
/*  442:     */     
/*  443: 869 */     return null != namespace ? namespace + ":" + getLocalNameOfNode(attr) : getLocalNameOfNode(attr);
/*  444:     */   }
/*  445:     */   
/*  446:     */   /**
/*  447:     */    * @deprecated
/*  448:     */    */
/*  449:     */   public boolean isIgnorableWhitespace(Text node)
/*  450:     */   {
/*  451: 896 */     boolean isIgnorable = false;
/*  452:     */     
/*  453:     */ 
/*  454:     */ 
/*  455:     */ 
/*  456:     */ 
/*  457:     */ 
/*  458:     */ 
/*  459: 904 */     return isIgnorable;
/*  460:     */   }
/*  461:     */   
/*  462:     */   /**
/*  463:     */    * @deprecated
/*  464:     */    */
/*  465:     */   public Node getRoot(Node node)
/*  466:     */   {
/*  467: 918 */     Node root = null;
/*  468: 920 */     while (node != null)
/*  469:     */     {
/*  470: 922 */       root = node;
/*  471: 923 */       node = getParentOfNode(node);
/*  472:     */     }
/*  473: 926 */     return root;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public Node getRootNode(Node n)
/*  477:     */   {
/*  478: 949 */     int nt = n.getNodeType();
/*  479: 950 */     return (9 == nt) || (11 == nt) ? n : n.getOwnerDocument();
/*  480:     */   }
/*  481:     */   
/*  482:     */   public boolean isNamespaceNode(Node n)
/*  483:     */   {
/*  484: 967 */     if (2 == n.getNodeType())
/*  485:     */     {
/*  486: 969 */       String attrName = n.getNodeName();
/*  487:     */       
/*  488: 971 */       return (attrName.startsWith("xmlns:")) || (attrName.equals("xmlns"));
/*  489:     */     }
/*  490: 974 */     return false;
/*  491:     */   }
/*  492:     */   
/*  493:     */   public static Node getParentOfNode(Node node)
/*  494:     */     throws RuntimeException
/*  495:     */   {
/*  496:1006 */     short nodeType = node.getNodeType();
/*  497:     */     Node parent;
/*  498:1008 */     if (2 == nodeType)
/*  499:     */     {
/*  500:1010 */       Document doc = node.getOwnerDocument();
/*  501:     */       
/*  502:     */ 
/*  503:     */ 
/*  504:     */ 
/*  505:     */ 
/*  506:     */ 
/*  507:     */ 
/*  508:     */ 
/*  509:     */ 
/*  510:     */ 
/*  511:     */ 
/*  512:     */ 
/*  513:     */ 
/*  514:     */ 
/*  515:     */ 
/*  516:     */ 
/*  517:     */ 
/*  518:1028 */       DOMImplementation impl = doc.getImplementation();
/*  519:1029 */       if ((impl != null) && (impl.hasFeature("Core", "2.0")))
/*  520:     */       {
/*  521:1031 */         parent = ((Attr)node).getOwnerElement();
/*  522:1032 */         return parent;
/*  523:     */       }
/*  524:1037 */       Element rootElem = doc.getDocumentElement();
/*  525:1039 */       if (null == rootElem) {
/*  526:1041 */         throw new RuntimeException(XMLMessages.createXMLMessage("ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", null));
/*  527:     */       }
/*  528:1047 */       parent = locateAttrParent(rootElem, node);
/*  529:     */     }
/*  530:     */     else
/*  531:     */     {
/*  532:1052 */       parent = node.getParentNode();
/*  533:     */     }
/*  534:1060 */     return parent;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public Element getElementByID(String id, Document doc)
/*  538:     */   {
/*  539:1083 */     return null;
/*  540:     */   }
/*  541:     */   
/*  542:     */   public String getUnparsedEntityURI(String name, Document doc)
/*  543:     */   {
/*  544:1124 */     String url = "";
/*  545:1125 */     DocumentType doctype = doc.getDoctype();
/*  546:1127 */     if (null != doctype)
/*  547:     */     {
/*  548:1129 */       NamedNodeMap entities = doctype.getEntities();
/*  549:1130 */       if (null == entities) {
/*  550:1131 */         return url;
/*  551:     */       }
/*  552:1132 */       Entity entity = (Entity)entities.getNamedItem(name);
/*  553:1133 */       if (null == entity) {
/*  554:1134 */         return url;
/*  555:     */       }
/*  556:1136 */       String notationName = entity.getNotationName();
/*  557:1138 */       if (null != notationName)
/*  558:     */       {
/*  559:1149 */         url = entity.getSystemId();
/*  560:1151 */         if (null == url) {
/*  561:1153 */           url = entity.getPublicId();
/*  562:     */         }
/*  563:     */       }
/*  564:     */     }
/*  565:1163 */     return url;
/*  566:     */   }
/*  567:     */   
/*  568:     */   private static Node locateAttrParent(Element elem, Node attr)
/*  569:     */   {
/*  570:1190 */     Node parent = null;
/*  571:     */     
/*  572:     */ 
/*  573:     */ 
/*  574:     */ 
/*  575:     */ 
/*  576:     */ 
/*  577:1197 */     Attr check = elem.getAttributeNode(attr.getNodeName());
/*  578:1198 */     if (check == attr) {
/*  579:1199 */       parent = elem;
/*  580:     */     }
/*  581:1201 */     if (null == parent) {
/*  582:1203 */       for (Node node = elem.getFirstChild(); null != node; node = node.getNextSibling()) {
/*  583:1206 */         if (1 == node.getNodeType())
/*  584:     */         {
/*  585:1208 */           parent = locateAttrParent((Element)node, attr);
/*  586:1210 */           if (null != parent) {
/*  587:     */             break;
/*  588:     */           }
/*  589:     */         }
/*  590:     */       }
/*  591:     */     }
/*  592:1216 */     return parent;
/*  593:     */   }
/*  594:     */   
/*  595:1223 */   protected Document m_DOMFactory = null;
/*  596:     */   
/*  597:     */   public void setDOMFactory(Document domFactory)
/*  598:     */   {
/*  599:1235 */     this.m_DOMFactory = domFactory;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public Document getDOMFactory()
/*  603:     */   {
/*  604:1247 */     if (null == this.m_DOMFactory) {
/*  605:1249 */       this.m_DOMFactory = createDocument();
/*  606:     */     }
/*  607:1252 */     return this.m_DOMFactory;
/*  608:     */   }
/*  609:     */   
/*  610:     */   public static String getNodeData(Node node)
/*  611:     */   {
/*  612:1269 */     FastStringBuffer buf = StringBufferPool.get();
/*  613:     */     String s;
/*  614:     */     try
/*  615:     */     {
/*  616:1274 */       getNodeData(node, buf);
/*  617:     */       
/*  618:1276 */       s = buf.length() > 0 ? buf.toString() : "";
/*  619:     */     }
/*  620:     */     finally
/*  621:     */     {
/*  622:1280 */       StringBufferPool.free(buf);
/*  623:     */     }
/*  624:1283 */     return s;
/*  625:     */   }
/*  626:     */   
/*  627:     */   public static void getNodeData(Node node, FastStringBuffer buf)
/*  628:     */   {
/*  629:1306 */     switch (node.getNodeType())
/*  630:     */     {
/*  631:     */     case 1: 
/*  632:     */     case 9: 
/*  633:     */     case 11: 
/*  634:1312 */       for (Node child = node.getFirstChild(); null != child; child = child.getNextSibling()) {
/*  635:1315 */         getNodeData(child, buf);
/*  636:     */       }
/*  637:1318 */       break;
/*  638:     */     case 3: 
/*  639:     */     case 4: 
/*  640:1321 */       buf.append(node.getNodeValue());
/*  641:1322 */       break;
/*  642:     */     case 2: 
/*  643:1324 */       buf.append(node.getNodeValue());
/*  644:1325 */       break;
/*  645:     */     case 7: 
/*  646:     */       break;
/*  647:     */     }
/*  648:     */   }
/*  649:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.DOMHelper
 * JD-Core Version:    0.7.0.1
 */