/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public abstract class IfInstruction
/*  4:   */   extends BranchInstruction
/*  5:   */   implements StackConsumer
/*  6:   */ {
/*  7:   */   IfInstruction() {}
/*  8:   */   
/*  9:   */   protected IfInstruction(short opcode, InstructionHandle target)
/* 10:   */   {
/* 11:74 */     super(opcode, target);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public abstract IfInstruction negate();
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IfInstruction
 * JD-Core Version:    0.7.0.1
 */