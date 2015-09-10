/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.ExceptionConstants;
/*  4:   */ 
/*  5:   */ public class LDIV
/*  6:   */   extends ArithmeticInstruction
/*  7:   */   implements ExceptionThrower
/*  8:   */ {
/*  9:   */   public LDIV()
/* 10:   */   {
/* 11:67 */     super((short)109);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Class[] getExceptions()
/* 15:   */   {
/* 16:71 */     return new Class[] { ExceptionConstants.ARITHMETIC_EXCEPTION };
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void accept(Visitor v)
/* 20:   */   {
/* 21:84 */     v.visitExceptionThrower(this);
/* 22:85 */     v.visitTypedInstruction(this);
/* 23:86 */     v.visitStackProducer(this);
/* 24:87 */     v.visitStackConsumer(this);
/* 25:88 */     v.visitArithmeticInstruction(this);
/* 26:89 */     v.visitLDIV(this);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LDIV
 * JD-Core Version:    0.7.0.1
 */