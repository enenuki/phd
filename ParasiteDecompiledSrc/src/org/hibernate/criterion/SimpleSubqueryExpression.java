/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.EntityMode;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.engine.spi.TypedValue;
/*  7:   */ 
/*  8:   */ public class SimpleSubqueryExpression
/*  9:   */   extends SubqueryExpression
/* 10:   */ {
/* 11:   */   private Object value;
/* 12:   */   
/* 13:   */   protected SimpleSubqueryExpression(Object value, String op, String quantifier, DetachedCriteria dc)
/* 14:   */   {
/* 15:40 */     super(op, quantifier, dc);
/* 16:41 */     this.value = value;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 20:   */     throws HibernateException
/* 21:   */   {
/* 22:47 */     TypedValue[] superTv = super.getTypedValues(criteria, criteriaQuery);
/* 23:48 */     TypedValue[] result = new TypedValue[superTv.length + 1];
/* 24:49 */     System.arraycopy(superTv, 0, result, 1, superTv.length);
/* 25:50 */     result[0] = new TypedValue(getTypes()[0], this.value, EntityMode.POJO);
/* 26:51 */     return result;
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected String toLeftSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 30:   */   {
/* 31:55 */     return "?";
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.SimpleSubqueryExpression
 * JD-Core Version:    0.7.0.1
 */