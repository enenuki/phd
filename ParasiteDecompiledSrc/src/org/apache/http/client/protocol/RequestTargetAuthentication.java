/*   1:    */ package org.apache.http.client.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.http.Header;
/*   7:    */ import org.apache.http.HttpException;
/*   8:    */ import org.apache.http.HttpRequest;
/*   9:    */ import org.apache.http.HttpRequestInterceptor;
/*  10:    */ import org.apache.http.RequestLine;
/*  11:    */ import org.apache.http.annotation.Immutable;
/*  12:    */ import org.apache.http.auth.AuthScheme;
/*  13:    */ import org.apache.http.auth.AuthState;
/*  14:    */ import org.apache.http.auth.AuthenticationException;
/*  15:    */ import org.apache.http.auth.ContextAwareAuthScheme;
/*  16:    */ import org.apache.http.auth.Credentials;
/*  17:    */ import org.apache.http.protocol.HttpContext;
/*  18:    */ 
/*  19:    */ @Immutable
/*  20:    */ public class RequestTargetAuthentication
/*  21:    */   implements HttpRequestInterceptor
/*  22:    */ {
/*  23: 57 */   private final Log log = LogFactory.getLog(getClass());
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
/*  34: 73 */     String method = request.getRequestLine().getMethod();
/*  35: 74 */     if (method.equalsIgnoreCase("CONNECT")) {
/*  36: 75 */       return;
/*  37:    */     }
/*  38: 78 */     if (request.containsHeader("Authorization")) {
/*  39: 79 */       return;
/*  40:    */     }
/*  41: 83 */     AuthState authState = (AuthState)context.getAttribute("http.auth.target-scope");
/*  42: 85 */     if (authState == null)
/*  43:    */     {
/*  44: 86 */       this.log.debug("Target auth state not set in the context");
/*  45: 87 */       return;
/*  46:    */     }
/*  47: 90 */     AuthScheme authScheme = authState.getAuthScheme();
/*  48: 91 */     if (authScheme == null) {
/*  49: 92 */       return;
/*  50:    */     }
/*  51: 95 */     Credentials creds = authState.getCredentials();
/*  52: 96 */     if (creds == null)
/*  53:    */     {
/*  54: 97 */       this.log.debug("User credentials not available");
/*  55: 98 */       return;
/*  56:    */     }
/*  57:101 */     if ((authState.getAuthScope() != null) || (!authScheme.isConnectionBased())) {
/*  58:    */       try
/*  59:    */       {
/*  60:    */         Header header;
/*  61:    */         Header header;
/*  62:104 */         if ((authScheme instanceof ContextAwareAuthScheme)) {
/*  63:105 */           header = ((ContextAwareAuthScheme)authScheme).authenticate(creds, request, context);
/*  64:    */         } else {
/*  65:108 */           header = authScheme.authenticate(creds, request);
/*  66:    */         }
/*  67:110 */         request.addHeader(header);
/*  68:    */       }
/*  69:    */       catch (AuthenticationException ex)
/*  70:    */       {
/*  71:112 */         if (this.log.isErrorEnabled()) {
/*  72:113 */           this.log.error("Authentication error: " + ex.getMessage());
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.RequestTargetAuthentication
 * JD-Core Version:    0.7.0.1
 */