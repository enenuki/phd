/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import javax.transaction.TransactionManager;
/*  5:   */ import javax.transaction.UserTransaction;
/*  6:   */ import org.hibernate.service.ServiceRegistry;
/*  7:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  8:   */ import org.hibernate.service.jndi.spi.JndiService;
/*  9:   */ import org.hibernate.service.jta.platform.spi.JtaPlatformException;
/* 10:   */ 
/* 11:   */ public class BitronixJtaPlatform
/* 12:   */   extends AbstractJtaPlatform
/* 13:   */ {
/* 14:   */   private static final String TM_CLASS_NAME = "bitronix.tm.TransactionManagerServices";
/* 15:   */   
/* 16:   */   protected TransactionManager locateTransactionManager()
/* 17:   */   {
/* 18:   */     try
/* 19:   */     {
/* 20:42 */       Class transactionManagerServicesClass = ((ClassLoaderService)serviceRegistry().getService(ClassLoaderService.class)).classForName("bitronix.tm.TransactionManagerServices");
/* 21:43 */       Method getTransactionManagerMethod = transactionManagerServicesClass.getMethod("getTransactionManager", new Class[0]);
/* 22:44 */       return (TransactionManager)getTransactionManagerMethod.invoke(null, new Object[0]);
/* 23:   */     }
/* 24:   */     catch (Exception e)
/* 25:   */     {
/* 26:47 */       throw new JtaPlatformException("Could not locate Bitronix TransactionManager", e);
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected UserTransaction locateUserTransaction()
/* 31:   */   {
/* 32:53 */     return (UserTransaction)jndiService().locate("java:comp/UserTransaction");
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.BitronixJtaPlatform
 * JD-Core Version:    0.7.0.1
 */