/*  1:   */ package hr.nukic.parasite.core;
/*  2:   */ 
/*  3:   */ import hr.nukic.parasite.accounts.VirtualnaBurza;
/*  4:   */ import hr.nukic.parasite.accounts.templates.HistoryDataProvider;
/*  5:   */ import hr.nukic.parasite.simulator.CsvFileMarketSimulator;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.List;
/* 10:   */ import nukic.parasite.utils.MainLogger;
/* 11:   */ import nukic.parasite.utils.ParasiteFileUtils;
/* 12:   */ 
/* 13:   */ public class StockMetrics
/* 14:   */ {
/* 15:29 */   public static HashMap<String, StockMetrics> stockMetricList = new HashMap();
/* 16:   */   public String ticker;
/* 17:   */   
/* 18:   */   public static StockMetrics getStockMetrics(String ticker)
/* 19:   */   {
/* 20:32 */     StockMetrics sm = (StockMetrics)stockMetricList.get(ticker);
/* 21:33 */     if (sm == null)
/* 22:   */     {
/* 23:34 */       sm = new StockMetrics(ticker);
/* 24:35 */       sm.averageIntradayVolatility = calculateAverageIntradayVolatilityForTicker(ticker);
/* 25:   */       
/* 26:37 */       stockMetricList.put(ticker, sm);
/* 27:   */     }
/* 28:39 */     return sm;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static float calculateAverageIntradayVolatilityForTicker(String ticker)
/* 32:   */   {
/* 33:44 */     StockHistoryData history = new StockHistoryData(ticker);
/* 34:45 */     if (!history.readDaySummariesFromFile())
/* 35:   */     {
/* 36:46 */       MainLogger.info("No day summaries file found.Connecting to web...");
/* 37:47 */       HistoryDataProvider historyProvider = new VirtualnaBurza();
/* 38:48 */       history.daySummaries = historyProvider.readDaySummariesFromWeb(ticker);
/* 39:49 */       history.writeDaySummariesToFile();
/* 40:   */     }
/* 41:52 */     Iterator<StockDaySummary> i = history.daySummaries.iterator();
/* 42:53 */     float sum = 0.0F;
/* 43:54 */     while (i.hasNext())
/* 44:   */     {
/* 45:55 */       StockDaySummary daySummary = (StockDaySummary)i.next();
/* 46:56 */       float relIntradayVolatility = (daySummary.high - daySummary.low) / daySummary.average * 100.0F;
/* 47:   */       
/* 48:58 */       sum += relIntradayVolatility;
/* 49:   */     }
/* 50:61 */     if (history.daySummaries.size() != 0)
/* 51:   */     {
/* 52:63 */       float averageIntradayVolatility = sum / history.daySummaries.size();
/* 53:64 */       return averageIntradayVolatility;
/* 54:   */     }
/* 55:66 */     return 0.0F;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static List<CsvFileMarketSimulator> loadAllCsvFilesForTicker(String ticker, String folder)
/* 59:   */   {
/* 60:72 */     List<String> files = ParasiteFileUtils.findAllCsvFilesByTicker(folder, ticker);
/* 61:73 */     List<CsvFileMarketSimulator> sims = new ArrayList();
/* 62:75 */     for (String file : files)
/* 63:   */     {
/* 64:76 */       CsvFileMarketSimulator csvSim = new CsvFileMarketSimulator(file, 25);
/* 65:77 */       sims.add(csvSim);
/* 66:   */     }
/* 67:79 */     return sims;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public StockMetrics(String ticker)
/* 71:   */   {
/* 72:93 */     this.ticker = ticker;
/* 73:   */   }
/* 74:   */   
/* 75:97 */   public float averageIntradayVolatility = 0.0F;
/* 76:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.StockMetrics
 * JD-Core Version:    0.7.0.1
 */