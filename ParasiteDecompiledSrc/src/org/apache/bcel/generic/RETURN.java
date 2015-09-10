/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class RETURN
/*  4:   */   extends ReturnInstruction
/*  5:   */ {
/*  6:   */   public RETURN()
/*  7:   */   {
/*  8:66 */     super((short)177);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:79 */     v.visitExceptionThrower(this);
/* 14:80 */     v.visitTypedInstruction(this);
/* 15:81 */     v.visitStackConsumer(this);
/* 16:82 */     v.visitReturnInstruction(this);
/* 17:83 */     v.visitRETURN(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.RETURN
 * JD-Core Version:    0.7.0.1
 */