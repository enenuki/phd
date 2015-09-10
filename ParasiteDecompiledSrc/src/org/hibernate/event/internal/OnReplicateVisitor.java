/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  6:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  7:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  8:   */ import org.hibernate.event.spi.EventSource;
/*  9:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 10:   */ import org.hibernate.type.CollectionType;
/* 11:   */ 
/* 12:   */ public class OnReplicateVisitor
/* 13:   */   extends ReattachVisitor
/* 14:   */ {
/* 15:   */   private boolean isUpdate;
/* 16:   */   
/* 17:   */   OnReplicateVisitor(EventSource session, Serializable key, Object owner, boolean isUpdate)
/* 18:   */   {
/* 19:50 */     super(session, key, owner);
/* 20:51 */     this.isUpdate = isUpdate;
/* 21:   */   }
/* 22:   */   
/* 23:   */   Object processCollection(Object collection, CollectionType type)
/* 24:   */     throws HibernateException
/* 25:   */   {
/* 26:57 */     if (collection == CollectionType.UNFETCHED_COLLECTION) {
/* 27:58 */       return null;
/* 28:   */     }
/* 29:61 */     EventSource session = getSession();
/* 30:62 */     CollectionPersister persister = session.getFactory().getCollectionPersister(type.getRole());
/* 31:64 */     if (this.isUpdate) {
/* 32:65 */       removeCollection(persister, extractCollectionKeyFromOwner(persister), session);
/* 33:   */     }
/* 34:67 */     if ((collection != null) && ((collection instanceof PersistentCollection)))
/* 35:   */     {
/* 36:68 */       PersistentCollection wrapper = (PersistentCollection)collection;
/* 37:69 */       wrapper.setCurrentSession(session);
/* 38:70 */       if (wrapper.wasInitialized()) {
/* 39:71 */         session.getPersistenceContext().addNewCollection(persister, wrapper);
/* 40:   */       } else {
/* 41:74 */         reattachCollection(wrapper, type);
/* 42:   */       }
/* 43:   */     }
/* 44:84 */     return null;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.OnReplicateVisitor
 * JD-Core Version:    0.7.0.1
 */