/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.NotThreadSafe;
/*  4:   */ import org.apache.http.conn.routing.HttpRoute;
/*  5:   */ 
/*  6:   */ @NotThreadSafe
/*  7:   */ public class RoutedRequest
/*  8:   */ {
/*  9:   */   protected final RequestWrapper request;
/* 10:   */   protected final HttpRoute route;
/* 11:   */   
/* 12:   */   public RoutedRequest(RequestWrapper req, HttpRoute route)
/* 13:   */   {
/* 14:53 */     this.request = req;
/* 15:54 */     this.route = route;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public final RequestWrapper getRequest()
/* 19:   */   {
/* 20:58 */     return this.request;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public final HttpRoute getRoute()
/* 24:   */   {
/* 25:62 */     return this.route;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.RoutedRequest
 * JD-Core Version:    0.7.0.1
 */