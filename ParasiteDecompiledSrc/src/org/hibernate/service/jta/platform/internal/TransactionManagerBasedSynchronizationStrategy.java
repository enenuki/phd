/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import javax.transaction.Synchronization;
/*  4:   */ import javax.transaction.Transaction;
/*  5:   */ import javax.transaction.TransactionManager;
/*  6:   */ import org.hibernate.engine.transaction.internal.jta.JtaStatusHelper;
/*  7:   */ import org.hibernate.service.jta.platform.spi.JtaPlatformException;
/*  8:   */ 
/*  9:   */ public class TransactionManagerBasedSynchronizationStrategy
/* 10:   */   implements JtaSynchronizationStrategy
/* 11:   */ {
/* 12:   */   private final TransactionManagerAccess transactionManagerAccess;
/* 13:   */   
/* 14:   */   public TransactionManagerBasedSynchronizationStrategy(TransactionManagerAccess transactionManagerAccess)
/* 15:   */   {
/* 16:41 */     this.transactionManagerAccess = transactionManagerAccess;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void registerSynchronization(Synchronization synchronization)
/* 20:   */   {
/* 21:   */     try
/* 22:   */     {
/* 23:47 */       this.transactionManagerAccess.getTransactionManager().getTransaction().registerSynchronization(synchronization);
/* 24:   */     }
/* 25:   */     catch (Exception e)
/* 26:   */     {
/* 27:50 */       throw new JtaPlatformException("Could not access JTA Transaction to register synchronization", e);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean canRegisterSynchronization()
/* 32:   */   {
/* 33:56 */     return JtaStatusHelper.isActive(this.transactionManagerAccess.getTransactionManager());
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.TransactionManagerBasedSynchronizationStrategy
 * JD-Core Version:    0.7.0.1
 */