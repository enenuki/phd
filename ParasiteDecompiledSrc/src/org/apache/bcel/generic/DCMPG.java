/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DCMPG
/*  4:   */   extends Instruction
/*  5:   */   implements TypedInstruction, StackProducer, StackConsumer
/*  6:   */ {
/*  7:   */   public DCMPG()
/*  8:   */   {
/*  9:69 */     super((short)152, (short)1);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public Type getType(ConstantPoolGen cp)
/* 13:   */   {
/* 14:75 */     return Type.DOUBLE;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void accept(Visitor v)
/* 18:   */   {
/* 19:88 */     v.visitTypedInstruction(this);
/* 20:89 */     v.visitStackProducer(this);
/* 21:90 */     v.visitStackConsumer(this);
/* 22:91 */     v.visitDCMPG(this);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DCMPG
 * JD-Core Version:    0.7.0.1
 */