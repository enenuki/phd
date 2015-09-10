/*   1:    */ package hr.nukic.parasite.core.algorithm;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.core.DataCollector;
/*   4:    */ import hr.nukic.parasite.core.StockMarketData;
/*   5:    */ import hr.nukic.parasite.core.StockState;
/*   6:    */ import hr.nukic.parasite.core.algorithm.configurations.AlgorithmConfiguration;
/*   7:    */ import java.util.Observable;
/*   8:    */ import nukic.parasite.utils.MainLogger;
/*   9:    */ 
/*  10:    */ public abstract class ThreadedTradingAlgorithm
/*  11:    */   extends TradingAlgorithm
/*  12:    */   implements Runnable
/*  13:    */ {
/*  14:    */   public static final int TRADING_THREAD_DEFAULT_WAIT_TIME = 10;
/*  15:    */   public static final int TRADING_THREAD_SHORT_WAIT_TIME = 10;
/*  16:    */   public static final int TRADING_THREAD_LONG_WAIT_TIME = 30;
/*  17: 17 */   private boolean stop = false;
/*  18: 18 */   boolean firstIteration = true;
/*  19:    */   
/*  20:    */   public ThreadedTradingAlgorithm(DataCollector dc, StockState stockState, String ticker, AlgorithmConfiguration config)
/*  21:    */   {
/*  22: 32 */     super(dc, stockState, ticker, config);
/*  23: 33 */     this.runningInSeparateThread = true;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void run()
/*  27:    */   {
/*  28: 39 */     Thread.currentThread().setName(this.ticker + "_AlgorithmThread");
/*  29: 40 */     while (!this.stop) {
/*  30: 41 */       if (this.isConfigCorrect)
/*  31:    */       {
/*  32: 44 */         if (this.marketData == null)
/*  33:    */         {
/*  34: 45 */           MainLogger.error("ERROR: Market data MUST be set by this point! Stoping thread...");
/*  35: 46 */           stop();
/*  36:    */         }
/*  37: 50 */         synchronized (this.marketData)
/*  38:    */         {
/*  39:    */           try
/*  40:    */           {
/*  41: 52 */             MainLogger.debug("Waiting until market data is changed...");
/*  42: 53 */             this.marketData.wait();
/*  43:    */           }
/*  44:    */           catch (InterruptedException e)
/*  45:    */           {
/*  46: 56 */             MainLogger.error("StockMarketData update waiting interupted!");
/*  47: 57 */             throw new RuntimeException(e);
/*  48:    */           }
/*  49:    */         }
/*  50: 62 */         if (this.marketData.isLastRecord)
/*  51:    */         {
/*  52: 63 */           MainLogger.debug("Last iteration. Stoping algorithm thread...");
/*  53: 64 */           stop();
/*  54:    */         }
/*  55:    */         else
/*  56:    */         {
/*  57: 68 */           switch (this.stockState)
/*  58:    */           {
/*  59:    */           case BUY_CANDIDATE: 
/*  60: 70 */             MainLogger.debug("NOT_INTERESTING_STATE");
/*  61: 71 */             processNotInterestingState();
/*  62: 72 */             break;
/*  63:    */           case NOT_INTERESTING: 
/*  64: 74 */             MainLogger.debug("UNDER_WATCH_STATE");
/*  65: 75 */             processUnderWatchState();
/*  66: 76 */             break;
/*  67:    */           case OWNED_NOT_PROFITABLE: 
/*  68: 78 */             MainLogger.debug("BUY_CANDIDATE_STATE");
/*  69: 79 */             processBuyCandidateState();
/*  70: 80 */             break;
/*  71:    */           case OWNED_PROFITABLE: 
/*  72: 82 */             MainLogger.debug("NOT_PROFITABLE_STATE");
/*  73: 83 */             processNotProfitableState();
/*  74: 84 */             break;
/*  75:    */           case OWNED_SELL_CANDIDATE: 
/*  76: 86 */             MainLogger.debug("PROFITABLE_STATE");
/*  77: 87 */             processProfitableState();
/*  78: 88 */             break;
/*  79:    */           case UNDER_WATCH: 
/*  80: 90 */             MainLogger.debug("SELL_CANDIDATE_STATE");
/*  81: 91 */             processSellCandidateState();
/*  82:    */           }
/*  83:    */         }
/*  84:    */       }
/*  85:    */       else
/*  86:    */       {
/*  87: 96 */         MainLogger.error("Could not start algorithm. Configuration is not correct!");
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void stop()
/*  93:    */   {
/*  94:102 */     this.stop = true;
/*  95:103 */     synchronized (this)
/*  96:    */     {
/*  97:104 */       notifyAll();
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void update(Observable o, Object arg)
/* 102:    */   {
/* 103:111 */     if ((o instanceof StockMarketData)) {
/* 104:113 */       o.notifyAll();
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void prepareForExecuting()
/* 109:    */   {
/* 110:124 */     switch (this.stockState)
/* 111:    */     {
/* 112:    */     case BUY_CANDIDATE: 
/* 113:126 */       prepareForExecutingNotInterestingState();
/* 114:127 */       break;
/* 115:    */     case NOT_INTERESTING: 
/* 116:129 */       prepareForExecutingUnderWatchState();
/* 117:130 */       break;
/* 118:    */     case OWNED_NOT_PROFITABLE: 
/* 119:132 */       prepareForExecutingBuyCandidateState();
/* 120:133 */       break;
/* 121:    */     case OWNED_PROFITABLE: 
/* 122:135 */       prepareForExecutingNotProfitableState();
/* 123:136 */       break;
/* 124:    */     case OWNED_SELL_CANDIDATE: 
/* 125:138 */       prepareForExecutingProfitableState();
/* 126:139 */       break;
/* 127:    */     case UNDER_WATCH: 
/* 128:141 */       prepareForExecutingSellCandidateState();
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public abstract void prepareForExecutingSellCandidateState();
/* 133:    */   
/* 134:    */   public abstract void prepareForExecutingProfitableState();
/* 135:    */   
/* 136:    */   public abstract void prepareForExecutingNotProfitableState();
/* 137:    */   
/* 138:    */   public abstract void prepareForExecutingBuyCandidateState();
/* 139:    */   
/* 140:    */   public abstract void prepareForExecutingUnderWatchState();
/* 141:    */   
/* 142:    */   public abstract void prepareForExecutingNotInterestingState();
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.ThreadedTradingAlgorithm
 * JD-Core Version:    0.7.0.1
 */