/*  1:   */ package org.hibernate.jdbc;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ 
/*  6:   */ public abstract class AbstractWork
/*  7:   */   implements Work, WorkExecutorVisitable<Void>
/*  8:   */ {
/*  9:   */   public Void accept(WorkExecutor<Void> executor, Connection connection)
/* 10:   */     throws SQLException
/* 11:   */   {
/* 12:55 */     return (Void)executor.executeWork(this, connection);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.AbstractWork
 * JD-Core Version:    0.7.0.1
 */