/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.LockMode;
/*  6:   */ import org.hibernate.TransientObjectException;
/*  7:   */ import org.hibernate.engine.internal.Cascade;
/*  8:   */ import org.hibernate.engine.internal.ForeignKeys;
/*  9:   */ import org.hibernate.engine.spi.CascadingAction;
/* 10:   */ import org.hibernate.engine.spi.EntityEntry;
/* 11:   */ import org.hibernate.engine.spi.PersistenceContext;
/* 12:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 13:   */ import org.hibernate.event.spi.EventSource;
/* 14:   */ import org.hibernate.event.spi.LockEvent;
/* 15:   */ import org.hibernate.event.spi.LockEventListener;
/* 16:   */ import org.hibernate.persister.entity.EntityPersister;
/* 17:   */ 
/* 18:   */ public class DefaultLockEventListener
/* 19:   */   extends AbstractLockUpgradeEventListener
/* 20:   */   implements LockEventListener
/* 21:   */ {
/* 22:   */   public void onLock(LockEvent event)
/* 23:   */     throws HibernateException
/* 24:   */   {
/* 25:56 */     if (event.getObject() == null) {
/* 26:57 */       throw new NullPointerException("attempted to lock null");
/* 27:   */     }
/* 28:60 */     if (event.getLockMode() == LockMode.WRITE) {
/* 29:61 */       throw new HibernateException("Invalid lock mode for lock()");
/* 30:   */     }
/* 31:64 */     SessionImplementor source = event.getSession();
/* 32:   */     
/* 33:66 */     Object entity = source.getPersistenceContext().unproxyAndReassociate(event.getObject());
/* 34:   */     
/* 35:   */ 
/* 36:   */ 
/* 37:70 */     EntityEntry entry = source.getPersistenceContext().getEntry(entity);
/* 38:71 */     if (entry == null)
/* 39:   */     {
/* 40:72 */       EntityPersister persister = source.getEntityPersister(event.getEntityName(), entity);
/* 41:73 */       Serializable id = persister.getIdentifier(entity, source);
/* 42:74 */       if (!ForeignKeys.isNotTransient(event.getEntityName(), entity, Boolean.FALSE, source)) {
/* 43:75 */         throw new TransientObjectException("cannot lock an unsaved transient instance: " + persister.getEntityName());
/* 44:   */       }
/* 45:81 */       entry = reassociate(event, entity, id, persister);
/* 46:82 */       cascadeOnLock(event, persister, entity);
/* 47:   */     }
/* 48:85 */     upgradeLock(entity, entry, event.getLockOptions(), event.getSession());
/* 49:   */   }
/* 50:   */   
/* 51:   */   private void cascadeOnLock(LockEvent event, EntityPersister persister, Object entity)
/* 52:   */   {
/* 53:89 */     EventSource source = event.getSession();
/* 54:90 */     source.getPersistenceContext().incrementCascadeLevel();
/* 55:   */     try
/* 56:   */     {
/* 57:92 */       new Cascade(CascadingAction.LOCK, 0, source).cascade(persister, entity, event.getLockOptions());
/* 58:   */     }
/* 59:   */     finally
/* 60:   */     {
/* 61:96 */       source.getPersistenceContext().decrementCascadeLevel();
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultLockEventListener
 * JD-Core Version:    0.7.0.1
 */