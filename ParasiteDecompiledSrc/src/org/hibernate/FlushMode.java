/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public enum FlushMode
/*  4:   */ {
/*  5:46 */   NEVER(0),  MANUAL(0),  COMMIT(5),  AUTO(10),  ALWAYS(20);
/*  6:   */   
/*  7:   */   private final int level;
/*  8:   */   
/*  9:   */   private FlushMode(int level)
/* 10:   */   {
/* 11:77 */     this.level = level;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public boolean lessThan(FlushMode other)
/* 15:   */   {
/* 16:81 */     return this.level < other.level;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static boolean isManualFlushMode(FlushMode mode)
/* 20:   */   {
/* 21:85 */     return MANUAL.level == mode.level;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.FlushMode
 * JD-Core Version:    0.7.0.1
 */