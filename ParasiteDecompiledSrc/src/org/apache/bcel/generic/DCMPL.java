/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DCMPL
/*  4:   */   extends Instruction
/*  5:   */   implements TypedInstruction, StackProducer, StackConsumer
/*  6:   */ {
/*  7:   */   public DCMPL()
/*  8:   */   {
/*  9:68 */     super((short)151, (short)1);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public Type getType(ConstantPoolGen cp)
/* 13:   */   {
/* 14:74 */     return Type.DOUBLE;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void accept(Visitor v)
/* 18:   */   {
/* 19:87 */     v.visitTypedInstruction(this);
/* 20:88 */     v.visitStackProducer(this);
/* 21:89 */     v.visitStackConsumer(this);
/* 22:90 */     v.visitDCMPL(this);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DCMPL
 * JD-Core Version:    0.7.0.1
 */