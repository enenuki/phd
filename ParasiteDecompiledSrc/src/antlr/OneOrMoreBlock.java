/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class OneOrMoreBlock
/*  4:   */   extends BlockWithImpliedExitPath
/*  5:   */ {
/*  6:   */   public OneOrMoreBlock(Grammar paramGrammar)
/*  7:   */   {
/*  8:13 */     super(paramGrammar);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public OneOrMoreBlock(Grammar paramGrammar, Token paramToken)
/* 12:   */   {
/* 13:17 */     super(paramGrammar, paramToken);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void generate()
/* 17:   */   {
/* 18:21 */     this.grammar.generator.gen(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Lookahead look(int paramInt)
/* 22:   */   {
/* 23:25 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String toString()
/* 27:   */   {
/* 28:29 */     return super.toString() + "+";
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.OneOrMoreBlock
 * JD-Core Version:    0.7.0.1
 */