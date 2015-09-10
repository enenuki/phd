/*  1:   */ package org.apache.http.client;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class HttpResponseException
/*  7:   */   extends ClientProtocolException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -7186627969477257933L;
/* 10:   */   private final int statusCode;
/* 11:   */   
/* 12:   */   public HttpResponseException(int statusCode, String s)
/* 13:   */   {
/* 14:44 */     super(s);
/* 15:45 */     this.statusCode = statusCode;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getStatusCode()
/* 19:   */   {
/* 20:49 */     return this.statusCode;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.HttpResponseException
 * JD-Core Version:    0.7.0.1
 */