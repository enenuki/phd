/*   1:    */ package org.apache.bcel.verifier.structurals;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import org.apache.bcel.generic.ATHROW;
/*   8:    */ import org.apache.bcel.generic.BranchInstruction;
/*   9:    */ import org.apache.bcel.generic.GotoInstruction;
/*  10:    */ import org.apache.bcel.generic.Instruction;
/*  11:    */ import org.apache.bcel.generic.InstructionHandle;
/*  12:    */ import org.apache.bcel.generic.InstructionList;
/*  13:    */ import org.apache.bcel.generic.JsrInstruction;
/*  14:    */ import org.apache.bcel.generic.MethodGen;
/*  15:    */ import org.apache.bcel.generic.RET;
/*  16:    */ import org.apache.bcel.generic.ReturnInstruction;
/*  17:    */ import org.apache.bcel.generic.Select;
/*  18:    */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*  19:    */ import org.apache.bcel.verifier.exc.StructuralCodeConstraintException;
/*  20:    */ import org.apache.bcel.verifier.exc.VerifierConstraintViolatedException;
/*  21:    */ 
/*  22:    */ public class ControlFlowGraph
/*  23:    */ {
/*  24:    */   private final MethodGen method_gen;
/*  25:    */   private final Subroutines subroutines;
/*  26:    */   private final ExceptionHandlers exceptionhandlers;
/*  27:    */   
/*  28:    */   private class InstructionContextImpl
/*  29:    */     implements InstructionContext
/*  30:    */   {
/*  31:    */     private int TAG;
/*  32:    */     private InstructionHandle instruction;
/*  33:    */     private HashMap inFrames;
/*  34:    */     private HashMap outFrames;
/*  35:104 */     private ArrayList executionPredecessors = null;
/*  36:    */     
/*  37:    */     public InstructionContextImpl(InstructionHandle inst)
/*  38:    */     {
/*  39:111 */       if (inst == null) {
/*  40:111 */         throw new AssertionViolatedException("Cannot instantiate InstructionContextImpl from NULL.");
/*  41:    */       }
/*  42:113 */       this.instruction = inst;
/*  43:114 */       this.inFrames = new HashMap();
/*  44:115 */       this.outFrames = new HashMap();
/*  45:    */     }
/*  46:    */     
/*  47:    */     public int getTag()
/*  48:    */     {
/*  49:120 */       return this.TAG;
/*  50:    */     }
/*  51:    */     
/*  52:    */     public void setTag(int tag)
/*  53:    */     {
/*  54:125 */       this.TAG = tag;
/*  55:    */     }
/*  56:    */     
/*  57:    */     public ExceptionHandler[] getExceptionHandlers()
/*  58:    */     {
/*  59:132 */       return ControlFlowGraph.this.exceptionhandlers.getExceptionHandlers(getInstruction());
/*  60:    */     }
/*  61:    */     
/*  62:    */     public Frame getOutFrame(ArrayList execChain)
/*  63:    */     {
/*  64:139 */       this.executionPredecessors = execChain;
/*  65:    */       
/*  66:    */ 
/*  67:    */ 
/*  68:143 */       InstructionContext jsr = lastExecutionJSR();
/*  69:    */       
/*  70:145 */       Frame org = (Frame)this.outFrames.get(jsr);
/*  71:147 */       if (org == null) {
/*  72:148 */         throw new AssertionViolatedException("outFrame not set! This:\n" + this + "\nExecutionChain: " + getExecutionChain() + "\nOutFrames: '" + this.outFrames + "'.");
/*  73:    */       }
/*  74:150 */       return org.getClone();
/*  75:    */     }
/*  76:    */     
/*  77:    */     public boolean execute(Frame inFrame, ArrayList execPreds, InstConstraintVisitor icv, ExecutionVisitor ev)
/*  78:    */     {
/*  79:170 */       this.executionPredecessors = ((ArrayList)execPreds.clone());
/*  80:173 */       if ((lastExecutionJSR() == null) && (ControlFlowGraph.this.subroutines.subroutineOf(getInstruction()) != ControlFlowGraph.this.subroutines.getTopLevel())) {
/*  81:174 */         throw new AssertionViolatedException("Huh?! Am I '" + this + "' part of a subroutine or not?");
/*  82:    */       }
/*  83:176 */       if ((lastExecutionJSR() != null) && (ControlFlowGraph.this.subroutines.subroutineOf(getInstruction()) == ControlFlowGraph.this.subroutines.getTopLevel())) {
/*  84:177 */         throw new AssertionViolatedException("Huh?! Am I '" + this + "' part of a subroutine or not?");
/*  85:    */       }
/*  86:180 */       Frame inF = (Frame)this.inFrames.get(lastExecutionJSR());
/*  87:181 */       if (inF == null)
/*  88:    */       {
/*  89:182 */         this.inFrames.put(lastExecutionJSR(), inFrame);
/*  90:183 */         inF = inFrame;
/*  91:    */       }
/*  92:    */       else
/*  93:    */       {
/*  94:186 */         if (inF.equals(inFrame)) {
/*  95:187 */           return false;
/*  96:    */         }
/*  97:189 */         if (!mergeInFrames(inFrame)) {
/*  98:190 */           return false;
/*  99:    */         }
/* 100:    */       }
/* 101:197 */       Frame workingFrame = inF.getClone();
/* 102:    */       try
/* 103:    */       {
/* 104:203 */         icv.setFrame(workingFrame);
/* 105:204 */         getInstruction().accept(icv);
/* 106:    */       }
/* 107:    */       catch (StructuralCodeConstraintException ce)
/* 108:    */       {
/* 109:207 */         ce.extendMessage("", "\nInstructionHandle: " + getInstruction() + "\n");
/* 110:208 */         ce.extendMessage("", "\nExecution Frame:\n" + workingFrame);
/* 111:209 */         extendMessageWithFlow(ce);
/* 112:210 */         throw ce;
/* 113:    */       }
/* 114:216 */       ev.setFrame(workingFrame);
/* 115:217 */       getInstruction().accept(ev);
/* 116:    */       
/* 117:219 */       this.outFrames.put(lastExecutionJSR(), workingFrame);
/* 118:    */       
/* 119:221 */       return true;
/* 120:    */     }
/* 121:    */     
/* 122:    */     public String toString()
/* 123:    */     {
/* 124:232 */       String ret = getInstruction().toString(false) + "\t[InstructionContext]";
/* 125:233 */       return ret;
/* 126:    */     }
/* 127:    */     
/* 128:    */     private boolean mergeInFrames(Frame inFrame)
/* 129:    */     {
/* 130:242 */       Frame inF = (Frame)this.inFrames.get(lastExecutionJSR());
/* 131:243 */       OperandStack oldstack = inF.getStack().getClone();
/* 132:244 */       LocalVariables oldlocals = inF.getLocals().getClone();
/* 133:    */       try
/* 134:    */       {
/* 135:246 */         inF.getStack().merge(inFrame.getStack());
/* 136:247 */         inF.getLocals().merge(inFrame.getLocals());
/* 137:    */       }
/* 138:    */       catch (StructuralCodeConstraintException sce)
/* 139:    */       {
/* 140:250 */         extendMessageWithFlow(sce);
/* 141:251 */         throw sce;
/* 142:    */       }
/* 143:253 */       if ((oldstack.equals(inF.getStack())) && (oldlocals.equals(inF.getLocals()))) {
/* 144:255 */         return false;
/* 145:    */       }
/* 146:258 */       return true;
/* 147:    */     }
/* 148:    */     
/* 149:    */     private String getExecutionChain()
/* 150:    */     {
/* 151:268 */       String s = toString();
/* 152:269 */       for (int i = this.executionPredecessors.size() - 1; i >= 0; i--) {
/* 153:270 */         s = this.executionPredecessors.get(i) + "\n" + s;
/* 154:    */       }
/* 155:272 */       return s;
/* 156:    */     }
/* 157:    */     
/* 158:    */     private void extendMessageWithFlow(StructuralCodeConstraintException e)
/* 159:    */     {
/* 160:282 */       String s = "Execution flow:\n";
/* 161:283 */       e.extendMessage("", s + getExecutionChain());
/* 162:    */     }
/* 163:    */     
/* 164:    */     public InstructionHandle getInstruction()
/* 165:    */     {
/* 166:290 */       return this.instruction;
/* 167:    */     }
/* 168:    */     
/* 169:    */     private InstructionContextImpl lastExecutionJSR()
/* 170:    */     {
/* 171:302 */       int size = this.executionPredecessors.size();
/* 172:303 */       int retcount = 0;
/* 173:305 */       for (int i = size - 1; i >= 0; i--)
/* 174:    */       {
/* 175:306 */         InstructionContextImpl current = (InstructionContextImpl)this.executionPredecessors.get(i);
/* 176:307 */         Instruction currentlast = current.getInstruction().getInstruction();
/* 177:308 */         if ((currentlast instanceof RET)) {
/* 178:308 */           retcount++;
/* 179:    */         }
/* 180:309 */         if ((currentlast instanceof JsrInstruction))
/* 181:    */         {
/* 182:310 */           retcount--;
/* 183:311 */           if (retcount == -1) {
/* 184:311 */             return current;
/* 185:    */           }
/* 186:    */         }
/* 187:    */       }
/* 188:314 */       return null;
/* 189:    */     }
/* 190:    */     
/* 191:    */     public InstructionContext[] getSuccessors()
/* 192:    */     {
/* 193:319 */       return ControlFlowGraph.this.contextsOf(_getSuccessors());
/* 194:    */     }
/* 195:    */     
/* 196:    */     private InstructionHandle[] _getSuccessors()
/* 197:    */     {
/* 198:330 */       InstructionHandle[] empty = new InstructionHandle[0];
/* 199:331 */       InstructionHandle[] single = new InstructionHandle[1];
/* 200:332 */       InstructionHandle[] pair = new InstructionHandle[2];
/* 201:    */       
/* 202:334 */       Instruction inst = getInstruction().getInstruction();
/* 203:336 */       if ((inst instanceof RET))
/* 204:    */       {
/* 205:337 */         Subroutine s = ControlFlowGraph.this.subroutines.subroutineOf(getInstruction());
/* 206:338 */         if (s == null) {
/* 207:339 */           throw new AssertionViolatedException("Asking for successors of a RET in dead code?!");
/* 208:    */         }
/* 209:342 */         throw new AssertionViolatedException("DID YOU REALLY WANT TO ASK FOR RET'S SUCCS?");
/* 210:    */       }
/* 211:354 */       if ((inst instanceof ReturnInstruction)) {
/* 212:355 */         return empty;
/* 213:    */       }
/* 214:360 */       if ((inst instanceof ATHROW)) {
/* 215:361 */         return empty;
/* 216:    */       }
/* 217:365 */       if ((inst instanceof JsrInstruction))
/* 218:    */       {
/* 219:366 */         single[0] = ((JsrInstruction)inst).getTarget();
/* 220:367 */         return single;
/* 221:    */       }
/* 222:370 */       if ((inst instanceof GotoInstruction))
/* 223:    */       {
/* 224:371 */         single[0] = ((GotoInstruction)inst).getTarget();
/* 225:372 */         return single;
/* 226:    */       }
/* 227:375 */       if ((inst instanceof BranchInstruction))
/* 228:    */       {
/* 229:376 */         if ((inst instanceof Select))
/* 230:    */         {
/* 231:379 */           InstructionHandle[] matchTargets = ((Select)inst).getTargets();
/* 232:380 */           InstructionHandle[] ret = new InstructionHandle[matchTargets.length + 1];
/* 233:381 */           ret[0] = ((Select)inst).getTarget();
/* 234:382 */           System.arraycopy(matchTargets, 0, ret, 1, matchTargets.length);
/* 235:383 */           return ret;
/* 236:    */         }
/* 237:386 */         pair[0] = getInstruction().getNext();
/* 238:387 */         pair[1] = ((BranchInstruction)inst).getTarget();
/* 239:388 */         return pair;
/* 240:    */       }
/* 241:393 */       single[0] = getInstruction().getNext();
/* 242:394 */       return single;
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:409 */   private Hashtable instructionContexts = new Hashtable();
/* 247:    */   
/* 248:    */   public ControlFlowGraph(MethodGen method_gen)
/* 249:    */   {
/* 250:415 */     this.subroutines = new Subroutines(method_gen);
/* 251:416 */     this.exceptionhandlers = new ExceptionHandlers(method_gen);
/* 252:    */     
/* 253:418 */     InstructionHandle[] instructionhandles = method_gen.getInstructionList().getInstructionHandles();
/* 254:419 */     for (int i = 0; i < instructionhandles.length; i++) {
/* 255:420 */       this.instructionContexts.put(instructionhandles[i], new InstructionContextImpl(instructionhandles[i]));
/* 256:    */     }
/* 257:423 */     this.method_gen = method_gen;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public InstructionContext contextOf(InstructionHandle inst)
/* 261:    */   {
/* 262:430 */     InstructionContext ic = (InstructionContext)this.instructionContexts.get(inst);
/* 263:431 */     if (ic == null) {
/* 264:432 */       throw new AssertionViolatedException("InstructionContext requested for an InstructionHandle that's not known!");
/* 265:    */     }
/* 266:434 */     return ic;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public InstructionContext[] contextsOf(InstructionHandle[] insts)
/* 270:    */   {
/* 271:442 */     InstructionContext[] ret = new InstructionContext[insts.length];
/* 272:443 */     for (int i = 0; i < insts.length; i++) {
/* 273:444 */       ret[i] = contextOf(insts[i]);
/* 274:    */     }
/* 275:446 */     return ret;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public InstructionContext[] getInstructionContexts()
/* 279:    */   {
/* 280:455 */     InstructionContext[] ret = new InstructionContext[this.instructionContexts.values().size()];
/* 281:456 */     return (InstructionContext[])this.instructionContexts.values().toArray(ret);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public boolean isDead(InstructionHandle i)
/* 285:    */   {
/* 286:464 */     return this.instructionContexts.containsKey(i);
/* 287:    */   }
/* 288:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.ControlFlowGraph
 * JD-Core Version:    0.7.0.1
 */