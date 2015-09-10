/*  1:   */ package org.hibernate.action.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.cache.CacheException;
/*  6:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  7:   */ import org.hibernate.engine.spi.CollectionEntry;
/*  8:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  9:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/* 10:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 11:   */ import org.hibernate.event.service.spi.EventListenerGroup;
/* 12:   */ import org.hibernate.event.spi.EventType;
/* 13:   */ import org.hibernate.event.spi.PostCollectionRecreateEvent;
/* 14:   */ import org.hibernate.event.spi.PostCollectionRecreateEventListener;
/* 15:   */ import org.hibernate.event.spi.PreCollectionRecreateEvent;
/* 16:   */ import org.hibernate.event.spi.PreCollectionRecreateEventListener;
/* 17:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 18:   */ import org.hibernate.stat.Statistics;
/* 19:   */ import org.hibernate.stat.spi.StatisticsImplementor;
/* 20:   */ 
/* 21:   */ public final class CollectionRecreateAction
/* 22:   */   extends CollectionAction
/* 23:   */ {
/* 24:   */   public CollectionRecreateAction(PersistentCollection collection, CollectionPersister persister, Serializable id, SessionImplementor session)
/* 25:   */     throws CacheException
/* 26:   */   {
/* 27:47 */     super(persister, collection, id, session);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void execute()
/* 31:   */     throws HibernateException
/* 32:   */   {
/* 33:54 */     PersistentCollection collection = getCollection();
/* 34:   */     
/* 35:56 */     preRecreate();
/* 36:   */     
/* 37:58 */     getPersister().recreate(collection, getKey(), getSession());
/* 38:   */     
/* 39:60 */     getSession().getPersistenceContext().getCollectionEntry(collection).afterAction(collection);
/* 40:   */     
/* 41:   */ 
/* 42:   */ 
/* 43:64 */     evict();
/* 44:   */     
/* 45:66 */     postRecreate();
/* 46:68 */     if (getSession().getFactory().getStatistics().isStatisticsEnabled()) {
/* 47:69 */       getSession().getFactory().getStatisticsImplementor().recreateCollection(getPersister().getRole());
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   private void preRecreate()
/* 52:   */   {
/* 53:75 */     EventListenerGroup<PreCollectionRecreateEventListener> listenerGroup = listenerGroup(EventType.PRE_COLLECTION_RECREATE);
/* 54:76 */     if (listenerGroup.isEmpty()) {
/* 55:77 */       return;
/* 56:   */     }
/* 57:79 */     PreCollectionRecreateEvent event = new PreCollectionRecreateEvent(getPersister(), getCollection(), eventSource());
/* 58:80 */     for (PreCollectionRecreateEventListener listener : listenerGroup.listeners()) {
/* 59:81 */       listener.onPreRecreateCollection(event);
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   private void postRecreate()
/* 64:   */   {
/* 65:86 */     EventListenerGroup<PostCollectionRecreateEventListener> listenerGroup = listenerGroup(EventType.POST_COLLECTION_RECREATE);
/* 66:87 */     if (listenerGroup.isEmpty()) {
/* 67:88 */       return;
/* 68:   */     }
/* 69:90 */     PostCollectionRecreateEvent event = new PostCollectionRecreateEvent(getPersister(), getCollection(), eventSource());
/* 70:91 */     for (PostCollectionRecreateEventListener listener : listenerGroup.listeners()) {
/* 71:92 */       listener.onPostRecreateCollection(event);
/* 72:   */     }
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.CollectionRecreateAction
 * JD-Core Version:    0.7.0.1
 */