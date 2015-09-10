/*    1:     */ package org.apache.log4j;
/*    2:     */ 
/*    3:     */ import java.util.ResourceBundle;
/*    4:     */ import org.apache.log4j.spi.LoggingEvent;
/*    5:     */ 
/*    6:     */ public final class LogSF
/*    7:     */   extends LogXF
/*    8:     */ {
/*    9:     */   private static String format(String pattern, Object[] arguments)
/*   10:     */   {
/*   11:  53 */     if (pattern != null)
/*   12:     */     {
/*   13:  54 */       String retval = "";
/*   14:  55 */       int count = 0;
/*   15:  56 */       int prev = 0;
/*   16:  57 */       int pos = pattern.indexOf("{");
/*   17:  58 */       while (pos >= 0)
/*   18:     */       {
/*   19:  59 */         if ((pos == 0) || (pattern.charAt(pos - 1) != '\\'))
/*   20:     */         {
/*   21:  60 */           retval = retval + pattern.substring(prev, pos);
/*   22:  61 */           if ((pos + 1 < pattern.length()) && (pattern.charAt(pos + 1) == '}'))
/*   23:     */           {
/*   24:  62 */             if ((arguments != null) && (count < arguments.length)) {
/*   25:  63 */               retval = retval + arguments[(count++)];
/*   26:     */             } else {
/*   27:  65 */               retval = retval + "{}";
/*   28:     */             }
/*   29:  67 */             prev = pos + 2;
/*   30:     */           }
/*   31:     */           else
/*   32:     */           {
/*   33:  69 */             retval = retval + "{";
/*   34:  70 */             prev = pos + 1;
/*   35:     */           }
/*   36:     */         }
/*   37:     */         else
/*   38:     */         {
/*   39:  73 */           retval = retval + pattern.substring(prev, pos - 1) + "{";
/*   40:  74 */           prev = pos + 1;
/*   41:     */         }
/*   42:  76 */         pos = pattern.indexOf("{", prev);
/*   43:     */       }
/*   44:  78 */       return retval + pattern.substring(prev);
/*   45:     */     }
/*   46:  80 */     return null;
/*   47:     */   }
/*   48:     */   
/*   49:     */   private static String format(String pattern, Object arg0)
/*   50:     */   {
/*   51:  90 */     if (pattern != null)
/*   52:     */     {
/*   53:  93 */       if (pattern.indexOf("\\{") >= 0) {
/*   54:  94 */         return format(pattern, new Object[] { arg0 });
/*   55:     */       }
/*   56:  96 */       int pos = pattern.indexOf("{}");
/*   57:  97 */       if (pos >= 0) {
/*   58:  98 */         return pattern.substring(0, pos) + arg0 + pattern.substring(pos + 2);
/*   59:     */       }
/*   60:     */     }
/*   61: 101 */     return pattern;
/*   62:     */   }
/*   63:     */   
/*   64:     */   private static String format(String resourceBundleName, String key, Object[] arguments)
/*   65:     */   {
/*   66:     */     String pattern;
/*   67: 117 */     if (resourceBundleName != null) {
/*   68:     */       try
/*   69:     */       {
/*   70: 119 */         ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName);
/*   71:     */         
/*   72: 121 */         pattern = bundle.getString(key);
/*   73:     */       }
/*   74:     */       catch (Exception ex)
/*   75:     */       {
/*   76: 123 */         String pattern = key;
/*   77:     */       }
/*   78:     */     } else {
/*   79: 126 */       pattern = key;
/*   80:     */     }
/*   81: 128 */     return format(pattern, arguments);
/*   82:     */   }
/*   83:     */   
/*   84: 135 */   private static final String FQCN = LogSF.class.getName();
/*   85:     */   
/*   86:     */   private static void forcedLog(Logger logger, Level level, String msg)
/*   87:     */   {
/*   88: 147 */     logger.callAppenders(new LoggingEvent(FQCN, logger, level, msg, null));
/*   89:     */   }
/*   90:     */   
/*   91:     */   private static void forcedLog(Logger logger, Level level, String msg, Throwable t)
/*   92:     */   {
/*   93: 162 */     logger.callAppenders(new LoggingEvent(FQCN, logger, level, msg, t));
/*   94:     */   }
/*   95:     */   
/*   96:     */   public static void trace(Logger logger, String pattern, Object[] arguments)
/*   97:     */   {
/*   98: 173 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*   99: 174 */       forcedLog(logger, LogXF.TRACE, format(pattern, arguments));
/*  100:     */     }
/*  101:     */   }
/*  102:     */   
/*  103:     */   public static void debug(Logger logger, String pattern, Object[] arguments)
/*  104:     */   {
/*  105: 186 */     if (logger.isDebugEnabled()) {
/*  106: 187 */       forcedLog(logger, Level.DEBUG, format(pattern, arguments));
/*  107:     */     }
/*  108:     */   }
/*  109:     */   
/*  110:     */   public static void info(Logger logger, String pattern, Object[] arguments)
/*  111:     */   {
/*  112: 199 */     if (logger.isInfoEnabled()) {
/*  113: 200 */       forcedLog(logger, Level.INFO, format(pattern, arguments));
/*  114:     */     }
/*  115:     */   }
/*  116:     */   
/*  117:     */   public static void warn(Logger logger, String pattern, Object[] arguments)
/*  118:     */   {
/*  119: 212 */     if (logger.isEnabledFor(Level.WARN)) {
/*  120: 213 */       forcedLog(logger, Level.WARN, format(pattern, arguments));
/*  121:     */     }
/*  122:     */   }
/*  123:     */   
/*  124:     */   public static void error(Logger logger, String pattern, Object[] arguments)
/*  125:     */   {
/*  126: 225 */     if (logger.isEnabledFor(Level.ERROR)) {
/*  127: 226 */       forcedLog(logger, Level.ERROR, format(pattern, arguments));
/*  128:     */     }
/*  129:     */   }
/*  130:     */   
/*  131:     */   public static void fatal(Logger logger, String pattern, Object[] arguments)
/*  132:     */   {
/*  133: 238 */     if (logger.isEnabledFor(Level.FATAL)) {
/*  134: 239 */       forcedLog(logger, Level.FATAL, format(pattern, arguments));
/*  135:     */     }
/*  136:     */   }
/*  137:     */   
/*  138:     */   public static void trace(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  139:     */   {
/*  140: 255 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  141: 256 */       forcedLog(logger, LogXF.TRACE, format(pattern, arguments), t);
/*  142:     */     }
/*  143:     */   }
/*  144:     */   
/*  145:     */   public static void debug(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  146:     */   {
/*  147: 271 */     if (logger.isDebugEnabled()) {
/*  148: 272 */       forcedLog(logger, Level.DEBUG, format(pattern, arguments), t);
/*  149:     */     }
/*  150:     */   }
/*  151:     */   
/*  152:     */   public static void info(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  153:     */   {
/*  154: 287 */     if (logger.isInfoEnabled()) {
/*  155: 288 */       forcedLog(logger, Level.INFO, format(pattern, arguments), t);
/*  156:     */     }
/*  157:     */   }
/*  158:     */   
/*  159:     */   public static void warn(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  160:     */   {
/*  161: 303 */     if (logger.isEnabledFor(Level.WARN)) {
/*  162: 304 */       forcedLog(logger, Level.WARN, format(pattern, arguments), t);
/*  163:     */     }
/*  164:     */   }
/*  165:     */   
/*  166:     */   public static void error(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  167:     */   {
/*  168: 319 */     if (logger.isEnabledFor(Level.ERROR)) {
/*  169: 320 */       forcedLog(logger, Level.ERROR, format(pattern, arguments), t);
/*  170:     */     }
/*  171:     */   }
/*  172:     */   
/*  173:     */   public static void fatal(Logger logger, Throwable t, String pattern, Object[] arguments)
/*  174:     */   {
/*  175: 335 */     if (logger.isEnabledFor(Level.FATAL)) {
/*  176: 336 */       forcedLog(logger, Level.FATAL, format(pattern, arguments), t);
/*  177:     */     }
/*  178:     */   }
/*  179:     */   
/*  180:     */   public static void trace(Logger logger, String pattern, boolean argument)
/*  181:     */   {
/*  182: 350 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  183: 351 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  184:     */     }
/*  185:     */   }
/*  186:     */   
/*  187:     */   public static void trace(Logger logger, String pattern, char argument)
/*  188:     */   {
/*  189: 363 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  190: 364 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  191:     */     }
/*  192:     */   }
/*  193:     */   
/*  194:     */   public static void trace(Logger logger, String pattern, byte argument)
/*  195:     */   {
/*  196: 376 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  197: 377 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  198:     */     }
/*  199:     */   }
/*  200:     */   
/*  201:     */   public static void trace(Logger logger, String pattern, short argument)
/*  202:     */   {
/*  203: 389 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  204: 390 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  205:     */     }
/*  206:     */   }
/*  207:     */   
/*  208:     */   public static void trace(Logger logger, String pattern, int argument)
/*  209:     */   {
/*  210: 402 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  211: 403 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  212:     */     }
/*  213:     */   }
/*  214:     */   
/*  215:     */   public static void trace(Logger logger, String pattern, long argument)
/*  216:     */   {
/*  217: 415 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  218: 416 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  219:     */     }
/*  220:     */   }
/*  221:     */   
/*  222:     */   public static void trace(Logger logger, String pattern, float argument)
/*  223:     */   {
/*  224: 428 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  225: 429 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  226:     */     }
/*  227:     */   }
/*  228:     */   
/*  229:     */   public static void trace(Logger logger, String pattern, double argument)
/*  230:     */   {
/*  231: 441 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  232: 442 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.valueOf(argument)));
/*  233:     */     }
/*  234:     */   }
/*  235:     */   
/*  236:     */   public static void trace(Logger logger, String pattern, Object argument)
/*  237:     */   {
/*  238: 454 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  239: 455 */       forcedLog(logger, LogXF.TRACE, format(pattern, argument));
/*  240:     */     }
/*  241:     */   }
/*  242:     */   
/*  243:     */   public static void trace(Logger logger, String pattern, Object arg0, Object arg1)
/*  244:     */   {
/*  245: 468 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  246: 469 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.toArray(arg0, arg1)));
/*  247:     */     }
/*  248:     */   }
/*  249:     */   
/*  250:     */   public static void trace(Logger logger, String pattern, Object arg0, Object arg1, Object arg2)
/*  251:     */   {
/*  252: 484 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  253: 485 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  254:     */     }
/*  255:     */   }
/*  256:     */   
/*  257:     */   public static void trace(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  258:     */   {
/*  259: 502 */     if (logger.isEnabledFor(LogXF.TRACE)) {
/*  260: 503 */       forcedLog(logger, LogXF.TRACE, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  261:     */     }
/*  262:     */   }
/*  263:     */   
/*  264:     */   public static void debug(Logger logger, String pattern, boolean argument)
/*  265:     */   {
/*  266: 516 */     if (logger.isDebugEnabled()) {
/*  267: 517 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  268:     */     }
/*  269:     */   }
/*  270:     */   
/*  271:     */   public static void debug(Logger logger, String pattern, char argument)
/*  272:     */   {
/*  273: 529 */     if (logger.isDebugEnabled()) {
/*  274: 530 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  275:     */     }
/*  276:     */   }
/*  277:     */   
/*  278:     */   public static void debug(Logger logger, String pattern, byte argument)
/*  279:     */   {
/*  280: 542 */     if (logger.isDebugEnabled()) {
/*  281: 543 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  282:     */     }
/*  283:     */   }
/*  284:     */   
/*  285:     */   public static void debug(Logger logger, String pattern, short argument)
/*  286:     */   {
/*  287: 555 */     if (logger.isDebugEnabled()) {
/*  288: 556 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  289:     */     }
/*  290:     */   }
/*  291:     */   
/*  292:     */   public static void debug(Logger logger, String pattern, int argument)
/*  293:     */   {
/*  294: 568 */     if (logger.isDebugEnabled()) {
/*  295: 569 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  296:     */     }
/*  297:     */   }
/*  298:     */   
/*  299:     */   public static void debug(Logger logger, String pattern, long argument)
/*  300:     */   {
/*  301: 581 */     if (logger.isDebugEnabled()) {
/*  302: 582 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  303:     */     }
/*  304:     */   }
/*  305:     */   
/*  306:     */   public static void debug(Logger logger, String pattern, float argument)
/*  307:     */   {
/*  308: 594 */     if (logger.isDebugEnabled()) {
/*  309: 595 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   public static void debug(Logger logger, String pattern, double argument)
/*  314:     */   {
/*  315: 607 */     if (logger.isDebugEnabled()) {
/*  316: 608 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.valueOf(argument)));
/*  317:     */     }
/*  318:     */   }
/*  319:     */   
/*  320:     */   public static void debug(Logger logger, String pattern, Object argument)
/*  321:     */   {
/*  322: 620 */     if (logger.isDebugEnabled()) {
/*  323: 621 */       forcedLog(logger, Level.DEBUG, format(pattern, argument));
/*  324:     */     }
/*  325:     */   }
/*  326:     */   
/*  327:     */   public static void debug(Logger logger, String pattern, Object arg0, Object arg1)
/*  328:     */   {
/*  329: 634 */     if (logger.isDebugEnabled()) {
/*  330: 635 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.toArray(arg0, arg1)));
/*  331:     */     }
/*  332:     */   }
/*  333:     */   
/*  334:     */   public static void debug(Logger logger, String pattern, Object arg0, Object arg1, Object arg2)
/*  335:     */   {
/*  336: 650 */     if (logger.isDebugEnabled()) {
/*  337: 651 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  338:     */     }
/*  339:     */   }
/*  340:     */   
/*  341:     */   public static void debug(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  342:     */   {
/*  343: 668 */     if (logger.isDebugEnabled()) {
/*  344: 669 */       forcedLog(logger, Level.DEBUG, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  345:     */     }
/*  346:     */   }
/*  347:     */   
/*  348:     */   public static void info(Logger logger, String pattern, boolean argument)
/*  349:     */   {
/*  350: 682 */     if (logger.isInfoEnabled()) {
/*  351: 683 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  352:     */     }
/*  353:     */   }
/*  354:     */   
/*  355:     */   public static void info(Logger logger, String pattern, char argument)
/*  356:     */   {
/*  357: 695 */     if (logger.isInfoEnabled()) {
/*  358: 696 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  359:     */     }
/*  360:     */   }
/*  361:     */   
/*  362:     */   public static void info(Logger logger, String pattern, byte argument)
/*  363:     */   {
/*  364: 708 */     if (logger.isInfoEnabled()) {
/*  365: 709 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  366:     */     }
/*  367:     */   }
/*  368:     */   
/*  369:     */   public static void info(Logger logger, String pattern, short argument)
/*  370:     */   {
/*  371: 721 */     if (logger.isInfoEnabled()) {
/*  372: 722 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  373:     */     }
/*  374:     */   }
/*  375:     */   
/*  376:     */   public static void info(Logger logger, String pattern, int argument)
/*  377:     */   {
/*  378: 734 */     if (logger.isInfoEnabled()) {
/*  379: 735 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  380:     */     }
/*  381:     */   }
/*  382:     */   
/*  383:     */   public static void info(Logger logger, String pattern, long argument)
/*  384:     */   {
/*  385: 747 */     if (logger.isInfoEnabled()) {
/*  386: 748 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  387:     */     }
/*  388:     */   }
/*  389:     */   
/*  390:     */   public static void info(Logger logger, String pattern, float argument)
/*  391:     */   {
/*  392: 760 */     if (logger.isInfoEnabled()) {
/*  393: 761 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  394:     */     }
/*  395:     */   }
/*  396:     */   
/*  397:     */   public static void info(Logger logger, String pattern, double argument)
/*  398:     */   {
/*  399: 773 */     if (logger.isInfoEnabled()) {
/*  400: 774 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.valueOf(argument)));
/*  401:     */     }
/*  402:     */   }
/*  403:     */   
/*  404:     */   public static void info(Logger logger, String pattern, Object argument)
/*  405:     */   {
/*  406: 786 */     if (logger.isInfoEnabled()) {
/*  407: 787 */       forcedLog(logger, Level.INFO, format(pattern, argument));
/*  408:     */     }
/*  409:     */   }
/*  410:     */   
/*  411:     */   public static void info(Logger logger, String pattern, Object arg0, Object arg1)
/*  412:     */   {
/*  413: 800 */     if (logger.isInfoEnabled()) {
/*  414: 801 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.toArray(arg0, arg1)));
/*  415:     */     }
/*  416:     */   }
/*  417:     */   
/*  418:     */   public static void info(Logger logger, String pattern, Object arg0, Object arg1, Object arg2)
/*  419:     */   {
/*  420: 815 */     if (logger.isInfoEnabled()) {
/*  421: 816 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  422:     */     }
/*  423:     */   }
/*  424:     */   
/*  425:     */   public static void info(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  426:     */   {
/*  427: 833 */     if (logger.isInfoEnabled()) {
/*  428: 834 */       forcedLog(logger, Level.INFO, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  429:     */     }
/*  430:     */   }
/*  431:     */   
/*  432:     */   public static void warn(Logger logger, String pattern, boolean argument)
/*  433:     */   {
/*  434: 847 */     if (logger.isEnabledFor(Level.WARN)) {
/*  435: 848 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  436:     */     }
/*  437:     */   }
/*  438:     */   
/*  439:     */   public static void warn(Logger logger, String pattern, char argument)
/*  440:     */   {
/*  441: 860 */     if (logger.isEnabledFor(Level.WARN)) {
/*  442: 861 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  443:     */     }
/*  444:     */   }
/*  445:     */   
/*  446:     */   public static void warn(Logger logger, String pattern, byte argument)
/*  447:     */   {
/*  448: 873 */     if (logger.isEnabledFor(Level.WARN)) {
/*  449: 874 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  450:     */     }
/*  451:     */   }
/*  452:     */   
/*  453:     */   public static void warn(Logger logger, String pattern, short argument)
/*  454:     */   {
/*  455: 886 */     if (logger.isEnabledFor(Level.WARN)) {
/*  456: 887 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  457:     */     }
/*  458:     */   }
/*  459:     */   
/*  460:     */   public static void warn(Logger logger, String pattern, int argument)
/*  461:     */   {
/*  462: 899 */     if (logger.isEnabledFor(Level.WARN)) {
/*  463: 900 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  464:     */     }
/*  465:     */   }
/*  466:     */   
/*  467:     */   public static void warn(Logger logger, String pattern, long argument)
/*  468:     */   {
/*  469: 912 */     if (logger.isEnabledFor(Level.WARN)) {
/*  470: 913 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  471:     */     }
/*  472:     */   }
/*  473:     */   
/*  474:     */   public static void warn(Logger logger, String pattern, float argument)
/*  475:     */   {
/*  476: 925 */     if (logger.isEnabledFor(Level.WARN)) {
/*  477: 926 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  478:     */     }
/*  479:     */   }
/*  480:     */   
/*  481:     */   public static void warn(Logger logger, String pattern, double argument)
/*  482:     */   {
/*  483: 938 */     if (logger.isEnabledFor(Level.WARN)) {
/*  484: 939 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.valueOf(argument)));
/*  485:     */     }
/*  486:     */   }
/*  487:     */   
/*  488:     */   public static void warn(Logger logger, String pattern, Object argument)
/*  489:     */   {
/*  490: 951 */     if (logger.isEnabledFor(Level.WARN)) {
/*  491: 952 */       forcedLog(logger, Level.WARN, format(pattern, argument));
/*  492:     */     }
/*  493:     */   }
/*  494:     */   
/*  495:     */   public static void warn(Logger logger, String pattern, Object arg0, Object arg1)
/*  496:     */   {
/*  497: 965 */     if (logger.isEnabledFor(Level.WARN)) {
/*  498: 966 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.toArray(arg0, arg1)));
/*  499:     */     }
/*  500:     */   }
/*  501:     */   
/*  502:     */   public static void warn(Logger logger, String pattern, Object arg0, Object arg1, Object arg2)
/*  503:     */   {
/*  504: 981 */     if (logger.isEnabledFor(Level.WARN)) {
/*  505: 982 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  506:     */     }
/*  507:     */   }
/*  508:     */   
/*  509:     */   public static void warn(Logger logger, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  510:     */   {
/*  511: 999 */     if (logger.isEnabledFor(Level.WARN)) {
/*  512:1000 */       forcedLog(logger, Level.WARN, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  513:     */     }
/*  514:     */   }
/*  515:     */   
/*  516:     */   public static void log(Logger logger, Level level, String pattern, Object[] parameters)
/*  517:     */   {
/*  518:1016 */     if (logger.isEnabledFor(level)) {
/*  519:1017 */       forcedLog(logger, level, format(pattern, parameters));
/*  520:     */     }
/*  521:     */   }
/*  522:     */   
/*  523:     */   public static void log(Logger logger, Level level, Throwable t, String pattern, Object[] parameters)
/*  524:     */   {
/*  525:1035 */     if (logger.isEnabledFor(level)) {
/*  526:1036 */       forcedLog(logger, level, format(pattern, parameters), t);
/*  527:     */     }
/*  528:     */   }
/*  529:     */   
/*  530:     */   public static void log(Logger logger, Level level, String pattern, Object param1)
/*  531:     */   {
/*  532:1052 */     if (logger.isEnabledFor(level)) {
/*  533:1053 */       forcedLog(logger, level, format(pattern, LogXF.toArray(param1)));
/*  534:     */     }
/*  535:     */   }
/*  536:     */   
/*  537:     */   public static void log(Logger logger, Level level, String pattern, boolean param1)
/*  538:     */   {
/*  539:1069 */     if (logger.isEnabledFor(level)) {
/*  540:1070 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  541:     */     }
/*  542:     */   }
/*  543:     */   
/*  544:     */   public static void log(Logger logger, Level level, String pattern, byte param1)
/*  545:     */   {
/*  546:1087 */     if (logger.isEnabledFor(level)) {
/*  547:1088 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  548:     */     }
/*  549:     */   }
/*  550:     */   
/*  551:     */   public static void log(Logger logger, Level level, String pattern, char param1)
/*  552:     */   {
/*  553:1105 */     if (logger.isEnabledFor(level)) {
/*  554:1106 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  555:     */     }
/*  556:     */   }
/*  557:     */   
/*  558:     */   public static void log(Logger logger, Level level, String pattern, short param1)
/*  559:     */   {
/*  560:1122 */     if (logger.isEnabledFor(level)) {
/*  561:1123 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  562:     */     }
/*  563:     */   }
/*  564:     */   
/*  565:     */   public static void log(Logger logger, Level level, String pattern, int param1)
/*  566:     */   {
/*  567:1139 */     if (logger.isEnabledFor(level)) {
/*  568:1140 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  569:     */     }
/*  570:     */   }
/*  571:     */   
/*  572:     */   public static void log(Logger logger, Level level, String pattern, long param1)
/*  573:     */   {
/*  574:1157 */     if (logger.isEnabledFor(level)) {
/*  575:1158 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  576:     */     }
/*  577:     */   }
/*  578:     */   
/*  579:     */   public static void log(Logger logger, Level level, String pattern, float param1)
/*  580:     */   {
/*  581:1175 */     if (logger.isEnabledFor(level)) {
/*  582:1176 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  583:     */     }
/*  584:     */   }
/*  585:     */   
/*  586:     */   public static void log(Logger logger, Level level, String pattern, double param1)
/*  587:     */   {
/*  588:1193 */     if (logger.isEnabledFor(level)) {
/*  589:1194 */       forcedLog(logger, level, format(pattern, LogXF.toArray(LogXF.valueOf(param1))));
/*  590:     */     }
/*  591:     */   }
/*  592:     */   
/*  593:     */   public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1)
/*  594:     */   {
/*  595:1212 */     if (logger.isEnabledFor(level)) {
/*  596:1213 */       forcedLog(logger, level, format(pattern, LogXF.toArray(arg0, arg1)));
/*  597:     */     }
/*  598:     */   }
/*  599:     */   
/*  600:     */   public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1, Object arg2)
/*  601:     */   {
/*  602:1231 */     if (logger.isEnabledFor(level)) {
/*  603:1232 */       forcedLog(logger, level, format(pattern, LogXF.toArray(arg0, arg1, arg2)));
/*  604:     */     }
/*  605:     */   }
/*  606:     */   
/*  607:     */   public static void log(Logger logger, Level level, String pattern, Object arg0, Object arg1, Object arg2, Object arg3)
/*  608:     */   {
/*  609:1252 */     if (logger.isEnabledFor(level)) {
/*  610:1253 */       forcedLog(logger, level, format(pattern, LogXF.toArray(arg0, arg1, arg2, arg3)));
/*  611:     */     }
/*  612:     */   }
/*  613:     */   
/*  614:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object[] parameters)
/*  615:     */   {
/*  616:1272 */     if (logger.isEnabledFor(level)) {
/*  617:1273 */       forcedLog(logger, level, format(bundleName, key, parameters));
/*  618:     */     }
/*  619:     */   }
/*  620:     */   
/*  621:     */   public static void logrb(Logger logger, Level level, Throwable t, String bundleName, String key, Object[] parameters)
/*  622:     */   {
/*  623:1293 */     if (logger.isEnabledFor(level)) {
/*  624:1294 */       forcedLog(logger, level, format(bundleName, key, parameters), t);
/*  625:     */     }
/*  626:     */   }
/*  627:     */   
/*  628:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object param1)
/*  629:     */   {
/*  630:1312 */     if (logger.isEnabledFor(level)) {
/*  631:1313 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(param1)));
/*  632:     */     }
/*  633:     */   }
/*  634:     */   
/*  635:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, boolean param1)
/*  636:     */   {
/*  637:1331 */     if (logger.isEnabledFor(level)) {
/*  638:1332 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  639:     */     }
/*  640:     */   }
/*  641:     */   
/*  642:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, char param1)
/*  643:     */   {
/*  644:1350 */     if (logger.isEnabledFor(level)) {
/*  645:1351 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  646:     */     }
/*  647:     */   }
/*  648:     */   
/*  649:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, byte param1)
/*  650:     */   {
/*  651:1369 */     if (logger.isEnabledFor(level)) {
/*  652:1370 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  653:     */     }
/*  654:     */   }
/*  655:     */   
/*  656:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, short param1)
/*  657:     */   {
/*  658:1388 */     if (logger.isEnabledFor(level)) {
/*  659:1389 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  660:     */     }
/*  661:     */   }
/*  662:     */   
/*  663:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, int param1)
/*  664:     */   {
/*  665:1407 */     if (logger.isEnabledFor(level)) {
/*  666:1408 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  667:     */     }
/*  668:     */   }
/*  669:     */   
/*  670:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, long param1)
/*  671:     */   {
/*  672:1426 */     if (logger.isEnabledFor(level)) {
/*  673:1427 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  674:     */     }
/*  675:     */   }
/*  676:     */   
/*  677:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, float param1)
/*  678:     */   {
/*  679:1444 */     if (logger.isEnabledFor(level)) {
/*  680:1445 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  681:     */     }
/*  682:     */   }
/*  683:     */   
/*  684:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, double param1)
/*  685:     */   {
/*  686:1464 */     if (logger.isEnabledFor(level)) {
/*  687:1465 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(LogXF.valueOf(param1))));
/*  688:     */     }
/*  689:     */   }
/*  690:     */   
/*  691:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1)
/*  692:     */   {
/*  693:1485 */     if (logger.isEnabledFor(level)) {
/*  694:1486 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(param0, param1)));
/*  695:     */     }
/*  696:     */   }
/*  697:     */   
/*  698:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1, Object param2)
/*  699:     */   {
/*  700:1509 */     if (logger.isEnabledFor(level)) {
/*  701:1510 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(param0, param1, param2)));
/*  702:     */     }
/*  703:     */   }
/*  704:     */   
/*  705:     */   public static void logrb(Logger logger, Level level, String bundleName, String key, Object param0, Object param1, Object param2, Object param3)
/*  706:     */   {
/*  707:1535 */     if (logger.isEnabledFor(level)) {
/*  708:1536 */       forcedLog(logger, level, format(bundleName, key, LogXF.toArray(param0, param1, param2, param3)));
/*  709:     */     }
/*  710:     */   }
/*  711:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.LogSF
 * JD-Core Version:    0.7.0.1
 */