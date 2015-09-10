/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.impl.BitSet;
/*  4:   */ 
/*  5:   */ class StringLiteralElement
/*  6:   */   extends GrammarAtom
/*  7:   */ {
/*  8:   */   protected String processedAtomText;
/*  9:   */   
/* 10:   */   public StringLiteralElement(Grammar paramGrammar, Token paramToken, int paramInt)
/* 11:   */   {
/* 12:16 */     super(paramGrammar, paramToken, paramInt);
/* 13:17 */     if (!(paramGrammar instanceof LexerGrammar))
/* 14:   */     {
/* 15:19 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(this.atomText);
/* 16:20 */       if (localTokenSymbol == null) {
/* 17:21 */         paramGrammar.antlrTool.error("Undefined literal: " + this.atomText, this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 18:   */       } else {
/* 19:24 */         this.tokenType = localTokenSymbol.getTokenType();
/* 20:   */       }
/* 21:   */     }
/* 22:27 */     this.line = paramToken.getLine();
/* 23:   */     
/* 24:   */ 
/* 25:   */ 
/* 26:31 */     this.processedAtomText = new String();
/* 27:32 */     for (int i = 1; i < this.atomText.length() - 1; i++)
/* 28:   */     {
/* 29:33 */       char c = this.atomText.charAt(i);
/* 30:34 */       if ((c == '\\') && 
/* 31:35 */         (i + 1 < this.atomText.length() - 1))
/* 32:   */       {
/* 33:36 */         i++;
/* 34:37 */         c = this.atomText.charAt(i);
/* 35:38 */         switch (c)
/* 36:   */         {
/* 37:   */         case 'n': 
/* 38:40 */           c = '\n';
/* 39:41 */           break;
/* 40:   */         case 'r': 
/* 41:43 */           c = '\r';
/* 42:44 */           break;
/* 43:   */         case 't': 
/* 44:46 */           c = '\t';
/* 45:   */         }
/* 46:   */       }
/* 47:51 */       if ((paramGrammar instanceof LexerGrammar)) {
/* 48:52 */         ((LexerGrammar)paramGrammar).charVocabulary.add(c);
/* 49:   */       }
/* 50:54 */       this.processedAtomText += c;
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void generate()
/* 55:   */   {
/* 56:59 */     this.grammar.generator.gen(this);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public Lookahead look(int paramInt)
/* 60:   */   {
/* 61:63 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 62:   */   }
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.StringLiteralElement
 * JD-Core Version:    0.7.0.1
 */