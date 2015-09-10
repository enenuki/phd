/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DUP
/*  4:   */   extends StackInstruction
/*  5:   */   implements PushInstruction
/*  6:   */ {
/*  7:   */   public DUP()
/*  8:   */   {
/*  9:66 */     super((short)89);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public void accept(Visitor v)
/* 13:   */   {
/* 14:79 */     v.visitStackProducer(this);
/* 15:80 */     v.visitPushInstruction(this);
/* 16:81 */     v.visitStackInstruction(this);
/* 17:82 */     v.visitDUP(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DUP
 * JD-Core Version:    0.7.0.1
 */