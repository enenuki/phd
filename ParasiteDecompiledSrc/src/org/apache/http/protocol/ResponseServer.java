/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpException;
/*  5:   */ import org.apache.http.HttpResponse;
/*  6:   */ import org.apache.http.HttpResponseInterceptor;
/*  7:   */ import org.apache.http.params.HttpParams;
/*  8:   */ 
/*  9:   */ public class ResponseServer
/* 10:   */   implements HttpResponseInterceptor
/* 11:   */ {
/* 12:   */   public void process(HttpResponse response, HttpContext context)
/* 13:   */     throws HttpException, IOException
/* 14:   */   {
/* 15:57 */     if (response == null) {
/* 16:58 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 17:   */     }
/* 18:60 */     if (!response.containsHeader("Server"))
/* 19:   */     {
/* 20:61 */       String s = (String)response.getParams().getParameter("http.origin-server");
/* 21:63 */       if (s != null) {
/* 22:64 */         response.addHeader("Server", s);
/* 23:   */       }
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.ResponseServer
 * JD-Core Version:    0.7.0.1
 */