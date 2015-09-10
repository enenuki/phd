/*  1:   */ package org.hibernate.cache.spi.entry;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.Map.Entry;
/*  8:   */ import java.util.Set;
/*  9:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/* 10:   */ 
/* 11:   */ public class StructuredMapCacheEntry
/* 12:   */   implements CacheEntryStructure
/* 13:   */ {
/* 14:   */   public Object structure(Object item)
/* 15:   */   {
/* 16:39 */     CollectionCacheEntry entry = (CollectionCacheEntry)item;
/* 17:40 */     Serializable[] state = entry.getState();
/* 18:41 */     Map map = new HashMap(state.length);
/* 19:42 */     for (int i = 0; i < state.length;) {
/* 20:43 */       map.put(state[(i++)], state[(i++)]);
/* 21:   */     }
/* 22:45 */     return map;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Object destructure(Object item, SessionFactoryImplementor factory)
/* 26:   */   {
/* 27:49 */     Map map = (Map)item;
/* 28:50 */     Serializable[] state = new Serializable[map.size() * 2];
/* 29:51 */     int i = 0;
/* 30:52 */     Iterator iter = map.entrySet().iterator();
/* 31:53 */     while (iter.hasNext())
/* 32:   */     {
/* 33:54 */       Map.Entry me = (Map.Entry)iter.next();
/* 34:55 */       state[(i++)] = ((Serializable)me.getKey());
/* 35:56 */       state[(i++)] = ((Serializable)me.getValue());
/* 36:   */     }
/* 37:58 */     return new CollectionCacheEntry(state);
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.entry.StructuredMapCacheEntry
 * JD-Core Version:    0.7.0.1
 */