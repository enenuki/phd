/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  4:   */ import org.hibernate.persister.collection.CollectionPersister;
/*  5:   */ 
/*  6:   */ public class PostCollectionUpdateEvent
/*  7:   */   extends AbstractCollectionEvent
/*  8:   */ {
/*  9:   */   public PostCollectionUpdateEvent(CollectionPersister collectionPersister, PersistentCollection collection, EventSource source)
/* 10:   */   {
/* 11:39 */     super(collectionPersister, collection, source, getLoadedOwnerOrNull(collection, source), getLoadedOwnerIdOrNull(collection, source));
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostCollectionUpdateEvent
 * JD-Core Version:    0.7.0.1
 */