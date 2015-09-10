/*    1:     */ package com.gargoylesoftware.htmlunit.html;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    5:     */ import com.gargoylesoftware.htmlunit.IncorrectnessListener;
/*    6:     */ import com.gargoylesoftware.htmlunit.Page;
/*    7:     */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*    8:     */ import com.gargoylesoftware.htmlunit.WebAssert;
/*    9:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*   10:     */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   11:     */ import com.gargoylesoftware.htmlunit.html.xpath.XPathUtils;
/*   12:     */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   13:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleDeclaration;
/*   14:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet;
/*   15:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   16:     */ import com.steadystate.css.parser.CSSOMParser;
/*   17:     */ import com.steadystate.css.parser.SACParserCSS21;
/*   18:     */ import java.io.PrintWriter;
/*   19:     */ import java.io.Serializable;
/*   20:     */ import java.io.StringReader;
/*   21:     */ import java.io.StringWriter;
/*   22:     */ import java.util.ArrayList;
/*   23:     */ import java.util.Iterator;
/*   24:     */ import java.util.List;
/*   25:     */ import java.util.NoSuchElementException;
/*   26:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   27:     */ import org.apache.commons.lang.NotImplementedException;
/*   28:     */ import org.apache.commons.logging.Log;
/*   29:     */ import org.apache.commons.logging.LogFactory;
/*   30:     */ import org.w3c.css.sac.ErrorHandler;
/*   31:     */ import org.w3c.css.sac.InputSource;
/*   32:     */ import org.w3c.css.sac.Selector;
/*   33:     */ import org.w3c.css.sac.SelectorList;
/*   34:     */ import org.w3c.dom.DOMException;
/*   35:     */ import org.w3c.dom.Document;
/*   36:     */ import org.w3c.dom.NamedNodeMap;
/*   37:     */ import org.w3c.dom.Node;
/*   38:     */ import org.w3c.dom.UserDataHandler;
/*   39:     */ 
/*   40:     */ public abstract class DomNode
/*   41:     */   implements Cloneable, Serializable, Node
/*   42:     */ {
/*   43:  77 */   private static final Log LOG = LogFactory.getLog(DomNode.class);
/*   44:     */   protected static final String AS_TEXT_BLOCK_SEPARATOR = "§bs§";
/*   45:     */   protected static final String AS_TEXT_NEW_LINE = "§nl§";
/*   46:     */   protected static final String AS_TEXT_BLANK = "§blank§";
/*   47:     */   protected static final String AS_TEXT_TAB = "§tab§";
/*   48:     */   public static final String READY_STATE_UNINITIALIZED = "uninitialized";
/*   49:     */   public static final String READY_STATE_LOADING = "loading";
/*   50:     */   public static final String READY_STATE_LOADED = "loaded";
/*   51:     */   public static final String READY_STATE_INTERACTIVE = "interactive";
/*   52:     */   public static final String READY_STATE_COMPLETE = "complete";
/*   53:     */   public static final String PROPERTY_ELEMENT = "element";
/*   54:     */   private SgmlPage page_;
/*   55:     */   private DomNode parent_;
/*   56:     */   private DomNode previousSibling_;
/*   57:     */   private DomNode nextSibling_;
/*   58:     */   private DomNode firstChild_;
/*   59:     */   private ScriptableObject scriptObject_;
/*   60:     */   private String readyState_;
/*   61: 138 */   private int startLineNumber_ = -1;
/*   62: 143 */   private int startColumnNumber_ = -1;
/*   63: 148 */   private int endLineNumber_ = -1;
/*   64: 153 */   private int endColumnNumber_ = -1;
/*   65:     */   private boolean directlyAttachedToPage_;
/*   66:     */   private List<DomChangeListener> domListeners_;
/*   67: 158 */   private final Object domListeners_lock_ = new Serializable() {};
/*   68:     */   
/*   69:     */   @Deprecated
/*   70:     */   protected DomNode()
/*   71:     */   {
/*   72: 165 */     this(null);
/*   73:     */   }
/*   74:     */   
/*   75:     */   protected DomNode(SgmlPage page)
/*   76:     */   {
/*   77: 173 */     this.readyState_ = "loading";
/*   78: 174 */     this.page_ = page;
/*   79:     */   }
/*   80:     */   
/*   81:     */   void setStartLocation(int startLineNumber, int startColumnNumber)
/*   82:     */   {
/*   83: 184 */     this.startLineNumber_ = startLineNumber;
/*   84: 185 */     this.startColumnNumber_ = startColumnNumber;
/*   85:     */   }
/*   86:     */   
/*   87:     */   void setEndLocation(int endLineNumber, int endColumnNumber)
/*   88:     */   {
/*   89: 195 */     this.endLineNumber_ = endLineNumber;
/*   90: 196 */     this.endColumnNumber_ = endColumnNumber;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public int getStartLineNumber()
/*   94:     */   {
/*   95: 204 */     return this.startLineNumber_;
/*   96:     */   }
/*   97:     */   
/*   98:     */   public int getStartColumnNumber()
/*   99:     */   {
/*  100: 212 */     return this.startColumnNumber_;
/*  101:     */   }
/*  102:     */   
/*  103:     */   public int getEndLineNumber()
/*  104:     */   {
/*  105: 221 */     return this.endLineNumber_;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public int getEndColumnNumber()
/*  109:     */   {
/*  110: 230 */     return this.endColumnNumber_;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public SgmlPage getPage()
/*  114:     */   {
/*  115: 238 */     return this.page_;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public Document getOwnerDocument()
/*  119:     */   {
/*  120: 245 */     return getPage();
/*  121:     */   }
/*  122:     */   
/*  123:     */   public void setScriptObject(ScriptableObject scriptObject)
/*  124:     */   {
/*  125: 257 */     this.scriptObject_ = scriptObject;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public DomNode getLastChild()
/*  129:     */   {
/*  130: 264 */     if (this.firstChild_ != null) {
/*  131: 266 */       return this.firstChild_.previousSibling_;
/*  132:     */     }
/*  133: 268 */     return null;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public DomNode getParentNode()
/*  137:     */   {
/*  138: 275 */     return this.parent_;
/*  139:     */   }
/*  140:     */   
/*  141:     */   protected void setParentNode(DomNode parent)
/*  142:     */   {
/*  143: 283 */     this.parent_ = parent;
/*  144:     */   }
/*  145:     */   
/*  146:     */   public int getIndex()
/*  147:     */   {
/*  148: 291 */     int index = 0;
/*  149: 292 */     for (DomNode n = this.previousSibling_; (n != null) && (n.nextSibling_ != null); n = n.previousSibling_) {
/*  150: 293 */       index++;
/*  151:     */     }
/*  152: 295 */     return index;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public DomNode getPreviousSibling()
/*  156:     */   {
/*  157: 302 */     if ((this.parent_ == null) || (this == this.parent_.firstChild_)) {
/*  158: 304 */       return null;
/*  159:     */     }
/*  160: 306 */     return this.previousSibling_;
/*  161:     */   }
/*  162:     */   
/*  163:     */   public DomNode getNextSibling()
/*  164:     */   {
/*  165: 313 */     return this.nextSibling_;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public DomNode getFirstChild()
/*  169:     */   {
/*  170: 320 */     return this.firstChild_;
/*  171:     */   }
/*  172:     */   
/*  173:     */   public boolean isAncestorOf(DomNode node)
/*  174:     */   {
/*  175: 330 */     while (node != null)
/*  176:     */     {
/*  177: 331 */       if (node == this) {
/*  178: 332 */         return true;
/*  179:     */       }
/*  180: 334 */       node = node.getParentNode();
/*  181:     */     }
/*  182: 336 */     return false;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public boolean isAncestorOfAny(DomNode... nodes)
/*  186:     */   {
/*  187: 346 */     for (DomNode node : nodes) {
/*  188: 347 */       if (isAncestorOf(node)) {
/*  189: 348 */         return true;
/*  190:     */       }
/*  191:     */     }
/*  192: 351 */     return false;
/*  193:     */   }
/*  194:     */   
/*  195:     */   protected void setPreviousSibling(DomNode previous)
/*  196:     */   {
/*  197: 356 */     this.previousSibling_ = previous;
/*  198:     */   }
/*  199:     */   
/*  200:     */   protected void setNextSibling(DomNode next)
/*  201:     */   {
/*  202: 361 */     this.nextSibling_ = next;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public abstract short getNodeType();
/*  206:     */   
/*  207:     */   public abstract String getNodeName();
/*  208:     */   
/*  209:     */   public String getNamespaceURI()
/*  210:     */   {
/*  211: 380 */     return null;
/*  212:     */   }
/*  213:     */   
/*  214:     */   public String getLocalName()
/*  215:     */   {
/*  216: 387 */     return null;
/*  217:     */   }
/*  218:     */   
/*  219:     */   public String getPrefix()
/*  220:     */   {
/*  221: 394 */     return null;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public void setPrefix(String prefix) {}
/*  225:     */   
/*  226:     */   public boolean hasChildNodes()
/*  227:     */   {
/*  228: 408 */     return this.firstChild_ != null;
/*  229:     */   }
/*  230:     */   
/*  231:     */   public DomNodeList<DomNode> getChildNodes()
/*  232:     */   {
/*  233: 415 */     return new SiblingDomNodeList(this);
/*  234:     */   }
/*  235:     */   
/*  236:     */   public boolean isSupported(String namespace, String featureName)
/*  237:     */   {
/*  238: 423 */     throw new UnsupportedOperationException("DomNode.isSupported is not yet implemented.");
/*  239:     */   }
/*  240:     */   
/*  241:     */   public void normalize()
/*  242:     */   {
/*  243: 430 */     for (DomNode child = getFirstChild(); child != null; child = child.getNextSibling()) {
/*  244: 431 */       if ((child instanceof DomText))
/*  245:     */       {
/*  246: 432 */         boolean removeChildTextNodes = getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.DOM_NORMALIZE_REMOVE_CHILDREN);
/*  247:     */         
/*  248: 434 */         StringBuilder dataBuilder = new StringBuilder();
/*  249: 435 */         DomNode toRemove = child;
/*  250: 436 */         DomText firstText = null;
/*  251: 438 */         while (((toRemove instanceof DomText)) && (!(toRemove instanceof DomCDataSection)))
/*  252:     */         {
/*  253: 439 */           DomNode nextChild = toRemove.getNextSibling();
/*  254: 440 */           dataBuilder.append(toRemove.getTextContent());
/*  255: 441 */           if ((removeChildTextNodes) || (firstText != null)) {
/*  256: 442 */             toRemove.remove();
/*  257:     */           }
/*  258: 444 */           if (firstText == null) {
/*  259: 445 */             firstText = (DomText)toRemove;
/*  260:     */           }
/*  261: 447 */           toRemove = nextChild;
/*  262:     */         }
/*  263: 449 */         if (firstText != null) {
/*  264: 450 */           if (removeChildTextNodes)
/*  265:     */           {
/*  266: 451 */             DomText newText = new DomText(getPage(), dataBuilder.toString());
/*  267: 452 */             insertBefore(newText, toRemove);
/*  268:     */           }
/*  269:     */           else
/*  270:     */           {
/*  271: 455 */             firstText.setData(dataBuilder.toString());
/*  272:     */           }
/*  273:     */         }
/*  274:     */       }
/*  275:     */     }
/*  276:     */   }
/*  277:     */   
/*  278:     */   public String getBaseURI()
/*  279:     */   {
/*  280: 467 */     throw new UnsupportedOperationException("DomNode.getBaseURI is not yet implemented.");
/*  281:     */   }
/*  282:     */   
/*  283:     */   public short compareDocumentPosition(Node other)
/*  284:     */   {
/*  285: 474 */     if (other == this) {
/*  286: 475 */       return 0;
/*  287:     */     }
/*  288: 479 */     List<Node> myAncestors = getAncestors(true);
/*  289: 480 */     List<Node> otherAncestors = ((DomNode)other).getAncestors(true);
/*  290:     */     
/*  291: 482 */     int max = Math.min(myAncestors.size(), otherAncestors.size());
/*  292:     */     
/*  293: 484 */     int i = 1;
/*  294: 485 */     while ((i < max) && (myAncestors.get(i) == otherAncestors.get(i))) {
/*  295: 486 */       i++;
/*  296:     */     }
/*  297: 489 */     if ((i != 1) && (i == max))
/*  298:     */     {
/*  299: 490 */       if (myAncestors.size() == max) {
/*  300: 491 */         return 20;
/*  301:     */       }
/*  302: 493 */       return 10;
/*  303:     */     }
/*  304: 496 */     if (max == 1)
/*  305:     */     {
/*  306: 497 */       if (myAncestors.contains(other)) {
/*  307: 498 */         return 8;
/*  308:     */       }
/*  309: 500 */       if (otherAncestors.contains(this)) {
/*  310: 501 */         return 20;
/*  311:     */       }
/*  312: 503 */       return 33;
/*  313:     */     }
/*  314: 507 */     Node myAncestor = (Node)myAncestors.get(i);
/*  315: 508 */     Node otherAncestor = (Node)otherAncestors.get(i);
/*  316: 509 */     Node node = myAncestor;
/*  317: 510 */     while ((node != otherAncestor) && (node != null)) {
/*  318: 511 */       node = node.getPreviousSibling();
/*  319:     */     }
/*  320: 513 */     if (node == null) {
/*  321: 514 */       return 4;
/*  322:     */     }
/*  323: 516 */     return 2;
/*  324:     */   }
/*  325:     */   
/*  326:     */   protected List<Node> getAncestors(boolean includeSelf)
/*  327:     */   {
/*  328: 525 */     List<Node> list = new ArrayList();
/*  329: 526 */     if (includeSelf) {
/*  330: 527 */       list.add(this);
/*  331:     */     }
/*  332: 529 */     Node node = getParentNode();
/*  333: 530 */     while (node != null)
/*  334:     */     {
/*  335: 531 */       list.add(0, node);
/*  336: 532 */       node = node.getParentNode();
/*  337:     */     }
/*  338: 534 */     return list;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public String getTextContent()
/*  342:     */   {
/*  343: 541 */     switch (getNodeType())
/*  344:     */     {
/*  345:     */     case 1: 
/*  346:     */     case 2: 
/*  347:     */     case 5: 
/*  348:     */     case 6: 
/*  349:     */     case 11: 
/*  350: 547 */       StringBuilder builder = new StringBuilder();
/*  351: 548 */       for (DomNode child : getChildren())
/*  352:     */       {
/*  353: 549 */         short childType = child.getNodeType();
/*  354: 550 */         if ((childType != 8) && (childType != 7)) {
/*  355: 551 */           builder.append(child.getTextContent());
/*  356:     */         }
/*  357:     */       }
/*  358: 554 */       return builder.toString();
/*  359:     */     case 3: 
/*  360:     */     case 4: 
/*  361:     */     case 7: 
/*  362:     */     case 8: 
/*  363: 560 */       return getNodeValue();
/*  364:     */     }
/*  365: 563 */     return null;
/*  366:     */   }
/*  367:     */   
/*  368:     */   public void setTextContent(String textContent)
/*  369:     */   {
/*  370: 571 */     removeAllChildren();
/*  371: 572 */     if (textContent != null) {
/*  372: 573 */       appendChild(new DomText(getPage(), textContent));
/*  373:     */     }
/*  374:     */   }
/*  375:     */   
/*  376:     */   public boolean isSameNode(Node other)
/*  377:     */   {
/*  378: 581 */     return other == this;
/*  379:     */   }
/*  380:     */   
/*  381:     */   public String lookupPrefix(String namespaceURI)
/*  382:     */   {
/*  383: 589 */     throw new UnsupportedOperationException("DomNode.lookupPrefix is not yet implemented.");
/*  384:     */   }
/*  385:     */   
/*  386:     */   public boolean isDefaultNamespace(String namespaceURI)
/*  387:     */   {
/*  388: 597 */     throw new UnsupportedOperationException("DomNode.isDefaultNamespace is not yet implemented.");
/*  389:     */   }
/*  390:     */   
/*  391:     */   public String lookupNamespaceURI(String prefix)
/*  392:     */   {
/*  393: 605 */     throw new UnsupportedOperationException("DomNode.lookupNamespaceURI is not yet implemented.");
/*  394:     */   }
/*  395:     */   
/*  396:     */   public boolean isEqualNode(Node arg)
/*  397:     */   {
/*  398: 613 */     throw new UnsupportedOperationException("DomNode.isEqualNode is not yet implemented.");
/*  399:     */   }
/*  400:     */   
/*  401:     */   public Object getFeature(String feature, String version)
/*  402:     */   {
/*  403: 621 */     throw new UnsupportedOperationException("DomNode.getFeature is not yet implemented.");
/*  404:     */   }
/*  405:     */   
/*  406:     */   public Object getUserData(String key)
/*  407:     */   {
/*  408: 629 */     throw new UnsupportedOperationException("DomNode.getUserData is not yet implemented.");
/*  409:     */   }
/*  410:     */   
/*  411:     */   public Object setUserData(String key, Object data, UserDataHandler handler)
/*  412:     */   {
/*  413: 637 */     throw new UnsupportedOperationException("DomNode.setUserData is not yet implemented.");
/*  414:     */   }
/*  415:     */   
/*  416:     */   public boolean hasAttributes()
/*  417:     */   {
/*  418: 644 */     return false;
/*  419:     */   }
/*  420:     */   
/*  421:     */   protected boolean isTrimmedText()
/*  422:     */   {
/*  423: 656 */     return true;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public boolean isDisplayed()
/*  427:     */   {
/*  428: 673 */     if (!mayBeDisplayed()) {
/*  429: 674 */       return false;
/*  430:     */     }
/*  431: 676 */     Page page = getPage();
/*  432: 677 */     if (((page instanceof HtmlPage)) && (page.getEnclosingWindow().getWebClient().isCssEnabled()))
/*  433:     */     {
/*  434: 680 */       for (Node node : getAncestors(true))
/*  435:     */       {
/*  436: 681 */         ScriptableObject scriptableObject = ((DomNode)node).getScriptObject();
/*  437: 682 */         if ((scriptableObject instanceof HTMLElement))
/*  438:     */         {
/*  439: 683 */           CSSStyleDeclaration style = ((HTMLElement)scriptableObject).jsxGet_currentStyle();
/*  440: 684 */           String display = style.jsxGet_display();
/*  441: 685 */           if ("none".equals(display)) {
/*  442: 686 */             return false;
/*  443:     */           }
/*  444:     */         }
/*  445:     */       }
/*  446: 692 */       boolean collapseInvisible = ((HtmlPage)page).getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.DISPLAYED_COLLAPSE);
/*  447:     */       
/*  448: 694 */       DomNode node = this;
/*  449:     */       do
/*  450:     */       {
/*  451: 696 */         ScriptableObject scriptableObject = node.getScriptObject();
/*  452: 697 */         if ((scriptableObject instanceof HTMLElement))
/*  453:     */         {
/*  454: 698 */           CSSStyleDeclaration style = ((HTMLElement)scriptableObject).jsxGet_currentStyle();
/*  455: 699 */           String visibility = style.jsxGet_visibility();
/*  456: 700 */           if (visibility.length() > 0)
/*  457:     */           {
/*  458: 701 */             if ("visible".equals(visibility)) {
/*  459: 702 */               return true;
/*  460:     */             }
/*  461: 704 */             if (("hidden".equals(visibility)) || ((collapseInvisible) && ("collapse".equals(visibility)))) {
/*  462: 705 */               return false;
/*  463:     */             }
/*  464:     */           }
/*  465:     */         }
/*  466: 709 */         node = node.getParentNode();
/*  467: 710 */       } while (node != null);
/*  468:     */     }
/*  469: 712 */     return true;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public boolean mayBeDisplayed()
/*  473:     */   {
/*  474: 722 */     return true;
/*  475:     */   }
/*  476:     */   
/*  477:     */   public String asText()
/*  478:     */   {
/*  479: 735 */     HtmlSerializer ser = new HtmlSerializer();
/*  480: 736 */     return ser.asText(this);
/*  481:     */   }
/*  482:     */   
/*  483:     */   protected boolean isBlock()
/*  484:     */   {
/*  485: 745 */     return false;
/*  486:     */   }
/*  487:     */   
/*  488:     */   public String asXml()
/*  489:     */   {
/*  490: 755 */     String charsetName = null;
/*  491: 756 */     if ((getPage() instanceof HtmlPage)) {
/*  492: 757 */       charsetName = ((HtmlPage)getPage()).getPageEncoding();
/*  493:     */     }
/*  494: 759 */     StringWriter stringWriter = new StringWriter();
/*  495: 760 */     PrintWriter printWriter = new PrintWriter(stringWriter);
/*  496: 761 */     if ((charsetName != null) && ((this instanceof HtmlHtml))) {
/*  497: 762 */       printWriter.println("<?xml version=\"1.0\" encoding=\"" + charsetName + "\"?>");
/*  498:     */     }
/*  499: 764 */     printXml("", printWriter);
/*  500: 765 */     printWriter.close();
/*  501: 766 */     return stringWriter.toString();
/*  502:     */   }
/*  503:     */   
/*  504:     */   protected void printXml(String indent, PrintWriter printWriter)
/*  505:     */   {
/*  506: 776 */     printWriter.println(indent + this);
/*  507: 777 */     printChildrenAsXml(indent, printWriter);
/*  508:     */   }
/*  509:     */   
/*  510:     */   protected void printChildrenAsXml(String indent, PrintWriter printWriter)
/*  511:     */   {
/*  512: 787 */     DomNode child = getFirstChild();
/*  513: 788 */     while (child != null)
/*  514:     */     {
/*  515: 789 */       child.printXml(indent + "  ", printWriter);
/*  516: 790 */       child = child.getNextSibling();
/*  517:     */     }
/*  518:     */   }
/*  519:     */   
/*  520:     */   public String getNodeValue()
/*  521:     */   {
/*  522: 798 */     return null;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public void setNodeValue(String value) {}
/*  526:     */   
/*  527:     */   public DomNode cloneNode(boolean deep)
/*  528:     */   {
/*  529:     */     DomNode newnode;
/*  530:     */     try
/*  531:     */     {
/*  532: 814 */       newnode = (DomNode)clone();
/*  533:     */     }
/*  534:     */     catch (CloneNotSupportedException e)
/*  535:     */     {
/*  536: 817 */       throw new IllegalStateException("Clone not supported for node [" + this + "]");
/*  537:     */     }
/*  538: 820 */     newnode.parent_ = null;
/*  539: 821 */     newnode.nextSibling_ = null;
/*  540: 822 */     newnode.previousSibling_ = null;
/*  541: 823 */     newnode.firstChild_ = null;
/*  542: 824 */     newnode.scriptObject_ = null;
/*  543: 827 */     if (deep) {
/*  544: 828 */       for (DomNode child = this.firstChild_; child != null; child = child.nextSibling_) {
/*  545: 829 */         newnode.appendChild(child.cloneNode(true));
/*  546:     */       }
/*  547:     */     }
/*  548: 832 */     return newnode;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public ScriptableObject getScriptObject()
/*  552:     */   {
/*  553: 846 */     if (this.scriptObject_ == null)
/*  554:     */     {
/*  555: 847 */       if (this == getPage()) {
/*  556: 848 */         throw new IllegalStateException("No script object associated with the Page");
/*  557:     */       }
/*  558: 850 */       this.scriptObject_ = ((SimpleScriptable)this.page_.getScriptObject()).makeScriptableFor(this);
/*  559:     */     }
/*  560: 852 */     return this.scriptObject_;
/*  561:     */   }
/*  562:     */   
/*  563:     */   public DomNode appendChild(Node node)
/*  564:     */   {
/*  565: 859 */     DomNode domNode = (DomNode)node;
/*  566: 861 */     if ((domNode instanceof DomDocumentFragment))
/*  567:     */     {
/*  568: 862 */       DomDocumentFragment fragment = (DomDocumentFragment)domNode;
/*  569: 863 */       for (DomNode child : fragment.getChildren()) {
/*  570: 864 */         appendChild(child);
/*  571:     */       }
/*  572:     */     }
/*  573:     */     else
/*  574:     */     {
/*  575: 869 */       if ((domNode != this) && (domNode.getParentNode() != null)) {
/*  576: 870 */         domNode.remove();
/*  577:     */       }
/*  578: 873 */       basicAppend(domNode);
/*  579:     */       
/*  580: 875 */       fireAddition(domNode);
/*  581:     */     }
/*  582: 878 */     return domNode;
/*  583:     */   }
/*  584:     */   
/*  585:     */   private void fireAddition(DomNode domNode)
/*  586:     */   {
/*  587: 882 */     boolean wasAlreadyAttached = domNode.isDirectlyAttachedToPage();
/*  588: 883 */     domNode.directlyAttachedToPage_ = isDirectlyAttachedToPage();
/*  589: 886 */     if ((!(this instanceof DomDocumentFragment)) && ((getPage() instanceof HtmlPage))) {
/*  590: 887 */       ((HtmlPage)getPage()).notifyNodeAdded(domNode);
/*  591:     */     }
/*  592: 891 */     if ((!domNode.isBodyParsed()) && (isDirectlyAttachedToPage()) && (!wasAlreadyAttached))
/*  593:     */     {
/*  594: 892 */       domNode.onAddedToPage();
/*  595: 893 */       for (DomNode child : domNode.getDescendants())
/*  596:     */       {
/*  597: 894 */         child.directlyAttachedToPage_ = true;
/*  598: 895 */         child.onAllChildrenAddedToPage(true);
/*  599:     */       }
/*  600: 897 */       domNode.onAllChildrenAddedToPage(true);
/*  601:     */     }
/*  602: 900 */     fireNodeAdded(this, domNode);
/*  603:     */   }
/*  604:     */   
/*  605:     */   private boolean isBodyParsed()
/*  606:     */   {
/*  607: 908 */     return (getStartLineNumber() != -1) && (getEndLineNumber() == -1);
/*  608:     */   }
/*  609:     */   
/*  610:     */   void quietlyRemoveAndMoveChildrenTo(DomNode destination)
/*  611:     */   {
/*  612: 919 */     if (destination.getPage() != getPage()) {
/*  613: 920 */       throw new RuntimeException("Cannot perform quiet move on nodes from different pages.");
/*  614:     */     }
/*  615: 922 */     for (DomNode child : getChildren())
/*  616:     */     {
/*  617: 923 */       child.basicRemove();
/*  618: 924 */       destination.basicAppend(child);
/*  619:     */     }
/*  620: 926 */     basicRemove();
/*  621:     */   }
/*  622:     */   
/*  623:     */   private void basicAppend(DomNode node)
/*  624:     */   {
/*  625: 936 */     node.setPage(getPage());
/*  626: 937 */     if (this.firstChild_ == null)
/*  627:     */     {
/*  628: 938 */       this.firstChild_ = node;
/*  629: 939 */       this.firstChild_.previousSibling_ = node;
/*  630:     */     }
/*  631:     */     else
/*  632:     */     {
/*  633: 942 */       DomNode last = getLastChild();
/*  634: 943 */       last.nextSibling_ = node;
/*  635: 944 */       node.previousSibling_ = last;
/*  636: 945 */       node.nextSibling_ = null;
/*  637: 946 */       this.firstChild_.previousSibling_ = node;
/*  638:     */     }
/*  639: 948 */     node.parent_ = this;
/*  640:     */   }
/*  641:     */   
/*  642:     */   protected void checkChildHierarchy(Node newChild)
/*  643:     */     throws DOMException
/*  644:     */   {
/*  645: 964 */     Node parentNode = this;
/*  646: 965 */     while (parentNode != null)
/*  647:     */     {
/*  648: 966 */       if (parentNode == newChild) {
/*  649: 967 */         throw new DOMException((short)3, "Child node is already a parent.");
/*  650:     */       }
/*  651: 969 */       parentNode = parentNode.getParentNode();
/*  652:     */     }
/*  653: 971 */     Document thisDocument = getOwnerDocument();
/*  654: 972 */     Document childDocument = newChild.getOwnerDocument();
/*  655: 973 */     if ((childDocument != thisDocument) && (childDocument != null)) {
/*  656: 974 */       throw new DOMException((short)4, "Child node " + newChild.getNodeName() + " is not in the same Document as this " + getNodeName() + ".");
/*  657:     */     }
/*  658:     */   }
/*  659:     */   
/*  660:     */   public Node insertBefore(Node newChild, Node refChild)
/*  661:     */   {
/*  662: 983 */     if (refChild == null)
/*  663:     */     {
/*  664: 984 */       appendChild(newChild);
/*  665:     */     }
/*  666:     */     else
/*  667:     */     {
/*  668: 987 */       if (refChild.getParentNode() != this) {
/*  669: 988 */         throw new DOMException((short)8, "Reference node is not a child of this node.");
/*  670:     */       }
/*  671: 990 */       ((DomNode)refChild).insertBefore((DomNode)newChild);
/*  672:     */     }
/*  673: 992 */     return null;
/*  674:     */   }
/*  675:     */   
/*  676:     */   public void insertBefore(DomNode newNode)
/*  677:     */     throws IllegalStateException
/*  678:     */   {
/*  679:1003 */     if (this.previousSibling_ == null) {
/*  680:1004 */       throw new IllegalStateException("Previous sibling for " + this + " is null.");
/*  681:     */     }
/*  682:1007 */     if (newNode == this) {
/*  683:1008 */       return;
/*  684:     */     }
/*  685:1012 */     DomNode exParent = newNode.getParentNode();
/*  686:1013 */     newNode.basicRemove();
/*  687:1015 */     if (this.parent_.firstChild_ == this) {
/*  688:1016 */       this.parent_.firstChild_ = newNode;
/*  689:     */     } else {
/*  690:1019 */       this.previousSibling_.nextSibling_ = newNode;
/*  691:     */     }
/*  692:1021 */     newNode.previousSibling_ = this.previousSibling_;
/*  693:1022 */     newNode.nextSibling_ = this;
/*  694:1023 */     this.previousSibling_ = newNode;
/*  695:1024 */     newNode.parent_ = this.parent_;
/*  696:1025 */     newNode.setPage(this.page_);
/*  697:     */     
/*  698:1027 */     fireAddition(newNode);
/*  699:1029 */     if (exParent != null)
/*  700:     */     {
/*  701:1030 */       fireNodeDeleted(exParent, newNode);
/*  702:1031 */       exParent.fireNodeDeleted(exParent, this);
/*  703:     */     }
/*  704:     */   }
/*  705:     */   
/*  706:     */   private void setPage(SgmlPage newPage)
/*  707:     */   {
/*  708:1040 */     if (this.page_ == newPage) {
/*  709:1041 */       return;
/*  710:     */     }
/*  711:1044 */     this.page_ = newPage;
/*  712:1045 */     for (DomNode node : getChildren()) {
/*  713:1046 */       node.setPage(newPage);
/*  714:     */     }
/*  715:     */   }
/*  716:     */   
/*  717:     */   public NamedNodeMap getAttributes()
/*  718:     */   {
/*  719:1054 */     return NamedAttrNodeMapImpl.EMPTY_MAP;
/*  720:     */   }
/*  721:     */   
/*  722:     */   public Node removeChild(Node child)
/*  723:     */   {
/*  724:1061 */     if (child.getParentNode() != this) {
/*  725:1062 */       throw new DOMException((short)8, "Node is not a child of this node.");
/*  726:     */     }
/*  727:1064 */     ((DomNode)child).remove();
/*  728:1065 */     return child;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void remove()
/*  732:     */   {
/*  733:1072 */     DomNode exParent = this.parent_;
/*  734:1073 */     basicRemove();
/*  735:1075 */     if ((getPage() instanceof HtmlPage)) {
/*  736:1076 */       ((HtmlPage)getPage()).notifyNodeRemoved(this);
/*  737:     */     }
/*  738:1079 */     if (exParent != null)
/*  739:     */     {
/*  740:1080 */       fireNodeDeleted(exParent, this);
/*  741:     */       
/*  742:1082 */       exParent.fireNodeDeleted(exParent, this);
/*  743:     */     }
/*  744:     */   }
/*  745:     */   
/*  746:     */   private void basicRemove()
/*  747:     */   {
/*  748:1090 */     if ((this.parent_ != null) && (this.parent_.firstChild_ == this)) {
/*  749:1091 */       this.parent_.firstChild_ = this.nextSibling_;
/*  750:1093 */     } else if ((this.previousSibling_ != null) && (this.previousSibling_.nextSibling_ == this)) {
/*  751:1094 */       this.previousSibling_.nextSibling_ = this.nextSibling_;
/*  752:     */     }
/*  753:1096 */     if ((this.nextSibling_ != null) && (this.nextSibling_.previousSibling_ == this)) {
/*  754:1097 */       this.nextSibling_.previousSibling_ = this.previousSibling_;
/*  755:     */     }
/*  756:1099 */     if ((this.parent_ != null) && (this == this.parent_.getLastChild())) {
/*  757:1100 */       this.parent_.firstChild_.previousSibling_ = this.previousSibling_;
/*  758:     */     }
/*  759:1103 */     this.nextSibling_ = null;
/*  760:1104 */     this.previousSibling_ = null;
/*  761:1105 */     this.parent_ = null;
/*  762:     */   }
/*  763:     */   
/*  764:     */   public Node replaceChild(Node newChild, Node oldChild)
/*  765:     */   {
/*  766:1112 */     if (oldChild.getParentNode() != this) {
/*  767:1113 */       throw new DOMException((short)8, "Node is not a child of this node.");
/*  768:     */     }
/*  769:1115 */     ((DomNode)oldChild).replace((DomNode)newChild);
/*  770:1116 */     return oldChild;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void replace(DomNode newNode)
/*  774:     */     throws IllegalStateException
/*  775:     */   {
/*  776:1126 */     if (newNode != this)
/*  777:     */     {
/*  778:1127 */       newNode.remove();
/*  779:1128 */       insertBefore(newNode);
/*  780:1129 */       remove();
/*  781:     */     }
/*  782:     */   }
/*  783:     */   
/*  784:     */   protected void onAddedToPage()
/*  785:     */   {
/*  786:1140 */     if (this.firstChild_ != null) {
/*  787:1141 */       for (DomNode child : getChildren()) {
/*  788:1142 */         child.onAddedToPage();
/*  789:     */       }
/*  790:     */     }
/*  791:     */   }
/*  792:     */   
/*  793:     */   protected void onAllChildrenAddedToPage(boolean postponed) {}
/*  794:     */   
/*  795:     */   public final Iterable<DomNode> getChildren()
/*  796:     */   {
/*  797:1163 */     new Iterable()
/*  798:     */     {
/*  799:     */       public Iterator<DomNode> iterator()
/*  800:     */       {
/*  801:1165 */         return new DomNode.ChildIterator(DomNode.this);
/*  802:     */       }
/*  803:     */     };
/*  804:     */   }
/*  805:     */   
/*  806:     */   protected class ChildIterator
/*  807:     */     implements Iterator<DomNode>
/*  808:     */   {
/*  809:1175 */     private DomNode nextNode_ = DomNode.this.firstChild_;
/*  810:1176 */     private DomNode currentNode_ = null;
/*  811:     */     
/*  812:     */     protected ChildIterator() {}
/*  813:     */     
/*  814:     */     public boolean hasNext()
/*  815:     */     {
/*  816:1180 */       return this.nextNode_ != null;
/*  817:     */     }
/*  818:     */     
/*  819:     */     public DomNode next()
/*  820:     */     {
/*  821:1185 */       if (this.nextNode_ != null)
/*  822:     */       {
/*  823:1186 */         this.currentNode_ = this.nextNode_;
/*  824:1187 */         this.nextNode_ = this.nextNode_.nextSibling_;
/*  825:1188 */         return this.currentNode_;
/*  826:     */       }
/*  827:1190 */       throw new NoSuchElementException();
/*  828:     */     }
/*  829:     */     
/*  830:     */     public void remove()
/*  831:     */     {
/*  832:1195 */       if (this.currentNode_ == null) {
/*  833:1196 */         throw new IllegalStateException();
/*  834:     */       }
/*  835:1198 */       this.currentNode_.remove();
/*  836:     */     }
/*  837:     */   }
/*  838:     */   
/*  839:     */   public final Iterable<DomNode> getDescendants()
/*  840:     */   {
/*  841:1209 */     new Iterable()
/*  842:     */     {
/*  843:     */       public Iterator<DomNode> iterator()
/*  844:     */       {
/*  845:1211 */         return new DomNode.DescendantElementsIterator(DomNode.this, DomNode.class);
/*  846:     */       }
/*  847:     */     };
/*  848:     */   }
/*  849:     */   
/*  850:     */   public final Iterable<HtmlElement> getHtmlElementDescendants()
/*  851:     */   {
/*  852:1224 */     new Iterable()
/*  853:     */     {
/*  854:     */       public Iterator<HtmlElement> iterator()
/*  855:     */       {
/*  856:1226 */         return new DomNode.DescendantElementsIterator(DomNode.this, HtmlElement.class);
/*  857:     */       }
/*  858:     */     };
/*  859:     */   }
/*  860:     */   
/*  861:     */   protected class DescendantElementsIterator<T extends DomNode>
/*  862:     */     implements Iterator<T>
/*  863:     */   {
/*  864:     */     private DomNode currentNode_;
/*  865:     */     private DomNode nextNode_;
/*  866:     */     private Class<T> type_;
/*  867:     */     
/*  868:     */     public DescendantElementsIterator()
/*  869:     */     {
/*  870:1246 */       this.type_ = type;
/*  871:1247 */       this.nextNode_ = getFirstChildElement(DomNode.this);
/*  872:     */     }
/*  873:     */     
/*  874:     */     public boolean hasNext()
/*  875:     */     {
/*  876:1252 */       return this.nextNode_ != null;
/*  877:     */     }
/*  878:     */     
/*  879:     */     public T next()
/*  880:     */     {
/*  881:1257 */       return nextNode();
/*  882:     */     }
/*  883:     */     
/*  884:     */     public void remove()
/*  885:     */     {
/*  886:1262 */       if (this.currentNode_ == null) {
/*  887:1263 */         throw new IllegalStateException("Unable to remove current node, because there is no current node.");
/*  888:     */       }
/*  889:1265 */       DomNode current = this.currentNode_;
/*  890:1266 */       while ((this.nextNode_ != null) && (current.isAncestorOf(this.nextNode_))) {
/*  891:1267 */         next();
/*  892:     */       }
/*  893:1269 */       current.remove();
/*  894:     */     }
/*  895:     */     
/*  896:     */     public T nextNode()
/*  897:     */     {
/*  898:1275 */       this.currentNode_ = this.nextNode_;
/*  899:1276 */       setNextElement();
/*  900:1277 */       return this.currentNode_;
/*  901:     */     }
/*  902:     */     
/*  903:     */     private void setNextElement()
/*  904:     */     {
/*  905:1281 */       DomNode next = getFirstChildElement(this.nextNode_);
/*  906:1282 */       if (next == null) {
/*  907:1283 */         next = getNextDomSibling(this.nextNode_);
/*  908:     */       }
/*  909:1285 */       if (next == null) {
/*  910:1286 */         next = getNextElementUpwards(this.nextNode_);
/*  911:     */       }
/*  912:1288 */       this.nextNode_ = next;
/*  913:     */     }
/*  914:     */     
/*  915:     */     private DomNode getNextElementUpwards(DomNode startingNode)
/*  916:     */     {
/*  917:1292 */       if (startingNode == DomNode.this) {
/*  918:1293 */         return null;
/*  919:     */       }
/*  920:1295 */       DomNode parent = startingNode.getParentNode();
/*  921:1296 */       if (parent == DomNode.this) {
/*  922:1297 */         return null;
/*  923:     */       }
/*  924:1299 */       DomNode next = parent.getNextSibling();
/*  925:1300 */       while ((next != null) && (!this.type_.isAssignableFrom(next.getClass()))) {
/*  926:1301 */         next = next.getNextSibling();
/*  927:     */       }
/*  928:1303 */       if (next == null) {
/*  929:1304 */         return getNextElementUpwards(parent);
/*  930:     */       }
/*  931:1306 */       return next;
/*  932:     */     }
/*  933:     */     
/*  934:     */     private DomNode getFirstChildElement(DomNode parent)
/*  935:     */     {
/*  936:1310 */       DomNode node = parent.getFirstChild();
/*  937:1311 */       while ((node != null) && (!this.type_.isAssignableFrom(node.getClass()))) {
/*  938:1312 */         node = node.getNextSibling();
/*  939:     */       }
/*  940:1314 */       return node;
/*  941:     */     }
/*  942:     */     
/*  943:     */     private DomNode getNextDomSibling(DomNode element)
/*  944:     */     {
/*  945:1318 */       DomNode node = element.getNextSibling();
/*  946:1319 */       while ((node != null) && (!this.type_.isAssignableFrom(node.getClass()))) {
/*  947:1320 */         node = node.getNextSibling();
/*  948:     */       }
/*  949:1322 */       return node;
/*  950:     */     }
/*  951:     */   }
/*  952:     */   
/*  953:     */   public String getReadyState()
/*  954:     */   {
/*  955:1331 */     return this.readyState_;
/*  956:     */   }
/*  957:     */   
/*  958:     */   public void setReadyState(String state)
/*  959:     */   {
/*  960:1339 */     this.readyState_ = state;
/*  961:     */   }
/*  962:     */   
/*  963:     */   public void removeAllChildren()
/*  964:     */   {
/*  965:1346 */     if (getFirstChild() == null) {
/*  966:1347 */       return;
/*  967:     */     }
/*  968:1350 */     for (Iterator<DomNode> it = getChildren().iterator(); it.hasNext();)
/*  969:     */     {
/*  970:1351 */       ((DomNode)it.next()).removeAllChildren();
/*  971:1352 */       it.remove();
/*  972:     */     }
/*  973:     */   }
/*  974:     */   
/*  975:     */   public List<?> getByXPath(String xpathExpr)
/*  976:     */   {
/*  977:1365 */     return XPathUtils.getByXPath(this, xpathExpr);
/*  978:     */   }
/*  979:     */   
/*  980:     */   public <X> X getFirstByXPath(String xpathExpr)
/*  981:     */   {
/*  982:1380 */     List<?> results = getByXPath(xpathExpr);
/*  983:1381 */     if (results.isEmpty()) {
/*  984:1382 */       return null;
/*  985:     */     }
/*  986:1384 */     return results.get(0);
/*  987:     */   }
/*  988:     */   
/*  989:     */   public String getCanonicalXPath()
/*  990:     */   {
/*  991:1399 */     throw new NotImplementedException("Not implemented for nodes of type " + getNodeType());
/*  992:     */   }
/*  993:     */   
/*  994:     */   protected void notifyIncorrectness(String message)
/*  995:     */   {
/*  996:1407 */     WebClient client = getPage().getEnclosingWindow().getWebClient();
/*  997:1408 */     IncorrectnessListener incorrectnessListener = client.getIncorrectnessListener();
/*  998:1409 */     incorrectnessListener.notify(message, this);
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public void addDomChangeListener(DomChangeListener listener)
/* 1002:     */   {
/* 1003:1420 */     WebAssert.notNull("listener", listener);
/* 1004:1421 */     synchronized (this.domListeners_lock_)
/* 1005:     */     {
/* 1006:1422 */       if (this.domListeners_ == null) {
/* 1007:1423 */         this.domListeners_ = new ArrayList();
/* 1008:     */       }
/* 1009:1425 */       if (!this.domListeners_.contains(listener)) {
/* 1010:1426 */         this.domListeners_.add(listener);
/* 1011:     */       }
/* 1012:     */     }
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public void removeDomChangeListener(DomChangeListener listener)
/* 1016:     */   {
/* 1017:1439 */     WebAssert.notNull("listener", listener);
/* 1018:1440 */     synchronized (this.domListeners_lock_)
/* 1019:     */     {
/* 1020:1441 */       if (this.domListeners_ != null) {
/* 1021:1442 */         this.domListeners_.remove(listener);
/* 1022:     */       }
/* 1023:     */     }
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   protected void fireNodeAdded(DomNode parentNode, DomNode addedNode)
/* 1027:     */   {
/* 1028:1457 */     List<DomChangeListener> listeners = safeGetDomListeners();
/* 1029:     */     DomChangeEvent event;
/* 1030:1458 */     if (listeners != null)
/* 1031:     */     {
/* 1032:1459 */       event = new DomChangeEvent(parentNode, addedNode);
/* 1033:1460 */       for (DomChangeListener listener : listeners) {
/* 1034:1461 */         listener.nodeAdded(event);
/* 1035:     */       }
/* 1036:     */     }
/* 1037:1464 */     if (this.parent_ != null) {
/* 1038:1465 */       this.parent_.fireNodeAdded(parentNode, addedNode);
/* 1039:     */     }
/* 1040:     */   }
/* 1041:     */   
/* 1042:     */   protected void fireNodeDeleted(DomNode parentNode, DomNode deletedNode)
/* 1043:     */   {
/* 1044:1479 */     List<DomChangeListener> listeners = safeGetDomListeners();
/* 1045:     */     DomChangeEvent event;
/* 1046:1480 */     if (listeners != null)
/* 1047:     */     {
/* 1048:1481 */       event = new DomChangeEvent(parentNode, deletedNode);
/* 1049:1482 */       for (DomChangeListener listener : listeners) {
/* 1050:1483 */         listener.nodeDeleted(event);
/* 1051:     */       }
/* 1052:     */     }
/* 1053:1486 */     if (this.parent_ != null) {
/* 1054:1487 */       this.parent_.fireNodeDeleted(parentNode, deletedNode);
/* 1055:     */     }
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   private List<DomChangeListener> safeGetDomListeners()
/* 1059:     */   {
/* 1060:1492 */     synchronized (this.domListeners_lock_)
/* 1061:     */     {
/* 1062:1493 */       if (this.domListeners_ != null) {
/* 1063:1494 */         return new ArrayList(this.domListeners_);
/* 1064:     */       }
/* 1065:1496 */       return null;
/* 1066:     */     }
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   protected DomNodeList<DomNode> querySelectorAll(String selectors)
/* 1070:     */   {
/* 1071:1507 */     List<DomNode> elements = new ArrayList();
/* 1072:     */     try
/* 1073:     */     {
/* 1074:1509 */       WebClient webClient = getPage().getWebClient();
/* 1075:1510 */       ErrorHandler errorHandler = webClient.getCssErrorHandler();
/* 1076:1511 */       CSSOMParser parser = new CSSOMParser(new SACParserCSS21());
/* 1077:1512 */       parser.setErrorHandler(errorHandler);
/* 1078:1513 */       selectorList = parser.parseSelectors(new InputSource(new StringReader(selectors)));
/* 1079:1515 */       if (null != selectorList)
/* 1080:     */       {
/* 1081:1516 */         browserVersion = webClient.getBrowserVersion();
/* 1082:1517 */         for (HtmlElement child : getPage().getHtmlElementDescendants()) {
/* 1083:1518 */           for (int i = 0; i < selectorList.getLength(); i++)
/* 1084:     */           {
/* 1085:1519 */             Selector selector = selectorList.item(i);
/* 1086:1520 */             if (CSSStyleSheet.selects(browserVersion, selector, child)) {
/* 1087:1521 */               elements.add(child);
/* 1088:     */             }
/* 1089:     */           }
/* 1090:     */         }
/* 1091:     */       }
/* 1092:     */     }
/* 1093:     */     catch (Exception e)
/* 1094:     */     {
/* 1095:     */       SelectorList selectorList;
/* 1096:     */       BrowserVersion browserVersion;
/* 1097:1528 */       LOG.error("Error parsing CSS selectors from '" + selectors + "': " + e.getMessage(), e);
/* 1098:     */     }
/* 1099:1530 */     return new StaticDomNodeList(elements);
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   protected DomNode querySelector(String selectors)
/* 1103:     */   {
/* 1104:1539 */     DomNodeList<DomNode> list = querySelectorAll(selectors);
/* 1105:1540 */     if (!list.isEmpty()) {
/* 1106:1541 */       return (DomNode)list.get(0);
/* 1107:     */     }
/* 1108:1543 */     return null;
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   protected boolean isDirectlyAttachedToPage()
/* 1112:     */   {
/* 1113:1551 */     return this.directlyAttachedToPage_;
/* 1114:     */   }
/* 1115:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomNode
 * JD-Core Version:    0.7.0.1
 */