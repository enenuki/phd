/*    1:     */ package jxl.read.biff;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Iterator;
/*    5:     */ import java.util.regex.Pattern;
/*    6:     */ import jxl.Cell;
/*    7:     */ import jxl.CellView;
/*    8:     */ import jxl.Hyperlink;
/*    9:     */ import jxl.Image;
/*   10:     */ import jxl.LabelCell;
/*   11:     */ import jxl.Range;
/*   12:     */ import jxl.Sheet;
/*   13:     */ import jxl.SheetSettings;
/*   14:     */ import jxl.WorkbookSettings;
/*   15:     */ import jxl.biff.AutoFilter;
/*   16:     */ import jxl.biff.BuiltInName;
/*   17:     */ import jxl.biff.CellFinder;
/*   18:     */ import jxl.biff.CellReferenceHelper;
/*   19:     */ import jxl.biff.ConditionalFormat;
/*   20:     */ import jxl.biff.DataValidation;
/*   21:     */ import jxl.biff.EmptyCell;
/*   22:     */ import jxl.biff.FormattingRecords;
/*   23:     */ import jxl.biff.Type;
/*   24:     */ import jxl.biff.WorkspaceInformationRecord;
/*   25:     */ import jxl.biff.drawing.Chart;
/*   26:     */ import jxl.biff.drawing.Drawing;
/*   27:     */ import jxl.biff.drawing.DrawingData;
/*   28:     */ import jxl.biff.drawing.DrawingGroupObject;
/*   29:     */ import jxl.common.Logger;
/*   30:     */ import jxl.format.CellFormat;
/*   31:     */ 
/*   32:     */ public class SheetImpl
/*   33:     */   implements Sheet
/*   34:     */ {
/*   35:  68 */   private static Logger logger = Logger.getLogger(SheetImpl.class);
/*   36:     */   private File excelFile;
/*   37:     */   private SSTRecord sharedStrings;
/*   38:     */   private BOFRecord sheetBof;
/*   39:     */   private BOFRecord workbookBof;
/*   40:     */   private FormattingRecords formattingRecords;
/*   41:     */   private String name;
/*   42:     */   private int numRows;
/*   43:     */   private int numCols;
/*   44:     */   private Cell[][] cells;
/*   45:     */   private int startPosition;
/*   46:     */   private ColumnInfoRecord[] columnInfos;
/*   47:     */   private RowRecord[] rowRecords;
/*   48:     */   private ArrayList rowProperties;
/*   49:     */   private ArrayList columnInfosArray;
/*   50:     */   private ArrayList sharedFormulas;
/*   51:     */   private ArrayList hyperlinks;
/*   52:     */   private ArrayList charts;
/*   53:     */   private ArrayList drawings;
/*   54:     */   private ArrayList images;
/*   55:     */   private DataValidation dataValidation;
/*   56:     */   private Range[] mergedCells;
/*   57:     */   private boolean columnInfosInitialized;
/*   58:     */   private boolean rowRecordsInitialized;
/*   59:     */   private boolean nineteenFour;
/*   60:     */   private WorkspaceInformationRecord workspaceOptions;
/*   61:     */   private boolean hidden;
/*   62:     */   private PLSRecord plsRecord;
/*   63:     */   private ButtonPropertySetRecord buttonPropertySet;
/*   64:     */   private SheetSettings settings;
/*   65:     */   private int[] rowBreaks;
/*   66:     */   private int[] columnBreaks;
/*   67:     */   private int maxRowOutlineLevel;
/*   68:     */   private int maxColumnOutlineLevel;
/*   69:     */   private ArrayList localNames;
/*   70:     */   private ArrayList conditionalFormats;
/*   71:     */   private AutoFilter autoFilter;
/*   72:     */   private WorkbookParser workbook;
/*   73:     */   private WorkbookSettings workbookSettings;
/*   74:     */   
/*   75:     */   SheetImpl(File f, SSTRecord sst, FormattingRecords fr, BOFRecord sb, BOFRecord wb, boolean nf, WorkbookParser wp)
/*   76:     */     throws BiffException
/*   77:     */   {
/*   78: 283 */     this.excelFile = f;
/*   79: 284 */     this.sharedStrings = sst;
/*   80: 285 */     this.formattingRecords = fr;
/*   81: 286 */     this.sheetBof = sb;
/*   82: 287 */     this.workbookBof = wb;
/*   83: 288 */     this.columnInfosArray = new ArrayList();
/*   84: 289 */     this.sharedFormulas = new ArrayList();
/*   85: 290 */     this.hyperlinks = new ArrayList();
/*   86: 291 */     this.rowProperties = new ArrayList(10);
/*   87: 292 */     this.columnInfosInitialized = false;
/*   88: 293 */     this.rowRecordsInitialized = false;
/*   89: 294 */     this.nineteenFour = nf;
/*   90: 295 */     this.workbook = wp;
/*   91: 296 */     this.workbookSettings = this.workbook.getSettings();
/*   92:     */     
/*   93:     */ 
/*   94: 299 */     this.startPosition = f.getPos();
/*   95: 301 */     if (this.sheetBof.isChart()) {
/*   96: 304 */       this.startPosition -= this.sheetBof.getLength() + 4;
/*   97:     */     }
/*   98: 307 */     Record r = null;
/*   99: 308 */     int bofs = 1;
/*  100: 310 */     while (bofs >= 1)
/*  101:     */     {
/*  102: 312 */       r = f.next();
/*  103: 315 */       if (r.getCode() == Type.EOF.value) {
/*  104: 317 */         bofs--;
/*  105:     */       }
/*  106: 320 */       if (r.getCode() == Type.BOF.value) {
/*  107: 322 */         bofs++;
/*  108:     */       }
/*  109:     */     }
/*  110:     */   }
/*  111:     */   
/*  112:     */   public Cell getCell(String loc)
/*  113:     */   {
/*  114: 336 */     return getCell(CellReferenceHelper.getColumn(loc), CellReferenceHelper.getRow(loc));
/*  115:     */   }
/*  116:     */   
/*  117:     */   public Cell getCell(int column, int row)
/*  118:     */   {
/*  119: 351 */     if (this.cells == null) {
/*  120: 353 */       readSheet();
/*  121:     */     }
/*  122: 356 */     Cell c = this.cells[row][column];
/*  123: 358 */     if (c == null)
/*  124:     */     {
/*  125: 360 */       c = new EmptyCell(column, row);
/*  126: 361 */       this.cells[row][column] = c;
/*  127:     */     }
/*  128: 364 */     return c;
/*  129:     */   }
/*  130:     */   
/*  131:     */   public Cell findCell(String contents)
/*  132:     */   {
/*  133: 378 */     CellFinder cellFinder = new CellFinder(this);
/*  134: 379 */     return cellFinder.findCell(contents);
/*  135:     */   }
/*  136:     */   
/*  137:     */   public Cell findCell(String contents, int firstCol, int firstRow, int lastCol, int lastRow, boolean reverse)
/*  138:     */   {
/*  139: 403 */     CellFinder cellFinder = new CellFinder(this);
/*  140: 404 */     return cellFinder.findCell(contents, firstCol, firstRow, lastCol, lastRow, reverse);
/*  141:     */   }
/*  142:     */   
/*  143:     */   public Cell findCell(Pattern pattern, int firstCol, int firstRow, int lastCol, int lastRow, boolean reverse)
/*  144:     */   {
/*  145: 433 */     CellFinder cellFinder = new CellFinder(this);
/*  146: 434 */     return cellFinder.findCell(pattern, firstCol, firstRow, lastCol, lastRow, reverse);
/*  147:     */   }
/*  148:     */   
/*  149:     */   public LabelCell findLabelCell(String contents)
/*  150:     */   {
/*  151: 456 */     CellFinder cellFinder = new CellFinder(this);
/*  152: 457 */     return cellFinder.findLabelCell(contents);
/*  153:     */   }
/*  154:     */   
/*  155:     */   public int getRows()
/*  156:     */   {
/*  157: 469 */     if (this.cells == null) {
/*  158: 471 */       readSheet();
/*  159:     */     }
/*  160: 474 */     return this.numRows;
/*  161:     */   }
/*  162:     */   
/*  163:     */   public int getColumns()
/*  164:     */   {
/*  165: 486 */     if (this.cells == null) {
/*  166: 488 */       readSheet();
/*  167:     */     }
/*  168: 491 */     return this.numCols;
/*  169:     */   }
/*  170:     */   
/*  171:     */   public Cell[] getRow(int row)
/*  172:     */   {
/*  173: 505 */     if (this.cells == null) {
/*  174: 507 */       readSheet();
/*  175:     */     }
/*  176: 511 */     boolean found = false;
/*  177: 512 */     int col = this.numCols - 1;
/*  178: 513 */     while ((col >= 0) && (!found)) {
/*  179: 515 */       if (this.cells[row][col] != null) {
/*  180: 517 */         found = true;
/*  181:     */       } else {
/*  182: 521 */         col--;
/*  183:     */       }
/*  184:     */     }
/*  185: 526 */     Cell[] c = new Cell[col + 1];
/*  186: 528 */     for (int i = 0; i <= col; i++) {
/*  187: 530 */       c[i] = getCell(i, row);
/*  188:     */     }
/*  189: 532 */     return c;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public Cell[] getColumn(int col)
/*  193:     */   {
/*  194: 546 */     if (this.cells == null) {
/*  195: 548 */       readSheet();
/*  196:     */     }
/*  197: 552 */     boolean found = false;
/*  198: 553 */     int row = this.numRows - 1;
/*  199: 554 */     while ((row >= 0) && (!found)) {
/*  200: 556 */       if (this.cells[row][col] != null) {
/*  201: 558 */         found = true;
/*  202:     */       } else {
/*  203: 562 */         row--;
/*  204:     */       }
/*  205:     */     }
/*  206: 567 */     Cell[] c = new Cell[row + 1];
/*  207: 569 */     for (int i = 0; i <= row; i++) {
/*  208: 571 */       c[i] = getCell(col, i);
/*  209:     */     }
/*  210: 573 */     return c;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public String getName()
/*  214:     */   {
/*  215: 583 */     return this.name;
/*  216:     */   }
/*  217:     */   
/*  218:     */   final void setName(String s)
/*  219:     */   {
/*  220: 593 */     this.name = s;
/*  221:     */   }
/*  222:     */   
/*  223:     */   /**
/*  224:     */    * @deprecated
/*  225:     */    */
/*  226:     */   public boolean isHidden()
/*  227:     */   {
/*  228: 604 */     return this.hidden;
/*  229:     */   }
/*  230:     */   
/*  231:     */   public ColumnInfoRecord getColumnInfo(int col)
/*  232:     */   {
/*  233: 616 */     if (!this.columnInfosInitialized)
/*  234:     */     {
/*  235: 619 */       Iterator i = this.columnInfosArray.iterator();
/*  236: 620 */       ColumnInfoRecord cir = null;
/*  237: 621 */       while (i.hasNext())
/*  238:     */       {
/*  239: 623 */         cir = (ColumnInfoRecord)i.next();
/*  240:     */         
/*  241: 625 */         int startcol = Math.max(0, cir.getStartColumn());
/*  242: 626 */         int endcol = Math.min(this.columnInfos.length - 1, cir.getEndColumn());
/*  243: 628 */         for (int c = startcol; c <= endcol; c++) {
/*  244: 630 */           this.columnInfos[c] = cir;
/*  245:     */         }
/*  246: 633 */         if (endcol < startcol) {
/*  247: 635 */           this.columnInfos[startcol] = cir;
/*  248:     */         }
/*  249:     */       }
/*  250: 639 */       this.columnInfosInitialized = true;
/*  251:     */     }
/*  252: 642 */     return col < this.columnInfos.length ? this.columnInfos[col] : null;
/*  253:     */   }
/*  254:     */   
/*  255:     */   public ColumnInfoRecord[] getColumnInfos()
/*  256:     */   {
/*  257: 653 */     ColumnInfoRecord[] infos = new ColumnInfoRecord[this.columnInfosArray.size()];
/*  258: 654 */     for (int i = 0; i < this.columnInfosArray.size(); i++) {
/*  259: 656 */       infos[i] = ((ColumnInfoRecord)this.columnInfosArray.get(i));
/*  260:     */     }
/*  261: 659 */     return infos;
/*  262:     */   }
/*  263:     */   
/*  264:     */   final void setHidden(boolean h)
/*  265:     */   {
/*  266: 669 */     this.hidden = h;
/*  267:     */   }
/*  268:     */   
/*  269:     */   final void clear()
/*  270:     */   {
/*  271: 678 */     this.cells = ((Cell[][])null);
/*  272: 679 */     this.mergedCells = null;
/*  273: 680 */     this.columnInfosArray.clear();
/*  274: 681 */     this.sharedFormulas.clear();
/*  275: 682 */     this.hyperlinks.clear();
/*  276: 683 */     this.columnInfosInitialized = false;
/*  277: 685 */     if (!this.workbookSettings.getGCDisabled()) {
/*  278: 687 */       System.gc();
/*  279:     */     }
/*  280:     */   }
/*  281:     */   
/*  282:     */   final void readSheet()
/*  283:     */   {
/*  284: 699 */     if (!this.sheetBof.isWorksheet())
/*  285:     */     {
/*  286: 701 */       this.numRows = 0;
/*  287: 702 */       this.numCols = 0;
/*  288: 703 */       this.cells = new Cell[0][0];
/*  289:     */     }
/*  290: 707 */     SheetReader reader = new SheetReader(this.excelFile, this.sharedStrings, this.formattingRecords, this.sheetBof, this.workbookBof, this.nineteenFour, this.workbook, this.startPosition, this);
/*  291:     */     
/*  292:     */ 
/*  293:     */ 
/*  294:     */ 
/*  295:     */ 
/*  296:     */ 
/*  297:     */ 
/*  298:     */ 
/*  299: 716 */     reader.read();
/*  300:     */     
/*  301:     */ 
/*  302: 719 */     this.numRows = reader.getNumRows();
/*  303: 720 */     this.numCols = reader.getNumCols();
/*  304: 721 */     this.cells = reader.getCells();
/*  305: 722 */     this.rowProperties = reader.getRowProperties();
/*  306: 723 */     this.columnInfosArray = reader.getColumnInfosArray();
/*  307: 724 */     this.hyperlinks = reader.getHyperlinks();
/*  308: 725 */     this.conditionalFormats = reader.getConditionalFormats();
/*  309: 726 */     this.autoFilter = reader.getAutoFilter();
/*  310: 727 */     this.charts = reader.getCharts();
/*  311: 728 */     this.drawings = reader.getDrawings();
/*  312: 729 */     this.dataValidation = reader.getDataValidation();
/*  313: 730 */     this.mergedCells = reader.getMergedCells();
/*  314: 731 */     this.settings = reader.getSettings();
/*  315: 732 */     this.settings.setHidden(this.hidden);
/*  316: 733 */     this.rowBreaks = reader.getRowBreaks();
/*  317: 734 */     this.columnBreaks = reader.getColumnBreaks();
/*  318: 735 */     this.workspaceOptions = reader.getWorkspaceOptions();
/*  319: 736 */     this.plsRecord = reader.getPLS();
/*  320: 737 */     this.buttonPropertySet = reader.getButtonPropertySet();
/*  321: 738 */     this.maxRowOutlineLevel = reader.getMaxRowOutlineLevel();
/*  322: 739 */     this.maxColumnOutlineLevel = reader.getMaxColumnOutlineLevel();
/*  323:     */     
/*  324: 741 */     reader = null;
/*  325: 743 */     if (!this.workbookSettings.getGCDisabled()) {
/*  326: 745 */       System.gc();
/*  327:     */     }
/*  328: 748 */     if (this.columnInfosArray.size() > 0)
/*  329:     */     {
/*  330: 750 */       ColumnInfoRecord cir = (ColumnInfoRecord)this.columnInfosArray.get(this.columnInfosArray.size() - 1);
/*  331:     */       
/*  332: 752 */       this.columnInfos = new ColumnInfoRecord[cir.getEndColumn() + 1];
/*  333:     */     }
/*  334:     */     else
/*  335:     */     {
/*  336: 756 */       this.columnInfos = new ColumnInfoRecord[0];
/*  337:     */     }
/*  338:     */     Iterator it;
/*  339: 760 */     if (this.localNames != null) {
/*  340: 762 */       for (it = this.localNames.iterator(); it.hasNext();)
/*  341:     */       {
/*  342: 764 */         NameRecord nr = (NameRecord)it.next();
/*  343: 765 */         if (nr.getBuiltInName() == BuiltInName.PRINT_AREA)
/*  344:     */         {
/*  345: 767 */           if (nr.getRanges().length > 0)
/*  346:     */           {
/*  347: 769 */             NameRecord.NameRange rng = nr.getRanges()[0];
/*  348: 770 */             this.settings.setPrintArea(rng.getFirstColumn(), rng.getFirstRow(), rng.getLastColumn(), rng.getLastRow());
/*  349:     */           }
/*  350:     */         }
/*  351: 776 */         else if (nr.getBuiltInName() == BuiltInName.PRINT_TITLES) {
/*  352: 783 */           for (int i = 0; i < nr.getRanges().length; i++)
/*  353:     */           {
/*  354: 785 */             NameRecord.NameRange rng = nr.getRanges()[i];
/*  355: 786 */             if ((rng.getFirstColumn() == 0) && (rng.getLastColumn() == 255)) {
/*  356: 788 */               this.settings.setPrintTitlesRow(rng.getFirstRow(), rng.getLastRow());
/*  357:     */             } else {
/*  358: 793 */               this.settings.setPrintTitlesCol(rng.getFirstColumn(), rng.getLastColumn());
/*  359:     */             }
/*  360:     */           }
/*  361:     */         }
/*  362:     */       }
/*  363:     */     }
/*  364:     */   }
/*  365:     */   
/*  366:     */   public Hyperlink[] getHyperlinks()
/*  367:     */   {
/*  368: 809 */     Hyperlink[] hl = new Hyperlink[this.hyperlinks.size()];
/*  369: 811 */     for (int i = 0; i < this.hyperlinks.size(); i++) {
/*  370: 813 */       hl[i] = ((Hyperlink)this.hyperlinks.get(i));
/*  371:     */     }
/*  372: 816 */     return hl;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public Range[] getMergedCells()
/*  376:     */   {
/*  377: 826 */     if (this.mergedCells == null) {
/*  378: 828 */       return new Range[0];
/*  379:     */     }
/*  380: 831 */     return this.mergedCells;
/*  381:     */   }
/*  382:     */   
/*  383:     */   public RowRecord[] getRowProperties()
/*  384:     */   {
/*  385: 841 */     RowRecord[] rp = new RowRecord[this.rowProperties.size()];
/*  386: 842 */     for (int i = 0; i < rp.length; i++) {
/*  387: 844 */       rp[i] = ((RowRecord)this.rowProperties.get(i));
/*  388:     */     }
/*  389: 847 */     return rp;
/*  390:     */   }
/*  391:     */   
/*  392:     */   public DataValidation getDataValidation()
/*  393:     */   {
/*  394: 857 */     return this.dataValidation;
/*  395:     */   }
/*  396:     */   
/*  397:     */   RowRecord getRowInfo(int r)
/*  398:     */   {
/*  399: 869 */     if (!this.rowRecordsInitialized)
/*  400:     */     {
/*  401: 871 */       this.rowRecords = new RowRecord[getRows()];
/*  402: 872 */       Iterator i = this.rowProperties.iterator();
/*  403:     */       
/*  404: 874 */       int rownum = 0;
/*  405: 875 */       RowRecord rr = null;
/*  406: 876 */       while (i.hasNext())
/*  407:     */       {
/*  408: 878 */         rr = (RowRecord)i.next();
/*  409: 879 */         rownum = rr.getRowNumber();
/*  410: 880 */         if (rownum < this.rowRecords.length) {
/*  411: 882 */           this.rowRecords[rownum] = rr;
/*  412:     */         }
/*  413:     */       }
/*  414: 886 */       this.rowRecordsInitialized = true;
/*  415:     */     }
/*  416: 889 */     return r < this.rowRecords.length ? this.rowRecords[r] : null;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public final int[] getRowPageBreaks()
/*  420:     */   {
/*  421: 899 */     return this.rowBreaks;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public final int[] getColumnPageBreaks()
/*  425:     */   {
/*  426: 909 */     return this.columnBreaks;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public final Chart[] getCharts()
/*  430:     */   {
/*  431: 919 */     Chart[] ch = new Chart[this.charts.size()];
/*  432: 921 */     for (int i = 0; i < ch.length; i++) {
/*  433: 923 */       ch[i] = ((Chart)this.charts.get(i));
/*  434:     */     }
/*  435: 925 */     return ch;
/*  436:     */   }
/*  437:     */   
/*  438:     */   public final DrawingGroupObject[] getDrawings()
/*  439:     */   {
/*  440: 935 */     DrawingGroupObject[] dr = new DrawingGroupObject[this.drawings.size()];
/*  441: 936 */     dr = (DrawingGroupObject[])this.drawings.toArray(dr);
/*  442: 937 */     return dr;
/*  443:     */   }
/*  444:     */   
/*  445:     */   /**
/*  446:     */    * @deprecated
/*  447:     */    */
/*  448:     */   public boolean isProtected()
/*  449:     */   {
/*  450: 948 */     return this.settings.isProtected();
/*  451:     */   }
/*  452:     */   
/*  453:     */   public WorkspaceInformationRecord getWorkspaceOptions()
/*  454:     */   {
/*  455: 959 */     return this.workspaceOptions;
/*  456:     */   }
/*  457:     */   
/*  458:     */   public SheetSettings getSettings()
/*  459:     */   {
/*  460: 969 */     return this.settings;
/*  461:     */   }
/*  462:     */   
/*  463:     */   public WorkbookParser getWorkbook()
/*  464:     */   {
/*  465: 980 */     return this.workbook;
/*  466:     */   }
/*  467:     */   
/*  468:     */   /**
/*  469:     */    * @deprecated
/*  470:     */    */
/*  471:     */   public CellFormat getColumnFormat(int col)
/*  472:     */   {
/*  473: 992 */     CellView cv = getColumnView(col);
/*  474: 993 */     return cv.getFormat();
/*  475:     */   }
/*  476:     */   
/*  477:     */   public int getColumnWidth(int col)
/*  478:     */   {
/*  479:1005 */     return getColumnView(col).getSize() / 256;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public CellView getColumnView(int col)
/*  483:     */   {
/*  484:1017 */     ColumnInfoRecord cir = getColumnInfo(col);
/*  485:1018 */     CellView cv = new CellView();
/*  486:1020 */     if (cir != null)
/*  487:     */     {
/*  488:1022 */       cv.setDimension(cir.getWidth() / 256);
/*  489:1023 */       cv.setSize(cir.getWidth());
/*  490:1024 */       cv.setHidden(cir.getHidden());
/*  491:1025 */       cv.setFormat(this.formattingRecords.getXFRecord(cir.getXFIndex()));
/*  492:     */     }
/*  493:     */     else
/*  494:     */     {
/*  495:1029 */       cv.setDimension(this.settings.getDefaultColumnWidth());
/*  496:1030 */       cv.setSize(this.settings.getDefaultColumnWidth() * 256);
/*  497:     */     }
/*  498:1033 */     return cv;
/*  499:     */   }
/*  500:     */   
/*  501:     */   /**
/*  502:     */    * @deprecated
/*  503:     */    */
/*  504:     */   public int getRowHeight(int row)
/*  505:     */   {
/*  506:1046 */     return getRowView(row).getDimension();
/*  507:     */   }
/*  508:     */   
/*  509:     */   public CellView getRowView(int row)
/*  510:     */   {
/*  511:1058 */     RowRecord rr = getRowInfo(row);
/*  512:     */     
/*  513:1060 */     CellView cv = new CellView();
/*  514:1062 */     if (rr != null)
/*  515:     */     {
/*  516:1064 */       cv.setDimension(rr.getRowHeight());
/*  517:1065 */       cv.setSize(rr.getRowHeight());
/*  518:1066 */       cv.setHidden(rr.isCollapsed());
/*  519:1067 */       if (rr.hasDefaultFormat()) {
/*  520:1069 */         cv.setFormat(this.formattingRecords.getXFRecord(rr.getXFIndex()));
/*  521:     */       }
/*  522:     */     }
/*  523:     */     else
/*  524:     */     {
/*  525:1074 */       cv.setDimension(this.settings.getDefaultRowHeight());
/*  526:1075 */       cv.setSize(this.settings.getDefaultRowHeight());
/*  527:     */     }
/*  528:1078 */     return cv;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public BOFRecord getSheetBof()
/*  532:     */   {
/*  533:1089 */     return this.sheetBof;
/*  534:     */   }
/*  535:     */   
/*  536:     */   public BOFRecord getWorkbookBof()
/*  537:     */   {
/*  538:1100 */     return this.workbookBof;
/*  539:     */   }
/*  540:     */   
/*  541:     */   public PLSRecord getPLS()
/*  542:     */   {
/*  543:1111 */     return this.plsRecord;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public ButtonPropertySetRecord getButtonPropertySet()
/*  547:     */   {
/*  548:1121 */     return this.buttonPropertySet;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public int getNumberOfImages()
/*  552:     */   {
/*  553:1131 */     if (this.images == null) {
/*  554:1133 */       initializeImages();
/*  555:     */     }
/*  556:1136 */     return this.images.size();
/*  557:     */   }
/*  558:     */   
/*  559:     */   public Image getDrawing(int i)
/*  560:     */   {
/*  561:1147 */     if (this.images == null) {
/*  562:1149 */       initializeImages();
/*  563:     */     }
/*  564:1152 */     return (Image)this.images.get(i);
/*  565:     */   }
/*  566:     */   
/*  567:     */   private void initializeImages()
/*  568:     */   {
/*  569:1160 */     if (this.images != null) {
/*  570:1162 */       return;
/*  571:     */     }
/*  572:1165 */     this.images = new ArrayList();
/*  573:1166 */     DrawingGroupObject[] dgos = getDrawings();
/*  574:1168 */     for (int i = 0; i < dgos.length; i++) {
/*  575:1170 */       if ((dgos[i] instanceof Drawing)) {
/*  576:1172 */         this.images.add(dgos[i]);
/*  577:     */       }
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   public DrawingData getDrawingData()
/*  582:     */   {
/*  583:1182 */     SheetReader reader = new SheetReader(this.excelFile, this.sharedStrings, this.formattingRecords, this.sheetBof, this.workbookBof, this.nineteenFour, this.workbook, this.startPosition, this);
/*  584:     */     
/*  585:     */ 
/*  586:     */ 
/*  587:     */ 
/*  588:     */ 
/*  589:     */ 
/*  590:     */ 
/*  591:     */ 
/*  592:1191 */     reader.read();
/*  593:1192 */     return reader.getDrawingData();
/*  594:     */   }
/*  595:     */   
/*  596:     */   void addLocalName(NameRecord nr)
/*  597:     */   {
/*  598:1202 */     if (this.localNames == null) {
/*  599:1204 */       this.localNames = new ArrayList();
/*  600:     */     }
/*  601:1207 */     this.localNames.add(nr);
/*  602:     */   }
/*  603:     */   
/*  604:     */   public ConditionalFormat[] getConditionalFormats()
/*  605:     */   {
/*  606:1217 */     ConditionalFormat[] formats = new ConditionalFormat[this.conditionalFormats.size()];
/*  607:     */     
/*  608:1219 */     formats = (ConditionalFormat[])this.conditionalFormats.toArray(formats);
/*  609:1220 */     return formats;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public AutoFilter getAutoFilter()
/*  613:     */   {
/*  614:1230 */     return this.autoFilter;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public int getMaxColumnOutlineLevel()
/*  618:     */   {
/*  619:1240 */     return this.maxColumnOutlineLevel;
/*  620:     */   }
/*  621:     */   
/*  622:     */   public int getMaxRowOutlineLevel()
/*  623:     */   {
/*  624:1250 */     return this.maxRowOutlineLevel;
/*  625:     */   }
/*  626:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.SheetImpl
 * JD-Core Version:    0.7.0.1
 */