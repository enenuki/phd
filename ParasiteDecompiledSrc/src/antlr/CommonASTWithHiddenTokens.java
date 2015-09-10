/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public class CommonASTWithHiddenTokens
/*  6:   */   extends CommonAST
/*  7:   */ {
/*  8:   */   protected CommonHiddenStreamToken hiddenBefore;
/*  9:   */   protected CommonHiddenStreamToken hiddenAfter;
/* 10:   */   
/* 11:   */   public CommonASTWithHiddenTokens() {}
/* 12:   */   
/* 13:   */   public CommonASTWithHiddenTokens(Token paramToken)
/* 14:   */   {
/* 15:23 */     super(paramToken);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CommonHiddenStreamToken getHiddenAfter()
/* 19:   */   {
/* 20:27 */     return this.hiddenAfter;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public CommonHiddenStreamToken getHiddenBefore()
/* 24:   */   {
/* 25:31 */     return this.hiddenBefore;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void initialize(AST paramAST)
/* 29:   */   {
/* 30:36 */     this.hiddenBefore = ((CommonASTWithHiddenTokens)paramAST).getHiddenBefore();
/* 31:37 */     this.hiddenAfter = ((CommonASTWithHiddenTokens)paramAST).getHiddenAfter();
/* 32:38 */     super.initialize(paramAST);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void initialize(Token paramToken)
/* 36:   */   {
/* 37:42 */     CommonHiddenStreamToken localCommonHiddenStreamToken = (CommonHiddenStreamToken)paramToken;
/* 38:43 */     super.initialize(localCommonHiddenStreamToken);
/* 39:44 */     this.hiddenBefore = localCommonHiddenStreamToken.getHiddenBefore();
/* 40:45 */     this.hiddenAfter = localCommonHiddenStreamToken.getHiddenAfter();
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CommonASTWithHiddenTokens
 * JD-Core Version:    0.7.0.1
 */