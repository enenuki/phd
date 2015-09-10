/*  1:   */ package org.hibernate.engine.transaction.internal;
/*  2:   */ 
/*  3:   */ import java.util.LinkedHashSet;
/*  4:   */ import javax.transaction.Synchronization;
/*  5:   */ import org.hibernate.engine.transaction.spi.SynchronizationRegistry;
/*  6:   */ import org.hibernate.internal.CoreMessageLogger;
/*  7:   */ import org.jboss.logging.Logger;
/*  8:   */ 
/*  9:   */ public class SynchronizationRegistryImpl
/* 10:   */   implements SynchronizationRegistry
/* 11:   */ {
/* 12:41 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SynchronizationRegistryImpl.class.getName());
/* 13:   */   private LinkedHashSet<Synchronization> synchronizations;
/* 14:   */   
/* 15:   */   public void registerSynchronization(Synchronization synchronization)
/* 16:   */   {
/* 17:47 */     if (synchronization == null) {
/* 18:48 */       throw new NullSynchronizationException();
/* 19:   */     }
/* 20:51 */     if (this.synchronizations == null) {
/* 21:52 */       this.synchronizations = new LinkedHashSet();
/* 22:   */     }
/* 23:55 */     boolean added = this.synchronizations.add(synchronization);
/* 24:56 */     if (!added) {
/* 25:57 */       LOG.synchronizationAlreadyRegistered(synchronization);
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void notifySynchronizationsBeforeTransactionCompletion()
/* 30:   */   {
/* 31:63 */     if (this.synchronizations != null) {
/* 32:64 */       for (Synchronization synchronization : this.synchronizations) {
/* 33:   */         try
/* 34:   */         {
/* 35:66 */           synchronization.beforeCompletion();
/* 36:   */         }
/* 37:   */         catch (Throwable t)
/* 38:   */         {
/* 39:69 */           LOG.synchronizationFailed(synchronization, t);
/* 40:   */         }
/* 41:   */       }
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void notifySynchronizationsAfterTransactionCompletion(int status)
/* 46:   */   {
/* 47:77 */     if (this.synchronizations != null) {
/* 48:78 */       for (Synchronization synchronization : this.synchronizations) {
/* 49:   */         try
/* 50:   */         {
/* 51:80 */           synchronization.afterCompletion(status);
/* 52:   */         }
/* 53:   */         catch (Throwable t)
/* 54:   */         {
/* 55:83 */           LOG.synchronizationFailed(synchronization, t);
/* 56:   */         }
/* 57:   */       }
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   void clearSynchronizations()
/* 62:   */   {
/* 63:93 */     if (this.synchronizations != null)
/* 64:   */     {
/* 65:94 */       this.synchronizations.clear();
/* 66:95 */       this.synchronizations = null;
/* 67:   */     }
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.SynchronizationRegistryImpl
 * JD-Core Version:    0.7.0.1
 */