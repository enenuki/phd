/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ 
/*   7:    */ public class ClassFileWriter
/*   8:    */ {
/*   9:    */   private ByteStream output;
/*  10:    */   private ConstPoolWriter constPool;
/*  11:    */   private FieldWriter fields;
/*  12:    */   private MethodWriter methods;
/*  13:    */   int thisClass;
/*  14:    */   int superClass;
/*  15:    */   
/*  16:    */   public ClassFileWriter(int major, int minor)
/*  17:    */   {
/*  18: 89 */     this.output = new ByteStream(512);
/*  19: 90 */     this.output.writeInt(-889275714);
/*  20: 91 */     this.output.writeShort(minor);
/*  21: 92 */     this.output.writeShort(major);
/*  22: 93 */     this.constPool = new ConstPoolWriter(this.output);
/*  23: 94 */     this.fields = new FieldWriter(this.constPool);
/*  24: 95 */     this.methods = new MethodWriter(this.constPool);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ConstPoolWriter getConstPool()
/*  28:    */   {
/*  29:102 */     return this.constPool;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public FieldWriter getFieldWriter()
/*  33:    */   {
/*  34:107 */     return this.fields;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public MethodWriter getMethodWriter()
/*  38:    */   {
/*  39:112 */     return this.methods;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public byte[] end(int accessFlags, int thisClass, int superClass, int[] interfaces, AttributeWriter aw)
/*  43:    */   {
/*  44:129 */     this.constPool.end();
/*  45:130 */     this.output.writeShort(accessFlags);
/*  46:131 */     this.output.writeShort(thisClass);
/*  47:132 */     this.output.writeShort(superClass);
/*  48:133 */     if (interfaces == null)
/*  49:    */     {
/*  50:134 */       this.output.writeShort(0);
/*  51:    */     }
/*  52:    */     else
/*  53:    */     {
/*  54:136 */       int n = interfaces.length;
/*  55:137 */       this.output.writeShort(n);
/*  56:138 */       for (int i = 0; i < n; i++) {
/*  57:139 */         this.output.writeShort(interfaces[i]);
/*  58:    */       }
/*  59:    */     }
/*  60:142 */     this.output.enlarge(this.fields.dataSize() + this.methods.dataSize() + 6);
/*  61:    */     try
/*  62:    */     {
/*  63:144 */       this.output.writeShort(this.fields.size());
/*  64:145 */       this.fields.write(this.output);
/*  65:    */       
/*  66:147 */       this.output.writeShort(this.methods.size());
/*  67:148 */       this.methods.write(this.output);
/*  68:    */     }
/*  69:    */     catch (IOException e) {}
/*  70:152 */     writeAttribute(this.output, aw, 0);
/*  71:153 */     return this.output.toByteArray();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void end(DataOutputStream out, int accessFlags, int thisClass, int superClass, int[] interfaces, AttributeWriter aw)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:175 */     this.constPool.end();
/*  78:176 */     this.output.writeTo(out);
/*  79:177 */     out.writeShort(accessFlags);
/*  80:178 */     out.writeShort(thisClass);
/*  81:179 */     out.writeShort(superClass);
/*  82:180 */     if (interfaces == null)
/*  83:    */     {
/*  84:181 */       out.writeShort(0);
/*  85:    */     }
/*  86:    */     else
/*  87:    */     {
/*  88:183 */       int n = interfaces.length;
/*  89:184 */       out.writeShort(n);
/*  90:185 */       for (int i = 0; i < n; i++) {
/*  91:186 */         out.writeShort(interfaces[i]);
/*  92:    */       }
/*  93:    */     }
/*  94:189 */     out.writeShort(this.fields.size());
/*  95:190 */     this.fields.write(out);
/*  96:    */     
/*  97:192 */     out.writeShort(this.methods.size());
/*  98:193 */     this.methods.write(out);
/*  99:194 */     if (aw == null)
/* 100:    */     {
/* 101:195 */       out.writeShort(0);
/* 102:    */     }
/* 103:    */     else
/* 104:    */     {
/* 105:197 */       out.writeShort(aw.size());
/* 106:198 */       aw.write(out);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   static void writeAttribute(ByteStream bs, AttributeWriter aw, int attrCount)
/* 111:    */   {
/* 112:236 */     if (aw == null)
/* 113:    */     {
/* 114:237 */       bs.writeShort(attrCount);
/* 115:238 */       return;
/* 116:    */     }
/* 117:241 */     bs.writeShort(aw.size() + attrCount);
/* 118:242 */     DataOutputStream dos = new DataOutputStream(bs);
/* 119:    */     try
/* 120:    */     {
/* 121:244 */       aw.write(dos);
/* 122:245 */       dos.flush();
/* 123:    */     }
/* 124:    */     catch (IOException e) {}
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static abstract interface AttributeWriter
/* 128:    */   {
/* 129:    */     public abstract int size();
/* 130:    */     
/* 131:    */     public abstract void write(DataOutputStream paramDataOutputStream)
/* 132:    */       throws IOException;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static final class FieldWriter
/* 136:    */   {
/* 137:    */     protected ByteStream output;
/* 138:    */     protected ClassFileWriter.ConstPoolWriter constPool;
/* 139:    */     private int fieldCount;
/* 140:    */     
/* 141:    */     FieldWriter(ClassFileWriter.ConstPoolWriter cp)
/* 142:    */     {
/* 143:259 */       this.output = new ByteStream(128);
/* 144:260 */       this.constPool = cp;
/* 145:261 */       this.fieldCount = 0;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public void add(int accessFlags, String name, String descriptor, ClassFileWriter.AttributeWriter aw)
/* 149:    */     {
/* 150:274 */       int nameIndex = this.constPool.addUtf8Info(name);
/* 151:275 */       int descIndex = this.constPool.addUtf8Info(descriptor);
/* 152:276 */       add(accessFlags, nameIndex, descIndex, aw);
/* 153:    */     }
/* 154:    */     
/* 155:    */     public void add(int accessFlags, int name, int descriptor, ClassFileWriter.AttributeWriter aw)
/* 156:    */     {
/* 157:289 */       this.fieldCount += 1;
/* 158:290 */       this.output.writeShort(accessFlags);
/* 159:291 */       this.output.writeShort(name);
/* 160:292 */       this.output.writeShort(descriptor);
/* 161:293 */       ClassFileWriter.writeAttribute(this.output, aw, 0);
/* 162:    */     }
/* 163:    */     
/* 164:    */     int size()
/* 165:    */     {
/* 166:296 */       return this.fieldCount;
/* 167:    */     }
/* 168:    */     
/* 169:    */     int dataSize()
/* 170:    */     {
/* 171:298 */       return this.output.size();
/* 172:    */     }
/* 173:    */     
/* 174:    */     void write(OutputStream out)
/* 175:    */       throws IOException
/* 176:    */     {
/* 177:304 */       this.output.writeTo(out);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static final class MethodWriter
/* 182:    */   {
/* 183:    */     protected ByteStream output;
/* 184:    */     protected ClassFileWriter.ConstPoolWriter constPool;
/* 185:    */     private int methodCount;
/* 186:    */     protected int codeIndex;
/* 187:    */     protected int throwsIndex;
/* 188:    */     protected int stackIndex;
/* 189:    */     private int startPos;
/* 190:    */     private boolean isAbstract;
/* 191:    */     private int catchPos;
/* 192:    */     private int catchCount;
/* 193:    */     
/* 194:    */     MethodWriter(ClassFileWriter.ConstPoolWriter cp)
/* 195:    */     {
/* 196:325 */       this.output = new ByteStream(256);
/* 197:326 */       this.constPool = cp;
/* 198:327 */       this.methodCount = 0;
/* 199:328 */       this.codeIndex = 0;
/* 200:329 */       this.throwsIndex = 0;
/* 201:330 */       this.stackIndex = 0;
/* 202:    */     }
/* 203:    */     
/* 204:    */     public void begin(int accessFlags, String name, String descriptor, String[] exceptions, ClassFileWriter.AttributeWriter aw)
/* 205:    */     {
/* 206:346 */       int nameIndex = this.constPool.addUtf8Info(name);
/* 207:347 */       int descIndex = this.constPool.addUtf8Info(descriptor);
/* 208:    */       int[] intfs;
/* 209:    */       int[] intfs;
/* 210:349 */       if (exceptions == null) {
/* 211:350 */         intfs = null;
/* 212:    */       } else {
/* 213:352 */         intfs = this.constPool.addClassInfo(exceptions);
/* 214:    */       }
/* 215:354 */       begin(accessFlags, nameIndex, descIndex, intfs, aw);
/* 216:    */     }
/* 217:    */     
/* 218:    */     public void begin(int accessFlags, int name, int descriptor, int[] exceptions, ClassFileWriter.AttributeWriter aw)
/* 219:    */     {
/* 220:368 */       this.methodCount += 1;
/* 221:369 */       this.output.writeShort(accessFlags);
/* 222:370 */       this.output.writeShort(name);
/* 223:371 */       this.output.writeShort(descriptor);
/* 224:372 */       this.isAbstract = ((accessFlags & 0x400) != 0);
/* 225:    */       
/* 226:374 */       int attrCount = this.isAbstract ? 0 : 1;
/* 227:375 */       if (exceptions != null) {
/* 228:376 */         attrCount++;
/* 229:    */       }
/* 230:378 */       ClassFileWriter.writeAttribute(this.output, aw, attrCount);
/* 231:380 */       if (exceptions != null) {
/* 232:381 */         writeThrows(exceptions);
/* 233:    */       }
/* 234:383 */       if (!this.isAbstract)
/* 235:    */       {
/* 236:384 */         if (this.codeIndex == 0) {
/* 237:385 */           this.codeIndex = this.constPool.addUtf8Info("Code");
/* 238:    */         }
/* 239:387 */         this.startPos = this.output.getPos();
/* 240:388 */         this.output.writeShort(this.codeIndex);
/* 241:389 */         this.output.writeBlank(12);
/* 242:    */       }
/* 243:392 */       this.catchPos = -1;
/* 244:393 */       this.catchCount = 0;
/* 245:    */     }
/* 246:    */     
/* 247:    */     private void writeThrows(int[] exceptions)
/* 248:    */     {
/* 249:397 */       if (this.throwsIndex == 0) {
/* 250:398 */         this.throwsIndex = this.constPool.addUtf8Info("Exceptions");
/* 251:    */       }
/* 252:400 */       this.output.writeShort(this.throwsIndex);
/* 253:401 */       this.output.writeInt(exceptions.length * 2 + 2);
/* 254:402 */       this.output.writeShort(exceptions.length);
/* 255:403 */       for (int i = 0; i < exceptions.length; i++) {
/* 256:404 */         this.output.writeShort(exceptions[i]);
/* 257:    */       }
/* 258:    */     }
/* 259:    */     
/* 260:    */     public void add(int b)
/* 261:    */     {
/* 262:413 */       this.output.write(b);
/* 263:    */     }
/* 264:    */     
/* 265:    */     public void add16(int b)
/* 266:    */     {
/* 267:420 */       this.output.writeShort(b);
/* 268:    */     }
/* 269:    */     
/* 270:    */     public void add32(int b)
/* 271:    */     {
/* 272:427 */       this.output.writeInt(b);
/* 273:    */     }
/* 274:    */     
/* 275:    */     public void addInvoke(int opcode, String targetClass, String methodName, String descriptor)
/* 276:    */     {
/* 277:437 */       int target = this.constPool.addClassInfo(targetClass);
/* 278:438 */       int nt = this.constPool.addNameAndTypeInfo(methodName, descriptor);
/* 279:439 */       int method = this.constPool.addMethodrefInfo(target, nt);
/* 280:440 */       add(opcode);
/* 281:441 */       add16(method);
/* 282:    */     }
/* 283:    */     
/* 284:    */     public void codeEnd(int maxStack, int maxLocals)
/* 285:    */     {
/* 286:448 */       if (!this.isAbstract)
/* 287:    */       {
/* 288:449 */         this.output.writeShort(this.startPos + 6, maxStack);
/* 289:450 */         this.output.writeShort(this.startPos + 8, maxLocals);
/* 290:451 */         this.output.writeInt(this.startPos + 10, this.output.getPos() - this.startPos - 14);
/* 291:452 */         this.catchPos = this.output.getPos();
/* 292:453 */         this.catchCount = 0;
/* 293:454 */         this.output.writeShort(0);
/* 294:    */       }
/* 295:    */     }
/* 296:    */     
/* 297:    */     public void addCatch(int startPc, int endPc, int handlerPc, int catchType)
/* 298:    */     {
/* 299:466 */       this.catchCount += 1;
/* 300:467 */       this.output.writeShort(startPc);
/* 301:468 */       this.output.writeShort(endPc);
/* 302:469 */       this.output.writeShort(handlerPc);
/* 303:470 */       this.output.writeShort(catchType);
/* 304:    */     }
/* 305:    */     
/* 306:    */     public void end(StackMapTable.Writer smap, ClassFileWriter.AttributeWriter aw)
/* 307:    */     {
/* 308:482 */       if (this.isAbstract) {
/* 309:483 */         return;
/* 310:    */       }
/* 311:486 */       this.output.writeShort(this.catchPos, this.catchCount);
/* 312:    */       
/* 313:488 */       int attrCount = smap == null ? 0 : 1;
/* 314:489 */       ClassFileWriter.writeAttribute(this.output, aw, attrCount);
/* 315:491 */       if (smap != null)
/* 316:    */       {
/* 317:492 */         if (this.stackIndex == 0) {
/* 318:493 */           this.stackIndex = this.constPool.addUtf8Info("StackMapTable");
/* 319:    */         }
/* 320:495 */         this.output.writeShort(this.stackIndex);
/* 321:496 */         byte[] data = smap.toByteArray();
/* 322:497 */         this.output.writeInt(data.length);
/* 323:498 */         this.output.write(data);
/* 324:    */       }
/* 325:502 */       this.output.writeInt(this.startPos + 2, this.output.getPos() - this.startPos - 6);
/* 326:    */     }
/* 327:    */     
/* 328:    */     int size()
/* 329:    */     {
/* 330:505 */       return this.methodCount;
/* 331:    */     }
/* 332:    */     
/* 333:    */     int dataSize()
/* 334:    */     {
/* 335:507 */       return this.output.size();
/* 336:    */     }
/* 337:    */     
/* 338:    */     void write(OutputStream out)
/* 339:    */       throws IOException
/* 340:    */     {
/* 341:513 */       this.output.writeTo(out);
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   public static final class ConstPoolWriter
/* 346:    */   {
/* 347:    */     ByteStream output;
/* 348:    */     protected int startPos;
/* 349:    */     protected int num;
/* 350:    */     
/* 351:    */     ConstPoolWriter(ByteStream out)
/* 352:    */     {
/* 353:526 */       this.output = out;
/* 354:527 */       this.startPos = out.getPos();
/* 355:528 */       this.num = 1;
/* 356:529 */       this.output.writeShort(1);
/* 357:    */     }
/* 358:    */     
/* 359:    */     public int[] addClassInfo(String[] classNames)
/* 360:    */     {
/* 361:538 */       int n = classNames.length;
/* 362:539 */       int[] result = new int[n];
/* 363:540 */       for (int i = 0; i < n; i++) {
/* 364:541 */         result[i] = addClassInfo(classNames[i]);
/* 365:    */       }
/* 366:543 */       return result;
/* 367:    */     }
/* 368:    */     
/* 369:    */     public int addClassInfo(String jvmname)
/* 370:    */     {
/* 371:557 */       int utf8 = addUtf8Info(jvmname);
/* 372:558 */       this.output.write(7);
/* 373:559 */       this.output.writeShort(utf8);
/* 374:560 */       return this.num++;
/* 375:    */     }
/* 376:    */     
/* 377:    */     public int addClassInfo(int name)
/* 378:    */     {
/* 379:570 */       this.output.write(7);
/* 380:571 */       this.output.writeShort(name);
/* 381:572 */       return this.num++;
/* 382:    */     }
/* 383:    */     
/* 384:    */     public int addNameAndTypeInfo(String name, String type)
/* 385:    */     {
/* 386:583 */       return addNameAndTypeInfo(addUtf8Info(name), addUtf8Info(type));
/* 387:    */     }
/* 388:    */     
/* 389:    */     public int addNameAndTypeInfo(int name, int type)
/* 390:    */     {
/* 391:594 */       this.output.write(12);
/* 392:595 */       this.output.writeShort(name);
/* 393:596 */       this.output.writeShort(type);
/* 394:597 */       return this.num++;
/* 395:    */     }
/* 396:    */     
/* 397:    */     public int addFieldrefInfo(int classInfo, int nameAndTypeInfo)
/* 398:    */     {
/* 399:608 */       this.output.write(9);
/* 400:609 */       this.output.writeShort(classInfo);
/* 401:610 */       this.output.writeShort(nameAndTypeInfo);
/* 402:611 */       return this.num++;
/* 403:    */     }
/* 404:    */     
/* 405:    */     public int addMethodrefInfo(int classInfo, int nameAndTypeInfo)
/* 406:    */     {
/* 407:622 */       this.output.write(10);
/* 408:623 */       this.output.writeShort(classInfo);
/* 409:624 */       this.output.writeShort(nameAndTypeInfo);
/* 410:625 */       return this.num++;
/* 411:    */     }
/* 412:    */     
/* 413:    */     public int addInterfaceMethodrefInfo(int classInfo, int nameAndTypeInfo)
/* 414:    */     {
/* 415:638 */       this.output.write(11);
/* 416:639 */       this.output.writeShort(classInfo);
/* 417:640 */       this.output.writeShort(nameAndTypeInfo);
/* 418:641 */       return this.num++;
/* 419:    */     }
/* 420:    */     
/* 421:    */     public int addStringInfo(String str)
/* 422:    */     {
/* 423:654 */       this.output.write(8);
/* 424:655 */       this.output.writeShort(addUtf8Info(str));
/* 425:656 */       return this.num++;
/* 426:    */     }
/* 427:    */     
/* 428:    */     public int addIntegerInfo(int i)
/* 429:    */     {
/* 430:666 */       this.output.write(3);
/* 431:667 */       this.output.writeInt(i);
/* 432:668 */       return this.num++;
/* 433:    */     }
/* 434:    */     
/* 435:    */     public int addFloatInfo(float f)
/* 436:    */     {
/* 437:678 */       this.output.write(4);
/* 438:679 */       this.output.writeFloat(f);
/* 439:680 */       return this.num++;
/* 440:    */     }
/* 441:    */     
/* 442:    */     public int addLongInfo(long l)
/* 443:    */     {
/* 444:690 */       this.output.write(5);
/* 445:691 */       this.output.writeLong(l);
/* 446:692 */       int n = this.num;
/* 447:693 */       this.num += 2;
/* 448:694 */       return n;
/* 449:    */     }
/* 450:    */     
/* 451:    */     public int addDoubleInfo(double d)
/* 452:    */     {
/* 453:704 */       this.output.write(6);
/* 454:705 */       this.output.writeDouble(d);
/* 455:706 */       int n = this.num;
/* 456:707 */       this.num += 2;
/* 457:708 */       return n;
/* 458:    */     }
/* 459:    */     
/* 460:    */     public int addUtf8Info(String utf8)
/* 461:    */     {
/* 462:718 */       this.output.write(1);
/* 463:719 */       this.output.writeUTF(utf8);
/* 464:720 */       return this.num++;
/* 465:    */     }
/* 466:    */     
/* 467:    */     void end()
/* 468:    */     {
/* 469:727 */       this.output.writeShort(this.startPos, this.num);
/* 470:    */     }
/* 471:    */   }
/* 472:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ClassFileWriter
 * JD-Core Version:    0.7.0.1
 */