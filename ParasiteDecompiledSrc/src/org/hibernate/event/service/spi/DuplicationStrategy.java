/*  1:   */ package org.hibernate.event.service.spi;
/*  2:   */ 
/*  3:   */ public abstract interface DuplicationStrategy
/*  4:   */ {
/*  5:   */   public abstract boolean areMatch(Object paramObject1, Object paramObject2);
/*  6:   */   
/*  7:   */   public abstract Action getAction();
/*  8:   */   
/*  9:   */   public static enum Action
/* 10:   */   {
/* 11:37 */     ERROR,  KEEP_ORIGINAL,  REPLACE_ORIGINAL;
/* 12:   */     
/* 13:   */     private Action() {}
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.service.spi.DuplicationStrategy
 * JD-Core Version:    0.7.0.1
 */