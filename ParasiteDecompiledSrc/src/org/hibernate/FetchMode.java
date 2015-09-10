/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public enum FetchMode
/*  4:   */ {
/*  5:41 */   DEFAULT,  JOIN,  SELECT;
/*  6:   */   
/*  7:   */   /**
/*  8:   */    * @deprecated
/*  9:   */    */
/* 10:57 */   public static final FetchMode LAZY = SELECT;
/* 11:   */   /**
/* 12:   */    * @deprecated
/* 13:   */    */
/* 14:63 */   public static final FetchMode EAGER = JOIN;
/* 15:   */   
/* 16:   */   private FetchMode() {}
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.FetchMode
 * JD-Core Version:    0.7.0.1
 */