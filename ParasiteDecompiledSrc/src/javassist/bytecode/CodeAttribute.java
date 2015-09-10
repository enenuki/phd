/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ 
/*  10:    */ public class CodeAttribute
/*  11:    */   extends AttributeInfo
/*  12:    */   implements Opcode
/*  13:    */ {
/*  14:    */   public static final String tag = "Code";
/*  15:    */   private int maxStack;
/*  16:    */   private int maxLocals;
/*  17:    */   private ExceptionTable exceptions;
/*  18:    */   private ArrayList attributes;
/*  19:    */   
/*  20:    */   public CodeAttribute(ConstPool cp, int stack, int locals, byte[] code, ExceptionTable etable)
/*  21:    */   {
/*  22: 60 */     super(cp, "Code");
/*  23: 61 */     this.maxStack = stack;
/*  24: 62 */     this.maxLocals = locals;
/*  25: 63 */     this.info = code;
/*  26: 64 */     this.exceptions = etable;
/*  27: 65 */     this.attributes = new ArrayList();
/*  28:    */   }
/*  29:    */   
/*  30:    */   private CodeAttribute(ConstPool cp, CodeAttribute src, Map classnames)
/*  31:    */     throws BadBytecode
/*  32:    */   {
/*  33: 80 */     super(cp, "Code");
/*  34:    */     
/*  35: 82 */     this.maxStack = src.getMaxStack();
/*  36: 83 */     this.maxLocals = src.getMaxLocals();
/*  37: 84 */     this.exceptions = src.getExceptionTable().copy(cp, classnames);
/*  38: 85 */     this.attributes = new ArrayList();
/*  39: 86 */     List src_attr = src.getAttributes();
/*  40: 87 */     int num = src_attr.size();
/*  41: 88 */     for (int i = 0; i < num; i++)
/*  42:    */     {
/*  43: 89 */       AttributeInfo ai = (AttributeInfo)src_attr.get(i);
/*  44: 90 */       this.attributes.add(ai.copy(cp, classnames));
/*  45:    */     }
/*  46: 93 */     this.info = src.copyCode(cp, classnames, this.exceptions, this);
/*  47:    */   }
/*  48:    */   
/*  49:    */   CodeAttribute(ConstPool cp, int name_id, DataInputStream in)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52: 99 */     super(cp, name_id, (byte[])null);
/*  53:100 */     int attr_len = in.readInt();
/*  54:    */     
/*  55:102 */     this.maxStack = in.readUnsignedShort();
/*  56:103 */     this.maxLocals = in.readUnsignedShort();
/*  57:    */     
/*  58:105 */     int code_len = in.readInt();
/*  59:106 */     this.info = new byte[code_len];
/*  60:107 */     in.readFully(this.info);
/*  61:    */     
/*  62:109 */     this.exceptions = new ExceptionTable(cp, in);
/*  63:    */     
/*  64:111 */     this.attributes = new ArrayList();
/*  65:112 */     int num = in.readUnsignedShort();
/*  66:113 */     for (int i = 0; i < num; i++) {
/*  67:114 */       this.attributes.add(AttributeInfo.read(cp, in));
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  72:    */     throws CodeAttribute.RuntimeCopyException
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:135 */       return new CodeAttribute(newCp, this, classnames);
/*  77:    */     }
/*  78:    */     catch (BadBytecode e)
/*  79:    */     {
/*  80:138 */       throw new RuntimeCopyException("bad bytecode. fatal?");
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static class RuntimeCopyException
/*  85:    */     extends RuntimeException
/*  86:    */   {
/*  87:    */     public RuntimeCopyException(String s)
/*  88:    */     {
/*  89:151 */       super();
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int length()
/*  94:    */   {
/*  95:161 */     return 18 + this.info.length + this.exceptions.size() * 8 + AttributeInfo.getLength(this.attributes);
/*  96:    */   }
/*  97:    */   
/*  98:    */   void write(DataOutputStream out)
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:166 */     out.writeShort(this.name);
/* 102:167 */     out.writeInt(length() - 6);
/* 103:168 */     out.writeShort(this.maxStack);
/* 104:169 */     out.writeShort(this.maxLocals);
/* 105:170 */     out.writeInt(this.info.length);
/* 106:171 */     out.write(this.info);
/* 107:172 */     this.exceptions.write(out);
/* 108:173 */     out.writeShort(this.attributes.size());
/* 109:174 */     AttributeInfo.writeAll(this.attributes, out);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public byte[] get()
/* 113:    */   {
/* 114:183 */     throw new UnsupportedOperationException("CodeAttribute.get()");
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void set(byte[] newinfo)
/* 118:    */   {
/* 119:192 */     throw new UnsupportedOperationException("CodeAttribute.set()");
/* 120:    */   }
/* 121:    */   
/* 122:    */   void renameClass(String oldname, String newname)
/* 123:    */   {
/* 124:196 */     AttributeInfo.renameClass(this.attributes, oldname, newname);
/* 125:    */   }
/* 126:    */   
/* 127:    */   void renameClass(Map classnames)
/* 128:    */   {
/* 129:200 */     AttributeInfo.renameClass(this.attributes, classnames);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getDeclaringClass()
/* 133:    */   {
/* 134:208 */     ConstPool cp = getConstPool();
/* 135:209 */     return cp.getClassName();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getMaxStack()
/* 139:    */   {
/* 140:216 */     return this.maxStack;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setMaxStack(int value)
/* 144:    */   {
/* 145:223 */     this.maxStack = value;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int computeMaxStack()
/* 149:    */     throws BadBytecode
/* 150:    */   {
/* 151:234 */     this.maxStack = new CodeAnalyzer(this).computeMaxStack();
/* 152:235 */     return this.maxStack;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getMaxLocals()
/* 156:    */   {
/* 157:242 */     return this.maxLocals;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setMaxLocals(int value)
/* 161:    */   {
/* 162:249 */     this.maxLocals = value;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getCodeLength()
/* 166:    */   {
/* 167:256 */     return this.info.length;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public byte[] getCode()
/* 171:    */   {
/* 172:263 */     return this.info;
/* 173:    */   }
/* 174:    */   
/* 175:    */   void setCode(byte[] newinfo)
/* 176:    */   {
/* 177:269 */     super.set(newinfo);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public CodeIterator iterator()
/* 181:    */   {
/* 182:275 */     return new CodeIterator(this);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public ExceptionTable getExceptionTable()
/* 186:    */   {
/* 187:281 */     return this.exceptions;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List getAttributes()
/* 191:    */   {
/* 192:291 */     return this.attributes;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public AttributeInfo getAttribute(String name)
/* 196:    */   {
/* 197:301 */     return AttributeInfo.lookup(this.attributes, name);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setAttribute(StackMapTable smt)
/* 201:    */   {
/* 202:313 */     AttributeInfo.remove(this.attributes, "StackMapTable");
/* 203:314 */     if (smt != null) {
/* 204:315 */       this.attributes.add(smt);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setAttribute(StackMap sm)
/* 209:    */   {
/* 210:328 */     AttributeInfo.remove(this.attributes, "StackMap");
/* 211:329 */     if (sm != null) {
/* 212:330 */       this.attributes.add(sm);
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   private byte[] copyCode(ConstPool destCp, Map classnames, ExceptionTable etable, CodeAttribute destCa)
/* 217:    */     throws BadBytecode
/* 218:    */   {
/* 219:340 */     int len = getCodeLength();
/* 220:341 */     byte[] newCode = new byte[len];
/* 221:342 */     destCa.info = newCode;
/* 222:343 */     LdcEntry ldc = copyCode(this.info, 0, len, getConstPool(), newCode, destCp, classnames);
/* 223:    */     
/* 224:345 */     return LdcEntry.doit(newCode, ldc, etable, destCa);
/* 225:    */   }
/* 226:    */   
/* 227:    */   private static LdcEntry copyCode(byte[] code, int beginPos, int endPos, ConstPool srcCp, byte[] newcode, ConstPool destCp, Map classnameMap)
/* 228:    */     throws BadBytecode
/* 229:    */   {
/* 230:354 */     LdcEntry ldcEntry = null;
/* 231:    */     int i2;
/* 232:356 */     for (int i = beginPos; i < endPos; i = i2)
/* 233:    */     {
/* 234:357 */       i2 = CodeIterator.nextOpcode(code, i);
/* 235:358 */       byte c = code[i];
/* 236:359 */       newcode[i] = c;
/* 237:360 */       switch (c & 0xFF)
/* 238:    */       {
/* 239:    */       case 19: 
/* 240:    */       case 20: 
/* 241:    */       case 178: 
/* 242:    */       case 179: 
/* 243:    */       case 180: 
/* 244:    */       case 181: 
/* 245:    */       case 182: 
/* 246:    */       case 183: 
/* 247:    */       case 184: 
/* 248:    */       case 187: 
/* 249:    */       case 189: 
/* 250:    */       case 192: 
/* 251:    */       case 193: 
/* 252:374 */         copyConstPoolInfo(i + 1, code, srcCp, newcode, destCp, classnameMap);
/* 253:    */         
/* 254:376 */         break;
/* 255:    */       case 18: 
/* 256:378 */         int index = code[(i + 1)] & 0xFF;
/* 257:379 */         index = srcCp.copy(index, destCp, classnameMap);
/* 258:380 */         if (index < 256)
/* 259:    */         {
/* 260:381 */           newcode[(i + 1)] = ((byte)index);
/* 261:    */         }
/* 262:    */         else
/* 263:    */         {
/* 264:383 */           newcode[i] = 0;
/* 265:384 */           newcode[(i + 1)] = 0;
/* 266:385 */           LdcEntry ldc = new LdcEntry();
/* 267:386 */           ldc.where = i;
/* 268:387 */           ldc.index = index;
/* 269:388 */           ldc.next = ldcEntry;
/* 270:389 */           ldcEntry = ldc;
/* 271:    */         }
/* 272:391 */         break;
/* 273:    */       case 185: 
/* 274:393 */         copyConstPoolInfo(i + 1, code, srcCp, newcode, destCp, classnameMap);
/* 275:    */         
/* 276:395 */         newcode[(i + 3)] = code[(i + 3)];
/* 277:396 */         newcode[(i + 4)] = code[(i + 4)];
/* 278:397 */         break;
/* 279:    */       case 197: 
/* 280:399 */         copyConstPoolInfo(i + 1, code, srcCp, newcode, destCp, classnameMap);
/* 281:    */         
/* 282:401 */         newcode[(i + 3)] = code[(i + 3)];
/* 283:402 */         break;
/* 284:    */       default: 
/* 285:    */         for (;;)
/* 286:    */         {
/* 287:404 */           i++;
/* 288:404 */           if (i >= i2) {
/* 289:    */             break;
/* 290:    */           }
/* 291:405 */           newcode[i] = code[i];
/* 292:    */         }
/* 293:    */       }
/* 294:    */     }
/* 295:411 */     return ldcEntry;
/* 296:    */   }
/* 297:    */   
/* 298:    */   private static void copyConstPoolInfo(int i, byte[] code, ConstPool srcCp, byte[] newcode, ConstPool destCp, Map classnameMap)
/* 299:    */   {
/* 300:417 */     int index = (code[i] & 0xFF) << 8 | code[(i + 1)] & 0xFF;
/* 301:418 */     index = srcCp.copy(index, destCp, classnameMap);
/* 302:419 */     newcode[i] = ((byte)(index >> 8));
/* 303:420 */     newcode[(i + 1)] = ((byte)index);
/* 304:    */   }
/* 305:    */   
/* 306:    */   static class LdcEntry
/* 307:    */   {
/* 308:    */     LdcEntry next;
/* 309:    */     int where;
/* 310:    */     int index;
/* 311:    */     
/* 312:    */     static byte[] doit(byte[] code, LdcEntry ldc, ExceptionTable etable, CodeAttribute ca)
/* 313:    */       throws BadBytecode
/* 314:    */     {
/* 315:432 */       if (ldc != null) {
/* 316:433 */         code = CodeIterator.changeLdcToLdcW(code, etable, ca, ldc);
/* 317:    */       }
/* 318:448 */       return code;
/* 319:    */     }
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void insertLocalVar(int where, int size)
/* 323:    */     throws BadBytecode
/* 324:    */   {
/* 325:467 */     CodeIterator ci = iterator();
/* 326:468 */     while (ci.hasNext()) {
/* 327:469 */       shiftIndex(ci, where, size);
/* 328:    */     }
/* 329:471 */     setMaxLocals(getMaxLocals() + size);
/* 330:    */   }
/* 331:    */   
/* 332:    */   private static void shiftIndex(CodeIterator ci, int lessThan, int delta)
/* 333:    */     throws BadBytecode
/* 334:    */   {
/* 335:482 */     int index = ci.next();
/* 336:483 */     int opcode = ci.byteAt(index);
/* 337:484 */     if (opcode < 21) {
/* 338:485 */       return;
/* 339:    */     }
/* 340:486 */     if (opcode < 79)
/* 341:    */     {
/* 342:487 */       if (opcode < 26)
/* 343:    */       {
/* 344:489 */         shiftIndex8(ci, index, opcode, lessThan, delta);
/* 345:    */       }
/* 346:491 */       else if (opcode < 46)
/* 347:    */       {
/* 348:493 */         shiftIndex0(ci, index, opcode, lessThan, delta, 26, 21);
/* 349:    */       }
/* 350:    */       else
/* 351:    */       {
/* 352:495 */         if (opcode < 54) {
/* 353:496 */           return;
/* 354:    */         }
/* 355:497 */         if (opcode < 59) {
/* 356:499 */           shiftIndex8(ci, index, opcode, lessThan, delta);
/* 357:    */         } else {
/* 358:503 */           shiftIndex0(ci, index, opcode, lessThan, delta, 59, 54);
/* 359:    */         }
/* 360:    */       }
/* 361:    */     }
/* 362:506 */     else if (opcode == 132)
/* 363:    */     {
/* 364:507 */       int var = ci.byteAt(index + 1);
/* 365:508 */       if (var < lessThan) {
/* 366:509 */         return;
/* 367:    */       }
/* 368:511 */       var += delta;
/* 369:512 */       if (var < 256)
/* 370:    */       {
/* 371:513 */         ci.writeByte(var, index + 1);
/* 372:    */       }
/* 373:    */       else
/* 374:    */       {
/* 375:515 */         int plus = (byte)ci.byteAt(index + 2);
/* 376:516 */         int pos = ci.insertExGap(3);
/* 377:517 */         ci.writeByte(196, pos - 3);
/* 378:518 */         ci.writeByte(132, pos - 2);
/* 379:519 */         ci.write16bit(var, pos - 1);
/* 380:520 */         ci.write16bit(plus, pos + 1);
/* 381:    */       }
/* 382:    */     }
/* 383:523 */     else if (opcode == 169)
/* 384:    */     {
/* 385:524 */       shiftIndex8(ci, index, opcode, lessThan, delta);
/* 386:    */     }
/* 387:525 */     else if (opcode == 196)
/* 388:    */     {
/* 389:526 */       int var = ci.u16bitAt(index + 2);
/* 390:527 */       if (var < lessThan) {
/* 391:528 */         return;
/* 392:    */       }
/* 393:530 */       var += delta;
/* 394:531 */       ci.write16bit(var, index + 2);
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   private static void shiftIndex8(CodeIterator ci, int index, int opcode, int lessThan, int delta)
/* 399:    */     throws BadBytecode
/* 400:    */   {
/* 401:539 */     int var = ci.byteAt(index + 1);
/* 402:540 */     if (var < lessThan) {
/* 403:541 */       return;
/* 404:    */     }
/* 405:543 */     var += delta;
/* 406:544 */     if (var < 256)
/* 407:    */     {
/* 408:545 */       ci.writeByte(var, index + 1);
/* 409:    */     }
/* 410:    */     else
/* 411:    */     {
/* 412:547 */       int pos = ci.insertExGap(2);
/* 413:548 */       ci.writeByte(196, pos - 2);
/* 414:549 */       ci.writeByte(opcode, pos - 1);
/* 415:550 */       ci.write16bit(var, pos);
/* 416:    */     }
/* 417:    */   }
/* 418:    */   
/* 419:    */   private static void shiftIndex0(CodeIterator ci, int index, int opcode, int lessThan, int delta, int opcode_i_0, int opcode_i)
/* 420:    */     throws BadBytecode
/* 421:    */   {
/* 422:559 */     int var = (opcode - opcode_i_0) % 4;
/* 423:560 */     if (var < lessThan) {
/* 424:561 */       return;
/* 425:    */     }
/* 426:563 */     var += delta;
/* 427:564 */     if (var < 4)
/* 428:    */     {
/* 429:565 */       ci.writeByte(opcode + delta, index);
/* 430:    */     }
/* 431:    */     else
/* 432:    */     {
/* 433:567 */       opcode = (opcode - opcode_i_0) / 4 + opcode_i;
/* 434:568 */       if (var < 256)
/* 435:    */       {
/* 436:569 */         int pos = ci.insertExGap(1);
/* 437:570 */         ci.writeByte(opcode, pos - 1);
/* 438:571 */         ci.writeByte(var, pos);
/* 439:    */       }
/* 440:    */       else
/* 441:    */       {
/* 442:574 */         int pos = ci.insertExGap(3);
/* 443:575 */         ci.writeByte(196, pos - 1);
/* 444:576 */         ci.writeByte(opcode, pos);
/* 445:577 */         ci.write16bit(var, pos + 1);
/* 446:    */       }
/* 447:    */     }
/* 448:    */   }
/* 449:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.CodeAttribute
 * JD-Core Version:    0.7.0.1
 */