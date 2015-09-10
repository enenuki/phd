/*   1:    */ package javassist.bytecode.analysis;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.IdentityHashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import javassist.ClassPool;
/*  11:    */ import javassist.CtClass;
/*  12:    */ import javassist.NotFoundException;
/*  13:    */ 
/*  14:    */ public class Type
/*  15:    */ {
/*  16:    */   private final CtClass clazz;
/*  17:    */   private final boolean special;
/*  18: 46 */   private static final Map prims = new IdentityHashMap();
/*  19: 48 */   public static final Type DOUBLE = new Type(CtClass.doubleType);
/*  20: 50 */   public static final Type BOOLEAN = new Type(CtClass.booleanType);
/*  21: 52 */   public static final Type LONG = new Type(CtClass.longType);
/*  22: 54 */   public static final Type CHAR = new Type(CtClass.charType);
/*  23: 56 */   public static final Type BYTE = new Type(CtClass.byteType);
/*  24: 58 */   public static final Type SHORT = new Type(CtClass.shortType);
/*  25: 60 */   public static final Type INTEGER = new Type(CtClass.intType);
/*  26: 62 */   public static final Type FLOAT = new Type(CtClass.floatType);
/*  27: 64 */   public static final Type VOID = new Type(CtClass.voidType);
/*  28: 75 */   public static final Type UNINIT = new Type(null);
/*  29: 81 */   public static final Type RETURN_ADDRESS = new Type(null, true);
/*  30: 84 */   public static final Type TOP = new Type(null, true);
/*  31: 93 */   public static final Type BOGUS = new Type(null, true);
/*  32: 96 */   public static final Type OBJECT = lookupType("java.lang.Object");
/*  33: 98 */   public static final Type SERIALIZABLE = lookupType("java.io.Serializable");
/*  34:100 */   public static final Type CLONEABLE = lookupType("java.lang.Cloneable");
/*  35:102 */   public static final Type THROWABLE = lookupType("java.lang.Throwable");
/*  36:    */   
/*  37:    */   static
/*  38:    */   {
/*  39:105 */     prims.put(CtClass.doubleType, DOUBLE);
/*  40:106 */     prims.put(CtClass.longType, LONG);
/*  41:107 */     prims.put(CtClass.charType, CHAR);
/*  42:108 */     prims.put(CtClass.shortType, SHORT);
/*  43:109 */     prims.put(CtClass.intType, INTEGER);
/*  44:110 */     prims.put(CtClass.floatType, FLOAT);
/*  45:111 */     prims.put(CtClass.byteType, BYTE);
/*  46:112 */     prims.put(CtClass.booleanType, BOOLEAN);
/*  47:113 */     prims.put(CtClass.voidType, VOID);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Type get(CtClass clazz)
/*  51:    */   {
/*  52:126 */     Type type = (Type)prims.get(clazz);
/*  53:127 */     return type != null ? type : new Type(clazz);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static Type lookupType(String name)
/*  57:    */   {
/*  58:    */     try
/*  59:    */     {
/*  60:132 */       return new Type(ClassPool.getDefault().get(name));
/*  61:    */     }
/*  62:    */     catch (NotFoundException e)
/*  63:    */     {
/*  64:134 */       throw new RuntimeException(e);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   Type(CtClass clazz)
/*  69:    */   {
/*  70:139 */     this(clazz, false);
/*  71:    */   }
/*  72:    */   
/*  73:    */   private Type(CtClass clazz, boolean special)
/*  74:    */   {
/*  75:143 */     this.clazz = clazz;
/*  76:144 */     this.special = special;
/*  77:    */   }
/*  78:    */   
/*  79:    */   boolean popChanged()
/*  80:    */   {
/*  81:149 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getSize()
/*  85:    */   {
/*  86:159 */     return (this.clazz == CtClass.doubleType) || (this.clazz == CtClass.longType) || (this == TOP) ? 2 : 1;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public CtClass getCtClass()
/*  90:    */   {
/*  91:168 */     return this.clazz;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isReference()
/*  95:    */   {
/*  96:177 */     return (!this.special) && ((this.clazz == null) || (!this.clazz.isPrimitive()));
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isSpecial()
/* 100:    */   {
/* 101:187 */     return this.special;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isArray()
/* 105:    */   {
/* 106:196 */     return (this.clazz != null) && (this.clazz.isArray());
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getDimensions()
/* 110:    */   {
/* 111:206 */     if (!isArray()) {
/* 112:206 */       return 0;
/* 113:    */     }
/* 114:208 */     String name = this.clazz.getName();
/* 115:209 */     int pos = name.length() - 1;
/* 116:210 */     int count = 0;
/* 117:211 */     while (name.charAt(pos) == ']')
/* 118:    */     {
/* 119:212 */       pos -= 2;
/* 120:213 */       count++;
/* 121:    */     }
/* 122:216 */     return count;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Type getComponent()
/* 126:    */   {
/* 127:226 */     if ((this.clazz == null) || (!this.clazz.isArray())) {
/* 128:227 */       return null;
/* 129:    */     }
/* 130:    */     CtClass component;
/* 131:    */     try
/* 132:    */     {
/* 133:231 */       component = this.clazz.getComponentType();
/* 134:    */     }
/* 135:    */     catch (NotFoundException e)
/* 136:    */     {
/* 137:233 */       throw new RuntimeException(e);
/* 138:    */     }
/* 139:236 */     Type type = (Type)prims.get(component);
/* 140:237 */     return type != null ? type : new Type(component);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isAssignableFrom(Type type)
/* 144:    */   {
/* 145:249 */     if (this == type) {
/* 146:250 */       return true;
/* 147:    */     }
/* 148:252 */     if (((type == UNINIT) && (isReference())) || ((this == UNINIT) && (type.isReference()))) {
/* 149:253 */       return true;
/* 150:    */     }
/* 151:255 */     if ((type instanceof MultiType)) {
/* 152:256 */       return ((MultiType)type).isAssignableTo(this);
/* 153:    */     }
/* 154:258 */     if ((type instanceof MultiArrayType)) {
/* 155:259 */       return ((MultiArrayType)type).isAssignableTo(this);
/* 156:    */     }
/* 157:263 */     if ((this.clazz == null) || (this.clazz.isPrimitive())) {
/* 158:264 */       return false;
/* 159:    */     }
/* 160:    */     try
/* 161:    */     {
/* 162:267 */       return type.clazz.subtypeOf(this.clazz);
/* 163:    */     }
/* 164:    */     catch (Exception e)
/* 165:    */     {
/* 166:269 */       throw new RuntimeException(e);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Type merge(Type type)
/* 171:    */   {
/* 172:285 */     if (type == this) {
/* 173:286 */       return this;
/* 174:    */     }
/* 175:287 */     if (type == null) {
/* 176:288 */       return this;
/* 177:    */     }
/* 178:289 */     if (type == UNINIT) {
/* 179:290 */       return this;
/* 180:    */     }
/* 181:291 */     if (this == UNINIT) {
/* 182:292 */       return type;
/* 183:    */     }
/* 184:295 */     if ((!type.isReference()) || (!isReference())) {
/* 185:296 */       return BOGUS;
/* 186:    */     }
/* 187:299 */     if ((type instanceof MultiType)) {
/* 188:300 */       return type.merge(this);
/* 189:    */     }
/* 190:302 */     if ((type.isArray()) && (isArray())) {
/* 191:303 */       return mergeArray(type);
/* 192:    */     }
/* 193:    */     try
/* 194:    */     {
/* 195:306 */       return mergeClasses(type);
/* 196:    */     }
/* 197:    */     catch (NotFoundException e)
/* 198:    */     {
/* 199:308 */       throw new RuntimeException(e);
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   Type getRootComponent(Type type)
/* 204:    */   {
/* 205:313 */     while (type.isArray()) {
/* 206:314 */       type = type.getComponent();
/* 207:    */     }
/* 208:316 */     return type;
/* 209:    */   }
/* 210:    */   
/* 211:    */   private Type createArray(Type rootComponent, int dims)
/* 212:    */   {
/* 213:320 */     if ((rootComponent instanceof MultiType)) {
/* 214:321 */       return new MultiArrayType((MultiType)rootComponent, dims);
/* 215:    */     }
/* 216:323 */     String name = arrayName(rootComponent.clazz.getName(), dims);
/* 217:    */     Type type;
/* 218:    */     try
/* 219:    */     {
/* 220:327 */       type = get(getClassPool(rootComponent).get(name));
/* 221:    */     }
/* 222:    */     catch (NotFoundException e)
/* 223:    */     {
/* 224:329 */       throw new RuntimeException(e);
/* 225:    */     }
/* 226:332 */     return type;
/* 227:    */   }
/* 228:    */   
/* 229:    */   String arrayName(String component, int dims)
/* 230:    */   {
/* 231:338 */     int i = component.length();
/* 232:339 */     int size = i + dims * 2;
/* 233:340 */     char[] string = new char[size];
/* 234:341 */     component.getChars(0, i, string, 0);
/* 235:342 */     while (i < size)
/* 236:    */     {
/* 237:343 */       string[(i++)] = '[';
/* 238:344 */       string[(i++)] = ']';
/* 239:    */     }
/* 240:346 */     component = new String(string);
/* 241:347 */     return component;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private ClassPool getClassPool(Type rootComponent)
/* 245:    */   {
/* 246:351 */     ClassPool pool = rootComponent.clazz.getClassPool();
/* 247:352 */     return pool != null ? pool : ClassPool.getDefault();
/* 248:    */   }
/* 249:    */   
/* 250:    */   private Type mergeArray(Type type)
/* 251:    */   {
/* 252:356 */     Type typeRoot = getRootComponent(type);
/* 253:357 */     Type thisRoot = getRootComponent(this);
/* 254:358 */     int typeDims = type.getDimensions();
/* 255:359 */     int thisDims = getDimensions();
/* 256:362 */     if (typeDims == thisDims)
/* 257:    */     {
/* 258:363 */       Type mergedComponent = thisRoot.merge(typeRoot);
/* 259:367 */       if (mergedComponent == BOGUS) {
/* 260:368 */         return OBJECT;
/* 261:    */       }
/* 262:370 */       return createArray(mergedComponent, thisDims);
/* 263:    */     }
/* 264:    */     int targetDims;
/* 265:    */     Type targetRoot;
/* 266:    */     int targetDims;
/* 267:376 */     if (typeDims < thisDims)
/* 268:    */     {
/* 269:377 */       Type targetRoot = typeRoot;
/* 270:378 */       targetDims = typeDims;
/* 271:    */     }
/* 272:    */     else
/* 273:    */     {
/* 274:380 */       targetRoot = thisRoot;
/* 275:381 */       targetDims = thisDims;
/* 276:    */     }
/* 277:385 */     if ((eq(CLONEABLE.clazz, targetRoot.clazz)) || (eq(SERIALIZABLE.clazz, targetRoot.clazz))) {
/* 278:386 */       return createArray(targetRoot, targetDims);
/* 279:    */     }
/* 280:388 */     return createArray(OBJECT, targetDims);
/* 281:    */   }
/* 282:    */   
/* 283:    */   private static CtClass findCommonSuperClass(CtClass one, CtClass two)
/* 284:    */     throws NotFoundException
/* 285:    */   {
/* 286:392 */     CtClass deep = one;
/* 287:393 */     CtClass shallow = two;
/* 288:394 */     CtClass backupShallow = shallow;
/* 289:395 */     CtClass backupDeep = deep;
/* 290:    */     for (;;)
/* 291:    */     {
/* 292:400 */       if ((eq(deep, shallow)) && (deep.getSuperclass() != null)) {
/* 293:401 */         return deep;
/* 294:    */       }
/* 295:403 */       CtClass deepSuper = deep.getSuperclass();
/* 296:404 */       CtClass shallowSuper = shallow.getSuperclass();
/* 297:406 */       if (shallowSuper == null)
/* 298:    */       {
/* 299:408 */         shallow = backupShallow;
/* 300:409 */         break;
/* 301:    */       }
/* 302:412 */       if (deepSuper == null)
/* 303:    */       {
/* 304:414 */         deep = backupDeep;
/* 305:415 */         backupDeep = backupShallow;
/* 306:416 */         backupShallow = deep;
/* 307:    */         
/* 308:418 */         deep = shallow;
/* 309:419 */         shallow = backupShallow;
/* 310:420 */         break;
/* 311:    */       }
/* 312:423 */       deep = deepSuper;
/* 313:424 */       shallow = shallowSuper;
/* 314:    */     }
/* 315:    */     for (;;)
/* 316:    */     {
/* 317:429 */       deep = deep.getSuperclass();
/* 318:430 */       if (deep == null) {
/* 319:    */         break;
/* 320:    */       }
/* 321:433 */       backupDeep = backupDeep.getSuperclass();
/* 322:    */     }
/* 323:436 */     deep = backupDeep;
/* 324:440 */     while (!eq(deep, shallow))
/* 325:    */     {
/* 326:441 */       deep = deep.getSuperclass();
/* 327:442 */       shallow = shallow.getSuperclass();
/* 328:    */     }
/* 329:445 */     return deep;
/* 330:    */   }
/* 331:    */   
/* 332:    */   private Type mergeClasses(Type type)
/* 333:    */     throws NotFoundException
/* 334:    */   {
/* 335:449 */     CtClass superClass = findCommonSuperClass(this.clazz, type.clazz);
/* 336:452 */     if (superClass.getSuperclass() == null)
/* 337:    */     {
/* 338:453 */       Map interfaces = findCommonInterfaces(type);
/* 339:454 */       if (interfaces.size() == 1) {
/* 340:455 */         return new Type((CtClass)interfaces.values().iterator().next());
/* 341:    */       }
/* 342:456 */       if (interfaces.size() > 1) {
/* 343:457 */         return new MultiType(interfaces);
/* 344:    */       }
/* 345:460 */       return new Type(superClass);
/* 346:    */     }
/* 347:464 */     Map commonDeclared = findExclusiveDeclaredInterfaces(type, superClass);
/* 348:465 */     if (commonDeclared.size() > 0) {
/* 349:466 */       return new MultiType(commonDeclared, new Type(superClass));
/* 350:    */     }
/* 351:469 */     return new Type(superClass);
/* 352:    */   }
/* 353:    */   
/* 354:    */   private Map findCommonInterfaces(Type type)
/* 355:    */   {
/* 356:473 */     Map typeMap = getAllInterfaces(type.clazz, null);
/* 357:474 */     Map thisMap = getAllInterfaces(this.clazz, null);
/* 358:    */     
/* 359:476 */     return findCommonInterfaces(typeMap, thisMap);
/* 360:    */   }
/* 361:    */   
/* 362:    */   private Map findExclusiveDeclaredInterfaces(Type type, CtClass exclude)
/* 363:    */   {
/* 364:480 */     Map typeMap = getDeclaredInterfaces(type.clazz, null);
/* 365:481 */     Map thisMap = getDeclaredInterfaces(this.clazz, null);
/* 366:482 */     Map excludeMap = getAllInterfaces(exclude, null);
/* 367:    */     
/* 368:484 */     Iterator i = excludeMap.keySet().iterator();
/* 369:485 */     while (i.hasNext())
/* 370:    */     {
/* 371:486 */       Object intf = i.next();
/* 372:487 */       typeMap.remove(intf);
/* 373:488 */       thisMap.remove(intf);
/* 374:    */     }
/* 375:491 */     return findCommonInterfaces(typeMap, thisMap);
/* 376:    */   }
/* 377:    */   
/* 378:    */   Map findCommonInterfaces(Map typeMap, Map alterMap)
/* 379:    */   {
/* 380:496 */     Iterator i = alterMap.keySet().iterator();
/* 381:497 */     while (i.hasNext()) {
/* 382:498 */       if (!typeMap.containsKey(i.next())) {
/* 383:499 */         i.remove();
/* 384:    */       }
/* 385:    */     }
/* 386:505 */     i = new ArrayList(alterMap.values()).iterator();
/* 387:506 */     while (i.hasNext())
/* 388:    */     {
/* 389:507 */       CtClass intf = (CtClass)i.next();
/* 390:    */       CtClass[] interfaces;
/* 391:    */       try
/* 392:    */       {
/* 393:510 */         interfaces = intf.getInterfaces();
/* 394:    */       }
/* 395:    */       catch (NotFoundException e)
/* 396:    */       {
/* 397:512 */         throw new RuntimeException(e);
/* 398:    */       }
/* 399:515 */       for (int c = 0; c < interfaces.length; c++) {
/* 400:516 */         alterMap.remove(interfaces[c].getName());
/* 401:    */       }
/* 402:    */     }
/* 403:519 */     return alterMap;
/* 404:    */   }
/* 405:    */   
/* 406:    */   Map getAllInterfaces(CtClass clazz, Map map)
/* 407:    */   {
/* 408:523 */     if (map == null) {
/* 409:524 */       map = new HashMap();
/* 410:    */     }
/* 411:526 */     if (clazz.isInterface()) {
/* 412:527 */       map.put(clazz.getName(), clazz);
/* 413:    */     }
/* 414:    */     do
/* 415:    */     {
/* 416:    */       try
/* 417:    */       {
/* 418:530 */         CtClass[] interfaces = clazz.getInterfaces();
/* 419:531 */         for (int i = 0; i < interfaces.length; i++)
/* 420:    */         {
/* 421:532 */           CtClass intf = interfaces[i];
/* 422:533 */           map.put(intf.getName(), intf);
/* 423:534 */           getAllInterfaces(intf, map);
/* 424:    */         }
/* 425:537 */         clazz = clazz.getSuperclass();
/* 426:    */       }
/* 427:    */       catch (NotFoundException e)
/* 428:    */       {
/* 429:539 */         throw new RuntimeException(e);
/* 430:    */       }
/* 431:541 */     } while (clazz != null);
/* 432:543 */     return map;
/* 433:    */   }
/* 434:    */   
/* 435:    */   Map getDeclaredInterfaces(CtClass clazz, Map map)
/* 436:    */   {
/* 437:547 */     if (map == null) {
/* 438:548 */       map = new HashMap();
/* 439:    */     }
/* 440:550 */     if (clazz.isInterface()) {
/* 441:551 */       map.put(clazz.getName(), clazz);
/* 442:    */     }
/* 443:    */     CtClass[] interfaces;
/* 444:    */     try
/* 445:    */     {
/* 446:555 */       interfaces = clazz.getInterfaces();
/* 447:    */     }
/* 448:    */     catch (NotFoundException e)
/* 449:    */     {
/* 450:557 */       throw new RuntimeException(e);
/* 451:    */     }
/* 452:560 */     for (int i = 0; i < interfaces.length; i++)
/* 453:    */     {
/* 454:561 */       CtClass intf = interfaces[i];
/* 455:562 */       map.put(intf.getName(), intf);
/* 456:563 */       getDeclaredInterfaces(intf, map);
/* 457:    */     }
/* 458:566 */     return map;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public boolean equals(Object o)
/* 462:    */   {
/* 463:570 */     if (!(o instanceof Type)) {
/* 464:571 */       return false;
/* 465:    */     }
/* 466:573 */     return (o.getClass() == getClass()) && (eq(this.clazz, ((Type)o).clazz));
/* 467:    */   }
/* 468:    */   
/* 469:    */   static boolean eq(CtClass one, CtClass two)
/* 470:    */   {
/* 471:577 */     return (one == two) || ((one != null) && (two != null) && (one.getName().equals(two.getName())));
/* 472:    */   }
/* 473:    */   
/* 474:    */   public String toString()
/* 475:    */   {
/* 476:581 */     if (this == BOGUS) {
/* 477:582 */       return "BOGUS";
/* 478:    */     }
/* 479:583 */     if (this == UNINIT) {
/* 480:584 */       return "UNINIT";
/* 481:    */     }
/* 482:585 */     if (this == RETURN_ADDRESS) {
/* 483:586 */       return "RETURN ADDRESS";
/* 484:    */     }
/* 485:587 */     if (this == TOP) {
/* 486:588 */       return "TOP";
/* 487:    */     }
/* 488:590 */     return this.clazz == null ? "null" : this.clazz.getName();
/* 489:    */   }
/* 490:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.Type
 * JD-Core Version:    0.7.0.1
 */