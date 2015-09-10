/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  6:   */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  7:   */ 
/*  8:   */ class SuppliedConnectionProviderConnectionHelper
/*  9:   */   implements ConnectionHelper
/* 10:   */ {
/* 11:   */   private ConnectionProvider provider;
/* 12:   */   private Connection connection;
/* 13:   */   private boolean toggleAutoCommit;
/* 14:   */   
/* 15:   */   public SuppliedConnectionProviderConnectionHelper(ConnectionProvider provider)
/* 16:   */   {
/* 17:47 */     this.provider = provider;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void prepare(boolean needsAutoCommit)
/* 21:   */     throws SQLException
/* 22:   */   {
/* 23:51 */     this.connection = this.provider.getConnection();
/* 24:52 */     this.toggleAutoCommit = ((needsAutoCommit) && (!this.connection.getAutoCommit()));
/* 25:53 */     if (this.toggleAutoCommit)
/* 26:   */     {
/* 27:   */       try
/* 28:   */       {
/* 29:55 */         this.connection.commit();
/* 30:   */       }
/* 31:   */       catch (Throwable ignore) {}
/* 32:60 */       this.connection.setAutoCommit(true);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Connection getConnection()
/* 37:   */     throws SQLException
/* 38:   */   {
/* 39:65 */     return this.connection;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void release()
/* 43:   */     throws SQLException
/* 44:   */   {
/* 45:70 */     if (this.connection != null)
/* 46:   */     {
/* 47:71 */       new SqlExceptionHelper().logAndClearWarnings(this.connection);
/* 48:72 */       if (this.toggleAutoCommit) {
/* 49:73 */         this.connection.setAutoCommit(false);
/* 50:   */       }
/* 51:75 */       this.provider.closeConnection(this.connection);
/* 52:76 */       this.connection = null;
/* 53:   */     }
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SuppliedConnectionProviderConnectionHelper
 * JD-Core Version:    0.7.0.1
 */