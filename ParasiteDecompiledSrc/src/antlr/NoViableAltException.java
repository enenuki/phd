/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public class NoViableAltException
/*  6:   */   extends RecognitionException
/*  7:   */ {
/*  8:   */   public Token token;
/*  9:   */   public AST node;
/* 10:   */   
/* 11:   */   public NoViableAltException(AST paramAST)
/* 12:   */   {
/* 13:17 */     super("NoViableAlt", "<AST>", paramAST.getLine(), paramAST.getColumn());
/* 14:18 */     this.node = paramAST;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public NoViableAltException(Token paramToken, String paramString)
/* 18:   */   {
/* 19:22 */     super("NoViableAlt", paramString, paramToken.getLine(), paramToken.getColumn());
/* 20:23 */     this.token = paramToken;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getMessage()
/* 24:   */   {
/* 25:30 */     if (this.token != null) {
/* 26:31 */       return "unexpected token: " + this.token.getText();
/* 27:   */     }
/* 28:35 */     if (this.node == TreeParser.ASTNULL) {
/* 29:36 */       return "unexpected end of subtree";
/* 30:   */     }
/* 31:38 */     return "unexpected AST node: " + this.node.toString();
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.NoViableAltException
 * JD-Core Version:    0.7.0.1
 */