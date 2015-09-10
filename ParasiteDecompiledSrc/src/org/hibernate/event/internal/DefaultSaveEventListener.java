/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.Hibernate;
/*  5:   */ import org.hibernate.PersistentObjectException;
/*  6:   */ import org.hibernate.engine.spi.EntityEntry;
/*  7:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  8:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  9:   */ import org.hibernate.engine.spi.Status;
/* 10:   */ import org.hibernate.event.spi.EventSource;
/* 11:   */ import org.hibernate.event.spi.SaveOrUpdateEvent;
/* 12:   */ 
/* 13:   */ public class DefaultSaveEventListener
/* 14:   */   extends DefaultSaveOrUpdateEventListener
/* 15:   */ {
/* 16:   */   protected Serializable performSaveOrUpdate(SaveOrUpdateEvent event)
/* 17:   */   {
/* 18:44 */     EntityEntry entry = event.getSession().getPersistenceContext().getEntry(event.getEntity());
/* 19:45 */     if ((entry != null) && (entry.getStatus() != Status.DELETED)) {
/* 20:46 */       return entityIsPersistent(event);
/* 21:   */     }
/* 22:49 */     return entityIsTransient(event);
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected Serializable saveWithGeneratedOrRequestedId(SaveOrUpdateEvent event)
/* 26:   */   {
/* 27:54 */     if (event.getRequestedId() == null) {
/* 28:55 */       return super.saveWithGeneratedOrRequestedId(event);
/* 29:   */     }
/* 30:58 */     return saveWithRequestedId(event.getEntity(), event.getRequestedId(), event.getEntityName(), null, event.getSession());
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected boolean reassociateIfUninitializedProxy(Object object, SessionImplementor source)
/* 34:   */   {
/* 35:70 */     if (!Hibernate.isInitialized(object)) {
/* 36:71 */       throw new PersistentObjectException("uninitialized proxy passed to save()");
/* 37:   */     }
/* 38:74 */     return false;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultSaveEventListener
 * JD-Core Version:    0.7.0.1
 */