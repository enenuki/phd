/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class SWAP
/*  4:   */   extends StackInstruction
/*  5:   */   implements StackConsumer, StackProducer
/*  6:   */ {
/*  7:   */   public SWAP()
/*  8:   */   {
/*  9:66 */     super((short)95);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public void accept(Visitor v)
/* 13:   */   {
/* 14:79 */     v.visitStackConsumer(this);
/* 15:80 */     v.visitStackProducer(this);
/* 16:81 */     v.visitStackInstruction(this);
/* 17:82 */     v.visitSWAP(this);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.SWAP
 * JD-Core Version:    0.7.0.1
 */