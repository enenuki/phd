/*  1:   */ package org.hibernate.engine.transaction.synchronization.internal;
/*  2:   */ 
/*  3:   */ import javax.transaction.Synchronization;
/*  4:   */ import org.hibernate.engine.transaction.synchronization.spi.SynchronizationCallbackCoordinator;
/*  5:   */ import org.hibernate.internal.CoreMessageLogger;
/*  6:   */ import org.jboss.logging.Logger;
/*  7:   */ 
/*  8:   */ public class RegisteredSynchronization
/*  9:   */   implements Synchronization
/* 10:   */ {
/* 11:40 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, RegisteredSynchronization.class.getName());
/* 12:   */   private final SynchronizationCallbackCoordinator synchronizationCallbackCoordinator;
/* 13:   */   
/* 14:   */   public RegisteredSynchronization(SynchronizationCallbackCoordinator synchronizationCallbackCoordinator)
/* 15:   */   {
/* 16:45 */     this.synchronizationCallbackCoordinator = synchronizationCallbackCoordinator;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void beforeCompletion()
/* 20:   */   {
/* 21:52 */     LOG.trace("JTA sync : beforeCompletion()");
/* 22:53 */     this.synchronizationCallbackCoordinator.beforeCompletion();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void afterCompletion(int status)
/* 26:   */   {
/* 27:60 */     LOG.tracef("JTA sync : afterCompletion(%s)", Integer.valueOf(status));
/* 28:61 */     this.synchronizationCallbackCoordinator.afterCompletion(status);
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.synchronization.internal.RegisteredSynchronization
 * JD-Core Version:    0.7.0.1
 */