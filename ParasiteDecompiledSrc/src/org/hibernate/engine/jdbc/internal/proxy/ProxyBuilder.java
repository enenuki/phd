/*   1:    */ package org.hibernate.engine.jdbc.internal.proxy;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Proxy;
/*   4:    */ import java.sql.CallableStatement;
/*   5:    */ import java.sql.Connection;
/*   6:    */ import java.sql.DatabaseMetaData;
/*   7:    */ import java.sql.PreparedStatement;
/*   8:    */ import java.sql.ResultSet;
/*   9:    */ import java.sql.Statement;
/*  10:    */ import org.hibernate.engine.jdbc.spi.InvalidatableWrapper;
/*  11:    */ import org.hibernate.engine.jdbc.spi.JdbcWrapper;
/*  12:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  13:    */ 
/*  14:    */ public class ProxyBuilder
/*  15:    */ {
/*  16: 46 */   public static final Class[] CONNECTION_PROXY_INTERFACES = { Connection.class, JdbcWrapper.class };
/*  17:    */   
/*  18:    */   public static Connection buildConnection(LogicalConnectionImplementor logicalConnection)
/*  19:    */   {
/*  20: 52 */     ConnectionProxyHandler proxyHandler = new ConnectionProxyHandler(logicalConnection);
/*  21: 53 */     return (Connection)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), CONNECTION_PROXY_INTERFACES, proxyHandler);
/*  22:    */   }
/*  23:    */   
/*  24: 63 */   public static final Class[] STMNT_PROXY_INTERFACES = { Statement.class, JdbcWrapper.class, InvalidatableWrapper.class };
/*  25:    */   
/*  26:    */   public static Statement buildStatement(Statement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/*  27:    */   {
/*  28: 73 */     BasicStatementProxyHandler proxyHandler = new BasicStatementProxyHandler(statement, connectionProxyHandler, connectionProxy);
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33: 78 */     return (Statement)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), STMNT_PROXY_INTERFACES, proxyHandler);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Statement buildImplicitStatement(Statement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/*  37:    */   {
/*  38: 89 */     if (statement == null) {
/*  39: 90 */       return null;
/*  40:    */     }
/*  41: 92 */     ImplicitStatementProxyHandler handler = new ImplicitStatementProxyHandler(statement, connectionProxyHandler, connectionProxy);
/*  42: 93 */     return (Statement)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), STMNT_PROXY_INTERFACES, handler);
/*  43:    */   }
/*  44:    */   
/*  45:103 */   public static final Class[] PREPARED_STMNT_PROXY_INTERFACES = { PreparedStatement.class, JdbcWrapper.class, InvalidatableWrapper.class };
/*  46:    */   
/*  47:    */   public static PreparedStatement buildPreparedStatement(String sql, Statement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/*  48:    */   {
/*  49:114 */     PreparedStatementProxyHandler proxyHandler = new PreparedStatementProxyHandler(sql, statement, connectionProxyHandler, connectionProxy);
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:120 */     return (PreparedStatement)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), PREPARED_STMNT_PROXY_INTERFACES, proxyHandler);
/*  56:    */   }
/*  57:    */   
/*  58:130 */   public static final Class[] CALLABLE_STMNT_PROXY_INTERFACES = { CallableStatement.class, JdbcWrapper.class, InvalidatableWrapper.class };
/*  59:    */   
/*  60:    */   public static CallableStatement buildCallableStatement(String sql, CallableStatement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/*  61:    */   {
/*  62:141 */     CallableStatementProxyHandler proxyHandler = new CallableStatementProxyHandler(sql, statement, connectionProxyHandler, connectionProxy);
/*  63:    */     
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:147 */     return (CallableStatement)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), CALLABLE_STMNT_PROXY_INTERFACES, proxyHandler);
/*  69:    */   }
/*  70:    */   
/*  71:157 */   public static final Class[] RESULTSET_PROXY_INTERFACES = { ResultSet.class, JdbcWrapper.class, InvalidatableWrapper.class };
/*  72:    */   
/*  73:    */   public static ResultSet buildResultSet(ResultSet resultSet, AbstractStatementProxyHandler statementProxyHandler, Statement statementProxy)
/*  74:    */   {
/*  75:168 */     ResultSetProxyHandler proxyHandler = new ResultSetProxyHandler(resultSet, statementProxyHandler, statementProxy);
/*  76:169 */     return (ResultSet)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), RESULTSET_PROXY_INTERFACES, proxyHandler);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static ResultSet buildImplicitResultSet(ResultSet resultSet, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/*  80:    */   {
/*  81:180 */     ImplicitResultSetProxyHandler proxyHandler = new ImplicitResultSetProxyHandler(resultSet, connectionProxyHandler, connectionProxy);
/*  82:181 */     return (ResultSet)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), RESULTSET_PROXY_INTERFACES, proxyHandler);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static ResultSet buildImplicitResultSet(ResultSet resultSet, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy, Statement sourceStatement)
/*  86:    */   {
/*  87:193 */     ImplicitResultSetProxyHandler proxyHandler = new ImplicitResultSetProxyHandler(resultSet, connectionProxyHandler, connectionProxy, sourceStatement);
/*  88:194 */     return (ResultSet)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), RESULTSET_PROXY_INTERFACES, proxyHandler);
/*  89:    */   }
/*  90:    */   
/*  91:204 */   public static final Class[] METADATA_PROXY_INTERFACES = { DatabaseMetaData.class, JdbcWrapper.class };
/*  92:    */   
/*  93:    */   public static DatabaseMetaData buildDatabaseMetaData(DatabaseMetaData metaData, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/*  94:    */   {
/*  95:213 */     DatabaseMetaDataProxyHandler handler = new DatabaseMetaDataProxyHandler(metaData, connectionProxyHandler, connectionProxy);
/*  96:214 */     return (DatabaseMetaData)Proxy.newProxyInstance(JdbcWrapper.class.getClassLoader(), METADATA_PROXY_INTERFACES, handler);
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.ProxyBuilder
 * JD-Core Version:    0.7.0.1
 */