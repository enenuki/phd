/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import java.sql.SQLWarning;
/*  6:   */ import java.sql.Statement;
/*  7:   */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  8:   */ import org.hibernate.internal.CoreMessageLogger;
/*  9:   */ import org.jboss.logging.Logger;
/* 10:   */ 
/* 11:   */ class DatabaseExporter
/* 12:   */   implements Exporter
/* 13:   */ {
/* 14:40 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DatabaseExporter.class.getName());
/* 15:   */   private final ConnectionHelper connectionHelper;
/* 16:   */   private final SqlExceptionHelper sqlExceptionHelper;
/* 17:   */   private final Connection connection;
/* 18:   */   private final Statement statement;
/* 19:   */   
/* 20:   */   public DatabaseExporter(ConnectionHelper connectionHelper, SqlExceptionHelper sqlExceptionHelper)
/* 21:   */     throws SQLException
/* 22:   */   {
/* 23:49 */     this.connectionHelper = connectionHelper;
/* 24:50 */     this.sqlExceptionHelper = sqlExceptionHelper;
/* 25:   */     
/* 26:52 */     connectionHelper.prepare(true);
/* 27:53 */     this.connection = connectionHelper.getConnection();
/* 28:54 */     this.statement = this.connection.createStatement();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean acceptsImportScripts()
/* 32:   */   {
/* 33:59 */     return true;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void export(String string)
/* 37:   */     throws Exception
/* 38:   */   {
/* 39:64 */     this.statement.executeUpdate(string);
/* 40:   */     try
/* 41:   */     {
/* 42:66 */       SQLWarning warnings = this.statement.getWarnings();
/* 43:67 */       if (warnings != null) {
/* 44:68 */         this.sqlExceptionHelper.logAndClearWarnings(this.connection);
/* 45:   */       }
/* 46:   */     }
/* 47:   */     catch (SQLException e)
/* 48:   */     {
/* 49:72 */       LOG.unableToLogSqlWarnings(e);
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void release()
/* 54:   */     throws Exception
/* 55:   */   {
/* 56:   */     try
/* 57:   */     {
/* 58:79 */       this.statement.close();
/* 59:   */     }
/* 60:   */     finally
/* 61:   */     {
/* 62:82 */       this.connectionHelper.release();
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.DatabaseExporter
 * JD-Core Version:    0.7.0.1
 */