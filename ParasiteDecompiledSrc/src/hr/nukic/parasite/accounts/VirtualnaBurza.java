/*    1:     */ package hr.nukic.parasite.accounts;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*    4:     */ import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
/*    5:     */ import com.gargoylesoftware.htmlunit.WebClient;
/*    6:     */ import com.gargoylesoftware.htmlunit.html.DomNodeList;
/*    7:     */ import com.gargoylesoftware.htmlunit.html.FrameWindow;
/*    8:     */ import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
/*    9:     */ import com.gargoylesoftware.htmlunit.html.HtmlDivision;
/*   10:     */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   11:     */ import com.gargoylesoftware.htmlunit.html.HtmlForm;
/*   12:     */ import com.gargoylesoftware.htmlunit.html.HtmlImage;
/*   13:     */ import com.gargoylesoftware.htmlunit.html.HtmlImageInput;
/*   14:     */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   15:     */ import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
/*   16:     */ import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
/*   17:     */ import com.gargoylesoftware.htmlunit.html.HtmlSpan;
/*   18:     */ import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
/*   19:     */ import com.gargoylesoftware.htmlunit.html.HtmlTable;
/*   20:     */ import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
/*   21:     */ import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
/*   22:     */ import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
/*   23:     */ import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
/*   24:     */ import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;
/*   25:     */ import hr.nukic.parasite.accounts.templates.HistoryDataProvider;
/*   26:     */ import hr.nukic.parasite.accounts.templates.TradingAccount;
/*   27:     */ import hr.nukic.parasite.core.DataCollector;
/*   28:     */ import hr.nukic.parasite.core.Order;
/*   29:     */ import hr.nukic.parasite.core.OrderType;
/*   30:     */ import hr.nukic.parasite.core.ParasiteManager;
/*   31:     */ import hr.nukic.parasite.core.PortfolioData;
/*   32:     */ import hr.nukic.parasite.core.QueryStatus;
/*   33:     */ import hr.nukic.parasite.core.StockDaySummary;
/*   34:     */ import hr.nukic.parasite.core.StockHistoryData;
/*   35:     */ import hr.nukic.parasite.core.StockPortfolioData;
/*   36:     */ import hr.nukic.parasite.core.Transaction;
/*   37:     */ import java.io.IOException;
/*   38:     */ import java.net.MalformedURLException;
/*   39:     */ import java.text.Format;
/*   40:     */ import java.text.NumberFormat;
/*   41:     */ import java.text.ParseException;
/*   42:     */ import java.text.SimpleDateFormat;
/*   43:     */ import java.util.ArrayList;
/*   44:     */ import java.util.Date;
/*   45:     */ import java.util.Iterator;
/*   46:     */ import java.util.List;
/*   47:     */ import java.util.Locale;
/*   48:     */ import nukic.parasite.utils.MainLogger;
/*   49:     */ import nukic.parasite.utils.ParasiteUtils;
/*   50:     */ 
/*   51:     */ public class VirtualnaBurza
/*   52:     */   extends TradingAccount
/*   53:     */   implements HistoryDataProvider
/*   54:     */ {
/*   55:  62 */   private static List<VBAccount> accounts = new ArrayList(2);
/*   56:  64 */   private static VirtualnaBurza instance = null;
/*   57:     */   
/*   58:     */   static
/*   59:     */   {
/*   60:  67 */     accounts.add(new VBAccount("ivanrilo", "ivanrilo", "ivana.rilota@gmail.com", "ivana.rilota", 
/*   61:  68 */       "ivanrilo"));
/*   62:  69 */     accounts.add(new VBAccount("markmaru2", "markmaru2", "marko.marun@gmail.com", "marko.marun", 
/*   63:  70 */       "markmaru"));
/*   64:     */   }
/*   65:     */   
/*   66:  71 */   private static VBAccount defaultAccount = (VBAccount)accounts.get(1);
/*   67:     */   
/*   68:     */   public VirtualnaBurza(DataCollector dc)
/*   69:     */   {
/*   70:  77 */     super(dc, 1.0F, 0.0F);
/*   71:  78 */     this.url = "http://vb.zse.hr/";
/*   72:  79 */     if (ParasiteManager.getInstance().useProxy) {
/*   73:  80 */       this.browser = new WebClient(BrowserVersion.FIREFOX_3_6, ParasiteManager.getInstance().proxy, 8080);
/*   74:     */     } else {
/*   75:  82 */       this.browser = new WebClient(BrowserVersion.FIREFOX_3_6);
/*   76:     */     }
/*   77:  85 */     this.browser.setJavaScriptEnabled(true);
/*   78:  86 */     this.browser.setThrowExceptionOnScriptError(false);
/*   79:  87 */     this.browser.setThrowExceptionOnFailingStatusCode(false);
/*   80:  88 */     this.userName = defaultAccount.username;
/*   81:  89 */     this.password = defaultAccount.password;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public VirtualnaBurza()
/*   85:     */   {
/*   86:  93 */     super(1.0F, 0.0F);
/*   87:  94 */     this.url = "http://vb.zse.hr/";
/*   88:  95 */     if (ParasiteManager.getInstance().useProxy) {
/*   89:  96 */       this.browser = new WebClient(BrowserVersion.FIREFOX_3_6, ParasiteManager.getInstance().proxy, 8080);
/*   90:     */     } else {
/*   91:  98 */       this.browser = new WebClient(BrowserVersion.FIREFOX_3_6);
/*   92:     */     }
/*   93: 101 */     this.browser.setJavaScriptEnabled(true);
/*   94: 102 */     this.browser.setThrowExceptionOnScriptError(false);
/*   95: 103 */     this.browser.setThrowExceptionOnFailingStatusCode(false);
/*   96: 104 */     this.userName = defaultAccount.username;
/*   97: 105 */     this.password = defaultAccount.password;
/*   98:     */   }
/*   99:     */   
/*  100: 112 */   private boolean logged = false;
/*  101: 113 */   private HtmlPage loginPage = null;
/*  102: 114 */   private Date lastLogin = null;
/*  103: 115 */   private HtmlPage mainPage = null;
/*  104: 116 */   private HtmlPage popisDionicaPage = null;
/*  105: 117 */   private HtmlPage zseStockDetailsPage = null;
/*  106: 118 */   private HtmlPage ordersPage = null;
/*  107:     */   public static final String URL = "http://vb.zse.hr/";
/*  108:     */   public static final float FEE_PERCENTAGE = 1.0F;
/*  109:     */   public static final float MIN_FEE = 0.0F;
/*  110:     */   public WebClient browser;
/*  111:     */   public String url;
/*  112:     */   private String userName;
/*  113:     */   private String password;
/*  114:     */   
/*  115:     */   private boolean openOrdersPage()
/*  116:     */   {
/*  117: 123 */     if (this.logged)
/*  118:     */     {
/*  119: 124 */       HtmlAnchor myOrderPageLink = this.mainPage.getAnchorByHref("MojiNalozi.aspx");
/*  120:     */       try
/*  121:     */       {
/*  122: 126 */         this.ordersPage = ((HtmlPage)myOrderPageLink.click());
/*  123:     */       }
/*  124:     */       catch (IOException e)
/*  125:     */       {
/*  126: 128 */         MainLogger.error(e);
/*  127:     */       }
/*  128: 130 */       return true;
/*  129:     */     }
/*  130: 132 */     MainLogger.error("Cannot open orders page because NOT LOGGED!");
/*  131: 133 */     return false;
/*  132:     */   }
/*  133:     */   
/*  134:     */   public boolean login()
/*  135:     */   {
/*  136: 141 */     MainLogger.info("Logging to VIRTUALNA BURZA as " + this.userName);
/*  137:     */     try
/*  138:     */     {
/*  139: 143 */       this.loginPage = ((HtmlPage)this.browser.getPage("http://vb.zse.hr/"));
/*  140:     */     }
/*  141:     */     catch (FailingHttpStatusCodeException e)
/*  142:     */     {
/*  143: 145 */       MainLogger.error(e);
/*  144:     */     }
/*  145:     */     catch (MalformedURLException e)
/*  146:     */     {
/*  147: 147 */       MainLogger.error(e);
/*  148:     */     }
/*  149:     */     catch (IOException e)
/*  150:     */     {
/*  151: 149 */       MainLogger.error(e);
/*  152:     */     }
/*  153: 152 */     HtmlForm form = this.loginPage.getFormByName("aspnetForm");
/*  154: 153 */     HtmlImageInput button = (HtmlImageInput)form.getInputByName("ctl00$ContentPlaceHolder1$imbPrijava");
/*  155: 154 */     HtmlTextInput userNameTextField = (HtmlTextInput)form.getInputByName("ctl00$ContentPlaceHolder1$tbKorisnickoIme");
/*  156: 155 */     HtmlPasswordInput passwordTextField = (HtmlPasswordInput)form.getInputByName("ctl00$ContentPlaceHolder1$tbPassword");
/*  157:     */     
/*  158: 157 */     userNameTextField.setValueAttribute(this.userName);
/*  159: 158 */     passwordTextField.setValueAttribute(this.password);
/*  160:     */     try
/*  161:     */     {
/*  162: 161 */       this.mainPage = ((HtmlPage)button.click());
/*  163:     */     }
/*  164:     */     catch (IOException e)
/*  165:     */     {
/*  166: 163 */       MainLogger.error(e);
/*  167:     */     }
/*  168: 167 */     if (this.mainPage.asXml().contains(
/*  169: 168 */       "<form name=\"aspnetForm\" method=\"post\" action=\"MojPortfelj.aspx\" id=\"aspnetForm\">"))
/*  170:     */     {
/*  171: 169 */       MainLogger.info("Login succesfull!");
/*  172: 170 */       this.logged = true;
/*  173: 171 */       this.lastLogin = new Date();
/*  174: 172 */       return true;
/*  175:     */     }
/*  176: 174 */     MainLogger.error("Login failed!");
/*  177: 175 */     this.logged = false;
/*  178: 176 */     return false;
/*  179:     */   }
/*  180:     */   
/*  181:     */   public boolean logout()
/*  182:     */   {
/*  183: 181 */     MainLogger.info("Logging out from VIRTUALNA BURZA...");
/*  184: 182 */     if ((this.logged) && (this.mainPage != null))
/*  185:     */     {
/*  186: 183 */       HtmlAnchor odjavi = (HtmlAnchor)this.mainPage.getElementById("ctl00_ctlHeader_imbOdjava");
/*  187:     */       try
/*  188:     */       {
/*  189: 185 */         odjavi.click();
/*  190:     */       }
/*  191:     */       catch (IOException e)
/*  192:     */       {
/*  193: 187 */         MainLogger.error(e);
/*  194:     */       }
/*  195: 189 */       this.browser.waitForBackgroundJavaScript(1000L);
/*  196: 190 */       MainLogger.info("Logged out!");
/*  197: 191 */       this.logged = false;
/*  198: 192 */       return true;
/*  199:     */     }
/*  200: 194 */     return false;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public QueryStatus setBuyOrder(String ticker, float price, int amount, Date expiryDate)
/*  204:     */   {
/*  205: 198 */     if (!this.logged) {
/*  206: 199 */       login();
/*  207:     */     }
/*  208: 202 */     openOrdersPage();
/*  209: 203 */     MainLogger.debug("****************************************");
/*  210: 204 */     MainLogger.debug("Setting buy order...");
/*  211: 205 */     MainLogger.debug("TICKER: " + ticker);
/*  212: 206 */     MainLogger.debug("PRICE: " + price);
/*  213: 207 */     MainLogger.debug("AMOUNT: " + amount);
/*  214: 208 */     if (expiryDate != null)
/*  215:     */     {
/*  216: 209 */       Format formatter = new SimpleDateFormat("MM/dd/yy");
/*  217: 210 */       String s = formatter.format(expiryDate);
/*  218: 211 */       MainLogger.debug("EXPIRY DATE: " + s);
/*  219:     */     }
/*  220: 213 */     MainLogger.debug("****************************************");
/*  221:     */     
/*  222:     */ 
/*  223: 216 */     HtmlDivision div4 = (HtmlDivision)this.ordersPage.getElementById("ctl00_ContentPlaceHolder1_radUpdatePanel");
/*  224: 217 */     HtmlImage buyImg = (HtmlImage)div4.getHtmlElementsByTagName("img").get(0);
/*  225: 219 */     if (!buyImg.getAttribute("title").equals("Kupi"))
/*  226:     */     {
/*  227: 220 */       MainLogger.error("Could not find prodaj image!");
/*  228: 221 */       return QueryStatus.PAGE_PARSING_FAILED;
/*  229:     */     }
/*  230: 224 */     HtmlPage buyPage = null;
/*  231:     */     try
/*  232:     */     {
/*  233: 227 */       buyPage = (HtmlPage)buyImg.click();
/*  234: 228 */       this.browser.waitForBackgroundJavaScript(1000L);
/*  235:     */     }
/*  236:     */     catch (IOException e)
/*  237:     */     {
/*  238: 230 */       MainLogger.error(e);
/*  239:     */     }
/*  240: 233 */     FrameWindow buyFrame = (FrameWindow)buyPage.getFrames().get(0);
/*  241: 234 */     HtmlPage enclosedPage = (HtmlPage)buyFrame.getEnclosedPage();
/*  242:     */     
/*  243:     */ 
/*  244: 237 */     String stockPriceString = String.format("%.2f", new Object[] { Float.valueOf(price) });
/*  245: 238 */     String amountString = Integer.toString(amount);
/*  246:     */     
/*  247:     */ 
/*  248: 241 */     HtmlTextInput kolicinaTextField = (HtmlTextInput)enclosedPage.getElementById("tbKolicina");
/*  249: 242 */     kolicinaTextField.setText(amountString);
/*  250:     */     
/*  251:     */ 
/*  252: 245 */     Date now = new Date();
/*  253: 246 */     int nowMonth = now.getMonth();
/*  254: 247 */     HtmlPage afterDateSet = null;
/*  255:     */     
/*  256: 249 */     HtmlRadioButtonInput rdBtnInputDnevni = (HtmlRadioButtonInput)enclosedPage.getElementById("rblIsDnevni_0");
/*  257:     */     
/*  258:     */ 
/*  259: 252 */     boolean isDnevni = rdBtnInputDnevni.isChecked();
/*  260: 253 */     boolean isDnevniDisabled = rdBtnInputDnevni.isDisabled();
/*  261:     */     
/*  262: 255 */     HtmlRadioButtonInput rdBtnInputDoDana = (HtmlRadioButtonInput)enclosedPage.getElementById("rblIsDnevni_1");
/*  263:     */     
/*  264:     */ 
/*  265: 258 */     boolean isDoDana = rdBtnInputDoDana.isChecked();
/*  266: 259 */     boolean isDoDanaDisabled = rdBtnInputDoDana.isDisabled();
/*  267: 261 */     if ((expiryDate != null) && (expiryDate.after(now)))
/*  268:     */     {
/*  269: 264 */       int expiryMonth = expiryDate.getMonth();
/*  270: 265 */       if (expiryMonth == nowMonth)
/*  271:     */       {
/*  272: 268 */         HtmlAnchor calendarPopUp = (HtmlAnchor)enclosedPage.getElementById("tbTrajanje_popupButton");
/*  273:     */         try
/*  274:     */         {
/*  275: 270 */           calendarPopUp.click();
/*  276:     */         }
/*  277:     */         catch (IOException e1)
/*  278:     */         {
/*  279: 272 */           e1.printStackTrace();
/*  280:     */         }
/*  281: 275 */         HtmlTable calendar = (HtmlTable)enclosedPage.getElementById("tbTrajanje_calendar_Top");
/*  282: 276 */         List tableRows = calendar.getHtmlElementsByTagName("tr");
/*  283: 277 */         Iterator ite = tableRows.iterator();
/*  284: 278 */         while (ite.hasNext())
/*  285:     */         {
/*  286: 279 */           boolean dateSet = false;
/*  287: 280 */           HtmlTableRow r = (HtmlTableRow)ite.next();
/*  288:     */           
/*  289: 282 */           List tds = r.getHtmlElementsByTagName("td");
/*  290: 283 */           Iterator i2 = tds.iterator();
/*  291: 284 */           while (i2.hasNext())
/*  292:     */           {
/*  293: 285 */             HtmlTableDataCell td = (HtmlTableDataCell)i2.next();
/*  294: 286 */             List as = td.getHtmlElementsByTagName("a");
/*  295: 287 */             if (as.size() != 0)
/*  296:     */             {
/*  297: 288 */               HtmlAnchor a = (HtmlAnchor)as.get(0);
/*  298: 289 */               if (a.getTextContent().equals(Integer.toString(expiryDate.getDate()))) {
/*  299:     */                 try
/*  300:     */                 {
/*  301: 291 */                   afterDateSet = (HtmlPage)a.click();
/*  302: 292 */                   this.browser.waitForBackgroundJavaScript(1000L);
/*  303: 293 */                   dateSet = true;
/*  304:     */                 }
/*  305:     */                 catch (IOException e)
/*  306:     */                 {
/*  307: 296 */                   MainLogger.error(e);
/*  308:     */                 }
/*  309:     */               }
/*  310:     */             }
/*  311:     */           }
/*  312: 301 */           if (dateSet) {
/*  313:     */             break;
/*  314:     */           }
/*  315:     */         }
/*  316:     */       }
/*  317:     */       else
/*  318:     */       {
/*  319: 306 */         MainLogger.error("Cannot put orders that are not in this month!");
/*  320:     */       }
/*  321:     */     }
/*  322: 313 */     else if ((isDnevniDisabled) && (isDoDanaDisabled))
/*  323:     */     {
/*  324: 315 */       HtmlAnchor calendarPopUp = (HtmlAnchor)enclosedPage.getElementById("tbTrajanje_popupButton");
/*  325:     */       try
/*  326:     */       {
/*  327: 317 */         calendarPopUp.click();
/*  328:     */       }
/*  329:     */       catch (IOException e1)
/*  330:     */       {
/*  331: 319 */         e1.printStackTrace();
/*  332:     */       }
/*  333: 323 */       HtmlTable calendar = (HtmlTable)enclosedPage.getElementById("tbTrajanje_calendar_Top");
/*  334: 324 */       List tableRows = calendar.getHtmlElementsByTagName("tr");
/*  335: 325 */       Iterator ite = tableRows.iterator();
/*  336: 326 */       while (ite.hasNext())
/*  337:     */       {
/*  338: 327 */         boolean dateSet = false;
/*  339: 328 */         HtmlTableRow r = (HtmlTableRow)ite.next();
/*  340:     */         
/*  341: 330 */         List tds = r.getHtmlElementsByTagName("td");
/*  342: 331 */         Iterator i2 = tds.iterator();
/*  343: 332 */         while (i2.hasNext())
/*  344:     */         {
/*  345: 333 */           HtmlTableDataCell td = (HtmlTableDataCell)i2.next();
/*  346: 334 */           List as = td.getHtmlElementsByTagName("a");
/*  347: 335 */           if (as.size() != 0)
/*  348:     */           {
/*  349: 336 */             HtmlAnchor a = (HtmlAnchor)as.get(0);
/*  350:     */             try
/*  351:     */             {
/*  352: 338 */               afterDateSet = (HtmlPage)a.click();
/*  353: 339 */               this.browser.waitForBackgroundJavaScript(1000L);
/*  354:     */             }
/*  355:     */             catch (IOException e)
/*  356:     */             {
/*  357: 341 */               MainLogger.error(e);
/*  358:     */             }
/*  359: 343 */             dateSet = true;
/*  360: 344 */             break;
/*  361:     */           }
/*  362:     */         }
/*  363: 347 */         if (dateSet) {
/*  364:     */           break;
/*  365:     */         }
/*  366:     */       }
/*  367:     */     }
/*  368: 354 */     else if ((!isDnevniDisabled) && (isDnevni))
/*  369:     */     {
/*  370: 358 */       afterDateSet = enclosedPage;
/*  371:     */     }
/*  372: 363 */     else if ((!isDnevniDisabled) && (!isDoDanaDisabled) && (isDoDana))
/*  373:     */     {
/*  374:     */       try
/*  375:     */       {
/*  376: 367 */         afterDateSet = (HtmlPage)rdBtnInputDnevni.click();
/*  377:     */       }
/*  378:     */       catch (IOException e)
/*  379:     */       {
/*  380: 369 */         MainLogger.error(e);
/*  381:     */       }
/*  382:     */     }
/*  383:     */     else
/*  384:     */     {
/*  385: 372 */       MainLogger.error("Unknown case happened when setting date!");
/*  386: 373 */       return QueryStatus.PAGE_PARSING_FAILED;
/*  387:     */     }
/*  388: 378 */     HtmlAnchor drpOznakaArrow = (HtmlAnchor)afterDateSet.getElementById("drpOznaka_Arrow");
/*  389:     */     try
/*  390:     */     {
/*  391: 380 */       drpOznakaArrow.click();
/*  392:     */     }
/*  393:     */     catch (IOException e1)
/*  394:     */     {
/*  395: 382 */       e1.printStackTrace();
/*  396:     */     }
/*  397: 384 */     HtmlPage afterStockSelect = null;
/*  398: 385 */     String stockDesc = "";
/*  399: 386 */     HtmlDivision div7 = (HtmlDivision)afterDateSet.getElementById("drpOznaka_DropDown");
/*  400: 387 */     HtmlUnorderedList list = (HtmlUnorderedList)div7.getElementsByTagName("ul").get(0);
/*  401: 388 */     List<HtmlElement> listElements = list.getHtmlElementsByTagName("li");
/*  402: 389 */     Iterator<HtmlElement> it = listElements.iterator();
/*  403: 390 */     while (it.hasNext())
/*  404:     */     {
/*  405: 391 */       HtmlElement he = (HtmlElement)it.next();
/*  406: 392 */       if (he.getTextContent().contains(ticker))
/*  407:     */       {
/*  408: 393 */         stockDesc = he.getTextContent();
/*  409:     */         try
/*  410:     */         {
/*  411: 396 */           afterStockSelect = (HtmlPage)he.click();
/*  412:     */         }
/*  413:     */         catch (IOException e)
/*  414:     */         {
/*  415: 398 */           MainLogger.error(e);
/*  416:     */         }
/*  417: 401 */         he.fireEvent("select");
/*  418:     */       }
/*  419:     */     }
/*  420: 404 */     this.browser.waitForBackgroundJavaScript(1000L);
/*  421:     */     
/*  422:     */ 
/*  423: 407 */     HtmlTextInput cijenaTextField = (HtmlTextInput)afterStockSelect.getElementById("tbCijena_text");
/*  424: 408 */     cijenaTextField.setAttribute("value", stockPriceString);
/*  425:     */     
/*  426:     */ 
/*  427: 411 */     HtmlSubmitInput submitBtn = (HtmlSubmitInput)afterStockSelect.getElementById("btnSave");
/*  428: 412 */     HtmlPage statusPage = null;
/*  429:     */     try
/*  430:     */     {
/*  431: 414 */       statusPage = (HtmlPage)submitBtn.click();
/*  432:     */     }
/*  433:     */     catch (IOException e)
/*  434:     */     {
/*  435: 416 */       MainLogger.error(e);
/*  436:     */     }
/*  437: 418 */     this.browser.waitForBackgroundJavaScript(1000L);
/*  438: 419 */     MainLogger.debug(statusPage.asXml());
/*  439:     */     
/*  440:     */ 
/*  441:     */ 
/*  442:     */ 
/*  443:     */ 
/*  444: 425 */     HtmlSpan span = (HtmlSpan)statusPage.getElementById("NalogPoruka");
/*  445:     */     
/*  446: 427 */     MainLogger.debug(span.asXml());
/*  447:     */     
/*  448: 429 */     String resString = span.getTextContent();
/*  449: 430 */     MainLogger.debug("BUY ORDER RESULT: " + resString);
/*  450: 432 */     if (resString.contains("Nalog je uspje�no zaprimljen"))
/*  451:     */     {
/*  452: 433 */       logout();
/*  453: 434 */       return QueryStatus.SUCCESS;
/*  454:     */     }
/*  455: 435 */     if (resString.contains("Nisu popunjena sva polja"))
/*  456:     */     {
/*  457: 436 */       logout();
/*  458: 437 */       return QueryStatus.NOT_ALL_FIELDS_FILLED;
/*  459:     */     }
/*  460: 438 */     if (resString.contains("Nemate dovoljno gotovine"))
/*  461:     */     {
/*  462: 439 */       logout();
/*  463: 440 */       return QueryStatus.NOT_ENOUGH_MONEY;
/*  464:     */     }
/*  465: 442 */     logout();
/*  466: 443 */     return QueryStatus.UNKNOWN;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public QueryStatus setSellOrder(String ticker, float price, int amount, Date expiryDate)
/*  470:     */   {
/*  471: 451 */     if (!this.logged) {
/*  472: 452 */       login();
/*  473:     */     }
/*  474: 454 */     openOrdersPage();
/*  475: 455 */     MainLogger.debug("****************************************");
/*  476: 456 */     MainLogger.debug("Setting sell order...");
/*  477: 457 */     MainLogger.debug("TICKER: " + ticker);
/*  478: 458 */     MainLogger.debug("PRICE: " + price);
/*  479: 459 */     MainLogger.debug("AMOUNT: " + amount);
/*  480: 460 */     if (expiryDate != null)
/*  481:     */     {
/*  482: 461 */       Format formatter = new SimpleDateFormat("MM/dd/yy");
/*  483: 462 */       String s = formatter.format(expiryDate);
/*  484: 463 */       MainLogger.debug("EXPIRY DATE: " + s);
/*  485:     */     }
/*  486: 465 */     MainLogger.debug("****************************************");
/*  487:     */     
/*  488:     */ 
/*  489: 468 */     HtmlDivision div4 = (HtmlDivision)this.ordersPage.getElementById("ctl00_ContentPlaceHolder1_radUpdatePanel");
/*  490: 469 */     HtmlImage sellImg = (HtmlImage)div4.getHtmlElementsByTagName("img").get(1);
/*  491: 470 */     if (!sellImg.getAttribute("title").equals("Prodaj"))
/*  492:     */     {
/*  493: 471 */       MainLogger.error("Could not find prodaj image!");
/*  494: 472 */       return QueryStatus.PAGE_PARSING_FAILED;
/*  495:     */     }
/*  496: 476 */     HtmlPage sellPage = null;
/*  497:     */     try
/*  498:     */     {
/*  499: 479 */       sellPage = (HtmlPage)sellImg.click();
/*  500: 480 */       this.browser.waitForBackgroundJavaScript(1000L);
/*  501:     */     }
/*  502:     */     catch (IOException e)
/*  503:     */     {
/*  504: 482 */       MainLogger.error(e);
/*  505:     */     }
/*  506: 485 */     FrameWindow sellFrame = (FrameWindow)sellPage.getFrames().get(0);
/*  507: 486 */     HtmlPage enclosedPage = (HtmlPage)sellFrame.getEnclosedPage();
/*  508:     */     
/*  509:     */ 
/*  510: 489 */     String stockPriceString = String.format("%.2f", new Object[] { Float.valueOf(price) });
/*  511: 490 */     String amountString = Integer.toString(amount);
/*  512:     */     
/*  513:     */ 
/*  514: 493 */     HtmlTextInput kolicinaTextField = (HtmlTextInput)enclosedPage.getElementById("tbKolicina");
/*  515: 494 */     kolicinaTextField.setText(amountString);
/*  516:     */     
/*  517:     */ 
/*  518: 497 */     Date now = new Date();
/*  519: 498 */     int nowMonth = now.getMonth();
/*  520: 499 */     HtmlPage afterDateSet = null;
/*  521:     */     
/*  522: 501 */     HtmlRadioButtonInput rdBtnInputDnevni = (HtmlRadioButtonInput)enclosedPage.getElementById("rblIsDnevni_0");
/*  523:     */     
/*  524:     */ 
/*  525: 504 */     boolean isDnevni = rdBtnInputDnevni.isChecked();
/*  526: 505 */     boolean isDnevniDisabled = rdBtnInputDnevni.isDisabled();
/*  527:     */     
/*  528: 507 */     HtmlRadioButtonInput rdBtnInputDoDana = (HtmlRadioButtonInput)enclosedPage.getElementById("rblIsDnevni_1");
/*  529:     */     
/*  530:     */ 
/*  531: 510 */     boolean isDoDana = rdBtnInputDoDana.isChecked();
/*  532: 511 */     boolean isDoDanaDisabled = rdBtnInputDoDana.isDisabled();
/*  533: 513 */     if ((expiryDate != null) && (expiryDate.after(now)))
/*  534:     */     {
/*  535: 516 */       int expiryMonth = expiryDate.getMonth();
/*  536: 517 */       if (expiryMonth == nowMonth)
/*  537:     */       {
/*  538: 520 */         HtmlAnchor calendarPopUp = (HtmlAnchor)enclosedPage.getElementById("tbTrajanje_popupButton");
/*  539:     */         try
/*  540:     */         {
/*  541: 522 */           calendarPopUp.click();
/*  542:     */         }
/*  543:     */         catch (IOException e1)
/*  544:     */         {
/*  545: 524 */           e1.printStackTrace();
/*  546:     */         }
/*  547: 527 */         HtmlTable calendar = (HtmlTable)enclosedPage.getElementById("tbTrajanje_calendar_Top");
/*  548: 528 */         List tableRows = calendar.getHtmlElementsByTagName("tr");
/*  549: 529 */         Iterator ite = tableRows.iterator();
/*  550: 530 */         while (ite.hasNext())
/*  551:     */         {
/*  552: 531 */           boolean dateSet = false;
/*  553: 532 */           HtmlTableRow r = (HtmlTableRow)ite.next();
/*  554:     */           
/*  555: 534 */           List tds = r.getHtmlElementsByTagName("td");
/*  556: 535 */           Iterator i2 = tds.iterator();
/*  557: 536 */           while (i2.hasNext())
/*  558:     */           {
/*  559: 537 */             HtmlTableDataCell td = (HtmlTableDataCell)i2.next();
/*  560: 538 */             List as = td.getHtmlElementsByTagName("a");
/*  561: 539 */             if (as.size() != 0)
/*  562:     */             {
/*  563: 540 */               HtmlAnchor a = (HtmlAnchor)as.get(0);
/*  564: 541 */               if (a.getTextContent().equals(Integer.toString(expiryDate.getDate()))) {
/*  565:     */                 try
/*  566:     */                 {
/*  567: 543 */                   afterDateSet = (HtmlPage)a.click();
/*  568: 544 */                   this.browser.waitForBackgroundJavaScript(1000L);
/*  569: 545 */                   dateSet = true;
/*  570:     */                 }
/*  571:     */                 catch (IOException e)
/*  572:     */                 {
/*  573: 548 */                   MainLogger.error(e);
/*  574:     */                 }
/*  575:     */               }
/*  576:     */             }
/*  577:     */           }
/*  578: 553 */           if (dateSet) {
/*  579:     */             break;
/*  580:     */           }
/*  581:     */         }
/*  582:     */       }
/*  583:     */       else
/*  584:     */       {
/*  585: 558 */         MainLogger.debug("Cannot put orders that are not in this month!");
/*  586:     */       }
/*  587:     */     }
/*  588: 565 */     else if ((isDnevniDisabled) && (isDoDanaDisabled))
/*  589:     */     {
/*  590: 567 */       HtmlAnchor calendarPopUp = (HtmlAnchor)enclosedPage.getElementById("tbTrajanje_popupButton");
/*  591:     */       try
/*  592:     */       {
/*  593: 569 */         calendarPopUp.click();
/*  594:     */       }
/*  595:     */       catch (IOException e1)
/*  596:     */       {
/*  597: 571 */         e1.printStackTrace();
/*  598:     */       }
/*  599: 575 */       HtmlTable calendar = (HtmlTable)enclosedPage.getElementById("tbTrajanje_calendar_Top");
/*  600: 576 */       List tableRows = calendar.getHtmlElementsByTagName("tr");
/*  601: 577 */       Iterator ite = tableRows.iterator();
/*  602: 578 */       while (ite.hasNext())
/*  603:     */       {
/*  604: 579 */         boolean dateSet = false;
/*  605: 580 */         HtmlTableRow r = (HtmlTableRow)ite.next();
/*  606:     */         
/*  607: 582 */         List tds = r.getHtmlElementsByTagName("td");
/*  608: 583 */         Iterator i2 = tds.iterator();
/*  609: 584 */         while (i2.hasNext())
/*  610:     */         {
/*  611: 585 */           HtmlTableDataCell td = (HtmlTableDataCell)i2.next();
/*  612: 586 */           List as = td.getHtmlElementsByTagName("a");
/*  613: 587 */           if (as.size() != 0)
/*  614:     */           {
/*  615: 588 */             HtmlAnchor a = (HtmlAnchor)as.get(0);
/*  616:     */             try
/*  617:     */             {
/*  618: 590 */               afterDateSet = (HtmlPage)a.click();
/*  619: 591 */               this.browser.waitForBackgroundJavaScript(1000L);
/*  620:     */             }
/*  621:     */             catch (IOException e)
/*  622:     */             {
/*  623: 593 */               MainLogger.error(e);
/*  624:     */             }
/*  625: 595 */             dateSet = true;
/*  626: 596 */             break;
/*  627:     */           }
/*  628:     */         }
/*  629: 599 */         if (dateSet) {
/*  630:     */           break;
/*  631:     */         }
/*  632:     */       }
/*  633:     */     }
/*  634: 606 */     else if ((!isDnevniDisabled) && (isDnevni))
/*  635:     */     {
/*  636: 610 */       afterDateSet = enclosedPage;
/*  637:     */     }
/*  638: 615 */     else if ((!isDnevniDisabled) && (!isDoDanaDisabled) && (isDoDana))
/*  639:     */     {
/*  640:     */       try
/*  641:     */       {
/*  642: 619 */         afterDateSet = (HtmlPage)rdBtnInputDnevni.click();
/*  643:     */       }
/*  644:     */       catch (IOException e)
/*  645:     */       {
/*  646: 621 */         MainLogger.error(e);
/*  647:     */       }
/*  648:     */     }
/*  649:     */     else
/*  650:     */     {
/*  651: 624 */       MainLogger.error("Unknown case happened when setting date!");
/*  652: 625 */       return QueryStatus.PAGE_PARSING_FAILED;
/*  653:     */     }
/*  654: 630 */     HtmlAnchor drpOznakaArrow = (HtmlAnchor)afterDateSet.getElementById("drpOznaka_Arrow");
/*  655:     */     try
/*  656:     */     {
/*  657: 632 */       drpOznakaArrow.click();
/*  658:     */     }
/*  659:     */     catch (IOException e1)
/*  660:     */     {
/*  661: 634 */       e1.printStackTrace();
/*  662:     */     }
/*  663: 636 */     HtmlPage afterStockSelect = null;
/*  664: 637 */     String stockDesc = "";
/*  665: 638 */     HtmlDivision div7 = (HtmlDivision)afterDateSet.getElementById("drpOznaka_DropDown");
/*  666: 639 */     HtmlUnorderedList list = (HtmlUnorderedList)div7.getElementsByTagName("ul").get(0);
/*  667: 640 */     List<HtmlElement> listElements = list.getHtmlElementsByTagName("li");
/*  668: 641 */     Iterator<HtmlElement> it = listElements.iterator();
/*  669: 642 */     while (it.hasNext())
/*  670:     */     {
/*  671: 643 */       HtmlElement he = (HtmlElement)it.next();
/*  672: 644 */       if (he.getTextContent().contains(ticker))
/*  673:     */       {
/*  674: 645 */         stockDesc = he.getTextContent();
/*  675:     */         try
/*  676:     */         {
/*  677: 648 */           afterStockSelect = (HtmlPage)he.click();
/*  678:     */         }
/*  679:     */         catch (IOException e)
/*  680:     */         {
/*  681: 650 */           MainLogger.error(e);
/*  682:     */         }
/*  683: 653 */         he.fireEvent("select");
/*  684:     */       }
/*  685:     */     }
/*  686: 656 */     this.browser.waitForBackgroundJavaScript(4000L);
/*  687:     */     
/*  688:     */ 
/*  689: 659 */     HtmlTextInput cijenaTextField = (HtmlTextInput)afterStockSelect.getElementById("tbCijena_text");
/*  690: 660 */     cijenaTextField.setAttribute("value", stockPriceString);
/*  691:     */     
/*  692:     */ 
/*  693: 663 */     HtmlSubmitInput submitBtn = (HtmlSubmitInput)afterStockSelect.getElementById("btnSave");
/*  694: 664 */     HtmlPage statusPage = null;
/*  695:     */     try
/*  696:     */     {
/*  697: 666 */       statusPage = (HtmlPage)submitBtn.click();
/*  698:     */     }
/*  699:     */     catch (IOException e)
/*  700:     */     {
/*  701: 668 */       MainLogger.error(e);
/*  702:     */     }
/*  703: 670 */     this.browser.waitForBackgroundJavaScript(2000L);
/*  704:     */     
/*  705:     */ 
/*  706:     */ 
/*  707:     */ 
/*  708:     */ 
/*  709:     */ 
/*  710: 677 */     HtmlSpan span = (HtmlSpan)statusPage.getElementById("NalogPoruka");
/*  711: 678 */     if (span != null)
/*  712:     */     {
/*  713: 679 */       String resString = span.getTextContent();
/*  714: 680 */       MainLogger.debug("SELL ORDER RESULT: " + resString);
/*  715: 683 */       if (resString.contains("Nalog je uspje�no zaprimljen!"))
/*  716:     */       {
/*  717: 684 */         logout();
/*  718: 685 */         return QueryStatus.SUCCESS;
/*  719:     */       }
/*  720: 687 */       if (resString.contains("Nalog je uspje�no proveden!"))
/*  721:     */       {
/*  722: 688 */         logout();
/*  723:     */         
/*  724: 690 */         readPortfolioData();
/*  725: 691 */         return QueryStatus.ORDER_EXECUTED;
/*  726:     */       }
/*  727: 694 */       if (resString.contains("Nisu popunjena sva polja"))
/*  728:     */       {
/*  729: 695 */         logout();
/*  730: 696 */         return QueryStatus.NOT_ALL_FIELDS_FILLED;
/*  731:     */       }
/*  732: 697 */       if (resString.contains("Nemate toliku koli�inu dionice"))
/*  733:     */       {
/*  734: 698 */         logout();
/*  735: 699 */         return QueryStatus.NOT_ENOUGH_STOCKS;
/*  736:     */       }
/*  737: 700 */       if (resString.contains("Navedenu dionicu ne posjedujete"))
/*  738:     */       {
/*  739: 701 */         logout();
/*  740: 702 */         return QueryStatus.STOCK_NOT_OWNED;
/*  741:     */       }
/*  742: 704 */       MainLogger.error("Nepoznata greska 1!");
/*  743: 705 */       logout();
/*  744: 706 */       return QueryStatus.UNKNOWN;
/*  745:     */     }
/*  746: 710 */     if (statusPage.asText().contains("Navedenu dionicu ne posjedujete"))
/*  747:     */     {
/*  748: 711 */       MainLogger.error("Navedenu dionicu ne posjedujete");
/*  749: 712 */       logout();
/*  750: 713 */       return QueryStatus.STOCK_NOT_OWNED;
/*  751:     */     }
/*  752: 714 */     if (statusPage.asText().contains("Nemate toliku koli�inu dionice"))
/*  753:     */     {
/*  754: 715 */       MainLogger.error("Nemate toliku koli�inu dionice");
/*  755: 716 */       logout();
/*  756: 717 */       return QueryStatus.STOCK_NOT_OWNED;
/*  757:     */     }
/*  758: 720 */     if (statusPage.asText().contains("Nisu popunjena sva polja!"))
/*  759:     */     {
/*  760: 721 */       MainLogger.error("Nisu popunjena sva polja!");
/*  761: 722 */       logout();
/*  762: 723 */       return QueryStatus.NOT_ALL_FIELDS_FILLED;
/*  763:     */     }
/*  764: 725 */     MainLogger.error("Nepoznata greska 2!");
/*  765: 726 */     logout();
/*  766: 727 */     return QueryStatus.UNKNOWN;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public PortfolioData readPortfolioData()
/*  770:     */   {
/*  771: 735 */     MainLogger.info("-----------------------------------------------------------");
/*  772: 736 */     MainLogger.info("Getting Portfolio Data from Virtualna Burza...");
/*  773: 737 */     if (!this.logged) {
/*  774: 738 */       login();
/*  775:     */     }
/*  776: 740 */     PortfolioData portfolioData = null;
/*  777: 741 */     MainLogger.info("Getting portfolio info...");
/*  778: 743 */     if (this.mainPage != null)
/*  779:     */     {
/*  780: 744 */       HtmlTable reportTable = null;
/*  781:     */       
/*  782: 746 */       List tables = this.mainPage.getElementsByTagName("table");
/*  783: 747 */       Iterator i = tables.iterator();
/*  784: 748 */       while (i.hasNext())
/*  785:     */       {
/*  786: 749 */         HtmlTable t = (HtmlTable)i.next();
/*  787: 750 */         if (t.getAttribute("class").equals("reportTable")) {
/*  788: 752 */           reportTable = t;
/*  789:     */         }
/*  790:     */       }
/*  791: 757 */       NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/*  792: 759 */       if (reportTable != null)
/*  793:     */       {
/*  794: 760 */         portfolioData = new PortfolioData();
/*  795: 761 */         List rows = reportTable.getElementsByTagName("tr");
/*  796: 762 */         Iterator it = rows.iterator();
/*  797: 764 */         while (it.hasNext())
/*  798:     */         {
/*  799: 765 */           HtmlTableRow row = (HtmlTableRow)it.next();
/*  800: 766 */           String rowFirstCellText = ((HtmlElement)row.getElementsByTagName("td").get(0)).getTextContent().trim();
/*  801:     */           try
/*  802:     */           {
/*  803: 769 */             if ((!rowFirstCellText.equals("Simbol")) && (!rowFirstCellText.equals(""))) {
/*  804: 771 */               if (rowFirstCellText.contains("Gotovina"))
/*  805:     */               {
/*  806: 772 */                 String cashString = ((HtmlElement)row.getElementsByTagName("td").get(2)).getTextContent().trim();
/*  807:     */                 
/*  808: 774 */                 portfolioData.cash = nfGerman.parse(cashString).floatValue();
/*  809: 775 */                 MainLogger.debug("Cash: " + portfolioData.cash);
/*  810:     */                 
/*  811: 777 */                 String cashPercentString = ((HtmlElement)row.getElementsByTagName("td").get(5)).getTextContent().trim();
/*  812:     */                 
/*  813: 779 */                 portfolioData.cashPercentage = 
/*  814: 780 */                   ParasiteUtils.getSignedFloatFromPercentageSpacedString(cashPercentString);
/*  815: 781 */                 MainLogger.debug("Cash (%):" + portfolioData.cashPercentage);
/*  816:     */               }
/*  817: 783 */               else if (rowFirstCellText.contains("Ukupno"))
/*  818:     */               {
/*  819: 784 */                 String totalProfitPercentString = ((HtmlElement)row.getElementsByTagName("td").get(1)).getTextContent()
/*  820: 785 */                   .trim();
/*  821:     */                 
/*  822: 787 */                 portfolioData.totalProfitPercent = 
/*  823: 788 */                   ParasiteUtils.getSignedFloatFromPercentageSpacedString(totalProfitPercentString);
/*  824: 789 */                 MainLogger.debug("Total profit (%):" + portfolioData.totalProfitPercent);
/*  825:     */                 
/*  826: 791 */                 String totalMarketValueString = ((HtmlElement)row.getElementsByTagName("td").get(3)).getTextContent()
/*  827: 792 */                   .trim();
/*  828:     */                 
/*  829: 794 */                 portfolioData.totalMarketValue = nfGerman.parse(totalMarketValueString).floatValue();
/*  830: 795 */                 MainLogger.debug("Total market value:" + portfolioData.totalMarketValue);
/*  831:     */                 
/*  832: 797 */                 String totalBuyValueString = ((HtmlElement)row.getElementsByTagName("td").get(4)).getTextContent().trim();
/*  833:     */                 
/*  834: 799 */                 portfolioData.totalBuyValue = nfGerman.parse(totalBuyValueString).floatValue();
/*  835: 800 */                 MainLogger.debug("Total buy  value:" + portfolioData.totalBuyValue);
/*  836:     */                 
/*  837: 802 */                 portfolioData.totalProfit = (portfolioData.totalMarketValue - portfolioData.totalBuyValue);
/*  838: 803 */                 MainLogger.debug("Total profit:" + portfolioData.totalProfit);
/*  839:     */               }
/*  840: 805 */               else if (rowFirstCellText.contains("-"))
/*  841:     */               {
/*  842: 807 */                 StockPortfolioData psi = new StockPortfolioData();
/*  843: 808 */                 psi.ticker = rowFirstCellText;
/*  844: 809 */                 MainLogger.debug("TICKER: _" + psi.ticker + "_");
/*  845:     */                 
/*  846: 811 */                 String profitString = ((HtmlElement)row.getElementsByTagName("td").get(1)).getTextContent().trim();
/*  847:     */                 
/*  848: 813 */                 psi.profitPercentage = ParasiteUtils.getSignedFloatFromPercentageSpacedString(profitString);
/*  849: 814 */                 MainLogger.debug("Profit percentage = " + psi.profitPercentage);
/*  850:     */                 
/*  851: 816 */                 String marketPriceString = ((HtmlElement)row.getElementsByTagName("td").get(2)).getTextContent().trim();
/*  852:     */                 
/*  853: 818 */                 psi.marketPrice = nfGerman.parse(marketPriceString).floatValue();
/*  854: 819 */                 MainLogger.debug("Market price = " + psi.marketPrice);
/*  855:     */                 
/*  856: 821 */                 String buyPriceString = ((HtmlElement)row.getElementsByTagName("td").get(3)).getTextContent().trim();
/*  857:     */                 
/*  858: 823 */                 psi.buyPrice = nfGerman.parse(buyPriceString).floatValue();
/*  859: 824 */                 MainLogger.debug("Buy price = " + psi.buyPrice);
/*  860:     */                 
/*  861: 826 */                 String marketValueString = ((HtmlElement)row.getElementsByTagName("td").get(4)).getTextContent().trim();
/*  862:     */                 
/*  863: 828 */                 psi.marketValue = nfGerman.parse(marketValueString).floatValue();
/*  864: 829 */                 MainLogger.debug("Market value = " + psi.marketValue);
/*  865:     */                 
/*  866: 831 */                 String buyValueString = ((HtmlElement)row.getElementsByTagName("td").get(5)).getTextContent().trim();
/*  867:     */                 
/*  868: 833 */                 psi.buyValue = nfGerman.parse(buyValueString).floatValue();
/*  869: 834 */                 MainLogger.debug("Buy value = " + psi.buyValue);
/*  870:     */                 
/*  871: 836 */                 String amountString = ((HtmlElement)row.getElementsByTagName("td").get(6)).getTextContent().trim();
/*  872:     */                 
/*  873: 838 */                 psi.amount = Integer.parseInt(amountString);
/*  874: 839 */                 MainLogger.debug("Amount = " + psi.amount);
/*  875:     */                 
/*  876: 841 */                 String portfolioValuePercentageString = ((HtmlElement)row.getElementsByTagName("td").get(7))
/*  877: 842 */                   .getTextContent().trim();
/*  878:     */                 
/*  879: 844 */                 String[] tokens2 = portfolioValuePercentageString.split(" ");
/*  880: 845 */                 psi.portfolioValuePercentage = nfGerman.parse(tokens2[0]).floatValue();
/*  881: 846 */                 MainLogger.debug("Portfolio value percentage = " + psi.portfolioValuePercentage);
/*  882:     */                 
/*  883: 848 */                 portfolioData.stockInfo.add(psi);
/*  884: 849 */                 portfolioData.lastRefresh = new Date();
/*  885:     */               }
/*  886:     */             }
/*  887:     */           }
/*  888:     */           catch (ParseException e)
/*  889:     */           {
/*  890: 852 */             MainLogger.error(e);
/*  891: 853 */             return null;
/*  892:     */           }
/*  893:     */         }
/*  894:     */       }
/*  895:     */       else
/*  896:     */       {
/*  897: 858 */         MainLogger.error("Could not find report table!");
/*  898:     */       }
/*  899:     */     }
/*  900:     */     else
/*  901:     */     {
/*  902: 862 */       MainLogger.error("Main page not loaded!!!");
/*  903:     */     }
/*  904: 865 */     return portfolioData;
/*  905:     */   }
/*  906:     */   
/*  907:     */   public List<Order> readPendingOrdersInfo()
/*  908:     */   {
/*  909: 870 */     MainLogger.info("-----------------------------------------------------------");
/*  910: 871 */     MainLogger.info("Getting pending orders from Virtualna Burza...");
/*  911: 872 */     if (!this.logged) {
/*  912: 873 */       login();
/*  913:     */     }
/*  914:     */     try
/*  915:     */     {
/*  916: 877 */       this.ordersPage = ((HtmlPage)this.browser.getPage("http://vb.zse.hr/MojiNalozi.aspx"));
/*  917:     */     }
/*  918:     */     catch (Exception e)
/*  919:     */     {
/*  920: 880 */       MainLogger.error(e);
/*  921: 881 */       return null;
/*  922:     */     }
/*  923: 883 */     this.ownOrders.clear();
/*  924: 884 */     HtmlDivision div = (HtmlDivision)this.ordersPage.getHtmlElementById("ctl00_ContentPlaceHolder1_radUpdatePanel");
/*  925: 885 */     HtmlTable table = (HtmlTable)div.getHtmlElementsByTagName("table").get(0);
/*  926:     */     
/*  927:     */ 
/*  928: 888 */     List<HtmlTableRow> rows = table.getHtmlElementsByTagName("tr");
/*  929: 889 */     if (rows.size() > 1)
/*  930:     */     {
/*  931: 890 */       Iterator<HtmlTableRow> i = rows.iterator();
/*  932:     */       
/*  933: 892 */       int rowIndex = 0;
/*  934: 893 */       while (i.hasNext())
/*  935:     */       {
/*  936: 894 */         HtmlTableRow row = (HtmlTableRow)i.next();
/*  937: 895 */         if (rowIndex > 0)
/*  938:     */         {
/*  939: 898 */           List<HtmlElement> dataCells = row.getHtmlElementsByTagName("td");
/*  940: 899 */           Order order = new Order();
/*  941: 900 */           NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/*  942:     */           try
/*  943:     */           {
/*  944: 903 */             HtmlTableDataCell typeCell = (HtmlTableDataCell)dataCells.get(0);
/*  945: 904 */             String typeString = typeCell.getTextContent().trim();
/*  946: 905 */             if (typeString.contains("Prodaja")) {
/*  947: 906 */               order.type = OrderType.SELL;
/*  948: 908 */             } else if (typeString.contains("Kupnja")) {
/*  949: 909 */               order.type = OrderType.BUY;
/*  950:     */             }
/*  951: 915 */             HtmlTableDataCell tickerCell = (HtmlTableDataCell)dataCells.get(1);
/*  952:     */             
/*  953: 917 */             HtmlAnchor a = (HtmlAnchor)tickerCell.getHtmlElementsByTagName("a").get(0);
/*  954: 918 */             String tickerString = a.getTextContent().trim();
/*  955:     */             
/*  956: 920 */             order.ticker = tickerString;
/*  957:     */             
/*  958:     */ 
/*  959: 923 */             HtmlTableDataCell amountCell = (HtmlTableDataCell)dataCells.get(2);
/*  960:     */             
/*  961: 925 */             String amountString = amountCell.getTextContent().trim();
/*  962:     */             
/*  963: 927 */             order.amount = Integer.parseInt(amountString);
/*  964:     */             
/*  965:     */ 
/*  966: 930 */             HtmlTableDataCell priceCell = (HtmlTableDataCell)dataCells.get(3);
/*  967: 931 */             String priceString = priceCell.getTextContent().trim();
/*  968: 932 */             order.price = nfGerman.parse(priceString).floatValue();
/*  969:     */             
/*  970:     */ 
/*  971: 935 */             HtmlTableDataCell dateCell1 = (HtmlTableDataCell)dataCells.get(4);
/*  972: 936 */             String dateString1 = dateCell1.getTextContent().trim();
/*  973: 937 */             SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
/*  974: 938 */             Date date = format.parse(dateString1);
/*  975:     */             
/*  976:     */ 
/*  977:     */ 
/*  978:     */ 
/*  979:     */ 
/*  980: 944 */             HtmlTableDataCell dateCell2 = (HtmlTableDataCell)dataCells.get(5);
/*  981: 945 */             String dateString2 = dateCell2.getTextContent().trim();
/*  982: 947 */             if (dateString2.equals("Dnevni"))
/*  983:     */             {
/*  984: 948 */               order.expiryDate = new Date();
/*  985:     */             }
/*  986:     */             else
/*  987:     */             {
/*  988: 950 */               SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy");
/*  989: 951 */               order.expiryDate = format2.parse(dateString2);
/*  990:     */             }
/*  991: 958 */             MainLogger.info("Found order :" + order.toString());
/*  992: 959 */             this.ownOrders.add(order);
/*  993:     */           }
/*  994:     */           catch (Exception e)
/*  995:     */           {
/*  996: 962 */             MainLogger.error(e);
/*  997:     */           }
/*  998:     */         }
/*  999: 965 */         rowIndex++;
/* 1000:     */       }
/* 1001:     */     }
/* 1002:     */     else
/* 1003:     */     {
/* 1004: 969 */       MainLogger.info("No pending orders!");
/* 1005:     */     }
/* 1006: 971 */     return this.ownOrders;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public float readAvailableCash()
/* 1010:     */   {
/* 1011: 976 */     if (this.portfolio == null) {
/* 1012: 977 */       this.portfolio = readPortfolioData();
/* 1013:     */     }
/* 1014: 979 */     return this.portfolio.cash;
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public float readCashInPendingBuyOrders()
/* 1018:     */   {
/* 1019: 984 */     float reservedCash = 0.0F;
/* 1020: 985 */     List<Order> ownOrders = readPendingOrdersInfo();
/* 1021: 986 */     Iterator<Order> i = ownOrders.iterator();
/* 1022: 987 */     while (i.hasNext())
/* 1023:     */     {
/* 1024: 988 */       Order order = (Order)i.next();
/* 1025: 989 */       if (order.type == OrderType.BUY)
/* 1026:     */       {
/* 1027: 990 */         float orderValue = order.amount * order.price;
/* 1028: 991 */         reservedCash = reservedCash + orderValue + calculateFee(orderValue);
/* 1029:     */       }
/* 1030:     */     }
/* 1031: 994 */     return reservedCash;
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public List<Order> readPendingOrdersInfoForTicker(String ticker)
/* 1035:     */   {
/* 1036:1001 */     MainLogger.error("VirtualnaBurza.java readPendingOrdersInfoForTicker() NOT IMPLEMENTED!!!");
/* 1037:1002 */     return null;
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public List<Transaction> readCompletedOrdersInfo()
/* 1041:     */   {
/* 1042:1008 */     return null;
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public List<Transaction> readCompletedOrdersInfoForTicker(String ticker)
/* 1046:     */   {
/* 1047:1014 */     return null;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public float readCashInPendingSellOrders()
/* 1051:     */   {
/* 1052:1020 */     return 0.0F;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public QueryStatus cancelBuyOrder(String ticker, float price, int amount, Date expiryDate)
/* 1056:     */   {
/* 1057:1026 */     return null;
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public QueryStatus cancelSellOrder(String ticker, float price, int amount, Date expiryDate)
/* 1061:     */   {
/* 1062:1032 */     return null;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   private static class VBAccount
/* 1066:     */   {
/* 1067:     */     String username;
/* 1068:     */     String password;
/* 1069:     */     String email;
/* 1070:     */     String emailUsername;
/* 1071:     */     String emailPassword;
/* 1072:     */     
/* 1073:     */     public VBAccount(String user, String pass, String email, String emailUser, String emailPass)
/* 1074:     */     {
/* 1075:1048 */       this.username = user;
/* 1076:1049 */       this.password = pass;
/* 1077:1050 */       this.email = email;
/* 1078:1051 */       this.emailPassword = emailPass;
/* 1079:1052 */       this.emailUsername = emailUser;
/* 1080:     */     }
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   public List<StockDaySummary> readDaySummariesFromWeb(String ticker)
/* 1084:     */   {
/* 1085:1059 */     List<StockDaySummary> daySummaryList = new ArrayList();
/* 1086:     */     
/* 1087:1061 */     MainLogger.debug("Getting history data (from WEB) for ticker " + ticker);
/* 1088:     */     try
/* 1089:     */     {
/* 1090:1063 */       this.zseStockDetailsPage = ((HtmlPage)this.browser.getPage("http://www.zse.hr/default.aspx?id=10006&dionica=" + ticker));
/* 1091:     */     }
/* 1092:     */     catch (FailingHttpStatusCodeException e)
/* 1093:     */     {
/* 1094:1065 */       MainLogger.error(e);
/* 1095:     */     }
/* 1096:     */     catch (MalformedURLException e)
/* 1097:     */     {
/* 1098:1067 */       MainLogger.error(e);
/* 1099:     */     }
/* 1100:     */     catch (IOException e)
/* 1101:     */     {
/* 1102:1069 */       MainLogger.error(e);
/* 1103:     */     }
/* 1104:     */     try
/* 1105:     */     {
/* 1106:1075 */       HtmlTable dnevnaTrgovanjaTable = (HtmlTable)this.zseStockDetailsPage.getHtmlElementById("dnevna_trgovanja");
/* 1107:     */       
/* 1108:     */ 
/* 1109:1078 */       List<HtmlElement> tbodyList = dnevnaTrgovanjaTable.getHtmlElementsByTagName("tbody");
/* 1110:1079 */       if (!tbodyList.isEmpty())
/* 1111:     */       {
/* 1112:1080 */         HtmlTableBody body = (HtmlTableBody)tbodyList.get(0);
/* 1113:     */         
/* 1114:     */ 
/* 1115:     */ 
/* 1116:1084 */         List<HtmlElement> rows = body.getHtmlElementsByTagName("tr");
/* 1117:1085 */         Iterator<HtmlElement> i = rows.iterator();
/* 1118:1086 */         while (i.hasNext())
/* 1119:     */         {
/* 1120:1087 */           HtmlTableRow row = (HtmlTableRow)i.next();
/* 1121:1088 */           List<HtmlElement> dataCells = row.getHtmlElementsByTagName("td");
/* 1122:     */           
/* 1123:1090 */           StockDaySummary daySummary = new StockDaySummary();
/* 1124:1091 */           NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/* 1125:     */           try
/* 1126:     */           {
/* 1127:1095 */             HtmlTableDataCell dateCell = (HtmlTableDataCell)dataCells.get(0);
/* 1128:1096 */             String dateString = dateCell.getTextContent().trim();
/* 1129:     */             
/* 1130:1098 */             SimpleDateFormat format = new SimpleDateFormat("dd.M.yyyy");
/* 1131:1099 */             daySummary.date = format.parse(dateString);
/* 1132:     */             
/* 1133:     */ 
/* 1134:     */ 
/* 1135:     */ 
/* 1136:     */ 
/* 1137:1105 */             HtmlTableDataCell firstCell = (HtmlTableDataCell)dataCells.get(1);
/* 1138:1106 */             String firstString = firstCell.getTextContent().trim();
/* 1139:1107 */             MainLogger.debug("First=" + firstString);
/* 1140:1108 */             daySummary.first = nfGerman.parse(firstString).floatValue();
/* 1141:     */             
/* 1142:     */ 
/* 1143:1111 */             HtmlTableDataCell highCell = (HtmlTableDataCell)dataCells.get(2);
/* 1144:1112 */             String highString = highCell.getTextContent().trim();
/* 1145:     */             
/* 1146:1114 */             daySummary.high = nfGerman.parse(highString).floatValue();
/* 1147:     */             
/* 1148:     */ 
/* 1149:1117 */             HtmlTableDataCell lowCell = (HtmlTableDataCell)dataCells.get(3);
/* 1150:1118 */             String lowString = lowCell.getTextContent().trim();
/* 1151:     */             
/* 1152:1120 */             daySummary.low = nfGerman.parse(lowString).floatValue();
/* 1153:     */             
/* 1154:     */ 
/* 1155:1123 */             HtmlTableDataCell lastCell = (HtmlTableDataCell)dataCells.get(4);
/* 1156:1124 */             String lastString = lastCell.getTextContent().trim();
/* 1157:     */             
/* 1158:1126 */             daySummary.last = nfGerman.parse(lastString).floatValue();
/* 1159:     */             
/* 1160:     */ 
/* 1161:1129 */             HtmlTableDataCell averageCell = (HtmlTableDataCell)dataCells.get(5);
/* 1162:1130 */             String averageString = averageCell.getTextContent().trim();
/* 1163:     */             
/* 1164:1132 */             daySummary.average = nfGerman.parse(averageString).floatValue();
/* 1165:     */             
/* 1166:     */ 
/* 1167:1135 */             HtmlTableDataCell changeCell = (HtmlTableDataCell)dataCells.get(6);
/* 1168:1136 */             String changeString = changeCell.getTextContent().trim();
/* 1169:     */             
/* 1170:1138 */             daySummary.change = nfGerman.parse(changeString).floatValue();
/* 1171:     */             
/* 1172:     */ 
/* 1173:1141 */             HtmlTableDataCell numTransCell = (HtmlTableDataCell)dataCells.get(7);
/* 1174:1142 */             String numTransString = numTransCell.getTextContent().trim();
/* 1175:     */             
/* 1176:1144 */             daySummary.transactionNum = nfGerman.parse(numTransString).intValue();
/* 1177:     */             
/* 1178:     */ 
/* 1179:1147 */             HtmlTableDataCell amountCell = (HtmlTableDataCell)dataCells.get(8);
/* 1180:1148 */             String amountString = amountCell.getTextContent().trim();
/* 1181:     */             
/* 1182:1150 */             daySummary.amount = nfGerman.parse(amountString).intValue();
/* 1183:     */             
/* 1184:     */ 
/* 1185:1153 */             HtmlTableDataCell turoverCell = (HtmlTableDataCell)dataCells.get(9);
/* 1186:1154 */             String turnoverString = turoverCell.getTextContent().trim();
/* 1187:     */             
/* 1188:1156 */             daySummary.turnover = nfGerman.parse(turnoverString).floatValue();
/* 1189:     */             
/* 1190:1158 */             daySummaryList.add(daySummary);
/* 1191:     */           }
/* 1192:     */           catch (ParseException e)
/* 1193:     */           {
/* 1194:1160 */             MainLogger.error(e);
/* 1195:     */           }
/* 1196:     */         }
/* 1197:     */       }
/* 1198:     */     }
/* 1199:     */     catch (Exception e)
/* 1200:     */     {
/* 1201:1166 */       MainLogger.error(e);
/* 1202:     */     }
/* 1203:1169 */     return daySummaryList;
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   public synchronized List<StockHistoryData> getHistoryDataForTickers(List<String> tickers)
/* 1207:     */   {
/* 1208:1174 */     List<StockHistoryData> historyData = new ArrayList();
/* 1209:1175 */     Iterator<String> i = tickers.iterator();
/* 1210:1176 */     while (i.hasNext())
/* 1211:     */     {
/* 1212:1177 */       String ticker = (String)i.next();
/* 1213:1178 */       StockHistoryData history = new StockHistoryData(ticker);
/* 1214:1179 */       if (!history.readDaySummariesFromFile())
/* 1215:     */       {
/* 1216:1180 */         MainLogger.info("No day summaries file found.");
/* 1217:1181 */         history.daySummaries = readDaySummariesFromWeb(ticker);
/* 1218:1182 */         history.writeDaySummariesToFile();
/* 1219:     */       }
/* 1220:1184 */       history.calculateAverageDayValues();
/* 1221:1185 */       historyData.add(history);
/* 1222:     */     }
/* 1223:1196 */     return historyData;
/* 1224:     */   }
/* 1225:     */   
/* 1226:     */   public QueryStatus cancelBuyOrder(String ticker)
/* 1227:     */   {
/* 1228:1201 */     Date now = new Date();
/* 1229:1202 */     Date fifteentMinutesBeforeNow = new Date(now.getTime() - 900000L);
/* 1230:1203 */     if ((!this.logged) || (this.lastLogin.before(fifteentMinutesBeforeNow))) {
/* 1231:1204 */       login();
/* 1232:     */     }
/* 1233:     */     try
/* 1234:     */     {
/* 1235:1208 */       this.ordersPage = ((HtmlPage)this.browser.getPage("http://vb.zse.hr/MojiNalozi.aspx"));
/* 1236:     */     }
/* 1237:     */     catch (Exception e)
/* 1238:     */     {
/* 1239:1212 */       MainLogger.error(e);
/* 1240:1213 */       return null;
/* 1241:     */     }
/* 1242:1216 */     HtmlDivision div = (HtmlDivision)this.ordersPage.getHtmlElementById("ctl00_ContentPlaceHolder1_radUpdatePanel");
/* 1243:1217 */     HtmlTable table = (HtmlTable)div.getHtmlElementsByTagName("table").get(0);
/* 1244:     */     
/* 1245:     */ 
/* 1246:1220 */     List<HtmlTableRow> rows = table.getHtmlElementsByTagName("tr");
/* 1247:1221 */     if (rows.size() > 1)
/* 1248:     */     {
/* 1249:1222 */       Iterator<HtmlTableRow> i = rows.iterator();
/* 1250:     */       
/* 1251:1224 */       int rowIndex = 0;
/* 1252:1225 */       while (i.hasNext())
/* 1253:     */       {
/* 1254:1226 */         HtmlTableRow row = (HtmlTableRow)i.next();
/* 1255:1227 */         if (rowIndex > 0)
/* 1256:     */         {
/* 1257:1228 */           List<HtmlElement> dataCells = row.getHtmlElementsByTagName("td");
/* 1258:1229 */           Order order = new Order();
/* 1259:1230 */           NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/* 1260:     */           try
/* 1261:     */           {
/* 1262:1233 */             HtmlTableDataCell typeCell = (HtmlTableDataCell)dataCells.get(0);
/* 1263:1234 */             String typeString = typeCell.getTextContent().trim();
/* 1264:1235 */             if (typeString.contains("Prodaja")) {
/* 1265:1236 */               order.type = OrderType.SELL;
/* 1266:1238 */             } else if (typeString.contains("Kupnja")) {
/* 1267:1239 */               order.type = OrderType.BUY;
/* 1268:     */             }
/* 1269:1243 */             HtmlTableDataCell tickerCell = (HtmlTableDataCell)dataCells.get(1);
/* 1270:     */             
/* 1271:1245 */             HtmlAnchor a = (HtmlAnchor)tickerCell.getHtmlElementsByTagName("a").get(0);
/* 1272:1246 */             String tickerString = a.getTextContent().trim();
/* 1273:     */             
/* 1274:1248 */             order.ticker = tickerString;
/* 1275:1250 */             if ((order.ticker.equals(ticker)) && (order.type == OrderType.BUY))
/* 1276:     */             {
/* 1277:1264 */               HtmlForm formA = this.ordersPage.getFormByName("aspnetForm");
/* 1278:1265 */               HtmlImageInput cancelButton = 
/* 1279:1266 */                 (HtmlImageInput)formA.getInputByName("ctl00$ContentPlaceHolder1$rptOtvoreniNalozi$ctl01$btnOtkaziLink");
/* 1280:     */               try
/* 1281:     */               {
/* 1282:1270 */                 this.ordersPage = ((HtmlPage)cancelButton.click());
/* 1283:     */                 
/* 1284:1272 */                 MainLogger.debug("cancel button clicked");
/* 1285:     */               }
/* 1286:     */               catch (Exception e)
/* 1287:     */               {
/* 1288:1274 */                 MainLogger.error(e);
/* 1289:     */               }
/* 1290:     */             }
/* 1291:1282 */             rowIndex++;
/* 1292:     */           }
/* 1293:     */           catch (Exception e)
/* 1294:     */           {
/* 1295:1279 */             MainLogger.error(e);
/* 1296:     */           }
/* 1297:     */         }
/* 1298:     */       }
/* 1299:     */     }
/* 1300:     */     else
/* 1301:     */     {
/* 1302:1286 */       MainLogger.info("No pending orders!");
/* 1303:     */     }
/* 1304:1288 */     return null;
/* 1305:     */   }
/* 1306:     */   
/* 1307:     */   public QueryStatus cancelSellOrder(String ticker)
/* 1308:     */   {
/* 1309:1295 */     return null;
/* 1310:     */   }
/* 1311:     */   
/* 1312:     */   public void setDataCollector(DataCollector dc)
/* 1313:     */   {
/* 1314:1300 */     this.dataCollector = dc;
/* 1315:     */   }
/* 1316:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.VirtualnaBurza
 * JD-Core Version:    0.7.0.1
 */