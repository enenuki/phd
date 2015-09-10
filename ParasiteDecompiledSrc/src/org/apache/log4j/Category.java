/*    1:     */ package org.apache.log4j;
/*    2:     */ 
/*    3:     */ import java.text.MessageFormat;
/*    4:     */ import java.util.Enumeration;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ import java.util.Vector;
/*    8:     */ import org.apache.log4j.helpers.AppenderAttachableImpl;
/*    9:     */ import org.apache.log4j.helpers.NullEnumeration;
/*   10:     */ import org.apache.log4j.spi.AppenderAttachable;
/*   11:     */ import org.apache.log4j.spi.HierarchyEventListener;
/*   12:     */ import org.apache.log4j.spi.LoggerRepository;
/*   13:     */ import org.apache.log4j.spi.LoggingEvent;
/*   14:     */ 
/*   15:     */ public class Category
/*   16:     */   implements AppenderAttachable
/*   17:     */ {
/*   18:     */   protected String name;
/*   19:     */   protected volatile Level level;
/*   20:     */   protected volatile Category parent;
/*   21: 118 */   private static final String FQCN = Category.class.getName();
/*   22:     */   protected ResourceBundle resourceBundle;
/*   23:     */   protected LoggerRepository repository;
/*   24:     */   AppenderAttachableImpl aai;
/*   25: 135 */   protected boolean additive = true;
/*   26:     */   
/*   27:     */   protected Category(String name)
/*   28:     */   {
/*   29: 148 */     this.name = name;
/*   30:     */   }
/*   31:     */   
/*   32:     */   public synchronized void addAppender(Appender newAppender)
/*   33:     */   {
/*   34: 161 */     if (this.aai == null) {
/*   35: 162 */       this.aai = new AppenderAttachableImpl();
/*   36:     */     }
/*   37: 164 */     this.aai.addAppender(newAppender);
/*   38: 165 */     this.repository.fireAddAppenderEvent(this, newAppender);
/*   39:     */   }
/*   40:     */   
/*   41:     */   public void assertLog(boolean assertion, String msg)
/*   42:     */   {
/*   43: 183 */     if (!assertion) {
/*   44: 184 */       error(msg);
/*   45:     */     }
/*   46:     */   }
/*   47:     */   
/*   48:     */   public void callAppenders(LoggingEvent event)
/*   49:     */   {
/*   50: 200 */     int writes = 0;
/*   51: 202 */     for (Category c = this; c != null; c = c.parent) {
/*   52: 204 */       synchronized (c)
/*   53:     */       {
/*   54: 205 */         if (c.aai != null) {
/*   55: 206 */           writes += c.aai.appendLoopOnAppenders(event);
/*   56:     */         }
/*   57: 208 */         if (!c.additive) {
/*   58:     */           break;
/*   59:     */         }
/*   60:     */       }
/*   61:     */     }
/*   62: 214 */     if (writes == 0) {
/*   63: 215 */       this.repository.emitNoAppenderWarning(this);
/*   64:     */     }
/*   65:     */   }
/*   66:     */   
/*   67:     */   synchronized void closeNestedAppenders()
/*   68:     */   {
/*   69: 226 */     Enumeration enumeration = getAllAppenders();
/*   70: 227 */     if (enumeration != null) {
/*   71: 228 */       while (enumeration.hasMoreElements())
/*   72:     */       {
/*   73: 229 */         Appender a = (Appender)enumeration.nextElement();
/*   74: 230 */         if ((a instanceof AppenderAttachable)) {
/*   75: 231 */           a.close();
/*   76:     */         }
/*   77:     */       }
/*   78:     */     }
/*   79:     */   }
/*   80:     */   
/*   81:     */   public void debug(Object message)
/*   82:     */   {
/*   83: 257 */     if (this.repository.isDisabled(10000)) {
/*   84: 258 */       return;
/*   85:     */     }
/*   86: 259 */     if (Level.DEBUG.isGreaterOrEqual(getEffectiveLevel())) {
/*   87: 260 */       forcedLog(FQCN, Level.DEBUG, message, null);
/*   88:     */     }
/*   89:     */   }
/*   90:     */   
/*   91:     */   public void debug(Object message, Throwable t)
/*   92:     */   {
/*   93: 276 */     if (this.repository.isDisabled(10000)) {
/*   94: 277 */       return;
/*   95:     */     }
/*   96: 278 */     if (Level.DEBUG.isGreaterOrEqual(getEffectiveLevel())) {
/*   97: 279 */       forcedLog(FQCN, Level.DEBUG, message, t);
/*   98:     */     }
/*   99:     */   }
/*  100:     */   
/*  101:     */   public void error(Object message)
/*  102:     */   {
/*  103: 302 */     if (this.repository.isDisabled(40000)) {
/*  104: 303 */       return;
/*  105:     */     }
/*  106: 304 */     if (Level.ERROR.isGreaterOrEqual(getEffectiveLevel())) {
/*  107: 305 */       forcedLog(FQCN, Level.ERROR, message, null);
/*  108:     */     }
/*  109:     */   }
/*  110:     */   
/*  111:     */   public void error(Object message, Throwable t)
/*  112:     */   {
/*  113: 319 */     if (this.repository.isDisabled(40000)) {
/*  114: 320 */       return;
/*  115:     */     }
/*  116: 321 */     if (Level.ERROR.isGreaterOrEqual(getEffectiveLevel())) {
/*  117: 322 */       forcedLog(FQCN, Level.ERROR, message, t);
/*  118:     */     }
/*  119:     */   }
/*  120:     */   
/*  121:     */   /**
/*  122:     */    * @deprecated
/*  123:     */    */
/*  124:     */   public static Logger exists(String name)
/*  125:     */   {
/*  126: 338 */     return LogManager.exists(name);
/*  127:     */   }
/*  128:     */   
/*  129:     */   public void fatal(Object message)
/*  130:     */   {
/*  131: 362 */     if (this.repository.isDisabled(50000)) {
/*  132: 363 */       return;
/*  133:     */     }
/*  134: 364 */     if (Level.FATAL.isGreaterOrEqual(getEffectiveLevel())) {
/*  135: 365 */       forcedLog(FQCN, Level.FATAL, message, null);
/*  136:     */     }
/*  137:     */   }
/*  138:     */   
/*  139:     */   public void fatal(Object message, Throwable t)
/*  140:     */   {
/*  141: 379 */     if (this.repository.isDisabled(50000)) {
/*  142: 380 */       return;
/*  143:     */     }
/*  144: 381 */     if (Level.FATAL.isGreaterOrEqual(getEffectiveLevel())) {
/*  145: 382 */       forcedLog(FQCN, Level.FATAL, message, t);
/*  146:     */     }
/*  147:     */   }
/*  148:     */   
/*  149:     */   protected void forcedLog(String fqcn, Priority level, Object message, Throwable t)
/*  150:     */   {
/*  151: 391 */     callAppenders(new LoggingEvent(fqcn, this, level, message, t));
/*  152:     */   }
/*  153:     */   
/*  154:     */   public boolean getAdditivity()
/*  155:     */   {
/*  156: 400 */     return this.additive;
/*  157:     */   }
/*  158:     */   
/*  159:     */   public synchronized Enumeration getAllAppenders()
/*  160:     */   {
/*  161: 412 */     if (this.aai == null) {
/*  162: 413 */       return NullEnumeration.getInstance();
/*  163:     */     }
/*  164: 415 */     return this.aai.getAllAppenders();
/*  165:     */   }
/*  166:     */   
/*  167:     */   public synchronized Appender getAppender(String name)
/*  168:     */   {
/*  169: 426 */     if ((this.aai == null) || (name == null)) {
/*  170: 427 */       return null;
/*  171:     */     }
/*  172: 429 */     return this.aai.getAppender(name);
/*  173:     */   }
/*  174:     */   
/*  175:     */   public Level getEffectiveLevel()
/*  176:     */   {
/*  177: 442 */     for (Category c = this; c != null; c = c.parent) {
/*  178: 443 */       if (c.level != null) {
/*  179: 444 */         return c.level;
/*  180:     */       }
/*  181:     */     }
/*  182: 446 */     return null;
/*  183:     */   }
/*  184:     */   
/*  185:     */   /**
/*  186:     */    * @deprecated
/*  187:     */    */
/*  188:     */   public Priority getChainedPriority()
/*  189:     */   {
/*  190: 456 */     for (Category c = this; c != null; c = c.parent) {
/*  191: 457 */       if (c.level != null) {
/*  192: 458 */         return c.level;
/*  193:     */       }
/*  194:     */     }
/*  195: 460 */     return null;
/*  196:     */   }
/*  197:     */   
/*  198:     */   /**
/*  199:     */    * @deprecated
/*  200:     */    */
/*  201:     */   public static Enumeration getCurrentCategories()
/*  202:     */   {
/*  203: 476 */     return LogManager.getCurrentLoggers();
/*  204:     */   }
/*  205:     */   
/*  206:     */   /**
/*  207:     */    * @deprecated
/*  208:     */    */
/*  209:     */   public static LoggerRepository getDefaultHierarchy()
/*  210:     */   {
/*  211: 490 */     return LogManager.getLoggerRepository();
/*  212:     */   }
/*  213:     */   
/*  214:     */   /**
/*  215:     */    * @deprecated
/*  216:     */    */
/*  217:     */   public LoggerRepository getHierarchy()
/*  218:     */   {
/*  219: 502 */     return this.repository;
/*  220:     */   }
/*  221:     */   
/*  222:     */   public LoggerRepository getLoggerRepository()
/*  223:     */   {
/*  224: 512 */     return this.repository;
/*  225:     */   }
/*  226:     */   
/*  227:     */   /**
/*  228:     */    * @deprecated
/*  229:     */    */
/*  230:     */   public static Category getInstance(String name)
/*  231:     */   {
/*  232: 522 */     return LogManager.getLogger(name);
/*  233:     */   }
/*  234:     */   
/*  235:     */   /**
/*  236:     */    * @deprecated
/*  237:     */    */
/*  238:     */   public static Category getInstance(Class clazz)
/*  239:     */   {
/*  240: 531 */     return LogManager.getLogger(clazz);
/*  241:     */   }
/*  242:     */   
/*  243:     */   public final String getName()
/*  244:     */   {
/*  245: 540 */     return this.name;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public final Category getParent()
/*  249:     */   {
/*  250: 555 */     return this.parent;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public final Level getLevel()
/*  254:     */   {
/*  255: 567 */     return this.level;
/*  256:     */   }
/*  257:     */   
/*  258:     */   /**
/*  259:     */    * @deprecated
/*  260:     */    */
/*  261:     */   public final Level getPriority()
/*  262:     */   {
/*  263: 576 */     return this.level;
/*  264:     */   }
/*  265:     */   
/*  266:     */   /**
/*  267:     */    * @deprecated
/*  268:     */    */
/*  269:     */   public static final Category getRoot()
/*  270:     */   {
/*  271: 587 */     return LogManager.getRootLogger();
/*  272:     */   }
/*  273:     */   
/*  274:     */   public ResourceBundle getResourceBundle()
/*  275:     */   {
/*  276: 603 */     for (Category c = this; c != null; c = c.parent) {
/*  277: 604 */       if (c.resourceBundle != null) {
/*  278: 605 */         return c.resourceBundle;
/*  279:     */       }
/*  280:     */     }
/*  281: 608 */     return null;
/*  282:     */   }
/*  283:     */   
/*  284:     */   protected String getResourceBundleString(String key)
/*  285:     */   {
/*  286: 621 */     ResourceBundle rb = getResourceBundle();
/*  287: 624 */     if (rb == null) {
/*  288: 629 */       return null;
/*  289:     */     }
/*  290:     */     try
/*  291:     */     {
/*  292: 633 */       return rb.getString(key);
/*  293:     */     }
/*  294:     */     catch (MissingResourceException mre)
/*  295:     */     {
/*  296: 636 */       error("No resource is associated with key \"" + key + "\".");
/*  297:     */     }
/*  298: 637 */     return null;
/*  299:     */   }
/*  300:     */   
/*  301:     */   public void info(Object message)
/*  302:     */   {
/*  303: 663 */     if (this.repository.isDisabled(20000)) {
/*  304: 664 */       return;
/*  305:     */     }
/*  306: 665 */     if (Level.INFO.isGreaterOrEqual(getEffectiveLevel())) {
/*  307: 666 */       forcedLog(FQCN, Level.INFO, message, null);
/*  308:     */     }
/*  309:     */   }
/*  310:     */   
/*  311:     */   public void info(Object message, Throwable t)
/*  312:     */   {
/*  313: 680 */     if (this.repository.isDisabled(20000)) {
/*  314: 681 */       return;
/*  315:     */     }
/*  316: 682 */     if (Level.INFO.isGreaterOrEqual(getEffectiveLevel())) {
/*  317: 683 */       forcedLog(FQCN, Level.INFO, message, t);
/*  318:     */     }
/*  319:     */   }
/*  320:     */   
/*  321:     */   public boolean isAttached(Appender appender)
/*  322:     */   {
/*  323: 691 */     if ((appender == null) || (this.aai == null)) {
/*  324: 692 */       return false;
/*  325:     */     }
/*  326: 694 */     return this.aai.isAttached(appender);
/*  327:     */   }
/*  328:     */   
/*  329:     */   public boolean isDebugEnabled()
/*  330:     */   {
/*  331: 734 */     if (this.repository.isDisabled(10000)) {
/*  332: 735 */       return false;
/*  333:     */     }
/*  334: 736 */     return Level.DEBUG.isGreaterOrEqual(getEffectiveLevel());
/*  335:     */   }
/*  336:     */   
/*  337:     */   public boolean isEnabledFor(Priority level)
/*  338:     */   {
/*  339: 749 */     if (this.repository.isDisabled(level.level)) {
/*  340: 750 */       return false;
/*  341:     */     }
/*  342: 751 */     return level.isGreaterOrEqual(getEffectiveLevel());
/*  343:     */   }
/*  344:     */   
/*  345:     */   public boolean isInfoEnabled()
/*  346:     */   {
/*  347: 763 */     if (this.repository.isDisabled(20000)) {
/*  348: 764 */       return false;
/*  349:     */     }
/*  350: 765 */     return Level.INFO.isGreaterOrEqual(getEffectiveLevel());
/*  351:     */   }
/*  352:     */   
/*  353:     */   public void l7dlog(Priority priority, String key, Throwable t)
/*  354:     */   {
/*  355: 779 */     if (this.repository.isDisabled(priority.level)) {
/*  356: 780 */       return;
/*  357:     */     }
/*  358: 782 */     if (priority.isGreaterOrEqual(getEffectiveLevel()))
/*  359:     */     {
/*  360: 783 */       String msg = getResourceBundleString(key);
/*  361: 786 */       if (msg == null) {
/*  362: 787 */         msg = key;
/*  363:     */       }
/*  364: 789 */       forcedLog(FQCN, priority, msg, t);
/*  365:     */     }
/*  366:     */   }
/*  367:     */   
/*  368:     */   public void l7dlog(Priority priority, String key, Object[] params, Throwable t)
/*  369:     */   {
/*  370: 803 */     if (this.repository.isDisabled(priority.level)) {
/*  371: 804 */       return;
/*  372:     */     }
/*  373: 806 */     if (priority.isGreaterOrEqual(getEffectiveLevel()))
/*  374:     */     {
/*  375: 807 */       String pattern = getResourceBundleString(key);
/*  376:     */       String msg;
/*  377:     */       String msg;
/*  378: 809 */       if (pattern == null) {
/*  379: 810 */         msg = key;
/*  380:     */       } else {
/*  381: 812 */         msg = MessageFormat.format(pattern, params);
/*  382:     */       }
/*  383: 813 */       forcedLog(FQCN, priority, msg, t);
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   public void log(Priority priority, Object message, Throwable t)
/*  388:     */   {
/*  389: 822 */     if (this.repository.isDisabled(priority.level)) {
/*  390: 823 */       return;
/*  391:     */     }
/*  392: 825 */     if (priority.isGreaterOrEqual(getEffectiveLevel())) {
/*  393: 826 */       forcedLog(FQCN, priority, message, t);
/*  394:     */     }
/*  395:     */   }
/*  396:     */   
/*  397:     */   public void log(Priority priority, Object message)
/*  398:     */   {
/*  399: 834 */     if (this.repository.isDisabled(priority.level)) {
/*  400: 835 */       return;
/*  401:     */     }
/*  402: 837 */     if (priority.isGreaterOrEqual(getEffectiveLevel())) {
/*  403: 838 */       forcedLog(FQCN, priority, message, null);
/*  404:     */     }
/*  405:     */   }
/*  406:     */   
/*  407:     */   public void log(String callerFQCN, Priority level, Object message, Throwable t)
/*  408:     */   {
/*  409: 852 */     if (this.repository.isDisabled(level.level)) {
/*  410: 853 */       return;
/*  411:     */     }
/*  412: 855 */     if (level.isGreaterOrEqual(getEffectiveLevel())) {
/*  413: 856 */       forcedLog(callerFQCN, level, message, t);
/*  414:     */     }
/*  415:     */   }
/*  416:     */   
/*  417:     */   private void fireRemoveAppenderEvent(Appender appender)
/*  418:     */   {
/*  419: 868 */     if (appender != null) {
/*  420: 869 */       if ((this.repository instanceof Hierarchy)) {
/*  421: 870 */         ((Hierarchy)this.repository).fireRemoveAppenderEvent(this, appender);
/*  422: 871 */       } else if ((this.repository instanceof HierarchyEventListener)) {
/*  423: 872 */         ((HierarchyEventListener)this.repository).removeAppenderEvent(this, appender);
/*  424:     */       }
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   public synchronized void removeAllAppenders()
/*  429:     */   {
/*  430: 886 */     if (this.aai != null)
/*  431:     */     {
/*  432: 887 */       Vector appenders = new Vector();
/*  433: 888 */       for (Enumeration iter = this.aai.getAllAppenders(); (iter != null) && (iter.hasMoreElements());) {
/*  434: 889 */         appenders.add(iter.nextElement());
/*  435:     */       }
/*  436: 891 */       this.aai.removeAllAppenders();
/*  437: 892 */       for (Enumeration iter = appenders.elements(); iter.hasMoreElements();) {
/*  438: 893 */         fireRemoveAppenderEvent((Appender)iter.nextElement());
/*  439:     */       }
/*  440: 895 */       this.aai = null;
/*  441:     */     }
/*  442:     */   }
/*  443:     */   
/*  444:     */   public synchronized void removeAppender(Appender appender)
/*  445:     */   {
/*  446: 908 */     if ((appender == null) || (this.aai == null)) {
/*  447: 909 */       return;
/*  448:     */     }
/*  449: 910 */     boolean wasAttached = this.aai.isAttached(appender);
/*  450: 911 */     this.aai.removeAppender(appender);
/*  451: 912 */     if (wasAttached) {
/*  452: 913 */       fireRemoveAppenderEvent(appender);
/*  453:     */     }
/*  454:     */   }
/*  455:     */   
/*  456:     */   public synchronized void removeAppender(String name)
/*  457:     */   {
/*  458: 925 */     if ((name == null) || (this.aai == null)) {
/*  459: 925 */       return;
/*  460:     */     }
/*  461: 926 */     Appender appender = this.aai.getAppender(name);
/*  462: 927 */     this.aai.removeAppender(name);
/*  463: 928 */     if (appender != null) {
/*  464: 929 */       fireRemoveAppenderEvent(appender);
/*  465:     */     }
/*  466:     */   }
/*  467:     */   
/*  468:     */   public void setAdditivity(boolean additive)
/*  469:     */   {
/*  470: 939 */     this.additive = additive;
/*  471:     */   }
/*  472:     */   
/*  473:     */   final void setHierarchy(LoggerRepository repository)
/*  474:     */   {
/*  475: 947 */     this.repository = repository;
/*  476:     */   }
/*  477:     */   
/*  478:     */   public void setLevel(Level level)
/*  479:     */   {
/*  480: 963 */     this.level = level;
/*  481:     */   }
/*  482:     */   
/*  483:     */   /**
/*  484:     */    * @deprecated
/*  485:     */    */
/*  486:     */   public void setPriority(Priority priority)
/*  487:     */   {
/*  488: 976 */     this.level = ((Level)priority);
/*  489:     */   }
/*  490:     */   
/*  491:     */   public void setResourceBundle(ResourceBundle bundle)
/*  492:     */   {
/*  493: 989 */     this.resourceBundle = bundle;
/*  494:     */   }
/*  495:     */   
/*  496:     */   /**
/*  497:     */    * @deprecated
/*  498:     */    */
/*  499:     */   public static void shutdown() {}
/*  500:     */   
/*  501:     */   public void warn(Object message)
/*  502:     */   {
/*  503:1039 */     if (this.repository.isDisabled(30000)) {
/*  504:1040 */       return;
/*  505:     */     }
/*  506:1042 */     if (Level.WARN.isGreaterOrEqual(getEffectiveLevel())) {
/*  507:1043 */       forcedLog(FQCN, Level.WARN, message, null);
/*  508:     */     }
/*  509:     */   }
/*  510:     */   
/*  511:     */   public void warn(Object message, Throwable t)
/*  512:     */   {
/*  513:1057 */     if (this.repository.isDisabled(30000)) {
/*  514:1058 */       return;
/*  515:     */     }
/*  516:1059 */     if (Level.WARN.isGreaterOrEqual(getEffectiveLevel())) {
/*  517:1060 */       forcedLog(FQCN, Level.WARN, message, t);
/*  518:     */     }
/*  519:     */   }
/*  520:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.Category
 * JD-Core Version:    0.7.0.1
 */