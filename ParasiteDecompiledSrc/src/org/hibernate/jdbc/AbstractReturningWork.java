/*  1:   */ package org.hibernate.jdbc;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ 
/*  6:   */ public abstract class AbstractReturningWork<T>
/*  7:   */   implements ReturningWork<T>, WorkExecutorVisitable<T>
/*  8:   */ {
/*  9:   */   public T accept(WorkExecutor<T> executor, Connection connection)
/* 10:   */     throws SQLException
/* 11:   */   {
/* 12:51 */     return executor.executeReturningWork(this, connection);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.AbstractReturningWork
 * JD-Core Version:    0.7.0.1
 */