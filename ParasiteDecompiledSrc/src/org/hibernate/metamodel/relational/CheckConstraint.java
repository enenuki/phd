/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ public class CheckConstraint
/*  4:   */ {
/*  5:   */   private final Table table;
/*  6:   */   private String name;
/*  7:   */   private String condition;
/*  8:   */   
/*  9:   */   public CheckConstraint(Table table)
/* 10:   */   {
/* 11:37 */     this.table = table;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public CheckConstraint(Table table, String name, String condition)
/* 15:   */   {
/* 16:41 */     this.table = table;
/* 17:42 */     this.name = name;
/* 18:43 */     this.condition = condition;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getCondition()
/* 22:   */   {
/* 23:47 */     return this.condition;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setCondition(String condition)
/* 27:   */   {
/* 28:51 */     this.condition = condition;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Table getTable()
/* 32:   */   {
/* 33:60 */     return this.table;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getName()
/* 37:   */   {
/* 38:69 */     return this.name;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.CheckConstraint
 * JD-Core Version:    0.7.0.1
 */