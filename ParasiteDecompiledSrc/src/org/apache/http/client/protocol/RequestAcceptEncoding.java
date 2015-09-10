/*  1:   */ package org.apache.http.client.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpException;
/*  5:   */ import org.apache.http.HttpRequest;
/*  6:   */ import org.apache.http.HttpRequestInterceptor;
/*  7:   */ import org.apache.http.annotation.Immutable;
/*  8:   */ import org.apache.http.protocol.HttpContext;
/*  9:   */ 
/* 10:   */ @Immutable
/* 11:   */ public class RequestAcceptEncoding
/* 12:   */   implements HttpRequestInterceptor
/* 13:   */ {
/* 14:   */   public void process(HttpRequest request, HttpContext context)
/* 15:   */     throws HttpException, IOException
/* 16:   */   {
/* 17:55 */     request.addHeader("Accept-Encoding", "gzip,deflate");
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.RequestAcceptEncoding
 * JD-Core Version:    0.7.0.1
 */