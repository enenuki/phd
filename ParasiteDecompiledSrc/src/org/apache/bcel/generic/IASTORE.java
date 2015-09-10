/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class IASTORE
/*  4:   */   extends ArrayInstruction
/*  5:   */   implements StackConsumer
/*  6:   */ {
/*  7:   */   public IASTORE()
/*  8:   */   {
/*  9:69 */     super((short)79);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public void accept(Visitor v)
/* 13:   */   {
/* 14:82 */     v.visitStackConsumer(this);
/* 15:83 */     v.visitExceptionThrower(this);
/* 16:84 */     v.visitTypedInstruction(this);
/* 17:85 */     v.visitArrayInstruction(this);
/* 18:86 */     v.visitIASTORE(this);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IASTORE
 * JD-Core Version:    0.7.0.1
 */