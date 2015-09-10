/*    1:     */ package hr.nukic.parasite.test;
/*    2:     */ 
/*    3:     */ import hr.nukic.parasite.accounts.FimaEtrade;
/*    4:     */ import hr.nukic.parasite.accounts.SimulatedMarketMonitorAccount;
/*    5:     */ import hr.nukic.parasite.accounts.SimulatedTradingAccount;
/*    6:     */ import hr.nukic.parasite.accounts.VirtualnaBurza;
/*    7:     */ import hr.nukic.parasite.accounts.templates.HistoryDataProvider;
/*    8:     */ import hr.nukic.parasite.accounts.templates.MarketMonitorAccount;
/*    9:     */ import hr.nukic.parasite.accounts.templates.TradingAccount;
/*   10:     */ import hr.nukic.parasite.core.DataCollector;
/*   11:     */ import hr.nukic.parasite.core.ImplicitStockMarketData;
/*   12:     */ import hr.nukic.parasite.core.Order;
/*   13:     */ import hr.nukic.parasite.core.OrderType;
/*   14:     */ import hr.nukic.parasite.core.ParasiteManager;
/*   15:     */ import hr.nukic.parasite.core.PortfolioData;
/*   16:     */ import hr.nukic.parasite.core.StockHistoryData;
/*   17:     */ import hr.nukic.parasite.core.StockMarketData;
/*   18:     */ import hr.nukic.parasite.core.StockMetrics;
/*   19:     */ import hr.nukic.parasite.core.StockPortfolioData;
/*   20:     */ import hr.nukic.parasite.core.StockState;
/*   21:     */ import hr.nukic.parasite.core.Transaction;
/*   22:     */ import hr.nukic.parasite.core.algorithm.SimpleThreadedParasite;
/*   23:     */ import hr.nukic.parasite.core.algorithm.configurations.AlgorithmConfiguration;
/*   24:     */ import hr.nukic.parasite.core.algorithm.configurations.AlgorithmConfigurationSuite;
/*   25:     */ import hr.nukic.parasite.core.algorithm.configurations.Configurable;
/*   26:     */ import hr.nukic.parasite.core.algorithm.configurations.ConfigurationEvaluationSuite;
/*   27:     */ import hr.nukic.parasite.simulator.CsvFileMarketSimulator;
/*   28:     */ import hr.nukic.parasite.simulator.SimulatorStatus;
/*   29:     */ import java.text.Format;
/*   30:     */ import java.text.ParseException;
/*   31:     */ import java.text.SimpleDateFormat;
/*   32:     */ import java.util.ArrayList;
/*   33:     */ import java.util.Date;
/*   34:     */ import java.util.Iterator;
/*   35:     */ import java.util.List;
/*   36:     */ import java.util.Map;
/*   37:     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*   38:     */ import junit.framework.Assert;
/*   39:     */ import nukic.parasite.utils.MainLogger;
/*   40:     */ import nukic.parasite.utils.ParasiteFileUtils;
/*   41:     */ import nukic.parasite.utils.ParasiteUtils;
/*   42:     */ 
/*   43:     */ public class Main
/*   44:     */ {
/*   45:     */   public static void main(String[] args) {}
/*   46:     */   
/*   47:     */   private static void testWriteAndReadFromDaySummariesFile()
/*   48:     */   {
/*   49: 153 */     DataCollector dc = new DataCollector();
/*   50: 154 */     HistoryDataProvider historyProvider = new VirtualnaBurza(dc);
/*   51: 155 */     ArrayList<String> tickers = new ArrayList();
/*   52: 156 */     tickers.add("HT-R-A");
/*   53:     */     
/*   54:     */ 
/*   55:     */ 
/*   56:     */ 
/*   57: 161 */     MainLogger.debug("Reading daySummaries for first time...");
/*   58: 162 */     List<StockHistoryData> list1 = historyProvider.getHistoryDataForTickers(tickers);
/*   59:     */     
/*   60: 164 */     MainLogger.debug("Reading daySummaries for second time...");
/*   61: 165 */     List<StockHistoryData> list2 = historyProvider.getHistoryDataForTickers(tickers);
/*   62:     */     
/*   63: 167 */     Assert.assertTrue(list1.size() == list2.size());
/*   64: 168 */     for (int i = 0; i < list1.size(); i++) {
/*   65: 169 */       Assert.assertTrue(((StockHistoryData)list1.get(i)).equals(list2.get(i)));
/*   66:     */     }
/*   67:     */   }
/*   68:     */   
/*   69:     */   private static void testParasiteManager()
/*   70:     */   {
/*   71: 175 */     ParasiteManager.getInstance().start();
/*   72:     */   }
/*   73:     */   
/*   74:     */   private static void testCreateEvaluationSuiteReport()
/*   75:     */   {
/*   76: 180 */     ArrayList<Configurable> configs = new ArrayList(3);
/*   77:     */     
/*   78:     */ 
/*   79: 183 */     configs.add((Configurable)Configurable.dictionary.get("DAMAGE_CONTROL_TRIGGER_PERCENTAGE"));
/*   80: 184 */     configs.add((Configurable)Configurable.dictionary.get("PROFITABLE_SELL_TRIGGER_PERCENTAGE"));
/*   81: 185 */     configs.add((Configurable)Configurable.dictionary.get("PROFITABLE_SELL_TRIGGER_VALUE"));
/*   82: 186 */     configs.add((Configurable)Configurable.dictionary.get("MIN_AVERAGE_DAY_TURNOVER"));
/*   83: 187 */     configs.add((Configurable)Configurable.dictionary.get("OWN_BID_VALUE"));
/*   84: 188 */     configs.add((Configurable)Configurable.dictionary.get("RATIO_WEIGHT"));
/*   85: 189 */     configs.add((Configurable)Configurable.dictionary.get("DELTA_WEIGHT"));
/*   86: 190 */     configs.add((Configurable)Configurable.dictionary.get("MIN_BA_RATIO"));
/*   87: 191 */     configs.add((Configurable)Configurable.dictionary.get("MIN_BA_DELTA"));
/*   88: 192 */     AlgorithmConfiguration ac = new AlgorithmConfiguration(configs);
/*   89:     */     
/*   90:     */ 
/*   91: 195 */     String p2 = "D:\\Documents\\devel\\java\\ParasiteTrade\\out\\2011-03-09\\KORF-R-A_MarketData_2011-03-09.csv";
/*   92:     */     
/*   93:     */ 
/*   94:     */ 
/*   95:     */ 
/*   96:     */ 
/*   97:     */ 
/*   98:     */ 
/*   99:     */ 
/*  100:     */ 
/*  101:     */ 
/*  102:     */ 
/*  103: 207 */     List<String> paths = new ArrayList();
/*  104:     */     
/*  105: 209 */     paths.add(p2);
/*  106:     */     
/*  107:     */ 
/*  108:     */ 
/*  109:     */ 
/*  110:     */ 
/*  111:     */ 
/*  112:     */ 
/*  113: 217 */     ConfigurationEvaluationSuite ces = new ConfigurationEvaluationSuite(ac, paths);
/*  114: 218 */     ces.execute();
/*  115: 219 */     ces.createEvaluationSuiteReport();
/*  116:     */   }
/*  117:     */   
/*  118:     */   private static void testCalculateNewDefaults()
/*  119:     */   {
/*  120: 224 */     ArrayList<Configurable> configs = new ArrayList(3);
/*  121: 225 */     configs.add((Configurable)Configurable.dictionary.get("MIN_BA_DELTA"));
/*  122: 226 */     configs.add((Configurable)Configurable.dictionary.get("MIN_BA_RATIO"));
/*  123: 227 */     configs.add((Configurable)Configurable.dictionary.get("DAMAGE_CONTROL_TRIGGER_PERCENTAGE"));
/*  124: 228 */     configs.add((Configurable)Configurable.dictionary.get("PROFITABLE_SELL_TRIGGER_PERCENTAGE"));
/*  125: 229 */     configs.add((Configurable)Configurable.dictionary.get("PROFITABLE_SELL_TRIGGER_VALUE"));
/*  126: 230 */     configs.add((Configurable)Configurable.dictionary.get("MIN_AVERAGE_DAY_TURNOVER"));
/*  127: 231 */     configs.add((Configurable)Configurable.dictionary.get("OWN_BID_VALUE"));
/*  128: 232 */     configs.add((Configurable)Configurable.dictionary.get("RATIO_WEIGHT"));
/*  129: 233 */     configs.add((Configurable)Configurable.dictionary.get("DELTA_WEIGHT"));
/*  130:     */     
/*  131: 235 */     AlgorithmConfiguration ac = new AlgorithmConfiguration(configs);
/*  132:     */     
/*  133:     */ 
/*  134:     */ 
/*  135:     */ 
/*  136:     */ 
/*  137:     */ 
/*  138:     */ 
/*  139: 243 */     String p7 = "D:\\Documents\\devel\\java\\ParasiteTrade\\ParasiteTrade\\out\\2011-03-24\\KORF-R-A_MarketData_2011-03-24.csv";
/*  140:     */     
/*  141:     */ 
/*  142:     */ 
/*  143:     */ 
/*  144:     */ 
/*  145: 249 */     List<String> paths = new ArrayList();
/*  146:     */     
/*  147: 251 */     paths.add(p7);
/*  148:     */     
/*  149: 253 */     AlgorithmConfigurationSuite configSuite = new AlgorithmConfigurationSuite(ac, false);
/*  150: 254 */     configSuite.calculateNewDefaultsUsing(paths);
/*  151:     */   }
/*  152:     */   
/*  153:     */   private static void testFindAllCsvFilesInFolder()
/*  154:     */   {
/*  155: 259 */     String s = "C://ParasiteTrade//out";
/*  156: 260 */     ConfigurationEvaluationSuite ces = new ConfigurationEvaluationSuite(null, s);
/*  157: 261 */     ParasiteFileUtils.findAllCsvFilesInFolder(s);
/*  158:     */   }
/*  159:     */   
/*  160:     */   private static void testSingleConfigurationEvaluationSuite()
/*  161:     */   {
/*  162: 266 */     ArrayList<Configurable> configs = new ArrayList(3);
/*  163:     */     
/*  164:     */ 
/*  165: 269 */     configs.add((Configurable)Configurable.dictionary.get("DAMAGE_CONTROL_TRIGGER_PERCENTAGE"));
/*  166: 270 */     configs.add((Configurable)Configurable.dictionary.get("PROFITABLE_SELL_TRIGGER_PERCENTAGE"));
/*  167: 271 */     configs.add((Configurable)Configurable.dictionary.get("PROFITABLE_SELL_TRIGGER_VALUE"));
/*  168: 272 */     configs.add((Configurable)Configurable.dictionary.get("MIN_AVERAGE_DAY_TURNOVER"));
/*  169: 273 */     configs.add((Configurable)Configurable.dictionary.get("OWN_BID_VALUE"));
/*  170: 274 */     configs.add((Configurable)Configurable.dictionary.get("RATIO_WEIGHT"));
/*  171: 275 */     configs.add((Configurable)Configurable.dictionary.get("DELTA_WEIGHT"));
/*  172: 276 */     configs.add((Configurable)Configurable.dictionary.get("MIN_BA_RATIO"));
/*  173: 277 */     configs.add((Configurable)Configurable.dictionary.get("MIN_BA_DELTA"));
/*  174: 278 */     AlgorithmConfiguration ac = new AlgorithmConfiguration(configs);
/*  175:     */     
/*  176:     */ 
/*  177: 281 */     String p2 = "D:\\Documents\\devel\\java\\ParasiteTrade\\out\\2011-03-09\\KORF-R-A_MarketData_2011-03-09.csv";
/*  178:     */     
/*  179:     */ 
/*  180:     */ 
/*  181:     */ 
/*  182:     */ 
/*  183:     */ 
/*  184:     */ 
/*  185:     */ 
/*  186:     */ 
/*  187:     */ 
/*  188:     */ 
/*  189: 293 */     List<String> paths = new ArrayList();
/*  190:     */     
/*  191: 295 */     paths.add(p2);
/*  192:     */     
/*  193:     */ 
/*  194:     */ 
/*  195:     */ 
/*  196:     */ 
/*  197:     */ 
/*  198:     */ 
/*  199: 303 */     ConfigurationEvaluationSuite ces = new ConfigurationEvaluationSuite(ac, paths);
/*  200: 304 */     ces.execute();
/*  201:     */   }
/*  202:     */   
/*  203:     */   private static void testLoadingConfigurationTestSuite()
/*  204:     */   {
/*  205: 309 */     Configurable c1 = new Configurable("TestConfigurable1", 0.0F, 5.0F, 1.0F, 0.0F, 10.0F);
/*  206: 310 */     Configurable c2 = new Configurable("TestConfigurable2", -10.0F, 0.0F, 2.0F, -10.0F, 10.0F);
/*  207: 311 */     Configurable c3 = new Configurable("TestConfigurable3", -5.0F, 50.0F, 5.0F, -10.0F, 100.0F);
/*  208:     */     
/*  209: 313 */     ArrayList<Configurable> configs = new ArrayList(3);
/*  210: 314 */     configs.add(c1);
/*  211: 315 */     configs.add(c2);
/*  212: 316 */     configs.add(c3);
/*  213:     */     
/*  214: 318 */     AlgorithmConfiguration ac = new AlgorithmConfiguration(configs);
/*  215:     */     
/*  216: 320 */     AlgorithmConfigurationSuite suite = new AlgorithmConfigurationSuite(ac, true);
/*  217: 321 */     suite.printConfigurationMap();
/*  218:     */   }
/*  219:     */   
/*  220:     */   private static void testRounding()
/*  221:     */   {
/*  222: 326 */     float a = 0.1234568F;
/*  223: 327 */     for (int i = 1; i < 9; i++) {
/*  224: 328 */       MainLogger.debug("Rounding to " + i + " decimal places: " + ParasiteUtils.round(a, i));
/*  225:     */     }
/*  226:     */   }
/*  227:     */   
/*  228:     */   private static void testLoadDefaultConfiguration()
/*  229:     */   {
/*  230: 333 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29.csv";
/*  231: 334 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 100000);
/*  232: 335 */     MainLogger.debug("Simulator ticker = " + ms.ticker);
/*  233: 336 */     String ticker = ms.ticker;
/*  234: 337 */     Thread simulator = new Thread(ms);
/*  235:     */     
/*  236: 339 */     PortfolioData p = new PortfolioData(new ArrayList(), 40000.0F);
/*  237: 340 */     SimulatedTradingAccount tradingAccount = new SimulatedTradingAccount(p, new ArrayList(), ms, 0.3F, 0.0F);
/*  238: 341 */     SimulatedMarketMonitorAccount monitorAccount = new SimulatedMarketMonitorAccount(ms);
/*  239: 342 */     DataCollector dc = new DataCollector(ms, tradingAccount, monitorAccount, ticker, false);
/*  240: 343 */     monitorAccount.setDataCollector(dc);
/*  241:     */     
/*  242: 345 */     ArrayList<Configurable> configs = new ArrayList(3);
/*  243:     */     
/*  244:     */ 
/*  245:     */ 
/*  246:     */ 
/*  247:     */ 
/*  248:     */ 
/*  249:     */ 
/*  250:     */ 
/*  251:     */ 
/*  252:     */ 
/*  253:     */ 
/*  254:     */ 
/*  255: 358 */     AlgorithmConfiguration ac = new AlgorithmConfiguration(configs);
/*  256:     */     
/*  257: 360 */     SimpleThreadedParasite algo = new SimpleThreadedParasite(dc, ticker, ac);
/*  258: 361 */     algo.loadDefaultConfiguration();
/*  259: 362 */     MainLogger.info(algo.config.toString());
/*  260:     */   }
/*  261:     */   
/*  262:     */   private static void testAlgorithmConfigCheck()
/*  263:     */   {
/*  264: 367 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29.csv";
/*  265: 368 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 100000);
/*  266: 369 */     MainLogger.debug("Simulator ticker = " + ms.ticker);
/*  267: 370 */     String ticker = ms.ticker;
/*  268: 371 */     Thread simulator = new Thread(ms);
/*  269:     */     
/*  270: 373 */     PortfolioData p = new PortfolioData(new ArrayList(), 40000.0F);
/*  271: 374 */     SimulatedTradingAccount tradingAccount = new SimulatedTradingAccount(p, new ArrayList(), ms, 0.3F, 0.0F);
/*  272: 375 */     SimulatedMarketMonitorAccount monitorAccount = new SimulatedMarketMonitorAccount(ms);
/*  273: 376 */     DataCollector dc = new DataCollector(ms, tradingAccount, monitorAccount, ticker, false);
/*  274: 377 */     monitorAccount.setDataCollector(dc);
/*  275:     */     
/*  276: 379 */     ArrayList<Configurable> configs = new ArrayList(3);
/*  277:     */     
/*  278:     */ 
/*  279: 382 */     configs.add((Configurable)Configurable.dictionary.get("DAMAGE_CONTROL_TRIGGER_PERCENTAGE"));
/*  280: 383 */     configs.add((Configurable)Configurable.dictionary.get("PROFITABLE_SELL_TRIGGER_PERCENTAGE"));
/*  281: 384 */     configs.add((Configurable)Configurable.dictionary.get("PROFITABLE_SELL_TRIGGER_VALUE"));
/*  282: 385 */     configs.add((Configurable)Configurable.dictionary.get("MIN_AVERAGE_DAY_TURNOVER"));
/*  283: 386 */     configs.add((Configurable)Configurable.dictionary.get("OWN_BID_VALUE"));
/*  284: 387 */     configs.add((Configurable)Configurable.dictionary.get("RATIO_WEIGHT"));
/*  285: 388 */     configs.add((Configurable)Configurable.dictionary.get("DELTA_WEIGHT"));
/*  286: 389 */     configs.add((Configurable)Configurable.dictionary.get("MIN_BA_RATIO"));
/*  287: 390 */     configs.add((Configurable)Configurable.dictionary.get("MIN_BA_DELTA"));
/*  288:     */     
/*  289:     */ 
/*  290: 393 */     configs.add((Configurable)Configurable.dictionary.get("TestConfigurable4"));
/*  291:     */     
/*  292: 395 */     AlgorithmConfiguration ac = new AlgorithmConfiguration(configs);
/*  293: 396 */     SimpleThreadedParasite algo = new SimpleThreadedParasite(dc, ticker, ac);
/*  294:     */   }
/*  295:     */   
/*  296:     */   private static void testReadConfigurableDictionary()
/*  297:     */   {
/*  298: 402 */     Configurable c1 = new Configurable("TestConfigurable1", 0.0F, 1.0F, 0.0F, 10.0F);
/*  299:     */   }
/*  300:     */   
/*  301:     */   private static void testWriteConfigToCsvFile()
/*  302:     */   {
/*  303: 407 */     Configurable c1 = new Configurable("TestConfigurable1", 0.0F, 5.0F, 1.0F, 0.0F, 10.0F);
/*  304: 408 */     Configurable c2 = new Configurable("TestConfigurable2", -10.0F, 0.0F, 2.0F, -10.0F, 10.0F);
/*  305: 409 */     Configurable c3 = new Configurable("TestConfigurable3", -5.0F, 50.0F, 5.0F, -10.0F, 100.0F);
/*  306:     */     
/*  307: 411 */     MainLogger.debug(c1.toString());
/*  308: 412 */     MainLogger.debug(c2.toString());
/*  309: 413 */     MainLogger.debug(c3.toString());
/*  310:     */     
/*  311: 415 */     ArrayList<Configurable> configs = new ArrayList(3);
/*  312: 416 */     configs.add(c1);
/*  313: 417 */     configs.add(c2);
/*  314: 418 */     configs.add(c3);
/*  315:     */     
/*  316: 420 */     AlgorithmConfiguration ac = new AlgorithmConfiguration(configs);
/*  317: 421 */     MainLogger.debug(ac.toString());
/*  318:     */     
/*  319: 423 */     ac.writeToCsvFile();
/*  320:     */   }
/*  321:     */   
/*  322:     */   private static void testParasiteManagerReadProperties()
/*  323:     */   {
/*  324: 427 */     ParasiteManager parasite = ParasiteManager.getInstance();
/*  325: 428 */     parasite.start();
/*  326:     */   }
/*  327:     */   
/*  328:     */   private static void testFindMinAndMaxPrice()
/*  329:     */   {
/*  330: 435 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29.csv";
/*  331: 436 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 100000);
/*  332: 437 */     MainLogger.debug("Simulator ticker = " + ms.ticker);
/*  333: 438 */     String ticker = ms.ticker;
/*  334: 439 */     Thread simulator = new Thread(ms);
/*  335:     */     
/*  336: 441 */     MainLogger.debug("Before: Min price = " + ((Transaction)ms.getMinPriceRecord().transactionList.get(0)).price);
/*  337: 442 */     MainLogger.debug("Before: Max price = " + ((Transaction)ms.getMaxPriceRecord().transactionList.get(0)).price);
/*  338:     */     
/*  339: 444 */     StockMarketData minBeforeMax = ms.getMinPriceRecordBeforeMaxPriceRecord();
/*  340: 445 */     if (minBeforeMax != null) {
/*  341: 446 */       MainLogger.debug(
/*  342: 447 */         "Before: Min price before max = " + ((Transaction)ms.getMinPriceRecordBeforeMaxPriceRecord().transactionList.get(0)).price);
/*  343:     */     } else {
/*  344: 449 */       MainLogger.debug("Min price before max = null!!!");
/*  345:     */     }
/*  346: 452 */     PortfolioData p = new PortfolioData(new ArrayList(), 40000.0F);
/*  347: 453 */     SimulatedTradingAccount tradingAccount = new SimulatedTradingAccount(p, new ArrayList(), ms, 0.3F, 0.0F);
/*  348: 454 */     SimulatedMarketMonitorAccount monitorAccount = new SimulatedMarketMonitorAccount(ms);
/*  349: 455 */     DataCollector dc = new DataCollector(ms, tradingAccount, monitorAccount, ticker, false);
/*  350: 456 */     monitorAccount.setDataCollector(dc);
/*  351:     */     
/*  352: 458 */     SimpleThreadedParasite algo = new SimpleThreadedParasite(dc, ticker, null);
/*  353: 459 */     algo.prepareForExecuting();
/*  354: 460 */     Thread simpleParasiteThread = new Thread(algo);
/*  355: 461 */     simpleParasiteThread.setName("SimpleParasiteThread");
/*  356: 462 */     simpleParasiteThread.setPriority(5);
/*  357:     */     
/*  358: 464 */     simpleParasiteThread.start();
/*  359:     */     
/*  360: 466 */     simulator.start();
/*  361:     */     try
/*  362:     */     {
/*  363: 469 */       Thread.sleep(20000L);
/*  364:     */     }
/*  365:     */     catch (InterruptedException e)
/*  366:     */     {
/*  367: 471 */       MainLogger.error(e);
/*  368:     */     }
/*  369: 474 */     MainLogger.debug("After: Min price = " + ((Transaction)ms.getMinPriceRecord().transactionList.get(0)).price);
/*  370: 475 */     MainLogger.debug("After: Max price = " + ((Transaction)ms.getMaxPriceRecord().transactionList.get(0)).price);
/*  371:     */     
/*  372: 477 */     minBeforeMax = ms.getMinPriceRecordBeforeMaxPriceRecord();
/*  373: 478 */     if (minBeforeMax != null) {
/*  374: 479 */       MainLogger.debug(
/*  375: 480 */         "After: Min price before max = " + ((Transaction)ms.getMinPriceRecordBeforeMaxPriceRecord().transactionList.get(0)).price);
/*  376:     */     } else {
/*  377: 482 */       MainLogger.debug("Min price before max = null!!!");
/*  378:     */     }
/*  379: 485 */     if (!simpleParasiteThread.isAlive()) {
/*  380: 486 */       MainLogger.debug("simpleParasiteThread is NOT alive!");
/*  381:     */     }
/*  382: 489 */     if (!simulator.isAlive()) {
/*  383: 490 */       MainLogger.debug("simulator thread is NOT alive!");
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   private static void testConfigurableSanityCheck()
/*  388:     */   {
/*  389: 496 */     Configurable c0 = new Configurable("TestConfigurable1", 11.0F, 1.0F, 0.0F, 10.0F);
/*  390: 497 */     MainLogger.debug(c0.toString());
/*  391:     */     
/*  392: 499 */     Configurable c1 = new Configurable("TestConfigurable1", -1.0F, 1.0F, 0.0F, 10.0F);
/*  393: 500 */     MainLogger.debug(c1.toString());
/*  394:     */     
/*  395: 502 */     Configurable c2 = new Configurable("TestConfigurable1", 0.0F, -1.0F, 0.0F, 10.0F);
/*  396: 503 */     MainLogger.debug(c2.toString());
/*  397:     */     
/*  398: 505 */     Configurable c3 = new Configurable("TestConfigurable1", 0.0F, 0.0F, 0.0F, 10.0F);
/*  399: 506 */     MainLogger.debug(c3.toString());
/*  400:     */     
/*  401: 508 */     Configurable c4 = new Configurable("TestConfigurable1", 0.0F, 20.0F, 0.0F, 10.0F);
/*  402: 509 */     MainLogger.debug(c4.toString());
/*  403:     */     
/*  404: 511 */     Configurable c5 = new Configurable("TestConfigurable1", 4.0F, 2.0F, 100.0F, 10.0F);
/*  405: 512 */     MainLogger.debug(c5.toString());
/*  406:     */     
/*  407:     */ 
/*  408: 515 */     Configurable c6 = new Configurable("TestConfigurable1", 0.0F, 0.0F, 0.0F, 0.0F);
/*  409: 516 */     MainLogger.debug(c6.toString());
/*  410:     */     
/*  411: 518 */     Configurable c7 = new Configurable("TestConfigurable1", 7.0F, 1.0F, 0.0F, 7.0F);
/*  412: 519 */     MainLogger.debug(c7.toString());
/*  413:     */   }
/*  414:     */   
/*  415:     */   private static void testCalculateOrdinal()
/*  416:     */   {
/*  417: 524 */     Configurable c1 = new Configurable("TestConfigurable1", 3.0F, 1.0F, 0.0F, 10.0F);
/*  418: 525 */     MainLogger.debug(c1.toString());
/*  419:     */     
/*  420: 527 */     Configurable c2 = new Configurable("TestConfigurable1", 43.0F, 1.0F, 0.0F, 10.0F);
/*  421: 528 */     MainLogger.debug(c2.toString());
/*  422:     */     
/*  423: 530 */     Configurable c3 = new Configurable("TestConfigurable1", 3.1F, 1.0F, 0.0F, 10.0F);
/*  424: 531 */     MainLogger.debug(c3.toString());
/*  425:     */   }
/*  426:     */   
/*  427:     */   private static void testConfigurationPossibleValues()
/*  428:     */   {
/*  429: 536 */     Configurable c1 = new Configurable("TestConfigurable1", 0.0F, 1.0F, 0.0F, 10.0F);
/*  430: 537 */     List<Float> pv = c1.getPossibleValues();
/*  431: 538 */     Iterator<Float> i = pv.iterator();
/*  432: 539 */     while (i.hasNext())
/*  433:     */     {
/*  434: 540 */       float value = ((Float)i.next()).floatValue();
/*  435: 541 */       MainLogger.debug("Value = " + value);
/*  436:     */     }
/*  437:     */   }
/*  438:     */   
/*  439:     */   private static void testReplacingConfigurableInConfiguration()
/*  440:     */   {
/*  441: 548 */     Configurable c1 = new Configurable("TestConfigurable1", 0.0F, 1.0F, 0.0F, 10.0F);
/*  442: 549 */     Configurable c2 = new Configurable("TestConfigurable2", -10.0F, 2.0F, -10.0F, 10.0F);
/*  443: 550 */     Configurable c3 = new Configurable("TestConfigurable3", -5.0F, 5.0F, -10.0F, 100.0F);
/*  444:     */     
/*  445: 552 */     ArrayList<Configurable> configs = new ArrayList(3);
/*  446: 553 */     configs.add(c1);
/*  447: 554 */     configs.add(c2);
/*  448: 555 */     configs.add(c3);
/*  449:     */     
/*  450: 557 */     AlgorithmConfiguration ac = new AlgorithmConfiguration(configs);
/*  451: 558 */     MainLogger.debug(ac.toString());
/*  452:     */     
/*  453: 560 */     Configurable c5 = new Configurable("TestConfigurable2", -50.0F, 2.0F, -100.0F, 10.0F);
/*  454: 561 */     ac.overwriteConfigurableWithSameName(c5);
/*  455: 562 */     MainLogger.debug(ac.toString());
/*  456:     */     
/*  457: 564 */     Configurable c6 = new Configurable("TestConfigurable1", 0.3F, 0.1F, 0.0F, 1.0F);
/*  458: 565 */     ac.overwriteConfigurableWithSameName(c6);
/*  459: 566 */     MainLogger.debug(ac.toString());
/*  460:     */     
/*  461: 568 */     Configurable c7 = new Configurable("TestConfigurable3", 10.0F, 1.0F, 0.0F, 10.0F);
/*  462: 569 */     ac.overwriteConfigurableWithSameName(c7);
/*  463: 570 */     MainLogger.debug(ac.toString());
/*  464:     */   }
/*  465:     */   
/*  466:     */   private static void testObserverPattern()
/*  467:     */   {
/*  468: 577 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29.csv";
/*  469: 578 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 10000);
/*  470: 579 */     MainLogger.debug("Simulator ticker = " + ms.ticker);
/*  471: 580 */     String ticker = ms.ticker;
/*  472: 581 */     Thread simulator = new Thread(ms);
/*  473:     */     
/*  474: 583 */     PortfolioData p = new PortfolioData(new ArrayList(), 40000.0F);
/*  475: 584 */     SimulatedTradingAccount tradingAccount = new SimulatedTradingAccount(p, new ArrayList(), ms, 0.3F, 0.0F);
/*  476: 585 */     SimulatedMarketMonitorAccount monitorAccount = new SimulatedMarketMonitorAccount(ms);
/*  477: 586 */     DataCollector dc = new DataCollector(ms, tradingAccount, monitorAccount, ticker, false);
/*  478: 587 */     monitorAccount.setDataCollector(dc);
/*  479:     */     
/*  480: 589 */     SimpleThreadedParasite algo = new SimpleThreadedParasite(dc, ticker, null);
/*  481: 590 */     algo.prepareForExecuting();
/*  482: 591 */     Thread simpleParasiteThread = new Thread(algo);
/*  483: 592 */     simpleParasiteThread.setName("SimpleParasiteThread");
/*  484: 593 */     simpleParasiteThread.setPriority(5);
/*  485: 594 */     simpleParasiteThread.start();
/*  486: 595 */     simulator.start();
/*  487:     */   }
/*  488:     */   
/*  489:     */   private static void testDelayedOrders()
/*  490:     */   {
/*  491: 601 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29.csv";
/*  492: 602 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 10000);
/*  493:     */     
/*  494: 604 */     MainLogger.debug("Simulator ticker = " + ms.ticker);
/*  495: 605 */     Thread simulator = new Thread(ms);
/*  496:     */     
/*  497:     */ 
/*  498: 608 */     StockPortfolioData spd2 = new StockPortfolioData("HT-R-A", 250.0F, 250.0F, 20);
/*  499:     */     
/*  500: 610 */     List<StockPortfolioData> list = new ArrayList();
/*  501:     */     
/*  502: 612 */     list.add(spd2);
/*  503: 613 */     PortfolioData p = new PortfolioData(list, 35000.0F);
/*  504:     */     
/*  505: 615 */     p.print();
/*  506:     */     
/*  507:     */ 
/*  508: 618 */     List<Order> ownOrders = new ArrayList();
/*  509: 619 */     ownOrders.add(new Order("HT-R-A", OrderType.BUY, 20, 280.0F));
/*  510: 620 */     SimulatedTradingAccount tradingAccount = new SimulatedTradingAccount(p, ownOrders, ms, 0.3F, 0.0F);
/*  511:     */     
/*  512:     */ 
/*  513: 623 */     Order order = new Order("HT-R-A", OrderType.SELL, 40, 280.32999F);
/*  514: 624 */     SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
/*  515: 625 */     String validString = "2011-04-29_13:11:00";
/*  516: 626 */     Date validFrom = new Date(9223372036854775807L);
/*  517:     */     try
/*  518:     */     {
/*  519: 628 */       validFrom = fullFormat.parse(validString);
/*  520:     */     }
/*  521:     */     catch (ParseException e)
/*  522:     */     {
/*  523: 630 */       MainLogger.error(e);
/*  524:     */     }
/*  525: 634 */     order.setValidFrom(validFrom);
/*  526: 635 */     tradingAccount.setOrder(order);
/*  527:     */     
/*  528:     */ 
/*  529: 638 */     simulator.start();
/*  530:     */   }
/*  531:     */   
/*  532:     */   private static void testParsingDateFromFilePath()
/*  533:     */   {
/*  534: 643 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29_v2.csv";
/*  535: 644 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 1000);
/*  536:     */     
/*  537: 646 */     String filePath2 = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29.csv";
/*  538: 647 */     CsvFileMarketSimulator ms2 = new CsvFileMarketSimulator(filePath2, 1000);
/*  539:     */     
/*  540: 649 */     ms.parseDateFromFilePath();
/*  541: 650 */     ms2.parseDateFromFilePath();
/*  542:     */   }
/*  543:     */   
/*  544:     */   private static void testNotificationsFromMarketSimulator()
/*  545:     */   {
/*  546: 656 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29.csv";
/*  547: 657 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 1000);
/*  548:     */     
/*  549: 659 */     MainLogger.debug("Simulator ticker = " + ms.ticker);
/*  550: 660 */     Thread simulator = new Thread(ms);
/*  551:     */     
/*  552:     */ 
/*  553: 663 */     StockPortfolioData spd2 = new StockPortfolioData("HT-R-A", 250.0F, 250.0F, 20);
/*  554:     */     
/*  555: 665 */     List<StockPortfolioData> list = new ArrayList();
/*  556:     */     
/*  557: 667 */     list.add(spd2);
/*  558: 668 */     PortfolioData p = new PortfolioData(list, 35000.0F);
/*  559:     */     
/*  560: 670 */     p.print();
/*  561:     */     
/*  562:     */ 
/*  563: 673 */     List<Order> ownOrders = new ArrayList();
/*  564: 674 */     ownOrders.add(new Order("HT-R-A", OrderType.BUY, 20, 280.0F));
/*  565: 675 */     SimulatedTradingAccount tradingAccount = new SimulatedTradingAccount(p, ownOrders, ms, 0.3F, 0.0F);
/*  566:     */     
/*  567: 677 */     simulator.start();
/*  568:     */   }
/*  569:     */   
/*  570:     */   private static void testCsvFileMarketSimulator()
/*  571:     */   {
/*  572: 685 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\HT-R-A_MarketData_2011-04-29.csv";
/*  573: 686 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 1000);
/*  574: 687 */     Thread simulator = new Thread(ms);
/*  575: 688 */     simulator.start();
/*  576:     */   }
/*  577:     */   
/*  578:     */   private static void testReadingAndTranscodingFiles()
/*  579:     */   {
/*  580: 693 */     testReadOldCsvFile();
/*  581: 694 */     testTranscoding();
/*  582: 695 */     testReadNewCsvFile();
/*  583:     */   }
/*  584:     */   
/*  585:     */   private static void testReadNewCsvFile()
/*  586:     */   {
/*  587: 700 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\ATPL-R-A_MarketData_2011-04-29_v2.csv";
/*  588: 701 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 25);
/*  589: 702 */     ms.loadData();
/*  590: 704 */     if (ms.status == SimulatorStatus.DATA_READY) {
/*  591: 705 */       MainLogger.debug("testReadNewCsvFile PASSED!");
/*  592:     */     }
/*  593:     */   }
/*  594:     */   
/*  595:     */   private static void testTranscoding()
/*  596:     */   {
/*  597: 711 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\ATPL-R-A_MarketData_2011-04-29.csv";
/*  598: 712 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 25);
/*  599: 713 */     ms.loadData();
/*  600: 715 */     if (ms.status != SimulatorStatus.DATA_READY)
/*  601:     */     {
/*  602: 716 */       MainLogger.info("testTranscoding FAILED! Error reading file!");
/*  603: 717 */       return;
/*  604:     */     }
/*  605: 720 */     ms.transcodeToNewFormat();
/*  606:     */     
/*  607: 722 */     String filePath2 = "C:\\ParasiteTrade\\out\\2011-04-29\\ATPL-R-A_MarketData_2011-04-29_v2.csv";
/*  608: 723 */     CsvFileMarketSimulator ms2 = new CsvFileMarketSimulator(filePath2, 25);
/*  609: 724 */     ms2.loadData();
/*  610: 726 */     if (ms.recordList.length != ms.recordList.length)
/*  611:     */     {
/*  612: 727 */       MainLogger.info("testTranscoding FAILED! Data read from new and old file are not the same length!");
/*  613: 728 */       return;
/*  614:     */     }
/*  615: 731 */     for (int i = 0; i < ms.recordList.length; i++) {
/*  616: 732 */       if (!ms.recordList[i].equals(ms2.recordList[i]))
/*  617:     */       {
/*  618: 733 */         MainLogger.info("testTranscoding FAILED! Data records at index " + i + " are not the same!");
/*  619: 734 */         MainLogger.info("Old file format data: " + ms.recordList[i].toString());
/*  620: 735 */         MainLogger.info("New file format data: " + ms2.recordList[i].toString());
/*  621: 736 */         return;
/*  622:     */       }
/*  623:     */     }
/*  624: 740 */     MainLogger.info("testTranscoding PASSED!");
/*  625:     */   }
/*  626:     */   
/*  627:     */   private static void testGetFirstNanTokenIndexAfter()
/*  628:     */   {
/*  629: 745 */     String[] tokens1 = { "1", "1", "1", "grubo", "1" };
/*  630: 746 */     String[] tokens2 = { "1", "tesko", "1", "1", "1", "1", "grubo", "1" };
/*  631: 747 */     String[] tokens3 = { "1.45", "1.5678", "1.67", "grubo", "1", "1.0", "2.456" };
/*  632:     */     
/*  633: 749 */     boolean test1 = false;
/*  634: 750 */     boolean test2 = false;
/*  635: 751 */     boolean test3 = false;
/*  636:     */     
/*  637: 753 */     MainLogger.debug(ParasiteUtils.getFirstNanTokenIndexAfter(0, tokens1));
/*  638: 754 */     if (3 == ParasiteUtils.getFirstNanTokenIndexAfter(0, tokens1)) {
/*  639: 755 */       test1 = true;
/*  640:     */     }
/*  641: 758 */     MainLogger.debug(ParasiteUtils.getFirstNanTokenIndexAfter(1, tokens2));
/*  642: 759 */     if (6 == ParasiteUtils.getFirstNanTokenIndexAfter(1, tokens2)) {
/*  643: 760 */       test2 = true;
/*  644:     */     }
/*  645: 763 */     MainLogger.debug(ParasiteUtils.getFirstNanTokenIndexAfter(3, tokens3));
/*  646: 764 */     if (-1 == ParasiteUtils.getFirstNanTokenIndexAfter(3, tokens3)) {
/*  647: 765 */       test3 = true;
/*  648:     */     }
/*  649: 768 */     if ((test1) && (test2) && (test3)) {
/*  650: 769 */       MainLogger.debug("testGetFirstNanTokenIndexAfter passed!");
/*  651:     */     } else {
/*  652: 771 */       MainLogger.debug("testGetFirstNanTokenIndexAfter failed!");
/*  653:     */     }
/*  654:     */   }
/*  655:     */   
/*  656:     */   private static void testGetFirstIndexOfToken()
/*  657:     */   {
/*  658: 776 */     String[] tokens1 = { "1", "2", "3", "grubo", "1" };
/*  659: 777 */     String[] tokens2 = { "1", "tesko", "1", "1", "7", "1", "grubo", "7" };
/*  660: 778 */     String[] tokens3 = { "1.45", "1.5678", "1.67", "grubo", "1", "1.0", "2.456" };
/*  661:     */     
/*  662: 780 */     boolean test1 = false;
/*  663: 781 */     boolean test2 = false;
/*  664: 782 */     boolean test3 = false;
/*  665:     */     
/*  666: 784 */     int t1 = ParasiteUtils.getFirstIndexOfToken("grubo", tokens1);
/*  667: 785 */     if (t1 == 3) {
/*  668: 786 */       test1 = true;
/*  669:     */     }
/*  670: 788 */     int t2 = ParasiteUtils.getFirstIndexOfToken("7", tokens2);
/*  671: 789 */     if (t2 == 4) {
/*  672: 790 */       test2 = true;
/*  673:     */     }
/*  674: 792 */     int t3 = ParasiteUtils.getFirstIndexOfToken("nema", tokens3);
/*  675: 793 */     if (t3 == -1) {
/*  676: 794 */       test3 = true;
/*  677:     */     }
/*  678: 796 */     if ((test1) && (test2) && (test3)) {
/*  679: 797 */       MainLogger.debug("testGetFirstIndexOfToken passed!");
/*  680:     */     } else {
/*  681: 799 */       MainLogger.debug("testGetFirstIndexOfToken failed!");
/*  682:     */     }
/*  683:     */   }
/*  684:     */   
/*  685:     */   private static void testReadOldCsvFile()
/*  686:     */   {
/*  687: 804 */     String filePath = "C:\\ParasiteTrade\\out\\2011-04-29\\ATPL-R-A_MarketData_2011-04-29.csv";
/*  688: 805 */     CsvFileMarketSimulator ms = new CsvFileMarketSimulator(filePath, 25);
/*  689: 806 */     ms.loadData();
/*  690: 808 */     if (ms.status == SimulatorStatus.DATA_READY) {
/*  691: 809 */       MainLogger.debug("testReadOldCsvFile PASSED!");
/*  692:     */     }
/*  693:     */   }
/*  694:     */   
/*  695:     */   private static void testCalculateStockMetrics()
/*  696:     */   {
/*  697: 815 */     String ticker = "KORF-R-A";
/*  698: 816 */     StockMetrics sm = StockMetrics.getStockMetrics(ticker);
/*  699: 817 */     MainLogger.debug("Intraday volatility:" + sm.averageIntradayVolatility);
/*  700:     */   }
/*  701:     */   
/*  702:     */   private static void testGetPendingOrdersInfo()
/*  703:     */   {
/*  704: 821 */     DataCollector dc = new DataCollector();
/*  705: 822 */     dc.tradingAccount.readPendingOrdersInfo();
/*  706: 823 */     MainLogger.debug("Zavrsio!");
/*  707:     */   }
/*  708:     */   
/*  709:     */   private static void testPrioritizedDataCollection()
/*  710:     */   {
/*  711: 828 */     DataCollector dc = new DataCollector();
/*  712:     */     
/*  713:     */ 
/*  714:     */ 
/*  715: 832 */     Thread dataCollectionThread = new Thread(new DataCollector());
/*  716: 833 */     dataCollectionThread.setName("MainDataCollectorThread");
/*  717: 834 */     dataCollectionThread.setPriority(5);
/*  718: 835 */     dataCollectionThread.start();
/*  719:     */   }
/*  720:     */   
/*  721:     */   private static void testIfConditions()
/*  722:     */   {
/*  723: 840 */     String ticker = "HT-R-A";
/*  724:     */     
/*  725:     */ 
/*  726: 843 */     List<String> highestPriorityTickers = new ArrayList();
/*  727: 844 */     List<String> highPriorityTickers = new ArrayList();
/*  728: 848 */     if ((!highestPriorityTickers.contains(ticker)) && (!highPriorityTickers.contains(ticker)))
/*  729:     */     {
/*  730: 849 */       MainLogger.debug("USO!");
/*  731: 850 */       highPriorityTickers.add("HT-R-A");
/*  732:     */     }
/*  733: 852 */     MainLogger.debug("PROSO!");
/*  734:     */   }
/*  735:     */   
/*  736:     */   private static void testCalculateMovingAverages()
/*  737:     */   {
/*  738: 857 */     ImplicitStockMarketData isd = new ImplicitStockMarketData();
/*  739:     */     
/*  740: 859 */     isd.baRatioQueue.add(Float.valueOf(100.0F));
/*  741: 860 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  742: 861 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  743: 862 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  744: 863 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  745: 864 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  746: 865 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  747: 866 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  748: 867 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  749: 868 */     isd.baRatioQueue.add(Float.valueOf(10.0F));
/*  750:     */     
/*  751: 870 */     isd.calculateMovingAverages();
/*  752:     */     
/*  753: 872 */     MainLogger.debug("SMA = " + isd.baRatioSma);
/*  754: 873 */     MainLogger.debug("WMA = " + isd.baRatioWma);
/*  755:     */   }
/*  756:     */   
/*  757:     */   private static void testSimpleParasiteThread()
/*  758:     */   {
/*  759: 878 */     DataCollector dc = new DataCollector();
/*  760: 879 */     Thread simpleParasiteThread = new Thread(new SimpleThreadedParasite(dc, "HT-R-A", null));
/*  761: 880 */     simpleParasiteThread.setName("SimpleParasiteThread");
/*  762: 881 */     simpleParasiteThread.setPriority(5);
/*  763: 882 */     simpleParasiteThread.start();
/*  764:     */   }
/*  765:     */   
/*  766:     */   private static void testSimpleParasiteUnderWatchState()
/*  767:     */   {
/*  768: 888 */     DataCollector dc = new DataCollector();
/*  769: 889 */     String ticker = "ATPL-R-A";
/*  770:     */     
/*  771: 891 */     StockMarketData md = new StockMarketData(dc, ticker);
/*  772:     */     
/*  773: 893 */     md.implicitData.baRatioWma = 1.1F;
/*  774: 894 */     md.implicitData.baRatioWmaDelta = 0.5F;
/*  775: 895 */     Thread simpleParasiteThread = new Thread(new SimpleThreadedParasite(dc, StockState.UNDER_WATCH, ticker, null));
/*  776:     */     
/*  777: 897 */     simpleParasiteThread.setName("SimpleParasite_for_" + ticker);
/*  778: 898 */     simpleParasiteThread.setPriority(5);
/*  779: 899 */     simpleParasiteThread.start();
/*  780:     */   }
/*  781:     */   
/*  782:     */   private static void testStockMarketDataQueues()
/*  783:     */   {
/*  784: 903 */     Thread dataCollectionThread = new Thread(new DataCollector());
/*  785: 904 */     dataCollectionThread.setName("DataCollectorThread");
/*  786: 905 */     dataCollectionThread.setPriority(5);
/*  787: 906 */     dataCollectionThread.start();
/*  788:     */     try
/*  789:     */     {
/*  790: 909 */       Thread.sleep(10000L);
/*  791:     */     }
/*  792:     */     catch (InterruptedException e)
/*  793:     */     {
/*  794: 911 */       MainLogger.error(e);
/*  795:     */     }
/*  796:     */   }
/*  797:     */   
/*  798:     */   private static void testCalculateOrdersValue()
/*  799:     */   {
/*  800: 929 */     DataCollector dc = new DataCollector();
/*  801: 930 */     StockMarketData marketData = new StockMarketData(dc, "ATGR-R-A");
/*  802: 931 */     marketData.askList.add(new Order("ATGR-R-A", OrderType.SELL, 1, 100.0F));
/*  803: 932 */     marketData.askList.add(new Order("ATGR-R-A", OrderType.SELL, 1, 101.0F));
/*  804: 933 */     marketData.askList.add(new Order("ATGR-R-A", OrderType.SELL, 1, 102.0F));
/*  805: 934 */     marketData.askList.add(new Order("ATGR-R-A", OrderType.SELL, 1, 103.0F));
/*  806: 935 */     marketData.askList.add(new Order("ATGR-R-A", OrderType.SELL, 1, 104.0F));
/*  807: 936 */     marketData.askList.add(new Order("ATGR-R-A", OrderType.SELL, 1, 105.0F));
/*  808: 937 */     marketData.askList.add(new Order("ATGR-R-A", OrderType.SELL, 1, 106.0F));
/*  809:     */     
/*  810: 939 */     marketData.bidList.add(new Order("ATGR-R-A", OrderType.BUY, 1, 100.0F));
/*  811: 940 */     marketData.bidList.add(new Order("ATGR-R-A", OrderType.BUY, 1, 99.0F));
/*  812: 941 */     marketData.bidList.add(new Order("ATGR-R-A", OrderType.BUY, 1, 98.0F));
/*  813: 942 */     marketData.bidList.add(new Order("ATGR-R-A", OrderType.BUY, 1, 97.0F));
/*  814: 943 */     marketData.bidList.add(new Order("ATGR-R-A", OrderType.BUY, 1, 96.0F));
/*  815: 944 */     marketData.bidList.add(new Order("ATGR-R-A", OrderType.BUY, 1, 95.0F));
/*  816: 945 */     marketData.bidList.add(new Order("ATGR-R-A", OrderType.BUY, 1, 94.0F));
/*  817: 947 */     for (int p = 1; p < 7; p++)
/*  818:     */     {
/*  819: 948 */       MainLogger.debug("---------------------------------");
/*  820: 949 */       float aval = marketData.implicitData.calculateAskOrdersValue(p);
/*  821: 950 */       MainLogger.debug("Ask value = " + aval);
/*  822: 951 */       float bval = marketData.implicitData.calculateBidOrdersValue(p);
/*  823: 952 */       MainLogger.debug("Bid value = " + bval);
/*  824:     */     }
/*  825:     */   }
/*  826:     */   
/*  827:     */   private static void testArrayLists()
/*  828:     */   {
/*  829: 972 */     Object t = new Object()
/*  830:     */     {
/*  831: 960 */       public ArrayList<Integer> tList = new ArrayList(3);
/*  832: 961 */       public ConcurrentLinkedQueue<ArrayList<Integer>> tFifoQueue = new ConcurrentLinkedQueue();
/*  833:     */       
/*  834:     */       public void enqueue()
/*  835:     */       {
/*  836: 964 */         this.tFifoQueue.add(new ArrayList(this.tList));
/*  837: 965 */         if (this.tFifoQueue.size() > 5) {
/*  838: 966 */           this.tFifoQueue.poll();
/*  839:     */         }
/*  840: 967 */         this.tList.clear();
/*  841:     */       }
/*  842:     */     };
/*  843: 973 */     for (int n = 0; n < 20; n++)
/*  844:     */     {
/*  845: 974 */       t.tList.add(Integer.valueOf(n));
/*  846: 975 */       t.tList.add(Integer.valueOf(n));
/*  847: 976 */       t.enqueue();
/*  848:     */       
/*  849: 978 */       Iterator i = t.tFifoQueue.iterator();
/*  850: 979 */       MainLogger.debug("********************** Queue size = " + t.tFifoQueue.size());
/*  851:     */       Iterator k;
/*  852: 980 */       for (; i.hasNext(); k.hasNext())
/*  853:     */       {
/*  854: 981 */         ArrayList a = (ArrayList)i.next();
/*  855: 982 */         k = a.iterator();
/*  856: 983 */         continue;
/*  857: 984 */         Integer in = (Integer)k.next();
/*  858: 985 */         MainLogger.debug(in);
/*  859:     */       }
/*  860:     */     }
/*  861:     */   }
/*  862:     */   
/*  863:     */   private static void testGetStockHistoryData()
/*  864:     */   {
/*  865: 993 */     DataCollector dc = new DataCollector();
/*  866: 994 */     HistoryDataProvider historyProvider = new VirtualnaBurza(dc);
/*  867: 995 */     historyProvider.readDaySummariesFromWeb("ATGR-R-A");
/*  868:     */   }
/*  869:     */   
/*  870:     */   private static void testAddingAndRemovingInterestingStock()
/*  871:     */   {
/*  872:1000 */     String ticker = "ADPL-R-A";
/*  873:1001 */     Thread dataCollectionThread = new Thread(new DataCollector());
/*  874:1002 */     dataCollectionThread.start();
/*  875:     */     try
/*  876:     */     {
/*  877:1005 */       Thread.sleep(60000L);
/*  878:     */     }
/*  879:     */     catch (InterruptedException e)
/*  880:     */     {
/*  881:1007 */       MainLogger.error(e);
/*  882:     */     }
/*  883:1009 */     MainLogger.debug("Adding interesting ticker: " + ticker);
/*  884:1010 */     new DataCollector().addInterestingTicker(ticker);
/*  885:     */     try
/*  886:     */     {
/*  887:1013 */       Thread.sleep(60000L);
/*  888:     */     }
/*  889:     */     catch (InterruptedException e)
/*  890:     */     {
/*  891:1015 */       MainLogger.error(e);
/*  892:     */     }
/*  893:1018 */     MainLogger.debug("Removing not interesting ticker: " + ticker);
/*  894:1019 */     new DataCollector().removeNotInterestingTicker(ticker);
/*  895:     */   }
/*  896:     */   
/*  897:     */   private static void testCollectingZseDataAtFixedTime()
/*  898:     */   {
/*  899:1024 */     Date now = new Date();
/*  900:1025 */     int seconds = 10;
/*  901:1026 */     MainLogger.debug("Starting to collect data in " + seconds + " seconds");
/*  902:1027 */     new DataCollector().startSimpleDataCollection(new Date(now.getTime() + seconds * 1000));
/*  903:     */   }
/*  904:     */   
/*  905:     */   public static void testCollectingZseData()
/*  906:     */   {
/*  907:1033 */     Thread dataCollectionThread = new Thread(new DataCollector());
/*  908:1034 */     dataCollectionThread.start();
/*  909:     */   }
/*  910:     */   
/*  911:     */   public static void testWriteToCsvFile()
/*  912:     */   {
/*  913:1039 */     List<String> tickerList = new ArrayList();
/*  914:1040 */     tickerList.add("ATPL-R-A");
/*  915:1041 */     tickerList.add("ADRS-P-A");
/*  916:1042 */     tickerList.add("ATGR-R-A");
/*  917:1043 */     tickerList.add("HT-R-A");
/*  918:1044 */     tickerList.add("INA-R-A");
/*  919:1045 */     tickerList.add("KOEI-R-A");
/*  920:     */     
/*  921:     */ 
/*  922:1048 */     DataCollector dc = new DataCollector();
/*  923:1049 */     MarketMonitorAccount marketMonitor = new FimaEtrade(dc);
/*  924:1050 */     marketMonitor.refreshMarketDataFromWeb(tickerList);
/*  925:     */     
/*  926:1052 */     List<StockMarketData> mmdList = new DataCollector().getMarketData();
/*  927:1053 */     Iterator k = mmdList.iterator();
/*  928:1054 */     while (k.hasNext())
/*  929:     */     {
/*  930:1055 */       StockMarketData mmd = (StockMarketData)k.next();
/*  931:     */       
/*  932:1057 */       Iterator i1 = mmd.bidList.iterator();
/*  933:1058 */       while (i1.hasNext())
/*  934:     */       {
/*  935:1059 */         Order abi = (Order)i1.next();
/*  936:1060 */         MainLogger.debug(abi.toString());
/*  937:     */       }
/*  938:1062 */       Iterator i2 = mmd.askList.iterator();
/*  939:1063 */       while (i2.hasNext())
/*  940:     */       {
/*  941:1064 */         Order abi2 = (Order)i2.next();
/*  942:1065 */         MainLogger.debug(abi2.toString());
/*  943:     */       }
/*  944:1073 */       Iterator i3 = mmd.transactionList.iterator();
/*  945:1074 */       while (i3.hasNext())
/*  946:     */       {
/*  947:1075 */         Transaction tin = (Transaction)i3.next();
/*  948:1076 */         MainLogger.debug(tin.toString());
/*  949:     */       }
/*  950:1078 */       mmd.appendToCsvFile();
/*  951:     */     }
/*  952:     */   }
/*  953:     */   
/*  954:     */   public static void testGetMarketDataForMultipleTickers()
/*  955:     */   {
/*  956:1086 */     List<String> tickerList = new ArrayList();
/*  957:1087 */     tickerList.add("ADRS-P-A");
/*  958:1088 */     tickerList.add("HT-R-A");
/*  959:1089 */     tickerList.add("ACI-R-A");
/*  960:1090 */     tickerList.add("3MAJ-R-A");
/*  961:     */     
/*  962:     */ 
/*  963:1093 */     DataCollector dc = new DataCollector();
/*  964:1094 */     MarketMonitorAccount marketMonitor = new FimaEtrade(dc);
/*  965:1095 */     marketMonitor.refreshMarketDataFromWeb(tickerList);
/*  966:     */     
/*  967:1097 */     List<StockMarketData> md = new DataCollector().getMarketData();
/*  968:     */     
/*  969:1099 */     Iterator k = md.iterator();
/*  970:     */     Iterator i3;
/*  971:1100 */     for (; k.hasNext(); i3.hasNext())
/*  972:     */     {
/*  973:1101 */       StockMarketData mmd = (StockMarketData)k.next();
/*  974:     */       
/*  975:1103 */       Iterator i1 = mmd.bidList.iterator();
/*  976:1104 */       while (i1.hasNext())
/*  977:     */       {
/*  978:1105 */         Order abi = (Order)i1.next();
/*  979:1106 */         MainLogger.debug(abi.toString());
/*  980:     */       }
/*  981:1108 */       Iterator i2 = mmd.askList.iterator();
/*  982:1109 */       while (i2.hasNext())
/*  983:     */       {
/*  984:1110 */         Order abi2 = (Order)i2.next();
/*  985:1111 */         MainLogger.debug(abi2.toString());
/*  986:     */       }
/*  987:1114 */       i3 = mmd.transactionList.iterator();
/*  988:1115 */       continue;
/*  989:1116 */       Transaction tin = (Transaction)i3.next();
/*  990:1117 */       MainLogger.debug(tin.toString());
/*  991:     */     }
/*  992:     */   }
/*  993:     */   
/*  994:     */   public static void testSetBuyOrders()
/*  995:     */   {
/*  996:1124 */     Date now = new Date();
/*  997:1125 */     Date expiryDate = new Date(now.getYear(), now.getMonth(), now.getDate() + 3);
/*  998:     */     
/*  999:1127 */     DataCollector dc = new DataCollector();
/* 1000:1128 */     TradingAccount tradingAccount = new VirtualnaBurza(dc);
/* 1001:     */     
/* 1002:1130 */     tradingAccount.setBuyOrder("HDEL-R-A", 20.0F, 3, null);
/* 1003:1131 */     tradingAccount.setBuyOrder("ATPL-R-A", 10.0F, 5, expiryDate);
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public static void testCancelBuyOrder()
/* 1007:     */   {
/* 1008:1136 */     DataCollector dc = new DataCollector();
/* 1009:1137 */     TradingAccount tradingAccount = new VirtualnaBurza(dc);
/* 1010:1138 */     tradingAccount.setBuyOrder("HDEL-R-A", 20.0F, 3, null);
/* 1011:     */     try
/* 1012:     */     {
/* 1013:1141 */       Thread.sleep(15000L);
/* 1014:     */     }
/* 1015:     */     catch (InterruptedException e)
/* 1016:     */     {
/* 1017:1143 */       MainLogger.error(e);
/* 1018:     */     }
/* 1019:1146 */     tradingAccount.cancelBuyOrder("HDEL-R-A");
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public static void testSetSellOrders()
/* 1023:     */   {
/* 1024:1151 */     Date now = new Date();
/* 1025:1152 */     Date expiryDate = new Date(now.getYear(), now.getMonth(), now.getDate() + 3);
/* 1026:     */     
/* 1027:1154 */     DataCollector dc = new DataCollector();
/* 1028:1155 */     TradingAccount tradingAccount = new VirtualnaBurza(dc);
/* 1029:     */     
/* 1030:1157 */     tradingAccount.setSellOrder("HDEL-R-A", 4000.0F, 3, null);
/* 1031:1158 */     tradingAccount.setSellOrder("ATPL-R-A", 4000.0F, 5, expiryDate);
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public static void testGetBidListForTicker()
/* 1035:     */   {
/* 1036:1164 */     DataCollector dc = new DataCollector();
/* 1037:1165 */     MarketMonitorAccount marketMonitor = new FimaEtrade(dc);
/* 1038:     */     
/* 1039:1167 */     List bidList = marketMonitor.readBidListFromWeb("ATPL-R-A");
/* 1040:1168 */     Iterator bi = bidList.iterator();
/* 1041:1169 */     while (bi.hasNext())
/* 1042:     */     {
/* 1043:1170 */       Order abi = (Order)bi.next();
/* 1044:1171 */       MainLogger.debug(abi.toString());
/* 1045:     */     }
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public static void testGetAskListForTicker()
/* 1049:     */   {
/* 1050:1177 */     DataCollector dc = new DataCollector();
/* 1051:1178 */     MarketMonitorAccount marketMonitor = new FimaEtrade(dc);
/* 1052:     */     
/* 1053:1180 */     List askList = marketMonitor.readAskListFromWeb("ATPL-R-A");
/* 1054:1181 */     Iterator ai = askList.iterator();
/* 1055:1182 */     while (ai.hasNext())
/* 1056:     */     {
/* 1057:1183 */       Order abi2 = (Order)ai.next();
/* 1058:1184 */       MainLogger.debug(abi2.toString());
/* 1059:     */     }
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public static void testGetTransactionListForTicker()
/* 1063:     */   {
/* 1064:1190 */     DataCollector dc = new DataCollector();
/* 1065:1191 */     MarketMonitorAccount marketMonitor = new FimaEtrade(dc);
/* 1066:     */     
/* 1067:1193 */     List tList = marketMonitor.readTransactionListFromWeb("ATPL-R-A");
/* 1068:1194 */     Iterator ti = tList.iterator();
/* 1069:1195 */     while (ti.hasNext())
/* 1070:     */     {
/* 1071:1196 */       Transaction tin = (Transaction)ti.next();
/* 1072:1197 */       MainLogger.debug(tin.toString());
/* 1073:     */     }
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public static void testGetPortfolioInfo()
/* 1077:     */   {
/* 1078:1203 */     DataCollector dc = new DataCollector();
/* 1079:1204 */     TradingAccount tradingAccount = new VirtualnaBurza(dc);
/* 1080:     */     
/* 1081:1206 */     PortfolioData portfolio = tradingAccount.readPortfolioData();
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public static void testGetMarketDataForSingleTicker()
/* 1085:     */   {
/* 1086:1210 */     MainLogger.debug("\n\n\n");
/* 1087:     */     
/* 1088:1212 */     DataCollector dc = new DataCollector();
/* 1089:1213 */     MarketMonitorAccount marketMonitor = new FimaEtrade(dc);
/* 1090:1214 */     StockMarketData mmd = marketMonitor.readMarketDataFromWeb("ACI-R-A");
/* 1091:     */     
/* 1092:1216 */     Iterator i1 = mmd.bidList.iterator();
/* 1093:1217 */     while (i1.hasNext())
/* 1094:     */     {
/* 1095:1218 */       Order abi = (Order)i1.next();
/* 1096:1219 */       MainLogger.debug(abi.toString());
/* 1097:     */     }
/* 1098:1221 */     Iterator i2 = mmd.askList.iterator();
/* 1099:1222 */     while (i2.hasNext())
/* 1100:     */     {
/* 1101:1223 */       Order abi2 = (Order)i2.next();
/* 1102:1224 */       MainLogger.debug(abi2.toString());
/* 1103:     */     }
/* 1104:1227 */     Transaction t = new Transaction();
/* 1105:1228 */     t.amount = 12;
/* 1106:1229 */     t.time = new Date();
/* 1107:1230 */     t.isOwn = false;
/* 1108:1231 */     t.price = 123.45F;
/* 1109:1232 */     t.ticker = "ACI-R-A";
/* 1110:1233 */     t.value = (t.amount * t.price);
/* 1111:1234 */     mmd.transactionList.add(t);
/* 1112:     */     
/* 1113:1236 */     Iterator i3 = mmd.transactionList.iterator();
/* 1114:1237 */     while (i3.hasNext())
/* 1115:     */     {
/* 1116:1238 */       Transaction tin = (Transaction)i3.next();
/* 1117:1239 */       MainLogger.debug(tin.toString());
/* 1118:     */     }
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public static void testDatePrint()
/* 1122:     */   {
/* 1123:1244 */     MainLogger.debug("\n\n\n");
/* 1124:1245 */     String dateString = "23.5.2011 15:34:45";
/* 1125:1246 */     MainLogger.debug("DATE PRINT: " + dateString);
/* 1126:1247 */     Format formatter = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
/* 1127:1248 */     Date date = null;
/* 1128:     */     try
/* 1129:     */     {
/* 1130:1250 */       date = (Date)formatter.parseObject(dateString);
/* 1131:     */     }
/* 1132:     */     catch (ParseException e)
/* 1133:     */     {
/* 1134:1252 */       MainLogger.debug(e);
/* 1135:     */     }
/* 1136:1254 */     MainLogger.debug("TEST DATE PRINT: " + formatter.format(date));
/* 1137:     */   }
/* 1138:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.test.Main
 * JD-Core Version:    0.7.0.1
 */