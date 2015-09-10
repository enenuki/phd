/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import org.apache.http.HeaderElement;
/*  4:   */ import org.apache.http.HeaderElementIterator;
/*  5:   */ import org.apache.http.HttpResponse;
/*  6:   */ import org.apache.http.annotation.Immutable;
/*  7:   */ import org.apache.http.conn.ConnectionKeepAliveStrategy;
/*  8:   */ import org.apache.http.message.BasicHeaderElementIterator;
/*  9:   */ import org.apache.http.protocol.HttpContext;
/* 10:   */ 
/* 11:   */ @Immutable
/* 12:   */ public class DefaultConnectionKeepAliveStrategy
/* 13:   */   implements ConnectionKeepAliveStrategy
/* 14:   */ {
/* 15:   */   public long getKeepAliveDuration(HttpResponse response, HttpContext context)
/* 16:   */   {
/* 17:52 */     if (response == null) {
/* 18:53 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 19:   */     }
/* 20:55 */     HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));
/* 21:57 */     while (it.hasNext())
/* 22:   */     {
/* 23:58 */       HeaderElement he = it.nextElement();
/* 24:59 */       String param = he.getName();
/* 25:60 */       String value = he.getValue();
/* 26:61 */       if ((value != null) && (param.equalsIgnoreCase("timeout"))) {
/* 27:   */         try
/* 28:   */         {
/* 29:63 */           return Long.parseLong(value) * 1000L;
/* 30:   */         }
/* 31:   */         catch (NumberFormatException ignore) {}
/* 32:   */       }
/* 33:   */     }
/* 34:68 */     return -1L;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy
 * JD-Core Version:    0.7.0.1
 */