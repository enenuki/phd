/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class NoViableAltForCharException
/*  4:   */   extends RecognitionException
/*  5:   */ {
/*  6:   */   public char foundChar;
/*  7:   */   
/*  8:   */   public NoViableAltForCharException(char paramChar, CharScanner paramCharScanner)
/*  9:   */   {
/* 10:14 */     super("NoViableAlt", paramCharScanner.getFilename(), paramCharScanner.getLine(), paramCharScanner.getColumn());
/* 11:   */     
/* 12:16 */     this.foundChar = paramChar;
/* 13:   */   }
/* 14:   */   
/* 15:   */   /**
/* 16:   */    * @deprecated
/* 17:   */    */
/* 18:   */   public NoViableAltForCharException(char paramChar, String paramString, int paramInt)
/* 19:   */   {
/* 20:21 */     this(paramChar, paramString, paramInt, -1);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public NoViableAltForCharException(char paramChar, String paramString, int paramInt1, int paramInt2)
/* 24:   */   {
/* 25:25 */     super("NoViableAlt", paramString, paramInt1, paramInt2);
/* 26:26 */     this.foundChar = paramChar;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getMessage()
/* 30:   */   {
/* 31:33 */     String str = "unexpected char: ";
/* 32:41 */     if ((this.foundChar >= ' ') && (this.foundChar <= '~'))
/* 33:   */     {
/* 34:42 */       str = str + '\'';
/* 35:43 */       str = str + this.foundChar;
/* 36:44 */       str = str + '\'';
/* 37:   */     }
/* 38:   */     else
/* 39:   */     {
/* 40:47 */       str = str + "0x" + Integer.toHexString(this.foundChar).toUpperCase();
/* 41:   */     }
/* 42:49 */     return str;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.NoViableAltForCharException
 * JD-Core Version:    0.7.0.1
 */