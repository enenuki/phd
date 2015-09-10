/*   1:    */ package org.hibernate.engine.jdbc.internal.proxy;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationHandler;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.sql.CallableStatement;
/*   7:    */ import java.sql.Connection;
/*   8:    */ import java.sql.DatabaseMetaData;
/*   9:    */ import java.sql.PreparedStatement;
/*  10:    */ import java.sql.SQLException;
/*  11:    */ import java.sql.Statement;
/*  12:    */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/*  13:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  14:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  15:    */ import org.hibernate.engine.jdbc.spi.NonDurableConnectionObserver;
/*  16:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  17:    */ import org.hibernate.internal.CoreMessageLogger;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public class ConnectionProxyHandler
/*  21:    */   extends AbstractProxyHandler
/*  22:    */   implements InvocationHandler, NonDurableConnectionObserver
/*  23:    */ {
/*  24: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ConnectionProxyHandler.class.getName());
/*  25:    */   private LogicalConnectionImplementor logicalConnection;
/*  26:    */   
/*  27:    */   public ConnectionProxyHandler(LogicalConnectionImplementor logicalConnection)
/*  28:    */   {
/*  29: 59 */     super(logicalConnection.hashCode());
/*  30: 60 */     this.logicalConnection = logicalConnection;
/*  31: 61 */     this.logicalConnection.addObserver(this);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected LogicalConnectionImplementor getLogicalConnection()
/*  35:    */   {
/*  36: 70 */     errorIfInvalid();
/*  37: 71 */     return this.logicalConnection;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private Connection extractPhysicalConnection()
/*  41:    */   {
/*  42: 82 */     return this.logicalConnection.getConnection();
/*  43:    */   }
/*  44:    */   
/*  45:    */   JdbcServices getJdbcServices()
/*  46:    */   {
/*  47: 93 */     return this.logicalConnection.getJdbcServices();
/*  48:    */   }
/*  49:    */   
/*  50:    */   JdbcResourceRegistry getResourceRegistry()
/*  51:    */   {
/*  52:104 */     return this.logicalConnection.getResourceRegistry();
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected Object continueInvocation(Object proxy, Method method, Object[] args)
/*  56:    */     throws Throwable
/*  57:    */   {
/*  58:109 */     String methodName = method.getName();
/*  59:110 */     LOG.tracev("Handling invocation of connection method [{0}]", methodName);
/*  60:113 */     if ("close".equals(methodName))
/*  61:    */     {
/*  62:114 */       explicitClose();
/*  63:115 */       return null;
/*  64:    */     }
/*  65:118 */     if ("isClosed".equals(methodName)) {
/*  66:119 */       return Boolean.valueOf(!isValid());
/*  67:    */     }
/*  68:122 */     errorIfInvalid();
/*  69:126 */     if (("isWrapperFor".equals(methodName)) && (args.length == 1)) {
/*  70:127 */       return method.invoke(extractPhysicalConnection(), args);
/*  71:    */     }
/*  72:129 */     if (("unwrap".equals(methodName)) && (args.length == 1)) {
/*  73:130 */       return method.invoke(extractPhysicalConnection(), args);
/*  74:    */     }
/*  75:133 */     if ("getWrappedObject".equals(methodName)) {
/*  76:134 */       return extractPhysicalConnection();
/*  77:    */     }
/*  78:    */     try
/*  79:    */     {
/*  80:138 */       Object result = method.invoke(extractPhysicalConnection(), args);
/*  81:139 */       return postProcess(result, proxy, method, args);
/*  82:    */     }
/*  83:    */     catch (InvocationTargetException e)
/*  84:    */     {
/*  85:144 */       Throwable realException = e.getTargetException();
/*  86:145 */       if (SQLException.class.isInstance(realException)) {
/*  87:146 */         throw this.logicalConnection.getJdbcServices().getSqlExceptionHelper().convert((SQLException)realException, realException.getMessage());
/*  88:    */       }
/*  89:150 */       throw realException;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   private Object postProcess(Object result, Object proxy, Method method, Object[] args)
/*  94:    */     throws SQLException
/*  95:    */   {
/*  96:156 */     String methodName = method.getName();
/*  97:157 */     Object wrapped = result;
/*  98:158 */     if ("createStatement".equals(methodName))
/*  99:    */     {
/* 100:159 */       wrapped = ProxyBuilder.buildStatement((Statement)result, this, (Connection)proxy);
/* 101:    */       
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:164 */       postProcessStatement((Statement)wrapped);
/* 106:    */     }
/* 107:166 */     else if ("prepareStatement".equals(methodName))
/* 108:    */     {
/* 109:167 */       wrapped = ProxyBuilder.buildPreparedStatement((String)args[0], (PreparedStatement)result, this, (Connection)proxy);
/* 110:    */       
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:173 */       postProcessPreparedStatement((Statement)wrapped);
/* 116:    */     }
/* 117:175 */     else if ("prepareCall".equals(methodName))
/* 118:    */     {
/* 119:176 */       wrapped = ProxyBuilder.buildCallableStatement((String)args[0], (CallableStatement)result, this, (Connection)proxy);
/* 120:    */       
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:182 */       postProcessPreparedStatement((Statement)wrapped);
/* 126:    */     }
/* 127:184 */     else if ("getMetaData".equals(methodName))
/* 128:    */     {
/* 129:185 */       wrapped = ProxyBuilder.buildDatabaseMetaData((DatabaseMetaData)result, this, (Connection)proxy);
/* 130:    */     }
/* 131:187 */     return wrapped;
/* 132:    */   }
/* 133:    */   
/* 134:    */   private void postProcessStatement(Statement statement)
/* 135:    */     throws SQLException
/* 136:    */   {
/* 137:191 */     getResourceRegistry().register(statement);
/* 138:    */   }
/* 139:    */   
/* 140:    */   private void postProcessPreparedStatement(Statement statement)
/* 141:    */     throws SQLException
/* 142:    */   {
/* 143:195 */     this.logicalConnection.notifyObserversStatementPrepared();
/* 144:196 */     postProcessStatement(statement);
/* 145:    */   }
/* 146:    */   
/* 147:    */   private void explicitClose()
/* 148:    */   {
/* 149:200 */     if (isValid()) {
/* 150:201 */       invalidateHandle();
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private void invalidateHandle()
/* 155:    */   {
/* 156:206 */     LOG.trace("Invalidating connection handle");
/* 157:207 */     this.logicalConnection = null;
/* 158:208 */     invalidate();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void physicalConnectionObtained(Connection connection) {}
/* 162:    */   
/* 163:    */   public void physicalConnectionReleased()
/* 164:    */   {
/* 165:219 */     LOG.logicalConnectionReleasingPhysicalConnection();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void logicalConnectionClosed()
/* 169:    */   {
/* 170:224 */     LOG.logicalConnectionClosed();
/* 171:225 */     invalidateHandle();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void statementPrepared() {}
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.ConnectionProxyHandler
 * JD-Core Version:    0.7.0.1
 */