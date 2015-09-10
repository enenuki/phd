/*    1:     */ package jxl;
/*    2:     */ 
/*    3:     */ import jxl.biff.SheetRangeImpl;
/*    4:     */ import jxl.common.Assert;
/*    5:     */ import jxl.format.PageOrder;
/*    6:     */ import jxl.format.PageOrientation;
/*    7:     */ import jxl.format.PaperSize;
/*    8:     */ 
/*    9:     */ public final class SheetSettings
/*   10:     */ {
/*   11:     */   private PageOrientation orientation;
/*   12:     */   private PageOrder pageOrder;
/*   13:     */   private PaperSize paperSize;
/*   14:     */   private boolean sheetProtected;
/*   15:     */   private boolean hidden;
/*   16:     */   private boolean selected;
/*   17:     */   private HeaderFooter header;
/*   18:     */   private double headerMargin;
/*   19:     */   private HeaderFooter footer;
/*   20:     */   private double footerMargin;
/*   21:     */   private int scaleFactor;
/*   22:     */   private int zoomFactor;
/*   23:     */   private int pageStart;
/*   24:     */   private int fitWidth;
/*   25:     */   private int fitHeight;
/*   26:     */   private int horizontalPrintResolution;
/*   27:     */   private int verticalPrintResolution;
/*   28:     */   private double leftMargin;
/*   29:     */   private double rightMargin;
/*   30:     */   private double topMargin;
/*   31:     */   private double bottomMargin;
/*   32:     */   private boolean fitToPages;
/*   33:     */   private boolean showGridLines;
/*   34:     */   private boolean printGridLines;
/*   35:     */   private boolean printHeaders;
/*   36:     */   private boolean pageBreakPreviewMode;
/*   37:     */   private boolean displayZeroValues;
/*   38:     */   private String password;
/*   39:     */   private int passwordHash;
/*   40:     */   private int defaultColumnWidth;
/*   41:     */   private int defaultRowHeight;
/*   42:     */   private int horizontalFreeze;
/*   43:     */   private int verticalFreeze;
/*   44:     */   private boolean verticalCentre;
/*   45:     */   private boolean horizontalCentre;
/*   46:     */   private int copies;
/*   47:     */   private boolean automaticFormulaCalculation;
/*   48:     */   private boolean recalculateFormulasBeforeSave;
/*   49:     */   private int pageBreakPreviewMagnification;
/*   50:     */   private int normalMagnification;
/*   51:     */   private Range printArea;
/*   52:     */   private Range printTitlesRow;
/*   53:     */   private Range printTitlesCol;
/*   54:     */   private Sheet sheet;
/*   55: 264 */   private static final PageOrientation DEFAULT_ORIENTATION = PageOrientation.PORTRAIT;
/*   56: 266 */   private static final PageOrder DEFAULT_ORDER = PageOrder.RIGHT_THEN_DOWN;
/*   57: 268 */   private static final PaperSize DEFAULT_PAPER_SIZE = PaperSize.A4;
/*   58:     */   private static final double DEFAULT_HEADER_MARGIN = 0.5D;
/*   59:     */   private static final double DEFAULT_FOOTER_MARGIN = 0.5D;
/*   60:     */   private static final int DEFAULT_PRINT_RESOLUTION = 300;
/*   61:     */   private static final double DEFAULT_WIDTH_MARGIN = 0.75D;
/*   62:     */   private static final double DEFAULT_HEIGHT_MARGIN = 1.0D;
/*   63:     */   private static final int DEFAULT_DEFAULT_COLUMN_WIDTH = 8;
/*   64:     */   private static final int DEFAULT_ZOOM_FACTOR = 100;
/*   65:     */   private static final int DEFAULT_NORMAL_MAGNIFICATION = 100;
/*   66:     */   private static final int DEFAULT_PAGE_BREAK_PREVIEW_MAGNIFICATION = 60;
/*   67:     */   public static final int DEFAULT_DEFAULT_ROW_HEIGHT = 255;
/*   68:     */   
/*   69:     */   public SheetSettings(Sheet s)
/*   70:     */   {
/*   71: 291 */     this.sheet = s;
/*   72: 292 */     this.orientation = DEFAULT_ORIENTATION;
/*   73: 293 */     this.pageOrder = DEFAULT_ORDER;
/*   74: 294 */     this.paperSize = DEFAULT_PAPER_SIZE;
/*   75: 295 */     this.sheetProtected = false;
/*   76: 296 */     this.hidden = false;
/*   77: 297 */     this.selected = false;
/*   78: 298 */     this.headerMargin = 0.5D;
/*   79: 299 */     this.footerMargin = 0.5D;
/*   80: 300 */     this.horizontalPrintResolution = 300;
/*   81: 301 */     this.verticalPrintResolution = 300;
/*   82: 302 */     this.leftMargin = 0.75D;
/*   83: 303 */     this.rightMargin = 0.75D;
/*   84: 304 */     this.topMargin = 1.0D;
/*   85: 305 */     this.bottomMargin = 1.0D;
/*   86: 306 */     this.fitToPages = false;
/*   87: 307 */     this.showGridLines = true;
/*   88: 308 */     this.printGridLines = false;
/*   89: 309 */     this.printHeaders = false;
/*   90: 310 */     this.pageBreakPreviewMode = false;
/*   91: 311 */     this.displayZeroValues = true;
/*   92: 312 */     this.defaultColumnWidth = 8;
/*   93: 313 */     this.defaultRowHeight = 255;
/*   94: 314 */     this.zoomFactor = 100;
/*   95: 315 */     this.pageBreakPreviewMagnification = 60;
/*   96: 316 */     this.normalMagnification = 100;
/*   97: 317 */     this.horizontalFreeze = 0;
/*   98: 318 */     this.verticalFreeze = 0;
/*   99: 319 */     this.copies = 1;
/*  100: 320 */     this.header = new HeaderFooter();
/*  101: 321 */     this.footer = new HeaderFooter();
/*  102: 322 */     this.automaticFormulaCalculation = true;
/*  103: 323 */     this.recalculateFormulasBeforeSave = true;
/*  104:     */   }
/*  105:     */   
/*  106:     */   public SheetSettings(SheetSettings copy, Sheet s)
/*  107:     */   {
/*  108: 332 */     Assert.verify(copy != null);
/*  109:     */     
/*  110: 334 */     this.sheet = s;
/*  111: 335 */     this.orientation = copy.orientation;
/*  112: 336 */     this.pageOrder = copy.pageOrder;
/*  113: 337 */     this.paperSize = copy.paperSize;
/*  114: 338 */     this.sheetProtected = copy.sheetProtected;
/*  115: 339 */     this.hidden = copy.hidden;
/*  116: 340 */     this.selected = false;
/*  117: 341 */     this.headerMargin = copy.headerMargin;
/*  118: 342 */     this.footerMargin = copy.footerMargin;
/*  119: 343 */     this.scaleFactor = copy.scaleFactor;
/*  120: 344 */     this.pageStart = copy.pageStart;
/*  121: 345 */     this.fitWidth = copy.fitWidth;
/*  122: 346 */     this.fitHeight = copy.fitHeight;
/*  123: 347 */     this.horizontalPrintResolution = copy.horizontalPrintResolution;
/*  124: 348 */     this.verticalPrintResolution = copy.verticalPrintResolution;
/*  125: 349 */     this.leftMargin = copy.leftMargin;
/*  126: 350 */     this.rightMargin = copy.rightMargin;
/*  127: 351 */     this.topMargin = copy.topMargin;
/*  128: 352 */     this.bottomMargin = copy.bottomMargin;
/*  129: 353 */     this.fitToPages = copy.fitToPages;
/*  130: 354 */     this.password = copy.password;
/*  131: 355 */     this.passwordHash = copy.passwordHash;
/*  132: 356 */     this.defaultColumnWidth = copy.defaultColumnWidth;
/*  133: 357 */     this.defaultRowHeight = copy.defaultRowHeight;
/*  134: 358 */     this.zoomFactor = copy.zoomFactor;
/*  135: 359 */     this.pageBreakPreviewMagnification = copy.pageBreakPreviewMagnification;
/*  136: 360 */     this.normalMagnification = copy.normalMagnification;
/*  137: 361 */     this.showGridLines = copy.showGridLines;
/*  138: 362 */     this.displayZeroValues = copy.displayZeroValues;
/*  139: 363 */     this.pageBreakPreviewMode = copy.pageBreakPreviewMode;
/*  140: 364 */     this.horizontalFreeze = copy.horizontalFreeze;
/*  141: 365 */     this.verticalFreeze = copy.verticalFreeze;
/*  142: 366 */     this.horizontalCentre = copy.horizontalCentre;
/*  143: 367 */     this.verticalCentre = copy.verticalCentre;
/*  144: 368 */     this.copies = copy.copies;
/*  145: 369 */     this.header = new HeaderFooter(copy.header);
/*  146: 370 */     this.footer = new HeaderFooter(copy.footer);
/*  147: 371 */     this.automaticFormulaCalculation = copy.automaticFormulaCalculation;
/*  148: 372 */     this.recalculateFormulasBeforeSave = copy.recalculateFormulasBeforeSave;
/*  149: 374 */     if (copy.printArea != null) {
/*  150: 376 */       this.printArea = new SheetRangeImpl(this.sheet, copy.getPrintArea().getTopLeft().getColumn(), copy.getPrintArea().getTopLeft().getRow(), copy.getPrintArea().getBottomRight().getColumn(), copy.getPrintArea().getBottomRight().getRow());
/*  151:     */     }
/*  152: 384 */     if (copy.printTitlesRow != null) {
/*  153: 386 */       this.printTitlesRow = new SheetRangeImpl(this.sheet, copy.getPrintTitlesRow().getTopLeft().getColumn(), copy.getPrintTitlesRow().getTopLeft().getRow(), copy.getPrintTitlesRow().getBottomRight().getColumn(), copy.getPrintTitlesRow().getBottomRight().getRow());
/*  154:     */     }
/*  155: 394 */     if (copy.printTitlesCol != null) {
/*  156: 396 */       this.printTitlesCol = new SheetRangeImpl(this.sheet, copy.getPrintTitlesCol().getTopLeft().getColumn(), copy.getPrintTitlesCol().getTopLeft().getRow(), copy.getPrintTitlesCol().getBottomRight().getColumn(), copy.getPrintTitlesCol().getBottomRight().getRow());
/*  157:     */     }
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void setOrientation(PageOrientation po)
/*  161:     */   {
/*  162: 412 */     this.orientation = po;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public PageOrientation getOrientation()
/*  166:     */   {
/*  167: 422 */     return this.orientation;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public PageOrder getPageOrder()
/*  171:     */   {
/*  172: 432 */     return this.pageOrder;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public void setPageOrder(PageOrder order)
/*  176:     */   {
/*  177: 442 */     this.pageOrder = order;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public void setPaperSize(PaperSize ps)
/*  181:     */   {
/*  182: 452 */     this.paperSize = ps;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public PaperSize getPaperSize()
/*  186:     */   {
/*  187: 462 */     return this.paperSize;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public boolean isProtected()
/*  191:     */   {
/*  192: 472 */     return this.sheetProtected;
/*  193:     */   }
/*  194:     */   
/*  195:     */   public void setProtected(boolean p)
/*  196:     */   {
/*  197: 482 */     this.sheetProtected = p;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public void setHeaderMargin(double d)
/*  201:     */   {
/*  202: 492 */     this.headerMargin = d;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public double getHeaderMargin()
/*  206:     */   {
/*  207: 502 */     return this.headerMargin;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public void setFooterMargin(double d)
/*  211:     */   {
/*  212: 512 */     this.footerMargin = d;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public double getFooterMargin()
/*  216:     */   {
/*  217: 522 */     return this.footerMargin;
/*  218:     */   }
/*  219:     */   
/*  220:     */   public void setHidden(boolean h)
/*  221:     */   {
/*  222: 532 */     this.hidden = h;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public boolean isHidden()
/*  226:     */   {
/*  227: 542 */     return this.hidden;
/*  228:     */   }
/*  229:     */   
/*  230:     */   /**
/*  231:     */    * @deprecated
/*  232:     */    */
/*  233:     */   public void setSelected()
/*  234:     */   {
/*  235: 552 */     setSelected(true);
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void setSelected(boolean s)
/*  239:     */   {
/*  240: 562 */     this.selected = s;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public boolean isSelected()
/*  244:     */   {
/*  245: 572 */     return this.selected;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public void setScaleFactor(int sf)
/*  249:     */   {
/*  250: 584 */     this.scaleFactor = sf;
/*  251: 585 */     this.fitToPages = false;
/*  252:     */   }
/*  253:     */   
/*  254:     */   public int getScaleFactor()
/*  255:     */   {
/*  256: 595 */     return this.scaleFactor;
/*  257:     */   }
/*  258:     */   
/*  259:     */   public void setPageStart(int ps)
/*  260:     */   {
/*  261: 605 */     this.pageStart = ps;
/*  262:     */   }
/*  263:     */   
/*  264:     */   public int getPageStart()
/*  265:     */   {
/*  266: 615 */     return this.pageStart;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public void setFitWidth(int fw)
/*  270:     */   {
/*  271: 626 */     this.fitWidth = fw;
/*  272: 627 */     this.fitToPages = true;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public int getFitWidth()
/*  276:     */   {
/*  277: 637 */     return this.fitWidth;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void setFitHeight(int fh)
/*  281:     */   {
/*  282: 647 */     this.fitHeight = fh;
/*  283: 648 */     this.fitToPages = true;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public int getFitHeight()
/*  287:     */   {
/*  288: 658 */     return this.fitHeight;
/*  289:     */   }
/*  290:     */   
/*  291:     */   public void setHorizontalPrintResolution(int hpw)
/*  292:     */   {
/*  293: 668 */     this.horizontalPrintResolution = hpw;
/*  294:     */   }
/*  295:     */   
/*  296:     */   public int getHorizontalPrintResolution()
/*  297:     */   {
/*  298: 678 */     return this.horizontalPrintResolution;
/*  299:     */   }
/*  300:     */   
/*  301:     */   public void setVerticalPrintResolution(int vpw)
/*  302:     */   {
/*  303: 688 */     this.verticalPrintResolution = vpw;
/*  304:     */   }
/*  305:     */   
/*  306:     */   public int getVerticalPrintResolution()
/*  307:     */   {
/*  308: 698 */     return this.verticalPrintResolution;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public void setRightMargin(double m)
/*  312:     */   {
/*  313: 708 */     this.rightMargin = m;
/*  314:     */   }
/*  315:     */   
/*  316:     */   public double getRightMargin()
/*  317:     */   {
/*  318: 718 */     return this.rightMargin;
/*  319:     */   }
/*  320:     */   
/*  321:     */   public void setLeftMargin(double m)
/*  322:     */   {
/*  323: 728 */     this.leftMargin = m;
/*  324:     */   }
/*  325:     */   
/*  326:     */   public double getLeftMargin()
/*  327:     */   {
/*  328: 738 */     return this.leftMargin;
/*  329:     */   }
/*  330:     */   
/*  331:     */   public void setTopMargin(double m)
/*  332:     */   {
/*  333: 748 */     this.topMargin = m;
/*  334:     */   }
/*  335:     */   
/*  336:     */   public double getTopMargin()
/*  337:     */   {
/*  338: 758 */     return this.topMargin;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public void setBottomMargin(double m)
/*  342:     */   {
/*  343: 768 */     this.bottomMargin = m;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public double getBottomMargin()
/*  347:     */   {
/*  348: 778 */     return this.bottomMargin;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public double getDefaultWidthMargin()
/*  352:     */   {
/*  353: 788 */     return 0.75D;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public double getDefaultHeightMargin()
/*  357:     */   {
/*  358: 798 */     return 1.0D;
/*  359:     */   }
/*  360:     */   
/*  361:     */   public boolean getFitToPages()
/*  362:     */   {
/*  363: 807 */     return this.fitToPages;
/*  364:     */   }
/*  365:     */   
/*  366:     */   public void setFitToPages(boolean b)
/*  367:     */   {
/*  368: 816 */     this.fitToPages = b;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public String getPassword()
/*  372:     */   {
/*  373: 826 */     return this.password;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void setPassword(String s)
/*  377:     */   {
/*  378: 836 */     this.password = s;
/*  379:     */   }
/*  380:     */   
/*  381:     */   public int getPasswordHash()
/*  382:     */   {
/*  383: 846 */     return this.passwordHash;
/*  384:     */   }
/*  385:     */   
/*  386:     */   public void setPasswordHash(int ph)
/*  387:     */   {
/*  388: 856 */     this.passwordHash = ph;
/*  389:     */   }
/*  390:     */   
/*  391:     */   public int getDefaultColumnWidth()
/*  392:     */   {
/*  393: 866 */     return this.defaultColumnWidth;
/*  394:     */   }
/*  395:     */   
/*  396:     */   public void setDefaultColumnWidth(int w)
/*  397:     */   {
/*  398: 876 */     this.defaultColumnWidth = w;
/*  399:     */   }
/*  400:     */   
/*  401:     */   public int getDefaultRowHeight()
/*  402:     */   {
/*  403: 886 */     return this.defaultRowHeight;
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void setDefaultRowHeight(int h)
/*  407:     */   {
/*  408: 896 */     this.defaultRowHeight = h;
/*  409:     */   }
/*  410:     */   
/*  411:     */   public int getZoomFactor()
/*  412:     */   {
/*  413: 908 */     return this.zoomFactor;
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void setZoomFactor(int zf)
/*  417:     */   {
/*  418: 920 */     this.zoomFactor = zf;
/*  419:     */   }
/*  420:     */   
/*  421:     */   public int getPageBreakPreviewMagnification()
/*  422:     */   {
/*  423: 931 */     return this.pageBreakPreviewMagnification;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void setPageBreakPreviewMagnification(int f)
/*  427:     */   {
/*  428: 942 */     this.pageBreakPreviewMagnification = f;
/*  429:     */   }
/*  430:     */   
/*  431:     */   public int getNormalMagnification()
/*  432:     */   {
/*  433: 953 */     return this.normalMagnification;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void setNormalMagnification(int f)
/*  437:     */   {
/*  438: 964 */     this.normalMagnification = f;
/*  439:     */   }
/*  440:     */   
/*  441:     */   public boolean getDisplayZeroValues()
/*  442:     */   {
/*  443: 975 */     return this.displayZeroValues;
/*  444:     */   }
/*  445:     */   
/*  446:     */   public void setDisplayZeroValues(boolean b)
/*  447:     */   {
/*  448: 985 */     this.displayZeroValues = b;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public boolean getShowGridLines()
/*  452:     */   {
/*  453: 995 */     return this.showGridLines;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void setShowGridLines(boolean b)
/*  457:     */   {
/*  458:1005 */     this.showGridLines = b;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public boolean getPageBreakPreviewMode()
/*  462:     */   {
/*  463:1015 */     return this.pageBreakPreviewMode;
/*  464:     */   }
/*  465:     */   
/*  466:     */   public void setPageBreakPreviewMode(boolean b)
/*  467:     */   {
/*  468:1025 */     this.pageBreakPreviewMode = b;
/*  469:     */   }
/*  470:     */   
/*  471:     */   public boolean getPrintGridLines()
/*  472:     */   {
/*  473:1035 */     return this.printGridLines;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public void setPrintGridLines(boolean b)
/*  477:     */   {
/*  478:1045 */     this.printGridLines = b;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public boolean getPrintHeaders()
/*  482:     */   {
/*  483:1055 */     return this.printHeaders;
/*  484:     */   }
/*  485:     */   
/*  486:     */   public void setPrintHeaders(boolean b)
/*  487:     */   {
/*  488:1065 */     this.printHeaders = b;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public int getHorizontalFreeze()
/*  492:     */   {
/*  493:1076 */     return this.horizontalFreeze;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public void setHorizontalFreeze(int row)
/*  497:     */   {
/*  498:1086 */     this.horizontalFreeze = Math.max(row, 0);
/*  499:     */   }
/*  500:     */   
/*  501:     */   public int getVerticalFreeze()
/*  502:     */   {
/*  503:1097 */     return this.verticalFreeze;
/*  504:     */   }
/*  505:     */   
/*  506:     */   public void setVerticalFreeze(int col)
/*  507:     */   {
/*  508:1107 */     this.verticalFreeze = Math.max(col, 0);
/*  509:     */   }
/*  510:     */   
/*  511:     */   public void setCopies(int c)
/*  512:     */   {
/*  513:1117 */     this.copies = c;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public int getCopies()
/*  517:     */   {
/*  518:1127 */     return this.copies;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public HeaderFooter getHeader()
/*  522:     */   {
/*  523:1137 */     return this.header;
/*  524:     */   }
/*  525:     */   
/*  526:     */   public void setHeader(HeaderFooter h)
/*  527:     */   {
/*  528:1147 */     this.header = h;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public void setFooter(HeaderFooter f)
/*  532:     */   {
/*  533:1157 */     this.footer = f;
/*  534:     */   }
/*  535:     */   
/*  536:     */   public HeaderFooter getFooter()
/*  537:     */   {
/*  538:1167 */     return this.footer;
/*  539:     */   }
/*  540:     */   
/*  541:     */   public boolean isHorizontalCentre()
/*  542:     */   {
/*  543:1177 */     return this.horizontalCentre;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void setHorizontalCentre(boolean horizCentre)
/*  547:     */   {
/*  548:1187 */     this.horizontalCentre = horizCentre;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public boolean isVerticalCentre()
/*  552:     */   {
/*  553:1197 */     return this.verticalCentre;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public void setVerticalCentre(boolean vertCentre)
/*  557:     */   {
/*  558:1207 */     this.verticalCentre = vertCentre;
/*  559:     */   }
/*  560:     */   
/*  561:     */   public void setAutomaticFormulaCalculation(boolean auto)
/*  562:     */   {
/*  563:1218 */     this.automaticFormulaCalculation = auto;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public boolean getAutomaticFormulaCalculation()
/*  567:     */   {
/*  568:1229 */     return this.automaticFormulaCalculation;
/*  569:     */   }
/*  570:     */   
/*  571:     */   public void setRecalculateFormulasBeforeSave(boolean recalc)
/*  572:     */   {
/*  573:1240 */     this.recalculateFormulasBeforeSave = recalc;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public boolean getRecalculateFormulasBeforeSave()
/*  577:     */   {
/*  578:1251 */     return this.recalculateFormulasBeforeSave;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void setPrintArea(int firstCol, int firstRow, int lastCol, int lastRow)
/*  582:     */   {
/*  583:1267 */     this.printArea = new SheetRangeImpl(this.sheet, firstCol, firstRow, lastCol, lastRow);
/*  584:     */   }
/*  585:     */   
/*  586:     */   public Range getPrintArea()
/*  587:     */   {
/*  588:1278 */     return this.printArea;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public void setPrintTitles(int firstRow, int lastRow, int firstCol, int lastCol)
/*  592:     */   {
/*  593:1294 */     setPrintTitlesRow(firstRow, lastRow);
/*  594:1295 */     setPrintTitlesCol(firstCol, lastCol);
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void setPrintTitlesRow(int firstRow, int lastRow)
/*  598:     */   {
/*  599:1307 */     this.printTitlesRow = new SheetRangeImpl(this.sheet, 0, firstRow, 255, lastRow);
/*  600:     */   }
/*  601:     */   
/*  602:     */   public void setPrintTitlesCol(int firstCol, int lastCol)
/*  603:     */   {
/*  604:1320 */     this.printTitlesCol = new SheetRangeImpl(this.sheet, firstCol, 0, lastCol, 65535);
/*  605:     */   }
/*  606:     */   
/*  607:     */   public Range getPrintTitlesRow()
/*  608:     */   {
/*  609:1331 */     return this.printTitlesRow;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public Range getPrintTitlesCol()
/*  613:     */   {
/*  614:1342 */     return this.printTitlesCol;
/*  615:     */   }
/*  616:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.SheetSettings
 * JD-Core Version:    0.7.0.1
 */