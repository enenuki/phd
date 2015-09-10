/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class CppBlockFinishingInfo
/*  4:   */ {
/*  5:   */   String postscript;
/*  6:   */   boolean generatedSwitch;
/*  7:   */   boolean generatedAnIf;
/*  8:   */   boolean needAnErrorClause;
/*  9:   */   
/* 10:   */   public CppBlockFinishingInfo()
/* 11:   */   {
/* 12:25 */     this.postscript = null;
/* 13:26 */     this.generatedSwitch = false;
/* 14:27 */     this.needAnErrorClause = true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public CppBlockFinishingInfo(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/* 18:   */   {
/* 19:30 */     this.postscript = paramString;
/* 20:31 */     this.generatedSwitch = paramBoolean1;
/* 21:32 */     this.generatedAnIf = paramBoolean2;
/* 22:33 */     this.needAnErrorClause = paramBoolean3;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CppBlockFinishingInfo
 * JD-Core Version:    0.7.0.1
 */