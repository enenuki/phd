/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public class InstructionFactory
/*   4:    */   implements InstructionConstants
/*   5:    */ {
/*   6:    */   protected ClassGen cg;
/*   7:    */   protected ConstantPoolGen cp;
/*   8:    */   
/*   9:    */   public InstructionFactory(ClassGen cg, ConstantPoolGen cp)
/*  10:    */   {
/*  11: 73 */     this.cg = cg;
/*  12: 74 */     this.cp = cp;
/*  13:    */   }
/*  14:    */   
/*  15:    */   public InstructionFactory(ClassGen cg)
/*  16:    */   {
/*  17: 80 */     this(cg, cg.getConstantPool());
/*  18:    */   }
/*  19:    */   
/*  20:    */   public InstructionFactory(ConstantPoolGen cp)
/*  21:    */   {
/*  22: 86 */     this(null, cp);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public InvokeInstruction createInvoke(String class_name, String name, Type ret_type, Type[] arg_types, short kind)
/*  26:    */   {
/*  27:102 */     int nargs = 0;
/*  28:103 */     String signature = Type.getMethodSignature(ret_type, arg_types);
/*  29:105 */     for (int i = 0; i < arg_types.length; i++) {
/*  30:106 */       nargs += arg_types[i].getSize();
/*  31:    */     }
/*  32:    */     int index;
/*  33:108 */     if (kind == 185) {
/*  34:109 */       index = this.cp.addInterfaceMethodref(class_name, name, signature);
/*  35:    */     } else {
/*  36:111 */       index = this.cp.addMethodref(class_name, name, signature);
/*  37:    */     }
/*  38:113 */     switch (kind)
/*  39:    */     {
/*  40:    */     case 183: 
/*  41:114 */       return new INVOKESPECIAL(index);
/*  42:    */     case 182: 
/*  43:115 */       return new INVOKEVIRTUAL(index);
/*  44:    */     case 184: 
/*  45:116 */       return new INVOKESTATIC(index);
/*  46:    */     case 185: 
/*  47:117 */       return new INVOKEINTERFACE(index, nargs + 1);
/*  48:    */     }
/*  49:119 */     throw new RuntimeException("Oops: Unknown invoke kind:" + kind);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public InstructionList createPrintln(String s)
/*  53:    */   {
/*  54:128 */     InstructionList il = new InstructionList();
/*  55:129 */     int out = this.cp.addFieldref("java.lang.System", "out", "Ljava/io/PrintStream;");
/*  56:    */     
/*  57:131 */     int println = this.cp.addMethodref("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
/*  58:    */     
/*  59:    */ 
/*  60:134 */     il.append(new GETSTATIC(out));
/*  61:135 */     il.append(new PUSH(this.cp, s));
/*  62:136 */     il.append(new INVOKEVIRTUAL(println));
/*  63:    */     
/*  64:138 */     return il;
/*  65:    */   }
/*  66:    */   
/*  67:    */   private static class MethodObject
/*  68:    */   {
/*  69:    */     Type[] arg_types;
/*  70:    */     Type result_type;
/*  71:    */     String[] arg_names;
/*  72:    */     String class_name;
/*  73:    */     String name;
/*  74:    */     int access;
/*  75:    */     
/*  76:    */     MethodObject(String c, String n, Type r, Type[] a, int acc)
/*  77:    */     {
/*  78:150 */       this.class_name = c;
/*  79:151 */       this.name = n;
/*  80:152 */       this.result_type = r;
/*  81:153 */       this.arg_types = a;
/*  82:154 */       this.access = acc;
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private InvokeInstruction createInvoke(MethodObject m, short kind)
/*  87:    */   {
/*  88:159 */     return createInvoke(m.class_name, m.name, m.result_type, m.arg_types, kind);
/*  89:    */   }
/*  90:    */   
/*  91:162 */   private static MethodObject[] append_mos = { new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.STRING }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.OBJECT }, 1), null, null, new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.BOOLEAN }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.CHAR }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.FLOAT }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.DOUBLE }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.LONG }, 1) };
/*  92:    */   
/*  93:    */   private static final boolean isString(Type type)
/*  94:    */   {
/*  95:187 */     return ((type instanceof ObjectType)) && (((ObjectType)type).getClassName().equals("java.lang.String"));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Instruction createAppend(Type type)
/*  99:    */   {
/* 100:192 */     byte t = type.getType();
/* 101:194 */     if (isString(type)) {
/* 102:195 */       return createInvoke(append_mos[0], (short)182);
/* 103:    */     }
/* 104:197 */     switch (t)
/* 105:    */     {
/* 106:    */     case 4: 
/* 107:    */     case 5: 
/* 108:    */     case 6: 
/* 109:    */     case 7: 
/* 110:    */     case 8: 
/* 111:    */     case 9: 
/* 112:    */     case 10: 
/* 113:    */     case 11: 
/* 114:206 */       return createInvoke(append_mos[t], (short)182);
/* 115:    */     case 13: 
/* 116:    */     case 14: 
/* 117:209 */       return createInvoke(append_mos[1], (short)182);
/* 118:    */     }
/* 119:211 */     throw new RuntimeException("Oops: No append for this type? " + type);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public FieldInstruction createFieldAccess(String class_name, String name, Type type, short kind)
/* 123:    */   {
/* 124:225 */     String signature = type.getSignature();
/* 125:    */     
/* 126:227 */     int index = this.cp.addFieldref(class_name, name, signature);
/* 127:229 */     switch (kind)
/* 128:    */     {
/* 129:    */     case 180: 
/* 130:230 */       return new GETFIELD(index);
/* 131:    */     case 181: 
/* 132:231 */       return new PUTFIELD(index);
/* 133:    */     case 178: 
/* 134:232 */       return new GETSTATIC(index);
/* 135:    */     case 179: 
/* 136:233 */       return new PUTSTATIC(index);
/* 137:    */     }
/* 138:236 */     throw new RuntimeException("Oops: Unknown getfield kind:" + kind);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static Instruction createThis()
/* 142:    */   {
/* 143:243 */     return new ALOAD(0);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static ReturnInstruction createReturn(Type type)
/* 147:    */   {
/* 148:249 */     switch (type.getType())
/* 149:    */     {
/* 150:    */     case 13: 
/* 151:    */     case 14: 
/* 152:251 */       return InstructionConstants.ARETURN;
/* 153:    */     case 4: 
/* 154:    */     case 5: 
/* 155:    */     case 8: 
/* 156:    */     case 9: 
/* 157:    */     case 10: 
/* 158:256 */       return InstructionConstants.IRETURN;
/* 159:    */     case 6: 
/* 160:257 */       return InstructionConstants.FRETURN;
/* 161:    */     case 7: 
/* 162:258 */       return InstructionConstants.DRETURN;
/* 163:    */     case 11: 
/* 164:259 */       return InstructionConstants.LRETURN;
/* 165:    */     case 12: 
/* 166:260 */       return InstructionConstants.RETURN;
/* 167:    */     }
/* 168:263 */     throw new RuntimeException("Invalid type: " + type);
/* 169:    */   }
/* 170:    */   
/* 171:    */   private static final ArithmeticInstruction createBinaryIntOp(char first, String op)
/* 172:    */   {
/* 173:268 */     switch (first)
/* 174:    */     {
/* 175:    */     case '-': 
/* 176:269 */       return InstructionConstants.ISUB;
/* 177:    */     case '+': 
/* 178:270 */       return InstructionConstants.IADD;
/* 179:    */     case '%': 
/* 180:271 */       return InstructionConstants.IREM;
/* 181:    */     case '*': 
/* 182:272 */       return InstructionConstants.IMUL;
/* 183:    */     case '/': 
/* 184:273 */       return InstructionConstants.IDIV;
/* 185:    */     case '&': 
/* 186:274 */       return InstructionConstants.IAND;
/* 187:    */     case '|': 
/* 188:275 */       return InstructionConstants.IOR;
/* 189:    */     case '^': 
/* 190:276 */       return InstructionConstants.IXOR;
/* 191:    */     case '<': 
/* 192:277 */       return InstructionConstants.ISHL;
/* 193:    */     case '>': 
/* 194:278 */       return op.equals(">>>") ? InstructionConstants.IUSHR : InstructionConstants.ISHR;
/* 195:    */     }
/* 196:280 */     throw new RuntimeException("Invalid operand " + op);
/* 197:    */   }
/* 198:    */   
/* 199:    */   private static final ArithmeticInstruction createBinaryLongOp(char first, String op)
/* 200:    */   {
/* 201:285 */     switch (first)
/* 202:    */     {
/* 203:    */     case '-': 
/* 204:286 */       return InstructionConstants.LSUB;
/* 205:    */     case '+': 
/* 206:287 */       return InstructionConstants.LADD;
/* 207:    */     case '%': 
/* 208:288 */       return InstructionConstants.LREM;
/* 209:    */     case '*': 
/* 210:289 */       return InstructionConstants.LMUL;
/* 211:    */     case '/': 
/* 212:290 */       return InstructionConstants.LDIV;
/* 213:    */     case '&': 
/* 214:291 */       return InstructionConstants.LAND;
/* 215:    */     case '|': 
/* 216:292 */       return InstructionConstants.LOR;
/* 217:    */     case '^': 
/* 218:293 */       return InstructionConstants.LXOR;
/* 219:    */     case '<': 
/* 220:294 */       return InstructionConstants.LSHL;
/* 221:    */     case '>': 
/* 222:295 */       return op.equals(">>>") ? InstructionConstants.LUSHR : InstructionConstants.LSHR;
/* 223:    */     }
/* 224:297 */     throw new RuntimeException("Invalid operand " + op);
/* 225:    */   }
/* 226:    */   
/* 227:    */   private static final ArithmeticInstruction createBinaryFloatOp(char op)
/* 228:    */   {
/* 229:302 */     switch (op)
/* 230:    */     {
/* 231:    */     case '-': 
/* 232:303 */       return InstructionConstants.FSUB;
/* 233:    */     case '+': 
/* 234:304 */       return InstructionConstants.FADD;
/* 235:    */     case '*': 
/* 236:305 */       return InstructionConstants.FMUL;
/* 237:    */     case '/': 
/* 238:306 */       return InstructionConstants.FDIV;
/* 239:    */     }
/* 240:307 */     throw new RuntimeException("Invalid operand " + op);
/* 241:    */   }
/* 242:    */   
/* 243:    */   private static final ArithmeticInstruction createBinaryDoubleOp(char op)
/* 244:    */   {
/* 245:312 */     switch (op)
/* 246:    */     {
/* 247:    */     case '-': 
/* 248:313 */       return InstructionConstants.DSUB;
/* 249:    */     case '+': 
/* 250:314 */       return InstructionConstants.DADD;
/* 251:    */     case '*': 
/* 252:315 */       return InstructionConstants.DMUL;
/* 253:    */     case '/': 
/* 254:316 */       return InstructionConstants.DDIV;
/* 255:    */     }
/* 256:317 */     throw new RuntimeException("Invalid operand " + op);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public static ArithmeticInstruction createBinaryOperation(String op, Type type)
/* 260:    */   {
/* 261:327 */     char first = op.toCharArray()[0];
/* 262:329 */     switch (type.getType())
/* 263:    */     {
/* 264:    */     case 5: 
/* 265:    */     case 8: 
/* 266:    */     case 9: 
/* 267:    */     case 10: 
/* 268:333 */       return createBinaryIntOp(first, op);
/* 269:    */     case 11: 
/* 270:334 */       return createBinaryLongOp(first, op);
/* 271:    */     case 6: 
/* 272:335 */       return createBinaryFloatOp(first);
/* 273:    */     case 7: 
/* 274:336 */       return createBinaryDoubleOp(first);
/* 275:    */     }
/* 276:337 */     throw new RuntimeException("Invalid type " + type);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public static StackInstruction createPop(int size)
/* 280:    */   {
/* 281:345 */     return size == 2 ? InstructionConstants.POP2 : InstructionConstants.POP;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public static StackInstruction createDup(int size)
/* 285:    */   {
/* 286:353 */     return size == 2 ? InstructionConstants.DUP2 : InstructionConstants.DUP;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public static StackInstruction createDup_2(int size)
/* 290:    */   {
/* 291:361 */     return size == 2 ? InstructionConstants.DUP2_X2 : InstructionConstants.DUP_X2;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public static StackInstruction createDup_1(int size)
/* 295:    */   {
/* 296:369 */     return size == 2 ? InstructionConstants.DUP2_X1 : InstructionConstants.DUP_X1;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public static LocalVariableInstruction createStore(Type type, int index)
/* 300:    */   {
/* 301:377 */     switch (type.getType())
/* 302:    */     {
/* 303:    */     case 4: 
/* 304:    */     case 5: 
/* 305:    */     case 8: 
/* 306:    */     case 9: 
/* 307:    */     case 10: 
/* 308:382 */       return new ISTORE(index);
/* 309:    */     case 6: 
/* 310:383 */       return new FSTORE(index);
/* 311:    */     case 7: 
/* 312:384 */       return new DSTORE(index);
/* 313:    */     case 11: 
/* 314:385 */       return new LSTORE(index);
/* 315:    */     case 13: 
/* 316:    */     case 14: 
/* 317:387 */       return new ASTORE(index);
/* 318:    */     }
/* 319:388 */     throw new RuntimeException("Invalid type " + type);
/* 320:    */   }
/* 321:    */   
/* 322:    */   public static LocalVariableInstruction createLoad(Type type, int index)
/* 323:    */   {
/* 324:396 */     switch (type.getType())
/* 325:    */     {
/* 326:    */     case 4: 
/* 327:    */     case 5: 
/* 328:    */     case 8: 
/* 329:    */     case 9: 
/* 330:    */     case 10: 
/* 331:401 */       return new ILOAD(index);
/* 332:    */     case 6: 
/* 333:402 */       return new FLOAD(index);
/* 334:    */     case 7: 
/* 335:403 */       return new DLOAD(index);
/* 336:    */     case 11: 
/* 337:404 */       return new LLOAD(index);
/* 338:    */     case 13: 
/* 339:    */     case 14: 
/* 340:406 */       return new ALOAD(index);
/* 341:    */     }
/* 342:407 */     throw new RuntimeException("Invalid type " + type);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public static ArrayInstruction createArrayLoad(Type type)
/* 346:    */   {
/* 347:415 */     switch (type.getType())
/* 348:    */     {
/* 349:    */     case 4: 
/* 350:    */     case 8: 
/* 351:417 */       return InstructionConstants.BALOAD;
/* 352:    */     case 5: 
/* 353:418 */       return InstructionConstants.CALOAD;
/* 354:    */     case 9: 
/* 355:419 */       return InstructionConstants.SALOAD;
/* 356:    */     case 10: 
/* 357:420 */       return InstructionConstants.IALOAD;
/* 358:    */     case 6: 
/* 359:421 */       return InstructionConstants.FALOAD;
/* 360:    */     case 7: 
/* 361:422 */       return InstructionConstants.DALOAD;
/* 362:    */     case 11: 
/* 363:423 */       return InstructionConstants.LALOAD;
/* 364:    */     case 13: 
/* 365:    */     case 14: 
/* 366:425 */       return InstructionConstants.AALOAD;
/* 367:    */     }
/* 368:426 */     throw new RuntimeException("Invalid type " + type);
/* 369:    */   }
/* 370:    */   
/* 371:    */   public static ArrayInstruction createArrayStore(Type type)
/* 372:    */   {
/* 373:434 */     switch (type.getType())
/* 374:    */     {
/* 375:    */     case 4: 
/* 376:    */     case 8: 
/* 377:436 */       return InstructionConstants.BASTORE;
/* 378:    */     case 5: 
/* 379:437 */       return InstructionConstants.CASTORE;
/* 380:    */     case 9: 
/* 381:438 */       return InstructionConstants.SASTORE;
/* 382:    */     case 10: 
/* 383:439 */       return InstructionConstants.IASTORE;
/* 384:    */     case 6: 
/* 385:440 */       return InstructionConstants.FASTORE;
/* 386:    */     case 7: 
/* 387:441 */       return InstructionConstants.DASTORE;
/* 388:    */     case 11: 
/* 389:442 */       return InstructionConstants.LASTORE;
/* 390:    */     case 13: 
/* 391:    */     case 14: 
/* 392:444 */       return InstructionConstants.AASTORE;
/* 393:    */     }
/* 394:445 */     throw new RuntimeException("Invalid type " + type);
/* 395:    */   }
/* 396:    */   
/* 397:    */   public Instruction createCast(Type src_type, Type dest_type)
/* 398:    */   {
/* 399:454 */     if (((src_type instanceof BasicType)) && ((dest_type instanceof BasicType)))
/* 400:    */     {
/* 401:455 */       byte dest = dest_type.getType();
/* 402:456 */       byte src = src_type.getType();
/* 403:458 */       if ((dest == 11) && ((src == 5) || (src == 8) || (src == 9))) {
/* 404:460 */         src = 10;
/* 405:    */       }
/* 406:462 */       String[] short_names = { "C", "F", "D", "B", "S", "I", "L" };
/* 407:    */       
/* 408:464 */       String name = "org.apache.bcel.generic." + short_names[(src - 5)] + "2" + short_names[(dest - 5)];
/* 409:    */       
/* 410:    */ 
/* 411:467 */       Instruction i = null;
/* 412:    */       try
/* 413:    */       {
/* 414:469 */         i = (Instruction)Class.forName(name).newInstance();
/* 415:    */       }
/* 416:    */       catch (Exception e)
/* 417:    */       {
/* 418:471 */         throw new RuntimeException("Could not find instruction: " + name);
/* 419:    */       }
/* 420:474 */       return i;
/* 421:    */     }
/* 422:475 */     if (((src_type instanceof ReferenceType)) && ((dest_type instanceof ReferenceType)))
/* 423:    */     {
/* 424:476 */       if ((dest_type instanceof ArrayType)) {
/* 425:477 */         return new CHECKCAST(this.cp.addArrayClass((ArrayType)dest_type));
/* 426:    */       }
/* 427:479 */       return new CHECKCAST(this.cp.addClass(((ObjectType)dest_type).getClassName()));
/* 428:    */     }
/* 429:482 */     throw new RuntimeException("Can not cast " + src_type + " to " + dest_type);
/* 430:    */   }
/* 431:    */   
/* 432:    */   public GETFIELD createGetField(String class_name, String name, Type t)
/* 433:    */   {
/* 434:486 */     return new GETFIELD(this.cp.addFieldref(class_name, name, t.getSignature()));
/* 435:    */   }
/* 436:    */   
/* 437:    */   public GETSTATIC createGetStatic(String class_name, String name, Type t)
/* 438:    */   {
/* 439:490 */     return new GETSTATIC(this.cp.addFieldref(class_name, name, t.getSignature()));
/* 440:    */   }
/* 441:    */   
/* 442:    */   public PUTFIELD createPutField(String class_name, String name, Type t)
/* 443:    */   {
/* 444:494 */     return new PUTFIELD(this.cp.addFieldref(class_name, name, t.getSignature()));
/* 445:    */   }
/* 446:    */   
/* 447:    */   public PUTSTATIC createPutStatic(String class_name, String name, Type t)
/* 448:    */   {
/* 449:498 */     return new PUTSTATIC(this.cp.addFieldref(class_name, name, t.getSignature()));
/* 450:    */   }
/* 451:    */   
/* 452:    */   public CHECKCAST createCheckCast(ReferenceType t)
/* 453:    */   {
/* 454:502 */     if ((t instanceof ArrayType)) {
/* 455:503 */       return new CHECKCAST(this.cp.addArrayClass((ArrayType)t));
/* 456:    */     }
/* 457:505 */     return new CHECKCAST(this.cp.addClass((ObjectType)t));
/* 458:    */   }
/* 459:    */   
/* 460:    */   public NEW createNew(ObjectType t)
/* 461:    */   {
/* 462:509 */     return new NEW(this.cp.addClass(t));
/* 463:    */   }
/* 464:    */   
/* 465:    */   public NEW createNew(String s)
/* 466:    */   {
/* 467:513 */     return createNew(new ObjectType(s));
/* 468:    */   }
/* 469:    */   
/* 470:    */   public AllocationInstruction createNewArray(Type t, short dim)
/* 471:    */   {
/* 472:519 */     if (dim == 1)
/* 473:    */     {
/* 474:520 */       if ((t instanceof ObjectType)) {
/* 475:521 */         return new ANEWARRAY(this.cp.addClass((ObjectType)t));
/* 476:    */       }
/* 477:522 */       if ((t instanceof ArrayType)) {
/* 478:523 */         return new ANEWARRAY(this.cp.addArrayClass((ArrayType)t));
/* 479:    */       }
/* 480:525 */       return new NEWARRAY(((BasicType)t).getType());
/* 481:    */     }
/* 482:    */     ArrayType at;
/* 483:529 */     if ((t instanceof ArrayType)) {
/* 484:530 */       at = (ArrayType)t;
/* 485:    */     } else {
/* 486:532 */       at = new ArrayType(t, dim);
/* 487:    */     }
/* 488:534 */     return new MULTIANEWARRAY(this.cp.addArrayClass(at), dim);
/* 489:    */   }
/* 490:    */   
/* 491:    */   public static Instruction createNull(Type type)
/* 492:    */   {
/* 493:541 */     switch (type.getType())
/* 494:    */     {
/* 495:    */     case 13: 
/* 496:    */     case 14: 
/* 497:543 */       return InstructionConstants.ACONST_NULL;
/* 498:    */     case 4: 
/* 499:    */     case 5: 
/* 500:    */     case 8: 
/* 501:    */     case 9: 
/* 502:    */     case 10: 
/* 503:548 */       return InstructionConstants.ICONST_0;
/* 504:    */     case 6: 
/* 505:549 */       return InstructionConstants.FCONST_0;
/* 506:    */     case 7: 
/* 507:550 */       return InstructionConstants.DCONST_0;
/* 508:    */     case 11: 
/* 509:551 */       return InstructionConstants.LCONST_0;
/* 510:    */     case 12: 
/* 511:552 */       return InstructionConstants.NOP;
/* 512:    */     }
/* 513:555 */     throw new RuntimeException("Invalid type: " + type);
/* 514:    */   }
/* 515:    */   
/* 516:    */   public static BranchInstruction createBranchInstruction(short opcode, InstructionHandle target)
/* 517:    */   {
/* 518:563 */     switch (opcode)
/* 519:    */     {
/* 520:    */     case 153: 
/* 521:564 */       return new IFEQ(target);
/* 522:    */     case 154: 
/* 523:565 */       return new IFNE(target);
/* 524:    */     case 155: 
/* 525:566 */       return new IFLT(target);
/* 526:    */     case 156: 
/* 527:567 */       return new IFGE(target);
/* 528:    */     case 157: 
/* 529:568 */       return new IFGT(target);
/* 530:    */     case 158: 
/* 531:569 */       return new IFLE(target);
/* 532:    */     case 159: 
/* 533:570 */       return new IF_ICMPEQ(target);
/* 534:    */     case 160: 
/* 535:571 */       return new IF_ICMPNE(target);
/* 536:    */     case 161: 
/* 537:572 */       return new IF_ICMPLT(target);
/* 538:    */     case 162: 
/* 539:573 */       return new IF_ICMPGE(target);
/* 540:    */     case 163: 
/* 541:574 */       return new IF_ICMPGT(target);
/* 542:    */     case 164: 
/* 543:575 */       return new IF_ICMPLE(target);
/* 544:    */     case 165: 
/* 545:576 */       return new IF_ACMPEQ(target);
/* 546:    */     case 166: 
/* 547:577 */       return new IF_ACMPNE(target);
/* 548:    */     case 167: 
/* 549:578 */       return new GOTO(target);
/* 550:    */     case 168: 
/* 551:579 */       return new JSR(target);
/* 552:    */     case 198: 
/* 553:580 */       return new IFNULL(target);
/* 554:    */     case 199: 
/* 555:581 */       return new IFNONNULL(target);
/* 556:    */     case 200: 
/* 557:582 */       return new GOTO_W(target);
/* 558:    */     case 201: 
/* 559:583 */       return new JSR_W(target);
/* 560:    */     }
/* 561:585 */     throw new RuntimeException("Invalid opcode: " + opcode);
/* 562:    */   }
/* 563:    */   
/* 564:    */   public void setClassGen(ClassGen c)
/* 565:    */   {
/* 566:589 */     this.cg = c;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public ClassGen getClassGen()
/* 570:    */   {
/* 571:590 */     return this.cg;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public void setConstantPool(ConstantPoolGen c)
/* 575:    */   {
/* 576:591 */     this.cp = c;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public ConstantPoolGen getConstantPool()
/* 580:    */   {
/* 581:592 */     return this.cp;
/* 582:    */   }
/* 583:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.InstructionFactory
 * JD-Core Version:    0.7.0.1
 */