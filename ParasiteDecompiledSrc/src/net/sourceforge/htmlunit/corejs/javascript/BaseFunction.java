/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class BaseFunction
/*   4:    */   extends IdScriptableObject
/*   5:    */   implements Function
/*   6:    */ {
/*   7:    */   static final long serialVersionUID = 5311394446546053859L;
/*   8: 54 */   private static final Object FUNCTION_TAG = "Function";
/*   9:    */   private static final int Id_length = 1;
/*  10:    */   private static final int Id_arity = 2;
/*  11:    */   private static final int Id_name = 3;
/*  12:    */   private static final int Id_prototype = 4;
/*  13:    */   private static final int Id_arguments = 5;
/*  14:    */   private static final int MAX_INSTANCE_ID = 5;
/*  15:    */   private static final int Id_constructor = 1;
/*  16:    */   private static final int Id_toString = 2;
/*  17:    */   private static final int Id_toSource = 3;
/*  18:    */   private static final int Id_apply = 4;
/*  19:    */   private static final int Id_call = 5;
/*  20:    */   private static final int Id_bind = 6;
/*  21:    */   private static final int MAX_PROTOTYPE_ID = 6;
/*  22:    */   private Object prototypeProperty;
/*  23:    */   
/*  24:    */   static void init(Scriptable scope, boolean sealed)
/*  25:    */   {
/*  26: 58 */     BaseFunction obj = new BaseFunction();
/*  27:    */     
/*  28: 60 */     obj.prototypePropertyAttributes = 7;
/*  29: 61 */     obj.exportAsJSClass(6, scope, sealed);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public BaseFunction() {}
/*  33:    */   
/*  34:    */   public BaseFunction(Scriptable scope, Scriptable prototype)
/*  35:    */   {
/*  36: 70 */     super(scope, prototype);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getClassName()
/*  40:    */   {
/*  41: 75 */     return "Function";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getTypeOf()
/*  45:    */   {
/*  46: 86 */     return avoidObjectDetection() ? "undefined" : "function";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasInstance(Scriptable instance)
/*  50:    */   {
/*  51:106 */     Object protoProp = ScriptableObject.getProperty(this, "prototype");
/*  52:107 */     if ((protoProp instanceof Scriptable)) {
/*  53:108 */       return ScriptRuntime.jsDelegatesTo(instance, (Scriptable)protoProp);
/*  54:    */     }
/*  55:110 */     throw ScriptRuntime.typeError1("msg.instanceof.bad.prototype", getFunctionName());
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected int getMaxInstanceId()
/*  59:    */   {
/*  60:128 */     return 5;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected int findInstanceIdInfo(String s)
/*  64:    */   {
/*  65:136 */     int id = 0;String X = null;
/*  66:137 */     switch (s.length())
/*  67:    */     {
/*  68:    */     case 4: 
/*  69:138 */       X = "name";id = 3; break;
/*  70:    */     case 5: 
/*  71:139 */       X = "arity";id = 2; break;
/*  72:    */     case 6: 
/*  73:140 */       X = "length";id = 1; break;
/*  74:    */     case 9: 
/*  75:141 */       int c = s.charAt(0);
/*  76:142 */       if (c == 97)
/*  77:    */       {
/*  78:142 */         X = "arguments";id = 5;
/*  79:    */       }
/*  80:143 */       else if (c == 112)
/*  81:    */       {
/*  82:143 */         X = "prototype";id = 4;
/*  83:    */       }
/*  84:    */       break;
/*  85:    */     }
/*  86:146 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/*  87:146 */       id = 0;
/*  88:    */     }
/*  89:152 */     if (id == 0) {
/*  90:152 */       return super.findInstanceIdInfo(s);
/*  91:    */     }
/*  92:    */     int attr;
/*  93:155 */     switch (id)
/*  94:    */     {
/*  95:    */     case 1: 
/*  96:    */     case 2: 
/*  97:    */     case 3: 
/*  98:159 */       attr = 7;
/*  99:160 */       break;
/* 100:    */     case 4: 
/* 101:162 */       attr = this.prototypePropertyAttributes;
/* 102:163 */       break;
/* 103:    */     case 5: 
/* 104:165 */       attr = 6;
/* 105:166 */       break;
/* 106:    */     default: 
/* 107:167 */       throw new IllegalStateException();
/* 108:    */     }
/* 109:169 */     return instanceIdInfo(attr, id);
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected String getInstanceIdName(int id)
/* 113:    */   {
/* 114:175 */     switch (id)
/* 115:    */     {
/* 116:    */     case 1: 
/* 117:176 */       return "length";
/* 118:    */     case 2: 
/* 119:177 */       return "arity";
/* 120:    */     case 3: 
/* 121:178 */       return "name";
/* 122:    */     case 4: 
/* 123:179 */       return "prototype";
/* 124:    */     case 5: 
/* 125:180 */       return "arguments";
/* 126:    */     }
/* 127:182 */     return super.getInstanceIdName(id);
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected Object getInstanceIdValue(int id)
/* 131:    */   {
/* 132:188 */     switch (id)
/* 133:    */     {
/* 134:    */     case 1: 
/* 135:189 */       return ScriptRuntime.wrapInt(getLength());
/* 136:    */     case 2: 
/* 137:190 */       return ScriptRuntime.wrapInt(getArity());
/* 138:    */     case 3: 
/* 139:191 */       return getFunctionName();
/* 140:    */     case 4: 
/* 141:192 */       return getPrototypeProperty();
/* 142:    */     case 5: 
/* 143:193 */       return getArguments();
/* 144:    */     }
/* 145:195 */     return super.getInstanceIdValue(id);
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected void setInstanceIdValue(int id, Object value)
/* 149:    */   {
/* 150:201 */     switch (id)
/* 151:    */     {
/* 152:    */     case 4: 
/* 153:203 */       if ((this.prototypePropertyAttributes & 0x1) == 0) {
/* 154:204 */         this.prototypeProperty = (value != null ? value : UniqueTag.NULL_VALUE);
/* 155:    */       }
/* 156:207 */       return;
/* 157:    */     case 5: 
/* 158:209 */       if (value == NOT_FOUND) {
/* 159:211 */         Kit.codeBug();
/* 160:    */       }
/* 161:213 */       defaultPut("arguments", value);
/* 162:214 */       return;
/* 163:    */     case 1: 
/* 164:    */     case 2: 
/* 165:    */     case 3: 
/* 166:218 */       return;
/* 167:    */     }
/* 168:220 */     super.setInstanceIdValue(id, value);
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected void fillConstructorProperties(IdFunctionObject ctor)
/* 172:    */   {
/* 173:229 */     ctor.setPrototype(this);
/* 174:230 */     super.fillConstructorProperties(ctor);
/* 175:    */   }
/* 176:    */   
/* 177:    */   protected void initPrototypeId(int id)
/* 178:    */   {
/* 179:    */     int arity;
/* 180:    */     String s;
/* 181:238 */     switch (id)
/* 182:    */     {
/* 183:    */     case 1: 
/* 184:239 */       arity = 1;s = "constructor"; break;
/* 185:    */     case 2: 
/* 186:240 */       arity = 1;s = "toString"; break;
/* 187:    */     case 3: 
/* 188:241 */       arity = 1;s = "toSource"; break;
/* 189:    */     case 4: 
/* 190:242 */       arity = 2;s = "apply"; break;
/* 191:    */     case 5: 
/* 192:243 */       arity = 1;s = "call"; break;
/* 193:    */     case 6: 
/* 194:244 */       arity = 1;s = "bind"; break;
/* 195:    */     default: 
/* 196:245 */       throw new IllegalArgumentException(String.valueOf(id));
/* 197:    */     }
/* 198:247 */     initPrototypeMethod(FUNCTION_TAG, id, s, arity);
/* 199:    */   }
/* 200:    */   
/* 201:    */   static boolean isApply(IdFunctionObject f)
/* 202:    */   {
/* 203:251 */     return (f.hasTag(FUNCTION_TAG)) && (f.methodId() == 4);
/* 204:    */   }
/* 205:    */   
/* 206:    */   static boolean isApplyOrCall(IdFunctionObject f)
/* 207:    */   {
/* 208:255 */     if (f.hasTag(FUNCTION_TAG)) {
/* 209:256 */       switch (f.methodId())
/* 210:    */       {
/* 211:    */       case 4: 
/* 212:    */       case 5: 
/* 213:259 */         return true;
/* 214:    */       }
/* 215:    */     }
/* 216:262 */     return false;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 220:    */   {
/* 221:269 */     if (!f.hasTag(FUNCTION_TAG)) {
/* 222:270 */       return super.execIdCall(f, cx, scope, thisObj, args);
/* 223:    */     }
/* 224:272 */     int id = f.methodId();
/* 225:273 */     switch (id)
/* 226:    */     {
/* 227:    */     case 1: 
/* 228:275 */       return jsConstructor(cx, scope, args);
/* 229:    */     case 2: 
/* 230:278 */       BaseFunction realf = realFunction(thisObj, f);
/* 231:279 */       int indent = ScriptRuntime.toInt32(args, 0);
/* 232:280 */       return realf.decompile(indent, 0);
/* 233:    */     case 3: 
/* 234:284 */       BaseFunction realf = realFunction(thisObj, f);
/* 235:285 */       int indent = 0;
/* 236:286 */       int flags = 2;
/* 237:287 */       if (args.length != 0)
/* 238:    */       {
/* 239:288 */         indent = ScriptRuntime.toInt32(args[0]);
/* 240:289 */         if (indent >= 0) {
/* 241:290 */           flags = 0;
/* 242:    */         } else {
/* 243:292 */           indent = 0;
/* 244:    */         }
/* 245:    */       }
/* 246:295 */       return realf.decompile(indent, flags);
/* 247:    */     case 4: 
/* 248:    */     case 5: 
/* 249:300 */       return ScriptRuntime.applyOrCall(id == 4, cx, scope, thisObj, args);
/* 250:    */     case 6: 
/* 251:304 */       if (!(thisObj instanceof Callable)) {
/* 252:305 */         throw ScriptRuntime.notFunctionError(thisObj);
/* 253:    */       }
/* 254:307 */       Callable targetFunction = (Callable)thisObj;
/* 255:308 */       int argc = args.length;
/* 256:    */       Scriptable boundThis;
/* 257:    */       Object[] boundArgs;
/* 258:311 */       if (argc > 0)
/* 259:    */       {
/* 260:312 */         Scriptable boundThis = ScriptRuntime.toObjectOrNull(cx, args[0], scope);
/* 261:313 */         Object[] boundArgs = new Object[argc - 1];
/* 262:314 */         System.arraycopy(args, 1, boundArgs, 0, argc - 1);
/* 263:    */       }
/* 264:    */       else
/* 265:    */       {
/* 266:316 */         boundThis = null;
/* 267:317 */         boundArgs = ScriptRuntime.emptyArgs;
/* 268:    */       }
/* 269:319 */       return new BoundFunction(cx, scope, targetFunction, boundThis, boundArgs);
/* 270:    */     }
/* 271:321 */     throw new IllegalArgumentException(String.valueOf(id));
/* 272:    */   }
/* 273:    */   
/* 274:    */   private BaseFunction realFunction(Scriptable thisObj, IdFunctionObject f)
/* 275:    */   {
/* 276:326 */     Object x = thisObj.getDefaultValue(ScriptRuntime.FunctionClass);
/* 277:327 */     if ((x instanceof BaseFunction)) {
/* 278:328 */       return (BaseFunction)x;
/* 279:    */     }
/* 280:330 */     throw ScriptRuntime.typeError1("msg.incompat.call", f.getFunctionName());
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setImmunePrototypeProperty(Object value)
/* 284:    */   {
/* 285:340 */     if ((this.prototypePropertyAttributes & 0x1) != 0) {
/* 286:341 */       throw new IllegalStateException();
/* 287:    */     }
/* 288:343 */     this.prototypeProperty = (value != null ? value : UniqueTag.NULL_VALUE);
/* 289:344 */     this.prototypePropertyAttributes = 7;
/* 290:    */   }
/* 291:    */   
/* 292:    */   protected Scriptable getClassPrototype()
/* 293:    */   {
/* 294:349 */     Object protoVal = getPrototypeProperty();
/* 295:350 */     if ((protoVal instanceof Scriptable)) {
/* 296:351 */       return (Scriptable)protoVal;
/* 297:    */     }
/* 298:353 */     return ScriptableObject.getObjectPrototype(this);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 302:    */   {
/* 303:362 */     return Undefined.instance;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/* 307:    */   {
/* 308:367 */     Scriptable result = createObject(cx, scope);
/* 309:368 */     if (result != null)
/* 310:    */     {
/* 311:369 */       Object val = call(cx, scope, result, args);
/* 312:370 */       if ((val instanceof Scriptable)) {
/* 313:371 */         result = (Scriptable)val;
/* 314:    */       }
/* 315:    */     }
/* 316:    */     else
/* 317:    */     {
/* 318:374 */       Object val = call(cx, scope, null, args);
/* 319:375 */       if (!(val instanceof Scriptable)) {
/* 320:378 */         throw new IllegalStateException("Bad implementaion of call as constructor, name=" + getFunctionName() + " in " + getClass().getName());
/* 321:    */       }
/* 322:382 */       result = (Scriptable)val;
/* 323:383 */       if (result.getPrototype() == null) {
/* 324:384 */         result.setPrototype(getClassPrototype());
/* 325:    */       }
/* 326:386 */       if (result.getParentScope() == null)
/* 327:    */       {
/* 328:387 */         Scriptable parent = getParentScope();
/* 329:388 */         if (result != parent) {
/* 330:389 */           result.setParentScope(parent);
/* 331:    */         }
/* 332:    */       }
/* 333:    */     }
/* 334:393 */     return result;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public Scriptable createObject(Context cx, Scriptable scope)
/* 338:    */   {
/* 339:408 */     Scriptable newInstance = new NativeObject();
/* 340:409 */     newInstance.setPrototype(getClassPrototype());
/* 341:410 */     newInstance.setParentScope(getParentScope());
/* 342:411 */     return newInstance;
/* 343:    */   }
/* 344:    */   
/* 345:    */   String decompile(int indent, int flags)
/* 346:    */   {
/* 347:424 */     StringBuffer sb = new StringBuffer();
/* 348:425 */     boolean justbody = 0 != (flags & 0x1);
/* 349:426 */     if (!justbody)
/* 350:    */     {
/* 351:427 */       sb.append("function ");
/* 352:428 */       sb.append(getFunctionName());
/* 353:429 */       sb.append("() {\n\t");
/* 354:    */     }
/* 355:431 */     sb.append("[native code, arity=");
/* 356:432 */     sb.append(getArity());
/* 357:433 */     sb.append("]\n");
/* 358:434 */     if (!justbody) {
/* 359:435 */       sb.append("}\n");
/* 360:    */     }
/* 361:437 */     return sb.toString();
/* 362:    */   }
/* 363:    */   
/* 364:    */   public int getArity()
/* 365:    */   {
/* 366:440 */     return 0;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public int getLength()
/* 370:    */   {
/* 371:442 */     return 0;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public String getFunctionName()
/* 375:    */   {
/* 376:446 */     return "";
/* 377:    */   }
/* 378:    */   
/* 379:    */   final Object getPrototypeProperty()
/* 380:    */   {
/* 381:450 */     Object result = this.prototypeProperty;
/* 382:451 */     if (result == null) {
/* 383:452 */       synchronized (this)
/* 384:    */       {
/* 385:453 */         result = this.prototypeProperty;
/* 386:454 */         if (result == null)
/* 387:    */         {
/* 388:455 */           setupDefaultPrototype();
/* 389:456 */           result = this.prototypeProperty;
/* 390:    */         }
/* 391:    */       }
/* 392:460 */     } else if (result == UniqueTag.NULL_VALUE) {
/* 393:460 */       result = null;
/* 394:    */     }
/* 395:461 */     return result;
/* 396:    */   }
/* 397:    */   
/* 398:    */   private void setupDefaultPrototype()
/* 399:    */   {
/* 400:466 */     NativeObject obj = new NativeObject();
/* 401:467 */     int attr = 2;
/* 402:468 */     obj.defineProperty("constructor", this, 2);
/* 403:    */     
/* 404:    */ 
/* 405:    */ 
/* 406:472 */     this.prototypeProperty = obj;
/* 407:473 */     Scriptable proto = getObjectPrototype(this);
/* 408:474 */     if (proto != obj) {
/* 409:476 */       obj.setPrototype(proto);
/* 410:    */     }
/* 411:    */   }
/* 412:    */   
/* 413:    */   private Object getArguments()
/* 414:    */   {
/* 415:485 */     Object value = defaultGet("arguments");
/* 416:486 */     if (value != NOT_FOUND) {
/* 417:492 */       return value;
/* 418:    */     }
/* 419:494 */     Context cx = Context.getContext();
/* 420:495 */     NativeCall activation = ScriptRuntime.findFunctionActivation(cx, this);
/* 421:496 */     return activation == null ? null : activation.get("arguments", activation);
/* 422:    */   }
/* 423:    */   
/* 424:    */   private static Object jsConstructor(Context cx, Scriptable scope, Object[] args)
/* 425:    */   {
/* 426:504 */     int arglen = args.length;
/* 427:505 */     StringBuffer sourceBuf = new StringBuffer();
/* 428:    */     
/* 429:507 */     sourceBuf.append("function ");
/* 430:514 */     if (cx.getLanguageVersion() != 120) {
/* 431:515 */       sourceBuf.append("anonymous");
/* 432:    */     }
/* 433:517 */     sourceBuf.append('(');
/* 434:520 */     for (int i = 0; i < arglen - 1; i++)
/* 435:    */     {
/* 436:521 */       if (i > 0) {
/* 437:522 */         sourceBuf.append(',');
/* 438:    */       }
/* 439:524 */       sourceBuf.append(ScriptRuntime.toString(args[i]));
/* 440:    */     }
/* 441:526 */     sourceBuf.append(") {");
/* 442:527 */     if (arglen != 0)
/* 443:    */     {
/* 444:529 */       String funBody = ScriptRuntime.toString(args[(arglen - 1)]);
/* 445:530 */       sourceBuf.append(funBody);
/* 446:    */     }
/* 447:532 */     sourceBuf.append("\n}");
/* 448:533 */     String source = sourceBuf.toString();
/* 449:    */     
/* 450:535 */     int[] linep = new int[1];
/* 451:536 */     String filename = Context.getSourcePositionFromStack(linep);
/* 452:537 */     if (filename == null)
/* 453:    */     {
/* 454:538 */       filename = "<eval'ed string>";
/* 455:539 */       linep[0] = 1;
/* 456:    */     }
/* 457:542 */     String sourceURI = ScriptRuntime.makeUrlForGeneratedScript(false, filename, linep[0]);
/* 458:    */     
/* 459:    */ 
/* 460:545 */     Scriptable global = ScriptableObject.getTopLevelScope(scope);
/* 461:    */     
/* 462:    */ 
/* 463:548 */     ErrorReporter reporter = DefaultErrorReporter.forEval(cx.getErrorReporter());
/* 464:    */     
/* 465:550 */     Evaluator evaluator = Context.createInterpreter();
/* 466:551 */     if (evaluator == null) {
/* 467:552 */       throw new JavaScriptException("Interpreter not present", filename, linep[0]);
/* 468:    */     }
/* 469:558 */     return cx.compileFunction(global, source, evaluator, reporter, sourceURI, 1, null);
/* 470:    */   }
/* 471:    */   
/* 472:    */   protected int findPrototypeId(String s)
/* 473:    */   {
/* 474:568 */     int id = 0;String X = null;
/* 475:    */     int c;
/* 476:569 */     switch (s.length())
/* 477:    */     {
/* 478:    */     case 4: 
/* 479:570 */       c = s.charAt(0);
/* 480:571 */       if (c == 98)
/* 481:    */       {
/* 482:571 */         X = "bind";id = 6;
/* 483:    */       }
/* 484:572 */       else if (c == 99)
/* 485:    */       {
/* 486:572 */         X = "call";id = 5;
/* 487:    */       }
/* 488:    */       break;
/* 489:    */     case 5: 
/* 490:574 */       X = "apply";id = 4; break;
/* 491:    */     case 8: 
/* 492:575 */       c = s.charAt(3);
/* 493:576 */       if (c == 111)
/* 494:    */       {
/* 495:576 */         X = "toSource";id = 3;
/* 496:    */       }
/* 497:577 */       else if (c == 116)
/* 498:    */       {
/* 499:577 */         X = "toString";id = 2;
/* 500:    */       }
/* 501:    */       break;
/* 502:    */     case 11: 
/* 503:579 */       X = "constructor";id = 1; break;
/* 504:    */     }
/* 505:581 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 506:581 */       id = 0;
/* 507:    */     }
/* 508:585 */     return id;
/* 509:    */   }
/* 510:    */   
/* 511:604 */   private int prototypePropertyAttributes = 6;
/* 512:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.BaseFunction
 * JD-Core Version:    0.7.0.1
 */