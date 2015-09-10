/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ abstract class GrammarAtom
/*  4:   */   extends AlternativeElement
/*  5:   */ {
/*  6:   */   protected String label;
/*  7:   */   protected String atomText;
/*  8:16 */   protected int tokenType = 0;
/*  9:17 */   protected boolean not = false;
/* 10:21 */   protected String ASTNodeType = null;
/* 11:   */   
/* 12:   */   public GrammarAtom(Grammar paramGrammar, Token paramToken, int paramInt)
/* 13:   */   {
/* 14:24 */     super(paramGrammar, paramToken, paramInt);
/* 15:25 */     this.atomText = paramToken.getText();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getLabel()
/* 19:   */   {
/* 20:29 */     return this.label;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getText()
/* 24:   */   {
/* 25:33 */     return this.atomText;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getType()
/* 29:   */   {
/* 30:37 */     return this.tokenType;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setLabel(String paramString)
/* 34:   */   {
/* 35:41 */     this.label = paramString;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getASTNodeType()
/* 39:   */   {
/* 40:45 */     return this.ASTNodeType;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setASTNodeType(String paramString)
/* 44:   */   {
/* 45:49 */     this.ASTNodeType = paramString;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setOption(Token paramToken1, Token paramToken2)
/* 49:   */   {
/* 50:53 */     if (paramToken1.getText().equals("AST")) {
/* 51:54 */       setASTNodeType(paramToken2.getText());
/* 52:   */     } else {
/* 53:57 */       this.grammar.antlrTool.error("Invalid element option:" + paramToken1.getText(), this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String toString()
/* 58:   */   {
/* 59:63 */     String str = " ";
/* 60:64 */     if (this.label != null) {
/* 61:64 */       str = str + this.label + ":";
/* 62:   */     }
/* 63:65 */     if (this.not) {
/* 64:65 */       str = str + "~";
/* 65:   */     }
/* 66:66 */     return str + this.atomText;
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.GrammarAtom
 * JD-Core Version:    0.7.0.1
 */