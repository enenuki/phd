/*    1:     */ package org.apache.commons.lang.builder;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.AccessibleObject;
/*    4:     */ import java.lang.reflect.Field;
/*    5:     */ import java.lang.reflect.Modifier;
/*    6:     */ import java.util.Collection;
/*    7:     */ import java.util.Comparator;
/*    8:     */ import org.apache.commons.lang.ArrayUtils;
/*    9:     */ import org.apache.commons.lang.math.NumberUtils;
/*   10:     */ 
/*   11:     */ public class CompareToBuilder
/*   12:     */ {
/*   13:     */   private int comparison;
/*   14:     */   
/*   15:     */   public CompareToBuilder()
/*   16:     */   {
/*   17: 109 */     this.comparison = 0;
/*   18:     */   }
/*   19:     */   
/*   20:     */   public static int reflectionCompare(Object lhs, Object rhs)
/*   21:     */   {
/*   22: 140 */     return reflectionCompare(lhs, rhs, false, null, null);
/*   23:     */   }
/*   24:     */   
/*   25:     */   public static int reflectionCompare(Object lhs, Object rhs, boolean compareTransients)
/*   26:     */   {
/*   27: 172 */     return reflectionCompare(lhs, rhs, compareTransients, null, null);
/*   28:     */   }
/*   29:     */   
/*   30:     */   public static int reflectionCompare(Object lhs, Object rhs, Collection excludeFields)
/*   31:     */   {
/*   32: 205 */     return reflectionCompare(lhs, rhs, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
/*   33:     */   }
/*   34:     */   
/*   35:     */   public static int reflectionCompare(Object lhs, Object rhs, String[] excludeFields)
/*   36:     */   {
/*   37: 238 */     return reflectionCompare(lhs, rhs, false, null, excludeFields);
/*   38:     */   }
/*   39:     */   
/*   40:     */   public static int reflectionCompare(Object lhs, Object rhs, boolean compareTransients, Class reflectUpToClass)
/*   41:     */   {
/*   42: 275 */     return reflectionCompare(lhs, rhs, compareTransients, reflectUpToClass, null);
/*   43:     */   }
/*   44:     */   
/*   45:     */   public static int reflectionCompare(Object lhs, Object rhs, boolean compareTransients, Class reflectUpToClass, String[] excludeFields)
/*   46:     */   {
/*   47: 317 */     if (lhs == rhs) {
/*   48: 318 */       return 0;
/*   49:     */     }
/*   50: 320 */     if ((lhs == null) || (rhs == null)) {
/*   51: 321 */       throw new NullPointerException();
/*   52:     */     }
/*   53: 323 */     Class lhsClazz = lhs.getClass();
/*   54: 324 */     if (!lhsClazz.isInstance(rhs)) {
/*   55: 325 */       throw new ClassCastException();
/*   56:     */     }
/*   57: 327 */     CompareToBuilder compareToBuilder = new CompareToBuilder();
/*   58: 328 */     reflectionAppend(lhs, rhs, lhsClazz, compareToBuilder, compareTransients, excludeFields);
/*   59: 329 */     while ((lhsClazz.getSuperclass() != null) && (lhsClazz != reflectUpToClass))
/*   60:     */     {
/*   61: 330 */       lhsClazz = lhsClazz.getSuperclass();
/*   62: 331 */       reflectionAppend(lhs, rhs, lhsClazz, compareToBuilder, compareTransients, excludeFields);
/*   63:     */     }
/*   64: 333 */     return compareToBuilder.toComparison();
/*   65:     */   }
/*   66:     */   
/*   67:     */   private static void reflectionAppend(Object lhs, Object rhs, Class clazz, CompareToBuilder builder, boolean useTransients, String[] excludeFields)
/*   68:     */   {
/*   69: 355 */     Field[] fields = clazz.getDeclaredFields();
/*   70: 356 */     AccessibleObject.setAccessible(fields, true);
/*   71: 357 */     for (int i = 0; (i < fields.length) && (builder.comparison == 0); i++)
/*   72:     */     {
/*   73: 358 */       Field f = fields[i];
/*   74: 359 */       if ((!ArrayUtils.contains(excludeFields, f.getName())) && (f.getName().indexOf('$') == -1) && ((useTransients) || (!Modifier.isTransient(f.getModifiers()))) && (!Modifier.isStatic(f.getModifiers()))) {
/*   75:     */         try
/*   76:     */         {
/*   77: 364 */           builder.append(f.get(lhs), f.get(rhs));
/*   78:     */         }
/*   79:     */         catch (IllegalAccessException e)
/*   80:     */         {
/*   81: 368 */           throw new InternalError("Unexpected IllegalAccessException");
/*   82:     */         }
/*   83:     */       }
/*   84:     */     }
/*   85:     */   }
/*   86:     */   
/*   87:     */   public CompareToBuilder appendSuper(int superCompareTo)
/*   88:     */   {
/*   89: 384 */     if (this.comparison != 0) {
/*   90: 385 */       return this;
/*   91:     */     }
/*   92: 387 */     this.comparison = superCompareTo;
/*   93: 388 */     return this;
/*   94:     */   }
/*   95:     */   
/*   96:     */   public CompareToBuilder append(Object lhs, Object rhs)
/*   97:     */   {
/*   98: 412 */     return append(lhs, rhs, null);
/*   99:     */   }
/*  100:     */   
/*  101:     */   public CompareToBuilder append(Object lhs, Object rhs, Comparator comparator)
/*  102:     */   {
/*  103: 441 */     if (this.comparison != 0) {
/*  104: 442 */       return this;
/*  105:     */     }
/*  106: 444 */     if (lhs == rhs) {
/*  107: 445 */       return this;
/*  108:     */     }
/*  109: 447 */     if (lhs == null)
/*  110:     */     {
/*  111: 448 */       this.comparison = -1;
/*  112: 449 */       return this;
/*  113:     */     }
/*  114: 451 */     if (rhs == null)
/*  115:     */     {
/*  116: 452 */       this.comparison = 1;
/*  117: 453 */       return this;
/*  118:     */     }
/*  119: 455 */     if (lhs.getClass().isArray())
/*  120:     */     {
/*  121: 459 */       if ((lhs instanceof long[])) {
/*  122: 460 */         append((long[])lhs, (long[])rhs);
/*  123: 461 */       } else if ((lhs instanceof int[])) {
/*  124: 462 */         append((int[])lhs, (int[])rhs);
/*  125: 463 */       } else if ((lhs instanceof short[])) {
/*  126: 464 */         append((short[])lhs, (short[])rhs);
/*  127: 465 */       } else if ((lhs instanceof char[])) {
/*  128: 466 */         append((char[])lhs, (char[])rhs);
/*  129: 467 */       } else if ((lhs instanceof byte[])) {
/*  130: 468 */         append((byte[])lhs, (byte[])rhs);
/*  131: 469 */       } else if ((lhs instanceof double[])) {
/*  132: 470 */         append((double[])lhs, (double[])rhs);
/*  133: 471 */       } else if ((lhs instanceof float[])) {
/*  134: 472 */         append((float[])lhs, (float[])rhs);
/*  135: 473 */       } else if ((lhs instanceof boolean[])) {
/*  136: 474 */         append((boolean[])lhs, (boolean[])rhs);
/*  137:     */       } else {
/*  138: 478 */         append((Object[])lhs, (Object[])rhs, comparator);
/*  139:     */       }
/*  140:     */     }
/*  141: 482 */     else if (comparator == null) {
/*  142: 483 */       this.comparison = ((Comparable)lhs).compareTo(rhs);
/*  143:     */     } else {
/*  144: 485 */       this.comparison = comparator.compare(lhs, rhs);
/*  145:     */     }
/*  146: 488 */     return this;
/*  147:     */   }
/*  148:     */   
/*  149:     */   public CompareToBuilder append(long lhs, long rhs)
/*  150:     */   {
/*  151: 501 */     if (this.comparison != 0) {
/*  152: 502 */       return this;
/*  153:     */     }
/*  154: 504 */     this.comparison = (lhs > rhs ? 1 : lhs < rhs ? -1 : 0);
/*  155: 505 */     return this;
/*  156:     */   }
/*  157:     */   
/*  158:     */   public CompareToBuilder append(int lhs, int rhs)
/*  159:     */   {
/*  160: 517 */     if (this.comparison != 0) {
/*  161: 518 */       return this;
/*  162:     */     }
/*  163: 520 */     this.comparison = (lhs > rhs ? 1 : lhs < rhs ? -1 : 0);
/*  164: 521 */     return this;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public CompareToBuilder append(short lhs, short rhs)
/*  168:     */   {
/*  169: 533 */     if (this.comparison != 0) {
/*  170: 534 */       return this;
/*  171:     */     }
/*  172: 536 */     this.comparison = (lhs > rhs ? 1 : lhs < rhs ? -1 : 0);
/*  173: 537 */     return this;
/*  174:     */   }
/*  175:     */   
/*  176:     */   public CompareToBuilder append(char lhs, char rhs)
/*  177:     */   {
/*  178: 549 */     if (this.comparison != 0) {
/*  179: 550 */       return this;
/*  180:     */     }
/*  181: 552 */     this.comparison = (lhs > rhs ? 1 : lhs < rhs ? -1 : 0);
/*  182: 553 */     return this;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public CompareToBuilder append(byte lhs, byte rhs)
/*  186:     */   {
/*  187: 565 */     if (this.comparison != 0) {
/*  188: 566 */       return this;
/*  189:     */     }
/*  190: 568 */     this.comparison = (lhs > rhs ? 1 : lhs < rhs ? -1 : 0);
/*  191: 569 */     return this;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public CompareToBuilder append(double lhs, double rhs)
/*  195:     */   {
/*  196: 586 */     if (this.comparison != 0) {
/*  197: 587 */       return this;
/*  198:     */     }
/*  199: 589 */     this.comparison = NumberUtils.compare(lhs, rhs);
/*  200: 590 */     return this;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public CompareToBuilder append(float lhs, float rhs)
/*  204:     */   {
/*  205: 607 */     if (this.comparison != 0) {
/*  206: 608 */       return this;
/*  207:     */     }
/*  208: 610 */     this.comparison = NumberUtils.compare(lhs, rhs);
/*  209: 611 */     return this;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public CompareToBuilder append(boolean lhs, boolean rhs)
/*  213:     */   {
/*  214: 623 */     if (this.comparison != 0) {
/*  215: 624 */       return this;
/*  216:     */     }
/*  217: 626 */     if (lhs == rhs) {
/*  218: 627 */       return this;
/*  219:     */     }
/*  220: 629 */     if (!lhs) {
/*  221: 630 */       this.comparison = -1;
/*  222:     */     } else {
/*  223: 632 */       this.comparison = 1;
/*  224:     */     }
/*  225: 634 */     return this;
/*  226:     */   }
/*  227:     */   
/*  228:     */   public CompareToBuilder append(Object[] lhs, Object[] rhs)
/*  229:     */   {
/*  230: 659 */     return append(lhs, rhs, null);
/*  231:     */   }
/*  232:     */   
/*  233:     */   public CompareToBuilder append(Object[] lhs, Object[] rhs, Comparator comparator)
/*  234:     */   {
/*  235: 686 */     if (this.comparison != 0) {
/*  236: 687 */       return this;
/*  237:     */     }
/*  238: 689 */     if (lhs == rhs) {
/*  239: 690 */       return this;
/*  240:     */     }
/*  241: 692 */     if (lhs == null)
/*  242:     */     {
/*  243: 693 */       this.comparison = -1;
/*  244: 694 */       return this;
/*  245:     */     }
/*  246: 696 */     if (rhs == null)
/*  247:     */     {
/*  248: 697 */       this.comparison = 1;
/*  249: 698 */       return this;
/*  250:     */     }
/*  251: 700 */     if (lhs.length != rhs.length)
/*  252:     */     {
/*  253: 701 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  254: 702 */       return this;
/*  255:     */     }
/*  256: 704 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  257: 705 */       append(lhs[i], rhs[i], comparator);
/*  258:     */     }
/*  259: 707 */     return this;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public CompareToBuilder append(long[] lhs, long[] rhs)
/*  263:     */   {
/*  264: 726 */     if (this.comparison != 0) {
/*  265: 727 */       return this;
/*  266:     */     }
/*  267: 729 */     if (lhs == rhs) {
/*  268: 730 */       return this;
/*  269:     */     }
/*  270: 732 */     if (lhs == null)
/*  271:     */     {
/*  272: 733 */       this.comparison = -1;
/*  273: 734 */       return this;
/*  274:     */     }
/*  275: 736 */     if (rhs == null)
/*  276:     */     {
/*  277: 737 */       this.comparison = 1;
/*  278: 738 */       return this;
/*  279:     */     }
/*  280: 740 */     if (lhs.length != rhs.length)
/*  281:     */     {
/*  282: 741 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  283: 742 */       return this;
/*  284:     */     }
/*  285: 744 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  286: 745 */       append(lhs[i], rhs[i]);
/*  287:     */     }
/*  288: 747 */     return this;
/*  289:     */   }
/*  290:     */   
/*  291:     */   public CompareToBuilder append(int[] lhs, int[] rhs)
/*  292:     */   {
/*  293: 766 */     if (this.comparison != 0) {
/*  294: 767 */       return this;
/*  295:     */     }
/*  296: 769 */     if (lhs == rhs) {
/*  297: 770 */       return this;
/*  298:     */     }
/*  299: 772 */     if (lhs == null)
/*  300:     */     {
/*  301: 773 */       this.comparison = -1;
/*  302: 774 */       return this;
/*  303:     */     }
/*  304: 776 */     if (rhs == null)
/*  305:     */     {
/*  306: 777 */       this.comparison = 1;
/*  307: 778 */       return this;
/*  308:     */     }
/*  309: 780 */     if (lhs.length != rhs.length)
/*  310:     */     {
/*  311: 781 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  312: 782 */       return this;
/*  313:     */     }
/*  314: 784 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  315: 785 */       append(lhs[i], rhs[i]);
/*  316:     */     }
/*  317: 787 */     return this;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public CompareToBuilder append(short[] lhs, short[] rhs)
/*  321:     */   {
/*  322: 806 */     if (this.comparison != 0) {
/*  323: 807 */       return this;
/*  324:     */     }
/*  325: 809 */     if (lhs == rhs) {
/*  326: 810 */       return this;
/*  327:     */     }
/*  328: 812 */     if (lhs == null)
/*  329:     */     {
/*  330: 813 */       this.comparison = -1;
/*  331: 814 */       return this;
/*  332:     */     }
/*  333: 816 */     if (rhs == null)
/*  334:     */     {
/*  335: 817 */       this.comparison = 1;
/*  336: 818 */       return this;
/*  337:     */     }
/*  338: 820 */     if (lhs.length != rhs.length)
/*  339:     */     {
/*  340: 821 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  341: 822 */       return this;
/*  342:     */     }
/*  343: 824 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  344: 825 */       append(lhs[i], rhs[i]);
/*  345:     */     }
/*  346: 827 */     return this;
/*  347:     */   }
/*  348:     */   
/*  349:     */   public CompareToBuilder append(char[] lhs, char[] rhs)
/*  350:     */   {
/*  351: 846 */     if (this.comparison != 0) {
/*  352: 847 */       return this;
/*  353:     */     }
/*  354: 849 */     if (lhs == rhs) {
/*  355: 850 */       return this;
/*  356:     */     }
/*  357: 852 */     if (lhs == null)
/*  358:     */     {
/*  359: 853 */       this.comparison = -1;
/*  360: 854 */       return this;
/*  361:     */     }
/*  362: 856 */     if (rhs == null)
/*  363:     */     {
/*  364: 857 */       this.comparison = 1;
/*  365: 858 */       return this;
/*  366:     */     }
/*  367: 860 */     if (lhs.length != rhs.length)
/*  368:     */     {
/*  369: 861 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  370: 862 */       return this;
/*  371:     */     }
/*  372: 864 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  373: 865 */       append(lhs[i], rhs[i]);
/*  374:     */     }
/*  375: 867 */     return this;
/*  376:     */   }
/*  377:     */   
/*  378:     */   public CompareToBuilder append(byte[] lhs, byte[] rhs)
/*  379:     */   {
/*  380: 886 */     if (this.comparison != 0) {
/*  381: 887 */       return this;
/*  382:     */     }
/*  383: 889 */     if (lhs == rhs) {
/*  384: 890 */       return this;
/*  385:     */     }
/*  386: 892 */     if (lhs == null)
/*  387:     */     {
/*  388: 893 */       this.comparison = -1;
/*  389: 894 */       return this;
/*  390:     */     }
/*  391: 896 */     if (rhs == null)
/*  392:     */     {
/*  393: 897 */       this.comparison = 1;
/*  394: 898 */       return this;
/*  395:     */     }
/*  396: 900 */     if (lhs.length != rhs.length)
/*  397:     */     {
/*  398: 901 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  399: 902 */       return this;
/*  400:     */     }
/*  401: 904 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  402: 905 */       append(lhs[i], rhs[i]);
/*  403:     */     }
/*  404: 907 */     return this;
/*  405:     */   }
/*  406:     */   
/*  407:     */   public CompareToBuilder append(double[] lhs, double[] rhs)
/*  408:     */   {
/*  409: 926 */     if (this.comparison != 0) {
/*  410: 927 */       return this;
/*  411:     */     }
/*  412: 929 */     if (lhs == rhs) {
/*  413: 930 */       return this;
/*  414:     */     }
/*  415: 932 */     if (lhs == null)
/*  416:     */     {
/*  417: 933 */       this.comparison = -1;
/*  418: 934 */       return this;
/*  419:     */     }
/*  420: 936 */     if (rhs == null)
/*  421:     */     {
/*  422: 937 */       this.comparison = 1;
/*  423: 938 */       return this;
/*  424:     */     }
/*  425: 940 */     if (lhs.length != rhs.length)
/*  426:     */     {
/*  427: 941 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  428: 942 */       return this;
/*  429:     */     }
/*  430: 944 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  431: 945 */       append(lhs[i], rhs[i]);
/*  432:     */     }
/*  433: 947 */     return this;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public CompareToBuilder append(float[] lhs, float[] rhs)
/*  437:     */   {
/*  438: 966 */     if (this.comparison != 0) {
/*  439: 967 */       return this;
/*  440:     */     }
/*  441: 969 */     if (lhs == rhs) {
/*  442: 970 */       return this;
/*  443:     */     }
/*  444: 972 */     if (lhs == null)
/*  445:     */     {
/*  446: 973 */       this.comparison = -1;
/*  447: 974 */       return this;
/*  448:     */     }
/*  449: 976 */     if (rhs == null)
/*  450:     */     {
/*  451: 977 */       this.comparison = 1;
/*  452: 978 */       return this;
/*  453:     */     }
/*  454: 980 */     if (lhs.length != rhs.length)
/*  455:     */     {
/*  456: 981 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  457: 982 */       return this;
/*  458:     */     }
/*  459: 984 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  460: 985 */       append(lhs[i], rhs[i]);
/*  461:     */     }
/*  462: 987 */     return this;
/*  463:     */   }
/*  464:     */   
/*  465:     */   public CompareToBuilder append(boolean[] lhs, boolean[] rhs)
/*  466:     */   {
/*  467:1006 */     if (this.comparison != 0) {
/*  468:1007 */       return this;
/*  469:     */     }
/*  470:1009 */     if (lhs == rhs) {
/*  471:1010 */       return this;
/*  472:     */     }
/*  473:1012 */     if (lhs == null)
/*  474:     */     {
/*  475:1013 */       this.comparison = -1;
/*  476:1014 */       return this;
/*  477:     */     }
/*  478:1016 */     if (rhs == null)
/*  479:     */     {
/*  480:1017 */       this.comparison = 1;
/*  481:1018 */       return this;
/*  482:     */     }
/*  483:1020 */     if (lhs.length != rhs.length)
/*  484:     */     {
/*  485:1021 */       this.comparison = (lhs.length < rhs.length ? -1 : 1);
/*  486:1022 */       return this;
/*  487:     */     }
/*  488:1024 */     for (int i = 0; (i < lhs.length) && (this.comparison == 0); i++) {
/*  489:1025 */       append(lhs[i], rhs[i]);
/*  490:     */     }
/*  491:1027 */     return this;
/*  492:     */   }
/*  493:     */   
/*  494:     */   public int toComparison()
/*  495:     */   {
/*  496:1040 */     return this.comparison;
/*  497:     */   }
/*  498:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.builder.CompareToBuilder
 * JD-Core Version:    0.7.0.1
 */