/*    1:     */ package org.hibernate.internal;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileNotFoundException;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.Serializable;
/*    7:     */ import java.lang.reflect.Method;
/*    8:     */ import java.net.URL;
/*    9:     */ import java.sql.SQLException;
/*   10:     */ import java.sql.SQLWarning;
/*   11:     */ import java.util.Hashtable;
/*   12:     */ import java.util.Properties;
/*   13:     */ import java.util.Set;
/*   14:     */ import javax.naming.NameNotFoundException;
/*   15:     */ import javax.naming.NamingException;
/*   16:     */ import javax.transaction.Synchronization;
/*   17:     */ import javax.transaction.SystemException;
/*   18:     */ import org.hibernate.HibernateException;
/*   19:     */ import org.hibernate.cache.CacheException;
/*   20:     */ import org.hibernate.dialect.Dialect;
/*   21:     */ import org.hibernate.engine.loading.internal.CollectionLoadContext;
/*   22:     */ import org.hibernate.engine.loading.internal.EntityLoadContext;
/*   23:     */ import org.hibernate.engine.spi.CollectionKey;
/*   24:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   25:     */ import org.hibernate.id.IntegralDataTypeHolder;
/*   26:     */ import org.hibernate.service.jdbc.dialect.internal.AbstractDialectResolver;
/*   27:     */ import org.hibernate.service.jndi.JndiException;
/*   28:     */ import org.hibernate.service.jndi.JndiNameException;
/*   29:     */ import org.hibernate.type.BasicType;
/*   30:     */ import org.hibernate.type.SerializationException;
/*   31:     */ import org.hibernate.type.Type;
/*   32:     */ import org.jboss.logging.BasicLogger;
/*   33:     */ import org.jboss.logging.Logger;
/*   34:     */ import org.jboss.logging.Logger.Level;
/*   35:     */ 
/*   36:     */ public class CoreMessageLogger_$logger
/*   37:     */   implements Serializable, CoreMessageLogger, BasicLogger
/*   38:     */ {
/*   39:     */   private static final long serialVersionUID = 1L;
/*   40:     */   private static final String projectCode = "HHH";
/*   41:  45 */   private static final String FQCN = logger.class.getName();
/*   42:     */   protected final Logger log;
/*   43:     */   private static final String narrowingProxy = "Narrowing proxy to %s - this operation breaks ==";
/*   44:     */   private static final String synchronizationFailed = "Exception calling user Synchronization [%s] : %s";
/*   45:     */   private static final String logicalConnectionReleasingPhysicalConnection = "Logical connection releasing its physical connection";
/*   46:     */   private static final String collectionsUpdated = "Collections updated: %s";
/*   47:     */   private static final String entitiesFetched = "Entities fetched (minimize this): %s";
/*   48:     */   private static final String logicalConnectionClosed = "*** Logical connection closed ***";
/*   49:     */   private static final String cacheProvider = "Cache provider: %s";
/*   50:     */   private static final String unableToAccessEjb3Configuration = "Naming exception occurred accessing Ejb3Configuration";
/*   51:     */   private static final String maxQueryTime = "Max query time: %sms";
/*   52:     */   private static final String unableToReadColumnValueFromResultSet = "Could not read column value from result set: %s; %s";
/*   53:     */   private static final String unregisteredStatement = "ResultSet's statement was not registered";
/*   54:     */   private static final String unableToMarkForRollbackOnPersistenceException = "Unable to mark for rollback on PersistenceException: ";
/*   55:     */   private static final String unableToInstantiateUuidGenerationStrategy = "Unable to instantiate UUID generation strategy class : %s";
/*   56:     */   private static final String lazyPropertyFetchingAvailable = "Lazy property fetching available for: %s";
/*   57:     */   private static final String unableToReleaseIsolatedConnection = "Unable to release isolated connection [%s]";
/*   58:     */   private static final String tableNotFound = "Table not found: %s";
/*   59:     */   private static final String proxoolProviderClassNotFound = "proxool properties were encountered, but the %s provider class was not found on the classpath; these properties are going to be ignored.";
/*   60:     */   private static final String collectionsFetched = "Collections fetched (minimize this): %s";
/*   61:     */   private static final String unableToExecuteResolver = "Error executing resolver [%s] : %s";
/*   62:     */   private static final String flushes = "Flushes: %s";
/*   63:     */   private static final String namingExceptionAccessingFactory = "Naming exception occurred accessing factory: %s";
/*   64:     */   private static final String timestampCacheMisses = "update timestamps cache misses: %s";
/*   65:     */   private static final String deprecatedOracle9Dialect = "The Oracle9Dialect dialect has been deprecated; use either Oracle9iDialect or Oracle10gDialect instead";
/*   66:     */   private static final String disablingContextualLOBCreationSinceConnectionNull = "Disabling contextual LOB creation as connection was null";
/*   67:     */   private static final String schemaUpdateComplete = "Schema update complete";
/*   68:     */   private static final String queriesExecuted = "Queries executed to database: %s";
/*   69:     */   private static final String exceptionInBeforeTransactionCompletionInterceptor = "Exception in interceptor beforeTransactionCompletion()";
/*   70:     */   private static final String timestampCachePuts = "update timestamps cache puts: %s";
/*   71:     */   private static final String usingStreams = "Using java.io streams to persist binary types";
/*   72:     */   private static final String unableToLogWarnings = "Could not log warnings";
/*   73:     */   private static final String alreadySessionBound = "Already session bound on call to bind(); make sure you clean up your sessions!";
/*   74:     */   private static final String duplicateGeneratorTable = "Duplicate generator table: %s";
/*   75:     */   private static final String noDefaultConstructor = "No default (no-argument) constructor for class: %s (class must be instantiated by Interceptor)";
/*   76:     */   private static final String disallowingInsertStatementComment = "Disallowing insert statement comment for select-identity due to Oracle driver bug";
/*   77:     */   private static final String filterAnnotationOnSubclass = "@Filter not allowed on subclasses (ignored): %s";
/*   78:     */   private static final String loggingStatistics = "Logging statistics....";
/*   79:     */   private static final String tableFound = "Table found: %s";
/*   80:     */   private static final String unableToCompleteSchemaValidation = "Could not complete schema validation";
/*   81:     */   private static final String unableToLocateConfiguredSchemaNameResolver = "Unable to locate configured schema name resolver class [%s] %s";
/*   82:     */   private static final String unableToCreateProxyFactory = "Could not create proxy factory for:%s";
/*   83:     */   private static final String unableToDestroyQueryCache = "Unable to destroy query cache: %s: %s";
/*   84:     */   private static final String duplicateGeneratorName = "Duplicate generator name %s";
/*   85:     */   private static final String unableToDropTemporaryIdTable = "Unable to drop temporary id table after use [%s]";
/*   86:     */   private static final String unableToCleanUpCallableStatement = "Unable to clean up callable statement";
/*   87:     */   private static final String unableToObtainConnectionToQueryMetadata = "Could not obtain connection to query metadata : %s";
/*   88:     */   private static final String entityMappedAsNonAbstract = "Entity [%s] is abstract-class/interface explicitly mapped as non-abstract; be sure to supply entity-names";
/*   89:     */   private static final String unableToCloseOutputFile = "Error closing output file: %s";
/*   90:     */   private static final String unableToDestroyCache = "Unable to destroy cache: %s";
/*   91:     */   private static final String duplicateMetadata = "Found more than one <persistence-unit-metadata>, subsequent ignored";
/*   92:     */   private static final String orderByAnnotationIndexedCollection = "@OrderBy not allowed for an indexed collection, annotation ignored.";
/*   93:     */   private static final String schemaExportComplete = "Schema export complete";
/*   94:     */   private static final String successfulTransactions = "Successful transactions: %s";
/*   95:     */   private static final String unableToCleanUpPreparedStatement = "Unable to clean up prepared statement";
/*   96:     */   private static final String addingOverrideFor = "Adding override for %s: %s";
/*   97:     */   private static final String jndiInitialContextProperties = "JNDI InitialContext properties:%s";
/*   98:     */   private static final String autoCommitMode = "Autocommit mode: %s";
/*   99:     */   private static final String unableToInstantiateConfiguredSchemaNameResolver = "Unable to instantiate configured schema name resolver [%s] %s";
/*  100:     */   private static final String invalidJndiName = "Invalid JNDI name: %s";
/*  101:     */   private static final String usingDefaultIdGeneratorSegmentValue = "Explicit segment value for id generator [%s.%s] suggested; using default [%s]";
/*  102:     */   private static final String exceptionHeaderNotFound = "%s No %s found";
/*  103:     */   private static final String disablingContextualLOBCreationSinceCreateClobFailed = "Disabling contextual LOB creation as createClob() method threw error : %s";
/*  104:     */   private static final String factoryBoundToJndiName = "Bound factory to JNDI name: %s";
/*  105:     */   private static final String creatingSubcontextInfo = "Creating subcontext: %s";
/*  106:     */   private static final String collectionsRemoved = "Collections removed: %s";
/*  107:     */   private static final String unableToCloseSessionButSwallowingError = "Could not close session; swallowing exception[%s] as transaction completed";
/*  108:     */   private static final String requiredDifferentProvider = "Required a different provider: %s";
/*  109:     */   private static final String sessionsOpened = "Sessions opened: %s";
/*  110:     */   private static final String collectionsRecreated = "Collections recreated: %s";
/*  111:     */   private static final String jdbcIsolationLevel = "JDBC isolation level: %s";
/*  112:     */   private static final String unableToPerformJdbcCommit = "JDBC commit failed";
/*  113:     */   private static final String unableToRetrieveTypeInfoResultSet = "Unable to retrieve type info result set : %s";
/*  114:     */   private static final String deprecatedTransactionManagerStrategy = "Using deprecated %s strategy [%s], use newer %s strategy instead [%s]";
/*  115:     */   private static final String processingPersistenceUnitInfoName = "Processing PersistenceUnitInfo [\n\tname: %s\n\t...]";
/*  116:     */   private static final String writeLocksNotSupported = "Write locks via update not supported for non-versioned entities [%s]";
/*  117:     */   private static final String unableToExecuteBatch = "Exception executing batch [%s]";
/*  118:     */   private static final String startTime = "Start time: %s";
/*  119:     */   private static final String invalidDiscriminatorAnnotation = "Discriminator column has to be defined in the root entity, it will be ignored in subclass: %s";
/*  120:     */   private static final String unableToConfigureSqlExceptionConverter = "Unable to configure SQLExceptionConverter : %s";
/*  121:     */   private static final String unsuccessfulCreate = "Unsuccessful: %s";
/*  122:     */   private static final String statementsPrepared = "Statements prepared: %s";
/*  123:     */   private static final String unableToCloseIterator = "Unable to close iterator";
/*  124:     */   private static final String unableToMarkForRollbackOnTransientObjectException = "Unable to mark for rollback on TransientObjectException: ";
/*  125:     */   private static final String illegalPropertyGetterArgument = "IllegalArgumentException in class: %s, getter method of property: %s";
/*  126:     */   private static final String unregisteredResultSetWithoutStatement = "ResultSet had no statement associated with it, but was not yet registered";
/*  127:     */   private static final String unableToStopHibernateService = "Exception while stopping service";
/*  128:     */   private static final String forcingTableUse = "Forcing table use for sequence-style generator due to pooled optimizer selection where db does not support pooled sequences";
/*  129:     */   private static final String entitiesInserted = "Entities inserted: %s";
/*  130:     */   private static final String jdbcDriverNotSpecified = "No JDBC Driver class was specified by property %s";
/*  131:     */   private static final String duplicateImport = "Duplicate import: %s -> %s";
/*  132:     */   private static final String unableToReleaseBatchStatement = "Unable to release batch statement...";
/*  133:     */   private static final String sessionsClosed = "Sessions closed: %s";
/*  134:     */   private static final String unexpectedRowCounts = "JDBC driver did not return the expected number of row counts";
/*  135:     */   private static final String stoppingService = "Stopping service";
/*  136:     */   private static final String unableToReleaseContext = "Unable to release initial context: %s";
/*  137:     */   private static final String expired = "An item was expired by the cache while it was locked (increase your cache timeout): %s";
/*  138:     */   private static final String unableToObjectConnectionMetadata = "Could not obtain connection metadata: %s";
/*  139:     */   private static final String unableToCloseInputStreamForResource = "Could not close input stream for %s";
/*  140:     */   private static final String legacyTransactionManagerStrategy = "Encountered legacy TransactionManagerLookup specified; convert to newer %s contract specified via %s setting";
/*  141:     */   private static final String missingEntityAnnotation = "Class annotated @org.hibernate.annotations.Entity but not javax.persistence.Entity (most likely a user error): %s";
/*  142:     */   private static final String honoringOptimizerSetting = "Config specified explicit optimizer of [%s], but [%s=%s; honoring optimizer setting";
/*  143:     */   private static final String deprecatedForceDescriminatorAnnotation = "@ForceDiscriminator is deprecated use @DiscriminatorOptions instead.";
/*  144:     */   private static final String bytecodeProvider = "Bytecode provider name : %s";
/*  145:     */   private static final String connectionsObtained = "Connections obtained: %s";
/*  146:     */   private static final String setManagerLookupClass = "You should set hibernate.transaction.manager_lookup_class if cache is enabled";
/*  147:     */   private static final String unableToQueryDatabaseMetadata = "Unable to query java.sql.DatabaseMetaData";
/*  148:     */   private static final String collectionsLoaded = "Collections loaded: %s";
/*  149:     */   private static final String transactionStrategy = "Transaction strategy: %s";
/*  150:     */   private static final String unableToUpdateHiValue = "Could not update hi value in: %s";
/*  151:     */   private static final String unableToObtainConnectionMetadata = "Could not obtain connection metadata : %s";
/*  152:     */   private static final String typeRegistrationOverridesPrevious = "Type registration [%s] overrides previous : %s";
/*  153:     */   private static final String noAppropriateConnectionProvider = "No appropriate connection provider encountered, assuming application will be supplying connections";
/*  154:     */   private static final String overridingTransactionStrategyDangerous = "Overriding %s is dangerous, this might break the EJB3 specification implementation";
/*  155:     */   private static final String secondLevelCacheHits = "Second level cache hits: %s";
/*  156:     */   private static final String ignoringUnrecognizedQueryHint = "Ignoring unrecognized query hint [%s]";
/*  157:     */   private static final String hsqldbSupportsOnlyReadCommittedIsolation = "HSQLDB supports only READ_UNCOMMITTED isolation";
/*  158:     */   private static final String sqlExceptionEscapedProxy = "SQLException escaped proxy";
/*  159:     */   private static final String unableToSetTransactionToRollbackOnly = "Could not set transaction to rollback only";
/*  160:     */   private static final String unableToLocateUuidGenerationStrategy = "Unable to locate requested UUID generation strategy class : %s";
/*  161:     */   private static final String sqlWarning = "SQL Error: %s, SQLState: %s";
/*  162:     */   private static final String unableToLogSqlWarnings = "Unable to log SQLWarnings : %s";
/*  163:     */   private static final String configuredSessionFactory = "Configured SessionFactory: %s";
/*  164:     */   private static final String parsingXmlError = "Error parsing XML (%s) : %s";
/*  165:     */   private static final String runningSchemaValidator = "Running schema validator";
/*  166:     */   private static final String unableToSynchronizeDatabaseStateWithSession = "Could not synchronize database state with session: %s";
/*  167:     */   private static final String unableToUnbindFactoryFromJndi = "Could not unbind factory from JNDI";
/*  168:     */   private static final String unableToLoadCommand = "Error performing load command : %s";
/*  169:     */   private static final String cleaningUpConnectionPool = "Cleaning up connection pool [%s]";
/*  170:     */   private static final String jdbcRollbackFailed = "JDBC rollback failed";
/*  171:     */   private static final String runningHbm2ddlSchemaExport = "Running hbm2ddl schema export";
/*  172:     */   private static final String unableToLocateMBeanServer = "Unable to locate MBeanServer on JMX service shutdown";
/*  173:     */   private static final String transactions = "Transactions: %s";
/*  174:     */   private static final String batchContainedStatementsOnRelease = "On release of batch it still contained JDBC statements";
/*  175:     */   private static final String optimisticLockFailures = "Optimistic lock failures: %s";
/*  176:     */   private static final String resolvedSqlTypeDescriptorForDifferentSqlCode = "Resolved SqlTypeDescriptor is for a different SQL code. %s has sqlCode=%s; type override %s has sqlCode=%s";
/*  177:     */   private static final String unableToRetrieveCache = "Unable to retreive cache from JNDI [%s]: %s";
/*  178:     */   private static final String usingReflectionOptimizer = "Using bytecode reflection optimizer";
/*  179:     */   private static final String jdbcUrlNotSpecified = "JDBC URL was not specified by property %s";
/*  180:     */   private static final String timestampCacheHits = "update timestamps cache hits: %s";
/*  181:     */   private static final String callingJoinTransactionOnNonJtaEntityManager = "Calling joinTransaction() on a non JTA EntityManager";
/*  182:     */   private static final String unableToRollbackIsolatedTransaction = "Unable to rollback isolated transaction on error [%s] : [%s]";
/*  183:     */   private static final String unableToCloseInputFiles = "Error closing input files: %s";
/*  184:     */   private static final String unableToAccessSessionFactory = "Error while accessing session factory with JNDI name %s";
/*  185:     */   private static final String firstOrMaxResultsSpecifiedWithCollectionFetch = "firstResult/maxResults specified with collection fetch; applying in memory!";
/*  186:     */   private static final String exceptionInAfterTransactionCompletionInterceptor = "Exception in interceptor afterTransactionCompletion()";
/*  187:     */   private static final String unableToReadOrInitHiValue = "Could not read or init a hi value";
/*  188:     */   private static final String unableToReadHiValue = "Could not read a hi value - you need to populate the table: %s";
/*  189:     */   private static final String disablingContextualLOBCreationSinceOldJdbcVersion = "Disabling contextual LOB creation as JDBC driver reported JDBC version [%s] less than 4";
/*  190:     */   private static final String containsJoinFetchedCollection = "Ignoring bag join fetch [%s] due to prior collection join fetch";
/*  191:     */   private static final String unableToApplyConstraints = "Unable to apply constraints on DDL for %s";
/*  192:     */   private static final String warningsCreatingTempTable = "Warnings creating temp table : %s";
/*  193:     */   private static final String unableToResolveAggregateFunction = "Could not resolve aggregate function [%s]; using standard definition";
/*  194:     */   private static final String unableToCloseStreamError = "Could not close stream on hibernate.properties: %s";
/*  195:     */   private static final String loadingCollectionKeyNotFound = "In CollectionLoadContext#endLoadingCollections, localLoadingCollectionKeys contained [%s], but no LoadingCollectionEntry was found in loadContexts";
/*  196:     */   private static final String exceptionInSubResolver = "Sub-resolver threw unexpected exception, continuing to next : %s";
/*  197:     */   private static final String parsingXmlWarningForFile = "Warning parsing XML: %s(%s) %s";
/*  198:     */   private static final String startingQueryCache = "Starting query cache at region: %s";
/*  199:     */   private static final String unableToCloseJar = "Could not close jar: %s";
/*  200:     */   private static final String unableToLocateConfigFile = "Unable to locate config file: %s";
/*  201:     */   private static final String unsupportedAfterStatement = "Overriding release mode as connection provider does not support 'after_statement'";
/*  202:     */   private static final String columns = "Columns: %s";
/*  203:     */   private static final String illegalPropertySetterArgument = "IllegalArgumentException in class: %s, setter method of property: %s";
/*  204:     */   private static final String unableToObjectConnectionToQueryMetadata = "Could not obtain connection to query metadata: %s";
/*  205:     */   private static final String disablingContextualLOBCreation = "Disabling contextual LOB creation as %s is true";
/*  206:     */   private static final String unableToInstantiateOptimizer = "Unable to instantiate specified optimizer [%s], falling back to noop";
/*  207:     */   private static final String unknownOracleVersion = "Unknown Oracle major version [%s]";
/*  208:     */   private static final String queryCacheMisses = "Query cache misses: %s";
/*  209:     */   private static final String containerProvidingNullPersistenceUnitRootUrl = "Container is providing a null PersistenceUnitRootUrl: discovery impossible";
/*  210:     */   private static final String unableToWrapResultSet = "Error wrapping result set";
/*  211:     */   private static final String recognizedObsoleteHibernateNamespace = "Recognized obsolete hibernate namespace %s. Use namespace %s instead. Refer to Hibernate 3.6 Migration Guide!";
/*  212:     */   private static final String foundMappingDocument = "Found mapping document in jar: %s";
/*  213:     */   private static final String unableToGetDatabaseMetadata = "Could not get database metadata";
/*  214:     */   private static final String jaccContextId = "JACC contextID: %s";
/*  215:     */   private static final String unableToStopService = "Error stopping service [%s] : %s";
/*  216:     */   private static final String runningHbm2ddlSchemaUpdate = "Running hbm2ddl schema update";
/*  217:     */   private static final String unsuccessful = "Unsuccessful: %s";
/*  218:     */   private static final String localLoadingCollectionKeysCount = "On CollectionLoadContext#cleanup, localLoadingCollectionKeys contained [%s] entries";
/*  219:     */   private static final String missingArguments = "Function template anticipated %s arguments, but %s arguments encountered";
/*  220:     */   private static final String unableToObtainInitialContext = "Could not obtain initial context";
/*  221:     */   private static final String unableToLoadDerbyDriver = "Unable to load/access derby driver class sysinfo to check versions : %s";
/*  222:     */   private static final String parameterPositionOccurredAsBothJpaAndHibernatePositionalParameter = "Parameter position [%s] occurred as both JPA and Hibernate positional parameter";
/*  223:     */   private static final String autoFlushWillNotWork = "JTASessionContext being used with JDBCTransactionFactory; auto-flush will not operate correctly with getCurrentSession()";
/*  224:     */   private static final String deprecatedUuidGenerator = "DEPRECATED : use [%s] instead with custom [%s] implementation";
/*  225:     */   private static final String usingTimestampWorkaround = "Using workaround for JVM bug in java.sql.Timestamp";
/*  226:     */   private static final String unknownIngresVersion = "Unknown Ingres major version [%s]; using Ingres 9.2 dialect";
/*  227:     */   private static final String processEqualityExpression = "processEqualityExpression() : No expression to process!";
/*  228:     */   private static final String instantiatingExplicitConnectionProvider = "Instantiating explicit connection provider: %s";
/*  229:     */   private static final String unableToReleaseTypeInfoResultSet = "Unable to release type info result set";
/*  230:     */   private static final String unableToBindEjb3ConfigurationToJndi = "Could not bind Ejb3Configuration to JNDI";
/*  231:     */   private static final String unsupportedInitialValue = "Hibernate does not support SequenceGenerator.initialValue() unless '%s' set";
/*  232:     */   private static final String rdmsOs2200Dialect = "RDMSOS2200Dialect version: 1.0";
/*  233:     */   private static final String failSafeCollectionsCleanup = "Fail-safe cleanup (collections) : %s";
/*  234:     */   private static final String noPersistentClassesFound = "no persistent classes found for query class: %s";
/*  235:     */   private static final String unableToDetermineTransactionStatus = "Could not determine transaction status";
/*  236:     */   private static final String sortAnnotationIndexedCollection = "@Sort not allowed for an indexed collection, annotation ignored.";
/*  237:     */   private static final String javassistEnhancementFailed = "Javassist Enhancement failed: %s";
/*  238:     */   private static final String jdbcAutoCommitFalseBreaksEjb3Spec = "%s = false breaks the EJB3 specification";
/*  239:     */   private static final String JavaSqlTypesMappedSameCodeMultipleTimes = "java.sql.Types mapped the same code [%s] multiple times; was [%s]; now [%s]";
/*  240:     */   private static final String providerClassDeprecated = "%s has been deprecated in favor of %s; that provider will be used instead.";
/*  241:     */   private static final String persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly = "Persistence provider caller does not implement the EJB3 spec correctly.PersistenceUnitInfo.getNewTempClassLoader() is null.";
/*  242:     */   private static final String factoryUnboundFromName = "A factory was unbound from name: %s";
/*  243:     */   private static final String duplicateJoins = "Duplicate joins for class: %s";
/*  244:     */   private static final String parsingXmlErrorForFile = "Error parsing XML: %s(%s) %s";
/*  245:     */   private static final String propertyNotFound = "Property %s not found in class but described in <mapping-file/> (possible typo error)";
/*  246:     */   private static final String unableToAccessTypeInfoResultSet = "Error accessing type info result set : %s";
/*  247:     */   private static final String incompleteMappingMetadataCacheProcessing = "Mapping metadata cache was not completely processed";
/*  248:     */   private static final String compositeIdClassDoesNotOverrideEquals = "Composite-id class does not override equals(): %s";
/*  249:     */   private static final String connectionProperties = "Connection properties: %s";
/*  250:     */   private static final String readOnlyCacheConfiguredForMutableCollection = "read-only cache configured for mutable collection [%s]";
/*  251:     */   private static final String unableToConstructSqlExceptionConverter = "Unable to construct instance of specified SQLExceptionConverter : %s";
/*  252:     */   private static final String readingCachedMappings = "Reading mappings from cache file: %s";
/*  253:     */   private static final String undeterminedH2Version = "Unable to determine H2 database version, certain features may not work";
/*  254:     */   private static final String deprecatedOracleDialect = "The OracleDialect dialect has been deprecated; use Oracle8iDialect instead";
/*  255:     */   private static final String unableToCloseSessionDuringRollback = "Could not close session during rollback";
/*  256:     */   private static final String unableToCloseInitialContext = "Error closing InitialContext [%s]";
/*  257:     */   private static final String preparedStatementAlreadyInBatch = "PreparedStatement was already in the batch, [%s].";
/*  258:     */   private static final String gettersOfLazyClassesCannotBeFinal = "Getters of lazy classes cannot be final: %s.%s";
/*  259:     */   private static final String namedQueryError = "Error in named query: %s";
/*  260:     */   private static final String closing = "Closing";
/*  261:     */   private static final String propertiesLoaded = "Loaded properties from resource hibernate.properties: %s";
/*  262:     */   private static final String unsupportedMultiTableBulkHqlJpaql = "The %s.%s.%s version of H2 implements temporary table creation such that it commits current transaction; multi-table, bulk hql/jpaql will not work properly";
/*  263:     */   private static final String invalidOnDeleteAnnotation = "Inapropriate use of @OnDelete on entity, annotation ignored: %s";
/*  264:     */   private static final String unableToDetermineTransactionStatusAfterCommit = "Could not determine transaction status after commit";
/*  265:     */   private static final String unableToRollbackJta = "JTA rollback failed";
/*  266:     */   private static final String unableToReleaseCreatedMBeanServer = "Unable to release created MBeanServer : %s";
/*  267:     */   private static final String failed = "an assertion failure occured (this may indicate a bug in Hibernate, but is more likely due to unsafe use of the session): %s";
/*  268:     */   private static final String hibernateConnectionPoolSize = "Hibernate connection pool size: %s";
/*  269:     */   private static final String failSafeEntitiesCleanup = "Fail-safe cleanup (entities) : %s";
/*  270:     */   private static final String hydratingEntitiesCount = "On EntityLoadContext#clear, hydratingEntities contained [%s] entries";
/*  271:     */   private static final String unableToBuildSessionFactoryUsingMBeanClasspath = "Could not build SessionFactory using the MBean classpath - will try again using client classpath: %s";
/*  272:     */   private static final String unableToParseMetadata = "Could not parse the package-level metadata [%s]";
/*  273:     */   private static final String unableToReleaseCacheLock = "Could not release a cache lock : %s";
/*  274:     */   private static final String unableToCloseOutputStream = "IOException occurred closing output stream";
/*  275:     */   private static final String transactionStartedOnNonRootSession = "Transaction started on non-root session";
/*  276:     */   private static final String searchingForMappingDocuments = "Searching for mapping documents in jar: %s";
/*  277:     */   private static final String unableToTransformClass = "Unable to transform class: %s";
/*  278:     */   private static final String writingGeneratedSchemaToFile = "Writing generated schema to file: %s";
/*  279:     */   private static final String unsupportedOracleVersion = "Oracle 11g is not yet fully supported; using Oracle 10g dialect";
/*  280:     */   private static final String entityManagerClosedBySomeoneElse = "Entity Manager closed by someone else (%s must not be used)";
/*  281:     */   private static final String parsingXmlWarning = "Warning parsing XML (%s) : %s";
/*  282:     */   private static final String invalidTableAnnotation = "Illegal use of @Table in a subclass of a SINGLE_TABLE hierarchy: %s";
/*  283:     */   private static final String noColumnsSpecifiedForIndex = "There were not column names specified for index %s on table %s";
/*  284:     */   private static final String typeDefinedNoRegistrationKeys = "Type [%s] defined no registration keys; ignoring";
/*  285:     */   private static final String synchronizationAlreadyRegistered = "Synchronization [%s] was already registered";
/*  286:     */   private static final String packageNotFound = "Package not found or wo package-info.java: %s";
/*  287:     */   private static final String usingAstQueryTranslatorFactory = "Using ASTQueryTranslatorFactory";
/*  288:     */   private static final String readingMappingsFromResource = "Reading mappings from resource: %s";
/*  289:     */   private static final String usingDialect = "Using dialect: %s";
/*  290:     */   private static final String unableToCloseInputStream = "Could not close input stream";
/*  291:     */   private static final String needsLimit = "FirstResult/maxResults specified on polymorphic query; applying in memory!";
/*  292:     */   private static final String unableToCloseConnection = "Error closing connection";
/*  293:     */   private static final String fetchingDatabaseMetadata = "Fetching database metadata";
/*  294:     */   private static final String unableToDetermineLockModeValue = "Unable to determine lock mode value : %s -> %s";
/*  295:     */   private static final String unableToDeserializeCache = "Could not deserialize cache file: %s : %s";
/*  296:     */   private static final String configuringFromResource = "Configuring from resource: %s";
/*  297:     */   private static final String serviceProperties = "Service properties: %s";
/*  298:     */   private static final String unableToUpdateQueryHiValue = "Could not updateQuery hi value in: %s";
/*  299:     */   private static final String forcingContainerResourceCleanup = "Forcing container resource cleanup on transaction completion";
/*  300:     */   private static final String unableToResolveMappingFile = "Unable to resolve mapping file [%s]";
/*  301:     */   private static final String willNotRegisterListeners = "Property hibernate.search.autoregister_listeners is set to false. No attempt will be made to register Hibernate Search event listeners.";
/*  302:     */   private static final String unableToCreateSchema = "Error creating schema ";
/*  303:     */   private static final String splitQueries = "Manipulation query [%s] resulted in [%s] split queries";
/*  304:     */   private static final String secondLevelCachePuts = "Second level cache puts: %s";
/*  305:     */   private static final String unableToPerformManagedFlush = "Error during managed flush [%s]";
/*  306:     */   private static final String unableToLoadProperties = "Problem loading properties from hibernate.properties";
/*  307:     */   private static final String unableToCloseStream = "IOException occurred closing stream";
/*  308:     */   private static final String ignoringTableGeneratorConstraints = "Ignoring unique constraints specified on table generator [%s]";
/*  309:     */   private static final String entitiesUpdated = "Entities updated: %s";
/*  310:     */   private static final String unableToCommitJta = "JTA commit failed";
/*  311:     */   private static final String duplicateListener = "entity-listener duplication, first event definition will be used: %s";
/*  312:     */   private static final String deprecatedDerbyDialect = "The DerbyDialect dialect has been deprecated; use one of the version-specific dialects instead";
/*  313:     */   private static final String renamedProperty = "Property [%s] has been renamed to [%s]; update your properties appropriately";
/*  314:     */   private static final String unableToCopySystemProperties = "Could not copy system properties, system properties will be ignored";
/*  315:     */   private static final String definingFlushBeforeCompletionIgnoredInHem = "Defining %s=true ignored in HEM";
/*  316:     */   private static final String unknownBytecodeProvider = "unrecognized bytecode provider [%s], using javassist by default";
/*  317:     */   private static final String entityAnnotationOnNonRoot = "@org.hibernate.annotations.Entity used on a non root entity: ignored for %s";
/*  318:     */   private static final String guidGenerated = "GUID identifier generated: %s";
/*  319:     */   private static final String jndiNameDoesNotHandleSessionFactoryReference = "JNDI name %s does not handle a session factory reference";
/*  320:     */   private static final String validatorNotFound = "Hibernate Validator not found: ignoring";
/*  321:     */   private static final String usingDriver = "using driver [%s] at URL [%s]";
/*  322:     */   private static final String queryCacheHits = "Query cache hits: %s";
/*  323:     */   private static final String foreignKeys = "Foreign keys: %s";
/*  324:     */   private static final String unableToRemoveBagJoinFetch = "Unable to erase previously added bag join fetch";
/*  325:     */   private static final String pooledOptimizerReportedInitialValue = "Pooled optimizer source reported [%s] as the initial value; use of 1 or greater highly recommended";
/*  326:     */   private static final String entityIdentifierValueBindingExists = "Setting entity-identifier value binding where one already existed : %s.";
/*  327:     */   private static final String readingMappingsFromFile = "Reading mappings from file: %s";
/*  328:     */   private static final String unableToReadClass = "Unable to read class: %s";
/*  329:     */   private static final String unknownSqlServerVersion = "Unknown Microsoft SQL Server major version [%s] using SQL Server 2000 dialect";
/*  330:     */   private static final String invalidPrimaryKeyJoinColumnAnnotation = "Root entity should not hold an PrimaryKeyJoinColum(s), will be ignored";
/*  331:     */   private static final String factoryJndiRename = "A factory was renamed from [%s] to [%s] in JNDI";
/*  332:     */   private static final String handlingTransientEntity = "Handling transient entity in delete processing";
/*  333:     */   private static final String unsupportedProperty = "Usage of obsolete property: %s no longer supported, use: %s";
/*  334:     */   private static final String invalidEditOfReadOnlyItem = "Application attempted to edit read only item: %s";
/*  335:     */   private static final String statementsClosed = "Statements closed: %s";
/*  336:     */   private static final String exceptionHeaderFound = "%s %s found";
/*  337:     */   private static final String unableToCleanupTemporaryIdTable = "Unable to cleanup temporary id table after use [%s]";
/*  338:     */   private static final String subResolverException = "sub-resolver threw unexpected exception, continuing to next : %s";
/*  339:     */   private static final String unableToSwitchToMethodUsingColumnIndex = "Exception switching from method: [%s] to a method using the column index. Reverting to using: [%<s]";
/*  340:     */   private static final String secondLevelCacheMisses = "Second level cache misses: %s";
/*  341:     */   private static final String invalidArrayElementType = "Array element type error\n%s";
/*  342:     */   private static final String unableToBindValueToParameter = "Could not bind value '%s' to parameter: %s; %s";
/*  343:     */   private static final String unableToConstructCurrentSessionContext = "Unable to construct current session context [%s]";
/*  344:     */   private static final String hql = "HQL: %s, time: %sms, rows: %s";
/*  345:     */   private static final String compositeIdClassDoesNotOverrideHashCode = "Composite-id class does not override hashCode(): %s";
/*  346:     */   private static final String noSessionFactoryWithJndiName = "No session factory with JNDI name %s";
/*  347:     */   private static final String unableToWriteCachedFile = "I/O reported error writing cached file : %s: %s";
/*  348:     */   private static final String factoryUnboundFromJndiName = "Unbound factory from JNDI name: %s";
/*  349:     */   private static final String unableToFindPersistenceXmlInClasspath = "Could not find any META-INF/persistence.xml file in the classpath";
/*  350:     */   private static final String unableToCompleteSchemaUpdate = "Could not complete schema update";
/*  351:     */   private static final String settersOfLazyClassesCannotBeFinal = "Setters of lazy classes cannot be final: %s.%s";
/*  352:     */   private static final String usingUuidHexGenerator = "Using %s which does not generate IETF RFC 4122 compliant UUID values; consider using %s instead";
/*  353:     */   private static final String propertiesNotFound = "hibernate.properties not found";
/*  354:     */   private static final String configuringFromUrl = "Configuring from URL: %s";
/*  355:     */   private static final String queryCachePuts = "Query cache puts: %s";
/*  356:     */   private static final String configuringFromXmlDocument = "Configuring from XML document";
/*  357:     */   private static final String expectedType = "Expected type: %s, actual value: %s";
/*  358:     */   private static final String usingOldDtd = "Don't use old DTDs, read the Hibernate 3.x Migration Guide!";
/*  359:     */   private static final String c3p0ProviderClassNotFound = "c3p0 properties were encountered, but the %s provider class was not found on the classpath; these properties are going to be ignored.";
/*  360:     */   private static final String indexes = "Indexes: %s";
/*  361:     */   private static final String unableToRollbackConnection = "Unable to rollback connection on exception [%s]";
/*  362:     */   private static final String immutableAnnotationOnNonRoot = "@Immutable used on a non root entity: ignored for %s";
/*  363:     */   private static final String configuringFromFile = "Configuring from file: %s";
/*  364:     */   private static final String entitiesLoaded = "Entities loaded: %s";
/*  365:     */   private static final String invalidSubStrategy = "Mixing inheritance strategy in a entity hierarchy is not allowed, ignoring sub strategy in: %s";
/*  366:     */   private static final String updatingSchema = "Updating schema";
/*  367:     */   private static final String unsupportedIngresVersion = "Ingres 10 is not yet fully supported; using Ingres 9.3 dialect";
/*  368:     */   private static final String unableToLocateNClobClass = "Could not locate 'java.sql.NClob' class; assuming JDBC 3";
/*  369:     */   private static final String unableToBindFactoryToJndi = "Could not bind factory to JNDI";
/*  370:     */   private static final String unableToClosePooledConnection = "Problem closing pooled connection";
/*  371:     */   private static final String unableToBuildEnhancementMetamodel = "Unable to build enhancement metamodel for %s";
/*  372:     */   private static final String unableToRunSchemaUpdate = "Error running schema update";
/*  373:     */   private static final String unexpectedLiteralTokenType = "Unexpected literal token type [%s] passed for numeric processing";
/*  374:     */   private static final String schemaExportUnsuccessful = "Schema export unsuccessful";
/*  375:     */   private static final String startingUpdateTimestampsCache = "Starting update timestamps cache at region: %s";
/*  376:     */   private static final String startingServiceAtJndiName = "Starting service at JNDI name: %s";
/*  377:     */   private static final String usingDefaultTransactionStrategy = "Using default transaction strategy (direct JDBC transactions)";
/*  378:     */   private static final String unableToJoinTransaction = "Cannot join transaction: do not override %s";
/*  379:     */   private static final String entitiesDeleted = "Entities deleted: %s";
/*  380:     */   private static final String version = "Hibernate Core {%s}";
/*  381:     */   private static final String unableToCloseSession = "Could not close session";
/*  382:     */   private static final String usingHibernateBuiltInConnectionPool = "Using Hibernate built-in connection pool (not for production use!)";
/*  383:     */   private static final String cachedFileNotFound = "I/O reported cached file could not be found : %s : %s";
/*  384:     */   private static final String closingUnreleasedBatch = "Closing un-released batch";
/*  385:     */   private static final String unableToDestroyUpdateTimestampsCache = "Unable to destroy update timestamps cache: %s: %s";
/*  386:     */   private static final String couldNotBindJndiListener = "Could not bind JNDI listener";
/*  387:     */   private static final String unableToToggleAutoCommit = "Could not toggle autocommit";
/*  388:     */   private static final String scopingTypesToSessionFactoryAfterAlreadyScoped = "Scoping types to session factory %s after already scoped %s";
/*  389:     */   private static final String configurationResource = "Configuration resource: %s";
/*  390:     */   
/*  391:     */   public CoreMessageLogger_$logger(Logger log)
/*  392:     */   {
/*  393: 396 */     this.log = log;
/*  394:     */   }
/*  395:     */   
/*  396:     */   public final boolean isTraceEnabled()
/*  397:     */   {
/*  398: 401 */     return this.log.isTraceEnabled();
/*  399:     */   }
/*  400:     */   
/*  401:     */   public final void trace(Object message)
/*  402:     */   {
/*  403: 406 */     this.log.trace(FQCN, message, null);
/*  404:     */   }
/*  405:     */   
/*  406:     */   public final void trace(Object message, Throwable t)
/*  407:     */   {
/*  408: 411 */     this.log.trace(FQCN, message, t);
/*  409:     */   }
/*  410:     */   
/*  411:     */   public final void trace(String loggerFqcn, Object message, Throwable t)
/*  412:     */   {
/*  413: 416 */     this.log.trace(loggerFqcn, message, t);
/*  414:     */   }
/*  415:     */   
/*  416:     */   public final void trace(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  417:     */   {
/*  418: 421 */     this.log.trace(loggerFqcn, message, params, t);
/*  419:     */   }
/*  420:     */   
/*  421:     */   public final void tracev(String format, Object... params)
/*  422:     */   {
/*  423: 426 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, params);
/*  424:     */   }
/*  425:     */   
/*  426:     */   public final void tracev(String format, Object param1)
/*  427:     */   {
/*  428: 431 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1);
/*  429:     */   }
/*  430:     */   
/*  431:     */   public final void tracev(String format, Object param1, Object param2)
/*  432:     */   {
/*  433: 436 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1, param2);
/*  434:     */   }
/*  435:     */   
/*  436:     */   public final void tracev(String format, Object param1, Object param2, Object param3)
/*  437:     */   {
/*  438: 441 */     this.log.logv(FQCN, Logger.Level.TRACE, null, format, param1, param2, param3);
/*  439:     */   }
/*  440:     */   
/*  441:     */   public final void tracev(Throwable t, String format, Object... params)
/*  442:     */   {
/*  443: 446 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, params);
/*  444:     */   }
/*  445:     */   
/*  446:     */   public final void tracev(Throwable t, String format, Object param1)
/*  447:     */   {
/*  448: 451 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1);
/*  449:     */   }
/*  450:     */   
/*  451:     */   public final void tracev(Throwable t, String format, Object param1, Object param2)
/*  452:     */   {
/*  453: 456 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1, param2);
/*  454:     */   }
/*  455:     */   
/*  456:     */   public final void tracev(Throwable t, String format, Object param1, Object param2, Object param3)
/*  457:     */   {
/*  458: 461 */     this.log.logv(FQCN, Logger.Level.TRACE, t, format, param1, param2, param3);
/*  459:     */   }
/*  460:     */   
/*  461:     */   public final void tracef(String format, Object... params)
/*  462:     */   {
/*  463: 466 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, params);
/*  464:     */   }
/*  465:     */   
/*  466:     */   public final void tracef(String format, Object param1)
/*  467:     */   {
/*  468: 471 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1);
/*  469:     */   }
/*  470:     */   
/*  471:     */   public final void tracef(String format, Object param1, Object param2)
/*  472:     */   {
/*  473: 476 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1, param2);
/*  474:     */   }
/*  475:     */   
/*  476:     */   public final void tracef(String format, Object param1, Object param2, Object param3)
/*  477:     */   {
/*  478: 481 */     this.log.logf(FQCN, Logger.Level.TRACE, null, format, param1, param2, param3);
/*  479:     */   }
/*  480:     */   
/*  481:     */   public final void tracef(Throwable t, String format, Object... params)
/*  482:     */   {
/*  483: 486 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, params);
/*  484:     */   }
/*  485:     */   
/*  486:     */   public final void tracef(Throwable t, String format, Object param1)
/*  487:     */   {
/*  488: 491 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1);
/*  489:     */   }
/*  490:     */   
/*  491:     */   public final void tracef(Throwable t, String format, Object param1, Object param2)
/*  492:     */   {
/*  493: 496 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1, param2);
/*  494:     */   }
/*  495:     */   
/*  496:     */   public final void tracef(Throwable t, String format, Object param1, Object param2, Object param3)
/*  497:     */   {
/*  498: 501 */     this.log.logf(FQCN, Logger.Level.TRACE, t, format, param1, param2, param3);
/*  499:     */   }
/*  500:     */   
/*  501:     */   public final boolean isDebugEnabled()
/*  502:     */   {
/*  503: 506 */     return this.log.isDebugEnabled();
/*  504:     */   }
/*  505:     */   
/*  506:     */   public final void debug(Object message)
/*  507:     */   {
/*  508: 511 */     this.log.debug(FQCN, message, null);
/*  509:     */   }
/*  510:     */   
/*  511:     */   public final void debug(Object message, Throwable t)
/*  512:     */   {
/*  513: 516 */     this.log.debug(FQCN, message, t);
/*  514:     */   }
/*  515:     */   
/*  516:     */   public final void debug(String loggerFqcn, Object message, Throwable t)
/*  517:     */   {
/*  518: 521 */     this.log.debug(loggerFqcn, message, t);
/*  519:     */   }
/*  520:     */   
/*  521:     */   public final void debug(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  522:     */   {
/*  523: 526 */     this.log.debug(loggerFqcn, message, params, t);
/*  524:     */   }
/*  525:     */   
/*  526:     */   public final void debugv(String format, Object... params)
/*  527:     */   {
/*  528: 531 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, params);
/*  529:     */   }
/*  530:     */   
/*  531:     */   public final void debugv(String format, Object param1)
/*  532:     */   {
/*  533: 536 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1);
/*  534:     */   }
/*  535:     */   
/*  536:     */   public final void debugv(String format, Object param1, Object param2)
/*  537:     */   {
/*  538: 541 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1, param2);
/*  539:     */   }
/*  540:     */   
/*  541:     */   public final void debugv(String format, Object param1, Object param2, Object param3)
/*  542:     */   {
/*  543: 546 */     this.log.logv(FQCN, Logger.Level.DEBUG, null, format, param1, param2, param3);
/*  544:     */   }
/*  545:     */   
/*  546:     */   public final void debugv(Throwable t, String format, Object... params)
/*  547:     */   {
/*  548: 551 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, params);
/*  549:     */   }
/*  550:     */   
/*  551:     */   public final void debugv(Throwable t, String format, Object param1)
/*  552:     */   {
/*  553: 556 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1);
/*  554:     */   }
/*  555:     */   
/*  556:     */   public final void debugv(Throwable t, String format, Object param1, Object param2)
/*  557:     */   {
/*  558: 561 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1, param2);
/*  559:     */   }
/*  560:     */   
/*  561:     */   public final void debugv(Throwable t, String format, Object param1, Object param2, Object param3)
/*  562:     */   {
/*  563: 566 */     this.log.logv(FQCN, Logger.Level.DEBUG, t, format, param1, param2, param3);
/*  564:     */   }
/*  565:     */   
/*  566:     */   public final void debugf(String format, Object... params)
/*  567:     */   {
/*  568: 571 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, params);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public final void debugf(String format, Object param1)
/*  572:     */   {
/*  573: 576 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1);
/*  574:     */   }
/*  575:     */   
/*  576:     */   public final void debugf(String format, Object param1, Object param2)
/*  577:     */   {
/*  578: 581 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1, param2);
/*  579:     */   }
/*  580:     */   
/*  581:     */   public final void debugf(String format, Object param1, Object param2, Object param3)
/*  582:     */   {
/*  583: 586 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, format, param1, param2, param3);
/*  584:     */   }
/*  585:     */   
/*  586:     */   public final void debugf(Throwable t, String format, Object... params)
/*  587:     */   {
/*  588: 591 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, params);
/*  589:     */   }
/*  590:     */   
/*  591:     */   public final void debugf(Throwable t, String format, Object param1)
/*  592:     */   {
/*  593: 596 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1);
/*  594:     */   }
/*  595:     */   
/*  596:     */   public final void debugf(Throwable t, String format, Object param1, Object param2)
/*  597:     */   {
/*  598: 601 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1, param2);
/*  599:     */   }
/*  600:     */   
/*  601:     */   public final void debugf(Throwable t, String format, Object param1, Object param2, Object param3)
/*  602:     */   {
/*  603: 606 */     this.log.logf(FQCN, Logger.Level.DEBUG, t, format, param1, param2, param3);
/*  604:     */   }
/*  605:     */   
/*  606:     */   public final boolean isInfoEnabled()
/*  607:     */   {
/*  608: 611 */     return this.log.isInfoEnabled();
/*  609:     */   }
/*  610:     */   
/*  611:     */   public final void info(Object message)
/*  612:     */   {
/*  613: 616 */     this.log.info(FQCN, message, null);
/*  614:     */   }
/*  615:     */   
/*  616:     */   public final void info(Object message, Throwable t)
/*  617:     */   {
/*  618: 621 */     this.log.info(FQCN, message, t);
/*  619:     */   }
/*  620:     */   
/*  621:     */   public final void info(String loggerFqcn, Object message, Throwable t)
/*  622:     */   {
/*  623: 626 */     this.log.info(loggerFqcn, message, t);
/*  624:     */   }
/*  625:     */   
/*  626:     */   public final void info(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  627:     */   {
/*  628: 631 */     this.log.info(loggerFqcn, message, params, t);
/*  629:     */   }
/*  630:     */   
/*  631:     */   public final void infov(String format, Object... params)
/*  632:     */   {
/*  633: 636 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, params);
/*  634:     */   }
/*  635:     */   
/*  636:     */   public final void infov(String format, Object param1)
/*  637:     */   {
/*  638: 641 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1);
/*  639:     */   }
/*  640:     */   
/*  641:     */   public final void infov(String format, Object param1, Object param2)
/*  642:     */   {
/*  643: 646 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1, param2);
/*  644:     */   }
/*  645:     */   
/*  646:     */   public final void infov(String format, Object param1, Object param2, Object param3)
/*  647:     */   {
/*  648: 651 */     this.log.logv(FQCN, Logger.Level.INFO, null, format, param1, param2, param3);
/*  649:     */   }
/*  650:     */   
/*  651:     */   public final void infov(Throwable t, String format, Object... params)
/*  652:     */   {
/*  653: 656 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, params);
/*  654:     */   }
/*  655:     */   
/*  656:     */   public final void infov(Throwable t, String format, Object param1)
/*  657:     */   {
/*  658: 661 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1);
/*  659:     */   }
/*  660:     */   
/*  661:     */   public final void infov(Throwable t, String format, Object param1, Object param2)
/*  662:     */   {
/*  663: 666 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1, param2);
/*  664:     */   }
/*  665:     */   
/*  666:     */   public final void infov(Throwable t, String format, Object param1, Object param2, Object param3)
/*  667:     */   {
/*  668: 671 */     this.log.logv(FQCN, Logger.Level.INFO, t, format, param1, param2, param3);
/*  669:     */   }
/*  670:     */   
/*  671:     */   public final void infof(String format, Object... params)
/*  672:     */   {
/*  673: 676 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, params);
/*  674:     */   }
/*  675:     */   
/*  676:     */   public final void infof(String format, Object param1)
/*  677:     */   {
/*  678: 681 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1);
/*  679:     */   }
/*  680:     */   
/*  681:     */   public final void infof(String format, Object param1, Object param2)
/*  682:     */   {
/*  683: 686 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1, param2);
/*  684:     */   }
/*  685:     */   
/*  686:     */   public final void infof(String format, Object param1, Object param2, Object param3)
/*  687:     */   {
/*  688: 691 */     this.log.logf(FQCN, Logger.Level.INFO, null, format, param1, param2, param3);
/*  689:     */   }
/*  690:     */   
/*  691:     */   public final void infof(Throwable t, String format, Object... params)
/*  692:     */   {
/*  693: 696 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, params);
/*  694:     */   }
/*  695:     */   
/*  696:     */   public final void infof(Throwable t, String format, Object param1)
/*  697:     */   {
/*  698: 701 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1);
/*  699:     */   }
/*  700:     */   
/*  701:     */   public final void infof(Throwable t, String format, Object param1, Object param2)
/*  702:     */   {
/*  703: 706 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1, param2);
/*  704:     */   }
/*  705:     */   
/*  706:     */   public final void infof(Throwable t, String format, Object param1, Object param2, Object param3)
/*  707:     */   {
/*  708: 711 */     this.log.logf(FQCN, Logger.Level.INFO, t, format, param1, param2, param3);
/*  709:     */   }
/*  710:     */   
/*  711:     */   public final void warn(Object message)
/*  712:     */   {
/*  713: 716 */     this.log.warn(FQCN, message, null);
/*  714:     */   }
/*  715:     */   
/*  716:     */   public final void warn(Object message, Throwable t)
/*  717:     */   {
/*  718: 721 */     this.log.warn(FQCN, message, t);
/*  719:     */   }
/*  720:     */   
/*  721:     */   public final void warn(String loggerFqcn, Object message, Throwable t)
/*  722:     */   {
/*  723: 726 */     this.log.warn(loggerFqcn, message, t);
/*  724:     */   }
/*  725:     */   
/*  726:     */   public final void warn(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  727:     */   {
/*  728: 731 */     this.log.warn(loggerFqcn, message, params, t);
/*  729:     */   }
/*  730:     */   
/*  731:     */   public final void warnv(String format, Object... params)
/*  732:     */   {
/*  733: 736 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, params);
/*  734:     */   }
/*  735:     */   
/*  736:     */   public final void warnv(String format, Object param1)
/*  737:     */   {
/*  738: 741 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1);
/*  739:     */   }
/*  740:     */   
/*  741:     */   public final void warnv(String format, Object param1, Object param2)
/*  742:     */   {
/*  743: 746 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1, param2);
/*  744:     */   }
/*  745:     */   
/*  746:     */   public final void warnv(String format, Object param1, Object param2, Object param3)
/*  747:     */   {
/*  748: 751 */     this.log.logv(FQCN, Logger.Level.WARN, null, format, param1, param2, param3);
/*  749:     */   }
/*  750:     */   
/*  751:     */   public final void warnv(Throwable t, String format, Object... params)
/*  752:     */   {
/*  753: 756 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, params);
/*  754:     */   }
/*  755:     */   
/*  756:     */   public final void warnv(Throwable t, String format, Object param1)
/*  757:     */   {
/*  758: 761 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1);
/*  759:     */   }
/*  760:     */   
/*  761:     */   public final void warnv(Throwable t, String format, Object param1, Object param2)
/*  762:     */   {
/*  763: 766 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1, param2);
/*  764:     */   }
/*  765:     */   
/*  766:     */   public final void warnv(Throwable t, String format, Object param1, Object param2, Object param3)
/*  767:     */   {
/*  768: 771 */     this.log.logv(FQCN, Logger.Level.WARN, t, format, param1, param2, param3);
/*  769:     */   }
/*  770:     */   
/*  771:     */   public final void warnf(String format, Object... params)
/*  772:     */   {
/*  773: 776 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, params);
/*  774:     */   }
/*  775:     */   
/*  776:     */   public final void warnf(String format, Object param1)
/*  777:     */   {
/*  778: 781 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1);
/*  779:     */   }
/*  780:     */   
/*  781:     */   public final void warnf(String format, Object param1, Object param2)
/*  782:     */   {
/*  783: 786 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1, param2);
/*  784:     */   }
/*  785:     */   
/*  786:     */   public final void warnf(String format, Object param1, Object param2, Object param3)
/*  787:     */   {
/*  788: 791 */     this.log.logf(FQCN, Logger.Level.WARN, null, format, param1, param2, param3);
/*  789:     */   }
/*  790:     */   
/*  791:     */   public final void warnf(Throwable t, String format, Object... params)
/*  792:     */   {
/*  793: 796 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, params);
/*  794:     */   }
/*  795:     */   
/*  796:     */   public final void warnf(Throwable t, String format, Object param1)
/*  797:     */   {
/*  798: 801 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1);
/*  799:     */   }
/*  800:     */   
/*  801:     */   public final void warnf(Throwable t, String format, Object param1, Object param2)
/*  802:     */   {
/*  803: 806 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1, param2);
/*  804:     */   }
/*  805:     */   
/*  806:     */   public final void warnf(Throwable t, String format, Object param1, Object param2, Object param3)
/*  807:     */   {
/*  808: 811 */     this.log.logf(FQCN, Logger.Level.WARN, t, format, param1, param2, param3);
/*  809:     */   }
/*  810:     */   
/*  811:     */   public final void error(Object message)
/*  812:     */   {
/*  813: 816 */     this.log.error(FQCN, message, null);
/*  814:     */   }
/*  815:     */   
/*  816:     */   public final void error(Object message, Throwable t)
/*  817:     */   {
/*  818: 821 */     this.log.error(FQCN, message, t);
/*  819:     */   }
/*  820:     */   
/*  821:     */   public final void error(String loggerFqcn, Object message, Throwable t)
/*  822:     */   {
/*  823: 826 */     this.log.error(loggerFqcn, message, t);
/*  824:     */   }
/*  825:     */   
/*  826:     */   public final void error(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  827:     */   {
/*  828: 831 */     this.log.error(loggerFqcn, message, params, t);
/*  829:     */   }
/*  830:     */   
/*  831:     */   public final void errorv(String format, Object... params)
/*  832:     */   {
/*  833: 836 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, params);
/*  834:     */   }
/*  835:     */   
/*  836:     */   public final void errorv(String format, Object param1)
/*  837:     */   {
/*  838: 841 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1);
/*  839:     */   }
/*  840:     */   
/*  841:     */   public final void errorv(String format, Object param1, Object param2)
/*  842:     */   {
/*  843: 846 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1, param2);
/*  844:     */   }
/*  845:     */   
/*  846:     */   public final void errorv(String format, Object param1, Object param2, Object param3)
/*  847:     */   {
/*  848: 851 */     this.log.logv(FQCN, Logger.Level.ERROR, null, format, param1, param2, param3);
/*  849:     */   }
/*  850:     */   
/*  851:     */   public final void errorv(Throwable t, String format, Object... params)
/*  852:     */   {
/*  853: 856 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, params);
/*  854:     */   }
/*  855:     */   
/*  856:     */   public final void errorv(Throwable t, String format, Object param1)
/*  857:     */   {
/*  858: 861 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1);
/*  859:     */   }
/*  860:     */   
/*  861:     */   public final void errorv(Throwable t, String format, Object param1, Object param2)
/*  862:     */   {
/*  863: 866 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1, param2);
/*  864:     */   }
/*  865:     */   
/*  866:     */   public final void errorv(Throwable t, String format, Object param1, Object param2, Object param3)
/*  867:     */   {
/*  868: 871 */     this.log.logv(FQCN, Logger.Level.ERROR, t, format, param1, param2, param3);
/*  869:     */   }
/*  870:     */   
/*  871:     */   public final void errorf(String format, Object... params)
/*  872:     */   {
/*  873: 876 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, params);
/*  874:     */   }
/*  875:     */   
/*  876:     */   public final void errorf(String format, Object param1)
/*  877:     */   {
/*  878: 881 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1);
/*  879:     */   }
/*  880:     */   
/*  881:     */   public final void errorf(String format, Object param1, Object param2)
/*  882:     */   {
/*  883: 886 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1, param2);
/*  884:     */   }
/*  885:     */   
/*  886:     */   public final void errorf(String format, Object param1, Object param2, Object param3)
/*  887:     */   {
/*  888: 891 */     this.log.logf(FQCN, Logger.Level.ERROR, null, format, param1, param2, param3);
/*  889:     */   }
/*  890:     */   
/*  891:     */   public final void errorf(Throwable t, String format, Object... params)
/*  892:     */   {
/*  893: 896 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, params);
/*  894:     */   }
/*  895:     */   
/*  896:     */   public final void errorf(Throwable t, String format, Object param1)
/*  897:     */   {
/*  898: 901 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1);
/*  899:     */   }
/*  900:     */   
/*  901:     */   public final void errorf(Throwable t, String format, Object param1, Object param2)
/*  902:     */   {
/*  903: 906 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1, param2);
/*  904:     */   }
/*  905:     */   
/*  906:     */   public final void errorf(Throwable t, String format, Object param1, Object param2, Object param3)
/*  907:     */   {
/*  908: 911 */     this.log.logf(FQCN, Logger.Level.ERROR, t, format, param1, param2, param3);
/*  909:     */   }
/*  910:     */   
/*  911:     */   public final void fatal(Object message)
/*  912:     */   {
/*  913: 916 */     this.log.fatal(FQCN, message, null);
/*  914:     */   }
/*  915:     */   
/*  916:     */   public final void fatal(Object message, Throwable t)
/*  917:     */   {
/*  918: 921 */     this.log.fatal(FQCN, message, t);
/*  919:     */   }
/*  920:     */   
/*  921:     */   public final void fatal(String loggerFqcn, Object message, Throwable t)
/*  922:     */   {
/*  923: 926 */     this.log.fatal(loggerFqcn, message, t);
/*  924:     */   }
/*  925:     */   
/*  926:     */   public final void fatal(String loggerFqcn, Object message, Object[] params, Throwable t)
/*  927:     */   {
/*  928: 931 */     this.log.fatal(loggerFqcn, message, params, t);
/*  929:     */   }
/*  930:     */   
/*  931:     */   public final void fatalv(String format, Object... params)
/*  932:     */   {
/*  933: 936 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, params);
/*  934:     */   }
/*  935:     */   
/*  936:     */   public final void fatalv(String format, Object param1)
/*  937:     */   {
/*  938: 941 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1);
/*  939:     */   }
/*  940:     */   
/*  941:     */   public final void fatalv(String format, Object param1, Object param2)
/*  942:     */   {
/*  943: 946 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1, param2);
/*  944:     */   }
/*  945:     */   
/*  946:     */   public final void fatalv(String format, Object param1, Object param2, Object param3)
/*  947:     */   {
/*  948: 951 */     this.log.logv(FQCN, Logger.Level.FATAL, null, format, param1, param2, param3);
/*  949:     */   }
/*  950:     */   
/*  951:     */   public final void fatalv(Throwable t, String format, Object... params)
/*  952:     */   {
/*  953: 956 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, params);
/*  954:     */   }
/*  955:     */   
/*  956:     */   public final void fatalv(Throwable t, String format, Object param1)
/*  957:     */   {
/*  958: 961 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1);
/*  959:     */   }
/*  960:     */   
/*  961:     */   public final void fatalv(Throwable t, String format, Object param1, Object param2)
/*  962:     */   {
/*  963: 966 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1, param2);
/*  964:     */   }
/*  965:     */   
/*  966:     */   public final void fatalv(Throwable t, String format, Object param1, Object param2, Object param3)
/*  967:     */   {
/*  968: 971 */     this.log.logv(FQCN, Logger.Level.FATAL, t, format, param1, param2, param3);
/*  969:     */   }
/*  970:     */   
/*  971:     */   public final void fatalf(String format, Object... params)
/*  972:     */   {
/*  973: 976 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, params);
/*  974:     */   }
/*  975:     */   
/*  976:     */   public final void fatalf(String format, Object param1)
/*  977:     */   {
/*  978: 981 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1);
/*  979:     */   }
/*  980:     */   
/*  981:     */   public final void fatalf(String format, Object param1, Object param2)
/*  982:     */   {
/*  983: 986 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1, param2);
/*  984:     */   }
/*  985:     */   
/*  986:     */   public final void fatalf(String format, Object param1, Object param2, Object param3)
/*  987:     */   {
/*  988: 991 */     this.log.logf(FQCN, Logger.Level.FATAL, null, format, param1, param2, param3);
/*  989:     */   }
/*  990:     */   
/*  991:     */   public final void fatalf(Throwable t, String format, Object... params)
/*  992:     */   {
/*  993: 996 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, params);
/*  994:     */   }
/*  995:     */   
/*  996:     */   public final void fatalf(Throwable t, String format, Object param1)
/*  997:     */   {
/*  998:1001 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1);
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public final void fatalf(Throwable t, String format, Object param1, Object param2)
/* 1002:     */   {
/* 1003:1006 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1, param2);
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public final void fatalf(Throwable t, String format, Object param1, Object param2, Object param3)
/* 1007:     */   {
/* 1008:1011 */     this.log.logf(FQCN, Logger.Level.FATAL, t, format, param1, param2, param3);
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public final boolean isEnabled(Logger.Level level)
/* 1012:     */   {
/* 1013:1015 */     return this.log.isEnabled(level);
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public final void log(Logger.Level level, Object message)
/* 1017:     */   {
/* 1018:1020 */     this.log.log(FQCN, level, message, null, null);
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public final void log(Logger.Level level, Object message, Throwable t)
/* 1022:     */   {
/* 1023:1025 */     this.log.log(FQCN, level, message, null, t);
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public final void log(Logger.Level level, String loggerFqcn, Object message, Throwable t)
/* 1027:     */   {
/* 1028:1030 */     this.log.log(level, loggerFqcn, message, t);
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public final void log(String loggerFqcn, Logger.Level level, Object message, Object[] params, Throwable t)
/* 1032:     */   {
/* 1033:1035 */     this.log.log(loggerFqcn, level, message, params, t);
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public final void logv(Logger.Level level, String format, Object... params)
/* 1037:     */   {
/* 1038:1040 */     this.log.logv(FQCN, level, null, format, params);
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public final void logv(Logger.Level level, String format, Object param1)
/* 1042:     */   {
/* 1043:1045 */     this.log.logv(FQCN, level, null, format, param1);
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public final void logv(Logger.Level level, String format, Object param1, Object param2)
/* 1047:     */   {
/* 1048:1050 */     this.log.logv(FQCN, level, null, format, param1, param2);
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public final void logv(Logger.Level level, String format, Object param1, Object param2, Object param3)
/* 1052:     */   {
/* 1053:1055 */     this.log.logv(FQCN, level, null, format, param1, param2, param3);
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public final void logv(Logger.Level level, Throwable t, String format, Object... params)
/* 1057:     */   {
/* 1058:1060 */     this.log.logv(FQCN, level, t, format, params);
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public final void logv(Logger.Level level, Throwable t, String format, Object param1)
/* 1062:     */   {
/* 1063:1065 */     this.log.logv(FQCN, level, t, format, param1);
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public final void logv(Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 1067:     */   {
/* 1068:1070 */     this.log.logv(FQCN, level, t, format, param1, param2);
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public final void logv(Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 1072:     */   {
/* 1073:1075 */     this.log.logv(FQCN, level, t, format, param1, param2, param3);
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public final void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object... params)
/* 1077:     */   {
/* 1078:1080 */     this.log.logv(loggerFqcn, level, t, format, params);
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public final void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1)
/* 1082:     */   {
/* 1083:1085 */     this.log.logv(loggerFqcn, level, t, format, param1);
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public final void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 1087:     */   {
/* 1088:1090 */     this.log.logv(loggerFqcn, level, t, format, param1, param2);
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public final void logv(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 1092:     */   {
/* 1093:1095 */     this.log.logv(loggerFqcn, level, t, format, param1, param2, param3);
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   public final void logf(Logger.Level level, String format, Object... params)
/* 1097:     */   {
/* 1098:1100 */     this.log.logf(FQCN, level, null, format, params);
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   public final void logf(Logger.Level level, String format, Object param1)
/* 1102:     */   {
/* 1103:1105 */     this.log.logf(FQCN, level, null, format, param1);
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public final void logf(Logger.Level level, String format, Object param1, Object param2)
/* 1107:     */   {
/* 1108:1110 */     this.log.logf(FQCN, level, null, format, param1, param2);
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public final void logf(Logger.Level level, String format, Object param1, Object param2, Object param3)
/* 1112:     */   {
/* 1113:1115 */     this.log.logf(FQCN, level, null, format, param1, param2, param3);
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public final void logf(Logger.Level level, Throwable t, String format, Object... params)
/* 1117:     */   {
/* 1118:1120 */     this.log.logf(FQCN, level, t, format, params);
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public final void logf(Logger.Level level, Throwable t, String format, Object param1)
/* 1122:     */   {
/* 1123:1125 */     this.log.logf(FQCN, level, t, format, param1);
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public final void logf(Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 1127:     */   {
/* 1128:1130 */     this.log.logf(FQCN, level, t, format, param1, param2);
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public final void logf(Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 1132:     */   {
/* 1133:1135 */     this.log.logf(FQCN, level, t, format, param1, param2, param3);
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public final void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object... params)
/* 1137:     */   {
/* 1138:1140 */     this.log.logf(loggerFqcn, level, t, format, params);
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public final void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1)
/* 1142:     */   {
/* 1143:1145 */     this.log.logf(loggerFqcn, level, t, format, param1);
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public final void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2)
/* 1147:     */   {
/* 1148:1150 */     this.log.logf(loggerFqcn, level, t, format, param1, param2);
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public final void logf(String loggerFqcn, Logger.Level level, Throwable t, String format, Object param1, Object param2, Object param3)
/* 1152:     */   {
/* 1153:1155 */     this.log.logf(loggerFqcn, level, t, format, param1, param2, param3);
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public final void narrowingProxy(Class concreteProxyClass)
/* 1157:     */   {
/* 1158:1160 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000179: " + narrowingProxy$str(), concreteProxyClass);
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   protected String narrowingProxy$str()
/* 1162:     */   {
/* 1163:1164 */     return "Narrowing proxy to %s - this operation breaks ==";
/* 1164:     */   }
/* 1165:     */   
/* 1166:     */   public final void synchronizationFailed(Synchronization synchronization, Throwable t)
/* 1167:     */   {
/* 1168:1169 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000260: " + synchronizationFailed$str(), synchronization, t);
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   protected String synchronizationFailed$str()
/* 1172:     */   {
/* 1173:1173 */     return "Exception calling user Synchronization [%s] : %s";
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   public final void logicalConnectionReleasingPhysicalConnection()
/* 1177:     */   {
/* 1178:1178 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, "HHH000163: " + logicalConnectionReleasingPhysicalConnection$str(), new Object[0]);
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   protected String logicalConnectionReleasingPhysicalConnection$str()
/* 1182:     */   {
/* 1183:1182 */     return "Logical connection releasing its physical connection";
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   public final void collectionsUpdated(long collectionUpdateCount)
/* 1187:     */   {
/* 1188:1187 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000036: " + collectionsUpdated$str(), Long.valueOf(collectionUpdateCount));
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   protected String collectionsUpdated$str()
/* 1192:     */   {
/* 1193:1191 */     return "Collections updated: %s";
/* 1194:     */   }
/* 1195:     */   
/* 1196:     */   public final void entitiesFetched(long entityFetchCount)
/* 1197:     */   {
/* 1198:1196 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000077: " + entitiesFetched$str(), Long.valueOf(entityFetchCount));
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   protected String entitiesFetched$str()
/* 1202:     */   {
/* 1203:1200 */     return "Entities fetched (minimize this): %s";
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   public final void logicalConnectionClosed()
/* 1207:     */   {
/* 1208:1205 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, "HHH000162: " + logicalConnectionClosed$str(), new Object[0]);
/* 1209:     */   }
/* 1210:     */   
/* 1211:     */   protected String logicalConnectionClosed$str()
/* 1212:     */   {
/* 1213:1209 */     return "*** Logical connection closed ***";
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   public final void cacheProvider(String name)
/* 1217:     */   {
/* 1218:1214 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000024: " + cacheProvider$str(), name);
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   protected String cacheProvider$str()
/* 1222:     */   {
/* 1223:1218 */     return "Cache provider: %s";
/* 1224:     */   }
/* 1225:     */   
/* 1226:     */   public final void unableToAccessEjb3Configuration(NamingException e)
/* 1227:     */   {
/* 1228:1223 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000271: " + unableToAccessEjb3Configuration$str(), new Object[0]);
/* 1229:     */   }
/* 1230:     */   
/* 1231:     */   protected String unableToAccessEjb3Configuration$str()
/* 1232:     */   {
/* 1233:1227 */     return "Naming exception occurred accessing Ejb3Configuration";
/* 1234:     */   }
/* 1235:     */   
/* 1236:     */   public final void maxQueryTime(long queryExecutionMaxTime)
/* 1237:     */   {
/* 1238:1232 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000173: " + maxQueryTime$str(), Long.valueOf(queryExecutionMaxTime));
/* 1239:     */   }
/* 1240:     */   
/* 1241:     */   protected String maxQueryTime$str()
/* 1242:     */   {
/* 1243:1236 */     return "Max query time: %sms";
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   public final void unableToReadColumnValueFromResultSet(String name, String message)
/* 1247:     */   {
/* 1248:1241 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000349: " + unableToReadColumnValueFromResultSet$str(), name, message);
/* 1249:     */   }
/* 1250:     */   
/* 1251:     */   protected String unableToReadColumnValueFromResultSet$str()
/* 1252:     */   {
/* 1253:1245 */     return "Could not read column value from result set: %s; %s";
/* 1254:     */   }
/* 1255:     */   
/* 1256:     */   public final void unregisteredStatement()
/* 1257:     */   {
/* 1258:1250 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000387: " + unregisteredStatement$str(), new Object[0]);
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   protected String unregisteredStatement$str()
/* 1262:     */   {
/* 1263:1254 */     return "ResultSet's statement was not registered";
/* 1264:     */   }
/* 1265:     */   
/* 1266:     */   public final void unableToMarkForRollbackOnPersistenceException(Exception e)
/* 1267:     */   {
/* 1268:1259 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000337: " + unableToMarkForRollbackOnPersistenceException$str(), new Object[0]);
/* 1269:     */   }
/* 1270:     */   
/* 1271:     */   protected String unableToMarkForRollbackOnPersistenceException$str()
/* 1272:     */   {
/* 1273:1263 */     return "Unable to mark for rollback on PersistenceException: ";
/* 1274:     */   }
/* 1275:     */   
/* 1276:     */   public final void unableToInstantiateUuidGenerationStrategy(Exception ignore)
/* 1277:     */   {
/* 1278:1268 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000325: " + unableToInstantiateUuidGenerationStrategy$str(), ignore);
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   protected String unableToInstantiateUuidGenerationStrategy$str()
/* 1282:     */   {
/* 1283:1272 */     return "Unable to instantiate UUID generation strategy class : %s";
/* 1284:     */   }
/* 1285:     */   
/* 1286:     */   public final void lazyPropertyFetchingAvailable(String name)
/* 1287:     */   {
/* 1288:1277 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000157: " + lazyPropertyFetchingAvailable$str(), name);
/* 1289:     */   }
/* 1290:     */   
/* 1291:     */   protected String lazyPropertyFetchingAvailable$str()
/* 1292:     */   {
/* 1293:1281 */     return "Lazy property fetching available for: %s";
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   public final void unableToReleaseIsolatedConnection(Throwable ignore)
/* 1297:     */   {
/* 1298:1286 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000356: " + unableToReleaseIsolatedConnection$str(), ignore);
/* 1299:     */   }
/* 1300:     */   
/* 1301:     */   protected String unableToReleaseIsolatedConnection$str()
/* 1302:     */   {
/* 1303:1290 */     return "Unable to release isolated connection [%s]";
/* 1304:     */   }
/* 1305:     */   
/* 1306:     */   public final void tableNotFound(String name)
/* 1307:     */   {
/* 1308:1295 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000262: " + tableNotFound$str(), name);
/* 1309:     */   }
/* 1310:     */   
/* 1311:     */   protected String tableNotFound$str()
/* 1312:     */   {
/* 1313:1299 */     return "Table not found: %s";
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   public final void proxoolProviderClassNotFound(String proxoolProviderClassName)
/* 1317:     */   {
/* 1318:1304 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000209: " + proxoolProviderClassNotFound$str(), proxoolProviderClassName);
/* 1319:     */   }
/* 1320:     */   
/* 1321:     */   protected String proxoolProviderClassNotFound$str()
/* 1322:     */   {
/* 1323:1308 */     return "proxool properties were encountered, but the %s provider class was not found on the classpath; these properties are going to be ignored.";
/* 1324:     */   }
/* 1325:     */   
/* 1326:     */   public final void collectionsFetched(long collectionFetchCount)
/* 1327:     */   {
/* 1328:1313 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000032: " + collectionsFetched$str(), Long.valueOf(collectionFetchCount));
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   protected String collectionsFetched$str()
/* 1332:     */   {
/* 1333:1317 */     return "Collections fetched (minimize this): %s";
/* 1334:     */   }
/* 1335:     */   
/* 1336:     */   public final void unableToExecuteResolver(AbstractDialectResolver abstractDialectResolver, String message)
/* 1337:     */   {
/* 1338:1322 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000316: " + unableToExecuteResolver$str(), abstractDialectResolver, message);
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   protected String unableToExecuteResolver$str()
/* 1342:     */   {
/* 1343:1326 */     return "Error executing resolver [%s] : %s";
/* 1344:     */   }
/* 1345:     */   
/* 1346:     */   public final void flushes(long flushCount)
/* 1347:     */   {
/* 1348:1331 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000105: " + flushes$str(), Long.valueOf(flushCount));
/* 1349:     */   }
/* 1350:     */   
/* 1351:     */   protected String flushes$str()
/* 1352:     */   {
/* 1353:1335 */     return "Flushes: %s";
/* 1354:     */   }
/* 1355:     */   
/* 1356:     */   public final void namingExceptionAccessingFactory(NamingException exception)
/* 1357:     */   {
/* 1358:1340 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000178: " + namingExceptionAccessingFactory$str(), exception);
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   protected String namingExceptionAccessingFactory$str()
/* 1362:     */   {
/* 1363:1344 */     return "Naming exception occurred accessing factory: %s";
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   public final void timestampCacheMisses(long updateTimestampsCachePutCount)
/* 1367:     */   {
/* 1368:1349 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000435: " + timestampCacheMisses$str(), Long.valueOf(updateTimestampsCachePutCount));
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   protected String timestampCacheMisses$str()
/* 1372:     */   {
/* 1373:1353 */     return "update timestamps cache misses: %s";
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   public final void deprecatedOracle9Dialect()
/* 1377:     */   {
/* 1378:1358 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000063: " + deprecatedOracle9Dialect$str(), new Object[0]);
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   protected String deprecatedOracle9Dialect$str()
/* 1382:     */   {
/* 1383:1362 */     return "The Oracle9Dialect dialect has been deprecated; use either Oracle9iDialect or Oracle10gDialect instead";
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   public final void disablingContextualLOBCreationSinceConnectionNull()
/* 1387:     */   {
/* 1388:1367 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000422: " + disablingContextualLOBCreationSinceConnectionNull$str(), new Object[0]);
/* 1389:     */   }
/* 1390:     */   
/* 1391:     */   protected String disablingContextualLOBCreationSinceConnectionNull$str()
/* 1392:     */   {
/* 1393:1371 */     return "Disabling contextual LOB creation as connection was null";
/* 1394:     */   }
/* 1395:     */   
/* 1396:     */   public final void schemaUpdateComplete()
/* 1397:     */   {
/* 1398:1376 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000232: " + schemaUpdateComplete$str(), new Object[0]);
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   protected String schemaUpdateComplete$str()
/* 1402:     */   {
/* 1403:1380 */     return "Schema update complete";
/* 1404:     */   }
/* 1405:     */   
/* 1406:     */   public final void queriesExecuted(long queryExecutionCount)
/* 1407:     */   {
/* 1408:1385 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000210: " + queriesExecuted$str(), Long.valueOf(queryExecutionCount));
/* 1409:     */   }
/* 1410:     */   
/* 1411:     */   protected String queriesExecuted$str()
/* 1412:     */   {
/* 1413:1389 */     return "Queries executed to database: %s";
/* 1414:     */   }
/* 1415:     */   
/* 1416:     */   public final void exceptionInBeforeTransactionCompletionInterceptor(Throwable e)
/* 1417:     */   {
/* 1418:1394 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000088: " + exceptionInBeforeTransactionCompletionInterceptor$str(), new Object[0]);
/* 1419:     */   }
/* 1420:     */   
/* 1421:     */   protected String exceptionInBeforeTransactionCompletionInterceptor$str()
/* 1422:     */   {
/* 1423:1398 */     return "Exception in interceptor beforeTransactionCompletion()";
/* 1424:     */   }
/* 1425:     */   
/* 1426:     */   public final void timestampCachePuts(long updateTimestampsCachePutCount)
/* 1427:     */   {
/* 1428:1403 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000433: " + timestampCachePuts$str(), Long.valueOf(updateTimestampsCachePutCount));
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   protected String timestampCachePuts$str()
/* 1432:     */   {
/* 1433:1407 */     return "update timestamps cache puts: %s";
/* 1434:     */   }
/* 1435:     */   
/* 1436:     */   public final void usingStreams()
/* 1437:     */   {
/* 1438:1412 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000407: " + usingStreams$str(), new Object[0]);
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   protected String usingStreams$str()
/* 1442:     */   {
/* 1443:1416 */     return "Using java.io streams to persist binary types";
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   public final void unableToLogWarnings(SQLException e)
/* 1447:     */   {
/* 1448:1421 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000336: " + unableToLogWarnings$str(), new Object[0]);
/* 1449:     */   }
/* 1450:     */   
/* 1451:     */   protected String unableToLogWarnings$str()
/* 1452:     */   {
/* 1453:1425 */     return "Could not log warnings";
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public final void alreadySessionBound()
/* 1457:     */   {
/* 1458:1430 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000002: " + alreadySessionBound$str(), new Object[0]);
/* 1459:     */   }
/* 1460:     */   
/* 1461:     */   protected String alreadySessionBound$str()
/* 1462:     */   {
/* 1463:1434 */     return "Already session bound on call to bind(); make sure you clean up your sessions!";
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public final void duplicateGeneratorTable(String name)
/* 1467:     */   {
/* 1468:1439 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000070: " + duplicateGeneratorTable$str(), name);
/* 1469:     */   }
/* 1470:     */   
/* 1471:     */   protected String duplicateGeneratorTable$str()
/* 1472:     */   {
/* 1473:1443 */     return "Duplicate generator table: %s";
/* 1474:     */   }
/* 1475:     */   
/* 1476:     */   public final void noDefaultConstructor(String name)
/* 1477:     */   {
/* 1478:1448 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000182: " + noDefaultConstructor$str(), name);
/* 1479:     */   }
/* 1480:     */   
/* 1481:     */   protected String noDefaultConstructor$str()
/* 1482:     */   {
/* 1483:1452 */     return "No default (no-argument) constructor for class: %s (class must be instantiated by Interceptor)";
/* 1484:     */   }
/* 1485:     */   
/* 1486:     */   public final void disallowingInsertStatementComment()
/* 1487:     */   {
/* 1488:1457 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000067: " + disallowingInsertStatementComment$str(), new Object[0]);
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   protected String disallowingInsertStatementComment$str()
/* 1492:     */   {
/* 1493:1461 */     return "Disallowing insert statement comment for select-identity due to Oracle driver bug";
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public final void filterAnnotationOnSubclass(String className)
/* 1497:     */   {
/* 1498:1466 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000103: " + filterAnnotationOnSubclass$str(), className);
/* 1499:     */   }
/* 1500:     */   
/* 1501:     */   protected String filterAnnotationOnSubclass$str()
/* 1502:     */   {
/* 1503:1470 */     return "@Filter not allowed on subclasses (ignored): %s";
/* 1504:     */   }
/* 1505:     */   
/* 1506:     */   public final void loggingStatistics()
/* 1507:     */   {
/* 1508:1475 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000161: " + loggingStatistics$str(), new Object[0]);
/* 1509:     */   }
/* 1510:     */   
/* 1511:     */   protected String loggingStatistics$str()
/* 1512:     */   {
/* 1513:1479 */     return "Logging statistics....";
/* 1514:     */   }
/* 1515:     */   
/* 1516:     */   public final void tableFound(String string)
/* 1517:     */   {
/* 1518:1484 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000261: " + tableFound$str(), string);
/* 1519:     */   }
/* 1520:     */   
/* 1521:     */   protected String tableFound$str()
/* 1522:     */   {
/* 1523:1488 */     return "Table found: %s";
/* 1524:     */   }
/* 1525:     */   
/* 1526:     */   public final void unableToCompleteSchemaValidation(SQLException e)
/* 1527:     */   {
/* 1528:1493 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000300: " + unableToCompleteSchemaValidation$str(), new Object[0]);
/* 1529:     */   }
/* 1530:     */   
/* 1531:     */   protected String unableToCompleteSchemaValidation$str()
/* 1532:     */   {
/* 1533:1497 */     return "Could not complete schema validation";
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public final void unableToLocateConfiguredSchemaNameResolver(String resolverClassName, String message)
/* 1537:     */   {
/* 1538:1502 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000331: " + unableToLocateConfiguredSchemaNameResolver$str(), resolverClassName, message);
/* 1539:     */   }
/* 1540:     */   
/* 1541:     */   protected String unableToLocateConfiguredSchemaNameResolver$str()
/* 1542:     */   {
/* 1543:1506 */     return "Unable to locate configured schema name resolver class [%s] %s";
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   public final void unableToCreateProxyFactory(String entityName, HibernateException e)
/* 1547:     */   {
/* 1548:1511 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000305: " + unableToCreateProxyFactory$str(), entityName);
/* 1549:     */   }
/* 1550:     */   
/* 1551:     */   protected String unableToCreateProxyFactory$str()
/* 1552:     */   {
/* 1553:1515 */     return "Could not create proxy factory for:%s";
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   public final void unableToDestroyQueryCache(String region, String message)
/* 1557:     */   {
/* 1558:1520 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000309: " + unableToDestroyQueryCache$str(), region, message);
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   protected String unableToDestroyQueryCache$str()
/* 1562:     */   {
/* 1563:1524 */     return "Unable to destroy query cache: %s: %s";
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public final void duplicateGeneratorName(String name)
/* 1567:     */   {
/* 1568:1529 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000069: " + duplicateGeneratorName$str(), name);
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   protected String duplicateGeneratorName$str()
/* 1572:     */   {
/* 1573:1533 */     return "Duplicate generator name %s";
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public final void unableToDropTemporaryIdTable(String message)
/* 1577:     */   {
/* 1578:1538 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000314: " + unableToDropTemporaryIdTable$str(), message);
/* 1579:     */   }
/* 1580:     */   
/* 1581:     */   protected String unableToDropTemporaryIdTable$str()
/* 1582:     */   {
/* 1583:1542 */     return "Unable to drop temporary id table after use [%s]";
/* 1584:     */   }
/* 1585:     */   
/* 1586:     */   public final void unableToCleanUpCallableStatement(SQLException e)
/* 1587:     */   {
/* 1588:1547 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000281: " + unableToCleanUpCallableStatement$str(), new Object[0]);
/* 1589:     */   }
/* 1590:     */   
/* 1591:     */   protected String unableToCleanUpCallableStatement$str()
/* 1592:     */   {
/* 1593:1551 */     return "Unable to clean up callable statement";
/* 1594:     */   }
/* 1595:     */   
/* 1596:     */   public final void unableToObtainConnectionToQueryMetadata(String message)
/* 1597:     */   {
/* 1598:1556 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000342: " + unableToObtainConnectionToQueryMetadata$str(), message);
/* 1599:     */   }
/* 1600:     */   
/* 1601:     */   protected String unableToObtainConnectionToQueryMetadata$str()
/* 1602:     */   {
/* 1603:1560 */     return "Could not obtain connection to query metadata : %s";
/* 1604:     */   }
/* 1605:     */   
/* 1606:     */   public final void entityMappedAsNonAbstract(String name)
/* 1607:     */   {
/* 1608:1565 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000084: " + entityMappedAsNonAbstract$str(), name);
/* 1609:     */   }
/* 1610:     */   
/* 1611:     */   protected String entityMappedAsNonAbstract$str()
/* 1612:     */   {
/* 1613:1569 */     return "Entity [%s] is abstract-class/interface explicitly mapped as non-abstract; be sure to supply entity-names";
/* 1614:     */   }
/* 1615:     */   
/* 1616:     */   public final void unableToCloseOutputFile(String outputFile, IOException e)
/* 1617:     */   {
/* 1618:1574 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000291: " + unableToCloseOutputFile$str(), outputFile);
/* 1619:     */   }
/* 1620:     */   
/* 1621:     */   protected String unableToCloseOutputFile$str()
/* 1622:     */   {
/* 1623:1578 */     return "Error closing output file: %s";
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   public final void unableToDestroyCache(String message)
/* 1627:     */   {
/* 1628:1583 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000308: " + unableToDestroyCache$str(), message);
/* 1629:     */   }
/* 1630:     */   
/* 1631:     */   protected String unableToDestroyCache$str()
/* 1632:     */   {
/* 1633:1587 */     return "Unable to destroy cache: %s";
/* 1634:     */   }
/* 1635:     */   
/* 1636:     */   public final void duplicateMetadata()
/* 1637:     */   {
/* 1638:1592 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000074: " + duplicateMetadata$str(), new Object[0]);
/* 1639:     */   }
/* 1640:     */   
/* 1641:     */   protected String duplicateMetadata$str()
/* 1642:     */   {
/* 1643:1596 */     return "Found more than one <persistence-unit-metadata>, subsequent ignored";
/* 1644:     */   }
/* 1645:     */   
/* 1646:     */   public final void orderByAnnotationIndexedCollection()
/* 1647:     */   {
/* 1648:1601 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000189: " + orderByAnnotationIndexedCollection$str(), new Object[0]);
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   protected String orderByAnnotationIndexedCollection$str()
/* 1652:     */   {
/* 1653:1605 */     return "@OrderBy not allowed for an indexed collection, annotation ignored.";
/* 1654:     */   }
/* 1655:     */   
/* 1656:     */   public final void schemaExportComplete()
/* 1657:     */   {
/* 1658:1610 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000230: " + schemaExportComplete$str(), new Object[0]);
/* 1659:     */   }
/* 1660:     */   
/* 1661:     */   protected String schemaExportComplete$str()
/* 1662:     */   {
/* 1663:1614 */     return "Schema export complete";
/* 1664:     */   }
/* 1665:     */   
/* 1666:     */   public final void successfulTransactions(long committedTransactionCount)
/* 1667:     */   {
/* 1668:1619 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000258: " + successfulTransactions$str(), Long.valueOf(committedTransactionCount));
/* 1669:     */   }
/* 1670:     */   
/* 1671:     */   protected String successfulTransactions$str()
/* 1672:     */   {
/* 1673:1623 */     return "Successful transactions: %s";
/* 1674:     */   }
/* 1675:     */   
/* 1676:     */   public final void unableToCleanUpPreparedStatement(SQLException e)
/* 1677:     */   {
/* 1678:1628 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000282: " + unableToCleanUpPreparedStatement$str(), new Object[0]);
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   protected String unableToCleanUpPreparedStatement$str()
/* 1682:     */   {
/* 1683:1632 */     return "Unable to clean up prepared statement";
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   public final void addingOverrideFor(String name, String name2)
/* 1687:     */   {
/* 1688:1637 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000418: " + addingOverrideFor$str(), name, name2);
/* 1689:     */   }
/* 1690:     */   
/* 1691:     */   protected String addingOverrideFor$str()
/* 1692:     */   {
/* 1693:1641 */     return "Adding override for %s: %s";
/* 1694:     */   }
/* 1695:     */   
/* 1696:     */   public final void jndiInitialContextProperties(Hashtable hash)
/* 1697:     */   {
/* 1698:1646 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000154: " + jndiInitialContextProperties$str(), hash);
/* 1699:     */   }
/* 1700:     */   
/* 1701:     */   protected String jndiInitialContextProperties$str()
/* 1702:     */   {
/* 1703:1650 */     return "JNDI InitialContext properties:%s";
/* 1704:     */   }
/* 1705:     */   
/* 1706:     */   public final void autoCommitMode(boolean autocommit)
/* 1707:     */   {
/* 1708:1655 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000006: " + autoCommitMode$str(), Boolean.valueOf(autocommit));
/* 1709:     */   }
/* 1710:     */   
/* 1711:     */   protected String autoCommitMode$str()
/* 1712:     */   {
/* 1713:1659 */     return "Autocommit mode: %s";
/* 1714:     */   }
/* 1715:     */   
/* 1716:     */   public final void unableToInstantiateConfiguredSchemaNameResolver(String resolverClassName, String message)
/* 1717:     */   {
/* 1718:1664 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000320: " + unableToInstantiateConfiguredSchemaNameResolver$str(), resolverClassName, message);
/* 1719:     */   }
/* 1720:     */   
/* 1721:     */   protected String unableToInstantiateConfiguredSchemaNameResolver$str()
/* 1722:     */   {
/* 1723:1668 */     return "Unable to instantiate configured schema name resolver [%s] %s";
/* 1724:     */   }
/* 1725:     */   
/* 1726:     */   public final void invalidJndiName(String name, JndiNameException e)
/* 1727:     */   {
/* 1728:1673 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000135: " + invalidJndiName$str(), name);
/* 1729:     */   }
/* 1730:     */   
/* 1731:     */   protected String invalidJndiName$str()
/* 1732:     */   {
/* 1733:1677 */     return "Invalid JNDI name: %s";
/* 1734:     */   }
/* 1735:     */   
/* 1736:     */   public final void usingDefaultIdGeneratorSegmentValue(String tableName, String segmentColumnName, String defaultToUse)
/* 1737:     */   {
/* 1738:1682 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000398: " + usingDefaultIdGeneratorSegmentValue$str(), tableName, segmentColumnName, defaultToUse);
/* 1739:     */   }
/* 1740:     */   
/* 1741:     */   protected String usingDefaultIdGeneratorSegmentValue$str()
/* 1742:     */   {
/* 1743:1686 */     return "Explicit segment value for id generator [%s.%s] suggested; using default [%s]";
/* 1744:     */   }
/* 1745:     */   
/* 1746:     */   public final void exceptionHeaderNotFound(String exceptionHeader, String metaInfOrmXml)
/* 1747:     */   {
/* 1748:1691 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000086: " + exceptionHeaderNotFound$str(), exceptionHeader, metaInfOrmXml);
/* 1749:     */   }
/* 1750:     */   
/* 1751:     */   protected String exceptionHeaderNotFound$str()
/* 1752:     */   {
/* 1753:1695 */     return "%s No %s found";
/* 1754:     */   }
/* 1755:     */   
/* 1756:     */   public final void disablingContextualLOBCreationSinceCreateClobFailed(Throwable t)
/* 1757:     */   {
/* 1758:1700 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000424: " + disablingContextualLOBCreationSinceCreateClobFailed$str(), t);
/* 1759:     */   }
/* 1760:     */   
/* 1761:     */   protected String disablingContextualLOBCreationSinceCreateClobFailed$str()
/* 1762:     */   {
/* 1763:1704 */     return "Disabling contextual LOB creation as createClob() method threw error : %s";
/* 1764:     */   }
/* 1765:     */   
/* 1766:     */   public final void factoryBoundToJndiName(String name)
/* 1767:     */   {
/* 1768:1709 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000094: " + factoryBoundToJndiName$str(), name);
/* 1769:     */   }
/* 1770:     */   
/* 1771:     */   protected String factoryBoundToJndiName$str()
/* 1772:     */   {
/* 1773:1713 */     return "Bound factory to JNDI name: %s";
/* 1774:     */   }
/* 1775:     */   
/* 1776:     */   public final void creatingSubcontextInfo(String intermediateContextName)
/* 1777:     */   {
/* 1778:1718 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000053: " + creatingSubcontextInfo$str(), intermediateContextName);
/* 1779:     */   }
/* 1780:     */   
/* 1781:     */   protected String creatingSubcontextInfo$str()
/* 1782:     */   {
/* 1783:1722 */     return "Creating subcontext: %s";
/* 1784:     */   }
/* 1785:     */   
/* 1786:     */   public final void collectionsRemoved(long collectionRemoveCount)
/* 1787:     */   {
/* 1788:1727 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000035: " + collectionsRemoved$str(), Long.valueOf(collectionRemoveCount));
/* 1789:     */   }
/* 1790:     */   
/* 1791:     */   protected String collectionsRemoved$str()
/* 1792:     */   {
/* 1793:1731 */     return "Collections removed: %s";
/* 1794:     */   }
/* 1795:     */   
/* 1796:     */   public final void unableToCloseSessionButSwallowingError(HibernateException e)
/* 1797:     */   {
/* 1798:1736 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000425: " + unableToCloseSessionButSwallowingError$str(), e);
/* 1799:     */   }
/* 1800:     */   
/* 1801:     */   protected String unableToCloseSessionButSwallowingError$str()
/* 1802:     */   {
/* 1803:1740 */     return "Could not close session; swallowing exception[%s] as transaction completed";
/* 1804:     */   }
/* 1805:     */   
/* 1806:     */   public final void requiredDifferentProvider(String provider)
/* 1807:     */   {
/* 1808:1745 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000226: " + requiredDifferentProvider$str(), provider);
/* 1809:     */   }
/* 1810:     */   
/* 1811:     */   protected String requiredDifferentProvider$str()
/* 1812:     */   {
/* 1813:1749 */     return "Required a different provider: %s";
/* 1814:     */   }
/* 1815:     */   
/* 1816:     */   public final void sessionsOpened(long sessionOpenCount)
/* 1817:     */   {
/* 1818:1754 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000242: " + sessionsOpened$str(), Long.valueOf(sessionOpenCount));
/* 1819:     */   }
/* 1820:     */   
/* 1821:     */   protected String sessionsOpened$str()
/* 1822:     */   {
/* 1823:1758 */     return "Sessions opened: %s";
/* 1824:     */   }
/* 1825:     */   
/* 1826:     */   public final void collectionsRecreated(long collectionRecreateCount)
/* 1827:     */   {
/* 1828:1763 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000034: " + collectionsRecreated$str(), Long.valueOf(collectionRecreateCount));
/* 1829:     */   }
/* 1830:     */   
/* 1831:     */   protected String collectionsRecreated$str()
/* 1832:     */   {
/* 1833:1767 */     return "Collections recreated: %s";
/* 1834:     */   }
/* 1835:     */   
/* 1836:     */   public final void jdbcIsolationLevel(String isolationLevelToString)
/* 1837:     */   {
/* 1838:1772 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000149: " + jdbcIsolationLevel$str(), isolationLevelToString);
/* 1839:     */   }
/* 1840:     */   
/* 1841:     */   protected String jdbcIsolationLevel$str()
/* 1842:     */   {
/* 1843:1776 */     return "JDBC isolation level: %s";
/* 1844:     */   }
/* 1845:     */   
/* 1846:     */   public final String unableToPerformJdbcCommit()
/* 1847:     */   {
/* 1848:1781 */     String result = String.format("HHH000345: " + unableToPerformJdbcCommit$str(), new Object[0]);
/* 1849:1782 */     return result;
/* 1850:     */   }
/* 1851:     */   
/* 1852:     */   protected String unableToPerformJdbcCommit$str()
/* 1853:     */   {
/* 1854:1786 */     return "JDBC commit failed";
/* 1855:     */   }
/* 1856:     */   
/* 1857:     */   public final void unableToRetrieveTypeInfoResultSet(String string)
/* 1858:     */   {
/* 1859:1791 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000362: " + unableToRetrieveTypeInfoResultSet$str(), string);
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   protected String unableToRetrieveTypeInfoResultSet$str()
/* 1863:     */   {
/* 1864:1795 */     return "Unable to retrieve type info result set : %s";
/* 1865:     */   }
/* 1866:     */   
/* 1867:     */   public final void deprecatedTransactionManagerStrategy(String name, String transactionManagerStrategy, String name2, String jtaPlatform)
/* 1868:     */   {
/* 1869:1800 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000427: " + deprecatedTransactionManagerStrategy$str(), new Object[] { name, transactionManagerStrategy, name2, jtaPlatform });
/* 1870:     */   }
/* 1871:     */   
/* 1872:     */   protected String deprecatedTransactionManagerStrategy$str()
/* 1873:     */   {
/* 1874:1804 */     return "Using deprecated %s strategy [%s], use newer %s strategy instead [%s]";
/* 1875:     */   }
/* 1876:     */   
/* 1877:     */   public final void processingPersistenceUnitInfoName(String persistenceUnitName)
/* 1878:     */   {
/* 1879:1809 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000204: " + processingPersistenceUnitInfoName$str(), persistenceUnitName);
/* 1880:     */   }
/* 1881:     */   
/* 1882:     */   protected String processingPersistenceUnitInfoName$str()
/* 1883:     */   {
/* 1884:1813 */     return "Processing PersistenceUnitInfo [\n\tname: %s\n\t...]";
/* 1885:     */   }
/* 1886:     */   
/* 1887:     */   public final void writeLocksNotSupported(String entityName)
/* 1888:     */   {
/* 1889:1818 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000416: " + writeLocksNotSupported$str(), entityName);
/* 1890:     */   }
/* 1891:     */   
/* 1892:     */   protected String writeLocksNotSupported$str()
/* 1893:     */   {
/* 1894:1822 */     return "Write locks via update not supported for non-versioned entities [%s]";
/* 1895:     */   }
/* 1896:     */   
/* 1897:     */   public final void unableToExecuteBatch(String message)
/* 1898:     */   {
/* 1899:1827 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000315: " + unableToExecuteBatch$str(), message);
/* 1900:     */   }
/* 1901:     */   
/* 1902:     */   protected String unableToExecuteBatch$str()
/* 1903:     */   {
/* 1904:1831 */     return "Exception executing batch [%s]";
/* 1905:     */   }
/* 1906:     */   
/* 1907:     */   public final void startTime(long startTime)
/* 1908:     */   {
/* 1909:1836 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000251: " + startTime$str(), Long.valueOf(startTime));
/* 1910:     */   }
/* 1911:     */   
/* 1912:     */   protected String startTime$str()
/* 1913:     */   {
/* 1914:1840 */     return "Start time: %s";
/* 1915:     */   }
/* 1916:     */   
/* 1917:     */   public final void invalidDiscriminatorAnnotation(String className)
/* 1918:     */   {
/* 1919:1845 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000133: " + invalidDiscriminatorAnnotation$str(), className);
/* 1920:     */   }
/* 1921:     */   
/* 1922:     */   protected String invalidDiscriminatorAnnotation$str()
/* 1923:     */   {
/* 1924:1849 */     return "Discriminator column has to be defined in the root entity, it will be ignored in subclass: %s";
/* 1925:     */   }
/* 1926:     */   
/* 1927:     */   public final void unableToConfigureSqlExceptionConverter(HibernateException e)
/* 1928:     */   {
/* 1929:1854 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000301: " + unableToConfigureSqlExceptionConverter$str(), e);
/* 1930:     */   }
/* 1931:     */   
/* 1932:     */   protected String unableToConfigureSqlExceptionConverter$str()
/* 1933:     */   {
/* 1934:1858 */     return "Unable to configure SQLExceptionConverter : %s";
/* 1935:     */   }
/* 1936:     */   
/* 1937:     */   public final void unsuccessfulCreate(String string)
/* 1938:     */   {
/* 1939:1863 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000389: " + unsuccessfulCreate$str(), string);
/* 1940:     */   }
/* 1941:     */   
/* 1942:     */   protected String unsuccessfulCreate$str()
/* 1943:     */   {
/* 1944:1867 */     return "Unsuccessful: %s";
/* 1945:     */   }
/* 1946:     */   
/* 1947:     */   public final void statementsPrepared(long prepareStatementCount)
/* 1948:     */   {
/* 1949:1872 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000253: " + statementsPrepared$str(), Long.valueOf(prepareStatementCount));
/* 1950:     */   }
/* 1951:     */   
/* 1952:     */   protected String statementsPrepared$str()
/* 1953:     */   {
/* 1954:1876 */     return "Statements prepared: %s";
/* 1955:     */   }
/* 1956:     */   
/* 1957:     */   public final void unableToCloseIterator(SQLException e)
/* 1958:     */   {
/* 1959:1881 */     this.log.logf(FQCN, Logger.Level.INFO, e, "HHH000289: " + unableToCloseIterator$str(), new Object[0]);
/* 1960:     */   }
/* 1961:     */   
/* 1962:     */   protected String unableToCloseIterator$str()
/* 1963:     */   {
/* 1964:1885 */     return "Unable to close iterator";
/* 1965:     */   }
/* 1966:     */   
/* 1967:     */   public final void unableToMarkForRollbackOnTransientObjectException(Exception e)
/* 1968:     */   {
/* 1969:1890 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000338: " + unableToMarkForRollbackOnTransientObjectException$str(), new Object[0]);
/* 1970:     */   }
/* 1971:     */   
/* 1972:     */   protected String unableToMarkForRollbackOnTransientObjectException$str()
/* 1973:     */   {
/* 1974:1894 */     return "Unable to mark for rollback on TransientObjectException: ";
/* 1975:     */   }
/* 1976:     */   
/* 1977:     */   public final void illegalPropertyGetterArgument(String name, String propertyName)
/* 1978:     */   {
/* 1979:1899 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000122: " + illegalPropertyGetterArgument$str(), name, propertyName);
/* 1980:     */   }
/* 1981:     */   
/* 1982:     */   protected String illegalPropertyGetterArgument$str()
/* 1983:     */   {
/* 1984:1903 */     return "IllegalArgumentException in class: %s, getter method of property: %s";
/* 1985:     */   }
/* 1986:     */   
/* 1987:     */   public final void unregisteredResultSetWithoutStatement()
/* 1988:     */   {
/* 1989:1908 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000386: " + unregisteredResultSetWithoutStatement$str(), new Object[0]);
/* 1990:     */   }
/* 1991:     */   
/* 1992:     */   protected String unregisteredResultSetWithoutStatement$str()
/* 1993:     */   {
/* 1994:1912 */     return "ResultSet had no statement associated with it, but was not yet registered";
/* 1995:     */   }
/* 1996:     */   
/* 1997:     */   public final void unableToStopHibernateService(Exception e)
/* 1998:     */   {
/* 1999:1917 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000368: " + unableToStopHibernateService$str(), new Object[0]);
/* 2000:     */   }
/* 2001:     */   
/* 2002:     */   protected String unableToStopHibernateService$str()
/* 2003:     */   {
/* 2004:1921 */     return "Exception while stopping service";
/* 2005:     */   }
/* 2006:     */   
/* 2007:     */   public final void forcingTableUse()
/* 2008:     */   {
/* 2009:1926 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000107: " + forcingTableUse$str(), new Object[0]);
/* 2010:     */   }
/* 2011:     */   
/* 2012:     */   protected String forcingTableUse$str()
/* 2013:     */   {
/* 2014:1930 */     return "Forcing table use for sequence-style generator due to pooled optimizer selection where db does not support pooled sequences";
/* 2015:     */   }
/* 2016:     */   
/* 2017:     */   public final void entitiesInserted(long entityInsertCount)
/* 2018:     */   {
/* 2019:1935 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000078: " + entitiesInserted$str(), Long.valueOf(entityInsertCount));
/* 2020:     */   }
/* 2021:     */   
/* 2022:     */   protected String entitiesInserted$str()
/* 2023:     */   {
/* 2024:1939 */     return "Entities inserted: %s";
/* 2025:     */   }
/* 2026:     */   
/* 2027:     */   public final void jdbcDriverNotSpecified(String driver)
/* 2028:     */   {
/* 2029:1944 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000148: " + jdbcDriverNotSpecified$str(), driver);
/* 2030:     */   }
/* 2031:     */   
/* 2032:     */   protected String jdbcDriverNotSpecified$str()
/* 2033:     */   {
/* 2034:1948 */     return "No JDBC Driver class was specified by property %s";
/* 2035:     */   }
/* 2036:     */   
/* 2037:     */   public final void duplicateImport(String entityName, String rename)
/* 2038:     */   {
/* 2039:1953 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000071: " + duplicateImport$str(), entityName, rename);
/* 2040:     */   }
/* 2041:     */   
/* 2042:     */   protected String duplicateImport$str()
/* 2043:     */   {
/* 2044:1957 */     return "Duplicate import: %s -> %s";
/* 2045:     */   }
/* 2046:     */   
/* 2047:     */   public final void unableToReleaseBatchStatement()
/* 2048:     */   {
/* 2049:1962 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000352: " + unableToReleaseBatchStatement$str(), new Object[0]);
/* 2050:     */   }
/* 2051:     */   
/* 2052:     */   protected String unableToReleaseBatchStatement$str()
/* 2053:     */   {
/* 2054:1966 */     return "Unable to release batch statement...";
/* 2055:     */   }
/* 2056:     */   
/* 2057:     */   public final void sessionsClosed(long sessionCloseCount)
/* 2058:     */   {
/* 2059:1971 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000241: " + sessionsClosed$str(), Long.valueOf(sessionCloseCount));
/* 2060:     */   }
/* 2061:     */   
/* 2062:     */   protected String sessionsClosed$str()
/* 2063:     */   {
/* 2064:1975 */     return "Sessions closed: %s";
/* 2065:     */   }
/* 2066:     */   
/* 2067:     */   public final void unexpectedRowCounts()
/* 2068:     */   {
/* 2069:1980 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000381: " + unexpectedRowCounts$str(), new Object[0]);
/* 2070:     */   }
/* 2071:     */   
/* 2072:     */   protected String unexpectedRowCounts$str()
/* 2073:     */   {
/* 2074:1984 */     return "JDBC driver did not return the expected number of row counts";
/* 2075:     */   }
/* 2076:     */   
/* 2077:     */   public final void stoppingService()
/* 2078:     */   {
/* 2079:1989 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000255: " + stoppingService$str(), new Object[0]);
/* 2080:     */   }
/* 2081:     */   
/* 2082:     */   protected String stoppingService$str()
/* 2083:     */   {
/* 2084:1993 */     return "Stopping service";
/* 2085:     */   }
/* 2086:     */   
/* 2087:     */   public final void unableToReleaseContext(String message)
/* 2088:     */   {
/* 2089:1998 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000354: " + unableToReleaseContext$str(), message);
/* 2090:     */   }
/* 2091:     */   
/* 2092:     */   protected String unableToReleaseContext$str()
/* 2093:     */   {
/* 2094:2002 */     return "Unable to release initial context: %s";
/* 2095:     */   }
/* 2096:     */   
/* 2097:     */   public final void expired(Object key)
/* 2098:     */   {
/* 2099:2007 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000092: " + expired$str(), key);
/* 2100:     */   }
/* 2101:     */   
/* 2102:     */   protected String expired$str()
/* 2103:     */   {
/* 2104:2011 */     return "An item was expired by the cache while it was locked (increase your cache timeout): %s";
/* 2105:     */   }
/* 2106:     */   
/* 2107:     */   public final void unableToObjectConnectionMetadata(SQLException error)
/* 2108:     */   {
/* 2109:2016 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000339: " + unableToObjectConnectionMetadata$str(), error);
/* 2110:     */   }
/* 2111:     */   
/* 2112:     */   protected String unableToObjectConnectionMetadata$str()
/* 2113:     */   {
/* 2114:2020 */     return "Could not obtain connection metadata: %s";
/* 2115:     */   }
/* 2116:     */   
/* 2117:     */   public final void unableToCloseInputStreamForResource(String resourceName, IOException e)
/* 2118:     */   {
/* 2119:2025 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000288: " + unableToCloseInputStreamForResource$str(), resourceName);
/* 2120:     */   }
/* 2121:     */   
/* 2122:     */   protected String unableToCloseInputStreamForResource$str()
/* 2123:     */   {
/* 2124:2029 */     return "Could not close input stream for %s";
/* 2125:     */   }
/* 2126:     */   
/* 2127:     */   public final void legacyTransactionManagerStrategy(String name, String jtaPlatform)
/* 2128:     */   {
/* 2129:2034 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000428: " + legacyTransactionManagerStrategy$str(), name, jtaPlatform);
/* 2130:     */   }
/* 2131:     */   
/* 2132:     */   protected String legacyTransactionManagerStrategy$str()
/* 2133:     */   {
/* 2134:2038 */     return "Encountered legacy TransactionManagerLookup specified; convert to newer %s contract specified via %s setting";
/* 2135:     */   }
/* 2136:     */   
/* 2137:     */   public final void missingEntityAnnotation(String className)
/* 2138:     */   {
/* 2139:2043 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000175: " + missingEntityAnnotation$str(), className);
/* 2140:     */   }
/* 2141:     */   
/* 2142:     */   protected String missingEntityAnnotation$str()
/* 2143:     */   {
/* 2144:2047 */     return "Class annotated @org.hibernate.annotations.Entity but not javax.persistence.Entity (most likely a user error): %s";
/* 2145:     */   }
/* 2146:     */   
/* 2147:     */   public final void honoringOptimizerSetting(String none, String incrementParam, int incrementSize)
/* 2148:     */   {
/* 2149:2052 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000116: " + honoringOptimizerSetting$str(), none, incrementParam, Integer.valueOf(incrementSize));
/* 2150:     */   }
/* 2151:     */   
/* 2152:     */   protected String honoringOptimizerSetting$str()
/* 2153:     */   {
/* 2154:2056 */     return "Config specified explicit optimizer of [%s], but [%s=%s; honoring optimizer setting";
/* 2155:     */   }
/* 2156:     */   
/* 2157:     */   public final void deprecatedForceDescriminatorAnnotation()
/* 2158:     */   {
/* 2159:2061 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000062: " + deprecatedForceDescriminatorAnnotation$str(), new Object[0]);
/* 2160:     */   }
/* 2161:     */   
/* 2162:     */   protected String deprecatedForceDescriminatorAnnotation$str()
/* 2163:     */   {
/* 2164:2065 */     return "@ForceDiscriminator is deprecated use @DiscriminatorOptions instead.";
/* 2165:     */   }
/* 2166:     */   
/* 2167:     */   public final void bytecodeProvider(String provider)
/* 2168:     */   {
/* 2169:2070 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000021: " + bytecodeProvider$str(), provider);
/* 2170:     */   }
/* 2171:     */   
/* 2172:     */   protected String bytecodeProvider$str()
/* 2173:     */   {
/* 2174:2074 */     return "Bytecode provider name : %s";
/* 2175:     */   }
/* 2176:     */   
/* 2177:     */   public final void connectionsObtained(long connectCount)
/* 2178:     */   {
/* 2179:2079 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000048: " + connectionsObtained$str(), Long.valueOf(connectCount));
/* 2180:     */   }
/* 2181:     */   
/* 2182:     */   protected String connectionsObtained$str()
/* 2183:     */   {
/* 2184:2083 */     return "Connections obtained: %s";
/* 2185:     */   }
/* 2186:     */   
/* 2187:     */   public final void setManagerLookupClass()
/* 2188:     */   {
/* 2189:2088 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000426: " + setManagerLookupClass$str(), new Object[0]);
/* 2190:     */   }
/* 2191:     */   
/* 2192:     */   protected String setManagerLookupClass$str()
/* 2193:     */   {
/* 2194:2092 */     return "You should set hibernate.transaction.manager_lookup_class if cache is enabled";
/* 2195:     */   }
/* 2196:     */   
/* 2197:     */   public final String unableToQueryDatabaseMetadata()
/* 2198:     */   {
/* 2199:2097 */     String result = String.format("HHH000347: " + unableToQueryDatabaseMetadata$str(), new Object[0]);
/* 2200:2098 */     return result;
/* 2201:     */   }
/* 2202:     */   
/* 2203:     */   protected String unableToQueryDatabaseMetadata$str()
/* 2204:     */   {
/* 2205:2102 */     return "Unable to query java.sql.DatabaseMetaData";
/* 2206:     */   }
/* 2207:     */   
/* 2208:     */   public final void collectionsLoaded(long collectionLoadCount)
/* 2209:     */   {
/* 2210:2107 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000033: " + collectionsLoaded$str(), Long.valueOf(collectionLoadCount));
/* 2211:     */   }
/* 2212:     */   
/* 2213:     */   protected String collectionsLoaded$str()
/* 2214:     */   {
/* 2215:2111 */     return "Collections loaded: %s";
/* 2216:     */   }
/* 2217:     */   
/* 2218:     */   public final void transactionStrategy(String strategyClassName)
/* 2219:     */   {
/* 2220:2116 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000268: " + transactionStrategy$str(), strategyClassName);
/* 2221:     */   }
/* 2222:     */   
/* 2223:     */   protected String transactionStrategy$str()
/* 2224:     */   {
/* 2225:2120 */     return "Transaction strategy: %s";
/* 2226:     */   }
/* 2227:     */   
/* 2228:     */   public final Object unableToUpdateHiValue(String tableName)
/* 2229:     */   {
/* 2230:2125 */     Object result = String.format("HHH000375: " + unableToUpdateHiValue$str(), new Object[] { tableName });
/* 2231:2126 */     return result;
/* 2232:     */   }
/* 2233:     */   
/* 2234:     */   protected String unableToUpdateHiValue$str()
/* 2235:     */   {
/* 2236:2130 */     return "Could not update hi value in: %s";
/* 2237:     */   }
/* 2238:     */   
/* 2239:     */   public final void unableToObtainConnectionMetadata(String message)
/* 2240:     */   {
/* 2241:2135 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000341: " + unableToObtainConnectionMetadata$str(), message);
/* 2242:     */   }
/* 2243:     */   
/* 2244:     */   protected String unableToObtainConnectionMetadata$str()
/* 2245:     */   {
/* 2246:2139 */     return "Could not obtain connection metadata : %s";
/* 2247:     */   }
/* 2248:     */   
/* 2249:     */   public final void typeRegistrationOverridesPrevious(String key, Type old)
/* 2250:     */   {
/* 2251:2144 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000270: " + typeRegistrationOverridesPrevious$str(), key, old);
/* 2252:     */   }
/* 2253:     */   
/* 2254:     */   protected String typeRegistrationOverridesPrevious$str()
/* 2255:     */   {
/* 2256:2148 */     return "Type registration [%s] overrides previous : %s";
/* 2257:     */   }
/* 2258:     */   
/* 2259:     */   public final void noAppropriateConnectionProvider()
/* 2260:     */   {
/* 2261:2153 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000181: " + noAppropriateConnectionProvider$str(), new Object[0]);
/* 2262:     */   }
/* 2263:     */   
/* 2264:     */   protected String noAppropriateConnectionProvider$str()
/* 2265:     */   {
/* 2266:2157 */     return "No appropriate connection provider encountered, assuming application will be supplying connections";
/* 2267:     */   }
/* 2268:     */   
/* 2269:     */   public final void overridingTransactionStrategyDangerous(String transactionStrategy)
/* 2270:     */   {
/* 2271:2162 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000193: " + overridingTransactionStrategyDangerous$str(), transactionStrategy);
/* 2272:     */   }
/* 2273:     */   
/* 2274:     */   protected String overridingTransactionStrategyDangerous$str()
/* 2275:     */   {
/* 2276:2166 */     return "Overriding %s is dangerous, this might break the EJB3 specification implementation";
/* 2277:     */   }
/* 2278:     */   
/* 2279:     */   public final void secondLevelCacheHits(long secondLevelCacheHitCount)
/* 2280:     */   {
/* 2281:2171 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000237: " + secondLevelCacheHits$str(), Long.valueOf(secondLevelCacheHitCount));
/* 2282:     */   }
/* 2283:     */   
/* 2284:     */   protected String secondLevelCacheHits$str()
/* 2285:     */   {
/* 2286:2175 */     return "Second level cache hits: %s";
/* 2287:     */   }
/* 2288:     */   
/* 2289:     */   public final void ignoringUnrecognizedQueryHint(String hintName)
/* 2290:     */   {
/* 2291:2180 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000121: " + ignoringUnrecognizedQueryHint$str(), hintName);
/* 2292:     */   }
/* 2293:     */   
/* 2294:     */   protected String ignoringUnrecognizedQueryHint$str()
/* 2295:     */   {
/* 2296:2184 */     return "Ignoring unrecognized query hint [%s]";
/* 2297:     */   }
/* 2298:     */   
/* 2299:     */   public final void hsqldbSupportsOnlyReadCommittedIsolation()
/* 2300:     */   {
/* 2301:2189 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000118: " + hsqldbSupportsOnlyReadCommittedIsolation$str(), new Object[0]);
/* 2302:     */   }
/* 2303:     */   
/* 2304:     */   protected String hsqldbSupportsOnlyReadCommittedIsolation$str()
/* 2305:     */   {
/* 2306:2193 */     return "HSQLDB supports only READ_UNCOMMITTED isolation";
/* 2307:     */   }
/* 2308:     */   
/* 2309:     */   public final void sqlExceptionEscapedProxy(SQLException e)
/* 2310:     */   {
/* 2311:2198 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000246: " + sqlExceptionEscapedProxy$str(), new Object[0]);
/* 2312:     */   }
/* 2313:     */   
/* 2314:     */   protected String sqlExceptionEscapedProxy$str()
/* 2315:     */   {
/* 2316:2202 */     return "SQLException escaped proxy";
/* 2317:     */   }
/* 2318:     */   
/* 2319:     */   public final void unableToSetTransactionToRollbackOnly(SystemException e)
/* 2320:     */   {
/* 2321:2207 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000367: " + unableToSetTransactionToRollbackOnly$str(), new Object[0]);
/* 2322:     */   }
/* 2323:     */   
/* 2324:     */   protected String unableToSetTransactionToRollbackOnly$str()
/* 2325:     */   {
/* 2326:2211 */     return "Could not set transaction to rollback only";
/* 2327:     */   }
/* 2328:     */   
/* 2329:     */   public final void unableToLocateUuidGenerationStrategy(String strategyClassName)
/* 2330:     */   {
/* 2331:2216 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000334: " + unableToLocateUuidGenerationStrategy$str(), strategyClassName);
/* 2332:     */   }
/* 2333:     */   
/* 2334:     */   protected String unableToLocateUuidGenerationStrategy$str()
/* 2335:     */   {
/* 2336:2220 */     return "Unable to locate requested UUID generation strategy class : %s";
/* 2337:     */   }
/* 2338:     */   
/* 2339:     */   public final void sqlWarning(int errorCode, String sqlState)
/* 2340:     */   {
/* 2341:2225 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000247: " + sqlWarning$str(), Integer.valueOf(errorCode), sqlState);
/* 2342:     */   }
/* 2343:     */   
/* 2344:     */   protected String sqlWarning$str()
/* 2345:     */   {
/* 2346:2229 */     return "SQL Error: %s, SQLState: %s";
/* 2347:     */   }
/* 2348:     */   
/* 2349:     */   public final void unableToLogSqlWarnings(SQLException sqle)
/* 2350:     */   {
/* 2351:2234 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000335: " + unableToLogSqlWarnings$str(), sqle);
/* 2352:     */   }
/* 2353:     */   
/* 2354:     */   protected String unableToLogSqlWarnings$str()
/* 2355:     */   {
/* 2356:2238 */     return "Unable to log SQLWarnings : %s";
/* 2357:     */   }
/* 2358:     */   
/* 2359:     */   public final void configuredSessionFactory(String name)
/* 2360:     */   {
/* 2361:2243 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000041: " + configuredSessionFactory$str(), name);
/* 2362:     */   }
/* 2363:     */   
/* 2364:     */   protected String configuredSessionFactory$str()
/* 2365:     */   {
/* 2366:2247 */     return "Configured SessionFactory: %s";
/* 2367:     */   }
/* 2368:     */   
/* 2369:     */   public final void parsingXmlError(int lineNumber, String message)
/* 2370:     */   {
/* 2371:2252 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000196: " + parsingXmlError$str(), Integer.valueOf(lineNumber), message);
/* 2372:     */   }
/* 2373:     */   
/* 2374:     */   protected String parsingXmlError$str()
/* 2375:     */   {
/* 2376:2256 */     return "Error parsing XML (%s) : %s";
/* 2377:     */   }
/* 2378:     */   
/* 2379:     */   public final void runningSchemaValidator()
/* 2380:     */   {
/* 2381:2261 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000229: " + runningSchemaValidator$str(), new Object[0]);
/* 2382:     */   }
/* 2383:     */   
/* 2384:     */   protected String runningSchemaValidator$str()
/* 2385:     */   {
/* 2386:2265 */     return "Running schema validator";
/* 2387:     */   }
/* 2388:     */   
/* 2389:     */   public final void unableToSynchronizeDatabaseStateWithSession(HibernateException he)
/* 2390:     */   {
/* 2391:2270 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000371: " + unableToSynchronizeDatabaseStateWithSession$str(), he);
/* 2392:     */   }
/* 2393:     */   
/* 2394:     */   protected String unableToSynchronizeDatabaseStateWithSession$str()
/* 2395:     */   {
/* 2396:2274 */     return "Could not synchronize database state with session: %s";
/* 2397:     */   }
/* 2398:     */   
/* 2399:     */   public final void unableToUnbindFactoryFromJndi(JndiException e)
/* 2400:     */   {
/* 2401:2279 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000374: " + unableToUnbindFactoryFromJndi$str(), new Object[0]);
/* 2402:     */   }
/* 2403:     */   
/* 2404:     */   protected String unableToUnbindFactoryFromJndi$str()
/* 2405:     */   {
/* 2406:2283 */     return "Could not unbind factory from JNDI";
/* 2407:     */   }
/* 2408:     */   
/* 2409:     */   public final void unableToLoadCommand(HibernateException e)
/* 2410:     */   {
/* 2411:2288 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000327: " + unableToLoadCommand$str(), e);
/* 2412:     */   }
/* 2413:     */   
/* 2414:     */   protected String unableToLoadCommand$str()
/* 2415:     */   {
/* 2416:2292 */     return "Error performing load command : %s";
/* 2417:     */   }
/* 2418:     */   
/* 2419:     */   public final void cleaningUpConnectionPool(String url)
/* 2420:     */   {
/* 2421:2297 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000030: " + cleaningUpConnectionPool$str(), url);
/* 2422:     */   }
/* 2423:     */   
/* 2424:     */   protected String cleaningUpConnectionPool$str()
/* 2425:     */   {
/* 2426:2301 */     return "Cleaning up connection pool [%s]";
/* 2427:     */   }
/* 2428:     */   
/* 2429:     */   public final String jdbcRollbackFailed()
/* 2430:     */   {
/* 2431:2306 */     String result = String.format("HHH000151: " + jdbcRollbackFailed$str(), new Object[0]);
/* 2432:2307 */     return result;
/* 2433:     */   }
/* 2434:     */   
/* 2435:     */   protected String jdbcRollbackFailed$str()
/* 2436:     */   {
/* 2437:2311 */     return "JDBC rollback failed";
/* 2438:     */   }
/* 2439:     */   
/* 2440:     */   public final void runningHbm2ddlSchemaExport()
/* 2441:     */   {
/* 2442:2316 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000227: " + runningHbm2ddlSchemaExport$str(), new Object[0]);
/* 2443:     */   }
/* 2444:     */   
/* 2445:     */   protected String runningHbm2ddlSchemaExport$str()
/* 2446:     */   {
/* 2447:2320 */     return "Running hbm2ddl schema export";
/* 2448:     */   }
/* 2449:     */   
/* 2450:     */   public final void unableToLocateMBeanServer()
/* 2451:     */   {
/* 2452:2325 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000332: " + unableToLocateMBeanServer$str(), new Object[0]);
/* 2453:     */   }
/* 2454:     */   
/* 2455:     */   protected String unableToLocateMBeanServer$str()
/* 2456:     */   {
/* 2457:2329 */     return "Unable to locate MBeanServer on JMX service shutdown";
/* 2458:     */   }
/* 2459:     */   
/* 2460:     */   public final void transactions(long transactionCount)
/* 2461:     */   {
/* 2462:2334 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000266: " + transactions$str(), Long.valueOf(transactionCount));
/* 2463:     */   }
/* 2464:     */   
/* 2465:     */   protected String transactions$str()
/* 2466:     */   {
/* 2467:2338 */     return "Transactions: %s";
/* 2468:     */   }
/* 2469:     */   
/* 2470:     */   public final void batchContainedStatementsOnRelease()
/* 2471:     */   {
/* 2472:2343 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000010: " + batchContainedStatementsOnRelease$str(), new Object[0]);
/* 2473:     */   }
/* 2474:     */   
/* 2475:     */   protected String batchContainedStatementsOnRelease$str()
/* 2476:     */   {
/* 2477:2347 */     return "On release of batch it still contained JDBC statements";
/* 2478:     */   }
/* 2479:     */   
/* 2480:     */   public final void optimisticLockFailures(long optimisticFailureCount)
/* 2481:     */   {
/* 2482:2352 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000187: " + optimisticLockFailures$str(), Long.valueOf(optimisticFailureCount));
/* 2483:     */   }
/* 2484:     */   
/* 2485:     */   protected String optimisticLockFailures$str()
/* 2486:     */   {
/* 2487:2356 */     return "Optimistic lock failures: %s";
/* 2488:     */   }
/* 2489:     */   
/* 2490:     */   public final void resolvedSqlTypeDescriptorForDifferentSqlCode(String name, String valueOf, String name2, String valueOf2)
/* 2491:     */   {
/* 2492:2361 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000419: " + resolvedSqlTypeDescriptorForDifferentSqlCode$str(), new Object[] { name, valueOf, name2, valueOf2 });
/* 2493:     */   }
/* 2494:     */   
/* 2495:     */   protected String resolvedSqlTypeDescriptorForDifferentSqlCode$str()
/* 2496:     */   {
/* 2497:2365 */     return "Resolved SqlTypeDescriptor is for a different SQL code. %s has sqlCode=%s; type override %s has sqlCode=%s";
/* 2498:     */   }
/* 2499:     */   
/* 2500:     */   public final void unableToRetrieveCache(String namespace, String message)
/* 2501:     */   {
/* 2502:2370 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000361: " + unableToRetrieveCache$str(), namespace, message);
/* 2503:     */   }
/* 2504:     */   
/* 2505:     */   protected String unableToRetrieveCache$str()
/* 2506:     */   {
/* 2507:2374 */     return "Unable to retreive cache from JNDI [%s]: %s";
/* 2508:     */   }
/* 2509:     */   
/* 2510:     */   public final void usingReflectionOptimizer()
/* 2511:     */   {
/* 2512:2379 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000406: " + usingReflectionOptimizer$str(), new Object[0]);
/* 2513:     */   }
/* 2514:     */   
/* 2515:     */   protected String usingReflectionOptimizer$str()
/* 2516:     */   {
/* 2517:2383 */     return "Using bytecode reflection optimizer";
/* 2518:     */   }
/* 2519:     */   
/* 2520:     */   public final String jdbcUrlNotSpecified(String url)
/* 2521:     */   {
/* 2522:2388 */     String result = String.format("HHH000152: " + jdbcUrlNotSpecified$str(), new Object[] { url });
/* 2523:2389 */     return result;
/* 2524:     */   }
/* 2525:     */   
/* 2526:     */   protected String jdbcUrlNotSpecified$str()
/* 2527:     */   {
/* 2528:2393 */     return "JDBC URL was not specified by property %s";
/* 2529:     */   }
/* 2530:     */   
/* 2531:     */   public final void timestampCacheHits(long updateTimestampsCachePutCount)
/* 2532:     */   {
/* 2533:2398 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000434: " + timestampCacheHits$str(), Long.valueOf(updateTimestampsCachePutCount));
/* 2534:     */   }
/* 2535:     */   
/* 2536:     */   protected String timestampCacheHits$str()
/* 2537:     */   {
/* 2538:2402 */     return "update timestamps cache hits: %s";
/* 2539:     */   }
/* 2540:     */   
/* 2541:     */   public final void callingJoinTransactionOnNonJtaEntityManager()
/* 2542:     */   {
/* 2543:2407 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000027: " + callingJoinTransactionOnNonJtaEntityManager$str(), new Object[0]);
/* 2544:     */   }
/* 2545:     */   
/* 2546:     */   protected String callingJoinTransactionOnNonJtaEntityManager$str()
/* 2547:     */   {
/* 2548:2411 */     return "Calling joinTransaction() on a non JTA EntityManager";
/* 2549:     */   }
/* 2550:     */   
/* 2551:     */   public final void unableToRollbackIsolatedTransaction(Exception e, Exception ignore)
/* 2552:     */   {
/* 2553:2416 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000364: " + unableToRollbackIsolatedTransaction$str(), e, ignore);
/* 2554:     */   }
/* 2555:     */   
/* 2556:     */   protected String unableToRollbackIsolatedTransaction$str()
/* 2557:     */   {
/* 2558:2420 */     return "Unable to rollback isolated transaction on error [%s] : [%s]";
/* 2559:     */   }
/* 2560:     */   
/* 2561:     */   public final void unableToCloseInputFiles(String name, IOException e)
/* 2562:     */   {
/* 2563:2425 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000286: " + unableToCloseInputFiles$str(), name);
/* 2564:     */   }
/* 2565:     */   
/* 2566:     */   protected String unableToCloseInputFiles$str()
/* 2567:     */   {
/* 2568:2429 */     return "Error closing input files: %s";
/* 2569:     */   }
/* 2570:     */   
/* 2571:     */   public final void unableToAccessSessionFactory(String sfJNDIName, NamingException e)
/* 2572:     */   {
/* 2573:2434 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000272: " + unableToAccessSessionFactory$str(), sfJNDIName);
/* 2574:     */   }
/* 2575:     */   
/* 2576:     */   protected String unableToAccessSessionFactory$str()
/* 2577:     */   {
/* 2578:2438 */     return "Error while accessing session factory with JNDI name %s";
/* 2579:     */   }
/* 2580:     */   
/* 2581:     */   public final void firstOrMaxResultsSpecifiedWithCollectionFetch()
/* 2582:     */   {
/* 2583:2443 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000104: " + firstOrMaxResultsSpecifiedWithCollectionFetch$str(), new Object[0]);
/* 2584:     */   }
/* 2585:     */   
/* 2586:     */   protected String firstOrMaxResultsSpecifiedWithCollectionFetch$str()
/* 2587:     */   {
/* 2588:2447 */     return "firstResult/maxResults specified with collection fetch; applying in memory!";
/* 2589:     */   }
/* 2590:     */   
/* 2591:     */   public final void exceptionInAfterTransactionCompletionInterceptor(Throwable e)
/* 2592:     */   {
/* 2593:2452 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000087: " + exceptionInAfterTransactionCompletionInterceptor$str(), new Object[0]);
/* 2594:     */   }
/* 2595:     */   
/* 2596:     */   protected String exceptionInAfterTransactionCompletionInterceptor$str()
/* 2597:     */   {
/* 2598:2456 */     return "Exception in interceptor afterTransactionCompletion()";
/* 2599:     */   }
/* 2600:     */   
/* 2601:     */   public final void unableToReadOrInitHiValue(SQLException e)
/* 2602:     */   {
/* 2603:2461 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000351: " + unableToReadOrInitHiValue$str(), new Object[0]);
/* 2604:     */   }
/* 2605:     */   
/* 2606:     */   protected String unableToReadOrInitHiValue$str()
/* 2607:     */   {
/* 2608:2465 */     return "Could not read or init a hi value";
/* 2609:     */   }
/* 2610:     */   
/* 2611:     */   public final String unableToReadHiValue(String tableName)
/* 2612:     */   {
/* 2613:2470 */     String result = String.format("HHH000350: " + unableToReadHiValue$str(), new Object[] { tableName });
/* 2614:2471 */     return result;
/* 2615:     */   }
/* 2616:     */   
/* 2617:     */   protected String unableToReadHiValue$str()
/* 2618:     */   {
/* 2619:2475 */     return "Could not read a hi value - you need to populate the table: %s";
/* 2620:     */   }
/* 2621:     */   
/* 2622:     */   public final void disablingContextualLOBCreationSinceOldJdbcVersion(int jdbcMajorVersion)
/* 2623:     */   {
/* 2624:2480 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000423: " + disablingContextualLOBCreationSinceOldJdbcVersion$str(), Integer.valueOf(jdbcMajorVersion));
/* 2625:     */   }
/* 2626:     */   
/* 2627:     */   protected String disablingContextualLOBCreationSinceOldJdbcVersion$str()
/* 2628:     */   {
/* 2629:2484 */     return "Disabling contextual LOB creation as JDBC driver reported JDBC version [%s] less than 4";
/* 2630:     */   }
/* 2631:     */   
/* 2632:     */   public final void containsJoinFetchedCollection(String role)
/* 2633:     */   {
/* 2634:2489 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000051: " + containsJoinFetchedCollection$str(), role);
/* 2635:     */   }
/* 2636:     */   
/* 2637:     */   protected String containsJoinFetchedCollection$str()
/* 2638:     */   {
/* 2639:2493 */     return "Ignoring bag join fetch [%s] due to prior collection join fetch";
/* 2640:     */   }
/* 2641:     */   
/* 2642:     */   public final void unableToApplyConstraints(String className, Exception e)
/* 2643:     */   {
/* 2644:2498 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000274: " + unableToApplyConstraints$str(), className);
/* 2645:     */   }
/* 2646:     */   
/* 2647:     */   protected String unableToApplyConstraints$str()
/* 2648:     */   {
/* 2649:2502 */     return "Unable to apply constraints on DDL for %s";
/* 2650:     */   }
/* 2651:     */   
/* 2652:     */   public final void warningsCreatingTempTable(SQLWarning warning)
/* 2653:     */   {
/* 2654:2507 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000413: " + warningsCreatingTempTable$str(), warning);
/* 2655:     */   }
/* 2656:     */   
/* 2657:     */   protected String warningsCreatingTempTable$str()
/* 2658:     */   {
/* 2659:2511 */     return "Warnings creating temp table : %s";
/* 2660:     */   }
/* 2661:     */   
/* 2662:     */   public final void unableToResolveAggregateFunction(String name)
/* 2663:     */   {
/* 2664:2516 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000359: " + unableToResolveAggregateFunction$str(), name);
/* 2665:     */   }
/* 2666:     */   
/* 2667:     */   protected String unableToResolveAggregateFunction$str()
/* 2668:     */   {
/* 2669:2520 */     return "Could not resolve aggregate function [%s]; using standard definition";
/* 2670:     */   }
/* 2671:     */   
/* 2672:     */   public final void unableToCloseStreamError(IOException error)
/* 2673:     */   {
/* 2674:2525 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000297: " + unableToCloseStreamError$str(), error);
/* 2675:     */   }
/* 2676:     */   
/* 2677:     */   protected String unableToCloseStreamError$str()
/* 2678:     */   {
/* 2679:2529 */     return "Could not close stream on hibernate.properties: %s";
/* 2680:     */   }
/* 2681:     */   
/* 2682:     */   public final void loadingCollectionKeyNotFound(CollectionKey collectionKey)
/* 2683:     */   {
/* 2684:2534 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000159: " + loadingCollectionKeyNotFound$str(), collectionKey);
/* 2685:     */   }
/* 2686:     */   
/* 2687:     */   protected String loadingCollectionKeyNotFound$str()
/* 2688:     */   {
/* 2689:2538 */     return "In CollectionLoadContext#endLoadingCollections, localLoadingCollectionKeys contained [%s], but no LoadingCollectionEntry was found in loadContexts";
/* 2690:     */   }
/* 2691:     */   
/* 2692:     */   public final void exceptionInSubResolver(String message)
/* 2693:     */   {
/* 2694:2543 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000089: " + exceptionInSubResolver$str(), message);
/* 2695:     */   }
/* 2696:     */   
/* 2697:     */   protected String exceptionInSubResolver$str()
/* 2698:     */   {
/* 2699:2547 */     return "Sub-resolver threw unexpected exception, continuing to next : %s";
/* 2700:     */   }
/* 2701:     */   
/* 2702:     */   public final void parsingXmlWarningForFile(String file, int lineNumber, String message)
/* 2703:     */   {
/* 2704:2552 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000199: " + parsingXmlWarningForFile$str(), file, Integer.valueOf(lineNumber), message);
/* 2705:     */   }
/* 2706:     */   
/* 2707:     */   protected String parsingXmlWarningForFile$str()
/* 2708:     */   {
/* 2709:2556 */     return "Warning parsing XML: %s(%s) %s";
/* 2710:     */   }
/* 2711:     */   
/* 2712:     */   public final void startingQueryCache(String region)
/* 2713:     */   {
/* 2714:2561 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000248: " + startingQueryCache$str(), region);
/* 2715:     */   }
/* 2716:     */   
/* 2717:     */   protected String startingQueryCache$str()
/* 2718:     */   {
/* 2719:2565 */     return "Starting query cache at region: %s";
/* 2720:     */   }
/* 2721:     */   
/* 2722:     */   public final void unableToCloseJar(String message)
/* 2723:     */   {
/* 2724:2570 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000290: " + unableToCloseJar$str(), message);
/* 2725:     */   }
/* 2726:     */   
/* 2727:     */   protected String unableToCloseJar$str()
/* 2728:     */   {
/* 2729:2574 */     return "Could not close jar: %s";
/* 2730:     */   }
/* 2731:     */   
/* 2732:     */   public final String unableToLocateConfigFile(String path)
/* 2733:     */   {
/* 2734:2579 */     String result = String.format("HHH000330: " + unableToLocateConfigFile$str(), new Object[] { path });
/* 2735:2580 */     return result;
/* 2736:     */   }
/* 2737:     */   
/* 2738:     */   protected String unableToLocateConfigFile$str()
/* 2739:     */   {
/* 2740:2584 */     return "Unable to locate config file: %s";
/* 2741:     */   }
/* 2742:     */   
/* 2743:     */   public final void unsupportedAfterStatement()
/* 2744:     */   {
/* 2745:2589 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000390: " + unsupportedAfterStatement$str(), new Object[0]);
/* 2746:     */   }
/* 2747:     */   
/* 2748:     */   protected String unsupportedAfterStatement$str()
/* 2749:     */   {
/* 2750:2593 */     return "Overriding release mode as connection provider does not support 'after_statement'";
/* 2751:     */   }
/* 2752:     */   
/* 2753:     */   public final void columns(Set keySet)
/* 2754:     */   {
/* 2755:2598 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000037: " + columns$str(), keySet);
/* 2756:     */   }
/* 2757:     */   
/* 2758:     */   protected String columns$str()
/* 2759:     */   {
/* 2760:2602 */     return "Columns: %s";
/* 2761:     */   }
/* 2762:     */   
/* 2763:     */   public final void illegalPropertySetterArgument(String name, String propertyName)
/* 2764:     */   {
/* 2765:2607 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000123: " + illegalPropertySetterArgument$str(), name, propertyName);
/* 2766:     */   }
/* 2767:     */   
/* 2768:     */   protected String illegalPropertySetterArgument$str()
/* 2769:     */   {
/* 2770:2611 */     return "IllegalArgumentException in class: %s, setter method of property: %s";
/* 2771:     */   }
/* 2772:     */   
/* 2773:     */   public final void unableToObjectConnectionToQueryMetadata(SQLException error)
/* 2774:     */   {
/* 2775:2616 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000340: " + unableToObjectConnectionToQueryMetadata$str(), error);
/* 2776:     */   }
/* 2777:     */   
/* 2778:     */   protected String unableToObjectConnectionToQueryMetadata$str()
/* 2779:     */   {
/* 2780:2620 */     return "Could not obtain connection to query metadata: %s";
/* 2781:     */   }
/* 2782:     */   
/* 2783:     */   public final void disablingContextualLOBCreation(String nonContextualLobCreation)
/* 2784:     */   {
/* 2785:2625 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000421: " + disablingContextualLOBCreation$str(), nonContextualLobCreation);
/* 2786:     */   }
/* 2787:     */   
/* 2788:     */   protected String disablingContextualLOBCreation$str()
/* 2789:     */   {
/* 2790:2629 */     return "Disabling contextual LOB creation as %s is true";
/* 2791:     */   }
/* 2792:     */   
/* 2793:     */   public final void unableToInstantiateOptimizer(String type)
/* 2794:     */   {
/* 2795:2634 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000322: " + unableToInstantiateOptimizer$str(), type);
/* 2796:     */   }
/* 2797:     */   
/* 2798:     */   protected String unableToInstantiateOptimizer$str()
/* 2799:     */   {
/* 2800:2638 */     return "Unable to instantiate specified optimizer [%s], falling back to noop";
/* 2801:     */   }
/* 2802:     */   
/* 2803:     */   public final void unknownOracleVersion(int databaseMajorVersion)
/* 2804:     */   {
/* 2805:2643 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000384: " + unknownOracleVersion$str(), Integer.valueOf(databaseMajorVersion));
/* 2806:     */   }
/* 2807:     */   
/* 2808:     */   protected String unknownOracleVersion$str()
/* 2809:     */   {
/* 2810:2647 */     return "Unknown Oracle major version [%s]";
/* 2811:     */   }
/* 2812:     */   
/* 2813:     */   public final void queryCacheMisses(long queryCacheMissCount)
/* 2814:     */   {
/* 2815:2652 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000214: " + queryCacheMisses$str(), Long.valueOf(queryCacheMissCount));
/* 2816:     */   }
/* 2817:     */   
/* 2818:     */   protected String queryCacheMisses$str()
/* 2819:     */   {
/* 2820:2656 */     return "Query cache misses: %s";
/* 2821:     */   }
/* 2822:     */   
/* 2823:     */   public final void containerProvidingNullPersistenceUnitRootUrl()
/* 2824:     */   {
/* 2825:2661 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000050: " + containerProvidingNullPersistenceUnitRootUrl$str(), new Object[0]);
/* 2826:     */   }
/* 2827:     */   
/* 2828:     */   protected String containerProvidingNullPersistenceUnitRootUrl$str()
/* 2829:     */   {
/* 2830:2665 */     return "Container is providing a null PersistenceUnitRootUrl: discovery impossible";
/* 2831:     */   }
/* 2832:     */   
/* 2833:     */   public final void unableToWrapResultSet(SQLException e)
/* 2834:     */   {
/* 2835:2670 */     this.log.logf(FQCN, Logger.Level.INFO, e, "HHH000377: " + unableToWrapResultSet$str(), new Object[0]);
/* 2836:     */   }
/* 2837:     */   
/* 2838:     */   protected String unableToWrapResultSet$str()
/* 2839:     */   {
/* 2840:2674 */     return "Error wrapping result set";
/* 2841:     */   }
/* 2842:     */   
/* 2843:     */   public final void recognizedObsoleteHibernateNamespace(String oldHibernateNamespace, String hibernateNamespace)
/* 2844:     */   {
/* 2845:2679 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000223: " + recognizedObsoleteHibernateNamespace$str(), oldHibernateNamespace, hibernateNamespace);
/* 2846:     */   }
/* 2847:     */   
/* 2848:     */   protected String recognizedObsoleteHibernateNamespace$str()
/* 2849:     */   {
/* 2850:2683 */     return "Recognized obsolete hibernate namespace %s. Use namespace %s instead. Refer to Hibernate 3.6 Migration Guide!";
/* 2851:     */   }
/* 2852:     */   
/* 2853:     */   public final void foundMappingDocument(String name)
/* 2854:     */   {
/* 2855:2688 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000109: " + foundMappingDocument$str(), name);
/* 2856:     */   }
/* 2857:     */   
/* 2858:     */   protected String foundMappingDocument$str()
/* 2859:     */   {
/* 2860:2692 */     return "Found mapping document in jar: %s";
/* 2861:     */   }
/* 2862:     */   
/* 2863:     */   public final void unableToGetDatabaseMetadata(SQLException e)
/* 2864:     */   {
/* 2865:2697 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000319: " + unableToGetDatabaseMetadata$str(), new Object[0]);
/* 2866:     */   }
/* 2867:     */   
/* 2868:     */   protected String unableToGetDatabaseMetadata$str()
/* 2869:     */   {
/* 2870:2701 */     return "Could not get database metadata";
/* 2871:     */   }
/* 2872:     */   
/* 2873:     */   public final void jaccContextId(String contextId)
/* 2874:     */   {
/* 2875:2706 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000140: " + jaccContextId$str(), contextId);
/* 2876:     */   }
/* 2877:     */   
/* 2878:     */   protected String jaccContextId$str()
/* 2879:     */   {
/* 2880:2710 */     return "JACC contextID: %s";
/* 2881:     */   }
/* 2882:     */   
/* 2883:     */   public final void unableToStopService(Class class1, String string)
/* 2884:     */   {
/* 2885:2715 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000369: " + unableToStopService$str(), class1, string);
/* 2886:     */   }
/* 2887:     */   
/* 2888:     */   protected String unableToStopService$str()
/* 2889:     */   {
/* 2890:2719 */     return "Error stopping service [%s] : %s";
/* 2891:     */   }
/* 2892:     */   
/* 2893:     */   public final void runningHbm2ddlSchemaUpdate()
/* 2894:     */   {
/* 2895:2724 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000228: " + runningHbm2ddlSchemaUpdate$str(), new Object[0]);
/* 2896:     */   }
/* 2897:     */   
/* 2898:     */   protected String runningHbm2ddlSchemaUpdate$str()
/* 2899:     */   {
/* 2900:2728 */     return "Running hbm2ddl schema update";
/* 2901:     */   }
/* 2902:     */   
/* 2903:     */   public final void unsuccessful(String sql)
/* 2904:     */   {
/* 2905:2733 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000388: " + unsuccessful$str(), sql);
/* 2906:     */   }
/* 2907:     */   
/* 2908:     */   protected String unsuccessful$str()
/* 2909:     */   {
/* 2910:2737 */     return "Unsuccessful: %s";
/* 2911:     */   }
/* 2912:     */   
/* 2913:     */   public final void localLoadingCollectionKeysCount(int size)
/* 2914:     */   {
/* 2915:2742 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000160: " + localLoadingCollectionKeysCount$str(), Integer.valueOf(size));
/* 2916:     */   }
/* 2917:     */   
/* 2918:     */   protected String localLoadingCollectionKeysCount$str()
/* 2919:     */   {
/* 2920:2746 */     return "On CollectionLoadContext#cleanup, localLoadingCollectionKeys contained [%s] entries";
/* 2921:     */   }
/* 2922:     */   
/* 2923:     */   public final void missingArguments(int anticipatedNumberOfArguments, int numberOfArguments)
/* 2924:     */   {
/* 2925:2751 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000174: " + missingArguments$str(), Integer.valueOf(anticipatedNumberOfArguments), Integer.valueOf(numberOfArguments));
/* 2926:     */   }
/* 2927:     */   
/* 2928:     */   protected String missingArguments$str()
/* 2929:     */   {
/* 2930:2755 */     return "Function template anticipated %s arguments, but %s arguments encountered";
/* 2931:     */   }
/* 2932:     */   
/* 2933:     */   public final void unableToObtainInitialContext(NamingException e)
/* 2934:     */   {
/* 2935:2760 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000343: " + unableToObtainInitialContext$str(), new Object[0]);
/* 2936:     */   }
/* 2937:     */   
/* 2938:     */   protected String unableToObtainInitialContext$str()
/* 2939:     */   {
/* 2940:2764 */     return "Could not obtain initial context";
/* 2941:     */   }
/* 2942:     */   
/* 2943:     */   public final void unableToLoadDerbyDriver(String message)
/* 2944:     */   {
/* 2945:2769 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000328: " + unableToLoadDerbyDriver$str(), message);
/* 2946:     */   }
/* 2947:     */   
/* 2948:     */   protected String unableToLoadDerbyDriver$str()
/* 2949:     */   {
/* 2950:2773 */     return "Unable to load/access derby driver class sysinfo to check versions : %s";
/* 2951:     */   }
/* 2952:     */   
/* 2953:     */   public final void parameterPositionOccurredAsBothJpaAndHibernatePositionalParameter(Integer position)
/* 2954:     */   {
/* 2955:2778 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000195: " + parameterPositionOccurredAsBothJpaAndHibernatePositionalParameter$str(), position);
/* 2956:     */   }
/* 2957:     */   
/* 2958:     */   protected String parameterPositionOccurredAsBothJpaAndHibernatePositionalParameter$str()
/* 2959:     */   {
/* 2960:2782 */     return "Parameter position [%s] occurred as both JPA and Hibernate positional parameter";
/* 2961:     */   }
/* 2962:     */   
/* 2963:     */   public final void autoFlushWillNotWork()
/* 2964:     */   {
/* 2965:2787 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000008: " + autoFlushWillNotWork$str(), new Object[0]);
/* 2966:     */   }
/* 2967:     */   
/* 2968:     */   protected String autoFlushWillNotWork$str()
/* 2969:     */   {
/* 2970:2791 */     return "JTASessionContext being used with JDBCTransactionFactory; auto-flush will not operate correctly with getCurrentSession()";
/* 2971:     */   }
/* 2972:     */   
/* 2973:     */   public final void deprecatedUuidGenerator(String name, String name2)
/* 2974:     */   {
/* 2975:2796 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000065: " + deprecatedUuidGenerator$str(), name, name2);
/* 2976:     */   }
/* 2977:     */   
/* 2978:     */   protected String deprecatedUuidGenerator$str()
/* 2979:     */   {
/* 2980:2800 */     return "DEPRECATED : use [%s] instead with custom [%s] implementation";
/* 2981:     */   }
/* 2982:     */   
/* 2983:     */   public final void usingTimestampWorkaround()
/* 2984:     */   {
/* 2985:2805 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000408: " + usingTimestampWorkaround$str(), new Object[0]);
/* 2986:     */   }
/* 2987:     */   
/* 2988:     */   protected String usingTimestampWorkaround$str()
/* 2989:     */   {
/* 2990:2809 */     return "Using workaround for JVM bug in java.sql.Timestamp";
/* 2991:     */   }
/* 2992:     */   
/* 2993:     */   public final void unknownIngresVersion(int databaseMajorVersion)
/* 2994:     */   {
/* 2995:2814 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000383: " + unknownIngresVersion$str(), Integer.valueOf(databaseMajorVersion));
/* 2996:     */   }
/* 2997:     */   
/* 2998:     */   protected String unknownIngresVersion$str()
/* 2999:     */   {
/* 3000:2818 */     return "Unknown Ingres major version [%s]; using Ingres 9.2 dialect";
/* 3001:     */   }
/* 3002:     */   
/* 3003:     */   public final void processEqualityExpression()
/* 3004:     */   {
/* 3005:2823 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000203: " + processEqualityExpression$str(), new Object[0]);
/* 3006:     */   }
/* 3007:     */   
/* 3008:     */   protected String processEqualityExpression$str()
/* 3009:     */   {
/* 3010:2827 */     return "processEqualityExpression() : No expression to process!";
/* 3011:     */   }
/* 3012:     */   
/* 3013:     */   public final void instantiatingExplicitConnectionProvider(String providerClassName)
/* 3014:     */   {
/* 3015:2832 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000130: " + instantiatingExplicitConnectionProvider$str(), providerClassName);
/* 3016:     */   }
/* 3017:     */   
/* 3018:     */   protected String instantiatingExplicitConnectionProvider$str()
/* 3019:     */   {
/* 3020:2836 */     return "Instantiating explicit connection provider: %s";
/* 3021:     */   }
/* 3022:     */   
/* 3023:     */   public final void unableToReleaseTypeInfoResultSet()
/* 3024:     */   {
/* 3025:2841 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000357: " + unableToReleaseTypeInfoResultSet$str(), new Object[0]);
/* 3026:     */   }
/* 3027:     */   
/* 3028:     */   protected String unableToReleaseTypeInfoResultSet$str()
/* 3029:     */   {
/* 3030:2845 */     return "Unable to release type info result set";
/* 3031:     */   }
/* 3032:     */   
/* 3033:     */   public final void unableToBindEjb3ConfigurationToJndi(JndiException e)
/* 3034:     */   {
/* 3035:2850 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000276: " + unableToBindEjb3ConfigurationToJndi$str(), new Object[0]);
/* 3036:     */   }
/* 3037:     */   
/* 3038:     */   protected String unableToBindEjb3ConfigurationToJndi$str()
/* 3039:     */   {
/* 3040:2854 */     return "Could not bind Ejb3Configuration to JNDI";
/* 3041:     */   }
/* 3042:     */   
/* 3043:     */   public final void unsupportedInitialValue(String propertyName)
/* 3044:     */   {
/* 3045:2859 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000392: " + unsupportedInitialValue$str(), propertyName);
/* 3046:     */   }
/* 3047:     */   
/* 3048:     */   protected String unsupportedInitialValue$str()
/* 3049:     */   {
/* 3050:2863 */     return "Hibernate does not support SequenceGenerator.initialValue() unless '%s' set";
/* 3051:     */   }
/* 3052:     */   
/* 3053:     */   public final void rdmsOs2200Dialect()
/* 3054:     */   {
/* 3055:2868 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000218: " + rdmsOs2200Dialect$str(), new Object[0]);
/* 3056:     */   }
/* 3057:     */   
/* 3058:     */   protected String rdmsOs2200Dialect$str()
/* 3059:     */   {
/* 3060:2872 */     return "RDMSOS2200Dialect version: 1.0";
/* 3061:     */   }
/* 3062:     */   
/* 3063:     */   public final void failSafeCollectionsCleanup(CollectionLoadContext collectionLoadContext)
/* 3064:     */   {
/* 3065:2877 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000100: " + failSafeCollectionsCleanup$str(), collectionLoadContext);
/* 3066:     */   }
/* 3067:     */   
/* 3068:     */   protected String failSafeCollectionsCleanup$str()
/* 3069:     */   {
/* 3070:2881 */     return "Fail-safe cleanup (collections) : %s";
/* 3071:     */   }
/* 3072:     */   
/* 3073:     */   public final void noPersistentClassesFound(String query)
/* 3074:     */   {
/* 3075:2886 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000183: " + noPersistentClassesFound$str(), query);
/* 3076:     */   }
/* 3077:     */   
/* 3078:     */   protected String noPersistentClassesFound$str()
/* 3079:     */   {
/* 3080:2890 */     return "no persistent classes found for query class: %s";
/* 3081:     */   }
/* 3082:     */   
/* 3083:     */   public final String unableToDetermineTransactionStatus()
/* 3084:     */   {
/* 3085:2895 */     String result = String.format("HHH000312: " + unableToDetermineTransactionStatus$str(), new Object[0]);
/* 3086:2896 */     return result;
/* 3087:     */   }
/* 3088:     */   
/* 3089:     */   protected String unableToDetermineTransactionStatus$str()
/* 3090:     */   {
/* 3091:2900 */     return "Could not determine transaction status";
/* 3092:     */   }
/* 3093:     */   
/* 3094:     */   public final void sortAnnotationIndexedCollection()
/* 3095:     */   {
/* 3096:2905 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000244: " + sortAnnotationIndexedCollection$str(), new Object[0]);
/* 3097:     */   }
/* 3098:     */   
/* 3099:     */   protected String sortAnnotationIndexedCollection$str()
/* 3100:     */   {
/* 3101:2909 */     return "@Sort not allowed for an indexed collection, annotation ignored.";
/* 3102:     */   }
/* 3103:     */   
/* 3104:     */   public final String javassistEnhancementFailed(String entityName)
/* 3105:     */   {
/* 3106:2914 */     String result = String.format("HHH000142: " + javassistEnhancementFailed$str(), new Object[] { entityName });
/* 3107:2915 */     return result;
/* 3108:     */   }
/* 3109:     */   
/* 3110:     */   protected String javassistEnhancementFailed$str()
/* 3111:     */   {
/* 3112:2919 */     return "Javassist Enhancement failed: %s";
/* 3113:     */   }
/* 3114:     */   
/* 3115:     */   public final void jdbcAutoCommitFalseBreaksEjb3Spec(String autocommit)
/* 3116:     */   {
/* 3117:2924 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000144: " + jdbcAutoCommitFalseBreaksEjb3Spec$str(), autocommit);
/* 3118:     */   }
/* 3119:     */   
/* 3120:     */   protected String jdbcAutoCommitFalseBreaksEjb3Spec$str()
/* 3121:     */   {
/* 3122:2928 */     return "%s = false breaks the EJB3 specification";
/* 3123:     */   }
/* 3124:     */   
/* 3125:     */   public final void JavaSqlTypesMappedSameCodeMultipleTimes(int code, String old, String name)
/* 3126:     */   {
/* 3127:2933 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000141: " + JavaSqlTypesMappedSameCodeMultipleTimes$str(), Integer.valueOf(code), old, name);
/* 3128:     */   }
/* 3129:     */   
/* 3130:     */   protected String JavaSqlTypesMappedSameCodeMultipleTimes$str()
/* 3131:     */   {
/* 3132:2937 */     return "java.sql.Types mapped the same code [%s] multiple times; was [%s]; now [%s]";
/* 3133:     */   }
/* 3134:     */   
/* 3135:     */   public final void providerClassDeprecated(String providerClassName, String actualProviderClassName)
/* 3136:     */   {
/* 3137:2942 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000208: " + providerClassDeprecated$str(), providerClassName, actualProviderClassName);
/* 3138:     */   }
/* 3139:     */   
/* 3140:     */   protected String providerClassDeprecated$str()
/* 3141:     */   {
/* 3142:2946 */     return "%s has been deprecated in favor of %s; that provider will be used instead.";
/* 3143:     */   }
/* 3144:     */   
/* 3145:     */   public final void persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly()
/* 3146:     */   {
/* 3147:2951 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000200: " + persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly$str(), new Object[0]);
/* 3148:     */   }
/* 3149:     */   
/* 3150:     */   protected String persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly$str()
/* 3151:     */   {
/* 3152:2955 */     return "Persistence provider caller does not implement the EJB3 spec correctly.PersistenceUnitInfo.getNewTempClassLoader() is null.";
/* 3153:     */   }
/* 3154:     */   
/* 3155:     */   public final void factoryUnboundFromName(String name)
/* 3156:     */   {
/* 3157:2960 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000098: " + factoryUnboundFromName$str(), name);
/* 3158:     */   }
/* 3159:     */   
/* 3160:     */   protected String factoryUnboundFromName$str()
/* 3161:     */   {
/* 3162:2964 */     return "A factory was unbound from name: %s";
/* 3163:     */   }
/* 3164:     */   
/* 3165:     */   public final void duplicateJoins(String entityName)
/* 3166:     */   {
/* 3167:2969 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000072: " + duplicateJoins$str(), entityName);
/* 3168:     */   }
/* 3169:     */   
/* 3170:     */   protected String duplicateJoins$str()
/* 3171:     */   {
/* 3172:2973 */     return "Duplicate joins for class: %s";
/* 3173:     */   }
/* 3174:     */   
/* 3175:     */   public final void parsingXmlErrorForFile(String file, int lineNumber, String message)
/* 3176:     */   {
/* 3177:2978 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000197: " + parsingXmlErrorForFile$str(), file, Integer.valueOf(lineNumber), message);
/* 3178:     */   }
/* 3179:     */   
/* 3180:     */   protected String parsingXmlErrorForFile$str()
/* 3181:     */   {
/* 3182:2982 */     return "Error parsing XML: %s(%s) %s";
/* 3183:     */   }
/* 3184:     */   
/* 3185:     */   public final void propertyNotFound(String property)
/* 3186:     */   {
/* 3187:2987 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000207: " + propertyNotFound$str(), property);
/* 3188:     */   }
/* 3189:     */   
/* 3190:     */   protected String propertyNotFound$str()
/* 3191:     */   {
/* 3192:2991 */     return "Property %s not found in class but described in <mapping-file/> (possible typo error)";
/* 3193:     */   }
/* 3194:     */   
/* 3195:     */   public final void unableToAccessTypeInfoResultSet(String string)
/* 3196:     */   {
/* 3197:2996 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000273: " + unableToAccessTypeInfoResultSet$str(), string);
/* 3198:     */   }
/* 3199:     */   
/* 3200:     */   protected String unableToAccessTypeInfoResultSet$str()
/* 3201:     */   {
/* 3202:3000 */     return "Error accessing type info result set : %s";
/* 3203:     */   }
/* 3204:     */   
/* 3205:     */   public final void incompleteMappingMetadataCacheProcessing()
/* 3206:     */   {
/* 3207:3005 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000125: " + incompleteMappingMetadataCacheProcessing$str(), new Object[0]);
/* 3208:     */   }
/* 3209:     */   
/* 3210:     */   protected String incompleteMappingMetadataCacheProcessing$str()
/* 3211:     */   {
/* 3212:3009 */     return "Mapping metadata cache was not completely processed";
/* 3213:     */   }
/* 3214:     */   
/* 3215:     */   public final void compositeIdClassDoesNotOverrideEquals(String name)
/* 3216:     */   {
/* 3217:3014 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000038: " + compositeIdClassDoesNotOverrideEquals$str(), name);
/* 3218:     */   }
/* 3219:     */   
/* 3220:     */   protected String compositeIdClassDoesNotOverrideEquals$str()
/* 3221:     */   {
/* 3222:3018 */     return "Composite-id class does not override equals(): %s";
/* 3223:     */   }
/* 3224:     */   
/* 3225:     */   public final void connectionProperties(Properties connectionProps)
/* 3226:     */   {
/* 3227:3023 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000046: " + connectionProperties$str(), connectionProps);
/* 3228:     */   }
/* 3229:     */   
/* 3230:     */   protected String connectionProperties$str()
/* 3231:     */   {
/* 3232:3027 */     return "Connection properties: %s";
/* 3233:     */   }
/* 3234:     */   
/* 3235:     */   public final void readOnlyCacheConfiguredForMutableCollection(String name)
/* 3236:     */   {
/* 3237:3032 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000222: " + readOnlyCacheConfiguredForMutableCollection$str(), name);
/* 3238:     */   }
/* 3239:     */   
/* 3240:     */   protected String readOnlyCacheConfiguredForMutableCollection$str()
/* 3241:     */   {
/* 3242:3036 */     return "read-only cache configured for mutable collection [%s]";
/* 3243:     */   }
/* 3244:     */   
/* 3245:     */   public final void unableToConstructSqlExceptionConverter(Throwable t)
/* 3246:     */   {
/* 3247:3041 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000303: " + unableToConstructSqlExceptionConverter$str(), t);
/* 3248:     */   }
/* 3249:     */   
/* 3250:     */   protected String unableToConstructSqlExceptionConverter$str()
/* 3251:     */   {
/* 3252:3045 */     return "Unable to construct instance of specified SQLExceptionConverter : %s";
/* 3253:     */   }
/* 3254:     */   
/* 3255:     */   public final void readingCachedMappings(File cachedFile)
/* 3256:     */   {
/* 3257:3050 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000219: " + readingCachedMappings$str(), cachedFile);
/* 3258:     */   }
/* 3259:     */   
/* 3260:     */   protected String readingCachedMappings$str()
/* 3261:     */   {
/* 3262:3054 */     return "Reading mappings from cache file: %s";
/* 3263:     */   }
/* 3264:     */   
/* 3265:     */   public final void undeterminedH2Version()
/* 3266:     */   {
/* 3267:3059 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000431: " + undeterminedH2Version$str(), new Object[0]);
/* 3268:     */   }
/* 3269:     */   
/* 3270:     */   protected String undeterminedH2Version$str()
/* 3271:     */   {
/* 3272:3063 */     return "Unable to determine H2 database version, certain features may not work";
/* 3273:     */   }
/* 3274:     */   
/* 3275:     */   public final void deprecatedOracleDialect()
/* 3276:     */   {
/* 3277:3068 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000064: " + deprecatedOracleDialect$str(), new Object[0]);
/* 3278:     */   }
/* 3279:     */   
/* 3280:     */   protected String deprecatedOracleDialect$str()
/* 3281:     */   {
/* 3282:3072 */     return "The OracleDialect dialect has been deprecated; use Oracle8iDialect instead";
/* 3283:     */   }
/* 3284:     */   
/* 3285:     */   public final void unableToCloseSessionDuringRollback(Exception e)
/* 3286:     */   {
/* 3287:3077 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000295: " + unableToCloseSessionDuringRollback$str(), new Object[0]);
/* 3288:     */   }
/* 3289:     */   
/* 3290:     */   protected String unableToCloseSessionDuringRollback$str()
/* 3291:     */   {
/* 3292:3081 */     return "Could not close session during rollback";
/* 3293:     */   }
/* 3294:     */   
/* 3295:     */   public final void unableToCloseInitialContext(String string)
/* 3296:     */   {
/* 3297:3086 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000285: " + unableToCloseInitialContext$str(), string);
/* 3298:     */   }
/* 3299:     */   
/* 3300:     */   protected String unableToCloseInitialContext$str()
/* 3301:     */   {
/* 3302:3090 */     return "Error closing InitialContext [%s]";
/* 3303:     */   }
/* 3304:     */   
/* 3305:     */   public final void preparedStatementAlreadyInBatch(String sql)
/* 3306:     */   {
/* 3307:3095 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000202: " + preparedStatementAlreadyInBatch$str(), sql);
/* 3308:     */   }
/* 3309:     */   
/* 3310:     */   protected String preparedStatementAlreadyInBatch$str()
/* 3311:     */   {
/* 3312:3099 */     return "PreparedStatement was already in the batch, [%s].";
/* 3313:     */   }
/* 3314:     */   
/* 3315:     */   public final void gettersOfLazyClassesCannotBeFinal(String entityName, String name)
/* 3316:     */   {
/* 3317:3104 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000112: " + gettersOfLazyClassesCannotBeFinal$str(), entityName, name);
/* 3318:     */   }
/* 3319:     */   
/* 3320:     */   protected String gettersOfLazyClassesCannotBeFinal$str()
/* 3321:     */   {
/* 3322:3108 */     return "Getters of lazy classes cannot be final: %s.%s";
/* 3323:     */   }
/* 3324:     */   
/* 3325:     */   public final void namedQueryError(String queryName, HibernateException e)
/* 3326:     */   {
/* 3327:3113 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000177: " + namedQueryError$str(), queryName);
/* 3328:     */   }
/* 3329:     */   
/* 3330:     */   protected String namedQueryError$str()
/* 3331:     */   {
/* 3332:3117 */     return "Error in named query: %s";
/* 3333:     */   }
/* 3334:     */   
/* 3335:     */   public final void closing()
/* 3336:     */   {
/* 3337:3122 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, "HHH000031: " + closing$str(), new Object[0]);
/* 3338:     */   }
/* 3339:     */   
/* 3340:     */   protected String closing$str()
/* 3341:     */   {
/* 3342:3126 */     return "Closing";
/* 3343:     */   }
/* 3344:     */   
/* 3345:     */   public final void propertiesLoaded(Properties maskOut)
/* 3346:     */   {
/* 3347:3131 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000205: " + propertiesLoaded$str(), maskOut);
/* 3348:     */   }
/* 3349:     */   
/* 3350:     */   protected String propertiesLoaded$str()
/* 3351:     */   {
/* 3352:3135 */     return "Loaded properties from resource hibernate.properties: %s";
/* 3353:     */   }
/* 3354:     */   
/* 3355:     */   public final void unsupportedMultiTableBulkHqlJpaql(int majorVersion, int minorVersion, int buildId)
/* 3356:     */   {
/* 3357:3140 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000393: " + unsupportedMultiTableBulkHqlJpaql$str(), Integer.valueOf(majorVersion), Integer.valueOf(minorVersion), Integer.valueOf(buildId));
/* 3358:     */   }
/* 3359:     */   
/* 3360:     */   protected String unsupportedMultiTableBulkHqlJpaql$str()
/* 3361:     */   {
/* 3362:3144 */     return "The %s.%s.%s version of H2 implements temporary table creation such that it commits current transaction; multi-table, bulk hql/jpaql will not work properly";
/* 3363:     */   }
/* 3364:     */   
/* 3365:     */   public final void invalidOnDeleteAnnotation(String entityName)
/* 3366:     */   {
/* 3367:3149 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000136: " + invalidOnDeleteAnnotation$str(), entityName);
/* 3368:     */   }
/* 3369:     */   
/* 3370:     */   protected String invalidOnDeleteAnnotation$str()
/* 3371:     */   {
/* 3372:3153 */     return "Inapropriate use of @OnDelete on entity, annotation ignored: %s";
/* 3373:     */   }
/* 3374:     */   
/* 3375:     */   public final String unableToDetermineTransactionStatusAfterCommit()
/* 3376:     */   {
/* 3377:3158 */     String result = String.format("HHH000313: " + unableToDetermineTransactionStatusAfterCommit$str(), new Object[0]);
/* 3378:3159 */     return result;
/* 3379:     */   }
/* 3380:     */   
/* 3381:     */   protected String unableToDetermineTransactionStatusAfterCommit$str()
/* 3382:     */   {
/* 3383:3163 */     return "Could not determine transaction status after commit";
/* 3384:     */   }
/* 3385:     */   
/* 3386:     */   public final String unableToRollbackJta()
/* 3387:     */   {
/* 3388:3168 */     String result = String.format("HHH000365: " + unableToRollbackJta$str(), new Object[0]);
/* 3389:3169 */     return result;
/* 3390:     */   }
/* 3391:     */   
/* 3392:     */   protected String unableToRollbackJta$str()
/* 3393:     */   {
/* 3394:3173 */     return "JTA rollback failed";
/* 3395:     */   }
/* 3396:     */   
/* 3397:     */   public final void unableToReleaseCreatedMBeanServer(String string)
/* 3398:     */   {
/* 3399:3178 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000355: " + unableToReleaseCreatedMBeanServer$str(), string);
/* 3400:     */   }
/* 3401:     */   
/* 3402:     */   protected String unableToReleaseCreatedMBeanServer$str()
/* 3403:     */   {
/* 3404:3182 */     return "Unable to release created MBeanServer : %s";
/* 3405:     */   }
/* 3406:     */   
/* 3407:     */   public final void failed(Throwable throwable)
/* 3408:     */   {
/* 3409:3187 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000099: " + failed$str(), throwable);
/* 3410:     */   }
/* 3411:     */   
/* 3412:     */   protected String failed$str()
/* 3413:     */   {
/* 3414:3191 */     return "an assertion failure occured (this may indicate a bug in Hibernate, but is more likely due to unsafe use of the session): %s";
/* 3415:     */   }
/* 3416:     */   
/* 3417:     */   public final void hibernateConnectionPoolSize(int poolSize)
/* 3418:     */   {
/* 3419:3196 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000115: " + hibernateConnectionPoolSize$str(), Integer.valueOf(poolSize));
/* 3420:     */   }
/* 3421:     */   
/* 3422:     */   protected String hibernateConnectionPoolSize$str()
/* 3423:     */   {
/* 3424:3200 */     return "Hibernate connection pool size: %s";
/* 3425:     */   }
/* 3426:     */   
/* 3427:     */   public final void failSafeEntitiesCleanup(EntityLoadContext entityLoadContext)
/* 3428:     */   {
/* 3429:3205 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000101: " + failSafeEntitiesCleanup$str(), entityLoadContext);
/* 3430:     */   }
/* 3431:     */   
/* 3432:     */   protected String failSafeEntitiesCleanup$str()
/* 3433:     */   {
/* 3434:3209 */     return "Fail-safe cleanup (entities) : %s";
/* 3435:     */   }
/* 3436:     */   
/* 3437:     */   public final void hydratingEntitiesCount(int size)
/* 3438:     */   {
/* 3439:3214 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000119: " + hydratingEntitiesCount$str(), Integer.valueOf(size));
/* 3440:     */   }
/* 3441:     */   
/* 3442:     */   protected String hydratingEntitiesCount$str()
/* 3443:     */   {
/* 3444:3218 */     return "On EntityLoadContext#clear, hydratingEntities contained [%s] entries";
/* 3445:     */   }
/* 3446:     */   
/* 3447:     */   public final void unableToBuildSessionFactoryUsingMBeanClasspath(String message)
/* 3448:     */   {
/* 3449:3223 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000280: " + unableToBuildSessionFactoryUsingMBeanClasspath$str(), message);
/* 3450:     */   }
/* 3451:     */   
/* 3452:     */   protected String unableToBuildSessionFactoryUsingMBeanClasspath$str()
/* 3453:     */   {
/* 3454:3227 */     return "Could not build SessionFactory using the MBean classpath - will try again using client classpath: %s";
/* 3455:     */   }
/* 3456:     */   
/* 3457:     */   public final void unableToParseMetadata(String packageName)
/* 3458:     */   {
/* 3459:3232 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000344: " + unableToParseMetadata$str(), packageName);
/* 3460:     */   }
/* 3461:     */   
/* 3462:     */   protected String unableToParseMetadata$str()
/* 3463:     */   {
/* 3464:3236 */     return "Could not parse the package-level metadata [%s]";
/* 3465:     */   }
/* 3466:     */   
/* 3467:     */   public final void unableToReleaseCacheLock(CacheException ce)
/* 3468:     */   {
/* 3469:3241 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000353: " + unableToReleaseCacheLock$str(), ce);
/* 3470:     */   }
/* 3471:     */   
/* 3472:     */   protected String unableToReleaseCacheLock$str()
/* 3473:     */   {
/* 3474:3245 */     return "Could not release a cache lock : %s";
/* 3475:     */   }
/* 3476:     */   
/* 3477:     */   public final void unableToCloseOutputStream(IOException e)
/* 3478:     */   {
/* 3479:3250 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000292: " + unableToCloseOutputStream$str(), new Object[0]);
/* 3480:     */   }
/* 3481:     */   
/* 3482:     */   protected String unableToCloseOutputStream$str()
/* 3483:     */   {
/* 3484:3254 */     return "IOException occurred closing output stream";
/* 3485:     */   }
/* 3486:     */   
/* 3487:     */   public final void transactionStartedOnNonRootSession()
/* 3488:     */   {
/* 3489:3259 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000267: " + transactionStartedOnNonRootSession$str(), new Object[0]);
/* 3490:     */   }
/* 3491:     */   
/* 3492:     */   protected String transactionStartedOnNonRootSession$str()
/* 3493:     */   {
/* 3494:3263 */     return "Transaction started on non-root session";
/* 3495:     */   }
/* 3496:     */   
/* 3497:     */   public final void searchingForMappingDocuments(String name)
/* 3498:     */   {
/* 3499:3268 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000235: " + searchingForMappingDocuments$str(), name);
/* 3500:     */   }
/* 3501:     */   
/* 3502:     */   protected String searchingForMappingDocuments$str()
/* 3503:     */   {
/* 3504:3272 */     return "Searching for mapping documents in jar: %s";
/* 3505:     */   }
/* 3506:     */   
/* 3507:     */   public final void unableToTransformClass(String message)
/* 3508:     */   {
/* 3509:3277 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000373: " + unableToTransformClass$str(), message);
/* 3510:     */   }
/* 3511:     */   
/* 3512:     */   protected String unableToTransformClass$str()
/* 3513:     */   {
/* 3514:3281 */     return "Unable to transform class: %s";
/* 3515:     */   }
/* 3516:     */   
/* 3517:     */   public final void writingGeneratedSchemaToFile(String outputFile)
/* 3518:     */   {
/* 3519:3286 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000417: " + writingGeneratedSchemaToFile$str(), outputFile);
/* 3520:     */   }
/* 3521:     */   
/* 3522:     */   protected String writingGeneratedSchemaToFile$str()
/* 3523:     */   {
/* 3524:3290 */     return "Writing generated schema to file: %s";
/* 3525:     */   }
/* 3526:     */   
/* 3527:     */   public final void unsupportedOracleVersion()
/* 3528:     */   {
/* 3529:3295 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000394: " + unsupportedOracleVersion$str(), new Object[0]);
/* 3530:     */   }
/* 3531:     */   
/* 3532:     */   protected String unsupportedOracleVersion$str()
/* 3533:     */   {
/* 3534:3299 */     return "Oracle 11g is not yet fully supported; using Oracle 10g dialect";
/* 3535:     */   }
/* 3536:     */   
/* 3537:     */   public final void entityManagerClosedBySomeoneElse(String autoCloseSession)
/* 3538:     */   {
/* 3539:3304 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000082: " + entityManagerClosedBySomeoneElse$str(), autoCloseSession);
/* 3540:     */   }
/* 3541:     */   
/* 3542:     */   protected String entityManagerClosedBySomeoneElse$str()
/* 3543:     */   {
/* 3544:3308 */     return "Entity Manager closed by someone else (%s must not be used)";
/* 3545:     */   }
/* 3546:     */   
/* 3547:     */   public final void parsingXmlWarning(int lineNumber, String message)
/* 3548:     */   {
/* 3549:3313 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000198: " + parsingXmlWarning$str(), Integer.valueOf(lineNumber), message);
/* 3550:     */   }
/* 3551:     */   
/* 3552:     */   protected String parsingXmlWarning$str()
/* 3553:     */   {
/* 3554:3317 */     return "Warning parsing XML (%s) : %s";
/* 3555:     */   }
/* 3556:     */   
/* 3557:     */   public final void invalidTableAnnotation(String className)
/* 3558:     */   {
/* 3559:3322 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000139: " + invalidTableAnnotation$str(), className);
/* 3560:     */   }
/* 3561:     */   
/* 3562:     */   protected String invalidTableAnnotation$str()
/* 3563:     */   {
/* 3564:3326 */     return "Illegal use of @Table in a subclass of a SINGLE_TABLE hierarchy: %s";
/* 3565:     */   }
/* 3566:     */   
/* 3567:     */   public final void noColumnsSpecifiedForIndex(String indexName, String tableName)
/* 3568:     */   {
/* 3569:3331 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000432: " + noColumnsSpecifiedForIndex$str(), indexName, tableName);
/* 3570:     */   }
/* 3571:     */   
/* 3572:     */   protected String noColumnsSpecifiedForIndex$str()
/* 3573:     */   {
/* 3574:3335 */     return "There were not column names specified for index %s on table %s";
/* 3575:     */   }
/* 3576:     */   
/* 3577:     */   public final void typeDefinedNoRegistrationKeys(BasicType type)
/* 3578:     */   {
/* 3579:3340 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000269: " + typeDefinedNoRegistrationKeys$str(), type);
/* 3580:     */   }
/* 3581:     */   
/* 3582:     */   protected String typeDefinedNoRegistrationKeys$str()
/* 3583:     */   {
/* 3584:3344 */     return "Type [%s] defined no registration keys; ignoring";
/* 3585:     */   }
/* 3586:     */   
/* 3587:     */   public final void synchronizationAlreadyRegistered(Synchronization synchronization)
/* 3588:     */   {
/* 3589:3349 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000259: " + synchronizationAlreadyRegistered$str(), synchronization);
/* 3590:     */   }
/* 3591:     */   
/* 3592:     */   protected String synchronizationAlreadyRegistered$str()
/* 3593:     */   {
/* 3594:3353 */     return "Synchronization [%s] was already registered";
/* 3595:     */   }
/* 3596:     */   
/* 3597:     */   public final void packageNotFound(String packageName)
/* 3598:     */   {
/* 3599:3358 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, "HHH000194: " + packageNotFound$str(), packageName);
/* 3600:     */   }
/* 3601:     */   
/* 3602:     */   protected String packageNotFound$str()
/* 3603:     */   {
/* 3604:3362 */     return "Package not found or wo package-info.java: %s";
/* 3605:     */   }
/* 3606:     */   
/* 3607:     */   public final void usingAstQueryTranslatorFactory()
/* 3608:     */   {
/* 3609:3367 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000397: " + usingAstQueryTranslatorFactory$str(), new Object[0]);
/* 3610:     */   }
/* 3611:     */   
/* 3612:     */   protected String usingAstQueryTranslatorFactory$str()
/* 3613:     */   {
/* 3614:3371 */     return "Using ASTQueryTranslatorFactory";
/* 3615:     */   }
/* 3616:     */   
/* 3617:     */   public final void readingMappingsFromResource(String resourceName)
/* 3618:     */   {
/* 3619:3376 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000221: " + readingMappingsFromResource$str(), resourceName);
/* 3620:     */   }
/* 3621:     */   
/* 3622:     */   protected String readingMappingsFromResource$str()
/* 3623:     */   {
/* 3624:3380 */     return "Reading mappings from resource: %s";
/* 3625:     */   }
/* 3626:     */   
/* 3627:     */   public final void usingDialect(Dialect dialect)
/* 3628:     */   {
/* 3629:3385 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000400: " + usingDialect$str(), dialect);
/* 3630:     */   }
/* 3631:     */   
/* 3632:     */   protected String usingDialect$str()
/* 3633:     */   {
/* 3634:3389 */     return "Using dialect: %s";
/* 3635:     */   }
/* 3636:     */   
/* 3637:     */   public final void unableToCloseInputStream(IOException e)
/* 3638:     */   {
/* 3639:3394 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000287: " + unableToCloseInputStream$str(), new Object[0]);
/* 3640:     */   }
/* 3641:     */   
/* 3642:     */   protected String unableToCloseInputStream$str()
/* 3643:     */   {
/* 3644:3398 */     return "Could not close input stream";
/* 3645:     */   }
/* 3646:     */   
/* 3647:     */   public final void needsLimit()
/* 3648:     */   {
/* 3649:3403 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000180: " + needsLimit$str(), new Object[0]);
/* 3650:     */   }
/* 3651:     */   
/* 3652:     */   protected String needsLimit$str()
/* 3653:     */   {
/* 3654:3407 */     return "FirstResult/maxResults specified on polymorphic query; applying in memory!";
/* 3655:     */   }
/* 3656:     */   
/* 3657:     */   public final void unableToCloseConnection(Exception e)
/* 3658:     */   {
/* 3659:3412 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000284: " + unableToCloseConnection$str(), new Object[0]);
/* 3660:     */   }
/* 3661:     */   
/* 3662:     */   protected String unableToCloseConnection$str()
/* 3663:     */   {
/* 3664:3416 */     return "Error closing connection";
/* 3665:     */   }
/* 3666:     */   
/* 3667:     */   public final void fetchingDatabaseMetadata()
/* 3668:     */   {
/* 3669:3421 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000102: " + fetchingDatabaseMetadata$str(), new Object[0]);
/* 3670:     */   }
/* 3671:     */   
/* 3672:     */   protected String fetchingDatabaseMetadata$str()
/* 3673:     */   {
/* 3674:3425 */     return "Fetching database metadata";
/* 3675:     */   }
/* 3676:     */   
/* 3677:     */   public final void unableToDetermineLockModeValue(String hintName, Object value)
/* 3678:     */   {
/* 3679:3430 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000311: " + unableToDetermineLockModeValue$str(), hintName, value);
/* 3680:     */   }
/* 3681:     */   
/* 3682:     */   protected String unableToDetermineLockModeValue$str()
/* 3683:     */   {
/* 3684:3434 */     return "Unable to determine lock mode value : %s -> %s";
/* 3685:     */   }
/* 3686:     */   
/* 3687:     */   public final void unableToDeserializeCache(String path, SerializationException error)
/* 3688:     */   {
/* 3689:3439 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000307: " + unableToDeserializeCache$str(), path, error);
/* 3690:     */   }
/* 3691:     */   
/* 3692:     */   protected String unableToDeserializeCache$str()
/* 3693:     */   {
/* 3694:3443 */     return "Could not deserialize cache file: %s : %s";
/* 3695:     */   }
/* 3696:     */   
/* 3697:     */   public final void configuringFromResource(String resource)
/* 3698:     */   {
/* 3699:3448 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000043: " + configuringFromResource$str(), resource);
/* 3700:     */   }
/* 3701:     */   
/* 3702:     */   protected String configuringFromResource$str()
/* 3703:     */   {
/* 3704:3452 */     return "Configuring from resource: %s";
/* 3705:     */   }
/* 3706:     */   
/* 3707:     */   public final void serviceProperties(Properties properties)
/* 3708:     */   {
/* 3709:3457 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000240: " + serviceProperties$str(), properties);
/* 3710:     */   }
/* 3711:     */   
/* 3712:     */   protected String serviceProperties$str()
/* 3713:     */   {
/* 3714:3461 */     return "Service properties: %s";
/* 3715:     */   }
/* 3716:     */   
/* 3717:     */   public final void unableToUpdateQueryHiValue(String tableName, SQLException e)
/* 3718:     */   {
/* 3719:3466 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000376: " + unableToUpdateQueryHiValue$str(), tableName);
/* 3720:     */   }
/* 3721:     */   
/* 3722:     */   protected String unableToUpdateQueryHiValue$str()
/* 3723:     */   {
/* 3724:3470 */     return "Could not updateQuery hi value in: %s";
/* 3725:     */   }
/* 3726:     */   
/* 3727:     */   public final void forcingContainerResourceCleanup()
/* 3728:     */   {
/* 3729:3475 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000106: " + forcingContainerResourceCleanup$str(), new Object[0]);
/* 3730:     */   }
/* 3731:     */   
/* 3732:     */   protected String forcingContainerResourceCleanup$str()
/* 3733:     */   {
/* 3734:3479 */     return "Forcing container resource cleanup on transaction completion";
/* 3735:     */   }
/* 3736:     */   
/* 3737:     */   public final void unableToResolveMappingFile(String xmlFile)
/* 3738:     */   {
/* 3739:3484 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000360: " + unableToResolveMappingFile$str(), xmlFile);
/* 3740:     */   }
/* 3741:     */   
/* 3742:     */   protected String unableToResolveMappingFile$str()
/* 3743:     */   {
/* 3744:3488 */     return "Unable to resolve mapping file [%s]";
/* 3745:     */   }
/* 3746:     */   
/* 3747:     */   public final void willNotRegisterListeners()
/* 3748:     */   {
/* 3749:3493 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000414: " + willNotRegisterListeners$str(), new Object[0]);
/* 3750:     */   }
/* 3751:     */   
/* 3752:     */   protected String willNotRegisterListeners$str()
/* 3753:     */   {
/* 3754:3497 */     return "Property hibernate.search.autoregister_listeners is set to false. No attempt will be made to register Hibernate Search event listeners.";
/* 3755:     */   }
/* 3756:     */   
/* 3757:     */   public final void unableToCreateSchema(Exception e)
/* 3758:     */   {
/* 3759:3502 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000306: " + unableToCreateSchema$str(), new Object[0]);
/* 3760:     */   }
/* 3761:     */   
/* 3762:     */   protected String unableToCreateSchema$str()
/* 3763:     */   {
/* 3764:3506 */     return "Error creating schema ";
/* 3765:     */   }
/* 3766:     */   
/* 3767:     */   public final void splitQueries(String sourceQuery, int length)
/* 3768:     */   {
/* 3769:3511 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000245: " + splitQueries$str(), sourceQuery, Integer.valueOf(length));
/* 3770:     */   }
/* 3771:     */   
/* 3772:     */   protected String splitQueries$str()
/* 3773:     */   {
/* 3774:3515 */     return "Manipulation query [%s] resulted in [%s] split queries";
/* 3775:     */   }
/* 3776:     */   
/* 3777:     */   public final void secondLevelCachePuts(long secondLevelCachePutCount)
/* 3778:     */   {
/* 3779:3520 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000239: " + secondLevelCachePuts$str(), Long.valueOf(secondLevelCachePutCount));
/* 3780:     */   }
/* 3781:     */   
/* 3782:     */   protected String secondLevelCachePuts$str()
/* 3783:     */   {
/* 3784:3524 */     return "Second level cache puts: %s";
/* 3785:     */   }
/* 3786:     */   
/* 3787:     */   public final void unableToPerformManagedFlush(String message)
/* 3788:     */   {
/* 3789:3529 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000346: " + unableToPerformManagedFlush$str(), message);
/* 3790:     */   }
/* 3791:     */   
/* 3792:     */   protected String unableToPerformManagedFlush$str()
/* 3793:     */   {
/* 3794:3533 */     return "Error during managed flush [%s]";
/* 3795:     */   }
/* 3796:     */   
/* 3797:     */   public final void unableToLoadProperties()
/* 3798:     */   {
/* 3799:3538 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000329: " + unableToLoadProperties$str(), new Object[0]);
/* 3800:     */   }
/* 3801:     */   
/* 3802:     */   protected String unableToLoadProperties$str()
/* 3803:     */   {
/* 3804:3542 */     return "Problem loading properties from hibernate.properties";
/* 3805:     */   }
/* 3806:     */   
/* 3807:     */   public final void unableToCloseStream(IOException e)
/* 3808:     */   {
/* 3809:3547 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000296: " + unableToCloseStream$str(), new Object[0]);
/* 3810:     */   }
/* 3811:     */   
/* 3812:     */   protected String unableToCloseStream$str()
/* 3813:     */   {
/* 3814:3551 */     return "IOException occurred closing stream";
/* 3815:     */   }
/* 3816:     */   
/* 3817:     */   public final void ignoringTableGeneratorConstraints(String name)
/* 3818:     */   {
/* 3819:3556 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000120: " + ignoringTableGeneratorConstraints$str(), name);
/* 3820:     */   }
/* 3821:     */   
/* 3822:     */   protected String ignoringTableGeneratorConstraints$str()
/* 3823:     */   {
/* 3824:3560 */     return "Ignoring unique constraints specified on table generator [%s]";
/* 3825:     */   }
/* 3826:     */   
/* 3827:     */   public final void entitiesUpdated(long entityUpdateCount)
/* 3828:     */   {
/* 3829:3565 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000080: " + entitiesUpdated$str(), Long.valueOf(entityUpdateCount));
/* 3830:     */   }
/* 3831:     */   
/* 3832:     */   protected String entitiesUpdated$str()
/* 3833:     */   {
/* 3834:3569 */     return "Entities updated: %s";
/* 3835:     */   }
/* 3836:     */   
/* 3837:     */   public final String unableToCommitJta()
/* 3838:     */   {
/* 3839:3574 */     String result = String.format("HHH000298: " + unableToCommitJta$str(), new Object[0]);
/* 3840:3575 */     return result;
/* 3841:     */   }
/* 3842:     */   
/* 3843:     */   protected String unableToCommitJta$str()
/* 3844:     */   {
/* 3845:3579 */     return "JTA commit failed";
/* 3846:     */   }
/* 3847:     */   
/* 3848:     */   public final void duplicateListener(String className)
/* 3849:     */   {
/* 3850:3584 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000073: " + duplicateListener$str(), className);
/* 3851:     */   }
/* 3852:     */   
/* 3853:     */   protected String duplicateListener$str()
/* 3854:     */   {
/* 3855:3588 */     return "entity-listener duplication, first event definition will be used: %s";
/* 3856:     */   }
/* 3857:     */   
/* 3858:     */   public final void deprecatedDerbyDialect()
/* 3859:     */   {
/* 3860:3593 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000430: " + deprecatedDerbyDialect$str(), new Object[0]);
/* 3861:     */   }
/* 3862:     */   
/* 3863:     */   protected String deprecatedDerbyDialect$str()
/* 3864:     */   {
/* 3865:3597 */     return "The DerbyDialect dialect has been deprecated; use one of the version-specific dialects instead";
/* 3866:     */   }
/* 3867:     */   
/* 3868:     */   public final void renamedProperty(Object propertyName, Object newPropertyName)
/* 3869:     */   {
/* 3870:3602 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000225: " + renamedProperty$str(), propertyName, newPropertyName);
/* 3871:     */   }
/* 3872:     */   
/* 3873:     */   protected String renamedProperty$str()
/* 3874:     */   {
/* 3875:3606 */     return "Property [%s] has been renamed to [%s]; update your properties appropriately";
/* 3876:     */   }
/* 3877:     */   
/* 3878:     */   public final void unableToCopySystemProperties()
/* 3879:     */   {
/* 3880:3611 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000304: " + unableToCopySystemProperties$str(), new Object[0]);
/* 3881:     */   }
/* 3882:     */   
/* 3883:     */   protected String unableToCopySystemProperties$str()
/* 3884:     */   {
/* 3885:3615 */     return "Could not copy system properties, system properties will be ignored";
/* 3886:     */   }
/* 3887:     */   
/* 3888:     */   public final void definingFlushBeforeCompletionIgnoredInHem(String flushBeforeCompletion)
/* 3889:     */   {
/* 3890:3620 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000059: " + definingFlushBeforeCompletionIgnoredInHem$str(), flushBeforeCompletion);
/* 3891:     */   }
/* 3892:     */   
/* 3893:     */   protected String definingFlushBeforeCompletionIgnoredInHem$str()
/* 3894:     */   {
/* 3895:3624 */     return "Defining %s=true ignored in HEM";
/* 3896:     */   }
/* 3897:     */   
/* 3898:     */   public final void unknownBytecodeProvider(String providerName)
/* 3899:     */   {
/* 3900:3629 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000382: " + unknownBytecodeProvider$str(), providerName);
/* 3901:     */   }
/* 3902:     */   
/* 3903:     */   protected String unknownBytecodeProvider$str()
/* 3904:     */   {
/* 3905:3633 */     return "unrecognized bytecode provider [%s], using javassist by default";
/* 3906:     */   }
/* 3907:     */   
/* 3908:     */   public final void entityAnnotationOnNonRoot(String className)
/* 3909:     */   {
/* 3910:3638 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000081: " + entityAnnotationOnNonRoot$str(), className);
/* 3911:     */   }
/* 3912:     */   
/* 3913:     */   protected String entityAnnotationOnNonRoot$str()
/* 3914:     */   {
/* 3915:3642 */     return "@org.hibernate.annotations.Entity used on a non root entity: ignored for %s";
/* 3916:     */   }
/* 3917:     */   
/* 3918:     */   public final void guidGenerated(String result)
/* 3919:     */   {
/* 3920:3647 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000113: " + guidGenerated$str(), result);
/* 3921:     */   }
/* 3922:     */   
/* 3923:     */   protected String guidGenerated$str()
/* 3924:     */   {
/* 3925:3651 */     return "GUID identifier generated: %s";
/* 3926:     */   }
/* 3927:     */   
/* 3928:     */   public final void jndiNameDoesNotHandleSessionFactoryReference(String sfJNDIName, ClassCastException e)
/* 3929:     */   {
/* 3930:3656 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000155: " + jndiNameDoesNotHandleSessionFactoryReference$str(), sfJNDIName);
/* 3931:     */   }
/* 3932:     */   
/* 3933:     */   protected String jndiNameDoesNotHandleSessionFactoryReference$str()
/* 3934:     */   {
/* 3935:3660 */     return "JNDI name %s does not handle a session factory reference";
/* 3936:     */   }
/* 3937:     */   
/* 3938:     */   public final void validatorNotFound()
/* 3939:     */   {
/* 3940:3665 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000410: " + validatorNotFound$str(), new Object[0]);
/* 3941:     */   }
/* 3942:     */   
/* 3943:     */   protected String validatorNotFound$str()
/* 3944:     */   {
/* 3945:3669 */     return "Hibernate Validator not found: ignoring";
/* 3946:     */   }
/* 3947:     */   
/* 3948:     */   public final void usingDriver(String driverClassName, String url)
/* 3949:     */   {
/* 3950:3674 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000401: " + usingDriver$str(), driverClassName, url);
/* 3951:     */   }
/* 3952:     */   
/* 3953:     */   protected String usingDriver$str()
/* 3954:     */   {
/* 3955:3678 */     return "using driver [%s] at URL [%s]";
/* 3956:     */   }
/* 3957:     */   
/* 3958:     */   public final void queryCacheHits(long queryCacheHitCount)
/* 3959:     */   {
/* 3960:3683 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000213: " + queryCacheHits$str(), Long.valueOf(queryCacheHitCount));
/* 3961:     */   }
/* 3962:     */   
/* 3963:     */   protected String queryCacheHits$str()
/* 3964:     */   {
/* 3965:3687 */     return "Query cache hits: %s";
/* 3966:     */   }
/* 3967:     */   
/* 3968:     */   public final void foreignKeys(Set keySet)
/* 3969:     */   {
/* 3970:3692 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000108: " + foreignKeys$str(), keySet);
/* 3971:     */   }
/* 3972:     */   
/* 3973:     */   protected String foreignKeys$str()
/* 3974:     */   {
/* 3975:3696 */     return "Foreign keys: %s";
/* 3976:     */   }
/* 3977:     */   
/* 3978:     */   public final void unableToRemoveBagJoinFetch()
/* 3979:     */   {
/* 3980:3701 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000358: " + unableToRemoveBagJoinFetch$str(), new Object[0]);
/* 3981:     */   }
/* 3982:     */   
/* 3983:     */   protected String unableToRemoveBagJoinFetch$str()
/* 3984:     */   {
/* 3985:3705 */     return "Unable to erase previously added bag join fetch";
/* 3986:     */   }
/* 3987:     */   
/* 3988:     */   public final void pooledOptimizerReportedInitialValue(IntegralDataTypeHolder value)
/* 3989:     */   {
/* 3990:3710 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000201: " + pooledOptimizerReportedInitialValue$str(), value);
/* 3991:     */   }
/* 3992:     */   
/* 3993:     */   protected String pooledOptimizerReportedInitialValue$str()
/* 3994:     */   {
/* 3995:3714 */     return "Pooled optimizer source reported [%s] as the initial value; use of 1 or greater highly recommended";
/* 3996:     */   }
/* 3997:     */   
/* 3998:     */   public final void entityIdentifierValueBindingExists(String name)
/* 3999:     */   {
/* 4000:3719 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000429: " + entityIdentifierValueBindingExists$str(), name);
/* 4001:     */   }
/* 4002:     */   
/* 4003:     */   protected String entityIdentifierValueBindingExists$str()
/* 4004:     */   {
/* 4005:3723 */     return "Setting entity-identifier value binding where one already existed : %s.";
/* 4006:     */   }
/* 4007:     */   
/* 4008:     */   public final void readingMappingsFromFile(String path)
/* 4009:     */   {
/* 4010:3728 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000220: " + readingMappingsFromFile$str(), path);
/* 4011:     */   }
/* 4012:     */   
/* 4013:     */   protected String readingMappingsFromFile$str()
/* 4014:     */   {
/* 4015:3732 */     return "Reading mappings from file: %s";
/* 4016:     */   }
/* 4017:     */   
/* 4018:     */   public final void unableToReadClass(String message)
/* 4019:     */   {
/* 4020:3737 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000348: " + unableToReadClass$str(), message);
/* 4021:     */   }
/* 4022:     */   
/* 4023:     */   protected String unableToReadClass$str()
/* 4024:     */   {
/* 4025:3741 */     return "Unable to read class: %s";
/* 4026:     */   }
/* 4027:     */   
/* 4028:     */   public final void unknownSqlServerVersion(int databaseMajorVersion)
/* 4029:     */   {
/* 4030:3746 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000385: " + unknownSqlServerVersion$str(), Integer.valueOf(databaseMajorVersion));
/* 4031:     */   }
/* 4032:     */   
/* 4033:     */   protected String unknownSqlServerVersion$str()
/* 4034:     */   {
/* 4035:3750 */     return "Unknown Microsoft SQL Server major version [%s] using SQL Server 2000 dialect";
/* 4036:     */   }
/* 4037:     */   
/* 4038:     */   public final void invalidPrimaryKeyJoinColumnAnnotation()
/* 4039:     */   {
/* 4040:3755 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000137: " + invalidPrimaryKeyJoinColumnAnnotation$str(), new Object[0]);
/* 4041:     */   }
/* 4042:     */   
/* 4043:     */   protected String invalidPrimaryKeyJoinColumnAnnotation$str()
/* 4044:     */   {
/* 4045:3759 */     return "Root entity should not hold an PrimaryKeyJoinColum(s), will be ignored";
/* 4046:     */   }
/* 4047:     */   
/* 4048:     */   public final void factoryJndiRename(String oldName, String newName)
/* 4049:     */   {
/* 4050:3764 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000096: " + factoryJndiRename$str(), oldName, newName);
/* 4051:     */   }
/* 4052:     */   
/* 4053:     */   protected String factoryJndiRename$str()
/* 4054:     */   {
/* 4055:3768 */     return "A factory was renamed from [%s] to [%s] in JNDI";
/* 4056:     */   }
/* 4057:     */   
/* 4058:     */   public final void handlingTransientEntity()
/* 4059:     */   {
/* 4060:3773 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000114: " + handlingTransientEntity$str(), new Object[0]);
/* 4061:     */   }
/* 4062:     */   
/* 4063:     */   protected String handlingTransientEntity$str()
/* 4064:     */   {
/* 4065:3777 */     return "Handling transient entity in delete processing";
/* 4066:     */   }
/* 4067:     */   
/* 4068:     */   public final void unsupportedProperty(Object propertyName, Object newPropertyName)
/* 4069:     */   {
/* 4070:3782 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000395: " + unsupportedProperty$str(), propertyName, newPropertyName);
/* 4071:     */   }
/* 4072:     */   
/* 4073:     */   protected String unsupportedProperty$str()
/* 4074:     */   {
/* 4075:3786 */     return "Usage of obsolete property: %s no longer supported, use: %s";
/* 4076:     */   }
/* 4077:     */   
/* 4078:     */   public final void invalidEditOfReadOnlyItem(Object key)
/* 4079:     */   {
/* 4080:3791 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000134: " + invalidEditOfReadOnlyItem$str(), key);
/* 4081:     */   }
/* 4082:     */   
/* 4083:     */   protected String invalidEditOfReadOnlyItem$str()
/* 4084:     */   {
/* 4085:3795 */     return "Application attempted to edit read only item: %s";
/* 4086:     */   }
/* 4087:     */   
/* 4088:     */   public final void statementsClosed(long closeStatementCount)
/* 4089:     */   {
/* 4090:3800 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000252: " + statementsClosed$str(), Long.valueOf(closeStatementCount));
/* 4091:     */   }
/* 4092:     */   
/* 4093:     */   protected String statementsClosed$str()
/* 4094:     */   {
/* 4095:3804 */     return "Statements closed: %s";
/* 4096:     */   }
/* 4097:     */   
/* 4098:     */   public final void exceptionHeaderFound(String exceptionHeader, String metaInfOrmXml)
/* 4099:     */   {
/* 4100:3809 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000085: " + exceptionHeaderFound$str(), exceptionHeader, metaInfOrmXml);
/* 4101:     */   }
/* 4102:     */   
/* 4103:     */   protected String exceptionHeaderFound$str()
/* 4104:     */   {
/* 4105:3813 */     return "%s %s found";
/* 4106:     */   }
/* 4107:     */   
/* 4108:     */   public final void unableToCleanupTemporaryIdTable(Throwable t)
/* 4109:     */   {
/* 4110:3818 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000283: " + unableToCleanupTemporaryIdTable$str(), t);
/* 4111:     */   }
/* 4112:     */   
/* 4113:     */   protected String unableToCleanupTemporaryIdTable$str()
/* 4114:     */   {
/* 4115:3822 */     return "Unable to cleanup temporary id table after use [%s]";
/* 4116:     */   }
/* 4117:     */   
/* 4118:     */   public final void subResolverException(String message)
/* 4119:     */   {
/* 4120:3827 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000257: " + subResolverException$str(), message);
/* 4121:     */   }
/* 4122:     */   
/* 4123:     */   protected String subResolverException$str()
/* 4124:     */   {
/* 4125:3831 */     return "sub-resolver threw unexpected exception, continuing to next : %s";
/* 4126:     */   }
/* 4127:     */   
/* 4128:     */   public final void unableToSwitchToMethodUsingColumnIndex(Method method)
/* 4129:     */   {
/* 4130:3836 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000370: " + unableToSwitchToMethodUsingColumnIndex$str(), method);
/* 4131:     */   }
/* 4132:     */   
/* 4133:     */   protected String unableToSwitchToMethodUsingColumnIndex$str()
/* 4134:     */   {
/* 4135:3840 */     return "Exception switching from method: [%s] to a method using the column index. Reverting to using: [%<s]";
/* 4136:     */   }
/* 4137:     */   
/* 4138:     */   public final void secondLevelCacheMisses(long secondLevelCacheMissCount)
/* 4139:     */   {
/* 4140:3845 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000238: " + secondLevelCacheMisses$str(), Long.valueOf(secondLevelCacheMissCount));
/* 4141:     */   }
/* 4142:     */   
/* 4143:     */   protected String secondLevelCacheMisses$str()
/* 4144:     */   {
/* 4145:3849 */     return "Second level cache misses: %s";
/* 4146:     */   }
/* 4147:     */   
/* 4148:     */   public final void invalidArrayElementType(String message)
/* 4149:     */   {
/* 4150:3854 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000132: " + invalidArrayElementType$str(), message);
/* 4151:     */   }
/* 4152:     */   
/* 4153:     */   protected String invalidArrayElementType$str()
/* 4154:     */   {
/* 4155:3858 */     return "Array element type error\n%s";
/* 4156:     */   }
/* 4157:     */   
/* 4158:     */   public final void unableToBindValueToParameter(String nullSafeToString, int index, String message)
/* 4159:     */   {
/* 4160:3863 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000278: " + unableToBindValueToParameter$str(), nullSafeToString, Integer.valueOf(index), message);
/* 4161:     */   }
/* 4162:     */   
/* 4163:     */   protected String unableToBindValueToParameter$str()
/* 4164:     */   {
/* 4165:3867 */     return "Could not bind value '%s' to parameter: %s; %s";
/* 4166:     */   }
/* 4167:     */   
/* 4168:     */   public final void unableToConstructCurrentSessionContext(String impl, Throwable e)
/* 4169:     */   {
/* 4170:3872 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000302: " + unableToConstructCurrentSessionContext$str(), impl);
/* 4171:     */   }
/* 4172:     */   
/* 4173:     */   protected String unableToConstructCurrentSessionContext$str()
/* 4174:     */   {
/* 4175:3876 */     return "Unable to construct current session context [%s]";
/* 4176:     */   }
/* 4177:     */   
/* 4178:     */   public final void hql(String hql, Long valueOf, Long valueOf2)
/* 4179:     */   {
/* 4180:3881 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, "HHH000117: " + hql$str(), hql, valueOf, valueOf2);
/* 4181:     */   }
/* 4182:     */   
/* 4183:     */   protected String hql$str()
/* 4184:     */   {
/* 4185:3885 */     return "HQL: %s, time: %sms, rows: %s";
/* 4186:     */   }
/* 4187:     */   
/* 4188:     */   public final void compositeIdClassDoesNotOverrideHashCode(String name)
/* 4189:     */   {
/* 4190:3890 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000039: " + compositeIdClassDoesNotOverrideHashCode$str(), name);
/* 4191:     */   }
/* 4192:     */   
/* 4193:     */   protected String compositeIdClassDoesNotOverrideHashCode$str()
/* 4194:     */   {
/* 4195:3894 */     return "Composite-id class does not override hashCode(): %s";
/* 4196:     */   }
/* 4197:     */   
/* 4198:     */   public final void noSessionFactoryWithJndiName(String sfJNDIName, NameNotFoundException e)
/* 4199:     */   {
/* 4200:3899 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000184: " + noSessionFactoryWithJndiName$str(), sfJNDIName);
/* 4201:     */   }
/* 4202:     */   
/* 4203:     */   protected String noSessionFactoryWithJndiName$str()
/* 4204:     */   {
/* 4205:3903 */     return "No session factory with JNDI name %s";
/* 4206:     */   }
/* 4207:     */   
/* 4208:     */   public final void unableToWriteCachedFile(String path, String message)
/* 4209:     */   {
/* 4210:3908 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000378: " + unableToWriteCachedFile$str(), path, message);
/* 4211:     */   }
/* 4212:     */   
/* 4213:     */   protected String unableToWriteCachedFile$str()
/* 4214:     */   {
/* 4215:3912 */     return "I/O reported error writing cached file : %s: %s";
/* 4216:     */   }
/* 4217:     */   
/* 4218:     */   public final void factoryUnboundFromJndiName(String name)
/* 4219:     */   {
/* 4220:3917 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000097: " + factoryUnboundFromJndiName$str(), name);
/* 4221:     */   }
/* 4222:     */   
/* 4223:     */   protected String factoryUnboundFromJndiName$str()
/* 4224:     */   {
/* 4225:3921 */     return "Unbound factory from JNDI name: %s";
/* 4226:     */   }
/* 4227:     */   
/* 4228:     */   public final void unableToFindPersistenceXmlInClasspath()
/* 4229:     */   {
/* 4230:3926 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000318: " + unableToFindPersistenceXmlInClasspath$str(), new Object[0]);
/* 4231:     */   }
/* 4232:     */   
/* 4233:     */   protected String unableToFindPersistenceXmlInClasspath$str()
/* 4234:     */   {
/* 4235:3930 */     return "Could not find any META-INF/persistence.xml file in the classpath";
/* 4236:     */   }
/* 4237:     */   
/* 4238:     */   public final void unableToCompleteSchemaUpdate(Exception e)
/* 4239:     */   {
/* 4240:3935 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000299: " + unableToCompleteSchemaUpdate$str(), new Object[0]);
/* 4241:     */   }
/* 4242:     */   
/* 4243:     */   protected String unableToCompleteSchemaUpdate$str()
/* 4244:     */   {
/* 4245:3939 */     return "Could not complete schema update";
/* 4246:     */   }
/* 4247:     */   
/* 4248:     */   public final void settersOfLazyClassesCannotBeFinal(String entityName, String name)
/* 4249:     */   {
/* 4250:3944 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000243: " + settersOfLazyClassesCannotBeFinal$str(), entityName, name);
/* 4251:     */   }
/* 4252:     */   
/* 4253:     */   protected String settersOfLazyClassesCannotBeFinal$str()
/* 4254:     */   {
/* 4255:3948 */     return "Setters of lazy classes cannot be final: %s.%s";
/* 4256:     */   }
/* 4257:     */   
/* 4258:     */   public final void usingUuidHexGenerator(String name, String name2)
/* 4259:     */   {
/* 4260:3953 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000409: " + usingUuidHexGenerator$str(), name, name2);
/* 4261:     */   }
/* 4262:     */   
/* 4263:     */   protected String usingUuidHexGenerator$str()
/* 4264:     */   {
/* 4265:3957 */     return "Using %s which does not generate IETF RFC 4122 compliant UUID values; consider using %s instead";
/* 4266:     */   }
/* 4267:     */   
/* 4268:     */   public final void propertiesNotFound()
/* 4269:     */   {
/* 4270:3962 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000206: " + propertiesNotFound$str(), new Object[0]);
/* 4271:     */   }
/* 4272:     */   
/* 4273:     */   protected String propertiesNotFound$str()
/* 4274:     */   {
/* 4275:3966 */     return "hibernate.properties not found";
/* 4276:     */   }
/* 4277:     */   
/* 4278:     */   public final void configuringFromUrl(URL url)
/* 4279:     */   {
/* 4280:3971 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000044: " + configuringFromUrl$str(), url);
/* 4281:     */   }
/* 4282:     */   
/* 4283:     */   protected String configuringFromUrl$str()
/* 4284:     */   {
/* 4285:3975 */     return "Configuring from URL: %s";
/* 4286:     */   }
/* 4287:     */   
/* 4288:     */   public final void queryCachePuts(long queryCachePutCount)
/* 4289:     */   {
/* 4290:3980 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000215: " + queryCachePuts$str(), Long.valueOf(queryCachePutCount));
/* 4291:     */   }
/* 4292:     */   
/* 4293:     */   protected String queryCachePuts$str()
/* 4294:     */   {
/* 4295:3984 */     return "Query cache puts: %s";
/* 4296:     */   }
/* 4297:     */   
/* 4298:     */   public final void configuringFromXmlDocument()
/* 4299:     */   {
/* 4300:3989 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000045: " + configuringFromXmlDocument$str(), new Object[0]);
/* 4301:     */   }
/* 4302:     */   
/* 4303:     */   protected String configuringFromXmlDocument$str()
/* 4304:     */   {
/* 4305:3993 */     return "Configuring from XML document";
/* 4306:     */   }
/* 4307:     */   
/* 4308:     */   public final void expectedType(String name, String string)
/* 4309:     */   {
/* 4310:3998 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000091: " + expectedType$str(), name, string);
/* 4311:     */   }
/* 4312:     */   
/* 4313:     */   protected String expectedType$str()
/* 4314:     */   {
/* 4315:4002 */     return "Expected type: %s, actual value: %s";
/* 4316:     */   }
/* 4317:     */   
/* 4318:     */   public final void usingOldDtd()
/* 4319:     */   {
/* 4320:4007 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000404: " + usingOldDtd$str(), new Object[0]);
/* 4321:     */   }
/* 4322:     */   
/* 4323:     */   protected String usingOldDtd$str()
/* 4324:     */   {
/* 4325:4011 */     return "Don't use old DTDs, read the Hibernate 3.x Migration Guide!";
/* 4326:     */   }
/* 4327:     */   
/* 4328:     */   public final void c3p0ProviderClassNotFound(String c3p0ProviderClassName)
/* 4329:     */   {
/* 4330:4016 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000022: " + c3p0ProviderClassNotFound$str(), c3p0ProviderClassName);
/* 4331:     */   }
/* 4332:     */   
/* 4333:     */   protected String c3p0ProviderClassNotFound$str()
/* 4334:     */   {
/* 4335:4020 */     return "c3p0 properties were encountered, but the %s provider class was not found on the classpath; these properties are going to be ignored.";
/* 4336:     */   }
/* 4337:     */   
/* 4338:     */   public final void indexes(Set keySet)
/* 4339:     */   {
/* 4340:4025 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000126: " + indexes$str(), keySet);
/* 4341:     */   }
/* 4342:     */   
/* 4343:     */   protected String indexes$str()
/* 4344:     */   {
/* 4345:4029 */     return "Indexes: %s";
/* 4346:     */   }
/* 4347:     */   
/* 4348:     */   public final void unableToRollbackConnection(Exception ignore)
/* 4349:     */   {
/* 4350:4034 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000363: " + unableToRollbackConnection$str(), ignore);
/* 4351:     */   }
/* 4352:     */   
/* 4353:     */   protected String unableToRollbackConnection$str()
/* 4354:     */   {
/* 4355:4038 */     return "Unable to rollback connection on exception [%s]";
/* 4356:     */   }
/* 4357:     */   
/* 4358:     */   public final void immutableAnnotationOnNonRoot(String className)
/* 4359:     */   {
/* 4360:4043 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000124: " + immutableAnnotationOnNonRoot$str(), className);
/* 4361:     */   }
/* 4362:     */   
/* 4363:     */   protected String immutableAnnotationOnNonRoot$str()
/* 4364:     */   {
/* 4365:4047 */     return "@Immutable used on a non root entity: ignored for %s";
/* 4366:     */   }
/* 4367:     */   
/* 4368:     */   public final void configuringFromFile(String file)
/* 4369:     */   {
/* 4370:4052 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000042: " + configuringFromFile$str(), file);
/* 4371:     */   }
/* 4372:     */   
/* 4373:     */   protected String configuringFromFile$str()
/* 4374:     */   {
/* 4375:4056 */     return "Configuring from file: %s";
/* 4376:     */   }
/* 4377:     */   
/* 4378:     */   public final void entitiesLoaded(long entityLoadCount)
/* 4379:     */   {
/* 4380:4061 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000079: " + entitiesLoaded$str(), Long.valueOf(entityLoadCount));
/* 4381:     */   }
/* 4382:     */   
/* 4383:     */   protected String entitiesLoaded$str()
/* 4384:     */   {
/* 4385:4065 */     return "Entities loaded: %s";
/* 4386:     */   }
/* 4387:     */   
/* 4388:     */   public final void invalidSubStrategy(String className)
/* 4389:     */   {
/* 4390:4070 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000138: " + invalidSubStrategy$str(), className);
/* 4391:     */   }
/* 4392:     */   
/* 4393:     */   protected String invalidSubStrategy$str()
/* 4394:     */   {
/* 4395:4074 */     return "Mixing inheritance strategy in a entity hierarchy is not allowed, ignoring sub strategy in: %s";
/* 4396:     */   }
/* 4397:     */   
/* 4398:     */   public final void updatingSchema()
/* 4399:     */   {
/* 4400:4079 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000396: " + updatingSchema$str(), new Object[0]);
/* 4401:     */   }
/* 4402:     */   
/* 4403:     */   protected String updatingSchema$str()
/* 4404:     */   {
/* 4405:4083 */     return "Updating schema";
/* 4406:     */   }
/* 4407:     */   
/* 4408:     */   public final void unsupportedIngresVersion()
/* 4409:     */   {
/* 4410:4088 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000391: " + unsupportedIngresVersion$str(), new Object[0]);
/* 4411:     */   }
/* 4412:     */   
/* 4413:     */   protected String unsupportedIngresVersion$str()
/* 4414:     */   {
/* 4415:4092 */     return "Ingres 10 is not yet fully supported; using Ingres 9.3 dialect";
/* 4416:     */   }
/* 4417:     */   
/* 4418:     */   public final void unableToLocateNClobClass()
/* 4419:     */   {
/* 4420:4097 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000333: " + unableToLocateNClobClass$str(), new Object[0]);
/* 4421:     */   }
/* 4422:     */   
/* 4423:     */   protected String unableToLocateNClobClass$str()
/* 4424:     */   {
/* 4425:4101 */     return "Could not locate 'java.sql.NClob' class; assuming JDBC 3";
/* 4426:     */   }
/* 4427:     */   
/* 4428:     */   public final void unableToBindFactoryToJndi(JndiException e)
/* 4429:     */   {
/* 4430:4106 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000277: " + unableToBindFactoryToJndi$str(), new Object[0]);
/* 4431:     */   }
/* 4432:     */   
/* 4433:     */   protected String unableToBindFactoryToJndi$str()
/* 4434:     */   {
/* 4435:4110 */     return "Could not bind factory to JNDI";
/* 4436:     */   }
/* 4437:     */   
/* 4438:     */   public final void unableToClosePooledConnection(SQLException e)
/* 4439:     */   {
/* 4440:4115 */     this.log.logf(FQCN, Logger.Level.WARN, e, "HHH000293: " + unableToClosePooledConnection$str(), new Object[0]);
/* 4441:     */   }
/* 4442:     */   
/* 4443:     */   protected String unableToClosePooledConnection$str()
/* 4444:     */   {
/* 4445:4119 */     return "Problem closing pooled connection";
/* 4446:     */   }
/* 4447:     */   
/* 4448:     */   public final void unableToBuildEnhancementMetamodel(String className)
/* 4449:     */   {
/* 4450:4124 */     this.log.logf(FQCN, Logger.Level.ERROR, null, "HHH000279: " + unableToBuildEnhancementMetamodel$str(), className);
/* 4451:     */   }
/* 4452:     */   
/* 4453:     */   protected String unableToBuildEnhancementMetamodel$str()
/* 4454:     */   {
/* 4455:4128 */     return "Unable to build enhancement metamodel for %s";
/* 4456:     */   }
/* 4457:     */   
/* 4458:     */   public final void unableToRunSchemaUpdate(Exception e)
/* 4459:     */   {
/* 4460:4133 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000366: " + unableToRunSchemaUpdate$str(), new Object[0]);
/* 4461:     */   }
/* 4462:     */   
/* 4463:     */   protected String unableToRunSchemaUpdate$str()
/* 4464:     */   {
/* 4465:4137 */     return "Error running schema update";
/* 4466:     */   }
/* 4467:     */   
/* 4468:     */   public final void unexpectedLiteralTokenType(int type)
/* 4469:     */   {
/* 4470:4142 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000380: " + unexpectedLiteralTokenType$str(), Integer.valueOf(type));
/* 4471:     */   }
/* 4472:     */   
/* 4473:     */   protected String unexpectedLiteralTokenType$str()
/* 4474:     */   {
/* 4475:4146 */     return "Unexpected literal token type [%s] passed for numeric processing";
/* 4476:     */   }
/* 4477:     */   
/* 4478:     */   public final void schemaExportUnsuccessful(Exception e)
/* 4479:     */   {
/* 4480:4151 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000231: " + schemaExportUnsuccessful$str(), new Object[0]);
/* 4481:     */   }
/* 4482:     */   
/* 4483:     */   protected String schemaExportUnsuccessful$str()
/* 4484:     */   {
/* 4485:4155 */     return "Schema export unsuccessful";
/* 4486:     */   }
/* 4487:     */   
/* 4488:     */   public final void startingUpdateTimestampsCache(String region)
/* 4489:     */   {
/* 4490:4160 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000250: " + startingUpdateTimestampsCache$str(), region);
/* 4491:     */   }
/* 4492:     */   
/* 4493:     */   protected String startingUpdateTimestampsCache$str()
/* 4494:     */   {
/* 4495:4164 */     return "Starting update timestamps cache at region: %s";
/* 4496:     */   }
/* 4497:     */   
/* 4498:     */   public final void startingServiceAtJndiName(String boundName)
/* 4499:     */   {
/* 4500:4169 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000249: " + startingServiceAtJndiName$str(), boundName);
/* 4501:     */   }
/* 4502:     */   
/* 4503:     */   protected String startingServiceAtJndiName$str()
/* 4504:     */   {
/* 4505:4173 */     return "Starting service at JNDI name: %s";
/* 4506:     */   }
/* 4507:     */   
/* 4508:     */   public final void usingDefaultTransactionStrategy()
/* 4509:     */   {
/* 4510:4178 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000399: " + usingDefaultTransactionStrategy$str(), new Object[0]);
/* 4511:     */   }
/* 4512:     */   
/* 4513:     */   protected String usingDefaultTransactionStrategy$str()
/* 4514:     */   {
/* 4515:4182 */     return "Using default transaction strategy (direct JDBC transactions)";
/* 4516:     */   }
/* 4517:     */   
/* 4518:     */   public final void unableToJoinTransaction(String transactionStrategy)
/* 4519:     */   {
/* 4520:4187 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000326: " + unableToJoinTransaction$str(), transactionStrategy);
/* 4521:     */   }
/* 4522:     */   
/* 4523:     */   protected String unableToJoinTransaction$str()
/* 4524:     */   {
/* 4525:4191 */     return "Cannot join transaction: do not override %s";
/* 4526:     */   }
/* 4527:     */   
/* 4528:     */   public final void entitiesDeleted(long entityDeleteCount)
/* 4529:     */   {
/* 4530:4196 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000076: " + entitiesDeleted$str(), Long.valueOf(entityDeleteCount));
/* 4531:     */   }
/* 4532:     */   
/* 4533:     */   protected String entitiesDeleted$str()
/* 4534:     */   {
/* 4535:4200 */     return "Entities deleted: %s";
/* 4536:     */   }
/* 4537:     */   
/* 4538:     */   public final void version(String versionString)
/* 4539:     */   {
/* 4540:4205 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000412: " + version$str(), versionString);
/* 4541:     */   }
/* 4542:     */   
/* 4543:     */   protected String version$str()
/* 4544:     */   {
/* 4545:4209 */     return "Hibernate Core {%s}";
/* 4546:     */   }
/* 4547:     */   
/* 4548:     */   public final void unableToCloseSession(HibernateException e)
/* 4549:     */   {
/* 4550:4214 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000294: " + unableToCloseSession$str(), new Object[0]);
/* 4551:     */   }
/* 4552:     */   
/* 4553:     */   protected String unableToCloseSession$str()
/* 4554:     */   {
/* 4555:4218 */     return "Could not close session";
/* 4556:     */   }
/* 4557:     */   
/* 4558:     */   public final void usingHibernateBuiltInConnectionPool()
/* 4559:     */   {
/* 4560:4223 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000402: " + usingHibernateBuiltInConnectionPool$str(), new Object[0]);
/* 4561:     */   }
/* 4562:     */   
/* 4563:     */   protected String usingHibernateBuiltInConnectionPool$str()
/* 4564:     */   {
/* 4565:4227 */     return "Using Hibernate built-in connection pool (not for production use!)";
/* 4566:     */   }
/* 4567:     */   
/* 4568:     */   public final void cachedFileNotFound(String path, FileNotFoundException error)
/* 4569:     */   {
/* 4570:4232 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000023: " + cachedFileNotFound$str(), path, error);
/* 4571:     */   }
/* 4572:     */   
/* 4573:     */   protected String cachedFileNotFound$str()
/* 4574:     */   {
/* 4575:4236 */     return "I/O reported cached file could not be found : %s : %s";
/* 4576:     */   }
/* 4577:     */   
/* 4578:     */   public final void closingUnreleasedBatch()
/* 4579:     */   {
/* 4580:4241 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, "HHH000420: " + closingUnreleasedBatch$str(), new Object[0]);
/* 4581:     */   }
/* 4582:     */   
/* 4583:     */   protected String closingUnreleasedBatch$str()
/* 4584:     */   {
/* 4585:4245 */     return "Closing un-released batch";
/* 4586:     */   }
/* 4587:     */   
/* 4588:     */   public final void unableToDestroyUpdateTimestampsCache(String region, String message)
/* 4589:     */   {
/* 4590:4250 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000310: " + unableToDestroyUpdateTimestampsCache$str(), region, message);
/* 4591:     */   }
/* 4592:     */   
/* 4593:     */   protected String unableToDestroyUpdateTimestampsCache$str()
/* 4594:     */   {
/* 4595:4254 */     return "Unable to destroy update timestamps cache: %s: %s";
/* 4596:     */   }
/* 4597:     */   
/* 4598:     */   public final void couldNotBindJndiListener()
/* 4599:     */   {
/* 4600:4259 */     this.log.logf(FQCN, Logger.Level.DEBUG, null, "HHH000127: " + couldNotBindJndiListener$str(), new Object[0]);
/* 4601:     */   }
/* 4602:     */   
/* 4603:     */   protected String couldNotBindJndiListener$str()
/* 4604:     */   {
/* 4605:4263 */     return "Could not bind JNDI listener";
/* 4606:     */   }
/* 4607:     */   
/* 4608:     */   public final void unableToToggleAutoCommit(Exception e)
/* 4609:     */   {
/* 4610:4268 */     this.log.logf(FQCN, Logger.Level.ERROR, e, "HHH000372: " + unableToToggleAutoCommit$str(), new Object[0]);
/* 4611:     */   }
/* 4612:     */   
/* 4613:     */   protected String unableToToggleAutoCommit$str()
/* 4614:     */   {
/* 4615:4272 */     return "Could not toggle autocommit";
/* 4616:     */   }
/* 4617:     */   
/* 4618:     */   public final void scopingTypesToSessionFactoryAfterAlreadyScoped(SessionFactoryImplementor factory, SessionFactoryImplementor factory2)
/* 4619:     */   {
/* 4620:4277 */     this.log.logf(FQCN, Logger.Level.WARN, null, "HHH000233: " + scopingTypesToSessionFactoryAfterAlreadyScoped$str(), factory, factory2);
/* 4621:     */   }
/* 4622:     */   
/* 4623:     */   protected String scopingTypesToSessionFactoryAfterAlreadyScoped$str()
/* 4624:     */   {
/* 4625:4281 */     return "Scoping types to session factory %s after already scoped %s";
/* 4626:     */   }
/* 4627:     */   
/* 4628:     */   public final void configurationResource(String resource)
/* 4629:     */   {
/* 4630:4286 */     this.log.logf(FQCN, Logger.Level.INFO, null, "HHH000040: " + configurationResource$str(), resource);
/* 4631:     */   }
/* 4632:     */   
/* 4633:     */   protected String configurationResource$str()
/* 4634:     */   {
/* 4635:4290 */     return "Configuration resource: %s";
/* 4636:     */   }
/* 4637:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.CoreMessageLogger_.logger
 * JD-Core Version:    0.7.0.1
 */