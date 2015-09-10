/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import jxl.CellType;
/*   7:    */ import jxl.SheetSettings;
/*   8:    */ import jxl.biff.CellReferenceHelper;
/*   9:    */ import jxl.biff.DVParser;
/*  10:    */ import jxl.biff.IndexMapping;
/*  11:    */ import jxl.biff.IntegerHelper;
/*  12:    */ import jxl.biff.Type;
/*  13:    */ import jxl.biff.WritableRecordData;
/*  14:    */ import jxl.biff.XFRecord;
/*  15:    */ import jxl.common.Logger;
/*  16:    */ import jxl.write.Number;
/*  17:    */ import jxl.write.WritableCellFeatures;
/*  18:    */ import jxl.write.WritableSheet;
/*  19:    */ 
/*  20:    */ class RowRecord
/*  21:    */   extends WritableRecordData
/*  22:    */ {
/*  23: 49 */   private static final Logger logger = Logger.getLogger(RowRecord.class);
/*  24:    */   private byte[] data;
/*  25:    */   private CellValue[] cells;
/*  26:    */   private int rowHeight;
/*  27:    */   private boolean collapsed;
/*  28:    */   private int rowNumber;
/*  29:    */   private int numColumns;
/*  30:    */   private int xfIndex;
/*  31:    */   private XFRecord style;
/*  32:    */   private boolean defaultFormat;
/*  33:    */   private boolean matchesDefFontHeight;
/*  34:    */   private static final int growSize = 10;
/*  35:    */   private static final int maxRKValue = 536870911;
/*  36:    */   private static final int minRKValue = -536870912;
/*  37:109 */   private static int defaultHeightIndicator = 255;
/*  38:114 */   private static int maxColumns = 256;
/*  39:    */   private int outlineLevel;
/*  40:    */   private boolean groupStart;
/*  41:    */   private WritableSheet sheet;
/*  42:    */   
/*  43:    */   public RowRecord(int rn, WritableSheet ws)
/*  44:    */   {
/*  45:138 */     super(Type.ROW);
/*  46:139 */     this.rowNumber = rn;
/*  47:140 */     this.cells = new CellValue[0];
/*  48:141 */     this.numColumns = 0;
/*  49:142 */     this.rowHeight = defaultHeightIndicator;
/*  50:143 */     this.collapsed = false;
/*  51:144 */     this.matchesDefFontHeight = true;
/*  52:145 */     this.sheet = ws;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setRowHeight(int h)
/*  56:    */   {
/*  57:155 */     if (h == 0)
/*  58:    */     {
/*  59:157 */       setCollapsed(true);
/*  60:158 */       this.matchesDefFontHeight = false;
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64:162 */       this.rowHeight = h;
/*  65:163 */       this.matchesDefFontHeight = false;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   void setRowDetails(int height, boolean mdfh, boolean col, int ol, boolean gs, XFRecord xfr)
/*  70:    */   {
/*  71:185 */     this.rowHeight = height;
/*  72:186 */     this.collapsed = col;
/*  73:187 */     this.matchesDefFontHeight = mdfh;
/*  74:188 */     this.outlineLevel = ol;
/*  75:189 */     this.groupStart = gs;
/*  76:191 */     if (xfr != null)
/*  77:    */     {
/*  78:193 */       this.defaultFormat = true;
/*  79:194 */       this.style = xfr;
/*  80:195 */       this.xfIndex = this.style.getXFIndex();
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setCollapsed(boolean c)
/*  85:    */   {
/*  86:206 */     this.collapsed = c;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getRowNumber()
/*  90:    */   {
/*  91:216 */     return this.rowNumber;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void addCell(CellValue cv)
/*  95:    */   {
/*  96:226 */     int col = cv.getColumn();
/*  97:228 */     if (col >= maxColumns)
/*  98:    */     {
/*  99:230 */       logger.warn("Could not add cell at " + CellReferenceHelper.getCellReference(cv.getRow(), cv.getColumn()) + " because it exceeds the maximum column limit");
/* 100:    */       
/* 101:    */ 
/* 102:    */ 
/* 103:234 */       return;
/* 104:    */     }
/* 105:238 */     if (col >= this.cells.length)
/* 106:    */     {
/* 107:240 */       CellValue[] oldCells = this.cells;
/* 108:241 */       this.cells = new CellValue[Math.max(oldCells.length + 10, col + 1)];
/* 109:242 */       System.arraycopy(oldCells, 0, this.cells, 0, oldCells.length);
/* 110:243 */       oldCells = null;
/* 111:    */     }
/* 112:247 */     if (this.cells[col] != null)
/* 113:    */     {
/* 114:249 */       WritableCellFeatures wcf = this.cells[col].getWritableCellFeatures();
/* 115:250 */       if (wcf != null)
/* 116:    */       {
/* 117:252 */         wcf.removeComment();
/* 118:256 */         if ((wcf.getDVParser() != null) && (!wcf.getDVParser().extendedCellsValidation())) {
/* 119:259 */           wcf.removeDataValidation();
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:265 */     this.cells[col] = cv;
/* 124:    */     
/* 125:267 */     this.numColumns = Math.max(col + 1, this.numColumns);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void removeCell(int col)
/* 129:    */   {
/* 130:278 */     if (col >= this.numColumns) {
/* 131:280 */       return;
/* 132:    */     }
/* 133:283 */     this.cells[col] = null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void write(File outputFile)
/* 137:    */     throws IOException
/* 138:    */   {
/* 139:294 */     outputFile.write(this);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void writeCells(File outputFile)
/* 143:    */     throws IOException
/* 144:    */   {
/* 145:309 */     ArrayList integerValues = new ArrayList();
/* 146:310 */     boolean integerValue = false;
/* 147:313 */     for (int i = 0; i < this.numColumns; i++)
/* 148:    */     {
/* 149:315 */       integerValue = false;
/* 150:316 */       if (this.cells[i] != null)
/* 151:    */       {
/* 152:320 */         if (this.cells[i].getType() == CellType.NUMBER)
/* 153:    */         {
/* 154:322 */           Number nc = (Number)this.cells[i];
/* 155:323 */           if ((nc.getValue() == (int)nc.getValue()) && (nc.getValue() < 536870911.0D) && (nc.getValue() > -536870912.0D) && (nc.getCellFeatures() == null)) {
/* 156:328 */             integerValue = true;
/* 157:    */           }
/* 158:    */         }
/* 159:332 */         if (integerValue)
/* 160:    */         {
/* 161:335 */           integerValues.add(this.cells[i]);
/* 162:    */         }
/* 163:    */         else
/* 164:    */         {
/* 165:341 */           writeIntegerValues(integerValues, outputFile);
/* 166:342 */           outputFile.write(this.cells[i]);
/* 167:346 */           if (this.cells[i].getType() == CellType.STRING_FORMULA)
/* 168:    */           {
/* 169:348 */             StringRecord sr = new StringRecord(this.cells[i].getContents());
/* 170:349 */             outputFile.write(sr);
/* 171:    */           }
/* 172:    */         }
/* 173:    */       }
/* 174:    */       else
/* 175:    */       {
/* 176:357 */         writeIntegerValues(integerValues, outputFile);
/* 177:    */       }
/* 178:    */     }
/* 179:362 */     writeIntegerValues(integerValues, outputFile);
/* 180:    */   }
/* 181:    */   
/* 182:    */   private void writeIntegerValues(ArrayList integerValues, File outputFile)
/* 183:    */     throws IOException
/* 184:    */   {
/* 185:376 */     if (integerValues.size() == 0) {
/* 186:378 */       return;
/* 187:    */     }
/* 188:381 */     if (integerValues.size() >= 3)
/* 189:    */     {
/* 190:384 */       MulRKRecord mulrk = new MulRKRecord(integerValues);
/* 191:385 */       outputFile.write(mulrk);
/* 192:    */     }
/* 193:    */     else
/* 194:    */     {
/* 195:390 */       Iterator i = integerValues.iterator();
/* 196:391 */       while (i.hasNext()) {
/* 197:393 */         outputFile.write((CellValue)i.next());
/* 198:    */       }
/* 199:    */     }
/* 200:398 */     integerValues.clear();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public byte[] getData()
/* 204:    */   {
/* 205:409 */     byte[] data = new byte[16];
/* 206:    */     
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:414 */     int rh = this.rowHeight;
/* 211:415 */     if (this.sheet.getSettings().getDefaultRowHeight() != 255) {
/* 212:420 */       if (rh == defaultHeightIndicator) {
/* 213:422 */         rh = this.sheet.getSettings().getDefaultRowHeight();
/* 214:    */       }
/* 215:    */     }
/* 216:426 */     IntegerHelper.getTwoBytes(this.rowNumber, data, 0);
/* 217:427 */     IntegerHelper.getTwoBytes(this.numColumns, data, 4);
/* 218:428 */     IntegerHelper.getTwoBytes(rh, data, 6);
/* 219:    */     
/* 220:430 */     int options = 256 + this.outlineLevel;
/* 221:432 */     if (this.groupStart) {
/* 222:434 */       options |= 0x10;
/* 223:    */     }
/* 224:437 */     if (this.collapsed) {
/* 225:439 */       options |= 0x20;
/* 226:    */     }
/* 227:442 */     if (!this.matchesDefFontHeight) {
/* 228:444 */       options |= 0x40;
/* 229:    */     }
/* 230:447 */     if (this.defaultFormat)
/* 231:    */     {
/* 232:449 */       options |= 0x80;
/* 233:450 */       options |= this.xfIndex << 16;
/* 234:    */     }
/* 235:453 */     IntegerHelper.getFourBytes(options, data, 12);
/* 236:    */     
/* 237:455 */     return data;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public int getMaxColumn()
/* 241:    */   {
/* 242:465 */     return this.numColumns;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public CellValue getCell(int col)
/* 246:    */   {
/* 247:477 */     return (col >= 0) && (col < this.numColumns) ? this.cells[col] : null;
/* 248:    */   }
/* 249:    */   
/* 250:    */   void incrementRow()
/* 251:    */   {
/* 252:486 */     this.rowNumber += 1;
/* 253:488 */     for (int i = 0; i < this.cells.length; i++) {
/* 254:490 */       if (this.cells[i] != null) {
/* 255:492 */         this.cells[i].incrementRow();
/* 256:    */       }
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   void decrementRow()
/* 261:    */   {
/* 262:503 */     this.rowNumber -= 1;
/* 263:504 */     for (int i = 0; i < this.cells.length; i++) {
/* 264:506 */       if (this.cells[i] != null) {
/* 265:508 */         this.cells[i].decrementRow();
/* 266:    */       }
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   void insertColumn(int col)
/* 271:    */   {
/* 272:523 */     if (col >= this.numColumns) {
/* 273:525 */       return;
/* 274:    */     }
/* 275:529 */     CellValue[] oldCells = this.cells;
/* 276:531 */     if (this.numColumns >= this.cells.length - 1) {
/* 277:533 */       this.cells = new CellValue[oldCells.length + 10];
/* 278:    */     } else {
/* 279:537 */       this.cells = new CellValue[oldCells.length];
/* 280:    */     }
/* 281:541 */     System.arraycopy(oldCells, 0, this.cells, 0, col);
/* 282:    */     
/* 283:    */ 
/* 284:544 */     System.arraycopy(oldCells, col, this.cells, col + 1, this.numColumns - col);
/* 285:547 */     for (int i = col + 1; i <= this.numColumns; i++) {
/* 286:549 */       if (this.cells[i] != null) {
/* 287:551 */         this.cells[i].incrementColumn();
/* 288:    */       }
/* 289:    */     }
/* 290:556 */     this.numColumns = Math.min(this.numColumns + 1, maxColumns);
/* 291:    */   }
/* 292:    */   
/* 293:    */   void removeColumn(int col)
/* 294:    */   {
/* 295:568 */     if (col >= this.numColumns) {
/* 296:570 */       return;
/* 297:    */     }
/* 298:574 */     CellValue[] oldCells = this.cells;
/* 299:    */     
/* 300:576 */     this.cells = new CellValue[oldCells.length];
/* 301:    */     
/* 302:    */ 
/* 303:579 */     System.arraycopy(oldCells, 0, this.cells, 0, col);
/* 304:    */     
/* 305:    */ 
/* 306:582 */     System.arraycopy(oldCells, col + 1, this.cells, col, this.numColumns - (col + 1));
/* 307:585 */     for (int i = col; i < this.numColumns; i++) {
/* 308:587 */       if (this.cells[i] != null) {
/* 309:589 */         this.cells[i].decrementColumn();
/* 310:    */       }
/* 311:    */     }
/* 312:594 */     this.numColumns -= 1;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public boolean isDefaultHeight()
/* 316:    */   {
/* 317:604 */     return this.rowHeight == defaultHeightIndicator;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public int getRowHeight()
/* 321:    */   {
/* 322:614 */     return this.rowHeight;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public boolean isCollapsed()
/* 326:    */   {
/* 327:624 */     return this.collapsed;
/* 328:    */   }
/* 329:    */   
/* 330:    */   void rationalize(IndexMapping xfmapping)
/* 331:    */   {
/* 332:633 */     if (this.defaultFormat) {
/* 333:635 */       this.xfIndex = xfmapping.getNewIndex(this.xfIndex);
/* 334:    */     }
/* 335:    */   }
/* 336:    */   
/* 337:    */   XFRecord getStyle()
/* 338:    */   {
/* 339:647 */     return this.style;
/* 340:    */   }
/* 341:    */   
/* 342:    */   boolean hasDefaultFormat()
/* 343:    */   {
/* 344:657 */     return this.defaultFormat;
/* 345:    */   }
/* 346:    */   
/* 347:    */   boolean matchesDefaultFontHeight()
/* 348:    */   {
/* 349:667 */     return this.matchesDefFontHeight;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public int getOutlineLevel()
/* 353:    */   {
/* 354:677 */     return this.outlineLevel;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public boolean getGroupStart()
/* 358:    */   {
/* 359:687 */     return this.groupStart;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void incrementOutlineLevel()
/* 363:    */   {
/* 364:695 */     this.outlineLevel += 1;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void decrementOutlineLevel()
/* 368:    */   {
/* 369:705 */     if (0 < this.outlineLevel) {
/* 370:707 */       this.outlineLevel -= 1;
/* 371:    */     }
/* 372:710 */     if (0 == this.outlineLevel) {
/* 373:712 */       this.collapsed = false;
/* 374:    */     }
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void setOutlineLevel(int level)
/* 378:    */   {
/* 379:723 */     this.outlineLevel = level;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setGroupStart(boolean value)
/* 383:    */   {
/* 384:733 */     this.groupStart = value;
/* 385:    */   }
/* 386:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.RowRecord
 * JD-Core Version:    0.7.0.1
 */