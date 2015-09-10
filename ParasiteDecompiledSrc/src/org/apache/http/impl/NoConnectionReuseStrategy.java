/*  1:   */ package org.apache.http.impl;
/*  2:   */ 
/*  3:   */ import org.apache.http.ConnectionReuseStrategy;
/*  4:   */ import org.apache.http.HttpResponse;
/*  5:   */ import org.apache.http.protocol.HttpContext;
/*  6:   */ 
/*  7:   */ public class NoConnectionReuseStrategy
/*  8:   */   implements ConnectionReuseStrategy
/*  9:   */ {
/* 10:   */   public boolean keepAlive(HttpResponse response, HttpContext context)
/* 11:   */   {
/* 12:46 */     if (response == null) {
/* 13:47 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 14:   */     }
/* 15:49 */     if (context == null) {
/* 16:50 */       throw new IllegalArgumentException("HTTP context may not be null");
/* 17:   */     }
/* 18:53 */     return false;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.NoConnectionReuseStrategy
 * JD-Core Version:    0.7.0.1
 */