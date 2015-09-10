/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DRETURN
/*  4:   */   extends ReturnInstruction
/*  5:   */ {
/*  6:   */   public DRETURN()
/*  7:   */   {
/*  8:68 */     super((short)175);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:81 */     v.visitExceptionThrower(this);
/* 14:82 */     v.visitTypedInstruction(this);
/* 15:83 */     v.visitStackConsumer(this);
/* 16:84 */     v.visitReturnInstruction(this);
/* 17:85 */     v.visitDRETURN(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DRETURN
 * JD-Core Version:    0.7.0.1
 */