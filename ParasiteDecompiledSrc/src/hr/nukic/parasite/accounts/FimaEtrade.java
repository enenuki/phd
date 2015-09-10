/*    1:     */ package hr.nukic.parasite.accounts;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
/*    5:     */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*    6:     */ import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
/*    7:     */ import com.gargoylesoftware.htmlunit.HttpWebConnection;
/*    8:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*    9:     */ import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
/*   10:     */ import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
/*   11:     */ import com.gargoylesoftware.htmlunit.html.HtmlDivision;
/*   12:     */ import com.gargoylesoftware.htmlunit.html.HtmlForm;
/*   13:     */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   14:     */ import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
/*   15:     */ import com.gargoylesoftware.htmlunit.html.HtmlSpan;
/*   16:     */ import com.gargoylesoftware.htmlunit.html.HtmlTable;
/*   17:     */ import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
/*   18:     */ import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
/*   19:     */ import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
/*   20:     */ import hr.nukic.parasite.accounts.security.NTLMSchemeFactory;
/*   21:     */ import hr.nukic.parasite.accounts.templates.MarketMonitorAccount;
/*   22:     */ import hr.nukic.parasite.accounts.templates.TradingAccount;
/*   23:     */ import hr.nukic.parasite.core.DataCollector;
/*   24:     */ import hr.nukic.parasite.core.Order;
/*   25:     */ import hr.nukic.parasite.core.OrderType;
/*   26:     */ import hr.nukic.parasite.core.ParasiteManager;
/*   27:     */ import hr.nukic.parasite.core.PortfolioData;
/*   28:     */ import hr.nukic.parasite.core.QueryStatus;
/*   29:     */ import hr.nukic.parasite.core.StockMarketData;
/*   30:     */ import hr.nukic.parasite.core.Transaction;
/*   31:     */ import java.io.BufferedReader;
/*   32:     */ import java.io.IOException;
/*   33:     */ import java.io.InputStreamReader;
/*   34:     */ import java.net.InetAddress;
/*   35:     */ import java.net.MalformedURLException;
/*   36:     */ import java.net.UnknownHostException;
/*   37:     */ import java.security.GeneralSecurityException;
/*   38:     */ import java.text.Format;
/*   39:     */ import java.text.NumberFormat;
/*   40:     */ import java.text.ParseException;
/*   41:     */ import java.text.SimpleDateFormat;
/*   42:     */ import java.util.ArrayList;
/*   43:     */ import java.util.Date;
/*   44:     */ import java.util.Iterator;
/*   45:     */ import java.util.List;
/*   46:     */ import java.util.Locale;
/*   47:     */ import nukic.parasite.utils.MainLogger;
/*   48:     */ import org.apache.http.auth.AuthSchemeRegistry;
/*   49:     */ import org.apache.http.impl.client.AbstractHttpClient;
/*   50:     */ 
/*   51:     */ public class FimaEtrade
/*   52:     */   extends TradingAccount
/*   53:     */   implements MarketMonitorAccount
/*   54:     */ {
/*   55:     */   public static final int DEFAULT_PAGE_LOAD_WAIT = 3000;
/*   56:     */   public static final int SHORT_PAGE_LOAD_WAIT = 1000;
/*   57:     */   public static final int LONG_PAGE_LOAD_WAIT = 6000;
/*   58:     */   public static final int TRADING_MONITOR_REFRESH_PERIOD_MINS = 60;
/*   59:     */   public static final String URL = "https://etrade.fima.com/";
/*   60:     */   public static final String ETK_PROXY = "cpbc0.etk.extern.eu.ericsson.se";
/*   61:     */   public static final float FEE_PERCENTAGE = 0.6F;
/*   62:     */   public static final float MIN_FEE = 60.0F;
/*   63:  85 */   private static FimaEtrade instance = null;
/*   64:  86 */   private static int iteration = 0;
/*   65:     */   public WebClient browser;
/*   66:     */   public String url;
/*   67:     */   private String userName;
/*   68:     */   private String password;
/*   69: 101 */   private boolean logged = false;
/*   70: 102 */   private HtmlPage currentPage = null;
/*   71: 103 */   private HtmlPage mainPage = null;
/*   72: 104 */   private HtmlPage tradingMonitorPage = null;
/*   73: 105 */   private Date tradingMonitorlastRefresh = null;
/*   74: 106 */   private HtmlPage stockTradingDetailsPage = null;
/*   75:     */   
/*   76:     */   public FimaEtrade(DataCollector dc)
/*   77:     */   {
/*   78: 109 */     super(dc, 0.6F, 60.0F);
/*   79: 110 */     this.url = "https://etrade.fima.com/";
/*   80: 112 */     if (ParasiteManager.getInstance().useProxy) {
/*   81: 113 */       this.browser = new WebClient(BrowserVersion.FIREFOX_3_6, ParasiteManager.getInstance().proxy, 8080);
/*   82:     */     } else {
/*   83: 115 */       this.browser = new WebClient(BrowserVersion.FIREFOX_3_6);
/*   84:     */     }
/*   85: 117 */     this.browser.setJavaScriptEnabled(true);
/*   86: 118 */     this.browser.setThrowExceptionOnScriptError(false);
/*   87: 119 */     this.browser.setThrowExceptionOnFailingStatusCode(false);
/*   88: 120 */     this.userName = "F049026";
/*   89: 121 */     this.password = "ffc37r22";
/*   90:     */   }
/*   91:     */   
/*   92:     */   private String getHostName()
/*   93:     */   {
/*   94: 126 */     String hostname = "";
/*   95:     */     try
/*   96:     */     {
/*   97: 128 */       InetAddress localMachine = InetAddress.getLocalHost();
/*   98: 129 */       hostname = localMachine.getHostName();
/*   99:     */     }
/*  100:     */     catch (UnknownHostException uhe)
/*  101:     */     {
/*  102: 132 */       MainLogger.error(uhe);
/*  103:     */     }
/*  104: 134 */     return hostname;
/*  105:     */   }
/*  106:     */   
/*  107:     */   private void authenticate()
/*  108:     */   {
/*  109: 139 */     this.browser.setWebConnection(new HttpWebConnection(this.browser)
/*  110:     */     {
/*  111:     */       protected synchronized AbstractHttpClient getHttpClient()
/*  112:     */       {
/*  113: 141 */         AbstractHttpClient httpClient = super.getHttpClient();
/*  114: 142 */         httpClient.getAuthSchemes().register("ntlm", new NTLMSchemeFactory());
/*  115: 143 */         return httpClient;
/*  116:     */       }
/*  117:     */     });
/*  118:     */     try
/*  119:     */     {
/*  120: 150 */       this.browser.setUseInsecureSSL(true);
/*  121:     */     }
/*  122:     */     catch (GeneralSecurityException e1)
/*  123:     */     {
/*  124: 152 */       MainLogger.error(e1);
/*  125:     */     }
/*  126: 156 */     if (this.password == "")
/*  127:     */     {
/*  128: 158 */       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
/*  129: 159 */       MainLogger.info("Please enter password for FIMA e-trade:");
/*  130:     */       try
/*  131:     */       {
/*  132: 162 */         this.password = reader.readLine();
/*  133:     */       }
/*  134:     */       catch (IOException e)
/*  135:     */       {
/*  136: 164 */         MainLogger.error(e);
/*  137:     */       }
/*  138:     */     }
/*  139: 168 */     DefaultCredentialsProvider credentialProvider = (DefaultCredentialsProvider)this.browser
/*  140: 169 */       .getCredentialsProvider();
/*  141: 170 */     credentialProvider.addNTLMCredentials(this.userName, this.password, "https://etrade.fima.com/", -1, getHostName(), "fima.com");
/*  142: 171 */     this.browser.setCredentialsProvider(credentialProvider);
/*  143:     */   }
/*  144:     */   
/*  145:     */   public synchronized boolean login()
/*  146:     */   {
/*  147: 176 */     if (!this.logged)
/*  148:     */     {
/*  149: 177 */       MainLogger.info("Logging to Fima e-Trade...");
/*  150: 178 */       authenticate();
/*  151:     */       try
/*  152:     */       {
/*  153: 181 */         this.currentPage = ((HtmlPage)this.browser.getPage("https://etrade.fima.com/"));
/*  154:     */       }
/*  155:     */       catch (FailingHttpStatusCodeException e)
/*  156:     */       {
/*  157: 183 */         MainLogger.error(e);
/*  158:     */       }
/*  159:     */       catch (MalformedURLException e)
/*  160:     */       {
/*  161: 185 */         MainLogger.error(e);
/*  162:     */       }
/*  163:     */       catch (IOException e)
/*  164:     */       {
/*  165: 187 */         MainLogger.error(e);
/*  166:     */       }
/*  167: 190 */       HtmlForm form = this.currentPage.getFormByName("aspnetForm");
/*  168:     */       
/*  169: 192 */       HtmlTextInput userNameInput = (HtmlTextInput)form.getInputByName("ctl00$ContentPlaceHolder$Around1$ctl01$UserName");
/*  170:     */       try
/*  171:     */       {
/*  172: 194 */         this.currentPage = ((HtmlPage)userNameInput.click());
/*  173:     */       }
/*  174:     */       catch (IOException e1)
/*  175:     */       {
/*  176: 196 */         MainLogger.error(e1);
/*  177:     */       }
/*  178: 198 */       userNameInput.setValueAttribute(this.userName);
/*  179:     */       
/*  180: 200 */       HtmlPasswordInput passwordInput = (HtmlPasswordInput)form.getInputByName("ctl00$ContentPlaceHolder$Around1$ctl01$Password");
/*  181:     */       try
/*  182:     */       {
/*  183: 202 */         this.currentPage = ((HtmlPage)passwordInput.click());
/*  184:     */       }
/*  185:     */       catch (IOException e1)
/*  186:     */       {
/*  187: 204 */         MainLogger.error(e1.toString());
/*  188: 205 */         e1.printStackTrace();
/*  189:     */       }
/*  190: 207 */       passwordInput.setValueAttribute(this.password);
/*  191:     */       
/*  192: 209 */       HtmlAnchor prijavaAnchor = 
/*  193: 210 */         (HtmlAnchor)form.getElementById("ctl00_ContentPlaceHolder_Around1_ctl01_LinkButton1");
/*  194:     */       try
/*  195:     */       {
/*  196: 212 */         this.currentPage = ((HtmlPage)prijavaAnchor.click());
/*  197:     */       }
/*  198:     */       catch (IOException e)
/*  199:     */       {
/*  200: 215 */         MainLogger.error(e);
/*  201:     */       }
/*  202: 222 */       HtmlSpan userNameSpan = null;
/*  203: 223 */       String userName = "";
/*  204:     */       try
/*  205:     */       {
/*  206: 225 */         userNameSpan = (HtmlSpan)this.currentPage.getHtmlElementById("ctl00_UserName");
/*  207: 226 */         userName = userNameSpan.getTextContent();
/*  208:     */       }
/*  209:     */       catch (ElementNotFoundException e)
/*  210:     */       {
/*  211: 228 */         MainLogger.error("EROROR while logging into FIMA e-TRADE!");
/*  212:     */       }
/*  213: 231 */       if (userName.contains("NENAD UKI�"))
/*  214:     */       {
/*  215: 232 */         MainLogger.info(userName + ", WELCOME TO FIMA e-TRADE!");
/*  216: 233 */         this.mainPage = this.currentPage;
/*  217: 234 */         this.logged = true;
/*  218: 235 */         return true;
/*  219:     */       }
/*  220:     */     }
/*  221: 238 */     return true;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public boolean logout()
/*  225:     */   {
/*  226: 243 */     MainLogger.info("Logging out from Fima e-trade...");
/*  227: 244 */     if (this.logged) {
/*  228: 247 */       return false;
/*  229:     */     }
/*  230: 249 */     MainLogger.info("Could not log out because not logged!");
/*  231: 250 */     return false;
/*  232:     */   }
/*  233:     */   
/*  234:     */   private synchronized boolean openTradingMonitorPageWithAllStocks()
/*  235:     */   {
/*  236: 255 */     MainLogger.info("Opening / refreshing trading monitor page with all the stocks...");
/*  237: 258 */     if (login())
/*  238:     */     {
/*  239: 259 */       HtmlAnchor tradingMonitorAnchor = 
/*  240: 260 */         (HtmlAnchor)this.currentPage.getHtmlElementById("ctl00_MainMenuPlaceHolder_MainMenu1_HyperLink3");
/*  241:     */       try
/*  242:     */       {
/*  243: 263 */         this.currentPage = ((HtmlPage)tradingMonitorAnchor.click());
/*  244:     */       }
/*  245:     */       catch (IOException e)
/*  246:     */       {
/*  247: 266 */         MainLogger.error("Could not open trading monitor page!1");
/*  248: 267 */         MainLogger.error(e.toString());
/*  249: 268 */         e.printStackTrace();
/*  250: 269 */         this.tradingMonitorPage = null;
/*  251: 270 */         return false;
/*  252:     */       }
/*  253:     */     }
/*  254:     */     else
/*  255:     */     {
/*  256: 275 */       MainLogger.error("Could not open trading monitor page!2");
/*  257: 276 */       this.tradingMonitorPage = null;
/*  258: 277 */       return false;
/*  259:     */     }
/*  260: 279 */     this.tradingMonitorPage = this.currentPage;
/*  261:     */     
/*  262:     */ 
/*  263: 282 */     HtmlCheckBoxInput checkBoxInput = (HtmlCheckBoxInput)this.tradingMonitorPage
/*  264: 283 */       .getElementById("ctl00_ContentPlaceHolder_TitleBar1_ctl00_TradedStockOnly");
/*  265:     */     
/*  266: 285 */     this.tradingMonitorPage = ((HtmlPage)checkBoxInput.setChecked(true));
/*  267:     */     
/*  268:     */ 
/*  269:     */ 
/*  270:     */ 
/*  271: 290 */     HtmlAnchor prikaziAnchor = 
/*  272: 291 */       (HtmlAnchor)this.tradingMonitorPage.getHtmlElementById("ctl00_ContentPlaceHolder_TitleBar1_ctl00_LinkRefresh");
/*  273: 293 */     if (prikaziAnchor == null)
/*  274:     */     {
/*  275: 294 */       MainLogger.error("Could not open trading monitor page!4");
/*  276: 295 */       this.tradingMonitorPage = null;
/*  277: 296 */       return false;
/*  278:     */     }
/*  279:     */     try
/*  280:     */     {
/*  281: 300 */       this.tradingMonitorPage = ((HtmlPage)prikaziAnchor.click());
/*  282:     */       
/*  283: 302 */       this.browser.waitForBackgroundJavaScript(6000L);
/*  284:     */     }
/*  285:     */     catch (IOException e)
/*  286:     */     {
/*  287: 304 */       MainLogger.error("Could not open trading monitor page!5");
/*  288: 305 */       MainLogger.error(e.toString());
/*  289: 306 */       e.printStackTrace();
/*  290: 307 */       this.tradingMonitorPage = null;
/*  291: 308 */       return false;
/*  292:     */     }
/*  293: 311 */     return true;
/*  294:     */   }
/*  295:     */   
/*  296:     */   /**
/*  297:     */    * @deprecated
/*  298:     */    */
/*  299:     */   private synchronized boolean openTradingMonitorPage()
/*  300:     */   {
/*  301: 321 */     if (login())
/*  302:     */     {
/*  303: 322 */       HtmlAnchor tradingMonitorAnchor = 
/*  304: 323 */         (HtmlAnchor)this.currentPage.getHtmlElementById("ctl00_MainMenuPlaceHolder_MainMenu1_HyperLink3");
/*  305:     */       try
/*  306:     */       {
/*  307: 326 */         this.currentPage = ((HtmlPage)tradingMonitorAnchor.click());
/*  308:     */       }
/*  309:     */       catch (IOException e)
/*  310:     */       {
/*  311: 329 */         MainLogger.error(e);
/*  312:     */       }
/*  313:     */     }
/*  314:     */     else
/*  315:     */     {
/*  316: 335 */       MainLogger.error("Could not open trading monitor page!");
/*  317: 336 */       return false;
/*  318:     */     }
/*  319: 338 */     this.tradingMonitorPage = this.currentPage;
/*  320: 339 */     return true;
/*  321:     */   }
/*  322:     */   
/*  323:     */   private synchronized boolean openStockTradingDetailsPage(String ticker)
/*  324:     */   {
/*  325: 346 */     Date now = new Date();
/*  326: 348 */     if ((this.tradingMonitorPage == null) || 
/*  327: 349 */       (now.getTime() - this.tradingMonitorlastRefresh.getTime() > 3600000L))
/*  328:     */     {
/*  329: 351 */       if (!openTradingMonitorPageWithAllStocks())
/*  330:     */       {
/*  331: 352 */         MainLogger.error("Error while opening stockTradingDetailsPage for ticker " + ticker);
/*  332: 353 */         return false;
/*  333:     */       }
/*  334: 355 */       this.tradingMonitorlastRefresh = now;
/*  335:     */     }
/*  336: 359 */     List anchors = this.tradingMonitorPage.getElementsByTagName("a");
/*  337: 360 */     Iterator i = anchors.iterator();
/*  338: 361 */     while (i.hasNext())
/*  339:     */     {
/*  340: 362 */       HtmlAnchor a = (HtmlAnchor)i.next();
/*  341:     */       
/*  342: 364 */       String id = a.getId();
/*  343: 365 */       boolean favorite = false;
/*  344: 366 */       if (id != null) {
/*  345: 367 */         favorite = 
/*  346: 368 */           id.contains("ctl00_PreviewBoxPlaceholder_PreviewBox1_TabContainer1_TabPanelFavs_FavouriteStocksRepeater");
/*  347:     */       }
/*  348: 371 */       if ((a.getTextContent().contains(ticker)) && (!favorite)) {
/*  349:     */         try
/*  350:     */         {
/*  351: 373 */           this.stockTradingDetailsPage = ((HtmlPage)a.click());
/*  352:     */           
/*  353: 375 */           this.browser.waitForBackgroundJavaScript(1000L);
/*  354:     */         }
/*  355:     */         catch (IOException e)
/*  356:     */         {
/*  357: 377 */           MainLogger.error("Exception happend while openinig stockTradingDetailsPage");
/*  358: 378 */           MainLogger.error(e.toString());
/*  359: 379 */           e.printStackTrace();
/*  360:     */         }
/*  361:     */       }
/*  362:     */     }
/*  363: 385 */     return true;
/*  364:     */   }
/*  365:     */   
/*  366:     */   /**
/*  367:     */    * @deprecated
/*  368:     */    */
/*  369:     */   private synchronized boolean openStockTradingDetailsPageOld(String ticker)
/*  370:     */   {
/*  371: 397 */     if ((this.tradingMonitorPage != null) && (this.logged))
/*  372:     */     {
/*  373:     */       try
/*  374:     */       {
/*  375: 400 */         refreshTradingMonitorPage();
/*  376:     */       }
/*  377:     */       catch (IOException e1)
/*  378:     */       {
/*  379: 402 */         MainLogger.error(e1);
/*  380:     */       }
/*  381: 404 */       List anchors = this.tradingMonitorPage.getElementsByTagName("a");
/*  382: 405 */       Iterator i = anchors.iterator();
/*  383: 406 */       while (i.hasNext())
/*  384:     */       {
/*  385: 407 */         HtmlAnchor a = (HtmlAnchor)i.next();
/*  386:     */         
/*  387:     */ 
/*  388: 410 */         String id = a.getId();
/*  389: 411 */         boolean favorite = false;
/*  390: 412 */         if (id != null) {
/*  391: 413 */           favorite = 
/*  392: 414 */             id.contains("ctl00_PreviewBoxPlaceholder_PreviewBox1_TabContainer1_TabPanelFavs_FavouriteStocksRepeater");
/*  393:     */         }
/*  394: 417 */         if ((a.getTextContent().contains(ticker)) && (!favorite)) {
/*  395:     */           try
/*  396:     */           {
/*  397: 419 */             this.stockTradingDetailsPage = ((HtmlPage)a.click());
/*  398:     */           }
/*  399:     */           catch (IOException e)
/*  400:     */           {
/*  401: 421 */             MainLogger.error(e);
/*  402:     */           }
/*  403:     */         }
/*  404:     */       }
/*  405:     */     }
/*  406: 430 */     else if (openTradingMonitorPage())
/*  407:     */     {
/*  408: 431 */       this.tradingMonitorlastRefresh = new Date();
/*  409: 432 */       List anchors = this.tradingMonitorPage.getElementsByTagName("a");
/*  410: 433 */       Iterator i = anchors.iterator();
/*  411: 434 */       while (i.hasNext())
/*  412:     */       {
/*  413: 435 */         HtmlAnchor a = (HtmlAnchor)i.next();
/*  414:     */         
/*  415: 437 */         String id = a.getId();
/*  416: 438 */         boolean favorite = false;
/*  417: 439 */         if (id != null) {
/*  418: 440 */           favorite = 
/*  419: 441 */             id.contains("ctl00_PreviewBoxPlaceholder_PreviewBox1_TabContainer1_TabPanelFavs_FavouriteStocksRepeater");
/*  420:     */         }
/*  421: 444 */         if ((a.getTextContent().contains(ticker)) && (!favorite)) {
/*  422:     */           try
/*  423:     */           {
/*  424: 446 */             this.stockTradingDetailsPage = ((HtmlPage)a.click());
/*  425:     */           }
/*  426:     */           catch (IOException e)
/*  427:     */           {
/*  428: 448 */             MainLogger.error(e);
/*  429:     */           }
/*  430:     */         }
/*  431:     */       }
/*  432:     */     }
/*  433:     */     else
/*  434:     */     {
/*  435: 455 */       MainLogger.error("Could not open stockTradingDetailsPage for ticker " + ticker);
/*  436: 456 */       return false;
/*  437:     */     }
/*  438: 459 */     return true;
/*  439:     */   }
/*  440:     */   
/*  441:     */   /**
/*  442:     */    * @deprecated
/*  443:     */    */
/*  444:     */   private void refreshTradingMonitorPage()
/*  445:     */     throws IOException
/*  446:     */   {
/*  447: 471 */     Date now = new Date();
/*  448: 472 */     long milisSinceLastRefresh = now.getTime() - this.tradingMonitorlastRefresh.getTime();
/*  449: 473 */     int secondsSinceLastReferesh = (int)(milisSinceLastRefresh / 1000L);
/*  450:     */     
/*  451:     */ 
/*  452: 476 */     int hour = now.getHours();
/*  453: 477 */     int min = now.getMinutes();
/*  454: 479 */     if (((hour == 9) && (min >= 59)) || ((hour == 10) && (min <= 5)))
/*  455:     */     {
/*  456: 481 */       MainLogger.info("Refreshing trading monitor page...");
/*  457: 482 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  458: 483 */       this.tradingMonitorlastRefresh = now;
/*  459: 484 */       return;
/*  460:     */     }
/*  461: 485 */     if ((hour == 10) && (min > 5) && (min <= 10) && (secondsSinceLastReferesh >= 15))
/*  462:     */     {
/*  463: 487 */       MainLogger.info("Refreshing trading monitor page...");
/*  464: 488 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  465: 489 */       this.tradingMonitorlastRefresh = now;
/*  466: 490 */       return;
/*  467:     */     }
/*  468: 491 */     if ((hour == 10) && (min > 10) && (min <= 20) && (secondsSinceLastReferesh >= 30))
/*  469:     */     {
/*  470: 493 */       MainLogger.info("Refreshing trading monitor page...");
/*  471: 494 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  472: 495 */       this.tradingMonitorlastRefresh = now;
/*  473: 496 */       return;
/*  474:     */     }
/*  475: 497 */     if ((hour == 10) && (min > 20) && (min <= 40) && (secondsSinceLastReferesh >= 60))
/*  476:     */     {
/*  477: 499 */       MainLogger.info("Refreshing trading monitor page...");
/*  478: 500 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  479: 501 */       this.tradingMonitorlastRefresh = now;
/*  480: 502 */       return;
/*  481:     */     }
/*  482: 503 */     if (secondsSinceLastReferesh >= 120)
/*  483:     */     {
/*  484: 505 */       MainLogger.info("Refreshing trading monitor page...");
/*  485: 506 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  486: 507 */       this.tradingMonitorlastRefresh = now;
/*  487: 508 */       return;
/*  488:     */     }
/*  489:     */   }
/*  490:     */   
/*  491:     */   /**
/*  492:     */    * @deprecated
/*  493:     */    */
/*  494:     */   private void refreshTradingMonitorPageOld()
/*  495:     */     throws IOException
/*  496:     */   {
/*  497: 520 */     Date now = new Date();
/*  498: 521 */     long milisSinceLastRefresh = now.getTime() - this.tradingMonitorlastRefresh.getTime();
/*  499: 522 */     int minutesSinceLastReferesh = (int)(milisSinceLastRefresh / 1000L) / 60;
/*  500:     */     
/*  501:     */ 
/*  502: 525 */     int hour = now.getHours();
/*  503: 526 */     int min = now.getMinutes();
/*  504: 528 */     if (((hour == 9) && (min >= 57)) || (
/*  505: 529 */       (hour == 10) && (min <= 30) && (minutesSinceLastReferesh >= 2)))
/*  506:     */     {
/*  507: 531 */       MainLogger.info("Refreshing trading monitor page...");
/*  508: 532 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  509: 533 */       this.tradingMonitorlastRefresh = now;
/*  510: 534 */       return;
/*  511:     */     }
/*  512: 535 */     if ((hour == 10) && (min > 30) && (min <= 59) && (minutesSinceLastReferesh >= 5))
/*  513:     */     {
/*  514: 537 */       MainLogger.info("Refreshing trading monitor page...");
/*  515: 538 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  516: 539 */       this.tradingMonitorlastRefresh = now;
/*  517: 540 */       return;
/*  518:     */     }
/*  519: 541 */     if ((hour == 11) && (minutesSinceLastReferesh >= 15))
/*  520:     */     {
/*  521: 543 */       MainLogger.info("Refreshing trading monitor page...");
/*  522: 544 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  523: 545 */       this.tradingMonitorlastRefresh = now;
/*  524: 546 */       return;
/*  525:     */     }
/*  526: 547 */     if (minutesSinceLastReferesh >= 30)
/*  527:     */     {
/*  528: 549 */       MainLogger.info("Refreshing trading monitor page...");
/*  529: 550 */       this.tradingMonitorPage = ((HtmlPage)this.tradingMonitorPage.refresh());
/*  530: 551 */       this.tradingMonitorlastRefresh = now;
/*  531: 552 */       return;
/*  532:     */     }
/*  533:     */   }
/*  534:     */   
/*  535:     */   public List<Order> readBidListFromWeb(String ticker)
/*  536:     */   {
/*  537: 559 */     List bidList = null;
/*  538: 561 */     if (openStockTradingDetailsPage(ticker)) {
/*  539:     */       try
/*  540:     */       {
/*  541: 563 */         HtmlDivision divKupnje = 
/*  542: 564 */           (HtmlDivision)this.currentPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel1");
/*  543: 565 */         HtmlTable tableKupnje = (HtmlTable)divKupnje.getHtmlElementsByTagName("table").get(0);
/*  544: 566 */         List rows = tableKupnje.getHtmlElementsByTagName("tr");
/*  545: 567 */         bidList = new ArrayList(5);
/*  546: 568 */         int bidCounter = 1;
/*  547: 569 */         Iterator ki = rows.iterator();
/*  548: 570 */         while (ki.hasNext())
/*  549:     */         {
/*  550: 571 */           HtmlTableRow tr = (HtmlTableRow)ki.next();
/*  551: 572 */           Order bidInfo = new Order();
/*  552: 573 */           bidInfo.ticker = ticker;
/*  553: 574 */           bidInfo.position = bidCounter;
/*  554: 575 */           bidInfo.type = OrderType.BUY;
/*  555:     */           
/*  556: 577 */           HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  557: 578 */           String cijenaString = cijenaTd.getTextContent();
/*  558: 579 */           NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/*  559: 580 */           bidInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  560:     */           
/*  561: 582 */           HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  562: 583 */           bidInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  563:     */           
/*  564: 585 */           bidList.add(bidInfo);
/*  565: 586 */           bidCounter++;
/*  566:     */         }
/*  567:     */       }
/*  568:     */       catch (ParseException e)
/*  569:     */       {
/*  570: 589 */         MainLogger.error(e);
/*  571:     */       }
/*  572:     */     }
/*  573: 593 */     return bidList;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public List<Order> readAskListFromWeb(String ticker)
/*  577:     */   {
/*  578: 598 */     List askList = null;
/*  579: 600 */     if (openStockTradingDetailsPage(ticker)) {
/*  580:     */       try
/*  581:     */       {
/*  582: 602 */         HtmlDivision divKupnje = 
/*  583: 603 */           (HtmlDivision)this.currentPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel3");
/*  584: 604 */         HtmlTable tableKupnje = (HtmlTable)divKupnje.getHtmlElementsByTagName("table").get(0);
/*  585: 605 */         List rows = tableKupnje.getHtmlElementsByTagName("tr");
/*  586: 606 */         askList = new ArrayList(5);
/*  587: 607 */         int askCounter = 1;
/*  588: 608 */         Iterator ki = rows.iterator();
/*  589: 609 */         while (ki.hasNext())
/*  590:     */         {
/*  591: 610 */           HtmlTableRow tr = (HtmlTableRow)ki.next();
/*  592: 611 */           Order askInfo = new Order();
/*  593: 612 */           askInfo.ticker = ticker;
/*  594: 613 */           askInfo.position = askCounter;
/*  595: 614 */           askInfo.type = OrderType.SELL;
/*  596:     */           
/*  597: 616 */           HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  598: 617 */           askInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  599:     */           
/*  600: 619 */           HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  601: 620 */           String cijenaString = cijenaTd.getTextContent();
/*  602: 621 */           NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/*  603: 622 */           askInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  604:     */           
/*  605: 624 */           askList.add(askInfo);
/*  606: 625 */           askCounter++;
/*  607:     */         }
/*  608:     */       }
/*  609:     */       catch (ParseException e)
/*  610:     */       {
/*  611: 628 */         MainLogger.error(e);
/*  612:     */       }
/*  613:     */     }
/*  614: 632 */     return askList;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public List<Transaction> readTransactionListFromWeb(String ticker)
/*  618:     */   {
/*  619: 637 */     List transactionList = null;
/*  620: 638 */     if (openStockTradingDetailsPage(ticker)) {
/*  621:     */       try
/*  622:     */       {
/*  623: 640 */         HtmlDivision divTransackcije = 
/*  624: 641 */           (HtmlDivision)this.currentPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel2");
/*  625: 642 */         HtmlTable tableTransackcije = (HtmlTable)divTransackcije.getHtmlElementsByTagName("table").get(0);
/*  626: 643 */         List rows = tableTransackcije.getHtmlElementsByTagName("tr");
/*  627: 644 */         transactionList = new ArrayList(5);
/*  628: 645 */         Iterator ki = rows.iterator();
/*  629: 646 */         while (ki.hasNext())
/*  630:     */         {
/*  631: 647 */           HtmlTableRow tr = (HtmlTableRow)ki.next();
/*  632: 648 */           NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/*  633: 649 */           Transaction tInfo = new Transaction();
/*  634: 650 */           tInfo.ticker = ticker;
/*  635:     */           
/*  636: 652 */           HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  637: 653 */           String cijenaString = cijenaTd.getTextContent();
/*  638: 654 */           tInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  639:     */           
/*  640: 656 */           HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  641: 657 */           tInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  642:     */           
/*  643: 659 */           HtmlTableDataCell iznosTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(2);
/*  644: 660 */           String iznosString = iznosTd.getTextContent();
/*  645: 661 */           tInfo.value = nfGerman.parse(iznosString).floatValue();
/*  646:     */           
/*  647: 663 */           HtmlTableDataCell dateTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(3);
/*  648: 664 */           String dateString = dateTd.getTextContent();
/*  649: 665 */           Format formatter = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
/*  650: 666 */           tInfo.time = ((Date)formatter.parseObject(dateString));
/*  651:     */           
/*  652: 668 */           transactionList.add(tInfo);
/*  653:     */         }
/*  654:     */       }
/*  655:     */       catch (ParseException e)
/*  656:     */       {
/*  657: 671 */         MainLogger.error(e);
/*  658:     */       }
/*  659:     */     }
/*  660: 674 */     return transactionList;
/*  661:     */   }
/*  662:     */   
/*  663:     */   public StockMarketData readMarketDataFromWeb(String ticker)
/*  664:     */   {
/*  665: 680 */     StockMarketData marketData = this.dataCollector.getStockMarketData(ticker);
/*  666: 682 */     if (marketData == null)
/*  667:     */     {
/*  668: 683 */       MainLogger.debug("1");
/*  669:     */       
/*  670:     */ 
/*  671:     */ 
/*  672:     */ 
/*  673:     */ 
/*  674: 689 */       marketData = new StockMarketData(this.dataCollector, ticker);
/*  675:     */     }
/*  676:     */     else
/*  677:     */     {
/*  678: 694 */       marketData.clearLists();
/*  679:     */     }
/*  680: 697 */     NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/*  681: 698 */     if ((openStockTradingDetailsPage(ticker)) && (this.stockTradingDetailsPage != null))
/*  682:     */     {
/*  683: 699 */       MainLogger.debug("2");
/*  684:     */       try
/*  685:     */       {
/*  686: 702 */         HtmlDivision divKupnje = 
/*  687: 703 */           (HtmlDivision)this.stockTradingDetailsPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel1");
/*  688:     */         
/*  689: 705 */         HtmlTable tableKupnje = (HtmlTable)divKupnje.getHtmlElementsByTagName("table").get(0);
/*  690: 706 */         List bidRows = tableKupnje.getHtmlElementsByTagName("tr");
/*  691: 707 */         int bidCounter = 1;
/*  692: 708 */         Iterator ki = bidRows.iterator();
/*  693: 709 */         while (ki.hasNext())
/*  694:     */         {
/*  695: 710 */           HtmlTableRow tr = (HtmlTableRow)ki.next();
/*  696: 711 */           Order bidInfo = new Order();
/*  697: 712 */           bidInfo.ticker = ticker;
/*  698: 713 */           bidInfo.position = bidCounter;
/*  699: 714 */           bidInfo.type = OrderType.BUY;
/*  700:     */           
/*  701: 716 */           HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  702: 717 */           String cijenaString = cijenaTd.getTextContent();
/*  703: 718 */           bidInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  704:     */           
/*  705: 720 */           HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  706: 721 */           bidInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  707:     */           
/*  708: 723 */           marketData.bidList.add(bidInfo);
/*  709: 724 */           bidCounter++;
/*  710:     */         }
/*  711: 728 */         HtmlDivision divProdaje = 
/*  712: 729 */           (HtmlDivision)this.stockTradingDetailsPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel3");
/*  713: 730 */         HtmlTable tableProdaje = (HtmlTable)divProdaje.getHtmlElementsByTagName("table").get(0);
/*  714: 731 */         List rows = tableProdaje.getHtmlElementsByTagName("tr");
/*  715: 732 */         int askCounter = 1;
/*  716: 733 */         Iterator pi = rows.iterator();
/*  717: 734 */         while (pi.hasNext())
/*  718:     */         {
/*  719: 735 */           HtmlTableRow tr = (HtmlTableRow)pi.next();
/*  720: 736 */           Order askInfo = new Order();
/*  721: 737 */           askInfo.ticker = ticker;
/*  722: 738 */           askInfo.position = askCounter;
/*  723: 739 */           askInfo.type = OrderType.SELL;
/*  724:     */           
/*  725: 741 */           HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  726: 742 */           askInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  727:     */           
/*  728: 744 */           HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  729: 745 */           String cijenaString = cijenaTd.getTextContent();
/*  730: 746 */           askInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  731:     */           
/*  732: 748 */           marketData.askList.add(askInfo);
/*  733: 749 */           askCounter++;
/*  734:     */         }
/*  735: 753 */         HtmlDivision divTransackcije = 
/*  736: 754 */           (HtmlDivision)this.stockTradingDetailsPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel2");
/*  737: 755 */         HtmlTable tableTransackcije = (HtmlTable)divTransackcije.getHtmlElementsByTagName("table").get(0);
/*  738: 756 */         List trows = tableTransackcije.getHtmlElementsByTagName("tr");
/*  739: 757 */         Iterator ti = trows.iterator();
/*  740: 758 */         while (ti.hasNext())
/*  741:     */         {
/*  742: 759 */           HtmlTableRow tr = (HtmlTableRow)ti.next();
/*  743:     */           
/*  744: 761 */           Transaction tInfo = new Transaction();
/*  745: 762 */           tInfo.ticker = ticker;
/*  746:     */           
/*  747: 764 */           HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  748: 765 */           String cijenaString = cijenaTd.getTextContent();
/*  749: 766 */           tInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  750:     */           
/*  751: 768 */           HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  752: 769 */           tInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  753:     */           
/*  754: 771 */           HtmlTableDataCell iznosTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(2);
/*  755: 772 */           String iznosString = iznosTd.getTextContent();
/*  756: 773 */           tInfo.value = nfGerman.parse(iznosString).floatValue();
/*  757:     */           
/*  758: 775 */           HtmlTableDataCell dateTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(3);
/*  759: 776 */           String dateString = dateTd.getTextContent();
/*  760: 777 */           Format formatter = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
/*  761: 778 */           tInfo.time = ((Date)formatter.parseObject(dateString));
/*  762:     */           
/*  763: 780 */           marketData.transactionList.add(tInfo);
/*  764:     */         }
/*  765:     */       }
/*  766:     */       catch (ParseException e)
/*  767:     */       {
/*  768: 784 */         MainLogger.error(e);
/*  769:     */       }
/*  770: 787 */       marketData.time = new Date();
/*  771: 788 */       marketData.calculateImplicitData();
/*  772: 789 */       marketData.appendWithImplicitDataToCsvFile();
/*  773: 790 */       this.stockTradingDetailsPage = null;
/*  774:     */     }
/*  775:     */     else
/*  776:     */     {
/*  777: 793 */       MainLogger.info("Could not open stock trading details page for ticker " + ticker + "\n");
/*  778:     */     }
/*  779: 797 */     onMarketDataChange(marketData);
/*  780: 798 */     return marketData;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public synchronized void refreshMarketDataFromWeb(List<String> tickerList)
/*  784:     */   {
/*  785: 806 */     if ((!tickerList.isEmpty()) && (isMarketOpen()))
/*  786:     */     {
/*  787: 807 */       MainLogger.info("Getting market data from FIMA e-trade. Iteration = " + iteration);
/*  788: 808 */       Iterator n = tickerList.iterator();
/*  789: 809 */       while (n.hasNext())
/*  790:     */       {
/*  791: 810 */         String ticker = (String)n.next();
/*  792:     */         
/*  793:     */ 
/*  794: 813 */         StockMarketData marketData = this.dataCollector.getStockMarketData(ticker);
/*  795: 814 */         if (marketData == null)
/*  796:     */         {
/*  797: 820 */           marketData = new StockMarketData(this.dataCollector, ticker);
/*  798: 821 */           this.dataCollector.addStockMarketData(marketData);
/*  799:     */         }
/*  800:     */         else
/*  801:     */         {
/*  802: 827 */           marketData.clearLists();
/*  803:     */         }
/*  804: 830 */         NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/*  805: 831 */         boolean ready = openStockTradingDetailsPage(ticker);
/*  806: 833 */         if ((ready) && (this.stockTradingDetailsPage != null)) {
/*  807:     */           try
/*  808:     */           {
/*  809: 837 */             HtmlDivision divKupnje = 
/*  810: 838 */               (HtmlDivision)this.stockTradingDetailsPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel1");
/*  811: 839 */             HtmlTable tableKupnje = (HtmlTable)divKupnje.getHtmlElementsByTagName("table").get(0);
/*  812: 840 */             List bidRows = tableKupnje.getHtmlElementsByTagName("tr");
/*  813: 841 */             int bidCounter = 1;
/*  814: 842 */             Iterator ki = bidRows.iterator();
/*  815: 843 */             while (ki.hasNext())
/*  816:     */             {
/*  817: 844 */               HtmlTableRow tr = (HtmlTableRow)ki.next();
/*  818: 845 */               Order bidInfo = new Order();
/*  819: 846 */               bidInfo.ticker = ticker;
/*  820: 847 */               bidInfo.position = bidCounter;
/*  821: 848 */               bidInfo.type = OrderType.BUY;
/*  822:     */               
/*  823: 850 */               HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  824: 851 */               String cijenaString = cijenaTd.getTextContent();
/*  825:     */               try
/*  826:     */               {
/*  827: 853 */                 bidInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  828:     */               }
/*  829:     */               catch (ParseException e)
/*  830:     */               {
/*  831: 855 */                 MainLogger.error("Error while parsing cijenaString...1");
/*  832: 856 */                 MainLogger.error(e.toString());
/*  833: 857 */                 e.printStackTrace();
/*  834:     */               }
/*  835: 860 */               HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  836: 861 */               bidInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  837:     */               
/*  838: 863 */               marketData.bidList.add(bidInfo);
/*  839: 864 */               bidCounter++;
/*  840:     */             }
/*  841: 868 */             HtmlDivision divProdaje = 
/*  842: 869 */               (HtmlDivision)this.stockTradingDetailsPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel3");
/*  843: 870 */             HtmlTable tableProdaje = (HtmlTable)divProdaje.getHtmlElementsByTagName("table").get(0);
/*  844: 871 */             List rows = tableProdaje.getHtmlElementsByTagName("tr");
/*  845: 872 */             int askCounter = 1;
/*  846: 873 */             Iterator pi = rows.iterator();
/*  847: 874 */             while (pi.hasNext())
/*  848:     */             {
/*  849: 875 */               HtmlTableRow tr = (HtmlTableRow)pi.next();
/*  850: 876 */               Order askInfo = new Order();
/*  851: 877 */               askInfo.ticker = ticker;
/*  852: 878 */               askInfo.position = askCounter;
/*  853: 879 */               askInfo.type = OrderType.SELL;
/*  854:     */               
/*  855: 881 */               HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  856: 882 */               askInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  857:     */               
/*  858: 884 */               HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  859: 885 */               String cijenaString = cijenaTd.getTextContent();
/*  860:     */               try
/*  861:     */               {
/*  862: 887 */                 askInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  863:     */               }
/*  864:     */               catch (ParseException e)
/*  865:     */               {
/*  866: 889 */                 MainLogger.error("Error while parsing cijenaString...2");
/*  867: 890 */                 MainLogger.error(e.toString());
/*  868: 891 */                 e.printStackTrace();
/*  869:     */               }
/*  870: 894 */               marketData.askList.add(askInfo);
/*  871: 895 */               askCounter++;
/*  872:     */             }
/*  873: 899 */             HtmlDivision divTransackcije = 
/*  874: 900 */               (HtmlDivision)this.stockTradingDetailsPage.getHtmlElementById("ctl00_ContentPlaceHolder_StockAndTradeInfo1_Panel2");
/*  875: 901 */             HtmlTable tableTransackcije = 
/*  876: 902 */               (HtmlTable)divTransackcije.getHtmlElementsByTagName("table").get(0);
/*  877: 903 */             List trows = tableTransackcije.getHtmlElementsByTagName("tr");
/*  878: 904 */             Iterator ti = trows.iterator();
/*  879: 905 */             while (ti.hasNext())
/*  880:     */             {
/*  881: 906 */               HtmlTableRow tr = (HtmlTableRow)ti.next();
/*  882:     */               
/*  883: 908 */               Transaction tInfo = new Transaction();
/*  884: 909 */               tInfo.ticker = ticker;
/*  885:     */               
/*  886: 911 */               HtmlTableDataCell cijenaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(0);
/*  887: 912 */               String cijenaString = cijenaTd.getTextContent();
/*  888:     */               try
/*  889:     */               {
/*  890: 914 */                 tInfo.price = nfGerman.parse(cijenaString).floatValue();
/*  891:     */               }
/*  892:     */               catch (ParseException e)
/*  893:     */               {
/*  894: 916 */                 MainLogger.error("Error while parsing cijenaString...3");
/*  895: 917 */                 MainLogger.error(e.toString());
/*  896: 918 */                 e.printStackTrace();
/*  897:     */               }
/*  898: 921 */               HtmlTableDataCell kolicinaTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(1);
/*  899: 922 */               tInfo.amount = Integer.parseInt(kolicinaTd.getTextContent());
/*  900:     */               
/*  901: 924 */               HtmlTableDataCell iznosTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(2);
/*  902: 925 */               String iznosString = iznosTd.getTextContent();
/*  903:     */               try
/*  904:     */               {
/*  905: 927 */                 tInfo.value = nfGerman.parse(iznosString).floatValue();
/*  906:     */               }
/*  907:     */               catch (ParseException e)
/*  908:     */               {
/*  909: 929 */                 MainLogger.error("Error while parsing iznosString...4");
/*  910: 930 */                 MainLogger.error(e.toString());
/*  911: 931 */                 e.printStackTrace();
/*  912:     */               }
/*  913: 934 */               HtmlTableDataCell dateTd = (HtmlTableDataCell)tr.getHtmlElementsByTagName("td").get(3);
/*  914: 935 */               String dateString = dateTd.getTextContent();
/*  915: 936 */               Format formatter = new SimpleDateFormat("dd.M.yyyy. HH:mm:ss");
/*  916:     */               try
/*  917:     */               {
/*  918: 939 */                 tInfo.time = ((Date)formatter.parseObject(dateString));
/*  919:     */               }
/*  920:     */               catch (ParseException e)
/*  921:     */               {
/*  922: 941 */                 MainLogger.error("Error while parsing dateString...5");
/*  923: 942 */                 MainLogger.error(e.toString());
/*  924: 943 */                 e.printStackTrace();
/*  925:     */               }
/*  926: 946 */               marketData.transactionList.add(tInfo);
/*  927:     */             }
/*  928: 949 */             marketData.time = new Date();
/*  929: 950 */             marketData.calculateImplicitData();
/*  930: 951 */             marketData.appendWithImplicitDataToCsvFile();
/*  931:     */             
/*  932: 953 */             onMarketDataChange(marketData);
/*  933:     */             
/*  934:     */ 
/*  935:     */ 
/*  936: 957 */             this.stockTradingDetailsPage = null;
/*  937:     */           }
/*  938:     */           catch (Exception e)
/*  939:     */           {
/*  940: 960 */             MainLogger.error("Unknown exception thrown!");
/*  941: 961 */             MainLogger.error(e.toString());
/*  942: 962 */             e.printStackTrace();
/*  943:     */           }
/*  944:     */         } else {
/*  945: 966 */           MainLogger.info("Could not open stock trading details page for ticker " + ticker + "\n");
/*  946:     */         }
/*  947:     */       }
/*  948: 970 */       iteration += 1;
/*  949:     */     }
/*  950: 973 */     System.gc();
/*  951:     */   }
/*  952:     */   
/*  953:     */   public float readCashInPendingSellOrders()
/*  954:     */   {
/*  955: 980 */     MainLogger.error("NOT IMPLEMENTED!");
/*  956: 981 */     return 0.0F;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public QueryStatus cancelBuyOrder(String ticker, float price, int amount, Date expiryDate)
/*  960:     */   {
/*  961: 986 */     MainLogger.error("NOT IMPLEMENTED!");
/*  962: 987 */     return null;
/*  963:     */   }
/*  964:     */   
/*  965:     */   public QueryStatus cancelSellOrder(String ticker, float price, int amount, Date expiryDate)
/*  966:     */   {
/*  967: 992 */     MainLogger.error("NOT IMPLEMENTED!");
/*  968: 993 */     return null;
/*  969:     */   }
/*  970:     */   
/*  971:     */   public QueryStatus setBuyOrder(String ticker, float price, int amount, Date expiryDate)
/*  972:     */   {
/*  973: 997 */     MainLogger.error("NOT IMPLEMENTED!");
/*  974: 998 */     return QueryStatus.UNKNOWN;
/*  975:     */   }
/*  976:     */   
/*  977:     */   public QueryStatus setSellOrder(String ticker, float price, int amount, Date expiryDate)
/*  978:     */   {
/*  979:1003 */     MainLogger.error("NOT IMPLEMENTED!");
/*  980:1004 */     return QueryStatus.UNKNOWN;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public PortfolioData readPortfolioData()
/*  984:     */   {
/*  985:1010 */     MainLogger.error("NOT IMPLEMENTED!");
/*  986:1011 */     return null;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public List<Order> readPendingOrdersInfo()
/*  990:     */   {
/*  991:1016 */     MainLogger.error("NOT IMPLEMENTED!");
/*  992:1017 */     return null;
/*  993:     */   }
/*  994:     */   
/*  995:     */   public float readAvailableCash()
/*  996:     */   {
/*  997:1022 */     MainLogger.error("NOT IMPLEMENTED!");
/*  998:1023 */     return 0.0F;
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public float readCashInPendingBuyOrders()
/* 1002:     */   {
/* 1003:1028 */     MainLogger.error("NOT IMPLEMENTED!");
/* 1004:1029 */     return 0.0F;
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public List<Order> readPendingOrdersInfoForTicker(String ticker)
/* 1008:     */   {
/* 1009:1034 */     MainLogger.error("NOT IMPLEMENTED!");
/* 1010:1035 */     return null;
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public List<Transaction> readCompletedOrdersInfo()
/* 1014:     */   {
/* 1015:1040 */     MainLogger.error("NOT IMPLEMENTED!");
/* 1016:1041 */     return null;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public List<Transaction> readCompletedOrdersInfoForTicker(String ticker)
/* 1020:     */   {
/* 1021:1046 */     MainLogger.error("NOT IMPLEMENTED!");
/* 1022:1047 */     return null;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   private boolean isMarketOpen()
/* 1026:     */   {
/* 1027:1053 */     Date now = new Date();
/* 1028:1054 */     if (((now.getHours() == 16) && (now.getMinutes() >= 5)) || 
/* 1029:1055 */       (now.getHours() > 16))
/* 1030:     */     {
/* 1031:1056 */       MainLogger.info("ZSE market closed. Not colecting data. Last iteration index = " + iteration);
/* 1032:     */       try
/* 1033:     */       {
/* 1034:1058 */         if (this.logged) {
/* 1035:1059 */           this.mainPage = ((HtmlPage)this.mainPage.refresh());
/* 1036:     */         }
/* 1037:1062 */         return false;
/* 1038:     */       }
/* 1039:     */       catch (IOException e)
/* 1040:     */       {
/* 1041:1064 */         MainLogger.error(e);
/* 1042:     */       }
/* 1043:     */     }
/* 1044:1066 */     else if (((now.getHours() == 9) && (now.getMinutes() < 55)) || 
/* 1045:1067 */       (now.getHours() < 9))
/* 1046:     */     {
/* 1047:1068 */       MainLogger.info("ZSE market not yet opened. Not collecting data.");
/* 1048:1069 */       iteration = 0;
/* 1049:     */       try
/* 1050:     */       {
/* 1051:1071 */         if (this.logged) {
/* 1052:1072 */           this.mainPage = ((HtmlPage)this.mainPage.refresh());
/* 1053:     */         }
/* 1054:1074 */         return false;
/* 1055:     */       }
/* 1056:     */       catch (IOException e)
/* 1057:     */       {
/* 1058:1076 */         MainLogger.error(e);
/* 1059:     */       }
/* 1060:     */     }
/* 1061:1079 */     return true;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public QueryStatus cancelBuyOrder(String ticker)
/* 1065:     */   {
/* 1066:1085 */     return null;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public QueryStatus cancelSellOrder(String ticker)
/* 1070:     */   {
/* 1071:1091 */     return null;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public void setDataCollector(DataCollector dc)
/* 1075:     */   {
/* 1076:1096 */     if ((this.dataCollector != null) && (!this.dataCollector.equals(dc))) {
/* 1077:1097 */       MainLogger.error("ERROR: FimaEtrade instance already uses DIFFERENT data collector!");
/* 1078:1098 */     } else if ((this.dataCollector != null) && (this.dataCollector.equals(dc))) {
/* 1079:1099 */       MainLogger.info("FimaEtrade instance already has SAME data collector!");
/* 1080:1100 */     } else if (this.dataCollector == null) {
/* 1081:1101 */       MainLogger.error("ERROR: FimaEtrade does not have data collector! And it should have!");
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public void onMarketDataChange(StockMarketData marketData)
/* 1086:     */   {
/* 1087:1109 */     marketData.setDataChanged();
/* 1088:1110 */     marketData.notifyObservers();
/* 1089:     */   }
/* 1090:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.FimaEtrade
 * JD-Core Version:    0.7.0.1
 */