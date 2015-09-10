/*   1:    */ package hr.nukic.parasite.simulator;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.core.StockMarketData;
/*   4:    */ import hr.nukic.parasite.core.Transaction;
/*   5:    */ import java.io.BufferedReader;
/*   6:    */ import java.io.DataInputStream;
/*   7:    */ import java.io.FileInputStream;
/*   8:    */ import java.io.InputStreamReader;
/*   9:    */ import java.text.ParseException;
/*  10:    */ import java.text.SimpleDateFormat;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import nukic.parasite.utils.MainLogger;
/*  15:    */ import nukic.parasite.utils.ParasiteUtils;
/*  16:    */ 
/*  17:    */ public class CsvFileMarketSimulator
/*  18:    */   extends MarketSimulator
/*  19:    */   implements Runnable
/*  20:    */ {
/*  21:    */   public String filePath;
/*  22: 21 */   public boolean isOldFileFormat = true;
/*  23:    */   public String[] lines;
/*  24:    */   
/*  25:    */   public CsvFileMarketSimulator(String filePath, int timeScale)
/*  26:    */   {
/*  27: 26 */     this.filePath = filePath;
/*  28: 27 */     this.timeScale = timeScale;
/*  29: 28 */     if (filePath.endsWith("_v2.csv")) {
/*  30: 29 */       this.isOldFileFormat = false;
/*  31:    */     } else {
/*  32: 31 */       this.isOldFileFormat = true;
/*  33:    */     }
/*  34: 34 */     parseTickerFromFilePath();
/*  35: 35 */     parseDateFromFilePath();
/*  36:    */     
/*  37: 37 */     loadData();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void run()
/*  41:    */   {
/*  42: 43 */     Thread.currentThread().setName("SIMULATOR_THREAD_" + this.ticker);
/*  43:    */     
/*  44:    */ 
/*  45: 46 */     loadData();
/*  46: 47 */     if (this.status != SimulatorStatus.DATA_READY) {
/*  47: 48 */       return;
/*  48:    */     }
/*  49: 51 */     this.status = SimulatorStatus.RUNNING;
/*  50: 52 */     this.ticker = this.lines[0].split(",")[0];
/*  51: 54 */     for (int i = 0; i < this.recordList.length; i++)
/*  52:    */     {
/*  53: 55 */       this.currentRecord = this.recordList[i];
/*  54: 57 */       if ((!isAfterMarketClose(this.currentRecord.time)) && (!isBeforeMarketOpen(this.currentRecord.time)))
/*  55:    */       {
/*  56: 59 */         MainLogger.debug("NEW DATA: " + this.currentRecord.toString());
/*  57: 62 */         if (i == this.recordList.length - 1)
/*  58:    */         {
/*  59: 63 */           this.nextRecord = null;
/*  60: 64 */           this.nextLinePeriod = 0L;
/*  61: 65 */           this.previousRecord = this.recordList[(i - 1)];
/*  62:    */           
/*  63: 67 */           MainLogger.debug("Last iteration...");
/*  64: 68 */           this.currentRecord.isLastRecord = true;
/*  65:    */         }
/*  66: 72 */         else if (i == 0)
/*  67:    */         {
/*  68: 73 */           this.nextRecord = this.recordList[(i + 1)];
/*  69: 74 */           this.nextLinePeriod = (this.nextRecord.time.getTime() - this.currentRecord.time.getTime());
/*  70: 75 */           this.previousRecord = null;
/*  71:    */         }
/*  72:    */         else
/*  73:    */         {
/*  74: 79 */           this.nextRecord = this.recordList[(i + 1)];
/*  75: 80 */           this.nextLinePeriod = (this.nextRecord.time.getTime() - this.currentRecord.time.getTime());
/*  76: 81 */           this.previousRecord = this.recordList[(i - 1)];
/*  77:    */         }
/*  78: 84 */         if ((this.minPriceRecord == null) || (
/*  79: 85 */           (!this.minPriceRecord.transactionList.isEmpty()) && 
/*  80: 86 */           (!this.currentRecord.transactionList.isEmpty()) && 
/*  81: 87 */           (((Transaction)this.minPriceRecord.transactionList.get(0)).price > ((Transaction)this.currentRecord.transactionList.get(0)).price))) {
/*  82: 88 */           this.minPriceRecord = this.currentRecord;
/*  83:    */         }
/*  84: 91 */         if ((this.maxPriceRecord == null) || (
/*  85: 92 */           (!this.maxPriceRecord.transactionList.isEmpty()) && 
/*  86: 93 */           (!this.currentRecord.transactionList.isEmpty()) && 
/*  87: 94 */           (((Transaction)this.maxPriceRecord.transactionList.get(0)).price < ((Transaction)this.currentRecord.transactionList.get(0)).price))) {
/*  88: 95 */           this.maxPriceRecord = this.currentRecord;
/*  89:    */         }
/*  90:100 */         notifyOrderSubscribers();
/*  91:    */         
/*  92:    */ 
/*  93:103 */         notifyPriceChangeSubscribers();
/*  94:    */         
/*  95:    */ 
/*  96:106 */         notifyMarketDataSubscribers();
/*  97:    */         try
/*  98:    */         {
/*  99:110 */           long s = this.nextLinePeriod / this.timeScale;
/* 100:111 */           Thread.sleep(s);
/* 101:    */         }
/* 102:    */         catch (InterruptedException e)
/* 103:    */         {
/* 104:113 */           MainLogger.error(e);
/* 105:    */         }
/* 106:117 */         this.currentRecordIndex = i;
/* 107:    */       }
/* 108:    */       else
/* 109:    */       {
/* 110:119 */         if (isAfterMarketClose(this.currentRecord.time))
/* 111:    */         {
/* 112:120 */           MainLogger.debug("Market is closed. Last iteration...");
/* 113:121 */           this.currentRecord.isLastRecord = true;
/* 114:    */           
/* 115:123 */           notifyMarketDataSubscribers();
/* 116:124 */           break;
/* 117:    */         }
/* 118:125 */         if (isBeforeMarketOpen(this.currentRecord.time)) {
/* 119:126 */           MainLogger.debug("Market is still not opened...");
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:131 */     MainLogger.debug("Simuation ends...");
/* 124:132 */     this.status = SimulatorStatus.FINISHED;
/* 125:    */   }
/* 126:    */   
/* 127:    */   private boolean isBeforeMarketOpen(Date time)
/* 128:    */   {
/* 129:137 */     if ((time.getHours() < 9) || ((time.getHours() == 9) && (time.getMinutes() < 50))) {
/* 130:138 */       return true;
/* 131:    */     }
/* 132:140 */     return false;
/* 133:    */   }
/* 134:    */   
/* 135:    */   private boolean isAfterMarketClose(Date time)
/* 136:    */   {
/* 137:144 */     if ((time.getHours() > 16) || ((time.getHours() == 16) && (time.getMinutes() > 10))) {
/* 138:145 */       return true;
/* 139:    */     }
/* 140:147 */     return false;
/* 141:    */   }
/* 142:    */   
/* 143:    */   private boolean readFile()
/* 144:    */   {
/* 145:    */     try
/* 146:    */     {
/* 147:152 */       List<String> lineList = new ArrayList(50);
/* 148:153 */       FileInputStream fstream = new FileInputStream(this.filePath);
/* 149:154 */       DataInputStream in = new DataInputStream(fstream);
/* 150:155 */       BufferedReader br = new BufferedReader(new InputStreamReader(in));
/* 151:    */       
/* 152:    */ 
/* 153:158 */       int lineNum = 0;
/* 154:    */       String strLine;
/* 155:161 */       while ((strLine = br.readLine()) != null)
/* 156:    */       {
/* 157:    */         String strLine;
/* 158:162 */         lineList.add(strLine);
/* 159:    */         
/* 160:164 */         lineNum++;
/* 161:    */       }
/* 162:167 */       this.lines = new String[lineList.size()];
/* 163:168 */       lineList.toArray(this.lines);
/* 164:    */       
/* 165:170 */       in.close();
/* 166:    */     }
/* 167:    */     catch (Exception e)
/* 168:    */     {
/* 169:172 */       MainLogger.error(e);
/* 170:173 */       return false;
/* 171:    */     }
/* 172:176 */     return true;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void loadData()
/* 176:    */   {
/* 177:181 */     boolean readOK = readFile();
/* 178:183 */     if (!readOK)
/* 179:    */     {
/* 180:184 */       MainLogger.error("ERROR: Csv file not read correctly!");
/* 181:185 */       this.status = SimulatorStatus.ERROR;
/* 182:186 */       return;
/* 183:    */     }
/* 184:189 */     if (this.lines.length == 0)
/* 185:    */     {
/* 186:190 */       MainLogger.error("ERROR: Csv file is empty!");
/* 187:191 */       this.status = SimulatorStatus.ERROR;
/* 188:192 */       return;
/* 189:    */     }
/* 190:195 */     if (this.isOldFileFormat) {
/* 191:196 */       this.recordList = StockMarketData.parseOldFormat(this.lines, this.marketDay);
/* 192:    */     } else {
/* 193:198 */       this.recordList = StockMarketData.parse(this.lines, this.marketDay);
/* 194:    */     }
/* 195:201 */     if (this.recordList == null)
/* 196:    */     {
/* 197:202 */       MainLogger.error("ERROR while parsing csv file lines!");
/* 198:203 */       this.status = SimulatorStatus.ERROR;
/* 199:    */     }
/* 200:206 */     this.startTime = this.recordList[0].time;
/* 201:207 */     this.endTime = this.recordList[(this.recordList.length - 1)].time;
/* 202:    */     
/* 203:209 */     this.lastRecord = this.recordList[(this.recordList.length - 1)];
/* 204:210 */     this.firstRecord = this.recordList[0];
/* 205:    */     
/* 206:212 */     this.status = SimulatorStatus.DATA_READY;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void transcodeToNewFormat()
/* 210:    */   {
/* 211:220 */     if (this.filePath.endsWith("_v2.csv"))
/* 212:    */     {
/* 213:221 */       MainLogger.error("ERROR: File is already in new format.");
/* 214:222 */       return;
/* 215:    */     }
/* 216:225 */     int end = this.filePath.indexOf(".csv");
/* 217:226 */     String renamedFilePath = this.filePath.substring(0, end) + "_v2.csv";
/* 218:    */     
/* 219:    */ 
/* 220:229 */     loadData();
/* 221:231 */     if (this.status == SimulatorStatus.DATA_READY) {
/* 222:232 */       for (int i = 0; i < this.recordList.length; i++) {
/* 223:233 */         this.recordList[i].appendToRenamedCsvFile(renamedFilePath);
/* 224:    */       }
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Date getFullDateAndTime()
/* 229:    */   {
/* 230:242 */     return ParasiteUtils.combineDateAndTimeIntoOneDate(this.currentRecord.time, this.marketDay);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void parseDateFromFilePath()
/* 234:    */   {
/* 235:248 */     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
/* 236:249 */     String dateString = "";
/* 237:    */     
/* 238:251 */     int begin = 0;
/* 239:252 */     int end = 0;
/* 240:254 */     if (this.isOldFileFormat)
/* 241:    */     {
/* 242:255 */       begin = this.filePath.lastIndexOf("_");
/* 243:256 */       end = this.filePath.indexOf(".csv");
/* 244:257 */       dateString = this.filePath.substring(begin + 1, end);
/* 245:    */     }
/* 246:    */     else
/* 247:    */     {
/* 248:260 */       end = this.filePath.lastIndexOf("_");
/* 249:261 */       dateString = this.filePath.substring(0, end);
/* 250:262 */       begin = dateString.lastIndexOf("_");
/* 251:263 */       dateString = dateString.substring(begin + 1);
/* 252:    */     }
/* 253:    */     try
/* 254:    */     {
/* 255:268 */       this.marketDay = formatter.parse(dateString);
/* 256:    */     }
/* 257:    */     catch (ParseException e)
/* 258:    */     {
/* 259:270 */       MainLogger.error(e);
/* 260:    */     }
/* 261:273 */     MainLogger.debug("Parsed market date:" + formatter.format(this.marketDay));
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void parseTickerFromFilePath()
/* 265:    */   {
/* 266:281 */     int begin = this.filePath.lastIndexOf("\\");
/* 267:282 */     if (begin == -1) {
/* 268:283 */       begin = this.filePath.lastIndexOf("/");
/* 269:    */     }
/* 270:285 */     int end = this.filePath.indexOf("_");
/* 271:    */     
/* 272:287 */     this.ticker = this.filePath.substring(begin + 1, end);
/* 273:288 */     MainLogger.debug("Parsed ticker = " + this.ticker);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public StockMarketData getMinPriceRecord()
/* 277:    */   {
/* 278:294 */     if (this.minPriceRecord == null) {
/* 279:295 */       searchForMinPriceRecord();
/* 280:    */     }
/* 281:297 */     return this.minPriceRecord;
/* 282:    */   }
/* 283:    */   
/* 284:    */   private void searchForMinPriceRecord()
/* 285:    */   {
/* 286:302 */     MainLogger.debug("Looking for min price transaction...");
/* 287:303 */     float minPrice = 3.4028235E+38F;
/* 288:304 */     int recordNum = this.recordList.length;
/* 289:305 */     for (int i = 0; i < recordNum; i++) {
/* 290:306 */       if (!this.recordList[i].transactionList.isEmpty())
/* 291:    */       {
/* 292:308 */         float price = ((Transaction)this.recordList[i].transactionList.get(0)).price;
/* 293:310 */         if (price < minPrice)
/* 294:    */         {
/* 295:311 */           MainLogger.debug("Changing min price. New value = " + price);
/* 296:312 */           minPrice = price;
/* 297:313 */           this.minPriceRecord = this.recordList[i];
/* 298:    */         }
/* 299:    */       }
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   public StockMarketData getMaxPriceRecord()
/* 304:    */   {
/* 305:321 */     if (this.maxPriceRecord == null) {
/* 306:322 */       searchForMaxPriceRecord();
/* 307:    */     }
/* 308:324 */     return this.maxPriceRecord;
/* 309:    */   }
/* 310:    */   
/* 311:    */   private void searchForMaxPriceRecord()
/* 312:    */   {
/* 313:328 */     MainLogger.debug("Looking for max price transaction...");
/* 314:329 */     float maxPrice = 0.0F;
/* 315:330 */     int recordNum = this.recordList.length;
/* 316:332 */     for (int i = 0; i < recordNum; i++) {
/* 317:333 */       if (!this.recordList[i].transactionList.isEmpty())
/* 318:    */       {
/* 319:335 */         float price = ((Transaction)this.recordList[i].transactionList.get(0)).price;
/* 320:336 */         MainLogger.debug("Price = " + price);
/* 321:337 */         if (price > maxPrice)
/* 322:    */         {
/* 323:338 */           MainLogger.debug("Changing max price. New value = " + price);
/* 324:339 */           maxPrice = price;
/* 325:340 */           this.maxPriceRecord = this.recordList[i];
/* 326:    */         }
/* 327:    */       }
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public StockMarketData getMinPriceRecordBeforeMaxPriceRecord()
/* 332:    */   {
/* 333:348 */     if ((!getMinPriceRecord().transactionList.isEmpty()) && (!getMaxPriceRecord().transactionList.isEmpty())) {
/* 334:350 */       if (((Transaction)getMinPriceRecord().transactionList.get(0)).time.getTime() > ((Transaction)getMaxPriceRecord().transactionList.get(0)).time.getTime())
/* 335:    */       {
/* 336:351 */         MainLogger.debug("Going to search...");
/* 337:352 */         searchForMinPriceRecordBeforeMaxPriceRecord();
/* 338:    */       }
/* 339:    */       else
/* 340:    */       {
/* 341:354 */         this.minPriceRecordBeforeMax = this.minPriceRecord;
/* 342:    */       }
/* 343:    */     }
/* 344:358 */     return this.minPriceRecordBeforeMax;
/* 345:    */   }
/* 346:    */   
/* 347:    */   private void searchForMinPriceRecordBeforeMaxPriceRecord()
/* 348:    */   {
/* 349:362 */     MainLogger.debug("Looking for min price transaction before max price transaction...");
/* 350:363 */     float minPrice = 3.4028235E+38F;
/* 351:364 */     StockMarketData minPriceRecordBeforeMaxPriceRecord = new StockMarketData(this.ticker);
/* 352:    */     
/* 353:366 */     int recordNum = this.recordList.length;
/* 354:368 */     for (int i = 0; i < recordNum; i++) {
/* 355:369 */       if (!this.recordList[i].transactionList.isEmpty())
/* 356:    */       {
/* 357:371 */         float price = ((Transaction)this.recordList[i].transactionList.get(0)).price;
/* 358:372 */         MainLogger.debug("Price = " + price);
/* 359:374 */         if ((price < minPrice) && (this.recordList[i].time.getTime() < this.maxPriceRecord.time.getTime()))
/* 360:    */         {
/* 361:375 */           MainLogger.debug("Changing min price. New value = " + price);
/* 362:376 */           minPrice = price;
/* 363:377 */           minPriceRecordBeforeMaxPriceRecord = this.recordList[i];
/* 364:    */         }
/* 365:379 */         else if (this.recordList[i].time.getTime() >= this.maxPriceRecord.time.getTime())
/* 366:    */         {
/* 367:381 */           MainLogger.info("Search for minimum price transaction finished! Reached max price transaction time...");
/* 368:382 */           MainLogger.debug("Min price before max = " + 
/* 369:383 */             ((Transaction)minPriceRecordBeforeMaxPriceRecord.transactionList.get(0)).price);
/* 370:384 */           MainLogger.debug("Max price = " + ((Transaction)this.maxPriceRecord.transactionList.get(0)).price);
/* 371:385 */           break;
/* 372:    */         }
/* 373:    */       }
/* 374:    */     }
/* 375:390 */     this.minPriceRecordBeforeMax = minPriceRecordBeforeMaxPriceRecord;
/* 376:    */   }
/* 377:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.simulator.CsvFileMarketSimulator
 * JD-Core Version:    0.7.0.1
 */