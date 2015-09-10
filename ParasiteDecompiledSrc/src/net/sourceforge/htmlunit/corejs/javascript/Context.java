/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.beans.PropertyChangeEvent;
/*    4:     */ import java.beans.PropertyChangeListener;
/*    5:     */ import java.io.CharArrayWriter;
/*    6:     */ import java.io.IOException;
/*    7:     */ import java.io.PrintWriter;
/*    8:     */ import java.io.Reader;
/*    9:     */ import java.io.StringWriter;
/*   10:     */ import java.io.Writer;
/*   11:     */ import java.lang.reflect.InvocationTargetException;
/*   12:     */ import java.lang.reflect.Method;
/*   13:     */ import java.util.HashMap;
/*   14:     */ import java.util.HashSet;
/*   15:     */ import java.util.Locale;
/*   16:     */ import java.util.Map;
/*   17:     */ import java.util.Set;
/*   18:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.AstRoot;
/*   19:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*   20:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/*   21:     */ import net.sourceforge.htmlunit.corejs.javascript.debug.Debugger;
/*   22:     */ import net.sourceforge.htmlunit.corejs.javascript.xml.XMLLib;
/*   23:     */ import net.sourceforge.htmlunit.corejs.javascript.xml.XMLLib.Factory;
/*   24:     */ 
/*   25:     */ public class Context
/*   26:     */ {
/*   27:     */   public static final int VERSION_UNKNOWN = -1;
/*   28:     */   public static final int VERSION_DEFAULT = 0;
/*   29:     */   public static final int VERSION_1_0 = 100;
/*   30:     */   public static final int VERSION_1_1 = 110;
/*   31:     */   public static final int VERSION_1_2 = 120;
/*   32:     */   public static final int VERSION_1_3 = 130;
/*   33:     */   public static final int VERSION_1_4 = 140;
/*   34:     */   public static final int VERSION_1_5 = 150;
/*   35:     */   public static final int VERSION_1_6 = 160;
/*   36:     */   public static final int VERSION_1_7 = 170;
/*   37:     */   public static final int VERSION_1_8 = 180;
/*   38:     */   public static final int FEATURE_NON_ECMA_GET_YEAR = 1;
/*   39:     */   public static final int FEATURE_MEMBER_EXPR_AS_FUNCTION_NAME = 2;
/*   40:     */   public static final int FEATURE_RESERVED_KEYWORD_AS_IDENTIFIER = 3;
/*   41:     */   public static final int FEATURE_TO_STRING_AS_SOURCE = 4;
/*   42:     */   public static final int FEATURE_PARENT_PROTO_PROPERTIES = 5;
/*   43:     */   /**
/*   44:     */    * @deprecated
/*   45:     */    */
/*   46:     */   public static final int FEATURE_PARENT_PROTO_PROPRTIES = 5;
/*   47:     */   public static final int FEATURE_E4X = 6;
/*   48:     */   public static final int FEATURE_DYNAMIC_SCOPE = 7;
/*   49:     */   public static final int FEATURE_STRICT_VARS = 8;
/*   50:     */   public static final int FEATURE_STRICT_EVAL = 9;
/*   51:     */   public static final int FEATURE_LOCATION_INFORMATION_IN_ERROR = 10;
/*   52:     */   public static final int FEATURE_STRICT_MODE = 11;
/*   53:     */   public static final int FEATURE_WARNING_AS_ERROR = 12;
/*   54:     */   public static final int FEATURE_ENHANCED_JAVA_ACCESS = 13;
/*   55:     */   public static final int FEATURE_HTMLUNIT_WRITE_READONLY_PROPERTIES = 14;
/*   56:     */   public static final int FEATURE_HTMLUNIT_JS_CATCH_JAVA_EXCEPTION = 15;
/*   57:     */   public static final String languageVersionProperty = "language version";
/*   58:     */   public static final String errorReporterProperty = "error reporter";
/*   59: 340 */   public static final Object[] emptyArgs = ScriptRuntime.emptyArgs;
/*   60:     */   
/*   61:     */   /**
/*   62:     */    * @deprecated
/*   63:     */    */
/*   64:     */   public Context()
/*   65:     */   {
/*   66: 357 */     this(ContextFactory.getGlobal());
/*   67:     */   }
/*   68:     */   
/*   69:     */   protected Context(ContextFactory factory)
/*   70:     */   {
/*   71: 371 */     if (factory == null) {
/*   72: 372 */       throw new IllegalArgumentException("factory == null");
/*   73:     */     }
/*   74: 374 */     this.factory = factory;
/*   75: 375 */     setLanguageVersion(0);
/*   76: 376 */     this.optimizationLevel = (codegenClass != null ? 0 : -1);
/*   77: 377 */     this.maximumInterpreterStackDepth = 2147483647;
/*   78:     */   }
/*   79:     */   
/*   80:     */   public static Context getCurrentContext()
/*   81:     */   {
/*   82: 394 */     Object helper = VMBridge.instance.getThreadContextHelper();
/*   83: 395 */     return VMBridge.instance.getContext(helper);
/*   84:     */   }
/*   85:     */   
/*   86:     */   public static Context enter()
/*   87:     */   {
/*   88: 408 */     return enter(null);
/*   89:     */   }
/*   90:     */   
/*   91:     */   /**
/*   92:     */    * @deprecated
/*   93:     */    */
/*   94:     */   public static Context enter(Context cx)
/*   95:     */   {
/*   96: 428 */     return enter(cx, ContextFactory.getGlobal());
/*   97:     */   }
/*   98:     */   
/*   99:     */   static final Context enter(Context cx, ContextFactory factory)
/*  100:     */   {
/*  101: 433 */     Object helper = VMBridge.instance.getThreadContextHelper();
/*  102: 434 */     Context old = VMBridge.instance.getContext(helper);
/*  103: 435 */     if (old != null)
/*  104:     */     {
/*  105: 436 */       cx = old;
/*  106:     */     }
/*  107:     */     else
/*  108:     */     {
/*  109: 438 */       if (cx == null)
/*  110:     */       {
/*  111: 439 */         cx = factory.makeContext();
/*  112: 440 */         if (cx.enterCount != 0) {
/*  113: 441 */           throw new IllegalStateException("factory.makeContext() returned Context instance already associated with some thread");
/*  114:     */         }
/*  115: 443 */         factory.onContextCreated(cx);
/*  116: 444 */         if ((factory.isSealed()) && (!cx.isSealed())) {
/*  117: 445 */           cx.seal(null);
/*  118:     */         }
/*  119:     */       }
/*  120: 448 */       else if (cx.enterCount != 0)
/*  121:     */       {
/*  122: 449 */         throw new IllegalStateException("can not use Context instance already associated with some thread");
/*  123:     */       }
/*  124: 452 */       VMBridge.instance.setContext(helper, cx);
/*  125:     */     }
/*  126: 454 */     cx.enterCount += 1;
/*  127: 455 */     return cx;
/*  128:     */   }
/*  129:     */   
/*  130:     */   public static void exit()
/*  131:     */   {
/*  132: 471 */     Object helper = VMBridge.instance.getThreadContextHelper();
/*  133: 472 */     Context cx = VMBridge.instance.getContext(helper);
/*  134: 473 */     if (cx == null) {
/*  135: 474 */       throw new IllegalStateException("Calling Context.exit without previous Context.enter");
/*  136:     */     }
/*  137: 477 */     if (cx.enterCount < 1) {
/*  138: 477 */       Kit.codeBug();
/*  139:     */     }
/*  140: 478 */     if (--cx.enterCount == 0)
/*  141:     */     {
/*  142: 479 */       VMBridge.instance.setContext(helper, null);
/*  143: 480 */       cx.factory.onContextReleased(cx);
/*  144:     */     }
/*  145:     */   }
/*  146:     */   
/*  147:     */   /**
/*  148:     */    * @deprecated
/*  149:     */    */
/*  150:     */   public static Object call(ContextAction action)
/*  151:     */   {
/*  152: 499 */     return call(ContextFactory.getGlobal(), action);
/*  153:     */   }
/*  154:     */   
/*  155:     */   public static Object call(ContextFactory factory, Callable callable, final Scriptable scope, final Scriptable thisObj, final Object[] args)
/*  156:     */   {
/*  157: 521 */     if (factory == null) {
/*  158: 522 */       factory = ContextFactory.getGlobal();
/*  159:     */     }
/*  160: 524 */     call(factory, new ContextAction()
/*  161:     */     {
/*  162:     */       public Object run(Context cx)
/*  163:     */       {
/*  164: 526 */         return this.val$callable.call(cx, scope, thisObj, args);
/*  165:     */       }
/*  166:     */     });
/*  167:     */   }
/*  168:     */   
/*  169:     */   static Object call(ContextFactory factory, ContextAction action)
/*  170:     */   {
/*  171: 535 */     Context cx = enter(null, factory);
/*  172:     */     try
/*  173:     */     {
/*  174: 537 */       return action.run(cx);
/*  175:     */     }
/*  176:     */     finally
/*  177:     */     {
/*  178: 540 */       exit();
/*  179:     */     }
/*  180:     */   }
/*  181:     */   
/*  182:     */   /**
/*  183:     */    * @deprecated
/*  184:     */    */
/*  185:     */   public static void addContextListener(ContextListener listener)
/*  186:     */   {
/*  187: 552 */     String DBG = "net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main";
/*  188: 553 */     if (DBG.equals(listener.getClass().getName()))
/*  189:     */     {
/*  190: 554 */       Class<?> cl = listener.getClass();
/*  191: 555 */       Class<?> factoryClass = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.ContextFactory");
/*  192:     */       
/*  193: 557 */       Class<?>[] sig = { factoryClass };
/*  194: 558 */       Object[] args = { ContextFactory.getGlobal() };
/*  195:     */       try
/*  196:     */       {
/*  197: 560 */         Method m = cl.getMethod("attachTo", sig);
/*  198: 561 */         m.invoke(listener, args);
/*  199:     */       }
/*  200:     */       catch (Exception ex)
/*  201:     */       {
/*  202: 563 */         RuntimeException rex = new RuntimeException();
/*  203: 564 */         Kit.initCause(rex, ex);
/*  204: 565 */         throw rex;
/*  205:     */       }
/*  206: 567 */       return;
/*  207:     */     }
/*  208: 570 */     ContextFactory.getGlobal().addListener(listener);
/*  209:     */   }
/*  210:     */   
/*  211:     */   /**
/*  212:     */    * @deprecated
/*  213:     */    */
/*  214:     */   public static void removeContextListener(ContextListener listener)
/*  215:     */   {
/*  216: 580 */     ContextFactory.getGlobal().addListener(listener);
/*  217:     */   }
/*  218:     */   
/*  219:     */   public final ContextFactory getFactory()
/*  220:     */   {
/*  221: 588 */     return this.factory;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public final boolean isSealed()
/*  225:     */   {
/*  226: 599 */     return this.sealed;
/*  227:     */   }
/*  228:     */   
/*  229:     */   public final void seal(Object sealKey)
/*  230:     */   {
/*  231: 616 */     if (this.sealed) {
/*  232: 616 */       onSealedMutation();
/*  233:     */     }
/*  234: 617 */     this.sealed = true;
/*  235: 618 */     this.sealKey = sealKey;
/*  236:     */   }
/*  237:     */   
/*  238:     */   public final void unseal(Object sealKey)
/*  239:     */   {
/*  240: 632 */     if (sealKey == null) {
/*  241: 632 */       throw new IllegalArgumentException();
/*  242:     */     }
/*  243: 633 */     if (this.sealKey != sealKey) {
/*  244: 633 */       throw new IllegalArgumentException();
/*  245:     */     }
/*  246: 634 */     if (!this.sealed) {
/*  247: 634 */       throw new IllegalStateException();
/*  248:     */     }
/*  249: 635 */     this.sealed = false;
/*  250: 636 */     this.sealKey = null;
/*  251:     */   }
/*  252:     */   
/*  253:     */   static void onSealedMutation()
/*  254:     */   {
/*  255: 641 */     throw new IllegalStateException();
/*  256:     */   }
/*  257:     */   
/*  258:     */   public final int getLanguageVersion()
/*  259:     */   {
/*  260: 654 */     return this.version;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public void setLanguageVersion(int version)
/*  264:     */   {
/*  265: 669 */     if (this.sealed) {
/*  266: 669 */       onSealedMutation();
/*  267:     */     }
/*  268: 670 */     checkLanguageVersion(version);
/*  269: 671 */     Object listeners = this.propertyListeners;
/*  270: 672 */     if ((listeners != null) && (version != this.version)) {
/*  271: 673 */       firePropertyChangeImpl(listeners, "language version", Integer.valueOf(this.version), Integer.valueOf(version));
/*  272:     */     }
/*  273: 677 */     this.version = version;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public static boolean isValidLanguageVersion(int version)
/*  277:     */   {
/*  278: 682 */     switch (version)
/*  279:     */     {
/*  280:     */     case 0: 
/*  281:     */     case 100: 
/*  282:     */     case 110: 
/*  283:     */     case 120: 
/*  284:     */     case 130: 
/*  285:     */     case 140: 
/*  286:     */     case 150: 
/*  287:     */     case 160: 
/*  288:     */     case 170: 
/*  289:     */     case 180: 
/*  290: 693 */       return true;
/*  291:     */     }
/*  292: 695 */     return false;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public static void checkLanguageVersion(int version)
/*  296:     */   {
/*  297: 700 */     if (isValidLanguageVersion(version)) {
/*  298: 701 */       return;
/*  299:     */     }
/*  300: 703 */     throw new IllegalArgumentException("Bad language version: " + version);
/*  301:     */   }
/*  302:     */   
/*  303:     */   public final String getImplementationVersion()
/*  304:     */   {
/*  305: 727 */     if (implementationVersion == null) {
/*  306: 728 */       implementationVersion = ScriptRuntime.getMessage0("implementation.version");
/*  307:     */     }
/*  308: 731 */     return implementationVersion;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public final ErrorReporter getErrorReporter()
/*  312:     */   {
/*  313: 741 */     if (this.errorReporter == null) {
/*  314: 742 */       return DefaultErrorReporter.instance;
/*  315:     */     }
/*  316: 744 */     return this.errorReporter;
/*  317:     */   }
/*  318:     */   
/*  319:     */   public final ErrorReporter setErrorReporter(ErrorReporter reporter)
/*  320:     */   {
/*  321: 755 */     if (this.sealed) {
/*  322: 755 */       onSealedMutation();
/*  323:     */     }
/*  324: 756 */     if (reporter == null) {
/*  325: 756 */       throw new IllegalArgumentException();
/*  326:     */     }
/*  327: 757 */     ErrorReporter old = getErrorReporter();
/*  328: 758 */     if (reporter == old) {
/*  329: 759 */       return old;
/*  330:     */     }
/*  331: 761 */     Object listeners = this.propertyListeners;
/*  332: 762 */     if (listeners != null) {
/*  333: 763 */       firePropertyChangeImpl(listeners, "error reporter", old, reporter);
/*  334:     */     }
/*  335: 766 */     this.errorReporter = reporter;
/*  336: 767 */     return old;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public final Locale getLocale()
/*  340:     */   {
/*  341: 779 */     if (this.locale == null) {
/*  342: 780 */       this.locale = Locale.getDefault();
/*  343:     */     }
/*  344: 781 */     return this.locale;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public final Locale setLocale(Locale loc)
/*  348:     */   {
/*  349: 791 */     if (this.sealed) {
/*  350: 791 */       onSealedMutation();
/*  351:     */     }
/*  352: 792 */     Locale result = this.locale;
/*  353: 793 */     this.locale = loc;
/*  354: 794 */     return result;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public final void addPropertyChangeListener(PropertyChangeListener l)
/*  358:     */   {
/*  359: 806 */     if (this.sealed) {
/*  360: 806 */       onSealedMutation();
/*  361:     */     }
/*  362: 807 */     this.propertyListeners = Kit.addListener(this.propertyListeners, l);
/*  363:     */   }
/*  364:     */   
/*  365:     */   public final void removePropertyChangeListener(PropertyChangeListener l)
/*  366:     */   {
/*  367: 819 */     if (this.sealed) {
/*  368: 819 */       onSealedMutation();
/*  369:     */     }
/*  370: 820 */     this.propertyListeners = Kit.removeListener(this.propertyListeners, l);
/*  371:     */   }
/*  372:     */   
/*  373:     */   final void firePropertyChange(String property, Object oldValue, Object newValue)
/*  374:     */   {
/*  375: 836 */     Object listeners = this.propertyListeners;
/*  376: 837 */     if (listeners != null) {
/*  377: 838 */       firePropertyChangeImpl(listeners, property, oldValue, newValue);
/*  378:     */     }
/*  379:     */   }
/*  380:     */   
/*  381:     */   private void firePropertyChangeImpl(Object listeners, String property, Object oldValue, Object newValue)
/*  382:     */   {
/*  383: 845 */     for (int i = 0;; i++)
/*  384:     */     {
/*  385: 846 */       Object l = Kit.getListener(listeners, i);
/*  386: 847 */       if (l == null) {
/*  387:     */         break;
/*  388:     */       }
/*  389: 849 */       if ((l instanceof PropertyChangeListener))
/*  390:     */       {
/*  391: 850 */         PropertyChangeListener pcl = (PropertyChangeListener)l;
/*  392: 851 */         pcl.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
/*  393:     */       }
/*  394:     */     }
/*  395:     */   }
/*  396:     */   
/*  397:     */   public static void reportWarning(String message, String sourceName, int lineno, String lineSource, int lineOffset)
/*  398:     */   {
/*  399: 871 */     Context cx = getContext();
/*  400: 872 */     if (cx.hasFeature(12)) {
/*  401: 873 */       reportError(message, sourceName, lineno, lineSource, lineOffset);
/*  402:     */     } else {
/*  403: 875 */       cx.getErrorReporter().warning(message, sourceName, lineno, lineSource, lineOffset);
/*  404:     */     }
/*  405:     */   }
/*  406:     */   
/*  407:     */   public static void reportWarning(String message)
/*  408:     */   {
/*  409: 887 */     int[] linep = { 0 };
/*  410: 888 */     String filename = getSourcePositionFromStack(linep);
/*  411: 889 */     reportWarning(message, filename, linep[0], null, 0);
/*  412:     */   }
/*  413:     */   
/*  414:     */   public static void reportWarning(String message, Throwable t)
/*  415:     */   {
/*  416: 894 */     int[] linep = { 0 };
/*  417: 895 */     String filename = getSourcePositionFromStack(linep);
/*  418: 896 */     Writer sw = new StringWriter();
/*  419: 897 */     PrintWriter pw = new PrintWriter(sw);
/*  420: 898 */     pw.println(message);
/*  421: 899 */     t.printStackTrace(pw);
/*  422: 900 */     pw.flush();
/*  423: 901 */     reportWarning(sw.toString(), filename, linep[0], null, 0);
/*  424:     */   }
/*  425:     */   
/*  426:     */   public static void reportError(String message, String sourceName, int lineno, String lineSource, int lineOffset)
/*  427:     */   {
/*  428: 918 */     Context cx = getCurrentContext();
/*  429: 919 */     if (cx != null) {
/*  430: 920 */       cx.getErrorReporter().error(message, sourceName, lineno, lineSource, lineOffset);
/*  431:     */     } else {
/*  432: 923 */       throw new EvaluatorException(message, sourceName, lineno, lineSource, lineOffset);
/*  433:     */     }
/*  434:     */   }
/*  435:     */   
/*  436:     */   public static void reportError(String message)
/*  437:     */   {
/*  438: 936 */     int[] linep = { 0 };
/*  439: 937 */     String filename = getSourcePositionFromStack(linep);
/*  440: 938 */     reportError(message, filename, linep[0], null, 0);
/*  441:     */   }
/*  442:     */   
/*  443:     */   public static EvaluatorException reportRuntimeError(String message, String sourceName, int lineno, String lineSource, int lineOffset)
/*  444:     */   {
/*  445: 959 */     Context cx = getCurrentContext();
/*  446: 960 */     if (cx != null) {
/*  447: 961 */       return cx.getErrorReporter().runtimeError(message, sourceName, lineno, lineSource, lineOffset);
/*  448:     */     }
/*  449: 965 */     throw new EvaluatorException(message, sourceName, lineno, lineSource, lineOffset);
/*  450:     */   }
/*  451:     */   
/*  452:     */   static EvaluatorException reportRuntimeError0(String messageId)
/*  453:     */   {
/*  454: 972 */     String msg = ScriptRuntime.getMessage0(messageId);
/*  455: 973 */     return reportRuntimeError(msg);
/*  456:     */   }
/*  457:     */   
/*  458:     */   static EvaluatorException reportRuntimeError1(String messageId, Object arg1)
/*  459:     */   {
/*  460: 979 */     String msg = ScriptRuntime.getMessage1(messageId, arg1);
/*  461: 980 */     return reportRuntimeError(msg);
/*  462:     */   }
/*  463:     */   
/*  464:     */   static EvaluatorException reportRuntimeError2(String messageId, Object arg1, Object arg2)
/*  465:     */   {
/*  466: 986 */     String msg = ScriptRuntime.getMessage2(messageId, arg1, arg2);
/*  467: 987 */     return reportRuntimeError(msg);
/*  468:     */   }
/*  469:     */   
/*  470:     */   static EvaluatorException reportRuntimeError3(String messageId, Object arg1, Object arg2, Object arg3)
/*  471:     */   {
/*  472: 994 */     String msg = ScriptRuntime.getMessage3(messageId, arg1, arg2, arg3);
/*  473: 995 */     return reportRuntimeError(msg);
/*  474:     */   }
/*  475:     */   
/*  476:     */   static EvaluatorException reportRuntimeError4(String messageId, Object arg1, Object arg2, Object arg3, Object arg4)
/*  477:     */   {
/*  478:1002 */     String msg = ScriptRuntime.getMessage4(messageId, arg1, arg2, arg3, arg4);
/*  479:     */     
/*  480:1004 */     return reportRuntimeError(msg);
/*  481:     */   }
/*  482:     */   
/*  483:     */   public static EvaluatorException reportRuntimeError(String message)
/*  484:     */   {
/*  485:1015 */     int[] linep = { 0 };
/*  486:1016 */     String filename = getSourcePositionFromStack(linep);
/*  487:1017 */     return reportRuntimeError(message, filename, linep[0], null, 0);
/*  488:     */   }
/*  489:     */   
/*  490:     */   public final ScriptableObject initStandardObjects()
/*  491:     */   {
/*  492:1036 */     return initStandardObjects(null, false);
/*  493:     */   }
/*  494:     */   
/*  495:     */   public final Scriptable initStandardObjects(ScriptableObject scope)
/*  496:     */   {
/*  497:1059 */     return initStandardObjects(scope, false);
/*  498:     */   }
/*  499:     */   
/*  500:     */   public ScriptableObject initStandardObjects(ScriptableObject scope, boolean sealed)
/*  501:     */   {
/*  502:1092 */     return ScriptRuntime.initStandardObjects(this, scope, sealed);
/*  503:     */   }
/*  504:     */   
/*  505:     */   public static Object getUndefinedValue()
/*  506:     */   {
/*  507:1100 */     return Undefined.instance;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public final Object evaluateString(Scriptable scope, String source, String sourceName, int lineno, Object securityDomain)
/*  511:     */   {
/*  512:1124 */     Script script = compileString(source, sourceName, lineno, securityDomain);
/*  513:1126 */     if (script != null) {
/*  514:1127 */       return script.exec(this, scope);
/*  515:     */     }
/*  516:1129 */     return null;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public final Object evaluateReader(Scriptable scope, Reader in, String sourceName, int lineno, Object securityDomain)
/*  520:     */     throws IOException
/*  521:     */   {
/*  522:1155 */     Script script = compileReader(scope, in, sourceName, lineno, securityDomain);
/*  523:1157 */     if (script != null) {
/*  524:1158 */       return script.exec(this, scope);
/*  525:     */     }
/*  526:1160 */     return null;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public Object executeScriptWithContinuations(Script script, Scriptable scope)
/*  530:     */     throws ContinuationPending
/*  531:     */   {
/*  532:1180 */     if ((!(script instanceof InterpretedFunction)) || (!((InterpretedFunction)script).isScript())) {
/*  533:1184 */       throw new IllegalArgumentException("Script argument was not a script or was not created by interpreted mode ");
/*  534:     */     }
/*  535:1187 */     return callFunctionWithContinuations((InterpretedFunction)script, scope, ScriptRuntime.emptyArgs);
/*  536:     */   }
/*  537:     */   
/*  538:     */   public Object callFunctionWithContinuations(Callable function, Scriptable scope, Object[] args)
/*  539:     */     throws ContinuationPending
/*  540:     */   {
/*  541:1208 */     if (!(function instanceof InterpretedFunction)) {
/*  542:1210 */       throw new IllegalArgumentException("Function argument was not created by interpreted mode ");
/*  543:     */     }
/*  544:1213 */     if (ScriptRuntime.hasTopCall(this)) {
/*  545:1214 */       throw new IllegalStateException("Cannot have any pending top calls when executing a script with continuations");
/*  546:     */     }
/*  547:1219 */     this.isContinuationsTopCall = true;
/*  548:1220 */     return ScriptRuntime.doTopCall(function, this, scope, scope, args);
/*  549:     */   }
/*  550:     */   
/*  551:     */   public ContinuationPending captureContinuation()
/*  552:     */   {
/*  553:1237 */     return new ContinuationPending(Interpreter.captureContinuation(this));
/*  554:     */   }
/*  555:     */   
/*  556:     */   public Object resumeContinuation(Object continuation, Scriptable scope, Object functionResult)
/*  557:     */     throws ContinuationPending
/*  558:     */   {
/*  559:1261 */     Object[] args = { functionResult };
/*  560:1262 */     return Interpreter.restartContinuation((NativeContinuation)continuation, this, scope, args);
/*  561:     */   }
/*  562:     */   
/*  563:     */   public final boolean stringIsCompilableUnit(String source)
/*  564:     */   {
/*  565:1285 */     boolean errorseen = false;
/*  566:1286 */     CompilerEnvirons compilerEnv = new CompilerEnvirons();
/*  567:1287 */     compilerEnv.initFromContext(this);
/*  568:     */     
/*  569:     */ 
/*  570:1290 */     compilerEnv.setGeneratingSource(false);
/*  571:1291 */     Parser p = new Parser(compilerEnv, DefaultErrorReporter.instance);
/*  572:     */     try
/*  573:     */     {
/*  574:1293 */       p.parse(source, null, 1);
/*  575:     */     }
/*  576:     */     catch (EvaluatorException ee)
/*  577:     */     {
/*  578:1295 */       errorseen = true;
/*  579:     */     }
/*  580:1300 */     if ((errorseen) && (p.eof())) {
/*  581:1301 */       return false;
/*  582:     */     }
/*  583:1303 */     return true;
/*  584:     */   }
/*  585:     */   
/*  586:     */   /**
/*  587:     */    * @deprecated
/*  588:     */    */
/*  589:     */   public final Script compileReader(Scriptable scope, Reader in, String sourceName, int lineno, Object securityDomain)
/*  590:     */     throws IOException
/*  591:     */   {
/*  592:1316 */     return compileReader(in, sourceName, lineno, securityDomain);
/*  593:     */   }
/*  594:     */   
/*  595:     */   public final Script compileReader(Reader in, String sourceName, int lineno, Object securityDomain)
/*  596:     */     throws IOException
/*  597:     */   {
/*  598:1340 */     if (lineno < 0) {
/*  599:1342 */       lineno = 0;
/*  600:     */     }
/*  601:1344 */     return (Script)compileImpl(null, in, null, sourceName, lineno, securityDomain, false, null, null);
/*  602:     */   }
/*  603:     */   
/*  604:     */   public final Script compileString(String source, String sourceName, int lineno, Object securityDomain)
/*  605:     */   {
/*  606:1368 */     if (lineno < 0) {
/*  607:1370 */       lineno = 0;
/*  608:     */     }
/*  609:1372 */     return compileString(source, null, null, sourceName, lineno, securityDomain);
/*  610:     */   }
/*  611:     */   
/*  612:     */   protected Script compileString(String source, Evaluator compiler, ErrorReporter compilationErrorReporter, String sourceName, int lineno, Object securityDomain)
/*  613:     */   {
/*  614:     */     try
/*  615:     */     {
/*  616:1383 */       return (Script)compileImpl(null, null, source, sourceName, lineno, securityDomain, false, compiler, compilationErrorReporter);
/*  617:     */     }
/*  618:     */     catch (IOException ex)
/*  619:     */     {
/*  620:1388 */       throw new RuntimeException();
/*  621:     */     }
/*  622:     */   }
/*  623:     */   
/*  624:     */   public final Function compileFunction(Scriptable scope, String source, String sourceName, int lineno, Object securityDomain)
/*  625:     */   {
/*  626:1413 */     return compileFunction(scope, source, null, null, sourceName, lineno, securityDomain);
/*  627:     */   }
/*  628:     */   
/*  629:     */   final Function compileFunction(Scriptable scope, String source, Evaluator compiler, ErrorReporter compilationErrorReporter, String sourceName, int lineno, Object securityDomain)
/*  630:     */   {
/*  631:     */     try
/*  632:     */     {
/*  633:1424 */       return (Function)compileImpl(scope, null, source, sourceName, lineno, securityDomain, true, compiler, compilationErrorReporter);
/*  634:     */     }
/*  635:     */     catch (IOException ioe)
/*  636:     */     {
/*  637:1431 */       throw new RuntimeException();
/*  638:     */     }
/*  639:     */   }
/*  640:     */   
/*  641:     */   public final String decompileScript(Script script, int indent)
/*  642:     */   {
/*  643:1446 */     NativeFunction scriptImpl = (NativeFunction)script;
/*  644:1447 */     return scriptImpl.decompile(indent, 0);
/*  645:     */   }
/*  646:     */   
/*  647:     */   public final String decompileFunction(Function fun, int indent)
/*  648:     */   {
/*  649:1465 */     if ((fun instanceof BaseFunction)) {
/*  650:1466 */       return ((BaseFunction)fun).decompile(indent, 0);
/*  651:     */     }
/*  652:1468 */     return "function " + fun.getClassName() + "() {\n\t[native code]\n}\n";
/*  653:     */   }
/*  654:     */   
/*  655:     */   public final String decompileFunctionBody(Function fun, int indent)
/*  656:     */   {
/*  657:1487 */     if ((fun instanceof BaseFunction))
/*  658:     */     {
/*  659:1488 */       BaseFunction bf = (BaseFunction)fun;
/*  660:1489 */       return bf.decompile(indent, 1);
/*  661:     */     }
/*  662:1492 */     return "[native code]\n";
/*  663:     */   }
/*  664:     */   
/*  665:     */   public Scriptable newObject(Scriptable scope)
/*  666:     */   {
/*  667:1505 */     NativeObject result = new NativeObject();
/*  668:1506 */     ScriptRuntime.setBuiltinProtoAndParent(result, scope, TopLevel.Builtins.Object);
/*  669:     */     
/*  670:1508 */     return result;
/*  671:     */   }
/*  672:     */   
/*  673:     */   public Scriptable newObject(Scriptable scope, String constructorName)
/*  674:     */   {
/*  675:1523 */     return newObject(scope, constructorName, ScriptRuntime.emptyArgs);
/*  676:     */   }
/*  677:     */   
/*  678:     */   public Scriptable newObject(Scriptable scope, String constructorName, Object[] args)
/*  679:     */   {
/*  680:1548 */     scope = ScriptableObject.getTopLevelScope(scope);
/*  681:1549 */     Function ctor = ScriptRuntime.getExistingCtor(this, scope, constructorName);
/*  682:1551 */     if (args == null) {
/*  683:1551 */       args = ScriptRuntime.emptyArgs;
/*  684:     */     }
/*  685:1552 */     return ctor.construct(this, scope, args);
/*  686:     */   }
/*  687:     */   
/*  688:     */   public Scriptable newArray(Scriptable scope, int length)
/*  689:     */   {
/*  690:1565 */     NativeArray result = new NativeArray(length);
/*  691:1566 */     ScriptRuntime.setBuiltinProtoAndParent(result, scope, TopLevel.Builtins.Array);
/*  692:     */     
/*  693:1568 */     return result;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public Scriptable newArray(Scriptable scope, Object[] elements)
/*  697:     */   {
/*  698:1583 */     if (elements.getClass().getComponentType() != ScriptRuntime.ObjectClass) {
/*  699:1584 */       throw new IllegalArgumentException();
/*  700:     */     }
/*  701:1585 */     NativeArray result = new NativeArray(elements);
/*  702:1586 */     ScriptRuntime.setBuiltinProtoAndParent(result, scope, TopLevel.Builtins.Array);
/*  703:     */     
/*  704:1588 */     return result;
/*  705:     */   }
/*  706:     */   
/*  707:     */   public final Object[] getElements(Scriptable object)
/*  708:     */   {
/*  709:1610 */     return ScriptRuntime.getArrayElements(object);
/*  710:     */   }
/*  711:     */   
/*  712:     */   public static boolean toBoolean(Object value)
/*  713:     */   {
/*  714:1624 */     return ScriptRuntime.toBoolean(value);
/*  715:     */   }
/*  716:     */   
/*  717:     */   public static double toNumber(Object value)
/*  718:     */   {
/*  719:1640 */     return ScriptRuntime.toNumber(value);
/*  720:     */   }
/*  721:     */   
/*  722:     */   public static String toString(Object value)
/*  723:     */   {
/*  724:1654 */     return ScriptRuntime.toString(value);
/*  725:     */   }
/*  726:     */   
/*  727:     */   public static Scriptable toObject(Object value, Scriptable scope)
/*  728:     */   {
/*  729:1676 */     return ScriptRuntime.toObject(scope, value);
/*  730:     */   }
/*  731:     */   
/*  732:     */   /**
/*  733:     */    * @deprecated
/*  734:     */    */
/*  735:     */   public static Scriptable toObject(Object value, Scriptable scope, Class<?> staticType)
/*  736:     */   {
/*  737:1686 */     return ScriptRuntime.toObject(scope, value);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public static Object javaToJS(Object value, Scriptable scope)
/*  741:     */   {
/*  742:1719 */     if (((value instanceof String)) || ((value instanceof Number)) || ((value instanceof Boolean)) || ((value instanceof Scriptable))) {
/*  743:1722 */       return value;
/*  744:     */     }
/*  745:1723 */     if ((value instanceof Character)) {
/*  746:1724 */       return String.valueOf(((Character)value).charValue());
/*  747:     */     }
/*  748:1726 */     Context cx = getContext();
/*  749:1727 */     return cx.getWrapFactory().wrap(cx, scope, value, null);
/*  750:     */   }
/*  751:     */   
/*  752:     */   public static Object jsToJava(Object value, Class<?> desiredType)
/*  753:     */     throws EvaluatorException
/*  754:     */   {
/*  755:1745 */     return NativeJavaObject.coerceTypeImpl(desiredType, value);
/*  756:     */   }
/*  757:     */   
/*  758:     */   /**
/*  759:     */    * @deprecated
/*  760:     */    */
/*  761:     */   public static Object toType(Object value, Class<?> desiredType)
/*  762:     */     throws IllegalArgumentException
/*  763:     */   {
/*  764:     */     try
/*  765:     */     {
/*  766:1759 */       return jsToJava(value, desiredType);
/*  767:     */     }
/*  768:     */     catch (EvaluatorException ex)
/*  769:     */     {
/*  770:1762 */       IllegalArgumentException ex2 = new IllegalArgumentException(ex.getMessage());
/*  771:1763 */       Kit.initCause(ex2, ex);
/*  772:1764 */       throw ex2;
/*  773:     */     }
/*  774:     */   }
/*  775:     */   
/*  776:     */   public static RuntimeException throwAsScriptRuntimeEx(Throwable e)
/*  777:     */   {
/*  778:1787 */     while ((e instanceof InvocationTargetException)) {
/*  779:1788 */       e = ((InvocationTargetException)e).getTargetException();
/*  780:     */     }
/*  781:1791 */     if ((e instanceof Error))
/*  782:     */     {
/*  783:1792 */       Context cx = getContext();
/*  784:1793 */       if ((cx == null) || (!cx.hasFeature(13))) {
/*  785:1796 */         throw ((Error)e);
/*  786:     */       }
/*  787:     */     }
/*  788:1799 */     if ((e instanceof RhinoException)) {
/*  789:1800 */       throw ((RhinoException)e);
/*  790:     */     }
/*  791:1802 */     throw new WrappedException(e);
/*  792:     */   }
/*  793:     */   
/*  794:     */   public final boolean isGeneratingDebug()
/*  795:     */   {
/*  796:1811 */     return this.generatingDebug;
/*  797:     */   }
/*  798:     */   
/*  799:     */   public final void setGeneratingDebug(boolean generatingDebug)
/*  800:     */   {
/*  801:1823 */     if (this.sealed) {
/*  802:1823 */       onSealedMutation();
/*  803:     */     }
/*  804:1824 */     this.generatingDebugChanged = true;
/*  805:1825 */     if ((generatingDebug) && (getOptimizationLevel() > 0)) {
/*  806:1826 */       setOptimizationLevel(0);
/*  807:     */     }
/*  808:1827 */     this.generatingDebug = generatingDebug;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public final boolean isGeneratingSource()
/*  812:     */   {
/*  813:1836 */     return this.generatingSource;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public final void setGeneratingSource(boolean generatingSource)
/*  817:     */   {
/*  818:1851 */     if (this.sealed) {
/*  819:1851 */       onSealedMutation();
/*  820:     */     }
/*  821:1852 */     this.generatingSource = generatingSource;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public final int getOptimizationLevel()
/*  825:     */   {
/*  826:1865 */     return this.optimizationLevel;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public final void setOptimizationLevel(int optimizationLevel)
/*  830:     */   {
/*  831:1887 */     if (this.sealed) {
/*  832:1887 */       onSealedMutation();
/*  833:     */     }
/*  834:1888 */     if (optimizationLevel == -2) {
/*  835:1890 */       optimizationLevel = -1;
/*  836:     */     }
/*  837:1892 */     checkOptimizationLevel(optimizationLevel);
/*  838:1893 */     if (codegenClass == null) {
/*  839:1894 */       optimizationLevel = -1;
/*  840:     */     }
/*  841:1895 */     this.optimizationLevel = optimizationLevel;
/*  842:     */   }
/*  843:     */   
/*  844:     */   public static boolean isValidOptimizationLevel(int optimizationLevel)
/*  845:     */   {
/*  846:1900 */     return (-1 <= optimizationLevel) && (optimizationLevel <= 9);
/*  847:     */   }
/*  848:     */   
/*  849:     */   public static void checkOptimizationLevel(int optimizationLevel)
/*  850:     */   {
/*  851:1905 */     if (isValidOptimizationLevel(optimizationLevel)) {
/*  852:1906 */       return;
/*  853:     */     }
/*  854:1908 */     throw new IllegalArgumentException("Optimization level outside [-1..9]: " + optimizationLevel);
/*  855:     */   }
/*  856:     */   
/*  857:     */   public final int getMaximumInterpreterStackDepth()
/*  858:     */   {
/*  859:1928 */     return this.maximumInterpreterStackDepth;
/*  860:     */   }
/*  861:     */   
/*  862:     */   public final void setMaximumInterpreterStackDepth(int max)
/*  863:     */   {
/*  864:1950 */     if (this.sealed) {
/*  865:1950 */       onSealedMutation();
/*  866:     */     }
/*  867:1951 */     if (this.optimizationLevel != -1) {
/*  868:1952 */       throw new IllegalStateException("Cannot set maximumInterpreterStackDepth when optimizationLevel != -1");
/*  869:     */     }
/*  870:1954 */     if (max < 1) {
/*  871:1955 */       throw new IllegalArgumentException("Cannot set maximumInterpreterStackDepth to less than 1");
/*  872:     */     }
/*  873:1957 */     this.maximumInterpreterStackDepth = max;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public final void setSecurityController(SecurityController controller)
/*  877:     */   {
/*  878:1973 */     if (this.sealed) {
/*  879:1973 */       onSealedMutation();
/*  880:     */     }
/*  881:1974 */     if (controller == null) {
/*  882:1974 */       throw new IllegalArgumentException();
/*  883:     */     }
/*  884:1975 */     if (this.securityController != null) {
/*  885:1976 */       throw new SecurityException("Can not overwrite existing SecurityController object");
/*  886:     */     }
/*  887:1978 */     if (SecurityController.hasGlobal()) {
/*  888:1979 */       throw new SecurityException("Can not overwrite existing global SecurityController object");
/*  889:     */     }
/*  890:1981 */     this.securityController = controller;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public final synchronized void setClassShutter(ClassShutter shutter)
/*  894:     */   {
/*  895:1994 */     if (this.sealed) {
/*  896:1994 */       onSealedMutation();
/*  897:     */     }
/*  898:1995 */     if (shutter == null) {
/*  899:1995 */       throw new IllegalArgumentException();
/*  900:     */     }
/*  901:1996 */     if (this.hasClassShutter) {
/*  902:1997 */       throw new SecurityException("Cannot overwrite existing ClassShutter object");
/*  903:     */     }
/*  904:2000 */     this.classShutter = shutter;
/*  905:2001 */     this.hasClassShutter = true;
/*  906:     */   }
/*  907:     */   
/*  908:     */   final synchronized ClassShutter getClassShutter()
/*  909:     */   {
/*  910:2006 */     return this.classShutter;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public final synchronized ClassShutterSetter getClassShutterSetter()
/*  914:     */   {
/*  915:2015 */     if (this.hasClassShutter) {
/*  916:2016 */       return null;
/*  917:     */     }
/*  918:2017 */     this.hasClassShutter = true;
/*  919:2018 */     new ClassShutterSetter()
/*  920:     */     {
/*  921:     */       public void setClassShutter(ClassShutter shutter)
/*  922:     */       {
/*  923:2020 */         Context.this.classShutter = shutter;
/*  924:     */       }
/*  925:     */       
/*  926:     */       public ClassShutter getClassShutter()
/*  927:     */       {
/*  928:2023 */         return Context.this.classShutter;
/*  929:     */       }
/*  930:     */     };
/*  931:     */   }
/*  932:     */   
/*  933:     */   public final Object getThreadLocal(Object key)
/*  934:     */   {
/*  935:2045 */     if (this.threadLocalMap == null) {
/*  936:2046 */       return null;
/*  937:     */     }
/*  938:2047 */     return this.threadLocalMap.get(key);
/*  939:     */   }
/*  940:     */   
/*  941:     */   public final synchronized void putThreadLocal(Object key, Object value)
/*  942:     */   {
/*  943:2058 */     if (this.sealed) {
/*  944:2058 */       onSealedMutation();
/*  945:     */     }
/*  946:2059 */     if (this.threadLocalMap == null) {
/*  947:2060 */       this.threadLocalMap = new HashMap();
/*  948:     */     }
/*  949:2061 */     this.threadLocalMap.put(key, value);
/*  950:     */   }
/*  951:     */   
/*  952:     */   public final void removeThreadLocal(Object key)
/*  953:     */   {
/*  954:2071 */     if (this.sealed) {
/*  955:2071 */       onSealedMutation();
/*  956:     */     }
/*  957:2072 */     if (this.threadLocalMap == null) {
/*  958:2073 */       return;
/*  959:     */     }
/*  960:2074 */     this.threadLocalMap.remove(key);
/*  961:     */   }
/*  962:     */   
/*  963:     */   /**
/*  964:     */    * @deprecated
/*  965:     */    */
/*  966:     */   public final boolean hasCompileFunctionsWithDynamicScope()
/*  967:     */   {
/*  968:2084 */     return this.compileFunctionsWithDynamicScopeFlag;
/*  969:     */   }
/*  970:     */   
/*  971:     */   /**
/*  972:     */    * @deprecated
/*  973:     */    */
/*  974:     */   public final void setCompileFunctionsWithDynamicScope(boolean flag)
/*  975:     */   {
/*  976:2094 */     if (this.sealed) {
/*  977:2094 */       onSealedMutation();
/*  978:     */     }
/*  979:2095 */     this.compileFunctionsWithDynamicScopeFlag = flag;
/*  980:     */   }
/*  981:     */   
/*  982:     */   /**
/*  983:     */    * @deprecated
/*  984:     */    */
/*  985:     */   public static void setCachingEnabled(boolean cachingEnabled) {}
/*  986:     */   
/*  987:     */   public final void setWrapFactory(WrapFactory wrapFactory)
/*  988:     */   {
/*  989:2117 */     if (this.sealed) {
/*  990:2117 */       onSealedMutation();
/*  991:     */     }
/*  992:2118 */     if (wrapFactory == null) {
/*  993:2118 */       throw new IllegalArgumentException();
/*  994:     */     }
/*  995:2119 */     this.wrapFactory = wrapFactory;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public final WrapFactory getWrapFactory()
/*  999:     */   {
/* 1000:2129 */     if (this.wrapFactory == null) {
/* 1001:2130 */       this.wrapFactory = new WrapFactory();
/* 1002:     */     }
/* 1003:2132 */     return this.wrapFactory;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public final Debugger getDebugger()
/* 1007:     */   {
/* 1008:2141 */     return this.debugger;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public final Object getDebuggerContextData()
/* 1012:     */   {
/* 1013:2150 */     return this.debuggerData;
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public final void setDebugger(Debugger debugger, Object contextData)
/* 1017:     */   {
/* 1018:2162 */     if (this.sealed) {
/* 1019:2162 */       onSealedMutation();
/* 1020:     */     }
/* 1021:2163 */     this.debugger = debugger;
/* 1022:2164 */     this.debuggerData = contextData;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public static DebuggableScript getDebuggableView(Script script)
/* 1026:     */   {
/* 1027:2174 */     if ((script instanceof NativeFunction)) {
/* 1028:2175 */       return ((NativeFunction)script).getDebuggableView();
/* 1029:     */     }
/* 1030:2177 */     return null;
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public boolean hasFeature(int featureIndex)
/* 1034:     */   {
/* 1035:2208 */     ContextFactory f = getFactory();
/* 1036:2209 */     return f.hasFeature(this, featureIndex);
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public XMLLib.Factory getE4xImplementationFactory()
/* 1040:     */   {
/* 1041:2224 */     return getFactory().getE4xImplementationFactory();
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public final int getInstructionObserverThreshold()
/* 1045:     */   {
/* 1046:2237 */     return this.instructionThreshold;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public final void setInstructionObserverThreshold(int threshold)
/* 1050:     */   {
/* 1051:2257 */     if (this.sealed) {
/* 1052:2257 */       onSealedMutation();
/* 1053:     */     }
/* 1054:2258 */     if (threshold < 0) {
/* 1055:2258 */       throw new IllegalArgumentException();
/* 1056:     */     }
/* 1057:2259 */     this.instructionThreshold = threshold;
/* 1058:2260 */     setGenerateObserverCount(threshold > 0);
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public void setGenerateObserverCount(boolean generateObserverCount)
/* 1062:     */   {
/* 1063:2275 */     this.generateObserverCount = generateObserverCount;
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   protected void observeInstructionCount(int instructionCount)
/* 1067:     */   {
/* 1068:2299 */     ContextFactory f = getFactory();
/* 1069:2300 */     f.observeInstructionCount(this, instructionCount);
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   public GeneratedClassLoader createClassLoader(ClassLoader parent)
/* 1073:     */   {
/* 1074:2310 */     ContextFactory f = getFactory();
/* 1075:2311 */     return f.createClassLoader(parent);
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public final ClassLoader getApplicationClassLoader()
/* 1079:     */   {
/* 1080:2316 */     if (this.applicationClassLoader == null)
/* 1081:     */     {
/* 1082:2317 */       ContextFactory f = getFactory();
/* 1083:2318 */       ClassLoader loader = f.getApplicationClassLoader();
/* 1084:2319 */       if (loader == null)
/* 1085:     */       {
/* 1086:2320 */         ClassLoader threadLoader = VMBridge.instance.getCurrentThreadClassLoader();
/* 1087:2322 */         if ((threadLoader != null) && (Kit.testIfCanLoadRhinoClasses(threadLoader))) {
/* 1088:2329 */           return threadLoader;
/* 1089:     */         }
/* 1090:2334 */         Class<?> fClass = f.getClass();
/* 1091:2335 */         if (fClass != ScriptRuntime.ContextFactoryClass) {
/* 1092:2336 */           loader = fClass.getClassLoader();
/* 1093:     */         } else {
/* 1094:2338 */           loader = getClass().getClassLoader();
/* 1095:     */         }
/* 1096:     */       }
/* 1097:2341 */       this.applicationClassLoader = loader;
/* 1098:     */     }
/* 1099:2343 */     return this.applicationClassLoader;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public final void setApplicationClassLoader(ClassLoader loader)
/* 1103:     */   {
/* 1104:2348 */     if (this.sealed) {
/* 1105:2348 */       onSealedMutation();
/* 1106:     */     }
/* 1107:2349 */     if (loader == null)
/* 1108:     */     {
/* 1109:2351 */       this.applicationClassLoader = null;
/* 1110:2352 */       return;
/* 1111:     */     }
/* 1112:2354 */     if (!Kit.testIfCanLoadRhinoClasses(loader)) {
/* 1113:2355 */       throw new IllegalArgumentException("Loader can not resolve Rhino classes");
/* 1114:     */     }
/* 1115:2358 */     this.applicationClassLoader = loader;
/* 1116:     */   }
/* 1117:     */   
/* 1118:     */   static Context getContext()
/* 1119:     */   {
/* 1120:2369 */     Context cx = getCurrentContext();
/* 1121:2370 */     if (cx == null) {
/* 1122:2371 */       throw new RuntimeException("No Context associated with current Thread");
/* 1123:     */     }
/* 1124:2374 */     return cx;
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   private Object compileImpl(Scriptable scope, Reader sourceReader, String sourceString, String sourceName, int lineno, Object securityDomain, boolean returnFunction, Evaluator compiler, ErrorReporter compilationErrorReporter)
/* 1128:     */     throws IOException
/* 1129:     */   {
/* 1130:2385 */     if (sourceName == null) {
/* 1131:2386 */       sourceName = "unnamed script";
/* 1132:     */     }
/* 1133:2388 */     if ((securityDomain != null) && (getSecurityController() == null)) {
/* 1134:2389 */       throw new IllegalArgumentException("securityDomain should be null if setSecurityController() was never called");
/* 1135:     */     }
/* 1136:2394 */     if (((sourceReader == null ? 1 : 0) ^ (sourceString == null ? 1 : 0)) == 0) {
/* 1137:2394 */       Kit.codeBug();
/* 1138:     */     }
/* 1139:2396 */     if (!(scope == null ^ returnFunction)) {
/* 1140:2396 */       Kit.codeBug();
/* 1141:     */     }
/* 1142:2398 */     CompilerEnvirons compilerEnv = new CompilerEnvirons();
/* 1143:2399 */     compilerEnv.initFromContext(this);
/* 1144:2400 */     if (compilationErrorReporter == null) {
/* 1145:2401 */       compilationErrorReporter = compilerEnv.getErrorReporter();
/* 1146:     */     }
/* 1147:2404 */     if ((this.debugger != null) && 
/* 1148:2405 */       (sourceReader != null))
/* 1149:     */     {
/* 1150:2406 */       sourceString = Kit.readReader(sourceReader);
/* 1151:2407 */       sourceReader = null;
/* 1152:     */     }
/* 1153:2411 */     Parser p = new Parser(compilerEnv, compilationErrorReporter);
/* 1154:2412 */     if (returnFunction) {
/* 1155:2413 */       p.calledByCompileFunction = true;
/* 1156:     */     }
/* 1157:     */     AstRoot ast;
/* 1158:2416 */     if (sourceString != null) {
/* 1159:2417 */       ast = p.parse(sourceString, sourceName, lineno);
/* 1160:     */     } else {
/* 1161:2419 */       ast = p.parse(sourceReader, sourceName, lineno);
/* 1162:     */     }
/* 1163:2421 */     if (returnFunction) {
/* 1164:2423 */       if ((ast.getFirstChild() == null) || (ast.getFirstChild().getType() != 109)) {
/* 1165:2429 */         throw new IllegalArgumentException("compileFunction only accepts source with single JS function: " + sourceString);
/* 1166:     */       }
/* 1167:     */     }
/* 1168:2434 */     IRFactory irf = new IRFactory(compilerEnv, compilationErrorReporter);
/* 1169:2435 */     ScriptNode tree = irf.transformTree(ast);
/* 1170:     */     
/* 1171:     */ 
/* 1172:2438 */     p = null;
/* 1173:2439 */     AstRoot ast = null;
/* 1174:2440 */     irf = null;
/* 1175:2442 */     if (compiler == null) {
/* 1176:2443 */       compiler = createCompiler();
/* 1177:     */     }
/* 1178:2446 */     Object bytecode = compiler.compile(compilerEnv, tree, tree.getEncodedSource(), returnFunction);
/* 1179:2449 */     if (this.debugger != null)
/* 1180:     */     {
/* 1181:2450 */       if (sourceString == null) {
/* 1182:2450 */         Kit.codeBug();
/* 1183:     */       }
/* 1184:2451 */       if ((bytecode instanceof DebuggableScript))
/* 1185:     */       {
/* 1186:2452 */         DebuggableScript dscript = (DebuggableScript)bytecode;
/* 1187:2453 */         notifyDebugger_r(this, dscript, sourceString);
/* 1188:     */       }
/* 1189:     */       else
/* 1190:     */       {
/* 1191:2455 */         throw new RuntimeException("NOT SUPPORTED");
/* 1192:     */       }
/* 1193:     */     }
/* 1194:     */     Object result;
/* 1195:     */     Object result;
/* 1196:2460 */     if (returnFunction) {
/* 1197:2461 */       result = compiler.createFunctionObject(this, scope, bytecode, securityDomain);
/* 1198:     */     } else {
/* 1199:2463 */       result = compiler.createScriptObject(bytecode, securityDomain);
/* 1200:     */     }
/* 1201:2466 */     return result;
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   private static void notifyDebugger_r(Context cx, DebuggableScript dscript, String debugSource)
/* 1205:     */   {
/* 1206:2472 */     cx.debugger.handleCompilationDone(cx, dscript, debugSource);
/* 1207:2473 */     for (int i = 0; i != dscript.getFunctionCount(); i++) {
/* 1208:2474 */       notifyDebugger_r(cx, dscript.getFunction(i), debugSource);
/* 1209:     */     }
/* 1210:     */   }
/* 1211:     */   
/* 1212:2478 */   private static Class<?> codegenClass = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.optimizer.Codegen");
/* 1213:2480 */   private static Class<?> interpreterClass = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.Interpreter");
/* 1214:     */   private static String implementationVersion;
/* 1215:     */   private final ContextFactory factory;
/* 1216:     */   private boolean sealed;
/* 1217:     */   private Object sealKey;
/* 1218:     */   Scriptable topCallScope;
/* 1219:     */   boolean isContinuationsTopCall;
/* 1220:     */   NativeCall currentActivationCall;
/* 1221:     */   XMLLib cachedXMLLib;
/* 1222:     */   ObjToIntMap iterating;
/* 1223:     */   Object interpreterSecurityDomain;
/* 1224:     */   int version;
/* 1225:     */   private SecurityController securityController;
/* 1226:     */   private boolean hasClassShutter;
/* 1227:     */   private ClassShutter classShutter;
/* 1228:     */   private ErrorReporter errorReporter;
/* 1229:     */   RegExpProxy regExpProxy;
/* 1230:     */   private Locale locale;
/* 1231:     */   private boolean generatingDebug;
/* 1232:     */   private boolean generatingDebugChanged;
/* 1233:     */   
/* 1234:     */   private Evaluator createCompiler()
/* 1235:     */   {
/* 1236:2485 */     Evaluator result = null;
/* 1237:2486 */     if ((this.optimizationLevel >= 0) && (codegenClass != null)) {
/* 1238:2487 */       result = (Evaluator)Kit.newInstanceOrNull(codegenClass);
/* 1239:     */     }
/* 1240:2489 */     if (result == null) {
/* 1241:2490 */       result = createInterpreter();
/* 1242:     */     }
/* 1243:2492 */     return result;
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   static Evaluator createInterpreter()
/* 1247:     */   {
/* 1248:2497 */     return (Evaluator)Kit.newInstanceOrNull(interpreterClass);
/* 1249:     */   }
/* 1250:     */   
/* 1251:     */   static String getSourcePositionFromStack(int[] linep)
/* 1252:     */   {
/* 1253:2502 */     Context cx = getCurrentContext();
/* 1254:2503 */     if (cx == null) {
/* 1255:2504 */       return null;
/* 1256:     */     }
/* 1257:2505 */     if (cx.lastInterpreterFrame != null)
/* 1258:     */     {
/* 1259:2506 */       Evaluator evaluator = createInterpreter();
/* 1260:2507 */       if (evaluator != null) {
/* 1261:2508 */         return evaluator.getSourcePositionFromStack(cx, linep);
/* 1262:     */       }
/* 1263:     */     }
/* 1264:2514 */     CharArrayWriter writer = new CharArrayWriter();
/* 1265:2515 */     RuntimeException re = new RuntimeException();
/* 1266:2516 */     re.printStackTrace(new PrintWriter(writer));
/* 1267:2517 */     String s = writer.toString();
/* 1268:2518 */     int open = -1;
/* 1269:2519 */     int close = -1;
/* 1270:2520 */     int colon = -1;
/* 1271:2521 */     for (int i = 0; i < s.length(); i++)
/* 1272:     */     {
/* 1273:2522 */       char c = s.charAt(i);
/* 1274:2523 */       if (c == ':')
/* 1275:     */       {
/* 1276:2524 */         colon = i;
/* 1277:     */       }
/* 1278:2525 */       else if (c == '(')
/* 1279:     */       {
/* 1280:2526 */         open = i;
/* 1281:     */       }
/* 1282:2527 */       else if (c == ')')
/* 1283:     */       {
/* 1284:2528 */         close = i;
/* 1285:     */       }
/* 1286:2529 */       else if ((c == '\n') && (open != -1) && (close != -1) && (colon != -1) && (open < colon) && (colon < close))
/* 1287:     */       {
/* 1288:2532 */         String fileStr = s.substring(open + 1, colon);
/* 1289:2533 */         if (!fileStr.endsWith(".java"))
/* 1290:     */         {
/* 1291:2534 */           String lineStr = s.substring(colon + 1, close);
/* 1292:     */           try
/* 1293:     */           {
/* 1294:2536 */             linep[0] = Integer.parseInt(lineStr);
/* 1295:2537 */             if (linep[0] < 0) {
/* 1296:2538 */               linep[0] = 0;
/* 1297:     */             }
/* 1298:2540 */             return fileStr;
/* 1299:     */           }
/* 1300:     */           catch (NumberFormatException e) {}
/* 1301:     */         }
/* 1302:2546 */         open = close = colon = -1;
/* 1303:     */       }
/* 1304:     */     }
/* 1305:2550 */     return null;
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   RegExpProxy getRegExpProxy()
/* 1309:     */   {
/* 1310:2555 */     if (this.regExpProxy == null)
/* 1311:     */     {
/* 1312:2556 */       Class<?> cl = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.regexp.RegExpImpl");
/* 1313:2558 */       if (cl != null) {
/* 1314:2559 */         this.regExpProxy = ((RegExpProxy)Kit.newInstanceOrNull(cl));
/* 1315:     */       }
/* 1316:     */     }
/* 1317:2562 */     return this.regExpProxy;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   final boolean isVersionECMA1()
/* 1321:     */   {
/* 1322:2567 */     return (this.version == 0) || (this.version >= 130);
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   SecurityController getSecurityController()
/* 1326:     */   {
/* 1327:2573 */     SecurityController global = SecurityController.global();
/* 1328:2574 */     if (global != null) {
/* 1329:2575 */       return global;
/* 1330:     */     }
/* 1331:2577 */     return this.securityController;
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public final boolean isGeneratingDebugChanged()
/* 1335:     */   {
/* 1336:2582 */     return this.generatingDebugChanged;
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   public void addActivationName(String name)
/* 1340:     */   {
/* 1341:2593 */     if (this.sealed) {
/* 1342:2593 */       onSealedMutation();
/* 1343:     */     }
/* 1344:2594 */     if (this.activationNames == null) {
/* 1345:2595 */       this.activationNames = new HashSet();
/* 1346:     */     }
/* 1347:2596 */     this.activationNames.add(name);
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   public final boolean isActivationNeeded(String name)
/* 1351:     */   {
/* 1352:2609 */     return (this.activationNames != null) && (this.activationNames.contains(name));
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public void removeActivationName(String name)
/* 1356:     */   {
/* 1357:2620 */     if (this.sealed) {
/* 1358:2620 */       onSealedMutation();
/* 1359:     */     }
/* 1360:2621 */     if (this.activationNames != null) {
/* 1361:2622 */       this.activationNames.remove(name);
/* 1362:     */     }
/* 1363:     */   }
/* 1364:     */   
/* 1365:2653 */   private boolean generatingSource = true;
/* 1366:     */   boolean compileFunctionsWithDynamicScopeFlag;
/* 1367:     */   boolean useDynamicScope;
/* 1368:     */   private int optimizationLevel;
/* 1369:     */   private int maximumInterpreterStackDepth;
/* 1370:     */   private WrapFactory wrapFactory;
/* 1371:     */   Debugger debugger;
/* 1372:     */   private Object debuggerData;
/* 1373:     */   private int enterCount;
/* 1374:     */   private Object propertyListeners;
/* 1375:     */   private Map<Object, Object> threadLocalMap;
/* 1376:     */   private ClassLoader applicationClassLoader;
/* 1377:     */   Set<String> activationNames;
/* 1378:     */   Object lastInterpreterFrame;
/* 1379:     */   ObjArray previousInterpreterInvocations;
/* 1380:     */   int instructionCount;
/* 1381:     */   int instructionThreshold;
/* 1382:     */   int scratchIndex;
/* 1383:     */   long scratchUint32;
/* 1384:     */   Scriptable scratchScriptable;
/* 1385:2693 */   public boolean generateObserverCount = false;
/* 1386:     */   
/* 1387:     */   public static abstract interface ClassShutterSetter
/* 1388:     */   {
/* 1389:     */     public abstract void setClassShutter(ClassShutter paramClassShutter);
/* 1390:     */     
/* 1391:     */     public abstract ClassShutter getClassShutter();
/* 1392:     */   }
/* 1393:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Context
 * JD-Core Version:    0.7.0.1
 */