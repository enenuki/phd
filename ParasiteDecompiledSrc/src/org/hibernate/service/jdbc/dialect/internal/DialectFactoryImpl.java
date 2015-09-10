/*   1:    */ package org.hibernate.service.jdbc.dialect.internal;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.DatabaseMetaData;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  10:    */ import org.hibernate.service.classloading.spi.ClassLoadingException;
/*  11:    */ import org.hibernate.service.jdbc.dialect.spi.DialectFactory;
/*  12:    */ import org.hibernate.service.jdbc.dialect.spi.DialectResolver;
/*  13:    */ import org.hibernate.service.spi.InjectService;
/*  14:    */ 
/*  15:    */ public class DialectFactoryImpl
/*  16:    */   implements DialectFactory
/*  17:    */ {
/*  18:    */   private ClassLoaderService classLoaderService;
/*  19:    */   private DialectResolver dialectResolver;
/*  20:    */   
/*  21:    */   @InjectService
/*  22:    */   public void setClassLoaderService(ClassLoaderService classLoaderService)
/*  23:    */   {
/*  24: 50 */     this.classLoaderService = classLoaderService;
/*  25:    */   }
/*  26:    */   
/*  27:    */   @InjectService
/*  28:    */   public void setDialectResolver(DialectResolver dialectResolver)
/*  29:    */   {
/*  30: 57 */     this.dialectResolver = dialectResolver;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Dialect buildDialect(Map configValues, Connection connection)
/*  34:    */     throws HibernateException
/*  35:    */   {
/*  36: 62 */     String dialectName = (String)configValues.get("hibernate.dialect");
/*  37: 63 */     if (dialectName != null) {
/*  38: 64 */       return constructDialect(dialectName);
/*  39:    */     }
/*  40: 67 */     return determineDialect(connection);
/*  41:    */   }
/*  42:    */   
/*  43:    */   private Dialect constructDialect(String dialectName)
/*  44:    */   {
/*  45:    */     try
/*  46:    */     {
/*  47: 73 */       return (Dialect)this.classLoaderService.classForName(dialectName).newInstance();
/*  48:    */     }
/*  49:    */     catch (ClassLoadingException e)
/*  50:    */     {
/*  51: 76 */       throw new HibernateException("Dialect class not found: " + dialectName, e);
/*  52:    */     }
/*  53:    */     catch (HibernateException e)
/*  54:    */     {
/*  55: 79 */       throw e;
/*  56:    */     }
/*  57:    */     catch (Exception e)
/*  58:    */     {
/*  59: 82 */       throw new HibernateException("Could not instantiate dialect class", e);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   private Dialect determineDialect(Connection connection)
/*  64:    */   {
/*  65: 96 */     if (connection == null) {
/*  66: 97 */       throw new HibernateException("Connection cannot be null when 'hibernate.dialect' not set");
/*  67:    */     }
/*  68:    */     try
/*  69:    */     {
/*  70:101 */       DatabaseMetaData databaseMetaData = connection.getMetaData();
/*  71:102 */       Dialect dialect = this.dialectResolver.resolveDialect(databaseMetaData);
/*  72:104 */       if (dialect == null) {
/*  73:105 */         throw new HibernateException("Unable to determine Dialect to use [name=" + databaseMetaData.getDatabaseProductName() + ", majorVersion=" + databaseMetaData.getDatabaseMajorVersion() + "]; user must register resolver or explicitly set 'hibernate.dialect'");
/*  74:    */       }
/*  75:112 */       return dialect;
/*  76:    */     }
/*  77:    */     catch (SQLException sqlException)
/*  78:    */     {
/*  79:115 */       throw new HibernateException("Unable to access java.sql.DatabaseMetaData to determine appropriate Dialect to use", sqlException);
/*  80:    */     }
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.internal.DialectFactoryImpl
 * JD-Core Version:    0.7.0.1
 */