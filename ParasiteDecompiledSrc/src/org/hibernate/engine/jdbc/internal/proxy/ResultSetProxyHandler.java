/*  1:   */ package org.hibernate.engine.jdbc.internal.proxy;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import java.sql.Statement;
/*  5:   */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/*  6:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  7:   */ 
/*  8:   */ public class ResultSetProxyHandler
/*  9:   */   extends AbstractResultSetProxyHandler
/* 10:   */ {
/* 11:   */   private AbstractStatementProxyHandler statementProxyHandler;
/* 12:   */   private Statement statementProxy;
/* 13:   */   
/* 14:   */   public ResultSetProxyHandler(ResultSet resultSet, AbstractStatementProxyHandler statementProxyHandler, Statement statementProxy)
/* 15:   */   {
/* 16:44 */     super(resultSet);
/* 17:45 */     this.statementProxyHandler = statementProxyHandler;
/* 18:46 */     this.statementProxy = statementProxy;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected AbstractStatementProxyHandler getStatementProxy()
/* 22:   */   {
/* 23:50 */     return this.statementProxyHandler;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected Statement getExposableStatement()
/* 27:   */   {
/* 28:54 */     return this.statementProxy;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected JdbcServices getJdbcServices()
/* 32:   */   {
/* 33:58 */     return getStatementProxy().getJdbcServices();
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected JdbcResourceRegistry getResourceRegistry()
/* 37:   */   {
/* 38:62 */     return getStatementProxy().getResourceRegistry();
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected void invalidateHandle()
/* 42:   */   {
/* 43:66 */     this.statementProxyHandler = null;
/* 44:67 */     super.invalidateHandle();
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.ResultSetProxyHandler
 * JD-Core Version:    0.7.0.1
 */