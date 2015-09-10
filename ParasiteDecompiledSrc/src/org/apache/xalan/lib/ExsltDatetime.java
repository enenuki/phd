/*    1:     */ package org.apache.xalan.lib;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.text.DateFormat;
/*    5:     */ import java.text.ParseException;
/*    6:     */ import java.text.SimpleDateFormat;
/*    7:     */ import java.util.Calendar;
/*    8:     */ import java.util.Date;
/*    9:     */ import java.util.Locale;
/*   10:     */ import java.util.TimeZone;
/*   11:     */ import org.apache.xpath.objects.XBoolean;
/*   12:     */ import org.apache.xpath.objects.XNumber;
/*   13:     */ import org.apache.xpath.objects.XObject;
/*   14:     */ 
/*   15:     */ public class ExsltDatetime
/*   16:     */ {
/*   17:     */   static final String dt = "yyyy-MM-dd'T'HH:mm:ss";
/*   18:     */   static final String d = "yyyy-MM-dd";
/*   19:     */   static final String gym = "yyyy-MM";
/*   20:     */   static final String gy = "yyyy";
/*   21:     */   static final String gmd = "--MM-dd";
/*   22:     */   static final String gm = "--MM--";
/*   23:     */   static final String gd = "---dd";
/*   24:     */   static final String t = "HH:mm:ss";
/*   25:     */   static final String EMPTY_STR = "";
/*   26:     */   
/*   27:     */   public static String dateTime()
/*   28:     */   {
/*   29:  78 */     Calendar cal = Calendar.getInstance();
/*   30:  79 */     Date datetime = cal.getTime();
/*   31:     */     
/*   32:  81 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
/*   33:     */     
/*   34:  83 */     StringBuffer buff = new StringBuffer(dateFormat.format(datetime));
/*   35:     */     
/*   36:     */ 
/*   37:  86 */     int offset = cal.get(15) + cal.get(16);
/*   38:  89 */     if (offset == 0)
/*   39:     */     {
/*   40:  90 */       buff.append("Z");
/*   41:     */     }
/*   42:     */     else
/*   43:     */     {
/*   44:  94 */       int hrs = offset / 3600000;
/*   45:     */       
/*   46:  96 */       int min = offset % 3600000;
/*   47:  97 */       char posneg = hrs < 0 ? '-' : '+';
/*   48:  98 */       buff.append(posneg + formatDigits(hrs) + ':' + formatDigits(min));
/*   49:     */     }
/*   50: 100 */     return buff.toString();
/*   51:     */   }
/*   52:     */   
/*   53:     */   private static String formatDigits(int q)
/*   54:     */   {
/*   55: 110 */     String dd = String.valueOf(Math.abs(q));
/*   56: 111 */     return dd.length() == 1 ? '0' + dd : dd;
/*   57:     */   }
/*   58:     */   
/*   59:     */   public static String date(String datetimeIn)
/*   60:     */     throws ParseException
/*   61:     */   {
/*   62: 138 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*   63: 139 */     String leader = edz[0];
/*   64: 140 */     String datetime = edz[1];
/*   65: 141 */     String zone = edz[2];
/*   66: 142 */     if ((datetime == null) || (zone == null)) {
/*   67: 143 */       return "";
/*   68:     */     }
/*   69: 145 */     String[] formatsIn = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd" };
/*   70: 146 */     String formatOut = "yyyy-MM-dd";
/*   71: 147 */     Date date = testFormats(datetime, formatsIn);
/*   72: 148 */     if (date == null) {
/*   73: 148 */       return "";
/*   74:     */     }
/*   75: 150 */     SimpleDateFormat dateFormat = new SimpleDateFormat(formatOut);
/*   76: 151 */     dateFormat.setLenient(false);
/*   77: 152 */     String dateOut = dateFormat.format(date);
/*   78: 153 */     if (dateOut.length() == 0) {
/*   79: 154 */       return "";
/*   80:     */     }
/*   81: 156 */     return leader + dateOut + zone;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public static String date()
/*   85:     */   {
/*   86: 165 */     String datetime = dateTime();
/*   87: 166 */     String date = datetime.substring(0, datetime.indexOf("T"));
/*   88: 167 */     String zone = datetime.substring(getZoneStart(datetime));
/*   89: 168 */     return date + zone;
/*   90:     */   }
/*   91:     */   
/*   92:     */   public static String time(String timeIn)
/*   93:     */     throws ParseException
/*   94:     */   {
/*   95: 196 */     String[] edz = getEraDatetimeZone(timeIn);
/*   96: 197 */     String time = edz[1];
/*   97: 198 */     String zone = edz[2];
/*   98: 199 */     if ((time == null) || (zone == null)) {
/*   99: 200 */       return "";
/*  100:     */     }
/*  101: 202 */     String[] formatsIn = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss" };
/*  102: 203 */     String formatOut = "HH:mm:ss";
/*  103: 204 */     Date date = testFormats(time, formatsIn);
/*  104: 205 */     if (date == null) {
/*  105: 205 */       return "";
/*  106:     */     }
/*  107: 206 */     SimpleDateFormat dateFormat = new SimpleDateFormat(formatOut);
/*  108: 207 */     String out = dateFormat.format(date);
/*  109: 208 */     return out + zone;
/*  110:     */   }
/*  111:     */   
/*  112:     */   public static String time()
/*  113:     */   {
/*  114: 216 */     String datetime = dateTime();
/*  115: 217 */     String time = datetime.substring(datetime.indexOf("T") + 1);
/*  116:     */     
/*  117:     */ 
/*  118:     */ 
/*  119:     */ 
/*  120:     */ 
/*  121:     */ 
/*  122:     */ 
/*  123: 225 */     return time;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public static double year(String datetimeIn)
/*  127:     */     throws ParseException
/*  128:     */   {
/*  129: 246 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  130: 247 */     boolean ad = edz[0].length() == 0;
/*  131: 248 */     String datetime = edz[1];
/*  132: 249 */     if (datetime == null) {
/*  133: 250 */       return (0.0D / 0.0D);
/*  134:     */     }
/*  135: 252 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "yyyy" };
/*  136: 253 */     double yr = getNumber(datetime, formats, 1);
/*  137: 254 */     if ((ad) || (yr == (0.0D / 0.0D))) {
/*  138: 255 */       return yr;
/*  139:     */     }
/*  140: 257 */     return -yr;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public static double year()
/*  144:     */   {
/*  145: 265 */     Calendar cal = Calendar.getInstance();
/*  146: 266 */     return cal.get(1);
/*  147:     */   }
/*  148:     */   
/*  149:     */   public static double monthInYear(String datetimeIn)
/*  150:     */     throws ParseException
/*  151:     */   {
/*  152: 288 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  153: 289 */     String datetime = edz[1];
/*  154: 290 */     if (datetime == null) {
/*  155: 291 */       return (0.0D / 0.0D);
/*  156:     */     }
/*  157: 293 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "--MM--", "--MM-dd" };
/*  158: 294 */     return getNumber(datetime, formats, 2) + 1.0D;
/*  159:     */   }
/*  160:     */   
/*  161:     */   public static double monthInYear()
/*  162:     */   {
/*  163: 302 */     Calendar cal = Calendar.getInstance();
/*  164: 303 */     return cal.get(2) + 1;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public static double weekInYear(String datetimeIn)
/*  168:     */     throws ParseException
/*  169:     */   {
/*  170: 322 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  171: 323 */     String datetime = edz[1];
/*  172: 324 */     if (datetime == null) {
/*  173: 325 */       return (0.0D / 0.0D);
/*  174:     */     }
/*  175: 327 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd" };
/*  176: 328 */     return getNumber(datetime, formats, 3);
/*  177:     */   }
/*  178:     */   
/*  179:     */   public static double weekInYear()
/*  180:     */   {
/*  181: 336 */     Calendar cal = Calendar.getInstance();
/*  182: 337 */     return cal.get(3);
/*  183:     */   }
/*  184:     */   
/*  185:     */   public static double dayInYear(String datetimeIn)
/*  186:     */     throws ParseException
/*  187:     */   {
/*  188: 356 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  189: 357 */     String datetime = edz[1];
/*  190: 358 */     if (datetime == null) {
/*  191: 359 */       return (0.0D / 0.0D);
/*  192:     */     }
/*  193: 361 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd" };
/*  194: 362 */     return getNumber(datetime, formats, 6);
/*  195:     */   }
/*  196:     */   
/*  197:     */   public static double dayInYear()
/*  198:     */   {
/*  199: 370 */     Calendar cal = Calendar.getInstance();
/*  200: 371 */     return cal.get(6);
/*  201:     */   }
/*  202:     */   
/*  203:     */   public static double dayInMonth(String datetimeIn)
/*  204:     */     throws ParseException
/*  205:     */   {
/*  206: 393 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  207: 394 */     String datetime = edz[1];
/*  208: 395 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "--MM-dd", "---dd" };
/*  209: 396 */     double day = getNumber(datetime, formats, 5);
/*  210: 397 */     return day;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public static double dayInMonth()
/*  214:     */   {
/*  215: 405 */     Calendar cal = Calendar.getInstance();
/*  216: 406 */     return cal.get(5);
/*  217:     */   }
/*  218:     */   
/*  219:     */   public static double dayOfWeekInMonth(String datetimeIn)
/*  220:     */     throws ParseException
/*  221:     */   {
/*  222: 426 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  223: 427 */     String datetime = edz[1];
/*  224: 428 */     if (datetime == null) {
/*  225: 429 */       return (0.0D / 0.0D);
/*  226:     */     }
/*  227: 431 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd" };
/*  228: 432 */     return getNumber(datetime, formats, 8);
/*  229:     */   }
/*  230:     */   
/*  231:     */   public static double dayOfWeekInMonth()
/*  232:     */   {
/*  233: 440 */     Calendar cal = Calendar.getInstance();
/*  234: 441 */     return cal.get(8);
/*  235:     */   }
/*  236:     */   
/*  237:     */   public static double dayInWeek(String datetimeIn)
/*  238:     */     throws ParseException
/*  239:     */   {
/*  240: 462 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  241: 463 */     String datetime = edz[1];
/*  242: 464 */     if (datetime == null) {
/*  243: 465 */       return (0.0D / 0.0D);
/*  244:     */     }
/*  245: 467 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd" };
/*  246: 468 */     return getNumber(datetime, formats, 7);
/*  247:     */   }
/*  248:     */   
/*  249:     */   public static double dayInWeek()
/*  250:     */   {
/*  251: 476 */     Calendar cal = Calendar.getInstance();
/*  252: 477 */     return cal.get(7);
/*  253:     */   }
/*  254:     */   
/*  255:     */   public static double hourInDay(String datetimeIn)
/*  256:     */     throws ParseException
/*  257:     */   {
/*  258: 496 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  259: 497 */     String datetime = edz[1];
/*  260: 498 */     if (datetime == null) {
/*  261: 499 */       return (0.0D / 0.0D);
/*  262:     */     }
/*  263: 501 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "HH:mm:ss" };
/*  264: 502 */     return getNumber(datetime, formats, 11);
/*  265:     */   }
/*  266:     */   
/*  267:     */   public static double hourInDay()
/*  268:     */   {
/*  269: 510 */     Calendar cal = Calendar.getInstance();
/*  270: 511 */     return cal.get(11);
/*  271:     */   }
/*  272:     */   
/*  273:     */   public static double minuteInHour(String datetimeIn)
/*  274:     */     throws ParseException
/*  275:     */   {
/*  276: 530 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  277: 531 */     String datetime = edz[1];
/*  278: 532 */     if (datetime == null) {
/*  279: 533 */       return (0.0D / 0.0D);
/*  280:     */     }
/*  281: 535 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "HH:mm:ss" };
/*  282: 536 */     return getNumber(datetime, formats, 12);
/*  283:     */   }
/*  284:     */   
/*  285:     */   public static double minuteInHour()
/*  286:     */   {
/*  287: 544 */     Calendar cal = Calendar.getInstance();
/*  288: 545 */     return cal.get(12);
/*  289:     */   }
/*  290:     */   
/*  291:     */   public static double secondInMinute(String datetimeIn)
/*  292:     */     throws ParseException
/*  293:     */   {
/*  294: 564 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  295: 565 */     String datetime = edz[1];
/*  296: 566 */     if (datetime == null) {
/*  297: 567 */       return (0.0D / 0.0D);
/*  298:     */     }
/*  299: 569 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "HH:mm:ss" };
/*  300: 570 */     return getNumber(datetime, formats, 13);
/*  301:     */   }
/*  302:     */   
/*  303:     */   public static double secondInMinute()
/*  304:     */   {
/*  305: 578 */     Calendar cal = Calendar.getInstance();
/*  306: 579 */     return cal.get(13);
/*  307:     */   }
/*  308:     */   
/*  309:     */   public static XObject leapYear(String datetimeIn)
/*  310:     */     throws ParseException
/*  311:     */   {
/*  312: 600 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  313: 601 */     String datetime = edz[1];
/*  314: 602 */     if (datetime == null) {
/*  315: 603 */       return new XNumber((0.0D / 0.0D));
/*  316:     */     }
/*  317: 605 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "yyyy" };
/*  318: 606 */     double dbl = getNumber(datetime, formats, 1);
/*  319: 607 */     if (dbl == (0.0D / 0.0D)) {
/*  320: 608 */       return new XNumber((0.0D / 0.0D));
/*  321:     */     }
/*  322: 609 */     int yr = (int)dbl;
/*  323: 610 */     return new XBoolean((yr % 400 == 0) || ((yr % 100 != 0) && (yr % 4 == 0)));
/*  324:     */   }
/*  325:     */   
/*  326:     */   public static boolean leapYear()
/*  327:     */   {
/*  328: 618 */     Calendar cal = Calendar.getInstance();
/*  329: 619 */     int yr = cal.get(1);
/*  330: 620 */     return (yr % 400 == 0) || ((yr % 100 != 0) && (yr % 4 == 0));
/*  331:     */   }
/*  332:     */   
/*  333:     */   public static String monthName(String datetimeIn)
/*  334:     */     throws ParseException
/*  335:     */   {
/*  336: 645 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  337: 646 */     String datetime = edz[1];
/*  338: 647 */     if (datetime == null) {
/*  339: 648 */       return "";
/*  340:     */     }
/*  341: 650 */     String[] formatsIn = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "--MM--" };
/*  342: 651 */     String formatOut = "MMMM";
/*  343: 652 */     return getNameOrAbbrev(datetimeIn, formatsIn, formatOut);
/*  344:     */   }
/*  345:     */   
/*  346:     */   public static String monthName()
/*  347:     */   {
/*  348: 660 */     String format = "MMMM";
/*  349: 661 */     return getNameOrAbbrev(format);
/*  350:     */   }
/*  351:     */   
/*  352:     */   public static String monthAbbreviation(String datetimeIn)
/*  353:     */     throws ParseException
/*  354:     */   {
/*  355: 687 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  356: 688 */     String datetime = edz[1];
/*  357: 689 */     if (datetime == null) {
/*  358: 690 */       return "";
/*  359:     */     }
/*  360: 692 */     String[] formatsIn = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "--MM--" };
/*  361: 693 */     String formatOut = "MMM";
/*  362: 694 */     return getNameOrAbbrev(datetimeIn, formatsIn, formatOut);
/*  363:     */   }
/*  364:     */   
/*  365:     */   public static String monthAbbreviation()
/*  366:     */   {
/*  367: 702 */     String format = "MMM";
/*  368: 703 */     return getNameOrAbbrev(format);
/*  369:     */   }
/*  370:     */   
/*  371:     */   public static String dayName(String datetimeIn)
/*  372:     */     throws ParseException
/*  373:     */   {
/*  374: 727 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  375: 728 */     String datetime = edz[1];
/*  376: 729 */     if (datetime == null) {
/*  377: 730 */       return "";
/*  378:     */     }
/*  379: 732 */     String[] formatsIn = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd" };
/*  380: 733 */     String formatOut = "EEEE";
/*  381: 734 */     return getNameOrAbbrev(datetimeIn, formatsIn, formatOut);
/*  382:     */   }
/*  383:     */   
/*  384:     */   public static String dayName()
/*  385:     */   {
/*  386: 742 */     String format = "EEEE";
/*  387: 743 */     return getNameOrAbbrev(format);
/*  388:     */   }
/*  389:     */   
/*  390:     */   public static String dayAbbreviation(String datetimeIn)
/*  391:     */     throws ParseException
/*  392:     */   {
/*  393: 767 */     String[] edz = getEraDatetimeZone(datetimeIn);
/*  394: 768 */     String datetime = edz[1];
/*  395: 769 */     if (datetime == null) {
/*  396: 770 */       return "";
/*  397:     */     }
/*  398: 772 */     String[] formatsIn = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd" };
/*  399: 773 */     String formatOut = "EEE";
/*  400: 774 */     return getNameOrAbbrev(datetimeIn, formatsIn, formatOut);
/*  401:     */   }
/*  402:     */   
/*  403:     */   public static String dayAbbreviation()
/*  404:     */   {
/*  405: 782 */     String format = "EEE";
/*  406: 783 */     return getNameOrAbbrev(format);
/*  407:     */   }
/*  408:     */   
/*  409:     */   private static String[] getEraDatetimeZone(String in)
/*  410:     */   {
/*  411: 793 */     String leader = "";
/*  412: 794 */     String datetime = in;
/*  413: 795 */     String zone = "";
/*  414: 796 */     if ((in.charAt(0) == '-') && (!in.startsWith("--")))
/*  415:     */     {
/*  416: 798 */       leader = "-";
/*  417: 799 */       datetime = in.substring(1);
/*  418:     */     }
/*  419: 801 */     int z = getZoneStart(datetime);
/*  420: 802 */     if (z > 0)
/*  421:     */     {
/*  422: 804 */       zone = datetime.substring(z);
/*  423: 805 */       datetime = datetime.substring(0, z);
/*  424:     */     }
/*  425: 807 */     else if (z == -2)
/*  426:     */     {
/*  427: 808 */       zone = null;
/*  428:     */     }
/*  429: 810 */     return new String[] { leader, datetime, zone };
/*  430:     */   }
/*  431:     */   
/*  432:     */   private static int getZoneStart(String datetime)
/*  433:     */   {
/*  434: 821 */     if (datetime.indexOf("Z") == datetime.length() - 1) {
/*  435: 822 */       return datetime.length() - 1;
/*  436:     */     }
/*  437: 823 */     if ((datetime.length() >= 6) && (datetime.charAt(datetime.length() - 3) == ':') && ((datetime.charAt(datetime.length() - 6) == '+') || (datetime.charAt(datetime.length() - 6) == '-'))) {
/*  438:     */       try
/*  439:     */       {
/*  440: 830 */         SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
/*  441: 831 */         dateFormat.setLenient(false);
/*  442: 832 */         Date d = dateFormat.parse(datetime.substring(datetime.length() - 5));
/*  443: 833 */         return datetime.length() - 6;
/*  444:     */       }
/*  445:     */       catch (ParseException pe)
/*  446:     */       {
/*  447: 837 */         System.out.println("ParseException " + pe.getErrorOffset());
/*  448: 838 */         return -2;
/*  449:     */       }
/*  450:     */     }
/*  451: 842 */     return -1;
/*  452:     */   }
/*  453:     */   
/*  454:     */   private static Date testFormats(String in, String[] formats)
/*  455:     */     throws ParseException
/*  456:     */   {
/*  457: 852 */     for (int i = 0; i < formats.length; i++) {
/*  458:     */       try
/*  459:     */       {
/*  460: 856 */         SimpleDateFormat dateFormat = new SimpleDateFormat(formats[i]);
/*  461: 857 */         dateFormat.setLenient(false);
/*  462: 858 */         return dateFormat.parse(in);
/*  463:     */       }
/*  464:     */       catch (ParseException pe) {}
/*  465:     */     }
/*  466: 864 */     return null;
/*  467:     */   }
/*  468:     */   
/*  469:     */   private static double getNumber(String in, String[] formats, int calField)
/*  470:     */     throws ParseException
/*  471:     */   {
/*  472: 875 */     Calendar cal = Calendar.getInstance();
/*  473: 876 */     cal.setLenient(false);
/*  474:     */     
/*  475: 878 */     Date date = testFormats(in, formats);
/*  476: 879 */     if (date == null) {
/*  477: 879 */       return (0.0D / 0.0D);
/*  478:     */     }
/*  479: 880 */     cal.setTime(date);
/*  480: 881 */     return cal.get(calField);
/*  481:     */   }
/*  482:     */   
/*  483:     */   private static String getNameOrAbbrev(String in, String[] formatsIn, String formatOut)
/*  484:     */     throws ParseException
/*  485:     */   {
/*  486: 892 */     for (int i = 0; i < formatsIn.length; i++) {
/*  487:     */       try
/*  488:     */       {
/*  489: 896 */         SimpleDateFormat dateFormat = new SimpleDateFormat(formatsIn[i], Locale.ENGLISH);
/*  490: 897 */         dateFormat.setLenient(false);
/*  491: 898 */         Date dt = dateFormat.parse(in);
/*  492: 899 */         dateFormat.applyPattern(formatOut);
/*  493: 900 */         return dateFormat.format(dt);
/*  494:     */       }
/*  495:     */       catch (ParseException pe) {}
/*  496:     */     }
/*  497: 906 */     return "";
/*  498:     */   }
/*  499:     */   
/*  500:     */   private static String getNameOrAbbrev(String format)
/*  501:     */   {
/*  502: 914 */     Calendar cal = Calendar.getInstance();
/*  503: 915 */     SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
/*  504: 916 */     return dateFormat.format(cal.getTime());
/*  505:     */   }
/*  506:     */   
/*  507:     */   public static String formatDate(String dateTime, String pattern)
/*  508:     */   {
/*  509: 957 */     String yearSymbols = "Gy";
/*  510: 958 */     String monthSymbols = "M";
/*  511: 959 */     String daySymbols = "dDEFwW";
/*  512:     */     TimeZone timeZone;
/*  513:     */     String zone;
/*  514: 965 */     if ((dateTime.endsWith("Z")) || (dateTime.endsWith("z")))
/*  515:     */     {
/*  516: 967 */       timeZone = TimeZone.getTimeZone("GMT");
/*  517: 968 */       dateTime = dateTime.substring(0, dateTime.length() - 1) + "GMT";
/*  518: 969 */       zone = "z";
/*  519:     */     }
/*  520: 971 */     else if ((dateTime.length() >= 6) && (dateTime.charAt(dateTime.length() - 3) == ':') && ((dateTime.charAt(dateTime.length() - 6) == '+') || (dateTime.charAt(dateTime.length() - 6) == '-')))
/*  521:     */     {
/*  522: 976 */       String offset = dateTime.substring(dateTime.length() - 6);
/*  523: 978 */       if (("+00:00".equals(offset)) || ("-00:00".equals(offset))) {
/*  524: 980 */         timeZone = TimeZone.getTimeZone("GMT");
/*  525:     */       } else {
/*  526: 984 */         timeZone = TimeZone.getTimeZone("GMT" + offset);
/*  527:     */       }
/*  528: 986 */       zone = "z";
/*  529:     */       
/*  530:     */ 
/*  531: 989 */       dateTime = dateTime.substring(0, dateTime.length() - 6) + "GMT" + offset;
/*  532:     */     }
/*  533:     */     else
/*  534:     */     {
/*  535: 994 */       timeZone = TimeZone.getDefault();
/*  536: 995 */       zone = "";
/*  537:     */     }
/*  538: 999 */     String[] formats = { "yyyy-MM-dd'T'HH:mm:ss" + zone, "yyyy-MM-dd", "yyyy-MM", "yyyy" };
/*  539:     */     try
/*  540:     */     {
/*  541:1006 */       SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm:ss" + zone);
/*  542:1007 */       inFormat.setLenient(false);
/*  543:1008 */       Date d = inFormat.parse(dateTime);
/*  544:1009 */       SimpleDateFormat outFormat = new SimpleDateFormat(strip("GyMdDEFwW", pattern));
/*  545:     */       
/*  546:1011 */       outFormat.setTimeZone(timeZone);
/*  547:1012 */       return outFormat.format(d);
/*  548:     */     }
/*  549:     */     catch (ParseException pe)
/*  550:     */     {
/*  551:1019 */       for (int i = 0; i < formats.length; i++) {
/*  552:     */         try
/*  553:     */         {
/*  554:1023 */           SimpleDateFormat inFormat = new SimpleDateFormat(formats[i]);
/*  555:1024 */           inFormat.setLenient(false);
/*  556:1025 */           Date d = inFormat.parse(dateTime);
/*  557:1026 */           SimpleDateFormat outFormat = new SimpleDateFormat(pattern);
/*  558:1027 */           outFormat.setTimeZone(timeZone);
/*  559:1028 */           return outFormat.format(d);
/*  560:     */         }
/*  561:     */         catch (ParseException pe) {}
/*  562:     */       }
/*  563:     */       try
/*  564:     */       {
/*  565:1041 */         SimpleDateFormat inFormat = new SimpleDateFormat("--MM-dd");
/*  566:1042 */         inFormat.setLenient(false);
/*  567:1043 */         Date d = inFormat.parse(dateTime);
/*  568:1044 */         SimpleDateFormat outFormat = new SimpleDateFormat(strip("Gy", pattern));
/*  569:1045 */         outFormat.setTimeZone(timeZone);
/*  570:1046 */         return outFormat.format(d);
/*  571:     */       }
/*  572:     */       catch (ParseException pe)
/*  573:     */       {
/*  574:     */         try
/*  575:     */         {
/*  576:1053 */           SimpleDateFormat inFormat = new SimpleDateFormat("--MM--");
/*  577:1054 */           inFormat.setLenient(false);
/*  578:1055 */           Date d = inFormat.parse(dateTime);
/*  579:1056 */           SimpleDateFormat outFormat = new SimpleDateFormat(strip("Gy", pattern));
/*  580:1057 */           outFormat.setTimeZone(timeZone);
/*  581:1058 */           return outFormat.format(d);
/*  582:     */         }
/*  583:     */         catch (ParseException pe)
/*  584:     */         {
/*  585:     */           try
/*  586:     */           {
/*  587:1065 */             SimpleDateFormat inFormat = new SimpleDateFormat("---dd");
/*  588:1066 */             inFormat.setLenient(false);
/*  589:1067 */             Date d = inFormat.parse(dateTime);
/*  590:1068 */             SimpleDateFormat outFormat = new SimpleDateFormat(strip("GyM", pattern));
/*  591:1069 */             outFormat.setTimeZone(timeZone);
/*  592:1070 */             return outFormat.format(d);
/*  593:     */           }
/*  594:     */           catch (ParseException pe) {}
/*  595:     */         }
/*  596:     */       }
/*  597:     */     }
/*  598:1075 */     return "";
/*  599:     */   }
/*  600:     */   
/*  601:     */   private static String strip(String symbols, String pattern)
/*  602:     */   {
/*  603:1086 */     int quoteSemaphore = 0;
/*  604:1087 */     int i = 0;
/*  605:1088 */     StringBuffer result = new StringBuffer(pattern.length());
/*  606:1090 */     while (i < pattern.length())
/*  607:     */     {
/*  608:1092 */       char ch = pattern.charAt(i);
/*  609:1093 */       if (ch == '\'')
/*  610:     */       {
/*  611:1097 */         int endQuote = pattern.indexOf('\'', i + 1);
/*  612:1098 */         if (endQuote == -1) {
/*  613:1100 */           endQuote = pattern.length();
/*  614:     */         }
/*  615:1102 */         result.append(pattern.substring(i, endQuote));
/*  616:1103 */         i = endQuote++;
/*  617:     */       }
/*  618:1105 */       else if (symbols.indexOf(ch) > -1)
/*  619:     */       {
/*  620:1108 */         i++;
/*  621:     */       }
/*  622:     */       else
/*  623:     */       {
/*  624:1112 */         result.append(ch);
/*  625:1113 */         i++;
/*  626:     */       }
/*  627:     */     }
/*  628:1116 */     return result.toString();
/*  629:     */   }
/*  630:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.ExsltDatetime
 * JD-Core Version:    0.7.0.1
 */