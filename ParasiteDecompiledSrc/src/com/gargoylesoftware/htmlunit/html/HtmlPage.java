/*    1:     */ package com.gargoylesoftware.htmlunit.html;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    5:     */ import com.gargoylesoftware.htmlunit.Cache;
/*    6:     */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*    7:     */ import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
/*    8:     */ import com.gargoylesoftware.htmlunit.IncorrectnessListener;
/*    9:     */ import com.gargoylesoftware.htmlunit.OnbeforeunloadHandler;
/*   10:     */ import com.gargoylesoftware.htmlunit.Page;
/*   11:     */ import com.gargoylesoftware.htmlunit.RefreshHandler;
/*   12:     */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*   13:     */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   14:     */ import com.gargoylesoftware.htmlunit.TopLevelWindow;
/*   15:     */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   16:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*   17:     */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   18:     */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   19:     */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   20:     */ import com.gargoylesoftware.htmlunit.html.impl.SelectableTextInput;
/*   21:     */ import com.gargoylesoftware.htmlunit.html.impl.SimpleRange;
/*   22:     */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*   23:     */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
/*   24:     */ import com.gargoylesoftware.htmlunit.javascript.PostponedAction;
/*   25:     */ import com.gargoylesoftware.htmlunit.javascript.host.Event;
/*   26:     */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   27:     */ import java.io.File;
/*   28:     */ import java.io.IOException;
/*   29:     */ import java.net.MalformedURLException;
/*   30:     */ import java.net.URL;
/*   31:     */ import java.util.ArrayList;
/*   32:     */ import java.util.Arrays;
/*   33:     */ import java.util.Collections;
/*   34:     */ import java.util.Comparator;
/*   35:     */ import java.util.HashMap;
/*   36:     */ import java.util.Iterator;
/*   37:     */ import java.util.List;
/*   38:     */ import java.util.Map;
/*   39:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   40:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   41:     */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*   42:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   43:     */ import org.apache.commons.lang.StringUtils;
/*   44:     */ import org.apache.commons.logging.Log;
/*   45:     */ import org.apache.commons.logging.LogFactory;
/*   46:     */ import org.w3c.dom.Attr;
/*   47:     */ import org.w3c.dom.CDATASection;
/*   48:     */ import org.w3c.dom.Comment;
/*   49:     */ import org.w3c.dom.DOMConfiguration;
/*   50:     */ import org.w3c.dom.DOMException;
/*   51:     */ import org.w3c.dom.DOMImplementation;
/*   52:     */ import org.w3c.dom.Document;
/*   53:     */ import org.w3c.dom.DocumentFragment;
/*   54:     */ import org.w3c.dom.DocumentType;
/*   55:     */ import org.w3c.dom.Element;
/*   56:     */ import org.w3c.dom.EntityReference;
/*   57:     */ import org.w3c.dom.NamedNodeMap;
/*   58:     */ import org.w3c.dom.ProcessingInstruction;
/*   59:     */ import org.w3c.dom.Text;
/*   60:     */ import org.w3c.dom.ranges.Range;
/*   61:     */ 
/*   62:     */ public class HtmlPage
/*   63:     */   extends SgmlPage
/*   64:     */ {
/*   65: 122 */   private static final Log LOG = LogFactory.getLog(HtmlPage.class);
/*   66:     */   private HTMLParser.HtmlUnitDOMBuilder builder_;
/*   67:     */   private String originalCharset_;
/*   68: 126 */   private Map<String, List<HtmlElement>> idMap_ = new HashMap();
/*   69: 127 */   private Map<String, List<HtmlElement>> nameMap_ = new HashMap();
/*   70:     */   private HtmlElement elementWithFocus_;
/*   71:     */   private int parserCount_;
/*   72:     */   private int snippetParserCount_;
/*   73:     */   private int inlineSnippetParserCount_;
/*   74:     */   private List<HtmlAttributeChangeListener> attributeListeners_;
/*   75: 133 */   private final Object lock_ = new String();
/*   76: 134 */   private List<Range> selectionRanges_ = new ArrayList(3);
/*   77: 135 */   private final List<PostponedAction> afterLoadActions_ = new ArrayList();
/*   78:     */   private boolean cleaning_;
/*   79:     */   private HtmlBase base_;
/*   80:     */   private URL baseUrl_;
/*   81:     */   
/*   82:     */   public HtmlPage(URL originatingUrl, WebResponse webResponse, WebWindow webWindow)
/*   83:     */   {
/*   84: 149 */     super(webResponse, webWindow);
/*   85:     */   }
/*   86:     */   
/*   87:     */   public HtmlPage getPage()
/*   88:     */   {
/*   89: 157 */     return this;
/*   90:     */   }
/*   91:     */   
/*   92:     */   public boolean hasCaseSensitiveTagNames()
/*   93:     */   {
/*   94: 165 */     return false;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public void initialize()
/*   98:     */     throws IOException, FailingHttpStatusCodeException
/*   99:     */   {
/*  100: 176 */     WebWindow enclosingWindow = getEnclosingWindow();
/*  101: 177 */     if (getWebResponse().getWebRequest().getUrl() == WebClient.URL_ABOUT_BLANK)
/*  102:     */     {
/*  103: 179 */       if (((enclosingWindow instanceof FrameWindow)) && (!((FrameWindow)enclosingWindow).getFrameElement().isContentLoaded())) {
/*  104: 181 */         return;
/*  105:     */       }
/*  106: 185 */       if ((enclosingWindow instanceof TopLevelWindow))
/*  107:     */       {
/*  108: 186 */         TopLevelWindow topWindow = (TopLevelWindow)enclosingWindow;
/*  109: 187 */         WebWindow openerWindow = topWindow.getOpener();
/*  110: 188 */         if ((openerWindow != null) && (openerWindow.getEnclosedPage() != null)) {
/*  111: 189 */           this.baseUrl_ = openerWindow.getEnclosedPage().getWebResponse().getWebRequest().getUrl();
/*  112:     */         }
/*  113:     */       }
/*  114:     */     }
/*  115: 194 */     loadFrames();
/*  116: 195 */     setReadyState("complete");
/*  117: 196 */     getDocumentElement().setReadyState("complete");
/*  118: 197 */     if (getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.EVENT_DOM_CONTENT_LOADED)) {
/*  119: 198 */       executeEventHandlersIfNeeded("DOMContentLoaded");
/*  120:     */     }
/*  121: 200 */     executeDeferredScriptsIfNeeded();
/*  122: 201 */     setReadyStateOnDeferredScriptsIfNeeded();
/*  123: 202 */     executeEventHandlersIfNeeded("load");
/*  124: 203 */     List<PostponedAction> actions = new ArrayList(this.afterLoadActions_);
/*  125: 204 */     this.afterLoadActions_.clear();
/*  126:     */     try
/*  127:     */     {
/*  128: 206 */       for (PostponedAction action : actions) {
/*  129: 207 */         action.execute();
/*  130:     */       }
/*  131:     */     }
/*  132:     */     catch (IOException e)
/*  133:     */     {
/*  134: 211 */       throw e;
/*  135:     */     }
/*  136:     */     catch (Exception e)
/*  137:     */     {
/*  138: 214 */       throw new RuntimeException(e);
/*  139:     */     }
/*  140: 216 */     executeRefreshIfNeeded();
/*  141:     */   }
/*  142:     */   
/*  143:     */   void addAfterLoadAction(PostponedAction action)
/*  144:     */   {
/*  145: 224 */     this.afterLoadActions_.add(action);
/*  146:     */   }
/*  147:     */   
/*  148:     */   public void cleanUp()
/*  149:     */   {
/*  150: 233 */     if (this.cleaning_) {
/*  151: 234 */       return;
/*  152:     */     }
/*  153: 236 */     this.cleaning_ = true;
/*  154: 237 */     executeEventHandlersIfNeeded("unload");
/*  155: 238 */     deregisterFramesIfNeeded();
/*  156: 239 */     this.cleaning_ = false;
/*  157:     */   }
/*  158:     */   
/*  159:     */   public HtmlElement getDocumentElement()
/*  160:     */   {
/*  161: 247 */     return (HtmlElement)super.getDocumentElement();
/*  162:     */   }
/*  163:     */   
/*  164:     */   public HtmlElement getBody()
/*  165:     */   {
/*  166: 255 */     HtmlElement doc = getDocumentElement();
/*  167: 256 */     if (doc != null) {
/*  168: 257 */       for (DomNode node : doc.getChildren()) {
/*  169: 258 */         if (((node instanceof HtmlBody)) || ((node instanceof HtmlFrameSet))) {
/*  170: 259 */           return (HtmlElement)node;
/*  171:     */         }
/*  172:     */       }
/*  173:     */     }
/*  174: 263 */     return null;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public Document getOwnerDocument()
/*  178:     */   {
/*  179: 271 */     return null;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public org.w3c.dom.Node importNode(org.w3c.dom.Node importedNode, boolean deep)
/*  183:     */   {
/*  184: 279 */     throw new UnsupportedOperationException("HtmlPage.importNode is not yet implemented.");
/*  185:     */   }
/*  186:     */   
/*  187:     */   public DomNodeList<HtmlElement> getElementsByTagName(String tagName)
/*  188:     */   {
/*  189: 286 */     return new XPathDomNodeList(this, "//*[local-name()='" + tagName + "']");
/*  190:     */   }
/*  191:     */   
/*  192:     */   public DomNodeList<HtmlElement> getElementsByTagNameNS(String namespaceURI, String localName)
/*  193:     */   {
/*  194: 294 */     throw new UnsupportedOperationException("HtmlPage.getElementsByTagNameNS is not yet implemented.");
/*  195:     */   }
/*  196:     */   
/*  197:     */   public HtmlElement getElementById(String elementId)
/*  198:     */   {
/*  199:     */     try
/*  200:     */     {
/*  201: 302 */       return getHtmlElementById(elementId);
/*  202:     */     }
/*  203:     */     catch (ElementNotFoundException e) {}
/*  204: 305 */     return null;
/*  205:     */   }
/*  206:     */   
/*  207:     */   public String getInputEncoding()
/*  208:     */   {
/*  209: 314 */     throw new UnsupportedOperationException("HtmlPage.getInputEncoding is not yet implemented.");
/*  210:     */   }
/*  211:     */   
/*  212:     */   public String getXmlEncoding()
/*  213:     */   {
/*  214: 322 */     throw new UnsupportedOperationException("HtmlPage.getXmlEncoding is not yet implemented.");
/*  215:     */   }
/*  216:     */   
/*  217:     */   public boolean getXmlStandalone()
/*  218:     */   {
/*  219: 330 */     throw new UnsupportedOperationException("HtmlPage.getXmlStandalone is not yet implemented.");
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void setXmlStandalone(boolean xmlStandalone)
/*  223:     */     throws DOMException
/*  224:     */   {
/*  225: 338 */     throw new UnsupportedOperationException("HtmlPage.setXmlStandalone is not yet implemented.");
/*  226:     */   }
/*  227:     */   
/*  228:     */   public String getXmlVersion()
/*  229:     */   {
/*  230: 346 */     throw new UnsupportedOperationException("HtmlPage.getXmlVersion is not yet implemented.");
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void setXmlVersion(String xmlVersion)
/*  234:     */     throws DOMException
/*  235:     */   {
/*  236: 354 */     throw new UnsupportedOperationException("HtmlPage.setXmlVersion is not yet implemented.");
/*  237:     */   }
/*  238:     */   
/*  239:     */   public boolean getStrictErrorChecking()
/*  240:     */   {
/*  241: 362 */     throw new UnsupportedOperationException("HtmlPage.getStrictErrorChecking is not yet implemented.");
/*  242:     */   }
/*  243:     */   
/*  244:     */   public void setStrictErrorChecking(boolean strictErrorChecking)
/*  245:     */   {
/*  246: 370 */     throw new UnsupportedOperationException("HtmlPage.setStrictErrorChecking is not yet implemented.");
/*  247:     */   }
/*  248:     */   
/*  249:     */   public String getDocumentURI()
/*  250:     */   {
/*  251: 378 */     throw new UnsupportedOperationException("HtmlPage.getDocumentURI is not yet implemented.");
/*  252:     */   }
/*  253:     */   
/*  254:     */   public void setDocumentURI(String documentURI)
/*  255:     */   {
/*  256: 386 */     throw new UnsupportedOperationException("HtmlPage.setDocumentURI is not yet implemented.");
/*  257:     */   }
/*  258:     */   
/*  259:     */   public org.w3c.dom.Node adoptNode(org.w3c.dom.Node source)
/*  260:     */     throws DOMException
/*  261:     */   {
/*  262: 394 */     throw new UnsupportedOperationException("HtmlPage.adoptNode is not yet implemented.");
/*  263:     */   }
/*  264:     */   
/*  265:     */   public DOMConfiguration getDomConfig()
/*  266:     */   {
/*  267: 402 */     throw new UnsupportedOperationException("HtmlPage.getDomConfig is not yet implemented.");
/*  268:     */   }
/*  269:     */   
/*  270:     */   public org.w3c.dom.Node renameNode(org.w3c.dom.Node newNode, String namespaceURI, String qualifiedName)
/*  271:     */     throws DOMException
/*  272:     */   {
/*  273: 411 */     throw new UnsupportedOperationException("HtmlPage.renameNode is not yet implemented.");
/*  274:     */   }
/*  275:     */   
/*  276:     */   public String getPageEncoding()
/*  277:     */   {
/*  278: 419 */     if (this.originalCharset_ == null) {
/*  279: 420 */       this.originalCharset_ = getWebResponse().getContentCharset();
/*  280:     */     }
/*  281: 422 */     return this.originalCharset_;
/*  282:     */   }
/*  283:     */   
/*  284:     */   public HtmlElement createElement(String tagName)
/*  285:     */   {
/*  286: 431 */     if (tagName.indexOf(':') == -1) {
/*  287: 432 */       tagName = tagName.toLowerCase();
/*  288:     */     }
/*  289: 434 */     return HTMLParser.getFactory(tagName).createElement(this, tagName, null);
/*  290:     */   }
/*  291:     */   
/*  292:     */   public HtmlElement createElementNS(String namespaceURI, String qualifiedName)
/*  293:     */   {
/*  294: 442 */     return HTMLParser.getElementFactory(namespaceURI, qualifiedName).createElementNS(this, namespaceURI, qualifiedName, null);
/*  295:     */   }
/*  296:     */   
/*  297:     */   public Attr createAttributeNS(String namespaceURI, String qualifiedName)
/*  298:     */   {
/*  299: 451 */     throw new UnsupportedOperationException("HtmlPage.createAttributeNS is not yet implemented.");
/*  300:     */   }
/*  301:     */   
/*  302:     */   public Comment createComment(String data)
/*  303:     */   {
/*  304: 458 */     return new DomComment(this, data);
/*  305:     */   }
/*  306:     */   
/*  307:     */   public Text createTextNode(String data)
/*  308:     */   {
/*  309: 465 */     return new DomText(this, data);
/*  310:     */   }
/*  311:     */   
/*  312:     */   public CDATASection createCDATASection(String data)
/*  313:     */   {
/*  314: 472 */     return new DomCDataSection(this, data);
/*  315:     */   }
/*  316:     */   
/*  317:     */   public DocumentFragment createDocumentFragment()
/*  318:     */   {
/*  319: 479 */     return new DomDocumentFragment(this);
/*  320:     */   }
/*  321:     */   
/*  322:     */   public DOMImplementation getImplementation()
/*  323:     */   {
/*  324: 487 */     throw new UnsupportedOperationException("HtmlPage.getImplementation is not yet implemented.");
/*  325:     */   }
/*  326:     */   
/*  327:     */   public EntityReference createEntityReference(String id)
/*  328:     */   {
/*  329: 495 */     throw new UnsupportedOperationException("HtmlPage.createEntityReference is not yet implemented.");
/*  330:     */   }
/*  331:     */   
/*  332:     */   public ProcessingInstruction createProcessingInstruction(String namespaceURI, String qualifiedName)
/*  333:     */   {
/*  334: 503 */     throw new UnsupportedOperationException("HtmlPage.createProcessingInstruction is not yet implemented.");
/*  335:     */   }
/*  336:     */   
/*  337:     */   public HtmlAnchor getAnchorByName(String name)
/*  338:     */     throws ElementNotFoundException
/*  339:     */   {
/*  340: 514 */     return (HtmlAnchor)getDocumentElement().getOneHtmlElementByAttribute("a", "name", name);
/*  341:     */   }
/*  342:     */   
/*  343:     */   public HtmlAnchor getAnchorByHref(String href)
/*  344:     */     throws ElementNotFoundException
/*  345:     */   {
/*  346: 525 */     return (HtmlAnchor)getDocumentElement().getOneHtmlElementByAttribute("a", "href", href);
/*  347:     */   }
/*  348:     */   
/*  349:     */   public List<HtmlAnchor> getAnchors()
/*  350:     */   {
/*  351: 533 */     return getDocumentElement().getHtmlElementsByTagName("a");
/*  352:     */   }
/*  353:     */   
/*  354:     */   public HtmlAnchor getAnchorByText(String text)
/*  355:     */     throws ElementNotFoundException
/*  356:     */   {
/*  357: 543 */     WebAssert.notNull("text", text);
/*  358: 545 */     for (HtmlAnchor anchor : getAnchors()) {
/*  359: 546 */       if (text.equals(anchor.asText())) {
/*  360: 547 */         return anchor;
/*  361:     */       }
/*  362:     */     }
/*  363: 550 */     throw new ElementNotFoundException("a", "<text>", text);
/*  364:     */   }
/*  365:     */   
/*  366:     */   public HtmlForm getFormByName(String name)
/*  367:     */     throws ElementNotFoundException
/*  368:     */   {
/*  369: 560 */     List<HtmlForm> forms = getDocumentElement().getElementsByAttribute("form", "name", name);
/*  370: 561 */     if (forms.size() == 0) {
/*  371: 562 */       throw new ElementNotFoundException("form", "name", name);
/*  372:     */     }
/*  373: 564 */     return (HtmlForm)forms.get(0);
/*  374:     */   }
/*  375:     */   
/*  376:     */   public List<HtmlForm> getForms()
/*  377:     */   {
/*  378: 572 */     return getDocumentElement().getHtmlElementsByTagName("form");
/*  379:     */   }
/*  380:     */   
/*  381:     */   public URL getFullyQualifiedUrl(String relativeUrl)
/*  382:     */     throws MalformedURLException
/*  383:     */   {
/*  384:     */     URL baseUrl;
/*  385: 585 */     if (this.base_ == null)
/*  386:     */     {
/*  387: 586 */       URL baseUrl = getWebResponse().getWebRequest().getUrl();
/*  388: 587 */       WebWindow window = getEnclosingWindow();
/*  389: 588 */       boolean frame = window != window.getTopWindow();
/*  390: 589 */       if (frame)
/*  391:     */       {
/*  392: 590 */         boolean frameSrcIsNotSet = baseUrl == WebClient.URL_ABOUT_BLANK;
/*  393: 591 */         boolean frameSrcIsJs = "javascript".equals(baseUrl.getProtocol());
/*  394: 592 */         boolean jsFrameUseParentUrl = getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_FRAME_RESOLVE_URL_WITH_PARENT_WINDOW);
/*  395: 594 */         if ((frameSrcIsNotSet) || ((frameSrcIsJs) && (jsFrameUseParentUrl))) {
/*  396: 595 */           baseUrl = ((HtmlPage)window.getTopWindow().getEnclosedPage()).getWebResponse().getWebRequest().getUrl();
/*  397:     */         }
/*  398:     */       }
/*  399: 599 */       else if (this.baseUrl_ != null)
/*  400:     */       {
/*  401: 600 */         baseUrl = this.baseUrl_;
/*  402:     */       }
/*  403:     */     }
/*  404:     */     else
/*  405:     */     {
/*  406: 604 */       boolean insideHead = false;
/*  407: 605 */       for (DomNode parent = this.base_.getParentNode(); parent != null; parent = parent.getParentNode()) {
/*  408: 606 */         if ((parent instanceof HtmlHead))
/*  409:     */         {
/*  410: 607 */           insideHead = true;
/*  411: 608 */           break;
/*  412:     */         }
/*  413:     */       }
/*  414: 613 */       if (!insideHead) {
/*  415: 614 */         notifyIncorrectness("Element 'base' must appear in <head>, it is ignored.");
/*  416:     */       }
/*  417: 617 */       String href = this.base_.getHrefAttribute();
/*  418:     */       URL baseUrl;
/*  419: 618 */       if ((!insideHead) || (StringUtils.isEmpty(href))) {
/*  420: 619 */         baseUrl = getWebResponse().getWebRequest().getUrl();
/*  421:     */       } else {
/*  422:     */         try
/*  423:     */         {
/*  424: 623 */           baseUrl = new URL(href);
/*  425:     */         }
/*  426:     */         catch (MalformedURLException e)
/*  427:     */         {
/*  428: 626 */           notifyIncorrectness("Invalid base url: \"" + href + "\", ignoring it");
/*  429: 627 */           baseUrl = getWebResponse().getWebRequest().getUrl();
/*  430:     */         }
/*  431:     */       }
/*  432:     */     }
/*  433: 633 */     if (getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.URL_MISSING_SLASHES))
/*  434:     */     {
/*  435: 634 */       boolean incorrectnessNotified = false;
/*  436: 635 */       while ((relativeUrl.startsWith("http:")) && (!relativeUrl.startsWith("http://")))
/*  437:     */       {
/*  438: 636 */         if (!incorrectnessNotified)
/*  439:     */         {
/*  440: 637 */           notifyIncorrectness("Incorrect URL \"" + relativeUrl + "\" has been corrected");
/*  441: 638 */           incorrectnessNotified = true;
/*  442:     */         }
/*  443: 640 */         relativeUrl = "http:/" + relativeUrl.substring(5);
/*  444:     */       }
/*  445:     */     }
/*  446: 644 */     return WebClient.expandUrl(baseUrl, relativeUrl);
/*  447:     */   }
/*  448:     */   
/*  449:     */   public String getResolvedTarget(String elementTarget)
/*  450:     */   {
/*  451:     */     String resolvedTarget;
/*  452:     */     String resolvedTarget;
/*  453: 655 */     if (this.base_ == null)
/*  454:     */     {
/*  455: 656 */       resolvedTarget = elementTarget;
/*  456:     */     }
/*  457:     */     else
/*  458:     */     {
/*  459:     */       String resolvedTarget;
/*  460: 658 */       if ((elementTarget != null) && (elementTarget.length() > 0)) {
/*  461: 659 */         resolvedTarget = elementTarget;
/*  462:     */       } else {
/*  463: 662 */         resolvedTarget = this.base_.getTargetAttribute();
/*  464:     */       }
/*  465:     */     }
/*  466: 664 */     return resolvedTarget;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public List<String> getTabbableElementIds()
/*  470:     */   {
/*  471: 674 */     List<String> list = new ArrayList();
/*  472: 676 */     for (HtmlElement element : getTabbableElements()) {
/*  473: 677 */       list.add(element.getAttribute("id"));
/*  474:     */     }
/*  475: 680 */     return Collections.unmodifiableList(list);
/*  476:     */   }
/*  477:     */   
/*  478:     */   public List<HtmlElement> getTabbableElements()
/*  479:     */   {
/*  480: 711 */     List<String> tags = Arrays.asList(new String[] { "a", "area", "button", "input", "object", "select", "textarea" });
/*  481:     */     
/*  482: 713 */     List<HtmlElement> tabbableElements = new ArrayList();
/*  483: 714 */     for (HtmlElement element : getHtmlElementDescendants())
/*  484:     */     {
/*  485: 715 */       String tagName = element.getTagName();
/*  486: 716 */       if (tags.contains(tagName))
/*  487:     */       {
/*  488: 717 */         boolean disabled = element.hasAttribute("disabled");
/*  489: 718 */         if ((!disabled) && (element.getTabIndex() != HtmlElement.TAB_INDEX_OUT_OF_BOUNDS)) {
/*  490: 719 */           tabbableElements.add(element);
/*  491:     */         }
/*  492:     */       }
/*  493:     */     }
/*  494: 723 */     Collections.sort(tabbableElements, createTabOrderComparator());
/*  495: 724 */     return Collections.unmodifiableList(tabbableElements);
/*  496:     */   }
/*  497:     */   
/*  498:     */   private Comparator<HtmlElement> createTabOrderComparator()
/*  499:     */   {
/*  500: 728 */     new Comparator()
/*  501:     */     {
/*  502:     */       public int compare(HtmlElement element1, HtmlElement element2)
/*  503:     */       {
/*  504: 730 */         Short i1 = element1.getTabIndex();
/*  505: 731 */         Short i2 = element2.getTabIndex();
/*  506:     */         short index1;
/*  507:     */         short index1;
/*  508: 734 */         if (i1 != null) {
/*  509: 735 */           index1 = i1.shortValue();
/*  510:     */         } else {
/*  511: 738 */           index1 = -1;
/*  512:     */         }
/*  513:     */         short index2;
/*  514:     */         short index2;
/*  515: 742 */         if (i2 != null) {
/*  516: 743 */           index2 = i2.shortValue();
/*  517:     */         } else {
/*  518: 746 */           index2 = -1;
/*  519:     */         }
/*  520:     */         int result;
/*  521:     */         int result;
/*  522: 750 */         if ((index1 > 0) && (index2 > 0))
/*  523:     */         {
/*  524: 751 */           result = index1 - index2;
/*  525:     */         }
/*  526:     */         else
/*  527:     */         {
/*  528:     */           int result;
/*  529: 753 */           if (index1 > 0)
/*  530:     */           {
/*  531: 754 */             result = -1;
/*  532:     */           }
/*  533:     */           else
/*  534:     */           {
/*  535:     */             int result;
/*  536: 756 */             if (index2 > 0)
/*  537:     */             {
/*  538: 757 */               result = 1;
/*  539:     */             }
/*  540:     */             else
/*  541:     */             {
/*  542:     */               int result;
/*  543: 759 */               if (index1 == index2) {
/*  544: 760 */                 result = 0;
/*  545:     */               } else {
/*  546: 763 */                 result = index2 - index1;
/*  547:     */               }
/*  548:     */             }
/*  549:     */           }
/*  550:     */         }
/*  551: 766 */         return result;
/*  552:     */       }
/*  553:     */     };
/*  554:     */   }
/*  555:     */   
/*  556:     */   public HtmlElement getElementByAccessKey(char accessKey)
/*  557:     */   {
/*  558: 784 */     List<HtmlElement> elements = getElementsByAccessKey(accessKey);
/*  559: 785 */     if (elements.isEmpty()) {
/*  560: 786 */       return null;
/*  561:     */     }
/*  562: 788 */     return (HtmlElement)elements.get(0);
/*  563:     */   }
/*  564:     */   
/*  565:     */   public List<HtmlElement> getElementsByAccessKey(char accessKey)
/*  566:     */   {
/*  567: 809 */     List<HtmlElement> elements = new ArrayList();
/*  568:     */     
/*  569: 811 */     String searchString = Character.toString(accessKey).toLowerCase();
/*  570: 812 */     List<String> acceptableTagNames = Arrays.asList(new String[] { "a", "area", "button", "input", "label", "legend", "textarea" });
/*  571: 815 */     for (HtmlElement element : getHtmlElementDescendants()) {
/*  572: 816 */       if (acceptableTagNames.contains(element.getTagName()))
/*  573:     */       {
/*  574: 817 */         String accessKeyAttribute = element.getAttribute("accesskey");
/*  575: 818 */         if (searchString.equalsIgnoreCase(accessKeyAttribute)) {
/*  576: 819 */           elements.add(element);
/*  577:     */         }
/*  578:     */       }
/*  579:     */     }
/*  580: 824 */     return elements;
/*  581:     */   }
/*  582:     */   
/*  583:     */   public ScriptResult executeJavaScript(String sourceCode)
/*  584:     */   {
/*  585: 838 */     return executeJavaScriptIfPossible(sourceCode, "injected script", 1);
/*  586:     */   }
/*  587:     */   
/*  588:     */   public ScriptResult executeJavaScriptIfPossible(String sourceCode, String sourceName, int startLine)
/*  589:     */   {
/*  590: 862 */     if (!getWebClient().isJavaScriptEnabled()) {
/*  591: 863 */       return new ScriptResult(null, this);
/*  592:     */     }
/*  593: 866 */     if (StringUtils.startsWithIgnoreCase(sourceCode, "javascript:")) {
/*  594: 867 */       sourceCode = sourceCode.substring("javascript:".length());
/*  595:     */     }
/*  596: 870 */     Object result = getWebClient().getJavaScriptEngine().execute(this, sourceCode, sourceName, startLine);
/*  597: 871 */     return new ScriptResult(result, getWebClient().getCurrentWindow().getEnclosedPage());
/*  598:     */   }
/*  599:     */   
/*  600:     */   public ScriptResult executeJavaScriptFunctionIfPossible(Function function, Scriptable thisObject, Object[] args, DomNode htmlElementScope)
/*  601:     */   {
/*  602: 891 */     if (!getWebClient().isJavaScriptEnabled()) {
/*  603: 892 */       return new ScriptResult(null, this);
/*  604:     */     }
/*  605: 895 */     JavaScriptEngine engine = getWebClient().getJavaScriptEngine();
/*  606: 896 */     Object result = engine.callFunction(this, function, thisObject, args, htmlElementScope);
/*  607:     */     
/*  608: 898 */     return new ScriptResult(result, getWebClient().getCurrentWindow().getEnclosedPage());
/*  609:     */   }
/*  610:     */   
/*  611:     */   static enum JavaScriptLoadResult
/*  612:     */   {
/*  613: 904 */     NOOP,  SUCCESS,  DOWNLOAD_ERROR,  COMPILATION_ERROR;
/*  614:     */     
/*  615:     */     private JavaScriptLoadResult() {}
/*  616:     */   }
/*  617:     */   
/*  618:     */   JavaScriptLoadResult loadExternalJavaScriptFile(String srcAttribute, String charset)
/*  619:     */     throws FailingHttpStatusCodeException
/*  620:     */   {
/*  621: 926 */     WebClient client = getWebClient();
/*  622: 927 */     if ((StringUtils.isBlank(srcAttribute)) || (!client.isJavaScriptEnabled())) {
/*  623: 928 */       return JavaScriptLoadResult.NOOP;
/*  624:     */     }
/*  625:     */     URL scriptURL;
/*  626:     */     try
/*  627:     */     {
/*  628: 933 */       scriptURL = getFullyQualifiedUrl(srcAttribute);
/*  629: 934 */       if ("javascript".equals(scriptURL.getProtocol()))
/*  630:     */       {
/*  631: 935 */         LOG.info("Ignoring script src [" + srcAttribute + "]");
/*  632: 936 */         return JavaScriptLoadResult.NOOP;
/*  633:     */       }
/*  634:     */     }
/*  635:     */     catch (MalformedURLException e)
/*  636:     */     {
/*  637: 940 */       LOG.error("Unable to build URL for script src tag [" + srcAttribute + "]");
/*  638: 941 */       JavaScriptErrorListener javaScriptErrorListener = client.getJavaScriptErrorListener();
/*  639: 942 */       if (javaScriptErrorListener != null) {
/*  640: 943 */         javaScriptErrorListener.malformedScriptURL(this, srcAttribute, e);
/*  641:     */       }
/*  642: 945 */       return JavaScriptLoadResult.NOOP;
/*  643:     */     }
/*  644:     */     Script script;
/*  645:     */     try
/*  646:     */     {
/*  647: 950 */       script = loadJavaScriptFromUrl(scriptURL, charset);
/*  648:     */     }
/*  649:     */     catch (IOException e)
/*  650:     */     {
/*  651: 953 */       LOG.error("Error loading JavaScript from [" + scriptURL + "].", e);
/*  652: 954 */       JavaScriptErrorListener javaScriptErrorListener = client.getJavaScriptErrorListener();
/*  653: 955 */       if (javaScriptErrorListener != null) {
/*  654: 956 */         javaScriptErrorListener.loadScriptError(this, scriptURL, e);
/*  655:     */       }
/*  656: 958 */       return JavaScriptLoadResult.DOWNLOAD_ERROR;
/*  657:     */     }
/*  658:     */     catch (FailingHttpStatusCodeException e)
/*  659:     */     {
/*  660: 961 */       LOG.error("Error loading JavaScript from [" + scriptURL + "].", e);
/*  661: 962 */       JavaScriptErrorListener javaScriptErrorListener = client.getJavaScriptErrorListener();
/*  662: 963 */       if (javaScriptErrorListener != null) {
/*  663: 964 */         javaScriptErrorListener.loadScriptError(this, scriptURL, e);
/*  664:     */       }
/*  665: 966 */       throw e;
/*  666:     */     }
/*  667: 969 */     if (script == null) {
/*  668: 970 */       return JavaScriptLoadResult.COMPILATION_ERROR;
/*  669:     */     }
/*  670: 973 */     client.getJavaScriptEngine().execute(this, script);
/*  671: 974 */     return JavaScriptLoadResult.SUCCESS;
/*  672:     */   }
/*  673:     */   
/*  674:     */   private Script loadJavaScriptFromUrl(URL url, String charset)
/*  675:     */     throws IOException, FailingHttpStatusCodeException
/*  676:     */   {
/*  677: 992 */     String scriptEncoding = charset;
/*  678: 993 */     String pageEncoding = getPageEncoding();
/*  679: 994 */     WebRequest referringRequest = getWebResponse().getWebRequest();
/*  680:     */     
/*  681: 996 */     WebClient client = getWebClient();
/*  682: 997 */     Cache cache = client.getCache();
/*  683:     */     
/*  684: 999 */     WebRequest request = new WebRequest(url);
/*  685:1000 */     request.setAdditionalHeaders(new HashMap(referringRequest.getAdditionalHeaders()));
/*  686:1001 */     request.setAdditionalHeader("Referer", referringRequest.getUrl().toString());
/*  687:     */     
/*  688:1003 */     Object cachedScript = cache.getCachedObject(request);
/*  689:1004 */     if ((cachedScript instanceof Script)) {
/*  690:1005 */       return (Script)cachedScript;
/*  691:     */     }
/*  692:1008 */     WebResponse response = client.loadWebResponse(request);
/*  693:1009 */     client.printContentIfNecessary(response);
/*  694:1010 */     client.throwFailingHttpStatusCodeExceptionIfNecessary(response);
/*  695:     */     
/*  696:1012 */     int statusCode = response.getStatusCode();
/*  697:1013 */     boolean successful = (statusCode >= 200) && (statusCode < 300);
/*  698:1014 */     boolean noContent = statusCode == 204;
/*  699:1015 */     if ((!successful) || (noContent)) {
/*  700:1016 */       throw new IOException("Unable to download JavaScript from '" + url + "' (status " + statusCode + ").");
/*  701:     */     }
/*  702:1020 */     String contentType = response.getContentType();
/*  703:1021 */     if ((!"application/javascript".equalsIgnoreCase(contentType)) && (!"application/ecmascript".equalsIgnoreCase(contentType))) {
/*  704:1024 */       if (("text/javascript".equals(contentType)) || ("text/ecmascript".equals(contentType)) || ("application/x-javascript".equalsIgnoreCase(contentType))) {
/*  705:1027 */         getWebClient().getIncorrectnessListener().notify("Obsolete content type encountered: '" + contentType + "'.", this);
/*  706:     */       } else {
/*  707:1031 */         getWebClient().getIncorrectnessListener().notify("Expected content type of 'application/javascript' or 'application/ecmascript' for remotely loaded JavaScript element at '" + url + "', " + "but got '" + contentType + "'.", this);
/*  708:     */       }
/*  709:     */     }
/*  710:1038 */     if (StringUtils.isEmpty(scriptEncoding))
/*  711:     */     {
/*  712:1039 */       String contentCharset = response.getContentCharset();
/*  713:1040 */       if (!contentCharset.equals("ISO-8859-1")) {
/*  714:1041 */         scriptEncoding = contentCharset;
/*  715:1043 */       } else if (!pageEncoding.equals("ISO-8859-1")) {
/*  716:1044 */         scriptEncoding = pageEncoding;
/*  717:     */       } else {
/*  718:1047 */         scriptEncoding = "ISO-8859-1";
/*  719:     */       }
/*  720:     */     }
/*  721:1051 */     String scriptCode = response.getContentAsString(scriptEncoding);
/*  722:1052 */     JavaScriptEngine javaScriptEngine = client.getJavaScriptEngine();
/*  723:1053 */     Script script = javaScriptEngine.compile(this, scriptCode, url.toExternalForm(), 1);
/*  724:1054 */     if (script != null) {
/*  725:1055 */       cache.cacheIfPossible(request, response, script);
/*  726:     */     }
/*  727:1058 */     return script;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public String getTitleText()
/*  731:     */   {
/*  732:1067 */     HtmlTitle titleElement = getTitleElement();
/*  733:1068 */     if (titleElement != null) {
/*  734:1069 */       return titleElement.asText();
/*  735:     */     }
/*  736:1071 */     return "";
/*  737:     */   }
/*  738:     */   
/*  739:     */   public void setTitleText(String message)
/*  740:     */   {
/*  741:1080 */     HtmlTitle titleElement = getTitleElement();
/*  742:1081 */     if (titleElement == null)
/*  743:     */     {
/*  744:1082 */       if (LOG.isDebugEnabled()) {
/*  745:1083 */         LOG.debug("No title element, creating one");
/*  746:     */       }
/*  747:1085 */       HtmlHead head = (HtmlHead)getFirstChildElement(getDocumentElement(), HtmlHead.class);
/*  748:1086 */       if (head == null) {
/*  749:1088 */         throw new IllegalStateException("Headelement was not defined for this page");
/*  750:     */       }
/*  751:1090 */       Map<String, DomAttr> emptyMap = Collections.emptyMap();
/*  752:1091 */       titleElement = new HtmlTitle(null, "title", this, emptyMap);
/*  753:1092 */       if (head.getFirstChild() != null) {
/*  754:1093 */         head.getFirstChild().insertBefore(titleElement);
/*  755:     */       } else {
/*  756:1096 */         head.appendChild(titleElement);
/*  757:     */       }
/*  758:     */     }
/*  759:1100 */     titleElement.setNodeValue(message);
/*  760:     */   }
/*  761:     */   
/*  762:     */   private HtmlElement getFirstChildElement(HtmlElement startElement, Class<?> clazz)
/*  763:     */   {
/*  764:1110 */     for (HtmlElement element : startElement.getChildElements()) {
/*  765:1111 */       if (clazz.isInstance(element)) {
/*  766:1112 */         return element;
/*  767:     */       }
/*  768:     */     }
/*  769:1116 */     return null;
/*  770:     */   }
/*  771:     */   
/*  772:     */   private HtmlTitle getTitleElement()
/*  773:     */   {
/*  774:1125 */     HtmlHead head = (HtmlHead)getFirstChildElement(getDocumentElement(), HtmlHead.class);
/*  775:1126 */     if (head != null) {
/*  776:1127 */       return (HtmlTitle)getFirstChildElement(head, HtmlTitle.class);
/*  777:     */     }
/*  778:1130 */     return null;
/*  779:     */   }
/*  780:     */   
/*  781:     */   private boolean executeEventHandlersIfNeeded(String eventType)
/*  782:     */   {
/*  783:1140 */     if (!getWebClient().isJavaScriptEnabled()) {
/*  784:1141 */       return true;
/*  785:     */     }
/*  786:1145 */     WebWindow window = getEnclosingWindow();
/*  787:1146 */     Window jsWindow = (Window)window.getScriptObject();
/*  788:1147 */     if (jsWindow != null)
/*  789:     */     {
/*  790:1148 */       HtmlElement element = getDocumentElement();
/*  791:1149 */       if (element == null)
/*  792:     */       {
/*  793:1151 */         StringBuilder sb = new StringBuilder("No document element (");
/*  794:1152 */         sb.append(getUrl()).append(")\n");
/*  795:     */         try
/*  796:     */         {
/*  797:1154 */           sb.append(asXml());
/*  798:     */         }
/*  799:     */         catch (Exception e) {}
/*  800:1159 */         throw new NullPointerException(sb.toString());
/*  801:     */       }
/*  802:1161 */       Event event = new Event(element, eventType);
/*  803:1162 */       element.fireEvent(event);
/*  804:1163 */       if (!isOnbeforeunloadAccepted(this, event)) {
/*  805:1164 */         return false;
/*  806:     */       }
/*  807:     */     }
/*  808:1169 */     if ((window instanceof FrameWindow))
/*  809:     */     {
/*  810:1170 */       FrameWindow fw = (FrameWindow)window;
/*  811:1171 */       BaseFrame frame = fw.getFrameElement();
/*  812:1172 */       if (frame.hasEventHandlers("on" + eventType))
/*  813:     */       {
/*  814:1173 */         if (LOG.isDebugEnabled()) {
/*  815:1174 */           LOG.debug("Executing on" + eventType + " handler for " + frame);
/*  816:     */         }
/*  817:1176 */         Event event = new Event(frame, eventType);
/*  818:1177 */         ((com.gargoylesoftware.htmlunit.javascript.host.Node)frame.getScriptObject()).executeEvent(event);
/*  819:1178 */         if (!isOnbeforeunloadAccepted((HtmlPage)frame.getPage(), event)) {
/*  820:1179 */           return false;
/*  821:     */         }
/*  822:     */       }
/*  823:     */     }
/*  824:1184 */     return true;
/*  825:     */   }
/*  826:     */   
/*  827:     */   private boolean isOnbeforeunloadAccepted(HtmlPage page, Event event)
/*  828:     */   {
/*  829:1188 */     if ((event.jsxGet_type().equals("beforeunload")) && (event.jsxGet_returnValue() != null))
/*  830:     */     {
/*  831:1189 */       OnbeforeunloadHandler handler = getWebClient().getOnbeforeunloadHandler();
/*  832:1190 */       if (handler == null)
/*  833:     */       {
/*  834:1191 */         LOG.warn("document.onbeforeunload() returned a string in event.returnValue, but no onbeforeunload handler installed.");
/*  835:     */       }
/*  836:     */       else
/*  837:     */       {
/*  838:1195 */         String message = Context.toString(event.jsxGet_returnValue());
/*  839:1196 */         return handler.handleEvent(page, message);
/*  840:     */       }
/*  841:     */     }
/*  842:1199 */     return true;
/*  843:     */   }
/*  844:     */   
/*  845:     */   private void executeRefreshIfNeeded()
/*  846:     */     throws IOException
/*  847:     */   {
/*  848:1211 */     WebWindow window = getEnclosingWindow();
/*  849:1212 */     if (window == null) {
/*  850:1213 */       return;
/*  851:     */     }
/*  852:1216 */     String refreshString = getRefreshStringOrNull();
/*  853:1217 */     if ((refreshString == null) || (refreshString.length() == 0)) {
/*  854:1218 */       return;
/*  855:     */     }
/*  856:1224 */     int index = StringUtils.indexOfAnyBut(refreshString, "0123456789");
/*  857:1225 */     boolean timeOnly = index == -1;
/*  858:     */     URL url;
/*  859:     */     double time;
/*  860:     */     URL url;
/*  861:1227 */     if (timeOnly)
/*  862:     */     {
/*  863:     */       try
/*  864:     */       {
/*  865:1230 */         time = Double.parseDouble(refreshString);
/*  866:     */       }
/*  867:     */       catch (NumberFormatException e)
/*  868:     */       {
/*  869:     */         double time;
/*  870:1233 */         LOG.error("Malformed refresh string (no ';' but not a number): " + refreshString, e);
/*  871:1234 */         return;
/*  872:     */       }
/*  873:1236 */       url = getWebResponse().getWebRequest().getUrl();
/*  874:     */     }
/*  875:     */     else
/*  876:     */     {
/*  877:     */       try
/*  878:     */       {
/*  879:1241 */         time = Double.parseDouble(refreshString.substring(0, index).trim());
/*  880:     */       }
/*  881:     */       catch (NumberFormatException e)
/*  882:     */       {
/*  883:1244 */         LOG.error("Malformed refresh string (no valid number before ';') " + refreshString, e);
/*  884:1245 */         return;
/*  885:     */       }
/*  886:1247 */       index = refreshString.toLowerCase().indexOf("url=", index);
/*  887:1248 */       if (index == -1)
/*  888:     */       {
/*  889:1249 */         LOG.error("Malformed refresh string (found ';' but no 'url='): " + refreshString);
/*  890:1250 */         return;
/*  891:     */       }
/*  892:1252 */       StringBuilder buffer = new StringBuilder(refreshString.substring(index + 4));
/*  893:     */       URL url;
/*  894:1253 */       if (StringUtils.isBlank(buffer.toString()))
/*  895:     */       {
/*  896:1255 */         url = getWebResponse().getWebRequest().getUrl();
/*  897:     */       }
/*  898:     */       else
/*  899:     */       {
/*  900:1258 */         if ((buffer.charAt(0) == '"') || (buffer.charAt(0) == '\'')) {
/*  901:1259 */           buffer.deleteCharAt(0);
/*  902:     */         }
/*  903:1261 */         if ((buffer.charAt(buffer.length() - 1) == '"') || (buffer.charAt(buffer.length() - 1) == '\'')) {
/*  904:1262 */           buffer.deleteCharAt(buffer.length() - 1);
/*  905:     */         }
/*  906:1264 */         String urlString = buffer.toString();
/*  907:     */         try
/*  908:     */         {
/*  909:1266 */           url = getFullyQualifiedUrl(urlString);
/*  910:     */         }
/*  911:     */         catch (MalformedURLException e)
/*  912:     */         {
/*  913:1269 */           LOG.error("Malformed URL in refresh string: " + refreshString, e);
/*  914:1270 */           throw e;
/*  915:     */         }
/*  916:     */       }
/*  917:     */     }
/*  918:1275 */     int timeRounded = (int)time;
/*  919:1276 */     getWebClient().getRefreshHandler().handleRefresh(this, url, timeRounded);
/*  920:     */   }
/*  921:     */   
/*  922:     */   private String getRefreshStringOrNull()
/*  923:     */   {
/*  924:1285 */     Iterator i$ = getMetaTags("refresh").iterator();
/*  925:1285 */     if (i$.hasNext())
/*  926:     */     {
/*  927:1285 */       HtmlMeta meta = (HtmlMeta)i$.next();
/*  928:1286 */       return meta.getContentAttribute().trim();
/*  929:     */     }
/*  930:1288 */     return getWebResponse().getResponseHeaderValue("Refresh");
/*  931:     */   }
/*  932:     */   
/*  933:     */   private void executeDeferredScriptsIfNeeded()
/*  934:     */   {
/*  935:1295 */     if (!getWebClient().isJavaScriptEnabled()) {
/*  936:1296 */       return;
/*  937:     */     }
/*  938:1298 */     if (getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_DEFERRED))
/*  939:     */     {
/*  940:1299 */       HtmlElement doc = getDocumentElement();
/*  941:1300 */       List<HtmlElement> elements = doc.getHtmlElementsByTagName("script");
/*  942:1301 */       for (HtmlElement e : elements) {
/*  943:1302 */         if ((e instanceof HtmlScript))
/*  944:     */         {
/*  945:1303 */           HtmlScript script = (HtmlScript)e;
/*  946:1304 */           if (script.isDeferred()) {
/*  947:1305 */             script.executeScriptIfNeeded();
/*  948:     */           }
/*  949:     */         }
/*  950:     */       }
/*  951:     */     }
/*  952:     */   }
/*  953:     */   
/*  954:     */   private void setReadyStateOnDeferredScriptsIfNeeded()
/*  955:     */   {
/*  956:1316 */     if ((getWebClient().isJavaScriptEnabled()) && (getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_DEFERRED)))
/*  957:     */     {
/*  958:1318 */       List<HtmlElement> elements = getDocumentElement().getHtmlElementsByTagName("script");
/*  959:1319 */       for (HtmlElement e : elements) {
/*  960:1320 */         if ((e instanceof HtmlScript))
/*  961:     */         {
/*  962:1321 */           HtmlScript script = (HtmlScript)e;
/*  963:1322 */           if (script.isDeferred()) {
/*  964:1323 */             script.setAndExecuteReadyState("complete");
/*  965:     */           }
/*  966:     */         }
/*  967:     */       }
/*  968:     */     }
/*  969:     */   }
/*  970:     */   
/*  971:     */   public void deregisterFramesIfNeeded()
/*  972:     */   {
/*  973:1334 */     for (WebWindow window : getFrames())
/*  974:     */     {
/*  975:1335 */       getWebClient().deregisterWebWindow(window);
/*  976:1336 */       if ((window.getEnclosedPage() instanceof HtmlPage))
/*  977:     */       {
/*  978:1337 */         HtmlPage page = (HtmlPage)window.getEnclosedPage();
/*  979:1338 */         if (page != null) {
/*  980:1341 */           page.deregisterFramesIfNeeded();
/*  981:     */         }
/*  982:     */       }
/*  983:     */     }
/*  984:     */   }
/*  985:     */   
/*  986:     */   public List<FrameWindow> getFrames()
/*  987:     */   {
/*  988:1352 */     List<FrameWindow> list = new ArrayList();
/*  989:1353 */     WebWindow enclosingWindow = getEnclosingWindow();
/*  990:1354 */     for (WebWindow window : getWebClient().getWebWindows()) {
/*  991:1356 */       if ((enclosingWindow == window.getParentWindow()) && (enclosingWindow != window)) {
/*  992:1358 */         list.add((FrameWindow)window);
/*  993:     */       }
/*  994:     */     }
/*  995:1361 */     return list;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public FrameWindow getFrameByName(String name)
/*  999:     */     throws ElementNotFoundException
/* 1000:     */   {
/* 1001:1371 */     for (FrameWindow frame : getFrames()) {
/* 1002:1372 */       if (frame.getName().equals(name)) {
/* 1003:1373 */         return frame;
/* 1004:     */       }
/* 1005:     */     }
/* 1006:1377 */     throw new ElementNotFoundException("frame or iframe", "name", name);
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public HtmlElement pressAccessKey(char accessKey)
/* 1010:     */     throws IOException
/* 1011:     */   {
/* 1012:1391 */     HtmlElement element = getElementByAccessKey(accessKey);
/* 1013:1392 */     if (element != null)
/* 1014:     */     {
/* 1015:1393 */       element.focus();
/* 1016:     */       Page newPage;
/* 1017:     */       Page newPage;
/* 1018:1395 */       if ((element instanceof HtmlAnchor))
/* 1019:     */       {
/* 1020:1396 */         newPage = ((HtmlAnchor)element).click();
/* 1021:     */       }
/* 1022:     */       else
/* 1023:     */       {
/* 1024:     */         Page newPage;
/* 1025:1398 */         if ((element instanceof HtmlArea))
/* 1026:     */         {
/* 1027:1399 */           newPage = ((HtmlArea)element).click();
/* 1028:     */         }
/* 1029:     */         else
/* 1030:     */         {
/* 1031:     */           Page newPage;
/* 1032:1401 */           if ((element instanceof HtmlButton))
/* 1033:     */           {
/* 1034:1402 */             newPage = ((HtmlButton)element).click();
/* 1035:     */           }
/* 1036:     */           else
/* 1037:     */           {
/* 1038:     */             Page newPage;
/* 1039:1404 */             if ((element instanceof HtmlInput))
/* 1040:     */             {
/* 1041:1405 */               newPage = ((HtmlInput)element).click();
/* 1042:     */             }
/* 1043:     */             else
/* 1044:     */             {
/* 1045:     */               Page newPage;
/* 1046:1407 */               if ((element instanceof HtmlLabel))
/* 1047:     */               {
/* 1048:1408 */                 newPage = ((HtmlLabel)element).click();
/* 1049:     */               }
/* 1050:     */               else
/* 1051:     */               {
/* 1052:     */                 Page newPage;
/* 1053:1410 */                 if ((element instanceof HtmlLegend))
/* 1054:     */                 {
/* 1055:1411 */                   newPage = ((HtmlLegend)element).click();
/* 1056:     */                 }
/* 1057:     */                 else
/* 1058:     */                 {
/* 1059:     */                   Page newPage;
/* 1060:1413 */                   if ((element instanceof HtmlTextArea)) {
/* 1061:1414 */                     newPage = ((HtmlTextArea)element).click();
/* 1062:     */                   } else {
/* 1063:1417 */                     newPage = this;
/* 1064:     */                   }
/* 1065:     */                 }
/* 1066:     */               }
/* 1067:     */             }
/* 1068:     */           }
/* 1069:     */         }
/* 1070:     */       }
/* 1071:1420 */       if ((newPage != this) && (getFocusedElement() == element)) {
/* 1072:1422 */         getFocusedElement().blur();
/* 1073:     */       }
/* 1074:     */     }
/* 1075:1426 */     return getFocusedElement();
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public HtmlElement tabToNextElement()
/* 1079:     */   {
/* 1080:1436 */     List<HtmlElement> elements = getTabbableElements();
/* 1081:1437 */     if (elements.isEmpty())
/* 1082:     */     {
/* 1083:1438 */       setFocusedElement(null);
/* 1084:1439 */       return null;
/* 1085:     */     }
/* 1086:1443 */     HtmlElement elementWithFocus = getFocusedElement();
/* 1087:     */     HtmlElement elementToGiveFocus;
/* 1088:     */     HtmlElement elementToGiveFocus;
/* 1089:1444 */     if (elementWithFocus == null)
/* 1090:     */     {
/* 1091:1445 */       elementToGiveFocus = (HtmlElement)elements.get(0);
/* 1092:     */     }
/* 1093:     */     else
/* 1094:     */     {
/* 1095:1448 */       int index = elements.indexOf(elementWithFocus);
/* 1096:     */       HtmlElement elementToGiveFocus;
/* 1097:1449 */       if (index == -1)
/* 1098:     */       {
/* 1099:1451 */         elementToGiveFocus = (HtmlElement)elements.get(0);
/* 1100:     */       }
/* 1101:     */       else
/* 1102:     */       {
/* 1103:     */         HtmlElement elementToGiveFocus;
/* 1104:1454 */         if (index == elements.size() - 1) {
/* 1105:1455 */           elementToGiveFocus = (HtmlElement)elements.get(0);
/* 1106:     */         } else {
/* 1107:1458 */           elementToGiveFocus = (HtmlElement)elements.get(index + 1);
/* 1108:     */         }
/* 1109:     */       }
/* 1110:     */     }
/* 1111:1463 */     setFocusedElement(elementToGiveFocus);
/* 1112:1464 */     return elementToGiveFocus;
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public HtmlElement tabToPreviousElement()
/* 1116:     */   {
/* 1117:1474 */     List<HtmlElement> elements = getTabbableElements();
/* 1118:1475 */     if (elements.isEmpty())
/* 1119:     */     {
/* 1120:1476 */       setFocusedElement(null);
/* 1121:1477 */       return null;
/* 1122:     */     }
/* 1123:1481 */     HtmlElement elementWithFocus = getFocusedElement();
/* 1124:     */     HtmlElement elementToGiveFocus;
/* 1125:     */     HtmlElement elementToGiveFocus;
/* 1126:1482 */     if (elementWithFocus == null)
/* 1127:     */     {
/* 1128:1483 */       elementToGiveFocus = (HtmlElement)elements.get(elements.size() - 1);
/* 1129:     */     }
/* 1130:     */     else
/* 1131:     */     {
/* 1132:1486 */       int index = elements.indexOf(elementWithFocus);
/* 1133:     */       HtmlElement elementToGiveFocus;
/* 1134:1487 */       if (index == -1)
/* 1135:     */       {
/* 1136:1489 */         elementToGiveFocus = (HtmlElement)elements.get(elements.size() - 1);
/* 1137:     */       }
/* 1138:     */       else
/* 1139:     */       {
/* 1140:     */         HtmlElement elementToGiveFocus;
/* 1141:1492 */         if (index == 0) {
/* 1142:1493 */           elementToGiveFocus = (HtmlElement)elements.get(elements.size() - 1);
/* 1143:     */         } else {
/* 1144:1496 */           elementToGiveFocus = (HtmlElement)elements.get(index - 1);
/* 1145:     */         }
/* 1146:     */       }
/* 1147:     */     }
/* 1148:1501 */     setFocusedElement(elementToGiveFocus);
/* 1149:1502 */     return elementToGiveFocus;
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   public <E extends HtmlElement> E getHtmlElementById(String id)
/* 1153:     */     throws ElementNotFoundException
/* 1154:     */   {
/* 1155:1517 */     return getHtmlElementById(id, true);
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public <E extends HtmlElement> E getHtmlElementById(String id, boolean caseSensitive)
/* 1159:     */     throws ElementNotFoundException
/* 1160:     */   {
/* 1161:1534 */     String usedID = id;
/* 1162:1535 */     if (!caseSensitive) {
/* 1163:1536 */       for (String key : this.idMap_.keySet()) {
/* 1164:1537 */         if (key.equalsIgnoreCase(usedID))
/* 1165:     */         {
/* 1166:1538 */           usedID = key;
/* 1167:1539 */           break;
/* 1168:     */         }
/* 1169:     */       }
/* 1170:     */     }
/* 1171:1543 */     List<HtmlElement> elements = (List)this.idMap_.get(usedID);
/* 1172:1544 */     if (elements != null) {
/* 1173:1545 */       return (HtmlElement)elements.get(0);
/* 1174:     */     }
/* 1175:1547 */     throw new ElementNotFoundException("*", "id", id);
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public <E extends HtmlElement> E getElementByName(String name)
/* 1179:     */     throws ElementNotFoundException
/* 1180:     */   {
/* 1181:1561 */     List<HtmlElement> elements = (List)this.nameMap_.get(name);
/* 1182:1562 */     if (elements != null) {
/* 1183:1563 */       return (HtmlElement)elements.get(0);
/* 1184:     */     }
/* 1185:1565 */     throw new ElementNotFoundException("*", "name", name);
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   public List<HtmlElement> getElementsByName(String name)
/* 1189:     */   {
/* 1190:1577 */     List<HtmlElement> list = (List)this.nameMap_.get(name);
/* 1191:1578 */     if (list != null) {
/* 1192:1579 */       return Collections.unmodifiableList(list);
/* 1193:     */     }
/* 1194:1581 */     return Collections.emptyList();
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   public List<HtmlElement> getElementsByIdAndOrName(String idAndOrName)
/* 1198:     */   {
/* 1199:1592 */     List<HtmlElement> list1 = (List)this.idMap_.get(idAndOrName);
/* 1200:1593 */     List<HtmlElement> list2 = (List)this.nameMap_.get(idAndOrName);
/* 1201:1594 */     List<HtmlElement> list = new ArrayList();
/* 1202:1595 */     if (list1 != null) {
/* 1203:1596 */       list.addAll(list1);
/* 1204:     */     }
/* 1205:1598 */     if (list2 != null) {
/* 1206:1599 */       for (HtmlElement elt : list2) {
/* 1207:1600 */         if (!list.contains(elt)) {
/* 1208:1601 */           list.add(elt);
/* 1209:     */         }
/* 1210:     */       }
/* 1211:     */     }
/* 1212:1605 */     return list;
/* 1213:     */   }
/* 1214:     */   
/* 1215:     */   void addMappedElement(HtmlElement element)
/* 1216:     */   {
/* 1217:1613 */     addMappedElement(element, false);
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   void addMappedElement(HtmlElement element, boolean recurse)
/* 1221:     */   {
/* 1222:1622 */     if (isDescendant(element))
/* 1223:     */     {
/* 1224:1623 */       addElement(this.idMap_, element, "id", recurse);
/* 1225:1624 */       addElement(this.nameMap_, element, "name", recurse);
/* 1226:     */     }
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   private boolean isDescendant(HtmlElement element)
/* 1230:     */   {
/* 1231:1632 */     for (DomNode parent = element; parent != null; parent = parent.getParentNode()) {
/* 1232:1633 */       if (parent == this) {
/* 1233:1634 */         return true;
/* 1234:     */       }
/* 1235:     */     }
/* 1236:1637 */     return false;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   private void addElement(Map<String, List<HtmlElement>> map, HtmlElement element, String attribute, boolean recurse)
/* 1240:     */   {
/* 1241:1642 */     String value = element.getAttribute(attribute);
/* 1242:1643 */     if (DomElement.ATTRIBUTE_NOT_DEFINED != value)
/* 1243:     */     {
/* 1244:1644 */       List<HtmlElement> elements = (List)map.get(value);
/* 1245:1645 */       if (elements == null)
/* 1246:     */       {
/* 1247:1646 */         elements = new ArrayList();
/* 1248:1647 */         elements.add(element);
/* 1249:1648 */         map.put(value, elements);
/* 1250:     */       }
/* 1251:1650 */       else if (!elements.contains(element))
/* 1252:     */       {
/* 1253:1651 */         elements.add(element);
/* 1254:     */       }
/* 1255:     */     }
/* 1256:1654 */     if (recurse) {
/* 1257:1655 */       for (HtmlElement child : element.getChildElements()) {
/* 1258:1656 */         addElement(map, child, attribute, true);
/* 1259:     */       }
/* 1260:     */     }
/* 1261:     */   }
/* 1262:     */   
/* 1263:     */   void removeMappedElement(HtmlElement element)
/* 1264:     */   {
/* 1265:1666 */     removeMappedElement(element, false, false);
/* 1266:     */   }
/* 1267:     */   
/* 1268:     */   void removeMappedElement(HtmlElement element, boolean recurse, boolean descendant)
/* 1269:     */   {
/* 1270:1676 */     if ((descendant) || (isDescendant(element)))
/* 1271:     */     {
/* 1272:1677 */       removeElement(this.idMap_, element, "id", recurse);
/* 1273:1678 */       removeElement(this.nameMap_, element, "name", recurse);
/* 1274:     */     }
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   private void removeElement(Map<String, List<HtmlElement>> map, HtmlElement element, String att, boolean recurse)
/* 1278:     */   {
/* 1279:1684 */     String value = element.getAttribute(att);
/* 1280:1685 */     if (!StringUtils.isEmpty(value))
/* 1281:     */     {
/* 1282:1686 */       List<HtmlElement> elements = (List)map.remove(value);
/* 1283:1687 */       if ((elements != null) && ((elements.size() != 1) || (!elements.contains(element))))
/* 1284:     */       {
/* 1285:1688 */         elements.remove(element);
/* 1286:1689 */         map.put(value, elements);
/* 1287:     */       }
/* 1288:     */     }
/* 1289:1692 */     if (recurse) {
/* 1290:1693 */       for (HtmlElement child : element.getChildElements()) {
/* 1291:1694 */         removeElement(map, child, att, true);
/* 1292:     */       }
/* 1293:     */     }
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   void notifyNodeAdded(DomNode node)
/* 1297:     */   {
/* 1298:1705 */     if ((node instanceof HtmlElement))
/* 1299:     */     {
/* 1300:1706 */       addMappedElement((HtmlElement)node, true);
/* 1301:1708 */       if ("base".equals(node.getNodeName())) {
/* 1302:1709 */         calculateBase();
/* 1303:     */       }
/* 1304:     */     }
/* 1305:1712 */     node.onAddedToPage();
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   private void calculateBase()
/* 1309:     */   {
/* 1310:1716 */     List<HtmlBase> baseElements = getDocumentElement().getHtmlElementsByTagName("base");
/* 1311:1717 */     switch (baseElements.size())
/* 1312:     */     {
/* 1313:     */     case 0: 
/* 1314:1719 */       this.base_ = null;
/* 1315:1720 */       break;
/* 1316:     */     case 1: 
/* 1317:1723 */       this.base_ = ((HtmlBase)baseElements.get(0));
/* 1318:1724 */       break;
/* 1319:     */     default: 
/* 1320:1727 */       this.base_ = ((HtmlBase)baseElements.get(0));
/* 1321:1728 */       notifyIncorrectness("Multiple 'base' detected, only the first is used.");
/* 1322:     */     }
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   void notifyNodeRemoved(DomNode node)
/* 1326:     */   {
/* 1327:1738 */     if ((node instanceof HtmlElement))
/* 1328:     */     {
/* 1329:1739 */       removeMappedElement((HtmlElement)node, true, true);
/* 1330:1740 */       if ("base".equals(node.getNodeName())) {
/* 1331:1741 */         calculateBase();
/* 1332:     */       }
/* 1333:     */     }
/* 1334:     */   }
/* 1335:     */   
/* 1336:     */   void loadFrames()
/* 1337:     */     throws FailingHttpStatusCodeException
/* 1338:     */   {
/* 1339:1753 */     for (FrameWindow w : getFrames())
/* 1340:     */     {
/* 1341:1754 */       BaseFrame frame = w.getFrameElement();
/* 1342:1758 */       if ((WebClient.URL_ABOUT_BLANK == frame.getEnclosedPage().getWebResponse().getWebRequest().getUrl()) && (!frame.isContentLoaded())) {
/* 1343:1760 */         frame.loadInnerPage();
/* 1344:     */       }
/* 1345:     */     }
/* 1346:     */   }
/* 1347:     */   
/* 1348:     */   public String toString()
/* 1349:     */   {
/* 1350:1771 */     StringBuilder buffer = new StringBuilder();
/* 1351:1772 */     buffer.append("HtmlPage(");
/* 1352:1773 */     buffer.append(getWebResponse().getWebRequest().getUrl());
/* 1353:1774 */     buffer.append(")@");
/* 1354:1775 */     buffer.append(hashCode());
/* 1355:1776 */     return buffer.toString();
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   public boolean setFocusedElement(HtmlElement newElement)
/* 1359:     */   {
/* 1360:1792 */     return setFocusedElement(newElement, false);
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   public boolean setFocusedElement(HtmlElement newElement, boolean windowActivated)
/* 1364:     */   {
/* 1365:1809 */     if ((this.elementWithFocus_ == newElement) && (!windowActivated)) {
/* 1366:1811 */       return true;
/* 1367:     */     }
/* 1368:1813 */     if ((newElement != null) && (newElement.getPage() != this)) {
/* 1369:1814 */       throw new IllegalArgumentException("Can't move focus to an element from a different page.");
/* 1370:     */     }
/* 1371:1817 */     HtmlElement oldFocusedElement = this.elementWithFocus_;
/* 1372:1818 */     this.elementWithFocus_ = null;
/* 1373:1820 */     if (!windowActivated)
/* 1374:     */     {
/* 1375:1821 */       if (oldFocusedElement != null) {
/* 1376:1822 */         oldFocusedElement.fireEvent("focusout");
/* 1377:     */       }
/* 1378:1825 */       if (newElement != null) {
/* 1379:1826 */         newElement.fireEvent("focusin");
/* 1380:     */       }
/* 1381:1829 */       if (oldFocusedElement != null) {
/* 1382:1830 */         if (getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.BLUR_BEFORE_ONCHANGE))
/* 1383:     */         {
/* 1384:1831 */           oldFocusedElement.fireEvent("blur");
/* 1385:1832 */           oldFocusedElement.removeFocus();
/* 1386:     */         }
/* 1387:     */         else
/* 1388:     */         {
/* 1389:1835 */           oldFocusedElement.removeFocus();
/* 1390:1836 */           oldFocusedElement.fireEvent("blur");
/* 1391:     */         }
/* 1392:     */       }
/* 1393:     */     }
/* 1394:1841 */     this.elementWithFocus_ = newElement;
/* 1395:1843 */     if (((this.elementWithFocus_ instanceof SelectableTextInput)) && (getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.PAGE_SELECTION_RANGE_FROM_SELECTABLE_TEXT_INPUT)))
/* 1396:     */     {
/* 1397:1845 */       SelectableTextInput sti = (SelectableTextInput)this.elementWithFocus_;
/* 1398:1846 */       setSelectionRange(new SimpleRange(sti, sti.getSelectionStart(), sti, sti.getSelectionEnd()));
/* 1399:     */     }
/* 1400:1849 */     if (this.elementWithFocus_ != null)
/* 1401:     */     {
/* 1402:1850 */       this.elementWithFocus_.focus();
/* 1403:1851 */       this.elementWithFocus_.fireEvent("focus");
/* 1404:     */     }
/* 1405:1856 */     return this == getEnclosingWindow().getEnclosedPage();
/* 1406:     */   }
/* 1407:     */   
/* 1408:     */   public HtmlElement getFocusedElement()
/* 1409:     */   {
/* 1410:1865 */     return this.elementWithFocus_;
/* 1411:     */   }
/* 1412:     */   
/* 1413:     */   protected List<HtmlMeta> getMetaTags(String httpEquiv)
/* 1414:     */   {
/* 1415:1874 */     String nameLC = httpEquiv.toLowerCase();
/* 1416:1875 */     List<HtmlMeta> tags = getDocumentElement().getHtmlElementsByTagName("meta");
/* 1417:1876 */     for (Iterator<HtmlMeta> iter = tags.iterator(); iter.hasNext();)
/* 1418:     */     {
/* 1419:1877 */       HtmlMeta element = (HtmlMeta)iter.next();
/* 1420:1878 */       if (!nameLC.equals(element.getHttpEquivAttribute().toLowerCase())) {
/* 1421:1879 */         iter.remove();
/* 1422:     */       }
/* 1423:     */     }
/* 1424:1882 */     return tags;
/* 1425:     */   }
/* 1426:     */   
/* 1427:     */   void setCheckedRadioButton(HtmlRadioButtonInput radioButtonInput)
/* 1428:     */   {
/* 1429:1893 */     List<HtmlRadioButtonInput> pageInputs = getByXPath("//input[lower-case(@type)='radio' and @name='" + radioButtonInput.getNameAttribute() + "']");
/* 1430:     */     
/* 1431:     */ 
/* 1432:1896 */     List<HtmlRadioButtonInput> formInputs = getByXPath("//form//input[lower-case(@type)='radio' and @name='" + radioButtonInput.getNameAttribute() + "']");
/* 1433:     */     
/* 1434:     */ 
/* 1435:     */ 
/* 1436:1900 */     pageInputs.removeAll(formInputs);
/* 1437:     */     
/* 1438:1902 */     boolean found = false;
/* 1439:1903 */     for (HtmlRadioButtonInput input : pageInputs) {
/* 1440:1904 */       if (input == radioButtonInput)
/* 1441:     */       {
/* 1442:1905 */         input.setAttribute("checked", "checked");
/* 1443:1906 */         found = true;
/* 1444:     */       }
/* 1445:     */       else
/* 1446:     */       {
/* 1447:1909 */         input.removeAttribute("checked");
/* 1448:     */       }
/* 1449:     */     }
/* 1450:1912 */     for (HtmlRadioButtonInput input : formInputs) {
/* 1451:1913 */       if (input == radioButtonInput) {
/* 1452:1914 */         found = true;
/* 1453:     */       }
/* 1454:     */     }
/* 1455:1917 */     if (!found) {
/* 1456:1918 */       radioButtonInput.setAttribute("checked", "checked");
/* 1457:     */     }
/* 1458:     */   }
/* 1459:     */   
/* 1460:     */   protected HtmlPage clone()
/* 1461:     */   {
/* 1462:1930 */     HtmlPage result = (HtmlPage)super.clone();
/* 1463:1931 */     result.elementWithFocus_ = null;
/* 1464:1932 */     result.idMap_ = new HashMap();
/* 1465:1933 */     result.nameMap_ = new HashMap();
/* 1466:1934 */     return result;
/* 1467:     */   }
/* 1468:     */   
/* 1469:     */   public HtmlPage cloneNode(boolean deep)
/* 1470:     */   {
/* 1471:1943 */     HtmlPage result = (HtmlPage)super.cloneNode(deep);
/* 1472:1944 */     result.setScriptObject(getScriptObject());
/* 1473:1945 */     if (deep) {
/* 1474:1947 */       for (HtmlElement child : result.getHtmlElementDescendants())
/* 1475:     */       {
/* 1476:1948 */         removeMappedElement(child);
/* 1477:1949 */         result.addMappedElement(child);
/* 1478:     */       }
/* 1479:     */     }
/* 1480:1952 */     return result;
/* 1481:     */   }
/* 1482:     */   
/* 1483:     */   public void addHtmlAttributeChangeListener(HtmlAttributeChangeListener listener)
/* 1484:     */   {
/* 1485:1963 */     WebAssert.notNull("listener", listener);
/* 1486:1964 */     synchronized (this.lock_)
/* 1487:     */     {
/* 1488:1965 */       if (this.attributeListeners_ == null) {
/* 1489:1966 */         this.attributeListeners_ = new ArrayList();
/* 1490:     */       }
/* 1491:1968 */       if (!this.attributeListeners_.contains(listener)) {
/* 1492:1969 */         this.attributeListeners_.add(listener);
/* 1493:     */       }
/* 1494:     */     }
/* 1495:     */   }
/* 1496:     */   
/* 1497:     */   public void removeHtmlAttributeChangeListener(HtmlAttributeChangeListener listener)
/* 1498:     */   {
/* 1499:1983 */     WebAssert.notNull("listener", listener);
/* 1500:1984 */     synchronized (this.lock_)
/* 1501:     */     {
/* 1502:1985 */       if (this.attributeListeners_ != null) {
/* 1503:1986 */         this.attributeListeners_.remove(listener);
/* 1504:     */       }
/* 1505:     */     }
/* 1506:     */   }
/* 1507:     */   
/* 1508:     */   void fireHtmlAttributeAdded(HtmlAttributeChangeEvent event)
/* 1509:     */   {
/* 1510:1996 */     List<HtmlAttributeChangeListener> listeners = safeGetAttributeListeners();
/* 1511:1997 */     if (listeners != null) {
/* 1512:1998 */       for (HtmlAttributeChangeListener listener : listeners) {
/* 1513:1999 */         listener.attributeAdded(event);
/* 1514:     */       }
/* 1515:     */     }
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   void fireHtmlAttributeReplaced(HtmlAttributeChangeEvent event)
/* 1519:     */   {
/* 1520:2009 */     List<HtmlAttributeChangeListener> listeners = safeGetAttributeListeners();
/* 1521:2010 */     if (listeners != null) {
/* 1522:2011 */       for (HtmlAttributeChangeListener listener : listeners) {
/* 1523:2012 */         listener.attributeReplaced(event);
/* 1524:     */       }
/* 1525:     */     }
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   void fireHtmlAttributeRemoved(HtmlAttributeChangeEvent event)
/* 1529:     */   {
/* 1530:2022 */     List<HtmlAttributeChangeListener> listeners = safeGetAttributeListeners();
/* 1531:2023 */     if (listeners != null) {
/* 1532:2024 */       for (HtmlAttributeChangeListener listener : listeners) {
/* 1533:2025 */         listener.attributeRemoved(event);
/* 1534:     */       }
/* 1535:     */     }
/* 1536:     */   }
/* 1537:     */   
/* 1538:     */   private List<HtmlAttributeChangeListener> safeGetAttributeListeners()
/* 1539:     */   {
/* 1540:2031 */     synchronized (this.lock_)
/* 1541:     */     {
/* 1542:2032 */       if (this.attributeListeners_ != null) {
/* 1543:2033 */         return new ArrayList(this.attributeListeners_);
/* 1544:     */       }
/* 1545:2035 */       return null;
/* 1546:     */     }
/* 1547:     */   }
/* 1548:     */   
/* 1549:     */   protected void checkChildHierarchy(org.w3c.dom.Node newChild)
/* 1550:     */     throws DOMException
/* 1551:     */   {
/* 1552:2044 */     if ((newChild instanceof Element))
/* 1553:     */     {
/* 1554:2045 */       if (getDocumentElement() != null) {
/* 1555:2046 */         throw new DOMException((short)3, "The Document may only have a single child Element.");
/* 1556:     */       }
/* 1557:     */     }
/* 1558:2050 */     else if ((newChild instanceof DocumentType))
/* 1559:     */     {
/* 1560:2051 */       if (getDoctype() != null) {
/* 1561:2052 */         throw new DOMException((short)3, "The Document may only have a single child DocumentType.");
/* 1562:     */       }
/* 1563:     */     }
/* 1564:2056 */     else if ((!(newChild instanceof Comment)) && (!(newChild instanceof ProcessingInstruction))) {
/* 1565:2057 */       throw new DOMException((short)3, "The Document may not have a child of this type: " + newChild.getNodeType());
/* 1566:     */     }
/* 1567:2060 */     super.checkChildHierarchy(newChild);
/* 1568:     */   }
/* 1569:     */   
/* 1570:     */   public boolean isOnbeforeunloadAccepted()
/* 1571:     */   {
/* 1572:2069 */     return executeEventHandlersIfNeeded("beforeunload");
/* 1573:     */   }
/* 1574:     */   
/* 1575:     */   public boolean isBeingParsed()
/* 1576:     */   {
/* 1577:2077 */     return this.parserCount_ > 0;
/* 1578:     */   }
/* 1579:     */   
/* 1580:     */   void registerParsingStart()
/* 1581:     */   {
/* 1582:2084 */     this.parserCount_ += 1;
/* 1583:     */   }
/* 1584:     */   
/* 1585:     */   void registerParsingEnd()
/* 1586:     */   {
/* 1587:2091 */     this.parserCount_ -= 1;
/* 1588:     */   }
/* 1589:     */   
/* 1590:     */   boolean isParsingHtmlSnippet()
/* 1591:     */   {
/* 1592:2105 */     return this.snippetParserCount_ > 0;
/* 1593:     */   }
/* 1594:     */   
/* 1595:     */   void registerSnippetParsingStart()
/* 1596:     */   {
/* 1597:2112 */     this.snippetParserCount_ += 1;
/* 1598:     */   }
/* 1599:     */   
/* 1600:     */   void registerSnippetParsingEnd()
/* 1601:     */   {
/* 1602:2119 */     this.snippetParserCount_ -= 1;
/* 1603:2122 */     if (0 == this.snippetParserCount_) {
/* 1604:2123 */       loadFrames();
/* 1605:     */     }
/* 1606:     */   }
/* 1607:     */   
/* 1608:     */   boolean isParsingInlineHtmlSnippet()
/* 1609:     */   {
/* 1610:2136 */     return this.inlineSnippetParserCount_ > 0;
/* 1611:     */   }
/* 1612:     */   
/* 1613:     */   void registerInlineSnippetParsingStart()
/* 1614:     */   {
/* 1615:2143 */     this.inlineSnippetParserCount_ += 1;
/* 1616:     */   }
/* 1617:     */   
/* 1618:     */   void registerInlineSnippetParsingEnd()
/* 1619:     */   {
/* 1620:2150 */     this.inlineSnippetParserCount_ -= 1;
/* 1621:     */   }
/* 1622:     */   
/* 1623:     */   public Page refresh()
/* 1624:     */     throws IOException
/* 1625:     */   {
/* 1626:2159 */     return getWebClient().getPage(getWebResponse().getWebRequest());
/* 1627:     */   }
/* 1628:     */   
/* 1629:     */   public void writeInParsedStream(String string)
/* 1630:     */   {
/* 1631:2171 */     this.builder_.pushInputString(string);
/* 1632:     */   }
/* 1633:     */   
/* 1634:     */   void setBuilder(HTMLParser.HtmlUnitDOMBuilder htmlUnitDOMBuilder)
/* 1635:     */   {
/* 1636:2179 */     this.builder_ = htmlUnitDOMBuilder;
/* 1637:     */   }
/* 1638:     */   
/* 1639:     */   HTMLParser.HtmlUnitDOMBuilder getBuilder()
/* 1640:     */   {
/* 1641:2187 */     return this.builder_;
/* 1642:     */   }
/* 1643:     */   
/* 1644:     */   public List<Range> getSelectionRanges()
/* 1645:     */   {
/* 1646:2199 */     return this.selectionRanges_;
/* 1647:     */   }
/* 1648:     */   
/* 1649:     */   public void setSelectionRange(Range selectionRange)
/* 1650:     */   {
/* 1651:2210 */     this.selectionRanges_.clear();
/* 1652:2211 */     this.selectionRanges_.add(selectionRange);
/* 1653:     */   }
/* 1654:     */   
/* 1655:     */   public Map<String, String> getNamespaces()
/* 1656:     */   {
/* 1657:2220 */     NamedNodeMap attributes = getDocumentElement().getAttributes();
/* 1658:2221 */     Map<String, String> namespaces = new HashMap();
/* 1659:2222 */     for (int i = 0; i < attributes.getLength(); i++)
/* 1660:     */     {
/* 1661:2223 */       Attr attr = (Attr)attributes.item(i);
/* 1662:2224 */       String name = attr.getName();
/* 1663:2225 */       if (name.startsWith("xmlns"))
/* 1664:     */       {
/* 1665:2226 */         int startPos = 5;
/* 1666:2227 */         if ((name.length() > 5) && (name.charAt(5) == ':')) {
/* 1667:2228 */           startPos = 6;
/* 1668:     */         }
/* 1669:2230 */         name = name.substring(startPos);
/* 1670:2231 */         namespaces.put(name, attr.getValue());
/* 1671:     */       }
/* 1672:     */     }
/* 1673:2234 */     return namespaces;
/* 1674:     */   }
/* 1675:     */   
/* 1676:     */   protected void setDocumentType(DomDocumentType type)
/* 1677:     */   {
/* 1678:2242 */     super.setDocumentType(type);
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   public void save(File file)
/* 1682:     */     throws IOException
/* 1683:     */   {
/* 1684:2253 */     new XmlSerializer().save(this, file);
/* 1685:     */   }
/* 1686:     */   
/* 1687:     */   static boolean isMappedElement(Document document, String attributeName)
/* 1688:     */   {
/* 1689:2263 */     return ((document instanceof HtmlPage)) && (("name".equals(attributeName)) || ("id".equals(attributeName)));
/* 1690:     */   }
/* 1691:     */   
/* 1692:     */   public boolean isQuirksMode()
/* 1693:     */   {
/* 1694:2272 */     boolean quirks = true;
/* 1695:2273 */     DocumentType docType = getDoctype();
/* 1696:2274 */     if (docType != null)
/* 1697:     */     {
/* 1698:2275 */       String publicId = docType.getPublicId();
/* 1699:2276 */       String systemId = docType.getSystemId();
/* 1700:2277 */       if (systemId != null) {
/* 1701:2278 */         if ("http://www.w3.org/TR/html4/strict.dtd".equals(systemId)) {
/* 1702:2279 */           quirks = false;
/* 1703:2281 */         } else if ("http://www.w3.org/TR/html4/loose.dtd".equals(systemId))
/* 1704:     */         {
/* 1705:2282 */           if (("-//W3C//DTD HTML 4.01 Transitional//EN".equals(publicId)) || (("-//W3C//DTD HTML 4.0 Transitional//EN".equals(publicId)) && (getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.DOCTYPE_4_0_TRANSITIONAL_STANDARDS)))) {
/* 1706:2286 */             quirks = false;
/* 1707:     */           }
/* 1708:     */         }
/* 1709:2289 */         else if (("http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd".equals(systemId)) || ("http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd".equals(systemId))) {
/* 1710:2291 */           quirks = false;
/* 1711:     */         }
/* 1712:     */       }
/* 1713:     */     }
/* 1714:2295 */     return quirks;
/* 1715:     */   }
/* 1716:     */   
/* 1717:     */   public DomNodeList<DomNode> querySelectorAll(String selectors)
/* 1718:     */   {
/* 1719:2305 */     return super.querySelectorAll(selectors);
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   public DomNode querySelector(String selectors)
/* 1723:     */   {
/* 1724:2314 */     return super.querySelector(selectors);
/* 1725:     */   }
/* 1726:     */   
/* 1727:     */   protected boolean isDirectlyAttachedToPage()
/* 1728:     */   {
/* 1729:2319 */     return true;
/* 1730:     */   }
/* 1731:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlPage
 * JD-Core Version:    0.7.0.1
 */