/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DADD
/*  4:   */   extends ArithmeticInstruction
/*  5:   */ {
/*  6:   */   public DADD()
/*  7:   */   {
/*  8:69 */     super((short)99);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:82 */     v.visitTypedInstruction(this);
/* 14:83 */     v.visitStackProducer(this);
/* 15:84 */     v.visitStackConsumer(this);
/* 16:85 */     v.visitArithmeticInstruction(this);
/* 17:86 */     v.visitDADD(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DADD
 * JD-Core Version:    0.7.0.1
 */