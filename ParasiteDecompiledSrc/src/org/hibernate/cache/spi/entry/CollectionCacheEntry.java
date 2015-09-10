/*  1:   */ package org.hibernate.cache.spi.entry;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  5:   */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  6:   */ import org.hibernate.persister.collection.CollectionPersister;
/*  7:   */ 
/*  8:   */ public class CollectionCacheEntry
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private final Serializable state;
/* 12:   */   
/* 13:   */   public Serializable[] getState()
/* 14:   */   {
/* 15:41 */     return (Serializable[])this.state;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CollectionCacheEntry(PersistentCollection collection, CollectionPersister persister)
/* 19:   */   {
/* 20:45 */     this.state = collection.disassemble(persister);
/* 21:   */   }
/* 22:   */   
/* 23:   */   CollectionCacheEntry(Serializable state)
/* 24:   */   {
/* 25:49 */     this.state = state;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void assemble(PersistentCollection collection, CollectionPersister persister, Object owner)
/* 29:   */   {
/* 30:57 */     collection.initializeFromCache(persister, this.state, owner);
/* 31:58 */     collection.afterInitialize();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toString()
/* 35:   */   {
/* 36:62 */     return "CollectionCacheEntry" + ArrayHelper.toString(getState());
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.entry.CollectionCacheEntry
 * JD-Core Version:    0.7.0.1
 */