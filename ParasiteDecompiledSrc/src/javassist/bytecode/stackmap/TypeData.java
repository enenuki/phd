/*   1:    */ package javassist.bytecode.stackmap;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CtClass;
/*   6:    */ import javassist.NotFoundException;
/*   7:    */ import javassist.bytecode.BadBytecode;
/*   8:    */ import javassist.bytecode.ConstPool;
/*   9:    */ 
/*  10:    */ public abstract class TypeData
/*  11:    */ {
/*  12:    */   public abstract void merge(TypeData paramTypeData);
/*  13:    */   
/*  14:    */   static void setType(TypeData td, String className, ClassPool cp)
/*  15:    */     throws BadBytecode
/*  16:    */   {
/*  17: 43 */     if (td == TypeTag.TOP) {
/*  18: 44 */       throw new BadBytecode("unset variable");
/*  19:    */     }
/*  20: 46 */     td.setType(className, cp);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public abstract boolean equals(Object paramObject);
/*  24:    */   
/*  25:    */   public abstract int getTypeTag();
/*  26:    */   
/*  27:    */   public abstract int getTypeData(ConstPool paramConstPool);
/*  28:    */   
/*  29:    */   public TypeData getSelf()
/*  30:    */   {
/*  31: 57 */     return this;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public abstract TypeData copy();
/*  35:    */   
/*  36:    */   public abstract boolean isObjectType();
/*  37:    */   
/*  38:    */   public boolean is2WordType()
/*  39:    */   {
/*  40: 65 */     return false;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isNullType()
/*  44:    */   {
/*  45: 66 */     return false;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public abstract String getName()
/*  49:    */     throws BadBytecode;
/*  50:    */   
/*  51:    */   protected abstract void setType(String paramString, ClassPool paramClassPool)
/*  52:    */     throws BadBytecode;
/*  53:    */   
/*  54:    */   public abstract void evalExpectedType(ClassPool paramClassPool)
/*  55:    */     throws BadBytecode;
/*  56:    */   
/*  57:    */   public abstract String getExpected()
/*  58:    */     throws BadBytecode;
/*  59:    */   
/*  60:    */   protected static class BasicType
/*  61:    */     extends TypeData
/*  62:    */   {
/*  63:    */     private String name;
/*  64:    */     private int typeTag;
/*  65:    */     
/*  66:    */     public BasicType(String type, int tag)
/*  67:    */     {
/*  68: 81 */       this.name = type;
/*  69: 82 */       this.typeTag = tag;
/*  70:    */     }
/*  71:    */     
/*  72:    */     public void merge(TypeData neighbor) {}
/*  73:    */     
/*  74:    */     public boolean equals(Object obj)
/*  75:    */     {
/*  76: 88 */       return this == obj;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public int getTypeTag()
/*  80:    */     {
/*  81: 91 */       return this.typeTag;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public int getTypeData(ConstPool cp)
/*  85:    */     {
/*  86: 92 */       return 0;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public boolean isObjectType()
/*  90:    */     {
/*  91: 94 */       return false;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public boolean is2WordType()
/*  95:    */     {
/*  96: 97 */       return (this.typeTag == 4) || (this.typeTag == 3);
/*  97:    */     }
/*  98:    */     
/*  99:    */     public TypeData copy()
/* 100:    */     {
/* 101:102 */       return this;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public void evalExpectedType(ClassPool cp)
/* 105:    */       throws BadBytecode
/* 106:    */     {}
/* 107:    */     
/* 108:    */     public String getExpected()
/* 109:    */       throws BadBytecode
/* 110:    */     {
/* 111:108 */       return this.name;
/* 112:    */     }
/* 113:    */     
/* 114:    */     public String getName()
/* 115:    */     {
/* 116:112 */       return this.name;
/* 117:    */     }
/* 118:    */     
/* 119:    */     protected void setType(String s, ClassPool cp)
/* 120:    */       throws BadBytecode
/* 121:    */     {
/* 122:116 */       throw new BadBytecode("conflict: " + this.name + " and " + s);
/* 123:    */     }
/* 124:    */     
/* 125:    */     public String toString()
/* 126:    */     {
/* 127:119 */       return this.name;
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected static abstract class TypeName
/* 132:    */     extends TypeData
/* 133:    */   {
/* 134:    */     protected ArrayList equivalences;
/* 135:    */     protected String expectedName;
/* 136:    */     private CtClass cache;
/* 137:    */     private boolean evalDone;
/* 138:    */     
/* 139:    */     protected TypeName()
/* 140:    */     {
/* 141:130 */       this.equivalences = new ArrayList();
/* 142:131 */       this.equivalences.add(this);
/* 143:132 */       this.expectedName = null;
/* 144:133 */       this.cache = null;
/* 145:134 */       this.evalDone = false;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public void merge(TypeData neighbor)
/* 149:    */     {
/* 150:138 */       if (this == neighbor) {
/* 151:139 */         return;
/* 152:    */       }
/* 153:141 */       if (!(neighbor instanceof TypeName)) {
/* 154:142 */         return;
/* 155:    */       }
/* 156:144 */       TypeName neighbor2 = (TypeName)neighbor;
/* 157:145 */       ArrayList list = this.equivalences;
/* 158:146 */       ArrayList list2 = neighbor2.equivalences;
/* 159:147 */       if (list == list2) {
/* 160:148 */         return;
/* 161:    */       }
/* 162:150 */       int n = list2.size();
/* 163:151 */       for (int i = 0; i < n; i++)
/* 164:    */       {
/* 165:152 */         TypeName tn = (TypeName)list2.get(i);
/* 166:153 */         add(list, tn);
/* 167:154 */         tn.equivalences = list;
/* 168:    */       }
/* 169:    */     }
/* 170:    */     
/* 171:    */     private static void add(ArrayList list, TypeData td)
/* 172:    */     {
/* 173:159 */       int n = list.size();
/* 174:160 */       for (int i = 0; i < n; i++) {
/* 175:161 */         if (list.get(i) == td) {
/* 176:162 */           return;
/* 177:    */         }
/* 178:    */       }
/* 179:164 */       list.add(td);
/* 180:    */     }
/* 181:    */     
/* 182:    */     public int getTypeTag()
/* 183:    */     {
/* 184:169 */       return 7;
/* 185:    */     }
/* 186:    */     
/* 187:    */     public int getTypeData(ConstPool cp)
/* 188:    */     {
/* 189:    */       String type;
/* 190:    */       try
/* 191:    */       {
/* 192:174 */         type = getExpected();
/* 193:    */       }
/* 194:    */       catch (BadBytecode e)
/* 195:    */       {
/* 196:176 */         throw new RuntimeException("fatal error: ", e);
/* 197:    */       }
/* 198:179 */       return getTypeData2(cp, type);
/* 199:    */     }
/* 200:    */     
/* 201:    */     protected int getTypeData2(ConstPool cp, String type)
/* 202:    */     {
/* 203:185 */       return cp.addClassInfo(type);
/* 204:    */     }
/* 205:    */     
/* 206:    */     public boolean equals(Object obj)
/* 207:    */     {
/* 208:189 */       if ((obj instanceof TypeName)) {
/* 209:    */         try
/* 210:    */         {
/* 211:191 */           TypeName tn = (TypeName)obj;
/* 212:192 */           return getExpected().equals(tn.getExpected());
/* 213:    */         }
/* 214:    */         catch (BadBytecode e) {}
/* 215:    */       }
/* 216:197 */       return false;
/* 217:    */     }
/* 218:    */     
/* 219:    */     public boolean isObjectType()
/* 220:    */     {
/* 221:200 */       return true;
/* 222:    */     }
/* 223:    */     
/* 224:    */     protected void setType(String typeName, ClassPool cp)
/* 225:    */       throws BadBytecode
/* 226:    */     {
/* 227:203 */       if (update(cp, this.expectedName, typeName)) {
/* 228:204 */         this.expectedName = typeName;
/* 229:    */       }
/* 230:    */     }
/* 231:    */     
/* 232:    */     public void evalExpectedType(ClassPool cp)
/* 233:    */       throws BadBytecode
/* 234:    */     {
/* 235:208 */       if (this.evalDone) {
/* 236:209 */         return;
/* 237:    */       }
/* 238:211 */       ArrayList equiv = this.equivalences;
/* 239:212 */       int n = equiv.size();
/* 240:213 */       String name = evalExpectedType2(equiv, n);
/* 241:214 */       if (name == null)
/* 242:    */       {
/* 243:215 */         name = this.expectedName;
/* 244:216 */         for (int i = 0; i < n; i++)
/* 245:    */         {
/* 246:217 */           TypeData td = (TypeData)equiv.get(i);
/* 247:218 */           if ((td instanceof TypeName))
/* 248:    */           {
/* 249:219 */             TypeName tn = (TypeName)td;
/* 250:220 */             if (update(cp, name, tn.expectedName)) {
/* 251:221 */               name = tn.expectedName;
/* 252:    */             }
/* 253:    */           }
/* 254:    */         }
/* 255:    */       }
/* 256:226 */       for (int i = 0; i < n; i++)
/* 257:    */       {
/* 258:227 */         TypeData td = (TypeData)equiv.get(i);
/* 259:228 */         if ((td instanceof TypeName))
/* 260:    */         {
/* 261:229 */           TypeName tn = (TypeName)td;
/* 262:230 */           tn.expectedName = name;
/* 263:231 */           tn.cache = null;
/* 264:232 */           tn.evalDone = true;
/* 265:    */         }
/* 266:    */       }
/* 267:    */     }
/* 268:    */     
/* 269:    */     private String evalExpectedType2(ArrayList equiv, int n)
/* 270:    */       throws BadBytecode
/* 271:    */     {
/* 272:238 */       String origName = null;
/* 273:239 */       for (int i = 0; i < n; i++)
/* 274:    */       {
/* 275:240 */         TypeData td = (TypeData)equiv.get(i);
/* 276:241 */         if (!td.isNullType()) {
/* 277:242 */           if (origName == null) {
/* 278:243 */             origName = td.getName();
/* 279:244 */           } else if (!origName.equals(td.getName())) {
/* 280:245 */             return null;
/* 281:    */           }
/* 282:    */         }
/* 283:    */       }
/* 284:248 */       return origName;
/* 285:    */     }
/* 286:    */     
/* 287:    */     protected boolean isTypeName()
/* 288:    */     {
/* 289:251 */       return true;
/* 290:    */     }
/* 291:    */     
/* 292:    */     private boolean update(ClassPool cp, String oldName, String typeName)
/* 293:    */       throws BadBytecode
/* 294:    */     {
/* 295:254 */       if (typeName == null) {
/* 296:255 */         return false;
/* 297:    */       }
/* 298:256 */       if (oldName == null) {
/* 299:257 */         return true;
/* 300:    */       }
/* 301:258 */       if (oldName.equals(typeName)) {
/* 302:259 */         return false;
/* 303:    */       }
/* 304:260 */       if ((typeName.charAt(0) == '[') && (oldName.equals("[Ljava.lang.Object;"))) {
/* 305:266 */         return true;
/* 306:    */       }
/* 307:    */       try
/* 308:    */       {
/* 309:270 */         if (this.cache == null) {
/* 310:271 */           this.cache = cp.get(oldName);
/* 311:    */         }
/* 312:273 */         CtClass cache2 = cp.get(typeName);
/* 313:274 */         if (cache2.subtypeOf(this.cache))
/* 314:    */         {
/* 315:275 */           this.cache = cache2;
/* 316:276 */           return true;
/* 317:    */         }
/* 318:279 */         return false;
/* 319:    */       }
/* 320:    */       catch (NotFoundException e)
/* 321:    */       {
/* 322:282 */         throw new BadBytecode("cannot find " + e.getMessage());
/* 323:    */       }
/* 324:    */     }
/* 325:    */     
/* 326:    */     public String getExpected()
/* 327:    */       throws BadBytecode
/* 328:    */     {
/* 329:289 */       ArrayList equiv = this.equivalences;
/* 330:290 */       if (equiv.size() == 1) {
/* 331:291 */         return getName();
/* 332:    */       }
/* 333:293 */       String en = this.expectedName;
/* 334:294 */       if (en == null) {
/* 335:295 */         return "java.lang.Object";
/* 336:    */       }
/* 337:297 */       return en;
/* 338:    */     }
/* 339:    */     
/* 340:    */     public String toString()
/* 341:    */     {
/* 342:    */       try
/* 343:    */       {
/* 344:303 */         String en = this.expectedName;
/* 345:304 */         if (en != null) {
/* 346:305 */           return en;
/* 347:    */         }
/* 348:307 */         String name = getName();
/* 349:308 */         if (this.equivalences.size() == 1) {
/* 350:309 */           return name;
/* 351:    */         }
/* 352:311 */         return name + "?";
/* 353:    */       }
/* 354:    */       catch (BadBytecode e)
/* 355:    */       {
/* 356:314 */         return "<" + e.getMessage() + ">";
/* 357:    */       }
/* 358:    */     }
/* 359:    */   }
/* 360:    */   
/* 361:    */   public static class ClassName
/* 362:    */     extends TypeData.TypeName
/* 363:    */   {
/* 364:    */     private String name;
/* 365:    */     
/* 366:    */     public ClassName(String n)
/* 367:    */     {
/* 368:326 */       this.name = n;
/* 369:    */     }
/* 370:    */     
/* 371:    */     public TypeData copy()
/* 372:    */     {
/* 373:330 */       return new ClassName(this.name);
/* 374:    */     }
/* 375:    */     
/* 376:    */     public String getName()
/* 377:    */     {
/* 378:334 */       return this.name;
/* 379:    */     }
/* 380:    */   }
/* 381:    */   
/* 382:    */   public static class NullType
/* 383:    */     extends TypeData.ClassName
/* 384:    */   {
/* 385:    */     public NullType()
/* 386:    */     {
/* 387:345 */       super();
/* 388:    */     }
/* 389:    */     
/* 390:    */     public TypeData copy()
/* 391:    */     {
/* 392:349 */       return new NullType();
/* 393:    */     }
/* 394:    */     
/* 395:    */     public boolean isNullType()
/* 396:    */     {
/* 397:352 */       return true;
/* 398:    */     }
/* 399:    */     
/* 400:    */     public int getTypeTag()
/* 401:    */     {
/* 402:    */       try
/* 403:    */       {
/* 404:356 */         if ("null".equals(getExpected())) {
/* 405:357 */           return 5;
/* 406:    */         }
/* 407:359 */         return super.getTypeTag();
/* 408:    */       }
/* 409:    */       catch (BadBytecode e)
/* 410:    */       {
/* 411:362 */         throw new RuntimeException("fatal error: ", e);
/* 412:    */       }
/* 413:    */     }
/* 414:    */     
/* 415:    */     protected int getTypeData2(ConstPool cp, String type)
/* 416:    */     {
/* 417:367 */       if ("null".equals(type)) {
/* 418:368 */         return 0;
/* 419:    */       }
/* 420:370 */       return super.getTypeData2(cp, type);
/* 421:    */     }
/* 422:    */     
/* 423:    */     public String getExpected()
/* 424:    */       throws BadBytecode
/* 425:    */     {
/* 426:374 */       String en = this.expectedName;
/* 427:375 */       if (en == null) {
/* 428:380 */         return "java.lang.Object";
/* 429:    */       }
/* 430:383 */       return en;
/* 431:    */     }
/* 432:    */   }
/* 433:    */   
/* 434:    */   public static class ArrayElement
/* 435:    */     extends TypeData.TypeName
/* 436:    */   {
/* 437:    */     TypeData array;
/* 438:    */     
/* 439:    */     public ArrayElement(TypeData a)
/* 440:    */     {
/* 441:395 */       this.array = a;
/* 442:    */     }
/* 443:    */     
/* 444:    */     public TypeData copy()
/* 445:    */     {
/* 446:399 */       return new ArrayElement(this.array);
/* 447:    */     }
/* 448:    */     
/* 449:    */     protected void setType(String typeName, ClassPool cp)
/* 450:    */       throws BadBytecode
/* 451:    */     {
/* 452:403 */       super.setType(typeName, cp);
/* 453:404 */       this.array.setType(getArrayType(typeName), cp);
/* 454:    */     }
/* 455:    */     
/* 456:    */     public String getName()
/* 457:    */       throws BadBytecode
/* 458:    */     {
/* 459:408 */       String name = this.array.getName();
/* 460:409 */       if ((name.length() > 1) && (name.charAt(0) == '['))
/* 461:    */       {
/* 462:410 */         char c = name.charAt(1);
/* 463:411 */         if (c == 'L') {
/* 464:412 */           return name.substring(2, name.length() - 1).replace('/', '.');
/* 465:    */         }
/* 466:413 */         if (c == '[') {
/* 467:414 */           return name.substring(1);
/* 468:    */         }
/* 469:    */       }
/* 470:417 */       throw new BadBytecode("bad array type for AALOAD: " + name);
/* 471:    */     }
/* 472:    */     
/* 473:    */     public static String getArrayType(String elementType)
/* 474:    */     {
/* 475:422 */       if (elementType.charAt(0) == '[') {
/* 476:423 */         return "[" + elementType;
/* 477:    */       }
/* 478:425 */       return "[L" + elementType.replace('.', '/') + ";";
/* 479:    */     }
/* 480:    */     
/* 481:    */     public static String getElementType(String arrayType)
/* 482:    */     {
/* 483:429 */       char c = arrayType.charAt(1);
/* 484:430 */       if (c == 'L') {
/* 485:431 */         return arrayType.substring(2, arrayType.length() - 1).replace('/', '.');
/* 486:    */       }
/* 487:432 */       if (c == '[') {
/* 488:433 */         return arrayType.substring(1);
/* 489:    */       }
/* 490:435 */       return arrayType;
/* 491:    */     }
/* 492:    */   }
/* 493:    */   
/* 494:    */   public static class UninitData
/* 495:    */     extends TypeData
/* 496:    */   {
/* 497:    */     String className;
/* 498:    */     int offset;
/* 499:    */     boolean initialized;
/* 500:    */     
/* 501:    */     UninitData(int offset, String className)
/* 502:    */     {
/* 503:448 */       this.className = className;
/* 504:449 */       this.offset = offset;
/* 505:450 */       this.initialized = false;
/* 506:    */     }
/* 507:    */     
/* 508:    */     public void merge(TypeData neighbor) {}
/* 509:    */     
/* 510:    */     public int getTypeTag()
/* 511:    */     {
/* 512:455 */       return 8;
/* 513:    */     }
/* 514:    */     
/* 515:    */     public int getTypeData(ConstPool cp)
/* 516:    */     {
/* 517:456 */       return this.offset;
/* 518:    */     }
/* 519:    */     
/* 520:    */     public boolean equals(Object obj)
/* 521:    */     {
/* 522:459 */       if ((obj instanceof UninitData))
/* 523:    */       {
/* 524:460 */         UninitData ud = (UninitData)obj;
/* 525:461 */         return (this.offset == ud.offset) && (this.className.equals(ud.className));
/* 526:    */       }
/* 527:464 */       return false;
/* 528:    */     }
/* 529:    */     
/* 530:    */     public TypeData getSelf()
/* 531:    */     {
/* 532:468 */       if (this.initialized) {
/* 533:469 */         return copy();
/* 534:    */       }
/* 535:471 */       return this;
/* 536:    */     }
/* 537:    */     
/* 538:    */     public TypeData copy()
/* 539:    */     {
/* 540:475 */       return new TypeData.ClassName(this.className);
/* 541:    */     }
/* 542:    */     
/* 543:    */     public boolean isObjectType()
/* 544:    */     {
/* 545:478 */       return true;
/* 546:    */     }
/* 547:    */     
/* 548:    */     protected void setType(String typeName, ClassPool cp)
/* 549:    */       throws BadBytecode
/* 550:    */     {
/* 551:481 */       this.initialized = true;
/* 552:    */     }
/* 553:    */     
/* 554:    */     public void evalExpectedType(ClassPool cp)
/* 555:    */       throws BadBytecode
/* 556:    */     {}
/* 557:    */     
/* 558:    */     public String getName()
/* 559:    */     {
/* 560:487 */       return this.className;
/* 561:    */     }
/* 562:    */     
/* 563:    */     public String getExpected()
/* 564:    */     {
/* 565:490 */       return this.className;
/* 566:    */     }
/* 567:    */     
/* 568:    */     public String toString()
/* 569:    */     {
/* 570:492 */       return "uninit:" + this.className + "@" + this.offset;
/* 571:    */     }
/* 572:    */   }
/* 573:    */   
/* 574:    */   public static class UninitThis
/* 575:    */     extends TypeData.UninitData
/* 576:    */   {
/* 577:    */     UninitThis(String className)
/* 578:    */     {
/* 579:497 */       super(className);
/* 580:    */     }
/* 581:    */     
/* 582:    */     public int getTypeTag()
/* 583:    */     {
/* 584:500 */       return 6;
/* 585:    */     }
/* 586:    */     
/* 587:    */     public int getTypeData(ConstPool cp)
/* 588:    */     {
/* 589:501 */       return 0;
/* 590:    */     }
/* 591:    */     
/* 592:    */     public boolean equals(Object obj)
/* 593:    */     {
/* 594:504 */       return obj instanceof UninitThis;
/* 595:    */     }
/* 596:    */     
/* 597:    */     public String toString()
/* 598:    */     {
/* 599:507 */       return "uninit:this";
/* 600:    */     }
/* 601:    */   }
/* 602:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.stackmap.TypeData
 * JD-Core Version:    0.7.0.1
 */