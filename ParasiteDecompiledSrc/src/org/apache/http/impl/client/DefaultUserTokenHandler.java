/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import java.security.Principal;
/*  4:   */ import javax.net.ssl.SSLSession;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ import org.apache.http.auth.AuthScheme;
/*  7:   */ import org.apache.http.auth.AuthState;
/*  8:   */ import org.apache.http.auth.Credentials;
/*  9:   */ import org.apache.http.client.UserTokenHandler;
/* 10:   */ import org.apache.http.conn.HttpRoutedConnection;
/* 11:   */ import org.apache.http.protocol.HttpContext;
/* 12:   */ 
/* 13:   */ @Immutable
/* 14:   */ public class DefaultUserTokenHandler
/* 15:   */   implements UserTokenHandler
/* 16:   */ {
/* 17:   */   public Object getUserToken(HttpContext context)
/* 18:   */   {
/* 19:63 */     Principal userPrincipal = null;
/* 20:   */     
/* 21:65 */     AuthState targetAuthState = (AuthState)context.getAttribute("http.auth.target-scope");
/* 22:67 */     if (targetAuthState != null)
/* 23:   */     {
/* 24:68 */       userPrincipal = getAuthPrincipal(targetAuthState);
/* 25:69 */       if (userPrincipal == null)
/* 26:   */       {
/* 27:70 */         AuthState proxyAuthState = (AuthState)context.getAttribute("http.auth.proxy-scope");
/* 28:   */         
/* 29:72 */         userPrincipal = getAuthPrincipal(proxyAuthState);
/* 30:   */       }
/* 31:   */     }
/* 32:76 */     if (userPrincipal == null)
/* 33:   */     {
/* 34:77 */       HttpRoutedConnection conn = (HttpRoutedConnection)context.getAttribute("http.connection");
/* 35:79 */       if (conn.isOpen())
/* 36:   */       {
/* 37:80 */         SSLSession sslsession = conn.getSSLSession();
/* 38:81 */         if (sslsession != null) {
/* 39:82 */           userPrincipal = sslsession.getLocalPrincipal();
/* 40:   */         }
/* 41:   */       }
/* 42:   */     }
/* 43:87 */     return userPrincipal;
/* 44:   */   }
/* 45:   */   
/* 46:   */   private static Principal getAuthPrincipal(AuthState authState)
/* 47:   */   {
/* 48:91 */     AuthScheme scheme = authState.getAuthScheme();
/* 49:92 */     if ((scheme != null) && (scheme.isComplete()) && (scheme.isConnectionBased()))
/* 50:   */     {
/* 51:93 */       Credentials creds = authState.getCredentials();
/* 52:94 */       if (creds != null) {
/* 53:95 */         return creds.getUserPrincipal();
/* 54:   */       }
/* 55:   */     }
/* 56:98 */     return null;
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultUserTokenHandler
 * JD-Core Version:    0.7.0.1
 */