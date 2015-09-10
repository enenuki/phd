/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.ExceptionConstants;
/*  4:   */ 
/*  5:   */ public class IREM
/*  6:   */   extends ArithmeticInstruction
/*  7:   */   implements ExceptionThrower
/*  8:   */ {
/*  9:   */   public IREM()
/* 10:   */   {
/* 11:68 */     super((short)112);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Class[] getExceptions()
/* 15:   */   {
/* 16:74 */     return new Class[] { ExceptionConstants.ARITHMETIC_EXCEPTION };
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void accept(Visitor v)
/* 20:   */   {
/* 21:87 */     v.visitExceptionThrower(this);
/* 22:88 */     v.visitTypedInstruction(this);
/* 23:89 */     v.visitStackProducer(this);
/* 24:90 */     v.visitStackConsumer(this);
/* 25:91 */     v.visitArithmeticInstruction(this);
/* 26:92 */     v.visitIREM(this);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IREM
 * JD-Core Version:    0.7.0.1
 */