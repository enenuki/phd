/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.TreeSet;
/*   7:    */ import jxl.BooleanCell;
/*   8:    */ import jxl.Cell;
/*   9:    */ import jxl.CellFeatures;
/*  10:    */ import jxl.CellType;
/*  11:    */ import jxl.DateCell;
/*  12:    */ import jxl.LabelCell;
/*  13:    */ import jxl.NumberCell;
/*  14:    */ import jxl.Range;
/*  15:    */ import jxl.WorkbookSettings;
/*  16:    */ import jxl.biff.CellReferenceHelper;
/*  17:    */ import jxl.biff.DataValidation;
/*  18:    */ import jxl.biff.FormattingRecords;
/*  19:    */ import jxl.biff.FormulaData;
/*  20:    */ import jxl.biff.NumFormatRecordsException;
/*  21:    */ import jxl.biff.SheetRangeImpl;
/*  22:    */ import jxl.biff.WorkspaceInformationRecord;
/*  23:    */ import jxl.biff.XFRecord;
/*  24:    */ import jxl.biff.drawing.Drawing;
/*  25:    */ import jxl.biff.formula.FormulaException;
/*  26:    */ import jxl.common.Assert;
/*  27:    */ import jxl.common.Logger;
/*  28:    */ import jxl.format.CellFormat;
/*  29:    */ import jxl.write.Blank;
/*  30:    */ import jxl.write.Boolean;
/*  31:    */ import jxl.write.DateTime;
/*  32:    */ import jxl.write.Formula;
/*  33:    */ import jxl.write.Label;
/*  34:    */ import jxl.write.Number;
/*  35:    */ import jxl.write.WritableCell;
/*  36:    */ import jxl.write.WritableCellFormat;
/*  37:    */ import jxl.write.WritableHyperlink;
/*  38:    */ import jxl.write.WritableImage;
/*  39:    */ import jxl.write.WritableSheet;
/*  40:    */ import jxl.write.WritableWorkbook;
/*  41:    */ import jxl.write.WriteException;
/*  42:    */ 
/*  43:    */ class WritableSheetCopier
/*  44:    */ {
/*  45: 87 */   private static Logger logger = Logger.getLogger(SheetCopier.class);
/*  46:    */   private WritableSheetImpl fromSheet;
/*  47:    */   private WritableSheetImpl toSheet;
/*  48:    */   private WorkbookSettings workbookSettings;
/*  49:    */   private TreeSet fromColumnFormats;
/*  50:    */   private TreeSet toColumnFormats;
/*  51:    */   private MergedCells fromMergedCells;
/*  52:    */   private MergedCells toMergedCells;
/*  53:    */   private RowRecord[] fromRows;
/*  54:    */   private ArrayList fromRowBreaks;
/*  55:    */   private ArrayList fromColumnBreaks;
/*  56:    */   private ArrayList toRowBreaks;
/*  57:    */   private ArrayList toColumnBreaks;
/*  58:    */   private DataValidation fromDataValidation;
/*  59:    */   private DataValidation toDataValidation;
/*  60:    */   private SheetWriter sheetWriter;
/*  61:    */   private ArrayList fromDrawings;
/*  62:    */   private ArrayList toDrawings;
/*  63:    */   private ArrayList toImages;
/*  64:    */   private WorkspaceInformationRecord fromWorkspaceOptions;
/*  65:    */   private PLSRecord fromPLSRecord;
/*  66:    */   private PLSRecord toPLSRecord;
/*  67:    */   private ButtonPropertySetRecord fromButtonPropertySet;
/*  68:    */   private ButtonPropertySetRecord toButtonPropertySet;
/*  69:    */   private ArrayList fromHyperlinks;
/*  70:    */   private ArrayList toHyperlinks;
/*  71:    */   private ArrayList validatedCells;
/*  72:    */   private int numRows;
/*  73:    */   private int maxRowOutlineLevel;
/*  74:    */   private int maxColumnOutlineLevel;
/*  75:    */   private boolean chartOnly;
/*  76:    */   private FormattingRecords formatRecords;
/*  77:    */   private HashMap xfRecords;
/*  78:    */   private HashMap fonts;
/*  79:    */   private HashMap formats;
/*  80:    */   
/*  81:    */   public WritableSheetCopier(WritableSheet f, WritableSheet t)
/*  82:    */   {
/*  83:134 */     this.fromSheet = ((WritableSheetImpl)f);
/*  84:135 */     this.toSheet = ((WritableSheetImpl)t);
/*  85:136 */     this.workbookSettings = this.toSheet.getWorkbook().getSettings();
/*  86:137 */     this.chartOnly = false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   void setColumnFormats(TreeSet fcf, TreeSet tcf)
/*  90:    */   {
/*  91:142 */     this.fromColumnFormats = fcf;
/*  92:143 */     this.toColumnFormats = tcf;
/*  93:    */   }
/*  94:    */   
/*  95:    */   void setMergedCells(MergedCells fmc, MergedCells tmc)
/*  96:    */   {
/*  97:148 */     this.fromMergedCells = fmc;
/*  98:149 */     this.toMergedCells = tmc;
/*  99:    */   }
/* 100:    */   
/* 101:    */   void setRows(RowRecord[] r)
/* 102:    */   {
/* 103:154 */     this.fromRows = r;
/* 104:    */   }
/* 105:    */   
/* 106:    */   void setValidatedCells(ArrayList vc)
/* 107:    */   {
/* 108:159 */     this.validatedCells = vc;
/* 109:    */   }
/* 110:    */   
/* 111:    */   void setRowBreaks(ArrayList frb, ArrayList trb)
/* 112:    */   {
/* 113:164 */     this.fromRowBreaks = frb;
/* 114:165 */     this.toRowBreaks = trb;
/* 115:    */   }
/* 116:    */   
/* 117:    */   void setColumnBreaks(ArrayList fcb, ArrayList tcb)
/* 118:    */   {
/* 119:170 */     this.fromColumnBreaks = fcb;
/* 120:171 */     this.toColumnBreaks = tcb;
/* 121:    */   }
/* 122:    */   
/* 123:    */   void setDrawings(ArrayList fd, ArrayList td, ArrayList ti)
/* 124:    */   {
/* 125:176 */     this.fromDrawings = fd;
/* 126:177 */     this.toDrawings = td;
/* 127:178 */     this.toImages = ti;
/* 128:    */   }
/* 129:    */   
/* 130:    */   void setHyperlinks(ArrayList fh, ArrayList th)
/* 131:    */   {
/* 132:183 */     this.fromHyperlinks = fh;
/* 133:184 */     this.toHyperlinks = th;
/* 134:    */   }
/* 135:    */   
/* 136:    */   void setWorkspaceOptions(WorkspaceInformationRecord wir)
/* 137:    */   {
/* 138:189 */     this.fromWorkspaceOptions = wir;
/* 139:    */   }
/* 140:    */   
/* 141:    */   void setDataValidation(DataValidation dv)
/* 142:    */   {
/* 143:194 */     this.fromDataValidation = dv;
/* 144:    */   }
/* 145:    */   
/* 146:    */   void setPLSRecord(PLSRecord plsr)
/* 147:    */   {
/* 148:199 */     this.fromPLSRecord = plsr;
/* 149:    */   }
/* 150:    */   
/* 151:    */   void setButtonPropertySetRecord(ButtonPropertySetRecord bpsr)
/* 152:    */   {
/* 153:204 */     this.fromButtonPropertySet = bpsr;
/* 154:    */   }
/* 155:    */   
/* 156:    */   void setSheetWriter(SheetWriter sw)
/* 157:    */   {
/* 158:209 */     this.sheetWriter = sw;
/* 159:    */   }
/* 160:    */   
/* 161:    */   DataValidation getDataValidation()
/* 162:    */   {
/* 163:215 */     return this.toDataValidation;
/* 164:    */   }
/* 165:    */   
/* 166:    */   PLSRecord getPLSRecord()
/* 167:    */   {
/* 168:220 */     return this.toPLSRecord;
/* 169:    */   }
/* 170:    */   
/* 171:    */   boolean isChartOnly()
/* 172:    */   {
/* 173:225 */     return this.chartOnly;
/* 174:    */   }
/* 175:    */   
/* 176:    */   ButtonPropertySetRecord getButtonPropertySet()
/* 177:    */   {
/* 178:230 */     return this.toButtonPropertySet;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void copySheet()
/* 182:    */   {
/* 183:239 */     shallowCopyCells();
/* 184:    */     
/* 185:    */ 
/* 186:242 */     Iterator cfit = this.fromColumnFormats.iterator();
/* 187:243 */     while (cfit.hasNext())
/* 188:    */     {
/* 189:245 */       ColumnInfoRecord cv = new ColumnInfoRecord((ColumnInfoRecord)cfit.next());
/* 190:    */       
/* 191:247 */       this.toColumnFormats.add(cv);
/* 192:    */     }
/* 193:251 */     Range[] merged = this.fromMergedCells.getMergedCells();
/* 194:253 */     for (int i = 0; i < merged.length; i++) {
/* 195:255 */       this.toMergedCells.add(new SheetRangeImpl((SheetRangeImpl)merged[i], this.toSheet));
/* 196:    */     }
/* 197:    */     try
/* 198:    */     {
/* 199:261 */       RowRecord row = null;
/* 200:262 */       RowRecord newRow = null;
/* 201:263 */       for (int i = 0; i < this.fromRows.length; i++)
/* 202:    */       {
/* 203:265 */         row = this.fromRows[i];
/* 204:267 */         if ((row != null) && ((!row.isDefaultHeight()) || (row.isCollapsed())))
/* 205:    */         {
/* 206:271 */           newRow = this.toSheet.getRowRecord(i);
/* 207:272 */           newRow.setRowDetails(row.getRowHeight(), row.matchesDefaultFontHeight(), row.isCollapsed(), row.getOutlineLevel(), row.getGroupStart(), row.getStyle());
/* 208:    */         }
/* 209:    */       }
/* 210:    */     }
/* 211:    */     catch (RowsExceededException e)
/* 212:    */     {
/* 213:285 */       Assert.verify(false);
/* 214:    */     }
/* 215:289 */     this.toRowBreaks = new ArrayList(this.fromRowBreaks);
/* 216:    */     
/* 217:    */ 
/* 218:292 */     this.toColumnBreaks = new ArrayList(this.fromColumnBreaks);
/* 219:295 */     if (this.fromDataValidation != null) {
/* 220:297 */       this.toDataValidation = new DataValidation(this.fromDataValidation, this.toSheet.getWorkbook(), this.toSheet.getWorkbook(), this.toSheet.getWorkbook().getSettings());
/* 221:    */     }
/* 222:305 */     this.sheetWriter.setCharts(this.fromSheet.getCharts());
/* 223:308 */     for (Iterator i = this.fromDrawings.iterator(); i.hasNext();)
/* 224:    */     {
/* 225:310 */       Object o = i.next();
/* 226:311 */       if ((o instanceof Drawing))
/* 227:    */       {
/* 228:313 */         WritableImage wi = new WritableImage((Drawing)o, this.toSheet.getWorkbook().getDrawingGroup());
/* 229:    */         
/* 230:    */ 
/* 231:316 */         this.toDrawings.add(wi);
/* 232:317 */         this.toImages.add(wi);
/* 233:    */       }
/* 234:    */     }
/* 235:325 */     this.sheetWriter.setWorkspaceOptions(this.fromWorkspaceOptions);
/* 236:328 */     if (this.fromPLSRecord != null) {
/* 237:330 */       this.toPLSRecord = new PLSRecord(this.fromPLSRecord);
/* 238:    */     }
/* 239:334 */     if (this.fromButtonPropertySet != null) {
/* 240:336 */       this.toButtonPropertySet = new ButtonPropertySetRecord(this.fromButtonPropertySet);
/* 241:    */     }
/* 242:340 */     for (Iterator i = this.fromHyperlinks.iterator(); i.hasNext();)
/* 243:    */     {
/* 244:342 */       WritableHyperlink hr = new WritableHyperlink((WritableHyperlink)i.next(), this.toSheet);
/* 245:    */       
/* 246:344 */       this.toHyperlinks.add(hr);
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   private WritableCell shallowCopyCell(Cell cell)
/* 251:    */   {
/* 252:353 */     CellType ct = cell.getType();
/* 253:354 */     WritableCell newCell = null;
/* 254:356 */     if (ct == CellType.LABEL) {
/* 255:358 */       newCell = new Label((LabelCell)cell);
/* 256:360 */     } else if (ct == CellType.NUMBER) {
/* 257:362 */       newCell = new Number((NumberCell)cell);
/* 258:364 */     } else if (ct == CellType.DATE) {
/* 259:366 */       newCell = new DateTime((DateCell)cell);
/* 260:368 */     } else if (ct == CellType.BOOLEAN) {
/* 261:370 */       newCell = new Boolean((BooleanCell)cell);
/* 262:372 */     } else if (ct == CellType.NUMBER_FORMULA) {
/* 263:374 */       newCell = new ReadNumberFormulaRecord((FormulaData)cell);
/* 264:376 */     } else if (ct == CellType.STRING_FORMULA) {
/* 265:378 */       newCell = new ReadStringFormulaRecord((FormulaData)cell);
/* 266:380 */     } else if (ct == CellType.BOOLEAN_FORMULA) {
/* 267:382 */       newCell = new ReadBooleanFormulaRecord((FormulaData)cell);
/* 268:384 */     } else if (ct == CellType.DATE_FORMULA) {
/* 269:386 */       newCell = new ReadDateFormulaRecord((FormulaData)cell);
/* 270:388 */     } else if (ct == CellType.FORMULA_ERROR) {
/* 271:390 */       newCell = new ReadErrorFormulaRecord((FormulaData)cell);
/* 272:392 */     } else if (ct == CellType.EMPTY) {
/* 273:394 */       if (cell.getCellFormat() != null) {
/* 274:399 */         newCell = new Blank(cell);
/* 275:    */       }
/* 276:    */     }
/* 277:403 */     return newCell;
/* 278:    */   }
/* 279:    */   
/* 280:    */   private WritableCell deepCopyCell(Cell cell)
/* 281:    */   {
/* 282:413 */     WritableCell c = shallowCopyCell(cell);
/* 283:415 */     if (c == null) {
/* 284:417 */       return c;
/* 285:    */     }
/* 286:420 */     if ((c instanceof ReadFormulaRecord))
/* 287:    */     {
/* 288:422 */       ReadFormulaRecord rfr = (ReadFormulaRecord)c;
/* 289:423 */       boolean crossSheetReference = !rfr.handleImportedCellReferences(this.fromSheet.getWorkbook(), this.fromSheet.getWorkbook(), this.workbookSettings);
/* 290:428 */       if (crossSheetReference)
/* 291:    */       {
/* 292:    */         try
/* 293:    */         {
/* 294:432 */           logger.warn("Formula " + rfr.getFormula() + " in cell " + CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow()) + " cannot be imported because it references another " + " sheet from the source workbook");
/* 295:    */         }
/* 296:    */         catch (FormulaException e)
/* 297:    */         {
/* 298:441 */           logger.warn("Formula  in cell " + CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow()) + " cannot be imported:  " + e.getMessage());
/* 299:    */         }
/* 300:448 */         c = new Formula(cell.getColumn(), cell.getRow(), "\"ERROR\"");
/* 301:    */       }
/* 302:    */     }
/* 303:453 */     CellFormat cf = c.getCellFormat();
/* 304:454 */     int index = ((XFRecord)cf).getXFIndex();
/* 305:455 */     WritableCellFormat wcf = (WritableCellFormat)this.xfRecords.get(new Integer(index));
/* 306:458 */     if (wcf == null) {
/* 307:460 */       wcf = copyCellFormat(cf);
/* 308:    */     }
/* 309:463 */     c.setCellFormat(wcf);
/* 310:    */     
/* 311:465 */     return c;
/* 312:    */   }
/* 313:    */   
/* 314:    */   void shallowCopyCells()
/* 315:    */   {
/* 316:474 */     int cells = this.fromSheet.getRows();
/* 317:475 */     Cell[] row = null;
/* 318:476 */     Cell cell = null;
/* 319:477 */     for (int i = 0; i < cells; i++)
/* 320:    */     {
/* 321:479 */       row = this.fromSheet.getRow(i);
/* 322:481 */       for (int j = 0; j < row.length; j++)
/* 323:    */       {
/* 324:483 */         cell = row[j];
/* 325:484 */         WritableCell c = shallowCopyCell(cell);
/* 326:    */         try
/* 327:    */         {
/* 328:493 */           if (c != null)
/* 329:    */           {
/* 330:495 */             this.toSheet.addCell(c);
/* 331:499 */             if ((c.getCellFeatures() != null & c.getCellFeatures().hasDataValidation())) {
/* 332:502 */               this.validatedCells.add(c);
/* 333:    */             }
/* 334:    */           }
/* 335:    */         }
/* 336:    */         catch (WriteException e)
/* 337:    */         {
/* 338:508 */           Assert.verify(false);
/* 339:    */         }
/* 340:    */       }
/* 341:    */     }
/* 342:512 */     this.numRows = this.toSheet.getRows();
/* 343:    */   }
/* 344:    */   
/* 345:    */   void deepCopyCells()
/* 346:    */   {
/* 347:521 */     int cells = this.fromSheet.getRows();
/* 348:522 */     Cell[] row = null;
/* 349:523 */     Cell cell = null;
/* 350:524 */     for (int i = 0; i < cells; i++)
/* 351:    */     {
/* 352:526 */       row = this.fromSheet.getRow(i);
/* 353:528 */       for (int j = 0; j < row.length; j++)
/* 354:    */       {
/* 355:530 */         cell = row[j];
/* 356:531 */         WritableCell c = deepCopyCell(cell);
/* 357:    */         try
/* 358:    */         {
/* 359:540 */           if (c != null)
/* 360:    */           {
/* 361:542 */             this.toSheet.addCell(c);
/* 362:546 */             if ((c.getCellFeatures() != null & c.getCellFeatures().hasDataValidation())) {
/* 363:549 */               this.validatedCells.add(c);
/* 364:    */             }
/* 365:    */           }
/* 366:    */         }
/* 367:    */         catch (WriteException e)
/* 368:    */         {
/* 369:555 */           Assert.verify(false);
/* 370:    */         }
/* 371:    */       }
/* 372:    */     }
/* 373:    */   }
/* 374:    */   
/* 375:    */   private WritableCellFormat copyCellFormat(CellFormat cf)
/* 376:    */   {
/* 377:    */     try
/* 378:    */     {
/* 379:574 */       XFRecord xfr = (XFRecord)cf;
/* 380:575 */       WritableCellFormat f = new WritableCellFormat(xfr);
/* 381:576 */       this.formatRecords.addStyle(f);
/* 382:    */       
/* 383:    */ 
/* 384:579 */       int xfIndex = xfr.getXFIndex();
/* 385:580 */       this.xfRecords.put(new Integer(xfIndex), f);
/* 386:    */       
/* 387:582 */       int fontIndex = xfr.getFontIndex();
/* 388:583 */       this.fonts.put(new Integer(fontIndex), new Integer(f.getFontIndex()));
/* 389:    */       
/* 390:585 */       int formatIndex = xfr.getFormatRecord();
/* 391:586 */       this.formats.put(new Integer(formatIndex), new Integer(f.getFormatRecord()));
/* 392:    */       
/* 393:588 */       return f;
/* 394:    */     }
/* 395:    */     catch (NumFormatRecordsException e)
/* 396:    */     {
/* 397:592 */       logger.warn("Maximum number of format records exceeded.  Using default format.");
/* 398:    */     }
/* 399:595 */     return WritableWorkbook.NORMAL_STYLE;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public int getMaxColumnOutlineLevel()
/* 403:    */   {
/* 404:607 */     return this.maxColumnOutlineLevel;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public int getMaxRowOutlineLevel()
/* 408:    */   {
/* 409:617 */     return this.maxRowOutlineLevel;
/* 410:    */   }
/* 411:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.WritableSheetCopier
 * JD-Core Version:    0.7.0.1
 */