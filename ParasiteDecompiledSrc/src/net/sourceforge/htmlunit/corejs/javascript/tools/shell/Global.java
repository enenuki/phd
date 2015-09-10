/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*    2:     */ 
/*    3:     */ import java.io.ByteArrayInputStream;
/*    4:     */ import java.io.ByteArrayOutputStream;
/*    5:     */ import java.io.File;
/*    6:     */ import java.io.FileInputStream;
/*    7:     */ import java.io.FileNotFoundException;
/*    8:     */ import java.io.FileOutputStream;
/*    9:     */ import java.io.IOException;
/*   10:     */ import java.io.InputStream;
/*   11:     */ import java.io.InputStreamReader;
/*   12:     */ import java.io.ObjectInputStream;
/*   13:     */ import java.io.OutputStream;
/*   14:     */ import java.io.PrintStream;
/*   15:     */ import java.io.Reader;
/*   16:     */ import java.lang.reflect.InvocationTargetException;
/*   17:     */ import java.net.URI;
/*   18:     */ import java.net.URISyntaxException;
/*   19:     */ import java.net.URL;
/*   20:     */ import java.net.URLConnection;
/*   21:     */ import java.util.ArrayList;
/*   22:     */ import java.util.HashMap;
/*   23:     */ import java.util.List;
/*   24:     */ import java.util.Map.Entry;
/*   25:     */ import java.util.regex.Matcher;
/*   26:     */ import java.util.regex.Pattern;
/*   27:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   28:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*   29:     */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*   30:     */ import net.sourceforge.htmlunit.corejs.javascript.ErrorReporter;
/*   31:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   32:     */ import net.sourceforge.htmlunit.corejs.javascript.ImporterTopLevel;
/*   33:     */ import net.sourceforge.htmlunit.corejs.javascript.NativeArray;
/*   34:     */ import net.sourceforge.htmlunit.corejs.javascript.RhinoException;
/*   35:     */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*   36:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   37:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   38:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   39:     */ import net.sourceforge.htmlunit.corejs.javascript.Synchronizer;
/*   40:     */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*   41:     */ import net.sourceforge.htmlunit.corejs.javascript.Wrapper;
/*   42:     */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.Require;
/*   43:     */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.RequireBuilder;
/*   44:     */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.SoftCachingModuleScriptProvider;
/*   45:     */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.UrlModuleSourceProvider;
/*   46:     */ import net.sourceforge.htmlunit.corejs.javascript.serialize.ScriptableInputStream;
/*   47:     */ import net.sourceforge.htmlunit.corejs.javascript.serialize.ScriptableOutputStream;
/*   48:     */ import net.sourceforge.htmlunit.corejs.javascript.tools.ToolErrorReporter;
/*   49:     */ 
/*   50:     */ public class Global
/*   51:     */   extends ImporterTopLevel
/*   52:     */ {
/*   53:     */   static final long serialVersionUID = 4029130780977538005L;
/*   54:     */   NativeArray history;
/*   55:     */   boolean attemptedJLineLoad;
/*   56:     */   private InputStream inStream;
/*   57:     */   private PrintStream outStream;
/*   58:     */   private PrintStream errStream;
/*   59:  78 */   private boolean sealedStdLib = false;
/*   60:     */   boolean initialized;
/*   61:     */   private QuitAction quitAction;
/*   62:  81 */   private String[] prompts = { "js> ", "  > " };
/*   63:     */   private HashMap<String, String> doctestCanonicalizations;
/*   64:     */   
/*   65:     */   public Global() {}
/*   66:     */   
/*   67:     */   public Global(Context cx)
/*   68:     */   {
/*   69:  90 */     init(cx);
/*   70:     */   }
/*   71:     */   
/*   72:     */   public boolean isInitialized()
/*   73:     */   {
/*   74:  94 */     return this.initialized;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public void initQuitAction(QuitAction quitAction)
/*   78:     */   {
/*   79: 102 */     if (quitAction == null) {
/*   80: 103 */       throw new IllegalArgumentException("quitAction is null");
/*   81:     */     }
/*   82: 104 */     if (this.quitAction != null) {
/*   83: 105 */       throw new IllegalArgumentException("The method is once-call.");
/*   84:     */     }
/*   85: 107 */     this.quitAction = quitAction;
/*   86:     */   }
/*   87:     */   
/*   88:     */   public void init(ContextFactory factory)
/*   89:     */   {
/*   90: 112 */     factory.call(new ContextAction()
/*   91:     */     {
/*   92:     */       public Object run(Context cx)
/*   93:     */       {
/*   94: 115 */         Global.this.init(cx);
/*   95: 116 */         return null;
/*   96:     */       }
/*   97:     */     });
/*   98:     */   }
/*   99:     */   
/*  100:     */   public void init(Context cx)
/*  101:     */   {
/*  102: 125 */     initStandardObjects(cx, this.sealedStdLib);
/*  103: 126 */     String[] names = { "defineClass", "deserialize", "doctest", "gc", "help", "load", "loadClass", "print", "quit", "readFile", "readUrl", "runCommand", "seal", "serialize", "spawn", "sync", "toint32", "version" };
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
/*  123: 146 */     defineFunctionProperties(names, Global.class, 2);
/*  124:     */     
/*  125:     */ 
/*  126:     */ 
/*  127:     */ 
/*  128: 151 */     Environment.defineClass(this);
/*  129: 152 */     Environment environment = new Environment(this);
/*  130: 153 */     defineProperty("environment", environment, 2);
/*  131:     */     
/*  132:     */ 
/*  133: 156 */     this.history = ((NativeArray)cx.newArray(this, 0));
/*  134: 157 */     defineProperty("history", this.history, 2);
/*  135:     */     
/*  136: 159 */     this.initialized = true;
/*  137:     */   }
/*  138:     */   
/*  139:     */   public Require installRequire(Context cx, List<String> modulePath, boolean sandboxed)
/*  140:     */   {
/*  141: 164 */     RequireBuilder rb = new RequireBuilder();
/*  142: 165 */     rb.setSandboxed(sandboxed);
/*  143: 166 */     List<URI> uris = new ArrayList();
/*  144: 167 */     if (modulePath != null) {
/*  145: 168 */       for (String path : modulePath) {
/*  146:     */         try
/*  147:     */         {
/*  148: 170 */           URI uri = new URI(path);
/*  149: 171 */           if (!uri.isAbsolute()) {
/*  150: 173 */             uri = new File(path).toURI().resolve("");
/*  151:     */           }
/*  152: 175 */           if (!uri.toString().endsWith("/")) {
/*  153: 178 */             uri = new URI(uri + "/");
/*  154:     */           }
/*  155: 180 */           uris.add(uri);
/*  156:     */         }
/*  157:     */         catch (URISyntaxException usx)
/*  158:     */         {
/*  159: 182 */           throw new RuntimeException(usx);
/*  160:     */         }
/*  161:     */       }
/*  162:     */     }
/*  163: 186 */     rb.setModuleScriptProvider(new SoftCachingModuleScriptProvider(new UrlModuleSourceProvider(uris, null)));
/*  164:     */     
/*  165:     */ 
/*  166: 189 */     Require require = rb.createRequire(cx, this);
/*  167: 190 */     require.install(this);
/*  168: 191 */     return require;
/*  169:     */   }
/*  170:     */   
/*  171:     */   public static void help(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  172:     */   {
/*  173: 202 */     PrintStream out = getInstance(funObj).getOut();
/*  174: 203 */     out.println(ToolErrorReporter.getMessage("msg.help"));
/*  175:     */   }
/*  176:     */   
/*  177:     */   public static void gc(Context cx, Scriptable thisObj, Object[] args, Function funObj) {}
/*  178:     */   
/*  179:     */   public static Object print(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  180:     */   {
/*  181: 225 */     PrintStream out = getInstance(funObj).getOut();
/*  182: 226 */     for (int i = 0; i < args.length; i++)
/*  183:     */     {
/*  184: 227 */       if (i > 0) {
/*  185: 228 */         out.print(" ");
/*  186:     */       }
/*  187: 231 */       String s = Context.toString(args[i]);
/*  188:     */       
/*  189: 233 */       out.print(s);
/*  190:     */     }
/*  191: 235 */     out.println();
/*  192: 236 */     return Context.getUndefinedValue();
/*  193:     */   }
/*  194:     */   
/*  195:     */   public static void quit(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  196:     */   {
/*  197: 248 */     Global global = getInstance(funObj);
/*  198: 249 */     if (global.quitAction != null)
/*  199:     */     {
/*  200: 250 */       int exitCode = args.length == 0 ? 0 : ScriptRuntime.toInt32(args[0]);
/*  201:     */       
/*  202: 252 */       global.quitAction.quit(cx, exitCode);
/*  203:     */     }
/*  204:     */   }
/*  205:     */   
/*  206:     */   public static double version(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  207:     */   {
/*  208: 264 */     double result = cx.getLanguageVersion();
/*  209: 265 */     if (args.length > 0)
/*  210:     */     {
/*  211: 266 */       double d = Context.toNumber(args[0]);
/*  212: 267 */       cx.setLanguageVersion((int)d);
/*  213:     */     }
/*  214: 269 */     return result;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public static void load(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  218:     */   {
/*  219: 281 */     for (int i = 0; i < args.length; i++) {
/*  220: 282 */       Main.processFile(cx, thisObj, Context.toString(args[i]));
/*  221:     */     }
/*  222:     */   }
/*  223:     */   
/*  224:     */   public static void defineClass(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  225:     */     throws IllegalAccessException, InstantiationException, InvocationTargetException
/*  226:     */   {
/*  227: 305 */     Class<?> clazz = getClass(args);
/*  228: 306 */     if (!Scriptable.class.isAssignableFrom(clazz)) {
/*  229: 307 */       throw reportRuntimeError("msg.must.implement.Scriptable");
/*  230:     */     }
/*  231: 309 */     ScriptableObject.defineClass(thisObj, clazz);
/*  232:     */   }
/*  233:     */   
/*  234:     */   public static void loadClass(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  235:     */     throws IllegalAccessException, InstantiationException
/*  236:     */   {
/*  237: 330 */     Class<?> clazz = getClass(args);
/*  238: 331 */     if (!Script.class.isAssignableFrom(clazz)) {
/*  239: 332 */       throw reportRuntimeError("msg.must.implement.Script");
/*  240:     */     }
/*  241: 334 */     Script script = (Script)clazz.newInstance();
/*  242: 335 */     script.exec(cx, thisObj);
/*  243:     */   }
/*  244:     */   
/*  245:     */   private static Class<?> getClass(Object[] args)
/*  246:     */   {
/*  247: 339 */     if (args.length == 0) {
/*  248: 340 */       throw reportRuntimeError("msg.expected.string.arg");
/*  249:     */     }
/*  250: 342 */     Object arg0 = args[0];
/*  251: 343 */     if ((arg0 instanceof Wrapper))
/*  252:     */     {
/*  253: 344 */       Object wrapped = ((Wrapper)arg0).unwrap();
/*  254: 345 */       if ((wrapped instanceof Class)) {
/*  255: 346 */         return (Class)wrapped;
/*  256:     */       }
/*  257:     */     }
/*  258: 348 */     String className = Context.toString(args[0]);
/*  259:     */     try
/*  260:     */     {
/*  261: 350 */       return Class.forName(className);
/*  262:     */     }
/*  263:     */     catch (ClassNotFoundException cnfe)
/*  264:     */     {
/*  265: 353 */       throw reportRuntimeError("msg.class.not.found", className);
/*  266:     */     }
/*  267:     */   }
/*  268:     */   
/*  269:     */   public static void serialize(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  270:     */     throws IOException
/*  271:     */   {
/*  272: 361 */     if (args.length < 2) {
/*  273: 362 */       throw Context.reportRuntimeError("Expected an object to serialize and a filename to write the serialization to");
/*  274:     */     }
/*  275: 366 */     Object obj = args[0];
/*  276: 367 */     String filename = Context.toString(args[1]);
/*  277: 368 */     FileOutputStream fos = new FileOutputStream(filename);
/*  278: 369 */     Scriptable scope = ScriptableObject.getTopLevelScope(thisObj);
/*  279: 370 */     ScriptableOutputStream out = new ScriptableOutputStream(fos, scope);
/*  280: 371 */     out.writeObject(obj);
/*  281: 372 */     out.close();
/*  282:     */   }
/*  283:     */   
/*  284:     */   public static Object deserialize(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  285:     */     throws IOException, ClassNotFoundException
/*  286:     */   {
/*  287: 379 */     if (args.length < 1) {
/*  288: 380 */       throw Context.reportRuntimeError("Expected a filename to read the serialization from");
/*  289:     */     }
/*  290: 383 */     String filename = Context.toString(args[0]);
/*  291: 384 */     FileInputStream fis = new FileInputStream(filename);
/*  292: 385 */     Scriptable scope = ScriptableObject.getTopLevelScope(thisObj);
/*  293: 386 */     ObjectInputStream in = new ScriptableInputStream(fis, scope);
/*  294: 387 */     Object deserialized = in.readObject();
/*  295: 388 */     in.close();
/*  296: 389 */     return Context.toObject(deserialized, scope);
/*  297:     */   }
/*  298:     */   
/*  299:     */   public String[] getPrompts(Context cx)
/*  300:     */   {
/*  301: 393 */     if (ScriptableObject.hasProperty(this, "prompts"))
/*  302:     */     {
/*  303: 394 */       Object promptsJS = ScriptableObject.getProperty(this, "prompts");
/*  304: 396 */       if ((promptsJS instanceof Scriptable))
/*  305:     */       {
/*  306: 397 */         Scriptable s = (Scriptable)promptsJS;
/*  307: 398 */         if ((ScriptableObject.hasProperty(s, 0)) && (ScriptableObject.hasProperty(s, 1)))
/*  308:     */         {
/*  309: 401 */           Object elem0 = ScriptableObject.getProperty(s, 0);
/*  310: 402 */           if ((elem0 instanceof Function)) {
/*  311: 403 */             elem0 = ((Function)elem0).call(cx, this, s, new Object[0]);
/*  312:     */           }
/*  313: 406 */           this.prompts[0] = Context.toString(elem0);
/*  314: 407 */           Object elem1 = ScriptableObject.getProperty(s, 1);
/*  315: 408 */           if ((elem1 instanceof Function)) {
/*  316: 409 */             elem1 = ((Function)elem1).call(cx, this, s, new Object[0]);
/*  317:     */           }
/*  318: 412 */           this.prompts[1] = Context.toString(elem1);
/*  319:     */         }
/*  320:     */       }
/*  321:     */     }
/*  322: 416 */     return this.prompts;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public static Object doctest(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  326:     */   {
/*  327: 426 */     if (args.length == 0) {
/*  328: 427 */       return Boolean.FALSE;
/*  329:     */     }
/*  330: 429 */     String session = Context.toString(args[0]);
/*  331: 430 */     Global global = getInstance(funObj);
/*  332: 431 */     return new Integer(global.runDoctest(cx, global, session, null, 0));
/*  333:     */   }
/*  334:     */   
/*  335:     */   public int runDoctest(Context cx, Scriptable scope, String session, String sourceName, int lineNumber)
/*  336:     */   {
/*  337: 437 */     this.doctestCanonicalizations = new HashMap();
/*  338: 438 */     String[] lines = session.split("[\n\r]+");
/*  339: 439 */     String prompt0 = this.prompts[0].trim();
/*  340: 440 */     String prompt1 = this.prompts[1].trim();
/*  341: 441 */     int testCount = 0;
/*  342: 442 */     int i = 0;
/*  343: 443 */     while ((i < lines.length) && (!lines[i].trim().startsWith(prompt0))) {
/*  344: 444 */       i++;
/*  345:     */     }
/*  346: 446 */     while (i < lines.length)
/*  347:     */     {
/*  348: 447 */       String inputString = lines[i].trim().substring(prompt0.length());
/*  349: 448 */       inputString = inputString + "\n";
/*  350: 449 */       i++;
/*  351: 450 */       while ((i < lines.length) && (lines[i].trim().startsWith(prompt1)))
/*  352:     */       {
/*  353: 451 */         inputString = inputString + lines[i].trim().substring(prompt1.length());
/*  354: 452 */         inputString = inputString + "\n";
/*  355: 453 */         i++;
/*  356:     */       }
/*  357: 455 */       String expectedString = "";
/*  358: 456 */       while ((i < lines.length) && (!lines[i].trim().startsWith(prompt0)))
/*  359:     */       {
/*  360: 459 */         expectedString = expectedString + lines[i] + "\n";
/*  361: 460 */         i++;
/*  362:     */       }
/*  363: 462 */       PrintStream savedOut = getOut();
/*  364: 463 */       PrintStream savedErr = getErr();
/*  365: 464 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/*  366: 465 */       ByteArrayOutputStream err = new ByteArrayOutputStream();
/*  367: 466 */       setOut(new PrintStream(out));
/*  368: 467 */       setErr(new PrintStream(err));
/*  369: 468 */       String resultString = "";
/*  370: 469 */       ErrorReporter savedErrorReporter = cx.getErrorReporter();
/*  371: 470 */       cx.setErrorReporter(new ToolErrorReporter(false, getErr()));
/*  372:     */       try
/*  373:     */       {
/*  374: 472 */         testCount++;
/*  375: 473 */         Object result = cx.evaluateString(scope, inputString, "doctest input", 1, null);
/*  376: 475 */         if ((result != Context.getUndefinedValue()) && ((!(result instanceof Function)) || (!inputString.trim().startsWith("function")))) {
/*  377: 479 */           resultString = Context.toString(result);
/*  378:     */         }
/*  379:     */       }
/*  380:     */       catch (RhinoException e)
/*  381:     */       {
/*  382: 482 */         ToolErrorReporter.reportException(cx.getErrorReporter(), e);
/*  383:     */       }
/*  384:     */       finally
/*  385:     */       {
/*  386: 484 */         setOut(savedOut);
/*  387: 485 */         setErr(savedErr);
/*  388: 486 */         cx.setErrorReporter(savedErrorReporter);
/*  389: 487 */         resultString = resultString + err.toString() + out.toString();
/*  390:     */       }
/*  391: 489 */       if (!doctestOutputMatches(expectedString, resultString))
/*  392:     */       {
/*  393: 490 */         String message = "doctest failure running:\n" + inputString + "expected: " + expectedString + "actual: " + resultString + "\n";
/*  394: 494 */         if (sourceName != null) {
/*  395: 495 */           throw Context.reportRuntimeError(message, sourceName, lineNumber + i - 1, null, 0);
/*  396:     */         }
/*  397: 498 */         throw Context.reportRuntimeError(message);
/*  398:     */       }
/*  399:     */     }
/*  400: 501 */     return testCount;
/*  401:     */   }
/*  402:     */   
/*  403:     */   private boolean doctestOutputMatches(String expected, String actual)
/*  404:     */   {
/*  405: 516 */     expected = expected.trim();
/*  406: 517 */     actual = actual.trim().replace("\r\n", "\n");
/*  407: 518 */     if (expected.equals(actual)) {
/*  408: 519 */       return true;
/*  409:     */     }
/*  410: 520 */     for (Map.Entry<String, String> entry : this.doctestCanonicalizations.entrySet()) {
/*  411: 521 */       expected = expected.replace((CharSequence)entry.getKey(), (CharSequence)entry.getValue());
/*  412:     */     }
/*  413: 523 */     if (expected.equals(actual)) {
/*  414: 524 */       return true;
/*  415:     */     }
/*  416: 531 */     Pattern p = Pattern.compile("@[0-9a-fA-F]+");
/*  417: 532 */     Matcher expectedMatcher = p.matcher(expected);
/*  418: 533 */     Matcher actualMatcher = p.matcher(actual);
/*  419:     */     for (;;)
/*  420:     */     {
/*  421: 535 */       if (!expectedMatcher.find()) {
/*  422: 536 */         return false;
/*  423:     */       }
/*  424: 537 */       if (!actualMatcher.find()) {
/*  425: 538 */         return false;
/*  426:     */       }
/*  427: 539 */       if (actualMatcher.start() != expectedMatcher.start()) {
/*  428: 540 */         return false;
/*  429:     */       }
/*  430: 541 */       int start = expectedMatcher.start();
/*  431: 542 */       if (!expected.substring(0, start).equals(actual.substring(0, start))) {
/*  432: 543 */         return false;
/*  433:     */       }
/*  434: 544 */       String expectedGroup = expectedMatcher.group();
/*  435: 545 */       String actualGroup = actualMatcher.group();
/*  436: 546 */       String mapping = (String)this.doctestCanonicalizations.get(expectedGroup);
/*  437: 547 */       if (mapping == null)
/*  438:     */       {
/*  439: 548 */         this.doctestCanonicalizations.put(expectedGroup, actualGroup);
/*  440: 549 */         expected = expected.replace(expectedGroup, actualGroup);
/*  441:     */       }
/*  442: 550 */       else if (!actualGroup.equals(mapping))
/*  443:     */       {
/*  444: 551 */         return false;
/*  445:     */       }
/*  446: 553 */       if (expected.equals(actual)) {
/*  447: 554 */         return true;
/*  448:     */       }
/*  449:     */     }
/*  450:     */   }
/*  451:     */   
/*  452:     */   public static Object spawn(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  453:     */   {
/*  454: 573 */     Scriptable scope = funObj.getParentScope();
/*  455:     */     Runner runner;
/*  456: 575 */     if ((args.length != 0) && ((args[0] instanceof Function)))
/*  457:     */     {
/*  458: 576 */       Object[] newArgs = null;
/*  459: 577 */       if ((args.length > 1) && ((args[1] instanceof Scriptable))) {
/*  460: 578 */         newArgs = cx.getElements((Scriptable)args[1]);
/*  461:     */       }
/*  462: 580 */       if (newArgs == null) {
/*  463: 580 */         newArgs = ScriptRuntime.emptyArgs;
/*  464:     */       }
/*  465: 581 */       runner = new Runner(scope, (Function)args[0], newArgs);
/*  466:     */     }
/*  467:     */     else
/*  468:     */     {
/*  469:     */       Runner runner;
/*  470: 582 */       if ((args.length != 0) && ((args[0] instanceof Script))) {
/*  471: 583 */         runner = new Runner(scope, (Script)args[0]);
/*  472:     */       } else {
/*  473: 585 */         throw reportRuntimeError("msg.spawn.args");
/*  474:     */       }
/*  475:     */     }
/*  476:     */     Runner runner;
/*  477: 587 */     runner.factory = cx.getFactory();
/*  478: 588 */     Thread thread = new Thread(runner);
/*  479: 589 */     thread.start();
/*  480: 590 */     return thread;
/*  481:     */   }
/*  482:     */   
/*  483:     */   public static Object sync(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  484:     */   {
/*  485: 617 */     if ((args.length >= 1) && (args.length <= 2) && ((args[0] instanceof Function)))
/*  486:     */     {
/*  487: 618 */       Object syncObject = null;
/*  488: 619 */       if ((args.length == 2) && (args[1] != Undefined.instance)) {
/*  489: 620 */         syncObject = args[1];
/*  490:     */       }
/*  491: 622 */       return new Synchronizer((Function)args[0], syncObject);
/*  492:     */     }
/*  493: 625 */     throw reportRuntimeError("msg.sync.args");
/*  494:     */   }
/*  495:     */   
/*  496:     */   public static Object runCommand(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  497:     */     throws IOException
/*  498:     */   {
/*  499: 668 */     int L = args.length;
/*  500: 669 */     if ((L == 0) || ((L == 1) && ((args[0] instanceof Scriptable)))) {
/*  501: 670 */       throw reportRuntimeError("msg.runCommand.bad.args");
/*  502:     */     }
/*  503: 673 */     InputStream in = null;
/*  504: 674 */     OutputStream out = null;OutputStream err = null;
/*  505: 675 */     ByteArrayOutputStream outBytes = null;ByteArrayOutputStream errBytes = null;
/*  506: 676 */     Object outObj = null;Object errObj = null;
/*  507: 677 */     String[] environment = null;
/*  508: 678 */     Scriptable params = null;
/*  509: 679 */     Object[] addArgs = null;
/*  510: 680 */     if ((args[(L - 1)] instanceof Scriptable))
/*  511:     */     {
/*  512: 681 */       params = (Scriptable)args[(L - 1)];
/*  513: 682 */       L--;
/*  514: 683 */       Object envObj = ScriptableObject.getProperty(params, "env");
/*  515: 684 */       if (envObj != Scriptable.NOT_FOUND) {
/*  516: 685 */         if (envObj == null)
/*  517:     */         {
/*  518: 686 */           environment = new String[0];
/*  519:     */         }
/*  520:     */         else
/*  521:     */         {
/*  522: 688 */           if (!(envObj instanceof Scriptable)) {
/*  523: 689 */             throw reportRuntimeError("msg.runCommand.bad.env");
/*  524:     */           }
/*  525: 691 */           Scriptable envHash = (Scriptable)envObj;
/*  526: 692 */           Object[] ids = ScriptableObject.getPropertyIds(envHash);
/*  527: 693 */           environment = new String[ids.length];
/*  528: 694 */           for (int i = 0; i != ids.length; i++)
/*  529:     */           {
/*  530: 695 */             Object keyObj = ids[i];
/*  531:     */             Object val;
/*  532:     */             String key;
/*  533:     */             Object val;
/*  534: 697 */             if ((keyObj instanceof String))
/*  535:     */             {
/*  536: 698 */               String key = (String)keyObj;
/*  537: 699 */               val = ScriptableObject.getProperty(envHash, key);
/*  538:     */             }
/*  539:     */             else
/*  540:     */             {
/*  541: 701 */               int ikey = ((Number)keyObj).intValue();
/*  542: 702 */               key = Integer.toString(ikey);
/*  543: 703 */               val = ScriptableObject.getProperty(envHash, ikey);
/*  544:     */             }
/*  545: 705 */             if (val == ScriptableObject.NOT_FOUND) {
/*  546: 706 */               val = Undefined.instance;
/*  547:     */             }
/*  548: 708 */             environment[i] = (key + '=' + ScriptRuntime.toString(val));
/*  549:     */           }
/*  550:     */         }
/*  551:     */       }
/*  552: 712 */       Object inObj = ScriptableObject.getProperty(params, "input");
/*  553: 713 */       if (inObj != Scriptable.NOT_FOUND) {
/*  554: 714 */         in = toInputStream(inObj);
/*  555:     */       }
/*  556: 716 */       outObj = ScriptableObject.getProperty(params, "output");
/*  557: 717 */       if (outObj != Scriptable.NOT_FOUND)
/*  558:     */       {
/*  559: 718 */         out = toOutputStream(outObj);
/*  560: 719 */         if (out == null)
/*  561:     */         {
/*  562: 720 */           outBytes = new ByteArrayOutputStream();
/*  563: 721 */           out = outBytes;
/*  564:     */         }
/*  565:     */       }
/*  566: 724 */       errObj = ScriptableObject.getProperty(params, "err");
/*  567: 725 */       if (errObj != Scriptable.NOT_FOUND)
/*  568:     */       {
/*  569: 726 */         err = toOutputStream(errObj);
/*  570: 727 */         if (err == null)
/*  571:     */         {
/*  572: 728 */           errBytes = new ByteArrayOutputStream();
/*  573: 729 */           err = errBytes;
/*  574:     */         }
/*  575:     */       }
/*  576: 732 */       Object addArgsObj = ScriptableObject.getProperty(params, "args");
/*  577: 733 */       if (addArgsObj != Scriptable.NOT_FOUND)
/*  578:     */       {
/*  579: 734 */         Scriptable s = Context.toObject(addArgsObj, getTopLevelScope(thisObj));
/*  580:     */         
/*  581: 736 */         addArgs = cx.getElements(s);
/*  582:     */       }
/*  583:     */     }
/*  584: 739 */     Global global = getInstance(funObj);
/*  585: 740 */     if (out == null) {
/*  586: 741 */       out = global != null ? global.getOut() : System.out;
/*  587:     */     }
/*  588: 743 */     if (err == null) {
/*  589: 744 */       err = global != null ? global.getErr() : System.err;
/*  590:     */     }
/*  591: 751 */     String[] cmd = new String[addArgs == null ? L : L + addArgs.length];
/*  592: 752 */     for (int i = 0; i != L; i++) {
/*  593: 753 */       cmd[i] = ScriptRuntime.toString(args[i]);
/*  594:     */     }
/*  595: 755 */     if (addArgs != null) {
/*  596: 756 */       for (int i = 0; i != addArgs.length; i++) {
/*  597: 757 */         cmd[(L + i)] = ScriptRuntime.toString(addArgs[i]);
/*  598:     */       }
/*  599:     */     }
/*  600: 761 */     int exitCode = runProcess(cmd, environment, in, out, err);
/*  601: 762 */     if (outBytes != null)
/*  602:     */     {
/*  603: 763 */       String s = ScriptRuntime.toString(outObj) + outBytes.toString();
/*  604: 764 */       ScriptableObject.putProperty(params, "output", s);
/*  605:     */     }
/*  606: 766 */     if (errBytes != null)
/*  607:     */     {
/*  608: 767 */       String s = ScriptRuntime.toString(errObj) + errBytes.toString();
/*  609: 768 */       ScriptableObject.putProperty(params, "err", s);
/*  610:     */     }
/*  611: 771 */     return new Integer(exitCode);
/*  612:     */   }
/*  613:     */   
/*  614:     */   public static void seal(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  615:     */   {
/*  616: 780 */     for (int i = 0; i != args.length; i++)
/*  617:     */     {
/*  618: 781 */       Object arg = args[i];
/*  619: 782 */       if ((!(arg instanceof ScriptableObject)) || (arg == Undefined.instance))
/*  620:     */       {
/*  621: 784 */         if ((!(arg instanceof Scriptable)) || (arg == Undefined.instance)) {
/*  622: 786 */           throw reportRuntimeError("msg.shell.seal.not.object");
/*  623:     */         }
/*  624: 788 */         throw reportRuntimeError("msg.shell.seal.not.scriptable");
/*  625:     */       }
/*  626:     */     }
/*  627: 793 */     for (int i = 0; i != args.length; i++)
/*  628:     */     {
/*  629: 794 */       Object arg = args[i];
/*  630: 795 */       ((ScriptableObject)arg).sealObject();
/*  631:     */     }
/*  632:     */   }
/*  633:     */   
/*  634:     */   public static Object readFile(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  635:     */     throws IOException
/*  636:     */   {
/*  637: 816 */     if (args.length == 0) {
/*  638: 817 */       throw reportRuntimeError("msg.shell.readFile.bad.args");
/*  639:     */     }
/*  640: 819 */     String path = ScriptRuntime.toString(args[0]);
/*  641: 820 */     String charCoding = null;
/*  642: 821 */     if (args.length >= 2) {
/*  643: 822 */       charCoding = ScriptRuntime.toString(args[1]);
/*  644:     */     }
/*  645: 825 */     return readUrl(path, charCoding, true);
/*  646:     */   }
/*  647:     */   
/*  648:     */   public static Object readUrl(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  649:     */     throws IOException
/*  650:     */   {
/*  651: 846 */     if (args.length == 0) {
/*  652: 847 */       throw reportRuntimeError("msg.shell.readUrl.bad.args");
/*  653:     */     }
/*  654: 849 */     String url = ScriptRuntime.toString(args[0]);
/*  655: 850 */     String charCoding = null;
/*  656: 851 */     if (args.length >= 2) {
/*  657: 852 */       charCoding = ScriptRuntime.toString(args[1]);
/*  658:     */     }
/*  659: 855 */     return readUrl(url, charCoding, false);
/*  660:     */   }
/*  661:     */   
/*  662:     */   public static Object toint32(Context cx, Scriptable thisObj, Object[] args, Function funObj)
/*  663:     */   {
/*  664: 864 */     Object arg = args.length != 0 ? args[0] : Undefined.instance;
/*  665: 865 */     if ((arg instanceof Integer)) {
/*  666: 866 */       return arg;
/*  667:     */     }
/*  668: 867 */     return ScriptRuntime.wrapInt(ScriptRuntime.toInt32(arg));
/*  669:     */   }
/*  670:     */   
/*  671:     */   public InputStream getIn()
/*  672:     */   {
/*  673: 871 */     if ((this.inStream == null) && (!this.attemptedJLineLoad))
/*  674:     */     {
/*  675: 873 */       InputStream jlineStream = ShellLine.getStream(this);
/*  676: 874 */       if (jlineStream != null) {
/*  677: 875 */         this.inStream = jlineStream;
/*  678:     */       }
/*  679: 876 */       this.attemptedJLineLoad = true;
/*  680:     */     }
/*  681: 878 */     return this.inStream == null ? System.in : this.inStream;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public void setIn(InputStream in)
/*  685:     */   {
/*  686: 882 */     this.inStream = in;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public PrintStream getOut()
/*  690:     */   {
/*  691: 886 */     return this.outStream == null ? System.out : this.outStream;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public void setOut(PrintStream out)
/*  695:     */   {
/*  696: 890 */     this.outStream = out;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public PrintStream getErr()
/*  700:     */   {
/*  701: 894 */     return this.errStream == null ? System.err : this.errStream;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public void setErr(PrintStream err)
/*  705:     */   {
/*  706: 898 */     this.errStream = err;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void setSealedStdLib(boolean value)
/*  710:     */   {
/*  711: 903 */     this.sealedStdLib = value;
/*  712:     */   }
/*  713:     */   
/*  714:     */   private static Global getInstance(Function function)
/*  715:     */   {
/*  716: 908 */     Scriptable scope = function.getParentScope();
/*  717: 909 */     if (!(scope instanceof Global)) {
/*  718: 910 */       throw reportRuntimeError("msg.bad.shell.function.scope", String.valueOf(scope));
/*  719:     */     }
/*  720: 912 */     return (Global)scope;
/*  721:     */   }
/*  722:     */   
/*  723:     */   private static int runProcess(String[] cmd, String[] environment, InputStream in, OutputStream out, OutputStream err)
/*  724:     */     throws IOException
/*  725:     */   {
/*  726:     */     Process p;
/*  727:     */     Process p;
/*  728: 930 */     if (environment == null) {
/*  729: 931 */       p = Runtime.getRuntime().exec(cmd);
/*  730:     */     } else {
/*  731: 933 */       p = Runtime.getRuntime().exec(cmd, environment);
/*  732:     */     }
/*  733:     */     try
/*  734:     */     {
/*  735: 937 */       PipeThread inThread = null;
/*  736: 938 */       if (in != null)
/*  737:     */       {
/*  738: 939 */         inThread = new PipeThread(false, in, p.getOutputStream());
/*  739: 940 */         inThread.start();
/*  740:     */       }
/*  741:     */       else
/*  742:     */       {
/*  743: 942 */         p.getOutputStream().close();
/*  744:     */       }
/*  745: 945 */       PipeThread outThread = null;
/*  746: 946 */       if (out != null)
/*  747:     */       {
/*  748: 947 */         outThread = new PipeThread(true, p.getInputStream(), out);
/*  749: 948 */         outThread.start();
/*  750:     */       }
/*  751:     */       else
/*  752:     */       {
/*  753: 950 */         p.getInputStream().close();
/*  754:     */       }
/*  755: 953 */       PipeThread errThread = null;
/*  756: 954 */       if (err != null)
/*  757:     */       {
/*  758: 955 */         errThread = new PipeThread(true, p.getErrorStream(), err);
/*  759: 956 */         errThread.start();
/*  760:     */       }
/*  761:     */       else
/*  762:     */       {
/*  763: 958 */         p.getErrorStream().close();
/*  764:     */       }
/*  765:     */       for (;;)
/*  766:     */       {
/*  767:     */         try
/*  768:     */         {
/*  769: 964 */           p.waitFor();
/*  770: 965 */           if (outThread != null) {
/*  771: 966 */             outThread.join();
/*  772:     */           }
/*  773: 968 */           if (inThread != null) {
/*  774: 969 */             inThread.join();
/*  775:     */           }
/*  776: 971 */           if (errThread == null) {}
/*  777:     */         }
/*  778:     */         catch (InterruptedException ignore) {}
/*  779:     */       }
/*  780: 979 */       return p.exitValue();
/*  781:     */     }
/*  782:     */     finally
/*  783:     */     {
/*  784: 981 */       p.destroy();
/*  785:     */     }
/*  786:     */   }
/*  787:     */   
/*  788:     */   static void pipe(boolean fromProcess, InputStream from, OutputStream to)
/*  789:     */     throws IOException
/*  790:     */   {
/*  791:     */     try
/*  792:     */     {
/*  793: 989 */       int SIZE = 4096;
/*  794: 990 */       byte[] buffer = new byte[4096];
/*  795:     */       for (;;)
/*  796:     */       {
/*  797:     */         int n;
/*  798:     */         int n;
/*  799: 993 */         if (!fromProcess) {
/*  800: 994 */           n = from.read(buffer, 0, 4096);
/*  801:     */         } else {
/*  802:     */           try
/*  803:     */           {
/*  804: 997 */             n = from.read(buffer, 0, 4096);
/*  805:     */           }
/*  806:     */           catch (IOException ex)
/*  807:     */           {
/*  808:     */             break;
/*  809:     */           }
/*  810:     */         }
/*  811:1003 */         if (n < 0) {
/*  812:     */           break;
/*  813:     */         }
/*  814:1004 */         if (fromProcess)
/*  815:     */         {
/*  816:1005 */           to.write(buffer, 0, n);
/*  817:1006 */           to.flush();
/*  818:     */         }
/*  819:     */         else
/*  820:     */         {
/*  821:     */           try
/*  822:     */           {
/*  823:1009 */             to.write(buffer, 0, n);
/*  824:1010 */             to.flush();
/*  825:     */           }
/*  826:     */           catch (IOException ex)
/*  827:     */           {
/*  828:     */             break;
/*  829:     */           }
/*  830:     */         }
/*  831:     */       }
/*  832:     */       return;
/*  833:     */     }
/*  834:     */     finally
/*  835:     */     {
/*  836:     */       try
/*  837:     */       {
/*  838:1019 */         if (fromProcess) {
/*  839:1020 */           from.close();
/*  840:     */         } else {
/*  841:1022 */           to.close();
/*  842:     */         }
/*  843:     */       }
/*  844:     */       catch (IOException ex) {}
/*  845:     */     }
/*  846:     */   }
/*  847:     */   
/*  848:     */   private static InputStream toInputStream(Object value)
/*  849:     */     throws IOException
/*  850:     */   {
/*  851:1034 */     InputStream is = null;
/*  852:1035 */     String s = null;
/*  853:1036 */     if ((value instanceof Wrapper))
/*  854:     */     {
/*  855:1037 */       Object unwrapped = ((Wrapper)value).unwrap();
/*  856:1038 */       if ((unwrapped instanceof InputStream)) {
/*  857:1039 */         is = (InputStream)unwrapped;
/*  858:1040 */       } else if ((unwrapped instanceof byte[])) {
/*  859:1041 */         is = new ByteArrayInputStream((byte[])unwrapped);
/*  860:1042 */       } else if ((unwrapped instanceof Reader)) {
/*  861:1043 */         s = readReader((Reader)unwrapped);
/*  862:1044 */       } else if ((unwrapped instanceof char[])) {
/*  863:1045 */         s = new String((char[])unwrapped);
/*  864:     */       }
/*  865:     */     }
/*  866:1048 */     if (is == null)
/*  867:     */     {
/*  868:1049 */       if (s == null) {
/*  869:1049 */         s = ScriptRuntime.toString(value);
/*  870:     */       }
/*  871:1050 */       is = new ByteArrayInputStream(s.getBytes());
/*  872:     */     }
/*  873:1052 */     return is;
/*  874:     */   }
/*  875:     */   
/*  876:     */   private static OutputStream toOutputStream(Object value)
/*  877:     */   {
/*  878:1056 */     OutputStream os = null;
/*  879:1057 */     if ((value instanceof Wrapper))
/*  880:     */     {
/*  881:1058 */       Object unwrapped = ((Wrapper)value).unwrap();
/*  882:1059 */       if ((unwrapped instanceof OutputStream)) {
/*  883:1060 */         os = (OutputStream)unwrapped;
/*  884:     */       }
/*  885:     */     }
/*  886:1063 */     return os;
/*  887:     */   }
/*  888:     */   
/*  889:     */   private static String readUrl(String filePath, String charCoding, boolean urlIsFile)
/*  890:     */     throws IOException
/*  891:     */   {
/*  892:1071 */     InputStream is = null;
/*  893:     */     try
/*  894:     */     {
/*  895:     */       long length;
/*  896:     */       int chunkLength;
/*  897:1073 */       if (!urlIsFile)
/*  898:     */       {
/*  899:1074 */         URL urlObj = new URL(filePath);
/*  900:1075 */         URLConnection uc = urlObj.openConnection();
/*  901:1076 */         is = uc.getInputStream();
/*  902:1077 */         int chunkLength = uc.getContentLength();
/*  903:1078 */         if (chunkLength <= 0) {
/*  904:1079 */           chunkLength = 1024;
/*  905:     */         }
/*  906:1080 */         if (charCoding == null)
/*  907:     */         {
/*  908:1081 */           String type = uc.getContentType();
/*  909:1082 */           if (type != null) {
/*  910:1083 */             charCoding = getCharCodingFromType(type);
/*  911:     */           }
/*  912:     */         }
/*  913:     */       }
/*  914:     */       else
/*  915:     */       {
/*  916:1087 */         File f = new File(filePath);
/*  917:1088 */         if (!f.exists()) {
/*  918:1089 */           throw new FileNotFoundException("File not found: " + filePath);
/*  919:     */         }
/*  920:1090 */         if (!f.canRead()) {
/*  921:1091 */           throw new IOException("Cannot read file: " + filePath);
/*  922:     */         }
/*  923:1093 */         length = f.length();
/*  924:1094 */         chunkLength = (int)length;
/*  925:1095 */         if (chunkLength != length) {
/*  926:1096 */           throw new IOException("Too big file size: " + length);
/*  927:     */         }
/*  928:1098 */         if (chunkLength == 0) {
/*  929:1098 */           return "";
/*  930:     */         }
/*  931:1100 */         is = new FileInputStream(f);
/*  932:     */       }
/*  933:     */       Reader r;
/*  934:     */       Reader r;
/*  935:1104 */       if (charCoding == null) {
/*  936:1105 */         r = new InputStreamReader(is);
/*  937:     */       } else {
/*  938:1107 */         r = new InputStreamReader(is, charCoding);
/*  939:     */       }
/*  940:1109 */       return readReader(r, chunkLength);
/*  941:     */     }
/*  942:     */     finally
/*  943:     */     {
/*  944:1112 */       if (is != null) {
/*  945:1113 */         is.close();
/*  946:     */       }
/*  947:     */     }
/*  948:     */   }
/*  949:     */   
/*  950:     */   private static String getCharCodingFromType(String type)
/*  951:     */   {
/*  952:1119 */     int i = type.indexOf(';');
/*  953:1120 */     if (i >= 0)
/*  954:     */     {
/*  955:1121 */       int end = type.length();
/*  956:1122 */       i++;
/*  957:1123 */       while ((i != end) && (type.charAt(i) <= ' ')) {
/*  958:1124 */         i++;
/*  959:     */       }
/*  960:1126 */       String charset = "charset";
/*  961:1127 */       if (charset.regionMatches(true, 0, type, i, charset.length()))
/*  962:     */       {
/*  963:1129 */         i += charset.length();
/*  964:1130 */         while ((i != end) && (type.charAt(i) <= ' ')) {
/*  965:1131 */           i++;
/*  966:     */         }
/*  967:1133 */         if ((i != end) && (type.charAt(i) == '='))
/*  968:     */         {
/*  969:1134 */           i++;
/*  970:1135 */           while ((i != end) && (type.charAt(i) <= ' ')) {
/*  971:1136 */             i++;
/*  972:     */           }
/*  973:1138 */           if (i != end)
/*  974:     */           {
/*  975:1141 */             while (type.charAt(end - 1) <= ' ') {
/*  976:1142 */               end--;
/*  977:     */             }
/*  978:1144 */             return type.substring(i, end);
/*  979:     */           }
/*  980:     */         }
/*  981:     */       }
/*  982:     */     }
/*  983:1149 */     return null;
/*  984:     */   }
/*  985:     */   
/*  986:     */   private static String readReader(Reader reader)
/*  987:     */     throws IOException
/*  988:     */   {
/*  989:1155 */     return readReader(reader, 4096);
/*  990:     */   }
/*  991:     */   
/*  992:     */   private static String readReader(Reader reader, int initialBufferSize)
/*  993:     */     throws IOException
/*  994:     */   {
/*  995:1161 */     char[] buffer = new char[initialBufferSize];
/*  996:1162 */     int offset = 0;
/*  997:     */     for (;;)
/*  998:     */     {
/*  999:1164 */       int n = reader.read(buffer, offset, buffer.length - offset);
/* 1000:1165 */       if (n < 0) {
/* 1001:     */         break;
/* 1002:     */       }
/* 1003:1166 */       offset += n;
/* 1004:1167 */       if (offset == buffer.length)
/* 1005:     */       {
/* 1006:1168 */         char[] tmp = new char[buffer.length * 2];
/* 1007:1169 */         System.arraycopy(buffer, 0, tmp, 0, offset);
/* 1008:1170 */         buffer = tmp;
/* 1009:     */       }
/* 1010:     */     }
/* 1011:1173 */     return new String(buffer, 0, offset);
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   static RuntimeException reportRuntimeError(String msgId)
/* 1015:     */   {
/* 1016:1177 */     String message = ToolErrorReporter.getMessage(msgId);
/* 1017:1178 */     return Context.reportRuntimeError(message);
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   static RuntimeException reportRuntimeError(String msgId, String msgArg)
/* 1021:     */   {
/* 1022:1183 */     String message = ToolErrorReporter.getMessage(msgId, msgArg);
/* 1023:1184 */     return Context.reportRuntimeError(message);
/* 1024:     */   }
/* 1025:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.Global
 * JD-Core Version:    0.7.0.1
 */