/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.ObjectDeletedException;
/*  6:   */ import org.hibernate.engine.spi.EntityEntry;
/*  7:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  8:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  9:   */ import org.hibernate.engine.spi.Status;
/* 10:   */ import org.hibernate.event.spi.EventSource;
/* 11:   */ import org.hibernate.event.spi.SaveOrUpdateEvent;
/* 12:   */ import org.hibernate.persister.entity.EntityPersister;
/* 13:   */ 
/* 14:   */ public class DefaultUpdateEventListener
/* 15:   */   extends DefaultSaveOrUpdateEventListener
/* 16:   */ {
/* 17:   */   protected Serializable performSaveOrUpdate(SaveOrUpdateEvent event)
/* 18:   */   {
/* 19:45 */     EntityEntry entry = event.getSession().getPersistenceContext().getEntry(event.getEntity());
/* 20:46 */     if (entry != null)
/* 21:   */     {
/* 22:47 */       if (entry.getStatus() == Status.DELETED) {
/* 23:48 */         throw new ObjectDeletedException("deleted instance passed to update()", null, event.getEntityName());
/* 24:   */       }
/* 25:51 */       return entityIsPersistent(event);
/* 26:   */     }
/* 27:55 */     entityIsDetached(event);
/* 28:56 */     return null;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected Serializable getUpdateId(Object entity, EntityPersister persister, Serializable requestedId, SessionImplementor session)
/* 32:   */     throws HibernateException
/* 33:   */   {
/* 34:69 */     if (requestedId == null) {
/* 35:70 */       return super.getUpdateId(entity, persister, requestedId, session);
/* 36:   */     }
/* 37:73 */     persister.setIdentifier(entity, requestedId, session);
/* 38:74 */     return requestedId;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultUpdateEventListener
 * JD-Core Version:    0.7.0.1
 */