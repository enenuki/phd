/*   1:    */ package org.hibernate.stat.internal;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import java.util.concurrent.atomic.AtomicLong;
/*   9:    */ import org.hibernate.cache.spi.CacheKey;
/*  10:    */ import org.hibernate.cache.spi.Region;
/*  11:    */ import org.hibernate.stat.SecondLevelCacheStatistics;
/*  12:    */ 
/*  13:    */ public class ConcurrentSecondLevelCacheStatisticsImpl
/*  14:    */   extends CategorizedStatistics
/*  15:    */   implements SecondLevelCacheStatistics
/*  16:    */ {
/*  17:    */   private final transient Region region;
/*  18: 42 */   private AtomicLong hitCount = new AtomicLong();
/*  19: 43 */   private AtomicLong missCount = new AtomicLong();
/*  20: 44 */   private AtomicLong putCount = new AtomicLong();
/*  21:    */   
/*  22:    */   ConcurrentSecondLevelCacheStatisticsImpl(Region region)
/*  23:    */   {
/*  24: 47 */     super(region.getName());
/*  25: 48 */     this.region = region;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public long getHitCount()
/*  29:    */   {
/*  30: 52 */     return this.hitCount.get();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public long getMissCount()
/*  34:    */   {
/*  35: 56 */     return this.missCount.get();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public long getPutCount()
/*  39:    */   {
/*  40: 60 */     return this.putCount.get();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public long getElementCountInMemory()
/*  44:    */   {
/*  45: 64 */     return this.region.getElementCountInMemory();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public long getElementCountOnDisk()
/*  49:    */   {
/*  50: 68 */     return this.region.getElementCountOnDisk();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public long getSizeInMemory()
/*  54:    */   {
/*  55: 72 */     return this.region.getSizeInMemory();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Map getEntries()
/*  59:    */   {
/*  60: 76 */     Map map = new HashMap();
/*  61: 77 */     Iterator iter = this.region.toMap().entrySet().iterator();
/*  62: 78 */     while (iter.hasNext())
/*  63:    */     {
/*  64: 79 */       Map.Entry me = (Map.Entry)iter.next();
/*  65: 80 */       map.put(((CacheKey)me.getKey()).getKey(), me.getValue());
/*  66:    */     }
/*  67: 82 */     return map;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String toString()
/*  71:    */   {
/*  72: 86 */     StringBuilder buf = new StringBuilder().append("SecondLevelCacheStatistics").append("[hitCount=").append(this.hitCount).append(",missCount=").append(this.missCount).append(",putCount=").append(this.putCount);
/*  73: 92 */     if (this.region != null) {
/*  74: 93 */       buf.append(",elementCountInMemory=").append(getElementCountInMemory()).append(",elementCountOnDisk=").append(getElementCountOnDisk()).append(",sizeInMemory=").append(getSizeInMemory());
/*  75:    */     }
/*  76: 97 */     buf.append(']');
/*  77: 98 */     return buf.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   void incrementHitCount()
/*  81:    */   {
/*  82:102 */     this.hitCount.getAndIncrement();
/*  83:    */   }
/*  84:    */   
/*  85:    */   void incrementMissCount()
/*  86:    */   {
/*  87:106 */     this.missCount.getAndIncrement();
/*  88:    */   }
/*  89:    */   
/*  90:    */   void incrementPutCount()
/*  91:    */   {
/*  92:110 */     this.putCount.getAndIncrement();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.internal.ConcurrentSecondLevelCacheStatisticsImpl
 * JD-Core Version:    0.7.0.1
 */