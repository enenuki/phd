/*  1:   */ package org.hibernate.engine.jdbc.internal.proxy;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import java.sql.Connection;
/*  5:   */ import java.sql.Statement;
/*  6:   */ import java.util.Arrays;
/*  7:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  8:   */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.jboss.logging.Logger;
/* 11:   */ 
/* 12:   */ public class PreparedStatementProxyHandler
/* 13:   */   extends AbstractStatementProxyHandler
/* 14:   */ {
/* 15:41 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PreparedStatementProxyHandler.class.getName());
/* 16:   */   private final String sql;
/* 17:   */   
/* 18:   */   protected PreparedStatementProxyHandler(String sql, Statement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/* 19:   */   {
/* 20:50 */     super(statement, connectionProxyHandler, connectionProxy);
/* 21:51 */     connectionProxyHandler.getJdbcServices().getSqlStatementLogger().logStatement(sql);
/* 22:52 */     this.sql = sql;
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected void beginningInvocationHandling(Method method, Object[] args)
/* 26:   */   {
/* 27:57 */     if (isExecution(method)) {
/* 28:58 */       logExecution();
/* 29:   */     } else {
/* 30:61 */       journalPossibleParameterBind(method, args);
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   private void journalPossibleParameterBind(Method method, Object[] args)
/* 35:   */   {
/* 36:66 */     String methodName = method.getName();
/* 37:68 */     if ((methodName.startsWith("set")) && (args != null) && (args.length >= 2)) {
/* 38:69 */       journalParameterBind(method, args);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   private void journalParameterBind(Method method, Object[] args)
/* 43:   */   {
/* 44:74 */     if (LOG.isTraceEnabled()) {
/* 45:75 */       LOG.tracev("Binding via {0}: {1}", method.getName(), Arrays.asList(args));
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   private boolean isExecution(Method method)
/* 50:   */   {
/* 51:80 */     return false;
/* 52:   */   }
/* 53:   */   
/* 54:   */   private void logExecution() {}
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.PreparedStatementProxyHandler
 * JD-Core Version:    0.7.0.1
 */