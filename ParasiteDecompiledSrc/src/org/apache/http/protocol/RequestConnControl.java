/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpException;
/*  5:   */ import org.apache.http.HttpRequest;
/*  6:   */ import org.apache.http.HttpRequestInterceptor;
/*  7:   */ import org.apache.http.RequestLine;
/*  8:   */ 
/*  9:   */ public class RequestConnControl
/* 10:   */   implements HttpRequestInterceptor
/* 11:   */ {
/* 12:   */   public void process(HttpRequest request, HttpContext context)
/* 13:   */     throws HttpException, IOException
/* 14:   */   {
/* 15:52 */     if (request == null) {
/* 16:53 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 17:   */     }
/* 18:56 */     String method = request.getRequestLine().getMethod();
/* 19:57 */     if (method.equalsIgnoreCase("CONNECT")) {
/* 20:58 */       return;
/* 21:   */     }
/* 22:61 */     if (!request.containsHeader("Connection")) {
/* 23:64 */       request.addHeader("Connection", "Keep-Alive");
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.RequestConnControl
 * JD-Core Version:    0.7.0.1
 */