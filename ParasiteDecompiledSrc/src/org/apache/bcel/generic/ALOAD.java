/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public class ALOAD
/*  4:   */   extends LoadInstruction
/*  5:   */ {
/*  6:   */   ALOAD()
/*  7:   */   {
/*  8:70 */     super((short)25, (short)42);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public ALOAD(int n)
/* 12:   */   {
/* 13:77 */     super((short)25, (short)42, n);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void accept(Visitor v)
/* 17:   */   {
/* 18:89 */     super.accept(v);
/* 19:90 */     v.visitALOAD(this);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ALOAD
 * JD-Core Version:    0.7.0.1
 */