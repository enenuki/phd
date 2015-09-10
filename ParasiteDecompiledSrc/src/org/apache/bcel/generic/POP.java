/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class POP
/*  4:   */   extends StackInstruction
/*  5:   */   implements PopInstruction
/*  6:   */ {
/*  7:   */   public POP()
/*  8:   */   {
/*  9:67 */     super((short)87);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public void accept(Visitor v)
/* 13:   */   {
/* 14:80 */     v.visitStackConsumer(this);
/* 15:81 */     v.visitPopInstruction(this);
/* 16:82 */     v.visitStackInstruction(this);
/* 17:83 */     v.visitPOP(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.POP
 * JD-Core Version:    0.7.0.1
 */