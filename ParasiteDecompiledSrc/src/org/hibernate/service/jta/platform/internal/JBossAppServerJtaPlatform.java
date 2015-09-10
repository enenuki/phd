/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import javax.transaction.TransactionManager;
/*  4:   */ import javax.transaction.UserTransaction;
/*  5:   */ import org.hibernate.service.jndi.JndiException;
/*  6:   */ import org.hibernate.service.jndi.spi.JndiService;
/*  7:   */ 
/*  8:   */ public class JBossAppServerJtaPlatform
/*  9:   */   extends AbstractJtaPlatform
/* 10:   */ {
/* 11:   */   public static final String AS7_TM_NAME = "java:jboss/TransactionManager";
/* 12:   */   public static final String AS4_TM_NAME = "java:/TransactionManager";
/* 13:   */   public static final String UT_NAME = "java:comp/UserTransaction";
/* 14:   */   
/* 15:   */   protected TransactionManager locateTransactionManager()
/* 16:   */   {
/* 17:   */     try
/* 18:   */     {
/* 19:44 */       return (TransactionManager)jndiService().locate("java:jboss/TransactionManager");
/* 20:   */     }
/* 21:   */     catch (JndiException jndiException)
/* 22:   */     {
/* 23:   */       try
/* 24:   */       {
/* 25:48 */         return (TransactionManager)jndiService().locate("java:/TransactionManager");
/* 26:   */       }
/* 27:   */       catch (JndiException jndiExceptionInner)
/* 28:   */       {
/* 29:51 */         throw new JndiException("unable to find transaction manager", jndiException);
/* 30:   */       }
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected UserTransaction locateUserTransaction()
/* 35:   */   {
/* 36:58 */     return (UserTransaction)jndiService().locate("java:comp/UserTransaction");
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform
 * JD-Core Version:    0.7.0.1
 */