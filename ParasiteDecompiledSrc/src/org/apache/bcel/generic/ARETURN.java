/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class ARETURN
/*  4:   */   extends ReturnInstruction
/*  5:   */ {
/*  6:   */   public ARETURN()
/*  7:   */   {
/*  8:69 */     super((short)176);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:82 */     v.visitExceptionThrower(this);
/* 14:83 */     v.visitTypedInstruction(this);
/* 15:84 */     v.visitStackConsumer(this);
/* 16:85 */     v.visitReturnInstruction(this);
/* 17:86 */     v.visitARETURN(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ARETURN
 * JD-Core Version:    0.7.0.1
 */