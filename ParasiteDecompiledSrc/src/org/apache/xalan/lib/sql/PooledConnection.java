/*  1:   */ package org.apache.xalan.lib.sql;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.sql.Connection;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ 
/*  7:   */ public class PooledConnection
/*  8:   */ {
/*  9:34 */   private Connection connection = null;
/* 10:38 */   private boolean inuse = false;
/* 11:   */   
/* 12:   */   public PooledConnection(Connection value)
/* 13:   */   {
/* 14:47 */     if (value != null) {
/* 15:47 */       this.connection = value;
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Connection getConnection()
/* 20:   */   {
/* 21:57 */     return this.connection;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setInUse(boolean value)
/* 25:   */   {
/* 26:68 */     this.inuse = value;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean inUse()
/* 30:   */   {
/* 31:75 */     return this.inuse;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void close()
/* 35:   */   {
/* 36:   */     try
/* 37:   */     {
/* 38:85 */       this.connection.close();
/* 39:   */     }
/* 40:   */     catch (SQLException sqle)
/* 41:   */     {
/* 42:89 */       System.err.println(sqle.getMessage());
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.PooledConnection
 * JD-Core Version:    0.7.0.1
 */