/*  1:   */ package org.apache.http.impl.conn;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.ThreadSafe;
/*  4:   */ import org.apache.http.conn.scheme.PlainSocketFactory;
/*  5:   */ import org.apache.http.conn.scheme.Scheme;
/*  6:   */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  7:   */ import org.apache.http.conn.ssl.SSLSocketFactory;
/*  8:   */ 
/*  9:   */ @ThreadSafe
/* 10:   */ public final class SchemeRegistryFactory
/* 11:   */ {
/* 12:   */   public static SchemeRegistry createDefault()
/* 13:   */   {
/* 14:42 */     SchemeRegistry registry = new SchemeRegistry();
/* 15:43 */     registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
/* 16:   */     
/* 17:45 */     registry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
/* 18:   */     
/* 19:47 */     return registry;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.SchemeRegistryFactory
 * JD-Core Version:    0.7.0.1
 */