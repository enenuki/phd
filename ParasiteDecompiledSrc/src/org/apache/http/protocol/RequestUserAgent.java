/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpException;
/*  5:   */ import org.apache.http.HttpRequest;
/*  6:   */ import org.apache.http.HttpRequestInterceptor;
/*  7:   */ import org.apache.http.params.HttpProtocolParams;
/*  8:   */ 
/*  9:   */ public class RequestUserAgent
/* 10:   */   implements HttpRequestInterceptor
/* 11:   */ {
/* 12:   */   public void process(HttpRequest request, HttpContext context)
/* 13:   */     throws HttpException, IOException
/* 14:   */   {
/* 15:57 */     if (request == null) {
/* 16:58 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 17:   */     }
/* 18:60 */     if (!request.containsHeader("User-Agent"))
/* 19:   */     {
/* 20:61 */       String useragent = HttpProtocolParams.getUserAgent(request.getParams());
/* 21:62 */       if (useragent != null) {
/* 22:63 */         request.addHeader("User-Agent", useragent);
/* 23:   */       }
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.RequestUserAgent
 * JD-Core Version:    0.7.0.1
 */