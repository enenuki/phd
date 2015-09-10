/*  1:   */ package org.hibernate.cache.spi.entry;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  4:   */ 
/*  5:   */ public class UnstructuredCacheEntry
/*  6:   */   implements CacheEntryStructure
/*  7:   */ {
/*  8:   */   public Object structure(Object item)
/*  9:   */   {
/* 10:34 */     return item;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Object destructure(Object map, SessionFactoryImplementor factory)
/* 14:   */   {
/* 15:38 */     return map;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.entry.UnstructuredCacheEntry
 * JD-Core Version:    0.7.0.1
 */