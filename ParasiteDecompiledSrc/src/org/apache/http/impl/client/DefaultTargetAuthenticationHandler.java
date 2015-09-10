/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.apache.http.Header;
/*  6:   */ import org.apache.http.HttpResponse;
/*  7:   */ import org.apache.http.StatusLine;
/*  8:   */ import org.apache.http.annotation.Immutable;
/*  9:   */ import org.apache.http.auth.MalformedChallengeException;
/* 10:   */ import org.apache.http.params.HttpParams;
/* 11:   */ import org.apache.http.protocol.HttpContext;
/* 12:   */ 
/* 13:   */ @Immutable
/* 14:   */ public class DefaultTargetAuthenticationHandler
/* 15:   */   extends AbstractAuthenticationHandler
/* 16:   */ {
/* 17:   */   public boolean isAuthenticationRequested(HttpResponse response, HttpContext context)
/* 18:   */   {
/* 19:60 */     if (response == null) {
/* 20:61 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 21:   */     }
/* 22:63 */     int status = response.getStatusLine().getStatusCode();
/* 23:64 */     return status == 401;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Map<String, Header> getChallenges(HttpResponse response, HttpContext context)
/* 27:   */     throws MalformedChallengeException
/* 28:   */   {
/* 29:70 */     if (response == null) {
/* 30:71 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 31:   */     }
/* 32:73 */     Header[] headers = response.getHeaders("WWW-Authenticate");
/* 33:74 */     return parseChallenges(headers);
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected List<String> getAuthPreferences(HttpResponse response, HttpContext context)
/* 37:   */   {
/* 38:82 */     List<String> authpref = (List)response.getParams().getParameter("http.auth.target-scheme-pref");
/* 39:84 */     if (authpref != null) {
/* 40:85 */       return authpref;
/* 41:   */     }
/* 42:87 */     return super.getAuthPreferences(response, context);
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultTargetAuthenticationHandler
 * JD-Core Version:    0.7.0.1
 */