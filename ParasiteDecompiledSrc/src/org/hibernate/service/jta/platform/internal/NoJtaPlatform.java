/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import javax.transaction.Synchronization;
/*  4:   */ import javax.transaction.SystemException;
/*  5:   */ import javax.transaction.Transaction;
/*  6:   */ import javax.transaction.TransactionManager;
/*  7:   */ import javax.transaction.UserTransaction;
/*  8:   */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  9:   */ 
/* 10:   */ public class NoJtaPlatform
/* 11:   */   implements JtaPlatform
/* 12:   */ {
/* 13:   */   public TransactionManager retrieveTransactionManager()
/* 14:   */   {
/* 15:43 */     return null;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public UserTransaction retrieveUserTransaction()
/* 19:   */   {
/* 20:48 */     return null;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object getTransactionIdentifier(Transaction transaction)
/* 24:   */   {
/* 25:53 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void registerSynchronization(Synchronization synchronization) {}
/* 29:   */   
/* 30:   */   public boolean canRegisterSynchronization()
/* 31:   */   {
/* 32:62 */     return false;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int getCurrentStatus()
/* 36:   */     throws SystemException
/* 37:   */   {
/* 38:67 */     return 5;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.NoJtaPlatform
 * JD-Core Version:    0.7.0.1
 */