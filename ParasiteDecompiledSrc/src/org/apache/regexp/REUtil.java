/*  1:   */ package org.apache.regexp;
/*  2:   */ 
/*  3:   */ public class REUtil
/*  4:   */ {
/*  5:   */   private static final String complexPrefix = "complex:";
/*  6:   */   
/*  7:   */   public static RE createRE(String paramString, int paramInt)
/*  8:   */     throws RESyntaxException
/*  9:   */   {
/* 10:81 */     if (paramString.startsWith("complex:")) {
/* 11:83 */       return new RE(paramString.substring("complex:".length()), paramInt);
/* 12:   */     }
/* 13:85 */     return new RE(RE.simplePatternToFullRegularExpression(paramString), paramInt);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static RE createRE(String paramString)
/* 17:   */     throws RESyntaxException
/* 18:   */   {
/* 19:97 */     return createRE(paramString, 0);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.REUtil
 * JD-Core Version:    0.7.0.1
 */