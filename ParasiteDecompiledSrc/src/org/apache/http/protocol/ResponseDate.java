/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpException;
/*  5:   */ import org.apache.http.HttpResponse;
/*  6:   */ import org.apache.http.HttpResponseInterceptor;
/*  7:   */ import org.apache.http.StatusLine;
/*  8:   */ 
/*  9:   */ public class ResponseDate
/* 10:   */   implements HttpResponseInterceptor
/* 11:   */ {
/* 12:46 */   private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();
/* 13:   */   
/* 14:   */   public void process(HttpResponse response, HttpContext context)
/* 15:   */     throws HttpException, IOException
/* 16:   */   {
/* 17:54 */     if (response == null) {
/* 18:55 */       throw new IllegalArgumentException("HTTP response may not be null.");
/* 19:   */     }
/* 20:58 */     int status = response.getStatusLine().getStatusCode();
/* 21:59 */     if ((status >= 200) && (!response.containsHeader("Date")))
/* 22:   */     {
/* 23:61 */       String httpdate = DATE_GENERATOR.getCurrentDate();
/* 24:62 */       response.setHeader("Date", httpdate);
/* 25:   */     }
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.ResponseDate
 * JD-Core Version:    0.7.0.1
 */