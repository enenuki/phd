/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import javax.transaction.TransactionManager;
/*  4:   */ import javax.transaction.UserTransaction;
/*  5:   */ import org.hibernate.service.jndi.spi.JndiService;
/*  6:   */ 
/*  7:   */ public class WeblogicJtaPlatform
/*  8:   */   extends AbstractJtaPlatform
/*  9:   */ {
/* 10:   */   public static final String TM_NAME = "javax.transaction.TransactionManager";
/* 11:   */   public static final String UT_NAME = "javax.transaction.UserTransaction";
/* 12:   */   
/* 13:   */   protected TransactionManager locateTransactionManager()
/* 14:   */   {
/* 15:41 */     return (TransactionManager)jndiService().locate("javax.transaction.TransactionManager");
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected UserTransaction locateUserTransaction()
/* 19:   */   {
/* 20:46 */     return (UserTransaction)jndiService().locate("javax.transaction.UserTransaction");
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.WeblogicJtaPlatform
 * JD-Core Version:    0.7.0.1
 */