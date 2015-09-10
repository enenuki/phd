/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class ILOAD
/*  4:   */   extends LoadInstruction
/*  5:   */ {
/*  6:   */   ILOAD()
/*  7:   */   {
/*  8:70 */     super((short)21, (short)26);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public ILOAD(int n)
/* 12:   */   {
/* 13:77 */     super((short)21, (short)26, n);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void accept(Visitor v)
/* 17:   */   {
/* 18:89 */     super.accept(v);
/* 19:90 */     v.visitILOAD(this);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ILOAD
 * JD-Core Version:    0.7.0.1
 */