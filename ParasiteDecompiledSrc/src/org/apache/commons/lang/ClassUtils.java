/*    1:     */ package org.apache.commons.lang;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.Method;
/*    4:     */ import java.lang.reflect.Modifier;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.HashMap;
/*    7:     */ import java.util.Iterator;
/*    8:     */ import java.util.List;
/*    9:     */ import java.util.Map;
/*   10:     */ import java.util.Set;
/*   11:     */ import org.apache.commons.lang.text.StrBuilder;
/*   12:     */ 
/*   13:     */ public class ClassUtils
/*   14:     */ {
/*   15:     */   public static final char PACKAGE_SEPARATOR_CHAR = '.';
/*   16:  58 */   public static final String PACKAGE_SEPARATOR = String.valueOf('.');
/*   17:     */   public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
/*   18:  68 */   public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');
/*   19:  73 */   private static final Map primitiveWrapperMap = new HashMap();
/*   20:     */   private static final Map wrapperPrimitiveMap;
/*   21:     */   private static final Map abbreviationMap;
/*   22:     */   private static final Map reverseAbbreviationMap;
/*   23:     */   
/*   24:     */   private static void addAbbreviation(String primitive, String abbreviation)
/*   25:     */   {
/*   26: 117 */     abbreviationMap.put(primitive, abbreviation);
/*   27: 118 */     reverseAbbreviationMap.put(abbreviation, primitive);
/*   28:     */   }
/*   29:     */   
/*   30:     */   static
/*   31:     */   {
/*   32:  75 */     primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
/*   33:  76 */     primitiveWrapperMap.put(Byte.TYPE, Byte.class);
/*   34:  77 */     primitiveWrapperMap.put(Character.TYPE, Character.class);
/*   35:  78 */     primitiveWrapperMap.put(Short.TYPE, Short.class);
/*   36:  79 */     primitiveWrapperMap.put(Integer.TYPE, Integer.class);
/*   37:  80 */     primitiveWrapperMap.put(Long.TYPE, Long.class);
/*   38:  81 */     primitiveWrapperMap.put(Double.TYPE, Double.class);
/*   39:  82 */     primitiveWrapperMap.put(Float.TYPE, Float.class);
/*   40:  83 */     primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
/*   41:     */     
/*   42:     */ 
/*   43:     */ 
/*   44:     */ 
/*   45:     */ 
/*   46:  89 */     wrapperPrimitiveMap = new HashMap();
/*   47:  91 */     for (Iterator it = primitiveWrapperMap.keySet().iterator(); it.hasNext();)
/*   48:     */     {
/*   49:  92 */       Class primitiveClass = (Class)it.next();
/*   50:  93 */       Class wrapperClass = (Class)primitiveWrapperMap.get(primitiveClass);
/*   51:  94 */       if (!primitiveClass.equals(wrapperClass)) {
/*   52:  95 */         wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
/*   53:     */       }
/*   54:     */     }
/*   55: 103 */     abbreviationMap = new HashMap();
/*   56:     */     
/*   57:     */ 
/*   58:     */ 
/*   59:     */ 
/*   60: 108 */     reverseAbbreviationMap = new HashMap();
/*   61:     */     
/*   62:     */ 
/*   63:     */ 
/*   64:     */ 
/*   65:     */ 
/*   66:     */ 
/*   67:     */ 
/*   68:     */ 
/*   69:     */ 
/*   70:     */ 
/*   71:     */ 
/*   72:     */ 
/*   73:     */ 
/*   74:     */ 
/*   75:     */ 
/*   76:     */ 
/*   77: 125 */     addAbbreviation("int", "I");
/*   78: 126 */     addAbbreviation("boolean", "Z");
/*   79: 127 */     addAbbreviation("float", "F");
/*   80: 128 */     addAbbreviation("long", "J");
/*   81: 129 */     addAbbreviation("short", "S");
/*   82: 130 */     addAbbreviation("byte", "B");
/*   83: 131 */     addAbbreviation("double", "D");
/*   84: 132 */     addAbbreviation("char", "C");
/*   85:     */   }
/*   86:     */   
/*   87:     */   public static String getShortClassName(Object object, String valueIfNull)
/*   88:     */   {
/*   89: 157 */     if (object == null) {
/*   90: 158 */       return valueIfNull;
/*   91:     */     }
/*   92: 160 */     return getShortClassName(object.getClass());
/*   93:     */   }
/*   94:     */   
/*   95:     */   public static String getShortClassName(Class cls)
/*   96:     */   {
/*   97: 170 */     if (cls == null) {
/*   98: 171 */       return "";
/*   99:     */     }
/*  100: 173 */     return getShortClassName(cls.getName());
/*  101:     */   }
/*  102:     */   
/*  103:     */   public static String getShortClassName(String className)
/*  104:     */   {
/*  105: 185 */     if (className == null) {
/*  106: 186 */       return "";
/*  107:     */     }
/*  108: 188 */     if (className.length() == 0) {
/*  109: 189 */       return "";
/*  110:     */     }
/*  111: 192 */     StrBuilder arrayPrefix = new StrBuilder();
/*  112: 195 */     if (className.startsWith("["))
/*  113:     */     {
/*  114: 196 */       while (className.charAt(0) == '[')
/*  115:     */       {
/*  116: 197 */         className = className.substring(1);
/*  117: 198 */         arrayPrefix.append("[]");
/*  118:     */       }
/*  119: 201 */       if ((className.charAt(0) == 'L') && (className.charAt(className.length() - 1) == ';')) {
/*  120: 202 */         className = className.substring(1, className.length() - 1);
/*  121:     */       }
/*  122:     */     }
/*  123: 206 */     if (reverseAbbreviationMap.containsKey(className)) {
/*  124: 207 */       className = (String)reverseAbbreviationMap.get(className);
/*  125:     */     }
/*  126: 210 */     int lastDotIdx = className.lastIndexOf('.');
/*  127: 211 */     int innerIdx = className.indexOf('$', lastDotIdx == -1 ? 0 : lastDotIdx + 1);
/*  128:     */     
/*  129: 213 */     String out = className.substring(lastDotIdx + 1);
/*  130: 214 */     if (innerIdx != -1) {
/*  131: 215 */       out = out.replace('$', '.');
/*  132:     */     }
/*  133: 217 */     return out + arrayPrefix;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public static String getPackageName(Object object, String valueIfNull)
/*  137:     */   {
/*  138: 230 */     if (object == null) {
/*  139: 231 */       return valueIfNull;
/*  140:     */     }
/*  141: 233 */     return getPackageName(object.getClass());
/*  142:     */   }
/*  143:     */   
/*  144:     */   public static String getPackageName(Class cls)
/*  145:     */   {
/*  146: 243 */     if (cls == null) {
/*  147: 244 */       return "";
/*  148:     */     }
/*  149: 246 */     return getPackageName(cls.getName());
/*  150:     */   }
/*  151:     */   
/*  152:     */   public static String getPackageName(String className)
/*  153:     */   {
/*  154: 259 */     if ((className == null) || (className.length() == 0)) {
/*  155: 260 */       return "";
/*  156:     */     }
/*  157: 264 */     while (className.charAt(0) == '[') {
/*  158: 265 */       className = className.substring(1);
/*  159:     */     }
/*  160: 268 */     if ((className.charAt(0) == 'L') && (className.charAt(className.length() - 1) == ';')) {
/*  161: 269 */       className = className.substring(1);
/*  162:     */     }
/*  163: 272 */     int i = className.lastIndexOf('.');
/*  164: 273 */     if (i == -1) {
/*  165: 274 */       return "";
/*  166:     */     }
/*  167: 276 */     return className.substring(0, i);
/*  168:     */   }
/*  169:     */   
/*  170:     */   public static List getAllSuperclasses(Class cls)
/*  171:     */   {
/*  172: 289 */     if (cls == null) {
/*  173: 290 */       return null;
/*  174:     */     }
/*  175: 292 */     List classes = new ArrayList();
/*  176: 293 */     Class superclass = cls.getSuperclass();
/*  177: 294 */     while (superclass != null)
/*  178:     */     {
/*  179: 295 */       classes.add(superclass);
/*  180: 296 */       superclass = superclass.getSuperclass();
/*  181:     */     }
/*  182: 298 */     return classes;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public static List getAllInterfaces(Class cls)
/*  186:     */   {
/*  187: 315 */     if (cls == null) {
/*  188: 316 */       return null;
/*  189:     */     }
/*  190: 319 */     List interfacesFound = new ArrayList();
/*  191: 320 */     getAllInterfaces(cls, interfacesFound);
/*  192:     */     
/*  193: 322 */     return interfacesFound;
/*  194:     */   }
/*  195:     */   
/*  196:     */   private static void getAllInterfaces(Class cls, List interfacesFound)
/*  197:     */   {
/*  198: 332 */     while (cls != null)
/*  199:     */     {
/*  200: 333 */       Class[] interfaces = cls.getInterfaces();
/*  201: 335 */       for (int i = 0; i < interfaces.length; i++) {
/*  202: 336 */         if (!interfacesFound.contains(interfaces[i]))
/*  203:     */         {
/*  204: 337 */           interfacesFound.add(interfaces[i]);
/*  205: 338 */           getAllInterfaces(interfaces[i], interfacesFound);
/*  206:     */         }
/*  207:     */       }
/*  208: 342 */       cls = cls.getSuperclass();
/*  209:     */     }
/*  210:     */   }
/*  211:     */   
/*  212:     */   public static List convertClassNamesToClasses(List classNames)
/*  213:     */   {
/*  214: 361 */     if (classNames == null) {
/*  215: 362 */       return null;
/*  216:     */     }
/*  217: 364 */     List classes = new ArrayList(classNames.size());
/*  218: 365 */     for (Iterator it = classNames.iterator(); it.hasNext();)
/*  219:     */     {
/*  220: 366 */       String className = (String)it.next();
/*  221:     */       try
/*  222:     */       {
/*  223: 368 */         classes.add(Class.forName(className));
/*  224:     */       }
/*  225:     */       catch (Exception ex)
/*  226:     */       {
/*  227: 370 */         classes.add(null);
/*  228:     */       }
/*  229:     */     }
/*  230: 373 */     return classes;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public static List convertClassesToClassNames(List classes)
/*  234:     */   {
/*  235: 389 */     if (classes == null) {
/*  236: 390 */       return null;
/*  237:     */     }
/*  238: 392 */     List classNames = new ArrayList(classes.size());
/*  239: 393 */     for (Iterator it = classes.iterator(); it.hasNext();)
/*  240:     */     {
/*  241: 394 */       Class cls = (Class)it.next();
/*  242: 395 */       if (cls == null) {
/*  243: 396 */         classNames.add(null);
/*  244:     */       } else {
/*  245: 398 */         classNames.add(cls.getName());
/*  246:     */       }
/*  247:     */     }
/*  248: 401 */     return classNames;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public static boolean isAssignable(Class[] classArray, Class[] toClassArray)
/*  252:     */   {
/*  253: 438 */     return isAssignable(classArray, toClassArray, false);
/*  254:     */   }
/*  255:     */   
/*  256:     */   public static boolean isAssignable(Class[] classArray, Class[] toClassArray, boolean autoboxing)
/*  257:     */   {
/*  258: 475 */     if (!ArrayUtils.isSameLength(classArray, toClassArray)) {
/*  259: 476 */       return false;
/*  260:     */     }
/*  261: 478 */     if (classArray == null) {
/*  262: 479 */       classArray = ArrayUtils.EMPTY_CLASS_ARRAY;
/*  263:     */     }
/*  264: 481 */     if (toClassArray == null) {
/*  265: 482 */       toClassArray = ArrayUtils.EMPTY_CLASS_ARRAY;
/*  266:     */     }
/*  267: 484 */     for (int i = 0; i < classArray.length; i++) {
/*  268: 485 */       if (!isAssignable(classArray[i], toClassArray[i], autoboxing)) {
/*  269: 486 */         return false;
/*  270:     */       }
/*  271:     */     }
/*  272: 489 */     return true;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public static boolean isAssignable(Class cls, Class toClass)
/*  276:     */   {
/*  277: 519 */     return isAssignable(cls, toClass, false);
/*  278:     */   }
/*  279:     */   
/*  280:     */   public static boolean isAssignable(Class cls, Class toClass, boolean autoboxing)
/*  281:     */   {
/*  282: 551 */     if (toClass == null) {
/*  283: 552 */       return false;
/*  284:     */     }
/*  285: 555 */     if (cls == null) {
/*  286: 556 */       return !toClass.isPrimitive();
/*  287:     */     }
/*  288: 559 */     if (autoboxing)
/*  289:     */     {
/*  290: 560 */       if ((cls.isPrimitive()) && (!toClass.isPrimitive()))
/*  291:     */       {
/*  292: 561 */         cls = primitiveToWrapper(cls);
/*  293: 562 */         if (cls == null) {
/*  294: 563 */           return false;
/*  295:     */         }
/*  296:     */       }
/*  297: 566 */       if ((toClass.isPrimitive()) && (!cls.isPrimitive()))
/*  298:     */       {
/*  299: 567 */         cls = wrapperToPrimitive(cls);
/*  300: 568 */         if (cls == null) {
/*  301: 569 */           return false;
/*  302:     */         }
/*  303:     */       }
/*  304:     */     }
/*  305: 573 */     if (cls.equals(toClass)) {
/*  306: 574 */       return true;
/*  307:     */     }
/*  308: 576 */     if (cls.isPrimitive())
/*  309:     */     {
/*  310: 577 */       if (!toClass.isPrimitive()) {
/*  311: 578 */         return false;
/*  312:     */       }
/*  313: 580 */       if (Integer.TYPE.equals(cls)) {
/*  314: 581 */         return (Long.TYPE.equals(toClass)) || (Float.TYPE.equals(toClass)) || (Double.TYPE.equals(toClass));
/*  315:     */       }
/*  316: 585 */       if (Long.TYPE.equals(cls)) {
/*  317: 586 */         return (Float.TYPE.equals(toClass)) || (Double.TYPE.equals(toClass));
/*  318:     */       }
/*  319: 589 */       if (Boolean.TYPE.equals(cls)) {
/*  320: 590 */         return false;
/*  321:     */       }
/*  322: 592 */       if (Double.TYPE.equals(cls)) {
/*  323: 593 */         return false;
/*  324:     */       }
/*  325: 595 */       if (Float.TYPE.equals(cls)) {
/*  326: 596 */         return Double.TYPE.equals(toClass);
/*  327:     */       }
/*  328: 598 */       if (Character.TYPE.equals(cls)) {
/*  329: 599 */         return (Integer.TYPE.equals(toClass)) || (Long.TYPE.equals(toClass)) || (Float.TYPE.equals(toClass)) || (Double.TYPE.equals(toClass));
/*  330:     */       }
/*  331: 604 */       if (Short.TYPE.equals(cls)) {
/*  332: 605 */         return (Integer.TYPE.equals(toClass)) || (Long.TYPE.equals(toClass)) || (Float.TYPE.equals(toClass)) || (Double.TYPE.equals(toClass));
/*  333:     */       }
/*  334: 610 */       if (Byte.TYPE.equals(cls)) {
/*  335: 611 */         return (Short.TYPE.equals(toClass)) || (Integer.TYPE.equals(toClass)) || (Long.TYPE.equals(toClass)) || (Float.TYPE.equals(toClass)) || (Double.TYPE.equals(toClass));
/*  336:     */       }
/*  337: 618 */       return false;
/*  338:     */     }
/*  339: 620 */     return toClass.isAssignableFrom(cls);
/*  340:     */   }
/*  341:     */   
/*  342:     */   public static Class primitiveToWrapper(Class cls)
/*  343:     */   {
/*  344: 636 */     Class convertedClass = cls;
/*  345: 637 */     if ((cls != null) && (cls.isPrimitive())) {
/*  346: 638 */       convertedClass = (Class)primitiveWrapperMap.get(cls);
/*  347:     */     }
/*  348: 640 */     return convertedClass;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public static Class[] primitivesToWrappers(Class[] classes)
/*  352:     */   {
/*  353: 654 */     if (classes == null) {
/*  354: 655 */       return null;
/*  355:     */     }
/*  356: 658 */     if (classes.length == 0) {
/*  357: 659 */       return classes;
/*  358:     */     }
/*  359: 662 */     Class[] convertedClasses = new Class[classes.length];
/*  360: 663 */     for (int i = 0; i < classes.length; i++) {
/*  361: 664 */       convertedClasses[i] = primitiveToWrapper(classes[i]);
/*  362:     */     }
/*  363: 666 */     return convertedClasses;
/*  364:     */   }
/*  365:     */   
/*  366:     */   public static Class wrapperToPrimitive(Class cls)
/*  367:     */   {
/*  368: 686 */     return (Class)wrapperPrimitiveMap.get(cls);
/*  369:     */   }
/*  370:     */   
/*  371:     */   public static Class[] wrappersToPrimitives(Class[] classes)
/*  372:     */   {
/*  373: 704 */     if (classes == null) {
/*  374: 705 */       return null;
/*  375:     */     }
/*  376: 708 */     if (classes.length == 0) {
/*  377: 709 */       return classes;
/*  378:     */     }
/*  379: 712 */     Class[] convertedClasses = new Class[classes.length];
/*  380: 713 */     for (int i = 0; i < classes.length; i++) {
/*  381: 714 */       convertedClasses[i] = wrapperToPrimitive(classes[i]);
/*  382:     */     }
/*  383: 716 */     return convertedClasses;
/*  384:     */   }
/*  385:     */   
/*  386:     */   public static boolean isInnerClass(Class cls)
/*  387:     */   {
/*  388: 729 */     if (cls == null) {
/*  389: 730 */       return false;
/*  390:     */     }
/*  391: 732 */     return cls.getName().indexOf('$') >= 0;
/*  392:     */   }
/*  393:     */   
/*  394:     */   public static Class getClass(ClassLoader classLoader, String className, boolean initialize)
/*  395:     */     throws ClassNotFoundException
/*  396:     */   {
/*  397:     */     try
/*  398:     */     {
/*  399:     */       Class clazz;
/*  400: 753 */       if (abbreviationMap.containsKey(className))
/*  401:     */       {
/*  402: 754 */         String clsName = "[" + abbreviationMap.get(className);
/*  403: 755 */         clazz = Class.forName(clsName, initialize, classLoader).getComponentType();
/*  404:     */       }
/*  405: 757 */       return Class.forName(toCanonicalName(className), initialize, classLoader);
/*  406:     */     }
/*  407:     */     catch (ClassNotFoundException ex)
/*  408:     */     {
/*  409: 762 */       int lastDotIndex = className.lastIndexOf('.');
/*  410: 764 */       if (lastDotIndex != -1) {
/*  411:     */         try
/*  412:     */         {
/*  413: 766 */           return getClass(classLoader, className.substring(0, lastDotIndex) + '$' + className.substring(lastDotIndex + 1), initialize);
/*  414:     */         }
/*  415:     */         catch (ClassNotFoundException ex2) {}
/*  416:     */       }
/*  417: 773 */       throw ex;
/*  418:     */     }
/*  419:     */   }
/*  420:     */   
/*  421:     */   public static Class getClass(ClassLoader classLoader, String className)
/*  422:     */     throws ClassNotFoundException
/*  423:     */   {
/*  424: 790 */     return getClass(classLoader, className, true);
/*  425:     */   }
/*  426:     */   
/*  427:     */   public static Class getClass(String className)
/*  428:     */     throws ClassNotFoundException
/*  429:     */   {
/*  430: 805 */     return getClass(className, true);
/*  431:     */   }
/*  432:     */   
/*  433:     */   public static Class getClass(String className, boolean initialize)
/*  434:     */     throws ClassNotFoundException
/*  435:     */   {
/*  436: 820 */     ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
/*  437: 821 */     ClassLoader loader = contextCL == null ? ClassUtils.class.getClassLoader() : contextCL;
/*  438: 822 */     return getClass(loader, className, initialize);
/*  439:     */   }
/*  440:     */   
/*  441:     */   public static Method getPublicMethod(Class cls, String methodName, Class[] parameterTypes)
/*  442:     */     throws SecurityException, NoSuchMethodException
/*  443:     */   {
/*  444: 851 */     Method declaredMethod = cls.getMethod(methodName, parameterTypes);
/*  445: 852 */     if (Modifier.isPublic(declaredMethod.getDeclaringClass().getModifiers())) {
/*  446: 853 */       return declaredMethod;
/*  447:     */     }
/*  448: 856 */     List candidateClasses = new ArrayList();
/*  449: 857 */     candidateClasses.addAll(getAllInterfaces(cls));
/*  450: 858 */     candidateClasses.addAll(getAllSuperclasses(cls));
/*  451: 860 */     for (Iterator it = candidateClasses.iterator(); it.hasNext();)
/*  452:     */     {
/*  453: 861 */       Class candidateClass = (Class)it.next();
/*  454: 862 */       if (Modifier.isPublic(candidateClass.getModifiers()))
/*  455:     */       {
/*  456:     */         Method candidateMethod;
/*  457:     */         try
/*  458:     */         {
/*  459: 867 */           candidateMethod = candidateClass.getMethod(methodName, parameterTypes);
/*  460:     */         }
/*  461:     */         catch (NoSuchMethodException ex) {}
/*  462: 869 */         continue;
/*  463: 871 */         if (Modifier.isPublic(candidateMethod.getDeclaringClass().getModifiers())) {
/*  464: 872 */           return candidateMethod;
/*  465:     */         }
/*  466:     */       }
/*  467:     */     }
/*  468: 876 */     throw new NoSuchMethodException("Can't find a public method for " + methodName + " " + ArrayUtils.toString(parameterTypes));
/*  469:     */   }
/*  470:     */   
/*  471:     */   private static String toCanonicalName(String className)
/*  472:     */   {
/*  473: 888 */     className = StringUtils.deleteWhitespace(className);
/*  474: 889 */     if (className == null) {
/*  475: 890 */       throw new NullArgumentException("className");
/*  476:     */     }
/*  477: 891 */     if (className.endsWith("[]"))
/*  478:     */     {
/*  479: 892 */       StrBuilder classNameBuffer = new StrBuilder();
/*  480: 893 */       while (className.endsWith("[]"))
/*  481:     */       {
/*  482: 894 */         className = className.substring(0, className.length() - 2);
/*  483: 895 */         classNameBuffer.append("[");
/*  484:     */       }
/*  485: 897 */       String abbreviation = (String)abbreviationMap.get(className);
/*  486: 898 */       if (abbreviation != null) {
/*  487: 899 */         classNameBuffer.append(abbreviation);
/*  488:     */       } else {
/*  489: 901 */         classNameBuffer.append("L").append(className).append(";");
/*  490:     */       }
/*  491: 903 */       className = classNameBuffer.toString();
/*  492:     */     }
/*  493: 905 */     return className;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public static Class[] toClass(Object[] array)
/*  497:     */   {
/*  498: 919 */     if (array == null) {
/*  499: 920 */       return null;
/*  500:     */     }
/*  501: 921 */     if (array.length == 0) {
/*  502: 922 */       return ArrayUtils.EMPTY_CLASS_ARRAY;
/*  503:     */     }
/*  504: 924 */     Class[] classes = new Class[array.length];
/*  505: 925 */     for (int i = 0; i < array.length; i++) {
/*  506: 926 */       classes[i] = (array[i] == null ? null : array[i].getClass());
/*  507:     */     }
/*  508: 928 */     return classes;
/*  509:     */   }
/*  510:     */   
/*  511:     */   public static String getShortCanonicalName(Object object, String valueIfNull)
/*  512:     */   {
/*  513: 942 */     if (object == null) {
/*  514: 943 */       return valueIfNull;
/*  515:     */     }
/*  516: 945 */     return getShortCanonicalName(object.getClass().getName());
/*  517:     */   }
/*  518:     */   
/*  519:     */   public static String getShortCanonicalName(Class cls)
/*  520:     */   {
/*  521: 956 */     if (cls == null) {
/*  522: 957 */       return "";
/*  523:     */     }
/*  524: 959 */     return getShortCanonicalName(cls.getName());
/*  525:     */   }
/*  526:     */   
/*  527:     */   public static String getShortCanonicalName(String canonicalName)
/*  528:     */   {
/*  529: 972 */     return getShortClassName(getCanonicalName(canonicalName));
/*  530:     */   }
/*  531:     */   
/*  532:     */   public static String getPackageCanonicalName(Object object, String valueIfNull)
/*  533:     */   {
/*  534: 986 */     if (object == null) {
/*  535: 987 */       return valueIfNull;
/*  536:     */     }
/*  537: 989 */     return getPackageCanonicalName(object.getClass().getName());
/*  538:     */   }
/*  539:     */   
/*  540:     */   public static String getPackageCanonicalName(Class cls)
/*  541:     */   {
/*  542:1000 */     if (cls == null) {
/*  543:1001 */       return "";
/*  544:     */     }
/*  545:1003 */     return getPackageCanonicalName(cls.getName());
/*  546:     */   }
/*  547:     */   
/*  548:     */   public static String getPackageCanonicalName(String canonicalName)
/*  549:     */   {
/*  550:1017 */     return getPackageName(getCanonicalName(canonicalName));
/*  551:     */   }
/*  552:     */   
/*  553:     */   private static String getCanonicalName(String className)
/*  554:     */   {
/*  555:1037 */     className = StringUtils.deleteWhitespace(className);
/*  556:1038 */     if (className == null) {
/*  557:1039 */       return null;
/*  558:     */     }
/*  559:1041 */     int dim = 0;
/*  560:1042 */     while (className.startsWith("["))
/*  561:     */     {
/*  562:1043 */       dim++;
/*  563:1044 */       className = className.substring(1);
/*  564:     */     }
/*  565:1046 */     if (dim < 1) {
/*  566:1047 */       return className;
/*  567:     */     }
/*  568:1049 */     if (className.startsWith("L")) {
/*  569:1050 */       className = className.substring(1, className.endsWith(";") ? className.length() - 1 : className.length());
/*  570:1056 */     } else if (className.length() > 0) {
/*  571:1057 */       className = (String)reverseAbbreviationMap.get(className.substring(0, 1));
/*  572:     */     }
/*  573:1061 */     StrBuilder canonicalClassNameBuffer = new StrBuilder(className);
/*  574:1062 */     for (int i = 0; i < dim; i++) {
/*  575:1063 */       canonicalClassNameBuffer.append("[]");
/*  576:     */     }
/*  577:1065 */     return canonicalClassNameBuffer.toString();
/*  578:     */   }
/*  579:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.ClassUtils
 * JD-Core Version:    0.7.0.1
 */