/*   1:    */ package hr.nukic.parasite.core.algorithm.configurations;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.accounts.SimulatedMarketMonitorAccount;
/*   4:    */ import hr.nukic.parasite.accounts.SimulatedTradingAccount;
/*   5:    */ import hr.nukic.parasite.core.DataCollector;
/*   6:    */ import hr.nukic.parasite.core.Order;
/*   7:    */ import hr.nukic.parasite.core.ParasiteManager;
/*   8:    */ import hr.nukic.parasite.core.PortfolioData;
/*   9:    */ import hr.nukic.parasite.core.StockMarketData;
/*  10:    */ import hr.nukic.parasite.core.StockPortfolioData;
/*  11:    */ import hr.nukic.parasite.core.Transaction;
/*  12:    */ import hr.nukic.parasite.core.algorithm.SimpleThreadedParasite;
/*  13:    */ import hr.nukic.parasite.simulator.CsvFileMarketSimulator;
/*  14:    */ import java.text.Format;
/*  15:    */ import java.text.SimpleDateFormat;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Properties;
/*  20:    */ import nukic.parasite.utils.MainLogger;
/*  21:    */ 
/*  22:    */ public class ConfigurationEvaluation
/*  23:    */ {
/*  24:    */   public String ticker;
/*  25:    */   public AlgorithmConfiguration configToBeEvaluated;
/*  26:    */   public String csvFilePath;
/*  27:    */   public PortfolioData portfolio;
/*  28:    */   public CsvFileMarketSimulator simulator;
/*  29: 33 */   public AlgorithmScore score = null;
/*  30:    */   private SimulatedTradingAccount tradingAccount;
/*  31:    */   private SimulatedMarketMonitorAccount monitorAccount;
/*  32: 37 */   private float feePercent = 0.3F;
/*  33: 38 */   private float minFeeValue = 0.0F;
/*  34: 39 */   private int timeScale = 10000;
/*  35: 40 */   private int startAmount = 40000;
/*  36:    */   
/*  37:    */   public ConfigurationEvaluation(AlgorithmConfiguration configToBeEvaluated, String csvFilePath)
/*  38:    */   {
/*  39: 43 */     this.configToBeEvaluated = configToBeEvaluated;
/*  40: 44 */     this.csvFilePath = csvFilePath;
/*  41:    */     
/*  42: 46 */     this.timeScale = Integer.parseInt(ParasiteManager.getInstance().properties
/*  43: 47 */       .getProperty("EVALUATION_SIMULATOR_TIME_SCALE"));
/*  44: 48 */     this.startAmount = Integer.parseInt(ParasiteManager.getInstance().properties
/*  45: 49 */       .getProperty("PORTFOLIO_EVALUATION_STARTING_AMOUNT"));
/*  46: 50 */     this.feePercent = Float.parseFloat(ParasiteManager.getInstance().properties
/*  47: 51 */       .getProperty("SIMULATED_TRADING_ACCOUNT_FEE_PERCENTAGE"));
/*  48: 52 */     this.minFeeValue = Float.parseFloat(ParasiteManager.getInstance().properties
/*  49: 53 */       .getProperty("SIMULATED_TRADING_ACCOUNT_FEE_MIN_VALUE"));
/*  50: 54 */     this.simulator = new CsvFileMarketSimulator(csvFilePath, this.timeScale);
/*  51: 55 */     this.ticker = this.simulator.ticker;
/*  52: 56 */     this.portfolio = new PortfolioData(new ArrayList(), this.startAmount);
/*  53: 57 */     this.tradingAccount = new SimulatedTradingAccount(this.portfolio, new ArrayList(), this.simulator, 
/*  54: 58 */       this.feePercent, this.minFeeValue);
/*  55: 59 */     this.monitorAccount = new SimulatedMarketMonitorAccount(this.simulator);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void startEvaluation()
/*  59:    */   {
/*  60: 64 */     Thread.currentThread().setName("EvaluationThread_" + this.configToBeEvaluated.id);
/*  61: 65 */     MainLogger.info("\n\n\n-----------------------------------------------------------------------------------------------------");
/*  62: 66 */     MainLogger.info("Evaluating algorithm configuration \n" + this.configToBeEvaluated.toString() + " on csv file " + 
/*  63: 67 */       this.csvFilePath + "\n");
/*  64:    */     
/*  65:    */ 
/*  66: 70 */     DataCollector dc = new DataCollector(this.simulator, this.tradingAccount, this.monitorAccount, this.ticker, false);
/*  67: 71 */     this.monitorAccount.setDataCollector(dc);
/*  68:    */     
/*  69:    */ 
/*  70: 74 */     SimpleThreadedParasite algo = new SimpleThreadedParasite(dc, this.ticker, this.configToBeEvaluated);
/*  71: 75 */     algo.prepareForExecuting();
/*  72: 76 */     Thread simpleParasiteThread = new Thread(algo);
/*  73: 77 */     simpleParasiteThread.setPriority(5);
/*  74: 78 */     simpleParasiteThread.start();
/*  75:    */     
/*  76:    */ 
/*  77: 81 */     Thread simulatorThread = new Thread(this.simulator);
/*  78: 82 */     simulatorThread.start();
/*  79: 84 */     synchronized (algo)
/*  80:    */     {
/*  81:    */       try
/*  82:    */       {
/*  83: 86 */         MainLogger.info("Waiting until algorithm thread is finished!");
/*  84: 87 */         algo.wait();
/*  85:    */       }
/*  86:    */       catch (InterruptedException e)
/*  87:    */       {
/*  88: 90 */         MainLogger.error("Waiting for algorithm end is interupted!");
/*  89: 91 */         throw new RuntimeException(e);
/*  90:    */       }
/*  91:    */     }
/*  92: 95 */     MainLogger.debug("\n\n\nWaiting for SimpleParasiteThread to fisnih is over!");
/*  93:    */     
/*  94: 97 */     calculateScore();
/*  95: 98 */     MainLogger.debug("\n-----------------------------------------------------------------------------------------------------\n\n\n");
/*  96: 99 */     System.gc();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void calculateScore()
/* 100:    */   {
/* 101:112 */     float portfolioValue = 0.0F;
/* 102:113 */     boolean soldAllStocks = false;
/* 103:114 */     float referentEarnings = calculateReferenceScore();
/* 104:116 */     if (this.portfolio.stockInfo.size() > 1)
/* 105:    */     {
/* 106:117 */       MainLogger.error("Configuration evaluation portfolio should contain only one owned stock!");
/* 107:118 */       return;
/* 108:    */     }
/* 109:119 */     if (this.portfolio.stockInfo.size() == 1)
/* 110:    */     {
/* 111:120 */       StockPortfolioData spd = (StockPortfolioData)this.portfolio.stockInfo.get(0);
/* 112:121 */       int amount = spd.amount;
/* 113:122 */       float highestBidPriceOnMarketDayEnd = ((Order)this.simulator.lastRecord.bidList.get(0)).price;
/* 114:    */       
/* 115:124 */       float orderValue = amount * highestBidPriceOnMarketDayEnd;
/* 116:125 */       portfolioValue = this.portfolio.cash + orderValue - this.tradingAccount.calculateFee(orderValue);
/* 117:    */     }
/* 118:    */     else
/* 119:    */     {
/* 120:127 */       portfolioValue = this.portfolio.cash;
/* 121:128 */       soldAllStocks = true;
/* 122:    */     }
/* 123:131 */     float earnings = portfolioValue - this.startAmount;
/* 124:132 */     MainLogger.info("Earned amount = " + earnings);
/* 125:    */     
/* 126:134 */     this.score = new AlgorithmScore(referentEarnings, earnings, soldAllStocks);
/* 127:135 */     System.gc();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public float calculateReferenceScore()
/* 131:    */   {
/* 132:143 */     MainLogger.debug("Calculating reference score...");
/* 133:144 */     Format formatter = new SimpleDateFormat("HH:mm:ss");
/* 134:    */     
/* 135:146 */     StockMarketData maxPriceStockMarketData = this.simulator.getMaxPriceRecord();
/* 136:147 */     StockMarketData minPriceRecordBeforeMaxRecord = this.simulator.getMinPriceRecordBeforeMaxPriceRecord();
/* 137:149 */     if ((minPriceRecordBeforeMaxRecord == null) || (minPriceRecordBeforeMaxRecord.time.getTime() == 0L))
/* 138:    */     {
/* 139:150 */       MainLogger.info("Price of the stock started with maximum value and did not recover during the day.");
/* 140:151 */       MainLogger.info("Reference earnings is zero! (Set to 0.01 in order to avoid \"divide by zero\" error)");
/* 141:    */       
/* 142:153 */       return 0.01F;
/* 143:    */     }
/* 144:156 */     float minPrice = ((Transaction)minPriceRecordBeforeMaxRecord.transactionList.get(0)).price;
/* 145:157 */     float maxPrice = ((Transaction)maxPriceStockMarketData.transactionList.get(0)).price;
/* 146:    */     
/* 147:159 */     MainLogger.debug("MIN PRICE " + minPrice + " occured at " + 
/* 148:160 */       formatter.format(((Transaction)minPriceRecordBeforeMaxRecord.transactionList.get(0)).time));
/* 149:161 */     MainLogger.debug("MAX PRICE " + maxPrice + " occured at " + 
/* 150:162 */       formatter.format(((Transaction)maxPriceStockMarketData.transactionList.get(0)).time));
/* 151:    */     
/* 152:164 */     float cash = Float.parseFloat(ParasiteManager.getInstance().properties
/* 153:165 */       .getProperty("REFERENCE_ALGORITHM_TRANSACTION_CASH"));
/* 154:    */     
/* 155:167 */     int amount = (int)(cash / minPrice);
/* 156:168 */     MainLogger.debug("Amount = " + amount);
/* 157:169 */     float valueAtMinPrice = amount * minPrice;
/* 158:170 */     MainLogger.debug("Value at min price = " + valueAtMinPrice);
/* 159:    */     
/* 160:172 */     float valueAtMaxPrice = amount * maxPrice;
/* 161:173 */     MainLogger.debug("Value at max price = " + valueAtMaxPrice);
/* 162:    */     
/* 163:175 */     float earnings = valueAtMaxPrice - valueAtMinPrice;
/* 164:176 */     MainLogger.debug("Reference score (earnings) = " + earnings);
/* 165:177 */     return earnings;
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.configurations.ConfigurationEvaluation
 * JD-Core Version:    0.7.0.1
 */