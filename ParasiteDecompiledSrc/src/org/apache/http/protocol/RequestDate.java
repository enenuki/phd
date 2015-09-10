/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpEntityEnclosingRequest;
/*  5:   */ import org.apache.http.HttpException;
/*  6:   */ import org.apache.http.HttpRequest;
/*  7:   */ import org.apache.http.HttpRequestInterceptor;
/*  8:   */ 
/*  9:   */ public class RequestDate
/* 10:   */   implements HttpRequestInterceptor
/* 11:   */ {
/* 12:46 */   private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();
/* 13:   */   
/* 14:   */   public void process(HttpRequest request, HttpContext context)
/* 15:   */     throws HttpException, IOException
/* 16:   */   {
/* 17:54 */     if (request == null) {
/* 18:55 */       throw new IllegalArgumentException("HTTP request may not be null.");
/* 19:   */     }
/* 20:58 */     if (((request instanceof HttpEntityEnclosingRequest)) && (!request.containsHeader("Date")))
/* 21:   */     {
/* 22:60 */       String httpdate = DATE_GENERATOR.getCurrentDate();
/* 23:61 */       request.setHeader("Date", httpdate);
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.RequestDate
 * JD-Core Version:    0.7.0.1
 */