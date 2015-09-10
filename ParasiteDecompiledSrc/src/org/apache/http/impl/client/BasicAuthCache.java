/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import org.apache.http.HttpHost;
/*  5:   */ import org.apache.http.annotation.NotThreadSafe;
/*  6:   */ import org.apache.http.auth.AuthScheme;
/*  7:   */ import org.apache.http.client.AuthCache;
/*  8:   */ 
/*  9:   */ @NotThreadSafe
/* 10:   */ public class BasicAuthCache
/* 11:   */   implements AuthCache
/* 12:   */ {
/* 13:   */   private final HashMap<HttpHost, AuthScheme> map;
/* 14:   */   
/* 15:   */   public BasicAuthCache()
/* 16:   */   {
/* 17:51 */     this.map = new HashMap();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void put(HttpHost host, AuthScheme authScheme)
/* 21:   */   {
/* 22:55 */     if (host == null) {
/* 23:56 */       throw new IllegalArgumentException("HTTP host may not be null");
/* 24:   */     }
/* 25:58 */     this.map.put(host, authScheme);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public AuthScheme get(HttpHost host)
/* 29:   */   {
/* 30:62 */     if (host == null) {
/* 31:63 */       throw new IllegalArgumentException("HTTP host may not be null");
/* 32:   */     }
/* 33:65 */     return (AuthScheme)this.map.get(host);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void remove(HttpHost host)
/* 37:   */   {
/* 38:69 */     if (host == null) {
/* 39:70 */       throw new IllegalArgumentException("HTTP host may not be null");
/* 40:   */     }
/* 41:72 */     this.map.remove(host);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void clear()
/* 45:   */   {
/* 46:76 */     this.map.clear();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:81 */     return this.map.toString();
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.BasicAuthCache
 * JD-Core Version:    0.7.0.1
 */