/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ public class UniqueConstraintHolder
/*  4:   */ {
/*  5:   */   private String name;
/*  6:   */   private String[] columns;
/*  7:   */   
/*  8:   */   public String getName()
/*  9:   */   {
/* 10:40 */     return this.name;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public UniqueConstraintHolder setName(String name)
/* 14:   */   {
/* 15:44 */     this.name = name;
/* 16:45 */     return this;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String[] getColumns()
/* 20:   */   {
/* 21:49 */     return this.columns;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public UniqueConstraintHolder setColumns(String[] columns)
/* 25:   */   {
/* 26:53 */     this.columns = columns;
/* 27:54 */     return this;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.UniqueConstraintHolder
 * JD-Core Version:    0.7.0.1
 */