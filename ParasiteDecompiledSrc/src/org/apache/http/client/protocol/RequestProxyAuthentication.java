/*   1:    */ package org.apache.http.client.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.http.Header;
/*   7:    */ import org.apache.http.HttpException;
/*   8:    */ import org.apache.http.HttpRequest;
/*   9:    */ import org.apache.http.HttpRequestInterceptor;
/*  10:    */ import org.apache.http.annotation.Immutable;
/*  11:    */ import org.apache.http.auth.AuthScheme;
/*  12:    */ import org.apache.http.auth.AuthState;
/*  13:    */ import org.apache.http.auth.AuthenticationException;
/*  14:    */ import org.apache.http.auth.ContextAwareAuthScheme;
/*  15:    */ import org.apache.http.auth.Credentials;
/*  16:    */ import org.apache.http.conn.HttpRoutedConnection;
/*  17:    */ import org.apache.http.conn.routing.HttpRoute;
/*  18:    */ import org.apache.http.protocol.HttpContext;
/*  19:    */ 
/*  20:    */ @Immutable
/*  21:    */ public class RequestProxyAuthentication
/*  22:    */   implements HttpRequestInterceptor
/*  23:    */ {
/*  24: 60 */   private final Log log = LogFactory.getLog(getClass());
/*  25:    */   
/*  26:    */   public void process(HttpRequest request, HttpContext context)
/*  27:    */     throws HttpException, IOException
/*  28:    */   {
/*  29: 69 */     if (request == null) {
/*  30: 70 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  31:    */     }
/*  32: 72 */     if (context == null) {
/*  33: 73 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  34:    */     }
/*  35: 76 */     if (request.containsHeader("Proxy-Authorization")) {
/*  36: 77 */       return;
/*  37:    */     }
/*  38: 80 */     HttpRoutedConnection conn = (HttpRoutedConnection)context.getAttribute("http.connection");
/*  39: 82 */     if (conn == null)
/*  40:    */     {
/*  41: 83 */       this.log.debug("HTTP connection not set in the context");
/*  42: 84 */       return;
/*  43:    */     }
/*  44: 86 */     HttpRoute route = conn.getRoute();
/*  45: 87 */     if (route.isTunnelled()) {
/*  46: 88 */       return;
/*  47:    */     }
/*  48: 92 */     AuthState authState = (AuthState)context.getAttribute("http.auth.proxy-scope");
/*  49: 94 */     if (authState == null)
/*  50:    */     {
/*  51: 95 */       this.log.debug("Proxy auth state not set in the context");
/*  52: 96 */       return;
/*  53:    */     }
/*  54: 99 */     AuthScheme authScheme = authState.getAuthScheme();
/*  55:100 */     if (authScheme == null) {
/*  56:101 */       return;
/*  57:    */     }
/*  58:104 */     Credentials creds = authState.getCredentials();
/*  59:105 */     if (creds == null)
/*  60:    */     {
/*  61:106 */       this.log.debug("User credentials not available");
/*  62:107 */       return;
/*  63:    */     }
/*  64:109 */     if ((authState.getAuthScope() != null) || (!authScheme.isConnectionBased())) {
/*  65:    */       try
/*  66:    */       {
/*  67:    */         Header header;
/*  68:    */         Header header;
/*  69:112 */         if ((authScheme instanceof ContextAwareAuthScheme)) {
/*  70:113 */           header = ((ContextAwareAuthScheme)authScheme).authenticate(creds, request, context);
/*  71:    */         } else {
/*  72:116 */           header = authScheme.authenticate(creds, request);
/*  73:    */         }
/*  74:118 */         request.addHeader(header);
/*  75:    */       }
/*  76:    */       catch (AuthenticationException ex)
/*  77:    */       {
/*  78:120 */         if (this.log.isErrorEnabled()) {
/*  79:121 */           this.log.error("Proxy authentication error: " + ex.getMessage());
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.RequestProxyAuthentication
 * JD-Core Version:    0.7.0.1
 */