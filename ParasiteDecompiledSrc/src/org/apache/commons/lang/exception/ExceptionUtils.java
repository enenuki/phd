/*    1:     */ package org.apache.commons.lang.exception;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.io.PrintWriter;
/*    5:     */ import java.io.StringWriter;
/*    6:     */ import java.lang.reflect.Field;
/*    7:     */ import java.lang.reflect.InvocationTargetException;
/*    8:     */ import java.lang.reflect.Method;
/*    9:     */ import java.sql.SQLException;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.Arrays;
/*   12:     */ import java.util.List;
/*   13:     */ import java.util.StringTokenizer;
/*   14:     */ import org.apache.commons.lang.ArrayUtils;
/*   15:     */ import org.apache.commons.lang.ClassUtils;
/*   16:     */ import org.apache.commons.lang.NullArgumentException;
/*   17:     */ import org.apache.commons.lang.StringUtils;
/*   18:     */ import org.apache.commons.lang.SystemUtils;
/*   19:     */ 
/*   20:     */ public class ExceptionUtils
/*   21:     */ {
/*   22:     */   static final String WRAPPED_MARKER = " [wrapped] ";
/*   23:  60 */   private static final Object CAUSE_METHOD_NAMES_LOCK = new Object();
/*   24:  65 */   private static String[] CAUSE_METHOD_NAMES = { "getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable" };
/*   25:     */   private static final Method THROWABLE_CAUSE_METHOD;
/*   26:     */   private static final Method THROWABLE_INITCAUSE_METHOD;
/*   27:     */   
/*   28:     */   static
/*   29:     */   {
/*   30:     */     Method causeMethod;
/*   31:     */     try
/*   32:     */     {
/*   33:  93 */       causeMethod = Throwable.class.getMethod("getCause", null);
/*   34:     */     }
/*   35:     */     catch (Exception e)
/*   36:     */     {
/*   37:  95 */       causeMethod = null;
/*   38:     */     }
/*   39:  97 */     THROWABLE_CAUSE_METHOD = causeMethod;
/*   40:     */     try
/*   41:     */     {
/*   42:  99 */       causeMethod = class$java$lang$Throwable.getMethod("initCause", new Class[] { Throwable.class });
/*   43:     */     }
/*   44:     */     catch (Exception e)
/*   45:     */     {
/*   46: 101 */       causeMethod = null;
/*   47:     */     }
/*   48: 103 */     THROWABLE_INITCAUSE_METHOD = causeMethod;
/*   49:     */   }
/*   50:     */   
/*   51:     */   public static void addCauseMethodName(String methodName)
/*   52:     */   {
/*   53: 126 */     if ((StringUtils.isNotEmpty(methodName)) && (!isCauseMethodName(methodName)))
/*   54:     */     {
/*   55: 127 */       List list = getCauseMethodNameList();
/*   56: 128 */       if (list.add(methodName)) {
/*   57: 129 */         synchronized (CAUSE_METHOD_NAMES_LOCK)
/*   58:     */         {
/*   59: 130 */           CAUSE_METHOD_NAMES = toArray(list);
/*   60:     */         }
/*   61:     */       }
/*   62:     */     }
/*   63:     */   }
/*   64:     */   
/*   65:     */   public static void removeCauseMethodName(String methodName)
/*   66:     */   {
/*   67: 145 */     if (StringUtils.isNotEmpty(methodName))
/*   68:     */     {
/*   69: 146 */       List list = getCauseMethodNameList();
/*   70: 147 */       if (list.remove(methodName)) {
/*   71: 148 */         synchronized (CAUSE_METHOD_NAMES_LOCK)
/*   72:     */         {
/*   73: 149 */           CAUSE_METHOD_NAMES = toArray(list);
/*   74:     */         }
/*   75:     */       }
/*   76:     */     }
/*   77:     */   }
/*   78:     */   
/*   79:     */   public static boolean setCause(Throwable target, Throwable cause)
/*   80:     */   {
/*   81: 184 */     if (target == null) {
/*   82: 185 */       throw new NullArgumentException("target");
/*   83:     */     }
/*   84: 187 */     Object[] causeArgs = { cause };
/*   85: 188 */     boolean modifiedTarget = false;
/*   86: 189 */     if (THROWABLE_INITCAUSE_METHOD != null) {
/*   87:     */       try
/*   88:     */       {
/*   89: 191 */         THROWABLE_INITCAUSE_METHOD.invoke(target, causeArgs);
/*   90: 192 */         modifiedTarget = true;
/*   91:     */       }
/*   92:     */       catch (IllegalAccessException ignored) {}catch (InvocationTargetException ignored) {}
/*   93:     */     }
/*   94:     */     try
/*   95:     */     {
/*   96: 200 */       Method setCauseMethod = target.getClass().getMethod("setCause", new Class[] { Throwable.class });
/*   97: 201 */       setCauseMethod.invoke(target, causeArgs);
/*   98: 202 */       modifiedTarget = true;
/*   99:     */     }
/*  100:     */     catch (NoSuchMethodException ignored) {}catch (IllegalAccessException ignored) {}catch (InvocationTargetException ignored) {}
/*  101: 210 */     return modifiedTarget;
/*  102:     */   }
/*  103:     */   
/*  104:     */   private static String[] toArray(List list)
/*  105:     */   {
/*  106: 219 */     return (String[])list.toArray(new String[list.size()]);
/*  107:     */   }
/*  108:     */   
/*  109:     */   private static ArrayList getCauseMethodNameList()
/*  110:     */   {
/*  111: 228 */     synchronized (CAUSE_METHOD_NAMES_LOCK)
/*  112:     */     {
/*  113: 229 */       return new ArrayList(Arrays.asList(CAUSE_METHOD_NAMES));
/*  114:     */     }
/*  115:     */   }
/*  116:     */   
/*  117:     */   public static boolean isCauseMethodName(String methodName)
/*  118:     */   {
/*  119: 243 */     synchronized (CAUSE_METHOD_NAMES_LOCK)
/*  120:     */     {
/*  121: 244 */       return ArrayUtils.indexOf(CAUSE_METHOD_NAMES, methodName) >= 0;
/*  122:     */     }
/*  123:     */   }
/*  124:     */   
/*  125:     */   public static Throwable getCause(Throwable throwable)
/*  126:     */   {
/*  127: 281 */     synchronized (CAUSE_METHOD_NAMES_LOCK)
/*  128:     */     {
/*  129: 282 */       return getCause(throwable, CAUSE_METHOD_NAMES);
/*  130:     */     }
/*  131:     */   }
/*  132:     */   
/*  133:     */   public static Throwable getCause(Throwable throwable, String[] methodNames)
/*  134:     */   {
/*  135: 305 */     if (throwable == null) {
/*  136: 306 */       return null;
/*  137:     */     }
/*  138: 308 */     Throwable cause = getCauseUsingWellKnownTypes(throwable);
/*  139: 309 */     if (cause == null)
/*  140:     */     {
/*  141: 310 */       if (methodNames == null) {
/*  142: 311 */         synchronized (CAUSE_METHOD_NAMES_LOCK)
/*  143:     */         {
/*  144: 312 */           methodNames = CAUSE_METHOD_NAMES;
/*  145:     */         }
/*  146:     */       }
/*  147: 315 */       for (int i = 0; i < methodNames.length; i++)
/*  148:     */       {
/*  149: 316 */         String methodName = methodNames[i];
/*  150: 317 */         if (methodName != null)
/*  151:     */         {
/*  152: 318 */           cause = getCauseUsingMethodName(throwable, methodName);
/*  153: 319 */           if (cause != null) {
/*  154:     */             break;
/*  155:     */           }
/*  156:     */         }
/*  157:     */       }
/*  158: 325 */       if (cause == null) {
/*  159: 326 */         cause = getCauseUsingFieldName(throwable, "detail");
/*  160:     */       }
/*  161:     */     }
/*  162: 329 */     return cause;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public static Throwable getRootCause(Throwable throwable)
/*  166:     */   {
/*  167: 350 */     List list = getThrowableList(throwable);
/*  168: 351 */     return list.size() < 2 ? null : (Throwable)list.get(list.size() - 1);
/*  169:     */   }
/*  170:     */   
/*  171:     */   private static Throwable getCauseUsingWellKnownTypes(Throwable throwable)
/*  172:     */   {
/*  173: 365 */     if ((throwable instanceof Nestable)) {
/*  174: 366 */       return ((Nestable)throwable).getCause();
/*  175:     */     }
/*  176: 367 */     if ((throwable instanceof SQLException)) {
/*  177: 368 */       return ((SQLException)throwable).getNextException();
/*  178:     */     }
/*  179: 369 */     if ((throwable instanceof InvocationTargetException)) {
/*  180: 370 */       return ((InvocationTargetException)throwable).getTargetException();
/*  181:     */     }
/*  182: 372 */     return null;
/*  183:     */   }
/*  184:     */   
/*  185:     */   private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName)
/*  186:     */   {
/*  187: 384 */     Method method = null;
/*  188:     */     try
/*  189:     */     {
/*  190: 386 */       method = throwable.getClass().getMethod(methodName, null);
/*  191:     */     }
/*  192:     */     catch (NoSuchMethodException ignored) {}catch (SecurityException ignored) {}
/*  193: 393 */     if ((method != null) && (Throwable.class.isAssignableFrom(method.getReturnType()))) {
/*  194:     */       try
/*  195:     */       {
/*  196: 395 */         return (Throwable)method.invoke(throwable, ArrayUtils.EMPTY_OBJECT_ARRAY);
/*  197:     */       }
/*  198:     */       catch (IllegalAccessException ignored) {}catch (IllegalArgumentException ignored) {}catch (InvocationTargetException ignored) {}
/*  199:     */     }
/*  200: 404 */     return null;
/*  201:     */   }
/*  202:     */   
/*  203:     */   private static Throwable getCauseUsingFieldName(Throwable throwable, String fieldName)
/*  204:     */   {
/*  205: 415 */     Field field = null;
/*  206:     */     try
/*  207:     */     {
/*  208: 417 */       field = throwable.getClass().getField(fieldName);
/*  209:     */     }
/*  210:     */     catch (NoSuchFieldException ignored) {}catch (SecurityException ignored) {}
/*  211: 424 */     if ((field != null) && (Throwable.class.isAssignableFrom(field.getType()))) {
/*  212:     */       try
/*  213:     */       {
/*  214: 426 */         return (Throwable)field.get(throwable);
/*  215:     */       }
/*  216:     */       catch (IllegalAccessException ignored) {}catch (IllegalArgumentException ignored) {}
/*  217:     */     }
/*  218: 433 */     return null;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public static boolean isThrowableNested()
/*  222:     */   {
/*  223: 446 */     return THROWABLE_CAUSE_METHOD != null;
/*  224:     */   }
/*  225:     */   
/*  226:     */   public static boolean isNestedThrowable(Throwable throwable)
/*  227:     */   {
/*  228: 459 */     if (throwable == null) {
/*  229: 460 */       return false;
/*  230:     */     }
/*  231: 463 */     if ((throwable instanceof Nestable)) {
/*  232: 464 */       return true;
/*  233:     */     }
/*  234: 465 */     if ((throwable instanceof SQLException)) {
/*  235: 466 */       return true;
/*  236:     */     }
/*  237: 467 */     if ((throwable instanceof InvocationTargetException)) {
/*  238: 468 */       return true;
/*  239:     */     }
/*  240: 469 */     if (isThrowableNested()) {
/*  241: 470 */       return true;
/*  242:     */     }
/*  243: 473 */     Class cls = throwable.getClass();
/*  244: 474 */     synchronized (CAUSE_METHOD_NAMES_LOCK)
/*  245:     */     {
/*  246: 475 */       int i = 0;
/*  247: 475 */       for (int isize = CAUSE_METHOD_NAMES.length; i < isize; i++) {
/*  248:     */         try
/*  249:     */         {
/*  250: 477 */           Method method = cls.getMethod(CAUSE_METHOD_NAMES[i], null);
/*  251: 478 */           if ((method != null) && (Throwable.class.isAssignableFrom(method.getReturnType()))) {
/*  252: 479 */             return true;
/*  253:     */           }
/*  254:     */         }
/*  255:     */         catch (NoSuchMethodException ignored) {}catch (SecurityException ignored) {}
/*  256:     */       }
/*  257:     */     }
/*  258:     */     try
/*  259:     */     {
/*  260: 490 */       Field field = cls.getField("detail");
/*  261: 491 */       if (field != null) {
/*  262: 492 */         return true;
/*  263:     */       }
/*  264:     */     }
/*  265:     */     catch (NoSuchFieldException ignored) {}catch (SecurityException ignored) {}
/*  266: 500 */     return false;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public static int getThrowableCount(Throwable throwable)
/*  270:     */   {
/*  271: 521 */     return getThrowableList(throwable).size();
/*  272:     */   }
/*  273:     */   
/*  274:     */   public static Throwable[] getThrowables(Throwable throwable)
/*  275:     */   {
/*  276: 544 */     List list = getThrowableList(throwable);
/*  277: 545 */     return (Throwable[])list.toArray(new Throwable[list.size()]);
/*  278:     */   }
/*  279:     */   
/*  280:     */   public static List getThrowableList(Throwable throwable)
/*  281:     */   {
/*  282: 568 */     List list = new ArrayList();
/*  283: 569 */     while ((throwable != null) && (!list.contains(throwable)))
/*  284:     */     {
/*  285: 570 */       list.add(throwable);
/*  286: 571 */       throwable = getCause(throwable);
/*  287:     */     }
/*  288: 573 */     return list;
/*  289:     */   }
/*  290:     */   
/*  291:     */   public static int indexOfThrowable(Throwable throwable, Class clazz)
/*  292:     */   {
/*  293: 592 */     return indexOf(throwable, clazz, 0, false);
/*  294:     */   }
/*  295:     */   
/*  296:     */   public static int indexOfThrowable(Throwable throwable, Class clazz, int fromIndex)
/*  297:     */   {
/*  298: 615 */     return indexOf(throwable, clazz, fromIndex, false);
/*  299:     */   }
/*  300:     */   
/*  301:     */   public static int indexOfType(Throwable throwable, Class type)
/*  302:     */   {
/*  303: 635 */     return indexOf(throwable, type, 0, true);
/*  304:     */   }
/*  305:     */   
/*  306:     */   public static int indexOfType(Throwable throwable, Class type, int fromIndex)
/*  307:     */   {
/*  308: 659 */     return indexOf(throwable, type, fromIndex, true);
/*  309:     */   }
/*  310:     */   
/*  311:     */   private static int indexOf(Throwable throwable, Class type, int fromIndex, boolean subclass)
/*  312:     */   {
/*  313: 674 */     if ((throwable == null) || (type == null)) {
/*  314: 675 */       return -1;
/*  315:     */     }
/*  316: 677 */     if (fromIndex < 0) {
/*  317: 678 */       fromIndex = 0;
/*  318:     */     }
/*  319: 680 */     Throwable[] throwables = getThrowables(throwable);
/*  320: 681 */     if (fromIndex >= throwables.length) {
/*  321: 682 */       return -1;
/*  322:     */     }
/*  323: 684 */     if (subclass) {
/*  324: 685 */       for (int i = fromIndex; i < throwables.length; i++) {
/*  325: 686 */         if (type.isAssignableFrom(throwables[i].getClass())) {
/*  326: 687 */           return i;
/*  327:     */         }
/*  328:     */       }
/*  329:     */     } else {
/*  330: 691 */       for (int i = fromIndex; i < throwables.length; i++) {
/*  331: 692 */         if (type.equals(throwables[i].getClass())) {
/*  332: 693 */           return i;
/*  333:     */         }
/*  334:     */       }
/*  335:     */     }
/*  336: 697 */     return -1;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public static void printRootCauseStackTrace(Throwable throwable)
/*  340:     */   {
/*  341: 720 */     printRootCauseStackTrace(throwable, System.err);
/*  342:     */   }
/*  343:     */   
/*  344:     */   public static void printRootCauseStackTrace(Throwable throwable, PrintStream stream)
/*  345:     */   {
/*  346: 743 */     if (throwable == null) {
/*  347: 744 */       return;
/*  348:     */     }
/*  349: 746 */     if (stream == null) {
/*  350: 747 */       throw new IllegalArgumentException("The PrintStream must not be null");
/*  351:     */     }
/*  352: 749 */     String[] trace = getRootCauseStackTrace(throwable);
/*  353: 750 */     for (int i = 0; i < trace.length; i++) {
/*  354: 751 */       stream.println(trace[i]);
/*  355:     */     }
/*  356: 753 */     stream.flush();
/*  357:     */   }
/*  358:     */   
/*  359:     */   public static void printRootCauseStackTrace(Throwable throwable, PrintWriter writer)
/*  360:     */   {
/*  361: 776 */     if (throwable == null) {
/*  362: 777 */       return;
/*  363:     */     }
/*  364: 779 */     if (writer == null) {
/*  365: 780 */       throw new IllegalArgumentException("The PrintWriter must not be null");
/*  366:     */     }
/*  367: 782 */     String[] trace = getRootCauseStackTrace(throwable);
/*  368: 783 */     for (int i = 0; i < trace.length; i++) {
/*  369: 784 */       writer.println(trace[i]);
/*  370:     */     }
/*  371: 786 */     writer.flush();
/*  372:     */   }
/*  373:     */   
/*  374:     */   public static String[] getRootCauseStackTrace(Throwable throwable)
/*  375:     */   {
/*  376: 804 */     if (throwable == null) {
/*  377: 805 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*  378:     */     }
/*  379: 807 */     Throwable[] throwables = getThrowables(throwable);
/*  380: 808 */     int count = throwables.length;
/*  381: 809 */     ArrayList frames = new ArrayList();
/*  382: 810 */     List nextTrace = getStackFrameList(throwables[(count - 1)]);
/*  383: 811 */     int i = count;
/*  384:     */     for (;;)
/*  385:     */     {
/*  386: 811 */       i--;
/*  387: 811 */       if (i < 0) {
/*  388:     */         break;
/*  389:     */       }
/*  390: 812 */       List trace = nextTrace;
/*  391: 813 */       if (i != 0)
/*  392:     */       {
/*  393: 814 */         nextTrace = getStackFrameList(throwables[(i - 1)]);
/*  394: 815 */         removeCommonFrames(trace, nextTrace);
/*  395:     */       }
/*  396: 817 */       if (i == count - 1) {
/*  397: 818 */         frames.add(throwables[i].toString());
/*  398:     */       } else {
/*  399: 820 */         frames.add(" [wrapped] " + throwables[i].toString());
/*  400:     */       }
/*  401: 822 */       for (int j = 0; j < trace.size(); j++) {
/*  402: 823 */         frames.add(trace.get(j));
/*  403:     */       }
/*  404:     */     }
/*  405: 826 */     return (String[])frames.toArray(new String[0]);
/*  406:     */   }
/*  407:     */   
/*  408:     */   public static void removeCommonFrames(List causeFrames, List wrapperFrames)
/*  409:     */   {
/*  410: 838 */     if ((causeFrames == null) || (wrapperFrames == null)) {
/*  411: 839 */       throw new IllegalArgumentException("The List must not be null");
/*  412:     */     }
/*  413: 841 */     int causeFrameIndex = causeFrames.size() - 1;
/*  414: 842 */     int wrapperFrameIndex = wrapperFrames.size() - 1;
/*  415: 843 */     while ((causeFrameIndex >= 0) && (wrapperFrameIndex >= 0))
/*  416:     */     {
/*  417: 846 */       String causeFrame = (String)causeFrames.get(causeFrameIndex);
/*  418: 847 */       String wrapperFrame = (String)wrapperFrames.get(wrapperFrameIndex);
/*  419: 848 */       if (causeFrame.equals(wrapperFrame)) {
/*  420: 849 */         causeFrames.remove(causeFrameIndex);
/*  421:     */       }
/*  422: 851 */       causeFrameIndex--;
/*  423: 852 */       wrapperFrameIndex--;
/*  424:     */     }
/*  425:     */   }
/*  426:     */   
/*  427:     */   public static String getFullStackTrace(Throwable throwable)
/*  428:     */   {
/*  429: 868 */     StringWriter sw = new StringWriter();
/*  430: 869 */     PrintWriter pw = new PrintWriter(sw, true);
/*  431: 870 */     Throwable[] ts = getThrowables(throwable);
/*  432: 871 */     for (int i = 0; i < ts.length; i++)
/*  433:     */     {
/*  434: 872 */       ts[i].printStackTrace(pw);
/*  435: 873 */       if (isNestedThrowable(ts[i])) {
/*  436:     */         break;
/*  437:     */       }
/*  438:     */     }
/*  439: 877 */     return sw.getBuffer().toString();
/*  440:     */   }
/*  441:     */   
/*  442:     */   public static String getStackTrace(Throwable throwable)
/*  443:     */   {
/*  444: 894 */     StringWriter sw = new StringWriter();
/*  445: 895 */     PrintWriter pw = new PrintWriter(sw, true);
/*  446: 896 */     throwable.printStackTrace(pw);
/*  447: 897 */     return sw.getBuffer().toString();
/*  448:     */   }
/*  449:     */   
/*  450:     */   public static String[] getStackFrames(Throwable throwable)
/*  451:     */   {
/*  452: 914 */     if (throwable == null) {
/*  453: 915 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*  454:     */     }
/*  455: 917 */     return getStackFrames(getStackTrace(throwable));
/*  456:     */   }
/*  457:     */   
/*  458:     */   static String[] getStackFrames(String stackTrace)
/*  459:     */   {
/*  460: 934 */     String linebreak = SystemUtils.LINE_SEPARATOR;
/*  461: 935 */     StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
/*  462: 936 */     List list = new ArrayList();
/*  463: 937 */     while (frames.hasMoreTokens()) {
/*  464: 938 */       list.add(frames.nextToken());
/*  465:     */     }
/*  466: 940 */     return toArray(list);
/*  467:     */   }
/*  468:     */   
/*  469:     */   static List getStackFrameList(Throwable t)
/*  470:     */   {
/*  471: 956 */     String stackTrace = getStackTrace(t);
/*  472: 957 */     String linebreak = SystemUtils.LINE_SEPARATOR;
/*  473: 958 */     StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
/*  474: 959 */     List list = new ArrayList();
/*  475: 960 */     boolean traceStarted = false;
/*  476: 961 */     while (frames.hasMoreTokens())
/*  477:     */     {
/*  478: 962 */       String token = frames.nextToken();
/*  479:     */       
/*  480: 964 */       int at = token.indexOf("at");
/*  481: 965 */       if ((at != -1) && (token.substring(0, at).trim().length() == 0))
/*  482:     */       {
/*  483: 966 */         traceStarted = true;
/*  484: 967 */         list.add(token);
/*  485:     */       }
/*  486:     */       else
/*  487:     */       {
/*  488: 968 */         if (traceStarted) {
/*  489:     */           break;
/*  490:     */         }
/*  491:     */       }
/*  492:     */     }
/*  493: 972 */     return list;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public static String getMessage(Throwable th)
/*  497:     */   {
/*  498: 987 */     if (th == null) {
/*  499: 988 */       return "";
/*  500:     */     }
/*  501: 990 */     String clsName = ClassUtils.getShortClassName(th, null);
/*  502: 991 */     String msg = th.getMessage();
/*  503: 992 */     return clsName + ": " + StringUtils.defaultString(msg);
/*  504:     */   }
/*  505:     */   
/*  506:     */   public static String getRootCauseMessage(Throwable th)
/*  507:     */   {
/*  508:1007 */     Throwable root = getRootCause(th);
/*  509:1008 */     root = root == null ? th : root;
/*  510:1009 */     return getMessage(root);
/*  511:     */   }
/*  512:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.exception.ExceptionUtils
 * JD-Core Version:    0.7.0.1
 */