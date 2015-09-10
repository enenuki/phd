/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class I2C
/*  4:   */   extends ConversionInstruction
/*  5:   */ {
/*  6:   */   public I2C()
/*  7:   */   {
/*  8:68 */     super((short)146);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:81 */     v.visitTypedInstruction(this);
/* 14:82 */     v.visitStackProducer(this);
/* 15:83 */     v.visitStackConsumer(this);
/* 16:84 */     v.visitConversionInstruction(this);
/* 17:85 */     v.visitI2C(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.I2C
 * JD-Core Version:    0.7.0.1
 */