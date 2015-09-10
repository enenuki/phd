/*   1:    */ package hr.nukic.parasite.core;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Properties;
/*   8:    */ import java.util.Set;
/*   9:    */ import nukic.parasite.utils.MainLogger;
/*  10:    */ 
/*  11:    */ public class ParasiteManager
/*  12:    */ {
/*  13: 14 */   public static String propertiesFilePath = "res/parasite.properties";
/*  14: 16 */   private static ParasiteManager instance = null;
/*  15:    */   
/*  16:    */   public static ParasiteManager getInstance()
/*  17:    */   {
/*  18: 19 */     if (instance == null) {
/*  19: 20 */       instance = new ParasiteManager();
/*  20:    */     }
/*  21: 22 */     return instance;
/*  22:    */   }
/*  23:    */   
/*  24: 25 */   public Properties properties = null;
/*  25: 26 */   public int runMode = 0;
/*  26: 27 */   public String outDir = "";
/*  27: 28 */   public String mainDir = "";
/*  28: 29 */   public String configDictFilePath = "";
/*  29: 30 */   public String evaluationReportsFolder = "";
/*  30: 31 */   public int portfolioEvaluationStartingAmount = 40000;
/*  31: 32 */   public int evaluationSimulatorTimeScale = 10000;
/*  32: 33 */   public String proxy = "";
/*  33: 34 */   public boolean useProxy = false;
/*  34:    */   
/*  35:    */   private ParasiteManager()
/*  36:    */   {
/*  37: 39 */     readProperties();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static String getProperty(String propertyName)
/*  41:    */   {
/*  42: 43 */     return getInstance().properties.getProperty(propertyName);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void readProperties()
/*  46:    */   {
/*  47: 47 */     MainLogger.info("---------------------------------------------------------------");
/*  48: 48 */     MainLogger.info("Reading properties file: " + propertiesFilePath);
/*  49:    */     try
/*  50:    */     {
/*  51: 51 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(propertiesFilePath);
/*  52: 52 */       this.properties = new Properties();
/*  53: 53 */       if (is != null) {
/*  54:    */         try
/*  55:    */         {
/*  56: 55 */           this.properties.load(is);
/*  57:    */         }
/*  58:    */         catch (IOException ioe)
/*  59:    */         {
/*  60: 57 */           MainLogger.error(ioe);
/*  61:    */         }
/*  62:    */       } else {
/*  63: 60 */         MainLogger.info("Properties file not found! Using defaults...");
/*  64:    */       }
/*  65: 63 */       MainLogger.info("---------------------------------------------------------------");
/*  66: 64 */       Iterator<Map.Entry<Object, Object>> i = this.properties.entrySet().iterator();
/*  67: 65 */       while (i.hasNext())
/*  68:    */       {
/*  69: 66 */         Map.Entry<Object, Object> entry = (Map.Entry)i.next();
/*  70: 67 */         MainLogger.info("Using property " + (String)entry.getKey() + " with value: " + (String)entry.getValue());
/*  71:    */       }
/*  72: 69 */       MainLogger.info("---------------------------------------------------------------");
/*  73:    */       
/*  74: 71 */       this.runMode = Integer.parseInt(this.properties.getProperty("MODE"));
/*  75: 72 */       this.outDir = this.properties.getProperty("OUT_DIR");
/*  76: 73 */       this.mainDir = this.properties.getProperty("DIR");
/*  77: 74 */       this.configDictFilePath = this.properties.getProperty("CONFIGURATION_DICTIONARY_FILE");
/*  78: 75 */       this.evaluationReportsFolder = this.properties.getProperty("EVALUATION_REPORTS_FOLDER");
/*  79: 76 */       this.portfolioEvaluationStartingAmount = Integer.parseInt(this.properties.getProperty("PORTFOLIO_EVALUATION_STARTING_AMOUNT"));
/*  80: 77 */       this.evaluationSimulatorTimeScale = Integer.parseInt(this.properties.getProperty("EVALUATION_SIMULATOR_TIME_SCALE"));
/*  81: 78 */       this.proxy = this.properties.getProperty("ETK_PROXY");
/*  82: 79 */       this.useProxy = Boolean.parseBoolean(this.properties.getProperty("USE_PROXY"));
/*  83:    */     }
/*  84:    */     catch (Exception ex)
/*  85:    */     {
/*  86: 84 */       MainLogger.info("Exception: " + ex.getMessage());
/*  87: 85 */       ex.printStackTrace();
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void start()
/*  92:    */   {
/*  93: 90 */     switch (this.runMode)
/*  94:    */     {
/*  95:    */     case 0: 
/*  96: 92 */       runAsDataCollector();
/*  97: 93 */       break;
/*  98:    */     case 1: 
/*  99:    */       break;
/* 100:    */     case 2: 
/* 101:    */       break;
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void runAsDataCollector()
/* 106:    */   {
/* 107:106 */     DataCollector dc = new DataCollector();
/* 108:107 */     dc.setRunMode(this.runMode);
/* 109:108 */     Thread dataCollectionThread = new Thread(dc);
/* 110:109 */     dataCollectionThread.setName("MainDataCollectorThread");
/* 111:110 */     dataCollectionThread.setPriority(5);
/* 112:111 */     dataCollectionThread.start();
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.ParasiteManager
 * JD-Core Version:    0.7.0.1
 */