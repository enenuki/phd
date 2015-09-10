/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.AbstractSet;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ 
/*  11:    */ public class NativeObject
/*  12:    */   extends IdScriptableObject
/*  13:    */   implements Map
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = -6345305608474346996L;
/*  16: 60 */   private static final Object OBJECT_TAG = "Object";
/*  17:    */   private static final int ConstructorId_getPrototypeOf = -1;
/*  18:    */   private static final int ConstructorId_keys = -2;
/*  19:    */   private static final int ConstructorId_getOwnPropertyNames = -3;
/*  20:    */   private static final int ConstructorId_getOwnPropertyDescriptor = -4;
/*  21:    */   private static final int ConstructorId_defineProperty = -5;
/*  22:    */   private static final int ConstructorId_isExtensible = -6;
/*  23:    */   private static final int ConstructorId_preventExtensions = -7;
/*  24:    */   private static final int ConstructorId_defineProperties = -8;
/*  25:    */   private static final int ConstructorId_create = -9;
/*  26:    */   private static final int ConstructorId_isSealed = -10;
/*  27:    */   private static final int ConstructorId_isFrozen = -11;
/*  28:    */   private static final int ConstructorId_seal = -12;
/*  29:    */   private static final int ConstructorId_freeze = -13;
/*  30:    */   private static final int Id_constructor = 1;
/*  31:    */   private static final int Id_toString = 2;
/*  32:    */   private static final int Id_toLocaleString = 3;
/*  33:    */   private static final int Id_valueOf = 4;
/*  34:    */   private static final int Id_hasOwnProperty = 5;
/*  35:    */   private static final int Id_propertyIsEnumerable = 6;
/*  36:    */   private static final int Id_isPrototypeOf = 7;
/*  37:    */   private static final int Id_toSource = 8;
/*  38:    */   private static final int Id___defineGetter__ = 9;
/*  39:    */   private static final int Id___defineSetter__ = 10;
/*  40:    */   private static final int Id___lookupGetter__ = 11;
/*  41:    */   private static final int Id___lookupSetter__ = 12;
/*  42:    */   private static final int MAX_PROTOTYPE_ID = 12;
/*  43:    */   
/*  44:    */   static void init(Scriptable scope, boolean sealed)
/*  45:    */   {
/*  46: 64 */     NativeObject obj = new NativeObject();
/*  47: 65 */     obj.exportAsJSClass(12, scope, sealed);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getClassName()
/*  51:    */   {
/*  52: 71 */     return "Object";
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toString()
/*  56:    */   {
/*  57: 77 */     return ScriptRuntime.defaultObjectToString(this);
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected void fillConstructorProperties(IdFunctionObject ctor)
/*  61:    */   {
/*  62: 83 */     addIdFunctionProperty(ctor, OBJECT_TAG, -1, "getPrototypeOf", 1);
/*  63:    */     
/*  64: 85 */     addIdFunctionProperty(ctor, OBJECT_TAG, -2, "keys", 1);
/*  65:    */     
/*  66: 87 */     addIdFunctionProperty(ctor, OBJECT_TAG, -3, "getOwnPropertyNames", 1);
/*  67:    */     
/*  68: 89 */     addIdFunctionProperty(ctor, OBJECT_TAG, -4, "getOwnPropertyDescriptor", 2);
/*  69:    */     
/*  70: 91 */     addIdFunctionProperty(ctor, OBJECT_TAG, -5, "defineProperty", 3);
/*  71:    */     
/*  72: 93 */     addIdFunctionProperty(ctor, OBJECT_TAG, -6, "isExtensible", 1);
/*  73:    */     
/*  74: 95 */     addIdFunctionProperty(ctor, OBJECT_TAG, -7, "preventExtensions", 1);
/*  75:    */     
/*  76: 97 */     addIdFunctionProperty(ctor, OBJECT_TAG, -8, "defineProperties", 2);
/*  77:    */     
/*  78: 99 */     addIdFunctionProperty(ctor, OBJECT_TAG, -9, "create", 2);
/*  79:    */     
/*  80:101 */     addIdFunctionProperty(ctor, OBJECT_TAG, -10, "isSealed", 1);
/*  81:    */     
/*  82:103 */     addIdFunctionProperty(ctor, OBJECT_TAG, -11, "isFrozen", 1);
/*  83:    */     
/*  84:105 */     addIdFunctionProperty(ctor, OBJECT_TAG, -12, "seal", 1);
/*  85:    */     
/*  86:107 */     addIdFunctionProperty(ctor, OBJECT_TAG, -13, "freeze", 1);
/*  87:    */     
/*  88:109 */     super.fillConstructorProperties(ctor);
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void initPrototypeId(int id)
/*  92:    */   {
/*  93:    */     int arity;
/*  94:    */     String s;
/*  95:117 */     switch (id)
/*  96:    */     {
/*  97:    */     case 1: 
/*  98:118 */       arity = 1;s = "constructor"; break;
/*  99:    */     case 2: 
/* 100:119 */       arity = 0;s = "toString"; break;
/* 101:    */     case 3: 
/* 102:120 */       arity = 0;s = "toLocaleString"; break;
/* 103:    */     case 4: 
/* 104:121 */       arity = 0;s = "valueOf"; break;
/* 105:    */     case 5: 
/* 106:122 */       arity = 1;s = "hasOwnProperty"; break;
/* 107:    */     case 6: 
/* 108:124 */       arity = 1;s = "propertyIsEnumerable"; break;
/* 109:    */     case 7: 
/* 110:125 */       arity = 1;s = "isPrototypeOf"; break;
/* 111:    */     case 8: 
/* 112:126 */       arity = 0;s = "toSource"; break;
/* 113:    */     case 9: 
/* 114:128 */       arity = 2;s = "__defineGetter__"; break;
/* 115:    */     case 10: 
/* 116:130 */       arity = 2;s = "__defineSetter__"; break;
/* 117:    */     case 11: 
/* 118:132 */       arity = 1;s = "__lookupGetter__"; break;
/* 119:    */     case 12: 
/* 120:134 */       arity = 1;s = "__lookupSetter__"; break;
/* 121:    */     default: 
/* 122:135 */       throw new IllegalArgumentException(String.valueOf(id));
/* 123:    */     }
/* 124:137 */     initPrototypeMethod(OBJECT_TAG, id, s, arity);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 128:    */   {
/* 129:144 */     if (!f.hasTag(OBJECT_TAG)) {
/* 130:145 */       return super.execIdCall(f, cx, scope, thisObj, args);
/* 131:    */     }
/* 132:147 */     int id = f.methodId();
/* 133:148 */     switch (id)
/* 134:    */     {
/* 135:    */     case 1: 
/* 136:150 */       if (thisObj != null) {
/* 137:152 */         return f.construct(cx, scope, args);
/* 138:    */       }
/* 139:154 */       if ((args.length == 0) || (args[0] == null) || (args[0] == Undefined.instance)) {
/* 140:157 */         return new NativeObject();
/* 141:    */       }
/* 142:159 */       return ScriptRuntime.toObject(cx, scope, args[0]);
/* 143:    */     case 2: 
/* 144:    */     case 3: 
/* 145:164 */       if (cx.hasFeature(4))
/* 146:    */       {
/* 147:165 */         String s = ScriptRuntime.defaultObjectToSource(cx, scope, thisObj, args);
/* 148:    */         
/* 149:167 */         int L = s.length();
/* 150:168 */         if ((L != 0) && (s.charAt(0) == '(') && (s.charAt(L - 1) == ')')) {
/* 151:170 */           s = s.substring(1, L - 1);
/* 152:    */         }
/* 153:172 */         return s;
/* 154:    */       }
/* 155:174 */       return ScriptRuntime.defaultObjectToString(thisObj);
/* 156:    */     case 4: 
/* 157:178 */       return thisObj;
/* 158:    */     case 5: 
/* 159:    */       boolean result;
/* 160:    */       boolean result;
/* 161:182 */       if (args.length == 0)
/* 162:    */       {
/* 163:183 */         result = false;
/* 164:    */       }
/* 165:    */       else
/* 166:    */       {
/* 167:185 */         String s = ScriptRuntime.toStringIdOrIndex(cx, args[0]);
/* 168:    */         boolean result;
/* 169:186 */         if (s == null)
/* 170:    */         {
/* 171:187 */           int index = ScriptRuntime.lastIndexResult(cx);
/* 172:188 */           result = thisObj.has(index, thisObj);
/* 173:    */         }
/* 174:    */         else
/* 175:    */         {
/* 176:190 */           result = thisObj.has(s, thisObj);
/* 177:    */         }
/* 178:    */       }
/* 179:193 */       return ScriptRuntime.wrapBoolean(result);
/* 180:    */     case 6: 
/* 181:    */       boolean result;
/* 182:    */       boolean result;
/* 183:198 */       if (args.length == 0)
/* 184:    */       {
/* 185:199 */         result = false;
/* 186:    */       }
/* 187:    */       else
/* 188:    */       {
/* 189:201 */         String s = ScriptRuntime.toStringIdOrIndex(cx, args[0]);
/* 190:202 */         if (s == null)
/* 191:    */         {
/* 192:203 */           int index = ScriptRuntime.lastIndexResult(cx);
/* 193:204 */           boolean result = thisObj.has(index, thisObj);
/* 194:205 */           if ((result) && ((thisObj instanceof ScriptableObject)))
/* 195:    */           {
/* 196:206 */             ScriptableObject so = (ScriptableObject)thisObj;
/* 197:207 */             int attrs = so.getAttributes(index);
/* 198:208 */             result = (attrs & 0x2) == 0;
/* 199:    */           }
/* 200:    */         }
/* 201:    */         else
/* 202:    */         {
/* 203:211 */           result = thisObj.has(s, thisObj);
/* 204:212 */           if ((result) && ((thisObj instanceof ScriptableObject)))
/* 205:    */           {
/* 206:213 */             ScriptableObject so = (ScriptableObject)thisObj;
/* 207:214 */             int attrs = so.getAttributes(s);
/* 208:215 */             result = (attrs & 0x2) == 0;
/* 209:    */           }
/* 210:    */         }
/* 211:    */       }
/* 212:219 */       return ScriptRuntime.wrapBoolean(result);
/* 213:    */     case 7: 
/* 214:223 */       boolean result = false;
/* 215:224 */       if ((args.length != 0) && ((args[0] instanceof Scriptable)))
/* 216:    */       {
/* 217:225 */         Scriptable v = (Scriptable)args[0];
/* 218:    */         do
/* 219:    */         {
/* 220:227 */           v = v.getPrototype();
/* 221:228 */           if (v == thisObj)
/* 222:    */           {
/* 223:229 */             result = true;
/* 224:230 */             break;
/* 225:    */           }
/* 226:232 */         } while (v != null);
/* 227:    */       }
/* 228:234 */       return ScriptRuntime.wrapBoolean(result);
/* 229:    */     case 8: 
/* 230:238 */       return ScriptRuntime.defaultObjectToSource(cx, scope, thisObj, args);
/* 231:    */     case 9: 
/* 232:    */     case 10: 
/* 233:243 */       if ((args.length < 2) || (!(args[1] instanceof Callable)))
/* 234:    */       {
/* 235:244 */         Object badArg = args.length >= 2 ? args[1] : Undefined.instance;
/* 236:    */         
/* 237:246 */         throw ScriptRuntime.notFunctionError(badArg);
/* 238:    */       }
/* 239:248 */       if (!(thisObj instanceof ScriptableObject)) {
/* 240:249 */         throw Context.reportRuntimeError2("msg.extend.scriptable", thisObj.getClass().getName(), String.valueOf(args[0]));
/* 241:    */       }
/* 242:254 */       ScriptableObject so = (ScriptableObject)thisObj;
/* 243:255 */       String name = ScriptRuntime.toStringIdOrIndex(cx, args[0]);
/* 244:256 */       int index = name != null ? 0 : ScriptRuntime.lastIndexResult(cx);
/* 245:    */       
/* 246:258 */       Callable getterOrSetter = (Callable)args[1];
/* 247:259 */       boolean isSetter = id == 10;
/* 248:260 */       so.setGetterOrSetter(name, index, getterOrSetter, isSetter);
/* 249:261 */       if ((so instanceof NativeArray)) {
/* 250:262 */         ((NativeArray)so).setDenseOnly(false);
/* 251:    */       }
/* 252:264 */       return Undefined.instance;
/* 253:    */     case 11: 
/* 254:    */     case 12: 
/* 255:269 */       if ((args.length < 1) || (!(thisObj instanceof ScriptableObject))) {
/* 256:271 */         return Undefined.instance;
/* 257:    */       }
/* 258:273 */       ScriptableObject so = (ScriptableObject)thisObj;
/* 259:274 */       String name = ScriptRuntime.toStringIdOrIndex(cx, args[0]);
/* 260:275 */       int index = name != null ? 0 : ScriptRuntime.lastIndexResult(cx);
/* 261:    */       
/* 262:277 */       boolean isSetter = id == 12;
/* 263:    */       Object gs;
/* 264:    */       for (;;)
/* 265:    */       {
/* 266:280 */         gs = so.getGetterOrSetter(name, index, isSetter);
/* 267:281 */         if (gs != null) {
/* 268:    */           break;
/* 269:    */         }
/* 270:285 */         Scriptable v = so.getPrototype();
/* 271:286 */         if (v == null) {
/* 272:    */           break;
/* 273:    */         }
/* 274:288 */         if (!(v instanceof ScriptableObject)) {
/* 275:    */           break;
/* 276:    */         }
/* 277:289 */         so = (ScriptableObject)v;
/* 278:    */       }
/* 279:293 */       if (gs != null) {
/* 280:294 */         return gs;
/* 281:    */       }
/* 282:296 */       return Undefined.instance;
/* 283:    */     case -1: 
/* 284:300 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 285:301 */       Scriptable obj = ensureScriptable(arg);
/* 286:302 */       return obj.getPrototype();
/* 287:    */     case -2: 
/* 288:306 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 289:307 */       Scriptable obj = ensureScriptable(arg);
/* 290:308 */       Object[] ids = obj.getIds();
/* 291:309 */       for (int i = 0; i < ids.length; i++) {
/* 292:310 */         ids[i] = ScriptRuntime.toString(ids[i]);
/* 293:    */       }
/* 294:312 */       return cx.newArray(scope, ids);
/* 295:    */     case -3: 
/* 296:316 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 297:317 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 298:318 */       Object[] ids = obj.getAllIds();
/* 299:319 */       for (int i = 0; i < ids.length; i++) {
/* 300:320 */         ids[i] = ScriptRuntime.toString(ids[i]);
/* 301:    */       }
/* 302:322 */       return cx.newArray(scope, ids);
/* 303:    */     case -4: 
/* 304:326 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 305:    */       
/* 306:    */ 
/* 307:    */ 
/* 308:330 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 309:331 */       Object nameArg = args.length < 2 ? Undefined.instance : args[1];
/* 310:332 */       String name = ScriptRuntime.toString(nameArg);
/* 311:333 */       Scriptable desc = obj.getOwnPropertyDescriptor(cx, name);
/* 312:334 */       return desc == null ? Undefined.instance : desc;
/* 313:    */     case -5: 
/* 314:338 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 315:339 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 316:340 */       Object name = args.length < 2 ? Undefined.instance : args[1];
/* 317:341 */       Object descArg = args.length < 3 ? Undefined.instance : args[2];
/* 318:342 */       ScriptableObject desc = ensureScriptableObject(descArg);
/* 319:343 */       obj.defineOwnProperty(cx, name, desc);
/* 320:344 */       return obj;
/* 321:    */     case -6: 
/* 322:348 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 323:349 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 324:350 */       return Boolean.valueOf(obj.isExtensible());
/* 325:    */     case -7: 
/* 326:354 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 327:355 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 328:356 */       obj.preventExtensions();
/* 329:357 */       return obj;
/* 330:    */     case -8: 
/* 331:361 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 332:362 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 333:363 */       Object propsObj = args.length < 2 ? Undefined.instance : args[1];
/* 334:364 */       Scriptable props = Context.toObject(propsObj, getParentScope());
/* 335:365 */       obj.defineOwnProperties(cx, ensureScriptableObject(props));
/* 336:366 */       return obj;
/* 337:    */     case -9: 
/* 338:370 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 339:371 */       Scriptable obj = arg == null ? null : ensureScriptable(arg);
/* 340:    */       
/* 341:373 */       ScriptableObject newObject = new NativeObject();
/* 342:374 */       newObject.setParentScope(getParentScope());
/* 343:375 */       newObject.setPrototype(obj);
/* 344:377 */       if ((args.length > 1) && (args[1] != Undefined.instance))
/* 345:    */       {
/* 346:378 */         Scriptable props = Context.toObject(args[1], getParentScope());
/* 347:379 */         newObject.defineOwnProperties(cx, ensureScriptableObject(props));
/* 348:    */       }
/* 349:382 */       return newObject;
/* 350:    */     case -10: 
/* 351:387 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 352:388 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 353:390 */       if (obj.isExtensible()) {
/* 354:390 */         return Boolean.valueOf(false);
/* 355:    */       }
/* 356:392 */       for (Object name : obj.getAllIds())
/* 357:    */       {
/* 358:393 */         Object configurable = obj.getOwnPropertyDescriptor(cx, name).get("configurable");
/* 359:394 */         if (Boolean.TRUE.equals(configurable)) {
/* 360:395 */           return Boolean.valueOf(false);
/* 361:    */         }
/* 362:    */       }
/* 363:398 */       return Boolean.valueOf(true);
/* 364:    */     case -11: 
/* 365:402 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 366:403 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 367:405 */       if (obj.isExtensible()) {
/* 368:405 */         return Boolean.valueOf(false);
/* 369:    */       }
/* 370:407 */       for (Object name : obj.getAllIds())
/* 371:    */       {
/* 372:408 */         ScriptableObject desc = obj.getOwnPropertyDescriptor(cx, name);
/* 373:409 */         if (Boolean.TRUE.equals(desc.get("configurable"))) {
/* 374:410 */           return Boolean.valueOf(false);
/* 375:    */         }
/* 376:411 */         if ((isDataDescriptor(desc)) && (Boolean.TRUE.equals(desc.get("writable")))) {
/* 377:412 */           return Boolean.valueOf(false);
/* 378:    */         }
/* 379:    */       }
/* 380:415 */       return Boolean.valueOf(true);
/* 381:    */     case -12: 
/* 382:419 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 383:420 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 384:422 */       for (Object name : obj.getAllIds())
/* 385:    */       {
/* 386:423 */         ScriptableObject desc = obj.getOwnPropertyDescriptor(cx, name);
/* 387:424 */         if (Boolean.TRUE.equals(desc.get("configurable")))
/* 388:    */         {
/* 389:425 */           desc.put("configurable", desc, Boolean.valueOf(false));
/* 390:426 */           obj.defineOwnProperty(cx, name, desc);
/* 391:    */         }
/* 392:    */       }
/* 393:429 */       obj.preventExtensions();
/* 394:    */       
/* 395:431 */       return obj;
/* 396:    */     case -13: 
/* 397:435 */       Object arg = args.length < 1 ? Undefined.instance : args[0];
/* 398:436 */       ScriptableObject obj = ensureScriptableObject(arg);
/* 399:438 */       for (Object name : obj.getAllIds())
/* 400:    */       {
/* 401:439 */         ScriptableObject desc = obj.getOwnPropertyDescriptor(cx, name);
/* 402:440 */         if ((isDataDescriptor(desc)) && (Boolean.TRUE.equals(desc.get("writable")))) {
/* 403:441 */           desc.put("writable", desc, Boolean.valueOf(false));
/* 404:    */         }
/* 405:442 */         if (Boolean.TRUE.equals(desc.get("configurable"))) {
/* 406:443 */           desc.put("configurable", desc, Boolean.valueOf(false));
/* 407:    */         }
/* 408:444 */         obj.defineOwnProperty(cx, name, desc);
/* 409:    */       }
/* 410:446 */       obj.preventExtensions();
/* 411:    */       
/* 412:448 */       return obj;
/* 413:    */     }
/* 414:453 */     throw new IllegalArgumentException(String.valueOf(id));
/* 415:    */   }
/* 416:    */   
/* 417:    */   public boolean containsKey(Object key)
/* 418:    */   {
/* 419:460 */     if ((key instanceof String)) {
/* 420:461 */       return has((String)key, this);
/* 421:    */     }
/* 422:462 */     if ((key instanceof Number)) {
/* 423:463 */       return has(((Number)key).intValue(), this);
/* 424:    */     }
/* 425:465 */     return false;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public boolean containsValue(Object value)
/* 429:    */   {
/* 430:469 */     for (Object obj : values()) {
/* 431:470 */       if ((value == obj) || ((value != null) && (value.equals(obj)))) {
/* 432:472 */         return true;
/* 433:    */       }
/* 434:    */     }
/* 435:475 */     return false;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public Object remove(Object key)
/* 439:    */   {
/* 440:479 */     Object value = get(key);
/* 441:480 */     if ((key instanceof String)) {
/* 442:481 */       delete((String)key);
/* 443:482 */     } else if ((key instanceof Number)) {
/* 444:483 */       delete(((Number)key).intValue());
/* 445:    */     }
/* 446:485 */     return value;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public Set<Object> keySet()
/* 450:    */   {
/* 451:490 */     return new KeySet();
/* 452:    */   }
/* 453:    */   
/* 454:    */   public Collection<Object> values()
/* 455:    */   {
/* 456:494 */     return new ValueCollection();
/* 457:    */   }
/* 458:    */   
/* 459:    */   public Set<Map.Entry<Object, Object>> entrySet()
/* 460:    */   {
/* 461:498 */     return new EntrySet();
/* 462:    */   }
/* 463:    */   
/* 464:    */   public Object put(Object key, Object value)
/* 465:    */   {
/* 466:502 */     throw new UnsupportedOperationException();
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void putAll(Map m)
/* 470:    */   {
/* 471:506 */     throw new UnsupportedOperationException();
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void clear()
/* 475:    */   {
/* 476:510 */     throw new UnsupportedOperationException();
/* 477:    */   }
/* 478:    */   
/* 479:    */   class EntrySet
/* 480:    */     extends AbstractSet<Map.Entry<Object, Object>>
/* 481:    */   {
/* 482:    */     EntrySet() {}
/* 483:    */     
/* 484:    */     public Iterator<Map.Entry<Object, Object>> iterator()
/* 485:    */     {
/* 486:517 */       new Iterator()
/* 487:    */       {
/* 488:518 */         Object[] ids = NativeObject.this.getIds();
/* 489:519 */         Object key = null;
/* 490:520 */         int index = 0;
/* 491:    */         
/* 492:    */         public boolean hasNext()
/* 493:    */         {
/* 494:523 */           return this.index < this.ids.length;
/* 495:    */         }
/* 496:    */         
/* 497:    */         public Map.Entry<Object, Object> next()
/* 498:    */         {
/* 499:527 */           final Object ekey = this.key = this.ids[(this.index++)];
/* 500:528 */           final Object value = NativeObject.this.get(this.key);
/* 501:529 */           new Map.Entry()
/* 502:    */           {
/* 503:    */             public Object getKey()
/* 504:    */             {
/* 505:531 */               return ekey;
/* 506:    */             }
/* 507:    */             
/* 508:    */             public Object getValue()
/* 509:    */             {
/* 510:535 */               return value;
/* 511:    */             }
/* 512:    */             
/* 513:    */             public Object setValue(Object value)
/* 514:    */             {
/* 515:539 */               throw new UnsupportedOperationException();
/* 516:    */             }
/* 517:    */             
/* 518:    */             public boolean equals(Object other)
/* 519:    */             {
/* 520:543 */               if (!(other instanceof Map.Entry)) {
/* 521:544 */                 return false;
/* 522:    */               }
/* 523:546 */               Map.Entry e = (Map.Entry)other;
/* 524:547 */               return (ekey == null ? e.getKey() == null : ekey.equals(e.getKey())) && (value == null ? e.getValue() == null : value.equals(e.getValue()));
/* 525:    */             }
/* 526:    */             
/* 527:    */             public int hashCode()
/* 528:    */             {
/* 529:552 */               return (ekey == null ? 0 : ekey.hashCode()) ^ (value == null ? 0 : value.hashCode());
/* 530:    */             }
/* 531:    */             
/* 532:    */             public String toString()
/* 533:    */             {
/* 534:557 */               return ekey + "=" + value;
/* 535:    */             }
/* 536:    */           };
/* 537:    */         }
/* 538:    */         
/* 539:    */         public void remove()
/* 540:    */         {
/* 541:563 */           if (this.key == null) {
/* 542:564 */             throw new IllegalStateException();
/* 543:    */           }
/* 544:566 */           NativeObject.this.remove(this.key);
/* 545:567 */           this.key = null;
/* 546:    */         }
/* 547:    */       };
/* 548:    */     }
/* 549:    */     
/* 550:    */     public int size()
/* 551:    */     {
/* 552:574 */       return NativeObject.this.size();
/* 553:    */     }
/* 554:    */   }
/* 555:    */   
/* 556:    */   class KeySet
/* 557:    */     extends AbstractSet<Object>
/* 558:    */   {
/* 559:    */     KeySet() {}
/* 560:    */     
/* 561:    */     public boolean contains(Object key)
/* 562:    */     {
/* 563:582 */       return NativeObject.this.containsKey(key);
/* 564:    */     }
/* 565:    */     
/* 566:    */     public Iterator<Object> iterator()
/* 567:    */     {
/* 568:587 */       new Iterator()
/* 569:    */       {
/* 570:588 */         Object[] ids = NativeObject.this.getIds();
/* 571:    */         Object key;
/* 572:590 */         int index = 0;
/* 573:    */         
/* 574:    */         public boolean hasNext()
/* 575:    */         {
/* 576:593 */           return this.index < this.ids.length;
/* 577:    */         }
/* 578:    */         
/* 579:    */         public Object next()
/* 580:    */         {
/* 581:597 */           return this.key = this.ids[(this.index++)];
/* 582:    */         }
/* 583:    */         
/* 584:    */         public void remove()
/* 585:    */         {
/* 586:601 */           if (this.key == null) {
/* 587:602 */             throw new IllegalStateException();
/* 588:    */           }
/* 589:604 */           NativeObject.this.remove(this.key);
/* 590:605 */           this.key = null;
/* 591:    */         }
/* 592:    */       };
/* 593:    */     }
/* 594:    */     
/* 595:    */     public int size()
/* 596:    */     {
/* 597:612 */       return NativeObject.this.size();
/* 598:    */     }
/* 599:    */   }
/* 600:    */   
/* 601:    */   class ValueCollection
/* 602:    */     extends AbstractCollection<Object>
/* 603:    */   {
/* 604:    */     ValueCollection() {}
/* 605:    */     
/* 606:    */     public Iterator<Object> iterator()
/* 607:    */     {
/* 608:620 */       new Iterator()
/* 609:    */       {
/* 610:621 */         Object[] ids = NativeObject.this.getIds();
/* 611:    */         Object key;
/* 612:623 */         int index = 0;
/* 613:    */         
/* 614:    */         public boolean hasNext()
/* 615:    */         {
/* 616:626 */           return this.index < this.ids.length;
/* 617:    */         }
/* 618:    */         
/* 619:    */         public Object next()
/* 620:    */         {
/* 621:630 */           return NativeObject.this.get(this.key = this.ids[(this.index++)]);
/* 622:    */         }
/* 623:    */         
/* 624:    */         public void remove()
/* 625:    */         {
/* 626:634 */           if (this.key == null) {
/* 627:635 */             throw new IllegalStateException();
/* 628:    */           }
/* 629:637 */           NativeObject.this.remove(this.key);
/* 630:638 */           this.key = null;
/* 631:    */         }
/* 632:    */       };
/* 633:    */     }
/* 634:    */     
/* 635:    */     public int size()
/* 636:    */     {
/* 637:645 */       return NativeObject.this.size();
/* 638:    */     }
/* 639:    */   }
/* 640:    */   
/* 641:    */   protected int findPrototypeId(String s)
/* 642:    */   {
/* 643:657 */     int id = 0;String X = null;
/* 644:    */     int c;
/* 645:658 */     switch (s.length())
/* 646:    */     {
/* 647:    */     case 7: 
/* 648:659 */       X = "valueOf";id = 4; break;
/* 649:    */     case 8: 
/* 650:660 */       c = s.charAt(3);
/* 651:661 */       if (c == 111)
/* 652:    */       {
/* 653:661 */         X = "toSource";id = 8;
/* 654:    */       }
/* 655:662 */       else if (c == 116)
/* 656:    */       {
/* 657:662 */         X = "toString";id = 2;
/* 658:    */       }
/* 659:    */       break;
/* 660:    */     case 11: 
/* 661:664 */       X = "constructor";id = 1; break;
/* 662:    */     case 13: 
/* 663:665 */       X = "isPrototypeOf";id = 7; break;
/* 664:    */     case 14: 
/* 665:666 */       c = s.charAt(0);
/* 666:667 */       if (c == 104)
/* 667:    */       {
/* 668:667 */         X = "hasOwnProperty";id = 5;
/* 669:    */       }
/* 670:668 */       else if (c == 116)
/* 671:    */       {
/* 672:668 */         X = "toLocaleString";id = 3;
/* 673:    */       }
/* 674:    */       break;
/* 675:    */     case 16: 
/* 676:670 */       c = s.charAt(2);
/* 677:671 */       if (c == 100)
/* 678:    */       {
/* 679:672 */         c = s.charAt(8);
/* 680:673 */         if (c == 71)
/* 681:    */         {
/* 682:673 */           X = "__defineGetter__";id = 9;
/* 683:    */         }
/* 684:674 */         else if (c == 83)
/* 685:    */         {
/* 686:674 */           X = "__defineSetter__";id = 10;
/* 687:    */         }
/* 688:    */       }
/* 689:676 */       else if (c == 108)
/* 690:    */       {
/* 691:677 */         c = s.charAt(8);
/* 692:678 */         if (c == 71)
/* 693:    */         {
/* 694:678 */           X = "__lookupGetter__";id = 11;
/* 695:    */         }
/* 696:679 */         else if (c == 83)
/* 697:    */         {
/* 698:679 */           X = "__lookupSetter__";id = 12;
/* 699:    */         }
/* 700:    */       }
/* 701:    */       break;
/* 702:    */     case 20: 
/* 703:682 */       X = "propertyIsEnumerable";id = 6; break;
/* 704:    */     }
/* 705:684 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 706:684 */       id = 0;
/* 707:    */     }
/* 708:688 */     return id;
/* 709:    */   }
/* 710:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeObject
 * JD-Core Version:    0.7.0.1
 */