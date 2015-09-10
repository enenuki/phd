/*   1:    */ package hr.nukic.parasite.core;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileWriter;
/*   6:    */ import java.text.Format;
/*   7:    */ import java.text.ParseException;
/*   8:    */ import java.text.SimpleDateFormat;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Observable;
/*  13:    */ import nukic.parasite.utils.MainLogger;
/*  14:    */ import nukic.parasite.utils.ParasiteUtils;
/*  15:    */ 
/*  16:    */ public class StockMarketData
/*  17:    */   extends Observable
/*  18:    */ {
/*  19:    */   public static final int DEFAULT_FILE_WRITE_PERIOD_MINUTES = 10;
/*  20:    */   public String ticker;
/*  21: 24 */   public Date time = new Date(0L);
/*  22: 25 */   public Date lastWriteToFile = new Date(0L);
/*  23: 27 */   public ArrayList<Order> askList = new ArrayList(5);
/*  24: 28 */   public ArrayList<Order> bidList = new ArrayList(5);
/*  25: 29 */   public ArrayList<Transaction> transactionList = new ArrayList(5);
/*  26: 30 */   public ImplicitStockMarketData implicitData = null;
/*  27: 31 */   public boolean isLastRecord = false;
/*  28: 33 */   public String dataHistory = "";
/*  29:    */   public DataCollector collector;
/*  30:    */   
/*  31:    */   public void clearLists()
/*  32:    */   {
/*  33: 39 */     this.askList.clear();
/*  34: 40 */     this.bidList.clear();
/*  35: 41 */     this.transactionList.clear();
/*  36: 42 */     System.gc();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public StockMarketData(DataCollector dc, String ticker)
/*  40:    */   {
/*  41: 46 */     this.ticker = ticker;
/*  42: 47 */     this.collector = dc;
/*  43: 48 */     this.implicitData = new ImplicitStockMarketData(this);
/*  44: 49 */     this.isLastRecord = false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public StockMarketData(String ticker)
/*  48:    */   {
/*  49: 53 */     this.ticker = ticker;
/*  50: 54 */     this.implicitData = new ImplicitStockMarketData(this);
/*  51: 55 */     this.isLastRecord = false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void appendToCsvFileEvery(int minutes)
/*  55:    */   {
/*  56: 60 */     Format formatter4 = new SimpleDateFormat("yyyy-MM-dd");
/*  57: 61 */     String onlyDate2 = formatter4.format(this.time);
/*  58: 62 */     String str = toString();
/*  59: 63 */     this.dataHistory += str;
/*  60:    */     
/*  61:    */ 
/*  62: 66 */     Date now = new Date();
/*  63: 67 */     long millisecondsPassed = now.getTime() - this.lastWriteToFile.getTime();
/*  64: 68 */     if (millisecondsPassed > minutes * 60 * 1000)
/*  65:    */     {
/*  66: 69 */       MainLogger.debug("Minutes since last writing to file: " + millisecondsPassed / 60000L);
/*  67:    */       try
/*  68:    */       {
/*  69: 72 */         String outDirPath = ParasiteUtils.outputPath + onlyDate2 + "\\";
/*  70: 73 */         File outFolder = new File(outDirPath);
/*  71: 74 */         if (!outFolder.exists())
/*  72:    */         {
/*  73: 75 */           boolean success = outFolder.mkdir();
/*  74: 76 */           if (!success) {
/*  75: 77 */             MainLogger.error("ERROR while creating directory: " + outDirPath);
/*  76:    */           }
/*  77:    */         }
/*  78: 80 */         FileWriter fstream = new FileWriter(outDirPath + this.ticker + "_MarketData_" + onlyDate2 + "_v2.csv", true);
/*  79: 81 */         BufferedWriter out = new BufferedWriter(fstream);
/*  80: 82 */         MainLogger.info("Appending HISTORY to CSV file for stock: " + this.ticker);
/*  81: 83 */         out.write(this.dataHistory);
/*  82: 84 */         out.close();
/*  83: 85 */         this.lastWriteToFile = new Date();
/*  84: 86 */         this.dataHistory = "";
/*  85:    */       }
/*  86:    */       catch (Exception e)
/*  87:    */       {
/*  88: 88 */         MainLogger.error(e);
/*  89:    */       }
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93: 92 */       MainLogger.debug("Appending to HISTORY: " + str);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void appendWithImplicitDataToCsvFile()
/*  98:    */   {
/*  99: 99 */     Format formatter4 = new SimpleDateFormat("yyyy-MM-dd");
/* 100:100 */     String onlyDate2 = formatter4.format(this.time);
/* 101:    */     
/* 102:102 */     String str = toString();
/* 103:    */     
/* 104:    */ 
/* 105:105 */     str = str + this.implicitData.toCsvFileString();
/* 106:    */     try
/* 107:    */     {
/* 108:108 */       String outDirPath = ParasiteUtils.outputPath + onlyDate2 + "\\";
/* 109:109 */       File outFolder = new File(outDirPath);
/* 110:110 */       if (!outFolder.exists())
/* 111:    */       {
/* 112:111 */         boolean success = outFolder.mkdir();
/* 113:112 */         if (!success) {
/* 114:113 */           MainLogger.error("ERROR while creating directory: " + outDirPath);
/* 115:    */         }
/* 116:    */       }
/* 117:116 */       FileWriter fstream = new FileWriter(outDirPath + this.ticker + "_MarketData_" + onlyDate2 + "_v2.csv", true);
/* 118:117 */       BufferedWriter out = new BufferedWriter(fstream);
/* 119:118 */       MainLogger.info("Appending to CSV file: " + str);
/* 120:119 */       out.write(str);
/* 121:120 */       out.close();
/* 122:121 */       this.lastWriteToFile = new Date();
/* 123:122 */       this.dataHistory = "";
/* 124:    */     }
/* 125:    */     catch (Exception e)
/* 126:    */     {
/* 127:124 */       MainLogger.error(e);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void appendWithImplicitDataToCsvFileEvery(int minutes)
/* 132:    */   {
/* 133:131 */     Format formatter4 = new SimpleDateFormat("yyyy-MM-dd");
/* 134:132 */     String onlyDate2 = formatter4.format(this.time);
/* 135:    */     
/* 136:134 */     String str = toString();
/* 137:    */     
/* 138:    */ 
/* 139:137 */     str = str + this.implicitData.toCsvFileString();
/* 140:    */     
/* 141:139 */     this.dataHistory += str;
/* 142:    */     
/* 143:    */ 
/* 144:142 */     Date now = new Date();
/* 145:143 */     long millisecondsPassed = now.getTime() - this.lastWriteToFile.getTime();
/* 146:144 */     if (millisecondsPassed > minutes * 60 * 1000)
/* 147:    */     {
/* 148:145 */       MainLogger.debug("Minutes since last writing to file: " + millisecondsPassed / 60000L);
/* 149:    */       try
/* 150:    */       {
/* 151:148 */         String outDirPath = ParasiteUtils.outputPath + onlyDate2 + "\\";
/* 152:149 */         File outFolder = new File(outDirPath);
/* 153:150 */         if (!outFolder.exists())
/* 154:    */         {
/* 155:151 */           boolean success = outFolder.mkdir();
/* 156:152 */           if (!success) {
/* 157:153 */             MainLogger.error("ERROR while creating directory: " + outDirPath);
/* 158:    */           }
/* 159:    */         }
/* 160:156 */         FileWriter fstream = new FileWriter(outDirPath + this.ticker + "_MarketData_" + onlyDate2 + "_v2.csv", true);
/* 161:157 */         BufferedWriter out = new BufferedWriter(fstream);
/* 162:158 */         MainLogger.info("Appending to CSV file for stock: " + this.ticker);
/* 163:159 */         out.write(this.dataHistory);
/* 164:160 */         out.close();
/* 165:161 */         this.lastWriteToFile = new Date();
/* 166:162 */         this.dataHistory = "";
/* 167:    */       }
/* 168:    */       catch (Exception e)
/* 169:    */       {
/* 170:164 */         MainLogger.error(e);
/* 171:    */       }
/* 172:    */     }
/* 173:    */     else
/* 174:    */     {
/* 175:168 */       MainLogger.debug("Appending to string buffer: " + str);
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void appendToCsvFile()
/* 180:    */   {
/* 181:175 */     Format formatter4 = new SimpleDateFormat("yyyy-MM-dd");
/* 182:176 */     String onlyDate2 = formatter4.format(this.time);
/* 183:177 */     String str = toString();
/* 184:    */     try
/* 185:    */     {
/* 186:180 */       String outDirPath = ParasiteUtils.outputPath + onlyDate2 + "\\";
/* 187:181 */       File outFolder = new File(outDirPath);
/* 188:182 */       if (!outFolder.exists())
/* 189:    */       {
/* 190:183 */         boolean success = outFolder.mkdir();
/* 191:184 */         if (!success) {
/* 192:185 */           MainLogger.error("ERROR while creating directory: " + outDirPath);
/* 193:    */         }
/* 194:    */       }
/* 195:188 */       FileWriter fstream = new FileWriter(outDirPath + this.ticker + "_MarketData_" + onlyDate2 + "_v2.csv", true);
/* 196:    */       
/* 197:190 */       BufferedWriter out = new BufferedWriter(fstream);
/* 198:191 */       MainLogger.info("Appending data to CSV file for stock: " + this.ticker);
/* 199:192 */       out.write(str);
/* 200:193 */       out.close();
/* 201:194 */       this.lastWriteToFile = new Date();
/* 202:    */     }
/* 203:    */     catch (Exception e)
/* 204:    */     {
/* 205:196 */       MainLogger.error(e);
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void appendToCsvFile(String newOutDir)
/* 210:    */   {
/* 211:203 */     Format formatter4 = new SimpleDateFormat("yyyy-MM-dd");
/* 212:204 */     String onlyDate2 = formatter4.format(this.time);
/* 213:205 */     String str = toString();
/* 214:    */     try
/* 215:    */     {
/* 216:209 */       File outFolder = new File(newOutDir);
/* 217:210 */       if (!outFolder.exists())
/* 218:    */       {
/* 219:211 */         boolean success = outFolder.mkdir();
/* 220:212 */         if (!success) {
/* 221:213 */           MainLogger.error("ERROR while creating directory: " + newOutDir);
/* 222:    */         }
/* 223:    */       }
/* 224:216 */       String filePath = newOutDir + "\\" + this.ticker + "_MarketData_" + onlyDate2 + "_v2.csv";
/* 225:217 */       FileWriter fstream = new FileWriter(filePath, true);
/* 226:    */       
/* 227:219 */       BufferedWriter out = new BufferedWriter(fstream);
/* 228:220 */       MainLogger.info("Appending data to CSV file for stock: " + this.ticker + "(Filepath: " + filePath);
/* 229:221 */       out.write(str);
/* 230:222 */       out.close();
/* 231:223 */       this.lastWriteToFile = new Date();
/* 232:    */     }
/* 233:    */     catch (Exception e)
/* 234:    */     {
/* 235:225 */       MainLogger.error(e);
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void appendToRenamedCsvFile(String newFilePath)
/* 240:    */   {
/* 241:231 */     String str = toString();
/* 242:    */     try
/* 243:    */     {
/* 244:235 */       FileWriter fstream = new FileWriter(newFilePath, true);
/* 245:    */       
/* 246:237 */       BufferedWriter out = new BufferedWriter(fstream);
/* 247:    */       
/* 248:239 */       out.write(str);
/* 249:240 */       out.close();
/* 250:241 */       this.lastWriteToFile = new Date();
/* 251:    */     }
/* 252:    */     catch (Exception e)
/* 253:    */     {
/* 254:243 */       MainLogger.error(e);
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void readFromCsvFile() {}
/* 259:    */   
/* 260:    */   public float calculateTurnover()
/* 261:    */   {
/* 262:253 */     float turnover = 0.0F;
/* 263:254 */     Iterator<Transaction> i = this.transactionList.iterator();
/* 264:255 */     while (i.hasNext())
/* 265:    */     {
/* 266:256 */       Transaction t = (Transaction)i.next();
/* 267:257 */       turnover += t.value;
/* 268:    */     }
/* 269:259 */     return turnover;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void calculateImplicitData()
/* 273:    */   {
/* 274:263 */     this.implicitData.calculateBidAskRelatedData();
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String toString()
/* 278:    */   {
/* 279:269 */     Format formatter2 = new SimpleDateFormat("HH:mm:ss");
/* 280:270 */     String onlyTime = formatter2.format(this.time);
/* 281:271 */     String str = this.ticker + "," + onlyTime + ",B,";
/* 282:    */     
/* 283:273 */     Iterator<Order> j1 = this.bidList.iterator();
/* 284:274 */     while (j1.hasNext())
/* 285:    */     {
/* 286:275 */       Order bInfo = (Order)j1.next();
/* 287:276 */       str = str + bInfo.toCsvString();
/* 288:    */     }
/* 289:278 */     if (this.bidList.isEmpty()) {
/* 290:279 */       str = str + "NO_BIDS,";
/* 291:    */     }
/* 292:282 */     str = str + "A,";
/* 293:283 */     Iterator<Order> j2 = this.askList.iterator();
/* 294:284 */     while (j2.hasNext())
/* 295:    */     {
/* 296:285 */       Order aInfo = (Order)j2.next();
/* 297:286 */       str = str + aInfo.toCsvString();
/* 298:    */     }
/* 299:288 */     if (this.askList.isEmpty()) {
/* 300:289 */       str = str + "NO_ASKS,";
/* 301:    */     }
/* 302:302 */     str = str + "ALL_T,";
/* 303:303 */     Iterator<Transaction> k2 = this.transactionList.iterator();
/* 304:304 */     while (k2.hasNext())
/* 305:    */     {
/* 306:305 */       Transaction tInfo = (Transaction)k2.next();
/* 307:306 */       str = str + tInfo.toCsvString();
/* 308:    */     }
/* 309:308 */     if (this.transactionList.isEmpty())
/* 310:    */     {
/* 311:309 */       str = str + "NO_TRANSACTIONS";
/* 312:310 */       str = str + ",";
/* 313:    */     }
/* 314:313 */     return str;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public String getTimeString()
/* 318:    */   {
/* 319:317 */     Format formatter = new SimpleDateFormat("HH:mm:ss");
/* 320:318 */     return formatter.format(this.time);
/* 321:    */   }
/* 322:    */   
/* 323:    */   public String getDateString()
/* 324:    */   {
/* 325:322 */     Format formatter = new SimpleDateFormat("yyyy/MM/dd");
/* 326:323 */     return formatter.format(this.time);
/* 327:    */   }
/* 328:    */   
/* 329:    */   public String toStringOld()
/* 330:    */   {
/* 331:327 */     Format formatter2 = new SimpleDateFormat("HH:mm:ss");
/* 332:328 */     String onlyTime = formatter2.format(this.time);
/* 333:329 */     String str = this.ticker + "," + onlyTime + ",B,";
/* 334:    */     
/* 335:331 */     Iterator<Order> j1 = this.bidList.iterator();
/* 336:332 */     while (j1.hasNext())
/* 337:    */     {
/* 338:333 */       Order bInfo = (Order)j1.next();
/* 339:334 */       str = str + bInfo.toCsvString();
/* 340:    */     }
/* 341:336 */     if (this.bidList.isEmpty()) {
/* 342:337 */       str = str + "NO_BIDS,";
/* 343:    */     }
/* 344:340 */     str = str + "A,";
/* 345:341 */     Iterator<Order> j2 = this.askList.iterator();
/* 346:342 */     while (j2.hasNext())
/* 347:    */     {
/* 348:343 */       Order aInfo = (Order)j2.next();
/* 349:344 */       str = str + aInfo.toCsvString();
/* 350:    */     }
/* 351:346 */     if (this.askList.isEmpty()) {
/* 352:347 */       str = str + "NO_ASKS,";
/* 353:    */     }
/* 354:351 */     str = str + "T,";
/* 355:352 */     if (!this.transactionList.isEmpty())
/* 356:    */     {
/* 357:353 */       str = str + ((Transaction)this.transactionList.get(0)).amount + "," + ((Transaction)this.transactionList.get(0)).price + ",";
/* 358:    */       
/* 359:355 */       str = str + formatter2.format(((Transaction)this.transactionList.get(0)).time) + "\n";
/* 360:    */     }
/* 361:    */     else
/* 362:    */     {
/* 363:357 */       str = str + "NO_TRANSACTIONS\n";
/* 364:    */     }
/* 365:360 */     return str;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public static StockMarketData[] parseOldFormat(String[] lines, Date marketDay)
/* 369:    */   {
/* 370:365 */     StockMarketData[] records = new StockMarketData[lines.length];
/* 371:366 */     SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
/* 372:367 */     String ticker = "";
/* 373:368 */     String timeString = "";
/* 374:370 */     for (int n = 0; n < lines.length; n++)
/* 375:    */     {
/* 376:373 */       String[] tokens = lines[n].split(",");
/* 377:374 */       int bIndex = ParasiteUtils.getFirstIndexOfToken("B", tokens);
/* 378:375 */       int aIndex = ParasiteUtils.getFirstIndexOfToken("A", tokens);
/* 379:376 */       int tIndex = ParasiteUtils.getFirstIndexOfToken("T", tokens);
/* 380:377 */       int fIndex = ParasiteUtils.getFirstNanTokenIndexAfter(tIndex, tokens);
/* 381:379 */       if (bIndex != 2) {
/* 382:380 */         return null;
/* 383:    */       }
/* 384:382 */       ticker = tokens[0];
/* 385:    */       
/* 386:384 */       StockMarketData marketData = new StockMarketData(ticker);
/* 387:    */       try
/* 388:    */       {
/* 389:389 */         Date time = formatter.parse(tokens[1]);
/* 390:390 */         marketData.time = ParasiteUtils.combineDateAndTimeIntoOneDate(time, marketDay);
/* 391:    */       }
/* 392:    */       catch (ParseException e)
/* 393:    */       {
/* 394:398 */         MainLogger.error(lines[n]);
/* 395:399 */         MainLogger.error(e);
/* 396:    */       }
/* 397:403 */       if (!tokens[(bIndex + 1)].equals("NO_BIDS")) {
/* 398:404 */         for (int k = bIndex + 1; k < aIndex; k += 3)
/* 399:    */         {
/* 400:405 */           int pos = Integer.parseInt(tokens[k]);
/* 401:    */           
/* 402:    */ 
/* 403:408 */           int amount = Integer.parseInt(tokens[(k + 1)]);
/* 404:    */           
/* 405:410 */           Float price = Float.valueOf(Float.parseFloat(tokens[(k + 2)]));
/* 406:411 */           Order order = new Order(ticker, OrderType.BUY, amount, price.floatValue());
/* 407:412 */           order.position = pos;
/* 408:413 */           marketData.bidList.add(order);
/* 409:    */         }
/* 410:    */       }
/* 411:418 */       if (!tokens[(aIndex + 1)].equals("NO_ASKS")) {
/* 412:419 */         for (int j = aIndex + 1; j < tIndex; j += 3)
/* 413:    */         {
/* 414:420 */           int pos = Integer.parseInt(tokens[j]);
/* 415:    */           
/* 416:    */ 
/* 417:423 */           int amount = Integer.parseInt(tokens[(j + 1)]);
/* 418:    */           
/* 419:425 */           Float price = Float.valueOf(Float.parseFloat(tokens[(j + 2)]));
/* 420:426 */           Order order = new Order(ticker, OrderType.SELL, amount, price.floatValue());
/* 421:427 */           order.position = pos;
/* 422:428 */           marketData.askList.add(order);
/* 423:    */         }
/* 424:    */       }
/* 425:434 */       if (!tokens[(tIndex + 1)].equals("NO_TRANSACTIONS"))
/* 426:    */       {
/* 427:440 */         int amount = Integer.parseInt(tokens[(tIndex + 1)]);
/* 428:    */         
/* 429:442 */         Float price = Float.valueOf(Float.parseFloat(tokens[(tIndex + 2)]));
/* 430:    */         
/* 431:444 */         Date time = new Date(0L);
/* 432:    */         try
/* 433:    */         {
/* 434:446 */           time = formatter.parse(tokens[(tIndex + 3)]);
/* 435:    */         }
/* 436:    */         catch (ParseException e)
/* 437:    */         {
/* 438:449 */           MainLogger.error(e);
/* 439:450 */           MainLogger.error("ERROR: Error while parsing time string...");
/* 440:    */         }
/* 441:453 */         Transaction last = new Transaction(ticker, amount, price.floatValue(), time);
/* 442:455 */         if (n != 0)
/* 443:    */         {
/* 444:456 */           StockMarketData previousRecord = records[(n - 1)];
/* 445:461 */           if (!previousRecord.transactionList.isEmpty())
/* 446:    */           {
/* 447:462 */             marketData.transactionList.addAll(previousRecord.transactionList);
/* 448:465 */             if (!((Transaction)previousRecord.transactionList.get(0)).equals(last)) {
/* 449:466 */               marketData.transactionList.add(0, last);
/* 450:    */             }
/* 451:    */           }
/* 452:    */           else
/* 453:    */           {
/* 454:469 */             MainLogger.debug("Transacion list empty!");
/* 455:    */           }
/* 456:    */         }
/* 457:    */         else
/* 458:    */         {
/* 459:473 */           marketData.transactionList.add(0, last);
/* 460:    */         }
/* 461:    */       }
/* 462:481 */       records[n] = marketData;
/* 463:    */     }
/* 464:489 */     return records;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public static StockMarketData[] parse(String[] lines, Date marketDay)
/* 468:    */   {
/* 469:494 */     StockMarketData[] records = new StockMarketData[lines.length];
/* 470:495 */     SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
/* 471:496 */     String ticker = "";
/* 472:497 */     String timeString = "";
/* 473:499 */     for (int n = 0; n < lines.length; n++)
/* 474:    */     {
/* 475:502 */       String[] tokens = lines[n].split(",");
/* 476:503 */       int bIndex = ParasiteUtils.getFirstIndexOfToken("B", tokens);
/* 477:504 */       int aIndex = ParasiteUtils.getFirstIndexOfToken("A", tokens);
/* 478:505 */       int tIndex = ParasiteUtils.getFirstIndexOfToken("ALL_T", tokens);
/* 479:506 */       int fIndex = ParasiteUtils.getFirstNanTokenIndexAfter(tIndex, tokens);
/* 480:507 */       if (fIndex == -1) {
/* 481:508 */         fIndex = tokens.length - 1;
/* 482:    */       }
/* 483:511 */       if (bIndex != 2) {
/* 484:512 */         return null;
/* 485:    */       }
/* 486:514 */       ticker = tokens[0];
/* 487:    */       
/* 488:516 */       StockMarketData marketData = new StockMarketData(ticker);
/* 489:    */       try
/* 490:    */       {
/* 491:520 */         Date time = formatter.parse(tokens[1]);
/* 492:521 */         marketData.time = ParasiteUtils.combineDateAndTimeIntoOneDate(time, marketDay);
/* 493:    */       }
/* 494:    */       catch (ParseException e)
/* 495:    */       {
/* 496:524 */         MainLogger.error(lines[n]);
/* 497:525 */         MainLogger.error(e);
/* 498:    */       }
/* 499:529 */       if (!tokens[(bIndex + 1)].equals("NO_BIDS")) {
/* 500:530 */         for (int k = bIndex + 1; k < aIndex; k += 3)
/* 501:    */         {
/* 502:531 */           int pos = Integer.parseInt(tokens[k]);
/* 503:    */           
/* 504:    */ 
/* 505:534 */           int amount = Integer.parseInt(tokens[(k + 1)]);
/* 506:    */           
/* 507:536 */           Float price = Float.valueOf(Float.parseFloat(tokens[(k + 2)]));
/* 508:537 */           Order order = new Order(ticker, OrderType.BUY, amount, price.floatValue());
/* 509:538 */           order.position = pos;
/* 510:539 */           marketData.bidList.add(order);
/* 511:    */         }
/* 512:    */       }
/* 513:544 */       if (!tokens[(aIndex + 1)].equals("NO_ASKS")) {
/* 514:546 */         for (int j = aIndex + 1; j < tIndex; j += 3)
/* 515:    */         {
/* 516:547 */           int pos = Integer.parseInt(tokens[j]);
/* 517:    */           
/* 518:    */ 
/* 519:550 */           int amount = Integer.parseInt(tokens[(j + 1)]);
/* 520:    */           
/* 521:552 */           Float price = Float.valueOf(Float.parseFloat(tokens[(j + 2)]));
/* 522:553 */           Order order = new Order(ticker, OrderType.SELL, amount, price.floatValue());
/* 523:554 */           order.position = pos;
/* 524:555 */           marketData.askList.add(order);
/* 525:    */         }
/* 526:    */       }
/* 527:560 */       if (!tokens[(tIndex + 1)].equals("NO_TRANSACTIONS")) {
/* 528:561 */         for (int m = tIndex + 1; m < fIndex; m += 3)
/* 529:    */         {
/* 530:562 */           int amount = Integer.parseInt(tokens[m]);
/* 531:    */           
/* 532:564 */           Float price = Float.valueOf(Float.parseFloat(tokens[(m + 1)]));
/* 533:    */           
/* 534:566 */           Date time = new Date(0L);
/* 535:    */           try
/* 536:    */           {
/* 537:568 */             time = formatter.parse(tokens[(m + 2)]);
/* 538:    */           }
/* 539:    */           catch (ParseException e)
/* 540:    */           {
/* 541:571 */             MainLogger.error(e);
/* 542:572 */             MainLogger.error("ERROR: Error while parsing time string...");
/* 543:    */           }
/* 544:574 */           marketData.transactionList.add(new Transaction(ticker, amount, price.floatValue(), time));
/* 545:    */         }
/* 546:    */       }
/* 547:579 */       records[n] = marketData;
/* 548:    */     }
/* 549:583 */     return records;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setDataChanged()
/* 553:    */   {
/* 554:588 */     setChanged();
/* 555:    */   }
/* 556:    */   
/* 557:    */   public boolean equals(StockMarketData md)
/* 558:    */   {
/* 559:592 */     if (md.toString().equals(toString())) {
/* 560:593 */       return true;
/* 561:    */     }
/* 562:595 */     return false;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public boolean hasSameDataAs(StockMarketData md)
/* 566:    */   {
/* 567:599 */     String str1 = md.toString();
/* 568:600 */     str1 = str1.substring(str1.indexOf("B"));
/* 569:601 */     String str2 = toString();
/* 570:602 */     str2 = str2.substring(str2.indexOf("B"));
/* 571:604 */     if (str1.equals(str2)) {
/* 572:605 */       return true;
/* 573:    */     }
/* 574:607 */     return false;
/* 575:    */   }
/* 576:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.StockMarketData
 * JD-Core Version:    0.7.0.1
 */