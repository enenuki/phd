/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.DataOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.PrintWriter;
/*   9:    */ import java.util.Map;
/*  10:    */ import javassist.CannotCompileException;
/*  11:    */ 
/*  12:    */ public class StackMapTable
/*  13:    */   extends AttributeInfo
/*  14:    */ {
/*  15:    */   public static final String tag = "StackMapTable";
/*  16:    */   public static final int TOP = 0;
/*  17:    */   public static final int INTEGER = 1;
/*  18:    */   public static final int FLOAT = 2;
/*  19:    */   public static final int DOUBLE = 3;
/*  20:    */   public static final int LONG = 4;
/*  21:    */   public static final int NULL = 5;
/*  22:    */   public static final int THIS = 6;
/*  23:    */   public static final int OBJECT = 7;
/*  24:    */   public static final int UNINIT = 8;
/*  25:    */   
/*  26:    */   StackMapTable(ConstPool cp, byte[] newInfo)
/*  27:    */   {
/*  28: 46 */     super(cp, "StackMapTable", newInfo);
/*  29:    */   }
/*  30:    */   
/*  31:    */   StackMapTable(ConstPool cp, int name_id, DataInputStream in)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 52 */     super(cp, name_id, in);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  38:    */     throws StackMapTable.RuntimeCopyException
/*  39:    */   {
/*  40:    */     try
/*  41:    */     {
/*  42: 68 */       return new StackMapTable(newCp, new Copier(this.constPool, this.info, newCp).doit());
/*  43:    */     }
/*  44:    */     catch (BadBytecode e)
/*  45:    */     {
/*  46: 72 */       throw new RuntimeCopyException("bad bytecode. fatal?");
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static class RuntimeCopyException
/*  51:    */     extends RuntimeException
/*  52:    */   {
/*  53:    */     public RuntimeCopyException(String s)
/*  54:    */     {
/*  55: 85 */       super();
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   void write(DataOutputStream out)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62: 90 */     super.write(out);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static class Walker
/*  66:    */   {
/*  67:    */     byte[] info;
/*  68:    */     int numOfEntries;
/*  69:    */     
/*  70:    */     public Walker(StackMapTable smt)
/*  71:    */     {
/*  72:152 */       this(smt.get());
/*  73:    */     }
/*  74:    */     
/*  75:    */     public Walker(byte[] data)
/*  76:    */     {
/*  77:164 */       this.info = data;
/*  78:165 */       this.numOfEntries = ByteArray.readU16bit(data, 0);
/*  79:    */     }
/*  80:    */     
/*  81:    */     public final int size()
/*  82:    */     {
/*  83:171 */       return this.numOfEntries;
/*  84:    */     }
/*  85:    */     
/*  86:    */     public void parse()
/*  87:    */       throws BadBytecode
/*  88:    */     {
/*  89:177 */       int n = this.numOfEntries;
/*  90:178 */       int pos = 2;
/*  91:179 */       for (int i = 0; i < n; i++) {
/*  92:180 */         pos = stackMapFrames(pos, i);
/*  93:    */       }
/*  94:    */     }
/*  95:    */     
/*  96:    */     int stackMapFrames(int pos, int nth)
/*  97:    */       throws BadBytecode
/*  98:    */     {
/*  99:193 */       int type = this.info[pos] & 0xFF;
/* 100:194 */       if (type < 64)
/* 101:    */       {
/* 102:195 */         sameFrame(pos, type);
/* 103:196 */         pos++;
/* 104:    */       }
/* 105:198 */       else if (type < 128)
/* 106:    */       {
/* 107:199 */         pos = sameLocals(pos, type);
/* 108:    */       }
/* 109:    */       else
/* 110:    */       {
/* 111:200 */         if (type < 247) {
/* 112:201 */           throw new BadBytecode("bad frame_type in StackMapTable");
/* 113:    */         }
/* 114:202 */         if (type == 247)
/* 115:    */         {
/* 116:203 */           pos = sameLocals(pos, type);
/* 117:    */         }
/* 118:204 */         else if (type < 251)
/* 119:    */         {
/* 120:205 */           int offset = ByteArray.readU16bit(this.info, pos + 1);
/* 121:206 */           chopFrame(pos, offset, 251 - type);
/* 122:207 */           pos += 3;
/* 123:    */         }
/* 124:209 */         else if (type == 251)
/* 125:    */         {
/* 126:210 */           int offset = ByteArray.readU16bit(this.info, pos + 1);
/* 127:211 */           sameFrame(pos, offset);
/* 128:212 */           pos += 3;
/* 129:    */         }
/* 130:214 */         else if (type < 255)
/* 131:    */         {
/* 132:215 */           pos = appendFrame(pos, type);
/* 133:    */         }
/* 134:    */         else
/* 135:    */         {
/* 136:217 */           pos = fullFrame(pos);
/* 137:    */         }
/* 138:    */       }
/* 139:219 */       return pos;
/* 140:    */     }
/* 141:    */     
/* 142:    */     public void sameFrame(int pos, int offsetDelta)
/* 143:    */       throws BadBytecode
/* 144:    */     {}
/* 145:    */     
/* 146:    */     private int sameLocals(int pos, int type)
/* 147:    */       throws BadBytecode
/* 148:    */     {
/* 149:233 */       int top = pos;
/* 150:    */       int offset;
/* 151:    */       int offset;
/* 152:235 */       if (type < 128)
/* 153:    */       {
/* 154:236 */         offset = type - 64;
/* 155:    */       }
/* 156:    */       else
/* 157:    */       {
/* 158:238 */         offset = ByteArray.readU16bit(this.info, pos + 1);
/* 159:239 */         pos += 2;
/* 160:    */       }
/* 161:242 */       int tag = this.info[(pos + 1)] & 0xFF;
/* 162:243 */       int data = 0;
/* 163:244 */       if ((tag == 7) || (tag == 8))
/* 164:    */       {
/* 165:245 */         data = ByteArray.readU16bit(this.info, pos + 2);
/* 166:246 */         pos += 2;
/* 167:    */       }
/* 168:249 */       sameLocals(top, offset, tag, data);
/* 169:250 */       return pos + 2;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public void sameLocals(int pos, int offsetDelta, int stackTag, int stackData)
/* 173:    */       throws BadBytecode
/* 174:    */     {}
/* 175:    */     
/* 176:    */     public void chopFrame(int pos, int offsetDelta, int k)
/* 177:    */       throws BadBytecode
/* 178:    */     {}
/* 179:    */     
/* 180:    */     private int appendFrame(int pos, int type)
/* 181:    */       throws BadBytecode
/* 182:    */     {
/* 183:278 */       int k = type - 251;
/* 184:279 */       int offset = ByteArray.readU16bit(this.info, pos + 1);
/* 185:280 */       int[] tags = new int[k];
/* 186:281 */       int[] data = new int[k];
/* 187:282 */       int p = pos + 3;
/* 188:283 */       for (int i = 0; i < k; i++)
/* 189:    */       {
/* 190:284 */         int tag = this.info[p] & 0xFF;
/* 191:285 */         tags[i] = tag;
/* 192:286 */         if ((tag == 7) || (tag == 8))
/* 193:    */         {
/* 194:287 */           data[i] = ByteArray.readU16bit(this.info, p + 1);
/* 195:288 */           p += 3;
/* 196:    */         }
/* 197:    */         else
/* 198:    */         {
/* 199:291 */           data[i] = 0;
/* 200:292 */           p++;
/* 201:    */         }
/* 202:    */       }
/* 203:296 */       appendFrame(pos, offset, tags, data);
/* 204:297 */       return p;
/* 205:    */     }
/* 206:    */     
/* 207:    */     public void appendFrame(int pos, int offsetDelta, int[] tags, int[] data)
/* 208:    */       throws BadBytecode
/* 209:    */     {}
/* 210:    */     
/* 211:    */     private int fullFrame(int pos)
/* 212:    */       throws BadBytecode
/* 213:    */     {
/* 214:313 */       int offset = ByteArray.readU16bit(this.info, pos + 1);
/* 215:314 */       int numOfLocals = ByteArray.readU16bit(this.info, pos + 3);
/* 216:315 */       int[] localsTags = new int[numOfLocals];
/* 217:316 */       int[] localsData = new int[numOfLocals];
/* 218:317 */       int p = verifyTypeInfo(pos + 5, numOfLocals, localsTags, localsData);
/* 219:318 */       int numOfItems = ByteArray.readU16bit(this.info, p);
/* 220:319 */       int[] itemsTags = new int[numOfItems];
/* 221:320 */       int[] itemsData = new int[numOfItems];
/* 222:321 */       p = verifyTypeInfo(p + 2, numOfItems, itemsTags, itemsData);
/* 223:322 */       fullFrame(pos, offset, localsTags, localsData, itemsTags, itemsData);
/* 224:323 */       return p;
/* 225:    */     }
/* 226:    */     
/* 227:    */     public void fullFrame(int pos, int offsetDelta, int[] localTags, int[] localData, int[] stackTags, int[] stackData)
/* 228:    */       throws BadBytecode
/* 229:    */     {}
/* 230:    */     
/* 231:    */     private int verifyTypeInfo(int pos, int n, int[] tags, int[] data)
/* 232:    */     {
/* 233:343 */       for (int i = 0; i < n; i++)
/* 234:    */       {
/* 235:344 */         int tag = this.info[(pos++)] & 0xFF;
/* 236:345 */         tags[i] = tag;
/* 237:346 */         if ((tag == 7) || (tag == 8))
/* 238:    */         {
/* 239:347 */           data[i] = ByteArray.readU16bit(this.info, pos);
/* 240:348 */           pos += 2;
/* 241:    */         }
/* 242:    */       }
/* 243:352 */       return pos;
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   static class SimpleCopy
/* 248:    */     extends StackMapTable.Walker
/* 249:    */   {
/* 250:    */     private StackMapTable.Writer writer;
/* 251:    */     
/* 252:    */     public SimpleCopy(byte[] data)
/* 253:    */     {
/* 254:360 */       super();
/* 255:361 */       this.writer = new StackMapTable.Writer(data.length);
/* 256:    */     }
/* 257:    */     
/* 258:    */     public byte[] doit()
/* 259:    */       throws BadBytecode
/* 260:    */     {
/* 261:365 */       parse();
/* 262:366 */       return this.writer.toByteArray();
/* 263:    */     }
/* 264:    */     
/* 265:    */     public void sameFrame(int pos, int offsetDelta)
/* 266:    */     {
/* 267:370 */       this.writer.sameFrame(offsetDelta);
/* 268:    */     }
/* 269:    */     
/* 270:    */     public void sameLocals(int pos, int offsetDelta, int stackTag, int stackData)
/* 271:    */     {
/* 272:374 */       this.writer.sameLocals(offsetDelta, stackTag, copyData(stackTag, stackData));
/* 273:    */     }
/* 274:    */     
/* 275:    */     public void chopFrame(int pos, int offsetDelta, int k)
/* 276:    */     {
/* 277:378 */       this.writer.chopFrame(offsetDelta, k);
/* 278:    */     }
/* 279:    */     
/* 280:    */     public void appendFrame(int pos, int offsetDelta, int[] tags, int[] data)
/* 281:    */     {
/* 282:382 */       this.writer.appendFrame(offsetDelta, tags, copyData(tags, data));
/* 283:    */     }
/* 284:    */     
/* 285:    */     public void fullFrame(int pos, int offsetDelta, int[] localTags, int[] localData, int[] stackTags, int[] stackData)
/* 286:    */     {
/* 287:387 */       this.writer.fullFrame(offsetDelta, localTags, copyData(localTags, localData), stackTags, copyData(stackTags, stackData));
/* 288:    */     }
/* 289:    */     
/* 290:    */     protected int copyData(int tag, int data)
/* 291:    */     {
/* 292:392 */       return data;
/* 293:    */     }
/* 294:    */     
/* 295:    */     protected int[] copyData(int[] tags, int[] data)
/* 296:    */     {
/* 297:396 */       return data;
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   static class Copier
/* 302:    */     extends StackMapTable.SimpleCopy
/* 303:    */   {
/* 304:    */     private ConstPool srcPool;
/* 305:    */     private ConstPool destPool;
/* 306:    */     
/* 307:    */     public Copier(ConstPool src, byte[] data, ConstPool dest)
/* 308:    */     {
/* 309:404 */       super();
/* 310:405 */       this.srcPool = src;
/* 311:406 */       this.destPool = dest;
/* 312:    */     }
/* 313:    */     
/* 314:    */     protected int copyData(int tag, int data)
/* 315:    */     {
/* 316:410 */       if (tag == 7) {
/* 317:411 */         return this.srcPool.copy(data, this.destPool, null);
/* 318:    */       }
/* 319:413 */       return data;
/* 320:    */     }
/* 321:    */     
/* 322:    */     protected int[] copyData(int[] tags, int[] data)
/* 323:    */     {
/* 324:417 */       int[] newData = new int[data.length];
/* 325:418 */       for (int i = 0; i < data.length; i++) {
/* 326:419 */         if (tags[i] == 7) {
/* 327:420 */           newData[i] = this.srcPool.copy(data[i], this.destPool, null);
/* 328:    */         } else {
/* 329:422 */           newData[i] = data[i];
/* 330:    */         }
/* 331:    */       }
/* 332:424 */       return newData;
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void insertLocal(int index, int tag, int classInfo)
/* 337:    */     throws BadBytecode
/* 338:    */   {
/* 339:445 */     byte[] data = new InsertLocal(get(), index, tag, classInfo).doit();
/* 340:446 */     set(data);
/* 341:    */   }
/* 342:    */   
/* 343:    */   public static int typeTagOf(char descriptor)
/* 344:    */   {
/* 345:459 */     switch (descriptor)
/* 346:    */     {
/* 347:    */     case 'D': 
/* 348:461 */       return 3;
/* 349:    */     case 'F': 
/* 350:463 */       return 2;
/* 351:    */     case 'J': 
/* 352:465 */       return 4;
/* 353:    */     case 'L': 
/* 354:    */     case '[': 
/* 355:468 */       return 7;
/* 356:    */     }
/* 357:471 */     return 1;
/* 358:    */   }
/* 359:    */   
/* 360:    */   static class InsertLocal
/* 361:    */     extends StackMapTable.SimpleCopy
/* 362:    */   {
/* 363:    */     private int varIndex;
/* 364:    */     private int varTag;
/* 365:    */     private int varData;
/* 366:    */     
/* 367:    */     public InsertLocal(byte[] data, int varIndex, int varTag, int varData)
/* 368:    */     {
/* 369:485 */       super();
/* 370:486 */       this.varIndex = varIndex;
/* 371:487 */       this.varTag = varTag;
/* 372:488 */       this.varData = varData;
/* 373:    */     }
/* 374:    */     
/* 375:    */     public void fullFrame(int pos, int offsetDelta, int[] localTags, int[] localData, int[] stackTags, int[] stackData)
/* 376:    */     {
/* 377:493 */       int len = localTags.length;
/* 378:494 */       if (len < this.varIndex)
/* 379:    */       {
/* 380:495 */         super.fullFrame(pos, offsetDelta, localTags, localData, stackTags, stackData);
/* 381:496 */         return;
/* 382:    */       }
/* 383:499 */       int typeSize = (this.varTag == 4) || (this.varTag == 3) ? 2 : 1;
/* 384:500 */       int[] localTags2 = new int[len + typeSize];
/* 385:501 */       int[] localData2 = new int[len + typeSize];
/* 386:502 */       int index = this.varIndex;
/* 387:503 */       int j = 0;
/* 388:504 */       for (int i = 0; i < len; i++)
/* 389:    */       {
/* 390:505 */         if (j == index) {
/* 391:506 */           j += typeSize;
/* 392:    */         }
/* 393:508 */         localTags2[j] = localTags[i];
/* 394:509 */         localData2[(j++)] = localData[i];
/* 395:    */       }
/* 396:512 */       localTags2[index] = this.varTag;
/* 397:513 */       localData2[index] = this.varData;
/* 398:514 */       if (typeSize > 1)
/* 399:    */       {
/* 400:515 */         localTags2[(index + 1)] = 0;
/* 401:516 */         localData2[(index + 1)] = 0;
/* 402:    */       }
/* 403:519 */       super.fullFrame(pos, offsetDelta, localTags2, localData2, stackTags, stackData);
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   public static class Writer
/* 408:    */   {
/* 409:    */     ByteArrayOutputStream output;
/* 410:    */     int numOfEntries;
/* 411:    */     
/* 412:    */     public Writer(int size)
/* 413:    */     {
/* 414:535 */       this.output = new ByteArrayOutputStream(size);
/* 415:536 */       this.numOfEntries = 0;
/* 416:537 */       this.output.write(0);
/* 417:538 */       this.output.write(0);
/* 418:    */     }
/* 419:    */     
/* 420:    */     public byte[] toByteArray()
/* 421:    */     {
/* 422:545 */       byte[] b = this.output.toByteArray();
/* 423:546 */       ByteArray.write16bit(this.numOfEntries, b, 0);
/* 424:547 */       return b;
/* 425:    */     }
/* 426:    */     
/* 427:    */     public StackMapTable toStackMapTable(ConstPool cp)
/* 428:    */     {
/* 429:558 */       return new StackMapTable(cp, toByteArray());
/* 430:    */     }
/* 431:    */     
/* 432:    */     public void sameFrame(int offsetDelta)
/* 433:    */     {
/* 434:565 */       this.numOfEntries += 1;
/* 435:566 */       if (offsetDelta < 64)
/* 436:    */       {
/* 437:567 */         this.output.write(offsetDelta);
/* 438:    */       }
/* 439:    */       else
/* 440:    */       {
/* 441:569 */         this.output.write(251);
/* 442:570 */         write16(offsetDelta);
/* 443:    */       }
/* 444:    */     }
/* 445:    */     
/* 446:    */     public void sameLocals(int offsetDelta, int tag, int data)
/* 447:    */     {
/* 448:586 */       this.numOfEntries += 1;
/* 449:587 */       if (offsetDelta < 64)
/* 450:    */       {
/* 451:588 */         this.output.write(offsetDelta + 64);
/* 452:    */       }
/* 453:    */       else
/* 454:    */       {
/* 455:590 */         this.output.write(247);
/* 456:591 */         write16(offsetDelta);
/* 457:    */       }
/* 458:594 */       writeTypeInfo(tag, data);
/* 459:    */     }
/* 460:    */     
/* 461:    */     public void chopFrame(int offsetDelta, int k)
/* 462:    */     {
/* 463:603 */       this.numOfEntries += 1;
/* 464:604 */       this.output.write(251 - k);
/* 465:605 */       write16(offsetDelta);
/* 466:    */     }
/* 467:    */     
/* 468:    */     public void appendFrame(int offsetDelta, int[] tags, int[] data)
/* 469:    */     {
/* 470:622 */       this.numOfEntries += 1;
/* 471:623 */       int k = tags.length;
/* 472:624 */       this.output.write(k + 251);
/* 473:625 */       write16(offsetDelta);
/* 474:626 */       for (int i = 0; i < k; i++) {
/* 475:627 */         writeTypeInfo(tags[i], data[i]);
/* 476:    */       }
/* 477:    */     }
/* 478:    */     
/* 479:    */     public void fullFrame(int offsetDelta, int[] localTags, int[] localData, int[] stackTags, int[] stackData)
/* 480:    */     {
/* 481:651 */       this.numOfEntries += 1;
/* 482:652 */       this.output.write(255);
/* 483:653 */       write16(offsetDelta);
/* 484:654 */       int n = localTags.length;
/* 485:655 */       write16(n);
/* 486:656 */       for (int i = 0; i < n; i++) {
/* 487:657 */         writeTypeInfo(localTags[i], localData[i]);
/* 488:    */       }
/* 489:659 */       n = stackTags.length;
/* 490:660 */       write16(n);
/* 491:661 */       for (int i = 0; i < n; i++) {
/* 492:662 */         writeTypeInfo(stackTags[i], stackData[i]);
/* 493:    */       }
/* 494:    */     }
/* 495:    */     
/* 496:    */     private void writeTypeInfo(int tag, int data)
/* 497:    */     {
/* 498:666 */       this.output.write(tag);
/* 499:667 */       if ((tag == 7) || (tag == 8)) {
/* 500:668 */         write16(data);
/* 501:    */       }
/* 502:    */     }
/* 503:    */     
/* 504:    */     private void write16(int value)
/* 505:    */     {
/* 506:672 */       this.output.write(value >>> 8 & 0xFF);
/* 507:673 */       this.output.write(value & 0xFF);
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void println(PrintWriter w)
/* 512:    */   {
/* 513:681 */     Printer.print(this, w);
/* 514:    */   }
/* 515:    */   
/* 516:    */   public void println(PrintStream ps)
/* 517:    */   {
/* 518:690 */     Printer.print(this, new PrintWriter(ps, true));
/* 519:    */   }
/* 520:    */   
/* 521:    */   static class Printer
/* 522:    */     extends StackMapTable.Walker
/* 523:    */   {
/* 524:    */     private PrintWriter writer;
/* 525:    */     private int offset;
/* 526:    */     
/* 527:    */     public static void print(StackMapTable smt, PrintWriter writer)
/* 528:    */     {
/* 529:    */       try
/* 530:    */       {
/* 531:702 */         new Printer(smt.get(), writer).parse();
/* 532:    */       }
/* 533:    */       catch (BadBytecode e)
/* 534:    */       {
/* 535:705 */         writer.println(e.getMessage());
/* 536:    */       }
/* 537:    */     }
/* 538:    */     
/* 539:    */     Printer(byte[] data, PrintWriter pw)
/* 540:    */     {
/* 541:710 */       super();
/* 542:711 */       this.writer = pw;
/* 543:712 */       this.offset = -1;
/* 544:    */     }
/* 545:    */     
/* 546:    */     public void sameFrame(int pos, int offsetDelta)
/* 547:    */     {
/* 548:716 */       this.offset += offsetDelta + 1;
/* 549:717 */       this.writer.println(this.offset + " same frame: " + offsetDelta);
/* 550:    */     }
/* 551:    */     
/* 552:    */     public void sameLocals(int pos, int offsetDelta, int stackTag, int stackData)
/* 553:    */     {
/* 554:721 */       this.offset += offsetDelta + 1;
/* 555:722 */       this.writer.println(this.offset + " same locals: " + offsetDelta);
/* 556:723 */       printTypeInfo(stackTag, stackData);
/* 557:    */     }
/* 558:    */     
/* 559:    */     public void chopFrame(int pos, int offsetDelta, int k)
/* 560:    */     {
/* 561:727 */       this.offset += offsetDelta + 1;
/* 562:728 */       this.writer.println(this.offset + " chop frame: " + offsetDelta + ",    " + k + " last locals");
/* 563:    */     }
/* 564:    */     
/* 565:    */     public void appendFrame(int pos, int offsetDelta, int[] tags, int[] data)
/* 566:    */     {
/* 567:732 */       this.offset += offsetDelta + 1;
/* 568:733 */       this.writer.println(this.offset + " append frame: " + offsetDelta);
/* 569:734 */       for (int i = 0; i < tags.length; i++) {
/* 570:735 */         printTypeInfo(tags[i], data[i]);
/* 571:    */       }
/* 572:    */     }
/* 573:    */     
/* 574:    */     public void fullFrame(int pos, int offsetDelta, int[] localTags, int[] localData, int[] stackTags, int[] stackData)
/* 575:    */     {
/* 576:740 */       this.offset += offsetDelta + 1;
/* 577:741 */       this.writer.println(this.offset + " full frame: " + offsetDelta);
/* 578:742 */       this.writer.println("[locals]");
/* 579:743 */       for (int i = 0; i < localTags.length; i++) {
/* 580:744 */         printTypeInfo(localTags[i], localData[i]);
/* 581:    */       }
/* 582:746 */       this.writer.println("[stack]");
/* 583:747 */       for (int i = 0; i < stackTags.length; i++) {
/* 584:748 */         printTypeInfo(stackTags[i], stackData[i]);
/* 585:    */       }
/* 586:    */     }
/* 587:    */     
/* 588:    */     private void printTypeInfo(int tag, int data)
/* 589:    */     {
/* 590:752 */       String msg = null;
/* 591:753 */       switch (tag)
/* 592:    */       {
/* 593:    */       case 0: 
/* 594:755 */         msg = "top";
/* 595:756 */         break;
/* 596:    */       case 1: 
/* 597:758 */         msg = "integer";
/* 598:759 */         break;
/* 599:    */       case 2: 
/* 600:761 */         msg = "float";
/* 601:762 */         break;
/* 602:    */       case 3: 
/* 603:764 */         msg = "double";
/* 604:765 */         break;
/* 605:    */       case 4: 
/* 606:767 */         msg = "long";
/* 607:768 */         break;
/* 608:    */       case 5: 
/* 609:770 */         msg = "null";
/* 610:771 */         break;
/* 611:    */       case 6: 
/* 612:773 */         msg = "this";
/* 613:774 */         break;
/* 614:    */       case 7: 
/* 615:776 */         msg = "object (cpool_index " + data + ")";
/* 616:777 */         break;
/* 617:    */       case 8: 
/* 618:779 */         msg = "uninitialized (offset " + data + ")";
/* 619:    */       }
/* 620:783 */       this.writer.print("    ");
/* 621:784 */       this.writer.println(msg);
/* 622:    */     }
/* 623:    */   }
/* 624:    */   
/* 625:    */   void shiftPc(int where, int gapSize, boolean exclusive)
/* 626:    */     throws BadBytecode
/* 627:    */   {
/* 628:791 */     new Shifter(this, where, gapSize, exclusive).doit();
/* 629:    */   }
/* 630:    */   
/* 631:    */   static class Shifter
/* 632:    */     extends StackMapTable.Walker
/* 633:    */   {
/* 634:    */     private StackMapTable stackMap;
/* 635:    */     private int where;
/* 636:    */     private int gap;
/* 637:    */     private int position;
/* 638:    */     private byte[] updatedInfo;
/* 639:    */     private boolean exclusive;
/* 640:    */     
/* 641:    */     public Shifter(StackMapTable smt, int where, int gap, boolean exclusive)
/* 642:    */     {
/* 643:802 */       super();
/* 644:803 */       this.stackMap = smt;
/* 645:804 */       this.where = where;
/* 646:805 */       this.gap = gap;
/* 647:806 */       this.position = 0;
/* 648:807 */       this.updatedInfo = null;
/* 649:808 */       this.exclusive = exclusive;
/* 650:    */     }
/* 651:    */     
/* 652:    */     public void doit()
/* 653:    */       throws BadBytecode
/* 654:    */     {
/* 655:812 */       parse();
/* 656:813 */       if (this.updatedInfo != null) {
/* 657:814 */         this.stackMap.set(this.updatedInfo);
/* 658:    */       }
/* 659:    */     }
/* 660:    */     
/* 661:    */     public void sameFrame(int pos, int offsetDelta)
/* 662:    */     {
/* 663:818 */       update(pos, offsetDelta, 0, 251);
/* 664:    */     }
/* 665:    */     
/* 666:    */     public void sameLocals(int pos, int offsetDelta, int stackTag, int stackData)
/* 667:    */     {
/* 668:822 */       update(pos, offsetDelta, 64, 247);
/* 669:    */     }
/* 670:    */     
/* 671:    */     private void update(int pos, int offsetDelta, int base, int entry)
/* 672:    */     {
/* 673:826 */       int oldPos = this.position;
/* 674:827 */       this.position = (oldPos + offsetDelta + (oldPos == 0 ? 0 : 1));
/* 675:    */       boolean match;
/* 676:    */       boolean match;
/* 677:829 */       if (this.exclusive) {
/* 678:830 */         match = (oldPos < this.where) && (this.where <= this.position);
/* 679:    */       } else {
/* 680:832 */         match = (oldPos <= this.where) && (this.where < this.position);
/* 681:    */       }
/* 682:834 */       if (match)
/* 683:    */       {
/* 684:835 */         int newDelta = offsetDelta + this.gap;
/* 685:836 */         this.position += this.gap;
/* 686:837 */         if (newDelta < 64)
/* 687:    */         {
/* 688:838 */           this.info[pos] = ((byte)(newDelta + base));
/* 689:    */         }
/* 690:839 */         else if (offsetDelta < 64)
/* 691:    */         {
/* 692:840 */           byte[] newinfo = insertGap(this.info, pos, 2);
/* 693:841 */           newinfo[pos] = ((byte)entry);
/* 694:842 */           ByteArray.write16bit(newDelta, newinfo, pos + 1);
/* 695:843 */           this.updatedInfo = newinfo;
/* 696:    */         }
/* 697:    */         else
/* 698:    */         {
/* 699:846 */           ByteArray.write16bit(newDelta, this.info, pos + 1);
/* 700:    */         }
/* 701:    */       }
/* 702:    */     }
/* 703:    */     
/* 704:    */     private static byte[] insertGap(byte[] info, int where, int gap)
/* 705:    */     {
/* 706:851 */       int len = info.length;
/* 707:852 */       byte[] newinfo = new byte[len + gap];
/* 708:853 */       for (int i = 0; i < len; i++) {
/* 709:854 */         newinfo[(i + (i < where ? 0 : gap))] = info[i];
/* 710:    */       }
/* 711:856 */       return newinfo;
/* 712:    */     }
/* 713:    */     
/* 714:    */     public void chopFrame(int pos, int offsetDelta, int k)
/* 715:    */     {
/* 716:860 */       update(pos, offsetDelta);
/* 717:    */     }
/* 718:    */     
/* 719:    */     public void appendFrame(int pos, int offsetDelta, int[] tags, int[] data)
/* 720:    */     {
/* 721:864 */       update(pos, offsetDelta);
/* 722:    */     }
/* 723:    */     
/* 724:    */     public void fullFrame(int pos, int offsetDelta, int[] localTags, int[] localData, int[] stackTags, int[] stackData)
/* 725:    */     {
/* 726:869 */       update(pos, offsetDelta);
/* 727:    */     }
/* 728:    */     
/* 729:    */     private void update(int pos, int offsetDelta)
/* 730:    */     {
/* 731:873 */       int oldPos = this.position;
/* 732:874 */       this.position = (oldPos + offsetDelta + (oldPos == 0 ? 0 : 1));
/* 733:    */       boolean match;
/* 734:    */       boolean match;
/* 735:876 */       if (this.exclusive) {
/* 736:877 */         match = (oldPos < this.where) && (this.where <= this.position);
/* 737:    */       } else {
/* 738:879 */         match = (oldPos <= this.where) && (this.where < this.position);
/* 739:    */       }
/* 740:881 */       if (match)
/* 741:    */       {
/* 742:882 */         int newDelta = offsetDelta + this.gap;
/* 743:883 */         ByteArray.write16bit(newDelta, this.info, pos + 1);
/* 744:884 */         this.position += this.gap;
/* 745:    */       }
/* 746:    */     }
/* 747:    */   }
/* 748:    */   
/* 749:    */   public void removeNew(int where)
/* 750:    */     throws CannotCompileException
/* 751:    */   {
/* 752:    */     try
/* 753:    */     {
/* 754:900 */       byte[] data = new NewRemover(get(), where).doit();
/* 755:901 */       set(data);
/* 756:    */     }
/* 757:    */     catch (BadBytecode e)
/* 758:    */     {
/* 759:904 */       throw new CannotCompileException("bad stack map table", e);
/* 760:    */     }
/* 761:    */   }
/* 762:    */   
/* 763:    */   static class NewRemover
/* 764:    */     extends StackMapTable.SimpleCopy
/* 765:    */   {
/* 766:    */     int posOfNew;
/* 767:    */     
/* 768:    */     public NewRemover(byte[] data, int pos)
/* 769:    */     {
/* 770:912 */       super();
/* 771:913 */       this.posOfNew = pos;
/* 772:    */     }
/* 773:    */     
/* 774:    */     public void sameLocals(int pos, int offsetDelta, int stackTag, int stackData)
/* 775:    */     {
/* 776:917 */       if ((stackTag == 8) && (stackData == this.posOfNew)) {
/* 777:918 */         super.sameFrame(pos, offsetDelta);
/* 778:    */       } else {
/* 779:920 */         super.sameLocals(pos, offsetDelta, stackTag, stackData);
/* 780:    */       }
/* 781:    */     }
/* 782:    */     
/* 783:    */     public void fullFrame(int pos, int offsetDelta, int[] localTags, int[] localData, int[] stackTags, int[] stackData)
/* 784:    */     {
/* 785:925 */       int n = stackTags.length - 1;
/* 786:926 */       for (int i = 0; i < n; i++) {
/* 787:927 */         if ((stackTags[i] == 8) && (stackData[i] == this.posOfNew) && (stackTags[(i + 1)] == 8) && (stackData[(i + 1)] == this.posOfNew))
/* 788:    */         {
/* 789:929 */           n++;
/* 790:930 */           int[] stackTags2 = new int[n - 2];
/* 791:931 */           int[] stackData2 = new int[n - 2];
/* 792:932 */           int k = 0;
/* 793:933 */           for (int j = 0; j < n; j++) {
/* 794:934 */             if (j == i)
/* 795:    */             {
/* 796:935 */               j++;
/* 797:    */             }
/* 798:    */             else
/* 799:    */             {
/* 800:937 */               stackTags2[k] = stackTags[j];
/* 801:938 */               stackData2[(k++)] = stackData[j];
/* 802:    */             }
/* 803:    */           }
/* 804:941 */           stackTags = stackTags2;
/* 805:942 */           stackData = stackData2;
/* 806:943 */           break;
/* 807:    */         }
/* 808:    */       }
/* 809:946 */       super.fullFrame(pos, offsetDelta, localTags, localData, stackTags, stackData);
/* 810:    */     }
/* 811:    */   }
/* 812:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.StackMapTable
 * JD-Core Version:    0.7.0.1
 */