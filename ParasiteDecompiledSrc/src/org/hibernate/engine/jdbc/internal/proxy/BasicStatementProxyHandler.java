/*  1:   */ package org.hibernate.engine.jdbc.internal.proxy;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import java.sql.Connection;
/*  5:   */ import java.sql.Statement;
/*  6:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  7:   */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  8:   */ 
/*  9:   */ public class BasicStatementProxyHandler
/* 10:   */   extends AbstractStatementProxyHandler
/* 11:   */ {
/* 12:   */   public BasicStatementProxyHandler(Statement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/* 13:   */   {
/* 14:39 */     super(statement, connectionProxyHandler, connectionProxy);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected void beginningInvocationHandling(Method method, Object[] args)
/* 18:   */   {
/* 19:43 */     if (isExecution(method)) {
/* 20:44 */       getJdbcServices().getSqlStatementLogger().logStatement((String)args[0]);
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   private boolean isExecution(Method method)
/* 25:   */   {
/* 26:49 */     String methodName = method.getName();
/* 27:50 */     return ("execute".equals(methodName)) || ("executeQuery".equals(methodName)) || ("executeUpdate".equals(methodName));
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.BasicStatementProxyHandler
 * JD-Core Version:    0.7.0.1
 */