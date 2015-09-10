/*   1:    */ package org.apache.http.impl;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.http.HttpResponse;
/*   5:    */ import org.apache.http.HttpResponseFactory;
/*   6:    */ import org.apache.http.ProtocolVersion;
/*   7:    */ import org.apache.http.ReasonPhraseCatalog;
/*   8:    */ import org.apache.http.StatusLine;
/*   9:    */ import org.apache.http.message.BasicHttpResponse;
/*  10:    */ import org.apache.http.message.BasicStatusLine;
/*  11:    */ import org.apache.http.protocol.HttpContext;
/*  12:    */ 
/*  13:    */ public class DefaultHttpResponseFactory
/*  14:    */   implements HttpResponseFactory
/*  15:    */ {
/*  16:    */   protected final ReasonPhraseCatalog reasonCatalog;
/*  17:    */   
/*  18:    */   public DefaultHttpResponseFactory(ReasonPhraseCatalog catalog)
/*  19:    */   {
/*  20: 59 */     if (catalog == null) {
/*  21: 60 */       throw new IllegalArgumentException("Reason phrase catalog must not be null.");
/*  22:    */     }
/*  23: 63 */     this.reasonCatalog = catalog;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public DefaultHttpResponseFactory()
/*  27:    */   {
/*  28: 71 */     this(EnglishReasonPhraseCatalog.INSTANCE);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public HttpResponse newHttpResponse(ProtocolVersion ver, int status, HttpContext context)
/*  32:    */   {
/*  33: 79 */     if (ver == null) {
/*  34: 80 */       throw new IllegalArgumentException("HTTP version may not be null");
/*  35:    */     }
/*  36: 82 */     Locale loc = determineLocale(context);
/*  37: 83 */     String reason = this.reasonCatalog.getReason(status, loc);
/*  38: 84 */     StatusLine statusline = new BasicStatusLine(ver, status, reason);
/*  39: 85 */     return new BasicHttpResponse(statusline, this.reasonCatalog, loc);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public HttpResponse newHttpResponse(StatusLine statusline, HttpContext context)
/*  43:    */   {
/*  44: 92 */     if (statusline == null) {
/*  45: 93 */       throw new IllegalArgumentException("Status line may not be null");
/*  46:    */     }
/*  47: 95 */     Locale loc = determineLocale(context);
/*  48: 96 */     return new BasicHttpResponse(statusline, this.reasonCatalog, loc);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected Locale determineLocale(HttpContext context)
/*  52:    */   {
/*  53:110 */     return Locale.getDefault();
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.DefaultHttpResponseFactory
 * JD-Core Version:    0.7.0.1
 */