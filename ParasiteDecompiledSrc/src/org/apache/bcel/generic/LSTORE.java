/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class LSTORE
/*  4:   */   extends StoreInstruction
/*  5:   */ {
/*  6:   */   LSTORE()
/*  7:   */   {
/*  8:70 */     super((short)55, (short)63);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public LSTORE(int n)
/* 12:   */   {
/* 13:74 */     super((short)55, (short)63, n);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void accept(Visitor v)
/* 17:   */   {
/* 18:86 */     super.accept(v);
/* 19:87 */     v.visitLSTORE(this);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LSTORE
 * JD-Core Version:    0.7.0.1
 */