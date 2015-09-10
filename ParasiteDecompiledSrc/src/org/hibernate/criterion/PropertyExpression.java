/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.TypedValue;
/*  6:   */ import org.hibernate.internal.util.StringHelper;
/*  7:   */ 
/*  8:   */ public class PropertyExpression
/*  9:   */   implements Criterion
/* 10:   */ {
/* 11:   */   private final String propertyName;
/* 12:   */   private final String otherPropertyName;
/* 13:   */   private final String op;
/* 14:41 */   private static final TypedValue[] NO_TYPED_VALUES = new TypedValue[0];
/* 15:   */   
/* 16:   */   protected PropertyExpression(String propertyName, String otherPropertyName, String op)
/* 17:   */   {
/* 18:44 */     this.propertyName = propertyName;
/* 19:45 */     this.otherPropertyName = otherPropertyName;
/* 20:46 */     this.op = op;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 24:   */     throws HibernateException
/* 25:   */   {
/* 26:51 */     String[] xcols = criteriaQuery.findColumns(this.propertyName, criteria);
/* 27:52 */     String[] ycols = criteriaQuery.findColumns(this.otherPropertyName, criteria);
/* 28:53 */     String result = StringHelper.join(" and ", StringHelper.add(xcols, getOp(), ycols));
/* 29:57 */     if (xcols.length > 1) {
/* 30:57 */       result = '(' + result + ')';
/* 31:   */     }
/* 32:58 */     return result;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 36:   */     throws HibernateException
/* 37:   */   {
/* 38:64 */     return NO_TYPED_VALUES;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String toString()
/* 42:   */   {
/* 43:68 */     return this.propertyName + getOp() + this.otherPropertyName;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String getOp()
/* 47:   */   {
/* 48:72 */     return this.op;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.PropertyExpression
 * JD-Core Version:    0.7.0.1
 */