/*    1:     */ package org.apache.commons.lang;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.Array;
/*    4:     */ import java.util.HashMap;
/*    5:     */ import java.util.Map;
/*    6:     */ import java.util.Map.Entry;
/*    7:     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*    8:     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*    9:     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*   10:     */ import org.apache.commons.lang.builder.ToStringStyle;
/*   11:     */ 
/*   12:     */ public class ArrayUtils
/*   13:     */ {
/*   14:  56 */   public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
/*   15:  60 */   public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
/*   16:  64 */   public static final String[] EMPTY_STRING_ARRAY = new String[0];
/*   17:  68 */   public static final long[] EMPTY_LONG_ARRAY = new long[0];
/*   18:  72 */   public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
/*   19:  76 */   public static final int[] EMPTY_INT_ARRAY = new int[0];
/*   20:  80 */   public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
/*   21:  84 */   public static final short[] EMPTY_SHORT_ARRAY = new short[0];
/*   22:  88 */   public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
/*   23:  92 */   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*   24:  96 */   public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
/*   25: 100 */   public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
/*   26: 104 */   public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
/*   27: 108 */   public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
/*   28: 112 */   public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
/*   29: 116 */   public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
/*   30: 120 */   public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
/*   31: 124 */   public static final char[] EMPTY_CHAR_ARRAY = new char[0];
/*   32: 128 */   public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
/*   33:     */   public static final int INDEX_NOT_FOUND = -1;
/*   34:     */   
/*   35:     */   public static String toString(Object array)
/*   36:     */   {
/*   37: 162 */     return toString(array, "{}");
/*   38:     */   }
/*   39:     */   
/*   40:     */   public static String toString(Object array, String stringIfNull)
/*   41:     */   {
/*   42: 178 */     if (array == null) {
/*   43: 179 */       return stringIfNull;
/*   44:     */     }
/*   45: 181 */     return new ToStringBuilder(array, ToStringStyle.SIMPLE_STYLE).append(array).toString();
/*   46:     */   }
/*   47:     */   
/*   48:     */   public static int hashCode(Object array)
/*   49:     */   {
/*   50: 193 */     return new HashCodeBuilder().append(array).toHashCode();
/*   51:     */   }
/*   52:     */   
/*   53:     */   public static boolean isEquals(Object array1, Object array2)
/*   54:     */   {
/*   55: 207 */     return new EqualsBuilder().append(array1, array2).isEquals();
/*   56:     */   }
/*   57:     */   
/*   58:     */   public static Map toMap(Object[] array)
/*   59:     */   {
/*   60: 238 */     if (array == null) {
/*   61: 239 */       return null;
/*   62:     */     }
/*   63: 241 */     Map map = new HashMap((int)(array.length * 1.5D));
/*   64: 242 */     for (int i = 0; i < array.length; i++)
/*   65:     */     {
/*   66: 243 */       Object object = array[i];
/*   67: 244 */       if ((object instanceof Map.Entry))
/*   68:     */       {
/*   69: 245 */         Map.Entry entry = (Map.Entry)object;
/*   70: 246 */         map.put(entry.getKey(), entry.getValue());
/*   71:     */       }
/*   72: 247 */       else if ((object instanceof Object[]))
/*   73:     */       {
/*   74: 248 */         Object[] entry = (Object[])object;
/*   75: 249 */         if (entry.length < 2) {
/*   76: 250 */           throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
/*   77:     */         }
/*   78: 254 */         map.put(entry[0], entry[1]);
/*   79:     */       }
/*   80:     */       else
/*   81:     */       {
/*   82: 256 */         throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
/*   83:     */       }
/*   84:     */     }
/*   85: 261 */     return map;
/*   86:     */   }
/*   87:     */   
/*   88:     */   public static Object[] clone(Object[] array)
/*   89:     */   {
/*   90: 279 */     if (array == null) {
/*   91: 280 */       return null;
/*   92:     */     }
/*   93: 282 */     return (Object[])array.clone();
/*   94:     */   }
/*   95:     */   
/*   96:     */   public static long[] clone(long[] array)
/*   97:     */   {
/*   98: 295 */     if (array == null) {
/*   99: 296 */       return null;
/*  100:     */     }
/*  101: 298 */     return (long[])array.clone();
/*  102:     */   }
/*  103:     */   
/*  104:     */   public static int[] clone(int[] array)
/*  105:     */   {
/*  106: 311 */     if (array == null) {
/*  107: 312 */       return null;
/*  108:     */     }
/*  109: 314 */     return (int[])array.clone();
/*  110:     */   }
/*  111:     */   
/*  112:     */   public static short[] clone(short[] array)
/*  113:     */   {
/*  114: 327 */     if (array == null) {
/*  115: 328 */       return null;
/*  116:     */     }
/*  117: 330 */     return (short[])array.clone();
/*  118:     */   }
/*  119:     */   
/*  120:     */   public static char[] clone(char[] array)
/*  121:     */   {
/*  122: 343 */     if (array == null) {
/*  123: 344 */       return null;
/*  124:     */     }
/*  125: 346 */     return (char[])array.clone();
/*  126:     */   }
/*  127:     */   
/*  128:     */   public static byte[] clone(byte[] array)
/*  129:     */   {
/*  130: 359 */     if (array == null) {
/*  131: 360 */       return null;
/*  132:     */     }
/*  133: 362 */     return (byte[])array.clone();
/*  134:     */   }
/*  135:     */   
/*  136:     */   public static double[] clone(double[] array)
/*  137:     */   {
/*  138: 375 */     if (array == null) {
/*  139: 376 */       return null;
/*  140:     */     }
/*  141: 378 */     return (double[])array.clone();
/*  142:     */   }
/*  143:     */   
/*  144:     */   public static float[] clone(float[] array)
/*  145:     */   {
/*  146: 391 */     if (array == null) {
/*  147: 392 */       return null;
/*  148:     */     }
/*  149: 394 */     return (float[])array.clone();
/*  150:     */   }
/*  151:     */   
/*  152:     */   public static boolean[] clone(boolean[] array)
/*  153:     */   {
/*  154: 407 */     if (array == null) {
/*  155: 408 */       return null;
/*  156:     */     }
/*  157: 410 */     return (boolean[])array.clone();
/*  158:     */   }
/*  159:     */   
/*  160:     */   public static Object[] nullToEmpty(Object[] array)
/*  161:     */   {
/*  162: 429 */     if ((array == null) || (array.length == 0)) {
/*  163: 430 */       return EMPTY_OBJECT_ARRAY;
/*  164:     */     }
/*  165: 432 */     return array;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public static String[] nullToEmpty(String[] array)
/*  169:     */   {
/*  170: 449 */     if ((array == null) || (array.length == 0)) {
/*  171: 450 */       return EMPTY_STRING_ARRAY;
/*  172:     */     }
/*  173: 452 */     return array;
/*  174:     */   }
/*  175:     */   
/*  176:     */   public static long[] nullToEmpty(long[] array)
/*  177:     */   {
/*  178: 469 */     if ((array == null) || (array.length == 0)) {
/*  179: 470 */       return EMPTY_LONG_ARRAY;
/*  180:     */     }
/*  181: 472 */     return array;
/*  182:     */   }
/*  183:     */   
/*  184:     */   public static int[] nullToEmpty(int[] array)
/*  185:     */   {
/*  186: 489 */     if ((array == null) || (array.length == 0)) {
/*  187: 490 */       return EMPTY_INT_ARRAY;
/*  188:     */     }
/*  189: 492 */     return array;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public static short[] nullToEmpty(short[] array)
/*  193:     */   {
/*  194: 509 */     if ((array == null) || (array.length == 0)) {
/*  195: 510 */       return EMPTY_SHORT_ARRAY;
/*  196:     */     }
/*  197: 512 */     return array;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public static char[] nullToEmpty(char[] array)
/*  201:     */   {
/*  202: 529 */     if ((array == null) || (array.length == 0)) {
/*  203: 530 */       return EMPTY_CHAR_ARRAY;
/*  204:     */     }
/*  205: 532 */     return array;
/*  206:     */   }
/*  207:     */   
/*  208:     */   public static byte[] nullToEmpty(byte[] array)
/*  209:     */   {
/*  210: 549 */     if ((array == null) || (array.length == 0)) {
/*  211: 550 */       return EMPTY_BYTE_ARRAY;
/*  212:     */     }
/*  213: 552 */     return array;
/*  214:     */   }
/*  215:     */   
/*  216:     */   public static double[] nullToEmpty(double[] array)
/*  217:     */   {
/*  218: 569 */     if ((array == null) || (array.length == 0)) {
/*  219: 570 */       return EMPTY_DOUBLE_ARRAY;
/*  220:     */     }
/*  221: 572 */     return array;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public static float[] nullToEmpty(float[] array)
/*  225:     */   {
/*  226: 589 */     if ((array == null) || (array.length == 0)) {
/*  227: 590 */       return EMPTY_FLOAT_ARRAY;
/*  228:     */     }
/*  229: 592 */     return array;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public static boolean[] nullToEmpty(boolean[] array)
/*  233:     */   {
/*  234: 609 */     if ((array == null) || (array.length == 0)) {
/*  235: 610 */       return EMPTY_BOOLEAN_ARRAY;
/*  236:     */     }
/*  237: 612 */     return array;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public static Long[] nullToEmpty(Long[] array)
/*  241:     */   {
/*  242: 629 */     if ((array == null) || (array.length == 0)) {
/*  243: 630 */       return EMPTY_LONG_OBJECT_ARRAY;
/*  244:     */     }
/*  245: 632 */     return array;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public static Integer[] nullToEmpty(Integer[] array)
/*  249:     */   {
/*  250: 649 */     if ((array == null) || (array.length == 0)) {
/*  251: 650 */       return EMPTY_INTEGER_OBJECT_ARRAY;
/*  252:     */     }
/*  253: 652 */     return array;
/*  254:     */   }
/*  255:     */   
/*  256:     */   public static Short[] nullToEmpty(Short[] array)
/*  257:     */   {
/*  258: 669 */     if ((array == null) || (array.length == 0)) {
/*  259: 670 */       return EMPTY_SHORT_OBJECT_ARRAY;
/*  260:     */     }
/*  261: 672 */     return array;
/*  262:     */   }
/*  263:     */   
/*  264:     */   public static Character[] nullToEmpty(Character[] array)
/*  265:     */   {
/*  266: 689 */     if ((array == null) || (array.length == 0)) {
/*  267: 690 */       return EMPTY_CHARACTER_OBJECT_ARRAY;
/*  268:     */     }
/*  269: 692 */     return array;
/*  270:     */   }
/*  271:     */   
/*  272:     */   public static Byte[] nullToEmpty(Byte[] array)
/*  273:     */   {
/*  274: 709 */     if ((array == null) || (array.length == 0)) {
/*  275: 710 */       return EMPTY_BYTE_OBJECT_ARRAY;
/*  276:     */     }
/*  277: 712 */     return array;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public static Double[] nullToEmpty(Double[] array)
/*  281:     */   {
/*  282: 729 */     if ((array == null) || (array.length == 0)) {
/*  283: 730 */       return EMPTY_DOUBLE_OBJECT_ARRAY;
/*  284:     */     }
/*  285: 732 */     return array;
/*  286:     */   }
/*  287:     */   
/*  288:     */   public static Float[] nullToEmpty(Float[] array)
/*  289:     */   {
/*  290: 749 */     if ((array == null) || (array.length == 0)) {
/*  291: 750 */       return EMPTY_FLOAT_OBJECT_ARRAY;
/*  292:     */     }
/*  293: 752 */     return array;
/*  294:     */   }
/*  295:     */   
/*  296:     */   public static Boolean[] nullToEmpty(Boolean[] array)
/*  297:     */   {
/*  298: 769 */     if ((array == null) || (array.length == 0)) {
/*  299: 770 */       return EMPTY_BOOLEAN_OBJECT_ARRAY;
/*  300:     */     }
/*  301: 772 */     return array;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public static Object[] subarray(Object[] array, int startIndexInclusive, int endIndexExclusive)
/*  305:     */   {
/*  306: 805 */     if (array == null) {
/*  307: 806 */       return null;
/*  308:     */     }
/*  309: 808 */     if (startIndexInclusive < 0) {
/*  310: 809 */       startIndexInclusive = 0;
/*  311:     */     }
/*  312: 811 */     if (endIndexExclusive > array.length) {
/*  313: 812 */       endIndexExclusive = array.length;
/*  314:     */     }
/*  315: 814 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  316: 815 */     Class type = array.getClass().getComponentType();
/*  317: 816 */     if (newSize <= 0) {
/*  318: 817 */       return (Object[])Array.newInstance(type, 0);
/*  319:     */     }
/*  320: 819 */     Object[] subarray = (Object[])Array.newInstance(type, newSize);
/*  321: 820 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  322: 821 */     return subarray;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public static long[] subarray(long[] array, int startIndexInclusive, int endIndexExclusive)
/*  326:     */   {
/*  327: 844 */     if (array == null) {
/*  328: 845 */       return null;
/*  329:     */     }
/*  330: 847 */     if (startIndexInclusive < 0) {
/*  331: 848 */       startIndexInclusive = 0;
/*  332:     */     }
/*  333: 850 */     if (endIndexExclusive > array.length) {
/*  334: 851 */       endIndexExclusive = array.length;
/*  335:     */     }
/*  336: 853 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  337: 854 */     if (newSize <= 0) {
/*  338: 855 */       return EMPTY_LONG_ARRAY;
/*  339:     */     }
/*  340: 858 */     long[] subarray = new long[newSize];
/*  341: 859 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  342: 860 */     return subarray;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive)
/*  346:     */   {
/*  347: 883 */     if (array == null) {
/*  348: 884 */       return null;
/*  349:     */     }
/*  350: 886 */     if (startIndexInclusive < 0) {
/*  351: 887 */       startIndexInclusive = 0;
/*  352:     */     }
/*  353: 889 */     if (endIndexExclusive > array.length) {
/*  354: 890 */       endIndexExclusive = array.length;
/*  355:     */     }
/*  356: 892 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  357: 893 */     if (newSize <= 0) {
/*  358: 894 */       return EMPTY_INT_ARRAY;
/*  359:     */     }
/*  360: 897 */     int[] subarray = new int[newSize];
/*  361: 898 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  362: 899 */     return subarray;
/*  363:     */   }
/*  364:     */   
/*  365:     */   public static short[] subarray(short[] array, int startIndexInclusive, int endIndexExclusive)
/*  366:     */   {
/*  367: 922 */     if (array == null) {
/*  368: 923 */       return null;
/*  369:     */     }
/*  370: 925 */     if (startIndexInclusive < 0) {
/*  371: 926 */       startIndexInclusive = 0;
/*  372:     */     }
/*  373: 928 */     if (endIndexExclusive > array.length) {
/*  374: 929 */       endIndexExclusive = array.length;
/*  375:     */     }
/*  376: 931 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  377: 932 */     if (newSize <= 0) {
/*  378: 933 */       return EMPTY_SHORT_ARRAY;
/*  379:     */     }
/*  380: 936 */     short[] subarray = new short[newSize];
/*  381: 937 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  382: 938 */     return subarray;
/*  383:     */   }
/*  384:     */   
/*  385:     */   public static char[] subarray(char[] array, int startIndexInclusive, int endIndexExclusive)
/*  386:     */   {
/*  387: 961 */     if (array == null) {
/*  388: 962 */       return null;
/*  389:     */     }
/*  390: 964 */     if (startIndexInclusive < 0) {
/*  391: 965 */       startIndexInclusive = 0;
/*  392:     */     }
/*  393: 967 */     if (endIndexExclusive > array.length) {
/*  394: 968 */       endIndexExclusive = array.length;
/*  395:     */     }
/*  396: 970 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  397: 971 */     if (newSize <= 0) {
/*  398: 972 */       return EMPTY_CHAR_ARRAY;
/*  399:     */     }
/*  400: 975 */     char[] subarray = new char[newSize];
/*  401: 976 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  402: 977 */     return subarray;
/*  403:     */   }
/*  404:     */   
/*  405:     */   public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive)
/*  406:     */   {
/*  407:1000 */     if (array == null) {
/*  408:1001 */       return null;
/*  409:     */     }
/*  410:1003 */     if (startIndexInclusive < 0) {
/*  411:1004 */       startIndexInclusive = 0;
/*  412:     */     }
/*  413:1006 */     if (endIndexExclusive > array.length) {
/*  414:1007 */       endIndexExclusive = array.length;
/*  415:     */     }
/*  416:1009 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  417:1010 */     if (newSize <= 0) {
/*  418:1011 */       return EMPTY_BYTE_ARRAY;
/*  419:     */     }
/*  420:1014 */     byte[] subarray = new byte[newSize];
/*  421:1015 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  422:1016 */     return subarray;
/*  423:     */   }
/*  424:     */   
/*  425:     */   public static double[] subarray(double[] array, int startIndexInclusive, int endIndexExclusive)
/*  426:     */   {
/*  427:1039 */     if (array == null) {
/*  428:1040 */       return null;
/*  429:     */     }
/*  430:1042 */     if (startIndexInclusive < 0) {
/*  431:1043 */       startIndexInclusive = 0;
/*  432:     */     }
/*  433:1045 */     if (endIndexExclusive > array.length) {
/*  434:1046 */       endIndexExclusive = array.length;
/*  435:     */     }
/*  436:1048 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  437:1049 */     if (newSize <= 0) {
/*  438:1050 */       return EMPTY_DOUBLE_ARRAY;
/*  439:     */     }
/*  440:1053 */     double[] subarray = new double[newSize];
/*  441:1054 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  442:1055 */     return subarray;
/*  443:     */   }
/*  444:     */   
/*  445:     */   public static float[] subarray(float[] array, int startIndexInclusive, int endIndexExclusive)
/*  446:     */   {
/*  447:1078 */     if (array == null) {
/*  448:1079 */       return null;
/*  449:     */     }
/*  450:1081 */     if (startIndexInclusive < 0) {
/*  451:1082 */       startIndexInclusive = 0;
/*  452:     */     }
/*  453:1084 */     if (endIndexExclusive > array.length) {
/*  454:1085 */       endIndexExclusive = array.length;
/*  455:     */     }
/*  456:1087 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  457:1088 */     if (newSize <= 0) {
/*  458:1089 */       return EMPTY_FLOAT_ARRAY;
/*  459:     */     }
/*  460:1092 */     float[] subarray = new float[newSize];
/*  461:1093 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  462:1094 */     return subarray;
/*  463:     */   }
/*  464:     */   
/*  465:     */   public static boolean[] subarray(boolean[] array, int startIndexInclusive, int endIndexExclusive)
/*  466:     */   {
/*  467:1117 */     if (array == null) {
/*  468:1118 */       return null;
/*  469:     */     }
/*  470:1120 */     if (startIndexInclusive < 0) {
/*  471:1121 */       startIndexInclusive = 0;
/*  472:     */     }
/*  473:1123 */     if (endIndexExclusive > array.length) {
/*  474:1124 */       endIndexExclusive = array.length;
/*  475:     */     }
/*  476:1126 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  477:1127 */     if (newSize <= 0) {
/*  478:1128 */       return EMPTY_BOOLEAN_ARRAY;
/*  479:     */     }
/*  480:1131 */     boolean[] subarray = new boolean[newSize];
/*  481:1132 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  482:1133 */     return subarray;
/*  483:     */   }
/*  484:     */   
/*  485:     */   public static boolean isSameLength(Object[] array1, Object[] array2)
/*  486:     */   {
/*  487:1150 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  488:1153 */       return false;
/*  489:     */     }
/*  490:1155 */     return true;
/*  491:     */   }
/*  492:     */   
/*  493:     */   public static boolean isSameLength(long[] array1, long[] array2)
/*  494:     */   {
/*  495:1168 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  496:1171 */       return false;
/*  497:     */     }
/*  498:1173 */     return true;
/*  499:     */   }
/*  500:     */   
/*  501:     */   public static boolean isSameLength(int[] array1, int[] array2)
/*  502:     */   {
/*  503:1186 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  504:1189 */       return false;
/*  505:     */     }
/*  506:1191 */     return true;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public static boolean isSameLength(short[] array1, short[] array2)
/*  510:     */   {
/*  511:1204 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  512:1207 */       return false;
/*  513:     */     }
/*  514:1209 */     return true;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public static boolean isSameLength(char[] array1, char[] array2)
/*  518:     */   {
/*  519:1222 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  520:1225 */       return false;
/*  521:     */     }
/*  522:1227 */     return true;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public static boolean isSameLength(byte[] array1, byte[] array2)
/*  526:     */   {
/*  527:1240 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  528:1243 */       return false;
/*  529:     */     }
/*  530:1245 */     return true;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public static boolean isSameLength(double[] array1, double[] array2)
/*  534:     */   {
/*  535:1258 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  536:1261 */       return false;
/*  537:     */     }
/*  538:1263 */     return true;
/*  539:     */   }
/*  540:     */   
/*  541:     */   public static boolean isSameLength(float[] array1, float[] array2)
/*  542:     */   {
/*  543:1276 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  544:1279 */       return false;
/*  545:     */     }
/*  546:1281 */     return true;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public static boolean isSameLength(boolean[] array1, boolean[] array2)
/*  550:     */   {
/*  551:1294 */     if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
/*  552:1297 */       return false;
/*  553:     */     }
/*  554:1299 */     return true;
/*  555:     */   }
/*  556:     */   
/*  557:     */   public static int getLength(Object array)
/*  558:     */   {
/*  559:1324 */     if (array == null) {
/*  560:1325 */       return 0;
/*  561:     */     }
/*  562:1327 */     return Array.getLength(array);
/*  563:     */   }
/*  564:     */   
/*  565:     */   public static boolean isSameType(Object array1, Object array2)
/*  566:     */   {
/*  567:1340 */     if ((array1 == null) || (array2 == null)) {
/*  568:1341 */       throw new IllegalArgumentException("The Array must not be null");
/*  569:     */     }
/*  570:1343 */     return array1.getClass().getName().equals(array2.getClass().getName());
/*  571:     */   }
/*  572:     */   
/*  573:     */   public static void reverse(Object[] array)
/*  574:     */   {
/*  575:1358 */     if (array == null) {
/*  576:1359 */       return;
/*  577:     */     }
/*  578:1361 */     int i = 0;
/*  579:1362 */     int j = array.length - 1;
/*  580:1364 */     while (j > i)
/*  581:     */     {
/*  582:1365 */       Object tmp = array[j];
/*  583:1366 */       array[j] = array[i];
/*  584:1367 */       array[i] = tmp;
/*  585:1368 */       j--;
/*  586:1369 */       i++;
/*  587:     */     }
/*  588:     */   }
/*  589:     */   
/*  590:     */   public static void reverse(long[] array)
/*  591:     */   {
/*  592:1381 */     if (array == null) {
/*  593:1382 */       return;
/*  594:     */     }
/*  595:1384 */     int i = 0;
/*  596:1385 */     int j = array.length - 1;
/*  597:1387 */     while (j > i)
/*  598:     */     {
/*  599:1388 */       long tmp = array[j];
/*  600:1389 */       array[j] = array[i];
/*  601:1390 */       array[i] = tmp;
/*  602:1391 */       j--;
/*  603:1392 */       i++;
/*  604:     */     }
/*  605:     */   }
/*  606:     */   
/*  607:     */   public static void reverse(int[] array)
/*  608:     */   {
/*  609:1404 */     if (array == null) {
/*  610:1405 */       return;
/*  611:     */     }
/*  612:1407 */     int i = 0;
/*  613:1408 */     int j = array.length - 1;
/*  614:1410 */     while (j > i)
/*  615:     */     {
/*  616:1411 */       int tmp = array[j];
/*  617:1412 */       array[j] = array[i];
/*  618:1413 */       array[i] = tmp;
/*  619:1414 */       j--;
/*  620:1415 */       i++;
/*  621:     */     }
/*  622:     */   }
/*  623:     */   
/*  624:     */   public static void reverse(short[] array)
/*  625:     */   {
/*  626:1427 */     if (array == null) {
/*  627:1428 */       return;
/*  628:     */     }
/*  629:1430 */     int i = 0;
/*  630:1431 */     int j = array.length - 1;
/*  631:1433 */     while (j > i)
/*  632:     */     {
/*  633:1434 */       short tmp = array[j];
/*  634:1435 */       array[j] = array[i];
/*  635:1436 */       array[i] = tmp;
/*  636:1437 */       j--;
/*  637:1438 */       i++;
/*  638:     */     }
/*  639:     */   }
/*  640:     */   
/*  641:     */   public static void reverse(char[] array)
/*  642:     */   {
/*  643:1450 */     if (array == null) {
/*  644:1451 */       return;
/*  645:     */     }
/*  646:1453 */     int i = 0;
/*  647:1454 */     int j = array.length - 1;
/*  648:1456 */     while (j > i)
/*  649:     */     {
/*  650:1457 */       char tmp = array[j];
/*  651:1458 */       array[j] = array[i];
/*  652:1459 */       array[i] = tmp;
/*  653:1460 */       j--;
/*  654:1461 */       i++;
/*  655:     */     }
/*  656:     */   }
/*  657:     */   
/*  658:     */   public static void reverse(byte[] array)
/*  659:     */   {
/*  660:1473 */     if (array == null) {
/*  661:1474 */       return;
/*  662:     */     }
/*  663:1476 */     int i = 0;
/*  664:1477 */     int j = array.length - 1;
/*  665:1479 */     while (j > i)
/*  666:     */     {
/*  667:1480 */       byte tmp = array[j];
/*  668:1481 */       array[j] = array[i];
/*  669:1482 */       array[i] = tmp;
/*  670:1483 */       j--;
/*  671:1484 */       i++;
/*  672:     */     }
/*  673:     */   }
/*  674:     */   
/*  675:     */   public static void reverse(double[] array)
/*  676:     */   {
/*  677:1496 */     if (array == null) {
/*  678:1497 */       return;
/*  679:     */     }
/*  680:1499 */     int i = 0;
/*  681:1500 */     int j = array.length - 1;
/*  682:1502 */     while (j > i)
/*  683:     */     {
/*  684:1503 */       double tmp = array[j];
/*  685:1504 */       array[j] = array[i];
/*  686:1505 */       array[i] = tmp;
/*  687:1506 */       j--;
/*  688:1507 */       i++;
/*  689:     */     }
/*  690:     */   }
/*  691:     */   
/*  692:     */   public static void reverse(float[] array)
/*  693:     */   {
/*  694:1519 */     if (array == null) {
/*  695:1520 */       return;
/*  696:     */     }
/*  697:1522 */     int i = 0;
/*  698:1523 */     int j = array.length - 1;
/*  699:1525 */     while (j > i)
/*  700:     */     {
/*  701:1526 */       float tmp = array[j];
/*  702:1527 */       array[j] = array[i];
/*  703:1528 */       array[i] = tmp;
/*  704:1529 */       j--;
/*  705:1530 */       i++;
/*  706:     */     }
/*  707:     */   }
/*  708:     */   
/*  709:     */   public static void reverse(boolean[] array)
/*  710:     */   {
/*  711:1542 */     if (array == null) {
/*  712:1543 */       return;
/*  713:     */     }
/*  714:1545 */     int i = 0;
/*  715:1546 */     int j = array.length - 1;
/*  716:1548 */     while (j > i)
/*  717:     */     {
/*  718:1549 */       boolean tmp = array[j];
/*  719:1550 */       array[j] = array[i];
/*  720:1551 */       array[i] = tmp;
/*  721:1552 */       j--;
/*  722:1553 */       i++;
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   public static int indexOf(Object[] array, Object objectToFind)
/*  727:     */   {
/*  728:1573 */     return indexOf(array, objectToFind, 0);
/*  729:     */   }
/*  730:     */   
/*  731:     */   public static int indexOf(Object[] array, Object objectToFind, int startIndex)
/*  732:     */   {
/*  733:1591 */     if (array == null) {
/*  734:1592 */       return -1;
/*  735:     */     }
/*  736:1594 */     if (startIndex < 0) {
/*  737:1595 */       startIndex = 0;
/*  738:     */     }
/*  739:1597 */     if (objectToFind == null) {
/*  740:1598 */       for (int i = startIndex; i < array.length; i++) {
/*  741:1599 */         if (array[i] == null) {
/*  742:1600 */           return i;
/*  743:     */         }
/*  744:     */       }
/*  745:1603 */     } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
/*  746:1604 */       for (int i = startIndex; i < array.length; i++) {
/*  747:1605 */         if (objectToFind.equals(array[i])) {
/*  748:1606 */           return i;
/*  749:     */         }
/*  750:     */       }
/*  751:     */     }
/*  752:1610 */     return -1;
/*  753:     */   }
/*  754:     */   
/*  755:     */   public static int lastIndexOf(Object[] array, Object objectToFind)
/*  756:     */   {
/*  757:1624 */     return lastIndexOf(array, objectToFind, 2147483647);
/*  758:     */   }
/*  759:     */   
/*  760:     */   public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex)
/*  761:     */   {
/*  762:1642 */     if (array == null) {
/*  763:1643 */       return -1;
/*  764:     */     }
/*  765:1645 */     if (startIndex < 0) {
/*  766:1646 */       return -1;
/*  767:     */     }
/*  768:1647 */     if (startIndex >= array.length) {
/*  769:1648 */       startIndex = array.length - 1;
/*  770:     */     }
/*  771:1650 */     if (objectToFind == null) {
/*  772:1651 */       for (int i = startIndex; i >= 0; i--) {
/*  773:1652 */         if (array[i] == null) {
/*  774:1653 */           return i;
/*  775:     */         }
/*  776:     */       }
/*  777:1656 */     } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
/*  778:1657 */       for (int i = startIndex; i >= 0; i--) {
/*  779:1658 */         if (objectToFind.equals(array[i])) {
/*  780:1659 */           return i;
/*  781:     */         }
/*  782:     */       }
/*  783:     */     }
/*  784:1663 */     return -1;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public static boolean contains(Object[] array, Object objectToFind)
/*  788:     */   {
/*  789:1676 */     return indexOf(array, objectToFind) != -1;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public static int indexOf(long[] array, long valueToFind)
/*  793:     */   {
/*  794:1692 */     return indexOf(array, valueToFind, 0);
/*  795:     */   }
/*  796:     */   
/*  797:     */   public static int indexOf(long[] array, long valueToFind, int startIndex)
/*  798:     */   {
/*  799:1710 */     if (array == null) {
/*  800:1711 */       return -1;
/*  801:     */     }
/*  802:1713 */     if (startIndex < 0) {
/*  803:1714 */       startIndex = 0;
/*  804:     */     }
/*  805:1716 */     for (int i = startIndex; i < array.length; i++) {
/*  806:1717 */       if (valueToFind == array[i]) {
/*  807:1718 */         return i;
/*  808:     */       }
/*  809:     */     }
/*  810:1721 */     return -1;
/*  811:     */   }
/*  812:     */   
/*  813:     */   public static int lastIndexOf(long[] array, long valueToFind)
/*  814:     */   {
/*  815:1735 */     return lastIndexOf(array, valueToFind, 2147483647);
/*  816:     */   }
/*  817:     */   
/*  818:     */   public static int lastIndexOf(long[] array, long valueToFind, int startIndex)
/*  819:     */   {
/*  820:1753 */     if (array == null) {
/*  821:1754 */       return -1;
/*  822:     */     }
/*  823:1756 */     if (startIndex < 0) {
/*  824:1757 */       return -1;
/*  825:     */     }
/*  826:1758 */     if (startIndex >= array.length) {
/*  827:1759 */       startIndex = array.length - 1;
/*  828:     */     }
/*  829:1761 */     for (int i = startIndex; i >= 0; i--) {
/*  830:1762 */       if (valueToFind == array[i]) {
/*  831:1763 */         return i;
/*  832:     */       }
/*  833:     */     }
/*  834:1766 */     return -1;
/*  835:     */   }
/*  836:     */   
/*  837:     */   public static boolean contains(long[] array, long valueToFind)
/*  838:     */   {
/*  839:1779 */     return indexOf(array, valueToFind) != -1;
/*  840:     */   }
/*  841:     */   
/*  842:     */   public static int indexOf(int[] array, int valueToFind)
/*  843:     */   {
/*  844:1795 */     return indexOf(array, valueToFind, 0);
/*  845:     */   }
/*  846:     */   
/*  847:     */   public static int indexOf(int[] array, int valueToFind, int startIndex)
/*  848:     */   {
/*  849:1813 */     if (array == null) {
/*  850:1814 */       return -1;
/*  851:     */     }
/*  852:1816 */     if (startIndex < 0) {
/*  853:1817 */       startIndex = 0;
/*  854:     */     }
/*  855:1819 */     for (int i = startIndex; i < array.length; i++) {
/*  856:1820 */       if (valueToFind == array[i]) {
/*  857:1821 */         return i;
/*  858:     */       }
/*  859:     */     }
/*  860:1824 */     return -1;
/*  861:     */   }
/*  862:     */   
/*  863:     */   public static int lastIndexOf(int[] array, int valueToFind)
/*  864:     */   {
/*  865:1838 */     return lastIndexOf(array, valueToFind, 2147483647);
/*  866:     */   }
/*  867:     */   
/*  868:     */   public static int lastIndexOf(int[] array, int valueToFind, int startIndex)
/*  869:     */   {
/*  870:1856 */     if (array == null) {
/*  871:1857 */       return -1;
/*  872:     */     }
/*  873:1859 */     if (startIndex < 0) {
/*  874:1860 */       return -1;
/*  875:     */     }
/*  876:1861 */     if (startIndex >= array.length) {
/*  877:1862 */       startIndex = array.length - 1;
/*  878:     */     }
/*  879:1864 */     for (int i = startIndex; i >= 0; i--) {
/*  880:1865 */       if (valueToFind == array[i]) {
/*  881:1866 */         return i;
/*  882:     */       }
/*  883:     */     }
/*  884:1869 */     return -1;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public static boolean contains(int[] array, int valueToFind)
/*  888:     */   {
/*  889:1882 */     return indexOf(array, valueToFind) != -1;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public static int indexOf(short[] array, short valueToFind)
/*  893:     */   {
/*  894:1898 */     return indexOf(array, valueToFind, 0);
/*  895:     */   }
/*  896:     */   
/*  897:     */   public static int indexOf(short[] array, short valueToFind, int startIndex)
/*  898:     */   {
/*  899:1916 */     if (array == null) {
/*  900:1917 */       return -1;
/*  901:     */     }
/*  902:1919 */     if (startIndex < 0) {
/*  903:1920 */       startIndex = 0;
/*  904:     */     }
/*  905:1922 */     for (int i = startIndex; i < array.length; i++) {
/*  906:1923 */       if (valueToFind == array[i]) {
/*  907:1924 */         return i;
/*  908:     */       }
/*  909:     */     }
/*  910:1927 */     return -1;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public static int lastIndexOf(short[] array, short valueToFind)
/*  914:     */   {
/*  915:1941 */     return lastIndexOf(array, valueToFind, 2147483647);
/*  916:     */   }
/*  917:     */   
/*  918:     */   public static int lastIndexOf(short[] array, short valueToFind, int startIndex)
/*  919:     */   {
/*  920:1959 */     if (array == null) {
/*  921:1960 */       return -1;
/*  922:     */     }
/*  923:1962 */     if (startIndex < 0) {
/*  924:1963 */       return -1;
/*  925:     */     }
/*  926:1964 */     if (startIndex >= array.length) {
/*  927:1965 */       startIndex = array.length - 1;
/*  928:     */     }
/*  929:1967 */     for (int i = startIndex; i >= 0; i--) {
/*  930:1968 */       if (valueToFind == array[i]) {
/*  931:1969 */         return i;
/*  932:     */       }
/*  933:     */     }
/*  934:1972 */     return -1;
/*  935:     */   }
/*  936:     */   
/*  937:     */   public static boolean contains(short[] array, short valueToFind)
/*  938:     */   {
/*  939:1985 */     return indexOf(array, valueToFind) != -1;
/*  940:     */   }
/*  941:     */   
/*  942:     */   public static int indexOf(char[] array, char valueToFind)
/*  943:     */   {
/*  944:2002 */     return indexOf(array, valueToFind, 0);
/*  945:     */   }
/*  946:     */   
/*  947:     */   public static int indexOf(char[] array, char valueToFind, int startIndex)
/*  948:     */   {
/*  949:2021 */     if (array == null) {
/*  950:2022 */       return -1;
/*  951:     */     }
/*  952:2024 */     if (startIndex < 0) {
/*  953:2025 */       startIndex = 0;
/*  954:     */     }
/*  955:2027 */     for (int i = startIndex; i < array.length; i++) {
/*  956:2028 */       if (valueToFind == array[i]) {
/*  957:2029 */         return i;
/*  958:     */       }
/*  959:     */     }
/*  960:2032 */     return -1;
/*  961:     */   }
/*  962:     */   
/*  963:     */   public static int lastIndexOf(char[] array, char valueToFind)
/*  964:     */   {
/*  965:2047 */     return lastIndexOf(array, valueToFind, 2147483647);
/*  966:     */   }
/*  967:     */   
/*  968:     */   public static int lastIndexOf(char[] array, char valueToFind, int startIndex)
/*  969:     */   {
/*  970:2066 */     if (array == null) {
/*  971:2067 */       return -1;
/*  972:     */     }
/*  973:2069 */     if (startIndex < 0) {
/*  974:2070 */       return -1;
/*  975:     */     }
/*  976:2071 */     if (startIndex >= array.length) {
/*  977:2072 */       startIndex = array.length - 1;
/*  978:     */     }
/*  979:2074 */     for (int i = startIndex; i >= 0; i--) {
/*  980:2075 */       if (valueToFind == array[i]) {
/*  981:2076 */         return i;
/*  982:     */       }
/*  983:     */     }
/*  984:2079 */     return -1;
/*  985:     */   }
/*  986:     */   
/*  987:     */   public static boolean contains(char[] array, char valueToFind)
/*  988:     */   {
/*  989:2093 */     return indexOf(array, valueToFind) != -1;
/*  990:     */   }
/*  991:     */   
/*  992:     */   public static int indexOf(byte[] array, byte valueToFind)
/*  993:     */   {
/*  994:2109 */     return indexOf(array, valueToFind, 0);
/*  995:     */   }
/*  996:     */   
/*  997:     */   public static int indexOf(byte[] array, byte valueToFind, int startIndex)
/*  998:     */   {
/*  999:2127 */     if (array == null) {
/* 1000:2128 */       return -1;
/* 1001:     */     }
/* 1002:2130 */     if (startIndex < 0) {
/* 1003:2131 */       startIndex = 0;
/* 1004:     */     }
/* 1005:2133 */     for (int i = startIndex; i < array.length; i++) {
/* 1006:2134 */       if (valueToFind == array[i]) {
/* 1007:2135 */         return i;
/* 1008:     */       }
/* 1009:     */     }
/* 1010:2138 */     return -1;
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public static int lastIndexOf(byte[] array, byte valueToFind)
/* 1014:     */   {
/* 1015:2152 */     return lastIndexOf(array, valueToFind, 2147483647);
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex)
/* 1019:     */   {
/* 1020:2170 */     if (array == null) {
/* 1021:2171 */       return -1;
/* 1022:     */     }
/* 1023:2173 */     if (startIndex < 0) {
/* 1024:2174 */       return -1;
/* 1025:     */     }
/* 1026:2175 */     if (startIndex >= array.length) {
/* 1027:2176 */       startIndex = array.length - 1;
/* 1028:     */     }
/* 1029:2178 */     for (int i = startIndex; i >= 0; i--) {
/* 1030:2179 */       if (valueToFind == array[i]) {
/* 1031:2180 */         return i;
/* 1032:     */       }
/* 1033:     */     }
/* 1034:2183 */     return -1;
/* 1035:     */   }
/* 1036:     */   
/* 1037:     */   public static boolean contains(byte[] array, byte valueToFind)
/* 1038:     */   {
/* 1039:2196 */     return indexOf(array, valueToFind) != -1;
/* 1040:     */   }
/* 1041:     */   
/* 1042:     */   public static int indexOf(double[] array, double valueToFind)
/* 1043:     */   {
/* 1044:2212 */     return indexOf(array, valueToFind, 0);
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public static int indexOf(double[] array, double valueToFind, double tolerance)
/* 1048:     */   {
/* 1049:2229 */     return indexOf(array, valueToFind, 0, tolerance);
/* 1050:     */   }
/* 1051:     */   
/* 1052:     */   public static int indexOf(double[] array, double valueToFind, int startIndex)
/* 1053:     */   {
/* 1054:2247 */     if (isEmpty(array)) {
/* 1055:2248 */       return -1;
/* 1056:     */     }
/* 1057:2250 */     if (startIndex < 0) {
/* 1058:2251 */       startIndex = 0;
/* 1059:     */     }
/* 1060:2253 */     for (int i = startIndex; i < array.length; i++) {
/* 1061:2254 */       if (valueToFind == array[i]) {
/* 1062:2255 */         return i;
/* 1063:     */       }
/* 1064:     */     }
/* 1065:2258 */     return -1;
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance)
/* 1069:     */   {
/* 1070:2279 */     if (isEmpty(array)) {
/* 1071:2280 */       return -1;
/* 1072:     */     }
/* 1073:2282 */     if (startIndex < 0) {
/* 1074:2283 */       startIndex = 0;
/* 1075:     */     }
/* 1076:2285 */     double min = valueToFind - tolerance;
/* 1077:2286 */     double max = valueToFind + tolerance;
/* 1078:2287 */     for (int i = startIndex; i < array.length; i++) {
/* 1079:2288 */       if ((array[i] >= min) && (array[i] <= max)) {
/* 1080:2289 */         return i;
/* 1081:     */       }
/* 1082:     */     }
/* 1083:2292 */     return -1;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public static int lastIndexOf(double[] array, double valueToFind)
/* 1087:     */   {
/* 1088:2306 */     return lastIndexOf(array, valueToFind, 2147483647);
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public static int lastIndexOf(double[] array, double valueToFind, double tolerance)
/* 1092:     */   {
/* 1093:2323 */     return lastIndexOf(array, valueToFind, 2147483647, tolerance);
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   public static int lastIndexOf(double[] array, double valueToFind, int startIndex)
/* 1097:     */   {
/* 1098:2341 */     if (isEmpty(array)) {
/* 1099:2342 */       return -1;
/* 1100:     */     }
/* 1101:2344 */     if (startIndex < 0) {
/* 1102:2345 */       return -1;
/* 1103:     */     }
/* 1104:2346 */     if (startIndex >= array.length) {
/* 1105:2347 */       startIndex = array.length - 1;
/* 1106:     */     }
/* 1107:2349 */     for (int i = startIndex; i >= 0; i--) {
/* 1108:2350 */       if (valueToFind == array[i]) {
/* 1109:2351 */         return i;
/* 1110:     */       }
/* 1111:     */     }
/* 1112:2354 */     return -1;
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance)
/* 1116:     */   {
/* 1117:2375 */     if (isEmpty(array)) {
/* 1118:2376 */       return -1;
/* 1119:     */     }
/* 1120:2378 */     if (startIndex < 0) {
/* 1121:2379 */       return -1;
/* 1122:     */     }
/* 1123:2380 */     if (startIndex >= array.length) {
/* 1124:2381 */       startIndex = array.length - 1;
/* 1125:     */     }
/* 1126:2383 */     double min = valueToFind - tolerance;
/* 1127:2384 */     double max = valueToFind + tolerance;
/* 1128:2385 */     for (int i = startIndex; i >= 0; i--) {
/* 1129:2386 */       if ((array[i] >= min) && (array[i] <= max)) {
/* 1130:2387 */         return i;
/* 1131:     */       }
/* 1132:     */     }
/* 1133:2390 */     return -1;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public static boolean contains(double[] array, double valueToFind)
/* 1137:     */   {
/* 1138:2403 */     return indexOf(array, valueToFind) != -1;
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public static boolean contains(double[] array, double valueToFind, double tolerance)
/* 1142:     */   {
/* 1143:2420 */     return indexOf(array, valueToFind, 0, tolerance) != -1;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public static int indexOf(float[] array, float valueToFind)
/* 1147:     */   {
/* 1148:2436 */     return indexOf(array, valueToFind, 0);
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public static int indexOf(float[] array, float valueToFind, int startIndex)
/* 1152:     */   {
/* 1153:2454 */     if (isEmpty(array)) {
/* 1154:2455 */       return -1;
/* 1155:     */     }
/* 1156:2457 */     if (startIndex < 0) {
/* 1157:2458 */       startIndex = 0;
/* 1158:     */     }
/* 1159:2460 */     for (int i = startIndex; i < array.length; i++) {
/* 1160:2461 */       if (valueToFind == array[i]) {
/* 1161:2462 */         return i;
/* 1162:     */       }
/* 1163:     */     }
/* 1164:2465 */     return -1;
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public static int lastIndexOf(float[] array, float valueToFind)
/* 1168:     */   {
/* 1169:2479 */     return lastIndexOf(array, valueToFind, 2147483647);
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public static int lastIndexOf(float[] array, float valueToFind, int startIndex)
/* 1173:     */   {
/* 1174:2497 */     if (isEmpty(array)) {
/* 1175:2498 */       return -1;
/* 1176:     */     }
/* 1177:2500 */     if (startIndex < 0) {
/* 1178:2501 */       return -1;
/* 1179:     */     }
/* 1180:2502 */     if (startIndex >= array.length) {
/* 1181:2503 */       startIndex = array.length - 1;
/* 1182:     */     }
/* 1183:2505 */     for (int i = startIndex; i >= 0; i--) {
/* 1184:2506 */       if (valueToFind == array[i]) {
/* 1185:2507 */         return i;
/* 1186:     */       }
/* 1187:     */     }
/* 1188:2510 */     return -1;
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public static boolean contains(float[] array, float valueToFind)
/* 1192:     */   {
/* 1193:2523 */     return indexOf(array, valueToFind) != -1;
/* 1194:     */   }
/* 1195:     */   
/* 1196:     */   public static int indexOf(boolean[] array, boolean valueToFind)
/* 1197:     */   {
/* 1198:2539 */     return indexOf(array, valueToFind, 0);
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   public static int indexOf(boolean[] array, boolean valueToFind, int startIndex)
/* 1202:     */   {
/* 1203:2558 */     if (isEmpty(array)) {
/* 1204:2559 */       return -1;
/* 1205:     */     }
/* 1206:2561 */     if (startIndex < 0) {
/* 1207:2562 */       startIndex = 0;
/* 1208:     */     }
/* 1209:2564 */     for (int i = startIndex; i < array.length; i++) {
/* 1210:2565 */       if (valueToFind == array[i]) {
/* 1211:2566 */         return i;
/* 1212:     */       }
/* 1213:     */     }
/* 1214:2569 */     return -1;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public static int lastIndexOf(boolean[] array, boolean valueToFind)
/* 1218:     */   {
/* 1219:2584 */     return lastIndexOf(array, valueToFind, 2147483647);
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex)
/* 1223:     */   {
/* 1224:2602 */     if (isEmpty(array)) {
/* 1225:2603 */       return -1;
/* 1226:     */     }
/* 1227:2605 */     if (startIndex < 0) {
/* 1228:2606 */       return -1;
/* 1229:     */     }
/* 1230:2607 */     if (startIndex >= array.length) {
/* 1231:2608 */       startIndex = array.length - 1;
/* 1232:     */     }
/* 1233:2610 */     for (int i = startIndex; i >= 0; i--) {
/* 1234:2611 */       if (valueToFind == array[i]) {
/* 1235:2612 */         return i;
/* 1236:     */       }
/* 1237:     */     }
/* 1238:2615 */     return -1;
/* 1239:     */   }
/* 1240:     */   
/* 1241:     */   public static boolean contains(boolean[] array, boolean valueToFind)
/* 1242:     */   {
/* 1243:2628 */     return indexOf(array, valueToFind) != -1;
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   public static char[] toPrimitive(Character[] array)
/* 1247:     */   {
/* 1248:2646 */     if (array == null) {
/* 1249:2647 */       return null;
/* 1250:     */     }
/* 1251:2648 */     if (array.length == 0) {
/* 1252:2649 */       return EMPTY_CHAR_ARRAY;
/* 1253:     */     }
/* 1254:2651 */     char[] result = new char[array.length];
/* 1255:2652 */     for (int i = 0; i < array.length; i++) {
/* 1256:2653 */       result[i] = array[i].charValue();
/* 1257:     */     }
/* 1258:2655 */     return result;
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   public static char[] toPrimitive(Character[] array, char valueForNull)
/* 1262:     */   {
/* 1263:2668 */     if (array == null) {
/* 1264:2669 */       return null;
/* 1265:     */     }
/* 1266:2670 */     if (array.length == 0) {
/* 1267:2671 */       return EMPTY_CHAR_ARRAY;
/* 1268:     */     }
/* 1269:2673 */     char[] result = new char[array.length];
/* 1270:2674 */     for (int i = 0; i < array.length; i++)
/* 1271:     */     {
/* 1272:2675 */       Character b = array[i];
/* 1273:2676 */       result[i] = (b == null ? valueForNull : b.charValue());
/* 1274:     */     }
/* 1275:2678 */     return result;
/* 1276:     */   }
/* 1277:     */   
/* 1278:     */   public static Character[] toObject(char[] array)
/* 1279:     */   {
/* 1280:2690 */     if (array == null) {
/* 1281:2691 */       return null;
/* 1282:     */     }
/* 1283:2692 */     if (array.length == 0) {
/* 1284:2693 */       return EMPTY_CHARACTER_OBJECT_ARRAY;
/* 1285:     */     }
/* 1286:2695 */     Character[] result = new Character[array.length];
/* 1287:2696 */     for (int i = 0; i < array.length; i++) {
/* 1288:2697 */       result[i] = new Character(array[i]);
/* 1289:     */     }
/* 1290:2699 */     return result;
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public static long[] toPrimitive(Long[] array)
/* 1294:     */   {
/* 1295:2714 */     if (array == null) {
/* 1296:2715 */       return null;
/* 1297:     */     }
/* 1298:2716 */     if (array.length == 0) {
/* 1299:2717 */       return EMPTY_LONG_ARRAY;
/* 1300:     */     }
/* 1301:2719 */     long[] result = new long[array.length];
/* 1302:2720 */     for (int i = 0; i < array.length; i++) {
/* 1303:2721 */       result[i] = array[i].longValue();
/* 1304:     */     }
/* 1305:2723 */     return result;
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public static long[] toPrimitive(Long[] array, long valueForNull)
/* 1309:     */   {
/* 1310:2736 */     if (array == null) {
/* 1311:2737 */       return null;
/* 1312:     */     }
/* 1313:2738 */     if (array.length == 0) {
/* 1314:2739 */       return EMPTY_LONG_ARRAY;
/* 1315:     */     }
/* 1316:2741 */     long[] result = new long[array.length];
/* 1317:2742 */     for (int i = 0; i < array.length; i++)
/* 1318:     */     {
/* 1319:2743 */       Long b = array[i];
/* 1320:2744 */       result[i] = (b == null ? valueForNull : b.longValue());
/* 1321:     */     }
/* 1322:2746 */     return result;
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public static Long[] toObject(long[] array)
/* 1326:     */   {
/* 1327:2758 */     if (array == null) {
/* 1328:2759 */       return null;
/* 1329:     */     }
/* 1330:2760 */     if (array.length == 0) {
/* 1331:2761 */       return EMPTY_LONG_OBJECT_ARRAY;
/* 1332:     */     }
/* 1333:2763 */     Long[] result = new Long[array.length];
/* 1334:2764 */     for (int i = 0; i < array.length; i++) {
/* 1335:2765 */       result[i] = new Long(array[i]);
/* 1336:     */     }
/* 1337:2767 */     return result;
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public static int[] toPrimitive(Integer[] array)
/* 1341:     */   {
/* 1342:2782 */     if (array == null) {
/* 1343:2783 */       return null;
/* 1344:     */     }
/* 1345:2784 */     if (array.length == 0) {
/* 1346:2785 */       return EMPTY_INT_ARRAY;
/* 1347:     */     }
/* 1348:2787 */     int[] result = new int[array.length];
/* 1349:2788 */     for (int i = 0; i < array.length; i++) {
/* 1350:2789 */       result[i] = array[i].intValue();
/* 1351:     */     }
/* 1352:2791 */     return result;
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public static int[] toPrimitive(Integer[] array, int valueForNull)
/* 1356:     */   {
/* 1357:2804 */     if (array == null) {
/* 1358:2805 */       return null;
/* 1359:     */     }
/* 1360:2806 */     if (array.length == 0) {
/* 1361:2807 */       return EMPTY_INT_ARRAY;
/* 1362:     */     }
/* 1363:2809 */     int[] result = new int[array.length];
/* 1364:2810 */     for (int i = 0; i < array.length; i++)
/* 1365:     */     {
/* 1366:2811 */       Integer b = array[i];
/* 1367:2812 */       result[i] = (b == null ? valueForNull : b.intValue());
/* 1368:     */     }
/* 1369:2814 */     return result;
/* 1370:     */   }
/* 1371:     */   
/* 1372:     */   public static Integer[] toObject(int[] array)
/* 1373:     */   {
/* 1374:2826 */     if (array == null) {
/* 1375:2827 */       return null;
/* 1376:     */     }
/* 1377:2828 */     if (array.length == 0) {
/* 1378:2829 */       return EMPTY_INTEGER_OBJECT_ARRAY;
/* 1379:     */     }
/* 1380:2831 */     Integer[] result = new Integer[array.length];
/* 1381:2832 */     for (int i = 0; i < array.length; i++) {
/* 1382:2833 */       result[i] = new Integer(array[i]);
/* 1383:     */     }
/* 1384:2835 */     return result;
/* 1385:     */   }
/* 1386:     */   
/* 1387:     */   public static short[] toPrimitive(Short[] array)
/* 1388:     */   {
/* 1389:2850 */     if (array == null) {
/* 1390:2851 */       return null;
/* 1391:     */     }
/* 1392:2852 */     if (array.length == 0) {
/* 1393:2853 */       return EMPTY_SHORT_ARRAY;
/* 1394:     */     }
/* 1395:2855 */     short[] result = new short[array.length];
/* 1396:2856 */     for (int i = 0; i < array.length; i++) {
/* 1397:2857 */       result[i] = array[i].shortValue();
/* 1398:     */     }
/* 1399:2859 */     return result;
/* 1400:     */   }
/* 1401:     */   
/* 1402:     */   public static short[] toPrimitive(Short[] array, short valueForNull)
/* 1403:     */   {
/* 1404:2872 */     if (array == null) {
/* 1405:2873 */       return null;
/* 1406:     */     }
/* 1407:2874 */     if (array.length == 0) {
/* 1408:2875 */       return EMPTY_SHORT_ARRAY;
/* 1409:     */     }
/* 1410:2877 */     short[] result = new short[array.length];
/* 1411:2878 */     for (int i = 0; i < array.length; i++)
/* 1412:     */     {
/* 1413:2879 */       Short b = array[i];
/* 1414:2880 */       result[i] = (b == null ? valueForNull : b.shortValue());
/* 1415:     */     }
/* 1416:2882 */     return result;
/* 1417:     */   }
/* 1418:     */   
/* 1419:     */   public static Short[] toObject(short[] array)
/* 1420:     */   {
/* 1421:2894 */     if (array == null) {
/* 1422:2895 */       return null;
/* 1423:     */     }
/* 1424:2896 */     if (array.length == 0) {
/* 1425:2897 */       return EMPTY_SHORT_OBJECT_ARRAY;
/* 1426:     */     }
/* 1427:2899 */     Short[] result = new Short[array.length];
/* 1428:2900 */     for (int i = 0; i < array.length; i++) {
/* 1429:2901 */       result[i] = new Short(array[i]);
/* 1430:     */     }
/* 1431:2903 */     return result;
/* 1432:     */   }
/* 1433:     */   
/* 1434:     */   public static byte[] toPrimitive(Byte[] array)
/* 1435:     */   {
/* 1436:2918 */     if (array == null) {
/* 1437:2919 */       return null;
/* 1438:     */     }
/* 1439:2920 */     if (array.length == 0) {
/* 1440:2921 */       return EMPTY_BYTE_ARRAY;
/* 1441:     */     }
/* 1442:2923 */     byte[] result = new byte[array.length];
/* 1443:2924 */     for (int i = 0; i < array.length; i++) {
/* 1444:2925 */       result[i] = array[i].byteValue();
/* 1445:     */     }
/* 1446:2927 */     return result;
/* 1447:     */   }
/* 1448:     */   
/* 1449:     */   public static byte[] toPrimitive(Byte[] array, byte valueForNull)
/* 1450:     */   {
/* 1451:2940 */     if (array == null) {
/* 1452:2941 */       return null;
/* 1453:     */     }
/* 1454:2942 */     if (array.length == 0) {
/* 1455:2943 */       return EMPTY_BYTE_ARRAY;
/* 1456:     */     }
/* 1457:2945 */     byte[] result = new byte[array.length];
/* 1458:2946 */     for (int i = 0; i < array.length; i++)
/* 1459:     */     {
/* 1460:2947 */       Byte b = array[i];
/* 1461:2948 */       result[i] = (b == null ? valueForNull : b.byteValue());
/* 1462:     */     }
/* 1463:2950 */     return result;
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public static Byte[] toObject(byte[] array)
/* 1467:     */   {
/* 1468:2962 */     if (array == null) {
/* 1469:2963 */       return null;
/* 1470:     */     }
/* 1471:2964 */     if (array.length == 0) {
/* 1472:2965 */       return EMPTY_BYTE_OBJECT_ARRAY;
/* 1473:     */     }
/* 1474:2967 */     Byte[] result = new Byte[array.length];
/* 1475:2968 */     for (int i = 0; i < array.length; i++) {
/* 1476:2969 */       result[i] = new Byte(array[i]);
/* 1477:     */     }
/* 1478:2971 */     return result;
/* 1479:     */   }
/* 1480:     */   
/* 1481:     */   public static double[] toPrimitive(Double[] array)
/* 1482:     */   {
/* 1483:2986 */     if (array == null) {
/* 1484:2987 */       return null;
/* 1485:     */     }
/* 1486:2988 */     if (array.length == 0) {
/* 1487:2989 */       return EMPTY_DOUBLE_ARRAY;
/* 1488:     */     }
/* 1489:2991 */     double[] result = new double[array.length];
/* 1490:2992 */     for (int i = 0; i < array.length; i++) {
/* 1491:2993 */       result[i] = array[i].doubleValue();
/* 1492:     */     }
/* 1493:2995 */     return result;
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public static double[] toPrimitive(Double[] array, double valueForNull)
/* 1497:     */   {
/* 1498:3008 */     if (array == null) {
/* 1499:3009 */       return null;
/* 1500:     */     }
/* 1501:3010 */     if (array.length == 0) {
/* 1502:3011 */       return EMPTY_DOUBLE_ARRAY;
/* 1503:     */     }
/* 1504:3013 */     double[] result = new double[array.length];
/* 1505:3014 */     for (int i = 0; i < array.length; i++)
/* 1506:     */     {
/* 1507:3015 */       Double b = array[i];
/* 1508:3016 */       result[i] = (b == null ? valueForNull : b.doubleValue());
/* 1509:     */     }
/* 1510:3018 */     return result;
/* 1511:     */   }
/* 1512:     */   
/* 1513:     */   public static Double[] toObject(double[] array)
/* 1514:     */   {
/* 1515:3030 */     if (array == null) {
/* 1516:3031 */       return null;
/* 1517:     */     }
/* 1518:3032 */     if (array.length == 0) {
/* 1519:3033 */       return EMPTY_DOUBLE_OBJECT_ARRAY;
/* 1520:     */     }
/* 1521:3035 */     Double[] result = new Double[array.length];
/* 1522:3036 */     for (int i = 0; i < array.length; i++) {
/* 1523:3037 */       result[i] = new Double(array[i]);
/* 1524:     */     }
/* 1525:3039 */     return result;
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   public static float[] toPrimitive(Float[] array)
/* 1529:     */   {
/* 1530:3054 */     if (array == null) {
/* 1531:3055 */       return null;
/* 1532:     */     }
/* 1533:3056 */     if (array.length == 0) {
/* 1534:3057 */       return EMPTY_FLOAT_ARRAY;
/* 1535:     */     }
/* 1536:3059 */     float[] result = new float[array.length];
/* 1537:3060 */     for (int i = 0; i < array.length; i++) {
/* 1538:3061 */       result[i] = array[i].floatValue();
/* 1539:     */     }
/* 1540:3063 */     return result;
/* 1541:     */   }
/* 1542:     */   
/* 1543:     */   public static float[] toPrimitive(Float[] array, float valueForNull)
/* 1544:     */   {
/* 1545:3076 */     if (array == null) {
/* 1546:3077 */       return null;
/* 1547:     */     }
/* 1548:3078 */     if (array.length == 0) {
/* 1549:3079 */       return EMPTY_FLOAT_ARRAY;
/* 1550:     */     }
/* 1551:3081 */     float[] result = new float[array.length];
/* 1552:3082 */     for (int i = 0; i < array.length; i++)
/* 1553:     */     {
/* 1554:3083 */       Float b = array[i];
/* 1555:3084 */       result[i] = (b == null ? valueForNull : b.floatValue());
/* 1556:     */     }
/* 1557:3086 */     return result;
/* 1558:     */   }
/* 1559:     */   
/* 1560:     */   public static Float[] toObject(float[] array)
/* 1561:     */   {
/* 1562:3098 */     if (array == null) {
/* 1563:3099 */       return null;
/* 1564:     */     }
/* 1565:3100 */     if (array.length == 0) {
/* 1566:3101 */       return EMPTY_FLOAT_OBJECT_ARRAY;
/* 1567:     */     }
/* 1568:3103 */     Float[] result = new Float[array.length];
/* 1569:3104 */     for (int i = 0; i < array.length; i++) {
/* 1570:3105 */       result[i] = new Float(array[i]);
/* 1571:     */     }
/* 1572:3107 */     return result;
/* 1573:     */   }
/* 1574:     */   
/* 1575:     */   public static boolean[] toPrimitive(Boolean[] array)
/* 1576:     */   {
/* 1577:3122 */     if (array == null) {
/* 1578:3123 */       return null;
/* 1579:     */     }
/* 1580:3124 */     if (array.length == 0) {
/* 1581:3125 */       return EMPTY_BOOLEAN_ARRAY;
/* 1582:     */     }
/* 1583:3127 */     boolean[] result = new boolean[array.length];
/* 1584:3128 */     for (int i = 0; i < array.length; i++) {
/* 1585:3129 */       result[i] = array[i].booleanValue();
/* 1586:     */     }
/* 1587:3131 */     return result;
/* 1588:     */   }
/* 1589:     */   
/* 1590:     */   public static boolean[] toPrimitive(Boolean[] array, boolean valueForNull)
/* 1591:     */   {
/* 1592:3144 */     if (array == null) {
/* 1593:3145 */       return null;
/* 1594:     */     }
/* 1595:3146 */     if (array.length == 0) {
/* 1596:3147 */       return EMPTY_BOOLEAN_ARRAY;
/* 1597:     */     }
/* 1598:3149 */     boolean[] result = new boolean[array.length];
/* 1599:3150 */     for (int i = 0; i < array.length; i++)
/* 1600:     */     {
/* 1601:3151 */       Boolean b = array[i];
/* 1602:3152 */       result[i] = (b == null ? valueForNull : b.booleanValue());
/* 1603:     */     }
/* 1604:3154 */     return result;
/* 1605:     */   }
/* 1606:     */   
/* 1607:     */   public static Boolean[] toObject(boolean[] array)
/* 1608:     */   {
/* 1609:3166 */     if (array == null) {
/* 1610:3167 */       return null;
/* 1611:     */     }
/* 1612:3168 */     if (array.length == 0) {
/* 1613:3169 */       return EMPTY_BOOLEAN_OBJECT_ARRAY;
/* 1614:     */     }
/* 1615:3171 */     Boolean[] result = new Boolean[array.length];
/* 1616:3172 */     for (int i = 0; i < array.length; i++) {
/* 1617:3173 */       result[i] = (array[i] != 0 ? Boolean.TRUE : Boolean.FALSE);
/* 1618:     */     }
/* 1619:3175 */     return result;
/* 1620:     */   }
/* 1621:     */   
/* 1622:     */   public static boolean isEmpty(Object[] array)
/* 1623:     */   {
/* 1624:3187 */     return (array == null) || (array.length == 0);
/* 1625:     */   }
/* 1626:     */   
/* 1627:     */   public static boolean isEmpty(long[] array)
/* 1628:     */   {
/* 1629:3198 */     return (array == null) || (array.length == 0);
/* 1630:     */   }
/* 1631:     */   
/* 1632:     */   public static boolean isEmpty(int[] array)
/* 1633:     */   {
/* 1634:3209 */     return (array == null) || (array.length == 0);
/* 1635:     */   }
/* 1636:     */   
/* 1637:     */   public static boolean isEmpty(short[] array)
/* 1638:     */   {
/* 1639:3220 */     return (array == null) || (array.length == 0);
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public static boolean isEmpty(char[] array)
/* 1643:     */   {
/* 1644:3231 */     return (array == null) || (array.length == 0);
/* 1645:     */   }
/* 1646:     */   
/* 1647:     */   public static boolean isEmpty(byte[] array)
/* 1648:     */   {
/* 1649:3242 */     return (array == null) || (array.length == 0);
/* 1650:     */   }
/* 1651:     */   
/* 1652:     */   public static boolean isEmpty(double[] array)
/* 1653:     */   {
/* 1654:3253 */     return (array == null) || (array.length == 0);
/* 1655:     */   }
/* 1656:     */   
/* 1657:     */   public static boolean isEmpty(float[] array)
/* 1658:     */   {
/* 1659:3264 */     return (array == null) || (array.length == 0);
/* 1660:     */   }
/* 1661:     */   
/* 1662:     */   public static boolean isEmpty(boolean[] array)
/* 1663:     */   {
/* 1664:3275 */     return (array == null) || (array.length == 0);
/* 1665:     */   }
/* 1666:     */   
/* 1667:     */   public static boolean isNotEmpty(Object[] array)
/* 1668:     */   {
/* 1669:3287 */     return (array != null) && (array.length != 0);
/* 1670:     */   }
/* 1671:     */   
/* 1672:     */   public static boolean isNotEmpty(long[] array)
/* 1673:     */   {
/* 1674:3298 */     return (array != null) && (array.length != 0);
/* 1675:     */   }
/* 1676:     */   
/* 1677:     */   public static boolean isNotEmpty(int[] array)
/* 1678:     */   {
/* 1679:3309 */     return (array != null) && (array.length != 0);
/* 1680:     */   }
/* 1681:     */   
/* 1682:     */   public static boolean isNotEmpty(short[] array)
/* 1683:     */   {
/* 1684:3320 */     return (array != null) && (array.length != 0);
/* 1685:     */   }
/* 1686:     */   
/* 1687:     */   public static boolean isNotEmpty(char[] array)
/* 1688:     */   {
/* 1689:3331 */     return (array != null) && (array.length != 0);
/* 1690:     */   }
/* 1691:     */   
/* 1692:     */   public static boolean isNotEmpty(byte[] array)
/* 1693:     */   {
/* 1694:3342 */     return (array != null) && (array.length != 0);
/* 1695:     */   }
/* 1696:     */   
/* 1697:     */   public static boolean isNotEmpty(double[] array)
/* 1698:     */   {
/* 1699:3353 */     return (array != null) && (array.length != 0);
/* 1700:     */   }
/* 1701:     */   
/* 1702:     */   public static boolean isNotEmpty(float[] array)
/* 1703:     */   {
/* 1704:3364 */     return (array != null) && (array.length != 0);
/* 1705:     */   }
/* 1706:     */   
/* 1707:     */   public static boolean isNotEmpty(boolean[] array)
/* 1708:     */   {
/* 1709:3375 */     return (array != null) && (array.length != 0);
/* 1710:     */   }
/* 1711:     */   
/* 1712:     */   public static Object[] addAll(Object[] array1, Object[] array2)
/* 1713:     */   {
/* 1714:3402 */     if (array1 == null) {
/* 1715:3403 */       return clone(array2);
/* 1716:     */     }
/* 1717:3404 */     if (array2 == null) {
/* 1718:3405 */       return clone(array1);
/* 1719:     */     }
/* 1720:3407 */     Object[] joinedArray = (Object[])Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
/* 1721:     */     
/* 1722:3409 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1723:     */     try
/* 1724:     */     {
/* 1725:3411 */       System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1726:     */     }
/* 1727:     */     catch (ArrayStoreException ase)
/* 1728:     */     {
/* 1729:3419 */       Class type1 = array1.getClass().getComponentType();
/* 1730:3420 */       Class type2 = array2.getClass().getComponentType();
/* 1731:3421 */       if (!type1.isAssignableFrom(type2)) {
/* 1732:3422 */         throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName());
/* 1733:     */       }
/* 1734:3424 */       throw ase;
/* 1735:     */     }
/* 1736:3426 */     return joinedArray;
/* 1737:     */   }
/* 1738:     */   
/* 1739:     */   public static boolean[] addAll(boolean[] array1, boolean[] array2)
/* 1740:     */   {
/* 1741:3447 */     if (array1 == null) {
/* 1742:3448 */       return clone(array2);
/* 1743:     */     }
/* 1744:3449 */     if (array2 == null) {
/* 1745:3450 */       return clone(array1);
/* 1746:     */     }
/* 1747:3452 */     boolean[] joinedArray = new boolean[array1.length + array2.length];
/* 1748:3453 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1749:3454 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1750:3455 */     return joinedArray;
/* 1751:     */   }
/* 1752:     */   
/* 1753:     */   public static char[] addAll(char[] array1, char[] array2)
/* 1754:     */   {
/* 1755:3476 */     if (array1 == null) {
/* 1756:3477 */       return clone(array2);
/* 1757:     */     }
/* 1758:3478 */     if (array2 == null) {
/* 1759:3479 */       return clone(array1);
/* 1760:     */     }
/* 1761:3481 */     char[] joinedArray = new char[array1.length + array2.length];
/* 1762:3482 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1763:3483 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1764:3484 */     return joinedArray;
/* 1765:     */   }
/* 1766:     */   
/* 1767:     */   public static byte[] addAll(byte[] array1, byte[] array2)
/* 1768:     */   {
/* 1769:3505 */     if (array1 == null) {
/* 1770:3506 */       return clone(array2);
/* 1771:     */     }
/* 1772:3507 */     if (array2 == null) {
/* 1773:3508 */       return clone(array1);
/* 1774:     */     }
/* 1775:3510 */     byte[] joinedArray = new byte[array1.length + array2.length];
/* 1776:3511 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1777:3512 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1778:3513 */     return joinedArray;
/* 1779:     */   }
/* 1780:     */   
/* 1781:     */   public static short[] addAll(short[] array1, short[] array2)
/* 1782:     */   {
/* 1783:3534 */     if (array1 == null) {
/* 1784:3535 */       return clone(array2);
/* 1785:     */     }
/* 1786:3536 */     if (array2 == null) {
/* 1787:3537 */       return clone(array1);
/* 1788:     */     }
/* 1789:3539 */     short[] joinedArray = new short[array1.length + array2.length];
/* 1790:3540 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1791:3541 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1792:3542 */     return joinedArray;
/* 1793:     */   }
/* 1794:     */   
/* 1795:     */   public static int[] addAll(int[] array1, int[] array2)
/* 1796:     */   {
/* 1797:3563 */     if (array1 == null) {
/* 1798:3564 */       return clone(array2);
/* 1799:     */     }
/* 1800:3565 */     if (array2 == null) {
/* 1801:3566 */       return clone(array1);
/* 1802:     */     }
/* 1803:3568 */     int[] joinedArray = new int[array1.length + array2.length];
/* 1804:3569 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1805:3570 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1806:3571 */     return joinedArray;
/* 1807:     */   }
/* 1808:     */   
/* 1809:     */   public static long[] addAll(long[] array1, long[] array2)
/* 1810:     */   {
/* 1811:3592 */     if (array1 == null) {
/* 1812:3593 */       return clone(array2);
/* 1813:     */     }
/* 1814:3594 */     if (array2 == null) {
/* 1815:3595 */       return clone(array1);
/* 1816:     */     }
/* 1817:3597 */     long[] joinedArray = new long[array1.length + array2.length];
/* 1818:3598 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1819:3599 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1820:3600 */     return joinedArray;
/* 1821:     */   }
/* 1822:     */   
/* 1823:     */   public static float[] addAll(float[] array1, float[] array2)
/* 1824:     */   {
/* 1825:3621 */     if (array1 == null) {
/* 1826:3622 */       return clone(array2);
/* 1827:     */     }
/* 1828:3623 */     if (array2 == null) {
/* 1829:3624 */       return clone(array1);
/* 1830:     */     }
/* 1831:3626 */     float[] joinedArray = new float[array1.length + array2.length];
/* 1832:3627 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1833:3628 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1834:3629 */     return joinedArray;
/* 1835:     */   }
/* 1836:     */   
/* 1837:     */   public static double[] addAll(double[] array1, double[] array2)
/* 1838:     */   {
/* 1839:3650 */     if (array1 == null) {
/* 1840:3651 */       return clone(array2);
/* 1841:     */     }
/* 1842:3652 */     if (array2 == null) {
/* 1843:3653 */       return clone(array1);
/* 1844:     */     }
/* 1845:3655 */     double[] joinedArray = new double[array1.length + array2.length];
/* 1846:3656 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1847:3657 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1848:3658 */     return joinedArray;
/* 1849:     */   }
/* 1850:     */   
/* 1851:     */   public static Object[] add(Object[] array, Object element)
/* 1852:     */   {
/* 1853:     */     Class type;
/* 1854:     */     Class type;
/* 1855:3689 */     if (array != null)
/* 1856:     */     {
/* 1857:3690 */       type = array.getClass();
/* 1858:     */     }
/* 1859:     */     else
/* 1860:     */     {
/* 1861:     */       Class type;
/* 1862:3691 */       if (element != null) {
/* 1863:3692 */         type = element.getClass();
/* 1864:     */       } else {
/* 1865:3694 */         type = Object.class;
/* 1866:     */       }
/* 1867:     */     }
/* 1868:3696 */     Object[] newArray = (Object[])copyArrayGrow1(array, type);
/* 1869:3697 */     newArray[(newArray.length - 1)] = element;
/* 1870:3698 */     return newArray;
/* 1871:     */   }
/* 1872:     */   
/* 1873:     */   public static boolean[] add(boolean[] array, boolean element)
/* 1874:     */   {
/* 1875:3723 */     boolean[] newArray = (boolean[])copyArrayGrow1(array, Boolean.TYPE);
/* 1876:3724 */     newArray[(newArray.length - 1)] = element;
/* 1877:3725 */     return newArray;
/* 1878:     */   }
/* 1879:     */   
/* 1880:     */   public static byte[] add(byte[] array, byte element)
/* 1881:     */   {
/* 1882:3750 */     byte[] newArray = (byte[])copyArrayGrow1(array, Byte.TYPE);
/* 1883:3751 */     newArray[(newArray.length - 1)] = element;
/* 1884:3752 */     return newArray;
/* 1885:     */   }
/* 1886:     */   
/* 1887:     */   public static char[] add(char[] array, char element)
/* 1888:     */   {
/* 1889:3777 */     char[] newArray = (char[])copyArrayGrow1(array, Character.TYPE);
/* 1890:3778 */     newArray[(newArray.length - 1)] = element;
/* 1891:3779 */     return newArray;
/* 1892:     */   }
/* 1893:     */   
/* 1894:     */   public static double[] add(double[] array, double element)
/* 1895:     */   {
/* 1896:3804 */     double[] newArray = (double[])copyArrayGrow1(array, Double.TYPE);
/* 1897:3805 */     newArray[(newArray.length - 1)] = element;
/* 1898:3806 */     return newArray;
/* 1899:     */   }
/* 1900:     */   
/* 1901:     */   public static float[] add(float[] array, float element)
/* 1902:     */   {
/* 1903:3831 */     float[] newArray = (float[])copyArrayGrow1(array, Float.TYPE);
/* 1904:3832 */     newArray[(newArray.length - 1)] = element;
/* 1905:3833 */     return newArray;
/* 1906:     */   }
/* 1907:     */   
/* 1908:     */   public static int[] add(int[] array, int element)
/* 1909:     */   {
/* 1910:3858 */     int[] newArray = (int[])copyArrayGrow1(array, Integer.TYPE);
/* 1911:3859 */     newArray[(newArray.length - 1)] = element;
/* 1912:3860 */     return newArray;
/* 1913:     */   }
/* 1914:     */   
/* 1915:     */   public static long[] add(long[] array, long element)
/* 1916:     */   {
/* 1917:3885 */     long[] newArray = (long[])copyArrayGrow1(array, Long.TYPE);
/* 1918:3886 */     newArray[(newArray.length - 1)] = element;
/* 1919:3887 */     return newArray;
/* 1920:     */   }
/* 1921:     */   
/* 1922:     */   public static short[] add(short[] array, short element)
/* 1923:     */   {
/* 1924:3912 */     short[] newArray = (short[])copyArrayGrow1(array, Short.TYPE);
/* 1925:3913 */     newArray[(newArray.length - 1)] = element;
/* 1926:3914 */     return newArray;
/* 1927:     */   }
/* 1928:     */   
/* 1929:     */   private static Object copyArrayGrow1(Object array, Class newArrayComponentType)
/* 1930:     */   {
/* 1931:3927 */     if (array != null)
/* 1932:     */     {
/* 1933:3928 */       int arrayLength = Array.getLength(array);
/* 1934:3929 */       Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
/* 1935:3930 */       System.arraycopy(array, 0, newArray, 0, arrayLength);
/* 1936:3931 */       return newArray;
/* 1937:     */     }
/* 1938:3933 */     return Array.newInstance(newArrayComponentType, 1);
/* 1939:     */   }
/* 1940:     */   
/* 1941:     */   public static Object[] add(Object[] array, int index, Object element)
/* 1942:     */   {
/* 1943:3965 */     Class clss = null;
/* 1944:3966 */     if (array != null) {
/* 1945:3967 */       clss = array.getClass().getComponentType();
/* 1946:3968 */     } else if (element != null) {
/* 1947:3969 */       clss = element.getClass();
/* 1948:     */     } else {
/* 1949:3971 */       return new Object[] { null };
/* 1950:     */     }
/* 1951:3973 */     return (Object[])add(array, index, element, clss);
/* 1952:     */   }
/* 1953:     */   
/* 1954:     */   public static boolean[] add(boolean[] array, int index, boolean element)
/* 1955:     */   {
/* 1956:4004 */     return (boolean[])add(array, index, BooleanUtils.toBooleanObject(element), Boolean.TYPE);
/* 1957:     */   }
/* 1958:     */   
/* 1959:     */   public static char[] add(char[] array, int index, char element)
/* 1960:     */   {
/* 1961:4036 */     return (char[])add(array, index, new Character(element), Character.TYPE);
/* 1962:     */   }
/* 1963:     */   
/* 1964:     */   public static byte[] add(byte[] array, int index, byte element)
/* 1965:     */   {
/* 1966:4067 */     return (byte[])add(array, index, new Byte(element), Byte.TYPE);
/* 1967:     */   }
/* 1968:     */   
/* 1969:     */   public static short[] add(short[] array, int index, short element)
/* 1970:     */   {
/* 1971:4098 */     return (short[])add(array, index, new Short(element), Short.TYPE);
/* 1972:     */   }
/* 1973:     */   
/* 1974:     */   public static int[] add(int[] array, int index, int element)
/* 1975:     */   {
/* 1976:4129 */     return (int[])add(array, index, new Integer(element), Integer.TYPE);
/* 1977:     */   }
/* 1978:     */   
/* 1979:     */   public static long[] add(long[] array, int index, long element)
/* 1980:     */   {
/* 1981:4160 */     return (long[])add(array, index, new Long(element), Long.TYPE);
/* 1982:     */   }
/* 1983:     */   
/* 1984:     */   public static float[] add(float[] array, int index, float element)
/* 1985:     */   {
/* 1986:4191 */     return (float[])add(array, index, new Float(element), Float.TYPE);
/* 1987:     */   }
/* 1988:     */   
/* 1989:     */   public static double[] add(double[] array, int index, double element)
/* 1990:     */   {
/* 1991:4222 */     return (double[])add(array, index, new Double(element), Double.TYPE);
/* 1992:     */   }
/* 1993:     */   
/* 1994:     */   private static Object add(Object array, int index, Object element, Class clss)
/* 1995:     */   {
/* 1996:4237 */     if (array == null)
/* 1997:     */     {
/* 1998:4238 */       if (index != 0) {
/* 1999:4239 */         throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
/* 2000:     */       }
/* 2001:4241 */       Object joinedArray = Array.newInstance(clss, 1);
/* 2002:4242 */       Array.set(joinedArray, 0, element);
/* 2003:4243 */       return joinedArray;
/* 2004:     */     }
/* 2005:4245 */     int length = Array.getLength(array);
/* 2006:4246 */     if ((index > length) || (index < 0)) {
/* 2007:4247 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
/* 2008:     */     }
/* 2009:4249 */     Object result = Array.newInstance(clss, length + 1);
/* 2010:4250 */     System.arraycopy(array, 0, result, 0, index);
/* 2011:4251 */     Array.set(result, index, element);
/* 2012:4252 */     if (index < length) {
/* 2013:4253 */       System.arraycopy(array, index, result, index + 1, length - index);
/* 2014:     */     }
/* 2015:4255 */     return result;
/* 2016:     */   }
/* 2017:     */   
/* 2018:     */   public static Object[] remove(Object[] array, int index)
/* 2019:     */   {
/* 2020:4287 */     return (Object[])remove(array, index);
/* 2021:     */   }
/* 2022:     */   
/* 2023:     */   public static Object[] removeElement(Object[] array, Object element)
/* 2024:     */   {
/* 2025:4316 */     int index = indexOf(array, element);
/* 2026:4317 */     if (index == -1) {
/* 2027:4318 */       return clone(array);
/* 2028:     */     }
/* 2029:4320 */     return remove(array, index);
/* 2030:     */   }
/* 2031:     */   
/* 2032:     */   public static boolean[] remove(boolean[] array, int index)
/* 2033:     */   {
/* 2034:4352 */     return (boolean[])remove(array, index);
/* 2035:     */   }
/* 2036:     */   
/* 2037:     */   public static boolean[] removeElement(boolean[] array, boolean element)
/* 2038:     */   {
/* 2039:4381 */     int index = indexOf(array, element);
/* 2040:4382 */     if (index == -1) {
/* 2041:4383 */       return clone(array);
/* 2042:     */     }
/* 2043:4385 */     return remove(array, index);
/* 2044:     */   }
/* 2045:     */   
/* 2046:     */   public static byte[] remove(byte[] array, int index)
/* 2047:     */   {
/* 2048:4417 */     return (byte[])remove(array, index);
/* 2049:     */   }
/* 2050:     */   
/* 2051:     */   public static byte[] removeElement(byte[] array, byte element)
/* 2052:     */   {
/* 2053:4446 */     int index = indexOf(array, element);
/* 2054:4447 */     if (index == -1) {
/* 2055:4448 */       return clone(array);
/* 2056:     */     }
/* 2057:4450 */     return remove(array, index);
/* 2058:     */   }
/* 2059:     */   
/* 2060:     */   public static char[] remove(char[] array, int index)
/* 2061:     */   {
/* 2062:4482 */     return (char[])remove(array, index);
/* 2063:     */   }
/* 2064:     */   
/* 2065:     */   public static char[] removeElement(char[] array, char element)
/* 2066:     */   {
/* 2067:4511 */     int index = indexOf(array, element);
/* 2068:4512 */     if (index == -1) {
/* 2069:4513 */       return clone(array);
/* 2070:     */     }
/* 2071:4515 */     return remove(array, index);
/* 2072:     */   }
/* 2073:     */   
/* 2074:     */   public static double[] remove(double[] array, int index)
/* 2075:     */   {
/* 2076:4547 */     return (double[])remove(array, index);
/* 2077:     */   }
/* 2078:     */   
/* 2079:     */   public static double[] removeElement(double[] array, double element)
/* 2080:     */   {
/* 2081:4576 */     int index = indexOf(array, element);
/* 2082:4577 */     if (index == -1) {
/* 2083:4578 */       return clone(array);
/* 2084:     */     }
/* 2085:4580 */     return remove(array, index);
/* 2086:     */   }
/* 2087:     */   
/* 2088:     */   public static float[] remove(float[] array, int index)
/* 2089:     */   {
/* 2090:4612 */     return (float[])remove(array, index);
/* 2091:     */   }
/* 2092:     */   
/* 2093:     */   public static float[] removeElement(float[] array, float element)
/* 2094:     */   {
/* 2095:4641 */     int index = indexOf(array, element);
/* 2096:4642 */     if (index == -1) {
/* 2097:4643 */       return clone(array);
/* 2098:     */     }
/* 2099:4645 */     return remove(array, index);
/* 2100:     */   }
/* 2101:     */   
/* 2102:     */   public static int[] remove(int[] array, int index)
/* 2103:     */   {
/* 2104:4677 */     return (int[])remove(array, index);
/* 2105:     */   }
/* 2106:     */   
/* 2107:     */   public static int[] removeElement(int[] array, int element)
/* 2108:     */   {
/* 2109:4706 */     int index = indexOf(array, element);
/* 2110:4707 */     if (index == -1) {
/* 2111:4708 */       return clone(array);
/* 2112:     */     }
/* 2113:4710 */     return remove(array, index);
/* 2114:     */   }
/* 2115:     */   
/* 2116:     */   public static long[] remove(long[] array, int index)
/* 2117:     */   {
/* 2118:4742 */     return (long[])remove(array, index);
/* 2119:     */   }
/* 2120:     */   
/* 2121:     */   public static long[] removeElement(long[] array, long element)
/* 2122:     */   {
/* 2123:4771 */     int index = indexOf(array, element);
/* 2124:4772 */     if (index == -1) {
/* 2125:4773 */       return clone(array);
/* 2126:     */     }
/* 2127:4775 */     return remove(array, index);
/* 2128:     */   }
/* 2129:     */   
/* 2130:     */   public static short[] remove(short[] array, int index)
/* 2131:     */   {
/* 2132:4807 */     return (short[])remove(array, index);
/* 2133:     */   }
/* 2134:     */   
/* 2135:     */   public static short[] removeElement(short[] array, short element)
/* 2136:     */   {
/* 2137:4836 */     int index = indexOf(array, element);
/* 2138:4837 */     if (index == -1) {
/* 2139:4838 */       return clone(array);
/* 2140:     */     }
/* 2141:4840 */     return remove(array, index);
/* 2142:     */   }
/* 2143:     */   
/* 2144:     */   private static Object remove(Object array, int index)
/* 2145:     */   {
/* 2146:4865 */     int length = getLength(array);
/* 2147:4866 */     if ((index < 0) || (index >= length)) {
/* 2148:4867 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
/* 2149:     */     }
/* 2150:4870 */     Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
/* 2151:4871 */     System.arraycopy(array, 0, result, 0, index);
/* 2152:4872 */     if (index < length - 1) {
/* 2153:4873 */       System.arraycopy(array, index + 1, result, index, length - index - 1);
/* 2154:     */     }
/* 2155:4876 */     return result;
/* 2156:     */   }
/* 2157:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.ArrayUtils
 * JD-Core Version:    0.7.0.1
 */