/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class ParseTreeToken
/*  4:   */   extends ParseTree
/*  5:   */ {
/*  6:   */   protected Token token;
/*  7:   */   
/*  8:   */   public ParseTreeToken(Token paramToken)
/*  9:   */   {
/* 10:15 */     this.token = paramToken;
/* 11:   */   }
/* 12:   */   
/* 13:   */   protected int getLeftmostDerivation(StringBuffer paramStringBuffer, int paramInt)
/* 14:   */   {
/* 15:19 */     paramStringBuffer.append(' ');
/* 16:20 */     paramStringBuffer.append(toString());
/* 17:21 */     return paramInt;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String toString()
/* 21:   */   {
/* 22:25 */     if (this.token != null) {
/* 23:26 */       return this.token.getText();
/* 24:   */     }
/* 25:28 */     return "<missing token>";
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ParseTreeToken
 * JD-Core Version:    0.7.0.1
 */