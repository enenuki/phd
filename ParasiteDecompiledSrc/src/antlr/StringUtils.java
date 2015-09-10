/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class StringUtils
/*  4:   */ {
/*  5:   */   public static String stripBack(String paramString, char paramChar)
/*  6:   */   {
/*  7:11 */     while ((paramString.length() > 0) && (paramString.charAt(paramString.length() - 1) == paramChar)) {
/*  8:12 */       paramString = paramString.substring(0, paramString.length() - 1);
/*  9:   */     }
/* 10:14 */     return paramString;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static String stripBack(String paramString1, String paramString2)
/* 14:   */   {
/* 15:   */     int i;
/* 16:   */     do
/* 17:   */     {
/* 18:26 */       i = 0;
/* 19:27 */       for (int j = 0; j < paramString2.length(); j++)
/* 20:   */       {
/* 21:28 */         int k = paramString2.charAt(j);
/* 22:29 */         while ((paramString1.length() > 0) && (paramString1.charAt(paramString1.length() - 1) == k))
/* 23:   */         {
/* 24:30 */           i = 1;
/* 25:31 */           paramString1 = paramString1.substring(0, paramString1.length() - 1);
/* 26:   */         }
/* 27:   */       }
/* 28:34 */     } while (i != 0);
/* 29:35 */     return paramString1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static String stripFront(String paramString, char paramChar)
/* 33:   */   {
/* 34:45 */     while ((paramString.length() > 0) && (paramString.charAt(0) == paramChar)) {
/* 35:46 */       paramString = paramString.substring(1);
/* 36:   */     }
/* 37:48 */     return paramString;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static String stripFront(String paramString1, String paramString2)
/* 41:   */   {
/* 42:   */     int i;
/* 43:   */     do
/* 44:   */     {
/* 45:60 */       i = 0;
/* 46:61 */       for (int j = 0; j < paramString2.length(); j++)
/* 47:   */       {
/* 48:62 */         int k = paramString2.charAt(j);
/* 49:63 */         while ((paramString1.length() > 0) && (paramString1.charAt(0) == k))
/* 50:   */         {
/* 51:64 */           i = 1;
/* 52:65 */           paramString1 = paramString1.substring(1);
/* 53:   */         }
/* 54:   */       }
/* 55:68 */     } while (i != 0);
/* 56:69 */     return paramString1;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static String stripFrontBack(String paramString1, String paramString2, String paramString3)
/* 60:   */   {
/* 61:80 */     int i = paramString1.indexOf(paramString2);
/* 62:81 */     int j = paramString1.lastIndexOf(paramString3);
/* 63:82 */     if ((i == -1) || (j == -1)) {
/* 64:82 */       return paramString1;
/* 65:   */     }
/* 66:83 */     return paramString1.substring(i + 1, j);
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.StringUtils
 * JD-Core Version:    0.7.0.1
 */