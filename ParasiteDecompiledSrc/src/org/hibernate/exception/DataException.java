/*  1:   */ package org.hibernate.exception;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ import org.hibernate.JDBCException;
/*  5:   */ 
/*  6:   */ public class DataException
/*  7:   */   extends JDBCException
/*  8:   */ {
/*  9:   */   public DataException(String message, SQLException root)
/* 10:   */   {
/* 11:44 */     super(message, root);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public DataException(String message, SQLException root, String sql)
/* 15:   */   {
/* 16:54 */     super(message, root, sql);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.DataException
 * JD-Core Version:    0.7.0.1
 */