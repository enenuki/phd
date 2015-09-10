/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class TokenSymbol
/*  4:   */   extends GrammarSymbol
/*  5:   */ {
/*  6:   */   protected int ttype;
/*  7:13 */   protected String paraphrase = null;
/*  8:   */   protected String ASTNodeType;
/*  9:   */   
/* 10:   */   public TokenSymbol(String paramString)
/* 11:   */   {
/* 12:19 */     super(paramString);
/* 13:20 */     this.ttype = 0;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getASTNodeType()
/* 17:   */   {
/* 18:24 */     return this.ASTNodeType;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setASTNodeType(String paramString)
/* 22:   */   {
/* 23:28 */     this.ASTNodeType = paramString;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getParaphrase()
/* 27:   */   {
/* 28:32 */     return this.paraphrase;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getTokenType()
/* 32:   */   {
/* 33:36 */     return this.ttype;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setParaphrase(String paramString)
/* 37:   */   {
/* 38:40 */     this.paraphrase = paramString;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setTokenType(int paramInt)
/* 42:   */   {
/* 43:44 */     this.ttype = paramInt;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenSymbol
 * JD-Core Version:    0.7.0.1
 */