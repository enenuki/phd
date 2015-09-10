/*    1:     */ package org.apache.commons.lang.time;
/*    2:     */ 
/*    3:     */ import java.text.ParseException;
/*    4:     */ import java.text.ParsePosition;
/*    5:     */ import java.text.SimpleDateFormat;
/*    6:     */ import java.util.Calendar;
/*    7:     */ import java.util.Date;
/*    8:     */ import java.util.Iterator;
/*    9:     */ import java.util.NoSuchElementException;
/*   10:     */ import java.util.TimeZone;
/*   11:     */ import org.apache.commons.lang.StringUtils;
/*   12:     */ 
/*   13:     */ public class DateUtils
/*   14:     */ {
/*   15:  61 */   public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
/*   16:     */   public static final long MILLIS_PER_SECOND = 1000L;
/*   17:     */   public static final long MILLIS_PER_MINUTE = 60000L;
/*   18:     */   public static final long MILLIS_PER_HOUR = 3600000L;
/*   19:     */   public static final long MILLIS_PER_DAY = 86400000L;
/*   20:     */   public static final int SEMI_MONTH = 1001;
/*   21:  89 */   private static final int[][] fields = { { 14 }, { 13 }, { 12 }, { 11, 10 }, { 5, 5, 9 }, { 2, 1001 }, { 1 }, { 0 } };
/*   22:     */   public static final int RANGE_WEEK_SUNDAY = 1;
/*   23:     */   public static final int RANGE_WEEK_MONDAY = 2;
/*   24:     */   public static final int RANGE_WEEK_RELATIVE = 3;
/*   25:     */   public static final int RANGE_WEEK_CENTER = 4;
/*   26:     */   public static final int RANGE_MONTH_SUNDAY = 5;
/*   27:     */   public static final int RANGE_MONTH_MONDAY = 6;
/*   28:     */   private static final int MODIFY_TRUNCATE = 0;
/*   29:     */   private static final int MODIFY_ROUND = 1;
/*   30:     */   private static final int MODIFY_CEILING = 2;
/*   31:     */   /**
/*   32:     */    * @deprecated
/*   33:     */    */
/*   34:     */   public static final int MILLIS_IN_SECOND = 1000;
/*   35:     */   /**
/*   36:     */    * @deprecated
/*   37:     */    */
/*   38:     */   public static final int MILLIS_IN_MINUTE = 60000;
/*   39:     */   /**
/*   40:     */    * @deprecated
/*   41:     */    */
/*   42:     */   public static final int MILLIS_IN_HOUR = 3600000;
/*   43:     */   /**
/*   44:     */    * @deprecated
/*   45:     */    */
/*   46:     */   public static final int MILLIS_IN_DAY = 86400000;
/*   47:     */   
/*   48:     */   public static boolean isSameDay(Date date1, Date date2)
/*   49:     */   {
/*   50: 173 */     if ((date1 == null) || (date2 == null)) {
/*   51: 174 */       throw new IllegalArgumentException("The date must not be null");
/*   52:     */     }
/*   53: 176 */     Calendar cal1 = Calendar.getInstance();
/*   54: 177 */     cal1.setTime(date1);
/*   55: 178 */     Calendar cal2 = Calendar.getInstance();
/*   56: 179 */     cal2.setTime(date2);
/*   57: 180 */     return isSameDay(cal1, cal2);
/*   58:     */   }
/*   59:     */   
/*   60:     */   public static boolean isSameDay(Calendar cal1, Calendar cal2)
/*   61:     */   {
/*   62: 197 */     if ((cal1 == null) || (cal2 == null)) {
/*   63: 198 */       throw new IllegalArgumentException("The date must not be null");
/*   64:     */     }
/*   65: 200 */     return (cal1.get(0) == cal2.get(0)) && (cal1.get(1) == cal2.get(1)) && (cal1.get(6) == cal2.get(6));
/*   66:     */   }
/*   67:     */   
/*   68:     */   public static boolean isSameInstant(Date date1, Date date2)
/*   69:     */   {
/*   70: 218 */     if ((date1 == null) || (date2 == null)) {
/*   71: 219 */       throw new IllegalArgumentException("The date must not be null");
/*   72:     */     }
/*   73: 221 */     return date1.getTime() == date2.getTime();
/*   74:     */   }
/*   75:     */   
/*   76:     */   public static boolean isSameInstant(Calendar cal1, Calendar cal2)
/*   77:     */   {
/*   78: 236 */     if ((cal1 == null) || (cal2 == null)) {
/*   79: 237 */       throw new IllegalArgumentException("The date must not be null");
/*   80:     */     }
/*   81: 239 */     return cal1.getTime().getTime() == cal2.getTime().getTime();
/*   82:     */   }
/*   83:     */   
/*   84:     */   public static boolean isSameLocalTime(Calendar cal1, Calendar cal2)
/*   85:     */   {
/*   86: 256 */     if ((cal1 == null) || (cal2 == null)) {
/*   87: 257 */       throw new IllegalArgumentException("The date must not be null");
/*   88:     */     }
/*   89: 259 */     return (cal1.get(14) == cal2.get(14)) && (cal1.get(13) == cal2.get(13)) && (cal1.get(12) == cal2.get(12)) && (cal1.get(10) == cal2.get(10)) && (cal1.get(6) == cal2.get(6)) && (cal1.get(1) == cal2.get(1)) && (cal1.get(0) == cal2.get(0)) && (cal1.getClass() == cal2.getClass());
/*   90:     */   }
/*   91:     */   
/*   92:     */   public static Date parseDate(String str, String[] parsePatterns)
/*   93:     */     throws ParseException
/*   94:     */   {
/*   95: 285 */     return parseDateWithLeniency(str, parsePatterns, true);
/*   96:     */   }
/*   97:     */   
/*   98:     */   public static Date parseDateStrictly(String str, String[] parsePatterns)
/*   99:     */     throws ParseException
/*  100:     */   {
/*  101: 305 */     return parseDateWithLeniency(str, parsePatterns, false);
/*  102:     */   }
/*  103:     */   
/*  104:     */   private static Date parseDateWithLeniency(String str, String[] parsePatterns, boolean lenient)
/*  105:     */     throws ParseException
/*  106:     */   {
/*  107: 325 */     if ((str == null) || (parsePatterns == null)) {
/*  108: 326 */       throw new IllegalArgumentException("Date and Patterns must not be null");
/*  109:     */     }
/*  110: 329 */     SimpleDateFormat parser = new SimpleDateFormat();
/*  111: 330 */     parser.setLenient(lenient);
/*  112: 331 */     ParsePosition pos = new ParsePosition(0);
/*  113: 332 */     for (int i = 0; i < parsePatterns.length; i++)
/*  114:     */     {
/*  115: 334 */       String pattern = parsePatterns[i];
/*  116: 337 */       if (parsePatterns[i].endsWith("ZZ")) {
/*  117: 338 */         pattern = pattern.substring(0, pattern.length() - 1);
/*  118:     */       }
/*  119: 341 */       parser.applyPattern(pattern);
/*  120: 342 */       pos.setIndex(0);
/*  121:     */       
/*  122: 344 */       String str2 = str;
/*  123: 346 */       if (parsePatterns[i].endsWith("ZZ"))
/*  124:     */       {
/*  125: 347 */         int signIdx = indexOfSignChars(str2, 0);
/*  126: 348 */         while (signIdx >= 0)
/*  127:     */         {
/*  128: 349 */           str2 = reformatTimezone(str2, signIdx);
/*  129: 350 */           signIdx = indexOfSignChars(str2, ++signIdx);
/*  130:     */         }
/*  131:     */       }
/*  132: 354 */       Date date = parser.parse(str2, pos);
/*  133: 355 */       if ((date != null) && (pos.getIndex() == str2.length())) {
/*  134: 356 */         return date;
/*  135:     */       }
/*  136:     */     }
/*  137: 359 */     throw new ParseException("Unable to parse the date: " + str, -1);
/*  138:     */   }
/*  139:     */   
/*  140:     */   private static int indexOfSignChars(String str, int startPos)
/*  141:     */   {
/*  142: 370 */     int idx = StringUtils.indexOf(str, '+', startPos);
/*  143: 371 */     if (idx < 0) {
/*  144: 372 */       idx = StringUtils.indexOf(str, '-', startPos);
/*  145:     */     }
/*  146: 374 */     return idx;
/*  147:     */   }
/*  148:     */   
/*  149:     */   private static String reformatTimezone(String str, int signIdx)
/*  150:     */   {
/*  151: 385 */     String str2 = str;
/*  152: 386 */     if ((signIdx >= 0) && (signIdx + 5 < str.length()) && (Character.isDigit(str.charAt(signIdx + 1))) && (Character.isDigit(str.charAt(signIdx + 2))) && (str.charAt(signIdx + 3) == ':') && (Character.isDigit(str.charAt(signIdx + 4))) && (Character.isDigit(str.charAt(signIdx + 5)))) {
/*  153: 393 */       str2 = str.substring(0, signIdx + 3) + str.substring(signIdx + 4);
/*  154:     */     }
/*  155: 395 */     return str2;
/*  156:     */   }
/*  157:     */   
/*  158:     */   public static Date addYears(Date date, int amount)
/*  159:     */   {
/*  160: 409 */     return add(date, 1, amount);
/*  161:     */   }
/*  162:     */   
/*  163:     */   public static Date addMonths(Date date, int amount)
/*  164:     */   {
/*  165: 423 */     return add(date, 2, amount);
/*  166:     */   }
/*  167:     */   
/*  168:     */   public static Date addWeeks(Date date, int amount)
/*  169:     */   {
/*  170: 437 */     return add(date, 3, amount);
/*  171:     */   }
/*  172:     */   
/*  173:     */   public static Date addDays(Date date, int amount)
/*  174:     */   {
/*  175: 451 */     return add(date, 5, amount);
/*  176:     */   }
/*  177:     */   
/*  178:     */   public static Date addHours(Date date, int amount)
/*  179:     */   {
/*  180: 465 */     return add(date, 11, amount);
/*  181:     */   }
/*  182:     */   
/*  183:     */   public static Date addMinutes(Date date, int amount)
/*  184:     */   {
/*  185: 479 */     return add(date, 12, amount);
/*  186:     */   }
/*  187:     */   
/*  188:     */   public static Date addSeconds(Date date, int amount)
/*  189:     */   {
/*  190: 493 */     return add(date, 13, amount);
/*  191:     */   }
/*  192:     */   
/*  193:     */   public static Date addMilliseconds(Date date, int amount)
/*  194:     */   {
/*  195: 507 */     return add(date, 14, amount);
/*  196:     */   }
/*  197:     */   
/*  198:     */   /**
/*  199:     */    * @deprecated
/*  200:     */    */
/*  201:     */   public static Date add(Date date, int calendarField, int amount)
/*  202:     */   {
/*  203: 523 */     if (date == null) {
/*  204: 524 */       throw new IllegalArgumentException("The date must not be null");
/*  205:     */     }
/*  206: 526 */     Calendar c = Calendar.getInstance();
/*  207: 527 */     c.setTime(date);
/*  208: 528 */     c.add(calendarField, amount);
/*  209: 529 */     return c.getTime();
/*  210:     */   }
/*  211:     */   
/*  212:     */   public static Date setYears(Date date, int amount)
/*  213:     */   {
/*  214: 544 */     return set(date, 1, amount);
/*  215:     */   }
/*  216:     */   
/*  217:     */   public static Date setMonths(Date date, int amount)
/*  218:     */   {
/*  219: 559 */     return set(date, 2, amount);
/*  220:     */   }
/*  221:     */   
/*  222:     */   public static Date setDays(Date date, int amount)
/*  223:     */   {
/*  224: 574 */     return set(date, 5, amount);
/*  225:     */   }
/*  226:     */   
/*  227:     */   public static Date setHours(Date date, int amount)
/*  228:     */   {
/*  229: 590 */     return set(date, 11, amount);
/*  230:     */   }
/*  231:     */   
/*  232:     */   public static Date setMinutes(Date date, int amount)
/*  233:     */   {
/*  234: 605 */     return set(date, 12, amount);
/*  235:     */   }
/*  236:     */   
/*  237:     */   public static Date setSeconds(Date date, int amount)
/*  238:     */   {
/*  239: 620 */     return set(date, 13, amount);
/*  240:     */   }
/*  241:     */   
/*  242:     */   public static Date setMilliseconds(Date date, int amount)
/*  243:     */   {
/*  244: 635 */     return set(date, 14, amount);
/*  245:     */   }
/*  246:     */   
/*  247:     */   private static Date set(Date date, int calendarField, int amount)
/*  248:     */   {
/*  249: 652 */     if (date == null) {
/*  250: 653 */       throw new IllegalArgumentException("The date must not be null");
/*  251:     */     }
/*  252: 656 */     Calendar c = Calendar.getInstance();
/*  253: 657 */     c.setLenient(false);
/*  254: 658 */     c.setTime(date);
/*  255: 659 */     c.set(calendarField, amount);
/*  256: 660 */     return c.getTime();
/*  257:     */   }
/*  258:     */   
/*  259:     */   public static Calendar toCalendar(Date date)
/*  260:     */   {
/*  261: 673 */     Calendar c = Calendar.getInstance();
/*  262: 674 */     c.setTime(date);
/*  263: 675 */     return c;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public static Date round(Date date, int field)
/*  267:     */   {
/*  268: 708 */     if (date == null) {
/*  269: 709 */       throw new IllegalArgumentException("The date must not be null");
/*  270:     */     }
/*  271: 711 */     Calendar gval = Calendar.getInstance();
/*  272: 712 */     gval.setTime(date);
/*  273: 713 */     modify(gval, field, 1);
/*  274: 714 */     return gval.getTime();
/*  275:     */   }
/*  276:     */   
/*  277:     */   public static Calendar round(Calendar date, int field)
/*  278:     */   {
/*  279: 746 */     if (date == null) {
/*  280: 747 */       throw new IllegalArgumentException("The date must not be null");
/*  281:     */     }
/*  282: 749 */     Calendar rounded = (Calendar)date.clone();
/*  283: 750 */     modify(rounded, field, 1);
/*  284: 751 */     return rounded;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public static Date round(Object date, int field)
/*  288:     */   {
/*  289: 785 */     if (date == null) {
/*  290: 786 */       throw new IllegalArgumentException("The date must not be null");
/*  291:     */     }
/*  292: 788 */     if ((date instanceof Date)) {
/*  293: 789 */       return round((Date)date, field);
/*  294:     */     }
/*  295: 790 */     if ((date instanceof Calendar)) {
/*  296: 791 */       return round((Calendar)date, field).getTime();
/*  297:     */     }
/*  298: 793 */     throw new ClassCastException("Could not round " + date);
/*  299:     */   }
/*  300:     */   
/*  301:     */   public static Date truncate(Date date, int field)
/*  302:     */   {
/*  303: 815 */     if (date == null) {
/*  304: 816 */       throw new IllegalArgumentException("The date must not be null");
/*  305:     */     }
/*  306: 818 */     Calendar gval = Calendar.getInstance();
/*  307: 819 */     gval.setTime(date);
/*  308: 820 */     modify(gval, field, 0);
/*  309: 821 */     return gval.getTime();
/*  310:     */   }
/*  311:     */   
/*  312:     */   public static Calendar truncate(Calendar date, int field)
/*  313:     */   {
/*  314: 841 */     if (date == null) {
/*  315: 842 */       throw new IllegalArgumentException("The date must not be null");
/*  316:     */     }
/*  317: 844 */     Calendar truncated = (Calendar)date.clone();
/*  318: 845 */     modify(truncated, field, 0);
/*  319: 846 */     return truncated;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public static Date truncate(Object date, int field)
/*  323:     */   {
/*  324: 870 */     if (date == null) {
/*  325: 871 */       throw new IllegalArgumentException("The date must not be null");
/*  326:     */     }
/*  327: 873 */     if ((date instanceof Date)) {
/*  328: 874 */       return truncate((Date)date, field);
/*  329:     */     }
/*  330: 875 */     if ((date instanceof Calendar)) {
/*  331: 876 */       return truncate((Calendar)date, field).getTime();
/*  332:     */     }
/*  333: 878 */     throw new ClassCastException("Could not truncate " + date);
/*  334:     */   }
/*  335:     */   
/*  336:     */   public static Date ceiling(Date date, int field)
/*  337:     */   {
/*  338: 901 */     if (date == null) {
/*  339: 902 */       throw new IllegalArgumentException("The date must not be null");
/*  340:     */     }
/*  341: 904 */     Calendar gval = Calendar.getInstance();
/*  342: 905 */     gval.setTime(date);
/*  343: 906 */     modify(gval, field, 2);
/*  344: 907 */     return gval.getTime();
/*  345:     */   }
/*  346:     */   
/*  347:     */   public static Calendar ceiling(Calendar date, int field)
/*  348:     */   {
/*  349: 928 */     if (date == null) {
/*  350: 929 */       throw new IllegalArgumentException("The date must not be null");
/*  351:     */     }
/*  352: 931 */     Calendar ceiled = (Calendar)date.clone();
/*  353: 932 */     modify(ceiled, field, 2);
/*  354: 933 */     return ceiled;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public static Date ceiling(Object date, int field)
/*  358:     */   {
/*  359: 958 */     if (date == null) {
/*  360: 959 */       throw new IllegalArgumentException("The date must not be null");
/*  361:     */     }
/*  362: 961 */     if ((date instanceof Date)) {
/*  363: 962 */       return ceiling((Date)date, field);
/*  364:     */     }
/*  365: 963 */     if ((date instanceof Calendar)) {
/*  366: 964 */       return ceiling((Calendar)date, field).getTime();
/*  367:     */     }
/*  368: 966 */     throw new ClassCastException("Could not find ceiling of for type: " + date.getClass());
/*  369:     */   }
/*  370:     */   
/*  371:     */   private static void modify(Calendar val, int field, int modType)
/*  372:     */   {
/*  373: 980 */     if (val.get(1) > 280000000) {
/*  374: 981 */       throw new ArithmeticException("Calendar value too large for accurate calculations");
/*  375:     */     }
/*  376: 984 */     if (field == 14) {
/*  377: 985 */       return;
/*  378:     */     }
/*  379: 994 */     Date date = val.getTime();
/*  380: 995 */     long time = date.getTime();
/*  381: 996 */     boolean done = false;
/*  382:     */     
/*  383:     */ 
/*  384: 999 */     int millisecs = val.get(14);
/*  385:1000 */     if ((0 == modType) || (millisecs < 500)) {
/*  386:1001 */       time -= millisecs;
/*  387:     */     }
/*  388:1003 */     if (field == 13) {
/*  389:1004 */       done = true;
/*  390:     */     }
/*  391:1008 */     int seconds = val.get(13);
/*  392:1009 */     if ((!done) && ((0 == modType) || (seconds < 30))) {
/*  393:1010 */       time -= seconds * 1000L;
/*  394:     */     }
/*  395:1012 */     if (field == 12) {
/*  396:1013 */       done = true;
/*  397:     */     }
/*  398:1017 */     int minutes = val.get(12);
/*  399:1018 */     if ((!done) && ((0 == modType) || (minutes < 30))) {
/*  400:1019 */       time -= minutes * 60000L;
/*  401:     */     }
/*  402:1023 */     if (date.getTime() != time)
/*  403:     */     {
/*  404:1024 */       date.setTime(time);
/*  405:1025 */       val.setTime(date);
/*  406:     */     }
/*  407:1029 */     boolean roundUp = false;
/*  408:1030 */     for (int i = 0; i < fields.length; i++)
/*  409:     */     {
/*  410:1031 */       for (int j = 0; j < fields[i].length; j++) {
/*  411:1032 */         if (fields[i][j] == field)
/*  412:     */         {
/*  413:1034 */           if ((modType == 2) || ((modType == 1) && (roundUp))) {
/*  414:1035 */             if (field == 1001)
/*  415:     */             {
/*  416:1039 */               if (val.get(5) == 1)
/*  417:     */               {
/*  418:1040 */                 val.add(5, 15);
/*  419:     */               }
/*  420:     */               else
/*  421:     */               {
/*  422:1042 */                 val.add(5, -15);
/*  423:1043 */                 val.add(2, 1);
/*  424:     */               }
/*  425:     */             }
/*  426:1046 */             else if (field == 9)
/*  427:     */             {
/*  428:1050 */               if (val.get(11) == 0)
/*  429:     */               {
/*  430:1051 */                 val.add(11, 12);
/*  431:     */               }
/*  432:     */               else
/*  433:     */               {
/*  434:1053 */                 val.add(11, -12);
/*  435:1054 */                 val.add(5, 1);
/*  436:     */               }
/*  437:     */             }
/*  438:     */             else {
/*  439:1060 */               val.add(fields[i][0], 1);
/*  440:     */             }
/*  441:     */           }
/*  442:1063 */           return;
/*  443:     */         }
/*  444:     */       }
/*  445:1067 */       int offset = 0;
/*  446:1068 */       boolean offsetSet = false;
/*  447:1070 */       switch (field)
/*  448:     */       {
/*  449:     */       case 1001: 
/*  450:1072 */         if (fields[i][0] == 5)
/*  451:     */         {
/*  452:1076 */           offset = val.get(5) - 1;
/*  453:1079 */           if (offset >= 15) {
/*  454:1080 */             offset -= 15;
/*  455:     */           }
/*  456:1083 */           roundUp = offset > 7;
/*  457:1084 */           offsetSet = true;
/*  458:     */         }
/*  459:     */         break;
/*  460:     */       case 9: 
/*  461:1088 */         if (fields[i][0] == 11)
/*  462:     */         {
/*  463:1091 */           offset = val.get(11);
/*  464:1092 */           if (offset >= 12) {
/*  465:1093 */             offset -= 12;
/*  466:     */           }
/*  467:1095 */           roundUp = offset >= 6;
/*  468:1096 */           offsetSet = true;
/*  469:     */         }
/*  470:     */         break;
/*  471:     */       }
/*  472:1100 */       if (!offsetSet)
/*  473:     */       {
/*  474:1101 */         int min = val.getActualMinimum(fields[i][0]);
/*  475:1102 */         int max = val.getActualMaximum(fields[i][0]);
/*  476:     */         
/*  477:1104 */         offset = val.get(fields[i][0]) - min;
/*  478:     */         
/*  479:1106 */         roundUp = offset > (max - min) / 2;
/*  480:     */       }
/*  481:1109 */       if (offset != 0) {
/*  482:1110 */         val.set(fields[i][0], val.get(fields[i][0]) - offset);
/*  483:     */       }
/*  484:     */     }
/*  485:1113 */     throw new IllegalArgumentException("The field " + field + " is not supported");
/*  486:     */   }
/*  487:     */   
/*  488:     */   public static Iterator iterator(Date focus, int rangeStyle)
/*  489:     */   {
/*  490:1143 */     if (focus == null) {
/*  491:1144 */       throw new IllegalArgumentException("The date must not be null");
/*  492:     */     }
/*  493:1146 */     Calendar gval = Calendar.getInstance();
/*  494:1147 */     gval.setTime(focus);
/*  495:1148 */     return iterator(gval, rangeStyle);
/*  496:     */   }
/*  497:     */   
/*  498:     */   public static Iterator iterator(Calendar focus, int rangeStyle)
/*  499:     */   {
/*  500:1176 */     if (focus == null) {
/*  501:1177 */       throw new IllegalArgumentException("The date must not be null");
/*  502:     */     }
/*  503:1179 */     Calendar start = null;
/*  504:1180 */     Calendar end = null;
/*  505:1181 */     int startCutoff = 1;
/*  506:1182 */     int endCutoff = 7;
/*  507:1183 */     switch (rangeStyle)
/*  508:     */     {
/*  509:     */     case 5: 
/*  510:     */     case 6: 
/*  511:1187 */       start = truncate(focus, 2);
/*  512:     */       
/*  513:1189 */       end = (Calendar)start.clone();
/*  514:1190 */       end.add(2, 1);
/*  515:1191 */       end.add(5, -1);
/*  516:1193 */       if (rangeStyle == 6)
/*  517:     */       {
/*  518:1194 */         startCutoff = 2;
/*  519:1195 */         endCutoff = 1;
/*  520:     */       }
/*  521:     */       break;
/*  522:     */     case 1: 
/*  523:     */     case 2: 
/*  524:     */     case 3: 
/*  525:     */     case 4: 
/*  526:1203 */       start = truncate(focus, 5);
/*  527:1204 */       end = truncate(focus, 5);
/*  528:1205 */       switch (rangeStyle)
/*  529:     */       {
/*  530:     */       case 1: 
/*  531:     */         break;
/*  532:     */       case 2: 
/*  533:1210 */         startCutoff = 2;
/*  534:1211 */         endCutoff = 1;
/*  535:1212 */         break;
/*  536:     */       case 3: 
/*  537:1214 */         startCutoff = focus.get(7);
/*  538:1215 */         endCutoff = startCutoff - 1;
/*  539:1216 */         break;
/*  540:     */       case 4: 
/*  541:1218 */         startCutoff = focus.get(7) - 3;
/*  542:1219 */         endCutoff = focus.get(7) + 3;
/*  543:     */       }
/*  544:1222 */       break;
/*  545:     */     default: 
/*  546:1224 */       throw new IllegalArgumentException("The range style " + rangeStyle + " is not valid.");
/*  547:     */     }
/*  548:1226 */     if (startCutoff < 1) {
/*  549:1227 */       startCutoff += 7;
/*  550:     */     }
/*  551:1229 */     if (startCutoff > 7) {
/*  552:1230 */       startCutoff -= 7;
/*  553:     */     }
/*  554:1232 */     if (endCutoff < 1) {
/*  555:1233 */       endCutoff += 7;
/*  556:     */     }
/*  557:1235 */     if (endCutoff > 7) {
/*  558:1236 */       endCutoff -= 7;
/*  559:     */     }
/*  560:1238 */     while (start.get(7) != startCutoff) {
/*  561:1239 */       start.add(5, -1);
/*  562:     */     }
/*  563:1241 */     while (end.get(7) != endCutoff) {
/*  564:1242 */       end.add(5, 1);
/*  565:     */     }
/*  566:1244 */     return new DateIterator(start, end);
/*  567:     */   }
/*  568:     */   
/*  569:     */   public static Iterator iterator(Object focus, int rangeStyle)
/*  570:     */   {
/*  571:1267 */     if (focus == null) {
/*  572:1268 */       throw new IllegalArgumentException("The date must not be null");
/*  573:     */     }
/*  574:1270 */     if ((focus instanceof Date)) {
/*  575:1271 */       return iterator((Date)focus, rangeStyle);
/*  576:     */     }
/*  577:1272 */     if ((focus instanceof Calendar)) {
/*  578:1273 */       return iterator((Calendar)focus, rangeStyle);
/*  579:     */     }
/*  580:1275 */     throw new ClassCastException("Could not iterate based on " + focus);
/*  581:     */   }
/*  582:     */   
/*  583:     */   public static long getFragmentInMilliseconds(Date date, int fragment)
/*  584:     */   {
/*  585:1313 */     return getFragment(date, fragment, 14);
/*  586:     */   }
/*  587:     */   
/*  588:     */   public static long getFragmentInSeconds(Date date, int fragment)
/*  589:     */   {
/*  590:1353 */     return getFragment(date, fragment, 13);
/*  591:     */   }
/*  592:     */   
/*  593:     */   public static long getFragmentInMinutes(Date date, int fragment)
/*  594:     */   {
/*  595:1393 */     return getFragment(date, fragment, 12);
/*  596:     */   }
/*  597:     */   
/*  598:     */   public static long getFragmentInHours(Date date, int fragment)
/*  599:     */   {
/*  600:1433 */     return getFragment(date, fragment, 11);
/*  601:     */   }
/*  602:     */   
/*  603:     */   public static long getFragmentInDays(Date date, int fragment)
/*  604:     */   {
/*  605:1473 */     return getFragment(date, fragment, 6);
/*  606:     */   }
/*  607:     */   
/*  608:     */   public static long getFragmentInMilliseconds(Calendar calendar, int fragment)
/*  609:     */   {
/*  610:1513 */     return getFragment(calendar, fragment, 14);
/*  611:     */   }
/*  612:     */   
/*  613:     */   public static long getFragmentInSeconds(Calendar calendar, int fragment)
/*  614:     */   {
/*  615:1552 */     return getFragment(calendar, fragment, 13);
/*  616:     */   }
/*  617:     */   
/*  618:     */   public static long getFragmentInMinutes(Calendar calendar, int fragment)
/*  619:     */   {
/*  620:1592 */     return getFragment(calendar, fragment, 12);
/*  621:     */   }
/*  622:     */   
/*  623:     */   public static long getFragmentInHours(Calendar calendar, int fragment)
/*  624:     */   {
/*  625:1632 */     return getFragment(calendar, fragment, 11);
/*  626:     */   }
/*  627:     */   
/*  628:     */   public static long getFragmentInDays(Calendar calendar, int fragment)
/*  629:     */   {
/*  630:1674 */     return getFragment(calendar, fragment, 6);
/*  631:     */   }
/*  632:     */   
/*  633:     */   private static long getFragment(Date date, int fragment, int unit)
/*  634:     */   {
/*  635:1689 */     if (date == null) {
/*  636:1690 */       throw new IllegalArgumentException("The date must not be null");
/*  637:     */     }
/*  638:1692 */     Calendar calendar = Calendar.getInstance();
/*  639:1693 */     calendar.setTime(date);
/*  640:1694 */     return getFragment(calendar, fragment, unit);
/*  641:     */   }
/*  642:     */   
/*  643:     */   private static long getFragment(Calendar calendar, int fragment, int unit)
/*  644:     */   {
/*  645:1709 */     if (calendar == null) {
/*  646:1710 */       throw new IllegalArgumentException("The date must not be null");
/*  647:     */     }
/*  648:1712 */     long millisPerUnit = getMillisPerUnit(unit);
/*  649:1713 */     long result = 0L;
/*  650:1716 */     switch (fragment)
/*  651:     */     {
/*  652:     */     case 1: 
/*  653:1718 */       result += calendar.get(6) * 86400000L / millisPerUnit;
/*  654:1719 */       break;
/*  655:     */     case 2: 
/*  656:1721 */       result += calendar.get(5) * 86400000L / millisPerUnit;
/*  657:     */     }
/*  658:1725 */     switch (fragment)
/*  659:     */     {
/*  660:     */     case 1: 
/*  661:     */     case 2: 
/*  662:     */     case 5: 
/*  663:     */     case 6: 
/*  664:1733 */       result += calendar.get(11) * 3600000L / millisPerUnit;
/*  665:     */     case 11: 
/*  666:1736 */       result += calendar.get(12) * 60000L / millisPerUnit;
/*  667:     */     case 12: 
/*  668:1739 */       result += calendar.get(13) * 1000L / millisPerUnit;
/*  669:     */     case 13: 
/*  670:1742 */       result += calendar.get(14) * 1 / millisPerUnit;
/*  671:1743 */       break;
/*  672:     */     case 14: 
/*  673:     */       break;
/*  674:     */     case 3: 
/*  675:     */     case 4: 
/*  676:     */     case 7: 
/*  677:     */     case 8: 
/*  678:     */     case 9: 
/*  679:     */     case 10: 
/*  680:     */     default: 
/*  681:1747 */       throw new IllegalArgumentException("The fragment " + fragment + " is not supported");
/*  682:     */     }
/*  683:1749 */     return result;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public static boolean truncatedEquals(Calendar cal1, Calendar cal2, int field)
/*  687:     */   {
/*  688:1766 */     return truncatedCompareTo(cal1, cal2, field) == 0;
/*  689:     */   }
/*  690:     */   
/*  691:     */   public static boolean truncatedEquals(Date date1, Date date2, int field)
/*  692:     */   {
/*  693:1783 */     return truncatedCompareTo(date1, date2, field) == 0;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field)
/*  697:     */   {
/*  698:1801 */     Calendar truncatedCal1 = truncate(cal1, field);
/*  699:1802 */     Calendar truncatedCal2 = truncate(cal2, field);
/*  700:1803 */     return truncatedCal1.getTime().compareTo(truncatedCal2.getTime());
/*  701:     */   }
/*  702:     */   
/*  703:     */   public static int truncatedCompareTo(Date date1, Date date2, int field)
/*  704:     */   {
/*  705:1821 */     Date truncatedDate1 = truncate(date1, field);
/*  706:1822 */     Date truncatedDate2 = truncate(date2, field);
/*  707:1823 */     return truncatedDate1.compareTo(truncatedDate2);
/*  708:     */   }
/*  709:     */   
/*  710:     */   private static long getMillisPerUnit(int unit)
/*  711:     */   {
/*  712:1835 */     long result = 9223372036854775807L;
/*  713:1836 */     switch (unit)
/*  714:     */     {
/*  715:     */     case 5: 
/*  716:     */     case 6: 
/*  717:1839 */       result = 86400000L;
/*  718:1840 */       break;
/*  719:     */     case 11: 
/*  720:1842 */       result = 3600000L;
/*  721:1843 */       break;
/*  722:     */     case 12: 
/*  723:1845 */       result = 60000L;
/*  724:1846 */       break;
/*  725:     */     case 13: 
/*  726:1848 */       result = 1000L;
/*  727:1849 */       break;
/*  728:     */     case 14: 
/*  729:1851 */       result = 1L;
/*  730:1852 */       break;
/*  731:     */     case 7: 
/*  732:     */     case 8: 
/*  733:     */     case 9: 
/*  734:     */     case 10: 
/*  735:     */     default: 
/*  736:1853 */       throw new IllegalArgumentException("The unit " + unit + " cannot be represented is milleseconds");
/*  737:     */     }
/*  738:1855 */     return result;
/*  739:     */   }
/*  740:     */   
/*  741:     */   static class DateIterator
/*  742:     */     implements Iterator
/*  743:     */   {
/*  744:     */     private final Calendar endFinal;
/*  745:     */     private final Calendar spot;
/*  746:     */     
/*  747:     */     DateIterator(Calendar startFinal, Calendar endFinal)
/*  748:     */     {
/*  749:1873 */       this.endFinal = endFinal;
/*  750:1874 */       this.spot = startFinal;
/*  751:1875 */       this.spot.add(5, -1);
/*  752:     */     }
/*  753:     */     
/*  754:     */     public boolean hasNext()
/*  755:     */     {
/*  756:1884 */       return this.spot.before(this.endFinal);
/*  757:     */     }
/*  758:     */     
/*  759:     */     public Object next()
/*  760:     */     {
/*  761:1893 */       if (this.spot.equals(this.endFinal)) {
/*  762:1894 */         throw new NoSuchElementException();
/*  763:     */       }
/*  764:1896 */       this.spot.add(5, 1);
/*  765:1897 */       return this.spot.clone();
/*  766:     */     }
/*  767:     */     
/*  768:     */     public void remove()
/*  769:     */     {
/*  770:1907 */       throw new UnsupportedOperationException();
/*  771:     */     }
/*  772:     */   }
/*  773:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.time.DateUtils
 * JD-Core Version:    0.7.0.1
 */