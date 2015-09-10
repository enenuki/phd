/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.hibernate.ConnectionReleaseMode;
/*   8:    */ import org.hibernate.EntityMode;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.MultiTenancyStrategy;
/*  11:    */ import org.hibernate.cache.internal.NoCachingRegionFactory;
/*  12:    */ import org.hibernate.cache.internal.RegionFactoryInitiator;
/*  13:    */ import org.hibernate.cache.internal.StandardQueryCacheFactory;
/*  14:    */ import org.hibernate.cache.spi.QueryCacheFactory;
/*  15:    */ import org.hibernate.cache.spi.RegionFactory;
/*  16:    */ import org.hibernate.dialect.Dialect;
/*  17:    */ import org.hibernate.engine.jdbc.spi.ExtractedDatabaseMetaData;
/*  18:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  19:    */ import org.hibernate.engine.transaction.spi.TransactionFactory;
/*  20:    */ import org.hibernate.hql.spi.QueryTranslatorFactory;
/*  21:    */ import org.hibernate.internal.CoreMessageLogger;
/*  22:    */ import org.hibernate.internal.util.ReflectHelper;
/*  23:    */ import org.hibernate.internal.util.StringHelper;
/*  24:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  25:    */ import org.hibernate.service.ServiceRegistry;
/*  26:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  27:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  28:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  29:    */ import org.hibernate.tuple.entity.EntityTuplizerFactory;
/*  30:    */ import org.jboss.logging.Logger;
/*  31:    */ 
/*  32:    */ public class SettingsFactory
/*  33:    */   implements Serializable
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = -1194386144994524825L;
/*  36: 62 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SettingsFactory.class.getName());
/*  37: 64 */   public static final String DEF_CACHE_REG_FACTORY = NoCachingRegionFactory.class.getName();
/*  38:    */   
/*  39:    */   public Settings buildSettings(Properties props, ServiceRegistry serviceRegistry)
/*  40:    */   {
/*  41: 70 */     boolean debugEnabled = LOG.isDebugEnabled();
/*  42: 71 */     JdbcServices jdbcServices = (JdbcServices)serviceRegistry.getService(JdbcServices.class);
/*  43: 72 */     Settings settings = new Settings();
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47: 76 */     String sessionFactoryName = props.getProperty("hibernate.session_factory_name");
/*  48: 77 */     settings.setSessionFactoryName(sessionFactoryName);
/*  49:    */     
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 82 */     ExtractedDatabaseMetaData meta = jdbcServices.getExtractedMetaDataSupport();
/*  54:    */     
/*  55: 84 */     settings.setDataDefinitionImplicitCommit(meta.doesDataDefinitionCauseTransactionCommit());
/*  56: 85 */     settings.setDataDefinitionInTransactionSupported(meta.supportsDataDefinitionInTransaction());
/*  57:    */     
/*  58:    */ 
/*  59: 88 */     Properties properties = new Properties();
/*  60: 89 */     properties.putAll(jdbcServices.getDialect().getDefaultProperties());
/*  61: 90 */     properties.putAll(props);
/*  62:    */     
/*  63:    */ 
/*  64: 93 */     settings.setJtaPlatform((JtaPlatform)serviceRegistry.getService(JtaPlatform.class));
/*  65:    */     
/*  66: 95 */     boolean flushBeforeCompletion = ConfigurationHelper.getBoolean("hibernate.transaction.flush_before_completion", properties);
/*  67: 96 */     if (debugEnabled) {
/*  68: 97 */       LOG.debugf("Automatic flush during beforeCompletion(): %s", enabledDisabled(flushBeforeCompletion));
/*  69:    */     }
/*  70: 99 */     settings.setFlushBeforeCompletionEnabled(flushBeforeCompletion);
/*  71:    */     
/*  72:101 */     boolean autoCloseSession = ConfigurationHelper.getBoolean("hibernate.transaction.auto_close_session", properties);
/*  73:102 */     if (debugEnabled) {
/*  74:103 */       LOG.debugf("Automatic session close at end of transaction: %s", enabledDisabled(autoCloseSession));
/*  75:    */     }
/*  76:105 */     settings.setAutoCloseSessionEnabled(autoCloseSession);
/*  77:    */     
/*  78:    */ 
/*  79:    */ 
/*  80:109 */     int batchSize = ConfigurationHelper.getInt("hibernate.jdbc.batch_size", properties, 0);
/*  81:110 */     if (!meta.supportsBatchUpdates()) {
/*  82:111 */       batchSize = 0;
/*  83:    */     }
/*  84:113 */     if ((batchSize > 0) && (debugEnabled)) {
/*  85:114 */       LOG.debugf("JDBC batch size: %s", Integer.valueOf(batchSize));
/*  86:    */     }
/*  87:116 */     settings.setJdbcBatchSize(batchSize);
/*  88:    */     
/*  89:118 */     boolean jdbcBatchVersionedData = ConfigurationHelper.getBoolean("hibernate.jdbc.batch_versioned_data", properties, false);
/*  90:119 */     if ((batchSize > 0) && (debugEnabled)) {
/*  91:120 */       LOG.debugf("JDBC batch updates for versioned data: %s", enabledDisabled(jdbcBatchVersionedData));
/*  92:    */     }
/*  93:122 */     settings.setJdbcBatchVersionedData(jdbcBatchVersionedData);
/*  94:    */     
/*  95:124 */     boolean useScrollableResultSets = ConfigurationHelper.getBoolean("hibernate.jdbc.use_scrollable_resultset", properties, meta.supportsScrollableResults());
/*  96:129 */     if (debugEnabled) {
/*  97:130 */       LOG.debugf("Scrollable result sets: %s", enabledDisabled(useScrollableResultSets));
/*  98:    */     }
/*  99:132 */     settings.setScrollableResultSetsEnabled(useScrollableResultSets);
/* 100:    */     
/* 101:134 */     boolean wrapResultSets = ConfigurationHelper.getBoolean("hibernate.jdbc.wrap_result_sets", properties, false);
/* 102:135 */     if (debugEnabled) {
/* 103:136 */       LOG.debugf("Wrap result sets: %s", enabledDisabled(wrapResultSets));
/* 104:    */     }
/* 105:138 */     settings.setWrapResultSetsEnabled(wrapResultSets);
/* 106:    */     
/* 107:140 */     boolean useGetGeneratedKeys = ConfigurationHelper.getBoolean("hibernate.jdbc.use_get_generated_keys", properties, meta.supportsGetGeneratedKeys());
/* 108:141 */     if (debugEnabled) {
/* 109:142 */       LOG.debugf("JDBC3 getGeneratedKeys(): %s", enabledDisabled(useGetGeneratedKeys));
/* 110:    */     }
/* 111:144 */     settings.setGetGeneratedKeysEnabled(useGetGeneratedKeys);
/* 112:    */     
/* 113:146 */     Integer statementFetchSize = ConfigurationHelper.getInteger("hibernate.jdbc.fetch_size", properties);
/* 114:147 */     if ((statementFetchSize != null) && (debugEnabled)) {
/* 115:148 */       LOG.debugf("JDBC result set fetch size: %s", statementFetchSize);
/* 116:    */     }
/* 117:150 */     settings.setJdbcFetchSize(statementFetchSize);
/* 118:    */     
/* 119:152 */     String releaseModeName = ConfigurationHelper.getString("hibernate.connection.release_mode", properties, "auto");
/* 120:153 */     if (debugEnabled) {
/* 121:154 */       LOG.debugf("Connection release mode: %s", releaseModeName);
/* 122:    */     }
/* 123:    */     ConnectionReleaseMode releaseMode;
/* 124:    */     ConnectionReleaseMode releaseMode;
/* 125:157 */     if ("auto".equals(releaseModeName))
/* 126:    */     {
/* 127:158 */       releaseMode = ((TransactionFactory)serviceRegistry.getService(TransactionFactory.class)).getDefaultReleaseMode();
/* 128:    */     }
/* 129:    */     else
/* 130:    */     {
/* 131:161 */       releaseMode = ConnectionReleaseMode.parse(releaseModeName);
/* 132:162 */       if ((releaseMode == ConnectionReleaseMode.AFTER_STATEMENT) && (!jdbcServices.getConnectionProvider().supportsAggressiveRelease()))
/* 133:    */       {
/* 134:164 */         LOG.unsupportedAfterStatement();
/* 135:165 */         releaseMode = ConnectionReleaseMode.AFTER_TRANSACTION;
/* 136:    */       }
/* 137:    */     }
/* 138:168 */     settings.setConnectionReleaseMode(releaseMode);
/* 139:    */     
/* 140:    */ 
/* 141:    */ 
/* 142:172 */     String defaultSchema = properties.getProperty("hibernate.default_schema");
/* 143:173 */     String defaultCatalog = properties.getProperty("hibernate.default_catalog");
/* 144:174 */     if ((defaultSchema != null) && (debugEnabled)) {
/* 145:175 */       LOG.debugf("Default schema: %s", defaultSchema);
/* 146:    */     }
/* 147:177 */     if ((defaultCatalog != null) && (debugEnabled)) {
/* 148:178 */       LOG.debugf("Default catalog: %s", defaultCatalog);
/* 149:    */     }
/* 150:180 */     settings.setDefaultSchemaName(defaultSchema);
/* 151:181 */     settings.setDefaultCatalogName(defaultCatalog);
/* 152:    */     
/* 153:183 */     Integer maxFetchDepth = ConfigurationHelper.getInteger("hibernate.max_fetch_depth", properties);
/* 154:184 */     if (maxFetchDepth != null) {
/* 155:185 */       LOG.debugf("Maximum outer join fetch depth: %s", maxFetchDepth);
/* 156:    */     }
/* 157:187 */     settings.setMaximumFetchDepth(maxFetchDepth);
/* 158:    */     
/* 159:189 */     int batchFetchSize = ConfigurationHelper.getInt("hibernate.default_batch_fetch_size", properties, 1);
/* 160:190 */     if (debugEnabled) {
/* 161:191 */       LOG.debugf("Default batch fetch size: %s", Integer.valueOf(batchFetchSize));
/* 162:    */     }
/* 163:193 */     settings.setDefaultBatchFetchSize(batchFetchSize);
/* 164:    */     
/* 165:195 */     boolean comments = ConfigurationHelper.getBoolean("hibernate.use_sql_comments", properties);
/* 166:196 */     if (debugEnabled) {
/* 167:197 */       LOG.debugf("Generate SQL with comments: %s", enabledDisabled(comments));
/* 168:    */     }
/* 169:199 */     settings.setCommentsEnabled(comments);
/* 170:    */     
/* 171:201 */     boolean orderUpdates = ConfigurationHelper.getBoolean("hibernate.order_updates", properties);
/* 172:202 */     if (debugEnabled) {
/* 173:203 */       LOG.debugf("Order SQL updates by primary key: %s", enabledDisabled(orderUpdates));
/* 174:    */     }
/* 175:205 */     settings.setOrderUpdatesEnabled(orderUpdates);
/* 176:    */     
/* 177:207 */     boolean orderInserts = ConfigurationHelper.getBoolean("hibernate.order_inserts", properties);
/* 178:208 */     if (debugEnabled) {
/* 179:209 */       LOG.debugf("Order SQL inserts for batching: %s", enabledDisabled(orderInserts));
/* 180:    */     }
/* 181:211 */     settings.setOrderInsertsEnabled(orderInserts);
/* 182:    */     
/* 183:    */ 
/* 184:    */ 
/* 185:215 */     settings.setQueryTranslatorFactory(createQueryTranslatorFactory(properties, serviceRegistry));
/* 186:    */     
/* 187:217 */     Map querySubstitutions = ConfigurationHelper.toMap("hibernate.query.substitutions", " ,=;:\n\t\r\f", properties);
/* 188:218 */     if (debugEnabled) {
/* 189:219 */       LOG.debugf("Query language substitutions: %s", querySubstitutions);
/* 190:    */     }
/* 191:221 */     settings.setQuerySubstitutions(querySubstitutions);
/* 192:    */     
/* 193:223 */     boolean jpaqlCompliance = ConfigurationHelper.getBoolean("hibernate.query.jpaql_strict_compliance", properties, false);
/* 194:224 */     if (debugEnabled) {
/* 195:225 */       LOG.debugf("JPA-QL strict compliance: %s", enabledDisabled(jpaqlCompliance));
/* 196:    */     }
/* 197:227 */     settings.setStrictJPAQLCompliance(jpaqlCompliance);
/* 198:    */     
/* 199:    */ 
/* 200:    */ 
/* 201:231 */     boolean useSecondLevelCache = ConfigurationHelper.getBoolean("hibernate.cache.use_second_level_cache", properties, true);
/* 202:232 */     if (debugEnabled) {
/* 203:233 */       LOG.debugf("Second-level cache: %s", enabledDisabled(useSecondLevelCache));
/* 204:    */     }
/* 205:235 */     settings.setSecondLevelCacheEnabled(useSecondLevelCache);
/* 206:    */     
/* 207:237 */     boolean useQueryCache = ConfigurationHelper.getBoolean("hibernate.cache.use_query_cache", properties);
/* 208:238 */     if (debugEnabled) {
/* 209:239 */       LOG.debugf("Query cache: %s", enabledDisabled(useQueryCache));
/* 210:    */     }
/* 211:241 */     settings.setQueryCacheEnabled(useQueryCache);
/* 212:242 */     if (useQueryCache) {
/* 213:243 */       settings.setQueryCacheFactory(createQueryCacheFactory(properties, serviceRegistry));
/* 214:    */     }
/* 215:248 */     settings.setRegionFactory(createRegionFactory(properties, (useSecondLevelCache) || (useQueryCache), serviceRegistry));
/* 216:    */     
/* 217:250 */     boolean useMinimalPuts = ConfigurationHelper.getBoolean("hibernate.cache.use_minimal_puts", properties, settings.getRegionFactory().isMinimalPutsEnabledByDefault());
/* 218:253 */     if (debugEnabled) {
/* 219:254 */       LOG.debugf("Optimize cache for minimal puts: %s", enabledDisabled(useMinimalPuts));
/* 220:    */     }
/* 221:256 */     settings.setMinimalPutsEnabled(useMinimalPuts);
/* 222:    */     
/* 223:258 */     String prefix = properties.getProperty("hibernate.cache.region_prefix");
/* 224:259 */     if (StringHelper.isEmpty(prefix)) {
/* 225:260 */       prefix = null;
/* 226:    */     }
/* 227:262 */     if ((prefix != null) && (debugEnabled)) {
/* 228:263 */       LOG.debugf("Cache region prefix: %s", prefix);
/* 229:    */     }
/* 230:265 */     settings.setCacheRegionPrefix(prefix);
/* 231:    */     
/* 232:267 */     boolean useStructuredCacheEntries = ConfigurationHelper.getBoolean("hibernate.cache.use_structured_entries", properties, false);
/* 233:268 */     if (debugEnabled) {
/* 234:269 */       LOG.debugf("Structured second-level cache entries: %s", enabledDisabled(useStructuredCacheEntries));
/* 235:    */     }
/* 236:271 */     settings.setStructuredCacheEntriesEnabled(useStructuredCacheEntries);
/* 237:    */     
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:276 */     boolean useStatistics = ConfigurationHelper.getBoolean("hibernate.generate_statistics", properties);
/* 242:277 */     if (debugEnabled) {
/* 243:278 */       LOG.debugf("Statistics: %s", enabledDisabled(useStatistics));
/* 244:    */     }
/* 245:280 */     settings.setStatisticsEnabled(useStatistics);
/* 246:    */     
/* 247:282 */     boolean useIdentifierRollback = ConfigurationHelper.getBoolean("hibernate.use_identifier_rollback", properties);
/* 248:283 */     if (debugEnabled) {
/* 249:284 */       LOG.debugf("Deleted entity synthetic identifier rollback: %s", enabledDisabled(useIdentifierRollback));
/* 250:    */     }
/* 251:286 */     settings.setIdentifierRollbackEnabled(useIdentifierRollback);
/* 252:    */     
/* 253:    */ 
/* 254:    */ 
/* 255:290 */     String autoSchemaExport = properties.getProperty("hibernate.hbm2ddl.auto");
/* 256:291 */     if ("validate".equals(autoSchemaExport)) {
/* 257:292 */       settings.setAutoValidateSchema(true);
/* 258:    */     }
/* 259:294 */     if ("update".equals(autoSchemaExport)) {
/* 260:295 */       settings.setAutoUpdateSchema(true);
/* 261:    */     }
/* 262:297 */     if ("create".equals(autoSchemaExport)) {
/* 263:298 */       settings.setAutoCreateSchema(true);
/* 264:    */     }
/* 265:300 */     if ("create-drop".equals(autoSchemaExport))
/* 266:    */     {
/* 267:301 */       settings.setAutoCreateSchema(true);
/* 268:302 */       settings.setAutoDropSchema(true);
/* 269:    */     }
/* 270:304 */     settings.setImportFiles(properties.getProperty("hibernate.hbm2ddl.import_files"));
/* 271:    */     
/* 272:306 */     EntityMode defaultEntityMode = EntityMode.parse(properties.getProperty("hibernate.default_entity_mode"));
/* 273:307 */     if (debugEnabled) {
/* 274:308 */       LOG.debugf("Default entity-mode: %s", defaultEntityMode);
/* 275:    */     }
/* 276:310 */     settings.setDefaultEntityMode(defaultEntityMode);
/* 277:    */     
/* 278:312 */     boolean namedQueryChecking = ConfigurationHelper.getBoolean("hibernate.query.startup_check", properties, true);
/* 279:313 */     if (debugEnabled) {
/* 280:314 */       LOG.debugf("Named query checking : %s", enabledDisabled(namedQueryChecking));
/* 281:    */     }
/* 282:316 */     settings.setNamedQueryStartupCheckingEnabled(namedQueryChecking);
/* 283:    */     
/* 284:318 */     boolean checkNullability = ConfigurationHelper.getBoolean("hibernate.check_nullability", properties, true);
/* 285:319 */     if (debugEnabled) {
/* 286:320 */       LOG.debugf("Check Nullability in Core (should be disabled when Bean Validation is on): %s", enabledDisabled(checkNullability));
/* 287:    */     }
/* 288:322 */     settings.setCheckNullability(checkNullability);
/* 289:    */     
/* 290:324 */     MultiTenancyStrategy multiTenancyStrategy = MultiTenancyStrategy.determineMultiTenancyStrategy(properties);
/* 291:325 */     if (debugEnabled) {
/* 292:326 */       LOG.debugf("multi-tenancy strategy : %s", multiTenancyStrategy);
/* 293:    */     }
/* 294:328 */     settings.setMultiTenancyStrategy(multiTenancyStrategy);
/* 295:    */     
/* 296:    */ 
/* 297:331 */     settings.setEntityTuplizerFactory(new EntityTuplizerFactory());
/* 298:    */     
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:338 */     return settings;
/* 305:    */   }
/* 306:    */   
/* 307:    */   private static String enabledDisabled(boolean value)
/* 308:    */   {
/* 309:353 */     return value ? "enabled" : "disabled";
/* 310:    */   }
/* 311:    */   
/* 312:    */   protected QueryCacheFactory createQueryCacheFactory(Properties properties, ServiceRegistry serviceRegistry)
/* 313:    */   {
/* 314:357 */     String queryCacheFactoryClassName = ConfigurationHelper.getString("hibernate.cache.query_cache_factory", properties, StandardQueryCacheFactory.class.getName());
/* 315:    */     
/* 316:    */ 
/* 317:360 */     LOG.debugf("Query cache factory: %s", queryCacheFactoryClassName);
/* 318:    */     try
/* 319:    */     {
/* 320:362 */       return (QueryCacheFactory)((ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class)).classForName(queryCacheFactoryClassName).newInstance();
/* 321:    */     }
/* 322:    */     catch (Exception e)
/* 323:    */     {
/* 324:367 */       throw new HibernateException("could not instantiate QueryCacheFactory: " + queryCacheFactoryClassName, e);
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   private static RegionFactory createRegionFactory(Properties properties, boolean cachingEnabled, ServiceRegistry serviceRegistry)
/* 329:    */   {
/* 330:372 */     String regionFactoryClassName = RegionFactoryInitiator.mapLegacyNames(ConfigurationHelper.getString("hibernate.cache.region.factory_class", properties, null));
/* 331:377 */     if ((regionFactoryClassName == null) || (!cachingEnabled)) {
/* 332:378 */       regionFactoryClassName = DEF_CACHE_REG_FACTORY;
/* 333:    */     }
/* 334:380 */     LOG.debugf("Cache region factory : %s", regionFactoryClassName);
/* 335:    */     try
/* 336:    */     {
/* 337:383 */       return (RegionFactory)((ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class)).classForName(regionFactoryClassName).getConstructor(new Class[] { Properties.class }).newInstance(new Object[] { properties });
/* 338:    */     }
/* 339:    */     catch (NoSuchMethodException e)
/* 340:    */     {
/* 341:390 */       LOG.debugf("%s did not provide constructor accepting java.util.Properties; attempting no-arg constructor.", regionFactoryClassName);
/* 342:    */       
/* 343:    */ 
/* 344:    */ 
/* 345:394 */       return (RegionFactory)((ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class)).classForName(regionFactoryClassName).newInstance();
/* 346:    */     }
/* 347:    */     catch (Exception e)
/* 348:    */     {
/* 349:400 */       throw new HibernateException("could not instantiate RegionFactory [" + regionFactoryClassName + "]", e);
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   public static RegionFactory createRegionFactory(Properties properties, boolean cachingEnabled)
/* 354:    */   {
/* 355:406 */     String regionFactoryClassName = RegionFactoryInitiator.mapLegacyNames(ConfigurationHelper.getString("hibernate.cache.region.factory_class", properties, null));
/* 356:411 */     if (regionFactoryClassName == null) {
/* 357:412 */       regionFactoryClassName = DEF_CACHE_REG_FACTORY;
/* 358:    */     }
/* 359:414 */     LOG.debugf("Cache region factory : %s", regionFactoryClassName);
/* 360:    */     try
/* 361:    */     {
/* 362:417 */       return (RegionFactory)ReflectHelper.classForName(regionFactoryClassName).getConstructor(new Class[] { Properties.class }).newInstance(new Object[] { properties });
/* 363:    */     }
/* 364:    */     catch (NoSuchMethodException e)
/* 365:    */     {
/* 366:423 */       LOG.debugf("%s did not provide constructor accepting java.util.Properties; attempting no-arg constructor.", regionFactoryClassName);
/* 367:    */       
/* 368:    */ 
/* 369:    */ 
/* 370:427 */       return (RegionFactory)ReflectHelper.classForName(regionFactoryClassName).newInstance();
/* 371:    */     }
/* 372:    */     catch (Exception e)
/* 373:    */     {
/* 374:432 */       throw new HibernateException("could not instantiate RegionFactory [" + regionFactoryClassName + "]", e);
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   protected QueryTranslatorFactory createQueryTranslatorFactory(Properties properties, ServiceRegistry serviceRegistry)
/* 379:    */   {
/* 380:437 */     String className = ConfigurationHelper.getString("hibernate.query.factory_class", properties, "org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory");
/* 381:    */     
/* 382:    */ 
/* 383:440 */     LOG.debugf("Query translator: %s", className);
/* 384:    */     try
/* 385:    */     {
/* 386:442 */       return (QueryTranslatorFactory)((ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class)).classForName(className).newInstance();
/* 387:    */     }
/* 388:    */     catch (Exception e)
/* 389:    */     {
/* 390:447 */       throw new HibernateException("could not instantiate QueryTranslatorFactory: " + className, e);
/* 391:    */     }
/* 392:    */   }
/* 393:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.SettingsFactory
 * JD-Core Version:    0.7.0.1
 */