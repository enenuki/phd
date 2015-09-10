/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class DLOAD
/*  4:   */   extends LoadInstruction
/*  5:   */ {
/*  6:   */   DLOAD()
/*  7:   */   {
/*  8:70 */     super((short)24, (short)38);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public DLOAD(int n)
/* 12:   */   {
/* 13:77 */     super((short)24, (short)38, n);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void accept(Visitor v)
/* 17:   */   {
/* 18:89 */     super.accept(v);
/* 19:90 */     v.visitDLOAD(this);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DLOAD
 * JD-Core Version:    0.7.0.1
 */