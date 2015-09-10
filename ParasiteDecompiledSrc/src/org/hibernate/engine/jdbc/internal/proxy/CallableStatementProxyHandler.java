/*  1:   */ package org.hibernate.engine.jdbc.internal.proxy;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import java.sql.CallableStatement;
/*  5:   */ import java.sql.Connection;
/*  6:   */ import java.sql.SQLException;
/*  7:   */ import java.sql.Statement;
/*  8:   */ import org.hibernate.dialect.Dialect;
/*  9:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/* 10:   */ 
/* 11:   */ public class CallableStatementProxyHandler
/* 12:   */   extends PreparedStatementProxyHandler
/* 13:   */ {
/* 14:   */   protected CallableStatementProxyHandler(String sql, Statement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/* 15:   */   {
/* 16:43 */     super(sql, statement, connectionProxyHandler, connectionProxy);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected Object continueInvocation(Object proxy, Method method, Object[] args)
/* 20:   */     throws Throwable
/* 21:   */   {
/* 22:48 */     if (!"executeQuery".equals(method.getName())) {
/* 23:49 */       return super.continueInvocation(proxy, method, args);
/* 24:   */     }
/* 25:51 */     errorIfInvalid();
/* 26:52 */     return executeQuery();
/* 27:   */   }
/* 28:   */   
/* 29:   */   private Object executeQuery()
/* 30:   */     throws SQLException
/* 31:   */   {
/* 32:56 */     return getConnectionProxy().getJdbcServices().getDialect().getResultSet((CallableStatement)getStatementWithoutChecks());
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.CallableStatementProxyHandler
 * JD-Core Version:    0.7.0.1
 */