/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.ExceptionConstants;
/*  4:   */ 
/*  5:   */ public class LREM
/*  6:   */   extends ArithmeticInstruction
/*  7:   */   implements ExceptionThrower
/*  8:   */ {
/*  9:   */   public LREM()
/* 10:   */   {
/* 11:66 */     super((short)113);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Class[] getExceptions()
/* 15:   */   {
/* 16:69 */     return new Class[] { ExceptionConstants.ARITHMETIC_EXCEPTION };
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void accept(Visitor v)
/* 20:   */   {
/* 21:81 */     v.visitExceptionThrower(this);
/* 22:82 */     v.visitTypedInstruction(this);
/* 23:83 */     v.visitStackProducer(this);
/* 24:84 */     v.visitStackConsumer(this);
/* 25:85 */     v.visitArithmeticInstruction(this);
/* 26:86 */     v.visitLREM(this);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LREM
 * JD-Core Version:    0.7.0.1
 */