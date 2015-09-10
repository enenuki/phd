/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.event.spi.EventSource;
/*  9:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 10:   */ import org.hibernate.type.CollectionType;
/* 11:   */ 
/* 12:   */ public class OnLockVisitor
/* 13:   */   extends ReattachVisitor
/* 14:   */ {
/* 15:   */   public OnLockVisitor(EventSource session, Serializable key, Object owner)
/* 16:   */   {
/* 17:47 */     super(session, key, owner);
/* 18:   */   }
/* 19:   */   
/* 20:   */   Object processCollection(Object collection, CollectionType type)
/* 21:   */     throws HibernateException
/* 22:   */   {
/* 23:52 */     SessionImplementor session = getSession();
/* 24:53 */     CollectionPersister persister = session.getFactory().getCollectionPersister(type.getRole());
/* 25:55 */     if (collection != null) {
/* 26:58 */       if ((collection instanceof PersistentCollection))
/* 27:   */       {
/* 28:59 */         PersistentCollection persistentCollection = (PersistentCollection)collection;
/* 29:60 */         if (persistentCollection.setCurrentSession(session))
/* 30:   */         {
/* 31:61 */           if (isOwnerUnchanged(persistentCollection, persister, extractCollectionKeyFromOwner(persister)))
/* 32:   */           {
/* 33:63 */             if (persistentCollection.isDirty()) {
/* 34:64 */               throw new HibernateException("reassociated object has dirty collection");
/* 35:   */             }
/* 36:66 */             reattachCollection(persistentCollection, type);
/* 37:   */           }
/* 38:   */           else
/* 39:   */           {
/* 40:70 */             throw new HibernateException("reassociated object has dirty collection reference");
/* 41:   */           }
/* 42:   */         }
/* 43:   */         else {
/* 44:77 */           throw new HibernateException("reassociated object has dirty collection reference");
/* 45:   */         }
/* 46:   */       }
/* 47:   */       else
/* 48:   */       {
/* 49:83 */         throw new HibernateException("reassociated object has dirty collection reference (or an array)");
/* 50:   */       }
/* 51:   */     }
/* 52:86 */     return null;
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.OnLockVisitor
 * JD-Core Version:    0.7.0.1
 */