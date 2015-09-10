/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  6:   */ 
/*  7:   */ class SuppliedConnectionHelper
/*  8:   */   implements ConnectionHelper
/*  9:   */ {
/* 10:   */   private Connection connection;
/* 11:   */   private boolean toggleAutoCommit;
/* 12:   */   
/* 13:   */   public SuppliedConnectionHelper(Connection connection)
/* 14:   */   {
/* 15:43 */     this.connection = connection;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void prepare(boolean needsAutoCommit)
/* 19:   */     throws SQLException
/* 20:   */   {
/* 21:47 */     this.toggleAutoCommit = ((needsAutoCommit) && (!this.connection.getAutoCommit()));
/* 22:48 */     if (this.toggleAutoCommit)
/* 23:   */     {
/* 24:   */       try
/* 25:   */       {
/* 26:50 */         this.connection.commit();
/* 27:   */       }
/* 28:   */       catch (Throwable ignore) {}
/* 29:55 */       this.connection.setAutoCommit(true);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Connection getConnection()
/* 34:   */   {
/* 35:60 */     return this.connection;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void release()
/* 39:   */     throws SQLException
/* 40:   */   {
/* 41:64 */     new SqlExceptionHelper().logAndClearWarnings(this.connection);
/* 42:65 */     if (this.toggleAutoCommit) {
/* 43:66 */       this.connection.setAutoCommit(false);
/* 44:   */     }
/* 45:68 */     this.connection = null;
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SuppliedConnectionHelper
 * JD-Core Version:    0.7.0.1
 */