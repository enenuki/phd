/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public abstract class StackInstruction
/*  4:   */   extends Instruction
/*  5:   */ {
/*  6:   */   StackInstruction() {}
/*  7:   */   
/*  8:   */   protected StackInstruction(short opcode)
/*  9:   */   {
/* 10:74 */     super(opcode, (short)1);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Type getType(ConstantPoolGen cp)
/* 14:   */   {
/* 15:80 */     return Type.UNKNOWN;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.StackInstruction
 * JD-Core Version:    0.7.0.1
 */