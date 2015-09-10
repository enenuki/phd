/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ 
/*  5:   */ public class PropertySubqueryExpression
/*  6:   */   extends SubqueryExpression
/*  7:   */ {
/*  8:   */   private String propertyName;
/*  9:   */   
/* 10:   */   protected PropertySubqueryExpression(String propertyName, String op, String quantifier, DetachedCriteria dc)
/* 11:   */   {
/* 12:37 */     super(op, quantifier, dc);
/* 13:38 */     this.propertyName = propertyName;
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected String toLeftSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 17:   */   {
/* 18:42 */     return criteriaQuery.getColumn(criteria, this.propertyName);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.PropertySubqueryExpression
 * JD-Core Version:    0.7.0.1
 */