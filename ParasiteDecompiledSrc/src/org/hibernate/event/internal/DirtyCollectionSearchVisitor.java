/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  5:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.event.spi.EventSource;
/*  8:   */ import org.hibernate.type.CollectionType;
/*  9:   */ 
/* 10:   */ public class DirtyCollectionSearchVisitor
/* 11:   */   extends AbstractVisitor
/* 12:   */ {
/* 13:42 */   private boolean dirty = false;
/* 14:   */   private boolean[] propertyVersionability;
/* 15:   */   
/* 16:   */   DirtyCollectionSearchVisitor(EventSource session, boolean[] propertyVersionability)
/* 17:   */   {
/* 18:46 */     super(session);
/* 19:47 */     this.propertyVersionability = propertyVersionability;
/* 20:   */   }
/* 21:   */   
/* 22:   */   boolean wasDirtyCollectionFound()
/* 23:   */   {
/* 24:51 */     return this.dirty;
/* 25:   */   }
/* 26:   */   
/* 27:   */   Object processCollection(Object collection, CollectionType type)
/* 28:   */     throws HibernateException
/* 29:   */   {
/* 30:57 */     if (collection != null)
/* 31:   */     {
/* 32:59 */       SessionImplementor session = getSession();
/* 33:   */       PersistentCollection persistentCollection;
/* 34:   */       PersistentCollection persistentCollection;
/* 35:62 */       if (type.isArrayType()) {
/* 36:63 */         persistentCollection = session.getPersistenceContext().getCollectionHolder(collection);
/* 37:   */       } else {
/* 38:73 */         persistentCollection = (PersistentCollection)collection;
/* 39:   */       }
/* 40:76 */       if (persistentCollection.isDirty())
/* 41:   */       {
/* 42:77 */         this.dirty = true;
/* 43:78 */         return null;
/* 44:   */       }
/* 45:   */     }
/* 46:82 */     return null;
/* 47:   */   }
/* 48:   */   
/* 49:   */   boolean includeEntityProperty(Object[] values, int i)
/* 50:   */   {
/* 51:86 */     return (this.propertyVersionability[i] != 0) && (super.includeEntityProperty(values, i));
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DirtyCollectionSearchVisitor
 * JD-Core Version:    0.7.0.1
 */