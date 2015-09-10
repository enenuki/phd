/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class SemanticException
/*  4:   */   extends RecognitionException
/*  5:   */ {
/*  6:   */   public SemanticException(String paramString)
/*  7:   */   {
/*  8:12 */     super(paramString);
/*  9:   */   }
/* 10:   */   
/* 11:   */   /**
/* 12:   */    * @deprecated
/* 13:   */    */
/* 14:   */   public SemanticException(String paramString1, String paramString2, int paramInt)
/* 15:   */   {
/* 16:17 */     this(paramString1, paramString2, paramInt, -1);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public SemanticException(String paramString1, String paramString2, int paramInt1, int paramInt2)
/* 20:   */   {
/* 21:21 */     super(paramString1, paramString2, paramInt1, paramInt2);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.SemanticException
 * JD-Core Version:    0.7.0.1
 */