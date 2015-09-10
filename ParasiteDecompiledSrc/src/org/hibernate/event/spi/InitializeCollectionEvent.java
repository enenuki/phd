/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  4:   */ 
/*  5:   */ public class InitializeCollectionEvent
/*  6:   */   extends AbstractCollectionEvent
/*  7:   */ {
/*  8:   */   public InitializeCollectionEvent(PersistentCollection collection, EventSource source)
/*  9:   */   {
/* 10:36 */     super(getLoadedCollectionPersister(collection, source), collection, source, getLoadedOwnerOrNull(collection, source), getLoadedOwnerIdOrNull(collection, source));
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.InitializeCollectionEvent
 * JD-Core Version:    0.7.0.1
 */