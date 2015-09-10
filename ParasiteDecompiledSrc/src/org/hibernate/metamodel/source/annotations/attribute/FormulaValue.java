/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute;
/*  2:   */ 
/*  3:   */ public class FormulaValue
/*  4:   */ {
/*  5:   */   private String tableName;
/*  6:   */   private final String expression;
/*  7:   */   
/*  8:   */   public FormulaValue(String tableName, String expression)
/*  9:   */   {
/* 10:11 */     this.tableName = tableName;
/* 11:12 */     this.expression = expression;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getExpression()
/* 15:   */   {
/* 16:16 */     return this.expression;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getContainingTableName()
/* 20:   */   {
/* 21:20 */     return this.tableName;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.FormulaValue
 * JD-Core Version:    0.7.0.1
 */