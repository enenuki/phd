/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public abstract class LoadInstruction
/*  4:   */   extends LocalVariableInstruction
/*  5:   */   implements PushInstruction
/*  6:   */ {
/*  7:   */   LoadInstruction(short canon_tag, short c_tag)
/*  8:   */   {
/*  9:73 */     super(canon_tag, c_tag);
/* 10:   */   }
/* 11:   */   
/* 12:   */   protected LoadInstruction(short opcode, short c_tag, int n)
/* 13:   */   {
/* 14:82 */     super(opcode, c_tag, n);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void accept(Visitor v)
/* 18:   */   {
/* 19:94 */     v.visitStackProducer(this);
/* 20:95 */     v.visitPushInstruction(this);
/* 21:96 */     v.visitTypedInstruction(this);
/* 22:97 */     v.visitLocalVariableInstruction(this);
/* 23:98 */     v.visitLoadInstruction(this);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LoadInstruction
 * JD-Core Version:    0.7.0.1
 */