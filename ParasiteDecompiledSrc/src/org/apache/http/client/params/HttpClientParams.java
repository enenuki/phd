/*  1:   */ package org.apache.http.client.params;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ import org.apache.http.params.HttpParams;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class HttpClientParams
/*  8:   */ {
/*  9:   */   public static boolean isRedirecting(HttpParams params)
/* 10:   */   {
/* 11:46 */     if (params == null) {
/* 12:47 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 13:   */     }
/* 14:49 */     return params.getBooleanParameter("http.protocol.handle-redirects", true);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static void setRedirecting(HttpParams params, boolean value)
/* 18:   */   {
/* 19:54 */     if (params == null) {
/* 20:55 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 21:   */     }
/* 22:57 */     params.setBooleanParameter("http.protocol.handle-redirects", value);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static boolean isAuthenticating(HttpParams params)
/* 26:   */   {
/* 27:62 */     if (params == null) {
/* 28:63 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 29:   */     }
/* 30:65 */     return params.getBooleanParameter("http.protocol.handle-authentication", true);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static void setAuthenticating(HttpParams params, boolean value)
/* 34:   */   {
/* 35:70 */     if (params == null) {
/* 36:71 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 37:   */     }
/* 38:73 */     params.setBooleanParameter("http.protocol.handle-authentication", value);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static String getCookiePolicy(HttpParams params)
/* 42:   */   {
/* 43:78 */     if (params == null) {
/* 44:79 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 45:   */     }
/* 46:81 */     String cookiePolicy = (String)params.getParameter("http.protocol.cookie-policy");
/* 47:83 */     if (cookiePolicy == null) {
/* 48:84 */       return "best-match";
/* 49:   */     }
/* 50:86 */     return cookiePolicy;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static void setCookiePolicy(HttpParams params, String cookiePolicy)
/* 54:   */   {
/* 55:90 */     if (params == null) {
/* 56:91 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 57:   */     }
/* 58:93 */     params.setParameter("http.protocol.cookie-policy", cookiePolicy);
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.params.HttpClientParams
 * JD-Core Version:    0.7.0.1
 */