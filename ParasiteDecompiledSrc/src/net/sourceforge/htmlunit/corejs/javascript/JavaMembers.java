/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.AccessibleObject;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.Field;
/*   6:    */ import java.lang.reflect.Member;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.lang.reflect.Modifier;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Arrays;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Map.Entry;
/*  16:    */ import java.util.Set;
/*  17:    */ 
/*  18:    */ class JavaMembers
/*  19:    */ {
/*  20:    */   private Class<?> cl;
/*  21:    */   private Map<String, Object> members;
/*  22:    */   private Map<String, FieldAndMethods> fieldAndMethods;
/*  23:    */   private Map<String, Object> staticMembers;
/*  24:    */   private Map<String, FieldAndMethods> staticFieldAndMethods;
/*  25:    */   MemberBox[] ctors;
/*  26:    */   private boolean includePrivate;
/*  27:    */   
/*  28:    */   JavaMembers(Scriptable scope, Class<?> cl)
/*  29:    */   {
/*  30: 59 */     this(scope, cl, false);
/*  31:    */   }
/*  32:    */   
/*  33:    */   JavaMembers(Scriptable scope, Class<?> cl, boolean includeProtected)
/*  34:    */   {
/*  35:    */     try
/*  36:    */     {
/*  37: 65 */       Context cx = ContextFactory.getGlobal().enterContext();
/*  38: 66 */       ClassShutter shutter = cx.getClassShutter();
/*  39: 67 */       if ((shutter != null) && (!shutter.visibleToScripts(cl.getName()))) {
/*  40: 68 */         throw Context.reportRuntimeError1("msg.access.prohibited", cl.getName());
/*  41:    */       }
/*  42: 71 */       this.includePrivate = cx.hasFeature(13);
/*  43:    */       
/*  44: 73 */       this.members = new HashMap();
/*  45: 74 */       this.staticMembers = new HashMap();
/*  46: 75 */       this.cl = cl;
/*  47: 76 */       reflect(scope, includeProtected);
/*  48:    */     }
/*  49:    */     finally
/*  50:    */     {
/*  51: 78 */       Context.exit();
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   boolean has(String name, boolean isStatic)
/*  56:    */   {
/*  57: 84 */     Map<String, Object> ht = isStatic ? this.staticMembers : this.members;
/*  58: 85 */     Object obj = ht.get(name);
/*  59: 86 */     if (obj != null) {
/*  60: 87 */       return true;
/*  61:    */     }
/*  62: 89 */     return findExplicitFunction(name, isStatic) != null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   Object get(Scriptable scope, String name, Object javaObject, boolean isStatic)
/*  66:    */   {
/*  67: 95 */     Map<String, Object> ht = isStatic ? this.staticMembers : this.members;
/*  68: 96 */     Object member = ht.get(name);
/*  69: 97 */     if ((!isStatic) && (member == null)) {
/*  70: 99 */       member = this.staticMembers.get(name);
/*  71:    */     }
/*  72:101 */     if (member == null)
/*  73:    */     {
/*  74:102 */       member = getExplicitFunction(scope, name, javaObject, isStatic);
/*  75:104 */       if (member == null) {
/*  76:105 */         return Scriptable.NOT_FOUND;
/*  77:    */       }
/*  78:    */     }
/*  79:107 */     if ((member instanceof Scriptable)) {
/*  80:108 */       return member;
/*  81:    */     }
/*  82:110 */     Context cx = Context.getContext();
/*  83:    */     Object rval;
/*  84:    */     Class<?> type;
/*  85:    */     try
/*  86:    */     {
/*  87:    */       Class<?> type;
/*  88:114 */       if ((member instanceof BeanProperty))
/*  89:    */       {
/*  90:115 */         BeanProperty bp = (BeanProperty)member;
/*  91:116 */         if (bp.getter == null) {
/*  92:117 */           return Scriptable.NOT_FOUND;
/*  93:    */         }
/*  94:118 */         Object rval = bp.getter.invoke(javaObject, Context.emptyArgs);
/*  95:119 */         type = bp.getter.method().getReturnType();
/*  96:    */       }
/*  97:    */       else
/*  98:    */       {
/*  99:121 */         Field field = (Field)member;
/* 100:122 */         rval = field.get(isStatic ? null : javaObject);
/* 101:123 */         type = field.getType();
/* 102:    */       }
/* 103:    */     }
/* 104:    */     catch (Exception ex)
/* 105:    */     {
/* 106:126 */       throw Context.throwAsScriptRuntimeEx(ex);
/* 107:    */     }
/* 108:129 */     scope = ScriptableObject.getTopLevelScope(scope);
/* 109:130 */     return cx.getWrapFactory().wrap(cx, scope, rval, type);
/* 110:    */   }
/* 111:    */   
/* 112:    */   void put(Scriptable scope, String name, Object javaObject, Object value, boolean isStatic)
/* 113:    */   {
/* 114:136 */     Map<String, Object> ht = isStatic ? this.staticMembers : this.members;
/* 115:137 */     Object member = ht.get(name);
/* 116:138 */     if ((!isStatic) && (member == null)) {
/* 117:140 */       member = this.staticMembers.get(name);
/* 118:    */     }
/* 119:142 */     if (member == null) {
/* 120:143 */       throw reportMemberNotFound(name);
/* 121:    */     }
/* 122:144 */     if ((member instanceof FieldAndMethods))
/* 123:    */     {
/* 124:145 */       FieldAndMethods fam = (FieldAndMethods)ht.get(name);
/* 125:146 */       member = fam.field;
/* 126:    */     }
/* 127:150 */     if ((member instanceof BeanProperty))
/* 128:    */     {
/* 129:151 */       BeanProperty bp = (BeanProperty)member;
/* 130:152 */       if (bp.setter == null) {
/* 131:153 */         throw reportMemberNotFound(name);
/* 132:    */       }
/* 133:158 */       if ((bp.setters == null) || (value == null))
/* 134:    */       {
/* 135:159 */         Class<?> setType = bp.setter.argTypes[0];
/* 136:160 */         Object[] args = { Context.jsToJava(value, setType) };
/* 137:    */         try
/* 138:    */         {
/* 139:162 */           bp.setter.invoke(javaObject, args);
/* 140:    */         }
/* 141:    */         catch (Exception ex)
/* 142:    */         {
/* 143:164 */           throw Context.throwAsScriptRuntimeEx(ex);
/* 144:    */         }
/* 145:    */       }
/* 146:    */       else
/* 147:    */       {
/* 148:167 */         Object[] args = { value };
/* 149:168 */         bp.setters.call(Context.getContext(), ScriptableObject.getTopLevelScope(scope), scope, args);
/* 150:    */       }
/* 151:    */     }
/* 152:    */     else
/* 153:    */     {
/* 154:174 */       if (!(member instanceof Field))
/* 155:    */       {
/* 156:175 */         String str = member == null ? "msg.java.internal.private" : "msg.java.method.assign";
/* 157:    */         
/* 158:177 */         throw Context.reportRuntimeError1(str, name);
/* 159:    */       }
/* 160:179 */       Field field = (Field)member;
/* 161:180 */       Object javaValue = Context.jsToJava(value, field.getType());
/* 162:    */       try
/* 163:    */       {
/* 164:182 */         field.set(javaObject, javaValue);
/* 165:    */       }
/* 166:    */       catch (IllegalAccessException accessEx)
/* 167:    */       {
/* 168:184 */         if ((field.getModifiers() & 0x10) != 0) {
/* 169:186 */           return;
/* 170:    */         }
/* 171:188 */         throw Context.throwAsScriptRuntimeEx(accessEx);
/* 172:    */       }
/* 173:    */       catch (IllegalArgumentException argEx)
/* 174:    */       {
/* 175:190 */         throw Context.reportRuntimeError3("msg.java.internal.field.type", value.getClass().getName(), field, javaObject.getClass().getName());
/* 176:    */       }
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   Object[] getIds(boolean isStatic)
/* 181:    */   {
/* 182:200 */     Map<String, Object> map = isStatic ? this.staticMembers : this.members;
/* 183:201 */     return map.keySet().toArray(new Object[map.size()]);
/* 184:    */   }
/* 185:    */   
/* 186:    */   static String javaSignature(Class<?> type)
/* 187:    */   {
/* 188:206 */     if (!type.isArray()) {
/* 189:207 */       return type.getName();
/* 190:    */     }
/* 191:209 */     int arrayDimension = 0;
/* 192:    */     do
/* 193:    */     {
/* 194:211 */       arrayDimension++;
/* 195:212 */       type = type.getComponentType();
/* 196:213 */     } while (type.isArray());
/* 197:214 */     String name = type.getName();
/* 198:215 */     String suffix = "[]";
/* 199:216 */     if (arrayDimension == 1) {
/* 200:217 */       return name.concat(suffix);
/* 201:    */     }
/* 202:219 */     int length = name.length() + arrayDimension * suffix.length();
/* 203:220 */     StringBuffer sb = new StringBuffer(length);
/* 204:221 */     sb.append(name);
/* 205:222 */     while (arrayDimension != 0)
/* 206:    */     {
/* 207:223 */       arrayDimension--;
/* 208:224 */       sb.append(suffix);
/* 209:    */     }
/* 210:226 */     return sb.toString();
/* 211:    */   }
/* 212:    */   
/* 213:    */   static String liveConnectSignature(Class<?>[] argTypes)
/* 214:    */   {
/* 215:233 */     int N = argTypes.length;
/* 216:234 */     if (N == 0) {
/* 217:234 */       return "()";
/* 218:    */     }
/* 219:235 */     StringBuffer sb = new StringBuffer();
/* 220:236 */     sb.append('(');
/* 221:237 */     for (int i = 0; i != N; i++)
/* 222:    */     {
/* 223:238 */       if (i != 0) {
/* 224:239 */         sb.append(',');
/* 225:    */       }
/* 226:241 */       sb.append(javaSignature(argTypes[i]));
/* 227:    */     }
/* 228:243 */     sb.append(')');
/* 229:244 */     return sb.toString();
/* 230:    */   }
/* 231:    */   
/* 232:    */   private MemberBox findExplicitFunction(String name, boolean isStatic)
/* 233:    */   {
/* 234:249 */     int sigStart = name.indexOf('(');
/* 235:250 */     if (sigStart < 0) {
/* 236:250 */       return null;
/* 237:    */     }
/* 238:252 */     Map<String, Object> ht = isStatic ? this.staticMembers : this.members;
/* 239:253 */     MemberBox[] methodsOrCtors = null;
/* 240:254 */     boolean isCtor = (isStatic) && (sigStart == 0);
/* 241:256 */     if (isCtor)
/* 242:    */     {
/* 243:258 */       methodsOrCtors = this.ctors;
/* 244:    */     }
/* 245:    */     else
/* 246:    */     {
/* 247:261 */       String trueName = name.substring(0, sigStart);
/* 248:262 */       Object obj = ht.get(trueName);
/* 249:263 */       if ((!isStatic) && (obj == null)) {
/* 250:265 */         obj = this.staticMembers.get(trueName);
/* 251:    */       }
/* 252:267 */       if ((obj instanceof NativeJavaMethod))
/* 253:    */       {
/* 254:268 */         NativeJavaMethod njm = (NativeJavaMethod)obj;
/* 255:269 */         methodsOrCtors = njm.methods;
/* 256:    */       }
/* 257:    */     }
/* 258:273 */     if (methodsOrCtors != null) {
/* 259:274 */       for (int i = 0; i < methodsOrCtors.length; i++)
/* 260:    */       {
/* 261:275 */         Class<?>[] type = methodsOrCtors[i].argTypes;
/* 262:276 */         String sig = liveConnectSignature(type);
/* 263:277 */         if ((sigStart + sig.length() == name.length()) && (name.regionMatches(sigStart, sig, 0, sig.length()))) {
/* 264:280 */           return methodsOrCtors[i];
/* 265:    */         }
/* 266:    */       }
/* 267:    */     }
/* 268:285 */     return null;
/* 269:    */   }
/* 270:    */   
/* 271:    */   private Object getExplicitFunction(Scriptable scope, String name, Object javaObject, boolean isStatic)
/* 272:    */   {
/* 273:291 */     Map<String, Object> ht = isStatic ? this.staticMembers : this.members;
/* 274:292 */     Object member = null;
/* 275:293 */     MemberBox methodOrCtor = findExplicitFunction(name, isStatic);
/* 276:295 */     if (methodOrCtor != null)
/* 277:    */     {
/* 278:296 */       Scriptable prototype = ScriptableObject.getFunctionPrototype(scope);
/* 279:299 */       if (methodOrCtor.isCtor())
/* 280:    */       {
/* 281:300 */         NativeJavaConstructor fun = new NativeJavaConstructor(methodOrCtor);
/* 282:    */         
/* 283:302 */         fun.setPrototype(prototype);
/* 284:303 */         member = fun;
/* 285:304 */         ht.put(name, fun);
/* 286:    */       }
/* 287:    */       else
/* 288:    */       {
/* 289:306 */         String trueName = methodOrCtor.getName();
/* 290:307 */         member = ht.get(trueName);
/* 291:309 */         if (((member instanceof NativeJavaMethod)) && (((NativeJavaMethod)member).methods.length > 1))
/* 292:    */         {
/* 293:311 */           NativeJavaMethod fun = new NativeJavaMethod(methodOrCtor, name);
/* 294:    */           
/* 295:313 */           fun.setPrototype(prototype);
/* 296:314 */           ht.put(name, fun);
/* 297:315 */           member = fun;
/* 298:    */         }
/* 299:    */       }
/* 300:    */     }
/* 301:320 */     return member;
/* 302:    */   }
/* 303:    */   
/* 304:    */   private static Method[] discoverAccessibleMethods(Class<?> clazz, boolean includeProtected, boolean includePrivate)
/* 305:    */   {
/* 306:334 */     Map<MethodSignature, Method> map = new HashMap();
/* 307:335 */     discoverAccessibleMethods(clazz, map, includeProtected, includePrivate);
/* 308:336 */     return (Method[])map.values().toArray(new Method[map.size()]);
/* 309:    */   }
/* 310:    */   
/* 311:    */   private static void discoverAccessibleMethods(Class<?> clazz, Map<MethodSignature, Method> map, boolean includeProtected, boolean includePrivate)
/* 312:    */   {
/* 313:343 */     if ((Modifier.isPublic(clazz.getModifiers())) || (includePrivate)) {
/* 314:    */       try
/* 315:    */       {
/* 316:345 */         if ((includeProtected) || (includePrivate)) {
/* 317:    */           for (;;)
/* 318:    */           {
/* 319:346 */             if (clazz == null) {
/* 320:    */               break label273;
/* 321:    */             }
/* 322:    */             try
/* 323:    */             {
/* 324:348 */               Method[] methods = clazz.getDeclaredMethods();
/* 325:349 */               for (int i = 0; i < methods.length; i++)
/* 326:    */               {
/* 327:350 */                 Method method = methods[i];
/* 328:351 */                 int mods = method.getModifiers();
/* 329:353 */                 if ((Modifier.isPublic(mods)) || (Modifier.isProtected(mods)) || (includePrivate))
/* 330:    */                 {
/* 331:357 */                   MethodSignature sig = new MethodSignature(method);
/* 332:358 */                   if (!map.containsKey(sig))
/* 333:    */                   {
/* 334:359 */                     if ((includePrivate) && (!method.isAccessible())) {
/* 335:360 */                       method.setAccessible(true);
/* 336:    */                     }
/* 337:361 */                     map.put(sig, method);
/* 338:    */                   }
/* 339:    */                 }
/* 340:    */               }
/* 341:365 */               clazz = clazz.getSuperclass();
/* 342:    */             }
/* 343:    */             catch (SecurityException e)
/* 344:    */             {
/* 345:370 */               Method[] methods = clazz.getMethods();
/* 346:371 */               for (int i = 0; i < methods.length; i++)
/* 347:    */               {
/* 348:372 */                 Method method = methods[i];
/* 349:373 */                 MethodSignature sig = new MethodSignature(method);
/* 350:375 */                 if (!map.containsKey(sig)) {
/* 351:376 */                   map.put(sig, method);
/* 352:    */                 }
/* 353:    */               }
/* 354:    */               break label273;
/* 355:    */             }
/* 356:    */           }
/* 357:    */         }
/* 358:383 */         Method[] methods = clazz.getMethods();
/* 359:384 */         for (int i = 0; i < methods.length; i++)
/* 360:    */         {
/* 361:385 */           Method method = methods[i];
/* 362:386 */           MethodSignature sig = new MethodSignature(method);
/* 363:388 */           if (!map.containsKey(sig)) {
/* 364:389 */             map.put(sig, method);
/* 365:    */           }
/* 366:    */         }
/* 367:    */         label273:
/* 368:392 */         return;
/* 369:    */       }
/* 370:    */       catch (SecurityException e)
/* 371:    */       {
/* 372:394 */         Context.reportWarning("Could not discover accessible methods of class " + clazz.getName() + " due to lack of privileges, " + "attemping superclasses/interfaces.");
/* 373:    */       }
/* 374:    */     }
/* 375:403 */     Class<?>[] interfaces = clazz.getInterfaces();
/* 376:404 */     for (int i = 0; i < interfaces.length; i++) {
/* 377:405 */       discoverAccessibleMethods(interfaces[i], map, includeProtected, includePrivate);
/* 378:    */     }
/* 379:408 */     Class<?> superclass = clazz.getSuperclass();
/* 380:409 */     if (superclass != null) {
/* 381:410 */       discoverAccessibleMethods(superclass, map, includeProtected, includePrivate);
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   private static final class MethodSignature
/* 386:    */   {
/* 387:    */     private final String name;
/* 388:    */     private final Class<?>[] args;
/* 389:    */     
/* 390:    */     private MethodSignature(String name, Class<?>[] args)
/* 391:    */     {
/* 392:422 */       this.name = name;
/* 393:423 */       this.args = args;
/* 394:    */     }
/* 395:    */     
/* 396:    */     MethodSignature(Method method)
/* 397:    */     {
/* 398:428 */       this(method.getName(), method.getParameterTypes());
/* 399:    */     }
/* 400:    */     
/* 401:    */     public boolean equals(Object o)
/* 402:    */     {
/* 403:434 */       if ((o instanceof MethodSignature))
/* 404:    */       {
/* 405:436 */         MethodSignature ms = (MethodSignature)o;
/* 406:437 */         return (ms.name.equals(this.name)) && (Arrays.equals(this.args, ms.args));
/* 407:    */       }
/* 408:439 */       return false;
/* 409:    */     }
/* 410:    */     
/* 411:    */     public int hashCode()
/* 412:    */     {
/* 413:445 */       return this.name.hashCode() ^ this.args.length;
/* 414:    */     }
/* 415:    */   }
/* 416:    */   
/* 417:    */   private void reflect(Scriptable scope, boolean includeProtected)
/* 418:    */   {
/* 419:455 */     Method[] methods = discoverAccessibleMethods(this.cl, includeProtected, this.includePrivate);
/* 420:457 */     for (int i = 0; i < methods.length; i++)
/* 421:    */     {
/* 422:458 */       Method method = methods[i];
/* 423:459 */       int mods = method.getModifiers();
/* 424:460 */       boolean isStatic = Modifier.isStatic(mods);
/* 425:461 */       Map<String, Object> ht = isStatic ? this.staticMembers : this.members;
/* 426:462 */       String name = method.getName();
/* 427:463 */       Object value = ht.get(name);
/* 428:464 */       if (value == null)
/* 429:    */       {
/* 430:465 */         ht.put(name, method);
/* 431:    */       }
/* 432:    */       else
/* 433:    */       {
/* 434:    */         ObjArray overloadedMethods;
/* 435:    */         ObjArray overloadedMethods;
/* 436:468 */         if ((value instanceof ObjArray))
/* 437:    */         {
/* 438:469 */           overloadedMethods = (ObjArray)value;
/* 439:    */         }
/* 440:    */         else
/* 441:    */         {
/* 442:471 */           if (!(value instanceof Method)) {
/* 443:471 */             Kit.codeBug();
/* 444:    */           }
/* 445:474 */           overloadedMethods = new ObjArray();
/* 446:475 */           overloadedMethods.add(value);
/* 447:476 */           ht.put(name, overloadedMethods);
/* 448:    */         }
/* 449:478 */         overloadedMethods.add(method);
/* 450:    */       }
/* 451:    */     }
/* 452:    */     Map<String, Object> ht;
/* 453:484 */     for (int tableCursor = 0; tableCursor != 2; tableCursor++)
/* 454:    */     {
/* 455:485 */       boolean isStatic = tableCursor == 0;
/* 456:486 */       ht = isStatic ? this.staticMembers : this.members;
/* 457:487 */       for (Map.Entry<String, Object> entry : ht.entrySet())
/* 458:    */       {
/* 459:489 */         Object value = entry.getValue();
/* 460:    */         MemberBox[] methodBoxes;
/* 461:490 */         if ((value instanceof Method))
/* 462:    */         {
/* 463:491 */           MemberBox[] methodBoxes = new MemberBox[1];
/* 464:492 */           methodBoxes[0] = new MemberBox((Method)value);
/* 465:    */         }
/* 466:    */         else
/* 467:    */         {
/* 468:494 */           ObjArray overloadedMethods = (ObjArray)value;
/* 469:495 */           int N = overloadedMethods.size();
/* 470:496 */           if (N < 2) {
/* 471:496 */             Kit.codeBug();
/* 472:    */           }
/* 473:497 */           methodBoxes = new MemberBox[N];
/* 474:498 */           for (int i = 0; i != N; i++)
/* 475:    */           {
/* 476:499 */             Method method = (Method)overloadedMethods.get(i);
/* 477:500 */             methodBoxes[i] = new MemberBox(method);
/* 478:    */           }
/* 479:    */         }
/* 480:503 */         NativeJavaMethod fun = new NativeJavaMethod(methodBoxes);
/* 481:504 */         if (scope != null) {
/* 482:505 */           ScriptRuntime.setFunctionProtoAndParent(fun, scope);
/* 483:    */         }
/* 484:507 */         ht.put(entry.getKey(), fun);
/* 485:    */       }
/* 486:    */     }
/* 487:512 */     Field[] fields = getAccessibleFields();
/* 488:513 */     for (int i = 0; i < fields.length; i++)
/* 489:    */     {
/* 490:514 */       Field field = fields[i];
/* 491:515 */       String name = field.getName();
/* 492:516 */       int mods = field.getModifiers();
/* 493:517 */       if ((this.includePrivate) || (Modifier.isPublic(mods))) {
/* 494:    */         try
/* 495:    */         {
/* 496:521 */           boolean isStatic = Modifier.isStatic(mods);
/* 497:522 */           Map<String, Object> ht = isStatic ? this.staticMembers : this.members;
/* 498:523 */           Object member = ht.get(name);
/* 499:524 */           if (member == null)
/* 500:    */           {
/* 501:525 */             ht.put(name, field);
/* 502:    */           }
/* 503:526 */           else if ((member instanceof NativeJavaMethod))
/* 504:    */           {
/* 505:527 */             NativeJavaMethod method = (NativeJavaMethod)member;
/* 506:528 */             FieldAndMethods fam = new FieldAndMethods(scope, method.methods, field);
/* 507:    */             
/* 508:530 */             Map<String, FieldAndMethods> fmht = isStatic ? this.staticFieldAndMethods : this.fieldAndMethods;
/* 509:532 */             if (fmht == null)
/* 510:    */             {
/* 511:533 */               fmht = new HashMap();
/* 512:534 */               if (isStatic) {
/* 513:535 */                 this.staticFieldAndMethods = fmht;
/* 514:    */               } else {
/* 515:537 */                 this.fieldAndMethods = fmht;
/* 516:    */               }
/* 517:    */             }
/* 518:540 */             fmht.put(name, fam);
/* 519:541 */             ht.put(name, fam);
/* 520:    */           }
/* 521:542 */           else if ((member instanceof Field))
/* 522:    */           {
/* 523:543 */             Field oldField = (Field)member;
/* 524:550 */             if (oldField.getDeclaringClass().isAssignableFrom(field.getDeclaringClass())) {
/* 525:553 */               ht.put(name, field);
/* 526:    */             }
/* 527:    */           }
/* 528:    */           else
/* 529:    */           {
/* 530:557 */             Kit.codeBug();
/* 531:    */           }
/* 532:    */         }
/* 533:    */         catch (SecurityException e)
/* 534:    */         {
/* 535:561 */           Context.reportWarning("Could not access field " + name + " of class " + this.cl.getName() + " due to lack of privileges.");
/* 536:    */         }
/* 537:    */       }
/* 538:    */     }
/* 539:    */     Map<String, Object> ht;
/* 540:    */     Map<String, BeanProperty> toAdd;
/* 541:569 */     for (int tableCursor = 0; tableCursor != 2; tableCursor++)
/* 542:    */     {
/* 543:570 */       boolean isStatic = tableCursor == 0;
/* 544:571 */       ht = isStatic ? this.staticMembers : this.members;
/* 545:    */       
/* 546:573 */       toAdd = new HashMap();
/* 547:576 */       for (String name : ht.keySet())
/* 548:    */       {
/* 549:578 */         boolean memberIsGetMethod = name.startsWith("get");
/* 550:579 */         boolean memberIsSetMethod = name.startsWith("set");
/* 551:580 */         boolean memberIsIsMethod = name.startsWith("is");
/* 552:581 */         if ((memberIsGetMethod) || (memberIsIsMethod) || (memberIsSetMethod))
/* 553:    */         {
/* 554:584 */           String nameComponent = name.substring(memberIsIsMethod ? 2 : 3);
/* 555:586 */           if (nameComponent.length() != 0)
/* 556:    */           {
/* 557:590 */             String beanPropertyName = nameComponent;
/* 558:591 */             char ch0 = nameComponent.charAt(0);
/* 559:592 */             if (Character.isUpperCase(ch0)) {
/* 560:593 */               if (nameComponent.length() == 1)
/* 561:    */               {
/* 562:594 */                 beanPropertyName = nameComponent.toLowerCase();
/* 563:    */               }
/* 564:    */               else
/* 565:    */               {
/* 566:596 */                 char ch1 = nameComponent.charAt(1);
/* 567:597 */                 if (!Character.isUpperCase(ch1)) {
/* 568:598 */                   beanPropertyName = Character.toLowerCase(ch0) + nameComponent.substring(1);
/* 569:    */                 }
/* 570:    */               }
/* 571:    */             }
/* 572:606 */             if (!toAdd.containsKey(beanPropertyName))
/* 573:    */             {
/* 574:608 */               Object v = ht.get(beanPropertyName);
/* 575:609 */               if ((v == null) || (
/* 576:    */               
/* 577:611 */                 (this.includePrivate) && ((v instanceof Member)) && (Modifier.isPrivate(((Member)v).getModifiers()))))
/* 578:    */               {
/* 579:621 */                 MemberBox getter = null;
/* 580:622 */                 getter = findGetter(isStatic, ht, "get", nameComponent);
/* 581:624 */                 if (getter == null) {
/* 582:625 */                   getter = findGetter(isStatic, ht, "is", nameComponent);
/* 583:    */                 }
/* 584:629 */                 MemberBox setter = null;
/* 585:630 */                 NativeJavaMethod setters = null;
/* 586:631 */                 String setterName = "set".concat(nameComponent);
/* 587:633 */                 if (ht.containsKey(setterName))
/* 588:    */                 {
/* 589:635 */                   Object member = ht.get(setterName);
/* 590:636 */                   if ((member instanceof NativeJavaMethod))
/* 591:    */                   {
/* 592:637 */                     NativeJavaMethod njmSet = (NativeJavaMethod)member;
/* 593:638 */                     if (getter != null)
/* 594:    */                     {
/* 595:641 */                       Class<?> type = getter.method().getReturnType();
/* 596:642 */                       setter = extractSetMethod(type, njmSet.methods, isStatic);
/* 597:    */                     }
/* 598:    */                     else
/* 599:    */                     {
/* 600:646 */                       setter = extractSetMethod(njmSet.methods, isStatic);
/* 601:    */                     }
/* 602:649 */                     if (njmSet.methods.length > 1) {
/* 603:650 */                       setters = njmSet;
/* 604:    */                     }
/* 605:    */                   }
/* 606:    */                 }
/* 607:655 */                 BeanProperty bp = new BeanProperty(getter, setter, setters);
/* 608:    */                 
/* 609:657 */                 toAdd.put(beanPropertyName, bp);
/* 610:    */               }
/* 611:    */             }
/* 612:    */           }
/* 613:    */         }
/* 614:    */       }
/* 615:662 */       for (String key : toAdd.keySet())
/* 616:    */       {
/* 617:663 */         Object value = toAdd.get(key);
/* 618:664 */         ht.put(key, value);
/* 619:    */       }
/* 620:    */     }
/* 621:669 */     Constructor<?>[] constructors = getAccessibleConstructors();
/* 622:670 */     this.ctors = new MemberBox[constructors.length];
/* 623:671 */     for (int i = 0; i != constructors.length; i++) {
/* 624:672 */       this.ctors[i] = new MemberBox(constructors[i]);
/* 625:    */     }
/* 626:    */   }
/* 627:    */   
/* 628:    */   private Constructor<?>[] getAccessibleConstructors()
/* 629:    */   {
/* 630:680 */     if ((this.includePrivate) && (this.cl != ScriptRuntime.ClassClass)) {
/* 631:    */       try
/* 632:    */       {
/* 633:682 */         Constructor<?>[] cons = this.cl.getDeclaredConstructors();
/* 634:683 */         AccessibleObject.setAccessible(cons, true);
/* 635:    */         
/* 636:685 */         return cons;
/* 637:    */       }
/* 638:    */       catch (SecurityException e)
/* 639:    */       {
/* 640:688 */         Context.reportWarning("Could not access constructor  of class " + this.cl.getName() + " due to lack of privileges.");
/* 641:    */       }
/* 642:    */     }
/* 643:693 */     return this.cl.getConstructors();
/* 644:    */   }
/* 645:    */   
/* 646:    */   private Field[] getAccessibleFields()
/* 647:    */   {
/* 648:697 */     if (this.includePrivate) {
/* 649:    */       try
/* 650:    */       {
/* 651:699 */         List<Field> fieldsList = new ArrayList();
/* 652:700 */         Class<?> currentClass = this.cl;
/* 653:702 */         while (currentClass != null)
/* 654:    */         {
/* 655:705 */           Field[] declared = currentClass.getDeclaredFields();
/* 656:706 */           for (int i = 0; i < declared.length; i++)
/* 657:    */           {
/* 658:707 */             declared[i].setAccessible(true);
/* 659:708 */             fieldsList.add(declared[i]);
/* 660:    */           }
/* 661:712 */           currentClass = currentClass.getSuperclass();
/* 662:    */         }
/* 663:715 */         return (Field[])fieldsList.toArray(new Field[fieldsList.size()]);
/* 664:    */       }
/* 665:    */       catch (SecurityException e) {}
/* 666:    */     }
/* 667:720 */     return this.cl.getFields();
/* 668:    */   }
/* 669:    */   
/* 670:    */   private MemberBox findGetter(boolean isStatic, Map<String, Object> ht, String prefix, String propertyName)
/* 671:    */   {
/* 672:726 */     String getterName = prefix.concat(propertyName);
/* 673:727 */     if (ht.containsKey(getterName))
/* 674:    */     {
/* 675:729 */       Object member = ht.get(getterName);
/* 676:730 */       if ((member instanceof NativeJavaMethod))
/* 677:    */       {
/* 678:731 */         NativeJavaMethod njmGet = (NativeJavaMethod)member;
/* 679:732 */         return extractGetMethod(njmGet.methods, isStatic);
/* 680:    */       }
/* 681:    */     }
/* 682:735 */     return null;
/* 683:    */   }
/* 684:    */   
/* 685:    */   private static MemberBox extractGetMethod(MemberBox[] methods, boolean isStatic)
/* 686:    */   {
/* 687:743 */     for (int methodIdx = 0; methodIdx < methods.length; methodIdx++)
/* 688:    */     {
/* 689:744 */       MemberBox method = methods[methodIdx];
/* 690:747 */       if ((method.argTypes.length == 0) && ((!isStatic) || (method.isStatic())))
/* 691:    */       {
/* 692:750 */         Class<?> type = method.method().getReturnType();
/* 693:751 */         if (type == Void.TYPE) {
/* 694:    */           break;
/* 695:    */         }
/* 696:752 */         return method;
/* 697:    */       }
/* 698:    */     }
/* 699:757 */     return null;
/* 700:    */   }
/* 701:    */   
/* 702:    */   private static MemberBox extractSetMethod(Class<?> type, MemberBox[] methods, boolean isStatic)
/* 703:    */   {
/* 704:771 */     for (int pass = 1; pass <= 2; pass++) {
/* 705:772 */       for (int i = 0; i < methods.length; i++)
/* 706:    */       {
/* 707:773 */         MemberBox method = methods[i];
/* 708:774 */         if ((!isStatic) || (method.isStatic()))
/* 709:    */         {
/* 710:775 */           Class<?>[] params = method.argTypes;
/* 711:776 */           if (params.length == 1) {
/* 712:777 */             if (pass == 1)
/* 713:    */             {
/* 714:778 */               if (params[0] == type) {
/* 715:779 */                 return method;
/* 716:    */               }
/* 717:    */             }
/* 718:    */             else
/* 719:    */             {
/* 720:782 */               if (pass != 2) {
/* 721:782 */                 Kit.codeBug();
/* 722:    */               }
/* 723:783 */               if (params[0].isAssignableFrom(type)) {
/* 724:784 */                 return method;
/* 725:    */               }
/* 726:    */             }
/* 727:    */           }
/* 728:    */         }
/* 729:    */       }
/* 730:    */     }
/* 731:791 */     return null;
/* 732:    */   }
/* 733:    */   
/* 734:    */   private static MemberBox extractSetMethod(MemberBox[] methods, boolean isStatic)
/* 735:    */   {
/* 736:798 */     for (int i = 0; i < methods.length; i++)
/* 737:    */     {
/* 738:799 */       MemberBox method = methods[i];
/* 739:800 */       if (((!isStatic) || (method.isStatic())) && 
/* 740:801 */         (method.method().getReturnType() == Void.TYPE) && 
/* 741:802 */         (method.argTypes.length == 1)) {
/* 742:803 */         return method;
/* 743:    */       }
/* 744:    */     }
/* 745:808 */     return null;
/* 746:    */   }
/* 747:    */   
/* 748:    */   Map<String, FieldAndMethods> getFieldAndMethodsObjects(Scriptable scope, Object javaObject, boolean isStatic)
/* 749:    */   {
/* 750:814 */     Map<String, FieldAndMethods> ht = isStatic ? this.staticFieldAndMethods : this.fieldAndMethods;
/* 751:815 */     if (ht == null) {
/* 752:816 */       return null;
/* 753:    */     }
/* 754:817 */     int len = ht.size();
/* 755:818 */     Map<String, FieldAndMethods> result = new HashMap(len);
/* 756:819 */     for (FieldAndMethods fam : ht.values())
/* 757:    */     {
/* 758:820 */       FieldAndMethods famNew = new FieldAndMethods(scope, fam.methods, fam.field);
/* 759:    */       
/* 760:822 */       famNew.javaObject = javaObject;
/* 761:823 */       result.put(fam.field.getName(), famNew);
/* 762:    */     }
/* 763:825 */     return result;
/* 764:    */   }
/* 765:    */   
/* 766:    */   static JavaMembers lookupClass(Scriptable scope, Class<?> dynamicType, Class<?> staticType, boolean includeProtected)
/* 767:    */   {
/* 768:832 */     ClassCache cache = ClassCache.get(scope);
/* 769:833 */     Map<Class<?>, JavaMembers> ct = cache.getClassCacheMap();
/* 770:    */     
/* 771:835 */     Class<?> cl = dynamicType;
/* 772:    */     JavaMembers members;
/* 773:    */     for (;;)
/* 774:    */     {
/* 775:837 */       members = (JavaMembers)ct.get(cl);
/* 776:838 */       if (members != null)
/* 777:    */       {
/* 778:839 */         if (cl != dynamicType) {
/* 779:842 */           ct.put(dynamicType, members);
/* 780:    */         }
/* 781:844 */         return members;
/* 782:    */       }
/* 783:    */       try
/* 784:    */       {
/* 785:847 */         members = new JavaMembers(cache.getAssociatedScope(), cl, includeProtected);
/* 786:    */       }
/* 787:    */       catch (SecurityException e)
/* 788:    */       {
/* 789:855 */         if ((staticType != null) && (staticType.isInterface()))
/* 790:    */         {
/* 791:856 */           cl = staticType;
/* 792:857 */           staticType = null;
/* 793:    */         }
/* 794:    */         else
/* 795:    */         {
/* 796:859 */           Class<?> parent = cl.getSuperclass();
/* 797:860 */           if (parent == null) {
/* 798:861 */             if (cl.isInterface()) {
/* 799:863 */               parent = ScriptRuntime.ObjectClass;
/* 800:    */             } else {
/* 801:865 */               throw e;
/* 802:    */             }
/* 803:    */           }
/* 804:868 */           cl = parent;
/* 805:    */         }
/* 806:    */       }
/* 807:    */     }
/* 808:873 */     if (cache.isCachingEnabled())
/* 809:    */     {
/* 810:874 */       ct.put(cl, members);
/* 811:875 */       if (cl != dynamicType) {
/* 812:878 */         ct.put(dynamicType, members);
/* 813:    */       }
/* 814:    */     }
/* 815:881 */     return members;
/* 816:    */   }
/* 817:    */   
/* 818:    */   RuntimeException reportMemberNotFound(String memberName)
/* 819:    */   {
/* 820:886 */     return Context.reportRuntimeError2("msg.java.member.not.found", this.cl.getName(), memberName);
/* 821:    */   }
/* 822:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.JavaMembers
 * JD-Core Version:    0.7.0.1
 */