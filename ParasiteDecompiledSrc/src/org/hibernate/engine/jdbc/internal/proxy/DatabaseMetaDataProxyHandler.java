/*  1:   */ package org.hibernate.engine.jdbc.internal.proxy;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.sql.Connection;
/*  6:   */ import java.sql.DatabaseMetaData;
/*  7:   */ import java.sql.ResultSet;
/*  8:   */ import java.sql.SQLException;
/*  9:   */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/* 10:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/* 11:   */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/* 12:   */ 
/* 13:   */ public class DatabaseMetaDataProxyHandler
/* 14:   */   extends AbstractProxyHandler
/* 15:   */ {
/* 16:   */   private ConnectionProxyHandler connectionProxyHandler;
/* 17:   */   private Connection connectionProxy;
/* 18:   */   private DatabaseMetaData databaseMetaData;
/* 19:   */   
/* 20:   */   public DatabaseMetaDataProxyHandler(DatabaseMetaData databaseMetaData, ConnectionProxyHandler connectionProxyHandler, Connection connectionProxy)
/* 21:   */   {
/* 22:47 */     super(databaseMetaData.hashCode());
/* 23:48 */     this.connectionProxyHandler = connectionProxyHandler;
/* 24:49 */     this.connectionProxy = connectionProxy;
/* 25:50 */     this.databaseMetaData = databaseMetaData;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected Object continueInvocation(Object proxy, Method method, Object[] args)
/* 29:   */     throws Throwable
/* 30:   */   {
/* 31:56 */     if (("isWrapperFor".equals(method.getName())) && (args.length == 1)) {
/* 32:57 */       return method.invoke(this.databaseMetaData, args);
/* 33:   */     }
/* 34:59 */     if (("unwrap".equals(method.getName())) && (args.length == 1)) {
/* 35:60 */       return method.invoke(this.databaseMetaData, args);
/* 36:   */     }
/* 37:   */     try
/* 38:   */     {
/* 39:64 */       boolean exposingResultSet = doesMethodExposeResultSet(method);
/* 40:   */       
/* 41:66 */       Object result = method.invoke(this.databaseMetaData, args);
/* 42:68 */       if (exposingResultSet)
/* 43:   */       {
/* 44:69 */         result = ProxyBuilder.buildImplicitResultSet((ResultSet)result, this.connectionProxyHandler, this.connectionProxy);
/* 45:70 */         this.connectionProxyHandler.getResourceRegistry().register((ResultSet)result);
/* 46:   */       }
/* 47:73 */       return result;
/* 48:   */     }
/* 49:   */     catch (InvocationTargetException e)
/* 50:   */     {
/* 51:76 */       Throwable realException = e.getTargetException();
/* 52:77 */       if (SQLException.class.isInstance(realException)) {
/* 53:78 */         throw this.connectionProxyHandler.getJdbcServices().getSqlExceptionHelper().convert((SQLException)realException, realException.getMessage());
/* 54:   */       }
/* 55:82 */       throw realException;
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:   */   protected boolean doesMethodExposeResultSet(Method method)
/* 60:   */   {
/* 61:88 */     return ResultSet.class.isAssignableFrom(method.getReturnType());
/* 62:   */   }
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.DatabaseMetaDataProxyHandler
 * JD-Core Version:    0.7.0.1
 */