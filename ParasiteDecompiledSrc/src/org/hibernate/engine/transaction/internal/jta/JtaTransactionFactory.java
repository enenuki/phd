/*  1:   */ package org.hibernate.engine.transaction.internal.jta;
/*  2:   */ 
/*  3:   */ import javax.transaction.SystemException;
/*  4:   */ import javax.transaction.TransactionManager;
/*  5:   */ import javax.transaction.UserTransaction;
/*  6:   */ import org.hibernate.ConnectionReleaseMode;
/*  7:   */ import org.hibernate.TransactionException;
/*  8:   */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  9:   */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/* 10:   */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/* 11:   */ import org.hibernate.engine.transaction.spi.TransactionFactory;
/* 12:   */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/* 13:   */ 
/* 14:   */ public class JtaTransactionFactory
/* 15:   */   implements TransactionFactory<JtaTransaction>
/* 16:   */ {
/* 17:   */   public JtaTransaction createTransaction(TransactionCoordinator transactionCoordinator)
/* 18:   */   {
/* 19:45 */     return new JtaTransaction(transactionCoordinator);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean canBeDriver()
/* 23:   */   {
/* 24:50 */     return true;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public ConnectionReleaseMode getDefaultReleaseMode()
/* 28:   */   {
/* 29:55 */     return ConnectionReleaseMode.AFTER_STATEMENT;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean compatibleWithJtaSynchronization()
/* 33:   */   {
/* 34:60 */     return true;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean isJoinableJtaTransaction(TransactionCoordinator transactionCoordinator, JtaTransaction transaction)
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:75 */       if (transaction != null)
/* 42:   */       {
/* 43:76 */         UserTransaction ut = transaction.getUserTransaction();
/* 44:77 */         if (ut != null) {
/* 45:78 */           return JtaStatusHelper.isActive(ut);
/* 46:   */         }
/* 47:   */       }
/* 48:82 */       JtaPlatform jtaPlatform = transactionCoordinator.getTransactionContext().getTransactionEnvironment().getJtaPlatform();
/* 49:86 */       if (jtaPlatform == null) {
/* 50:87 */         throw new TransactionException("Unable to check transaction status");
/* 51:   */       }
/* 52:89 */       if (jtaPlatform.retrieveTransactionManager() != null) {
/* 53:90 */         return JtaStatusHelper.isActive(jtaPlatform.retrieveTransactionManager().getStatus());
/* 54:   */       }
/* 55:93 */       UserTransaction ut = jtaPlatform.retrieveUserTransaction();
/* 56:94 */       return (ut != null) && (JtaStatusHelper.isActive(ut));
/* 57:   */     }
/* 58:   */     catch (SystemException se)
/* 59:   */     {
/* 60:98 */       throw new TransactionException("Unable to check transaction status", se);
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jta.JtaTransactionFactory
 * JD-Core Version:    0.7.0.1
 */