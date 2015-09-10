/*   1:    */ package org.hibernate.engine.loading.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.hibernate.CacheMode;
/*  12:    */ import org.hibernate.EntityMode;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.cache.spi.CacheKey;
/*  15:    */ import org.hibernate.cache.spi.CollectionRegion;
/*  16:    */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*  17:    */ import org.hibernate.cache.spi.entry.CacheEntryStructure;
/*  18:    */ import org.hibernate.cache.spi.entry.CollectionCacheEntry;
/*  19:    */ import org.hibernate.cfg.Settings;
/*  20:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  21:    */ import org.hibernate.engine.spi.CollectionEntry;
/*  22:    */ import org.hibernate.engine.spi.CollectionKey;
/*  23:    */ import org.hibernate.engine.spi.EntityEntry;
/*  24:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  25:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  26:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  27:    */ import org.hibernate.engine.spi.Status;
/*  28:    */ import org.hibernate.internal.CoreMessageLogger;
/*  29:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  30:    */ import org.hibernate.persister.entity.EntityPersister;
/*  31:    */ import org.hibernate.pretty.MessageHelper;
/*  32:    */ import org.hibernate.stat.Statistics;
/*  33:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  34:    */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  35:    */ import org.hibernate.type.CollectionType;
/*  36:    */ import org.jboss.logging.Logger;
/*  37:    */ 
/*  38:    */ public class CollectionLoadContext
/*  39:    */ {
/*  40: 63 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, CollectionLoadContext.class.getName());
/*  41:    */   private final LoadContexts loadContexts;
/*  42:    */   private final ResultSet resultSet;
/*  43: 67 */   private Set localLoadingCollectionKeys = new HashSet();
/*  44:    */   
/*  45:    */   public CollectionLoadContext(LoadContexts loadContexts, ResultSet resultSet)
/*  46:    */   {
/*  47: 76 */     this.loadContexts = loadContexts;
/*  48: 77 */     this.resultSet = resultSet;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ResultSet getResultSet()
/*  52:    */   {
/*  53: 81 */     return this.resultSet;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public LoadContexts getLoadContext()
/*  57:    */   {
/*  58: 85 */     return this.loadContexts;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public PersistentCollection getLoadingCollection(CollectionPersister persister, Serializable key)
/*  62:    */   {
/*  63:111 */     EntityMode em = persister.getOwnerEntityPersister().getEntityMetamodel().getEntityMode();
/*  64:112 */     CollectionKey collectionKey = new CollectionKey(persister, key, em);
/*  65:113 */     if (LOG.isTraceEnabled()) {
/*  66:114 */       LOG.tracev("Starting attempt to find loading collection [{0}]", MessageHelper.collectionInfoString(persister.getRole(), key));
/*  67:    */     }
/*  68:117 */     LoadingCollectionEntry loadingCollectionEntry = this.loadContexts.locateLoadingCollectionEntry(collectionKey);
/*  69:118 */     if (loadingCollectionEntry == null)
/*  70:    */     {
/*  71:120 */       PersistentCollection collection = this.loadContexts.getPersistenceContext().getCollection(collectionKey);
/*  72:121 */       if (collection != null)
/*  73:    */       {
/*  74:122 */         if (collection.wasInitialized())
/*  75:    */         {
/*  76:123 */           LOG.trace("Collection already initialized; ignoring");
/*  77:124 */           return null;
/*  78:    */         }
/*  79:126 */         LOG.trace("Collection not yet initialized; initializing");
/*  80:    */       }
/*  81:    */       else
/*  82:    */       {
/*  83:129 */         Object owner = this.loadContexts.getPersistenceContext().getCollectionOwner(key, persister);
/*  84:130 */         boolean newlySavedEntity = (owner != null) && (this.loadContexts.getPersistenceContext().getEntry(owner).getStatus() != Status.LOADING);
/*  85:132 */         if (newlySavedEntity)
/*  86:    */         {
/*  87:135 */           LOG.trace("Owning entity already loaded; ignoring");
/*  88:136 */           return null;
/*  89:    */         }
/*  90:139 */         LOG.tracev("Instantiating new collection [key={0}, rs={1}]", key, this.resultSet);
/*  91:140 */         collection = persister.getCollectionType().instantiate(this.loadContexts.getPersistenceContext().getSession(), persister, key);
/*  92:    */       }
/*  93:143 */       collection.beforeInitialize(persister, -1);
/*  94:144 */       collection.beginRead();
/*  95:145 */       this.localLoadingCollectionKeys.add(collectionKey);
/*  96:146 */       this.loadContexts.registerLoadingCollectionXRef(collectionKey, new LoadingCollectionEntry(this.resultSet, persister, key, collection));
/*  97:147 */       return collection;
/*  98:    */     }
/*  99:149 */     if (loadingCollectionEntry.getResultSet() == this.resultSet)
/* 100:    */     {
/* 101:150 */       LOG.trace("Found loading collection bound to current result set processing; reading row");
/* 102:151 */       return loadingCollectionEntry.getCollection();
/* 103:    */     }
/* 104:155 */     LOG.trace("Collection is already being initialized; ignoring row");
/* 105:156 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void endLoadingCollections(CollectionPersister persister)
/* 109:    */   {
/* 110:167 */     SessionImplementor session = getLoadContext().getPersistenceContext().getSession();
/* 111:168 */     if ((!this.loadContexts.hasLoadingCollectionEntries()) && (this.localLoadingCollectionKeys.isEmpty())) {
/* 112:170 */       return;
/* 113:    */     }
/* 114:179 */     List matches = null;
/* 115:180 */     Iterator iter = this.localLoadingCollectionKeys.iterator();
/* 116:181 */     while (iter.hasNext())
/* 117:    */     {
/* 118:182 */       CollectionKey collectionKey = (CollectionKey)iter.next();
/* 119:183 */       LoadingCollectionEntry lce = this.loadContexts.locateLoadingCollectionEntry(collectionKey);
/* 120:184 */       if (lce == null)
/* 121:    */       {
/* 122:185 */         LOG.loadingCollectionKeyNotFound(collectionKey);
/* 123:    */       }
/* 124:187 */       else if ((lce.getResultSet() == this.resultSet) && (lce.getPersister() == persister))
/* 125:    */       {
/* 126:188 */         if (matches == null) {
/* 127:189 */           matches = new ArrayList();
/* 128:    */         }
/* 129:191 */         matches.add(lce);
/* 130:192 */         if (lce.getCollection().getOwner() == null) {
/* 131:193 */           session.getPersistenceContext().addUnownedCollection(new CollectionKey(persister, lce.getKey(), persister.getOwnerEntityPersister().getEntityMetamodel().getEntityMode()), lce.getCollection());
/* 132:    */         }
/* 133:202 */         LOG.tracev("Removing collection load entry [{0}]", lce);
/* 134:    */         
/* 135:    */ 
/* 136:205 */         this.loadContexts.unregisterLoadingCollectionXRef(collectionKey);
/* 137:206 */         iter.remove();
/* 138:    */       }
/* 139:    */     }
/* 140:210 */     endLoadingCollections(persister, matches);
/* 141:211 */     if (this.localLoadingCollectionKeys.isEmpty()) {
/* 142:218 */       this.loadContexts.cleanup(this.resultSet);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void endLoadingCollections(CollectionPersister persister, List matchedCollectionEntries)
/* 147:    */   {
/* 148:223 */     if (matchedCollectionEntries == null)
/* 149:    */     {
/* 150:224 */       if (LOG.isDebugEnabled()) {
/* 151:224 */         LOG.debugf("No collections were found in result set for role: %s", persister.getRole());
/* 152:    */       }
/* 153:225 */       return;
/* 154:    */     }
/* 155:228 */     int count = matchedCollectionEntries.size();
/* 156:229 */     if (LOG.isDebugEnabled()) {
/* 157:229 */       LOG.debugf("%s collections were found in result set for role: %s", Integer.valueOf(count), persister.getRole());
/* 158:    */     }
/* 159:231 */     for (int i = 0; i < count; i++)
/* 160:    */     {
/* 161:232 */       LoadingCollectionEntry lce = (LoadingCollectionEntry)matchedCollectionEntries.get(i);
/* 162:233 */       endLoadingCollection(lce, persister);
/* 163:    */     }
/* 164:236 */     if (LOG.isDebugEnabled()) {
/* 165:236 */       LOG.debugf("%s collections initialized for role: %s", Integer.valueOf(count), persister.getRole());
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   private void endLoadingCollection(LoadingCollectionEntry lce, CollectionPersister persister)
/* 170:    */   {
/* 171:240 */     LOG.tracev("Ending loading collection [{0}]", lce);
/* 172:241 */     SessionImplementor session = getLoadContext().getPersistenceContext().getSession();
/* 173:    */     
/* 174:243 */     boolean hasNoQueuedAdds = lce.getCollection().endRead();
/* 175:245 */     if (persister.getCollectionType().hasHolder()) {
/* 176:246 */       getLoadContext().getPersistenceContext().addCollectionHolder(lce.getCollection());
/* 177:    */     }
/* 178:249 */     CollectionEntry ce = getLoadContext().getPersistenceContext().getCollectionEntry(lce.getCollection());
/* 179:250 */     if (ce == null) {
/* 180:251 */       ce = getLoadContext().getPersistenceContext().addInitializedCollection(persister, lce.getCollection(), lce.getKey());
/* 181:    */     } else {
/* 182:254 */       ce.postInitialize(lce.getCollection());
/* 183:    */     }
/* 184:257 */     boolean addToCache = (hasNoQueuedAdds) && (persister.hasCache()) && (session.getCacheMode().isPutEnabled()) && (!ce.isDoremove());
/* 185:261 */     if (addToCache) {
/* 186:262 */       addCollectionToCache(lce, persister);
/* 187:    */     }
/* 188:265 */     if (LOG.isDebugEnabled()) {
/* 189:266 */       LOG.debugf("Collection fully initialized: %s", MessageHelper.collectionInfoString(persister, lce.getKey(), session.getFactory()));
/* 190:    */     }
/* 191:271 */     if (session.getFactory().getStatistics().isStatisticsEnabled()) {
/* 192:272 */       session.getFactory().getStatisticsImplementor().loadCollection(persister.getRole());
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void addCollectionToCache(LoadingCollectionEntry lce, CollectionPersister persister)
/* 197:    */   {
/* 198:283 */     SessionImplementor session = getLoadContext().getPersistenceContext().getSession();
/* 199:284 */     SessionFactoryImplementor factory = session.getFactory();
/* 200:286 */     if (LOG.isDebugEnabled()) {
/* 201:287 */       LOG.debugf("Caching collection: %s", MessageHelper.collectionInfoString(persister, lce.getKey(), factory));
/* 202:    */     }
/* 203:290 */     if ((!session.getEnabledFilters().isEmpty()) && (persister.isAffectedByEnabledFilters(session)))
/* 204:    */     {
/* 205:292 */       LOG.debug("Refusing to add to cache due to enabled filters"); return;
/* 206:    */     }
/* 207:    */     Object version;
/* 208:    */     Object version;
/* 209:302 */     if (persister.isVersioned())
/* 210:    */     {
/* 211:303 */       Object collectionOwner = getLoadContext().getPersistenceContext().getCollectionOwner(lce.getKey(), persister);
/* 212:304 */       if (collectionOwner == null)
/* 213:    */       {
/* 214:310 */         if (lce.getCollection() != null)
/* 215:    */         {
/* 216:311 */           Object linkedOwner = lce.getCollection().getOwner();
/* 217:312 */           if (linkedOwner != null)
/* 218:    */           {
/* 219:313 */             Serializable ownerKey = persister.getOwnerEntityPersister().getIdentifier(linkedOwner, session);
/* 220:314 */             collectionOwner = getLoadContext().getPersistenceContext().getCollectionOwner(ownerKey, persister);
/* 221:    */           }
/* 222:    */         }
/* 223:317 */         if (collectionOwner == null) {
/* 224:318 */           throw new HibernateException("Unable to resolve owner of loading collection [" + MessageHelper.collectionInfoString(persister, lce.getKey(), factory) + "] for second level caching");
/* 225:    */         }
/* 226:    */       }
/* 227:325 */       version = getLoadContext().getPersistenceContext().getEntry(collectionOwner).getVersion();
/* 228:    */     }
/* 229:    */     else
/* 230:    */     {
/* 231:328 */       version = null;
/* 232:    */     }
/* 233:331 */     CollectionCacheEntry entry = new CollectionCacheEntry(lce.getCollection(), persister);
/* 234:332 */     CacheKey cacheKey = session.generateCacheKey(lce.getKey(), persister.getKeyType(), persister.getRole());
/* 235:333 */     boolean put = persister.getCacheAccessStrategy().putFromLoad(cacheKey, persister.getCacheEntryStructure().structure(entry), session.getTimestamp(), version, (factory.getSettings().isMinimalPutsEnabled()) && (session.getCacheMode() != CacheMode.REFRESH));
/* 236:341 */     if ((put) && (factory.getStatistics().isStatisticsEnabled())) {
/* 237:342 */       factory.getStatisticsImplementor().secondLevelCachePut(persister.getCacheAccessStrategy().getRegion().getName());
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   void cleanup()
/* 242:    */   {
/* 243:347 */     if (!this.localLoadingCollectionKeys.isEmpty()) {
/* 244:348 */       LOG.localLoadingCollectionKeysCount(this.localLoadingCollectionKeys.size());
/* 245:    */     }
/* 246:350 */     this.loadContexts.cleanupCollectionXRefs(this.localLoadingCollectionKeys);
/* 247:351 */     this.localLoadingCollectionKeys.clear();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String toString()
/* 251:    */   {
/* 252:357 */     return super.toString() + "<rs=" + this.resultSet + ">";
/* 253:    */   }
/* 254:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.loading.internal.CollectionLoadContext
 * JD-Core Version:    0.7.0.1
 */