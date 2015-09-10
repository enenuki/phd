/*   1:    */ package org.apache.http.client.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.http.HttpException;
/*   7:    */ import org.apache.http.HttpHost;
/*   8:    */ import org.apache.http.HttpRequest;
/*   9:    */ import org.apache.http.HttpRequestInterceptor;
/*  10:    */ import org.apache.http.annotation.Immutable;
/*  11:    */ import org.apache.http.auth.AuthScheme;
/*  12:    */ import org.apache.http.auth.AuthScope;
/*  13:    */ import org.apache.http.auth.AuthState;
/*  14:    */ import org.apache.http.auth.Credentials;
/*  15:    */ import org.apache.http.client.AuthCache;
/*  16:    */ import org.apache.http.client.CredentialsProvider;
/*  17:    */ import org.apache.http.protocol.HttpContext;
/*  18:    */ 
/*  19:    */ @Immutable
/*  20:    */ public class RequestAuthCache
/*  21:    */   implements HttpRequestInterceptor
/*  22:    */ {
/*  23: 58 */   private final Log log = LogFactory.getLog(getClass());
/*  24:    */   
/*  25:    */   public void process(HttpRequest request, HttpContext context)
/*  26:    */     throws HttpException, IOException
/*  27:    */   {
/*  28: 66 */     if (request == null) {
/*  29: 67 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  30:    */     }
/*  31: 69 */     if (context == null) {
/*  32: 70 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  33:    */     }
/*  34: 73 */     AuthCache authCache = (AuthCache)context.getAttribute("http.auth.auth-cache");
/*  35: 74 */     if (authCache == null)
/*  36:    */     {
/*  37: 75 */       this.log.debug("Auth cache not set in the context");
/*  38: 76 */       return;
/*  39:    */     }
/*  40: 79 */     CredentialsProvider credsProvider = (CredentialsProvider)context.getAttribute("http.auth.credentials-provider");
/*  41: 81 */     if (credsProvider == null)
/*  42:    */     {
/*  43: 82 */       this.log.debug("Credentials provider not set in the context");
/*  44: 83 */       return;
/*  45:    */     }
/*  46: 86 */     HttpHost target = (HttpHost)context.getAttribute("http.target_host");
/*  47: 87 */     AuthState targetState = (AuthState)context.getAttribute("http.auth.target-scope");
/*  48: 88 */     if ((target != null) && (targetState != null) && (targetState.getAuthScheme() == null))
/*  49:    */     {
/*  50: 89 */       AuthScheme authScheme = authCache.get(target);
/*  51: 90 */       if (authScheme != null) {
/*  52: 91 */         doPreemptiveAuth(target, authScheme, targetState, credsProvider);
/*  53:    */       }
/*  54:    */     }
/*  55: 95 */     HttpHost proxy = (HttpHost)context.getAttribute("http.proxy_host");
/*  56: 96 */     AuthState proxyState = (AuthState)context.getAttribute("http.auth.proxy-scope");
/*  57: 97 */     if ((proxy != null) && (proxyState != null) && (proxyState.getAuthScheme() == null))
/*  58:    */     {
/*  59: 98 */       AuthScheme authScheme = authCache.get(proxy);
/*  60: 99 */       if (authScheme != null) {
/*  61:100 */         doPreemptiveAuth(proxy, authScheme, proxyState, credsProvider);
/*  62:    */       }
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   private void doPreemptiveAuth(HttpHost host, AuthScheme authScheme, AuthState authState, CredentialsProvider credsProvider)
/*  67:    */   {
/*  68:110 */     String schemeName = authScheme.getSchemeName();
/*  69:111 */     if (this.log.isDebugEnabled()) {
/*  70:112 */       this.log.debug("Re-using cached '" + schemeName + "' auth scheme for " + host);
/*  71:    */     }
/*  72:115 */     AuthScope authScope = new AuthScope(host.getHostName(), host.getPort(), AuthScope.ANY_REALM, schemeName);
/*  73:    */     
/*  74:117 */     Credentials creds = credsProvider.getCredentials(authScope);
/*  75:119 */     if (creds != null)
/*  76:    */     {
/*  77:120 */       authState.setAuthScheme(authScheme);
/*  78:121 */       authState.setCredentials(creds);
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82:123 */       this.log.debug("No credentials for preemptive authentication");
/*  83:    */     }
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.RequestAuthCache
 * JD-Core Version:    0.7.0.1
 */