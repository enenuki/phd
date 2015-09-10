/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  4:   */ import org.hibernate.persister.collection.CollectionPersister;
/*  5:   */ 
/*  6:   */ public class PreCollectionRemoveEvent
/*  7:   */   extends AbstractCollectionEvent
/*  8:   */ {
/*  9:   */   public PreCollectionRemoveEvent(CollectionPersister collectionPersister, PersistentCollection collection, EventSource source, Object loadedOwner)
/* 10:   */   {
/* 11:40 */     super(collectionPersister, collection, source, loadedOwner, getOwnerIdOrNull(loadedOwner, source));
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreCollectionRemoveEvent
 * JD-Core Version:    0.7.0.1
 */