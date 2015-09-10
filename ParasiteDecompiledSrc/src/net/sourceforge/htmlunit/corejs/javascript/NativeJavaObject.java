/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.lang.reflect.Array;
/*   8:    */ import java.lang.reflect.InvocationTargetException;
/*   9:    */ import java.lang.reflect.Method;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.Map;
/*  12:    */ 
/*  13:    */ public class NativeJavaObject
/*  14:    */   implements Scriptable, Wrapper, Serializable
/*  15:    */ {
/*  16:    */   static final long serialVersionUID = -6948590651130498591L;
/*  17:    */   private static final int JSTYPE_UNDEFINED = 0;
/*  18:    */   private static final int JSTYPE_NULL = 1;
/*  19:    */   private static final int JSTYPE_BOOLEAN = 2;
/*  20:    */   private static final int JSTYPE_NUMBER = 3;
/*  21:    */   private static final int JSTYPE_STRING = 4;
/*  22:    */   private static final int JSTYPE_JAVA_CLASS = 5;
/*  23:    */   private static final int JSTYPE_JAVA_OBJECT = 6;
/*  24:    */   private static final int JSTYPE_JAVA_ARRAY = 7;
/*  25:    */   private static final int JSTYPE_OBJECT = 8;
/*  26:    */   static final byte CONVERSION_TRIVIAL = 1;
/*  27:    */   static final byte CONVERSION_NONTRIVIAL = 0;
/*  28:    */   static final byte CONVERSION_NONE = 99;
/*  29:    */   protected Scriptable prototype;
/*  30:    */   protected Scriptable parent;
/*  31:    */   protected transient Object javaObject;
/*  32:    */   protected transient Class<?> staticType;
/*  33:    */   protected transient JavaMembers members;
/*  34:    */   private transient Map<String, FieldAndMethods> fieldAndMethods;
/*  35:    */   private transient boolean isAdapter;
/*  36:    */   
/*  37:    */   public NativeJavaObject() {}
/*  38:    */   
/*  39:    */   public NativeJavaObject(Scriptable scope, Object javaObject, Class<?> staticType)
/*  40:    */   {
/*  41: 70 */     this(scope, javaObject, staticType, false);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public NativeJavaObject(Scriptable scope, Object javaObject, Class<?> staticType, boolean isAdapter)
/*  45:    */   {
/*  46: 76 */     this.parent = scope;
/*  47: 77 */     this.javaObject = javaObject;
/*  48: 78 */     this.staticType = staticType;
/*  49: 79 */     this.isAdapter = isAdapter;
/*  50: 80 */     initMembers();
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected void initMembers()
/*  54:    */   {
/*  55:    */     Class<?> dynamicType;
/*  56:    */     Class<?> dynamicType;
/*  57: 85 */     if (this.javaObject != null) {
/*  58: 86 */       dynamicType = this.javaObject.getClass();
/*  59:    */     } else {
/*  60: 88 */       dynamicType = this.staticType;
/*  61:    */     }
/*  62: 90 */     this.members = JavaMembers.lookupClass(this.parent, dynamicType, this.staticType, this.isAdapter);
/*  63:    */     
/*  64: 92 */     this.fieldAndMethods = this.members.getFieldAndMethodsObjects(this, this.javaObject, false);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean has(String name, Scriptable start)
/*  68:    */   {
/*  69: 97 */     return this.members.has(name, false);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean has(int index, Scriptable start)
/*  73:    */   {
/*  74:101 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Object get(String name, Scriptable start)
/*  78:    */   {
/*  79:105 */     if (this.fieldAndMethods != null)
/*  80:    */     {
/*  81:106 */       Object result = this.fieldAndMethods.get(name);
/*  82:107 */       if (result != null) {
/*  83:108 */         return result;
/*  84:    */       }
/*  85:    */     }
/*  86:113 */     return this.members.get(this, name, this.javaObject, false);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Object get(int index, Scriptable start)
/*  90:    */   {
/*  91:117 */     throw this.members.reportMemberNotFound(Integer.toString(index));
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void put(String name, Scriptable start, Object value)
/*  95:    */   {
/*  96:124 */     if ((this.prototype == null) || (this.members.has(name, false))) {
/*  97:125 */       this.members.put(this, name, this.javaObject, value, false);
/*  98:    */     } else {
/*  99:127 */       this.prototype.put(name, this.prototype, value);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void put(int index, Scriptable start, Object value)
/* 104:    */   {
/* 105:131 */     throw this.members.reportMemberNotFound(Integer.toString(index));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean hasInstance(Scriptable value)
/* 109:    */   {
/* 110:136 */     return false;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void delete(String name) {}
/* 114:    */   
/* 115:    */   public void delete(int index) {}
/* 116:    */   
/* 117:    */   public Scriptable getPrototype()
/* 118:    */   {
/* 119:146 */     if ((this.prototype == null) && ((this.javaObject instanceof String))) {
/* 120:147 */       return TopLevel.getBuiltinPrototype(this.parent, TopLevel.Builtins.String);
/* 121:    */     }
/* 122:149 */     return this.prototype;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setPrototype(Scriptable m)
/* 126:    */   {
/* 127:156 */     this.prototype = m;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Scriptable getParentScope()
/* 131:    */   {
/* 132:163 */     return this.parent;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setParentScope(Scriptable m)
/* 136:    */   {
/* 137:170 */     this.parent = m;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Object[] getIds()
/* 141:    */   {
/* 142:174 */     return this.members.getIds(false);
/* 143:    */   }
/* 144:    */   
/* 145:    */   /**
/* 146:    */    * @deprecated
/* 147:    */    */
/* 148:    */   public static Object wrap(Scriptable scope, Object obj, Class<?> staticType)
/* 149:    */   {
/* 150:183 */     Context cx = Context.getContext();
/* 151:184 */     return cx.getWrapFactory().wrap(cx, scope, obj, staticType);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Object unwrap()
/* 155:    */   {
/* 156:188 */     return this.javaObject;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getClassName()
/* 160:    */   {
/* 161:192 */     return "JavaObject";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Object getDefaultValue(Class<?> hint)
/* 165:    */   {
/* 166:198 */     if ((hint == null) && 
/* 167:199 */       ((this.javaObject instanceof Boolean))) {
/* 168:200 */       hint = ScriptRuntime.BooleanClass;
/* 169:    */     }
/* 170:    */     Object value;
/* 171:    */     Object value;
/* 172:203 */     if ((hint == null) || (hint == ScriptRuntime.StringClass))
/* 173:    */     {
/* 174:204 */       value = this.javaObject.toString();
/* 175:    */     }
/* 176:    */     else
/* 177:    */     {
/* 178:    */       String converterName;
/* 179:207 */       if (hint == ScriptRuntime.BooleanClass)
/* 180:    */       {
/* 181:208 */         converterName = "booleanValue";
/* 182:    */       }
/* 183:    */       else
/* 184:    */       {
/* 185:    */         String converterName;
/* 186:209 */         if (hint == ScriptRuntime.NumberClass) {
/* 187:210 */           converterName = "doubleValue";
/* 188:    */         } else {
/* 189:212 */           throw Context.reportRuntimeError0("msg.default.value");
/* 190:    */         }
/* 191:    */       }
/* 192:    */       String converterName;
/* 193:214 */       Object converterObject = get(converterName, this);
/* 194:    */       Object value;
/* 195:215 */       if ((converterObject instanceof Function))
/* 196:    */       {
/* 197:216 */         Function f = (Function)converterObject;
/* 198:217 */         value = f.call(Context.getContext(), f.getParentScope(), this, ScriptRuntime.emptyArgs);
/* 199:    */       }
/* 200:    */       else
/* 201:    */       {
/* 202:    */         Object value;
/* 203:220 */         if ((hint == ScriptRuntime.NumberClass) && ((this.javaObject instanceof Boolean)))
/* 204:    */         {
/* 205:223 */           boolean b = ((Boolean)this.javaObject).booleanValue();
/* 206:224 */           value = ScriptRuntime.wrapNumber(b ? 1.0D : 0.0D);
/* 207:    */         }
/* 208:    */         else
/* 209:    */         {
/* 210:226 */           value = this.javaObject.toString();
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:230 */     return value;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static boolean canConvert(Object fromObj, Class<?> to)
/* 218:    */   {
/* 219:239 */     int weight = getConversionWeight(fromObj, to);
/* 220:    */     
/* 221:241 */     return weight < 99;
/* 222:    */   }
/* 223:    */   
/* 224:    */   static int getConversionWeight(Object fromObj, Class<?> to)
/* 225:    */   {
/* 226:268 */     int fromCode = getJSTypeCode(fromObj);
/* 227:270 */     switch (fromCode)
/* 228:    */     {
/* 229:    */     case 0: 
/* 230:273 */       if ((to == ScriptRuntime.StringClass) || (to == ScriptRuntime.ObjectClass)) {
/* 231:275 */         return 1;
/* 232:    */       }
/* 233:    */       break;
/* 234:    */     case 1: 
/* 235:280 */       if (!to.isPrimitive()) {
/* 236:281 */         return 1;
/* 237:    */       }
/* 238:    */       break;
/* 239:    */     case 2: 
/* 240:287 */       if (to == Boolean.TYPE) {
/* 241:288 */         return 1;
/* 242:    */       }
/* 243:290 */       if (to == ScriptRuntime.BooleanClass) {
/* 244:291 */         return 2;
/* 245:    */       }
/* 246:293 */       if (to == ScriptRuntime.ObjectClass) {
/* 247:294 */         return 3;
/* 248:    */       }
/* 249:296 */       if (to == ScriptRuntime.StringClass) {
/* 250:297 */         return 4;
/* 251:    */       }
/* 252:    */       break;
/* 253:    */     case 3: 
/* 254:302 */       if (to.isPrimitive())
/* 255:    */       {
/* 256:303 */         if (to == Double.TYPE) {
/* 257:304 */           return 1;
/* 258:    */         }
/* 259:306 */         if (to != Boolean.TYPE) {
/* 260:307 */           return 1 + getSizeRank(to);
/* 261:    */         }
/* 262:    */       }
/* 263:    */       else
/* 264:    */       {
/* 265:311 */         if (to == ScriptRuntime.StringClass) {
/* 266:313 */           return 9;
/* 267:    */         }
/* 268:315 */         if (to == ScriptRuntime.ObjectClass) {
/* 269:316 */           return 10;
/* 270:    */         }
/* 271:318 */         if (ScriptRuntime.NumberClass.isAssignableFrom(to)) {
/* 272:320 */           return 2;
/* 273:    */         }
/* 274:    */       }
/* 275:    */       break;
/* 276:    */     case 4: 
/* 277:326 */       if (to == ScriptRuntime.StringClass) {
/* 278:327 */         return 1;
/* 279:    */       }
/* 280:329 */       if (to.isInstance(fromObj)) {
/* 281:330 */         return 2;
/* 282:    */       }
/* 283:332 */       if (to.isPrimitive())
/* 284:    */       {
/* 285:333 */         if (to == Character.TYPE) {
/* 286:334 */           return 3;
/* 287:    */         }
/* 288:335 */         if (to != Boolean.TYPE) {
/* 289:336 */           return 4;
/* 290:    */         }
/* 291:    */       }
/* 292:    */       break;
/* 293:    */     case 5: 
/* 294:342 */       if (to == ScriptRuntime.ClassClass) {
/* 295:343 */         return 1;
/* 296:    */       }
/* 297:345 */       if (to == ScriptRuntime.ObjectClass) {
/* 298:346 */         return 3;
/* 299:    */       }
/* 300:348 */       if (to == ScriptRuntime.StringClass) {
/* 301:349 */         return 4;
/* 302:    */       }
/* 303:    */       break;
/* 304:    */     case 6: 
/* 305:    */     case 7: 
/* 306:355 */       Object javaObj = fromObj;
/* 307:356 */       if ((javaObj instanceof Wrapper)) {
/* 308:357 */         javaObj = ((Wrapper)javaObj).unwrap();
/* 309:    */       }
/* 310:359 */       if (to.isInstance(javaObj)) {
/* 311:360 */         return 0;
/* 312:    */       }
/* 313:362 */       if (to == ScriptRuntime.StringClass) {
/* 314:363 */         return 2;
/* 315:    */       }
/* 316:365 */       if ((to.isPrimitive()) && (to != Boolean.TYPE)) {
/* 317:366 */         return fromCode == 7 ? 99 : 2 + getSizeRank(to);
/* 318:    */       }
/* 319:    */       break;
/* 320:    */     case 8: 
/* 321:373 */       if ((to != ScriptRuntime.ObjectClass) && (to.isInstance(fromObj))) {
/* 322:375 */         return 1;
/* 323:    */       }
/* 324:377 */       if (to.isArray())
/* 325:    */       {
/* 326:378 */         if ((fromObj instanceof NativeArray)) {
/* 327:382 */           return 1;
/* 328:    */         }
/* 329:    */       }
/* 330:    */       else
/* 331:    */       {
/* 332:385 */         if (to == ScriptRuntime.ObjectClass) {
/* 333:386 */           return 2;
/* 334:    */         }
/* 335:388 */         if (to == ScriptRuntime.StringClass) {
/* 336:389 */           return 3;
/* 337:    */         }
/* 338:391 */         if (to == ScriptRuntime.DateClass)
/* 339:    */         {
/* 340:392 */           if ((fromObj instanceof NativeDate)) {
/* 341:394 */             return 1;
/* 342:    */           }
/* 343:    */         }
/* 344:    */         else
/* 345:    */         {
/* 346:397 */           if (to.isInterface())
/* 347:    */           {
/* 348:398 */             if ((fromObj instanceof Function)) {
/* 349:400 */               if (to.getMethods().length == 1) {
/* 350:401 */                 return 1;
/* 351:    */               }
/* 352:    */             }
/* 353:404 */             return 11;
/* 354:    */           }
/* 355:406 */           if ((to.isPrimitive()) && (to != Boolean.TYPE)) {
/* 356:407 */             return 3 + getSizeRank(to);
/* 357:    */           }
/* 358:    */         }
/* 359:    */       }
/* 360:    */       break;
/* 361:    */     }
/* 362:412 */     return 99;
/* 363:    */   }
/* 364:    */   
/* 365:    */   static int getSizeRank(Class<?> aType)
/* 366:    */   {
/* 367:416 */     if (aType == Double.TYPE) {
/* 368:417 */       return 1;
/* 369:    */     }
/* 370:419 */     if (aType == Float.TYPE) {
/* 371:420 */       return 2;
/* 372:    */     }
/* 373:422 */     if (aType == Long.TYPE) {
/* 374:423 */       return 3;
/* 375:    */     }
/* 376:425 */     if (aType == Integer.TYPE) {
/* 377:426 */       return 4;
/* 378:    */     }
/* 379:428 */     if (aType == Short.TYPE) {
/* 380:429 */       return 5;
/* 381:    */     }
/* 382:431 */     if (aType == Character.TYPE) {
/* 383:432 */       return 6;
/* 384:    */     }
/* 385:434 */     if (aType == Byte.TYPE) {
/* 386:435 */       return 7;
/* 387:    */     }
/* 388:437 */     if (aType == Boolean.TYPE) {
/* 389:438 */       return 99;
/* 390:    */     }
/* 391:441 */     return 8;
/* 392:    */   }
/* 393:    */   
/* 394:    */   private static int getJSTypeCode(Object value)
/* 395:    */   {
/* 396:446 */     if (value == null) {
/* 397:447 */       return 1;
/* 398:    */     }
/* 399:449 */     if (value == Undefined.instance) {
/* 400:450 */       return 0;
/* 401:    */     }
/* 402:452 */     if ((value instanceof String)) {
/* 403:453 */       return 4;
/* 404:    */     }
/* 405:455 */     if ((value instanceof Number)) {
/* 406:456 */       return 3;
/* 407:    */     }
/* 408:458 */     if ((value instanceof Boolean)) {
/* 409:459 */       return 2;
/* 410:    */     }
/* 411:461 */     if ((value instanceof Scriptable))
/* 412:    */     {
/* 413:462 */       if ((value instanceof NativeJavaClass)) {
/* 414:463 */         return 5;
/* 415:    */       }
/* 416:465 */       if ((value instanceof NativeJavaArray)) {
/* 417:466 */         return 7;
/* 418:    */       }
/* 419:468 */       if ((value instanceof Wrapper)) {
/* 420:469 */         return 6;
/* 421:    */       }
/* 422:472 */       return 8;
/* 423:    */     }
/* 424:475 */     if ((value instanceof Class)) {
/* 425:476 */       return 5;
/* 426:    */     }
/* 427:479 */     Class<?> valueClass = value.getClass();
/* 428:480 */     if (valueClass.isArray()) {
/* 429:481 */       return 7;
/* 430:    */     }
/* 431:484 */     return 6;
/* 432:    */   }
/* 433:    */   
/* 434:    */   /**
/* 435:    */    * @deprecated
/* 436:    */    */
/* 437:    */   public static Object coerceType(Class<?> type, Object value)
/* 438:    */   {
/* 439:497 */     return coerceTypeImpl(type, value);
/* 440:    */   }
/* 441:    */   
/* 442:    */   static Object coerceTypeImpl(Class<?> type, Object value)
/* 443:    */   {
/* 444:506 */     if ((value != null) && (value.getClass() == type)) {
/* 445:507 */       return value;
/* 446:    */     }
/* 447:510 */     switch (getJSTypeCode(value))
/* 448:    */     {
/* 449:    */     case 1: 
/* 450:514 */       if (type.isPrimitive()) {
/* 451:515 */         reportConversionError(value, type);
/* 452:    */       }
/* 453:517 */       return null;
/* 454:    */     case 0: 
/* 455:520 */       if ((type == ScriptRuntime.StringClass) || (type == ScriptRuntime.ObjectClass)) {
/* 456:522 */         return "undefined";
/* 457:    */       }
/* 458:525 */       reportConversionError("undefined", type);
/* 459:    */       
/* 460:527 */       break;
/* 461:    */     case 2: 
/* 462:531 */       if ((type == Boolean.TYPE) || (type == ScriptRuntime.BooleanClass) || (type == ScriptRuntime.ObjectClass)) {
/* 463:534 */         return value;
/* 464:    */       }
/* 465:536 */       if (type == ScriptRuntime.StringClass) {
/* 466:537 */         return value.toString();
/* 467:    */       }
/* 468:540 */       reportConversionError(value, type);
/* 469:    */       
/* 470:542 */       break;
/* 471:    */     case 3: 
/* 472:545 */       if (type == ScriptRuntime.StringClass) {
/* 473:546 */         return ScriptRuntime.toString(value);
/* 474:    */       }
/* 475:548 */       if (type == ScriptRuntime.ObjectClass) {
/* 476:549 */         return coerceToNumber(Double.TYPE, value);
/* 477:    */       }
/* 478:551 */       if (((type.isPrimitive()) && (type != Boolean.TYPE)) || (ScriptRuntime.NumberClass.isAssignableFrom(type))) {
/* 479:553 */         return coerceToNumber(type, value);
/* 480:    */       }
/* 481:556 */       reportConversionError(value, type);
/* 482:    */       
/* 483:558 */       break;
/* 484:    */     case 4: 
/* 485:561 */       if ((type == ScriptRuntime.StringClass) || (type.isInstance(value))) {
/* 486:562 */         return value;
/* 487:    */       }
/* 488:564 */       if ((type == Character.TYPE) || (type == ScriptRuntime.CharacterClass))
/* 489:    */       {
/* 490:571 */         if (((String)value).length() == 1) {
/* 491:572 */           return Character.valueOf(((String)value).charAt(0));
/* 492:    */         }
/* 493:575 */         return coerceToNumber(type, value);
/* 494:    */       }
/* 495:578 */       if (((type.isPrimitive()) && (type != Boolean.TYPE)) || (ScriptRuntime.NumberClass.isAssignableFrom(type))) {
/* 496:581 */         return coerceToNumber(type, value);
/* 497:    */       }
/* 498:584 */       reportConversionError(value, type);
/* 499:    */       
/* 500:586 */       break;
/* 501:    */     case 5: 
/* 502:589 */       if ((value instanceof Wrapper)) {
/* 503:590 */         value = ((Wrapper)value).unwrap();
/* 504:    */       }
/* 505:593 */       if ((type == ScriptRuntime.ClassClass) || (type == ScriptRuntime.ObjectClass)) {
/* 506:595 */         return value;
/* 507:    */       }
/* 508:597 */       if (type == ScriptRuntime.StringClass) {
/* 509:598 */         return value.toString();
/* 510:    */       }
/* 511:601 */       reportConversionError(value, type);
/* 512:    */       
/* 513:603 */       break;
/* 514:    */     case 6: 
/* 515:    */     case 7: 
/* 516:607 */       if ((value instanceof Wrapper)) {
/* 517:608 */         value = ((Wrapper)value).unwrap();
/* 518:    */       }
/* 519:610 */       if (type.isPrimitive())
/* 520:    */       {
/* 521:611 */         if (type == Boolean.TYPE) {
/* 522:612 */           reportConversionError(value, type);
/* 523:    */         }
/* 524:614 */         return coerceToNumber(type, value);
/* 525:    */       }
/* 526:617 */       if (type == ScriptRuntime.StringClass) {
/* 527:618 */         return value.toString();
/* 528:    */       }
/* 529:621 */       if (type.isInstance(value)) {
/* 530:622 */         return value;
/* 531:    */       }
/* 532:625 */       reportConversionError(value, type);
/* 533:    */       
/* 534:    */ 
/* 535:    */ 
/* 536:629 */       break;
/* 537:    */     case 8: 
/* 538:632 */       if (type == ScriptRuntime.StringClass) {
/* 539:633 */         return ScriptRuntime.toString(value);
/* 540:    */       }
/* 541:635 */       if (type.isPrimitive())
/* 542:    */       {
/* 543:636 */         if (type == Boolean.TYPE) {
/* 544:637 */           reportConversionError(value, type);
/* 545:    */         }
/* 546:639 */         return coerceToNumber(type, value);
/* 547:    */       }
/* 548:641 */       if (type.isInstance(value)) {
/* 549:642 */         return value;
/* 550:    */       }
/* 551:644 */       if ((type == ScriptRuntime.DateClass) && ((value instanceof NativeDate)))
/* 552:    */       {
/* 553:647 */         double time = ((NativeDate)value).getJSTimeValue();
/* 554:    */         
/* 555:649 */         return new Date(time);
/* 556:    */       }
/* 557:651 */       if ((type.isArray()) && ((value instanceof NativeArray)))
/* 558:    */       {
/* 559:654 */         NativeArray array = (NativeArray)value;
/* 560:655 */         long length = array.getLength();
/* 561:656 */         Class<?> arrayType = type.getComponentType();
/* 562:657 */         Object Result = Array.newInstance(arrayType, (int)length);
/* 563:658 */         for (int i = 0; i < length; i++) {
/* 564:    */           try
/* 565:    */           {
/* 566:660 */             Array.set(Result, i, coerceType(arrayType, array.get(i, array)));
/* 567:    */           }
/* 568:    */           catch (EvaluatorException ee)
/* 569:    */           {
/* 570:664 */             reportConversionError(value, type);
/* 571:    */           }
/* 572:    */         }
/* 573:668 */         return Result;
/* 574:    */       }
/* 575:670 */       if ((value instanceof Wrapper))
/* 576:    */       {
/* 577:671 */         value = ((Wrapper)value).unwrap();
/* 578:672 */         if (type.isInstance(value)) {
/* 579:673 */           return value;
/* 580:    */         }
/* 581:674 */         reportConversionError(value, type);
/* 582:    */       }
/* 583:676 */       else if ((type.isInterface()) && ((value instanceof Callable)))
/* 584:    */       {
/* 585:684 */         if ((value instanceof ScriptableObject))
/* 586:    */         {
/* 587:685 */           ScriptableObject so = (ScriptableObject)value;
/* 588:686 */           Object key = Kit.makeHashKeyFromPair(COERCED_INTERFACE_KEY, type);
/* 589:    */           
/* 590:688 */           Object old = so.getAssociatedValue(key);
/* 591:689 */           if (old != null) {
/* 592:691 */             return old;
/* 593:    */           }
/* 594:693 */           Context cx = Context.getContext();
/* 595:694 */           Object glue = InterfaceAdapter.create(cx, type, (Callable)value);
/* 596:    */           
/* 597:    */ 
/* 598:697 */           glue = so.associateValue(key, glue);
/* 599:698 */           return glue;
/* 600:    */         }
/* 601:700 */         reportConversionError(value, type);
/* 602:    */       }
/* 603:    */       else
/* 604:    */       {
/* 605:702 */         reportConversionError(value, type);
/* 606:    */       }
/* 607:    */       break;
/* 608:    */     }
/* 609:707 */     return value;
/* 610:    */   }
/* 611:    */   
/* 612:    */   private static Object coerceToNumber(Class<?> type, Object value)
/* 613:    */   {
/* 614:712 */     Class<?> valueClass = value.getClass();
/* 615:715 */     if ((type == Character.TYPE) || (type == ScriptRuntime.CharacterClass))
/* 616:    */     {
/* 617:716 */       if (valueClass == ScriptRuntime.CharacterClass) {
/* 618:717 */         return value;
/* 619:    */       }
/* 620:719 */       return Character.valueOf((char)(int)toInteger(value, ScriptRuntime.CharacterClass, 0.0D, 65535.0D));
/* 621:    */     }
/* 622:726 */     if ((type == ScriptRuntime.ObjectClass) || (type == ScriptRuntime.DoubleClass) || (type == Double.TYPE)) {
/* 623:728 */       return valueClass == ScriptRuntime.DoubleClass ? value : new Double(toDouble(value));
/* 624:    */     }
/* 625:733 */     if ((type == ScriptRuntime.FloatClass) || (type == Float.TYPE))
/* 626:    */     {
/* 627:734 */       if (valueClass == ScriptRuntime.FloatClass) {
/* 628:735 */         return value;
/* 629:    */       }
/* 630:738 */       double number = toDouble(value);
/* 631:739 */       if ((Double.isInfinite(number)) || (Double.isNaN(number)) || (number == 0.0D)) {
/* 632:741 */         return new Float((float)number);
/* 633:    */       }
/* 634:744 */       double absNumber = Math.abs(number);
/* 635:745 */       if (absNumber < 1.401298464324817E-045D) {
/* 636:746 */         return new Float(number > 0.0D ? 0.0D : -0.0D);
/* 637:    */       }
/* 638:748 */       if (absNumber > 3.402823466385289E+038D) {
/* 639:749 */         return new Float(number > 0.0D ? (1.0F / 1.0F) : (1.0F / -1.0F));
/* 640:    */       }
/* 641:754 */       return new Float((float)number);
/* 642:    */     }
/* 643:761 */     if ((type == ScriptRuntime.IntegerClass) || (type == Integer.TYPE))
/* 644:    */     {
/* 645:762 */       if (valueClass == ScriptRuntime.IntegerClass) {
/* 646:763 */         return value;
/* 647:    */       }
/* 648:766 */       return Integer.valueOf((int)toInteger(value, ScriptRuntime.IntegerClass, -2147483648.0D, 2147483647.0D));
/* 649:    */     }
/* 650:773 */     if ((type == ScriptRuntime.LongClass) || (type == Long.TYPE))
/* 651:    */     {
/* 652:774 */       if (valueClass == ScriptRuntime.LongClass) {
/* 653:775 */         return value;
/* 654:    */       }
/* 655:784 */       double max = Double.longBitsToDouble(4890909195324358655L);
/* 656:785 */       double min = Double.longBitsToDouble(-4332462841530417152L);
/* 657:786 */       return Long.valueOf(toInteger(value, ScriptRuntime.LongClass, min, max));
/* 658:    */     }
/* 659:793 */     if ((type == ScriptRuntime.ShortClass) || (type == Short.TYPE))
/* 660:    */     {
/* 661:794 */       if (valueClass == ScriptRuntime.ShortClass) {
/* 662:795 */         return value;
/* 663:    */       }
/* 664:798 */       return Short.valueOf((short)(int)toInteger(value, ScriptRuntime.ShortClass, -32768.0D, 32767.0D));
/* 665:    */     }
/* 666:805 */     if ((type == ScriptRuntime.ByteClass) || (type == Byte.TYPE))
/* 667:    */     {
/* 668:806 */       if (valueClass == ScriptRuntime.ByteClass) {
/* 669:807 */         return value;
/* 670:    */       }
/* 671:810 */       return Byte.valueOf((byte)(int)toInteger(value, ScriptRuntime.ByteClass, -128.0D, 127.0D));
/* 672:    */     }
/* 673:817 */     return new Double(toDouble(value));
/* 674:    */   }
/* 675:    */   
/* 676:    */   private static double toDouble(Object value)
/* 677:    */   {
/* 678:823 */     if ((value instanceof Number)) {
/* 679:824 */       return ((Number)value).doubleValue();
/* 680:    */     }
/* 681:826 */     if ((value instanceof String)) {
/* 682:827 */       return ScriptRuntime.toNumber((String)value);
/* 683:    */     }
/* 684:829 */     if ((value instanceof Scriptable))
/* 685:    */     {
/* 686:830 */       if ((value instanceof Wrapper)) {
/* 687:832 */         return toDouble(((Wrapper)value).unwrap());
/* 688:    */       }
/* 689:835 */       return ScriptRuntime.toNumber(value);
/* 690:    */     }
/* 691:    */     Method meth;
/* 692:    */     try
/* 693:    */     {
/* 694:841 */       meth = value.getClass().getMethod("doubleValue", (Class[])null);
/* 695:    */     }
/* 696:    */     catch (NoSuchMethodException e)
/* 697:    */     {
/* 698:845 */       meth = null;
/* 699:    */     }
/* 700:    */     catch (SecurityException e)
/* 701:    */     {
/* 702:848 */       meth = null;
/* 703:    */     }
/* 704:850 */     if (meth != null) {
/* 705:    */       try
/* 706:    */       {
/* 707:852 */         return ((Number)meth.invoke(value, (Object[])null)).doubleValue();
/* 708:    */       }
/* 709:    */       catch (IllegalAccessException e)
/* 710:    */       {
/* 711:857 */         reportConversionError(value, Double.TYPE);
/* 712:    */       }
/* 713:    */       catch (InvocationTargetException e)
/* 714:    */       {
/* 715:861 */         reportConversionError(value, Double.TYPE);
/* 716:    */       }
/* 717:    */     }
/* 718:864 */     return ScriptRuntime.toNumber(value.toString());
/* 719:    */   }
/* 720:    */   
/* 721:    */   private static long toInteger(Object value, Class<?> type, double min, double max)
/* 722:    */   {
/* 723:871 */     double d = toDouble(value);
/* 724:873 */     if ((Double.isInfinite(d)) || (Double.isNaN(d))) {
/* 725:875 */       reportConversionError(ScriptRuntime.toString(value), type);
/* 726:    */     }
/* 727:878 */     if (d > 0.0D) {
/* 728:879 */       d = Math.floor(d);
/* 729:    */     } else {
/* 730:882 */       d = Math.ceil(d);
/* 731:    */     }
/* 732:885 */     if ((d < min) || (d > max)) {
/* 733:887 */       reportConversionError(ScriptRuntime.toString(value), type);
/* 734:    */     }
/* 735:889 */     return d;
/* 736:    */   }
/* 737:    */   
/* 738:    */   static void reportConversionError(Object value, Class<?> type)
/* 739:    */   {
/* 740:896 */     throw Context.reportRuntimeError2("msg.conversion.not.allowed", String.valueOf(value), JavaMembers.javaSignature(type));
/* 741:    */   }
/* 742:    */   
/* 743:    */   private void writeObject(ObjectOutputStream out)
/* 744:    */     throws IOException
/* 745:    */   {
/* 746:905 */     out.defaultWriteObject();
/* 747:    */     
/* 748:907 */     out.writeBoolean(this.isAdapter);
/* 749:908 */     if (this.isAdapter)
/* 750:    */     {
/* 751:909 */       if (adapter_writeAdapterObject == null) {
/* 752:910 */         throw new IOException();
/* 753:    */       }
/* 754:912 */       Object[] args = { this.javaObject, out };
/* 755:    */       try
/* 756:    */       {
/* 757:914 */         adapter_writeAdapterObject.invoke(null, args);
/* 758:    */       }
/* 759:    */       catch (Exception ex)
/* 760:    */       {
/* 761:916 */         throw new IOException();
/* 762:    */       }
/* 763:    */     }
/* 764:    */     else
/* 765:    */     {
/* 766:919 */       out.writeObject(this.javaObject);
/* 767:    */     }
/* 768:922 */     if (this.staticType != null) {
/* 769:923 */       out.writeObject(this.staticType.getClass().getName());
/* 770:    */     } else {
/* 771:925 */       out.writeObject(null);
/* 772:    */     }
/* 773:    */   }
/* 774:    */   
/* 775:    */   private void readObject(ObjectInputStream in)
/* 776:    */     throws IOException, ClassNotFoundException
/* 777:    */   {
/* 778:932 */     in.defaultReadObject();
/* 779:    */     
/* 780:934 */     this.isAdapter = in.readBoolean();
/* 781:935 */     if (this.isAdapter)
/* 782:    */     {
/* 783:936 */       if (adapter_readAdapterObject == null) {
/* 784:937 */         throw new ClassNotFoundException();
/* 785:    */       }
/* 786:938 */       Object[] args = { this, in };
/* 787:    */       try
/* 788:    */       {
/* 789:940 */         this.javaObject = adapter_readAdapterObject.invoke(null, args);
/* 790:    */       }
/* 791:    */       catch (Exception ex)
/* 792:    */       {
/* 793:942 */         throw new IOException();
/* 794:    */       }
/* 795:    */     }
/* 796:    */     else
/* 797:    */     {
/* 798:945 */       this.javaObject = in.readObject();
/* 799:    */     }
/* 800:948 */     String className = (String)in.readObject();
/* 801:949 */     if (className != null) {
/* 802:950 */       this.staticType = Class.forName(className);
/* 803:    */     } else {
/* 804:952 */       this.staticType = null;
/* 805:    */     }
/* 806:955 */     initMembers();
/* 807:    */   }
/* 808:    */   
/* 809:975 */   private static final Object COERCED_INTERFACE_KEY = "Coerced Interface";
/* 810:    */   private static Method adapter_writeAdapterObject;
/* 811:    */   private static Method adapter_readAdapterObject;
/* 812:    */   
/* 813:    */   static
/* 814:    */   {
/* 815:981 */     Class<?>[] sig2 = new Class[2];
/* 816:982 */     Class<?> cl = Kit.classOrNull("net.sourceforge.htmlunit.corejs.javascript.JavaAdapter");
/* 817:983 */     if (cl != null) {
/* 818:    */       try
/* 819:    */       {
/* 820:985 */         sig2[0] = ScriptRuntime.ObjectClass;
/* 821:986 */         sig2[1] = Kit.classOrNull("java.io.ObjectOutputStream");
/* 822:987 */         adapter_writeAdapterObject = cl.getMethod("writeAdapterObject", sig2);
/* 823:    */         
/* 824:    */ 
/* 825:990 */         sig2[0] = ScriptRuntime.ScriptableClass;
/* 826:991 */         sig2[1] = Kit.classOrNull("java.io.ObjectInputStream");
/* 827:992 */         adapter_readAdapterObject = cl.getMethod("readAdapterObject", sig2);
/* 828:    */       }
/* 829:    */       catch (NoSuchMethodException e)
/* 830:    */       {
/* 831:996 */         adapter_writeAdapterObject = null;
/* 832:997 */         adapter_readAdapterObject = null;
/* 833:    */       }
/* 834:    */     }
/* 835:    */   }
/* 836:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeJavaObject
 * JD-Core Version:    0.7.0.1
 */