/*    1:     */ package jxl.read.biff;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.HashMap;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.Set;
/*    7:     */ import jxl.Cell;
/*    8:     */ import jxl.Range;
/*    9:     */ import jxl.Sheet;
/*   10:     */ import jxl.Workbook;
/*   11:     */ import jxl.WorkbookSettings;
/*   12:     */ import jxl.biff.BuiltInName;
/*   13:     */ import jxl.biff.CellReferenceHelper;
/*   14:     */ import jxl.biff.EmptyCell;
/*   15:     */ import jxl.biff.FontRecord;
/*   16:     */ import jxl.biff.Fonts;
/*   17:     */ import jxl.biff.FormatRecord;
/*   18:     */ import jxl.biff.FormattingRecords;
/*   19:     */ import jxl.biff.NameRangeException;
/*   20:     */ import jxl.biff.NumFormatRecordsException;
/*   21:     */ import jxl.biff.PaletteRecord;
/*   22:     */ import jxl.biff.RangeImpl;
/*   23:     */ import jxl.biff.StringHelper;
/*   24:     */ import jxl.biff.Type;
/*   25:     */ import jxl.biff.WorkbookMethods;
/*   26:     */ import jxl.biff.XCTRecord;
/*   27:     */ import jxl.biff.XFRecord;
/*   28:     */ import jxl.biff.drawing.DrawingGroup;
/*   29:     */ import jxl.biff.drawing.MsoDrawingGroupRecord;
/*   30:     */ import jxl.biff.drawing.Origin;
/*   31:     */ import jxl.biff.formula.ExternalSheet;
/*   32:     */ import jxl.common.Assert;
/*   33:     */ import jxl.common.Logger;
/*   34:     */ 
/*   35:     */ public class WorkbookParser
/*   36:     */   extends Workbook
/*   37:     */   implements ExternalSheet, WorkbookMethods
/*   38:     */ {
/*   39:  65 */   private static Logger logger = Logger.getLogger(WorkbookParser.class);
/*   40:     */   private File excelFile;
/*   41:     */   private int bofs;
/*   42:     */   private boolean nineteenFour;
/*   43:     */   private SSTRecord sharedStrings;
/*   44:     */   private ArrayList boundsheets;
/*   45:     */   private FormattingRecords formattingRecords;
/*   46:     */   private Fonts fonts;
/*   47:     */   private ArrayList sheets;
/*   48:     */   private SheetImpl lastSheet;
/*   49:     */   private int lastSheetIndex;
/*   50:     */   private HashMap namedRecords;
/*   51:     */   private ArrayList nameTable;
/*   52:     */   private ArrayList addInFunctions;
/*   53:     */   private ExternalSheetRecord externSheet;
/*   54:     */   private ArrayList supbooks;
/*   55:     */   private BOFRecord workbookBof;
/*   56:     */   private MsoDrawingGroupRecord msoDrawingGroup;
/*   57:     */   private ButtonPropertySetRecord buttonPropertySet;
/*   58:     */   private boolean wbProtected;
/*   59:     */   private boolean containsMacros;
/*   60:     */   private WorkbookSettings settings;
/*   61:     */   private DrawingGroup drawingGroup;
/*   62:     */   private CountryRecord countryRecord;
/*   63:     */   private ArrayList xctRecords;
/*   64:     */   
/*   65:     */   public WorkbookParser(File f, WorkbookSettings s)
/*   66:     */   {
/*   67: 188 */     this.excelFile = f;
/*   68: 189 */     this.boundsheets = new ArrayList(10);
/*   69: 190 */     this.fonts = new Fonts();
/*   70: 191 */     this.formattingRecords = new FormattingRecords(this.fonts);
/*   71: 192 */     this.sheets = new ArrayList(10);
/*   72: 193 */     this.supbooks = new ArrayList(10);
/*   73: 194 */     this.namedRecords = new HashMap();
/*   74: 195 */     this.lastSheetIndex = -1;
/*   75: 196 */     this.wbProtected = false;
/*   76: 197 */     this.containsMacros = false;
/*   77: 198 */     this.settings = s;
/*   78: 199 */     this.xctRecords = new ArrayList(10);
/*   79:     */   }
/*   80:     */   
/*   81:     */   public Sheet[] getSheets()
/*   82:     */   {
/*   83: 212 */     Sheet[] sheetArray = new Sheet[getNumberOfSheets()];
/*   84: 213 */     return (Sheet[])this.sheets.toArray(sheetArray);
/*   85:     */   }
/*   86:     */   
/*   87:     */   public Sheet getReadSheet(int index)
/*   88:     */   {
/*   89: 225 */     return getSheet(index);
/*   90:     */   }
/*   91:     */   
/*   92:     */   public Sheet getSheet(int index)
/*   93:     */   {
/*   94: 239 */     if ((this.lastSheet != null) && (this.lastSheetIndex == index)) {
/*   95: 241 */       return this.lastSheet;
/*   96:     */     }
/*   97: 245 */     if (this.lastSheet != null)
/*   98:     */     {
/*   99: 247 */       this.lastSheet.clear();
/*  100: 249 */       if (!this.settings.getGCDisabled()) {
/*  101: 251 */         System.gc();
/*  102:     */       }
/*  103:     */     }
/*  104: 255 */     this.lastSheet = ((SheetImpl)this.sheets.get(index));
/*  105: 256 */     this.lastSheetIndex = index;
/*  106: 257 */     this.lastSheet.readSheet();
/*  107:     */     
/*  108: 259 */     return this.lastSheet;
/*  109:     */   }
/*  110:     */   
/*  111:     */   public Sheet getSheet(String name)
/*  112:     */   {
/*  113: 271 */     int pos = 0;
/*  114: 272 */     boolean found = false;
/*  115: 273 */     Iterator i = this.boundsheets.iterator();
/*  116: 274 */     BoundsheetRecord br = null;
/*  117: 276 */     while ((i.hasNext()) && (!found))
/*  118:     */     {
/*  119: 278 */       br = (BoundsheetRecord)i.next();
/*  120: 280 */       if (br.getName().equals(name)) {
/*  121: 282 */         found = true;
/*  122:     */       } else {
/*  123: 286 */         pos++;
/*  124:     */       }
/*  125:     */     }
/*  126: 290 */     return found ? getSheet(pos) : null;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public String[] getSheetNames()
/*  130:     */   {
/*  131: 300 */     String[] names = new String[this.boundsheets.size()];
/*  132:     */     
/*  133: 302 */     BoundsheetRecord br = null;
/*  134: 303 */     for (int i = 0; i < names.length; i++)
/*  135:     */     {
/*  136: 305 */       br = (BoundsheetRecord)this.boundsheets.get(i);
/*  137: 306 */       names[i] = br.getName();
/*  138:     */     }
/*  139: 309 */     return names;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public int getExternalSheetIndex(int index)
/*  143:     */   {
/*  144: 325 */     if (this.workbookBof.isBiff7()) {
/*  145: 327 */       return index;
/*  146:     */     }
/*  147: 330 */     Assert.verify(this.externSheet != null);
/*  148:     */     
/*  149: 332 */     int firstTab = this.externSheet.getFirstTabIndex(index);
/*  150:     */     
/*  151: 334 */     return firstTab;
/*  152:     */   }
/*  153:     */   
/*  154:     */   public int getLastExternalSheetIndex(int index)
/*  155:     */   {
/*  156: 349 */     if (this.workbookBof.isBiff7()) {
/*  157: 351 */       return index;
/*  158:     */     }
/*  159: 354 */     Assert.verify(this.externSheet != null);
/*  160:     */     
/*  161: 356 */     int lastTab = this.externSheet.getLastTabIndex(index);
/*  162:     */     
/*  163: 358 */     return lastTab;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public String getExternalSheetName(int index)
/*  167:     */   {
/*  168: 371 */     if (this.workbookBof.isBiff7())
/*  169:     */     {
/*  170: 373 */       BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(index);
/*  171:     */       
/*  172: 375 */       return br.getName();
/*  173:     */     }
/*  174: 378 */     int supbookIndex = this.externSheet.getSupbookIndex(index);
/*  175: 379 */     SupbookRecord sr = (SupbookRecord)this.supbooks.get(supbookIndex);
/*  176:     */     
/*  177: 381 */     int firstTab = this.externSheet.getFirstTabIndex(index);
/*  178: 382 */     int lastTab = this.externSheet.getLastTabIndex(index);
/*  179: 383 */     String firstTabName = "";
/*  180: 384 */     String lastTabName = "";
/*  181: 386 */     if (sr.getType() == SupbookRecord.INTERNAL)
/*  182:     */     {
/*  183: 389 */       if (firstTab == 65535)
/*  184:     */       {
/*  185: 391 */         firstTabName = "#REF";
/*  186:     */       }
/*  187:     */       else
/*  188:     */       {
/*  189: 395 */         BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(firstTab);
/*  190: 396 */         firstTabName = br.getName();
/*  191:     */       }
/*  192: 399 */       if (lastTab == 65535)
/*  193:     */       {
/*  194: 401 */         lastTabName = "#REF";
/*  195:     */       }
/*  196:     */       else
/*  197:     */       {
/*  198: 405 */         BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(lastTab);
/*  199: 406 */         lastTabName = br.getName();
/*  200:     */       }
/*  201: 409 */       String sheetName = firstTabName + ':' + lastTabName;
/*  202:     */       
/*  203:     */ 
/*  204:     */ 
/*  205: 413 */       sheetName = sheetName.indexOf('\'') == -1 ? sheetName : StringHelper.replace(sheetName, "'", "''");
/*  206:     */       
/*  207:     */ 
/*  208:     */ 
/*  209:     */ 
/*  210: 418 */       return '\'' + sheetName + '\'';
/*  211:     */     }
/*  212: 421 */     if (sr.getType() == SupbookRecord.EXTERNAL)
/*  213:     */     {
/*  214: 424 */       StringBuffer sb = new StringBuffer();
/*  215: 425 */       java.io.File fl = new java.io.File(sr.getFileName());
/*  216: 426 */       sb.append("'");
/*  217: 427 */       sb.append(fl.getAbsolutePath());
/*  218: 428 */       sb.append("[");
/*  219: 429 */       sb.append(fl.getName());
/*  220: 430 */       sb.append("]");
/*  221: 431 */       sb.append(firstTab == 65535 ? "#REF" : sr.getSheetName(firstTab));
/*  222: 432 */       if (lastTab != firstTab) {
/*  223: 434 */         sb.append(sr.getSheetName(lastTab));
/*  224:     */       }
/*  225: 436 */       sb.append("'");
/*  226: 437 */       return sb.toString();
/*  227:     */     }
/*  228: 441 */     logger.warn("Unknown Supbook 3");
/*  229: 442 */     return "[UNKNOWN]";
/*  230:     */   }
/*  231:     */   
/*  232:     */   public String getLastExternalSheetName(int index)
/*  233:     */   {
/*  234: 455 */     if (this.workbookBof.isBiff7())
/*  235:     */     {
/*  236: 457 */       BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(index);
/*  237:     */       
/*  238: 459 */       return br.getName();
/*  239:     */     }
/*  240: 462 */     int supbookIndex = this.externSheet.getSupbookIndex(index);
/*  241: 463 */     SupbookRecord sr = (SupbookRecord)this.supbooks.get(supbookIndex);
/*  242:     */     
/*  243: 465 */     int lastTab = this.externSheet.getLastTabIndex(index);
/*  244: 467 */     if (sr.getType() == SupbookRecord.INTERNAL)
/*  245:     */     {
/*  246: 470 */       if (lastTab == 65535) {
/*  247: 472 */         return "#REF";
/*  248:     */       }
/*  249: 476 */       BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(lastTab);
/*  250: 477 */       return br.getName();
/*  251:     */     }
/*  252: 480 */     if (sr.getType() == SupbookRecord.EXTERNAL)
/*  253:     */     {
/*  254: 483 */       StringBuffer sb = new StringBuffer();
/*  255: 484 */       java.io.File fl = new java.io.File(sr.getFileName());
/*  256: 485 */       sb.append("'");
/*  257: 486 */       sb.append(fl.getAbsolutePath());
/*  258: 487 */       sb.append("[");
/*  259: 488 */       sb.append(fl.getName());
/*  260: 489 */       sb.append("]");
/*  261: 490 */       sb.append(lastTab == 65535 ? "#REF" : sr.getSheetName(lastTab));
/*  262: 491 */       sb.append("'");
/*  263: 492 */       return sb.toString();
/*  264:     */     }
/*  265: 496 */     logger.warn("Unknown Supbook 4");
/*  266: 497 */     return "[UNKNOWN]";
/*  267:     */   }
/*  268:     */   
/*  269:     */   public int getNumberOfSheets()
/*  270:     */   {
/*  271: 507 */     return this.sheets.size();
/*  272:     */   }
/*  273:     */   
/*  274:     */   public void close()
/*  275:     */   {
/*  276: 516 */     if (this.lastSheet != null) {
/*  277: 518 */       this.lastSheet.clear();
/*  278:     */     }
/*  279: 520 */     this.excelFile.clear();
/*  280: 522 */     if (!this.settings.getGCDisabled()) {
/*  281: 524 */       System.gc();
/*  282:     */     }
/*  283:     */   }
/*  284:     */   
/*  285:     */   final void addSheet(Sheet s)
/*  286:     */   {
/*  287: 535 */     this.sheets.add(s);
/*  288:     */   }
/*  289:     */   
/*  290:     */   protected void parse()
/*  291:     */     throws BiffException, PasswordException
/*  292:     */   {
/*  293: 546 */     Record r = null;
/*  294:     */     
/*  295: 548 */     BOFRecord bof = new BOFRecord(this.excelFile.next());
/*  296: 549 */     this.workbookBof = bof;
/*  297: 550 */     this.bofs += 1;
/*  298: 552 */     if ((!bof.isBiff8()) && (!bof.isBiff7())) {
/*  299: 554 */       throw new BiffException(BiffException.unrecognizedBiffVersion);
/*  300:     */     }
/*  301: 557 */     if (!bof.isWorkbookGlobals()) {
/*  302: 559 */       throw new BiffException(BiffException.expectedGlobals);
/*  303:     */     }
/*  304: 561 */     ArrayList continueRecords = new ArrayList();
/*  305: 562 */     ArrayList localNames = new ArrayList();
/*  306: 563 */     this.nameTable = new ArrayList();
/*  307: 564 */     this.addInFunctions = new ArrayList();
/*  308: 567 */     while (this.bofs == 1)
/*  309:     */     {
/*  310: 569 */       r = this.excelFile.next();
/*  311: 571 */       if (r.getType() == Type.SST)
/*  312:     */       {
/*  313: 573 */         continueRecords.clear();
/*  314: 574 */         Record nextrec = this.excelFile.peek();
/*  315: 575 */         while (nextrec.getType() == Type.CONTINUE)
/*  316:     */         {
/*  317: 577 */           continueRecords.add(this.excelFile.next());
/*  318: 578 */           nextrec = this.excelFile.peek();
/*  319:     */         }
/*  320: 582 */         Record[] records = new Record[continueRecords.size()];
/*  321: 583 */         records = (Record[])continueRecords.toArray(records);
/*  322:     */         
/*  323: 585 */         this.sharedStrings = new SSTRecord(r, records, this.settings);
/*  324:     */       }
/*  325:     */       else
/*  326:     */       {
/*  327: 587 */         if (r.getType() == Type.FILEPASS) {
/*  328: 589 */           throw new PasswordException();
/*  329:     */         }
/*  330: 591 */         if (r.getType() == Type.NAME)
/*  331:     */         {
/*  332: 593 */           NameRecord nr = null;
/*  333: 595 */           if (bof.isBiff8()) {
/*  334: 597 */             nr = new NameRecord(r, this.settings, this.nameTable.size());
/*  335:     */           } else {
/*  336: 602 */             nr = new NameRecord(r, this.settings, this.nameTable.size(), NameRecord.biff7);
/*  337:     */           }
/*  338: 608 */           this.nameTable.add(nr);
/*  339: 610 */           if (nr.isGlobal()) {
/*  340: 612 */             this.namedRecords.put(nr.getName(), nr);
/*  341:     */           } else {
/*  342: 616 */             localNames.add(nr);
/*  343:     */           }
/*  344:     */         }
/*  345: 619 */         else if (r.getType() == Type.FONT)
/*  346:     */         {
/*  347: 621 */           FontRecord fr = null;
/*  348: 623 */           if (bof.isBiff8()) {
/*  349: 625 */             fr = new FontRecord(r, this.settings);
/*  350:     */           } else {
/*  351: 629 */             fr = new FontRecord(r, this.settings, FontRecord.biff7);
/*  352:     */           }
/*  353: 631 */           this.fonts.addFont(fr);
/*  354:     */         }
/*  355: 633 */         else if (r.getType() == Type.PALETTE)
/*  356:     */         {
/*  357: 635 */           PaletteRecord palette = new PaletteRecord(r);
/*  358: 636 */           this.formattingRecords.setPalette(palette);
/*  359:     */         }
/*  360: 638 */         else if (r.getType() == Type.NINETEENFOUR)
/*  361:     */         {
/*  362: 640 */           NineteenFourRecord nr = new NineteenFourRecord(r);
/*  363: 641 */           this.nineteenFour = nr.is1904();
/*  364:     */         }
/*  365: 643 */         else if (r.getType() == Type.FORMAT)
/*  366:     */         {
/*  367: 645 */           FormatRecord fr = null;
/*  368: 646 */           if (bof.isBiff8()) {
/*  369: 648 */             fr = new FormatRecord(r, this.settings, FormatRecord.biff8);
/*  370:     */           } else {
/*  371: 652 */             fr = new FormatRecord(r, this.settings, FormatRecord.biff7);
/*  372:     */           }
/*  373:     */           try
/*  374:     */           {
/*  375: 656 */             this.formattingRecords.addFormat(fr);
/*  376:     */           }
/*  377:     */           catch (NumFormatRecordsException e)
/*  378:     */           {
/*  379: 661 */             Assert.verify(false, e.getMessage());
/*  380:     */           }
/*  381:     */         }
/*  382: 664 */         else if (r.getType() == Type.XF)
/*  383:     */         {
/*  384: 666 */           XFRecord xfr = null;
/*  385: 667 */           if (bof.isBiff8()) {
/*  386: 669 */             xfr = new XFRecord(r, this.settings, XFRecord.biff8);
/*  387:     */           } else {
/*  388: 673 */             xfr = new XFRecord(r, this.settings, XFRecord.biff7);
/*  389:     */           }
/*  390:     */           try
/*  391:     */           {
/*  392: 678 */             this.formattingRecords.addStyle(xfr);
/*  393:     */           }
/*  394:     */           catch (NumFormatRecordsException e)
/*  395:     */           {
/*  396: 683 */             Assert.verify(false, e.getMessage());
/*  397:     */           }
/*  398:     */         }
/*  399: 686 */         else if (r.getType() == Type.BOUNDSHEET)
/*  400:     */         {
/*  401: 688 */           BoundsheetRecord br = null;
/*  402: 690 */           if (bof.isBiff8()) {
/*  403: 692 */             br = new BoundsheetRecord(r, this.settings);
/*  404:     */           } else {
/*  405: 696 */             br = new BoundsheetRecord(r, BoundsheetRecord.biff7);
/*  406:     */           }
/*  407: 699 */           if (br.isSheet()) {
/*  408: 701 */             this.boundsheets.add(br);
/*  409: 703 */           } else if ((br.isChart()) && (!this.settings.getDrawingsDisabled())) {
/*  410: 705 */             this.boundsheets.add(br);
/*  411:     */           }
/*  412:     */         }
/*  413: 708 */         else if (r.getType() == Type.EXTERNSHEET)
/*  414:     */         {
/*  415: 710 */           if (bof.isBiff8()) {
/*  416: 712 */             this.externSheet = new ExternalSheetRecord(r, this.settings);
/*  417:     */           } else {
/*  418: 716 */             this.externSheet = new ExternalSheetRecord(r, this.settings, ExternalSheetRecord.biff7);
/*  419:     */           }
/*  420:     */         }
/*  421: 720 */         else if (r.getType() == Type.XCT)
/*  422:     */         {
/*  423: 722 */           XCTRecord xctr = new XCTRecord(r);
/*  424: 723 */           this.xctRecords.add(xctr);
/*  425:     */         }
/*  426: 725 */         else if (r.getType() == Type.CODEPAGE)
/*  427:     */         {
/*  428: 727 */           CodepageRecord cr = new CodepageRecord(r);
/*  429: 728 */           this.settings.setCharacterSet(cr.getCharacterSet());
/*  430:     */         }
/*  431: 730 */         else if (r.getType() == Type.SUPBOOK)
/*  432:     */         {
/*  433: 732 */           Record nextrec = this.excelFile.peek();
/*  434: 733 */           while (nextrec.getType() == Type.CONTINUE)
/*  435:     */           {
/*  436: 735 */             r.addContinueRecord(this.excelFile.next());
/*  437: 736 */             nextrec = this.excelFile.peek();
/*  438:     */           }
/*  439: 739 */           SupbookRecord sr = new SupbookRecord(r, this.settings);
/*  440: 740 */           this.supbooks.add(sr);
/*  441:     */         }
/*  442: 742 */         else if (r.getType() == Type.EXTERNNAME)
/*  443:     */         {
/*  444: 744 */           ExternalNameRecord enr = new ExternalNameRecord(r, this.settings);
/*  445: 746 */           if (enr.isAddInFunction()) {
/*  446: 748 */             this.addInFunctions.add(enr.getName());
/*  447:     */           }
/*  448:     */         }
/*  449: 751 */         else if (r.getType() == Type.PROTECT)
/*  450:     */         {
/*  451: 753 */           ProtectRecord pr = new ProtectRecord(r);
/*  452: 754 */           this.wbProtected = pr.isProtected();
/*  453:     */         }
/*  454: 756 */         else if (r.getType() == Type.OBJPROJ)
/*  455:     */         {
/*  456: 758 */           this.containsMacros = true;
/*  457:     */         }
/*  458: 760 */         else if (r.getType() == Type.COUNTRY)
/*  459:     */         {
/*  460: 762 */           this.countryRecord = new CountryRecord(r);
/*  461:     */         }
/*  462: 764 */         else if (r.getType() == Type.MSODRAWINGGROUP)
/*  463:     */         {
/*  464: 766 */           if (!this.settings.getDrawingsDisabled())
/*  465:     */           {
/*  466: 768 */             this.msoDrawingGroup = new MsoDrawingGroupRecord(r);
/*  467: 770 */             if (this.drawingGroup == null) {
/*  468: 772 */               this.drawingGroup = new DrawingGroup(Origin.READ);
/*  469:     */             }
/*  470: 775 */             this.drawingGroup.add(this.msoDrawingGroup);
/*  471:     */             
/*  472: 777 */             Record nextrec = this.excelFile.peek();
/*  473: 778 */             while (nextrec.getType() == Type.CONTINUE)
/*  474:     */             {
/*  475: 780 */               this.drawingGroup.add(this.excelFile.next());
/*  476: 781 */               nextrec = this.excelFile.peek();
/*  477:     */             }
/*  478:     */           }
/*  479:     */         }
/*  480: 785 */         else if (r.getType() == Type.BUTTONPROPERTYSET)
/*  481:     */         {
/*  482: 787 */           this.buttonPropertySet = new ButtonPropertySetRecord(r);
/*  483:     */         }
/*  484: 789 */         else if (r.getType() == Type.EOF)
/*  485:     */         {
/*  486: 791 */           this.bofs -= 1;
/*  487:     */         }
/*  488: 793 */         else if (r.getType() == Type.REFRESHALL)
/*  489:     */         {
/*  490: 795 */           RefreshAllRecord rfm = new RefreshAllRecord(r);
/*  491: 796 */           this.settings.setRefreshAll(rfm.getRefreshAll());
/*  492:     */         }
/*  493: 798 */         else if (r.getType() == Type.TEMPLATE)
/*  494:     */         {
/*  495: 800 */           TemplateRecord rfm = new TemplateRecord(r);
/*  496: 801 */           this.settings.setTemplate(rfm.getTemplate());
/*  497:     */         }
/*  498: 803 */         else if (r.getType() == Type.EXCEL9FILE)
/*  499:     */         {
/*  500: 805 */           Excel9FileRecord e9f = new Excel9FileRecord(r);
/*  501: 806 */           this.settings.setExcel9File(e9f.getExcel9File());
/*  502:     */         }
/*  503: 808 */         else if (r.getType() == Type.WINDOWPROTECT)
/*  504:     */         {
/*  505: 810 */           WindowProtectedRecord winp = new WindowProtectedRecord(r);
/*  506: 811 */           this.settings.setWindowProtected(winp.getWindowProtected());
/*  507:     */         }
/*  508: 813 */         else if (r.getType() == Type.HIDEOBJ)
/*  509:     */         {
/*  510: 815 */           HideobjRecord hobj = new HideobjRecord(r);
/*  511: 816 */           this.settings.setHideobj(hobj.getHideMode());
/*  512:     */         }
/*  513: 818 */         else if (r.getType() == Type.WRITEACCESS)
/*  514:     */         {
/*  515: 820 */           WriteAccessRecord war = new WriteAccessRecord(r, bof.isBiff8(), this.settings);
/*  516:     */           
/*  517: 822 */           this.settings.setWriteAccess(war.getWriteAccess());
/*  518:     */         }
/*  519:     */       }
/*  520:     */     }
/*  521: 831 */     bof = null;
/*  522: 832 */     if (this.excelFile.hasNext())
/*  523:     */     {
/*  524: 834 */       r = this.excelFile.next();
/*  525: 836 */       if (r.getType() == Type.BOF) {
/*  526: 838 */         bof = new BOFRecord(r);
/*  527:     */       }
/*  528:     */     }
/*  529: 843 */     while ((bof != null) && (getNumberOfSheets() < this.boundsheets.size()))
/*  530:     */     {
/*  531: 845 */       if ((!bof.isBiff8()) && (!bof.isBiff7())) {
/*  532: 847 */         throw new BiffException(BiffException.unrecognizedBiffVersion);
/*  533:     */       }
/*  534: 850 */       if (bof.isWorksheet())
/*  535:     */       {
/*  536: 853 */         SheetImpl s = new SheetImpl(this.excelFile, this.sharedStrings, this.formattingRecords, bof, this.workbookBof, this.nineteenFour, this);
/*  537:     */         
/*  538:     */ 
/*  539:     */ 
/*  540:     */ 
/*  541:     */ 
/*  542:     */ 
/*  543:     */ 
/*  544: 861 */         BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(getNumberOfSheets());
/*  545:     */         
/*  546: 863 */         s.setName(br.getName());
/*  547: 864 */         s.setHidden(br.isHidden());
/*  548: 865 */         addSheet(s);
/*  549:     */       }
/*  550: 867 */       else if (bof.isChart())
/*  551:     */       {
/*  552: 870 */         SheetImpl s = new SheetImpl(this.excelFile, this.sharedStrings, this.formattingRecords, bof, this.workbookBof, this.nineteenFour, this);
/*  553:     */         
/*  554:     */ 
/*  555:     */ 
/*  556:     */ 
/*  557:     */ 
/*  558:     */ 
/*  559:     */ 
/*  560: 878 */         BoundsheetRecord br = (BoundsheetRecord)this.boundsheets.get(getNumberOfSheets());
/*  561:     */         
/*  562: 880 */         s.setName(br.getName());
/*  563: 881 */         s.setHidden(br.isHidden());
/*  564: 882 */         addSheet(s);
/*  565:     */       }
/*  566:     */       else
/*  567:     */       {
/*  568: 886 */         logger.warn("BOF is unrecognized");
/*  569: 889 */         while ((this.excelFile.hasNext()) && (r.getType() != Type.EOF)) {
/*  570: 891 */           r = this.excelFile.next();
/*  571:     */         }
/*  572:     */       }
/*  573: 900 */       bof = null;
/*  574: 901 */       if (this.excelFile.hasNext())
/*  575:     */       {
/*  576: 903 */         r = this.excelFile.next();
/*  577: 905 */         if (r.getType() == Type.BOF) {
/*  578: 907 */           bof = new BOFRecord(r);
/*  579:     */         }
/*  580:     */       }
/*  581:     */     }
/*  582: 913 */     for (Iterator it = localNames.iterator(); it.hasNext();)
/*  583:     */     {
/*  584: 915 */       NameRecord nr = (NameRecord)it.next();
/*  585: 917 */       if (nr.getBuiltInName() == null)
/*  586:     */       {
/*  587: 919 */         logger.warn("Usage of a local non-builtin name");
/*  588:     */       }
/*  589: 921 */       else if ((nr.getBuiltInName() == BuiltInName.PRINT_AREA) || (nr.getBuiltInName() == BuiltInName.PRINT_TITLES))
/*  590:     */       {
/*  591: 926 */         SheetImpl s = (SheetImpl)this.sheets.get(nr.getSheetRef() - 1);
/*  592: 927 */         s.addLocalName(nr);
/*  593:     */       }
/*  594:     */     }
/*  595:     */   }
/*  596:     */   
/*  597:     */   public FormattingRecords getFormattingRecords()
/*  598:     */   {
/*  599: 940 */     return this.formattingRecords;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public ExternalSheetRecord getExternalSheetRecord()
/*  603:     */   {
/*  604: 951 */     return this.externSheet;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public MsoDrawingGroupRecord getMsoDrawingGroupRecord()
/*  608:     */   {
/*  609: 962 */     return this.msoDrawingGroup;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public SupbookRecord[] getSupbookRecords()
/*  613:     */   {
/*  614: 973 */     SupbookRecord[] sr = new SupbookRecord[this.supbooks.size()];
/*  615: 974 */     return (SupbookRecord[])this.supbooks.toArray(sr);
/*  616:     */   }
/*  617:     */   
/*  618:     */   public NameRecord[] getNameRecords()
/*  619:     */   {
/*  620: 985 */     NameRecord[] na = new NameRecord[this.nameTable.size()];
/*  621: 986 */     return (NameRecord[])this.nameTable.toArray(na);
/*  622:     */   }
/*  623:     */   
/*  624:     */   public Fonts getFonts()
/*  625:     */   {
/*  626: 996 */     return this.fonts;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public Cell getCell(String loc)
/*  630:     */   {
/*  631:1010 */     Sheet s = getSheet(CellReferenceHelper.getSheet(loc));
/*  632:1011 */     return s.getCell(loc);
/*  633:     */   }
/*  634:     */   
/*  635:     */   public Cell findCellByName(String name)
/*  636:     */   {
/*  637:1025 */     NameRecord nr = (NameRecord)this.namedRecords.get(name);
/*  638:1027 */     if (nr == null) {
/*  639:1029 */       return null;
/*  640:     */     }
/*  641:1032 */     NameRecord.NameRange[] ranges = nr.getRanges();
/*  642:     */     
/*  643:     */ 
/*  644:1035 */     Sheet s = getSheet(getExternalSheetIndex(ranges[0].getExternalSheet()));
/*  645:1036 */     int col = ranges[0].getFirstColumn();
/*  646:1037 */     int row = ranges[0].getFirstRow();
/*  647:1041 */     if ((col > s.getColumns()) || (row > s.getRows())) {
/*  648:1043 */       return new EmptyCell(col, row);
/*  649:     */     }
/*  650:1046 */     Cell cell = s.getCell(col, row);
/*  651:     */     
/*  652:1048 */     return cell;
/*  653:     */   }
/*  654:     */   
/*  655:     */   public Range[] findByName(String name)
/*  656:     */   {
/*  657:1067 */     NameRecord nr = (NameRecord)this.namedRecords.get(name);
/*  658:1069 */     if (nr == null) {
/*  659:1071 */       return null;
/*  660:     */     }
/*  661:1074 */     NameRecord.NameRange[] ranges = nr.getRanges();
/*  662:     */     
/*  663:1076 */     Range[] cellRanges = new Range[ranges.length];
/*  664:1078 */     for (int i = 0; i < ranges.length; i++) {
/*  665:1080 */       cellRanges[i] = new RangeImpl(this, getExternalSheetIndex(ranges[i].getExternalSheet()), ranges[i].getFirstColumn(), ranges[i].getFirstRow(), getLastExternalSheetIndex(ranges[i].getExternalSheet()), ranges[i].getLastColumn(), ranges[i].getLastRow());
/*  666:     */     }
/*  667:1090 */     return cellRanges;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public String[] getRangeNames()
/*  671:     */   {
/*  672:1100 */     Object[] keys = this.namedRecords.keySet().toArray();
/*  673:1101 */     String[] names = new String[keys.length];
/*  674:1102 */     System.arraycopy(keys, 0, names, 0, keys.length);
/*  675:     */     
/*  676:1104 */     return names;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public BOFRecord getWorkbookBof()
/*  680:     */   {
/*  681:1115 */     return this.workbookBof;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public boolean isProtected()
/*  685:     */   {
/*  686:1125 */     return this.wbProtected;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public WorkbookSettings getSettings()
/*  690:     */   {
/*  691:1135 */     return this.settings;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public int getExternalSheetIndex(String sheetName)
/*  695:     */   {
/*  696:1146 */     return 0;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public int getLastExternalSheetIndex(String sheetName)
/*  700:     */   {
/*  701:1157 */     return 0;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public String getName(int index)
/*  705:     */     throws NameRangeException
/*  706:     */   {
/*  707:1170 */     if ((index < 0) || (index >= this.nameTable.size())) {
/*  708:1172 */       throw new NameRangeException();
/*  709:     */     }
/*  710:1174 */     return ((NameRecord)this.nameTable.get(index)).getName();
/*  711:     */   }
/*  712:     */   
/*  713:     */   public int getNameIndex(String name)
/*  714:     */   {
/*  715:1185 */     NameRecord nr = (NameRecord)this.namedRecords.get(name);
/*  716:     */     
/*  717:1187 */     return nr != null ? nr.getIndex() : 0;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public DrawingGroup getDrawingGroup()
/*  721:     */   {
/*  722:1197 */     return this.drawingGroup;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public CompoundFile getCompoundFile()
/*  726:     */   {
/*  727:1211 */     return this.excelFile.getCompoundFile();
/*  728:     */   }
/*  729:     */   
/*  730:     */   public boolean containsMacros()
/*  731:     */   {
/*  732:1221 */     return this.containsMacros;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public ButtonPropertySetRecord getButtonPropertySet()
/*  736:     */   {
/*  737:1231 */     return this.buttonPropertySet;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public CountryRecord getCountryRecord()
/*  741:     */   {
/*  742:1241 */     return this.countryRecord;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public String[] getAddInFunctionNames()
/*  746:     */   {
/*  747:1251 */     String[] addins = new String[0];
/*  748:1252 */     return (String[])this.addInFunctions.toArray(addins);
/*  749:     */   }
/*  750:     */   
/*  751:     */   public int getIndex(Sheet sheet)
/*  752:     */   {
/*  753:1263 */     String name = sheet.getName();
/*  754:1264 */     int index = -1;
/*  755:1265 */     int pos = 0;
/*  756:1267 */     for (Iterator i = this.boundsheets.iterator(); (i.hasNext()) && (index == -1);)
/*  757:     */     {
/*  758:1269 */       BoundsheetRecord br = (BoundsheetRecord)i.next();
/*  759:1271 */       if (br.getName().equals(name)) {
/*  760:1273 */         index = pos;
/*  761:     */       } else {
/*  762:1277 */         pos++;
/*  763:     */       }
/*  764:     */     }
/*  765:1281 */     return index;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public XCTRecord[] getXCTRecords()
/*  769:     */   {
/*  770:1286 */     XCTRecord[] xctr = new XCTRecord[0];
/*  771:1287 */     return (XCTRecord[])this.xctRecords.toArray(xctr);
/*  772:     */   }
/*  773:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.WorkbookParser
 * JD-Core Version:    0.7.0.1
 */