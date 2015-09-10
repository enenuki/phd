/*   1:    */ package javassist.bytecode.analysis;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.CtMethod;
/*   8:    */ import javassist.NotFoundException;
/*   9:    */ import javassist.bytecode.BadBytecode;
/*  10:    */ import javassist.bytecode.CodeAttribute;
/*  11:    */ import javassist.bytecode.CodeIterator;
/*  12:    */ import javassist.bytecode.ConstPool;
/*  13:    */ import javassist.bytecode.Descriptor;
/*  14:    */ import javassist.bytecode.ExceptionTable;
/*  15:    */ import javassist.bytecode.MethodInfo;
/*  16:    */ import javassist.bytecode.Opcode;
/*  17:    */ 
/*  18:    */ public class Analyzer
/*  19:    */   implements Opcode
/*  20:    */ {
/*  21:    */   private final SubroutineScanner scanner;
/*  22:    */   private CtClass clazz;
/*  23:    */   private ExceptionInfo[] exceptions;
/*  24:    */   private Frame[] frames;
/*  25:    */   private Subroutine[] subroutines;
/*  26:    */   
/*  27:    */   public Analyzer()
/*  28:    */   {
/*  29: 86 */     this.scanner = new SubroutineScanner();
/*  30:    */   }
/*  31:    */   
/*  32:    */   private static class ExceptionInfo
/*  33:    */   {
/*  34:    */     private int end;
/*  35:    */     private int handler;
/*  36:    */     private int start;
/*  37:    */     private Type type;
/*  38:    */     
/*  39:    */     ExceptionInfo(int x0, int x1, int x2, Type x3, Analyzer.1 x4)
/*  40:    */     {
/*  41: 92 */       this(x0, x1, x2, x3);
/*  42:    */     }
/*  43:    */     
/*  44:    */     private ExceptionInfo(int start, int end, int handler, Type type)
/*  45:    */     {
/*  46: 99 */       this.start = start;
/*  47:100 */       this.end = end;
/*  48:101 */       this.handler = handler;
/*  49:102 */       this.type = type;
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Frame[] analyze(CtClass clazz, MethodInfo method)
/*  54:    */     throws BadBytecode
/*  55:    */   {
/*  56:122 */     this.clazz = clazz;
/*  57:123 */     CodeAttribute codeAttribute = method.getCodeAttribute();
/*  58:125 */     if (codeAttribute == null) {
/*  59:126 */       return null;
/*  60:    */     }
/*  61:128 */     int maxLocals = codeAttribute.getMaxLocals();
/*  62:129 */     int maxStack = codeAttribute.getMaxStack();
/*  63:130 */     int codeLength = codeAttribute.getCodeLength();
/*  64:    */     
/*  65:132 */     CodeIterator iter = codeAttribute.iterator();
/*  66:133 */     IntQueue queue = new IntQueue();
/*  67:    */     
/*  68:135 */     this.exceptions = buildExceptionInfo(method);
/*  69:136 */     this.subroutines = this.scanner.scan(method);
/*  70:    */     
/*  71:138 */     Executor executor = new Executor(clazz.getClassPool(), method.getConstPool());
/*  72:139 */     this.frames = new Frame[codeLength];
/*  73:140 */     this.frames[iter.lookAhead()] = firstFrame(method, maxLocals, maxStack);
/*  74:141 */     queue.add(iter.next());
/*  75:142 */     while (!queue.isEmpty()) {
/*  76:143 */       analyzeNextEntry(method, iter, queue, executor);
/*  77:    */     }
/*  78:146 */     return this.frames;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Frame[] analyze(CtMethod method)
/*  82:    */     throws BadBytecode
/*  83:    */   {
/*  84:164 */     return analyze(method.getDeclaringClass(), method.getMethodInfo2());
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void analyzeNextEntry(MethodInfo method, CodeIterator iter, IntQueue queue, Executor executor)
/*  88:    */     throws BadBytecode
/*  89:    */   {
/*  90:169 */     int pos = queue.take();
/*  91:170 */     iter.move(pos);
/*  92:171 */     iter.next();
/*  93:    */     
/*  94:173 */     Frame frame = this.frames[pos].copy();
/*  95:174 */     Subroutine subroutine = this.subroutines[pos];
/*  96:    */     try
/*  97:    */     {
/*  98:177 */       executor.execute(method, pos, iter, frame, subroutine);
/*  99:    */     }
/* 100:    */     catch (RuntimeException e)
/* 101:    */     {
/* 102:179 */       throw new BadBytecode(e.getMessage() + "[pos = " + pos + "]", e);
/* 103:    */     }
/* 104:182 */     int opcode = iter.byteAt(pos);
/* 105:184 */     if (opcode == 170)
/* 106:    */     {
/* 107:185 */       mergeTableSwitch(queue, pos, iter, frame);
/* 108:    */     }
/* 109:186 */     else if (opcode == 171)
/* 110:    */     {
/* 111:187 */       mergeLookupSwitch(queue, pos, iter, frame);
/* 112:    */     }
/* 113:188 */     else if (opcode == 169)
/* 114:    */     {
/* 115:189 */       mergeRet(queue, iter, pos, frame, subroutine);
/* 116:    */     }
/* 117:190 */     else if (Util.isJumpInstruction(opcode))
/* 118:    */     {
/* 119:191 */       int target = Util.getJumpTarget(pos, iter);
/* 120:193 */       if (Util.isJsr(opcode)) {
/* 121:195 */         mergeJsr(queue, this.frames[pos], this.subroutines[target], pos, lookAhead(iter, pos));
/* 122:196 */       } else if (!Util.isGoto(opcode)) {
/* 123:197 */         merge(queue, frame, lookAhead(iter, pos));
/* 124:    */       }
/* 125:200 */       merge(queue, frame, target);
/* 126:    */     }
/* 127:201 */     else if ((opcode != 191) && (!Util.isReturn(opcode)))
/* 128:    */     {
/* 129:203 */       merge(queue, frame, lookAhead(iter, pos));
/* 130:    */     }
/* 131:209 */     mergeExceptionHandlers(queue, method, pos, frame);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private ExceptionInfo[] buildExceptionInfo(MethodInfo method)
/* 135:    */   {
/* 136:213 */     ConstPool constPool = method.getConstPool();
/* 137:214 */     ClassPool classes = this.clazz.getClassPool();
/* 138:    */     
/* 139:216 */     ExceptionTable table = method.getCodeAttribute().getExceptionTable();
/* 140:217 */     ExceptionInfo[] exceptions = new ExceptionInfo[table.size()];
/* 141:218 */     for (int i = 0; i < table.size(); i++)
/* 142:    */     {
/* 143:219 */       int index = table.catchType(i);
/* 144:    */       Type type;
/* 145:    */       try
/* 146:    */       {
/* 147:222 */         type = index == 0 ? Type.THROWABLE : Type.get(classes.get(constPool.getClassInfo(index)));
/* 148:    */       }
/* 149:    */       catch (NotFoundException e)
/* 150:    */       {
/* 151:224 */         throw new IllegalStateException(e.getMessage());
/* 152:    */       }
/* 153:227 */       exceptions[i] = new ExceptionInfo(table.startPc(i), table.endPc(i), table.handlerPc(i), type, null);
/* 154:    */     }
/* 155:230 */     return exceptions;
/* 156:    */   }
/* 157:    */   
/* 158:    */   private Frame firstFrame(MethodInfo method, int maxLocals, int maxStack)
/* 159:    */   {
/* 160:234 */     int pos = 0;
/* 161:    */     
/* 162:236 */     Frame first = new Frame(maxLocals, maxStack);
/* 163:237 */     if ((method.getAccessFlags() & 0x8) == 0) {
/* 164:238 */       first.setLocal(pos++, Type.get(this.clazz));
/* 165:    */     }
/* 166:    */     CtClass[] parameters;
/* 167:    */     try
/* 168:    */     {
/* 169:243 */       parameters = Descriptor.getParameterTypes(method.getDescriptor(), this.clazz.getClassPool());
/* 170:    */     }
/* 171:    */     catch (NotFoundException e)
/* 172:    */     {
/* 173:245 */       throw new RuntimeException(e);
/* 174:    */     }
/* 175:248 */     for (int i = 0; i < parameters.length; i++)
/* 176:    */     {
/* 177:249 */       Type type = zeroExtend(Type.get(parameters[i]));
/* 178:250 */       first.setLocal(pos++, type);
/* 179:251 */       if (type.getSize() == 2) {
/* 180:252 */         first.setLocal(pos++, Type.TOP);
/* 181:    */       }
/* 182:    */     }
/* 183:255 */     return first;
/* 184:    */   }
/* 185:    */   
/* 186:    */   private int getNext(CodeIterator iter, int of, int restore)
/* 187:    */     throws BadBytecode
/* 188:    */   {
/* 189:259 */     iter.move(of);
/* 190:260 */     iter.next();
/* 191:261 */     int next = iter.lookAhead();
/* 192:262 */     iter.move(restore);
/* 193:263 */     iter.next();
/* 194:    */     
/* 195:265 */     return next;
/* 196:    */   }
/* 197:    */   
/* 198:    */   private int lookAhead(CodeIterator iter, int pos)
/* 199:    */     throws BadBytecode
/* 200:    */   {
/* 201:269 */     if (!iter.hasNext()) {
/* 202:270 */       throw new BadBytecode("Execution falls off end! [pos = " + pos + "]");
/* 203:    */     }
/* 204:272 */     return iter.lookAhead();
/* 205:    */   }
/* 206:    */   
/* 207:    */   private void merge(IntQueue queue, Frame frame, int target)
/* 208:    */   {
/* 209:277 */     Frame old = this.frames[target];
/* 210:    */     boolean changed;
/* 211:    */     boolean changed;
/* 212:280 */     if (old == null)
/* 213:    */     {
/* 214:281 */       this.frames[target] = frame.copy();
/* 215:282 */       changed = true;
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:284 */       changed = old.merge(frame);
/* 220:    */     }
/* 221:287 */     if (changed) {
/* 222:288 */       queue.add(target);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   private void mergeExceptionHandlers(IntQueue queue, MethodInfo method, int pos, Frame frame)
/* 227:    */   {
/* 228:293 */     for (int i = 0; i < this.exceptions.length; i++)
/* 229:    */     {
/* 230:294 */       ExceptionInfo exception = this.exceptions[i];
/* 231:297 */       if ((pos >= exception.start) && (pos < exception.end))
/* 232:    */       {
/* 233:298 */         Frame newFrame = frame.copy();
/* 234:299 */         newFrame.clearStack();
/* 235:300 */         newFrame.push(exception.type);
/* 236:301 */         merge(queue, newFrame, exception.handler);
/* 237:    */       }
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   private void mergeJsr(IntQueue queue, Frame frame, Subroutine sub, int pos, int next)
/* 242:    */     throws BadBytecode
/* 243:    */   {
/* 244:307 */     if (sub == null) {
/* 245:308 */       throw new BadBytecode("No subroutine at jsr target! [pos = " + pos + "]");
/* 246:    */     }
/* 247:310 */     Frame old = this.frames[next];
/* 248:311 */     boolean changed = false;
/* 249:313 */     if (old == null)
/* 250:    */     {
/* 251:314 */       old = this.frames[next] =  = frame.copy();
/* 252:315 */       changed = true;
/* 253:    */     }
/* 254:    */     else
/* 255:    */     {
/* 256:317 */       for (int i = 0; i < frame.localsLength(); i++) {
/* 257:319 */         if (!sub.isAccessed(i))
/* 258:    */         {
/* 259:320 */           Type oldType = old.getLocal(i);
/* 260:321 */           Type newType = frame.getLocal(i);
/* 261:322 */           if (oldType == null)
/* 262:    */           {
/* 263:323 */             old.setLocal(i, newType);
/* 264:324 */             changed = true;
/* 265:    */           }
/* 266:    */           else
/* 267:    */           {
/* 268:328 */             newType = oldType.merge(newType);
/* 269:    */             
/* 270:330 */             old.setLocal(i, newType);
/* 271:331 */             if ((!newType.equals(oldType)) || (newType.popChanged())) {
/* 272:332 */               changed = true;
/* 273:    */             }
/* 274:    */           }
/* 275:    */         }
/* 276:    */       }
/* 277:    */     }
/* 278:337 */     if (!old.isJsrMerged())
/* 279:    */     {
/* 280:338 */       old.setJsrMerged(true);
/* 281:339 */       changed = true;
/* 282:    */     }
/* 283:342 */     if ((changed) && (old.isRetMerged())) {
/* 284:343 */       queue.add(next);
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   private void mergeLookupSwitch(IntQueue queue, int pos, CodeIterator iter, Frame frame)
/* 289:    */     throws BadBytecode
/* 290:    */   {
/* 291:348 */     int index = (pos & 0xFFFFFFFC) + 4;
/* 292:    */     
/* 293:350 */     merge(queue, frame, pos + iter.s32bitAt(index));
/* 294:351 */     index += 4;int npairs = iter.s32bitAt(index);
/* 295:352 */     index += 4;int end = npairs * 8 + index;
/* 296:355 */     for (index += 4; index < end; index += 8)
/* 297:    */     {
/* 298:356 */       int target = iter.s32bitAt(index) + pos;
/* 299:357 */       merge(queue, frame, target);
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   private void mergeRet(IntQueue queue, CodeIterator iter, int pos, Frame frame, Subroutine subroutine)
/* 304:    */     throws BadBytecode
/* 305:    */   {
/* 306:362 */     if (subroutine == null) {
/* 307:363 */       throw new BadBytecode("Ret on no subroutine! [pos = " + pos + "]");
/* 308:    */     }
/* 309:365 */     Iterator callerIter = subroutine.callers().iterator();
/* 310:366 */     while (callerIter.hasNext())
/* 311:    */     {
/* 312:367 */       int caller = ((Integer)callerIter.next()).intValue();
/* 313:368 */       int returnLoc = getNext(iter, caller, pos);
/* 314:369 */       boolean changed = false;
/* 315:    */       
/* 316:371 */       Frame old = this.frames[returnLoc];
/* 317:372 */       if (old == null)
/* 318:    */       {
/* 319:373 */         old = this.frames[returnLoc] =  = frame.copyStack();
/* 320:374 */         changed = true;
/* 321:    */       }
/* 322:    */       else
/* 323:    */       {
/* 324:376 */         changed = old.mergeStack(frame);
/* 325:    */       }
/* 326:379 */       for (Iterator i = subroutine.accessed().iterator(); i.hasNext();)
/* 327:    */       {
/* 328:380 */         int index = ((Integer)i.next()).intValue();
/* 329:381 */         Type oldType = old.getLocal(index);
/* 330:382 */         Type newType = frame.getLocal(index);
/* 331:383 */         if (oldType != newType)
/* 332:    */         {
/* 333:384 */           old.setLocal(index, newType);
/* 334:385 */           changed = true;
/* 335:    */         }
/* 336:    */       }
/* 337:389 */       if (!old.isRetMerged())
/* 338:    */       {
/* 339:390 */         old.setRetMerged(true);
/* 340:391 */         changed = true;
/* 341:    */       }
/* 342:394 */       if ((changed) && (old.isJsrMerged())) {
/* 343:395 */         queue.add(returnLoc);
/* 344:    */       }
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   private void mergeTableSwitch(IntQueue queue, int pos, CodeIterator iter, Frame frame)
/* 349:    */     throws BadBytecode
/* 350:    */   {
/* 351:402 */     int index = (pos & 0xFFFFFFFC) + 4;
/* 352:    */     
/* 353:404 */     merge(queue, frame, pos + iter.s32bitAt(index));
/* 354:405 */     index += 4;int low = iter.s32bitAt(index);
/* 355:406 */     index += 4;int high = iter.s32bitAt(index);
/* 356:407 */     index += 4;int end = (high - low + 1) * 4 + index;
/* 357:410 */     for (; index < end; index += 4)
/* 358:    */     {
/* 359:411 */       int target = iter.s32bitAt(index) + pos;
/* 360:412 */       merge(queue, frame, target);
/* 361:    */     }
/* 362:    */   }
/* 363:    */   
/* 364:    */   private Type zeroExtend(Type type)
/* 365:    */   {
/* 366:417 */     if ((type == Type.SHORT) || (type == Type.BYTE) || (type == Type.CHAR) || (type == Type.BOOLEAN)) {
/* 367:418 */       return Type.INTEGER;
/* 368:    */     }
/* 369:420 */     return type;
/* 370:    */   }
/* 371:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.Analyzer
 * JD-Core Version:    0.7.0.1
 */