/*    1:     */ package jxl.write.biff;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Arrays;
/*    5:     */ import java.util.HashMap;
/*    6:     */ import java.util.TreeSet;
/*    7:     */ import jxl.BooleanCell;
/*    8:     */ import jxl.Cell;
/*    9:     */ import jxl.CellFeatures;
/*   10:     */ import jxl.CellType;
/*   11:     */ import jxl.CellView;
/*   12:     */ import jxl.DateCell;
/*   13:     */ import jxl.Hyperlink;
/*   14:     */ import jxl.LabelCell;
/*   15:     */ import jxl.NumberCell;
/*   16:     */ import jxl.Range;
/*   17:     */ import jxl.Sheet;
/*   18:     */ import jxl.WorkbookSettings;
/*   19:     */ import jxl.biff.AutoFilter;
/*   20:     */ import jxl.biff.CellReferenceHelper;
/*   21:     */ import jxl.biff.ConditionalFormat;
/*   22:     */ import jxl.biff.DataValidation;
/*   23:     */ import jxl.biff.FormattingRecords;
/*   24:     */ import jxl.biff.FormulaData;
/*   25:     */ import jxl.biff.NumFormatRecordsException;
/*   26:     */ import jxl.biff.SheetRangeImpl;
/*   27:     */ import jxl.biff.XFRecord;
/*   28:     */ import jxl.biff.drawing.Button;
/*   29:     */ import jxl.biff.drawing.Chart;
/*   30:     */ import jxl.biff.drawing.CheckBox;
/*   31:     */ import jxl.biff.drawing.ComboBox;
/*   32:     */ import jxl.biff.drawing.Comment;
/*   33:     */ import jxl.biff.drawing.Drawing;
/*   34:     */ import jxl.biff.drawing.DrawingGroupObject;
/*   35:     */ import jxl.biff.formula.FormulaException;
/*   36:     */ import jxl.common.Assert;
/*   37:     */ import jxl.common.Logger;
/*   38:     */ import jxl.format.CellFormat;
/*   39:     */ import jxl.read.biff.BOFRecord;
/*   40:     */ import jxl.read.biff.NameRecord;
/*   41:     */ import jxl.read.biff.NameRecord.NameRange;
/*   42:     */ import jxl.read.biff.SheetImpl;
/*   43:     */ import jxl.read.biff.WorkbookParser;
/*   44:     */ import jxl.write.Blank;
/*   45:     */ import jxl.write.Boolean;
/*   46:     */ import jxl.write.DateTime;
/*   47:     */ import jxl.write.Formula;
/*   48:     */ import jxl.write.Label;
/*   49:     */ import jxl.write.Number;
/*   50:     */ import jxl.write.WritableCell;
/*   51:     */ import jxl.write.WritableCellFeatures;
/*   52:     */ import jxl.write.WritableCellFormat;
/*   53:     */ import jxl.write.WritableHyperlink;
/*   54:     */ import jxl.write.WritableImage;
/*   55:     */ import jxl.write.WritableSheet;
/*   56:     */ import jxl.write.WritableWorkbook;
/*   57:     */ import jxl.write.WriteException;
/*   58:     */ 
/*   59:     */ class SheetCopier
/*   60:     */ {
/*   61:  87 */   private static Logger logger = Logger.getLogger(SheetCopier.class);
/*   62:     */   private SheetImpl fromSheet;
/*   63:     */   private WritableSheetImpl toSheet;
/*   64:     */   private WorkbookSettings workbookSettings;
/*   65:     */   private TreeSet columnFormats;
/*   66:     */   private FormattingRecords formatRecords;
/*   67:     */   private ArrayList hyperlinks;
/*   68:     */   private MergedCells mergedCells;
/*   69:     */   private ArrayList rowBreaks;
/*   70:     */   private ArrayList columnBreaks;
/*   71:     */   private SheetWriter sheetWriter;
/*   72:     */   private ArrayList drawings;
/*   73:     */   private ArrayList images;
/*   74:     */   private ArrayList conditionalFormats;
/*   75:     */   private ArrayList validatedCells;
/*   76:     */   private AutoFilter autoFilter;
/*   77:     */   private DataValidation dataValidation;
/*   78:     */   private ComboBox comboBox;
/*   79:     */   private PLSRecord plsRecord;
/*   80:     */   private boolean chartOnly;
/*   81:     */   private ButtonPropertySetRecord buttonPropertySet;
/*   82:     */   private int numRows;
/*   83:     */   private int maxRowOutlineLevel;
/*   84:     */   private int maxColumnOutlineLevel;
/*   85:     */   private HashMap xfRecords;
/*   86:     */   private HashMap fonts;
/*   87:     */   private HashMap formats;
/*   88:     */   
/*   89:     */   public SheetCopier(Sheet f, WritableSheet t)
/*   90:     */   {
/*   91: 122 */     this.fromSheet = ((SheetImpl)f);
/*   92: 123 */     this.toSheet = ((WritableSheetImpl)t);
/*   93: 124 */     this.workbookSettings = this.toSheet.getWorkbook().getSettings();
/*   94: 125 */     this.chartOnly = false;
/*   95:     */   }
/*   96:     */   
/*   97:     */   void setColumnFormats(TreeSet cf)
/*   98:     */   {
/*   99: 130 */     this.columnFormats = cf;
/*  100:     */   }
/*  101:     */   
/*  102:     */   void setFormatRecords(FormattingRecords fr)
/*  103:     */   {
/*  104: 135 */     this.formatRecords = fr;
/*  105:     */   }
/*  106:     */   
/*  107:     */   void setHyperlinks(ArrayList h)
/*  108:     */   {
/*  109: 140 */     this.hyperlinks = h;
/*  110:     */   }
/*  111:     */   
/*  112:     */   void setMergedCells(MergedCells mc)
/*  113:     */   {
/*  114: 145 */     this.mergedCells = mc;
/*  115:     */   }
/*  116:     */   
/*  117:     */   void setRowBreaks(ArrayList rb)
/*  118:     */   {
/*  119: 150 */     this.rowBreaks = rb;
/*  120:     */   }
/*  121:     */   
/*  122:     */   void setColumnBreaks(ArrayList cb)
/*  123:     */   {
/*  124: 155 */     this.columnBreaks = cb;
/*  125:     */   }
/*  126:     */   
/*  127:     */   void setSheetWriter(SheetWriter sw)
/*  128:     */   {
/*  129: 160 */     this.sheetWriter = sw;
/*  130:     */   }
/*  131:     */   
/*  132:     */   void setDrawings(ArrayList d)
/*  133:     */   {
/*  134: 165 */     this.drawings = d;
/*  135:     */   }
/*  136:     */   
/*  137:     */   void setImages(ArrayList i)
/*  138:     */   {
/*  139: 170 */     this.images = i;
/*  140:     */   }
/*  141:     */   
/*  142:     */   void setConditionalFormats(ArrayList cf)
/*  143:     */   {
/*  144: 175 */     this.conditionalFormats = cf;
/*  145:     */   }
/*  146:     */   
/*  147:     */   void setValidatedCells(ArrayList vc)
/*  148:     */   {
/*  149: 180 */     this.validatedCells = vc;
/*  150:     */   }
/*  151:     */   
/*  152:     */   AutoFilter getAutoFilter()
/*  153:     */   {
/*  154: 185 */     return this.autoFilter;
/*  155:     */   }
/*  156:     */   
/*  157:     */   DataValidation getDataValidation()
/*  158:     */   {
/*  159: 190 */     return this.dataValidation;
/*  160:     */   }
/*  161:     */   
/*  162:     */   ComboBox getComboBox()
/*  163:     */   {
/*  164: 195 */     return this.comboBox;
/*  165:     */   }
/*  166:     */   
/*  167:     */   PLSRecord getPLSRecord()
/*  168:     */   {
/*  169: 200 */     return this.plsRecord;
/*  170:     */   }
/*  171:     */   
/*  172:     */   boolean isChartOnly()
/*  173:     */   {
/*  174: 205 */     return this.chartOnly;
/*  175:     */   }
/*  176:     */   
/*  177:     */   ButtonPropertySetRecord getButtonPropertySet()
/*  178:     */   {
/*  179: 210 */     return this.buttonPropertySet;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void copySheet()
/*  183:     */   {
/*  184: 219 */     shallowCopyCells();
/*  185:     */     
/*  186:     */ 
/*  187: 222 */     jxl.read.biff.ColumnInfoRecord[] readCirs = this.fromSheet.getColumnInfos();
/*  188: 224 */     for (int i = 0; i < readCirs.length; i++)
/*  189:     */     {
/*  190: 226 */       jxl.read.biff.ColumnInfoRecord rcir = readCirs[i];
/*  191: 227 */       for (int j = rcir.getStartColumn(); j <= rcir.getEndColumn(); j++)
/*  192:     */       {
/*  193: 229 */         ColumnInfoRecord cir = new ColumnInfoRecord(rcir, j, this.formatRecords);
/*  194:     */         
/*  195: 231 */         cir.setHidden(rcir.getHidden());
/*  196: 232 */         this.columnFormats.add(cir);
/*  197:     */       }
/*  198:     */     }
/*  199: 237 */     Hyperlink[] hls = this.fromSheet.getHyperlinks();
/*  200: 238 */     for (int i = 0; i < hls.length; i++)
/*  201:     */     {
/*  202: 240 */       WritableHyperlink hr = new WritableHyperlink(hls[i], this.toSheet);
/*  203:     */       
/*  204: 242 */       this.hyperlinks.add(hr);
/*  205:     */     }
/*  206: 246 */     Range[] merged = this.fromSheet.getMergedCells();
/*  207: 248 */     for (int i = 0; i < merged.length; i++) {
/*  208: 250 */       this.mergedCells.add(new SheetRangeImpl((SheetRangeImpl)merged[i], this.toSheet));
/*  209:     */     }
/*  210:     */     try
/*  211:     */     {
/*  212: 256 */       jxl.read.biff.RowRecord[] rowprops = this.fromSheet.getRowProperties();
/*  213: 258 */       for (int i = 0; i < rowprops.length; i++)
/*  214:     */       {
/*  215: 260 */         RowRecord rr = this.toSheet.getRowRecord(rowprops[i].getRowNumber());
/*  216: 261 */         XFRecord format = rowprops[i].hasDefaultFormat() ? this.formatRecords.getXFRecord(rowprops[i].getXFIndex()) : null;
/*  217:     */         
/*  218: 263 */         rr.setRowDetails(rowprops[i].getRowHeight(), rowprops[i].matchesDefaultFontHeight(), rowprops[i].isCollapsed(), rowprops[i].getOutlineLevel(), rowprops[i].getGroupStart(), format);
/*  219:     */         
/*  220:     */ 
/*  221:     */ 
/*  222:     */ 
/*  223:     */ 
/*  224: 269 */         this.numRows = Math.max(this.numRows, rowprops[i].getRowNumber() + 1);
/*  225:     */       }
/*  226:     */     }
/*  227:     */     catch (RowsExceededException e)
/*  228:     */     {
/*  229: 276 */       Assert.verify(false);
/*  230:     */     }
/*  231: 284 */     int[] rowbreaks = this.fromSheet.getRowPageBreaks();
/*  232: 286 */     if (rowbreaks != null) {
/*  233: 288 */       for (int i = 0; i < rowbreaks.length; i++) {
/*  234: 290 */         this.rowBreaks.add(new Integer(rowbreaks[i]));
/*  235:     */       }
/*  236:     */     }
/*  237: 294 */     int[] columnbreaks = this.fromSheet.getColumnPageBreaks();
/*  238: 296 */     if (columnbreaks != null) {
/*  239: 298 */       for (int i = 0; i < columnbreaks.length; i++) {
/*  240: 300 */         this.columnBreaks.add(new Integer(columnbreaks[i]));
/*  241:     */       }
/*  242:     */     }
/*  243: 305 */     this.sheetWriter.setCharts(this.fromSheet.getCharts());
/*  244:     */     
/*  245:     */ 
/*  246: 308 */     DrawingGroupObject[] dr = this.fromSheet.getDrawings();
/*  247: 309 */     for (int i = 0; i < dr.length; i++) {
/*  248: 311 */       if ((dr[i] instanceof Drawing))
/*  249:     */       {
/*  250: 313 */         WritableImage wi = new WritableImage(dr[i], this.toSheet.getWorkbook().getDrawingGroup());
/*  251:     */         
/*  252: 315 */         this.drawings.add(wi);
/*  253: 316 */         this.images.add(wi);
/*  254:     */       }
/*  255: 318 */       else if ((dr[i] instanceof Comment))
/*  256:     */       {
/*  257: 320 */         Comment c = new Comment(dr[i], this.toSheet.getWorkbook().getDrawingGroup(), this.workbookSettings);
/*  258:     */         
/*  259:     */ 
/*  260:     */ 
/*  261: 324 */         this.drawings.add(c);
/*  262:     */         
/*  263:     */ 
/*  264: 327 */         CellValue cv = (CellValue)this.toSheet.getWritableCell(c.getColumn(), c.getRow());
/*  265:     */         
/*  266: 329 */         Assert.verify(cv.getCellFeatures() != null);
/*  267: 330 */         cv.getWritableCellFeatures().setCommentDrawing(c);
/*  268:     */       }
/*  269: 332 */       else if ((dr[i] instanceof Button))
/*  270:     */       {
/*  271: 334 */         Button b = new Button(dr[i], this.toSheet.getWorkbook().getDrawingGroup(), this.workbookSettings);
/*  272:     */         
/*  273:     */ 
/*  274:     */ 
/*  275:     */ 
/*  276: 339 */         this.drawings.add(b);
/*  277:     */       }
/*  278: 341 */       else if ((dr[i] instanceof ComboBox))
/*  279:     */       {
/*  280: 343 */         ComboBox cb = new ComboBox(dr[i], this.toSheet.getWorkbook().getDrawingGroup(), this.workbookSettings);
/*  281:     */         
/*  282:     */ 
/*  283:     */ 
/*  284:     */ 
/*  285: 348 */         this.drawings.add(cb);
/*  286:     */       }
/*  287: 350 */       else if ((dr[i] instanceof CheckBox))
/*  288:     */       {
/*  289: 352 */         CheckBox cb = new CheckBox(dr[i], this.toSheet.getWorkbook().getDrawingGroup(), this.workbookSettings);
/*  290:     */         
/*  291:     */ 
/*  292:     */ 
/*  293:     */ 
/*  294: 357 */         this.drawings.add(cb);
/*  295:     */       }
/*  296:     */     }
/*  297: 363 */     DataValidation rdv = this.fromSheet.getDataValidation();
/*  298: 364 */     if (rdv != null)
/*  299:     */     {
/*  300: 366 */       this.dataValidation = new DataValidation(rdv, this.toSheet.getWorkbook(), this.toSheet.getWorkbook(), this.workbookSettings);
/*  301:     */       
/*  302:     */ 
/*  303:     */ 
/*  304: 370 */       int objid = this.dataValidation.getComboBoxObjectId();
/*  305: 372 */       if (objid != 0) {
/*  306: 374 */         this.comboBox = ((ComboBox)this.drawings.get(objid));
/*  307:     */       }
/*  308:     */     }
/*  309: 379 */     ConditionalFormat[] cf = this.fromSheet.getConditionalFormats();
/*  310: 380 */     if (cf.length > 0) {
/*  311: 382 */       for (int i = 0; i < cf.length; i++) {
/*  312: 384 */         this.conditionalFormats.add(cf[i]);
/*  313:     */       }
/*  314:     */     }
/*  315: 389 */     this.autoFilter = this.fromSheet.getAutoFilter();
/*  316:     */     
/*  317:     */ 
/*  318: 392 */     this.sheetWriter.setWorkspaceOptions(this.fromSheet.getWorkspaceOptions());
/*  319: 395 */     if (this.fromSheet.getSheetBof().isChart())
/*  320:     */     {
/*  321: 397 */       this.chartOnly = true;
/*  322: 398 */       this.sheetWriter.setChartOnly();
/*  323:     */     }
/*  324: 402 */     if (this.fromSheet.getPLS() != null) {
/*  325: 404 */       if (this.fromSheet.getWorkbookBof().isBiff7()) {
/*  326: 406 */         logger.warn("Cannot copy Biff7 print settings record - ignoring");
/*  327:     */       } else {
/*  328: 410 */         this.plsRecord = new PLSRecord(this.fromSheet.getPLS());
/*  329:     */       }
/*  330:     */     }
/*  331: 415 */     if (this.fromSheet.getButtonPropertySet() != null) {
/*  332: 417 */       this.buttonPropertySet = new ButtonPropertySetRecord(this.fromSheet.getButtonPropertySet());
/*  333:     */     }
/*  334: 422 */     this.maxRowOutlineLevel = this.fromSheet.getMaxRowOutlineLevel();
/*  335: 423 */     this.maxColumnOutlineLevel = this.fromSheet.getMaxColumnOutlineLevel();
/*  336:     */   }
/*  337:     */   
/*  338:     */   public void copyWritableSheet()
/*  339:     */   {
/*  340: 432 */     shallowCopyCells();
/*  341:     */   }
/*  342:     */   
/*  343:     */   public void importSheet()
/*  344:     */   {
/*  345: 538 */     this.xfRecords = new HashMap();
/*  346: 539 */     this.fonts = new HashMap();
/*  347: 540 */     this.formats = new HashMap();
/*  348:     */     
/*  349: 542 */     deepCopyCells();
/*  350:     */     
/*  351:     */ 
/*  352: 545 */     jxl.read.biff.ColumnInfoRecord[] readCirs = this.fromSheet.getColumnInfos();
/*  353: 547 */     for (int i = 0; i < readCirs.length; i++)
/*  354:     */     {
/*  355: 549 */       jxl.read.biff.ColumnInfoRecord rcir = readCirs[i];
/*  356: 550 */       for (int j = rcir.getStartColumn(); j <= rcir.getEndColumn(); j++)
/*  357:     */       {
/*  358: 552 */         ColumnInfoRecord cir = new ColumnInfoRecord(rcir, j);
/*  359: 553 */         int xfIndex = cir.getXfIndex();
/*  360: 554 */         XFRecord cf = (WritableCellFormat)this.xfRecords.get(new Integer(xfIndex));
/*  361:     */         WritableCellFormat wcf;
/*  362: 556 */         if (cf == null)
/*  363:     */         {
/*  364: 558 */           CellFormat readFormat = this.fromSheet.getColumnView(j).getFormat();
/*  365: 559 */           wcf = copyCellFormat(readFormat);
/*  366:     */         }
/*  367: 562 */         cir.setCellFormat(cf);
/*  368: 563 */         cir.setHidden(rcir.getHidden());
/*  369: 564 */         this.columnFormats.add(cir);
/*  370:     */       }
/*  371:     */     }
/*  372: 569 */     Hyperlink[] hls = this.fromSheet.getHyperlinks();
/*  373: 570 */     for (int i = 0; i < hls.length; i++)
/*  374:     */     {
/*  375: 572 */       WritableHyperlink hr = new WritableHyperlink(hls[i], this.toSheet);
/*  376:     */       
/*  377: 574 */       this.hyperlinks.add(hr);
/*  378:     */     }
/*  379: 578 */     Range[] merged = this.fromSheet.getMergedCells();
/*  380: 580 */     for (int i = 0; i < merged.length; i++) {
/*  381: 582 */       this.mergedCells.add(new SheetRangeImpl((SheetRangeImpl)merged[i], this.toSheet));
/*  382:     */     }
/*  383:     */     try
/*  384:     */     {
/*  385: 588 */       jxl.read.biff.RowRecord[] rowprops = this.fromSheet.getRowProperties();
/*  386: 590 */       for (int i = 0; i < rowprops.length; i++)
/*  387:     */       {
/*  388: 592 */         RowRecord rr = this.toSheet.getRowRecord(rowprops[i].getRowNumber());
/*  389: 593 */         XFRecord format = null;
/*  390: 594 */         jxl.read.biff.RowRecord rowrec = rowprops[i];
/*  391:     */         WritableCellFormat wcf;
/*  392: 595 */         if (rowrec.hasDefaultFormat())
/*  393:     */         {
/*  394: 597 */           format = (WritableCellFormat)this.xfRecords.get(new Integer(rowrec.getXFIndex()));
/*  395: 600 */           if (format == null)
/*  396:     */           {
/*  397: 602 */             int rownum = rowrec.getRowNumber();
/*  398: 603 */             CellFormat readFormat = this.fromSheet.getRowView(rownum).getFormat();
/*  399: 604 */             wcf = copyCellFormat(readFormat);
/*  400:     */           }
/*  401:     */         }
/*  402: 608 */         rr.setRowDetails(rowrec.getRowHeight(), rowrec.matchesDefaultFontHeight(), rowrec.isCollapsed(), rowrec.getOutlineLevel(), rowrec.getGroupStart(), format);
/*  403:     */         
/*  404:     */ 
/*  405:     */ 
/*  406:     */ 
/*  407:     */ 
/*  408: 614 */         this.numRows = Math.max(this.numRows, rowprops[i].getRowNumber() + 1);
/*  409:     */       }
/*  410:     */     }
/*  411:     */     catch (RowsExceededException e)
/*  412:     */     {
/*  413: 621 */       Assert.verify(false);
/*  414:     */     }
/*  415: 629 */     int[] rowbreaks = this.fromSheet.getRowPageBreaks();
/*  416: 631 */     if (rowbreaks != null) {
/*  417: 633 */       for (int i = 0; i < rowbreaks.length; i++) {
/*  418: 635 */         this.rowBreaks.add(new Integer(rowbreaks[i]));
/*  419:     */       }
/*  420:     */     }
/*  421: 639 */     int[] columnbreaks = this.fromSheet.getColumnPageBreaks();
/*  422: 641 */     if (columnbreaks != null) {
/*  423: 643 */       for (int i = 0; i < columnbreaks.length; i++) {
/*  424: 645 */         this.columnBreaks.add(new Integer(columnbreaks[i]));
/*  425:     */       }
/*  426:     */     }
/*  427: 650 */     Chart[] fromCharts = this.fromSheet.getCharts();
/*  428: 651 */     if ((fromCharts != null) && (fromCharts.length > 0)) {
/*  429: 653 */       logger.warn("Importing of charts is not supported");
/*  430:     */     }
/*  431: 690 */     DrawingGroupObject[] dr = this.fromSheet.getDrawings();
/*  432: 694 */     if ((dr.length > 0) && (this.toSheet.getWorkbook().getDrawingGroup() == null)) {
/*  433: 697 */       this.toSheet.getWorkbook().createDrawingGroup();
/*  434:     */     }
/*  435: 700 */     for (int i = 0; i < dr.length; i++) {
/*  436: 702 */       if ((dr[i] instanceof Drawing))
/*  437:     */       {
/*  438: 704 */         WritableImage wi = new WritableImage(dr[i].getX(), dr[i].getY(), dr[i].getWidth(), dr[i].getHeight(), dr[i].getImageData());
/*  439:     */         
/*  440:     */ 
/*  441:     */ 
/*  442: 708 */         this.toSheet.getWorkbook().addDrawing(wi);
/*  443: 709 */         this.drawings.add(wi);
/*  444: 710 */         this.images.add(wi);
/*  445:     */       }
/*  446: 712 */       else if ((dr[i] instanceof Comment))
/*  447:     */       {
/*  448: 714 */         Comment c = new Comment(dr[i], this.toSheet.getWorkbook().getDrawingGroup(), this.workbookSettings);
/*  449:     */         
/*  450:     */ 
/*  451:     */ 
/*  452: 718 */         this.drawings.add(c);
/*  453:     */         
/*  454:     */ 
/*  455: 721 */         CellValue cv = (CellValue)this.toSheet.getWritableCell(c.getColumn(), c.getRow());
/*  456:     */         
/*  457: 723 */         Assert.verify(cv.getCellFeatures() != null);
/*  458: 724 */         cv.getWritableCellFeatures().setCommentDrawing(c);
/*  459:     */       }
/*  460: 726 */       else if ((dr[i] instanceof Button))
/*  461:     */       {
/*  462: 728 */         Button b = new Button(dr[i], this.toSheet.getWorkbook().getDrawingGroup(), this.workbookSettings);
/*  463:     */         
/*  464:     */ 
/*  465:     */ 
/*  466:     */ 
/*  467: 733 */         this.drawings.add(b);
/*  468:     */       }
/*  469: 735 */       else if ((dr[i] instanceof ComboBox))
/*  470:     */       {
/*  471: 737 */         ComboBox cb = new ComboBox(dr[i], this.toSheet.getWorkbook().getDrawingGroup(), this.workbookSettings);
/*  472:     */         
/*  473:     */ 
/*  474:     */ 
/*  475:     */ 
/*  476: 742 */         this.drawings.add(cb);
/*  477:     */       }
/*  478:     */     }
/*  479: 747 */     DataValidation rdv = this.fromSheet.getDataValidation();
/*  480: 748 */     if (rdv != null)
/*  481:     */     {
/*  482: 750 */       this.dataValidation = new DataValidation(rdv, this.toSheet.getWorkbook(), this.toSheet.getWorkbook(), this.workbookSettings);
/*  483:     */       
/*  484:     */ 
/*  485:     */ 
/*  486: 754 */       int objid = this.dataValidation.getComboBoxObjectId();
/*  487: 755 */       if (objid != 0) {
/*  488: 757 */         this.comboBox = ((ComboBox)this.drawings.get(objid));
/*  489:     */       }
/*  490:     */     }
/*  491: 762 */     this.sheetWriter.setWorkspaceOptions(this.fromSheet.getWorkspaceOptions());
/*  492: 765 */     if (this.fromSheet.getSheetBof().isChart())
/*  493:     */     {
/*  494: 767 */       this.chartOnly = true;
/*  495: 768 */       this.sheetWriter.setChartOnly();
/*  496:     */     }
/*  497: 772 */     if (this.fromSheet.getPLS() != null) {
/*  498: 774 */       if (this.fromSheet.getWorkbookBof().isBiff7()) {
/*  499: 776 */         logger.warn("Cannot copy Biff7 print settings record - ignoring");
/*  500:     */       } else {
/*  501: 780 */         this.plsRecord = new PLSRecord(this.fromSheet.getPLS());
/*  502:     */       }
/*  503:     */     }
/*  504: 785 */     if (this.fromSheet.getButtonPropertySet() != null) {
/*  505: 787 */       this.buttonPropertySet = new ButtonPropertySetRecord(this.fromSheet.getButtonPropertySet());
/*  506:     */     }
/*  507: 791 */     importNames();
/*  508:     */     
/*  509:     */ 
/*  510: 794 */     this.maxRowOutlineLevel = this.fromSheet.getMaxRowOutlineLevel();
/*  511: 795 */     this.maxColumnOutlineLevel = this.fromSheet.getMaxColumnOutlineLevel();
/*  512:     */   }
/*  513:     */   
/*  514:     */   private WritableCell shallowCopyCell(Cell cell)
/*  515:     */   {
/*  516: 803 */     CellType ct = cell.getType();
/*  517: 804 */     WritableCell newCell = null;
/*  518: 806 */     if (ct == CellType.LABEL) {
/*  519: 808 */       newCell = new Label((LabelCell)cell);
/*  520: 810 */     } else if (ct == CellType.NUMBER) {
/*  521: 812 */       newCell = new Number((NumberCell)cell);
/*  522: 814 */     } else if (ct == CellType.DATE) {
/*  523: 816 */       newCell = new DateTime((DateCell)cell);
/*  524: 818 */     } else if (ct == CellType.BOOLEAN) {
/*  525: 820 */       newCell = new Boolean((BooleanCell)cell);
/*  526: 822 */     } else if (ct == CellType.NUMBER_FORMULA) {
/*  527: 824 */       newCell = new ReadNumberFormulaRecord((FormulaData)cell);
/*  528: 826 */     } else if (ct == CellType.STRING_FORMULA) {
/*  529: 828 */       newCell = new ReadStringFormulaRecord((FormulaData)cell);
/*  530: 830 */     } else if (ct == CellType.BOOLEAN_FORMULA) {
/*  531: 832 */       newCell = new ReadBooleanFormulaRecord((FormulaData)cell);
/*  532: 834 */     } else if (ct == CellType.DATE_FORMULA) {
/*  533: 836 */       newCell = new ReadDateFormulaRecord((FormulaData)cell);
/*  534: 838 */     } else if (ct == CellType.FORMULA_ERROR) {
/*  535: 840 */       newCell = new ReadErrorFormulaRecord((FormulaData)cell);
/*  536: 842 */     } else if (ct == CellType.EMPTY) {
/*  537: 844 */       if (cell.getCellFormat() != null) {
/*  538: 849 */         newCell = new Blank(cell);
/*  539:     */       }
/*  540:     */     }
/*  541: 853 */     return newCell;
/*  542:     */   }
/*  543:     */   
/*  544:     */   private WritableCell deepCopyCell(Cell cell)
/*  545:     */   {
/*  546: 863 */     WritableCell c = shallowCopyCell(cell);
/*  547: 865 */     if (c == null) {
/*  548: 867 */       return c;
/*  549:     */     }
/*  550: 870 */     if ((c instanceof ReadFormulaRecord))
/*  551:     */     {
/*  552: 872 */       ReadFormulaRecord rfr = (ReadFormulaRecord)c;
/*  553: 873 */       boolean crossSheetReference = !rfr.handleImportedCellReferences(this.fromSheet.getWorkbook(), this.fromSheet.getWorkbook(), this.workbookSettings);
/*  554: 878 */       if (crossSheetReference)
/*  555:     */       {
/*  556:     */         try
/*  557:     */         {
/*  558: 882 */           logger.warn("Formula " + rfr.getFormula() + " in cell " + CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow()) + " cannot be imported because it references another " + " sheet from the source workbook");
/*  559:     */         }
/*  560:     */         catch (FormulaException e)
/*  561:     */         {
/*  562: 891 */           logger.warn("Formula  in cell " + CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow()) + " cannot be imported:  " + e.getMessage());
/*  563:     */         }
/*  564: 898 */         c = new Formula(cell.getColumn(), cell.getRow(), "\"ERROR\"");
/*  565:     */       }
/*  566:     */     }
/*  567: 903 */     CellFormat cf = c.getCellFormat();
/*  568: 904 */     int index = ((XFRecord)cf).getXFIndex();
/*  569: 905 */     WritableCellFormat wcf = (WritableCellFormat)this.xfRecords.get(new Integer(index));
/*  570: 908 */     if (wcf == null) {
/*  571: 910 */       wcf = copyCellFormat(cf);
/*  572:     */     }
/*  573: 913 */     c.setCellFormat(wcf);
/*  574:     */     
/*  575: 915 */     return c;
/*  576:     */   }
/*  577:     */   
/*  578:     */   void shallowCopyCells()
/*  579:     */   {
/*  580: 924 */     int cells = this.fromSheet.getRows();
/*  581: 925 */     Cell[] row = null;
/*  582: 926 */     Cell cell = null;
/*  583: 927 */     for (int i = 0; i < cells; i++)
/*  584:     */     {
/*  585: 929 */       row = this.fromSheet.getRow(i);
/*  586: 931 */       for (int j = 0; j < row.length; j++)
/*  587:     */       {
/*  588: 933 */         cell = row[j];
/*  589: 934 */         WritableCell c = shallowCopyCell(cell);
/*  590:     */         try
/*  591:     */         {
/*  592: 943 */           if (c != null)
/*  593:     */           {
/*  594: 945 */             this.toSheet.addCell(c);
/*  595: 949 */             if ((c.getCellFeatures() != null) && (c.getCellFeatures().hasDataValidation())) {
/*  596: 952 */               this.validatedCells.add(c);
/*  597:     */             }
/*  598:     */           }
/*  599:     */         }
/*  600:     */         catch (WriteException e)
/*  601:     */         {
/*  602: 958 */           Assert.verify(false);
/*  603:     */         }
/*  604:     */       }
/*  605:     */     }
/*  606: 962 */     this.numRows = this.toSheet.getRows();
/*  607:     */   }
/*  608:     */   
/*  609:     */   void deepCopyCells()
/*  610:     */   {
/*  611: 971 */     int cells = this.fromSheet.getRows();
/*  612: 972 */     Cell[] row = null;
/*  613: 973 */     Cell cell = null;
/*  614: 974 */     for (int i = 0; i < cells; i++)
/*  615:     */     {
/*  616: 976 */       row = this.fromSheet.getRow(i);
/*  617: 978 */       for (int j = 0; j < row.length; j++)
/*  618:     */       {
/*  619: 980 */         cell = row[j];
/*  620: 981 */         WritableCell c = deepCopyCell(cell);
/*  621:     */         try
/*  622:     */         {
/*  623: 990 */           if (c != null)
/*  624:     */           {
/*  625: 992 */             this.toSheet.addCell(c);
/*  626: 996 */             if ((c.getCellFeatures() != null & c.getCellFeatures().hasDataValidation())) {
/*  627: 999 */               this.validatedCells.add(c);
/*  628:     */             }
/*  629:     */           }
/*  630:     */         }
/*  631:     */         catch (WriteException e)
/*  632:     */         {
/*  633:1005 */           Assert.verify(false);
/*  634:     */         }
/*  635:     */       }
/*  636:     */     }
/*  637:     */   }
/*  638:     */   
/*  639:     */   private WritableCellFormat copyCellFormat(CellFormat cf)
/*  640:     */   {
/*  641:     */     try
/*  642:     */     {
/*  643:1024 */       XFRecord xfr = (XFRecord)cf;
/*  644:1025 */       WritableCellFormat f = new WritableCellFormat(xfr);
/*  645:1026 */       this.formatRecords.addStyle(f);
/*  646:     */       
/*  647:     */ 
/*  648:1029 */       int xfIndex = xfr.getXFIndex();
/*  649:1030 */       this.xfRecords.put(new Integer(xfIndex), f);
/*  650:     */       
/*  651:1032 */       int fontIndex = xfr.getFontIndex();
/*  652:1033 */       this.fonts.put(new Integer(fontIndex), new Integer(f.getFontIndex()));
/*  653:     */       
/*  654:1035 */       int formatIndex = xfr.getFormatRecord();
/*  655:1036 */       this.formats.put(new Integer(formatIndex), new Integer(f.getFormatRecord()));
/*  656:     */       
/*  657:1038 */       return f;
/*  658:     */     }
/*  659:     */     catch (NumFormatRecordsException e)
/*  660:     */     {
/*  661:1042 */       logger.warn("Maximum number of format records exceeded.  Using default format.");
/*  662:     */     }
/*  663:1045 */     return WritableWorkbook.NORMAL_STYLE;
/*  664:     */   }
/*  665:     */   
/*  666:     */   private void importNames()
/*  667:     */   {
/*  668:1054 */     WorkbookParser fromWorkbook = this.fromSheet.getWorkbook();
/*  669:1055 */     WritableWorkbook toWorkbook = this.toSheet.getWorkbook();
/*  670:1056 */     int fromSheetIndex = fromWorkbook.getIndex(this.fromSheet);
/*  671:1057 */     NameRecord[] nameRecords = fromWorkbook.getNameRecords();
/*  672:1058 */     String[] names = toWorkbook.getRangeNames();
/*  673:1060 */     for (int i = 0; i < nameRecords.length; i++)
/*  674:     */     {
/*  675:1062 */       NameRecord.NameRange[] nameRanges = nameRecords[i].getRanges();
/*  676:1064 */       for (int j = 0; j < nameRanges.length; j++)
/*  677:     */       {
/*  678:1066 */         int nameSheetIndex = fromWorkbook.getExternalSheetIndex(nameRanges[j].getExternalSheet());
/*  679:1069 */         if (fromSheetIndex == nameSheetIndex)
/*  680:     */         {
/*  681:1071 */           String name = nameRecords[i].getName();
/*  682:1072 */           if (Arrays.binarySearch(names, name) < 0) {
/*  683:1074 */             toWorkbook.addNameArea(name, this.toSheet, nameRanges[j].getFirstColumn(), nameRanges[j].getFirstRow(), nameRanges[j].getLastColumn(), nameRanges[j].getLastRow());
/*  684:     */           } else {
/*  685:1083 */             logger.warn("Named range " + name + " is already present in the destination workbook");
/*  686:     */           }
/*  687:     */         }
/*  688:     */       }
/*  689:     */     }
/*  690:     */   }
/*  691:     */   
/*  692:     */   int getRows()
/*  693:     */   {
/*  694:1100 */     return this.numRows;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public int getMaxColumnOutlineLevel()
/*  698:     */   {
/*  699:1110 */     return this.maxColumnOutlineLevel;
/*  700:     */   }
/*  701:     */   
/*  702:     */   public int getMaxRowOutlineLevel()
/*  703:     */   {
/*  704:1120 */     return this.maxRowOutlineLevel;
/*  705:     */   }
/*  706:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.SheetCopier
 * JD-Core Version:    0.7.0.1
 */