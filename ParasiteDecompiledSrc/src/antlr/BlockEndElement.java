/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class BlockEndElement
/*  4:   */   extends AlternativeElement
/*  5:   */ {
/*  6:   */   protected boolean[] lock;
/*  7:   */   protected AlternativeBlock block;
/*  8:   */   
/*  9:   */   public BlockEndElement(Grammar paramGrammar)
/* 10:   */   {
/* 11:19 */     super(paramGrammar);
/* 12:20 */     this.lock = new boolean[paramGrammar.maxk + 1];
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Lookahead look(int paramInt)
/* 16:   */   {
/* 17:24 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String toString()
/* 21:   */   {
/* 22:29 */     return "";
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.BlockEndElement
 * JD-Core Version:    0.7.0.1
 */