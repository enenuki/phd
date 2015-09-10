/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class CommonHiddenStreamToken
/*  4:   */   extends CommonToken
/*  5:   */ {
/*  6:   */   protected CommonHiddenStreamToken hiddenBefore;
/*  7:   */   protected CommonHiddenStreamToken hiddenAfter;
/*  8:   */   
/*  9:   */   public CommonHiddenStreamToken() {}
/* 10:   */   
/* 11:   */   public CommonHiddenStreamToken(int paramInt, String paramString)
/* 12:   */   {
/* 13:19 */     super(paramInt, paramString);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public CommonHiddenStreamToken(String paramString)
/* 17:   */   {
/* 18:23 */     super(paramString);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public CommonHiddenStreamToken getHiddenAfter()
/* 22:   */   {
/* 23:27 */     return this.hiddenAfter;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public CommonHiddenStreamToken getHiddenBefore()
/* 27:   */   {
/* 28:31 */     return this.hiddenBefore;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected void setHiddenAfter(CommonHiddenStreamToken paramCommonHiddenStreamToken)
/* 32:   */   {
/* 33:35 */     this.hiddenAfter = paramCommonHiddenStreamToken;
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected void setHiddenBefore(CommonHiddenStreamToken paramCommonHiddenStreamToken)
/* 37:   */   {
/* 38:39 */     this.hiddenBefore = paramCommonHiddenStreamToken;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CommonHiddenStreamToken
 * JD-Core Version:    0.7.0.1
 */