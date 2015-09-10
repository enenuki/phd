/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public class CommonAST
/*  6:   */   extends BaseAST
/*  7:   */ {
/*  8:14 */   int ttype = 0;
/*  9:   */   String text;
/* 10:   */   
/* 11:   */   public String getText()
/* 12:   */   {
/* 13:20 */     return this.text;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int getType()
/* 17:   */   {
/* 18:25 */     return this.ttype;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void initialize(int paramInt, String paramString)
/* 22:   */   {
/* 23:29 */     setType(paramInt);
/* 24:30 */     setText(paramString);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void initialize(AST paramAST)
/* 28:   */   {
/* 29:34 */     setText(paramAST.getText());
/* 30:35 */     setType(paramAST.getType());
/* 31:   */   }
/* 32:   */   
/* 33:   */   public CommonAST() {}
/* 34:   */   
/* 35:   */   public CommonAST(Token paramToken)
/* 36:   */   {
/* 37:42 */     initialize(paramToken);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void initialize(Token paramToken)
/* 41:   */   {
/* 42:46 */     setText(paramToken.getText());
/* 43:47 */     setType(paramToken.getType());
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setText(String paramString)
/* 47:   */   {
/* 48:52 */     this.text = paramString;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setType(int paramInt)
/* 52:   */   {
/* 53:57 */     this.ttype = paramInt;
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CommonAST
 * JD-Core Version:    0.7.0.1
 */