/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class RuleEndElement
/*  4:   */   extends BlockEndElement
/*  5:   */ {
/*  6:   */   protected Lookahead[] cache;
/*  7:   */   protected boolean noFOLLOW;
/*  8:   */   
/*  9:   */   public RuleEndElement(Grammar paramGrammar)
/* 10:   */   {
/* 11:21 */     super(paramGrammar);
/* 12:22 */     this.cache = new Lookahead[paramGrammar.maxk + 1];
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Lookahead look(int paramInt)
/* 16:   */   {
/* 17:26 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String toString()
/* 21:   */   {
/* 22:31 */     return "";
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.RuleEndElement
 * JD-Core Version:    0.7.0.1
 */