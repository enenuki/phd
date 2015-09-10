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
/*  13:    */ import org.hibernate.event.spi.PostCollectionRemoveEvent;
/*  14:    */ import org.hibernate.event.spi.PostCollectionRemoveEventListener;
/*  15:    */ import org.hibernate.event.spi.PreCollectionRemoveEvent;
/*  16:    */ import org.hibernate.event.spi.PreCollectionRemoveEventListener;
/*  17:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  18:    */ import org.hibernate.stat.Statistics;
/*  19:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  20:    */ 
/*  21:    */ public final class CollectionRemoveAction
/*  22:    */   extends CollectionAction
/*  23:    */ {
/*  24:    */   private boolean emptySnapshot;
/*  25:    */   private final Object affectedOwner;
/*  26:    */   
/*  27:    */   public CollectionRemoveAction(PersistentCollection collection, CollectionPersister persister, Serializable id, boolean emptySnapshot, SessionImplementor session)
/*  28:    */   {
/*  29: 64 */     super(persister, collection, id, session);
/*  30: 65 */     if (collection == null) {
/*  31: 66 */       throw new AssertionFailure("collection == null");
/*  32:    */     }
/*  33: 68 */     this.emptySnapshot = emptySnapshot;
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37: 72 */     this.affectedOwner = session.getPersistenceContext().getLoadedCollectionOwnerOrNull(collection);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public CollectionRemoveAction(Object affectedOwner, CollectionPersister persister, Serializable id, boolean emptySnapshot, SessionImplementor session)
/*  41:    */   {
/*  42: 94 */     super(persister, null, id, session);
/*  43: 95 */     if (affectedOwner == null) {
/*  44: 96 */       throw new AssertionFailure("affectedOwner == null");
/*  45:    */     }
/*  46: 98 */     this.emptySnapshot = emptySnapshot;
/*  47: 99 */     this.affectedOwner = affectedOwner;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void execute()
/*  51:    */     throws HibernateException
/*  52:    */   {
/*  53:104 */     preRemove();
/*  54:106 */     if (!this.emptySnapshot) {
/*  55:111 */       getPersister().remove(getKey(), getSession());
/*  56:    */     }
/*  57:114 */     PersistentCollection collection = getCollection();
/*  58:115 */     if (collection != null) {
/*  59:116 */       getSession().getPersistenceContext().getCollectionEntry(collection).afterAction(collection);
/*  60:    */     }
/*  61:121 */     evict();
/*  62:    */     
/*  63:123 */     postRemove();
/*  64:125 */     if (getSession().getFactory().getStatistics().isStatisticsEnabled()) {
/*  65:126 */       getSession().getFactory().getStatisticsImplementor().removeCollection(getPersister().getRole());
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void preRemove()
/*  70:    */   {
/*  71:132 */     EventListenerGroup<PreCollectionRemoveEventListener> listenerGroup = listenerGroup(EventType.PRE_COLLECTION_REMOVE);
/*  72:133 */     if (listenerGroup.isEmpty()) {
/*  73:134 */       return;
/*  74:    */     }
/*  75:136 */     PreCollectionRemoveEvent event = new PreCollectionRemoveEvent(getPersister(), getCollection(), eventSource(), this.affectedOwner);
/*  76:142 */     for (PreCollectionRemoveEventListener listener : listenerGroup.listeners()) {
/*  77:143 */       listener.onPreRemoveCollection(event);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void postRemove()
/*  82:    */   {
/*  83:148 */     EventListenerGroup<PostCollectionRemoveEventListener> listenerGroup = listenerGroup(EventType.POST_COLLECTION_REMOVE);
/*  84:149 */     if (listenerGroup.isEmpty()) {
/*  85:150 */       return;
/*  86:    */     }
/*  87:152 */     PostCollectionRemoveEvent event = new PostCollectionRemoveEvent(getPersister(), getCollection(), eventSource(), this.affectedOwner);
/*  88:158 */     for (PostCollectionRemoveEventListener listener : listenerGroup.listeners()) {
/*  89:159 */       listener.onPostRemoveCollection(event);
/*  90:    */     }
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.CollectionRemoveAction
 * JD-Core Version:    0.7.0.1
 */