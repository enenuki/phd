/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.TypedValue;
/*  6:   */ import org.hibernate.internal.util.StringHelper;
/*  7:   */ 
/*  8:   */ public class NullExpression
/*  9:   */   implements Criterion
/* 10:   */ {
/* 11:   */   private final String propertyName;
/* 12:39 */   private static final TypedValue[] NO_VALUES = new TypedValue[0];
/* 13:   */   
/* 14:   */   protected NullExpression(String propertyName)
/* 15:   */   {
/* 16:42 */     this.propertyName = propertyName;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 20:   */     throws HibernateException
/* 21:   */   {
/* 22:47 */     String[] columns = criteriaQuery.findColumns(this.propertyName, criteria);
/* 23:48 */     String result = StringHelper.join(" and ", StringHelper.suffix(columns, " is null"));
/* 24:52 */     if (columns.length > 1) {
/* 25:52 */       result = '(' + result + ')';
/* 26:   */     }
/* 27:53 */     return result;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 31:   */     throws HibernateException
/* 32:   */   {
/* 33:60 */     return NO_VALUES;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String toString()
/* 37:   */   {
/* 38:64 */     return this.propertyName + " is null";
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.NullExpression
 * JD-Core Version:    0.7.0.1
 */