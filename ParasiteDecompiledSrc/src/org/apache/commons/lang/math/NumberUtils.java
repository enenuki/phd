/*    1:     */ package org.apache.commons.lang.math;
/*    2:     */ 
/*    3:     */ import java.math.BigDecimal;
/*    4:     */ import java.math.BigInteger;
/*    5:     */ import org.apache.commons.lang.StringUtils;
/*    6:     */ 
/*    7:     */ public class NumberUtils
/*    8:     */ {
/*    9:  41 */   public static final Long LONG_ZERO = new Long(0L);
/*   10:  43 */   public static final Long LONG_ONE = new Long(1L);
/*   11:  45 */   public static final Long LONG_MINUS_ONE = new Long(-1L);
/*   12:  47 */   public static final Integer INTEGER_ZERO = new Integer(0);
/*   13:  49 */   public static final Integer INTEGER_ONE = new Integer(1);
/*   14:  51 */   public static final Integer INTEGER_MINUS_ONE = new Integer(-1);
/*   15:  53 */   public static final Short SHORT_ZERO = new Short((short)0);
/*   16:  55 */   public static final Short SHORT_ONE = new Short((short)1);
/*   17:  57 */   public static final Short SHORT_MINUS_ONE = new Short((short)-1);
/*   18:  59 */   public static final Byte BYTE_ZERO = new Byte((byte)0);
/*   19:  61 */   public static final Byte BYTE_ONE = new Byte((byte)1);
/*   20:  63 */   public static final Byte BYTE_MINUS_ONE = new Byte((byte)-1);
/*   21:  65 */   public static final Double DOUBLE_ZERO = new Double(0.0D);
/*   22:  67 */   public static final Double DOUBLE_ONE = new Double(1.0D);
/*   23:  69 */   public static final Double DOUBLE_MINUS_ONE = new Double(-1.0D);
/*   24:  71 */   public static final Float FLOAT_ZERO = new Float(0.0F);
/*   25:  73 */   public static final Float FLOAT_ONE = new Float(1.0F);
/*   26:  75 */   public static final Float FLOAT_MINUS_ONE = new Float(-1.0F);
/*   27:     */   
/*   28:     */   /**
/*   29:     */    * @deprecated
/*   30:     */    */
/*   31:     */   public static int stringToInt(String str)
/*   32:     */   {
/*   33: 108 */     return toInt(str);
/*   34:     */   }
/*   35:     */   
/*   36:     */   public static int toInt(String str)
/*   37:     */   {
/*   38: 129 */     return toInt(str, 0);
/*   39:     */   }
/*   40:     */   
/*   41:     */   /**
/*   42:     */    * @deprecated
/*   43:     */    */
/*   44:     */   public static int stringToInt(String str, int defaultValue)
/*   45:     */   {
/*   46: 151 */     return toInt(str, defaultValue);
/*   47:     */   }
/*   48:     */   
/*   49:     */   public static int toInt(String str, int defaultValue)
/*   50:     */   {
/*   51: 172 */     if (str == null) {
/*   52: 173 */       return defaultValue;
/*   53:     */     }
/*   54:     */     try
/*   55:     */     {
/*   56: 176 */       return Integer.parseInt(str);
/*   57:     */     }
/*   58:     */     catch (NumberFormatException nfe) {}
/*   59: 178 */     return defaultValue;
/*   60:     */   }
/*   61:     */   
/*   62:     */   public static long toLong(String str)
/*   63:     */   {
/*   64: 200 */     return toLong(str, 0L);
/*   65:     */   }
/*   66:     */   
/*   67:     */   public static long toLong(String str, long defaultValue)
/*   68:     */   {
/*   69: 221 */     if (str == null) {
/*   70: 222 */       return defaultValue;
/*   71:     */     }
/*   72:     */     try
/*   73:     */     {
/*   74: 225 */       return Long.parseLong(str);
/*   75:     */     }
/*   76:     */     catch (NumberFormatException nfe) {}
/*   77: 227 */     return defaultValue;
/*   78:     */   }
/*   79:     */   
/*   80:     */   public static float toFloat(String str)
/*   81:     */   {
/*   82: 250 */     return toFloat(str, 0.0F);
/*   83:     */   }
/*   84:     */   
/*   85:     */   public static float toFloat(String str, float defaultValue)
/*   86:     */   {
/*   87: 273 */     if (str == null) {
/*   88: 274 */       return defaultValue;
/*   89:     */     }
/*   90:     */     try
/*   91:     */     {
/*   92: 277 */       return Float.parseFloat(str);
/*   93:     */     }
/*   94:     */     catch (NumberFormatException nfe) {}
/*   95: 279 */     return defaultValue;
/*   96:     */   }
/*   97:     */   
/*   98:     */   public static double toDouble(String str)
/*   99:     */   {
/*  100: 302 */     return toDouble(str, 0.0D);
/*  101:     */   }
/*  102:     */   
/*  103:     */   public static double toDouble(String str, double defaultValue)
/*  104:     */   {
/*  105: 325 */     if (str == null) {
/*  106: 326 */       return defaultValue;
/*  107:     */     }
/*  108:     */     try
/*  109:     */     {
/*  110: 329 */       return Double.parseDouble(str);
/*  111:     */     }
/*  112:     */     catch (NumberFormatException nfe) {}
/*  113: 331 */     return defaultValue;
/*  114:     */   }
/*  115:     */   
/*  116:     */   public static byte toByte(String str)
/*  117:     */   {
/*  118: 354 */     return toByte(str, (byte)0);
/*  119:     */   }
/*  120:     */   
/*  121:     */   public static byte toByte(String str, byte defaultValue)
/*  122:     */   {
/*  123: 375 */     if (str == null) {
/*  124: 376 */       return defaultValue;
/*  125:     */     }
/*  126:     */     try
/*  127:     */     {
/*  128: 379 */       return Byte.parseByte(str);
/*  129:     */     }
/*  130:     */     catch (NumberFormatException nfe) {}
/*  131: 381 */     return defaultValue;
/*  132:     */   }
/*  133:     */   
/*  134:     */   public static short toShort(String str)
/*  135:     */   {
/*  136: 403 */     return toShort(str, (short)0);
/*  137:     */   }
/*  138:     */   
/*  139:     */   public static short toShort(String str, short defaultValue)
/*  140:     */   {
/*  141: 424 */     if (str == null) {
/*  142: 425 */       return defaultValue;
/*  143:     */     }
/*  144:     */     try
/*  145:     */     {
/*  146: 428 */       return Short.parseShort(str);
/*  147:     */     }
/*  148:     */     catch (NumberFormatException nfe) {}
/*  149: 430 */     return defaultValue;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public static Number createNumber(String str)
/*  153:     */     throws NumberFormatException
/*  154:     */   {
/*  155: 497 */     if (str == null) {
/*  156: 498 */       return null;
/*  157:     */     }
/*  158: 500 */     if (StringUtils.isBlank(str)) {
/*  159: 501 */       throw new NumberFormatException("A blank string is not a valid number");
/*  160:     */     }
/*  161: 503 */     if (str.startsWith("--")) {
/*  162: 508 */       return null;
/*  163:     */     }
/*  164: 510 */     if ((str.startsWith("0x")) || (str.startsWith("-0x"))) {
/*  165: 511 */       return createInteger(str);
/*  166:     */     }
/*  167: 513 */     char lastChar = str.charAt(str.length() - 1);
/*  168:     */     
/*  169:     */ 
/*  170:     */ 
/*  171: 517 */     int decPos = str.indexOf('.');
/*  172: 518 */     int expPos = str.indexOf('e') + str.indexOf('E') + 1;
/*  173:     */     String mant;
/*  174:     */     String mant;
/*  175:     */     String dec;
/*  176: 520 */     if (decPos > -1)
/*  177:     */     {
/*  178:     */       String dec;
/*  179:     */       String dec;
/*  180: 522 */       if (expPos > -1)
/*  181:     */       {
/*  182: 523 */         if ((expPos < decPos) || (expPos > str.length())) {
/*  183: 524 */           throw new NumberFormatException(str + " is not a valid number.");
/*  184:     */         }
/*  185: 526 */         dec = str.substring(decPos + 1, expPos);
/*  186:     */       }
/*  187:     */       else
/*  188:     */       {
/*  189: 528 */         dec = str.substring(decPos + 1);
/*  190:     */       }
/*  191: 530 */       mant = str.substring(0, decPos);
/*  192:     */     }
/*  193:     */     else
/*  194:     */     {
/*  195:     */       String mant;
/*  196: 532 */       if (expPos > -1)
/*  197:     */       {
/*  198: 533 */         if (expPos > str.length()) {
/*  199: 534 */           throw new NumberFormatException(str + " is not a valid number.");
/*  200:     */         }
/*  201: 536 */         mant = str.substring(0, expPos);
/*  202:     */       }
/*  203:     */       else
/*  204:     */       {
/*  205: 538 */         mant = str;
/*  206:     */       }
/*  207: 540 */       dec = null;
/*  208:     */     }
/*  209: 542 */     if ((!Character.isDigit(lastChar)) && (lastChar != '.'))
/*  210:     */     {
/*  211:     */       String exp;
/*  212:     */       String exp;
/*  213: 543 */       if ((expPos > -1) && (expPos < str.length() - 1)) {
/*  214: 544 */         exp = str.substring(expPos + 1, str.length() - 1);
/*  215:     */       } else {
/*  216: 546 */         exp = null;
/*  217:     */       }
/*  218: 549 */       String numeric = str.substring(0, str.length() - 1);
/*  219: 550 */       boolean allZeros = (isAllZeros(mant)) && (isAllZeros(exp));
/*  220: 551 */       switch (lastChar)
/*  221:     */       {
/*  222:     */       case 'L': 
/*  223:     */       case 'l': 
/*  224: 554 */         if ((dec == null) && (exp == null) && (((numeric.charAt(0) == '-') && (isDigits(numeric.substring(1)))) || (isDigits(numeric)))) {
/*  225:     */           try
/*  226:     */           {
/*  227: 558 */             return createLong(numeric);
/*  228:     */           }
/*  229:     */           catch (NumberFormatException nfe)
/*  230:     */           {
/*  231: 562 */             return createBigInteger(numeric);
/*  232:     */           }
/*  233:     */         }
/*  234: 565 */         throw new NumberFormatException(str + " is not a valid number.");
/*  235:     */       case 'F': 
/*  236:     */       case 'f': 
/*  237:     */         try
/*  238:     */         {
/*  239: 569 */           Float f = createFloat(numeric);
/*  240: 570 */           if ((!f.isInfinite()) && ((f.floatValue() != 0.0F) || (allZeros))) {
/*  241: 573 */             return f;
/*  242:     */           }
/*  243:     */         }
/*  244:     */         catch (NumberFormatException nfe) {}
/*  245:     */       case 'D': 
/*  246:     */       case 'd': 
/*  247:     */         try
/*  248:     */         {
/*  249: 583 */           Double d = createDouble(numeric);
/*  250: 584 */           if ((!d.isInfinite()) && ((d.floatValue() != 0.0D) || (allZeros))) {
/*  251: 585 */             return d;
/*  252:     */           }
/*  253:     */         }
/*  254:     */         catch (NumberFormatException nfe) {}
/*  255:     */         try
/*  256:     */         {
/*  257: 591 */           return createBigDecimal(numeric);
/*  258:     */         }
/*  259:     */         catch (NumberFormatException e) {}
/*  260:     */       }
/*  261: 597 */       throw new NumberFormatException(str + " is not a valid number.");
/*  262:     */     }
/*  263:     */     String exp;
/*  264:     */     String exp;
/*  265: 603 */     if ((expPos > -1) && (expPos < str.length() - 1)) {
/*  266: 604 */       exp = str.substring(expPos + 1, str.length());
/*  267:     */     } else {
/*  268: 606 */       exp = null;
/*  269:     */     }
/*  270: 608 */     if ((dec == null) && (exp == null)) {
/*  271:     */       try
/*  272:     */       {
/*  273: 611 */         return createInteger(str);
/*  274:     */       }
/*  275:     */       catch (NumberFormatException nfe)
/*  276:     */       {
/*  277:     */         try
/*  278:     */         {
/*  279: 616 */           return createLong(str);
/*  280:     */         }
/*  281:     */         catch (NumberFormatException nfe)
/*  282:     */         {
/*  283: 620 */           return createBigInteger(str);
/*  284:     */         }
/*  285:     */       }
/*  286:     */     }
/*  287: 624 */     boolean allZeros = (isAllZeros(mant)) && (isAllZeros(exp));
/*  288:     */     try
/*  289:     */     {
/*  290: 626 */       Float f = createFloat(str);
/*  291: 627 */       if ((!f.isInfinite()) && ((f.floatValue() != 0.0F) || (allZeros))) {
/*  292: 628 */         return f;
/*  293:     */       }
/*  294:     */     }
/*  295:     */     catch (NumberFormatException nfe) {}
/*  296:     */     try
/*  297:     */     {
/*  298: 634 */       Double d = createDouble(str);
/*  299: 635 */       if ((!d.isInfinite()) && ((d.doubleValue() != 0.0D) || (allZeros))) {
/*  300: 636 */         return d;
/*  301:     */       }
/*  302:     */     }
/*  303:     */     catch (NumberFormatException nfe) {}
/*  304: 642 */     return createBigDecimal(str);
/*  305:     */   }
/*  306:     */   
/*  307:     */   private static boolean isAllZeros(String str)
/*  308:     */   {
/*  309: 657 */     if (str == null) {
/*  310: 658 */       return true;
/*  311:     */     }
/*  312: 660 */     for (int i = str.length() - 1; i >= 0; i--) {
/*  313: 661 */       if (str.charAt(i) != '0') {
/*  314: 662 */         return false;
/*  315:     */       }
/*  316:     */     }
/*  317: 665 */     return str.length() > 0;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public static Float createFloat(String str)
/*  321:     */   {
/*  322: 679 */     if (str == null) {
/*  323: 680 */       return null;
/*  324:     */     }
/*  325: 682 */     return Float.valueOf(str);
/*  326:     */   }
/*  327:     */   
/*  328:     */   public static Double createDouble(String str)
/*  329:     */   {
/*  330: 695 */     if (str == null) {
/*  331: 696 */       return null;
/*  332:     */     }
/*  333: 698 */     return Double.valueOf(str);
/*  334:     */   }
/*  335:     */   
/*  336:     */   public static Integer createInteger(String str)
/*  337:     */   {
/*  338: 712 */     if (str == null) {
/*  339: 713 */       return null;
/*  340:     */     }
/*  341: 716 */     return Integer.decode(str);
/*  342:     */   }
/*  343:     */   
/*  344:     */   public static Long createLong(String str)
/*  345:     */   {
/*  346: 729 */     if (str == null) {
/*  347: 730 */       return null;
/*  348:     */     }
/*  349: 732 */     return Long.valueOf(str);
/*  350:     */   }
/*  351:     */   
/*  352:     */   public static BigInteger createBigInteger(String str)
/*  353:     */   {
/*  354: 745 */     if (str == null) {
/*  355: 746 */       return null;
/*  356:     */     }
/*  357: 748 */     return new BigInteger(str);
/*  358:     */   }
/*  359:     */   
/*  360:     */   public static BigDecimal createBigDecimal(String str)
/*  361:     */   {
/*  362: 761 */     if (str == null) {
/*  363: 762 */       return null;
/*  364:     */     }
/*  365: 765 */     if (StringUtils.isBlank(str)) {
/*  366: 766 */       throw new NumberFormatException("A blank string is not a valid number");
/*  367:     */     }
/*  368: 768 */     return new BigDecimal(str);
/*  369:     */   }
/*  370:     */   
/*  371:     */   public static long min(long[] array)
/*  372:     */   {
/*  373: 783 */     if (array == null) {
/*  374: 784 */       throw new IllegalArgumentException("The Array must not be null");
/*  375:     */     }
/*  376: 785 */     if (array.length == 0) {
/*  377: 786 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  378:     */     }
/*  379: 790 */     long min = array[0];
/*  380: 791 */     for (int i = 1; i < array.length; i++) {
/*  381: 792 */       if (array[i] < min) {
/*  382: 793 */         min = array[i];
/*  383:     */       }
/*  384:     */     }
/*  385: 797 */     return min;
/*  386:     */   }
/*  387:     */   
/*  388:     */   public static int min(int[] array)
/*  389:     */   {
/*  390: 810 */     if (array == null) {
/*  391: 811 */       throw new IllegalArgumentException("The Array must not be null");
/*  392:     */     }
/*  393: 812 */     if (array.length == 0) {
/*  394: 813 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  395:     */     }
/*  396: 817 */     int min = array[0];
/*  397: 818 */     for (int j = 1; j < array.length; j++) {
/*  398: 819 */       if (array[j] < min) {
/*  399: 820 */         min = array[j];
/*  400:     */       }
/*  401:     */     }
/*  402: 824 */     return min;
/*  403:     */   }
/*  404:     */   
/*  405:     */   public static short min(short[] array)
/*  406:     */   {
/*  407: 837 */     if (array == null) {
/*  408: 838 */       throw new IllegalArgumentException("The Array must not be null");
/*  409:     */     }
/*  410: 839 */     if (array.length == 0) {
/*  411: 840 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  412:     */     }
/*  413: 844 */     short min = array[0];
/*  414: 845 */     for (int i = 1; i < array.length; i++) {
/*  415: 846 */       if (array[i] < min) {
/*  416: 847 */         min = array[i];
/*  417:     */       }
/*  418:     */     }
/*  419: 851 */     return min;
/*  420:     */   }
/*  421:     */   
/*  422:     */   public static byte min(byte[] array)
/*  423:     */   {
/*  424: 864 */     if (array == null) {
/*  425: 865 */       throw new IllegalArgumentException("The Array must not be null");
/*  426:     */     }
/*  427: 866 */     if (array.length == 0) {
/*  428: 867 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  429:     */     }
/*  430: 871 */     byte min = array[0];
/*  431: 872 */     for (int i = 1; i < array.length; i++) {
/*  432: 873 */       if (array[i] < min) {
/*  433: 874 */         min = array[i];
/*  434:     */       }
/*  435:     */     }
/*  436: 878 */     return min;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public static double min(double[] array)
/*  440:     */   {
/*  441: 892 */     if (array == null) {
/*  442: 893 */       throw new IllegalArgumentException("The Array must not be null");
/*  443:     */     }
/*  444: 894 */     if (array.length == 0) {
/*  445: 895 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  446:     */     }
/*  447: 899 */     double min = array[0];
/*  448: 900 */     for (int i = 1; i < array.length; i++)
/*  449:     */     {
/*  450: 901 */       if (Double.isNaN(array[i])) {
/*  451: 902 */         return (0.0D / 0.0D);
/*  452:     */       }
/*  453: 904 */       if (array[i] < min) {
/*  454: 905 */         min = array[i];
/*  455:     */       }
/*  456:     */     }
/*  457: 909 */     return min;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public static float min(float[] array)
/*  461:     */   {
/*  462: 923 */     if (array == null) {
/*  463: 924 */       throw new IllegalArgumentException("The Array must not be null");
/*  464:     */     }
/*  465: 925 */     if (array.length == 0) {
/*  466: 926 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  467:     */     }
/*  468: 930 */     float min = array[0];
/*  469: 931 */     for (int i = 1; i < array.length; i++)
/*  470:     */     {
/*  471: 932 */       if (Float.isNaN(array[i])) {
/*  472: 933 */         return (0.0F / 0.0F);
/*  473:     */       }
/*  474: 935 */       if (array[i] < min) {
/*  475: 936 */         min = array[i];
/*  476:     */       }
/*  477:     */     }
/*  478: 940 */     return min;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public static long max(long[] array)
/*  482:     */   {
/*  483: 955 */     if (array == null) {
/*  484: 956 */       throw new IllegalArgumentException("The Array must not be null");
/*  485:     */     }
/*  486: 957 */     if (array.length == 0) {
/*  487: 958 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  488:     */     }
/*  489: 962 */     long max = array[0];
/*  490: 963 */     for (int j = 1; j < array.length; j++) {
/*  491: 964 */       if (array[j] > max) {
/*  492: 965 */         max = array[j];
/*  493:     */       }
/*  494:     */     }
/*  495: 969 */     return max;
/*  496:     */   }
/*  497:     */   
/*  498:     */   public static int max(int[] array)
/*  499:     */   {
/*  500: 982 */     if (array == null) {
/*  501: 983 */       throw new IllegalArgumentException("The Array must not be null");
/*  502:     */     }
/*  503: 984 */     if (array.length == 0) {
/*  504: 985 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  505:     */     }
/*  506: 989 */     int max = array[0];
/*  507: 990 */     for (int j = 1; j < array.length; j++) {
/*  508: 991 */       if (array[j] > max) {
/*  509: 992 */         max = array[j];
/*  510:     */       }
/*  511:     */     }
/*  512: 996 */     return max;
/*  513:     */   }
/*  514:     */   
/*  515:     */   public static short max(short[] array)
/*  516:     */   {
/*  517:1009 */     if (array == null) {
/*  518:1010 */       throw new IllegalArgumentException("The Array must not be null");
/*  519:     */     }
/*  520:1011 */     if (array.length == 0) {
/*  521:1012 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  522:     */     }
/*  523:1016 */     short max = array[0];
/*  524:1017 */     for (int i = 1; i < array.length; i++) {
/*  525:1018 */       if (array[i] > max) {
/*  526:1019 */         max = array[i];
/*  527:     */       }
/*  528:     */     }
/*  529:1023 */     return max;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public static byte max(byte[] array)
/*  533:     */   {
/*  534:1036 */     if (array == null) {
/*  535:1037 */       throw new IllegalArgumentException("The Array must not be null");
/*  536:     */     }
/*  537:1038 */     if (array.length == 0) {
/*  538:1039 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  539:     */     }
/*  540:1043 */     byte max = array[0];
/*  541:1044 */     for (int i = 1; i < array.length; i++) {
/*  542:1045 */       if (array[i] > max) {
/*  543:1046 */         max = array[i];
/*  544:     */       }
/*  545:     */     }
/*  546:1050 */     return max;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public static double max(double[] array)
/*  550:     */   {
/*  551:1064 */     if (array == null) {
/*  552:1065 */       throw new IllegalArgumentException("The Array must not be null");
/*  553:     */     }
/*  554:1066 */     if (array.length == 0) {
/*  555:1067 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  556:     */     }
/*  557:1071 */     double max = array[0];
/*  558:1072 */     for (int j = 1; j < array.length; j++)
/*  559:     */     {
/*  560:1073 */       if (Double.isNaN(array[j])) {
/*  561:1074 */         return (0.0D / 0.0D);
/*  562:     */       }
/*  563:1076 */       if (array[j] > max) {
/*  564:1077 */         max = array[j];
/*  565:     */       }
/*  566:     */     }
/*  567:1081 */     return max;
/*  568:     */   }
/*  569:     */   
/*  570:     */   public static float max(float[] array)
/*  571:     */   {
/*  572:1095 */     if (array == null) {
/*  573:1096 */       throw new IllegalArgumentException("The Array must not be null");
/*  574:     */     }
/*  575:1097 */     if (array.length == 0) {
/*  576:1098 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  577:     */     }
/*  578:1102 */     float max = array[0];
/*  579:1103 */     for (int j = 1; j < array.length; j++)
/*  580:     */     {
/*  581:1104 */       if (Float.isNaN(array[j])) {
/*  582:1105 */         return (0.0F / 0.0F);
/*  583:     */       }
/*  584:1107 */       if (array[j] > max) {
/*  585:1108 */         max = array[j];
/*  586:     */       }
/*  587:     */     }
/*  588:1112 */     return max;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public static long min(long a, long b, long c)
/*  592:     */   {
/*  593:1126 */     if (b < a) {
/*  594:1127 */       a = b;
/*  595:     */     }
/*  596:1129 */     if (c < a) {
/*  597:1130 */       a = c;
/*  598:     */     }
/*  599:1132 */     return a;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public static int min(int a, int b, int c)
/*  603:     */   {
/*  604:1144 */     if (b < a) {
/*  605:1145 */       a = b;
/*  606:     */     }
/*  607:1147 */     if (c < a) {
/*  608:1148 */       a = c;
/*  609:     */     }
/*  610:1150 */     return a;
/*  611:     */   }
/*  612:     */   
/*  613:     */   public static short min(short a, short b, short c)
/*  614:     */   {
/*  615:1162 */     if (b < a) {
/*  616:1163 */       a = b;
/*  617:     */     }
/*  618:1165 */     if (c < a) {
/*  619:1166 */       a = c;
/*  620:     */     }
/*  621:1168 */     return a;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public static byte min(byte a, byte b, byte c)
/*  625:     */   {
/*  626:1180 */     if (b < a) {
/*  627:1181 */       a = b;
/*  628:     */     }
/*  629:1183 */     if (c < a) {
/*  630:1184 */       a = c;
/*  631:     */     }
/*  632:1186 */     return a;
/*  633:     */   }
/*  634:     */   
/*  635:     */   public static double min(double a, double b, double c)
/*  636:     */   {
/*  637:1202 */     return Math.min(Math.min(a, b), c);
/*  638:     */   }
/*  639:     */   
/*  640:     */   public static float min(float a, float b, float c)
/*  641:     */   {
/*  642:1218 */     return Math.min(Math.min(a, b), c);
/*  643:     */   }
/*  644:     */   
/*  645:     */   public static long max(long a, long b, long c)
/*  646:     */   {
/*  647:1232 */     if (b > a) {
/*  648:1233 */       a = b;
/*  649:     */     }
/*  650:1235 */     if (c > a) {
/*  651:1236 */       a = c;
/*  652:     */     }
/*  653:1238 */     return a;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public static int max(int a, int b, int c)
/*  657:     */   {
/*  658:1250 */     if (b > a) {
/*  659:1251 */       a = b;
/*  660:     */     }
/*  661:1253 */     if (c > a) {
/*  662:1254 */       a = c;
/*  663:     */     }
/*  664:1256 */     return a;
/*  665:     */   }
/*  666:     */   
/*  667:     */   public static short max(short a, short b, short c)
/*  668:     */   {
/*  669:1268 */     if (b > a) {
/*  670:1269 */       a = b;
/*  671:     */     }
/*  672:1271 */     if (c > a) {
/*  673:1272 */       a = c;
/*  674:     */     }
/*  675:1274 */     return a;
/*  676:     */   }
/*  677:     */   
/*  678:     */   public static byte max(byte a, byte b, byte c)
/*  679:     */   {
/*  680:1286 */     if (b > a) {
/*  681:1287 */       a = b;
/*  682:     */     }
/*  683:1289 */     if (c > a) {
/*  684:1290 */       a = c;
/*  685:     */     }
/*  686:1292 */     return a;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public static double max(double a, double b, double c)
/*  690:     */   {
/*  691:1308 */     return Math.max(Math.max(a, b), c);
/*  692:     */   }
/*  693:     */   
/*  694:     */   public static float max(float a, float b, float c)
/*  695:     */   {
/*  696:1324 */     return Math.max(Math.max(a, b), c);
/*  697:     */   }
/*  698:     */   
/*  699:     */   public static int compare(double lhs, double rhs)
/*  700:     */   {
/*  701:1363 */     if (lhs < rhs) {
/*  702:1364 */       return -1;
/*  703:     */     }
/*  704:1366 */     if (lhs > rhs) {
/*  705:1367 */       return 1;
/*  706:     */     }
/*  707:1373 */     long lhsBits = Double.doubleToLongBits(lhs);
/*  708:1374 */     long rhsBits = Double.doubleToLongBits(rhs);
/*  709:1375 */     if (lhsBits == rhsBits) {
/*  710:1376 */       return 0;
/*  711:     */     }
/*  712:1384 */     if (lhsBits < rhsBits) {
/*  713:1385 */       return -1;
/*  714:     */     }
/*  715:1387 */     return 1;
/*  716:     */   }
/*  717:     */   
/*  718:     */   public static int compare(float lhs, float rhs)
/*  719:     */   {
/*  720:1424 */     if (lhs < rhs) {
/*  721:1425 */       return -1;
/*  722:     */     }
/*  723:1427 */     if (lhs > rhs) {
/*  724:1428 */       return 1;
/*  725:     */     }
/*  726:1434 */     int lhsBits = Float.floatToIntBits(lhs);
/*  727:1435 */     int rhsBits = Float.floatToIntBits(rhs);
/*  728:1436 */     if (lhsBits == rhsBits) {
/*  729:1437 */       return 0;
/*  730:     */     }
/*  731:1445 */     if (lhsBits < rhsBits) {
/*  732:1446 */       return -1;
/*  733:     */     }
/*  734:1448 */     return 1;
/*  735:     */   }
/*  736:     */   
/*  737:     */   public static boolean isDigits(String str)
/*  738:     */   {
/*  739:1464 */     if (StringUtils.isEmpty(str)) {
/*  740:1465 */       return false;
/*  741:     */     }
/*  742:1467 */     for (int i = 0; i < str.length(); i++) {
/*  743:1468 */       if (!Character.isDigit(str.charAt(i))) {
/*  744:1469 */         return false;
/*  745:     */       }
/*  746:     */     }
/*  747:1472 */     return true;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public static boolean isNumber(String str)
/*  751:     */   {
/*  752:1489 */     if (StringUtils.isEmpty(str)) {
/*  753:1490 */       return false;
/*  754:     */     }
/*  755:1492 */     char[] chars = str.toCharArray();
/*  756:1493 */     int sz = chars.length;
/*  757:1494 */     boolean hasExp = false;
/*  758:1495 */     boolean hasDecPoint = false;
/*  759:1496 */     boolean allowSigns = false;
/*  760:1497 */     boolean foundDigit = false;
/*  761:     */     
/*  762:1499 */     int start = chars[0] == '-' ? 1 : 0;
/*  763:1500 */     if ((sz > start + 1) && 
/*  764:1501 */       (chars[start] == '0') && (chars[(start + 1)] == 'x'))
/*  765:     */     {
/*  766:1502 */       int i = start + 2;
/*  767:1503 */       if (i == sz) {
/*  768:1504 */         return false;
/*  769:     */       }
/*  770:1507 */       for (; i < chars.length; i++) {
/*  771:1508 */         if (((chars[i] < '0') || (chars[i] > '9')) && ((chars[i] < 'a') || (chars[i] > 'f')) && ((chars[i] < 'A') || (chars[i] > 'F'))) {
/*  772:1511 */           return false;
/*  773:     */         }
/*  774:     */       }
/*  775:1514 */       return true;
/*  776:     */     }
/*  777:1517 */     sz--;
/*  778:     */     
/*  779:1519 */     int i = start;
/*  780:1522 */     while ((i < sz) || ((i < sz + 1) && (allowSigns) && (!foundDigit)))
/*  781:     */     {
/*  782:1523 */       if ((chars[i] >= '0') && (chars[i] <= '9'))
/*  783:     */       {
/*  784:1524 */         foundDigit = true;
/*  785:1525 */         allowSigns = false;
/*  786:     */       }
/*  787:1527 */       else if (chars[i] == '.')
/*  788:     */       {
/*  789:1528 */         if ((hasDecPoint) || (hasExp)) {
/*  790:1530 */           return false;
/*  791:     */         }
/*  792:1532 */         hasDecPoint = true;
/*  793:     */       }
/*  794:1533 */       else if ((chars[i] == 'e') || (chars[i] == 'E'))
/*  795:     */       {
/*  796:1535 */         if (hasExp) {
/*  797:1537 */           return false;
/*  798:     */         }
/*  799:1539 */         if (!foundDigit) {
/*  800:1540 */           return false;
/*  801:     */         }
/*  802:1542 */         hasExp = true;
/*  803:1543 */         allowSigns = true;
/*  804:     */       }
/*  805:1544 */       else if ((chars[i] == '+') || (chars[i] == '-'))
/*  806:     */       {
/*  807:1545 */         if (!allowSigns) {
/*  808:1546 */           return false;
/*  809:     */         }
/*  810:1548 */         allowSigns = false;
/*  811:1549 */         foundDigit = false;
/*  812:     */       }
/*  813:     */       else
/*  814:     */       {
/*  815:1551 */         return false;
/*  816:     */       }
/*  817:1553 */       i++;
/*  818:     */     }
/*  819:1555 */     if (i < chars.length)
/*  820:     */     {
/*  821:1556 */       if ((chars[i] >= '0') && (chars[i] <= '9')) {
/*  822:1558 */         return true;
/*  823:     */       }
/*  824:1560 */       if ((chars[i] == 'e') || (chars[i] == 'E')) {
/*  825:1562 */         return false;
/*  826:     */       }
/*  827:1564 */       if (chars[i] == '.')
/*  828:     */       {
/*  829:1565 */         if ((hasDecPoint) || (hasExp)) {
/*  830:1567 */           return false;
/*  831:     */         }
/*  832:1570 */         return foundDigit;
/*  833:     */       }
/*  834:1572 */       if ((!allowSigns) && ((chars[i] == 'd') || (chars[i] == 'D') || (chars[i] == 'f') || (chars[i] == 'F'))) {
/*  835:1577 */         return foundDigit;
/*  836:     */       }
/*  837:1579 */       if ((chars[i] == 'l') || (chars[i] == 'L')) {
/*  838:1582 */         return (foundDigit) && (!hasExp);
/*  839:     */       }
/*  840:1585 */       return false;
/*  841:     */     }
/*  842:1589 */     return (!allowSigns) && (foundDigit);
/*  843:     */   }
/*  844:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.NumberUtils
 * JD-Core Version:    0.7.0.1
 */