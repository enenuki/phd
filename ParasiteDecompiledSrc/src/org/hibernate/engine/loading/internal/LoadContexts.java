/*   1:    */ package org.hibernate.engine.loading.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.IdentityHashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  10:    */ import org.hibernate.engine.spi.CollectionKey;
/*  11:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  15:    */ import org.hibernate.pretty.MessageHelper;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class LoadContexts
/*  19:    */ {
/*  20: 62 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, LoadContexts.class.getName());
/*  21:    */   private final PersistenceContext persistenceContext;
/*  22:    */   private Map<ResultSet, CollectionLoadContext> collectionLoadContexts;
/*  23:    */   private Map<ResultSet, EntityLoadContext> entityLoadContexts;
/*  24:    */   private Map<CollectionKey, LoadingCollectionEntry> xrefLoadingCollectionEntries;
/*  25:    */   
/*  26:    */   public LoadContexts(PersistenceContext persistenceContext)
/*  27:    */   {
/*  28: 77 */     this.persistenceContext = persistenceContext;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public PersistenceContext getPersistenceContext()
/*  32:    */   {
/*  33: 86 */     return this.persistenceContext;
/*  34:    */   }
/*  35:    */   
/*  36:    */   private SessionImplementor getSession()
/*  37:    */   {
/*  38: 90 */     return getPersistenceContext().getSession();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void cleanup(ResultSet resultSet)
/*  42:    */   {
/*  43:106 */     if (this.collectionLoadContexts != null)
/*  44:    */     {
/*  45:107 */       CollectionLoadContext collectionLoadContext = (CollectionLoadContext)this.collectionLoadContexts.remove(resultSet);
/*  46:108 */       collectionLoadContext.cleanup();
/*  47:    */     }
/*  48:110 */     if (this.entityLoadContexts != null)
/*  49:    */     {
/*  50:111 */       EntityLoadContext entityLoadContext = (EntityLoadContext)this.entityLoadContexts.remove(resultSet);
/*  51:112 */       entityLoadContext.cleanup();
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void cleanup()
/*  56:    */   {
/*  57:123 */     if (this.collectionLoadContexts != null)
/*  58:    */     {
/*  59:124 */       for (CollectionLoadContext collectionLoadContext : this.collectionLoadContexts.values())
/*  60:    */       {
/*  61:125 */         LOG.failSafeCollectionsCleanup(collectionLoadContext);
/*  62:126 */         collectionLoadContext.cleanup();
/*  63:    */       }
/*  64:128 */       this.collectionLoadContexts.clear();
/*  65:    */     }
/*  66:130 */     if (this.entityLoadContexts != null)
/*  67:    */     {
/*  68:131 */       for (EntityLoadContext entityLoadContext : this.entityLoadContexts.values())
/*  69:    */       {
/*  70:132 */         LOG.failSafeEntitiesCleanup(entityLoadContext);
/*  71:133 */         entityLoadContext.cleanup();
/*  72:    */       }
/*  73:135 */       this.entityLoadContexts.clear();
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean hasLoadingCollectionEntries()
/*  78:    */   {
/*  79:150 */     return (this.collectionLoadContexts != null) && (!this.collectionLoadContexts.isEmpty());
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean hasRegisteredLoadingCollectionEntries()
/*  83:    */   {
/*  84:161 */     return (this.xrefLoadingCollectionEntries != null) && (!this.xrefLoadingCollectionEntries.isEmpty());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public CollectionLoadContext getCollectionLoadContext(ResultSet resultSet)
/*  88:    */   {
/*  89:173 */     CollectionLoadContext context = null;
/*  90:174 */     if (this.collectionLoadContexts == null) {
/*  91:175 */       this.collectionLoadContexts = new IdentityHashMap(8);
/*  92:    */     } else {
/*  93:178 */       context = (CollectionLoadContext)this.collectionLoadContexts.get(resultSet);
/*  94:    */     }
/*  95:180 */     if (context == null)
/*  96:    */     {
/*  97:181 */       LOG.tracev("Constructing collection load context for result set [{0}]", resultSet);
/*  98:182 */       context = new CollectionLoadContext(this, resultSet);
/*  99:183 */       this.collectionLoadContexts.put(resultSet, context);
/* 100:    */     }
/* 101:185 */     return context;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public PersistentCollection locateLoadingCollection(CollectionPersister persister, Serializable ownerKey)
/* 105:    */   {
/* 106:197 */     LoadingCollectionEntry lce = locateLoadingCollectionEntry(new CollectionKey(persister, ownerKey));
/* 107:198 */     if (lce != null)
/* 108:    */     {
/* 109:199 */       if (LOG.isTraceEnabled()) {
/* 110:200 */         LOG.tracef("Returning loading collection: %s", MessageHelper.collectionInfoString(persister, ownerKey, getSession().getFactory()));
/* 111:    */       }
/* 112:205 */       return lce.getCollection();
/* 113:    */     }
/* 114:208 */     if (LOG.isTraceEnabled()) {
/* 115:209 */       LOG.tracef("Creating collection wrapper: %s", MessageHelper.collectionInfoString(persister, ownerKey, getSession().getFactory()));
/* 116:    */     }
/* 117:212 */     return null;
/* 118:    */   }
/* 119:    */   
/* 120:    */   void registerLoadingCollectionXRef(CollectionKey entryKey, LoadingCollectionEntry entry)
/* 121:    */   {
/* 122:232 */     if (this.xrefLoadingCollectionEntries == null) {
/* 123:233 */       this.xrefLoadingCollectionEntries = new HashMap();
/* 124:    */     }
/* 125:235 */     this.xrefLoadingCollectionEntries.put(entryKey, entry);
/* 126:    */   }
/* 127:    */   
/* 128:    */   void unregisterLoadingCollectionXRef(CollectionKey key)
/* 129:    */   {
/* 130:254 */     if (!hasRegisteredLoadingCollectionEntries()) {
/* 131:255 */       return;
/* 132:    */     }
/* 133:257 */     this.xrefLoadingCollectionEntries.remove(key);
/* 134:    */   }
/* 135:    */   
/* 136:    */   Map getLoadingCollectionXRefs()
/* 137:    */   {
/* 138:261 */     return this.xrefLoadingCollectionEntries;
/* 139:    */   }
/* 140:    */   
/* 141:    */   LoadingCollectionEntry locateLoadingCollectionEntry(CollectionKey key)
/* 142:    */   {
/* 143:277 */     if (this.xrefLoadingCollectionEntries == null) {
/* 144:278 */       return null;
/* 145:    */     }
/* 146:280 */     LOG.tracev("Attempting to locate loading collection entry [{0}] in any result-set context", key);
/* 147:281 */     LoadingCollectionEntry rtn = (LoadingCollectionEntry)this.xrefLoadingCollectionEntries.get(key);
/* 148:282 */     if (rtn == null) {
/* 149:283 */       LOG.tracev("Collection [{0}] not located in load context", key);
/* 150:    */     } else {
/* 151:286 */       LOG.tracev("Collection [{0}] located in load context", key);
/* 152:    */     }
/* 153:288 */     return rtn;
/* 154:    */   }
/* 155:    */   
/* 156:    */   void cleanupCollectionXRefs(Set<CollectionKey> entryKeys)
/* 157:    */   {
/* 158:292 */     for (CollectionKey entryKey : entryKeys) {
/* 159:293 */       this.xrefLoadingCollectionEntries.remove(entryKey);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public EntityLoadContext getEntityLoadContext(ResultSet resultSet)
/* 164:    */   {
/* 165:302 */     EntityLoadContext context = null;
/* 166:303 */     if (this.entityLoadContexts == null) {
/* 167:304 */       this.entityLoadContexts = new IdentityHashMap(8);
/* 168:    */     } else {
/* 169:307 */       context = (EntityLoadContext)this.entityLoadContexts.get(resultSet);
/* 170:    */     }
/* 171:309 */     if (context == null)
/* 172:    */     {
/* 173:310 */       context = new EntityLoadContext(this, resultSet);
/* 174:311 */       this.entityLoadContexts.put(resultSet, context);
/* 175:    */     }
/* 176:313 */     return context;
/* 177:    */   }
/* 178:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.loading.internal.LoadContexts
 * JD-Core Version:    0.7.0.1
 */