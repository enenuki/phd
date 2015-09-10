/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.EntityMode;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.engine.spi.TypedValue;
/*  7:   */ import org.hibernate.internal.util.StringHelper;
/*  8:   */ import org.hibernate.type.Type;
/*  9:   */ 
/* 10:   */ public class SQLCriterion
/* 11:   */   implements Criterion
/* 12:   */ {
/* 13:   */   private final String sql;
/* 14:   */   private final TypedValue[] typedValues;
/* 15:   */   
/* 16:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 17:   */     throws HibernateException
/* 18:   */   {
/* 19:46 */     return StringHelper.replace(this.sql, "{alias}", criteriaQuery.getSQLAlias(criteria));
/* 20:   */   }
/* 21:   */   
/* 22:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 23:   */     throws HibernateException
/* 24:   */   {
/* 25:51 */     return this.typedValues;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String toString()
/* 29:   */   {
/* 30:55 */     return this.sql;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected SQLCriterion(String sql, Object[] values, Type[] types)
/* 34:   */   {
/* 35:59 */     this.sql = sql;
/* 36:60 */     this.typedValues = new TypedValue[values.length];
/* 37:61 */     for (int i = 0; i < this.typedValues.length; i++) {
/* 38:62 */       this.typedValues[i] = new TypedValue(types[i], values[i], EntityMode.POJO);
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.SQLCriterion
 * JD-Core Version:    0.7.0.1
 */