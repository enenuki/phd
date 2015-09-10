/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  5:   */ import org.hibernate.engine.internal.Collections;
/*  6:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  7:   */ import org.hibernate.event.spi.EventSource;
/*  8:   */ import org.hibernate.type.CollectionType;
/*  9:   */ 
/* 10:   */ public class FlushVisitor
/* 11:   */   extends AbstractVisitor
/* 12:   */ {
/* 13:   */   private Object owner;
/* 14:   */   
/* 15:   */   Object processCollection(Object collection, CollectionType type)
/* 16:   */     throws HibernateException
/* 17:   */   {
/* 18:46 */     if (collection == CollectionType.UNFETCHED_COLLECTION) {
/* 19:47 */       return null;
/* 20:   */     }
/* 21:50 */     if (collection != null)
/* 22:   */     {
/* 23:   */       PersistentCollection coll;
/* 24:   */       PersistentCollection coll;
/* 25:52 */       if (type.hasHolder()) {
/* 26:53 */         coll = getSession().getPersistenceContext().getCollectionHolder(collection);
/* 27:   */       } else {
/* 28:56 */         coll = (PersistentCollection)collection;
/* 29:   */       }
/* 30:59 */       Collections.processReachableCollection(coll, type, this.owner, getSession());
/* 31:   */     }
/* 32:62 */     return null;
/* 33:   */   }
/* 34:   */   
/* 35:   */   FlushVisitor(EventSource session, Object owner)
/* 36:   */   {
/* 37:67 */     super(session);
/* 38:68 */     this.owner = owner;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.FlushVisitor
 * JD-Core Version:    0.7.0.1
 */