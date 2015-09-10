/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.ThreadSafe;
/*  4:   */ import org.apache.http.client.protocol.RequestAcceptEncoding;
/*  5:   */ import org.apache.http.client.protocol.ResponseContentEncoding;
/*  6:   */ import org.apache.http.conn.ClientConnectionManager;
/*  7:   */ import org.apache.http.params.HttpParams;
/*  8:   */ import org.apache.http.protocol.BasicHttpProcessor;
/*  9:   */ 
/* 10:   */ @ThreadSafe
/* 11:   */ public class ContentEncodingHttpClient
/* 12:   */   extends DefaultHttpClient
/* 13:   */ {
/* 14:   */   public ContentEncodingHttpClient(ClientConnectionManager conman, HttpParams params)
/* 15:   */   {
/* 16:52 */     super(conman, params);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ContentEncodingHttpClient(HttpParams params)
/* 20:   */   {
/* 21:59 */     this(null, params);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ContentEncodingHttpClient()
/* 25:   */   {
/* 26:66 */     this(null);
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected BasicHttpProcessor createHttpProcessor()
/* 30:   */   {
/* 31:74 */     BasicHttpProcessor result = super.createHttpProcessor();
/* 32:   */     
/* 33:76 */     result.addRequestInterceptor(new RequestAcceptEncoding());
/* 34:77 */     result.addResponseInterceptor(new ResponseContentEncoding());
/* 35:   */     
/* 36:79 */     return result;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.ContentEncodingHttpClient
 * JD-Core Version:    0.7.0.1
 */