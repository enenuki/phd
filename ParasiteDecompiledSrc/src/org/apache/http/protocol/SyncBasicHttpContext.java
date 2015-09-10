/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ public class SyncBasicHttpContext
/*  4:   */   extends BasicHttpContext
/*  5:   */ {
/*  6:   */   public SyncBasicHttpContext(HttpContext parentContext)
/*  7:   */   {
/*  8:38 */     super(parentContext);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public synchronized Object getAttribute(String id)
/* 12:   */   {
/* 13:42 */     return super.getAttribute(id);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public synchronized void setAttribute(String id, Object obj)
/* 17:   */   {
/* 18:46 */     super.setAttribute(id, obj);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public synchronized Object removeAttribute(String id)
/* 22:   */   {
/* 23:50 */     return super.removeAttribute(id);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.SyncBasicHttpContext
 * JD-Core Version:    0.7.0.1
 */