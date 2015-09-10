/*   1:    */ package org.hibernate.exception.spi;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.Properties;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.JDBCException;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.exception.GenericJDBCException;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.internal.util.ReflectHelper;
/*  12:    */ import org.hibernate.internal.util.StringHelper;
/*  13:    */ import org.jboss.logging.Logger;
/*  14:    */ 
/*  15:    */ public class SQLExceptionConverterFactory
/*  16:    */ {
/*  17: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SQLExceptionConverterFactory.class.getName());
/*  18:    */   
/*  19:    */   public static SQLExceptionConverter buildSQLExceptionConverter(Dialect dialect, Properties properties)
/*  20:    */     throws HibernateException
/*  21:    */   {
/*  22: 68 */     SQLExceptionConverter converter = null;
/*  23:    */     
/*  24: 70 */     String converterClassName = (String)properties.get("hibernate.jdbc.sql_exception_converter");
/*  25: 71 */     if (StringHelper.isNotEmpty(converterClassName)) {
/*  26: 72 */       converter = constructConverter(converterClassName, dialect.getViolatedConstraintNameExtracter());
/*  27:    */     }
/*  28: 75 */     if (converter == null)
/*  29:    */     {
/*  30: 76 */       LOG.trace("Using dialect defined converter");
/*  31: 77 */       converter = dialect.buildSQLExceptionConverter();
/*  32:    */     }
/*  33: 80 */     if ((converter instanceof Configurable)) {
/*  34:    */       try
/*  35:    */       {
/*  36: 82 */         ((Configurable)converter).configure(properties);
/*  37:    */       }
/*  38:    */       catch (HibernateException e)
/*  39:    */       {
/*  40: 85 */         LOG.unableToConfigureSqlExceptionConverter(e);
/*  41: 86 */         throw e;
/*  42:    */       }
/*  43:    */     }
/*  44: 90 */     return converter;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static SQLExceptionConverter buildMinimalSQLExceptionConverter()
/*  48:    */   {
/*  49:100 */     new SQLExceptionConverter()
/*  50:    */     {
/*  51:    */       public JDBCException convert(SQLException sqlException, String message, String sql)
/*  52:    */       {
/*  53:102 */         return new GenericJDBCException(message, sqlException, sql);
/*  54:    */       }
/*  55:    */     };
/*  56:    */   }
/*  57:    */   
/*  58:    */   private static SQLExceptionConverter constructConverter(String converterClassName, ViolatedConstraintNameExtracter violatedConstraintNameExtracter)
/*  59:    */   {
/*  60:    */     try
/*  61:    */     {
/*  62:109 */       LOG.tracev("Attempting to construct instance of specified SQLExceptionConverter [{0}]", converterClassName);
/*  63:110 */       Class converterClass = ReflectHelper.classForName(converterClassName);
/*  64:    */       
/*  65:    */ 
/*  66:113 */       Constructor[] ctors = converterClass.getDeclaredConstructors();
/*  67:114 */       for (int i = 0; i < ctors.length; i++) {
/*  68:115 */         if ((ctors[i].getParameterTypes() != null) && (ctors[i].getParameterTypes().length == 1) && 
/*  69:116 */           (ViolatedConstraintNameExtracter.class.isAssignableFrom(ctors[i].getParameterTypes()[0]))) {
/*  70:    */           try
/*  71:    */           {
/*  72:118 */             return (SQLExceptionConverter)ctors[i].newInstance(new Object[] { violatedConstraintNameExtracter });
/*  73:    */           }
/*  74:    */           catch (Throwable t) {}
/*  75:    */         }
/*  76:    */       }
/*  77:129 */       return (SQLExceptionConverter)converterClass.newInstance();
/*  78:    */     }
/*  79:    */     catch (Throwable t)
/*  80:    */     {
/*  81:133 */       LOG.unableToConstructSqlExceptionConverter(t);
/*  82:    */     }
/*  83:136 */     return null;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.spi.SQLExceptionConverterFactory
 * JD-Core Version:    0.7.0.1
 */