/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.Criteria;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.dialect.function.SQLFunction;
/*  8:   */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*  9:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/* 10:   */ import org.hibernate.type.Type;
/* 11:   */ 
/* 12:   */ public class RowCountProjection
/* 13:   */   extends SimpleProjection
/* 14:   */ {
/* 15:38 */   private static List ARGS = Collections.singletonList("*");
/* 16:   */   
/* 17:   */   public String toString()
/* 18:   */   {
/* 19:41 */     return "count(*)";
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
/* 23:   */     throws HibernateException
/* 24:   */   {
/* 25:45 */     return new Type[] { getFunction(criteriaQuery).getReturnType(null, criteriaQuery.getFactory()) };
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery)
/* 29:   */     throws HibernateException
/* 30:   */   {
/* 31:51 */     return getFunction(criteriaQuery).render(null, ARGS, criteriaQuery.getFactory()) + " as y" + position + '_';
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected SQLFunction getFunction(CriteriaQuery criteriaQuery)
/* 35:   */   {
/* 36:56 */     SQLFunction function = criteriaQuery.getFactory().getSqlFunctionRegistry().findSQLFunction("count");
/* 37:59 */     if (function == null) {
/* 38:60 */       throw new HibernateException("Unable to locate count function mapping");
/* 39:   */     }
/* 40:62 */     return function;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.RowCountProjection
 * JD-Core Version:    0.7.0.1
 */