/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpEntity;
/*  5:   */ import org.apache.http.HttpEntityEnclosingRequest;
/*  6:   */ import org.apache.http.HttpException;
/*  7:   */ import org.apache.http.HttpRequest;
/*  8:   */ import org.apache.http.HttpRequestInterceptor;
/*  9:   */ import org.apache.http.HttpVersion;
/* 10:   */ import org.apache.http.ProtocolVersion;
/* 11:   */ import org.apache.http.RequestLine;
/* 12:   */ import org.apache.http.params.HttpProtocolParams;
/* 13:   */ 
/* 14:   */ public class RequestExpectContinue
/* 15:   */   implements HttpRequestInterceptor
/* 16:   */ {
/* 17:   */   public void process(HttpRequest request, HttpContext context)
/* 18:   */     throws HttpException, IOException
/* 19:   */   {
/* 20:62 */     if (request == null) {
/* 21:63 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 22:   */     }
/* 23:65 */     if ((request instanceof HttpEntityEnclosingRequest))
/* 24:   */     {
/* 25:66 */       HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
/* 26:68 */       if ((entity != null) && (entity.getContentLength() != 0L))
/* 27:   */       {
/* 28:69 */         ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
/* 29:70 */         if ((HttpProtocolParams.useExpectContinue(request.getParams())) && (!ver.lessEquals(HttpVersion.HTTP_1_0))) {
/* 30:72 */           request.addHeader("Expect", "100-continue");
/* 31:   */         }
/* 32:   */       }
/* 33:   */     }
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.RequestExpectContinue
 * JD-Core Version:    0.7.0.1
 */