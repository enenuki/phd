/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.TypedValue;
/*  6:   */ 
/*  7:   */ public class NaturalIdentifier
/*  8:   */   implements Criterion
/*  9:   */ {
/* 10:35 */   private Junction conjunction = new Conjunction();
/* 11:   */   
/* 12:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 13:   */     throws HibernateException
/* 14:   */   {
/* 15:38 */     return this.conjunction.getTypedValues(criteria, criteriaQuery);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 19:   */     throws HibernateException
/* 20:   */   {
/* 21:42 */     return this.conjunction.toSqlString(criteria, criteriaQuery);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public NaturalIdentifier set(String property, Object value)
/* 25:   */   {
/* 26:46 */     this.conjunction.add(Restrictions.eq(property, value));
/* 27:47 */     return this;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.NaturalIdentifier
 * JD-Core Version:    0.7.0.1
 */