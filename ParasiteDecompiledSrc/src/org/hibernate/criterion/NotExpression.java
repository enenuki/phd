/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.dialect.MySQLDialect;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.engine.spi.TypedValue;
/*  8:   */ 
/*  9:   */ public class NotExpression
/* 10:   */   implements Criterion
/* 11:   */ {
/* 12:   */   private Criterion criterion;
/* 13:   */   
/* 14:   */   protected NotExpression(Criterion criterion)
/* 15:   */   {
/* 16:40 */     this.criterion = criterion;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 20:   */     throws HibernateException
/* 21:   */   {
/* 22:45 */     if ((criteriaQuery.getFactory().getDialect() instanceof MySQLDialect)) {
/* 23:46 */       return "not (" + this.criterion.toSqlString(criteria, criteriaQuery) + ')';
/* 24:   */     }
/* 25:49 */     return "not " + this.criterion.toSqlString(criteria, criteriaQuery);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 29:   */     throws HibernateException
/* 30:   */   {
/* 31:56 */     return this.criterion.getTypedValues(criteria, criteriaQuery);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toString()
/* 35:   */   {
/* 36:60 */     return "not " + this.criterion.toString();
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.NotExpression
 * JD-Core Version:    0.7.0.1
 */