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
/* 11:   */ public class JOTMJtaPlatform
/* 12:   */   extends AbstractJtaPlatform
/* 13:   */ {
/* 14:   */   public static final String TM_CLASS_NAME = "org.objectweb.jotm.Current";
/* 15:   */   public static final String UT_NAME = "java:comp/UserTransaction";
/* 16:   */   
/* 17:   */   protected TransactionManager locateTransactionManager()
/* 18:   */   {
/* 19:   */     try
/* 20:   */     {
/* 21:43 */       Class tmClass = ((ClassLoaderService)serviceRegistry().getService(ClassLoaderService.class)).classForName("org.objectweb.jotm.Current");
/* 22:44 */       Method getTransactionManagerMethod = tmClass.getMethod("getTransactionManager", new Class[0]);
/* 23:45 */       return (TransactionManager)getTransactionManagerMethod.invoke(null, (Object[])null);
/* 24:   */     }
/* 25:   */     catch (Exception e)
/* 26:   */     {
/* 27:48 */       throw new JtaPlatformException("Could not obtain JOTM transaction manager instance", e);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected UserTransaction locateUserTransaction()
/* 32:   */   {
/* 33:54 */     return (UserTransaction)jndiService().locate("java:comp/UserTransaction");
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.JOTMJtaPlatform
 * JD-Core Version:    0.7.0.1
 */