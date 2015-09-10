/*  1:   */ package org.hibernate.exception;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ import org.hibernate.JDBCException;
/*  5:   */ 
/*  6:   */ public class LockAcquisitionException
/*  7:   */   extends JDBCException
/*  8:   */ {
/*  9:   */   public LockAcquisitionException(String string, SQLException root)
/* 10:   */   {
/* 11:38 */     super(string, root);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public LockAcquisitionException(String string, SQLException root, String sql)
/* 15:   */   {
/* 16:42 */     super(string, root, sql);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.LockAcquisitionException
 * JD-Core Version:    0.7.0.1
 */