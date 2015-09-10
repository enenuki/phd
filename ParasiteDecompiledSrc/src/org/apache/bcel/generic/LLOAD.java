/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class LLOAD
/*  4:   */   extends LoadInstruction
/*  5:   */ {
/*  6:   */   LLOAD()
/*  7:   */   {
/*  8:70 */     super((short)22, (short)30);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public LLOAD(int n)
/* 12:   */   {
/* 13:74 */     super((short)22, (short)30, n);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void accept(Visitor v)
/* 17:   */   {
/* 18:86 */     super.accept(v);
/* 19:87 */     v.visitLLOAD(this);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LLOAD
 * JD-Core Version:    0.7.0.1
 */