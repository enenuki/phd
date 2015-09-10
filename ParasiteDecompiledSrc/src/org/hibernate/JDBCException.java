/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ 
/*  5:   */ public class JDBCException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   private SQLException sqle;
/*  9:   */   private String sql;
/* 10:   */   
/* 11:   */   public JDBCException(String string, SQLException root)
/* 12:   */   {
/* 13:42 */     super(string, root);
/* 14:43 */     this.sqle = root;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public JDBCException(String string, SQLException root, String sql)
/* 18:   */   {
/* 19:47 */     this(string, root);
/* 20:48 */     this.sql = sql;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getSQLState()
/* 24:   */   {
/* 25:57 */     return this.sqle.getSQLState();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getErrorCode()
/* 29:   */   {
/* 30:66 */     return this.sqle.getErrorCode();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public SQLException getSQLException()
/* 34:   */   {
/* 35:74 */     return this.sqle;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getSQL()
/* 39:   */   {
/* 40:82 */     return this.sql;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.JDBCException
 * JD-Core Version:    0.7.0.1
 */