/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class BREAKPOINT
/*  4:   */   extends Instruction
/*  5:   */ {
/*  6:   */   public BREAKPOINT()
/*  7:   */   {
/*  8:65 */     super((short)202, (short)1);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:78 */     v.visitBREAKPOINT(this);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.BREAKPOINT
 * JD-Core Version:    0.7.0.1
 */