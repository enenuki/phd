/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class LSUB
/*  4:   */   extends ArithmeticInstruction
/*  5:   */ {
/*  6:   */   public LSUB()
/*  7:   */   {
/*  8:67 */     super((short)101);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:80 */     v.visitTypedInstruction(this);
/* 14:81 */     v.visitStackProducer(this);
/* 15:82 */     v.visitStackConsumer(this);
/* 16:83 */     v.visitArithmeticInstruction(this);
/* 17:84 */     v.visitLSUB(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LSUB
 * JD-Core Version:    0.7.0.1
 */