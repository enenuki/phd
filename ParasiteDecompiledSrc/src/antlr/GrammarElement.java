/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ abstract class GrammarElement
/*  4:   */ {
/*  5:   */   public static final int AUTO_GEN_NONE = 1;
/*  6:   */   public static final int AUTO_GEN_CARET = 2;
/*  7:   */   public static final int AUTO_GEN_BANG = 3;
/*  8:   */   protected Grammar grammar;
/*  9:   */   protected int line;
/* 10:   */   protected int column;
/* 11:   */   
/* 12:   */   public GrammarElement(Grammar paramGrammar)
/* 13:   */   {
/* 14:35 */     this.grammar = paramGrammar;
/* 15:36 */     this.line = -1;
/* 16:37 */     this.column = -1;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public GrammarElement(Grammar paramGrammar, Token paramToken)
/* 20:   */   {
/* 21:41 */     this.grammar = paramGrammar;
/* 22:42 */     this.line = paramToken.getLine();
/* 23:43 */     this.column = paramToken.getColumn();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void generate() {}
/* 27:   */   
/* 28:   */   public int getLine()
/* 29:   */   {
/* 30:50 */     return this.line;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getColumn()
/* 34:   */   {
/* 35:54 */     return this.column;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Lookahead look(int paramInt)
/* 39:   */   {
/* 40:58 */     return null;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public abstract String toString();
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.GrammarElement
 * JD-Core Version:    0.7.0.1
 */