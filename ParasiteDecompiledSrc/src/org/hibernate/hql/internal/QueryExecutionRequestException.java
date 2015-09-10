/*  1:   */ package org.hibernate.hql.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ 
/*  5:   */ public class QueryExecutionRequestException
/*  6:   */   extends QueryException
/*  7:   */ {
/*  8:   */   public QueryExecutionRequestException(String message, String queryString)
/*  9:   */   {
/* 10:36 */     super(message, queryString);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.QueryExecutionRequestException
 * JD-Core Version:    0.7.0.1
 */