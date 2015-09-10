/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DDIV
/*  4:   */   extends ArithmeticInstruction
/*  5:   */ {
/*  6:   */   public DDIV()
/*  7:   */   {
/*  8:69 */     super((short)111);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:82 */     v.visitTypedInstruction(this);
/* 14:83 */     v.visitStackProducer(this);
/* 15:84 */     v.visitStackConsumer(this);
/* 16:85 */     v.visitArithmeticInstruction(this);
/* 17:86 */     v.visitDDIV(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DDIV
 * JD-Core Version:    0.7.0.1
 */