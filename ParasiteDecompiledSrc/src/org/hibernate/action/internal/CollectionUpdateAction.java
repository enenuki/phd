/*   1:    */ package org.hibernate.action.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.collection.spi.PersistentCollection;
/*   7:    */ import org.hibernate.engine.spi.CollectionEntry;
/*   8:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  11:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  12:    */ import org.hibernate.event.spi.EventType;
/*  13:    */ import org.hibernate.event.spi.PostCollectionUpdateEvent;
/*  14:    */ import org.hibernate.event.spi.PostCollectionUpdateEventListener;
/*  15:    */ import org.hibernate.event.spi.PreCollectionUpdateEvent;
/*  16:    */ import org.hibernate.event.spi.PreCollectionUpdateEventListener;
/*  17:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  18:    */ import org.hibernate.pretty.MessageHelper;
/*  19:    */ import org.hibernate.stat.Statistics;
/*  20:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  21:    */ 
/*  22:    */ public final class CollectionUpdateAction
/*  23:    */   extends CollectionAction
/*  24:    */ {
/*  25:    */   private final boolean emptySnapshot;
/*  26:    */   
/*  27:    */   public CollectionUpdateAction(PersistentCollection collection, CollectionPersister persister, Serializable id, boolean emptySnapshot, SessionImplementor session)
/*  28:    */   {
/*  29: 51 */     super(persister, collection, id, session);
/*  30: 52 */     this.emptySnapshot = emptySnapshot;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void execute()
/*  34:    */     throws HibernateException
/*  35:    */   {
/*  36: 57 */     Serializable id = getKey();
/*  37: 58 */     SessionImplementor session = getSession();
/*  38: 59 */     CollectionPersister persister = getPersister();
/*  39: 60 */     PersistentCollection collection = getCollection();
/*  40: 61 */     boolean affectedByFilters = persister.isAffectedByEnabledFilters(session);
/*  41:    */     
/*  42: 63 */     preUpdate();
/*  43: 65 */     if (!collection.wasInitialized())
/*  44:    */     {
/*  45: 66 */       if (!collection.hasQueuedOperations()) {
/*  46: 66 */         throw new AssertionFailure("no queued adds");
/*  47:    */       }
/*  48:    */     }
/*  49: 69 */     else if ((!affectedByFilters) && (collection.empty()))
/*  50:    */     {
/*  51: 70 */       if (!this.emptySnapshot) {
/*  52: 70 */         persister.remove(id, session);
/*  53:    */       }
/*  54:    */     }
/*  55: 72 */     else if (collection.needsRecreate(persister))
/*  56:    */     {
/*  57: 73 */       if (affectedByFilters) {
/*  58: 74 */         throw new HibernateException("cannot recreate collection while filter is enabled: " + MessageHelper.collectionInfoString(persister, id, persister.getFactory()));
/*  59:    */       }
/*  60: 79 */       if (!this.emptySnapshot) {
/*  61: 79 */         persister.remove(id, session);
/*  62:    */       }
/*  63: 80 */       persister.recreate(collection, id, session);
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67: 83 */       persister.deleteRows(collection, id, session);
/*  68: 84 */       persister.updateRows(collection, id, session);
/*  69: 85 */       persister.insertRows(collection, id, session);
/*  70:    */     }
/*  71: 88 */     getSession().getPersistenceContext().getCollectionEntry(collection).afterAction(collection);
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75: 92 */     evict();
/*  76:    */     
/*  77: 94 */     postUpdate();
/*  78: 96 */     if (getSession().getFactory().getStatistics().isStatisticsEnabled()) {
/*  79: 97 */       getSession().getFactory().getStatisticsImplementor().updateCollection(getPersister().getRole());
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void preUpdate()
/*  84:    */   {
/*  85:103 */     EventListenerGroup<PreCollectionUpdateEventListener> listenerGroup = listenerGroup(EventType.PRE_COLLECTION_UPDATE);
/*  86:104 */     if (listenerGroup.isEmpty()) {
/*  87:105 */       return;
/*  88:    */     }
/*  89:107 */     PreCollectionUpdateEvent event = new PreCollectionUpdateEvent(getPersister(), getCollection(), eventSource());
/*  90:112 */     for (PreCollectionUpdateEventListener listener : listenerGroup.listeners()) {
/*  91:113 */       listener.onPreUpdateCollection(event);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void postUpdate()
/*  96:    */   {
/*  97:118 */     EventListenerGroup<PostCollectionUpdateEventListener> listenerGroup = listenerGroup(EventType.POST_COLLECTION_UPDATE);
/*  98:119 */     if (listenerGroup.isEmpty()) {
/*  99:120 */       return;
/* 100:    */     }
/* 101:122 */     PostCollectionUpdateEvent event = new PostCollectionUpdateEvent(getPersister(), getCollection(), eventSource());
/* 102:127 */     for (PostCollectionUpdateEventListener listener : listenerGroup.listeners()) {
/* 103:128 */       listener.onPostUpdateCollection(event);
/* 104:    */     }
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.CollectionUpdateAction
 * JD-Core Version:    0.7.0.1
 */