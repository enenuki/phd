/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public abstract class ParseTree
/*  6:   */   extends BaseAST
/*  7:   */ {
/*  8:   */   public String getLeftmostDerivationStep(int paramInt)
/*  9:   */   {
/* 10:18 */     if (paramInt <= 0) {
/* 11:19 */       return toString();
/* 12:   */     }
/* 13:21 */     StringBuffer localStringBuffer = new StringBuffer(2000);
/* 14:22 */     getLeftmostDerivation(localStringBuffer, paramInt);
/* 15:23 */     return localStringBuffer.toString();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getLeftmostDerivation(int paramInt)
/* 19:   */   {
/* 20:27 */     StringBuffer localStringBuffer = new StringBuffer(2000);
/* 21:28 */     localStringBuffer.append("    " + toString());
/* 22:29 */     localStringBuffer.append("\n");
/* 23:30 */     for (int i = 1; i < paramInt; i++)
/* 24:   */     {
/* 25:31 */       localStringBuffer.append(" =>");
/* 26:32 */       localStringBuffer.append(getLeftmostDerivationStep(i));
/* 27:33 */       localStringBuffer.append("\n");
/* 28:   */     }
/* 29:35 */     return localStringBuffer.toString();
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected abstract int getLeftmostDerivation(StringBuffer paramStringBuffer, int paramInt);
/* 33:   */   
/* 34:   */   public void initialize(int paramInt, String paramString) {}
/* 35:   */   
/* 36:   */   public void initialize(AST paramAST) {}
/* 37:   */   
/* 38:   */   public void initialize(Token paramToken) {}
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ParseTree
 * JD-Core Version:    0.7.0.1
 */