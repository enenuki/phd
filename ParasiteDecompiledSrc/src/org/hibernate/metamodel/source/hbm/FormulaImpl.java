/*  1:   */ package org.hibernate.metamodel.source.hbm;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.source.binder.DerivedValueSource;
/*  4:   */ 
/*  5:   */ class FormulaImpl
/*  6:   */   implements DerivedValueSource
/*  7:   */ {
/*  8:   */   private String tableName;
/*  9:   */   private final String expression;
/* 10:   */   
/* 11:   */   FormulaImpl(String tableName, String expression)
/* 12:   */   {
/* 13:36 */     this.tableName = tableName;
/* 14:37 */     this.expression = expression;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getExpression()
/* 18:   */   {
/* 19:42 */     return this.expression;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getContainingTableName()
/* 23:   */   {
/* 24:47 */     return this.tableName;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.FormulaImpl
 * JD-Core Version:    0.7.0.1
 */