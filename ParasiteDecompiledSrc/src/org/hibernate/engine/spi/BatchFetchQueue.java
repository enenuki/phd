/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedHashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.EntityMode;
/*  11:    */ import org.hibernate.cache.spi.CacheKey;
/*  12:    */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*  13:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  14:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  15:    */ import org.hibernate.internal.util.MarkerObject;
/*  16:    */ import org.hibernate.internal.util.collections.IdentityMap;
/*  17:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  18:    */ import org.hibernate.persister.entity.EntityPersister;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ 
/*  21:    */ public class BatchFetchQueue
/*  22:    */ {
/*  23: 49 */   public static final Object MARKER = new MarkerObject("MARKER");
/*  24: 61 */   private final Map batchLoadableEntityKeys = new LinkedHashMap(8);
/*  25: 67 */   private final Map subselectsByEntityKey = new HashMap(8);
/*  26:    */   private final PersistenceContext context;
/*  27:    */   
/*  28:    */   public BatchFetchQueue(PersistenceContext context)
/*  29:    */   {
/*  30: 80 */     this.context = context;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void clear()
/*  34:    */   {
/*  35: 87 */     this.batchLoadableEntityKeys.clear();
/*  36: 88 */     this.subselectsByEntityKey.clear();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public SubselectFetch getSubselect(EntityKey key)
/*  40:    */   {
/*  41: 99 */     return (SubselectFetch)this.subselectsByEntityKey.get(key);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void addSubselect(EntityKey key, SubselectFetch subquery)
/*  45:    */   {
/*  46:109 */     this.subselectsByEntityKey.put(key, subquery);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void removeSubselect(EntityKey key)
/*  50:    */   {
/*  51:119 */     this.subselectsByEntityKey.remove(key);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void clearSubselects()
/*  55:    */   {
/*  56:128 */     this.subselectsByEntityKey.clear();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void addBatchLoadableEntityKey(EntityKey key)
/*  60:    */   {
/*  61:142 */     if (key.isBatchLoadable()) {
/*  62:143 */       this.batchLoadableEntityKeys.put(key, MARKER);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void removeBatchLoadableEntityKey(EntityKey key)
/*  67:    */   {
/*  68:153 */     if (key.isBatchLoadable()) {
/*  69:153 */       this.batchLoadableEntityKeys.remove(key);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Serializable[] getCollectionBatch(CollectionPersister collectionPersister, Serializable id, int batchSize)
/*  74:    */   {
/*  75:168 */     Serializable[] keys = new Serializable[batchSize];
/*  76:169 */     keys[0] = id;
/*  77:170 */     int i = 1;
/*  78:    */     
/*  79:172 */     int end = -1;
/*  80:173 */     boolean checkForEnd = false;
/*  81:179 */     for (Map.Entry<PersistentCollection, CollectionEntry> me : IdentityMap.concurrentEntries(this.context.getCollectionEntries()))
/*  82:    */     {
/*  83:181 */       CollectionEntry ce = (CollectionEntry)me.getValue();
/*  84:182 */       PersistentCollection collection = (PersistentCollection)me.getKey();
/*  85:183 */       if ((!collection.wasInitialized()) && (ce.getLoadedPersister() == collectionPersister))
/*  86:    */       {
/*  87:185 */         if ((checkForEnd) && (i == end)) {
/*  88:186 */           return keys;
/*  89:    */         }
/*  90:191 */         boolean isEqual = collectionPersister.getKeyType().isEqual(id, ce.getLoadedKey(), collectionPersister.getFactory());
/*  91:197 */         if (isEqual) {
/*  92:198 */           end = i;
/*  93:201 */         } else if (!isCached(ce.getLoadedKey(), collectionPersister)) {
/*  94:202 */           keys[(i++)] = ce.getLoadedKey();
/*  95:    */         }
/*  96:206 */         if (i == batchSize)
/*  97:    */         {
/*  98:207 */           i = 1;
/*  99:208 */           if (end != -1) {
/* 100:209 */             checkForEnd = true;
/* 101:    */           }
/* 102:    */         }
/* 103:    */       }
/* 104:    */     }
/* 105:215 */     return keys;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Serializable[] getEntityBatch(EntityPersister persister, Serializable id, int batchSize, EntityMode entityMode)
/* 109:    */   {
/* 110:233 */     Serializable[] ids = new Serializable[batchSize];
/* 111:234 */     ids[0] = id;
/* 112:235 */     int i = 1;
/* 113:236 */     int end = -1;
/* 114:237 */     boolean checkForEnd = false;
/* 115:    */     
/* 116:239 */     Iterator iter = this.batchLoadableEntityKeys.keySet().iterator();
/* 117:240 */     while (iter.hasNext())
/* 118:    */     {
/* 119:241 */       EntityKey key = (EntityKey)iter.next();
/* 120:242 */       if (key.getEntityName().equals(persister.getEntityName()))
/* 121:    */       {
/* 122:243 */         if ((checkForEnd) && (i == end)) {
/* 123:245 */           return ids;
/* 124:    */         }
/* 125:247 */         if (persister.getIdentifierType().isEqual(id, key.getIdentifier())) {
/* 126:248 */           end = i;
/* 127:251 */         } else if (!isCached(key, persister)) {
/* 128:252 */           ids[(i++)] = key.getIdentifier();
/* 129:    */         }
/* 130:255 */         if (i == batchSize)
/* 131:    */         {
/* 132:256 */           i = 1;
/* 133:257 */           if (end != -1) {
/* 134:257 */             checkForEnd = true;
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:261 */     return ids;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private boolean isCached(EntityKey entityKey, EntityPersister persister)
/* 143:    */   {
/* 144:265 */     if (persister.hasCache())
/* 145:    */     {
/* 146:266 */       CacheKey key = this.context.getSession().generateCacheKey(entityKey.getIdentifier(), persister.getIdentifierType(), entityKey.getEntityName());
/* 147:    */       
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:271 */       return persister.getCacheAccessStrategy().get(key, this.context.getSession().getTimestamp()) != null;
/* 152:    */     }
/* 153:273 */     return false;
/* 154:    */   }
/* 155:    */   
/* 156:    */   private boolean isCached(Serializable collectionKey, CollectionPersister persister)
/* 157:    */   {
/* 158:277 */     if (persister.hasCache())
/* 159:    */     {
/* 160:278 */       CacheKey cacheKey = this.context.getSession().generateCacheKey(collectionKey, persister.getKeyType(), persister.getRole());
/* 161:    */       
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:283 */       return persister.getCacheAccessStrategy().get(cacheKey, this.context.getSession().getTimestamp()) != null;
/* 166:    */     }
/* 167:285 */     return false;
/* 168:    */   }
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.BatchFetchQueue
 * JD-Core Version:    0.7.0.1
 */