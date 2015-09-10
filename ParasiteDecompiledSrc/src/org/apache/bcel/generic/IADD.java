/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class IADD
/*  4:   */   extends ArithmeticInstruction
/*  5:   */ {
/*  6:   */   public IADD()
/*  7:   */   {
/*  8:68 */     super((short)96);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:81 */     v.visitTypedInstruction(this);
/* 14:82 */     v.visitStackProducer(this);
/* 15:83 */     v.visitStackConsumer(this);
/* 16:84 */     v.visitArithmeticInstruction(this);
/* 17:85 */     v.visitIADD(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IADD
 * JD-Core Version:    0.7.0.1
 */