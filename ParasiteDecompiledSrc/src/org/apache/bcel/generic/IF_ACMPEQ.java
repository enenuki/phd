/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class IF_ACMPEQ
/*  4:   */   extends IfInstruction
/*  5:   */ {
/*  6:   */   IF_ACMPEQ() {}
/*  7:   */   
/*  8:   */   public IF_ACMPEQ(InstructionHandle target)
/*  9:   */   {
/* 10:73 */     super((short)165, target);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public IfInstruction negate()
/* 14:   */   {
/* 15:80 */     return new IF_ACMPNE(this.target);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void accept(Visitor v)
/* 19:   */   {
/* 20:92 */     v.visitStackConsumer(this);
/* 21:93 */     v.visitBranchInstruction(this);
/* 22:94 */     v.visitIfInstruction(this);
/* 23:95 */     v.visitIF_ACMPEQ(this);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IF_ACMPEQ
 * JD-Core Version:    0.7.0.1
 */