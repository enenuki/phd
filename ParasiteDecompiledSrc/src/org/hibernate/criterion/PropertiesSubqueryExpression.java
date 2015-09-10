/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.internal.util.StringHelper;
/*  5:   */ 
/*  6:   */ public class PropertiesSubqueryExpression
/*  7:   */   extends SubqueryExpression
/*  8:   */ {
/*  9:   */   private final String[] propertyNames;
/* 10:   */   
/* 11:   */   protected PropertiesSubqueryExpression(String[] propertyNames, String op, DetachedCriteria dc)
/* 12:   */   {
/* 13:14 */     super(op, null, dc);
/* 14:15 */     this.propertyNames = propertyNames;
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected String toLeftSqlString(Criteria criteria, CriteriaQuery outerQuery)
/* 18:   */   {
/* 19:20 */     StringBuilder left = new StringBuilder("(");
/* 20:21 */     String[] sqlColumnNames = new String[this.propertyNames.length];
/* 21:22 */     for (int i = 0; i < sqlColumnNames.length; i++) {
/* 22:23 */       sqlColumnNames[i] = outerQuery.getColumn(criteria, this.propertyNames[i]);
/* 23:   */     }
/* 24:25 */     left.append(StringHelper.join(", ", sqlColumnNames));
/* 25:26 */     return ")";
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.PropertiesSubqueryExpression
 * JD-Core Version:    0.7.0.1
 */