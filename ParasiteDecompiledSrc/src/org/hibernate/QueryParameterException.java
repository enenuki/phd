/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class QueryParameterException
/*  4:   */   extends QueryException
/*  5:   */ {
/*  6:   */   public QueryParameterException(Exception e)
/*  7:   */   {
/*  8:36 */     super(e);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public QueryParameterException(String message)
/* 12:   */   {
/* 13:40 */     super(message);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public QueryParameterException(String message, Throwable e)
/* 17:   */   {
/* 18:44 */     super(message, e);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public QueryParameterException(String message, String queryString)
/* 22:   */   {
/* 23:48 */     super(message, queryString);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.QueryParameterException
 * JD-Core Version:    0.7.0.1
 */