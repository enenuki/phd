/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DUP_X1
/*  4:   */   extends StackInstruction
/*  5:   */ {
/*  6:   */   public DUP_X1()
/*  7:   */   {
/*  8:66 */     super((short)90);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:79 */     v.visitStackInstruction(this);
/* 14:80 */     v.visitDUP_X1(this);
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DUP_X1
 * JD-Core Version:    0.7.0.1
 */