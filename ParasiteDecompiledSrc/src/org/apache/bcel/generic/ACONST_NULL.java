/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class ACONST_NULL
/*  4:   */   extends Instruction
/*  5:   */   implements PushInstruction, TypedInstruction
/*  6:   */ {
/*  7:   */   public ACONST_NULL()
/*  8:   */   {
/*  9:70 */     super((short)1, (short)1);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public Type getType(ConstantPoolGen cp)
/* 13:   */   {
/* 14:76 */     return Type.NULL;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void accept(Visitor v)
/* 18:   */   {
/* 19:89 */     v.visitStackProducer(this);
/* 20:90 */     v.visitPushInstruction(this);
/* 21:91 */     v.visitTypedInstruction(this);
/* 22:92 */     v.visitACONST_NULL(this);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ACONST_NULL
 * JD-Core Version:    0.7.0.1
 */