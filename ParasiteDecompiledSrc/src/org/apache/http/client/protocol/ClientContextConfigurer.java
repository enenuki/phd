/*  1:   */ package org.apache.http.client.protocol;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.apache.http.annotation.NotThreadSafe;
/*  5:   */ import org.apache.http.auth.AuthSchemeRegistry;
/*  6:   */ import org.apache.http.client.CookieStore;
/*  7:   */ import org.apache.http.client.CredentialsProvider;
/*  8:   */ import org.apache.http.cookie.CookieSpecRegistry;
/*  9:   */ import org.apache.http.protocol.HttpContext;
/* 10:   */ 
/* 11:   */ @NotThreadSafe
/* 12:   */ public class ClientContextConfigurer
/* 13:   */   implements ClientContext
/* 14:   */ {
/* 15:   */   private final HttpContext context;
/* 16:   */   
/* 17:   */   public ClientContextConfigurer(HttpContext context)
/* 18:   */   {
/* 19:52 */     if (context == null) {
/* 20:53 */       throw new IllegalArgumentException("HTTP context may not be null");
/* 21:   */     }
/* 22:54 */     this.context = context;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setCookieSpecRegistry(CookieSpecRegistry registry)
/* 26:   */   {
/* 27:58 */     this.context.setAttribute("http.cookiespec-registry", registry);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setAuthSchemeRegistry(AuthSchemeRegistry registry)
/* 31:   */   {
/* 32:62 */     this.context.setAttribute("http.authscheme-registry", registry);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setCookieStore(CookieStore store)
/* 36:   */   {
/* 37:66 */     this.context.setAttribute("http.cookie-store", store);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setCredentialsProvider(CredentialsProvider provider)
/* 41:   */   {
/* 42:70 */     this.context.setAttribute("http.auth.credentials-provider", provider);
/* 43:   */   }
/* 44:   */   
/* 45:   */   @Deprecated
/* 46:   */   public void setAuthSchemePref(List<String> list)
/* 47:   */   {
/* 48:80 */     this.context.setAttribute("http.auth.scheme-pref", list);
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.ClientContextConfigurer
 * JD-Core Version:    0.7.0.1
 */