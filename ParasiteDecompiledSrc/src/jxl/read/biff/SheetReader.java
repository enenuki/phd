/*    1:     */ package jxl.read.biff;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.HashMap;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import jxl.Cell;
/*    7:     */ import jxl.CellFeatures;
/*    8:     */ import jxl.CellReferenceHelper;
/*    9:     */ import jxl.CellType;
/*   10:     */ import jxl.DateCell;
/*   11:     */ import jxl.HeaderFooter;
/*   12:     */ import jxl.Range;
/*   13:     */ import jxl.SheetSettings;
/*   14:     */ import jxl.WorkbookSettings;
/*   15:     */ import jxl.biff.AutoFilter;
/*   16:     */ import jxl.biff.AutoFilterInfoRecord;
/*   17:     */ import jxl.biff.AutoFilterRecord;
/*   18:     */ import jxl.biff.ConditionalFormat;
/*   19:     */ import jxl.biff.ConditionalFormatRangeRecord;
/*   20:     */ import jxl.biff.ConditionalFormatRecord;
/*   21:     */ import jxl.biff.ContinueRecord;
/*   22:     */ import jxl.biff.DataValidation;
/*   23:     */ import jxl.biff.DataValidityListRecord;
/*   24:     */ import jxl.biff.DataValiditySettingsRecord;
/*   25:     */ import jxl.biff.FilterModeRecord;
/*   26:     */ import jxl.biff.FormattingRecords;
/*   27:     */ import jxl.biff.Type;
/*   28:     */ import jxl.biff.WorkspaceInformationRecord;
/*   29:     */ import jxl.biff.drawing.Button;
/*   30:     */ import jxl.biff.drawing.Chart;
/*   31:     */ import jxl.biff.drawing.CheckBox;
/*   32:     */ import jxl.biff.drawing.ComboBox;
/*   33:     */ import jxl.biff.drawing.Comment;
/*   34:     */ import jxl.biff.drawing.Drawing;
/*   35:     */ import jxl.biff.drawing.Drawing2;
/*   36:     */ import jxl.biff.drawing.DrawingData;
/*   37:     */ import jxl.biff.drawing.DrawingDataException;
/*   38:     */ import jxl.biff.drawing.DrawingGroup;
/*   39:     */ import jxl.biff.drawing.MsoDrawingRecord;
/*   40:     */ import jxl.biff.drawing.NoteRecord;
/*   41:     */ import jxl.biff.drawing.ObjRecord;
/*   42:     */ import jxl.biff.drawing.TextObjectRecord;
/*   43:     */ import jxl.biff.formula.FormulaException;
/*   44:     */ import jxl.common.Assert;
/*   45:     */ import jxl.common.Logger;
/*   46:     */ import jxl.format.PageOrder;
/*   47:     */ import jxl.format.PageOrientation;
/*   48:     */ import jxl.format.PaperSize;
/*   49:     */ 
/*   50:     */ final class SheetReader
/*   51:     */ {
/*   52:  80 */   private static Logger logger = Logger.getLogger(SheetReader.class);
/*   53:     */   private File excelFile;
/*   54:     */   private SSTRecord sharedStrings;
/*   55:     */   private BOFRecord sheetBof;
/*   56:     */   private BOFRecord workbookBof;
/*   57:     */   private FormattingRecords formattingRecords;
/*   58:     */   private int numRows;
/*   59:     */   private int numCols;
/*   60:     */   private Cell[][] cells;
/*   61:     */   private ArrayList outOfBoundsCells;
/*   62:     */   private int startPosition;
/*   63:     */   private ArrayList rowProperties;
/*   64:     */   private ArrayList columnInfosArray;
/*   65:     */   private ArrayList sharedFormulas;
/*   66:     */   private ArrayList hyperlinks;
/*   67:     */   private ArrayList conditionalFormats;
/*   68:     */   private AutoFilter autoFilter;
/*   69:     */   private Range[] mergedCells;
/*   70:     */   private DataValidation dataValidation;
/*   71:     */   private ArrayList charts;
/*   72:     */   private ArrayList drawings;
/*   73:     */   private DrawingData drawingData;
/*   74:     */   private boolean nineteenFour;
/*   75:     */   private PLSRecord plsRecord;
/*   76:     */   private ButtonPropertySetRecord buttonPropertySet;
/*   77:     */   private WorkspaceInformationRecord workspaceOptions;
/*   78:     */   private int[] rowBreaks;
/*   79:     */   private int[] columnBreaks;
/*   80:     */   private int maxRowOutlineLevel;
/*   81:     */   private int maxColumnOutlineLevel;
/*   82:     */   private SheetSettings settings;
/*   83:     */   private WorkbookSettings workbookSettings;
/*   84:     */   private WorkbookParser workbook;
/*   85:     */   private SheetImpl sheet;
/*   86:     */   
/*   87:     */   SheetReader(File f, SSTRecord sst, FormattingRecords fr, BOFRecord sb, BOFRecord wb, boolean nf, WorkbookParser wp, int sp, SheetImpl sh)
/*   88:     */   {
/*   89: 273 */     this.excelFile = f;
/*   90: 274 */     this.sharedStrings = sst;
/*   91: 275 */     this.formattingRecords = fr;
/*   92: 276 */     this.sheetBof = sb;
/*   93: 277 */     this.workbookBof = wb;
/*   94: 278 */     this.columnInfosArray = new ArrayList();
/*   95: 279 */     this.sharedFormulas = new ArrayList();
/*   96: 280 */     this.hyperlinks = new ArrayList();
/*   97: 281 */     this.conditionalFormats = new ArrayList();
/*   98: 282 */     this.rowProperties = new ArrayList(10);
/*   99: 283 */     this.charts = new ArrayList();
/*  100: 284 */     this.drawings = new ArrayList();
/*  101: 285 */     this.outOfBoundsCells = new ArrayList();
/*  102: 286 */     this.nineteenFour = nf;
/*  103: 287 */     this.workbook = wp;
/*  104: 288 */     this.startPosition = sp;
/*  105: 289 */     this.sheet = sh;
/*  106: 290 */     this.settings = new SheetSettings(sh);
/*  107: 291 */     this.workbookSettings = this.workbook.getSettings();
/*  108:     */   }
/*  109:     */   
/*  110:     */   private void addCell(Cell cell)
/*  111:     */   {
/*  112: 303 */     if ((cell.getRow() < this.numRows) && (cell.getColumn() < this.numCols))
/*  113:     */     {
/*  114: 305 */       if (this.cells[cell.getRow()][cell.getColumn()] != null)
/*  115:     */       {
/*  116: 307 */         StringBuffer sb = new StringBuffer();
/*  117: 308 */         CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow(), sb);
/*  118:     */         
/*  119: 310 */         logger.warn("Cell " + sb.toString() + " already contains data");
/*  120:     */       }
/*  121: 313 */       this.cells[cell.getRow()][cell.getColumn()] = cell;
/*  122:     */     }
/*  123:     */     else
/*  124:     */     {
/*  125: 317 */       this.outOfBoundsCells.add(cell);
/*  126:     */     }
/*  127:     */   }
/*  128:     */   
/*  129:     */   final void read()
/*  130:     */   {
/*  131: 333 */     Record r = null;
/*  132: 334 */     BaseSharedFormulaRecord sharedFormula = null;
/*  133: 335 */     boolean sharedFormulaAdded = false;
/*  134:     */     
/*  135: 337 */     boolean cont = true;
/*  136:     */     
/*  137:     */ 
/*  138: 340 */     this.excelFile.setPos(this.startPosition);
/*  139:     */     
/*  140:     */ 
/*  141: 343 */     MsoDrawingRecord msoRecord = null;
/*  142: 344 */     ObjRecord objRecord = null;
/*  143: 345 */     boolean firstMsoRecord = true;
/*  144:     */     
/*  145:     */ 
/*  146: 348 */     ConditionalFormat condFormat = null;
/*  147:     */     
/*  148:     */ 
/*  149: 351 */     FilterModeRecord filterMode = null;
/*  150: 352 */     AutoFilterInfoRecord autoFilterInfo = null;
/*  151:     */     
/*  152:     */ 
/*  153: 355 */     Window2Record window2Record = null;
/*  154:     */     
/*  155:     */ 
/*  156: 358 */     PrintGridLinesRecord printGridLinesRecord = null;
/*  157:     */     
/*  158:     */ 
/*  159: 361 */     PrintHeadersRecord printHeadersRecord = null;
/*  160:     */     
/*  161:     */ 
/*  162:     */ 
/*  163: 365 */     HashMap comments = new HashMap();
/*  164:     */     
/*  165:     */ 
/*  166: 368 */     ArrayList objectIds = new ArrayList();
/*  167:     */     
/*  168:     */ 
/*  169: 371 */     ContinueRecord continueRecord = null;
/*  170: 373 */     while (cont)
/*  171:     */     {
/*  172: 375 */       r = this.excelFile.next();
/*  173: 376 */       Type type = r.getType();
/*  174: 378 */       if ((type == Type.UNKNOWN) && (r.getCode() == 0))
/*  175:     */       {
/*  176: 380 */         logger.warn("Biff code zero found");
/*  177: 383 */         if (r.getLength() == 10)
/*  178:     */         {
/*  179: 385 */           logger.warn("Biff code zero found - trying a dimension record.");
/*  180: 386 */           r.setType(Type.DIMENSION);
/*  181:     */         }
/*  182:     */         else
/*  183:     */         {
/*  184: 390 */           logger.warn("Biff code zero found - Ignoring.");
/*  185:     */         }
/*  186:     */       }
/*  187: 394 */       if (type == Type.DIMENSION)
/*  188:     */       {
/*  189: 396 */         DimensionRecord dr = null;
/*  190: 398 */         if (this.workbookBof.isBiff8()) {
/*  191: 400 */           dr = new DimensionRecord(r);
/*  192:     */         } else {
/*  193: 404 */           dr = new DimensionRecord(r, DimensionRecord.biff7);
/*  194:     */         }
/*  195: 406 */         this.numRows = dr.getNumberOfRows();
/*  196: 407 */         this.numCols = dr.getNumberOfColumns();
/*  197: 408 */         this.cells = new Cell[this.numRows][this.numCols];
/*  198:     */       }
/*  199: 410 */       else if (type == Type.LABELSST)
/*  200:     */       {
/*  201: 412 */         LabelSSTRecord label = new LabelSSTRecord(r, this.sharedStrings, this.formattingRecords, this.sheet);
/*  202:     */         
/*  203:     */ 
/*  204:     */ 
/*  205: 416 */         addCell(label);
/*  206:     */       }
/*  207: 418 */       else if ((type == Type.RK) || (type == Type.RK2))
/*  208:     */       {
/*  209: 420 */         RKRecord rkr = new RKRecord(r, this.formattingRecords, this.sheet);
/*  210: 422 */         if (this.formattingRecords.isDate(rkr.getXFIndex()))
/*  211:     */         {
/*  212: 424 */           DateCell dc = new DateRecord(rkr, rkr.getXFIndex(), this.formattingRecords, this.nineteenFour, this.sheet);
/*  213:     */           
/*  214: 426 */           addCell(dc);
/*  215:     */         }
/*  216:     */         else
/*  217:     */         {
/*  218: 430 */           addCell(rkr);
/*  219:     */         }
/*  220:     */       }
/*  221: 433 */       else if (type == Type.HLINK)
/*  222:     */       {
/*  223: 435 */         HyperlinkRecord hr = new HyperlinkRecord(r, this.sheet, this.workbookSettings);
/*  224: 436 */         this.hyperlinks.add(hr);
/*  225:     */       }
/*  226: 438 */       else if (type == Type.MERGEDCELLS)
/*  227:     */       {
/*  228: 440 */         MergedCellsRecord mc = new MergedCellsRecord(r, this.sheet);
/*  229: 441 */         if (this.mergedCells == null)
/*  230:     */         {
/*  231: 443 */           this.mergedCells = mc.getRanges();
/*  232:     */         }
/*  233:     */         else
/*  234:     */         {
/*  235: 447 */           Range[] newMergedCells = new Range[this.mergedCells.length + mc.getRanges().length];
/*  236:     */           
/*  237: 449 */           System.arraycopy(this.mergedCells, 0, newMergedCells, 0, this.mergedCells.length);
/*  238:     */           
/*  239: 451 */           System.arraycopy(mc.getRanges(), 0, newMergedCells, this.mergedCells.length, mc.getRanges().length);
/*  240:     */           
/*  241:     */ 
/*  242:     */ 
/*  243: 455 */           this.mergedCells = newMergedCells;
/*  244:     */         }
/*  245:     */       }
/*  246: 458 */       else if (type == Type.MULRK)
/*  247:     */       {
/*  248: 460 */         MulRKRecord mulrk = new MulRKRecord(r);
/*  249:     */         
/*  250:     */ 
/*  251: 463 */         int num = mulrk.getNumberOfColumns();
/*  252: 464 */         int ixf = 0;
/*  253: 465 */         for (int i = 0; i < num; i++)
/*  254:     */         {
/*  255: 467 */           ixf = mulrk.getXFIndex(i);
/*  256:     */           
/*  257: 469 */           NumberValue nv = new NumberValue(mulrk.getRow(), mulrk.getFirstColumn() + i, RKHelper.getDouble(mulrk.getRKNumber(i)), ixf, this.formattingRecords, this.sheet);
/*  258: 478 */           if (this.formattingRecords.isDate(ixf))
/*  259:     */           {
/*  260: 480 */             DateCell dc = new DateRecord(nv, ixf, this.formattingRecords, this.nineteenFour, this.sheet);
/*  261:     */             
/*  262:     */ 
/*  263:     */ 
/*  264:     */ 
/*  265: 485 */             addCell(dc);
/*  266:     */           }
/*  267:     */           else
/*  268:     */           {
/*  269: 489 */             nv.setNumberFormat(this.formattingRecords.getNumberFormat(ixf));
/*  270: 490 */             addCell(nv);
/*  271:     */           }
/*  272:     */         }
/*  273:     */       }
/*  274: 494 */       else if (type == Type.NUMBER)
/*  275:     */       {
/*  276: 496 */         NumberRecord nr = new NumberRecord(r, this.formattingRecords, this.sheet);
/*  277: 498 */         if (this.formattingRecords.isDate(nr.getXFIndex()))
/*  278:     */         {
/*  279: 500 */           DateCell dc = new DateRecord(nr, nr.getXFIndex(), this.formattingRecords, this.nineteenFour, this.sheet);
/*  280:     */           
/*  281:     */ 
/*  282:     */ 
/*  283: 504 */           addCell(dc);
/*  284:     */         }
/*  285:     */         else
/*  286:     */         {
/*  287: 508 */           addCell(nr);
/*  288:     */         }
/*  289:     */       }
/*  290: 511 */       else if (type == Type.BOOLERR)
/*  291:     */       {
/*  292: 513 */         BooleanRecord br = new BooleanRecord(r, this.formattingRecords, this.sheet);
/*  293: 515 */         if (br.isError())
/*  294:     */         {
/*  295: 517 */           ErrorRecord er = new ErrorRecord(br.getRecord(), this.formattingRecords, this.sheet);
/*  296:     */           
/*  297: 519 */           addCell(er);
/*  298:     */         }
/*  299:     */         else
/*  300:     */         {
/*  301: 523 */           addCell(br);
/*  302:     */         }
/*  303:     */       }
/*  304: 526 */       else if (type == Type.PRINTGRIDLINES)
/*  305:     */       {
/*  306: 528 */         printGridLinesRecord = new PrintGridLinesRecord(r);
/*  307: 529 */         this.settings.setPrintGridLines(printGridLinesRecord.getPrintGridLines());
/*  308:     */       }
/*  309: 531 */       else if (type == Type.PRINTHEADERS)
/*  310:     */       {
/*  311: 533 */         printHeadersRecord = new PrintHeadersRecord(r);
/*  312: 534 */         this.settings.setPrintHeaders(printHeadersRecord.getPrintHeaders());
/*  313:     */       }
/*  314: 536 */       else if (type == Type.WINDOW2)
/*  315:     */       {
/*  316: 538 */         window2Record = null;
/*  317: 540 */         if (this.workbookBof.isBiff8()) {
/*  318: 542 */           window2Record = new Window2Record(r);
/*  319:     */         } else {
/*  320: 546 */           window2Record = new Window2Record(r, Window2Record.biff7);
/*  321:     */         }
/*  322: 549 */         this.settings.setShowGridLines(window2Record.getShowGridLines());
/*  323: 550 */         this.settings.setDisplayZeroValues(window2Record.getDisplayZeroValues());
/*  324: 551 */         this.settings.setSelected(true);
/*  325: 552 */         this.settings.setPageBreakPreviewMode(window2Record.isPageBreakPreview());
/*  326:     */       }
/*  327: 554 */       else if (type == Type.PANE)
/*  328:     */       {
/*  329: 556 */         PaneRecord pr = new PaneRecord(r);
/*  330: 558 */         if ((window2Record != null) && (window2Record.getFrozen()))
/*  331:     */         {
/*  332: 561 */           this.settings.setVerticalFreeze(pr.getRowsVisible());
/*  333: 562 */           this.settings.setHorizontalFreeze(pr.getColumnsVisible());
/*  334:     */         }
/*  335:     */       }
/*  336: 565 */       else if (type == Type.CONTINUE)
/*  337:     */       {
/*  338: 568 */         continueRecord = new ContinueRecord(r);
/*  339:     */       }
/*  340: 570 */       else if (type == Type.NOTE)
/*  341:     */       {
/*  342: 572 */         if (!this.workbookSettings.getDrawingsDisabled())
/*  343:     */         {
/*  344: 574 */           NoteRecord nr = new NoteRecord(r);
/*  345:     */           
/*  346:     */ 
/*  347: 577 */           Comment comment = (Comment)comments.remove(new Integer(nr.getObjectId()));
/*  348: 580 */           if (comment == null)
/*  349:     */           {
/*  350: 582 */             logger.warn(" cannot find comment for note id " + nr.getObjectId() + "...ignoring");
/*  351:     */           }
/*  352:     */           else
/*  353:     */           {
/*  354: 587 */             comment.setNote(nr);
/*  355:     */             
/*  356: 589 */             this.drawings.add(comment);
/*  357:     */             
/*  358: 591 */             addCellComment(comment.getColumn(), comment.getRow(), comment.getText(), comment.getWidth(), comment.getHeight());
/*  359:     */           }
/*  360:     */         }
/*  361:     */       }
/*  362: 599 */       else if (type != Type.ARRAY)
/*  363:     */       {
/*  364: 603 */         if (type == Type.PROTECT)
/*  365:     */         {
/*  366: 605 */           ProtectRecord pr = new ProtectRecord(r);
/*  367: 606 */           this.settings.setProtected(pr.isProtected());
/*  368:     */         }
/*  369: 608 */         else if (type == Type.SHAREDFORMULA)
/*  370:     */         {
/*  371: 610 */           if (sharedFormula == null)
/*  372:     */           {
/*  373: 612 */             logger.warn("Shared template formula is null - trying most recent formula template");
/*  374:     */             
/*  375: 614 */             SharedFormulaRecord lastSharedFormula = (SharedFormulaRecord)this.sharedFormulas.get(this.sharedFormulas.size() - 1);
/*  376: 617 */             if (lastSharedFormula != null) {
/*  377: 619 */               sharedFormula = lastSharedFormula.getTemplateFormula();
/*  378:     */             }
/*  379:     */           }
/*  380: 623 */           SharedFormulaRecord sfr = new SharedFormulaRecord(r, sharedFormula, this.workbook, this.workbook, this.sheet);
/*  381:     */           
/*  382: 625 */           this.sharedFormulas.add(sfr);
/*  383: 626 */           sharedFormula = null;
/*  384:     */         }
/*  385: 628 */         else if ((type == Type.FORMULA) || (type == Type.FORMULA2))
/*  386:     */         {
/*  387: 630 */           FormulaRecord fr = new FormulaRecord(r, this.excelFile, this.formattingRecords, this.workbook, this.workbook, this.sheet, this.workbookSettings);
/*  388: 638 */           if (fr.isShared())
/*  389:     */           {
/*  390: 640 */             BaseSharedFormulaRecord prevSharedFormula = sharedFormula;
/*  391: 641 */             sharedFormula = (BaseSharedFormulaRecord)fr.getFormula();
/*  392:     */             
/*  393:     */ 
/*  394: 644 */             sharedFormulaAdded = addToSharedFormulas(sharedFormula);
/*  395: 646 */             if (sharedFormulaAdded) {
/*  396: 648 */               sharedFormula = prevSharedFormula;
/*  397:     */             }
/*  398: 653 */             if ((!sharedFormulaAdded) && (prevSharedFormula != null)) {
/*  399: 660 */               addCell(revertSharedFormula(prevSharedFormula));
/*  400:     */             }
/*  401:     */           }
/*  402:     */           else
/*  403:     */           {
/*  404: 665 */             Cell cell = fr.getFormula();
/*  405:     */             try
/*  406:     */             {
/*  407: 669 */               if (fr.getFormula().getType() == CellType.NUMBER_FORMULA)
/*  408:     */               {
/*  409: 671 */                 NumberFormulaRecord nfr = (NumberFormulaRecord)fr.getFormula();
/*  410: 672 */                 if (this.formattingRecords.isDate(nfr.getXFIndex())) {
/*  411: 674 */                   cell = new DateFormulaRecord(nfr, this.formattingRecords, this.workbook, this.workbook, this.nineteenFour, this.sheet);
/*  412:     */                 }
/*  413:     */               }
/*  414: 683 */               addCell(cell);
/*  415:     */             }
/*  416:     */             catch (FormulaException e)
/*  417:     */             {
/*  418: 689 */               logger.warn(CellReferenceHelper.getCellReference(cell.getColumn(), cell.getRow()) + " " + e.getMessage());
/*  419:     */             }
/*  420:     */           }
/*  421:     */         }
/*  422: 695 */         else if (type == Type.LABEL)
/*  423:     */         {
/*  424: 697 */           LabelRecord lr = null;
/*  425: 699 */           if (this.workbookBof.isBiff8()) {
/*  426: 701 */             lr = new LabelRecord(r, this.formattingRecords, this.sheet, this.workbookSettings);
/*  427:     */           } else {
/*  428: 705 */             lr = new LabelRecord(r, this.formattingRecords, this.sheet, this.workbookSettings, LabelRecord.biff7);
/*  429:     */           }
/*  430: 708 */           addCell(lr);
/*  431:     */         }
/*  432: 710 */         else if (type == Type.RSTRING)
/*  433:     */         {
/*  434: 712 */           RStringRecord lr = null;
/*  435:     */           
/*  436:     */ 
/*  437: 715 */           Assert.verify(!this.workbookBof.isBiff8());
/*  438: 716 */           lr = new RStringRecord(r, this.formattingRecords, this.sheet, this.workbookSettings, RStringRecord.biff7);
/*  439:     */           
/*  440:     */ 
/*  441: 719 */           addCell(lr);
/*  442:     */         }
/*  443: 721 */         else if (type != Type.NAME)
/*  444:     */         {
/*  445: 725 */           if (type == Type.PASSWORD)
/*  446:     */           {
/*  447: 727 */             PasswordRecord pr = new PasswordRecord(r);
/*  448: 728 */             this.settings.setPasswordHash(pr.getPasswordHash());
/*  449:     */           }
/*  450: 730 */           else if (type == Type.ROW)
/*  451:     */           {
/*  452: 732 */             RowRecord rr = new RowRecord(r);
/*  453: 735 */             if ((!rr.isDefaultHeight()) || (!rr.matchesDefaultFontHeight()) || (rr.isCollapsed()) || (rr.hasDefaultFormat()) || (rr.getOutlineLevel() != 0)) {
/*  454: 741 */               this.rowProperties.add(rr);
/*  455:     */             }
/*  456:     */           }
/*  457: 744 */           else if (type == Type.BLANK)
/*  458:     */           {
/*  459: 746 */             if (!this.workbookSettings.getIgnoreBlanks())
/*  460:     */             {
/*  461: 748 */               BlankCell bc = new BlankCell(r, this.formattingRecords, this.sheet);
/*  462: 749 */               addCell(bc);
/*  463:     */             }
/*  464:     */           }
/*  465: 752 */           else if (type == Type.MULBLANK)
/*  466:     */           {
/*  467: 754 */             if (!this.workbookSettings.getIgnoreBlanks())
/*  468:     */             {
/*  469: 756 */               MulBlankRecord mulblank = new MulBlankRecord(r);
/*  470:     */               
/*  471:     */ 
/*  472: 759 */               int num = mulblank.getNumberOfColumns();
/*  473: 761 */               for (int i = 0; i < num; i++)
/*  474:     */               {
/*  475: 763 */                 int ixf = mulblank.getXFIndex(i);
/*  476:     */                 
/*  477: 765 */                 MulBlankCell mbc = new MulBlankCell(mulblank.getRow(), mulblank.getFirstColumn() + i, ixf, this.formattingRecords, this.sheet);
/*  478:     */                 
/*  479:     */ 
/*  480:     */ 
/*  481:     */ 
/*  482:     */ 
/*  483:     */ 
/*  484: 772 */                 addCell(mbc);
/*  485:     */               }
/*  486:     */             }
/*  487:     */           }
/*  488: 776 */           else if (type == Type.SCL)
/*  489:     */           {
/*  490: 778 */             SCLRecord scl = new SCLRecord(r);
/*  491: 779 */             this.settings.setZoomFactor(scl.getZoomFactor());
/*  492:     */           }
/*  493: 781 */           else if (type == Type.COLINFO)
/*  494:     */           {
/*  495: 783 */             ColumnInfoRecord cir = new ColumnInfoRecord(r);
/*  496: 784 */             this.columnInfosArray.add(cir);
/*  497:     */           }
/*  498: 786 */           else if (type == Type.HEADER)
/*  499:     */           {
/*  500: 788 */             HeaderRecord hr = null;
/*  501: 789 */             if (this.workbookBof.isBiff8()) {
/*  502: 791 */               hr = new HeaderRecord(r, this.workbookSettings);
/*  503:     */             } else {
/*  504: 795 */               hr = new HeaderRecord(r, this.workbookSettings, HeaderRecord.biff7);
/*  505:     */             }
/*  506: 798 */             HeaderFooter header = new HeaderFooter(hr.getHeader());
/*  507: 799 */             this.settings.setHeader(header);
/*  508:     */           }
/*  509: 801 */           else if (type == Type.FOOTER)
/*  510:     */           {
/*  511: 803 */             FooterRecord fr = null;
/*  512: 804 */             if (this.workbookBof.isBiff8()) {
/*  513: 806 */               fr = new FooterRecord(r, this.workbookSettings);
/*  514:     */             } else {
/*  515: 810 */               fr = new FooterRecord(r, this.workbookSettings, FooterRecord.biff7);
/*  516:     */             }
/*  517: 813 */             HeaderFooter footer = new HeaderFooter(fr.getFooter());
/*  518: 814 */             this.settings.setFooter(footer);
/*  519:     */           }
/*  520: 816 */           else if (type == Type.SETUP)
/*  521:     */           {
/*  522: 818 */             SetupRecord sr = new SetupRecord(r);
/*  523: 822 */             if (sr.getInitialized())
/*  524:     */             {
/*  525: 824 */               if (sr.isPortrait()) {
/*  526: 826 */                 this.settings.setOrientation(PageOrientation.PORTRAIT);
/*  527:     */               } else {
/*  528: 830 */                 this.settings.setOrientation(PageOrientation.LANDSCAPE);
/*  529:     */               }
/*  530: 832 */               if (sr.isRightDown()) {
/*  531: 834 */                 this.settings.setPageOrder(PageOrder.RIGHT_THEN_DOWN);
/*  532:     */               } else {
/*  533: 838 */                 this.settings.setPageOrder(PageOrder.DOWN_THEN_RIGHT);
/*  534:     */               }
/*  535: 840 */               this.settings.setPaperSize(PaperSize.getPaperSize(sr.getPaperSize()));
/*  536: 841 */               this.settings.setHeaderMargin(sr.getHeaderMargin());
/*  537: 842 */               this.settings.setFooterMargin(sr.getFooterMargin());
/*  538: 843 */               this.settings.setScaleFactor(sr.getScaleFactor());
/*  539: 844 */               this.settings.setPageStart(sr.getPageStart());
/*  540: 845 */               this.settings.setFitWidth(sr.getFitWidth());
/*  541: 846 */               this.settings.setFitHeight(sr.getFitHeight());
/*  542: 847 */               this.settings.setHorizontalPrintResolution(sr.getHorizontalPrintResolution());
/*  543:     */               
/*  544: 849 */               this.settings.setVerticalPrintResolution(sr.getVerticalPrintResolution());
/*  545: 850 */               this.settings.setCopies(sr.getCopies());
/*  546: 852 */               if (this.workspaceOptions != null) {
/*  547: 854 */                 this.settings.setFitToPages(this.workspaceOptions.getFitToPages());
/*  548:     */               }
/*  549:     */             }
/*  550:     */           }
/*  551: 858 */           else if (type == Type.WSBOOL)
/*  552:     */           {
/*  553: 860 */             this.workspaceOptions = new WorkspaceInformationRecord(r);
/*  554:     */           }
/*  555: 862 */           else if (type == Type.DEFCOLWIDTH)
/*  556:     */           {
/*  557: 864 */             DefaultColumnWidthRecord dcwr = new DefaultColumnWidthRecord(r);
/*  558: 865 */             this.settings.setDefaultColumnWidth(dcwr.getWidth());
/*  559:     */           }
/*  560: 867 */           else if (type == Type.DEFAULTROWHEIGHT)
/*  561:     */           {
/*  562: 869 */             DefaultRowHeightRecord drhr = new DefaultRowHeightRecord(r);
/*  563: 870 */             if (drhr.getHeight() != 0) {
/*  564: 872 */               this.settings.setDefaultRowHeight(drhr.getHeight());
/*  565:     */             }
/*  566:     */           }
/*  567: 875 */           else if (type == Type.CONDFMT)
/*  568:     */           {
/*  569: 877 */             ConditionalFormatRangeRecord cfrr = new ConditionalFormatRangeRecord(r);
/*  570:     */             
/*  571: 879 */             condFormat = new ConditionalFormat(cfrr);
/*  572: 880 */             this.conditionalFormats.add(condFormat);
/*  573:     */           }
/*  574: 882 */           else if (type == Type.CF)
/*  575:     */           {
/*  576: 884 */             ConditionalFormatRecord cfr = new ConditionalFormatRecord(r);
/*  577: 885 */             condFormat.addCondition(cfr);
/*  578:     */           }
/*  579: 887 */           else if (type == Type.FILTERMODE)
/*  580:     */           {
/*  581: 889 */             filterMode = new FilterModeRecord(r);
/*  582:     */           }
/*  583: 891 */           else if (type == Type.AUTOFILTERINFO)
/*  584:     */           {
/*  585: 893 */             autoFilterInfo = new AutoFilterInfoRecord(r);
/*  586:     */           }
/*  587: 895 */           else if (type == Type.AUTOFILTER)
/*  588:     */           {
/*  589: 897 */             if (!this.workbookSettings.getAutoFilterDisabled())
/*  590:     */             {
/*  591: 899 */               AutoFilterRecord af = new AutoFilterRecord(r);
/*  592: 901 */               if (this.autoFilter == null)
/*  593:     */               {
/*  594: 903 */                 this.autoFilter = new AutoFilter(filterMode, autoFilterInfo);
/*  595: 904 */                 filterMode = null;
/*  596: 905 */                 autoFilterInfo = null;
/*  597:     */               }
/*  598: 908 */               this.autoFilter.add(af);
/*  599:     */             }
/*  600:     */           }
/*  601: 911 */           else if (type == Type.LEFTMARGIN)
/*  602:     */           {
/*  603: 913 */             MarginRecord m = new LeftMarginRecord(r);
/*  604: 914 */             this.settings.setLeftMargin(m.getMargin());
/*  605:     */           }
/*  606: 916 */           else if (type == Type.RIGHTMARGIN)
/*  607:     */           {
/*  608: 918 */             MarginRecord m = new RightMarginRecord(r);
/*  609: 919 */             this.settings.setRightMargin(m.getMargin());
/*  610:     */           }
/*  611: 921 */           else if (type == Type.TOPMARGIN)
/*  612:     */           {
/*  613: 923 */             MarginRecord m = new TopMarginRecord(r);
/*  614: 924 */             this.settings.setTopMargin(m.getMargin());
/*  615:     */           }
/*  616: 926 */           else if (type == Type.BOTTOMMARGIN)
/*  617:     */           {
/*  618: 928 */             MarginRecord m = new BottomMarginRecord(r);
/*  619: 929 */             this.settings.setBottomMargin(m.getMargin());
/*  620:     */           }
/*  621: 931 */           else if (type == Type.HORIZONTALPAGEBREAKS)
/*  622:     */           {
/*  623: 933 */             HorizontalPageBreaksRecord dr = null;
/*  624: 935 */             if (this.workbookBof.isBiff8()) {
/*  625: 937 */               dr = new HorizontalPageBreaksRecord(r);
/*  626:     */             } else {
/*  627: 941 */               dr = new HorizontalPageBreaksRecord(r, HorizontalPageBreaksRecord.biff7);
/*  628:     */             }
/*  629: 944 */             this.rowBreaks = dr.getRowBreaks();
/*  630:     */           }
/*  631: 946 */           else if (type == Type.VERTICALPAGEBREAKS)
/*  632:     */           {
/*  633: 948 */             VerticalPageBreaksRecord dr = null;
/*  634: 950 */             if (this.workbookBof.isBiff8()) {
/*  635: 952 */               dr = new VerticalPageBreaksRecord(r);
/*  636:     */             } else {
/*  637: 956 */               dr = new VerticalPageBreaksRecord(r, VerticalPageBreaksRecord.biff7);
/*  638:     */             }
/*  639: 959 */             this.columnBreaks = dr.getColumnBreaks();
/*  640:     */           }
/*  641:     */           else
/*  642:     */           {
/*  643: 961 */             if (type == Type.PLS)
/*  644:     */             {
/*  645: 963 */               this.plsRecord = new PLSRecord(r);
/*  646: 966 */               while (this.excelFile.peek().getType() == Type.CONTINUE) {
/*  647: 968 */                 r.addContinueRecord(this.excelFile.next());
/*  648:     */               }
/*  649:     */             }
/*  650: 971 */             if (type == Type.DVAL)
/*  651:     */             {
/*  652: 973 */               if (!this.workbookSettings.getCellValidationDisabled())
/*  653:     */               {
/*  654: 975 */                 DataValidityListRecord dvlr = new DataValidityListRecord(r);
/*  655: 976 */                 if (dvlr.getObjectId() == -1)
/*  656:     */                 {
/*  657: 978 */                   if ((msoRecord != null) && (objRecord == null))
/*  658:     */                   {
/*  659: 981 */                     if (this.drawingData == null) {
/*  660: 983 */                       this.drawingData = new DrawingData();
/*  661:     */                     }
/*  662: 986 */                     Drawing2 d2 = new Drawing2(msoRecord, this.drawingData, this.workbook.getDrawingGroup());
/*  663:     */                     
/*  664: 988 */                     this.drawings.add(d2);
/*  665: 989 */                     msoRecord = null;
/*  666:     */                     
/*  667: 991 */                     this.dataValidation = new DataValidation(dvlr);
/*  668:     */                   }
/*  669:     */                   else
/*  670:     */                   {
/*  671: 996 */                     this.dataValidation = new DataValidation(dvlr);
/*  672:     */                   }
/*  673:     */                 }
/*  674: 999 */                 else if (objectIds.contains(new Integer(dvlr.getObjectId()))) {
/*  675:1001 */                   this.dataValidation = new DataValidation(dvlr);
/*  676:     */                 } else {
/*  677:1005 */                   logger.warn("object id " + dvlr.getObjectId() + " referenced " + " by data validity list record not found - ignoring");
/*  678:     */                 }
/*  679:     */               }
/*  680:     */             }
/*  681:1010 */             else if (type == Type.HCENTER)
/*  682:     */             {
/*  683:1012 */               CentreRecord hr = new CentreRecord(r);
/*  684:1013 */               this.settings.setHorizontalCentre(hr.isCentre());
/*  685:     */             }
/*  686:1015 */             else if (type == Type.VCENTER)
/*  687:     */             {
/*  688:1017 */               CentreRecord vc = new CentreRecord(r);
/*  689:1018 */               this.settings.setVerticalCentre(vc.isCentre());
/*  690:     */             }
/*  691:1020 */             else if (type == Type.DV)
/*  692:     */             {
/*  693:1022 */               if (!this.workbookSettings.getCellValidationDisabled())
/*  694:     */               {
/*  695:1024 */                 DataValiditySettingsRecord dvsr = new DataValiditySettingsRecord(r, this.workbook, this.workbook, this.workbook.getSettings());
/*  696:1029 */                 if (this.dataValidation != null)
/*  697:     */                 {
/*  698:1031 */                   this.dataValidation.add(dvsr);
/*  699:1032 */                   addCellValidation(dvsr.getFirstColumn(), dvsr.getFirstRow(), dvsr.getLastColumn(), dvsr.getLastRow(), dvsr);
/*  700:     */                 }
/*  701:     */                 else
/*  702:     */                 {
/*  703:1040 */                   logger.warn("cannot add data validity settings");
/*  704:     */                 }
/*  705:     */               }
/*  706:     */             }
/*  707:1044 */             else if (type == Type.OBJ)
/*  708:     */             {
/*  709:1046 */               objRecord = new ObjRecord(r);
/*  710:1048 */               if (!this.workbookSettings.getDrawingsDisabled())
/*  711:     */               {
/*  712:1053 */                 if ((msoRecord == null) && (continueRecord != null))
/*  713:     */                 {
/*  714:1055 */                   logger.warn("Cannot find drawing record - using continue record");
/*  715:1056 */                   msoRecord = new MsoDrawingRecord(continueRecord.getRecord());
/*  716:1057 */                   continueRecord = null;
/*  717:     */                 }
/*  718:1059 */                 handleObjectRecord(objRecord, msoRecord, comments);
/*  719:1060 */                 objectIds.add(new Integer(objRecord.getObjectId()));
/*  720:     */               }
/*  721:1064 */               if (objRecord.getType() != ObjRecord.CHART)
/*  722:     */               {
/*  723:1066 */                 objRecord = null;
/*  724:1067 */                 msoRecord = null;
/*  725:     */               }
/*  726:     */             }
/*  727:1070 */             else if (type == Type.MSODRAWING)
/*  728:     */             {
/*  729:1072 */               if (!this.workbookSettings.getDrawingsDisabled())
/*  730:     */               {
/*  731:1074 */                 if (msoRecord != null) {
/*  732:1078 */                   this.drawingData.addRawData(msoRecord.getData());
/*  733:     */                 }
/*  734:1080 */                 msoRecord = new MsoDrawingRecord(r);
/*  735:1082 */                 if (firstMsoRecord)
/*  736:     */                 {
/*  737:1084 */                   msoRecord.setFirst();
/*  738:1085 */                   firstMsoRecord = false;
/*  739:     */                 }
/*  740:     */               }
/*  741:     */             }
/*  742:1089 */             else if (type == Type.BUTTONPROPERTYSET)
/*  743:     */             {
/*  744:1091 */               this.buttonPropertySet = new ButtonPropertySetRecord(r);
/*  745:     */             }
/*  746:1093 */             else if (type == Type.CALCMODE)
/*  747:     */             {
/*  748:1095 */               CalcModeRecord cmr = new CalcModeRecord(r);
/*  749:1096 */               this.settings.setAutomaticFormulaCalculation(cmr.isAutomatic());
/*  750:     */             }
/*  751:1098 */             else if (type == Type.SAVERECALC)
/*  752:     */             {
/*  753:1100 */               SaveRecalcRecord cmr = new SaveRecalcRecord(r);
/*  754:1101 */               this.settings.setRecalculateFormulasBeforeSave(cmr.getRecalculateOnSave());
/*  755:     */             }
/*  756:1103 */             else if (type == Type.GUTS)
/*  757:     */             {
/*  758:1105 */               GuttersRecord gr = new GuttersRecord(r);
/*  759:1106 */               this.maxRowOutlineLevel = (gr.getRowOutlineLevel() > 0 ? gr.getRowOutlineLevel() - 1 : 0);
/*  760:     */               
/*  761:1108 */               this.maxColumnOutlineLevel = (gr.getColumnOutlineLevel() > 0 ? gr.getRowOutlineLevel() - 1 : 0);
/*  762:     */             }
/*  763:1111 */             else if (type == Type.BOF)
/*  764:     */             {
/*  765:1113 */               BOFRecord br = new BOFRecord(r);
/*  766:1114 */               Assert.verify(!br.isWorksheet());
/*  767:     */               
/*  768:1116 */               int startpos = this.excelFile.getPos() - r.getLength() - 4;
/*  769:     */               
/*  770:     */ 
/*  771:     */ 
/*  772:1120 */               Record r2 = this.excelFile.next();
/*  773:1121 */               while (r2.getCode() != Type.EOF.value) {
/*  774:1123 */                 r2 = this.excelFile.next();
/*  775:     */               }
/*  776:1126 */               if (br.isChart())
/*  777:     */               {
/*  778:1128 */                 if (!this.workbook.getWorkbookBof().isBiff8())
/*  779:     */                 {
/*  780:1130 */                   logger.warn("only biff8 charts are supported");
/*  781:     */                 }
/*  782:     */                 else
/*  783:     */                 {
/*  784:1134 */                   if (this.drawingData == null) {
/*  785:1136 */                     this.drawingData = new DrawingData();
/*  786:     */                   }
/*  787:1139 */                   if (!this.workbookSettings.getDrawingsDisabled())
/*  788:     */                   {
/*  789:1141 */                     Chart chart = new Chart(msoRecord, objRecord, this.drawingData, startpos, this.excelFile.getPos(), this.excelFile, this.workbookSettings);
/*  790:     */                     
/*  791:     */ 
/*  792:1144 */                     this.charts.add(chart);
/*  793:1146 */                     if (this.workbook.getDrawingGroup() != null) {
/*  794:1148 */                       this.workbook.getDrawingGroup().add(chart);
/*  795:     */                     }
/*  796:     */                   }
/*  797:     */                 }
/*  798:1154 */                 msoRecord = null;
/*  799:1155 */                 objRecord = null;
/*  800:     */               }
/*  801:1160 */               if (this.sheetBof.isChart()) {
/*  802:1162 */                 cont = false;
/*  803:     */               }
/*  804:     */             }
/*  805:1165 */             else if (type == Type.EOF)
/*  806:     */             {
/*  807:1167 */               cont = false;
/*  808:     */             }
/*  809:     */           }
/*  810:     */         }
/*  811:     */       }
/*  812:     */     }
/*  813:1172 */     this.excelFile.restorePos();
/*  814:1175 */     if (this.outOfBoundsCells.size() > 0) {
/*  815:1177 */       handleOutOfBoundsCells();
/*  816:     */     }
/*  817:1181 */     Iterator i = this.sharedFormulas.iterator();
/*  818:1183 */     while (i.hasNext())
/*  819:     */     {
/*  820:1185 */       SharedFormulaRecord sfr = (SharedFormulaRecord)i.next();
/*  821:     */       
/*  822:1187 */       Cell[] sfnr = sfr.getFormulas(this.formattingRecords, this.nineteenFour);
/*  823:1189 */       for (int sf = 0; sf < sfnr.length; sf++) {
/*  824:1191 */         addCell(sfnr[sf]);
/*  825:     */       }
/*  826:     */     }
/*  827:1197 */     if ((!sharedFormulaAdded) && (sharedFormula != null)) {
/*  828:1199 */       addCell(revertSharedFormula(sharedFormula));
/*  829:     */     }
/*  830:1204 */     if ((msoRecord != null) && (this.workbook.getDrawingGroup() != null)) {
/*  831:1206 */       this.workbook.getDrawingGroup().setDrawingsOmitted(msoRecord, objRecord);
/*  832:     */     }
/*  833:1210 */     if (!comments.isEmpty()) {
/*  834:1212 */       logger.warn("Not all comments have a corresponding Note record");
/*  835:     */     }
/*  836:     */   }
/*  837:     */   
/*  838:     */   private boolean addToSharedFormulas(BaseSharedFormulaRecord fr)
/*  839:     */   {
/*  840:1225 */     boolean added = false;
/*  841:1226 */     SharedFormulaRecord sfr = null;
/*  842:     */     
/*  843:1228 */     int i = 0;
/*  844:1228 */     for (int size = this.sharedFormulas.size(); (i < size) && (!added); i++)
/*  845:     */     {
/*  846:1230 */       sfr = (SharedFormulaRecord)this.sharedFormulas.get(i);
/*  847:1231 */       added = sfr.add(fr);
/*  848:     */     }
/*  849:1234 */     return added;
/*  850:     */   }
/*  851:     */   
/*  852:     */   private Cell revertSharedFormula(BaseSharedFormulaRecord f)
/*  853:     */   {
/*  854:1250 */     int pos = this.excelFile.getPos();
/*  855:1251 */     this.excelFile.setPos(f.getFilePos());
/*  856:     */     
/*  857:1253 */     FormulaRecord fr = new FormulaRecord(f.getRecord(), this.excelFile, this.formattingRecords, this.workbook, this.workbook, FormulaRecord.ignoreSharedFormula, this.sheet, this.workbookSettings);
/*  858:     */     try
/*  859:     */     {
/*  860:1264 */       Cell cell = fr.getFormula();
/*  861:1267 */       if (fr.getFormula().getType() == CellType.NUMBER_FORMULA)
/*  862:     */       {
/*  863:1269 */         NumberFormulaRecord nfr = (NumberFormulaRecord)fr.getFormula();
/*  864:1270 */         if (this.formattingRecords.isDate(fr.getXFIndex())) {
/*  865:1272 */           cell = new DateFormulaRecord(nfr, this.formattingRecords, this.workbook, this.workbook, this.nineteenFour, this.sheet);
/*  866:     */         }
/*  867:     */       }
/*  868:1281 */       this.excelFile.setPos(pos);
/*  869:1282 */       return cell;
/*  870:     */     }
/*  871:     */     catch (FormulaException e)
/*  872:     */     {
/*  873:1288 */       logger.warn(CellReferenceHelper.getCellReference(fr.getColumn(), fr.getRow()) + " " + e.getMessage());
/*  874:     */     }
/*  875:1292 */     return null;
/*  876:     */   }
/*  877:     */   
/*  878:     */   final int getNumRows()
/*  879:     */   {
/*  880:1304 */     return this.numRows;
/*  881:     */   }
/*  882:     */   
/*  883:     */   final int getNumCols()
/*  884:     */   {
/*  885:1314 */     return this.numCols;
/*  886:     */   }
/*  887:     */   
/*  888:     */   final Cell[][] getCells()
/*  889:     */   {
/*  890:1324 */     return this.cells;
/*  891:     */   }
/*  892:     */   
/*  893:     */   final ArrayList getRowProperties()
/*  894:     */   {
/*  895:1334 */     return this.rowProperties;
/*  896:     */   }
/*  897:     */   
/*  898:     */   final ArrayList getColumnInfosArray()
/*  899:     */   {
/*  900:1344 */     return this.columnInfosArray;
/*  901:     */   }
/*  902:     */   
/*  903:     */   final ArrayList getHyperlinks()
/*  904:     */   {
/*  905:1354 */     return this.hyperlinks;
/*  906:     */   }
/*  907:     */   
/*  908:     */   final ArrayList getConditionalFormats()
/*  909:     */   {
/*  910:1364 */     return this.conditionalFormats;
/*  911:     */   }
/*  912:     */   
/*  913:     */   final AutoFilter getAutoFilter()
/*  914:     */   {
/*  915:1374 */     return this.autoFilter;
/*  916:     */   }
/*  917:     */   
/*  918:     */   final ArrayList getCharts()
/*  919:     */   {
/*  920:1384 */     return this.charts;
/*  921:     */   }
/*  922:     */   
/*  923:     */   final ArrayList getDrawings()
/*  924:     */   {
/*  925:1394 */     return this.drawings;
/*  926:     */   }
/*  927:     */   
/*  928:     */   final DataValidation getDataValidation()
/*  929:     */   {
/*  930:1404 */     return this.dataValidation;
/*  931:     */   }
/*  932:     */   
/*  933:     */   final Range[] getMergedCells()
/*  934:     */   {
/*  935:1414 */     return this.mergedCells;
/*  936:     */   }
/*  937:     */   
/*  938:     */   final SheetSettings getSettings()
/*  939:     */   {
/*  940:1424 */     return this.settings;
/*  941:     */   }
/*  942:     */   
/*  943:     */   final int[] getRowBreaks()
/*  944:     */   {
/*  945:1434 */     return this.rowBreaks;
/*  946:     */   }
/*  947:     */   
/*  948:     */   final int[] getColumnBreaks()
/*  949:     */   {
/*  950:1444 */     return this.columnBreaks;
/*  951:     */   }
/*  952:     */   
/*  953:     */   final WorkspaceInformationRecord getWorkspaceOptions()
/*  954:     */   {
/*  955:1454 */     return this.workspaceOptions;
/*  956:     */   }
/*  957:     */   
/*  958:     */   final PLSRecord getPLS()
/*  959:     */   {
/*  960:1464 */     return this.plsRecord;
/*  961:     */   }
/*  962:     */   
/*  963:     */   final ButtonPropertySetRecord getButtonPropertySet()
/*  964:     */   {
/*  965:1474 */     return this.buttonPropertySet;
/*  966:     */   }
/*  967:     */   
/*  968:     */   private void addCellComment(int col, int row, String text, double width, double height)
/*  969:     */   {
/*  970:1492 */     Cell c = this.cells[row][col];
/*  971:1493 */     if (c == null)
/*  972:     */     {
/*  973:1495 */       logger.warn("Cell at " + CellReferenceHelper.getCellReference(col, row) + " not present - adding a blank");
/*  974:     */       
/*  975:1497 */       MulBlankCell mbc = new MulBlankCell(row, col, 0, this.formattingRecords, this.sheet);
/*  976:     */       
/*  977:     */ 
/*  978:     */ 
/*  979:     */ 
/*  980:1502 */       CellFeatures cf = new CellFeatures();
/*  981:1503 */       cf.setReadComment(text, width, height);
/*  982:1504 */       mbc.setCellFeatures(cf);
/*  983:1505 */       addCell(mbc);
/*  984:     */       
/*  985:1507 */       return;
/*  986:     */     }
/*  987:1510 */     if ((c instanceof CellFeaturesAccessor))
/*  988:     */     {
/*  989:1512 */       CellFeaturesAccessor cv = (CellFeaturesAccessor)c;
/*  990:1513 */       CellFeatures cf = cv.getCellFeatures();
/*  991:1515 */       if (cf == null)
/*  992:     */       {
/*  993:1517 */         cf = new CellFeatures();
/*  994:1518 */         cv.setCellFeatures(cf);
/*  995:     */       }
/*  996:1521 */       cf.setReadComment(text, width, height);
/*  997:     */     }
/*  998:     */     else
/*  999:     */     {
/* 1000:1525 */       logger.warn("Not able to add comment to cell type " + c.getClass().getName() + " at " + CellReferenceHelper.getCellReference(col, row));
/* 1001:     */     }
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   private void addCellValidation(int col1, int row1, int col2, int row2, DataValiditySettingsRecord dvsr)
/* 1005:     */   {
/* 1006:1546 */     for (int row = row1; row <= row2; row++) {
/* 1007:1548 */       for (int col = col1; col <= col2; col++)
/* 1008:     */       {
/* 1009:1550 */         Cell c = null;
/* 1010:1552 */         if ((this.cells.length > row) && (this.cells[row].length > col)) {
/* 1011:1554 */           c = this.cells[row][col];
/* 1012:     */         }
/* 1013:1557 */         if (c == null)
/* 1014:     */         {
/* 1015:1559 */           MulBlankCell mbc = new MulBlankCell(row, col, 0, this.formattingRecords, this.sheet);
/* 1016:     */           
/* 1017:     */ 
/* 1018:     */ 
/* 1019:     */ 
/* 1020:1564 */           CellFeatures cf = new CellFeatures();
/* 1021:1565 */           cf.setValidationSettings(dvsr);
/* 1022:1566 */           mbc.setCellFeatures(cf);
/* 1023:1567 */           addCell(mbc);
/* 1024:     */         }
/* 1025:1569 */         else if ((c instanceof CellFeaturesAccessor))
/* 1026:     */         {
/* 1027:1571 */           CellFeaturesAccessor cv = (CellFeaturesAccessor)c;
/* 1028:1572 */           CellFeatures cf = cv.getCellFeatures();
/* 1029:1574 */           if (cf == null)
/* 1030:     */           {
/* 1031:1576 */             cf = new CellFeatures();
/* 1032:1577 */             cv.setCellFeatures(cf);
/* 1033:     */           }
/* 1034:1580 */           cf.setValidationSettings(dvsr);
/* 1035:     */         }
/* 1036:     */         else
/* 1037:     */         {
/* 1038:1584 */           logger.warn("Not able to add comment to cell type " + c.getClass().getName() + " at " + CellReferenceHelper.getCellReference(col, row));
/* 1039:     */         }
/* 1040:     */       }
/* 1041:     */     }
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   private void handleObjectRecord(ObjRecord objRecord, MsoDrawingRecord msoRecord, HashMap comments)
/* 1045:     */   {
/* 1046:1603 */     if (msoRecord == null)
/* 1047:     */     {
/* 1048:1605 */       logger.warn("Object record is not associated with a drawing  record - ignoring");
/* 1049:     */       
/* 1050:1607 */       return;
/* 1051:     */     }
/* 1052:     */     try
/* 1053:     */     {
/* 1054:1613 */       if (objRecord.getType() == ObjRecord.PICTURE)
/* 1055:     */       {
/* 1056:1615 */         if (this.drawingData == null) {
/* 1057:1617 */           this.drawingData = new DrawingData();
/* 1058:     */         }
/* 1059:1620 */         Drawing drawing = new Drawing(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.sheet);
/* 1060:     */         
/* 1061:     */ 
/* 1062:     */ 
/* 1063:     */ 
/* 1064:1625 */         this.drawings.add(drawing);
/* 1065:1626 */         return;
/* 1066:     */       }
/* 1067:1630 */       if (objRecord.getType() == ObjRecord.EXCELNOTE)
/* 1068:     */       {
/* 1069:1632 */         if (this.drawingData == null) {
/* 1070:1634 */           this.drawingData = new DrawingData();
/* 1071:     */         }
/* 1072:1637 */         Comment comment = new Comment(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
/* 1073:     */         
/* 1074:     */ 
/* 1075:     */ 
/* 1076:     */ 
/* 1077:     */ 
/* 1078:     */ 
/* 1079:     */ 
/* 1080:1645 */         Record r2 = this.excelFile.next();
/* 1081:1646 */         if ((r2.getType() == Type.MSODRAWING) || (r2.getType() == Type.CONTINUE))
/* 1082:     */         {
/* 1083:1648 */           MsoDrawingRecord mso = new MsoDrawingRecord(r2);
/* 1084:1649 */           comment.addMso(mso);
/* 1085:1650 */           r2 = this.excelFile.next();
/* 1086:     */         }
/* 1087:1652 */         Assert.verify(r2.getType() == Type.TXO);
/* 1088:1653 */         TextObjectRecord txo = new TextObjectRecord(r2);
/* 1089:1654 */         comment.setTextObject(txo);
/* 1090:     */         
/* 1091:1656 */         r2 = this.excelFile.next();
/* 1092:1657 */         Assert.verify(r2.getType() == Type.CONTINUE);
/* 1093:1658 */         ContinueRecord text = new ContinueRecord(r2);
/* 1094:1659 */         comment.setText(text);
/* 1095:     */         
/* 1096:1661 */         r2 = this.excelFile.next();
/* 1097:1662 */         if (r2.getType() == Type.CONTINUE)
/* 1098:     */         {
/* 1099:1664 */           ContinueRecord formatting = new ContinueRecord(r2);
/* 1100:1665 */           comment.setFormatting(formatting);
/* 1101:     */         }
/* 1102:1668 */         comments.put(new Integer(comment.getObjectId()), comment);
/* 1103:1669 */         return;
/* 1104:     */       }
/* 1105:1673 */       if (objRecord.getType() == ObjRecord.COMBOBOX)
/* 1106:     */       {
/* 1107:1675 */         if (this.drawingData == null) {
/* 1108:1677 */           this.drawingData = new DrawingData();
/* 1109:     */         }
/* 1110:1680 */         ComboBox comboBox = new ComboBox(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
/* 1111:     */         
/* 1112:     */ 
/* 1113:     */ 
/* 1114:     */ 
/* 1115:1685 */         this.drawings.add(comboBox);
/* 1116:1686 */         return;
/* 1117:     */       }
/* 1118:1690 */       if (objRecord.getType() == ObjRecord.CHECKBOX)
/* 1119:     */       {
/* 1120:1692 */         if (this.drawingData == null) {
/* 1121:1694 */           this.drawingData = new DrawingData();
/* 1122:     */         }
/* 1123:1697 */         CheckBox checkBox = new CheckBox(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
/* 1124:     */         
/* 1125:     */ 
/* 1126:     */ 
/* 1127:     */ 
/* 1128:     */ 
/* 1129:1703 */         Record r2 = this.excelFile.next();
/* 1130:1704 */         Assert.verify((r2.getType() == Type.MSODRAWING) || (r2.getType() == Type.CONTINUE));
/* 1131:1706 */         if ((r2.getType() == Type.MSODRAWING) || (r2.getType() == Type.CONTINUE))
/* 1132:     */         {
/* 1133:1708 */           MsoDrawingRecord mso = new MsoDrawingRecord(r2);
/* 1134:1709 */           checkBox.addMso(mso);
/* 1135:1710 */           r2 = this.excelFile.next();
/* 1136:     */         }
/* 1137:1713 */         Assert.verify(r2.getType() == Type.TXO);
/* 1138:1714 */         TextObjectRecord txo = new TextObjectRecord(r2);
/* 1139:1715 */         checkBox.setTextObject(txo);
/* 1140:1717 */         if (txo.getTextLength() == 0) {
/* 1141:1719 */           return;
/* 1142:     */         }
/* 1143:1722 */         r2 = this.excelFile.next();
/* 1144:1723 */         Assert.verify(r2.getType() == Type.CONTINUE);
/* 1145:1724 */         ContinueRecord text = new ContinueRecord(r2);
/* 1146:1725 */         checkBox.setText(text);
/* 1147:     */         
/* 1148:1727 */         r2 = this.excelFile.next();
/* 1149:1728 */         if (r2.getType() == Type.CONTINUE)
/* 1150:     */         {
/* 1151:1730 */           ContinueRecord formatting = new ContinueRecord(r2);
/* 1152:1731 */           checkBox.setFormatting(formatting);
/* 1153:     */         }
/* 1154:1734 */         this.drawings.add(checkBox);
/* 1155:     */         
/* 1156:1736 */         return;
/* 1157:     */       }
/* 1158:1740 */       if (objRecord.getType() == ObjRecord.BUTTON)
/* 1159:     */       {
/* 1160:1742 */         if (this.drawingData == null) {
/* 1161:1744 */           this.drawingData = new DrawingData();
/* 1162:     */         }
/* 1163:1747 */         Button button = new Button(msoRecord, objRecord, this.drawingData, this.workbook.getDrawingGroup(), this.workbookSettings);
/* 1164:     */         
/* 1165:     */ 
/* 1166:     */ 
/* 1167:     */ 
/* 1168:     */ 
/* 1169:1753 */         Record r2 = this.excelFile.next();
/* 1170:1754 */         Assert.verify((r2.getType() == Type.MSODRAWING) || (r2.getType() == Type.CONTINUE));
/* 1171:1756 */         if ((r2.getType() == Type.MSODRAWING) || (r2.getType() == Type.CONTINUE))
/* 1172:     */         {
/* 1173:1759 */           MsoDrawingRecord mso = new MsoDrawingRecord(r2);
/* 1174:1760 */           button.addMso(mso);
/* 1175:1761 */           r2 = this.excelFile.next();
/* 1176:     */         }
/* 1177:1764 */         Assert.verify(r2.getType() == Type.TXO);
/* 1178:1765 */         TextObjectRecord txo = new TextObjectRecord(r2);
/* 1179:1766 */         button.setTextObject(txo);
/* 1180:     */         
/* 1181:1768 */         r2 = this.excelFile.next();
/* 1182:1769 */         Assert.verify(r2.getType() == Type.CONTINUE);
/* 1183:1770 */         ContinueRecord text = new ContinueRecord(r2);
/* 1184:1771 */         button.setText(text);
/* 1185:     */         
/* 1186:1773 */         r2 = this.excelFile.next();
/* 1187:1774 */         if (r2.getType() == Type.CONTINUE)
/* 1188:     */         {
/* 1189:1776 */           ContinueRecord formatting = new ContinueRecord(r2);
/* 1190:1777 */           button.setFormatting(formatting);
/* 1191:     */         }
/* 1192:1780 */         this.drawings.add(button);
/* 1193:     */         
/* 1194:1782 */         return;
/* 1195:     */       }
/* 1196:1786 */       if (objRecord.getType() == ObjRecord.TEXT)
/* 1197:     */       {
/* 1198:1788 */         logger.warn(objRecord.getType() + " Object on sheet \"" + this.sheet.getName() + "\" not supported - omitting");
/* 1199:1793 */         if (this.drawingData == null) {
/* 1200:1795 */           this.drawingData = new DrawingData();
/* 1201:     */         }
/* 1202:1798 */         this.drawingData.addData(msoRecord.getData());
/* 1203:     */         
/* 1204:1800 */         Record r2 = this.excelFile.next();
/* 1205:1801 */         Assert.verify((r2.getType() == Type.MSODRAWING) || (r2.getType() == Type.CONTINUE));
/* 1206:1803 */         if ((r2.getType() == Type.MSODRAWING) || (r2.getType() == Type.CONTINUE))
/* 1207:     */         {
/* 1208:1806 */           MsoDrawingRecord mso = new MsoDrawingRecord(r2);
/* 1209:1807 */           this.drawingData.addRawData(mso.getData());
/* 1210:1808 */           r2 = this.excelFile.next();
/* 1211:     */         }
/* 1212:1811 */         Assert.verify(r2.getType() == Type.TXO);
/* 1213:1813 */         if (this.workbook.getDrawingGroup() != null) {
/* 1214:1815 */           this.workbook.getDrawingGroup().setDrawingsOmitted(msoRecord, objRecord);
/* 1215:     */         }
/* 1216:1819 */         return;
/* 1217:     */       }
/* 1218:1823 */       if (objRecord.getType() != ObjRecord.CHART)
/* 1219:     */       {
/* 1220:1825 */         logger.warn(objRecord.getType() + " Object on sheet \"" + this.sheet.getName() + "\" not supported - omitting");
/* 1221:1830 */         if (this.drawingData == null) {
/* 1222:1832 */           this.drawingData = new DrawingData();
/* 1223:     */         }
/* 1224:1835 */         this.drawingData.addData(msoRecord.getData());
/* 1225:1837 */         if (this.workbook.getDrawingGroup() != null) {
/* 1226:1839 */           this.workbook.getDrawingGroup().setDrawingsOmitted(msoRecord, objRecord);
/* 1227:     */         }
/* 1228:1843 */         return;
/* 1229:     */       }
/* 1230:     */     }
/* 1231:     */     catch (DrawingDataException e)
/* 1232:     */     {
/* 1233:1848 */       logger.warn(e.getMessage() + "...disabling drawings for the remainder of the workbook");
/* 1234:     */       
/* 1235:1850 */       this.workbookSettings.setDrawingsDisabled(true);
/* 1236:     */     }
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   DrawingData getDrawingData()
/* 1240:     */   {
/* 1241:1859 */     return this.drawingData;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   private void handleOutOfBoundsCells()
/* 1245:     */   {
/* 1246:1868 */     int resizedRows = this.numRows;
/* 1247:1869 */     int resizedCols = this.numCols;
/* 1248:1872 */     for (Iterator i = this.outOfBoundsCells.iterator(); i.hasNext();)
/* 1249:     */     {
/* 1250:1874 */       Cell cell = (Cell)i.next();
/* 1251:1875 */       resizedRows = Math.max(resizedRows, cell.getRow() + 1);
/* 1252:1876 */       resizedCols = Math.max(resizedCols, cell.getColumn() + 1);
/* 1253:     */     }
/* 1254:1885 */     if (resizedCols > this.numCols) {
/* 1255:1887 */       for (int r = 0; r < this.numRows; r++)
/* 1256:     */       {
/* 1257:1889 */         Cell[] newRow = new Cell[resizedCols];
/* 1258:1890 */         Cell[] oldRow = this.cells[r];
/* 1259:1891 */         System.arraycopy(oldRow, 0, newRow, 0, oldRow.length);
/* 1260:1892 */         this.cells[r] = newRow;
/* 1261:     */       }
/* 1262:     */     }
/* 1263:1897 */     if (resizedRows > this.numRows)
/* 1264:     */     {
/* 1265:1899 */       Cell[][] newCells = new Cell[resizedRows][];
/* 1266:1900 */       System.arraycopy(this.cells, 0, newCells, 0, this.cells.length);
/* 1267:1901 */       this.cells = newCells;
/* 1268:1904 */       for (int i = this.numRows; i < resizedRows; i++) {
/* 1269:1906 */         newCells[i] = new Cell[resizedCols];
/* 1270:     */       }
/* 1271:     */     }
/* 1272:1910 */     this.numRows = resizedRows;
/* 1273:1911 */     this.numCols = resizedCols;
/* 1274:1914 */     for (Iterator i = this.outOfBoundsCells.iterator(); i.hasNext();)
/* 1275:     */     {
/* 1276:1916 */       Cell cell = (Cell)i.next();
/* 1277:1917 */       addCell(cell);
/* 1278:     */     }
/* 1279:1920 */     this.outOfBoundsCells.clear();
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   public int getMaxColumnOutlineLevel()
/* 1283:     */   {
/* 1284:1930 */     return this.maxColumnOutlineLevel;
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   public int getMaxRowOutlineLevel()
/* 1288:     */   {
/* 1289:1940 */     return this.maxRowOutlineLevel;
/* 1290:     */   }
/* 1291:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.SheetReader
 * JD-Core Version:    0.7.0.1
 */