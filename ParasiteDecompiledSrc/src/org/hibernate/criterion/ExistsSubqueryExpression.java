/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ 
/*  5:   */ public class ExistsSubqueryExpression
/*  6:   */   extends SubqueryExpression
/*  7:   */ {
/*  8:   */   protected String toLeftSqlString(Criteria criteria, CriteriaQuery outerQuery)
/*  9:   */   {
/* 10:34 */     return "";
/* 11:   */   }
/* 12:   */   
/* 13:   */   protected ExistsSubqueryExpression(String quantifier, DetachedCriteria dc)
/* 14:   */   {
/* 15:38 */     super(null, quantifier, dc);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.ExistsSubqueryExpression
 * JD-Core Version:    0.7.0.1
 */