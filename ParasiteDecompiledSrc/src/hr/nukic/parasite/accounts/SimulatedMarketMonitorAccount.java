/*   1:    */ package hr.nukic.parasite.accounts;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.accounts.templates.MarketMonitorAccount;
/*   4:    */ import hr.nukic.parasite.core.DataCollector;
/*   5:    */ import hr.nukic.parasite.core.Order;
/*   6:    */ import hr.nukic.parasite.core.StockMarketData;
/*   7:    */ import hr.nukic.parasite.core.Transaction;
/*   8:    */ import hr.nukic.parasite.simulator.MarketDataSubscriber;
/*   9:    */ import hr.nukic.parasite.simulator.MarketSimulator;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import nukic.parasite.utils.MainLogger;
/*  14:    */ 
/*  15:    */ public class SimulatedMarketMonitorAccount
/*  16:    */   implements MarketMonitorAccount, MarketDataSubscriber
/*  17:    */ {
/*  18:    */   private MarketSimulator marketSimulator;
/*  19:    */   private DataCollector dataCollector;
/*  20:    */   long id;
/*  21:    */   
/*  22:    */   public SimulatedMarketMonitorAccount(MarketSimulator ms)
/*  23:    */   {
/*  24: 40 */     this.id = ((9.223372036854776E+018D * Math.random()));
/*  25: 41 */     this.marketSimulator = ms;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setDataCollector(DataCollector dc)
/*  29:    */   {
/*  30: 48 */     this.dataCollector = dc;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean login()
/*  34:    */   {
/*  35: 56 */     return true;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean logout()
/*  39:    */   {
/*  40: 61 */     return true;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<Order> readAskListFromWeb(String ticker)
/*  44:    */   {
/*  45: 66 */     if (this.marketSimulator.ticker.equals(ticker)) {
/*  46: 67 */       return this.marketSimulator.currentRecord.askList;
/*  47:    */     }
/*  48: 70 */     MainLogger.error("ERROR: Cannot read ask list for this ticker.Ticker mismatch! Probably wrong intialization!");
/*  49: 71 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public List<Order> readBidListFromWeb(String ticker)
/*  53:    */   {
/*  54: 77 */     if (this.marketSimulator.ticker.equals(ticker)) {
/*  55: 78 */       return this.marketSimulator.currentRecord.bidList;
/*  56:    */     }
/*  57: 81 */     MainLogger.error("ERROR: Cannot read bid list for this ticker.Ticker mismatch! Probably wrong intialization!");
/*  58: 82 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<Transaction> readTransactionListFromWeb(String ticker)
/*  62:    */   {
/*  63: 88 */     if (this.marketSimulator.ticker.equals(ticker)) {
/*  64: 89 */       return this.marketSimulator.currentRecord.transactionList;
/*  65:    */     }
/*  66: 92 */     MainLogger.error("ERROR: Cannot read transaction list for this ticker.Ticker mismatch! Probably wrong intialization!");
/*  67: 93 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public StockMarketData readMarketDataFromWeb(String ticker)
/*  71:    */   {
/*  72: 99 */     if (this.marketSimulator.ticker.equals(ticker)) {
/*  73:100 */       return this.marketSimulator.currentRecord;
/*  74:    */     }
/*  75:103 */     MainLogger.error("ERROR: Cannot read market data for this ticker.Ticker mismatch! Probably wrong intialization!");
/*  76:104 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void refreshMarketDataFromWeb(List<String> tickerList) {}
/*  80:    */   
/*  81:    */   public void onMarketDataChange(StockMarketData smd)
/*  82:    */   {
/*  83:119 */     StockMarketData marketData = this.dataCollector.getStockMarketData(smd.ticker);
/*  84:120 */     if (marketData == null)
/*  85:    */     {
/*  86:121 */       marketData = new StockMarketData(this.dataCollector, smd.ticker);
/*  87:122 */       this.dataCollector.addStockMarketData(marketData);
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:125 */       marketData.bidList.clear();
/*  92:126 */       marketData.askList.clear();
/*  93:127 */       marketData.transactionList.clear();
/*  94:    */     }
/*  95:130 */     marketData.bidList.addAll(smd.bidList);
/*  96:131 */     marketData.askList.addAll(smd.askList);
/*  97:132 */     marketData.transactionList.addAll(smd.transactionList);
/*  98:133 */     marketData.time.setTime(smd.time.getTime());
/*  99:134 */     marketData.isLastRecord = smd.isLastRecord;
/* 100:    */     
/* 101:    */ 
/* 102:137 */     marketData.calculateImplicitData();
/* 103:    */     
/* 104:    */ 
/* 105:140 */     marketData.setDataChanged();
/* 106:141 */     synchronized (marketData)
/* 107:    */     {
/* 108:142 */       marketData.notifyObservers();
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public long getSubscriberId()
/* 113:    */   {
/* 114:150 */     return this.id;
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.SimulatedMarketMonitorAccount
 * JD-Core Version:    0.7.0.1
 */