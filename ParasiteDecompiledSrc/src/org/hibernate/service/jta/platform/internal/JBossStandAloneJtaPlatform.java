/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import javax.transaction.TransactionManager;
/*  5:   */ import javax.transaction.UserTransaction;
/*  6:   */ import org.hibernate.service.ServiceRegistry;
/*  7:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  8:   */ import org.hibernate.service.jta.platform.spi.JtaPlatformException;
/*  9:   */ 
/* 10:   */ public class JBossStandAloneJtaPlatform
/* 11:   */   extends AbstractJtaPlatform
/* 12:   */ {
/* 13:   */   private static final String JBOSS_TM_CLASS_NAME = "com.arjuna.ats.jta.TransactionManager";
/* 14:   */   private static final String JBOSS_UT_CLASS_NAME = "com.arjuna.ats.jta.UserTransaction";
/* 15:   */   
/* 16:   */   protected TransactionManager locateTransactionManager()
/* 17:   */   {
/* 18:   */     try
/* 19:   */     {
/* 20:46 */       Class jbossTmClass = ((ClassLoaderService)serviceRegistry().getService(ClassLoaderService.class)).classForName("com.arjuna.ats.jta.TransactionManager");
/* 21:   */       
/* 22:   */ 
/* 23:49 */       return (TransactionManager)jbossTmClass.getMethod("transactionManager", new Class[0]).invoke(null, new Object[0]);
/* 24:   */     }
/* 25:   */     catch (Exception e)
/* 26:   */     {
/* 27:52 */       throw new JtaPlatformException("Could not obtain JBoss Transactions transaction manager instance", e);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected UserTransaction locateUserTransaction()
/* 32:   */   {
/* 33:   */     try
/* 34:   */     {
/* 35:59 */       Class jbossUtClass = ((ClassLoaderService)serviceRegistry().getService(ClassLoaderService.class)).classForName("com.arjuna.ats.jta.UserTransaction");
/* 36:   */       
/* 37:   */ 
/* 38:62 */       return (UserTransaction)jbossUtClass.getMethod("userTransaction", new Class[0]).invoke(null, new Object[0]);
/* 39:   */     }
/* 40:   */     catch (Exception e)
/* 41:   */     {
/* 42:65 */       throw new JtaPlatformException("Could not obtain JBoss Transactions user transaction instance", e);
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.JBossStandAloneJtaPlatform
 * JD-Core Version:    0.7.0.1
 */