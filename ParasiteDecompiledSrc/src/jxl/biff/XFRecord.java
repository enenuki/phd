/*    1:     */ package jxl.biff;
/*    2:     */ 
/*    3:     */ import java.text.DateFormat;
/*    4:     */ import java.text.DecimalFormat;
/*    5:     */ import java.text.DecimalFormatSymbols;
/*    6:     */ import java.text.NumberFormat;
/*    7:     */ import java.text.SimpleDateFormat;
/*    8:     */ import jxl.WorkbookSettings;
/*    9:     */ import jxl.common.Assert;
/*   10:     */ import jxl.common.Logger;
/*   11:     */ import jxl.format.Alignment;
/*   12:     */ import jxl.format.Border;
/*   13:     */ import jxl.format.BorderLineStyle;
/*   14:     */ import jxl.format.CellFormat;
/*   15:     */ import jxl.format.Colour;
/*   16:     */ import jxl.format.Font;
/*   17:     */ import jxl.format.Format;
/*   18:     */ import jxl.format.Orientation;
/*   19:     */ import jxl.format.Pattern;
/*   20:     */ import jxl.format.VerticalAlignment;
/*   21:     */ import jxl.read.biff.Record;
/*   22:     */ 
/*   23:     */ public class XFRecord
/*   24:     */   extends WritableRecordData
/*   25:     */   implements CellFormat
/*   26:     */ {
/*   27:  53 */   private static Logger logger = Logger.getLogger(XFRecord.class);
/*   28:     */   public int formatIndex;
/*   29:     */   private int parentFormat;
/*   30:     */   private XFType xfFormatType;
/*   31:     */   private boolean date;
/*   32:     */   private boolean number;
/*   33:     */   private DateFormat dateFormat;
/*   34:     */   private NumberFormat numberFormat;
/*   35:     */   private byte usedAttributes;
/*   36:     */   private int fontIndex;
/*   37:     */   private boolean locked;
/*   38:     */   private boolean hidden;
/*   39:     */   private Alignment align;
/*   40:     */   private VerticalAlignment valign;
/*   41:     */   private Orientation orientation;
/*   42:     */   private boolean wrap;
/*   43:     */   private int indentation;
/*   44:     */   private boolean shrinkToFit;
/*   45:     */   private BorderLineStyle leftBorder;
/*   46:     */   private BorderLineStyle rightBorder;
/*   47:     */   private BorderLineStyle topBorder;
/*   48:     */   private BorderLineStyle bottomBorder;
/*   49:     */   private Colour leftBorderColour;
/*   50:     */   private Colour rightBorderColour;
/*   51:     */   private Colour topBorderColour;
/*   52:     */   private Colour bottomBorderColour;
/*   53:     */   private Colour backgroundColour;
/*   54:     */   private Pattern pattern;
/*   55:     */   private int options;
/*   56:     */   private int xfIndex;
/*   57:     */   private FontRecord font;
/*   58:     */   private DisplayFormat format;
/*   59:     */   private boolean initialized;
/*   60:     */   private boolean read;
/*   61:     */   private Format excelFormat;
/*   62:     */   private boolean formatInfoInitialized;
/*   63:     */   private boolean copied;
/*   64:     */   private FormattingRecords formattingRecords;
/*   65:     */   private static final int USE_FONT = 4;
/*   66:     */   private static final int USE_FORMAT = 8;
/*   67:     */   private static final int USE_ALIGNMENT = 16;
/*   68:     */   private static final int USE_BORDER = 32;
/*   69:     */   private static final int USE_BACKGROUND = 64;
/*   70:     */   private static final int USE_PROTECTION = 128;
/*   71:     */   private static final int USE_DEFAULT_VALUE = 248;
/*   72: 250 */   private static final int[] dateFormats = { 14, 15, 16, 17, 18, 19, 20, 21, 22, 45, 46, 47 };
/*   73: 267 */   private static final DateFormat[] javaDateFormats = { SimpleDateFormat.getDateInstance(3), SimpleDateFormat.getDateInstance(2), new SimpleDateFormat("d-MMM"), new SimpleDateFormat("MMM-yy"), new SimpleDateFormat("h:mm a"), new SimpleDateFormat("h:mm:ss a"), new SimpleDateFormat("H:mm"), new SimpleDateFormat("H:mm:ss"), new SimpleDateFormat("M/d/yy H:mm"), new SimpleDateFormat("mm:ss"), new SimpleDateFormat("H:mm:ss"), new SimpleDateFormat("mm:ss.S") };
/*   74: 284 */   private static int[] numberFormats = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 37, 38, 39, 40, 41, 42, 43, 44, 48 };
/*   75: 309 */   private static NumberFormat[] javaNumberFormats = { new DecimalFormat("0"), new DecimalFormat("0.00"), new DecimalFormat("#,##0"), new DecimalFormat("#,##0.00"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("0%"), new DecimalFormat("0.00%"), new DecimalFormat("0.00E00"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("#,##0;(#,##0)"), new DecimalFormat("$#,##0;($#,##0)"), new DecimalFormat("#,##0.00;(#,##0.00)"), new DecimalFormat("$#,##0.00;($#,##0.00)"), new DecimalFormat("##0.0E0") };
/*   76: 334 */   public static final BiffType biff8 = new BiffType(null);
/*   77: 335 */   public static final BiffType biff7 = new BiffType(null);
/*   78:     */   private BiffType biffType;
/*   79: 346 */   protected static final XFType cell = new XFType(null);
/*   80: 347 */   protected static final XFType style = new XFType(null);
/*   81:     */   
/*   82:     */   public XFRecord(Record t, WorkbookSettings ws, BiffType bt)
/*   83:     */   {
/*   84: 357 */     super(t);
/*   85:     */     
/*   86: 359 */     this.biffType = bt;
/*   87:     */     
/*   88: 361 */     byte[] data = getRecord().getData();
/*   89:     */     
/*   90: 363 */     this.fontIndex = IntegerHelper.getInt(data[0], data[1]);
/*   91: 364 */     this.formatIndex = IntegerHelper.getInt(data[2], data[3]);
/*   92: 365 */     this.date = false;
/*   93: 366 */     this.number = false;
/*   94: 370 */     for (int i = 0; (i < dateFormats.length) && (!this.date); i++) {
/*   95: 372 */       if (this.formatIndex == dateFormats[i])
/*   96:     */       {
/*   97: 374 */         this.date = true;
/*   98: 375 */         this.dateFormat = javaDateFormats[i];
/*   99:     */       }
/*  100:     */     }
/*  101: 380 */     for (int i = 0; (i < numberFormats.length) && (!this.number); i++) {
/*  102: 382 */       if (this.formatIndex == numberFormats[i])
/*  103:     */       {
/*  104: 384 */         this.number = true;
/*  105: 385 */         DecimalFormat df = (DecimalFormat)javaNumberFormats[i].clone();
/*  106: 386 */         DecimalFormatSymbols symbols = new DecimalFormatSymbols(ws.getLocale());
/*  107:     */         
/*  108: 388 */         df.setDecimalFormatSymbols(symbols);
/*  109: 389 */         this.numberFormat = df;
/*  110:     */       }
/*  111:     */     }
/*  112: 395 */     int cellAttributes = IntegerHelper.getInt(data[4], data[5]);
/*  113: 396 */     this.parentFormat = ((cellAttributes & 0xFFF0) >> 4);
/*  114:     */     
/*  115: 398 */     int formatType = cellAttributes & 0x4;
/*  116: 399 */     this.xfFormatType = (formatType == 0 ? cell : style);
/*  117: 400 */     this.locked = ((cellAttributes & 0x1) != 0);
/*  118: 401 */     this.hidden = ((cellAttributes & 0x2) != 0);
/*  119: 403 */     if ((this.xfFormatType == cell) && ((this.parentFormat & 0xFFF) == 4095))
/*  120:     */     {
/*  121: 407 */       this.parentFormat = 0;
/*  122: 408 */       logger.warn("Invalid parent format found - ignoring");
/*  123:     */     }
/*  124: 411 */     this.initialized = false;
/*  125: 412 */     this.read = true;
/*  126: 413 */     this.formatInfoInitialized = false;
/*  127: 414 */     this.copied = false;
/*  128:     */   }
/*  129:     */   
/*  130:     */   public XFRecord(FontRecord fnt, DisplayFormat form)
/*  131:     */   {
/*  132: 425 */     super(Type.XF);
/*  133:     */     
/*  134: 427 */     this.initialized = false;
/*  135: 428 */     this.locked = true;
/*  136: 429 */     this.hidden = false;
/*  137: 430 */     this.align = Alignment.GENERAL;
/*  138: 431 */     this.valign = VerticalAlignment.BOTTOM;
/*  139: 432 */     this.orientation = Orientation.HORIZONTAL;
/*  140: 433 */     this.wrap = false;
/*  141: 434 */     this.leftBorder = BorderLineStyle.NONE;
/*  142: 435 */     this.rightBorder = BorderLineStyle.NONE;
/*  143: 436 */     this.topBorder = BorderLineStyle.NONE;
/*  144: 437 */     this.bottomBorder = BorderLineStyle.NONE;
/*  145: 438 */     this.leftBorderColour = Colour.AUTOMATIC;
/*  146: 439 */     this.rightBorderColour = Colour.AUTOMATIC;
/*  147: 440 */     this.topBorderColour = Colour.AUTOMATIC;
/*  148: 441 */     this.bottomBorderColour = Colour.AUTOMATIC;
/*  149: 442 */     this.pattern = Pattern.NONE;
/*  150: 443 */     this.backgroundColour = Colour.DEFAULT_BACKGROUND;
/*  151: 444 */     this.indentation = 0;
/*  152: 445 */     this.shrinkToFit = false;
/*  153: 446 */     this.usedAttributes = 124;
/*  154:     */     
/*  155:     */ 
/*  156:     */ 
/*  157: 450 */     this.parentFormat = 0;
/*  158: 451 */     this.xfFormatType = null;
/*  159:     */     
/*  160: 453 */     this.font = fnt;
/*  161: 454 */     this.format = form;
/*  162: 455 */     this.biffType = biff8;
/*  163: 456 */     this.read = false;
/*  164: 457 */     this.copied = false;
/*  165: 458 */     this.formatInfoInitialized = true;
/*  166:     */     
/*  167: 460 */     Assert.verify(this.font != null);
/*  168: 461 */     Assert.verify(this.format != null);
/*  169:     */   }
/*  170:     */   
/*  171:     */   protected XFRecord(XFRecord fmt)
/*  172:     */   {
/*  173: 472 */     super(Type.XF);
/*  174:     */     
/*  175: 474 */     this.initialized = false;
/*  176: 475 */     this.locked = fmt.locked;
/*  177: 476 */     this.hidden = fmt.hidden;
/*  178: 477 */     this.align = fmt.align;
/*  179: 478 */     this.valign = fmt.valign;
/*  180: 479 */     this.orientation = fmt.orientation;
/*  181: 480 */     this.wrap = fmt.wrap;
/*  182: 481 */     this.leftBorder = fmt.leftBorder;
/*  183: 482 */     this.rightBorder = fmt.rightBorder;
/*  184: 483 */     this.topBorder = fmt.topBorder;
/*  185: 484 */     this.bottomBorder = fmt.bottomBorder;
/*  186: 485 */     this.leftBorderColour = fmt.leftBorderColour;
/*  187: 486 */     this.rightBorderColour = fmt.rightBorderColour;
/*  188: 487 */     this.topBorderColour = fmt.topBorderColour;
/*  189: 488 */     this.bottomBorderColour = fmt.bottomBorderColour;
/*  190: 489 */     this.pattern = fmt.pattern;
/*  191: 490 */     this.xfFormatType = fmt.xfFormatType;
/*  192: 491 */     this.indentation = fmt.indentation;
/*  193: 492 */     this.shrinkToFit = fmt.shrinkToFit;
/*  194: 493 */     this.parentFormat = fmt.parentFormat;
/*  195: 494 */     this.backgroundColour = fmt.backgroundColour;
/*  196:     */     
/*  197:     */ 
/*  198: 497 */     this.font = fmt.font;
/*  199: 498 */     this.format = fmt.format;
/*  200:     */     
/*  201: 500 */     this.fontIndex = fmt.fontIndex;
/*  202: 501 */     this.formatIndex = fmt.formatIndex;
/*  203:     */     
/*  204: 503 */     this.formatInfoInitialized = fmt.formatInfoInitialized;
/*  205:     */     
/*  206: 505 */     this.biffType = biff8;
/*  207: 506 */     this.read = false;
/*  208: 507 */     this.copied = true;
/*  209:     */   }
/*  210:     */   
/*  211:     */   protected XFRecord(CellFormat cellFormat)
/*  212:     */   {
/*  213: 519 */     super(Type.XF);
/*  214:     */     
/*  215: 521 */     Assert.verify(cellFormat != null);
/*  216: 522 */     Assert.verify(cellFormat instanceof XFRecord);
/*  217: 523 */     XFRecord fmt = (XFRecord)cellFormat;
/*  218: 525 */     if (!fmt.formatInfoInitialized) {
/*  219: 527 */       fmt.initializeFormatInformation();
/*  220:     */     }
/*  221: 530 */     this.locked = fmt.locked;
/*  222: 531 */     this.hidden = fmt.hidden;
/*  223: 532 */     this.align = fmt.align;
/*  224: 533 */     this.valign = fmt.valign;
/*  225: 534 */     this.orientation = fmt.orientation;
/*  226: 535 */     this.wrap = fmt.wrap;
/*  227: 536 */     this.leftBorder = fmt.leftBorder;
/*  228: 537 */     this.rightBorder = fmt.rightBorder;
/*  229: 538 */     this.topBorder = fmt.topBorder;
/*  230: 539 */     this.bottomBorder = fmt.bottomBorder;
/*  231: 540 */     this.leftBorderColour = fmt.leftBorderColour;
/*  232: 541 */     this.rightBorderColour = fmt.rightBorderColour;
/*  233: 542 */     this.topBorderColour = fmt.topBorderColour;
/*  234: 543 */     this.bottomBorderColour = fmt.bottomBorderColour;
/*  235: 544 */     this.pattern = fmt.pattern;
/*  236: 545 */     this.xfFormatType = fmt.xfFormatType;
/*  237: 546 */     this.parentFormat = fmt.parentFormat;
/*  238: 547 */     this.indentation = fmt.indentation;
/*  239: 548 */     this.shrinkToFit = fmt.shrinkToFit;
/*  240: 549 */     this.backgroundColour = fmt.backgroundColour;
/*  241:     */     
/*  242:     */ 
/*  243: 552 */     this.font = new FontRecord(fmt.getFont());
/*  244: 555 */     if (fmt.getFormat() == null)
/*  245:     */     {
/*  246: 558 */       if (fmt.format.isBuiltIn()) {
/*  247: 560 */         this.format = fmt.format;
/*  248:     */       } else {
/*  249: 565 */         this.format = new FormatRecord((FormatRecord)fmt.format);
/*  250:     */       }
/*  251:     */     }
/*  252: 568 */     else if ((fmt.getFormat() instanceof BuiltInFormat))
/*  253:     */     {
/*  254: 571 */       this.excelFormat = ((BuiltInFormat)fmt.excelFormat);
/*  255: 572 */       this.format = ((BuiltInFormat)fmt.excelFormat);
/*  256:     */     }
/*  257:     */     else
/*  258:     */     {
/*  259: 577 */       Assert.verify(fmt.formatInfoInitialized);
/*  260:     */       
/*  261:     */ 
/*  262:     */ 
/*  263: 581 */       Assert.verify(fmt.excelFormat instanceof FormatRecord);
/*  264:     */       
/*  265:     */ 
/*  266: 584 */       FormatRecord fr = new FormatRecord((FormatRecord)fmt.excelFormat);
/*  267:     */       
/*  268:     */ 
/*  269:     */ 
/*  270: 588 */       this.excelFormat = fr;
/*  271: 589 */       this.format = fr;
/*  272:     */     }
/*  273: 592 */     this.biffType = biff8;
/*  274:     */     
/*  275:     */ 
/*  276: 595 */     this.formatInfoInitialized = true;
/*  277:     */     
/*  278:     */ 
/*  279: 598 */     this.read = false;
/*  280:     */     
/*  281:     */ 
/*  282: 601 */     this.copied = false;
/*  283:     */     
/*  284:     */ 
/*  285: 604 */     this.initialized = false;
/*  286:     */   }
/*  287:     */   
/*  288:     */   public DateFormat getDateFormat()
/*  289:     */   {
/*  290: 614 */     return this.dateFormat;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public NumberFormat getNumberFormat()
/*  294:     */   {
/*  295: 624 */     return this.numberFormat;
/*  296:     */   }
/*  297:     */   
/*  298:     */   public int getFormatRecord()
/*  299:     */   {
/*  300: 634 */     return this.formatIndex;
/*  301:     */   }
/*  302:     */   
/*  303:     */   public boolean isDate()
/*  304:     */   {
/*  305: 644 */     return this.date;
/*  306:     */   }
/*  307:     */   
/*  308:     */   public boolean isNumber()
/*  309:     */   {
/*  310: 654 */     return this.number;
/*  311:     */   }
/*  312:     */   
/*  313:     */   public byte[] getData()
/*  314:     */   {
/*  315: 670 */     if (!this.formatInfoInitialized) {
/*  316: 672 */       initializeFormatInformation();
/*  317:     */     }
/*  318: 675 */     byte[] data = new byte[20];
/*  319:     */     
/*  320: 677 */     IntegerHelper.getTwoBytes(this.fontIndex, data, 0);
/*  321: 678 */     IntegerHelper.getTwoBytes(this.formatIndex, data, 2);
/*  322:     */     
/*  323:     */ 
/*  324: 681 */     int cellAttributes = 0;
/*  325: 683 */     if (getLocked()) {
/*  326: 685 */       cellAttributes |= 0x1;
/*  327:     */     }
/*  328: 688 */     if (getHidden()) {
/*  329: 690 */       cellAttributes |= 0x2;
/*  330:     */     }
/*  331: 693 */     if (this.xfFormatType == style)
/*  332:     */     {
/*  333: 695 */       cellAttributes |= 0x4;
/*  334: 696 */       this.parentFormat = 65535;
/*  335:     */     }
/*  336: 699 */     cellAttributes |= this.parentFormat << 4;
/*  337:     */     
/*  338: 701 */     IntegerHelper.getTwoBytes(cellAttributes, data, 4);
/*  339:     */     
/*  340: 703 */     int alignMask = this.align.getValue();
/*  341: 705 */     if (this.wrap) {
/*  342: 707 */       alignMask |= 0x8;
/*  343:     */     }
/*  344: 710 */     alignMask |= this.valign.getValue() << 4;
/*  345:     */     
/*  346: 712 */     alignMask |= this.orientation.getValue() << 8;
/*  347:     */     
/*  348: 714 */     IntegerHelper.getTwoBytes(alignMask, data, 6);
/*  349:     */     
/*  350: 716 */     data[9] = 16;
/*  351:     */     
/*  352:     */ 
/*  353: 719 */     int borderMask = this.leftBorder.getValue();
/*  354: 720 */     borderMask |= this.rightBorder.getValue() << 4;
/*  355: 721 */     borderMask |= this.topBorder.getValue() << 8;
/*  356: 722 */     borderMask |= this.bottomBorder.getValue() << 12;
/*  357:     */     
/*  358: 724 */     IntegerHelper.getTwoBytes(borderMask, data, 10);
/*  359: 728 */     if (borderMask != 0)
/*  360:     */     {
/*  361: 730 */       byte lc = (byte)this.leftBorderColour.getValue();
/*  362: 731 */       byte rc = (byte)this.rightBorderColour.getValue();
/*  363: 732 */       byte tc = (byte)this.topBorderColour.getValue();
/*  364: 733 */       byte bc = (byte)this.bottomBorderColour.getValue();
/*  365:     */       
/*  366: 735 */       int sideColourMask = lc & 0x7F | (rc & 0x7F) << 7;
/*  367: 736 */       int topColourMask = tc & 0x7F | (bc & 0x7F) << 7;
/*  368:     */       
/*  369: 738 */       IntegerHelper.getTwoBytes(sideColourMask, data, 12);
/*  370: 739 */       IntegerHelper.getTwoBytes(topColourMask, data, 14);
/*  371:     */     }
/*  372: 743 */     int patternVal = this.pattern.getValue() << 10;
/*  373: 744 */     IntegerHelper.getTwoBytes(patternVal, data, 16);
/*  374:     */     
/*  375:     */ 
/*  376: 747 */     int colourPaletteMask = this.backgroundColour.getValue();
/*  377: 748 */     colourPaletteMask |= 0x2000;
/*  378: 749 */     IntegerHelper.getTwoBytes(colourPaletteMask, data, 18);
/*  379:     */     
/*  380:     */ 
/*  381: 752 */     this.options |= this.indentation & 0xF;
/*  382: 754 */     if (this.shrinkToFit) {
/*  383: 756 */       this.options |= 0x10;
/*  384:     */     } else {
/*  385: 760 */       this.options &= 0xEF;
/*  386:     */     }
/*  387: 763 */     data[8] = ((byte)this.options);
/*  388: 765 */     if (this.biffType == biff8) {
/*  389: 767 */       data[9] = this.usedAttributes;
/*  390:     */     }
/*  391: 770 */     return data;
/*  392:     */   }
/*  393:     */   
/*  394:     */   protected final boolean getLocked()
/*  395:     */   {
/*  396: 780 */     return this.locked;
/*  397:     */   }
/*  398:     */   
/*  399:     */   protected final boolean getHidden()
/*  400:     */   {
/*  401: 790 */     return this.hidden;
/*  402:     */   }
/*  403:     */   
/*  404:     */   protected final void setXFLocked(boolean l)
/*  405:     */   {
/*  406: 800 */     this.locked = l;
/*  407: 801 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x80));
/*  408:     */   }
/*  409:     */   
/*  410:     */   protected final void setXFCellOptions(int opt)
/*  411:     */   {
/*  412: 811 */     this.options |= opt;
/*  413:     */   }
/*  414:     */   
/*  415:     */   protected void setXFAlignment(Alignment a)
/*  416:     */   {
/*  417: 823 */     Assert.verify(!this.initialized);
/*  418: 824 */     this.align = a;
/*  419: 825 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x10));
/*  420:     */   }
/*  421:     */   
/*  422:     */   protected void setXFIndentation(int i)
/*  423:     */   {
/*  424: 835 */     Assert.verify(!this.initialized);
/*  425: 836 */     this.indentation = i;
/*  426: 837 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x10));
/*  427:     */   }
/*  428:     */   
/*  429:     */   protected void setXFShrinkToFit(boolean s)
/*  430:     */   {
/*  431: 847 */     Assert.verify(!this.initialized);
/*  432: 848 */     this.shrinkToFit = s;
/*  433: 849 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x10));
/*  434:     */   }
/*  435:     */   
/*  436:     */   public Alignment getAlignment()
/*  437:     */   {
/*  438: 859 */     if (!this.formatInfoInitialized) {
/*  439: 861 */       initializeFormatInformation();
/*  440:     */     }
/*  441: 864 */     return this.align;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public int getIndentation()
/*  445:     */   {
/*  446: 874 */     if (!this.formatInfoInitialized) {
/*  447: 876 */       initializeFormatInformation();
/*  448:     */     }
/*  449: 879 */     return this.indentation;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public boolean isShrinkToFit()
/*  453:     */   {
/*  454: 889 */     if (!this.formatInfoInitialized) {
/*  455: 891 */       initializeFormatInformation();
/*  456:     */     }
/*  457: 894 */     return this.shrinkToFit;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public boolean isLocked()
/*  461:     */   {
/*  462: 904 */     if (!this.formatInfoInitialized) {
/*  463: 906 */       initializeFormatInformation();
/*  464:     */     }
/*  465: 909 */     return this.locked;
/*  466:     */   }
/*  467:     */   
/*  468:     */   public VerticalAlignment getVerticalAlignment()
/*  469:     */   {
/*  470: 920 */     if (!this.formatInfoInitialized) {
/*  471: 922 */       initializeFormatInformation();
/*  472:     */     }
/*  473: 925 */     return this.valign;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public Orientation getOrientation()
/*  477:     */   {
/*  478: 935 */     if (!this.formatInfoInitialized) {
/*  479: 937 */       initializeFormatInformation();
/*  480:     */     }
/*  481: 940 */     return this.orientation;
/*  482:     */   }
/*  483:     */   
/*  484:     */   protected void setXFBackground(Colour c, Pattern p)
/*  485:     */   {
/*  486: 953 */     Assert.verify(!this.initialized);
/*  487: 954 */     this.backgroundColour = c;
/*  488: 955 */     this.pattern = p;
/*  489: 956 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x40));
/*  490:     */   }
/*  491:     */   
/*  492:     */   public Colour getBackgroundColour()
/*  493:     */   {
/*  494: 966 */     if (!this.formatInfoInitialized) {
/*  495: 968 */       initializeFormatInformation();
/*  496:     */     }
/*  497: 971 */     return this.backgroundColour;
/*  498:     */   }
/*  499:     */   
/*  500:     */   public Pattern getPattern()
/*  501:     */   {
/*  502: 981 */     if (!this.formatInfoInitialized) {
/*  503: 983 */       initializeFormatInformation();
/*  504:     */     }
/*  505: 986 */     return this.pattern;
/*  506:     */   }
/*  507:     */   
/*  508:     */   protected void setXFVerticalAlignment(VerticalAlignment va)
/*  509:     */   {
/*  510: 999 */     Assert.verify(!this.initialized);
/*  511:1000 */     this.valign = va;
/*  512:1001 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x10));
/*  513:     */   }
/*  514:     */   
/*  515:     */   protected void setXFOrientation(Orientation o)
/*  516:     */   {
/*  517:1014 */     Assert.verify(!this.initialized);
/*  518:1015 */     this.orientation = o;
/*  519:1016 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x10));
/*  520:     */   }
/*  521:     */   
/*  522:     */   protected void setXFWrap(boolean w)
/*  523:     */   {
/*  524:1028 */     Assert.verify(!this.initialized);
/*  525:1029 */     this.wrap = w;
/*  526:1030 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x10));
/*  527:     */   }
/*  528:     */   
/*  529:     */   public boolean getWrap()
/*  530:     */   {
/*  531:1040 */     if (!this.formatInfoInitialized) {
/*  532:1042 */       initializeFormatInformation();
/*  533:     */     }
/*  534:1045 */     return this.wrap;
/*  535:     */   }
/*  536:     */   
/*  537:     */   protected void setXFBorder(Border b, BorderLineStyle ls, Colour c)
/*  538:     */   {
/*  539:1058 */     Assert.verify(!this.initialized);
/*  540:1060 */     if ((c == Colour.BLACK) || (c == Colour.UNKNOWN)) {
/*  541:1062 */       c = Colour.PALETTE_BLACK;
/*  542:     */     }
/*  543:1065 */     if (b == Border.LEFT)
/*  544:     */     {
/*  545:1067 */       this.leftBorder = ls;
/*  546:1068 */       this.leftBorderColour = c;
/*  547:     */     }
/*  548:1070 */     else if (b == Border.RIGHT)
/*  549:     */     {
/*  550:1072 */       this.rightBorder = ls;
/*  551:1073 */       this.rightBorderColour = c;
/*  552:     */     }
/*  553:1075 */     else if (b == Border.TOP)
/*  554:     */     {
/*  555:1077 */       this.topBorder = ls;
/*  556:1078 */       this.topBorderColour = c;
/*  557:     */     }
/*  558:1080 */     else if (b == Border.BOTTOM)
/*  559:     */     {
/*  560:1082 */       this.bottomBorder = ls;
/*  561:1083 */       this.bottomBorderColour = c;
/*  562:     */     }
/*  563:1086 */     this.usedAttributes = ((byte)(this.usedAttributes | 0x20));
/*  564:     */   }
/*  565:     */   
/*  566:     */   public BorderLineStyle getBorder(Border border)
/*  567:     */   {
/*  568:1102 */     return getBorderLine(border);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public BorderLineStyle getBorderLine(Border border)
/*  572:     */   {
/*  573:1116 */     if ((border == Border.NONE) || (border == Border.ALL)) {
/*  574:1119 */       return BorderLineStyle.NONE;
/*  575:     */     }
/*  576:1122 */     if (!this.formatInfoInitialized) {
/*  577:1124 */       initializeFormatInformation();
/*  578:     */     }
/*  579:1127 */     if (border == Border.LEFT) {
/*  580:1129 */       return this.leftBorder;
/*  581:     */     }
/*  582:1131 */     if (border == Border.RIGHT) {
/*  583:1133 */       return this.rightBorder;
/*  584:     */     }
/*  585:1135 */     if (border == Border.TOP) {
/*  586:1137 */       return this.topBorder;
/*  587:     */     }
/*  588:1139 */     if (border == Border.BOTTOM) {
/*  589:1141 */       return this.bottomBorder;
/*  590:     */     }
/*  591:1144 */     return BorderLineStyle.NONE;
/*  592:     */   }
/*  593:     */   
/*  594:     */   public Colour getBorderColour(Border border)
/*  595:     */   {
/*  596:1158 */     if ((border == Border.NONE) || (border == Border.ALL)) {
/*  597:1161 */       return Colour.PALETTE_BLACK;
/*  598:     */     }
/*  599:1164 */     if (!this.formatInfoInitialized) {
/*  600:1166 */       initializeFormatInformation();
/*  601:     */     }
/*  602:1169 */     if (border == Border.LEFT) {
/*  603:1171 */       return this.leftBorderColour;
/*  604:     */     }
/*  605:1173 */     if (border == Border.RIGHT) {
/*  606:1175 */       return this.rightBorderColour;
/*  607:     */     }
/*  608:1177 */     if (border == Border.TOP) {
/*  609:1179 */       return this.topBorderColour;
/*  610:     */     }
/*  611:1181 */     if (border == Border.BOTTOM) {
/*  612:1183 */       return this.bottomBorderColour;
/*  613:     */     }
/*  614:1186 */     return Colour.BLACK;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public final boolean hasBorders()
/*  618:     */   {
/*  619:1198 */     if (!this.formatInfoInitialized) {
/*  620:1200 */       initializeFormatInformation();
/*  621:     */     }
/*  622:1203 */     if ((this.leftBorder == BorderLineStyle.NONE) && (this.rightBorder == BorderLineStyle.NONE) && (this.topBorder == BorderLineStyle.NONE) && (this.bottomBorder == BorderLineStyle.NONE)) {
/*  623:1208 */       return false;
/*  624:     */     }
/*  625:1211 */     return true;
/*  626:     */   }
/*  627:     */   
/*  628:     */   public final void initialize(int pos, FormattingRecords fr, Fonts fonts)
/*  629:     */     throws NumFormatRecordsException
/*  630:     */   {
/*  631:1227 */     this.xfIndex = pos;
/*  632:1228 */     this.formattingRecords = fr;
/*  633:1234 */     if ((this.read) || (this.copied))
/*  634:     */     {
/*  635:1236 */       this.initialized = true;
/*  636:1237 */       return;
/*  637:     */     }
/*  638:1240 */     if (!this.font.isInitialized()) {
/*  639:1242 */       fonts.addFont(this.font);
/*  640:     */     }
/*  641:1245 */     if (!this.format.isInitialized()) {
/*  642:1247 */       fr.addFormat(this.format);
/*  643:     */     }
/*  644:1250 */     this.fontIndex = this.font.getFontIndex();
/*  645:1251 */     this.formatIndex = this.format.getFormatIndex();
/*  646:     */     
/*  647:1253 */     this.initialized = true;
/*  648:     */   }
/*  649:     */   
/*  650:     */   public final void uninitialize()
/*  651:     */   {
/*  652:1264 */     if (this.initialized == true) {
/*  653:1266 */       logger.warn("A default format has been initialized");
/*  654:     */     }
/*  655:1268 */     this.initialized = false;
/*  656:     */   }
/*  657:     */   
/*  658:     */   final void setXFIndex(int xfi)
/*  659:     */   {
/*  660:1279 */     this.xfIndex = xfi;
/*  661:     */   }
/*  662:     */   
/*  663:     */   public final int getXFIndex()
/*  664:     */   {
/*  665:1289 */     return this.xfIndex;
/*  666:     */   }
/*  667:     */   
/*  668:     */   public final boolean isInitialized()
/*  669:     */   {
/*  670:1299 */     return this.initialized;
/*  671:     */   }
/*  672:     */   
/*  673:     */   public final boolean isRead()
/*  674:     */   {
/*  675:1311 */     return this.read;
/*  676:     */   }
/*  677:     */   
/*  678:     */   public Format getFormat()
/*  679:     */   {
/*  680:1321 */     if (!this.formatInfoInitialized) {
/*  681:1323 */       initializeFormatInformation();
/*  682:     */     }
/*  683:1325 */     return this.excelFormat;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public Font getFont()
/*  687:     */   {
/*  688:1335 */     if (!this.formatInfoInitialized) {
/*  689:1337 */       initializeFormatInformation();
/*  690:     */     }
/*  691:1339 */     return this.font;
/*  692:     */   }
/*  693:     */   
/*  694:     */   private void initializeFormatInformation()
/*  695:     */   {
/*  696:1348 */     if ((this.formatIndex < BuiltInFormat.builtIns.length) && (BuiltInFormat.builtIns[this.formatIndex] != null)) {
/*  697:1351 */       this.excelFormat = BuiltInFormat.builtIns[this.formatIndex];
/*  698:     */     } else {
/*  699:1355 */       this.excelFormat = this.formattingRecords.getFormatRecord(this.formatIndex);
/*  700:     */     }
/*  701:1359 */     this.font = this.formattingRecords.getFonts().getFont(this.fontIndex);
/*  702:     */     
/*  703:     */ 
/*  704:1362 */     byte[] data = getRecord().getData();
/*  705:     */     
/*  706:     */ 
/*  707:1365 */     int cellAttributes = IntegerHelper.getInt(data[4], data[5]);
/*  708:1366 */     this.parentFormat = ((cellAttributes & 0xFFF0) >> 4);
/*  709:1367 */     int formatType = cellAttributes & 0x4;
/*  710:1368 */     this.xfFormatType = (formatType == 0 ? cell : style);
/*  711:1369 */     this.locked = ((cellAttributes & 0x1) != 0);
/*  712:1370 */     this.hidden = ((cellAttributes & 0x2) != 0);
/*  713:1372 */     if ((this.xfFormatType == cell) && ((this.parentFormat & 0xFFF) == 4095))
/*  714:     */     {
/*  715:1376 */       this.parentFormat = 0;
/*  716:1377 */       logger.warn("Invalid parent format found - ignoring");
/*  717:     */     }
/*  718:1381 */     int alignMask = IntegerHelper.getInt(data[6], data[7]);
/*  719:1384 */     if ((alignMask & 0x8) != 0) {
/*  720:1386 */       this.wrap = true;
/*  721:     */     }
/*  722:1390 */     this.align = Alignment.getAlignment(alignMask & 0x7);
/*  723:     */     
/*  724:     */ 
/*  725:1393 */     this.valign = VerticalAlignment.getAlignment(alignMask >> 4 & 0x7);
/*  726:     */     
/*  727:     */ 
/*  728:1396 */     this.orientation = Orientation.getOrientation(alignMask >> 8 & 0xFF);
/*  729:     */     
/*  730:1398 */     int attr = IntegerHelper.getInt(data[8], data[9]);
/*  731:     */     
/*  732:     */ 
/*  733:1401 */     this.indentation = (attr & 0xF);
/*  734:     */     
/*  735:     */ 
/*  736:1404 */     this.shrinkToFit = ((attr & 0x10) != 0);
/*  737:1407 */     if (this.biffType == biff8) {
/*  738:1409 */       this.usedAttributes = data[9];
/*  739:     */     }
/*  740:1413 */     int borderMask = IntegerHelper.getInt(data[10], data[11]);
/*  741:     */     
/*  742:1415 */     this.leftBorder = BorderLineStyle.getStyle(borderMask & 0x7);
/*  743:1416 */     this.rightBorder = BorderLineStyle.getStyle(borderMask >> 4 & 0x7);
/*  744:1417 */     this.topBorder = BorderLineStyle.getStyle(borderMask >> 8 & 0x7);
/*  745:1418 */     this.bottomBorder = BorderLineStyle.getStyle(borderMask >> 12 & 0x7);
/*  746:     */     
/*  747:1420 */     int borderColourMask = IntegerHelper.getInt(data[12], data[13]);
/*  748:     */     
/*  749:1422 */     this.leftBorderColour = Colour.getInternalColour(borderColourMask & 0x7F);
/*  750:1423 */     this.rightBorderColour = Colour.getInternalColour((borderColourMask & 0x3F80) >> 7);
/*  751:     */     
/*  752:     */ 
/*  753:1426 */     borderColourMask = IntegerHelper.getInt(data[14], data[15]);
/*  754:1427 */     this.topBorderColour = Colour.getInternalColour(borderColourMask & 0x7F);
/*  755:1428 */     this.bottomBorderColour = Colour.getInternalColour((borderColourMask & 0x3F80) >> 7);
/*  756:1431 */     if (this.biffType == biff8)
/*  757:     */     {
/*  758:1434 */       int patternVal = IntegerHelper.getInt(data[16], data[17]);
/*  759:1435 */       patternVal &= 0xFC00;
/*  760:1436 */       patternVal >>= 10;
/*  761:1437 */       this.pattern = Pattern.getPattern(patternVal);
/*  762:     */       
/*  763:     */ 
/*  764:1440 */       int colourPaletteMask = IntegerHelper.getInt(data[18], data[19]);
/*  765:1441 */       this.backgroundColour = Colour.getInternalColour(colourPaletteMask & 0x3F);
/*  766:1443 */       if ((this.backgroundColour == Colour.UNKNOWN) || (this.backgroundColour == Colour.DEFAULT_BACKGROUND1)) {
/*  767:1446 */         this.backgroundColour = Colour.DEFAULT_BACKGROUND;
/*  768:     */       }
/*  769:     */     }
/*  770:     */     else
/*  771:     */     {
/*  772:1451 */       this.pattern = Pattern.NONE;
/*  773:1452 */       this.backgroundColour = Colour.DEFAULT_BACKGROUND;
/*  774:     */     }
/*  775:1456 */     this.formatInfoInitialized = true;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public int hashCode()
/*  779:     */   {
/*  780:1466 */     if (!this.formatInfoInitialized) {
/*  781:1468 */       initializeFormatInformation();
/*  782:     */     }
/*  783:1471 */     int hashValue = 17;
/*  784:1472 */     int oddPrimeNumber = 37;
/*  785:     */     
/*  786:     */ 
/*  787:1475 */     hashValue = oddPrimeNumber * hashValue + (this.hidden ? 1 : 0);
/*  788:1476 */     hashValue = oddPrimeNumber * hashValue + (this.locked ? 1 : 0);
/*  789:1477 */     hashValue = oddPrimeNumber * hashValue + (this.wrap ? 1 : 0);
/*  790:1478 */     hashValue = oddPrimeNumber * hashValue + (this.shrinkToFit ? 1 : 0);
/*  791:1481 */     if (this.xfFormatType == cell) {
/*  792:1483 */       hashValue = oddPrimeNumber * hashValue + 1;
/*  793:1485 */     } else if (this.xfFormatType == style) {
/*  794:1487 */       hashValue = oddPrimeNumber * hashValue + 2;
/*  795:     */     }
/*  796:1490 */     hashValue = oddPrimeNumber * hashValue + (this.align.getValue() + 1);
/*  797:1491 */     hashValue = oddPrimeNumber * hashValue + (this.valign.getValue() + 1);
/*  798:1492 */     hashValue = oddPrimeNumber * hashValue + this.orientation.getValue();
/*  799:     */     
/*  800:1494 */     hashValue ^= this.leftBorder.getDescription().hashCode();
/*  801:1495 */     hashValue ^= this.rightBorder.getDescription().hashCode();
/*  802:1496 */     hashValue ^= this.topBorder.getDescription().hashCode();
/*  803:1497 */     hashValue ^= this.bottomBorder.getDescription().hashCode();
/*  804:     */     
/*  805:1499 */     hashValue = oddPrimeNumber * hashValue + this.leftBorderColour.getValue();
/*  806:1500 */     hashValue = oddPrimeNumber * hashValue + this.rightBorderColour.getValue();
/*  807:1501 */     hashValue = oddPrimeNumber * hashValue + this.topBorderColour.getValue();
/*  808:1502 */     hashValue = oddPrimeNumber * hashValue + this.bottomBorderColour.getValue();
/*  809:1503 */     hashValue = oddPrimeNumber * hashValue + this.backgroundColour.getValue();
/*  810:1504 */     hashValue = oddPrimeNumber * hashValue + (this.pattern.getValue() + 1);
/*  811:     */     
/*  812:     */ 
/*  813:1507 */     hashValue = oddPrimeNumber * hashValue + this.usedAttributes;
/*  814:1508 */     hashValue = oddPrimeNumber * hashValue + this.parentFormat;
/*  815:1509 */     hashValue = oddPrimeNumber * hashValue + this.fontIndex;
/*  816:1510 */     hashValue = oddPrimeNumber * hashValue + this.formatIndex;
/*  817:1511 */     hashValue = oddPrimeNumber * hashValue + this.indentation;
/*  818:     */     
/*  819:1513 */     return hashValue;
/*  820:     */   }
/*  821:     */   
/*  822:     */   public boolean equals(Object o)
/*  823:     */   {
/*  824:1525 */     if (o == this) {
/*  825:1527 */       return true;
/*  826:     */     }
/*  827:1530 */     if (!(o instanceof XFRecord)) {
/*  828:1532 */       return false;
/*  829:     */     }
/*  830:1535 */     XFRecord xfr = (XFRecord)o;
/*  831:1538 */     if (!this.formatInfoInitialized) {
/*  832:1540 */       initializeFormatInformation();
/*  833:     */     }
/*  834:1543 */     if (!xfr.formatInfoInitialized) {
/*  835:1545 */       xfr.initializeFormatInformation();
/*  836:     */     }
/*  837:1548 */     if ((this.xfFormatType != xfr.xfFormatType) || (this.parentFormat != xfr.parentFormat) || (this.locked != xfr.locked) || (this.hidden != xfr.hidden) || (this.usedAttributes != xfr.usedAttributes)) {
/*  838:1554 */       return false;
/*  839:     */     }
/*  840:1557 */     if ((this.align != xfr.align) || (this.valign != xfr.valign) || (this.orientation != xfr.orientation) || (this.wrap != xfr.wrap) || (this.shrinkToFit != xfr.shrinkToFit) || (this.indentation != xfr.indentation)) {
/*  841:1564 */       return false;
/*  842:     */     }
/*  843:1567 */     if ((this.leftBorder != xfr.leftBorder) || (this.rightBorder != xfr.rightBorder) || (this.topBorder != xfr.topBorder) || (this.bottomBorder != xfr.bottomBorder)) {
/*  844:1572 */       return false;
/*  845:     */     }
/*  846:1575 */     if ((this.leftBorderColour != xfr.leftBorderColour) || (this.rightBorderColour != xfr.rightBorderColour) || (this.topBorderColour != xfr.topBorderColour) || (this.bottomBorderColour != xfr.bottomBorderColour)) {
/*  847:1580 */       return false;
/*  848:     */     }
/*  849:1583 */     if ((this.backgroundColour != xfr.backgroundColour) || (this.pattern != xfr.pattern)) {
/*  850:1586 */       return false;
/*  851:     */     }
/*  852:1589 */     if ((this.initialized) && (xfr.initialized))
/*  853:     */     {
/*  854:1596 */       if ((this.fontIndex != xfr.fontIndex) || (this.formatIndex != xfr.formatIndex)) {
/*  855:1599 */         return false;
/*  856:     */       }
/*  857:     */     }
/*  858:1605 */     else if ((!this.font.equals(xfr.font)) || (!this.format.equals(xfr.format))) {
/*  859:1608 */       return false;
/*  860:     */     }
/*  861:1612 */     return true;
/*  862:     */   }
/*  863:     */   
/*  864:     */   void setFormatIndex(int newindex)
/*  865:     */   {
/*  866:1622 */     this.formatIndex = newindex;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public int getFontIndex()
/*  870:     */   {
/*  871:1632 */     return this.fontIndex;
/*  872:     */   }
/*  873:     */   
/*  874:     */   void setFontIndex(int newindex)
/*  875:     */   {
/*  876:1643 */     this.fontIndex = newindex;
/*  877:     */   }
/*  878:     */   
/*  879:     */   protected void setXFDetails(XFType t, int pf)
/*  880:     */   {
/*  881:1653 */     this.xfFormatType = t;
/*  882:1654 */     this.parentFormat = pf;
/*  883:     */   }
/*  884:     */   
/*  885:     */   void rationalize(IndexMapping xfMapping)
/*  886:     */   {
/*  887:1663 */     this.xfIndex = xfMapping.getNewIndex(this.xfIndex);
/*  888:1665 */     if (this.xfFormatType == cell) {
/*  889:1667 */       this.parentFormat = xfMapping.getNewIndex(this.parentFormat);
/*  890:     */     }
/*  891:     */   }
/*  892:     */   
/*  893:     */   public void setFont(FontRecord f)
/*  894:     */   {
/*  895:1684 */     this.font = f;
/*  896:     */   }
/*  897:     */   
/*  898:     */   private static class XFType {}
/*  899:     */   
/*  900:     */   private static class BiffType {}
/*  901:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.XFRecord
 * JD-Core Version:    0.7.0.1
 */