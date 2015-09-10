/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.AssertionFailure;
/*  4:   */ import org.hibernate.LockMode;
/*  5:   */ import org.hibernate.action.internal.EntityIncrementVersionProcess;
/*  6:   */ import org.hibernate.action.internal.EntityVerifyVersionProcess;
/*  7:   */ import org.hibernate.classic.Lifecycle;
/*  8:   */ import org.hibernate.engine.spi.ActionQueue;
/*  9:   */ import org.hibernate.engine.spi.EntityEntry;
/* 10:   */ import org.hibernate.engine.spi.PersistenceContext;
/* 11:   */ import org.hibernate.event.spi.EventSource;
/* 12:   */ import org.hibernate.event.spi.PostLoadEvent;
/* 13:   */ import org.hibernate.event.spi.PostLoadEventListener;
/* 14:   */ import org.hibernate.persister.entity.EntityPersister;
/* 15:   */ 
/* 16:   */ public class DefaultPostLoadEventListener
/* 17:   */   implements PostLoadEventListener
/* 18:   */ {
/* 19:   */   public void onPostLoad(PostLoadEvent event)
/* 20:   */   {
/* 21:48 */     Object entity = event.getEntity();
/* 22:49 */     EntityEntry entry = event.getSession().getPersistenceContext().getEntry(entity);
/* 23:50 */     if (entry == null) {
/* 24:51 */       throw new AssertionFailure("possible non-threadsafe access to the session");
/* 25:   */     }
/* 26:54 */     LockMode lockMode = entry.getLockMode();
/* 27:55 */     if (LockMode.PESSIMISTIC_FORCE_INCREMENT.equals(lockMode))
/* 28:   */     {
/* 29:56 */       EntityPersister persister = entry.getPersister();
/* 30:57 */       Object nextVersion = persister.forceVersionIncrement(entry.getId(), entry.getVersion(), event.getSession());
/* 31:   */       
/* 32:   */ 
/* 33:60 */       entry.forceLocked(entity, nextVersion);
/* 34:   */     }
/* 35:62 */     else if (LockMode.OPTIMISTIC_FORCE_INCREMENT.equals(lockMode))
/* 36:   */     {
/* 37:63 */       EntityIncrementVersionProcess incrementVersion = new EntityIncrementVersionProcess(entity, entry);
/* 38:64 */       event.getSession().getActionQueue().registerProcess(incrementVersion);
/* 39:   */     }
/* 40:66 */     else if (LockMode.OPTIMISTIC.equals(lockMode))
/* 41:   */     {
/* 42:67 */       EntityVerifyVersionProcess verifyVersion = new EntityVerifyVersionProcess(entity, entry);
/* 43:68 */       event.getSession().getActionQueue().registerProcess(verifyVersion);
/* 44:   */     }
/* 45:71 */     if (event.getPersister().implementsLifecycle()) {
/* 46:73 */       ((Lifecycle)event.getEntity()).onLoad(event.getSession(), event.getId());
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultPostLoadEventListener
 * JD-Core Version:    0.7.0.1
 */