/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ 
/*  5:   */ public class PessimisticLockException
/*  6:   */   extends JDBCException
/*  7:   */ {
/*  8:   */   public PessimisticLockException(String s, SQLException se, String sql)
/*  9:   */   {
/* 10:35 */     super(s, se, sql);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.PessimisticLockException
 * JD-Core Version:    0.7.0.1
 */