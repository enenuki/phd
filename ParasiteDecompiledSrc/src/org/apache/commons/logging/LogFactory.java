/*    1:     */ package org.apache.commons.logging;
/*    2:     */ 
/*    3:     */ import java.io.BufferedReader;
/*    4:     */ import java.io.FileOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.io.InputStreamReader;
/*    8:     */ import java.io.PrintStream;
/*    9:     */ import java.io.UnsupportedEncodingException;
/*   10:     */ import java.lang.reflect.InvocationTargetException;
/*   11:     */ import java.lang.reflect.Method;
/*   12:     */ import java.net.URL;
/*   13:     */ import java.security.AccessController;
/*   14:     */ import java.security.PrivilegedAction;
/*   15:     */ import java.util.Enumeration;
/*   16:     */ import java.util.Hashtable;
/*   17:     */ import java.util.Properties;
/*   18:     */ 
/*   19:     */ public abstract class LogFactory
/*   20:     */ {
/*   21:     */   public static final String PRIORITY_KEY = "priority";
/*   22:     */   public static final String TCCL_KEY = "use_tccl";
/*   23:     */   public static final String FACTORY_PROPERTY = "org.apache.commons.logging.LogFactory";
/*   24:     */   public static final String FACTORY_DEFAULT = "org.apache.commons.logging.impl.LogFactoryImpl";
/*   25:     */   public static final String FACTORY_PROPERTIES = "commons-logging.properties";
/*   26:     */   protected static final String SERVICE_ID = "META-INF/services/org.apache.commons.logging.LogFactory";
/*   27:     */   public static final String DIAGNOSTICS_DEST_PROPERTY = "org.apache.commons.logging.diagnostics.dest";
/*   28: 148 */   private static PrintStream diagnosticsStream = null;
/*   29:     */   private static String diagnosticPrefix;
/*   30:     */   public static final String HASHTABLE_IMPLEMENTATION_PROPERTY = "org.apache.commons.logging.LogFactory.HashtableImpl";
/*   31:     */   private static final String WEAK_HASHTABLE_CLASSNAME = "org.apache.commons.logging.impl.WeakHashtable";
/*   32:     */   private static ClassLoader thisClassLoader;
/*   33: 309 */   protected static Hashtable factories = null;
/*   34: 325 */   protected static LogFactory nullClassLoaderFactory = null;
/*   35:     */   
/*   36:     */   public abstract Object getAttribute(String paramString);
/*   37:     */   
/*   38:     */   public abstract String[] getAttributeNames();
/*   39:     */   
/*   40:     */   public abstract Log getInstance(Class paramClass)
/*   41:     */     throws LogConfigurationException;
/*   42:     */   
/*   43:     */   public abstract Log getInstance(String paramString)
/*   44:     */     throws LogConfigurationException;
/*   45:     */   
/*   46:     */   public abstract void release();
/*   47:     */   
/*   48:     */   public abstract void removeAttribute(String paramString);
/*   49:     */   
/*   50:     */   public abstract void setAttribute(String paramString, Object paramObject);
/*   51:     */   
/*   52:     */   private static final Hashtable createFactoryStore()
/*   53:     */   {
/*   54: 343 */     Hashtable result = null;
/*   55:     */     String storeImplementationClass;
/*   56:     */     try
/*   57:     */     {
/*   58: 346 */       storeImplementationClass = getSystemProperty("org.apache.commons.logging.LogFactory.HashtableImpl", null);
/*   59:     */     }
/*   60:     */     catch (SecurityException ex)
/*   61:     */     {
/*   62:     */       String storeImplementationClass;
/*   63: 350 */       storeImplementationClass = null;
/*   64:     */     }
/*   65: 353 */     if (storeImplementationClass == null) {
/*   66: 354 */       storeImplementationClass = "org.apache.commons.logging.impl.WeakHashtable";
/*   67:     */     }
/*   68:     */     try
/*   69:     */     {
/*   70: 357 */       Class implementationClass = Class.forName(storeImplementationClass);
/*   71: 358 */       result = (Hashtable)implementationClass.newInstance();
/*   72:     */     }
/*   73:     */     catch (Throwable t)
/*   74:     */     {
/*   75: 362 */       if (!"org.apache.commons.logging.impl.WeakHashtable".equals(storeImplementationClass)) {
/*   76: 364 */         if (isDiagnosticsEnabled()) {
/*   77: 366 */           logDiagnostic("[ERROR] LogFactory: Load of custom hashtable failed");
/*   78:     */         } else {
/*   79: 370 */           System.err.println("[ERROR] LogFactory: Load of custom hashtable failed");
/*   80:     */         }
/*   81:     */       }
/*   82:     */     }
/*   83: 374 */     if (result == null) {
/*   84: 375 */       result = new Hashtable();
/*   85:     */     }
/*   86: 377 */     return result;
/*   87:     */   }
/*   88:     */   
/*   89:     */   private static String trim(String src)
/*   90:     */   {
/*   91: 385 */     if (src == null) {
/*   92: 386 */       return null;
/*   93:     */     }
/*   94: 388 */     return src.trim();
/*   95:     */   }
/*   96:     */   
/*   97:     */   public static LogFactory getFactory()
/*   98:     */     throws LogConfigurationException
/*   99:     */   {
/*  100: 423 */     ClassLoader contextClassLoader = getContextClassLoaderInternal();
/*  101: 425 */     if (contextClassLoader == null) {
/*  102: 429 */       if (isDiagnosticsEnabled()) {
/*  103: 430 */         logDiagnostic("Context classloader is null.");
/*  104:     */       }
/*  105:     */     }
/*  106: 435 */     LogFactory factory = getCachedFactory(contextClassLoader);
/*  107: 436 */     if (factory != null) {
/*  108: 437 */       return factory;
/*  109:     */     }
/*  110: 440 */     if (isDiagnosticsEnabled())
/*  111:     */     {
/*  112: 441 */       logDiagnostic("[LOOKUP] LogFactory implementation requested for the first time for context classloader " + objectId(contextClassLoader));
/*  113:     */       
/*  114:     */ 
/*  115: 444 */       logHierarchy("[LOOKUP] ", contextClassLoader);
/*  116:     */     }
/*  117: 457 */     Properties props = getConfigurationFile(contextClassLoader, "commons-logging.properties");
/*  118:     */     
/*  119:     */ 
/*  120:     */ 
/*  121: 461 */     ClassLoader baseClassLoader = contextClassLoader;
/*  122: 462 */     if (props != null)
/*  123:     */     {
/*  124: 463 */       String useTCCLStr = props.getProperty("use_tccl");
/*  125: 464 */       if (useTCCLStr != null) {
/*  126: 467 */         if (!Boolean.valueOf(useTCCLStr).booleanValue()) {
/*  127: 475 */           baseClassLoader = thisClassLoader;
/*  128:     */         }
/*  129:     */       }
/*  130:     */     }
/*  131: 482 */     if (isDiagnosticsEnabled()) {
/*  132: 483 */       logDiagnostic("[LOOKUP] Looking for system property [org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
/*  133:     */     }
/*  134:     */     try
/*  135:     */     {
/*  136: 489 */       String factoryClass = getSystemProperty("org.apache.commons.logging.LogFactory", null);
/*  137: 490 */       if (factoryClass != null)
/*  138:     */       {
/*  139: 491 */         if (isDiagnosticsEnabled()) {
/*  140: 492 */           logDiagnostic("[LOOKUP] Creating an instance of LogFactory class '" + factoryClass + "' as specified by system property " + "org.apache.commons.logging.LogFactory");
/*  141:     */         }
/*  142: 497 */         factory = newFactory(factoryClass, baseClassLoader, contextClassLoader);
/*  143:     */       }
/*  144: 499 */       else if (isDiagnosticsEnabled())
/*  145:     */       {
/*  146: 500 */         logDiagnostic("[LOOKUP] No system property [org.apache.commons.logging.LogFactory] defined.");
/*  147:     */       }
/*  148:     */     }
/*  149:     */     catch (SecurityException e)
/*  150:     */     {
/*  151: 506 */       if (isDiagnosticsEnabled()) {
/*  152: 507 */         logDiagnostic("[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [" + trim(e.getMessage()) + "]. Trying alternative implementations...");
/*  153:     */       }
/*  154:     */     }
/*  155:     */     catch (RuntimeException e)
/*  156:     */     {
/*  157: 520 */       if (isDiagnosticsEnabled()) {
/*  158: 521 */         logDiagnostic("[LOOKUP] An exception occurred while trying to create an instance of the custom factory class: [" + trim(e.getMessage()) + "] as specified by a system property.");
/*  159:     */       }
/*  160: 527 */       throw e;
/*  161:     */     }
/*  162: 537 */     if (factory == null)
/*  163:     */     {
/*  164: 538 */       if (isDiagnosticsEnabled()) {
/*  165: 539 */         logDiagnostic("[LOOKUP] Looking for a resource file of name [META-INF/services/org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
/*  166:     */       }
/*  167:     */       try
/*  168:     */       {
/*  169: 544 */         InputStream is = getResourceAsStream(contextClassLoader, "META-INF/services/org.apache.commons.logging.LogFactory");
/*  170: 547 */         if (is != null)
/*  171:     */         {
/*  172:     */           BufferedReader rd;
/*  173:     */           try
/*  174:     */           {
/*  175: 552 */             rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*  176:     */           }
/*  177:     */           catch (UnsupportedEncodingException e)
/*  178:     */           {
/*  179:     */             BufferedReader rd;
/*  180: 554 */             rd = new BufferedReader(new InputStreamReader(is));
/*  181:     */           }
/*  182: 557 */           String factoryClassName = rd.readLine();
/*  183: 558 */           rd.close();
/*  184: 560 */           if ((factoryClassName != null) && (!"".equals(factoryClassName)))
/*  185:     */           {
/*  186: 562 */             if (isDiagnosticsEnabled()) {
/*  187: 563 */               logDiagnostic("[LOOKUP]  Creating an instance of LogFactory class " + factoryClassName + " as specified by file '" + "META-INF/services/org.apache.commons.logging.LogFactory" + "' which was present in the path of the context" + " classloader.");
/*  188:     */             }
/*  189: 569 */             factory = newFactory(factoryClassName, baseClassLoader, contextClassLoader);
/*  190:     */           }
/*  191:     */         }
/*  192: 573 */         else if (isDiagnosticsEnabled())
/*  193:     */         {
/*  194: 574 */           logDiagnostic("[LOOKUP] No resource file with name 'META-INF/services/org.apache.commons.logging.LogFactory' found.");
/*  195:     */         }
/*  196:     */       }
/*  197:     */       catch (Exception ex)
/*  198:     */       {
/*  199: 583 */         if (isDiagnosticsEnabled()) {
/*  200: 584 */           logDiagnostic("[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [" + trim(ex.getMessage()) + "]. Trying alternative implementations...");
/*  201:     */         }
/*  202:     */       }
/*  203:     */     }
/*  204: 597 */     if (factory == null) {
/*  205: 598 */       if (props != null)
/*  206:     */       {
/*  207: 599 */         if (isDiagnosticsEnabled()) {
/*  208: 600 */           logDiagnostic("[LOOKUP] Looking in properties file for entry with key 'org.apache.commons.logging.LogFactory' to define the LogFactory subclass to use...");
/*  209:     */         }
/*  210: 605 */         String factoryClass = props.getProperty("org.apache.commons.logging.LogFactory");
/*  211: 606 */         if (factoryClass != null)
/*  212:     */         {
/*  213: 607 */           if (isDiagnosticsEnabled()) {
/*  214: 608 */             logDiagnostic("[LOOKUP] Properties file specifies LogFactory subclass '" + factoryClass + "'");
/*  215:     */           }
/*  216: 612 */           factory = newFactory(factoryClass, baseClassLoader, contextClassLoader);
/*  217:     */         }
/*  218: 616 */         else if (isDiagnosticsEnabled())
/*  219:     */         {
/*  220: 617 */           logDiagnostic("[LOOKUP] Properties file has no entry specifying LogFactory subclass.");
/*  221:     */         }
/*  222:     */       }
/*  223: 622 */       else if (isDiagnosticsEnabled())
/*  224:     */       {
/*  225: 623 */         logDiagnostic("[LOOKUP] No properties file available to determine LogFactory subclass from..");
/*  226:     */       }
/*  227:     */     }
/*  228: 633 */     if (factory == null)
/*  229:     */     {
/*  230: 634 */       if (isDiagnosticsEnabled()) {
/*  231: 635 */         logDiagnostic("[LOOKUP] Loading the default LogFactory implementation 'org.apache.commons.logging.impl.LogFactoryImpl' via the same classloader that loaded this LogFactory class (ie not looking in the context classloader).");
/*  232:     */       }
/*  233: 650 */       factory = newFactory("org.apache.commons.logging.impl.LogFactoryImpl", thisClassLoader, contextClassLoader);
/*  234:     */     }
/*  235: 653 */     if (factory != null)
/*  236:     */     {
/*  237: 657 */       cacheFactory(contextClassLoader, factory);
/*  238: 659 */       if (props != null)
/*  239:     */       {
/*  240: 660 */         Enumeration names = props.propertyNames();
/*  241: 661 */         while (names.hasMoreElements())
/*  242:     */         {
/*  243: 662 */           String name = (String)names.nextElement();
/*  244: 663 */           String value = props.getProperty(name);
/*  245: 664 */           factory.setAttribute(name, value);
/*  246:     */         }
/*  247:     */       }
/*  248:     */     }
/*  249: 669 */     return factory;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public static Log getLog(Class clazz)
/*  253:     */     throws LogConfigurationException
/*  254:     */   {
/*  255: 685 */     return getFactory().getInstance(clazz);
/*  256:     */   }
/*  257:     */   
/*  258:     */   public static Log getLog(String name)
/*  259:     */     throws LogConfigurationException
/*  260:     */   {
/*  261: 704 */     return getFactory().getInstance(name);
/*  262:     */   }
/*  263:     */   
/*  264:     */   public static void release(ClassLoader classLoader)
/*  265:     */   {
/*  266: 719 */     if (isDiagnosticsEnabled()) {
/*  267: 720 */       logDiagnostic("Releasing factory for classloader " + objectId(classLoader));
/*  268:     */     }
/*  269: 722 */     synchronized (factories)
/*  270:     */     {
/*  271: 723 */       if (classLoader == null)
/*  272:     */       {
/*  273: 724 */         if (nullClassLoaderFactory != null)
/*  274:     */         {
/*  275: 725 */           nullClassLoaderFactory.release();
/*  276: 726 */           nullClassLoaderFactory = null;
/*  277:     */         }
/*  278:     */       }
/*  279:     */       else
/*  280:     */       {
/*  281: 729 */         LogFactory factory = (LogFactory)factories.get(classLoader);
/*  282: 730 */         if (factory != null)
/*  283:     */         {
/*  284: 731 */           factory.release();
/*  285: 732 */           factories.remove(classLoader);
/*  286:     */         }
/*  287:     */       }
/*  288:     */     }
/*  289:     */   }
/*  290:     */   
/*  291:     */   public static void releaseAll()
/*  292:     */   {
/*  293: 750 */     if (isDiagnosticsEnabled()) {
/*  294: 751 */       logDiagnostic("Releasing factory for all classloaders.");
/*  295:     */     }
/*  296: 753 */     synchronized (factories)
/*  297:     */     {
/*  298: 754 */       Enumeration elements = factories.elements();
/*  299: 755 */       while (elements.hasMoreElements())
/*  300:     */       {
/*  301: 756 */         LogFactory element = (LogFactory)elements.nextElement();
/*  302: 757 */         element.release();
/*  303:     */       }
/*  304: 759 */       factories.clear();
/*  305: 761 */       if (nullClassLoaderFactory != null)
/*  306:     */       {
/*  307: 762 */         nullClassLoaderFactory.release();
/*  308: 763 */         nullClassLoaderFactory = null;
/*  309:     */       }
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   protected static ClassLoader getClassLoader(Class clazz)
/*  314:     */   {
/*  315:     */     try
/*  316:     */     {
/*  317: 801 */       return clazz.getClassLoader();
/*  318:     */     }
/*  319:     */     catch (SecurityException ex)
/*  320:     */     {
/*  321: 803 */       if (isDiagnosticsEnabled()) {
/*  322: 804 */         logDiagnostic("Unable to get classloader for class '" + clazz + "' due to security restrictions - " + ex.getMessage());
/*  323:     */       }
/*  324: 808 */       throw ex;
/*  325:     */     }
/*  326:     */   }
/*  327:     */   
/*  328:     */   protected static ClassLoader getContextClassLoader()
/*  329:     */     throws LogConfigurationException
/*  330:     */   {
/*  331: 836 */     return directGetContextClassLoader();
/*  332:     */   }
/*  333:     */   
/*  334:     */   private static ClassLoader getContextClassLoaderInternal()
/*  335:     */     throws LogConfigurationException
/*  336:     */   {
/*  337: 859 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  338:     */     {
/*  339:     */       public Object run()
/*  340:     */       {
/*  341: 862 */         return LogFactory.directGetContextClassLoader();
/*  342:     */       }
/*  343:     */     });
/*  344:     */   }
/*  345:     */   
/*  346:     */   protected static ClassLoader directGetContextClassLoader()
/*  347:     */     throws LogConfigurationException
/*  348:     */   {
/*  349: 892 */     ClassLoader classLoader = null;
/*  350:     */     try
/*  351:     */     {
/*  352: 896 */       Method method = Thread.class.getMethod("getContextClassLoader", (Class[])null);
/*  353:     */       try
/*  354:     */       {
/*  355: 901 */         classLoader = (ClassLoader)method.invoke(Thread.currentThread(), (Object[])null);
/*  356:     */       }
/*  357:     */       catch (IllegalAccessException e)
/*  358:     */       {
/*  359: 904 */         throw new LogConfigurationException("Unexpected IllegalAccessException", e);
/*  360:     */       }
/*  361:     */       catch (InvocationTargetException e)
/*  362:     */       {
/*  363: 923 */         if (!(e.getTargetException() instanceof SecurityException)) {
/*  364: 928 */           throw new LogConfigurationException("Unexpected InvocationTargetException", e.getTargetException());
/*  365:     */         }
/*  366:     */       }
/*  367:     */     }
/*  368:     */     catch (NoSuchMethodException e)
/*  369:     */     {
/*  370: 934 */       classLoader = getClassLoader(LogFactory.class);
/*  371:     */     }
/*  372: 951 */     return classLoader;
/*  373:     */   }
/*  374:     */   
/*  375:     */   private static LogFactory getCachedFactory(ClassLoader contextClassLoader)
/*  376:     */   {
/*  377: 970 */     LogFactory factory = null;
/*  378: 972 */     if (contextClassLoader == null) {
/*  379: 977 */       factory = nullClassLoaderFactory;
/*  380:     */     } else {
/*  381: 979 */       factory = (LogFactory)factories.get(contextClassLoader);
/*  382:     */     }
/*  383: 982 */     return factory;
/*  384:     */   }
/*  385:     */   
/*  386:     */   private static void cacheFactory(ClassLoader classLoader, LogFactory factory)
/*  387:     */   {
/*  388:1000 */     if (factory != null) {
/*  389:1001 */       if (classLoader == null) {
/*  390:1002 */         nullClassLoaderFactory = factory;
/*  391:     */       } else {
/*  392:1004 */         factories.put(classLoader, factory);
/*  393:     */       }
/*  394:     */     }
/*  395:     */   }
/*  396:     */   
/*  397:     */   protected static LogFactory newFactory(String factoryClass, ClassLoader classLoader, ClassLoader contextClassLoader)
/*  398:     */     throws LogConfigurationException
/*  399:     */   {
/*  400:1062 */     Object result = AccessController.doPrivileged(new PrivilegedAction()
/*  401:     */     {
/*  402:     */       private final String val$factoryClass;
/*  403:     */       private final ClassLoader val$classLoader;
/*  404:     */       
/*  405:     */       public Object run()
/*  406:     */       {
/*  407:1065 */         return LogFactory.createFactory(this.val$factoryClass, this.val$classLoader);
/*  408:     */       }
/*  409:     */     });
/*  410:1069 */     if ((result instanceof LogConfigurationException))
/*  411:     */     {
/*  412:1070 */       LogConfigurationException ex = (LogConfigurationException)result;
/*  413:1071 */       if (isDiagnosticsEnabled()) {
/*  414:1072 */         logDiagnostic("An error occurred while loading the factory class:" + ex.getMessage());
/*  415:     */       }
/*  416:1076 */       throw ex;
/*  417:     */     }
/*  418:1078 */     if (isDiagnosticsEnabled()) {
/*  419:1079 */       logDiagnostic("Created object " + objectId(result) + " to manage classloader " + objectId(contextClassLoader));
/*  420:     */     }
/*  421:1083 */     return (LogFactory)result;
/*  422:     */   }
/*  423:     */   
/*  424:     */   protected static LogFactory newFactory(String factoryClass, ClassLoader classLoader)
/*  425:     */   {
/*  426:1103 */     return newFactory(factoryClass, classLoader, null);
/*  427:     */   }
/*  428:     */   
/*  429:     */   protected static Object createFactory(String factoryClass, ClassLoader classLoader)
/*  430:     */   {
/*  431:1123 */     Class logFactoryClass = null;
/*  432:     */     try
/*  433:     */     {
/*  434:1125 */       if (classLoader != null) {
/*  435:     */         try
/*  436:     */         {
/*  437:1131 */           logFactoryClass = classLoader.loadClass(factoryClass);
/*  438:1132 */           if (LogFactory.class.isAssignableFrom(logFactoryClass))
/*  439:     */           {
/*  440:1133 */             if (isDiagnosticsEnabled()) {
/*  441:1134 */               logDiagnostic("Loaded class " + logFactoryClass.getName() + " from classloader " + objectId(classLoader));
/*  442:     */             }
/*  443:     */           }
/*  444:1150 */           else if (isDiagnosticsEnabled())
/*  445:     */           {
/*  446:1151 */             logDiagnostic("Factory class " + logFactoryClass.getName() + " loaded from classloader " + objectId(logFactoryClass.getClassLoader()) + " does not extend '" + LogFactory.class.getName() + "' as loaded by this classloader.");
/*  447:     */             
/*  448:     */ 
/*  449:     */ 
/*  450:     */ 
/*  451:1156 */             logHierarchy("[BAD CL TREE] ", classLoader);
/*  452:     */           }
/*  453:1160 */           return (LogFactory)logFactoryClass.newInstance();
/*  454:     */         }
/*  455:     */         catch (ClassNotFoundException ex)
/*  456:     */         {
/*  457:1163 */           if (classLoader == thisClassLoader)
/*  458:     */           {
/*  459:1165 */             if (isDiagnosticsEnabled()) {
/*  460:1166 */               logDiagnostic("Unable to locate any class called '" + factoryClass + "' via classloader " + objectId(classLoader));
/*  461:     */             }
/*  462:1170 */             throw ex;
/*  463:     */           }
/*  464:     */         }
/*  465:     */         catch (NoClassDefFoundError e)
/*  466:     */         {
/*  467:1174 */           if (classLoader == thisClassLoader)
/*  468:     */           {
/*  469:1176 */             if (isDiagnosticsEnabled()) {
/*  470:1177 */               logDiagnostic("Class '" + factoryClass + "' cannot be loaded" + " via classloader " + objectId(classLoader) + " - it depends on some other class that cannot" + " be found.");
/*  471:     */             }
/*  472:1183 */             throw e;
/*  473:     */           }
/*  474:     */         }
/*  475:     */         catch (ClassCastException e)
/*  476:     */         {
/*  477:1187 */           if (classLoader == thisClassLoader)
/*  478:     */           {
/*  479:1193 */             boolean implementsLogFactory = implementsLogFactory(logFactoryClass);
/*  480:     */             
/*  481:     */ 
/*  482:     */ 
/*  483:     */ 
/*  484:     */ 
/*  485:     */ 
/*  486:1200 */             String msg = "The application has specified that a custom LogFactory implementation should be used but Class '" + factoryClass + "' cannot be converted to '" + LogFactory.class.getName() + "'. ";
/*  487:1204 */             if (implementsLogFactory) {
/*  488:1205 */               msg = msg + "The conflict is caused by the presence of multiple LogFactory classes in incompatible classloaders. " + "Background can be found in http://commons.apache.org/logging/tech.html. " + "If you have not explicitly specified a custom LogFactory then it is likely that " + "the container has set one without your knowledge. " + "In this case, consider using the commons-logging-adapters.jar file or " + "specifying the standard LogFactory from the command line. ";
/*  489:     */             } else {
/*  490:1212 */               msg = msg + "Please check the custom implementation. ";
/*  491:     */             }
/*  492:1214 */             msg = msg + "Help can be found @http://commons.apache.org/logging/troubleshooting.html.";
/*  493:1216 */             if (isDiagnosticsEnabled()) {
/*  494:1217 */               logDiagnostic(msg);
/*  495:     */             }
/*  496:1220 */             ClassCastException ex = new ClassCastException(msg);
/*  497:1221 */             throw ex;
/*  498:     */           }
/*  499:     */         }
/*  500:     */       }
/*  501:1255 */       if (isDiagnosticsEnabled()) {
/*  502:1256 */         logDiagnostic("Unable to load factory class via classloader " + objectId(classLoader) + " - trying the classloader associated with this LogFactory.");
/*  503:     */       }
/*  504:1261 */       logFactoryClass = Class.forName(factoryClass);
/*  505:1262 */       return (LogFactory)logFactoryClass.newInstance();
/*  506:     */     }
/*  507:     */     catch (Exception e)
/*  508:     */     {
/*  509:1265 */       if (isDiagnosticsEnabled()) {
/*  510:1266 */         logDiagnostic("Unable to create LogFactory instance.");
/*  511:     */       }
/*  512:1268 */       if ((logFactoryClass != null) && (!LogFactory.class.isAssignableFrom(logFactoryClass))) {
/*  513:1271 */         return new LogConfigurationException("The chosen LogFactory implementation does not extend LogFactory. Please check your configuration.", e);
/*  514:     */       }
/*  515:1276 */       return new LogConfigurationException(e);
/*  516:     */     }
/*  517:     */   }
/*  518:     */   
/*  519:     */   private static boolean implementsLogFactory(Class logFactoryClass)
/*  520:     */   {
/*  521:1293 */     boolean implementsLogFactory = false;
/*  522:1294 */     if (logFactoryClass != null) {
/*  523:     */       try
/*  524:     */       {
/*  525:1296 */         ClassLoader logFactoryClassLoader = logFactoryClass.getClassLoader();
/*  526:1297 */         if (logFactoryClassLoader == null)
/*  527:     */         {
/*  528:1298 */           logDiagnostic("[CUSTOM LOG FACTORY] was loaded by the boot classloader");
/*  529:     */         }
/*  530:     */         else
/*  531:     */         {
/*  532:1300 */           logHierarchy("[CUSTOM LOG FACTORY] ", logFactoryClassLoader);
/*  533:1301 */           Class factoryFromCustomLoader = Class.forName("org.apache.commons.logging.LogFactory", false, logFactoryClassLoader);
/*  534:     */           
/*  535:1303 */           implementsLogFactory = factoryFromCustomLoader.isAssignableFrom(logFactoryClass);
/*  536:1304 */           if (implementsLogFactory) {
/*  537:1305 */             logDiagnostic("[CUSTOM LOG FACTORY] " + logFactoryClass.getName() + " implements LogFactory but was loaded by an incompatible classloader.");
/*  538:     */           } else {
/*  539:1308 */             logDiagnostic("[CUSTOM LOG FACTORY] " + logFactoryClass.getName() + " does not implement LogFactory.");
/*  540:     */           }
/*  541:     */         }
/*  542:     */       }
/*  543:     */       catch (SecurityException e)
/*  544:     */       {
/*  545:1318 */         logDiagnostic("[CUSTOM LOG FACTORY] SecurityException thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: " + e.getMessage());
/*  546:     */       }
/*  547:     */       catch (LinkageError e)
/*  548:     */       {
/*  549:1328 */         logDiagnostic("[CUSTOM LOG FACTORY] LinkageError thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: " + e.getMessage());
/*  550:     */       }
/*  551:     */       catch (ClassNotFoundException e)
/*  552:     */       {
/*  553:1339 */         logDiagnostic("[CUSTOM LOG FACTORY] LogFactory class cannot be loaded by classloader which loaded the custom LogFactory implementation. Is the custom factory in the right classloader?");
/*  554:     */       }
/*  555:     */     }
/*  556:1343 */     return implementsLogFactory;
/*  557:     */   }
/*  558:     */   
/*  559:     */   private static InputStream getResourceAsStream(ClassLoader loader, String name)
/*  560:     */   {
/*  561:1355 */     (InputStream)AccessController.doPrivileged(new PrivilegedAction()
/*  562:     */     {
/*  563:     */       private final ClassLoader val$loader;
/*  564:     */       private final String val$name;
/*  565:     */       
/*  566:     */       public Object run()
/*  567:     */       {
/*  568:1358 */         if (this.val$loader != null) {
/*  569:1359 */           return this.val$loader.getResourceAsStream(this.val$name);
/*  570:     */         }
/*  571:1361 */         return ClassLoader.getSystemResourceAsStream(this.val$name);
/*  572:     */       }
/*  573:     */     });
/*  574:     */   }
/*  575:     */   
/*  576:     */   private static Enumeration getResources(ClassLoader loader, String name)
/*  577:     */   {
/*  578:1383 */     PrivilegedAction action = new PrivilegedAction()
/*  579:     */     {
/*  580:     */       private final ClassLoader val$loader;
/*  581:     */       private final String val$name;
/*  582:     */       
/*  583:     */       public Object run()
/*  584:     */       {
/*  585:     */         try
/*  586:     */         {
/*  587:1387 */           if (this.val$loader != null) {
/*  588:1388 */             return this.val$loader.getResources(this.val$name);
/*  589:     */           }
/*  590:1390 */           return ClassLoader.getSystemResources(this.val$name);
/*  591:     */         }
/*  592:     */         catch (IOException e)
/*  593:     */         {
/*  594:1393 */           if (LogFactory.isDiagnosticsEnabled()) {
/*  595:1394 */             LogFactory.logDiagnostic("Exception while trying to find configuration file " + this.val$name + ":" + e.getMessage());
/*  596:     */           }
/*  597:1398 */           return null;
/*  598:     */         }
/*  599:     */         catch (NoSuchMethodError e) {}
/*  600:1403 */         return null;
/*  601:     */       }
/*  602:1406 */     };
/*  603:1407 */     Object result = AccessController.doPrivileged(action);
/*  604:1408 */     return (Enumeration)result;
/*  605:     */   }
/*  606:     */   
/*  607:     */   private static Properties getProperties(URL url)
/*  608:     */   {
/*  609:1420 */     PrivilegedAction action = new PrivilegedAction()
/*  610:     */     {
/*  611:     */       private final URL val$url;
/*  612:     */       
/*  613:     */       public Object run()
/*  614:     */       {
/*  615:     */         try
/*  616:     */         {
/*  617:1424 */           InputStream stream = this.val$url.openStream();
/*  618:1425 */           if (stream != null)
/*  619:     */           {
/*  620:1426 */             Properties props = new Properties();
/*  621:1427 */             props.load(stream);
/*  622:1428 */             stream.close();
/*  623:1429 */             return props;
/*  624:     */           }
/*  625:     */         }
/*  626:     */         catch (IOException e)
/*  627:     */         {
/*  628:1432 */           if (LogFactory.isDiagnosticsEnabled()) {
/*  629:1433 */             LogFactory.logDiagnostic("Unable to read URL " + this.val$url);
/*  630:     */           }
/*  631:     */         }
/*  632:1437 */         return null;
/*  633:     */       }
/*  634:1439 */     };
/*  635:1440 */     return (Properties)AccessController.doPrivileged(action);
/*  636:     */   }
/*  637:     */   
/*  638:     */   private static final Properties getConfigurationFile(ClassLoader classLoader, String fileName)
/*  639:     */   {
/*  640:1465 */     Properties props = null;
/*  641:1466 */     double priority = 0.0D;
/*  642:1467 */     URL propsUrl = null;
/*  643:     */     try
/*  644:     */     {
/*  645:1469 */       Enumeration urls = getResources(classLoader, fileName);
/*  646:1471 */       if (urls == null) {
/*  647:1472 */         return null;
/*  648:     */       }
/*  649:1475 */       while (urls.hasMoreElements())
/*  650:     */       {
/*  651:1476 */         URL url = (URL)urls.nextElement();
/*  652:     */         
/*  653:1478 */         Properties newProps = getProperties(url);
/*  654:1479 */         if (newProps != null) {
/*  655:1480 */           if (props == null)
/*  656:     */           {
/*  657:1481 */             propsUrl = url;
/*  658:1482 */             props = newProps;
/*  659:1483 */             String priorityStr = props.getProperty("priority");
/*  660:1484 */             priority = 0.0D;
/*  661:1485 */             if (priorityStr != null) {
/*  662:1486 */               priority = Double.parseDouble(priorityStr);
/*  663:     */             }
/*  664:1489 */             if (isDiagnosticsEnabled()) {
/*  665:1490 */               logDiagnostic("[LOOKUP] Properties file found at '" + url + "'" + " with priority " + priority);
/*  666:     */             }
/*  667:     */           }
/*  668:     */           else
/*  669:     */           {
/*  670:1495 */             String newPriorityStr = newProps.getProperty("priority");
/*  671:1496 */             double newPriority = 0.0D;
/*  672:1497 */             if (newPriorityStr != null) {
/*  673:1498 */               newPriority = Double.parseDouble(newPriorityStr);
/*  674:     */             }
/*  675:1501 */             if (newPriority > priority)
/*  676:     */             {
/*  677:1502 */               if (isDiagnosticsEnabled()) {
/*  678:1503 */                 logDiagnostic("[LOOKUP] Properties file at '" + url + "'" + " with priority " + newPriority + " overrides file at '" + propsUrl + "'" + " with priority " + priority);
/*  679:     */               }
/*  680:1510 */               propsUrl = url;
/*  681:1511 */               props = newProps;
/*  682:1512 */               priority = newPriority;
/*  683:     */             }
/*  684:1514 */             else if (isDiagnosticsEnabled())
/*  685:     */             {
/*  686:1515 */               logDiagnostic("[LOOKUP] Properties file at '" + url + "'" + " with priority " + newPriority + " does not override file at '" + propsUrl + "'" + " with priority " + priority);
/*  687:     */             }
/*  688:     */           }
/*  689:     */         }
/*  690:     */       }
/*  691:     */     }
/*  692:     */     catch (SecurityException e)
/*  693:     */     {
/*  694:1527 */       if (isDiagnosticsEnabled()) {
/*  695:1528 */         logDiagnostic("SecurityException thrown while trying to find/read config files.");
/*  696:     */       }
/*  697:     */     }
/*  698:1532 */     if (isDiagnosticsEnabled()) {
/*  699:1533 */       if (props == null) {
/*  700:1534 */         logDiagnostic("[LOOKUP] No properties file of name '" + fileName + "' found.");
/*  701:     */       } else {
/*  702:1538 */         logDiagnostic("[LOOKUP] Properties file of name '" + fileName + "' found at '" + propsUrl + '"');
/*  703:     */       }
/*  704:     */     }
/*  705:1544 */     return props;
/*  706:     */   }
/*  707:     */   
/*  708:     */   private static String getSystemProperty(String key, String def)
/*  709:     */     throws SecurityException
/*  710:     */   {
/*  711:1558 */     (String)AccessController.doPrivileged(new PrivilegedAction()
/*  712:     */     {
/*  713:     */       private final String val$key;
/*  714:     */       private final String val$def;
/*  715:     */       
/*  716:     */       public Object run()
/*  717:     */       {
/*  718:1561 */         return System.getProperty(this.val$key, this.val$def);
/*  719:     */       }
/*  720:     */     });
/*  721:     */   }
/*  722:     */   
/*  723:     */   private static void initDiagnostics()
/*  724:     */   {
/*  725:     */     try
/*  726:     */     {
/*  727:1575 */       String dest = getSystemProperty("org.apache.commons.logging.diagnostics.dest", null);
/*  728:1576 */       if (dest == null) {
/*  729:     */         return;
/*  730:     */       }
/*  731:     */     }
/*  732:     */     catch (SecurityException ex)
/*  733:     */     {
/*  734:     */       return;
/*  735:     */     }
/*  736:     */     String dest;
/*  737:1585 */     if (dest.equals("STDOUT")) {
/*  738:1586 */       diagnosticsStream = System.out;
/*  739:1587 */     } else if (dest.equals("STDERR")) {
/*  740:1588 */       diagnosticsStream = System.err;
/*  741:     */     } else {
/*  742:     */       try
/*  743:     */       {
/*  744:1592 */         FileOutputStream fos = new FileOutputStream(dest, true);
/*  745:1593 */         diagnosticsStream = new PrintStream(fos);
/*  746:     */       }
/*  747:     */       catch (IOException ex)
/*  748:     */       {
/*  749:     */         return;
/*  750:     */       }
/*  751:     */     }
/*  752:     */     String classLoaderName;
/*  753:     */     try
/*  754:     */     {
/*  755:1611 */       ClassLoader classLoader = thisClassLoader;
/*  756:     */       String classLoaderName;
/*  757:1612 */       if (thisClassLoader == null) {
/*  758:1613 */         classLoaderName = "BOOTLOADER";
/*  759:     */       } else {
/*  760:1615 */         classLoaderName = objectId(classLoader);
/*  761:     */       }
/*  762:     */     }
/*  763:     */     catch (SecurityException e)
/*  764:     */     {
/*  765:     */       String classLoaderName;
/*  766:1618 */       classLoaderName = "UNKNOWN";
/*  767:     */     }
/*  768:1620 */     diagnosticPrefix = "[LogFactory from " + classLoaderName + "] ";
/*  769:     */   }
/*  770:     */   
/*  771:     */   protected static boolean isDiagnosticsEnabled()
/*  772:     */   {
/*  773:1633 */     return diagnosticsStream != null;
/*  774:     */   }
/*  775:     */   
/*  776:     */   private static final void logDiagnostic(String msg)
/*  777:     */   {
/*  778:1655 */     if (diagnosticsStream != null)
/*  779:     */     {
/*  780:1656 */       diagnosticsStream.print(diagnosticPrefix);
/*  781:1657 */       diagnosticsStream.println(msg);
/*  782:1658 */       diagnosticsStream.flush();
/*  783:     */     }
/*  784:     */   }
/*  785:     */   
/*  786:     */   protected static final void logRawDiagnostic(String msg)
/*  787:     */   {
/*  788:1669 */     if (diagnosticsStream != null)
/*  789:     */     {
/*  790:1670 */       diagnosticsStream.println(msg);
/*  791:1671 */       diagnosticsStream.flush();
/*  792:     */     }
/*  793:     */   }
/*  794:     */   
/*  795:     */   private static void logClassLoaderEnvironment(Class clazz)
/*  796:     */   {
/*  797:1693 */     if (!isDiagnosticsEnabled()) {
/*  798:1694 */       return;
/*  799:     */     }
/*  800:     */     try
/*  801:     */     {
/*  802:1701 */       logDiagnostic("[ENV] Extension directories (java.ext.dir): " + System.getProperty("java.ext.dir"));
/*  803:1702 */       logDiagnostic("[ENV] Application classpath (java.class.path): " + System.getProperty("java.class.path"));
/*  804:     */     }
/*  805:     */     catch (SecurityException ex)
/*  806:     */     {
/*  807:1704 */       logDiagnostic("[ENV] Security setting prevent interrogation of system classpaths.");
/*  808:     */     }
/*  809:1707 */     String className = clazz.getName();
/*  810:     */     try
/*  811:     */     {
/*  812:1711 */       classLoader = getClassLoader(clazz);
/*  813:     */     }
/*  814:     */     catch (SecurityException ex)
/*  815:     */     {
/*  816:     */       ClassLoader classLoader;
/*  817:1714 */       logDiagnostic("[ENV] Security forbids determining the classloader for " + className); return;
/*  818:     */     }
/*  819:     */     ClassLoader classLoader;
/*  820:1719 */     logDiagnostic("[ENV] Class " + className + " was loaded via classloader " + objectId(classLoader));
/*  821:     */     
/*  822:     */ 
/*  823:1722 */     logHierarchy("[ENV] Ancestry of classloader which loaded " + className + " is ", classLoader);
/*  824:     */   }
/*  825:     */   
/*  826:     */   private static void logHierarchy(String prefix, ClassLoader classLoader)
/*  827:     */   {
/*  828:1733 */     if (!isDiagnosticsEnabled()) {
/*  829:1734 */       return;
/*  830:     */     }
/*  831:1737 */     if (classLoader != null)
/*  832:     */     {
/*  833:1738 */       String classLoaderString = classLoader.toString();
/*  834:1739 */       logDiagnostic(prefix + objectId(classLoader) + " == '" + classLoaderString + "'");
/*  835:     */     }
/*  836:     */     try
/*  837:     */     {
/*  838:1743 */       systemClassLoader = ClassLoader.getSystemClassLoader();
/*  839:     */     }
/*  840:     */     catch (SecurityException ex)
/*  841:     */     {
/*  842:     */       ClassLoader systemClassLoader;
/*  843:1745 */       logDiagnostic(prefix + "Security forbids determining the system classloader."); return;
/*  844:     */     }
/*  845:     */     ClassLoader systemClassLoader;
/*  846:1749 */     if (classLoader != null)
/*  847:     */     {
/*  848:1750 */       StringBuffer buf = new StringBuffer(prefix + "ClassLoader tree:");
/*  849:     */       do
/*  850:     */       {
/*  851:1752 */         buf.append(objectId(classLoader));
/*  852:1753 */         if (classLoader == systemClassLoader) {
/*  853:1754 */           buf.append(" (SYSTEM) ");
/*  854:     */         }
/*  855:     */         try
/*  856:     */         {
/*  857:1758 */           classLoader = classLoader.getParent();
/*  858:     */         }
/*  859:     */         catch (SecurityException ex)
/*  860:     */         {
/*  861:1760 */           buf.append(" --> SECRET");
/*  862:1761 */           break;
/*  863:     */         }
/*  864:1764 */         buf.append(" --> ");
/*  865:1765 */       } while (classLoader != null);
/*  866:1766 */       buf.append("BOOT");
/*  867:     */       
/*  868:     */ 
/*  869:     */ 
/*  870:1770 */       logDiagnostic(buf.toString());
/*  871:     */     }
/*  872:     */   }
/*  873:     */   
/*  874:     */   public static String objectId(Object o)
/*  875:     */   {
/*  876:1787 */     if (o == null) {
/*  877:1788 */       return "null";
/*  878:     */     }
/*  879:1790 */     return o.getClass().getName() + "@" + System.identityHashCode(o);
/*  880:     */   }
/*  881:     */   
/*  882:     */   static
/*  883:     */   {
/*  884:1816 */     thisClassLoader = getClassLoader(LogFactory.class);
/*  885:1817 */     initDiagnostics();
/*  886:1818 */     logClassLoaderEnvironment(LogFactory.class);
/*  887:1819 */     factories = createFactoryStore();
/*  888:1820 */     if (isDiagnosticsEnabled()) {
/*  889:1821 */       logDiagnostic("BOOTSTRAP COMPLETED");
/*  890:     */     }
/*  891:     */   }
/*  892:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.LogFactory
 * JD-Core Version:    0.7.0.1
 */