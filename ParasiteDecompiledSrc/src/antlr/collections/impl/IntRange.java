/*  1:   */ package antlr.collections.impl;
/*  2:   */ 
/*  3:   */ public class IntRange
/*  4:   */ {
/*  5:   */   int begin;
/*  6:   */   int end;
/*  7:   */   
/*  8:   */   public IntRange(int paramInt1, int paramInt2)
/*  9:   */   {
/* 10:15 */     this.begin = paramInt1;
/* 11:16 */     this.end = paramInt2;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String toString()
/* 15:   */   {
/* 16:20 */     return this.begin + ".." + this.end;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.IntRange
 * JD-Core Version:    0.7.0.1
 */