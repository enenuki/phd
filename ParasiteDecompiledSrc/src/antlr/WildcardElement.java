/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class WildcardElement
/*  4:   */   extends GrammarAtom
/*  5:   */ {
/*  6:   */   protected String label;
/*  7:   */   
/*  8:   */   public WildcardElement(Grammar paramGrammar, Token paramToken, int paramInt)
/*  9:   */   {
/* 10:14 */     super(paramGrammar, paramToken, paramInt);
/* 11:15 */     this.line = paramToken.getLine();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void generate()
/* 15:   */   {
/* 16:19 */     this.grammar.generator.gen(this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getLabel()
/* 20:   */   {
/* 21:23 */     return this.label;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Lookahead look(int paramInt)
/* 25:   */   {
/* 26:27 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setLabel(String paramString)
/* 30:   */   {
/* 31:31 */     this.label = paramString;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toString()
/* 35:   */   {
/* 36:35 */     String str = " ";
/* 37:36 */     if (this.label != null) {
/* 38:36 */       str = str + this.label + ":";
/* 39:   */     }
/* 40:37 */     return str + ".";
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.WildcardElement
 * JD-Core Version:    0.7.0.1
 */