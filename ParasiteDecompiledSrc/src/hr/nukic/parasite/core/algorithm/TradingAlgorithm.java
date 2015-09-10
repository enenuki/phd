/*   1:    */ package hr.nukic.parasite.core.algorithm;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.core.DataCollector;
/*   4:    */ import hr.nukic.parasite.core.StockMarketData;
/*   5:    */ import hr.nukic.parasite.core.StockState;
/*   6:    */ import hr.nukic.parasite.core.algorithm.configurations.AlgorithmConfiguration;
/*   7:    */ import hr.nukic.parasite.core.algorithm.configurations.Configurable;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Observer;
/*  13:    */ import nukic.parasite.utils.MainLogger;
/*  14:    */ 
/*  15:    */ public abstract class TradingAlgorithm
/*  16:    */   implements Observer
/*  17:    */ {
/*  18: 17 */   public List<String> configurableNames = new ArrayList();
/*  19:    */   public AlgorithmConfiguration config;
/*  20: 19 */   public StockState stockState = StockState.NOT_INTERESTING;
/*  21: 20 */   public String ticker = "";
/*  22:    */   public DataCollector dataCollector;
/*  23: 22 */   public boolean runningInSeparateThread = false;
/*  24: 23 */   public boolean isConfigCorrect = true;
/*  25: 24 */   public StockMarketData marketData = null;
/*  26: 26 */   float relativeProfit = 0.0F;
/*  27: 27 */   float absoluteProfit = 0.0F;
/*  28:    */   
/*  29:    */   public TradingAlgorithm(DataCollector dc, String ticker, AlgorithmConfiguration config)
/*  30:    */   {
/*  31: 46 */     this.dataCollector = dc;
/*  32: 47 */     this.stockState = StockState.NOT_INTERESTING;
/*  33: 48 */     this.ticker = ticker;
/*  34: 49 */     becomeObserver();
/*  35:    */     
/*  36: 51 */     this.config = config;
/*  37: 52 */     fillConfigurableNameList();
/*  38: 53 */     if ((config != null) && (!config.configurables.isEmpty()))
/*  39:    */     {
/*  40: 54 */       this.isConfigCorrect = checkIsConfigCorrect(config);
/*  41: 55 */       if (!this.isConfigCorrect)
/*  42:    */       {
/*  43: 56 */         MainLogger.error("ERROR: Configuration is not correct!");
/*  44: 57 */         return;
/*  45:    */       }
/*  46: 59 */       MainLogger.debug("Algorithm configuration is correct!");
/*  47: 60 */       MainLogger.debug(config.toString());
/*  48:    */     }
/*  49: 61 */     else if (config == null)
/*  50:    */     {
/*  51: 62 */       MainLogger.info("Algorithm configuration is null!");
/*  52: 63 */       this.config = new AlgorithmConfiguration(new ArrayList());
/*  53: 64 */       loadDefaultConfiguration();
/*  54:    */     }
/*  55: 65 */     else if (config.configurables.isEmpty())
/*  56:    */     {
/*  57: 66 */       MainLogger.info("Algorithm configuration configurable list is empty!");
/*  58: 67 */       loadDefaultConfiguration();
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public TradingAlgorithm(DataCollector dc, StockState stockState, String ticker, AlgorithmConfiguration config)
/*  63:    */   {
/*  64: 72 */     this.dataCollector = dc;
/*  65: 73 */     this.stockState = stockState;
/*  66: 74 */     this.ticker = ticker;
/*  67: 75 */     becomeObserver();
/*  68:    */     
/*  69: 77 */     this.config = config;
/*  70: 78 */     fillConfigurableNameList();
/*  71: 79 */     if ((config != null) && (!config.configurables.isEmpty()))
/*  72:    */     {
/*  73: 80 */       this.isConfigCorrect = checkIsConfigCorrect(config);
/*  74: 81 */       if (!this.isConfigCorrect) {
/*  75: 82 */         MainLogger.error("ERROR: Configuration is not correct!");
/*  76:    */       }
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80: 87 */       if (config == null)
/*  81:    */       {
/*  82: 88 */         MainLogger.info("Algorithm configuration is null!");
/*  83: 89 */         this.config = new AlgorithmConfiguration(new ArrayList());
/*  84: 90 */         loadDefaultConfiguration();
/*  85: 91 */         return;
/*  86:    */       }
/*  87: 92 */       if (config.configurables.isEmpty())
/*  88:    */       {
/*  89: 93 */         MainLogger.info("Algorithm configuration configurable list is empty!");
/*  90: 94 */         loadDefaultConfiguration();
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   @Deprecated
/*  96:    */   private boolean checkIsConfigCorrectFast(AlgorithmConfiguration config)
/*  97:    */   {
/*  98:102 */     Iterator<Configurable> i = this.config.configurables.iterator();
/*  99:103 */     while (i.hasNext())
/* 100:    */     {
/* 101:104 */       Configurable c = (Configurable)i.next();
/* 102:105 */       if (!this.configurableNames.contains(c.name)) {
/* 103:106 */         return false;
/* 104:    */       }
/* 105:    */     }
/* 106:109 */     return true;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private boolean checkIsConfigCorrect(AlgorithmConfiguration config)
/* 110:    */   {
/* 111:113 */     Iterator<String> i = this.configurableNames.iterator();
/* 112:114 */     while (i.hasNext())
/* 113:    */     {
/* 114:115 */       String c = (String)i.next();
/* 115:116 */       if (this.config.getConfigurableByName(c) == null)
/* 116:    */       {
/* 117:117 */         MainLogger.info("Missing at least one configurable! First one detected missing is: " + c);
/* 118:118 */         return false;
/* 119:    */       }
/* 120:    */     }
/* 121:121 */     return true;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void loadDefaultConfiguration()
/* 125:    */   {
/* 126:125 */     MainLogger.info("Loading default configuration...");
/* 127:126 */     Iterator<String> i = this.configurableNames.iterator();
/* 128:127 */     while (i.hasNext())
/* 129:    */     {
/* 130:128 */       String c = (String)i.next();
/* 131:129 */       Configurable conf = this.config.getConfigurableByName(c);
/* 132:130 */       if (conf != null)
/* 133:    */       {
/* 134:131 */         MainLogger.info("Using existing configurable...");
/* 135:132 */         conf.value = conf.defaultValue;
/* 136:133 */         conf.calculateOrdinal();
/* 137:    */       }
/* 138:    */       else
/* 139:    */       {
/* 140:135 */         MainLogger.info("Creating new configurable...");
/* 141:136 */         conf = (Configurable)Configurable.dictionary.get(c);
/* 142:137 */         conf.value = conf.defaultValue;
/* 143:138 */         conf.calculateOrdinal();
/* 144:139 */         this.config.configurables.add(conf);
/* 145:    */       }
/* 146:    */     }
/* 147:143 */     this.config.generateFullName();
/* 148:144 */     this.config.generateVersionString();
/* 149:    */   }
/* 150:    */   
/* 151:    */   private void becomeObserver()
/* 152:    */   {
/* 153:150 */     this.marketData = this.dataCollector.getStockMarketData(this.ticker);
/* 154:151 */     if (this.marketData == null)
/* 155:    */     {
/* 156:152 */       this.marketData = new StockMarketData(this.dataCollector, this.ticker);
/* 157:153 */       this.dataCollector.addStockMarketData(this.marketData);
/* 158:    */     }
/* 159:155 */     this.marketData.addObserver(this);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public abstract void fillConfigurableNameList();
/* 163:    */   
/* 164:    */   public abstract void processSellCandidateState();
/* 165:    */   
/* 166:    */   public abstract void processNotInterestingState();
/* 167:    */   
/* 168:    */   public abstract void processProfitableState();
/* 169:    */   
/* 170:    */   public abstract void processNotProfitableState();
/* 171:    */   
/* 172:    */   public abstract void processBuyCandidateState();
/* 173:    */   
/* 174:    */   public abstract void processUnderWatchState();
/* 175:    */   
/* 176:    */   public abstract void generateBuyOrder(float paramFloat);
/* 177:    */   
/* 178:    */   public abstract void generateSellOrder(float paramFloat);
/* 179:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.TradingAlgorithm
 * JD-Core Version:    0.7.0.1
 */