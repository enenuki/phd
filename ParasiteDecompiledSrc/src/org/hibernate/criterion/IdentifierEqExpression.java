/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.TypedValue;
/*  6:   */ import org.hibernate.internal.util.StringHelper;
/*  7:   */ 
/*  8:   */ public class IdentifierEqExpression
/*  9:   */   implements Criterion
/* 10:   */ {
/* 11:   */   private final Object value;
/* 12:   */   
/* 13:   */   protected IdentifierEqExpression(Object value)
/* 14:   */   {
/* 15:40 */     this.value = value;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 19:   */     throws HibernateException
/* 20:   */   {
/* 21:46 */     String[] columns = criteriaQuery.getIdentifierColumns(criteria);
/* 22:   */     
/* 23:48 */     String result = StringHelper.join(" and ", StringHelper.suffix(columns, " = ?"));
/* 24:52 */     if (columns.length > 1) {
/* 25:52 */       result = '(' + result + ')';
/* 26:   */     }
/* 27:53 */     return result;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 31:   */     throws HibernateException
/* 32:   */   {
/* 33:60 */     return new TypedValue[] { criteriaQuery.getTypedIdentifierValue(criteria, this.value) };
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String toString()
/* 37:   */   {
/* 38:64 */     return "id = " + this.value;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.IdentifierEqExpression
 * JD-Core Version:    0.7.0.1
 */