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
/* 11:   */ import org.hibernate.type.EntityType;
/* 12:   */ 
/* 13:   */ public abstract class ProxyVisitor
/* 14:   */   extends AbstractVisitor
/* 15:   */ {
/* 16:   */   public ProxyVisitor(EventSource session)
/* 17:   */   {
/* 18:43 */     super(session);
/* 19:   */   }
/* 20:   */   
/* 21:   */   Object processEntity(Object value, EntityType entityType)
/* 22:   */     throws HibernateException
/* 23:   */   {
/* 24:48 */     if (value != null) {
/* 25:49 */       getSession().getPersistenceContext().reassociateIfUninitializedProxy(value);
/* 26:   */     }
/* 27:54 */     return null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected static boolean isOwnerUnchanged(PersistentCollection snapshot, CollectionPersister persister, Serializable id)
/* 31:   */   {
/* 32:66 */     return (isCollectionSnapshotValid(snapshot)) && (persister.getRole().equals(snapshot.getRole())) && (id.equals(snapshot.getKey()));
/* 33:   */   }
/* 34:   */   
/* 35:   */   private static boolean isCollectionSnapshotValid(PersistentCollection snapshot)
/* 36:   */   {
/* 37:72 */     return (snapshot != null) && (snapshot.getRole() != null) && (snapshot.getKey() != null);
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected void reattachCollection(PersistentCollection collection, CollectionType type)
/* 41:   */     throws HibernateException
/* 42:   */   {
/* 43:84 */     if (collection.wasInitialized())
/* 44:   */     {
/* 45:85 */       CollectionPersister collectionPersister = getSession().getFactory().getCollectionPersister(type.getRole());
/* 46:   */       
/* 47:87 */       getSession().getPersistenceContext().addInitializedDetachedCollection(collectionPersister, collection);
/* 48:   */     }
/* 49:   */     else
/* 50:   */     {
/* 51:91 */       if (!isCollectionSnapshotValid(collection)) {
/* 52:92 */         throw new HibernateException("could not reassociate uninitialized transient collection");
/* 53:   */       }
/* 54:94 */       CollectionPersister collectionPersister = getSession().getFactory().getCollectionPersister(collection.getRole());
/* 55:   */       
/* 56:96 */       getSession().getPersistenceContext().addUninitializedDetachedCollection(collectionPersister, collection);
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.ProxyVisitor
 * JD-Core Version:    0.7.0.1
 */