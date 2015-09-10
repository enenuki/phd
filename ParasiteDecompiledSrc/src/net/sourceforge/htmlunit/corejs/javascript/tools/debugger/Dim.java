/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.debugger;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileInputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.io.InputStreamReader;
/*    8:     */ import java.io.PrintStream;
/*    9:     */ import java.net.URL;
/*   10:     */ import java.util.Collections;
/*   11:     */ import java.util.HashMap;
/*   12:     */ import java.util.Map;
/*   13:     */ import java.util.Set;
/*   14:     */ import net.sourceforge.htmlunit.corejs.javascript.Callable;
/*   15:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   16:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*   17:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*   18:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory.Listener;
/*   19:     */ import net.sourceforge.htmlunit.corejs.javascript.ImporterTopLevel;
/*   20:     */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*   21:     */ import net.sourceforge.htmlunit.corejs.javascript.NativeCall;
/*   22:     */ import net.sourceforge.htmlunit.corejs.javascript.ObjArray;
/*   23:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   24:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   25:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   26:     */ import net.sourceforge.htmlunit.corejs.javascript.SecurityUtilities;
/*   27:     */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*   28:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebugFrame;
/*   29:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableObject;
/*   30:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/*   31:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.Debugger;
/*   32:     */ 
/*   33:     */ public class Dim
/*   34:     */ {
/*   35:     */   public static final int STEP_OVER = 0;
/*   36:     */   public static final int STEP_INTO = 1;
/*   37:     */   public static final int STEP_OUT = 2;
/*   38:     */   public static final int GO = 3;
/*   39:     */   public static final int BREAK = 4;
/*   40:     */   public static final int EXIT = 5;
/*   41:     */   private static final int IPROXY_DEBUG = 0;
/*   42:     */   private static final int IPROXY_LISTEN = 1;
/*   43:     */   private static final int IPROXY_COMPILE_SCRIPT = 2;
/*   44:     */   private static final int IPROXY_EVAL_SCRIPT = 3;
/*   45:     */   private static final int IPROXY_STRING_IS_COMPILABLE = 4;
/*   46:     */   private static final int IPROXY_OBJECT_TO_STRING = 5;
/*   47:     */   private static final int IPROXY_OBJECT_PROPERTY = 6;
/*   48:     */   private static final int IPROXY_OBJECT_IDS = 7;
/*   49:     */   private GuiCallback callback;
/*   50:     */   private boolean breakFlag;
/*   51:     */   private ScopeProvider scopeProvider;
/*   52:     */   private SourceProvider sourceProvider;
/*   53:     */   private int frameIndex;
/*   54:     */   private volatile ContextData interruptedContextData;
/*   55:     */   private ContextFactory contextFactory;
/*   56:     */   private Object monitor;
/*   57:     */   private Object eventThreadMonitor;
/*   58:     */   private volatile int returnValue;
/*   59:     */   private boolean insideInterruptLoop;
/*   60:     */   private String evalRequest;
/*   61:     */   private StackFrame evalFrame;
/*   62:     */   private String evalResult;
/*   63:     */   private boolean breakOnExceptions;
/*   64:     */   private boolean breakOnEnter;
/*   65:     */   private boolean breakOnReturn;
/*   66:     */   private final Map<String, SourceInfo> urlToSourceInfo;
/*   67:     */   private final Map<String, FunctionSource> functionNames;
/*   68:     */   private final Map<DebuggableScript, FunctionSource> functionToSource;
/*   69:     */   private DimIProxy listener;
/*   70:     */   
/*   71:     */   public Dim()
/*   72:     */   {
/*   73:  97 */     this.frameIndex = -1;
/*   74:     */     
/*   75:     */ 
/*   76:     */ 
/*   77:     */ 
/*   78:     */ 
/*   79:     */ 
/*   80:     */ 
/*   81:     */ 
/*   82:     */ 
/*   83:     */ 
/*   84:     */ 
/*   85:     */ 
/*   86:     */ 
/*   87:     */ 
/*   88:     */ 
/*   89: 113 */     this.monitor = new Object();
/*   90:     */     
/*   91:     */ 
/*   92:     */ 
/*   93:     */ 
/*   94:     */ 
/*   95: 119 */     this.eventThreadMonitor = new Object();
/*   96:     */     
/*   97:     */ 
/*   98:     */ 
/*   99:     */ 
/*  100: 124 */     this.returnValue = -1;
/*  101:     */     
/*  102:     */ 
/*  103:     */ 
/*  104:     */ 
/*  105:     */ 
/*  106:     */ 
/*  107:     */ 
/*  108:     */ 
/*  109:     */ 
/*  110:     */ 
/*  111:     */ 
/*  112:     */ 
/*  113:     */ 
/*  114:     */ 
/*  115:     */ 
/*  116:     */ 
/*  117:     */ 
/*  118:     */ 
/*  119:     */ 
/*  120:     */ 
/*  121:     */ 
/*  122:     */ 
/*  123:     */ 
/*  124:     */ 
/*  125:     */ 
/*  126:     */ 
/*  127:     */ 
/*  128:     */ 
/*  129:     */ 
/*  130:     */ 
/*  131:     */ 
/*  132:     */ 
/*  133:     */ 
/*  134:     */ 
/*  135:     */ 
/*  136:     */ 
/*  137:     */ 
/*  138:     */ 
/*  139:     */ 
/*  140:     */ 
/*  141:     */ 
/*  142: 166 */     this.urlToSourceInfo = Collections.synchronizedMap(new HashMap());
/*  143:     */     
/*  144:     */ 
/*  145:     */ 
/*  146:     */ 
/*  147:     */ 
/*  148: 172 */     this.functionNames = Collections.synchronizedMap(new HashMap());
/*  149:     */     
/*  150:     */ 
/*  151:     */ 
/*  152:     */ 
/*  153:     */ 
/*  154: 178 */     this.functionToSource = Collections.synchronizedMap(new HashMap());
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void setGuiCallback(GuiCallback callback)
/*  158:     */   {
/*  159: 190 */     this.callback = callback;
/*  160:     */   }
/*  161:     */   
/*  162:     */   public void setBreak()
/*  163:     */   {
/*  164: 197 */     this.breakFlag = true;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public void setScopeProvider(ScopeProvider scopeProvider)
/*  168:     */   {
/*  169: 204 */     this.scopeProvider = scopeProvider;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public void setSourceProvider(SourceProvider sourceProvider)
/*  173:     */   {
/*  174: 211 */     this.sourceProvider = sourceProvider;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public void contextSwitch(int frameIndex)
/*  178:     */   {
/*  179: 218 */     this.frameIndex = frameIndex;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void setBreakOnExceptions(boolean breakOnExceptions)
/*  183:     */   {
/*  184: 225 */     this.breakOnExceptions = breakOnExceptions;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public void setBreakOnEnter(boolean breakOnEnter)
/*  188:     */   {
/*  189: 232 */     this.breakOnEnter = breakOnEnter;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public void setBreakOnReturn(boolean breakOnReturn)
/*  193:     */   {
/*  194: 239 */     this.breakOnReturn = breakOnReturn;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public void attachTo(ContextFactory factory)
/*  198:     */   {
/*  199: 246 */     detach();
/*  200: 247 */     this.contextFactory = factory;
/*  201: 248 */     this.listener = new DimIProxy(this, 1, null);
/*  202: 249 */     factory.addListener(this.listener);
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void detach()
/*  206:     */   {
/*  207: 256 */     if (this.listener != null)
/*  208:     */     {
/*  209: 257 */       this.contextFactory.removeListener(this.listener);
/*  210: 258 */       this.contextFactory = null;
/*  211: 259 */       this.listener = null;
/*  212:     */     }
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void dispose()
/*  216:     */   {
/*  217: 267 */     detach();
/*  218:     */   }
/*  219:     */   
/*  220:     */   private FunctionSource getFunctionSource(DebuggableScript fnOrScript)
/*  221:     */   {
/*  222: 274 */     FunctionSource fsource = functionSource(fnOrScript);
/*  223: 275 */     if (fsource == null)
/*  224:     */     {
/*  225: 276 */       String url = getNormalizedUrl(fnOrScript);
/*  226: 277 */       SourceInfo si = sourceInfo(url);
/*  227: 278 */       if ((si == null) && 
/*  228: 279 */         (!fnOrScript.isGeneratedScript()))
/*  229:     */       {
/*  230: 281 */         String source = loadSource(url);
/*  231: 282 */         if (source != null)
/*  232:     */         {
/*  233: 283 */           DebuggableScript top = fnOrScript;
/*  234:     */           for (;;)
/*  235:     */           {
/*  236: 285 */             DebuggableScript parent = top.getParent();
/*  237: 286 */             if (parent == null) {
/*  238:     */               break;
/*  239:     */             }
/*  240: 289 */             top = parent;
/*  241:     */           }
/*  242: 291 */           registerTopScript(top, source);
/*  243: 292 */           fsource = functionSource(fnOrScript);
/*  244:     */         }
/*  245:     */       }
/*  246:     */     }
/*  247: 297 */     return fsource;
/*  248:     */   }
/*  249:     */   
/*  250:     */   private String loadSource(String sourceUrl)
/*  251:     */   {
/*  252: 304 */     String source = null;
/*  253: 305 */     int hash = sourceUrl.indexOf('#');
/*  254: 306 */     if (hash >= 0) {
/*  255: 307 */       sourceUrl = sourceUrl.substring(0, hash);
/*  256:     */     }
/*  257:     */     try
/*  258:     */     {
/*  259: 313 */       if (sourceUrl.indexOf(':') < 0)
/*  260:     */       {
/*  261:     */         try
/*  262:     */         {
/*  263: 316 */           if (sourceUrl.startsWith("~/"))
/*  264:     */           {
/*  265: 317 */             String home = SecurityUtilities.getSystemProperty("user.home");
/*  266: 318 */             if (home != null)
/*  267:     */             {
/*  268: 319 */               String pathFromHome = sourceUrl.substring(2);
/*  269: 320 */               File f = new File(new File(home), pathFromHome);
/*  270: 321 */               if (f.exists())
/*  271:     */               {
/*  272: 322 */                 InputStream is = new FileInputStream(f);
/*  273:     */                 break label233;
/*  274:     */               }
/*  275:     */             }
/*  276:     */           }
/*  277: 327 */           File f = new File(sourceUrl);
/*  278: 328 */           if (f.exists())
/*  279:     */           {
/*  280: 329 */             InputStream is = new FileInputStream(f);
/*  281:     */             break label233;
/*  282:     */           }
/*  283:     */         }
/*  284:     */         catch (SecurityException ex) {}
/*  285: 334 */         if (sourceUrl.startsWith("//")) {
/*  286: 335 */           sourceUrl = "http:" + sourceUrl;
/*  287: 336 */         } else if (sourceUrl.startsWith("/")) {
/*  288: 337 */           sourceUrl = "http://127.0.0.1" + sourceUrl;
/*  289:     */         } else {
/*  290: 339 */           sourceUrl = "http://" + sourceUrl;
/*  291:     */         }
/*  292:     */       }
/*  293: 343 */       InputStream is = new URL(sourceUrl).openStream();
/*  294:     */       try
/*  295:     */       {
/*  296:     */         label233:
/*  297: 347 */         source = Kit.readReader(new InputStreamReader(is));
/*  298:     */       }
/*  299:     */       finally
/*  300:     */       {
/*  301: 349 */         is.close();
/*  302:     */       }
/*  303:     */     }
/*  304:     */     catch (IOException ex)
/*  305:     */     {
/*  306: 352 */       System.err.println("Failed to load source from " + sourceUrl + ": " + ex);
/*  307:     */     }
/*  308: 355 */     return source;
/*  309:     */   }
/*  310:     */   
/*  311:     */   private void registerTopScript(DebuggableScript topScript, String source)
/*  312:     */   {
/*  313: 362 */     if (!topScript.isTopLevel()) {
/*  314: 363 */       throw new IllegalArgumentException();
/*  315:     */     }
/*  316: 365 */     String url = getNormalizedUrl(topScript);
/*  317: 366 */     DebuggableScript[] functions = getAllFunctions(topScript);
/*  318: 367 */     if (this.sourceProvider != null)
/*  319:     */     {
/*  320: 368 */       String providedSource = this.sourceProvider.getSource(topScript);
/*  321: 369 */       if (providedSource != null) {
/*  322: 370 */         source = providedSource;
/*  323:     */       }
/*  324:     */     }
/*  325: 374 */     SourceInfo sourceInfo = new SourceInfo(source, functions, url, null);
/*  326: 376 */     synchronized (this.urlToSourceInfo)
/*  327:     */     {
/*  328: 377 */       SourceInfo old = (SourceInfo)this.urlToSourceInfo.get(url);
/*  329: 378 */       if (old != null) {
/*  330: 379 */         sourceInfo.copyBreakpointsFrom(old);
/*  331:     */       }
/*  332: 381 */       this.urlToSourceInfo.put(url, sourceInfo);
/*  333: 382 */       for (int i = 0; i != sourceInfo.functionSourcesTop(); i++)
/*  334:     */       {
/*  335: 383 */         FunctionSource fsource = sourceInfo.functionSource(i);
/*  336: 384 */         String name = fsource.name();
/*  337: 385 */         if (name.length() != 0) {
/*  338: 386 */           this.functionNames.put(name, fsource);
/*  339:     */         }
/*  340:     */       }
/*  341:     */     }
/*  342: 391 */     synchronized (this.functionToSource)
/*  343:     */     {
/*  344: 392 */       for (int i = 0; i != functions.length; i++)
/*  345:     */       {
/*  346: 393 */         FunctionSource fsource = sourceInfo.functionSource(i);
/*  347: 394 */         this.functionToSource.put(functions[i], fsource);
/*  348:     */       }
/*  349:     */     }
/*  350: 398 */     this.callback.updateSourceText(sourceInfo);
/*  351:     */   }
/*  352:     */   
/*  353:     */   private FunctionSource functionSource(DebuggableScript fnOrScript)
/*  354:     */   {
/*  355: 405 */     return (FunctionSource)this.functionToSource.get(fnOrScript);
/*  356:     */   }
/*  357:     */   
/*  358:     */   public String[] functionNames()
/*  359:     */   {
/*  360: 412 */     synchronized (this.urlToSourceInfo)
/*  361:     */     {
/*  362: 413 */       return (String[])this.functionNames.keySet().toArray(new String[this.functionNames.size()]);
/*  363:     */     }
/*  364:     */   }
/*  365:     */   
/*  366:     */   public FunctionSource functionSourceByName(String functionName)
/*  367:     */   {
/*  368: 421 */     return (FunctionSource)this.functionNames.get(functionName);
/*  369:     */   }
/*  370:     */   
/*  371:     */   public SourceInfo sourceInfo(String url)
/*  372:     */   {
/*  373: 428 */     return (SourceInfo)this.urlToSourceInfo.get(url);
/*  374:     */   }
/*  375:     */   
/*  376:     */   private String getNormalizedUrl(DebuggableScript fnOrScript)
/*  377:     */   {
/*  378: 435 */     String url = fnOrScript.getSourceName();
/*  379: 436 */     if (url == null)
/*  380:     */     {
/*  381: 436 */       url = "<stdin>";
/*  382:     */     }
/*  383:     */     else
/*  384:     */     {
/*  385: 442 */       char evalSeparator = '#';
/*  386: 443 */       StringBuffer sb = null;
/*  387: 444 */       int urlLength = url.length();
/*  388: 445 */       int cursor = 0;
/*  389:     */       for (;;)
/*  390:     */       {
/*  391: 447 */         int searchStart = url.indexOf(evalSeparator, cursor);
/*  392: 448 */         if (searchStart < 0) {
/*  393:     */           break;
/*  394:     */         }
/*  395: 451 */         String replace = null;
/*  396: 452 */         int i = searchStart + 1;
/*  397: 453 */         while (i != urlLength)
/*  398:     */         {
/*  399: 454 */           int c = url.charAt(i);
/*  400: 455 */           if ((48 > c) || (c > 57)) {
/*  401:     */             break;
/*  402:     */           }
/*  403: 458 */           i++;
/*  404:     */         }
/*  405: 460 */         if (i != searchStart + 1) {
/*  406: 462 */           if ("(eval)".regionMatches(0, url, i, 6))
/*  407:     */           {
/*  408: 463 */             cursor = i + 6;
/*  409: 464 */             replace = "(eval)";
/*  410:     */           }
/*  411:     */         }
/*  412: 467 */         if (replace == null) {
/*  413:     */           break;
/*  414:     */         }
/*  415: 470 */         if (sb == null)
/*  416:     */         {
/*  417: 471 */           sb = new StringBuffer();
/*  418: 472 */           sb.append(url.substring(0, searchStart));
/*  419:     */         }
/*  420: 474 */         sb.append(replace);
/*  421:     */       }
/*  422: 476 */       if (sb != null)
/*  423:     */       {
/*  424: 477 */         if (cursor != urlLength) {
/*  425: 478 */           sb.append(url.substring(cursor));
/*  426:     */         }
/*  427: 480 */         url = sb.toString();
/*  428:     */       }
/*  429:     */     }
/*  430: 483 */     return url;
/*  431:     */   }
/*  432:     */   
/*  433:     */   private static DebuggableScript[] getAllFunctions(DebuggableScript function)
/*  434:     */   {
/*  435: 491 */     ObjArray functions = new ObjArray();
/*  436: 492 */     collectFunctions_r(function, functions);
/*  437: 493 */     DebuggableScript[] result = new DebuggableScript[functions.size()];
/*  438: 494 */     functions.toArray(result);
/*  439: 495 */     return result;
/*  440:     */   }
/*  441:     */   
/*  442:     */   private static void collectFunctions_r(DebuggableScript function, ObjArray array)
/*  443:     */   {
/*  444: 503 */     array.add(function);
/*  445: 504 */     for (int i = 0; i != function.getFunctionCount(); i++) {
/*  446: 505 */       collectFunctions_r(function.getFunction(i), array);
/*  447:     */     }
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void clearAllBreakpoints()
/*  451:     */   {
/*  452: 513 */     for (SourceInfo si : this.urlToSourceInfo.values()) {
/*  453: 514 */       si.removeAllBreakpoints();
/*  454:     */     }
/*  455:     */   }
/*  456:     */   
/*  457:     */   private void handleBreakpointHit(StackFrame frame, Context cx)
/*  458:     */   {
/*  459: 522 */     this.breakFlag = false;
/*  460: 523 */     interrupted(cx, frame, null);
/*  461:     */   }
/*  462:     */   
/*  463:     */   private void handleExceptionThrown(Context cx, Throwable ex, StackFrame frame)
/*  464:     */   {
/*  465: 531 */     if (this.breakOnExceptions)
/*  466:     */     {
/*  467: 532 */       ContextData cd = frame.contextData();
/*  468: 533 */       if (cd.lastProcessedException != ex)
/*  469:     */       {
/*  470: 534 */         interrupted(cx, frame, ex);
/*  471: 535 */         cd.lastProcessedException = ex;
/*  472:     */       }
/*  473:     */     }
/*  474:     */   }
/*  475:     */   
/*  476:     */   public ContextData currentContextData()
/*  477:     */   {
/*  478: 544 */     return this.interruptedContextData;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public void setReturnValue(int returnValue)
/*  482:     */   {
/*  483: 551 */     synchronized (this.monitor)
/*  484:     */     {
/*  485: 552 */       this.returnValue = returnValue;
/*  486: 553 */       this.monitor.notify();
/*  487:     */     }
/*  488:     */   }
/*  489:     */   
/*  490:     */   public void go()
/*  491:     */   {
/*  492: 561 */     synchronized (this.monitor)
/*  493:     */     {
/*  494: 562 */       this.returnValue = 3;
/*  495: 563 */       this.monitor.notifyAll();
/*  496:     */     }
/*  497:     */   }
/*  498:     */   
/*  499:     */   public String eval(String expr)
/*  500:     */   {
/*  501: 571 */     String result = "undefined";
/*  502: 572 */     if (expr == null) {
/*  503: 573 */       return result;
/*  504:     */     }
/*  505: 575 */     ContextData contextData = currentContextData();
/*  506: 576 */     if ((contextData == null) || (this.frameIndex >= contextData.frameCount())) {
/*  507: 577 */       return result;
/*  508:     */     }
/*  509: 579 */     StackFrame frame = contextData.getFrame(this.frameIndex);
/*  510: 580 */     if (contextData.eventThreadFlag)
/*  511:     */     {
/*  512: 581 */       Context cx = Context.getCurrentContext();
/*  513: 582 */       result = do_eval(cx, frame, expr);
/*  514:     */     }
/*  515:     */     else
/*  516:     */     {
/*  517: 584 */       synchronized (this.monitor)
/*  518:     */       {
/*  519: 585 */         if (this.insideInterruptLoop)
/*  520:     */         {
/*  521: 586 */           this.evalRequest = expr;
/*  522: 587 */           this.evalFrame = frame;
/*  523: 588 */           this.monitor.notify();
/*  524:     */           do
/*  525:     */           {
/*  526:     */             try
/*  527:     */             {
/*  528: 591 */               this.monitor.wait();
/*  529:     */             }
/*  530:     */             catch (InterruptedException exc)
/*  531:     */             {
/*  532: 593 */               Thread.currentThread().interrupt();
/*  533: 594 */               break;
/*  534:     */             }
/*  535: 596 */           } while (this.evalRequest != null);
/*  536: 597 */           result = this.evalResult;
/*  537:     */         }
/*  538:     */       }
/*  539:     */     }
/*  540: 601 */     return result;
/*  541:     */   }
/*  542:     */   
/*  543:     */   public void compileScript(String url, String text)
/*  544:     */   {
/*  545: 608 */     DimIProxy action = new DimIProxy(this, 2, null);
/*  546: 609 */     action.url = url;
/*  547: 610 */     action.text = text;
/*  548: 611 */     action.withContext();
/*  549:     */   }
/*  550:     */   
/*  551:     */   public void evalScript(String url, String text)
/*  552:     */   {
/*  553: 618 */     DimIProxy action = new DimIProxy(this, 3, null);
/*  554: 619 */     action.url = url;
/*  555: 620 */     action.text = text;
/*  556: 621 */     action.withContext();
/*  557:     */   }
/*  558:     */   
/*  559:     */   public String objectToString(Object object)
/*  560:     */   {
/*  561: 628 */     DimIProxy action = new DimIProxy(this, 5, null);
/*  562: 629 */     action.object = object;
/*  563: 630 */     action.withContext();
/*  564: 631 */     return action.stringResult;
/*  565:     */   }
/*  566:     */   
/*  567:     */   public boolean stringIsCompilableUnit(String str)
/*  568:     */   {
/*  569: 638 */     DimIProxy action = new DimIProxy(this, 4, null);
/*  570: 639 */     action.text = str;
/*  571: 640 */     action.withContext();
/*  572: 641 */     return action.booleanResult;
/*  573:     */   }
/*  574:     */   
/*  575:     */   public Object getObjectProperty(Object object, Object id)
/*  576:     */   {
/*  577: 648 */     DimIProxy action = new DimIProxy(this, 6, null);
/*  578: 649 */     action.object = object;
/*  579: 650 */     action.id = id;
/*  580: 651 */     action.withContext();
/*  581: 652 */     return action.objectResult;
/*  582:     */   }
/*  583:     */   
/*  584:     */   public Object[] getObjectIds(Object object)
/*  585:     */   {
/*  586: 659 */     DimIProxy action = new DimIProxy(this, 7, null);
/*  587: 660 */     action.object = object;
/*  588: 661 */     action.withContext();
/*  589: 662 */     return action.objectArrayResult;
/*  590:     */   }
/*  591:     */   
/*  592:     */   private Object getObjectPropertyImpl(Context cx, Object object, Object id)
/*  593:     */   {
/*  594: 670 */     Scriptable scriptable = (Scriptable)object;
/*  595:     */     Object result;
/*  596: 672 */     if ((id instanceof String))
/*  597:     */     {
/*  598: 673 */       String name = (String)id;
/*  599:     */       Object result;
/*  600: 674 */       if (name.equals("this"))
/*  601:     */       {
/*  602: 675 */         result = scriptable;
/*  603:     */       }
/*  604:     */       else
/*  605:     */       {
/*  606:     */         Object result;
/*  607: 676 */         if (name.equals("__proto__"))
/*  608:     */         {
/*  609: 677 */           result = scriptable.getPrototype();
/*  610:     */         }
/*  611:     */         else
/*  612:     */         {
/*  613:     */           Object result;
/*  614: 678 */           if (name.equals("__parent__"))
/*  615:     */           {
/*  616: 679 */             result = scriptable.getParentScope();
/*  617:     */           }
/*  618:     */           else
/*  619:     */           {
/*  620: 681 */             Object result = ScriptableObject.getProperty(scriptable, name);
/*  621: 682 */             if (result == ScriptableObject.NOT_FOUND) {
/*  622: 683 */               result = Undefined.instance;
/*  623:     */             }
/*  624:     */           }
/*  625:     */         }
/*  626:     */       }
/*  627:     */     }
/*  628:     */     else
/*  629:     */     {
/*  630: 687 */       int index = ((Integer)id).intValue();
/*  631: 688 */       result = ScriptableObject.getProperty(scriptable, index);
/*  632: 689 */       if (result == ScriptableObject.NOT_FOUND) {
/*  633: 690 */         result = Undefined.instance;
/*  634:     */       }
/*  635:     */     }
/*  636: 693 */     return result;
/*  637:     */   }
/*  638:     */   
/*  639:     */   private Object[] getObjectIdsImpl(Context cx, Object object)
/*  640:     */   {
/*  641: 700 */     if ((!(object instanceof Scriptable)) || (object == Undefined.instance)) {
/*  642: 701 */       return Context.emptyArgs;
/*  643:     */     }
/*  644: 705 */     Scriptable scriptable = (Scriptable)object;
/*  645:     */     Object[] ids;
/*  646:     */     Object[] ids;
/*  647: 706 */     if ((scriptable instanceof DebuggableObject)) {
/*  648: 707 */       ids = ((DebuggableObject)scriptable).getAllIds();
/*  649:     */     } else {
/*  650: 709 */       ids = scriptable.getIds();
/*  651:     */     }
/*  652: 712 */     Scriptable proto = scriptable.getPrototype();
/*  653: 713 */     Scriptable parent = scriptable.getParentScope();
/*  654: 714 */     int extra = 0;
/*  655: 715 */     if (proto != null) {
/*  656: 716 */       extra++;
/*  657:     */     }
/*  658: 718 */     if (parent != null) {
/*  659: 719 */       extra++;
/*  660:     */     }
/*  661: 721 */     if (extra != 0)
/*  662:     */     {
/*  663: 722 */       Object[] tmp = new Object[extra + ids.length];
/*  664: 723 */       System.arraycopy(ids, 0, tmp, extra, ids.length);
/*  665: 724 */       ids = tmp;
/*  666: 725 */       extra = 0;
/*  667: 726 */       if (proto != null) {
/*  668: 727 */         ids[(extra++)] = "__proto__";
/*  669:     */       }
/*  670: 729 */       if (parent != null) {
/*  671: 730 */         ids[(extra++)] = "__parent__";
/*  672:     */       }
/*  673:     */     }
/*  674: 734 */     return ids;
/*  675:     */   }
/*  676:     */   
/*  677:     */   private void interrupted(Context cx, StackFrame frame, Throwable scriptException)
/*  678:     */   {
/*  679: 742 */     ContextData contextData = frame.contextData();
/*  680: 743 */     boolean eventThreadFlag = this.callback.isGuiEventThread();
/*  681: 744 */     contextData.eventThreadFlag = eventThreadFlag;
/*  682:     */     
/*  683: 746 */     boolean recursiveEventThreadCall = false;
/*  684: 749 */     synchronized (this.eventThreadMonitor)
/*  685:     */     {
/*  686: 750 */       if (eventThreadFlag)
/*  687:     */       {
/*  688: 751 */         if (this.interruptedContextData != null)
/*  689:     */         {
/*  690: 752 */           recursiveEventThreadCall = true;
/*  691:     */           break label100;
/*  692:     */         }
/*  693:     */       }
/*  694:     */       else {
/*  695: 756 */         while (this.interruptedContextData != null) {
/*  696:     */           try
/*  697:     */           {
/*  698: 758 */             this.eventThreadMonitor.wait();
/*  699:     */           }
/*  700:     */           catch (InterruptedException exc)
/*  701:     */           {
/*  702: 760 */             return;
/*  703:     */           }
/*  704:     */         }
/*  705:     */       }
/*  706: 764 */       this.interruptedContextData = contextData;
/*  707:     */     }
/*  708:     */     label100:
/*  709: 767 */     if (recursiveEventThreadCall) {
/*  710: 782 */       return;
/*  711:     */     }
/*  712: 785 */     if (this.interruptedContextData == null) {
/*  713: 785 */       Kit.codeBug();
/*  714:     */     }
/*  715:     */     try
/*  716:     */     {
/*  717: 789 */       int frameCount = contextData.frameCount();
/*  718: 790 */       this.frameIndex = (frameCount - 1);
/*  719:     */       
/*  720: 792 */       String threadTitle = Thread.currentThread().toString();
/*  721:     */       String alertMessage;
/*  722:     */       String alertMessage;
/*  723: 794 */       if (scriptException == null) {
/*  724: 795 */         alertMessage = null;
/*  725:     */       } else {
/*  726: 797 */         alertMessage = scriptException.toString();
/*  727:     */       }
/*  728: 800 */       int returnValue = -1;
/*  729: 801 */       if (!eventThreadFlag)
/*  730:     */       {
/*  731: 802 */         synchronized (this.monitor)
/*  732:     */         {
/*  733: 803 */           if (this.insideInterruptLoop) {
/*  734: 803 */             Kit.codeBug();
/*  735:     */           }
/*  736: 804 */           this.insideInterruptLoop = true;
/*  737: 805 */           this.evalRequest = null;
/*  738: 806 */           this.returnValue = -1;
/*  739: 807 */           this.callback.enterInterrupt(frame, threadTitle, alertMessage);
/*  740:     */           try
/*  741:     */           {
/*  742:     */             do
/*  743:     */             {
/*  744:     */               for (;;)
/*  745:     */               {
/*  746:     */                 try
/*  747:     */                 {
/*  748: 812 */                   this.monitor.wait();
/*  749:     */                 }
/*  750:     */                 catch (InterruptedException exc)
/*  751:     */                 {
/*  752: 814 */                   Thread.currentThread().interrupt();
/*  753:     */                   break label323;
/*  754:     */                 }
/*  755: 817 */                 if (this.evalRequest == null) {
/*  756:     */                   break;
/*  757:     */                 }
/*  758: 818 */                 this.evalResult = null;
/*  759:     */                 try
/*  760:     */                 {
/*  761: 820 */                   this.evalResult = do_eval(cx, this.evalFrame, this.evalRequest);
/*  762:     */                 }
/*  763:     */                 finally
/*  764:     */                 {
/*  765: 823 */                   this.evalRequest = null;
/*  766: 824 */                   this.evalFrame = null;
/*  767: 825 */                   this.monitor.notify();
/*  768:     */                 }
/*  769:     */               }
/*  770: 829 */             } while (this.returnValue == -1);
/*  771: 830 */             returnValue = this.returnValue;
/*  772:     */           }
/*  773:     */           finally
/*  774:     */           {
/*  775:     */             label323:
/*  776: 835 */             this.insideInterruptLoop = false;
/*  777:     */           }
/*  778:     */         }
/*  779:     */       }
/*  780:     */       else
/*  781:     */       {
/*  782: 839 */         this.returnValue = -1;
/*  783: 840 */         this.callback.enterInterrupt(frame, threadTitle, alertMessage);
/*  784: 841 */         while (this.returnValue == -1) {
/*  785:     */           try
/*  786:     */           {
/*  787: 843 */             this.callback.dispatchNextGuiEvent();
/*  788:     */           }
/*  789:     */           catch (InterruptedException exc) {}
/*  790:     */         }
/*  791: 847 */         returnValue = this.returnValue;
/*  792:     */       }
/*  793: 849 */       switch (returnValue)
/*  794:     */       {
/*  795:     */       case 0: 
/*  796: 851 */         contextData.breakNextLine = true;
/*  797: 852 */         contextData.stopAtFrameDepth = contextData.frameCount();
/*  798: 853 */         break;
/*  799:     */       case 1: 
/*  800: 855 */         contextData.breakNextLine = true;
/*  801: 856 */         contextData.stopAtFrameDepth = -1;
/*  802: 857 */         break;
/*  803:     */       case 2: 
/*  804: 859 */         if (contextData.frameCount() > 1)
/*  805:     */         {
/*  806: 860 */           contextData.breakNextLine = true;
/*  807: 861 */           contextData.stopAtFrameDepth = (contextData.frameCount() - 1);
/*  808:     */         }
/*  809:     */         break;
/*  810:     */       }
/*  811:     */     }
/*  812:     */     finally
/*  813:     */     {
/*  814: 868 */       synchronized (this.eventThreadMonitor)
/*  815:     */       {
/*  816: 869 */         this.interruptedContextData = null;
/*  817: 870 */         this.eventThreadMonitor.notifyAll();
/*  818:     */       }
/*  819:     */     }
/*  820:     */   }
/*  821:     */   
/*  822:     */   private static String do_eval(Context cx, StackFrame frame, String expr)
/*  823:     */   {
/*  824: 881 */     Debugger saved_debugger = cx.getDebugger();
/*  825: 882 */     Object saved_data = cx.getDebuggerContextData();
/*  826: 883 */     int saved_level = cx.getOptimizationLevel();
/*  827:     */     
/*  828: 885 */     cx.setDebugger(null, null);
/*  829: 886 */     cx.setOptimizationLevel(-1);
/*  830: 887 */     cx.setGeneratingDebug(false);
/*  831:     */     String resultString;
/*  832:     */     try
/*  833:     */     {
/*  834: 889 */       Callable script = (Callable)cx.compileString(expr, "", 0, null);
/*  835: 890 */       Object result = script.call(cx, frame.scope, frame.thisObj, ScriptRuntime.emptyArgs);
/*  836:     */       String resultString;
/*  837: 892 */       if (result == Undefined.instance) {
/*  838: 893 */         resultString = "";
/*  839:     */       } else {
/*  840: 895 */         resultString = ScriptRuntime.toString(result);
/*  841:     */       }
/*  842:     */     }
/*  843:     */     catch (Exception exc)
/*  844:     */     {
/*  845: 898 */       resultString = exc.getMessage();
/*  846:     */     }
/*  847:     */     finally
/*  848:     */     {
/*  849: 900 */       cx.setGeneratingDebug(true);
/*  850: 901 */       cx.setOptimizationLevel(saved_level);
/*  851: 902 */       cx.setDebugger(saved_debugger, saved_data);
/*  852:     */     }
/*  853: 904 */     if (resultString == null) {
/*  854: 905 */       resultString = "null";
/*  855:     */     }
/*  856: 907 */     return resultString;
/*  857:     */   }
/*  858:     */   
/*  859:     */   private static class DimIProxy
/*  860:     */     implements ContextAction, ContextFactory.Listener, Debugger
/*  861:     */   {
/*  862:     */     private Dim dim;
/*  863:     */     private int type;
/*  864:     */     private String url;
/*  865:     */     private String text;
/*  866:     */     private Object object;
/*  867:     */     private Object id;
/*  868:     */     private boolean booleanResult;
/*  869:     */     private String stringResult;
/*  870:     */     private Object objectResult;
/*  871:     */     private Object[] objectArrayResult;
/*  872:     */     
/*  873:     */     private DimIProxy(Dim dim, int type)
/*  874:     */     {
/*  875: 972 */       this.dim = dim;
/*  876: 973 */       this.type = type;
/*  877:     */     }
/*  878:     */     
/*  879:     */     public Object run(Context cx)
/*  880:     */     {
/*  881: 982 */       switch (this.type)
/*  882:     */       {
/*  883:     */       case 2: 
/*  884: 984 */         cx.compileString(this.text, this.url, 1, null);
/*  885: 985 */         break;
/*  886:     */       case 3: 
/*  887: 989 */         Scriptable scope = null;
/*  888: 990 */         if (this.dim.scopeProvider != null) {
/*  889: 991 */           scope = this.dim.scopeProvider.getScope();
/*  890:     */         }
/*  891: 993 */         if (scope == null) {
/*  892: 994 */           scope = new ImporterTopLevel(cx);
/*  893:     */         }
/*  894: 996 */         cx.evaluateString(scope, this.text, this.url, 1, null);
/*  895:     */         
/*  896: 998 */         break;
/*  897:     */       case 4: 
/*  898:1001 */         this.booleanResult = cx.stringIsCompilableUnit(this.text);
/*  899:1002 */         break;
/*  900:     */       case 5: 
/*  901:1005 */         if (this.object == Undefined.instance) {
/*  902:1006 */           this.stringResult = "undefined";
/*  903:1007 */         } else if (this.object == null) {
/*  904:1008 */           this.stringResult = "null";
/*  905:1009 */         } else if ((this.object instanceof NativeCall)) {
/*  906:1010 */           this.stringResult = "[object Call]";
/*  907:     */         } else {
/*  908:1012 */           this.stringResult = Context.toString(this.object);
/*  909:     */         }
/*  910:1014 */         break;
/*  911:     */       case 6: 
/*  912:1017 */         this.objectResult = this.dim.getObjectPropertyImpl(cx, this.object, this.id);
/*  913:1018 */         break;
/*  914:     */       case 7: 
/*  915:1021 */         this.objectArrayResult = this.dim.getObjectIdsImpl(cx, this.object);
/*  916:1022 */         break;
/*  917:     */       default: 
/*  918:1025 */         throw Kit.codeBug();
/*  919:     */       }
/*  920:1027 */       return null;
/*  921:     */     }
/*  922:     */     
/*  923:     */     private void withContext()
/*  924:     */     {
/*  925:1035 */       this.dim.contextFactory.call(this);
/*  926:     */     }
/*  927:     */     
/*  928:     */     public void contextCreated(Context cx)
/*  929:     */     {
/*  930:1044 */       if (this.type != 1) {
/*  931:1044 */         Kit.codeBug();
/*  932:     */       }
/*  933:1045 */       Dim.ContextData contextData = new Dim.ContextData();
/*  934:1046 */       Debugger debugger = new DimIProxy(this.dim, 0);
/*  935:1047 */       cx.setDebugger(debugger, contextData);
/*  936:1048 */       cx.setGeneratingDebug(true);
/*  937:1049 */       cx.setOptimizationLevel(-1);
/*  938:     */     }
/*  939:     */     
/*  940:     */     public void contextReleased(Context cx)
/*  941:     */     {
/*  942:1056 */       if (this.type != 1) {
/*  943:1056 */         Kit.codeBug();
/*  944:     */       }
/*  945:     */     }
/*  946:     */     
/*  947:     */     public DebugFrame getFrame(Context cx, DebuggableScript fnOrScript)
/*  948:     */     {
/*  949:1065 */       if (this.type != 0) {
/*  950:1065 */         Kit.codeBug();
/*  951:     */       }
/*  952:1067 */       Dim.FunctionSource item = this.dim.getFunctionSource(fnOrScript);
/*  953:1068 */       if (item == null) {
/*  954:1070 */         return null;
/*  955:     */       }
/*  956:1072 */       return new Dim.StackFrame(cx, this.dim, item, null);
/*  957:     */     }
/*  958:     */     
/*  959:     */     public void handleCompilationDone(Context cx, DebuggableScript fnOrScript, String source)
/*  960:     */     {
/*  961:1081 */       if (this.type != 0) {
/*  962:1081 */         Kit.codeBug();
/*  963:     */       }
/*  964:1083 */       if (!fnOrScript.isTopLevel()) {
/*  965:1084 */         return;
/*  966:     */       }
/*  967:1086 */       this.dim.registerTopScript(fnOrScript, source);
/*  968:     */     }
/*  969:     */   }
/*  970:     */   
/*  971:     */   public static class ContextData
/*  972:     */   {
/*  973:1098 */     private ObjArray frameStack = new ObjArray();
/*  974:     */     private boolean breakNextLine;
/*  975:1109 */     private int stopAtFrameDepth = -1;
/*  976:     */     private boolean eventThreadFlag;
/*  977:     */     private Throwable lastProcessedException;
/*  978:     */     
/*  979:     */     public static ContextData get(Context cx)
/*  980:     */     {
/*  981:1125 */       return (ContextData)cx.getDebuggerContextData();
/*  982:     */     }
/*  983:     */     
/*  984:     */     public int frameCount()
/*  985:     */     {
/*  986:1132 */       return this.frameStack.size();
/*  987:     */     }
/*  988:     */     
/*  989:     */     public Dim.StackFrame getFrame(int frameNumber)
/*  990:     */     {
/*  991:1139 */       int num = this.frameStack.size() - frameNumber - 1;
/*  992:1140 */       return (Dim.StackFrame)this.frameStack.get(num);
/*  993:     */     }
/*  994:     */     
/*  995:     */     private void pushFrame(Dim.StackFrame frame)
/*  996:     */     {
/*  997:1147 */       this.frameStack.push(frame);
/*  998:     */     }
/*  999:     */     
/* 1000:     */     private void popFrame()
/* 1001:     */     {
/* 1002:1154 */       this.frameStack.pop();
/* 1003:     */     }
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public static class StackFrame
/* 1007:     */     implements DebugFrame
/* 1008:     */   {
/* 1009:     */     private Dim dim;
/* 1010:     */     private Dim.ContextData contextData;
/* 1011:     */     private Scriptable scope;
/* 1012:     */     private Scriptable thisObj;
/* 1013:     */     private Dim.FunctionSource fsource;
/* 1014:     */     private boolean[] breakpoints;
/* 1015:     */     private int lineNumber;
/* 1016:     */     
/* 1017:     */     private StackFrame(Context cx, Dim dim, Dim.FunctionSource fsource)
/* 1018:     */     {
/* 1019:1202 */       this.dim = dim;
/* 1020:1203 */       this.contextData = Dim.ContextData.get(cx);
/* 1021:1204 */       this.fsource = fsource;
/* 1022:1205 */       this.breakpoints = fsource.sourceInfo().breakpoints;
/* 1023:1206 */       this.lineNumber = fsource.firstLine();
/* 1024:     */     }
/* 1025:     */     
/* 1026:     */     public void onEnter(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 1027:     */     {
/* 1028:1214 */       Dim.ContextData.access$2600(this.contextData, this);
/* 1029:1215 */       this.scope = scope;
/* 1030:1216 */       this.thisObj = thisObj;
/* 1031:1217 */       if (this.dim.breakOnEnter) {
/* 1032:1218 */         this.dim.handleBreakpointHit(this, cx);
/* 1033:     */       }
/* 1034:     */     }
/* 1035:     */     
/* 1036:     */     public void onLineChange(Context cx, int lineno)
/* 1037:     */     {
/* 1038:1226 */       this.lineNumber = lineno;
/* 1039:1228 */       if ((this.breakpoints[lineno] == 0) && (!this.dim.breakFlag))
/* 1040:     */       {
/* 1041:1229 */         boolean lineBreak = Dim.ContextData.access$1400(this.contextData);
/* 1042:1230 */         if ((lineBreak) && (Dim.ContextData.access$1500(this.contextData) >= 0)) {
/* 1043:1231 */           lineBreak = this.contextData.frameCount() <= Dim.ContextData.access$1500(this.contextData);
/* 1044:     */         }
/* 1045:1234 */         if (!lineBreak) {
/* 1046:1235 */           return;
/* 1047:     */         }
/* 1048:1237 */         Dim.ContextData.access$1502(this.contextData, -1);
/* 1049:1238 */         Dim.ContextData.access$1402(this.contextData, false);
/* 1050:     */       }
/* 1051:1241 */       this.dim.handleBreakpointHit(this, cx);
/* 1052:     */     }
/* 1053:     */     
/* 1054:     */     public void onExceptionThrown(Context cx, Throwable exception)
/* 1055:     */     {
/* 1056:1248 */       this.dim.handleExceptionThrown(cx, exception, this);
/* 1057:     */     }
/* 1058:     */     
/* 1059:     */     public void onExit(Context cx, boolean byThrow, Object resultOrException)
/* 1060:     */     {
/* 1061:1256 */       if ((this.dim.breakOnReturn) && (!byThrow)) {
/* 1062:1257 */         this.dim.handleBreakpointHit(this, cx);
/* 1063:     */       }
/* 1064:1259 */       Dim.ContextData.access$3200(this.contextData);
/* 1065:     */     }
/* 1066:     */     
/* 1067:     */     public void onDebuggerStatement(Context cx)
/* 1068:     */     {
/* 1069:1266 */       this.dim.handleBreakpointHit(this, cx);
/* 1070:     */     }
/* 1071:     */     
/* 1072:     */     public Dim.SourceInfo sourceInfo()
/* 1073:     */     {
/* 1074:1273 */       return this.fsource.sourceInfo();
/* 1075:     */     }
/* 1076:     */     
/* 1077:     */     public Dim.ContextData contextData()
/* 1078:     */     {
/* 1079:1280 */       return this.contextData;
/* 1080:     */     }
/* 1081:     */     
/* 1082:     */     public Object scope()
/* 1083:     */     {
/* 1084:1287 */       return this.scope;
/* 1085:     */     }
/* 1086:     */     
/* 1087:     */     public Object thisObj()
/* 1088:     */     {
/* 1089:1294 */       return this.thisObj;
/* 1090:     */     }
/* 1091:     */     
/* 1092:     */     public String getUrl()
/* 1093:     */     {
/* 1094:1301 */       return this.fsource.sourceInfo().url();
/* 1095:     */     }
/* 1096:     */     
/* 1097:     */     public int getLineNumber()
/* 1098:     */     {
/* 1099:1308 */       return this.lineNumber;
/* 1100:     */     }
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public static class FunctionSource
/* 1104:     */   {
/* 1105:     */     private Dim.SourceInfo sourceInfo;
/* 1106:     */     private int firstLine;
/* 1107:     */     private String name;
/* 1108:     */     
/* 1109:     */     private FunctionSource(Dim.SourceInfo sourceInfo, int firstLine, String name)
/* 1110:     */     {
/* 1111:1337 */       if (name == null) {
/* 1112:1337 */         throw new IllegalArgumentException();
/* 1113:     */       }
/* 1114:1338 */       this.sourceInfo = sourceInfo;
/* 1115:1339 */       this.firstLine = firstLine;
/* 1116:1340 */       this.name = name;
/* 1117:     */     }
/* 1118:     */     
/* 1119:     */     public Dim.SourceInfo sourceInfo()
/* 1120:     */     {
/* 1121:1348 */       return this.sourceInfo;
/* 1122:     */     }
/* 1123:     */     
/* 1124:     */     public int firstLine()
/* 1125:     */     {
/* 1126:1355 */       return this.firstLine;
/* 1127:     */     }
/* 1128:     */     
/* 1129:     */     public String name()
/* 1130:     */     {
/* 1131:1362 */       return this.name;
/* 1132:     */     }
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public static class SourceInfo
/* 1136:     */   {
/* 1137:1374 */     private static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
/* 1138:     */     private String source;
/* 1139:     */     private String url;
/* 1140:     */     private boolean[] breakableLines;
/* 1141:     */     private boolean[] breakpoints;
/* 1142:     */     private Dim.FunctionSource[] functionSources;
/* 1143:     */     
/* 1144:     */     private SourceInfo(String source, DebuggableScript[] functions, String normilizedUrl)
/* 1145:     */     {
/* 1146:1406 */       this.source = source;
/* 1147:1407 */       this.url = normilizedUrl;
/* 1148:     */       
/* 1149:1409 */       int N = functions.length;
/* 1150:1410 */       int[][] lineArrays = new int[N][];
/* 1151:1411 */       for (int i = 0; i != N; i++) {
/* 1152:1412 */         lineArrays[i] = functions[i].getLineNumbers();
/* 1153:     */       }
/* 1154:1415 */       int minAll = 0;int maxAll = -1;
/* 1155:1416 */       int[] firstLines = new int[N];
/* 1156:1417 */       for (int i = 0; i != N; i++)
/* 1157:     */       {
/* 1158:1418 */         int[] lines = lineArrays[i];
/* 1159:1419 */         if ((lines == null) || (lines.length == 0))
/* 1160:     */         {
/* 1161:1420 */           firstLines[i] = -1;
/* 1162:     */         }
/* 1163:     */         else
/* 1164:     */         {
/* 1165:     */           int max;
/* 1166:1423 */           int min = max = lines[0];
/* 1167:1424 */           for (int j = 1; j != lines.length; j++)
/* 1168:     */           {
/* 1169:1425 */             int line = lines[j];
/* 1170:1426 */             if (line < min) {
/* 1171:1427 */               min = line;
/* 1172:1428 */             } else if (line > max) {
/* 1173:1429 */               max = line;
/* 1174:     */             }
/* 1175:     */           }
/* 1176:1432 */           firstLines[i] = min;
/* 1177:1433 */           if (minAll > maxAll)
/* 1178:     */           {
/* 1179:1434 */             minAll = min;
/* 1180:1435 */             maxAll = max;
/* 1181:     */           }
/* 1182:     */           else
/* 1183:     */           {
/* 1184:1437 */             if (min < minAll) {
/* 1185:1438 */               minAll = min;
/* 1186:     */             }
/* 1187:1440 */             if (max > maxAll) {
/* 1188:1441 */               maxAll = max;
/* 1189:     */             }
/* 1190:     */           }
/* 1191:     */         }
/* 1192:     */       }
/* 1193:1447 */       if (minAll > maxAll)
/* 1194:     */       {
/* 1195:1449 */         this.breakableLines = EMPTY_BOOLEAN_ARRAY;
/* 1196:1450 */         this.breakpoints = EMPTY_BOOLEAN_ARRAY;
/* 1197:     */       }
/* 1198:     */       else
/* 1199:     */       {
/* 1200:1452 */         if (minAll < 0) {
/* 1201:1454 */           throw new IllegalStateException(String.valueOf(minAll));
/* 1202:     */         }
/* 1203:1456 */         int linesTop = maxAll + 1;
/* 1204:1457 */         this.breakableLines = new boolean[linesTop];
/* 1205:1458 */         this.breakpoints = new boolean[linesTop];
/* 1206:1459 */         for (int i = 0; i != N; i++)
/* 1207:     */         {
/* 1208:1460 */           int[] lines = lineArrays[i];
/* 1209:1461 */           if ((lines != null) && (lines.length != 0)) {
/* 1210:1462 */             for (int j = 0; j != lines.length; j++)
/* 1211:     */             {
/* 1212:1463 */               int line = lines[j];
/* 1213:1464 */               this.breakableLines[line] = true;
/* 1214:     */             }
/* 1215:     */           }
/* 1216:     */         }
/* 1217:     */       }
/* 1218:1469 */       this.functionSources = new Dim.FunctionSource[N];
/* 1219:1470 */       for (int i = 0; i != N; i++)
/* 1220:     */       {
/* 1221:1471 */         String name = functions[i].getFunctionName();
/* 1222:1472 */         if (name == null) {
/* 1223:1473 */           name = "";
/* 1224:     */         }
/* 1225:1475 */         this.functionSources[i] = new Dim.FunctionSource(this, firstLines[i], name, null);
/* 1226:     */       }
/* 1227:     */     }
/* 1228:     */     
/* 1229:     */     public String source()
/* 1230:     */     {
/* 1231:1484 */       return this.source;
/* 1232:     */     }
/* 1233:     */     
/* 1234:     */     public String url()
/* 1235:     */     {
/* 1236:1491 */       return this.url;
/* 1237:     */     }
/* 1238:     */     
/* 1239:     */     public int functionSourcesTop()
/* 1240:     */     {
/* 1241:1498 */       return this.functionSources.length;
/* 1242:     */     }
/* 1243:     */     
/* 1244:     */     public Dim.FunctionSource functionSource(int i)
/* 1245:     */     {
/* 1246:1505 */       return this.functionSources[i];
/* 1247:     */     }
/* 1248:     */     
/* 1249:     */     private void copyBreakpointsFrom(SourceInfo old)
/* 1250:     */     {
/* 1251:1513 */       int end = old.breakpoints.length;
/* 1252:1514 */       if (end > this.breakpoints.length) {
/* 1253:1515 */         end = this.breakpoints.length;
/* 1254:     */       }
/* 1255:1517 */       for (int line = 0; line != end; line++) {
/* 1256:1518 */         if (old.breakpoints[line] != 0) {
/* 1257:1519 */           this.breakpoints[line] = true;
/* 1258:     */         }
/* 1259:     */       }
/* 1260:     */     }
/* 1261:     */     
/* 1262:     */     public boolean breakableLine(int line)
/* 1263:     */     {
/* 1264:1529 */       return (line < this.breakableLines.length) && (this.breakableLines[line] != 0);
/* 1265:     */     }
/* 1266:     */     
/* 1267:     */     public boolean breakpoint(int line)
/* 1268:     */     {
/* 1269:1537 */       if (!breakableLine(line)) {
/* 1270:1538 */         throw new IllegalArgumentException(String.valueOf(line));
/* 1271:     */       }
/* 1272:1540 */       return (line < this.breakpoints.length) && (this.breakpoints[line] != 0);
/* 1273:     */     }
/* 1274:     */     
/* 1275:     */     public boolean breakpoint(int line, boolean value)
/* 1276:     */     {
/* 1277:1547 */       if (!breakableLine(line)) {
/* 1278:1548 */         throw new IllegalArgumentException(String.valueOf(line));
/* 1279:     */       }
/* 1280:     */       boolean changed;
/* 1281:1551 */       synchronized (this.breakpoints)
/* 1282:     */       {
/* 1283:     */         boolean changed;
/* 1284:1552 */         if (this.breakpoints[line] != value)
/* 1285:     */         {
/* 1286:1553 */           this.breakpoints[line] = value;
/* 1287:1554 */           changed = true;
/* 1288:     */         }
/* 1289:     */         else
/* 1290:     */         {
/* 1291:1556 */           changed = false;
/* 1292:     */         }
/* 1293:     */       }
/* 1294:1559 */       return changed;
/* 1295:     */     }
/* 1296:     */     
/* 1297:     */     public void removeAllBreakpoints()
/* 1298:     */     {
/* 1299:1566 */       synchronized (this.breakpoints)
/* 1300:     */       {
/* 1301:1567 */         for (int line = 0; line != this.breakpoints.length; line++) {
/* 1302:1568 */           this.breakpoints[line] = false;
/* 1303:     */         }
/* 1304:     */       }
/* 1305:     */     }
/* 1306:     */   }
/* 1307:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Dim
 * JD-Core Version:    0.7.0.1
 */