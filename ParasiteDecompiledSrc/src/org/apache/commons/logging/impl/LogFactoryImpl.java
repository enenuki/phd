/*    1:     */ package org.apache.commons.logging.impl;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.Constructor;
/*    4:     */ import java.lang.reflect.InvocationTargetException;
/*    5:     */ import java.lang.reflect.Method;
/*    6:     */ import java.net.URL;
/*    7:     */ import java.security.AccessController;
/*    8:     */ import java.security.PrivilegedAction;
/*    9:     */ import java.util.Enumeration;
/*   10:     */ import java.util.Hashtable;
/*   11:     */ import java.util.Vector;
/*   12:     */ import org.apache.commons.logging.Log;
/*   13:     */ import org.apache.commons.logging.LogConfigurationException;
/*   14:     */ import org.apache.commons.logging.LogFactory;
/*   15:     */ 
/*   16:     */ public class LogFactoryImpl
/*   17:     */   extends LogFactory
/*   18:     */ {
/*   19:     */   private static final String LOGGING_IMPL_LOG4J_LOGGER = "org.apache.commons.logging.impl.Log4JLogger";
/*   20:     */   private static final String LOGGING_IMPL_JDK14_LOGGER = "org.apache.commons.logging.impl.Jdk14Logger";
/*   21:     */   private static final String LOGGING_IMPL_LUMBERJACK_LOGGER = "org.apache.commons.logging.impl.Jdk13LumberjackLogger";
/*   22:     */   private static final String LOGGING_IMPL_SIMPLE_LOGGER = "org.apache.commons.logging.impl.SimpleLog";
/*   23:     */   private static final String PKG_IMPL = "org.apache.commons.logging.impl.";
/*   24:  84 */   private static final int PKG_LEN = "org.apache.commons.logging.impl.".length();
/*   25:     */   public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";
/*   26:     */   protected static final String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";
/*   27:     */   public static final String ALLOW_FLAWED_CONTEXT_PROPERTY = "org.apache.commons.logging.Log.allowFlawedContext";
/*   28:     */   public static final String ALLOW_FLAWED_DISCOVERY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedDiscovery";
/*   29:     */   public static final String ALLOW_FLAWED_HIERARCHY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedHierarchy";
/*   30:     */   
/*   31:     */   public LogFactoryImpl()
/*   32:     */   {
/*   33:  95 */     initDiagnostics();
/*   34:  96 */     if (isDiagnosticsEnabled()) {
/*   35:  97 */       logDiagnostic("Instance created.");
/*   36:     */     }
/*   37:     */   }
/*   38:     */   
/*   39: 176 */   private static final String[] classesToDiscover = { "org.apache.commons.logging.impl.Log4JLogger", "org.apache.commons.logging.impl.Jdk14Logger", "org.apache.commons.logging.impl.Jdk13LumberjackLogger", "org.apache.commons.logging.impl.SimpleLog" };
/*   40: 190 */   private boolean useTCCL = true;
/*   41:     */   private String diagnosticPrefix;
/*   42: 201 */   protected Hashtable attributes = new Hashtable();
/*   43: 208 */   protected Hashtable instances = new Hashtable();
/*   44:     */   private String logClassName;
/*   45: 224 */   protected Constructor logConstructor = null;
/*   46: 230 */   protected Class[] logConstructorSignature = { String.class };
/*   47: 238 */   protected Method logMethod = null;
/*   48: 244 */   protected Class[] logMethodSignature = { LogFactory.class };
/*   49:     */   private boolean allowFlawedContext;
/*   50:     */   private boolean allowFlawedDiscovery;
/*   51:     */   private boolean allowFlawedHierarchy;
/*   52:     */   
/*   53:     */   public Object getAttribute(String name)
/*   54:     */   {
/*   55: 273 */     return this.attributes.get(name);
/*   56:     */   }
/*   57:     */   
/*   58:     */   public String[] getAttributeNames()
/*   59:     */   {
/*   60: 285 */     Vector names = new Vector();
/*   61: 286 */     Enumeration keys = this.attributes.keys();
/*   62: 287 */     while (keys.hasMoreElements()) {
/*   63: 288 */       names.addElement((String)keys.nextElement());
/*   64:     */     }
/*   65: 290 */     String[] results = new String[names.size()];
/*   66: 291 */     for (int i = 0; i < results.length; i++) {
/*   67: 292 */       results[i] = ((String)names.elementAt(i));
/*   68:     */     }
/*   69: 294 */     return results;
/*   70:     */   }
/*   71:     */   
/*   72:     */   public Log getInstance(Class clazz)
/*   73:     */     throws LogConfigurationException
/*   74:     */   {
/*   75: 310 */     return getInstance(clazz.getName());
/*   76:     */   }
/*   77:     */   
/*   78:     */   public Log getInstance(String name)
/*   79:     */     throws LogConfigurationException
/*   80:     */   {
/*   81: 334 */     Log instance = (Log)this.instances.get(name);
/*   82: 335 */     if (instance == null)
/*   83:     */     {
/*   84: 336 */       instance = newInstance(name);
/*   85: 337 */       this.instances.put(name, instance);
/*   86:     */     }
/*   87: 339 */     return instance;
/*   88:     */   }
/*   89:     */   
/*   90:     */   public void release()
/*   91:     */   {
/*   92: 354 */     logDiagnostic("Releasing all known loggers");
/*   93: 355 */     this.instances.clear();
/*   94:     */   }
/*   95:     */   
/*   96:     */   public void removeAttribute(String name)
/*   97:     */   {
/*   98: 367 */     this.attributes.remove(name);
/*   99:     */   }
/*  100:     */   
/*  101:     */   public void setAttribute(String name, Object value)
/*  102:     */   {
/*  103: 398 */     if (this.logConstructor != null) {
/*  104: 399 */       logDiagnostic("setAttribute: call too late; configuration already performed.");
/*  105:     */     }
/*  106: 402 */     if (value == null) {
/*  107: 403 */       this.attributes.remove(name);
/*  108:     */     } else {
/*  109: 405 */       this.attributes.put(name, value);
/*  110:     */     }
/*  111: 408 */     if (name.equals("use_tccl")) {
/*  112: 409 */       this.useTCCL = Boolean.valueOf(value.toString()).booleanValue();
/*  113:     */     }
/*  114:     */   }
/*  115:     */   
/*  116:     */   protected static ClassLoader getContextClassLoader()
/*  117:     */     throws LogConfigurationException
/*  118:     */   {
/*  119: 428 */     return LogFactory.getContextClassLoader();
/*  120:     */   }
/*  121:     */   
/*  122:     */   protected static boolean isDiagnosticsEnabled()
/*  123:     */   {
/*  124: 437 */     return LogFactory.isDiagnosticsEnabled();
/*  125:     */   }
/*  126:     */   
/*  127:     */   protected static ClassLoader getClassLoader(Class clazz)
/*  128:     */   {
/*  129: 447 */     return LogFactory.getClassLoader(clazz);
/*  130:     */   }
/*  131:     */   
/*  132:     */   private void initDiagnostics()
/*  133:     */   {
/*  134: 475 */     Class clazz = getClass();
/*  135: 476 */     ClassLoader classLoader = getClassLoader(clazz);
/*  136:     */     String classLoaderName;
/*  137:     */     try
/*  138:     */     {
/*  139:     */       String classLoaderName;
/*  140: 479 */       if (classLoader == null) {
/*  141: 480 */         classLoaderName = "BOOTLOADER";
/*  142:     */       } else {
/*  143: 482 */         classLoaderName = LogFactory.objectId(classLoader);
/*  144:     */       }
/*  145:     */     }
/*  146:     */     catch (SecurityException e)
/*  147:     */     {
/*  148:     */       String classLoaderName;
/*  149: 485 */       classLoaderName = "UNKNOWN";
/*  150:     */     }
/*  151: 487 */     this.diagnosticPrefix = ("[LogFactoryImpl@" + System.identityHashCode(this) + " from " + classLoaderName + "] ");
/*  152:     */   }
/*  153:     */   
/*  154:     */   protected void logDiagnostic(String msg)
/*  155:     */   {
/*  156: 499 */     if (isDiagnosticsEnabled()) {
/*  157: 500 */       LogFactory.logRawDiagnostic(this.diagnosticPrefix + msg);
/*  158:     */     }
/*  159:     */   }
/*  160:     */   
/*  161:     */   /**
/*  162:     */    * @deprecated
/*  163:     */    */
/*  164:     */   protected String getLogClassName()
/*  165:     */   {
/*  166: 513 */     if (this.logClassName == null) {
/*  167: 514 */       discoverLogImplementation(getClass().getName());
/*  168:     */     }
/*  169: 517 */     return this.logClassName;
/*  170:     */   }
/*  171:     */   
/*  172:     */   /**
/*  173:     */    * @deprecated
/*  174:     */    */
/*  175:     */   protected Constructor getLogConstructor()
/*  176:     */     throws LogConfigurationException
/*  177:     */   {
/*  178: 540 */     if (this.logConstructor == null) {
/*  179: 541 */       discoverLogImplementation(getClass().getName());
/*  180:     */     }
/*  181: 544 */     return this.logConstructor;
/*  182:     */   }
/*  183:     */   
/*  184:     */   /**
/*  185:     */    * @deprecated
/*  186:     */    */
/*  187:     */   protected boolean isJdk13LumberjackAvailable()
/*  188:     */   {
/*  189: 555 */     return isLogLibraryAvailable("Jdk13Lumberjack", "org.apache.commons.logging.impl.Jdk13LumberjackLogger");
/*  190:     */   }
/*  191:     */   
/*  192:     */   /**
/*  193:     */    * @deprecated
/*  194:     */    */
/*  195:     */   protected boolean isJdk14Available()
/*  196:     */   {
/*  197: 571 */     return isLogLibraryAvailable("Jdk14", "org.apache.commons.logging.impl.Jdk14Logger");
/*  198:     */   }
/*  199:     */   
/*  200:     */   /**
/*  201:     */    * @deprecated
/*  202:     */    */
/*  203:     */   protected boolean isLog4JAvailable()
/*  204:     */   {
/*  205: 584 */     return isLogLibraryAvailable("Log4J", "org.apache.commons.logging.impl.Log4JLogger");
/*  206:     */   }
/*  207:     */   
/*  208:     */   protected Log newInstance(String name)
/*  209:     */     throws LogConfigurationException
/*  210:     */   {
/*  211: 601 */     Log instance = null;
/*  212:     */     try
/*  213:     */     {
/*  214: 603 */       if (this.logConstructor == null)
/*  215:     */       {
/*  216: 604 */         instance = discoverLogImplementation(name);
/*  217:     */       }
/*  218:     */       else
/*  219:     */       {
/*  220: 607 */         Object[] params = { name };
/*  221: 608 */         instance = (Log)this.logConstructor.newInstance(params);
/*  222:     */       }
/*  223: 611 */       if (this.logMethod != null)
/*  224:     */       {
/*  225: 612 */         Object[] params = { this };
/*  226: 613 */         this.logMethod.invoke(instance, params);
/*  227:     */       }
/*  228: 616 */       return instance;
/*  229:     */     }
/*  230:     */     catch (LogConfigurationException lce)
/*  231:     */     {
/*  232: 623 */       throw lce;
/*  233:     */     }
/*  234:     */     catch (InvocationTargetException e)
/*  235:     */     {
/*  236: 628 */       Throwable c = e.getTargetException();
/*  237: 629 */       if (c != null) {
/*  238: 630 */         throw new LogConfigurationException(c);
/*  239:     */       }
/*  240: 632 */       throw new LogConfigurationException(e);
/*  241:     */     }
/*  242:     */     catch (Throwable t)
/*  243:     */     {
/*  244: 637 */       throw new LogConfigurationException(t);
/*  245:     */     }
/*  246:     */   }
/*  247:     */   
/*  248:     */   private static ClassLoader getContextClassLoaderInternal()
/*  249:     */     throws LogConfigurationException
/*  250:     */   {
/*  251: 664 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  252:     */     {
/*  253:     */       public Object run()
/*  254:     */       {
/*  255: 667 */         return LogFactoryImpl.access$000();
/*  256:     */       }
/*  257:     */     });
/*  258:     */   }
/*  259:     */   
/*  260:     */   private static String getSystemProperty(String key, String def)
/*  261:     */     throws SecurityException
/*  262:     */   {
/*  263: 683 */     (String)AccessController.doPrivileged(new PrivilegedAction()
/*  264:     */     {
/*  265:     */       private final String val$key;
/*  266:     */       private final String val$def;
/*  267:     */       
/*  268:     */       public Object run()
/*  269:     */       {
/*  270: 686 */         return System.getProperty(this.val$key, this.val$def);
/*  271:     */       }
/*  272:     */     });
/*  273:     */   }
/*  274:     */   
/*  275:     */   private ClassLoader getParentClassLoader(ClassLoader cl)
/*  276:     */   {
/*  277:     */     try
/*  278:     */     {
/*  279: 700 */       (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  280:     */       {
/*  281:     */         private final ClassLoader val$cl;
/*  282:     */         
/*  283:     */         public Object run()
/*  284:     */         {
/*  285: 703 */           return this.val$cl.getParent();
/*  286:     */         }
/*  287:     */       });
/*  288:     */     }
/*  289:     */     catch (SecurityException ex)
/*  290:     */     {
/*  291: 707 */       logDiagnostic("[SECURITY] Unable to obtain parent classloader");
/*  292:     */     }
/*  293: 708 */     return null;
/*  294:     */   }
/*  295:     */   
/*  296:     */   private boolean isLogLibraryAvailable(String name, String classname)
/*  297:     */   {
/*  298: 719 */     if (isDiagnosticsEnabled()) {
/*  299: 720 */       logDiagnostic("Checking for '" + name + "'.");
/*  300:     */     }
/*  301:     */     try
/*  302:     */     {
/*  303: 723 */       Log log = createLogFromClass(classname, getClass().getName(), false);
/*  304: 728 */       if (log == null)
/*  305:     */       {
/*  306: 729 */         if (isDiagnosticsEnabled()) {
/*  307: 730 */           logDiagnostic("Did not find '" + name + "'.");
/*  308:     */         }
/*  309: 732 */         return false;
/*  310:     */       }
/*  311: 734 */       if (isDiagnosticsEnabled()) {
/*  312: 735 */         logDiagnostic("Found '" + name + "'.");
/*  313:     */       }
/*  314: 737 */       return true;
/*  315:     */     }
/*  316:     */     catch (LogConfigurationException e)
/*  317:     */     {
/*  318: 740 */       if (isDiagnosticsEnabled()) {
/*  319: 741 */         logDiagnostic("Logging system '" + name + "' is available but not useable.");
/*  320:     */       }
/*  321:     */     }
/*  322: 743 */     return false;
/*  323:     */   }
/*  324:     */   
/*  325:     */   private String getConfigurationValue(String property)
/*  326:     */   {
/*  327: 759 */     if (isDiagnosticsEnabled()) {
/*  328: 760 */       logDiagnostic("[ENV] Trying to get configuration for item " + property);
/*  329:     */     }
/*  330: 763 */     Object valueObj = getAttribute(property);
/*  331: 764 */     if (valueObj != null)
/*  332:     */     {
/*  333: 765 */       if (isDiagnosticsEnabled()) {
/*  334: 766 */         logDiagnostic("[ENV] Found LogFactory attribute [" + valueObj + "] for " + property);
/*  335:     */       }
/*  336: 768 */       return valueObj.toString();
/*  337:     */     }
/*  338: 771 */     if (isDiagnosticsEnabled()) {
/*  339: 772 */       logDiagnostic("[ENV] No LogFactory attribute found for " + property);
/*  340:     */     }
/*  341:     */     try
/*  342:     */     {
/*  343: 780 */       String value = getSystemProperty(property, null);
/*  344: 781 */       if (value != null)
/*  345:     */       {
/*  346: 782 */         if (isDiagnosticsEnabled()) {
/*  347: 783 */           logDiagnostic("[ENV] Found system property [" + value + "] for " + property);
/*  348:     */         }
/*  349: 785 */         return value;
/*  350:     */       }
/*  351: 788 */       if (isDiagnosticsEnabled()) {
/*  352: 789 */         logDiagnostic("[ENV] No system property found for property " + property);
/*  353:     */       }
/*  354:     */     }
/*  355:     */     catch (SecurityException e)
/*  356:     */     {
/*  357: 792 */       if (isDiagnosticsEnabled()) {
/*  358: 793 */         logDiagnostic("[ENV] Security prevented reading system property " + property);
/*  359:     */       }
/*  360:     */     }
/*  361: 797 */     if (isDiagnosticsEnabled()) {
/*  362: 798 */       logDiagnostic("[ENV] No configuration defined for item " + property);
/*  363:     */     }
/*  364: 801 */     return null;
/*  365:     */   }
/*  366:     */   
/*  367:     */   private boolean getBooleanConfiguration(String key, boolean dflt)
/*  368:     */   {
/*  369: 809 */     String val = getConfigurationValue(key);
/*  370: 810 */     if (val == null) {
/*  371: 811 */       return dflt;
/*  372:     */     }
/*  373: 812 */     return Boolean.valueOf(val).booleanValue();
/*  374:     */   }
/*  375:     */   
/*  376:     */   private void initConfiguration()
/*  377:     */   {
/*  378: 823 */     this.allowFlawedContext = getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedContext", true);
/*  379: 824 */     this.allowFlawedDiscovery = getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedDiscovery", true);
/*  380: 825 */     this.allowFlawedHierarchy = getBooleanConfiguration("org.apache.commons.logging.Log.allowFlawedHierarchy", true);
/*  381:     */   }
/*  382:     */   
/*  383:     */   private Log discoverLogImplementation(String logCategory)
/*  384:     */     throws LogConfigurationException
/*  385:     */   {
/*  386: 841 */     if (isDiagnosticsEnabled()) {
/*  387: 842 */       logDiagnostic("Discovering a Log implementation...");
/*  388:     */     }
/*  389: 845 */     initConfiguration();
/*  390:     */     
/*  391: 847 */     Log result = null;
/*  392:     */     
/*  393:     */ 
/*  394: 850 */     String specifiedLogClassName = findUserSpecifiedLogClassName();
/*  395: 852 */     if (specifiedLogClassName != null)
/*  396:     */     {
/*  397: 853 */       if (isDiagnosticsEnabled()) {
/*  398: 854 */         logDiagnostic("Attempting to load user-specified log class '" + specifiedLogClassName + "'...");
/*  399:     */       }
/*  400: 858 */       result = createLogFromClass(specifiedLogClassName, logCategory, true);
/*  401: 861 */       if (result == null)
/*  402:     */       {
/*  403: 862 */         StringBuffer messageBuffer = new StringBuffer("User-specified log class '");
/*  404: 863 */         messageBuffer.append(specifiedLogClassName);
/*  405: 864 */         messageBuffer.append("' cannot be found or is not useable.");
/*  406: 868 */         if (specifiedLogClassName != null)
/*  407:     */         {
/*  408: 869 */           informUponSimilarName(messageBuffer, specifiedLogClassName, "org.apache.commons.logging.impl.Log4JLogger");
/*  409: 870 */           informUponSimilarName(messageBuffer, specifiedLogClassName, "org.apache.commons.logging.impl.Jdk14Logger");
/*  410: 871 */           informUponSimilarName(messageBuffer, specifiedLogClassName, "org.apache.commons.logging.impl.Jdk13LumberjackLogger");
/*  411: 872 */           informUponSimilarName(messageBuffer, specifiedLogClassName, "org.apache.commons.logging.impl.SimpleLog");
/*  412:     */         }
/*  413: 874 */         throw new LogConfigurationException(messageBuffer.toString());
/*  414:     */       }
/*  415: 877 */       return result;
/*  416:     */     }
/*  417: 908 */     if (isDiagnosticsEnabled()) {
/*  418: 909 */       logDiagnostic("No user-specified Log implementation; performing discovery using the standard supported logging implementations...");
/*  419:     */     }
/*  420: 913 */     for (int i = 0; (i < classesToDiscover.length) && (result == null); i++) {
/*  421: 914 */       result = createLogFromClass(classesToDiscover[i], logCategory, true);
/*  422:     */     }
/*  423: 917 */     if (result == null) {
/*  424: 918 */       throw new LogConfigurationException("No suitable Log implementation");
/*  425:     */     }
/*  426: 922 */     return result;
/*  427:     */   }
/*  428:     */   
/*  429:     */   private void informUponSimilarName(StringBuffer messageBuffer, String name, String candidate)
/*  430:     */   {
/*  431: 935 */     if (name.equals(candidate)) {
/*  432: 938 */       return;
/*  433:     */     }
/*  434: 944 */     if (name.regionMatches(true, 0, candidate, 0, PKG_LEN + 5))
/*  435:     */     {
/*  436: 945 */       messageBuffer.append(" Did you mean '");
/*  437: 946 */       messageBuffer.append(candidate);
/*  438: 947 */       messageBuffer.append("'?");
/*  439:     */     }
/*  440:     */   }
/*  441:     */   
/*  442:     */   private String findUserSpecifiedLogClassName()
/*  443:     */   {
/*  444: 961 */     if (isDiagnosticsEnabled()) {
/*  445: 962 */       logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.Log'");
/*  446:     */     }
/*  447: 964 */     String specifiedClass = (String)getAttribute("org.apache.commons.logging.Log");
/*  448: 966 */     if (specifiedClass == null)
/*  449:     */     {
/*  450: 967 */       if (isDiagnosticsEnabled()) {
/*  451: 968 */         logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.log'");
/*  452:     */       }
/*  453: 971 */       specifiedClass = (String)getAttribute("org.apache.commons.logging.log");
/*  454:     */     }
/*  455: 974 */     if (specifiedClass == null)
/*  456:     */     {
/*  457: 975 */       if (isDiagnosticsEnabled()) {
/*  458: 976 */         logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.Log'");
/*  459:     */       }
/*  460:     */       try
/*  461:     */       {
/*  462: 980 */         specifiedClass = getSystemProperty("org.apache.commons.logging.Log", null);
/*  463:     */       }
/*  464:     */       catch (SecurityException e)
/*  465:     */       {
/*  466: 982 */         if (isDiagnosticsEnabled()) {
/*  467: 983 */           logDiagnostic("No access allowed to system property 'org.apache.commons.logging.Log' - " + e.getMessage());
/*  468:     */         }
/*  469:     */       }
/*  470:     */     }
/*  471: 989 */     if (specifiedClass == null)
/*  472:     */     {
/*  473: 990 */       if (isDiagnosticsEnabled()) {
/*  474: 991 */         logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.log'");
/*  475:     */       }
/*  476:     */       try
/*  477:     */       {
/*  478: 995 */         specifiedClass = getSystemProperty("org.apache.commons.logging.log", null);
/*  479:     */       }
/*  480:     */       catch (SecurityException e)
/*  481:     */       {
/*  482: 997 */         if (isDiagnosticsEnabled()) {
/*  483: 998 */           logDiagnostic("No access allowed to system property 'org.apache.commons.logging.log' - " + e.getMessage());
/*  484:     */         }
/*  485:     */       }
/*  486:     */     }
/*  487:1007 */     if (specifiedClass != null) {
/*  488:1008 */       specifiedClass = specifiedClass.trim();
/*  489:     */     }
/*  490:1011 */     return specifiedClass;
/*  491:     */   }
/*  492:     */   
/*  493:     */   private Log createLogFromClass(String logAdapterClassName, String logCategory, boolean affectState)
/*  494:     */     throws LogConfigurationException
/*  495:     */   {
/*  496:1039 */     if (isDiagnosticsEnabled()) {
/*  497:1040 */       logDiagnostic("Attempting to instantiate '" + logAdapterClassName + "'");
/*  498:     */     }
/*  499:1043 */     Object[] params = { logCategory };
/*  500:1044 */     Log logAdapter = null;
/*  501:1045 */     Constructor constructor = null;
/*  502:     */     
/*  503:1047 */     Class logAdapterClass = null;
/*  504:1048 */     ClassLoader currentCL = getBaseClassLoader();
/*  505:     */     for (;;)
/*  506:     */     {
/*  507:1053 */       logDiagnostic("Trying to load '" + logAdapterClassName + "' from classloader " + LogFactory.objectId(currentCL));
/*  508:     */       try
/*  509:     */       {
/*  510:1059 */         if (isDiagnosticsEnabled())
/*  511:     */         {
/*  512:1065 */           String resourceName = logAdapterClassName.replace('.', '/') + ".class";
/*  513:     */           URL url;
/*  514:     */           URL url;
/*  515:1066 */           if (currentCL != null) {
/*  516:1067 */             url = currentCL.getResource(resourceName);
/*  517:     */           } else {
/*  518:1069 */             url = ClassLoader.getSystemResource(resourceName + ".class");
/*  519:     */           }
/*  520:1072 */           if (url == null) {
/*  521:1073 */             logDiagnostic("Class '" + logAdapterClassName + "' [" + resourceName + "] cannot be found.");
/*  522:     */           } else {
/*  523:1075 */             logDiagnostic("Class '" + logAdapterClassName + "' was found at '" + url + "'");
/*  524:     */           }
/*  525:     */         }
/*  526:1079 */         Class c = null;
/*  527:     */         try
/*  528:     */         {
/*  529:1081 */           c = Class.forName(logAdapterClassName, true, currentCL);
/*  530:     */         }
/*  531:     */         catch (ClassNotFoundException originalClassNotFoundException)
/*  532:     */         {
/*  533:1086 */           String msg = "" + originalClassNotFoundException.getMessage();
/*  534:1087 */           logDiagnostic("The log adapter '" + logAdapterClassName + "' is not available via classloader " + LogFactory.objectId(currentCL) + ": " + msg.trim());
/*  535:     */           try
/*  536:     */           {
/*  537:1102 */             c = Class.forName(logAdapterClassName);
/*  538:     */           }
/*  539:     */           catch (ClassNotFoundException secondaryClassNotFoundException)
/*  540:     */           {
/*  541:1105 */             msg = "" + secondaryClassNotFoundException.getMessage();
/*  542:1106 */             logDiagnostic("The log adapter '" + logAdapterClassName + "' is not available via the LogFactoryImpl class classloader: " + msg.trim());
/*  543:     */             
/*  544:     */ 
/*  545:     */ 
/*  546:     */ 
/*  547:1111 */             break;
/*  548:     */           }
/*  549:     */         }
/*  550:1115 */         constructor = c.getConstructor(this.logConstructorSignature);
/*  551:1116 */         Object o = constructor.newInstance(params);
/*  552:1122 */         if ((o instanceof Log))
/*  553:     */         {
/*  554:1123 */           logAdapterClass = c;
/*  555:1124 */           logAdapter = (Log)o;
/*  556:1125 */           break;
/*  557:     */         }
/*  558:1138 */         handleFlawedHierarchy(currentCL, c);
/*  559:     */       }
/*  560:     */       catch (NoClassDefFoundError e)
/*  561:     */       {
/*  562:1145 */         String msg = "" + e.getMessage();
/*  563:1146 */         logDiagnostic("The log adapter '" + logAdapterClassName + "' is missing dependencies when loaded via classloader " + LogFactory.objectId(currentCL) + ": " + msg.trim());
/*  564:     */         
/*  565:     */ 
/*  566:     */ 
/*  567:     */ 
/*  568:     */ 
/*  569:     */ 
/*  570:1153 */         break;
/*  571:     */       }
/*  572:     */       catch (ExceptionInInitializerError e)
/*  573:     */       {
/*  574:1161 */         String msg = "" + e.getMessage();
/*  575:1162 */         logDiagnostic("The log adapter '" + logAdapterClassName + "' is unable to initialize itself when loaded via classloader " + LogFactory.objectId(currentCL) + ": " + msg.trim());
/*  576:     */         
/*  577:     */ 
/*  578:     */ 
/*  579:     */ 
/*  580:     */ 
/*  581:     */ 
/*  582:1169 */         break;
/*  583:     */       }
/*  584:     */       catch (LogConfigurationException e)
/*  585:     */       {
/*  586:1173 */         throw e;
/*  587:     */       }
/*  588:     */       catch (Throwable t)
/*  589:     */       {
/*  590:1178 */         handleFlawedDiscovery(logAdapterClassName, currentCL, t);
/*  591:     */       }
/*  592:1181 */       if (currentCL == null) {
/*  593:     */         break;
/*  594:     */       }
/*  595:1187 */       currentCL = getParentClassLoader(currentCL);
/*  596:     */     }
/*  597:1190 */     if ((logAdapter != null) && (affectState))
/*  598:     */     {
/*  599:1192 */       this.logClassName = logAdapterClassName;
/*  600:1193 */       this.logConstructor = constructor;
/*  601:     */       try
/*  602:     */       {
/*  603:1197 */         this.logMethod = logAdapterClass.getMethod("setLogFactory", this.logMethodSignature);
/*  604:     */         
/*  605:1199 */         logDiagnostic("Found method setLogFactory(LogFactory) in '" + logAdapterClassName + "'");
/*  606:     */       }
/*  607:     */       catch (Throwable t)
/*  608:     */       {
/*  609:1202 */         this.logMethod = null;
/*  610:1203 */         logDiagnostic("[INFO] '" + logAdapterClassName + "' from classloader " + LogFactory.objectId(currentCL) + " does not declare optional method " + "setLogFactory(LogFactory)");
/*  611:     */       }
/*  612:1210 */       logDiagnostic("Log adapter '" + logAdapterClassName + "' from classloader " + LogFactory.objectId(logAdapterClass.getClassLoader()) + " has been selected for use.");
/*  613:     */     }
/*  614:1216 */     return logAdapter;
/*  615:     */   }
/*  616:     */   
/*  617:     */   private ClassLoader getBaseClassLoader()
/*  618:     */     throws LogConfigurationException
/*  619:     */   {
/*  620:1239 */     ClassLoader thisClassLoader = getClassLoader(LogFactoryImpl.class);
/*  621:1241 */     if (!this.useTCCL) {
/*  622:1242 */       return thisClassLoader;
/*  623:     */     }
/*  624:1245 */     ClassLoader contextClassLoader = getContextClassLoaderInternal();
/*  625:     */     
/*  626:1247 */     ClassLoader baseClassLoader = getLowestClassLoader(contextClassLoader, thisClassLoader);
/*  627:1250 */     if (baseClassLoader == null)
/*  628:     */     {
/*  629:1255 */       if (this.allowFlawedContext)
/*  630:     */       {
/*  631:1256 */         if (isDiagnosticsEnabled()) {
/*  632:1257 */           logDiagnostic("[WARNING] the context classloader is not part of a parent-child relationship with the classloader that loaded LogFactoryImpl.");
/*  633:     */         }
/*  634:1265 */         return contextClassLoader;
/*  635:     */       }
/*  636:1268 */       throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
/*  637:     */     }
/*  638:1275 */     if (baseClassLoader != contextClassLoader) {
/*  639:1281 */       if (this.allowFlawedContext)
/*  640:     */       {
/*  641:1282 */         if (isDiagnosticsEnabled()) {
/*  642:1283 */           logDiagnostic("Warning: the context classloader is an ancestor of the classloader that loaded LogFactoryImpl; it should be the same or a descendant. The application using commons-logging should ensure the context classloader is used correctly.");
/*  643:     */         }
/*  644:     */       }
/*  645:     */       else {
/*  646:1291 */         throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
/*  647:     */       }
/*  648:     */     }
/*  649:1298 */     return baseClassLoader;
/*  650:     */   }
/*  651:     */   
/*  652:     */   private ClassLoader getLowestClassLoader(ClassLoader c1, ClassLoader c2)
/*  653:     */   {
/*  654:1314 */     if (c1 == null) {
/*  655:1315 */       return c2;
/*  656:     */     }
/*  657:1317 */     if (c2 == null) {
/*  658:1318 */       return c1;
/*  659:     */     }
/*  660:1323 */     ClassLoader current = c1;
/*  661:1324 */     while (current != null)
/*  662:     */     {
/*  663:1325 */       if (current == c2) {
/*  664:1326 */         return c1;
/*  665:     */       }
/*  666:1327 */       current = current.getParent();
/*  667:     */     }
/*  668:1331 */     current = c2;
/*  669:1332 */     while (current != null)
/*  670:     */     {
/*  671:1333 */       if (current == c1) {
/*  672:1334 */         return c2;
/*  673:     */       }
/*  674:1335 */       current = current.getParent();
/*  675:     */     }
/*  676:1338 */     return null;
/*  677:     */   }
/*  678:     */   
/*  679:     */   private void handleFlawedDiscovery(String logAdapterClassName, ClassLoader classLoader, Throwable discoveryFlaw)
/*  680:     */   {
/*  681:1360 */     if (isDiagnosticsEnabled())
/*  682:     */     {
/*  683:1361 */       logDiagnostic("Could not instantiate Log '" + logAdapterClassName + "' -- " + discoveryFlaw.getClass().getName() + ": " + discoveryFlaw.getLocalizedMessage());
/*  684:1366 */       if ((discoveryFlaw instanceof InvocationTargetException))
/*  685:     */       {
/*  686:1370 */         InvocationTargetException ite = (InvocationTargetException)discoveryFlaw;
/*  687:1371 */         Throwable cause = ite.getTargetException();
/*  688:1372 */         if (cause != null)
/*  689:     */         {
/*  690:1373 */           logDiagnostic("... InvocationTargetException: " + cause.getClass().getName() + ": " + cause.getLocalizedMessage());
/*  691:1377 */           if ((cause instanceof ExceptionInInitializerError))
/*  692:     */           {
/*  693:1378 */             ExceptionInInitializerError eiie = (ExceptionInInitializerError)cause;
/*  694:1379 */             Throwable cause2 = eiie.getException();
/*  695:1380 */             if (cause2 != null) {
/*  696:1381 */               logDiagnostic("... ExceptionInInitializerError: " + cause2.getClass().getName() + ": " + cause2.getLocalizedMessage());
/*  697:     */             }
/*  698:     */           }
/*  699:     */         }
/*  700:     */       }
/*  701:     */     }
/*  702:1390 */     if (!this.allowFlawedDiscovery) {
/*  703:1391 */       throw new LogConfigurationException(discoveryFlaw);
/*  704:     */     }
/*  705:     */   }
/*  706:     */   
/*  707:     */   private void handleFlawedHierarchy(ClassLoader badClassLoader, Class badClass)
/*  708:     */     throws LogConfigurationException
/*  709:     */   {
/*  710:1425 */     boolean implementsLog = false;
/*  711:1426 */     String logInterfaceName = Log.class.getName();
/*  712:1427 */     Class[] interfaces = badClass.getInterfaces();
/*  713:1428 */     for (int i = 0; i < interfaces.length; i++) {
/*  714:1429 */       if (logInterfaceName.equals(interfaces[i].getName()))
/*  715:     */       {
/*  716:1430 */         implementsLog = true;
/*  717:1431 */         break;
/*  718:     */       }
/*  719:     */     }
/*  720:1435 */     if (implementsLog)
/*  721:     */     {
/*  722:1438 */       if (isDiagnosticsEnabled()) {
/*  723:     */         try
/*  724:     */         {
/*  725:1440 */           ClassLoader logInterfaceClassLoader = getClassLoader(Log.class);
/*  726:1441 */           logDiagnostic("Class '" + badClass.getName() + "' was found in classloader " + LogFactory.objectId(badClassLoader) + ". It is bound to a Log interface which is not" + " the one loaded from classloader " + LogFactory.objectId(logInterfaceClassLoader));
/*  727:     */         }
/*  728:     */         catch (Throwable t)
/*  729:     */         {
/*  730:1449 */           logDiagnostic("Error while trying to output diagnostics about bad class '" + badClass + "'");
/*  731:     */         }
/*  732:     */       }
/*  733:1455 */       if (!this.allowFlawedHierarchy)
/*  734:     */       {
/*  735:1456 */         StringBuffer msg = new StringBuffer();
/*  736:1457 */         msg.append("Terminating logging for this context ");
/*  737:1458 */         msg.append("due to bad log hierarchy. ");
/*  738:1459 */         msg.append("You have more than one version of '");
/*  739:1460 */         msg.append(Log.class.getName());
/*  740:1461 */         msg.append("' visible.");
/*  741:1462 */         if (isDiagnosticsEnabled()) {
/*  742:1463 */           logDiagnostic(msg.toString());
/*  743:     */         }
/*  744:1465 */         throw new LogConfigurationException(msg.toString());
/*  745:     */       }
/*  746:1468 */       if (isDiagnosticsEnabled())
/*  747:     */       {
/*  748:1469 */         StringBuffer msg = new StringBuffer();
/*  749:1470 */         msg.append("Warning: bad log hierarchy. ");
/*  750:1471 */         msg.append("You have more than one version of '");
/*  751:1472 */         msg.append(Log.class.getName());
/*  752:1473 */         msg.append("' visible.");
/*  753:1474 */         logDiagnostic(msg.toString());
/*  754:     */       }
/*  755:     */     }
/*  756:     */     else
/*  757:     */     {
/*  758:1478 */       if (!this.allowFlawedDiscovery)
/*  759:     */       {
/*  760:1479 */         StringBuffer msg = new StringBuffer();
/*  761:1480 */         msg.append("Terminating logging for this context. ");
/*  762:1481 */         msg.append("Log class '");
/*  763:1482 */         msg.append(badClass.getName());
/*  764:1483 */         msg.append("' does not implement the Log interface.");
/*  765:1484 */         if (isDiagnosticsEnabled()) {
/*  766:1485 */           logDiagnostic(msg.toString());
/*  767:     */         }
/*  768:1488 */         throw new LogConfigurationException(msg.toString());
/*  769:     */       }
/*  770:1491 */       if (isDiagnosticsEnabled())
/*  771:     */       {
/*  772:1492 */         StringBuffer msg = new StringBuffer();
/*  773:1493 */         msg.append("[WARNING] Log class '");
/*  774:1494 */         msg.append(badClass.getName());
/*  775:1495 */         msg.append("' does not implement the Log interface.");
/*  776:1496 */         logDiagnostic(msg.toString());
/*  777:     */       }
/*  778:     */     }
/*  779:     */   }
/*  780:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.LogFactoryImpl
 * JD-Core Version:    0.7.0.1
 */