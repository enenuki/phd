/*  1:   */ package antlr.ASdebug;
/*  2:   */ 
/*  3:   */ import antlr.Token;
/*  4:   */ import antlr.TokenStream;
/*  5:   */ 
/*  6:   */ public final class ASDebugStream
/*  7:   */ {
/*  8:   */   public static String getEntireText(TokenStream paramTokenStream)
/*  9:   */   {
/* 10:15 */     if ((paramTokenStream instanceof IASDebugStream))
/* 11:   */     {
/* 12:17 */       IASDebugStream localIASDebugStream = (IASDebugStream)paramTokenStream;
/* 13:18 */       return localIASDebugStream.getEntireText();
/* 14:   */     }
/* 15:20 */     return null;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static TokenOffsetInfo getOffsetInfo(TokenStream paramTokenStream, Token paramToken)
/* 19:   */   {
/* 20:25 */     if ((paramTokenStream instanceof IASDebugStream))
/* 21:   */     {
/* 22:27 */       IASDebugStream localIASDebugStream = (IASDebugStream)paramTokenStream;
/* 23:28 */       return localIASDebugStream.getOffsetInfo(paramToken);
/* 24:   */     }
/* 25:30 */     return null;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ASdebug.ASDebugStream
 * JD-Core Version:    0.7.0.1
 */