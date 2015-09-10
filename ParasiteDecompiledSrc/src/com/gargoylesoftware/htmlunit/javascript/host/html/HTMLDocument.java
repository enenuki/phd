/*    1:     */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    5:     */ import com.gargoylesoftware.htmlunit.CookieManager;
/*    6:     */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*    7:     */ import com.gargoylesoftware.htmlunit.Page;
/*    8:     */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*    9:     */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   10:     */ import com.gargoylesoftware.htmlunit.StringWebResponse;
/*   11:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*   12:     */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   13:     */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   14:     */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   15:     */ import com.gargoylesoftware.htmlunit.html.BaseFrame;
/*   16:     */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   17:     */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   18:     */ import com.gargoylesoftware.htmlunit.html.FrameWindow;
/*   19:     */ import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
/*   20:     */ import com.gargoylesoftware.htmlunit.html.HtmlApplet;
/*   21:     */ import com.gargoylesoftware.htmlunit.html.HtmlArea;
/*   22:     */ import com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeEvent;
/*   23:     */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   24:     */ import com.gargoylesoftware.htmlunit.html.HtmlForm;
/*   25:     */ import com.gargoylesoftware.htmlunit.html.HtmlImage;
/*   26:     */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   27:     */ import com.gargoylesoftware.htmlunit.html.HtmlScript;
/*   28:     */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*   29:     */ import com.gargoylesoftware.htmlunit.javascript.PostponedAction;
/*   30:     */ import com.gargoylesoftware.htmlunit.javascript.ScriptableWithFallbackGetter;
/*   31:     */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   32:     */ import com.gargoylesoftware.htmlunit.javascript.host.Document;
/*   33:     */ import com.gargoylesoftware.htmlunit.javascript.host.Event;
/*   34:     */ import com.gargoylesoftware.htmlunit.javascript.host.KeyboardEvent;
/*   35:     */ import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;
/*   36:     */ import com.gargoylesoftware.htmlunit.javascript.host.MutationEvent;
/*   37:     */ import com.gargoylesoftware.htmlunit.javascript.host.NamespaceCollection;
/*   38:     */ import com.gargoylesoftware.htmlunit.javascript.host.Node;
/*   39:     */ import com.gargoylesoftware.htmlunit.javascript.host.NodeFilter;
/*   40:     */ import com.gargoylesoftware.htmlunit.javascript.host.Range;
/*   41:     */ import com.gargoylesoftware.htmlunit.javascript.host.Selection;
/*   42:     */ import com.gargoylesoftware.htmlunit.javascript.host.StaticNodeList;
/*   43:     */ import com.gargoylesoftware.htmlunit.javascript.host.TreeWalker;
/*   44:     */ import com.gargoylesoftware.htmlunit.javascript.host.UIEvent;
/*   45:     */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   46:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet;
/*   47:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.StyleSheetList;
/*   48:     */ import com.gargoylesoftware.htmlunit.util.Cookie;
/*   49:     */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*   50:     */ import java.io.IOException;
/*   51:     */ import java.net.MalformedURLException;
/*   52:     */ import java.net.URL;
/*   53:     */ import java.text.SimpleDateFormat;
/*   54:     */ import java.util.ArrayList;
/*   55:     */ import java.util.Arrays;
/*   56:     */ import java.util.Collections;
/*   57:     */ import java.util.Date;
/*   58:     */ import java.util.HashMap;
/*   59:     */ import java.util.List;
/*   60:     */ import java.util.Map;
/*   61:     */ import java.util.Set;
/*   62:     */ import java.util.StringTokenizer;
/*   63:     */ import java.util.regex.Matcher;
/*   64:     */ import java.util.regex.Pattern;
/*   65:     */ import net.sourceforge.htmlunit.corejs.javascript.Callable;
/*   66:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   67:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   68:     */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*   69:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   70:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   71:     */ import net.sourceforge.htmlunit.corejs.javascript.UniqueTag;
/*   72:     */ import org.apache.commons.logging.Log;
/*   73:     */ import org.apache.commons.logging.LogFactory;
/*   74:     */ import org.w3c.dom.DOMException;
/*   75:     */ 
/*   76:     */ public class HTMLDocument
/*   77:     */   extends Document
/*   78:     */   implements ScriptableWithFallbackGetter
/*   79:     */ {
/*   80: 117 */   private static final Log LOG = LogFactory.getLog(HTMLDocument.class);
/*   81:     */   public static final String EMPTY_COOKIE_NAME = "HTMLUNIT_EMPTY_COOKIE";
/*   82:     */   private static final String LAST_MODIFIED_DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
/*   83: 125 */   private static final Pattern FIRST_TAG_PATTERN = Pattern.compile("<(\\w+)(\\s+[^>]*)?>");
/*   84: 126 */   private static final Pattern ATTRIBUTES_PATTERN = Pattern.compile("(\\w+)\\s*=\\s*['\"]([^'\"]*)['\"]");
/*   85:     */   private static final Map<String, Class<? extends Event>> SUPPORTED_EVENT_TYPE_MAP;
/*   86: 137 */   private static final List<String> EXECUTE_CMDS_IE = Arrays.asList(new String[] { "2D-Position", "AbsolutePosition", "BackColor", "BackgroundImageCache", "BlockDirLTR", "BlockDirRTL", "Bold", "BrowseMode", "ClearAuthenticationCache", "Copy", "CreateBookmark", "CreateLink", "Cut", "Delete", "DirLTR", "DirRTL", "EditMode", "FontName", "FontSize", "ForeColor", "FormatBlock", "Indent", "InlineDirLTR", "InlineDirRTL", "InsertButton", "InsertFieldset", "InsertHorizontalRule", "InsertIFrame", "InsertImage", "InsertInputButton", "InsertInputCheckbox", "InsertInputFileUpload", "InsertInputHidden", "InsertInputImage", "InsertInputPassword", "InsertInputRadio", "InsertInputReset", "InsertInputSubmit", "InsertInputText", "InsertMarquee", "InsertOrderedList", "InsertParagraph", "InsertSelectDropdown", "InsertSelectListbox", "InsertTextArea", "InsertUnorderedList", "Italic", "JustifyCenter", "JustifyFull", "JustifyLeft", "JustifyNone", "JustifyRight", "LiveResize", "MultipleSelection", "Open", "Outdent", "OverWrite", "Paste", "PlayImage", "Print", "Redo", "Refresh", "RemoveFormat", "RemoveParaFormat", "SaveAs", "SelectAll", "SizeToControl", "SizeToControlHeight", "SizeToControlWidth", "Stop", "StopImage", "StrikeThrough", "Subscript", "Superscript", "UnBookmark", "Underline", "Undo", "Unlink", "Unselect" });
/*   87: 157 */   private static final List<String> EXECUTE_CMDS_FF = Arrays.asList(new String[] { "backColor", "bold", "contentReadOnly", "copy", "createLink", "cut", "decreaseFontSize", "delete", "fontName", "fontSize", "foreColor", "formatBlock", "heading", "hiliteColor", "increaseFontSize", "indent", "insertHorizontalRule", "insertHTML", "insertImage", "insertOrderedList", "insertUnorderedList", "insertParagraph", "italic", "justifyCenter", "justifyLeft", "justifyRight", "outdent", "paste", "redo", "removeFormat", "selectAll", "strikeThrough", "subscript", "superscript", "underline", "undo", "unlink", "useCSS", "styleWithCSS" });
/*   88: 169 */   private static int UniqueID_Counter_ = 1;
/*   89:     */   private HTMLCollection all_;
/*   90:     */   private HTMLCollection forms_;
/*   91:     */   private HTMLCollection links_;
/*   92:     */   private HTMLCollection images_;
/*   93:     */   private HTMLCollection scripts_;
/*   94:     */   private HTMLCollection anchors_;
/*   95:     */   private HTMLCollection applets_;
/*   96:     */   private StyleSheetList styleSheets_;
/*   97:     */   private NamespaceCollection namespaces_;
/*   98:     */   private HTMLElement activeElement_;
/*   99: 183 */   private final StringBuilder writeBuffer_ = new StringBuilder();
/*  100: 184 */   private boolean writeInCurrentDocument_ = true;
/*  101:     */   private String domain_;
/*  102:     */   private String uniqueID_;
/*  103:     */   private String lastModified_;
/*  104:     */   private boolean closePostponedAction_;
/*  105:     */   
/*  106:     */   static
/*  107:     */   {
/*  108: 193 */     Map<String, Class<? extends Event>> eventMap = new HashMap();
/*  109: 194 */     eventMap.put("Event", Event.class);
/*  110: 195 */     eventMap.put("Events", Event.class);
/*  111: 196 */     eventMap.put("KeyboardEvent", KeyboardEvent.class);
/*  112: 197 */     eventMap.put("KeyEvents", KeyboardEvent.class);
/*  113: 198 */     eventMap.put("HTMLEvents", Event.class);
/*  114: 199 */     eventMap.put("MouseEvent", MouseEvent.class);
/*  115: 200 */     eventMap.put("MouseEvents", MouseEvent.class);
/*  116: 201 */     eventMap.put("MutationEvent", MutationEvent.class);
/*  117: 202 */     eventMap.put("MutationEvents", MutationEvent.class);
/*  118: 203 */     eventMap.put("UIEvent", UIEvent.class);
/*  119: 204 */     eventMap.put("UIEvents", UIEvent.class);
/*  120: 205 */     SUPPORTED_EVENT_TYPE_MAP = Collections.unmodifiableMap(eventMap);
/*  121:     */   }
/*  122:     */   
/*  123:     */   public <N extends DomNode> N getDomNodeOrDie()
/*  124:     */     throws IllegalStateException
/*  125:     */   {
/*  126:     */     try
/*  127:     */     {
/*  128: 222 */       return super.getDomNodeOrDie();
/*  129:     */     }
/*  130:     */     catch (IllegalStateException e)
/*  131:     */     {
/*  132: 225 */       DomNode node = getDomNodeOrNullFromRealDocument();
/*  133: 226 */       if (node != null) {
/*  134: 227 */         return node;
/*  135:     */       }
/*  136: 229 */       throw Context.reportRuntimeError("No node attached to this object");
/*  137:     */     }
/*  138:     */   }
/*  139:     */   
/*  140:     */   public DomNode getDomNodeOrNull()
/*  141:     */   {
/*  142: 239 */     DomNode node = super.getDomNodeOrNull();
/*  143: 240 */     if (node == null) {
/*  144: 241 */       node = getDomNodeOrNullFromRealDocument();
/*  145:     */     }
/*  146: 243 */     return node;
/*  147:     */   }
/*  148:     */   
/*  149:     */   private DomNode getDomNodeOrNullFromRealDocument()
/*  150:     */   {
/*  151: 257 */     DomNode node = null;
/*  152: 258 */     boolean ie = getWindow().getWebWindow().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_51);
/*  153: 260 */     if (ie)
/*  154:     */     {
/*  155: 261 */       Scriptable scope = getParentScope();
/*  156: 262 */       if ((scope instanceof Window))
/*  157:     */       {
/*  158: 263 */         Window w = (Window)scope;
/*  159: 264 */         Document realDocument = w.getDocument();
/*  160: 265 */         if (realDocument != this) {
/*  161: 266 */           node = realDocument.getDomNodeOrDie();
/*  162:     */         }
/*  163:     */       }
/*  164:     */     }
/*  165: 270 */     return node;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public HtmlPage getHtmlPage()
/*  169:     */   {
/*  170: 278 */     return (HtmlPage)getDomNodeOrDie();
/*  171:     */   }
/*  172:     */   
/*  173:     */   public HtmlPage getHtmlPageOrNull()
/*  174:     */   {
/*  175: 286 */     return (HtmlPage)getDomNodeOrNull();
/*  176:     */   }
/*  177:     */   
/*  178:     */   public Object jsxGet_forms()
/*  179:     */   {
/*  180: 294 */     if (this.forms_ == null)
/*  181:     */     {
/*  182: 295 */       HtmlPage page = getHtmlPage();
/*  183: 296 */       this.forms_ = new HTMLCollection(page, false, "HTMLDocument.forms")
/*  184:     */       {
/*  185:     */         protected boolean isMatching(DomNode node)
/*  186:     */         {
/*  187: 298 */           return node instanceof HtmlForm;
/*  188:     */         }
/*  189:     */       };
/*  190:     */     }
/*  191: 302 */     return this.forms_;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Object jsxGet_links()
/*  195:     */   {
/*  196: 311 */     if (this.links_ == null) {
/*  197: 312 */       this.links_ = new HTMLCollection(getDomNodeOrDie(), true, "HTMLDocument.links")
/*  198:     */       {
/*  199:     */         protected boolean isMatching(DomNode node)
/*  200:     */         {
/*  201: 315 */           return (((node instanceof HtmlAnchor)) || ((node instanceof HtmlArea))) && (((HtmlElement)node).hasAttribute("href"));
/*  202:     */         }
/*  203:     */         
/*  204:     */         protected HTMLCollection.EffectOnCache getEffectOnCache(HtmlAttributeChangeEvent event)
/*  205:     */         {
/*  206: 321 */           HtmlElement node = event.getHtmlElement();
/*  207: 322 */           if ((((node instanceof HtmlAnchor)) || ((node instanceof HtmlArea))) && ("href".equals(event.getName()))) {
/*  208: 323 */             return HTMLCollection.EffectOnCache.RESET;
/*  209:     */           }
/*  210: 325 */           return HTMLCollection.EffectOnCache.NONE;
/*  211:     */         }
/*  212:     */       };
/*  213:     */     }
/*  214: 329 */     return this.links_;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public String jsxGet_lastModified()
/*  218:     */   {
/*  219: 338 */     if (this.lastModified_ == null)
/*  220:     */     {
/*  221: 339 */       WebResponse webResponse = getPage().getWebResponse();
/*  222: 340 */       String stringDate = webResponse.getResponseHeaderValue("Last-Modified");
/*  223: 341 */       if (stringDate == null) {
/*  224: 342 */         stringDate = webResponse.getResponseHeaderValue("Date");
/*  225:     */       }
/*  226: 344 */       Date lastModified = parseDateOrNow(stringDate);
/*  227: 345 */       this.lastModified_ = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(lastModified);
/*  228:     */     }
/*  229: 347 */     return this.lastModified_;
/*  230:     */   }
/*  231:     */   
/*  232:     */   private static Date parseDateOrNow(String stringDate)
/*  233:     */   {
/*  234: 351 */     Date date = com.gargoylesoftware.htmlunit.util.StringUtils.parseHttpDate(stringDate);
/*  235: 352 */     if (date == null) {
/*  236: 353 */       return new Date();
/*  237:     */     }
/*  238: 355 */     return date;
/*  239:     */   }
/*  240:     */   
/*  241:     */   public Object jsxGet_namespaces()
/*  242:     */   {
/*  243: 363 */     if (this.namespaces_ == null) {
/*  244: 364 */       this.namespaces_ = new NamespaceCollection(this);
/*  245:     */     }
/*  246: 366 */     return this.namespaces_;
/*  247:     */   }
/*  248:     */   
/*  249:     */   public Object jsxGet_anchors()
/*  250:     */   {
/*  251: 377 */     if (this.anchors_ == null)
/*  252:     */     {
/*  253: 378 */       final boolean checkId = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_ANCHORS_REQUIRES_NAME_OR_ID);
/*  254:     */       
/*  255:     */ 
/*  256: 381 */       this.anchors_ = new HTMLCollection(getDomNodeOrDie(), true, "HTMLDocument.anchors")
/*  257:     */       {
/*  258:     */         protected boolean isMatching(DomNode node)
/*  259:     */         {
/*  260: 384 */           if (!(node instanceof HtmlAnchor)) {
/*  261: 385 */             return false;
/*  262:     */           }
/*  263: 387 */           HtmlAnchor anchor = (HtmlAnchor)node;
/*  264: 388 */           if (checkId) {
/*  265: 389 */             return (anchor.hasAttribute("name")) || (anchor.hasAttribute("id"));
/*  266:     */           }
/*  267: 391 */           return anchor.hasAttribute("name");
/*  268:     */         }
/*  269:     */         
/*  270:     */         protected HTMLCollection.EffectOnCache getEffectOnCache(HtmlAttributeChangeEvent event)
/*  271:     */         {
/*  272: 396 */           HtmlElement node = event.getHtmlElement();
/*  273: 397 */           if (!(node instanceof HtmlAnchor)) {
/*  274: 398 */             return HTMLCollection.EffectOnCache.NONE;
/*  275:     */           }
/*  276: 400 */           if (("name".equals(event.getName())) || ("id".equals(event.getName()))) {
/*  277: 401 */             return HTMLCollection.EffectOnCache.RESET;
/*  278:     */           }
/*  279: 403 */           return HTMLCollection.EffectOnCache.NONE;
/*  280:     */         }
/*  281:     */       };
/*  282:     */     }
/*  283: 407 */     return this.anchors_;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public Object jsxGet_applets()
/*  287:     */   {
/*  288: 419 */     if (this.applets_ == null) {
/*  289: 420 */       this.applets_ = new HTMLCollection(getDomNodeOrDie(), false, "HTMLDocument.applets")
/*  290:     */       {
/*  291:     */         protected boolean isMatching(DomNode node)
/*  292:     */         {
/*  293: 423 */           return node instanceof HtmlApplet;
/*  294:     */         }
/*  295:     */       };
/*  296:     */     }
/*  297: 427 */     return this.applets_;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public static void jsxFunction_write(Context context, Scriptable thisObj, Object[] args, Function function)
/*  301:     */   {
/*  302: 441 */     HTMLDocument thisAsDocument = getDocument(thisObj);
/*  303: 442 */     thisAsDocument.write(concatArgsAsString(args));
/*  304:     */   }
/*  305:     */   
/*  306:     */   private static String concatArgsAsString(Object[] args)
/*  307:     */   {
/*  308: 451 */     StringBuilder buffer = new StringBuilder();
/*  309: 452 */     for (Object arg : args) {
/*  310: 453 */       buffer.append(Context.toString(arg));
/*  311:     */     }
/*  312: 455 */     return buffer.toString();
/*  313:     */   }
/*  314:     */   
/*  315:     */   public static void jsxFunction_writeln(Context context, Scriptable thisObj, Object[] args, Function function)
/*  316:     */   {
/*  317: 469 */     HTMLDocument thisAsDocument = getDocument(thisObj);
/*  318: 470 */     thisAsDocument.write(concatArgsAsString(args) + "\n");
/*  319:     */   }
/*  320:     */   
/*  321:     */   private static HTMLDocument getDocument(Scriptable thisObj)
/*  322:     */   {
/*  323: 483 */     if (((thisObj instanceof HTMLDocument)) && ((thisObj.getPrototype() instanceof HTMLDocument))) {
/*  324: 484 */       return (HTMLDocument)thisObj;
/*  325:     */     }
/*  326: 486 */     if (((thisObj instanceof DocumentProxy)) && ((thisObj.getPrototype() instanceof HTMLDocument))) {
/*  327: 487 */       return (HTMLDocument)((DocumentProxy)thisObj).getDelegee();
/*  328:     */     }
/*  329: 489 */     Window window = getWindow(thisObj);
/*  330: 490 */     BrowserVersion browser = window.getWebWindow().getWebClient().getBrowserVersion();
/*  331: 491 */     if (browser.hasFeature(BrowserVersionFeatures.GENERATED_53)) {
/*  332: 492 */       return (HTMLDocument)window.getDocument();
/*  333:     */     }
/*  334: 494 */     throw Context.reportRuntimeError("Function can't be used detached from document");
/*  335:     */   }
/*  336:     */   
/*  337:     */   protected void write(String content)
/*  338:     */   {
/*  339: 506 */     if (LOG.isDebugEnabled()) {
/*  340: 507 */       LOG.debug("write: " + content);
/*  341:     */     }
/*  342: 510 */     HtmlPage page = (HtmlPage)getDomNodeOrDie();
/*  343: 511 */     if (!page.isBeingParsed()) {
/*  344: 512 */       this.writeInCurrentDocument_ = false;
/*  345:     */     }
/*  346: 516 */     this.writeBuffer_.append(content);
/*  347: 519 */     if (!this.writeInCurrentDocument_)
/*  348:     */     {
/*  349: 520 */       if (LOG.isDebugEnabled()) {
/*  350: 521 */         LOG.debug("wrote content to buffer");
/*  351:     */       }
/*  352: 523 */       scheduleImplicitClose();
/*  353: 524 */       return;
/*  354:     */     }
/*  355: 526 */     String bufferedContent = this.writeBuffer_.toString();
/*  356: 527 */     if (!canAlreadyBeParsed(bufferedContent))
/*  357:     */     {
/*  358: 528 */       if (LOG.isDebugEnabled()) {
/*  359: 529 */         LOG.debug("write: not enough content to parse it now");
/*  360:     */       }
/*  361: 531 */       return;
/*  362:     */     }
/*  363: 534 */     this.writeBuffer_.setLength(0);
/*  364: 535 */     page.writeInParsedStream(bufferedContent);
/*  365:     */   }
/*  366:     */   
/*  367:     */   private void scheduleImplicitClose()
/*  368:     */   {
/*  369: 539 */     if (!this.closePostponedAction_)
/*  370:     */     {
/*  371: 540 */       this.closePostponedAction_ = true;
/*  372: 541 */       HtmlPage page = (HtmlPage)getDomNodeOrDie();
/*  373: 542 */       page.getWebClient().getJavaScriptEngine().addPostponedAction(new PostponedAction(page)
/*  374:     */       {
/*  375:     */         public void execute()
/*  376:     */           throws Exception
/*  377:     */         {
/*  378: 545 */           if (HTMLDocument.this.writeBuffer_.length() > 0) {
/*  379: 546 */             HTMLDocument.this.jsxFunction_close();
/*  380:     */           }
/*  381: 548 */           HTMLDocument.this.closePostponedAction_ = false;
/*  382:     */         }
/*  383:     */       });
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   private static enum PARSING_STATUS
/*  388:     */   {
/*  389: 554 */     OUTSIDE,  START,  IN_NAME,  INSIDE,  IN_STRING;
/*  390:     */     
/*  391:     */     private PARSING_STATUS() {}
/*  392:     */   }
/*  393:     */   
/*  394:     */   static boolean canAlreadyBeParsed(String content)
/*  395:     */   {
/*  396: 565 */     PARSING_STATUS tagState = PARSING_STATUS.OUTSIDE;
/*  397: 566 */     int tagNameBeginIndex = 0;
/*  398: 567 */     int scriptTagCount = 0;
/*  399: 568 */     boolean tagIsOpen = true;
/*  400: 569 */     char stringBoundary = '\000';
/*  401: 570 */     boolean stringSkipNextChar = false;
/*  402: 571 */     int index = 0;
/*  403: 572 */     char openingQuote = '\000';
/*  404: 573 */     for (char currentChar : content.toCharArray())
/*  405:     */     {
/*  406: 574 */       switch (12.$SwitchMap$com$gargoylesoftware$htmlunit$javascript$host$html$HTMLDocument$PARSING_STATUS[tagState.ordinal()])
/*  407:     */       {
/*  408:     */       case 1: 
/*  409: 576 */         if (currentChar == '<')
/*  410:     */         {
/*  411: 577 */           tagState = PARSING_STATUS.START;
/*  412: 578 */           tagIsOpen = true;
/*  413:     */         }
/*  414: 580 */         else if ((scriptTagCount > 0) && ((currentChar == '\'') || (currentChar == '"')))
/*  415:     */         {
/*  416: 581 */           tagState = PARSING_STATUS.IN_STRING;
/*  417: 582 */           stringBoundary = currentChar;
/*  418: 583 */           stringSkipNextChar = false;
/*  419:     */         }
/*  420:     */         break;
/*  421:     */       case 2: 
/*  422: 587 */         if (currentChar == '/')
/*  423:     */         {
/*  424: 588 */           tagIsOpen = false;
/*  425: 589 */           tagNameBeginIndex = index + 1;
/*  426:     */         }
/*  427:     */         else
/*  428:     */         {
/*  429: 592 */           tagNameBeginIndex = index;
/*  430:     */         }
/*  431: 594 */         tagState = PARSING_STATUS.IN_NAME;
/*  432: 595 */         break;
/*  433:     */       case 3: 
/*  434: 597 */         if ((Character.isWhitespace(currentChar)) || (currentChar == '>'))
/*  435:     */         {
/*  436: 598 */           String tagName = content.substring(tagNameBeginIndex, index);
/*  437: 599 */           if ("script".equalsIgnoreCase(tagName)) {
/*  438: 600 */             if (tagIsOpen) {
/*  439: 601 */               scriptTagCount++;
/*  440: 603 */             } else if (scriptTagCount > 0) {
/*  441: 605 */               scriptTagCount--;
/*  442:     */             }
/*  443:     */           }
/*  444: 608 */           if (currentChar == '>') {
/*  445: 609 */             tagState = PARSING_STATUS.OUTSIDE;
/*  446:     */           } else {
/*  447: 612 */             tagState = PARSING_STATUS.INSIDE;
/*  448:     */           }
/*  449:     */         }
/*  450: 615 */         else if (!Character.isLetter(currentChar))
/*  451:     */         {
/*  452: 616 */           tagState = PARSING_STATUS.OUTSIDE;
/*  453:     */         }
/*  454:     */         break;
/*  455:     */       case 4: 
/*  456: 620 */         if (currentChar == openingQuote) {
/*  457: 621 */           openingQuote = '\000';
/*  458: 623 */         } else if (openingQuote == 0) {
/*  459: 624 */           if ((currentChar == '\'') || (currentChar == '"')) {
/*  460: 625 */             openingQuote = currentChar;
/*  461: 627 */           } else if ((currentChar == '>') && (openingQuote == 0)) {
/*  462: 628 */             tagState = PARSING_STATUS.OUTSIDE;
/*  463:     */           }
/*  464:     */         }
/*  465:     */         break;
/*  466:     */       case 5: 
/*  467: 633 */         if (stringSkipNextChar) {
/*  468: 634 */           stringSkipNextChar = false;
/*  469: 637 */         } else if (currentChar == stringBoundary) {
/*  470: 638 */           tagState = PARSING_STATUS.OUTSIDE;
/*  471: 640 */         } else if (currentChar == '\\') {
/*  472: 641 */           stringSkipNextChar = true;
/*  473:     */         }
/*  474:     */         break;
/*  475:     */       }
/*  476: 648 */       index++;
/*  477:     */     }
/*  478: 650 */     if ((scriptTagCount > 0) || (tagState != PARSING_STATUS.OUTSIDE)) {
/*  479: 651 */       return false;
/*  480:     */     }
/*  481: 654 */     return true;
/*  482:     */   }
/*  483:     */   
/*  484:     */   HtmlElement getLastHtmlElement(HtmlElement node)
/*  485:     */   {
/*  486: 663 */     DomNode lastChild = node.getLastChild();
/*  487: 664 */     if ((lastChild == null) || (!(lastChild instanceof HtmlElement)) || ((lastChild instanceof HtmlScript))) {
/*  488: 667 */       return node;
/*  489:     */     }
/*  490: 670 */     return getLastHtmlElement((HtmlElement)lastChild);
/*  491:     */   }
/*  492:     */   
/*  493:     */   public String jsxGet_cookie()
/*  494:     */   {
/*  495: 678 */     HtmlPage page = getHtmlPage();
/*  496:     */     
/*  497: 680 */     URL url = page.getWebResponse().getWebRequest().getUrl();
/*  498: 681 */     url = replaceForCookieIfNecessary(url);
/*  499:     */     
/*  500: 683 */     StringBuilder buffer = new StringBuilder();
/*  501: 684 */     Set<Cookie> cookies = page.getWebClient().getCookieManager().getCookies(url);
/*  502: 685 */     for (Cookie cookie : cookies)
/*  503:     */     {
/*  504: 686 */       if (buffer.length() != 0) {
/*  505: 687 */         buffer.append("; ");
/*  506:     */       }
/*  507: 689 */       if (!"HTMLUNIT_EMPTY_COOKIE".equals(cookie.getName()))
/*  508:     */       {
/*  509: 690 */         buffer.append(cookie.getName());
/*  510: 691 */         buffer.append("=");
/*  511:     */       }
/*  512: 693 */       buffer.append(cookie.getValue());
/*  513:     */     }
/*  514: 696 */     return buffer.toString();
/*  515:     */   }
/*  516:     */   
/*  517:     */   public String jsxGet_compatMode()
/*  518:     */   {
/*  519: 705 */     return getHtmlPage().isQuirksMode() ? "BackCompat" : "CSS1Compat";
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void jsxSet_cookie(String newCookie)
/*  523:     */   {
/*  524: 714 */     CookieManager cookieManager = getHtmlPage().getWebClient().getCookieManager();
/*  525: 715 */     if (cookieManager.isCookiesEnabled())
/*  526:     */     {
/*  527: 716 */       URL url = getHtmlPage().getWebResponse().getWebRequest().getUrl();
/*  528: 717 */       url = replaceForCookieIfNecessary(url);
/*  529: 718 */       Cookie cookie = buildCookie(newCookie, url);
/*  530: 719 */       cookieManager.addCookie(cookie);
/*  531: 720 */       if (LOG.isDebugEnabled()) {
/*  532: 721 */         LOG.debug("Added cookie: " + cookie);
/*  533:     */       }
/*  534:     */     }
/*  535: 724 */     else if (LOG.isDebugEnabled())
/*  536:     */     {
/*  537: 725 */       LOG.debug("Skipped adding cookie: " + newCookie);
/*  538:     */     }
/*  539:     */   }
/*  540:     */   
/*  541:     */   private static URL replaceForCookieIfNecessary(URL url)
/*  542:     */   {
/*  543: 738 */     String protocol = url.getProtocol();
/*  544: 739 */     boolean file = "file".equals(protocol);
/*  545: 740 */     if (file) {
/*  546:     */       try
/*  547:     */       {
/*  548: 742 */         url = UrlUtils.getUrlWithNewPort(UrlUtils.getUrlWithNewHost(url, "LOCAL_FILESYSTEM"), 0);
/*  549:     */       }
/*  550:     */       catch (MalformedURLException e)
/*  551:     */       {
/*  552: 745 */         throw new RuntimeException(e);
/*  553:     */       }
/*  554:     */     }
/*  555: 748 */     return url;
/*  556:     */   }
/*  557:     */   
/*  558:     */   public static Cookie buildCookie(String newCookie, URL currentURL)
/*  559:     */   {
/*  560: 760 */     StringTokenizer st = new StringTokenizer(newCookie, ";");
/*  561:     */     String value;
/*  562:     */     String name;
/*  563:     */     String value;
/*  564: 761 */     if (newCookie.contains("="))
/*  565:     */     {
/*  566: 762 */       String nameAndValue = st.nextToken();
/*  567: 763 */       String name = org.apache.commons.lang.StringUtils.substringBefore(nameAndValue, "=").trim();
/*  568: 764 */       value = org.apache.commons.lang.StringUtils.substringAfter(nameAndValue, "=").trim();
/*  569:     */     }
/*  570:     */     else
/*  571:     */     {
/*  572: 767 */       name = "HTMLUNIT_EMPTY_COOKIE";
/*  573: 768 */       value = newCookie;
/*  574:     */     }
/*  575: 772 */     Map<String, Object> atts = new HashMap();
/*  576: 773 */     atts.put("domain", currentURL.getHost());
/*  577: 774 */     atts.put("path", "");
/*  578: 777 */     while (st.hasMoreTokens())
/*  579:     */     {
/*  580: 778 */       String token = st.nextToken();
/*  581: 779 */       int indexEqual = token.indexOf('=');
/*  582: 780 */       if (indexEqual > -1) {
/*  583: 781 */         atts.put(token.substring(0, indexEqual).toLowerCase().trim(), token.substring(indexEqual + 1).trim());
/*  584:     */       } else {
/*  585: 784 */         atts.put(token.toLowerCase().trim(), Boolean.TRUE);
/*  586:     */       }
/*  587:     */     }
/*  588: 789 */     String date = (String)atts.get("expires");
/*  589: 790 */     Date expires = com.gargoylesoftware.htmlunit.util.StringUtils.parseHttpDate(date);
/*  590:     */     
/*  591:     */ 
/*  592: 793 */     String domain = (String)atts.get("domain");
/*  593: 794 */     String path = (String)atts.get("path");
/*  594: 795 */     boolean secure = atts.get("secure") != null;
/*  595: 796 */     Cookie cookie = new Cookie(domain, name, value, path, expires, secure);
/*  596:     */     
/*  597: 798 */     return cookie;
/*  598:     */   }
/*  599:     */   
/*  600:     */   public Object jsxGet_images()
/*  601:     */   {
/*  602: 806 */     if (this.images_ == null) {
/*  603: 807 */       this.images_ = new HTMLCollection(getDomNodeOrDie(), false, "HTMLDocument.images")
/*  604:     */       {
/*  605:     */         protected boolean isMatching(DomNode node)
/*  606:     */         {
/*  607: 810 */           return node instanceof HtmlImage;
/*  608:     */         }
/*  609:     */       };
/*  610:     */     }
/*  611: 814 */     return this.images_;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public String jsxGet_URL()
/*  615:     */   {
/*  616: 822 */     return getHtmlPage().getWebResponse().getWebRequest().getUrl().toExternalForm();
/*  617:     */   }
/*  618:     */   
/*  619:     */   public String jsxGet_uniqueID()
/*  620:     */   {
/*  621: 831 */     if (this.uniqueID_ == null) {
/*  622: 832 */       this.uniqueID_ = ("ms__id" + UniqueID_Counter_++);
/*  623:     */     }
/*  624: 834 */     return this.uniqueID_;
/*  625:     */   }
/*  626:     */   
/*  627:     */   public HTMLCollection jsxGet_all()
/*  628:     */   {
/*  629: 842 */     if (this.all_ == null)
/*  630:     */     {
/*  631: 843 */       this.all_ = new HTMLCollectionTags(getDomNodeOrDie(), "HTMLDocument.all")
/*  632:     */       {
/*  633:     */         protected boolean isMatching(DomNode node)
/*  634:     */         {
/*  635: 846 */           return true;
/*  636:     */         }
/*  637: 848 */       };
/*  638: 849 */       this.all_.setAvoidObjectDetection(!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_54));
/*  639:     */     }
/*  640: 851 */     return this.all_;
/*  641:     */   }
/*  642:     */   
/*  643:     */   public Object jsxFunction_open(String url, Object name, Object features, Object replace)
/*  644:     */   {
/*  645: 872 */     HtmlPage page = getHtmlPage();
/*  646: 873 */     if (page.isBeingParsed())
/*  647:     */     {
/*  648: 874 */       LOG.warn("Ignoring call to open() during the parsing stage.");
/*  649: 875 */       return null;
/*  650:     */     }
/*  651: 879 */     if (!this.writeInCurrentDocument_) {
/*  652: 880 */       LOG.warn("Function open() called when document is already open.");
/*  653:     */     }
/*  654: 882 */     this.writeInCurrentDocument_ = false;
/*  655: 883 */     return null;
/*  656:     */   }
/*  657:     */   
/*  658:     */   public void jsxFunction_close()
/*  659:     */     throws IOException
/*  660:     */   {
/*  661: 895 */     if (this.writeInCurrentDocument_)
/*  662:     */     {
/*  663: 896 */       LOG.warn("close() called when document is not open.");
/*  664:     */     }
/*  665:     */     else
/*  666:     */     {
/*  667: 899 */       HtmlPage page = getHtmlPage();
/*  668: 900 */       URL url = page.getWebResponse().getWebRequest().getUrl();
/*  669: 901 */       StringWebResponse webResponse = new StringWebResponse(this.writeBuffer_.toString(), url);
/*  670: 902 */       webResponse.setFromJavascript(true);
/*  671: 903 */       WebClient webClient = page.getWebClient();
/*  672: 904 */       WebWindow window = page.getEnclosingWindow();
/*  673:     */       
/*  674: 906 */       webClient.loadWebResponseInto(webResponse, window);
/*  675:     */       
/*  676: 908 */       this.writeInCurrentDocument_ = true;
/*  677: 909 */       this.writeBuffer_.setLength(0);
/*  678:     */     }
/*  679:     */   }
/*  680:     */   
/*  681:     */   private void implicitCloseIfNecessary()
/*  682:     */   {
/*  683: 917 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_55);
/*  684: 918 */     if ((!this.writeInCurrentDocument_) && (ie)) {
/*  685:     */       try
/*  686:     */       {
/*  687: 920 */         jsxFunction_close();
/*  688:     */       }
/*  689:     */       catch (IOException e)
/*  690:     */       {
/*  691: 923 */         throw Context.throwAsScriptRuntimeEx(e);
/*  692:     */       }
/*  693:     */     }
/*  694:     */   }
/*  695:     */   
/*  696:     */   public Object jsxGet_parentWindow()
/*  697:     */   {
/*  698: 933 */     return getWindow();
/*  699:     */   }
/*  700:     */   
/*  701:     */   public Object jsxFunction_appendChild(Object childObject)
/*  702:     */   {
/*  703: 941 */     if ((limitAppendChildToIE()) && (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_56))) {
/*  704: 943 */       throw Context.reportRuntimeError("Node cannot be inserted at the specified point in the hierarchy.");
/*  705:     */     }
/*  706: 946 */     return super.jsxFunction_appendChild(childObject);
/*  707:     */   }
/*  708:     */   
/*  709:     */   protected boolean limitAppendChildToIE()
/*  710:     */   {
/*  711: 960 */     return true;
/*  712:     */   }
/*  713:     */   
/*  714:     */   public Object jsxFunction_createElement(String tagName)
/*  715:     */   {
/*  716: 971 */     Object result = NOT_FOUND;
/*  717: 974 */     if ((tagName.startsWith("<")) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_57)))
/*  718:     */     {
/*  719: 975 */       Matcher m = FIRST_TAG_PATTERN.matcher(tagName);
/*  720: 976 */       if (m.find())
/*  721:     */       {
/*  722: 977 */         tagName = m.group(1);
/*  723: 978 */         result = super.jsxFunction_createElement(tagName);
/*  724: 979 */         if ((result == NOT_FOUND) || (m.group(2) == null)) {
/*  725: 980 */           return result;
/*  726:     */         }
/*  727: 982 */         HTMLElement elt = (HTMLElement)result;
/*  728:     */         
/*  729:     */ 
/*  730: 985 */         String attributes = m.group(2);
/*  731: 986 */         Matcher mAttribute = ATTRIBUTES_PATTERN.matcher(attributes);
/*  732: 987 */         while (mAttribute.find())
/*  733:     */         {
/*  734: 988 */           String attrName = mAttribute.group(1);
/*  735: 989 */           String attrValue = mAttribute.group(2);
/*  736: 990 */           elt.jsxFunction_setAttribute(attrName, attrValue);
/*  737:     */         }
/*  738:     */       }
/*  739:     */     }
/*  740:     */     else
/*  741:     */     {
/*  742: 995 */       return super.jsxFunction_createElement(tagName);
/*  743:     */     }
/*  744: 998 */     return result;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public CSSStyleSheet jsxFunction_createStyleSheet(String url, int index)
/*  748:     */   {
/*  749:1010 */     CSSStyleSheet stylesheet = new CSSStyleSheet();
/*  750:1011 */     stylesheet.setPrototype(getPrototype(CSSStyleSheet.class));
/*  751:1012 */     stylesheet.setParentScope(getWindow());
/*  752:1013 */     return stylesheet;
/*  753:     */   }
/*  754:     */   
/*  755:     */   public Object jsxFunction_getElementById(String id)
/*  756:     */   {
/*  757:1022 */     implicitCloseIfNecessary();
/*  758:1023 */     Object result = null;
/*  759:     */     try
/*  760:     */     {
/*  761:1025 */       boolean caseSensitive = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_GET_ELEMENT_BY_ID_CASE_SENSITIVE);
/*  762:     */       
/*  763:1027 */       HtmlElement htmlElement = ((HtmlPage)getDomNodeOrDie()).getHtmlElementById(id, caseSensitive);
/*  764:1028 */       Object jsElement = getScriptableFor(htmlElement);
/*  765:1029 */       if (jsElement == NOT_FOUND)
/*  766:     */       {
/*  767:1030 */         if (LOG.isDebugEnabled()) {
/*  768:1031 */           LOG.debug("getElementById(" + id + ") cannot return a result as there isn't a JavaScript object for the HTML element " + htmlElement.getClass().getName());
/*  769:     */         }
/*  770:     */       }
/*  771:     */       else {
/*  772:1037 */         result = jsElement;
/*  773:     */       }
/*  774:     */     }
/*  775:     */     catch (ElementNotFoundException e)
/*  776:     */     {
/*  777:1042 */       BrowserVersion browser = getBrowserVersion();
/*  778:1043 */       if (browser.hasFeature(BrowserVersionFeatures.JS_GET_ELEMENT_BY_ID_ALSO_BY_NAME))
/*  779:     */       {
/*  780:1044 */         HTMLCollection elements = jsxFunction_getElementsByName(id);
/*  781:1045 */         result = elements.get(0, elements);
/*  782:1046 */         if ((result instanceof UniqueTag)) {
/*  783:1047 */           return null;
/*  784:     */         }
/*  785:1049 */         LOG.warn("getElementById(" + id + ") did a getElementByName for Internet Explorer");
/*  786:1050 */         return result;
/*  787:     */       }
/*  788:1052 */       if (LOG.isDebugEnabled()) {
/*  789:1053 */         LOG.debug("getElementById(" + id + "): no DOM node found with this id");
/*  790:     */       }
/*  791:     */     }
/*  792:1056 */     return result;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public HTMLCollection jsxFunction_getElementsByClassName(String className)
/*  796:     */   {
/*  797:1066 */     return ((HTMLElement)jsxGet_documentElement()).jsxFunction_getElementsByClassName(className);
/*  798:     */   }
/*  799:     */   
/*  800:     */   public HTMLCollection jsxFunction_getElementsByName(String elementName)
/*  801:     */   {
/*  802:1079 */     implicitCloseIfNecessary();
/*  803:1080 */     if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_59)) && ((org.apache.commons.lang.StringUtils.isEmpty(elementName)) || ("null".equals(elementName)))) {
/*  804:1082 */       return HTMLCollection.emptyCollection(getWindow());
/*  805:     */     }
/*  806:1085 */     final String expElementName = "null".equals(elementName) ? "" : elementName;
/*  807:     */     
/*  808:1087 */     final HtmlPage page = (HtmlPage)getPage();
/*  809:1088 */     String description = "HTMLDocument.getElementsByName('" + elementName + "')";
/*  810:1089 */     HTMLCollection collection = new HTMLCollection(page, true, description)
/*  811:     */     {
/*  812:     */       protected List<Object> computeElements()
/*  813:     */       {
/*  814:1092 */         return new ArrayList(page.getElementsByName(expElementName));
/*  815:     */       }
/*  816:     */       
/*  817:     */       protected HTMLCollection.EffectOnCache getEffectOnCache(HtmlAttributeChangeEvent event)
/*  818:     */       {
/*  819:1096 */         if ("name".equals(event.getName())) {
/*  820:1097 */           return HTMLCollection.EffectOnCache.RESET;
/*  821:     */         }
/*  822:1099 */         return HTMLCollection.EffectOnCache.NONE;
/*  823:     */       }
/*  824:1102 */     };
/*  825:1103 */     return collection;
/*  826:     */   }
/*  827:     */   
/*  828:     */   protected Object getWithPreemption(String name)
/*  829:     */   {
/*  830:1114 */     HtmlPage page = (HtmlPage)getDomNodeOrNull();
/*  831:1115 */     if ((page == null) || (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_160))) {
/*  832:1116 */       return NOT_FOUND;
/*  833:     */     }
/*  834:1118 */     return getIt(name);
/*  835:     */   }
/*  836:     */   
/*  837:     */   private Object getIt(final String name)
/*  838:     */   {
/*  839:1122 */     final HtmlPage page = (HtmlPage)getDomNodeOrNull();
/*  840:     */     
/*  841:1124 */     final boolean isIE = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_60);
/*  842:1125 */     HTMLCollection collection = new HTMLCollection(page, true, "HTMLDocument." + name)
/*  843:     */     {
/*  844:     */       protected List<Object> computeElements()
/*  845:     */       {
/*  846:     */         List<HtmlElement> elements;
/*  847:     */         List<HtmlElement> elements;
/*  848:1129 */         if (isIE) {
/*  849:1130 */           elements = page.getElementsByIdAndOrName(name);
/*  850:     */         } else {
/*  851:1133 */           elements = page.getElementsByName(name);
/*  852:     */         }
/*  853:1135 */         List<Object> matchingElements = new ArrayList();
/*  854:1136 */         for (HtmlElement elt : elements) {
/*  855:1137 */           if (((elt instanceof HtmlForm)) || ((elt instanceof HtmlImage)) || ((elt instanceof HtmlApplet)) || ((isIE) && ((elt instanceof BaseFrame)))) {
/*  856:1139 */             matchingElements.add(elt);
/*  857:     */           }
/*  858:     */         }
/*  859:1142 */         return matchingElements;
/*  860:     */       }
/*  861:     */       
/*  862:     */       protected HTMLCollection.EffectOnCache getEffectOnCache(HtmlAttributeChangeEvent event)
/*  863:     */       {
/*  864:1147 */         String attributeName = event.getName();
/*  865:1148 */         if ("name".equals(attributeName)) {
/*  866:1149 */           return HTMLCollection.EffectOnCache.RESET;
/*  867:     */         }
/*  868:1151 */         if ((isIE) && ("id".equals(attributeName))) {
/*  869:1152 */           return HTMLCollection.EffectOnCache.RESET;
/*  870:     */         }
/*  871:1155 */         return HTMLCollection.EffectOnCache.NONE;
/*  872:     */       }
/*  873:     */       
/*  874:     */       protected SimpleScriptable getScriptableFor(Object object)
/*  875:     */       {
/*  876:1160 */         if ((isIE) && ((object instanceof BaseFrame))) {
/*  877:1161 */           return (SimpleScriptable)((BaseFrame)object).getEnclosedWindow().getScriptObject();
/*  878:     */         }
/*  879:1163 */         return super.getScriptableFor(object);
/*  880:     */       }
/*  881:1166 */     };
/*  882:1167 */     int length = collection.jsxGet_length();
/*  883:1168 */     if (length == 0) {
/*  884:1169 */       return NOT_FOUND;
/*  885:     */     }
/*  886:1171 */     if (length == 1) {
/*  887:1172 */       return collection.jsxFunction_item(Integer.valueOf(0));
/*  888:     */     }
/*  889:1175 */     return collection;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public Object getWithFallback(String name)
/*  893:     */   {
/*  894:1183 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_161)) {
/*  895:1184 */       return getIt(name);
/*  896:     */     }
/*  897:1186 */     return NOT_FOUND;
/*  898:     */   }
/*  899:     */   
/*  900:     */   public HTMLElement jsxGet_body()
/*  901:     */   {
/*  902:1194 */     HtmlPage page = getHtmlPage();
/*  903:1196 */     if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_61)) && ((page.getEnclosingWindow() instanceof FrameWindow)))
/*  904:     */     {
/*  905:1198 */       HtmlPage enclosingPage = (HtmlPage)page.getEnclosingWindow().getParentWindow().getEnclosedPage();
/*  906:1199 */       if ((WebClient.URL_ABOUT_BLANK.equals(page.getWebResponse().getWebRequest().getUrl())) && (enclosingPage.getReadyState() != "complete")) {
/*  907:1201 */         return null;
/*  908:     */       }
/*  909:     */     }
/*  910:1204 */     HtmlElement body = getHtmlPage().getBody();
/*  911:1205 */     if (body != null) {
/*  912:1206 */       return (HTMLElement)body.getScriptObject();
/*  913:     */     }
/*  914:1208 */     return null;
/*  915:     */   }
/*  916:     */   
/*  917:     */   public String jsxGet_title()
/*  918:     */   {
/*  919:1216 */     return getHtmlPage().getTitleText();
/*  920:     */   }
/*  921:     */   
/*  922:     */   public void jsxSet_title(String title)
/*  923:     */   {
/*  924:1224 */     getHtmlPage().setTitleText(title);
/*  925:     */   }
/*  926:     */   
/*  927:     */   public String jsxGet_bgColor()
/*  928:     */   {
/*  929:1233 */     String bgColor = getHtmlPage().getBody().getAttribute("bgColor");
/*  930:1234 */     if (bgColor == DomElement.ATTRIBUTE_NOT_DEFINED) {
/*  931:1235 */       bgColor = "#ffffff";
/*  932:     */     }
/*  933:1237 */     return bgColor;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void jsxSet_bgColor(String bgColor)
/*  937:     */   {
/*  938:1246 */     HTMLBodyElement body = (HTMLBodyElement)getHtmlPage().getBody().getScriptObject();
/*  939:1247 */     body.jsxSet_bgColor(bgColor);
/*  940:     */   }
/*  941:     */   
/*  942:     */   public String jsxGet_readyState()
/*  943:     */   {
/*  944:1260 */     DomNode node = getDomNodeOrDie();
/*  945:1261 */     return node.getReadyState();
/*  946:     */   }
/*  947:     */   
/*  948:     */   public String jsxGet_domain()
/*  949:     */   {
/*  950:1272 */     if (this.domain_ == null)
/*  951:     */     {
/*  952:1273 */       URL url = getHtmlPage().getWebResponse().getWebRequest().getUrl();
/*  953:1274 */       if (url == WebClient.URL_ABOUT_BLANK)
/*  954:     */       {
/*  955:1275 */         WebWindow w = getWindow().getWebWindow();
/*  956:1276 */         if ((w instanceof FrameWindow)) {
/*  957:1277 */           url = ((FrameWindow)w).getEnclosingPage().getWebResponse().getWebRequest().getUrl();
/*  958:     */         } else {
/*  959:1280 */           return null;
/*  960:     */         }
/*  961:     */       }
/*  962:1283 */       this.domain_ = url.getHost();
/*  963:1284 */       BrowserVersion browser = getBrowserVersion();
/*  964:1285 */       if (browser.hasFeature(BrowserVersionFeatures.GENERATED_162)) {
/*  965:1286 */         this.domain_ = this.domain_.toLowerCase();
/*  966:     */       }
/*  967:     */     }
/*  968:1290 */     return this.domain_;
/*  969:     */   }
/*  970:     */   
/*  971:     */   public void jsxSet_domain(String newDomain)
/*  972:     */   {
/*  973:1322 */     BrowserVersion browserVersion = getBrowserVersion();
/*  974:1325 */     if ((WebClient.URL_ABOUT_BLANK == getPage().getWebResponse().getWebRequest().getUrl()) && (browserVersion.hasFeature(BrowserVersionFeatures.GENERATED_62))) {
/*  975:1327 */       throw Context.reportRuntimeError("Illegal domain value, cannot set domain from about:blank to: \"" + newDomain + "\"");
/*  976:     */     }
/*  977:1331 */     String currentDomain = jsxGet_domain();
/*  978:1332 */     if (currentDomain.equalsIgnoreCase(newDomain)) {
/*  979:1333 */       return;
/*  980:     */     }
/*  981:1336 */     if ((newDomain.indexOf('.') == -1) || (!currentDomain.toLowerCase().endsWith("." + newDomain.toLowerCase()))) {
/*  982:1338 */       throw Context.reportRuntimeError("Illegal domain value, cannot set domain from: \"" + currentDomain + "\" to: \"" + newDomain + "\"");
/*  983:     */     }
/*  984:1343 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_163)) {
/*  985:1344 */       this.domain_ = newDomain.toLowerCase();
/*  986:     */     } else {
/*  987:1347 */       this.domain_ = newDomain;
/*  988:     */     }
/*  989:     */   }
/*  990:     */   
/*  991:     */   public Object jsxGet_scripts()
/*  992:     */   {
/*  993:1356 */     if (this.scripts_ == null) {
/*  994:1357 */       this.scripts_ = new HTMLCollection(getDomNodeOrDie(), false, "HTMLDocument.scripts")
/*  995:     */       {
/*  996:     */         protected boolean isMatching(DomNode node)
/*  997:     */         {
/*  998:1360 */           return node instanceof HtmlScript;
/*  999:     */         }
/* 1000:     */       };
/* 1001:     */     }
/* 1002:1364 */     return this.scripts_;
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public Selection jsxGet_selection()
/* 1006:     */   {
/* 1007:1372 */     return getWindow().getSelection();
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public Object jsxGet_frames()
/* 1011:     */   {
/* 1012:1381 */     return getWindow().jsxGet_frames();
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public StyleSheetList jsxGet_styleSheets()
/* 1016:     */   {
/* 1017:1392 */     if (this.styleSheets_ == null) {
/* 1018:1393 */       this.styleSheets_ = new StyleSheetList(this);
/* 1019:     */     }
/* 1020:1395 */     return this.styleSheets_;
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public Event jsxFunction_createEvent(String eventType)
/* 1024:     */     throws DOMException
/* 1025:     */   {
/* 1026:1410 */     Class<? extends Event> clazz = (Class)SUPPORTED_EVENT_TYPE_MAP.get(eventType);
/* 1027:1411 */     if (clazz == null)
/* 1028:     */     {
/* 1029:1412 */       Context.throwAsScriptRuntimeEx(new DOMException((short)9, "Event Type is not supported: " + eventType));
/* 1030:     */       
/* 1031:1414 */       return null;
/* 1032:     */     }
/* 1033:     */     try
/* 1034:     */     {
/* 1035:1417 */       Event event = (Event)clazz.newInstance();
/* 1036:1418 */       event.setEventType(eventType);
/* 1037:1419 */       event.setParentScope(getWindow());
/* 1038:1420 */       event.setPrototype(getPrototype(clazz));
/* 1039:1421 */       return event;
/* 1040:     */     }
/* 1041:     */     catch (InstantiationException e)
/* 1042:     */     {
/* 1043:1424 */       throw Context.reportRuntimeError("Failed to instantiate event: class ='" + clazz.getName() + "' for event type of '" + eventType + "': " + e.getMessage());
/* 1044:     */     }
/* 1045:     */     catch (IllegalAccessException e)
/* 1046:     */     {
/* 1047:1428 */       throw Context.reportRuntimeError("Failed to instantiate event: class ='" + clazz.getName() + "' for event type of '" + eventType + "': " + e.getMessage());
/* 1048:     */     }
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public Event jsxFunction_createEventObject()
/* 1052:     */   {
/* 1053:1442 */     Event event = new MouseEvent();
/* 1054:1443 */     event.setParentScope(getWindow());
/* 1055:1444 */     event.setPrototype(getPrototype(event.getClass()));
/* 1056:1445 */     return event;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public Object jsxFunction_elementFromPoint(int x, int y)
/* 1060:     */   {
/* 1061:1458 */     if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_164)) && ((x <= 0) || (y <= 0))) {
/* 1062:1459 */       return null;
/* 1063:     */     }
/* 1064:1461 */     return jsxGet_body();
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public Range jsxFunction_createRange()
/* 1068:     */   {
/* 1069:1470 */     Range r = new Range(this);
/* 1070:1471 */     r.setParentScope(getWindow());
/* 1071:1472 */     r.setPrototype(getPrototype(Range.class));
/* 1072:1473 */     return r;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public Object jsxFunction_createTreeWalker(Node root, int whatToShow, final Scriptable filter, boolean expandEntityReferences)
/* 1076:     */     throws DOMException
/* 1077:     */   {
/* 1078:1500 */     NodeFilter filterWrapper = null;
/* 1079:1501 */     if (filter != null) {
/* 1080:1502 */       filterWrapper = new NodeFilter()
/* 1081:     */       {
/* 1082:     */         public short acceptNode(Node n)
/* 1083:     */         {
/* 1084:1505 */           Object[] args = { n };
/* 1085:     */           Object response;
/* 1086:     */           Object response;
/* 1087:1507 */           if ((filter instanceof Callable)) {
/* 1088:1508 */             response = ((Callable)filter).call(Context.getCurrentContext(), filter, filter, args);
/* 1089:     */           } else {
/* 1090:1511 */             response = ScriptableObject.callMethod(filter, "acceptNode", args);
/* 1091:     */           }
/* 1092:1513 */           return (short)(int)Context.toNumber(response);
/* 1093:     */         }
/* 1094:     */       };
/* 1095:     */     }
/* 1096:1518 */     TreeWalker t = new TreeWalker(root, whatToShow, filterWrapper, Boolean.valueOf(expandEntityReferences));
/* 1097:1519 */     t.setParentScope(getWindow(this));
/* 1098:1520 */     t.setPrototype(staticGetPrototype(getWindow(this), TreeWalker.class));
/* 1099:1521 */     return t;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   private static Scriptable staticGetPrototype(Window window, Class<? extends SimpleScriptable> javaScriptClass)
/* 1103:     */   {
/* 1104:1527 */     Scriptable prototype = window.getPrototype(javaScriptClass);
/* 1105:1528 */     if ((prototype == null) && (javaScriptClass != SimpleScriptable.class)) {
/* 1106:1529 */       return staticGetPrototype(window, javaScriptClass.getSuperclass());
/* 1107:     */     }
/* 1108:1531 */     return prototype;
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public boolean jsxFunction_queryCommandSupported(String cmd)
/* 1112:     */   {
/* 1113:1541 */     boolean ff = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_165);
/* 1114:1542 */     String mode = jsxGet_designMode();
/* 1115:1543 */     if (!ff) {
/* 1116:1544 */       return com.gargoylesoftware.htmlunit.util.StringUtils.containsCaseInsensitive(EXECUTE_CMDS_IE, cmd);
/* 1117:     */     }
/* 1118:1546 */     if (!"on".equals(mode))
/* 1119:     */     {
/* 1120:1547 */       String msg = "queryCommandSupported() called while document.designMode='" + mode + "'.";
/* 1121:1548 */       throw Context.reportRuntimeError(msg);
/* 1122:     */     }
/* 1123:1550 */     return com.gargoylesoftware.htmlunit.util.StringUtils.containsCaseInsensitive(EXECUTE_CMDS_FF, cmd);
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public boolean jsxFunction_queryCommandEnabled(String cmd)
/* 1127:     */   {
/* 1128:1560 */     boolean ff = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_166);
/* 1129:1561 */     String mode = jsxGet_designMode();
/* 1130:1562 */     if (!ff) {
/* 1131:1563 */       return com.gargoylesoftware.htmlunit.util.StringUtils.containsCaseInsensitive(EXECUTE_CMDS_IE, cmd);
/* 1132:     */     }
/* 1133:1565 */     if (!"on".equals(mode))
/* 1134:     */     {
/* 1135:1566 */       String msg = "queryCommandEnabled() called while document.designMode='" + mode + "'.";
/* 1136:1567 */       throw Context.reportRuntimeError(msg);
/* 1137:     */     }
/* 1138:1569 */     return com.gargoylesoftware.htmlunit.util.StringUtils.containsCaseInsensitive(EXECUTE_CMDS_FF, cmd);
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public boolean jsxFunction_execCommand(String cmd, boolean userInterface, Object value)
/* 1142:     */   {
/* 1143:1581 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_63);
/* 1144:1582 */     if (((ie) && (!com.gargoylesoftware.htmlunit.util.StringUtils.containsCaseInsensitive(EXECUTE_CMDS_IE, cmd))) || ((!ie) && (!com.gargoylesoftware.htmlunit.util.StringUtils.containsCaseInsensitive(EXECUTE_CMDS_FF, cmd))))
/* 1145:     */     {
/* 1146:1585 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.EXECCOMMAND_THROWS_ON_WRONG_COMMAND)) {
/* 1147:1586 */         throw Context.reportRuntimeError("document.execCommand(): invalid command '" + cmd + "'");
/* 1148:     */       }
/* 1149:1588 */       return false;
/* 1150:     */     }
/* 1151:1590 */     LOG.warn("Nothing done for execCommand(" + cmd + ", ...) (feature not implemented)");
/* 1152:1591 */     return true;
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   public Object jsxGet_activeElement()
/* 1156:     */   {
/* 1157:1600 */     if (this.activeElement_ == null)
/* 1158:     */     {
/* 1159:1601 */       HtmlElement body = getHtmlPage().getBody();
/* 1160:1602 */       if (body != null) {
/* 1161:1603 */         this.activeElement_ = ((HTMLElement)getScriptableFor(body));
/* 1162:     */       }
/* 1163:     */     }
/* 1164:1606 */     return this.activeElement_;
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public void setActiveElement(HTMLElement element)
/* 1168:     */   {
/* 1169:1615 */     this.activeElement_ = element;
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public SimpleScriptable jsxGet_doctype()
/* 1173:     */   {
/* 1174:1623 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_64)) {
/* 1175:1624 */       return null;
/* 1176:     */     }
/* 1177:1626 */     return super.jsxGet_doctype();
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   public boolean jsxFunction_dispatchEvent(Event event)
/* 1181:     */   {
/* 1182:1639 */     event.setTarget(this);
/* 1183:1640 */     ScriptResult result = fireEvent(event);
/* 1184:1641 */     return !event.isAborted(result);
/* 1185:     */   }
/* 1186:     */   
/* 1187:     */   public StaticNodeList jsxFunction_querySelectorAll(String selectors)
/* 1188:     */   {
/* 1189:1652 */     List<Node> nodes = new ArrayList();
/* 1190:1653 */     for (DomNode domNode : getHtmlPage().querySelectorAll(selectors)) {
/* 1191:1654 */       nodes.add((Node)domNode.getScriptObject());
/* 1192:     */     }
/* 1193:1656 */     return new StaticNodeList(nodes, this);
/* 1194:     */   }
/* 1195:     */   
/* 1196:     */   public Node jsxFunction_querySelector(String selectors)
/* 1197:     */   {
/* 1198:1665 */     DomNode node = getHtmlPage().querySelector(selectors);
/* 1199:1666 */     if (node != null) {
/* 1200:1667 */       return (Node)node.getScriptObject();
/* 1201:     */     }
/* 1202:1669 */     return null;
/* 1203:     */   }
/* 1204:     */   
/* 1205:     */   public Object get(String name, Scriptable start)
/* 1206:     */   {
/* 1207:1677 */     Object response = super.get(name, start);
/* 1208:1681 */     if (((response instanceof FunctionObject)) && (("querySelectorAll".equals(name)) || ("querySelector".equals(name))) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.QUERYSELECTORALL_NOT_IN_QUIRKS)) && (getHtmlPage().isQuirksMode())) {
/* 1209:1685 */       return NOT_FOUND;
/* 1210:     */     }
/* 1211:1688 */     return response;
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public void jsxFunction_clear() {}
/* 1215:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument
 * JD-Core Version:    0.7.0.1
 */