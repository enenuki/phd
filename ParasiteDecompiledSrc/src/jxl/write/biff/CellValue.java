/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.Cell;
/*   4:    */ import jxl.CellFeatures;
/*   5:    */ import jxl.CellReferenceHelper;
/*   6:    */ import jxl.Sheet;
/*   7:    */ import jxl.biff.DVParser;
/*   8:    */ import jxl.biff.FormattingRecords;
/*   9:    */ import jxl.biff.IntegerHelper;
/*  10:    */ import jxl.biff.NumFormatRecordsException;
/*  11:    */ import jxl.biff.Type;
/*  12:    */ import jxl.biff.WritableRecordData;
/*  13:    */ import jxl.biff.XFRecord;
/*  14:    */ import jxl.biff.drawing.ComboBox;
/*  15:    */ import jxl.biff.drawing.Comment;
/*  16:    */ import jxl.biff.formula.FormulaException;
/*  17:    */ import jxl.common.Assert;
/*  18:    */ import jxl.common.Logger;
/*  19:    */ import jxl.format.CellFormat;
/*  20:    */ import jxl.write.WritableCell;
/*  21:    */ import jxl.write.WritableCellFeatures;
/*  22:    */ import jxl.write.WritableWorkbook;
/*  23:    */ 
/*  24:    */ public abstract class CellValue
/*  25:    */   extends WritableRecordData
/*  26:    */   implements WritableCell
/*  27:    */ {
/*  28: 58 */   private static Logger logger = Logger.getLogger(CellValue.class);
/*  29:    */   private int row;
/*  30:    */   private int column;
/*  31:    */   private XFRecord format;
/*  32:    */   private FormattingRecords formattingRecords;
/*  33:    */   private boolean referenced;
/*  34:    */   private WritableSheetImpl sheet;
/*  35:    */   private WritableCellFeatures features;
/*  36:    */   private boolean copied;
/*  37:    */   
/*  38:    */   protected CellValue(Type t, int c, int r)
/*  39:    */   {
/*  40:113 */     this(t, c, r, WritableWorkbook.NORMAL_STYLE);
/*  41:114 */     this.copied = false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected CellValue(Type t, Cell c)
/*  45:    */   {
/*  46:126 */     this(t, c.getColumn(), c.getRow());
/*  47:127 */     this.copied = true;
/*  48:    */     
/*  49:129 */     this.format = ((XFRecord)c.getCellFormat());
/*  50:131 */     if (c.getCellFeatures() != null)
/*  51:    */     {
/*  52:133 */       this.features = new WritableCellFeatures(c.getCellFeatures());
/*  53:134 */       this.features.setWritableCell(this);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected CellValue(Type t, int c, int r, CellFormat st)
/*  58:    */   {
/*  59:149 */     super(t);
/*  60:150 */     this.row = r;
/*  61:151 */     this.column = c;
/*  62:152 */     this.format = ((XFRecord)st);
/*  63:153 */     this.referenced = false;
/*  64:154 */     this.copied = false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected CellValue(Type t, int c, int r, CellValue cv)
/*  68:    */   {
/*  69:167 */     super(t);
/*  70:168 */     this.row = r;
/*  71:169 */     this.column = c;
/*  72:170 */     this.format = cv.format;
/*  73:171 */     this.referenced = false;
/*  74:172 */     this.copied = false;
/*  75:175 */     if (cv.features != null)
/*  76:    */     {
/*  77:177 */       this.features = new WritableCellFeatures(cv.features);
/*  78:178 */       this.features.setWritableCell(this);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setCellFormat(CellFormat cf)
/*  83:    */   {
/*  84:189 */     this.format = ((XFRecord)cf);
/*  85:194 */     if (!this.referenced) {
/*  86:196 */       return;
/*  87:    */     }
/*  88:201 */     Assert.verify(this.formattingRecords != null);
/*  89:    */     
/*  90:203 */     addCellFormat();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getRow()
/*  94:    */   {
/*  95:213 */     return this.row;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getColumn()
/*  99:    */   {
/* 100:223 */     return this.column;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isHidden()
/* 104:    */   {
/* 105:234 */     ColumnInfoRecord cir = this.sheet.getColumnInfo(this.column);
/* 106:236 */     if ((cir != null) && (cir.getWidth() == 0)) {
/* 107:238 */       return true;
/* 108:    */     }
/* 109:241 */     RowRecord rr = this.sheet.getRowInfo(this.row);
/* 110:243 */     if ((rr != null) && ((rr.getRowHeight() == 0) || (rr.isCollapsed()))) {
/* 111:245 */       return true;
/* 112:    */     }
/* 113:248 */     return false;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public byte[] getData()
/* 117:    */   {
/* 118:258 */     byte[] mydata = new byte[6];
/* 119:259 */     IntegerHelper.getTwoBytes(this.row, mydata, 0);
/* 120:260 */     IntegerHelper.getTwoBytes(this.column, mydata, 2);
/* 121:261 */     IntegerHelper.getTwoBytes(this.format.getXFIndex(), mydata, 4);
/* 122:262 */     return mydata;
/* 123:    */   }
/* 124:    */   
/* 125:    */   void setCellDetails(FormattingRecords fr, SharedStrings ss, WritableSheetImpl s)
/* 126:    */   {
/* 127:278 */     this.referenced = true;
/* 128:279 */     this.sheet = s;
/* 129:280 */     this.formattingRecords = fr;
/* 130:    */     
/* 131:282 */     addCellFormat();
/* 132:283 */     addCellFeatures();
/* 133:    */   }
/* 134:    */   
/* 135:    */   final boolean isReferenced()
/* 136:    */   {
/* 137:294 */     return this.referenced;
/* 138:    */   }
/* 139:    */   
/* 140:    */   final int getXFIndex()
/* 141:    */   {
/* 142:304 */     return this.format.getXFIndex();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public CellFormat getCellFormat()
/* 146:    */   {
/* 147:314 */     return this.format;
/* 148:    */   }
/* 149:    */   
/* 150:    */   void incrementRow()
/* 151:    */   {
/* 152:323 */     this.row += 1;
/* 153:325 */     if (this.features != null)
/* 154:    */     {
/* 155:327 */       Comment c = this.features.getCommentDrawing();
/* 156:328 */       if (c != null)
/* 157:    */       {
/* 158:330 */         c.setX(this.column);
/* 159:331 */         c.setY(this.row);
/* 160:    */       }
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   void decrementRow()
/* 165:    */   {
/* 166:342 */     this.row -= 1;
/* 167:344 */     if (this.features != null)
/* 168:    */     {
/* 169:346 */       Comment c = this.features.getCommentDrawing();
/* 170:347 */       if (c != null)
/* 171:    */       {
/* 172:349 */         c.setX(this.column);
/* 173:350 */         c.setY(this.row);
/* 174:    */       }
/* 175:353 */       if (this.features.hasDropDown()) {
/* 176:355 */         logger.warn("need to change value for drop down drawing");
/* 177:    */       }
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   void incrementColumn()
/* 182:    */   {
/* 183:366 */     this.column += 1;
/* 184:368 */     if (this.features != null)
/* 185:    */     {
/* 186:370 */       Comment c = this.features.getCommentDrawing();
/* 187:371 */       if (c != null)
/* 188:    */       {
/* 189:373 */         c.setX(this.column);
/* 190:374 */         c.setY(this.row);
/* 191:    */       }
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   void decrementColumn()
/* 196:    */   {
/* 197:386 */     this.column -= 1;
/* 198:388 */     if (this.features != null)
/* 199:    */     {
/* 200:390 */       Comment c = this.features.getCommentDrawing();
/* 201:391 */       if (c != null)
/* 202:    */       {
/* 203:393 */         c.setX(this.column);
/* 204:394 */         c.setY(this.row);
/* 205:    */       }
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   void columnInserted(Sheet s, int sheetIndex, int col) {}
/* 210:    */   
/* 211:    */   void columnRemoved(Sheet s, int sheetIndex, int col) {}
/* 212:    */   
/* 213:    */   void rowInserted(Sheet s, int sheetIndex, int row) {}
/* 214:    */   
/* 215:    */   void rowRemoved(Sheet s, int sheetIndex, int row) {}
/* 216:    */   
/* 217:    */   public WritableSheetImpl getSheet()
/* 218:    */   {
/* 219:455 */     return this.sheet;
/* 220:    */   }
/* 221:    */   
/* 222:    */   private void addCellFormat()
/* 223:    */   {
/* 224:467 */     Styles styles = this.sheet.getWorkbook().getStyles();
/* 225:468 */     this.format = styles.getFormat(this.format);
/* 226:    */     try
/* 227:    */     {
/* 228:472 */       if (!this.format.isInitialized()) {
/* 229:474 */         this.formattingRecords.addStyle(this.format);
/* 230:    */       }
/* 231:    */     }
/* 232:    */     catch (NumFormatRecordsException e)
/* 233:    */     {
/* 234:479 */       logger.warn("Maximum number of format records exceeded.  Using default format.");
/* 235:    */       
/* 236:481 */       this.format = styles.getNormalStyle();
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public CellFeatures getCellFeatures()
/* 241:    */   {
/* 242:492 */     return this.features;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public WritableCellFeatures getWritableCellFeatures()
/* 246:    */   {
/* 247:502 */     return this.features;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setCellFeatures(WritableCellFeatures cf)
/* 251:    */   {
/* 252:512 */     if (this.features != null)
/* 253:    */     {
/* 254:514 */       logger.warn("current cell features for " + CellReferenceHelper.getCellReference(this) + " not null - overwriting");
/* 255:519 */       if ((this.features.hasDataValidation()) && (this.features.getDVParser() != null) && (this.features.getDVParser().extendedCellsValidation()))
/* 256:    */       {
/* 257:523 */         DVParser dvp = this.features.getDVParser();
/* 258:524 */         logger.warn("Cannot add cell features to " + CellReferenceHelper.getCellReference(this) + " because it is part of the shared cell validation " + "group " + CellReferenceHelper.getCellReference(dvp.getFirstColumn(), dvp.getFirstRow()) + "-" + CellReferenceHelper.getCellReference(dvp.getLastColumn(), dvp.getLastRow()));
/* 259:    */         
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:533 */         return;
/* 268:    */       }
/* 269:    */     }
/* 270:537 */     this.features = cf;
/* 271:538 */     cf.setWritableCell(this);
/* 272:542 */     if (this.referenced) {
/* 273:544 */       addCellFeatures();
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public final void addCellFeatures()
/* 278:    */   {
/* 279:556 */     if (this.features == null) {
/* 280:558 */       return;
/* 281:    */     }
/* 282:561 */     if (this.copied == true)
/* 283:    */     {
/* 284:563 */       this.copied = false;
/* 285:    */       
/* 286:565 */       return;
/* 287:    */     }
/* 288:568 */     if (this.features.getComment() != null)
/* 289:    */     {
/* 290:570 */       Comment comment = new Comment(this.features.getComment(), this.column, this.row);
/* 291:    */       
/* 292:572 */       comment.setWidth(this.features.getCommentWidth());
/* 293:573 */       comment.setHeight(this.features.getCommentHeight());
/* 294:574 */       this.sheet.addDrawing(comment);
/* 295:575 */       this.sheet.getWorkbook().addDrawing(comment);
/* 296:576 */       this.features.setCommentDrawing(comment);
/* 297:    */     }
/* 298:579 */     if (this.features.hasDataValidation())
/* 299:    */     {
/* 300:    */       try
/* 301:    */       {
/* 302:583 */         this.features.getDVParser().setCell(this.column, this.row, this.sheet.getWorkbook(), this.sheet.getWorkbook(), this.sheet.getWorkbookSettings());
/* 303:    */       }
/* 304:    */       catch (FormulaException e)
/* 305:    */       {
/* 306:591 */         Assert.verify(false);
/* 307:    */       }
/* 308:594 */       this.sheet.addValidationCell(this);
/* 309:595 */       if (!this.features.hasDropDown()) {
/* 310:597 */         return;
/* 311:    */       }
/* 312:601 */       if (this.sheet.getComboBox() == null)
/* 313:    */       {
/* 314:606 */         ComboBox cb = new ComboBox();
/* 315:607 */         this.sheet.addDrawing(cb);
/* 316:608 */         this.sheet.getWorkbook().addDrawing(cb);
/* 317:609 */         this.sheet.setComboBox(cb);
/* 318:    */       }
/* 319:612 */       this.features.setComboBox(this.sheet.getComboBox());
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public final void removeCellFeatures()
/* 324:    */   {
/* 325:630 */     this.features = null;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public final void removeComment(Comment c)
/* 329:    */   {
/* 330:641 */     this.sheet.removeDrawing(c);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public final void removeDataValidation()
/* 334:    */   {
/* 335:649 */     this.sheet.removeDataValidation(this);
/* 336:    */   }
/* 337:    */   
/* 338:    */   final void setCopied(boolean c)
/* 339:    */   {
/* 340:661 */     this.copied = c;
/* 341:    */   }
/* 342:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.CellValue
 * JD-Core Version:    0.7.0.1
 */