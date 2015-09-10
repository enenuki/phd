/*  1:   */ package org.hibernate.engine.jdbc.internal.proxy;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.ResultSet;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import java.sql.Statement;
/*  7:   */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/*  8:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  9:   */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/* 10:   */ 
/* 11:   */ public class ImplicitResultSetProxyHandler
/* 12:   */   extends AbstractResultSetProxyHandler
/* 13:   */ {
/* 14:   */   private ConnectionProxyHandler connectionProxyHandler;
/* 15:   */   private Connection connectionProxy;
/* 16:   */   private Statement sourceStatement;
/* 17:   */   
/* 18:   */   public ImplicitResultSetProxyHandler(ResultSet resultSet, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/* 19:   */   {
/* 20:44 */     super(resultSet);
/* 21:45 */     this.connectionProxyHandler = connectionProxyHandler;
/* 22:46 */     this.connectionProxy = connectionProxy;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ImplicitResultSetProxyHandler(ResultSet resultSet, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy, Statement sourceStatement)
/* 26:   */   {
/* 27:50 */     super(resultSet);
/* 28:51 */     this.connectionProxyHandler = connectionProxyHandler;
/* 29:52 */     this.connectionProxy = connectionProxy;
/* 30:53 */     this.sourceStatement = sourceStatement;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected JdbcServices getJdbcServices()
/* 34:   */   {
/* 35:58 */     return this.connectionProxyHandler.getJdbcServices();
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected JdbcResourceRegistry getResourceRegistry()
/* 39:   */   {
/* 40:63 */     return this.connectionProxyHandler.getResourceRegistry();
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected Statement getExposableStatement()
/* 44:   */   {
/* 45:68 */     if (this.sourceStatement == null) {
/* 46:   */       try
/* 47:   */       {
/* 48:70 */         Statement stmnt = getResultSet().getStatement();
/* 49:71 */         if (stmnt == null) {
/* 50:72 */           return null;
/* 51:   */         }
/* 52:74 */         this.sourceStatement = ProxyBuilder.buildImplicitStatement(stmnt, this.connectionProxyHandler, this.connectionProxy);
/* 53:   */       }
/* 54:   */       catch (SQLException e)
/* 55:   */       {
/* 56:77 */         throw getJdbcServices().getSqlExceptionHelper().convert(e, e.getMessage());
/* 57:   */       }
/* 58:   */     }
/* 59:80 */     return this.sourceStatement;
/* 60:   */   }
/* 61:   */   
/* 62:   */   protected void invalidateHandle()
/* 63:   */   {
/* 64:84 */     this.sourceStatement = null;
/* 65:85 */     super.invalidateHandle();
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.ImplicitResultSetProxyHandler
 * JD-Core Version:    0.7.0.1
 */