/*   1:    */ package hr.nukic.parasite.neuralnet.test;
/*   2:    */ 
/*   3:    */ import cern.colt.list.DoubleArrayList;
/*   4:    */ import cern.jet.stat.Descriptive;
/*   5:    */ import hr.nukic.parasite.core.StockMarketData;
/*   6:    */ import hr.nukic.parasite.core.StockMetrics;
/*   7:    */ import hr.nukic.parasite.core.Transaction;
/*   8:    */ import hr.nukic.parasite.simulator.CsvFileMarketSimulator;
/*   9:    */ import hr.nukic.parasite.statistics.Statistics;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import nukic.parasite.utils.MainLogger;
/*  15:    */ import nukic.parasite.utils.ParasiteFileUtils;
/*  16:    */ 
/*  17:    */ public class NNTest
/*  18:    */ {
/*  19:    */   public static void main(String[] args)
/*  20:    */   {
/*  21: 32 */     boolean test3 = testGettingPriceChangeArray2();
/*  22:    */   }
/*  23:    */   
/*  24:    */   private static void testReadBidAskValues()
/*  25:    */   {
/*  26: 47 */     String folder = "C:/ParasiteTrade/out";
/*  27: 48 */     String ticker = "ADRS-P-A";
/*  28:    */     
/*  29: 50 */     List<CsvFileMarketSimulator> sims = StockMetrics.loadAllCsvFilesForTicker(ticker, folder);
/*  30:    */     CsvFileMarketSimulator sim;
/*  31:    */     int i;
/*  32: 51 */     for (Iterator localIterator = sims.iterator(); localIterator.hasNext(); i < sim.recordList.length)
/*  33:    */     {
/*  34: 51 */       sim = (CsvFileMarketSimulator)localIterator.next();
/*  35: 52 */       i = 0; continue;
/*  36: 53 */       sim.recordList[i].calculateImplicitData();i++;
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   private static void testGetFileNameFromPath()
/*  41:    */   {
/*  42: 60 */     String path = "D:\\Documents\\devel//java/ParasiteTrade\\out\\2011-03-09//DLKV-R-A_MarketData_2011-03-09.csv";
/*  43: 61 */     String fileName = ParasiteFileUtils.getFileNameFromPath(ParasiteFileUtils.correctPath(path));
/*  44: 62 */     MainLogger.debug("Filename = " + fileName);
/*  45:    */   }
/*  46:    */   
/*  47:    */   private static void testFindAllCsvFilesByTicker()
/*  48:    */   {
/*  49: 67 */     String folder = "C:/ParasiteTrade/out";
/*  50: 68 */     List<String> files = ParasiteFileUtils.findAllCsvFilesByTicker(folder, "ADRS-P-A");
/*  51: 69 */     for (String file : files) {
/*  52: 70 */       System.out.println(file);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static void testCorrectPath()
/*  57:    */   {
/*  58: 76 */     String path = "D:\\Documents\\devel\\java/ParasiteTrade\\out\\2011-03-09/DLKV-R-A_MarketData_2011-03-09.csv";
/*  59: 77 */     MainLogger.debug("Old path: " + path);
/*  60: 78 */     MainLogger.debug("New path: " + ParasiteFileUtils.correctPath(path));
/*  61:    */   }
/*  62:    */   
/*  63:    */   private static void testFindAllCsvFilesInFolder()
/*  64:    */   {
/*  65: 83 */     String s = "C:/ParasiteTrade/out";
/*  66: 84 */     List<String> files = ParasiteFileUtils.findAllCsvFilesInFolder(s);
/*  67: 86 */     for (String file : files) {
/*  68: 87 */       System.out.println(file);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   private static boolean testGettingPriceChangeArray1()
/*  73:    */   {
/*  74: 95 */     String p3 = "D:\\Documents\\devel\\java\\ParasiteTrade\\out\\2011-03-09\\ZABA-R-A_MarketData_2011-03-09.csv";
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:106 */     List<String> paths = new ArrayList();
/*  86:    */     
/*  87:108 */     paths.add(p3);
/*  88:    */     
/*  89:110 */     CsvFileMarketSimulator csvSim = new CsvFileMarketSimulator(p3, 25);
/*  90:111 */     ArrayList<Float> rPriceChangeList = new ArrayList();
/*  91:112 */     ArrayList<Float> aPriceChangeList = new ArrayList();
/*  92:    */     StockMarketData record;
/*  93:114 */     for (int i = 0; i < csvSim.recordList.length; i++)
/*  94:    */     {
/*  95:115 */       record = csvSim.recordList[i];
/*  96:116 */       int size = record.transactionList.size();
/*  97:117 */       System.out.println("Number of transactions in record " + i + " :" + size);
/*  98:119 */       if (size >= 2)
/*  99:    */       {
/* 100:120 */         float absolute = ((Transaction)record.transactionList.get(0)).price - ((Transaction)record.transactionList.get(1)).price;
/* 101:121 */         aPriceChangeList.add(Float.valueOf(absolute));
/* 102:122 */         float relative = absolute / ((Transaction)record.transactionList.get(1)).price * 100.0F;
/* 103:123 */         rPriceChangeList.add(Float.valueOf(relative));
/* 104:    */       }
/* 105:    */     }
/* 106:128 */     System.out.println("\nRelative price changes (Total number: " + rPriceChangeList.size() + ")   ");
/* 107:129 */     for (Float priceChange : rPriceChangeList) {
/* 108:130 */       System.out.print(priceChange.floatValue() + ", ");
/* 109:    */     }
/* 110:133 */     System.out.println("\nAbsolute price changes (Total number: " + aPriceChangeList.size() + ")   ");
/* 111:134 */     for (Float priceChange : aPriceChangeList) {
/* 112:135 */       System.out.print(priceChange.floatValue() + ", ");
/* 113:    */     }
/* 114:138 */     return true;
/* 115:    */   }
/* 116:    */   
/* 117:    */   private static boolean testGettingPriceChangeArray2()
/* 118:    */   {
/* 119:145 */     String p3 = "D:\\Documents\\devel\\java\\ParasiteTrade\\out\\2011-03-09\\ZABA-R-A_MarketData_2011-03-09.csv";
/* 120:    */     
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:156 */     List<String> paths = new ArrayList();
/* 131:    */     
/* 132:158 */     paths.add(p3);
/* 133:    */     
/* 134:160 */     CsvFileMarketSimulator csvSim = new CsvFileMarketSimulator(p3, 25);
/* 135:161 */     ArrayList<Float> rPriceChangeList = new ArrayList();
/* 136:162 */     ArrayList<Float> aPriceChangeList = new ArrayList();
/* 137:    */     StockMarketData current;
/* 138:164 */     for (int i = 0; i < csvSim.recordList.length; i++)
/* 139:    */     {
/* 140:165 */       current = csvSim.recordList[i];
/* 141:166 */       StockMarketData previous = null;
/* 142:    */       
/* 143:168 */       int currentSize = current.transactionList.size();
/* 144:169 */       System.out.println("Number of transactions in current record " + (i + 1) + " :" + currentSize);
/* 145:171 */       if (i > 1)
/* 146:    */       {
/* 147:172 */         previous = csvSim.recordList[(i - 1)];
/* 148:173 */         int prevSize = previous.transactionList.size();
/* 149:174 */         System.out.println("Number of transactions in previous record " + i + " :" + currentSize);
/* 150:177 */         if (prevSize < currentSize)
/* 151:    */         {
/* 152:178 */           float absolute = ((Transaction)current.transactionList.get(0)).price - ((Transaction)previous.transactionList.get(0)).price;
/* 153:179 */           aPriceChangeList.add(Float.valueOf(absolute));
/* 154:180 */           float relative = absolute / ((Transaction)previous.transactionList.get(0)).price * 100.0F;
/* 155:181 */           rPriceChangeList.add(Float.valueOf(relative));
/* 156:    */         }
/* 157:    */         else
/* 158:    */         {
/* 159:183 */           aPriceChangeList.add(Float.valueOf(0.0F));
/* 160:184 */           rPriceChangeList.add(Float.valueOf(0.0F));
/* 161:    */         }
/* 162:    */       }
/* 163:    */     }
/* 164:189 */     System.out.println("\nRelative price changes (Total number: " + rPriceChangeList.size() + ")   ");
/* 165:190 */     for (Float priceChange : rPriceChangeList) {
/* 166:191 */       System.out.print(priceChange.floatValue() + ", ");
/* 167:    */     }
/* 168:194 */     System.out.println("\nAbsolute price changes (Total number: " + aPriceChangeList.size() + ")   ");
/* 169:195 */     for (Float priceChange : aPriceChangeList) {
/* 170:196 */       System.out.print(priceChange.floatValue() + ", ");
/* 171:    */     }
/* 172:199 */     return true;
/* 173:    */   }
/* 174:    */   
/* 175:    */   private static boolean testCorreleationCalculations()
/* 176:    */   {
/* 177:203 */     double[] sample1 = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D, 17.0D, 18.0D, 19.0D, 20.0D };
/* 178:    */     
/* 179:205 */     double[] sample2 = { 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D };
/* 180:    */     
/* 181:207 */     DoubleArrayList s1 = new DoubleArrayList(sample1);
/* 182:208 */     double sumOfSquares1 = Descriptive.sumOfSquares(s1);
/* 183:209 */     double sum1 = Descriptive.sum(s1);
/* 184:210 */     double variance1 = Descriptive.variance(s1.size(), sum1, sumOfSquares1);
/* 185:211 */     double standardDeviation1 = Descriptive.standardDeviation(variance1);
/* 186:    */     
/* 187:213 */     DoubleArrayList s2 = new DoubleArrayList(sample2);
/* 188:214 */     double sumOfSquares2 = Descriptive.sumOfSquares(s2);
/* 189:215 */     double sum2 = Descriptive.sum(s2);
/* 190:216 */     double variance2 = Descriptive.variance(s2.size(), sum2, sumOfSquares2);
/* 191:217 */     double standardDeviation2 = Descriptive.standardDeviation(variance2);
/* 192:    */     
/* 193:219 */     Double corr = Double.valueOf(Descriptive.correlation(s1, standardDeviation1, s2, standardDeviation2));
/* 194:220 */     System.out.println(corr);
/* 195:    */     
/* 196:222 */     float[] sample3 = { 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F, 17.0F, 18.0F, 19.0F, 20.0F };
/* 197:    */     
/* 198:224 */     float[] sample4 = { 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F };
/* 199:    */     
/* 200:226 */     Float corr2 = Float.valueOf(Statistics.correlation(sample3, sample4));
/* 201:227 */     System.out.println(corr2);
/* 202:229 */     if (corr.floatValue() == corr2.floatValue())
/* 203:    */     {
/* 204:230 */       System.out.println("testGettingPriceChangeArray1 PASSED!");
/* 205:231 */       return true;
/* 206:    */     }
/* 207:233 */     System.out.println("testGettingPriceChangeArray1 FAILED!");
/* 208:234 */     return false;
/* 209:    */   }
/* 210:    */   
/* 211:    */   private static boolean testCorreleationCalculations2()
/* 212:    */   {
/* 213:239 */     float[] sample1 = { 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F };
/* 214:    */     
/* 215:241 */     float[] sample2 = { 9.0F, 8.9F, 8.8F, 8.7F, 8.6F, 8.5F, 8.4F };
/* 216:    */     
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:256 */     Float corr2 = Float.valueOf(Statistics.correlation(sample1, sample2));
/* 231:257 */     System.out.println(corr2);
/* 232:    */     
/* 233:259 */     return true;
/* 234:    */   }
/* 235:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.neuralnet.test.NNTest
 * JD-Core Version:    0.7.0.1
 */