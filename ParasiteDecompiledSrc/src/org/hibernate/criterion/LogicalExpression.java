/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.TypedValue;
/*  6:   */ 
/*  7:   */ public class LogicalExpression
/*  8:   */   implements Criterion
/*  9:   */ {
/* 10:   */   private final Criterion lhs;
/* 11:   */   private final Criterion rhs;
/* 12:   */   private final String op;
/* 13:   */   
/* 14:   */   protected LogicalExpression(Criterion lhs, Criterion rhs, String op)
/* 15:   */   {
/* 16:41 */     this.lhs = lhs;
/* 17:42 */     this.rhs = rhs;
/* 18:43 */     this.op = op;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 22:   */     throws HibernateException
/* 23:   */   {
/* 24:49 */     TypedValue[] lhstv = this.lhs.getTypedValues(criteria, criteriaQuery);
/* 25:50 */     TypedValue[] rhstv = this.rhs.getTypedValues(criteria, criteriaQuery);
/* 26:51 */     TypedValue[] result = new TypedValue[lhstv.length + rhstv.length];
/* 27:52 */     System.arraycopy(lhstv, 0, result, 0, lhstv.length);
/* 28:53 */     System.arraycopy(rhstv, 0, result, lhstv.length, rhstv.length);
/* 29:54 */     return result;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 33:   */     throws HibernateException
/* 34:   */   {
/* 35:60 */     return '(' + this.lhs.toSqlString(criteria, criteriaQuery) + ' ' + getOp() + ' ' + this.rhs.toSqlString(criteria, criteriaQuery) + ')';
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getOp()
/* 39:   */   {
/* 40:70 */     return this.op;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String toString()
/* 44:   */   {
/* 45:74 */     return this.lhs.toString() + ' ' + getOp() + ' ' + this.rhs.toString();
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.LogicalExpression
 * JD-Core Version:    0.7.0.1
 */