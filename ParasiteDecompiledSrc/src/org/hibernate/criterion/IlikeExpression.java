/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.dialect.PostgreSQLDialect;
/*  7:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  8:   */ import org.hibernate.engine.spi.TypedValue;
/*  9:   */ 
/* 10:   */ @Deprecated
/* 11:   */ public class IlikeExpression
/* 12:   */   implements Criterion
/* 13:   */ {
/* 14:   */   private final String propertyName;
/* 15:   */   private final Object value;
/* 16:   */   
/* 17:   */   protected IlikeExpression(String propertyName, Object value)
/* 18:   */   {
/* 19:45 */     this.propertyName = propertyName;
/* 20:46 */     this.value = value;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected IlikeExpression(String propertyName, String value, MatchMode matchMode)
/* 24:   */   {
/* 25:50 */     this(propertyName, matchMode.toMatchString(value));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 29:   */     throws HibernateException
/* 30:   */   {
/* 31:55 */     Dialect dialect = criteriaQuery.getFactory().getDialect();
/* 32:56 */     String[] columns = criteriaQuery.findColumns(this.propertyName, criteria);
/* 33:57 */     if (columns.length != 1) {
/* 34:58 */       throw new HibernateException("ilike may only be used with single-column properties");
/* 35:   */     }
/* 36:60 */     if ((dialect instanceof PostgreSQLDialect)) {
/* 37:61 */       return columns[0] + " ilike ?";
/* 38:   */     }
/* 39:64 */     return dialect.getLowercaseFunction() + '(' + columns[0] + ") like ?";
/* 40:   */   }
/* 41:   */   
/* 42:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 43:   */     throws HibernateException
/* 44:   */   {
/* 45:72 */     return new TypedValue[] { criteriaQuery.getTypedValue(criteria, this.propertyName, this.value.toString().toLowerCase()) };
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String toString()
/* 49:   */   {
/* 50:82 */     return this.propertyName + " ilike " + this.value;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.IlikeExpression
 * JD-Core Version:    0.7.0.1
 */