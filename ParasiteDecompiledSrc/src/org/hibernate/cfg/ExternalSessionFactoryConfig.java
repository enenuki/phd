/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Map.Entry;
/*   8:    */ import java.util.Properties;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  11:    */ 
/*  12:    */ public abstract class ExternalSessionFactoryConfig
/*  13:    */ {
/*  14:    */   private String mapResources;
/*  15:    */   private String dialect;
/*  16:    */   private String defaultSchema;
/*  17:    */   private String defaultCatalog;
/*  18:    */   private String maximumFetchDepth;
/*  19:    */   private String jdbcFetchSize;
/*  20:    */   private String jdbcBatchSize;
/*  21:    */   private String batchVersionedDataEnabled;
/*  22:    */   private String jdbcScrollableResultSetEnabled;
/*  23:    */   private String getGeneratedKeysEnabled;
/*  24:    */   private String streamsForBinaryEnabled;
/*  25:    */   private String reflectionOptimizationEnabled;
/*  26:    */   private String querySubstitutions;
/*  27:    */   private String showSqlEnabled;
/*  28:    */   private String commentsEnabled;
/*  29:    */   private String cacheRegionFactory;
/*  30:    */   private String cacheProviderConfig;
/*  31:    */   private String cacheRegionPrefix;
/*  32:    */   private String secondLevelCacheEnabled;
/*  33:    */   private String minimalPutsEnabled;
/*  34:    */   private String queryCacheEnabled;
/*  35:    */   private Map additionalProperties;
/*  36: 66 */   private Set excludedPropertyNames = new HashSet();
/*  37:    */   
/*  38:    */   protected Set getExcludedPropertyNames()
/*  39:    */   {
/*  40: 69 */     return this.excludedPropertyNames;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final String getMapResources()
/*  44:    */   {
/*  45: 73 */     return this.mapResources;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final void setMapResources(String mapResources)
/*  49:    */   {
/*  50: 77 */     this.mapResources = mapResources;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void addMapResource(String mapResource)
/*  54:    */   {
/*  55: 81 */     if ((this.mapResources == null) || (this.mapResources.length() == 0)) {
/*  56: 82 */       this.mapResources = mapResource.trim();
/*  57:    */     } else {
/*  58: 85 */       this.mapResources = (this.mapResources + ", " + mapResource.trim());
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final String getDialect()
/*  63:    */   {
/*  64: 90 */     return this.dialect;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final void setDialect(String dialect)
/*  68:    */   {
/*  69: 94 */     this.dialect = dialect;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final String getDefaultSchema()
/*  73:    */   {
/*  74: 98 */     return this.defaultSchema;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final void setDefaultSchema(String defaultSchema)
/*  78:    */   {
/*  79:102 */     this.defaultSchema = defaultSchema;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final String getDefaultCatalog()
/*  83:    */   {
/*  84:106 */     return this.defaultCatalog;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final void setDefaultCatalog(String defaultCatalog)
/*  88:    */   {
/*  89:110 */     this.defaultCatalog = defaultCatalog;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final String getMaximumFetchDepth()
/*  93:    */   {
/*  94:114 */     return this.maximumFetchDepth;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final void setMaximumFetchDepth(String maximumFetchDepth)
/*  98:    */   {
/*  99:118 */     verifyInt(maximumFetchDepth);
/* 100:119 */     this.maximumFetchDepth = maximumFetchDepth;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public final String getJdbcFetchSize()
/* 104:    */   {
/* 105:123 */     return this.jdbcFetchSize;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public final void setJdbcFetchSize(String jdbcFetchSize)
/* 109:    */   {
/* 110:127 */     verifyInt(jdbcFetchSize);
/* 111:128 */     this.jdbcFetchSize = jdbcFetchSize;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public final String getJdbcBatchSize()
/* 115:    */   {
/* 116:132 */     return this.jdbcBatchSize;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public final void setJdbcBatchSize(String jdbcBatchSize)
/* 120:    */   {
/* 121:136 */     verifyInt(jdbcBatchSize);
/* 122:137 */     this.jdbcBatchSize = jdbcBatchSize;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public final String getBatchVersionedDataEnabled()
/* 126:    */   {
/* 127:141 */     return this.batchVersionedDataEnabled;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public final void setBatchVersionedDataEnabled(String batchVersionedDataEnabled)
/* 131:    */   {
/* 132:145 */     this.batchVersionedDataEnabled = batchVersionedDataEnabled;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public final String getJdbcScrollableResultSetEnabled()
/* 136:    */   {
/* 137:149 */     return this.jdbcScrollableResultSetEnabled;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public final void setJdbcScrollableResultSetEnabled(String jdbcScrollableResultSetEnabled)
/* 141:    */   {
/* 142:153 */     this.jdbcScrollableResultSetEnabled = jdbcScrollableResultSetEnabled;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public final String getGetGeneratedKeysEnabled()
/* 146:    */   {
/* 147:157 */     return this.getGeneratedKeysEnabled;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public final void setGetGeneratedKeysEnabled(String getGeneratedKeysEnabled)
/* 151:    */   {
/* 152:161 */     this.getGeneratedKeysEnabled = getGeneratedKeysEnabled;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public final String getStreamsForBinaryEnabled()
/* 156:    */   {
/* 157:165 */     return this.streamsForBinaryEnabled;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public final void setStreamsForBinaryEnabled(String streamsForBinaryEnabled)
/* 161:    */   {
/* 162:169 */     this.streamsForBinaryEnabled = streamsForBinaryEnabled;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public final String getReflectionOptimizationEnabled()
/* 166:    */   {
/* 167:173 */     return this.reflectionOptimizationEnabled;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public final void setReflectionOptimizationEnabled(String reflectionOptimizationEnabled)
/* 171:    */   {
/* 172:177 */     this.reflectionOptimizationEnabled = reflectionOptimizationEnabled;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public final String getQuerySubstitutions()
/* 176:    */   {
/* 177:181 */     return this.querySubstitutions;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public final void setQuerySubstitutions(String querySubstitutions)
/* 181:    */   {
/* 182:185 */     this.querySubstitutions = querySubstitutions;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public final String getShowSqlEnabled()
/* 186:    */   {
/* 187:189 */     return this.showSqlEnabled;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public final void setShowSqlEnabled(String showSqlEnabled)
/* 191:    */   {
/* 192:193 */     this.showSqlEnabled = showSqlEnabled;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public final String getCommentsEnabled()
/* 196:    */   {
/* 197:197 */     return this.commentsEnabled;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public final void setCommentsEnabled(String commentsEnabled)
/* 201:    */   {
/* 202:201 */     this.commentsEnabled = commentsEnabled;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public final String getSecondLevelCacheEnabled()
/* 206:    */   {
/* 207:205 */     return this.secondLevelCacheEnabled;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public final void setSecondLevelCacheEnabled(String secondLevelCacheEnabled)
/* 211:    */   {
/* 212:209 */     this.secondLevelCacheEnabled = secondLevelCacheEnabled;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public final String getCacheRegionFactory()
/* 216:    */   {
/* 217:213 */     return this.cacheRegionFactory;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public final void setCacheRegionFactory(String cacheRegionFactory)
/* 221:    */   {
/* 222:217 */     this.cacheRegionFactory = cacheRegionFactory;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public String getCacheProviderConfig()
/* 226:    */   {
/* 227:221 */     return this.cacheProviderConfig;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setCacheProviderConfig(String cacheProviderConfig)
/* 231:    */   {
/* 232:225 */     this.cacheProviderConfig = cacheProviderConfig;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public final String getCacheRegionPrefix()
/* 236:    */   {
/* 237:229 */     return this.cacheRegionPrefix;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public final void setCacheRegionPrefix(String cacheRegionPrefix)
/* 241:    */   {
/* 242:233 */     this.cacheRegionPrefix = cacheRegionPrefix;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public final String getMinimalPutsEnabled()
/* 246:    */   {
/* 247:237 */     return this.minimalPutsEnabled;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public final void setMinimalPutsEnabled(String minimalPutsEnabled)
/* 251:    */   {
/* 252:241 */     this.minimalPutsEnabled = minimalPutsEnabled;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public final String getQueryCacheEnabled()
/* 256:    */   {
/* 257:245 */     return this.queryCacheEnabled;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public final void setQueryCacheEnabled(String queryCacheEnabled)
/* 261:    */   {
/* 262:249 */     this.queryCacheEnabled = queryCacheEnabled;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public final void addAdditionalProperty(String name, String value)
/* 266:    */   {
/* 267:253 */     if (!getExcludedPropertyNames().contains(name))
/* 268:    */     {
/* 269:254 */       if (this.additionalProperties == null) {
/* 270:255 */         this.additionalProperties = new HashMap();
/* 271:    */       }
/* 272:257 */       this.additionalProperties.put(name, value);
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   protected final Configuration buildConfiguration()
/* 277:    */   {
/* 278:263 */     Configuration cfg = new Configuration().setProperties(buildProperties());
/* 279:    */     
/* 280:    */ 
/* 281:266 */     String[] mappingFiles = ConfigurationHelper.toStringArray(this.mapResources, " ,\n\t\r\f");
/* 282:267 */     for (int i = 0; i < mappingFiles.length; i++) {
/* 283:268 */       cfg.addResource(mappingFiles[i]);
/* 284:    */     }
/* 285:271 */     return cfg;
/* 286:    */   }
/* 287:    */   
/* 288:    */   protected final Properties buildProperties()
/* 289:    */   {
/* 290:275 */     Properties props = new Properties();
/* 291:276 */     setUnlessNull(props, "hibernate.dialect", this.dialect);
/* 292:277 */     setUnlessNull(props, "hibernate.default_schema", this.defaultSchema);
/* 293:278 */     setUnlessNull(props, "hibernate.default_catalog", this.defaultCatalog);
/* 294:279 */     setUnlessNull(props, "hibernate.max_fetch_depth", this.maximumFetchDepth);
/* 295:280 */     setUnlessNull(props, "hibernate.jdbc.fetch_size", this.jdbcFetchSize);
/* 296:281 */     setUnlessNull(props, "hibernate.jdbc.batch_size", this.jdbcBatchSize);
/* 297:282 */     setUnlessNull(props, "hibernate.jdbc.batch_versioned_data", this.batchVersionedDataEnabled);
/* 298:283 */     setUnlessNull(props, "hibernate.jdbc.use_scrollable_resultset", this.jdbcScrollableResultSetEnabled);
/* 299:284 */     setUnlessNull(props, "hibernate.jdbc.use_get_generated_keys", this.getGeneratedKeysEnabled);
/* 300:285 */     setUnlessNull(props, "hibernate.jdbc.use_streams_for_binary", this.streamsForBinaryEnabled);
/* 301:286 */     setUnlessNull(props, "hibernate.bytecode.use_reflection_optimizer", this.reflectionOptimizationEnabled);
/* 302:287 */     setUnlessNull(props, "hibernate.query.substitutions", this.querySubstitutions);
/* 303:288 */     setUnlessNull(props, "hibernate.show_sql", this.showSqlEnabled);
/* 304:289 */     setUnlessNull(props, "hibernate.use_sql_comments", this.commentsEnabled);
/* 305:290 */     setUnlessNull(props, "hibernate.cache.region.factory_class", this.cacheRegionFactory);
/* 306:291 */     setUnlessNull(props, "hibernate.cache.provider_configuration_file_resource_path", this.cacheProviderConfig);
/* 307:292 */     setUnlessNull(props, "hibernate.cache.region_prefix", this.cacheRegionPrefix);
/* 308:293 */     setUnlessNull(props, "hibernate.cache.use_minimal_puts", this.minimalPutsEnabled);
/* 309:294 */     setUnlessNull(props, "hibernate.cache.use_second_level_cache", this.secondLevelCacheEnabled);
/* 310:295 */     setUnlessNull(props, "hibernate.cache.use_query_cache", this.queryCacheEnabled);
/* 311:    */     
/* 312:297 */     Map extraProperties = getExtraProperties();
/* 313:298 */     if (extraProperties != null) {
/* 314:299 */       addAll(props, extraProperties);
/* 315:    */     }
/* 316:302 */     if (this.additionalProperties != null) {
/* 317:303 */       addAll(props, this.additionalProperties);
/* 318:    */     }
/* 319:306 */     return props;
/* 320:    */   }
/* 321:    */   
/* 322:    */   protected void addAll(Properties target, Map source)
/* 323:    */   {
/* 324:310 */     Iterator itr = source.entrySet().iterator();
/* 325:311 */     while (itr.hasNext())
/* 326:    */     {
/* 327:312 */       Map.Entry entry = (Map.Entry)itr.next();
/* 328:313 */       String propertyName = (String)entry.getKey();
/* 329:314 */       String propertyValue = (String)entry.getValue();
/* 330:315 */       if ((propertyName != null) && (propertyValue != null)) {
/* 331:317 */         if ((!target.keySet().contains(propertyName)) && 
/* 332:318 */           (!getExcludedPropertyNames().contains(propertyName))) {
/* 333:319 */           target.put(propertyName, propertyValue);
/* 334:    */         }
/* 335:    */       }
/* 336:    */     }
/* 337:    */   }
/* 338:    */   
/* 339:    */   protected Map getExtraProperties()
/* 340:    */   {
/* 341:327 */     return null;
/* 342:    */   }
/* 343:    */   
/* 344:    */   private void setUnlessNull(Properties props, String key, String value)
/* 345:    */   {
/* 346:331 */     if (value != null) {
/* 347:332 */       props.setProperty(key, value);
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   private void verifyInt(String value)
/* 352:    */   {
/* 353:338 */     if (value != null) {
/* 354:339 */       Integer.parseInt(value);
/* 355:    */     }
/* 356:    */   }
/* 357:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ExternalSessionFactoryConfig
 * JD-Core Version:    0.7.0.1
 */