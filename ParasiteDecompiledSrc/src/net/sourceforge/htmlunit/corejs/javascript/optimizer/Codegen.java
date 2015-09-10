/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*    2:     */ 
/*    3:     */ import java.lang.reflect.Constructor;
/*    4:     */ import java.util.HashMap;
/*    5:     */ import java.util.List;
/*    6:     */ import java.util.Map;
/*    7:     */ import net.sourceforge.htmlunit.corejs.classfile.ClassFileWriter;
/*    8:     */ import net.sourceforge.htmlunit.corejs.classfile.ClassFileWriter.ClassFileFormatException;
/*    9:     */ import net.sourceforge.htmlunit.corejs.javascript.CompilerEnvirons;
/*   10:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   11:     */ import net.sourceforge.htmlunit.corejs.javascript.Evaluator;
/*   12:     */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   13:     */ import net.sourceforge.htmlunit.corejs.javascript.GeneratedClassLoader;
/*   14:     */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*   15:     */ import net.sourceforge.htmlunit.corejs.javascript.NativeFunction;
/*   16:     */ import net.sourceforge.htmlunit.corejs.javascript.ObjArray;
/*   17:     */ import net.sourceforge.htmlunit.corejs.javascript.ObjToIntMap;
/*   18:     */ import net.sourceforge.htmlunit.corejs.javascript.RhinoException;
/*   19:     */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*   20:     */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   21:     */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   22:     */ import net.sourceforge.htmlunit.corejs.javascript.SecurityController;
/*   23:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*   24:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Name;
/*   25:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*   26:     */ 
/*   27:     */ public class Codegen
/*   28:     */   implements Evaluator
/*   29:     */ {
/*   30:     */   static final String DEFAULT_MAIN_METHOD_CLASS = "net.sourceforge.htmlunit.corejs.javascript.optimizer.OptRuntime";
/*   31:     */   private static final String SUPER_CLASS_NAME = "net.sourceforge.htmlunit.corejs.javascript.NativeFunction";
/*   32:     */   static final String DIRECT_CALL_PARENT_FIELD = "_dcp";
/*   33:     */   private static final String ID_FIELD_NAME = "_id";
/*   34:     */   private static final String REGEXP_INIT_METHOD_NAME = "_reInit";
/*   35:     */   private static final String REGEXP_INIT_METHOD_SIGNATURE = "(Lnet/sourceforge/htmlunit/corejs/javascript/RegExpProxy;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)V";
/*   36:     */   static final String REGEXP_ARRAY_FIELD_NAME = "_re";
/*   37:     */   static final String REGEXP_ARRAY_FIELD_TYPE = "[Ljava/lang/Object;";
/*   38:     */   static final String FUNCTION_INIT_SIGNATURE = "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)V";
/*   39:     */   static final String FUNCTION_CONSTRUCTOR_SIGNATURE = "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)V";
/*   40:     */   
/*   41:     */   public void captureStackInfo(RhinoException ex)
/*   42:     */   {
/*   43:  66 */     throw new UnsupportedOperationException();
/*   44:     */   }
/*   45:     */   
/*   46:     */   public String getSourcePositionFromStack(Context cx, int[] linep)
/*   47:     */   {
/*   48:  70 */     throw new UnsupportedOperationException();
/*   49:     */   }
/*   50:     */   
/*   51:     */   public String getPatchedStack(RhinoException ex, String nativeStackTrace)
/*   52:     */   {
/*   53:  74 */     throw new UnsupportedOperationException();
/*   54:     */   }
/*   55:     */   
/*   56:     */   public List<String> getScriptStack(RhinoException ex)
/*   57:     */   {
/*   58:  78 */     throw new UnsupportedOperationException();
/*   59:     */   }
/*   60:     */   
/*   61:     */   public void setEvalScriptFlag(Script script)
/*   62:     */   {
/*   63:  82 */     throw new UnsupportedOperationException();
/*   64:     */   }
/*   65:     */   
/*   66:     */   public Object compile(CompilerEnvirons compilerEnv, ScriptNode tree, String encodedSource, boolean returnFunction)
/*   67:     */   {
/*   68:     */     int serial;
/*   69:  91 */     synchronized (globalLock)
/*   70:     */     {
/*   71:  92 */       serial = ++globalSerialClassCounter;
/*   72:     */     }
/*   73:  95 */     String baseName = "c";
/*   74:  96 */     if (tree.getSourceName().length() > 0)
/*   75:     */     {
/*   76:  97 */       baseName = tree.getSourceName().replaceAll("\\W", "_");
/*   77:  98 */       if (!Character.isJavaIdentifierStart(baseName.charAt(0))) {
/*   78:  99 */         baseName = "_" + baseName;
/*   79:     */       }
/*   80:     */     }
/*   81: 103 */     String mainClassName = "net.sourceforge.htmlunit.corejs.javascript.gen." + baseName + "_" + serial;
/*   82:     */     
/*   83: 105 */     byte[] mainClassBytes = compileToClassFile(compilerEnv, mainClassName, tree, encodedSource, returnFunction);
/*   84:     */     
/*   85:     */ 
/*   86:     */ 
/*   87: 109 */     return new Object[] { mainClassName, mainClassBytes };
/*   88:     */   }
/*   89:     */   
/*   90:     */   public Script createScriptObject(Object bytecode, Object staticSecurityDomain)
/*   91:     */   {
/*   92: 115 */     Class<?> cl = defineClass(bytecode, staticSecurityDomain);
/*   93:     */     Script script;
/*   94:     */     try
/*   95:     */     {
/*   96: 119 */       script = (Script)cl.newInstance();
/*   97:     */     }
/*   98:     */     catch (Exception ex)
/*   99:     */     {
/*  100: 121 */       throw new RuntimeException("Unable to instantiate compiled class:" + ex.toString());
/*  101:     */     }
/*  102: 124 */     return script;
/*  103:     */   }
/*  104:     */   
/*  105:     */   public Function createFunctionObject(Context cx, Scriptable scope, Object bytecode, Object staticSecurityDomain)
/*  106:     */   {
/*  107: 131 */     Class<?> cl = defineClass(bytecode, staticSecurityDomain);
/*  108:     */     NativeFunction f;
/*  109:     */     try
/*  110:     */     {
/*  111: 135 */       Constructor<?> ctor = cl.getConstructors()[0];
/*  112: 136 */       Object[] initArgs = { scope, cx, Integer.valueOf(0) };
/*  113: 137 */       f = (NativeFunction)ctor.newInstance(initArgs);
/*  114:     */     }
/*  115:     */     catch (Exception ex)
/*  116:     */     {
/*  117: 139 */       throw new RuntimeException("Unable to instantiate compiled class:" + ex.toString());
/*  118:     */     }
/*  119: 142 */     return f;
/*  120:     */   }
/*  121:     */   
/*  122:     */   private Class<?> defineClass(Object bytecode, Object staticSecurityDomain)
/*  123:     */   {
/*  124: 148 */     Object[] nameBytesPair = (Object[])bytecode;
/*  125: 149 */     String className = (String)nameBytesPair[0];
/*  126: 150 */     byte[] classBytes = (byte[])nameBytesPair[1];
/*  127:     */     
/*  128:     */ 
/*  129:     */ 
/*  130: 154 */     ClassLoader rhinoLoader = getClass().getClassLoader();
/*  131:     */     
/*  132: 156 */     GeneratedClassLoader loader = SecurityController.createLoader(rhinoLoader, staticSecurityDomain);
/*  133:     */     Exception e;
/*  134:     */     try
/*  135:     */     {
/*  136: 160 */       Class<?> cl = loader.defineClass(className, classBytes);
/*  137: 161 */       loader.linkClass(cl);
/*  138: 162 */       return cl;
/*  139:     */     }
/*  140:     */     catch (SecurityException x)
/*  141:     */     {
/*  142: 164 */       e = x;
/*  143:     */     }
/*  144:     */     catch (IllegalArgumentException x)
/*  145:     */     {
/*  146: 166 */       e = x;
/*  147:     */     }
/*  148: 168 */     throw new RuntimeException("Malformed optimizer package " + e);
/*  149:     */   }
/*  150:     */   
/*  151:     */   byte[] compileToClassFile(CompilerEnvirons compilerEnv, String mainClassName, ScriptNode scriptOrFn, String encodedSource, boolean returnFunction)
/*  152:     */   {
/*  153: 177 */     this.compilerEnv = compilerEnv;
/*  154:     */     
/*  155: 179 */     transform(scriptOrFn);
/*  156: 185 */     if (returnFunction) {
/*  157: 186 */       scriptOrFn = scriptOrFn.getFunctionNode(0);
/*  158:     */     }
/*  159: 189 */     initScriptNodesData(scriptOrFn);
/*  160:     */     
/*  161: 191 */     this.mainClassName = mainClassName;
/*  162: 192 */     this.mainClassSignature = ClassFileWriter.classNameToSignature(mainClassName);
/*  163:     */     try
/*  164:     */     {
/*  165: 196 */       return generateCode(encodedSource);
/*  166:     */     }
/*  167:     */     catch (ClassFileWriter.ClassFileFormatException e)
/*  168:     */     {
/*  169: 198 */       throw reportClassFileFormatException(scriptOrFn, e.getMessage());
/*  170:     */     }
/*  171:     */   }
/*  172:     */   
/*  173:     */   private RuntimeException reportClassFileFormatException(ScriptNode scriptOrFn, String message)
/*  174:     */   {
/*  175: 206 */     String msg = (scriptOrFn instanceof FunctionNode) ? ScriptRuntime.getMessage2("msg.while.compiling.fn", ((FunctionNode)scriptOrFn).getFunctionName(), message) : ScriptRuntime.getMessage1("msg.while.compiling.script", message);
/*  176:     */     
/*  177:     */ 
/*  178:     */ 
/*  179: 210 */     return Context.reportRuntimeError(msg, scriptOrFn.getSourceName(), scriptOrFn.getLineno(), null, 0);
/*  180:     */   }
/*  181:     */   
/*  182:     */   private void transform(ScriptNode tree)
/*  183:     */   {
/*  184: 216 */     initOptFunctions_r(tree);
/*  185:     */     
/*  186: 218 */     int optLevel = this.compilerEnv.getOptimizationLevel();
/*  187:     */     
/*  188: 220 */     Map<String, OptFunctionNode> possibleDirectCalls = null;
/*  189: 221 */     if (optLevel > 0) {
/*  190: 227 */       if (tree.getType() == 136)
/*  191:     */       {
/*  192: 228 */         int functionCount = tree.getFunctionCount();
/*  193: 229 */         for (int i = 0; i != functionCount; i++)
/*  194:     */         {
/*  195: 230 */           OptFunctionNode ofn = OptFunctionNode.get(tree, i);
/*  196: 231 */           if (ofn.fnode.getFunctionType() == 1)
/*  197:     */           {
/*  198: 234 */             String name = ofn.fnode.getName();
/*  199: 235 */             if (name.length() != 0)
/*  200:     */             {
/*  201: 236 */               if (possibleDirectCalls == null) {
/*  202: 237 */                 possibleDirectCalls = new HashMap();
/*  203:     */               }
/*  204: 239 */               possibleDirectCalls.put(name, ofn);
/*  205:     */             }
/*  206:     */           }
/*  207:     */         }
/*  208:     */       }
/*  209:     */     }
/*  210: 246 */     if (possibleDirectCalls != null) {
/*  211: 247 */       this.directCallTargets = new ObjArray();
/*  212:     */     }
/*  213: 250 */     OptTransformer ot = new OptTransformer(possibleDirectCalls, this.directCallTargets);
/*  214:     */     
/*  215: 252 */     ot.transform(tree);
/*  216: 254 */     if (optLevel > 0) {
/*  217: 255 */       new Optimizer().optimize(tree);
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   private static void initOptFunctions_r(ScriptNode scriptOrFn)
/*  222:     */   {
/*  223: 261 */     int i = 0;
/*  224: 261 */     for (int N = scriptOrFn.getFunctionCount(); i != N; i++)
/*  225:     */     {
/*  226: 262 */       FunctionNode fn = scriptOrFn.getFunctionNode(i);
/*  227: 263 */       new OptFunctionNode(fn);
/*  228: 264 */       initOptFunctions_r(fn);
/*  229:     */     }
/*  230:     */   }
/*  231:     */   
/*  232:     */   private void initScriptNodesData(ScriptNode scriptOrFn)
/*  233:     */   {
/*  234: 270 */     ObjArray x = new ObjArray();
/*  235: 271 */     collectScriptNodes_r(scriptOrFn, x);
/*  236:     */     
/*  237: 273 */     int count = x.size();
/*  238: 274 */     this.scriptOrFnNodes = new ScriptNode[count];
/*  239: 275 */     x.toArray(this.scriptOrFnNodes);
/*  240:     */     
/*  241: 277 */     this.scriptOrFnIndexes = new ObjToIntMap(count);
/*  242: 278 */     for (int i = 0; i != count; i++) {
/*  243: 279 */       this.scriptOrFnIndexes.put(this.scriptOrFnNodes[i], i);
/*  244:     */     }
/*  245:     */   }
/*  246:     */   
/*  247:     */   private static void collectScriptNodes_r(ScriptNode n, ObjArray x)
/*  248:     */   {
/*  249: 286 */     x.add(n);
/*  250: 287 */     int nestedCount = n.getFunctionCount();
/*  251: 288 */     for (int i = 0; i != nestedCount; i++) {
/*  252: 289 */       collectScriptNodes_r(n.getFunctionNode(i), x);
/*  253:     */     }
/*  254:     */   }
/*  255:     */   
/*  256:     */   private byte[] generateCode(String encodedSource)
/*  257:     */   {
/*  258: 295 */     boolean hasScript = this.scriptOrFnNodes[0].getType() == 136;
/*  259: 296 */     boolean hasFunctions = (this.scriptOrFnNodes.length > 1) || (!hasScript);
/*  260:     */     
/*  261: 298 */     String sourceFile = null;
/*  262: 299 */     if (this.compilerEnv.isGenerateDebugInfo()) {
/*  263: 300 */       sourceFile = this.scriptOrFnNodes[0].getSourceName();
/*  264:     */     }
/*  265: 303 */     ClassFileWriter cfw = new ClassFileWriter(this.mainClassName, "net.sourceforge.htmlunit.corejs.javascript.NativeFunction", sourceFile);
/*  266:     */     
/*  267:     */ 
/*  268: 306 */     cfw.addField("_id", "I", (short)2);
/*  269:     */     
/*  270: 308 */     cfw.addField("_dcp", this.mainClassSignature, (short)2);
/*  271:     */     
/*  272: 310 */     cfw.addField("_re", "[Ljava/lang/Object;", (short)2);
/*  273: 313 */     if (hasFunctions) {
/*  274: 314 */       generateFunctionConstructor(cfw);
/*  275:     */     }
/*  276: 317 */     if (hasScript)
/*  277:     */     {
/*  278: 318 */       cfw.addInterface("net/sourceforge/htmlunit/corejs/javascript/Script");
/*  279: 319 */       generateScriptCtor(cfw);
/*  280: 320 */       generateMain(cfw);
/*  281: 321 */       generateExecute(cfw);
/*  282:     */     }
/*  283: 324 */     generateCallMethod(cfw);
/*  284: 325 */     generateResumeGenerator(cfw);
/*  285:     */     
/*  286: 327 */     generateNativeFunctionOverrides(cfw, encodedSource);
/*  287:     */     
/*  288: 329 */     int count = this.scriptOrFnNodes.length;
/*  289: 330 */     for (int i = 0; i != count; i++)
/*  290:     */     {
/*  291: 331 */       ScriptNode n = this.scriptOrFnNodes[i];
/*  292:     */       
/*  293: 333 */       BodyCodegen bodygen = new BodyCodegen();
/*  294: 334 */       bodygen.cfw = cfw;
/*  295: 335 */       bodygen.codegen = this;
/*  296: 336 */       bodygen.compilerEnv = this.compilerEnv;
/*  297: 337 */       bodygen.scriptOrFn = n;
/*  298: 338 */       bodygen.scriptOrFnIndex = i;
/*  299:     */       try
/*  300:     */       {
/*  301: 341 */         bodygen.generateBodyCode();
/*  302:     */       }
/*  303:     */       catch (ClassFileWriter.ClassFileFormatException e)
/*  304:     */       {
/*  305: 343 */         throw reportClassFileFormatException(n, e.getMessage());
/*  306:     */       }
/*  307: 346 */       if (n.getType() == 109)
/*  308:     */       {
/*  309: 347 */         OptFunctionNode ofn = OptFunctionNode.get(n);
/*  310: 348 */         generateFunctionInit(cfw, ofn);
/*  311: 349 */         if (ofn.isTargetOfDirectCall()) {
/*  312: 350 */           emitDirectConstructor(cfw, ofn);
/*  313:     */         }
/*  314:     */       }
/*  315:     */     }
/*  316: 355 */     if (this.directCallTargets != null)
/*  317:     */     {
/*  318: 356 */       int N = this.directCallTargets.size();
/*  319: 357 */       for (int j = 0; j != N; j++) {
/*  320: 358 */         cfw.addField(getDirectTargetFieldName(j), this.mainClassSignature, (short)2);
/*  321:     */       }
/*  322:     */     }
/*  323: 364 */     emitRegExpInit(cfw);
/*  324: 365 */     emitConstantDudeInitializers(cfw);
/*  325:     */     
/*  326: 367 */     return cfw.toByteArray();
/*  327:     */   }
/*  328:     */   
/*  329:     */   private void emitDirectConstructor(ClassFileWriter cfw, OptFunctionNode ofn)
/*  330:     */   {
/*  331: 384 */     cfw.startMethod(getDirectCtorName(ofn.fnode), getBodyMethodSignature(ofn.fnode), (short)10);
/*  332:     */     
/*  333:     */ 
/*  334:     */ 
/*  335:     */ 
/*  336: 389 */     int argCount = ofn.fnode.getParamCount();
/*  337: 390 */     int firstLocal = 4 + argCount * 3 + 1;
/*  338:     */     
/*  339: 392 */     cfw.addALoad(0);
/*  340: 393 */     cfw.addALoad(1);
/*  341: 394 */     cfw.addALoad(2);
/*  342: 395 */     cfw.addInvoke(182, "net/sourceforge/htmlunit/corejs/javascript/BaseFunction", "createObject", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  343:     */     
/*  344:     */ 
/*  345:     */ 
/*  346:     */ 
/*  347:     */ 
/*  348: 401 */     cfw.addAStore(firstLocal);
/*  349:     */     
/*  350: 403 */     cfw.addALoad(0);
/*  351: 404 */     cfw.addALoad(1);
/*  352: 405 */     cfw.addALoad(2);
/*  353: 406 */     cfw.addALoad(firstLocal);
/*  354: 407 */     for (int i = 0; i < argCount; i++)
/*  355:     */     {
/*  356: 408 */       cfw.addALoad(4 + i * 3);
/*  357: 409 */       cfw.addDLoad(5 + i * 3);
/*  358:     */     }
/*  359: 411 */     cfw.addALoad(4 + argCount * 3);
/*  360: 412 */     cfw.addInvoke(184, this.mainClassName, getBodyMethodName(ofn.fnode), getBodyMethodSignature(ofn.fnode));
/*  361:     */     
/*  362:     */ 
/*  363:     */ 
/*  364: 416 */     int exitLabel = cfw.acquireLabel();
/*  365: 417 */     cfw.add(89);
/*  366: 418 */     cfw.add(193, "net/sourceforge/htmlunit/corejs/javascript/Scriptable");
/*  367: 419 */     cfw.add(153, exitLabel);
/*  368:     */     
/*  369: 421 */     cfw.add(192, "net/sourceforge/htmlunit/corejs/javascript/Scriptable");
/*  370: 422 */     cfw.add(176);
/*  371: 423 */     cfw.markLabel(exitLabel);
/*  372:     */     
/*  373: 425 */     cfw.addALoad(firstLocal);
/*  374: 426 */     cfw.add(176);
/*  375:     */     
/*  376: 428 */     cfw.stopMethod((short)(firstLocal + 1));
/*  377:     */   }
/*  378:     */   
/*  379:     */   static boolean isGenerator(ScriptNode node)
/*  380:     */   {
/*  381: 433 */     return (node.getType() == 109) && (((FunctionNode)node).isGenerator());
/*  382:     */   }
/*  383:     */   
/*  384:     */   private void generateResumeGenerator(ClassFileWriter cfw)
/*  385:     */   {
/*  386: 451 */     boolean hasGenerators = false;
/*  387: 452 */     for (int i = 0; i < this.scriptOrFnNodes.length; i++) {
/*  388: 453 */       if (isGenerator(this.scriptOrFnNodes[i])) {
/*  389: 454 */         hasGenerators = true;
/*  390:     */       }
/*  391:     */     }
/*  392: 459 */     if (!hasGenerators) {
/*  393: 460 */       return;
/*  394:     */     }
/*  395: 462 */     cfw.startMethod("resumeGenerator", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;ILjava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", (short)17);
/*  396:     */     
/*  397:     */ 
/*  398:     */ 
/*  399:     */ 
/*  400:     */ 
/*  401:     */ 
/*  402:     */ 
/*  403:     */ 
/*  404: 471 */     cfw.addALoad(0);
/*  405: 472 */     cfw.addALoad(1);
/*  406: 473 */     cfw.addALoad(2);
/*  407: 474 */     cfw.addALoad(4);
/*  408: 475 */     cfw.addALoad(5);
/*  409: 476 */     cfw.addILoad(3);
/*  410:     */     
/*  411: 478 */     cfw.addLoadThis();
/*  412: 479 */     cfw.add(180, cfw.getClassName(), "_id", "I");
/*  413:     */     
/*  414: 481 */     int startSwitch = cfw.addTableSwitch(0, this.scriptOrFnNodes.length - 1);
/*  415: 482 */     cfw.markTableSwitchDefault(startSwitch);
/*  416: 483 */     int endlabel = cfw.acquireLabel();
/*  417: 485 */     for (int i = 0; i < this.scriptOrFnNodes.length; i++)
/*  418:     */     {
/*  419: 486 */       ScriptNode n = this.scriptOrFnNodes[i];
/*  420: 487 */       cfw.markTableSwitchCase(startSwitch, i, 6);
/*  421: 488 */       if (isGenerator(n))
/*  422:     */       {
/*  423: 489 */         String type = "(" + this.mainClassSignature + "Lnet/sourceforge/htmlunit/corejs/javascript/Context;" + "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;" + "Ljava/lang/Object;" + "Ljava/lang/Object;I)Ljava/lang/Object;";
/*  424:     */         
/*  425:     */ 
/*  426:     */ 
/*  427:     */ 
/*  428:     */ 
/*  429: 495 */         cfw.addInvoke(184, this.mainClassName, getBodyMethodName(n) + "_gen", type);
/*  430:     */         
/*  431:     */ 
/*  432:     */ 
/*  433: 499 */         cfw.add(176);
/*  434:     */       }
/*  435:     */       else
/*  436:     */       {
/*  437: 501 */         cfw.add(167, endlabel);
/*  438:     */       }
/*  439:     */     }
/*  440: 505 */     cfw.markLabel(endlabel);
/*  441: 506 */     pushUndefined(cfw);
/*  442: 507 */     cfw.add(176);
/*  443:     */     
/*  444:     */ 
/*  445:     */ 
/*  446: 511 */     cfw.stopMethod((short)6);
/*  447:     */   }
/*  448:     */   
/*  449:     */   private void generateCallMethod(ClassFileWriter cfw)
/*  450:     */   {
/*  451: 516 */     cfw.startMethod("call", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;", (short)17);
/*  452:     */     
/*  453:     */ 
/*  454:     */ 
/*  455:     */ 
/*  456:     */ 
/*  457:     */ 
/*  458:     */ 
/*  459:     */ 
/*  460:     */ 
/*  461:     */ 
/*  462:     */ 
/*  463:     */ 
/*  464: 529 */     int nonTopCallLabel = cfw.acquireLabel();
/*  465: 530 */     cfw.addALoad(1);
/*  466: 531 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/ScriptRuntime", "hasTopCall", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Z");
/*  467:     */     
/*  468:     */ 
/*  469:     */ 
/*  470:     */ 
/*  471: 536 */     cfw.add(154, nonTopCallLabel);
/*  472: 537 */     cfw.addALoad(0);
/*  473: 538 */     cfw.addALoad(1);
/*  474: 539 */     cfw.addALoad(2);
/*  475: 540 */     cfw.addALoad(3);
/*  476: 541 */     cfw.addALoad(4);
/*  477: 542 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/ScriptRuntime", "doTopCall", "(Lnet/sourceforge/htmlunit/corejs/javascript/Callable;Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;");
/*  478:     */     
/*  479:     */ 
/*  480:     */ 
/*  481:     */ 
/*  482:     */ 
/*  483:     */ 
/*  484:     */ 
/*  485:     */ 
/*  486: 551 */     cfw.add(176);
/*  487: 552 */     cfw.markLabel(nonTopCallLabel);
/*  488:     */     
/*  489:     */ 
/*  490: 555 */     cfw.addALoad(0);
/*  491: 556 */     cfw.addALoad(1);
/*  492: 557 */     cfw.addALoad(2);
/*  493: 558 */     cfw.addALoad(3);
/*  494: 559 */     cfw.addALoad(4);
/*  495:     */     
/*  496: 561 */     int end = this.scriptOrFnNodes.length;
/*  497: 562 */     boolean generateSwitch = 2 <= end;
/*  498:     */     
/*  499: 564 */     int switchStart = 0;
/*  500: 565 */     int switchStackTop = 0;
/*  501: 566 */     if (generateSwitch)
/*  502:     */     {
/*  503: 567 */       cfw.addLoadThis();
/*  504: 568 */       cfw.add(180, cfw.getClassName(), "_id", "I");
/*  505:     */       
/*  506:     */ 
/*  507: 571 */       switchStart = cfw.addTableSwitch(1, end - 1);
/*  508:     */     }
/*  509: 574 */     for (int i = 0; i != end; i++)
/*  510:     */     {
/*  511: 575 */       ScriptNode n = this.scriptOrFnNodes[i];
/*  512: 576 */       if (generateSwitch) {
/*  513: 577 */         if (i == 0)
/*  514:     */         {
/*  515: 578 */           cfw.markTableSwitchDefault(switchStart);
/*  516: 579 */           switchStackTop = cfw.getStackTop();
/*  517:     */         }
/*  518:     */         else
/*  519:     */         {
/*  520: 581 */           cfw.markTableSwitchCase(switchStart, i - 1, switchStackTop);
/*  521:     */         }
/*  522:     */       }
/*  523: 585 */       if (n.getType() == 109)
/*  524:     */       {
/*  525: 586 */         OptFunctionNode ofn = OptFunctionNode.get(n);
/*  526: 587 */         if (ofn.isTargetOfDirectCall())
/*  527:     */         {
/*  528: 588 */           int pcount = ofn.fnode.getParamCount();
/*  529: 589 */           if (pcount != 0) {
/*  530: 592 */             for (int p = 0; p != pcount; p++)
/*  531:     */             {
/*  532: 593 */               cfw.add(190);
/*  533: 594 */               cfw.addPush(p);
/*  534: 595 */               int undefArg = cfw.acquireLabel();
/*  535: 596 */               int beyond = cfw.acquireLabel();
/*  536: 597 */               cfw.add(164, undefArg);
/*  537:     */               
/*  538: 599 */               cfw.addALoad(4);
/*  539: 600 */               cfw.addPush(p);
/*  540: 601 */               cfw.add(50);
/*  541: 602 */               cfw.add(167, beyond);
/*  542: 603 */               cfw.markLabel(undefArg);
/*  543: 604 */               pushUndefined(cfw);
/*  544: 605 */               cfw.markLabel(beyond);
/*  545:     */               
/*  546: 607 */               cfw.adjustStackTop(-1);
/*  547: 608 */               cfw.addPush(0.0D);
/*  548:     */               
/*  549: 610 */               cfw.addALoad(4);
/*  550:     */             }
/*  551:     */           }
/*  552:     */         }
/*  553:     */       }
/*  554: 615 */       cfw.addInvoke(184, this.mainClassName, getBodyMethodName(n), getBodyMethodSignature(n));
/*  555:     */       
/*  556:     */ 
/*  557:     */ 
/*  558: 619 */       cfw.add(176);
/*  559:     */     }
/*  560: 621 */     cfw.stopMethod((short)5);
/*  561:     */   }
/*  562:     */   
/*  563:     */   private void generateMain(ClassFileWriter cfw)
/*  564:     */   {
/*  565: 627 */     cfw.startMethod("main", "([Ljava/lang/String;)V", (short)9);
/*  566:     */     
/*  567:     */ 
/*  568:     */ 
/*  569:     */ 
/*  570: 632 */     cfw.add(187, cfw.getClassName());
/*  571: 633 */     cfw.add(89);
/*  572: 634 */     cfw.addInvoke(183, cfw.getClassName(), "<init>", "()V");
/*  573:     */     
/*  574:     */ 
/*  575: 637 */     cfw.add(42);
/*  576:     */     
/*  577: 639 */     cfw.addInvoke(184, this.mainMethodClass, "main", "(Lnet/sourceforge/htmlunit/corejs/javascript/Script;[Ljava/lang/String;)V");
/*  578:     */     
/*  579:     */ 
/*  580:     */ 
/*  581: 643 */     cfw.add(177);
/*  582:     */     
/*  583: 645 */     cfw.stopMethod((short)1);
/*  584:     */   }
/*  585:     */   
/*  586:     */   private void generateExecute(ClassFileWriter cfw)
/*  587:     */   {
/*  588: 650 */     cfw.startMethod("exec", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)Ljava/lang/Object;", (short)17);
/*  589:     */     
/*  590:     */ 
/*  591:     */ 
/*  592:     */ 
/*  593:     */ 
/*  594:     */ 
/*  595: 657 */     int CONTEXT_ARG = 1;
/*  596: 658 */     int SCOPE_ARG = 2;
/*  597:     */     
/*  598: 660 */     cfw.addLoadThis();
/*  599: 661 */     cfw.addALoad(1);
/*  600: 662 */     cfw.addALoad(2);
/*  601: 663 */     cfw.add(89);
/*  602: 664 */     cfw.add(1);
/*  603: 665 */     cfw.addInvoke(182, cfw.getClassName(), "call", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;");
/*  604:     */     
/*  605:     */ 
/*  606:     */ 
/*  607:     */ 
/*  608:     */ 
/*  609:     */ 
/*  610:     */ 
/*  611:     */ 
/*  612: 674 */     cfw.add(176);
/*  613:     */     
/*  614: 676 */     cfw.stopMethod((short)3);
/*  615:     */   }
/*  616:     */   
/*  617:     */   private void generateScriptCtor(ClassFileWriter cfw)
/*  618:     */   {
/*  619: 681 */     cfw.startMethod("<init>", "()V", (short)1);
/*  620:     */     
/*  621: 683 */     cfw.addLoadThis();
/*  622: 684 */     cfw.addInvoke(183, "net.sourceforge.htmlunit.corejs.javascript.NativeFunction", "<init>", "()V");
/*  623:     */     
/*  624:     */ 
/*  625: 687 */     cfw.addLoadThis();
/*  626: 688 */     cfw.addPush(0);
/*  627: 689 */     cfw.add(181, cfw.getClassName(), "_id", "I");
/*  628:     */     
/*  629: 691 */     cfw.add(177);
/*  630:     */     
/*  631: 693 */     cfw.stopMethod((short)1);
/*  632:     */   }
/*  633:     */   
/*  634:     */   private void generateFunctionConstructor(ClassFileWriter cfw)
/*  635:     */   {
/*  636: 698 */     int SCOPE_ARG = 1;
/*  637: 699 */     int CONTEXT_ARG = 2;
/*  638: 700 */     int ID_ARG = 3;
/*  639:     */     
/*  640: 702 */     cfw.startMethod("<init>", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Context;I)V", (short)1);
/*  641:     */     
/*  642: 704 */     cfw.addALoad(0);
/*  643: 705 */     cfw.addInvoke(183, "net.sourceforge.htmlunit.corejs.javascript.NativeFunction", "<init>", "()V");
/*  644:     */     
/*  645:     */ 
/*  646: 708 */     cfw.addLoadThis();
/*  647: 709 */     cfw.addILoad(3);
/*  648: 710 */     cfw.add(181, cfw.getClassName(), "_id", "I");
/*  649:     */     
/*  650: 712 */     cfw.addLoadThis();
/*  651: 713 */     cfw.addALoad(2);
/*  652: 714 */     cfw.addALoad(1);
/*  653:     */     
/*  654: 716 */     int start = this.scriptOrFnNodes[0].getType() == 136 ? 1 : 0;
/*  655: 717 */     int end = this.scriptOrFnNodes.length;
/*  656: 718 */     if (start == end) {
/*  657: 718 */       throw badTree();
/*  658:     */     }
/*  659: 719 */     boolean generateSwitch = 2 <= end - start;
/*  660:     */     
/*  661: 721 */     int switchStart = 0;
/*  662: 722 */     int switchStackTop = 0;
/*  663: 723 */     if (generateSwitch)
/*  664:     */     {
/*  665: 724 */       cfw.addILoad(3);
/*  666:     */       
/*  667:     */ 
/*  668: 727 */       switchStart = cfw.addTableSwitch(start + 1, end - 1);
/*  669:     */     }
/*  670: 730 */     for (int i = start; i != end; i++)
/*  671:     */     {
/*  672: 731 */       if (generateSwitch) {
/*  673: 732 */         if (i == start)
/*  674:     */         {
/*  675: 733 */           cfw.markTableSwitchDefault(switchStart);
/*  676: 734 */           switchStackTop = cfw.getStackTop();
/*  677:     */         }
/*  678:     */         else
/*  679:     */         {
/*  680: 736 */           cfw.markTableSwitchCase(switchStart, i - 1 - start, switchStackTop);
/*  681:     */         }
/*  682:     */       }
/*  683: 740 */       OptFunctionNode ofn = OptFunctionNode.get(this.scriptOrFnNodes[i]);
/*  684: 741 */       cfw.addInvoke(183, this.mainClassName, getFunctionInitMethodName(ofn), "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)V");
/*  685:     */       
/*  686:     */ 
/*  687:     */ 
/*  688: 745 */       cfw.add(177);
/*  689:     */     }
/*  690: 749 */     cfw.stopMethod((short)4);
/*  691:     */   }
/*  692:     */   
/*  693:     */   private void generateFunctionInit(ClassFileWriter cfw, OptFunctionNode ofn)
/*  694:     */   {
/*  695: 755 */     int CONTEXT_ARG = 1;
/*  696: 756 */     int SCOPE_ARG = 2;
/*  697: 757 */     cfw.startMethod(getFunctionInitMethodName(ofn), "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)V", (short)18);
/*  698:     */     
/*  699:     */ 
/*  700:     */ 
/*  701:     */ 
/*  702:     */ 
/*  703: 763 */     cfw.addLoadThis();
/*  704: 764 */     cfw.addALoad(1);
/*  705: 765 */     cfw.addALoad(2);
/*  706: 766 */     cfw.addInvoke(182, "net/sourceforge/htmlunit/corejs/javascript/NativeFunction", "initScriptFunction", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)V");
/*  707:     */     
/*  708:     */ 
/*  709:     */ 
/*  710:     */ 
/*  711:     */ 
/*  712:     */ 
/*  713:     */ 
/*  714: 774 */     int regexpCount = ofn.fnode.getRegexpCount();
/*  715: 775 */     if (regexpCount != 0)
/*  716:     */     {
/*  717: 776 */       cfw.addLoadThis();
/*  718: 777 */       pushRegExpArray(cfw, ofn.fnode, 1, 2);
/*  719: 778 */       cfw.add(181, this.mainClassName, "_re", "[Ljava/lang/Object;");
/*  720:     */     }
/*  721: 782 */     cfw.add(177);
/*  722:     */     
/*  723: 784 */     cfw.stopMethod((short)3);
/*  724:     */   }
/*  725:     */   
/*  726:     */   private void generateNativeFunctionOverrides(ClassFileWriter cfw, String encodedSource)
/*  727:     */   {
/*  728: 793 */     cfw.startMethod("getLanguageVersion", "()I", (short)1);
/*  729:     */     
/*  730:     */ 
/*  731: 796 */     cfw.addPush(this.compilerEnv.getLanguageVersion());
/*  732: 797 */     cfw.add(172);
/*  733:     */     
/*  734:     */ 
/*  735: 800 */     cfw.stopMethod((short)1);
/*  736:     */     
/*  737:     */ 
/*  738:     */ 
/*  739:     */ 
/*  740: 805 */     int Do_getFunctionName = 0;
/*  741: 806 */     int Do_getParamCount = 1;
/*  742: 807 */     int Do_getParamAndVarCount = 2;
/*  743: 808 */     int Do_getParamOrVarName = 3;
/*  744: 809 */     int Do_getEncodedSource = 4;
/*  745: 810 */     int Do_getParamOrVarConst = 5;
/*  746: 811 */     int SWITCH_COUNT = 6;
/*  747: 813 */     for (int methodIndex = 0; methodIndex != 6; methodIndex++) {
/*  748: 814 */       if ((methodIndex != 4) || (encodedSource != null))
/*  749:     */       {
/*  750:     */         short methodLocals;
/*  751: 824 */         switch (methodIndex)
/*  752:     */         {
/*  753:     */         case 0: 
/*  754: 826 */           methodLocals = 1;
/*  755: 827 */           cfw.startMethod("getFunctionName", "()Ljava/lang/String;", (short)1);
/*  756:     */           
/*  757: 829 */           break;
/*  758:     */         case 1: 
/*  759: 831 */           methodLocals = 1;
/*  760: 832 */           cfw.startMethod("getParamCount", "()I", (short)1);
/*  761:     */           
/*  762: 834 */           break;
/*  763:     */         case 2: 
/*  764: 836 */           methodLocals = 1;
/*  765: 837 */           cfw.startMethod("getParamAndVarCount", "()I", (short)1);
/*  766:     */           
/*  767: 839 */           break;
/*  768:     */         case 3: 
/*  769: 841 */           methodLocals = 2;
/*  770: 842 */           cfw.startMethod("getParamOrVarName", "(I)Ljava/lang/String;", (short)1);
/*  771:     */           
/*  772: 844 */           break;
/*  773:     */         case 5: 
/*  774: 846 */           methodLocals = 3;
/*  775: 847 */           cfw.startMethod("getParamOrVarConst", "(I)Z", (short)1);
/*  776:     */           
/*  777: 849 */           break;
/*  778:     */         case 4: 
/*  779: 851 */           methodLocals = 1;
/*  780: 852 */           cfw.startMethod("getEncodedSource", "()Ljava/lang/String;", (short)1);
/*  781:     */           
/*  782: 854 */           cfw.addPush(encodedSource);
/*  783: 855 */           break;
/*  784:     */         default: 
/*  785: 857 */           throw Kit.codeBug();
/*  786:     */         }
/*  787: 860 */         int count = this.scriptOrFnNodes.length;
/*  788:     */         
/*  789: 862 */         int switchStart = 0;
/*  790: 863 */         int switchStackTop = 0;
/*  791: 864 */         if (count > 1)
/*  792:     */         {
/*  793: 867 */           cfw.addLoadThis();
/*  794: 868 */           cfw.add(180, cfw.getClassName(), "_id", "I");
/*  795:     */           
/*  796:     */ 
/*  797:     */ 
/*  798: 872 */           switchStart = cfw.addTableSwitch(1, count - 1);
/*  799:     */         }
/*  800: 875 */         for (int i = 0; i != count; i++)
/*  801:     */         {
/*  802: 876 */           ScriptNode n = this.scriptOrFnNodes[i];
/*  803: 877 */           if (i == 0)
/*  804:     */           {
/*  805: 878 */             if (count > 1)
/*  806:     */             {
/*  807: 879 */               cfw.markTableSwitchDefault(switchStart);
/*  808: 880 */               switchStackTop = cfw.getStackTop();
/*  809:     */             }
/*  810:     */           }
/*  811:     */           else {
/*  812: 883 */             cfw.markTableSwitchCase(switchStart, i - 1, switchStackTop);
/*  813:     */           }
/*  814:     */           int paramAndVarCount;
/*  815: 888 */           switch (methodIndex)
/*  816:     */           {
/*  817:     */           case 0: 
/*  818: 891 */             if (n.getType() == 136)
/*  819:     */             {
/*  820: 892 */               cfw.addPush("");
/*  821:     */             }
/*  822:     */             else
/*  823:     */             {
/*  824: 894 */               String name = ((FunctionNode)n).getName();
/*  825: 895 */               cfw.addPush(name);
/*  826:     */             }
/*  827: 897 */             cfw.add(176);
/*  828: 898 */             break;
/*  829:     */           case 1: 
/*  830: 902 */             cfw.addPush(n.getParamCount());
/*  831: 903 */             cfw.add(172);
/*  832: 904 */             break;
/*  833:     */           case 2: 
/*  834: 908 */             cfw.addPush(n.getParamAndVarCount());
/*  835: 909 */             cfw.add(172);
/*  836: 910 */             break;
/*  837:     */           case 3: 
/*  838: 915 */             paramAndVarCount = n.getParamAndVarCount();
/*  839: 916 */             if (paramAndVarCount == 0)
/*  840:     */             {
/*  841: 920 */               cfw.add(1);
/*  842: 921 */               cfw.add(176);
/*  843:     */             }
/*  844: 922 */             else if (paramAndVarCount == 1)
/*  845:     */             {
/*  846: 925 */               cfw.addPush(n.getParamOrVarName(0));
/*  847: 926 */               cfw.add(176);
/*  848:     */             }
/*  849:     */             else
/*  850:     */             {
/*  851: 929 */               cfw.addILoad(1);
/*  852:     */               
/*  853:     */ 
/*  854: 932 */               int paramSwitchStart = cfw.addTableSwitch(1, paramAndVarCount - 1);
/*  855: 934 */               for (int j = 0; j != paramAndVarCount; j++)
/*  856:     */               {
/*  857: 935 */                 if (cfw.getStackTop() != 0) {
/*  858: 935 */                   Kit.codeBug();
/*  859:     */                 }
/*  860: 936 */                 String s = n.getParamOrVarName(j);
/*  861: 937 */                 if (j == 0) {
/*  862: 938 */                   cfw.markTableSwitchDefault(paramSwitchStart);
/*  863:     */                 } else {
/*  864: 940 */                   cfw.markTableSwitchCase(paramSwitchStart, j - 1, 0);
/*  865:     */                 }
/*  866: 943 */                 cfw.addPush(s);
/*  867: 944 */                 cfw.add(176);
/*  868:     */               }
/*  869:     */             }
/*  870: 947 */             break;
/*  871:     */           case 5: 
/*  872: 952 */             paramAndVarCount = n.getParamAndVarCount();
/*  873: 953 */             boolean[] constness = n.getParamAndVarConst();
/*  874: 954 */             if (paramAndVarCount == 0)
/*  875:     */             {
/*  876: 958 */               cfw.add(3);
/*  877: 959 */               cfw.add(172);
/*  878:     */             }
/*  879: 960 */             else if (paramAndVarCount == 1)
/*  880:     */             {
/*  881: 963 */               cfw.addPush(constness[0]);
/*  882: 964 */               cfw.add(172);
/*  883:     */             }
/*  884:     */             else
/*  885:     */             {
/*  886: 967 */               cfw.addILoad(1);
/*  887:     */               
/*  888:     */ 
/*  889: 970 */               int paramSwitchStart = cfw.addTableSwitch(1, paramAndVarCount - 1);
/*  890: 972 */               for (int j = 0; j != paramAndVarCount; j++)
/*  891:     */               {
/*  892: 973 */                 if (cfw.getStackTop() != 0) {
/*  893: 973 */                   Kit.codeBug();
/*  894:     */                 }
/*  895: 974 */                 if (j == 0) {
/*  896: 975 */                   cfw.markTableSwitchDefault(paramSwitchStart);
/*  897:     */                 } else {
/*  898: 977 */                   cfw.markTableSwitchCase(paramSwitchStart, j - 1, 0);
/*  899:     */                 }
/*  900: 980 */                 cfw.addPush(constness[j]);
/*  901: 981 */                 cfw.add(172);
/*  902:     */               }
/*  903:     */             }
/*  904: 984 */             break;
/*  905:     */           case 4: 
/*  906: 989 */             cfw.addPush(n.getEncodedSourceStart());
/*  907: 990 */             cfw.addPush(n.getEncodedSourceEnd());
/*  908: 991 */             cfw.addInvoke(182, "java/lang/String", "substring", "(II)Ljava/lang/String;");
/*  909:     */             
/*  910:     */ 
/*  911:     */ 
/*  912: 995 */             cfw.add(176);
/*  913: 996 */             break;
/*  914:     */           default: 
/*  915: 999 */             throw Kit.codeBug();
/*  916:     */           }
/*  917:     */         }
/*  918:1003 */         cfw.stopMethod(methodLocals);
/*  919:     */       }
/*  920:     */     }
/*  921:     */   }
/*  922:     */   
/*  923:     */   private void emitRegExpInit(ClassFileWriter cfw)
/*  924:     */   {
/*  925:1011 */     int totalRegCount = 0;
/*  926:1012 */     for (int i = 0; i != this.scriptOrFnNodes.length; i++) {
/*  927:1013 */       totalRegCount += this.scriptOrFnNodes[i].getRegexpCount();
/*  928:     */     }
/*  929:1015 */     if (totalRegCount == 0) {
/*  930:1016 */       return;
/*  931:     */     }
/*  932:1019 */     cfw.startMethod("_reInit", "(Lnet/sourceforge/htmlunit/corejs/javascript/RegExpProxy;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)V", (short)42);
/*  933:     */     
/*  934:     */ 
/*  935:1022 */     cfw.addField("_reInitDone", "Z", (short)10);
/*  936:     */     
/*  937:     */ 
/*  938:1025 */     cfw.add(178, this.mainClassName, "_reInitDone", "Z");
/*  939:1026 */     int doInit = cfw.acquireLabel();
/*  940:1027 */     cfw.add(153, doInit);
/*  941:1028 */     cfw.add(177);
/*  942:1029 */     cfw.markLabel(doInit);
/*  943:1031 */     for (int i = 0; i != this.scriptOrFnNodes.length; i++)
/*  944:     */     {
/*  945:1032 */       ScriptNode n = this.scriptOrFnNodes[i];
/*  946:1033 */       int regCount = n.getRegexpCount();
/*  947:1034 */       for (int j = 0; j != regCount; j++)
/*  948:     */       {
/*  949:1035 */         String reFieldName = getCompiledRegexpName(n, j);
/*  950:1036 */         String reFieldType = "Ljava/lang/Object;";
/*  951:1037 */         String reString = n.getRegexpString(j);
/*  952:1038 */         String reFlags = n.getRegexpFlags(j);
/*  953:1039 */         cfw.addField(reFieldName, reFieldType, (short)10);
/*  954:     */         
/*  955:     */ 
/*  956:1042 */         cfw.addALoad(0);
/*  957:1043 */         cfw.addALoad(1);
/*  958:1044 */         cfw.addPush(reString);
/*  959:1045 */         if (reFlags == null) {
/*  960:1046 */           cfw.add(1);
/*  961:     */         } else {
/*  962:1048 */           cfw.addPush(reFlags);
/*  963:     */         }
/*  964:1050 */         cfw.addInvoke(185, "net/sourceforge/htmlunit/corejs/javascript/RegExpProxy", "compileRegExp", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
/*  965:     */         
/*  966:     */ 
/*  967:     */ 
/*  968:     */ 
/*  969:     */ 
/*  970:1056 */         cfw.add(179, this.mainClassName, reFieldName, reFieldType);
/*  971:     */       }
/*  972:     */     }
/*  973:1061 */     cfw.addPush(1);
/*  974:1062 */     cfw.add(179, this.mainClassName, "_reInitDone", "Z");
/*  975:1063 */     cfw.add(177);
/*  976:1064 */     cfw.stopMethod((short)2);
/*  977:     */   }
/*  978:     */   
/*  979:     */   private void emitConstantDudeInitializers(ClassFileWriter cfw)
/*  980:     */   {
/*  981:1069 */     int N = this.itsConstantListSize;
/*  982:1070 */     if (N == 0) {
/*  983:1071 */       return;
/*  984:     */     }
/*  985:1073 */     cfw.startMethod("<clinit>", "()V", (short)24);
/*  986:     */     
/*  987:     */ 
/*  988:1076 */     double[] array = this.itsConstantList;
/*  989:1077 */     for (int i = 0; i != N; i++)
/*  990:     */     {
/*  991:1078 */       double num = array[i];
/*  992:1079 */       String constantName = "_k" + i;
/*  993:1080 */       String constantType = getStaticConstantWrapperType(num);
/*  994:1081 */       cfw.addField(constantName, constantType, (short)10);
/*  995:     */       
/*  996:     */ 
/*  997:1084 */       int inum = (int)num;
/*  998:1085 */       if (inum == num)
/*  999:     */       {
/* 1000:1086 */         cfw.add(187, "java/lang/Integer");
/* 1001:1087 */         cfw.add(89);
/* 1002:1088 */         cfw.addPush(inum);
/* 1003:1089 */         cfw.addInvoke(183, "java/lang/Integer", "<init>", "(I)V");
/* 1004:     */       }
/* 1005:     */       else
/* 1006:     */       {
/* 1007:1092 */         cfw.addPush(num);
/* 1008:1093 */         addDoubleWrap(cfw);
/* 1009:     */       }
/* 1010:1095 */       cfw.add(179, this.mainClassName, constantName, constantType);
/* 1011:     */     }
/* 1012:1099 */     cfw.add(177);
/* 1013:1100 */     cfw.stopMethod((short)0);
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   void pushRegExpArray(ClassFileWriter cfw, ScriptNode n, int contextArg, int scopeArg)
/* 1017:     */   {
/* 1018:1106 */     int regexpCount = n.getRegexpCount();
/* 1019:1107 */     if (regexpCount == 0) {
/* 1020:1107 */       throw badTree();
/* 1021:     */     }
/* 1022:1109 */     cfw.addPush(regexpCount);
/* 1023:1110 */     cfw.add(189, "java/lang/Object");
/* 1024:     */     
/* 1025:1112 */     cfw.addALoad(contextArg);
/* 1026:1113 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/ScriptRuntime", "checkRegExpProxy", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;)Lnet/sourceforge/htmlunit/corejs/javascript/RegExpProxy;");
/* 1027:     */     
/* 1028:     */ 
/* 1029:     */ 
/* 1030:     */ 
/* 1031:     */ 
/* 1032:1119 */     cfw.add(89);
/* 1033:1120 */     cfw.addALoad(contextArg);
/* 1034:1121 */     cfw.addInvoke(184, this.mainClassName, "_reInit", "(Lnet/sourceforge/htmlunit/corejs/javascript/RegExpProxy;Lnet/sourceforge/htmlunit/corejs/javascript/Context;)V");
/* 1035:1123 */     for (int i = 0; i != regexpCount; i++)
/* 1036:     */     {
/* 1037:1125 */       cfw.add(92);
/* 1038:1126 */       cfw.addALoad(contextArg);
/* 1039:1127 */       cfw.addALoad(scopeArg);
/* 1040:1128 */       cfw.add(178, this.mainClassName, getCompiledRegexpName(n, i), "Ljava/lang/Object;");
/* 1041:     */       
/* 1042:     */ 
/* 1043:1131 */       cfw.addInvoke(185, "net/sourceforge/htmlunit/corejs/javascript/RegExpProxy", "wrapRegExp", "(Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/Object;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 1044:     */       
/* 1045:     */ 
/* 1046:     */ 
/* 1047:     */ 
/* 1048:     */ 
/* 1049:     */ 
/* 1050:     */ 
/* 1051:1139 */       cfw.addPush(i);
/* 1052:1140 */       cfw.add(95);
/* 1053:1141 */       cfw.add(83);
/* 1054:     */     }
/* 1055:1145 */     cfw.add(87);
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   void pushNumberAsObject(ClassFileWriter cfw, double num)
/* 1059:     */   {
/* 1060:1150 */     if (num == 0.0D)
/* 1061:     */     {
/* 1062:1151 */       if (1.0D / num > 0.0D)
/* 1063:     */       {
/* 1064:1153 */         cfw.add(178, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime", "zeroObj", "Ljava/lang/Double;");
/* 1065:     */       }
/* 1066:     */       else
/* 1067:     */       {
/* 1068:1157 */         cfw.addPush(num);
/* 1069:1158 */         addDoubleWrap(cfw);
/* 1070:     */       }
/* 1071:     */     }
/* 1072:     */     else
/* 1073:     */     {
/* 1074:1161 */       if (num == 1.0D)
/* 1075:     */       {
/* 1076:1162 */         cfw.add(178, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime", "oneObj", "Ljava/lang/Double;");
/* 1077:     */         
/* 1078:     */ 
/* 1079:1165 */         return;
/* 1080:     */       }
/* 1081:1167 */       if (num == -1.0D)
/* 1082:     */       {
/* 1083:1168 */         cfw.add(178, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime", "minusOneObj", "Ljava/lang/Double;");
/* 1084:     */       }
/* 1085:1172 */       else if (num != num)
/* 1086:     */       {
/* 1087:1173 */         cfw.add(178, "net/sourceforge/htmlunit/corejs/javascript/ScriptRuntime", "NaNobj", "Ljava/lang/Double;");
/* 1088:     */       }
/* 1089:1177 */       else if (this.itsConstantListSize >= 2000)
/* 1090:     */       {
/* 1091:1182 */         cfw.addPush(num);
/* 1092:1183 */         addDoubleWrap(cfw);
/* 1093:     */       }
/* 1094:     */       else
/* 1095:     */       {
/* 1096:1186 */         int N = this.itsConstantListSize;
/* 1097:1187 */         int index = 0;
/* 1098:1188 */         if (N == 0)
/* 1099:     */         {
/* 1100:1189 */           this.itsConstantList = new double[64];
/* 1101:     */         }
/* 1102:     */         else
/* 1103:     */         {
/* 1104:1191 */           double[] array = this.itsConstantList;
/* 1105:1192 */           while ((index != N) && (array[index] != num)) {
/* 1106:1193 */             index++;
/* 1107:     */           }
/* 1108:1195 */           if (N == array.length)
/* 1109:     */           {
/* 1110:1196 */             array = new double[N * 2];
/* 1111:1197 */             System.arraycopy(this.itsConstantList, 0, array, 0, N);
/* 1112:1198 */             this.itsConstantList = array;
/* 1113:     */           }
/* 1114:     */         }
/* 1115:1201 */         if (index == N)
/* 1116:     */         {
/* 1117:1202 */           this.itsConstantList[N] = num;
/* 1118:1203 */           this.itsConstantListSize = (N + 1);
/* 1119:     */         }
/* 1120:1205 */         String constantName = "_k" + index;
/* 1121:1206 */         String constantType = getStaticConstantWrapperType(num);
/* 1122:1207 */         cfw.add(178, this.mainClassName, constantName, constantType);
/* 1123:     */       }
/* 1124:     */     }
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   private static void addDoubleWrap(ClassFileWriter cfw)
/* 1128:     */   {
/* 1129:1214 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/optimizer/OptRuntime", "wrapDouble", "(D)Ljava/lang/Double;");
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   private static String getStaticConstantWrapperType(double num)
/* 1133:     */   {
/* 1134:1221 */     int inum = (int)num;
/* 1135:1222 */     if (inum == num) {
/* 1136:1223 */       return "Ljava/lang/Integer;";
/* 1137:     */     }
/* 1138:1225 */     return "Ljava/lang/Double;";
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   static void pushUndefined(ClassFileWriter cfw)
/* 1142:     */   {
/* 1143:1230 */     cfw.add(178, "net/sourceforge/htmlunit/corejs/javascript/Undefined", "instance", "Ljava/lang/Object;");
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   int getIndex(ScriptNode n)
/* 1147:     */   {
/* 1148:1236 */     return this.scriptOrFnIndexes.getExisting(n);
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   static String getDirectTargetFieldName(int i)
/* 1152:     */   {
/* 1153:1241 */     return "_dt" + i;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   String getDirectCtorName(ScriptNode n)
/* 1157:     */   {
/* 1158:1246 */     return "_n" + getIndex(n);
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   String getBodyMethodName(ScriptNode n)
/* 1162:     */   {
/* 1163:1251 */     return "_c_" + cleanName(n) + "_" + getIndex(n);
/* 1164:     */   }
/* 1165:     */   
/* 1166:     */   String cleanName(ScriptNode n)
/* 1167:     */   {
/* 1168:1259 */     String result = "";
/* 1169:1260 */     if ((n instanceof FunctionNode))
/* 1170:     */     {
/* 1171:1261 */       Name name = ((FunctionNode)n).getFunctionName();
/* 1172:1262 */       if (name == null) {
/* 1173:1263 */         result = "anonymous";
/* 1174:     */       } else {
/* 1175:1265 */         result = name.getIdentifier();
/* 1176:     */       }
/* 1177:     */     }
/* 1178:     */     else
/* 1179:     */     {
/* 1180:1268 */       result = "script";
/* 1181:     */     }
/* 1182:1270 */     return result;
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   String getBodyMethodSignature(ScriptNode n)
/* 1186:     */   {
/* 1187:1275 */     StringBuffer sb = new StringBuffer();
/* 1188:1276 */     sb.append('(');
/* 1189:1277 */     sb.append(this.mainClassSignature);
/* 1190:1278 */     sb.append("Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/* 1191:1281 */     if (n.getType() == 109)
/* 1192:     */     {
/* 1193:1282 */       OptFunctionNode ofn = OptFunctionNode.get(n);
/* 1194:1283 */       if (ofn.isTargetOfDirectCall())
/* 1195:     */       {
/* 1196:1284 */         int pCount = ofn.fnode.getParamCount();
/* 1197:1285 */         for (int i = 0; i != pCount; i++) {
/* 1198:1286 */           sb.append("Ljava/lang/Object;D");
/* 1199:     */         }
/* 1200:     */       }
/* 1201:     */     }
/* 1202:1290 */     sb.append("[Ljava/lang/Object;)Ljava/lang/Object;");
/* 1203:1291 */     return sb.toString();
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   String getFunctionInitMethodName(OptFunctionNode ofn)
/* 1207:     */   {
/* 1208:1296 */     return "_i" + getIndex(ofn.fnode);
/* 1209:     */   }
/* 1210:     */   
/* 1211:     */   String getCompiledRegexpName(ScriptNode n, int regexpIndex)
/* 1212:     */   {
/* 1213:1301 */     return "_re" + getIndex(n) + "_" + regexpIndex;
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   static RuntimeException badTree()
/* 1217:     */   {
/* 1218:1306 */     throw new RuntimeException("Bad tree in codegen");
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   void setMainMethodClass(String className)
/* 1222:     */   {
/* 1223:1311 */     this.mainMethodClass = className;
/* 1224:     */   }
/* 1225:     */   
/* 1226:1340 */   private static final Object globalLock = new Object();
/* 1227:     */   private static int globalSerialClassCounter;
/* 1228:     */   private CompilerEnvirons compilerEnv;
/* 1229:     */   private ObjArray directCallTargets;
/* 1230:     */   ScriptNode[] scriptOrFnNodes;
/* 1231:     */   private ObjToIntMap scriptOrFnIndexes;
/* 1232:1349 */   private String mainMethodClass = "net.sourceforge.htmlunit.corejs.javascript.optimizer.OptRuntime";
/* 1233:     */   String mainClassName;
/* 1234:     */   String mainClassSignature;
/* 1235:     */   private double[] itsConstantList;
/* 1236:     */   private int itsConstantListSize;
/* 1237:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.Codegen
 * JD-Core Version:    0.7.0.1
 */