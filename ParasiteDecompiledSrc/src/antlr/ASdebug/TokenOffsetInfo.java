/*  1:   */ package antlr.ASdebug;
/*  2:   */ 
/*  3:   */ public class TokenOffsetInfo
/*  4:   */ {
/*  5:   */   public final int beginOffset;
/*  6:   */   public final int length;
/*  7:   */   
/*  8:   */   public TokenOffsetInfo(int paramInt1, int paramInt2)
/*  9:   */   {
/* 10:14 */     this.beginOffset = paramInt1;
/* 11:15 */     this.length = paramInt2;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int getEndOffset()
/* 15:   */   {
/* 16:20 */     return this.beginOffset + this.length - 1;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ASdebug.TokenOffsetInfo
 * JD-Core Version:    0.7.0.1
 */