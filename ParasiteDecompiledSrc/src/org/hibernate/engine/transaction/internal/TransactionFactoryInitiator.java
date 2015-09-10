/*  1:   */ package org.hibernate.engine.transaction.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.transaction.internal.jdbc.JdbcTransactionFactory;
/*  6:   */ import org.hibernate.engine.transaction.internal.jta.CMTTransactionFactory;
/*  7:   */ import org.hibernate.engine.transaction.internal.jta.JtaTransactionFactory;
/*  8:   */ import org.hibernate.engine.transaction.spi.TransactionFactory;
/*  9:   */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/* 10:   */ import org.hibernate.internal.CoreMessageLogger;
/* 11:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/* 12:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/* 13:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/* 14:   */ import org.jboss.logging.Logger;
/* 15:   */ 
/* 16:   */ public class TransactionFactoryInitiator<T extends TransactionImplementor>
/* 17:   */   implements BasicServiceInitiator<TransactionFactory>
/* 18:   */ {
/* 19:48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TransactionFactoryInitiator.class.getName());
/* 20:51 */   public static final TransactionFactoryInitiator INSTANCE = new TransactionFactoryInitiator();
/* 21:   */   
/* 22:   */   public Class<TransactionFactory> getServiceInitiated()
/* 23:   */   {
/* 24:56 */     return TransactionFactory.class;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public TransactionFactory initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 28:   */   {
/* 29:62 */     Object strategy = configurationValues.get("hibernate.transaction.factory_class");
/* 30:63 */     if (TransactionFactory.class.isInstance(strategy)) {
/* 31:64 */       return (TransactionFactory)strategy;
/* 32:   */     }
/* 33:67 */     if (strategy == null)
/* 34:   */     {
/* 35:68 */       LOG.usingDefaultTransactionStrategy();
/* 36:69 */       return new JdbcTransactionFactory();
/* 37:   */     }
/* 38:72 */     String strategyClassName = mapLegacyNames(strategy.toString());
/* 39:73 */     LOG.transactionStrategy(strategyClassName);
/* 40:   */     
/* 41:75 */     ClassLoaderService classLoaderService = (ClassLoaderService)registry.getService(ClassLoaderService.class);
/* 42:   */     try
/* 43:   */     {
/* 44:77 */       return (TransactionFactory)classLoaderService.classForName(strategyClassName).newInstance();
/* 45:   */     }
/* 46:   */     catch (Exception e)
/* 47:   */     {
/* 48:80 */       throw new HibernateException("Unable to instantiate specified TransactionFactory class [" + strategyClassName + "]", e);
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   private String mapLegacyNames(String name)
/* 53:   */   {
/* 54:85 */     if ("org.hibernate.transaction.JDBCTransactionFactory".equals(name)) {
/* 55:86 */       return JdbcTransactionFactory.class.getName();
/* 56:   */     }
/* 57:89 */     if ("org.hibernate.transaction.JTATransactionFactory".equals(name)) {
/* 58:90 */       return JtaTransactionFactory.class.getName();
/* 59:   */     }
/* 60:93 */     if ("org.hibernate.transaction.CMTTransactionFactory".equals(name)) {
/* 61:94 */       return CMTTransactionFactory.class.getName();
/* 62:   */     }
/* 63:97 */     return name;
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.TransactionFactoryInitiator
 * JD-Core Version:    0.7.0.1
 */