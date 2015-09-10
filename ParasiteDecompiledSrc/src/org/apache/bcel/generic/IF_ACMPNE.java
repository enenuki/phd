/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class IF_ACMPNE
/*  4:   */   extends IfInstruction
/*  5:   */ {
/*  6:   */   IF_ACMPNE() {}
/*  7:   */   
/*  8:   */   public IF_ACMPNE(InstructionHandle target)
/*  9:   */   {
/* 10:73 */     super((short)166, target);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public IfInstruction negate()
/* 14:   */   {
/* 15:80 */     return new IF_ACMPEQ(this.target);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void accept(Visitor v)
/* 19:   */   {
/* 20:93 */     v.visitStackConsumer(this);
/* 21:94 */     v.visitBranchInstruction(this);
/* 22:95 */     v.visitIfInstruction(this);
/* 23:96 */     v.visitIF_ACMPNE(this);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IF_ACMPNE
 * JD-Core Version:    0.7.0.1
 */