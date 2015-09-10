/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.lang.reflect.Constructor;
/*   6:    */ import java.lang.reflect.Member;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.lang.reflect.Modifier;
/*   9:    */ 
/*  10:    */ public class FunctionObject
/*  11:    */   extends BaseFunction
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -5332312783643935019L;
/*  14:    */   private static final short VARARGS_METHOD = -1;
/*  15:    */   private static final short VARARGS_CTOR = -2;
/*  16:    */   private static boolean sawSecurityException;
/*  17:    */   public static final int JAVA_UNSUPPORTED_TYPE = 0;
/*  18:    */   public static final int JAVA_STRING_TYPE = 1;
/*  19:    */   public static final int JAVA_INT_TYPE = 2;
/*  20:    */   public static final int JAVA_BOOLEAN_TYPE = 3;
/*  21:    */   public static final int JAVA_DOUBLE_TYPE = 4;
/*  22:    */   public static final int JAVA_SCRIPTABLE_TYPE = 5;
/*  23:    */   public static final int JAVA_OBJECT_TYPE = 6;
/*  24:    */   MemberBox member;
/*  25:    */   private String functionName;
/*  26:    */   private transient byte[] typeTags;
/*  27:    */   private int parmsLength;
/*  28:    */   private transient boolean hasVoidReturn;
/*  29:    */   private transient int returnTypeTag;
/*  30:    */   private boolean isStatic;
/*  31:    */   
/*  32:    */   public FunctionObject(String name, Member methodOrConstructor, Scriptable scope)
/*  33:    */   {
/*  34:120 */     if ((methodOrConstructor instanceof Constructor))
/*  35:    */     {
/*  36:121 */       this.member = new MemberBox((Constructor)methodOrConstructor);
/*  37:122 */       this.isStatic = true;
/*  38:    */     }
/*  39:    */     else
/*  40:    */     {
/*  41:124 */       this.member = new MemberBox((Method)methodOrConstructor);
/*  42:125 */       this.isStatic = this.member.isStatic();
/*  43:    */     }
/*  44:127 */     String methodName = this.member.getName();
/*  45:128 */     this.functionName = name;
/*  46:129 */     Class<?>[] types = this.member.argTypes;
/*  47:130 */     int arity = types.length;
/*  48:131 */     if ((arity == 4) && ((types[1].isArray()) || (types[2].isArray())))
/*  49:    */     {
/*  50:133 */       if (types[1].isArray())
/*  51:    */       {
/*  52:134 */         if ((!this.isStatic) || (types[0] != ScriptRuntime.ContextClass) || (types[1].getComponentType() != ScriptRuntime.ObjectClass) || (types[2] != ScriptRuntime.FunctionClass) || (types[3] != Boolean.TYPE)) {
/*  53:140 */           throw Context.reportRuntimeError1("msg.varargs.ctor", methodName);
/*  54:    */         }
/*  55:143 */         this.parmsLength = -2;
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59:145 */         if ((!this.isStatic) || (types[0] != ScriptRuntime.ContextClass) || (types[1] != ScriptRuntime.ScriptableClass) || (types[2].getComponentType() != ScriptRuntime.ObjectClass) || (types[3] != ScriptRuntime.FunctionClass)) {
/*  60:151 */           throw Context.reportRuntimeError1("msg.varargs.fun", methodName);
/*  61:    */         }
/*  62:154 */         this.parmsLength = -1;
/*  63:    */       }
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67:157 */       this.parmsLength = arity;
/*  68:158 */       if (arity > 0)
/*  69:    */       {
/*  70:159 */         this.typeTags = new byte[arity];
/*  71:160 */         for (int i = 0; i != arity; i++)
/*  72:    */         {
/*  73:161 */           int tag = getTypeTag(types[i]);
/*  74:162 */           if (tag == 0) {
/*  75:163 */             throw Context.reportRuntimeError2("msg.bad.parms", types[i].getName(), methodName);
/*  76:    */           }
/*  77:166 */           this.typeTags[i] = ((byte)tag);
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:171 */     if (this.member.isMethod())
/*  82:    */     {
/*  83:172 */       Method method = this.member.method();
/*  84:173 */       Class<?> returnType = method.getReturnType();
/*  85:174 */       if (returnType == Void.TYPE) {
/*  86:175 */         this.hasVoidReturn = true;
/*  87:    */       } else {
/*  88:177 */         this.returnTypeTag = getTypeTag(returnType);
/*  89:    */       }
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:180 */       Class<?> ctorType = this.member.getDeclaringClass();
/*  94:181 */       if (!ScriptRuntime.ScriptableClass.isAssignableFrom(ctorType)) {
/*  95:182 */         throw Context.reportRuntimeError1("msg.bad.ctor.return", ctorType.getName());
/*  96:    */       }
/*  97:    */     }
/*  98:187 */     ScriptRuntime.setFunctionProtoAndParent(this, scope);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static int getTypeTag(Class<?> type)
/* 102:    */   {
/* 103:197 */     if (type == ScriptRuntime.StringClass) {
/* 104:198 */       return 1;
/* 105:    */     }
/* 106:199 */     if ((type == ScriptRuntime.IntegerClass) || (type == Integer.TYPE)) {
/* 107:200 */       return 2;
/* 108:    */     }
/* 109:201 */     if ((type == ScriptRuntime.BooleanClass) || (type == Boolean.TYPE)) {
/* 110:202 */       return 3;
/* 111:    */     }
/* 112:203 */     if ((type == ScriptRuntime.DoubleClass) || (type == Double.TYPE)) {
/* 113:204 */       return 4;
/* 114:    */     }
/* 115:205 */     if (ScriptRuntime.ScriptableClass.isAssignableFrom(type)) {
/* 116:206 */       return 5;
/* 117:    */     }
/* 118:207 */     if (type == ScriptRuntime.ObjectClass) {
/* 119:208 */       return 6;
/* 120:    */     }
/* 121:213 */     return 0;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static Object convertArg(Context cx, Scriptable scope, Object arg, int typeTag)
/* 125:    */   {
/* 126:219 */     switch (typeTag)
/* 127:    */     {
/* 128:    */     case 1: 
/* 129:221 */       if ((arg instanceof String)) {
/* 130:222 */         return arg;
/* 131:    */       }
/* 132:223 */       return ScriptRuntime.toString(arg);
/* 133:    */     case 2: 
/* 134:225 */       if ((arg instanceof Integer)) {
/* 135:226 */         return arg;
/* 136:    */       }
/* 137:227 */       return Integer.valueOf(ScriptRuntime.toInt32(arg));
/* 138:    */     case 3: 
/* 139:229 */       if ((arg instanceof Boolean)) {
/* 140:230 */         return arg;
/* 141:    */       }
/* 142:231 */       return ScriptRuntime.toBoolean(arg) ? Boolean.TRUE : Boolean.FALSE;
/* 143:    */     case 4: 
/* 144:234 */       if ((arg instanceof Double)) {
/* 145:235 */         return arg;
/* 146:    */       }
/* 147:236 */       return new Double(ScriptRuntime.toNumber(arg));
/* 148:    */     case 5: 
/* 149:238 */       return ScriptRuntime.toObjectOrNull(cx, arg, scope);
/* 150:    */     case 6: 
/* 151:240 */       return arg;
/* 152:    */     }
/* 153:242 */     throw new IllegalArgumentException();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getArity()
/* 157:    */   {
/* 158:253 */     return this.parmsLength < 0 ? 1 : this.parmsLength;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getLength()
/* 162:    */   {
/* 163:261 */     return getArity();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getFunctionName()
/* 167:    */   {
/* 168:267 */     return this.functionName == null ? "" : this.functionName;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Member getMethodOrConstructor()
/* 172:    */   {
/* 173:275 */     if (this.member.isMethod()) {
/* 174:276 */       return this.member.method();
/* 175:    */     }
/* 176:278 */     return this.member.ctor();
/* 177:    */   }
/* 178:    */   
/* 179:    */   static Method findSingleMethod(Method[] methods, String name)
/* 180:    */   {
/* 181:284 */     Method found = null;
/* 182:285 */     int i = 0;
/* 183:285 */     for (int N = methods.length; i != N; i++)
/* 184:    */     {
/* 185:286 */       Method method = methods[i];
/* 186:287 */       if ((method != null) && (name.equals(method.getName())))
/* 187:    */       {
/* 188:288 */         if (found != null) {
/* 189:289 */           throw Context.reportRuntimeError2("msg.no.overload", name, method.getDeclaringClass().getName());
/* 190:    */         }
/* 191:293 */         found = method;
/* 192:    */       }
/* 193:    */     }
/* 194:296 */     return found;
/* 195:    */   }
/* 196:    */   
/* 197:    */   static Method[] getMethodList(Class<?> clazz)
/* 198:    */   {
/* 199:308 */     Method[] methods = null;
/* 200:    */     try
/* 201:    */     {
/* 202:312 */       if (!sawSecurityException) {
/* 203:313 */         methods = clazz.getDeclaredMethods();
/* 204:    */       }
/* 205:    */     }
/* 206:    */     catch (SecurityException e)
/* 207:    */     {
/* 208:316 */       sawSecurityException = true;
/* 209:    */     }
/* 210:318 */     if (methods == null) {
/* 211:319 */       methods = clazz.getMethods();
/* 212:    */     }
/* 213:321 */     int count = 0;
/* 214:322 */     for (int i = 0; i < methods.length; i++) {
/* 215:323 */       if (sawSecurityException ? methods[i].getDeclaringClass() != clazz : !Modifier.isPublic(methods[i].getModifiers())) {
/* 216:327 */         methods[i] = null;
/* 217:    */       } else {
/* 218:329 */         count++;
/* 219:    */       }
/* 220:    */     }
/* 221:332 */     Method[] result = new Method[count];
/* 222:333 */     int j = 0;
/* 223:334 */     for (int i = 0; i < methods.length; i++) {
/* 224:335 */       if (methods[i] != null) {
/* 225:336 */         result[(j++)] = methods[i];
/* 226:    */       }
/* 227:    */     }
/* 228:338 */     return result;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void addAsConstructor(Scriptable scope, Scriptable prototype)
/* 232:    */   {
/* 233:359 */     initAsConstructor(scope, prototype);
/* 234:360 */     defineProperty(scope, prototype.getClassName(), this, 2);
/* 235:    */   }
/* 236:    */   
/* 237:    */   void initAsConstructor(Scriptable scope, Scriptable prototype)
/* 238:    */   {
/* 239:366 */     ScriptRuntime.setFunctionProtoAndParent(this, scope);
/* 240:367 */     setImmunePrototypeProperty(prototype);
/* 241:    */     
/* 242:369 */     prototype.setParentScope(this);
/* 243:    */     
/* 244:371 */     defineProperty(prototype, "constructor", this, 7);
/* 245:    */     
/* 246:    */ 
/* 247:    */ 
/* 248:375 */     setParentScope(scope);
/* 249:    */   }
/* 250:    */   
/* 251:    */   /**
/* 252:    */    * @deprecated
/* 253:    */    */
/* 254:    */   public static Object convertArg(Context cx, Scriptable scope, Object arg, Class<?> desired)
/* 255:    */   {
/* 256:386 */     int tag = getTypeTag(desired);
/* 257:387 */     if (tag == 0) {
/* 258:388 */       throw Context.reportRuntimeError1("msg.cant.convert", desired.getName());
/* 259:    */     }
/* 260:391 */     return convertArg(cx, scope, arg, tag);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 264:    */   {
/* 265:408 */     boolean checkMethodResult = false;
/* 266:    */     Object result;
/* 267:    */     Object result;
/* 268:410 */     if (this.parmsLength < 0)
/* 269:    */     {
/* 270:411 */       if (this.parmsLength == -1)
/* 271:    */       {
/* 272:412 */         Object[] invokeArgs = { cx, thisObj, args, this };
/* 273:413 */         Object result = this.member.invoke(null, invokeArgs);
/* 274:414 */         checkMethodResult = true;
/* 275:    */       }
/* 276:    */       else
/* 277:    */       {
/* 278:416 */         boolean inNewExpr = thisObj == null;
/* 279:417 */         Boolean b = inNewExpr ? Boolean.TRUE : Boolean.FALSE;
/* 280:418 */         Object[] invokeArgs = { cx, args, this, b };
/* 281:419 */         result = this.member.isCtor() ? this.member.newInstance(invokeArgs) : this.member.invoke(null, invokeArgs);
/* 282:    */       }
/* 283:    */     }
/* 284:    */     else
/* 285:    */     {
/* 286:425 */       if (!this.isStatic)
/* 287:    */       {
/* 288:426 */         Class<?> clazz = this.member.getDeclaringClass();
/* 289:427 */         if ((thisObj instanceof Delegator)) {
/* 290:428 */           thisObj = ((Delegator)thisObj).getDelegee();
/* 291:    */         }
/* 292:430 */         if (!clazz.isInstance(thisObj))
/* 293:    */         {
/* 294:431 */           boolean compatible = false;
/* 295:432 */           if (thisObj == scope)
/* 296:    */           {
/* 297:433 */             Scriptable parentScope = getParentScope();
/* 298:434 */             if (scope != parentScope)
/* 299:    */             {
/* 300:437 */               compatible = clazz.isInstance(parentScope);
/* 301:438 */               if (compatible) {
/* 302:439 */                 thisObj = parentScope;
/* 303:    */               }
/* 304:    */             }
/* 305:    */           }
/* 306:443 */           if (!compatible) {
/* 307:445 */             throw ScriptRuntime.typeError1("msg.incompat.call", this.functionName);
/* 308:    */           }
/* 309:    */         }
/* 310:    */       }
/* 311:    */       Object[] invokeArgs;
/* 312:452 */       if (this.parmsLength == args.length)
/* 313:    */       {
/* 314:455 */         Object[] invokeArgs = args;
/* 315:456 */         for (int i = 0; i != this.parmsLength; i++)
/* 316:    */         {
/* 317:457 */           Object arg = args[i];
/* 318:458 */           Object converted = convertArg(cx, scope, arg, this.typeTags[i]);
/* 319:459 */           if (arg != converted)
/* 320:    */           {
/* 321:460 */             if (invokeArgs == args) {
/* 322:461 */               invokeArgs = (Object[])args.clone();
/* 323:    */             }
/* 324:463 */             invokeArgs[i] = converted;
/* 325:    */           }
/* 326:    */         }
/* 327:    */       }
/* 328:    */       else
/* 329:    */       {
/* 330:    */         Object[] invokeArgs;
/* 331:466 */         if (this.parmsLength == 0)
/* 332:    */         {
/* 333:467 */           invokeArgs = ScriptRuntime.emptyArgs;
/* 334:    */         }
/* 335:    */         else
/* 336:    */         {
/* 337:469 */           invokeArgs = new Object[this.parmsLength];
/* 338:470 */           for (int i = 0; i != this.parmsLength; i++)
/* 339:    */           {
/* 340:471 */             Object arg = i < args.length ? args[i] : Undefined.instance;
/* 341:    */             
/* 342:    */ 
/* 343:474 */             invokeArgs[i] = convertArg(cx, scope, arg, this.typeTags[i]);
/* 344:    */           }
/* 345:    */         }
/* 346:    */       }
/* 347:478 */       if (this.member.isMethod())
/* 348:    */       {
/* 349:479 */         Object result = this.member.invoke(thisObj, invokeArgs);
/* 350:480 */         checkMethodResult = true;
/* 351:    */       }
/* 352:    */       else
/* 353:    */       {
/* 354:482 */         result = this.member.newInstance(invokeArgs);
/* 355:    */       }
/* 356:    */     }
/* 357:487 */     if (checkMethodResult) {
/* 358:488 */       if (this.hasVoidReturn) {
/* 359:489 */         result = Undefined.instance;
/* 360:490 */       } else if (this.returnTypeTag == 0) {
/* 361:491 */         result = cx.getWrapFactory().wrap(cx, scope, result, null);
/* 362:    */       }
/* 363:    */     }
/* 364:499 */     return result;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public Scriptable createObject(Context cx, Scriptable scope)
/* 368:    */   {
/* 369:510 */     if ((this.member.isCtor()) || (this.parmsLength == -2)) {
/* 370:511 */       return null;
/* 371:    */     }
/* 372:    */     Scriptable result;
/* 373:    */     try
/* 374:    */     {
/* 375:515 */       result = (Scriptable)this.member.getDeclaringClass().newInstance();
/* 376:    */     }
/* 377:    */     catch (Exception ex)
/* 378:    */     {
/* 379:517 */       throw Context.throwAsScriptRuntimeEx(ex);
/* 380:    */     }
/* 381:520 */     result.setPrototype(getClassPrototype());
/* 382:521 */     result.setParentScope(getParentScope());
/* 383:522 */     return result;
/* 384:    */   }
/* 385:    */   
/* 386:    */   boolean isVarArgsMethod()
/* 387:    */   {
/* 388:526 */     return this.parmsLength == -1;
/* 389:    */   }
/* 390:    */   
/* 391:    */   boolean isVarArgsConstructor()
/* 392:    */   {
/* 393:530 */     return this.parmsLength == -2;
/* 394:    */   }
/* 395:    */   
/* 396:    */   private void readObject(ObjectInputStream in)
/* 397:    */     throws IOException, ClassNotFoundException
/* 398:    */   {
/* 399:536 */     in.defaultReadObject();
/* 400:537 */     if (this.parmsLength > 0)
/* 401:    */     {
/* 402:538 */       Class<?>[] types = this.member.argTypes;
/* 403:539 */       this.typeTags = new byte[this.parmsLength];
/* 404:540 */       for (int i = 0; i != this.parmsLength; i++) {
/* 405:541 */         this.typeTags[i] = ((byte)getTypeTag(types[i]));
/* 406:    */       }
/* 407:    */     }
/* 408:544 */     if (this.member.isMethod())
/* 409:    */     {
/* 410:545 */       Method method = this.member.method();
/* 411:546 */       Class<?> returnType = method.getReturnType();
/* 412:547 */       if (returnType == Void.TYPE) {
/* 413:548 */         this.hasVoidReturn = true;
/* 414:    */       } else {
/* 415:550 */         this.returnTypeTag = getTypeTag(returnType);
/* 416:    */       }
/* 417:    */     }
/* 418:    */   }
/* 419:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.FunctionObject
 * JD-Core Version:    0.7.0.1
 */