/*  1:   */ package org.hibernate.jdbc;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ 
/*  6:   */ public class WorkExecutor<T>
/*  7:   */ {
/*  8:   */   public <T> T executeWork(Work work, Connection connection)
/*  9:   */     throws SQLException
/* 10:   */   {
/* 11:54 */     work.execute(connection);
/* 12:55 */     return null;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public <T> T executeReturningWork(ReturningWork<T> work, Connection connection)
/* 16:   */     throws SQLException
/* 17:   */   {
/* 18:72 */     return work.execute(connection);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.WorkExecutor
 * JD-Core Version:    0.7.0.1
 */