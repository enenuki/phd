/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class BasicHttpContext
/*  7:   */   implements HttpContext
/*  8:   */ {
/*  9:   */   private final HttpContext parentContext;
/* 10:44 */   private Map map = null;
/* 11:   */   
/* 12:   */   public BasicHttpContext()
/* 13:   */   {
/* 14:47 */     this(null);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public BasicHttpContext(HttpContext parentContext)
/* 18:   */   {
/* 19:52 */     this.parentContext = parentContext;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object getAttribute(String id)
/* 23:   */   {
/* 24:56 */     if (id == null) {
/* 25:57 */       throw new IllegalArgumentException("Id may not be null");
/* 26:   */     }
/* 27:59 */     Object obj = null;
/* 28:60 */     if (this.map != null) {
/* 29:61 */       obj = this.map.get(id);
/* 30:   */     }
/* 31:63 */     if ((obj == null) && (this.parentContext != null)) {
/* 32:64 */       obj = this.parentContext.getAttribute(id);
/* 33:   */     }
/* 34:66 */     return obj;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setAttribute(String id, Object obj)
/* 38:   */   {
/* 39:70 */     if (id == null) {
/* 40:71 */       throw new IllegalArgumentException("Id may not be null");
/* 41:   */     }
/* 42:73 */     if (this.map == null) {
/* 43:74 */       this.map = new HashMap();
/* 44:   */     }
/* 45:76 */     this.map.put(id, obj);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Object removeAttribute(String id)
/* 49:   */   {
/* 50:80 */     if (id == null) {
/* 51:81 */       throw new IllegalArgumentException("Id may not be null");
/* 52:   */     }
/* 53:83 */     if (this.map != null) {
/* 54:84 */       return this.map.remove(id);
/* 55:   */     }
/* 56:86 */     return null;
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.BasicHttpContext
 * JD-Core Version:    0.7.0.1
 */