/*   1:    */ package hr.nukic.parasite.core.algorithm.configurations;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.core.ParasiteManager;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.text.Format;
/*   8:    */ import java.text.SimpleDateFormat;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Properties;
/*  14:    */ import jxl.Workbook;
/*  15:    */ import jxl.write.Alignment;
/*  16:    */ import jxl.write.Label;
/*  17:    */ import jxl.write.VerticalAlignment;
/*  18:    */ import jxl.write.WritableCellFormat;
/*  19:    */ import jxl.write.WritableFont;
/*  20:    */ import jxl.write.WritableSheet;
/*  21:    */ import jxl.write.WritableWorkbook;
/*  22:    */ import jxl.write.WriteException;
/*  23:    */ import jxl.write.biff.RowsExceededException;
/*  24:    */ import nukic.parasite.utils.MainLogger;
/*  25:    */ import nukic.parasite.utils.ParasiteFileUtils;
/*  26:    */ 
/*  27:    */ public class ConfigurationEvaluationSuite
/*  28:    */ {
/*  29:    */   public AlgorithmConfiguration configToBeEvaluated;
/*  30: 39 */   public String csvFileFolder = "";
/*  31: 40 */   public List<String> csvFilePathList = new ArrayList();
/*  32: 41 */   public boolean isSingleStockSuite = false;
/*  33: 42 */   public List<ConfigurationEvaluation> evaluationList = new ArrayList();
/*  34:    */   public AlgorithmScore totalScore;
/*  35: 44 */   private int timeScale = 10000;
/*  36:    */   private Date executionDate;
/*  37:    */   
/*  38:    */   public ConfigurationEvaluationSuite(AlgorithmConfiguration configToBeEvaluated, List<String> csvFilePathList)
/*  39:    */   {
/*  40: 48 */     this.configToBeEvaluated = configToBeEvaluated;
/*  41: 49 */     this.csvFilePathList = csvFilePathList;
/*  42: 50 */     this.executionDate = new Date();
/*  43: 51 */     configToBeEvaluated.setEvaluationSuite(this);
/*  44:    */     
/*  45: 53 */     this.timeScale = Integer.parseInt(ParasiteManager.getInstance().properties
/*  46: 54 */       .getProperty("EVALUATION_SIMULATOR_TIME_SCALE"));
/*  47: 56 */     if ((csvFilePathList != null) && (!csvFilePathList.isEmpty()))
/*  48:    */     {
/*  49: 58 */       Iterator<String> i = csvFilePathList.iterator();
/*  50: 59 */       while (i.hasNext())
/*  51:    */       {
/*  52: 60 */         String csvFilePath = (String)i.next();
/*  53: 61 */         this.evaluationList.add(new ConfigurationEvaluation(configToBeEvaluated, csvFilePath));
/*  54:    */       }
/*  55: 64 */       Iterator<ConfigurationEvaluation> k = this.evaluationList.iterator();
/*  56: 65 */       String firstTicker = ((ConfigurationEvaluation)this.evaluationList.get(0)).ticker;
/*  57: 66 */       while (k.hasNext())
/*  58:    */       {
/*  59: 67 */         ConfigurationEvaluation eval = (ConfigurationEvaluation)k.next();
/*  60: 68 */         if (!eval.ticker.equals(firstTicker))
/*  61:    */         {
/*  62: 69 */           this.isSingleStockSuite = false;
/*  63: 70 */           break;
/*  64:    */         }
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ConfigurationEvaluationSuite(AlgorithmConfiguration configToBeEvaluated, String csvFileFolder)
/*  70:    */   {
/*  71: 77 */     this.configToBeEvaluated = configToBeEvaluated;
/*  72: 78 */     this.csvFileFolder = csvFileFolder;
/*  73: 79 */     this.csvFilePathList = ParasiteFileUtils.findAllCsvFilesInFolder(this.csvFileFolder);
/*  74: 80 */     this.timeScale = Integer.parseInt(ParasiteManager.getInstance().properties
/*  75: 81 */       .getProperty("EVALUATION_SIMULATOR_TIME_SCALE"));
/*  76: 83 */     if ((this.csvFilePathList != null) && (!this.csvFilePathList.isEmpty()))
/*  77:    */     {
/*  78: 84 */       Iterator<String> i = this.csvFilePathList.iterator();
/*  79: 85 */       while (i.hasNext())
/*  80:    */       {
/*  81: 86 */         String csvFilePath = (String)i.next();
/*  82: 87 */         this.evaluationList.add(new ConfigurationEvaluation(configToBeEvaluated, csvFilePath));
/*  83:    */       }
/*  84: 90 */       Iterator<ConfigurationEvaluation> k = this.evaluationList.iterator();
/*  85: 91 */       String firstTicker = ((ConfigurationEvaluation)this.evaluationList.get(0)).ticker;
/*  86: 92 */       while (k.hasNext())
/*  87:    */       {
/*  88: 93 */         ConfigurationEvaluation eval = (ConfigurationEvaluation)k.next();
/*  89: 94 */         if (!eval.ticker.equals(firstTicker))
/*  90:    */         {
/*  91: 95 */           this.isSingleStockSuite = false;
/*  92: 96 */           break;
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void execute()
/*  99:    */   {
/* 100:104 */     MainLogger.info("\n**************************************************************************************************");
/* 101:105 */     MainLogger.info("Evaluation suite started!");
/* 102:106 */     Iterator<ConfigurationEvaluation> i = this.evaluationList.iterator();
/* 103:107 */     Thread.currentThread().setPriority(5);
/* 104:108 */     while (i.hasNext())
/* 105:    */     {
/* 106:109 */       ConfigurationEvaluation ce = (ConfigurationEvaluation)i.next();
/* 107:110 */       ce.startEvaluation();
/* 108:    */     }
/* 109:114 */     calculateTotalScore();
/* 110:115 */     createEvaluationSuiteReport();
/* 111:    */     
/* 112:117 */     MainLogger.info("Evaluation suite finished!");
/* 113:    */     
/* 114:119 */     MainLogger.info("\n************************************************************************************************\n\n");
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void createEvaluationSuiteReport()
/* 118:    */   {
/* 119:126 */     Format formatter = new SimpleDateFormat("yyyy-M-dd");
/* 120:    */     try
/* 121:    */     {
/* 122:129 */       File outFolder = new File(ParasiteManager.getInstance().outDir + "/" + 
/* 123:130 */         ParasiteManager.getInstance().evaluationReportsFolder);
/* 124:131 */       if (!outFolder.exists())
/* 125:    */       {
/* 126:132 */         boolean success = outFolder.mkdir();
/* 127:133 */         if (!success) {
/* 128:134 */           MainLogger.error("ERROR while creating directory: " + outFolder);
/* 129:    */         }
/* 130:    */       }
/* 131:138 */       File outFile = new File(outFolder + "/" + formatter.format(this.executionDate) + ".xls");
/* 132:    */       
/* 133:140 */       WritableWorkbook workbook = null;
/* 134:141 */       WritableSheet sheet = null;
/* 135:142 */       workbook = Workbook.createWorkbook(outFile);
/* 136:143 */       if (outFile.exists())
/* 137:    */       {
/* 138:144 */         MainLogger.info("Ode san");
/* 139:145 */         sheet = workbook.getSheet("Evaluation Suite Report");
/* 140:    */       }
/* 141:    */       else
/* 142:    */       {
/* 143:147 */         MainLogger.info("Ode");
/* 144:148 */         sheet = workbook.createSheet("Evaluation Suite Report", 0);
/* 145:    */       }
/* 146:151 */       calculateTotalScore();
/* 147:    */       
/* 148:153 */       WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
/* 149:154 */       WritableCellFormat cellFormat = new WritableCellFormat(font);
/* 150:155 */       cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 151:156 */       cellFormat.setAlignment(Alignment.LEFT);
/* 152:    */       
/* 153:    */ 
/* 154:159 */       Label testSuiteLabel = new Label(4, 9, "Test suite", cellFormat);
/* 155:160 */       sheet.addCell(testSuiteLabel);
/* 156:    */       
/* 157:162 */       Label testSuiteLabel2 = new Label(12, 9, "Test suite", cellFormat);
/* 158:163 */       sheet.addCell(testSuiteLabel2);
/* 159:    */       
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:169 */       workbook.write();
/* 165:170 */       workbook.close();
/* 166:171 */       System.out.println("Successffully written to file!!!");
/* 167:    */     }
/* 168:    */     catch (IOException e1)
/* 169:    */     {
/* 170:175 */       e1.printStackTrace();
/* 171:    */     }
/* 172:    */     catch (RowsExceededException e2)
/* 173:    */     {
/* 174:178 */       e2.printStackTrace();
/* 175:    */     }
/* 176:    */     catch (WriteException e3)
/* 177:    */     {
/* 178:181 */       e3.printStackTrace();
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void calculateTotalScore()
/* 183:    */   {
/* 184:187 */     Iterator<ConfigurationEvaluation> i = this.evaluationList.iterator();
/* 185:188 */     float totalReferentValue = 0.0F;
/* 186:189 */     float totalEarnedValue = 0.0F;
/* 187:190 */     boolean allSoldAllStocks = true;
/* 188:192 */     while (i.hasNext())
/* 189:    */     {
/* 190:193 */       ConfigurationEvaluation ce = (ConfigurationEvaluation)i.next();
/* 191:194 */       totalReferentValue += ce.score.refernceScore;
/* 192:195 */       totalEarnedValue += ce.score.absoluteScore;
/* 193:196 */       if (!ce.score.soldAllStocks) {
/* 194:197 */         allSoldAllStocks = false;
/* 195:    */       }
/* 196:    */     }
/* 197:201 */     this.totalScore = new AlgorithmScore(totalReferentValue, totalEarnedValue, allSoldAllStocks);
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.configurations.ConfigurationEvaluationSuite
 * JD-Core Version:    0.7.0.1
 */