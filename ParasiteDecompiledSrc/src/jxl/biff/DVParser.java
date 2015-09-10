/*    1:     */ package jxl.biff;
/*    2:     */ 
/*    3:     */ import java.text.DecimalFormat;
/*    4:     */ import java.text.MessageFormat;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.Iterator;
/*    7:     */ import jxl.WorkbookSettings;
/*    8:     */ import jxl.biff.formula.ExternalSheet;
/*    9:     */ import jxl.biff.formula.FormulaException;
/*   10:     */ import jxl.biff.formula.FormulaParser;
/*   11:     */ import jxl.biff.formula.ParseContext;
/*   12:     */ import jxl.common.Assert;
/*   13:     */ import jxl.common.Logger;
/*   14:     */ 
/*   15:     */ public class DVParser
/*   16:     */ {
/*   17:  45 */   private static Logger logger = Logger.getLogger(DVParser.class);
/*   18:     */   
/*   19:     */   public static class DVType
/*   20:     */   {
/*   21:     */     private int value;
/*   22:     */     private String desc;
/*   23:  53 */     private static DVType[] types = new DVType[0];
/*   24:     */     
/*   25:     */     DVType(int v, String d)
/*   26:     */     {
/*   27:  57 */       this.value = v;
/*   28:  58 */       this.desc = d;
/*   29:  59 */       DVType[] oldtypes = types;
/*   30:  60 */       types = new DVType[oldtypes.length + 1];
/*   31:  61 */       System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
/*   32:  62 */       types[oldtypes.length] = this;
/*   33:     */     }
/*   34:     */     
/*   35:     */     static DVType getType(int v)
/*   36:     */     {
/*   37:  67 */       DVType found = null;
/*   38:  68 */       for (int i = 0; (i < types.length) && (found == null); i++) {
/*   39:  70 */         if (types[i].value == v) {
/*   40:  72 */           found = types[i];
/*   41:     */         }
/*   42:     */       }
/*   43:  75 */       return found;
/*   44:     */     }
/*   45:     */     
/*   46:     */     public int getValue()
/*   47:     */     {
/*   48:  80 */       return this.value;
/*   49:     */     }
/*   50:     */     
/*   51:     */     public String getDescription()
/*   52:     */     {
/*   53:  85 */       return this.desc;
/*   54:     */     }
/*   55:     */   }
/*   56:     */   
/*   57:     */   public static class ErrorStyle
/*   58:     */   {
/*   59:     */     private int value;
/*   60:  94 */     private static ErrorStyle[] types = new ErrorStyle[0];
/*   61:     */     
/*   62:     */     ErrorStyle(int v)
/*   63:     */     {
/*   64:  98 */       this.value = v;
/*   65:  99 */       ErrorStyle[] oldtypes = types;
/*   66: 100 */       types = new ErrorStyle[oldtypes.length + 1];
/*   67: 101 */       System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
/*   68: 102 */       types[oldtypes.length] = this;
/*   69:     */     }
/*   70:     */     
/*   71:     */     static ErrorStyle getErrorStyle(int v)
/*   72:     */     {
/*   73: 107 */       ErrorStyle found = null;
/*   74: 108 */       for (int i = 0; (i < types.length) && (found == null); i++) {
/*   75: 110 */         if (types[i].value == v) {
/*   76: 112 */           found = types[i];
/*   77:     */         }
/*   78:     */       }
/*   79: 115 */       return found;
/*   80:     */     }
/*   81:     */     
/*   82:     */     public int getValue()
/*   83:     */     {
/*   84: 120 */       return this.value;
/*   85:     */     }
/*   86:     */   }
/*   87:     */   
/*   88:     */   public static class Condition
/*   89:     */   {
/*   90:     */     private int value;
/*   91:     */     private MessageFormat format;
/*   92: 130 */     private static Condition[] types = new Condition[0];
/*   93:     */     
/*   94:     */     Condition(int v, String pattern)
/*   95:     */     {
/*   96: 134 */       this.value = v;
/*   97: 135 */       this.format = new MessageFormat(pattern);
/*   98: 136 */       Condition[] oldtypes = types;
/*   99: 137 */       types = new Condition[oldtypes.length + 1];
/*  100: 138 */       System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
/*  101: 139 */       types[oldtypes.length] = this;
/*  102:     */     }
/*  103:     */     
/*  104:     */     static Condition getCondition(int v)
/*  105:     */     {
/*  106: 144 */       Condition found = null;
/*  107: 145 */       for (int i = 0; (i < types.length) && (found == null); i++) {
/*  108: 147 */         if (types[i].value == v) {
/*  109: 149 */           found = types[i];
/*  110:     */         }
/*  111:     */       }
/*  112: 152 */       return found;
/*  113:     */     }
/*  114:     */     
/*  115:     */     public int getValue()
/*  116:     */     {
/*  117: 157 */       return this.value;
/*  118:     */     }
/*  119:     */     
/*  120:     */     public String getConditionString(String s1, String s2)
/*  121:     */     {
/*  122: 162 */       return this.format.format(new String[] { s1, s2 });
/*  123:     */     }
/*  124:     */   }
/*  125:     */   
/*  126: 167 */   public static final DVType ANY = new DVType(0, "any");
/*  127: 168 */   public static final DVType INTEGER = new DVType(1, "int");
/*  128: 169 */   public static final DVType DECIMAL = new DVType(2, "dec");
/*  129: 170 */   public static final DVType LIST = new DVType(3, "list");
/*  130: 171 */   public static final DVType DATE = new DVType(4, "date");
/*  131: 172 */   public static final DVType TIME = new DVType(5, "time");
/*  132: 173 */   public static final DVType TEXT_LENGTH = new DVType(6, "strlen");
/*  133: 174 */   public static final DVType FORMULA = new DVType(7, "form");
/*  134: 177 */   public static final ErrorStyle STOP = new ErrorStyle(0);
/*  135: 178 */   public static final ErrorStyle WARNING = new ErrorStyle(1);
/*  136: 179 */   public static final ErrorStyle INFO = new ErrorStyle(2);
/*  137: 182 */   public static final Condition BETWEEN = new Condition(0, "{0} <= x <= {1}");
/*  138: 183 */   public static final Condition NOT_BETWEEN = new Condition(1, "!({0} <= x <= {1}");
/*  139: 185 */   public static final Condition EQUAL = new Condition(2, "x == {0}");
/*  140: 186 */   public static final Condition NOT_EQUAL = new Condition(3, "x != {0}");
/*  141: 187 */   public static final Condition GREATER_THAN = new Condition(4, "x > {0}");
/*  142: 188 */   public static final Condition LESS_THAN = new Condition(5, "x < {0}");
/*  143: 189 */   public static final Condition GREATER_EQUAL = new Condition(6, "x >= {0}");
/*  144: 190 */   public static final Condition LESS_EQUAL = new Condition(7, "x <= {0}");
/*  145:     */   private static final int STRING_LIST_GIVEN_MASK = 128;
/*  146:     */   private static final int EMPTY_CELLS_ALLOWED_MASK = 256;
/*  147:     */   private static final int SUPPRESS_ARROW_MASK = 512;
/*  148:     */   private static final int SHOW_PROMPT_MASK = 262144;
/*  149:     */   private static final int SHOW_ERROR_MASK = 524288;
/*  150: 200 */   private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
/*  151:     */   private static final int MAX_VALIDATION_LIST_LENGTH = 254;
/*  152:     */   private static final int MAX_ROWS = 65535;
/*  153:     */   private static final int MAX_COLUMNS = 255;
/*  154:     */   private DVType type;
/*  155:     */   private ErrorStyle errorStyle;
/*  156:     */   private Condition condition;
/*  157:     */   private boolean stringListGiven;
/*  158:     */   private boolean emptyCellsAllowed;
/*  159:     */   private boolean suppressArrow;
/*  160:     */   private boolean showPrompt;
/*  161:     */   private boolean showError;
/*  162:     */   private String promptTitle;
/*  163:     */   private String errorTitle;
/*  164:     */   private String promptText;
/*  165:     */   private String errorText;
/*  166:     */   private FormulaParser formula1;
/*  167:     */   private String formula1String;
/*  168:     */   private FormulaParser formula2;
/*  169:     */   private String formula2String;
/*  170:     */   private int column1;
/*  171:     */   private int row1;
/*  172:     */   private int column2;
/*  173:     */   private int row2;
/*  174:     */   private boolean extendedCellsValidation;
/*  175:     */   private boolean copied;
/*  176:     */   
/*  177:     */   public DVParser(byte[] data, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws)
/*  178:     */   {
/*  179: 328 */     Assert.verify(nt != null);
/*  180:     */     
/*  181: 330 */     this.copied = false;
/*  182: 331 */     int options = IntegerHelper.getInt(data[0], data[1], data[2], data[3]);
/*  183:     */     
/*  184: 333 */     int typeVal = options & 0xF;
/*  185: 334 */     this.type = DVType.getType(typeVal);
/*  186:     */     
/*  187: 336 */     int errorStyleVal = (options & 0x70) >> 4;
/*  188: 337 */     this.errorStyle = ErrorStyle.getErrorStyle(errorStyleVal);
/*  189:     */     
/*  190: 339 */     int conditionVal = (options & 0xF00000) >> 20;
/*  191: 340 */     this.condition = Condition.getCondition(conditionVal);
/*  192:     */     
/*  193: 342 */     this.stringListGiven = ((options & 0x80) != 0);
/*  194: 343 */     this.emptyCellsAllowed = ((options & 0x100) != 0);
/*  195: 344 */     this.suppressArrow = ((options & 0x200) != 0);
/*  196: 345 */     this.showPrompt = ((options & 0x40000) != 0);
/*  197: 346 */     this.showError = ((options & 0x80000) != 0);
/*  198:     */     
/*  199: 348 */     int pos = 4;
/*  200: 349 */     int length = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  201: 350 */     if ((length > 0) && (data[(pos + 2)] == 0))
/*  202:     */     {
/*  203: 352 */       this.promptTitle = StringHelper.getString(data, length, pos + 3, ws);
/*  204: 353 */       pos += length + 3;
/*  205:     */     }
/*  206: 355 */     else if (length > 0)
/*  207:     */     {
/*  208: 357 */       this.promptTitle = StringHelper.getUnicodeString(data, length, pos + 3);
/*  209: 358 */       pos += length * 2 + 3;
/*  210:     */     }
/*  211:     */     else
/*  212:     */     {
/*  213: 362 */       pos += 3;
/*  214:     */     }
/*  215: 365 */     length = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  216: 366 */     if ((length > 0) && (data[(pos + 2)] == 0))
/*  217:     */     {
/*  218: 368 */       this.errorTitle = StringHelper.getString(data, length, pos + 3, ws);
/*  219: 369 */       pos += length + 3;
/*  220:     */     }
/*  221: 371 */     else if (length > 0)
/*  222:     */     {
/*  223: 373 */       this.errorTitle = StringHelper.getUnicodeString(data, length, pos + 3);
/*  224: 374 */       pos += length * 2 + 3;
/*  225:     */     }
/*  226:     */     else
/*  227:     */     {
/*  228: 378 */       pos += 3;
/*  229:     */     }
/*  230: 381 */     length = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  231: 382 */     if ((length > 0) && (data[(pos + 2)] == 0))
/*  232:     */     {
/*  233: 384 */       this.promptText = StringHelper.getString(data, length, pos + 3, ws);
/*  234: 385 */       pos += length + 3;
/*  235:     */     }
/*  236: 387 */     else if (length > 0)
/*  237:     */     {
/*  238: 389 */       this.promptText = StringHelper.getUnicodeString(data, length, pos + 3);
/*  239: 390 */       pos += length * 2 + 3;
/*  240:     */     }
/*  241:     */     else
/*  242:     */     {
/*  243: 394 */       pos += 3;
/*  244:     */     }
/*  245: 397 */     length = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  246: 398 */     if ((length > 0) && (data[(pos + 2)] == 0))
/*  247:     */     {
/*  248: 400 */       this.errorText = StringHelper.getString(data, length, pos + 3, ws);
/*  249: 401 */       pos += length + 3;
/*  250:     */     }
/*  251: 403 */     else if (length > 0)
/*  252:     */     {
/*  253: 405 */       this.errorText = StringHelper.getUnicodeString(data, length, pos + 3);
/*  254: 406 */       pos += length * 2 + 3;
/*  255:     */     }
/*  256:     */     else
/*  257:     */     {
/*  258: 410 */       pos += 3;
/*  259:     */     }
/*  260: 413 */     int formula1Length = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  261: 414 */     pos += 4;
/*  262: 415 */     int formula1Pos = pos;
/*  263: 416 */     pos += formula1Length;
/*  264:     */     
/*  265: 418 */     int formula2Length = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  266: 419 */     pos += 4;
/*  267: 420 */     int formula2Pos = pos;
/*  268: 421 */     pos += formula2Length;
/*  269:     */     
/*  270: 423 */     pos += 2;
/*  271:     */     
/*  272: 425 */     this.row1 = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  273: 426 */     pos += 2;
/*  274:     */     
/*  275: 428 */     this.row2 = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  276: 429 */     pos += 2;
/*  277:     */     
/*  278: 431 */     this.column1 = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  279: 432 */     pos += 2;
/*  280:     */     
/*  281: 434 */     this.column2 = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  282: 435 */     pos += 2;
/*  283:     */     
/*  284: 437 */     this.extendedCellsValidation = ((this.row1 != this.row2) || (this.column1 != this.column2));
/*  285:     */     try
/*  286:     */     {
/*  287: 445 */       EmptyCell tmprt = new EmptyCell(this.column1, this.row1);
/*  288: 447 */       if (formula1Length != 0)
/*  289:     */       {
/*  290: 449 */         byte[] tokens = new byte[formula1Length];
/*  291: 450 */         System.arraycopy(data, formula1Pos, tokens, 0, formula1Length);
/*  292: 451 */         this.formula1 = new FormulaParser(tokens, tmprt, es, nt, ws, ParseContext.DATA_VALIDATION);
/*  293:     */         
/*  294: 453 */         this.formula1.parse();
/*  295:     */       }
/*  296: 456 */       if (formula2Length != 0)
/*  297:     */       {
/*  298: 458 */         byte[] tokens = new byte[formula2Length];
/*  299: 459 */         System.arraycopy(data, formula2Pos, tokens, 0, formula2Length);
/*  300: 460 */         this.formula2 = new FormulaParser(tokens, tmprt, es, nt, ws, ParseContext.DATA_VALIDATION);
/*  301:     */         
/*  302: 462 */         this.formula2.parse();
/*  303:     */       }
/*  304:     */     }
/*  305:     */     catch (FormulaException e)
/*  306:     */     {
/*  307: 467 */       logger.warn(e.getMessage() + " for cells " + CellReferenceHelper.getCellReference(this.column1, this.row1) + "-" + CellReferenceHelper.getCellReference(this.column2, this.row2));
/*  308:     */     }
/*  309:     */   }
/*  310:     */   
/*  311:     */   public DVParser(Collection strings)
/*  312:     */   {
/*  313: 478 */     this.copied = false;
/*  314: 479 */     this.type = LIST;
/*  315: 480 */     this.errorStyle = STOP;
/*  316: 481 */     this.condition = BETWEEN;
/*  317: 482 */     this.extendedCellsValidation = false;
/*  318:     */     
/*  319:     */ 
/*  320: 485 */     this.stringListGiven = true;
/*  321: 486 */     this.emptyCellsAllowed = true;
/*  322: 487 */     this.suppressArrow = false;
/*  323: 488 */     this.showPrompt = true;
/*  324: 489 */     this.showError = true;
/*  325:     */     
/*  326: 491 */     this.promptTitle = "";
/*  327: 492 */     this.errorTitle = "";
/*  328: 493 */     this.promptText = "";
/*  329: 494 */     this.errorText = "";
/*  330: 495 */     if (strings.size() == 0) {
/*  331: 497 */       logger.warn("no validation strings - ignoring");
/*  332:     */     }
/*  333: 500 */     Iterator i = strings.iterator();
/*  334: 501 */     StringBuffer formulaString = new StringBuffer();
/*  335:     */     
/*  336: 503 */     formulaString.append(i.next().toString());
/*  337: 504 */     while (i.hasNext())
/*  338:     */     {
/*  339: 506 */       formulaString.append('\000');
/*  340: 507 */       formulaString.append(' ');
/*  341: 508 */       formulaString.append(i.next().toString());
/*  342:     */     }
/*  343: 513 */     if (formulaString.length() > 254)
/*  344:     */     {
/*  345: 515 */       logger.warn("Validation list exceeds maximum number of characters - truncating");
/*  346:     */       
/*  347: 517 */       formulaString.delete(254, formulaString.length());
/*  348:     */     }
/*  349: 522 */     formulaString.insert(0, '"');
/*  350: 523 */     formulaString.append('"');
/*  351: 524 */     this.formula1String = formulaString.toString();
/*  352:     */   }
/*  353:     */   
/*  354:     */   public DVParser(String namedRange)
/*  355:     */   {
/*  356: 533 */     if (namedRange.length() == 0)
/*  357:     */     {
/*  358: 535 */       this.copied = false;
/*  359: 536 */       this.type = FORMULA;
/*  360: 537 */       this.errorStyle = STOP;
/*  361: 538 */       this.condition = EQUAL;
/*  362: 539 */       this.extendedCellsValidation = false;
/*  363:     */       
/*  364: 541 */       this.stringListGiven = false;
/*  365: 542 */       this.emptyCellsAllowed = false;
/*  366: 543 */       this.suppressArrow = false;
/*  367: 544 */       this.showPrompt = true;
/*  368: 545 */       this.showError = true;
/*  369:     */       
/*  370: 547 */       this.promptTitle = "";
/*  371: 548 */       this.errorTitle = "";
/*  372: 549 */       this.promptText = "";
/*  373: 550 */       this.errorText = "";
/*  374: 551 */       this.formula1String = "\"\"";
/*  375: 552 */       return;
/*  376:     */     }
/*  377: 555 */     this.copied = false;
/*  378: 556 */     this.type = LIST;
/*  379: 557 */     this.errorStyle = STOP;
/*  380: 558 */     this.condition = BETWEEN;
/*  381: 559 */     this.extendedCellsValidation = false;
/*  382:     */     
/*  383:     */ 
/*  384: 562 */     this.stringListGiven = false;
/*  385: 563 */     this.emptyCellsAllowed = true;
/*  386: 564 */     this.suppressArrow = false;
/*  387: 565 */     this.showPrompt = true;
/*  388: 566 */     this.showError = true;
/*  389:     */     
/*  390: 568 */     this.promptTitle = "";
/*  391: 569 */     this.errorTitle = "";
/*  392: 570 */     this.promptText = "";
/*  393: 571 */     this.errorText = "";
/*  394: 572 */     this.formula1String = namedRange;
/*  395:     */   }
/*  396:     */   
/*  397:     */   public DVParser(int c1, int r1, int c2, int r2)
/*  398:     */   {
/*  399: 580 */     this.copied = false;
/*  400: 581 */     this.type = LIST;
/*  401: 582 */     this.errorStyle = STOP;
/*  402: 583 */     this.condition = BETWEEN;
/*  403: 584 */     this.extendedCellsValidation = false;
/*  404:     */     
/*  405:     */ 
/*  406: 587 */     this.stringListGiven = false;
/*  407: 588 */     this.emptyCellsAllowed = true;
/*  408: 589 */     this.suppressArrow = false;
/*  409: 590 */     this.showPrompt = true;
/*  410: 591 */     this.showError = true;
/*  411:     */     
/*  412: 593 */     this.promptTitle = "";
/*  413: 594 */     this.errorTitle = "";
/*  414: 595 */     this.promptText = "";
/*  415: 596 */     this.errorText = "";
/*  416: 597 */     StringBuffer formulaString = new StringBuffer();
/*  417: 598 */     CellReferenceHelper.getCellReference(c1, r1, formulaString);
/*  418: 599 */     formulaString.append(':');
/*  419: 600 */     CellReferenceHelper.getCellReference(c2, r2, formulaString);
/*  420: 601 */     this.formula1String = formulaString.toString();
/*  421:     */   }
/*  422:     */   
/*  423:     */   public DVParser(double val1, double val2, Condition c)
/*  424:     */   {
/*  425: 609 */     this.copied = false;
/*  426: 610 */     this.type = DECIMAL;
/*  427: 611 */     this.errorStyle = STOP;
/*  428: 612 */     this.condition = c;
/*  429: 613 */     this.extendedCellsValidation = false;
/*  430:     */     
/*  431:     */ 
/*  432: 616 */     this.stringListGiven = false;
/*  433: 617 */     this.emptyCellsAllowed = true;
/*  434: 618 */     this.suppressArrow = false;
/*  435: 619 */     this.showPrompt = true;
/*  436: 620 */     this.showError = true;
/*  437:     */     
/*  438: 622 */     this.promptTitle = "";
/*  439: 623 */     this.errorTitle = "";
/*  440: 624 */     this.promptText = "";
/*  441: 625 */     this.errorText = "";
/*  442: 626 */     this.formula1String = DECIMAL_FORMAT.format(val1);
/*  443: 628 */     if (!Double.isNaN(val2)) {
/*  444: 630 */       this.formula2String = DECIMAL_FORMAT.format(val2);
/*  445:     */     }
/*  446:     */   }
/*  447:     */   
/*  448:     */   public DVParser(DVParser copy)
/*  449:     */   {
/*  450: 639 */     this.copied = true;
/*  451: 640 */     this.type = copy.type;
/*  452: 641 */     this.errorStyle = copy.errorStyle;
/*  453: 642 */     this.condition = copy.condition;
/*  454: 643 */     this.stringListGiven = copy.stringListGiven;
/*  455: 644 */     this.emptyCellsAllowed = copy.emptyCellsAllowed;
/*  456: 645 */     this.suppressArrow = copy.suppressArrow;
/*  457: 646 */     this.showPrompt = copy.showPrompt;
/*  458: 647 */     this.showError = copy.showError;
/*  459: 648 */     this.promptTitle = copy.promptTitle;
/*  460: 649 */     this.promptText = copy.promptText;
/*  461: 650 */     this.errorTitle = copy.errorTitle;
/*  462: 651 */     this.errorText = copy.errorText;
/*  463: 652 */     this.extendedCellsValidation = copy.extendedCellsValidation;
/*  464:     */     
/*  465: 654 */     this.row1 = copy.row1;
/*  466: 655 */     this.row2 = copy.row2;
/*  467: 656 */     this.column1 = copy.column1;
/*  468: 657 */     this.column2 = copy.column2;
/*  469: 660 */     if (copy.formula1String != null)
/*  470:     */     {
/*  471: 662 */       this.formula1String = copy.formula1String;
/*  472: 663 */       this.formula2String = copy.formula2String;
/*  473:     */     }
/*  474:     */     else
/*  475:     */     {
/*  476:     */       try
/*  477:     */       {
/*  478: 669 */         this.formula1String = copy.formula1.getFormula();
/*  479: 670 */         this.formula2String = (copy.formula2 != null ? copy.formula2.getFormula() : null);
/*  480:     */       }
/*  481:     */       catch (FormulaException e)
/*  482:     */       {
/*  483: 675 */         logger.warn("Cannot parse validation formula:  " + e.getMessage());
/*  484:     */       }
/*  485:     */     }
/*  486:     */   }
/*  487:     */   
/*  488:     */   public byte[] getData()
/*  489:     */   {
/*  490: 687 */     byte[] f1Bytes = this.formula1 != null ? this.formula1.getBytes() : new byte[0];
/*  491: 688 */     byte[] f2Bytes = this.formula2 != null ? this.formula2.getBytes() : new byte[0];
/*  492: 689 */     int dataLength = 4 + this.promptTitle.length() * 2 + 3 + this.errorTitle.length() * 2 + 3 + this.promptText.length() * 2 + 3 + this.errorText.length() * 2 + 3 + f1Bytes.length + 2 + f2Bytes.length + 2 + 4 + 10;
/*  493:     */     
/*  494:     */ 
/*  495:     */ 
/*  496:     */ 
/*  497:     */ 
/*  498:     */ 
/*  499:     */ 
/*  500:     */ 
/*  501:     */ 
/*  502:     */ 
/*  503: 700 */     byte[] data = new byte[dataLength];
/*  504:     */     
/*  505:     */ 
/*  506: 703 */     int pos = 0;
/*  507:     */     
/*  508:     */ 
/*  509: 706 */     int options = 0;
/*  510: 707 */     options |= this.type.getValue();
/*  511: 708 */     options |= this.errorStyle.getValue() << 4;
/*  512: 709 */     options |= this.condition.getValue() << 20;
/*  513: 711 */     if (this.stringListGiven) {
/*  514: 713 */       options |= 0x80;
/*  515:     */     }
/*  516: 716 */     if (this.emptyCellsAllowed) {
/*  517: 718 */       options |= 0x100;
/*  518:     */     }
/*  519: 721 */     if (this.suppressArrow) {
/*  520: 723 */       options |= 0x200;
/*  521:     */     }
/*  522: 726 */     if (this.showPrompt) {
/*  523: 728 */       options |= 0x40000;
/*  524:     */     }
/*  525: 731 */     if (this.showError) {
/*  526: 733 */       options |= 0x80000;
/*  527:     */     }
/*  528: 737 */     IntegerHelper.getFourBytes(options, data, pos);
/*  529: 738 */     pos += 4;
/*  530:     */     
/*  531: 740 */     IntegerHelper.getTwoBytes(this.promptTitle.length(), data, pos);
/*  532: 741 */     pos += 2;
/*  533:     */     
/*  534: 743 */     data[pos] = 1;
/*  535: 744 */     pos++;
/*  536:     */     
/*  537: 746 */     StringHelper.getUnicodeBytes(this.promptTitle, data, pos);
/*  538: 747 */     pos += this.promptTitle.length() * 2;
/*  539:     */     
/*  540: 749 */     IntegerHelper.getTwoBytes(this.errorTitle.length(), data, pos);
/*  541: 750 */     pos += 2;
/*  542:     */     
/*  543: 752 */     data[pos] = 1;
/*  544: 753 */     pos++;
/*  545:     */     
/*  546: 755 */     StringHelper.getUnicodeBytes(this.errorTitle, data, pos);
/*  547: 756 */     pos += this.errorTitle.length() * 2;
/*  548:     */     
/*  549: 758 */     IntegerHelper.getTwoBytes(this.promptText.length(), data, pos);
/*  550: 759 */     pos += 2;
/*  551:     */     
/*  552: 761 */     data[pos] = 1;
/*  553: 762 */     pos++;
/*  554:     */     
/*  555: 764 */     StringHelper.getUnicodeBytes(this.promptText, data, pos);
/*  556: 765 */     pos += this.promptText.length() * 2;
/*  557:     */     
/*  558: 767 */     IntegerHelper.getTwoBytes(this.errorText.length(), data, pos);
/*  559: 768 */     pos += 2;
/*  560:     */     
/*  561: 770 */     data[pos] = 1;
/*  562: 771 */     pos++;
/*  563:     */     
/*  564: 773 */     StringHelper.getUnicodeBytes(this.errorText, data, pos);
/*  565: 774 */     pos += this.errorText.length() * 2;
/*  566:     */     
/*  567:     */ 
/*  568: 777 */     IntegerHelper.getTwoBytes(f1Bytes.length, data, pos);
/*  569: 778 */     pos += 4;
/*  570:     */     
/*  571: 780 */     System.arraycopy(f1Bytes, 0, data, pos, f1Bytes.length);
/*  572: 781 */     pos += f1Bytes.length;
/*  573:     */     
/*  574:     */ 
/*  575: 784 */     IntegerHelper.getTwoBytes(f2Bytes.length, data, pos);
/*  576: 785 */     pos += 4;
/*  577:     */     
/*  578: 787 */     System.arraycopy(f2Bytes, 0, data, pos, f2Bytes.length);
/*  579: 788 */     pos += f2Bytes.length;
/*  580:     */     
/*  581:     */ 
/*  582: 791 */     IntegerHelper.getTwoBytes(1, data, pos);
/*  583: 792 */     pos += 2;
/*  584:     */     
/*  585: 794 */     IntegerHelper.getTwoBytes(this.row1, data, pos);
/*  586: 795 */     pos += 2;
/*  587:     */     
/*  588: 797 */     IntegerHelper.getTwoBytes(this.row2, data, pos);
/*  589: 798 */     pos += 2;
/*  590:     */     
/*  591: 800 */     IntegerHelper.getTwoBytes(this.column1, data, pos);
/*  592: 801 */     pos += 2;
/*  593:     */     
/*  594: 803 */     IntegerHelper.getTwoBytes(this.column2, data, pos);
/*  595: 804 */     pos += 2;
/*  596:     */     
/*  597: 806 */     return data;
/*  598:     */   }
/*  599:     */   
/*  600:     */   public void insertRow(int row)
/*  601:     */   {
/*  602: 816 */     if (this.formula1 != null) {
/*  603: 818 */       this.formula1.rowInserted(0, row, true);
/*  604:     */     }
/*  605: 821 */     if (this.formula2 != null) {
/*  606: 823 */       this.formula2.rowInserted(0, row, true);
/*  607:     */     }
/*  608: 826 */     if (this.row1 >= row) {
/*  609: 828 */       this.row1 += 1;
/*  610:     */     }
/*  611: 831 */     if ((this.row2 >= row) && (this.row2 != 65535)) {
/*  612: 833 */       this.row2 += 1;
/*  613:     */     }
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void insertColumn(int col)
/*  617:     */   {
/*  618: 844 */     if (this.formula1 != null) {
/*  619: 846 */       this.formula1.columnInserted(0, col, true);
/*  620:     */     }
/*  621: 849 */     if (this.formula2 != null) {
/*  622: 851 */       this.formula2.columnInserted(0, col, true);
/*  623:     */     }
/*  624: 854 */     if (this.column1 >= col) {
/*  625: 856 */       this.column1 += 1;
/*  626:     */     }
/*  627: 859 */     if ((this.column2 >= col) && (this.column2 != 255)) {
/*  628: 861 */       this.column2 += 1;
/*  629:     */     }
/*  630:     */   }
/*  631:     */   
/*  632:     */   public void removeRow(int row)
/*  633:     */   {
/*  634: 872 */     if (this.formula1 != null) {
/*  635: 874 */       this.formula1.rowRemoved(0, row, true);
/*  636:     */     }
/*  637: 877 */     if (this.formula2 != null) {
/*  638: 879 */       this.formula2.rowRemoved(0, row, true);
/*  639:     */     }
/*  640: 882 */     if (this.row1 > row) {
/*  641: 884 */       this.row1 -= 1;
/*  642:     */     }
/*  643: 887 */     if (this.row2 >= row) {
/*  644: 889 */       this.row2 -= 1;
/*  645:     */     }
/*  646:     */   }
/*  647:     */   
/*  648:     */   public void removeColumn(int col)
/*  649:     */   {
/*  650: 900 */     if (this.formula1 != null) {
/*  651: 902 */       this.formula1.columnRemoved(0, col, true);
/*  652:     */     }
/*  653: 905 */     if (this.formula2 != null) {
/*  654: 907 */       this.formula2.columnRemoved(0, col, true);
/*  655:     */     }
/*  656: 910 */     if (this.column1 > col) {
/*  657: 912 */       this.column1 -= 1;
/*  658:     */     }
/*  659: 915 */     if ((this.column2 >= col) && (this.column2 != 255)) {
/*  660: 917 */       this.column2 -= 1;
/*  661:     */     }
/*  662:     */   }
/*  663:     */   
/*  664:     */   public int getFirstColumn()
/*  665:     */   {
/*  666: 928 */     return this.column1;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public int getLastColumn()
/*  670:     */   {
/*  671: 938 */     return this.column2;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public int getFirstRow()
/*  675:     */   {
/*  676: 948 */     return this.row1;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public int getLastRow()
/*  680:     */   {
/*  681: 958 */     return this.row2;
/*  682:     */   }
/*  683:     */   
/*  684:     */   String getValidationFormula()
/*  685:     */     throws FormulaException
/*  686:     */   {
/*  687: 969 */     if (this.type == LIST) {
/*  688: 971 */       return this.formula1.getFormula();
/*  689:     */     }
/*  690: 974 */     String s1 = this.formula1.getFormula();
/*  691: 975 */     String s2 = this.formula2 != null ? this.formula2.getFormula() : null;
/*  692: 976 */     return this.condition.getConditionString(s1, s2) + "; x " + this.type.getDescription();
/*  693:     */   }
/*  694:     */   
/*  695:     */   public void setCell(int col, int row, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws)
/*  696:     */     throws FormulaException
/*  697:     */   {
/*  698: 992 */     if (this.extendedCellsValidation) {
/*  699: 994 */       return;
/*  700:     */     }
/*  701: 997 */     this.row1 = row;
/*  702: 998 */     this.row2 = row;
/*  703: 999 */     this.column1 = col;
/*  704:1000 */     this.column2 = col;
/*  705:     */     
/*  706:1002 */     this.formula1 = new FormulaParser(this.formula1String, es, nt, ws, ParseContext.DATA_VALIDATION);
/*  707:     */     
/*  708:     */ 
/*  709:1005 */     this.formula1.parse();
/*  710:1007 */     if (this.formula2String != null)
/*  711:     */     {
/*  712:1009 */       this.formula2 = new FormulaParser(this.formula2String, es, nt, ws, ParseContext.DATA_VALIDATION);
/*  713:     */       
/*  714:     */ 
/*  715:1012 */       this.formula2.parse();
/*  716:     */     }
/*  717:     */   }
/*  718:     */   
/*  719:     */   public void extendCellValidation(int cols, int rows)
/*  720:     */   {
/*  721:1024 */     this.row2 = (this.row1 + rows);
/*  722:1025 */     this.column2 = (this.column1 + cols);
/*  723:1026 */     this.extendedCellsValidation = true;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public boolean extendedCellsValidation()
/*  727:     */   {
/*  728:1035 */     return this.extendedCellsValidation;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public boolean copied()
/*  732:     */   {
/*  733:1040 */     return this.copied;
/*  734:     */   }
/*  735:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.DVParser
 * JD-Core Version:    0.7.0.1
 */