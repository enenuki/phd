/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.impl.BitSet;
/*  4:   */ 
/*  5:   */ class CharLiteralElement
/*  6:   */   extends GrammarAtom
/*  7:   */ {
/*  8:   */   public CharLiteralElement(LexerGrammar paramLexerGrammar, Token paramToken, boolean paramBoolean, int paramInt)
/*  9:   */   {
/* 10:14 */     super(paramLexerGrammar, paramToken, 1);
/* 11:15 */     this.tokenType = ANTLRLexer.tokenTypeForCharLiteral(paramToken.getText());
/* 12:16 */     paramLexerGrammar.charVocabulary.add(this.tokenType);
/* 13:17 */     this.line = paramToken.getLine();
/* 14:18 */     this.not = paramBoolean;
/* 15:19 */     this.autoGenType = paramInt;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void generate()
/* 19:   */   {
/* 20:23 */     this.grammar.generator.gen(this);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Lookahead look(int paramInt)
/* 24:   */   {
/* 25:27 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CharLiteralElement
 * JD-Core Version:    0.7.0.1
 */