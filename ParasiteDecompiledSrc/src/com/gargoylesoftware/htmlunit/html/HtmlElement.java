/*    1:     */ package com.gargoylesoftware.htmlunit.html;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    5:     */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*    6:     */ import com.gargoylesoftware.htmlunit.Page;
/*    7:     */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*    8:     */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*    9:     */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   10:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*   11:     */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   12:     */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*   13:     */ import com.gargoylesoftware.htmlunit.javascript.host.Event;
/*   14:     */ import com.gargoylesoftware.htmlunit.javascript.host.EventHandler;
/*   15:     */ import com.gargoylesoftware.htmlunit.javascript.host.KeyboardEvent;
/*   16:     */ import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;
/*   17:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   18:     */ import java.io.IOException;
/*   19:     */ import java.io.PrintWriter;
/*   20:     */ import java.io.StringWriter;
/*   21:     */ import java.util.ArrayList;
/*   22:     */ import java.util.Iterator;
/*   23:     */ import java.util.LinkedHashMap;
/*   24:     */ import java.util.List;
/*   25:     */ import java.util.Map;
/*   26:     */ import java.util.NoSuchElementException;
/*   27:     */ import net.sourceforge.htmlunit.corejs.javascript.BaseFunction;
/*   28:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   29:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*   30:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*   31:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   32:     */ import org.apache.commons.lang.ClassUtils;
/*   33:     */ import org.apache.commons.logging.Log;
/*   34:     */ import org.apache.commons.logging.LogFactory;
/*   35:     */ import org.w3c.dom.CDATASection;
/*   36:     */ import org.w3c.dom.Comment;
/*   37:     */ import org.w3c.dom.DOMException;
/*   38:     */ import org.w3c.dom.Element;
/*   39:     */ import org.w3c.dom.EntityReference;
/*   40:     */ import org.w3c.dom.Node;
/*   41:     */ import org.w3c.dom.ProcessingInstruction;
/*   42:     */ import org.w3c.dom.Text;
/*   43:     */ 
/*   44:     */ public abstract class HtmlElement
/*   45:     */   extends DomElement
/*   46:     */ {
/*   47:  78 */   private static final Log LOG = LogFactory.getLog(HtmlElement.class);
/*   48:  86 */   public static final Short TAB_INDEX_OUT_OF_BOUNDS = new Short((short)-32768);
/*   49:     */   private List<HtmlAttributeChangeListener> attributeListeners_;
/*   50:     */   private HtmlForm owningForm_;
/*   51:     */   
/*   52:     */   protected HtmlElement(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*   53:     */   {
/*   54: 105 */     super(namespaceURI, qualifiedName, page, attributes);
/*   55: 106 */     this.attributeListeners_ = new ArrayList();
/*   56: 107 */     if ((page != null) && (page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLELEMENT_TRIM_CLASS_ATTRIBUTE)))
/*   57:     */     {
/*   58: 109 */       String value = getAttribute("class");
/*   59: 110 */       if (value != ATTRIBUTE_NOT_DEFINED) {
/*   60: 111 */         getAttributeNode("class").setValue(value.trim());
/*   61:     */       }
/*   62:     */     }
/*   63:     */   }
/*   64:     */   
/*   65:     */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/*   66:     */   {
/*   67: 130 */     String oldAttributeValue = getAttribute(qualifiedName);
/*   68:     */     
/*   69: 132 */     boolean mappedElement = HtmlPage.isMappedElement(getOwnerDocument(), qualifiedName);
/*   70: 133 */     if (mappedElement) {
/*   71: 134 */       ((HtmlPage)getPage()).removeMappedElement(this);
/*   72:     */     }
/*   73: 137 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/*   74: 140 */     if (!(getOwnerDocument() instanceof HtmlPage)) {
/*   75: 141 */       return;
/*   76:     */     }
/*   77: 144 */     HtmlPage htmlPage = (HtmlPage)getPage();
/*   78: 145 */     if (mappedElement) {
/*   79: 146 */       htmlPage.addMappedElement(this);
/*   80:     */     }
/*   81:     */     HtmlAttributeChangeEvent htmlEvent;
/*   82:     */     HtmlAttributeChangeEvent htmlEvent;
/*   83: 150 */     if (oldAttributeValue == ATTRIBUTE_NOT_DEFINED) {
/*   84: 151 */       htmlEvent = new HtmlAttributeChangeEvent(this, qualifiedName, attributeValue);
/*   85:     */     } else {
/*   86: 154 */       htmlEvent = new HtmlAttributeChangeEvent(this, qualifiedName, oldAttributeValue);
/*   87:     */     }
/*   88: 157 */     if (oldAttributeValue == ATTRIBUTE_NOT_DEFINED)
/*   89:     */     {
/*   90: 158 */       fireHtmlAttributeAdded(htmlEvent);
/*   91: 159 */       ((HtmlPage)getPage()).fireHtmlAttributeAdded(htmlEvent);
/*   92:     */     }
/*   93:     */     else
/*   94:     */     {
/*   95: 162 */       fireHtmlAttributeReplaced(htmlEvent);
/*   96: 163 */       ((HtmlPage)getPage()).fireHtmlAttributeReplaced(htmlEvent);
/*   97:     */     }
/*   98: 165 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.EVENT_PROPERTY_CHANGE)) {
/*   99: 166 */       fireEvent(Event.createPropertyChangeEvent(this, qualifiedName));
/*  100:     */     }
/*  101:     */   }
/*  102:     */   
/*  103:     */   public final List<HtmlElement> getHtmlElementsByTagNames(List<String> tagNames)
/*  104:     */   {
/*  105: 176 */     List<HtmlElement> list = new ArrayList();
/*  106: 177 */     for (String tagName : tagNames) {
/*  107: 178 */       list.addAll(getHtmlElementsByTagName(tagName));
/*  108:     */     }
/*  109: 180 */     return list;
/*  110:     */   }
/*  111:     */   
/*  112:     */   public final <E extends HtmlElement> List<E> getHtmlElementsByTagName(String tagName)
/*  113:     */   {
/*  114: 191 */     List<E> list = new ArrayList();
/*  115: 192 */     String lowerCaseTagName = tagName.toLowerCase();
/*  116: 193 */     Iterable<HtmlElement> iterable = getHtmlElementDescendants();
/*  117: 194 */     for (HtmlElement element : iterable) {
/*  118: 195 */       if (lowerCaseTagName.equals(element.getTagName())) {
/*  119: 196 */         list.add(element);
/*  120:     */       }
/*  121:     */     }
/*  122: 199 */     return list;
/*  123:     */   }
/*  124:     */   
/*  125:     */   public final void removeAttribute(String attributeName)
/*  126:     */   {
/*  127: 208 */     String value = getAttribute(attributeName);
/*  128: 210 */     if ((getPage() instanceof HtmlPage)) {
/*  129: 211 */       ((HtmlPage)getPage()).removeMappedElement(this);
/*  130:     */     }
/*  131: 214 */     super.removeAttribute(attributeName);
/*  132: 216 */     if ((getPage() instanceof HtmlPage))
/*  133:     */     {
/*  134: 217 */       ((HtmlPage)getPage()).addMappedElement(this);
/*  135:     */       
/*  136: 219 */       HtmlAttributeChangeEvent event = new HtmlAttributeChangeEvent(this, attributeName, value);
/*  137: 220 */       fireHtmlAttributeRemoved(event);
/*  138: 221 */       ((HtmlPage)getPage()).fireHtmlAttributeRemoved(event);
/*  139:     */     }
/*  140:     */   }
/*  141:     */   
/*  142:     */   protected void fireHtmlAttributeAdded(HtmlAttributeChangeEvent event)
/*  143:     */   {
/*  144: 237 */     synchronized (this.attributeListeners_)
/*  145:     */     {
/*  146: 238 */       for (HtmlAttributeChangeListener listener : this.attributeListeners_) {
/*  147: 239 */         listener.attributeAdded(event);
/*  148:     */       }
/*  149:     */     }
/*  150: 242 */     DomNode parentNode = getParentNode();
/*  151: 243 */     if ((parentNode instanceof HtmlElement)) {
/*  152: 244 */       ((HtmlElement)parentNode).fireHtmlAttributeAdded(event);
/*  153:     */     }
/*  154:     */   }
/*  155:     */   
/*  156:     */   protected void fireHtmlAttributeReplaced(HtmlAttributeChangeEvent event)
/*  157:     */   {
/*  158: 260 */     synchronized (this.attributeListeners_)
/*  159:     */     {
/*  160: 261 */       for (HtmlAttributeChangeListener listener : this.attributeListeners_) {
/*  161: 262 */         listener.attributeReplaced(event);
/*  162:     */       }
/*  163:     */     }
/*  164: 265 */     DomNode parentNode = getParentNode();
/*  165: 266 */     if ((parentNode instanceof HtmlElement)) {
/*  166: 267 */       ((HtmlElement)parentNode).fireHtmlAttributeReplaced(event);
/*  167:     */     }
/*  168:     */   }
/*  169:     */   
/*  170:     */   protected void fireHtmlAttributeRemoved(HtmlAttributeChangeEvent event)
/*  171:     */   {
/*  172: 283 */     synchronized (this.attributeListeners_)
/*  173:     */     {
/*  174: 284 */       for (HtmlAttributeChangeListener listener : this.attributeListeners_) {
/*  175: 285 */         listener.attributeRemoved(event);
/*  176:     */       }
/*  177:     */     }
/*  178: 288 */     DomNode parentNode = getParentNode();
/*  179: 289 */     if ((parentNode instanceof HtmlElement)) {
/*  180: 290 */       ((HtmlElement)parentNode).fireHtmlAttributeRemoved(event);
/*  181:     */     }
/*  182:     */   }
/*  183:     */   
/*  184:     */   public String getNodeName()
/*  185:     */   {
/*  186: 299 */     StringBuilder name = new StringBuilder();
/*  187: 300 */     if (getPrefix() != null) {
/*  188: 301 */       name.append(getPrefix() + ':');
/*  189:     */     }
/*  190: 303 */     name.append(getLocalName());
/*  191: 304 */     return name.toString().toLowerCase();
/*  192:     */   }
/*  193:     */   
/*  194:     */   public final String getId()
/*  195:     */   {
/*  196: 311 */     return getAttribute("id");
/*  197:     */   }
/*  198:     */   
/*  199:     */   public final void setId(String newId)
/*  200:     */   {
/*  201: 320 */     setAttribute("id", newId);
/*  202:     */   }
/*  203:     */   
/*  204:     */   public Short getTabIndex()
/*  205:     */   {
/*  206: 332 */     String index = getAttribute("tabindex");
/*  207: 333 */     if ((index == null) || (index.length() == 0)) {
/*  208: 334 */       return null;
/*  209:     */     }
/*  210:     */     try
/*  211:     */     {
/*  212: 337 */       long l = Long.parseLong(index);
/*  213: 338 */       if ((l >= 0L) && (l <= 32767L)) {
/*  214: 339 */         return Short.valueOf((short)(int)l);
/*  215:     */       }
/*  216: 341 */       return TAB_INDEX_OUT_OF_BOUNDS;
/*  217:     */     }
/*  218:     */     catch (NumberFormatException e) {}
/*  219: 344 */     return null;
/*  220:     */   }
/*  221:     */   
/*  222:     */   public HtmlElement getEnclosingElement(String tagName)
/*  223:     */   {
/*  224: 355 */     String tagNameLC = tagName.toLowerCase();
/*  225: 357 */     for (DomNode currentNode = getParentNode(); currentNode != null; currentNode = currentNode.getParentNode()) {
/*  226: 358 */       if (((currentNode instanceof HtmlElement)) && (currentNode.getNodeName().equals(tagNameLC))) {
/*  227: 359 */         return (HtmlElement)currentNode;
/*  228:     */       }
/*  229:     */     }
/*  230: 362 */     return null;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public HtmlForm getEnclosingForm()
/*  234:     */   {
/*  235: 371 */     if (this.owningForm_ != null) {
/*  236: 372 */       return this.owningForm_;
/*  237:     */     }
/*  238: 374 */     return (HtmlForm)getEnclosingElement("form");
/*  239:     */   }
/*  240:     */   
/*  241:     */   public HtmlForm getEnclosingFormOrDie()
/*  242:     */     throws IllegalStateException
/*  243:     */   {
/*  244: 384 */     HtmlForm form = getEnclosingForm();
/*  245: 385 */     if (form == null) {
/*  246: 386 */       throw new IllegalStateException("Element is not contained within a form: " + this);
/*  247:     */     }
/*  248: 388 */     return form;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public void type(String text)
/*  252:     */     throws IOException
/*  253:     */   {
/*  254: 398 */     for (char ch : text.toCharArray()) {
/*  255: 399 */       type(ch);
/*  256:     */     }
/*  257:     */   }
/*  258:     */   
/*  259:     */   public void type(String text, boolean shiftKey, boolean ctrlKey, boolean altKey)
/*  260:     */     throws IOException
/*  261:     */   {
/*  262: 414 */     for (char ch : text.toCharArray()) {
/*  263: 415 */       type(ch, shiftKey, ctrlKey, altKey);
/*  264:     */     }
/*  265:     */   }
/*  266:     */   
/*  267:     */   public Page type(char c)
/*  268:     */     throws IOException
/*  269:     */   {
/*  270: 430 */     return type(c, false, false, false);
/*  271:     */   }
/*  272:     */   
/*  273:     */   public Page type(char c, boolean shiftKey, boolean ctrlKey, boolean altKey)
/*  274:     */     throws IOException
/*  275:     */   {
/*  276: 448 */     if (((this instanceof DisabledElement)) && (((DisabledElement)this).isDisabled())) {
/*  277: 449 */       return getPage();
/*  278:     */     }
/*  279: 452 */     HtmlPage page = (HtmlPage)getPage();
/*  280: 453 */     if (page.getFocusedElement() != this) {
/*  281: 454 */       focus();
/*  282:     */     }
/*  283: 457 */     Event keyDown = new KeyboardEvent(this, "keydown", c, shiftKey, ctrlKey, altKey);
/*  284: 458 */     ScriptResult keyDownResult = fireEvent(keyDown);
/*  285:     */     
/*  286: 460 */     Event keyPress = new KeyboardEvent(this, "keypress", c, shiftKey, ctrlKey, altKey);
/*  287: 461 */     ScriptResult keyPressResult = fireEvent(keyPress);
/*  288: 463 */     if ((!keyDown.isAborted(keyDownResult)) && (!keyPress.isAborted(keyPressResult))) {
/*  289: 464 */       doType(c, shiftKey, ctrlKey, altKey);
/*  290:     */     }
/*  291: 467 */     if ((page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.EVENT_INPUT)) && (((this instanceof HtmlTextInput)) || ((this instanceof HtmlTextArea)) || ((this instanceof HtmlPasswordInput))))
/*  292:     */     {
/*  293: 471 */       Event input = new KeyboardEvent(this, "input", c, shiftKey, ctrlKey, altKey);
/*  294: 472 */       fireEvent(input);
/*  295:     */     }
/*  296: 475 */     Event keyUp = new KeyboardEvent(this, "keyup", c, shiftKey, ctrlKey, altKey);
/*  297: 476 */     fireEvent(keyUp);
/*  298:     */     
/*  299: 478 */     HtmlForm form = getEnclosingForm();
/*  300: 479 */     if ((form != null) && (c == '\n') && (isSubmittableByEnter()))
/*  301:     */     {
/*  302: 480 */       if (!getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.BUTTON_EMPTY_TYPE_BUTTON))
/*  303:     */       {
/*  304: 482 */         HtmlSubmitInput submit = (HtmlSubmitInput)form.getFirstByXPath(".//input[@type='submit']");
/*  305: 483 */         if (submit != null) {
/*  306: 484 */           return submit.click();
/*  307:     */         }
/*  308:     */       }
/*  309: 487 */       form.submit((SubmittableElement)this);
/*  310: 488 */       page.getWebClient().getJavaScriptEngine().processPostponedActions();
/*  311:     */     }
/*  312: 490 */     return page.getWebClient().getCurrentWindow().getEnclosedPage();
/*  313:     */   }
/*  314:     */   
/*  315:     */   protected void doType(char c, boolean shiftKey, boolean ctrlKey, boolean altKey) {}
/*  316:     */   
/*  317:     */   protected boolean isSubmittableByEnter()
/*  318:     */   {
/*  319: 510 */     return false;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public String toString()
/*  323:     */   {
/*  324: 519 */     StringWriter writer = new StringWriter();
/*  325: 520 */     PrintWriter printWriter = new PrintWriter(writer);
/*  326:     */     
/*  327: 522 */     printWriter.print(ClassUtils.getShortClassName(getClass()));
/*  328: 523 */     printWriter.print("[<");
/*  329: 524 */     printOpeningTagContentAsXml(printWriter);
/*  330: 525 */     printWriter.print(">]");
/*  331: 526 */     printWriter.flush();
/*  332: 527 */     return writer.toString();
/*  333:     */   }
/*  334:     */   
/*  335:     */   public final <E extends HtmlElement> E getOneHtmlElementByAttribute(String elementName, String attributeName, String attributeValue)
/*  336:     */     throws ElementNotFoundException
/*  337:     */   {
/*  338: 545 */     WebAssert.notNull("elementName", elementName);
/*  339: 546 */     WebAssert.notNull("attributeName", attributeName);
/*  340: 547 */     WebAssert.notNull("attributeValue", attributeValue);
/*  341:     */     
/*  342: 549 */     List<E> list = getElementsByAttribute(elementName, attributeName, attributeValue);
/*  343:     */     
/*  344: 551 */     int listSize = list.size();
/*  345: 552 */     if (listSize == 0) {
/*  346: 553 */       throw new ElementNotFoundException(elementName, attributeName, attributeValue);
/*  347:     */     }
/*  348: 556 */     return (HtmlElement)list.get(0);
/*  349:     */   }
/*  350:     */   
/*  351:     */   public <E extends HtmlElement> E getElementById(String id)
/*  352:     */     throws ElementNotFoundException
/*  353:     */   {
/*  354: 570 */     return ((HtmlPage)getPage()).getHtmlElementById(id);
/*  355:     */   }
/*  356:     */   
/*  357:     */   public boolean hasHtmlElementWithId(String id)
/*  358:     */   {
/*  359:     */     try
/*  360:     */     {
/*  361: 593 */       getElementById(id);
/*  362: 594 */       return true;
/*  363:     */     }
/*  364:     */     catch (ElementNotFoundException e) {}
/*  365: 597 */     return false;
/*  366:     */   }
/*  367:     */   
/*  368:     */   public final <E extends HtmlElement> List<E> getElementsByAttribute(String elementName, String attributeName, String attributeValue)
/*  369:     */   {
/*  370: 616 */     List<E> list = new ArrayList();
/*  371: 617 */     String lowerCaseTagName = elementName.toLowerCase();
/*  372: 619 */     for (HtmlElement next : getHtmlElementDescendants()) {
/*  373: 620 */       if (next.getTagName().equals(lowerCaseTagName))
/*  374:     */       {
/*  375: 621 */         String attValue = next.getAttribute(attributeName);
/*  376: 622 */         if ((attValue != null) && (attValue.equals(attributeValue))) {
/*  377: 623 */           list.add(next);
/*  378:     */         }
/*  379:     */       }
/*  380:     */     }
/*  381: 627 */     return list;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public final HtmlElement appendChildIfNoneExists(String tagName)
/*  385:     */   {
/*  386: 640 */     List<HtmlElement> children = getHtmlElementsByTagName(tagName);
/*  387:     */     HtmlElement child;
/*  388: 641 */     if (children.isEmpty())
/*  389:     */     {
/*  390: 643 */       HtmlElement child = ((HtmlPage)getPage()).createElement(tagName);
/*  391: 644 */       appendChild(child);
/*  392:     */     }
/*  393:     */     else
/*  394:     */     {
/*  395: 648 */       child = (HtmlElement)children.get(0);
/*  396:     */     }
/*  397: 650 */     return child;
/*  398:     */   }
/*  399:     */   
/*  400:     */   public final void removeChild(String tagName, int i)
/*  401:     */   {
/*  402: 660 */     List<HtmlElement> children = getHtmlElementsByTagName(tagName);
/*  403: 661 */     if ((i >= 0) && (i < children.size())) {
/*  404: 662 */       ((HtmlElement)children.get(i)).remove();
/*  405:     */     }
/*  406:     */   }
/*  407:     */   
/*  408:     */   public final Iterable<HtmlElement> getChildElements()
/*  409:     */   {
/*  410: 670 */     new Iterable()
/*  411:     */     {
/*  412:     */       public Iterator<HtmlElement> iterator()
/*  413:     */       {
/*  414: 672 */         return new HtmlElement.ChildElementsIterator(HtmlElement.this);
/*  415:     */       }
/*  416:     */     };
/*  417:     */   }
/*  418:     */   
/*  419:     */   protected class ChildElementsIterator
/*  420:     */     implements Iterator<HtmlElement>
/*  421:     */   {
/*  422:     */     private HtmlElement nextElement_;
/*  423:     */     
/*  424:     */     protected ChildElementsIterator()
/*  425:     */     {
/*  426: 686 */       if (HtmlElement.this.getFirstChild() != null) {
/*  427: 687 */         if ((HtmlElement.this.getFirstChild() instanceof HtmlElement)) {
/*  428: 688 */           this.nextElement_ = ((HtmlElement)HtmlElement.this.getFirstChild());
/*  429:     */         } else {
/*  430: 691 */           setNextElement(HtmlElement.this.getFirstChild());
/*  431:     */         }
/*  432:     */       }
/*  433:     */     }
/*  434:     */     
/*  435:     */     public boolean hasNext()
/*  436:     */     {
/*  437: 698 */       return this.nextElement_ != null;
/*  438:     */     }
/*  439:     */     
/*  440:     */     public HtmlElement next()
/*  441:     */     {
/*  442: 703 */       return nextElement();
/*  443:     */     }
/*  444:     */     
/*  445:     */     public void remove()
/*  446:     */     {
/*  447: 708 */       if (this.nextElement_ == null) {
/*  448: 709 */         throw new IllegalStateException();
/*  449:     */       }
/*  450: 711 */       DomNode sibling = this.nextElement_.getPreviousSibling();
/*  451: 712 */       if (sibling != null) {
/*  452: 713 */         sibling.remove();
/*  453:     */       }
/*  454:     */     }
/*  455:     */     
/*  456:     */     public HtmlElement nextElement()
/*  457:     */     {
/*  458: 719 */       if (this.nextElement_ != null)
/*  459:     */       {
/*  460: 720 */         HtmlElement result = this.nextElement_;
/*  461: 721 */         setNextElement(this.nextElement_);
/*  462: 722 */         return result;
/*  463:     */       }
/*  464: 724 */       throw new NoSuchElementException();
/*  465:     */     }
/*  466:     */     
/*  467:     */     private void setNextElement(DomNode node)
/*  468:     */     {
/*  469: 728 */       DomNode next = node.getNextSibling();
/*  470: 729 */       while ((next != null) && (!(next instanceof HtmlElement))) {
/*  471: 730 */         next = next.getNextSibling();
/*  472:     */       }
/*  473: 732 */       this.nextElement_ = ((HtmlElement)next);
/*  474:     */     }
/*  475:     */   }
/*  476:     */   
/*  477:     */   static Map<String, DomAttr> createAttributeMap(int attributeCount)
/*  478:     */   {
/*  479: 742 */     return new LinkedHashMap(attributeCount);
/*  480:     */   }
/*  481:     */   
/*  482:     */   static DomAttr addAttributeToMap(SgmlPage page, Map<String, DomAttr> attributeMap, String namespaceURI, String qualifiedName, String value)
/*  483:     */   {
/*  484: 756 */     DomAttr newAttr = new DomAttr(page, namespaceURI, qualifiedName, value, true);
/*  485: 757 */     attributeMap.put(qualifiedName, newAttr);
/*  486: 758 */     return newAttr;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public final boolean hasEventHandlers(String eventName)
/*  490:     */   {
/*  491: 769 */     HTMLElement jsObj = (HTMLElement)getScriptObject();
/*  492: 770 */     return jsObj.hasEventHandlers(eventName);
/*  493:     */   }
/*  494:     */   
/*  495:     */   public final void setEventHandler(String eventName, Function eventHandler)
/*  496:     */   {
/*  497: 780 */     HTMLElement jsObj = (HTMLElement)getScriptObject();
/*  498: 781 */     jsObj.setEventHandler(eventName, eventHandler);
/*  499:     */   }
/*  500:     */   
/*  501:     */   public final void setEventHandler(String eventName, String jsSnippet)
/*  502:     */   {
/*  503: 793 */     BaseFunction function = new EventHandler(this, eventName, jsSnippet);
/*  504: 794 */     setEventHandler(eventName, function);
/*  505: 795 */     if (LOG.isDebugEnabled()) {
/*  506: 796 */       LOG.debug("Created event handler " + function.getFunctionName() + " for " + eventName + " on " + this);
/*  507:     */     }
/*  508:     */   }
/*  509:     */   
/*  510:     */   public final void removeEventHandler(String eventName)
/*  511:     */   {
/*  512: 807 */     setEventHandler(eventName, (Function)null);
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void addHtmlAttributeChangeListener(HtmlAttributeChangeListener listener)
/*  516:     */   {
/*  517: 819 */     WebAssert.notNull("listener", listener);
/*  518: 820 */     synchronized (this.attributeListeners_)
/*  519:     */     {
/*  520: 821 */       this.attributeListeners_.add(listener);
/*  521:     */     }
/*  522:     */   }
/*  523:     */   
/*  524:     */   public void removeHtmlAttributeChangeListener(HtmlAttributeChangeListener listener)
/*  525:     */   {
/*  526: 834 */     WebAssert.notNull("listener", listener);
/*  527: 835 */     synchronized (this.attributeListeners_)
/*  528:     */     {
/*  529: 836 */       this.attributeListeners_.remove(listener);
/*  530:     */     }
/*  531:     */   }
/*  532:     */   
/*  533:     */   public ScriptResult fireEvent(String eventType)
/*  534:     */   {
/*  535: 846 */     return fireEvent(new Event(this, eventType));
/*  536:     */   }
/*  537:     */   
/*  538:     */   public ScriptResult fireEvent(final Event event)
/*  539:     */   {
/*  540: 855 */     WebClient client = getPage().getWebClient();
/*  541: 856 */     if (!client.isJavaScriptEnabled()) {
/*  542: 857 */       return null;
/*  543:     */     }
/*  544: 860 */     if (LOG.isDebugEnabled()) {
/*  545: 861 */       LOG.debug("Firing " + event);
/*  546:     */     }
/*  547: 863 */     final HTMLElement jsElt = (HTMLElement)getScriptObject();
/*  548: 864 */     ContextAction action = new ContextAction()
/*  549:     */     {
/*  550:     */       public Object run(Context cx)
/*  551:     */       {
/*  552: 866 */         return jsElt.fireEvent(event);
/*  553:     */       }
/*  554: 869 */     };
/*  555: 870 */     ContextFactory cf = client.getJavaScriptEngine().getContextFactory();
/*  556: 871 */     ScriptResult result = (ScriptResult)cf.call(action);
/*  557: 872 */     if (event.isAborted(result)) {
/*  558: 873 */       preventDefault();
/*  559:     */     }
/*  560: 875 */     return result;
/*  561:     */   }
/*  562:     */   
/*  563:     */   protected void preventDefault() {}
/*  564:     */   
/*  565:     */   public Page mouseOver()
/*  566:     */   {
/*  567: 896 */     return mouseOver(false, false, false, 0);
/*  568:     */   }
/*  569:     */   
/*  570:     */   public Page mouseOver(boolean shiftKey, boolean ctrlKey, boolean altKey, int button)
/*  571:     */   {
/*  572: 912 */     return doMouseEvent("mouseover", shiftKey, ctrlKey, altKey, button);
/*  573:     */   }
/*  574:     */   
/*  575:     */   public Page mouseMove()
/*  576:     */   {
/*  577: 923 */     return mouseMove(false, false, false, 0);
/*  578:     */   }
/*  579:     */   
/*  580:     */   public Page mouseMove(boolean shiftKey, boolean ctrlKey, boolean altKey, int button)
/*  581:     */   {
/*  582: 939 */     return doMouseEvent("mousemove", shiftKey, ctrlKey, altKey, button);
/*  583:     */   }
/*  584:     */   
/*  585:     */   public Page mouseOut()
/*  586:     */   {
/*  587: 950 */     return mouseOut(false, false, false, 0);
/*  588:     */   }
/*  589:     */   
/*  590:     */   public Page mouseOut(boolean shiftKey, boolean ctrlKey, boolean altKey, int button)
/*  591:     */   {
/*  592: 966 */     return doMouseEvent("mouseout", shiftKey, ctrlKey, altKey, button);
/*  593:     */   }
/*  594:     */   
/*  595:     */   public Page mouseDown()
/*  596:     */   {
/*  597: 977 */     return mouseDown(false, false, false, 0);
/*  598:     */   }
/*  599:     */   
/*  600:     */   public Page mouseDown(boolean shiftKey, boolean ctrlKey, boolean altKey, int button)
/*  601:     */   {
/*  602: 993 */     return doMouseEvent("mousedown", shiftKey, ctrlKey, altKey, button);
/*  603:     */   }
/*  604:     */   
/*  605:     */   public Page mouseUp()
/*  606:     */   {
/*  607:1004 */     return mouseUp(false, false, false, 0);
/*  608:     */   }
/*  609:     */   
/*  610:     */   public Page mouseUp(boolean shiftKey, boolean ctrlKey, boolean altKey, int button)
/*  611:     */   {
/*  612:1020 */     return doMouseEvent("mouseup", shiftKey, ctrlKey, altKey, button);
/*  613:     */   }
/*  614:     */   
/*  615:     */   public Page rightClick()
/*  616:     */   {
/*  617:1031 */     return rightClick(false, false, false);
/*  618:     */   }
/*  619:     */   
/*  620:     */   public Page rightClick(boolean shiftKey, boolean ctrlKey, boolean altKey)
/*  621:     */   {
/*  622:1045 */     Page mouseDownPage = mouseDown(shiftKey, ctrlKey, altKey, 2);
/*  623:1046 */     if (mouseDownPage != getPage())
/*  624:     */     {
/*  625:1047 */       if (LOG.isDebugEnabled()) {
/*  626:1048 */         LOG.debug("rightClick() is incomplete, as mouseDown() loaded a different page.");
/*  627:     */       }
/*  628:1050 */       return mouseDownPage;
/*  629:     */     }
/*  630:1052 */     Page mouseUpPage = mouseUp(shiftKey, ctrlKey, altKey, 2);
/*  631:1053 */     if (mouseUpPage != getPage())
/*  632:     */     {
/*  633:1054 */       if (LOG.isDebugEnabled()) {
/*  634:1055 */         LOG.debug("rightClick() is incomplete, as mouseUp() loaded a different page.");
/*  635:     */       }
/*  636:1057 */       return mouseUpPage;
/*  637:     */     }
/*  638:1059 */     return doMouseEvent("contextmenu", shiftKey, ctrlKey, altKey, 2);
/*  639:     */   }
/*  640:     */   
/*  641:     */   private Page doMouseEvent(String eventType, boolean shiftKey, boolean ctrlKey, boolean altKey, int button)
/*  642:     */   {
/*  643:1076 */     if (((this instanceof DisabledElement)) && (((DisabledElement)this).isDisabled())) {
/*  644:1077 */       return getPage();
/*  645:     */     }
/*  646:1079 */     HtmlPage page = (HtmlPage)getPage();
/*  647:1080 */     Event event = new MouseEvent(this, eventType, shiftKey, ctrlKey, altKey, button);
/*  648:1081 */     ScriptResult scriptResult = fireEvent(event);
/*  649:     */     Page currentPage;
/*  650:     */     Page currentPage;
/*  651:1083 */     if (scriptResult == null) {
/*  652:1084 */       currentPage = page;
/*  653:     */     } else {
/*  654:1087 */       currentPage = scriptResult.getNewPage();
/*  655:     */     }
/*  656:1089 */     return currentPage;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void blur()
/*  660:     */   {
/*  661:1096 */     ((HtmlPage)getPage()).setFocusedElement(null);
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void focus()
/*  665:     */   {
/*  666:1103 */     HtmlPage page = (HtmlPage)getPage();
/*  667:1104 */     page.setFocusedElement(this);
/*  668:1105 */     WebClient webClient = page.getWebClient();
/*  669:1106 */     if (webClient.getBrowserVersion().hasFeature(BrowserVersionFeatures.WINDOW_ACTIVE_ELEMENT_FOCUSED))
/*  670:     */     {
/*  671:1107 */       HTMLElement jsElt = (HTMLElement)getScriptObject();
/*  672:1108 */       jsElt.jsxFunction_setActive();
/*  673:     */     }
/*  674:     */   }
/*  675:     */   
/*  676:     */   protected void checkChildHierarchy(Node childNode)
/*  677:     */     throws DOMException
/*  678:     */   {
/*  679:1117 */     if ((!(childNode instanceof Element)) && (!(childNode instanceof Text)) && (!(childNode instanceof Comment)) && (!(childNode instanceof ProcessingInstruction)) && (!(childNode instanceof CDATASection)) && (!(childNode instanceof EntityReference))) {
/*  680:1120 */       throw new DOMException((short)3, "The Element may not have a child of this type: " + childNode.getNodeType());
/*  681:     */     }
/*  682:1123 */     super.checkChildHierarchy(childNode);
/*  683:     */   }
/*  684:     */   
/*  685:     */   void setOwningForm(HtmlForm form)
/*  686:     */   {
/*  687:1127 */     this.owningForm_ = form;
/*  688:     */   }
/*  689:     */   
/*  690:     */   void removeFocus() {}
/*  691:     */   
/*  692:     */   protected boolean isAttributeCaseSensitive()
/*  693:     */   {
/*  694:1143 */     return false;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public <P extends Page> P click()
/*  698:     */     throws IOException
/*  699:     */   {
/*  700:1158 */     return click(false, false, false);
/*  701:     */   }
/*  702:     */   
/*  703:     */   public <P extends Page> P click(boolean shiftKey, boolean ctrlKey, boolean altKey)
/*  704:     */     throws IOException
/*  705:     */   {
/*  706:1179 */     getPage().getWebClient().setCurrentWindow(getPage().getEnclosingWindow());
/*  707:1181 */     if (((this instanceof DisabledElement)) && (((DisabledElement)this).isDisabled())) {
/*  708:1182 */       return getPage();
/*  709:     */     }
/*  710:1185 */     mouseDown(shiftKey, ctrlKey, altKey, 0);
/*  711:     */     
/*  712:     */ 
/*  713:1188 */     HtmlElement elementToFocus = (this instanceof SubmittableElement) ? this : null;
/*  714:1189 */     ((HtmlPage)getPage()).setFocusedElement(elementToFocus);
/*  715:     */     
/*  716:1191 */     mouseUp(shiftKey, ctrlKey, altKey, 0);
/*  717:     */     
/*  718:1193 */     Event event = new MouseEvent(getEventTargetElement(), "click", shiftKey, ctrlKey, altKey, 0);
/*  719:     */     
/*  720:1195 */     return click(event);
/*  721:     */   }
/*  722:     */   
/*  723:     */   protected DomNode getEventTargetElement()
/*  724:     */   {
/*  725:1204 */     return this;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public <P extends Page> P click(Event event)
/*  729:     */     throws IOException
/*  730:     */   {
/*  731:1222 */     SgmlPage page = getPage();
/*  732:1224 */     if (((this instanceof DisabledElement)) && (((DisabledElement)this).isDisabled())) {
/*  733:1225 */       return page;
/*  734:     */     }
/*  735:1230 */     Page contentPage = page.getEnclosingWindow().getEnclosedPage();
/*  736:     */     
/*  737:1232 */     boolean stateUpdated = false;
/*  738:1233 */     if (isStateUpdateFirst())
/*  739:     */     {
/*  740:1234 */       doClickAction();
/*  741:1235 */       stateUpdated = true;
/*  742:     */     }
/*  743:1238 */     JavaScriptEngine jsEngine = page.getWebClient().getJavaScriptEngine();
/*  744:1239 */     jsEngine.holdPosponedActions();
/*  745:1240 */     ScriptResult scriptResult = fireEvent(event);
/*  746:     */     
/*  747:1242 */     boolean pageAlreadyChanged = contentPage != page.getEnclosingWindow().getEnclosedPage();
/*  748:1243 */     if ((!pageAlreadyChanged) && (!stateUpdated) && (!event.isAborted(scriptResult))) {
/*  749:1244 */       doClickAction();
/*  750:     */     }
/*  751:1246 */     jsEngine.processPostponedActions();
/*  752:     */     
/*  753:1248 */     return getPage().getWebClient().getCurrentWindow().getEnclosedPage();
/*  754:     */   }
/*  755:     */   
/*  756:     */   public <P extends Page> P dblClick()
/*  757:     */     throws IOException
/*  758:     */   {
/*  759:1263 */     return dblClick(false, false, false);
/*  760:     */   }
/*  761:     */   
/*  762:     */   public <P extends Page> P dblClick(boolean shiftKey, boolean ctrlKey, boolean altKey)
/*  763:     */     throws IOException
/*  764:     */   {
/*  765:1283 */     if (((this instanceof DisabledElement)) && (((DisabledElement)this).isDisabled())) {
/*  766:1284 */       return getPage();
/*  767:     */     }
/*  768:1288 */     Page clickPage = click(shiftKey, ctrlKey, altKey);
/*  769:1289 */     if (clickPage != getPage())
/*  770:     */     {
/*  771:1290 */       if (LOG.isDebugEnabled()) {
/*  772:1291 */         LOG.debug("dblClick() is ignored, as click() loaded a different page.");
/*  773:     */       }
/*  774:1293 */       return clickPage;
/*  775:     */     }
/*  776:1296 */     Event event = new MouseEvent(this, "dblclick", shiftKey, ctrlKey, altKey, 0);
/*  777:     */     
/*  778:1298 */     ScriptResult scriptResult = fireEvent(event);
/*  779:1299 */     if (scriptResult == null) {
/*  780:1300 */       return clickPage;
/*  781:     */     }
/*  782:1302 */     return scriptResult.getNewPage();
/*  783:     */   }
/*  784:     */   
/*  785:     */   protected void doClickAction()
/*  786:     */     throws IOException
/*  787:     */   {
/*  788:1316 */     DomNode parent = getParentNode();
/*  789:1321 */     if ((parent instanceof HtmlElement)) {
/*  790:1322 */       ((HtmlElement)parent).doClickAction();
/*  791:     */     }
/*  792:     */   }
/*  793:     */   
/*  794:     */   public final String getLangAttribute()
/*  795:     */   {
/*  796:1334 */     return getAttribute("lang");
/*  797:     */   }
/*  798:     */   
/*  799:     */   public final String getXmlLangAttribute()
/*  800:     */   {
/*  801:1345 */     return getAttribute("xml:lang");
/*  802:     */   }
/*  803:     */   
/*  804:     */   public final String getTextDirectionAttribute()
/*  805:     */   {
/*  806:1356 */     return getAttribute("dir");
/*  807:     */   }
/*  808:     */   
/*  809:     */   public final String getOnClickAttribute()
/*  810:     */   {
/*  811:1367 */     return getAttribute("onclick");
/*  812:     */   }
/*  813:     */   
/*  814:     */   public final String getOnDblClickAttribute()
/*  815:     */   {
/*  816:1378 */     return getAttribute("ondblclick");
/*  817:     */   }
/*  818:     */   
/*  819:     */   public final String getOnMouseDownAttribute()
/*  820:     */   {
/*  821:1389 */     return getAttribute("onmousedown");
/*  822:     */   }
/*  823:     */   
/*  824:     */   public final String getOnMouseUpAttribute()
/*  825:     */   {
/*  826:1400 */     return getAttribute("onmouseup");
/*  827:     */   }
/*  828:     */   
/*  829:     */   public final String getOnMouseOverAttribute()
/*  830:     */   {
/*  831:1411 */     return getAttribute("onmouseover");
/*  832:     */   }
/*  833:     */   
/*  834:     */   public final String getOnMouseMoveAttribute()
/*  835:     */   {
/*  836:1422 */     return getAttribute("onmousemove");
/*  837:     */   }
/*  838:     */   
/*  839:     */   public final String getOnMouseOutAttribute()
/*  840:     */   {
/*  841:1433 */     return getAttribute("onmouseout");
/*  842:     */   }
/*  843:     */   
/*  844:     */   public final String getOnKeyPressAttribute()
/*  845:     */   {
/*  846:1444 */     return getAttribute("onkeypress");
/*  847:     */   }
/*  848:     */   
/*  849:     */   public final String getOnKeyDownAttribute()
/*  850:     */   {
/*  851:1455 */     return getAttribute("onkeydown");
/*  852:     */   }
/*  853:     */   
/*  854:     */   public final String getOnKeyUpAttribute()
/*  855:     */   {
/*  856:1466 */     return getAttribute("onkeyup");
/*  857:     */   }
/*  858:     */   
/*  859:     */   protected boolean isStateUpdateFirst()
/*  860:     */   {
/*  861:1476 */     return false;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public String getCanonicalXPath()
/*  865:     */   {
/*  866:1484 */     DomNode parent = getParentNode();
/*  867:1485 */     if (parent.getNodeType() == 9) {
/*  868:1486 */       return "/" + getNodeName();
/*  869:     */     }
/*  870:1488 */     return parent.getCanonicalXPath() + '/' + getXPathToken();
/*  871:     */   }
/*  872:     */   
/*  873:     */   private String getXPathToken()
/*  874:     */   {
/*  875:1495 */     DomNode parent = getParentNode();
/*  876:1496 */     int total = 0;
/*  877:1497 */     int nodeIndex = 0;
/*  878:1498 */     for (DomNode child : parent.getChildren())
/*  879:     */     {
/*  880:1499 */       if ((child.getNodeType() == 1) && (child.getNodeName().equals(getNodeName()))) {
/*  881:1500 */         total++;
/*  882:     */       }
/*  883:1502 */       if (child == this) {
/*  884:1503 */         nodeIndex = total;
/*  885:     */       }
/*  886:     */     }
/*  887:1507 */     if ((nodeIndex == 1) && (total == 1)) {
/*  888:1508 */       return getNodeName();
/*  889:     */     }
/*  890:1510 */     return getNodeName() + '[' + nodeIndex + ']';
/*  891:     */   }
/*  892:     */   
/*  893:     */   public DomNodeList<DomNode> querySelectorAll(String selectors)
/*  894:     */   {
/*  895:1520 */     return super.querySelectorAll(selectors);
/*  896:     */   }
/*  897:     */   
/*  898:     */   public DomNode querySelector(String selectors)
/*  899:     */   {
/*  900:1529 */     return super.querySelector(selectors);
/*  901:     */   }
/*  902:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlElement
 * JD-Core Version:    0.7.0.1
 */