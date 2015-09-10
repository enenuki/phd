/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import org.apache.http.HttpException;
/*  4:   */ import org.apache.http.HttpResponse;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class TunnelRefusedException
/*  9:   */   extends HttpException
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -8646722842745617323L;
/* 12:   */   private final HttpResponse response;
/* 13:   */   
/* 14:   */   public TunnelRefusedException(String message, HttpResponse response)
/* 15:   */   {
/* 16:48 */     super(message);
/* 17:49 */     this.response = response;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public HttpResponse getResponse()
/* 21:   */   {
/* 22:53 */     return this.response;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.TunnelRefusedException
 * JD-Core Version:    0.7.0.1
 */