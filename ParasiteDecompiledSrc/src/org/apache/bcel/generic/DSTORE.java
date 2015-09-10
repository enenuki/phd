/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DSTORE
/*  4:   */   extends StoreInstruction
/*  5:   */ {
/*  6:   */   DSTORE()
/*  7:   */   {
/*  8:70 */     super((short)57, (short)71);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public DSTORE(int n)
/* 12:   */   {
/* 13:77 */     super((short)57, (short)71, n);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void accept(Visitor v)
/* 17:   */   {
/* 18:89 */     super.accept(v);
/* 19:90 */     v.visitDSTORE(this);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DSTORE
 * JD-Core Version:    0.7.0.1
 */