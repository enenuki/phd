/*   1:    */ package org.apache.http.client.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.http.HttpException;
/*   7:    */ import org.apache.http.HttpHost;
/*   8:    */ import org.apache.http.HttpResponse;
/*   9:    */ import org.apache.http.HttpResponseInterceptor;
/*  10:    */ import org.apache.http.annotation.Immutable;
/*  11:    */ import org.apache.http.auth.AuthScheme;
/*  12:    */ import org.apache.http.auth.AuthState;
/*  13:    */ import org.apache.http.client.AuthCache;
/*  14:    */ import org.apache.http.impl.client.BasicAuthCache;
/*  15:    */ import org.apache.http.protocol.HttpContext;
/*  16:    */ 
/*  17:    */ @Immutable
/*  18:    */ public class ResponseAuthCache
/*  19:    */   implements HttpResponseInterceptor
/*  20:    */ {
/*  21: 58 */   private final Log log = LogFactory.getLog(getClass());
/*  22:    */   
/*  23:    */   public void process(HttpResponse response, HttpContext context)
/*  24:    */     throws HttpException, IOException
/*  25:    */   {
/*  26: 66 */     if (response == null) {
/*  27: 67 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  28:    */     }
/*  29: 69 */     if (context == null) {
/*  30: 70 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  31:    */     }
/*  32: 72 */     AuthCache authCache = (AuthCache)context.getAttribute("http.auth.auth-cache");
/*  33:    */     
/*  34: 74 */     HttpHost target = (HttpHost)context.getAttribute("http.target_host");
/*  35: 75 */     AuthState targetState = (AuthState)context.getAttribute("http.auth.target-scope");
/*  36: 76 */     if ((target != null) && (targetState != null) && 
/*  37: 77 */       (isCachable(targetState)))
/*  38:    */     {
/*  39: 78 */       if (authCache == null)
/*  40:    */       {
/*  41: 79 */         authCache = new BasicAuthCache();
/*  42: 80 */         context.setAttribute("http.auth.auth-cache", authCache);
/*  43:    */       }
/*  44: 82 */       cache(authCache, target, targetState);
/*  45:    */     }
/*  46: 86 */     HttpHost proxy = (HttpHost)context.getAttribute("http.proxy_host");
/*  47: 87 */     AuthState proxyState = (AuthState)context.getAttribute("http.auth.proxy-scope");
/*  48: 88 */     if ((proxy != null) && (proxyState != null) && 
/*  49: 89 */       (isCachable(proxyState)))
/*  50:    */     {
/*  51: 90 */       if (authCache == null)
/*  52:    */       {
/*  53: 91 */         authCache = new BasicAuthCache();
/*  54: 92 */         context.setAttribute("http.auth.auth-cache", authCache);
/*  55:    */       }
/*  56: 94 */       cache(authCache, proxy, proxyState);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   private boolean isCachable(AuthState authState)
/*  61:    */   {
/*  62:100 */     AuthScheme authScheme = authState.getAuthScheme();
/*  63:101 */     if ((authScheme == null) || (!authScheme.isComplete())) {
/*  64:102 */       return false;
/*  65:    */     }
/*  66:104 */     String schemeName = authScheme.getSchemeName();
/*  67:105 */     return (schemeName.equalsIgnoreCase("Basic")) || (schemeName.equalsIgnoreCase("Digest"));
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void cache(AuthCache authCache, HttpHost host, AuthState authState)
/*  71:    */   {
/*  72:110 */     AuthScheme authScheme = authState.getAuthScheme();
/*  73:111 */     if (authState.getAuthScope() != null) {
/*  74:112 */       if (authState.getCredentials() != null)
/*  75:    */       {
/*  76:113 */         if (this.log.isDebugEnabled()) {
/*  77:114 */           this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + host);
/*  78:    */         }
/*  79:117 */         authCache.put(host, authScheme);
/*  80:    */       }
/*  81:    */       else
/*  82:    */       {
/*  83:119 */         authCache.remove(host);
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.ResponseAuthCache
 * JD-Core Version:    0.7.0.1
 */