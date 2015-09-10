/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class ActionElement
/*  4:   */   extends AlternativeElement
/*  5:   */ {
/*  6:   */   protected String actionText;
/*  7:12 */   protected boolean isSemPred = false;
/*  8:   */   
/*  9:   */   public ActionElement(Grammar paramGrammar, Token paramToken)
/* 10:   */   {
/* 11:16 */     super(paramGrammar);
/* 12:17 */     this.actionText = paramToken.getText();
/* 13:18 */     this.line = paramToken.getLine();
/* 14:19 */     this.column = paramToken.getColumn();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void generate()
/* 18:   */   {
/* 19:23 */     this.grammar.generator.gen(this);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Lookahead look(int paramInt)
/* 23:   */   {
/* 24:27 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String toString()
/* 28:   */   {
/* 29:31 */     return " " + this.actionText + (this.isSemPred ? "?" : "");
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ActionElement
 * JD-Core Version:    0.7.0.1
 */