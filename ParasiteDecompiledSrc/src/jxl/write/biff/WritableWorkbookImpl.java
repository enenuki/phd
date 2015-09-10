/*    1:     */ package jxl.write.biff;
/*    2:     */ 
/*    3:     */ import java.io.FileOutputStream;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.io.OutputStream;
/*    6:     */ import java.util.ArrayList;
/*    7:     */ import java.util.HashMap;
/*    8:     */ import java.util.Iterator;
/*    9:     */ import jxl.Cell;
/*   10:     */ import jxl.Range;
/*   11:     */ import jxl.Sheet;
/*   12:     */ import jxl.SheetSettings;
/*   13:     */ import jxl.Workbook;
/*   14:     */ import jxl.WorkbookSettings;
/*   15:     */ import jxl.biff.BuiltInName;
/*   16:     */ import jxl.biff.CellReferenceHelper;
/*   17:     */ import jxl.biff.CountryCode;
/*   18:     */ import jxl.biff.Fonts;
/*   19:     */ import jxl.biff.FormattingRecords;
/*   20:     */ import jxl.biff.IndexMapping;
/*   21:     */ import jxl.biff.IntegerHelper;
/*   22:     */ import jxl.biff.RangeImpl;
/*   23:     */ import jxl.biff.WorkbookMethods;
/*   24:     */ import jxl.biff.XCTRecord;
/*   25:     */ import jxl.biff.drawing.Drawing;
/*   26:     */ import jxl.biff.drawing.DrawingGroup;
/*   27:     */ import jxl.biff.drawing.DrawingGroupObject;
/*   28:     */ import jxl.biff.drawing.Origin;
/*   29:     */ import jxl.biff.formula.ExternalSheet;
/*   30:     */ import jxl.common.Assert;
/*   31:     */ import jxl.common.Logger;
/*   32:     */ import jxl.format.Colour;
/*   33:     */ import jxl.format.RGB;
/*   34:     */ import jxl.read.biff.WorkbookParser;
/*   35:     */ import jxl.write.WritableCell;
/*   36:     */ import jxl.write.WritableCellFormat;
/*   37:     */ import jxl.write.WritableFont;
/*   38:     */ import jxl.write.WritableSheet;
/*   39:     */ import jxl.write.WritableWorkbook;
/*   40:     */ 
/*   41:     */ public class WritableWorkbookImpl
/*   42:     */   extends WritableWorkbook
/*   43:     */   implements ExternalSheet, WorkbookMethods
/*   44:     */ {
/*   45:  68 */   private static Logger logger = Logger.getLogger(WritableWorkbookImpl.class);
/*   46:     */   private FormattingRecords formatRecords;
/*   47:     */   private File outputFile;
/*   48:     */   private ArrayList sheets;
/*   49:     */   private Fonts fonts;
/*   50:     */   private ExternalSheetRecord externSheet;
/*   51:     */   private ArrayList supbooks;
/*   52:     */   private ArrayList names;
/*   53:     */   private HashMap nameRecords;
/*   54:     */   private SharedStrings sharedStrings;
/*   55:     */   private boolean closeStream;
/*   56:     */   private boolean wbProtected;
/*   57:     */   private WorkbookSettings settings;
/*   58:     */   private ArrayList rcirCells;
/*   59:     */   private DrawingGroup drawingGroup;
/*   60:     */   private Styles styles;
/*   61:     */   private boolean containsMacros;
/*   62:     */   private ButtonPropertySetRecord buttonPropertySet;
/*   63:     */   private CountryRecord countryRecord;
/*   64: 159 */   private static Object SYNCHRONIZER = new Object();
/*   65:     */   private String[] addInFunctionNames;
/*   66:     */   private XCTRecord[] xctRecords;
/*   67:     */   
/*   68:     */   public WritableWorkbookImpl(OutputStream os, boolean cs, WorkbookSettings ws)
/*   69:     */     throws IOException
/*   70:     */   {
/*   71: 183 */     this.outputFile = new File(os, ws, null);
/*   72: 184 */     this.sheets = new ArrayList();
/*   73: 185 */     this.sharedStrings = new SharedStrings();
/*   74: 186 */     this.nameRecords = new HashMap();
/*   75: 187 */     this.closeStream = cs;
/*   76: 188 */     this.wbProtected = false;
/*   77: 189 */     this.containsMacros = false;
/*   78: 190 */     this.settings = ws;
/*   79: 191 */     this.rcirCells = new ArrayList();
/*   80: 192 */     this.styles = new Styles();
/*   81: 199 */     synchronized (SYNCHRONIZER)
/*   82:     */     {
/*   83: 201 */       WritableWorkbook.ARIAL_10_PT.uninitialize();
/*   84: 202 */       WritableWorkbook.HYPERLINK_FONT.uninitialize();
/*   85: 203 */       WritableWorkbook.NORMAL_STYLE.uninitialize();
/*   86: 204 */       WritableWorkbook.HYPERLINK_STYLE.uninitialize();
/*   87: 205 */       WritableWorkbook.HIDDEN_STYLE.uninitialize();
/*   88: 206 */       DateRecord.defaultDateFormat.uninitialize();
/*   89:     */     }
/*   90: 209 */     WritableFonts wf = new WritableFonts(this);
/*   91: 210 */     this.fonts = wf;
/*   92:     */     
/*   93: 212 */     WritableFormattingRecords wfr = new WritableFormattingRecords(this.fonts, this.styles);
/*   94:     */     
/*   95: 214 */     this.formatRecords = wfr;
/*   96:     */   }
/*   97:     */   
/*   98:     */   public WritableWorkbookImpl(OutputStream os, Workbook w, boolean cs, WorkbookSettings ws)
/*   99:     */     throws IOException
/*  100:     */   {
/*  101: 233 */     WorkbookParser wp = (WorkbookParser)w;
/*  102: 240 */     synchronized (SYNCHRONIZER)
/*  103:     */     {
/*  104: 242 */       WritableWorkbook.ARIAL_10_PT.uninitialize();
/*  105: 243 */       WritableWorkbook.HYPERLINK_FONT.uninitialize();
/*  106: 244 */       WritableWorkbook.NORMAL_STYLE.uninitialize();
/*  107: 245 */       WritableWorkbook.HYPERLINK_STYLE.uninitialize();
/*  108: 246 */       WritableWorkbook.HIDDEN_STYLE.uninitialize();
/*  109: 247 */       DateRecord.defaultDateFormat.uninitialize();
/*  110:     */     }
/*  111: 250 */     this.closeStream = cs;
/*  112: 251 */     this.sheets = new ArrayList();
/*  113: 252 */     this.sharedStrings = new SharedStrings();
/*  114: 253 */     this.nameRecords = new HashMap();
/*  115: 254 */     this.fonts = wp.getFonts();
/*  116: 255 */     this.formatRecords = wp.getFormattingRecords();
/*  117: 256 */     this.wbProtected = false;
/*  118: 257 */     this.settings = ws;
/*  119: 258 */     this.rcirCells = new ArrayList();
/*  120: 259 */     this.styles = new Styles();
/*  121: 260 */     this.outputFile = new File(os, ws, wp.getCompoundFile());
/*  122:     */     
/*  123: 262 */     this.containsMacros = false;
/*  124: 263 */     if (!ws.getPropertySetsDisabled()) {
/*  125: 265 */       this.containsMacros = wp.containsMacros();
/*  126:     */     }
/*  127: 269 */     if (wp.getCountryRecord() != null) {
/*  128: 271 */       this.countryRecord = new CountryRecord(wp.getCountryRecord());
/*  129:     */     }
/*  130: 275 */     this.addInFunctionNames = wp.getAddInFunctionNames();
/*  131:     */     
/*  132:     */ 
/*  133: 278 */     this.xctRecords = wp.getXCTRecords();
/*  134: 281 */     if (wp.getExternalSheetRecord() != null)
/*  135:     */     {
/*  136: 283 */       this.externSheet = new ExternalSheetRecord(wp.getExternalSheetRecord());
/*  137:     */       
/*  138:     */ 
/*  139: 286 */       jxl.read.biff.SupbookRecord[] readsr = wp.getSupbookRecords();
/*  140: 287 */       this.supbooks = new ArrayList(readsr.length);
/*  141: 289 */       for (int i = 0; i < readsr.length; i++)
/*  142:     */       {
/*  143: 291 */         jxl.read.biff.SupbookRecord readSupbook = readsr[i];
/*  144: 292 */         if ((readSupbook.getType() == jxl.read.biff.SupbookRecord.INTERNAL) || (readSupbook.getType() == jxl.read.biff.SupbookRecord.EXTERNAL)) {
/*  145: 295 */           this.supbooks.add(new SupbookRecord(readSupbook, this.settings));
/*  146: 299 */         } else if (readSupbook.getType() != jxl.read.biff.SupbookRecord.ADDIN) {
/*  147: 301 */           logger.warn("unsupported supbook type - ignoring");
/*  148:     */         }
/*  149:     */       }
/*  150:     */     }
/*  151: 309 */     if (wp.getDrawingGroup() != null) {
/*  152: 311 */       this.drawingGroup = new DrawingGroup(wp.getDrawingGroup());
/*  153:     */     }
/*  154: 315 */     if ((this.containsMacros) && (wp.getButtonPropertySet() != null)) {
/*  155: 317 */       this.buttonPropertySet = new ButtonPropertySetRecord(wp.getButtonPropertySet());
/*  156:     */     }
/*  157: 322 */     if (!this.settings.getNamesDisabled())
/*  158:     */     {
/*  159: 324 */       jxl.read.biff.NameRecord[] na = wp.getNameRecords();
/*  160: 325 */       this.names = new ArrayList(na.length);
/*  161: 327 */       for (int i = 0; i < na.length; i++) {
/*  162: 329 */         if (na[i].isBiff8())
/*  163:     */         {
/*  164: 331 */           NameRecord n = new NameRecord(na[i], i);
/*  165: 332 */           this.names.add(n);
/*  166: 333 */           String name = n.getName();
/*  167: 334 */           this.nameRecords.put(name, n);
/*  168:     */         }
/*  169:     */         else
/*  170:     */         {
/*  171: 338 */           logger.warn("Cannot copy Biff7 name records - ignoring");
/*  172:     */         }
/*  173:     */       }
/*  174:     */     }
/*  175: 343 */     copyWorkbook(w);
/*  176: 348 */     if (this.drawingGroup != null) {
/*  177: 350 */       this.drawingGroup.updateData(wp.getDrawingGroup());
/*  178:     */     }
/*  179:     */   }
/*  180:     */   
/*  181:     */   public WritableSheet[] getSheets()
/*  182:     */   {
/*  183: 362 */     WritableSheet[] sheetArray = new WritableSheet[getNumberOfSheets()];
/*  184: 364 */     for (int i = 0; i < getNumberOfSheets(); i++) {
/*  185: 366 */       sheetArray[i] = getSheet(i);
/*  186:     */     }
/*  187: 368 */     return sheetArray;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public String[] getSheetNames()
/*  191:     */   {
/*  192: 378 */     String[] sheetNames = new String[getNumberOfSheets()];
/*  193: 380 */     for (int i = 0; i < sheetNames.length; i++) {
/*  194: 382 */       sheetNames[i] = getSheet(i).getName();
/*  195:     */     }
/*  196: 385 */     return sheetNames;
/*  197:     */   }
/*  198:     */   
/*  199:     */   public Sheet getReadSheet(int index)
/*  200:     */   {
/*  201: 397 */     return getSheet(index);
/*  202:     */   }
/*  203:     */   
/*  204:     */   public WritableSheet getSheet(int index)
/*  205:     */   {
/*  206: 408 */     return (WritableSheet)this.sheets.get(index);
/*  207:     */   }
/*  208:     */   
/*  209:     */   public WritableSheet getSheet(String name)
/*  210:     */   {
/*  211: 420 */     boolean found = false;
/*  212: 421 */     Iterator i = this.sheets.iterator();
/*  213: 422 */     WritableSheet s = null;
/*  214: 424 */     while ((i.hasNext()) && (!found))
/*  215:     */     {
/*  216: 426 */       s = (WritableSheet)i.next();
/*  217: 428 */       if (s.getName().equals(name)) {
/*  218: 430 */         found = true;
/*  219:     */       }
/*  220:     */     }
/*  221: 434 */     return found ? s : null;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public int getNumberOfSheets()
/*  225:     */   {
/*  226: 444 */     return this.sheets.size();
/*  227:     */   }
/*  228:     */   
/*  229:     */   public void close()
/*  230:     */     throws IOException, JxlWriteException
/*  231:     */   {
/*  232: 456 */     this.outputFile.close(this.closeStream);
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void setOutputFile(java.io.File fileName)
/*  236:     */     throws IOException
/*  237:     */   {
/*  238: 469 */     FileOutputStream fos = new FileOutputStream(fileName);
/*  239: 470 */     this.outputFile.setOutputFile(fos);
/*  240:     */   }
/*  241:     */   
/*  242:     */   private WritableSheet createSheet(String name, int index, boolean handleRefs)
/*  243:     */   {
/*  244: 486 */     WritableSheet w = new WritableSheetImpl(name, this.outputFile, this.formatRecords, this.sharedStrings, this.settings, this);
/*  245:     */     
/*  246:     */ 
/*  247:     */ 
/*  248:     */ 
/*  249:     */ 
/*  250:     */ 
/*  251: 493 */     int pos = index;
/*  252: 495 */     if (index <= 0)
/*  253:     */     {
/*  254: 497 */       pos = 0;
/*  255: 498 */       this.sheets.add(0, w);
/*  256:     */     }
/*  257: 500 */     else if (index > this.sheets.size())
/*  258:     */     {
/*  259: 502 */       pos = this.sheets.size();
/*  260: 503 */       this.sheets.add(w);
/*  261:     */     }
/*  262:     */     else
/*  263:     */     {
/*  264: 507 */       this.sheets.add(index, w);
/*  265:     */     }
/*  266: 510 */     if ((handleRefs) && (this.externSheet != null)) {
/*  267: 512 */       this.externSheet.sheetInserted(pos);
/*  268:     */     }
/*  269: 515 */     if ((this.supbooks != null) && (this.supbooks.size() > 0))
/*  270:     */     {
/*  271: 517 */       SupbookRecord supbook = (SupbookRecord)this.supbooks.get(0);
/*  272: 518 */       if (supbook.getType() == SupbookRecord.INTERNAL) {
/*  273: 520 */         supbook.adjustInternal(this.sheets.size());
/*  274:     */       }
/*  275:     */     }
/*  276: 524 */     return w;
/*  277:     */   }
/*  278:     */   
/*  279:     */   public WritableSheet createSheet(String name, int index)
/*  280:     */   {
/*  281: 538 */     return createSheet(name, index, true);
/*  282:     */   }
/*  283:     */   
/*  284:     */   public void removeSheet(int index)
/*  285:     */   {
/*  286: 550 */     int pos = index;
/*  287: 551 */     if (index <= 0)
/*  288:     */     {
/*  289: 553 */       pos = 0;
/*  290: 554 */       this.sheets.remove(0);
/*  291:     */     }
/*  292: 556 */     else if (index >= this.sheets.size())
/*  293:     */     {
/*  294: 558 */       pos = this.sheets.size() - 1;
/*  295: 559 */       this.sheets.remove(this.sheets.size() - 1);
/*  296:     */     }
/*  297:     */     else
/*  298:     */     {
/*  299: 563 */       this.sheets.remove(index);
/*  300:     */     }
/*  301: 566 */     if (this.externSheet != null) {
/*  302: 568 */       this.externSheet.sheetRemoved(pos);
/*  303:     */     }
/*  304: 571 */     if ((this.supbooks != null) && (this.supbooks.size() > 0))
/*  305:     */     {
/*  306: 573 */       SupbookRecord supbook = (SupbookRecord)this.supbooks.get(0);
/*  307: 574 */       if (supbook.getType() == SupbookRecord.INTERNAL) {
/*  308: 576 */         supbook.adjustInternal(this.sheets.size());
/*  309:     */       }
/*  310:     */     }
/*  311: 580 */     if ((this.names != null) && (this.names.size() > 0)) {
/*  312: 582 */       for (int i = 0; i < this.names.size(); i++)
/*  313:     */       {
/*  314: 584 */         NameRecord n = (NameRecord)this.names.get(i);
/*  315: 585 */         int oldRef = n.getSheetRef();
/*  316: 586 */         if (oldRef == pos + 1)
/*  317:     */         {
/*  318: 588 */           n.setSheetRef(0);
/*  319:     */         }
/*  320: 590 */         else if (oldRef > pos + 1)
/*  321:     */         {
/*  322: 592 */           if (oldRef < 1) {
/*  323: 594 */             oldRef = 1;
/*  324:     */           }
/*  325: 596 */           n.setSheetRef(oldRef - 1);
/*  326:     */         }
/*  327:     */       }
/*  328:     */     }
/*  329:     */   }
/*  330:     */   
/*  331:     */   public WritableSheet moveSheet(int fromIndex, int toIndex)
/*  332:     */   {
/*  333: 613 */     fromIndex = Math.max(fromIndex, 0);
/*  334: 614 */     fromIndex = Math.min(fromIndex, this.sheets.size() - 1);
/*  335: 615 */     toIndex = Math.max(toIndex, 0);
/*  336: 616 */     toIndex = Math.min(toIndex, this.sheets.size() - 1);
/*  337:     */     
/*  338: 618 */     WritableSheet sheet = (WritableSheet)this.sheets.remove(fromIndex);
/*  339: 619 */     this.sheets.add(toIndex, sheet);
/*  340:     */     
/*  341: 621 */     return sheet;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void write()
/*  345:     */     throws IOException
/*  346:     */   {
/*  347: 635 */     WritableSheetImpl wsi = null;
/*  348: 636 */     for (int i = 0; i < getNumberOfSheets(); i++)
/*  349:     */     {
/*  350: 638 */       wsi = (WritableSheetImpl)getSheet(i);
/*  351:     */       
/*  352:     */ 
/*  353:     */ 
/*  354: 642 */       wsi.checkMergedBorders();
/*  355:     */       
/*  356:     */ 
/*  357: 645 */       Range range = wsi.getSettings().getPrintArea();
/*  358: 646 */       if (range != null) {
/*  359: 648 */         addNameArea(BuiltInName.PRINT_AREA, wsi, range.getTopLeft().getColumn(), range.getTopLeft().getRow(), range.getBottomRight().getColumn(), range.getBottomRight().getRow(), false);
/*  360:     */       }
/*  361: 658 */       Range rangeR = wsi.getSettings().getPrintTitlesRow();
/*  362: 659 */       Range rangeC = wsi.getSettings().getPrintTitlesCol();
/*  363: 660 */       if ((rangeR != null) && (rangeC != null)) {
/*  364: 662 */         addNameArea(BuiltInName.PRINT_TITLES, wsi, rangeR.getTopLeft().getColumn(), rangeR.getTopLeft().getRow(), rangeR.getBottomRight().getColumn(), rangeR.getBottomRight().getRow(), rangeC.getTopLeft().getColumn(), rangeC.getTopLeft().getRow(), rangeC.getBottomRight().getColumn(), rangeC.getBottomRight().getRow(), false);
/*  365: 675 */       } else if (rangeR != null) {
/*  366: 677 */         addNameArea(BuiltInName.PRINT_TITLES, wsi, rangeR.getTopLeft().getColumn(), rangeR.getTopLeft().getRow(), rangeR.getBottomRight().getColumn(), rangeR.getBottomRight().getRow(), false);
/*  367: 686 */       } else if (rangeC != null) {
/*  368: 688 */         addNameArea(BuiltInName.PRINT_TITLES, wsi, rangeC.getTopLeft().getColumn(), rangeC.getTopLeft().getRow(), rangeC.getBottomRight().getColumn(), rangeC.getBottomRight().getRow(), false);
/*  369:     */       }
/*  370:     */     }
/*  371: 699 */     if (!this.settings.getRationalizationDisabled()) {
/*  372: 701 */       rationalize();
/*  373:     */     }
/*  374: 705 */     BOFRecord bof = new BOFRecord(BOFRecord.workbookGlobals);
/*  375: 706 */     this.outputFile.write(bof);
/*  376: 709 */     if (this.settings.getTemplate())
/*  377:     */     {
/*  378: 712 */       TemplateRecord trec = new TemplateRecord();
/*  379: 713 */       this.outputFile.write(trec);
/*  380:     */     }
/*  381: 717 */     InterfaceHeaderRecord ihr = new InterfaceHeaderRecord();
/*  382: 718 */     this.outputFile.write(ihr);
/*  383:     */     
/*  384: 720 */     MMSRecord mms = new MMSRecord(0, 0);
/*  385: 721 */     this.outputFile.write(mms);
/*  386:     */     
/*  387: 723 */     InterfaceEndRecord ier = new InterfaceEndRecord();
/*  388: 724 */     this.outputFile.write(ier);
/*  389:     */     
/*  390: 726 */     WriteAccessRecord wr = new WriteAccessRecord(this.settings.getWriteAccess());
/*  391: 727 */     this.outputFile.write(wr);
/*  392:     */     
/*  393: 729 */     CodepageRecord cp = new CodepageRecord();
/*  394: 730 */     this.outputFile.write(cp);
/*  395:     */     
/*  396: 732 */     DSFRecord dsf = new DSFRecord();
/*  397: 733 */     this.outputFile.write(dsf);
/*  398: 735 */     if (this.settings.getExcel9File())
/*  399:     */     {
/*  400: 739 */       Excel9FileRecord e9rec = new Excel9FileRecord();
/*  401: 740 */       this.outputFile.write(e9rec);
/*  402:     */     }
/*  403: 743 */     TabIdRecord tabid = new TabIdRecord(getNumberOfSheets());
/*  404: 744 */     this.outputFile.write(tabid);
/*  405: 746 */     if (this.containsMacros)
/*  406:     */     {
/*  407: 748 */       ObjProjRecord objproj = new ObjProjRecord();
/*  408: 749 */       this.outputFile.write(objproj);
/*  409:     */     }
/*  410: 752 */     if (this.buttonPropertySet != null) {
/*  411: 754 */       this.outputFile.write(this.buttonPropertySet);
/*  412:     */     }
/*  413: 757 */     FunctionGroupCountRecord fgcr = new FunctionGroupCountRecord();
/*  414: 758 */     this.outputFile.write(fgcr);
/*  415:     */     
/*  416:     */ 
/*  417: 761 */     WindowProtectRecord wpr = new WindowProtectRecord(this.settings.getWindowProtected());
/*  418:     */     
/*  419: 763 */     this.outputFile.write(wpr);
/*  420:     */     
/*  421: 765 */     ProtectRecord pr = new ProtectRecord(this.wbProtected);
/*  422: 766 */     this.outputFile.write(pr);
/*  423:     */     
/*  424: 768 */     PasswordRecord pw = new PasswordRecord(null);
/*  425: 769 */     this.outputFile.write(pw);
/*  426:     */     
/*  427: 771 */     Prot4RevRecord p4r = new Prot4RevRecord(false);
/*  428: 772 */     this.outputFile.write(p4r);
/*  429:     */     
/*  430: 774 */     Prot4RevPassRecord p4rp = new Prot4RevPassRecord();
/*  431: 775 */     this.outputFile.write(p4rp);
/*  432:     */     
/*  433:     */ 
/*  434:     */ 
/*  435: 779 */     boolean sheetSelected = false;
/*  436: 780 */     WritableSheetImpl wsheet = null;
/*  437: 781 */     int selectedSheetIndex = 0;
/*  438: 782 */     for (int i = 0; (i < getNumberOfSheets()) && (!sheetSelected); i++)
/*  439:     */     {
/*  440: 784 */       wsheet = (WritableSheetImpl)getSheet(i);
/*  441: 785 */       if (wsheet.getSettings().isSelected())
/*  442:     */       {
/*  443: 787 */         sheetSelected = true;
/*  444: 788 */         selectedSheetIndex = i;
/*  445:     */       }
/*  446:     */     }
/*  447: 792 */     if (!sheetSelected)
/*  448:     */     {
/*  449: 794 */       wsheet = (WritableSheetImpl)getSheet(0);
/*  450: 795 */       wsheet.getSettings().setSelected(true);
/*  451: 796 */       selectedSheetIndex = 0;
/*  452:     */     }
/*  453: 799 */     Window1Record w1r = new Window1Record(selectedSheetIndex);
/*  454: 800 */     this.outputFile.write(w1r);
/*  455:     */     
/*  456: 802 */     BackupRecord bkr = new BackupRecord(false);
/*  457: 803 */     this.outputFile.write(bkr);
/*  458:     */     
/*  459: 805 */     HideobjRecord ho = new HideobjRecord(this.settings.getHideobj());
/*  460: 806 */     this.outputFile.write(ho);
/*  461:     */     
/*  462: 808 */     NineteenFourRecord nf = new NineteenFourRecord(false);
/*  463: 809 */     this.outputFile.write(nf);
/*  464:     */     
/*  465: 811 */     PrecisionRecord pc = new PrecisionRecord(false);
/*  466: 812 */     this.outputFile.write(pc);
/*  467:     */     
/*  468: 814 */     RefreshAllRecord rar = new RefreshAllRecord(this.settings.getRefreshAll());
/*  469: 815 */     this.outputFile.write(rar);
/*  470:     */     
/*  471: 817 */     BookboolRecord bb = new BookboolRecord(true);
/*  472: 818 */     this.outputFile.write(bb);
/*  473:     */     
/*  474:     */ 
/*  475: 821 */     this.fonts.write(this.outputFile);
/*  476:     */     
/*  477:     */ 
/*  478: 824 */     this.formatRecords.write(this.outputFile);
/*  479: 827 */     if (this.formatRecords.getPalette() != null) {
/*  480: 829 */       this.outputFile.write(this.formatRecords.getPalette());
/*  481:     */     }
/*  482: 833 */     UsesElfsRecord uer = new UsesElfsRecord();
/*  483: 834 */     this.outputFile.write(uer);
/*  484:     */     
/*  485:     */ 
/*  486:     */ 
/*  487: 838 */     int[] boundsheetPos = new int[getNumberOfSheets()];
/*  488: 839 */     Sheet sheet = null;
/*  489: 841 */     for (int i = 0; i < getNumberOfSheets(); i++)
/*  490:     */     {
/*  491: 843 */       boundsheetPos[i] = this.outputFile.getPos();
/*  492: 844 */       sheet = getSheet(i);
/*  493: 845 */       BoundsheetRecord br = new BoundsheetRecord(sheet.getName());
/*  494: 846 */       if (sheet.getSettings().isHidden()) {
/*  495: 848 */         br.setHidden();
/*  496:     */       }
/*  497: 851 */       if (((WritableSheetImpl)this.sheets.get(i)).isChartOnly()) {
/*  498: 853 */         br.setChartOnly();
/*  499:     */       }
/*  500: 856 */       this.outputFile.write(br);
/*  501:     */     }
/*  502: 859 */     if (this.countryRecord == null)
/*  503:     */     {
/*  504: 861 */       CountryCode lang = CountryCode.getCountryCode(this.settings.getExcelDisplayLanguage());
/*  505: 863 */       if (lang == CountryCode.UNKNOWN)
/*  506:     */       {
/*  507: 865 */         logger.warn("Unknown country code " + this.settings.getExcelDisplayLanguage() + " using " + CountryCode.USA.getCode());
/*  508:     */         
/*  509:     */ 
/*  510: 868 */         lang = CountryCode.USA;
/*  511:     */       }
/*  512: 870 */       CountryCode region = CountryCode.getCountryCode(this.settings.getExcelRegionalSettings());
/*  513:     */       
/*  514: 872 */       this.countryRecord = new CountryRecord(lang, region);
/*  515: 873 */       if (region == CountryCode.UNKNOWN)
/*  516:     */       {
/*  517: 875 */         logger.warn("Unknown country code " + this.settings.getExcelDisplayLanguage() + " using " + CountryCode.UK.getCode());
/*  518:     */         
/*  519:     */ 
/*  520: 878 */         region = CountryCode.UK;
/*  521:     */       }
/*  522:     */     }
/*  523: 882 */     this.outputFile.write(this.countryRecord);
/*  524: 885 */     if ((this.addInFunctionNames != null) && (this.addInFunctionNames.length > 0)) {
/*  525: 891 */       for (int i = 0; i < this.addInFunctionNames.length; i++)
/*  526:     */       {
/*  527: 893 */         ExternalNameRecord enr = new ExternalNameRecord(this.addInFunctionNames[i]);
/*  528: 894 */         this.outputFile.write(enr);
/*  529:     */       }
/*  530:     */     }
/*  531: 898 */     if (this.xctRecords != null) {
/*  532: 900 */       for (int i = 0; i < this.xctRecords.length; i++) {
/*  533: 902 */         this.outputFile.write(this.xctRecords[i]);
/*  534:     */       }
/*  535:     */     }
/*  536: 907 */     if (this.externSheet != null)
/*  537:     */     {
/*  538: 910 */       for (int i = 0; i < this.supbooks.size(); i++)
/*  539:     */       {
/*  540: 912 */         SupbookRecord supbook = (SupbookRecord)this.supbooks.get(i);
/*  541: 913 */         this.outputFile.write(supbook);
/*  542:     */       }
/*  543: 915 */       this.outputFile.write(this.externSheet);
/*  544:     */     }
/*  545: 919 */     if (this.names != null) {
/*  546: 921 */       for (int i = 0; i < this.names.size(); i++)
/*  547:     */       {
/*  548: 923 */         NameRecord n = (NameRecord)this.names.get(i);
/*  549: 924 */         this.outputFile.write(n);
/*  550:     */       }
/*  551:     */     }
/*  552: 929 */     if (this.drawingGroup != null) {
/*  553: 931 */       this.drawingGroup.write(this.outputFile);
/*  554:     */     }
/*  555: 934 */     this.sharedStrings.write(this.outputFile);
/*  556:     */     
/*  557: 936 */     EOFRecord eof = new EOFRecord();
/*  558: 937 */     this.outputFile.write(eof);
/*  559: 941 */     for (int i = 0; i < getNumberOfSheets(); i++)
/*  560:     */     {
/*  561: 945 */       this.outputFile.setData(IntegerHelper.getFourBytes(this.outputFile.getPos()), boundsheetPos[i] + 4);
/*  562:     */       
/*  563:     */ 
/*  564:     */ 
/*  565: 949 */       wsheet = (WritableSheetImpl)getSheet(i);
/*  566: 950 */       wsheet.write();
/*  567:     */     }
/*  568:     */   }
/*  569:     */   
/*  570:     */   private void copyWorkbook(Workbook w)
/*  571:     */   {
/*  572: 963 */     int numSheets = w.getNumberOfSheets();
/*  573: 964 */     this.wbProtected = w.isProtected();
/*  574: 965 */     Sheet s = null;
/*  575: 966 */     WritableSheetImpl ws = null;
/*  576: 967 */     for (int i = 0; i < numSheets; i++)
/*  577:     */     {
/*  578: 969 */       s = w.getSheet(i);
/*  579: 970 */       ws = (WritableSheetImpl)createSheet(s.getName(), i, false);
/*  580: 971 */       ws.copy(s);
/*  581:     */     }
/*  582:     */   }
/*  583:     */   
/*  584:     */   public void copySheet(int s, String name, int index)
/*  585:     */   {
/*  586: 985 */     WritableSheet sheet = getSheet(s);
/*  587: 986 */     WritableSheetImpl ws = (WritableSheetImpl)createSheet(name, index);
/*  588: 987 */     ws.copy(sheet);
/*  589:     */   }
/*  590:     */   
/*  591:     */   public void copySheet(String s, String name, int index)
/*  592:     */   {
/*  593:1000 */     WritableSheet sheet = getSheet(s);
/*  594:1001 */     WritableSheetImpl ws = (WritableSheetImpl)createSheet(name, index);
/*  595:1002 */     ws.copy(sheet);
/*  596:     */   }
/*  597:     */   
/*  598:     */   public void setProtected(boolean prot)
/*  599:     */   {
/*  600:1012 */     this.wbProtected = prot;
/*  601:     */   }
/*  602:     */   
/*  603:     */   private void rationalize()
/*  604:     */   {
/*  605:1021 */     IndexMapping fontMapping = this.formatRecords.rationalizeFonts();
/*  606:1022 */     IndexMapping formatMapping = this.formatRecords.rationalizeDisplayFormats();
/*  607:1023 */     IndexMapping xfMapping = this.formatRecords.rationalize(fontMapping, formatMapping);
/*  608:     */     
/*  609:     */ 
/*  610:1026 */     WritableSheetImpl wsi = null;
/*  611:1027 */     for (int i = 0; i < this.sheets.size(); i++)
/*  612:     */     {
/*  613:1029 */       wsi = (WritableSheetImpl)this.sheets.get(i);
/*  614:1030 */       wsi.rationalize(xfMapping, fontMapping, formatMapping);
/*  615:     */     }
/*  616:     */   }
/*  617:     */   
/*  618:     */   private int getInternalSheetIndex(String name)
/*  619:     */   {
/*  620:1042 */     int index = -1;
/*  621:1043 */     String[] names = getSheetNames();
/*  622:1044 */     for (int i = 0; i < names.length; i++) {
/*  623:1046 */       if (name.equals(names[i]))
/*  624:     */       {
/*  625:1048 */         index = i;
/*  626:1049 */         break;
/*  627:     */       }
/*  628:     */     }
/*  629:1053 */     return index;
/*  630:     */   }
/*  631:     */   
/*  632:     */   public String getExternalSheetName(int index)
/*  633:     */   {
/*  634:1064 */     int supbookIndex = this.externSheet.getSupbookIndex(index);
/*  635:1065 */     SupbookRecord sr = (SupbookRecord)this.supbooks.get(supbookIndex);
/*  636:     */     
/*  637:1067 */     int firstTab = this.externSheet.getFirstTabIndex(index);
/*  638:1069 */     if (sr.getType() == SupbookRecord.INTERNAL)
/*  639:     */     {
/*  640:1072 */       WritableSheet ws = getSheet(firstTab);
/*  641:     */       
/*  642:1074 */       return ws.getName();
/*  643:     */     }
/*  644:1076 */     if (sr.getType() == SupbookRecord.EXTERNAL)
/*  645:     */     {
/*  646:1078 */       String name = sr.getFileName() + sr.getSheetName(firstTab);
/*  647:1079 */       return name;
/*  648:     */     }
/*  649:1083 */     logger.warn("Unknown Supbook 1");
/*  650:1084 */     return "[UNKNOWN]";
/*  651:     */   }
/*  652:     */   
/*  653:     */   public String getLastExternalSheetName(int index)
/*  654:     */   {
/*  655:1095 */     int supbookIndex = this.externSheet.getSupbookIndex(index);
/*  656:1096 */     SupbookRecord sr = (SupbookRecord)this.supbooks.get(supbookIndex);
/*  657:     */     
/*  658:1098 */     int lastTab = this.externSheet.getLastTabIndex(index);
/*  659:1100 */     if (sr.getType() == SupbookRecord.INTERNAL)
/*  660:     */     {
/*  661:1103 */       WritableSheet ws = getSheet(lastTab);
/*  662:     */       
/*  663:1105 */       return ws.getName();
/*  664:     */     }
/*  665:1107 */     if (sr.getType() == SupbookRecord.EXTERNAL) {
/*  666:1109 */       Assert.verify(false);
/*  667:     */     }
/*  668:1113 */     logger.warn("Unknown Supbook 2");
/*  669:1114 */     return "[UNKNOWN]";
/*  670:     */   }
/*  671:     */   
/*  672:     */   public jxl.read.biff.BOFRecord getWorkbookBof()
/*  673:     */   {
/*  674:1125 */     return null;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public int getExternalSheetIndex(int index)
/*  678:     */   {
/*  679:1137 */     if (this.externSheet == null) {
/*  680:1139 */       return index;
/*  681:     */     }
/*  682:1142 */     Assert.verify(this.externSheet != null);
/*  683:     */     
/*  684:1144 */     int firstTab = this.externSheet.getFirstTabIndex(index);
/*  685:     */     
/*  686:1146 */     return firstTab;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public int getLastExternalSheetIndex(int index)
/*  690:     */   {
/*  691:1157 */     if (this.externSheet == null) {
/*  692:1159 */       return index;
/*  693:     */     }
/*  694:1162 */     Assert.verify(this.externSheet != null);
/*  695:     */     
/*  696:1164 */     int lastTab = this.externSheet.getLastTabIndex(index);
/*  697:     */     
/*  698:1166 */     return lastTab;
/*  699:     */   }
/*  700:     */   
/*  701:     */   public int getExternalSheetIndex(String sheetName)
/*  702:     */   {
/*  703:1177 */     if (this.externSheet == null)
/*  704:     */     {
/*  705:1179 */       this.externSheet = new ExternalSheetRecord();
/*  706:1180 */       this.supbooks = new ArrayList();
/*  707:1181 */       this.supbooks.add(new SupbookRecord(getNumberOfSheets(), this.settings));
/*  708:     */     }
/*  709:1185 */     boolean found = false;
/*  710:1186 */     Iterator i = this.sheets.iterator();
/*  711:1187 */     int sheetpos = 0;
/*  712:1188 */     WritableSheetImpl s = null;
/*  713:1190 */     while ((i.hasNext()) && (!found))
/*  714:     */     {
/*  715:1192 */       s = (WritableSheetImpl)i.next();
/*  716:1194 */       if (s.getName().equals(sheetName)) {
/*  717:1196 */         found = true;
/*  718:     */       } else {
/*  719:1200 */         sheetpos++;
/*  720:     */       }
/*  721:     */     }
/*  722:1204 */     if (found)
/*  723:     */     {
/*  724:1208 */       SupbookRecord supbook = (SupbookRecord)this.supbooks.get(0);
/*  725:1209 */       if ((supbook.getType() != SupbookRecord.INTERNAL) || (supbook.getNumberOfSheets() != getNumberOfSheets())) {
/*  726:1212 */         logger.warn("Cannot find sheet " + sheetName + " in supbook record");
/*  727:     */       }
/*  728:1215 */       return this.externSheet.getIndex(0, sheetpos);
/*  729:     */     }
/*  730:1219 */     int closeSquareBracketsIndex = sheetName.lastIndexOf(']');
/*  731:1220 */     int openSquareBracketsIndex = sheetName.lastIndexOf('[');
/*  732:1222 */     if ((closeSquareBracketsIndex == -1) || (openSquareBracketsIndex == -1))
/*  733:     */     {
/*  734:1225 */       logger.warn("Square brackets");
/*  735:1226 */       return -1;
/*  736:     */     }
/*  737:1229 */     String worksheetName = sheetName.substring(closeSquareBracketsIndex + 1);
/*  738:1230 */     String workbookName = sheetName.substring(openSquareBracketsIndex + 1, closeSquareBracketsIndex);
/*  739:     */     
/*  740:1232 */     String path = sheetName.substring(0, openSquareBracketsIndex);
/*  741:1233 */     String fileName = path + workbookName;
/*  742:     */     
/*  743:1235 */     boolean supbookFound = false;
/*  744:1236 */     SupbookRecord externalSupbook = null;
/*  745:1237 */     int supbookIndex = -1;
/*  746:1238 */     for (int ind = 0; (ind < this.supbooks.size()) && (!supbookFound); ind++)
/*  747:     */     {
/*  748:1240 */       externalSupbook = (SupbookRecord)this.supbooks.get(ind);
/*  749:1241 */       if ((externalSupbook.getType() == SupbookRecord.EXTERNAL) && (externalSupbook.getFileName().equals(fileName)))
/*  750:     */       {
/*  751:1244 */         supbookFound = true;
/*  752:1245 */         supbookIndex = ind;
/*  753:     */       }
/*  754:     */     }
/*  755:1249 */     if (!supbookFound)
/*  756:     */     {
/*  757:1251 */       externalSupbook = new SupbookRecord(fileName, this.settings);
/*  758:1252 */       supbookIndex = this.supbooks.size();
/*  759:1253 */       this.supbooks.add(externalSupbook);
/*  760:     */     }
/*  761:1256 */     int sheetIndex = externalSupbook.getSheetIndex(worksheetName);
/*  762:     */     
/*  763:1258 */     return this.externSheet.getIndex(supbookIndex, sheetIndex);
/*  764:     */   }
/*  765:     */   
/*  766:     */   public int getLastExternalSheetIndex(String sheetName)
/*  767:     */   {
/*  768:1268 */     if (this.externSheet == null)
/*  769:     */     {
/*  770:1270 */       this.externSheet = new ExternalSheetRecord();
/*  771:1271 */       this.supbooks = new ArrayList();
/*  772:1272 */       this.supbooks.add(new SupbookRecord(getNumberOfSheets(), this.settings));
/*  773:     */     }
/*  774:1276 */     boolean found = false;
/*  775:1277 */     Iterator i = this.sheets.iterator();
/*  776:1278 */     int sheetpos = 0;
/*  777:1279 */     WritableSheetImpl s = null;
/*  778:1281 */     while ((i.hasNext()) && (!found))
/*  779:     */     {
/*  780:1283 */       s = (WritableSheetImpl)i.next();
/*  781:1285 */       if (s.getName().equals(sheetName)) {
/*  782:1287 */         found = true;
/*  783:     */       } else {
/*  784:1291 */         sheetpos++;
/*  785:     */       }
/*  786:     */     }
/*  787:1295 */     if (!found) {
/*  788:1297 */       return -1;
/*  789:     */     }
/*  790:1302 */     SupbookRecord supbook = (SupbookRecord)this.supbooks.get(0);
/*  791:1303 */     Assert.verify((supbook.getType() == SupbookRecord.INTERNAL) && (supbook.getNumberOfSheets() == getNumberOfSheets()));
/*  792:     */     
/*  793:     */ 
/*  794:1306 */     return this.externSheet.getIndex(0, sheetpos);
/*  795:     */   }
/*  796:     */   
/*  797:     */   public void setColourRGB(Colour c, int r, int g, int b)
/*  798:     */   {
/*  799:1319 */     this.formatRecords.setColourRGB(c, r, g, b);
/*  800:     */   }
/*  801:     */   
/*  802:     */   public RGB getColourRGB(Colour c)
/*  803:     */   {
/*  804:1329 */     return this.formatRecords.getColourRGB(c);
/*  805:     */   }
/*  806:     */   
/*  807:     */   public String getName(int index)
/*  808:     */   {
/*  809:1340 */     Assert.verify((index >= 0) && (index < this.names.size()));
/*  810:1341 */     NameRecord n = (NameRecord)this.names.get(index);
/*  811:1342 */     return n.getName();
/*  812:     */   }
/*  813:     */   
/*  814:     */   public int getNameIndex(String name)
/*  815:     */   {
/*  816:1353 */     NameRecord nr = (NameRecord)this.nameRecords.get(name);
/*  817:1354 */     return nr != null ? nr.getIndex() : -1;
/*  818:     */   }
/*  819:     */   
/*  820:     */   void addRCIRCell(CellValue cv)
/*  821:     */   {
/*  822:1365 */     this.rcirCells.add(cv);
/*  823:     */   }
/*  824:     */   
/*  825:     */   void columnInserted(WritableSheetImpl s, int col)
/*  826:     */   {
/*  827:1377 */     int externalSheetIndex = getExternalSheetIndex(s.getName());
/*  828:1378 */     for (Iterator i = this.rcirCells.iterator(); i.hasNext();)
/*  829:     */     {
/*  830:1380 */       CellValue cv = (CellValue)i.next();
/*  831:1381 */       cv.columnInserted(s, externalSheetIndex, col);
/*  832:     */     }
/*  833:     */     Iterator i;
/*  834:1385 */     if (this.names != null) {
/*  835:1387 */       for (i = this.names.iterator(); i.hasNext();)
/*  836:     */       {
/*  837:1389 */         NameRecord nameRecord = (NameRecord)i.next();
/*  838:1390 */         nameRecord.columnInserted(externalSheetIndex, col);
/*  839:     */       }
/*  840:     */     }
/*  841:     */   }
/*  842:     */   
/*  843:     */   void columnRemoved(WritableSheetImpl s, int col)
/*  844:     */   {
/*  845:1404 */     int externalSheetIndex = getExternalSheetIndex(s.getName());
/*  846:1405 */     for (Iterator i = this.rcirCells.iterator(); i.hasNext();)
/*  847:     */     {
/*  848:1407 */       CellValue cv = (CellValue)i.next();
/*  849:1408 */       cv.columnRemoved(s, externalSheetIndex, col);
/*  850:     */     }
/*  851:1412 */     ArrayList removedNames = new ArrayList();
/*  852:     */     Iterator i;
/*  853:1413 */     if (this.names != null)
/*  854:     */     {
/*  855:1415 */       for (Iterator i = this.names.iterator(); i.hasNext();)
/*  856:     */       {
/*  857:1417 */         NameRecord nameRecord = (NameRecord)i.next();
/*  858:1418 */         boolean removeName = nameRecord.columnRemoved(externalSheetIndex, col);
/*  859:1421 */         if (removeName) {
/*  860:1423 */           removedNames.add(nameRecord);
/*  861:     */         }
/*  862:     */       }
/*  863:1428 */       for (i = removedNames.iterator(); i.hasNext();)
/*  864:     */       {
/*  865:1430 */         NameRecord nameRecord = (NameRecord)i.next();
/*  866:1431 */         boolean removed = this.names.remove(nameRecord);
/*  867:1432 */         Assert.verify(removed, "Could not remove name " + nameRecord.getName());
/*  868:     */       }
/*  869:     */     }
/*  870:     */   }
/*  871:     */   
/*  872:     */   void rowInserted(WritableSheetImpl s, int row)
/*  873:     */   {
/*  874:1447 */     int externalSheetIndex = getExternalSheetIndex(s.getName());
/*  875:1450 */     for (Iterator i = this.rcirCells.iterator(); i.hasNext();)
/*  876:     */     {
/*  877:1452 */       CellValue cv = (CellValue)i.next();
/*  878:1453 */       cv.rowInserted(s, externalSheetIndex, row);
/*  879:     */     }
/*  880:     */     Iterator i;
/*  881:1457 */     if (this.names != null) {
/*  882:1459 */       for (i = this.names.iterator(); i.hasNext();)
/*  883:     */       {
/*  884:1461 */         NameRecord nameRecord = (NameRecord)i.next();
/*  885:1462 */         nameRecord.rowInserted(externalSheetIndex, row);
/*  886:     */       }
/*  887:     */     }
/*  888:     */   }
/*  889:     */   
/*  890:     */   void rowRemoved(WritableSheetImpl s, int row)
/*  891:     */   {
/*  892:1476 */     int externalSheetIndex = getExternalSheetIndex(s.getName());
/*  893:1477 */     for (Iterator i = this.rcirCells.iterator(); i.hasNext();)
/*  894:     */     {
/*  895:1479 */       CellValue cv = (CellValue)i.next();
/*  896:1480 */       cv.rowRemoved(s, externalSheetIndex, row);
/*  897:     */     }
/*  898:1484 */     ArrayList removedNames = new ArrayList();
/*  899:     */     Iterator i;
/*  900:1485 */     if (this.names != null)
/*  901:     */     {
/*  902:1487 */       for (Iterator i = this.names.iterator(); i.hasNext();)
/*  903:     */       {
/*  904:1489 */         NameRecord nameRecord = (NameRecord)i.next();
/*  905:1490 */         boolean removeName = nameRecord.rowRemoved(externalSheetIndex, row);
/*  906:1492 */         if (removeName) {
/*  907:1494 */           removedNames.add(nameRecord);
/*  908:     */         }
/*  909:     */       }
/*  910:1499 */       for (i = removedNames.iterator(); i.hasNext();)
/*  911:     */       {
/*  912:1501 */         NameRecord nameRecord = (NameRecord)i.next();
/*  913:1502 */         boolean removed = this.names.remove(nameRecord);
/*  914:1503 */         Assert.verify(removed, "Could not remove name " + nameRecord.getName());
/*  915:     */       }
/*  916:     */     }
/*  917:     */   }
/*  918:     */   
/*  919:     */   public WritableCell findCellByName(String name)
/*  920:     */   {
/*  921:1520 */     NameRecord nr = (NameRecord)this.nameRecords.get(name);
/*  922:1522 */     if (nr == null) {
/*  923:1524 */       return null;
/*  924:     */     }
/*  925:1527 */     NameRecord.NameRange[] ranges = nr.getRanges();
/*  926:     */     
/*  927:     */ 
/*  928:1530 */     int sheetIndex = getExternalSheetIndex(ranges[0].getExternalSheet());
/*  929:1531 */     WritableSheet s = getSheet(sheetIndex);
/*  930:1532 */     WritableCell cell = s.getWritableCell(ranges[0].getFirstColumn(), ranges[0].getFirstRow());
/*  931:     */     
/*  932:     */ 
/*  933:1535 */     return cell;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public Range[] findByName(String name)
/*  937:     */   {
/*  938:1554 */     NameRecord nr = (NameRecord)this.nameRecords.get(name);
/*  939:1556 */     if (nr == null) {
/*  940:1558 */       return null;
/*  941:     */     }
/*  942:1561 */     NameRecord.NameRange[] ranges = nr.getRanges();
/*  943:     */     
/*  944:1563 */     Range[] cellRanges = new Range[ranges.length];
/*  945:1565 */     for (int i = 0; i < ranges.length; i++) {
/*  946:1567 */       cellRanges[i] = new RangeImpl(this, getExternalSheetIndex(ranges[i].getExternalSheet()), ranges[i].getFirstColumn(), ranges[i].getFirstRow(), getLastExternalSheetIndex(ranges[i].getExternalSheet()), ranges[i].getLastColumn(), ranges[i].getLastRow());
/*  947:     */     }
/*  948:1577 */     return cellRanges;
/*  949:     */   }
/*  950:     */   
/*  951:     */   void addDrawing(DrawingGroupObject d)
/*  952:     */   {
/*  953:1587 */     if (this.drawingGroup == null) {
/*  954:1589 */       this.drawingGroup = new DrawingGroup(Origin.WRITE);
/*  955:     */     }
/*  956:1592 */     this.drawingGroup.add(d);
/*  957:     */   }
/*  958:     */   
/*  959:     */   void removeDrawing(Drawing d)
/*  960:     */   {
/*  961:1602 */     Assert.verify(this.drawingGroup != null);
/*  962:     */     
/*  963:1604 */     this.drawingGroup.remove(d);
/*  964:     */   }
/*  965:     */   
/*  966:     */   DrawingGroup getDrawingGroup()
/*  967:     */   {
/*  968:1614 */     return this.drawingGroup;
/*  969:     */   }
/*  970:     */   
/*  971:     */   DrawingGroup createDrawingGroup()
/*  972:     */   {
/*  973:1626 */     if (this.drawingGroup == null) {
/*  974:1628 */       this.drawingGroup = new DrawingGroup(Origin.WRITE);
/*  975:     */     }
/*  976:1631 */     return this.drawingGroup;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public String[] getRangeNames()
/*  980:     */   {
/*  981:1641 */     if (this.names == null) {
/*  982:1643 */       return new String[0];
/*  983:     */     }
/*  984:1646 */     String[] n = new String[this.names.size()];
/*  985:1647 */     for (int i = 0; i < this.names.size(); i++)
/*  986:     */     {
/*  987:1649 */       NameRecord nr = (NameRecord)this.names.get(i);
/*  988:1650 */       n[i] = nr.getName();
/*  989:     */     }
/*  990:1653 */     return n;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public void removeRangeName(String name)
/*  994:     */   {
/*  995:1663 */     int pos = 0;
/*  996:1664 */     boolean found = false;
/*  997:1665 */     for (Iterator i = this.names.iterator(); (i.hasNext()) && (!found);)
/*  998:     */     {
/*  999:1667 */       NameRecord nr = (NameRecord)i.next();
/* 1000:1668 */       if (nr.getName().equals(name)) {
/* 1001:1670 */         found = true;
/* 1002:     */       } else {
/* 1003:1674 */         pos++;
/* 1004:     */       }
/* 1005:     */     }
/* 1006:1681 */     if (found)
/* 1007:     */     {
/* 1008:1683 */       this.names.remove(pos);
/* 1009:1684 */       if (this.nameRecords.remove(name) == null) {
/* 1010:1686 */         logger.warn("Could not remove " + name + " from index lookups");
/* 1011:     */       }
/* 1012:     */     }
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   Styles getStyles()
/* 1016:     */   {
/* 1017:1698 */     return this.styles;
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public void addNameArea(String name, WritableSheet sheet, int firstCol, int firstRow, int lastCol, int lastRow)
/* 1021:     */   {
/* 1022:1718 */     addNameArea(name, sheet, firstCol, firstRow, lastCol, lastRow, true);
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   void addNameArea(String name, WritableSheet sheet, int firstCol, int firstRow, int lastCol, int lastRow, boolean global)
/* 1026:     */   {
/* 1027:1741 */     if (this.names == null) {
/* 1028:1743 */       this.names = new ArrayList();
/* 1029:     */     }
/* 1030:1746 */     int externalSheetIndex = getExternalSheetIndex(sheet.getName());
/* 1031:     */     
/* 1032:     */ 
/* 1033:1749 */     NameRecord nr = new NameRecord(name, this.names.size(), externalSheetIndex, firstRow, lastRow, firstCol, lastCol, global);
/* 1034:     */     
/* 1035:     */ 
/* 1036:     */ 
/* 1037:     */ 
/* 1038:     */ 
/* 1039:     */ 
/* 1040:     */ 
/* 1041:     */ 
/* 1042:1758 */     this.names.add(nr);
/* 1043:     */     
/* 1044:     */ 
/* 1045:1761 */     this.nameRecords.put(name, nr);
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   void addNameArea(BuiltInName name, WritableSheet sheet, int firstCol, int firstRow, int lastCol, int lastRow, boolean global)
/* 1049:     */   {
/* 1050:1784 */     if (this.names == null) {
/* 1051:1786 */       this.names = new ArrayList();
/* 1052:     */     }
/* 1053:1789 */     int index = getInternalSheetIndex(sheet.getName());
/* 1054:1790 */     int externalSheetIndex = getExternalSheetIndex(sheet.getName());
/* 1055:     */     
/* 1056:     */ 
/* 1057:1793 */     NameRecord nr = new NameRecord(name, index, externalSheetIndex, firstRow, lastRow, firstCol, lastCol, global);
/* 1058:     */     
/* 1059:     */ 
/* 1060:     */ 
/* 1061:     */ 
/* 1062:     */ 
/* 1063:     */ 
/* 1064:     */ 
/* 1065:     */ 
/* 1066:1802 */     this.names.add(nr);
/* 1067:     */     
/* 1068:     */ 
/* 1069:1805 */     this.nameRecords.put(name, nr);
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   void addNameArea(BuiltInName name, WritableSheet sheet, int firstCol, int firstRow, int lastCol, int lastRow, int firstCol2, int firstRow2, int lastCol2, int lastRow2, boolean global)
/* 1073:     */   {
/* 1074:1836 */     if (this.names == null) {
/* 1075:1838 */       this.names = new ArrayList();
/* 1076:     */     }
/* 1077:1841 */     int index = getInternalSheetIndex(sheet.getName());
/* 1078:1842 */     int externalSheetIndex = getExternalSheetIndex(sheet.getName());
/* 1079:     */     
/* 1080:     */ 
/* 1081:1845 */     NameRecord nr = new NameRecord(name, index, externalSheetIndex, firstRow2, lastRow2, firstCol2, lastCol2, firstRow, lastRow, firstCol, lastCol, global);
/* 1082:     */     
/* 1083:     */ 
/* 1084:     */ 
/* 1085:     */ 
/* 1086:     */ 
/* 1087:     */ 
/* 1088:     */ 
/* 1089:     */ 
/* 1090:     */ 
/* 1091:     */ 
/* 1092:1856 */     this.names.add(nr);
/* 1093:     */     
/* 1094:     */ 
/* 1095:1859 */     this.nameRecords.put(name, nr);
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   WorkbookSettings getSettings()
/* 1099:     */   {
/* 1100:1867 */     return this.settings;
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public WritableCell getWritableCell(String loc)
/* 1104:     */   {
/* 1105:1881 */     WritableSheet s = getSheet(CellReferenceHelper.getSheet(loc));
/* 1106:1882 */     return s.getWritableCell(loc);
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public WritableSheet importSheet(String name, int index, Sheet sheet)
/* 1110:     */   {
/* 1111:1896 */     WritableSheet ws = createSheet(name, index);
/* 1112:1897 */     ((WritableSheetImpl)ws).importSheet(sheet);
/* 1113:     */     
/* 1114:1899 */     return ws;
/* 1115:     */   }
/* 1116:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.WritableWorkbookImpl
 * JD-Core Version:    0.7.0.1
 */