/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.ObjectOutputStream;
/*    6:     */ import java.lang.reflect.Constructor;
/*    7:     */ import java.lang.reflect.Field;
/*    8:     */ import java.lang.reflect.InvocationTargetException;
/*    9:     */ import java.lang.reflect.Method;
/*   10:     */ import java.lang.reflect.Modifier;
/*   11:     */ import java.security.CodeSource;
/*   12:     */ import java.security.ProtectionDomain;
/*   13:     */ import java.util.ArrayList;
/*   14:     */ import java.util.Arrays;
/*   15:     */ import java.util.HashSet;
/*   16:     */ import java.util.Map;
/*   17:     */ import net.sourceforge.htmlunit.corejs.classfile.ClassFileWriter;
/*   18:     */ 
/*   19:     */ public final class JavaAdapter
/*   20:     */   implements IdFunctionCall
/*   21:     */ {
/*   22:     */   static class JavaAdapterSignature
/*   23:     */   {
/*   24:     */     Class<?> superClass;
/*   25:     */     Class<?>[] interfaces;
/*   26:     */     ObjToIntMap names;
/*   27:     */     
/*   28:     */     JavaAdapterSignature(Class<?> superClass, Class<?>[] interfaces, ObjToIntMap names)
/*   29:     */     {
/*   30:  69 */       this.superClass = superClass;
/*   31:  70 */       this.interfaces = interfaces;
/*   32:  71 */       this.names = names;
/*   33:     */     }
/*   34:     */     
/*   35:     */     public boolean equals(Object obj)
/*   36:     */     {
/*   37:  77 */       if (!(obj instanceof JavaAdapterSignature)) {
/*   38:  78 */         return false;
/*   39:     */       }
/*   40:  79 */       JavaAdapterSignature sig = (JavaAdapterSignature)obj;
/*   41:  80 */       if (this.superClass != sig.superClass) {
/*   42:  81 */         return false;
/*   43:     */       }
/*   44:  82 */       if (this.interfaces != sig.interfaces)
/*   45:     */       {
/*   46:  83 */         if (this.interfaces.length != sig.interfaces.length) {
/*   47:  84 */           return false;
/*   48:     */         }
/*   49:  85 */         for (int i = 0; i < this.interfaces.length; i++) {
/*   50:  86 */           if (this.interfaces[i] != sig.interfaces[i]) {
/*   51:  87 */             return false;
/*   52:     */           }
/*   53:     */         }
/*   54:     */       }
/*   55:  89 */       if (this.names.size() != sig.names.size()) {
/*   56:  90 */         return false;
/*   57:     */       }
/*   58:  91 */       ObjToIntMap.Iterator iter = new ObjToIntMap.Iterator(this.names);
/*   59:  92 */       for (iter.start(); !iter.done(); iter.next())
/*   60:     */       {
/*   61:  93 */         String name = (String)iter.getKey();
/*   62:  94 */         int arity = iter.getValue();
/*   63:  95 */         if (arity != sig.names.get(name, arity + 1)) {
/*   64:  96 */           return false;
/*   65:     */         }
/*   66:     */       }
/*   67:  98 */       return true;
/*   68:     */     }
/*   69:     */     
/*   70:     */     public int hashCode()
/*   71:     */     {
/*   72: 104 */       return this.superClass.hashCode() + Arrays.hashCode(this.interfaces) ^ this.names.size();
/*   73:     */     }
/*   74:     */   }
/*   75:     */   
/*   76:     */   public static void init(Context cx, Scriptable scope, boolean sealed)
/*   77:     */   {
/*   78: 110 */     JavaAdapter obj = new JavaAdapter();
/*   79: 111 */     IdFunctionObject ctor = new IdFunctionObject(obj, FTAG, 1, "JavaAdapter", 1, scope);
/*   80:     */     
/*   81: 113 */     ctor.markAsConstructor(null);
/*   82: 114 */     if (sealed) {
/*   83: 115 */       ctor.sealObject();
/*   84:     */     }
/*   85: 117 */     ctor.exportAsScopeProperty();
/*   86:     */   }
/*   87:     */   
/*   88:     */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*   89:     */   {
/*   90: 123 */     if ((f.hasTag(FTAG)) && 
/*   91: 124 */       (f.methodId() == 1)) {
/*   92: 125 */       return js_createAdapter(cx, scope, args);
/*   93:     */     }
/*   94: 128 */     throw f.unknown();
/*   95:     */   }
/*   96:     */   
/*   97:     */   public static Object convertResult(Object result, Class<?> c)
/*   98:     */   {
/*   99: 133 */     if ((result == Undefined.instance) && (c != ScriptRuntime.ObjectClass) && (c != ScriptRuntime.StringClass)) {
/*  100: 138 */       return null;
/*  101:     */     }
/*  102: 140 */     return Context.jsToJava(result, c);
/*  103:     */   }
/*  104:     */   
/*  105:     */   public static Scriptable createAdapterWrapper(Scriptable obj, Object adapter)
/*  106:     */   {
/*  107: 146 */     Scriptable scope = ScriptableObject.getTopLevelScope(obj);
/*  108: 147 */     NativeJavaObject res = new NativeJavaObject(scope, adapter, null, true);
/*  109: 148 */     res.setPrototype(obj);
/*  110: 149 */     return res;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public static Object getAdapterSelf(Class<?> adapterClass, Object adapter)
/*  114:     */     throws NoSuchFieldException, IllegalAccessException
/*  115:     */   {
/*  116: 155 */     Field self = adapterClass.getDeclaredField("self");
/*  117: 156 */     return self.get(adapter);
/*  118:     */   }
/*  119:     */   
/*  120:     */   static Object js_createAdapter(Context cx, Scriptable scope, Object[] args)
/*  121:     */   {
/*  122: 161 */     int N = args.length;
/*  123: 162 */     if (N == 0) {
/*  124: 163 */       throw ScriptRuntime.typeError0("msg.adapter.zero.args");
/*  125:     */     }
/*  126: 166 */     Class<?> superClass = null;
/*  127: 167 */     Class<?>[] intfs = new Class[N - 1];
/*  128: 168 */     int interfaceCount = 0;
/*  129: 169 */     for (int i = 0; i != N - 1; i++)
/*  130:     */     {
/*  131: 170 */       Object arg = args[i];
/*  132: 171 */       if (!(arg instanceof NativeJavaClass)) {
/*  133: 172 */         throw ScriptRuntime.typeError2("msg.not.java.class.arg", String.valueOf(i), ScriptRuntime.toString(arg));
/*  134:     */       }
/*  135: 176 */       Class<?> c = ((NativeJavaClass)arg).getClassObject();
/*  136: 177 */       if (!c.isInterface())
/*  137:     */       {
/*  138: 178 */         if (superClass != null) {
/*  139: 179 */           throw ScriptRuntime.typeError2("msg.only.one.super", superClass.getName(), c.getName());
/*  140:     */         }
/*  141: 182 */         superClass = c;
/*  142:     */       }
/*  143:     */       else
/*  144:     */       {
/*  145: 184 */         intfs[(interfaceCount++)] = c;
/*  146:     */       }
/*  147:     */     }
/*  148: 188 */     if (superClass == null) {
/*  149: 189 */       superClass = ScriptRuntime.ObjectClass;
/*  150:     */     }
/*  151: 191 */     Class<?>[] interfaces = new Class[interfaceCount];
/*  152: 192 */     System.arraycopy(intfs, 0, interfaces, 0, interfaceCount);
/*  153: 193 */     Scriptable obj = ScriptRuntime.toObject(cx, scope, args[(N - 1)]);
/*  154:     */     
/*  155: 195 */     Class<?> adapterClass = getAdapterClass(scope, superClass, interfaces, obj);
/*  156:     */     
/*  157:     */ 
/*  158: 198 */     Class<?>[] ctorParms = { ScriptRuntime.ContextFactoryClass, ScriptRuntime.ScriptableClass };
/*  159:     */     
/*  160:     */ 
/*  161:     */ 
/*  162: 202 */     Object[] ctorArgs = { cx.getFactory(), obj };
/*  163:     */     try
/*  164:     */     {
/*  165: 204 */       Object adapter = adapterClass.getConstructor(ctorParms).newInstance(ctorArgs);
/*  166:     */       
/*  167: 206 */       Object self = getAdapterSelf(adapterClass, adapter);
/*  168: 208 */       if ((self instanceof Wrapper))
/*  169:     */       {
/*  170: 209 */         Object unwrapped = ((Wrapper)self).unwrap();
/*  171: 210 */         if ((unwrapped instanceof Scriptable))
/*  172:     */         {
/*  173: 211 */           if ((unwrapped instanceof ScriptableObject)) {
/*  174: 212 */             ScriptRuntime.setObjectProtoAndParent((ScriptableObject)unwrapped, scope);
/*  175:     */           }
/*  176: 215 */           return unwrapped;
/*  177:     */         }
/*  178:     */       }
/*  179: 218 */       return self;
/*  180:     */     }
/*  181:     */     catch (Exception ex)
/*  182:     */     {
/*  183: 220 */       throw Context.throwAsScriptRuntimeEx(ex);
/*  184:     */     }
/*  185:     */   }
/*  186:     */   
/*  187:     */   public static void writeAdapterObject(Object javaObject, ObjectOutputStream out)
/*  188:     */     throws IOException
/*  189:     */   {
/*  190: 229 */     Class<?> cl = javaObject.getClass();
/*  191: 230 */     out.writeObject(cl.getSuperclass().getName());
/*  192:     */     
/*  193: 232 */     Class<?>[] interfaces = cl.getInterfaces();
/*  194: 233 */     String[] interfaceNames = new String[interfaces.length];
/*  195: 235 */     for (int i = 0; i < interfaces.length; i++) {
/*  196: 236 */       interfaceNames[i] = interfaces[i].getName();
/*  197:     */     }
/*  198: 238 */     out.writeObject(interfaceNames);
/*  199:     */     try
/*  200:     */     {
/*  201: 241 */       Object delegee = cl.getField("delegee").get(javaObject);
/*  202: 242 */       out.writeObject(delegee);
/*  203: 243 */       return;
/*  204:     */     }
/*  205:     */     catch (IllegalAccessException e) {}catch (NoSuchFieldException e) {}
/*  206: 247 */     throw new IOException();
/*  207:     */   }
/*  208:     */   
/*  209:     */   public static Object readAdapterObject(Scriptable self, ObjectInputStream in)
/*  210:     */     throws IOException, ClassNotFoundException
/*  211:     */   {
/*  212: 256 */     Context cx = Context.getCurrentContext();
/*  213:     */     ContextFactory factory;
/*  214:     */     ContextFactory factory;
/*  215: 257 */     if (cx != null) {
/*  216: 258 */       factory = cx.getFactory();
/*  217:     */     } else {
/*  218: 260 */       factory = null;
/*  219:     */     }
/*  220: 263 */     Class<?> superClass = Class.forName((String)in.readObject());
/*  221:     */     
/*  222: 265 */     String[] interfaceNames = (String[])in.readObject();
/*  223: 266 */     Class<?>[] interfaces = new Class[interfaceNames.length];
/*  224: 268 */     for (int i = 0; i < interfaceNames.length; i++) {
/*  225: 269 */       interfaces[i] = Class.forName(interfaceNames[i]);
/*  226:     */     }
/*  227: 271 */     Scriptable delegee = (Scriptable)in.readObject();
/*  228:     */     
/*  229: 273 */     Class<?> adapterClass = getAdapterClass(self, superClass, interfaces, delegee);
/*  230:     */     
/*  231:     */ 
/*  232: 276 */     Class<?>[] ctorParms = { ScriptRuntime.ContextFactoryClass, ScriptRuntime.ScriptableClass, ScriptRuntime.ScriptableClass };
/*  233:     */     
/*  234:     */ 
/*  235:     */ 
/*  236:     */ 
/*  237: 281 */     Object[] ctorArgs = { factory, delegee, self };
/*  238:     */     try
/*  239:     */     {
/*  240: 283 */       return adapterClass.getConstructor(ctorParms).newInstance(ctorArgs);
/*  241:     */     }
/*  242:     */     catch (InstantiationException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}catch (NoSuchMethodException e) {}
/*  243: 290 */     throw new ClassNotFoundException("adapter");
/*  244:     */   }
/*  245:     */   
/*  246:     */   private static ObjToIntMap getObjectFunctionNames(Scriptable obj)
/*  247:     */   {
/*  248: 295 */     Object[] ids = ScriptableObject.getPropertyIds(obj);
/*  249: 296 */     ObjToIntMap map = new ObjToIntMap(ids.length);
/*  250: 297 */     for (int i = 0; i != ids.length; i++) {
/*  251: 298 */       if ((ids[i] instanceof String))
/*  252:     */       {
/*  253: 300 */         String id = (String)ids[i];
/*  254: 301 */         Object value = ScriptableObject.getProperty(obj, id);
/*  255: 302 */         if ((value instanceof Function))
/*  256:     */         {
/*  257: 303 */           Function f = (Function)value;
/*  258: 304 */           int length = ScriptRuntime.toInt32(ScriptableObject.getProperty(f, "length"));
/*  259: 306 */           if (length < 0) {
/*  260: 307 */             length = 0;
/*  261:     */           }
/*  262: 309 */           map.put(id, length);
/*  263:     */         }
/*  264:     */       }
/*  265:     */     }
/*  266: 312 */     return map;
/*  267:     */   }
/*  268:     */   
/*  269:     */   private static Class<?> getAdapterClass(Scriptable scope, Class<?> superClass, Class<?>[] interfaces, Scriptable obj)
/*  270:     */   {
/*  271: 318 */     ClassCache cache = ClassCache.get(scope);
/*  272: 319 */     Map<JavaAdapterSignature, Class<?>> generated = cache.getInterfaceAdapterCacheMap();
/*  273:     */     
/*  274:     */ 
/*  275: 322 */     ObjToIntMap names = getObjectFunctionNames(obj);
/*  276:     */     
/*  277: 324 */     JavaAdapterSignature sig = new JavaAdapterSignature(superClass, interfaces, names);
/*  278: 325 */     Class<?> adapterClass = (Class)generated.get(sig);
/*  279: 326 */     if (adapterClass == null)
/*  280:     */     {
/*  281: 327 */       String adapterName = "adapter" + cache.newClassSerialNumber();
/*  282:     */       
/*  283: 329 */       byte[] code = createAdapterCode(names, adapterName, superClass, interfaces, null);
/*  284:     */       
/*  285:     */ 
/*  286: 332 */       adapterClass = loadAdapterClass(adapterName, code);
/*  287: 333 */       if (cache.isCachingEnabled()) {
/*  288: 334 */         generated.put(sig, adapterClass);
/*  289:     */       }
/*  290:     */     }
/*  291: 337 */     return adapterClass;
/*  292:     */   }
/*  293:     */   
/*  294:     */   public static byte[] createAdapterCode(ObjToIntMap functionNames, String adapterName, Class<?> superClass, Class<?>[] interfaces, String scriptClassName)
/*  295:     */   {
/*  296: 346 */     ClassFileWriter cfw = new ClassFileWriter(adapterName, superClass.getName(), "<adapter>");
/*  297:     */     
/*  298:     */ 
/*  299: 349 */     cfw.addField("factory", "Lnet/sourceforge/htmlunit/corejs/javascript/ContextFactory;", (short)17);
/*  300:     */     
/*  301:     */ 
/*  302: 352 */     cfw.addField("delegee", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;", (short)17);
/*  303:     */     
/*  304:     */ 
/*  305: 355 */     cfw.addField("self", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;", (short)17);
/*  306:     */     
/*  307:     */ 
/*  308: 358 */     int interfacesCount = interfaces == null ? 0 : interfaces.length;
/*  309: 359 */     for (int i = 0; i < interfacesCount; i++) {
/*  310: 360 */       if (interfaces[i] != null) {
/*  311: 361 */         cfw.addInterface(interfaces[i].getName());
/*  312:     */       }
/*  313:     */     }
/*  314: 364 */     String superName = superClass.getName().replace('.', '/');
/*  315: 365 */     generateCtor(cfw, adapterName, superName);
/*  316: 366 */     generateSerialCtor(cfw, adapterName, superName);
/*  317: 367 */     if (scriptClassName != null) {
/*  318: 368 */       generateEmptyCtor(cfw, adapterName, superName, scriptClassName);
/*  319:     */     }
/*  320: 370 */     ObjToIntMap generatedOverrides = new ObjToIntMap();
/*  321: 371 */     ObjToIntMap generatedMethods = new ObjToIntMap();
/*  322: 374 */     for (int i = 0; i < interfacesCount; i++)
/*  323:     */     {
/*  324: 375 */       Method[] methods = interfaces[i].getMethods();
/*  325: 376 */       for (int j = 0; j < methods.length; j++)
/*  326:     */       {
/*  327: 377 */         Method method = methods[j];
/*  328: 378 */         int mods = method.getModifiers();
/*  329: 379 */         if ((!Modifier.isStatic(mods)) && (!Modifier.isFinal(mods)))
/*  330:     */         {
/*  331: 382 */           String methodName = method.getName();
/*  332: 383 */           Class<?>[] argTypes = method.getParameterTypes();
/*  333: 384 */           if (!functionNames.has(methodName))
/*  334:     */           {
/*  335:     */             try
/*  336:     */             {
/*  337: 386 */               superClass.getMethod(methodName, argTypes);
/*  338:     */             }
/*  339:     */             catch (NoSuchMethodException e) {}
/*  340:     */           }
/*  341:     */           else
/*  342:     */           {
/*  343: 397 */             String methodSignature = getMethodSignature(method, argTypes);
/*  344: 398 */             String methodKey = methodName + methodSignature;
/*  345: 399 */             if (!generatedOverrides.has(methodKey))
/*  346:     */             {
/*  347: 400 */               generateMethod(cfw, adapterName, methodName, argTypes, method.getReturnType());
/*  348:     */               
/*  349: 402 */               generatedOverrides.put(methodKey, 0);
/*  350: 403 */               generatedMethods.put(methodName, 0);
/*  351:     */             }
/*  352:     */           }
/*  353:     */         }
/*  354:     */       }
/*  355:     */     }
/*  356: 412 */     Method[] methods = getOverridableMethods(superClass);
/*  357: 413 */     for (int j = 0; j < methods.length; j++)
/*  358:     */     {
/*  359: 414 */       Method method = methods[j];
/*  360: 415 */       int mods = method.getModifiers();
/*  361:     */       
/*  362:     */ 
/*  363:     */ 
/*  364: 419 */       boolean isAbstractMethod = Modifier.isAbstract(mods);
/*  365: 420 */       String methodName = method.getName();
/*  366: 421 */       if ((isAbstractMethod) || (functionNames.has(methodName)))
/*  367:     */       {
/*  368: 424 */         Class<?>[] argTypes = method.getParameterTypes();
/*  369: 425 */         String methodSignature = getMethodSignature(method, argTypes);
/*  370: 426 */         String methodKey = methodName + methodSignature;
/*  371: 427 */         if (!generatedOverrides.has(methodKey))
/*  372:     */         {
/*  373: 428 */           generateMethod(cfw, adapterName, methodName, argTypes, method.getReturnType());
/*  374:     */           
/*  375: 430 */           generatedOverrides.put(methodKey, 0);
/*  376: 431 */           generatedMethods.put(methodName, 0);
/*  377: 435 */           if (!isAbstractMethod) {
/*  378: 436 */             generateSuper(cfw, adapterName, superName, methodName, methodSignature, argTypes, method.getReturnType());
/*  379:     */           }
/*  380:     */         }
/*  381:     */       }
/*  382:     */     }
/*  383: 446 */     ObjToIntMap.Iterator iter = new ObjToIntMap.Iterator(functionNames);
/*  384: 447 */     for (iter.start(); !iter.done(); iter.next())
/*  385:     */     {
/*  386: 448 */       String functionName = (String)iter.getKey();
/*  387: 449 */       if (!generatedMethods.has(functionName))
/*  388:     */       {
/*  389: 451 */         int length = iter.getValue();
/*  390: 452 */         Class<?>[] parms = new Class[length];
/*  391: 453 */         for (int k = 0; k < length; k++) {
/*  392: 454 */           parms[k] = ScriptRuntime.ObjectClass;
/*  393:     */         }
/*  394: 455 */         generateMethod(cfw, adapterName, functionName, parms, ScriptRuntime.ObjectClass);
/*  395:     */       }
/*  396:     */     }
/*  397: 458 */     return cfw.toByteArray();
/*  398:     */   }
/*  399:     */   
/*  400:     */   static Method[] getOverridableMethods(Class<?> clazz)
/*  401:     */   {
/*  402: 463 */     ArrayList<Method> list = new ArrayList();
/*  403: 464 */     HashSet<String> skip = new HashSet();
/*  404: 469 */     for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
/*  405: 470 */       appendOverridableMethods(c, list, skip);
/*  406:     */     }
/*  407: 472 */     for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
/*  408: 473 */       for (Class<?> intf : c.getInterfaces()) {
/*  409: 474 */         appendOverridableMethods(intf, list, skip);
/*  410:     */       }
/*  411:     */     }
/*  412: 476 */     return (Method[])list.toArray(new Method[list.size()]);
/*  413:     */   }
/*  414:     */   
/*  415:     */   private static void appendOverridableMethods(Class<?> c, ArrayList<Method> list, HashSet<String> skip)
/*  416:     */   {
/*  417: 482 */     Method[] methods = c.getDeclaredMethods();
/*  418: 483 */     for (int i = 0; i < methods.length; i++)
/*  419:     */     {
/*  420: 484 */       String methodKey = methods[i].getName() + getMethodSignature(methods[i], methods[i].getParameterTypes());
/*  421: 487 */       if (!skip.contains(methodKey))
/*  422:     */       {
/*  423: 489 */         int mods = methods[i].getModifiers();
/*  424: 490 */         if (!Modifier.isStatic(mods)) {
/*  425: 492 */           if (Modifier.isFinal(mods))
/*  426:     */           {
/*  427: 495 */             skip.add(methodKey);
/*  428:     */           }
/*  429: 498 */           else if ((Modifier.isPublic(mods)) || (Modifier.isProtected(mods)))
/*  430:     */           {
/*  431: 499 */             list.add(methods[i]);
/*  432: 500 */             skip.add(methodKey);
/*  433:     */           }
/*  434:     */         }
/*  435:     */       }
/*  436:     */     }
/*  437:     */   }
/*  438:     */   
/*  439:     */   static Class<?> loadAdapterClass(String className, byte[] classBytes)
/*  440:     */   {
/*  441: 508 */     Class<?> domainClass = SecurityController.getStaticSecurityDomainClass();
/*  442:     */     Object staticDomain;
/*  443:     */     Object staticDomain;
/*  444: 509 */     if ((domainClass == CodeSource.class) || (domainClass == ProtectionDomain.class))
/*  445:     */     {
/*  446: 511 */       ProtectionDomain protectionDomain = SecurityUtilities.getScriptProtectionDomain();
/*  447: 512 */       if (protectionDomain == null) {
/*  448: 513 */         protectionDomain = JavaAdapter.class.getProtectionDomain();
/*  449:     */       }
/*  450:     */       Object staticDomain;
/*  451: 515 */       if (domainClass == CodeSource.class) {
/*  452: 516 */         staticDomain = protectionDomain == null ? null : protectionDomain.getCodeSource();
/*  453:     */       } else {
/*  454: 519 */         staticDomain = protectionDomain;
/*  455:     */       }
/*  456:     */     }
/*  457:     */     else
/*  458:     */     {
/*  459: 523 */       staticDomain = null;
/*  460:     */     }
/*  461: 525 */     GeneratedClassLoader loader = SecurityController.createLoader(null, staticDomain);
/*  462:     */     
/*  463: 527 */     Class<?> result = loader.defineClass(className, classBytes);
/*  464: 528 */     loader.linkClass(result);
/*  465: 529 */     return result;
/*  466:     */   }
/*  467:     */   
/*  468:     */   public static Function getFunction(Scriptable obj, String functionName)
/*  469:     */   {
/*  470: 534 */     Object x = ScriptableObject.getProperty(obj, functionName);
/*  471: 535 */     if (x == Scriptable.NOT_FOUND) {
/*  472: 541 */       return null;
/*  473:     */     }
/*  474: 543 */     if (!(x instanceof Function)) {
/*  475: 544 */       throw ScriptRuntime.notFunctionError(x, functionName);
/*  476:     */     }
/*  477: 546 */     return (Function)x;
/*  478:     */   }
/*  479:     */   
/*  480:     */   public static Object callMethod(ContextFactory factory, final Scriptable thisObj, final Function f, final Object[] args, final long argsToWrap)
/*  481:     */   {
/*  482: 558 */     if (f == null) {
/*  483: 560 */       return Undefined.instance;
/*  484:     */     }
/*  485: 562 */     if (factory == null) {
/*  486: 563 */       factory = ContextFactory.getGlobal();
/*  487:     */     }
/*  488: 566 */     Scriptable scope = f.getParentScope();
/*  489: 567 */     if (argsToWrap == 0L) {
/*  490: 568 */       return Context.call(factory, f, scope, thisObj, args);
/*  491:     */     }
/*  492: 571 */     Context cx = Context.getCurrentContext();
/*  493: 572 */     if (cx != null) {
/*  494: 573 */       return doCall(cx, scope, thisObj, f, args, argsToWrap);
/*  495:     */     }
/*  496: 575 */     factory.call(new ContextAction()
/*  497:     */     {
/*  498:     */       public Object run(Context cx)
/*  499:     */       {
/*  500: 578 */         return JavaAdapter.doCall(cx, this.val$scope, thisObj, f, args, argsToWrap);
/*  501:     */       }
/*  502:     */     });
/*  503:     */   }
/*  504:     */   
/*  505:     */   private static Object doCall(Context cx, Scriptable scope, Scriptable thisObj, Function f, Object[] args, long argsToWrap)
/*  506:     */   {
/*  507: 589 */     for (int i = 0; i != args.length; i++) {
/*  508: 590 */       if (0L != (argsToWrap & 1 << i))
/*  509:     */       {
/*  510: 591 */         Object arg = args[i];
/*  511: 592 */         if (!(arg instanceof Scriptable)) {
/*  512: 593 */           args[i] = cx.getWrapFactory().wrap(cx, scope, arg, null);
/*  513:     */         }
/*  514:     */       }
/*  515:     */     }
/*  516: 598 */     return f.call(cx, scope, thisObj, args);
/*  517:     */   }
/*  518:     */   
/*  519:     */   public static Scriptable runScript(Script script)
/*  520:     */   {
/*  521: 603 */     (Scriptable)ContextFactory.getGlobal().call(new ContextAction()
/*  522:     */     {
/*  523:     */       public Object run(Context cx)
/*  524:     */       {
/*  525: 607 */         ScriptableObject global = ScriptRuntime.getGlobal(cx);
/*  526: 608 */         this.val$script.exec(cx, global);
/*  527: 609 */         return global;
/*  528:     */       }
/*  529:     */     });
/*  530:     */   }
/*  531:     */   
/*  532:     */   private static void generateCtor(ClassFileWriter cfw, String adapterName, String superName)
/*  533:     */   {
/*  534: 617 */     cfw.startMethod("<init>", "(Lnet/sourceforge/htmlunit/corejs/javascript/ContextFactory;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)V", (short)1);
/*  535:     */     
/*  536:     */ 
/*  537:     */ 
/*  538:     */ 
/*  539:     */ 
/*  540: 623 */     cfw.add(42);
/*  541: 624 */     cfw.addInvoke(183, superName, "<init>", "()V");
/*  542:     */     
/*  543:     */ 
/*  544: 627 */     cfw.add(42);
/*  545: 628 */     cfw.add(43);
/*  546: 629 */     cfw.add(181, adapterName, "factory", "Lnet/sourceforge/htmlunit/corejs/javascript/ContextFactory;");
/*  547:     */     
/*  548:     */ 
/*  549:     */ 
/*  550: 633 */     cfw.add(42);
/*  551: 634 */     cfw.add(44);
/*  552: 635 */     cfw.add(181, adapterName, "delegee", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  553:     */     
/*  554:     */ 
/*  555: 638 */     cfw.add(42);
/*  556:     */     
/*  557: 640 */     cfw.add(44);
/*  558: 641 */     cfw.add(42);
/*  559: 642 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/JavaAdapter", "createAdapterWrapper", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/Object;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  560:     */     
/*  561:     */ 
/*  562:     */ 
/*  563:     */ 
/*  564:     */ 
/*  565: 648 */     cfw.add(181, adapterName, "self", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  566:     */     
/*  567:     */ 
/*  568: 651 */     cfw.add(177);
/*  569: 652 */     cfw.stopMethod((short)3);
/*  570:     */   }
/*  571:     */   
/*  572:     */   private static void generateSerialCtor(ClassFileWriter cfw, String adapterName, String superName)
/*  573:     */   {
/*  574: 659 */     cfw.startMethod("<init>", "(Lnet/sourceforge/htmlunit/corejs/javascript/ContextFactory;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;)V", (short)1);
/*  575:     */     
/*  576:     */ 
/*  577:     */ 
/*  578:     */ 
/*  579:     */ 
/*  580:     */ 
/*  581:     */ 
/*  582: 667 */     cfw.add(42);
/*  583: 668 */     cfw.addInvoke(183, superName, "<init>", "()V");
/*  584:     */     
/*  585:     */ 
/*  586: 671 */     cfw.add(42);
/*  587: 672 */     cfw.add(43);
/*  588: 673 */     cfw.add(181, adapterName, "factory", "Lnet/sourceforge/htmlunit/corejs/javascript/ContextFactory;");
/*  589:     */     
/*  590:     */ 
/*  591:     */ 
/*  592: 677 */     cfw.add(42);
/*  593: 678 */     cfw.add(44);
/*  594: 679 */     cfw.add(181, adapterName, "delegee", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  595:     */     
/*  596:     */ 
/*  597: 682 */     cfw.add(42);
/*  598: 683 */     cfw.add(45);
/*  599: 684 */     cfw.add(181, adapterName, "self", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  600:     */     
/*  601:     */ 
/*  602: 687 */     cfw.add(177);
/*  603: 688 */     cfw.stopMethod((short)4);
/*  604:     */   }
/*  605:     */   
/*  606:     */   private static void generateEmptyCtor(ClassFileWriter cfw, String adapterName, String superName, String scriptClassName)
/*  607:     */   {
/*  608: 696 */     cfw.startMethod("<init>", "()V", (short)1);
/*  609:     */     
/*  610:     */ 
/*  611: 699 */     cfw.add(42);
/*  612: 700 */     cfw.addInvoke(183, superName, "<init>", "()V");
/*  613:     */     
/*  614:     */ 
/*  615: 703 */     cfw.add(42);
/*  616: 704 */     cfw.add(1);
/*  617: 705 */     cfw.add(181, adapterName, "factory", "Lnet/sourceforge/htmlunit/corejs/javascript/ContextFactory;");
/*  618:     */     
/*  619:     */ 
/*  620:     */ 
/*  621: 709 */     cfw.add(187, scriptClassName);
/*  622: 710 */     cfw.add(89);
/*  623: 711 */     cfw.addInvoke(183, scriptClassName, "<init>", "()V");
/*  624:     */     
/*  625:     */ 
/*  626: 714 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/JavaAdapter", "runScript", "(Lnet/sourceforge/htmlunit/corejs/javascript/Script;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  627:     */     
/*  628:     */ 
/*  629:     */ 
/*  630:     */ 
/*  631: 719 */     cfw.add(76);
/*  632:     */     
/*  633:     */ 
/*  634: 722 */     cfw.add(42);
/*  635: 723 */     cfw.add(43);
/*  636: 724 */     cfw.add(181, adapterName, "delegee", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  637:     */     
/*  638:     */ 
/*  639: 727 */     cfw.add(42);
/*  640:     */     
/*  641: 729 */     cfw.add(43);
/*  642: 730 */     cfw.add(42);
/*  643: 731 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/JavaAdapter", "createAdapterWrapper", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/Object;)Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  644:     */     
/*  645:     */ 
/*  646:     */ 
/*  647:     */ 
/*  648:     */ 
/*  649: 737 */     cfw.add(181, adapterName, "self", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  650:     */     
/*  651:     */ 
/*  652: 740 */     cfw.add(177);
/*  653: 741 */     cfw.stopMethod((short)2);
/*  654:     */   }
/*  655:     */   
/*  656:     */   static void generatePushWrappedArgs(ClassFileWriter cfw, Class<?>[] argTypes, int arrayLength)
/*  657:     */   {
/*  658: 754 */     cfw.addPush(arrayLength);
/*  659: 755 */     cfw.add(189, "java/lang/Object");
/*  660: 756 */     int paramOffset = 1;
/*  661: 757 */     for (int i = 0; i != argTypes.length; i++)
/*  662:     */     {
/*  663: 758 */       cfw.add(89);
/*  664: 759 */       cfw.addPush(i);
/*  665: 760 */       paramOffset += generateWrapArg(cfw, paramOffset, argTypes[i]);
/*  666: 761 */       cfw.add(83);
/*  667:     */     }
/*  668:     */   }
/*  669:     */   
/*  670:     */   private static int generateWrapArg(ClassFileWriter cfw, int paramOffset, Class<?> argType)
/*  671:     */   {
/*  672: 773 */     int size = 1;
/*  673: 774 */     if (!argType.isPrimitive())
/*  674:     */     {
/*  675: 775 */       cfw.add(25, paramOffset);
/*  676:     */     }
/*  677: 777 */     else if (argType == Boolean.TYPE)
/*  678:     */     {
/*  679: 779 */       cfw.add(187, "java/lang/Boolean");
/*  680: 780 */       cfw.add(89);
/*  681: 781 */       cfw.add(21, paramOffset);
/*  682: 782 */       cfw.addInvoke(183, "java/lang/Boolean", "<init>", "(Z)V");
/*  683:     */     }
/*  684: 785 */     else if (argType == Character.TYPE)
/*  685:     */     {
/*  686: 787 */       cfw.add(21, paramOffset);
/*  687: 788 */       cfw.addInvoke(184, "java/lang/String", "valueOf", "(C)Ljava/lang/String;");
/*  688:     */     }
/*  689:     */     else
/*  690:     */     {
/*  691: 793 */       cfw.add(187, "java/lang/Double");
/*  692: 794 */       cfw.add(89);
/*  693: 795 */       String typeName = argType.getName();
/*  694: 796 */       switch (typeName.charAt(0))
/*  695:     */       {
/*  696:     */       case 'b': 
/*  697:     */       case 'i': 
/*  698:     */       case 's': 
/*  699: 801 */         cfw.add(21, paramOffset);
/*  700: 802 */         cfw.add(135);
/*  701: 803 */         break;
/*  702:     */       case 'l': 
/*  703: 806 */         cfw.add(22, paramOffset);
/*  704: 807 */         cfw.add(138);
/*  705: 808 */         size = 2;
/*  706: 809 */         break;
/*  707:     */       case 'f': 
/*  708: 812 */         cfw.add(23, paramOffset);
/*  709: 813 */         cfw.add(141);
/*  710: 814 */         break;
/*  711:     */       case 'd': 
/*  712: 816 */         cfw.add(24, paramOffset);
/*  713: 817 */         size = 2;
/*  714:     */       }
/*  715: 820 */       cfw.addInvoke(183, "java/lang/Double", "<init>", "(D)V");
/*  716:     */     }
/*  717: 823 */     return size;
/*  718:     */   }
/*  719:     */   
/*  720:     */   static void generateReturnResult(ClassFileWriter cfw, Class<?> retType, boolean callConvertResult)
/*  721:     */   {
/*  722: 836 */     if (retType == Void.TYPE)
/*  723:     */     {
/*  724: 837 */       cfw.add(87);
/*  725: 838 */       cfw.add(177);
/*  726:     */     }
/*  727: 840 */     else if (retType == Boolean.TYPE)
/*  728:     */     {
/*  729: 841 */       cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/Context", "toBoolean", "(Ljava/lang/Object;)Z");
/*  730:     */       
/*  731:     */ 
/*  732: 844 */       cfw.add(172);
/*  733:     */     }
/*  734: 846 */     else if (retType == Character.TYPE)
/*  735:     */     {
/*  736: 850 */       cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/Context", "toString", "(Ljava/lang/Object;)Ljava/lang/String;");
/*  737:     */       
/*  738:     */ 
/*  739:     */ 
/*  740: 854 */       cfw.add(3);
/*  741: 855 */       cfw.addInvoke(182, "java/lang/String", "charAt", "(I)C");
/*  742:     */       
/*  743: 857 */       cfw.add(172);
/*  744:     */     }
/*  745: 859 */     else if (retType.isPrimitive())
/*  746:     */     {
/*  747: 860 */       cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/Context", "toNumber", "(Ljava/lang/Object;)D");
/*  748:     */       
/*  749:     */ 
/*  750: 863 */       String typeName = retType.getName();
/*  751: 864 */       switch (typeName.charAt(0))
/*  752:     */       {
/*  753:     */       case 'b': 
/*  754:     */       case 'i': 
/*  755:     */       case 's': 
/*  756: 868 */         cfw.add(142);
/*  757: 869 */         cfw.add(172);
/*  758: 870 */         break;
/*  759:     */       case 'l': 
/*  760: 872 */         cfw.add(143);
/*  761: 873 */         cfw.add(173);
/*  762: 874 */         break;
/*  763:     */       case 'f': 
/*  764: 876 */         cfw.add(144);
/*  765: 877 */         cfw.add(174);
/*  766: 878 */         break;
/*  767:     */       case 'd': 
/*  768: 880 */         cfw.add(175);
/*  769: 881 */         break;
/*  770:     */       case 'c': 
/*  771:     */       case 'e': 
/*  772:     */       case 'g': 
/*  773:     */       case 'h': 
/*  774:     */       case 'j': 
/*  775:     */       case 'k': 
/*  776:     */       case 'm': 
/*  777:     */       case 'n': 
/*  778:     */       case 'o': 
/*  779:     */       case 'p': 
/*  780:     */       case 'q': 
/*  781:     */       case 'r': 
/*  782:     */       default: 
/*  783: 883 */         throw new RuntimeException("Unexpected return type " + retType.toString());
/*  784:     */       }
/*  785:     */     }
/*  786:     */     else
/*  787:     */     {
/*  788: 888 */       String retTypeStr = retType.getName();
/*  789: 889 */       if (callConvertResult)
/*  790:     */       {
/*  791: 890 */         cfw.addLoadConstant(retTypeStr);
/*  792: 891 */         cfw.addInvoke(184, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
/*  793:     */         
/*  794:     */ 
/*  795:     */ 
/*  796:     */ 
/*  797: 896 */         cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/JavaAdapter", "convertResult", "(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
/*  798:     */       }
/*  799: 904 */       cfw.add(192, retTypeStr);
/*  800: 905 */       cfw.add(176);
/*  801:     */     }
/*  802:     */   }
/*  803:     */   
/*  804:     */   private static void generateMethod(ClassFileWriter cfw, String genName, String methodName, Class<?>[] parms, Class<?> returnType)
/*  805:     */   {
/*  806: 913 */     StringBuffer sb = new StringBuffer();
/*  807: 914 */     int paramsEnd = appendMethodSignature(parms, returnType, sb);
/*  808: 915 */     String methodSignature = sb.toString();
/*  809: 916 */     cfw.startMethod(methodName, methodSignature, (short)1);
/*  810:     */     
/*  811:     */ 
/*  812:     */ 
/*  813:     */ 
/*  814:     */ 
/*  815: 922 */     cfw.add(42);
/*  816: 923 */     cfw.add(180, genName, "factory", "Lnet/sourceforge/htmlunit/corejs/javascript/ContextFactory;");
/*  817:     */     
/*  818:     */ 
/*  819:     */ 
/*  820: 927 */     cfw.add(42);
/*  821: 928 */     cfw.add(180, genName, "self", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  822:     */     
/*  823:     */ 
/*  824:     */ 
/*  825: 932 */     cfw.add(42);
/*  826: 933 */     cfw.add(180, genName, "delegee", "Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;");
/*  827:     */     
/*  828: 935 */     cfw.addPush(methodName);
/*  829: 936 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/JavaAdapter", "getFunction", "(Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Ljava/lang/String;)Lnet/sourceforge/htmlunit/corejs/javascript/Function;");
/*  830:     */     
/*  831:     */ 
/*  832:     */ 
/*  833:     */ 
/*  834:     */ 
/*  835:     */ 
/*  836:     */ 
/*  837: 944 */     generatePushWrappedArgs(cfw, parms, parms.length);
/*  838: 947 */     if (parms.length > 64) {
/*  839: 950 */       throw Context.reportRuntimeError0("JavaAdapter can not subclass methods with more then 64 arguments.");
/*  840:     */     }
/*  841: 954 */     long convertionMask = 0L;
/*  842: 955 */     for (int i = 0; i != parms.length; i++) {
/*  843: 956 */       if (!parms[i].isPrimitive()) {
/*  844: 957 */         convertionMask |= 1 << i;
/*  845:     */       }
/*  846:     */     }
/*  847: 960 */     cfw.addPush(convertionMask);
/*  848:     */     
/*  849:     */ 
/*  850:     */ 
/*  851: 964 */     cfw.addInvoke(184, "net/sourceforge/htmlunit/corejs/javascript/JavaAdapter", "callMethod", "(Lnet/sourceforge/htmlunit/corejs/javascript/ContextFactory;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Function;[Ljava/lang/Object;J)Ljava/lang/Object;");
/*  852:     */     
/*  853:     */ 
/*  854:     */ 
/*  855:     */ 
/*  856:     */ 
/*  857:     */ 
/*  858:     */ 
/*  859:     */ 
/*  860:     */ 
/*  861: 974 */     generateReturnResult(cfw, returnType, true);
/*  862:     */     
/*  863: 976 */     cfw.stopMethod((short)paramsEnd);
/*  864:     */   }
/*  865:     */   
/*  866:     */   private static int generatePushParam(ClassFileWriter cfw, int paramOffset, Class<?> paramType)
/*  867:     */   {
/*  868: 986 */     if (!paramType.isPrimitive())
/*  869:     */     {
/*  870: 987 */       cfw.addALoad(paramOffset);
/*  871: 988 */       return 1;
/*  872:     */     }
/*  873: 990 */     String typeName = paramType.getName();
/*  874: 991 */     switch (typeName.charAt(0))
/*  875:     */     {
/*  876:     */     case 'b': 
/*  877:     */     case 'c': 
/*  878:     */     case 'i': 
/*  879:     */     case 's': 
/*  880:     */     case 'z': 
/*  881: 998 */       cfw.addILoad(paramOffset);
/*  882: 999 */       return 1;
/*  883:     */     case 'l': 
/*  884:1002 */       cfw.addLLoad(paramOffset);
/*  885:1003 */       return 2;
/*  886:     */     case 'f': 
/*  887:1006 */       cfw.addFLoad(paramOffset);
/*  888:1007 */       return 1;
/*  889:     */     case 'd': 
/*  890:1009 */       cfw.addDLoad(paramOffset);
/*  891:1010 */       return 2;
/*  892:     */     }
/*  893:1012 */     throw Kit.codeBug();
/*  894:     */   }
/*  895:     */   
/*  896:     */   private static void generatePopResult(ClassFileWriter cfw, Class<?> retType)
/*  897:     */   {
/*  898:1023 */     if (retType.isPrimitive())
/*  899:     */     {
/*  900:1024 */       String typeName = retType.getName();
/*  901:1025 */       switch (typeName.charAt(0))
/*  902:     */       {
/*  903:     */       case 'b': 
/*  904:     */       case 'c': 
/*  905:     */       case 'i': 
/*  906:     */       case 's': 
/*  907:     */       case 'z': 
/*  908:1031 */         cfw.add(172);
/*  909:1032 */         break;
/*  910:     */       case 'l': 
/*  911:1034 */         cfw.add(173);
/*  912:1035 */         break;
/*  913:     */       case 'f': 
/*  914:1037 */         cfw.add(174);
/*  915:1038 */         break;
/*  916:     */       case 'd': 
/*  917:1040 */         cfw.add(175);
/*  918:     */       }
/*  919:     */     }
/*  920:     */     else
/*  921:     */     {
/*  922:1044 */       cfw.add(176);
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   private static void generateSuper(ClassFileWriter cfw, String genName, String superName, String methodName, String methodSignature, Class<?>[] parms, Class<?> returnType)
/*  927:     */   {
/*  928:1058 */     cfw.startMethod("super$" + methodName, methodSignature, (short)1);
/*  929:     */     
/*  930:     */ 
/*  931:     */ 
/*  932:1062 */     cfw.add(25, 0);
/*  933:     */     
/*  934:     */ 
/*  935:1065 */     int paramOffset = 1;
/*  936:1066 */     for (int i = 0; i < parms.length; i++) {
/*  937:1067 */       paramOffset += generatePushParam(cfw, paramOffset, parms[i]);
/*  938:     */     }
/*  939:1071 */     cfw.addInvoke(183, superName, methodName, methodSignature);
/*  940:     */     
/*  941:     */ 
/*  942:     */ 
/*  943:     */ 
/*  944:     */ 
/*  945:1077 */     Class<?> retType = returnType;
/*  946:1078 */     if (!retType.equals(Void.TYPE)) {
/*  947:1079 */       generatePopResult(cfw, retType);
/*  948:     */     } else {
/*  949:1081 */       cfw.add(177);
/*  950:     */     }
/*  951:1083 */     cfw.stopMethod((short)(paramOffset + 1));
/*  952:     */   }
/*  953:     */   
/*  954:     */   private static String getMethodSignature(Method method, Class<?>[] argTypes)
/*  955:     */   {
/*  956:1091 */     StringBuffer sb = new StringBuffer();
/*  957:1092 */     appendMethodSignature(argTypes, method.getReturnType(), sb);
/*  958:1093 */     return sb.toString();
/*  959:     */   }
/*  960:     */   
/*  961:     */   static int appendMethodSignature(Class<?>[] argTypes, Class<?> returnType, StringBuffer sb)
/*  962:     */   {
/*  963:1100 */     sb.append('(');
/*  964:1101 */     int firstLocal = 1 + argTypes.length;
/*  965:1102 */     for (int i = 0; i < argTypes.length; i++)
/*  966:     */     {
/*  967:1103 */       Class<?> type = argTypes[i];
/*  968:1104 */       appendTypeString(sb, type);
/*  969:1105 */       if ((type == Long.TYPE) || (type == Double.TYPE)) {
/*  970:1107 */         firstLocal++;
/*  971:     */       }
/*  972:     */     }
/*  973:1110 */     sb.append(')');
/*  974:1111 */     appendTypeString(sb, returnType);
/*  975:1112 */     return firstLocal;
/*  976:     */   }
/*  977:     */   
/*  978:     */   private static StringBuffer appendTypeString(StringBuffer sb, Class<?> type)
/*  979:     */   {
/*  980:1117 */     while (type.isArray())
/*  981:     */     {
/*  982:1118 */       sb.append('[');
/*  983:1119 */       type = type.getComponentType();
/*  984:     */     }
/*  985:1121 */     if (type.isPrimitive())
/*  986:     */     {
/*  987:     */       char typeLetter;
/*  988:     */       char typeLetter;
/*  989:1123 */       if (type == Boolean.TYPE)
/*  990:     */       {
/*  991:1124 */         typeLetter = 'Z';
/*  992:     */       }
/*  993:     */       else
/*  994:     */       {
/*  995:     */         char typeLetter;
/*  996:1125 */         if (type == Long.TYPE)
/*  997:     */         {
/*  998:1126 */           typeLetter = 'J';
/*  999:     */         }
/* 1000:     */         else
/* 1001:     */         {
/* 1002:1128 */           String typeName = type.getName();
/* 1003:1129 */           typeLetter = Character.toUpperCase(typeName.charAt(0));
/* 1004:     */         }
/* 1005:     */       }
/* 1006:1131 */       sb.append(typeLetter);
/* 1007:     */     }
/* 1008:     */     else
/* 1009:     */     {
/* 1010:1133 */       sb.append('L');
/* 1011:1134 */       sb.append(type.getName().replace('.', '/'));
/* 1012:1135 */       sb.append(';');
/* 1013:     */     }
/* 1014:1137 */     return sb;
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   static int[] getArgsToConvert(Class<?>[] argTypes)
/* 1018:     */   {
/* 1019:1142 */     int count = 0;
/* 1020:1143 */     for (int i = 0; i != argTypes.length; i++) {
/* 1021:1144 */       if (!argTypes[i].isPrimitive()) {
/* 1022:1145 */         count++;
/* 1023:     */       }
/* 1024:     */     }
/* 1025:1147 */     if (count == 0) {
/* 1026:1148 */       return null;
/* 1027:     */     }
/* 1028:1149 */     int[] array = new int[count];
/* 1029:1150 */     count = 0;
/* 1030:1151 */     for (int i = 0; i != argTypes.length; i++) {
/* 1031:1152 */       if (!argTypes[i].isPrimitive()) {
/* 1032:1153 */         array[(count++)] = i;
/* 1033:     */       }
/* 1034:     */     }
/* 1035:1155 */     return array;
/* 1036:     */   }
/* 1037:     */   
/* 1038:1158 */   private static final Object FTAG = "JavaAdapter";
/* 1039:     */   private static final int Id_JavaAdapter = 1;
/* 1040:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.JavaAdapter
 * JD-Core Version:    0.7.0.1
 */