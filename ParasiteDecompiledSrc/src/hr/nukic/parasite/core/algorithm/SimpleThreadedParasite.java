/*   1:    */ package hr.nukic.parasite.core.algorithm;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.accounts.templates.HistoryDataProvider;
/*   4:    */ import hr.nukic.parasite.accounts.templates.MarketMonitorAccount;
/*   5:    */ import hr.nukic.parasite.accounts.templates.TradingAccount;
/*   6:    */ import hr.nukic.parasite.core.DataCollector;
/*   7:    */ import hr.nukic.parasite.core.ImplicitStockMarketData;
/*   8:    */ import hr.nukic.parasite.core.Order;
/*   9:    */ import hr.nukic.parasite.core.OrderType;
/*  10:    */ import hr.nukic.parasite.core.PortfolioData;
/*  11:    */ import hr.nukic.parasite.core.StockDaySummary;
/*  12:    */ import hr.nukic.parasite.core.StockHistoryData;
/*  13:    */ import hr.nukic.parasite.core.StockMarketData;
/*  14:    */ import hr.nukic.parasite.core.StockPortfolioData;
/*  15:    */ import hr.nukic.parasite.core.StockState;
/*  16:    */ import hr.nukic.parasite.core.Transaction;
/*  17:    */ import hr.nukic.parasite.core.algorithm.configurations.AlgorithmConfiguration;
/*  18:    */ import hr.nukic.parasite.core.algorithm.configurations.Configurable;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.Iterator;
/*  22:    */ import java.util.List;
/*  23:    */ import nukic.parasite.utils.MainLogger;
/*  24:    */ 
/*  25:    */ public final class SimpleThreadedParasite
/*  26:    */   extends ThreadedTradingAlgorithm
/*  27:    */ {
/*  28:    */   public static final int INSTANT_SELL_INDICATOR = 0;
/*  29:    */   public static final int PROFITABLE_SELL_INDICATOR = 1;
/*  30:    */   public static final float MAX_TRY_NUM = 10.0F;
/*  31:    */   public static final float BUY_REFRESH_PERIOD = 1.0F;
/*  32:    */   
/*  33:    */   public void fillConfigurableNameList()
/*  34:    */   {
/*  35: 33 */     MainLogger.debug("Filling configurable name list...");
/*  36:    */     
/*  37: 35 */     this.configurableNames.add("MIN_BA_DELTA");
/*  38: 36 */     this.configurableNames.add("MIN_BA_RATIO");
/*  39: 37 */     this.configurableNames.add("DAMAGE_CONTROL_TRIGGER_PERCENTAGE");
/*  40: 38 */     this.configurableNames.add("PROFITABLE_SELL_TRIGGER_PERCENTAGE");
/*  41: 39 */     this.configurableNames.add("PROFITABLE_SELL_TRIGGER_VALUE");
/*  42: 40 */     this.configurableNames.add("MIN_AVERAGE_DAY_TURNOVER");
/*  43: 41 */     this.configurableNames.add("OWN_BID_VALUE");
/*  44: 42 */     this.configurableNames.add("RATIO_WEIGHT");
/*  45: 43 */     this.configurableNames.add("DELTA_WEIGHT");
/*  46:    */   }
/*  47:    */   
/*  48: 47 */   float baRatioWma = 0.0F;
/*  49: 48 */   float baRatioWmaDelta = 0.0F;
/*  50: 49 */   float baRatioSma = 0.0F;
/*  51: 50 */   float baRatioSmaDelta = 0.0F;
/*  52:    */   TradingAccount tradingAccount;
/*  53:    */   
/*  54:    */   public SimpleThreadedParasite(DataCollector dc, String ticker, AlgorithmConfiguration config)
/*  55:    */   {
/*  56: 56 */     super(dc, StockState.NOT_INTERESTING, ticker, config);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public SimpleThreadedParasite(DataCollector dc, StockState stockState, String ticker, AlgorithmConfiguration config)
/*  60:    */   {
/*  61: 60 */     super(dc, stockState, ticker, config);
/*  62:    */   }
/*  63:    */   
/*  64:    */   private float getAverageTurnover()
/*  65:    */   {
/*  66: 64 */     StockHistoryData historyData = this.dataCollector.getStockHistoryData(this.ticker);
/*  67: 65 */     historyData.calculateAverageDayValues();
/*  68: 66 */     return historyData.averageDayValues.turnover;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private float getCurrentTurnover()
/*  72:    */   {
/*  73: 71 */     return this.dataCollector.getStockMarketData(this.ticker).calculateTurnover();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void processSellCandidateState() {}
/*  77:    */   
/*  78:    */   public void processNotInterestingState()
/*  79:    */   {
/*  80: 83 */     float averageTurnover = getAverageTurnover();
/*  81: 84 */     float currentTurnover = getCurrentTurnover();
/*  82: 85 */     List<String> blackList = this.dataCollector.blackList;
/*  83: 86 */     List<String> whiteList = this.dataCollector.whiteList;
/*  84:    */     
/*  85:    */ 
/*  86: 89 */     MainLogger.debug(this.config.getConfigurableByName("MIN_AVERAGE_DAY_TURNOVER").value);
/*  87: 91 */     if (((averageTurnover > this.config.getConfigurableByName("MIN_AVERAGE_DAY_TURNOVER").value) && 
/*  88: 92 */       (!blackList.contains(this.ticker))) || 
/*  89: 93 */       (whiteList.contains(this.ticker)) || 
/*  90: 94 */       (currentTurnover > this.config.getConfigurableByName("MIN_AVERAGE_DAY_TURNOVER").value))
/*  91:    */     {
/*  92: 95 */       MainLogger.info("STATE CHANGE: From NOT_INTERESTING to UNDER_WATCH!");
/*  93: 96 */       this.stockState = StockState.UNDER_WATCH;
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void processProfitableState()
/*  98:    */   {
/*  99:115 */     refreshPortfolioDataIfNecessary();
/* 100:116 */     calculateNetProfits();
/* 101:118 */     if ((this.relativeProfit >= this.config.getConfigurableByName("PROFITABLE_SELL_TRIGGER_PERCENTAGE").value) || 
/* 102:119 */       (this.absoluteProfit >= this.config.getConfigurableByName("PROFITABLE_SELL_TRIGGER_VALUE").value)) {
/* 103:121 */       if (!this.dataCollector.tradingAccount.hasPendingSellOrdersForTicker(this.ticker)) {
/* 104:122 */         generateSellOrder(1.0F);
/* 105:    */       }
/* 106:    */     }
/* 107:129 */     sanityCheck();
/* 108:    */     
/* 109:131 */     createSellReport();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void processNotProfitableState()
/* 113:    */   {
/* 114:139 */     float buyPrice = 0.0F;float marketPrice = 0.0F;
/* 115:140 */     boolean stockFound = false;
/* 116:141 */     List<StockPortfolioData> ownedStocks = this.dataCollector.tradingAccount.readPortfolioData().stockInfo;
/* 117:142 */     Iterator<StockPortfolioData> i = ownedStocks.iterator();
/* 118:143 */     while (i.hasNext())
/* 119:    */     {
/* 120:144 */       StockPortfolioData spd = (StockPortfolioData)i.next();
/* 121:145 */       if (spd.ticker.equals(this.ticker))
/* 122:    */       {
/* 123:146 */         buyPrice = spd.buyPrice;
/* 124:147 */         marketPrice = spd.marketPrice;
/* 125:148 */         stockFound = true;
/* 126:    */       }
/* 127:    */     }
/* 128:151 */     if (!stockFound)
/* 129:    */     {
/* 130:153 */       MainLogger.info("Did not found stock in portfolio that should be owned! It has probably been sold or error occured !");
/* 131:154 */       MainLogger.info("STATE CHANGE: From NOT_PROFITABLE to UNDER_WATCH!");
/* 132:155 */       this.stockState = StockState.UNDER_WATCH;
/* 133:156 */       return;
/* 134:    */     }
/* 135:159 */     MainLogger.debug("Market price = " + marketPrice + " Buy price = " + buyPrice);
/* 136:161 */     if (marketPrice > buyPrice)
/* 137:    */     {
/* 138:162 */       MainLogger.info("STATE CHANGE: From NOT_PROFITABLE to PROFITABLE!");
/* 139:163 */       this.stockState = StockState.OWNED_PROFITABLE;
/* 140:164 */       return;
/* 141:    */     }
/* 142:166 */     if (marketPrice < buyPrice * (1.0F - this.config.getConfigurableByName("DAMAGE_CONTROL_TRIGGER_PERCENTAGE").value / 100.0F))
/* 143:    */     {
/* 144:167 */       generateSellOrder(0.0F);
/* 145:169 */       if (this.dataCollector.tradingAccount.hasPendingBuyOrdersForTicker(this.ticker)) {
/* 146:170 */         this.dataCollector.tradingAccount.cancelBuyOrder(this.ticker);
/* 147:    */       }
/* 148:172 */       return;
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void processBuyCandidateState()
/* 153:    */   {
/* 154:180 */     StockMarketData smd = this.dataCollector.getStockMarketData(this.ticker);
/* 155:181 */     ImplicitStockMarketData imd2 = smd.implicitData;
/* 156:182 */     this.baRatioWma = imd2.getBaRatioWma();
/* 157:183 */     this.baRatioWmaDelta = imd2.getBaRatioWmaDelta();
/* 158:    */     
/* 159:    */ 
/* 160:    */ 
/* 161:187 */     MainLogger.debug("Current BA_RATIO = " + this.baRatioWma);
/* 162:188 */     MainLogger.debug("Current BA_RATIO_DELTA = " + this.baRatioWmaDelta);
/* 163:    */     
/* 164:190 */     int amountPending = getAmountInPendingBuyOrdersForTicker(this.ticker);
/* 165:193 */     if (amountPending == 0)
/* 166:    */     {
/* 167:194 */       MainLogger.info("STATE CHANGE: From BUY CANDIDATE to NOT_PROFITABLE!");
/* 168:195 */       this.stockState = StockState.OWNED_NOT_PROFITABLE;
/* 169:    */     }
/* 170:    */     else
/* 171:    */     {
/* 172:201 */       refreshMarketDataIfNecessary();
/* 173:    */       
/* 174:    */ 
/* 175:204 */       ImplicitStockMarketData imd = this.dataCollector.getStockMarketData(this.ticker).implicitData;
/* 176:205 */       this.baRatioWma = imd.getBaRatioWma();
/* 177:209 */       if (this.baRatioWma <= this.config.getConfigurableByName("MIN_BA_RATIO").value * 0.75F)
/* 178:    */       {
/* 179:211 */         this.dataCollector.tradingAccount.cancelBuyOrder(this.ticker);
/* 180:212 */         int cancelCheckCounter = 0;
/* 181:214 */         while (this.dataCollector.tradingAccount.hasPendingBuyOrdersForTicker(this.ticker))
/* 182:    */         {
/* 183:215 */           this.dataCollector.tradingAccount.refreshTradingAccountData();
/* 184:216 */           cancelCheckCounter++;
/* 185:217 */           if (cancelCheckCounter == 10.0F)
/* 186:    */           {
/* 187:218 */             MainLogger.error("Did not succed to cancel buy order after 10.0 trys! Giving up!");
/* 188:    */             
/* 189:220 */             MainLogger.error("Stopping trading algorithm thread for ticker " + this.ticker + " !");
/* 190:221 */             super.stop();
/* 191:222 */             return;
/* 192:    */           }
/* 193:    */         }
/* 194:226 */         if (this.dataCollector.tradingAccount.portfolio.isTickerOwned(this.ticker))
/* 195:    */         {
/* 196:227 */           MainLogger.info("STATE CHANGE: From BUY CANDIDATE to NOT_PROFITABLE!");
/* 197:228 */           this.stockState = StockState.OWNED_NOT_PROFITABLE;
/* 198:    */         }
/* 199:    */         else
/* 200:    */         {
/* 201:232 */           MainLogger.info("STATE CHANGE: From BUY CANDIDATE to UNDER_WATCH!");
/* 202:233 */           this.stockState = StockState.UNDER_WATCH;
/* 203:    */         }
/* 204:    */       }
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void processUnderWatchState()
/* 209:    */   {
/* 210:245 */     StockMarketData smd = this.dataCollector.getStockMarketData(this.ticker);
/* 211:246 */     ImplicitStockMarketData imd = smd.implicitData;
/* 212:247 */     this.baRatioWma = imd.getBaRatioWma();
/* 213:248 */     this.baRatioWmaDelta = imd.getBaRatioWmaDelta();
/* 214:253 */     if (!smd.transactionList.isEmpty()) {
/* 215:254 */       MainLogger.debug("Market price = " + ((Transaction)smd.transactionList.get(0)).price);
/* 216:    */     }
/* 217:257 */     float RATIO_WEIGHT = this.config.getConfigurableByName("RATIO_WEIGHT").value;
/* 218:258 */     float MIN_BA_RATIO = this.config.getConfigurableByName("MIN_BA_RATIO").value;
/* 219:259 */     float DELTA_WEIGHT = this.config.getConfigurableByName("DELTA_WEIGHT").value;
/* 220:260 */     float MIN_BA_DELTA = this.config.getConfigurableByName("MIN_BA_DELTA").value;
/* 221:    */     
/* 222:    */ 
/* 223:263 */     MainLogger.debug("Current BA_RATIO = " + this.baRatioWma + "(Triger value = " + MIN_BA_RATIO + ")");
/* 224:264 */     MainLogger.debug("Current BA_RATIO_DELTA = " + this.baRatioWmaDelta + "(Trigger value = " + MIN_BA_DELTA + ")");
/* 225:268 */     if ((this.baRatioWma >= this.config.getConfigurableByName("MIN_BA_RATIO").value) && 
/* 226:269 */       (this.baRatioWmaDelta >= this.config.getConfigurableByName("MIN_BA_DELTA").value))
/* 227:    */     {
/* 228:270 */       MainLogger.info("-------> BUY TRIGGER OCCURED!!!");
/* 229:    */       
/* 230:272 */       float buyIndicator = RATIO_WEIGHT * (this.baRatioWma / MIN_BA_RATIO) * DELTA_WEIGHT * (
/* 231:273 */         this.baRatioWmaDelta / MIN_BA_DELTA);
/* 232:    */       
/* 233:275 */       generateBuyOrder(buyIndicator);
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void generateBuyOrder(float buyIndicator)
/* 238:    */   {
/* 239:282 */     TradingAccount tradingAccount = this.dataCollector.tradingAccount;
/* 240:283 */     StockMarketData marketData = this.dataCollector.getStockMarketData(this.ticker);
/* 241:284 */     if (marketData == null)
/* 242:    */     {
/* 243:285 */       marketData = this.dataCollector.monitorAccount.readMarketDataFromWeb(this.ticker);
/* 244:286 */       if (marketData.bidList.isEmpty())
/* 245:    */       {
/* 246:287 */         MainLogger.error("Empty bid list! Could not generate buy order!");
/* 247:288 */         return;
/* 248:    */       }
/* 249:    */     }
/* 250:291 */     if (!tradingAccount.readPortfolioData().isTickerOwned(this.ticker))
/* 251:    */     {
/* 252:292 */       if (!tradingAccount.hasPendingBuyOrdersForTicker(this.ticker))
/* 253:    */       {
/* 254:293 */         MainLogger.info("Generating buy order...");
/* 255:294 */         float availableCash = tradingAccount.readAvailableCash();
/* 256:295 */         float reservedCash = tradingAccount.readCashInPendingBuyOrders();
/* 257:    */         
/* 258:297 */         float OWN_BID_VALUE = this.config.getConfigurableByName("OWN_BID_VALUE").value;
/* 259:299 */         if (availableCash - reservedCash > OWN_BID_VALUE)
/* 260:    */         {
/* 261:300 */           float price = ((Order)marketData.bidList.get(0)).price;
/* 262:301 */           int amount = calculateOrderAmount(price, OWN_BID_VALUE);
/* 263:303 */           if (amount > 0)
/* 264:    */           {
/* 265:304 */             tradingAccount.setBuyOrder(this.ticker, price, amount, null);
/* 266:    */             
/* 267:306 */             int buyOrderCheckCounter = 0;
/* 268:307 */             while (!this.dataCollector.tradingAccount.hasPendingBuyOrdersForTicker(this.ticker))
/* 269:    */             {
/* 270:309 */               this.dataCollector.tradingAccount.refreshTradingAccountData();
/* 271:310 */               buyOrderCheckCounter++;
/* 272:311 */               if (buyOrderCheckCounter == 10.0F)
/* 273:    */               {
/* 274:312 */                 MainLogger.error("Did not succed to set buy order after 10.0 trys! Giving up!");
/* 275:    */                 
/* 276:314 */                 MainLogger.error("Stopping trading algorithm thread for ticker " + this.ticker + " !");
/* 277:315 */                 super.stop();
/* 278:316 */                 return;
/* 279:    */               }
/* 280:    */             }
/* 281:320 */             this.stockState = StockState.BUY_CANDIDATE;
/* 282:    */           }
/* 283:    */           else
/* 284:    */           {
/* 285:322 */             MainLogger.error(
/* 286:323 */               "Could not set buy trigger for ticker " + this.ticker + "REASON: Own bid value is not enough to buy even one stock!");
/* 287:324 */             MainLogger.error("Suggestion: Increase own bid value!");
/* 288:325 */             super.stop();
/* 289:    */           }
/* 290:    */         }
/* 291:    */       }
/* 292:    */       else
/* 293:    */       {
/* 294:331 */         MainLogger.info("Buy order not set because there are already pending buy orders for this stock!");
/* 295:332 */         MainLogger.info("------> Switching to BUY_CANDIDATE state!");
/* 296:333 */         this.stockState = StockState.BUY_CANDIDATE;
/* 297:    */       }
/* 298:    */     }
/* 299:    */     else
/* 300:    */     {
/* 301:336 */       MainLogger.info("Buy order not set because stock already owned!");
/* 302:337 */       MainLogger.info("-----> Switching to OWNED_NOT_PROFITABLE state!");
/* 303:338 */       this.stockState = StockState.OWNED_NOT_PROFITABLE;
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void generateSellOrder(float sellIndicator)
/* 308:    */   {
/* 309:347 */     if (sellIndicator == 0.0F) {
/* 310:348 */       while (!hasBeenSold()) {
/* 311:    */         try
/* 312:    */         {
/* 313:350 */           Thread.sleep(1000L);
/* 314:    */         }
/* 315:    */         catch (InterruptedException e)
/* 316:    */         {
/* 317:352 */           MainLogger.error(e);
/* 318:    */         }
/* 319:    */       }
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   private void createSellReport() {}
/* 324:    */   
/* 325:    */   private boolean sanityCheck()
/* 326:    */   {
/* 327:375 */     return false;
/* 328:    */   }
/* 329:    */   
/* 330:    */   private void calculateNetProfits()
/* 331:    */   {
/* 332:381 */     StockPortfolioData portfolioData = null;
/* 333:382 */     Iterator i = this.dataCollector.tradingAccount.portfolio.stockInfo.iterator();
/* 334:383 */     while (i.hasNext())
/* 335:    */     {
/* 336:384 */       StockPortfolioData spd = (StockPortfolioData)i.next();
/* 337:385 */       if (spd.ticker.equals(this.ticker)) {
/* 338:386 */         portfolioData = spd;
/* 339:    */       }
/* 340:    */     }
/* 341:390 */     float marketValueAfterSell = portfolioData.marketValue - 
/* 342:391 */       this.dataCollector.tradingAccount.calculateFee(portfolioData.marketValue);
/* 343:392 */     this.absoluteProfit = (marketValueAfterSell - portfolioData.buyValue);
/* 344:393 */     this.relativeProfit = (this.absoluteProfit / portfolioData.buyValue * 100.0F);
/* 345:    */   }
/* 346:    */   
/* 347:    */   private void refreshPortfolioDataIfNecessary()
/* 348:    */   {
/* 349:397 */     Date portfolioLastRefresh = this.dataCollector.tradingAccount.portfolio.lastRefresh;
/* 350:398 */     Date now = new Date();
/* 351:399 */     Date minuteAgo = new Date(now.getTime() - 60000L);
/* 352:400 */     if (portfolioLastRefresh.getTime() < minuteAgo.getTime()) {
/* 353:401 */       this.dataCollector.tradingAccount.refreshTradingAccountData();
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   private int getAmountInPendingBuyOrdersForTicker(String myTicker)
/* 358:    */   {
/* 359:408 */     int amountPending = 0;
/* 360:    */     
/* 361:410 */     List<Order> partiallyCompleteOrders = this.dataCollector.tradingAccount.readPendingOrdersInfo();
/* 362:411 */     Iterator<Order> n = partiallyCompleteOrders.iterator();
/* 363:412 */     while (n.hasNext())
/* 364:    */     {
/* 365:413 */       Order o = (Order)n.next();
/* 366:414 */       if ((o.ticker.equals(myTicker)) && (o.type == OrderType.BUY)) {
/* 367:415 */         amountPending = o.amount;
/* 368:    */       }
/* 369:    */     }
/* 370:418 */     return amountPending;
/* 371:    */   }
/* 372:    */   
/* 373:    */   private int getAmountInPendingSellOrdersForTicker(String myTicker)
/* 374:    */   {
/* 375:422 */     int amountPending = 0;
/* 376:    */     
/* 377:424 */     List<Order> partiallyCompleteOrders = this.dataCollector.tradingAccount.readPendingOrdersInfo();
/* 378:425 */     Iterator<Order> n = partiallyCompleteOrders.iterator();
/* 379:426 */     while (n.hasNext())
/* 380:    */     {
/* 381:427 */       Order o = (Order)n.next();
/* 382:428 */       if ((o.ticker.equals(myTicker)) && (o.type == OrderType.SELL)) {
/* 383:429 */         amountPending = o.amount;
/* 384:    */       }
/* 385:    */     }
/* 386:432 */     return amountPending;
/* 387:    */   }
/* 388:    */   
/* 389:    */   private void refreshMarketDataIfNecessary()
/* 390:    */   {
/* 391:436 */     Date lastRefresh = this.dataCollector.getStockMarketData(this.ticker).time;
/* 392:437 */     Date now = new Date();
/* 393:439 */     if ((float)(now.getTime() - lastRefresh.getTime()) > 60.0F) {
/* 394:441 */       this.dataCollector.monitorAccount.readMarketDataFromWeb(this.ticker);
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   private int calculateOrderAmount(float price, float moneyToInvest)
/* 399:    */   {
/* 400:447 */     int amount = (int)(moneyToInvest / price);
/* 401:448 */     float fee = this.dataCollector.tradingAccount.calculateFee(price * amount);
/* 402:452 */     while (amount * price + fee > moneyToInvest)
/* 403:    */     {
/* 404:453 */       amount--;
/* 405:454 */       fee = this.dataCollector.tradingAccount.calculateFee(price * amount);
/* 406:    */     }
/* 407:456 */     assert (amount * price + fee <= moneyToInvest);
/* 408:457 */     return amount;
/* 409:    */   }
/* 410:    */   
/* 411:    */   private boolean isInLast24Hours(Date toCheck)
/* 412:    */   {
/* 413:461 */     Date now = new Date();
/* 414:462 */     if (now.getTime() - toCheck.getTime() < 86400000L) {
/* 415:463 */       return true;
/* 416:    */     }
/* 417:465 */     return false;
/* 418:    */   }
/* 419:    */   
/* 420:    */   private boolean isProfitable()
/* 421:    */   {
/* 422:470 */     float buyPrice = 0.0F;float marketPrice = 0.0F;
/* 423:471 */     boolean stockFound = false;
/* 424:472 */     List<StockPortfolioData> ownedStocks = this.dataCollector.tradingAccount.readPortfolioData().stockInfo;
/* 425:473 */     Iterator<StockPortfolioData> i = ownedStocks.iterator();
/* 426:474 */     while (i.hasNext())
/* 427:    */     {
/* 428:475 */       StockPortfolioData spd = (StockPortfolioData)i.next();
/* 429:476 */       if (spd.ticker.equals(this.ticker))
/* 430:    */       {
/* 431:477 */         buyPrice = spd.buyPrice;
/* 432:478 */         marketPrice = spd.marketPrice;
/* 433:479 */         stockFound = true;
/* 434:    */       }
/* 435:    */     }
/* 436:483 */     if (!stockFound)
/* 437:    */     {
/* 438:485 */       MainLogger.error("Checking profitability of stock that is not owned! This may happen because stock is sold or due to an error!");
/* 439:486 */       this.stockState = StockState.UNDER_WATCH;
/* 440:487 */       return false;
/* 441:    */     }
/* 442:489 */     if (marketPrice > buyPrice) {
/* 443:490 */       return true;
/* 444:    */     }
/* 445:492 */     return false;
/* 446:    */   }
/* 447:    */   
/* 448:    */   private boolean hasBeenSold()
/* 449:    */   {
/* 450:499 */     return false;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void prepareForExecutingSellCandidateState() {}
/* 454:    */   
/* 455:    */   public void prepareForExecutingProfitableState() {}
/* 456:    */   
/* 457:    */   public void prepareForExecutingNotProfitableState() {}
/* 458:    */   
/* 459:    */   public void prepareForExecutingBuyCandidateState() {}
/* 460:    */   
/* 461:    */   public void prepareForExecutingUnderWatchState() {}
/* 462:    */   
/* 463:    */   public void prepareForExecutingNotInterestingState()
/* 464:    */   {
/* 465:535 */     if (this.dataCollector.getStockHistoryData(this.ticker) == null)
/* 466:    */     {
/* 467:536 */       List<String> tickers = new ArrayList(1);
/* 468:537 */       tickers.add(this.ticker);
/* 469:538 */       this.dataCollector.setStockHistoryData(
/* 470:539 */         (StockHistoryData)this.dataCollector.historyProvider.getHistoryDataForTickers(tickers).get(0));
/* 471:    */     }
/* 472:    */   }
/* 473:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.SimpleThreadedParasite
 * JD-Core Version:    0.7.0.1
 */