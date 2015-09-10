/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.impl.BitSet;
/*  4:   */ 
/*  5:   */ class CharRangeElement
/*  6:   */   extends AlternativeElement
/*  7:   */ {
/*  8:   */   String label;
/*  9:12 */   protected char begin = '\000';
/* 10:13 */   protected char end = '\000';
/* 11:   */   protected String beginText;
/* 12:   */   protected String endText;
/* 13:   */   
/* 14:   */   public CharRangeElement(LexerGrammar paramLexerGrammar, Token paramToken1, Token paramToken2, int paramInt)
/* 15:   */   {
/* 16:19 */     super(paramLexerGrammar);
/* 17:20 */     this.begin = ((char)ANTLRLexer.tokenTypeForCharLiteral(paramToken1.getText()));
/* 18:21 */     this.beginText = paramToken1.getText();
/* 19:22 */     this.end = ((char)ANTLRLexer.tokenTypeForCharLiteral(paramToken2.getText()));
/* 20:23 */     this.endText = paramToken2.getText();
/* 21:24 */     this.line = paramToken1.getLine();
/* 22:26 */     for (int i = this.begin; i <= this.end; i++) {
/* 23:27 */       paramLexerGrammar.charVocabulary.add(i);
/* 24:   */     }
/* 25:29 */     this.autoGenType = paramInt;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void generate()
/* 29:   */   {
/* 30:33 */     this.grammar.generator.gen(this);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getLabel()
/* 34:   */   {
/* 35:37 */     return this.label;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Lookahead look(int paramInt)
/* 39:   */   {
/* 40:41 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setLabel(String paramString)
/* 44:   */   {
/* 45:45 */     this.label = paramString;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String toString()
/* 49:   */   {
/* 50:49 */     if (this.label != null) {
/* 51:50 */       return " " + this.label + ":" + this.beginText + ".." + this.endText;
/* 52:   */     }
/* 53:52 */     return " " + this.beginText + ".." + this.endText;
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CharRangeElement
 * JD-Core Version:    0.7.0.1
 */