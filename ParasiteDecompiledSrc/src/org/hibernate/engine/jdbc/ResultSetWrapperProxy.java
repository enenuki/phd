/*   1:    */ package org.hibernate.engine.jdbc;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationHandler;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.lang.reflect.Proxy;
/*   7:    */ import java.sql.ResultSet;
/*   8:    */ import java.sql.SQLException;
/*   9:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.jboss.logging.Logger;
/*  12:    */ 
/*  13:    */ public class ResultSetWrapperProxy
/*  14:    */   implements InvocationHandler
/*  15:    */ {
/*  16: 47 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ResultSetWrapperProxy.class.getName());
/*  17: 48 */   private static final Class[] PROXY_INTERFACES = { ResultSet.class };
/*  18: 49 */   private static final SqlExceptionHelper sqlExceptionHelper = new SqlExceptionHelper();
/*  19:    */   private final ResultSet rs;
/*  20:    */   private final ColumnNameCache columnNameCache;
/*  21:    */   
/*  22:    */   private ResultSetWrapperProxy(ResultSet rs, ColumnNameCache columnNameCache)
/*  23:    */   {
/*  24: 55 */     this.rs = rs;
/*  25: 56 */     this.columnNameCache = columnNameCache;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static ResultSet generateProxy(ResultSet resultSet, ColumnNameCache columnNameCache)
/*  29:    */   {
/*  30: 67 */     return (ResultSet)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new ResultSetWrapperProxy(resultSet, columnNameCache));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static ClassLoader getProxyClassLoader()
/*  34:    */   {
/*  35: 81 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  36: 82 */     if (cl == null) {
/*  37: 83 */       cl = ResultSet.class.getClassLoader();
/*  38:    */     }
/*  39: 85 */     return cl;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object invoke(Object proxy, Method method, Object[] args)
/*  43:    */     throws Throwable
/*  44:    */   {
/*  45: 91 */     if ("findColumn".equals(method.getName())) {
/*  46: 92 */       return Integer.valueOf(findColumn((String)args[0]));
/*  47:    */     }
/*  48: 95 */     if (isFirstArgColumnLabel(method, args)) {
/*  49:    */       try
/*  50:    */       {
/*  51: 97 */         int columnIndex = findColumn((String)args[0]);
/*  52: 98 */         return invokeMethod(locateCorrespondingColumnIndexMethod(method), buildColumnIndexMethodArgs(args, columnIndex));
/*  53:    */       }
/*  54:    */       catch (SQLException ex)
/*  55:    */       {
/*  56:103 */         StringBuffer buf = new StringBuffer().append("Exception getting column index for column: [").append(args[0]).append("].\nReverting to using: [").append(args[0]).append("] as first argument for method: [").append(method).append("]");
/*  57:    */         
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:111 */         sqlExceptionHelper.logExceptions(ex, buf.toString());
/*  65:    */       }
/*  66:    */       catch (NoSuchMethodException ex)
/*  67:    */       {
/*  68:114 */         LOG.unableToSwitchToMethodUsingColumnIndex(method);
/*  69:    */       }
/*  70:    */     }
/*  71:117 */     return invokeMethod(method, args);
/*  72:    */   }
/*  73:    */   
/*  74:    */   private int findColumn(String columnName)
/*  75:    */     throws SQLException
/*  76:    */   {
/*  77:128 */     return this.columnNameCache.getIndexForColumnName(columnName, this.rs);
/*  78:    */   }
/*  79:    */   
/*  80:    */   private boolean isFirstArgColumnLabel(Method method, Object[] args)
/*  81:    */   {
/*  82:133 */     if ((!method.getName().startsWith("get")) && (!method.getName().startsWith("update"))) {
/*  83:134 */       return false;
/*  84:    */     }
/*  85:138 */     if ((method.getParameterTypes().length <= 0) || (args.length != method.getParameterTypes().length)) {
/*  86:139 */       return false;
/*  87:    */     }
/*  88:144 */     if ((!String.class.isInstance(args[0])) || (!method.getParameterTypes()[0].equals(String.class))) {
/*  89:145 */       return false;
/*  90:    */     }
/*  91:148 */     return true;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private Method locateCorrespondingColumnIndexMethod(Method columnNameMethod)
/*  95:    */     throws NoSuchMethodException
/*  96:    */   {
/*  97:160 */     Class[] actualParameterTypes = new Class[columnNameMethod.getParameterTypes().length];
/*  98:161 */     actualParameterTypes[0] = Integer.TYPE;
/*  99:162 */     System.arraycopy(columnNameMethod.getParameterTypes(), 1, actualParameterTypes, 1, columnNameMethod.getParameterTypes().length - 1);
/* 100:    */     
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:169 */     return columnNameMethod.getDeclaringClass().getMethod(columnNameMethod.getName(), actualParameterTypes);
/* 107:    */   }
/* 108:    */   
/* 109:    */   private Object[] buildColumnIndexMethodArgs(Object[] incomingArgs, int columnIndex)
/* 110:    */   {
/* 111:174 */     Object[] actualArgs = new Object[incomingArgs.length];
/* 112:175 */     actualArgs[0] = Integer.valueOf(columnIndex);
/* 113:176 */     System.arraycopy(incomingArgs, 1, actualArgs, 1, incomingArgs.length - 1);
/* 114:177 */     return actualArgs;
/* 115:    */   }
/* 116:    */   
/* 117:    */   private Object invokeMethod(Method method, Object[] args)
/* 118:    */     throws Throwable
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:182 */       return method.invoke(this.rs, args);
/* 123:    */     }
/* 124:    */     catch (InvocationTargetException e)
/* 125:    */     {
/* 126:185 */       throw e.getTargetException();
/* 127:    */     }
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.ResultSetWrapperProxy
 * JD-Core Version:    0.7.0.1
 */