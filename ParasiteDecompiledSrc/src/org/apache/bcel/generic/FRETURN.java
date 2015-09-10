/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class FRETURN
/*  4:   */   extends ReturnInstruction
/*  5:   */ {
/*  6:   */   public FRETURN()
/*  7:   */   {
/*  8:68 */     super((short)174);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:81 */     v.visitExceptionThrower(this);
/* 14:82 */     v.visitTypedInstruction(this);
/* 15:83 */     v.visitStackConsumer(this);
/* 16:84 */     v.visitReturnInstruction(this);
/* 17:85 */     v.visitFRETURN(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.FRETURN
 * JD-Core Version:    0.7.0.1
 */