/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class IMPDEP2
/*  4:   */   extends Instruction
/*  5:   */ {
/*  6:   */   public IMPDEP2()
/*  7:   */   {
/*  8:65 */     super((short)255, (short)1);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public void accept(Visitor v)
/* 12:   */   {
/* 13:78 */     v.visitIMPDEP2(this);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IMPDEP2
 * JD-Core Version:    0.7.0.1
 */