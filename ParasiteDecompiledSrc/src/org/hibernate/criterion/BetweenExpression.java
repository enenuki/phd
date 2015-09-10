/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.TypedValue;
/*  6:   */ import org.hibernate.internal.util.StringHelper;
/*  7:   */ 
/*  8:   */ public class BetweenExpression
/*  9:   */   implements Criterion
/* 10:   */ {
/* 11:   */   private final String propertyName;
/* 12:   */   private final Object lo;
/* 13:   */   private final Object hi;
/* 14:   */   
/* 15:   */   protected BetweenExpression(String propertyName, Object lo, Object hi)
/* 16:   */   {
/* 17:42 */     this.propertyName = propertyName;
/* 18:43 */     this.lo = lo;
/* 19:44 */     this.hi = hi;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 23:   */     throws HibernateException
/* 24:   */   {
/* 25:49 */     return StringHelper.join(" and ", StringHelper.suffix(criteriaQuery.findColumns(this.propertyName, criteria), " between ? and ?"));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 29:   */     throws HibernateException
/* 30:   */   {
/* 31:59 */     return new TypedValue[] { criteriaQuery.getTypedValue(criteria, this.propertyName, this.lo), criteriaQuery.getTypedValue(criteria, this.propertyName, this.hi) };
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toString()
/* 35:   */   {
/* 36:66 */     return this.propertyName + " between " + this.lo + " and " + this.hi;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.BetweenExpression
 * JD-Core Version:    0.7.0.1
 */