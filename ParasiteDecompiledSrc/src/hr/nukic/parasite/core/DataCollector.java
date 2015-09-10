/*   1:    */ package hr.nukic.parasite.core;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.accounts.FimaEtrade;
/*   4:    */ import hr.nukic.parasite.accounts.SimulatedMarketMonitorAccount;
/*   5:    */ import hr.nukic.parasite.accounts.VirtualnaBurza;
/*   6:    */ import hr.nukic.parasite.accounts.templates.HistoryDataProvider;
/*   7:    */ import hr.nukic.parasite.accounts.templates.MarketMonitorAccount;
/*   8:    */ import hr.nukic.parasite.accounts.templates.TradingAccount;
/*   9:    */ import hr.nukic.parasite.simulator.MarketSimulator;
/*  10:    */ import java.text.Format;
/*  11:    */ import java.text.SimpleDateFormat;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Arrays;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Timer;
/*  18:    */ import java.util.TimerTask;
/*  19:    */ import nukic.parasite.utils.MainLogger;
/*  20:    */ import nukic.parasite.utils.ParasiteUtils;
/*  21:    */ 
/*  22:    */ public class DataCollector
/*  23:    */   implements Runnable
/*  24:    */ {
/*  25: 68 */   public static List<String> CROBEX_TICKERS = new ArrayList();
/*  26: 71 */   public static Format DATE_FORMATTER = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
/*  27:    */   
/*  28:    */   static
/*  29:    */   {
/*  30: 74 */     CROBEX_TICKERS.add("ADRS-P-A");
/*  31: 75 */     CROBEX_TICKERS.add("ATGR-R-A");
/*  32: 76 */     CROBEX_TICKERS.add("ATPL-R-A");
/*  33: 77 */     CROBEX_TICKERS.add("CKML-R-A");
/*  34: 78 */     CROBEX_TICKERS.add("DLKV-R-A");
/*  35:    */     
/*  36: 80 */     CROBEX_TICKERS.add("ERNT-R-A");
/*  37: 81 */     CROBEX_TICKERS.add("HT-R-A");
/*  38: 82 */     CROBEX_TICKERS.add("IGH-R-A");
/*  39: 83 */     CROBEX_TICKERS.add("INA-R-A");
/*  40: 84 */     CROBEX_TICKERS.add("INGR-R-A");
/*  41:    */     
/*  42: 86 */     CROBEX_TICKERS.add("ISTT-R-A");
/*  43: 87 */     CROBEX_TICKERS.add("JDPL-R-A");
/*  44: 88 */     CROBEX_TICKERS.add("JNAF-R-A");
/*  45: 89 */     CROBEX_TICKERS.add("KNZM-R-A");
/*  46: 90 */     CROBEX_TICKERS.add("KOEI-R-A");
/*  47:    */     
/*  48: 92 */     CROBEX_TICKERS.add("KORF-R-A");
/*  49: 93 */     CROBEX_TICKERS.add("KRAS-R-A");
/*  50: 94 */     CROBEX_TICKERS.add("LEDO-R-A");
/*  51: 95 */     CROBEX_TICKERS.add("LKPC-R-A");
/*  52: 96 */     CROBEX_TICKERS.add("PODR-R-A");
/*  53:    */     
/*  54: 98 */     CROBEX_TICKERS.add("PTKM-R-A");
/*  55: 99 */     CROBEX_TICKERS.add("THNK-R-A");
/*  56:100 */     CROBEX_TICKERS.add("ULPL-R-A");
/*  57:101 */     CROBEX_TICKERS.add("VIRO-R-A");
/*  58:102 */     CROBEX_TICKERS.add("ZABA-R-A");
/*  59:    */   }
/*  60:    */   
/*  61:105 */   public static List<String> CROBEX10_TICKERS = Arrays.asList(new String[] { "ADRS-P-A", "ATPL-R-A", "DLKV-R-A", "ERNT-R-A", "HT-R-A", "IGH-R-A", "INA-R-A", "INGR-R-A", "KOEI-R-A", "PODR-R-A" });
/*  62:116 */   public static List<String> FREQUENT_ZSE_TICKERS = Arrays.asList(new String[] { "ACI-R-A", "ADPL-R-A", "ADRS-P-A", "ADRS-R-A", "ARNT-R-A", "ATGR-R-A", "ATLN-R-A", "ATPL-R-A", "AUHR-R-A", "BDMR-R-A", "BLSC-R-A", "BLJE-R-A", "BPBA-R-A", "CKML-R-A", "CROS-R-A", "DDJH-R-A", "DIOK-R-A", "DKVS-R-A", "DLKV-R-A", "ELPR-R-A", "ERNT-R-A", "FNVC-R-A", "HDEL-R-A", "HGSP-R-A", "HIMR-R-A", "HT-R-A", "HTPK-R-A", "HUPZ-R-A", "IGH-R-A", "IMZV-R-A", "INA-R-A", "INDG-R-A", "INGR-R-A", "ISTT-R-A", "JDBA-R-A", "JDGT-R-A", "JDPL-R-A", "JNAF-R-A", "KABA-R-A", "KNZM-R-A", "KODT-R-A", "KOEI-R-A", "KOKA-R-A", "KORF-R-A", "KRAS-R-A", "KSST-R-A", "KTJV-R-A", "LEDO-R-A", "LKPC-R-A", "LKRI-R-A", "LPLH-R-A", "LRH-R-A", "LURA-R-A", "LVCV-R-A", "MLNR-R-A", "PBZ-R-A", "PLAG-R-A", "PODR-R-A", "PTKM-R-A", "RIVP-R-A", "RIZO-R-A", "SLPF-R-A", "SNBA-R-A", "SUNH-R-A", "THNK-R-A", "TISK-R-A", "TLM-R-A", "TNPL-R-A", "TNSA-R-A", "ULPL-R-A", "VDKT-R-A", "VERN-R-A", "VIRO-R-A", "VLDS-R-A", "VPIK-R-A", "ZABA-R-A", "ZTNJ-R-A", "ZVCV-R-A", "ZVZD-R-A" });
/*  63:120 */   public MarketMonitorAccount monitorAccount = null;
/*  64:121 */   public TradingAccount tradingAccount = null;
/*  65:122 */   public HistoryDataProvider historyProvider = null;
/*  66:128 */   public boolean isPolling = false;
/*  67:131 */   public List<String> interestingTickers = new ArrayList();
/*  68:132 */   public List<String> blackList = new ArrayList();
/*  69:133 */   public List<String> whiteList = new ArrayList();
/*  70:135 */   public List<String> highestPriorityTickers = new ArrayList();
/*  71:137 */   public List<String> highPriorityTickers = new ArrayList();
/*  72:139 */   public List<String> mediumPriorityTickers = new ArrayList();
/*  73:141 */   public List<String> lowPriorityTickers = new ArrayList();
/*  74:144 */   private List<StockMetrics> stockMetrics = new ArrayList();
/*  75:145 */   private List<StockMarketData> marketData = new ArrayList();
/*  76:146 */   private List<StockHistoryData> historyData = new ArrayList();
/*  77:153 */   private int runMode = 0;
/*  78:    */   public static final int DEFAULT_MARKET_DATA_COLELCTION_PERIOD = 120;
/*  79:    */   public static final int HIGHEST_PRIORITY_DATA_COLELCTION_PERIOD = 10;
/*  80:    */   public static final int HIGH_PRIORITY_DATA_COLELCTION_PERIOD = 30;
/*  81:    */   public static final int MEDIUM_PRIORITY_DATA_COLELCTION_PERIOD = 60;
/*  82:    */   public static final int LOW_PRIORITY_DATA_COLELCTION_PERIOD = 60;
/*  83:    */   public static final int HIGHEST_DATA_COLLECTION_PRIORITY = 0;
/*  84:    */   public static final int HIGH_DATA_COLLECTION_PRIORITY = 1;
/*  85:    */   public static final int MEDIUM_DATA_COLLECTION_PRIORITY = 2;
/*  86:    */   public static final int LOW_DATA_COLLECTION_PRIORITY = 3;
/*  87:    */   public static final float MINIMUM_AVERAGE_TURNOVER = 75000.0F;
/*  88:    */   public static final String DATE_FORMAT = "dd.M.yyyy HH:mm:ss";
/*  89:    */   public static final int ZSE_OPEN_HOUR = 10;
/*  90:    */   public static final int ZSE_CLOSE_HOUR = 16;
/*  91:    */   public static final int RUN_MODE_DATA_COLLECTION_ONLY = 0;
/*  92:    */   public static final int RUN_MODE_TRADING = 1;
/*  93:    */   public MarketSimulator marketSimulator;
/*  94:    */   private Timer simpleCollectionTimer;
/*  95:    */   private Timer highestPriorityCollectionTimer;
/*  96:    */   private Timer highPriorityCollectionTimer;
/*  97:    */   private Timer mediumPriorityCollectionTimer;
/*  98:    */   private Timer lowPriorityCollectionTimer;
/*  99:    */   
/* 100:    */   public DataCollector()
/* 101:    */   {
/* 102:159 */     this.monitorAccount = new FimaEtrade(this);
/* 103:160 */     this.tradingAccount = new VirtualnaBurza(this);
/* 104:161 */     this.historyProvider = ((HistoryDataProvider)this.tradingAccount);
/* 105:162 */     this.interestingTickers = FREQUENT_ZSE_TICKERS;
/* 106:163 */     this.isPolling = true;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public DataCollector(MarketSimulator ms, TradingAccount ta, SimulatedMarketMonitorAccount mma, String interestingTicker, boolean isPolling)
/* 110:    */   {
/* 111:173 */     this.marketSimulator = ms;
/* 112:174 */     this.monitorAccount = mma;
/* 113:175 */     this.tradingAccount = ta;
/* 114:176 */     this.historyProvider = new VirtualnaBurza(this);
/* 115:177 */     this.interestingTickers.add(interestingTicker);
/* 116:178 */     this.marketData.add(new StockMarketData(this, interestingTicker));
/* 117:179 */     if (!isPolling) {
/* 118:180 */       this.marketSimulator.marketDataSubscriptions.add(mma);
/* 119:    */     }
/* 120:182 */     this.isPolling = isPolling;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void run()
/* 124:    */   {
/* 125:187 */     prioritizeTickers();
/* 126:188 */     startPrioritizedDataCollection();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setRunMode(int runMode)
/* 130:    */   {
/* 131:192 */     this.runMode = runMode;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public synchronized List<StockMarketData> getMarketData()
/* 135:    */   {
/* 136:197 */     return this.marketData;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public PortfolioData getPortfolioData()
/* 140:    */   {
/* 141:205 */     return this.tradingAccount.readPortfolioData();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void prioritizeTickers()
/* 145:    */   {
/* 146:219 */     if (this.runMode == 1)
/* 147:    */     {
/* 148:220 */       this.tradingAccount.refreshTradingAccountData();
/* 149:    */       
/* 150:222 */       Iterator<Order> i = this.tradingAccount.ownOrders.iterator();
/* 151:223 */       while (i.hasNext())
/* 152:    */       {
/* 153:224 */         Order oo = (Order)i.next();
/* 154:225 */         if (oo.type == OrderType.SELL)
/* 155:    */         {
/* 156:226 */           this.highestPriorityTickers.add(oo.ticker);
/* 157:227 */           MainLogger.debug("Adding " + oo.ticker + " to highest priority tickers!!!");
/* 158:    */         }
/* 159:228 */         else if ((oo.type == OrderType.BUY) && (!this.highestPriorityTickers.contains(oo.ticker)))
/* 160:    */         {
/* 161:229 */           this.highPriorityTickers.add(oo.ticker);
/* 162:230 */           MainLogger.debug("Adding " + oo.ticker + " to high priority tickers!!!");
/* 163:    */         }
/* 164:    */       }
/* 165:235 */       Iterator<StockPortfolioData> j = this.tradingAccount.portfolio.stockInfo.iterator();
/* 166:236 */       while (j.hasNext())
/* 167:    */       {
/* 168:237 */         StockPortfolioData stockData = (StockPortfolioData)j.next();
/* 169:238 */         if ((!this.highestPriorityTickers.contains(stockData.ticker)) && 
/* 170:239 */           (!this.highPriorityTickers.contains(stockData.ticker)))
/* 171:    */         {
/* 172:240 */           this.highPriorityTickers.add(stockData.ticker);
/* 173:241 */           MainLogger.debug("Adding " + stockData.ticker + " to high priority tickers!!!");
/* 174:    */         }
/* 175:    */       }
/* 176:    */     }
/* 177:249 */     this.historyData = this.historyProvider.getHistoryDataForTickers(CROBEX_TICKERS);
/* 178:250 */     Iterator<StockHistoryData> k = this.historyData.iterator();
/* 179:251 */     while (k.hasNext())
/* 180:    */     {
/* 181:252 */       StockHistoryData stockHistory = (StockHistoryData)k.next();
/* 182:253 */       if ((!this.highestPriorityTickers.contains(stockHistory.ticker)) && 
/* 183:254 */         (!this.highPriorityTickers.contains(stockHistory.ticker))) {
/* 184:255 */         if ((stockHistory.averageDayValues.turnover >= 75000.0F) && 
/* 185:256 */           (!this.blackList.contains(stockHistory.ticker)))
/* 186:    */         {
/* 187:257 */           this.mediumPriorityTickers.add(stockHistory.ticker);
/* 188:258 */           MainLogger.debug("Adding " + stockHistory.ticker + " to medium priority tickers!!!");
/* 189:    */         }
/* 190:259 */         else if (!this.blackList.contains(stockHistory.ticker))
/* 191:    */         {
/* 192:260 */           this.lowPriorityTickers.add(stockHistory.ticker);
/* 193:261 */           MainLogger.debug("Adding " + stockHistory.ticker + " to low priority tickers!!!");
/* 194:    */         }
/* 195:    */         else
/* 196:    */         {
/* 197:263 */           MainLogger.info("Ticker " + stockHistory.ticker + " is on black list! Not collecting data for it.");
/* 198:    */         }
/* 199:    */       }
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void startPrioritizedDataCollection()
/* 204:    */   {
/* 205:273 */     MainLogger.debug("----------------------------------------");
/* 206:274 */     MainLogger.debug("Highest priority tickers:");
/* 207:275 */     List<String> highest = this.highestPriorityTickers;
/* 208:276 */     Iterator<String> i = highest.iterator();
/* 209:277 */     while (i.hasNext())
/* 210:    */     {
/* 211:278 */       String ticker = (String)i.next();
/* 212:279 */       MainLogger.debug(ticker);
/* 213:    */     }
/* 214:281 */     this.highestPriorityCollectionTimer = new Timer();
/* 215:282 */     this.highestPriorityCollectionTimer.schedule(new HighestPriorityCollectionTask(null), 0L, 
/* 216:283 */       10000L);
/* 217:    */     
/* 218:285 */     MainLogger.debug("High priority tickers:");
/* 219:286 */     List<String> high = this.highPriorityTickers;
/* 220:287 */     Iterator<String> j = high.iterator();
/* 221:288 */     while (j.hasNext())
/* 222:    */     {
/* 223:289 */       String ticker = (String)j.next();
/* 224:290 */       MainLogger.debug(ticker);
/* 225:    */     }
/* 226:292 */     this.highPriorityCollectionTimer = new Timer();
/* 227:293 */     this.highPriorityCollectionTimer.schedule(new HighPriorityCollectionTask(null), 0L, 
/* 228:294 */       30000L);
/* 229:    */     
/* 230:296 */     MainLogger.debug("Medium priority tickers:");
/* 231:297 */     List<String> medium = this.mediumPriorityTickers;
/* 232:298 */     Iterator<String> k = medium.iterator();
/* 233:299 */     while (k.hasNext())
/* 234:    */     {
/* 235:300 */       String ticker = (String)k.next();
/* 236:301 */       MainLogger.debug(ticker);
/* 237:    */     }
/* 238:303 */     this.mediumPriorityCollectionTimer = new Timer();
/* 239:304 */     this.mediumPriorityCollectionTimer.schedule(new MediumPriorityCollectionTask(null), 0L, 
/* 240:305 */       60000L);
/* 241:    */     
/* 242:307 */     MainLogger.debug("Low priority tickers:");
/* 243:308 */     List<String> low = this.lowPriorityTickers;
/* 244:309 */     Iterator<String> n = low.iterator();
/* 245:310 */     while (n.hasNext())
/* 246:    */     {
/* 247:311 */       String ticker = (String)n.next();
/* 248:312 */       MainLogger.debug(ticker);
/* 249:    */     }
/* 250:314 */     this.lowPriorityCollectionTimer = new Timer();
/* 251:315 */     this.lowPriorityCollectionTimer.schedule(new LowPriorityCollectionTask(null), 0L, 
/* 252:316 */       60000L);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public StockMarketData getStockMarketData(String ticker)
/* 256:    */   {
/* 257:321 */     StockMarketData smd = null;
/* 258:322 */     Iterator<StockMarketData> i = this.marketData.iterator();
/* 259:323 */     while (i.hasNext())
/* 260:    */     {
/* 261:324 */       StockMarketData temp = (StockMarketData)i.next();
/* 262:325 */       if (temp.ticker.equals(ticker))
/* 263:    */       {
/* 264:326 */         smd = temp;
/* 265:327 */         break;
/* 266:    */       }
/* 267:    */     }
/* 268:331 */     return smd;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void addStockMarketData(StockMarketData smd)
/* 272:    */   {
/* 273:335 */     this.marketData.add(smd);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public StockHistoryData getStockHistoryData(String ticker)
/* 277:    */   {
/* 278:339 */     StockHistoryData h = null;
/* 279:340 */     Iterator<StockHistoryData> i = this.historyData.iterator();
/* 280:341 */     while (i.hasNext())
/* 281:    */     {
/* 282:342 */       StockHistoryData temp = (StockHistoryData)i.next();
/* 283:343 */       if (temp.ticker.equals(ticker))
/* 284:    */       {
/* 285:344 */         h = temp;
/* 286:345 */         break;
/* 287:    */       }
/* 288:    */     }
/* 289:348 */     return h;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setStockHistoryData(StockHistoryData history)
/* 293:    */   {
/* 294:352 */     Iterator<StockHistoryData> i = this.historyData.iterator();
/* 295:353 */     while (i.hasNext())
/* 296:    */     {
/* 297:354 */       StockHistoryData temp = (StockHistoryData)i.next();
/* 298:355 */       if (temp.ticker.equals(history.ticker))
/* 299:    */       {
/* 300:356 */         i.remove();
/* 301:357 */         break;
/* 302:    */       }
/* 303:    */     }
/* 304:360 */     this.historyData.add(history);
/* 305:    */   }
/* 306:    */   
/* 307:    */   public StockPortfolioData getStockPortfolioData(String ticker)
/* 308:    */   {
/* 309:364 */     StockPortfolioData pd = null;
/* 310:    */     
/* 311:366 */     Iterator<StockPortfolioData> i = this.tradingAccount.portfolio.stockInfo.iterator();
/* 312:368 */     while (i.hasNext())
/* 313:    */     {
/* 314:369 */       StockPortfolioData pdTemp = (StockPortfolioData)i.next();
/* 315:370 */       if (pdTemp.ticker.equals(ticker))
/* 316:    */       {
/* 317:371 */         pd = pdTemp;
/* 318:372 */         break;
/* 319:    */       }
/* 320:    */     }
/* 321:375 */     if (pd == null) {
/* 322:376 */       MainLogger.info("Stock " + ticker + " is not part of the portfolio!");
/* 323:    */     }
/* 324:379 */     return pd;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void addInterestingTicker(String ticker)
/* 328:    */   {
/* 329:416 */     if (!this.interestingTickers.contains(ticker)) {
/* 330:417 */       this.interestingTickers.add(ticker);
/* 331:    */     }
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void removeNotInterestingTicker(String ticker)
/* 335:    */   {
/* 336:423 */     if (this.interestingTickers.contains(ticker)) {
/* 337:424 */       this.interestingTickers.remove(ticker);
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   public MarketMonitorAccount getMarketMonitorInstance()
/* 342:    */   {
/* 343:429 */     return this.monitorAccount;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public TradingAccount getTradingAccountInstance()
/* 347:    */   {
/* 348:433 */     return this.tradingAccount;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void startSimpleDataCollection()
/* 352:    */   {
/* 353:451 */     this.simpleCollectionTimer = new Timer();
/* 354:452 */     this.simpleCollectionTimer.schedule(new SimpleMarketDataCollectionTask(null), 0L, 
/* 355:453 */       120000L);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void startSimpleDataCollection(Date startTime)
/* 359:    */   {
/* 360:458 */     this.simpleCollectionTimer = new Timer();
/* 361:459 */     this.simpleCollectionTimer.schedule(new SimpleMarketDataCollectionTask(null), startTime, 
/* 362:460 */       120000L);
/* 363:    */   }
/* 364:    */   
/* 365:    */   private class SimpleMarketDataCollectionTask
/* 366:    */     extends TimerTask
/* 367:    */   {
/* 368:    */     private SimpleMarketDataCollectionTask() {}
/* 369:    */     
/* 370:    */     public void run()
/* 371:    */     {
/* 372:469 */       DataCollector.this.monitorAccount.refreshMarketDataFromWeb(DataCollector.this.interestingTickers);
/* 373:    */     }
/* 374:    */   }
/* 375:    */   
/* 376:    */   private class HighestPriorityCollectionTask
/* 377:    */     extends TimerTask
/* 378:    */   {
/* 379:483 */     boolean first = true;
/* 380:    */     
/* 381:    */     private HighestPriorityCollectionTask() {}
/* 382:    */     
/* 383:    */     public void run()
/* 384:    */     {
/* 385:487 */       if (this.first)
/* 386:    */       {
/* 387:488 */         Thread.currentThread().setName("Highest");
/* 388:489 */         Thread.currentThread().setPriority(8);
/* 389:490 */         this.first = false;
/* 390:    */       }
/* 391:493 */       DataCollector.this.monitorAccount.refreshMarketDataFromWeb(DataCollector.this.highestPriorityTickers);
/* 392:    */     }
/* 393:    */   }
/* 394:    */   
/* 395:    */   private class HighPriorityCollectionTask
/* 396:    */     extends TimerTask
/* 397:    */   {
/* 398:498 */     boolean first = true;
/* 399:    */     
/* 400:    */     private HighPriorityCollectionTask() {}
/* 401:    */     
/* 402:    */     public void run()
/* 403:    */     {
/* 404:502 */       if (this.first)
/* 405:    */       {
/* 406:503 */         Thread.currentThread().setName("High");
/* 407:504 */         Thread.currentThread().setPriority(7);
/* 408:505 */         this.first = false;
/* 409:    */       }
/* 410:507 */       DataCollector.this.monitorAccount.refreshMarketDataFromWeb(DataCollector.this.highPriorityTickers);
/* 411:    */     }
/* 412:    */   }
/* 413:    */   
/* 414:    */   private class MediumPriorityCollectionTask
/* 415:    */     extends TimerTask
/* 416:    */   {
/* 417:512 */     boolean first = true;
/* 418:    */     
/* 419:    */     private MediumPriorityCollectionTask() {}
/* 420:    */     
/* 421:    */     public void run()
/* 422:    */     {
/* 423:516 */       if (this.first)
/* 424:    */       {
/* 425:517 */         Thread.currentThread().setName("Medium");
/* 426:518 */         Thread.currentThread().setPriority(6);
/* 427:519 */         this.first = false;
/* 428:    */       }
/* 429:521 */       ParasiteUtils.printMemoryStatistics();
/* 430:522 */       DataCollector.this.monitorAccount.refreshMarketDataFromWeb(DataCollector.this.mediumPriorityTickers);
/* 431:    */     }
/* 432:    */   }
/* 433:    */   
/* 434:    */   private class LowPriorityCollectionTask
/* 435:    */     extends TimerTask
/* 436:    */   {
/* 437:527 */     boolean first = true;
/* 438:    */     
/* 439:    */     private LowPriorityCollectionTask() {}
/* 440:    */     
/* 441:    */     public void run()
/* 442:    */     {
/* 443:531 */       if (this.first)
/* 444:    */       {
/* 445:532 */         Thread.currentThread().setName("Low");
/* 446:533 */         Thread.currentThread().setPriority(6);
/* 447:534 */         this.first = false;
/* 448:    */       }
/* 449:536 */       ParasiteUtils.printMemoryStatistics();
/* 450:537 */       DataCollector.this.monitorAccount.refreshMarketDataFromWeb(DataCollector.this.lowPriorityTickers);
/* 451:    */     }
/* 452:    */   }
/* 453:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.DataCollector
 * JD-Core Version:    0.7.0.1
 */