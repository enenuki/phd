/*    1:     */ package com.gargoylesoftware.htmlunit.javascript.host;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.AlertHandler;
/*    4:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    5:     */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*    6:     */ import com.gargoylesoftware.htmlunit.ConfirmHandler;
/*    7:     */ import com.gargoylesoftware.htmlunit.DialogWindow;
/*    8:     */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*    9:     */ import com.gargoylesoftware.htmlunit.Page;
/*   10:     */ import com.gargoylesoftware.htmlunit.PromptHandler;
/*   11:     */ import com.gargoylesoftware.htmlunit.ScriptException;
/*   12:     */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   13:     */ import com.gargoylesoftware.htmlunit.StatusHandler;
/*   14:     */ import com.gargoylesoftware.htmlunit.TopLevelWindow;
/*   15:     */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   16:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*   17:     */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   18:     */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   19:     */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   20:     */ import com.gargoylesoftware.htmlunit.WebWindowNotFoundException;
/*   21:     */ import com.gargoylesoftware.htmlunit.html.BaseFrame;
/*   22:     */ import com.gargoylesoftware.htmlunit.html.DomChangeEvent;
/*   23:     */ import com.gargoylesoftware.htmlunit.html.DomChangeListener;
/*   24:     */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   25:     */ import com.gargoylesoftware.htmlunit.html.FrameWindow;
/*   26:     */ import com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeEvent;
/*   27:     */ import com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeListener;
/*   28:     */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   29:     */ import com.gargoylesoftware.htmlunit.html.HtmlLink;
/*   30:     */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   31:     */ import com.gargoylesoftware.htmlunit.html.HtmlStyle;
/*   32:     */ import com.gargoylesoftware.htmlunit.javascript.ScriptableWithFallbackGetter;
/*   33:     */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   34:     */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptFunctionJob;
/*   35:     */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
/*   36:     */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptStringJob;
/*   37:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleDeclaration;
/*   38:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet;
/*   39:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.ComputedCSSStyleDeclaration;
/*   40:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.StyleSheetList;
/*   41:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.DocumentProxy;
/*   42:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLBodyElement;
/*   43:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*   44:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument;
/*   45:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   46:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLUnknownElement;
/*   47:     */ import com.gargoylesoftware.htmlunit.javascript.host.xml.XMLDocument;
/*   48:     */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*   49:     */ import java.io.IOException;
/*   50:     */ import java.io.ObjectInputStream;
/*   51:     */ import java.io.Serializable;
/*   52:     */ import java.net.MalformedURLException;
/*   53:     */ import java.net.URL;
/*   54:     */ import java.util.HashMap;
/*   55:     */ import java.util.Iterator;
/*   56:     */ import java.util.List;
/*   57:     */ import java.util.Map;
/*   58:     */ import java.util.Map.Entry;
/*   59:     */ import java.util.Set;
/*   60:     */ import java.util.WeakHashMap;
/*   61:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   62:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   63:     */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*   64:     */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*   65:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   66:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   67:     */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*   68:     */ import org.apache.commons.codec.binary.Base64;
/*   69:     */ import org.apache.commons.lang.StringUtils;
/*   70:     */ import org.apache.commons.logging.Log;
/*   71:     */ import org.apache.commons.logging.LogFactory;
/*   72:     */ 
/*   73:     */ public class Window
/*   74:     */   extends SimpleScriptable
/*   75:     */   implements ScriptableWithFallbackGetter, Function
/*   76:     */ {
/*   77: 104 */   private static final Log LOG = LogFactory.getLog(Window.class);
/*   78:     */   public static final int WINDOW_WIDTH = 1256;
/*   79:     */   public static final int WINDOW_HEIGHT = 605;
/*   80:     */   private static final int MIN_TIMER_DELAY = 1;
/*   81:     */   private Document document_;
/*   82:     */   private DocumentProxy documentProxy_;
/*   83:     */   private Navigator navigator_;
/*   84:     */   private WebWindow webWindow_;
/*   85:     */   private WindowProxy windowProxy_;
/*   86:     */   private Screen screen_;
/*   87:     */   private History history_;
/*   88:     */   private Location location_;
/*   89:     */   private OfflineResourceList applicationCache_;
/*   90:     */   private Selection selection_;
/*   91:     */   private Event currentEvent_;
/*   92: 130 */   private String status_ = "";
/*   93:     */   private HTMLCollection frames_;
/*   94: 132 */   private Map<Class<? extends SimpleScriptable>, Scriptable> prototypes_ = new HashMap();
/*   95:     */   private EventListenersContainer eventListenersContainer_;
/*   96:     */   private Object controllers_;
/*   97:     */   private Object opener_;
/*   98: 137 */   private Object top_ = NOT_FOUND;
/*   99: 144 */   private transient WeakHashMap<Node, ComputedCSSStyleDeclaration> computedStyles_ = new WeakHashMap();
/*  100:     */   
/*  101:     */   private void readObject(ObjectInputStream stream)
/*  102:     */     throws IOException, ClassNotFoundException
/*  103:     */   {
/*  104: 154 */     stream.defaultReadObject();
/*  105: 155 */     this.computedStyles_ = new WeakHashMap();
/*  106:     */   }
/*  107:     */   
/*  108:     */   public Scriptable getPrototype(Class<? extends SimpleScriptable> jsClass)
/*  109:     */   {
/*  110: 171 */     return (Scriptable)this.prototypes_.get(jsClass);
/*  111:     */   }
/*  112:     */   
/*  113:     */   public void setPrototypes(Map<Class<? extends SimpleScriptable>, Scriptable> map)
/*  114:     */   {
/*  115: 179 */     this.prototypes_ = map;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public void jsxFunction_alert(Object message)
/*  119:     */   {
/*  120: 189 */     String stringMessage = Context.toString(message);
/*  121: 190 */     AlertHandler handler = getWebWindow().getWebClient().getAlertHandler();
/*  122: 191 */     if (handler == null) {
/*  123: 192 */       LOG.warn("window.alert(\"" + stringMessage + "\") no alert handler installed");
/*  124:     */     } else {
/*  125: 195 */       handler.handleAlert(((HTMLDocument)this.document_).getHtmlPage(), stringMessage);
/*  126:     */     }
/*  127:     */   }
/*  128:     */   
/*  129:     */   public String jsxFunction_btoa(String stringToEncode)
/*  130:     */   {
/*  131: 205 */     return new String(Base64.encodeBase64(stringToEncode.getBytes()));
/*  132:     */   }
/*  133:     */   
/*  134:     */   public String jsxFunction_atob(String encodedData)
/*  135:     */   {
/*  136: 214 */     return new String(Base64.decodeBase64(encodedData.getBytes()));
/*  137:     */   }
/*  138:     */   
/*  139:     */   public boolean jsxFunction_confirm(String message)
/*  140:     */   {
/*  141: 223 */     ConfirmHandler handler = getWebWindow().getWebClient().getConfirmHandler();
/*  142: 224 */     if (handler == null)
/*  143:     */     {
/*  144: 225 */       LOG.warn("window.confirm(\"" + message + "\") no confirm handler installed, simulating the OK button");
/*  145:     */       
/*  146: 227 */       return true;
/*  147:     */     }
/*  148: 229 */     return handler.handleConfirm(((HTMLDocument)this.document_).getHtmlPage(), message);
/*  149:     */   }
/*  150:     */   
/*  151:     */   public String jsxFunction_prompt(String message)
/*  152:     */   {
/*  153: 238 */     PromptHandler handler = getWebWindow().getWebClient().getPromptHandler();
/*  154: 239 */     if (handler == null)
/*  155:     */     {
/*  156: 240 */       LOG.warn("window.prompt(\"" + message + "\") no prompt handler installed");
/*  157: 241 */       return null;
/*  158:     */     }
/*  159: 243 */     return handler.handlePrompt(((HTMLDocument)this.document_).getHtmlPage(), message);
/*  160:     */   }
/*  161:     */   
/*  162:     */   public DocumentProxy jsxGet_document()
/*  163:     */   {
/*  164: 251 */     return this.documentProxy_;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public Document getDocument()
/*  168:     */   {
/*  169: 259 */     return this.document_;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public OfflineResourceList jsxGet_applicationCache()
/*  173:     */   {
/*  174: 267 */     return this.applicationCache_;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public Object jsxGet_event()
/*  178:     */   {
/*  179: 275 */     return this.currentEvent_;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public Event getCurrentEvent()
/*  183:     */   {
/*  184: 283 */     return this.currentEvent_;
/*  185:     */   }
/*  186:     */   
/*  187:     */   void setCurrentEvent(Event event)
/*  188:     */   {
/*  189: 291 */     this.currentEvent_ = event;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public WindowProxy jsxFunction_open(Object url, Object name, Object features, Object replace)
/*  193:     */   {
/*  194: 308 */     String urlString = null;
/*  195: 309 */     if (url != Undefined.instance) {
/*  196: 310 */       urlString = Context.toString(url);
/*  197:     */     }
/*  198: 312 */     String windowName = "";
/*  199: 313 */     if (name != Undefined.instance) {
/*  200: 314 */       windowName = Context.toString(name);
/*  201:     */     }
/*  202: 316 */     String featuresString = null;
/*  203: 317 */     if (features != Undefined.instance) {
/*  204: 318 */       featuresString = Context.toString(features);
/*  205:     */     }
/*  206: 320 */     boolean replaceCurrentEntryInBrowsingHistory = false;
/*  207: 321 */     if (replace != Undefined.instance) {
/*  208: 322 */       replaceCurrentEntryInBrowsingHistory = ((Boolean)replace).booleanValue();
/*  209:     */     }
/*  210: 324 */     WebClient webClient = this.webWindow_.getWebClient();
/*  211: 326 */     if (webClient.isPopupBlockerEnabled())
/*  212:     */     {
/*  213: 327 */       if (LOG.isDebugEnabled()) {
/*  214: 328 */         LOG.debug("Ignoring window.open() invocation because popups are blocked.");
/*  215:     */       }
/*  216: 330 */       return null;
/*  217:     */     }
/*  218: 333 */     if (((featuresString != null) || (replaceCurrentEntryInBrowsingHistory)) && 
/*  219: 334 */       (LOG.isDebugEnabled())) {
/*  220: 335 */       LOG.debug("window.open: features and replaceCurrentEntryInBrowsingHistory not implemented: url=[" + urlString + "] windowName=[" + windowName + "] features=[" + featuresString + "] replaceCurrentEntry=[" + replaceCurrentEntryInBrowsingHistory + "]");
/*  221:     */     }
/*  222: 346 */     if ((StringUtils.isEmpty(urlString)) && (!"".equals(windowName))) {
/*  223:     */       try
/*  224:     */       {
/*  225: 349 */         WebWindow webWindow = webClient.getWebWindowByName(windowName);
/*  226: 350 */         return getProxy(webWindow);
/*  227:     */       }
/*  228:     */       catch (WebWindowNotFoundException e) {}
/*  229:     */     }
/*  230: 356 */     URL newUrl = makeUrlForOpenWindow(urlString);
/*  231: 357 */     WebWindow newWebWindow = webClient.openWindow(newUrl, windowName, this.webWindow_);
/*  232: 358 */     return getProxy(newWebWindow);
/*  233:     */   }
/*  234:     */   
/*  235:     */   public Popup jsxFunction_createPopup()
/*  236:     */   {
/*  237: 367 */     Popup popup = new Popup();
/*  238: 368 */     popup.setParentScope(this);
/*  239: 369 */     popup.setPrototype(getPrototype(Popup.class));
/*  240: 370 */     popup.init(this);
/*  241: 371 */     return popup;
/*  242:     */   }
/*  243:     */   
/*  244:     */   private URL makeUrlForOpenWindow(String urlString)
/*  245:     */   {
/*  246: 375 */     if (urlString.length() == 0) {
/*  247: 376 */       return WebClient.URL_ABOUT_BLANK;
/*  248:     */     }
/*  249:     */     try
/*  250:     */     {
/*  251: 380 */       Page page = this.webWindow_.getEnclosedPage();
/*  252: 381 */       if ((page != null) && ((page instanceof HtmlPage))) {
/*  253: 382 */         return ((HtmlPage)page).getFullyQualifiedUrl(urlString);
/*  254:     */       }
/*  255: 384 */       return new URL(urlString);
/*  256:     */     }
/*  257:     */     catch (MalformedURLException e)
/*  258:     */     {
/*  259: 387 */       LOG.error("Unable to create URL for openWindow: relativeUrl=[" + urlString + "]", e);
/*  260:     */     }
/*  261: 388 */     return null;
/*  262:     */   }
/*  263:     */   
/*  264:     */   public int jsxFunction_setTimeout(Object code, int timeout, Object language)
/*  265:     */   {
/*  266: 404 */     if (timeout < 1) {
/*  267: 405 */       timeout = 1;
/*  268:     */     }
/*  269: 408 */     WebWindow w = getWebWindow();
/*  270: 409 */     Page page = (Page)getDomNodeOrNull();
/*  271: 410 */     if (code == null) {
/*  272: 411 */       throw Context.reportRuntimeError("Function not provided.");
/*  273:     */     }
/*  274:     */     int id;
/*  275: 413 */     if ((code instanceof String))
/*  276:     */     {
/*  277: 414 */       String s = (String)code;
/*  278: 415 */       String description = "window.setTimeout(" + s + ", " + timeout + ")";
/*  279: 416 */       JavaScriptStringJob job = new JavaScriptStringJob(timeout, null, description, w, s);
/*  280: 417 */       id = getWebWindow().getJobManager().addJob(job, page);
/*  281:     */     }
/*  282:     */     else
/*  283:     */     {
/*  284:     */       int id;
/*  285: 419 */       if ((code instanceof Function))
/*  286:     */       {
/*  287: 420 */         Function f = (Function)code;
/*  288:     */         String functionName;
/*  289:     */         String functionName;
/*  290: 422 */         if ((f instanceof FunctionObject)) {
/*  291: 423 */           functionName = ((FunctionObject)f).getFunctionName();
/*  292:     */         } else {
/*  293: 426 */           functionName = String.valueOf(f);
/*  294:     */         }
/*  295: 429 */         String description = "window.setTimeout(" + functionName + ", " + timeout + ")";
/*  296: 430 */         JavaScriptFunctionJob job = new JavaScriptFunctionJob(timeout, null, description, w, f);
/*  297: 431 */         id = getWebWindow().getJobManager().addJob(job, page);
/*  298:     */       }
/*  299:     */       else
/*  300:     */       {
/*  301: 434 */         throw Context.reportRuntimeError("Unknown type for function.");
/*  302:     */       }
/*  303:     */     }
/*  304:     */     int id;
/*  305: 436 */     return id;
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void jsxFunction_clearTimeout(int timeoutId)
/*  309:     */   {
/*  310: 445 */     if (LOG.isDebugEnabled()) {
/*  311: 446 */       LOG.debug("clearTimeout(" + timeoutId + ")");
/*  312:     */     }
/*  313: 448 */     getWebWindow().getJobManager().removeJob(timeoutId);
/*  314:     */   }
/*  315:     */   
/*  316:     */   public Navigator jsxGet_navigator()
/*  317:     */   {
/*  318: 456 */     return this.navigator_;
/*  319:     */   }
/*  320:     */   
/*  321:     */   public Navigator jsxGet_clientInformation()
/*  322:     */   {
/*  323: 464 */     return this.navigator_;
/*  324:     */   }
/*  325:     */   
/*  326:     */   public ClipboardData jsxGet_clipboardData()
/*  327:     */   {
/*  328: 472 */     ClipboardData clipboardData = new ClipboardData();
/*  329: 473 */     clipboardData.setParentScope(this);
/*  330: 474 */     clipboardData.setPrototype(getPrototype(clipboardData.getClass()));
/*  331: 475 */     return clipboardData;
/*  332:     */   }
/*  333:     */   
/*  334:     */   public WindowProxy jsxGet_window()
/*  335:     */   {
/*  336: 483 */     return this.windowProxy_;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public WindowProxy jsxGet_self()
/*  340:     */   {
/*  341: 491 */     return this.windowProxy_;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public Storage jsxGet_localStorage()
/*  345:     */   {
/*  346: 499 */     Storage storage = new Storage();
/*  347: 500 */     storage.setParentScope(this);
/*  348: 501 */     storage.setPrototype(getPrototype(storage.getClass()));
/*  349: 502 */     storage.setType(Storage.Type.LOCAL_STORAGE);
/*  350: 503 */     return storage;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public Storage jsxGet_sessionStorage()
/*  354:     */   {
/*  355: 511 */     Storage storage = new Storage();
/*  356: 512 */     storage.setParentScope(this);
/*  357: 513 */     storage.setPrototype(getPrototype(storage.getClass()));
/*  358: 514 */     storage.setType(Storage.Type.SESSION_STORAGE);
/*  359: 515 */     return storage;
/*  360:     */   }
/*  361:     */   
/*  362:     */   public StorageList jsxGet_globalStorage()
/*  363:     */   {
/*  364: 523 */     StorageList list = new StorageList();
/*  365: 524 */     list.setParentScope(this);
/*  366: 525 */     list.setPrototype(getPrototype(list.getClass()));
/*  367: 526 */     return list;
/*  368:     */   }
/*  369:     */   
/*  370:     */   public Location jsxGet_location()
/*  371:     */   {
/*  372: 534 */     return this.location_;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void jsxSet_location(String newLocation)
/*  376:     */     throws IOException
/*  377:     */   {
/*  378: 543 */     this.location_.jsxSet_href(newLocation);
/*  379:     */   }
/*  380:     */   
/*  381:     */   public Screen jsxGet_screen()
/*  382:     */   {
/*  383: 551 */     return this.screen_;
/*  384:     */   }
/*  385:     */   
/*  386:     */   public History jsxGet_history()
/*  387:     */   {
/*  388: 559 */     return this.history_;
/*  389:     */   }
/*  390:     */   
/*  391:     */   public External jsxGet_external()
/*  392:     */   {
/*  393: 567 */     External external = new External();
/*  394: 568 */     external.setParentScope(this);
/*  395: 569 */     external.setPrototype(getPrototype(external.getClass()));
/*  396: 570 */     return external;
/*  397:     */   }
/*  398:     */   
/*  399:     */   public void initialize(WebWindow webWindow)
/*  400:     */   {
/*  401: 578 */     this.webWindow_ = webWindow;
/*  402: 579 */     this.webWindow_.setScriptObject(this);
/*  403:     */     
/*  404: 581 */     this.windowProxy_ = new WindowProxy(this.webWindow_);
/*  405: 583 */     if ((webWindow.getEnclosedPage() instanceof XmlPage)) {
/*  406: 584 */       this.document_ = new XMLDocument();
/*  407:     */     } else {
/*  408: 587 */       this.document_ = new HTMLDocument();
/*  409:     */     }
/*  410: 589 */     this.document_.setParentScope(this);
/*  411: 590 */     this.document_.setPrototype(getPrototype(this.document_.getClass()));
/*  412: 591 */     this.document_.setWindow(this);
/*  413: 593 */     if ((webWindow.getEnclosedPage() instanceof SgmlPage))
/*  414:     */     {
/*  415: 594 */       SgmlPage page = (SgmlPage)webWindow.getEnclosedPage();
/*  416: 595 */       this.document_.setDomNode(page);
/*  417:     */       
/*  418: 597 */       DomHtmlAttributeChangeListenerImpl listener = new DomHtmlAttributeChangeListenerImpl(null);
/*  419: 598 */       page.addDomChangeListener(listener);
/*  420: 600 */       if ((page instanceof HtmlPage)) {
/*  421: 601 */         ((HtmlPage)page).addHtmlAttributeChangeListener(listener);
/*  422:     */       }
/*  423:     */     }
/*  424: 605 */     this.documentProxy_ = new DocumentProxy(this.webWindow_);
/*  425:     */     
/*  426: 607 */     this.navigator_ = new Navigator();
/*  427: 608 */     this.navigator_.setParentScope(this);
/*  428: 609 */     this.navigator_.setPrototype(getPrototype(this.navigator_.getClass()));
/*  429:     */     
/*  430: 611 */     this.screen_ = new Screen();
/*  431: 612 */     this.screen_.setParentScope(this);
/*  432: 613 */     this.screen_.setPrototype(getPrototype(this.screen_.getClass()));
/*  433:     */     
/*  434: 615 */     this.history_ = new History();
/*  435: 616 */     this.history_.setParentScope(this);
/*  436: 617 */     this.history_.setPrototype(getPrototype(this.history_.getClass()));
/*  437:     */     
/*  438: 619 */     this.location_ = new Location();
/*  439: 620 */     this.location_.setParentScope(this);
/*  440: 621 */     this.location_.setPrototype(getPrototype(this.location_.getClass()));
/*  441: 622 */     this.location_.initialize(this);
/*  442:     */     
/*  443: 624 */     this.applicationCache_ = new OfflineResourceList();
/*  444: 625 */     this.applicationCache_.setParentScope(this);
/*  445: 626 */     this.applicationCache_.setPrototype(getPrototype(this.applicationCache_.getClass()));
/*  446:     */     
/*  447:     */ 
/*  448: 629 */     Context ctx = Context.getCurrentContext();
/*  449: 630 */     this.controllers_ = ctx.newObject(this);
/*  450: 632 */     if ((this.webWindow_ instanceof TopLevelWindow))
/*  451:     */     {
/*  452: 633 */       WebWindow opener = ((TopLevelWindow)this.webWindow_).getOpener();
/*  453: 634 */       if (opener != null) {
/*  454: 635 */         this.opener_ = opener.getScriptObject();
/*  455:     */       }
/*  456:     */     }
/*  457:     */   }
/*  458:     */   
/*  459:     */   public void initialize(Page enclosedPage)
/*  460:     */   {
/*  461: 645 */     if ((enclosedPage instanceof HtmlPage))
/*  462:     */     {
/*  463: 646 */       HtmlPage htmlPage = (HtmlPage)enclosedPage;
/*  464:     */       
/*  465:     */ 
/*  466:     */ 
/*  467:     */ 
/*  468: 651 */       setDomNode(htmlPage);
/*  469: 652 */       this.eventListenersContainer_ = null;
/*  470:     */       
/*  471: 654 */       WebAssert.notNull("document_", this.document_);
/*  472: 655 */       this.document_.setDomNode(htmlPage);
/*  473:     */     }
/*  474:     */   }
/*  475:     */   
/*  476:     */   public void initialize() {}
/*  477:     */   
/*  478:     */   public Object jsxGet_top()
/*  479:     */   {
/*  480: 671 */     if (this.top_ != NOT_FOUND) {
/*  481: 672 */       return this.top_;
/*  482:     */     }
/*  483: 675 */     WebWindow top = this.webWindow_.getTopWindow();
/*  484: 676 */     return getProxy(top);
/*  485:     */   }
/*  486:     */   
/*  487:     */   public void jsxSet_top(Object o)
/*  488:     */   {
/*  489: 684 */     this.top_ = o;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public WindowProxy jsxGet_parent()
/*  493:     */   {
/*  494: 692 */     WebWindow parent = this.webWindow_.getParentWindow();
/*  495: 693 */     return getProxy(parent);
/*  496:     */   }
/*  497:     */   
/*  498:     */   public Object jsxGet_opener()
/*  499:     */   {
/*  500: 701 */     Object opener = this.opener_;
/*  501: 702 */     if ((opener instanceof Window)) {
/*  502: 703 */       opener = ((Window)opener).windowProxy_;
/*  503:     */     }
/*  504: 705 */     return opener;
/*  505:     */   }
/*  506:     */   
/*  507:     */   public void jsxSet_opener(Object newValue)
/*  508:     */   {
/*  509: 713 */     if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_177)) && (newValue != this.opener_)) {
/*  510: 714 */       if ((this.opener_ == null) || (newValue == null) || (newValue == Context.getUndefinedValue())) {
/*  511: 715 */         newValue = null;
/*  512:     */       } else {
/*  513: 718 */         throw Context.reportRuntimeError("Can't set opener!");
/*  514:     */       }
/*  515:     */     }
/*  516: 721 */     this.opener_ = newValue;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public Object jsxGet_frameElement()
/*  520:     */   {
/*  521: 729 */     WebWindow window = getWebWindow();
/*  522: 730 */     if ((window instanceof FrameWindow)) {
/*  523: 731 */       return ((FrameWindow)window).getFrameElement().getScriptObject();
/*  524:     */     }
/*  525: 733 */     return null;
/*  526:     */   }
/*  527:     */   
/*  528:     */   public WindowProxy jsxGet_frames()
/*  529:     */   {
/*  530: 741 */     return this.windowProxy_;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public int jsxGet_length()
/*  534:     */   {
/*  535: 749 */     return getFrames().jsxGet_length();
/*  536:     */   }
/*  537:     */   
/*  538:     */   private HTMLCollection getFrames()
/*  539:     */   {
/*  540: 757 */     if (this.frames_ == null)
/*  541:     */     {
/*  542: 758 */       HtmlPage page = (HtmlPage)getWebWindow().getEnclosedPage();
/*  543: 759 */       this.frames_ = new HTMLCollectionFrames(page);
/*  544:     */     }
/*  545: 761 */     return this.frames_;
/*  546:     */   }
/*  547:     */   
/*  548:     */   public WebWindow getWebWindow()
/*  549:     */   {
/*  550: 769 */     return this.webWindow_;
/*  551:     */   }
/*  552:     */   
/*  553:     */   public void jsxFunction_focus()
/*  554:     */   {
/*  555: 776 */     this.webWindow_.getWebClient().setCurrentWindow(this.webWindow_);
/*  556:     */   }
/*  557:     */   
/*  558:     */   public void jsxFunction_blur()
/*  559:     */   {
/*  560: 783 */     if (LOG.isDebugEnabled()) {
/*  561: 784 */       LOG.debug("window.blur() not implemented");
/*  562:     */     }
/*  563:     */   }
/*  564:     */   
/*  565:     */   public void jsxFunction_close()
/*  566:     */   {
/*  567: 792 */     WebWindow webWindow = getWebWindow();
/*  568: 793 */     if ((webWindow instanceof TopLevelWindow)) {
/*  569: 794 */       ((TopLevelWindow)webWindow).close();
/*  570:     */     } else {
/*  571: 797 */       webWindow.getWebClient().deregisterWebWindow(webWindow);
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   public boolean jsxGet_closed()
/*  576:     */   {
/*  577: 806 */     return !getWebWindow().getWebClient().getWebWindows().contains(getWebWindow());
/*  578:     */   }
/*  579:     */   
/*  580:     */   public void jsxFunction_moveTo(int x, int y)
/*  581:     */   {
/*  582: 815 */     if (LOG.isDebugEnabled()) {
/*  583: 816 */       LOG.debug("window.moveTo() not implemented");
/*  584:     */     }
/*  585:     */   }
/*  586:     */   
/*  587:     */   public void jsxFunction_moveBy(int x, int y)
/*  588:     */   {
/*  589: 826 */     if (LOG.isDebugEnabled()) {
/*  590: 827 */       LOG.debug("window.moveBy() not implemented");
/*  591:     */     }
/*  592:     */   }
/*  593:     */   
/*  594:     */   public void jsxFunction_resizeBy(int width, int height)
/*  595:     */   {
/*  596: 837 */     if (LOG.isDebugEnabled()) {
/*  597: 838 */       LOG.debug("window.resizeBy() not implemented");
/*  598:     */     }
/*  599:     */   }
/*  600:     */   
/*  601:     */   public void jsxFunction_resizeTo(int width, int height)
/*  602:     */   {
/*  603: 848 */     if (LOG.isDebugEnabled()) {
/*  604: 849 */       LOG.debug("window.resizeTo() not implemented");
/*  605:     */     }
/*  606:     */   }
/*  607:     */   
/*  608:     */   public void jsxFunction_scroll(int x, int y)
/*  609:     */   {
/*  610: 859 */     jsxFunction_scrollTo(x, y);
/*  611:     */   }
/*  612:     */   
/*  613:     */   public void jsxFunction_scrollBy(int x, int y)
/*  614:     */   {
/*  615: 868 */     HTMLElement body = ((HTMLDocument)this.document_).jsxGet_body();
/*  616: 869 */     if (body != null)
/*  617:     */     {
/*  618: 870 */       body.jsxSet_scrollLeft(body.jsxGet_scrollLeft() + x);
/*  619: 871 */       body.jsxSet_scrollTop(body.jsxGet_scrollTop() + y);
/*  620:     */     }
/*  621:     */   }
/*  622:     */   
/*  623:     */   public void jsxFunction_scrollByLines(int lines)
/*  624:     */   {
/*  625: 880 */     HTMLElement body = ((HTMLDocument)this.document_).jsxGet_body();
/*  626: 881 */     if (body != null) {
/*  627: 882 */       body.jsxSet_scrollTop(body.jsxGet_scrollTop() + 19 * lines);
/*  628:     */     }
/*  629:     */   }
/*  630:     */   
/*  631:     */   public void jsxFunction_scrollByPages(int pages)
/*  632:     */   {
/*  633: 891 */     HTMLElement body = ((HTMLDocument)this.document_).jsxGet_body();
/*  634: 892 */     if (body != null) {
/*  635: 893 */       body.jsxSet_scrollTop(body.jsxGet_scrollTop() + 605 * pages);
/*  636:     */     }
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void jsxFunction_scrollTo(int x, int y)
/*  640:     */   {
/*  641: 903 */     HTMLElement body = ((HTMLDocument)this.document_).jsxGet_body();
/*  642: 904 */     if (body != null)
/*  643:     */     {
/*  644: 905 */       body.jsxSet_scrollLeft(x);
/*  645: 906 */       body.jsxSet_scrollTop(y);
/*  646:     */     }
/*  647:     */   }
/*  648:     */   
/*  649:     */   public void jsxSet_onload(Object newOnload)
/*  650:     */   {
/*  651: 915 */     getEventListenersContainer().setEventHandlerProp("load", newOnload);
/*  652:     */   }
/*  653:     */   
/*  654:     */   public void jsxSet_onclick(Object newOnload)
/*  655:     */   {
/*  656: 923 */     getEventListenersContainer().setEventHandlerProp("click", newOnload);
/*  657:     */   }
/*  658:     */   
/*  659:     */   public Object jsxGet_onclick()
/*  660:     */   {
/*  661: 932 */     return getEventListenersContainer().getEventHandlerProp("click");
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void jsxSet_ondblclick(Object newHandler)
/*  665:     */   {
/*  666: 940 */     getEventListenersContainer().setEventHandlerProp("dblclick", newHandler);
/*  667:     */   }
/*  668:     */   
/*  669:     */   public Object jsxGet_ondblclick()
/*  670:     */   {
/*  671: 949 */     return getEventListenersContainer().getEventHandlerProp("dblclick");
/*  672:     */   }
/*  673:     */   
/*  674:     */   public Object jsxGet_onload()
/*  675:     */   {
/*  676: 957 */     Object onload = getEventListenersContainer().getEventHandlerProp("load");
/*  677: 958 */     if (onload == null)
/*  678:     */     {
/*  679: 960 */       HtmlPage page = (HtmlPage)this.webWindow_.getEnclosedPage();
/*  680: 961 */       HtmlElement body = page.getBody();
/*  681: 962 */       if (body != null)
/*  682:     */       {
/*  683: 963 */         HTMLBodyElement b = (HTMLBodyElement)body.getScriptObject();
/*  684: 964 */         return b.getEventHandler("onload");
/*  685:     */       }
/*  686: 966 */       return null;
/*  687:     */     }
/*  688: 968 */     return onload;
/*  689:     */   }
/*  690:     */   
/*  691:     */   public EventListenersContainer getEventListenersContainer()
/*  692:     */   {
/*  693: 976 */     if (this.eventListenersContainer_ == null) {
/*  694: 977 */       this.eventListenersContainer_ = new EventListenersContainer(this);
/*  695:     */     }
/*  696: 979 */     return this.eventListenersContainer_;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public boolean jsxFunction_attachEvent(String type, Function listener)
/*  700:     */   {
/*  701: 990 */     return getEventListenersContainer().addEventListener(StringUtils.substring(type, 2), listener, false);
/*  702:     */   }
/*  703:     */   
/*  704:     */   public void jsxFunction_addEventListener(String type, Function listener, boolean useCapture)
/*  705:     */   {
/*  706:1001 */     getEventListenersContainer().addEventListener(type, listener, useCapture);
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void jsxFunction_detachEvent(String type, Function listener)
/*  710:     */   {
/*  711:1011 */     getEventListenersContainer().removeEventListener(StringUtils.substring(type, 2), listener, false);
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void jsxFunction_removeEventListener(String type, Function listener, boolean useCapture)
/*  715:     */   {
/*  716:1022 */     getEventListenersContainer().removeEventListener(type, listener, useCapture);
/*  717:     */   }
/*  718:     */   
/*  719:     */   public String jsxGet_name()
/*  720:     */   {
/*  721:1030 */     return this.webWindow_.getName();
/*  722:     */   }
/*  723:     */   
/*  724:     */   public void jsxSet_name(String name)
/*  725:     */   {
/*  726:1038 */     this.webWindow_.setName(name);
/*  727:     */   }
/*  728:     */   
/*  729:     */   public Object jsxGet_onbeforeunload()
/*  730:     */   {
/*  731:1046 */     return getHandlerForJavaScript("beforeunload");
/*  732:     */   }
/*  733:     */   
/*  734:     */   public void jsxSet_onbeforeunload(Object onbeforeunload)
/*  735:     */   {
/*  736:1054 */     setHandlerForJavaScript("beforeunload", onbeforeunload);
/*  737:     */   }
/*  738:     */   
/*  739:     */   public Object jsxGet_onerror()
/*  740:     */   {
/*  741:1062 */     return getHandlerForJavaScript("error");
/*  742:     */   }
/*  743:     */   
/*  744:     */   public void jsxSet_onerror(Object onerror)
/*  745:     */   {
/*  746:1070 */     setHandlerForJavaScript("error", onerror);
/*  747:     */   }
/*  748:     */   
/*  749:     */   public void triggerOnError(ScriptException e)
/*  750:     */   {
/*  751:1078 */     Object o = jsxGet_onerror();
/*  752:1079 */     if ((o instanceof Function))
/*  753:     */     {
/*  754:1080 */       Function f = (Function)o;
/*  755:1081 */       String msg = e.getMessage();
/*  756:1082 */       String url = e.getPage().getWebResponse().getWebRequest().getUrl().toExternalForm();
/*  757:1083 */       int line = e.getFailingLineNumber();
/*  758:1084 */       Object[] args = { msg, url, Integer.valueOf(line) };
/*  759:1085 */       f.call(Context.getCurrentContext(), this, this, args);
/*  760:     */     }
/*  761:     */   }
/*  762:     */   
/*  763:     */   private Object getHandlerForJavaScript(String eventName)
/*  764:     */   {
/*  765:1090 */     Object handler = getEventListenersContainer().getEventHandlerProp(eventName);
/*  766:1091 */     if ((handler == null) && (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_129))) {
/*  767:1092 */       handler = Scriptable.NOT_FOUND;
/*  768:     */     }
/*  769:1094 */     return handler;
/*  770:     */   }
/*  771:     */   
/*  772:     */   private void setHandlerForJavaScript(String eventName, Object handler)
/*  773:     */   {
/*  774:1098 */     if ((handler instanceof Function)) {
/*  775:1099 */       getEventListenersContainer().setEventHandlerProp(eventName, handler);
/*  776:     */     }
/*  777:     */   }
/*  778:     */   
/*  779:     */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  780:     */   {
/*  781:1108 */     if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_130)) {
/*  782:1109 */       throw Context.reportRuntimeError("Window is not a function.");
/*  783:     */     }
/*  784:1111 */     if (args.length > 0)
/*  785:     */     {
/*  786:1112 */       Object arg = args[0];
/*  787:1113 */       if ((arg instanceof String)) {
/*  788:1114 */         return ScriptableObject.getProperty(this, (String)arg);
/*  789:     */       }
/*  790:1116 */       if ((arg instanceof Number)) {
/*  791:1117 */         return ScriptableObject.getProperty(this, ((Number)arg).intValue());
/*  792:     */       }
/*  793:     */     }
/*  794:1120 */     return Context.getUndefinedValue();
/*  795:     */   }
/*  796:     */   
/*  797:     */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  798:     */   {
/*  799:1127 */     if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_131)) {
/*  800:1128 */       throw Context.reportRuntimeError("Window is not a function.");
/*  801:     */     }
/*  802:1130 */     return null;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public Object getWithFallback(String name)
/*  806:     */   {
/*  807:1137 */     Object result = NOT_FOUND;
/*  808:     */     
/*  809:1139 */     DomNode domNode = getDomNodeOrNull();
/*  810:1140 */     if (domNode != null)
/*  811:     */     {
/*  812:1143 */       HtmlPage page = (HtmlPage)domNode.getPage();
/*  813:1144 */       result = getFrameWindowByName(page, name);
/*  814:1146 */       if (result == NOT_FOUND)
/*  815:     */       {
/*  816:1152 */         List<HtmlElement> elements = page.getElementsByName(name);
/*  817:1153 */         if (elements.size() == 1) {
/*  818:1154 */           result = getScriptableFor(elements.get(0));
/*  819:1156 */         } else if (elements.size() > 1) {
/*  820:1157 */           result = ((HTMLDocument)this.document_).jsxFunction_getElementsByName(name);
/*  821:     */         } else {
/*  822:     */           try
/*  823:     */           {
/*  824:1162 */             HtmlElement htmlElement = page.getHtmlElementById(name);
/*  825:1163 */             result = getScriptableFor(htmlElement);
/*  826:     */           }
/*  827:     */           catch (ElementNotFoundException e)
/*  828:     */           {
/*  829:1166 */             result = NOT_FOUND;
/*  830:     */           }
/*  831:     */         }
/*  832:     */       }
/*  833:1171 */       if ((result instanceof Window))
/*  834:     */       {
/*  835:1172 */         WebWindow webWindow = ((Window)result).getWebWindow();
/*  836:1173 */         result = getProxy(webWindow);
/*  837:     */       }
/*  838:1175 */       else if (((result instanceof HTMLUnknownElement)) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_132)))
/*  839:     */       {
/*  840:1177 */         HtmlElement unknownElement = ((HTMLUnknownElement)result).getDomNodeOrDie();
/*  841:1178 */         if ("xml".equals(unknownElement.getNodeName()))
/*  842:     */         {
/*  843:1179 */           XMLDocument document = ActiveXObject.buildXMLDocument(getWebWindow());
/*  844:1180 */           document.setParentScope(this);
/*  845:1181 */           Iterator<HtmlElement> children = unknownElement.getHtmlElementDescendants().iterator();
/*  846:1182 */           if (children.hasNext())
/*  847:     */           {
/*  848:1183 */             HtmlElement root = (HtmlElement)children.next();
/*  849:1184 */             document.jsxFunction_loadXML(root.asXml().trim());
/*  850:     */           }
/*  851:1186 */           result = document;
/*  852:     */         }
/*  853:     */       }
/*  854:     */     }
/*  855:1191 */     return result;
/*  856:     */   }
/*  857:     */   
/*  858:     */   public Object get(int index, Scriptable start)
/*  859:     */   {
/*  860:1199 */     HTMLCollection frames = getFrames();
/*  861:1200 */     if (index >= frames.jsxGet_length()) {
/*  862:1201 */       return Context.getUndefinedValue();
/*  863:     */     }
/*  864:1203 */     return frames.jsxFunction_item(Integer.valueOf(index));
/*  865:     */   }
/*  866:     */   
/*  867:     */   public Object get(String name, Scriptable start)
/*  868:     */   {
/*  869:1214 */     if ("eval".equals(name))
/*  870:     */     {
/*  871:1215 */       Window w = (Window)getTopScope(getStartingScope());
/*  872:1216 */       if (w != this) {
/*  873:1217 */         return getAssociatedValue("custom_eval");
/*  874:     */       }
/*  875:     */     }
/*  876:1220 */     else if ("Option".equals(name))
/*  877:     */     {
/*  878:1221 */       name = "HTMLOptionElement";
/*  879:     */     }
/*  880:1223 */     else if ("Image".equals(name))
/*  881:     */     {
/*  882:1224 */       name = "HTMLImageElement";
/*  883:     */     }
/*  884:1226 */     return super.get(name, start);
/*  885:     */   }
/*  886:     */   
/*  887:     */   private static Scriptable getTopScope(Scriptable s)
/*  888:     */   {
/*  889:1230 */     Scriptable top = s;
/*  890:1231 */     while ((top != null) && (top.getParentScope() != null)) {
/*  891:1232 */       top = top.getParentScope();
/*  892:     */     }
/*  893:1234 */     return top;
/*  894:     */   }
/*  895:     */   
/*  896:     */   private static Object getFrameWindowByName(HtmlPage page, String name)
/*  897:     */   {
/*  898:     */     try
/*  899:     */     {
/*  900:1239 */       return page.getFrameByName(name).getScriptObject();
/*  901:     */     }
/*  902:     */     catch (ElementNotFoundException e) {}
/*  903:1242 */     return NOT_FOUND;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public static WindowProxy getProxy(WebWindow w)
/*  907:     */   {
/*  908:1252 */     return ((Window)w.getScriptObject()).windowProxy_;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public void jsxFunction_execScript(String script, Object language)
/*  912:     */   {
/*  913:1264 */     String languageStr = Context.toString(language);
/*  914:1265 */     if ((language == Undefined.instance) || ("javascript".equalsIgnoreCase(languageStr)) || ("jscript".equalsIgnoreCase(languageStr))) {
/*  915:1267 */       custom_eval(script);
/*  916:1269 */     } else if ("vbscript".equalsIgnoreCase(languageStr)) {
/*  917:1270 */       LOG.warn("VBScript not supported in Window.execScript().");
/*  918:     */     } else {
/*  919:1274 */       throw Context.reportRuntimeError("Invalid class string");
/*  920:     */     }
/*  921:     */   }
/*  922:     */   
/*  923:     */   public Object custom_eval(String scriptCode)
/*  924:     */   {
/*  925:1285 */     Context context = Context.getCurrentContext();
/*  926:1286 */     Script script = context.compileString(scriptCode, "eval body", 0, null);
/*  927:1287 */     return script.exec(context, this);
/*  928:     */   }
/*  929:     */   
/*  930:     */   public String jsxGet_status()
/*  931:     */   {
/*  932:1295 */     return this.status_;
/*  933:     */   }
/*  934:     */   
/*  935:     */   public void jsxSet_status(String message)
/*  936:     */   {
/*  937:1303 */     this.status_ = message;
/*  938:     */     
/*  939:1305 */     StatusHandler statusHandler = this.webWindow_.getWebClient().getStatusHandler();
/*  940:1306 */     if (statusHandler != null) {
/*  941:1307 */       statusHandler.statusMessageChanged(this.webWindow_.getEnclosedPage(), message);
/*  942:     */     }
/*  943:     */   }
/*  944:     */   
/*  945:     */   public int jsxFunction_setInterval(Object code, int timeout, Object language)
/*  946:     */   {
/*  947:1322 */     if ((timeout == 0) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_133))) {
/*  948:1323 */       return jsxFunction_setTimeout(code, timeout, language);
/*  949:     */     }
/*  950:1325 */     if (timeout < 1) {
/*  951:1326 */       timeout = 1;
/*  952:     */     }
/*  953:1329 */     WebWindow w = getWebWindow();
/*  954:1330 */     Page page = (Page)getDomNodeOrNull();
/*  955:1331 */     String description = "window.setInterval(" + timeout + ")";
/*  956:1332 */     if (code == null) {
/*  957:1333 */       throw Context.reportRuntimeError("Function not provided.");
/*  958:     */     }
/*  959:     */     int id;
/*  960:1335 */     if ((code instanceof String))
/*  961:     */     {
/*  962:1336 */       String s = (String)code;
/*  963:1337 */       JavaScriptStringJob job = new JavaScriptStringJob(timeout, Integer.valueOf(timeout), description, w, s);
/*  964:     */       
/*  965:1339 */       id = getWebWindow().getJobManager().addJob(job, page);
/*  966:     */     }
/*  967:     */     else
/*  968:     */     {
/*  969:     */       int id;
/*  970:1341 */       if ((code instanceof Function))
/*  971:     */       {
/*  972:1342 */         Function f = (Function)code;
/*  973:1343 */         JavaScriptFunctionJob job = new JavaScriptFunctionJob(timeout, Integer.valueOf(timeout), description, w, f);
/*  974:     */         
/*  975:1345 */         id = getWebWindow().getJobManager().addJob(job, page);
/*  976:     */       }
/*  977:     */       else
/*  978:     */       {
/*  979:1348 */         throw Context.reportRuntimeError("Unknown type for function.");
/*  980:     */       }
/*  981:     */     }
/*  982:     */     int id;
/*  983:1350 */     return id;
/*  984:     */   }
/*  985:     */   
/*  986:     */   public void jsxFunction_clearInterval(int intervalID)
/*  987:     */   {
/*  988:1360 */     if (LOG.isDebugEnabled()) {
/*  989:1361 */       LOG.debug("clearInterval(" + intervalID + ")");
/*  990:     */     }
/*  991:1363 */     getWebWindow().getJobManager().removeJob(intervalID);
/*  992:     */   }
/*  993:     */   
/*  994:     */   public int jsxGet_innerWidth()
/*  995:     */   {
/*  996:1372 */     return 1256;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public int jsxGet_outerWidth()
/* 1000:     */   {
/* 1001:1381 */     return 1264;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public int jsxGet_innerHeight()
/* 1005:     */   {
/* 1006:1390 */     return 605;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public int jsxGet_outerHeight()
/* 1010:     */   {
/* 1011:1399 */     return 755;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void jsxFunction_print()
/* 1015:     */   {
/* 1016:1409 */     if (LOG.isDebugEnabled()) {
/* 1017:1410 */       LOG.debug("window.print() not implemented");
/* 1018:     */     }
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public void jsxFunction_captureEvents(String type) {}
/* 1022:     */   
/* 1023:     */   public void jsxFunction_CollectGarbage() {}
/* 1024:     */   
/* 1025:     */   public ComputedCSSStyleDeclaration jsxFunction_getComputedStyle(HTMLElement element, String pseudo)
/* 1026:     */   {
/* 1027:1442 */     synchronized (this.computedStyles_)
/* 1028:     */     {
/* 1029:1443 */       style = (ComputedCSSStyleDeclaration)this.computedStyles_.get(element);
/* 1030:     */     }
/* 1031:1445 */     if (style != null) {
/* 1032:1446 */       return style;
/* 1033:     */     }
/* 1034:1449 */     CSSStyleDeclaration original = element.jsxGet_style();
/* 1035:1450 */     ComputedCSSStyleDeclaration style = new ComputedCSSStyleDeclaration(original);
/* 1036:     */     
/* 1037:1452 */     StyleSheetList sheets = ((HTMLDocument)this.document_).jsxGet_styleSheets();
/* 1038:1453 */     for (int i = 0; i < sheets.jsxGet_length(); i++)
/* 1039:     */     {
/* 1040:1454 */       CSSStyleSheet sheet = (CSSStyleSheet)sheets.jsxFunction_item(i);
/* 1041:1455 */       if (sheet.isActive())
/* 1042:     */       {
/* 1043:1456 */         if (LOG.isTraceEnabled()) {
/* 1044:1457 */           LOG.trace("modifyIfNecessary: " + sheet + ", " + style + ", " + element);
/* 1045:     */         }
/* 1046:1459 */         sheet.modifyIfNecessary(style, element);
/* 1047:     */       }
/* 1048:     */     }
/* 1049:1463 */     synchronized (this.computedStyles_)
/* 1050:     */     {
/* 1051:1464 */       this.computedStyles_.put(element, style);
/* 1052:     */     }
/* 1053:1467 */     return style;
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public Selection jsxFunction_getSelection()
/* 1057:     */   {
/* 1058:1476 */     if ((this.webWindow_ instanceof FrameWindow))
/* 1059:     */     {
/* 1060:1477 */       FrameWindow frameWindow = (FrameWindow)this.webWindow_;
/* 1061:1478 */       if (!frameWindow.getFrameElement().isDisplayed()) {
/* 1062:1479 */         return null;
/* 1063:     */       }
/* 1064:     */     }
/* 1065:1482 */     return getSelection();
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public Selection getSelection()
/* 1069:     */   {
/* 1070:1490 */     if (this.selection_ == null)
/* 1071:     */     {
/* 1072:1491 */       this.selection_ = new Selection();
/* 1073:1492 */       this.selection_.setParentScope(this);
/* 1074:1493 */       this.selection_.setPrototype(getPrototype(this.selection_.getClass()));
/* 1075:     */     }
/* 1076:1495 */     return this.selection_;
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public Object jsxFunction_showModalDialog(String url, Object arguments, String features)
/* 1080:     */   {
/* 1081:1508 */     WebWindow ww = getWebWindow();
/* 1082:1509 */     WebClient client = ww.getWebClient();
/* 1083:     */     try
/* 1084:     */     {
/* 1085:1511 */       URL completeUrl = ((HtmlPage)getDomNodeOrDie()).getFullyQualifiedUrl(url);
/* 1086:1512 */       DialogWindow dialog = client.openDialogWindow(completeUrl, ww, arguments);
/* 1087:     */       
/* 1088:     */ 
/* 1089:     */ 
/* 1090:     */ 
/* 1091:1517 */       ScriptableObject jsDialog = (ScriptableObject)dialog.getScriptObject();
/* 1092:1518 */       return jsDialog.get("returnValue", jsDialog);
/* 1093:     */     }
/* 1094:     */     catch (IOException e)
/* 1095:     */     {
/* 1096:1521 */       throw Context.throwAsScriptRuntimeEx(e);
/* 1097:     */     }
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public Object jsxFunction_showModelessDialog(String url, Object arguments, String features)
/* 1101:     */   {
/* 1102:1534 */     WebWindow ww = getWebWindow();
/* 1103:1535 */     WebClient client = ww.getWebClient();
/* 1104:     */     try
/* 1105:     */     {
/* 1106:1537 */       URL completeUrl = ((HtmlPage)getDomNodeOrDie()).getFullyQualifiedUrl(url);
/* 1107:1538 */       DialogWindow dialog = client.openDialogWindow(completeUrl, ww, arguments);
/* 1108:1539 */       return (Window)dialog.getScriptObject();
/* 1109:     */     }
/* 1110:     */     catch (IOException e)
/* 1111:     */     {
/* 1112:1543 */       throw Context.throwAsScriptRuntimeEx(e);
/* 1113:     */     }
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public Object jsxGet_controllers()
/* 1117:     */   {
/* 1118:1554 */     return this.controllers_;
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public void jsxSet_controllers(Object value)
/* 1122:     */   {
/* 1123:1562 */     this.controllers_ = value;
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   private class DomHtmlAttributeChangeListenerImpl
/* 1127:     */     implements DomChangeListener, HtmlAttributeChangeListener, Serializable
/* 1128:     */   {
/* 1129:     */     private DomHtmlAttributeChangeListenerImpl() {}
/* 1130:     */     
/* 1131:     */     public void nodeAdded(DomChangeEvent event)
/* 1132:     */     {
/* 1133:1605 */       nodeChanged(event.getChangedNode());
/* 1134:     */     }
/* 1135:     */     
/* 1136:     */     public void nodeDeleted(DomChangeEvent event)
/* 1137:     */     {
/* 1138:1612 */       nodeChanged(event.getChangedNode());
/* 1139:     */     }
/* 1140:     */     
/* 1141:     */     public void attributeAdded(HtmlAttributeChangeEvent event)
/* 1142:     */     {
/* 1143:1619 */       nodeChanged(event.getHtmlElement());
/* 1144:     */     }
/* 1145:     */     
/* 1146:     */     public void attributeRemoved(HtmlAttributeChangeEvent event)
/* 1147:     */     {
/* 1148:1626 */       nodeChanged(event.getHtmlElement());
/* 1149:     */     }
/* 1150:     */     
/* 1151:     */     public void attributeReplaced(HtmlAttributeChangeEvent event)
/* 1152:     */     {
/* 1153:1633 */       nodeChanged(event.getHtmlElement());
/* 1154:     */     }
/* 1155:     */     
/* 1156:     */     private void nodeChanged(DomNode changed)
/* 1157:     */     {
/* 1158:1638 */       if ((changed instanceof HtmlStyle))
/* 1159:     */       {
/* 1160:1639 */         synchronized (Window.this.computedStyles_)
/* 1161:     */         {
/* 1162:1640 */           Window.this.computedStyles_.clear();
/* 1163:     */         }
/* 1164:1642 */         return;
/* 1165:     */       }
/* 1166:1644 */       if ((changed instanceof HtmlLink))
/* 1167:     */       {
/* 1168:1645 */         String rel = ((HtmlLink)changed).getRelAttribute().toLowerCase();
/* 1169:1646 */         if ("stylesheet".equals(rel))
/* 1170:     */         {
/* 1171:1647 */           synchronized (Window.this.computedStyles_)
/* 1172:     */           {
/* 1173:1648 */             Window.this.computedStyles_.clear();
/* 1174:     */           }
/* 1175:1650 */           return;
/* 1176:     */         }
/* 1177:     */       }
/* 1178:1654 */       synchronized (Window.this.computedStyles_)
/* 1179:     */       {
/* 1180:1655 */         Object i = Window.this.computedStyles_.entrySet().iterator();
/* 1181:1656 */         while (((Iterator)i).hasNext())
/* 1182:     */         {
/* 1183:1657 */           Object entry = (Map.Entry)((Iterator)i).next();
/* 1184:1658 */           DomNode node = ((Node)((Map.Entry)entry).getKey()).getDomNodeOrDie();
/* 1185:1659 */           if ((changed == node) || (changed.getParentNode() == node.getParentNode()) || (changed.isAncestorOf(node))) {
/* 1186:1662 */             ((Iterator)i).remove();
/* 1187:     */           }
/* 1188:     */         }
/* 1189:     */       }
/* 1190:     */     }
/* 1191:     */   }
/* 1192:     */   
/* 1193:     */   public String jsxFunction_ScriptEngine()
/* 1194:     */   {
/* 1195:1675 */     return "JScript";
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   public int jsxFunction_ScriptEngineBuildVersion()
/* 1199:     */   {
/* 1200:1684 */     return 12345;
/* 1201:     */   }
/* 1202:     */   
/* 1203:     */   public int jsxFunction_ScriptEngineMajorVersion()
/* 1204:     */   {
/* 1205:1693 */     return 5;
/* 1206:     */   }
/* 1207:     */   
/* 1208:     */   public int jsxFunction_ScriptEngineMinorVersion()
/* 1209:     */   {
/* 1210:1702 */     return (int)getBrowserVersion().getBrowserVersionNumeric();
/* 1211:     */   }
/* 1212:     */   
/* 1213:     */   public void jsxFunction_stop() {}
/* 1214:     */   
/* 1215:     */   public int jsxGet_pageXOffset()
/* 1216:     */   {
/* 1217:1719 */     return 0;
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   public int jsxGet_pageYOffset()
/* 1221:     */   {
/* 1222:1727 */     return 0;
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   public int jsxGet_scrollX()
/* 1226:     */   {
/* 1227:1735 */     return 0;
/* 1228:     */   }
/* 1229:     */   
/* 1230:     */   public int jsxGet_scrollY()
/* 1231:     */   {
/* 1232:1743 */     return 0;
/* 1233:     */   }
/* 1234:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Window
 * JD-Core Version:    0.7.0.1
 */