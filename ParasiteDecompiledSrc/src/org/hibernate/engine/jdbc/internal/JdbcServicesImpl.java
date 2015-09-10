/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.sql.Connection;
/*   6:    */ import java.sql.DatabaseMetaData;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.LinkedHashSet;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.hibernate.MultiTenancyStrategy;
/*  14:    */ import org.hibernate.dialect.Dialect;
/*  15:    */ import org.hibernate.engine.jdbc.LobCreationContext;
/*  16:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  17:    */ import org.hibernate.engine.jdbc.spi.ExtractedDatabaseMetaData;
/*  18:    */ import org.hibernate.engine.jdbc.spi.ExtractedDatabaseMetaData.SQLStateType;
/*  19:    */ import org.hibernate.engine.jdbc.spi.JdbcConnectionAccess;
/*  20:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  21:    */ import org.hibernate.engine.jdbc.spi.ResultSetWrapper;
/*  22:    */ import org.hibernate.engine.jdbc.spi.SchemaNameResolver;
/*  23:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  24:    */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  25:    */ import org.hibernate.internal.CoreMessageLogger;
/*  26:    */ import org.hibernate.internal.util.ReflectHelper;
/*  27:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  28:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  29:    */ import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;
/*  30:    */ import org.hibernate.service.jdbc.dialect.spi.DialectFactory;
/*  31:    */ import org.hibernate.service.spi.Configurable;
/*  32:    */ import org.hibernate.service.spi.ServiceRegistryAwareService;
/*  33:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  34:    */ import org.jboss.logging.Logger;
/*  35:    */ 
/*  36:    */ public class JdbcServicesImpl
/*  37:    */   implements JdbcServices, ServiceRegistryAwareService, Configurable
/*  38:    */ {
/*  39: 67 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JdbcServicesImpl.class.getName());
/*  40:    */   private ServiceRegistryImplementor serviceRegistry;
/*  41:    */   private Dialect dialect;
/*  42:    */   private ConnectionProvider connectionProvider;
/*  43:    */   private SqlStatementLogger sqlStatementLogger;
/*  44:    */   private SqlExceptionHelper sqlExceptionHelper;
/*  45:    */   private ExtractedDatabaseMetaData extractedMetaDataSupport;
/*  46:    */   private LobCreatorBuilder lobCreatorBuilder;
/*  47:    */   public static final String SCHEMA_NAME_RESOLVER = "hibernate.schema_name_resolver";
/*  48:    */   
/*  49:    */   public void injectServices(ServiceRegistryImplementor serviceRegistry)
/*  50:    */   {
/*  51: 80 */     this.serviceRegistry = serviceRegistry;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void configure(Map configValues)
/*  55:    */   {
/*  56: 85 */     JdbcConnectionAccess jdbcConnectionAccess = buildJdbcConnectionAccess(configValues);
/*  57: 86 */     DialectFactory dialectFactory = (DialectFactory)this.serviceRegistry.getService(DialectFactory.class);
/*  58:    */     
/*  59: 88 */     Dialect dialect = null;
/*  60: 89 */     LobCreatorBuilder lobCreatorBuilder = null;
/*  61:    */     
/*  62: 91 */     boolean metaSupportsScrollable = false;
/*  63: 92 */     boolean metaSupportsGetGeneratedKeys = false;
/*  64: 93 */     boolean metaSupportsBatchUpdates = false;
/*  65: 94 */     boolean metaReportsDDLCausesTxnCommit = false;
/*  66: 95 */     boolean metaReportsDDLInTxnSupported = true;
/*  67: 96 */     String extraKeywordsString = "";
/*  68: 97 */     int sqlStateType = -1;
/*  69: 98 */     boolean lobLocatorUpdateCopy = false;
/*  70: 99 */     String catalogName = null;
/*  71:100 */     String schemaName = null;
/*  72:101 */     LinkedHashSet<TypeInfo> typeInfoSet = new LinkedHashSet();
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:110 */     boolean useJdbcMetadata = ConfigurationHelper.getBoolean("hibernate.temp.use_jdbc_metadata_defaults", configValues, true);
/*  82:111 */     if (useJdbcMetadata) {
/*  83:    */       try
/*  84:    */       {
/*  85:113 */         Connection connection = jdbcConnectionAccess.obtainConnection();
/*  86:    */         try
/*  87:    */         {
/*  88:115 */           DatabaseMetaData meta = connection.getMetaData();
/*  89:116 */           if (LOG.isDebugEnabled())
/*  90:    */           {
/*  91:117 */             LOG.debugf("Database ->\n       name : %s\n    version : %s\n      major : %s\n      minor : %s", new Object[] { meta.getDatabaseProductName(), meta.getDatabaseProductVersion(), Integer.valueOf(meta.getDatabaseMajorVersion()), Integer.valueOf(meta.getDatabaseMinorVersion()) });
/*  92:    */             
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:123 */             LOG.debugf("Driver ->\n       name : %s\n    version : %s\n      major : %s\n      minor : %s", new Object[] { meta.getDriverName(), meta.getDriverVersion(), Integer.valueOf(meta.getDriverMajorVersion()), Integer.valueOf(meta.getDriverMinorVersion()) });
/*  98:    */             
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:129 */             LOG.debugf("JDBC version : %s.%s", Integer.valueOf(meta.getJDBCMajorVersion()), Integer.valueOf(meta.getJDBCMinorVersion()));
/* 104:    */           }
/* 105:132 */           metaSupportsScrollable = meta.supportsResultSetType(1004);
/* 106:133 */           metaSupportsBatchUpdates = meta.supportsBatchUpdates();
/* 107:134 */           metaReportsDDLCausesTxnCommit = meta.dataDefinitionCausesTransactionCommit();
/* 108:135 */           metaReportsDDLInTxnSupported = !meta.dataDefinitionIgnoredInTransactions();
/* 109:136 */           metaSupportsGetGeneratedKeys = meta.supportsGetGeneratedKeys();
/* 110:137 */           extraKeywordsString = meta.getSQLKeywords();
/* 111:138 */           sqlStateType = meta.getSQLStateType();
/* 112:139 */           lobLocatorUpdateCopy = meta.locatorsUpdateCopy();
/* 113:140 */           typeInfoSet.addAll(TypeInfoExtracter.extractTypeInfo(meta));
/* 114:    */           
/* 115:142 */           dialect = dialectFactory.buildDialect(configValues, connection);
/* 116:    */           
/* 117:144 */           catalogName = connection.getCatalog();
/* 118:145 */           SchemaNameResolver schemaNameResolver = determineExplicitSchemaNameResolver(configValues);
/* 119:146 */           if ((schemaNameResolver != null) || 
/* 120:    */           
/* 121:    */ 
/* 122:    */ 
/* 123:150 */             (schemaNameResolver != null)) {
/* 124:151 */             schemaName = schemaNameResolver.resolveSchemaName(connection);
/* 125:    */           }
/* 126:153 */           lobCreatorBuilder = new LobCreatorBuilder(configValues, connection);
/* 127:    */         }
/* 128:    */         catch (SQLException sqle)
/* 129:    */         {
/* 130:156 */           LOG.unableToObtainConnectionMetadata(sqle.getMessage());
/* 131:    */         }
/* 132:    */         finally
/* 133:    */         {
/* 134:159 */           if (connection != null) {
/* 135:160 */             jdbcConnectionAccess.releaseConnection(connection);
/* 136:    */           }
/* 137:    */         }
/* 138:    */       }
/* 139:    */       catch (SQLException sqle)
/* 140:    */       {
/* 141:165 */         LOG.unableToObtainConnectionToQueryMetadata(sqle.getMessage());
/* 142:166 */         dialect = dialectFactory.buildDialect(configValues, null);
/* 143:    */       }
/* 144:    */       catch (UnsupportedOperationException uoe)
/* 145:    */       {
/* 146:170 */         dialect = dialectFactory.buildDialect(configValues, null);
/* 147:    */       }
/* 148:    */     } else {
/* 149:174 */       dialect = dialectFactory.buildDialect(configValues, null);
/* 150:    */     }
/* 151:177 */     boolean showSQL = ConfigurationHelper.getBoolean("hibernate.show_sql", configValues, false);
/* 152:178 */     boolean formatSQL = ConfigurationHelper.getBoolean("hibernate.format_sql", configValues, false);
/* 153:    */     
/* 154:180 */     this.dialect = dialect;
/* 155:181 */     this.lobCreatorBuilder = (lobCreatorBuilder == null ? new LobCreatorBuilder(configValues, null) : lobCreatorBuilder);
/* 156:    */     
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:187 */     this.sqlStatementLogger = new SqlStatementLogger(showSQL, formatSQL);
/* 162:188 */     this.sqlExceptionHelper = new SqlExceptionHelper(dialect.buildSQLExceptionConverter());
/* 163:189 */     this.extractedMetaDataSupport = new ExtractedDatabaseMetaDataImpl(metaSupportsScrollable, metaSupportsGetGeneratedKeys, metaSupportsBatchUpdates, metaReportsDDLInTxnSupported, metaReportsDDLCausesTxnCommit, parseKeywords(extraKeywordsString), parseSQLStateType(sqlStateType), lobLocatorUpdateCopy, schemaName, catalogName, typeInfoSet, null);
/* 164:    */   }
/* 165:    */   
/* 166:    */   private JdbcConnectionAccess buildJdbcConnectionAccess(Map configValues)
/* 167:    */   {
/* 168:205 */     MultiTenancyStrategy multiTenancyStrategy = MultiTenancyStrategy.determineMultiTenancyStrategy(configValues);
/* 169:207 */     if (MultiTenancyStrategy.NONE == multiTenancyStrategy)
/* 170:    */     {
/* 171:208 */       this.connectionProvider = ((ConnectionProvider)this.serviceRegistry.getService(ConnectionProvider.class));
/* 172:209 */       return new ConnectionProviderJdbcConnectionAccess(this.connectionProvider);
/* 173:    */     }
/* 174:212 */     this.connectionProvider = null;
/* 175:213 */     MultiTenantConnectionProvider multiTenantConnectionProvider = (MultiTenantConnectionProvider)this.serviceRegistry.getService(MultiTenantConnectionProvider.class);
/* 176:214 */     return new MultiTenantConnectionProviderJdbcConnectionAccess(multiTenantConnectionProvider);
/* 177:    */   }
/* 178:    */   
/* 179:    */   private static class ConnectionProviderJdbcConnectionAccess
/* 180:    */     implements JdbcConnectionAccess
/* 181:    */   {
/* 182:    */     private final ConnectionProvider connectionProvider;
/* 183:    */     
/* 184:    */     public ConnectionProviderJdbcConnectionAccess(ConnectionProvider connectionProvider)
/* 185:    */     {
/* 186:222 */       this.connectionProvider = connectionProvider;
/* 187:    */     }
/* 188:    */     
/* 189:    */     public Connection obtainConnection()
/* 190:    */       throws SQLException
/* 191:    */     {
/* 192:227 */       return this.connectionProvider.getConnection();
/* 193:    */     }
/* 194:    */     
/* 195:    */     public void releaseConnection(Connection connection)
/* 196:    */       throws SQLException
/* 197:    */     {
/* 198:232 */       this.connectionProvider.closeConnection(connection);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   private static class MultiTenantConnectionProviderJdbcConnectionAccess
/* 203:    */     implements JdbcConnectionAccess
/* 204:    */   {
/* 205:    */     private final MultiTenantConnectionProvider connectionProvider;
/* 206:    */     
/* 207:    */     public MultiTenantConnectionProviderJdbcConnectionAccess(MultiTenantConnectionProvider connectionProvider)
/* 208:    */     {
/* 209:240 */       this.connectionProvider = connectionProvider;
/* 210:    */     }
/* 211:    */     
/* 212:    */     public Connection obtainConnection()
/* 213:    */       throws SQLException
/* 214:    */     {
/* 215:245 */       return this.connectionProvider.getAnyConnection();
/* 216:    */     }
/* 217:    */     
/* 218:    */     public void releaseConnection(Connection connection)
/* 219:    */       throws SQLException
/* 220:    */     {
/* 221:250 */       this.connectionProvider.releaseAnyConnection(connection);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   private SchemaNameResolver determineExplicitSchemaNameResolver(Map configValues)
/* 226:    */   {
/* 227:259 */     Object setting = configValues.get("hibernate.schema_name_resolver");
/* 228:260 */     if (SchemaNameResolver.class.isInstance(setting)) {
/* 229:261 */       return (SchemaNameResolver)setting;
/* 230:    */     }
/* 231:264 */     String resolverClassName = (String)setting;
/* 232:265 */     if (resolverClassName != null) {
/* 233:    */       try
/* 234:    */       {
/* 235:267 */         Class resolverClass = ReflectHelper.classForName(resolverClassName, getClass());
/* 236:268 */         return (SchemaNameResolver)ReflectHelper.getDefaultConstructor(resolverClass).newInstance(new Object[0]);
/* 237:    */       }
/* 238:    */       catch (ClassNotFoundException e)
/* 239:    */       {
/* 240:271 */         LOG.unableToLocateConfiguredSchemaNameResolver(resolverClassName, e.toString());
/* 241:    */       }
/* 242:    */       catch (InvocationTargetException e)
/* 243:    */       {
/* 244:274 */         LOG.unableToInstantiateConfiguredSchemaNameResolver(resolverClassName, e.getTargetException().toString());
/* 245:    */       }
/* 246:    */       catch (Exception e)
/* 247:    */       {
/* 248:277 */         LOG.unableToInstantiateConfiguredSchemaNameResolver(resolverClassName, e.toString());
/* 249:    */       }
/* 250:    */     }
/* 251:280 */     return null;
/* 252:    */   }
/* 253:    */   
/* 254:    */   private Set<String> parseKeywords(String extraKeywordsString)
/* 255:    */   {
/* 256:284 */     Set<String> keywordSet = new HashSet();
/* 257:285 */     keywordSet.addAll(Arrays.asList(extraKeywordsString.split(",")));
/* 258:286 */     return keywordSet;
/* 259:    */   }
/* 260:    */   
/* 261:    */   private ExtractedDatabaseMetaData.SQLStateType parseSQLStateType(int sqlStateType)
/* 262:    */   {
/* 263:290 */     switch (sqlStateType)
/* 264:    */     {
/* 265:    */     case 2: 
/* 266:292 */       return ExtractedDatabaseMetaData.SQLStateType.SQL99;
/* 267:    */     case 1: 
/* 268:295 */       return ExtractedDatabaseMetaData.SQLStateType.XOpen;
/* 269:    */     }
/* 270:298 */     return ExtractedDatabaseMetaData.SQLStateType.UNKOWN;
/* 271:    */   }
/* 272:    */   
/* 273:    */   private static class ExtractedDatabaseMetaDataImpl
/* 274:    */     implements ExtractedDatabaseMetaData
/* 275:    */   {
/* 276:    */     private final boolean supportsScrollableResults;
/* 277:    */     private final boolean supportsGetGeneratedKeys;
/* 278:    */     private final boolean supportsBatchUpdates;
/* 279:    */     private final boolean supportsDataDefinitionInTransaction;
/* 280:    */     private final boolean doesDataDefinitionCauseTransactionCommit;
/* 281:    */     private final Set<String> extraKeywords;
/* 282:    */     private final ExtractedDatabaseMetaData.SQLStateType sqlStateType;
/* 283:    */     private final boolean lobLocatorUpdateCopy;
/* 284:    */     private final String connectionSchemaName;
/* 285:    */     private final String connectionCatalogName;
/* 286:    */     private final LinkedHashSet<TypeInfo> typeInfoSet;
/* 287:    */     
/* 288:    */     private ExtractedDatabaseMetaDataImpl(boolean supportsScrollableResults, boolean supportsGetGeneratedKeys, boolean supportsBatchUpdates, boolean supportsDataDefinitionInTransaction, boolean doesDataDefinitionCauseTransactionCommit, Set<String> extraKeywords, ExtractedDatabaseMetaData.SQLStateType sqlStateType, boolean lobLocatorUpdateCopy, String connectionSchemaName, String connectionCatalogName, LinkedHashSet<TypeInfo> typeInfoSet)
/* 289:    */     {
/* 290:328 */       this.supportsScrollableResults = supportsScrollableResults;
/* 291:329 */       this.supportsGetGeneratedKeys = supportsGetGeneratedKeys;
/* 292:330 */       this.supportsBatchUpdates = supportsBatchUpdates;
/* 293:331 */       this.supportsDataDefinitionInTransaction = supportsDataDefinitionInTransaction;
/* 294:332 */       this.doesDataDefinitionCauseTransactionCommit = doesDataDefinitionCauseTransactionCommit;
/* 295:333 */       this.extraKeywords = extraKeywords;
/* 296:334 */       this.sqlStateType = sqlStateType;
/* 297:335 */       this.lobLocatorUpdateCopy = lobLocatorUpdateCopy;
/* 298:336 */       this.connectionSchemaName = connectionSchemaName;
/* 299:337 */       this.connectionCatalogName = connectionCatalogName;
/* 300:338 */       this.typeInfoSet = typeInfoSet;
/* 301:    */     }
/* 302:    */     
/* 303:    */     public boolean supportsScrollableResults()
/* 304:    */     {
/* 305:343 */       return this.supportsScrollableResults;
/* 306:    */     }
/* 307:    */     
/* 308:    */     public boolean supportsGetGeneratedKeys()
/* 309:    */     {
/* 310:348 */       return this.supportsGetGeneratedKeys;
/* 311:    */     }
/* 312:    */     
/* 313:    */     public boolean supportsBatchUpdates()
/* 314:    */     {
/* 315:353 */       return this.supportsBatchUpdates;
/* 316:    */     }
/* 317:    */     
/* 318:    */     public boolean supportsDataDefinitionInTransaction()
/* 319:    */     {
/* 320:358 */       return this.supportsDataDefinitionInTransaction;
/* 321:    */     }
/* 322:    */     
/* 323:    */     public boolean doesDataDefinitionCauseTransactionCommit()
/* 324:    */     {
/* 325:363 */       return this.doesDataDefinitionCauseTransactionCommit;
/* 326:    */     }
/* 327:    */     
/* 328:    */     public Set<String> getExtraKeywords()
/* 329:    */     {
/* 330:368 */       return this.extraKeywords;
/* 331:    */     }
/* 332:    */     
/* 333:    */     public ExtractedDatabaseMetaData.SQLStateType getSqlStateType()
/* 334:    */     {
/* 335:373 */       return this.sqlStateType;
/* 336:    */     }
/* 337:    */     
/* 338:    */     public boolean doesLobLocatorUpdateCopy()
/* 339:    */     {
/* 340:378 */       return this.lobLocatorUpdateCopy;
/* 341:    */     }
/* 342:    */     
/* 343:    */     public String getConnectionSchemaName()
/* 344:    */     {
/* 345:383 */       return this.connectionSchemaName;
/* 346:    */     }
/* 347:    */     
/* 348:    */     public String getConnectionCatalogName()
/* 349:    */     {
/* 350:388 */       return this.connectionCatalogName;
/* 351:    */     }
/* 352:    */     
/* 353:    */     public LinkedHashSet<TypeInfo> getTypeInfoSet()
/* 354:    */     {
/* 355:393 */       return this.typeInfoSet;
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public ConnectionProvider getConnectionProvider()
/* 360:    */   {
/* 361:399 */     return this.connectionProvider;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public SqlStatementLogger getSqlStatementLogger()
/* 365:    */   {
/* 366:404 */     return this.sqlStatementLogger;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public SqlExceptionHelper getSqlExceptionHelper()
/* 370:    */   {
/* 371:409 */     return this.sqlExceptionHelper;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public Dialect getDialect()
/* 375:    */   {
/* 376:414 */     return this.dialect;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public ExtractedDatabaseMetaData getExtractedMetaDataSupport()
/* 380:    */   {
/* 381:419 */     return this.extractedMetaDataSupport;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public LobCreator getLobCreator(LobCreationContext lobCreationContext)
/* 385:    */   {
/* 386:424 */     return this.lobCreatorBuilder.buildLobCreator(lobCreationContext);
/* 387:    */   }
/* 388:    */   
/* 389:    */   public ResultSetWrapper getResultSetWrapper()
/* 390:    */   {
/* 391:429 */     return ResultSetWrapperImpl.INSTANCE;
/* 392:    */   }
/* 393:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.JdbcServicesImpl
 * JD-Core Version:    0.7.0.1
 */