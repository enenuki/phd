/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ 
/*  5:   */ public class QueryTimeoutException
/*  6:   */   extends JDBCException
/*  7:   */ {
/*  8:   */   public QueryTimeoutException(String s, JDBCException je, String sql)
/*  9:   */   {
/* 10:38 */     super(s, je.getSQLException(), sql);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public QueryTimeoutException(String s, SQLException se, String sql)
/* 14:   */   {
/* 15:42 */     super(s, se, sql);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.QueryTimeoutException
 * JD-Core Version:    0.7.0.1
 */