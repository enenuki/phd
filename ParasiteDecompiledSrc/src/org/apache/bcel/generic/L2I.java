/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class L2I
/*  4:   */   extends ConversionInstruction
/*  5:   */ {
/*  6:   */   public L2I()
/*  7:   */   {
/*  8:66 */     super((short)136);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:79 */     v.visitTypedInstruction(this);
/* 14:80 */     v.visitStackProducer(this);
/* 15:81 */     v.visitStackConsumer(this);
/* 16:82 */     v.visitConversionInstruction(this);
/* 17:83 */     v.visitL2I(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.L2I
 * JD-Core Version:    0.7.0.1
 */