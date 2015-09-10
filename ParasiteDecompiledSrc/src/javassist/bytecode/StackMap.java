/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.util.Map;
/*   8:    */ import javassist.CannotCompileException;
/*   9:    */ 
/*  10:    */ public class StackMap
/*  11:    */   extends AttributeInfo
/*  12:    */ {
/*  13:    */   public static final String tag = "StackMap";
/*  14:    */   public static final int TOP = 0;
/*  15:    */   public static final int INTEGER = 1;
/*  16:    */   public static final int FLOAT = 2;
/*  17:    */   public static final int DOUBLE = 3;
/*  18:    */   public static final int LONG = 4;
/*  19:    */   public static final int NULL = 5;
/*  20:    */   public static final int THIS = 6;
/*  21:    */   public static final int OBJECT = 7;
/*  22:    */   public static final int UNINIT = 8;
/*  23:    */   
/*  24:    */   StackMap(ConstPool cp, byte[] newInfo)
/*  25:    */   {
/*  26: 54 */     super(cp, "StackMap", newInfo);
/*  27:    */   }
/*  28:    */   
/*  29:    */   StackMap(ConstPool cp, int name_id, DataInputStream in)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 60 */     super(cp, name_id, in);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int numOfEntries()
/*  36:    */   {
/*  37: 67 */     return ByteArray.readU16bit(this.info, 0);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  41:    */   {
/*  42:119 */     Copier copier = new Copier(this, newCp, classnames);
/*  43:120 */     copier.visit();
/*  44:121 */     return copier.getStackMap();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static class Walker
/*  48:    */   {
/*  49:    */     byte[] info;
/*  50:    */     
/*  51:    */     public Walker(StackMap sm)
/*  52:    */     {
/*  53:134 */       this.info = sm.get();
/*  54:    */     }
/*  55:    */     
/*  56:    */     public void visit()
/*  57:    */     {
/*  58:141 */       int num = ByteArray.readU16bit(this.info, 0);
/*  59:142 */       int pos = 2;
/*  60:143 */       for (int i = 0; i < num; i++)
/*  61:    */       {
/*  62:144 */         int offset = ByteArray.readU16bit(this.info, pos);
/*  63:145 */         int numLoc = ByteArray.readU16bit(this.info, pos + 2);
/*  64:146 */         pos = locals(pos + 4, offset, numLoc);
/*  65:147 */         int numStack = ByteArray.readU16bit(this.info, pos);
/*  66:148 */         pos = stack(pos + 2, offset, numStack);
/*  67:    */       }
/*  68:    */     }
/*  69:    */     
/*  70:    */     public int locals(int pos, int offset, int num)
/*  71:    */     {
/*  72:157 */       return typeInfoArray(pos, offset, num, true);
/*  73:    */     }
/*  74:    */     
/*  75:    */     public int stack(int pos, int offset, int num)
/*  76:    */     {
/*  77:165 */       return typeInfoArray(pos, offset, num, false);
/*  78:    */     }
/*  79:    */     
/*  80:    */     public int typeInfoArray(int pos, int offset, int num, boolean isLocals)
/*  81:    */     {
/*  82:177 */       for (int k = 0; k < num; k++) {
/*  83:178 */         pos = typeInfoArray2(k, pos);
/*  84:    */       }
/*  85:180 */       return pos;
/*  86:    */     }
/*  87:    */     
/*  88:    */     int typeInfoArray2(int k, int pos)
/*  89:    */     {
/*  90:184 */       byte tag = this.info[pos];
/*  91:185 */       if (tag == 7)
/*  92:    */       {
/*  93:186 */         int clazz = ByteArray.readU16bit(this.info, pos + 1);
/*  94:187 */         objectVariable(pos, clazz);
/*  95:188 */         pos += 3;
/*  96:    */       }
/*  97:190 */       else if (tag == 8)
/*  98:    */       {
/*  99:191 */         int offsetOfNew = ByteArray.readU16bit(this.info, pos + 1);
/* 100:192 */         uninitialized(pos, offsetOfNew);
/* 101:193 */         pos += 3;
/* 102:    */       }
/* 103:    */       else
/* 104:    */       {
/* 105:196 */         typeInfo(pos, tag);
/* 106:197 */         pos++;
/* 107:    */       }
/* 108:200 */       return pos;
/* 109:    */     }
/* 110:    */     
/* 111:    */     public void typeInfo(int pos, byte tag) {}
/* 112:    */     
/* 113:    */     public void objectVariable(int pos, int clazz) {}
/* 114:    */     
/* 115:    */     public void uninitialized(int pos, int offset) {}
/* 116:    */   }
/* 117:    */   
/* 118:    */   static class Copier
/* 119:    */     extends StackMap.Walker
/* 120:    */   {
/* 121:    */     byte[] dest;
/* 122:    */     ConstPool srcCp;
/* 123:    */     ConstPool destCp;
/* 124:    */     Map classnames;
/* 125:    */     
/* 126:    */     Copier(StackMap map, ConstPool newCp, Map classnames)
/* 127:    */     {
/* 128:229 */       super();
/* 129:230 */       this.srcCp = map.getConstPool();
/* 130:231 */       this.dest = new byte[this.info.length];
/* 131:232 */       this.destCp = newCp;
/* 132:233 */       this.classnames = classnames;
/* 133:    */     }
/* 134:    */     
/* 135:    */     public void visit()
/* 136:    */     {
/* 137:237 */       int num = ByteArray.readU16bit(this.info, 0);
/* 138:238 */       ByteArray.write16bit(num, this.dest, 0);
/* 139:239 */       super.visit();
/* 140:    */     }
/* 141:    */     
/* 142:    */     public int locals(int pos, int offset, int num)
/* 143:    */     {
/* 144:243 */       ByteArray.write16bit(offset, this.dest, pos - 4);
/* 145:244 */       return super.locals(pos, offset, num);
/* 146:    */     }
/* 147:    */     
/* 148:    */     public int typeInfoArray(int pos, int offset, int num, boolean isLocals)
/* 149:    */     {
/* 150:248 */       ByteArray.write16bit(num, this.dest, pos - 2);
/* 151:249 */       return super.typeInfoArray(pos, offset, num, isLocals);
/* 152:    */     }
/* 153:    */     
/* 154:    */     public void typeInfo(int pos, byte tag)
/* 155:    */     {
/* 156:253 */       this.dest[pos] = tag;
/* 157:    */     }
/* 158:    */     
/* 159:    */     public void objectVariable(int pos, int clazz)
/* 160:    */     {
/* 161:257 */       this.dest[pos] = 7;
/* 162:258 */       int newClazz = this.srcCp.copy(clazz, this.destCp, this.classnames);
/* 163:259 */       ByteArray.write16bit(newClazz, this.dest, pos + 1);
/* 164:    */     }
/* 165:    */     
/* 166:    */     public void uninitialized(int pos, int offset)
/* 167:    */     {
/* 168:263 */       this.dest[pos] = 8;
/* 169:264 */       ByteArray.write16bit(offset, this.dest, pos + 1);
/* 170:    */     }
/* 171:    */     
/* 172:    */     public StackMap getStackMap()
/* 173:    */     {
/* 174:268 */       return new StackMap(this.destCp, this.dest);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void insertLocal(int index, int tag, int classInfo)
/* 179:    */     throws BadBytecode
/* 180:    */   {
/* 181:290 */     byte[] data = new InsertLocal(this, index, tag, classInfo).doit();
/* 182:291 */     set(data);
/* 183:    */   }
/* 184:    */   
/* 185:    */   static class SimpleCopy
/* 186:    */     extends StackMap.Walker
/* 187:    */   {
/* 188:    */     StackMap.Writer writer;
/* 189:    */     
/* 190:    */     SimpleCopy(StackMap map)
/* 191:    */     {
/* 192:298 */       super();
/* 193:299 */       this.writer = new StackMap.Writer();
/* 194:    */     }
/* 195:    */     
/* 196:    */     byte[] doit()
/* 197:    */     {
/* 198:303 */       visit();
/* 199:304 */       return this.writer.toByteArray();
/* 200:    */     }
/* 201:    */     
/* 202:    */     public void visit()
/* 203:    */     {
/* 204:308 */       int num = ByteArray.readU16bit(this.info, 0);
/* 205:309 */       this.writer.write16bit(num);
/* 206:310 */       super.visit();
/* 207:    */     }
/* 208:    */     
/* 209:    */     public int locals(int pos, int offset, int num)
/* 210:    */     {
/* 211:314 */       this.writer.write16bit(offset);
/* 212:315 */       return super.locals(pos, offset, num);
/* 213:    */     }
/* 214:    */     
/* 215:    */     public int typeInfoArray(int pos, int offset, int num, boolean isLocals)
/* 216:    */     {
/* 217:319 */       this.writer.write16bit(num);
/* 218:320 */       return super.typeInfoArray(pos, offset, num, isLocals);
/* 219:    */     }
/* 220:    */     
/* 221:    */     public void typeInfo(int pos, byte tag)
/* 222:    */     {
/* 223:324 */       this.writer.writeVerifyTypeInfo(tag, 0);
/* 224:    */     }
/* 225:    */     
/* 226:    */     public void objectVariable(int pos, int clazz)
/* 227:    */     {
/* 228:328 */       this.writer.writeVerifyTypeInfo(7, clazz);
/* 229:    */     }
/* 230:    */     
/* 231:    */     public void uninitialized(int pos, int offset)
/* 232:    */     {
/* 233:332 */       this.writer.writeVerifyTypeInfo(8, offset);
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   static class InsertLocal
/* 238:    */     extends StackMap.SimpleCopy
/* 239:    */   {
/* 240:    */     private int varIndex;
/* 241:    */     private int varTag;
/* 242:    */     private int varData;
/* 243:    */     
/* 244:    */     InsertLocal(StackMap map, int varIndex, int varTag, int varData)
/* 245:    */     {
/* 246:341 */       super();
/* 247:342 */       this.varIndex = varIndex;
/* 248:343 */       this.varTag = varTag;
/* 249:344 */       this.varData = varData;
/* 250:    */     }
/* 251:    */     
/* 252:    */     public int typeInfoArray(int pos, int offset, int num, boolean isLocals)
/* 253:    */     {
/* 254:348 */       if ((!isLocals) || (num < this.varIndex)) {
/* 255:349 */         return super.typeInfoArray(pos, offset, num, isLocals);
/* 256:    */       }
/* 257:351 */       this.writer.write16bit(num + 1);
/* 258:352 */       for (int k = 0; k < num; k++)
/* 259:    */       {
/* 260:353 */         if (k == this.varIndex) {
/* 261:354 */           writeVarTypeInfo();
/* 262:    */         }
/* 263:356 */         pos = typeInfoArray2(k, pos);
/* 264:    */       }
/* 265:359 */       if (num == this.varIndex) {
/* 266:360 */         writeVarTypeInfo();
/* 267:    */       }
/* 268:362 */       return pos;
/* 269:    */     }
/* 270:    */     
/* 271:    */     private void writeVarTypeInfo()
/* 272:    */     {
/* 273:366 */       if (this.varTag == 7) {
/* 274:367 */         this.writer.writeVerifyTypeInfo(7, this.varData);
/* 275:368 */       } else if (this.varTag == 8) {
/* 276:369 */         this.writer.writeVerifyTypeInfo(8, this.varData);
/* 277:    */       } else {
/* 278:371 */         this.writer.writeVerifyTypeInfo(this.varTag, 0);
/* 279:    */       }
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   void shiftPc(int where, int gapSize, boolean exclusive)
/* 284:    */     throws BadBytecode
/* 285:    */   {
/* 286:378 */     new Shifter(this, where, gapSize, exclusive).visit();
/* 287:    */   }
/* 288:    */   
/* 289:    */   static class Shifter
/* 290:    */     extends StackMap.Walker
/* 291:    */   {
/* 292:    */     private int where;
/* 293:    */     private int gap;
/* 294:    */     private boolean exclusive;
/* 295:    */     
/* 296:    */     public Shifter(StackMap smt, int where, int gap, boolean exclusive)
/* 297:    */     {
/* 298:386 */       super();
/* 299:387 */       this.where = where;
/* 300:388 */       this.gap = gap;
/* 301:389 */       this.exclusive = exclusive;
/* 302:    */     }
/* 303:    */     
/* 304:    */     public int locals(int pos, int offset, int num)
/* 305:    */     {
/* 306:393 */       if (this.exclusive ? this.where <= offset : this.where < offset) {
/* 307:394 */         ByteArray.write16bit(offset + this.gap, this.info, pos - 4);
/* 308:    */       }
/* 309:396 */       return super.locals(pos, offset, num);
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void removeNew(int where)
/* 314:    */     throws CannotCompileException
/* 315:    */   {
/* 316:410 */     byte[] data = new NewRemover(this, where).doit();
/* 317:411 */     set(data);
/* 318:    */   }
/* 319:    */   
/* 320:    */   static class NewRemover
/* 321:    */     extends StackMap.SimpleCopy
/* 322:    */   {
/* 323:    */     int posOfNew;
/* 324:    */     
/* 325:    */     NewRemover(StackMap map, int where)
/* 326:    */     {
/* 327:418 */       super();
/* 328:419 */       this.posOfNew = where;
/* 329:    */     }
/* 330:    */     
/* 331:    */     public int stack(int pos, int offset, int num)
/* 332:    */     {
/* 333:423 */       return stackTypeInfoArray(pos, offset, num);
/* 334:    */     }
/* 335:    */     
/* 336:    */     private int stackTypeInfoArray(int pos, int offset, int num)
/* 337:    */     {
/* 338:427 */       int p = pos;
/* 339:428 */       int count = 0;
/* 340:429 */       for (int k = 0; k < num; k++)
/* 341:    */       {
/* 342:430 */         byte tag = this.info[p];
/* 343:431 */         if (tag == 7)
/* 344:    */         {
/* 345:432 */           p += 3;
/* 346:    */         }
/* 347:433 */         else if (tag == 8)
/* 348:    */         {
/* 349:434 */           int offsetOfNew = ByteArray.readU16bit(this.info, p + 1);
/* 350:435 */           if (offsetOfNew == this.posOfNew) {
/* 351:436 */             count++;
/* 352:    */           }
/* 353:438 */           p += 3;
/* 354:    */         }
/* 355:    */         else
/* 356:    */         {
/* 357:441 */           p++;
/* 358:    */         }
/* 359:    */       }
/* 360:444 */       this.writer.write16bit(num - count);
/* 361:445 */       for (int k = 0; k < num; k++)
/* 362:    */       {
/* 363:446 */         byte tag = this.info[pos];
/* 364:447 */         if (tag == 7)
/* 365:    */         {
/* 366:448 */           int clazz = ByteArray.readU16bit(this.info, pos + 1);
/* 367:449 */           objectVariable(pos, clazz);
/* 368:450 */           pos += 3;
/* 369:    */         }
/* 370:452 */         else if (tag == 8)
/* 371:    */         {
/* 372:453 */           int offsetOfNew = ByteArray.readU16bit(this.info, pos + 1);
/* 373:454 */           if (offsetOfNew != this.posOfNew) {
/* 374:455 */             uninitialized(pos, offsetOfNew);
/* 375:    */           }
/* 376:457 */           pos += 3;
/* 377:    */         }
/* 378:    */         else
/* 379:    */         {
/* 380:460 */           typeInfo(pos, tag);
/* 381:461 */           pos++;
/* 382:    */         }
/* 383:    */       }
/* 384:465 */       return pos;
/* 385:    */     }
/* 386:    */   }
/* 387:    */   
/* 388:    */   public void print(PrintWriter out)
/* 389:    */   {
/* 390:473 */     new Printer(this, out).print();
/* 391:    */   }
/* 392:    */   
/* 393:    */   static class Printer
/* 394:    */     extends StackMap.Walker
/* 395:    */   {
/* 396:    */     private PrintWriter writer;
/* 397:    */     
/* 398:    */     public Printer(StackMap map, PrintWriter out)
/* 399:    */     {
/* 400:480 */       super();
/* 401:481 */       this.writer = out;
/* 402:    */     }
/* 403:    */     
/* 404:    */     public void print()
/* 405:    */     {
/* 406:485 */       int num = ByteArray.readU16bit(this.info, 0);
/* 407:486 */       this.writer.println(num + " entries");
/* 408:487 */       visit();
/* 409:    */     }
/* 410:    */     
/* 411:    */     public int locals(int pos, int offset, int num)
/* 412:    */     {
/* 413:491 */       this.writer.println("  * offset " + offset);
/* 414:492 */       return super.locals(pos, offset, num);
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   public static class Writer
/* 419:    */   {
/* 420:    */     private ByteArrayOutputStream output;
/* 421:    */     
/* 422:    */     public Writer()
/* 423:    */     {
/* 424:508 */       this.output = new ByteArrayOutputStream();
/* 425:    */     }
/* 426:    */     
/* 427:    */     public byte[] toByteArray()
/* 428:    */     {
/* 429:515 */       return this.output.toByteArray();
/* 430:    */     }
/* 431:    */     
/* 432:    */     public StackMap toStackMap(ConstPool cp)
/* 433:    */     {
/* 434:522 */       return new StackMap(cp, this.output.toByteArray());
/* 435:    */     }
/* 436:    */     
/* 437:    */     public void writeVerifyTypeInfo(int tag, int data)
/* 438:    */     {
/* 439:531 */       this.output.write(tag);
/* 440:532 */       if ((tag == 7) || (tag == 8)) {
/* 441:533 */         write16bit(data);
/* 442:    */       }
/* 443:    */     }
/* 444:    */     
/* 445:    */     public void write16bit(int value)
/* 446:    */     {
/* 447:540 */       this.output.write(value >>> 8 & 0xFF);
/* 448:541 */       this.output.write(value & 0xFF);
/* 449:    */     }
/* 450:    */   }
/* 451:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.StackMap
 * JD-Core Version:    0.7.0.1
 */