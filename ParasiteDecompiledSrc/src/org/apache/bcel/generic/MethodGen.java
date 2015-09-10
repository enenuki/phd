/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.AbstractList;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Stack;
/*   9:    */ import org.apache.bcel.classfile.AccessFlags;
/*  10:    */ import org.apache.bcel.classfile.Attribute;
/*  11:    */ import org.apache.bcel.classfile.Code;
/*  12:    */ import org.apache.bcel.classfile.CodeException;
/*  13:    */ import org.apache.bcel.classfile.ConstantPool;
/*  14:    */ import org.apache.bcel.classfile.ExceptionTable;
/*  15:    */ import org.apache.bcel.classfile.FieldOrMethod;
/*  16:    */ import org.apache.bcel.classfile.LineNumber;
/*  17:    */ import org.apache.bcel.classfile.LineNumberTable;
/*  18:    */ import org.apache.bcel.classfile.LocalVariable;
/*  19:    */ import org.apache.bcel.classfile.LocalVariableTable;
/*  20:    */ import org.apache.bcel.classfile.Method;
/*  21:    */ import org.apache.bcel.classfile.Utility;
/*  22:    */ 
/*  23:    */ public class MethodGen
/*  24:    */   extends FieldGenOrMethodGen
/*  25:    */ {
/*  26:    */   private String class_name;
/*  27:    */   private Type[] arg_types;
/*  28:    */   private String[] arg_names;
/*  29:    */   private int max_locals;
/*  30:    */   private int max_stack;
/*  31:    */   private InstructionList il;
/*  32:    */   private boolean strip_attributes;
/*  33: 86 */   private ArrayList variable_vec = new ArrayList();
/*  34: 87 */   private ArrayList line_number_vec = new ArrayList();
/*  35: 88 */   private ArrayList exception_vec = new ArrayList();
/*  36: 89 */   private ArrayList throws_vec = new ArrayList();
/*  37: 90 */   private ArrayList code_attrs_vec = new ArrayList();
/*  38:    */   private ArrayList observers;
/*  39:    */   
/*  40:    */   public MethodGen(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cp)
/*  41:    */   {
/*  42:117 */     setAccessFlags(access_flags);
/*  43:118 */     setType(return_type);
/*  44:119 */     setArgumentTypes(arg_types);
/*  45:120 */     setArgumentNames(arg_names);
/*  46:121 */     setName(method_name);
/*  47:122 */     setClassName(class_name);
/*  48:123 */     setInstructionList(il);
/*  49:124 */     setConstantPool(cp);
/*  50:126 */     if ((access_flags & 0x500) == 0)
/*  51:    */     {
/*  52:127 */       InstructionHandle start = il.getStart();
/*  53:128 */       InstructionHandle end = il.getEnd();
/*  54:132 */       if ((!isStatic()) && (class_name != null)) {
/*  55:133 */         addLocalVariable("this", new ObjectType(class_name), start, end);
/*  56:    */       }
/*  57:135 */       if (arg_types != null)
/*  58:    */       {
/*  59:136 */         int size = arg_types.length;
/*  60:138 */         if (arg_names != null)
/*  61:    */         {
/*  62:139 */           if (size != arg_names.length) {
/*  63:140 */             throw new ClassGenException("Mismatch in argument array lengths: " + size + " vs. " + arg_names.length);
/*  64:    */           }
/*  65:    */         }
/*  66:    */         else
/*  67:    */         {
/*  68:143 */           arg_names = new String[size];
/*  69:145 */           for (int i = 0; i < size; i++) {
/*  70:146 */             arg_names[i] = ("arg" + i);
/*  71:    */           }
/*  72:148 */           setArgumentNames(arg_names);
/*  73:    */         }
/*  74:151 */         for (int i = 0; i < size; i++) {
/*  75:152 */           addLocalVariable(arg_names[i], arg_types[i], start, end);
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public MethodGen(Method m, String class_name, ConstantPoolGen cp)
/*  82:    */   {
/*  83:165 */     this(m.getAccessFlags(), Type.getReturnType(m.getSignature()), Type.getArgumentTypes(m.getSignature()), null, m.getName(), class_name, (m.getAccessFlags() & 0x500) == 0 ? new InstructionList(m.getCode().getCode()) : null, cp);
/*  84:    */     
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:172 */     Attribute[] attributes = m.getAttributes();
/*  91:173 */     for (int i = 0; i < attributes.length; i++)
/*  92:    */     {
/*  93:174 */       Attribute a = attributes[i];
/*  94:176 */       if ((a instanceof Code))
/*  95:    */       {
/*  96:177 */         Code c = (Code)a;
/*  97:178 */         setMaxStack(c.getMaxStack());
/*  98:179 */         setMaxLocals(c.getMaxLocals());
/*  99:    */         
/* 100:181 */         CodeException[] ces = c.getExceptionTable();
/* 101:183 */         if (ces != null) {
/* 102:184 */           for (int j = 0; j < ces.length; j++)
/* 103:    */           {
/* 104:185 */             CodeException ce = ces[j];
/* 105:186 */             int type = ce.getCatchType();
/* 106:187 */             ObjectType c_type = null;
/* 107:189 */             if (type > 0)
/* 108:    */             {
/* 109:190 */               String cen = m.getConstantPool().getConstantString(type, (byte)7);
/* 110:191 */               c_type = new ObjectType(cen);
/* 111:    */             }
/* 112:194 */             int end_pc = ce.getEndPC();
/* 113:195 */             int length = m.getCode().getCode().length;
/* 114:    */             InstructionHandle end;
/* 115:199 */             if (length == end_pc)
/* 116:    */             {
/* 117:200 */               end = this.il.getEnd();
/* 118:    */             }
/* 119:    */             else
/* 120:    */             {
/* 121:202 */               end = this.il.findHandle(end_pc);
/* 122:203 */               end = end.getPrev();
/* 123:    */             }
/* 124:206 */             addExceptionHandler(this.il.findHandle(ce.getStartPC()), end, this.il.findHandle(ce.getHandlerPC()), c_type);
/* 125:    */           }
/* 126:    */         }
/* 127:211 */         Attribute[] c_attributes = c.getAttributes();
/* 128:212 */         for (int j = 0; j < c_attributes.length; j++)
/* 129:    */         {
/* 130:213 */           a = c_attributes[j];
/* 131:215 */           if ((a instanceof LineNumberTable))
/* 132:    */           {
/* 133:216 */             LineNumber[] ln = ((LineNumberTable)a).getLineNumberTable();
/* 134:217 */             for (int k = 0; k < ln.length; k++)
/* 135:    */             {
/* 136:218 */               LineNumber l = ln[k];
/* 137:219 */               addLineNumber(this.il.findHandle(l.getStartPC()), l.getLineNumber());
/* 138:    */             }
/* 139:    */           }
/* 140:221 */           else if ((a instanceof LocalVariableTable))
/* 141:    */           {
/* 142:222 */             LocalVariable[] lv = ((LocalVariableTable)a).getLocalVariableTable();
/* 143:223 */             for (int k = 0; k < lv.length; k++)
/* 144:    */             {
/* 145:224 */               LocalVariable l = lv[k];
/* 146:225 */               InstructionHandle start = this.il.findHandle(l.getStartPC());
/* 147:226 */               InstructionHandle end = this.il.findHandle(l.getStartPC() + l.getLength());
/* 148:229 */               if (start == null) {
/* 149:230 */                 start = this.il.getStart();
/* 150:    */               }
/* 151:231 */               if (end == null) {
/* 152:232 */                 end = this.il.getEnd();
/* 153:    */               }
/* 154:234 */               addLocalVariable(l.getName(), Type.getType(l.getSignature()), l.getIndex(), start, end);
/* 155:    */             }
/* 156:    */           }
/* 157:    */           else
/* 158:    */           {
/* 159:238 */             addCodeAttribute(a);
/* 160:    */           }
/* 161:    */         }
/* 162:    */       }
/* 163:240 */       else if ((a instanceof ExceptionTable))
/* 164:    */       {
/* 165:241 */         String[] names = ((ExceptionTable)a).getExceptionNames();
/* 166:242 */         for (int j = 0; j < names.length; j++) {
/* 167:243 */           addException(names[j]);
/* 168:    */         }
/* 169:    */       }
/* 170:    */       else
/* 171:    */       {
/* 172:245 */         addAttribute(a);
/* 173:    */       }
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public LocalVariableGen addLocalVariable(String name, Type type, int slot, InstructionHandle start, InstructionHandle end)
/* 178:    */   {
/* 179:264 */     byte t = type.getType();
/* 180:265 */     int add = type.getSize();
/* 181:267 */     if (slot + add > this.max_locals) {
/* 182:268 */       this.max_locals = (slot + add);
/* 183:    */     }
/* 184:270 */     LocalVariableGen l = new LocalVariableGen(slot, name, type, start, end);
/* 185:    */     int i;
/* 186:273 */     if ((i = this.variable_vec.indexOf(l)) >= 0) {
/* 187:274 */       this.variable_vec.set(i, l);
/* 188:    */     } else {
/* 189:276 */       this.variable_vec.add(l);
/* 190:    */     }
/* 191:277 */     return l;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public LocalVariableGen addLocalVariable(String name, Type type, InstructionHandle start, InstructionHandle end)
/* 195:    */   {
/* 196:295 */     return addLocalVariable(name, type, this.max_locals, start, end);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void removeLocalVariable(LocalVariableGen l)
/* 200:    */   {
/* 201:303 */     this.variable_vec.remove(l);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void removeLocalVariables()
/* 205:    */   {
/* 206:310 */     this.variable_vec.clear();
/* 207:    */   }
/* 208:    */   
/* 209:    */   private static final void sort(LocalVariableGen[] vars, int l, int r)
/* 210:    */   {
/* 211:317 */     int i = l;int j = r;
/* 212:318 */     int m = vars[((l + r) / 2)].getIndex();
/* 213:    */     do
/* 214:    */     {
/* 215:322 */       while (vars[i].getIndex() < m) {
/* 216:322 */         i++;
/* 217:    */       }
/* 218:323 */       while (m < vars[j].getIndex()) {
/* 219:323 */         j--;
/* 220:    */       }
/* 221:325 */       if (i <= j)
/* 222:    */       {
/* 223:326 */         LocalVariableGen h = vars[i];vars[i] = vars[j];vars[j] = h;
/* 224:327 */         i++;j--;
/* 225:    */       }
/* 226:329 */     } while (i <= j);
/* 227:331 */     if (l < j) {
/* 228:331 */       sort(vars, l, j);
/* 229:    */     }
/* 230:332 */     if (i < r) {
/* 231:332 */       sort(vars, i, r);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public LocalVariableGen[] getLocalVariables()
/* 236:    */   {
/* 237:342 */     int size = this.variable_vec.size();
/* 238:343 */     LocalVariableGen[] lg = new LocalVariableGen[size];
/* 239:344 */     this.variable_vec.toArray(lg);
/* 240:346 */     for (int i = 0; i < size; i++)
/* 241:    */     {
/* 242:347 */       if (lg[i].getStart() == null) {
/* 243:348 */         lg[i].setStart(this.il.getStart());
/* 244:    */       }
/* 245:350 */       if (lg[i].getEnd() == null) {
/* 246:351 */         lg[i].setEnd(this.il.getEnd());
/* 247:    */       }
/* 248:    */     }
/* 249:354 */     if (size > 1) {
/* 250:355 */       sort(lg, 0, size - 1);
/* 251:    */     }
/* 252:357 */     return lg;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public LocalVariableTable getLocalVariableTable(ConstantPoolGen cp)
/* 256:    */   {
/* 257:364 */     LocalVariableGen[] lg = getLocalVariables();
/* 258:365 */     int size = lg.length;
/* 259:366 */     LocalVariable[] lv = new LocalVariable[size];
/* 260:368 */     for (int i = 0; i < size; i++) {
/* 261:369 */       lv[i] = lg[i].getLocalVariable(cp);
/* 262:    */     }
/* 263:371 */     return new LocalVariableTable(cp.addUtf8("LocalVariableTable"), 2 + lv.length * 10, lv, cp.getConstantPool());
/* 264:    */   }
/* 265:    */   
/* 266:    */   public LineNumberGen addLineNumber(InstructionHandle ih, int src_line)
/* 267:    */   {
/* 268:383 */     LineNumberGen l = new LineNumberGen(ih, src_line);
/* 269:384 */     this.line_number_vec.add(l);
/* 270:385 */     return l;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void removeLineNumber(LineNumberGen l)
/* 274:    */   {
/* 275:392 */     this.line_number_vec.remove(l);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void removeLineNumbers()
/* 279:    */   {
/* 280:399 */     this.line_number_vec.clear();
/* 281:    */   }
/* 282:    */   
/* 283:    */   public LineNumberGen[] getLineNumbers()
/* 284:    */   {
/* 285:406 */     LineNumberGen[] lg = new LineNumberGen[this.line_number_vec.size()];
/* 286:407 */     this.line_number_vec.toArray(lg);
/* 287:408 */     return lg;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public LineNumberTable getLineNumberTable(ConstantPoolGen cp)
/* 291:    */   {
/* 292:415 */     int size = this.line_number_vec.size();
/* 293:416 */     LineNumber[] ln = new LineNumber[size];
/* 294:    */     try
/* 295:    */     {
/* 296:419 */       for (int i = 0; i < size; i++) {
/* 297:420 */         ln[i] = ((LineNumberGen)this.line_number_vec.get(i)).getLineNumber();
/* 298:    */       }
/* 299:    */     }
/* 300:    */     catch (ArrayIndexOutOfBoundsException e) {}
/* 301:423 */     return new LineNumberTable(cp.addUtf8("LineNumberTable"), 2 + ln.length * 4, ln, cp.getConstantPool());
/* 302:    */   }
/* 303:    */   
/* 304:    */   public CodeExceptionGen addExceptionHandler(InstructionHandle start_pc, InstructionHandle end_pc, InstructionHandle handler_pc, ObjectType catch_type)
/* 305:    */   {
/* 306:442 */     if ((start_pc == null) || (end_pc == null) || (handler_pc == null)) {
/* 307:443 */       throw new ClassGenException("Exception handler target is null instruction");
/* 308:    */     }
/* 309:445 */     CodeExceptionGen c = new CodeExceptionGen(start_pc, end_pc, handler_pc, catch_type);
/* 310:    */     
/* 311:447 */     this.exception_vec.add(c);
/* 312:448 */     return c;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void removeExceptionHandler(CodeExceptionGen c)
/* 316:    */   {
/* 317:455 */     this.exception_vec.remove(c);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void removeExceptionHandlers()
/* 321:    */   {
/* 322:462 */     this.exception_vec.clear();
/* 323:    */   }
/* 324:    */   
/* 325:    */   public CodeExceptionGen[] getExceptionHandlers()
/* 326:    */   {
/* 327:469 */     CodeExceptionGen[] cg = new CodeExceptionGen[this.exception_vec.size()];
/* 328:470 */     this.exception_vec.toArray(cg);
/* 329:471 */     return cg;
/* 330:    */   }
/* 331:    */   
/* 332:    */   private CodeException[] getCodeExceptions()
/* 333:    */   {
/* 334:478 */     int size = this.exception_vec.size();
/* 335:479 */     CodeException[] c_exc = new CodeException[size];
/* 336:    */     try
/* 337:    */     {
/* 338:482 */       for (int i = 0; i < size; i++)
/* 339:    */       {
/* 340:483 */         CodeExceptionGen c = (CodeExceptionGen)this.exception_vec.get(i);
/* 341:484 */         c_exc[i] = c.getCodeException(this.cp);
/* 342:    */       }
/* 343:    */     }
/* 344:    */     catch (ArrayIndexOutOfBoundsException e) {}
/* 345:488 */     return c_exc;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void addException(String class_name)
/* 349:    */   {
/* 350:497 */     this.throws_vec.add(class_name);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void removeException(String c)
/* 354:    */   {
/* 355:504 */     this.throws_vec.remove(c);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void removeExceptions()
/* 359:    */   {
/* 360:511 */     this.throws_vec.clear();
/* 361:    */   }
/* 362:    */   
/* 363:    */   public String[] getExceptions()
/* 364:    */   {
/* 365:518 */     String[] e = new String[this.throws_vec.size()];
/* 366:519 */     this.throws_vec.toArray(e);
/* 367:520 */     return e;
/* 368:    */   }
/* 369:    */   
/* 370:    */   private ExceptionTable getExceptionTable(ConstantPoolGen cp)
/* 371:    */   {
/* 372:527 */     int size = this.throws_vec.size();
/* 373:528 */     int[] ex = new int[size];
/* 374:    */     try
/* 375:    */     {
/* 376:531 */       for (int i = 0; i < size; i++) {
/* 377:532 */         ex[i] = cp.addClass((String)this.throws_vec.get(i));
/* 378:    */       }
/* 379:    */     }
/* 380:    */     catch (ArrayIndexOutOfBoundsException e) {}
/* 381:535 */     return new ExceptionTable(cp.addUtf8("Exceptions"), 2 + 2 * size, ex, cp.getConstantPool());
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void addCodeAttribute(Attribute a)
/* 385:    */   {
/* 386:548 */     this.code_attrs_vec.add(a);
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void removeCodeAttribute(Attribute a)
/* 390:    */   {
/* 391:553 */     this.code_attrs_vec.remove(a);
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void removeCodeAttributes()
/* 395:    */   {
/* 396:559 */     this.code_attrs_vec.clear();
/* 397:    */   }
/* 398:    */   
/* 399:    */   public Attribute[] getCodeAttributes()
/* 400:    */   {
/* 401:566 */     Attribute[] attributes = new Attribute[this.code_attrs_vec.size()];
/* 402:567 */     this.code_attrs_vec.toArray(attributes);
/* 403:568 */     return attributes;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public Method getMethod()
/* 407:    */   {
/* 408:578 */     String signature = getSignature();
/* 409:579 */     int name_index = this.cp.addUtf8(this.name);
/* 410:580 */     int signature_index = this.cp.addUtf8(signature);
/* 411:    */     
/* 412:    */ 
/* 413:    */ 
/* 414:584 */     byte[] byte_code = null;
/* 415:586 */     if (this.il != null) {
/* 416:587 */       byte_code = this.il.getByteCode();
/* 417:    */     }
/* 418:590 */     LineNumberTable lnt = null;
/* 419:591 */     LocalVariableTable lvt = null;
/* 420:595 */     if ((this.variable_vec.size() > 0) && (!this.strip_attributes)) {
/* 421:596 */       addCodeAttribute(lvt = getLocalVariableTable(this.cp));
/* 422:    */     }
/* 423:598 */     if ((this.line_number_vec.size() > 0) && (!this.strip_attributes)) {
/* 424:599 */       addCodeAttribute(lnt = getLineNumberTable(this.cp));
/* 425:    */     }
/* 426:601 */     Attribute[] code_attrs = getCodeAttributes();
/* 427:    */     
/* 428:    */ 
/* 429:    */ 
/* 430:605 */     int attrs_len = 0;
/* 431:606 */     for (int i = 0; i < code_attrs.length; i++) {
/* 432:607 */       attrs_len += code_attrs[i].getLength() + 6;
/* 433:    */     }
/* 434:609 */     CodeException[] c_exc = getCodeExceptions();
/* 435:610 */     int exc_len = c_exc.length * 8;
/* 436:    */     
/* 437:612 */     Code code = null;
/* 438:613 */     if ((this.il != null) && (!isAbstract()))
/* 439:    */     {
/* 440:614 */       code = new Code(this.cp.addUtf8("Code"), 8 + byte_code.length + 2 + exc_len + 2 + attrs_len, this.max_stack, this.max_locals, byte_code, c_exc, code_attrs, this.cp.getConstantPool());
/* 441:    */       
/* 442:    */ 
/* 443:    */ 
/* 444:    */ 
/* 445:    */ 
/* 446:    */ 
/* 447:    */ 
/* 448:    */ 
/* 449:623 */       addAttribute(code);
/* 450:    */     }
/* 451:626 */     ExceptionTable et = null;
/* 452:628 */     if (this.throws_vec.size() > 0) {
/* 453:629 */       addAttribute(et = getExceptionTable(this.cp));
/* 454:    */     }
/* 455:631 */     Method m = new Method(this.access_flags, name_index, signature_index, getAttributes(), this.cp.getConstantPool());
/* 456:635 */     if (lvt != null) {
/* 457:635 */       removeCodeAttribute(lvt);
/* 458:    */     }
/* 459:636 */     if (lnt != null) {
/* 460:636 */       removeCodeAttribute(lnt);
/* 461:    */     }
/* 462:637 */     if (code != null) {
/* 463:637 */       removeAttribute(code);
/* 464:    */     }
/* 465:638 */     if (et != null) {
/* 466:638 */       removeAttribute(et);
/* 467:    */     }
/* 468:640 */     return m;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void removeNOPs()
/* 472:    */   {
/* 473:649 */     if (this.il != null)
/* 474:    */     {
/* 475:    */       InstructionHandle next;
/* 476:653 */       for (InstructionHandle ih = this.il.getStart(); ih != null; ih = next)
/* 477:    */       {
/* 478:654 */         next = ih.next;
/* 479:656 */         if ((next != null) && ((ih.getInstruction() instanceof NOP)))
/* 480:    */         {
/* 481:    */           InstructionHandle[] targets;
/* 482:    */           int i;
/* 483:    */           try
/* 484:    */           {
/* 485:658 */             this.il.delete(ih);
/* 486:    */           }
/* 487:    */           catch (TargetLostException e)
/* 488:    */           {
/* 489:660 */             targets = e.getTargets();
/* 490:    */             
/* 491:662 */             i = 0;
/* 492:    */           }
/* 493:662 */           for (; i < targets.length; i++)
/* 494:    */           {
/* 495:663 */             InstructionTargeter[] targeters = targets[i].getTargeters();
/* 496:665 */             for (int j = 0; j < targeters.length; j++) {
/* 497:666 */               targeters[j].updateTarget(targets[i], next);
/* 498:    */             }
/* 499:    */           }
/* 500:    */         }
/* 501:    */       }
/* 502:    */     }
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void setMaxLocals(int m)
/* 506:    */   {
/* 507:677 */     this.max_locals = m;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public int getMaxLocals()
/* 511:    */   {
/* 512:678 */     return this.max_locals;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void setMaxStack(int m)
/* 516:    */   {
/* 517:683 */     this.max_stack = m;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public int getMaxStack()
/* 521:    */   {
/* 522:684 */     return this.max_stack;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public String getClassName()
/* 526:    */   {
/* 527:688 */     return this.class_name;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setClassName(String class_name)
/* 531:    */   {
/* 532:689 */     this.class_name = class_name;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public void setReturnType(Type return_type)
/* 536:    */   {
/* 537:691 */     setType(return_type);
/* 538:    */   }
/* 539:    */   
/* 540:    */   public Type getReturnType()
/* 541:    */   {
/* 542:692 */     return getType();
/* 543:    */   }
/* 544:    */   
/* 545:    */   public void setArgumentTypes(Type[] arg_types)
/* 546:    */   {
/* 547:694 */     this.arg_types = arg_types;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public Type[] getArgumentTypes()
/* 551:    */   {
/* 552:695 */     return (Type[])this.arg_types.clone();
/* 553:    */   }
/* 554:    */   
/* 555:    */   public void setArgumentType(int i, Type type)
/* 556:    */   {
/* 557:696 */     this.arg_types[i] = type;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public Type getArgumentType(int i)
/* 561:    */   {
/* 562:697 */     return this.arg_types[i];
/* 563:    */   }
/* 564:    */   
/* 565:    */   public void setArgumentNames(String[] arg_names)
/* 566:    */   {
/* 567:699 */     this.arg_names = arg_names;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public String[] getArgumentNames()
/* 571:    */   {
/* 572:700 */     return (String[])this.arg_names.clone();
/* 573:    */   }
/* 574:    */   
/* 575:    */   public void setArgumentName(int i, String name)
/* 576:    */   {
/* 577:701 */     this.arg_names[i] = name;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public String getArgumentName(int i)
/* 581:    */   {
/* 582:702 */     return this.arg_names[i];
/* 583:    */   }
/* 584:    */   
/* 585:    */   public InstructionList getInstructionList()
/* 586:    */   {
/* 587:704 */     return this.il;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public void setInstructionList(InstructionList il)
/* 591:    */   {
/* 592:705 */     this.il = il;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public String getSignature()
/* 596:    */   {
/* 597:708 */     return Type.getMethodSignature(this.type, this.arg_types);
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setMaxStack()
/* 601:    */   {
/* 602:716 */     if (this.il != null) {
/* 603:717 */       this.max_stack = getMaxStack(this.cp, this.il, getExceptionHandlers());
/* 604:    */     } else {
/* 605:719 */       this.max_stack = 0;
/* 606:    */     }
/* 607:    */   }
/* 608:    */   
/* 609:    */   public void setMaxLocals()
/* 610:    */   {
/* 611:726 */     if (this.il != null)
/* 612:    */     {
/* 613:727 */       int max = isStatic() ? 0 : 1;
/* 614:729 */       if (this.arg_types != null) {
/* 615:730 */         for (int i = 0; i < this.arg_types.length; i++) {
/* 616:731 */           max += this.arg_types[i].getSize();
/* 617:    */         }
/* 618:    */       }
/* 619:733 */       for (InstructionHandle ih = this.il.getStart(); ih != null; ih = ih.getNext())
/* 620:    */       {
/* 621:734 */         Instruction ins = ih.getInstruction();
/* 622:736 */         if (((ins instanceof LocalVariableInstruction)) || ((ins instanceof RET)) || ((ins instanceof IINC)))
/* 623:    */         {
/* 624:739 */           int index = ((IndexedInstruction)ins).getIndex() + ((TypedInstruction)ins).getType(this.cp).getSize();
/* 625:742 */           if (index > max) {
/* 626:743 */             max = index;
/* 627:    */           }
/* 628:    */         }
/* 629:    */       }
/* 630:747 */       this.max_locals = max;
/* 631:    */     }
/* 632:    */     else
/* 633:    */     {
/* 634:749 */       this.max_locals = 0;
/* 635:    */     }
/* 636:    */   }
/* 637:    */   
/* 638:    */   public void stripAttributes(boolean flag)
/* 639:    */   {
/* 640:755 */     this.strip_attributes = flag;
/* 641:    */   }
/* 642:    */   
/* 643:    */   static final class BranchTarget
/* 644:    */   {
/* 645:    */     InstructionHandle target;
/* 646:    */     int stackDepth;
/* 647:    */     
/* 648:    */     BranchTarget(InstructionHandle target, int stackDepth)
/* 649:    */     {
/* 650:762 */       this.target = target;
/* 651:763 */       this.stackDepth = stackDepth;
/* 652:    */     }
/* 653:    */   }
/* 654:    */   
/* 655:    */   static final class BranchStack
/* 656:    */   {
/* 657:768 */     Stack branchTargets = new Stack();
/* 658:769 */     Hashtable visitedTargets = new Hashtable();
/* 659:    */     
/* 660:    */     public void push(InstructionHandle target, int stackDepth)
/* 661:    */     {
/* 662:772 */       if (visited(target)) {
/* 663:773 */         return;
/* 664:    */       }
/* 665:775 */       this.branchTargets.push(visit(target, stackDepth));
/* 666:    */     }
/* 667:    */     
/* 668:    */     public MethodGen.BranchTarget pop()
/* 669:    */     {
/* 670:779 */       if (!this.branchTargets.empty())
/* 671:    */       {
/* 672:780 */         MethodGen.BranchTarget bt = (MethodGen.BranchTarget)this.branchTargets.pop();
/* 673:781 */         return bt;
/* 674:    */       }
/* 675:784 */       return null;
/* 676:    */     }
/* 677:    */     
/* 678:    */     private final MethodGen.BranchTarget visit(InstructionHandle target, int stackDepth)
/* 679:    */     {
/* 680:788 */       MethodGen.BranchTarget bt = new MethodGen.BranchTarget(target, stackDepth);
/* 681:789 */       this.visitedTargets.put(target, bt);
/* 682:    */       
/* 683:791 */       return bt;
/* 684:    */     }
/* 685:    */     
/* 686:    */     private final boolean visited(InstructionHandle target)
/* 687:    */     {
/* 688:795 */       return this.visitedTargets.get(target) != null;
/* 689:    */     }
/* 690:    */   }
/* 691:    */   
/* 692:    */   public static int getMaxStack(ConstantPoolGen cp, InstructionList il, CodeExceptionGen[] et)
/* 693:    */   {
/* 694:805 */     BranchStack branchTargets = new BranchStack();
/* 695:812 */     for (int i = 0; i < et.length; i++)
/* 696:    */     {
/* 697:813 */       InstructionHandle handler_pc = et[i].getHandlerPC();
/* 698:814 */       if (handler_pc != null) {
/* 699:815 */         branchTargets.push(handler_pc, 1);
/* 700:    */       }
/* 701:    */     }
/* 702:818 */     int stackDepth = 0;int maxStackDepth = 0;
/* 703:819 */     InstructionHandle ih = il.getStart();
/* 704:821 */     while (ih != null)
/* 705:    */     {
/* 706:822 */       Instruction instruction = ih.getInstruction();
/* 707:823 */       short opcode = instruction.getOpcode();
/* 708:824 */       int delta = instruction.produceStack(cp) - instruction.consumeStack(cp);
/* 709:    */       
/* 710:826 */       stackDepth += delta;
/* 711:827 */       if (stackDepth > maxStackDepth) {
/* 712:828 */         maxStackDepth = stackDepth;
/* 713:    */       }
/* 714:831 */       if ((instruction instanceof BranchInstruction))
/* 715:    */       {
/* 716:832 */         BranchInstruction branch = (BranchInstruction)instruction;
/* 717:833 */         if ((instruction instanceof Select))
/* 718:    */         {
/* 719:835 */           Select select = (Select)branch;
/* 720:836 */           InstructionHandle[] targets = select.getTargets();
/* 721:837 */           for (int i = 0; i < targets.length; i++) {
/* 722:838 */             branchTargets.push(targets[i], stackDepth);
/* 723:    */           }
/* 724:840 */           ih = null;
/* 725:    */         }
/* 726:841 */         else if (!(branch instanceof IfInstruction))
/* 727:    */         {
/* 728:844 */           if ((opcode == 168) || (opcode == 201)) {
/* 729:845 */             branchTargets.push(ih.getNext(), stackDepth - 1);
/* 730:    */           }
/* 731:846 */           ih = null;
/* 732:    */         }
/* 733:851 */         branchTargets.push(branch.getTarget(), stackDepth);
/* 734:    */       }
/* 735:854 */       else if ((opcode == 191) || (opcode == 169) || ((opcode >= 172) && (opcode <= 177)))
/* 736:    */       {
/* 737:856 */         ih = null;
/* 738:    */       }
/* 739:859 */       if (ih != null) {
/* 740:860 */         ih = ih.getNext();
/* 741:    */       }
/* 742:862 */       if (ih == null)
/* 743:    */       {
/* 744:863 */         BranchTarget bt = branchTargets.pop();
/* 745:864 */         if (bt != null)
/* 746:    */         {
/* 747:865 */           ih = bt.target;
/* 748:866 */           stackDepth = bt.stackDepth;
/* 749:    */         }
/* 750:    */       }
/* 751:    */     }
/* 752:871 */     return maxStackDepth;
/* 753:    */   }
/* 754:    */   
/* 755:    */   public void addObserver(MethodObserver o)
/* 756:    */   {
/* 757:879 */     if (this.observers == null) {
/* 758:880 */       this.observers = new ArrayList();
/* 759:    */     }
/* 760:882 */     this.observers.add(o);
/* 761:    */   }
/* 762:    */   
/* 763:    */   public void removeObserver(MethodObserver o)
/* 764:    */   {
/* 765:888 */     if (this.observers != null) {
/* 766:889 */       this.observers.remove(o);
/* 767:    */     }
/* 768:    */   }
/* 769:    */   
/* 770:    */   public void update()
/* 771:    */   {
/* 772:897 */     if (this.observers != null) {
/* 773:898 */       for (Iterator e = this.observers.iterator(); e.hasNext();) {
/* 774:899 */         ((MethodObserver)e.next()).notify(this);
/* 775:    */       }
/* 776:    */     }
/* 777:    */   }
/* 778:    */   
/* 779:    */   public final String toString()
/* 780:    */   {
/* 781:909 */     String access = Utility.accessToString(this.access_flags);
/* 782:910 */     String signature = Type.getMethodSignature(this.type, this.arg_types);
/* 783:    */     
/* 784:912 */     signature = Utility.methodSignatureToString(signature, this.name, access, true, getLocalVariableTable(this.cp));
/* 785:    */     
/* 786:    */ 
/* 787:915 */     StringBuffer buf = new StringBuffer(signature);
/* 788:917 */     if (this.throws_vec.size() > 0) {
/* 789:918 */       for (Iterator e = this.throws_vec.iterator(); e.hasNext();) {
/* 790:919 */         buf.append("\n\t\tthrows " + e.next());
/* 791:    */       }
/* 792:    */     }
/* 793:922 */     return buf.toString();
/* 794:    */   }
/* 795:    */   
/* 796:    */   public MethodGen copy(String class_name, ConstantPoolGen cp)
/* 797:    */   {
/* 798:928 */     Method m = ((MethodGen)clone()).getMethod();
/* 799:929 */     MethodGen mg = new MethodGen(m, class_name, this.cp);
/* 800:931 */     if (this.cp != cp)
/* 801:    */     {
/* 802:932 */       mg.setConstantPool(cp);
/* 803:933 */       mg.getInstructionList().replaceConstantPool(this.cp, cp);
/* 804:    */     }
/* 805:936 */     return mg;
/* 806:    */   }
/* 807:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.MethodGen
 * JD-Core Version:    0.7.0.1
 */