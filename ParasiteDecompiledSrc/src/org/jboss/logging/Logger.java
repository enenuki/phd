/*    1:     */ package org.jboss.logging;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.lang.reflect.Constructor;
/*    5:     */ import java.lang.reflect.InvocationTargetException;
/*    6:     */ import java.util.Locale;
/*    7:     */ 
/*    8:     */ public abstract class Logger
/*    9:     */   implements Serializable, BasicLogger
/*   10:     */ {
/*   11:     */   private static final long serialVersionUID = 4232175575988879434L;
/*   12:  36 */   private static final String FQCN = Logger.class.getName();
/*   13:     */   private final String name;
/*   14:     */   
/*   15:     */   public static enum Level
/*   16:     */   {
/*   17:  42 */     FATAL,  ERROR,  WARN,  INFO,  DEBUG,  TRACE;
/*   18:     */     
/*   19:     */     private Level() {}
/*   20:     */   }
/*   21:     */   
/*   22:     */   protected Logger(String name)
/*   23:     */   {
/*   24:  58 */     this.name = name;
/*   25:     */   }
/*   26:     */   
/*   27:     */   public String getName()
/*   28:     */   {
/*   29:  67 */     return this.name;
/*   30:     */   }
/*   31:     */   
/*   32:     */   protected abstract void doLog(Level paramLevel, String paramString, Object paramObject, Object[] paramArrayOfObject, Throwable paramThrowable);
/*   33:     */   
/*   34:     */   protected abstract void doLogf(Level paramLevel, String paramString1, String paramString2, Object[] paramArrayOfObject, Throwable paramThrowable);
/*   35:     */   
/*   36:     */   public boolean isTraceEnabled()
/*   37:     */   {
/*   38:  98 */     return isEnabled(Level.TRACE);
/*   39:     */   }
/*   40:     */   
/*   41:     */   public void trace(Object message)
/*   42:     */   {
/*   43: 107 */     doLog(Level.TRACE, FQCN, message, null, null);
/*   44:     */   }
/*   45:     */   
/*   46:     */   public void trace(Object message, Throwable t)
/*   47:     */   {
/*   48: 117 */     doLog(Level.TRACE, FQCN, message, null, t);
/*   49:     */   }
/*   50:     */   
/*   51:     */   public void trace(String loggerFqcn, Object message, Throwable t)
/*   52:     */   {
/*   53: 128 */     doLog(Level.TRACE, loggerFqcn, message, null, t);
/*   54:     */   }
/*   55:     */   
/*   56:     */   /**
/*   57:     */    * @deprecated
/*   58:     */    */
/*   59:     */   public void trace(Object message, Object[] params)
/*   60:     */   {
/*   61: 139 */     doLog(Level.TRACE, FQCN, message, params, null);
/*   62:     */   }
/*   63:     */   
/*   64:     */   /**
/*   65:     */    * @deprecated
/*   66:     */    */
/*   67:     */   public void trace(Object message, Object[] params, Throwable t)
/*   68:     */   {
/*   69: 151 */     doLog(Level.TRACE, FQCN, message, params, t);
/*   70:     */   }
/*   71:     */   
/*   72:     */   public void trace(String loggerFqcn, Object message, Object[] params, Throwable t)
/*   73:     */   {
/*   74: 163 */     doLog(Level.TRACE, loggerFqcn, message, params, t);
/*   75:     */   }
/*   76:     */   
/*   77:     */   public void tracev(String format, Object... params)
/*   78:     */   {
/*   79: 173 */     doLog(Level.TRACE, FQCN, format, params, null);
/*   80:     */   }
/*   81:     */   
/*   82:     */   public void tracev(String format, Object param1)
/*   83:     */   {
/*   84: 183 */     if (isEnabled(Level.TRACE)) {
/*   85: 184 */       doLog(Level.TRACE, FQCN, format, new Object[] { param1 }, null);
/*   86:     */     }
/*   87:     */   }
/*   88:     */   
/*   89:     */   public void tracev(String format, Object param1, Object param2)
/*   90:     */   {
/*   91: 196 */     if (isEnabled(Level.TRACE)) {
/*   92: 197 */       doLog(Level.TRACE, FQCN, format, new Object[] { param1, param2 }, null);
/*   93:     */     }
/*   94:     */   }
/*   95:     */   
/*   96:     */   public void tracev(String format, Object param1, Object param2, Object param3)
/*   97:     */   {
/*   98: 210 */     if (isEnabled(Level.TRACE)) {
/*   99: 211 */       doLog(Level.TRACE, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  100:     */     }
/*  101:     */   }
/*  102:     */   
/*  103:     */   public void tracev(Throwable t, String format, Object... params)
/*  104:     */   {
/*  105: 223 */     doLog(Level.TRACE, FQCN, format, params, t);
/*  106:     */   }
/*  107:     */   
/*  108:     */   public void tracev(Throwable t, String format, Object param1)
/*  109:     */   {
/*  110: 234 */     if (isEnabled(Level.TRACE)) {
/*  111: 235 */       doLog(Level.TRACE, FQCN, format, new Object[] { param1 }, t);
/*  112:     */     }
/*  113:     */   }
/*  114:     */   
/*  115:     */   public void tracev(Throwable t, String format, Object param1, Object param2)
/*  116:     */   {
/*  117: 248 */     if (isEnabled(Level.TRACE)) {
/*  118: 249 */       doLog(Level.TRACE, FQCN, format, new Object[] { param1, param2 }, t);
/*  119:     */     }
/*  120:     */   }
/*  121:     */   
/*  122:     */   public void tracev(Throwable t, String format, Object param1, Object param2, Object param3)
/*  123:     */   {
/*  124: 263 */     if (isEnabled(Level.TRACE)) {
/*  125: 264 */       doLog(Level.TRACE, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  126:     */     }
/*  127:     */   }
/*  128:     */   
/*  129:     */   public void tracef(String format, Object... params)
/*  130:     */   {
/*  131: 275 */     doLogf(Level.TRACE, FQCN, format, params, null);
/*  132:     */   }
/*  133:     */   
/*  134:     */   public void tracef(String format, Object param1)
/*  135:     */   {
/*  136: 285 */     if (isEnabled(Level.TRACE)) {
/*  137: 286 */       doLogf(Level.TRACE, FQCN, format, new Object[] { param1 }, null);
/*  138:     */     }
/*  139:     */   }
/*  140:     */   
/*  141:     */   public void tracef(String format, Object param1, Object param2)
/*  142:     */   {
/*  143: 298 */     if (isEnabled(Level.TRACE)) {
/*  144: 299 */       doLogf(Level.TRACE, FQCN, format, new Object[] { param1, param2 }, null);
/*  145:     */     }
/*  146:     */   }
/*  147:     */   
/*  148:     */   public void tracef(String format, Object param1, Object param2, Object param3)
/*  149:     */   {
/*  150: 312 */     if (isEnabled(Level.TRACE)) {
/*  151: 313 */       doLogf(Level.TRACE, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  152:     */     }
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void tracef(Throwable t, String format, Object... params)
/*  156:     */   {
/*  157: 325 */     doLogf(Level.TRACE, FQCN, format, params, t);
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void tracef(Throwable t, String format, Object param1)
/*  161:     */   {
/*  162: 336 */     if (isEnabled(Level.TRACE)) {
/*  163: 337 */       doLogf(Level.TRACE, FQCN, format, new Object[] { param1 }, t);
/*  164:     */     }
/*  165:     */   }
/*  166:     */   
/*  167:     */   public void tracef(Throwable t, String format, Object param1, Object param2)
/*  168:     */   {
/*  169: 350 */     if (isEnabled(Level.TRACE)) {
/*  170: 351 */       doLogf(Level.TRACE, FQCN, format, new Object[] { param1, param2 }, t);
/*  171:     */     }
/*  172:     */   }
/*  173:     */   
/*  174:     */   public void tracef(Throwable t, String format, Object param1, Object param2, Object param3)
/*  175:     */   {
/*  176: 365 */     if (isEnabled(Level.TRACE)) {
/*  177: 366 */       doLogf(Level.TRACE, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  178:     */     }
/*  179:     */   }
/*  180:     */   
/*  181:     */   public boolean isDebugEnabled()
/*  182:     */   {
/*  183: 376 */     return isEnabled(Level.DEBUG);
/*  184:     */   }
/*  185:     */   
/*  186:     */   public void debug(Object message)
/*  187:     */   {
/*  188: 385 */     doLog(Level.DEBUG, FQCN, message, null, null);
/*  189:     */   }
/*  190:     */   
/*  191:     */   public void debug(Object message, Throwable t)
/*  192:     */   {
/*  193: 395 */     doLog(Level.DEBUG, FQCN, message, null, t);
/*  194:     */   }
/*  195:     */   
/*  196:     */   public void debug(String loggerFqcn, Object message, Throwable t)
/*  197:     */   {
/*  198: 406 */     doLog(Level.DEBUG, loggerFqcn, message, null, t);
/*  199:     */   }
/*  200:     */   
/*  201:     */   /**
/*  202:     */    * @deprecated
/*  203:     */    */
/*  204:     */   public void debug(Object message, Object[] params)
/*  205:     */   {
/*  206: 417 */     doLog(Level.DEBUG, FQCN, message, params, null);
/*  207:     */   }
/*  208:     */   
/*  209:     */   /**
/*  210:     */    * @deprecated
/*  211:     */    */
/*  212:     */   public void debug(Object message, Object[] params, Throwable t)
/*  213:     */   {
/*  214: 429 */     doLog(Level.DEBUG, FQCN, message, params, t);
/*  215:     */   }
/*  216:     */   
/*  217:     */   public void debug(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  218:     */   {
/*  219: 441 */     doLog(Level.DEBUG, loggerFqcn, message, params, t);
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void debugv(String format, Object... params)
/*  223:     */   {
/*  224: 451 */     doLog(Level.DEBUG, FQCN, format, params, null);
/*  225:     */   }
/*  226:     */   
/*  227:     */   public void debugv(String format, Object param1)
/*  228:     */   {
/*  229: 461 */     if (isEnabled(Level.DEBUG)) {
/*  230: 462 */       doLog(Level.DEBUG, FQCN, format, new Object[] { param1 }, null);
/*  231:     */     }
/*  232:     */   }
/*  233:     */   
/*  234:     */   public void debugv(String format, Object param1, Object param2)
/*  235:     */   {
/*  236: 474 */     if (isEnabled(Level.DEBUG)) {
/*  237: 475 */       doLog(Level.DEBUG, FQCN, format, new Object[] { param1, param2 }, null);
/*  238:     */     }
/*  239:     */   }
/*  240:     */   
/*  241:     */   public void debugv(String format, Object param1, Object param2, Object param3)
/*  242:     */   {
/*  243: 488 */     if (isEnabled(Level.DEBUG)) {
/*  244: 489 */       doLog(Level.DEBUG, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  245:     */     }
/*  246:     */   }
/*  247:     */   
/*  248:     */   public void debugv(Throwable t, String format, Object... params)
/*  249:     */   {
/*  250: 501 */     doLog(Level.DEBUG, FQCN, format, params, t);
/*  251:     */   }
/*  252:     */   
/*  253:     */   public void debugv(Throwable t, String format, Object param1)
/*  254:     */   {
/*  255: 512 */     if (isEnabled(Level.DEBUG)) {
/*  256: 513 */       doLog(Level.DEBUG, FQCN, format, new Object[] { param1 }, t);
/*  257:     */     }
/*  258:     */   }
/*  259:     */   
/*  260:     */   public void debugv(Throwable t, String format, Object param1, Object param2)
/*  261:     */   {
/*  262: 526 */     if (isEnabled(Level.DEBUG)) {
/*  263: 527 */       doLog(Level.DEBUG, FQCN, format, new Object[] { param1, param2 }, t);
/*  264:     */     }
/*  265:     */   }
/*  266:     */   
/*  267:     */   public void debugv(Throwable t, String format, Object param1, Object param2, Object param3)
/*  268:     */   {
/*  269: 541 */     if (isEnabled(Level.DEBUG)) {
/*  270: 542 */       doLog(Level.DEBUG, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  271:     */     }
/*  272:     */   }
/*  273:     */   
/*  274:     */   public void debugf(String format, Object... params)
/*  275:     */   {
/*  276: 553 */     doLogf(Level.DEBUG, FQCN, format, params, null);
/*  277:     */   }
/*  278:     */   
/*  279:     */   public void debugf(String format, Object param1)
/*  280:     */   {
/*  281: 563 */     if (isEnabled(Level.DEBUG)) {
/*  282: 564 */       doLogf(Level.DEBUG, FQCN, format, new Object[] { param1 }, null);
/*  283:     */     }
/*  284:     */   }
/*  285:     */   
/*  286:     */   public void debugf(String format, Object param1, Object param2)
/*  287:     */   {
/*  288: 576 */     if (isEnabled(Level.DEBUG)) {
/*  289: 577 */       doLogf(Level.DEBUG, FQCN, format, new Object[] { param1, param2 }, null);
/*  290:     */     }
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void debugf(String format, Object param1, Object param2, Object param3)
/*  294:     */   {
/*  295: 590 */     if (isEnabled(Level.DEBUG)) {
/*  296: 591 */       doLogf(Level.DEBUG, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  297:     */     }
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void debugf(Throwable t, String format, Object... params)
/*  301:     */   {
/*  302: 603 */     doLogf(Level.DEBUG, FQCN, format, params, t);
/*  303:     */   }
/*  304:     */   
/*  305:     */   public void debugf(Throwable t, String format, Object param1)
/*  306:     */   {
/*  307: 614 */     if (isEnabled(Level.DEBUG)) {
/*  308: 615 */       doLogf(Level.DEBUG, FQCN, format, new Object[] { param1 }, t);
/*  309:     */     }
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void debugf(Throwable t, String format, Object param1, Object param2)
/*  313:     */   {
/*  314: 628 */     if (isEnabled(Level.DEBUG)) {
/*  315: 629 */       doLogf(Level.DEBUG, FQCN, format, new Object[] { param1, param2 }, t);
/*  316:     */     }
/*  317:     */   }
/*  318:     */   
/*  319:     */   public void debugf(Throwable t, String format, Object param1, Object param2, Object param3)
/*  320:     */   {
/*  321: 643 */     if (isEnabled(Level.DEBUG)) {
/*  322: 644 */       doLogf(Level.DEBUG, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  323:     */     }
/*  324:     */   }
/*  325:     */   
/*  326:     */   public boolean isInfoEnabled()
/*  327:     */   {
/*  328: 654 */     return isEnabled(Level.INFO);
/*  329:     */   }
/*  330:     */   
/*  331:     */   public void info(Object message)
/*  332:     */   {
/*  333: 663 */     doLog(Level.INFO, FQCN, message, null, null);
/*  334:     */   }
/*  335:     */   
/*  336:     */   public void info(Object message, Throwable t)
/*  337:     */   {
/*  338: 673 */     doLog(Level.INFO, FQCN, message, null, t);
/*  339:     */   }
/*  340:     */   
/*  341:     */   public void info(String loggerFqcn, Object message, Throwable t)
/*  342:     */   {
/*  343: 684 */     doLog(Level.INFO, loggerFqcn, message, null, t);
/*  344:     */   }
/*  345:     */   
/*  346:     */   /**
/*  347:     */    * @deprecated
/*  348:     */    */
/*  349:     */   public void info(Object message, Object[] params)
/*  350:     */   {
/*  351: 695 */     doLog(Level.INFO, FQCN, message, params, null);
/*  352:     */   }
/*  353:     */   
/*  354:     */   /**
/*  355:     */    * @deprecated
/*  356:     */    */
/*  357:     */   public void info(Object message, Object[] params, Throwable t)
/*  358:     */   {
/*  359: 707 */     doLog(Level.INFO, FQCN, message, params, t);
/*  360:     */   }
/*  361:     */   
/*  362:     */   public void info(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  363:     */   {
/*  364: 719 */     doLog(Level.INFO, loggerFqcn, message, params, t);
/*  365:     */   }
/*  366:     */   
/*  367:     */   public void infov(String format, Object... params)
/*  368:     */   {
/*  369: 729 */     doLog(Level.INFO, FQCN, format, params, null);
/*  370:     */   }
/*  371:     */   
/*  372:     */   public void infov(String format, Object param1)
/*  373:     */   {
/*  374: 739 */     if (isEnabled(Level.INFO)) {
/*  375: 740 */       doLog(Level.INFO, FQCN, format, new Object[] { param1 }, null);
/*  376:     */     }
/*  377:     */   }
/*  378:     */   
/*  379:     */   public void infov(String format, Object param1, Object param2)
/*  380:     */   {
/*  381: 752 */     if (isEnabled(Level.INFO)) {
/*  382: 753 */       doLog(Level.INFO, FQCN, format, new Object[] { param1, param2 }, null);
/*  383:     */     }
/*  384:     */   }
/*  385:     */   
/*  386:     */   public void infov(String format, Object param1, Object param2, Object param3)
/*  387:     */   {
/*  388: 766 */     if (isEnabled(Level.INFO)) {
/*  389: 767 */       doLog(Level.INFO, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  390:     */     }
/*  391:     */   }
/*  392:     */   
/*  393:     */   public void infov(Throwable t, String format, Object... params)
/*  394:     */   {
/*  395: 779 */     doLog(Level.INFO, FQCN, format, params, t);
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void infov(Throwable t, String format, Object param1)
/*  399:     */   {
/*  400: 790 */     if (isEnabled(Level.INFO)) {
/*  401: 791 */       doLog(Level.INFO, FQCN, format, new Object[] { param1 }, t);
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   public void infov(Throwable t, String format, Object param1, Object param2)
/*  406:     */   {
/*  407: 804 */     if (isEnabled(Level.INFO)) {
/*  408: 805 */       doLog(Level.INFO, FQCN, format, new Object[] { param1, param2 }, t);
/*  409:     */     }
/*  410:     */   }
/*  411:     */   
/*  412:     */   public void infov(Throwable t, String format, Object param1, Object param2, Object param3)
/*  413:     */   {
/*  414: 819 */     if (isEnabled(Level.INFO)) {
/*  415: 820 */       doLog(Level.INFO, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  416:     */     }
/*  417:     */   }
/*  418:     */   
/*  419:     */   public void infof(String format, Object... params)
/*  420:     */   {
/*  421: 831 */     doLogf(Level.INFO, FQCN, format, params, null);
/*  422:     */   }
/*  423:     */   
/*  424:     */   public void infof(String format, Object param1)
/*  425:     */   {
/*  426: 841 */     if (isEnabled(Level.INFO)) {
/*  427: 842 */       doLogf(Level.INFO, FQCN, format, new Object[] { param1 }, null);
/*  428:     */     }
/*  429:     */   }
/*  430:     */   
/*  431:     */   public void infof(String format, Object param1, Object param2)
/*  432:     */   {
/*  433: 854 */     if (isEnabled(Level.INFO)) {
/*  434: 855 */       doLogf(Level.INFO, FQCN, format, new Object[] { param1, param2 }, null);
/*  435:     */     }
/*  436:     */   }
/*  437:     */   
/*  438:     */   public void infof(String format, Object param1, Object param2, Object param3)
/*  439:     */   {
/*  440: 868 */     if (isEnabled(Level.INFO)) {
/*  441: 869 */       doLogf(Level.INFO, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   public void infof(Throwable t, String format, Object... params)
/*  446:     */   {
/*  447: 881 */     doLogf(Level.INFO, FQCN, format, params, t);
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void infof(Throwable t, String format, Object param1)
/*  451:     */   {
/*  452: 892 */     if (isEnabled(Level.INFO)) {
/*  453: 893 */       doLogf(Level.INFO, FQCN, format, new Object[] { param1 }, t);
/*  454:     */     }
/*  455:     */   }
/*  456:     */   
/*  457:     */   public void infof(Throwable t, String format, Object param1, Object param2)
/*  458:     */   {
/*  459: 906 */     if (isEnabled(Level.INFO)) {
/*  460: 907 */       doLogf(Level.INFO, FQCN, format, new Object[] { param1, param2 }, t);
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   public void infof(Throwable t, String format, Object param1, Object param2, Object param3)
/*  465:     */   {
/*  466: 921 */     if (isEnabled(Level.INFO)) {
/*  467: 922 */       doLogf(Level.INFO, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  468:     */     }
/*  469:     */   }
/*  470:     */   
/*  471:     */   public void warn(Object message)
/*  472:     */   {
/*  473: 932 */     doLog(Level.WARN, FQCN, message, null, null);
/*  474:     */   }
/*  475:     */   
/*  476:     */   public void warn(Object message, Throwable t)
/*  477:     */   {
/*  478: 942 */     doLog(Level.WARN, FQCN, message, null, t);
/*  479:     */   }
/*  480:     */   
/*  481:     */   public void warn(String loggerFqcn, Object message, Throwable t)
/*  482:     */   {
/*  483: 953 */     doLog(Level.WARN, loggerFqcn, message, null, t);
/*  484:     */   }
/*  485:     */   
/*  486:     */   /**
/*  487:     */    * @deprecated
/*  488:     */    */
/*  489:     */   public void warn(Object message, Object[] params)
/*  490:     */   {
/*  491: 964 */     doLog(Level.WARN, FQCN, message, params, null);
/*  492:     */   }
/*  493:     */   
/*  494:     */   /**
/*  495:     */    * @deprecated
/*  496:     */    */
/*  497:     */   public void warn(Object message, Object[] params, Throwable t)
/*  498:     */   {
/*  499: 976 */     doLog(Level.WARN, FQCN, message, params, t);
/*  500:     */   }
/*  501:     */   
/*  502:     */   public void warn(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  503:     */   {
/*  504: 988 */     doLog(Level.WARN, loggerFqcn, message, params, t);
/*  505:     */   }
/*  506:     */   
/*  507:     */   public void warnv(String format, Object... params)
/*  508:     */   {
/*  509: 998 */     doLog(Level.WARN, FQCN, format, params, null);
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void warnv(String format, Object param1)
/*  513:     */   {
/*  514:1008 */     if (isEnabled(Level.WARN)) {
/*  515:1009 */       doLog(Level.WARN, FQCN, format, new Object[] { param1 }, null);
/*  516:     */     }
/*  517:     */   }
/*  518:     */   
/*  519:     */   public void warnv(String format, Object param1, Object param2)
/*  520:     */   {
/*  521:1021 */     if (isEnabled(Level.WARN)) {
/*  522:1022 */       doLog(Level.WARN, FQCN, format, new Object[] { param1, param2 }, null);
/*  523:     */     }
/*  524:     */   }
/*  525:     */   
/*  526:     */   public void warnv(String format, Object param1, Object param2, Object param3)
/*  527:     */   {
/*  528:1035 */     if (isEnabled(Level.WARN)) {
/*  529:1036 */       doLog(Level.WARN, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  530:     */     }
/*  531:     */   }
/*  532:     */   
/*  533:     */   public void warnv(Throwable t, String format, Object... params)
/*  534:     */   {
/*  535:1048 */     doLog(Level.WARN, FQCN, format, params, t);
/*  536:     */   }
/*  537:     */   
/*  538:     */   public void warnv(Throwable t, String format, Object param1)
/*  539:     */   {
/*  540:1059 */     if (isEnabled(Level.WARN)) {
/*  541:1060 */       doLog(Level.WARN, FQCN, format, new Object[] { param1 }, t);
/*  542:     */     }
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void warnv(Throwable t, String format, Object param1, Object param2)
/*  546:     */   {
/*  547:1073 */     if (isEnabled(Level.WARN)) {
/*  548:1074 */       doLog(Level.WARN, FQCN, format, new Object[] { param1, param2 }, t);
/*  549:     */     }
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void warnv(Throwable t, String format, Object param1, Object param2, Object param3)
/*  553:     */   {
/*  554:1088 */     if (isEnabled(Level.WARN)) {
/*  555:1089 */       doLog(Level.WARN, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  556:     */     }
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void warnf(String format, Object... params)
/*  560:     */   {
/*  561:1100 */     doLogf(Level.WARN, FQCN, format, params, null);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public void warnf(String format, Object param1)
/*  565:     */   {
/*  566:1110 */     if (isEnabled(Level.WARN)) {
/*  567:1111 */       doLogf(Level.WARN, FQCN, format, new Object[] { param1 }, null);
/*  568:     */     }
/*  569:     */   }
/*  570:     */   
/*  571:     */   public void warnf(String format, Object param1, Object param2)
/*  572:     */   {
/*  573:1123 */     if (isEnabled(Level.WARN)) {
/*  574:1124 */       doLogf(Level.WARN, FQCN, format, new Object[] { param1, param2 }, null);
/*  575:     */     }
/*  576:     */   }
/*  577:     */   
/*  578:     */   public void warnf(String format, Object param1, Object param2, Object param3)
/*  579:     */   {
/*  580:1137 */     if (isEnabled(Level.WARN)) {
/*  581:1138 */       doLogf(Level.WARN, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  582:     */     }
/*  583:     */   }
/*  584:     */   
/*  585:     */   public void warnf(Throwable t, String format, Object... params)
/*  586:     */   {
/*  587:1150 */     doLogf(Level.WARN, FQCN, format, params, t);
/*  588:     */   }
/*  589:     */   
/*  590:     */   public void warnf(Throwable t, String format, Object param1)
/*  591:     */   {
/*  592:1161 */     if (isEnabled(Level.WARN)) {
/*  593:1162 */       doLogf(Level.WARN, FQCN, format, new Object[] { param1 }, t);
/*  594:     */     }
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void warnf(Throwable t, String format, Object param1, Object param2)
/*  598:     */   {
/*  599:1175 */     if (isEnabled(Level.WARN)) {
/*  600:1176 */       doLogf(Level.WARN, FQCN, format, new Object[] { param1, param2 }, t);
/*  601:     */     }
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void warnf(Throwable t, String format, Object param1, Object param2, Object param3)
/*  605:     */   {
/*  606:1190 */     if (isEnabled(Level.WARN)) {
/*  607:1191 */       doLogf(Level.WARN, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  608:     */     }
/*  609:     */   }
/*  610:     */   
/*  611:     */   public void error(Object message)
/*  612:     */   {
/*  613:1201 */     doLog(Level.ERROR, FQCN, message, null, null);
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void error(Object message, Throwable t)
/*  617:     */   {
/*  618:1211 */     doLog(Level.ERROR, FQCN, message, null, t);
/*  619:     */   }
/*  620:     */   
/*  621:     */   public void error(String loggerFqcn, Object message, Throwable t)
/*  622:     */   {
/*  623:1222 */     doLog(Level.ERROR, loggerFqcn, message, null, t);
/*  624:     */   }
/*  625:     */   
/*  626:     */   /**
/*  627:     */    * @deprecated
/*  628:     */    */
/*  629:     */   public void error(Object message, Object[] params)
/*  630:     */   {
/*  631:1233 */     doLog(Level.ERROR, FQCN, message, params, null);
/*  632:     */   }
/*  633:     */   
/*  634:     */   /**
/*  635:     */    * @deprecated
/*  636:     */    */
/*  637:     */   public void error(Object message, Object[] params, Throwable t)
/*  638:     */   {
/*  639:1245 */     doLog(Level.ERROR, FQCN, message, params, t);
/*  640:     */   }
/*  641:     */   
/*  642:     */   public void error(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  643:     */   {
/*  644:1257 */     doLog(Level.ERROR, loggerFqcn, message, params, t);
/*  645:     */   }
/*  646:     */   
/*  647:     */   public void errorv(String format, Object... params)
/*  648:     */   {
/*  649:1267 */     doLog(Level.ERROR, FQCN, format, params, null);
/*  650:     */   }
/*  651:     */   
/*  652:     */   public void errorv(String format, Object param1)
/*  653:     */   {
/*  654:1277 */     if (isEnabled(Level.ERROR)) {
/*  655:1278 */       doLog(Level.ERROR, FQCN, format, new Object[] { param1 }, null);
/*  656:     */     }
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void errorv(String format, Object param1, Object param2)
/*  660:     */   {
/*  661:1290 */     if (isEnabled(Level.ERROR)) {
/*  662:1291 */       doLog(Level.ERROR, FQCN, format, new Object[] { param1, param2 }, null);
/*  663:     */     }
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void errorv(String format, Object param1, Object param2, Object param3)
/*  667:     */   {
/*  668:1304 */     if (isEnabled(Level.ERROR)) {
/*  669:1305 */       doLog(Level.ERROR, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  670:     */     }
/*  671:     */   }
/*  672:     */   
/*  673:     */   public void errorv(Throwable t, String format, Object... params)
/*  674:     */   {
/*  675:1317 */     doLog(Level.ERROR, FQCN, format, params, t);
/*  676:     */   }
/*  677:     */   
/*  678:     */   public void errorv(Throwable t, String format, Object param1)
/*  679:     */   {
/*  680:1328 */     if (isEnabled(Level.ERROR)) {
/*  681:1329 */       doLog(Level.ERROR, FQCN, format, new Object[] { param1 }, t);
/*  682:     */     }
/*  683:     */   }
/*  684:     */   
/*  685:     */   public void errorv(Throwable t, String format, Object param1, Object param2)
/*  686:     */   {
/*  687:1342 */     if (isEnabled(Level.ERROR)) {
/*  688:1343 */       doLog(Level.ERROR, FQCN, format, new Object[] { param1, param2 }, t);
/*  689:     */     }
/*  690:     */   }
/*  691:     */   
/*  692:     */   public void errorv(Throwable t, String format, Object param1, Object param2, Object param3)
/*  693:     */   {
/*  694:1357 */     if (isEnabled(Level.ERROR)) {
/*  695:1358 */       doLog(Level.ERROR, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  696:     */     }
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void errorf(String format, Object... params)
/*  700:     */   {
/*  701:1369 */     doLogf(Level.ERROR, FQCN, format, params, null);
/*  702:     */   }
/*  703:     */   
/*  704:     */   public void errorf(String format, Object param1)
/*  705:     */   {
/*  706:1379 */     if (isEnabled(Level.ERROR)) {
/*  707:1380 */       doLogf(Level.ERROR, FQCN, format, new Object[] { param1 }, null);
/*  708:     */     }
/*  709:     */   }
/*  710:     */   
/*  711:     */   public void errorf(String format, Object param1, Object param2)
/*  712:     */   {
/*  713:1392 */     if (isEnabled(Level.ERROR)) {
/*  714:1393 */       doLogf(Level.ERROR, FQCN, format, new Object[] { param1, param2 }, null);
/*  715:     */     }
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void errorf(String format, Object param1, Object param2, Object param3)
/*  719:     */   {
/*  720:1406 */     if (isEnabled(Level.ERROR)) {
/*  721:1407 */       doLogf(Level.ERROR, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  722:     */     }
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void errorf(Throwable t, String format, Object... params)
/*  726:     */   {
/*  727:1419 */     doLogf(Level.ERROR, FQCN, format, params, t);
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void errorf(Throwable t, String format, Object param1)
/*  731:     */   {
/*  732:1430 */     if (isEnabled(Level.ERROR)) {
/*  733:1431 */       doLogf(Level.ERROR, FQCN, format, new Object[] { param1 }, t);
/*  734:     */     }
/*  735:     */   }
/*  736:     */   
/*  737:     */   public void errorf(Throwable t, String format, Object param1, Object param2)
/*  738:     */   {
/*  739:1444 */     if (isEnabled(Level.ERROR)) {
/*  740:1445 */       doLogf(Level.ERROR, FQCN, format, new Object[] { param1, param2 }, t);
/*  741:     */     }
/*  742:     */   }
/*  743:     */   
/*  744:     */   public void errorf(Throwable t, String format, Object param1, Object param2, Object param3)
/*  745:     */   {
/*  746:1459 */     if (isEnabled(Level.ERROR)) {
/*  747:1460 */       doLogf(Level.ERROR, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  748:     */     }
/*  749:     */   }
/*  750:     */   
/*  751:     */   public void fatal(Object message)
/*  752:     */   {
/*  753:1470 */     doLog(Level.FATAL, FQCN, message, null, null);
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void fatal(Object message, Throwable t)
/*  757:     */   {
/*  758:1480 */     doLog(Level.FATAL, FQCN, message, null, t);
/*  759:     */   }
/*  760:     */   
/*  761:     */   public void fatal(String loggerFqcn, Object message, Throwable t)
/*  762:     */   {
/*  763:1491 */     doLog(Level.FATAL, loggerFqcn, message, null, t);
/*  764:     */   }
/*  765:     */   
/*  766:     */   /**
/*  767:     */    * @deprecated
/*  768:     */    */
/*  769:     */   public void fatal(Object message, Object[] params)
/*  770:     */   {
/*  771:1502 */     doLog(Level.FATAL, FQCN, message, params, null);
/*  772:     */   }
/*  773:     */   
/*  774:     */   /**
/*  775:     */    * @deprecated
/*  776:     */    */
/*  777:     */   public void fatal(Object message, Object[] params, Throwable t)
/*  778:     */   {
/*  779:1514 */     doLog(Level.FATAL, FQCN, message, params, t);
/*  780:     */   }
/*  781:     */   
/*  782:     */   public void fatal(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  783:     */   {
/*  784:1526 */     doLog(Level.FATAL, loggerFqcn, message, params, t);
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void fatalv(String format, Object... params)
/*  788:     */   {
/*  789:1536 */     doLog(Level.FATAL, FQCN, format, params, null);
/*  790:     */   }
/*  791:     */   
/*  792:     */   public void fatalv(String format, Object param1)
/*  793:     */   {
/*  794:1546 */     if (isEnabled(Level.FATAL)) {
/*  795:1547 */       doLog(Level.FATAL, FQCN, format, new Object[] { param1 }, null);
/*  796:     */     }
/*  797:     */   }
/*  798:     */   
/*  799:     */   public void fatalv(String format, Object param1, Object param2)
/*  800:     */   {
/*  801:1559 */     if (isEnabled(Level.FATAL)) {
/*  802:1560 */       doLog(Level.FATAL, FQCN, format, new Object[] { param1, param2 }, null);
/*  803:     */     }
/*  804:     */   }
/*  805:     */   
/*  806:     */   public void fatalv(String format, Object param1, Object param2, Object param3)
/*  807:     */   {
/*  808:1573 */     if (isEnabled(Level.FATAL)) {
/*  809:1574 */       doLog(Level.FATAL, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  810:     */     }
/*  811:     */   }
/*  812:     */   
/*  813:     */   public void fatalv(Throwable t, String format, Object... params)
/*  814:     */   {
/*  815:1586 */     doLog(Level.FATAL, FQCN, format, params, t);
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void fatalv(Throwable t, String format, Object param1)
/*  819:     */   {
/*  820:1597 */     if (isEnabled(Level.FATAL)) {
/*  821:1598 */       doLog(Level.FATAL, FQCN, format, new Object[] { param1 }, t);
/*  822:     */     }
/*  823:     */   }
/*  824:     */   
/*  825:     */   public void fatalv(Throwable t, String format, Object param1, Object param2)
/*  826:     */   {
/*  827:1611 */     if (isEnabled(Level.FATAL)) {
/*  828:1612 */       doLog(Level.FATAL, FQCN, format, new Object[] { param1, param2 }, t);
/*  829:     */     }
/*  830:     */   }
/*  831:     */   
/*  832:     */   public void fatalv(Throwable t, String format, Object param1, Object param2, Object param3)
/*  833:     */   {
/*  834:1626 */     if (isEnabled(Level.FATAL)) {
/*  835:1627 */       doLog(Level.FATAL, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  836:     */     }
/*  837:     */   }
/*  838:     */   
/*  839:     */   public void fatalf(String format, Object... params)
/*  840:     */   {
/*  841:1638 */     doLogf(Level.FATAL, FQCN, format, params, null);
/*  842:     */   }
/*  843:     */   
/*  844:     */   public void fatalf(String format, Object param1)
/*  845:     */   {
/*  846:1648 */     if (isEnabled(Level.FATAL)) {
/*  847:1649 */       doLogf(Level.FATAL, FQCN, format, new Object[] { param1 }, null);
/*  848:     */     }
/*  849:     */   }
/*  850:     */   
/*  851:     */   public void fatalf(String format, Object param1, Object param2)
/*  852:     */   {
/*  853:1661 */     if (isEnabled(Level.FATAL)) {
/*  854:1662 */       doLogf(Level.FATAL, FQCN, format, new Object[] { param1, param2 }, null);
/*  855:     */     }
/*  856:     */   }
/*  857:     */   
/*  858:     */   public void fatalf(String format, Object param1, Object param2, Object param3)
/*  859:     */   {
/*  860:1675 */     if (isEnabled(Level.FATAL)) {
/*  861:1676 */       doLogf(Level.FATAL, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  862:     */     }
/*  863:     */   }
/*  864:     */   
/*  865:     */   public void fatalf(Throwable t, String format, Object... params)
/*  866:     */   {
/*  867:1688 */     doLogf(Level.FATAL, FQCN, format, params, t);
/*  868:     */   }
/*  869:     */   
/*  870:     */   public void fatalf(Throwable t, String format, Object param1)
/*  871:     */   {
/*  872:1699 */     if (isEnabled(Level.FATAL)) {
/*  873:1700 */       doLogf(Level.FATAL, FQCN, format, new Object[] { param1 }, t);
/*  874:     */     }
/*  875:     */   }
/*  876:     */   
/*  877:     */   public void fatalf(Throwable t, String format, Object param1, Object param2)
/*  878:     */   {
/*  879:1713 */     if (isEnabled(Level.FATAL)) {
/*  880:1714 */       doLogf(Level.FATAL, FQCN, format, new Object[] { param1, param2 }, t);
/*  881:     */     }
/*  882:     */   }
/*  883:     */   
/*  884:     */   public void fatalf(Throwable t, String format, Object param1, Object param2, Object param3)
/*  885:     */   {
/*  886:1728 */     if (isEnabled(Level.FATAL)) {
/*  887:1729 */       doLogf(Level.FATAL, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  888:     */     }
/*  889:     */   }
/*  890:     */   
/*  891:     */   public void log(Level level, Object message)
/*  892:     */   {
/*  893:1740 */     doLog(level, FQCN, message, null, null);
/*  894:     */   }
/*  895:     */   
/*  896:     */   public void log(Level level, Object message, Throwable t)
/*  897:     */   {
/*  898:1751 */     doLog(level, FQCN, message, null, t);
/*  899:     */   }
/*  900:     */   
/*  901:     */   public void log(Level level, String loggerFqcn, Object message, Throwable t)
/*  902:     */   {
/*  903:1763 */     doLog(level, loggerFqcn, message, null, t);
/*  904:     */   }
/*  905:     */   
/*  906:     */   /**
/*  907:     */    * @deprecated
/*  908:     */    */
/*  909:     */   public void log(Level level, Object message, Object[] params)
/*  910:     */   {
/*  911:1775 */     doLog(level, FQCN, message, params, null);
/*  912:     */   }
/*  913:     */   
/*  914:     */   /**
/*  915:     */    * @deprecated
/*  916:     */    */
/*  917:     */   public void log(Level level, Object message, Object[] params, Throwable t)
/*  918:     */   {
/*  919:1788 */     doLog(level, FQCN, message, params, t);
/*  920:     */   }
/*  921:     */   
/*  922:     */   public void log(String loggerFqcn, Level level, Object message, Object[] params, Throwable t)
/*  923:     */   {
/*  924:1801 */     doLog(level, loggerFqcn, message, params, t);
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void logv(Level level, String format, Object... params)
/*  928:     */   {
/*  929:1812 */     doLog(level, FQCN, format, params, null);
/*  930:     */   }
/*  931:     */   
/*  932:     */   public void logv(Level level, String format, Object param1)
/*  933:     */   {
/*  934:1823 */     if (isEnabled(level)) {
/*  935:1824 */       doLog(level, FQCN, format, new Object[] { param1 }, null);
/*  936:     */     }
/*  937:     */   }
/*  938:     */   
/*  939:     */   public void logv(Level level, String format, Object param1, Object param2)
/*  940:     */   {
/*  941:1837 */     if (isEnabled(level)) {
/*  942:1838 */       doLog(level, FQCN, format, new Object[] { param1, param2 }, null);
/*  943:     */     }
/*  944:     */   }
/*  945:     */   
/*  946:     */   public void logv(Level level, String format, Object param1, Object param2, Object param3)
/*  947:     */   {
/*  948:1852 */     if (isEnabled(level)) {
/*  949:1853 */       doLog(level, FQCN, format, new Object[] { param1, param2, param3 }, null);
/*  950:     */     }
/*  951:     */   }
/*  952:     */   
/*  953:     */   public void logv(Level level, Throwable t, String format, Object... params)
/*  954:     */   {
/*  955:1866 */     doLog(level, FQCN, format, params, t);
/*  956:     */   }
/*  957:     */   
/*  958:     */   public void logv(Level level, Throwable t, String format, Object param1)
/*  959:     */   {
/*  960:1878 */     if (isEnabled(level)) {
/*  961:1879 */       doLog(level, FQCN, format, new Object[] { param1 }, t);
/*  962:     */     }
/*  963:     */   }
/*  964:     */   
/*  965:     */   public void logv(Level level, Throwable t, String format, Object param1, Object param2)
/*  966:     */   {
/*  967:1893 */     if (isEnabled(level)) {
/*  968:1894 */       doLog(level, FQCN, format, new Object[] { param1, param2 }, t);
/*  969:     */     }
/*  970:     */   }
/*  971:     */   
/*  972:     */   public void logv(Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/*  973:     */   {
/*  974:1909 */     if (isEnabled(level)) {
/*  975:1910 */       doLog(level, FQCN, format, new Object[] { param1, param2, param3 }, t);
/*  976:     */     }
/*  977:     */   }
/*  978:     */   
/*  979:     */   public void logv(String loggerFqcn, Level level, Throwable t, String format, Object... params)
/*  980:     */   {
/*  981:1924 */     doLog(level, loggerFqcn, format, params, t);
/*  982:     */   }
/*  983:     */   
/*  984:     */   public void logv(String loggerFqcn, Level level, Throwable t, String format, Object param1)
/*  985:     */   {
/*  986:1937 */     if (isEnabled(level)) {
/*  987:1938 */       doLog(level, loggerFqcn, format, new Object[] { param1 }, t);
/*  988:     */     }
/*  989:     */   }
/*  990:     */   
/*  991:     */   public void logv(String loggerFqcn, Level level, Throwable t, String format, Object param1, Object param2)
/*  992:     */   {
/*  993:1953 */     if (isEnabled(level)) {
/*  994:1954 */       doLog(level, loggerFqcn, format, new Object[] { param1, param2 }, t);
/*  995:     */     }
/*  996:     */   }
/*  997:     */   
/*  998:     */   public void logv(String loggerFqcn, Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/*  999:     */   {
/* 1000:1970 */     if (isEnabled(level)) {
/* 1001:1971 */       doLog(level, loggerFqcn, format, new Object[] { param1, param2, param3 }, t);
/* 1002:     */     }
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public void logf(Level level, String format, Object... params)
/* 1006:     */   {
/* 1007:1983 */     doLogf(level, FQCN, format, params, null);
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public void logf(Level level, String format, Object param1)
/* 1011:     */   {
/* 1012:1994 */     if (isEnabled(level)) {
/* 1013:1995 */       doLogf(level, FQCN, format, new Object[] { param1 }, null);
/* 1014:     */     }
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public void logf(Level level, String format, Object param1, Object param2)
/* 1018:     */   {
/* 1019:2008 */     if (isEnabled(level)) {
/* 1020:2009 */       doLogf(level, FQCN, format, new Object[] { param1, param2 }, null);
/* 1021:     */     }
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public void logf(Level level, String format, Object param1, Object param2, Object param3)
/* 1025:     */   {
/* 1026:2023 */     if (isEnabled(level)) {
/* 1027:2024 */       doLogf(level, FQCN, format, new Object[] { param1, param2, param3 }, null);
/* 1028:     */     }
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public void logf(Level level, Throwable t, String format, Object... params)
/* 1032:     */   {
/* 1033:2037 */     doLogf(level, FQCN, format, params, t);
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void logf(Level level, Throwable t, String format, Object param1)
/* 1037:     */   {
/* 1038:2049 */     if (isEnabled(level)) {
/* 1039:2050 */       doLogf(level, FQCN, format, new Object[] { param1 }, t);
/* 1040:     */     }
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   public void logf(Level level, Throwable t, String format, Object param1, Object param2)
/* 1044:     */   {
/* 1045:2064 */     if (isEnabled(level)) {
/* 1046:2065 */       doLogf(level, FQCN, format, new Object[] { param1, param2 }, t);
/* 1047:     */     }
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public void logf(Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 1051:     */   {
/* 1052:2080 */     if (isEnabled(level)) {
/* 1053:2081 */       doLogf(level, FQCN, format, new Object[] { param1, param2, param3 }, t);
/* 1054:     */     }
/* 1055:     */   }
/* 1056:     */   
/* 1057:     */   public void logf(String loggerFqcn, Level level, Throwable t, String format, Object param1)
/* 1058:     */   {
/* 1059:2095 */     if (isEnabled(level)) {
/* 1060:2096 */       doLogf(level, loggerFqcn, format, new Object[] { param1 }, t);
/* 1061:     */     }
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public void logf(String loggerFqcn, Level level, Throwable t, String format, Object param1, Object param2)
/* 1065:     */   {
/* 1066:2111 */     if (isEnabled(level)) {
/* 1067:2112 */       doLogf(level, loggerFqcn, format, new Object[] { param1, param2 }, t);
/* 1068:     */     }
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public void logf(String loggerFqcn, Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 1072:     */   {
/* 1073:2128 */     if (isEnabled(level)) {
/* 1074:2129 */       doLogf(level, loggerFqcn, format, new Object[] { param1, param2, param3 }, t);
/* 1075:     */     }
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public void logf(String loggerFqcn, Level level, Throwable t, String format, Object... params)
/* 1079:     */   {
/* 1080:2143 */     doLogf(level, loggerFqcn, format, params, t);
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   protected final Object writeReplace()
/* 1084:     */   {
/* 1085:2152 */     return new SerializedLogger(this.name);
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   public static Logger getLogger(String name)
/* 1089:     */   {
/* 1090:2163 */     return LoggerProviders.PROVIDER.getLogger(name);
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public static Logger getLogger(String name, String suffix)
/* 1094:     */   {
/* 1095:2177 */     return getLogger(name + "." + suffix);
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   public static Logger getLogger(Class<?> clazz)
/* 1099:     */   {
/* 1100:2188 */     return getLogger(clazz.getName());
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public static Logger getLogger(Class<?> clazz, String suffix)
/* 1104:     */   {
/* 1105:2202 */     return getLogger(clazz.getName(), suffix);
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public static <T> T getMessageLogger(Class<T> type, String category)
/* 1109:     */   {
/* 1110:2214 */     return getMessageLogger(type, category, Locale.getDefault());
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public static <T> T getMessageLogger(Class<T> type, String category, Locale locale)
/* 1114:     */   {
/* 1115:2227 */     String language = locale.getLanguage();
/* 1116:2228 */     String country = locale.getCountry();
/* 1117:2229 */     String variant = locale.getVariant();
/* 1118:     */     
/* 1119:2231 */     Class<? extends T> loggerClass = null;
/* 1120:2232 */     if ((variant != null) && (variant.length() > 0)) {
/* 1121:     */       try
/* 1122:     */       {
/* 1123:2233 */         loggerClass = Class.forName(join(type.getName(), "$logger", language, country, variant), true, type.getClassLoader()).asSubclass(type);
/* 1124:     */       }
/* 1125:     */       catch (ClassNotFoundException e) {}
/* 1126:     */     }
/* 1127:2237 */     if ((loggerClass == null) && (country != null) && (country.length() > 0)) {
/* 1128:     */       try
/* 1129:     */       {
/* 1130:2238 */         loggerClass = Class.forName(join(type.getName(), "$logger", language, country, null), true, type.getClassLoader()).asSubclass(type);
/* 1131:     */       }
/* 1132:     */       catch (ClassNotFoundException e) {}
/* 1133:     */     }
/* 1134:2242 */     if ((loggerClass == null) && (language != null) && (language.length() > 0)) {
/* 1135:     */       try
/* 1136:     */       {
/* 1137:2243 */         loggerClass = Class.forName(join(type.getName(), "$logger", language, null, null), true, type.getClassLoader()).asSubclass(type);
/* 1138:     */       }
/* 1139:     */       catch (ClassNotFoundException e) {}
/* 1140:     */     }
/* 1141:2247 */     if (loggerClass == null) {
/* 1142:     */       try
/* 1143:     */       {
/* 1144:2248 */         loggerClass = Class.forName(join(type.getName(), "$logger", null, null, null), true, type.getClassLoader()).asSubclass(type);
/* 1145:     */       }
/* 1146:     */       catch (ClassNotFoundException e)
/* 1147:     */       {
/* 1148:2250 */         throw new IllegalArgumentException("Invalid logger " + type + " (implementation not found)");
/* 1149:     */       }
/* 1150:     */     }
/* 1151:     */     Constructor<? extends T> constructor;
/* 1152:     */     try
/* 1153:     */     {
/* 1154:2254 */       constructor = loggerClass.getConstructor(new Class[] { Logger.class });
/* 1155:     */     }
/* 1156:     */     catch (NoSuchMethodException e)
/* 1157:     */     {
/* 1158:2256 */       throw new IllegalArgumentException("Logger implementation " + loggerClass + " has no matching constructor");
/* 1159:     */     }
/* 1160:     */     try
/* 1161:     */     {
/* 1162:2259 */       return constructor.newInstance(new Object[] { getLogger(category) });
/* 1163:     */     }
/* 1164:     */     catch (InstantiationException e)
/* 1165:     */     {
/* 1166:2261 */       throw new IllegalArgumentException("Logger implementation " + loggerClass + " could not be instantiated", e);
/* 1167:     */     }
/* 1168:     */     catch (IllegalAccessException e)
/* 1169:     */     {
/* 1170:2263 */       throw new IllegalArgumentException("Logger implementation " + loggerClass + " could not be instantiated", e);
/* 1171:     */     }
/* 1172:     */     catch (InvocationTargetException e)
/* 1173:     */     {
/* 1174:2265 */       throw new IllegalArgumentException("Logger implementation " + loggerClass + " could not be instantiated", e.getCause());
/* 1175:     */     }
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   private static String join(String interfaceName, String a, String b, String c, String d)
/* 1179:     */   {
/* 1180:2270 */     StringBuilder build = new StringBuilder();
/* 1181:2271 */     build.append(interfaceName).append('_').append(a);
/* 1182:2272 */     if ((b != null) && (b.length() > 0))
/* 1183:     */     {
/* 1184:2273 */       build.append('_');
/* 1185:2274 */       build.append(b);
/* 1186:     */     }
/* 1187:2276 */     if ((c != null) && (c.length() > 0))
/* 1188:     */     {
/* 1189:2277 */       build.append('_');
/* 1190:2278 */       build.append(c);
/* 1191:     */     }
/* 1192:2280 */     if ((d != null) && (d.length() > 0))
/* 1193:     */     {
/* 1194:2281 */       build.append('_');
/* 1195:2282 */       build.append(d);
/* 1196:     */     }
/* 1197:2284 */     return build.toString();
/* 1198:     */   }
/* 1199:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.Logger
 * JD-Core Version:    0.7.0.1
 */