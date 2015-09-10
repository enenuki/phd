/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.Page;
/*   6:    */ import com.gargoylesoftware.htmlunit.ScriptException;
/*   7:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  11:    */ import com.gargoylesoftware.htmlunit.gae.GAEUtils;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.HtmlDivision;
/*  14:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  15:    */ import com.gargoylesoftware.htmlunit.javascript.background.GAEJavaScriptExecutor;
/*  16:    */ import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
/*  17:    */ import com.gargoylesoftware.htmlunit.javascript.configuration.ClassConfiguration;
/*  18:    */ import com.gargoylesoftware.htmlunit.javascript.configuration.JavaScriptConfiguration;
/*  19:    */ import com.gargoylesoftware.htmlunit.javascript.host.Element;
/*  20:    */ import com.gargoylesoftware.htmlunit.javascript.host.StringCustom;
/*  21:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.io.ObjectInputStream;
/*  24:    */ import java.lang.reflect.Field;
/*  25:    */ import java.lang.reflect.Member;
/*  26:    */ import java.lang.reflect.Method;
/*  27:    */ import java.util.ArrayList;
/*  28:    */ import java.util.HashMap;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import java.util.Map.Entry;
/*  32:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  33:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*  34:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  35:    */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*  36:    */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  37:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  38:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  39:    */ import org.apache.commons.lang.StringUtils;
/*  40:    */ import org.apache.commons.logging.Log;
/*  41:    */ import org.apache.commons.logging.LogFactory;
/*  42:    */ 
/*  43:    */ public class JavaScriptEngine
/*  44:    */ {
/*  45: 81 */   private static final Log LOG = LogFactory.getLog(JavaScriptEngine.class);
/*  46:    */   private final WebClient webClient_;
/*  47:    */   private final HtmlUnitContextFactory contextFactory_;
/*  48:    */   private final JavaScriptConfiguration jsConfig_;
/*  49:    */   private transient ThreadLocal<Boolean> javaScriptRunning_;
/*  50:    */   private transient ThreadLocal<List<PostponedAction>> postponedActions_;
/*  51:    */   private transient ThreadLocal<Boolean> holdPostponedActions_;
/*  52:    */   private transient JavaScriptExecutor javaScriptExecutor_;
/*  53:    */   public static final String KEY_STARTING_SCOPE = "startingScope";
/*  54:    */   public static final String KEY_STARTING_PAGE = "startingPage";
/*  55:    */   
/*  56:    */   public JavaScriptEngine(WebClient webClient)
/*  57:    */   {
/*  58:114 */     this.webClient_ = webClient;
/*  59:115 */     this.contextFactory_ = new HtmlUnitContextFactory(webClient);
/*  60:116 */     initTransientFields();
/*  61:117 */     this.jsConfig_ = JavaScriptConfiguration.getInstance(webClient.getBrowserVersion());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final WebClient getWebClient()
/*  65:    */   {
/*  66:125 */     return this.webClient_;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public HtmlUnitContextFactory getContextFactory()
/*  70:    */   {
/*  71:133 */     return this.contextFactory_;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void initialize(final WebWindow webWindow)
/*  75:    */   {
/*  76:141 */     WebAssert.notNull("webWindow", webWindow);
/*  77:    */     
/*  78:143 */     ContextAction action = new ContextAction()
/*  79:    */     {
/*  80:    */       public Object run(Context cx)
/*  81:    */       {
/*  82:    */         try
/*  83:    */         {
/*  84:146 */           JavaScriptEngine.this.init(webWindow, cx);
/*  85:    */         }
/*  86:    */         catch (Exception e)
/*  87:    */         {
/*  88:149 */           JavaScriptEngine.LOG.error("Exception while initializing JavaScript for the page", e);
/*  89:150 */           throw new ScriptException(null, e);
/*  90:    */         }
/*  91:153 */         return null;
/*  92:    */       }
/*  93:156 */     };
/*  94:157 */     getContextFactory().call(action);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public JavaScriptExecutor getJavaScriptExecutor()
/*  98:    */   {
/*  99:165 */     return this.javaScriptExecutor_;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void init(WebWindow webWindow, Context context)
/* 103:    */     throws Exception
/* 104:    */   {
/* 105:175 */     WebClient webClient = webWindow.getWebClient();
/* 106:176 */     BrowserVersion browserVersion = webClient.getBrowserVersion();
/* 107:177 */     Map<Class<? extends SimpleScriptable>, Scriptable> prototypes = new HashMap();
/* 108:    */     
/* 109:179 */     Map<String, Scriptable> prototypesPerJSName = new HashMap();
/* 110:180 */     Window window = new Window();
/* 111:181 */     context.initStandardObjects(window);
/* 112:    */     
/* 113:    */ 
/* 114:184 */     deleteProperties(window, new String[] { "javax", "org", "com", "edu", "net", "JavaAdapter", "JavaImporter", "Continuation" });
/* 115:185 */     if (browserVersion.hasFeature(BrowserVersionFeatures.GENERATED_144)) {
/* 116:186 */       deleteProperties(window, new String[] { "Packages", "java", "getClass", "XML", "XMLList", "Namespace", "QName" });
/* 117:    */     }
/* 118:190 */     Scriptable fallbackCaller = new FallbackCaller(null);
/* 119:191 */     ScriptableObject.getObjectPrototype(window).setPrototype(fallbackCaller);
/* 120:193 */     for (ClassConfiguration config : this.jsConfig_.getAll())
/* 121:    */     {
/* 122:194 */       boolean isWindow = Window.class.getName().equals(config.getHostClass().getName());
/* 123:195 */       if (isWindow)
/* 124:    */       {
/* 125:196 */         configureConstantsPropertiesAndFunctions(config, window);
/* 126:    */       }
/* 127:    */       else
/* 128:    */       {
/* 129:199 */         ScriptableObject prototype = configureClass(config, window);
/* 130:200 */         if (config.isJsObject())
/* 131:    */         {
/* 132:202 */           if (browserVersion.hasFeature(BrowserVersionFeatures.JS_HAS_OBJECT_WITH_PROTOTYPE_PROPERTY_IN_WINDOW_SCOPE))
/* 133:    */           {
/* 134:203 */             SimpleScriptable obj = (SimpleScriptable)config.getHostClass().newInstance();
/* 135:204 */             prototype.defineProperty("__proto__", prototype, 2);
/* 136:205 */             obj.defineProperty("prototype", prototype, 2);
/* 137:206 */             obj.setParentScope(window);
/* 138:207 */             ScriptableObject.defineProperty(window, obj.getClassName(), obj, 2);
/* 139:    */             
/* 140:209 */             configureConstants(config, obj);
/* 141:211 */             if ((obj.getClass() == Element.class) && ((webWindow.getEnclosedPage() instanceof HtmlPage)))
/* 142:    */             {
/* 143:212 */               DomNode domNode = new HtmlDivision(null, "", (SgmlPage)webWindow.getEnclosedPage(), null);
/* 144:    */               
/* 145:214 */               obj.setDomNode(domNode);
/* 146:    */             }
/* 147:    */           }
/* 148:217 */           prototypes.put(config.getHostClass(), prototype);
/* 149:    */         }
/* 150:219 */         prototypesPerJSName.put(config.getHostClass().getSimpleName(), prototype);
/* 151:    */       }
/* 152:    */     }
/* 153:224 */     Scriptable objectPrototype = ScriptableObject.getObjectPrototype(window);
/* 154:225 */     for (Map.Entry<String, Scriptable> entry : prototypesPerJSName.entrySet())
/* 155:    */     {
/* 156:226 */       String name = (String)entry.getKey();
/* 157:227 */       ClassConfiguration config = this.jsConfig_.getClassConfiguration(name);
/* 158:228 */       Scriptable prototype = (Scriptable)entry.getValue();
/* 159:229 */       if (prototype.getPrototype() != null) {
/* 160:230 */         prototype = prototype.getPrototype();
/* 161:    */       }
/* 162:232 */       if (!StringUtils.isEmpty(config.getExtendedClassName()))
/* 163:    */       {
/* 164:233 */         Scriptable parentPrototype = (Scriptable)prototypesPerJSName.get(config.getExtendedClassName());
/* 165:234 */         prototype.setPrototype(parentPrototype);
/* 166:    */       }
/* 167:    */       else
/* 168:    */       {
/* 169:237 */         prototype.setPrototype(objectPrototype);
/* 170:    */       }
/* 171:    */     }
/* 172:242 */     Member evalFn = Window.class.getMethod("custom_eval", new Class[] { String.class });
/* 173:243 */     FunctionObject jsCustomEval = new FunctionObject("eval", evalFn, window);
/* 174:244 */     window.associateValue("custom_eval", jsCustomEval);
/* 175:246 */     for (ClassConfiguration config : this.jsConfig_.getAll())
/* 176:    */     {
/* 177:247 */       Method jsConstructor = config.getJsConstructor();
/* 178:248 */       if (jsConstructor != null)
/* 179:    */       {
/* 180:249 */         String jsClassName = config.getHostClass().getSimpleName();
/* 181:250 */         Scriptable prototype = (Scriptable)prototypesPerJSName.get(jsClassName);
/* 182:251 */         if (prototype != null)
/* 183:    */         {
/* 184:252 */           FunctionObject jsCtor = new FunctionObject(jsClassName, jsConstructor, window);
/* 185:253 */           jsCtor.addAsConstructor(window, prototype);
/* 186:    */         }
/* 187:    */       }
/* 188:    */     }
/* 189:259 */     removePrototypeProperties(window, "String", new String[] { "equals", "equalsIgnoreCase" });
/* 190:260 */     if (browserVersion.hasFeature(BrowserVersionFeatures.STRING_TRIM))
/* 191:    */     {
/* 192:261 */       ScriptableObject stringPrototype = (ScriptableObject)ScriptableObject.getClassPrototype(window, "String");
/* 193:    */       
/* 194:263 */       stringPrototype.defineFunctionProperties(new String[] { "trimLeft", "trimRight" }, StringCustom.class, 0);
/* 195:    */     }
/* 196:    */     else
/* 197:    */     {
/* 198:267 */       removePrototypeProperties(window, "String", new String[] { "trim" });
/* 199:    */     }
/* 200:269 */     removePrototypeProperties(window, "Function", new String[] { "bind" });
/* 201:270 */     removePrototypeProperties(window, "Date", new String[] { "toISOString", "toJSON" });
/* 202:273 */     if (browserVersion.hasFeature(BrowserVersionFeatures.GENERATED_146))
/* 203:    */     {
/* 204:274 */       deleteProperties(window, new String[] { "isXMLName", "uneval" });
/* 205:275 */       removePrototypeProperties(window, "Object", new String[] { "__defineGetter__", "__defineSetter__", "__lookupGetter__", "__lookupSetter__", "toSource" });
/* 206:    */       
/* 207:277 */       removePrototypeProperties(window, "Array", new String[] { "every", "filter", "forEach", "indexOf", "lastIndexOf", "map", "reduce", "reduceRight", "some", "toSource" });
/* 208:    */       
/* 209:279 */       removePrototypeProperties(window, "Date", new String[] { "toSource" });
/* 210:280 */       removePrototypeProperties(window, "Function", new String[] { "toSource" });
/* 211:281 */       removePrototypeProperties(window, "Number", new String[] { "toSource" });
/* 212:282 */       removePrototypeProperties(window, "String", new String[] { "toSource" });
/* 213:    */     }
/* 214:285 */     NativeFunctionToStringFunction.installFix(window, webClient.getBrowserVersion());
/* 215:    */     
/* 216:287 */     window.setPrototypes(prototypes);
/* 217:288 */     window.initialize(webWindow);
/* 218:    */   }
/* 219:    */   
/* 220:    */   private void deleteProperties(Window window, String... propertiesToDelete)
/* 221:    */   {
/* 222:297 */     for (String property : propertiesToDelete) {
/* 223:298 */       window.delete(property);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   private void removePrototypeProperties(Window window, String className, String... properties)
/* 228:    */   {
/* 229:309 */     ScriptableObject prototype = (ScriptableObject)ScriptableObject.getClassPrototype(window, className);
/* 230:310 */     for (String property : properties) {
/* 231:311 */       prototype.delete(property);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   private ScriptableObject configureClass(ClassConfiguration config, Scriptable window)
/* 236:    */     throws InstantiationException, IllegalAccessException
/* 237:    */   {
/* 238:326 */     Class<?> jsHostClass = config.getHostClass();
/* 239:327 */     ScriptableObject prototype = (ScriptableObject)jsHostClass.newInstance();
/* 240:328 */     prototype.setParentScope(window);
/* 241:    */     
/* 242:330 */     configureConstantsPropertiesAndFunctions(config, prototype);
/* 243:    */     
/* 244:332 */     return prototype;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private void configureConstantsPropertiesAndFunctions(ClassConfiguration config, ScriptableObject scriptable)
/* 248:    */   {
/* 249:344 */     configureConstants(config, scriptable);
/* 250:347 */     for (String propertyName : config.propertyKeys())
/* 251:    */     {
/* 252:348 */       Method readMethod = config.getPropertyReadMethod(propertyName);
/* 253:349 */       Method writeMethod = config.getPropertyWriteMethod(propertyName);
/* 254:350 */       scriptable.defineProperty(propertyName, null, readMethod, writeMethod, 0);
/* 255:    */     }
/* 256:353 */     int attributes = 0;
/* 257:354 */     if (this.webClient_.getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_147)) {
/* 258:355 */       attributes = 2;
/* 259:    */     }
/* 260:358 */     for (String functionName : config.functionKeys())
/* 261:    */     {
/* 262:359 */       Method method = config.getFunctionMethod(functionName);
/* 263:360 */       FunctionObject functionObject = new FunctionObject(functionName, method, scriptable);
/* 264:361 */       scriptable.defineProperty(functionName, functionObject, attributes);
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   private void configureConstants(ClassConfiguration config, ScriptableObject scriptable)
/* 269:    */   {
/* 270:367 */     for (String constant : config.constants())
/* 271:    */     {
/* 272:368 */       Class<?> linkedClass = config.getHostClass();
/* 273:    */       try
/* 274:    */       {
/* 275:370 */         Object value = linkedClass.getField(constant).get(null);
/* 276:371 */         scriptable.defineProperty(constant, value, 0);
/* 277:    */       }
/* 278:    */       catch (Exception e)
/* 279:    */       {
/* 280:374 */         throw Context.reportRuntimeError("Cannot get field '" + constant + "' for type: " + config.getHostClass().getName());
/* 281:    */       }
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void registerWindowAndMaybeStartEventLoop(WebWindow webWindow)
/* 286:    */   {
/* 287:385 */     if (this.javaScriptExecutor_ == null) {
/* 288:386 */       this.javaScriptExecutor_ = createJavaScriptExecutor();
/* 289:    */     }
/* 290:388 */     this.javaScriptExecutor_.addWindow(webWindow);
/* 291:    */   }
/* 292:    */   
/* 293:    */   protected JavaScriptExecutor createJavaScriptExecutor()
/* 294:    */   {
/* 295:396 */     if (GAEUtils.isGaeMode()) {
/* 296:397 */       return new GAEJavaScriptExecutor(this.webClient_);
/* 297:    */     }
/* 298:399 */     return new JavaScriptExecutor(this.webClient_);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public int pumpEventLoop(long timeoutMillis)
/* 302:    */   {
/* 303:409 */     if (this.javaScriptExecutor_ == null) {
/* 304:410 */       return 0;
/* 305:    */     }
/* 306:412 */     return this.javaScriptExecutor_.pumpEventLoop(timeoutMillis);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void shutdownJavaScriptExecutor()
/* 310:    */   {
/* 311:419 */     if (this.javaScriptExecutor_ != null)
/* 312:    */     {
/* 313:420 */       this.javaScriptExecutor_.shutdown();
/* 314:421 */       this.javaScriptExecutor_ = null;
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Script compile(HtmlPage htmlPage, String sourceCode, final String sourceName, final int startLine)
/* 319:    */   {
/* 320:437 */     WebAssert.notNull("sourceCode", sourceCode);
/* 321:    */     
/* 322:439 */     Scriptable scope = getScope(htmlPage, null);
/* 323:440 */     final String source = sourceCode;
/* 324:441 */     ContextAction action = new HtmlUnitContextAction(scope, htmlPage, source)
/* 325:    */     {
/* 326:    */       public Object doRun(Context cx)
/* 327:    */       {
/* 328:444 */         return cx.compileString(source, sourceName, startLine, null);
/* 329:    */       }
/* 330:    */       
/* 331:    */       protected String getSourceCode(Context cx)
/* 332:    */       {
/* 333:449 */         return source;
/* 334:    */       }
/* 335:452 */     };
/* 336:453 */     return (Script)getContextFactory().call(action);
/* 337:    */   }
/* 338:    */   
/* 339:    */   public Object execute(HtmlPage htmlPage, String sourceCode, String sourceName, int startLine)
/* 340:    */   {
/* 341:470 */     Script script = compile(htmlPage, sourceCode, sourceName, startLine);
/* 342:471 */     if (script == null) {
/* 343:472 */       return null;
/* 344:    */     }
/* 345:474 */     return execute(htmlPage, script);
/* 346:    */   }
/* 347:    */   
/* 348:    */   public Object execute(HtmlPage htmlPage, final Script script)
/* 349:    */   {
/* 350:485 */     final Scriptable scope = getScope(htmlPage, null);
/* 351:    */     
/* 352:487 */     ContextAction action = new HtmlUnitContextAction(scope, htmlPage, script)
/* 353:    */     {
/* 354:    */       public Object doRun(Context cx)
/* 355:    */       {
/* 356:490 */         return script.exec(cx, scope);
/* 357:    */       }
/* 358:    */       
/* 359:    */       protected String getSourceCode(Context cx)
/* 360:    */       {
/* 361:495 */         return null;
/* 362:    */       }
/* 363:498 */     };
/* 364:499 */     return getContextFactory().call(action);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public Object callFunction(HtmlPage htmlPage, Function javaScriptFunction, Scriptable thisObject, Object[] args, DomNode htmlElement)
/* 368:    */   {
/* 369:518 */     Scriptable scope = getScope(htmlPage, htmlElement);
/* 370:    */     
/* 371:520 */     return callFunction(htmlPage, javaScriptFunction, scope, thisObject, args);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public Object callFunction(HtmlPage htmlPage, final Function function, final Scriptable scope, final Scriptable thisObject, final Object[] args)
/* 375:    */   {
/* 376:535 */     ContextAction action = new HtmlUnitContextAction(scope, htmlPage, function)
/* 377:    */     {
/* 378:    */       public Object doRun(Context cx)
/* 379:    */       {
/* 380:538 */         return function.call(cx, scope, thisObject, args);
/* 381:    */       }
/* 382:    */       
/* 383:    */       protected String getSourceCode(Context cx)
/* 384:    */       {
/* 385:542 */         return cx.decompileFunction(function, 2);
/* 386:    */       }
/* 387:544 */     };
/* 388:545 */     return getContextFactory().call(action);
/* 389:    */   }
/* 390:    */   
/* 391:    */   private Scriptable getScope(HtmlPage htmlPage, DomNode htmlElement)
/* 392:    */   {
/* 393:    */     Scriptable scope;
/* 394:    */     Scriptable scope;
/* 395:550 */     if (htmlElement != null) {
/* 396:551 */       scope = htmlElement.getScriptObject();
/* 397:    */     } else {
/* 398:554 */       scope = (Window)htmlPage.getEnclosingWindow().getScriptObject();
/* 399:    */     }
/* 400:556 */     return scope;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public boolean isScriptRunning()
/* 404:    */   {
/* 405:565 */     return Boolean.TRUE.equals(this.javaScriptRunning_.get());
/* 406:    */   }
/* 407:    */   
/* 408:    */   private abstract class HtmlUnitContextAction
/* 409:    */     implements ContextAction
/* 410:    */   {
/* 411:    */     private final Scriptable scope_;
/* 412:    */     private final HtmlPage htmlPage_;
/* 413:    */     
/* 414:    */     public HtmlUnitContextAction(Scriptable scope, HtmlPage htmlPage)
/* 415:    */     {
/* 416:577 */       this.scope_ = scope;
/* 417:578 */       this.htmlPage_ = htmlPage;
/* 418:    */     }
/* 419:    */     
/* 420:    */     /* Error */
/* 421:    */     public final Object run(Context cx)
/* 422:    */     {
/* 423:    */       // Byte code:
/* 424:    */       //   0: aload_0
/* 425:    */       //   1: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 426:    */       //   4: invokestatic 5	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:access$300	(Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;)Ljava/lang/ThreadLocal;
/* 427:    */       //   7: invokevirtual 6	java/lang/ThreadLocal:get	()Ljava/lang/Object;
/* 428:    */       //   10: checkcast 7	java/lang/Boolean
/* 429:    */       //   13: astore_2
/* 430:    */       //   14: aload_0
/* 431:    */       //   15: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 432:    */       //   18: invokestatic 5	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:access$300	(Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;)Ljava/lang/ThreadLocal;
/* 433:    */       //   21: getstatic 8	java/lang/Boolean:TRUE	Ljava/lang/Boolean;
/* 434:    */       //   24: invokevirtual 9	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
/* 435:    */       //   27: aload_1
/* 436:    */       //   28: ldc 10
/* 437:    */       //   30: aload_0
/* 438:    */       //   31: getfield 3	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:scope_	Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;
/* 439:    */       //   34: invokevirtual 11	net/sourceforge/htmlunit/corejs/javascript/Context:putThreadLocal	(Ljava/lang/Object;Ljava/lang/Object;)V
/* 440:    */       //   37: aload_1
/* 441:    */       //   38: ldc 12
/* 442:    */       //   40: aload_0
/* 443:    */       //   41: getfield 4	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:htmlPage_	Lcom/gargoylesoftware/htmlunit/html/HtmlPage;
/* 444:    */       //   44: invokevirtual 11	net/sourceforge/htmlunit/corejs/javascript/Context:putThreadLocal	(Ljava/lang/Object;Ljava/lang/Object;)V
/* 445:    */       //   47: aload_0
/* 446:    */       //   48: getfield 4	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:htmlPage_	Lcom/gargoylesoftware/htmlunit/html/HtmlPage;
/* 447:    */       //   51: dup
/* 448:    */       //   52: astore_3
/* 449:    */       //   53: monitorenter
/* 450:    */       //   54: aload_0
/* 451:    */       //   55: aload_1
/* 452:    */       //   56: invokevirtual 13	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:doRun	(Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/Object;
/* 453:    */       //   59: astore 4
/* 454:    */       //   61: aload_0
/* 455:    */       //   62: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 456:    */       //   65: invokestatic 14	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:access$400	(Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;)V
/* 457:    */       //   68: aload 4
/* 458:    */       //   70: astore 5
/* 459:    */       //   72: aload_3
/* 460:    */       //   73: monitorexit
/* 461:    */       //   74: aload_0
/* 462:    */       //   75: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 463:    */       //   78: invokestatic 5	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:access$300	(Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;)Ljava/lang/ThreadLocal;
/* 464:    */       //   81: aload_2
/* 465:    */       //   82: invokevirtual 9	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
/* 466:    */       //   85: aload 5
/* 467:    */       //   87: areturn
/* 468:    */       //   88: astore 6
/* 469:    */       //   90: aload_3
/* 470:    */       //   91: monitorexit
/* 471:    */       //   92: aload 6
/* 472:    */       //   94: athrow
/* 473:    */       //   95: astore_3
/* 474:    */       //   96: aload_0
/* 475:    */       //   97: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 476:    */       //   100: new 16	com/gargoylesoftware/htmlunit/ScriptException
/* 477:    */       //   103: dup
/* 478:    */       //   104: aload_0
/* 479:    */       //   105: getfield 4	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:htmlPage_	Lcom/gargoylesoftware/htmlunit/html/HtmlPage;
/* 480:    */       //   108: aload_3
/* 481:    */       //   109: aload_0
/* 482:    */       //   110: aload_1
/* 483:    */       //   111: invokevirtual 17	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:getSourceCode	(Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Ljava/lang/String;
/* 484:    */       //   114: invokespecial 18	com/gargoylesoftware/htmlunit/ScriptException:<init>	(Lcom/gargoylesoftware/htmlunit/html/HtmlPage;Ljava/lang/Throwable;Ljava/lang/String;)V
/* 485:    */       //   117: invokevirtual 19	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:handleJavaScriptException	(Lcom/gargoylesoftware/htmlunit/ScriptException;)V
/* 486:    */       //   120: aconst_null
/* 487:    */       //   121: astore 4
/* 488:    */       //   123: aload_0
/* 489:    */       //   124: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 490:    */       //   127: invokestatic 5	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:access$300	(Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;)Ljava/lang/ThreadLocal;
/* 491:    */       //   130: aload_2
/* 492:    */       //   131: invokevirtual 9	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
/* 493:    */       //   134: aload 4
/* 494:    */       //   136: areturn
/* 495:    */       //   137: astore_3
/* 496:    */       //   138: aload_0
/* 497:    */       //   139: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 498:    */       //   142: invokevirtual 21	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:getWebClient	()Lcom/gargoylesoftware/htmlunit/WebClient;
/* 499:    */       //   145: invokevirtual 22	com/gargoylesoftware/htmlunit/WebClient:getJavaScriptErrorListener	()Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptErrorListener;
/* 500:    */       //   148: astore 4
/* 501:    */       //   150: aload 4
/* 502:    */       //   152: ifnull +22 -> 174
/* 503:    */       //   155: aload 4
/* 504:    */       //   157: aload_0
/* 505:    */       //   158: getfield 4	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:htmlPage_	Lcom/gargoylesoftware/htmlunit/html/HtmlPage;
/* 506:    */       //   161: aload_3
/* 507:    */       //   162: invokevirtual 23	com/gargoylesoftware/htmlunit/javascript/TimeoutError:getAllowedTime	()J
/* 508:    */       //   165: aload_3
/* 509:    */       //   166: invokevirtual 24	com/gargoylesoftware/htmlunit/javascript/TimeoutError:getExecutionTime	()J
/* 510:    */       //   169: invokeinterface 25 6 0
/* 511:    */       //   174: aload_0
/* 512:    */       //   175: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 513:    */       //   178: invokevirtual 21	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:getWebClient	()Lcom/gargoylesoftware/htmlunit/WebClient;
/* 514:    */       //   181: invokevirtual 26	com/gargoylesoftware/htmlunit/WebClient:isThrowExceptionOnScriptError	()Z
/* 515:    */       //   184: ifeq +12 -> 196
/* 516:    */       //   187: new 27	java/lang/RuntimeException
/* 517:    */       //   190: dup
/* 518:    */       //   191: aload_3
/* 519:    */       //   192: invokespecial 28	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
/* 520:    */       //   195: athrow
/* 521:    */       //   196: invokestatic 29	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:access$100	()Lorg/apache/commons/logging/Log;
/* 522:    */       //   199: ldc 30
/* 523:    */       //   201: aload_3
/* 524:    */       //   202: invokeinterface 31 3 0
/* 525:    */       //   207: aconst_null
/* 526:    */       //   208: astore 5
/* 527:    */       //   210: aload_0
/* 528:    */       //   211: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 529:    */       //   214: invokestatic 5	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:access$300	(Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;)Ljava/lang/ThreadLocal;
/* 530:    */       //   217: aload_2
/* 531:    */       //   218: invokevirtual 9	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
/* 532:    */       //   221: aload 5
/* 533:    */       //   223: areturn
/* 534:    */       //   224: astore 7
/* 535:    */       //   226: aload_0
/* 536:    */       //   227: getfield 1	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine$HtmlUnitContextAction:this$0	Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;
/* 537:    */       //   230: invokestatic 5	com/gargoylesoftware/htmlunit/javascript/JavaScriptEngine:access$300	(Lcom/gargoylesoftware/htmlunit/javascript/JavaScriptEngine;)Ljava/lang/ThreadLocal;
/* 538:    */       //   233: aload_2
/* 539:    */       //   234: invokevirtual 9	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
/* 540:    */       //   237: aload 7
/* 541:    */       //   239: athrow
/* 542:    */       // Line number table:
/* 543:    */       //   Java source line #582	-> byte code offset #0
/* 544:    */       //   Java source line #583	-> byte code offset #14
/* 545:    */       //   Java source line #586	-> byte code offset #27
/* 546:    */       //   Java source line #587	-> byte code offset #37
/* 547:    */       //   Java source line #588	-> byte code offset #47
/* 548:    */       //   Java source line #589	-> byte code offset #54
/* 549:    */       //   Java source line #590	-> byte code offset #61
/* 550:    */       //   Java source line #591	-> byte code offset #68
/* 551:    */       //   Java source line #610	-> byte code offset #74
/* 552:    */       //   Java source line #592	-> byte code offset #88
/* 553:    */       //   Java source line #594	-> byte code offset #95
/* 554:    */       //   Java source line #595	-> byte code offset #96
/* 555:    */       //   Java source line #596	-> byte code offset #120
/* 556:    */       //   Java source line #610	-> byte code offset #123
/* 557:    */       //   Java source line #598	-> byte code offset #137
/* 558:    */       //   Java source line #599	-> byte code offset #138
/* 559:    */       //   Java source line #600	-> byte code offset #150
/* 560:    */       //   Java source line #601	-> byte code offset #155
/* 561:    */       //   Java source line #603	-> byte code offset #174
/* 562:    */       //   Java source line #604	-> byte code offset #187
/* 563:    */       //   Java source line #606	-> byte code offset #196
/* 564:    */       //   Java source line #607	-> byte code offset #207
/* 565:    */       //   Java source line #610	-> byte code offset #210
/* 566:    */       // Local variable table:
/* 567:    */       //   start	length	slot	name	signature
/* 568:    */       //   0	240	0	this	HtmlUnitContextAction
/* 569:    */       //   0	240	1	cx	Context
/* 570:    */       //   13	221	2	javaScriptAlreadyRunning	Boolean
/* 571:    */       //   95	14	3	e	Exception
/* 572:    */       //   137	65	3	e	TimeoutError
/* 573:    */       //   59	76	4	response	Object
/* 574:    */       //   148	8	4	javaScriptErrorListener	JavaScriptErrorListener
/* 575:    */       //   70	152	5	localObject1	Object
/* 576:    */       //   88	5	6	localObject2	Object
/* 577:    */       //   224	14	7	localObject3	Object
/* 578:    */       // Exception table:
/* 579:    */       //   from	to	target	type
/* 580:    */       //   54	74	88	finally
/* 581:    */       //   88	92	88	finally
/* 582:    */       //   27	74	95	java/lang/Exception
/* 583:    */       //   88	95	95	java/lang/Exception
/* 584:    */       //   27	74	137	com/gargoylesoftware/htmlunit/javascript/TimeoutError
/* 585:    */       //   88	95	137	com/gargoylesoftware/htmlunit/javascript/TimeoutError
/* 586:    */       //   27	74	224	finally
/* 587:    */       //   88	123	224	finally
/* 588:    */       //   137	210	224	finally
/* 589:    */       //   224	226	224	finally
/* 590:    */     }
/* 591:    */     
/* 592:    */     protected abstract Object doRun(Context paramContext);
/* 593:    */     
/* 594:    */     protected abstract String getSourceCode(Context paramContext);
/* 595:    */   }
/* 596:    */   
/* 597:    */   private void doProcessPostponedActions()
/* 598:    */   {
/* 599:620 */     if (Boolean.TRUE.equals(this.holdPostponedActions_.get())) {
/* 600:621 */       return;
/* 601:    */     }
/* 602:    */     try
/* 603:    */     {
/* 604:625 */       getWebClient().loadDownloadedResponses();
/* 605:    */     }
/* 606:    */     catch (RuntimeException e)
/* 607:    */     {
/* 608:628 */       throw e;
/* 609:    */     }
/* 610:    */     catch (Exception e)
/* 611:    */     {
/* 612:631 */       throw new RuntimeException(e);
/* 613:    */     }
/* 614:634 */     List<PostponedAction> actions = (List)this.postponedActions_.get();
/* 615:635 */     this.postponedActions_.set(null);
/* 616:636 */     if (actions != null) {
/* 617:    */       try
/* 618:    */       {
/* 619:638 */         for (PostponedAction action : actions)
/* 620:    */         {
/* 621:640 */           Page owningPage = action.getOwningPage();
/* 622:641 */           if ((owningPage != null) && (owningPage == owningPage.getEnclosingWindow().getEnclosedPage())) {
/* 623:642 */             action.execute();
/* 624:    */           }
/* 625:    */         }
/* 626:    */       }
/* 627:    */       catch (Exception e)
/* 628:    */       {
/* 629:647 */         Context.throwAsScriptRuntimeEx(e);
/* 630:    */       }
/* 631:    */     }
/* 632:    */   }
/* 633:    */   
/* 634:    */   public void addPostponedAction(PostponedAction action)
/* 635:    */   {
/* 636:657 */     List<PostponedAction> actions = (List)this.postponedActions_.get();
/* 637:658 */     if (actions == null)
/* 638:    */     {
/* 639:659 */       actions = new ArrayList();
/* 640:660 */       this.postponedActions_.set(actions);
/* 641:    */     }
/* 642:662 */     actions.add(action);
/* 643:    */   }
/* 644:    */   
/* 645:    */   protected void handleJavaScriptException(ScriptException scriptException)
/* 646:    */   {
/* 647:671 */     HtmlPage page = scriptException.getPage();
/* 648:672 */     if (page != null)
/* 649:    */     {
/* 650:673 */       WebWindow window = page.getEnclosingWindow();
/* 651:674 */       if (window != null)
/* 652:    */       {
/* 653:675 */         Window w = (Window)window.getScriptObject();
/* 654:676 */         if (w != null) {
/* 655:677 */           w.triggerOnError(scriptException);
/* 656:    */         }
/* 657:    */       }
/* 658:    */     }
/* 659:681 */     JavaScriptErrorListener javaScriptErrorListener = getWebClient().getJavaScriptErrorListener();
/* 660:682 */     if (javaScriptErrorListener != null) {
/* 661:683 */       javaScriptErrorListener.scriptException(page, scriptException);
/* 662:    */     }
/* 663:686 */     if (getWebClient().isThrowExceptionOnScriptError()) {
/* 664:687 */       throw scriptException;
/* 665:    */     }
/* 666:690 */     LOG.info("Caught script exception", scriptException);
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void holdPosponedActions()
/* 670:    */   {
/* 671:698 */     this.holdPostponedActions_.set(Boolean.TRUE);
/* 672:    */   }
/* 673:    */   
/* 674:    */   public void processPostponedActions()
/* 675:    */   {
/* 676:706 */     this.holdPostponedActions_.set(Boolean.FALSE);
/* 677:707 */     doProcessPostponedActions();
/* 678:    */   }
/* 679:    */   
/* 680:    */   private void readObject(ObjectInputStream in)
/* 681:    */     throws IOException, ClassNotFoundException
/* 682:    */   {
/* 683:714 */     in.defaultReadObject();
/* 684:715 */     initTransientFields();
/* 685:    */   }
/* 686:    */   
/* 687:    */   private void initTransientFields()
/* 688:    */   {
/* 689:719 */     this.javaScriptRunning_ = new ThreadLocal();
/* 690:720 */     this.postponedActions_ = new ThreadLocal();
/* 691:721 */     this.holdPostponedActions_ = new ThreadLocal();
/* 692:    */   }
/* 693:    */   
/* 694:    */   private static class FallbackCaller
/* 695:    */     extends ScriptableObject
/* 696:    */   {
/* 697:    */     public Object get(String name, Scriptable start)
/* 698:    */     {
/* 699:727 */       if ((start instanceof ScriptableWithFallbackGetter)) {
/* 700:728 */         return ((ScriptableWithFallbackGetter)start).getWithFallback(name);
/* 701:    */       }
/* 702:730 */       return NOT_FOUND;
/* 703:    */     }
/* 704:    */     
/* 705:    */     public String getClassName()
/* 706:    */     {
/* 707:735 */       return "htmlUnitHelper-fallbackCaller";
/* 708:    */     }
/* 709:    */   }
/* 710:    */   
/* 711:    */   public Class<? extends SimpleScriptable> getJavaScriptClass(Class<?> c)
/* 712:    */   {
/* 713:745 */     return (Class)this.jsConfig_.getHtmlJavaScriptMapping().get(c);
/* 714:    */   }
/* 715:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine
 * JD-Core Version:    0.7.0.1
 */