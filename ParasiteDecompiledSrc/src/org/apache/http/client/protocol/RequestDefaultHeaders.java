/*  1:   */ package org.apache.http.client.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Collection;
/*  5:   */ import org.apache.http.Header;
/*  6:   */ import org.apache.http.HttpException;
/*  7:   */ import org.apache.http.HttpRequest;
/*  8:   */ import org.apache.http.HttpRequestInterceptor;
/*  9:   */ import org.apache.http.RequestLine;
/* 10:   */ import org.apache.http.annotation.Immutable;
/* 11:   */ import org.apache.http.params.HttpParams;
/* 12:   */ import org.apache.http.protocol.HttpContext;
/* 13:   */ 
/* 14:   */ @Immutable
/* 15:   */ public class RequestDefaultHeaders
/* 16:   */   implements HttpRequestInterceptor
/* 17:   */ {
/* 18:   */   public void process(HttpRequest request, HttpContext context)
/* 19:   */     throws HttpException, IOException
/* 20:   */   {
/* 21:56 */     if (request == null) {
/* 22:57 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 23:   */     }
/* 24:60 */     String method = request.getRequestLine().getMethod();
/* 25:61 */     if (method.equalsIgnoreCase("CONNECT")) {
/* 26:62 */       return;
/* 27:   */     }
/* 28:67 */     Collection<Header> defHeaders = (Collection)request.getParams().getParameter("http.default-headers");
/* 29:70 */     if (defHeaders != null) {
/* 30:71 */       for (Header defHeader : defHeaders) {
/* 31:72 */         request.addHeader(defHeader);
/* 32:   */       }
/* 33:   */     }
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.RequestDefaultHeaders
 * JD-Core Version:    0.7.0.1
 */