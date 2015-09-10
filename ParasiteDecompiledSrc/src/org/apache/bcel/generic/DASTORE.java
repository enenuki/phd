/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DASTORE
/*  4:   */   extends ArrayInstruction
/*  5:   */   implements StackConsumer
/*  6:   */ {
/*  7:   */   public DASTORE()
/*  8:   */   {
/*  9:68 */     super((short)82);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public void accept(Visitor v)
/* 13:   */   {
/* 14:81 */     v.visitStackConsumer(this);
/* 15:82 */     v.visitExceptionThrower(this);
/* 16:83 */     v.visitTypedInstruction(this);
/* 17:84 */     v.visitArrayInstruction(this);
/* 18:85 */     v.visitDASTORE(this);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DASTORE
 * JD-Core Version:    0.7.0.1
 */