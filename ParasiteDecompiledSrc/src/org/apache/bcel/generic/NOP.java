/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class NOP
/*  4:   */   extends Instruction
/*  5:   */ {
/*  6:   */   public NOP()
/*  7:   */   {
/*  8:65 */     super((short)0, (short)1);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:78 */     v.visitNOP(this);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.NOP
 * JD-Core Version:    0.7.0.1
 */