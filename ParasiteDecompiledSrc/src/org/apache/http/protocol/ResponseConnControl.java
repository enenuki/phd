/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.Header;
/*  5:   */ import org.apache.http.HttpEntity;
/*  6:   */ import org.apache.http.HttpException;
/*  7:   */ import org.apache.http.HttpRequest;
/*  8:   */ import org.apache.http.HttpResponse;
/*  9:   */ import org.apache.http.HttpResponseInterceptor;
/* 10:   */ import org.apache.http.HttpVersion;
/* 11:   */ import org.apache.http.ProtocolVersion;
/* 12:   */ import org.apache.http.StatusLine;
/* 13:   */ 
/* 14:   */ public class ResponseConnControl
/* 15:   */   implements HttpResponseInterceptor
/* 16:   */ {
/* 17:   */   public void process(HttpResponse response, HttpContext context)
/* 18:   */     throws HttpException, IOException
/* 19:   */   {
/* 20:58 */     if (response == null) {
/* 21:59 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 22:   */     }
/* 23:61 */     if (context == null) {
/* 24:62 */       throw new IllegalArgumentException("HTTP context may not be null");
/* 25:   */     }
/* 26:65 */     int status = response.getStatusLine().getStatusCode();
/* 27:66 */     if ((status == 400) || (status == 408) || (status == 411) || (status == 413) || (status == 414) || (status == 503) || (status == 501))
/* 28:   */     {
/* 29:73 */       response.setHeader("Connection", "Close");
/* 30:74 */       return;
/* 31:   */     }
/* 32:78 */     HttpEntity entity = response.getEntity();
/* 33:79 */     if (entity != null)
/* 34:   */     {
/* 35:80 */       ProtocolVersion ver = response.getStatusLine().getProtocolVersion();
/* 36:81 */       if ((entity.getContentLength() < 0L) && ((!entity.isChunked()) || (ver.lessEquals(HttpVersion.HTTP_1_0))))
/* 37:   */       {
/* 38:83 */         response.setHeader("Connection", "Close");
/* 39:84 */         return;
/* 40:   */       }
/* 41:   */     }
/* 42:88 */     HttpRequest request = (HttpRequest)context.getAttribute("http.request");
/* 43:90 */     if (request != null)
/* 44:   */     {
/* 45:91 */       Header header = request.getFirstHeader("Connection");
/* 46:92 */       if (header != null) {
/* 47:93 */         response.setHeader("Connection", header.getValue());
/* 48:   */       }
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.ResponseConnControl
 * JD-Core Version:    0.7.0.1
 */