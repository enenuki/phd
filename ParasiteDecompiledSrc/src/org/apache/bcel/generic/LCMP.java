/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class LCMP
/*  4:   */   extends Instruction
/*  5:   */ {
/*  6:   */   public LCMP()
/*  7:   */   {
/*  8:67 */     super((short)148, (short)1);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:80 */     v.visitLCMP(this);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LCMP
 * JD-Core Version:    0.7.0.1
 */