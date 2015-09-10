/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class CommonToken
/*  4:   */   extends Token
/*  5:   */ {
/*  6:   */   protected int line;
/*  7:13 */   protected String text = null;
/*  8:   */   protected int col;
/*  9:   */   
/* 10:   */   public CommonToken() {}
/* 11:   */   
/* 12:   */   public CommonToken(int paramInt, String paramString)
/* 13:   */   {
/* 14:20 */     this.type = paramInt;
/* 15:21 */     setText(paramString);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CommonToken(String paramString)
/* 19:   */   {
/* 20:25 */     this.text = paramString;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getLine()
/* 24:   */   {
/* 25:29 */     return this.line;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getText()
/* 29:   */   {
/* 30:33 */     return this.text;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setLine(int paramInt)
/* 34:   */   {
/* 35:37 */     this.line = paramInt;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setText(String paramString)
/* 39:   */   {
/* 40:41 */     this.text = paramString;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String toString()
/* 44:   */   {
/* 45:45 */     return "[\"" + getText() + "\",<" + this.type + ">,line=" + this.line + ",col=" + this.col + "]";
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int getColumn()
/* 49:   */   {
/* 50:50 */     return this.col;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void setColumn(int paramInt)
/* 54:   */   {
/* 55:54 */     this.col = paramInt;
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CommonToken
 * JD-Core Version:    0.7.0.1
 */