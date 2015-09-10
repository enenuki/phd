/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class QueryException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   private String queryString;
/*  7:   */   
/*  8:   */   public QueryException(String message)
/*  9:   */   {
/* 10:37 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public QueryException(String message, Throwable e)
/* 14:   */   {
/* 15:40 */     super(message, e);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public QueryException(String message, String queryString)
/* 19:   */   {
/* 20:44 */     super(message);
/* 21:45 */     this.queryString = queryString;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public QueryException(Exception e)
/* 25:   */   {
/* 26:49 */     super(e);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getQueryString()
/* 30:   */   {
/* 31:52 */     return this.queryString;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setQueryString(String queryString)
/* 35:   */   {
/* 36:56 */     this.queryString = queryString;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getMessage()
/* 40:   */   {
/* 41:60 */     String msg = super.getMessage();
/* 42:61 */     if (this.queryString != null) {
/* 43:61 */       msg = msg + " [" + this.queryString + ']';
/* 44:   */     }
/* 45:62 */     return msg;
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.QueryException
 * JD-Core Version:    0.7.0.1
 */