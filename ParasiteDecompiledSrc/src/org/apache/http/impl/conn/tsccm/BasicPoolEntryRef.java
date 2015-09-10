/*  1:   */ package org.apache.http.impl.conn.tsccm;
/*  2:   */ 
/*  3:   */ import java.lang.ref.ReferenceQueue;
/*  4:   */ import java.lang.ref.WeakReference;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ import org.apache.http.conn.routing.HttpRoute;
/*  7:   */ 
/*  8:   */ @Immutable
/*  9:   */ public class BasicPoolEntryRef
/* 10:   */   extends WeakReference<BasicPoolEntry>
/* 11:   */ {
/* 12:   */   private final HttpRoute route;
/* 13:   */   
/* 14:   */   public BasicPoolEntryRef(BasicPoolEntry entry, ReferenceQueue<Object> queue)
/* 15:   */   {
/* 16:61 */     super(entry, queue);
/* 17:62 */     if (entry == null) {
/* 18:63 */       throw new IllegalArgumentException("Pool entry must not be null.");
/* 19:   */     }
/* 20:66 */     this.route = entry.getPlannedRoute();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public final HttpRoute getRoute()
/* 24:   */   {
/* 25:77 */     return this.route;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.BasicPoolEntryRef
 * JD-Core Version:    0.7.0.1
 */