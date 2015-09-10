/*    1:     */ package jxl.write.biff;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.net.URL;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.Comparator;
/*    7:     */ import java.util.Iterator;
/*    8:     */ import java.util.TreeSet;
/*    9:     */ import java.util.regex.Pattern;
/*   10:     */ import jxl.Cell;
/*   11:     */ import jxl.CellFeatures;
/*   12:     */ import jxl.CellReferenceHelper;
/*   13:     */ import jxl.CellType;
/*   14:     */ import jxl.CellView;
/*   15:     */ import jxl.HeaderFooter;
/*   16:     */ import jxl.HeaderFooter.Contents;
/*   17:     */ import jxl.Hyperlink;
/*   18:     */ import jxl.Image;
/*   19:     */ import jxl.LabelCell;
/*   20:     */ import jxl.Range;
/*   21:     */ import jxl.Sheet;
/*   22:     */ import jxl.SheetSettings;
/*   23:     */ import jxl.WorkbookSettings;
/*   24:     */ import jxl.biff.AutoFilter;
/*   25:     */ import jxl.biff.CellFinder;
/*   26:     */ import jxl.biff.ConditionalFormat;
/*   27:     */ import jxl.biff.DVParser;
/*   28:     */ import jxl.biff.DataValidation;
/*   29:     */ import jxl.biff.EmptyCell;
/*   30:     */ import jxl.biff.FormattingRecords;
/*   31:     */ import jxl.biff.IndexMapping;
/*   32:     */ import jxl.biff.NumFormatRecordsException;
/*   33:     */ import jxl.biff.SheetRangeImpl;
/*   34:     */ import jxl.biff.WorkspaceInformationRecord;
/*   35:     */ import jxl.biff.XFRecord;
/*   36:     */ import jxl.biff.drawing.Chart;
/*   37:     */ import jxl.biff.drawing.ComboBox;
/*   38:     */ import jxl.biff.drawing.Drawing;
/*   39:     */ import jxl.biff.drawing.DrawingGroup;
/*   40:     */ import jxl.biff.drawing.DrawingGroupObject;
/*   41:     */ import jxl.common.Assert;
/*   42:     */ import jxl.common.Logger;
/*   43:     */ import jxl.format.CellFormat;
/*   44:     */ import jxl.format.Font;
/*   45:     */ import jxl.format.PageOrientation;
/*   46:     */ import jxl.format.PaperSize;
/*   47:     */ import jxl.write.Blank;
/*   48:     */ import jxl.write.Label;
/*   49:     */ import jxl.write.WritableCell;
/*   50:     */ import jxl.write.WritableCellFeatures;
/*   51:     */ import jxl.write.WritableCellFormat;
/*   52:     */ import jxl.write.WritableHyperlink;
/*   53:     */ import jxl.write.WritableImage;
/*   54:     */ import jxl.write.WritableSheet;
/*   55:     */ import jxl.write.WritableWorkbook;
/*   56:     */ import jxl.write.WriteException;
/*   57:     */ 
/*   58:     */ class WritableSheetImpl
/*   59:     */   implements WritableSheet
/*   60:     */ {
/*   61:  94 */   private static Logger logger = Logger.getLogger(WritableSheetImpl.class);
/*   62:     */   private String name;
/*   63:     */   private File outputFile;
/*   64:     */   private RowRecord[] rows;
/*   65:     */   private FormattingRecords formatRecords;
/*   66:     */   private SharedStrings sharedStrings;
/*   67:     */   private TreeSet columnFormats;
/*   68:     */   private TreeSet autosizedColumns;
/*   69:     */   private ArrayList hyperlinks;
/*   70:     */   private MergedCells mergedCells;
/*   71:     */   private int numRows;
/*   72:     */   private int numColumns;
/*   73:     */   private PLSRecord plsRecord;
/*   74:     */   private ButtonPropertySetRecord buttonPropertySet;
/*   75:     */   private boolean chartOnly;
/*   76:     */   private DataValidation dataValidation;
/*   77:     */   private ArrayList rowBreaks;
/*   78:     */   private ArrayList columnBreaks;
/*   79:     */   private ArrayList drawings;
/*   80:     */   private ArrayList images;
/*   81:     */   private ArrayList conditionalFormats;
/*   82:     */   private AutoFilter autoFilter;
/*   83:     */   private ArrayList validatedCells;
/*   84:     */   private ComboBox comboBox;
/*   85:     */   private boolean drawingsModified;
/*   86:     */   private int maxRowOutlineLevel;
/*   87:     */   private int maxColumnOutlineLevel;
/*   88:     */   private SheetSettings settings;
/*   89:     */   private SheetWriter sheetWriter;
/*   90:     */   private WorkbookSettings workbookSettings;
/*   91:     */   private WritableWorkbookImpl workbook;
/*   92:     */   private static final int rowGrowSize = 10;
/*   93:     */   private static final int numRowsPerSheet = 65536;
/*   94:     */   private static final int maxSheetNameLength = 31;
/*   95: 263 */   private static final char[] illegalSheetNameCharacters = { '*', ':', '?', '\\' };
/*   96: 269 */   private static final String[] imageTypes = { "png" };
/*   97:     */   
/*   98:     */   private static class ColumnInfoComparator
/*   99:     */     implements Comparator
/*  100:     */   {
/*  101:     */     public boolean equals(Object o)
/*  102:     */     {
/*  103: 284 */       return o == this;
/*  104:     */     }
/*  105:     */     
/*  106:     */     public int compare(Object o1, Object o2)
/*  107:     */     {
/*  108: 296 */       if (o1 == o2) {
/*  109: 298 */         return 0;
/*  110:     */       }
/*  111: 301 */       Assert.verify(o1 instanceof ColumnInfoRecord);
/*  112: 302 */       Assert.verify(o2 instanceof ColumnInfoRecord);
/*  113:     */       
/*  114: 304 */       ColumnInfoRecord ci1 = (ColumnInfoRecord)o1;
/*  115: 305 */       ColumnInfoRecord ci2 = (ColumnInfoRecord)o2;
/*  116:     */       
/*  117: 307 */       return ci1.getColumn() - ci2.getColumn();
/*  118:     */     }
/*  119:     */   }
/*  120:     */   
/*  121:     */   public WritableSheetImpl(String n, File of, FormattingRecords fr, SharedStrings ss, WorkbookSettings ws, WritableWorkbookImpl ww)
/*  122:     */   {
/*  123: 328 */     this.name = validateName(n);
/*  124: 329 */     this.outputFile = of;
/*  125: 330 */     this.rows = new RowRecord[0];
/*  126: 331 */     this.numRows = 0;
/*  127: 332 */     this.numColumns = 0;
/*  128: 333 */     this.chartOnly = false;
/*  129: 334 */     this.workbook = ww;
/*  130:     */     
/*  131: 336 */     this.formatRecords = fr;
/*  132: 337 */     this.sharedStrings = ss;
/*  133: 338 */     this.workbookSettings = ws;
/*  134: 339 */     this.drawingsModified = false;
/*  135: 340 */     this.columnFormats = new TreeSet(new ColumnInfoComparator(null));
/*  136: 341 */     this.autosizedColumns = new TreeSet();
/*  137: 342 */     this.hyperlinks = new ArrayList();
/*  138: 343 */     this.mergedCells = new MergedCells(this);
/*  139: 344 */     this.rowBreaks = new ArrayList();
/*  140: 345 */     this.columnBreaks = new ArrayList();
/*  141: 346 */     this.drawings = new ArrayList();
/*  142: 347 */     this.images = new ArrayList();
/*  143: 348 */     this.conditionalFormats = new ArrayList();
/*  144: 349 */     this.validatedCells = new ArrayList();
/*  145: 350 */     this.settings = new SheetSettings(this);
/*  146:     */     
/*  147:     */ 
/*  148: 353 */     this.sheetWriter = new SheetWriter(this.outputFile, this, this.workbookSettings);
/*  149:     */   }
/*  150:     */   
/*  151:     */   public Cell getCell(String loc)
/*  152:     */   {
/*  153: 367 */     return getCell(CellReferenceHelper.getColumn(loc), CellReferenceHelper.getRow(loc));
/*  154:     */   }
/*  155:     */   
/*  156:     */   public Cell getCell(int column, int row)
/*  157:     */   {
/*  158: 380 */     return getWritableCell(column, row);
/*  159:     */   }
/*  160:     */   
/*  161:     */   public WritableCell getWritableCell(String loc)
/*  162:     */   {
/*  163: 395 */     return getWritableCell(CellReferenceHelper.getColumn(loc), CellReferenceHelper.getRow(loc));
/*  164:     */   }
/*  165:     */   
/*  166:     */   public WritableCell getWritableCell(int column, int row)
/*  167:     */   {
/*  168: 408 */     WritableCell c = null;
/*  169: 410 */     if ((row < this.rows.length) && (this.rows[row] != null)) {
/*  170: 412 */       c = this.rows[row].getCell(column);
/*  171:     */     }
/*  172: 415 */     if (c == null) {
/*  173: 417 */       c = new EmptyCell(column, row);
/*  174:     */     }
/*  175: 420 */     return c;
/*  176:     */   }
/*  177:     */   
/*  178:     */   public int getRows()
/*  179:     */   {
/*  180: 430 */     return this.numRows;
/*  181:     */   }
/*  182:     */   
/*  183:     */   public int getColumns()
/*  184:     */   {
/*  185: 440 */     return this.numColumns;
/*  186:     */   }
/*  187:     */   
/*  188:     */   public Cell findCell(String contents)
/*  189:     */   {
/*  190: 454 */     CellFinder cellFinder = new CellFinder(this);
/*  191: 455 */     return cellFinder.findCell(contents);
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Cell findCell(String contents, int firstCol, int firstRow, int lastCol, int lastRow, boolean reverse)
/*  195:     */   {
/*  196: 479 */     CellFinder cellFinder = new CellFinder(this);
/*  197: 480 */     return cellFinder.findCell(contents, firstCol, firstRow, lastCol, lastRow, reverse);
/*  198:     */   }
/*  199:     */   
/*  200:     */   public Cell findCell(Pattern pattern, int firstCol, int firstRow, int lastCol, int lastRow, boolean reverse)
/*  201:     */   {
/*  202: 509 */     CellFinder cellFinder = new CellFinder(this);
/*  203: 510 */     return cellFinder.findCell(pattern, firstCol, firstRow, lastCol, lastRow, reverse);
/*  204:     */   }
/*  205:     */   
/*  206:     */   public LabelCell findLabelCell(String contents)
/*  207:     */   {
/*  208: 532 */     CellFinder cellFinder = new CellFinder(this);
/*  209: 533 */     return cellFinder.findLabelCell(contents);
/*  210:     */   }
/*  211:     */   
/*  212:     */   public Cell[] getRow(int row)
/*  213:     */   {
/*  214: 545 */     boolean found = false;
/*  215: 546 */     int col = this.numColumns - 1;
/*  216: 547 */     while ((col >= 0) && (!found)) {
/*  217: 549 */       if (getCell(col, row).getType() != CellType.EMPTY) {
/*  218: 551 */         found = true;
/*  219:     */       } else {
/*  220: 555 */         col--;
/*  221:     */       }
/*  222:     */     }
/*  223: 560 */     Cell[] cells = new Cell[col + 1];
/*  224: 562 */     for (int i = 0; i <= col; i++) {
/*  225: 564 */       cells[i] = getCell(i, row);
/*  226:     */     }
/*  227: 566 */     return cells;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public Cell[] getColumn(int col)
/*  231:     */   {
/*  232: 578 */     boolean found = false;
/*  233: 579 */     int row = this.numRows - 1;
/*  234: 581 */     while ((row >= 0) && (!found)) {
/*  235: 583 */       if (getCell(col, row).getType() != CellType.EMPTY) {
/*  236: 585 */         found = true;
/*  237:     */       } else {
/*  238: 589 */         row--;
/*  239:     */       }
/*  240:     */     }
/*  241: 594 */     Cell[] cells = new Cell[row + 1];
/*  242: 596 */     for (int i = 0; i <= row; i++) {
/*  243: 598 */       cells[i] = getCell(col, i);
/*  244:     */     }
/*  245: 600 */     return cells;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public String getName()
/*  249:     */   {
/*  250: 610 */     return this.name;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public void insertRow(int row)
/*  254:     */   {
/*  255: 621 */     if ((row < 0) || (row >= this.numRows)) {
/*  256: 623 */       return;
/*  257:     */     }
/*  258: 627 */     RowRecord[] oldRows = this.rows;
/*  259: 629 */     if (this.numRows == this.rows.length) {
/*  260: 631 */       this.rows = new RowRecord[oldRows.length + 10];
/*  261:     */     } else {
/*  262: 635 */       this.rows = new RowRecord[oldRows.length];
/*  263:     */     }
/*  264: 639 */     System.arraycopy(oldRows, 0, this.rows, 0, row);
/*  265:     */     
/*  266:     */ 
/*  267: 642 */     System.arraycopy(oldRows, row, this.rows, row + 1, this.numRows - row);
/*  268: 645 */     for (int i = row + 1; i <= this.numRows; i++) {
/*  269: 647 */       if (this.rows[i] != null) {
/*  270: 649 */         this.rows[i].incrementRow();
/*  271:     */       }
/*  272:     */     }
/*  273: 654 */     HyperlinkRecord hr = null;
/*  274: 655 */     Iterator i = this.hyperlinks.iterator();
/*  275: 656 */     while (i.hasNext())
/*  276:     */     {
/*  277: 658 */       hr = (HyperlinkRecord)i.next();
/*  278: 659 */       hr.insertRow(row);
/*  279:     */     }
/*  280: 663 */     if (this.dataValidation != null) {
/*  281: 665 */       this.dataValidation.insertRow(row);
/*  282:     */     }
/*  283:     */     Iterator vci;
/*  284: 668 */     if ((this.validatedCells != null) && (this.validatedCells.size() > 0)) {
/*  285: 670 */       for (vci = this.validatedCells.iterator(); vci.hasNext();)
/*  286:     */       {
/*  287: 672 */         CellValue cv = (CellValue)vci.next();
/*  288: 673 */         CellFeatures cf = cv.getCellFeatures();
/*  289: 674 */         if (cf.getDVParser() != null) {
/*  290: 676 */           cf.getDVParser().insertRow(row);
/*  291:     */         }
/*  292:     */       }
/*  293:     */     }
/*  294: 682 */     this.mergedCells.insertRow(row);
/*  295:     */     
/*  296:     */ 
/*  297: 685 */     ArrayList newRowBreaks = new ArrayList();
/*  298: 686 */     Iterator ri = this.rowBreaks.iterator();
/*  299: 687 */     while (ri.hasNext())
/*  300:     */     {
/*  301: 689 */       int val = ((Integer)ri.next()).intValue();
/*  302: 690 */       if (val >= row) {
/*  303: 692 */         val++;
/*  304:     */       }
/*  305: 695 */       newRowBreaks.add(new Integer(val));
/*  306:     */     }
/*  307: 697 */     this.rowBreaks = newRowBreaks;
/*  308: 700 */     for (Iterator cfit = this.conditionalFormats.iterator(); cfit.hasNext();)
/*  309:     */     {
/*  310: 702 */       ConditionalFormat cf = (ConditionalFormat)cfit.next();
/*  311: 703 */       cf.insertRow(row);
/*  312:     */     }
/*  313: 707 */     if (this.workbookSettings.getFormulaAdjust()) {
/*  314: 709 */       this.workbook.rowInserted(this, row);
/*  315:     */     }
/*  316: 713 */     this.numRows += 1;
/*  317:     */   }
/*  318:     */   
/*  319:     */   public void insertColumn(int col)
/*  320:     */   {
/*  321: 726 */     if ((col < 0) || (col >= this.numColumns)) {
/*  322: 728 */       return;
/*  323:     */     }
/*  324: 732 */     for (int i = 0; i < this.numRows; i++) {
/*  325: 734 */       if (this.rows[i] != null) {
/*  326: 736 */         this.rows[i].insertColumn(col);
/*  327:     */       }
/*  328:     */     }
/*  329: 741 */     HyperlinkRecord hr = null;
/*  330: 742 */     Iterator i = this.hyperlinks.iterator();
/*  331: 743 */     while (i.hasNext())
/*  332:     */     {
/*  333: 745 */       hr = (HyperlinkRecord)i.next();
/*  334: 746 */       hr.insertColumn(col);
/*  335:     */     }
/*  336: 750 */     i = this.columnFormats.iterator();
/*  337: 751 */     while (i.hasNext())
/*  338:     */     {
/*  339: 753 */       ColumnInfoRecord cir = (ColumnInfoRecord)i.next();
/*  340: 755 */       if (cir.getColumn() >= col) {
/*  341: 757 */         cir.incrementColumn();
/*  342:     */       }
/*  343:     */     }
/*  344: 762 */     if (this.autosizedColumns.size() > 0)
/*  345:     */     {
/*  346: 764 */       TreeSet newAutosized = new TreeSet();
/*  347: 765 */       i = this.autosizedColumns.iterator();
/*  348: 766 */       while (i.hasNext())
/*  349:     */       {
/*  350: 768 */         Integer colnumber = (Integer)i.next();
/*  351: 770 */         if (colnumber.intValue() >= col) {
/*  352: 772 */           newAutosized.add(new Integer(colnumber.intValue() + 1));
/*  353:     */         } else {
/*  354: 776 */           newAutosized.add(colnumber);
/*  355:     */         }
/*  356:     */       }
/*  357: 779 */       this.autosizedColumns = newAutosized;
/*  358:     */     }
/*  359: 783 */     if (this.dataValidation != null) {
/*  360: 785 */       this.dataValidation.insertColumn(col);
/*  361:     */     }
/*  362:     */     Iterator vci;
/*  363: 788 */     if ((this.validatedCells != null) && (this.validatedCells.size() > 0)) {
/*  364: 790 */       for (vci = this.validatedCells.iterator(); vci.hasNext();)
/*  365:     */       {
/*  366: 792 */         CellValue cv = (CellValue)vci.next();
/*  367: 793 */         CellFeatures cf = cv.getCellFeatures();
/*  368: 794 */         if (cf.getDVParser() != null) {
/*  369: 796 */           cf.getDVParser().insertColumn(col);
/*  370:     */         }
/*  371:     */       }
/*  372:     */     }
/*  373: 802 */     this.mergedCells.insertColumn(col);
/*  374:     */     
/*  375:     */ 
/*  376: 805 */     ArrayList newColumnBreaks = new ArrayList();
/*  377: 806 */     Iterator ri = this.columnBreaks.iterator();
/*  378: 807 */     while (ri.hasNext())
/*  379:     */     {
/*  380: 809 */       int val = ((Integer)ri.next()).intValue();
/*  381: 810 */       if (val >= col) {
/*  382: 812 */         val++;
/*  383:     */       }
/*  384: 815 */       newColumnBreaks.add(new Integer(val));
/*  385:     */     }
/*  386: 817 */     this.columnBreaks = newColumnBreaks;
/*  387: 820 */     for (Iterator cfit = this.conditionalFormats.iterator(); cfit.hasNext();)
/*  388:     */     {
/*  389: 822 */       ConditionalFormat cf = (ConditionalFormat)cfit.next();
/*  390: 823 */       cf.insertColumn(col);
/*  391:     */     }
/*  392: 827 */     if (this.workbookSettings.getFormulaAdjust()) {
/*  393: 829 */       this.workbook.columnInserted(this, col);
/*  394:     */     }
/*  395: 832 */     this.numColumns += 1;
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void removeColumn(int col)
/*  399:     */   {
/*  400: 843 */     if ((col < 0) || (col >= this.numColumns)) {
/*  401: 845 */       return;
/*  402:     */     }
/*  403: 849 */     for (int i = 0; i < this.numRows; i++) {
/*  404: 851 */       if (this.rows[i] != null) {
/*  405: 853 */         this.rows[i].removeColumn(col);
/*  406:     */       }
/*  407:     */     }
/*  408: 858 */     HyperlinkRecord hr = null;
/*  409: 859 */     Iterator i = this.hyperlinks.iterator();
/*  410: 860 */     while (i.hasNext())
/*  411:     */     {
/*  412: 862 */       hr = (HyperlinkRecord)i.next();
/*  413: 864 */       if ((hr.getColumn() == col) && (hr.getLastColumn() == col)) {
/*  414: 869 */         i.remove();
/*  415:     */       } else {
/*  416: 873 */         hr.removeColumn(col);
/*  417:     */       }
/*  418:     */     }
/*  419: 878 */     if (this.dataValidation != null) {
/*  420: 880 */       this.dataValidation.removeColumn(col);
/*  421:     */     }
/*  422:     */     Iterator vci;
/*  423: 883 */     if ((this.validatedCells != null) && (this.validatedCells.size() > 0)) {
/*  424: 885 */       for (vci = this.validatedCells.iterator(); vci.hasNext();)
/*  425:     */       {
/*  426: 887 */         CellValue cv = (CellValue)vci.next();
/*  427: 888 */         CellFeatures cf = cv.getCellFeatures();
/*  428: 889 */         if (cf.getDVParser() != null) {
/*  429: 891 */           cf.getDVParser().removeColumn(col);
/*  430:     */         }
/*  431:     */       }
/*  432:     */     }
/*  433: 897 */     this.mergedCells.removeColumn(col);
/*  434:     */     
/*  435:     */ 
/*  436: 900 */     ArrayList newColumnBreaks = new ArrayList();
/*  437: 901 */     Iterator ri = this.columnBreaks.iterator();
/*  438: 902 */     while (ri.hasNext())
/*  439:     */     {
/*  440: 904 */       int val = ((Integer)ri.next()).intValue();
/*  441: 906 */       if (val != col)
/*  442:     */       {
/*  443: 908 */         if (val > col) {
/*  444: 910 */           val--;
/*  445:     */         }
/*  446: 913 */         newColumnBreaks.add(new Integer(val));
/*  447:     */       }
/*  448:     */     }
/*  449: 917 */     this.columnBreaks = newColumnBreaks;
/*  450:     */     
/*  451:     */ 
/*  452:     */ 
/*  453: 921 */     i = this.columnFormats.iterator();
/*  454: 922 */     ColumnInfoRecord removeColumn = null;
/*  455: 923 */     while (i.hasNext())
/*  456:     */     {
/*  457: 925 */       ColumnInfoRecord cir = (ColumnInfoRecord)i.next();
/*  458: 927 */       if (cir.getColumn() == col) {
/*  459: 929 */         removeColumn = cir;
/*  460: 931 */       } else if (cir.getColumn() > col) {
/*  461: 933 */         cir.decrementColumn();
/*  462:     */       }
/*  463:     */     }
/*  464: 937 */     if (removeColumn != null) {
/*  465: 939 */       this.columnFormats.remove(removeColumn);
/*  466:     */     }
/*  467: 943 */     if (this.autosizedColumns.size() > 0)
/*  468:     */     {
/*  469: 945 */       TreeSet newAutosized = new TreeSet();
/*  470: 946 */       i = this.autosizedColumns.iterator();
/*  471: 947 */       while (i.hasNext())
/*  472:     */       {
/*  473: 949 */         Integer colnumber = (Integer)i.next();
/*  474: 951 */         if (colnumber.intValue() != col) {
/*  475: 955 */           if (colnumber.intValue() > col) {
/*  476: 957 */             newAutosized.add(new Integer(colnumber.intValue() - 1));
/*  477:     */           } else {
/*  478: 961 */             newAutosized.add(colnumber);
/*  479:     */           }
/*  480:     */         }
/*  481:     */       }
/*  482: 964 */       this.autosizedColumns = newAutosized;
/*  483:     */     }
/*  484: 968 */     for (Iterator cfit = this.conditionalFormats.iterator(); cfit.hasNext();)
/*  485:     */     {
/*  486: 970 */       ConditionalFormat cf = (ConditionalFormat)cfit.next();
/*  487: 971 */       cf.removeColumn(col);
/*  488:     */     }
/*  489: 975 */     if (this.workbookSettings.getFormulaAdjust()) {
/*  490: 977 */       this.workbook.columnRemoved(this, col);
/*  491:     */     }
/*  492: 980 */     this.numColumns -= 1;
/*  493:     */   }
/*  494:     */   
/*  495:     */   public void removeRow(int row)
/*  496:     */   {
/*  497: 991 */     if ((row < 0) || (row >= this.numRows))
/*  498:     */     {
/*  499: 994 */       if (this.workbookSettings.getFormulaAdjust()) {
/*  500: 996 */         this.workbook.rowRemoved(this, row);
/*  501:     */       }
/*  502: 999 */       return;
/*  503:     */     }
/*  504:1003 */     RowRecord[] oldRows = this.rows;
/*  505:     */     
/*  506:1005 */     this.rows = new RowRecord[oldRows.length];
/*  507:     */     
/*  508:     */ 
/*  509:1008 */     System.arraycopy(oldRows, 0, this.rows, 0, row);
/*  510:     */     
/*  511:     */ 
/*  512:1011 */     System.arraycopy(oldRows, row + 1, this.rows, row, this.numRows - (row + 1));
/*  513:1014 */     for (int i = row; i < this.numRows; i++) {
/*  514:1016 */       if (this.rows[i] != null) {
/*  515:1018 */         this.rows[i].decrementRow();
/*  516:     */       }
/*  517:     */     }
/*  518:1023 */     HyperlinkRecord hr = null;
/*  519:1024 */     Iterator i = this.hyperlinks.iterator();
/*  520:1025 */     while (i.hasNext())
/*  521:     */     {
/*  522:1027 */       hr = (HyperlinkRecord)i.next();
/*  523:1029 */       if ((hr.getRow() == row) && (hr.getLastRow() == row)) {
/*  524:1034 */         i.remove();
/*  525:     */       } else {
/*  526:1038 */         hr.removeRow(row);
/*  527:     */       }
/*  528:     */     }
/*  529:1043 */     if (this.dataValidation != null) {
/*  530:1045 */       this.dataValidation.removeRow(row);
/*  531:     */     }
/*  532:     */     Iterator vci;
/*  533:1048 */     if ((this.validatedCells != null) && (this.validatedCells.size() > 0)) {
/*  534:1050 */       for (vci = this.validatedCells.iterator(); vci.hasNext();)
/*  535:     */       {
/*  536:1052 */         CellValue cv = (CellValue)vci.next();
/*  537:1053 */         CellFeatures cf = cv.getCellFeatures();
/*  538:1054 */         if (cf.getDVParser() != null) {
/*  539:1056 */           cf.getDVParser().removeRow(row);
/*  540:     */         }
/*  541:     */       }
/*  542:     */     }
/*  543:1062 */     this.mergedCells.removeRow(row);
/*  544:     */     
/*  545:     */ 
/*  546:1065 */     ArrayList newRowBreaks = new ArrayList();
/*  547:1066 */     Iterator ri = this.rowBreaks.iterator();
/*  548:1067 */     while (ri.hasNext())
/*  549:     */     {
/*  550:1069 */       int val = ((Integer)ri.next()).intValue();
/*  551:1071 */       if (val != row)
/*  552:     */       {
/*  553:1073 */         if (val > row) {
/*  554:1075 */           val--;
/*  555:     */         }
/*  556:1078 */         newRowBreaks.add(new Integer(val));
/*  557:     */       }
/*  558:     */     }
/*  559:1082 */     this.rowBreaks = newRowBreaks;
/*  560:1085 */     for (Iterator cfit = this.conditionalFormats.iterator(); cfit.hasNext();)
/*  561:     */     {
/*  562:1087 */       ConditionalFormat cf = (ConditionalFormat)cfit.next();
/*  563:1088 */       cf.removeRow(row);
/*  564:     */     }
/*  565:1092 */     if (this.workbookSettings.getFormulaAdjust()) {
/*  566:1094 */       this.workbook.rowRemoved(this, row);
/*  567:     */     }
/*  568:1110 */     this.numRows -= 1;
/*  569:     */   }
/*  570:     */   
/*  571:     */   public void addCell(WritableCell cell)
/*  572:     */     throws WriteException, RowsExceededException
/*  573:     */   {
/*  574:1133 */     if (cell.getType() == CellType.EMPTY) {
/*  575:1135 */       if ((cell != null) && (cell.getCellFormat() == null)) {
/*  576:1139 */         return;
/*  577:     */       }
/*  578:     */     }
/*  579:1143 */     CellValue cv = (CellValue)cell;
/*  580:1145 */     if (cv.isReferenced()) {
/*  581:1147 */       throw new JxlWriteException(JxlWriteException.cellReferenced);
/*  582:     */     }
/*  583:1150 */     int row = cell.getRow();
/*  584:1151 */     RowRecord rowrec = getRowRecord(row);
/*  585:     */     
/*  586:1153 */     CellValue curcell = rowrec.getCell(cv.getColumn());
/*  587:1154 */     boolean curSharedValidation = (curcell != null) && (curcell.getCellFeatures() != null) && (curcell.getCellFeatures().getDVParser() != null) && (curcell.getCellFeatures().getDVParser().extendedCellsValidation());
/*  588:1161 */     if ((cell.getCellFeatures() != null) && (cell.getCellFeatures().hasDataValidation()) && (curSharedValidation))
/*  589:     */     {
/*  590:1165 */       DVParser dvp = curcell.getCellFeatures().getDVParser();
/*  591:1166 */       logger.warn("Cannot add cell at " + CellReferenceHelper.getCellReference(cv) + " because it is part of the shared cell validation group " + CellReferenceHelper.getCellReference(dvp.getFirstColumn(), dvp.getFirstRow()) + "-" + CellReferenceHelper.getCellReference(dvp.getLastColumn(), dvp.getLastRow()));
/*  592:     */       
/*  593:     */ 
/*  594:     */ 
/*  595:     */ 
/*  596:     */ 
/*  597:     */ 
/*  598:     */ 
/*  599:1174 */       return;
/*  600:     */     }
/*  601:1178 */     if (curSharedValidation)
/*  602:     */     {
/*  603:1180 */       WritableCellFeatures wcf = cell.getWritableCellFeatures();
/*  604:1182 */       if (wcf == null)
/*  605:     */       {
/*  606:1184 */         wcf = new WritableCellFeatures();
/*  607:1185 */         cell.setCellFeatures(wcf);
/*  608:     */       }
/*  609:1188 */       wcf.shareDataValidation(curcell.getCellFeatures());
/*  610:     */     }
/*  611:1191 */     rowrec.addCell(cv);
/*  612:     */     
/*  613:     */ 
/*  614:1194 */     this.numRows = Math.max(row + 1, this.numRows);
/*  615:1195 */     this.numColumns = Math.max(this.numColumns, rowrec.getMaxColumn());
/*  616:     */     
/*  617:     */ 
/*  618:     */ 
/*  619:1199 */     cv.setCellDetails(this.formatRecords, this.sharedStrings, this);
/*  620:     */   }
/*  621:     */   
/*  622:     */   RowRecord getRowRecord(int row)
/*  623:     */     throws RowsExceededException
/*  624:     */   {
/*  625:1212 */     if (row >= 65536) {
/*  626:1214 */       throw new RowsExceededException();
/*  627:     */     }
/*  628:1220 */     if (row >= this.rows.length)
/*  629:     */     {
/*  630:1222 */       RowRecord[] oldRows = this.rows;
/*  631:1223 */       this.rows = new RowRecord[Math.max(oldRows.length + 10, row + 1)];
/*  632:1224 */       System.arraycopy(oldRows, 0, this.rows, 0, oldRows.length);
/*  633:1225 */       oldRows = null;
/*  634:     */     }
/*  635:1228 */     RowRecord rowrec = this.rows[row];
/*  636:1230 */     if (rowrec == null)
/*  637:     */     {
/*  638:1232 */       rowrec = new RowRecord(row, this);
/*  639:1233 */       this.rows[row] = rowrec;
/*  640:     */     }
/*  641:1236 */     return rowrec;
/*  642:     */   }
/*  643:     */   
/*  644:     */   RowRecord getRowInfo(int r)
/*  645:     */   {
/*  646:1247 */     if ((r < 0) || (r > this.rows.length)) {
/*  647:1249 */       return null;
/*  648:     */     }
/*  649:1252 */     return this.rows[r];
/*  650:     */   }
/*  651:     */   
/*  652:     */   ColumnInfoRecord getColumnInfo(int c)
/*  653:     */   {
/*  654:1263 */     Iterator i = this.columnFormats.iterator();
/*  655:1264 */     ColumnInfoRecord cir = null;
/*  656:1265 */     boolean stop = false;
/*  657:1267 */     while ((i.hasNext()) && (!stop))
/*  658:     */     {
/*  659:1269 */       cir = (ColumnInfoRecord)i.next();
/*  660:1271 */       if (cir.getColumn() >= c) {
/*  661:1273 */         stop = true;
/*  662:     */       }
/*  663:     */     }
/*  664:1277 */     if (!stop) {
/*  665:1279 */       return null;
/*  666:     */     }
/*  667:1282 */     return cir.getColumn() == c ? cir : null;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public void setName(String n)
/*  671:     */   {
/*  672:1292 */     this.name = n;
/*  673:     */   }
/*  674:     */   
/*  675:     */   /**
/*  676:     */    * @deprecated
/*  677:     */    */
/*  678:     */   public void setHidden(boolean h)
/*  679:     */   {
/*  680:1303 */     this.settings.setHidden(h);
/*  681:     */   }
/*  682:     */   
/*  683:     */   /**
/*  684:     */    * @deprecated
/*  685:     */    */
/*  686:     */   public void setProtected(boolean prot)
/*  687:     */   {
/*  688:1314 */     this.settings.setProtected(prot);
/*  689:     */   }
/*  690:     */   
/*  691:     */   /**
/*  692:     */    * @deprecated
/*  693:     */    */
/*  694:     */   public void setSelected()
/*  695:     */   {
/*  696:1323 */     this.settings.setSelected();
/*  697:     */   }
/*  698:     */   
/*  699:     */   /**
/*  700:     */    * @deprecated
/*  701:     */    */
/*  702:     */   public boolean isHidden()
/*  703:     */   {
/*  704:1334 */     return this.settings.isHidden();
/*  705:     */   }
/*  706:     */   
/*  707:     */   public void setColumnView(int col, int width)
/*  708:     */   {
/*  709:1345 */     CellView cv = new CellView();
/*  710:1346 */     cv.setSize(width * 256);
/*  711:1347 */     setColumnView(col, cv);
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void setColumnView(int col, int width, CellFormat format)
/*  715:     */   {
/*  716:1360 */     CellView cv = new CellView();
/*  717:1361 */     cv.setSize(width * 256);
/*  718:1362 */     cv.setFormat(format);
/*  719:1363 */     setColumnView(col, cv);
/*  720:     */   }
/*  721:     */   
/*  722:     */   public void setColumnView(int col, CellView view)
/*  723:     */   {
/*  724:1374 */     XFRecord xfr = (XFRecord)view.getFormat();
/*  725:1375 */     if (xfr == null)
/*  726:     */     {
/*  727:1377 */       Styles styles = getWorkbook().getStyles();
/*  728:1378 */       xfr = styles.getNormalStyle();
/*  729:     */     }
/*  730:     */     try
/*  731:     */     {
/*  732:1383 */       if (!xfr.isInitialized()) {
/*  733:1385 */         this.formatRecords.addStyle(xfr);
/*  734:     */       }
/*  735:1388 */       int width = view.depUsed() ? view.getDimension() * 256 : view.getSize();
/*  736:1390 */       if (view.isAutosize()) {
/*  737:1392 */         this.autosizedColumns.add(new Integer(col));
/*  738:     */       }
/*  739:1395 */       ColumnInfoRecord cir = new ColumnInfoRecord(col, width, xfr);
/*  740:1399 */       if (view.isHidden()) {
/*  741:1401 */         cir.setHidden(true);
/*  742:     */       }
/*  743:1404 */       if (!this.columnFormats.contains(cir))
/*  744:     */       {
/*  745:1406 */         this.columnFormats.add(cir);
/*  746:     */       }
/*  747:     */       else
/*  748:     */       {
/*  749:1410 */         this.columnFormats.remove(cir);
/*  750:1411 */         this.columnFormats.add(cir);
/*  751:     */       }
/*  752:     */     }
/*  753:     */     catch (NumFormatRecordsException e)
/*  754:     */     {
/*  755:1416 */       logger.warn("Maximum number of format records exceeded.  Using default format.");
/*  756:     */       
/*  757:     */ 
/*  758:1419 */       ColumnInfoRecord cir = new ColumnInfoRecord(col, view.getDimension() * 256, WritableWorkbook.NORMAL_STYLE);
/*  759:1421 */       if (!this.columnFormats.contains(cir)) {
/*  760:1423 */         this.columnFormats.add(cir);
/*  761:     */       }
/*  762:     */     }
/*  763:     */   }
/*  764:     */   
/*  765:     */   /**
/*  766:     */    * @deprecated
/*  767:     */    */
/*  768:     */   public void setRowView(int row, int height)
/*  769:     */     throws RowsExceededException
/*  770:     */   {
/*  771:1439 */     CellView cv = new CellView();
/*  772:1440 */     cv.setSize(height);
/*  773:1441 */     cv.setHidden(false);
/*  774:1442 */     setRowView(row, cv);
/*  775:     */   }
/*  776:     */   
/*  777:     */   /**
/*  778:     */    * @deprecated
/*  779:     */    */
/*  780:     */   public void setRowView(int row, boolean collapsed)
/*  781:     */     throws RowsExceededException
/*  782:     */   {
/*  783:1456 */     CellView cv = new CellView();
/*  784:1457 */     cv.setHidden(collapsed);
/*  785:1458 */     setRowView(row, cv);
/*  786:     */   }
/*  787:     */   
/*  788:     */   /**
/*  789:     */    * @deprecated
/*  790:     */    */
/*  791:     */   public void setRowView(int row, int height, boolean collapsed)
/*  792:     */     throws RowsExceededException
/*  793:     */   {
/*  794:1475 */     CellView cv = new CellView();
/*  795:1476 */     cv.setSize(height);
/*  796:1477 */     cv.setHidden(collapsed);
/*  797:1478 */     setRowView(row, cv);
/*  798:     */   }
/*  799:     */   
/*  800:     */   public void setRowView(int row, CellView view)
/*  801:     */     throws RowsExceededException
/*  802:     */   {
/*  803:1490 */     RowRecord rowrec = getRowRecord(row);
/*  804:     */     
/*  805:1492 */     XFRecord xfr = (XFRecord)view.getFormat();
/*  806:     */     try
/*  807:     */     {
/*  808:1496 */       if (xfr != null) {
/*  809:1498 */         if (!xfr.isInitialized()) {
/*  810:1500 */           this.formatRecords.addStyle(xfr);
/*  811:     */         }
/*  812:     */       }
/*  813:     */     }
/*  814:     */     catch (NumFormatRecordsException e)
/*  815:     */     {
/*  816:1506 */       logger.warn("Maximum number of format records exceeded.  Using default format.");
/*  817:     */       
/*  818:     */ 
/*  819:1509 */       xfr = null;
/*  820:     */     }
/*  821:1512 */     rowrec.setRowDetails(view.getSize(), false, view.isHidden(), 0, false, xfr);
/*  822:     */     
/*  823:     */ 
/*  824:     */ 
/*  825:     */ 
/*  826:     */ 
/*  827:1518 */     this.numRows = Math.max(this.numRows, row + 1);
/*  828:     */   }
/*  829:     */   
/*  830:     */   public void write()
/*  831:     */     throws IOException
/*  832:     */   {
/*  833:1530 */     boolean dmod = this.drawingsModified;
/*  834:1531 */     if (this.workbook.getDrawingGroup() != null) {
/*  835:1533 */       dmod |= this.workbook.getDrawingGroup().hasDrawingsOmitted();
/*  836:     */     }
/*  837:1536 */     if (this.autosizedColumns.size() > 0) {
/*  838:1538 */       autosizeColumns();
/*  839:     */     }
/*  840:1541 */     this.sheetWriter.setWriteData(this.rows, this.rowBreaks, this.columnBreaks, this.hyperlinks, this.mergedCells, this.columnFormats, this.maxRowOutlineLevel, this.maxColumnOutlineLevel);
/*  841:     */     
/*  842:     */ 
/*  843:     */ 
/*  844:     */ 
/*  845:     */ 
/*  846:     */ 
/*  847:     */ 
/*  848:1549 */     this.sheetWriter.setDimensions(getRows(), getColumns());
/*  849:1550 */     this.sheetWriter.setSettings(this.settings);
/*  850:1551 */     this.sheetWriter.setPLS(this.plsRecord);
/*  851:1552 */     this.sheetWriter.setDrawings(this.drawings, dmod);
/*  852:1553 */     this.sheetWriter.setButtonPropertySet(this.buttonPropertySet);
/*  853:1554 */     this.sheetWriter.setDataValidation(this.dataValidation, this.validatedCells);
/*  854:1555 */     this.sheetWriter.setConditionalFormats(this.conditionalFormats);
/*  855:1556 */     this.sheetWriter.setAutoFilter(this.autoFilter);
/*  856:     */     
/*  857:1558 */     this.sheetWriter.write();
/*  858:     */   }
/*  859:     */   
/*  860:     */   void copy(Sheet s)
/*  861:     */   {
/*  862:1569 */     this.settings = new SheetSettings(s.getSettings(), this);
/*  863:     */     
/*  864:1571 */     SheetCopier si = new SheetCopier(s, this);
/*  865:1572 */     si.setColumnFormats(this.columnFormats);
/*  866:1573 */     si.setFormatRecords(this.formatRecords);
/*  867:1574 */     si.setHyperlinks(this.hyperlinks);
/*  868:1575 */     si.setMergedCells(this.mergedCells);
/*  869:1576 */     si.setRowBreaks(this.rowBreaks);
/*  870:1577 */     si.setColumnBreaks(this.columnBreaks);
/*  871:1578 */     si.setSheetWriter(this.sheetWriter);
/*  872:1579 */     si.setDrawings(this.drawings);
/*  873:1580 */     si.setImages(this.images);
/*  874:1581 */     si.setConditionalFormats(this.conditionalFormats);
/*  875:1582 */     si.setValidatedCells(this.validatedCells);
/*  876:     */     
/*  877:1584 */     si.copySheet();
/*  878:     */     
/*  879:1586 */     this.dataValidation = si.getDataValidation();
/*  880:1587 */     this.comboBox = si.getComboBox();
/*  881:1588 */     this.plsRecord = si.getPLSRecord();
/*  882:1589 */     this.chartOnly = si.isChartOnly();
/*  883:1590 */     this.buttonPropertySet = si.getButtonPropertySet();
/*  884:1591 */     this.numRows = si.getRows();
/*  885:1592 */     this.autoFilter = si.getAutoFilter();
/*  886:1593 */     this.maxRowOutlineLevel = si.getMaxRowOutlineLevel();
/*  887:1594 */     this.maxColumnOutlineLevel = si.getMaxColumnOutlineLevel();
/*  888:     */   }
/*  889:     */   
/*  890:     */   void copy(WritableSheet s)
/*  891:     */   {
/*  892:1604 */     this.settings = new SheetSettings(s.getSettings(), this);
/*  893:1605 */     WritableSheetImpl si = (WritableSheetImpl)s;
/*  894:     */     
/*  895:1607 */     WritableSheetCopier sc = new WritableSheetCopier(s, this);
/*  896:1608 */     sc.setColumnFormats(si.columnFormats, this.columnFormats);
/*  897:1609 */     sc.setMergedCells(si.mergedCells, this.mergedCells);
/*  898:1610 */     sc.setRows(si.rows);
/*  899:1611 */     sc.setRowBreaks(si.rowBreaks, this.rowBreaks);
/*  900:1612 */     sc.setColumnBreaks(si.columnBreaks, this.columnBreaks);
/*  901:1613 */     sc.setDataValidation(si.dataValidation);
/*  902:1614 */     sc.setSheetWriter(this.sheetWriter);
/*  903:1615 */     sc.setDrawings(si.drawings, this.drawings, this.images);
/*  904:1616 */     sc.setWorkspaceOptions(si.getWorkspaceOptions());
/*  905:1617 */     sc.setPLSRecord(si.plsRecord);
/*  906:1618 */     sc.setButtonPropertySetRecord(si.buttonPropertySet);
/*  907:1619 */     sc.setHyperlinks(si.hyperlinks, this.hyperlinks);
/*  908:1620 */     sc.setValidatedCells(this.validatedCells);
/*  909:     */     
/*  910:1622 */     sc.copySheet();
/*  911:     */     
/*  912:1624 */     this.dataValidation = sc.getDataValidation();
/*  913:1625 */     this.plsRecord = sc.getPLSRecord();
/*  914:1626 */     this.buttonPropertySet = sc.getButtonPropertySet();
/*  915:     */   }
/*  916:     */   
/*  917:     */   final HeaderRecord getHeader()
/*  918:     */   {
/*  919:1636 */     return this.sheetWriter.getHeader();
/*  920:     */   }
/*  921:     */   
/*  922:     */   final FooterRecord getFooter()
/*  923:     */   {
/*  924:1646 */     return this.sheetWriter.getFooter();
/*  925:     */   }
/*  926:     */   
/*  927:     */   /**
/*  928:     */    * @deprecated
/*  929:     */    */
/*  930:     */   public boolean isProtected()
/*  931:     */   {
/*  932:1656 */     return this.settings.isProtected();
/*  933:     */   }
/*  934:     */   
/*  935:     */   public Hyperlink[] getHyperlinks()
/*  936:     */   {
/*  937:1666 */     Hyperlink[] hl = new Hyperlink[this.hyperlinks.size()];
/*  938:1668 */     for (int i = 0; i < this.hyperlinks.size(); i++) {
/*  939:1670 */       hl[i] = ((Hyperlink)this.hyperlinks.get(i));
/*  940:     */     }
/*  941:1673 */     return hl;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public Range[] getMergedCells()
/*  945:     */   {
/*  946:1683 */     return this.mergedCells.getMergedCells();
/*  947:     */   }
/*  948:     */   
/*  949:     */   public WritableHyperlink[] getWritableHyperlinks()
/*  950:     */   {
/*  951:1693 */     WritableHyperlink[] hl = new WritableHyperlink[this.hyperlinks.size()];
/*  952:1695 */     for (int i = 0; i < this.hyperlinks.size(); i++) {
/*  953:1697 */       hl[i] = ((WritableHyperlink)this.hyperlinks.get(i));
/*  954:     */     }
/*  955:1700 */     return hl;
/*  956:     */   }
/*  957:     */   
/*  958:     */   public void removeHyperlink(WritableHyperlink h)
/*  959:     */   {
/*  960:1717 */     removeHyperlink(h, false);
/*  961:     */   }
/*  962:     */   
/*  963:     */   public void removeHyperlink(WritableHyperlink h, boolean preserveLabel)
/*  964:     */   {
/*  965:1737 */     this.hyperlinks.remove(this.hyperlinks.indexOf(h));
/*  966:1739 */     if (!preserveLabel)
/*  967:     */     {
/*  968:1743 */       Assert.verify((this.rows.length > h.getRow()) && (this.rows[h.getRow()] != null));
/*  969:1744 */       this.rows[h.getRow()].removeCell(h.getColumn());
/*  970:     */     }
/*  971:     */   }
/*  972:     */   
/*  973:     */   public void addHyperlink(WritableHyperlink h)
/*  974:     */     throws WriteException, RowsExceededException
/*  975:     */   {
/*  976:1759 */     Cell c = getCell(h.getColumn(), h.getRow());
/*  977:     */     
/*  978:1761 */     String contents = null;
/*  979:1762 */     if ((h.isFile()) || (h.isUNC()))
/*  980:     */     {
/*  981:1764 */       String cnts = h.getContents();
/*  982:1765 */       if (cnts == null) {
/*  983:1767 */         contents = h.getFile().getPath();
/*  984:     */       } else {
/*  985:1771 */         contents = cnts;
/*  986:     */       }
/*  987:     */     }
/*  988:1774 */     else if (h.isURL())
/*  989:     */     {
/*  990:1776 */       String cnts = h.getContents();
/*  991:1777 */       if (cnts == null) {
/*  992:1779 */         contents = h.getURL().toString();
/*  993:     */       } else {
/*  994:1783 */         contents = cnts;
/*  995:     */       }
/*  996:     */     }
/*  997:1786 */     else if (h.isLocation())
/*  998:     */     {
/*  999:1788 */       contents = h.getContents();
/* 1000:     */     }
/* 1001:1795 */     if (c.getType() == CellType.LABEL)
/* 1002:     */     {
/* 1003:1797 */       Label l = (Label)c;
/* 1004:1798 */       l.setString(contents);
/* 1005:1799 */       WritableCellFormat wcf = new WritableCellFormat(l.getCellFormat());
/* 1006:1800 */       wcf.setFont(WritableWorkbook.HYPERLINK_FONT);
/* 1007:1801 */       l.setCellFormat(wcf);
/* 1008:     */     }
/* 1009:     */     else
/* 1010:     */     {
/* 1011:1805 */       Label l = new Label(h.getColumn(), h.getRow(), contents, WritableWorkbook.HYPERLINK_STYLE);
/* 1012:     */       
/* 1013:1807 */       addCell(l);
/* 1014:     */     }
/* 1015:1811 */     for (int i = h.getRow(); i <= h.getLastRow(); i++) {
/* 1016:1813 */       for (int j = h.getColumn(); j <= h.getLastColumn(); j++) {
/* 1017:1815 */         if ((i != h.getRow()) && (j != h.getColumn())) {
/* 1018:1818 */           if ((this.rows.length < h.getLastColumn()) && (this.rows[i] != null)) {
/* 1019:1820 */             this.rows[i].removeCell(j);
/* 1020:     */           }
/* 1021:     */         }
/* 1022:     */       }
/* 1023:     */     }
/* 1024:1826 */     h.initialize(this);
/* 1025:1827 */     this.hyperlinks.add(h);
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public Range mergeCells(int col1, int row1, int col2, int row2)
/* 1029:     */     throws WriteException, RowsExceededException
/* 1030:     */   {
/* 1031:1846 */     if ((col2 < col1) || (row2 < row1)) {
/* 1032:1848 */       logger.warn("Cannot merge cells - top left and bottom right incorrectly specified");
/* 1033:     */     }
/* 1034:1853 */     if ((col2 >= this.numColumns) || (row2 >= this.numRows)) {
/* 1035:1855 */       addCell(new Blank(col2, row2));
/* 1036:     */     }
/* 1037:1858 */     SheetRangeImpl range = new SheetRangeImpl(this, col1, row1, col2, row2);
/* 1038:1859 */     this.mergedCells.add(range);
/* 1039:     */     
/* 1040:1861 */     return range;
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   public void setRowGroup(int row1, int row2, boolean collapsed)
/* 1044:     */     throws WriteException, RowsExceededException
/* 1045:     */   {
/* 1046:1877 */     if (row2 < row1) {
/* 1047:1879 */       logger.warn("Cannot merge cells - top and bottom rows incorrectly specified");
/* 1048:     */     }
/* 1049:1883 */     for (int i = row1; i <= row2; i++)
/* 1050:     */     {
/* 1051:1885 */       RowRecord row = getRowRecord(i);
/* 1052:1886 */       this.numRows = Math.max(i + 1, this.numRows);
/* 1053:1887 */       row.incrementOutlineLevel();
/* 1054:1888 */       row.setCollapsed(collapsed);
/* 1055:1889 */       this.maxRowOutlineLevel = Math.max(this.maxRowOutlineLevel, row.getOutlineLevel());
/* 1056:     */     }
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public void unsetRowGroup(int row1, int row2)
/* 1060:     */     throws WriteException, RowsExceededException
/* 1061:     */   {
/* 1062:1905 */     if (row2 < row1) {
/* 1063:1907 */       logger.warn("Cannot merge cells - top and bottom rows incorrectly specified");
/* 1064:     */     }
/* 1065:1912 */     if (row2 >= this.numRows)
/* 1066:     */     {
/* 1067:1914 */       logger.warn("" + row2 + " is greater than the sheet bounds");
/* 1068:     */       
/* 1069:1916 */       row2 = this.numRows - 1;
/* 1070:     */     }
/* 1071:1919 */     for (int i = row1; i <= row2; i++) {
/* 1072:1921 */       this.rows[i].decrementOutlineLevel();
/* 1073:     */     }
/* 1074:1925 */     this.maxRowOutlineLevel = 0;
/* 1075:1926 */     for (int i = this.rows.length; i-- > 0;) {
/* 1076:1928 */       this.maxRowOutlineLevel = Math.max(this.maxRowOutlineLevel, this.rows[i].getOutlineLevel());
/* 1077:     */     }
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public void setColumnGroup(int col1, int col2, boolean collapsed)
/* 1081:     */     throws WriteException, RowsExceededException
/* 1082:     */   {
/* 1083:1945 */     if (col2 < col1) {
/* 1084:1947 */       logger.warn("Cannot merge cells - top and bottom rows incorrectly specified");
/* 1085:     */     }
/* 1086:1951 */     for (int i = col1; i <= col2; i++)
/* 1087:     */     {
/* 1088:1953 */       ColumnInfoRecord cir = getColumnInfo(i);
/* 1089:1957 */       if (cir == null)
/* 1090:     */       {
/* 1091:1959 */         setColumnView(i, new CellView());
/* 1092:1960 */         cir = getColumnInfo(i);
/* 1093:     */       }
/* 1094:1963 */       cir.incrementOutlineLevel();
/* 1095:1964 */       cir.setCollapsed(collapsed);
/* 1096:1965 */       this.maxColumnOutlineLevel = Math.max(this.maxColumnOutlineLevel, cir.getOutlineLevel());
/* 1097:     */     }
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public void unsetColumnGroup(int col1, int col2)
/* 1101:     */     throws WriteException, RowsExceededException
/* 1102:     */   {
/* 1103:1981 */     if (col2 < col1) {
/* 1104:1983 */       logger.warn("Cannot merge cells - top and bottom rows incorrectly specified");
/* 1105:     */     }
/* 1106:1987 */     for (int i = col1; i <= col2; i++)
/* 1107:     */     {
/* 1108:1989 */       ColumnInfoRecord cir = getColumnInfo(i);
/* 1109:1990 */       cir.decrementOutlineLevel();
/* 1110:     */     }
/* 1111:1994 */     this.maxColumnOutlineLevel = 0;
/* 1112:1995 */     for (Iterator it = this.columnFormats.iterator(); it.hasNext();)
/* 1113:     */     {
/* 1114:1997 */       ColumnInfoRecord cir = (ColumnInfoRecord)it.next();
/* 1115:1998 */       this.maxColumnOutlineLevel = Math.max(this.maxColumnOutlineLevel, cir.getOutlineLevel());
/* 1116:     */     }
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public void unmergeCells(Range r)
/* 1120:     */   {
/* 1121:2011 */     this.mergedCells.unmergeCells(r);
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   /**
/* 1125:     */    * @deprecated
/* 1126:     */    */
/* 1127:     */   public void setHeader(String l, String c, String r)
/* 1128:     */   {
/* 1129:2024 */     HeaderFooter header = new HeaderFooter();
/* 1130:2025 */     header.getLeft().append(l);
/* 1131:2026 */     header.getCentre().append(c);
/* 1132:2027 */     header.getRight().append(r);
/* 1133:2028 */     this.settings.setHeader(header);
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   /**
/* 1137:     */    * @deprecated
/* 1138:     */    */
/* 1139:     */   public void setFooter(String l, String c, String r)
/* 1140:     */   {
/* 1141:2041 */     HeaderFooter footer = new HeaderFooter();
/* 1142:2042 */     footer.getLeft().append(l);
/* 1143:2043 */     footer.getCentre().append(c);
/* 1144:2044 */     footer.getRight().append(r);
/* 1145:2045 */     this.settings.setFooter(footer);
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   /**
/* 1149:     */    * @deprecated
/* 1150:     */    */
/* 1151:     */   public void setPageSetup(PageOrientation p)
/* 1152:     */   {
/* 1153:2056 */     this.settings.setOrientation(p);
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   /**
/* 1157:     */    * @deprecated
/* 1158:     */    */
/* 1159:     */   public void setPageSetup(PageOrientation p, double hm, double fm)
/* 1160:     */   {
/* 1161:2069 */     this.settings.setOrientation(p);
/* 1162:2070 */     this.settings.setHeaderMargin(hm);
/* 1163:2071 */     this.settings.setFooterMargin(fm);
/* 1164:     */   }
/* 1165:     */   
/* 1166:     */   /**
/* 1167:     */    * @deprecated
/* 1168:     */    */
/* 1169:     */   public void setPageSetup(PageOrientation p, PaperSize ps, double hm, double fm)
/* 1170:     */   {
/* 1171:2086 */     this.settings.setPaperSize(ps);
/* 1172:2087 */     this.settings.setOrientation(p);
/* 1173:2088 */     this.settings.setHeaderMargin(hm);
/* 1174:2089 */     this.settings.setFooterMargin(fm);
/* 1175:     */   }
/* 1176:     */   
/* 1177:     */   public SheetSettings getSettings()
/* 1178:     */   {
/* 1179:2099 */     return this.settings;
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   WorkbookSettings getWorkbookSettings()
/* 1183:     */   {
/* 1184:2107 */     return this.workbookSettings;
/* 1185:     */   }
/* 1186:     */   
/* 1187:     */   public void addRowPageBreak(int row)
/* 1188:     */   {
/* 1189:2118 */     Iterator i = this.rowBreaks.iterator();
/* 1190:2119 */     boolean found = false;
/* 1191:2121 */     while ((i.hasNext()) && (!found)) {
/* 1192:2123 */       if (((Integer)i.next()).intValue() == row) {
/* 1193:2125 */         found = true;
/* 1194:     */       }
/* 1195:     */     }
/* 1196:2129 */     if (!found) {
/* 1197:2131 */       this.rowBreaks.add(new Integer(row));
/* 1198:     */     }
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   public void addColumnPageBreak(int col)
/* 1202:     */   {
/* 1203:2143 */     Iterator i = this.columnBreaks.iterator();
/* 1204:2144 */     boolean found = false;
/* 1205:2146 */     while ((i.hasNext()) && (!found)) {
/* 1206:2148 */       if (((Integer)i.next()).intValue() == col) {
/* 1207:2150 */         found = true;
/* 1208:     */       }
/* 1209:     */     }
/* 1210:2154 */     if (!found) {
/* 1211:2156 */       this.columnBreaks.add(new Integer(col));
/* 1212:     */     }
/* 1213:     */   }
/* 1214:     */   
/* 1215:     */   Chart[] getCharts()
/* 1216:     */   {
/* 1217:2167 */     return this.sheetWriter.getCharts();
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   private DrawingGroupObject[] getDrawings()
/* 1221:     */   {
/* 1222:2177 */     DrawingGroupObject[] dr = new DrawingGroupObject[this.drawings.size()];
/* 1223:2178 */     dr = (DrawingGroupObject[])this.drawings.toArray(dr);
/* 1224:2179 */     return dr;
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   void checkMergedBorders()
/* 1228:     */   {
/* 1229:2190 */     this.sheetWriter.setWriteData(this.rows, this.rowBreaks, this.columnBreaks, this.hyperlinks, this.mergedCells, this.columnFormats, this.maxRowOutlineLevel, this.maxColumnOutlineLevel);
/* 1230:     */     
/* 1231:     */ 
/* 1232:     */ 
/* 1233:     */ 
/* 1234:     */ 
/* 1235:     */ 
/* 1236:     */ 
/* 1237:2198 */     this.sheetWriter.setDimensions(getRows(), getColumns());
/* 1238:2199 */     this.sheetWriter.checkMergedBorders();
/* 1239:     */   }
/* 1240:     */   
/* 1241:     */   private WorkspaceInformationRecord getWorkspaceOptions()
/* 1242:     */   {
/* 1243:2209 */     return this.sheetWriter.getWorkspaceOptions();
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   void rationalize(IndexMapping xfMapping, IndexMapping fontMapping, IndexMapping formatMapping)
/* 1247:     */   {
/* 1248:2223 */     for (Iterator i = this.columnFormats.iterator(); i.hasNext();)
/* 1249:     */     {
/* 1250:2225 */       ColumnInfoRecord cir = (ColumnInfoRecord)i.next();
/* 1251:2226 */       cir.rationalize(xfMapping);
/* 1252:     */     }
/* 1253:2230 */     for (int i = 0; i < this.rows.length; i++) {
/* 1254:2232 */       if (this.rows[i] != null) {
/* 1255:2234 */         this.rows[i].rationalize(xfMapping);
/* 1256:     */       }
/* 1257:     */     }
/* 1258:2239 */     Chart[] charts = getCharts();
/* 1259:2240 */     for (int c = 0; c < charts.length; c++) {
/* 1260:2242 */       charts[c].rationalize(xfMapping, fontMapping, formatMapping);
/* 1261:     */     }
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   WritableWorkbookImpl getWorkbook()
/* 1265:     */   {
/* 1266:2252 */     return this.workbook;
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   /**
/* 1270:     */    * @deprecated
/* 1271:     */    */
/* 1272:     */   public CellFormat getColumnFormat(int col)
/* 1273:     */   {
/* 1274:2264 */     return getColumnView(col).getFormat();
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   /**
/* 1278:     */    * @deprecated
/* 1279:     */    */
/* 1280:     */   public int getColumnWidth(int col)
/* 1281:     */   {
/* 1282:2277 */     return getColumnView(col).getDimension();
/* 1283:     */   }
/* 1284:     */   
/* 1285:     */   /**
/* 1286:     */    * @deprecated
/* 1287:     */    */
/* 1288:     */   public int getRowHeight(int row)
/* 1289:     */   {
/* 1290:2290 */     return getRowView(row).getDimension();
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   boolean isChartOnly()
/* 1294:     */   {
/* 1295:2300 */     return this.chartOnly;
/* 1296:     */   }
/* 1297:     */   
/* 1298:     */   public CellView getRowView(int row)
/* 1299:     */   {
/* 1300:2312 */     CellView cv = new CellView();
/* 1301:     */     try
/* 1302:     */     {
/* 1303:2316 */       RowRecord rr = getRowRecord(row);
/* 1304:2318 */       if ((rr == null) || (rr.isDefaultHeight()))
/* 1305:     */       {
/* 1306:2320 */         cv.setDimension(this.settings.getDefaultRowHeight());
/* 1307:2321 */         cv.setSize(this.settings.getDefaultRowHeight());
/* 1308:     */       }
/* 1309:2323 */       else if (rr.isCollapsed())
/* 1310:     */       {
/* 1311:2325 */         cv.setHidden(true);
/* 1312:     */       }
/* 1313:     */       else
/* 1314:     */       {
/* 1315:2329 */         cv.setDimension(rr.getRowHeight());
/* 1316:2330 */         cv.setSize(rr.getRowHeight());
/* 1317:     */       }
/* 1318:2332 */       return cv;
/* 1319:     */     }
/* 1320:     */     catch (RowsExceededException e)
/* 1321:     */     {
/* 1322:2337 */       cv.setDimension(this.settings.getDefaultRowHeight());
/* 1323:2338 */       cv.setSize(this.settings.getDefaultRowHeight());
/* 1324:     */     }
/* 1325:2339 */     return cv;
/* 1326:     */   }
/* 1327:     */   
/* 1328:     */   public CellView getColumnView(int col)
/* 1329:     */   {
/* 1330:2352 */     ColumnInfoRecord cir = getColumnInfo(col);
/* 1331:2353 */     CellView cv = new CellView();
/* 1332:2355 */     if (cir != null)
/* 1333:     */     {
/* 1334:2357 */       cv.setDimension(cir.getWidth() / 256);
/* 1335:2358 */       cv.setSize(cir.getWidth());
/* 1336:2359 */       cv.setHidden(cir.getHidden());
/* 1337:2360 */       cv.setFormat(cir.getCellFormat());
/* 1338:     */     }
/* 1339:     */     else
/* 1340:     */     {
/* 1341:2364 */       cv.setDimension(this.settings.getDefaultColumnWidth() / 256);
/* 1342:2365 */       cv.setSize(this.settings.getDefaultColumnWidth() * 256);
/* 1343:     */     }
/* 1344:2368 */     return cv;
/* 1345:     */   }
/* 1346:     */   
/* 1347:     */   public void addImage(WritableImage image)
/* 1348:     */   {
/* 1349:2378 */     boolean supported = false;
/* 1350:2379 */     java.io.File imageFile = image.getImageFile();
/* 1351:2380 */     String fileType = "?";
/* 1352:2382 */     if (imageFile != null)
/* 1353:     */     {
/* 1354:2384 */       String fileName = imageFile.getName();
/* 1355:2385 */       int fileTypeIndex = fileName.lastIndexOf('.');
/* 1356:2386 */       fileType = fileTypeIndex != -1 ? fileName.substring(fileTypeIndex + 1) : "";
/* 1357:2389 */       for (int i = 0; (i < imageTypes.length) && (!supported); i++) {
/* 1358:2391 */         if (fileType.equalsIgnoreCase(imageTypes[i])) {
/* 1359:2393 */           supported = true;
/* 1360:     */         }
/* 1361:     */       }
/* 1362:     */     }
/* 1363:     */     else
/* 1364:     */     {
/* 1365:2399 */       supported = true;
/* 1366:     */     }
/* 1367:2402 */     if (supported)
/* 1368:     */     {
/* 1369:2404 */       this.workbook.addDrawing(image);
/* 1370:2405 */       this.drawings.add(image);
/* 1371:2406 */       this.images.add(image);
/* 1372:     */     }
/* 1373:     */     else
/* 1374:     */     {
/* 1375:2410 */       StringBuffer message = new StringBuffer("Image type ");
/* 1376:2411 */       message.append(fileType);
/* 1377:2412 */       message.append(" not supported.  Supported types are ");
/* 1378:2413 */       message.append(imageTypes[0]);
/* 1379:2414 */       for (int i = 1; i < imageTypes.length; i++)
/* 1380:     */       {
/* 1381:2416 */         message.append(", ");
/* 1382:2417 */         message.append(imageTypes[i]);
/* 1383:     */       }
/* 1384:2419 */       logger.warn(message.toString());
/* 1385:     */     }
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   public int getNumberOfImages()
/* 1389:     */   {
/* 1390:2430 */     return this.images.size();
/* 1391:     */   }
/* 1392:     */   
/* 1393:     */   public WritableImage getImage(int i)
/* 1394:     */   {
/* 1395:2441 */     return (WritableImage)this.images.get(i);
/* 1396:     */   }
/* 1397:     */   
/* 1398:     */   public Image getDrawing(int i)
/* 1399:     */   {
/* 1400:2452 */     return (Image)this.images.get(i);
/* 1401:     */   }
/* 1402:     */   
/* 1403:     */   public void removeImage(WritableImage wi)
/* 1404:     */   {
/* 1405:2463 */     this.drawings.remove(wi);
/* 1406:2464 */     this.images.remove(wi);
/* 1407:2465 */     this.drawingsModified = true;
/* 1408:2466 */     this.workbook.removeDrawing(wi);
/* 1409:     */   }
/* 1410:     */   
/* 1411:     */   private String validateName(String n)
/* 1412:     */   {
/* 1413:2474 */     if (n.length() > 31)
/* 1414:     */     {
/* 1415:2476 */       logger.warn("Sheet name " + n + " too long - truncating");
/* 1416:2477 */       n = n.substring(0, 31);
/* 1417:     */     }
/* 1418:2480 */     if (n.charAt(0) == '\'')
/* 1419:     */     {
/* 1420:2482 */       logger.warn("Sheet naming cannot start with ' - removing");
/* 1421:2483 */       n = n.substring(1);
/* 1422:     */     }
/* 1423:2486 */     for (int i = 0; i < illegalSheetNameCharacters.length; i++)
/* 1424:     */     {
/* 1425:2488 */       String newname = n.replace(illegalSheetNameCharacters[i], '@');
/* 1426:2489 */       if (n != newname) {
/* 1427:2491 */         logger.warn(illegalSheetNameCharacters[i] + " is not a valid character within a sheet name - replacing");
/* 1428:     */       }
/* 1429:2494 */       n = newname;
/* 1430:     */     }
/* 1431:2497 */     return n;
/* 1432:     */   }
/* 1433:     */   
/* 1434:     */   void addDrawing(DrawingGroupObject o)
/* 1435:     */   {
/* 1436:2507 */     this.drawings.add(o);
/* 1437:2508 */     Assert.verify(!(o instanceof Drawing));
/* 1438:     */   }
/* 1439:     */   
/* 1440:     */   void removeDrawing(DrawingGroupObject o)
/* 1441:     */   {
/* 1442:2518 */     int origSize = this.drawings.size();
/* 1443:2519 */     this.drawings.remove(o);
/* 1444:2520 */     int newSize = this.drawings.size();
/* 1445:2521 */     this.drawingsModified = true;
/* 1446:2522 */     Assert.verify(newSize == origSize - 1);
/* 1447:     */   }
/* 1448:     */   
/* 1449:     */   void removeDataValidation(CellValue cv)
/* 1450:     */   {
/* 1451:2533 */     if (this.dataValidation != null) {
/* 1452:2535 */       this.dataValidation.removeDataValidation(cv.getColumn(), cv.getRow());
/* 1453:     */     }
/* 1454:2538 */     if (this.validatedCells != null)
/* 1455:     */     {
/* 1456:2540 */       boolean result = this.validatedCells.remove(cv);
/* 1457:2542 */       if (!result) {
/* 1458:2544 */         logger.warn("Could not remove validated cell " + CellReferenceHelper.getCellReference(cv));
/* 1459:     */       }
/* 1460:     */     }
/* 1461:     */   }
/* 1462:     */   
/* 1463:     */   public int[] getRowPageBreaks()
/* 1464:     */   {
/* 1465:2557 */     int[] rb = new int[this.rowBreaks.size()];
/* 1466:2558 */     int pos = 0;
/* 1467:2559 */     for (Iterator i = this.rowBreaks.iterator(); i.hasNext(); pos++) {
/* 1468:2561 */       rb[pos] = ((Integer)i.next()).intValue();
/* 1469:     */     }
/* 1470:2563 */     return rb;
/* 1471:     */   }
/* 1472:     */   
/* 1473:     */   public int[] getColumnPageBreaks()
/* 1474:     */   {
/* 1475:2573 */     int[] rb = new int[this.columnBreaks.size()];
/* 1476:2574 */     int pos = 0;
/* 1477:2575 */     for (Iterator i = this.columnBreaks.iterator(); i.hasNext(); pos++) {
/* 1478:2577 */       rb[pos] = ((Integer)i.next()).intValue();
/* 1479:     */     }
/* 1480:2579 */     return rb;
/* 1481:     */   }
/* 1482:     */   
/* 1483:     */   void addValidationCell(CellValue cv)
/* 1484:     */   {
/* 1485:2589 */     this.validatedCells.add(cv);
/* 1486:     */   }
/* 1487:     */   
/* 1488:     */   ComboBox getComboBox()
/* 1489:     */   {
/* 1490:2600 */     return this.comboBox;
/* 1491:     */   }
/* 1492:     */   
/* 1493:     */   void setComboBox(ComboBox cb)
/* 1494:     */   {
/* 1495:2610 */     this.comboBox = cb;
/* 1496:     */   }
/* 1497:     */   
/* 1498:     */   public DataValidation getDataValidation()
/* 1499:     */   {
/* 1500:2618 */     return this.dataValidation;
/* 1501:     */   }
/* 1502:     */   
/* 1503:     */   private void autosizeColumns()
/* 1504:     */   {
/* 1505:2626 */     Iterator i = this.autosizedColumns.iterator();
/* 1506:2627 */     while (i.hasNext())
/* 1507:     */     {
/* 1508:2629 */       Integer col = (Integer)i.next();
/* 1509:2630 */       autosizeColumn(col.intValue());
/* 1510:     */     }
/* 1511:     */   }
/* 1512:     */   
/* 1513:     */   private void autosizeColumn(int col)
/* 1514:     */   {
/* 1515:2641 */     int maxWidth = 0;
/* 1516:2642 */     ColumnInfoRecord cir = getColumnInfo(col);
/* 1517:2643 */     Font columnFont = cir.getCellFormat().getFont();
/* 1518:2644 */     Font defaultFont = WritableWorkbook.NORMAL_STYLE.getFont();
/* 1519:2646 */     for (int i = 0; i < this.numRows; i++)
/* 1520:     */     {
/* 1521:2648 */       Cell cell = null;
/* 1522:2649 */       if (this.rows[i] != null) {
/* 1523:2651 */         cell = this.rows[i].getCell(col);
/* 1524:     */       }
/* 1525:2654 */       if (cell != null)
/* 1526:     */       {
/* 1527:2656 */         String contents = cell.getContents();
/* 1528:2657 */         Font font = cell.getCellFormat().getFont();
/* 1529:     */         
/* 1530:2659 */         Font activeFont = font.equals(defaultFont) ? columnFont : font;
/* 1531:     */         
/* 1532:2661 */         int pointSize = activeFont.getPointSize();
/* 1533:2662 */         int numChars = contents.length();
/* 1534:2664 */         if ((activeFont.isItalic()) || (activeFont.getBoldWeight() > 400)) {
/* 1535:2667 */           numChars += 2;
/* 1536:     */         }
/* 1537:2670 */         int points = numChars * pointSize;
/* 1538:2671 */         maxWidth = Math.max(maxWidth, points * 256);
/* 1539:     */       }
/* 1540:     */     }
/* 1541:2674 */     cir.setWidth(maxWidth / defaultFont.getPointSize());
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   void importSheet(Sheet s)
/* 1545:     */   {
/* 1546:2685 */     this.settings = new SheetSettings(s.getSettings(), this);
/* 1547:     */     
/* 1548:2687 */     SheetCopier si = new SheetCopier(s, this);
/* 1549:2688 */     si.setColumnFormats(this.columnFormats);
/* 1550:2689 */     si.setFormatRecords(this.formatRecords);
/* 1551:2690 */     si.setHyperlinks(this.hyperlinks);
/* 1552:2691 */     si.setMergedCells(this.mergedCells);
/* 1553:2692 */     si.setRowBreaks(this.rowBreaks);
/* 1554:2693 */     si.setColumnBreaks(this.columnBreaks);
/* 1555:2694 */     si.setSheetWriter(this.sheetWriter);
/* 1556:2695 */     si.setDrawings(this.drawings);
/* 1557:2696 */     si.setImages(this.images);
/* 1558:2697 */     si.setValidatedCells(this.validatedCells);
/* 1559:     */     
/* 1560:2699 */     si.importSheet();
/* 1561:     */     
/* 1562:2701 */     this.dataValidation = si.getDataValidation();
/* 1563:2702 */     this.comboBox = si.getComboBox();
/* 1564:2703 */     this.plsRecord = si.getPLSRecord();
/* 1565:2704 */     this.chartOnly = si.isChartOnly();
/* 1566:2705 */     this.buttonPropertySet = si.getButtonPropertySet();
/* 1567:2706 */     this.numRows = si.getRows();
/* 1568:2707 */     this.maxRowOutlineLevel = si.getMaxRowOutlineLevel();
/* 1569:2708 */     this.maxColumnOutlineLevel = si.getMaxColumnOutlineLevel();
/* 1570:     */   }
/* 1571:     */   
/* 1572:     */   public void applySharedDataValidation(WritableCell c, int extraCols, int extraRows)
/* 1573:     */     throws WriteException
/* 1574:     */   {
/* 1575:2724 */     if ((c.getWritableCellFeatures() == null) || (!c.getWritableCellFeatures().hasDataValidation()))
/* 1576:     */     {
/* 1577:2727 */       logger.warn("Cannot extend data validation for " + CellReferenceHelper.getCellReference(c.getColumn(), c.getRow()) + " as it has no data validation");
/* 1578:     */       
/* 1579:     */ 
/* 1580:     */ 
/* 1581:2731 */       return;
/* 1582:     */     }
/* 1583:2736 */     int startColumn = c.getColumn();
/* 1584:2737 */     int startRow = c.getRow();
/* 1585:2738 */     int endRow = Math.min(this.numRows - 1, startRow + extraRows);
/* 1586:2739 */     for (int y = startRow; y <= endRow; y++) {
/* 1587:2741 */       if (this.rows[y] != null)
/* 1588:     */       {
/* 1589:2743 */         int endCol = Math.min(this.rows[y].getMaxColumn() - 1, startColumn + extraCols);
/* 1590:2745 */         for (int x = startColumn; x <= endCol; x++) {
/* 1591:2748 */           if ((x != startColumn) || (y != startRow))
/* 1592:     */           {
/* 1593:2753 */             WritableCell c2 = this.rows[y].getCell(x);
/* 1594:2756 */             if ((c2 != null) && (c2.getWritableCellFeatures() != null) && (c2.getWritableCellFeatures().hasDataValidation()))
/* 1595:     */             {
/* 1596:2760 */               logger.warn("Cannot apply data validation from " + CellReferenceHelper.getCellReference(startColumn, startRow) + " to " + CellReferenceHelper.getCellReference(startColumn + extraCols, startRow + extraRows) + " as cell " + CellReferenceHelper.getCellReference(x, y) + " already has a data validation");
/* 1597:     */               
/* 1598:     */ 
/* 1599:     */ 
/* 1600:     */ 
/* 1601:     */ 
/* 1602:     */ 
/* 1603:     */ 
/* 1604:     */ 
/* 1605:     */ 
/* 1606:2770 */               return;
/* 1607:     */             }
/* 1608:     */           }
/* 1609:     */         }
/* 1610:     */       }
/* 1611:     */     }
/* 1612:2777 */     WritableCellFeatures sourceDataValidation = c.getWritableCellFeatures();
/* 1613:2778 */     sourceDataValidation.getDVParser().extendCellValidation(extraCols, extraRows);
/* 1614:2782 */     for (int y = startRow; y <= startRow + extraRows; y++)
/* 1615:     */     {
/* 1616:2784 */       RowRecord rowrec = getRowRecord(y);
/* 1617:2785 */       for (int x = startColumn; x <= startColumn + extraCols; x++) {
/* 1618:2788 */         if ((x != startColumn) || (y != startRow))
/* 1619:     */         {
/* 1620:2793 */           WritableCell c2 = rowrec.getCell(x);
/* 1621:2796 */           if (c2 == null)
/* 1622:     */           {
/* 1623:2798 */             Blank b = new Blank(x, y);
/* 1624:2799 */             WritableCellFeatures validation = new WritableCellFeatures();
/* 1625:2800 */             validation.shareDataValidation(sourceDataValidation);
/* 1626:2801 */             b.setCellFeatures(validation);
/* 1627:2802 */             addCell(b);
/* 1628:     */           }
/* 1629:     */           else
/* 1630:     */           {
/* 1631:2807 */             WritableCellFeatures validation = c2.getWritableCellFeatures();
/* 1632:2809 */             if (validation != null)
/* 1633:     */             {
/* 1634:2811 */               validation.shareDataValidation(sourceDataValidation);
/* 1635:     */             }
/* 1636:     */             else
/* 1637:     */             {
/* 1638:2815 */               validation = new WritableCellFeatures();
/* 1639:2816 */               validation.shareDataValidation(sourceDataValidation);
/* 1640:2817 */               c2.setCellFeatures(validation);
/* 1641:     */             }
/* 1642:     */           }
/* 1643:     */         }
/* 1644:     */       }
/* 1645:     */     }
/* 1646:     */   }
/* 1647:     */   
/* 1648:     */   public void removeSharedDataValidation(WritableCell cell)
/* 1649:     */     throws WriteException
/* 1650:     */   {
/* 1651:2834 */     WritableCellFeatures wcf = cell.getWritableCellFeatures();
/* 1652:2835 */     if ((wcf == null) || (!wcf.hasDataValidation())) {
/* 1653:2838 */       return;
/* 1654:     */     }
/* 1655:2841 */     DVParser dvp = wcf.getDVParser();
/* 1656:2845 */     if (!dvp.extendedCellsValidation())
/* 1657:     */     {
/* 1658:2847 */       wcf.removeDataValidation();
/* 1659:2848 */       return;
/* 1660:     */     }
/* 1661:2853 */     if (dvp.extendedCellsValidation()) {
/* 1662:2855 */       if ((cell.getColumn() != dvp.getFirstColumn()) || (cell.getRow() != dvp.getFirstRow()))
/* 1663:     */       {
/* 1664:2858 */         logger.warn("Cannot remove data validation from " + CellReferenceHelper.getCellReference(dvp.getFirstColumn(), dvp.getFirstRow()) + "-" + CellReferenceHelper.getCellReference(dvp.getLastColumn(), dvp.getLastRow()) + " because the selected cell " + CellReferenceHelper.getCellReference(cell) + " is not the top left cell in the range");
/* 1665:     */         
/* 1666:     */ 
/* 1667:     */ 
/* 1668:     */ 
/* 1669:     */ 
/* 1670:     */ 
/* 1671:     */ 
/* 1672:     */ 
/* 1673:2867 */         return;
/* 1674:     */       }
/* 1675:     */     }
/* 1676:2871 */     for (int y = dvp.getFirstRow(); y <= dvp.getLastRow(); y++) {
/* 1677:2873 */       for (int x = dvp.getFirstColumn(); x <= dvp.getLastColumn(); x++)
/* 1678:     */       {
/* 1679:2875 */         CellValue c2 = this.rows[y].getCell(x);
/* 1680:2879 */         if (c2 != null)
/* 1681:     */         {
/* 1682:2881 */           c2.getWritableCellFeatures().removeSharedDataValidation();
/* 1683:2882 */           c2.removeCellFeatures();
/* 1684:     */         }
/* 1685:     */       }
/* 1686:     */     }
/* 1687:2889 */     if (this.dataValidation != null) {
/* 1688:2891 */       this.dataValidation.removeSharedDataValidation(dvp.getFirstColumn(), dvp.getFirstRow(), dvp.getLastColumn(), dvp.getLastRow());
/* 1689:     */     }
/* 1690:     */   }
/* 1691:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.WritableSheetImpl
 * JD-Core Version:    0.7.0.1
 */