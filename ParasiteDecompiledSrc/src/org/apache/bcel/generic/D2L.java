/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class D2L
/*  4:   */   extends ConversionInstruction
/*  5:   */ {
/*  6:   */   public D2L()
/*  7:   */   {
/*  8:68 */     super((short)143);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:81 */     v.visitTypedInstruction(this);
/* 14:82 */     v.visitStackProducer(this);
/* 15:83 */     v.visitStackConsumer(this);
/* 16:84 */     v.visitConversionInstruction(this);
/* 17:85 */     v.visitD2L(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.D2L
 * JD-Core Version:    0.7.0.1
 */