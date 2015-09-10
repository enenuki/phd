/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class FCMPL
/*  4:   */   extends Instruction
/*  5:   */   implements TypedInstruction, StackProducer, StackConsumer
/*  6:   */ {
/*  7:   */   public FCMPL()
/*  8:   */   {
/*  9:67 */     super((short)149, (short)1);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public Type getType(ConstantPoolGen cp)
/* 13:   */   {
/* 14:73 */     return Type.FLOAT;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void accept(Visitor v)
/* 18:   */   {
/* 19:86 */     v.visitTypedInstruction(this);
/* 20:87 */     v.visitStackProducer(this);
/* 21:88 */     v.visitStackConsumer(this);
/* 22:89 */     v.visitFCMPL(this);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.FCMPL
 * JD-Core Version:    0.7.0.1
 */