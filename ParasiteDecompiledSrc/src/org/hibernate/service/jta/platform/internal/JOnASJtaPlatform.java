/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import javax.transaction.TransactionManager;
/*  5:   */ import javax.transaction.UserTransaction;
/*  6:   */ import org.hibernate.service.jndi.spi.JndiService;
/*  7:   */ import org.hibernate.service.jta.platform.spi.JtaPlatformException;
/*  8:   */ 
/*  9:   */ public class JOnASJtaPlatform
/* 10:   */   extends AbstractJtaPlatform
/* 11:   */ {
/* 12:   */   public static final String UT_NAME = "java:comp/UserTransaction";
/* 13:   */   private static final String TM_CLASS_NAME = "org.objectweb.jonas_tm.Current";
/* 14:   */   
/* 15:   */   protected TransactionManager locateTransactionManager()
/* 16:   */   {
/* 17:   */     try
/* 18:   */     {
/* 19:44 */       Class clazz = Class.forName("org.objectweb.jonas_tm.Current");
/* 20:45 */       Method getTransactionManagerMethod = clazz.getMethod("getTransactionManager", new Class[0]);
/* 21:46 */       return (TransactionManager)getTransactionManagerMethod.invoke(null, new Object[0]);
/* 22:   */     }
/* 23:   */     catch (Exception e)
/* 24:   */     {
/* 25:49 */       throw new JtaPlatformException("Could not obtain JOnAS transaction manager instance", e);
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected UserTransaction locateUserTransaction()
/* 30:   */   {
/* 31:55 */     return (UserTransaction)jndiService().locate("java:comp/UserTransaction");
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.JOnASJtaPlatform
 * JD-Core Version:    0.7.0.1
 */