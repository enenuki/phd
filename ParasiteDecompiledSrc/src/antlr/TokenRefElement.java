/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class TokenRefElement
/*  4:   */   extends GrammarAtom
/*  5:   */ {
/*  6:   */   public TokenRefElement(Grammar paramGrammar, Token paramToken, boolean paramBoolean, int paramInt)
/*  7:   */   {
/*  8:16 */     super(paramGrammar, paramToken, paramInt);
/*  9:17 */     this.not = paramBoolean;
/* 10:18 */     TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(this.atomText);
/* 11:19 */     if (localTokenSymbol == null)
/* 12:   */     {
/* 13:20 */       paramGrammar.antlrTool.error("Undefined token symbol: " + this.atomText, this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 14:   */     }
/* 15:   */     else
/* 16:   */     {
/* 17:24 */       this.tokenType = localTokenSymbol.getTokenType();
/* 18:   */       
/* 19:   */ 
/* 20:   */ 
/* 21:28 */       setASTNodeType(localTokenSymbol.getASTNodeType());
/* 22:   */     }
/* 23:30 */     this.line = paramToken.getLine();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void generate()
/* 27:   */   {
/* 28:34 */     this.grammar.generator.gen(this);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Lookahead look(int paramInt)
/* 32:   */   {
/* 33:38 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenRefElement
 * JD-Core Version:    0.7.0.1
 */