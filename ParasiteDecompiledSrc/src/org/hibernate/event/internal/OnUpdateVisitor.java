/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.event.spi.EventSource;
/*  8:   */ import org.hibernate.persister.collection.CollectionPersister;
/*  9:   */ import org.hibernate.type.CollectionType;
/* 10:   */ 
/* 11:   */ public class OnUpdateVisitor
/* 12:   */   extends ReattachVisitor
/* 13:   */ {
/* 14:   */   OnUpdateVisitor(EventSource session, Serializable key, Object owner)
/* 15:   */   {
/* 16:47 */     super(session, key, owner);
/* 17:   */   }
/* 18:   */   
/* 19:   */   Object processCollection(Object collection, CollectionType type)
/* 20:   */     throws HibernateException
/* 21:   */   {
/* 22:55 */     if (collection == CollectionType.UNFETCHED_COLLECTION) {
/* 23:56 */       return null;
/* 24:   */     }
/* 25:59 */     EventSource session = getSession();
/* 26:60 */     CollectionPersister persister = session.getFactory().getCollectionPersister(type.getRole());
/* 27:   */     
/* 28:62 */     Serializable collectionKey = extractCollectionKeyFromOwner(persister);
/* 29:63 */     if ((collection != null) && ((collection instanceof PersistentCollection)))
/* 30:   */     {
/* 31:64 */       PersistentCollection wrapper = (PersistentCollection)collection;
/* 32:65 */       if (wrapper.setCurrentSession(session))
/* 33:   */       {
/* 34:67 */         if (!isOwnerUnchanged(wrapper, persister, collectionKey)) {
/* 35:70 */           removeCollection(persister, collectionKey, session);
/* 36:   */         }
/* 37:72 */         reattachCollection(wrapper, type);
/* 38:   */       }
/* 39:   */       else
/* 40:   */       {
/* 41:78 */         removeCollection(persister, collectionKey, session);
/* 42:   */       }
/* 43:   */     }
/* 44:   */     else
/* 45:   */     {
/* 46:85 */       removeCollection(persister, collectionKey, session);
/* 47:   */     }
/* 48:88 */     return null;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.OnUpdateVisitor
 * JD-Core Version:    0.7.0.1
 */