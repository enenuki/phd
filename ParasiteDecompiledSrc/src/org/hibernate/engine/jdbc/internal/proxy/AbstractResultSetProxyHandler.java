/*   1:    */ package org.hibernate.engine.jdbc.internal.proxy;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.sql.Statement;
/*   8:    */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/*   9:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  10:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  11:    */ import org.hibernate.internal.CoreMessageLogger;
/*  12:    */ import org.jboss.logging.Logger;
/*  13:    */ 
/*  14:    */ public abstract class AbstractResultSetProxyHandler
/*  15:    */   extends AbstractProxyHandler
/*  16:    */ {
/*  17: 44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractResultSetProxyHandler.class.getName());
/*  18:    */   private ResultSet resultSet;
/*  19:    */   
/*  20:    */   public AbstractResultSetProxyHandler(ResultSet resultSet)
/*  21:    */   {
/*  22: 50 */     super(resultSet.hashCode());
/*  23: 51 */     this.resultSet = resultSet;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected abstract JdbcServices getJdbcServices();
/*  27:    */   
/*  28:    */   protected abstract JdbcResourceRegistry getResourceRegistry();
/*  29:    */   
/*  30:    */   protected abstract Statement getExposableStatement();
/*  31:    */   
/*  32:    */   protected final ResultSet getResultSet()
/*  33:    */   {
/*  34: 61 */     errorIfInvalid();
/*  35: 62 */     return this.resultSet;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected final ResultSet getResultSetWithoutChecks()
/*  39:    */   {
/*  40: 66 */     return this.resultSet;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected Object continueInvocation(Object proxy, Method method, Object[] args)
/*  44:    */     throws Throwable
/*  45:    */   {
/*  46: 71 */     String methodName = method.getName();
/*  47: 72 */     LOG.tracev("Handling invocation of ResultSet method [{0}]", methodName);
/*  48: 75 */     if ("close".equals(methodName))
/*  49:    */     {
/*  50: 76 */       explicitClose((ResultSet)proxy);
/*  51: 77 */       return null;
/*  52:    */     }
/*  53: 79 */     if ("invalidate".equals(methodName))
/*  54:    */     {
/*  55: 80 */       invalidateHandle();
/*  56: 81 */       return null;
/*  57:    */     }
/*  58: 84 */     errorIfInvalid();
/*  59: 88 */     if (("isWrapperFor".equals(methodName)) && (args.length == 1)) {
/*  60: 89 */       return method.invoke(getResultSetWithoutChecks(), args);
/*  61:    */     }
/*  62: 91 */     if (("unwrap".equals(methodName)) && (args.length == 1)) {
/*  63: 92 */       return method.invoke(getResultSetWithoutChecks(), args);
/*  64:    */     }
/*  65: 95 */     if ("getWrappedObject".equals(methodName)) {
/*  66: 96 */       return getResultSetWithoutChecks();
/*  67:    */     }
/*  68: 99 */     if ("getStatement".equals(methodName)) {
/*  69:100 */       return getExposableStatement();
/*  70:    */     }
/*  71:    */     try
/*  72:    */     {
/*  73:104 */       return method.invoke(this.resultSet, args);
/*  74:    */     }
/*  75:    */     catch (InvocationTargetException e)
/*  76:    */     {
/*  77:107 */       Throwable realException = e.getTargetException();
/*  78:108 */       if (SQLException.class.isInstance(realException)) {
/*  79:108 */         throw getJdbcServices().getSqlExceptionHelper().convert((SQLException)realException, realException.getMessage());
/*  80:    */       }
/*  81:110 */       throw realException;
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void explicitClose(ResultSet proxy)
/*  86:    */   {
/*  87:115 */     if (isValid()) {
/*  88:116 */       getResourceRegistry().release(proxy);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected void invalidateHandle()
/*  93:    */   {
/*  94:121 */     this.resultSet = null;
/*  95:122 */     invalidate();
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.AbstractResultSetProxyHandler
 * JD-Core Version:    0.7.0.1
 */