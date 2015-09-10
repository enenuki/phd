/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ public final class DefaultedHttpContext
/*  4:   */   implements HttpContext
/*  5:   */ {
/*  6:   */   private final HttpContext local;
/*  7:   */   private final HttpContext defaults;
/*  8:   */   
/*  9:   */   public DefaultedHttpContext(HttpContext local, HttpContext defaults)
/* 10:   */   {
/* 11:45 */     if (local == null) {
/* 12:46 */       throw new IllegalArgumentException("HTTP context may not be null");
/* 13:   */     }
/* 14:48 */     this.local = local;
/* 15:49 */     this.defaults = defaults;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Object getAttribute(String id)
/* 19:   */   {
/* 20:53 */     Object obj = this.local.getAttribute(id);
/* 21:54 */     if (obj == null) {
/* 22:55 */       return this.defaults.getAttribute(id);
/* 23:   */     }
/* 24:57 */     return obj;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Object removeAttribute(String id)
/* 28:   */   {
/* 29:62 */     return this.local.removeAttribute(id);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setAttribute(String id, Object obj)
/* 33:   */   {
/* 34:66 */     this.local.setAttribute(id, obj);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public HttpContext getDefaults()
/* 38:   */   {
/* 39:70 */     return this.defaults;
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.DefaultedHttpContext
 * JD-Core Version:    0.7.0.1
 */