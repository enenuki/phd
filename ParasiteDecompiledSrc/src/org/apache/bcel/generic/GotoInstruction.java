/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public abstract class GotoInstruction
/*  4:   */   extends BranchInstruction
/*  5:   */   implements UnconditionalBranch
/*  6:   */ {
/*  7:   */   GotoInstruction(short opcode, InstructionHandle target)
/*  8:   */   {
/*  9:67 */     super(opcode, target);
/* 10:   */   }
/* 11:   */   
/* 12:   */   GotoInstruction() {}
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.GotoInstruction
 * JD-Core Version:    0.7.0.1
 */