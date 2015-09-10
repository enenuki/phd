/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class SASTORE
/*  4:   */   extends ArrayInstruction
/*  5:   */   implements StackConsumer
/*  6:   */ {
/*  7:   */   public SASTORE()
/*  8:   */   {
/*  9:66 */     super((short)86);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public void accept(Visitor v)
/* 13:   */   {
/* 14:79 */     v.visitStackConsumer(this);
/* 15:80 */     v.visitExceptionThrower(this);
/* 16:81 */     v.visitTypedInstruction(this);
/* 17:82 */     v.visitArrayInstruction(this);
/* 18:83 */     v.visitSASTORE(this);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.SASTORE
 * JD-Core Version:    0.7.0.1
 */