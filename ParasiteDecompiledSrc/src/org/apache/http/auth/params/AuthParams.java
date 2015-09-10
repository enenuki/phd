/*  1:   */ package org.apache.http.auth.params;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.params.HttpParams;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public final class AuthParams
/*  8:   */ {
/*  9:   */   public static String getCredentialCharset(HttpParams params)
/* 10:   */   {
/* 11:58 */     if (params == null) {
/* 12:59 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 13:   */     }
/* 14:61 */     String charset = (String)params.getParameter("http.auth.credential-charset");
/* 15:63 */     if (charset == null) {
/* 16:64 */       charset = "US-ASCII";
/* 17:   */     }
/* 18:66 */     return charset;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static void setCredentialCharset(HttpParams params, String charset)
/* 22:   */   {
/* 23:77 */     if (params == null) {
/* 24:78 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 25:   */     }
/* 26:80 */     params.setParameter("http.auth.credential-charset", charset);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.params.AuthParams
 * JD-Core Version:    0.7.0.1
 */