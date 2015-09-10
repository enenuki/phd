/*    1:     */ package org.apache.xalan.templates;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.Enumeration;
/*    6:     */ import java.util.List;
/*    7:     */ import javax.xml.transform.SourceLocator;
/*    8:     */ import javax.xml.transform.TransformerException;
/*    9:     */ import org.apache.xalan.res.XSLMessages;
/*   10:     */ import org.apache.xalan.transformer.TransformerImpl;
/*   11:     */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   12:     */ import org.apache.xml.serializer.SerializationHandler;
/*   13:     */ import org.apache.xml.utils.PrefixResolver;
/*   14:     */ import org.apache.xml.utils.UnImplNode;
/*   15:     */ import org.apache.xpath.ExpressionNode;
/*   16:     */ import org.apache.xpath.WhitespaceStrippingElementMatcher;
/*   17:     */ import org.apache.xpath.XPathContext;
/*   18:     */ import org.w3c.dom.DOMException;
/*   19:     */ import org.w3c.dom.Document;
/*   20:     */ import org.w3c.dom.Element;
/*   21:     */ import org.w3c.dom.Node;
/*   22:     */ import org.w3c.dom.NodeList;
/*   23:     */ import org.xml.sax.ContentHandler;
/*   24:     */ import org.xml.sax.SAXException;
/*   25:     */ import org.xml.sax.helpers.NamespaceSupport;
/*   26:     */ 
/*   27:     */ public class ElemTemplateElement
/*   28:     */   extends UnImplNode
/*   29:     */   implements PrefixResolver, Serializable, ExpressionNode, WhitespaceStrippingElementMatcher, XSLTVisitable
/*   30:     */ {
/*   31:     */   static final long serialVersionUID = 4440018597841834447L;
/*   32:     */   private int m_lineNumber;
/*   33:     */   private int m_endLineNumber;
/*   34:     */   private int m_columnNumber;
/*   35:     */   private int m_endColumnNumber;
/*   36:     */   
/*   37:     */   public boolean isCompiledTemplate()
/*   38:     */   {
/*   39:  79 */     return false;
/*   40:     */   }
/*   41:     */   
/*   42:     */   public int getXSLToken()
/*   43:     */   {
/*   44:  91 */     return -1;
/*   45:     */   }
/*   46:     */   
/*   47:     */   public String getNodeName()
/*   48:     */   {
/*   49: 101 */     return "Unknown XSLT Element";
/*   50:     */   }
/*   51:     */   
/*   52:     */   public String getLocalName()
/*   53:     */   {
/*   54: 113 */     return getNodeName();
/*   55:     */   }
/*   56:     */   
/*   57:     */   public void runtimeInit(TransformerImpl transformer)
/*   58:     */     throws TransformerException
/*   59:     */   {}
/*   60:     */   
/*   61:     */   public void execute(TransformerImpl transformer)
/*   62:     */     throws TransformerException
/*   63:     */   {}
/*   64:     */   
/*   65:     */   public StylesheetComposed getStylesheetComposed()
/*   66:     */   {
/*   67: 149 */     return this.m_parentNode.getStylesheetComposed();
/*   68:     */   }
/*   69:     */   
/*   70:     */   public Stylesheet getStylesheet()
/*   71:     */   {
/*   72: 161 */     return null == this.m_parentNode ? null : this.m_parentNode.getStylesheet();
/*   73:     */   }
/*   74:     */   
/*   75:     */   public StylesheetRoot getStylesheetRoot()
/*   76:     */   {
/*   77: 174 */     return this.m_parentNode.getStylesheetRoot();
/*   78:     */   }
/*   79:     */   
/*   80:     */   public void recompose(StylesheetRoot root)
/*   81:     */     throws TransformerException
/*   82:     */   {}
/*   83:     */   
/*   84:     */   public void compose(StylesheetRoot sroot)
/*   85:     */     throws TransformerException
/*   86:     */   {
/*   87: 193 */     resolvePrefixTables();
/*   88: 194 */     ElemTemplateElement t = getFirstChildElem();
/*   89: 195 */     this.m_hasTextLitOnly = ((t != null) && (t.getXSLToken() == 78) && (t.getNextSiblingElem() == null));
/*   90:     */     
/*   91:     */ 
/*   92:     */ 
/*   93: 199 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/*   94: 200 */     cstate.pushStackMark();
/*   95:     */   }
/*   96:     */   
/*   97:     */   public void endCompose(StylesheetRoot sroot)
/*   98:     */     throws TransformerException
/*   99:     */   {
/*  100: 208 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/*  101: 209 */     cstate.popStackMark();
/*  102:     */   }
/*  103:     */   
/*  104:     */   public void error(String msg, Object[] args)
/*  105:     */   {
/*  106: 221 */     String themsg = XSLMessages.createMessage(msg, args);
/*  107:     */     
/*  108: 223 */     throw new RuntimeException(XSLMessages.createMessage("ER_ELEMTEMPLATEELEM_ERR", new Object[] { themsg }));
/*  109:     */   }
/*  110:     */   
/*  111:     */   public void error(String msg)
/*  112:     */   {
/*  113: 236 */     error(msg, null);
/*  114:     */   }
/*  115:     */   
/*  116:     */   public Node appendChild(Node newChild)
/*  117:     */     throws DOMException
/*  118:     */   {
/*  119: 257 */     if (null == newChild) {
/*  120: 259 */       error("ER_NULL_CHILD", null);
/*  121:     */     }
/*  122: 262 */     ElemTemplateElement elem = (ElemTemplateElement)newChild;
/*  123: 264 */     if (null == this.m_firstChild)
/*  124:     */     {
/*  125: 266 */       this.m_firstChild = elem;
/*  126:     */     }
/*  127:     */     else
/*  128:     */     {
/*  129: 270 */       ElemTemplateElement last = (ElemTemplateElement)getLastChild();
/*  130:     */       
/*  131: 272 */       last.m_nextSibling = elem;
/*  132:     */     }
/*  133: 275 */     elem.m_parentNode = this;
/*  134:     */     
/*  135: 277 */     return newChild;
/*  136:     */   }
/*  137:     */   
/*  138:     */   public ElemTemplateElement appendChild(ElemTemplateElement elem)
/*  139:     */   {
/*  140: 295 */     if (null == elem) {
/*  141: 297 */       error("ER_NULL_CHILD", null);
/*  142:     */     }
/*  143: 300 */     if (null == this.m_firstChild)
/*  144:     */     {
/*  145: 302 */       this.m_firstChild = elem;
/*  146:     */     }
/*  147:     */     else
/*  148:     */     {
/*  149: 306 */       ElemTemplateElement last = getLastChildElem();
/*  150:     */       
/*  151: 308 */       last.m_nextSibling = elem;
/*  152:     */     }
/*  153: 311 */     elem.setParentElem(this);
/*  154:     */     
/*  155: 313 */     return elem;
/*  156:     */   }
/*  157:     */   
/*  158:     */   public boolean hasChildNodes()
/*  159:     */   {
/*  160: 324 */     return null != this.m_firstChild;
/*  161:     */   }
/*  162:     */   
/*  163:     */   public short getNodeType()
/*  164:     */   {
/*  165: 334 */     return 1;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public NodeList getChildNodes()
/*  169:     */   {
/*  170: 344 */     return this;
/*  171:     */   }
/*  172:     */   
/*  173:     */   public ElemTemplateElement removeChild(ElemTemplateElement childETE)
/*  174:     */   {
/*  175: 364 */     if ((childETE == null) || (childETE.m_parentNode != this)) {
/*  176: 365 */       return null;
/*  177:     */     }
/*  178: 368 */     if (childETE == this.m_firstChild)
/*  179:     */     {
/*  180: 369 */       this.m_firstChild = childETE.m_nextSibling;
/*  181:     */     }
/*  182:     */     else
/*  183:     */     {
/*  184: 372 */       ElemTemplateElement prev = childETE.getPreviousSiblingElem();
/*  185:     */       
/*  186: 374 */       prev.m_nextSibling = childETE.m_nextSibling;
/*  187:     */     }
/*  188: 378 */     childETE.m_parentNode = null;
/*  189: 379 */     childETE.m_nextSibling = null;
/*  190:     */     
/*  191: 381 */     return childETE;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Node replaceChild(Node newChild, Node oldChild)
/*  195:     */     throws DOMException
/*  196:     */   {
/*  197: 397 */     if ((oldChild == null) || (oldChild.getParentNode() != this)) {
/*  198: 398 */       return null;
/*  199:     */     }
/*  200: 400 */     ElemTemplateElement newChildElem = (ElemTemplateElement)newChild;
/*  201: 401 */     ElemTemplateElement oldChildElem = (ElemTemplateElement)oldChild;
/*  202:     */     
/*  203:     */ 
/*  204: 404 */     ElemTemplateElement prev = (ElemTemplateElement)oldChildElem.getPreviousSibling();
/*  205: 407 */     if (null != prev) {
/*  206: 408 */       prev.m_nextSibling = newChildElem;
/*  207:     */     }
/*  208: 411 */     if (this.m_firstChild == oldChildElem) {
/*  209: 412 */       this.m_firstChild = newChildElem;
/*  210:     */     }
/*  211: 414 */     newChildElem.m_parentNode = this;
/*  212: 415 */     oldChildElem.m_parentNode = null;
/*  213: 416 */     newChildElem.m_nextSibling = oldChildElem.m_nextSibling;
/*  214: 417 */     oldChildElem.m_nextSibling = null;
/*  215:     */     
/*  216:     */ 
/*  217:     */ 
/*  218: 421 */     return newChildElem;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public Node insertBefore(Node newChild, Node refChild)
/*  222:     */     throws DOMException
/*  223:     */   {
/*  224: 436 */     if (null == refChild)
/*  225:     */     {
/*  226: 438 */       appendChild(newChild);
/*  227: 439 */       return newChild;
/*  228:     */     }
/*  229: 442 */     if (newChild == refChild) {
/*  230: 445 */       return newChild;
/*  231:     */     }
/*  232: 448 */     Node node = this.m_firstChild;
/*  233: 449 */     Node prev = null;
/*  234: 450 */     boolean foundit = false;
/*  235: 452 */     while (null != node) {
/*  236: 455 */       if (newChild == node)
/*  237:     */       {
/*  238: 457 */         if (null != prev) {
/*  239: 458 */           ((ElemTemplateElement)prev).m_nextSibling = ((ElemTemplateElement)node.getNextSibling());
/*  240:     */         } else {
/*  241: 461 */           this.m_firstChild = ((ElemTemplateElement)node.getNextSibling());
/*  242:     */         }
/*  243: 462 */         node = node.getNextSibling();
/*  244:     */       }
/*  245: 465 */       else if (refChild == node)
/*  246:     */       {
/*  247: 467 */         if (null != prev) {
/*  248: 469 */           ((ElemTemplateElement)prev).m_nextSibling = ((ElemTemplateElement)newChild);
/*  249:     */         } else {
/*  250: 473 */           this.m_firstChild = ((ElemTemplateElement)newChild);
/*  251:     */         }
/*  252: 475 */         ((ElemTemplateElement)newChild).m_nextSibling = ((ElemTemplateElement)refChild);
/*  253: 476 */         ((ElemTemplateElement)newChild).setParentElem(this);
/*  254: 477 */         prev = newChild;
/*  255: 478 */         node = node.getNextSibling();
/*  256: 479 */         foundit = true;
/*  257:     */       }
/*  258:     */       else
/*  259:     */       {
/*  260: 482 */         prev = node;
/*  261: 483 */         node = node.getNextSibling();
/*  262:     */       }
/*  263:     */     }
/*  264: 486 */     if (!foundit) {
/*  265: 487 */       throw new DOMException((short)8, "refChild was not found in insertBefore method!");
/*  266:     */     }
/*  267: 490 */     return newChild;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public ElemTemplateElement replaceChild(ElemTemplateElement newChildElem, ElemTemplateElement oldChildElem)
/*  271:     */   {
/*  272: 508 */     if ((oldChildElem == null) || (oldChildElem.getParentElem() != this)) {
/*  273: 509 */       return null;
/*  274:     */     }
/*  275: 512 */     ElemTemplateElement prev = oldChildElem.getPreviousSiblingElem();
/*  276: 515 */     if (null != prev) {
/*  277: 516 */       prev.m_nextSibling = newChildElem;
/*  278:     */     }
/*  279: 519 */     if (this.m_firstChild == oldChildElem) {
/*  280: 520 */       this.m_firstChild = newChildElem;
/*  281:     */     }
/*  282: 522 */     newChildElem.m_parentNode = this;
/*  283: 523 */     oldChildElem.m_parentNode = null;
/*  284: 524 */     newChildElem.m_nextSibling = oldChildElem.m_nextSibling;
/*  285: 525 */     oldChildElem.m_nextSibling = null;
/*  286:     */     
/*  287:     */ 
/*  288:     */ 
/*  289: 529 */     return newChildElem;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public int getLength()
/*  293:     */   {
/*  294: 543 */     int count = 0;
/*  295: 545 */     for (ElemTemplateElement node = this.m_firstChild; node != null; node = node.m_nextSibling) {
/*  296: 548 */       count++;
/*  297:     */     }
/*  298: 551 */     return count;
/*  299:     */   }
/*  300:     */   
/*  301:     */   public Node item(int index)
/*  302:     */   {
/*  303: 567 */     ElemTemplateElement node = this.m_firstChild;
/*  304: 569 */     for (int i = 0; (i < index) && (node != null); i++) {
/*  305: 571 */       node = node.m_nextSibling;
/*  306:     */     }
/*  307: 574 */     return node;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public Document getOwnerDocument()
/*  311:     */   {
/*  312: 584 */     return getStylesheet();
/*  313:     */   }
/*  314:     */   
/*  315:     */   public ElemTemplate getOwnerXSLTemplate()
/*  316:     */   {
/*  317: 594 */     ElemTemplateElement el = this;
/*  318: 595 */     int type = el.getXSLToken();
/*  319: 596 */     while ((null != el) && (type != 19))
/*  320:     */     {
/*  321: 598 */       el = el.getParentElem();
/*  322: 599 */       if (null != el) {
/*  323: 600 */         type = el.getXSLToken();
/*  324:     */       }
/*  325:     */     }
/*  326: 602 */     return (ElemTemplate)el;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public String getTagName()
/*  330:     */   {
/*  331: 613 */     return getNodeName();
/*  332:     */   }
/*  333:     */   
/*  334:     */   public boolean hasTextLitOnly()
/*  335:     */   {
/*  336: 622 */     return this.m_hasTextLitOnly;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public String getBaseIdentifier()
/*  340:     */   {
/*  341: 634 */     return getSystemId();
/*  342:     */   }
/*  343:     */   
/*  344:     */   public int getEndLineNumber()
/*  345:     */   {
/*  346: 654 */     return this.m_endLineNumber;
/*  347:     */   }
/*  348:     */   
/*  349:     */   public int getLineNumber()
/*  350:     */   {
/*  351: 666 */     return this.m_lineNumber;
/*  352:     */   }
/*  353:     */   
/*  354:     */   public int getEndColumnNumber()
/*  355:     */   {
/*  356: 687 */     return this.m_endColumnNumber;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public int getColumnNumber()
/*  360:     */   {
/*  361: 700 */     return this.m_columnNumber;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public String getPublicId()
/*  365:     */   {
/*  366: 712 */     return null != this.m_parentNode ? this.m_parentNode.getPublicId() : null;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public String getSystemId()
/*  370:     */   {
/*  371: 727 */     Stylesheet sheet = getStylesheet();
/*  372: 728 */     return sheet == null ? null : sheet.getHref();
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void setLocaterInfo(SourceLocator locator)
/*  376:     */   {
/*  377: 738 */     this.m_lineNumber = locator.getLineNumber();
/*  378: 739 */     this.m_columnNumber = locator.getColumnNumber();
/*  379:     */   }
/*  380:     */   
/*  381:     */   public void setEndLocaterInfo(SourceLocator locator)
/*  382:     */   {
/*  383: 749 */     this.m_endLineNumber = locator.getLineNumber();
/*  384: 750 */     this.m_endColumnNumber = locator.getColumnNumber();
/*  385:     */   }
/*  386:     */   
/*  387: 758 */   private boolean m_defaultSpace = true;
/*  388: 764 */   private boolean m_hasTextLitOnly = false;
/*  389: 770 */   protected boolean m_hasVariableDecl = false;
/*  390:     */   private List m_declaredPrefixes;
/*  391:     */   private List m_prefixTable;
/*  392:     */   
/*  393:     */   public boolean hasVariableDecl()
/*  394:     */   {
/*  395: 774 */     return this.m_hasVariableDecl;
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void setXmlSpace(int v)
/*  399:     */   {
/*  400: 790 */     this.m_defaultSpace = (2 == v);
/*  401:     */   }
/*  402:     */   
/*  403:     */   public boolean getXmlSpace()
/*  404:     */   {
/*  405: 805 */     return this.m_defaultSpace;
/*  406:     */   }
/*  407:     */   
/*  408:     */   public List getDeclaredPrefixes()
/*  409:     */   {
/*  410: 823 */     return this.m_declaredPrefixes;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public void setPrefixes(NamespaceSupport nsSupport)
/*  414:     */     throws TransformerException
/*  415:     */   {
/*  416: 838 */     setPrefixes(nsSupport, false);
/*  417:     */   }
/*  418:     */   
/*  419:     */   public void setPrefixes(NamespaceSupport nsSupport, boolean excludeXSLDecl)
/*  420:     */     throws TransformerException
/*  421:     */   {
/*  422: 856 */     Enumeration decls = nsSupport.getDeclaredPrefixes();
/*  423: 858 */     while (decls.hasMoreElements())
/*  424:     */     {
/*  425: 860 */       String prefix = (String)decls.nextElement();
/*  426: 862 */       if (null == this.m_declaredPrefixes) {
/*  427: 863 */         this.m_declaredPrefixes = new ArrayList();
/*  428:     */       }
/*  429: 865 */       String uri = nsSupport.getURI(prefix);
/*  430: 867 */       if ((!excludeXSLDecl) || (!uri.equals("http://www.w3.org/1999/XSL/Transform")))
/*  431:     */       {
/*  432: 871 */         XMLNSDecl decl = new XMLNSDecl(prefix, uri, false);
/*  433:     */         
/*  434: 873 */         this.m_declaredPrefixes.add(decl);
/*  435:     */       }
/*  436:     */     }
/*  437:     */   }
/*  438:     */   
/*  439:     */   public String getNamespaceForPrefix(String prefix, Node context)
/*  440:     */   {
/*  441: 889 */     error("ER_CANT_RESOLVE_NSPREFIX", null);
/*  442:     */     
/*  443: 891 */     return null;
/*  444:     */   }
/*  445:     */   
/*  446:     */   public String getNamespaceForPrefix(String prefix)
/*  447:     */   {
/*  448: 915 */     List nsDecls = this.m_declaredPrefixes;
/*  449: 917 */     if (null != nsDecls)
/*  450:     */     {
/*  451: 919 */       int n = nsDecls.size();
/*  452: 920 */       if (prefix.equals("#default")) {
/*  453: 922 */         prefix = "";
/*  454:     */       }
/*  455: 925 */       for (int i = 0; i < n; i++)
/*  456:     */       {
/*  457: 927 */         XMLNSDecl decl = (XMLNSDecl)nsDecls.get(i);
/*  458: 929 */         if (prefix.equals(decl.getPrefix())) {
/*  459: 930 */           return decl.getURI();
/*  460:     */         }
/*  461:     */       }
/*  462:     */     }
/*  463: 935 */     if (null != this.m_parentNode) {
/*  464: 936 */       return this.m_parentNode.getNamespaceForPrefix(prefix);
/*  465:     */     }
/*  466: 941 */     if ("xml".equals(prefix)) {
/*  467: 942 */       return "http://www.w3.org/XML/1998/namespace";
/*  468:     */     }
/*  469: 945 */     return null;
/*  470:     */   }
/*  471:     */   
/*  472:     */   List getPrefixTable()
/*  473:     */   {
/*  474: 963 */     return this.m_prefixTable;
/*  475:     */   }
/*  476:     */   
/*  477:     */   void setPrefixTable(List list)
/*  478:     */   {
/*  479: 967 */     this.m_prefixTable = list;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public boolean containsExcludeResultPrefix(String prefix, String uri)
/*  483:     */   {
/*  484: 982 */     ElemTemplateElement parent = getParentElem();
/*  485: 983 */     if (null != parent) {
/*  486: 984 */       return parent.containsExcludeResultPrefix(prefix, uri);
/*  487:     */     }
/*  488: 986 */     return false;
/*  489:     */   }
/*  490:     */   
/*  491:     */   private boolean excludeResultNSDecl(String prefix, String uri)
/*  492:     */     throws TransformerException
/*  493:     */   {
/*  494:1005 */     if (uri != null)
/*  495:     */     {
/*  496:1007 */       if ((uri.equals("http://www.w3.org/1999/XSL/Transform")) || (getStylesheet().containsExtensionElementURI(uri))) {
/*  497:1009 */         return true;
/*  498:     */       }
/*  499:1011 */       if (containsExcludeResultPrefix(prefix, uri)) {
/*  500:1012 */         return true;
/*  501:     */       }
/*  502:     */     }
/*  503:1015 */     return false;
/*  504:     */   }
/*  505:     */   
/*  506:     */   public void resolvePrefixTables()
/*  507:     */     throws TransformerException
/*  508:     */   {
/*  509:1032 */     setPrefixTable(null);
/*  510:1038 */     if (null != this.m_declaredPrefixes)
/*  511:     */     {
/*  512:1040 */       StylesheetRoot stylesheet = getStylesheetRoot();
/*  513:     */       
/*  514:     */ 
/*  515:     */ 
/*  516:1044 */       int n = this.m_declaredPrefixes.size();
/*  517:1046 */       for (int i = 0; i < n; i++)
/*  518:     */       {
/*  519:1048 */         XMLNSDecl decl = (XMLNSDecl)this.m_declaredPrefixes.get(i);
/*  520:1049 */         String prefix = decl.getPrefix();
/*  521:1050 */         String uri = decl.getURI();
/*  522:1051 */         if (null == uri) {
/*  523:1052 */           uri = "";
/*  524:     */         }
/*  525:1053 */         boolean shouldExclude = excludeResultNSDecl(prefix, uri);
/*  526:1056 */         if (null == this.m_prefixTable) {
/*  527:1057 */           setPrefixTable(new ArrayList());
/*  528:     */         }
/*  529:1059 */         NamespaceAlias nsAlias = stylesheet.getNamespaceAliasComposed(uri);
/*  530:1060 */         if (null != nsAlias) {
/*  531:1068 */           decl = new XMLNSDecl(nsAlias.getStylesheetPrefix(), nsAlias.getResultNamespace(), shouldExclude);
/*  532:     */         } else {
/*  533:1072 */           decl = new XMLNSDecl(prefix, uri, shouldExclude);
/*  534:     */         }
/*  535:1074 */         this.m_prefixTable.add(decl);
/*  536:     */       }
/*  537:     */     }
/*  538:1079 */     ElemTemplateElement parent = getParentNodeElem();
/*  539:1081 */     if (null != parent)
/*  540:     */     {
/*  541:1085 */       List prefixes = parent.m_prefixTable;
/*  542:1087 */       if ((null == this.m_prefixTable) && (!needToCheckExclude()))
/*  543:     */       {
/*  544:1091 */         setPrefixTable(parent.m_prefixTable);
/*  545:     */       }
/*  546:     */       else
/*  547:     */       {
/*  548:1097 */         int n = prefixes.size();
/*  549:1099 */         for (int i = 0; i < n; i++)
/*  550:     */         {
/*  551:1101 */           XMLNSDecl decl = (XMLNSDecl)prefixes.get(i);
/*  552:1102 */           boolean shouldExclude = excludeResultNSDecl(decl.getPrefix(), decl.getURI());
/*  553:1105 */           if (shouldExclude != decl.getIsExcluded()) {
/*  554:1107 */             decl = new XMLNSDecl(decl.getPrefix(), decl.getURI(), shouldExclude);
/*  555:     */           }
/*  556:1112 */           addOrReplaceDecls(decl);
/*  557:     */         }
/*  558:     */       }
/*  559:     */     }
/*  560:1116 */     else if (null == this.m_prefixTable)
/*  561:     */     {
/*  562:1120 */       setPrefixTable(new ArrayList());
/*  563:     */     }
/*  564:     */   }
/*  565:     */   
/*  566:     */   void addOrReplaceDecls(XMLNSDecl newDecl)
/*  567:     */   {
/*  568:1132 */     int n = this.m_prefixTable.size();
/*  569:1134 */     for (int i = n - 1; i >= 0; i--)
/*  570:     */     {
/*  571:1136 */       XMLNSDecl decl = (XMLNSDecl)this.m_prefixTable.get(i);
/*  572:1138 */       if (decl.getPrefix().equals(newDecl.getPrefix())) {
/*  573:1140 */         return;
/*  574:     */       }
/*  575:     */     }
/*  576:1143 */     this.m_prefixTable.add(newDecl);
/*  577:     */   }
/*  578:     */   
/*  579:     */   boolean needToCheckExclude()
/*  580:     */   {
/*  581:1153 */     return false;
/*  582:     */   }
/*  583:     */   
/*  584:     */   void executeNSDecls(TransformerImpl transformer)
/*  585:     */     throws TransformerException
/*  586:     */   {
/*  587:1166 */     executeNSDecls(transformer, null);
/*  588:     */   }
/*  589:     */   
/*  590:     */   void executeNSDecls(TransformerImpl transformer, String ignorePrefix)
/*  591:     */     throws TransformerException
/*  592:     */   {
/*  593:     */     try
/*  594:     */     {
/*  595:1182 */       if (null != this.m_prefixTable)
/*  596:     */       {
/*  597:1184 */         SerializationHandler rhandler = transformer.getResultTreeHandler();
/*  598:1185 */         int n = this.m_prefixTable.size();
/*  599:1187 */         for (int i = n - 1; i >= 0; i--)
/*  600:     */         {
/*  601:1189 */           XMLNSDecl decl = (XMLNSDecl)this.m_prefixTable.get(i);
/*  602:1191 */           if ((!decl.getIsExcluded()) && ((null == ignorePrefix) || (!decl.getPrefix().equals(ignorePrefix)))) {
/*  603:1193 */             rhandler.startPrefixMapping(decl.getPrefix(), decl.getURI(), true);
/*  604:     */           }
/*  605:     */         }
/*  606:     */       }
/*  607:     */     }
/*  608:     */     catch (SAXException se)
/*  609:     */     {
/*  610:1200 */       throw new TransformerException(se);
/*  611:     */     }
/*  612:     */   }
/*  613:     */   
/*  614:     */   void unexecuteNSDecls(TransformerImpl transformer)
/*  615:     */     throws TransformerException
/*  616:     */   {
/*  617:1214 */     unexecuteNSDecls(transformer, null);
/*  618:     */   }
/*  619:     */   
/*  620:     */   void unexecuteNSDecls(TransformerImpl transformer, String ignorePrefix)
/*  621:     */     throws TransformerException
/*  622:     */   {
/*  623:     */     try
/*  624:     */     {
/*  625:1231 */       if (null != this.m_prefixTable)
/*  626:     */       {
/*  627:1233 */         SerializationHandler rhandler = transformer.getResultTreeHandler();
/*  628:1234 */         int n = this.m_prefixTable.size();
/*  629:1236 */         for (int i = 0; i < n; i++)
/*  630:     */         {
/*  631:1238 */           XMLNSDecl decl = (XMLNSDecl)this.m_prefixTable.get(i);
/*  632:1240 */           if ((!decl.getIsExcluded()) && ((null == ignorePrefix) || (!decl.getPrefix().equals(ignorePrefix)))) {
/*  633:1242 */             rhandler.endPrefixMapping(decl.getPrefix());
/*  634:     */           }
/*  635:     */         }
/*  636:     */       }
/*  637:     */     }
/*  638:     */     catch (SAXException se)
/*  639:     */     {
/*  640:1249 */       throw new TransformerException(se);
/*  641:     */     }
/*  642:     */   }
/*  643:     */   
/*  644:1255 */   protected int m_docOrderNumber = -1;
/*  645:     */   protected ElemTemplateElement m_parentNode;
/*  646:     */   ElemTemplateElement m_nextSibling;
/*  647:     */   ElemTemplateElement m_firstChild;
/*  648:     */   private transient Node m_DOMBackPointer;
/*  649:     */   
/*  650:     */   public void setUid(int i)
/*  651:     */   {
/*  652:1264 */     this.m_docOrderNumber = i;
/*  653:     */   }
/*  654:     */   
/*  655:     */   public int getUid()
/*  656:     */   {
/*  657:1274 */     return this.m_docOrderNumber;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public Node getParentNode()
/*  661:     */   {
/*  662:1291 */     return this.m_parentNode;
/*  663:     */   }
/*  664:     */   
/*  665:     */   public ElemTemplateElement getParentElem()
/*  666:     */   {
/*  667:1301 */     return this.m_parentNode;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public void setParentElem(ElemTemplateElement p)
/*  671:     */   {
/*  672:1311 */     this.m_parentNode = p;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public Node getNextSibling()
/*  676:     */   {
/*  677:1327 */     return this.m_nextSibling;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public Node getPreviousSibling()
/*  681:     */   {
/*  682:1342 */     Node walker = getParentNode();Node prev = null;
/*  683:1344 */     if (walker != null) {
/*  684:1345 */       for (walker = walker.getFirstChild(); walker != null; walker = walker.getNextSibling())
/*  685:     */       {
/*  686:1348 */         if (walker == this) {
/*  687:1349 */           return prev;
/*  688:     */         }
/*  689:1346 */         prev = walker;
/*  690:     */       }
/*  691:     */     }
/*  692:1352 */     return null;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public ElemTemplateElement getPreviousSiblingElem()
/*  696:     */   {
/*  697:1367 */     ElemTemplateElement walker = getParentNodeElem();
/*  698:1368 */     ElemTemplateElement prev = null;
/*  699:1370 */     if (walker != null) {
/*  700:1371 */       for (walker = walker.getFirstChildElem(); walker != null; walker = walker.getNextSiblingElem())
/*  701:     */       {
/*  702:1374 */         if (walker == this) {
/*  703:1375 */           return prev;
/*  704:     */         }
/*  705:1372 */         prev = walker;
/*  706:     */       }
/*  707:     */     }
/*  708:1378 */     return null;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public ElemTemplateElement getNextSiblingElem()
/*  712:     */   {
/*  713:1389 */     return this.m_nextSibling;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public ElemTemplateElement getParentNodeElem()
/*  717:     */   {
/*  718:1399 */     return this.m_parentNode;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public Node getFirstChild()
/*  722:     */   {
/*  723:1416 */     return this.m_firstChild;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public ElemTemplateElement getFirstChildElem()
/*  727:     */   {
/*  728:1426 */     return this.m_firstChild;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public Node getLastChild()
/*  732:     */   {
/*  733:1437 */     ElemTemplateElement lastChild = null;
/*  734:1439 */     for (ElemTemplateElement node = this.m_firstChild; node != null; node = node.m_nextSibling) {
/*  735:1442 */       lastChild = node;
/*  736:     */     }
/*  737:1445 */     return lastChild;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public ElemTemplateElement getLastChildElem()
/*  741:     */   {
/*  742:1456 */     ElemTemplateElement lastChild = null;
/*  743:1458 */     for (ElemTemplateElement node = this.m_firstChild; node != null; node = node.m_nextSibling) {
/*  744:1461 */       lastChild = node;
/*  745:     */     }
/*  746:1464 */     return lastChild;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public Node getDOMBackPointer()
/*  750:     */   {
/*  751:1480 */     return this.m_DOMBackPointer;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public void setDOMBackPointer(Node n)
/*  755:     */   {
/*  756:1492 */     this.m_DOMBackPointer = n;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public int compareTo(Object o)
/*  760:     */     throws ClassCastException
/*  761:     */   {
/*  762:1510 */     ElemTemplateElement ro = (ElemTemplateElement)o;
/*  763:1511 */     int roPrecedence = ro.getStylesheetComposed().getImportCountComposed();
/*  764:1512 */     int myPrecedence = getStylesheetComposed().getImportCountComposed();
/*  765:1514 */     if (myPrecedence < roPrecedence) {
/*  766:1515 */       return -1;
/*  767:     */     }
/*  768:1516 */     if (myPrecedence > roPrecedence) {
/*  769:1517 */       return 1;
/*  770:     */     }
/*  771:1519 */     return getUid() - ro.getUid();
/*  772:     */   }
/*  773:     */   
/*  774:     */   public boolean shouldStripWhiteSpace(XPathContext support, Element targetElement)
/*  775:     */     throws TransformerException
/*  776:     */   {
/*  777:1537 */     StylesheetRoot sroot = getStylesheetRoot();
/*  778:1538 */     return null != sroot ? sroot.shouldStripWhiteSpace(support, targetElement) : false;
/*  779:     */   }
/*  780:     */   
/*  781:     */   public boolean canStripWhiteSpace()
/*  782:     */   {
/*  783:1549 */     StylesheetRoot sroot = getStylesheetRoot();
/*  784:1550 */     return null != sroot ? sroot.canStripWhiteSpace() : false;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public boolean canAcceptVariables()
/*  788:     */   {
/*  789:1559 */     return true;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public void exprSetParent(ExpressionNode n)
/*  793:     */   {
/*  794:1572 */     setParentElem((ElemTemplateElement)n);
/*  795:     */   }
/*  796:     */   
/*  797:     */   public ExpressionNode exprGetParent()
/*  798:     */   {
/*  799:1580 */     return getParentElem();
/*  800:     */   }
/*  801:     */   
/*  802:     */   public void exprAddChild(ExpressionNode n, int i)
/*  803:     */   {
/*  804:1590 */     appendChild((ElemTemplateElement)n);
/*  805:     */   }
/*  806:     */   
/*  807:     */   public ExpressionNode exprGetChild(int i)
/*  808:     */   {
/*  809:1597 */     return (ExpressionNode)item(i);
/*  810:     */   }
/*  811:     */   
/*  812:     */   public int exprGetNumChildren()
/*  813:     */   {
/*  814:1603 */     return getLength();
/*  815:     */   }
/*  816:     */   
/*  817:     */   protected boolean accept(XSLTVisitor visitor)
/*  818:     */   {
/*  819:1615 */     return visitor.visitInstruction(this);
/*  820:     */   }
/*  821:     */   
/*  822:     */   public void callVisitors(XSLTVisitor visitor)
/*  823:     */   {
/*  824:1623 */     if (accept(visitor)) {
/*  825:1625 */       callChildVisitors(visitor);
/*  826:     */     }
/*  827:     */   }
/*  828:     */   
/*  829:     */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttributes)
/*  830:     */   {
/*  831:1635 */     for (ElemTemplateElement node = this.m_firstChild; node != null; node = node.m_nextSibling) {
/*  832:1639 */       node.callVisitors(visitor);
/*  833:     */     }
/*  834:     */   }
/*  835:     */   
/*  836:     */   protected void callChildVisitors(XSLTVisitor visitor)
/*  837:     */   {
/*  838:1649 */     callChildVisitors(visitor, true);
/*  839:     */   }
/*  840:     */   
/*  841:     */   public boolean handlesNullPrefixes()
/*  842:     */   {
/*  843:1657 */     return false;
/*  844:     */   }
/*  845:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemTemplateElement
 * JD-Core Version:    0.7.0.1
 */