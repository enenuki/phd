/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpEntity;
/*  5:   */ import org.apache.http.HttpEntityEnclosingRequest;
/*  6:   */ import org.apache.http.HttpException;
/*  7:   */ import org.apache.http.HttpRequest;
/*  8:   */ import org.apache.http.HttpRequestInterceptor;
/*  9:   */ import org.apache.http.HttpVersion;
/* 10:   */ import org.apache.http.ProtocolException;
/* 11:   */ import org.apache.http.ProtocolVersion;
/* 12:   */ import org.apache.http.RequestLine;
/* 13:   */ 
/* 14:   */ public class RequestContent
/* 15:   */   implements HttpRequestInterceptor
/* 16:   */ {
/* 17:   */   public void process(HttpRequest request, HttpContext context)
/* 18:   */     throws HttpException, IOException
/* 19:   */   {
/* 20:59 */     if (request == null) {
/* 21:60 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 22:   */     }
/* 23:62 */     if ((request instanceof HttpEntityEnclosingRequest))
/* 24:   */     {
/* 25:63 */       if (request.containsHeader("Transfer-Encoding")) {
/* 26:64 */         throw new ProtocolException("Transfer-encoding header already present");
/* 27:   */       }
/* 28:66 */       if (request.containsHeader("Content-Length")) {
/* 29:67 */         throw new ProtocolException("Content-Length header already present");
/* 30:   */       }
/* 31:69 */       ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
/* 32:70 */       HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
/* 33:71 */       if (entity == null)
/* 34:   */       {
/* 35:72 */         request.addHeader("Content-Length", "0");
/* 36:73 */         return;
/* 37:   */       }
/* 38:76 */       if ((entity.isChunked()) || (entity.getContentLength() < 0L))
/* 39:   */       {
/* 40:77 */         if (ver.lessEquals(HttpVersion.HTTP_1_0)) {
/* 41:78 */           throw new ProtocolException("Chunked transfer encoding not allowed for " + ver);
/* 42:   */         }
/* 43:81 */         request.addHeader("Transfer-Encoding", "chunked");
/* 44:   */       }
/* 45:   */       else
/* 46:   */       {
/* 47:83 */         request.addHeader("Content-Length", Long.toString(entity.getContentLength()));
/* 48:   */       }
/* 49:86 */       if ((entity.getContentType() != null) && (!request.containsHeader("Content-Type"))) {
/* 50:88 */         request.addHeader(entity.getContentType());
/* 51:   */       }
/* 52:91 */       if ((entity.getContentEncoding() != null) && (!request.containsHeader("Content-Encoding"))) {
/* 53:93 */         request.addHeader(entity.getContentEncoding());
/* 54:   */       }
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.RequestContent
 * JD-Core Version:    0.7.0.1
 */