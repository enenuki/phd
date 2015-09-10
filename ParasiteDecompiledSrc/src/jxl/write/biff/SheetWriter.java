/*    1:     */ package jxl.write.biff;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.TreeSet;
/*    7:     */ import jxl.Cell;
/*    8:     */ import jxl.CellFeatures;
/*    9:     */ import jxl.HeaderFooter;
/*   10:     */ import jxl.Range;
/*   11:     */ import jxl.SheetSettings;
/*   12:     */ import jxl.WorkbookSettings;
/*   13:     */ import jxl.biff.AutoFilter;
/*   14:     */ import jxl.biff.ConditionalFormat;
/*   15:     */ import jxl.biff.DVParser;
/*   16:     */ import jxl.biff.DataValidation;
/*   17:     */ import jxl.biff.DataValiditySettingsRecord;
/*   18:     */ import jxl.biff.WorkspaceInformationRecord;
/*   19:     */ import jxl.biff.XFRecord;
/*   20:     */ import jxl.biff.drawing.Chart;
/*   21:     */ import jxl.biff.drawing.ComboBox;
/*   22:     */ import jxl.biff.drawing.SheetDrawingWriter;
/*   23:     */ import jxl.common.Assert;
/*   24:     */ import jxl.common.Logger;
/*   25:     */ import jxl.format.Border;
/*   26:     */ import jxl.format.BorderLineStyle;
/*   27:     */ import jxl.format.Colour;
/*   28:     */ import jxl.write.Blank;
/*   29:     */ import jxl.write.WritableCell;
/*   30:     */ import jxl.write.WritableCellFormat;
/*   31:     */ import jxl.write.WritableHyperlink;
/*   32:     */ import jxl.write.WriteException;
/*   33:     */ 
/*   34:     */ final class SheetWriter
/*   35:     */ {
/*   36:  66 */   private static Logger logger = Logger.getLogger(SheetWriter.class);
/*   37:     */   private File outputFile;
/*   38:     */   private RowRecord[] rows;
/*   39:     */   private int numRows;
/*   40:     */   private int numCols;
/*   41:     */   private HeaderRecord header;
/*   42:     */   private FooterRecord footer;
/*   43:     */   private SheetSettings settings;
/*   44:     */   private WorkbookSettings workbookSettings;
/*   45:     */   private ArrayList rowBreaks;
/*   46:     */   private ArrayList columnBreaks;
/*   47:     */   private ArrayList hyperlinks;
/*   48:     */   private ArrayList conditionalFormats;
/*   49:     */   private AutoFilter autoFilter;
/*   50:     */   private ArrayList validatedCells;
/*   51:     */   private DataValidation dataValidation;
/*   52:     */   private MergedCells mergedCells;
/*   53:     */   private PLSRecord plsRecord;
/*   54:     */   private ButtonPropertySetRecord buttonPropertySet;
/*   55:     */   private WorkspaceInformationRecord workspaceOptions;
/*   56:     */   private TreeSet columnFormats;
/*   57:     */   private SheetDrawingWriter drawingWriter;
/*   58:     */   private boolean chartOnly;
/*   59:     */   private int maxRowOutlineLevel;
/*   60:     */   private int maxColumnOutlineLevel;
/*   61:     */   private WritableSheetImpl sheet;
/*   62:     */   
/*   63:     */   public SheetWriter(File of, WritableSheetImpl wsi, WorkbookSettings ws)
/*   64:     */   {
/*   65: 194 */     this.outputFile = of;
/*   66: 195 */     this.sheet = wsi;
/*   67: 196 */     this.workspaceOptions = new WorkspaceInformationRecord();
/*   68: 197 */     this.workbookSettings = ws;
/*   69: 198 */     this.chartOnly = false;
/*   70: 199 */     this.drawingWriter = new SheetDrawingWriter(ws);
/*   71:     */   }
/*   72:     */   
/*   73:     */   public void write()
/*   74:     */     throws IOException
/*   75:     */   {
/*   76: 212 */     Assert.verify(this.rows != null);
/*   77: 215 */     if (this.chartOnly)
/*   78:     */     {
/*   79: 217 */       this.drawingWriter.write(this.outputFile);
/*   80: 218 */       return;
/*   81:     */     }
/*   82: 221 */     BOFRecord bof = new BOFRecord(BOFRecord.sheet);
/*   83: 222 */     this.outputFile.write(bof);
/*   84:     */     
/*   85:     */ 
/*   86: 225 */     int numBlocks = this.numRows / 32;
/*   87: 226 */     if (this.numRows - numBlocks * 32 != 0) {
/*   88: 228 */       numBlocks++;
/*   89:     */     }
/*   90: 231 */     int indexPos = this.outputFile.getPos();
/*   91:     */     
/*   92:     */ 
/*   93:     */ 
/*   94: 235 */     IndexRecord indexRecord = new IndexRecord(0, this.numRows, numBlocks);
/*   95: 236 */     this.outputFile.write(indexRecord);
/*   96: 238 */     if (this.settings.getAutomaticFormulaCalculation())
/*   97:     */     {
/*   98: 240 */       CalcModeRecord cmr = new CalcModeRecord(CalcModeRecord.automatic);
/*   99: 241 */       this.outputFile.write(cmr);
/*  100:     */     }
/*  101:     */     else
/*  102:     */     {
/*  103: 245 */       CalcModeRecord cmr = new CalcModeRecord(CalcModeRecord.manual);
/*  104: 246 */       this.outputFile.write(cmr);
/*  105:     */     }
/*  106: 249 */     CalcCountRecord ccr = new CalcCountRecord(100);
/*  107: 250 */     this.outputFile.write(ccr);
/*  108:     */     
/*  109: 252 */     RefModeRecord rmr = new RefModeRecord();
/*  110: 253 */     this.outputFile.write(rmr);
/*  111:     */     
/*  112: 255 */     IterationRecord itr = new IterationRecord(false);
/*  113: 256 */     this.outputFile.write(itr);
/*  114:     */     
/*  115: 258 */     DeltaRecord dtr = new DeltaRecord(0.001D);
/*  116: 259 */     this.outputFile.write(dtr);
/*  117:     */     
/*  118: 261 */     SaveRecalcRecord srr = new SaveRecalcRecord(this.settings.getRecalculateFormulasBeforeSave());
/*  119:     */     
/*  120: 263 */     this.outputFile.write(srr);
/*  121:     */     
/*  122: 265 */     PrintHeadersRecord phr = new PrintHeadersRecord(this.settings.getPrintHeaders());
/*  123:     */     
/*  124: 267 */     this.outputFile.write(phr);
/*  125:     */     
/*  126: 269 */     PrintGridLinesRecord pglr = new PrintGridLinesRecord(this.settings.getPrintGridLines());
/*  127:     */     
/*  128: 271 */     this.outputFile.write(pglr);
/*  129:     */     
/*  130: 273 */     GridSetRecord gsr = new GridSetRecord(true);
/*  131: 274 */     this.outputFile.write(gsr);
/*  132:     */     
/*  133: 276 */     GuttersRecord gutr = new GuttersRecord();
/*  134: 277 */     gutr.setMaxColumnOutline(this.maxColumnOutlineLevel + 1);
/*  135: 278 */     gutr.setMaxRowOutline(this.maxRowOutlineLevel + 1);
/*  136:     */     
/*  137: 280 */     this.outputFile.write(gutr);
/*  138:     */     
/*  139: 282 */     DefaultRowHeightRecord drhr = new DefaultRowHeightRecord(this.settings.getDefaultRowHeight(), this.settings.getDefaultRowHeight() != 255);
/*  140:     */     
/*  141:     */ 
/*  142:     */ 
/*  143: 286 */     this.outputFile.write(drhr);
/*  144: 288 */     if (this.maxRowOutlineLevel > 0) {
/*  145: 290 */       this.workspaceOptions.setRowOutlines(true);
/*  146:     */     }
/*  147: 293 */     if (this.maxColumnOutlineLevel > 0) {
/*  148: 295 */       this.workspaceOptions.setColumnOutlines(true);
/*  149:     */     }
/*  150: 298 */     this.workspaceOptions.setFitToPages(this.settings.getFitToPages());
/*  151: 299 */     this.outputFile.write(this.workspaceOptions);
/*  152: 301 */     if (this.rowBreaks.size() > 0)
/*  153:     */     {
/*  154: 303 */       int[] rb = new int[this.rowBreaks.size()];
/*  155: 305 */       for (int i = 0; i < rb.length; i++) {
/*  156: 307 */         rb[i] = ((Integer)this.rowBreaks.get(i)).intValue();
/*  157:     */       }
/*  158: 310 */       HorizontalPageBreaksRecord hpbr = new HorizontalPageBreaksRecord(rb);
/*  159: 311 */       this.outputFile.write(hpbr);
/*  160:     */     }
/*  161: 314 */     if (this.columnBreaks.size() > 0)
/*  162:     */     {
/*  163: 316 */       int[] rb = new int[this.columnBreaks.size()];
/*  164: 318 */       for (int i = 0; i < rb.length; i++) {
/*  165: 320 */         rb[i] = ((Integer)this.columnBreaks.get(i)).intValue();
/*  166:     */       }
/*  167: 323 */       VerticalPageBreaksRecord hpbr = new VerticalPageBreaksRecord(rb);
/*  168: 324 */       this.outputFile.write(hpbr);
/*  169:     */     }
/*  170: 327 */     HeaderRecord header = new HeaderRecord(this.settings.getHeader().toString());
/*  171: 328 */     this.outputFile.write(header);
/*  172:     */     
/*  173: 330 */     FooterRecord footer = new FooterRecord(this.settings.getFooter().toString());
/*  174: 331 */     this.outputFile.write(footer);
/*  175:     */     
/*  176: 333 */     HorizontalCentreRecord hcr = new HorizontalCentreRecord(this.settings.isHorizontalCentre());
/*  177:     */     
/*  178: 335 */     this.outputFile.write(hcr);
/*  179:     */     
/*  180: 337 */     VerticalCentreRecord vcr = new VerticalCentreRecord(this.settings.isVerticalCentre());
/*  181:     */     
/*  182: 339 */     this.outputFile.write(vcr);
/*  183: 342 */     if (this.settings.getLeftMargin() != this.settings.getDefaultWidthMargin())
/*  184:     */     {
/*  185: 344 */       MarginRecord mr = new LeftMarginRecord(this.settings.getLeftMargin());
/*  186: 345 */       this.outputFile.write(mr);
/*  187:     */     }
/*  188: 348 */     if (this.settings.getRightMargin() != this.settings.getDefaultWidthMargin())
/*  189:     */     {
/*  190: 350 */       MarginRecord mr = new RightMarginRecord(this.settings.getRightMargin());
/*  191: 351 */       this.outputFile.write(mr);
/*  192:     */     }
/*  193: 354 */     if (this.settings.getTopMargin() != this.settings.getDefaultHeightMargin())
/*  194:     */     {
/*  195: 356 */       MarginRecord mr = new TopMarginRecord(this.settings.getTopMargin());
/*  196: 357 */       this.outputFile.write(mr);
/*  197:     */     }
/*  198: 360 */     if (this.settings.getBottomMargin() != this.settings.getDefaultHeightMargin())
/*  199:     */     {
/*  200: 362 */       MarginRecord mr = new BottomMarginRecord(this.settings.getBottomMargin());
/*  201: 363 */       this.outputFile.write(mr);
/*  202:     */     }
/*  203: 366 */     if (this.plsRecord != null) {
/*  204: 368 */       this.outputFile.write(this.plsRecord);
/*  205:     */     }
/*  206: 371 */     SetupRecord setup = new SetupRecord(this.settings);
/*  207: 372 */     this.outputFile.write(setup);
/*  208: 374 */     if (this.settings.isProtected())
/*  209:     */     {
/*  210: 376 */       ProtectRecord pr = new ProtectRecord(this.settings.isProtected());
/*  211: 377 */       this.outputFile.write(pr);
/*  212:     */       
/*  213: 379 */       ScenarioProtectRecord spr = new ScenarioProtectRecord(this.settings.isProtected());
/*  214:     */       
/*  215: 381 */       this.outputFile.write(spr);
/*  216:     */       
/*  217: 383 */       ObjectProtectRecord opr = new ObjectProtectRecord(this.settings.isProtected());
/*  218:     */       
/*  219: 385 */       this.outputFile.write(opr);
/*  220: 387 */       if (this.settings.getPassword() != null)
/*  221:     */       {
/*  222: 389 */         PasswordRecord pw = new PasswordRecord(this.settings.getPassword());
/*  223: 390 */         this.outputFile.write(pw);
/*  224:     */       }
/*  225: 392 */       else if (this.settings.getPasswordHash() != 0)
/*  226:     */       {
/*  227: 394 */         PasswordRecord pw = new PasswordRecord(this.settings.getPasswordHash());
/*  228: 395 */         this.outputFile.write(pw);
/*  229:     */       }
/*  230:     */     }
/*  231: 399 */     indexRecord.setDataStartPosition(this.outputFile.getPos());
/*  232: 400 */     DefaultColumnWidth dcw = new DefaultColumnWidth(this.settings.getDefaultColumnWidth());
/*  233:     */     
/*  234: 402 */     this.outputFile.write(dcw);
/*  235:     */     
/*  236:     */ 
/*  237: 405 */     WritableCellFormat normalStyle = this.sheet.getWorkbook().getStyles().getNormalStyle();
/*  238:     */     
/*  239: 407 */     WritableCellFormat defaultDateFormat = this.sheet.getWorkbook().getStyles().getDefaultDateFormat();
/*  240:     */     
/*  241:     */ 
/*  242:     */ 
/*  243: 411 */     ColumnInfoRecord cir = null;
/*  244: 412 */     for (Iterator colit = this.columnFormats.iterator(); colit.hasNext();)
/*  245:     */     {
/*  246: 414 */       cir = (ColumnInfoRecord)colit.next();
/*  247: 417 */       if (cir.getColumn() < 256) {
/*  248: 419 */         this.outputFile.write(cir);
/*  249:     */       }
/*  250: 422 */       XFRecord xfr = cir.getCellFormat();
/*  251: 424 */       if ((xfr != normalStyle) && (cir.getColumn() < 256))
/*  252:     */       {
/*  253: 427 */         Cell[] cells = getColumn(cir.getColumn());
/*  254: 429 */         for (int i = 0; i < cells.length; i++) {
/*  255: 431 */           if ((cells[i] != null) && ((cells[i].getCellFormat() == normalStyle) || (cells[i].getCellFormat() == defaultDateFormat))) {
/*  256: 437 */             ((WritableCell)cells[i]).setCellFormat(xfr);
/*  257:     */           }
/*  258:     */         }
/*  259:     */       }
/*  260:     */     }
/*  261: 444 */     if (this.autoFilter != null) {
/*  262: 446 */       this.autoFilter.write(this.outputFile);
/*  263:     */     }
/*  264: 449 */     DimensionRecord dr = new DimensionRecord(this.numRows, this.numCols);
/*  265: 450 */     this.outputFile.write(dr);
/*  266: 453 */     for (int block = 0; block < numBlocks; block++)
/*  267:     */     {
/*  268: 455 */       DBCellRecord dbcell = new DBCellRecord(this.outputFile.getPos());
/*  269:     */       
/*  270: 457 */       int blockRows = Math.min(32, this.numRows - block * 32);
/*  271: 458 */       boolean firstRow = true;
/*  272: 461 */       for (int i = block * 32; i < block * 32 + blockRows; i++) {
/*  273: 463 */         if (this.rows[i] != null)
/*  274:     */         {
/*  275: 465 */           this.rows[i].write(this.outputFile);
/*  276: 466 */           if (firstRow)
/*  277:     */           {
/*  278: 468 */             dbcell.setCellOffset(this.outputFile.getPos());
/*  279: 469 */             firstRow = false;
/*  280:     */           }
/*  281:     */         }
/*  282:     */       }
/*  283: 475 */       for (int i = block * 32; i < block * 32 + blockRows; i++) {
/*  284: 477 */         if (this.rows[i] != null)
/*  285:     */         {
/*  286: 479 */           dbcell.addCellRowPosition(this.outputFile.getPos());
/*  287: 480 */           this.rows[i].writeCells(this.outputFile);
/*  288:     */         }
/*  289:     */       }
/*  290: 485 */       indexRecord.addBlockPosition(this.outputFile.getPos());
/*  291:     */       
/*  292:     */ 
/*  293:     */ 
/*  294: 489 */       dbcell.setPosition(this.outputFile.getPos());
/*  295: 490 */       this.outputFile.write(dbcell);
/*  296:     */     }
/*  297: 494 */     if (!this.workbookSettings.getDrawingsDisabled()) {
/*  298: 496 */       this.drawingWriter.write(this.outputFile);
/*  299:     */     }
/*  300: 499 */     Window2Record w2r = new Window2Record(this.settings);
/*  301: 500 */     this.outputFile.write(w2r);
/*  302: 503 */     if ((this.settings.getHorizontalFreeze() != 0) || (this.settings.getVerticalFreeze() != 0))
/*  303:     */     {
/*  304: 506 */       PaneRecord pr = new PaneRecord(this.settings.getHorizontalFreeze(), this.settings.getVerticalFreeze());
/*  305:     */       
/*  306: 508 */       this.outputFile.write(pr);
/*  307:     */       
/*  308:     */ 
/*  309: 511 */       SelectionRecord sr = new SelectionRecord(SelectionRecord.upperLeft, 0, 0);
/*  310:     */       
/*  311: 513 */       this.outputFile.write(sr);
/*  312: 516 */       if (this.settings.getHorizontalFreeze() != 0)
/*  313:     */       {
/*  314: 518 */         sr = new SelectionRecord(SelectionRecord.upperRight, this.settings.getHorizontalFreeze(), 0);
/*  315:     */         
/*  316: 520 */         this.outputFile.write(sr);
/*  317:     */       }
/*  318: 524 */       if (this.settings.getVerticalFreeze() != 0)
/*  319:     */       {
/*  320: 526 */         sr = new SelectionRecord(SelectionRecord.lowerLeft, 0, this.settings.getVerticalFreeze());
/*  321:     */         
/*  322: 528 */         this.outputFile.write(sr);
/*  323:     */       }
/*  324: 532 */       if ((this.settings.getHorizontalFreeze() != 0) && (this.settings.getVerticalFreeze() != 0))
/*  325:     */       {
/*  326: 535 */         sr = new SelectionRecord(SelectionRecord.lowerRight, this.settings.getHorizontalFreeze(), this.settings.getVerticalFreeze());
/*  327:     */         
/*  328:     */ 
/*  329:     */ 
/*  330: 539 */         this.outputFile.write(sr);
/*  331:     */       }
/*  332: 542 */       Weird1Record w1r = new Weird1Record();
/*  333: 543 */       this.outputFile.write(w1r);
/*  334:     */     }
/*  335:     */     else
/*  336:     */     {
/*  337: 549 */       SelectionRecord sr = new SelectionRecord(SelectionRecord.upperLeft, 0, 0);
/*  338:     */       
/*  339: 551 */       this.outputFile.write(sr);
/*  340:     */     }
/*  341: 555 */     if (this.settings.getZoomFactor() != 100)
/*  342:     */     {
/*  343: 557 */       SCLRecord sclr = new SCLRecord(this.settings.getZoomFactor());
/*  344: 558 */       this.outputFile.write(sclr);
/*  345:     */     }
/*  346: 562 */     this.mergedCells.write(this.outputFile);
/*  347:     */     
/*  348:     */ 
/*  349: 565 */     Iterator hi = this.hyperlinks.iterator();
/*  350: 566 */     WritableHyperlink hlr = null;
/*  351: 567 */     while (hi.hasNext())
/*  352:     */     {
/*  353: 569 */       hlr = (WritableHyperlink)hi.next();
/*  354: 570 */       this.outputFile.write(hlr);
/*  355:     */     }
/*  356: 573 */     if (this.buttonPropertySet != null) {
/*  357: 575 */       this.outputFile.write(this.buttonPropertySet);
/*  358:     */     }
/*  359: 579 */     if ((this.dataValidation != null) || (this.validatedCells.size() > 0)) {
/*  360: 581 */       writeDataValidation();
/*  361:     */     }
/*  362:     */     Iterator i;
/*  363: 585 */     if ((this.conditionalFormats != null) && (this.conditionalFormats.size() > 0)) {
/*  364: 587 */       for (i = this.conditionalFormats.iterator(); i.hasNext();)
/*  365:     */       {
/*  366: 589 */         ConditionalFormat cf = (ConditionalFormat)i.next();
/*  367: 590 */         cf.write(this.outputFile);
/*  368:     */       }
/*  369:     */     }
/*  370: 594 */     EOFRecord eof = new EOFRecord();
/*  371: 595 */     this.outputFile.write(eof);
/*  372:     */     
/*  373:     */ 
/*  374:     */ 
/*  375: 599 */     this.outputFile.setData(indexRecord.getData(), indexPos + 4);
/*  376:     */   }
/*  377:     */   
/*  378:     */   final HeaderRecord getHeader()
/*  379:     */   {
/*  380: 609 */     return this.header;
/*  381:     */   }
/*  382:     */   
/*  383:     */   final FooterRecord getFooter()
/*  384:     */   {
/*  385: 619 */     return this.footer;
/*  386:     */   }
/*  387:     */   
/*  388:     */   void setWriteData(RowRecord[] rws, ArrayList rb, ArrayList cb, ArrayList hl, MergedCells mc, TreeSet cf, int mrol, int mcol)
/*  389:     */   {
/*  390: 637 */     this.rows = rws;
/*  391: 638 */     this.rowBreaks = rb;
/*  392: 639 */     this.columnBreaks = cb;
/*  393: 640 */     this.hyperlinks = hl;
/*  394: 641 */     this.mergedCells = mc;
/*  395: 642 */     this.columnFormats = cf;
/*  396: 643 */     this.maxRowOutlineLevel = mrol;
/*  397: 644 */     this.maxColumnOutlineLevel = mcol;
/*  398:     */   }
/*  399:     */   
/*  400:     */   void setDimensions(int rws, int cls)
/*  401:     */   {
/*  402: 656 */     this.numRows = rws;
/*  403: 657 */     this.numCols = cls;
/*  404:     */   }
/*  405:     */   
/*  406:     */   void setSettings(SheetSettings sr)
/*  407:     */   {
/*  408: 668 */     this.settings = sr;
/*  409:     */   }
/*  410:     */   
/*  411:     */   WorkspaceInformationRecord getWorkspaceOptions()
/*  412:     */   {
/*  413: 678 */     return this.workspaceOptions;
/*  414:     */   }
/*  415:     */   
/*  416:     */   void setWorkspaceOptions(WorkspaceInformationRecord wo)
/*  417:     */   {
/*  418: 688 */     if (wo != null) {
/*  419: 690 */       this.workspaceOptions = wo;
/*  420:     */     }
/*  421:     */   }
/*  422:     */   
/*  423:     */   void setCharts(Chart[] ch)
/*  424:     */   {
/*  425: 702 */     this.drawingWriter.setCharts(ch);
/*  426:     */   }
/*  427:     */   
/*  428:     */   void setDrawings(ArrayList dr, boolean mod)
/*  429:     */   {
/*  430: 713 */     this.drawingWriter.setDrawings(dr, mod);
/*  431:     */   }
/*  432:     */   
/*  433:     */   Chart[] getCharts()
/*  434:     */   {
/*  435: 723 */     return this.drawingWriter.getCharts();
/*  436:     */   }
/*  437:     */   
/*  438:     */   void checkMergedBorders()
/*  439:     */   {
/*  440: 734 */     Range[] mcells = this.mergedCells.getMergedCells();
/*  441: 735 */     ArrayList borderFormats = new ArrayList();
/*  442: 736 */     for (int mci = 0; mci < mcells.length; mci++)
/*  443:     */     {
/*  444: 738 */       Range range = mcells[mci];
/*  445: 739 */       Cell topLeft = range.getTopLeft();
/*  446: 740 */       XFRecord tlformat = (XFRecord)topLeft.getCellFormat();
/*  447: 742 */       if ((tlformat != null) && (tlformat.hasBorders() == true) && (!tlformat.isRead())) {
/*  448:     */         try
/*  449:     */         {
/*  450: 748 */           CellXFRecord cf1 = new CellXFRecord(tlformat);
/*  451: 749 */           Cell bottomRight = range.getBottomRight();
/*  452:     */           
/*  453: 751 */           cf1.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
/*  454: 752 */           cf1.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
/*  455:     */           
/*  456:     */ 
/*  457: 755 */           cf1.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
/*  458: 759 */           if (topLeft.getRow() == bottomRight.getRow()) {
/*  459: 761 */             cf1.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
/*  460:     */           }
/*  461: 766 */           if (topLeft.getColumn() == bottomRight.getColumn()) {
/*  462: 768 */             cf1.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
/*  463:     */           }
/*  464: 773 */           int index = borderFormats.indexOf(cf1);
/*  465: 774 */           if (index != -1) {
/*  466: 776 */             cf1 = (CellXFRecord)borderFormats.get(index);
/*  467:     */           } else {
/*  468: 780 */             borderFormats.add(cf1);
/*  469:     */           }
/*  470: 782 */           ((WritableCell)topLeft).setCellFormat(cf1);
/*  471: 785 */           if (bottomRight.getRow() > topLeft.getRow())
/*  472:     */           {
/*  473: 788 */             if (bottomRight.getColumn() != topLeft.getColumn())
/*  474:     */             {
/*  475: 790 */               CellXFRecord cf2 = new CellXFRecord(tlformat);
/*  476: 791 */               cf2.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
/*  477: 792 */               cf2.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
/*  478:     */               
/*  479:     */ 
/*  480: 795 */               cf2.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
/*  481:     */               
/*  482:     */ 
/*  483:     */ 
/*  484: 799 */               index = borderFormats.indexOf(cf2);
/*  485: 800 */               if (index != -1) {
/*  486: 802 */                 cf2 = (CellXFRecord)borderFormats.get(index);
/*  487:     */               } else {
/*  488: 806 */                 borderFormats.add(cf2);
/*  489:     */               }
/*  490: 809 */               this.sheet.addCell(new Blank(topLeft.getColumn(), bottomRight.getRow(), cf2));
/*  491:     */             }
/*  492: 815 */             for (int i = topLeft.getRow() + 1; i < bottomRight.getRow(); i++)
/*  493:     */             {
/*  494: 817 */               CellXFRecord cf3 = new CellXFRecord(tlformat);
/*  495: 818 */               cf3.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
/*  496: 819 */               cf3.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
/*  497: 823 */               if (topLeft.getColumn() == bottomRight.getColumn()) {
/*  498: 825 */                 cf3.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
/*  499:     */               }
/*  500: 830 */               index = borderFormats.indexOf(cf3);
/*  501: 831 */               if (index != -1) {
/*  502: 833 */                 cf3 = (CellXFRecord)borderFormats.get(index);
/*  503:     */               } else {
/*  504: 837 */                 borderFormats.add(cf3);
/*  505:     */               }
/*  506: 840 */               this.sheet.addCell(new Blank(topLeft.getColumn(), i, cf3));
/*  507:     */             }
/*  508:     */           }
/*  509: 845 */           if (bottomRight.getColumn() > topLeft.getColumn())
/*  510:     */           {
/*  511: 847 */             if (bottomRight.getRow() != topLeft.getRow())
/*  512:     */             {
/*  513: 850 */               CellXFRecord cf6 = new CellXFRecord(tlformat);
/*  514: 851 */               cf6.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
/*  515: 852 */               cf6.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
/*  516:     */               
/*  517:     */ 
/*  518: 855 */               cf6.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
/*  519:     */               
/*  520:     */ 
/*  521: 858 */               index = borderFormats.indexOf(cf6);
/*  522: 859 */               if (index != -1) {
/*  523: 861 */                 cf6 = (CellXFRecord)borderFormats.get(index);
/*  524:     */               } else {
/*  525: 865 */                 borderFormats.add(cf6);
/*  526:     */               }
/*  527: 868 */               this.sheet.addCell(new Blank(bottomRight.getColumn(), topLeft.getRow(), cf6));
/*  528:     */             }
/*  529: 873 */             for (int i = topLeft.getRow() + 1; i < bottomRight.getRow(); i++)
/*  530:     */             {
/*  531: 876 */               CellXFRecord cf7 = new CellXFRecord(tlformat);
/*  532: 877 */               cf7.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
/*  533: 878 */               cf7.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
/*  534:     */               
/*  535:     */ 
/*  536:     */ 
/*  537: 882 */               index = borderFormats.indexOf(cf7);
/*  538: 883 */               if (index != -1) {
/*  539: 885 */                 cf7 = (CellXFRecord)borderFormats.get(index);
/*  540:     */               } else {
/*  541: 889 */                 borderFormats.add(cf7);
/*  542:     */               }
/*  543: 892 */               this.sheet.addCell(new Blank(bottomRight.getColumn(), i, cf7));
/*  544:     */             }
/*  545: 896 */             for (int i = topLeft.getColumn() + 1; i < bottomRight.getColumn(); i++)
/*  546:     */             {
/*  547: 899 */               CellXFRecord cf8 = new CellXFRecord(tlformat);
/*  548: 900 */               cf8.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
/*  549: 901 */               cf8.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
/*  550: 905 */               if (topLeft.getRow() == bottomRight.getRow()) {
/*  551: 907 */                 cf8.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
/*  552:     */               }
/*  553: 912 */               index = borderFormats.indexOf(cf8);
/*  554: 913 */               if (index != -1) {
/*  555: 915 */                 cf8 = (CellXFRecord)borderFormats.get(index);
/*  556:     */               } else {
/*  557: 919 */                 borderFormats.add(cf8);
/*  558:     */               }
/*  559: 922 */               this.sheet.addCell(new Blank(i, topLeft.getRow(), cf8));
/*  560:     */             }
/*  561:     */           }
/*  562: 927 */           if ((bottomRight.getColumn() > topLeft.getColumn()) || (bottomRight.getRow() > topLeft.getRow()))
/*  563:     */           {
/*  564: 931 */             CellXFRecord cf4 = new CellXFRecord(tlformat);
/*  565: 932 */             cf4.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
/*  566: 933 */             cf4.setBorder(Border.RIGHT, tlformat.getBorderLine(Border.RIGHT), tlformat.getBorderColour(Border.RIGHT));
/*  567:     */             
/*  568:     */ 
/*  569: 936 */             cf4.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
/*  570: 940 */             if (bottomRight.getRow() == topLeft.getRow()) {
/*  571: 942 */               cf4.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
/*  572:     */             }
/*  573: 947 */             if (bottomRight.getColumn() == topLeft.getColumn()) {
/*  574: 949 */               cf4.setBorder(Border.LEFT, tlformat.getBorderLine(Border.LEFT), tlformat.getBorderColour(Border.LEFT));
/*  575:     */             }
/*  576: 954 */             index = borderFormats.indexOf(cf4);
/*  577: 955 */             if (index != -1) {
/*  578: 957 */               cf4 = (CellXFRecord)borderFormats.get(index);
/*  579:     */             } else {
/*  580: 961 */               borderFormats.add(cf4);
/*  581:     */             }
/*  582: 964 */             this.sheet.addCell(new Blank(bottomRight.getColumn(), bottomRight.getRow(), cf4));
/*  583: 969 */             for (int i = topLeft.getColumn() + 1; i < bottomRight.getColumn(); i++)
/*  584:     */             {
/*  585: 972 */               CellXFRecord cf5 = new CellXFRecord(tlformat);
/*  586: 973 */               cf5.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
/*  587: 974 */               cf5.setBorder(Border.BOTTOM, tlformat.getBorderLine(Border.BOTTOM), tlformat.getBorderColour(Border.BOTTOM));
/*  588: 978 */               if (topLeft.getRow() == bottomRight.getRow()) {
/*  589: 980 */                 cf5.setBorder(Border.TOP, tlformat.getBorderLine(Border.TOP), tlformat.getBorderColour(Border.TOP));
/*  590:     */               }
/*  591: 985 */               index = borderFormats.indexOf(cf5);
/*  592: 986 */               if (index != -1) {
/*  593: 988 */                 cf5 = (CellXFRecord)borderFormats.get(index);
/*  594:     */               } else {
/*  595: 992 */                 borderFormats.add(cf5);
/*  596:     */               }
/*  597: 995 */               this.sheet.addCell(new Blank(i, bottomRight.getRow(), cf5));
/*  598:     */             }
/*  599:     */           }
/*  600:     */         }
/*  601:     */         catch (WriteException e)
/*  602:     */         {
/*  603:1002 */           logger.warn(e.toString());
/*  604:     */         }
/*  605:     */       }
/*  606:     */     }
/*  607:     */   }
/*  608:     */   
/*  609:     */   private Cell[] getColumn(int col)
/*  610:     */   {
/*  611:1016 */     boolean found = false;
/*  612:1017 */     int row = this.numRows - 1;
/*  613:1019 */     while ((row >= 0) && (!found)) {
/*  614:1021 */       if ((this.rows[row] != null) && (this.rows[row].getCell(col) != null)) {
/*  615:1024 */         found = true;
/*  616:     */       } else {
/*  617:1028 */         row--;
/*  618:     */       }
/*  619:     */     }
/*  620:1033 */     Cell[] cells = new Cell[row + 1];
/*  621:1035 */     for (int i = 0; i <= row; i++) {
/*  622:1037 */       cells[i] = (this.rows[i] != null ? this.rows[i].getCell(col) : null);
/*  623:     */     }
/*  624:1040 */     return cells;
/*  625:     */   }
/*  626:     */   
/*  627:     */   void setChartOnly()
/*  628:     */   {
/*  629:1048 */     this.chartOnly = true;
/*  630:     */   }
/*  631:     */   
/*  632:     */   void setPLS(PLSRecord pls)
/*  633:     */   {
/*  634:1058 */     this.plsRecord = pls;
/*  635:     */   }
/*  636:     */   
/*  637:     */   void setButtonPropertySet(ButtonPropertySetRecord bps)
/*  638:     */   {
/*  639:1068 */     this.buttonPropertySet = bps;
/*  640:     */   }
/*  641:     */   
/*  642:     */   void setDataValidation(DataValidation dv, ArrayList vc)
/*  643:     */   {
/*  644:1079 */     this.dataValidation = dv;
/*  645:1080 */     this.validatedCells = vc;
/*  646:     */   }
/*  647:     */   
/*  648:     */   void setConditionalFormats(ArrayList cf)
/*  649:     */   {
/*  650:1090 */     this.conditionalFormats = cf;
/*  651:     */   }
/*  652:     */   
/*  653:     */   void setAutoFilter(AutoFilter af)
/*  654:     */   {
/*  655:1100 */     this.autoFilter = af;
/*  656:     */   }
/*  657:     */   
/*  658:     */   private void writeDataValidation()
/*  659:     */     throws IOException
/*  660:     */   {
/*  661:1108 */     if ((this.dataValidation != null) && (this.validatedCells.size() == 0))
/*  662:     */     {
/*  663:1113 */       this.dataValidation.write(this.outputFile);
/*  664:1114 */       return;
/*  665:     */     }
/*  666:1117 */     if ((this.dataValidation == null) && (this.validatedCells.size() > 0))
/*  667:     */     {
/*  668:1121 */       int comboBoxId = this.sheet.getComboBox() != null ? this.sheet.getComboBox().getObjectId() : -1;
/*  669:     */       
/*  670:1123 */       this.dataValidation = new DataValidation(comboBoxId, this.sheet.getWorkbook(), this.sheet.getWorkbook(), this.workbookSettings);
/*  671:     */     }
/*  672:1129 */     for (Iterator i = this.validatedCells.iterator(); i.hasNext();)
/*  673:     */     {
/*  674:1131 */       CellValue cv = (CellValue)i.next();
/*  675:1132 */       CellFeatures cf = cv.getCellFeatures();
/*  676:1137 */       if (!cf.getDVParser().copied()) {
/*  677:1139 */         if (!cf.getDVParser().extendedCellsValidation())
/*  678:     */         {
/*  679:1142 */           DataValiditySettingsRecord dvsr = new DataValiditySettingsRecord(cf.getDVParser());
/*  680:     */           
/*  681:1144 */           this.dataValidation.add(dvsr);
/*  682:     */         }
/*  683:1150 */         else if ((cv.getColumn() == cf.getDVParser().getFirstColumn()) && (cv.getRow() == cf.getDVParser().getFirstRow()))
/*  684:     */         {
/*  685:1153 */           DataValiditySettingsRecord dvsr = new DataValiditySettingsRecord(cf.getDVParser());
/*  686:     */           
/*  687:1155 */           this.dataValidation.add(dvsr);
/*  688:     */         }
/*  689:     */       }
/*  690:     */     }
/*  691:1160 */     this.dataValidation.write(this.outputFile);
/*  692:     */   }
/*  693:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.SheetWriter
 * JD-Core Version:    0.7.0.1
 */