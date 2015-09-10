/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class Token
/*  4:   */   implements Cloneable
/*  5:   */ {
/*  6:   */   public static final int MIN_USER_TYPE = 4;
/*  7:   */   public static final int NULL_TREE_LOOKAHEAD = 3;
/*  8:   */   public static final int INVALID_TYPE = 0;
/*  9:   */   public static final int EOF_TYPE = 1;
/* 10:   */   public static final int SKIP = -1;
/* 11:22 */   protected int type = 0;
/* 12:25 */   public static Token badToken = new Token(0, "<no text>");
/* 13:   */   
/* 14:   */   public Token() {}
/* 15:   */   
/* 16:   */   public Token(int paramInt)
/* 17:   */   {
/* 18:31 */     this.type = paramInt;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Token(int paramInt, String paramString)
/* 22:   */   {
/* 23:35 */     this.type = paramInt;
/* 24:36 */     setText(paramString);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getColumn()
/* 28:   */   {
/* 29:40 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getLine()
/* 33:   */   {
/* 34:44 */     return 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getFilename()
/* 38:   */   {
/* 39:48 */     return null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setFilename(String paramString) {}
/* 43:   */   
/* 44:   */   public String getText()
/* 45:   */   {
/* 46:55 */     return "<no text>";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setText(String paramString) {}
/* 50:   */   
/* 51:   */   public void setColumn(int paramInt) {}
/* 52:   */   
/* 53:   */   public void setLine(int paramInt) {}
/* 54:   */   
/* 55:   */   public int getType()
/* 56:   */   {
/* 57:68 */     return this.type;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setType(int paramInt)
/* 61:   */   {
/* 62:72 */     this.type = paramInt;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String toString()
/* 66:   */   {
/* 67:76 */     return "[\"" + getText() + "\",<" + getType() + ">]";
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.Token
 * JD-Core Version:    0.7.0.1
 */