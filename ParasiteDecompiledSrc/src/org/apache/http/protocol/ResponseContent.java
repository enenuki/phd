/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpEntity;
/*  5:   */ import org.apache.http.HttpException;
/*  6:   */ import org.apache.http.HttpResponse;
/*  7:   */ import org.apache.http.HttpResponseInterceptor;
/*  8:   */ import org.apache.http.HttpVersion;
/*  9:   */ import org.apache.http.ProtocolException;
/* 10:   */ import org.apache.http.ProtocolVersion;
/* 11:   */ import org.apache.http.StatusLine;
/* 12:   */ 
/* 13:   */ public class ResponseContent
/* 14:   */   implements HttpResponseInterceptor
/* 15:   */ {
/* 16:   */   public void process(HttpResponse response, HttpContext context)
/* 17:   */     throws HttpException, IOException
/* 18:   */   {
/* 19:59 */     if (response == null) {
/* 20:60 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 21:   */     }
/* 22:62 */     if (response.containsHeader("Transfer-Encoding")) {
/* 23:63 */       throw new ProtocolException("Transfer-encoding header already present");
/* 24:   */     }
/* 25:65 */     if (response.containsHeader("Content-Length")) {
/* 26:66 */       throw new ProtocolException("Content-Length header already present");
/* 27:   */     }
/* 28:68 */     ProtocolVersion ver = response.getStatusLine().getProtocolVersion();
/* 29:69 */     HttpEntity entity = response.getEntity();
/* 30:70 */     if (entity != null)
/* 31:   */     {
/* 32:71 */       long len = entity.getContentLength();
/* 33:72 */       if ((entity.isChunked()) && (!ver.lessEquals(HttpVersion.HTTP_1_0))) {
/* 34:73 */         response.addHeader("Transfer-Encoding", "chunked");
/* 35:74 */       } else if (len >= 0L) {
/* 36:75 */         response.addHeader("Content-Length", Long.toString(entity.getContentLength()));
/* 37:   */       }
/* 38:78 */       if ((entity.getContentType() != null) && (!response.containsHeader("Content-Type"))) {
/* 39:80 */         response.addHeader(entity.getContentType());
/* 40:   */       }
/* 41:83 */       if ((entity.getContentEncoding() != null) && (!response.containsHeader("Content-Encoding"))) {
/* 42:85 */         response.addHeader(entity.getContentEncoding());
/* 43:   */       }
/* 44:   */     }
/* 45:   */     else
/* 46:   */     {
/* 47:88 */       int status = response.getStatusLine().getStatusCode();
/* 48:89 */       if ((status != 204) && (status != 304) && (status != 205)) {
/* 49:92 */         response.addHeader("Content-Length", "0");
/* 50:   */       }
/* 51:   */     }
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.ResponseContent
 * JD-Core Version:    0.7.0.1
 */