/*  1:   */ package org.hibernate.cache.spi.entry;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.persister.entity.EntityPersister;
/*  8:   */ 
/*  9:   */ public class StructuredCacheEntry
/* 10:   */   implements CacheEntryStructure
/* 11:   */ {
/* 12:   */   private EntityPersister persister;
/* 13:   */   
/* 14:   */   public StructuredCacheEntry(EntityPersister persister)
/* 15:   */   {
/* 16:41 */     this.persister = persister;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Object destructure(Object item, SessionFactoryImplementor factory)
/* 20:   */   {
/* 21:45 */     Map map = (Map)item;
/* 22:46 */     boolean lazyPropertiesUnfetched = ((Boolean)map.get("_lazyPropertiesUnfetched")).booleanValue();
/* 23:47 */     String subclass = (String)map.get("_subclass");
/* 24:48 */     Object version = map.get("_version");
/* 25:49 */     EntityPersister subclassPersister = factory.getEntityPersister(subclass);
/* 26:50 */     String[] names = subclassPersister.getPropertyNames();
/* 27:51 */     Serializable[] state = new Serializable[names.length];
/* 28:52 */     for (int i = 0; i < names.length; i++) {
/* 29:53 */       state[i] = ((Serializable)map.get(names[i]));
/* 30:   */     }
/* 31:55 */     return new CacheEntry(state, subclass, lazyPropertiesUnfetched, version);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Object structure(Object item)
/* 35:   */   {
/* 36:59 */     CacheEntry entry = (CacheEntry)item;
/* 37:60 */     String[] names = this.persister.getPropertyNames();
/* 38:61 */     Map map = new HashMap(names.length + 2);
/* 39:62 */     map.put("_subclass", entry.getSubclass());
/* 40:63 */     map.put("_version", entry.getVersion());
/* 41:64 */     map.put("_lazyPropertiesUnfetched", Boolean.valueOf(entry.areLazyPropertiesUnfetched()));
/* 42:65 */     for (int i = 0; i < names.length; i++) {
/* 43:66 */       map.put(names[i], entry.getDisassembledState()[i]);
/* 44:   */     }
/* 45:68 */     return map;
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.entry.StructuredCacheEntry
 * JD-Core Version:    0.7.0.1
 */