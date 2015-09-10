/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import javax.transaction.TransactionManager;
/*  4:   */ import javax.transaction.UserTransaction;
/*  5:   */ import org.hibernate.service.jndi.spi.JndiService;
/*  6:   */ 
/*  7:   */ public class BorlandEnterpriseServerJtaPlatform
/*  8:   */   extends AbstractJtaPlatform
/*  9:   */ {
/* 10:   */   protected static final String TM_NAME = "java:pm/TransactionManager";
/* 11:   */   protected static final String UT_NAME = "java:comp/UserTransaction";
/* 12:   */   
/* 13:   */   protected TransactionManager locateTransactionManager()
/* 14:   */   {
/* 15:38 */     return (TransactionManager)jndiService().locate("java:pm/TransactionManager");
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected UserTransaction locateUserTransaction()
/* 19:   */   {
/* 20:43 */     return (UserTransaction)jndiService().locate("java:comp/UserTransaction");
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.BorlandEnterpriseServerJtaPlatform
 * JD-Core Version:    0.7.0.1
 */