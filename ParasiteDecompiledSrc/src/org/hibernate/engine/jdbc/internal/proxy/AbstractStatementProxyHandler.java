/*   1:    */ package org.hibernate.engine.jdbc.internal.proxy;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.sql.Connection;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.sql.Statement;
/*   9:    */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/*  10:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  11:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  12:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ public abstract class AbstractStatementProxyHandler
/*  17:    */   extends AbstractProxyHandler
/*  18:    */ {
/*  19: 46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractStatementProxyHandler.class.getName());
/*  20:    */   private ConnectionProxyHandler connectionProxyHandler;
/*  21:    */   private Connection connectionProxy;
/*  22:    */   private Statement statement;
/*  23:    */   
/*  24:    */   protected AbstractStatementProxyHandler(Statement statement, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/*  25:    */   {
/*  26: 57 */     super(statement.hashCode());
/*  27: 58 */     this.statement = statement;
/*  28: 59 */     this.connectionProxyHandler = connectionProxyHandler;
/*  29: 60 */     this.connectionProxy = connectionProxy;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected ConnectionProxyHandler getConnectionProxy()
/*  33:    */   {
/*  34: 64 */     errorIfInvalid();
/*  35: 65 */     return this.connectionProxyHandler;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected JdbcServices getJdbcServices()
/*  39:    */   {
/*  40: 69 */     return getConnectionProxy().getJdbcServices();
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected JdbcResourceRegistry getResourceRegistry()
/*  44:    */   {
/*  45: 73 */     return getConnectionProxy().getResourceRegistry();
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected Statement getStatement()
/*  49:    */   {
/*  50: 77 */     errorIfInvalid();
/*  51: 78 */     return this.statement;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected Statement getStatementWithoutChecks()
/*  55:    */   {
/*  56: 82 */     return this.statement;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Object continueInvocation(Object proxy, Method method, Object[] args)
/*  60:    */     throws Throwable
/*  61:    */   {
/*  62: 87 */     String methodName = method.getName();
/*  63: 88 */     LOG.tracev("Handling invocation of statement method [{0}]", methodName);
/*  64: 91 */     if ("close".equals(methodName))
/*  65:    */     {
/*  66: 92 */       explicitClose((Statement)proxy);
/*  67: 93 */       return null;
/*  68:    */     }
/*  69: 95 */     if ("invalidate".equals(methodName))
/*  70:    */     {
/*  71: 96 */       invalidateHandle();
/*  72: 97 */       return null;
/*  73:    */     }
/*  74:100 */     errorIfInvalid();
/*  75:104 */     if (("isWrapperFor".equals(methodName)) && (args.length == 1)) {
/*  76:105 */       return method.invoke(getStatementWithoutChecks(), args);
/*  77:    */     }
/*  78:107 */     if (("unwrap".equals(methodName)) && (args.length == 1)) {
/*  79:108 */       return method.invoke(getStatementWithoutChecks(), args);
/*  80:    */     }
/*  81:111 */     if ("getWrappedObject".equals(methodName)) {
/*  82:112 */       return getStatementWithoutChecks();
/*  83:    */     }
/*  84:115 */     if ("getConnection".equals(methodName)) {
/*  85:116 */       return this.connectionProxy;
/*  86:    */     }
/*  87:119 */     beginningInvocationHandling(method, args);
/*  88:    */     try
/*  89:    */     {
/*  90:122 */       Object result = method.invoke(this.statement, args);
/*  91:123 */       return wrapIfNecessary(result, proxy, method);
/*  92:    */     }
/*  93:    */     catch (InvocationTargetException e)
/*  94:    */     {
/*  95:127 */       Throwable realException = e.getTargetException();
/*  96:128 */       if (SQLException.class.isInstance(realException)) {
/*  97:129 */         throw this.connectionProxyHandler.getJdbcServices().getSqlExceptionHelper().convert((SQLException)realException, realException.getMessage());
/*  98:    */       }
/*  99:133 */       throw realException;
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   private Object wrapIfNecessary(Object result, Object proxy, Method method)
/* 104:    */   {
/* 105:139 */     if (!ResultSet.class.isAssignableFrom(method.getReturnType())) {
/* 106:140 */       return result;
/* 107:    */     }
/* 108:    */     ResultSet wrapper;
/* 109:    */     ResultSet wrapper;
/* 110:144 */     if ("getGeneratedKeys".equals(method.getName())) {
/* 111:145 */       wrapper = ProxyBuilder.buildImplicitResultSet((ResultSet)result, this.connectionProxyHandler, this.connectionProxy, (Statement)proxy);
/* 112:    */     } else {
/* 113:148 */       wrapper = ProxyBuilder.buildResultSet((ResultSet)result, this, (Statement)proxy);
/* 114:    */     }
/* 115:150 */     getResourceRegistry().register(wrapper);
/* 116:151 */     return wrapper;
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void beginningInvocationHandling(Method method, Object[] args) {}
/* 120:    */   
/* 121:    */   private void explicitClose(Statement proxy)
/* 122:    */   {
/* 123:158 */     if (isValid())
/* 124:    */     {
/* 125:159 */       LogicalConnectionImplementor lc = getConnectionProxy().getLogicalConnection();
/* 126:160 */       getResourceRegistry().release(proxy);
/* 127:161 */       lc.afterStatementExecution();
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   private void invalidateHandle()
/* 132:    */   {
/* 133:166 */     this.connectionProxyHandler = null;
/* 134:167 */     this.statement = null;
/* 135:168 */     invalidate();
/* 136:    */   }
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.AbstractStatementProxyHandler
 * JD-Core Version:    0.7.0.1
 */