/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class IFNONNULL
/*  4:   */   extends IfInstruction
/*  5:   */ {
/*  6:   */   IFNONNULL() {}
/*  7:   */   
/*  8:   */   public IFNONNULL(InstructionHandle target)
/*  9:   */   {
/* 10:73 */     super((short)199, target);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public IfInstruction negate()
/* 14:   */   {
/* 15:80 */     return new IFNULL(this.target);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void accept(Visitor v)
/* 19:   */   {
/* 20:92 */     v.visitStackConsumer(this);
/* 21:93 */     v.visitBranchInstruction(this);
/* 22:94 */     v.visitIfInstruction(this);
/* 23:95 */     v.visitIFNONNULL(this);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IFNONNULL
 * JD-Core Version:    0.7.0.1
 */