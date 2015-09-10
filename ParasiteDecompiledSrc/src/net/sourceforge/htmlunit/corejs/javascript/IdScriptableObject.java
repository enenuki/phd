/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ 
/*   8:    */ public abstract class IdScriptableObject
/*   9:    */   extends ScriptableObject
/*  10:    */   implements IdFunctionCall
/*  11:    */ {
/*  12:    */   private volatile transient PrototypeValues prototypeValues;
/*  13:    */   public IdScriptableObject() {}
/*  14:    */   
/*  15:    */   private static final class PrototypeValues
/*  16:    */     implements Serializable
/*  17:    */   {
/*  18:    */     static final long serialVersionUID = 3038645279153854371L;
/*  19:    */     private static final int VALUE_SLOT = 0;
/*  20:    */     private static final int NAME_SLOT = 1;
/*  21:    */     private static final int SLOT_SPAN = 2;
/*  22:    */     private IdScriptableObject obj;
/*  23:    */     private int maxId;
/*  24:    */     private volatile Object[] valueArray;
/*  25:    */     private volatile short[] attributeArray;
/*  26: 79 */     private volatile int lastFoundId = 1;
/*  27:    */     int constructorId;
/*  28:    */     private IdFunctionObject constructor;
/*  29:    */     private short constructorAttrs;
/*  30:    */     
/*  31:    */     PrototypeValues(IdScriptableObject obj, int maxId)
/*  32:    */     {
/*  33: 89 */       if (obj == null) {
/*  34: 89 */         throw new IllegalArgumentException();
/*  35:    */       }
/*  36: 90 */       if (maxId < 1) {
/*  37: 90 */         throw new IllegalArgumentException();
/*  38:    */       }
/*  39: 91 */       this.obj = obj;
/*  40: 92 */       this.maxId = maxId;
/*  41:    */     }
/*  42:    */     
/*  43:    */     final int getMaxId()
/*  44:    */     {
/*  45: 97 */       return this.maxId;
/*  46:    */     }
/*  47:    */     
/*  48:    */     final void initValue(int id, String name, Object value, int attributes)
/*  49:    */     {
/*  50:102 */       if ((1 > id) || (id > this.maxId)) {
/*  51:103 */         throw new IllegalArgumentException();
/*  52:    */       }
/*  53:104 */       if (name == null) {
/*  54:105 */         throw new IllegalArgumentException();
/*  55:    */       }
/*  56:106 */       if (value == Scriptable.NOT_FOUND) {
/*  57:107 */         throw new IllegalArgumentException();
/*  58:    */       }
/*  59:108 */       ScriptableObject.checkValidAttributes(attributes);
/*  60:109 */       if (this.obj.findPrototypeId(name) != id) {
/*  61:110 */         throw new IllegalArgumentException(name);
/*  62:    */       }
/*  63:112 */       if (id == this.constructorId)
/*  64:    */       {
/*  65:113 */         if (!(value instanceof IdFunctionObject)) {
/*  66:114 */           throw new IllegalArgumentException("consructor should be initialized with IdFunctionObject");
/*  67:    */         }
/*  68:116 */         this.constructor = ((IdFunctionObject)value);
/*  69:117 */         this.constructorAttrs = ((short)attributes);
/*  70:118 */         return;
/*  71:    */       }
/*  72:121 */       initSlot(id, name, value, attributes);
/*  73:    */     }
/*  74:    */     
/*  75:    */     private void initSlot(int id, String name, Object value, int attributes)
/*  76:    */     {
/*  77:127 */       Object[] array = this.valueArray;
/*  78:128 */       if (array == null) {
/*  79:129 */         throw new IllegalStateException();
/*  80:    */       }
/*  81:131 */       if (value == null) {
/*  82:132 */         value = UniqueTag.NULL_VALUE;
/*  83:    */       }
/*  84:134 */       int index = (id - 1) * 2;
/*  85:135 */       synchronized (this)
/*  86:    */       {
/*  87:136 */         Object value2 = array[(index + 0)];
/*  88:137 */         if (value2 == null)
/*  89:    */         {
/*  90:138 */           array[(index + 0)] = value;
/*  91:139 */           array[(index + 1)] = name;
/*  92:140 */           this.attributeArray[(id - 1)] = ((short)attributes);
/*  93:    */         }
/*  94:142 */         else if (!name.equals(array[(index + 1)]))
/*  95:    */         {
/*  96:143 */           throw new IllegalStateException();
/*  97:    */         }
/*  98:    */       }
/*  99:    */     }
/* 100:    */     
/* 101:    */     final IdFunctionObject createPrecachedConstructor()
/* 102:    */     {
/* 103:150 */       if (this.constructorId != 0) {
/* 104:150 */         throw new IllegalStateException();
/* 105:    */       }
/* 106:151 */       this.constructorId = this.obj.findPrototypeId("constructor");
/* 107:152 */       if (this.constructorId == 0) {
/* 108:153 */         throw new IllegalStateException("No id for constructor property");
/* 109:    */       }
/* 110:156 */       this.obj.initPrototypeId(this.constructorId);
/* 111:157 */       if (this.constructor == null) {
/* 112:158 */         throw new IllegalStateException(this.obj.getClass().getName() + ".initPrototypeId() did not " + "initialize id=" + this.constructorId);
/* 113:    */       }
/* 114:162 */       this.constructor.initFunction(this.obj.getClassName(), ScriptableObject.getTopLevelScope(this.obj));
/* 115:    */       
/* 116:164 */       this.constructor.markAsConstructor(this.obj);
/* 117:165 */       return this.constructor;
/* 118:    */     }
/* 119:    */     
/* 120:    */     final int findId(String name)
/* 121:    */     {
/* 122:170 */       Object[] array = this.valueArray;
/* 123:171 */       if (array == null) {
/* 124:172 */         return this.obj.findPrototypeId(name);
/* 125:    */       }
/* 126:174 */       int id = this.lastFoundId;
/* 127:175 */       if (name == array[((id - 1) * 2 + 1)]) {
/* 128:176 */         return id;
/* 129:    */       }
/* 130:178 */       id = this.obj.findPrototypeId(name);
/* 131:179 */       if (id != 0)
/* 132:    */       {
/* 133:180 */         int nameSlot = (id - 1) * 2 + 1;
/* 134:    */         
/* 135:182 */         array[nameSlot] = name;
/* 136:183 */         this.lastFoundId = id;
/* 137:    */       }
/* 138:185 */       return id;
/* 139:    */     }
/* 140:    */     
/* 141:    */     final boolean has(int id)
/* 142:    */     {
/* 143:190 */       Object[] array = this.valueArray;
/* 144:191 */       if (array == null) {
/* 145:193 */         return true;
/* 146:    */       }
/* 147:195 */       int valueSlot = (id - 1) * 2 + 0;
/* 148:196 */       Object value = array[valueSlot];
/* 149:197 */       if (value == null) {
/* 150:199 */         return true;
/* 151:    */       }
/* 152:201 */       return value != Scriptable.NOT_FOUND;
/* 153:    */     }
/* 154:    */     
/* 155:    */     final Object get(int id)
/* 156:    */     {
/* 157:206 */       Object value = ensureId(id);
/* 158:207 */       if (value == UniqueTag.NULL_VALUE) {
/* 159:208 */         value = null;
/* 160:    */       }
/* 161:210 */       return value;
/* 162:    */     }
/* 163:    */     
/* 164:    */     final void set(int id, Scriptable start, Object value)
/* 165:    */     {
/* 166:215 */       if (value == Scriptable.NOT_FOUND) {
/* 167:215 */         throw new IllegalArgumentException();
/* 168:    */       }
/* 169:216 */       ensureId(id);
/* 170:217 */       int attr = this.attributeArray[(id - 1)];
/* 171:218 */       if ((attr & 0x1) == 0) {
/* 172:219 */         if (start == this.obj)
/* 173:    */         {
/* 174:220 */           if (value == null) {
/* 175:221 */             value = UniqueTag.NULL_VALUE;
/* 176:    */           }
/* 177:223 */           int valueSlot = (id - 1) * 2 + 0;
/* 178:224 */           synchronized (this)
/* 179:    */           {
/* 180:225 */             this.valueArray[valueSlot] = value;
/* 181:    */           }
/* 182:    */         }
/* 183:    */         else
/* 184:    */         {
/* 185:229 */           int nameSlot = (id - 1) * 2 + 1;
/* 186:230 */           String name = (String)this.valueArray[nameSlot];
/* 187:231 */           start.put(name, start, value);
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:    */     
/* 192:    */     final void delete(int id)
/* 193:    */     {
/* 194:238 */       ensureId(id);
/* 195:239 */       int attr = this.attributeArray[(id - 1)];
/* 196:240 */       if ((attr & 0x4) == 0)
/* 197:    */       {
/* 198:241 */         int valueSlot = (id - 1) * 2 + 0;
/* 199:242 */         synchronized (this)
/* 200:    */         {
/* 201:243 */           this.valueArray[valueSlot] = Scriptable.NOT_FOUND;
/* 202:244 */           this.attributeArray[(id - 1)] = 0;
/* 203:    */         }
/* 204:    */       }
/* 205:    */     }
/* 206:    */     
/* 207:    */     final int getAttributes(int id)
/* 208:    */     {
/* 209:251 */       ensureId(id);
/* 210:252 */       return this.attributeArray[(id - 1)];
/* 211:    */     }
/* 212:    */     
/* 213:    */     final void setAttributes(int id, int attributes)
/* 214:    */     {
/* 215:257 */       ScriptableObject.checkValidAttributes(attributes);
/* 216:258 */       ensureId(id);
/* 217:259 */       synchronized (this)
/* 218:    */       {
/* 219:260 */         this.attributeArray[(id - 1)] = ((short)attributes);
/* 220:    */       }
/* 221:    */     }
/* 222:    */     
/* 223:    */     final Object[] getNames(boolean getAll, Object[] extraEntries)
/* 224:    */     {
/* 225:266 */       Object[] names = null;
/* 226:267 */       int count = 0;
/* 227:268 */       for (int id = 1; id <= this.maxId; id++)
/* 228:    */       {
/* 229:269 */         Object value = ensureId(id);
/* 230:270 */         if (((getAll) || ((this.attributeArray[(id - 1)] & 0x2) == 0)) && 
/* 231:271 */           (value != Scriptable.NOT_FOUND))
/* 232:    */         {
/* 233:272 */           int nameSlot = (id - 1) * 2 + 1;
/* 234:273 */           String name = (String)this.valueArray[nameSlot];
/* 235:274 */           if (names == null) {
/* 236:275 */             names = new Object[this.maxId];
/* 237:    */           }
/* 238:277 */           names[(count++)] = name;
/* 239:    */         }
/* 240:    */       }
/* 241:281 */       if (count == 0) {
/* 242:282 */         return extraEntries;
/* 243:    */       }
/* 244:283 */       if ((extraEntries == null) || (extraEntries.length == 0))
/* 245:    */       {
/* 246:284 */         if (count != names.length)
/* 247:    */         {
/* 248:285 */           Object[] tmp = new Object[count];
/* 249:286 */           System.arraycopy(names, 0, tmp, 0, count);
/* 250:287 */           names = tmp;
/* 251:    */         }
/* 252:289 */         return names;
/* 253:    */       }
/* 254:291 */       int extra = extraEntries.length;
/* 255:292 */       Object[] tmp = new Object[extra + count];
/* 256:293 */       System.arraycopy(extraEntries, 0, tmp, 0, extra);
/* 257:294 */       System.arraycopy(names, 0, tmp, extra, count);
/* 258:295 */       return tmp;
/* 259:    */     }
/* 260:    */     
/* 261:    */     private Object ensureId(int id)
/* 262:    */     {
/* 263:301 */       Object[] array = this.valueArray;
/* 264:302 */       if (array == null) {
/* 265:303 */         synchronized (this)
/* 266:    */         {
/* 267:304 */           array = this.valueArray;
/* 268:305 */           if (array == null)
/* 269:    */           {
/* 270:306 */             array = new Object[this.maxId * 2];
/* 271:307 */             this.valueArray = array;
/* 272:308 */             this.attributeArray = new short[this.maxId];
/* 273:    */           }
/* 274:    */         }
/* 275:    */       }
/* 276:312 */       int valueSlot = (id - 1) * 2 + 0;
/* 277:313 */       Object value = array[valueSlot];
/* 278:314 */       if (value == null)
/* 279:    */       {
/* 280:315 */         if (id == this.constructorId)
/* 281:    */         {
/* 282:316 */           initSlot(this.constructorId, "constructor", this.constructor, this.constructorAttrs);
/* 283:    */           
/* 284:318 */           this.constructor = null;
/* 285:    */         }
/* 286:    */         else
/* 287:    */         {
/* 288:320 */           this.obj.initPrototypeId(id);
/* 289:    */         }
/* 290:322 */         value = array[valueSlot];
/* 291:323 */         if (value == null) {
/* 292:324 */           throw new IllegalStateException(this.obj.getClass().getName() + ".initPrototypeId(int id) " + "did not initialize id=" + id);
/* 293:    */         }
/* 294:    */       }
/* 295:329 */       return value;
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public IdScriptableObject(Scriptable scope, Scriptable prototype)
/* 300:    */   {
/* 301:339 */     super(scope, prototype);
/* 302:    */   }
/* 303:    */   
/* 304:    */   protected final Object defaultGet(String name)
/* 305:    */   {
/* 306:344 */     return super.get(name, this);
/* 307:    */   }
/* 308:    */   
/* 309:    */   protected final void defaultPut(String name, Object value)
/* 310:    */   {
/* 311:349 */     super.put(name, this, value);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public boolean has(String name, Scriptable start)
/* 315:    */   {
/* 316:355 */     int info = findInstanceIdInfo(name);
/* 317:356 */     if (info != 0)
/* 318:    */     {
/* 319:357 */       int attr = info >>> 16;
/* 320:358 */       if ((attr & 0x4) != 0) {
/* 321:359 */         return true;
/* 322:    */       }
/* 323:361 */       int id = info & 0xFFFF;
/* 324:362 */       return NOT_FOUND != getInstanceIdValue(id);
/* 325:    */     }
/* 326:364 */     if (this.prototypeValues != null)
/* 327:    */     {
/* 328:365 */       int id = this.prototypeValues.findId(name);
/* 329:366 */       if (id != 0) {
/* 330:367 */         return this.prototypeValues.has(id);
/* 331:    */       }
/* 332:    */     }
/* 333:370 */     return super.has(name, start);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public Object get(String name, Scriptable start)
/* 337:    */   {
/* 338:376 */     int info = findInstanceIdInfo(name);
/* 339:377 */     if (info != 0)
/* 340:    */     {
/* 341:378 */       int id = info & 0xFFFF;
/* 342:379 */       Object value = getInstanceIdValue(id);
/* 343:380 */       if (value != NOT_FOUND) {
/* 344:380 */         return value;
/* 345:    */       }
/* 346:    */     }
/* 347:382 */     if (this.prototypeValues != null)
/* 348:    */     {
/* 349:383 */       int id = this.prototypeValues.findId(name);
/* 350:384 */       if (id != 0)
/* 351:    */       {
/* 352:385 */         Object value = this.prototypeValues.get(id);
/* 353:386 */         if (value != NOT_FOUND) {
/* 354:386 */           return value;
/* 355:    */         }
/* 356:    */       }
/* 357:    */     }
/* 358:389 */     return super.get(name, start);
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void put(String name, Scriptable start, Object value)
/* 362:    */   {
/* 363:395 */     int info = findInstanceIdInfo(name);
/* 364:396 */     if (info != 0)
/* 365:    */     {
/* 366:397 */       if ((start == this) && (isSealed())) {
/* 367:398 */         throw Context.reportRuntimeError1("msg.modify.sealed", name);
/* 368:    */       }
/* 369:401 */       int attr = info >>> 16;
/* 370:402 */       if ((attr & 0x1) == 0) {
/* 371:403 */         if (start == this)
/* 372:    */         {
/* 373:404 */           int id = info & 0xFFFF;
/* 374:405 */           setInstanceIdValue(id, value);
/* 375:    */         }
/* 376:    */         else
/* 377:    */         {
/* 378:408 */           start.put(name, start, value);
/* 379:    */         }
/* 380:    */       }
/* 381:411 */       return;
/* 382:    */     }
/* 383:413 */     if (this.prototypeValues != null)
/* 384:    */     {
/* 385:414 */       int id = this.prototypeValues.findId(name);
/* 386:415 */       if (id != 0)
/* 387:    */       {
/* 388:416 */         if ((start == this) && (isSealed())) {
/* 389:417 */           throw Context.reportRuntimeError1("msg.modify.sealed", name);
/* 390:    */         }
/* 391:420 */         this.prototypeValues.set(id, start, value);
/* 392:421 */         return;
/* 393:    */       }
/* 394:    */     }
/* 395:424 */     super.put(name, start, value);
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void delete(String name)
/* 399:    */   {
/* 400:430 */     int info = findInstanceIdInfo(name);
/* 401:431 */     if (info != 0) {
/* 402:433 */       if (!isSealed())
/* 403:    */       {
/* 404:434 */         int attr = info >>> 16;
/* 405:435 */         if ((attr & 0x4) == 0)
/* 406:    */         {
/* 407:436 */           int id = info & 0xFFFF;
/* 408:437 */           setInstanceIdValue(id, NOT_FOUND);
/* 409:    */         }
/* 410:439 */         return;
/* 411:    */       }
/* 412:    */     }
/* 413:442 */     if (this.prototypeValues != null)
/* 414:    */     {
/* 415:443 */       int id = this.prototypeValues.findId(name);
/* 416:444 */       if (id != 0)
/* 417:    */       {
/* 418:445 */         if (!isSealed()) {
/* 419:446 */           this.prototypeValues.delete(id);
/* 420:    */         }
/* 421:448 */         return;
/* 422:    */       }
/* 423:    */     }
/* 424:451 */     super.delete(name);
/* 425:    */   }
/* 426:    */   
/* 427:    */   public int getAttributes(String name)
/* 428:    */   {
/* 429:457 */     int info = findInstanceIdInfo(name);
/* 430:458 */     if (info != 0)
/* 431:    */     {
/* 432:459 */       int attr = info >>> 16;
/* 433:460 */       return attr;
/* 434:    */     }
/* 435:462 */     if (this.prototypeValues != null)
/* 436:    */     {
/* 437:463 */       int id = this.prototypeValues.findId(name);
/* 438:464 */       if (id != 0) {
/* 439:465 */         return this.prototypeValues.getAttributes(id);
/* 440:    */       }
/* 441:    */     }
/* 442:468 */     return super.getAttributes(name);
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setAttributes(String name, int attributes)
/* 446:    */   {
/* 447:474 */     ScriptableObject.checkValidAttributes(attributes);
/* 448:475 */     int info = findInstanceIdInfo(name);
/* 449:476 */     if (info != 0)
/* 450:    */     {
/* 451:477 */       int currentAttributes = info >>> 16;
/* 452:478 */       if (attributes != currentAttributes) {
/* 453:479 */         throw new RuntimeException("Change of attributes for this id is not supported");
/* 454:    */       }
/* 455:482 */       return;
/* 456:    */     }
/* 457:484 */     if (this.prototypeValues != null)
/* 458:    */     {
/* 459:485 */       int id = this.prototypeValues.findId(name);
/* 460:486 */       if (id != 0)
/* 461:    */       {
/* 462:487 */         this.prototypeValues.setAttributes(id, attributes);
/* 463:488 */         return;
/* 464:    */       }
/* 465:    */     }
/* 466:491 */     super.setAttributes(name, attributes);
/* 467:    */   }
/* 468:    */   
/* 469:    */   Object[] getIds(boolean getAll)
/* 470:    */   {
/* 471:497 */     Object[] result = super.getIds(getAll);
/* 472:499 */     if (this.prototypeValues != null) {
/* 473:500 */       result = this.prototypeValues.getNames(getAll, result);
/* 474:    */     }
/* 475:503 */     int maxInstanceId = getMaxInstanceId();
/* 476:504 */     if (maxInstanceId != 0)
/* 477:    */     {
/* 478:505 */       Object[] ids = null;
/* 479:506 */       int count = 0;
/* 480:508 */       for (int id = maxInstanceId; id != 0; id--)
/* 481:    */       {
/* 482:509 */         String name = getInstanceIdName(id);
/* 483:510 */         int info = findInstanceIdInfo(name);
/* 484:511 */         if (info != 0)
/* 485:    */         {
/* 486:512 */           int attr = info >>> 16;
/* 487:513 */           if (((attr & 0x4) != 0) || 
/* 488:514 */             (NOT_FOUND != getInstanceIdValue(id))) {
/* 489:518 */             if ((getAll) || ((attr & 0x2) == 0))
/* 490:    */             {
/* 491:519 */               if (count == 0) {
/* 492:521 */                 ids = new Object[id];
/* 493:    */               }
/* 494:523 */               ids[(count++)] = name;
/* 495:    */             }
/* 496:    */           }
/* 497:    */         }
/* 498:    */       }
/* 499:527 */       if (count != 0) {
/* 500:528 */         if ((result.length == 0) && (ids.length == count))
/* 501:    */         {
/* 502:529 */           result = ids;
/* 503:    */         }
/* 504:    */         else
/* 505:    */         {
/* 506:532 */           Object[] tmp = new Object[result.length + count];
/* 507:533 */           System.arraycopy(result, 0, tmp, 0, result.length);
/* 508:534 */           System.arraycopy(ids, 0, tmp, result.length, count);
/* 509:535 */           result = tmp;
/* 510:    */         }
/* 511:    */       }
/* 512:    */     }
/* 513:539 */     return result;
/* 514:    */   }
/* 515:    */   
/* 516:    */   protected int getMaxInstanceId()
/* 517:    */   {
/* 518:547 */     return 0;
/* 519:    */   }
/* 520:    */   
/* 521:    */   protected static int instanceIdInfo(int attributes, int id)
/* 522:    */   {
/* 523:552 */     return attributes << 16 | id;
/* 524:    */   }
/* 525:    */   
/* 526:    */   protected int findInstanceIdInfo(String name)
/* 527:    */   {
/* 528:562 */     return 0;
/* 529:    */   }
/* 530:    */   
/* 531:    */   protected String getInstanceIdName(int id)
/* 532:    */   {
/* 533:569 */     throw new IllegalArgumentException(String.valueOf(id));
/* 534:    */   }
/* 535:    */   
/* 536:    */   protected Object getInstanceIdValue(int id)
/* 537:    */   {
/* 538:580 */     throw new IllegalStateException(String.valueOf(id));
/* 539:    */   }
/* 540:    */   
/* 541:    */   protected void setInstanceIdValue(int id, Object value)
/* 542:    */   {
/* 543:589 */     throw new IllegalStateException(String.valueOf(id));
/* 544:    */   }
/* 545:    */   
/* 546:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 547:    */   {
/* 548:597 */     throw f.unknown();
/* 549:    */   }
/* 550:    */   
/* 551:    */   public final IdFunctionObject exportAsJSClass(int maxPrototypeId, Scriptable scope, boolean sealed)
/* 552:    */   {
/* 553:605 */     if ((scope != this) && (scope != null))
/* 554:    */     {
/* 555:606 */       setParentScope(scope);
/* 556:607 */       setPrototype(getObjectPrototype(scope));
/* 557:    */     }
/* 558:610 */     activatePrototypeMap(maxPrototypeId);
/* 559:611 */     IdFunctionObject ctor = this.prototypeValues.createPrecachedConstructor();
/* 560:612 */     if (sealed) {
/* 561:613 */       sealObject();
/* 562:    */     }
/* 563:615 */     fillConstructorProperties(ctor);
/* 564:616 */     if (sealed) {
/* 565:617 */       ctor.sealObject();
/* 566:    */     }
/* 567:619 */     ctor.exportAsScopeProperty();
/* 568:620 */     return ctor;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public final boolean hasPrototypeMap()
/* 572:    */   {
/* 573:625 */     return this.prototypeValues != null;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public final void activatePrototypeMap(int maxPrototypeId)
/* 577:    */   {
/* 578:630 */     PrototypeValues values = new PrototypeValues(this, maxPrototypeId);
/* 579:631 */     synchronized (this)
/* 580:    */     {
/* 581:632 */       if (this.prototypeValues != null) {
/* 582:633 */         throw new IllegalStateException();
/* 583:    */       }
/* 584:634 */       this.prototypeValues = values;
/* 585:    */     }
/* 586:    */   }
/* 587:    */   
/* 588:    */   public final void initPrototypeMethod(Object tag, int id, String name, int arity)
/* 589:    */   {
/* 590:641 */     Scriptable scope = ScriptableObject.getTopLevelScope(this);
/* 591:642 */     IdFunctionObject f = newIdFunction(tag, id, name, arity, scope);
/* 592:643 */     this.prototypeValues.initValue(id, name, f, 2);
/* 593:    */   }
/* 594:    */   
/* 595:    */   public final void initPrototypeConstructor(IdFunctionObject f)
/* 596:    */   {
/* 597:648 */     int id = this.prototypeValues.constructorId;
/* 598:649 */     if (id == 0) {
/* 599:650 */       throw new IllegalStateException();
/* 600:    */     }
/* 601:651 */     if (f.methodId() != id) {
/* 602:652 */       throw new IllegalArgumentException();
/* 603:    */     }
/* 604:653 */     if (isSealed()) {
/* 605:653 */       f.sealObject();
/* 606:    */     }
/* 607:654 */     this.prototypeValues.initValue(id, "constructor", f, 2);
/* 608:    */   }
/* 609:    */   
/* 610:    */   public final void initPrototypeValue(int id, String name, Object value, int attributes)
/* 611:    */   {
/* 612:660 */     this.prototypeValues.initValue(id, name, value, attributes);
/* 613:    */   }
/* 614:    */   
/* 615:    */   protected void initPrototypeId(int id)
/* 616:    */   {
/* 617:665 */     throw new IllegalStateException(String.valueOf(id));
/* 618:    */   }
/* 619:    */   
/* 620:    */   protected int findPrototypeId(String name)
/* 621:    */   {
/* 622:670 */     throw new IllegalStateException(name);
/* 623:    */   }
/* 624:    */   
/* 625:    */   protected void fillConstructorProperties(IdFunctionObject ctor) {}
/* 626:    */   
/* 627:    */   protected void addIdFunctionProperty(Scriptable obj, Object tag, int id, String name, int arity)
/* 628:    */   {
/* 629:680 */     Scriptable scope = ScriptableObject.getTopLevelScope(obj);
/* 630:681 */     IdFunctionObject f = newIdFunction(tag, id, name, arity, scope);
/* 631:682 */     f.addAsProperty(obj);
/* 632:    */   }
/* 633:    */   
/* 634:    */   protected static EcmaError incompatibleCallError(IdFunctionObject f)
/* 635:    */   {
/* 636:708 */     throw ScriptRuntime.typeError1("msg.incompat.call", f.getFunctionName());
/* 637:    */   }
/* 638:    */   
/* 639:    */   private IdFunctionObject newIdFunction(Object tag, int id, String name, int arity, Scriptable scope)
/* 640:    */   {
/* 641:715 */     IdFunctionObject f = new IdFunctionObject(this, tag, id, name, arity, scope);
/* 642:717 */     if (isSealed()) {
/* 643:717 */       f.sealObject();
/* 644:    */     }
/* 645:718 */     return f;
/* 646:    */   }
/* 647:    */   
/* 648:    */   public void defineOwnProperty(Context cx, Object key, ScriptableObject desc)
/* 649:    */   {
/* 650:723 */     if ((key instanceof String))
/* 651:    */     {
/* 652:724 */       String name = (String)key;
/* 653:725 */       int info = findInstanceIdInfo(name);
/* 654:726 */       if (info != 0)
/* 655:    */       {
/* 656:727 */         int id = info & 0xFFFF;
/* 657:728 */         if (isAccessorDescriptor(desc))
/* 658:    */         {
/* 659:729 */           delete(id);
/* 660:    */         }
/* 661:    */         else
/* 662:    */         {
/* 663:731 */           int attr = info >>> 16;
/* 664:732 */           Object value = getProperty(desc, "value");
/* 665:733 */           setInstanceIdValue(id, value == NOT_FOUND ? Undefined.instance : value);
/* 666:734 */           setAttributes(id, applyDescriptorToAttributeBitset(attr, desc));
/* 667:735 */           return;
/* 668:    */         }
/* 669:    */       }
/* 670:738 */       if (this.prototypeValues != null)
/* 671:    */       {
/* 672:739 */         int id = this.prototypeValues.findId(name);
/* 673:740 */         if (id != 0) {
/* 674:741 */           if (isAccessorDescriptor(desc))
/* 675:    */           {
/* 676:742 */             this.prototypeValues.delete(id);
/* 677:    */           }
/* 678:    */           else
/* 679:    */           {
/* 680:744 */             int attr = this.prototypeValues.getAttributes(id);
/* 681:745 */             Object value = getProperty(desc, "value");
/* 682:746 */             this.prototypeValues.set(id, this, value == NOT_FOUND ? Undefined.instance : value);
/* 683:747 */             this.prototypeValues.setAttributes(id, applyDescriptorToAttributeBitset(attr, desc));
/* 684:748 */             return;
/* 685:    */           }
/* 686:    */         }
/* 687:    */       }
/* 688:    */     }
/* 689:753 */     super.defineOwnProperty(cx, key, desc);
/* 690:    */   }
/* 691:    */   
/* 692:    */   protected ScriptableObject getOwnPropertyDescriptor(Context cx, Object id)
/* 693:    */   {
/* 694:759 */     ScriptableObject desc = super.getOwnPropertyDescriptor(cx, id);
/* 695:760 */     if ((desc == null) && ((id instanceof String))) {
/* 696:761 */       desc = getBuiltInDescriptor((String)id);
/* 697:    */     }
/* 698:763 */     return desc;
/* 699:    */   }
/* 700:    */   
/* 701:    */   private ScriptableObject getBuiltInDescriptor(String name)
/* 702:    */   {
/* 703:767 */     Object value = null;
/* 704:768 */     int attr = 0;
/* 705:    */     
/* 706:770 */     Scriptable scope = getParentScope();
/* 707:771 */     if (scope == null) {
/* 708:772 */       scope = this;
/* 709:    */     }
/* 710:775 */     int info = findInstanceIdInfo(name);
/* 711:776 */     if (info != 0)
/* 712:    */     {
/* 713:777 */       int id = info & 0xFFFF;
/* 714:778 */       value = getInstanceIdValue(id);
/* 715:779 */       attr = info >>> 16;
/* 716:780 */       return buildDataDescriptor(scope, value, attr);
/* 717:    */     }
/* 718:782 */     if (this.prototypeValues != null)
/* 719:    */     {
/* 720:783 */       int id = this.prototypeValues.findId(name);
/* 721:784 */       if (id != 0)
/* 722:    */       {
/* 723:785 */         value = this.prototypeValues.get(id);
/* 724:786 */         attr = this.prototypeValues.getAttributes(id);
/* 725:787 */         return buildDataDescriptor(scope, value, attr);
/* 726:    */       }
/* 727:    */     }
/* 728:790 */     return null;
/* 729:    */   }
/* 730:    */   
/* 731:    */   private void readObject(ObjectInputStream stream)
/* 732:    */     throws IOException, ClassNotFoundException
/* 733:    */   {
/* 734:796 */     stream.defaultReadObject();
/* 735:797 */     int maxPrototypeId = stream.readInt();
/* 736:798 */     if (maxPrototypeId != 0) {
/* 737:799 */       activatePrototypeMap(maxPrototypeId);
/* 738:    */     }
/* 739:    */   }
/* 740:    */   
/* 741:    */   private void writeObject(ObjectOutputStream stream)
/* 742:    */     throws IOException
/* 743:    */   {
/* 744:806 */     stream.defaultWriteObject();
/* 745:807 */     int maxPrototypeId = 0;
/* 746:808 */     if (this.prototypeValues != null) {
/* 747:809 */       maxPrototypeId = this.prototypeValues.getMaxId();
/* 748:    */     }
/* 749:811 */     stream.writeInt(maxPrototypeId);
/* 750:    */   }
/* 751:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.IdScriptableObject
 * JD-Core Version:    0.7.0.1
 */