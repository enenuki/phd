/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.ConnectionReleaseMode;
/*   5:    */ import org.hibernate.EntityMode;
/*   6:    */ import org.hibernate.MultiTenancyStrategy;
/*   7:    */ import org.hibernate.cache.spi.QueryCacheFactory;
/*   8:    */ import org.hibernate.cache.spi.RegionFactory;
/*   9:    */ import org.hibernate.hql.spi.QueryTranslatorFactory;
/*  10:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  11:    */ import org.hibernate.tuple.entity.EntityTuplizerFactory;
/*  12:    */ 
/*  13:    */ public final class Settings
/*  14:    */ {
/*  15:    */   private Integer maximumFetchDepth;
/*  16:    */   private Map querySubstitutions;
/*  17:    */   private int jdbcBatchSize;
/*  18:    */   private int defaultBatchFetchSize;
/*  19:    */   private boolean scrollableResultSetsEnabled;
/*  20:    */   private boolean getGeneratedKeysEnabled;
/*  21:    */   private String defaultSchemaName;
/*  22:    */   private String defaultCatalogName;
/*  23:    */   private Integer jdbcFetchSize;
/*  24:    */   private String sessionFactoryName;
/*  25:    */   private boolean autoCreateSchema;
/*  26:    */   private boolean autoDropSchema;
/*  27:    */   private boolean autoUpdateSchema;
/*  28:    */   private boolean autoValidateSchema;
/*  29:    */   private boolean queryCacheEnabled;
/*  30:    */   private boolean structuredCacheEntriesEnabled;
/*  31:    */   private boolean secondLevelCacheEnabled;
/*  32:    */   private String cacheRegionPrefix;
/*  33:    */   private boolean minimalPutsEnabled;
/*  34:    */   private boolean commentsEnabled;
/*  35:    */   private boolean statisticsEnabled;
/*  36:    */   private boolean jdbcBatchVersionedData;
/*  37:    */   private boolean identifierRollbackEnabled;
/*  38:    */   private boolean flushBeforeCompletionEnabled;
/*  39:    */   private boolean autoCloseSessionEnabled;
/*  40:    */   private ConnectionReleaseMode connectionReleaseMode;
/*  41:    */   private RegionFactory regionFactory;
/*  42:    */   private QueryCacheFactory queryCacheFactory;
/*  43:    */   private QueryTranslatorFactory queryTranslatorFactory;
/*  44:    */   private boolean wrapResultSetsEnabled;
/*  45:    */   private boolean orderUpdatesEnabled;
/*  46:    */   private boolean orderInsertsEnabled;
/*  47:    */   private EntityMode defaultEntityMode;
/*  48:    */   private boolean dataDefinitionImplicitCommit;
/*  49:    */   private boolean dataDefinitionInTransactionSupported;
/*  50:    */   private boolean strictJPAQLCompliance;
/*  51:    */   private boolean namedQueryStartupCheckingEnabled;
/*  52:    */   private EntityTuplizerFactory entityTuplizerFactory;
/*  53:    */   private boolean checkNullability;
/*  54:    */   private String importFiles;
/*  55:    */   private MultiTenancyStrategy multiTenancyStrategy;
/*  56:    */   private JtaPlatform jtaPlatform;
/*  57:    */   
/*  58:    */   public String getImportFiles()
/*  59:    */   {
/*  60: 99 */     return this.importFiles;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setImportFiles(String importFiles)
/*  64:    */   {
/*  65:103 */     this.importFiles = importFiles;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getDefaultSchemaName()
/*  69:    */   {
/*  70:107 */     return this.defaultSchemaName;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getDefaultCatalogName()
/*  74:    */   {
/*  75:111 */     return this.defaultCatalogName;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getJdbcBatchSize()
/*  79:    */   {
/*  80:115 */     return this.jdbcBatchSize;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getDefaultBatchFetchSize()
/*  84:    */   {
/*  85:119 */     return this.defaultBatchFetchSize;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Map getQuerySubstitutions()
/*  89:    */   {
/*  90:123 */     return this.querySubstitutions;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isIdentifierRollbackEnabled()
/*  94:    */   {
/*  95:127 */     return this.identifierRollbackEnabled;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isScrollableResultSetsEnabled()
/*  99:    */   {
/* 100:131 */     return this.scrollableResultSetsEnabled;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isGetGeneratedKeysEnabled()
/* 104:    */   {
/* 105:135 */     return this.getGeneratedKeysEnabled;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isMinimalPutsEnabled()
/* 109:    */   {
/* 110:139 */     return this.minimalPutsEnabled;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Integer getJdbcFetchSize()
/* 114:    */   {
/* 115:143 */     return this.jdbcFetchSize;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getSessionFactoryName()
/* 119:    */   {
/* 120:147 */     return this.sessionFactoryName;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isAutoCreateSchema()
/* 124:    */   {
/* 125:151 */     return this.autoCreateSchema;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isAutoDropSchema()
/* 129:    */   {
/* 130:155 */     return this.autoDropSchema;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isAutoUpdateSchema()
/* 134:    */   {
/* 135:159 */     return this.autoUpdateSchema;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Integer getMaximumFetchDepth()
/* 139:    */   {
/* 140:163 */     return this.maximumFetchDepth;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public RegionFactory getRegionFactory()
/* 144:    */   {
/* 145:167 */     return this.regionFactory;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isQueryCacheEnabled()
/* 149:    */   {
/* 150:171 */     return this.queryCacheEnabled;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isCommentsEnabled()
/* 154:    */   {
/* 155:175 */     return this.commentsEnabled;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isSecondLevelCacheEnabled()
/* 159:    */   {
/* 160:179 */     return this.secondLevelCacheEnabled;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getCacheRegionPrefix()
/* 164:    */   {
/* 165:183 */     return this.cacheRegionPrefix;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public QueryCacheFactory getQueryCacheFactory()
/* 169:    */   {
/* 170:187 */     return this.queryCacheFactory;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean isStatisticsEnabled()
/* 174:    */   {
/* 175:191 */     return this.statisticsEnabled;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isJdbcBatchVersionedData()
/* 179:    */   {
/* 180:195 */     return this.jdbcBatchVersionedData;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean isFlushBeforeCompletionEnabled()
/* 184:    */   {
/* 185:199 */     return this.flushBeforeCompletionEnabled;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean isAutoCloseSessionEnabled()
/* 189:    */   {
/* 190:203 */     return this.autoCloseSessionEnabled;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public ConnectionReleaseMode getConnectionReleaseMode()
/* 194:    */   {
/* 195:207 */     return this.connectionReleaseMode;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public QueryTranslatorFactory getQueryTranslatorFactory()
/* 199:    */   {
/* 200:211 */     return this.queryTranslatorFactory;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean isWrapResultSetsEnabled()
/* 204:    */   {
/* 205:215 */     return this.wrapResultSetsEnabled;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isOrderUpdatesEnabled()
/* 209:    */   {
/* 210:219 */     return this.orderUpdatesEnabled;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public boolean isOrderInsertsEnabled()
/* 214:    */   {
/* 215:223 */     return this.orderInsertsEnabled;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isStructuredCacheEntriesEnabled()
/* 219:    */   {
/* 220:227 */     return this.structuredCacheEntriesEnabled;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public EntityMode getDefaultEntityMode()
/* 224:    */   {
/* 225:231 */     return this.defaultEntityMode;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isAutoValidateSchema()
/* 229:    */   {
/* 230:235 */     return this.autoValidateSchema;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isDataDefinitionImplicitCommit()
/* 234:    */   {
/* 235:239 */     return this.dataDefinitionImplicitCommit;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean isDataDefinitionInTransactionSupported()
/* 239:    */   {
/* 240:243 */     return this.dataDefinitionInTransactionSupported;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean isStrictJPAQLCompliance()
/* 244:    */   {
/* 245:247 */     return this.strictJPAQLCompliance;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public boolean isNamedQueryStartupCheckingEnabled()
/* 249:    */   {
/* 250:251 */     return this.namedQueryStartupCheckingEnabled;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public EntityTuplizerFactory getEntityTuplizerFactory()
/* 254:    */   {
/* 255:255 */     return this.entityTuplizerFactory;
/* 256:    */   }
/* 257:    */   
/* 258:    */   void setDefaultSchemaName(String string)
/* 259:    */   {
/* 260:265 */     this.defaultSchemaName = string;
/* 261:    */   }
/* 262:    */   
/* 263:    */   void setDefaultCatalogName(String string)
/* 264:    */   {
/* 265:269 */     this.defaultCatalogName = string;
/* 266:    */   }
/* 267:    */   
/* 268:    */   void setJdbcBatchSize(int i)
/* 269:    */   {
/* 270:273 */     this.jdbcBatchSize = i;
/* 271:    */   }
/* 272:    */   
/* 273:    */   void setDefaultBatchFetchSize(int i)
/* 274:    */   {
/* 275:277 */     this.defaultBatchFetchSize = i;
/* 276:    */   }
/* 277:    */   
/* 278:    */   void setQuerySubstitutions(Map map)
/* 279:    */   {
/* 280:281 */     this.querySubstitutions = map;
/* 281:    */   }
/* 282:    */   
/* 283:    */   void setIdentifierRollbackEnabled(boolean b)
/* 284:    */   {
/* 285:285 */     this.identifierRollbackEnabled = b;
/* 286:    */   }
/* 287:    */   
/* 288:    */   void setMinimalPutsEnabled(boolean b)
/* 289:    */   {
/* 290:289 */     this.minimalPutsEnabled = b;
/* 291:    */   }
/* 292:    */   
/* 293:    */   void setScrollableResultSetsEnabled(boolean b)
/* 294:    */   {
/* 295:293 */     this.scrollableResultSetsEnabled = b;
/* 296:    */   }
/* 297:    */   
/* 298:    */   void setGetGeneratedKeysEnabled(boolean b)
/* 299:    */   {
/* 300:297 */     this.getGeneratedKeysEnabled = b;
/* 301:    */   }
/* 302:    */   
/* 303:    */   void setJdbcFetchSize(Integer integer)
/* 304:    */   {
/* 305:301 */     this.jdbcFetchSize = integer;
/* 306:    */   }
/* 307:    */   
/* 308:    */   void setSessionFactoryName(String string)
/* 309:    */   {
/* 310:305 */     this.sessionFactoryName = string;
/* 311:    */   }
/* 312:    */   
/* 313:    */   void setAutoCreateSchema(boolean b)
/* 314:    */   {
/* 315:309 */     this.autoCreateSchema = b;
/* 316:    */   }
/* 317:    */   
/* 318:    */   void setAutoDropSchema(boolean b)
/* 319:    */   {
/* 320:313 */     this.autoDropSchema = b;
/* 321:    */   }
/* 322:    */   
/* 323:    */   void setAutoUpdateSchema(boolean b)
/* 324:    */   {
/* 325:317 */     this.autoUpdateSchema = b;
/* 326:    */   }
/* 327:    */   
/* 328:    */   void setMaximumFetchDepth(Integer i)
/* 329:    */   {
/* 330:321 */     this.maximumFetchDepth = i;
/* 331:    */   }
/* 332:    */   
/* 333:    */   void setRegionFactory(RegionFactory regionFactory)
/* 334:    */   {
/* 335:325 */     this.regionFactory = regionFactory;
/* 336:    */   }
/* 337:    */   
/* 338:    */   void setQueryCacheEnabled(boolean b)
/* 339:    */   {
/* 340:329 */     this.queryCacheEnabled = b;
/* 341:    */   }
/* 342:    */   
/* 343:    */   void setCommentsEnabled(boolean commentsEnabled)
/* 344:    */   {
/* 345:333 */     this.commentsEnabled = commentsEnabled;
/* 346:    */   }
/* 347:    */   
/* 348:    */   void setSecondLevelCacheEnabled(boolean secondLevelCacheEnabled)
/* 349:    */   {
/* 350:337 */     this.secondLevelCacheEnabled = secondLevelCacheEnabled;
/* 351:    */   }
/* 352:    */   
/* 353:    */   void setCacheRegionPrefix(String cacheRegionPrefix)
/* 354:    */   {
/* 355:341 */     this.cacheRegionPrefix = cacheRegionPrefix;
/* 356:    */   }
/* 357:    */   
/* 358:    */   void setQueryCacheFactory(QueryCacheFactory queryCacheFactory)
/* 359:    */   {
/* 360:345 */     this.queryCacheFactory = queryCacheFactory;
/* 361:    */   }
/* 362:    */   
/* 363:    */   void setStatisticsEnabled(boolean statisticsEnabled)
/* 364:    */   {
/* 365:349 */     this.statisticsEnabled = statisticsEnabled;
/* 366:    */   }
/* 367:    */   
/* 368:    */   void setJdbcBatchVersionedData(boolean jdbcBatchVersionedData)
/* 369:    */   {
/* 370:353 */     this.jdbcBatchVersionedData = jdbcBatchVersionedData;
/* 371:    */   }
/* 372:    */   
/* 373:    */   void setFlushBeforeCompletionEnabled(boolean flushBeforeCompletionEnabled)
/* 374:    */   {
/* 375:357 */     this.flushBeforeCompletionEnabled = flushBeforeCompletionEnabled;
/* 376:    */   }
/* 377:    */   
/* 378:    */   void setAutoCloseSessionEnabled(boolean autoCloseSessionEnabled)
/* 379:    */   {
/* 380:361 */     this.autoCloseSessionEnabled = autoCloseSessionEnabled;
/* 381:    */   }
/* 382:    */   
/* 383:    */   void setConnectionReleaseMode(ConnectionReleaseMode connectionReleaseMode)
/* 384:    */   {
/* 385:365 */     this.connectionReleaseMode = connectionReleaseMode;
/* 386:    */   }
/* 387:    */   
/* 388:    */   void setQueryTranslatorFactory(QueryTranslatorFactory queryTranslatorFactory)
/* 389:    */   {
/* 390:369 */     this.queryTranslatorFactory = queryTranslatorFactory;
/* 391:    */   }
/* 392:    */   
/* 393:    */   void setWrapResultSetsEnabled(boolean wrapResultSetsEnabled)
/* 394:    */   {
/* 395:373 */     this.wrapResultSetsEnabled = wrapResultSetsEnabled;
/* 396:    */   }
/* 397:    */   
/* 398:    */   void setOrderUpdatesEnabled(boolean orderUpdatesEnabled)
/* 399:    */   {
/* 400:377 */     this.orderUpdatesEnabled = orderUpdatesEnabled;
/* 401:    */   }
/* 402:    */   
/* 403:    */   void setOrderInsertsEnabled(boolean orderInsertsEnabled)
/* 404:    */   {
/* 405:381 */     this.orderInsertsEnabled = orderInsertsEnabled;
/* 406:    */   }
/* 407:    */   
/* 408:    */   void setStructuredCacheEntriesEnabled(boolean structuredCacheEntriesEnabled)
/* 409:    */   {
/* 410:385 */     this.structuredCacheEntriesEnabled = structuredCacheEntriesEnabled;
/* 411:    */   }
/* 412:    */   
/* 413:    */   void setDefaultEntityMode(EntityMode defaultEntityMode)
/* 414:    */   {
/* 415:389 */     this.defaultEntityMode = defaultEntityMode;
/* 416:    */   }
/* 417:    */   
/* 418:    */   void setAutoValidateSchema(boolean autoValidateSchema)
/* 419:    */   {
/* 420:393 */     this.autoValidateSchema = autoValidateSchema;
/* 421:    */   }
/* 422:    */   
/* 423:    */   void setDataDefinitionImplicitCommit(boolean dataDefinitionImplicitCommit)
/* 424:    */   {
/* 425:397 */     this.dataDefinitionImplicitCommit = dataDefinitionImplicitCommit;
/* 426:    */   }
/* 427:    */   
/* 428:    */   void setDataDefinitionInTransactionSupported(boolean dataDefinitionInTransactionSupported)
/* 429:    */   {
/* 430:401 */     this.dataDefinitionInTransactionSupported = dataDefinitionInTransactionSupported;
/* 431:    */   }
/* 432:    */   
/* 433:    */   void setStrictJPAQLCompliance(boolean strictJPAQLCompliance)
/* 434:    */   {
/* 435:405 */     this.strictJPAQLCompliance = strictJPAQLCompliance;
/* 436:    */   }
/* 437:    */   
/* 438:    */   void setNamedQueryStartupCheckingEnabled(boolean namedQueryStartupCheckingEnabled)
/* 439:    */   {
/* 440:409 */     this.namedQueryStartupCheckingEnabled = namedQueryStartupCheckingEnabled;
/* 441:    */   }
/* 442:    */   
/* 443:    */   void setEntityTuplizerFactory(EntityTuplizerFactory entityTuplizerFactory)
/* 444:    */   {
/* 445:413 */     this.entityTuplizerFactory = entityTuplizerFactory;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public boolean isCheckNullability()
/* 449:    */   {
/* 450:417 */     return this.checkNullability;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void setCheckNullability(boolean checkNullability)
/* 454:    */   {
/* 455:421 */     this.checkNullability = checkNullability;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public JtaPlatform getJtaPlatform()
/* 459:    */   {
/* 460:438 */     return this.jtaPlatform;
/* 461:    */   }
/* 462:    */   
/* 463:    */   void setJtaPlatform(JtaPlatform jtaPlatform)
/* 464:    */   {
/* 465:442 */     this.jtaPlatform = jtaPlatform;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public MultiTenancyStrategy getMultiTenancyStrategy()
/* 469:    */   {
/* 470:446 */     return this.multiTenancyStrategy;
/* 471:    */   }
/* 472:    */   
/* 473:    */   void setMultiTenancyStrategy(MultiTenancyStrategy multiTenancyStrategy)
/* 474:    */   {
/* 475:450 */     this.multiTenancyStrategy = multiTenancyStrategy;
/* 476:    */   }
/* 477:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.Settings
 * JD-Core Version:    0.7.0.1
 */