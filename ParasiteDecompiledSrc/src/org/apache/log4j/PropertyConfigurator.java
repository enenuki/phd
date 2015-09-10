/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.FileInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InterruptedIOException;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.net.URLConnection;
/*   9:    */ import java.util.Enumeration;
/*  10:    */ import java.util.Hashtable;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import java.util.Properties;
/*  14:    */ import java.util.Set;
/*  15:    */ import java.util.StringTokenizer;
/*  16:    */ import java.util.Vector;
/*  17:    */ import org.apache.log4j.config.PropertySetter;
/*  18:    */ import org.apache.log4j.helpers.FileWatchdog;
/*  19:    */ import org.apache.log4j.helpers.LogLog;
/*  20:    */ import org.apache.log4j.helpers.OptionConverter;
/*  21:    */ import org.apache.log4j.or.RendererMap;
/*  22:    */ import org.apache.log4j.spi.Configurator;
/*  23:    */ import org.apache.log4j.spi.ErrorHandler;
/*  24:    */ import org.apache.log4j.spi.Filter;
/*  25:    */ import org.apache.log4j.spi.LoggerFactory;
/*  26:    */ import org.apache.log4j.spi.LoggerRepository;
/*  27:    */ import org.apache.log4j.spi.OptionHandler;
/*  28:    */ import org.apache.log4j.spi.RendererSupport;
/*  29:    */ import org.apache.log4j.spi.ThrowableRenderer;
/*  30:    */ import org.apache.log4j.spi.ThrowableRendererSupport;
/*  31:    */ 
/*  32:    */ public class PropertyConfigurator
/*  33:    */   implements Configurator
/*  34:    */ {
/*  35: 98 */   protected Hashtable registry = new Hashtable(11);
/*  36:    */   private LoggerRepository repository;
/*  37:100 */   protected LoggerFactory loggerFactory = new DefaultCategoryFactory();
/*  38:    */   static final String CATEGORY_PREFIX = "log4j.category.";
/*  39:    */   static final String LOGGER_PREFIX = "log4j.logger.";
/*  40:    */   static final String FACTORY_PREFIX = "log4j.factory";
/*  41:    */   static final String ADDITIVITY_PREFIX = "log4j.additivity.";
/*  42:    */   static final String ROOT_CATEGORY_PREFIX = "log4j.rootCategory";
/*  43:    */   static final String ROOT_LOGGER_PREFIX = "log4j.rootLogger";
/*  44:    */   static final String APPENDER_PREFIX = "log4j.appender.";
/*  45:    */   static final String RENDERER_PREFIX = "log4j.renderer.";
/*  46:    */   static final String THRESHOLD_PREFIX = "log4j.threshold";
/*  47:    */   private static final String THROWABLE_RENDERER_PREFIX = "log4j.throwableRenderer";
/*  48:    */   private static final String LOGGER_REF = "logger-ref";
/*  49:    */   private static final String ROOT_REF = "root-ref";
/*  50:    */   private static final String APPENDER_REF_TAG = "appender-ref";
/*  51:    */   public static final String LOGGER_FACTORY_KEY = "log4j.loggerFactory";
/*  52:    */   private static final String RESET_KEY = "log4j.reset";
/*  53:    */   private static final String INTERNAL_ROOT_NAME = "root";
/*  54:    */   
/*  55:    */   public void doConfigure(String configFileName, LoggerRepository hierarchy)
/*  56:    */   {
/*  57:369 */     Properties props = new Properties();
/*  58:370 */     FileInputStream istream = null;
/*  59:    */     try
/*  60:    */     {
/*  61:372 */       istream = new FileInputStream(configFileName);
/*  62:373 */       props.load(istream);
/*  63:374 */       istream.close();
/*  64:384 */       if (istream != null) {
/*  65:    */         try
/*  66:    */         {
/*  67:386 */           istream.close();
/*  68:    */         }
/*  69:    */         catch (InterruptedIOException ignore)
/*  70:    */         {
/*  71:388 */           Thread.currentThread().interrupt();
/*  72:    */         }
/*  73:    */         catch (Throwable ignore) {}
/*  74:    */       }
/*  75:395 */       doConfigure(props, hierarchy);
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79:377 */       if (((e instanceof InterruptedIOException)) || ((e instanceof InterruptedException))) {
/*  80:378 */         Thread.currentThread().interrupt();
/*  81:    */       }
/*  82:380 */       LogLog.error("Could not read configuration file [" + configFileName + "].", e);
/*  83:381 */       LogLog.error("Ignoring configuration file [" + configFileName + "].");
/*  84:    */     }
/*  85:    */     finally
/*  86:    */     {
/*  87:384 */       if (istream != null) {
/*  88:    */         try
/*  89:    */         {
/*  90:386 */           istream.close();
/*  91:    */         }
/*  92:    */         catch (InterruptedIOException ignore)
/*  93:    */         {
/*  94:388 */           Thread.currentThread().interrupt();
/*  95:    */         }
/*  96:    */         catch (Throwable ignore) {}
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void configure(String configFilename)
/* 102:    */   {
/* 103:403 */     new PropertyConfigurator().doConfigure(configFilename, LogManager.getLoggerRepository());
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static void configure(URL configURL)
/* 107:    */   {
/* 108:415 */     new PropertyConfigurator().doConfigure(configURL, LogManager.getLoggerRepository());
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static void configure(Properties properties)
/* 112:    */   {
/* 113:428 */     new PropertyConfigurator().doConfigure(properties, LogManager.getLoggerRepository());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static void configureAndWatch(String configFilename)
/* 117:    */   {
/* 118:443 */     configureAndWatch(configFilename, 60000L);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static void configureAndWatch(String configFilename, long delay)
/* 122:    */   {
/* 123:461 */     PropertyWatchdog pdog = new PropertyWatchdog(configFilename);
/* 124:462 */     pdog.setDelay(delay);
/* 125:463 */     pdog.start();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void doConfigure(Properties properties, LoggerRepository hierarchy)
/* 129:    */   {
/* 130:474 */     this.repository = hierarchy;
/* 131:475 */     String value = properties.getProperty("log4j.debug");
/* 132:476 */     if (value == null)
/* 133:    */     {
/* 134:477 */       value = properties.getProperty("log4j.configDebug");
/* 135:478 */       if (value != null) {
/* 136:479 */         LogLog.warn("[log4j.configDebug] is deprecated. Use [log4j.debug] instead.");
/* 137:    */       }
/* 138:    */     }
/* 139:482 */     if (value != null) {
/* 140:483 */       LogLog.setInternalDebugging(OptionConverter.toBoolean(value, true));
/* 141:    */     }
/* 142:489 */     String reset = properties.getProperty("log4j.reset");
/* 143:490 */     if ((reset != null) && (OptionConverter.toBoolean(reset, false))) {
/* 144:491 */       hierarchy.resetConfiguration();
/* 145:    */     }
/* 146:494 */     String thresholdStr = OptionConverter.findAndSubst("log4j.threshold", properties);
/* 147:496 */     if (thresholdStr != null)
/* 148:    */     {
/* 149:497 */       hierarchy.setThreshold(OptionConverter.toLevel(thresholdStr, Level.ALL));
/* 150:    */       
/* 151:499 */       LogLog.debug("Hierarchy threshold set to [" + hierarchy.getThreshold() + "].");
/* 152:    */     }
/* 153:502 */     configureRootCategory(properties, hierarchy);
/* 154:503 */     configureLoggerFactory(properties);
/* 155:504 */     parseCatsAndRenderers(properties, hierarchy);
/* 156:    */     
/* 157:506 */     LogLog.debug("Finished configuring.");
/* 158:    */     
/* 159:    */ 
/* 160:509 */     this.registry.clear();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void doConfigure(URL configURL, LoggerRepository hierarchy)
/* 164:    */   {
/* 165:517 */     Properties props = new Properties();
/* 166:518 */     LogLog.debug("Reading configuration from URL " + configURL);
/* 167:519 */     InputStream istream = null;
/* 168:520 */     URLConnection uConn = null;
/* 169:    */     try
/* 170:    */     {
/* 171:522 */       uConn = configURL.openConnection();
/* 172:523 */       uConn.setUseCaches(false);
/* 173:524 */       istream = uConn.getInputStream();
/* 174:525 */       props.load(istream);
/* 175:537 */       if (istream != null) {
/* 176:    */         try
/* 177:    */         {
/* 178:539 */           istream.close();
/* 179:    */         }
/* 180:    */         catch (InterruptedIOException ignore)
/* 181:    */         {
/* 182:541 */           Thread.currentThread().interrupt();
/* 183:    */         }
/* 184:    */         catch (IOException ignore) {}catch (RuntimeException ignore) {}
/* 185:    */       }
/* 186:547 */       doConfigure(props, hierarchy);
/* 187:    */     }
/* 188:    */     catch (Exception e)
/* 189:    */     {
/* 190:528 */       if (((e instanceof InterruptedIOException)) || ((e instanceof InterruptedException))) {
/* 191:529 */         Thread.currentThread().interrupt();
/* 192:    */       }
/* 193:531 */       LogLog.error("Could not read configuration file from URL [" + configURL + "].", e);
/* 194:    */       
/* 195:533 */       LogLog.error("Ignoring configuration file [" + configURL + "].");
/* 196:    */     }
/* 197:    */     finally
/* 198:    */     {
/* 199:537 */       if (istream != null) {
/* 200:    */         try
/* 201:    */         {
/* 202:539 */           istream.close();
/* 203:    */         }
/* 204:    */         catch (InterruptedIOException ignore)
/* 205:    */         {
/* 206:541 */           Thread.currentThread().interrupt();
/* 207:    */         }
/* 208:    */         catch (IOException ignore) {}catch (RuntimeException ignore) {}
/* 209:    */       }
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   protected void configureLoggerFactory(Properties props)
/* 214:    */   {
/* 215:566 */     String factoryClassName = OptionConverter.findAndSubst("log4j.loggerFactory", props);
/* 216:568 */     if (factoryClassName != null)
/* 217:    */     {
/* 218:569 */       LogLog.debug("Setting category factory to [" + factoryClassName + "].");
/* 219:570 */       this.loggerFactory = ((LoggerFactory)OptionConverter.instantiateByClassName(factoryClassName, LoggerFactory.class, this.loggerFactory));
/* 220:    */       
/* 221:    */ 
/* 222:    */ 
/* 223:574 */       PropertySetter.setProperties(this.loggerFactory, props, "log4j.factory.");
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   void configureRootCategory(Properties props, LoggerRepository hierarchy)
/* 228:    */   {
/* 229:602 */     String effectiveFrefix = "log4j.rootLogger";
/* 230:603 */     String value = OptionConverter.findAndSubst("log4j.rootLogger", props);
/* 231:605 */     if (value == null)
/* 232:    */     {
/* 233:606 */       value = OptionConverter.findAndSubst("log4j.rootCategory", props);
/* 234:607 */       effectiveFrefix = "log4j.rootCategory";
/* 235:    */     }
/* 236:610 */     if (value == null)
/* 237:    */     {
/* 238:611 */       LogLog.debug("Could not find root logger information. Is this OK?");
/* 239:    */     }
/* 240:    */     else
/* 241:    */     {
/* 242:613 */       Logger root = hierarchy.getRootLogger();
/* 243:614 */       synchronized (root)
/* 244:    */       {
/* 245:615 */         parseCategory(props, root, effectiveFrefix, "root", value);
/* 246:    */       }
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   protected void parseCatsAndRenderers(Properties props, LoggerRepository hierarchy)
/* 251:    */   {
/* 252:626 */     Enumeration enumeration = props.propertyNames();
/* 253:627 */     while (enumeration.hasMoreElements())
/* 254:    */     {
/* 255:628 */       String key = (String)enumeration.nextElement();
/* 256:629 */       if ((key.startsWith("log4j.category.")) || (key.startsWith("log4j.logger.")))
/* 257:    */       {
/* 258:630 */         String loggerName = null;
/* 259:631 */         if (key.startsWith("log4j.category.")) {
/* 260:632 */           loggerName = key.substring("log4j.category.".length());
/* 261:633 */         } else if (key.startsWith("log4j.logger.")) {
/* 262:634 */           loggerName = key.substring("log4j.logger.".length());
/* 263:    */         }
/* 264:636 */         String value = OptionConverter.findAndSubst(key, props);
/* 265:637 */         Logger logger = hierarchy.getLogger(loggerName, this.loggerFactory);
/* 266:638 */         synchronized (logger)
/* 267:    */         {
/* 268:639 */           parseCategory(props, logger, key, loggerName, value);
/* 269:640 */           parseAdditivityForLogger(props, logger, loggerName);
/* 270:    */         }
/* 271:    */       }
/* 272:642 */       else if (key.startsWith("log4j.renderer."))
/* 273:    */       {
/* 274:643 */         String renderedClass = key.substring("log4j.renderer.".length());
/* 275:644 */         String renderingClass = OptionConverter.findAndSubst(key, props);
/* 276:645 */         if ((hierarchy instanceof RendererSupport)) {
/* 277:646 */           RendererMap.addRenderer((RendererSupport)hierarchy, renderedClass, renderingClass);
/* 278:    */         }
/* 279:    */       }
/* 280:649 */       else if ((key.equals("log4j.throwableRenderer")) && 
/* 281:650 */         ((hierarchy instanceof ThrowableRendererSupport)))
/* 282:    */       {
/* 283:651 */         ThrowableRenderer tr = (ThrowableRenderer)OptionConverter.instantiateByKey(props, "log4j.throwableRenderer", ThrowableRenderer.class, null);
/* 284:656 */         if (tr == null)
/* 285:    */         {
/* 286:657 */           LogLog.error("Could not instantiate throwableRenderer.");
/* 287:    */         }
/* 288:    */         else
/* 289:    */         {
/* 290:660 */           PropertySetter setter = new PropertySetter(tr);
/* 291:661 */           setter.setProperties(props, "log4j.throwableRenderer.");
/* 292:662 */           ((ThrowableRendererSupport)hierarchy).setThrowableRenderer(tr);
/* 293:    */         }
/* 294:    */       }
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   void parseAdditivityForLogger(Properties props, Logger cat, String loggerName)
/* 299:    */   {
/* 300:675 */     String value = OptionConverter.findAndSubst("log4j.additivity." + loggerName, props);
/* 301:    */     
/* 302:677 */     LogLog.debug("Handling log4j.additivity." + loggerName + "=[" + value + "]");
/* 303:679 */     if ((value != null) && (!value.equals("")))
/* 304:    */     {
/* 305:680 */       boolean additivity = OptionConverter.toBoolean(value, true);
/* 306:681 */       LogLog.debug("Setting additivity for \"" + loggerName + "\" to " + additivity);
/* 307:    */       
/* 308:683 */       cat.setAdditivity(additivity);
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   void parseCategory(Properties props, Logger logger, String optionKey, String loggerName, String value)
/* 313:    */   {
/* 314:693 */     LogLog.debug("Parsing for [" + loggerName + "] with value=[" + value + "].");
/* 315:    */     
/* 316:695 */     StringTokenizer st = new StringTokenizer(value, ",");
/* 317:700 */     if ((!value.startsWith(",")) && (!value.equals("")))
/* 318:    */     {
/* 319:703 */       if (!st.hasMoreTokens()) {
/* 320:704 */         return;
/* 321:    */       }
/* 322:706 */       String levelStr = st.nextToken();
/* 323:707 */       LogLog.debug("Level token is [" + levelStr + "].");
/* 324:712 */       if (("inherited".equalsIgnoreCase(levelStr)) || ("null".equalsIgnoreCase(levelStr)))
/* 325:    */       {
/* 326:714 */         if (loggerName.equals("root")) {
/* 327:715 */           LogLog.warn("The root logger cannot be set to null.");
/* 328:    */         } else {
/* 329:717 */           logger.setLevel(null);
/* 330:    */         }
/* 331:    */       }
/* 332:    */       else {
/* 333:720 */         logger.setLevel(OptionConverter.toLevel(levelStr, Level.DEBUG));
/* 334:    */       }
/* 335:722 */       LogLog.debug("Category " + loggerName + " set to " + logger.getLevel());
/* 336:    */     }
/* 337:726 */     logger.removeAllAppenders();
/* 338:730 */     while (st.hasMoreTokens())
/* 339:    */     {
/* 340:731 */       String appenderName = st.nextToken().trim();
/* 341:732 */       if ((appenderName != null) && (!appenderName.equals(",")))
/* 342:    */       {
/* 343:734 */         LogLog.debug("Parsing appender named \"" + appenderName + "\".");
/* 344:735 */         Appender appender = parseAppender(props, appenderName);
/* 345:736 */         if (appender != null) {
/* 346:737 */           logger.addAppender(appender);
/* 347:    */         }
/* 348:    */       }
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   Appender parseAppender(Properties props, String appenderName)
/* 353:    */   {
/* 354:743 */     Appender appender = registryGet(appenderName);
/* 355:744 */     if (appender != null)
/* 356:    */     {
/* 357:745 */       LogLog.debug("Appender \"" + appenderName + "\" was already parsed.");
/* 358:746 */       return appender;
/* 359:    */     }
/* 360:749 */     String prefix = "log4j.appender." + appenderName;
/* 361:750 */     String layoutPrefix = prefix + ".layout";
/* 362:    */     
/* 363:752 */     appender = (Appender)OptionConverter.instantiateByKey(props, prefix, Appender.class, null);
/* 364:755 */     if (appender == null)
/* 365:    */     {
/* 366:756 */       LogLog.error("Could not instantiate appender named \"" + appenderName + "\".");
/* 367:    */       
/* 368:758 */       return null;
/* 369:    */     }
/* 370:760 */     appender.setName(appenderName);
/* 371:762 */     if ((appender instanceof OptionHandler))
/* 372:    */     {
/* 373:763 */       if (appender.requiresLayout())
/* 374:    */       {
/* 375:764 */         Layout layout = (Layout)OptionConverter.instantiateByKey(props, layoutPrefix, Layout.class, null);
/* 376:768 */         if (layout != null)
/* 377:    */         {
/* 378:769 */           appender.setLayout(layout);
/* 379:770 */           LogLog.debug("Parsing layout options for \"" + appenderName + "\".");
/* 380:    */           
/* 381:772 */           PropertySetter.setProperties(layout, props, layoutPrefix + ".");
/* 382:773 */           LogLog.debug("End of parsing for \"" + appenderName + "\".");
/* 383:    */         }
/* 384:    */       }
/* 385:776 */       String errorHandlerPrefix = prefix + ".errorhandler";
/* 386:777 */       String errorHandlerClass = OptionConverter.findAndSubst(errorHandlerPrefix, props);
/* 387:778 */       if (errorHandlerClass != null)
/* 388:    */       {
/* 389:779 */         ErrorHandler eh = (ErrorHandler)OptionConverter.instantiateByKey(props, errorHandlerPrefix, ErrorHandler.class, null);
/* 390:783 */         if (eh != null)
/* 391:    */         {
/* 392:784 */           appender.setErrorHandler(eh);
/* 393:785 */           LogLog.debug("Parsing errorhandler options for \"" + appenderName + "\".");
/* 394:786 */           parseErrorHandler(eh, errorHandlerPrefix, props, this.repository);
/* 395:787 */           Properties edited = new Properties();
/* 396:788 */           String[] keys = { errorHandlerPrefix + "." + "root-ref", errorHandlerPrefix + "." + "logger-ref", errorHandlerPrefix + "." + "appender-ref" };
/* 397:793 */           for (Iterator iter = props.entrySet().iterator(); iter.hasNext();)
/* 398:    */           {
/* 399:794 */             Map.Entry entry = (Map.Entry)iter.next();
/* 400:795 */             for (int i = 0; i < keys.length; i++) {
/* 401:797 */               if (keys[i].equals(entry.getKey())) {
/* 402:    */                 break;
/* 403:    */               }
/* 404:    */             }
/* 405:799 */             if (i == keys.length) {
/* 406:800 */               edited.put(entry.getKey(), entry.getValue());
/* 407:    */             }
/* 408:    */           }
/* 409:803 */           PropertySetter.setProperties(eh, edited, errorHandlerPrefix + ".");
/* 410:804 */           LogLog.debug("End of errorhandler parsing for \"" + appenderName + "\".");
/* 411:    */         }
/* 412:    */       }
/* 413:809 */       PropertySetter.setProperties(appender, props, prefix + ".");
/* 414:810 */       LogLog.debug("Parsed \"" + appenderName + "\" options.");
/* 415:    */     }
/* 416:812 */     parseAppenderFilters(props, appenderName, appender);
/* 417:813 */     registryPut(appender);
/* 418:814 */     return appender;
/* 419:    */   }
/* 420:    */   
/* 421:    */   private void parseErrorHandler(ErrorHandler eh, String errorHandlerPrefix, Properties props, LoggerRepository hierarchy)
/* 422:    */   {
/* 423:822 */     boolean rootRef = OptionConverter.toBoolean(OptionConverter.findAndSubst(errorHandlerPrefix + "root-ref", props), false);
/* 424:824 */     if (rootRef) {
/* 425:825 */       eh.setLogger(hierarchy.getRootLogger());
/* 426:    */     }
/* 427:827 */     String loggerName = OptionConverter.findAndSubst(errorHandlerPrefix + "logger-ref", props);
/* 428:828 */     if (loggerName != null)
/* 429:    */     {
/* 430:829 */       Logger logger = this.loggerFactory == null ? hierarchy.getLogger(loggerName) : hierarchy.getLogger(loggerName, this.loggerFactory);
/* 431:    */       
/* 432:831 */       eh.setLogger(logger);
/* 433:    */     }
/* 434:833 */     String appenderName = OptionConverter.findAndSubst(errorHandlerPrefix + "appender-ref", props);
/* 435:834 */     if (appenderName != null)
/* 436:    */     {
/* 437:835 */       Appender backup = parseAppender(props, appenderName);
/* 438:836 */       if (backup != null) {
/* 439:837 */         eh.setBackupAppender(backup);
/* 440:    */       }
/* 441:    */     }
/* 442:    */   }
/* 443:    */   
/* 444:    */   void parseAppenderFilters(Properties props, String appenderName, Appender appender)
/* 445:    */   {
/* 446:847 */     String filterPrefix = "log4j.appender." + appenderName + ".filter.";
/* 447:848 */     int fIdx = filterPrefix.length();
/* 448:849 */     Hashtable filters = new Hashtable();
/* 449:850 */     Enumeration e = props.keys();
/* 450:851 */     String name = "";
/* 451:852 */     while (e.hasMoreElements())
/* 452:    */     {
/* 453:853 */       String key = (String)e.nextElement();
/* 454:854 */       if (key.startsWith(filterPrefix))
/* 455:    */       {
/* 456:855 */         int dotIdx = key.indexOf('.', fIdx);
/* 457:856 */         String filterKey = key;
/* 458:857 */         if (dotIdx != -1)
/* 459:    */         {
/* 460:858 */           filterKey = key.substring(0, dotIdx);
/* 461:859 */           name = key.substring(dotIdx + 1);
/* 462:    */         }
/* 463:861 */         Vector filterOpts = (Vector)filters.get(filterKey);
/* 464:862 */         if (filterOpts == null)
/* 465:    */         {
/* 466:863 */           filterOpts = new Vector();
/* 467:864 */           filters.put(filterKey, filterOpts);
/* 468:    */         }
/* 469:866 */         if (dotIdx != -1)
/* 470:    */         {
/* 471:867 */           String value = OptionConverter.findAndSubst(key, props);
/* 472:868 */           filterOpts.add(new NameValue(name, value));
/* 473:    */         }
/* 474:    */       }
/* 475:    */     }
/* 476:875 */     Enumeration g = new SortedKeyEnumeration(filters);
/* 477:876 */     while (g.hasMoreElements())
/* 478:    */     {
/* 479:877 */       String key = (String)g.nextElement();
/* 480:878 */       String clazz = props.getProperty(key);
/* 481:879 */       if (clazz != null)
/* 482:    */       {
/* 483:880 */         LogLog.debug("Filter key: [" + key + "] class: [" + props.getProperty(key) + "] props: " + filters.get(key));
/* 484:881 */         Filter filter = (Filter)OptionConverter.instantiateByClassName(clazz, Filter.class, null);
/* 485:882 */         if (filter != null)
/* 486:    */         {
/* 487:883 */           PropertySetter propSetter = new PropertySetter(filter);
/* 488:884 */           Vector v = (Vector)filters.get(key);
/* 489:885 */           Enumeration filterProps = v.elements();
/* 490:886 */           while (filterProps.hasMoreElements())
/* 491:    */           {
/* 492:887 */             NameValue kv = (NameValue)filterProps.nextElement();
/* 493:888 */             propSetter.setProperty(kv.key, kv.value);
/* 494:    */           }
/* 495:890 */           propSetter.activate();
/* 496:891 */           LogLog.debug("Adding filter of type [" + filter.getClass() + "] to appender named [" + appender.getName() + "].");
/* 497:    */           
/* 498:893 */           appender.addFilter(filter);
/* 499:    */         }
/* 500:    */       }
/* 501:    */       else
/* 502:    */       {
/* 503:896 */         LogLog.warn("Missing class definition for filter: [" + key + "]");
/* 504:    */       }
/* 505:    */     }
/* 506:    */   }
/* 507:    */   
/* 508:    */   void registryPut(Appender appender)
/* 509:    */   {
/* 510:903 */     this.registry.put(appender.getName(), appender);
/* 511:    */   }
/* 512:    */   
/* 513:    */   Appender registryGet(String name)
/* 514:    */   {
/* 515:907 */     return (Appender)this.registry.get(name);
/* 516:    */   }
/* 517:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.PropertyConfigurator
 * JD-Core Version:    0.7.0.1
 */