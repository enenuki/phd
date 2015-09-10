/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class TokenWithIndex
/*  4:   */   extends CommonToken
/*  5:   */ {
/*  6:   */   int index;
/*  7:   */   
/*  8:   */   public TokenWithIndex() {}
/*  9:   */   
/* 10:   */   public TokenWithIndex(int paramInt, String paramString)
/* 11:   */   {
/* 12:20 */     super(paramInt, paramString);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void setIndex(int paramInt)
/* 16:   */   {
/* 17:24 */     this.index = paramInt;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getIndex()
/* 21:   */   {
/* 22:28 */     return this.index;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String toString()
/* 26:   */   {
/* 27:32 */     return "[" + this.index + ":\"" + getText() + "\",<" + getType() + ">,line=" + this.line + ",col=" + this.col + "]\n";
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenWithIndex
 * JD-Core Version:    0.7.0.1
 */