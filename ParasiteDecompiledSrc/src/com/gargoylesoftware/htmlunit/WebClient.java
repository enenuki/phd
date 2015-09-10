/*    1:     */ package com.gargoylesoftware.htmlunit;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.attachment.Attachment;
/*    4:     */ import com.gargoylesoftware.htmlunit.attachment.AttachmentHandler;
/*    5:     */ import com.gargoylesoftware.htmlunit.gae.GAEUtils;
/*    6:     */ import com.gargoylesoftware.htmlunit.html.BaseFrame;
/*    7:     */ import com.gargoylesoftware.htmlunit.html.FrameWindow;
/*    8:     */ import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
/*    9:     */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   10:     */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   11:     */ import com.gargoylesoftware.htmlunit.javascript.HtmlUnitContextFactory;
/*   12:     */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*   13:     */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
/*   14:     */ import com.gargoylesoftware.htmlunit.javascript.ProxyAutoConfig;
/*   15:     */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
/*   16:     */ import com.gargoylesoftware.htmlunit.javascript.host.Document;
/*   17:     */ import com.gargoylesoftware.htmlunit.javascript.host.Event;
/*   18:     */ import com.gargoylesoftware.htmlunit.javascript.host.Location;
/*   19:     */ import com.gargoylesoftware.htmlunit.javascript.host.Node;
/*   20:     */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   21:     */ import com.gargoylesoftware.htmlunit.javascript.host.css.ComputedCSSStyleDeclaration;
/*   22:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument;
/*   23:     */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   24:     */ import com.gargoylesoftware.htmlunit.protocol.data.DataUrlDecoder;
/*   25:     */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   26:     */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*   27:     */ import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
/*   28:     */ import java.io.BufferedInputStream;
/*   29:     */ import java.io.File;
/*   30:     */ import java.io.FileInputStream;
/*   31:     */ import java.io.IOException;
/*   32:     */ import java.io.InputStream;
/*   33:     */ import java.io.ObjectInputStream;
/*   34:     */ import java.io.Serializable;
/*   35:     */ import java.lang.ref.WeakReference;
/*   36:     */ import java.net.MalformedURLException;
/*   37:     */ import java.net.URL;
/*   38:     */ import java.net.URLConnection;
/*   39:     */ import java.security.GeneralSecurityException;
/*   40:     */ import java.util.ArrayList;
/*   41:     */ import java.util.Collections;
/*   42:     */ import java.util.ConcurrentModificationException;
/*   43:     */ import java.util.HashMap;
/*   44:     */ import java.util.HashSet;
/*   45:     */ import java.util.Iterator;
/*   46:     */ import java.util.List;
/*   47:     */ import java.util.Map;
/*   48:     */ import java.util.Map.Entry;
/*   49:     */ import java.util.Set;
/*   50:     */ import java.util.Stack;
/*   51:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   52:     */ import org.apache.commons.codec.DecoderException;
/*   53:     */ import org.apache.commons.io.FileUtils;
/*   54:     */ import org.apache.commons.io.IOUtils;
/*   55:     */ import org.apache.commons.lang.StringUtils;
/*   56:     */ import org.apache.commons.logging.Log;
/*   57:     */ import org.apache.commons.logging.LogFactory;
/*   58:     */ import org.apache.http.client.CredentialsProvider;
/*   59:     */ import org.w3c.css.sac.ErrorHandler;
/*   60:     */ 
/*   61:     */ public class WebClient
/*   62:     */   implements Serializable
/*   63:     */ {
/*   64: 119 */   private static final Log LOG = LogFactory.getLog(WebClient.class);
/*   65:     */   private static final int ALLOWED_REDIRECTIONS_SAME_URL = 20;
/*   66: 124 */   private transient WebConnection webConnection_ = createWebConnection();
/*   67: 125 */   private boolean printContentOnFailingStatusCode_ = true;
/*   68: 126 */   private boolean throwExceptionOnFailingStatusCode_ = true;
/*   69: 127 */   private CredentialsProvider credentialsProvider_ = new DefaultCredentialsProvider();
/*   70:     */   private ProxyConfig proxyConfig_;
/*   71: 129 */   private CookieManager cookieManager_ = new CookieManager();
/*   72:     */   private transient JavaScriptEngine scriptEngine_;
/*   73: 131 */   private boolean javaScriptEnabled_ = true;
/*   74: 132 */   private boolean cssEnabled_ = true;
/*   75:     */   private boolean appletEnabled_;
/*   76:     */   private boolean popupBlockerEnabled_;
/*   77:     */   private String homePage_;
/*   78: 136 */   private final Map<String, String> requestHeaders_ = Collections.synchronizedMap(new HashMap(89));
/*   79: 137 */   private IncorrectnessListener incorrectnessListener_ = new IncorrectnessListenerImpl();
/*   80:     */   private AlertHandler alertHandler_;
/*   81:     */   private ConfirmHandler confirmHandler_;
/*   82:     */   private PromptHandler promptHandler_;
/*   83:     */   private StatusHandler statusHandler_;
/*   84:     */   private AttachmentHandler attachmentHandler_;
/*   85: 144 */   private AjaxController ajaxController_ = new AjaxController();
/*   86:     */   private BrowserVersion browserVersion_;
/*   87: 147 */   private boolean isRedirectEnabled_ = true;
/*   88: 148 */   private PageCreator pageCreator_ = new DefaultPageCreator();
/*   89: 150 */   private final Set<WebWindowListener> webWindowListeners_ = new HashSet(5);
/*   90: 151 */   private final Stack<TopLevelWindow> topLevelWindows_ = new Stack();
/*   91: 152 */   private final List<WebWindow> windows_ = Collections.synchronizedList(new ArrayList());
/*   92:     */   private WebWindow currentWindow_;
/*   93:     */   private int timeout_;
/*   94:     */   private HTMLParserListener htmlParserListener_;
/*   95: 157 */   private ErrorHandler cssErrorHandler_ = new DefaultCssErrorHandler();
/*   96:     */   private OnbeforeunloadHandler onbeforeunloadHandler_;
/*   97: 159 */   private Cache cache_ = new Cache();
/*   98: 162 */   public static final URL URL_ABOUT_BLANK = UrlUtils.toUrlSafe("about:blank");
/*   99: 165 */   private static final WebResponse WEB_RESPONSE_FOR_ABOUT_BLANK = new StringWebResponse("", URL_ABOUT_BLANK);
/*  100:     */   private ScriptPreProcessor scriptPreProcessor_;
/*  101: 169 */   private Map<String, String> activeXObjectMap_ = Collections.emptyMap();
/*  102:     */   private boolean activeXNative_;
/*  103: 171 */   private RefreshHandler refreshHandler_ = new ImmediateRefreshHandler();
/*  104: 172 */   private boolean throwExceptionOnScriptError_ = true;
/*  105:     */   private JavaScriptErrorListener javaScriptErrorListener_;
/*  106:     */   
/*  107:     */   public WebClient()
/*  108:     */   {
/*  109: 180 */     this(BrowserVersion.getDefault());
/*  110:     */   }
/*  111:     */   
/*  112:     */   public WebClient(BrowserVersion browserVersion)
/*  113:     */   {
/*  114: 188 */     WebAssert.notNull("browserVersion", browserVersion);
/*  115: 189 */     init(browserVersion, new ProxyConfig());
/*  116:     */   }
/*  117:     */   
/*  118:     */   public WebClient(BrowserVersion browserVersion, String proxyHost, int proxyPort)
/*  119:     */   {
/*  120: 199 */     WebAssert.notNull("browserVersion", browserVersion);
/*  121: 200 */     WebAssert.notNull("proxyHost", proxyHost);
/*  122: 201 */     init(browserVersion, new ProxyConfig(proxyHost, proxyPort));
/*  123:     */   }
/*  124:     */   
/*  125:     */   private void init(BrowserVersion browserVersion, ProxyConfig proxyConfig)
/*  126:     */   {
/*  127: 211 */     this.homePage_ = "http://htmlunit.sf.net/";
/*  128: 212 */     this.browserVersion_ = browserVersion;
/*  129: 213 */     this.proxyConfig_ = proxyConfig;
/*  130:     */     
/*  131: 215 */     this.scriptEngine_ = new JavaScriptEngine(this);
/*  132:     */     
/*  133: 217 */     addWebWindowListener(new CurrentWindowTracker(this, null));
/*  134: 218 */     this.currentWindow_ = new TopLevelWindow("", this);
/*  135: 219 */     fireWindowOpened(new WebWindowEvent(this.currentWindow_, 1, null, null));
/*  136:     */   }
/*  137:     */   
/*  138:     */   public WebConnection getWebConnection()
/*  139:     */   {
/*  140: 229 */     return this.webConnection_;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public void setWebConnection(WebConnection webConnection)
/*  144:     */   {
/*  145: 240 */     WebAssert.notNull("webConnection", webConnection);
/*  146: 241 */     this.webConnection_ = webConnection;
/*  147:     */   }
/*  148:     */   
/*  149:     */   public <P extends Page> P getPage(WebWindow webWindow, WebRequest webRequest)
/*  150:     */     throws IOException, FailingHttpStatusCodeException
/*  151:     */   {
/*  152: 269 */     Page page = webWindow.getEnclosedPage();
/*  153: 271 */     if (page != null)
/*  154:     */     {
/*  155: 272 */       URL prev = page.getWebResponse().getWebRequest().getUrl();
/*  156: 273 */       URL current = webRequest.getUrl();
/*  157: 274 */       if ((current.sameFile(prev)) && (current.getRef() != null) && (!StringUtils.equals(current.getRef(), prev.getRef())))
/*  158:     */       {
/*  159: 277 */         page.getWebResponse().getWebRequest().setUrl(current);
/*  160: 278 */         webWindow.getHistory().addPage(page);
/*  161: 279 */         return page;
/*  162:     */       }
/*  163:     */     }
/*  164: 283 */     if ((page instanceof HtmlPage))
/*  165:     */     {
/*  166: 284 */       HtmlPage htmlPage = (HtmlPage)page;
/*  167: 285 */       if (!htmlPage.isOnbeforeunloadAccepted())
/*  168:     */       {
/*  169: 286 */         if (LOG.isDebugEnabled()) {
/*  170: 287 */           LOG.debug("The registered OnbeforeunloadHandler rejected to load a new page.");
/*  171:     */         }
/*  172: 289 */         return page;
/*  173:     */       }
/*  174:     */     }
/*  175: 293 */     if (LOG.isDebugEnabled()) {
/*  176: 294 */       LOG.debug("Get page for window named '" + webWindow.getName() + "', using " + webRequest);
/*  177:     */     }
/*  178: 298 */     String protocol = webRequest.getUrl().getProtocol();
/*  179:     */     WebResponse webResponse;
/*  180: 299 */     if ("javascript".equals(protocol))
/*  181:     */     {
/*  182: 300 */       WebResponse webResponse = makeWebResponseForJavaScriptUrl(webWindow, webRequest.getUrl(), webRequest.getCharset());
/*  183: 301 */       if ((webWindow.getEnclosedPage() != null) && (webWindow.getEnclosedPage().getWebResponse() == webResponse)) {
/*  184: 303 */         return webWindow.getEnclosedPage();
/*  185:     */       }
/*  186:     */     }
/*  187:     */     else
/*  188:     */     {
/*  189: 307 */       webResponse = loadWebResponse(webRequest);
/*  190:     */     }
/*  191: 310 */     printContentIfNecessary(webResponse);
/*  192: 311 */     loadWebResponseInto(webResponse, webWindow);
/*  193: 312 */     throwFailingHttpStatusCodeExceptionIfNecessary(webResponse);
/*  194: 315 */     if (this.scriptEngine_ != null) {
/*  195: 316 */       this.scriptEngine_.registerWindowAndMaybeStartEventLoop(webWindow);
/*  196:     */     }
/*  197: 319 */     return webWindow.getEnclosedPage();
/*  198:     */   }
/*  199:     */   
/*  200:     */   public <P extends Page> P getPage(WebWindow opener, String target, WebRequest params)
/*  201:     */     throws FailingHttpStatusCodeException, IOException
/*  202:     */   {
/*  203: 341 */     return getPage(openTargetWindow(opener, target, "_self"), params);
/*  204:     */   }
/*  205:     */   
/*  206:     */   public <P extends Page> P getPage(String url)
/*  207:     */     throws IOException, FailingHttpStatusCodeException, MalformedURLException
/*  208:     */   {
/*  209: 358 */     return getPage(UrlUtils.toUrlUnsafe(url));
/*  210:     */   }
/*  211:     */   
/*  212:     */   public <P extends Page> P getPage(URL url)
/*  213:     */     throws IOException, FailingHttpStatusCodeException
/*  214:     */   {
/*  215: 373 */     return getPage(getCurrentWindow().getTopWindow(), new WebRequest(url));
/*  216:     */   }
/*  217:     */   
/*  218:     */   public <P extends Page> P getPage(WebRequest request)
/*  219:     */     throws IOException, FailingHttpStatusCodeException
/*  220:     */   {
/*  221: 389 */     return getPage(getCurrentWindow().getTopWindow(), request);
/*  222:     */   }
/*  223:     */   
/*  224:     */   public Page loadWebResponseInto(WebResponse webResponse, WebWindow webWindow)
/*  225:     */     throws IOException, FailingHttpStatusCodeException
/*  226:     */   {
/*  227: 412 */     WebAssert.notNull("webResponse", webResponse);
/*  228: 413 */     WebAssert.notNull("webWindow", webWindow);
/*  229: 415 */     if (webResponse.getStatusCode() == 204) {
/*  230: 416 */       return webWindow.getEnclosedPage();
/*  231:     */     }
/*  232: 419 */     if ((this.attachmentHandler_ != null) && (Attachment.isAttachment(webResponse)))
/*  233:     */     {
/*  234: 420 */       WebWindow w = openWindow(null, null, webWindow);
/*  235: 421 */       Page page = this.pageCreator_.createPage(webResponse, w);
/*  236: 422 */       this.attachmentHandler_.handleAttachment(page);
/*  237: 423 */       return page;
/*  238:     */     }
/*  239: 426 */     Page oldPage = webWindow.getEnclosedPage();
/*  240: 427 */     if (oldPage != null) {
/*  241: 429 */       oldPage.cleanUp();
/*  242:     */     }
/*  243: 431 */     Page newPage = null;
/*  244: 432 */     if ((this.windows_.contains(webWindow)) || (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_150)))
/*  245:     */     {
/*  246: 433 */       newPage = this.pageCreator_.createPage(webResponse, webWindow);
/*  247: 435 */       if (this.windows_.contains(webWindow))
/*  248:     */       {
/*  249: 436 */         fireWindowContentChanged(new WebWindowEvent(webWindow, 3, oldPage, newPage));
/*  250: 439 */         if (webWindow.getEnclosedPage() == newPage)
/*  251:     */         {
/*  252: 440 */           newPage.initialize();
/*  253: 443 */           if (((webWindow instanceof FrameWindow)) && (!(newPage instanceof HtmlPage)))
/*  254:     */           {
/*  255: 444 */             FrameWindow fw = (FrameWindow)webWindow;
/*  256: 445 */             BaseFrame frame = fw.getFrameElement();
/*  257: 446 */             if (frame.hasEventHandlers("onload"))
/*  258:     */             {
/*  259: 447 */               if (LOG.isDebugEnabled()) {
/*  260: 448 */                 LOG.debug("Executing onload handler for " + frame);
/*  261:     */               }
/*  262: 450 */               Event event = new Event(frame, "load");
/*  263: 451 */               ((Node)frame.getScriptObject()).executeEvent(event);
/*  264:     */             }
/*  265:     */           }
/*  266:     */         }
/*  267:     */       }
/*  268:     */     }
/*  269: 457 */     return newPage;
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void setPrintContentOnFailingStatusCode(boolean enabled)
/*  273:     */   {
/*  274: 468 */     this.printContentOnFailingStatusCode_ = enabled;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public boolean getPrintContentOnFailingStatusCode()
/*  278:     */   {
/*  279: 480 */     return this.printContentOnFailingStatusCode_;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public void printContentIfNecessary(WebResponse webResponse)
/*  283:     */   {
/*  284: 492 */     String contentType = webResponse.getContentType();
/*  285: 493 */     int statusCode = webResponse.getStatusCode();
/*  286: 494 */     boolean successful = (statusCode >= 200) && (statusCode < 300);
/*  287: 495 */     if ((getPrintContentOnFailingStatusCode()) && (!successful))
/*  288:     */     {
/*  289: 496 */       LOG.info("statusCode=[" + statusCode + "] contentType=[" + contentType + "]");
/*  290: 497 */       LOG.info(webResponse.getContentAsString());
/*  291:     */     }
/*  292:     */   }
/*  293:     */   
/*  294:     */   public void setThrowExceptionOnFailingStatusCode(boolean enabled)
/*  295:     */   {
/*  296: 509 */     this.throwExceptionOnFailingStatusCode_ = enabled;
/*  297:     */   }
/*  298:     */   
/*  299:     */   public boolean isThrowExceptionOnFailingStatusCode()
/*  300:     */   {
/*  301: 518 */     return this.throwExceptionOnFailingStatusCode_;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public void throwFailingHttpStatusCodeExceptionIfNecessary(WebResponse webResponse)
/*  305:     */   {
/*  306: 530 */     int statusCode = webResponse.getStatusCode();
/*  307: 531 */     boolean successful = ((statusCode >= 200) && (statusCode < 300)) || (statusCode == 305) || (statusCode == 304);
/*  308: 534 */     if ((isThrowExceptionOnFailingStatusCode()) && (!successful)) {
/*  309: 535 */       throw new FailingHttpStatusCodeException(webResponse);
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void addRequestHeader(String name, String value)
/*  314:     */   {
/*  315: 546 */     this.requestHeaders_.put(name, value);
/*  316:     */   }
/*  317:     */   
/*  318:     */   public void removeRequestHeader(String name)
/*  319:     */   {
/*  320: 555 */     this.requestHeaders_.remove(name);
/*  321:     */   }
/*  322:     */   
/*  323:     */   public void setCredentialsProvider(CredentialsProvider credentialsProvider)
/*  324:     */   {
/*  325: 566 */     WebAssert.notNull("credentialsProvider", credentialsProvider);
/*  326: 567 */     this.credentialsProvider_ = credentialsProvider;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public CredentialsProvider getCredentialsProvider()
/*  330:     */   {
/*  331: 576 */     return this.credentialsProvider_;
/*  332:     */   }
/*  333:     */   
/*  334:     */   public JavaScriptEngine getJavaScriptEngine()
/*  335:     */   {
/*  336: 584 */     return this.scriptEngine_;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public void setJavaScriptEngine(JavaScriptEngine engine)
/*  340:     */   {
/*  341: 593 */     if (engine == null) {
/*  342: 594 */       throw new NullPointerException("Can't set JavaScriptEngine to null");
/*  343:     */     }
/*  344: 596 */     this.scriptEngine_ = engine;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public void setJavaScriptEnabled(boolean enabled)
/*  348:     */   {
/*  349: 605 */     this.javaScriptEnabled_ = enabled;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public boolean isJavaScriptEnabled()
/*  353:     */   {
/*  354: 614 */     return this.javaScriptEnabled_;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public void setCssEnabled(boolean enabled)
/*  358:     */   {
/*  359: 623 */     this.cssEnabled_ = enabled;
/*  360:     */   }
/*  361:     */   
/*  362:     */   public boolean isCssEnabled()
/*  363:     */   {
/*  364: 632 */     return this.cssEnabled_;
/*  365:     */   }
/*  366:     */   
/*  367:     */   public void setAppletEnabled(boolean enabled)
/*  368:     */   {
/*  369: 644 */     this.appletEnabled_ = enabled;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public boolean isAppletEnabled()
/*  373:     */   {
/*  374: 653 */     return this.appletEnabled_;
/*  375:     */   }
/*  376:     */   
/*  377:     */   public void setPopupBlockerEnabled(boolean enabled)
/*  378:     */   {
/*  379: 664 */     this.popupBlockerEnabled_ = enabled;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public boolean isPopupBlockerEnabled()
/*  383:     */   {
/*  384: 673 */     return this.popupBlockerEnabled_;
/*  385:     */   }
/*  386:     */   
/*  387:     */   public String getHomePage()
/*  388:     */   {
/*  389: 681 */     return this.homePage_;
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void setHomePage(String homePage)
/*  393:     */   {
/*  394: 689 */     this.homePage_ = homePage;
/*  395:     */   }
/*  396:     */   
/*  397:     */   public ProxyConfig getProxyConfig()
/*  398:     */   {
/*  399: 697 */     return this.proxyConfig_;
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void setProxyConfig(ProxyConfig proxyConfig)
/*  403:     */   {
/*  404: 705 */     WebAssert.notNull("proxyConfig", proxyConfig);
/*  405: 706 */     this.proxyConfig_ = proxyConfig;
/*  406:     */   }
/*  407:     */   
/*  408:     */   public CookieManager getCookieManager()
/*  409:     */   {
/*  410: 714 */     return this.cookieManager_;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public void setCookieManager(CookieManager cookieManager)
/*  414:     */   {
/*  415: 722 */     WebAssert.notNull("cookieManager", cookieManager);
/*  416: 723 */     this.cookieManager_ = cookieManager;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public void setAlertHandler(AlertHandler alertHandler)
/*  420:     */   {
/*  421: 731 */     this.alertHandler_ = alertHandler;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public AlertHandler getAlertHandler()
/*  425:     */   {
/*  426: 739 */     return this.alertHandler_;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public void setConfirmHandler(ConfirmHandler handler)
/*  430:     */   {
/*  431: 747 */     this.confirmHandler_ = handler;
/*  432:     */   }
/*  433:     */   
/*  434:     */   public ConfirmHandler getConfirmHandler()
/*  435:     */   {
/*  436: 755 */     return this.confirmHandler_;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public void setPromptHandler(PromptHandler handler)
/*  440:     */   {
/*  441: 763 */     this.promptHandler_ = handler;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public PromptHandler getPromptHandler()
/*  445:     */   {
/*  446: 771 */     return this.promptHandler_;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public void setStatusHandler(StatusHandler statusHandler)
/*  450:     */   {
/*  451: 779 */     this.statusHandler_ = statusHandler;
/*  452:     */   }
/*  453:     */   
/*  454:     */   public StatusHandler getStatusHandler()
/*  455:     */   {
/*  456: 787 */     return this.statusHandler_;
/*  457:     */   }
/*  458:     */   
/*  459:     */   public void setJavaScriptErrorListener(JavaScriptErrorListener javaScriptErrorListener)
/*  460:     */   {
/*  461: 795 */     this.javaScriptErrorListener_ = javaScriptErrorListener;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public JavaScriptErrorListener getJavaScriptErrorListener()
/*  465:     */   {
/*  466: 803 */     return this.javaScriptErrorListener_;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public BrowserVersion getBrowserVersion()
/*  470:     */   {
/*  471: 811 */     return this.browserVersion_;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public WebWindow getCurrentWindow()
/*  475:     */   {
/*  476: 820 */     return this.currentWindow_;
/*  477:     */   }
/*  478:     */   
/*  479:     */   public void setCurrentWindow(WebWindow window)
/*  480:     */   {
/*  481: 829 */     WebAssert.notNull("window", window);
/*  482: 830 */     if (this.currentWindow_ == window) {
/*  483: 831 */       return;
/*  484:     */     }
/*  485: 834 */     if ((this.currentWindow_ != null) && (!this.currentWindow_.isClosed()))
/*  486:     */     {
/*  487: 835 */       Page enclosedPage = this.currentWindow_.getEnclosedPage();
/*  488: 836 */       if ((enclosedPage instanceof HtmlPage))
/*  489:     */       {
/*  490: 837 */         HtmlElement focusedElement = ((HtmlPage)enclosedPage).getFocusedElement();
/*  491: 838 */         if (focusedElement != null) {
/*  492: 839 */           focusedElement.fireEvent("blur");
/*  493:     */         }
/*  494:     */       }
/*  495:     */     }
/*  496: 843 */     this.currentWindow_ = window;
/*  497:     */     
/*  498:     */ 
/*  499: 846 */     Page enclosedPage = this.currentWindow_.getEnclosedPage();
/*  500: 847 */     if ((enclosedPage instanceof HtmlPage))
/*  501:     */     {
/*  502: 848 */       Window jsWindow = (Window)this.currentWindow_.getScriptObject();
/*  503: 849 */       if (jsWindow != null) {
/*  504: 850 */         if (getBrowserVersion().hasFeature(BrowserVersionFeatures.WINDOW_ACTIVE_ELEMENT_FOCUSED))
/*  505:     */         {
/*  506: 851 */           HTMLElement activeElement = (HTMLElement)((HTMLDocument)jsWindow.getDocument()).jsxGet_activeElement();
/*  507: 853 */           if (activeElement != null) {
/*  508: 854 */             ((HtmlPage)enclosedPage).setFocusedElement(activeElement.getDomNodeOrDie(), true);
/*  509:     */           }
/*  510:     */         }
/*  511:     */         else
/*  512:     */         {
/*  513: 858 */           HtmlElement focusedElement = ((HtmlPage)enclosedPage).getFocusedElement();
/*  514: 859 */           if (focusedElement != null) {
/*  515: 860 */             ((HtmlPage)enclosedPage).setFocusedElement(focusedElement, true);
/*  516:     */           }
/*  517:     */         }
/*  518:     */       }
/*  519:     */     }
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void addWebWindowListener(WebWindowListener listener)
/*  523:     */   {
/*  524: 873 */     WebAssert.notNull("listener", listener);
/*  525: 874 */     this.webWindowListeners_.add(listener);
/*  526:     */   }
/*  527:     */   
/*  528:     */   public void removeWebWindowListener(WebWindowListener listener)
/*  529:     */   {
/*  530: 882 */     WebAssert.notNull("listener", listener);
/*  531: 883 */     this.webWindowListeners_.remove(listener);
/*  532:     */   }
/*  533:     */   
/*  534:     */   private void fireWindowContentChanged(WebWindowEvent event)
/*  535:     */   {
/*  536: 887 */     for (WebWindowListener listener : new ArrayList(this.webWindowListeners_)) {
/*  537: 888 */       listener.webWindowContentChanged(event);
/*  538:     */     }
/*  539:     */   }
/*  540:     */   
/*  541:     */   private void fireWindowOpened(WebWindowEvent event)
/*  542:     */   {
/*  543: 893 */     for (WebWindowListener listener : new ArrayList(this.webWindowListeners_)) {
/*  544: 894 */       listener.webWindowOpened(event);
/*  545:     */     }
/*  546:     */   }
/*  547:     */   
/*  548:     */   private void fireWindowClosed(WebWindowEvent event)
/*  549:     */   {
/*  550: 899 */     for (WebWindowListener listener : new ArrayList(this.webWindowListeners_)) {
/*  551: 900 */       listener.webWindowClosed(event);
/*  552:     */     }
/*  553:     */   }
/*  554:     */   
/*  555:     */   public WebWindow openWindow(URL url, String windowName)
/*  556:     */   {
/*  557: 913 */     WebAssert.notNull("windowName", windowName);
/*  558: 914 */     return openWindow(url, windowName, getCurrentWindow());
/*  559:     */   }
/*  560:     */   
/*  561:     */   public WebWindow openWindow(URL url, String windowName, WebWindow opener)
/*  562:     */   {
/*  563: 927 */     WebWindow window = openTargetWindow(opener, windowName, "_blank");
/*  564: 928 */     HtmlPage openerPage = (HtmlPage)opener.getEnclosedPage();
/*  565: 929 */     if (url != null)
/*  566:     */     {
/*  567:     */       try
/*  568:     */       {
/*  569: 931 */         WebRequest request = new WebRequest(url);
/*  570: 932 */         if ((getBrowserVersion().hasFeature(BrowserVersionFeatures.DIALOGWINDOW_REFERER)) && (openerPage != null))
/*  571:     */         {
/*  572: 934 */           String referer = openerPage.getWebResponse().getWebRequest().getUrl().toExternalForm();
/*  573: 935 */           request.setAdditionalHeader("Referer", referer);
/*  574:     */         }
/*  575: 937 */         getPage(window, request);
/*  576:     */       }
/*  577:     */       catch (IOException e)
/*  578:     */       {
/*  579: 940 */         LOG.error("Error loading content into window", e);
/*  580:     */       }
/*  581:     */     }
/*  582:     */     else
/*  583:     */     {
/*  584: 944 */       initializeEmptyWindow(window);
/*  585: 945 */       if (openerPage != null)
/*  586:     */       {
/*  587: 946 */         Window jsWindow = (Window)window.getScriptObject();
/*  588: 947 */         jsWindow.setDomNode(openerPage);
/*  589: 948 */         jsWindow.getDocument().setDomNode(openerPage);
/*  590:     */       }
/*  591:     */     }
/*  592: 951 */     return window;
/*  593:     */   }
/*  594:     */   
/*  595:     */   private WebWindow openTargetWindow(WebWindow opener, String windowName, String defaultName)
/*  596:     */   {
/*  597: 968 */     WebAssert.notNull("opener", opener);
/*  598: 969 */     WebAssert.notNull("defaultName", defaultName);
/*  599:     */     
/*  600: 971 */     String windowToOpen = windowName;
/*  601: 972 */     if ((windowToOpen == null) || (windowToOpen.length() == 0)) {
/*  602: 973 */       windowToOpen = defaultName;
/*  603:     */     }
/*  604: 976 */     WebWindow webWindow = resolveWindow(opener, windowToOpen);
/*  605: 978 */     if (webWindow == null)
/*  606:     */     {
/*  607: 979 */       if ("_blank".equals(windowToOpen)) {
/*  608: 980 */         windowToOpen = "";
/*  609:     */       }
/*  610: 982 */       webWindow = new TopLevelWindow(windowToOpen, this);
/*  611: 983 */       fireWindowOpened(new WebWindowEvent(webWindow, 1, null, null));
/*  612:     */     }
/*  613: 986 */     if (((webWindow instanceof TopLevelWindow)) && (webWindow != opener.getTopWindow())) {
/*  614: 987 */       ((TopLevelWindow)webWindow).setOpener(opener);
/*  615:     */     }
/*  616: 990 */     return webWindow;
/*  617:     */   }
/*  618:     */   
/*  619:     */   private WebWindow resolveWindow(WebWindow opener, String name)
/*  620:     */   {
/*  621: 994 */     if ((name == null) || (name.length() == 0) || ("_self".equals(name))) {
/*  622: 995 */       return opener;
/*  623:     */     }
/*  624: 997 */     if ("_parent".equals(name)) {
/*  625: 998 */       return opener.getParentWindow();
/*  626:     */     }
/*  627:1000 */     if ("_top".equals(name)) {
/*  628:1001 */       return opener.getTopWindow();
/*  629:     */     }
/*  630:1003 */     if ("_blank".equals(name)) {
/*  631:1004 */       return null;
/*  632:     */     }
/*  633:1006 */     if (name.length() != 0) {
/*  634:     */       try
/*  635:     */       {
/*  636:1008 */         return getWebWindowByName(name);
/*  637:     */       }
/*  638:     */       catch (WebWindowNotFoundException e) {}
/*  639:     */     }
/*  640:1014 */     return null;
/*  641:     */   }
/*  642:     */   
/*  643:     */   public DialogWindow openDialogWindow(URL url, WebWindow opener, Object dialogArguments)
/*  644:     */     throws IOException
/*  645:     */   {
/*  646:1030 */     WebAssert.notNull("url", url);
/*  647:1031 */     WebAssert.notNull("opener", opener);
/*  648:     */     
/*  649:1033 */     DialogWindow window = new DialogWindow(this, dialogArguments);
/*  650:1034 */     fireWindowOpened(new WebWindowEvent(window, 1, null, null));
/*  651:     */     
/*  652:1036 */     HtmlPage openerPage = (HtmlPage)opener.getEnclosedPage();
/*  653:1037 */     WebRequest request = new WebRequest(url);
/*  654:1038 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.DIALOGWINDOW_REFERER))
/*  655:     */     {
/*  656:1039 */       String referer = openerPage.getWebResponse().getWebRequest().getUrl().toExternalForm();
/*  657:1040 */       request.setAdditionalHeader("Referer", referer);
/*  658:     */     }
/*  659:1043 */     getPage(window, request);
/*  660:     */     
/*  661:1045 */     return window;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void setRedirectEnabled(boolean enabled)
/*  665:     */   {
/*  666:1054 */     this.isRedirectEnabled_ = enabled;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public boolean isRedirectEnabled()
/*  670:     */   {
/*  671:1063 */     return this.isRedirectEnabled_;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public void setUseInsecureSSL(boolean useInsecureSSL)
/*  675:     */     throws GeneralSecurityException
/*  676:     */   {
/*  677:1079 */     WebConnection webConnection = getWebConnection();
/*  678:1080 */     while ((webConnection instanceof WebConnectionWrapper)) {
/*  679:1081 */       webConnection = ((WebConnectionWrapper)webConnection).getWrappedWebConnection();
/*  680:     */     }
/*  681:1084 */     if ((webConnection instanceof HttpWebConnection)) {
/*  682:1085 */       ((HttpWebConnection)webConnection).setUseInsecureSSL(useInsecureSSL);
/*  683:     */     } else {
/*  684:1088 */       LOG.warn("Can't configure useInsecureSSL on " + this.webConnection_);
/*  685:     */     }
/*  686:     */   }
/*  687:     */   
/*  688:     */   public void setPageCreator(PageCreator pageCreator)
/*  689:     */   {
/*  690:1099 */     WebAssert.notNull("pageCreator", pageCreator);
/*  691:1100 */     this.pageCreator_ = pageCreator;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public PageCreator getPageCreator()
/*  695:     */   {
/*  696:1109 */     return this.pageCreator_;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public WebWindow getWebWindowByName(String name)
/*  700:     */     throws WebWindowNotFoundException
/*  701:     */   {
/*  702:1122 */     WebAssert.notNull("name", name);
/*  703:1124 */     for (WebWindow webWindow : this.windows_) {
/*  704:1125 */       if (webWindow.getName().equals(name)) {
/*  705:1126 */         return webWindow;
/*  706:     */       }
/*  707:     */     }
/*  708:1130 */     throw new WebWindowNotFoundException(name);
/*  709:     */   }
/*  710:     */   
/*  711:     */   public void initialize(WebWindow webWindow)
/*  712:     */   {
/*  713:1140 */     WebAssert.notNull("webWindow", webWindow);
/*  714:1141 */     this.scriptEngine_.initialize(webWindow);
/*  715:     */   }
/*  716:     */   
/*  717:     */   public void initialize(Page newPage)
/*  718:     */   {
/*  719:1151 */     WebAssert.notNull("newPage", newPage);
/*  720:1152 */     ((Window)newPage.getEnclosingWindow().getScriptObject()).initialize(newPage);
/*  721:     */   }
/*  722:     */   
/*  723:     */   public void initializeEmptyWindow(WebWindow webWindow)
/*  724:     */   {
/*  725:1163 */     WebAssert.notNull("webWindow", webWindow);
/*  726:1164 */     initialize(webWindow);
/*  727:1165 */     ((Window)webWindow.getScriptObject()).initialize();
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void registerWebWindow(WebWindow webWindow)
/*  731:     */   {
/*  732:1176 */     WebAssert.notNull("webWindow", webWindow);
/*  733:1177 */     this.windows_.add(webWindow);
/*  734:     */   }
/*  735:     */   
/*  736:     */   public void deregisterWebWindow(WebWindow webWindow)
/*  737:     */   {
/*  738:1188 */     WebAssert.notNull("webWindow", webWindow);
/*  739:1189 */     this.windows_.remove(webWindow);
/*  740:1190 */     fireWindowClosed(new WebWindowEvent(webWindow, 2, webWindow.getEnclosedPage(), null));
/*  741:     */   }
/*  742:     */   
/*  743:     */   public static URL expandUrl(URL baseUrl, String relativeUrl)
/*  744:     */     throws MalformedURLException
/*  745:     */   {
/*  746:1206 */     String newUrl = UrlUtils.resolveUrl(baseUrl, relativeUrl);
/*  747:1207 */     return UrlUtils.toUrlUnsafe(newUrl);
/*  748:     */   }
/*  749:     */   
/*  750:     */   private WebResponse makeWebResponseForDataUrl(WebRequest webRequest)
/*  751:     */     throws IOException
/*  752:     */   {
/*  753:1211 */     URL url = webRequest.getUrl();
/*  754:1212 */     List<NameValuePair> responseHeaders = new ArrayList();
/*  755:     */     DataUrlDecoder decoder;
/*  756:     */     try
/*  757:     */     {
/*  758:1215 */       decoder = DataUrlDecoder.decode(url);
/*  759:     */     }
/*  760:     */     catch (DecoderException e)
/*  761:     */     {
/*  762:1218 */       throw new IOException(e.getMessage());
/*  763:     */     }
/*  764:1220 */     responseHeaders.add(new NameValuePair("content-type", decoder.getMediaType() + ";charset=" + decoder.getCharset()));
/*  765:     */     
/*  766:1222 */     DownloadedContent downloadedContent = HttpWebConnection.downloadContent(url.openStream());
/*  767:1223 */     WebResponseData data = new WebResponseData(downloadedContent, 200, "OK", responseHeaders);
/*  768:1224 */     return new WebResponse(data, url, webRequest.getHttpMethod(), 0L);
/*  769:     */   }
/*  770:     */   
/*  771:     */   private WebResponse makeWebResponseForAboutUrl(URL url)
/*  772:     */   {
/*  773:1228 */     String urlWithoutQuery = StringUtils.substringBefore(url.toExternalForm(), "?");
/*  774:1229 */     if (!"blank".equalsIgnoreCase(StringUtils.substringAfter(urlWithoutQuery, "about:"))) {
/*  775:1230 */       throw new IllegalArgumentException(url + " is not supported, only about:blank is supported now.");
/*  776:     */     }
/*  777:1232 */     return WEB_RESPONSE_FOR_ABOUT_BLANK;
/*  778:     */   }
/*  779:     */   
/*  780:     */   private WebResponse makeWebResponseForFileUrl(WebRequest webRequest)
/*  781:     */     throws IOException
/*  782:     */   {
/*  783:1245 */     URL cleanUrl = webRequest.getUrl();
/*  784:1246 */     if (cleanUrl.getQuery() != null) {
/*  785:1248 */       cleanUrl = UrlUtils.getUrlWithNewQuery(cleanUrl, null);
/*  786:     */     }
/*  787:1250 */     if (cleanUrl.getRef() != null) {
/*  788:1252 */       cleanUrl = UrlUtils.getUrlWithNewRef(cleanUrl, null);
/*  789:     */     }
/*  790:1255 */     File file = FileUtils.toFile(cleanUrl);
/*  791:1256 */     String contentType = guessContentType(file);
/*  792:     */     
/*  793:1258 */     DownloadedContent content = new DownloadedContent.OnFile(file);
/*  794:1259 */     List<NameValuePair> compiledHeaders = new ArrayList();
/*  795:1260 */     compiledHeaders.add(new NameValuePair("Content-Type", contentType));
/*  796:1261 */     WebResponseData responseData = new WebResponseData(content, 200, "OK", compiledHeaders);
/*  797:1262 */     return new WebResponse(responseData, webRequest, 0L);
/*  798:     */   }
/*  799:     */   
/*  800:     */   public String guessContentType(File file)
/*  801:     */   {
/*  802:1274 */     String contentType = URLConnection.guessContentTypeFromName(file.getName());
/*  803:1275 */     if (file.getName().endsWith(".xhtml")) {
/*  804:1277 */       contentType = "application/xhtml+xml";
/*  805:     */     }
/*  806:1279 */     if (contentType == null)
/*  807:     */     {
/*  808:1280 */       InputStream inputStream = null;
/*  809:     */       try
/*  810:     */       {
/*  811:1282 */         inputStream = new BufferedInputStream(new FileInputStream(file));
/*  812:1283 */         contentType = URLConnection.guessContentTypeFromStream(inputStream);
/*  813:     */       }
/*  814:     */       catch (IOException e) {}finally
/*  815:     */       {
/*  816:1289 */         IOUtils.closeQuietly(inputStream);
/*  817:     */       }
/*  818:     */     }
/*  819:1292 */     if (contentType == null) {
/*  820:1293 */       if (file.getName().endsWith(".js")) {
/*  821:1294 */         contentType = "text/javascript";
/*  822:     */       } else {
/*  823:1297 */         contentType = "application/octet-stream";
/*  824:     */       }
/*  825:     */     }
/*  826:1300 */     return contentType;
/*  827:     */   }
/*  828:     */   
/*  829:     */   private WebResponse makeWebResponseForJavaScriptUrl(WebWindow webWindow, URL url, String charset)
/*  830:     */     throws FailingHttpStatusCodeException, IOException
/*  831:     */   {
/*  832:     */     HtmlPage page;
/*  833:     */     HtmlPage page;
/*  834:1307 */     if ((webWindow instanceof FrameWindow))
/*  835:     */     {
/*  836:1308 */       FrameWindow frameWindow = (FrameWindow)webWindow;
/*  837:1309 */       page = frameWindow.getEnclosingPage();
/*  838:     */     }
/*  839:     */     else
/*  840:     */     {
/*  841:1312 */       Page currentPage = webWindow.getEnclosedPage();
/*  842:1313 */       if (currentPage == null) {
/*  843:1315 */         currentPage = getPage(webWindow, new WebRequest(URL_ABOUT_BLANK));
/*  844:     */       }
/*  845:1317 */       page = (HtmlPage)currentPage;
/*  846:     */     }
/*  847:1320 */     ScriptResult r = page.executeJavaScriptIfPossible(url.toExternalForm(), "JavaScript URL", 1);
/*  848:1321 */     if (((r != null) && (r.getJavaScriptResult() == null)) || (ScriptResult.isUndefined(r))) {
/*  849:1323 */       return webWindow.getEnclosedPage().getWebResponse();
/*  850:     */     }
/*  851:1326 */     String contentString = r.getJavaScriptResult().toString();
/*  852:1327 */     StringWebResponse response = new StringWebResponse(contentString, charset, url);
/*  853:1328 */     response.setFromJavascript(true);
/*  854:1329 */     return response;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public WebResponse loadWebResponse(WebRequest webRequest)
/*  858:     */     throws IOException
/*  859:     */   {
/*  860:1342 */     String protocol = webRequest.getUrl().getProtocol();
/*  861:     */     WebResponse response;
/*  862:     */     WebResponse response;
/*  863:1343 */     if ("about".equals(protocol))
/*  864:     */     {
/*  865:1344 */       response = makeWebResponseForAboutUrl(webRequest.getUrl());
/*  866:     */     }
/*  867:     */     else
/*  868:     */     {
/*  869:     */       WebResponse response;
/*  870:1346 */       if ("file".equals(protocol))
/*  871:     */       {
/*  872:1347 */         response = makeWebResponseForFileUrl(webRequest);
/*  873:     */       }
/*  874:1349 */       else if ("data".equals(protocol))
/*  875:     */       {
/*  876:     */         WebResponse response;
/*  877:1350 */         if (this.browserVersion_.hasFeature(BrowserVersionFeatures.PROTOCOL_DATA)) {
/*  878:1351 */           response = makeWebResponseForDataUrl(webRequest);
/*  879:     */         } else {
/*  880:1354 */           throw new MalformedURLException("Unknown protocol: data");
/*  881:     */         }
/*  882:     */       }
/*  883:     */       else
/*  884:     */       {
/*  885:1358 */         response = loadWebResponseFromWebConnection(webRequest, 20);
/*  886:     */       }
/*  887:     */     }
/*  888:1361 */     return response;
/*  889:     */   }
/*  890:     */   
/*  891:     */   private WebResponse loadWebResponseFromWebConnection(WebRequest webRequest, int allowedRedirects)
/*  892:     */     throws IOException
/*  893:     */   {
/*  894:1374 */     URL url = webRequest.getUrl();
/*  895:1375 */     HttpMethod method = webRequest.getHttpMethod();
/*  896:1376 */     List<NameValuePair> parameters = webRequest.getRequestParameters();
/*  897:     */     
/*  898:1378 */     WebAssert.notNull("url", url);
/*  899:1379 */     WebAssert.notNull("method", method);
/*  900:1380 */     WebAssert.notNull("parameters", parameters);
/*  901:     */     
/*  902:1382 */     url = UrlUtils.encodeUrl(url, getBrowserVersion().hasFeature(BrowserVersionFeatures.URL_MINIMAL_QUERY_ENCODING));
/*  903:     */     
/*  904:1384 */     webRequest.setUrl(url);
/*  905:1386 */     if (LOG.isDebugEnabled()) {
/*  906:1387 */       LOG.debug("Load response for " + method + " " + url.toExternalForm());
/*  907:     */     }
/*  908:1391 */     if (webRequest.getProxyHost() == null) {
/*  909:1392 */       if (this.proxyConfig_.getProxyAutoConfigUrl() != null)
/*  910:     */       {
/*  911:1393 */         if (!this.proxyConfig_.getProxyAutoConfigUrl().equals(url.toExternalForm()))
/*  912:     */         {
/*  913:1394 */           String content = this.proxyConfig_.getProxyAutoConfigContent();
/*  914:1395 */           if (content == null)
/*  915:     */           {
/*  916:1396 */             content = getPage(this.proxyConfig_.getProxyAutoConfigUrl()).getWebResponse().getContentAsString();
/*  917:     */             
/*  918:1398 */             this.proxyConfig_.setProxyAutoConfigContent(content);
/*  919:     */           }
/*  920:1400 */           String allValue = ProxyAutoConfig.evaluate(content, url);
/*  921:1401 */           if (LOG.isDebugEnabled()) {
/*  922:1402 */             LOG.debug("Proxy Auto-Config: value '" + allValue + "' for URL " + url);
/*  923:     */           }
/*  924:1404 */           String value = allValue.split(";")[0].trim();
/*  925:1405 */           if (value.startsWith("PROXY"))
/*  926:     */           {
/*  927:1406 */             value = value.substring(6);
/*  928:1407 */             int colonIndex = value.indexOf(':');
/*  929:1408 */             webRequest.setSocksProxy(false);
/*  930:1409 */             webRequest.setProxyHost(value.substring(0, colonIndex));
/*  931:1410 */             webRequest.setProxyPort(Integer.parseInt(value.substring(colonIndex + 1)));
/*  932:     */           }
/*  933:1412 */           else if (value.startsWith("SOCKS"))
/*  934:     */           {
/*  935:1413 */             value = value.substring(6);
/*  936:1414 */             int colonIndex = value.indexOf(':');
/*  937:1415 */             webRequest.setSocksProxy(true);
/*  938:1416 */             webRequest.setProxyHost(value.substring(0, colonIndex));
/*  939:1417 */             webRequest.setProxyPort(Integer.parseInt(value.substring(colonIndex + 1)));
/*  940:     */           }
/*  941:     */         }
/*  942:     */       }
/*  943:1422 */       else if (!this.proxyConfig_.shouldBypassProxy(webRequest.getUrl().getHost()))
/*  944:     */       {
/*  945:1423 */         webRequest.setProxyHost(this.proxyConfig_.getProxyHost());
/*  946:1424 */         webRequest.setProxyPort(this.proxyConfig_.getProxyPort());
/*  947:1425 */         webRequest.setSocksProxy(this.proxyConfig_.isSocksProxy());
/*  948:     */       }
/*  949:     */     }
/*  950:1430 */     addDefaultHeaders(webRequest);
/*  951:     */     
/*  952:     */ 
/*  953:1433 */     Object fromCache = getCache().getCachedObject(webRequest);
/*  954:     */     WebResponse webResponse;
/*  955:     */     WebResponse webResponse;
/*  956:1435 */     if ((fromCache != null) && ((fromCache instanceof WebResponse)))
/*  957:     */     {
/*  958:1436 */       webResponse = new WebResponseFromCache((WebResponse)fromCache, webRequest);
/*  959:     */     }
/*  960:     */     else
/*  961:     */     {
/*  962:1439 */       webResponse = getWebConnection().getResponse(webRequest);
/*  963:1440 */       getCache().cacheIfPossible(webRequest, webResponse, webResponse);
/*  964:     */     }
/*  965:1444 */     int status = webResponse.getStatusCode();
/*  966:1445 */     if (status == 305)
/*  967:     */     {
/*  968:1446 */       getIncorrectnessListener().notify("Ignoring HTTP status code [305] 'Use Proxy'", this);
/*  969:     */     }
/*  970:1448 */     else if ((status >= 301) && (status <= 307) && (status != 304) && (isRedirectEnabled()))
/*  971:     */     {
/*  972:1454 */       String locationString = null;
/*  973:     */       URL newUrl;
/*  974:     */       try
/*  975:     */       {
/*  976:1456 */         locationString = webResponse.getResponseHeaderValue("Location");
/*  977:1457 */         if (locationString == null) {
/*  978:1458 */           return webResponse;
/*  979:     */         }
/*  980:1460 */         newUrl = expandUrl(url, locationString);
/*  981:     */       }
/*  982:     */       catch (MalformedURLException e)
/*  983:     */       {
/*  984:1463 */         getIncorrectnessListener().notify("Got a redirect status code [" + status + " " + webResponse.getStatusMessage() + "] but the location is not a valid URL [" + locationString + "]. Skipping redirection processing.", this);
/*  985:     */         
/*  986:     */ 
/*  987:     */ 
/*  988:1467 */         return webResponse;
/*  989:     */       }
/*  990:1470 */       if (LOG.isDebugEnabled()) {
/*  991:1471 */         LOG.debug("Got a redirect status code [" + status + "] new location = [" + locationString + "]");
/*  992:     */       }
/*  993:1474 */       if (allowedRedirects == 0) {
/*  994:1475 */         throw new FailingHttpStatusCodeException("Too much redirect for " + webResponse.getWebRequest().getUrl(), webResponse);
/*  995:     */       }
/*  996:1478 */       if (((status == 301) || (status == 307)) && (method.equals(HttpMethod.GET)))
/*  997:     */       {
/*  998:1480 */         WebRequest wrs = new WebRequest(newUrl);
/*  999:1481 */         wrs.setRequestParameters(parameters);
/* 1000:1482 */         for (Map.Entry<String, String> entry : webRequest.getAdditionalHeaders().entrySet()) {
/* 1001:1483 */           wrs.setAdditionalHeader((String)entry.getKey(), (String)entry.getValue());
/* 1002:     */         }
/* 1003:1485 */         return loadWebResponseFromWebConnection(wrs, allowedRedirects - 1);
/* 1004:     */       }
/* 1005:1487 */       if (status <= 303)
/* 1006:     */       {
/* 1007:1488 */         WebRequest wrs = new WebRequest(newUrl);
/* 1008:1489 */         wrs.setHttpMethod(HttpMethod.GET);
/* 1009:1490 */         for (Map.Entry<String, String> entry : webRequest.getAdditionalHeaders().entrySet()) {
/* 1010:1491 */           wrs.setAdditionalHeader((String)entry.getKey(), (String)entry.getValue());
/* 1011:     */         }
/* 1012:1493 */         return loadWebResponseFromWebConnection(wrs, allowedRedirects - 1);
/* 1013:     */       }
/* 1014:     */     }
/* 1015:1497 */     return webResponse;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   private void addDefaultHeaders(WebRequest wrs)
/* 1019:     */   {
/* 1020:1506 */     if (!wrs.isAdditionalHeader("Accept-Language")) {
/* 1021:1507 */       wrs.setAdditionalHeader("Accept-Language", getBrowserVersion().getBrowserLanguage());
/* 1022:     */     }
/* 1023:1510 */     wrs.getAdditionalHeaders().putAll(this.requestHeaders_);
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public List<WebWindow> getWebWindows()
/* 1027:     */   {
/* 1028:1520 */     return Collections.unmodifiableList(this.windows_);
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public List<TopLevelWindow> getTopLevelWindows()
/* 1032:     */   {
/* 1033:1530 */     return Collections.unmodifiableList(this.topLevelWindows_);
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void setRefreshHandler(RefreshHandler handler)
/* 1037:     */   {
/* 1038:1539 */     if (handler == null) {
/* 1039:1540 */       this.refreshHandler_ = new ImmediateRefreshHandler();
/* 1040:     */     } else {
/* 1041:1543 */       this.refreshHandler_ = handler;
/* 1042:     */     }
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public RefreshHandler getRefreshHandler()
/* 1046:     */   {
/* 1047:1552 */     return this.refreshHandler_;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public void setScriptPreProcessor(ScriptPreProcessor scriptPreProcessor)
/* 1051:     */   {
/* 1052:1560 */     this.scriptPreProcessor_ = scriptPreProcessor;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public ScriptPreProcessor getScriptPreProcessor()
/* 1056:     */   {
/* 1057:1568 */     return this.scriptPreProcessor_;
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public void setActiveXObjectMap(Map<String, String> activeXObjectMap)
/* 1061:     */   {
/* 1062:1579 */     this.activeXObjectMap_ = activeXObjectMap;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public Map<String, String> getActiveXObjectMap()
/* 1066:     */   {
/* 1067:1587 */     return this.activeXObjectMap_;
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public void setActiveXNative(boolean allow)
/* 1071:     */   {
/* 1072:1598 */     this.activeXNative_ = allow;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public boolean isActiveXNative()
/* 1076:     */   {
/* 1077:1606 */     return this.activeXNative_;
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public void setHTMLParserListener(HTMLParserListener listener)
/* 1081:     */   {
/* 1082:1614 */     this.htmlParserListener_ = listener;
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public HTMLParserListener getHTMLParserListener()
/* 1086:     */   {
/* 1087:1622 */     return this.htmlParserListener_;
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public ErrorHandler getCssErrorHandler()
/* 1091:     */   {
/* 1092:1632 */     return this.cssErrorHandler_;
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public void setCssErrorHandler(ErrorHandler cssErrorHandler)
/* 1096:     */   {
/* 1097:1642 */     WebAssert.notNull("cssErrorHandler", cssErrorHandler);
/* 1098:1643 */     this.cssErrorHandler_ = cssErrorHandler;
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   public void setJavaScriptTimeout(long timeout)
/* 1102:     */   {
/* 1103:1653 */     this.scriptEngine_.getContextFactory().setTimeout(timeout);
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public long getJavaScriptTimeout()
/* 1107:     */   {
/* 1108:1663 */     return this.scriptEngine_.getContextFactory().getTimeout();
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public int getTimeout()
/* 1112:     */   {
/* 1113:1673 */     return this.timeout_;
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public void setTimeout(int timeout)
/* 1117:     */   {
/* 1118:1685 */     this.timeout_ = timeout;
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public boolean isThrowExceptionOnScriptError()
/* 1122:     */   {
/* 1123:1695 */     return this.throwExceptionOnScriptError_;
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public void setThrowExceptionOnScriptError(boolean newValue)
/* 1127:     */   {
/* 1128:1703 */     this.throwExceptionOnScriptError_ = newValue;
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public IncorrectnessListener getIncorrectnessListener()
/* 1132:     */   {
/* 1133:1713 */     return this.incorrectnessListener_;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public void setIncorrectnessListener(IncorrectnessListener listener)
/* 1137:     */   {
/* 1138:1721 */     if (listener == null) {
/* 1139:1722 */       throw new NullPointerException("Null incorrectness listener.");
/* 1140:     */     }
/* 1141:1724 */     this.incorrectnessListener_ = listener;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public AjaxController getAjaxController()
/* 1145:     */   {
/* 1146:1732 */     return this.ajaxController_;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public void setAjaxController(AjaxController newValue)
/* 1150:     */   {
/* 1151:1740 */     if (newValue == null) {
/* 1152:1741 */       throw new NullPointerException();
/* 1153:     */     }
/* 1154:1743 */     this.ajaxController_ = newValue;
/* 1155:     */   }
/* 1156:     */   
/* 1157:     */   public void setAttachmentHandler(AttachmentHandler handler)
/* 1158:     */   {
/* 1159:1751 */     this.attachmentHandler_ = handler;
/* 1160:     */   }
/* 1161:     */   
/* 1162:     */   public AttachmentHandler getAttachmentHandler()
/* 1163:     */   {
/* 1164:1759 */     return this.attachmentHandler_;
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public void setOnbeforeunloadHandler(OnbeforeunloadHandler onbeforeunloadHandler)
/* 1168:     */   {
/* 1169:1767 */     this.onbeforeunloadHandler_ = onbeforeunloadHandler;
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public OnbeforeunloadHandler getOnbeforeunloadHandler()
/* 1173:     */   {
/* 1174:1775 */     return this.onbeforeunloadHandler_;
/* 1175:     */   }
/* 1176:     */   
/* 1177:     */   public Cache getCache()
/* 1178:     */   {
/* 1179:1783 */     return this.cache_;
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   public void setCache(Cache cache)
/* 1183:     */   {
/* 1184:1791 */     if (cache == null) {
/* 1185:1792 */       throw new IllegalArgumentException("cache should not be null!");
/* 1186:     */     }
/* 1187:1794 */     this.cache_ = cache;
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   private static final class CurrentWindowTracker
/* 1191:     */     implements WebWindowListener, Serializable
/* 1192:     */   {
/* 1193:     */     private WebClient webClient_;
/* 1194:     */     
/* 1195:     */     private CurrentWindowTracker(WebClient webClient)
/* 1196:     */     {
/* 1197:1804 */       this.webClient_ = webClient;
/* 1198:     */     }
/* 1199:     */     
/* 1200:     */     public void webWindowClosed(WebWindowEvent event)
/* 1201:     */     {
/* 1202:1811 */       WebWindow window = event.getWebWindow();
/* 1203:1812 */       if ((window instanceof TopLevelWindow))
/* 1204:     */       {
/* 1205:1813 */         TopLevelWindow tlw = (TopLevelWindow)event.getWebWindow();
/* 1206:1814 */         this.webClient_.topLevelWindows_.remove(tlw);
/* 1207:1815 */         if (tlw.equals(this.webClient_.getCurrentWindow())) {
/* 1208:1816 */           if (this.webClient_.topLevelWindows_.isEmpty())
/* 1209:     */           {
/* 1210:1818 */             TopLevelWindow newWindow = new TopLevelWindow("", this.webClient_);
/* 1211:1819 */             this.webClient_.topLevelWindows_.push(newWindow);
/* 1212:1820 */             this.webClient_.setCurrentWindow(newWindow);
/* 1213:     */           }
/* 1214:     */           else
/* 1215:     */           {
/* 1216:1824 */             this.webClient_.setCurrentWindow((WebWindow)this.webClient_.topLevelWindows_.peek());
/* 1217:     */           }
/* 1218:     */         }
/* 1219:     */       }
/* 1220:1828 */       else if (event.getWebWindow() == this.webClient_.getCurrentWindow())
/* 1221:     */       {
/* 1222:1830 */         this.webClient_.setCurrentWindow((WebWindow)this.webClient_.topLevelWindows_.peek());
/* 1223:     */       }
/* 1224:     */     }
/* 1225:     */     
/* 1226:     */     public void webWindowContentChanged(WebWindowEvent event)
/* 1227:     */     {
/* 1228:1837 */       WebWindow window = event.getWebWindow();
/* 1229:1838 */       boolean use = false;
/* 1230:1839 */       if ((window instanceof DialogWindow))
/* 1231:     */       {
/* 1232:1840 */         use = true;
/* 1233:     */       }
/* 1234:1842 */       else if ((window instanceof TopLevelWindow))
/* 1235:     */       {
/* 1236:1843 */         use = event.getOldPage() == null;
/* 1237:     */       }
/* 1238:1845 */       else if ((window instanceof FrameWindow))
/* 1239:     */       {
/* 1240:1846 */         FrameWindow fw = (FrameWindow)window;
/* 1241:1847 */         String enclosingPageState = fw.getEnclosingPage().getDocumentElement().getReadyState();
/* 1242:1848 */         URL frameUrl = fw.getEnclosedPage().getWebResponse().getWebRequest().getUrl();
/* 1243:1849 */         if ((!"complete".equals(enclosingPageState)) || (frameUrl == WebClient.URL_ABOUT_BLANK)) {
/* 1244:1850 */           return;
/* 1245:     */         }
/* 1246:1854 */         BaseFrame frameElement = fw.getFrameElement();
/* 1247:1855 */         if (frameElement.isDisplayed())
/* 1248:     */         {
/* 1249:1856 */           ScriptableObject scriptableObject = frameElement.getScriptObject();
/* 1250:1857 */           ComputedCSSStyleDeclaration style = ((HTMLElement)scriptableObject).jsxGet_currentStyle();
/* 1251:1858 */           use = (style.getCalculatedWidth(false, false) != 0) && (style.getCalculatedHeight(false, false) != 0);
/* 1252:     */         }
/* 1253:     */       }
/* 1254:1862 */       if (use) {
/* 1255:1863 */         this.webClient_.setCurrentWindow(window);
/* 1256:     */       }
/* 1257:     */     }
/* 1258:     */     
/* 1259:     */     public void webWindowOpened(WebWindowEvent event)
/* 1260:     */     {
/* 1261:1870 */       WebWindow window = event.getWebWindow();
/* 1262:1871 */       if ((window instanceof TopLevelWindow))
/* 1263:     */       {
/* 1264:1872 */         TopLevelWindow tlw = (TopLevelWindow)event.getWebWindow();
/* 1265:1873 */         this.webClient_.topLevelWindows_.push(tlw);
/* 1266:     */       }
/* 1267:     */     }
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   public void closeAllWindows()
/* 1271:     */   {
/* 1272:1883 */     if (this.scriptEngine_ != null) {
/* 1273:1884 */       this.scriptEngine_.shutdownJavaScriptExecutor();
/* 1274:     */     }
/* 1275:1888 */     List<TopLevelWindow> topWindows = new ArrayList();
/* 1276:1889 */     for (WebWindow window : this.topLevelWindows_) {
/* 1277:1890 */       if ((window instanceof TopLevelWindow)) {
/* 1278:1891 */         topWindows.add((TopLevelWindow)window);
/* 1279:     */       }
/* 1280:     */     }
/* 1281:1894 */     for (TopLevelWindow topWindow : topWindows) {
/* 1282:1895 */       if (this.topLevelWindows_.contains(topWindow)) {
/* 1283:1896 */         topWindow.close();
/* 1284:     */       }
/* 1285:     */     }
/* 1286:1900 */     if ((this.webConnection_ instanceof HttpWebConnection)) {
/* 1287:1901 */       ((HttpWebConnection)this.webConnection_).shutdown();
/* 1288:     */     }
/* 1289:     */   }
/* 1290:     */   
/* 1291:     */   public int waitForBackgroundJavaScript(long timeoutMillis)
/* 1292:     */   {
/* 1293:1926 */     int count = 0;
/* 1294:1927 */     long endTime = System.currentTimeMillis() + timeoutMillis;
/* 1295:1928 */     for (Iterator<WebWindow> i = this.windows_.iterator(); i.hasNext();)
/* 1296:     */     {
/* 1297:     */       WebWindow window;
/* 1298:     */       try
/* 1299:     */       {
/* 1300:1931 */         window = (WebWindow)i.next();
/* 1301:     */       }
/* 1302:     */       catch (ConcurrentModificationException e)
/* 1303:     */       {
/* 1304:1934 */         i = this.windows_.iterator();
/* 1305:1935 */         count = 0;
/* 1306:     */       }
/* 1307:1936 */       continue;
/* 1308:     */       
/* 1309:1938 */       long newTimeout = endTime - System.currentTimeMillis();
/* 1310:1939 */       count += window.getJobManager().waitForJobs(newTimeout);
/* 1311:     */     }
/* 1312:1941 */     if (count != getAggregateJobCount())
/* 1313:     */     {
/* 1314:1942 */       long newTimeout = endTime - System.currentTimeMillis();
/* 1315:1943 */       return waitForBackgroundJavaScript(newTimeout);
/* 1316:     */     }
/* 1317:1945 */     return count;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public int waitForBackgroundJavaScriptStartingBefore(long delayMillis)
/* 1321:     */   {
/* 1322:1973 */     int count = 0;
/* 1323:1974 */     long endTime = System.currentTimeMillis() + delayMillis;
/* 1324:1975 */     for (Iterator<WebWindow> i = this.windows_.iterator(); i.hasNext();)
/* 1325:     */     {
/* 1326:     */       WebWindow window;
/* 1327:     */       try
/* 1328:     */       {
/* 1329:1978 */         window = (WebWindow)i.next();
/* 1330:     */       }
/* 1331:     */       catch (ConcurrentModificationException e)
/* 1332:     */       {
/* 1333:1981 */         i = this.windows_.iterator();
/* 1334:1982 */         count = 0;
/* 1335:     */       }
/* 1336:1983 */       continue;
/* 1337:     */       
/* 1338:1985 */       long newDelay = endTime - System.currentTimeMillis();
/* 1339:1986 */       count += window.getJobManager().waitForJobsStartingBefore(newDelay);
/* 1340:     */     }
/* 1341:1988 */     if (count != getAggregateJobCount())
/* 1342:     */     {
/* 1343:1989 */       long newDelay = endTime - System.currentTimeMillis();
/* 1344:1990 */       return waitForBackgroundJavaScriptStartingBefore(newDelay);
/* 1345:     */     }
/* 1346:1992 */     return count;
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   private int getAggregateJobCount()
/* 1350:     */   {
/* 1351:2000 */     int count = 0;
/* 1352:2001 */     for (Iterator<WebWindow> i = this.windows_.iterator(); i.hasNext();)
/* 1353:     */     {
/* 1354:     */       WebWindow window;
/* 1355:     */       try
/* 1356:     */       {
/* 1357:2004 */         window = (WebWindow)i.next();
/* 1358:     */       }
/* 1359:     */       catch (ConcurrentModificationException e)
/* 1360:     */       {
/* 1361:2007 */         i = this.windows_.iterator();
/* 1362:2008 */         count = 0;
/* 1363:     */       }
/* 1364:2009 */       continue;
/* 1365:     */       
/* 1366:2011 */       count += window.getJobManager().getJobCount();
/* 1367:     */     }
/* 1368:2013 */     return count;
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   private void readObject(ObjectInputStream in)
/* 1372:     */     throws IOException, ClassNotFoundException
/* 1373:     */   {
/* 1374:2020 */     in.defaultReadObject();
/* 1375:2021 */     this.webConnection_ = createWebConnection();
/* 1376:2022 */     this.scriptEngine_ = new JavaScriptEngine(this);
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   private WebConnection createWebConnection()
/* 1380:     */   {
/* 1381:2026 */     if (GAEUtils.isGaeMode()) {
/* 1382:2027 */       return new UrlFetchWebConnection(this);
/* 1383:     */     }
/* 1384:2030 */     return new HttpWebConnection(this);
/* 1385:     */   }
/* 1386:     */   
/* 1387:     */   private static class LoadJob
/* 1388:     */   {
/* 1389:     */     private final WebWindow requestingWindow_;
/* 1390:     */     private final String target_;
/* 1391:     */     private final WebResponse response_;
/* 1392:     */     private final URL urlWithOnlyHashChange_;
/* 1393:     */     private final WeakReference<Page> originalPage_;
/* 1394:     */     
/* 1395:     */     LoadJob(WebWindow requestingWindow, String target, WebResponse response)
/* 1396:     */     {
/* 1397:2041 */       this.requestingWindow_ = requestingWindow;
/* 1398:2042 */       this.target_ = target;
/* 1399:2043 */       this.response_ = response;
/* 1400:2044 */       this.urlWithOnlyHashChange_ = null;
/* 1401:2045 */       this.originalPage_ = new WeakReference(requestingWindow.getEnclosedPage());
/* 1402:     */     }
/* 1403:     */     
/* 1404:     */     LoadJob(WebWindow requestingWindow, String target, URL urlWithOnlyHashChange)
/* 1405:     */     {
/* 1406:2048 */       this.requestingWindow_ = requestingWindow;
/* 1407:2049 */       this.target_ = target;
/* 1408:2050 */       this.response_ = null;
/* 1409:2051 */       this.urlWithOnlyHashChange_ = urlWithOnlyHashChange;
/* 1410:2052 */       this.originalPage_ = new WeakReference(requestingWindow.getEnclosedPage());
/* 1411:     */     }
/* 1412:     */     
/* 1413:     */     public boolean isOutdated()
/* 1414:     */     {
/* 1415:2055 */       if ((this.target_ != null) && (this.target_.length() != 0)) {
/* 1416:2056 */         return false;
/* 1417:     */       }
/* 1418:2058 */       if (this.requestingWindow_.isClosed()) {
/* 1419:2059 */         return true;
/* 1420:     */       }
/* 1421:2061 */       if (this.requestingWindow_.getEnclosedPage() != this.originalPage_.get()) {
/* 1422:2062 */         return true;
/* 1423:     */       }
/* 1424:2065 */       return false;
/* 1425:     */     }
/* 1426:     */   }
/* 1427:     */   
/* 1428:2069 */   private final List<LoadJob> loadQueue_ = new ArrayList();
/* 1429:     */   
/* 1430:     */   public void download(WebWindow requestingWindow, String target, WebRequest request, boolean isHashJump, String description)
/* 1431:     */   {
/* 1432:2087 */     WebWindow win = resolveWindow(requestingWindow, target);
/* 1433:2088 */     URL url = request.getUrl();
/* 1434:2089 */     boolean justHashJump = isHashJump;
/* 1435:2091 */     if ((win != null) && (HttpMethod.POST != request.getHttpMethod()))
/* 1436:     */     {
/* 1437:2092 */       Page page = win.getEnclosedPage();
/* 1438:2093 */       if (((page instanceof HtmlPage)) && (!((HtmlPage)page).isOnbeforeunloadAccepted())) {
/* 1439:2094 */         return;
/* 1440:     */       }
/* 1441:2096 */       URL current = page.getWebResponse().getWebRequest().getUrl();
/* 1442:2097 */       if ((!justHashJump) && (url.sameFile(current)) && (StringUtils.isNotEmpty(url.getRef()))) {
/* 1443:2098 */         justHashJump = true;
/* 1444:     */       }
/* 1445:     */     }
/* 1446:2102 */     synchronized (this.loadQueue_)
/* 1447:     */     {
/* 1448:2104 */       for (LoadJob loadJob : this.loadQueue_) {
/* 1449:2105 */         if (loadJob.response_ != null)
/* 1450:     */         {
/* 1451:2108 */           WebRequest otherRequest = loadJob.response_.getWebRequest();
/* 1452:2109 */           URL otherUrl = otherRequest.getUrl();
/* 1453:2111 */           if ((url.getPath().equals(otherUrl.getPath())) && (url.getHost().equals(otherUrl.getHost())) && (url.getProtocol().equals(otherUrl.getProtocol())) && (url.getPort() == otherUrl.getPort()) && (request.getHttpMethod() == otherRequest.getHttpMethod())) {
/* 1454:     */             return;
/* 1455:     */           }
/* 1456:     */         }
/* 1457:     */       }
/* 1458:     */     }
/* 1459:     */     LoadJob loadJob;
/* 1460:     */     LoadJob loadJob;
/* 1461:2122 */     if (justHashJump) {
/* 1462:2123 */       loadJob = new LoadJob(win, target, url);
/* 1463:     */     } else {
/* 1464:     */       try
/* 1465:     */       {
/* 1466:2127 */         WebResponse response = loadWebResponse(request);
/* 1467:2128 */         loadJob = new LoadJob(requestingWindow, target, response);
/* 1468:     */       }
/* 1469:     */       catch (IOException e)
/* 1470:     */       {
/* 1471:2131 */         throw new RuntimeException(e);
/* 1472:     */       }
/* 1473:     */     }
/* 1474:2134 */     synchronized (this.loadQueue_)
/* 1475:     */     {
/* 1476:2135 */       this.loadQueue_.add(loadJob);
/* 1477:     */     }
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public void loadDownloadedResponses()
/* 1481:     */     throws FailingHttpStatusCodeException, IOException
/* 1482:     */   {
/* 1483:     */     List<LoadJob> queue;
/* 1484:2152 */     synchronized (this.loadQueue_)
/* 1485:     */     {
/* 1486:2153 */       if (this.loadQueue_.isEmpty()) {
/* 1487:2154 */         return;
/* 1488:     */       }
/* 1489:2156 */       queue = new ArrayList(this.loadQueue_);
/* 1490:2157 */       this.loadQueue_.clear();
/* 1491:     */     }
/* 1492:2160 */     HashSet<WebWindow> updatedWindows = new HashSet();
/* 1493:2161 */     for (int i = queue.size() - 1; i >= 0; i--)
/* 1494:     */     {
/* 1495:2162 */       LoadJob downloadedResponse = (LoadJob)queue.get(i);
/* 1496:2163 */       if (downloadedResponse.isOutdated())
/* 1497:     */       {
/* 1498:2164 */         LOG.info("No usage of download: " + downloadedResponse);
/* 1499:     */       }
/* 1500:2167 */       else if (downloadedResponse.urlWithOnlyHashChange_ != null)
/* 1501:     */       {
/* 1502:2168 */         WebWindow window = downloadedResponse.requestingWindow_;
/* 1503:2169 */         HtmlPage page = (HtmlPage)window.getEnclosedPage();
/* 1504:2170 */         page.getWebResponse().getWebRequest().setUrl(downloadedResponse.urlWithOnlyHashChange_);
/* 1505:2171 */         window.getHistory().addPage(page);
/* 1506:     */         
/* 1507:     */ 
/* 1508:2174 */         Window jsWindow = (Window)window.getScriptObject();
/* 1509:2175 */         if (null != jsWindow)
/* 1510:     */         {
/* 1511:2176 */           Location location = jsWindow.jsxGet_location();
/* 1512:2177 */           location.jsxSet_hash(downloadedResponse.urlWithOnlyHashChange_.getRef());
/* 1513:     */         }
/* 1514:     */       }
/* 1515:     */       else
/* 1516:     */       {
/* 1517:2181 */         WebWindow window = resolveWindow(downloadedResponse.requestingWindow_, downloadedResponse.target_);
/* 1518:2183 */         if (!updatedWindows.contains(window))
/* 1519:     */         {
/* 1520:2184 */           WebWindow win = openTargetWindow(downloadedResponse.requestingWindow_, downloadedResponse.target_, "_self");
/* 1521:     */           
/* 1522:2186 */           Page pageBeforeLoad = win.getEnclosedPage();
/* 1523:2187 */           loadWebResponseInto(downloadedResponse.response_, win);
/* 1524:2188 */           throwFailingHttpStatusCodeExceptionIfNecessary(downloadedResponse.response_);
/* 1525:2191 */           if (this.scriptEngine_ != null) {
/* 1526:2192 */             this.scriptEngine_.registerWindowAndMaybeStartEventLoop(win);
/* 1527:     */           }
/* 1528:2195 */           if (pageBeforeLoad != win.getEnclosedPage()) {
/* 1529:2196 */             updatedWindows.add(win);
/* 1530:     */           }
/* 1531:     */         }
/* 1532:     */         else
/* 1533:     */         {
/* 1534:2200 */           LOG.info("No usage of download: " + downloadedResponse);
/* 1535:     */         }
/* 1536:     */       }
/* 1537:     */     }
/* 1538:     */   }
/* 1539:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebClient
 * JD-Core Version:    0.7.0.1
 */