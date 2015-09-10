/*   1:    */ package org.hibernate.service.jta.platform.internal;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import javax.transaction.Synchronization;
/*   5:    */ import javax.transaction.SystemException;
/*   6:    */ import javax.transaction.Transaction;
/*   7:    */ import javax.transaction.TransactionManager;
/*   8:    */ import javax.transaction.UserTransaction;
/*   9:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  10:    */ import org.hibernate.service.ServiceRegistry;
/*  11:    */ import org.hibernate.service.jndi.spi.JndiService;
/*  12:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  13:    */ import org.hibernate.service.spi.Configurable;
/*  14:    */ import org.hibernate.service.spi.ServiceRegistryAwareService;
/*  15:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  16:    */ 
/*  17:    */ public abstract class AbstractJtaPlatform
/*  18:    */   implements JtaPlatform, Configurable, ServiceRegistryAwareService, TransactionManagerAccess
/*  19:    */ {
/*  20:    */   private boolean cacheTransactionManager;
/*  21:    */   private boolean cacheUserTransaction;
/*  22:    */   private ServiceRegistryImplementor serviceRegistry;
/*  23: 51 */   private final JtaSynchronizationStrategy tmSynchronizationStrategy = new TransactionManagerBasedSynchronizationStrategy(this);
/*  24:    */   private TransactionManager transactionManager;
/*  25:    */   private UserTransaction userTransaction;
/*  26:    */   
/*  27:    */   public void injectServices(ServiceRegistryImplementor serviceRegistry)
/*  28:    */   {
/*  29: 55 */     this.serviceRegistry = serviceRegistry;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected ServiceRegistry serviceRegistry()
/*  33:    */   {
/*  34: 59 */     return this.serviceRegistry;
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected JndiService jndiService()
/*  38:    */   {
/*  39: 63 */     return (JndiService)serviceRegistry().getService(JndiService.class);
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected abstract TransactionManager locateTransactionManager();
/*  43:    */   
/*  44:    */   protected abstract UserTransaction locateUserTransaction();
/*  45:    */   
/*  46:    */   public void configure(Map configValues)
/*  47:    */   {
/*  48: 70 */     this.cacheTransactionManager = ConfigurationHelper.getBoolean("hibernate.jta.cacheTransactionManager", configValues, true);
/*  49: 71 */     this.cacheUserTransaction = ConfigurationHelper.getBoolean("hibernate.jta.cacheUserTransaction", configValues, false);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected boolean canCacheTransactionManager()
/*  53:    */   {
/*  54: 75 */     return this.cacheTransactionManager;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected boolean canCacheUserTransaction()
/*  58:    */   {
/*  59: 79 */     return this.cacheUserTransaction;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public TransactionManager retrieveTransactionManager()
/*  63:    */   {
/*  64: 86 */     if (canCacheTransactionManager())
/*  65:    */     {
/*  66: 87 */       if (this.transactionManager == null) {
/*  67: 88 */         this.transactionManager = locateTransactionManager();
/*  68:    */       }
/*  69: 90 */       return this.transactionManager;
/*  70:    */     }
/*  71: 93 */     return locateTransactionManager();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public TransactionManager getTransactionManager()
/*  75:    */   {
/*  76: 99 */     return retrieveTransactionManager();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public UserTransaction retrieveUserTransaction()
/*  80:    */   {
/*  81:106 */     if (canCacheUserTransaction())
/*  82:    */     {
/*  83:107 */       if (this.userTransaction == null) {
/*  84:108 */         this.userTransaction = locateUserTransaction();
/*  85:    */       }
/*  86:110 */       return this.userTransaction;
/*  87:    */     }
/*  88:112 */     return locateUserTransaction();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Object getTransactionIdentifier(Transaction transaction)
/*  92:    */   {
/*  93:118 */     return transaction;
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected JtaSynchronizationStrategy getSynchronizationStrategy()
/*  97:    */   {
/*  98:122 */     return this.tmSynchronizationStrategy;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void registerSynchronization(Synchronization synchronization)
/* 102:    */   {
/* 103:127 */     getSynchronizationStrategy().registerSynchronization(synchronization);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean canRegisterSynchronization()
/* 107:    */   {
/* 108:132 */     return getSynchronizationStrategy().canRegisterSynchronization();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int getCurrentStatus()
/* 112:    */     throws SystemException
/* 113:    */   {
/* 114:137 */     return retrieveTransactionManager().getStatus();
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.AbstractJtaPlatform
 * JD-Core Version:    0.7.0.1
 */