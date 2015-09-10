/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class CSharpBlockFinishingInfo
/*  4:   */ {
/*  5:   */   String postscript;
/*  6:   */   boolean generatedSwitch;
/*  7:   */   boolean generatedAnIf;
/*  8:   */   boolean needAnErrorClause;
/*  9:   */   
/* 10:   */   public CSharpBlockFinishingInfo()
/* 11:   */   {
/* 12:29 */     this.postscript = null;
/* 13:30 */     this.generatedSwitch = (this.generatedSwitch = 0);
/* 14:31 */     this.needAnErrorClause = true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public CSharpBlockFinishingInfo(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/* 18:   */   {
/* 19:36 */     this.postscript = paramString;
/* 20:37 */     this.generatedSwitch = paramBoolean1;
/* 21:38 */     this.generatedAnIf = paramBoolean2;
/* 22:39 */     this.needAnErrorClause = paramBoolean3;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CSharpBlockFinishingInfo
 * JD-Core Version:    0.7.0.1
 */