/*   1:    */ package javassist.bytecode.stackmap;
/*   2:    */ 
/*   3:    */ import javassist.ClassPool;
/*   4:    */ import javassist.bytecode.BadBytecode;
/*   5:    */ import javassist.bytecode.ByteArray;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ import javassist.bytecode.Descriptor;
/*   8:    */ 
/*   9:    */ public abstract class Tracer
/*  10:    */   implements TypeTag
/*  11:    */ {
/*  12:    */   protected ClassPool classPool;
/*  13:    */   protected ConstPool cpool;
/*  14:    */   protected String returnType;
/*  15:    */   protected int stackTop;
/*  16:    */   protected TypeData[] stackTypes;
/*  17:    */   protected TypeData[] localsTypes;
/*  18:    */   
/*  19:    */   public Tracer(ClassPool classes, ConstPool cp, int maxStack, int maxLocals, String retType)
/*  20:    */   {
/*  21: 41 */     this.classPool = classes;
/*  22: 42 */     this.cpool = cp;
/*  23: 43 */     this.returnType = retType;
/*  24: 44 */     this.stackTop = 0;
/*  25: 45 */     this.stackTypes = new TypeData[maxStack];
/*  26: 46 */     this.localsTypes = new TypeData[maxLocals];
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Tracer(Tracer t, boolean copyStack)
/*  30:    */   {
/*  31: 50 */     this.classPool = t.classPool;
/*  32: 51 */     this.cpool = t.cpool;
/*  33: 52 */     this.returnType = t.returnType;
/*  34:    */     
/*  35: 54 */     this.stackTop = t.stackTop;
/*  36: 55 */     int size = t.stackTypes.length;
/*  37: 56 */     this.stackTypes = new TypeData[size];
/*  38: 57 */     if (copyStack) {
/*  39: 58 */       copyFrom(t.stackTop, t.stackTypes, this.stackTypes);
/*  40:    */     }
/*  41: 60 */     int size2 = t.localsTypes.length;
/*  42: 61 */     this.localsTypes = new TypeData[size2];
/*  43: 62 */     copyFrom(size2, t.localsTypes, this.localsTypes);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected static int copyFrom(int n, TypeData[] srcTypes, TypeData[] destTypes)
/*  47:    */   {
/*  48: 66 */     int k = -1;
/*  49: 67 */     for (int i = 0; i < n; i++)
/*  50:    */     {
/*  51: 68 */       TypeData t = srcTypes[i];
/*  52: 69 */       destTypes[i] = (t == TOP ? TOP : t.getSelf());
/*  53: 70 */       if (t != TOP) {
/*  54: 71 */         if (t.is2WordType()) {
/*  55: 72 */           k = i + 1;
/*  56:    */         } else {
/*  57: 74 */           k = i;
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61: 77 */     return k + 1;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected int doOpcode(int pos, byte[] code)
/*  65:    */     throws BadBytecode
/*  66:    */   {
/*  67: 91 */     int op = code[pos] & 0xFF;
/*  68: 92 */     if (op < 96)
/*  69:    */     {
/*  70: 93 */       if (op < 54) {
/*  71: 94 */         return doOpcode0_53(pos, code, op);
/*  72:    */       }
/*  73: 96 */       return doOpcode54_95(pos, code, op);
/*  74:    */     }
/*  75: 98 */     if (op < 148) {
/*  76: 99 */       return doOpcode96_147(pos, code, op);
/*  77:    */     }
/*  78:101 */     return doOpcode148_201(pos, code, op);
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected void visitBranch(int pos, byte[] code, int offset)
/*  82:    */     throws BadBytecode
/*  83:    */   {}
/*  84:    */   
/*  85:    */   protected void visitGoto(int pos, byte[] code, int offset)
/*  86:    */     throws BadBytecode
/*  87:    */   {}
/*  88:    */   
/*  89:    */   protected void visitReturn(int pos, byte[] code)
/*  90:    */     throws BadBytecode
/*  91:    */   {}
/*  92:    */   
/*  93:    */   protected void visitThrow(int pos, byte[] code)
/*  94:    */     throws BadBytecode
/*  95:    */   {}
/*  96:    */   
/*  97:    */   protected void visitTableSwitch(int pos, byte[] code, int n, int offsetPos, int defaultOffset)
/*  98:    */     throws BadBytecode
/*  99:    */   {}
/* 100:    */   
/* 101:    */   protected void visitLookupSwitch(int pos, byte[] code, int n, int pairsPos, int defaultOffset)
/* 102:    */     throws BadBytecode
/* 103:    */   {}
/* 104:    */   
/* 105:    */   protected void visitJSR(int pos, byte[] code)
/* 106:    */     throws BadBytecode
/* 107:    */   {
/* 108:133 */     throwBadBytecode(pos, "jsr");
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void visitRET(int pos, byte[] code)
/* 112:    */     throws BadBytecode
/* 113:    */   {
/* 114:140 */     throwBadBytecode(pos, "ret");
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void throwBadBytecode(int pos, String name)
/* 118:    */     throws BadBytecode
/* 119:    */   {
/* 120:144 */     throw new BadBytecode(name + " at " + pos);
/* 121:    */   }
/* 122:    */   
/* 123:    */   private int doOpcode0_53(int pos, byte[] code, int op)
/* 124:    */     throws BadBytecode
/* 125:    */   {
/* 126:149 */     TypeData[] stackTypes = this.stackTypes;
/* 127:150 */     switch (op)
/* 128:    */     {
/* 129:    */     case 0: 
/* 130:    */       break;
/* 131:    */     case 1: 
/* 132:154 */       stackTypes[(this.stackTop++)] = new TypeData.NullType();
/* 133:155 */       break;
/* 134:    */     case 2: 
/* 135:    */     case 3: 
/* 136:    */     case 4: 
/* 137:    */     case 5: 
/* 138:    */     case 6: 
/* 139:    */     case 7: 
/* 140:    */     case 8: 
/* 141:163 */       stackTypes[(this.stackTop++)] = INTEGER;
/* 142:164 */       break;
/* 143:    */     case 9: 
/* 144:    */     case 10: 
/* 145:167 */       stackTypes[(this.stackTop++)] = LONG;
/* 146:168 */       stackTypes[(this.stackTop++)] = TOP;
/* 147:169 */       break;
/* 148:    */     case 11: 
/* 149:    */     case 12: 
/* 150:    */     case 13: 
/* 151:173 */       stackTypes[(this.stackTop++)] = FLOAT;
/* 152:174 */       break;
/* 153:    */     case 14: 
/* 154:    */     case 15: 
/* 155:177 */       stackTypes[(this.stackTop++)] = DOUBLE;
/* 156:178 */       stackTypes[(this.stackTop++)] = TOP;
/* 157:179 */       break;
/* 158:    */     case 16: 
/* 159:    */     case 17: 
/* 160:182 */       stackTypes[(this.stackTop++)] = INTEGER;
/* 161:183 */       return op == 17 ? 3 : 2;
/* 162:    */     case 18: 
/* 163:185 */       doLDC(code[(pos + 1)] & 0xFF);
/* 164:186 */       return 2;
/* 165:    */     case 19: 
/* 166:    */     case 20: 
/* 167:189 */       doLDC(ByteArray.readU16bit(code, pos + 1));
/* 168:190 */       return 3;
/* 169:    */     case 21: 
/* 170:192 */       return doXLOAD(INTEGER, code, pos);
/* 171:    */     case 22: 
/* 172:194 */       return doXLOAD(LONG, code, pos);
/* 173:    */     case 23: 
/* 174:196 */       return doXLOAD(FLOAT, code, pos);
/* 175:    */     case 24: 
/* 176:198 */       return doXLOAD(DOUBLE, code, pos);
/* 177:    */     case 25: 
/* 178:200 */       return doALOAD(code[(pos + 1)] & 0xFF);
/* 179:    */     case 26: 
/* 180:    */     case 27: 
/* 181:    */     case 28: 
/* 182:    */     case 29: 
/* 183:205 */       stackTypes[(this.stackTop++)] = INTEGER;
/* 184:206 */       break;
/* 185:    */     case 30: 
/* 186:    */     case 31: 
/* 187:    */     case 32: 
/* 188:    */     case 33: 
/* 189:211 */       stackTypes[(this.stackTop++)] = LONG;
/* 190:212 */       stackTypes[(this.stackTop++)] = TOP;
/* 191:213 */       break;
/* 192:    */     case 34: 
/* 193:    */     case 35: 
/* 194:    */     case 36: 
/* 195:    */     case 37: 
/* 196:218 */       stackTypes[(this.stackTop++)] = FLOAT;
/* 197:219 */       break;
/* 198:    */     case 38: 
/* 199:    */     case 39: 
/* 200:    */     case 40: 
/* 201:    */     case 41: 
/* 202:224 */       stackTypes[(this.stackTop++)] = DOUBLE;
/* 203:225 */       stackTypes[(this.stackTop++)] = TOP;
/* 204:226 */       break;
/* 205:    */     case 42: 
/* 206:    */     case 43: 
/* 207:    */     case 44: 
/* 208:    */     case 45: 
/* 209:231 */       int reg = op - 42;
/* 210:232 */       stackTypes[(this.stackTop++)] = this.localsTypes[reg];
/* 211:233 */       break;
/* 212:    */     case 46: 
/* 213:235 */       stackTypes[(--this.stackTop - 1)] = INTEGER;
/* 214:236 */       break;
/* 215:    */     case 47: 
/* 216:238 */       stackTypes[(this.stackTop - 2)] = LONG;
/* 217:239 */       stackTypes[(this.stackTop - 1)] = TOP;
/* 218:240 */       break;
/* 219:    */     case 48: 
/* 220:242 */       stackTypes[(--this.stackTop - 1)] = FLOAT;
/* 221:243 */       break;
/* 222:    */     case 49: 
/* 223:245 */       stackTypes[(this.stackTop - 2)] = DOUBLE;
/* 224:246 */       stackTypes[(this.stackTop - 1)] = TOP;
/* 225:247 */       break;
/* 226:    */     case 50: 
/* 227:249 */       int s = --this.stackTop - 1;
/* 228:250 */       TypeData data = stackTypes[s];
/* 229:251 */       if ((data == null) || (!data.isObjectType())) {
/* 230:252 */         throw new BadBytecode("bad AALOAD");
/* 231:    */       }
/* 232:254 */       stackTypes[s] = new TypeData.ArrayElement(data);
/* 233:    */       
/* 234:256 */       break;
/* 235:    */     case 51: 
/* 236:    */     case 52: 
/* 237:    */     case 53: 
/* 238:260 */       stackTypes[(--this.stackTop - 1)] = INTEGER;
/* 239:261 */       break;
/* 240:    */     default: 
/* 241:263 */       throw new RuntimeException("fatal");
/* 242:    */     }
/* 243:266 */     return 1;
/* 244:    */   }
/* 245:    */   
/* 246:    */   private void doLDC(int index)
/* 247:    */   {
/* 248:270 */     TypeData[] stackTypes = this.stackTypes;
/* 249:271 */     int tag = this.cpool.getTag(index);
/* 250:272 */     if (tag == 8)
/* 251:    */     {
/* 252:273 */       stackTypes[(this.stackTop++)] = new TypeData.ClassName("java.lang.String");
/* 253:    */     }
/* 254:274 */     else if (tag == 3)
/* 255:    */     {
/* 256:275 */       stackTypes[(this.stackTop++)] = INTEGER;
/* 257:    */     }
/* 258:276 */     else if (tag == 4)
/* 259:    */     {
/* 260:277 */       stackTypes[(this.stackTop++)] = FLOAT;
/* 261:    */     }
/* 262:278 */     else if (tag == 5)
/* 263:    */     {
/* 264:279 */       stackTypes[(this.stackTop++)] = LONG;
/* 265:280 */       stackTypes[(this.stackTop++)] = TOP;
/* 266:    */     }
/* 267:282 */     else if (tag == 6)
/* 268:    */     {
/* 269:283 */       stackTypes[(this.stackTop++)] = DOUBLE;
/* 270:284 */       stackTypes[(this.stackTop++)] = TOP;
/* 271:    */     }
/* 272:286 */     else if (tag == 7)
/* 273:    */     {
/* 274:287 */       stackTypes[(this.stackTop++)] = new TypeData.ClassName("java.lang.Class");
/* 275:    */     }
/* 276:    */     else
/* 277:    */     {
/* 278:289 */       throw new RuntimeException("bad LDC: " + tag);
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   private int doXLOAD(TypeData type, byte[] code, int pos)
/* 283:    */   {
/* 284:293 */     int localVar = code[(pos + 1)] & 0xFF;
/* 285:294 */     return doXLOAD(localVar, type);
/* 286:    */   }
/* 287:    */   
/* 288:    */   private int doXLOAD(int localVar, TypeData type)
/* 289:    */   {
/* 290:298 */     this.stackTypes[(this.stackTop++)] = type;
/* 291:299 */     if (type.is2WordType()) {
/* 292:300 */       this.stackTypes[(this.stackTop++)] = TOP;
/* 293:    */     }
/* 294:302 */     return 2;
/* 295:    */   }
/* 296:    */   
/* 297:    */   private int doALOAD(int localVar)
/* 298:    */   {
/* 299:306 */     this.stackTypes[(this.stackTop++)] = this.localsTypes[localVar];
/* 300:307 */     return 2;
/* 301:    */   }
/* 302:    */   
/* 303:    */   private int doOpcode54_95(int pos, byte[] code, int op)
/* 304:    */     throws BadBytecode
/* 305:    */   {
/* 306:311 */     TypeData[] localsTypes = this.localsTypes;
/* 307:312 */     TypeData[] stackTypes = this.stackTypes;
/* 308:313 */     switch (op)
/* 309:    */     {
/* 310:    */     case 54: 
/* 311:315 */       return doXSTORE(pos, code, INTEGER);
/* 312:    */     case 55: 
/* 313:317 */       return doXSTORE(pos, code, LONG);
/* 314:    */     case 56: 
/* 315:319 */       return doXSTORE(pos, code, FLOAT);
/* 316:    */     case 57: 
/* 317:321 */       return doXSTORE(pos, code, DOUBLE);
/* 318:    */     case 58: 
/* 319:323 */       return doASTORE(code[(pos + 1)] & 0xFF);
/* 320:    */     case 59: 
/* 321:    */     case 60: 
/* 322:    */     case 61: 
/* 323:    */     case 62: 
/* 324:328 */       int var = op - 59;
/* 325:329 */       localsTypes[var] = INTEGER;
/* 326:330 */       this.stackTop -= 1;
/* 327:331 */       break;
/* 328:    */     case 63: 
/* 329:    */     case 64: 
/* 330:    */     case 65: 
/* 331:    */     case 66: 
/* 332:336 */       int var = op - 63;
/* 333:337 */       localsTypes[var] = LONG;
/* 334:338 */       localsTypes[(var + 1)] = TOP;
/* 335:339 */       this.stackTop -= 2;
/* 336:340 */       break;
/* 337:    */     case 67: 
/* 338:    */     case 68: 
/* 339:    */     case 69: 
/* 340:    */     case 70: 
/* 341:345 */       int var = op - 67;
/* 342:346 */       localsTypes[var] = FLOAT;
/* 343:347 */       this.stackTop -= 1;
/* 344:348 */       break;
/* 345:    */     case 71: 
/* 346:    */     case 72: 
/* 347:    */     case 73: 
/* 348:    */     case 74: 
/* 349:353 */       int var = op - 71;
/* 350:354 */       localsTypes[var] = DOUBLE;
/* 351:355 */       localsTypes[(var + 1)] = TOP;
/* 352:356 */       this.stackTop -= 2;
/* 353:357 */       break;
/* 354:    */     case 75: 
/* 355:    */     case 76: 
/* 356:    */     case 77: 
/* 357:    */     case 78: 
/* 358:362 */       int var = op - 75;
/* 359:363 */       doASTORE(var);
/* 360:364 */       break;
/* 361:    */     case 79: 
/* 362:    */     case 80: 
/* 363:    */     case 81: 
/* 364:    */     case 82: 
/* 365:369 */       this.stackTop -= ((op == 80) || (op == 82) ? 4 : 3);
/* 366:370 */       break;
/* 367:    */     case 83: 
/* 368:372 */       TypeData.setType(stackTypes[(this.stackTop - 1)], TypeData.ArrayElement.getElementType(stackTypes[(this.stackTop - 3)].getName()), this.classPool);
/* 369:    */       
/* 370:    */ 
/* 371:375 */       this.stackTop -= 3;
/* 372:376 */       break;
/* 373:    */     case 84: 
/* 374:    */     case 85: 
/* 375:    */     case 86: 
/* 376:380 */       this.stackTop -= 3;
/* 377:381 */       break;
/* 378:    */     case 87: 
/* 379:383 */       this.stackTop -= 1;
/* 380:384 */       break;
/* 381:    */     case 88: 
/* 382:386 */       this.stackTop -= 2;
/* 383:387 */       break;
/* 384:    */     case 89: 
/* 385:389 */       int sp = this.stackTop;
/* 386:390 */       stackTypes[sp] = stackTypes[(sp - 1)];
/* 387:391 */       this.stackTop = (sp + 1);
/* 388:392 */       break;
/* 389:    */     case 90: 
/* 390:    */     case 91: 
/* 391:395 */       int len = op - 90 + 2;
/* 392:396 */       doDUP_XX(1, len);
/* 393:397 */       int sp = this.stackTop;
/* 394:398 */       stackTypes[(sp - len)] = stackTypes[sp];
/* 395:399 */       this.stackTop = (sp + 1);
/* 396:400 */       break;
/* 397:    */     case 92: 
/* 398:402 */       doDUP_XX(2, 2);
/* 399:403 */       this.stackTop += 2;
/* 400:404 */       break;
/* 401:    */     case 93: 
/* 402:    */     case 94: 
/* 403:407 */       int len = op - 93 + 3;
/* 404:408 */       doDUP_XX(2, len);
/* 405:409 */       int sp = this.stackTop;
/* 406:410 */       stackTypes[(sp - len)] = stackTypes[sp];
/* 407:411 */       stackTypes[(sp - len + 1)] = stackTypes[(sp + 1)];
/* 408:412 */       this.stackTop = (sp + 2);
/* 409:413 */       break;
/* 410:    */     case 95: 
/* 411:415 */       int sp = this.stackTop - 1;
/* 412:416 */       TypeData t = stackTypes[sp];
/* 413:417 */       stackTypes[sp] = stackTypes[(sp - 1)];
/* 414:418 */       stackTypes[(sp - 1)] = t;
/* 415:419 */       break;
/* 416:    */     default: 
/* 417:421 */       throw new RuntimeException("fatal");
/* 418:    */     }
/* 419:424 */     return 1;
/* 420:    */   }
/* 421:    */   
/* 422:    */   private int doXSTORE(int pos, byte[] code, TypeData type)
/* 423:    */   {
/* 424:428 */     int index = code[(pos + 1)] & 0xFF;
/* 425:429 */     return doXSTORE(index, type);
/* 426:    */   }
/* 427:    */   
/* 428:    */   private int doXSTORE(int index, TypeData type)
/* 429:    */   {
/* 430:433 */     this.stackTop -= 1;
/* 431:434 */     this.localsTypes[index] = type;
/* 432:435 */     if (type.is2WordType())
/* 433:    */     {
/* 434:436 */       this.stackTop -= 1;
/* 435:437 */       this.localsTypes[(index + 1)] = TOP;
/* 436:    */     }
/* 437:440 */     return 2;
/* 438:    */   }
/* 439:    */   
/* 440:    */   private int doASTORE(int index)
/* 441:    */   {
/* 442:444 */     this.stackTop -= 1;
/* 443:    */     
/* 444:446 */     this.localsTypes[index] = this.stackTypes[this.stackTop].copy();
/* 445:447 */     return 2;
/* 446:    */   }
/* 447:    */   
/* 448:    */   private void doDUP_XX(int delta, int len)
/* 449:    */   {
/* 450:451 */     TypeData[] types = this.stackTypes;
/* 451:452 */     int sp = this.stackTop - 1;
/* 452:453 */     int end = sp - len;
/* 453:454 */     while (sp > end)
/* 454:    */     {
/* 455:455 */       types[(sp + delta)] = types[sp];
/* 456:456 */       sp--;
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   private int doOpcode96_147(int pos, byte[] code, int op)
/* 461:    */   {
/* 462:461 */     if (op <= 131)
/* 463:    */     {
/* 464:462 */       this.stackTop += javassist.bytecode.Opcode.STACK_GROW[op];
/* 465:463 */       return 1;
/* 466:    */     }
/* 467:466 */     switch (op)
/* 468:    */     {
/* 469:    */     case 132: 
/* 470:469 */       return 3;
/* 471:    */     case 133: 
/* 472:471 */       this.stackTypes[this.stackTop] = LONG;
/* 473:472 */       this.stackTypes[(this.stackTop - 1)] = TOP;
/* 474:473 */       this.stackTop += 1;
/* 475:474 */       break;
/* 476:    */     case 134: 
/* 477:476 */       this.stackTypes[(this.stackTop - 1)] = FLOAT;
/* 478:477 */       break;
/* 479:    */     case 135: 
/* 480:479 */       this.stackTypes[this.stackTop] = DOUBLE;
/* 481:480 */       this.stackTypes[(this.stackTop - 1)] = TOP;
/* 482:481 */       this.stackTop += 1;
/* 483:482 */       break;
/* 484:    */     case 136: 
/* 485:484 */       this.stackTypes[(--this.stackTop - 1)] = INTEGER;
/* 486:485 */       break;
/* 487:    */     case 137: 
/* 488:487 */       this.stackTypes[(--this.stackTop - 1)] = FLOAT;
/* 489:488 */       break;
/* 490:    */     case 138: 
/* 491:490 */       this.stackTypes[(this.stackTop - 1)] = DOUBLE;
/* 492:491 */       break;
/* 493:    */     case 139: 
/* 494:493 */       this.stackTypes[(this.stackTop - 1)] = INTEGER;
/* 495:494 */       break;
/* 496:    */     case 140: 
/* 497:496 */       this.stackTypes[(this.stackTop - 1)] = TOP;
/* 498:497 */       this.stackTypes[(this.stackTop++)] = LONG;
/* 499:498 */       break;
/* 500:    */     case 141: 
/* 501:500 */       this.stackTypes[(this.stackTop - 1)] = TOP;
/* 502:501 */       this.stackTypes[(this.stackTop++)] = DOUBLE;
/* 503:502 */       break;
/* 504:    */     case 142: 
/* 505:504 */       this.stackTypes[(--this.stackTop - 1)] = INTEGER;
/* 506:505 */       break;
/* 507:    */     case 143: 
/* 508:507 */       this.stackTypes[(this.stackTop - 1)] = LONG;
/* 509:508 */       break;
/* 510:    */     case 144: 
/* 511:510 */       this.stackTypes[(--this.stackTop - 1)] = FLOAT;
/* 512:511 */       break;
/* 513:    */     case 145: 
/* 514:    */     case 146: 
/* 515:    */     case 147: 
/* 516:    */       break;
/* 517:    */     default: 
/* 518:517 */       throw new RuntimeException("fatal");
/* 519:    */     }
/* 520:520 */     return 1;
/* 521:    */   }
/* 522:    */   
/* 523:    */   private int doOpcode148_201(int pos, byte[] code, int op)
/* 524:    */     throws BadBytecode
/* 525:    */   {
/* 526:524 */     switch (op)
/* 527:    */     {
/* 528:    */     case 148: 
/* 529:526 */       this.stackTypes[(this.stackTop - 4)] = INTEGER;
/* 530:527 */       this.stackTop -= 3;
/* 531:528 */       break;
/* 532:    */     case 149: 
/* 533:    */     case 150: 
/* 534:531 */       this.stackTypes[(--this.stackTop - 1)] = INTEGER;
/* 535:532 */       break;
/* 536:    */     case 151: 
/* 537:    */     case 152: 
/* 538:535 */       this.stackTypes[(this.stackTop - 4)] = INTEGER;
/* 539:536 */       this.stackTop -= 3;
/* 540:537 */       break;
/* 541:    */     case 153: 
/* 542:    */     case 154: 
/* 543:    */     case 155: 
/* 544:    */     case 156: 
/* 545:    */     case 157: 
/* 546:    */     case 158: 
/* 547:544 */       this.stackTop -= 1;
/* 548:545 */       visitBranch(pos, code, ByteArray.readS16bit(code, pos + 1));
/* 549:546 */       return 3;
/* 550:    */     case 159: 
/* 551:    */     case 160: 
/* 552:    */     case 161: 
/* 553:    */     case 162: 
/* 554:    */     case 163: 
/* 555:    */     case 164: 
/* 556:    */     case 165: 
/* 557:    */     case 166: 
/* 558:555 */       this.stackTop -= 2;
/* 559:556 */       visitBranch(pos, code, ByteArray.readS16bit(code, pos + 1));
/* 560:557 */       return 3;
/* 561:    */     case 167: 
/* 562:559 */       visitGoto(pos, code, ByteArray.readS16bit(code, pos + 1));
/* 563:560 */       return 3;
/* 564:    */     case 168: 
/* 565:562 */       this.stackTypes[(this.stackTop++)] = TOP;
/* 566:563 */       visitJSR(pos, code);
/* 567:564 */       return 3;
/* 568:    */     case 169: 
/* 569:566 */       visitRET(pos, code);
/* 570:567 */       return 2;
/* 571:    */     case 170: 
/* 572:569 */       this.stackTop -= 1;
/* 573:570 */       int pos2 = (pos & 0xFFFFFFFC) + 8;
/* 574:571 */       int low = ByteArray.read32bit(code, pos2);
/* 575:572 */       int high = ByteArray.read32bit(code, pos2 + 4);
/* 576:573 */       int n = high - low + 1;
/* 577:574 */       visitTableSwitch(pos, code, n, pos2 + 8, ByteArray.read32bit(code, pos2 - 4));
/* 578:575 */       return n * 4 + 16 - (pos & 0x3);
/* 579:    */     case 171: 
/* 580:577 */       this.stackTop -= 1;
/* 581:578 */       int pos2 = (pos & 0xFFFFFFFC) + 8;
/* 582:579 */       int n = ByteArray.read32bit(code, pos2);
/* 583:580 */       visitLookupSwitch(pos, code, n, pos2 + 4, ByteArray.read32bit(code, pos2 - 4));
/* 584:581 */       return n * 8 + 12 - (pos & 0x3);
/* 585:    */     case 172: 
/* 586:583 */       this.stackTop -= 1;
/* 587:584 */       visitReturn(pos, code);
/* 588:585 */       break;
/* 589:    */     case 173: 
/* 590:587 */       this.stackTop -= 2;
/* 591:588 */       visitReturn(pos, code);
/* 592:589 */       break;
/* 593:    */     case 174: 
/* 594:591 */       this.stackTop -= 1;
/* 595:592 */       visitReturn(pos, code);
/* 596:593 */       break;
/* 597:    */     case 175: 
/* 598:595 */       this.stackTop -= 2;
/* 599:596 */       visitReturn(pos, code);
/* 600:597 */       break;
/* 601:    */     case 176: 
/* 602:599 */       TypeData.setType(this.stackTypes[(--this.stackTop)], this.returnType, this.classPool);
/* 603:600 */       visitReturn(pos, code);
/* 604:601 */       break;
/* 605:    */     case 177: 
/* 606:603 */       visitReturn(pos, code);
/* 607:604 */       break;
/* 608:    */     case 178: 
/* 609:606 */       return doGetField(pos, code, false);
/* 610:    */     case 179: 
/* 611:608 */       return doPutField(pos, code, false);
/* 612:    */     case 180: 
/* 613:610 */       return doGetField(pos, code, true);
/* 614:    */     case 181: 
/* 615:612 */       return doPutField(pos, code, true);
/* 616:    */     case 182: 
/* 617:    */     case 183: 
/* 618:615 */       return doInvokeMethod(pos, code, true);
/* 619:    */     case 184: 
/* 620:617 */       return doInvokeMethod(pos, code, false);
/* 621:    */     case 185: 
/* 622:619 */       return doInvokeIntfMethod(pos, code);
/* 623:    */     case 186: 
/* 624:621 */       throw new RuntimeException("bad opcode 186");
/* 625:    */     case 187: 
/* 626:623 */       int i = ByteArray.readU16bit(code, pos + 1);
/* 627:624 */       this.stackTypes[(this.stackTop++)] = new TypeData.UninitData(pos, this.cpool.getClassInfo(i));
/* 628:    */       
/* 629:626 */       return 3;
/* 630:    */     case 188: 
/* 631:628 */       return doNEWARRAY(pos, code);
/* 632:    */     case 189: 
/* 633:630 */       int i = ByteArray.readU16bit(code, pos + 1);
/* 634:631 */       String type = this.cpool.getClassInfo(i).replace('.', '/');
/* 635:632 */       if (type.charAt(0) == '[') {
/* 636:633 */         type = "[" + type;
/* 637:    */       } else {
/* 638:635 */         type = "[L" + type + ";";
/* 639:    */       }
/* 640:637 */       this.stackTypes[(this.stackTop - 1)] = new TypeData.ClassName(type);
/* 641:    */       
/* 642:639 */       return 3;
/* 643:    */     case 190: 
/* 644:641 */       TypeData.setType(this.stackTypes[(this.stackTop - 1)], "[Ljava.lang.Object;", this.classPool);
/* 645:642 */       this.stackTypes[(this.stackTop - 1)] = INTEGER;
/* 646:643 */       break;
/* 647:    */     case 191: 
/* 648:645 */       TypeData.setType(this.stackTypes[(--this.stackTop)], "java.lang.Throwable", this.classPool);
/* 649:646 */       visitThrow(pos, code);
/* 650:647 */       break;
/* 651:    */     case 192: 
/* 652:650 */       int i = ByteArray.readU16bit(code, pos + 1);
/* 653:651 */       this.stackTypes[(this.stackTop - 1)] = new TypeData.ClassName(this.cpool.getClassInfo(i));
/* 654:652 */       return 3;
/* 655:    */     case 193: 
/* 656:655 */       this.stackTypes[(this.stackTop - 1)] = INTEGER;
/* 657:656 */       return 3;
/* 658:    */     case 194: 
/* 659:    */     case 195: 
/* 660:659 */       this.stackTop -= 1;
/* 661:    */       
/* 662:661 */       break;
/* 663:    */     case 196: 
/* 664:663 */       return doWIDE(pos, code);
/* 665:    */     case 197: 
/* 666:665 */       return doMultiANewArray(pos, code);
/* 667:    */     case 198: 
/* 668:    */     case 199: 
/* 669:668 */       this.stackTop -= 1;
/* 670:669 */       visitBranch(pos, code, ByteArray.readS16bit(code, pos + 1));
/* 671:670 */       return 3;
/* 672:    */     case 200: 
/* 673:672 */       visitGoto(pos, code, ByteArray.read32bit(code, pos + 1));
/* 674:673 */       return 5;
/* 675:    */     case 201: 
/* 676:675 */       this.stackTypes[(this.stackTop++)] = TOP;
/* 677:676 */       visitJSR(pos, code);
/* 678:677 */       return 5;
/* 679:    */     }
/* 680:679 */     return 1;
/* 681:    */   }
/* 682:    */   
/* 683:    */   private int doWIDE(int pos, byte[] code)
/* 684:    */     throws BadBytecode
/* 685:    */   {
/* 686:683 */     int op = code[(pos + 1)] & 0xFF;
/* 687:684 */     switch (op)
/* 688:    */     {
/* 689:    */     case 21: 
/* 690:686 */       doWIDE_XLOAD(pos, code, INTEGER);
/* 691:687 */       break;
/* 692:    */     case 22: 
/* 693:689 */       doWIDE_XLOAD(pos, code, LONG);
/* 694:690 */       break;
/* 695:    */     case 23: 
/* 696:692 */       doWIDE_XLOAD(pos, code, FLOAT);
/* 697:693 */       break;
/* 698:    */     case 24: 
/* 699:695 */       doWIDE_XLOAD(pos, code, DOUBLE);
/* 700:696 */       break;
/* 701:    */     case 25: 
/* 702:698 */       int index = ByteArray.readU16bit(code, pos + 2);
/* 703:699 */       doALOAD(index);
/* 704:700 */       break;
/* 705:    */     case 54: 
/* 706:702 */       doWIDE_STORE(pos, code, INTEGER);
/* 707:703 */       break;
/* 708:    */     case 55: 
/* 709:705 */       doWIDE_STORE(pos, code, LONG);
/* 710:706 */       break;
/* 711:    */     case 56: 
/* 712:708 */       doWIDE_STORE(pos, code, FLOAT);
/* 713:709 */       break;
/* 714:    */     case 57: 
/* 715:711 */       doWIDE_STORE(pos, code, DOUBLE);
/* 716:712 */       break;
/* 717:    */     case 58: 
/* 718:714 */       int index = ByteArray.readU16bit(code, pos + 2);
/* 719:715 */       doASTORE(index);
/* 720:716 */       break;
/* 721:    */     case 132: 
/* 722:719 */       return 6;
/* 723:    */     case 169: 
/* 724:721 */       visitRET(pos, code);
/* 725:722 */       break;
/* 726:    */     default: 
/* 727:724 */       throw new RuntimeException("bad WIDE instruction: " + op);
/* 728:    */     }
/* 729:727 */     return 4;
/* 730:    */   }
/* 731:    */   
/* 732:    */   private void doWIDE_XLOAD(int pos, byte[] code, TypeData type)
/* 733:    */   {
/* 734:731 */     int index = ByteArray.readU16bit(code, pos + 2);
/* 735:732 */     doXLOAD(index, type);
/* 736:    */   }
/* 737:    */   
/* 738:    */   private void doWIDE_STORE(int pos, byte[] code, TypeData type)
/* 739:    */   {
/* 740:736 */     int index = ByteArray.readU16bit(code, pos + 2);
/* 741:737 */     doXSTORE(index, type);
/* 742:    */   }
/* 743:    */   
/* 744:    */   private int doPutField(int pos, byte[] code, boolean notStatic)
/* 745:    */     throws BadBytecode
/* 746:    */   {
/* 747:741 */     int index = ByteArray.readU16bit(code, pos + 1);
/* 748:742 */     String desc = this.cpool.getFieldrefType(index);
/* 749:743 */     this.stackTop -= Descriptor.dataSize(desc);
/* 750:744 */     char c = desc.charAt(0);
/* 751:745 */     if (c == 'L') {
/* 752:746 */       TypeData.setType(this.stackTypes[this.stackTop], getFieldClassName(desc, 0), this.classPool);
/* 753:747 */     } else if (c == '[') {
/* 754:748 */       TypeData.setType(this.stackTypes[this.stackTop], desc, this.classPool);
/* 755:    */     }
/* 756:750 */     setFieldTarget(notStatic, index);
/* 757:751 */     return 3;
/* 758:    */   }
/* 759:    */   
/* 760:    */   private int doGetField(int pos, byte[] code, boolean notStatic)
/* 761:    */     throws BadBytecode
/* 762:    */   {
/* 763:755 */     int index = ByteArray.readU16bit(code, pos + 1);
/* 764:756 */     setFieldTarget(notStatic, index);
/* 765:757 */     String desc = this.cpool.getFieldrefType(index);
/* 766:758 */     pushMemberType(desc);
/* 767:759 */     return 3;
/* 768:    */   }
/* 769:    */   
/* 770:    */   private void setFieldTarget(boolean notStatic, int index)
/* 771:    */     throws BadBytecode
/* 772:    */   {
/* 773:763 */     if (notStatic)
/* 774:    */     {
/* 775:764 */       String className = this.cpool.getFieldrefClassName(index);
/* 776:765 */       TypeData.setType(this.stackTypes[(--this.stackTop)], className, this.classPool);
/* 777:    */     }
/* 778:    */   }
/* 779:    */   
/* 780:    */   private int doNEWARRAY(int pos, byte[] code)
/* 781:    */   {
/* 782:770 */     int s = this.stackTop - 1;
/* 783:    */     String type;
/* 784:772 */     switch (code[(pos + 1)] & 0xFF)
/* 785:    */     {
/* 786:    */     case 4: 
/* 787:774 */       type = "[Z";
/* 788:775 */       break;
/* 789:    */     case 5: 
/* 790:777 */       type = "[C";
/* 791:778 */       break;
/* 792:    */     case 6: 
/* 793:780 */       type = "[F";
/* 794:781 */       break;
/* 795:    */     case 7: 
/* 796:783 */       type = "[D";
/* 797:784 */       break;
/* 798:    */     case 8: 
/* 799:786 */       type = "[B";
/* 800:787 */       break;
/* 801:    */     case 9: 
/* 802:789 */       type = "[S";
/* 803:790 */       break;
/* 804:    */     case 10: 
/* 805:792 */       type = "[I";
/* 806:793 */       break;
/* 807:    */     case 11: 
/* 808:795 */       type = "[J";
/* 809:796 */       break;
/* 810:    */     default: 
/* 811:798 */       throw new RuntimeException("bad newarray");
/* 812:    */     }
/* 813:801 */     this.stackTypes[s] = new TypeData.ClassName(type);
/* 814:802 */     return 2;
/* 815:    */   }
/* 816:    */   
/* 817:    */   private int doMultiANewArray(int pos, byte[] code)
/* 818:    */   {
/* 819:806 */     int i = ByteArray.readU16bit(code, pos + 1);
/* 820:807 */     int dim = code[(pos + 3)] & 0xFF;
/* 821:808 */     this.stackTop -= dim - 1;
/* 822:    */     
/* 823:810 */     String type = this.cpool.getClassInfo(i).replace('.', '/');
/* 824:811 */     this.stackTypes[(this.stackTop - 1)] = new TypeData.ClassName(type);
/* 825:812 */     return 4;
/* 826:    */   }
/* 827:    */   
/* 828:    */   private int doInvokeMethod(int pos, byte[] code, boolean notStatic)
/* 829:    */     throws BadBytecode
/* 830:    */   {
/* 831:816 */     int i = ByteArray.readU16bit(code, pos + 1);
/* 832:817 */     String desc = this.cpool.getMethodrefType(i);
/* 833:818 */     checkParamTypes(desc, 1);
/* 834:819 */     if (notStatic)
/* 835:    */     {
/* 836:820 */       String className = this.cpool.getMethodrefClassName(i);
/* 837:821 */       TypeData.setType(this.stackTypes[(--this.stackTop)], className, this.classPool);
/* 838:    */     }
/* 839:824 */     pushMemberType(desc);
/* 840:825 */     return 3;
/* 841:    */   }
/* 842:    */   
/* 843:    */   private int doInvokeIntfMethod(int pos, byte[] code)
/* 844:    */     throws BadBytecode
/* 845:    */   {
/* 846:829 */     int i = ByteArray.readU16bit(code, pos + 1);
/* 847:830 */     String desc = this.cpool.getInterfaceMethodrefType(i);
/* 848:831 */     checkParamTypes(desc, 1);
/* 849:832 */     String className = this.cpool.getInterfaceMethodrefClassName(i);
/* 850:833 */     TypeData.setType(this.stackTypes[(--this.stackTop)], className, this.classPool);
/* 851:834 */     pushMemberType(desc);
/* 852:835 */     return 5;
/* 853:    */   }
/* 854:    */   
/* 855:    */   private void pushMemberType(String descriptor)
/* 856:    */   {
/* 857:839 */     int top = 0;
/* 858:840 */     if (descriptor.charAt(0) == '(')
/* 859:    */     {
/* 860:841 */       top = descriptor.indexOf(')') + 1;
/* 861:842 */       if (top < 1) {
/* 862:843 */         throw new IndexOutOfBoundsException("bad descriptor: " + descriptor);
/* 863:    */       }
/* 864:    */     }
/* 865:847 */     TypeData[] types = this.stackTypes;
/* 866:848 */     int index = this.stackTop;
/* 867:849 */     switch (descriptor.charAt(top))
/* 868:    */     {
/* 869:    */     case '[': 
/* 870:851 */       types[index] = new TypeData.ClassName(descriptor.substring(top));
/* 871:852 */       break;
/* 872:    */     case 'L': 
/* 873:854 */       types[index] = new TypeData.ClassName(getFieldClassName(descriptor, top));
/* 874:855 */       break;
/* 875:    */     case 'J': 
/* 876:857 */       types[index] = LONG;
/* 877:858 */       types[(index + 1)] = TOP;
/* 878:859 */       this.stackTop += 2;
/* 879:860 */       return;
/* 880:    */     case 'F': 
/* 881:862 */       types[index] = FLOAT;
/* 882:863 */       break;
/* 883:    */     case 'D': 
/* 884:865 */       types[index] = DOUBLE;
/* 885:866 */       types[(index + 1)] = TOP;
/* 886:867 */       this.stackTop += 2;
/* 887:868 */       return;
/* 888:    */     case 'V': 
/* 889:870 */       return;
/* 890:    */     default: 
/* 891:872 */       types[index] = INTEGER;
/* 892:    */     }
/* 893:876 */     this.stackTop += 1;
/* 894:    */   }
/* 895:    */   
/* 896:    */   private static String getFieldClassName(String desc, int index)
/* 897:    */   {
/* 898:880 */     return desc.substring(index + 1, desc.length() - 1).replace('/', '.');
/* 899:    */   }
/* 900:    */   
/* 901:    */   private void checkParamTypes(String desc, int i)
/* 902:    */     throws BadBytecode
/* 903:    */   {
/* 904:884 */     char c = desc.charAt(i);
/* 905:885 */     if (c == ')') {
/* 906:886 */       return;
/* 907:    */     }
/* 908:888 */     int k = i;
/* 909:889 */     boolean array = false;
/* 910:890 */     while (c == '[')
/* 911:    */     {
/* 912:891 */       array = true;
/* 913:892 */       c = desc.charAt(++k);
/* 914:    */     }
/* 915:895 */     if (c == 'L')
/* 916:    */     {
/* 917:896 */       k = desc.indexOf(';', k) + 1;
/* 918:897 */       if (k <= 0) {
/* 919:898 */         throw new IndexOutOfBoundsException("bad descriptor");
/* 920:    */       }
/* 921:    */     }
/* 922:    */     else
/* 923:    */     {
/* 924:901 */       k++;
/* 925:    */     }
/* 926:903 */     checkParamTypes(desc, k);
/* 927:904 */     if ((!array) && ((c == 'J') || (c == 'D'))) {
/* 928:905 */       this.stackTop -= 2;
/* 929:    */     } else {
/* 930:907 */       this.stackTop -= 1;
/* 931:    */     }
/* 932:909 */     if (array) {
/* 933:910 */       TypeData.setType(this.stackTypes[this.stackTop], desc.substring(i, k), this.classPool);
/* 934:912 */     } else if (c == 'L') {
/* 935:913 */       TypeData.setType(this.stackTypes[this.stackTop], desc.substring(i + 1, k - 1).replace('/', '.'), this.classPool);
/* 936:    */     }
/* 937:    */   }
/* 938:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.stackmap.Tracer
 * JD-Core Version:    0.7.0.1
 */