/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public enum ScrollMode
/*  4:   */ {
/*  5:41 */   FORWARD_ONLY(1003),  SCROLL_SENSITIVE(1005),  SCROLL_INSENSITIVE(1004);
/*  6:   */   
/*  7:   */   private final int resultSetType;
/*  8:   */   
/*  9:   */   private ScrollMode(int level)
/* 10:   */   {
/* 11:62 */     this.resultSetType = level;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int toResultSetType()
/* 15:   */   {
/* 16:70 */     return this.resultSetType;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean lessThan(ScrollMode other)
/* 20:   */   {
/* 21:75 */     return this.resultSetType < other.resultSetType;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.ScrollMode
 * JD-Core Version:    0.7.0.1
 */