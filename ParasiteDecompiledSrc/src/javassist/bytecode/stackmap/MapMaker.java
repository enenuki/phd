/*   1:    */ package javassist.bytecode.stackmap;
/*   2:    */ 
/*   3:    */ import javassist.ClassPool;
/*   4:    */ import javassist.bytecode.BadBytecode;
/*   5:    */ import javassist.bytecode.CodeAttribute;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ import javassist.bytecode.MethodInfo;
/*   8:    */ import javassist.bytecode.StackMap;
/*   9:    */ import javassist.bytecode.StackMap.Writer;
/*  10:    */ import javassist.bytecode.StackMapTable;
/*  11:    */ import javassist.bytecode.StackMapTable.Writer;
/*  12:    */ 
/*  13:    */ public class MapMaker
/*  14:    */   extends Tracer
/*  15:    */ {
/*  16:    */   public static StackMapTable make(ClassPool classes, MethodInfo minfo)
/*  17:    */     throws BadBytecode
/*  18:    */   {
/*  19: 87 */     CodeAttribute ca = minfo.getCodeAttribute();
/*  20: 88 */     if (ca == null) {
/*  21: 89 */       return null;
/*  22:    */     }
/*  23: 91 */     TypedBlock[] blocks = TypedBlock.makeBlocks(minfo, ca, true);
/*  24: 92 */     if (blocks == null) {
/*  25: 93 */       return null;
/*  26:    */     }
/*  27: 95 */     MapMaker mm = new MapMaker(classes, minfo, ca);
/*  28: 96 */     mm.make(blocks, ca.getCode());
/*  29: 97 */     return mm.toStackMap(blocks);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static StackMap make2(ClassPool classes, MethodInfo minfo)
/*  33:    */     throws BadBytecode
/*  34:    */   {
/*  35:108 */     CodeAttribute ca = minfo.getCodeAttribute();
/*  36:109 */     if (ca == null) {
/*  37:110 */       return null;
/*  38:    */     }
/*  39:112 */     TypedBlock[] blocks = TypedBlock.makeBlocks(minfo, ca, true);
/*  40:113 */     if (blocks == null) {
/*  41:114 */       return null;
/*  42:    */     }
/*  43:116 */     MapMaker mm = new MapMaker(classes, minfo, ca);
/*  44:117 */     mm.make(blocks, ca.getCode());
/*  45:118 */     return mm.toStackMap2(minfo.getConstPool(), blocks);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public MapMaker(ClassPool classes, MethodInfo minfo, CodeAttribute ca)
/*  49:    */   {
/*  50:122 */     super(classes, minfo.getConstPool(), ca.getMaxStack(), ca.getMaxLocals(), TypedBlock.getRetType(minfo.getDescriptor()));
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected MapMaker(MapMaker old, boolean copyStack)
/*  54:    */   {
/*  55:128 */     super(old, copyStack);
/*  56:    */   }
/*  57:    */   
/*  58:    */   void make(TypedBlock[] blocks, byte[] code)
/*  59:    */     throws BadBytecode
/*  60:    */   {
/*  61:137 */     TypedBlock first = blocks[0];
/*  62:138 */     fixParamTypes(first);
/*  63:139 */     TypeData[] srcTypes = first.localsTypes;
/*  64:140 */     copyFrom(srcTypes.length, srcTypes, this.localsTypes);
/*  65:141 */     make(code, first);
/*  66:    */     
/*  67:143 */     int n = blocks.length;
/*  68:144 */     for (int i = 0; i < n; i++) {
/*  69:145 */       evalExpected(blocks[i]);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void fixParamTypes(TypedBlock first)
/*  74:    */     throws BadBytecode
/*  75:    */   {
/*  76:155 */     TypeData[] types = first.localsTypes;
/*  77:156 */     int n = types.length;
/*  78:157 */     for (int i = 0; i < n; i++)
/*  79:    */     {
/*  80:158 */       TypeData t = types[i];
/*  81:159 */       if ((t instanceof TypeData.ClassName)) {
/*  82:163 */         TypeData.setType(t, t.getName(), this.classPool);
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void make(byte[] code, TypedBlock tb)
/*  88:    */     throws BadBytecode
/*  89:    */   {
/*  90:173 */     BasicBlock.Catch handlers = tb.toCatch;
/*  91:174 */     while (handlers != null)
/*  92:    */     {
/*  93:175 */       traceException(code, handlers);
/*  94:176 */       handlers = handlers.next;
/*  95:    */     }
/*  96:179 */     int pos = tb.position;
/*  97:180 */     int end = pos + tb.length;
/*  98:181 */     while (pos < end) {
/*  99:182 */       pos += doOpcode(pos, code);
/* 100:    */     }
/* 101:184 */     if (tb.exit != null) {
/* 102:185 */       for (int i = 0; i < tb.exit.length; i++)
/* 103:    */       {
/* 104:186 */         TypedBlock e = (TypedBlock)tb.exit[i];
/* 105:187 */         if (e.alreadySet())
/* 106:    */         {
/* 107:188 */           mergeMap(e, true);
/* 108:    */         }
/* 109:    */         else
/* 110:    */         {
/* 111:190 */           recordStackMap(e);
/* 112:191 */           MapMaker maker = new MapMaker(this, true);
/* 113:192 */           maker.make(code, e);
/* 114:    */         }
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private void traceException(byte[] code, BasicBlock.Catch handler)
/* 120:    */     throws BadBytecode
/* 121:    */   {
/* 122:201 */     TypedBlock tb = (TypedBlock)handler.body;
/* 123:202 */     if (tb.alreadySet())
/* 124:    */     {
/* 125:203 */       mergeMap(tb, false);
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:205 */       recordStackMap(tb, handler.typeIndex);
/* 130:206 */       MapMaker maker = new MapMaker(this, false);
/* 131:    */       
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:211 */       maker.stackTypes[0] = tb.stackTypes[0].getSelf();
/* 136:212 */       maker.stackTop = 1;
/* 137:213 */       maker.make(code, tb);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void mergeMap(TypedBlock dest, boolean mergeStack)
/* 142:    */   {
/* 143:218 */     boolean[] inputs = dest.inputs;
/* 144:219 */     int n = inputs.length;
/* 145:220 */     for (int i = 0; i < n; i++) {
/* 146:221 */       if (inputs[i] != 0) {
/* 147:222 */         merge(this.localsTypes[i], dest.localsTypes[i]);
/* 148:    */       }
/* 149:    */     }
/* 150:224 */     if (mergeStack)
/* 151:    */     {
/* 152:225 */       n = this.stackTop;
/* 153:226 */       for (int i = 0; i < n; i++) {
/* 154:227 */         merge(this.stackTypes[i], dest.stackTypes[i]);
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   private void merge(TypeData td, TypeData target)
/* 160:    */   {
/* 161:232 */     boolean tdIsObj = false;
/* 162:233 */     boolean targetIsObj = false;
/* 163:235 */     if ((td != TOP) && (td.isObjectType())) {
/* 164:236 */       tdIsObj = true;
/* 165:    */     }
/* 166:238 */     if ((target != TOP) && (target.isObjectType())) {
/* 167:239 */       targetIsObj = true;
/* 168:    */     }
/* 169:241 */     if ((tdIsObj) && (targetIsObj)) {
/* 170:242 */       target.merge(td);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   private void recordStackMap(TypedBlock target)
/* 175:    */     throws BadBytecode
/* 176:    */   {
/* 177:248 */     TypeData[] tStackTypes = new TypeData[this.stackTypes.length];
/* 178:249 */     int st = this.stackTop;
/* 179:250 */     copyFrom(st, this.stackTypes, tStackTypes);
/* 180:251 */     recordStackMap0(target, st, tStackTypes);
/* 181:    */   }
/* 182:    */   
/* 183:    */   private void recordStackMap(TypedBlock target, int exceptionType)
/* 184:    */     throws BadBytecode
/* 185:    */   {
/* 186:    */     String type;
/* 187:    */     String type;
/* 188:258 */     if (exceptionType == 0) {
/* 189:259 */       type = "java.lang.Throwable";
/* 190:    */     } else {
/* 191:261 */       type = this.cpool.getClassInfo(exceptionType);
/* 192:    */     }
/* 193:263 */     TypeData[] tStackTypes = new TypeData[this.stackTypes.length];
/* 194:264 */     tStackTypes[0] = new TypeData.ClassName(type);
/* 195:    */     
/* 196:266 */     recordStackMap0(target, 1, tStackTypes);
/* 197:    */   }
/* 198:    */   
/* 199:    */   private void recordStackMap0(TypedBlock target, int st, TypeData[] tStackTypes)
/* 200:    */     throws BadBytecode
/* 201:    */   {
/* 202:272 */     int n = this.localsTypes.length;
/* 203:273 */     TypeData[] tLocalsTypes = new TypeData[n];
/* 204:274 */     int k = copyFrom(n, this.localsTypes, tLocalsTypes);
/* 205:    */     
/* 206:276 */     boolean[] inputs = target.inputs;
/* 207:277 */     for (int i = 0; i < n; i++) {
/* 208:278 */       if (inputs[i] == 0) {
/* 209:279 */         tLocalsTypes[i] = TOP;
/* 210:    */       }
/* 211:    */     }
/* 212:281 */     target.setStackMap(st, tStackTypes, k, tLocalsTypes);
/* 213:    */   }
/* 214:    */   
/* 215:    */   void evalExpected(TypedBlock target)
/* 216:    */     throws BadBytecode
/* 217:    */   {
/* 218:287 */     ClassPool cp = this.classPool;
/* 219:288 */     evalExpected(cp, target.stackTop, target.stackTypes);
/* 220:289 */     TypeData[] types = target.localsTypes;
/* 221:290 */     if (types != null) {
/* 222:291 */       evalExpected(cp, types.length, types);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   private static void evalExpected(ClassPool cp, int n, TypeData[] types)
/* 227:    */     throws BadBytecode
/* 228:    */   {
/* 229:297 */     for (int i = 0; i < n; i++)
/* 230:    */     {
/* 231:298 */       TypeData td = types[i];
/* 232:299 */       if (td != null) {
/* 233:300 */         td.evalExpectedType(cp);
/* 234:    */       }
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   public StackMapTable toStackMap(TypedBlock[] blocks)
/* 239:    */   {
/* 240:307 */     StackMapTable.Writer writer = new StackMapTable.Writer(32);
/* 241:308 */     int n = blocks.length;
/* 242:309 */     TypedBlock prev = blocks[0];
/* 243:310 */     int offsetDelta = prev.length;
/* 244:311 */     if (prev.incoming > 0)
/* 245:    */     {
/* 246:312 */       writer.sameFrame(0);
/* 247:313 */       offsetDelta--;
/* 248:    */     }
/* 249:316 */     for (int i = 1; i < n; i++)
/* 250:    */     {
/* 251:317 */       TypedBlock bb = blocks[i];
/* 252:318 */       if (isTarget(bb, blocks[(i - 1)]))
/* 253:    */       {
/* 254:319 */         bb.resetNumLocals();
/* 255:320 */         int diffL = stackMapDiff(prev.numLocals, prev.localsTypes, bb.numLocals, bb.localsTypes);
/* 256:    */         
/* 257:322 */         toStackMapBody(writer, bb, diffL, offsetDelta, prev);
/* 258:323 */         offsetDelta = bb.length - 1;
/* 259:324 */         prev = bb;
/* 260:    */       }
/* 261:    */       else
/* 262:    */       {
/* 263:327 */         offsetDelta += bb.length;
/* 264:    */       }
/* 265:    */     }
/* 266:330 */     return writer.toStackMapTable(this.cpool);
/* 267:    */   }
/* 268:    */   
/* 269:    */   private boolean isTarget(TypedBlock cur, TypedBlock prev)
/* 270:    */   {
/* 271:337 */     int in = cur.incoming;
/* 272:338 */     if (in > 1) {
/* 273:339 */       return true;
/* 274:    */     }
/* 275:340 */     if (in < 1) {
/* 276:341 */       return false;
/* 277:    */     }
/* 278:343 */     return prev.stop;
/* 279:    */   }
/* 280:    */   
/* 281:    */   private void toStackMapBody(StackMapTable.Writer writer, TypedBlock bb, int diffL, int offsetDelta, TypedBlock prev)
/* 282:    */   {
/* 283:351 */     int stackTop = bb.stackTop;
/* 284:352 */     if (stackTop == 0)
/* 285:    */     {
/* 286:353 */       if (diffL == 0)
/* 287:    */       {
/* 288:354 */         writer.sameFrame(offsetDelta);
/* 289:355 */         return;
/* 290:    */       }
/* 291:357 */       if ((0 > diffL) && (diffL >= -3))
/* 292:    */       {
/* 293:358 */         writer.chopFrame(offsetDelta, -diffL);
/* 294:359 */         return;
/* 295:    */       }
/* 296:361 */       if ((0 < diffL) && (diffL <= 3))
/* 297:    */       {
/* 298:362 */         int[] data = new int[diffL];
/* 299:363 */         int[] tags = fillStackMap(bb.numLocals - prev.numLocals, prev.numLocals, data, bb.localsTypes);
/* 300:    */         
/* 301:    */ 
/* 302:366 */         writer.appendFrame(offsetDelta, tags, data);
/* 303:    */       }
/* 304:    */     }
/* 305:    */     else
/* 306:    */     {
/* 307:370 */       if ((stackTop == 1) && (diffL == 0))
/* 308:    */       {
/* 309:371 */         TypeData td = bb.stackTypes[0];
/* 310:372 */         if (td == TOP) {
/* 311:373 */           writer.sameLocals(offsetDelta, 0, 0);
/* 312:    */         } else {
/* 313:375 */           writer.sameLocals(offsetDelta, td.getTypeTag(), td.getTypeData(this.cpool));
/* 314:    */         }
/* 315:377 */         return;
/* 316:    */       }
/* 317:379 */       if ((stackTop == 2) && (diffL == 0))
/* 318:    */       {
/* 319:380 */         TypeData td = bb.stackTypes[0];
/* 320:381 */         if ((td != TOP) && (td.is2WordType()))
/* 321:    */         {
/* 322:383 */           writer.sameLocals(offsetDelta, td.getTypeTag(), td.getTypeData(this.cpool));
/* 323:    */           
/* 324:385 */           return;
/* 325:    */         }
/* 326:    */       }
/* 327:    */     }
/* 328:389 */     int[] sdata = new int[stackTop];
/* 329:390 */     int[] stags = fillStackMap(stackTop, 0, sdata, bb.stackTypes);
/* 330:391 */     int[] ldata = new int[bb.numLocals];
/* 331:392 */     int[] ltags = fillStackMap(bb.numLocals, 0, ldata, bb.localsTypes);
/* 332:393 */     writer.fullFrame(offsetDelta, ltags, ldata, stags, sdata);
/* 333:    */   }
/* 334:    */   
/* 335:    */   private int[] fillStackMap(int num, int offset, int[] data, TypeData[] types)
/* 336:    */   {
/* 337:397 */     int realNum = diffSize(types, offset, offset + num);
/* 338:398 */     ConstPool cp = this.cpool;
/* 339:399 */     int[] tags = new int[realNum];
/* 340:400 */     int j = 0;
/* 341:401 */     for (int i = 0; i < num; i++)
/* 342:    */     {
/* 343:402 */       TypeData td = types[(offset + i)];
/* 344:403 */       if (td == TOP)
/* 345:    */       {
/* 346:404 */         tags[j] = 0;
/* 347:405 */         data[j] = 0;
/* 348:    */       }
/* 349:    */       else
/* 350:    */       {
/* 351:408 */         tags[j] = td.getTypeTag();
/* 352:409 */         data[j] = td.getTypeData(cp);
/* 353:410 */         if (td.is2WordType()) {
/* 354:411 */           i++;
/* 355:    */         }
/* 356:    */       }
/* 357:414 */       j++;
/* 358:    */     }
/* 359:417 */     return tags;
/* 360:    */   }
/* 361:    */   
/* 362:    */   private static int stackMapDiff(int oldTdLen, TypeData[] oldTd, int newTdLen, TypeData[] newTd)
/* 363:    */   {
/* 364:423 */     int diff = newTdLen - oldTdLen;
/* 365:    */     int len;
/* 366:    */     int len;
/* 367:425 */     if (diff > 0) {
/* 368:426 */       len = oldTdLen;
/* 369:    */     } else {
/* 370:428 */       len = newTdLen;
/* 371:    */     }
/* 372:430 */     if (stackMapEq(oldTd, newTd, len))
/* 373:    */     {
/* 374:431 */       if (diff > 0) {
/* 375:432 */         return diffSize(newTd, len, newTdLen);
/* 376:    */       }
/* 377:434 */       return -diffSize(oldTd, len, oldTdLen);
/* 378:    */     }
/* 379:436 */     return -100;
/* 380:    */   }
/* 381:    */   
/* 382:    */   private static boolean stackMapEq(TypeData[] oldTd, TypeData[] newTd, int len)
/* 383:    */   {
/* 384:440 */     for (int i = 0; i < len; i++)
/* 385:    */     {
/* 386:441 */       TypeData td = oldTd[i];
/* 387:442 */       if (td == TOP)
/* 388:    */       {
/* 389:443 */         if (newTd[i] != TOP) {
/* 390:444 */           return false;
/* 391:    */         }
/* 392:    */       }
/* 393:447 */       else if (!oldTd[i].equals(newTd[i])) {
/* 394:448 */         return false;
/* 395:    */       }
/* 396:    */     }
/* 397:451 */     return true;
/* 398:    */   }
/* 399:    */   
/* 400:    */   private static int diffSize(TypeData[] types, int offset, int len)
/* 401:    */   {
/* 402:455 */     int num = 0;
/* 403:456 */     while (offset < len)
/* 404:    */     {
/* 405:457 */       TypeData td = types[(offset++)];
/* 406:458 */       num++;
/* 407:459 */       if ((td != TOP) && (td.is2WordType())) {
/* 408:460 */         offset++;
/* 409:    */       }
/* 410:    */     }
/* 411:463 */     return num;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public StackMap toStackMap2(ConstPool cp, TypedBlock[] blocks)
/* 415:    */   {
/* 416:469 */     StackMap.Writer writer = new StackMap.Writer();
/* 417:470 */     int n = blocks.length;
/* 418:471 */     boolean[] effective = new boolean[n];
/* 419:472 */     TypedBlock prev = blocks[0];
/* 420:    */     
/* 421:    */ 
/* 422:475 */     effective[0] = (prev.incoming > 0 ? 1 : false);
/* 423:    */     
/* 424:477 */     int num = effective[0] != 0 ? 1 : 0;
/* 425:478 */     for (int i = 1; i < n; i++)
/* 426:    */     {
/* 427:479 */       TypedBlock bb = blocks[i];
/* 428:480 */       if ((effective[i] = isTarget(bb, blocks[(i - 1)])))
/* 429:    */       {
/* 430:481 */         bb.resetNumLocals();
/* 431:482 */         prev = bb;
/* 432:483 */         num++;
/* 433:    */       }
/* 434:    */     }
/* 435:487 */     if (num == 0) {
/* 436:488 */       return null;
/* 437:    */     }
/* 438:490 */     writer.write16bit(num);
/* 439:491 */     for (int i = 0; i < n; i++) {
/* 440:492 */       if (effective[i] != 0) {
/* 441:493 */         writeStackFrame(writer, cp, blocks[i].position, blocks[i]);
/* 442:    */       }
/* 443:    */     }
/* 444:495 */     return writer.toStackMap(cp);
/* 445:    */   }
/* 446:    */   
/* 447:    */   private void writeStackFrame(StackMap.Writer writer, ConstPool cp, int offset, TypedBlock tb)
/* 448:    */   {
/* 449:499 */     writer.write16bit(offset);
/* 450:500 */     writeVerifyTypeInfo(writer, cp, tb.localsTypes, tb.numLocals);
/* 451:501 */     writeVerifyTypeInfo(writer, cp, tb.stackTypes, tb.stackTop);
/* 452:    */   }
/* 453:    */   
/* 454:    */   private void writeVerifyTypeInfo(StackMap.Writer writer, ConstPool cp, TypeData[] types, int num)
/* 455:    */   {
/* 456:505 */     int numDWord = 0;
/* 457:506 */     for (int i = 0; i < num; i++)
/* 458:    */     {
/* 459:507 */       TypeData td = types[i];
/* 460:508 */       if ((td != null) && (td.is2WordType()))
/* 461:    */       {
/* 462:509 */         numDWord++;
/* 463:510 */         i++;
/* 464:    */       }
/* 465:    */     }
/* 466:514 */     writer.write16bit(num - numDWord);
/* 467:515 */     for (int i = 0; i < num; i++)
/* 468:    */     {
/* 469:516 */       TypeData td = types[i];
/* 470:517 */       if (td == TOP)
/* 471:    */       {
/* 472:518 */         writer.writeVerifyTypeInfo(0, 0);
/* 473:    */       }
/* 474:    */       else
/* 475:    */       {
/* 476:520 */         writer.writeVerifyTypeInfo(td.getTypeTag(), td.getTypeData(cp));
/* 477:521 */         if (td.is2WordType()) {
/* 478:522 */           i++;
/* 479:    */         }
/* 480:    */       }
/* 481:    */     }
/* 482:    */   }
/* 483:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.stackmap.MapMaker
 * JD-Core Version:    0.7.0.1
 */