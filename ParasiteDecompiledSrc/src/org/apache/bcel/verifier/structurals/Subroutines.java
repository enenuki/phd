/*   1:    */ package org.apache.bcel.verifier.structurals;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.AbstractCollection;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Enumeration;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Hashtable;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import org.apache.bcel.generic.ASTORE;
/*  13:    */ import org.apache.bcel.generic.ATHROW;
/*  14:    */ import org.apache.bcel.generic.BranchInstruction;
/*  15:    */ import org.apache.bcel.generic.CodeExceptionGen;
/*  16:    */ import org.apache.bcel.generic.GotoInstruction;
/*  17:    */ import org.apache.bcel.generic.IndexedInstruction;
/*  18:    */ import org.apache.bcel.generic.Instruction;
/*  19:    */ import org.apache.bcel.generic.InstructionHandle;
/*  20:    */ import org.apache.bcel.generic.InstructionList;
/*  21:    */ import org.apache.bcel.generic.JsrInstruction;
/*  22:    */ import org.apache.bcel.generic.LocalVariableInstruction;
/*  23:    */ import org.apache.bcel.generic.MethodGen;
/*  24:    */ import org.apache.bcel.generic.RET;
/*  25:    */ import org.apache.bcel.generic.ReturnInstruction;
/*  26:    */ import org.apache.bcel.generic.Select;
/*  27:    */ import org.apache.bcel.generic.Type;
/*  28:    */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*  29:    */ import org.apache.bcel.verifier.exc.StructuralCodeConstraintException;
/*  30:    */ 
/*  31:    */ public class Subroutines
/*  32:    */ {
/*  33:    */   private class SubroutineImpl
/*  34:    */     implements Subroutine
/*  35:    */   {
/*  36:100 */     private final int UNSET = -1;
/*  37:108 */     private int localVariable = -1;
/*  38:111 */     private HashSet instructions = new HashSet();
/*  39:    */     
/*  40:    */     public boolean contains(InstructionHandle inst)
/*  41:    */     {
/*  42:117 */       return this.instructions.contains(inst);
/*  43:    */     }
/*  44:    */     
/*  45:124 */     private HashSet theJSRs = new HashSet();
/*  46:    */     private InstructionHandle theRET;
/*  47:    */     
/*  48:    */     public String toString()
/*  49:    */     {
/*  50:139 */       String ret = "Subroutine: Local variable is '" + this.localVariable + "', JSRs are '" + this.theJSRs + "', RET is '" + this.theRET + "', Instructions: '" + this.instructions.toString() + "'.";
/*  51:    */       
/*  52:141 */       ret = ret + " Accessed local variable slots: '";
/*  53:142 */       int[] alv = getAccessedLocalsIndices();
/*  54:143 */       for (int i = 0; i < alv.length; i++) {
/*  55:144 */         ret = ret + alv[i] + " ";
/*  56:    */       }
/*  57:146 */       ret = ret + "'.";
/*  58:    */       
/*  59:148 */       ret = ret + " Recursively (via subsub...routines) accessed local variable slots: '";
/*  60:149 */       alv = getRecursivelyAccessedLocalsIndices();
/*  61:150 */       for (int i = 0; i < alv.length; i++) {
/*  62:151 */         ret = ret + alv[i] + " ";
/*  63:    */       }
/*  64:153 */       ret = ret + "'.";
/*  65:    */       
/*  66:155 */       return ret;
/*  67:    */     }
/*  68:    */     
/*  69:    */     void setLeavingRET()
/*  70:    */     {
/*  71:163 */       if (this.localVariable == -1) {
/*  72:164 */         throw new AssertionViolatedException("setLeavingRET() called for top-level 'subroutine' or forgot to set local variable first.");
/*  73:    */       }
/*  74:166 */       Iterator iter = this.instructions.iterator();
/*  75:167 */       InstructionHandle ret = null;
/*  76:168 */       while (iter.hasNext())
/*  77:    */       {
/*  78:169 */         InstructionHandle actual = (InstructionHandle)iter.next();
/*  79:170 */         if ((actual.getInstruction() instanceof RET))
/*  80:    */         {
/*  81:171 */           if (ret != null) {
/*  82:172 */             throw new StructuralCodeConstraintException("Subroutine with more then one RET detected: '" + ret + "' and '" + actual + "'.");
/*  83:    */           }
/*  84:175 */           ret = actual;
/*  85:    */         }
/*  86:    */       }
/*  87:179 */       if (ret == null) {
/*  88:180 */         throw new StructuralCodeConstraintException("Subroutine without a RET detected.");
/*  89:    */       }
/*  90:182 */       if (((RET)ret.getInstruction()).getIndex() != this.localVariable) {
/*  91:183 */         throw new StructuralCodeConstraintException("Subroutine uses '" + ret + "' which does not match the correct local variable '" + this.localVariable + "'.");
/*  92:    */       }
/*  93:185 */       this.theRET = ret;
/*  94:    */     }
/*  95:    */     
/*  96:    */     public InstructionHandle[] getEnteringJsrInstructions()
/*  97:    */     {
/*  98:192 */       if (this == Subroutines.this.TOPLEVEL) {
/*  99:193 */         throw new AssertionViolatedException("getLeavingRET() called on top level pseudo-subroutine.");
/* 100:    */       }
/* 101:195 */       InstructionHandle[] jsrs = new InstructionHandle[this.theJSRs.size()];
/* 102:196 */       return (InstructionHandle[])this.theJSRs.toArray(jsrs);
/* 103:    */     }
/* 104:    */     
/* 105:    */     public void addEnteringJsrInstruction(InstructionHandle jsrInst)
/* 106:    */     {
/* 107:203 */       if ((jsrInst == null) || (!(jsrInst.getInstruction() instanceof JsrInstruction))) {
/* 108:204 */         throw new AssertionViolatedException("Expecting JsrInstruction InstructionHandle.");
/* 109:    */       }
/* 110:206 */       if (this.localVariable == -1) {
/* 111:207 */         throw new AssertionViolatedException("Set the localVariable first!");
/* 112:    */       }
/* 113:213 */       if (this.localVariable != ((ASTORE)((JsrInstruction)jsrInst.getInstruction()).getTarget().getInstruction()).getIndex()) {
/* 114:214 */         throw new AssertionViolatedException("Setting a wrong JsrInstruction.");
/* 115:    */       }
/* 116:217 */       this.theJSRs.add(jsrInst);
/* 117:    */     }
/* 118:    */     
/* 119:    */     public InstructionHandle getLeavingRET()
/* 120:    */     {
/* 121:224 */       if (this == Subroutines.this.TOPLEVEL) {
/* 122:225 */         throw new AssertionViolatedException("getLeavingRET() called on top level pseudo-subroutine.");
/* 123:    */       }
/* 124:227 */       return this.theRET;
/* 125:    */     }
/* 126:    */     
/* 127:    */     public InstructionHandle[] getInstructions()
/* 128:    */     {
/* 129:234 */       InstructionHandle[] ret = new InstructionHandle[this.instructions.size()];
/* 130:235 */       return (InstructionHandle[])this.instructions.toArray(ret);
/* 131:    */     }
/* 132:    */     
/* 133:    */     void addInstruction(InstructionHandle ih)
/* 134:    */     {
/* 135:244 */       if (this.theRET != null) {
/* 136:245 */         throw new AssertionViolatedException("All instructions must have been added before invoking setLeavingRET().");
/* 137:    */       }
/* 138:247 */       this.instructions.add(ih);
/* 139:    */     }
/* 140:    */     
/* 141:    */     public int[] getRecursivelyAccessedLocalsIndices()
/* 142:    */     {
/* 143:252 */       HashSet s = new HashSet();
/* 144:253 */       int[] lvs = getAccessedLocalsIndices();
/* 145:254 */       for (int j = 0; j < lvs.length; j++) {
/* 146:255 */         s.add(new Integer(lvs[j]));
/* 147:    */       }
/* 148:257 */       _getRecursivelyAccessedLocalsIndicesHelper(s, subSubs());
/* 149:258 */       int[] ret = new int[s.size()];
/* 150:259 */       Iterator i = s.iterator();
/* 151:260 */       int j = -1;
/* 152:261 */       while (i.hasNext())
/* 153:    */       {
/* 154:262 */         j++;
/* 155:263 */         ret[j] = ((Integer)i.next()).intValue();
/* 156:    */       }
/* 157:265 */       return ret;
/* 158:    */     }
/* 159:    */     
/* 160:    */     private void _getRecursivelyAccessedLocalsIndicesHelper(HashSet s, Subroutine[] subs)
/* 161:    */     {
/* 162:273 */       for (int i = 0; i < subs.length; i++)
/* 163:    */       {
/* 164:274 */         int[] lvs = subs[i].getAccessedLocalsIndices();
/* 165:275 */         for (int j = 0; j < lvs.length; j++) {
/* 166:276 */           s.add(new Integer(lvs[j]));
/* 167:    */         }
/* 168:278 */         if (subs[i].subSubs().length != 0) {
/* 169:279 */           _getRecursivelyAccessedLocalsIndicesHelper(s, subs[i].subSubs());
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:    */     
/* 174:    */     public int[] getAccessedLocalsIndices()
/* 175:    */     {
/* 176:289 */       HashSet acc = new HashSet();
/* 177:290 */       if ((this.theRET == null) && (this != Subroutines.this.TOPLEVEL)) {
/* 178:291 */         throw new AssertionViolatedException("This subroutine object must be built up completely before calculating accessed locals.");
/* 179:    */       }
/* 180:293 */       Iterator i = this.instructions.iterator();
/* 181:294 */       while (i.hasNext())
/* 182:    */       {
/* 183:295 */         InstructionHandle ih = (InstructionHandle)i.next();
/* 184:297 */         if (((ih.getInstruction() instanceof LocalVariableInstruction)) || ((ih.getInstruction() instanceof RET)))
/* 185:    */         {
/* 186:298 */           int idx = ((IndexedInstruction)ih.getInstruction()).getIndex();
/* 187:299 */           acc.add(new Integer(idx));
/* 188:    */           try
/* 189:    */           {
/* 190:304 */             if ((ih.getInstruction() instanceof LocalVariableInstruction))
/* 191:    */             {
/* 192:305 */               int s = ((LocalVariableInstruction)ih.getInstruction()).getType(null).getSize();
/* 193:306 */               if (s == 2) {
/* 194:306 */                 acc.add(new Integer(idx + 1));
/* 195:    */               }
/* 196:    */             }
/* 197:    */           }
/* 198:    */           catch (RuntimeException re)
/* 199:    */           {
/* 200:310 */             throw new AssertionViolatedException("Oops. BCEL did not like NULL as a ConstantPoolGen object.");
/* 201:    */           }
/* 202:    */         }
/* 203:    */       }
/* 204:315 */       int[] ret = new int[acc.size()];
/* 205:316 */       i = acc.iterator();
/* 206:317 */       int j = -1;
/* 207:318 */       while (i.hasNext())
/* 208:    */       {
/* 209:319 */         j++;
/* 210:320 */         ret[j] = ((Integer)i.next()).intValue();
/* 211:    */       }
/* 212:322 */       return ret;
/* 213:    */     }
/* 214:    */     
/* 215:    */     public Subroutine[] subSubs()
/* 216:    */     {
/* 217:329 */       HashSet h = new HashSet();
/* 218:    */       
/* 219:331 */       Iterator i = this.instructions.iterator();
/* 220:332 */       while (i.hasNext())
/* 221:    */       {
/* 222:333 */         Instruction inst = ((InstructionHandle)i.next()).getInstruction();
/* 223:334 */         if ((inst instanceof JsrInstruction))
/* 224:    */         {
/* 225:335 */           InstructionHandle targ = ((JsrInstruction)inst).getTarget();
/* 226:336 */           h.add(Subroutines.this.getSubroutine(targ));
/* 227:    */         }
/* 228:    */       }
/* 229:339 */       Subroutine[] ret = new Subroutine[h.size()];
/* 230:340 */       return (Subroutine[])h.toArray(ret);
/* 231:    */     }
/* 232:    */     
/* 233:    */     void setLocalVariable(int i)
/* 234:    */     {
/* 235:350 */       if (this.localVariable != -1) {
/* 236:351 */         throw new AssertionViolatedException("localVariable set twice.");
/* 237:    */       }
/* 238:354 */       this.localVariable = i;
/* 239:    */     }
/* 240:    */     
/* 241:    */     public SubroutineImpl() {}
/* 242:    */   }
/* 243:    */   
/* 244:371 */   private Hashtable subroutines = new Hashtable();
/* 245:    */   public final Subroutine TOPLEVEL;
/* 246:    */   
/* 247:    */   public Subroutines(MethodGen mg)
/* 248:    */   {
/* 249:388 */     InstructionHandle[] all = mg.getInstructionList().getInstructionHandles();
/* 250:389 */     CodeExceptionGen[] handlers = mg.getExceptionHandlers();
/* 251:    */     
/* 252:    */ 
/* 253:392 */     this.TOPLEVEL = new SubroutineImpl();
/* 254:    */     
/* 255:    */ 
/* 256:395 */     HashSet sub_leaders = new HashSet();
/* 257:396 */     InstructionHandle ih = all[0];
/* 258:397 */     for (int i = 0; i < all.length; i++)
/* 259:    */     {
/* 260:398 */       Instruction inst = all[i].getInstruction();
/* 261:399 */       if ((inst instanceof JsrInstruction)) {
/* 262:400 */         sub_leaders.add(((JsrInstruction)inst).getTarget());
/* 263:    */       }
/* 264:    */     }
/* 265:405 */     Iterator iter = sub_leaders.iterator();
/* 266:406 */     while (iter.hasNext())
/* 267:    */     {
/* 268:407 */       SubroutineImpl sr = new SubroutineImpl();
/* 269:408 */       InstructionHandle astore = (InstructionHandle)iter.next();
/* 270:409 */       sr.setLocalVariable(((ASTORE)astore.getInstruction()).getIndex());
/* 271:410 */       this.subroutines.put(astore, sr);
/* 272:    */     }
/* 273:414 */     this.subroutines.put(all[0], this.TOPLEVEL);
/* 274:415 */     sub_leaders.add(all[0]);
/* 275:422 */     for (int i = 0; i < all.length; i++)
/* 276:    */     {
/* 277:423 */       Instruction inst = all[i].getInstruction();
/* 278:424 */       if ((inst instanceof JsrInstruction))
/* 279:    */       {
/* 280:425 */         InstructionHandle leader = ((JsrInstruction)inst).getTarget();
/* 281:426 */         ((SubroutineImpl)getSubroutine(leader)).addEnteringJsrInstruction(all[i]);
/* 282:    */       }
/* 283:    */     }
/* 284:432 */     HashSet instructions_assigned = new HashSet();
/* 285:    */     
/* 286:434 */     Hashtable colors = new Hashtable();
/* 287:    */     
/* 288:436 */     iter = sub_leaders.iterator();
/* 289:437 */     while (iter.hasNext())
/* 290:    */     {
/* 291:439 */       InstructionHandle actual = (InstructionHandle)iter.next();
/* 292:441 */       for (int i = 0; i < all.length; i++) {
/* 293:442 */         colors.put(all[i], Color.white);
/* 294:    */       }
/* 295:444 */       colors.put(actual, Color.gray);
/* 296:    */       
/* 297:446 */       ArrayList Q = new ArrayList();
/* 298:447 */       Q.add(actual);
/* 299:450 */       if (actual == all[0]) {
/* 300:451 */         for (int j = 0; j < handlers.length; j++)
/* 301:    */         {
/* 302:452 */           colors.put(handlers[j].getHandlerPC(), Color.gray);
/* 303:453 */           Q.add(handlers[j].getHandlerPC());
/* 304:    */         }
/* 305:    */       }
/* 306:459 */       while (Q.size() != 0)
/* 307:    */       {
/* 308:460 */         InstructionHandle u = (InstructionHandle)Q.remove(0);
/* 309:461 */         InstructionHandle[] successors = getSuccessors(u);
/* 310:462 */         for (int i = 0; i < successors.length; i++) {
/* 311:463 */           if ((Color)colors.get(successors[i]) == Color.white)
/* 312:    */           {
/* 313:464 */             colors.put(successors[i], Color.gray);
/* 314:465 */             Q.add(successors[i]);
/* 315:    */           }
/* 316:    */         }
/* 317:468 */         colors.put(u, Color.black);
/* 318:    */       }
/* 319:471 */       for (int i = 0; i < all.length; i++) {
/* 320:472 */         if (colors.get(all[i]) == Color.black)
/* 321:    */         {
/* 322:473 */           ((SubroutineImpl)(actual == all[0] ? getTopLevel() : getSubroutine(actual))).addInstruction(all[i]);
/* 323:474 */           if (instructions_assigned.contains(all[i])) {
/* 324:475 */             throw new StructuralCodeConstraintException("Instruction '" + all[i] + "' is part of more than one subroutine (or of the top level and a subroutine).");
/* 325:    */           }
/* 326:478 */           instructions_assigned.add(all[i]);
/* 327:    */         }
/* 328:    */       }
/* 329:482 */       if (actual != all[0]) {
/* 330:483 */         ((SubroutineImpl)getSubroutine(actual)).setLeavingRET();
/* 331:    */       }
/* 332:    */     }
/* 333:489 */     for (int i = 0; i < handlers.length; i++)
/* 334:    */     {
/* 335:490 */       InstructionHandle _protected = handlers[i].getStartPC();
/* 336:491 */       while (_protected != handlers[i].getEndPC().getNext())
/* 337:    */       {
/* 338:492 */         Enumeration subs = this.subroutines.elements();
/* 339:493 */         while (subs.hasMoreElements())
/* 340:    */         {
/* 341:494 */           Subroutine sub = (Subroutine)subs.nextElement();
/* 342:495 */           if ((sub != this.subroutines.get(all[0])) && 
/* 343:496 */             (sub.contains(_protected))) {
/* 344:497 */             throw new StructuralCodeConstraintException("Subroutine instruction '" + _protected + "' is protected by an exception handler, '" + handlers[i] + "'. This is forbidden by the JustIce verifier due to its clear definition of subroutines.");
/* 345:    */           }
/* 346:    */         }
/* 347:501 */         _protected = _protected.getNext();
/* 348:    */       }
/* 349:    */     }
/* 350:511 */     noRecursiveCalls(getTopLevel(), new HashSet());
/* 351:    */   }
/* 352:    */   
/* 353:    */   private void noRecursiveCalls(Subroutine sub, HashSet set)
/* 354:    */   {
/* 355:527 */     Subroutine[] subs = sub.subSubs();
/* 356:529 */     for (int i = 0; i < subs.length; i++)
/* 357:    */     {
/* 358:530 */       int index = ((RET)subs[i].getLeavingRET().getInstruction()).getIndex();
/* 359:532 */       if (!set.add(new Integer(index)))
/* 360:    */       {
/* 361:534 */         SubroutineImpl si = (SubroutineImpl)subs[i];
/* 362:535 */         throw new StructuralCodeConstraintException("Subroutine with local variable '" + si.localVariable + "', JSRs '" + si.theJSRs + "', RET '" + si.theRET + "' is called by a subroutine which uses the same local variable index as itself; maybe even a recursive call? JustIce's clean definition of a subroutine forbids both.");
/* 363:    */       }
/* 364:538 */       noRecursiveCalls(subs[i], set);
/* 365:    */       
/* 366:540 */       set.remove(new Integer(index));
/* 367:    */     }
/* 368:    */   }
/* 369:    */   
/* 370:    */   public Subroutine getSubroutine(InstructionHandle leader)
/* 371:    */   {
/* 372:553 */     Subroutine ret = (Subroutine)this.subroutines.get(leader);
/* 373:555 */     if (ret == null) {
/* 374:556 */       throw new AssertionViolatedException("Subroutine requested for an InstructionHandle that is not a leader of a subroutine.");
/* 375:    */     }
/* 376:559 */     if (ret == this.TOPLEVEL) {
/* 377:560 */       throw new AssertionViolatedException("TOPLEVEL special subroutine requested; use getTopLevel().");
/* 378:    */     }
/* 379:563 */     return ret;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public Subroutine subroutineOf(InstructionHandle any)
/* 383:    */   {
/* 384:578 */     Iterator i = this.subroutines.values().iterator();
/* 385:579 */     while (i.hasNext())
/* 386:    */     {
/* 387:580 */       Subroutine s = (Subroutine)i.next();
/* 388:581 */       if (s.contains(any)) {
/* 389:581 */         return s;
/* 390:    */       }
/* 391:    */     }
/* 392:583 */     System.err.println("DEBUG: Please verify '" + any + "' lies in dead code.");
/* 393:584 */     return null;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public Subroutine getTopLevel()
/* 397:    */   {
/* 398:599 */     return this.TOPLEVEL;
/* 399:    */   }
/* 400:    */   
/* 401:    */   private static InstructionHandle[] getSuccessors(InstructionHandle instruction)
/* 402:    */   {
/* 403:608 */     InstructionHandle[] empty = new InstructionHandle[0];
/* 404:609 */     InstructionHandle[] single = new InstructionHandle[1];
/* 405:610 */     InstructionHandle[] pair = new InstructionHandle[2];
/* 406:    */     
/* 407:612 */     Instruction inst = instruction.getInstruction();
/* 408:614 */     if ((inst instanceof RET)) {
/* 409:615 */       return empty;
/* 410:    */     }
/* 411:619 */     if ((inst instanceof ReturnInstruction)) {
/* 412:620 */       return empty;
/* 413:    */     }
/* 414:625 */     if ((inst instanceof ATHROW)) {
/* 415:626 */       return empty;
/* 416:    */     }
/* 417:630 */     if ((inst instanceof JsrInstruction))
/* 418:    */     {
/* 419:631 */       single[0] = instruction.getNext();
/* 420:632 */       return single;
/* 421:    */     }
/* 422:635 */     if ((inst instanceof GotoInstruction))
/* 423:    */     {
/* 424:636 */       single[0] = ((GotoInstruction)inst).getTarget();
/* 425:637 */       return single;
/* 426:    */     }
/* 427:640 */     if ((inst instanceof BranchInstruction))
/* 428:    */     {
/* 429:641 */       if ((inst instanceof Select))
/* 430:    */       {
/* 431:644 */         InstructionHandle[] matchTargets = ((Select)inst).getTargets();
/* 432:645 */         InstructionHandle[] ret = new InstructionHandle[matchTargets.length + 1];
/* 433:646 */         ret[0] = ((Select)inst).getTarget();
/* 434:647 */         System.arraycopy(matchTargets, 0, ret, 1, matchTargets.length);
/* 435:648 */         return ret;
/* 436:    */       }
/* 437:651 */       pair[0] = instruction.getNext();
/* 438:652 */       pair[1] = ((BranchInstruction)inst).getTarget();
/* 439:653 */       return pair;
/* 440:    */     }
/* 441:658 */     single[0] = instruction.getNext();
/* 442:659 */     return single;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public String toString()
/* 446:    */   {
/* 447:666 */     return "---\n" + this.subroutines.toString() + "\n---\n";
/* 448:    */   }
/* 449:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.Subroutines
 * JD-Core Version:    0.7.0.1
 */