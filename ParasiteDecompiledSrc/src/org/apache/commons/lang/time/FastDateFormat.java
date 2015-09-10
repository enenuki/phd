/*    1:     */ package org.apache.commons.lang.time;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.text.DateFormat;
/*    6:     */ import java.text.DateFormatSymbols;
/*    7:     */ import java.text.FieldPosition;
/*    8:     */ import java.text.Format;
/*    9:     */ import java.text.ParsePosition;
/*   10:     */ import java.text.SimpleDateFormat;
/*   11:     */ import java.util.ArrayList;
/*   12:     */ import java.util.Calendar;
/*   13:     */ import java.util.Date;
/*   14:     */ import java.util.GregorianCalendar;
/*   15:     */ import java.util.HashMap;
/*   16:     */ import java.util.List;
/*   17:     */ import java.util.Locale;
/*   18:     */ import java.util.Map;
/*   19:     */ import java.util.TimeZone;
/*   20:     */ import org.apache.commons.lang.Validate;
/*   21:     */ import org.apache.commons.lang.text.StrBuilder;
/*   22:     */ 
/*   23:     */ public class FastDateFormat
/*   24:     */   extends Format
/*   25:     */ {
/*   26:     */   private static final long serialVersionUID = 1L;
/*   27:     */   public static final int FULL = 0;
/*   28:     */   public static final int LONG = 1;
/*   29:     */   public static final int MEDIUM = 2;
/*   30:     */   public static final int SHORT = 3;
/*   31:     */   private static String cDefaultPattern;
/*   32: 111 */   private static final Map cInstanceCache = new HashMap(7);
/*   33: 112 */   private static final Map cDateInstanceCache = new HashMap(7);
/*   34: 113 */   private static final Map cTimeInstanceCache = new HashMap(7);
/*   35: 114 */   private static final Map cDateTimeInstanceCache = new HashMap(7);
/*   36: 115 */   private static final Map cTimeZoneDisplayCache = new HashMap(7);
/*   37:     */   private final String mPattern;
/*   38:     */   private final TimeZone mTimeZone;
/*   39:     */   private final boolean mTimeZoneForced;
/*   40:     */   private final Locale mLocale;
/*   41:     */   private final boolean mLocaleForced;
/*   42:     */   private transient Rule[] mRules;
/*   43:     */   private transient int mMaxLengthEstimate;
/*   44:     */   
/*   45:     */   public static FastDateFormat getInstance()
/*   46:     */   {
/*   47: 154 */     return getInstance(getDefaultPattern(), null, null);
/*   48:     */   }
/*   49:     */   
/*   50:     */   public static FastDateFormat getInstance(String pattern)
/*   51:     */   {
/*   52: 167 */     return getInstance(pattern, null, null);
/*   53:     */   }
/*   54:     */   
/*   55:     */   public static FastDateFormat getInstance(String pattern, TimeZone timeZone)
/*   56:     */   {
/*   57: 182 */     return getInstance(pattern, timeZone, null);
/*   58:     */   }
/*   59:     */   
/*   60:     */   public static FastDateFormat getInstance(String pattern, Locale locale)
/*   61:     */   {
/*   62: 196 */     return getInstance(pattern, null, locale);
/*   63:     */   }
/*   64:     */   
/*   65:     */   public static synchronized FastDateFormat getInstance(String pattern, TimeZone timeZone, Locale locale)
/*   66:     */   {
/*   67: 213 */     FastDateFormat emptyFormat = new FastDateFormat(pattern, timeZone, locale);
/*   68: 214 */     FastDateFormat format = (FastDateFormat)cInstanceCache.get(emptyFormat);
/*   69: 215 */     if (format == null)
/*   70:     */     {
/*   71: 216 */       format = emptyFormat;
/*   72: 217 */       format.init();
/*   73: 218 */       cInstanceCache.put(format, format);
/*   74:     */     }
/*   75: 220 */     return format;
/*   76:     */   }
/*   77:     */   
/*   78:     */   public static FastDateFormat getDateInstance(int style)
/*   79:     */   {
/*   80: 235 */     return getDateInstance(style, null, null);
/*   81:     */   }
/*   82:     */   
/*   83:     */   public static FastDateFormat getDateInstance(int style, Locale locale)
/*   84:     */   {
/*   85: 250 */     return getDateInstance(style, null, locale);
/*   86:     */   }
/*   87:     */   
/*   88:     */   public static FastDateFormat getDateInstance(int style, TimeZone timeZone)
/*   89:     */   {
/*   90: 266 */     return getDateInstance(style, timeZone, null);
/*   91:     */   }
/*   92:     */   
/*   93:     */   public static synchronized FastDateFormat getDateInstance(int style, TimeZone timeZone, Locale locale)
/*   94:     */   {
/*   95: 281 */     Object key = new Integer(style);
/*   96: 282 */     if (timeZone != null) {
/*   97: 283 */       key = new Pair(key, timeZone);
/*   98:     */     }
/*   99: 286 */     if (locale == null) {
/*  100: 287 */       locale = Locale.getDefault();
/*  101:     */     }
/*  102: 290 */     key = new Pair(key, locale);
/*  103:     */     
/*  104: 292 */     FastDateFormat format = (FastDateFormat)cDateInstanceCache.get(key);
/*  105: 293 */     if (format == null) {
/*  106:     */       try
/*  107:     */       {
/*  108: 295 */         SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getDateInstance(style, locale);
/*  109: 296 */         String pattern = formatter.toPattern();
/*  110: 297 */         format = getInstance(pattern, timeZone, locale);
/*  111: 298 */         cDateInstanceCache.put(key, format);
/*  112:     */       }
/*  113:     */       catch (ClassCastException ex)
/*  114:     */       {
/*  115: 301 */         throw new IllegalArgumentException("No date pattern for locale: " + locale);
/*  116:     */       }
/*  117:     */     }
/*  118: 304 */     return format;
/*  119:     */   }
/*  120:     */   
/*  121:     */   public static FastDateFormat getTimeInstance(int style)
/*  122:     */   {
/*  123: 319 */     return getTimeInstance(style, null, null);
/*  124:     */   }
/*  125:     */   
/*  126:     */   public static FastDateFormat getTimeInstance(int style, Locale locale)
/*  127:     */   {
/*  128: 334 */     return getTimeInstance(style, null, locale);
/*  129:     */   }
/*  130:     */   
/*  131:     */   public static FastDateFormat getTimeInstance(int style, TimeZone timeZone)
/*  132:     */   {
/*  133: 350 */     return getTimeInstance(style, timeZone, null);
/*  134:     */   }
/*  135:     */   
/*  136:     */   public static synchronized FastDateFormat getTimeInstance(int style, TimeZone timeZone, Locale locale)
/*  137:     */   {
/*  138: 366 */     Object key = new Integer(style);
/*  139: 367 */     if (timeZone != null) {
/*  140: 368 */       key = new Pair(key, timeZone);
/*  141:     */     }
/*  142: 370 */     if (locale != null) {
/*  143: 371 */       key = new Pair(key, locale);
/*  144:     */     }
/*  145: 374 */     FastDateFormat format = (FastDateFormat)cTimeInstanceCache.get(key);
/*  146: 375 */     if (format == null)
/*  147:     */     {
/*  148: 376 */       if (locale == null) {
/*  149: 377 */         locale = Locale.getDefault();
/*  150:     */       }
/*  151:     */       try
/*  152:     */       {
/*  153: 381 */         SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getTimeInstance(style, locale);
/*  154: 382 */         String pattern = formatter.toPattern();
/*  155: 383 */         format = getInstance(pattern, timeZone, locale);
/*  156: 384 */         cTimeInstanceCache.put(key, format);
/*  157:     */       }
/*  158:     */       catch (ClassCastException ex)
/*  159:     */       {
/*  160: 387 */         throw new IllegalArgumentException("No date pattern for locale: " + locale);
/*  161:     */       }
/*  162:     */     }
/*  163: 390 */     return format;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle)
/*  167:     */   {
/*  168: 407 */     return getDateTimeInstance(dateStyle, timeStyle, null, null);
/*  169:     */   }
/*  170:     */   
/*  171:     */   public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale)
/*  172:     */   {
/*  173: 424 */     return getDateTimeInstance(dateStyle, timeStyle, null, locale);
/*  174:     */   }
/*  175:     */   
/*  176:     */   public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone)
/*  177:     */   {
/*  178: 442 */     return getDateTimeInstance(dateStyle, timeStyle, timeZone, null);
/*  179:     */   }
/*  180:     */   
/*  181:     */   public static synchronized FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone, Locale locale)
/*  182:     */   {
/*  183: 460 */     Object key = new Pair(new Integer(dateStyle), new Integer(timeStyle));
/*  184: 461 */     if (timeZone != null) {
/*  185: 462 */       key = new Pair(key, timeZone);
/*  186:     */     }
/*  187: 464 */     if (locale == null) {
/*  188: 465 */       locale = Locale.getDefault();
/*  189:     */     }
/*  190: 467 */     key = new Pair(key, locale);
/*  191:     */     
/*  192: 469 */     FastDateFormat format = (FastDateFormat)cDateTimeInstanceCache.get(key);
/*  193: 470 */     if (format == null) {
/*  194:     */       try
/*  195:     */       {
/*  196: 472 */         SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
/*  197:     */         
/*  198: 474 */         String pattern = formatter.toPattern();
/*  199: 475 */         format = getInstance(pattern, timeZone, locale);
/*  200: 476 */         cDateTimeInstanceCache.put(key, format);
/*  201:     */       }
/*  202:     */       catch (ClassCastException ex)
/*  203:     */       {
/*  204: 479 */         throw new IllegalArgumentException("No date time pattern for locale: " + locale);
/*  205:     */       }
/*  206:     */     }
/*  207: 482 */     return format;
/*  208:     */   }
/*  209:     */   
/*  210:     */   static synchronized String getTimeZoneDisplay(TimeZone tz, boolean daylight, int style, Locale locale)
/*  211:     */   {
/*  212: 497 */     Object key = new TimeZoneDisplayKey(tz, daylight, style, locale);
/*  213: 498 */     String value = (String)cTimeZoneDisplayCache.get(key);
/*  214: 499 */     if (value == null)
/*  215:     */     {
/*  216: 501 */       value = tz.getDisplayName(daylight, style, locale);
/*  217: 502 */       cTimeZoneDisplayCache.put(key, value);
/*  218:     */     }
/*  219: 504 */     return value;
/*  220:     */   }
/*  221:     */   
/*  222:     */   private static synchronized String getDefaultPattern()
/*  223:     */   {
/*  224: 513 */     if (cDefaultPattern == null) {
/*  225: 514 */       cDefaultPattern = new SimpleDateFormat().toPattern();
/*  226:     */     }
/*  227: 516 */     return cDefaultPattern;
/*  228:     */   }
/*  229:     */   
/*  230:     */   protected FastDateFormat(String pattern, TimeZone timeZone, Locale locale)
/*  231:     */   {
/*  232: 536 */     if (pattern == null) {
/*  233: 537 */       throw new IllegalArgumentException("The pattern must not be null");
/*  234:     */     }
/*  235: 539 */     this.mPattern = pattern;
/*  236:     */     
/*  237: 541 */     this.mTimeZoneForced = (timeZone != null);
/*  238: 542 */     if (timeZone == null) {
/*  239: 543 */       timeZone = TimeZone.getDefault();
/*  240:     */     }
/*  241: 545 */     this.mTimeZone = timeZone;
/*  242:     */     
/*  243: 547 */     this.mLocaleForced = (locale != null);
/*  244: 548 */     if (locale == null) {
/*  245: 549 */       locale = Locale.getDefault();
/*  246:     */     }
/*  247: 551 */     this.mLocale = locale;
/*  248:     */   }
/*  249:     */   
/*  250:     */   protected void init()
/*  251:     */   {
/*  252: 558 */     List rulesList = parsePattern();
/*  253: 559 */     this.mRules = ((Rule[])rulesList.toArray(new Rule[rulesList.size()]));
/*  254:     */     
/*  255: 561 */     int len = 0;
/*  256: 562 */     int i = this.mRules.length;
/*  257:     */     for (;;)
/*  258:     */     {
/*  259: 562 */       i--;
/*  260: 562 */       if (i < 0) {
/*  261:     */         break;
/*  262:     */       }
/*  263: 563 */       len += this.mRules[i].estimateLength();
/*  264:     */     }
/*  265: 566 */     this.mMaxLengthEstimate = len;
/*  266:     */   }
/*  267:     */   
/*  268:     */   protected List parsePattern()
/*  269:     */   {
/*  270: 578 */     DateFormatSymbols symbols = new DateFormatSymbols(this.mLocale);
/*  271: 579 */     List rules = new ArrayList();
/*  272:     */     
/*  273: 581 */     String[] ERAs = symbols.getEras();
/*  274: 582 */     String[] months = symbols.getMonths();
/*  275: 583 */     String[] shortMonths = symbols.getShortMonths();
/*  276: 584 */     String[] weekdays = symbols.getWeekdays();
/*  277: 585 */     String[] shortWeekdays = symbols.getShortWeekdays();
/*  278: 586 */     String[] AmPmStrings = symbols.getAmPmStrings();
/*  279:     */     
/*  280: 588 */     int length = this.mPattern.length();
/*  281: 589 */     int[] indexRef = new int[1];
/*  282: 591 */     for (int i = 0; i < length; i++)
/*  283:     */     {
/*  284: 592 */       indexRef[0] = i;
/*  285: 593 */       String token = parseToken(this.mPattern, indexRef);
/*  286: 594 */       i = indexRef[0];
/*  287:     */       
/*  288: 596 */       int tokenLen = token.length();
/*  289: 597 */       if (tokenLen == 0) {
/*  290:     */         break;
/*  291:     */       }
/*  292: 602 */       char c = token.charAt(0);
/*  293:     */       Rule rule;
/*  294:     */       Rule rule;
/*  295:     */       Rule rule;
/*  296:     */       Rule rule;
/*  297:     */       Rule rule;
/*  298:     */       Rule rule;
/*  299: 604 */       switch (c)
/*  300:     */       {
/*  301:     */       case 'G': 
/*  302: 606 */         rule = new TextField(0, ERAs);
/*  303: 607 */         break;
/*  304:     */       case 'y': 
/*  305: 609 */         if (tokenLen >= 4) {
/*  306: 610 */           rule = selectNumberRule(1, tokenLen);
/*  307:     */         } else {
/*  308: 612 */           rule = TwoDigitYearField.INSTANCE;
/*  309:     */         }
/*  310: 614 */         break;
/*  311:     */       case 'M': 
/*  312: 616 */         if (tokenLen >= 4)
/*  313:     */         {
/*  314: 617 */           rule = new TextField(2, months);
/*  315:     */         }
/*  316:     */         else
/*  317:     */         {
/*  318:     */           Rule rule;
/*  319: 618 */           if (tokenLen == 3)
/*  320:     */           {
/*  321: 619 */             rule = new TextField(2, shortMonths);
/*  322:     */           }
/*  323:     */           else
/*  324:     */           {
/*  325:     */             Rule rule;
/*  326: 620 */             if (tokenLen == 2) {
/*  327: 621 */               rule = TwoDigitMonthField.INSTANCE;
/*  328:     */             } else {
/*  329: 623 */               rule = UnpaddedMonthField.INSTANCE;
/*  330:     */             }
/*  331:     */           }
/*  332:     */         }
/*  333: 625 */         break;
/*  334:     */       case 'd': 
/*  335: 627 */         rule = selectNumberRule(5, tokenLen);
/*  336: 628 */         break;
/*  337:     */       case 'h': 
/*  338: 630 */         rule = new TwelveHourField(selectNumberRule(10, tokenLen));
/*  339: 631 */         break;
/*  340:     */       case 'H': 
/*  341: 633 */         rule = selectNumberRule(11, tokenLen);
/*  342: 634 */         break;
/*  343:     */       case 'm': 
/*  344: 636 */         rule = selectNumberRule(12, tokenLen);
/*  345: 637 */         break;
/*  346:     */       case 's': 
/*  347: 639 */         rule = selectNumberRule(13, tokenLen);
/*  348: 640 */         break;
/*  349:     */       case 'S': 
/*  350: 642 */         rule = selectNumberRule(14, tokenLen);
/*  351: 643 */         break;
/*  352:     */       case 'E': 
/*  353: 645 */         rule = new TextField(7, tokenLen < 4 ? shortWeekdays : weekdays);
/*  354: 646 */         break;
/*  355:     */       case 'D': 
/*  356: 648 */         rule = selectNumberRule(6, tokenLen);
/*  357: 649 */         break;
/*  358:     */       case 'F': 
/*  359: 651 */         rule = selectNumberRule(8, tokenLen);
/*  360: 652 */         break;
/*  361:     */       case 'w': 
/*  362: 654 */         rule = selectNumberRule(3, tokenLen);
/*  363: 655 */         break;
/*  364:     */       case 'W': 
/*  365: 657 */         rule = selectNumberRule(4, tokenLen);
/*  366: 658 */         break;
/*  367:     */       case 'a': 
/*  368: 660 */         rule = new TextField(9, AmPmStrings);
/*  369: 661 */         break;
/*  370:     */       case 'k': 
/*  371: 663 */         rule = new TwentyFourHourField(selectNumberRule(11, tokenLen));
/*  372: 664 */         break;
/*  373:     */       case 'K': 
/*  374: 666 */         rule = selectNumberRule(10, tokenLen);
/*  375: 667 */         break;
/*  376:     */       case 'z': 
/*  377: 669 */         if (tokenLen >= 4) {
/*  378: 670 */           rule = new TimeZoneNameRule(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 1);
/*  379:     */         } else {
/*  380: 672 */           rule = new TimeZoneNameRule(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 0);
/*  381:     */         }
/*  382: 674 */         break;
/*  383:     */       case 'Z': 
/*  384: 676 */         if (tokenLen == 1) {
/*  385: 677 */           rule = TimeZoneNumberRule.INSTANCE_NO_COLON;
/*  386:     */         } else {
/*  387: 679 */           rule = TimeZoneNumberRule.INSTANCE_COLON;
/*  388:     */         }
/*  389: 681 */         break;
/*  390:     */       case '\'': 
/*  391: 683 */         String sub = token.substring(1);
/*  392: 684 */         if (sub.length() == 1) {
/*  393: 685 */           rule = new CharacterLiteral(sub.charAt(0));
/*  394:     */         } else {
/*  395: 687 */           rule = new StringLiteral(sub);
/*  396:     */         }
/*  397: 689 */         break;
/*  398:     */       case '(': 
/*  399:     */       case ')': 
/*  400:     */       case '*': 
/*  401:     */       case '+': 
/*  402:     */       case ',': 
/*  403:     */       case '-': 
/*  404:     */       case '.': 
/*  405:     */       case '/': 
/*  406:     */       case '0': 
/*  407:     */       case '1': 
/*  408:     */       case '2': 
/*  409:     */       case '3': 
/*  410:     */       case '4': 
/*  411:     */       case '5': 
/*  412:     */       case '6': 
/*  413:     */       case '7': 
/*  414:     */       case '8': 
/*  415:     */       case '9': 
/*  416:     */       case ':': 
/*  417:     */       case ';': 
/*  418:     */       case '<': 
/*  419:     */       case '=': 
/*  420:     */       case '>': 
/*  421:     */       case '?': 
/*  422:     */       case '@': 
/*  423:     */       case 'A': 
/*  424:     */       case 'B': 
/*  425:     */       case 'C': 
/*  426:     */       case 'I': 
/*  427:     */       case 'J': 
/*  428:     */       case 'L': 
/*  429:     */       case 'N': 
/*  430:     */       case 'O': 
/*  431:     */       case 'P': 
/*  432:     */       case 'Q': 
/*  433:     */       case 'R': 
/*  434:     */       case 'T': 
/*  435:     */       case 'U': 
/*  436:     */       case 'V': 
/*  437:     */       case 'X': 
/*  438:     */       case 'Y': 
/*  439:     */       case '[': 
/*  440:     */       case '\\': 
/*  441:     */       case ']': 
/*  442:     */       case '^': 
/*  443:     */       case '_': 
/*  444:     */       case '`': 
/*  445:     */       case 'b': 
/*  446:     */       case 'c': 
/*  447:     */       case 'e': 
/*  448:     */       case 'f': 
/*  449:     */       case 'g': 
/*  450:     */       case 'i': 
/*  451:     */       case 'j': 
/*  452:     */       case 'l': 
/*  453:     */       case 'n': 
/*  454:     */       case 'o': 
/*  455:     */       case 'p': 
/*  456:     */       case 'q': 
/*  457:     */       case 'r': 
/*  458:     */       case 't': 
/*  459:     */       case 'u': 
/*  460:     */       case 'v': 
/*  461:     */       case 'x': 
/*  462:     */       default: 
/*  463: 691 */         throw new IllegalArgumentException("Illegal pattern component: " + token);
/*  464:     */       }
/*  465: 694 */       rules.add(rule);
/*  466:     */     }
/*  467: 697 */     return rules;
/*  468:     */   }
/*  469:     */   
/*  470:     */   protected String parseToken(String pattern, int[] indexRef)
/*  471:     */   {
/*  472: 708 */     StrBuilder buf = new StrBuilder();
/*  473:     */     
/*  474: 710 */     int i = indexRef[0];
/*  475: 711 */     int length = pattern.length();
/*  476:     */     
/*  477: 713 */     char c = pattern.charAt(i);
/*  478: 714 */     if (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z'))) {
/*  479: 717 */       buf.append(c);
/*  480:     */     }
/*  481: 719 */     while (i + 1 < length)
/*  482:     */     {
/*  483: 720 */       char peek = pattern.charAt(i + 1);
/*  484: 721 */       if (peek == c)
/*  485:     */       {
/*  486: 722 */         buf.append(c);
/*  487: 723 */         i++;
/*  488:     */         
/*  489:     */ 
/*  490:     */ 
/*  491: 727 */         continue;
/*  492:     */         
/*  493:     */ 
/*  494: 730 */         buf.append('\'');
/*  495:     */         
/*  496: 732 */         boolean inLiteral = false;
/*  497: 734 */         for (; i < length; i++)
/*  498:     */         {
/*  499: 735 */           c = pattern.charAt(i);
/*  500: 737 */           if (c == '\'')
/*  501:     */           {
/*  502: 738 */             if ((i + 1 < length) && (pattern.charAt(i + 1) == '\''))
/*  503:     */             {
/*  504: 740 */               i++;
/*  505: 741 */               buf.append(c);
/*  506:     */             }
/*  507:     */             else
/*  508:     */             {
/*  509: 743 */               inLiteral = !inLiteral;
/*  510:     */             }
/*  511:     */           }
/*  512:     */           else
/*  513:     */           {
/*  514: 745 */             if ((!inLiteral) && (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z'))))
/*  515:     */             {
/*  516: 747 */               i--;
/*  517: 748 */               break;
/*  518:     */             }
/*  519: 750 */             buf.append(c);
/*  520:     */           }
/*  521:     */         }
/*  522:     */       }
/*  523:     */     }
/*  524: 755 */     indexRef[0] = i;
/*  525: 756 */     return buf.toString();
/*  526:     */   }
/*  527:     */   
/*  528:     */   protected NumberRule selectNumberRule(int field, int padding)
/*  529:     */   {
/*  530: 767 */     switch (padding)
/*  531:     */     {
/*  532:     */     case 1: 
/*  533: 769 */       return new UnpaddedNumberField(field);
/*  534:     */     case 2: 
/*  535: 771 */       return new TwoDigitNumberField(field);
/*  536:     */     }
/*  537: 773 */     return new PaddedNumberField(field, padding);
/*  538:     */   }
/*  539:     */   
/*  540:     */   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
/*  541:     */   {
/*  542: 789 */     if ((obj instanceof Date)) {
/*  543: 790 */       return format((Date)obj, toAppendTo);
/*  544:     */     }
/*  545: 791 */     if ((obj instanceof Calendar)) {
/*  546: 792 */       return format((Calendar)obj, toAppendTo);
/*  547:     */     }
/*  548: 793 */     if ((obj instanceof Long)) {
/*  549: 794 */       return format(((Long)obj).longValue(), toAppendTo);
/*  550:     */     }
/*  551: 796 */     throw new IllegalArgumentException("Unknown class: " + (obj == null ? "<null>" : obj.getClass().getName()));
/*  552:     */   }
/*  553:     */   
/*  554:     */   public String format(long millis)
/*  555:     */   {
/*  556: 809 */     return format(new Date(millis));
/*  557:     */   }
/*  558:     */   
/*  559:     */   public String format(Date date)
/*  560:     */   {
/*  561: 819 */     Calendar c = new GregorianCalendar(this.mTimeZone, this.mLocale);
/*  562: 820 */     c.setTime(date);
/*  563: 821 */     return applyRules(c, new StringBuffer(this.mMaxLengthEstimate)).toString();
/*  564:     */   }
/*  565:     */   
/*  566:     */   public String format(Calendar calendar)
/*  567:     */   {
/*  568: 831 */     return format(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
/*  569:     */   }
/*  570:     */   
/*  571:     */   public StringBuffer format(long millis, StringBuffer buf)
/*  572:     */   {
/*  573: 844 */     return format(new Date(millis), buf);
/*  574:     */   }
/*  575:     */   
/*  576:     */   public StringBuffer format(Date date, StringBuffer buf)
/*  577:     */   {
/*  578: 856 */     Calendar c = new GregorianCalendar(this.mTimeZone);
/*  579: 857 */     c.setTime(date);
/*  580: 858 */     return applyRules(c, buf);
/*  581:     */   }
/*  582:     */   
/*  583:     */   public StringBuffer format(Calendar calendar, StringBuffer buf)
/*  584:     */   {
/*  585: 870 */     if (this.mTimeZoneForced)
/*  586:     */     {
/*  587: 871 */       calendar.getTime();
/*  588: 872 */       calendar = (Calendar)calendar.clone();
/*  589: 873 */       calendar.setTimeZone(this.mTimeZone);
/*  590:     */     }
/*  591: 875 */     return applyRules(calendar, buf);
/*  592:     */   }
/*  593:     */   
/*  594:     */   protected StringBuffer applyRules(Calendar calendar, StringBuffer buf)
/*  595:     */   {
/*  596: 887 */     Rule[] rules = this.mRules;
/*  597: 888 */     int len = this.mRules.length;
/*  598: 889 */     for (int i = 0; i < len; i++) {
/*  599: 890 */       rules[i].appendTo(buf, calendar);
/*  600:     */     }
/*  601: 892 */     return buf;
/*  602:     */   }
/*  603:     */   
/*  604:     */   public Object parseObject(String source, ParsePosition pos)
/*  605:     */   {
/*  606: 905 */     pos.setIndex(0);
/*  607: 906 */     pos.setErrorIndex(0);
/*  608: 907 */     return null;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public String getPattern()
/*  612:     */   {
/*  613: 918 */     return this.mPattern;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public TimeZone getTimeZone()
/*  617:     */   {
/*  618: 932 */     return this.mTimeZone;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public boolean getTimeZoneOverridesCalendar()
/*  622:     */   {
/*  623: 943 */     return this.mTimeZoneForced;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public Locale getLocale()
/*  627:     */   {
/*  628: 952 */     return this.mLocale;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public int getMaxLengthEstimate()
/*  632:     */   {
/*  633: 965 */     return this.mMaxLengthEstimate;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public boolean equals(Object obj)
/*  637:     */   {
/*  638: 977 */     if (!(obj instanceof FastDateFormat)) {
/*  639: 978 */       return false;
/*  640:     */     }
/*  641: 980 */     FastDateFormat other = (FastDateFormat)obj;
/*  642: 981 */     if (((this.mPattern == other.mPattern) || (this.mPattern.equals(other.mPattern))) && ((this.mTimeZone == other.mTimeZone) || (this.mTimeZone.equals(other.mTimeZone))) && ((this.mLocale == other.mLocale) || (this.mLocale.equals(other.mLocale))) && (this.mTimeZoneForced == other.mTimeZoneForced) && (this.mLocaleForced == other.mLocaleForced)) {
/*  643: 988 */       return true;
/*  644:     */     }
/*  645: 990 */     return false;
/*  646:     */   }
/*  647:     */   
/*  648:     */   public int hashCode()
/*  649:     */   {
/*  650: 999 */     int total = 0;
/*  651:1000 */     total += this.mPattern.hashCode();
/*  652:1001 */     total += this.mTimeZone.hashCode();
/*  653:1002 */     total += (this.mTimeZoneForced ? 1 : 0);
/*  654:1003 */     total += this.mLocale.hashCode();
/*  655:1004 */     total += (this.mLocaleForced ? 1 : 0);
/*  656:1005 */     return total;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public String toString()
/*  660:     */   {
/*  661:1014 */     return "FastDateFormat[" + this.mPattern + "]";
/*  662:     */   }
/*  663:     */   
/*  664:     */   private void readObject(ObjectInputStream in)
/*  665:     */     throws IOException, ClassNotFoundException
/*  666:     */   {
/*  667:1028 */     in.defaultReadObject();
/*  668:1029 */     init();
/*  669:     */   }
/*  670:     */   
/*  671:     */   private static abstract interface Rule
/*  672:     */   {
/*  673:     */     public abstract int estimateLength();
/*  674:     */     
/*  675:     */     public abstract void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar);
/*  676:     */   }
/*  677:     */   
/*  678:     */   private static abstract interface NumberRule
/*  679:     */     extends FastDateFormat.Rule
/*  680:     */   {
/*  681:     */     public abstract void appendTo(StringBuffer paramStringBuffer, int paramInt);
/*  682:     */   }
/*  683:     */   
/*  684:     */   private static class CharacterLiteral
/*  685:     */     implements FastDateFormat.Rule
/*  686:     */   {
/*  687:     */     private final char mValue;
/*  688:     */     
/*  689:     */     CharacterLiteral(char value)
/*  690:     */     {
/*  691:1080 */       this.mValue = value;
/*  692:     */     }
/*  693:     */     
/*  694:     */     public int estimateLength()
/*  695:     */     {
/*  696:1087 */       return 1;
/*  697:     */     }
/*  698:     */     
/*  699:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  700:     */     {
/*  701:1094 */       buffer.append(this.mValue);
/*  702:     */     }
/*  703:     */   }
/*  704:     */   
/*  705:     */   private static class StringLiteral
/*  706:     */     implements FastDateFormat.Rule
/*  707:     */   {
/*  708:     */     private final String mValue;
/*  709:     */     
/*  710:     */     StringLiteral(String value)
/*  711:     */     {
/*  712:1111 */       this.mValue = value;
/*  713:     */     }
/*  714:     */     
/*  715:     */     public int estimateLength()
/*  716:     */     {
/*  717:1118 */       return this.mValue.length();
/*  718:     */     }
/*  719:     */     
/*  720:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  721:     */     {
/*  722:1125 */       buffer.append(this.mValue);
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   private static class TextField
/*  727:     */     implements FastDateFormat.Rule
/*  728:     */   {
/*  729:     */     private final int mField;
/*  730:     */     private final String[] mValues;
/*  731:     */     
/*  732:     */     TextField(int field, String[] values)
/*  733:     */     {
/*  734:1144 */       this.mField = field;
/*  735:1145 */       this.mValues = values;
/*  736:     */     }
/*  737:     */     
/*  738:     */     public int estimateLength()
/*  739:     */     {
/*  740:1152 */       int max = 0;
/*  741:1153 */       int i = this.mValues.length;
/*  742:     */       for (;;)
/*  743:     */       {
/*  744:1153 */         i--;
/*  745:1153 */         if (i < 0) {
/*  746:     */           break;
/*  747:     */         }
/*  748:1154 */         int len = this.mValues[i].length();
/*  749:1155 */         if (len > max) {
/*  750:1156 */           max = len;
/*  751:     */         }
/*  752:     */       }
/*  753:1159 */       return max;
/*  754:     */     }
/*  755:     */     
/*  756:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  757:     */     {
/*  758:1166 */       buffer.append(this.mValues[calendar.get(this.mField)]);
/*  759:     */     }
/*  760:     */   }
/*  761:     */   
/*  762:     */   private static class UnpaddedNumberField
/*  763:     */     implements FastDateFormat.NumberRule
/*  764:     */   {
/*  765:     */     private final int mField;
/*  766:     */     
/*  767:     */     UnpaddedNumberField(int field)
/*  768:     */     {
/*  769:1182 */       this.mField = field;
/*  770:     */     }
/*  771:     */     
/*  772:     */     public int estimateLength()
/*  773:     */     {
/*  774:1189 */       return 4;
/*  775:     */     }
/*  776:     */     
/*  777:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  778:     */     {
/*  779:1196 */       appendTo(buffer, calendar.get(this.mField));
/*  780:     */     }
/*  781:     */     
/*  782:     */     public final void appendTo(StringBuffer buffer, int value)
/*  783:     */     {
/*  784:1203 */       if (value < 10)
/*  785:     */       {
/*  786:1204 */         buffer.append((char)(value + 48));
/*  787:     */       }
/*  788:1205 */       else if (value < 100)
/*  789:     */       {
/*  790:1206 */         buffer.append((char)(value / 10 + 48));
/*  791:1207 */         buffer.append((char)(value % 10 + 48));
/*  792:     */       }
/*  793:     */       else
/*  794:     */       {
/*  795:1209 */         buffer.append(Integer.toString(value));
/*  796:     */       }
/*  797:     */     }
/*  798:     */   }
/*  799:     */   
/*  800:     */   private static class UnpaddedMonthField
/*  801:     */     implements FastDateFormat.NumberRule
/*  802:     */   {
/*  803:1218 */     static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();
/*  804:     */     
/*  805:     */     public int estimateLength()
/*  806:     */     {
/*  807:1232 */       return 2;
/*  808:     */     }
/*  809:     */     
/*  810:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  811:     */     {
/*  812:1239 */       appendTo(buffer, calendar.get(2) + 1);
/*  813:     */     }
/*  814:     */     
/*  815:     */     public final void appendTo(StringBuffer buffer, int value)
/*  816:     */     {
/*  817:1246 */       if (value < 10)
/*  818:     */       {
/*  819:1247 */         buffer.append((char)(value + 48));
/*  820:     */       }
/*  821:     */       else
/*  822:     */       {
/*  823:1249 */         buffer.append((char)(value / 10 + 48));
/*  824:1250 */         buffer.append((char)(value % 10 + 48));
/*  825:     */       }
/*  826:     */     }
/*  827:     */   }
/*  828:     */   
/*  829:     */   private static class PaddedNumberField
/*  830:     */     implements FastDateFormat.NumberRule
/*  831:     */   {
/*  832:     */     private final int mField;
/*  833:     */     private final int mSize;
/*  834:     */     
/*  835:     */     PaddedNumberField(int field, int size)
/*  836:     */     {
/*  837:1269 */       if (size < 3) {
/*  838:1271 */         throw new IllegalArgumentException();
/*  839:     */       }
/*  840:1273 */       this.mField = field;
/*  841:1274 */       this.mSize = size;
/*  842:     */     }
/*  843:     */     
/*  844:     */     public int estimateLength()
/*  845:     */     {
/*  846:1281 */       return 4;
/*  847:     */     }
/*  848:     */     
/*  849:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  850:     */     {
/*  851:1288 */       appendTo(buffer, calendar.get(this.mField));
/*  852:     */     }
/*  853:     */     
/*  854:     */     public final void appendTo(StringBuffer buffer, int value)
/*  855:     */     {
/*  856:1295 */       if (value < 100)
/*  857:     */       {
/*  858:1296 */         int i = this.mSize;
/*  859:     */         for (;;)
/*  860:     */         {
/*  861:1296 */           i--;
/*  862:1296 */           if (i < 2) {
/*  863:     */             break;
/*  864:     */           }
/*  865:1297 */           buffer.append('0');
/*  866:     */         }
/*  867:1299 */         buffer.append((char)(value / 10 + 48));
/*  868:1300 */         buffer.append((char)(value % 10 + 48));
/*  869:     */       }
/*  870:     */       else
/*  871:     */       {
/*  872:     */         int digits;
/*  873:     */         int digits;
/*  874:1303 */         if (value < 1000)
/*  875:     */         {
/*  876:1304 */           digits = 3;
/*  877:     */         }
/*  878:     */         else
/*  879:     */         {
/*  880:1306 */           Validate.isTrue(value > -1, "Negative values should not be possible", value);
/*  881:1307 */           digits = Integer.toString(value).length();
/*  882:     */         }
/*  883:1309 */         int i = this.mSize;
/*  884:     */         for (;;)
/*  885:     */         {
/*  886:1309 */           i--;
/*  887:1309 */           if (i < digits) {
/*  888:     */             break;
/*  889:     */           }
/*  890:1310 */           buffer.append('0');
/*  891:     */         }
/*  892:1312 */         buffer.append(Integer.toString(value));
/*  893:     */       }
/*  894:     */     }
/*  895:     */   }
/*  896:     */   
/*  897:     */   private static class TwoDigitNumberField
/*  898:     */     implements FastDateFormat.NumberRule
/*  899:     */   {
/*  900:     */     private final int mField;
/*  901:     */     
/*  902:     */     TwoDigitNumberField(int field)
/*  903:     */     {
/*  904:1329 */       this.mField = field;
/*  905:     */     }
/*  906:     */     
/*  907:     */     public int estimateLength()
/*  908:     */     {
/*  909:1336 */       return 2;
/*  910:     */     }
/*  911:     */     
/*  912:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  913:     */     {
/*  914:1343 */       appendTo(buffer, calendar.get(this.mField));
/*  915:     */     }
/*  916:     */     
/*  917:     */     public final void appendTo(StringBuffer buffer, int value)
/*  918:     */     {
/*  919:1350 */       if (value < 100)
/*  920:     */       {
/*  921:1351 */         buffer.append((char)(value / 10 + 48));
/*  922:1352 */         buffer.append((char)(value % 10 + 48));
/*  923:     */       }
/*  924:     */       else
/*  925:     */       {
/*  926:1354 */         buffer.append(Integer.toString(value));
/*  927:     */       }
/*  928:     */     }
/*  929:     */   }
/*  930:     */   
/*  931:     */   private static class TwoDigitYearField
/*  932:     */     implements FastDateFormat.NumberRule
/*  933:     */   {
/*  934:1363 */     static final TwoDigitYearField INSTANCE = new TwoDigitYearField();
/*  935:     */     
/*  936:     */     public int estimateLength()
/*  937:     */     {
/*  938:1376 */       return 2;
/*  939:     */     }
/*  940:     */     
/*  941:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  942:     */     {
/*  943:1383 */       appendTo(buffer, calendar.get(1) % 100);
/*  944:     */     }
/*  945:     */     
/*  946:     */     public final void appendTo(StringBuffer buffer, int value)
/*  947:     */     {
/*  948:1390 */       buffer.append((char)(value / 10 + 48));
/*  949:1391 */       buffer.append((char)(value % 10 + 48));
/*  950:     */     }
/*  951:     */   }
/*  952:     */   
/*  953:     */   private static class TwoDigitMonthField
/*  954:     */     implements FastDateFormat.NumberRule
/*  955:     */   {
/*  956:1399 */     static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();
/*  957:     */     
/*  958:     */     public int estimateLength()
/*  959:     */     {
/*  960:1412 */       return 2;
/*  961:     */     }
/*  962:     */     
/*  963:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  964:     */     {
/*  965:1419 */       appendTo(buffer, calendar.get(2) + 1);
/*  966:     */     }
/*  967:     */     
/*  968:     */     public final void appendTo(StringBuffer buffer, int value)
/*  969:     */     {
/*  970:1426 */       buffer.append((char)(value / 10 + 48));
/*  971:1427 */       buffer.append((char)(value % 10 + 48));
/*  972:     */     }
/*  973:     */   }
/*  974:     */   
/*  975:     */   private static class TwelveHourField
/*  976:     */     implements FastDateFormat.NumberRule
/*  977:     */   {
/*  978:     */     private final FastDateFormat.NumberRule mRule;
/*  979:     */     
/*  980:     */     TwelveHourField(FastDateFormat.NumberRule rule)
/*  981:     */     {
/*  982:1444 */       this.mRule = rule;
/*  983:     */     }
/*  984:     */     
/*  985:     */     public int estimateLength()
/*  986:     */     {
/*  987:1451 */       return this.mRule.estimateLength();
/*  988:     */     }
/*  989:     */     
/*  990:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/*  991:     */     {
/*  992:1458 */       int value = calendar.get(10);
/*  993:1459 */       if (value == 0) {
/*  994:1460 */         value = calendar.getLeastMaximum(10) + 1;
/*  995:     */       }
/*  996:1462 */       this.mRule.appendTo(buffer, value);
/*  997:     */     }
/*  998:     */     
/*  999:     */     public void appendTo(StringBuffer buffer, int value)
/* 1000:     */     {
/* 1001:1469 */       this.mRule.appendTo(buffer, value);
/* 1002:     */     }
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   private static class TwentyFourHourField
/* 1006:     */     implements FastDateFormat.NumberRule
/* 1007:     */   {
/* 1008:     */     private final FastDateFormat.NumberRule mRule;
/* 1009:     */     
/* 1010:     */     TwentyFourHourField(FastDateFormat.NumberRule rule)
/* 1011:     */     {
/* 1012:1486 */       this.mRule = rule;
/* 1013:     */     }
/* 1014:     */     
/* 1015:     */     public int estimateLength()
/* 1016:     */     {
/* 1017:1493 */       return this.mRule.estimateLength();
/* 1018:     */     }
/* 1019:     */     
/* 1020:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/* 1021:     */     {
/* 1022:1500 */       int value = calendar.get(11);
/* 1023:1501 */       if (value == 0) {
/* 1024:1502 */         value = calendar.getMaximum(11) + 1;
/* 1025:     */       }
/* 1026:1504 */       this.mRule.appendTo(buffer, value);
/* 1027:     */     }
/* 1028:     */     
/* 1029:     */     public void appendTo(StringBuffer buffer, int value)
/* 1030:     */     {
/* 1031:1511 */       this.mRule.appendTo(buffer, value);
/* 1032:     */     }
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   private static class TimeZoneNameRule
/* 1036:     */     implements FastDateFormat.Rule
/* 1037:     */   {
/* 1038:     */     private final TimeZone mTimeZone;
/* 1039:     */     private final boolean mTimeZoneForced;
/* 1040:     */     private final Locale mLocale;
/* 1041:     */     private final int mStyle;
/* 1042:     */     private final String mStandard;
/* 1043:     */     private final String mDaylight;
/* 1044:     */     
/* 1045:     */     TimeZoneNameRule(TimeZone timeZone, boolean timeZoneForced, Locale locale, int style)
/* 1046:     */     {
/* 1047:1535 */       this.mTimeZone = timeZone;
/* 1048:1536 */       this.mTimeZoneForced = timeZoneForced;
/* 1049:1537 */       this.mLocale = locale;
/* 1050:1538 */       this.mStyle = style;
/* 1051:1540 */       if (timeZoneForced)
/* 1052:     */       {
/* 1053:1541 */         this.mStandard = FastDateFormat.getTimeZoneDisplay(timeZone, false, style, locale);
/* 1054:1542 */         this.mDaylight = FastDateFormat.getTimeZoneDisplay(timeZone, true, style, locale);
/* 1055:     */       }
/* 1056:     */       else
/* 1057:     */       {
/* 1058:1544 */         this.mStandard = null;
/* 1059:1545 */         this.mDaylight = null;
/* 1060:     */       }
/* 1061:     */     }
/* 1062:     */     
/* 1063:     */     public int estimateLength()
/* 1064:     */     {
/* 1065:1553 */       if (this.mTimeZoneForced) {
/* 1066:1554 */         return Math.max(this.mStandard.length(), this.mDaylight.length());
/* 1067:     */       }
/* 1068:1555 */       if (this.mStyle == 0) {
/* 1069:1556 */         return 4;
/* 1070:     */       }
/* 1071:1558 */       return 40;
/* 1072:     */     }
/* 1073:     */     
/* 1074:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/* 1075:     */     {
/* 1076:1566 */       if (this.mTimeZoneForced)
/* 1077:     */       {
/* 1078:1567 */         if ((this.mTimeZone.useDaylightTime()) && (calendar.get(16) != 0)) {
/* 1079:1568 */           buffer.append(this.mDaylight);
/* 1080:     */         } else {
/* 1081:1570 */           buffer.append(this.mStandard);
/* 1082:     */         }
/* 1083:     */       }
/* 1084:     */       else
/* 1085:     */       {
/* 1086:1573 */         TimeZone timeZone = calendar.getTimeZone();
/* 1087:1574 */         if ((timeZone.useDaylightTime()) && (calendar.get(16) != 0)) {
/* 1088:1575 */           buffer.append(FastDateFormat.getTimeZoneDisplay(timeZone, true, this.mStyle, this.mLocale));
/* 1089:     */         } else {
/* 1090:1577 */           buffer.append(FastDateFormat.getTimeZoneDisplay(timeZone, false, this.mStyle, this.mLocale));
/* 1091:     */         }
/* 1092:     */       }
/* 1093:     */     }
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   private static class TimeZoneNumberRule
/* 1097:     */     implements FastDateFormat.Rule
/* 1098:     */   {
/* 1099:1588 */     static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
/* 1100:1589 */     static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
/* 1101:     */     final boolean mColon;
/* 1102:     */     
/* 1103:     */     TimeZoneNumberRule(boolean colon)
/* 1104:     */     {
/* 1105:1599 */       this.mColon = colon;
/* 1106:     */     }
/* 1107:     */     
/* 1108:     */     public int estimateLength()
/* 1109:     */     {
/* 1110:1606 */       return 5;
/* 1111:     */     }
/* 1112:     */     
/* 1113:     */     public void appendTo(StringBuffer buffer, Calendar calendar)
/* 1114:     */     {
/* 1115:1613 */       int offset = calendar.get(15) + calendar.get(16);
/* 1116:1615 */       if (offset < 0)
/* 1117:     */       {
/* 1118:1616 */         buffer.append('-');
/* 1119:1617 */         offset = -offset;
/* 1120:     */       }
/* 1121:     */       else
/* 1122:     */       {
/* 1123:1619 */         buffer.append('+');
/* 1124:     */       }
/* 1125:1622 */       int hours = offset / 3600000;
/* 1126:1623 */       buffer.append((char)(hours / 10 + 48));
/* 1127:1624 */       buffer.append((char)(hours % 10 + 48));
/* 1128:1626 */       if (this.mColon) {
/* 1129:1627 */         buffer.append(':');
/* 1130:     */       }
/* 1131:1630 */       int minutes = offset / 60000 - 60 * hours;
/* 1132:1631 */       buffer.append((char)(minutes / 10 + 48));
/* 1133:1632 */       buffer.append((char)(minutes % 10 + 48));
/* 1134:     */     }
/* 1135:     */   }
/* 1136:     */   
/* 1137:     */   private static class TimeZoneDisplayKey
/* 1138:     */   {
/* 1139:     */     private final TimeZone mTimeZone;
/* 1140:     */     private final int mStyle;
/* 1141:     */     private final Locale mLocale;
/* 1142:     */     
/* 1143:     */     TimeZoneDisplayKey(TimeZone timeZone, boolean daylight, int style, Locale locale)
/* 1144:     */     {
/* 1145:1655 */       this.mTimeZone = timeZone;
/* 1146:1656 */       if (daylight) {
/* 1147:1657 */         style |= 0x80000000;
/* 1148:     */       }
/* 1149:1659 */       this.mStyle = style;
/* 1150:1660 */       this.mLocale = locale;
/* 1151:     */     }
/* 1152:     */     
/* 1153:     */     public int hashCode()
/* 1154:     */     {
/* 1155:1667 */       return this.mStyle * 31 + this.mLocale.hashCode();
/* 1156:     */     }
/* 1157:     */     
/* 1158:     */     public boolean equals(Object obj)
/* 1159:     */     {
/* 1160:1674 */       if (this == obj) {
/* 1161:1675 */         return true;
/* 1162:     */       }
/* 1163:1677 */       if ((obj instanceof TimeZoneDisplayKey))
/* 1164:     */       {
/* 1165:1678 */         TimeZoneDisplayKey other = (TimeZoneDisplayKey)obj;
/* 1166:1679 */         return (this.mTimeZone.equals(other.mTimeZone)) && (this.mStyle == other.mStyle) && (this.mLocale.equals(other.mLocale));
/* 1167:     */       }
/* 1168:1684 */       return false;
/* 1169:     */     }
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   private static class Pair
/* 1173:     */   {
/* 1174:     */     private final Object mObj1;
/* 1175:     */     private final Object mObj2;
/* 1176:     */     
/* 1177:     */     public Pair(Object obj1, Object obj2)
/* 1178:     */     {
/* 1179:1705 */       this.mObj1 = obj1;
/* 1180:1706 */       this.mObj2 = obj2;
/* 1181:     */     }
/* 1182:     */     
/* 1183:     */     public boolean equals(Object obj)
/* 1184:     */     {
/* 1185:1713 */       if (this == obj) {
/* 1186:1714 */         return true;
/* 1187:     */       }
/* 1188:1717 */       if (!(obj instanceof Pair)) {
/* 1189:1718 */         return false;
/* 1190:     */       }
/* 1191:1721 */       Pair key = (Pair)obj;
/* 1192:     */       
/* 1193:1723 */       return (this.mObj1 == null ? key.mObj1 == null : this.mObj1.equals(key.mObj1)) && (this.mObj2 == null ? key.mObj2 == null : this.mObj2.equals(key.mObj2));
/* 1194:     */     }
/* 1195:     */     
/* 1196:     */     public int hashCode()
/* 1197:     */     {
/* 1198:1734 */       return (this.mObj1 == null ? 0 : this.mObj1.hashCode()) + (this.mObj2 == null ? 0 : this.mObj2.hashCode());
/* 1199:     */     }
/* 1200:     */     
/* 1201:     */     public String toString()
/* 1202:     */     {
/* 1203:1743 */       return "[" + this.mObj1 + ':' + this.mObj2 + ']';
/* 1204:     */     }
/* 1205:     */   }
/* 1206:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.time.FastDateFormat
 * JD-Core Version:    0.7.0.1
 */