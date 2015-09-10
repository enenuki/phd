/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.biff.BuiltInName;
/*   4:    */ import jxl.biff.IntegerHelper;
/*   5:    */ import jxl.biff.StringHelper;
/*   6:    */ import jxl.biff.Type;
/*   7:    */ import jxl.biff.WritableRecordData;
/*   8:    */ import jxl.common.Logger;
/*   9:    */ 
/*  10:    */ class NameRecord
/*  11:    */   extends WritableRecordData
/*  12:    */ {
/*  13: 37 */   private static Logger logger = Logger.getLogger(NameRecord.class);
/*  14:    */   private byte[] data;
/*  15:    */   private String name;
/*  16:    */   private BuiltInName builtInName;
/*  17:    */   private int index;
/*  18: 62 */   private int sheetRef = 0;
/*  19:    */   private boolean modified;
/*  20:    */   private NameRange[] ranges;
/*  21:    */   private static final int cellReference = 58;
/*  22:    */   private static final int areaReference = 59;
/*  23:    */   private static final int subExpression = 41;
/*  24:    */   private static final int union = 16;
/*  25:    */   
/*  26:    */   static class NameRange
/*  27:    */   {
/*  28:    */     private int columnFirst;
/*  29:    */     private int rowFirst;
/*  30:    */     private int columnLast;
/*  31:    */     private int rowLast;
/*  32:    */     private int externalSheet;
/*  33:    */     
/*  34:    */     NameRange(jxl.read.biff.NameRecord.NameRange nr)
/*  35:    */     {
/*  36: 82 */       this.columnFirst = nr.getFirstColumn();
/*  37: 83 */       this.rowFirst = nr.getFirstRow();
/*  38: 84 */       this.columnLast = nr.getLastColumn();
/*  39: 85 */       this.rowLast = nr.getLastRow();
/*  40: 86 */       this.externalSheet = nr.getExternalSheet();
/*  41:    */     }
/*  42:    */     
/*  43:    */     NameRange(int extSheet, int theStartRow, int theEndRow, int theStartCol, int theEndCol)
/*  44:    */     {
/*  45: 98 */       this.columnFirst = theStartCol;
/*  46: 99 */       this.rowFirst = theStartRow;
/*  47:100 */       this.columnLast = theEndCol;
/*  48:101 */       this.rowLast = theEndRow;
/*  49:102 */       this.externalSheet = extSheet;
/*  50:    */     }
/*  51:    */     
/*  52:    */     int getFirstColumn()
/*  53:    */     {
/*  54:105 */       return this.columnFirst;
/*  55:    */     }
/*  56:    */     
/*  57:    */     int getFirstRow()
/*  58:    */     {
/*  59:106 */       return this.rowFirst;
/*  60:    */     }
/*  61:    */     
/*  62:    */     int getLastColumn()
/*  63:    */     {
/*  64:107 */       return this.columnLast;
/*  65:    */     }
/*  66:    */     
/*  67:    */     int getLastRow()
/*  68:    */     {
/*  69:108 */       return this.rowLast;
/*  70:    */     }
/*  71:    */     
/*  72:    */     int getExternalSheet()
/*  73:    */     {
/*  74:109 */       return this.externalSheet;
/*  75:    */     }
/*  76:    */     
/*  77:    */     void incrementFirstRow()
/*  78:    */     {
/*  79:111 */       this.rowFirst += 1;
/*  80:    */     }
/*  81:    */     
/*  82:    */     void incrementLastRow()
/*  83:    */     {
/*  84:112 */       this.rowLast += 1;
/*  85:    */     }
/*  86:    */     
/*  87:    */     void decrementFirstRow()
/*  88:    */     {
/*  89:113 */       this.rowFirst -= 1;
/*  90:    */     }
/*  91:    */     
/*  92:    */     void decrementLastRow()
/*  93:    */     {
/*  94:114 */       this.rowLast -= 1;
/*  95:    */     }
/*  96:    */     
/*  97:    */     void incrementFirstColumn()
/*  98:    */     {
/*  99:115 */       this.columnFirst += 1;
/* 100:    */     }
/* 101:    */     
/* 102:    */     void incrementLastColumn()
/* 103:    */     {
/* 104:116 */       this.columnLast += 1;
/* 105:    */     }
/* 106:    */     
/* 107:    */     void decrementFirstColumn()
/* 108:    */     {
/* 109:117 */       this.columnFirst -= 1;
/* 110:    */     }
/* 111:    */     
/* 112:    */     void decrementLastColumn()
/* 113:    */     {
/* 114:118 */       this.columnLast -= 1;
/* 115:    */     }
/* 116:    */     
/* 117:    */     byte[] getData()
/* 118:    */     {
/* 119:122 */       byte[] d = new byte[10];
/* 120:    */       
/* 121:    */ 
/* 122:125 */       IntegerHelper.getTwoBytes(this.externalSheet, d, 0);
/* 123:    */       
/* 124:    */ 
/* 125:128 */       IntegerHelper.getTwoBytes(this.rowFirst, d, 2);
/* 126:    */       
/* 127:    */ 
/* 128:131 */       IntegerHelper.getTwoBytes(this.rowLast, d, 4);
/* 129:    */       
/* 130:    */ 
/* 131:134 */       IntegerHelper.getTwoBytes(this.columnFirst & 0xFF, d, 6);
/* 132:    */       
/* 133:    */ 
/* 134:137 */       IntegerHelper.getTwoBytes(this.columnLast & 0xFF, d, 8);
/* 135:    */       
/* 136:139 */       return d;
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:155 */   private static final NameRange EMPTY_RANGE = new NameRange(0, 0, 0, 0, 0);
/* 141:    */   
/* 142:    */   public NameRecord(jxl.read.biff.NameRecord sr, int ind)
/* 143:    */   {
/* 144:164 */     super(Type.NAME);
/* 145:    */     
/* 146:166 */     this.data = sr.getData();
/* 147:167 */     this.name = sr.getName();
/* 148:168 */     this.sheetRef = sr.getSheetRef();
/* 149:169 */     this.index = ind;
/* 150:170 */     this.modified = false;
/* 151:    */     
/* 152:    */ 
/* 153:173 */     jxl.read.biff.NameRecord.NameRange[] r = sr.getRanges();
/* 154:174 */     this.ranges = new NameRange[r.length];
/* 155:175 */     for (int i = 0; i < this.ranges.length; i++) {
/* 156:177 */       this.ranges[i] = new NameRange(r[i]);
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   NameRecord(String theName, int theIndex, int extSheet, int theStartRow, int theEndRow, int theStartCol, int theEndCol, boolean global)
/* 161:    */   {
/* 162:202 */     super(Type.NAME);
/* 163:    */     
/* 164:204 */     this.name = theName;
/* 165:205 */     this.index = theIndex;
/* 166:206 */     this.sheetRef = (global ? 0 : this.index + 1);
/* 167:    */     
/* 168:    */ 
/* 169:209 */     this.ranges = new NameRange[1];
/* 170:210 */     this.ranges[0] = new NameRange(extSheet, theStartRow, theEndRow, theStartCol, theEndCol);
/* 171:    */     
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:215 */     this.modified = true;
/* 176:    */   }
/* 177:    */   
/* 178:    */   NameRecord(BuiltInName theName, int theIndex, int extSheet, int theStartRow, int theEndRow, int theStartCol, int theEndCol, boolean global)
/* 179:    */   {
/* 180:239 */     super(Type.NAME);
/* 181:    */     
/* 182:241 */     this.builtInName = theName;
/* 183:242 */     this.index = theIndex;
/* 184:243 */     this.sheetRef = (global ? 0 : this.index + 1);
/* 185:    */     
/* 186:    */ 
/* 187:246 */     this.ranges = new NameRange[1];
/* 188:247 */     this.ranges[0] = new NameRange(extSheet, theStartRow, theEndRow, theStartCol, theEndCol);
/* 189:    */   }
/* 190:    */   
/* 191:    */   NameRecord(BuiltInName theName, int theIndex, int extSheet, int theStartRow, int theEndRow, int theStartCol, int theEndCol, int theStartRow2, int theEndRow2, int theStartCol2, int theEndCol2, boolean global)
/* 192:    */   {
/* 193:283 */     super(Type.NAME);
/* 194:    */     
/* 195:285 */     this.builtInName = theName;
/* 196:286 */     this.index = theIndex;
/* 197:287 */     this.sheetRef = (global ? 0 : this.index + 1);
/* 198:    */     
/* 199:    */ 
/* 200:290 */     this.ranges = new NameRange[2];
/* 201:291 */     this.ranges[0] = new NameRange(extSheet, theStartRow, theEndRow, theStartCol, theEndCol);
/* 202:    */     
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:296 */     this.ranges[1] = new NameRange(extSheet, theStartRow2, theEndRow2, theStartCol2, theEndCol2);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public byte[] getData()
/* 210:    */   {
/* 211:311 */     if ((this.data != null) && (!this.modified)) {
/* 212:314 */       return this.data;
/* 213:    */     }
/* 214:317 */     int NAME_HEADER_LENGTH = 15;
/* 215:318 */     byte AREA_RANGE_LENGTH = 11;
/* 216:319 */     byte AREA_REFERENCE = 59;
/* 217:    */     int detailLength;
/* 218:    */     int detailLength;
/* 219:323 */     if (this.ranges.length > 1) {
/* 220:325 */       detailLength = this.ranges.length * 11 + 4;
/* 221:    */     } else {
/* 222:329 */       detailLength = 11;
/* 223:    */     }
/* 224:332 */     int length = 15 + detailLength;
/* 225:333 */     length += (this.builtInName != null ? 1 : this.name.length());
/* 226:334 */     this.data = new byte[length];
/* 227:    */     
/* 228:    */ 
/* 229:337 */     int options = 0;
/* 230:339 */     if (this.builtInName != null) {
/* 231:341 */       options |= 0x20;
/* 232:    */     }
/* 233:343 */     IntegerHelper.getTwoBytes(options, this.data, 0);
/* 234:    */     
/* 235:    */ 
/* 236:346 */     this.data[2] = 0;
/* 237:349 */     if (this.builtInName != null) {
/* 238:351 */       this.data[3] = 1;
/* 239:    */     } else {
/* 240:355 */       this.data[3] = ((byte)this.name.length());
/* 241:    */     }
/* 242:359 */     IntegerHelper.getTwoBytes(detailLength, this.data, 4);
/* 243:    */     
/* 244:    */ 
/* 245:362 */     IntegerHelper.getTwoBytes(this.sheetRef, this.data, 6);
/* 246:363 */     IntegerHelper.getTwoBytes(this.sheetRef, this.data, 8);
/* 247:369 */     if (this.builtInName != null) {
/* 248:371 */       this.data[15] = ((byte)this.builtInName.getValue());
/* 249:    */     } else {
/* 250:375 */       StringHelper.getBytes(this.name, this.data, 15);
/* 251:    */     }
/* 252:379 */     int pos = this.builtInName != null ? 16 : this.name.length() + 15;
/* 253:384 */     if (this.ranges.length > 1)
/* 254:    */     {
/* 255:386 */       this.data[(pos++)] = 41;
/* 256:    */       
/* 257:388 */       IntegerHelper.getTwoBytes(detailLength - 3, this.data, pos);
/* 258:389 */       pos += 2;
/* 259:391 */       for (int i = 0; i < this.ranges.length; i++)
/* 260:    */       {
/* 261:393 */         this.data[(pos++)] = 59;
/* 262:394 */         byte[] rd = this.ranges[i].getData();
/* 263:395 */         System.arraycopy(rd, 0, this.data, pos, rd.length);
/* 264:396 */         pos += rd.length;
/* 265:    */       }
/* 266:398 */       this.data[pos] = 16;
/* 267:    */     }
/* 268:    */     else
/* 269:    */     {
/* 270:403 */       this.data[pos] = 59;
/* 271:    */       
/* 272:    */ 
/* 273:406 */       byte[] rd = this.ranges[0].getData();
/* 274:407 */       System.arraycopy(rd, 0, this.data, pos + 1, rd.length);
/* 275:    */     }
/* 276:410 */     return this.data;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public String getName()
/* 280:    */   {
/* 281:420 */     return this.name;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public int getIndex()
/* 285:    */   {
/* 286:430 */     return this.index;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public int getSheetRef()
/* 290:    */   {
/* 291:441 */     return this.sheetRef;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setSheetRef(int i)
/* 295:    */   {
/* 296:451 */     this.sheetRef = i;
/* 297:452 */     IntegerHelper.getTwoBytes(this.sheetRef, this.data, 8);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public NameRange[] getRanges()
/* 301:    */   {
/* 302:461 */     return this.ranges;
/* 303:    */   }
/* 304:    */   
/* 305:    */   void rowInserted(int sheetIndex, int row)
/* 306:    */   {
/* 307:472 */     for (int i = 0; i < this.ranges.length; i++) {
/* 308:474 */       if (sheetIndex == this.ranges[i].getExternalSheet())
/* 309:    */       {
/* 310:479 */         if (row <= this.ranges[i].getFirstRow())
/* 311:    */         {
/* 312:481 */           this.ranges[i].incrementFirstRow();
/* 313:482 */           this.modified = true;
/* 314:    */         }
/* 315:485 */         if (row <= this.ranges[i].getLastRow())
/* 316:    */         {
/* 317:487 */           this.ranges[i].incrementLastRow();
/* 318:488 */           this.modified = true;
/* 319:    */         }
/* 320:    */       }
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   boolean rowRemoved(int sheetIndex, int row)
/* 325:    */   {
/* 326:502 */     for (int i = 0; i < this.ranges.length; i++) {
/* 327:504 */       if (sheetIndex == this.ranges[i].getExternalSheet())
/* 328:    */       {
/* 329:509 */         if ((row == this.ranges[i].getFirstRow()) && (row == this.ranges[i].getLastRow())) {
/* 330:512 */           this.ranges[i] = EMPTY_RANGE;
/* 331:    */         }
/* 332:515 */         if ((row < this.ranges[i].getFirstRow()) && (row > 0))
/* 333:    */         {
/* 334:517 */           this.ranges[i].decrementFirstRow();
/* 335:518 */           this.modified = true;
/* 336:    */         }
/* 337:521 */         if (row <= this.ranges[i].getLastRow())
/* 338:    */         {
/* 339:523 */           this.ranges[i].decrementLastRow();
/* 340:524 */           this.modified = true;
/* 341:    */         }
/* 342:    */       }
/* 343:    */     }
/* 344:529 */     int emptyRanges = 0;
/* 345:530 */     for (int i = 0; i < this.ranges.length; i++) {
/* 346:532 */       if (this.ranges[i] == EMPTY_RANGE) {
/* 347:534 */         emptyRanges++;
/* 348:    */       }
/* 349:    */     }
/* 350:538 */     if (emptyRanges == this.ranges.length) {
/* 351:540 */       return true;
/* 352:    */     }
/* 353:544 */     NameRange[] newRanges = new NameRange[this.ranges.length - emptyRanges];
/* 354:545 */     for (int i = 0; i < this.ranges.length; i++) {
/* 355:547 */       if (this.ranges[i] != EMPTY_RANGE) {
/* 356:549 */         newRanges[i] = this.ranges[i];
/* 357:    */       }
/* 358:    */     }
/* 359:553 */     this.ranges = newRanges;
/* 360:    */     
/* 361:555 */     return false;
/* 362:    */   }
/* 363:    */   
/* 364:    */   boolean columnRemoved(int sheetIndex, int col)
/* 365:    */   {
/* 366:567 */     for (int i = 0; i < this.ranges.length; i++) {
/* 367:569 */       if (sheetIndex == this.ranges[i].getExternalSheet())
/* 368:    */       {
/* 369:574 */         if ((col == this.ranges[i].getFirstColumn()) && (col == this.ranges[i].getLastColumn())) {
/* 370:578 */           this.ranges[i] = EMPTY_RANGE;
/* 371:    */         }
/* 372:581 */         if ((col < this.ranges[i].getFirstColumn()) && (col > 0))
/* 373:    */         {
/* 374:583 */           this.ranges[i].decrementFirstColumn();
/* 375:584 */           this.modified = true;
/* 376:    */         }
/* 377:587 */         if (col <= this.ranges[i].getLastColumn())
/* 378:    */         {
/* 379:589 */           this.ranges[i].decrementLastColumn();
/* 380:590 */           this.modified = true;
/* 381:    */         }
/* 382:    */       }
/* 383:    */     }
/* 384:595 */     int emptyRanges = 0;
/* 385:596 */     for (int i = 0; i < this.ranges.length; i++) {
/* 386:598 */       if (this.ranges[i] == EMPTY_RANGE) {
/* 387:600 */         emptyRanges++;
/* 388:    */       }
/* 389:    */     }
/* 390:604 */     if (emptyRanges == this.ranges.length) {
/* 391:606 */       return true;
/* 392:    */     }
/* 393:610 */     NameRange[] newRanges = new NameRange[this.ranges.length - emptyRanges];
/* 394:611 */     for (int i = 0; i < this.ranges.length; i++) {
/* 395:613 */       if (this.ranges[i] != EMPTY_RANGE) {
/* 396:615 */         newRanges[i] = this.ranges[i];
/* 397:    */       }
/* 398:    */     }
/* 399:619 */     this.ranges = newRanges;
/* 400:    */     
/* 401:621 */     return false;
/* 402:    */   }
/* 403:    */   
/* 404:    */   void columnInserted(int sheetIndex, int col)
/* 405:    */   {
/* 406:633 */     for (int i = 0; i < this.ranges.length; i++) {
/* 407:635 */       if (sheetIndex == this.ranges[i].getExternalSheet())
/* 408:    */       {
/* 409:640 */         if (col <= this.ranges[i].getFirstColumn())
/* 410:    */         {
/* 411:642 */           this.ranges[i].incrementFirstColumn();
/* 412:643 */           this.modified = true;
/* 413:    */         }
/* 414:646 */         if (col <= this.ranges[i].getLastColumn())
/* 415:    */         {
/* 416:648 */           this.ranges[i].incrementLastColumn();
/* 417:649 */           this.modified = true;
/* 418:    */         }
/* 419:    */       }
/* 420:    */     }
/* 421:    */   }
/* 422:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.NameRecord
 * JD-Core Version:    0.7.0.1
 */