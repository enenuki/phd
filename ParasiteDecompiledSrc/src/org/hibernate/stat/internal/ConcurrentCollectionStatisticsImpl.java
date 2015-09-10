/*  1:   */ package org.hibernate.stat.internal;
/*  2:   */ 
/*  3:   */ import java.util.concurrent.atomic.AtomicLong;
/*  4:   */ import org.hibernate.stat.CollectionStatistics;
/*  5:   */ 
/*  6:   */ public class ConcurrentCollectionStatisticsImpl
/*  7:   */   extends CategorizedStatistics
/*  8:   */   implements CollectionStatistics
/*  9:   */ {
/* 10:   */   ConcurrentCollectionStatisticsImpl(String role)
/* 11:   */   {
/* 12:37 */     super(role);
/* 13:   */   }
/* 14:   */   
/* 15:40 */   private AtomicLong loadCount = new AtomicLong();
/* 16:41 */   private AtomicLong fetchCount = new AtomicLong();
/* 17:42 */   private AtomicLong updateCount = new AtomicLong();
/* 18:43 */   private AtomicLong removeCount = new AtomicLong();
/* 19:44 */   private AtomicLong recreateCount = new AtomicLong();
/* 20:   */   
/* 21:   */   public long getLoadCount()
/* 22:   */   {
/* 23:47 */     return this.loadCount.get();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public long getFetchCount()
/* 27:   */   {
/* 28:51 */     return this.fetchCount.get();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public long getRecreateCount()
/* 32:   */   {
/* 33:55 */     return this.recreateCount.get();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public long getRemoveCount()
/* 37:   */   {
/* 38:59 */     return this.removeCount.get();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public long getUpdateCount()
/* 42:   */   {
/* 43:63 */     return this.updateCount.get();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String toString()
/* 47:   */   {
/* 48:67 */     return "CollectionStatistics" + "[loadCount=" + this.loadCount + ",fetchCount=" + this.fetchCount + ",recreateCount=" + this.recreateCount + ",removeCount=" + this.removeCount + ",updateCount=" + this.updateCount + ']';
/* 49:   */   }
/* 50:   */   
/* 51:   */   void incrementLoadCount()
/* 52:   */   {
/* 53:79 */     this.loadCount.getAndIncrement();
/* 54:   */   }
/* 55:   */   
/* 56:   */   void incrementFetchCount()
/* 57:   */   {
/* 58:83 */     this.fetchCount.getAndIncrement();
/* 59:   */   }
/* 60:   */   
/* 61:   */   void incrementUpdateCount()
/* 62:   */   {
/* 63:87 */     this.updateCount.getAndIncrement();
/* 64:   */   }
/* 65:   */   
/* 66:   */   void incrementRecreateCount()
/* 67:   */   {
/* 68:91 */     this.recreateCount.getAndIncrement();
/* 69:   */   }
/* 70:   */   
/* 71:   */   void incrementRemoveCount()
/* 72:   */   {
/* 73:95 */     this.removeCount.getAndIncrement();
/* 74:   */   }
/* 75:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.internal.ConcurrentCollectionStatisticsImpl
 * JD-Core Version:    0.7.0.1
 */