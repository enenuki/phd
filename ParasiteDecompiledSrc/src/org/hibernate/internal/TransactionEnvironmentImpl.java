/*  1:   */ package org.hibernate.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  6:   */ import org.hibernate.engine.transaction.spi.TransactionFactory;
/*  7:   */ import org.hibernate.service.ServiceRegistry;
/*  8:   */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  9:   */ import org.hibernate.stat.spi.StatisticsImplementor;
/* 10:   */ 
/* 11:   */ public class TransactionEnvironmentImpl
/* 12:   */   implements TransactionEnvironment
/* 13:   */ {
/* 14:   */   private final SessionFactoryImpl sessionFactory;
/* 15:   */   private final transient StatisticsImplementor statisticsImplementor;
/* 16:   */   private final transient ServiceRegistry serviceRegistry;
/* 17:   */   private final transient JdbcServices jdbcServices;
/* 18:   */   private final transient JtaPlatform jtaPlatform;
/* 19:   */   private final transient TransactionFactory transactionFactory;
/* 20:   */   
/* 21:   */   public TransactionEnvironmentImpl(SessionFactoryImpl sessionFactory)
/* 22:   */   {
/* 23:46 */     this.sessionFactory = sessionFactory;
/* 24:47 */     this.statisticsImplementor = sessionFactory.getStatisticsImplementor();
/* 25:48 */     this.serviceRegistry = sessionFactory.getServiceRegistry();
/* 26:49 */     this.jdbcServices = ((JdbcServices)this.serviceRegistry.getService(JdbcServices.class));
/* 27:50 */     this.jtaPlatform = ((JtaPlatform)this.serviceRegistry.getService(JtaPlatform.class));
/* 28:51 */     this.transactionFactory = ((TransactionFactory)this.serviceRegistry.getService(TransactionFactory.class));
/* 29:   */   }
/* 30:   */   
/* 31:   */   public SessionFactoryImplementor getSessionFactory()
/* 32:   */   {
/* 33:56 */     return this.sessionFactory;
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected ServiceRegistry serviceRegistry()
/* 37:   */   {
/* 38:60 */     return this.serviceRegistry;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public JdbcServices getJdbcServices()
/* 42:   */   {
/* 43:65 */     return this.jdbcServices;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public JtaPlatform getJtaPlatform()
/* 47:   */   {
/* 48:70 */     return this.jtaPlatform;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public TransactionFactory getTransactionFactory()
/* 52:   */   {
/* 53:75 */     return this.transactionFactory;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public StatisticsImplementor getStatisticsImplementor()
/* 57:   */   {
/* 58:80 */     return this.statisticsImplementor;
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.TransactionEnvironmentImpl
 * JD-Core Version:    0.7.0.1
 */