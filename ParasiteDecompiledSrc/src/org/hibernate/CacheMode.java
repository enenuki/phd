/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public enum CacheMode
/*  4:   */ {
/*  5:40 */   NORMAL(true, true),  IGNORE(false, false),  GET(false, true),  PUT(true, false),  REFRESH(true, false);
/*  6:   */   
/*  7:   */   private final boolean isPutEnabled;
/*  8:   */   private final boolean isGetEnabled;
/*  9:   */   
/* 10:   */   private CacheMode(boolean isPutEnabled, boolean isGetEnabled)
/* 11:   */   {
/* 12:69 */     this.isPutEnabled = isPutEnabled;
/* 13:70 */     this.isGetEnabled = isGetEnabled;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean isGetEnabled()
/* 17:   */   {
/* 18:74 */     return this.isGetEnabled;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isPutEnabled()
/* 22:   */   {
/* 23:78 */     return this.isPutEnabled;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.CacheMode
 * JD-Core Version:    0.7.0.1
 */