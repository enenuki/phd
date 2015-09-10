/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class StringLiteralSymbol
/*  4:   */   extends TokenSymbol
/*  5:   */ {
/*  6:   */   protected String label;
/*  7:   */   
/*  8:   */   public StringLiteralSymbol(String paramString)
/*  9:   */   {
/* 10:15 */     super(paramString);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String getLabel()
/* 14:   */   {
/* 15:19 */     return this.label;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setLabel(String paramString)
/* 19:   */   {
/* 20:23 */     this.label = paramString;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.StringLiteralSymbol
 * JD-Core Version:    0.7.0.1
 */