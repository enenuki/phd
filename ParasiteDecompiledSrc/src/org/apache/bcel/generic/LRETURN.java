/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class LRETURN
/*  4:   */   extends ReturnInstruction
/*  5:   */ {
/*  6:   */   public LRETURN()
/*  7:   */   {
/*  8:66 */     super((short)173);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:79 */     v.visitExceptionThrower(this);
/* 14:80 */     v.visitTypedInstruction(this);
/* 15:81 */     v.visitStackConsumer(this);
/* 16:82 */     v.visitReturnInstruction(this);
/* 17:83 */     v.visitLRETURN(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LRETURN
 * JD-Core Version:    0.7.0.1
 */