/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import javax.transaction.Synchronization;
/*  4:   */ import javax.transaction.TransactionSynchronizationRegistry;
/*  5:   */ import org.hibernate.engine.transaction.internal.jta.JtaStatusHelper;
/*  6:   */ 
/*  7:   */ public class SynchronizationRegistryBasedSynchronizationStrategy
/*  8:   */   implements JtaSynchronizationStrategy
/*  9:   */ {
/* 10:   */   private final SynchronizationRegistryAccess synchronizationRegistryAccess;
/* 11:   */   
/* 12:   */   public SynchronizationRegistryBasedSynchronizationStrategy(SynchronizationRegistryAccess synchronizationRegistryAccess)
/* 13:   */   {
/* 14:41 */     this.synchronizationRegistryAccess = synchronizationRegistryAccess;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void registerSynchronization(Synchronization synchronization)
/* 18:   */   {
/* 19:46 */     this.synchronizationRegistryAccess.getSynchronizationRegistry().registerInterposedSynchronization(synchronization);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean canRegisterSynchronization()
/* 23:   */   {
/* 24:53 */     TransactionSynchronizationRegistry registry = this.synchronizationRegistryAccess.getSynchronizationRegistry();
/* 25:54 */     return (JtaStatusHelper.isActive(registry.getTransactionStatus())) && (!registry.getRollbackOnly());
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy
 * JD-Core Version:    0.7.0.1
 */