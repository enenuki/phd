/*   1:    */ package org.hibernate.stat.internal;
/*   2:    */ 
/*   3:    */ import java.util.concurrent.atomic.AtomicLong;
/*   4:    */ import org.hibernate.stat.EntityStatistics;
/*   5:    */ 
/*   6:    */ public class ConcurrentEntityStatisticsImpl
/*   7:    */   extends CategorizedStatistics
/*   8:    */   implements EntityStatistics
/*   9:    */ {
/*  10:    */   ConcurrentEntityStatisticsImpl(String name)
/*  11:    */   {
/*  12: 38 */     super(name);
/*  13:    */   }
/*  14:    */   
/*  15: 41 */   private AtomicLong loadCount = new AtomicLong();
/*  16: 42 */   private AtomicLong updateCount = new AtomicLong();
/*  17: 43 */   private AtomicLong insertCount = new AtomicLong();
/*  18: 44 */   private AtomicLong deleteCount = new AtomicLong();
/*  19: 45 */   private AtomicLong fetchCount = new AtomicLong();
/*  20: 46 */   private AtomicLong optimisticFailureCount = new AtomicLong();
/*  21:    */   
/*  22:    */   public long getDeleteCount()
/*  23:    */   {
/*  24: 49 */     return this.deleteCount.get();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public long getInsertCount()
/*  28:    */   {
/*  29: 53 */     return this.insertCount.get();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public long getLoadCount()
/*  33:    */   {
/*  34: 57 */     return this.loadCount.get();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public long getUpdateCount()
/*  38:    */   {
/*  39: 61 */     return this.updateCount.get();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public long getFetchCount()
/*  43:    */   {
/*  44: 65 */     return this.fetchCount.get();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public long getOptimisticFailureCount()
/*  48:    */   {
/*  49: 69 */     return this.optimisticFailureCount.get();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String toString()
/*  53:    */   {
/*  54: 73 */     return "EntityStatistics" + "[loadCount=" + this.loadCount + ",updateCount=" + this.updateCount + ",insertCount=" + this.insertCount + ",deleteCount=" + this.deleteCount + ",fetchCount=" + this.fetchCount + ",optimisticLockFailureCount=" + this.optimisticFailureCount + ']';
/*  55:    */   }
/*  56:    */   
/*  57:    */   void incrementLoadCount()
/*  58:    */   {
/*  59: 86 */     this.loadCount.getAndIncrement();
/*  60:    */   }
/*  61:    */   
/*  62:    */   void incrementFetchCount()
/*  63:    */   {
/*  64: 90 */     this.fetchCount.getAndIncrement();
/*  65:    */   }
/*  66:    */   
/*  67:    */   void incrementUpdateCount()
/*  68:    */   {
/*  69: 94 */     this.updateCount.getAndIncrement();
/*  70:    */   }
/*  71:    */   
/*  72:    */   void incrementInsertCount()
/*  73:    */   {
/*  74: 98 */     this.insertCount.getAndIncrement();
/*  75:    */   }
/*  76:    */   
/*  77:    */   void incrementDeleteCount()
/*  78:    */   {
/*  79:102 */     this.deleteCount.getAndIncrement();
/*  80:    */   }
/*  81:    */   
/*  82:    */   void incrementOptimisticFailureCount()
/*  83:    */   {
/*  84:106 */     this.optimisticFailureCount.getAndIncrement();
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.internal.ConcurrentEntityStatisticsImpl
 * JD-Core Version:    0.7.0.1
 */