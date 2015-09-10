/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InterruptedIOException;
/*   5:    */ import java.net.ConnectException;
/*   6:    */ import java.net.UnknownHostException;
/*   7:    */ import javax.net.ssl.SSLException;
/*   8:    */ import org.apache.http.HttpEntityEnclosingRequest;
/*   9:    */ import org.apache.http.HttpRequest;
/*  10:    */ import org.apache.http.annotation.Immutable;
/*  11:    */ import org.apache.http.client.HttpRequestRetryHandler;
/*  12:    */ import org.apache.http.protocol.HttpContext;
/*  13:    */ 
/*  14:    */ @Immutable
/*  15:    */ public class DefaultHttpRequestRetryHandler
/*  16:    */   implements HttpRequestRetryHandler
/*  17:    */ {
/*  18:    */   private final int retryCount;
/*  19:    */   private final boolean requestSentRetryEnabled;
/*  20:    */   
/*  21:    */   public DefaultHttpRequestRetryHandler(int retryCount, boolean requestSentRetryEnabled)
/*  22:    */   {
/*  23: 65 */     this.retryCount = retryCount;
/*  24: 66 */     this.requestSentRetryEnabled = requestSentRetryEnabled;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public DefaultHttpRequestRetryHandler()
/*  28:    */   {
/*  29: 73 */     this(3, false);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean retryRequest(IOException exception, int executionCount, HttpContext context)
/*  33:    */   {
/*  34: 83 */     if (exception == null) {
/*  35: 84 */       throw new IllegalArgumentException("Exception parameter may not be null");
/*  36:    */     }
/*  37: 86 */     if (context == null) {
/*  38: 87 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  39:    */     }
/*  40: 89 */     if (executionCount > this.retryCount) {
/*  41: 91 */       return false;
/*  42:    */     }
/*  43: 93 */     if ((exception instanceof InterruptedIOException)) {
/*  44: 95 */       return false;
/*  45:    */     }
/*  46: 97 */     if ((exception instanceof UnknownHostException)) {
/*  47: 99 */       return false;
/*  48:    */     }
/*  49:101 */     if ((exception instanceof ConnectException)) {
/*  50:103 */       return false;
/*  51:    */     }
/*  52:105 */     if ((exception instanceof SSLException)) {
/*  53:107 */       return false;
/*  54:    */     }
/*  55:110 */     HttpRequest request = (HttpRequest)context.getAttribute("http.request");
/*  56:112 */     if (handleAsIdempotent(request)) {
/*  57:114 */       return true;
/*  58:    */     }
/*  59:117 */     Boolean b = (Boolean)context.getAttribute("http.request_sent");
/*  60:    */     
/*  61:119 */     boolean sent = (b != null) && (b.booleanValue());
/*  62:121 */     if ((!sent) || (this.requestSentRetryEnabled)) {
/*  63:124 */       return true;
/*  64:    */     }
/*  65:127 */     return false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isRequestSentRetryEnabled()
/*  69:    */   {
/*  70:135 */     return this.requestSentRetryEnabled;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getRetryCount()
/*  74:    */   {
/*  75:142 */     return this.retryCount;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private boolean handleAsIdempotent(HttpRequest request)
/*  79:    */   {
/*  80:146 */     return !(request instanceof HttpEntityEnclosingRequest);
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultHttpRequestRetryHandler
 * JD-Core Version:    0.7.0.1
 */