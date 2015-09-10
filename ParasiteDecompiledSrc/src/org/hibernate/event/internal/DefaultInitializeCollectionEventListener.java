/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.CacheMode;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.cache.spi.CacheKey;
/*   8:    */ import org.hibernate.cache.spi.CollectionRegion;
/*   9:    */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*  10:    */ import org.hibernate.cache.spi.entry.CacheEntryStructure;
/*  11:    */ import org.hibernate.cache.spi.entry.CollectionCacheEntry;
/*  12:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  13:    */ import org.hibernate.engine.spi.CollectionEntry;
/*  14:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  15:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  16:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  17:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  18:    */ import org.hibernate.event.spi.InitializeCollectionEvent;
/*  19:    */ import org.hibernate.event.spi.InitializeCollectionEventListener;
/*  20:    */ import org.hibernate.internal.CoreMessageLogger;
/*  21:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  22:    */ import org.hibernate.pretty.MessageHelper;
/*  23:    */ import org.hibernate.stat.Statistics;
/*  24:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  25:    */ import org.jboss.logging.Logger;
/*  26:    */ 
/*  27:    */ public class DefaultInitializeCollectionEventListener
/*  28:    */   implements InitializeCollectionEventListener
/*  29:    */ {
/*  30: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultInitializeCollectionEventListener.class.getName());
/*  31:    */   
/*  32:    */   public void onInitializeCollection(InitializeCollectionEvent event)
/*  33:    */     throws HibernateException
/*  34:    */   {
/*  35: 57 */     PersistentCollection collection = event.getCollection();
/*  36: 58 */     SessionImplementor source = event.getSession();
/*  37:    */     
/*  38: 60 */     CollectionEntry ce = source.getPersistenceContext().getCollectionEntry(collection);
/*  39: 61 */     if (ce == null) {
/*  40: 61 */       throw new HibernateException("collection was evicted");
/*  41:    */     }
/*  42: 62 */     if (!collection.wasInitialized())
/*  43:    */     {
/*  44: 63 */       if (LOG.isTraceEnabled()) {
/*  45: 64 */         LOG.tracev("Initializing collection {0}", MessageHelper.collectionInfoString(ce.getLoadedPersister(), ce.getLoadedKey(), source.getFactory()));
/*  46:    */       }
/*  47: 69 */       LOG.trace("Checking second-level cache");
/*  48: 70 */       boolean foundInCache = initializeCollectionFromCache(ce.getLoadedKey(), ce.getLoadedPersister(), collection, source);
/*  49: 77 */       if (foundInCache)
/*  50:    */       {
/*  51: 78 */         LOG.trace("Collection initialized from cache");
/*  52:    */       }
/*  53:    */       else
/*  54:    */       {
/*  55: 81 */         LOG.trace("Collection not cached");
/*  56: 82 */         ce.getLoadedPersister().initialize(ce.getLoadedKey(), source);
/*  57: 83 */         LOG.trace("Collection initialized");
/*  58: 85 */         if (source.getFactory().getStatistics().isStatisticsEnabled()) {
/*  59: 86 */           source.getFactory().getStatisticsImplementor().fetchCollection(ce.getLoadedPersister().getRole());
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   private boolean initializeCollectionFromCache(Serializable id, CollectionPersister persister, PersistentCollection collection, SessionImplementor source)
/*  66:    */   {
/*  67:110 */     if ((!source.getLoadQueryInfluencers().getEnabledFilters().isEmpty()) && (persister.isAffectedByEnabledFilters(source)))
/*  68:    */     {
/*  69:111 */       LOG.trace("Disregarding cached version (if any) of collection due to enabled filters");
/*  70:112 */       return false;
/*  71:    */     }
/*  72:115 */     boolean useCache = (persister.hasCache()) && (source.getCacheMode().isGetEnabled());
/*  73:118 */     if (!useCache) {
/*  74:118 */       return false;
/*  75:    */     }
/*  76:120 */     SessionFactoryImplementor factory = source.getFactory();
/*  77:    */     
/*  78:122 */     CacheKey ck = source.generateCacheKey(id, persister.getKeyType(), persister.getRole());
/*  79:123 */     Object ce = persister.getCacheAccessStrategy().get(ck, source.getTimestamp());
/*  80:125 */     if (factory.getStatistics().isStatisticsEnabled()) {
/*  81:126 */       if (ce == null) {
/*  82:127 */         factory.getStatisticsImplementor().secondLevelCacheMiss(persister.getCacheAccessStrategy().getRegion().getName());
/*  83:    */       } else {
/*  84:131 */         factory.getStatisticsImplementor().secondLevelCacheHit(persister.getCacheAccessStrategy().getRegion().getName());
/*  85:    */       }
/*  86:    */     }
/*  87:136 */     if (ce == null) {
/*  88:137 */       return false;
/*  89:    */     }
/*  90:140 */     CollectionCacheEntry cacheEntry = (CollectionCacheEntry)persister.getCacheEntryStructure().destructure(ce, factory);
/*  91:    */     
/*  92:142 */     PersistenceContext persistenceContext = source.getPersistenceContext();
/*  93:143 */     cacheEntry.assemble(collection, persister, persistenceContext.getCollectionOwner(id, persister));
/*  94:144 */     persistenceContext.getCollectionEntry(collection).postInitialize(collection);
/*  95:    */     
/*  96:146 */     return true;
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultInitializeCollectionEventListener
 * JD-Core Version:    0.7.0.1
 */