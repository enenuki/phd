/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ abstract class BlockWithImpliedExitPath
/*  4:   */   extends AlternativeBlock
/*  5:   */ {
/*  6:   */   protected int exitLookaheadDepth;
/*  7:15 */   protected Lookahead[] exitCache = new Lookahead[this.grammar.maxk + 1];
/*  8:   */   
/*  9:   */   public BlockWithImpliedExitPath(Grammar paramGrammar)
/* 10:   */   {
/* 11:18 */     super(paramGrammar);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public BlockWithImpliedExitPath(Grammar paramGrammar, Token paramToken)
/* 15:   */   {
/* 16:22 */     super(paramGrammar, paramToken, false);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.BlockWithImpliedExitPath
 * JD-Core Version:    0.7.0.1
 */