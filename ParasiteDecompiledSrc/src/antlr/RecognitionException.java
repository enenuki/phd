/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class RecognitionException
/*  4:   */   extends ANTLRException
/*  5:   */ {
/*  6:   */   public String fileName;
/*  7:   */   public int line;
/*  8:   */   public int column;
/*  9:   */   
/* 10:   */   public RecognitionException()
/* 11:   */   {
/* 12:16 */     super("parsing error");
/* 13:17 */     this.fileName = null;
/* 14:18 */     this.line = -1;
/* 15:19 */     this.column = -1;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public RecognitionException(String paramString)
/* 19:   */   {
/* 20:27 */     super(paramString);
/* 21:28 */     this.fileName = null;
/* 22:29 */     this.line = -1;
/* 23:30 */     this.column = -1;
/* 24:   */   }
/* 25:   */   
/* 26:   */   /**
/* 27:   */    * @deprecated
/* 28:   */    */
/* 29:   */   public RecognitionException(String paramString1, String paramString2, int paramInt)
/* 30:   */   {
/* 31:35 */     this(paramString1, paramString2, paramInt, -1);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public RecognitionException(String paramString1, String paramString2, int paramInt1, int paramInt2)
/* 35:   */   {
/* 36:43 */     super(paramString1);
/* 37:44 */     this.fileName = paramString2;
/* 38:45 */     this.line = paramInt1;
/* 39:46 */     this.column = paramInt2;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getFilename()
/* 43:   */   {
/* 44:50 */     return this.fileName;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int getLine()
/* 48:   */   {
/* 49:54 */     return this.line;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int getColumn()
/* 53:   */   {
/* 54:58 */     return this.column;
/* 55:   */   }
/* 56:   */   
/* 57:   */   /**
/* 58:   */    * @deprecated
/* 59:   */    */
/* 60:   */   public String getErrorMessage()
/* 61:   */   {
/* 62:63 */     return getMessage();
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String toString()
/* 66:   */   {
/* 67:67 */     return FileLineFormatter.getFormatter().getFormatString(this.fileName, this.line, this.column) + getMessage();
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.RecognitionException
 * JD-Core Version:    0.7.0.1
 */