/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class TokenRangeElement
/*  4:   */   extends AlternativeElement
/*  5:   */ {
/*  6:   */   String label;
/*  7:12 */   protected int begin = 0;
/*  8:13 */   protected int end = 0;
/*  9:   */   protected String beginText;
/* 10:   */   protected String endText;
/* 11:   */   
/* 12:   */   public TokenRangeElement(Grammar paramGrammar, Token paramToken1, Token paramToken2, int paramInt)
/* 13:   */   {
/* 14:18 */     super(paramGrammar, paramToken1, paramInt);
/* 15:19 */     this.begin = this.grammar.tokenManager.getTokenSymbol(paramToken1.getText()).getTokenType();
/* 16:20 */     this.beginText = paramToken1.getText();
/* 17:21 */     this.end = this.grammar.tokenManager.getTokenSymbol(paramToken2.getText()).getTokenType();
/* 18:22 */     this.endText = paramToken2.getText();
/* 19:23 */     this.line = paramToken1.getLine();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void generate()
/* 23:   */   {
/* 24:27 */     this.grammar.generator.gen(this);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getLabel()
/* 28:   */   {
/* 29:31 */     return this.label;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Lookahead look(int paramInt)
/* 33:   */   {
/* 34:35 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setLabel(String paramString)
/* 38:   */   {
/* 39:39 */     this.label = paramString;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String toString()
/* 43:   */   {
/* 44:43 */     if (this.label != null) {
/* 45:44 */       return " " + this.label + ":" + this.beginText + ".." + this.endText;
/* 46:   */     }
/* 47:47 */     return " " + this.beginText + ".." + this.endText;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenRangeElement
 * JD-Core Version:    0.7.0.1
 */