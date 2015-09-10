/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public abstract class StoreInstruction
/*  4:   */   extends LocalVariableInstruction
/*  5:   */   implements PopInstruction
/*  6:   */ {
/*  7:   */   StoreInstruction(short canon_tag, short c_tag)
/*  8:   */   {
/*  9:73 */     super(canon_tag, c_tag);
/* 10:   */   }
/* 11:   */   
/* 12:   */   protected StoreInstruction(short opcode, short c_tag, int n)
/* 13:   */   {
/* 14:82 */     super(opcode, c_tag, n);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void accept(Visitor v)
/* 18:   */   {
/* 19:94 */     v.visitStackConsumer(this);
/* 20:95 */     v.visitPopInstruction(this);
/* 21:96 */     v.visitStoreInstruction(this);
/* 22:97 */     v.visitTypedInstruction(this);
/* 23:98 */     v.visitLocalVariableInstruction(this);
/* 24:99 */     v.visitStoreInstruction(this);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.StoreInstruction
 * JD-Core Version:    0.7.0.1
 */