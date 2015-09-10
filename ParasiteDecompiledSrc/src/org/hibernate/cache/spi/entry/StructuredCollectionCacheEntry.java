/*  1:   */ package org.hibernate.cache.spi.entry;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Arrays;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ 
/*  8:   */ public class StructuredCollectionCacheEntry
/*  9:   */   implements CacheEntryStructure
/* 10:   */ {
/* 11:   */   public Object structure(Object item)
/* 12:   */   {
/* 13:38 */     CollectionCacheEntry entry = (CollectionCacheEntry)item;
/* 14:39 */     return Arrays.asList(entry.getState());
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Object destructure(Object item, SessionFactoryImplementor factory)
/* 18:   */   {
/* 19:43 */     List list = (List)item;
/* 20:44 */     return new CollectionCacheEntry(list.toArray(new Serializable[list.size()]));
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.entry.StructuredCollectionCacheEntry
 * JD-Core Version:    0.7.0.1
 */