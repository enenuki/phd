/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class JavaBlockFinishingInfo
/*  4:   */ {
/*  5:   */   String postscript;
/*  6:   */   boolean generatedSwitch;
/*  7:   */   boolean generatedAnIf;
/*  8:   */   boolean needAnErrorClause;
/*  9:   */   
/* 10:   */   public JavaBlockFinishingInfo()
/* 11:   */   {
/* 12:23 */     this.postscript = null;
/* 13:24 */     this.generatedSwitch = (this.generatedSwitch = 0);
/* 14:25 */     this.needAnErrorClause = true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public JavaBlockFinishingInfo(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/* 18:   */   {
/* 19:29 */     this.postscript = paramString;
/* 20:30 */     this.generatedSwitch = paramBoolean1;
/* 21:31 */     this.generatedAnIf = paramBoolean2;
/* 22:32 */     this.needAnErrorClause = paramBoolean3;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.JavaBlockFinishingInfo
 * JD-Core Version:    0.7.0.1
 */