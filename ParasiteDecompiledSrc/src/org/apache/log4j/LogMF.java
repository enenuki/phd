/*    1:     */ package org.apache.log4j;
/*    2:     */ 
/*    3:     */ import java.text.DateFormat;
/*    4:     */ import java.text.Format;
/*    5:     */ import java.text.MessageFormat;
/*    6:     */ import java.text.NumberFormat;
/*    7:     */ import java.util.Date;
/*    8:     */ import java.util.Locale;
/*    9:     */ import java.util.ResourceBundle;
/*   10:     */ import org.apache.log4j.spi.LoggingEvent;
/*   11:     */ 
/*   12:     */ public final class LogMF
/*   13:     */   extends LogXF
/*   14:     */ {
/*   15:  56 */   private static NumberFormat numberFormat = null;
/*   16:  60 */   private static Locale numberLocale = null;
/*   17:  64 */   private static DateFormat dateFormat = null;
/*   18:  68 */   private static Locale dateLocale = null;
/*   19:     */   
/*   20:     */   private static synchronized String formatNumber(Object n)
/*   21:     */   {
/*   22:  76 */     Locale currentLocale = Locale.getDefault();
/*   23:  77 */     if ((currentLocale != numberLocale) || (numberFormat == null))
/*   24:     */     {
/*   25:  78 */       numberLocale = currentLocale;
/*   26:  79 */       numberFormat = NumberFormat.getInstance(currentLocale);
/*   27:     */     }
/*   28:  81 */     return numberFormat.format(n);
/*   29:     */   }
/*   30:     */   
/*   31:     */   private static synchronized String formatDate(Object d)
/*   32:     */   {
/*   33:  91 */     Locale currentLocale = Locale.getDefault();
/*   34:  92 */     if ((currentLocale != dateLocale) || (dateFormat == null))
/*   35:     */     {
/*   36:  93 */       dateLocale = currentLocale;
/*   37:  94 */       dateFormat = DateFormat.getDateTimeInstance(3, 3, currentLocale);
/*   38:     */     }
/*   39:  99 */     return dateFormat.format(d);
/*   40:     */   }
/*   41:     */   
/*   42:     */   private static String formatObject(Object arg0)
/*   43:     */   {
/*   44: 109 */     if ((arg0 instanceof String)) {
/*   45: 110 */       return arg0.toString();
/*   46:     */     }
/*   47: 111 */     if (((arg0 instanceof Double)) || ((arg0 instanceof Float))) {
/*   48: 113 */       return formatNumber(arg0);
/*   49:     */     }
/*   50: 114 */     if ((arg0 instanceof Date)) {
/*   51: 115 */       return formatDate(arg0);
/*   52:     */     }
/*   53: 117 */     return String.valueOf(arg0);
/*   54:     */   }
/*   55:     */   
/*   56:     */   private static boolean isSimple(String pattern)
/*   57:     */   {
/*   58: 129 */     if (pattern.indexOf('\'') != -1) {
/*   59: 130 */       return false;
/*   60:     */     }
/*   61: 132 */     for (int pos = pattern.indexOf('{'); pos != -1; pos = pattern.indexOf('{', pos + 1)) {
/*   62: 135 */       if ((pos + 2 >= pattern.length()) || (pattern.charAt(pos + 2) != '}') || (pattern.charAt(pos + 1) < '0') || (pattern.charAt(pos + 1) > '9')) {
/*   63: 139 */         return false;
/*   64:     */       }
/*   65:     */     }
/*   66: 142 */     return true;
/*   67:     */   }
/*   68:     */   
/*   69:     */   private static String format(String pattern, Object[] arguments)
/*   70:     */   {
/*   71: 154 */     if (pattern == null) {
/*   72: 155 */       return null;
/*   73:     */     }
/*   74: 156 */     if (isSimple(pattern))
/*   75:     */     {
/*   76: 157 */       String[] formatted = new String[10];
/*   77: 158 */       int prev = 0;
/*   78: 159 */       String retval = "";
/*   79: 160 */       int pos = pattern.indexOf('{');
/*   80: 161 */       while (pos >= 0) {
/*   81: 162 */         if ((pos + 2 < pattern.length()) && (pattern.charAt(pos + 2) == '}') && (pattern.charAt(pos + 1) >= '0') && (pattern.charAt(pos + 1) <= '9'))
/*   82:     */         {
/*   83: 166 */           int index = pattern.charAt(pos + 1) - '0';
/*   84: 167 */           retval = retval + pattern.substring(prev, pos);
/*   85: 168 */           if (formatted[index] == null) {
/*   86: 169 */             if ((arguments == null) || (index >= arguments.length)) {
/*   87: 170 */               formatted[index] = pattern.substring(pos, pos + 3);
/*   88:     */             } else {
/*   89: 172 */               formatted[index] = formatObject(arguments[index]);
/*   90:     */             }
/*   91:     */           }
/*   92: 175 */           retval = retval + formatted[index];
/*   93: 176 */           prev = pos + 3;
/*   94: 177 */           pos = pattern.indexOf('{', prev);
/*   95:     */         }
/*   96:     */         else
/*   97:     */         {
/*   98: 179 */           pos = pattern.indexOf('{', pos + 1);
/*   99:     */         }
/*  100:     */       }
/*  101: 182 */       retval = retval + pattern.substring(prev);
/*  102: 183 */       return retval;
/*  103:     */     }
/*  104:     */     try
/*  105:     */     {
/*  106: 186 */       return MessageFormat.format(pattern, arguments);
/*  107:     */     }
/*  108:     */     catch (IllegalArgumentException ex) {}
/*  109: 188 */     return pattern;
/*  110:     */   }
/*  111:     */   
/*  112:     */   private static String format(String pattern, Object arg0)
/*  113:     */   {
/*  114: 200 */     if (pattern == null) {
/*  115: 201 */       return null;
/*  116:     */     }
/*  117: 202 */     if (isSimple(pattern))
/*  118:     */     {
/*  119: 203 */       String formatted = null;
/*  120: 204 */       int prev = 0;
/*  121: 205 */       String retval = "";
/*  122: 206 */       int pos = pattern.indexOf('{');
/*  123: 207 */       while (pos >= 0) {
/*  124: 208 */         if ((pos + 2 < pattern.length()) && (pattern.charAt(pos + 2) == '}') && (pattern.charAt(pos + 1) >= '0') && (pattern.charAt(pos + 1) <= '9'))
/*  125:     */         {
/*  126: 212 */           int index = pattern.charAt(pos + 1) - '0';
/*  127: 213 */           retval = retval + pattern.substring(prev, pos);
/*  128: 214 */           if (index != 0)
/*  129:     */           {
/*  130: 215 */             retval = retval + pattern.substring(pos, pos + 3);
/*  131:     */           }
/*  132:     */           else
/*  133:     */           {
/*  134: 217 */             if (formatted == null) {
/*  135: 218 */               formatted = formatObject(arg0);
/*  136:     */             }
/*  137: 220 */             retval = retval + formatted;
/*  138:     */           }
/*  139: 222 */           prev = pos + 3;
/*  140: 223 */           pos = pattern.indexOf('{', prev);
/*  141:     */         }
/*  142:     */         else
/*  143:     */         {
/*  144: 225 */           pos = pattern.indexOf('{', pos + 1);
/*  145:     */         }
/*  146:     */       }
/*  147: 228 */       retval = retval + pattern.substring(prev);
/*  148: 229 */       return retval;
/*  149:     */     }
/*  150:     */     try
/*  151:     */     {
/*  152: 232 */       return MessageFormat.format(pattern, new Object[] { arg0 });
/*  153:     */     }
/*  154:     */     catch (IllegalArgumentException ex) {}
/*  155: 234 */     return pattern;
/*  156:     */   }
/*  157:     */   
/*  158:     */   private static String format(String resourceBundleName, String key, Object[] arguments)
/*  159:     */   {
/*  160:     */     String pattern;
/*  161: 252 */     if (resourceBundleName != null) {
/*  162:     */       try
/*  163:     */       {
/*  164: 254 */         ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName);
/*  165:     */         
/*  166: 256 */         pattern = bundle.getString(key);
/*  167:     */       }
/*  168:     */       catch (Exception ex)
/*  169:     */       {
/*  170: 258 */         String pattern = key;
/*  171:     */       }
/*  172:     */     } else {
/*  173: 261 */       pattern = key;
/*  174:     */     }
/*  175: 263 */     return format(pattern, arguments);
/*  176:     */   }
/*  177:     */   
/*  178: 270 */   private static final String FQCN = LogMF.class.getName();
/*  179:     */   
/*  180:     */   private static void forcedLog(Logger logger, Level level, String msg)
/*  181:     */   {
/*  182: 282 */     logger.callAppenders(new LoggingEvent(FQCN, logger, level, msg, null));
/*  183:     */   }
/*  184:     */   
/*  185:     */   private static void forcedLog(Logger logger, Level level, String msg, Throwable t)
/*  186:     */   {
/*  187: 297 */     logger.callAppenders(new LoggingEvent(FQCN, logger, level, msg, t));
/*  188:     */   }
/*  189:     */   
/*  190:     */   public static void trace(Logger logger, String pattern, Object[] arguments)
/*  191:     */   {
/*  192: 308 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  193: 309 */       forcedLog(logger, LogXF.TRACE, format(pattern, arguments));
/*  194:     */     }
/*  195:     */   }
/*  196:     */   
/*  197:     */   public static void debug(Logger logger, String pattern, Object[] arguments)
/*  198:     */   {
/*  199: 321 */     if (logger.isDebugEnabled()) {
/*  200: 322 */       forcedLog(logger, Level.DEBUG, format(pattern, arguments));
/*  201:     */     }
/*  202:     */   }
/*  203:     */   
/*  204:     */   public static void info(Logger logger, String pattern, Object[] arguments)
/*  205:     */   {
/*  206: 334 */     if (logger.isInfoEnabled()) {
/*  207: 335 */       forcedLog(logger, Level.INFO, format(pattern, arguments));
/*  208:     */     }
/*  209:     */   }
/*  210:     */   
/*  211:     */   public static void warn(Logger logger, String pattern, Object[] arguments)
/*  212:     */   {
/*  213: 347 */     if (logger.isEnabledFor(Level.WARN)) {
/*  214: 348 */       forcedLog(logger, Level.WARN, format(pattern, arguments));
/*  215:     */     }
/*  216:     */   }
/*  217:     */   
/*  218:     */   public static void error(Logger logger, String pattern, Object[] arguments)
/*  219:     */   {
/*  220: 360 */     if (logger.isEnabledFor(Level.ERROR)) {
/*  221: 361 */       forcedLog(logger, Level.ERROR, format(pattern, arguments));
/*  222:     */     }
/*  223:     */   }
/*  224:     */   
/*  225:     */   public static void fatal(Logger logger, String pattern, Object[] arguments)
/*  226:     */   {
/*  227: 373 */     if (logger.isEnabledFor(Level.FATAL)) {
/*  228: 374 */       forcedLog(logger, Level.FATAL, format(pattern, arguments));
/*  229:     */     }
/*  230:     */   }
/*  231:     */   
/*  232:     */   public static void trace(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  233:     */   {
/*  234: 390 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  235: 391 */       forcedLog(logger, LogXF.TRACE, format(pattern, arguments), t);
/*  236:     */     }
/*  237:     */   }
/*  238:     */   
/*  239:     */   public static void debug(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  240:     */   {
/*  241: 406 */     if (logger.isDebugEnabled()) {
/*  242: 407 */       forcedLog(logger, Level.DEBUG, format(pattern, arguments), t);
/*  243:     */     }
/*  244:     */   }
/*  245:     */   
/*  246:     */   public static void info(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  247:     */   {
/*  248: 422 */     if (logger.isInfoEnabled()) {
/*  249: 423 */       forcedLog(logger, Level.INFO, format(pattern, arguments), t);
/*  250:     */     }
/*  251:     */   }
/*  252:     */   
/*  253:     */   public static void warn(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  254:     */   {
/*  255: 438 */     if (logger.isEnabledFor(Level.WARN)) {
/*  256: 439 */       forcedLog(logger, Level.WARN, format(pattern, arguments), t);
/*  257:     */     }
/*  258:     */   }
/*  259:     */   
/*  260:     */   public static void error(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  261:     */   {
/*  262: 454 */     if (logger.isEnabledFor(Level.ERROR)) {
/*  263: 455 */       forcedLog(logger, Level.ERROR, format(pattern, arguments), t);
/*  264:     */     }
/*  265:     */   }
/*  266:     */   
/*  267:     */   public static void fatal(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  268:     */   {
/*  269: 470 */     if (logger.isEnabledFor(Level.FATAL)) {
/*  270: 471 */       forcedLog(logger, Level.FATAL, format(pattern, arguments), t);
/*  271:     */     }
/*  272:     */   }
/*  273:     */   
/*  274:     */   public static void trace(Logger logger, String pattern, boolean argument)
/*  275:     */   {
/*  276: 485 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  277: 486 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  278:     */     }
/*  279:     */   }
/*  280:     */   
/*  281:     */   public static void trace(Logger logger, String pattern, char argument)
/*  282:     */   {
/*  283: 498 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  284: 499 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  285:     */     }
/*  286:     */   }
/*  287:     */   
/*  288:     */   public static void trace(Logger logger, String pattern, byte argument)
/*  289:     */   {
/*  290: 511 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  291: 512 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  292:     */     }
/*  293:     */   }
/*  294:     */   
/*  295:     */   public static void trace(Logger logger, String pattern, short argument)
/*  296:     */   {
/*  297: 524 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  298: 525 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  299:     */     }
/*  300:     */   }
/*  301:     */   
/*  302:     */   public static void trace(Logger logger, String pattern, int argument)
/*  303:     */   {
/*  304: 537 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  305: 538 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  306:     */     }
/*  307:     */   }
/*  308:     */   
/*  309:     */   public static void trace(Logger logger, String pattern, long argument)
/*  310:     */   {
/*  311: 550 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  312: 551 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  313:     */     }
/*  314:     */   }
/*  315:     */   
/*  316:     */   public static void trace(Logger logger, String pattern, float argument)
/*  317:     */   {
/*  318: 563 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  319: 564 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  320:     */     }
/*  321:     */   }
/*  322:     */   
/*  323:     */   public static void trace(Logger logger, String pattern, double argument)
/*  324:     */   {
/*  325: 576 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  326: 577 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  327:     */     }
/*  328:     */   }
/*  329:     */   
/*  330:     */   public static void trace(Logger logger, String pattern, Object argument)
/*  331:     */   {
/*  332: 589 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  333: 590 */       forcedLog(logger, LogXF.TRACE, format(pattern, argument));
/*  334:     */     }
/*  335:     */   }
/*  336:     */   
/*  337:     */   public static void trace(Logger logger, String pattern, Object arg0, Object arg1)
/*  338:     */   {
/*  339: 603 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  340: 604 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.toArray(arg0, arg1)));
/*  341:     */     }
/*  342:     */   }
/*  343:     */   
/*  344:     */   public static void trace(Logger logger, String pattern, Object arg0, Object arg1, Object arg2)
/*  345:     */   {
/*  346: 619 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  347: 620 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  348:     */     }
/*  349:     */   }
/*  350:     */   
/*  351:     */   public static void trace(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  352:     */   {
/*  353: 637 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  354: 638 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  355:     */     }
/*  356:     */   }
/*  357:     */   
/*  358:     */   public static void debug(Logger logger, String pattern, boolean argument)
/*  359:     */   {
/*  360: 651 */     if (logger.isDebugEnabled()) {
/*  361: 652 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  362:     */     }
/*  363:     */   }
/*  364:     */   
/*  365:     */   public static void debug(Logger logger, String pattern, char argument)
/*  366:     */   {
/*  367: 664 */     if (logger.isDebugEnabled()) {
/*  368: 665 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  369:     */     }
/*  370:     */   }
/*  371:     */   
/*  372:     */   public static void debug(Logger logger, String pattern, byte argument)
/*  373:     */   {
/*  374: 677 */     if (logger.isDebugEnabled()) {
/*  375: 678 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  376:     */     }
/*  377:     */   }
/*  378:     */   
/*  379:     */   public static void debug(Logger logger, String pattern, short argument)
/*  380:     */   {
/*  381: 690 */     if (logger.isDebugEnabled()) {
/*  382: 691 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  383:     */     }
/*  384:     */   }
/*  385:     */   
/*  386:     */   public static void debug(Logger logger, String pattern, int argument)
/*  387:     */   {
/*  388: 703 */     if (logger.isDebugEnabled()) {
/*  389: 704 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  390:     */     }
/*  391:     */   }
/*  392:     */   
/*  393:     */   public static void debug(Logger logger, String pattern, long argument)
/*  394:     */   {
/*  395: 716 */     if (logger.isDebugEnabled()) {
/*  396: 717 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  397:     */     }
/*  398:     */   }
/*  399:     */   
/*  400:     */   public static void debug(Logger logger, String pattern, float argument)
/*  401:     */   {
/*  402: 729 */     if (logger.isDebugEnabled()) {
/*  403: 730 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  404:     */     }
/*  405:     */   }
/*  406:     */   
/*  407:     */   public static void debug(Logger logger, String pattern, double argument)
/*  408:     */   {
/*  409: 742 */     if (logger.isDebugEnabled()) {
/*  410: 743 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  411:     */     }
/*  412:     */   }
/*  413:     */   
/*  414:     */   public static void debug(Logger logger, String pattern, Object argument)
/*  415:     */   {
/*  416: 755 */     if (logger.isDebugEnabled()) {
/*  417: 756 */       forcedLog(logger, Level.DEBUG, format(pattern, argument));
/*  418:     */     }
/*  419:     */   }
/*  420:     */   
/*  421:     */   public static void debug(Logger logger, String pattern, Object arg0, Object arg1)
/*  422:     */   {
/*  423: 769 */     if (logger.isDebugEnabled()) {
/*  424: 770 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.toArray(arg0, arg1)));
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   public static void debug(Logger logger, String pattern, Object arg0, Object arg1, Object arg2)
/*  429:     */   {
/*  430: 785 */     if (logger.isDebugEnabled()) {
/*  431: 786 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  432:     */     }
/*  433:     */   }
/*  434:     */   
/*  435:     */   public static void debug(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  436:     */   {
/*  437: 803 */     if (logger.isDebugEnabled()) {
/*  438: 804 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  439:     */     }
/*  440:     */   }
/*  441:     */   
/*  442:     */   public static void info(Logger logger, String pattern, boolean argument)
/*  443:     */   {
/*  444: 817 */     if (logger.isInfoEnabled()) {
/*  445: 818 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  446:     */     }
/*  447:     */   }
/*  448:     */   
/*  449:     */   public static void info(Logger logger, String pattern, char argument)
/*  450:     */   {
/*  451: 830 */     if (logger.isInfoEnabled()) {
/*  452: 831 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  453:     */     }
/*  454:     */   }
/*  455:     */   
/*  456:     */   public static void info(Logger logger, String pattern, byte argument)
/*  457:     */   {
/*  458: 843 */     if (logger.isInfoEnabled()) {
/*  459: 844 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  460:     */     }
/*  461:     */   }
/*  462:     */   
/*  463:     */   public static void info(Logger logger, String pattern, short argument)
/*  464:     */   {
/*  465: 856 */     if (logger.isInfoEnabled()) {
/*  466: 857 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  467:     */     }
/*  468:     */   }
/*  469:     */   
/*  470:     */   public static void info(Logger logger, String pattern, int argument)
/*  471:     */   {
/*  472: 869 */     if (logger.isInfoEnabled()) {
/*  473: 870 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  474:     */     }
/*  475:     */   }
/*  476:     */   
/*  477:     */   public static void info(Logger logger, String pattern, long argument)
/*  478:     */   {
/*  479: 882 */     if (logger.isInfoEnabled()) {
/*  480: 883 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  481:     */     }
/*  482:     */   }
/*  483:     */   
/*  484:     */   public static void info(Logger logger, String pattern, float argument)
/*  485:     */   {
/*  486: 895 */     if (logger.isInfoEnabled()) {
/*  487: 896 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  488:     */     }
/*  489:     */   }
/*  490:     */   
/*  491:     */   public static void info(Logger logger, String pattern, double argument)
/*  492:     */   {
/*  493: 908 */     if (logger.isInfoEnabled()) {
/*  494: 909 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  495:     */     }
/*  496:     */   }
/*  497:     */   
/*  498:     */   public static void info(Logger logger, String pattern, Object argument)
/*  499:     */   {
/*  500: 921 */     if (logger.isInfoEnabled()) {
/*  501: 922 */       forcedLog(logger, Level.INFO, format(pattern, argument));
/*  502:     */     }
/*  503:     */   }
/*  504:     */   
/*  505:     */   public static void info(Logger logger, String pattern, Object arg0, Object arg1)
/*  506:     */   {
/*  507: 935 */     if (logger.isInfoEnabled()) {
/*  508: 936 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.toArray(arg0, arg1)));
/*  509:     */     }
/*  510:     */   }
/*  511:     */   
/*  512:     */   public static void info(Logger logger, String pattern, Object arg0, Object arg1, Object arg2)
/*  513:     */   {
/*  514: 950 */     if (logger.isInfoEnabled()) {
/*  515: 951 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  516:     */     }
/*  517:     */   }
/*  518:     */   
/*  519:     */   public static void info(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  520:     */   {
/*  521: 968 */     if (logger.isInfoEnabled()) {
/*  522: 969 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  523:     */     }
/*  524:     */   }
/*  525:     */   
/*  526:     */   public static void warn(Logger logger, String pattern, boolean argument)
/*  527:     */   {
/*  528: 982 */     if (logger.isEnabledFor(Level.WARN)) {
/*  529: 983 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  530:     */     }
/*  531:     */   }
/*  532:     */   
/*  533:     */   public static void warn(Logger logger, String pattern, char argument)
/*  534:     */   {
/*  535: 995 */     if (logger.isEnabledFor(Level.WARN)) {
/*  536: 996 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  537:     */     }
/*  538:     */   }
/*  539:     */   
/*  540:     */   public static void warn(Logger logger, String pattern, byte argument)
/*  541:     */   {
/*  542:1008 */     if (logger.isEnabledFor(Level.WARN)) {
/*  543:1009 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  544:     */     }
/*  545:     */   }
/*  546:     */   
/*  547:     */   public static void warn(Logger logger, String pattern, short argument)
/*  548:     */   {
/*  549:1021 */     if (logger.isEnabledFor(Level.WARN)) {
/*  550:1022 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  551:     */     }
/*  552:     */   }
/*  553:     */   
/*  554:     */   public static void warn(Logger logger, String pattern, int argument)
/*  555:     */   {
/*  556:1034 */     if (logger.isEnabledFor(Level.WARN)) {
/*  557:1035 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  558:     */     }
/*  559:     */   }
/*  560:     */   
/*  561:     */   public static void warn(Logger logger, String pattern, long argument)
/*  562:     */   {
/*  563:1047 */     if (logger.isEnabledFor(Level.WARN)) {
/*  564:1048 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  565:     */     }
/*  566:     */   }
/*  567:     */   
/*  568:     */   public static void warn(Logger logger, String pattern, float argument)
/*  569:     */   {
/*  570:1060 */     if (logger.isEnabledFor(Level.WARN)) {
/*  571:1061 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   public static void warn(Logger logger, String pattern, double argument)
/*  576:     */   {
/*  577:1073 */     if (logger.isEnabledFor(Level.WARN)) {
/*  578:1074 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  579:     */     }
/*  580:     */   }
/*  581:     */   
/*  582:     */   public static void warn(Logger logger, String pattern, Object argument)
/*  583:     */   {
/*  584:1086 */     if (logger.isEnabledFor(Level.WARN)) {
/*  585:1087 */       forcedLog(logger, Level.WARN, format(pattern, argument));
/*  586:     */     }
/*  587:     */   }
/*  588:     */   
/*  589:     */   public static void warn(Logger logger, String pattern, Object arg0, Object arg1)
/*  590:     */   {
/*  591:1100 */     if (logger.isEnabledFor(Level.WARN)) {
/*  592:1101 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.toArray(arg0, arg1)));
/*  593:     */     }
/*  594:     */   }
/*  595:     */   
/*  596:     */   public static void warn(Logger logger, String pattern, Object arg0, Object arg1, Object arg2)
/*  597:     */   {
/*  598:1116 */     if (logger.isEnabledFor(Level.WARN)) {
/*  599:1117 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  600:     */     }
/*  601:     */   }
/*  602:     */   
/*  603:     */   public static void warn(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  604:     */   {
/*  605:1134 */     if (logger.isEnabledFor(Level.WARN)) {
/*  606:1135 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  607:     */     }
/*  608:     */   }
/*  609:     */   
/*  610:     */   public static void log(Logger logger, Level level, String pattern, Object[] parameters)
/*  611:     */   {
/*  612:1151 */     if (logger.isEnabledFor(level)) {
/*  613:1152 */       forcedLog(logger, level, format(pattern, parameters));
/*  614:     */     }
/*  615:     */   }
/*  616:     */   
/*  617:     */   public static void log(Logger logger, Level level, Throwable t, String pattern, Object[] parameters)
/*  618:     */   {
/*  619:1170 */     if (logger.isEnabledFor(level)) {
/*  620:1171 */       forcedLog(logger, level, format(pattern, parameters), t);
/*  621:     */     }
/*  622:     */   }
/*  623:     */   
/*  624:     */   public static void log(Logger logger, Level level, String pattern, Object param1)
/*  625:     */   {
/*  626:1187 */     if (logger.isEnabledFor(level)) {
/*  627:1188 */       forcedLog(logger, level, format(pattern, LogXF.toArray(param1)));
/*  628:     */     }
/*  629:     */   }
/*  630:     */   
/*  631:     */   public static void log(Logger logger, Level level, String pattern, boolean param1)
/*  632:     */   {
/*  633:1204 */     if (logger.isEnabledFor(level)) {
/*  634:1205 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  635:     */     }
/*  636:     */   }
/*  637:     */   
/*  638:     */   public static void log(Logger logger, Level level, String pattern, byte param1)
/*  639:     */   {
/*  640:1222 */     if (logger.isEnabledFor(level)) {
/*  641:1223 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  642:     */     }
/*  643:     */   }
/*  644:     */   
/*  645:     */   public static void log(Logger logger, Level level, String pattern, char param1)
/*  646:     */   {
/*  647:1240 */     if (logger.isEnabledFor(level)) {
/*  648:1241 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  649:     */     }
/*  650:     */   }
/*  651:     */   
/*  652:     */   public static void log(Logger logger, Level level, String pattern, short param1)
/*  653:     */   {
/*  654:1257 */     if (logger.isEnabledFor(level)) {
/*  655:1258 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  656:     */     }
/*  657:     */   }
/*  658:     */   
/*  659:     */   public static void log(Logger logger, Level level, String pattern, int param1)
/*  660:     */   {
/*  661:1274 */     if (logger.isEnabledFor(level)) {
/*  662:1275 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  663:     */     }
/*  664:     */   }
/*  665:     */   
/*  666:     */   public static void log(Logger logger, Level level, String pattern, long param1)
/*  667:     */   {
/*  668:1292 */     if (logger.isEnabledFor(level)) {
/*  669:1293 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  670:     */     }
/*  671:     */   }
/*  672:     */   
/*  673:     */   public static void log(Logger logger, Level level, String pattern, float param1)
/*  674:     */   {
/*  675:1310 */     if (logger.isEnabledFor(level)) {
/*  676:1311 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  677:     */     }
/*  678:     */   }
/*  679:     */   
/*  680:     */   public static void log(Logger logger, Level level, String pattern, double param1)
/*  681:     */   {
/*  682:1328 */     if (logger.isEnabledFor(level)) {
/*  683:1329 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  684:     */     }
/*  685:     */   }
/*  686:     */   
/*  687:     */   public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1)
/*  688:     */   {
/*  689:1347 */     if (logger.isEnabledFor(level)) {
/*  690:1348 */       forcedLog(logger, level, format(pattern, LogXF.toArray(arg0, arg1)));
/*  691:     */     }
/*  692:     */   }
/*  693:     */   
/*  694:     */   public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1, Object arg2)
/*  695:     */   {
/*  696:1366 */     if (logger.isEnabledFor(level)) {
/*  697:1367 */       forcedLog(logger, level, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  698:     */     }
/*  699:     */   }
/*  700:     */   
/*  701:     */   public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  702:     */   {
/*  703:1387 */     if (logger.isEnabledFor(level)) {
/*  704:1388 */       forcedLog(logger, level, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  705:     */     }
/*  706:     */   }
/*  707:     */   
/*  708:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object[] parameters)
/*  709:     */   {
/*  710:1407 */     if (logger.isEnabledFor(level)) {
/*  711:1408 */       forcedLog(logger, level, format(bundleName, key, parameters));
/*  712:     */     }
/*  713:     */   }
/*  714:     */   
/*  715:     */   public static void logrb(Logger logger, Level level, Throwable t, String bundleName, String key, Object[] parameters)
/*  716:     */   {
/*  717:1428 */     if (logger.isEnabledFor(level)) {
/*  718:1429 */       forcedLog(logger, level, format(bundleName, key, parameters), t);
/*  719:     */     }
/*  720:     */   }
/*  721:     */   
/*  722:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object param1)
/*  723:     */   {
/*  724:1447 */     if (logger.isEnabledFor(level)) {
/*  725:1448 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(param1)));
/*  726:     */     }
/*  727:     */   }
/*  728:     */   
/*  729:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, boolean param1)
/*  730:     */   {
/*  731:1466 */     if (logger.isEnabledFor(level)) {
/*  732:1467 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  733:     */     }
/*  734:     */   }
/*  735:     */   
/*  736:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, char param1)
/*  737:     */   {
/*  738:1485 */     if (logger.isEnabledFor(level)) {
/*  739:1486 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  740:     */     }
/*  741:     */   }
/*  742:     */   
/*  743:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, byte param1)
/*  744:     */   {
/*  745:1504 */     if (logger.isEnabledFor(level)) {
/*  746:1505 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  747:     */     }
/*  748:     */   }
/*  749:     */   
/*  750:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, short param1)
/*  751:     */   {
/*  752:1523 */     if (logger.isEnabledFor(level)) {
/*  753:1524 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  754:     */     }
/*  755:     */   }
/*  756:     */   
/*  757:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, int param1)
/*  758:     */   {
/*  759:1542 */     if (logger.isEnabledFor(level)) {
/*  760:1543 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  761:     */     }
/*  762:     */   }
/*  763:     */   
/*  764:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, long param1)
/*  765:     */   {
/*  766:1561 */     if (logger.isEnabledFor(level)) {
/*  767:1562 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  768:     */     }
/*  769:     */   }
/*  770:     */   
/*  771:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, float param1)
/*  772:     */   {
/*  773:1579 */     if (logger.isEnabledFor(level)) {
/*  774:1580 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  775:     */     }
/*  776:     */   }
/*  777:     */   
/*  778:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, double param1)
/*  779:     */   {
/*  780:1599 */     if (logger.isEnabledFor(level)) {
/*  781:1600 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  782:     */     }
/*  783:     */   }
/*  784:     */   
/*  785:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1)
/*  786:     */   {
/*  787:1620 */     if (logger.isEnabledFor(level)) {
/*  788:1621 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(param0, param1)));
/*  789:     */     }
/*  790:     */   }
/*  791:     */   
/*  792:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1, Object param2)
/*  793:     */   {
/*  794:1644 */     if (logger.isEnabledFor(level)) {
/*  795:1645 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(param0, param1, param2)));
/*  796:     */     }
/*  797:     */   }
/*  798:     */   
/*  799:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1, Object param2, Object param3)
/*  800:     */   {
/*  801:1670 */     if (logger.isEnabledFor(level)) {
/*  802:1671 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(param0, param1, param2, param3)));
/*  803:     */     }
/*  804:     */   }
/*  805:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.LogMF
 * JD-Core Version:    0.7.0.1
 */