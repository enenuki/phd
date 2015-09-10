/*   1:    */ package org.hibernate.stat.internal;
/*   2:    */ 
/*   3:    */ import java.util.concurrent.atomic.AtomicLong;
/*   4:    */ import java.util.concurrent.locks.Lock;
/*   5:    */ import java.util.concurrent.locks.ReadWriteLock;
/*   6:    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*   7:    */ import org.hibernate.stat.QueryStatistics;
/*   8:    */ 
/*   9:    */ public class ConcurrentQueryStatisticsImpl
/*  10:    */   extends CategorizedStatistics
/*  11:    */   implements QueryStatistics
/*  12:    */ {
/*  13: 41 */   private final AtomicLong cacheHitCount = new AtomicLong();
/*  14: 42 */   private final AtomicLong cacheMissCount = new AtomicLong();
/*  15: 43 */   private final AtomicLong cachePutCount = new AtomicLong();
/*  16: 44 */   private final AtomicLong executionCount = new AtomicLong();
/*  17: 45 */   private final AtomicLong executionRowCount = new AtomicLong();
/*  18: 46 */   private final AtomicLong executionMaxTime = new AtomicLong();
/*  19: 47 */   private final AtomicLong executionMinTime = new AtomicLong(9223372036854775807L);
/*  20: 48 */   private final AtomicLong totalExecutionTime = new AtomicLong();
/*  21:    */   private final Lock readLock;
/*  22:    */   private final Lock writeLock;
/*  23:    */   
/*  24:    */   ConcurrentQueryStatisticsImpl(String query)
/*  25:    */   {
/*  26: 60 */     super(query);ReadWriteLock lock = new ReentrantReadWriteLock();this.readLock = lock.readLock();this.writeLock = lock.writeLock();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public long getExecutionCount()
/*  30:    */   {
/*  31: 67 */     return this.executionCount.get();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public long getCacheHitCount()
/*  35:    */   {
/*  36: 74 */     return this.cacheHitCount.get();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public long getCachePutCount()
/*  40:    */   {
/*  41: 78 */     return this.cachePutCount.get();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public long getCacheMissCount()
/*  45:    */   {
/*  46: 82 */     return this.cacheMissCount.get();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public long getExecutionRowCount()
/*  50:    */   {
/*  51: 95 */     return this.executionRowCount.get();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public long getExecutionAvgTime()
/*  55:    */   {
/*  56:105 */     this.writeLock.lock();
/*  57:    */     try
/*  58:    */     {
/*  59:107 */       long avgExecutionTime = 0L;
/*  60:108 */       if (this.executionCount.get() > 0L) {
/*  61:109 */         avgExecutionTime = this.totalExecutionTime.get() / this.executionCount.get();
/*  62:    */       }
/*  63:111 */       return avgExecutionTime;
/*  64:    */     }
/*  65:    */     finally
/*  66:    */     {
/*  67:113 */       this.writeLock.unlock();
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public long getExecutionMaxTime()
/*  72:    */   {
/*  73:121 */     return this.executionMaxTime.get();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public long getExecutionMinTime()
/*  77:    */   {
/*  78:128 */     return this.executionMinTime.get();
/*  79:    */   }
/*  80:    */   
/*  81:    */   void executed(long rows, long time)
/*  82:    */   {
/*  83:140 */     this.readLock.lock();
/*  84:    */     try
/*  85:    */     {
/*  86:143 */       for (long old = this.executionMinTime.get(); (time < old) && (!this.executionMinTime.compareAndSet(old, time)); old = this.executionMinTime.get()) {}
/*  87:144 */       for (long old = this.executionMaxTime.get(); (time > old) && (!this.executionMaxTime.compareAndSet(old, time)); old = this.executionMaxTime.get()) {}
/*  88:145 */       this.executionCount.getAndIncrement();
/*  89:146 */       this.executionRowCount.addAndGet(rows);
/*  90:147 */       this.totalExecutionTime.addAndGet(time);
/*  91:    */     }
/*  92:    */     finally
/*  93:    */     {
/*  94:149 */       this.readLock.unlock();
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String toString()
/*  99:    */   {
/* 100:154 */     return "QueryStatistics" + "[cacheHitCount=" + this.cacheHitCount + ",cacheMissCount=" + this.cacheMissCount + ",cachePutCount=" + this.cachePutCount + ",executionCount=" + this.executionCount + ",executionRowCount=" + this.executionRowCount + ",executionAvgTime=" + getExecutionAvgTime() + ",executionMaxTime=" + this.executionMaxTime + ",executionMinTime=" + this.executionMinTime + ']';
/* 101:    */   }
/* 102:    */   
/* 103:    */   void incrementCacheHitCount()
/* 104:    */   {
/* 105:169 */     this.cacheHitCount.getAndIncrement();
/* 106:    */   }
/* 107:    */   
/* 108:    */   void incrementCacheMissCount()
/* 109:    */   {
/* 110:173 */     this.cacheMissCount.getAndIncrement();
/* 111:    */   }
/* 112:    */   
/* 113:    */   void incrementCachePutCount()
/* 114:    */   {
/* 115:177 */     this.cachePutCount.getAndIncrement();
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.internal.ConcurrentQueryStatisticsImpl
 * JD-Core Version:    0.7.0.1
 */