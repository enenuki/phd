/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import org.apache.http.HttpRequest;
/*   4:    */ import org.apache.http.ProtocolVersion;
/*   5:    */ import org.apache.http.RequestLine;
/*   6:    */ import org.apache.http.params.HttpProtocolParams;
/*   7:    */ 
/*   8:    */ public class BasicHttpRequest
/*   9:    */   extends AbstractHttpMessage
/*  10:    */   implements HttpRequest
/*  11:    */ {
/*  12:    */   private final String method;
/*  13:    */   private final String uri;
/*  14:    */   private RequestLine requestline;
/*  15:    */   
/*  16:    */   public BasicHttpRequest(String method, String uri)
/*  17:    */   {
/*  18: 65 */     if (method == null) {
/*  19: 66 */       throw new IllegalArgumentException("Method name may not be null");
/*  20:    */     }
/*  21: 68 */     if (uri == null) {
/*  22: 69 */       throw new IllegalArgumentException("Request URI may not be null");
/*  23:    */     }
/*  24: 71 */     this.method = method;
/*  25: 72 */     this.uri = uri;
/*  26: 73 */     this.requestline = null;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BasicHttpRequest(String method, String uri, ProtocolVersion ver)
/*  30:    */   {
/*  31: 85 */     this(new BasicRequestLine(method, uri, ver));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public BasicHttpRequest(RequestLine requestline)
/*  35:    */   {
/*  36: 95 */     if (requestline == null) {
/*  37: 96 */       throw new IllegalArgumentException("Request line may not be null");
/*  38:    */     }
/*  39: 98 */     this.requestline = requestline;
/*  40: 99 */     this.method = requestline.getMethod();
/*  41:100 */     this.uri = requestline.getUri();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ProtocolVersion getProtocolVersion()
/*  45:    */   {
/*  46:112 */     return getRequestLine().getProtocolVersion();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public RequestLine getRequestLine()
/*  50:    */   {
/*  51:123 */     if (this.requestline == null)
/*  52:    */     {
/*  53:124 */       ProtocolVersion ver = HttpProtocolParams.getVersion(getParams());
/*  54:125 */       this.requestline = new BasicRequestLine(this.method, this.uri, ver);
/*  55:    */     }
/*  56:127 */     return this.requestline;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String toString()
/*  60:    */   {
/*  61:131 */     return this.method + " " + this.uri + " " + this.headergroup;
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHttpRequest
 * JD-Core Version:    0.7.0.1
 */