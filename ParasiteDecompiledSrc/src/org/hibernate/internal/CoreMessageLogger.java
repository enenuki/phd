package org.hibernate.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import org.hibernate.HibernateException;
import org.hibernate.cache.CacheException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.loading.internal.CollectionLoadContext;
import org.hibernate.engine.loading.internal.EntityLoadContext;
import org.hibernate.engine.spi.CollectionKey;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.service.jdbc.dialect.internal.AbstractDialectResolver;
import org.hibernate.service.jndi.JndiException;
import org.hibernate.service.jndi.JndiNameException;
import org.hibernate.type.BasicType;
import org.hibernate.type.SerializationException;
import org.hibernate.type.Type;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Cause;
import org.jboss.logging.LogMessage;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.Message;
import org.jboss.logging.MessageLogger;

@MessageLogger(projectCode="HHH")
public abstract interface CoreMessageLogger
  extends BasicLogger
{
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Already session bound on call to bind(); make sure you clean up your sessions!", id=2)
  public abstract void alreadySessionBound();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Autocommit mode: %s", id=6)
  public abstract void autoCommitMode(boolean paramBoolean);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="JTASessionContext being used with JDBCTransactionFactory; auto-flush will not operate correctly with getCurrentSession()", id=8)
  public abstract void autoFlushWillNotWork();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="On release of batch it still contained JDBC statements", id=10)
  public abstract void batchContainedStatementsOnRelease();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Bytecode provider name : %s", id=21)
  public abstract void bytecodeProvider(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="c3p0 properties were encountered, but the %s provider class was not found on the classpath; these properties are going to be ignored.", id=22)
  public abstract void c3p0ProviderClassNotFound(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="I/O reported cached file could not be found : %s : %s", id=23)
  public abstract void cachedFileNotFound(String paramString, FileNotFoundException paramFileNotFoundException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Cache provider: %s", id=24)
  public abstract void cacheProvider(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Calling joinTransaction() on a non JTA EntityManager", id=27)
  public abstract void callingJoinTransactionOnNonJtaEntityManager();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Cleaning up connection pool [%s]", id=30)
  public abstract void cleaningUpConnectionPool(String paramString);
  
  @LogMessage(level=Logger.Level.DEBUG)
  @Message(value="Closing", id=31)
  public abstract void closing();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Collections fetched (minimize this): %s", id=32)
  public abstract void collectionsFetched(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Collections loaded: %s", id=33)
  public abstract void collectionsLoaded(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Collections recreated: %s", id=34)
  public abstract void collectionsRecreated(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Collections removed: %s", id=35)
  public abstract void collectionsRemoved(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Collections updated: %s", id=36)
  public abstract void collectionsUpdated(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Columns: %s", id=37)
  public abstract void columns(Set paramSet);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Composite-id class does not override equals(): %s", id=38)
  public abstract void compositeIdClassDoesNotOverrideEquals(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Composite-id class does not override hashCode(): %s", id=39)
  public abstract void compositeIdClassDoesNotOverrideHashCode(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Configuration resource: %s", id=40)
  public abstract void configurationResource(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Configured SessionFactory: %s", id=41)
  public abstract void configuredSessionFactory(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Configuring from file: %s", id=42)
  public abstract void configuringFromFile(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Configuring from resource: %s", id=43)
  public abstract void configuringFromResource(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Configuring from URL: %s", id=44)
  public abstract void configuringFromUrl(URL paramURL);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Configuring from XML document", id=45)
  public abstract void configuringFromXmlDocument();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Connection properties: %s", id=46)
  public abstract void connectionProperties(Properties paramProperties);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Connections obtained: %s", id=48)
  public abstract void connectionsObtained(long paramLong);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Container is providing a null PersistenceUnitRootUrl: discovery impossible", id=50)
  public abstract void containerProvidingNullPersistenceUnitRootUrl();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Ignoring bag join fetch [%s] due to prior collection join fetch", id=51)
  public abstract void containsJoinFetchedCollection(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Creating subcontext: %s", id=53)
  public abstract void creatingSubcontextInfo(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Defining %s=true ignored in HEM", id=59)
  public abstract void definingFlushBeforeCompletionIgnoredInHem(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="@ForceDiscriminator is deprecated use @DiscriminatorOptions instead.", id=62)
  public abstract void deprecatedForceDescriminatorAnnotation();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="The Oracle9Dialect dialect has been deprecated; use either Oracle9iDialect or Oracle10gDialect instead", id=63)
  public abstract void deprecatedOracle9Dialect();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="The OracleDialect dialect has been deprecated; use Oracle8iDialect instead", id=64)
  public abstract void deprecatedOracleDialect();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="DEPRECATED : use [%s] instead with custom [%s] implementation", id=65)
  public abstract void deprecatedUuidGenerator(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Disallowing insert statement comment for select-identity due to Oracle driver bug", id=67)
  public abstract void disallowingInsertStatementComment();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Duplicate generator name %s", id=69)
  public abstract void duplicateGeneratorName(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Duplicate generator table: %s", id=70)
  public abstract void duplicateGeneratorTable(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Duplicate import: %s -> %s", id=71)
  public abstract void duplicateImport(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Duplicate joins for class: %s", id=72)
  public abstract void duplicateJoins(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="entity-listener duplication, first event definition will be used: %s", id=73)
  public abstract void duplicateListener(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Found more than one <persistence-unit-metadata>, subsequent ignored", id=74)
  public abstract void duplicateMetadata();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Entities deleted: %s", id=76)
  public abstract void entitiesDeleted(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Entities fetched (minimize this): %s", id=77)
  public abstract void entitiesFetched(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Entities inserted: %s", id=78)
  public abstract void entitiesInserted(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Entities loaded: %s", id=79)
  public abstract void entitiesLoaded(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Entities updated: %s", id=80)
  public abstract void entitiesUpdated(long paramLong);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="@org.hibernate.annotations.Entity used on a non root entity: ignored for %s", id=81)
  public abstract void entityAnnotationOnNonRoot(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Entity Manager closed by someone else (%s must not be used)", id=82)
  public abstract void entityManagerClosedBySomeoneElse(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Entity [%s] is abstract-class/interface explicitly mapped as non-abstract; be sure to supply entity-names", id=84)
  public abstract void entityMappedAsNonAbstract(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="%s %s found", id=85)
  public abstract void exceptionHeaderFound(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="%s No %s found", id=86)
  public abstract void exceptionHeaderNotFound(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Exception in interceptor afterTransactionCompletion()", id=87)
  public abstract void exceptionInAfterTransactionCompletionInterceptor(@Cause Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Exception in interceptor beforeTransactionCompletion()", id=88)
  public abstract void exceptionInBeforeTransactionCompletionInterceptor(@Cause Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Sub-resolver threw unexpected exception, continuing to next : %s", id=89)
  public abstract void exceptionInSubResolver(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Expected type: %s, actual value: %s", id=91)
  public abstract void expectedType(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="An item was expired by the cache while it was locked (increase your cache timeout): %s", id=92)
  public abstract void expired(Object paramObject);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Bound factory to JNDI name: %s", id=94)
  public abstract void factoryBoundToJndiName(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="A factory was renamed from [%s] to [%s] in JNDI", id=96)
  public abstract void factoryJndiRename(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unbound factory from JNDI name: %s", id=97)
  public abstract void factoryUnboundFromJndiName(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="A factory was unbound from name: %s", id=98)
  public abstract void factoryUnboundFromName(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="an assertion failure occured (this may indicate a bug in Hibernate, but is more likely due to unsafe use of the session): %s", id=99)
  public abstract void failed(Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Fail-safe cleanup (collections) : %s", id=100)
  public abstract void failSafeCollectionsCleanup(CollectionLoadContext paramCollectionLoadContext);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Fail-safe cleanup (entities) : %s", id=101)
  public abstract void failSafeEntitiesCleanup(EntityLoadContext paramEntityLoadContext);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Fetching database metadata", id=102)
  public abstract void fetchingDatabaseMetadata();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="@Filter not allowed on subclasses (ignored): %s", id=103)
  public abstract void filterAnnotationOnSubclass(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="firstResult/maxResults specified with collection fetch; applying in memory!", id=104)
  public abstract void firstOrMaxResultsSpecifiedWithCollectionFetch();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Flushes: %s", id=105)
  public abstract void flushes(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Forcing container resource cleanup on transaction completion", id=106)
  public abstract void forcingContainerResourceCleanup();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Forcing table use for sequence-style generator due to pooled optimizer selection where db does not support pooled sequences", id=107)
  public abstract void forcingTableUse();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Foreign keys: %s", id=108)
  public abstract void foreignKeys(Set paramSet);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Found mapping document in jar: %s", id=109)
  public abstract void foundMappingDocument(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Getters of lazy classes cannot be final: %s.%s", id=112)
  public abstract void gettersOfLazyClassesCannotBeFinal(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="GUID identifier generated: %s", id=113)
  public abstract void guidGenerated(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Handling transient entity in delete processing", id=114)
  public abstract void handlingTransientEntity();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Hibernate connection pool size: %s", id=115)
  public abstract void hibernateConnectionPoolSize(int paramInt);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Config specified explicit optimizer of [%s], but [%s=%s; honoring optimizer setting", id=116)
  public abstract void honoringOptimizerSetting(String paramString1, String paramString2, int paramInt);
  
  @LogMessage(level=Logger.Level.DEBUG)
  @Message(value="HQL: %s, time: %sms, rows: %s", id=117)
  public abstract void hql(String paramString, Long paramLong1, Long paramLong2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="HSQLDB supports only READ_UNCOMMITTED isolation", id=118)
  public abstract void hsqldbSupportsOnlyReadCommittedIsolation();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="On EntityLoadContext#clear, hydratingEntities contained [%s] entries", id=119)
  public abstract void hydratingEntitiesCount(int paramInt);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Ignoring unique constraints specified on table generator [%s]", id=120)
  public abstract void ignoringTableGeneratorConstraints(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Ignoring unrecognized query hint [%s]", id=121)
  public abstract void ignoringUnrecognizedQueryHint(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="IllegalArgumentException in class: %s, getter method of property: %s", id=122)
  public abstract void illegalPropertyGetterArgument(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="IllegalArgumentException in class: %s, setter method of property: %s", id=123)
  public abstract void illegalPropertySetterArgument(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="@Immutable used on a non root entity: ignored for %s", id=124)
  public abstract void immutableAnnotationOnNonRoot(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Mapping metadata cache was not completely processed", id=125)
  public abstract void incompleteMappingMetadataCacheProcessing();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Indexes: %s", id=126)
  public abstract void indexes(Set paramSet);
  
  @LogMessage(level=Logger.Level.DEBUG)
  @Message(value="Could not bind JNDI listener", id=127)
  public abstract void couldNotBindJndiListener();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Instantiating explicit connection provider: %s", id=130)
  public abstract void instantiatingExplicitConnectionProvider(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Array element type error\n%s", id=132)
  public abstract void invalidArrayElementType(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Discriminator column has to be defined in the root entity, it will be ignored in subclass: %s", id=133)
  public abstract void invalidDiscriminatorAnnotation(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Application attempted to edit read only item: %s", id=134)
  public abstract void invalidEditOfReadOnlyItem(Object paramObject);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Invalid JNDI name: %s", id=135)
  public abstract void invalidJndiName(String paramString, @Cause JndiNameException paramJndiNameException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Inapropriate use of @OnDelete on entity, annotation ignored: %s", id=136)
  public abstract void invalidOnDeleteAnnotation(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Root entity should not hold an PrimaryKeyJoinColum(s), will be ignored", id=137)
  public abstract void invalidPrimaryKeyJoinColumnAnnotation();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Mixing inheritance strategy in a entity hierarchy is not allowed, ignoring sub strategy in: %s", id=138)
  public abstract void invalidSubStrategy(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Illegal use of @Table in a subclass of a SINGLE_TABLE hierarchy: %s", id=139)
  public abstract void invalidTableAnnotation(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="JACC contextID: %s", id=140)
  public abstract void jaccContextId(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="java.sql.Types mapped the same code [%s] multiple times; was [%s]; now [%s]", id=141)
  public abstract void JavaSqlTypesMappedSameCodeMultipleTimes(int paramInt, String paramString1, String paramString2);
  
  @Message(value="Javassist Enhancement failed: %s", id=142)
  public abstract String javassistEnhancementFailed(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="%s = false breaks the EJB3 specification", id=144)
  public abstract void jdbcAutoCommitFalseBreaksEjb3Spec(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="No JDBC Driver class was specified by property %s", id=148)
  public abstract void jdbcDriverNotSpecified(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="JDBC isolation level: %s", id=149)
  public abstract void jdbcIsolationLevel(String paramString);
  
  @Message(value="JDBC rollback failed", id=151)
  public abstract String jdbcRollbackFailed();
  
  @Message(value="JDBC URL was not specified by property %s", id=152)
  public abstract String jdbcUrlNotSpecified(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="JNDI InitialContext properties:%s", id=154)
  public abstract void jndiInitialContextProperties(Hashtable paramHashtable);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="JNDI name %s does not handle a session factory reference", id=155)
  public abstract void jndiNameDoesNotHandleSessionFactoryReference(String paramString, @Cause ClassCastException paramClassCastException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Lazy property fetching available for: %s", id=157)
  public abstract void lazyPropertyFetchingAvailable(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="In CollectionLoadContext#endLoadingCollections, localLoadingCollectionKeys contained [%s], but no LoadingCollectionEntry was found in loadContexts", id=159)
  public abstract void loadingCollectionKeyNotFound(CollectionKey paramCollectionKey);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="On CollectionLoadContext#cleanup, localLoadingCollectionKeys contained [%s] entries", id=160)
  public abstract void localLoadingCollectionKeysCount(int paramInt);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Logging statistics....", id=161)
  public abstract void loggingStatistics();
  
  @LogMessage(level=Logger.Level.DEBUG)
  @Message(value="*** Logical connection closed ***", id=162)
  public abstract void logicalConnectionClosed();
  
  @LogMessage(level=Logger.Level.DEBUG)
  @Message(value="Logical connection releasing its physical connection", id=163)
  public abstract void logicalConnectionReleasingPhysicalConnection();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Max query time: %sms", id=173)
  public abstract void maxQueryTime(long paramLong);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Function template anticipated %s arguments, but %s arguments encountered", id=174)
  public abstract void missingArguments(int paramInt1, int paramInt2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Class annotated @org.hibernate.annotations.Entity but not javax.persistence.Entity (most likely a user error): %s", id=175)
  public abstract void missingEntityAnnotation(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error in named query: %s", id=177)
  public abstract void namedQueryError(String paramString, @Cause HibernateException paramHibernateException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Naming exception occurred accessing factory: %s", id=178)
  public abstract void namingExceptionAccessingFactory(NamingException paramNamingException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Narrowing proxy to %s - this operation breaks ==", id=179)
  public abstract void narrowingProxy(Class paramClass);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="FirstResult/maxResults specified on polymorphic query; applying in memory!", id=180)
  public abstract void needsLimit();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="No appropriate connection provider encountered, assuming application will be supplying connections", id=181)
  public abstract void noAppropriateConnectionProvider();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="No default (no-argument) constructor for class: %s (class must be instantiated by Interceptor)", id=182)
  public abstract void noDefaultConstructor(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="no persistent classes found for query class: %s", id=183)
  public abstract void noPersistentClassesFound(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="No session factory with JNDI name %s", id=184)
  public abstract void noSessionFactoryWithJndiName(String paramString, @Cause NameNotFoundException paramNameNotFoundException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Optimistic lock failures: %s", id=187)
  public abstract void optimisticLockFailures(long paramLong);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="@OrderBy not allowed for an indexed collection, annotation ignored.", id=189)
  public abstract void orderByAnnotationIndexedCollection();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Overriding %s is dangerous, this might break the EJB3 specification implementation", id=193)
  public abstract void overridingTransactionStrategyDangerous(String paramString);
  
  @LogMessage(level=Logger.Level.DEBUG)
  @Message(value="Package not found or wo package-info.java: %s", id=194)
  public abstract void packageNotFound(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Parameter position [%s] occurred as both JPA and Hibernate positional parameter", id=195)
  public abstract void parameterPositionOccurredAsBothJpaAndHibernatePositionalParameter(Integer paramInteger);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error parsing XML (%s) : %s", id=196)
  public abstract void parsingXmlError(int paramInt, String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error parsing XML: %s(%s) %s", id=197)
  public abstract void parsingXmlErrorForFile(String paramString1, int paramInt, String paramString2);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Warning parsing XML (%s) : %s", id=198)
  public abstract void parsingXmlWarning(int paramInt, String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Warning parsing XML: %s(%s) %s", id=199)
  public abstract void parsingXmlWarningForFile(String paramString1, int paramInt, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Persistence provider caller does not implement the EJB3 spec correctly.PersistenceUnitInfo.getNewTempClassLoader() is null.", id=200)
  public abstract void persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Pooled optimizer source reported [%s] as the initial value; use of 1 or greater highly recommended", id=201)
  public abstract void pooledOptimizerReportedInitialValue(IntegralDataTypeHolder paramIntegralDataTypeHolder);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="PreparedStatement was already in the batch, [%s].", id=202)
  public abstract void preparedStatementAlreadyInBatch(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="processEqualityExpression() : No expression to process!", id=203)
  public abstract void processEqualityExpression();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Processing PersistenceUnitInfo [\n\tname: %s\n\t...]", id=204)
  public abstract void processingPersistenceUnitInfoName(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Loaded properties from resource hibernate.properties: %s", id=205)
  public abstract void propertiesLoaded(Properties paramProperties);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="hibernate.properties not found", id=206)
  public abstract void propertiesNotFound();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Property %s not found in class but described in <mapping-file/> (possible typo error)", id=207)
  public abstract void propertyNotFound(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="%s has been deprecated in favor of %s; that provider will be used instead.", id=208)
  public abstract void providerClassDeprecated(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="proxool properties were encountered, but the %s provider class was not found on the classpath; these properties are going to be ignored.", id=209)
  public abstract void proxoolProviderClassNotFound(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Queries executed to database: %s", id=210)
  public abstract void queriesExecuted(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Query cache hits: %s", id=213)
  public abstract void queryCacheHits(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Query cache misses: %s", id=214)
  public abstract void queryCacheMisses(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Query cache puts: %s", id=215)
  public abstract void queryCachePuts(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="RDMSOS2200Dialect version: 1.0", id=218)
  public abstract void rdmsOs2200Dialect();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Reading mappings from cache file: %s", id=219)
  public abstract void readingCachedMappings(File paramFile);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Reading mappings from file: %s", id=220)
  public abstract void readingMappingsFromFile(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Reading mappings from resource: %s", id=221)
  public abstract void readingMappingsFromResource(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="read-only cache configured for mutable collection [%s]", id=222)
  public abstract void readOnlyCacheConfiguredForMutableCollection(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Recognized obsolete hibernate namespace %s. Use namespace %s instead. Refer to Hibernate 3.6 Migration Guide!", id=223)
  public abstract void recognizedObsoleteHibernateNamespace(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Property [%s] has been renamed to [%s]; update your properties appropriately", id=225)
  public abstract void renamedProperty(Object paramObject1, Object paramObject2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Required a different provider: %s", id=226)
  public abstract void requiredDifferentProvider(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Running hbm2ddl schema export", id=227)
  public abstract void runningHbm2ddlSchemaExport();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Running hbm2ddl schema update", id=228)
  public abstract void runningHbm2ddlSchemaUpdate();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Running schema validator", id=229)
  public abstract void runningSchemaValidator();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Schema export complete", id=230)
  public abstract void schemaExportComplete();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Schema export unsuccessful", id=231)
  public abstract void schemaExportUnsuccessful(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Schema update complete", id=232)
  public abstract void schemaUpdateComplete();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Scoping types to session factory %s after already scoped %s", id=233)
  public abstract void scopingTypesToSessionFactoryAfterAlreadyScoped(SessionFactoryImplementor paramSessionFactoryImplementor1, SessionFactoryImplementor paramSessionFactoryImplementor2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Searching for mapping documents in jar: %s", id=235)
  public abstract void searchingForMappingDocuments(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Second level cache hits: %s", id=237)
  public abstract void secondLevelCacheHits(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Second level cache misses: %s", id=238)
  public abstract void secondLevelCacheMisses(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Second level cache puts: %s", id=239)
  public abstract void secondLevelCachePuts(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Service properties: %s", id=240)
  public abstract void serviceProperties(Properties paramProperties);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Sessions closed: %s", id=241)
  public abstract void sessionsClosed(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Sessions opened: %s", id=242)
  public abstract void sessionsOpened(long paramLong);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Setters of lazy classes cannot be final: %s.%s", id=243)
  public abstract void settersOfLazyClassesCannotBeFinal(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="@Sort not allowed for an indexed collection, annotation ignored.", id=244)
  public abstract void sortAnnotationIndexedCollection();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Manipulation query [%s] resulted in [%s] split queries", id=245)
  public abstract void splitQueries(String paramString, int paramInt);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="SQLException escaped proxy", id=246)
  public abstract void sqlExceptionEscapedProxy(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="SQL Error: %s, SQLState: %s", id=247)
  public abstract void sqlWarning(int paramInt, String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Starting query cache at region: %s", id=248)
  public abstract void startingQueryCache(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Starting service at JNDI name: %s", id=249)
  public abstract void startingServiceAtJndiName(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Starting update timestamps cache at region: %s", id=250)
  public abstract void startingUpdateTimestampsCache(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Start time: %s", id=251)
  public abstract void startTime(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Statements closed: %s", id=252)
  public abstract void statementsClosed(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Statements prepared: %s", id=253)
  public abstract void statementsPrepared(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Stopping service", id=255)
  public abstract void stoppingService();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="sub-resolver threw unexpected exception, continuing to next : %s", id=257)
  public abstract void subResolverException(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Successful transactions: %s", id=258)
  public abstract void successfulTransactions(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Synchronization [%s] was already registered", id=259)
  public abstract void synchronizationAlreadyRegistered(Synchronization paramSynchronization);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Exception calling user Synchronization [%s] : %s", id=260)
  public abstract void synchronizationFailed(Synchronization paramSynchronization, Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Table found: %s", id=261)
  public abstract void tableFound(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Table not found: %s", id=262)
  public abstract void tableNotFound(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Transactions: %s", id=266)
  public abstract void transactions(long paramLong);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Transaction started on non-root session", id=267)
  public abstract void transactionStartedOnNonRootSession();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Transaction strategy: %s", id=268)
  public abstract void transactionStrategy(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Type [%s] defined no registration keys; ignoring", id=269)
  public abstract void typeDefinedNoRegistrationKeys(BasicType paramBasicType);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Type registration [%s] overrides previous : %s", id=270)
  public abstract void typeRegistrationOverridesPrevious(String paramString, Type paramType);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Naming exception occurred accessing Ejb3Configuration", id=271)
  public abstract void unableToAccessEjb3Configuration(@Cause NamingException paramNamingException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error while accessing session factory with JNDI name %s", id=272)
  public abstract void unableToAccessSessionFactory(String paramString, @Cause NamingException paramNamingException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Error accessing type info result set : %s", id=273)
  public abstract void unableToAccessTypeInfoResultSet(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to apply constraints on DDL for %s", id=274)
  public abstract void unableToApplyConstraints(String paramString, @Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not bind Ejb3Configuration to JNDI", id=276)
  public abstract void unableToBindEjb3ConfigurationToJndi(@Cause JndiException paramJndiException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not bind factory to JNDI", id=277)
  public abstract void unableToBindFactoryToJndi(@Cause JndiException paramJndiException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Could not bind value '%s' to parameter: %s; %s", id=278)
  public abstract void unableToBindValueToParameter(String paramString1, int paramInt, String paramString2);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unable to build enhancement metamodel for %s", id=279)
  public abstract void unableToBuildEnhancementMetamodel(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Could not build SessionFactory using the MBean classpath - will try again using client classpath: %s", id=280)
  public abstract void unableToBuildSessionFactoryUsingMBeanClasspath(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to clean up callable statement", id=281)
  public abstract void unableToCleanUpCallableStatement(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to clean up prepared statement", id=282)
  public abstract void unableToCleanUpPreparedStatement(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to cleanup temporary id table after use [%s]", id=283)
  public abstract void unableToCleanupTemporaryIdTable(Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error closing connection", id=284)
  public abstract void unableToCloseConnection(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Error closing InitialContext [%s]", id=285)
  public abstract void unableToCloseInitialContext(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error closing input files: %s", id=286)
  public abstract void unableToCloseInputFiles(String paramString, @Cause IOException paramIOException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not close input stream", id=287)
  public abstract void unableToCloseInputStream(@Cause IOException paramIOException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not close input stream for %s", id=288)
  public abstract void unableToCloseInputStreamForResource(String paramString, @Cause IOException paramIOException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unable to close iterator", id=289)
  public abstract void unableToCloseIterator(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not close jar: %s", id=290)
  public abstract void unableToCloseJar(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error closing output file: %s", id=291)
  public abstract void unableToCloseOutputFile(String paramString, @Cause IOException paramIOException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="IOException occurred closing output stream", id=292)
  public abstract void unableToCloseOutputStream(@Cause IOException paramIOException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Problem closing pooled connection", id=293)
  public abstract void unableToClosePooledConnection(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not close session", id=294)
  public abstract void unableToCloseSession(@Cause HibernateException paramHibernateException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not close session during rollback", id=295)
  public abstract void unableToCloseSessionDuringRollback(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="IOException occurred closing stream", id=296)
  public abstract void unableToCloseStream(@Cause IOException paramIOException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not close stream on hibernate.properties: %s", id=297)
  public abstract void unableToCloseStreamError(IOException paramIOException);
  
  @Message(value="JTA commit failed", id=298)
  public abstract String unableToCommitJta();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not complete schema update", id=299)
  public abstract void unableToCompleteSchemaUpdate(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not complete schema validation", id=300)
  public abstract void unableToCompleteSchemaValidation(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to configure SQLExceptionConverter : %s", id=301)
  public abstract void unableToConfigureSqlExceptionConverter(HibernateException paramHibernateException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unable to construct current session context [%s]", id=302)
  public abstract void unableToConstructCurrentSessionContext(String paramString, @Cause Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to construct instance of specified SQLExceptionConverter : %s", id=303)
  public abstract void unableToConstructSqlExceptionConverter(Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not copy system properties, system properties will be ignored", id=304)
  public abstract void unableToCopySystemProperties();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not create proxy factory for:%s", id=305)
  public abstract void unableToCreateProxyFactory(String paramString, @Cause HibernateException paramHibernateException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error creating schema ", id=306)
  public abstract void unableToCreateSchema(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not deserialize cache file: %s : %s", id=307)
  public abstract void unableToDeserializeCache(String paramString, SerializationException paramSerializationException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to destroy cache: %s", id=308)
  public abstract void unableToDestroyCache(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to destroy query cache: %s: %s", id=309)
  public abstract void unableToDestroyQueryCache(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to destroy update timestamps cache: %s: %s", id=310)
  public abstract void unableToDestroyUpdateTimestampsCache(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unable to determine lock mode value : %s -> %s", id=311)
  public abstract void unableToDetermineLockModeValue(String paramString, Object paramObject);
  
  @Message(value="Could not determine transaction status", id=312)
  public abstract String unableToDetermineTransactionStatus();
  
  @Message(value="Could not determine transaction status after commit", id=313)
  public abstract String unableToDetermineTransactionStatusAfterCommit();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to drop temporary id table after use [%s]", id=314)
  public abstract void unableToDropTemporaryIdTable(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Exception executing batch [%s]", id=315)
  public abstract void unableToExecuteBatch(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Error executing resolver [%s] : %s", id=316)
  public abstract void unableToExecuteResolver(AbstractDialectResolver paramAbstractDialectResolver, String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Could not find any META-INF/persistence.xml file in the classpath", id=318)
  public abstract void unableToFindPersistenceXmlInClasspath();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not get database metadata", id=319)
  public abstract void unableToGetDatabaseMetadata(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to instantiate configured schema name resolver [%s] %s", id=320)
  public abstract void unableToInstantiateConfiguredSchemaNameResolver(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to instantiate specified optimizer [%s], falling back to noop", id=322)
  public abstract void unableToInstantiateOptimizer(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to instantiate UUID generation strategy class : %s", id=325)
  public abstract void unableToInstantiateUuidGenerationStrategy(Exception paramException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Cannot join transaction: do not override %s", id=326)
  public abstract void unableToJoinTransaction(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Error performing load command : %s", id=327)
  public abstract void unableToLoadCommand(HibernateException paramHibernateException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to load/access derby driver class sysinfo to check versions : %s", id=328)
  public abstract void unableToLoadDerbyDriver(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Problem loading properties from hibernate.properties", id=329)
  public abstract void unableToLoadProperties();
  
  @Message(value="Unable to locate config file: %s", id=330)
  public abstract String unableToLocateConfigFile(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to locate configured schema name resolver class [%s] %s", id=331)
  public abstract void unableToLocateConfiguredSchemaNameResolver(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to locate MBeanServer on JMX service shutdown", id=332)
  public abstract void unableToLocateMBeanServer();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Could not locate 'java.sql.NClob' class; assuming JDBC 3", id=333)
  public abstract void unableToLocateNClobClass();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to locate requested UUID generation strategy class : %s", id=334)
  public abstract void unableToLocateUuidGenerationStrategy(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to log SQLWarnings : %s", id=335)
  public abstract void unableToLogSqlWarnings(SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not log warnings", id=336)
  public abstract void unableToLogWarnings(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unable to mark for rollback on PersistenceException: ", id=337)
  public abstract void unableToMarkForRollbackOnPersistenceException(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unable to mark for rollback on TransientObjectException: ", id=338)
  public abstract void unableToMarkForRollbackOnTransientObjectException(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not obtain connection metadata: %s", id=339)
  public abstract void unableToObjectConnectionMetadata(SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not obtain connection to query metadata: %s", id=340)
  public abstract void unableToObjectConnectionToQueryMetadata(SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not obtain connection metadata : %s", id=341)
  public abstract void unableToObtainConnectionMetadata(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not obtain connection to query metadata : %s", id=342)
  public abstract void unableToObtainConnectionToQueryMetadata(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not obtain initial context", id=343)
  public abstract void unableToObtainInitialContext(@Cause NamingException paramNamingException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not parse the package-level metadata [%s]", id=344)
  public abstract void unableToParseMetadata(String paramString);
  
  @Message(value="JDBC commit failed", id=345)
  public abstract String unableToPerformJdbcCommit();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error during managed flush [%s]", id=346)
  public abstract void unableToPerformManagedFlush(String paramString);
  
  @Message(value="Unable to query java.sql.DatabaseMetaData", id=347)
  public abstract String unableToQueryDatabaseMetadata();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unable to read class: %s", id=348)
  public abstract void unableToReadClass(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Could not read column value from result set: %s; %s", id=349)
  public abstract void unableToReadColumnValueFromResultSet(String paramString1, String paramString2);
  
  @Message(value="Could not read a hi value - you need to populate the table: %s", id=350)
  public abstract String unableToReadHiValue(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not read or init a hi value", id=351)
  public abstract void unableToReadOrInitHiValue(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unable to release batch statement...", id=352)
  public abstract void unableToReleaseBatchStatement();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not release a cache lock : %s", id=353)
  public abstract void unableToReleaseCacheLock(CacheException paramCacheException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unable to release initial context: %s", id=354)
  public abstract void unableToReleaseContext(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to release created MBeanServer : %s", id=355)
  public abstract void unableToReleaseCreatedMBeanServer(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unable to release isolated connection [%s]", id=356)
  public abstract void unableToReleaseIsolatedConnection(Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to release type info result set", id=357)
  public abstract void unableToReleaseTypeInfoResultSet();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to erase previously added bag join fetch", id=358)
  public abstract void unableToRemoveBagJoinFetch();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Could not resolve aggregate function [%s]; using standard definition", id=359)
  public abstract void unableToResolveAggregateFunction(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unable to resolve mapping file [%s]", id=360)
  public abstract void unableToResolveMappingFile(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unable to retreive cache from JNDI [%s]: %s", id=361)
  public abstract void unableToRetrieveCache(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to retrieve type info result set : %s", id=362)
  public abstract void unableToRetrieveTypeInfoResultSet(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unable to rollback connection on exception [%s]", id=363)
  public abstract void unableToRollbackConnection(Exception paramException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Unable to rollback isolated transaction on error [%s] : [%s]", id=364)
  public abstract void unableToRollbackIsolatedTransaction(Exception paramException1, Exception paramException2);
  
  @Message(value="JTA rollback failed", id=365)
  public abstract String unableToRollbackJta();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Error running schema update", id=366)
  public abstract void unableToRunSchemaUpdate(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not set transaction to rollback only", id=367)
  public abstract void unableToSetTransactionToRollbackOnly(@Cause SystemException paramSystemException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Exception while stopping service", id=368)
  public abstract void unableToStopHibernateService(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Error stopping service [%s] : %s", id=369)
  public abstract void unableToStopService(Class paramClass, String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Exception switching from method: [%s] to a method using the column index. Reverting to using: [%<s]", id=370)
  public abstract void unableToSwitchToMethodUsingColumnIndex(Method paramMethod);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not synchronize database state with session: %s", id=371)
  public abstract void unableToSynchronizeDatabaseStateWithSession(HibernateException paramHibernateException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not toggle autocommit", id=372)
  public abstract void unableToToggleAutoCommit(@Cause Exception paramException);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unable to transform class: %s", id=373)
  public abstract void unableToTransformClass(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Could not unbind factory from JNDI", id=374)
  public abstract void unableToUnbindFactoryFromJndi(@Cause JndiException paramJndiException);
  
  @Message(value="Could not update hi value in: %s", id=375)
  public abstract Object unableToUpdateHiValue(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Could not updateQuery hi value in: %s", id=376)
  public abstract void unableToUpdateQueryHiValue(String paramString, @Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Error wrapping result set", id=377)
  public abstract void unableToWrapResultSet(@Cause SQLException paramSQLException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="I/O reported error writing cached file : %s: %s", id=378)
  public abstract void unableToWriteCachedFile(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unexpected literal token type [%s] passed for numeric processing", id=380)
  public abstract void unexpectedLiteralTokenType(int paramInt);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="JDBC driver did not return the expected number of row counts", id=381)
  public abstract void unexpectedRowCounts();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="unrecognized bytecode provider [%s], using javassist by default", id=382)
  public abstract void unknownBytecodeProvider(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unknown Ingres major version [%s]; using Ingres 9.2 dialect", id=383)
  public abstract void unknownIngresVersion(int paramInt);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unknown Oracle major version [%s]", id=384)
  public abstract void unknownOracleVersion(int paramInt);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unknown Microsoft SQL Server major version [%s] using SQL Server 2000 dialect", id=385)
  public abstract void unknownSqlServerVersion(int paramInt);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="ResultSet had no statement associated with it, but was not yet registered", id=386)
  public abstract void unregisteredResultSetWithoutStatement();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="ResultSet's statement was not registered", id=387)
  public abstract void unregisteredStatement();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unsuccessful: %s", id=388)
  public abstract void unsuccessful(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Unsuccessful: %s", id=389)
  public abstract void unsuccessfulCreate(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Overriding release mode as connection provider does not support 'after_statement'", id=390)
  public abstract void unsupportedAfterStatement();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Ingres 10 is not yet fully supported; using Ingres 9.3 dialect", id=391)
  public abstract void unsupportedIngresVersion();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Hibernate does not support SequenceGenerator.initialValue() unless '%s' set", id=392)
  public abstract void unsupportedInitialValue(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="The %s.%s.%s version of H2 implements temporary table creation such that it commits current transaction; multi-table, bulk hql/jpaql will not work properly", id=393)
  public abstract void unsupportedMultiTableBulkHqlJpaql(int paramInt1, int paramInt2, int paramInt3);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Oracle 11g is not yet fully supported; using Oracle 10g dialect", id=394)
  public abstract void unsupportedOracleVersion();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Usage of obsolete property: %s no longer supported, use: %s", id=395)
  public abstract void unsupportedProperty(Object paramObject1, Object paramObject2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Updating schema", id=396)
  public abstract void updatingSchema();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Using ASTQueryTranslatorFactory", id=397)
  public abstract void usingAstQueryTranslatorFactory();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Explicit segment value for id generator [%s.%s] suggested; using default [%s]", id=398)
  public abstract void usingDefaultIdGeneratorSegmentValue(String paramString1, String paramString2, String paramString3);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Using default transaction strategy (direct JDBC transactions)", id=399)
  public abstract void usingDefaultTransactionStrategy();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Using dialect: %s", id=400)
  public abstract void usingDialect(Dialect paramDialect);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="using driver [%s] at URL [%s]", id=401)
  public abstract void usingDriver(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Using Hibernate built-in connection pool (not for production use!)", id=402)
  public abstract void usingHibernateBuiltInConnectionPool();
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(value="Don't use old DTDs, read the Hibernate 3.x Migration Guide!", id=404)
  public abstract void usingOldDtd();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Using bytecode reflection optimizer", id=406)
  public abstract void usingReflectionOptimizer();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Using java.io streams to persist binary types", id=407)
  public abstract void usingStreams();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Using workaround for JVM bug in java.sql.Timestamp", id=408)
  public abstract void usingTimestampWorkaround();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Using %s which does not generate IETF RFC 4122 compliant UUID values; consider using %s instead", id=409)
  public abstract void usingUuidHexGenerator(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Hibernate Validator not found: ignoring", id=410)
  public abstract void validatorNotFound();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Hibernate Core {%s}", id=412)
  public abstract void version(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Warnings creating temp table : %s", id=413)
  public abstract void warningsCreatingTempTable(SQLWarning paramSQLWarning);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Property hibernate.search.autoregister_listeners is set to false. No attempt will be made to register Hibernate Search event listeners.", id=414)
  public abstract void willNotRegisterListeners();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Write locks via update not supported for non-versioned entities [%s]", id=416)
  public abstract void writeLocksNotSupported(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Writing generated schema to file: %s", id=417)
  public abstract void writingGeneratedSchemaToFile(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Adding override for %s: %s", id=418)
  public abstract void addingOverrideFor(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Resolved SqlTypeDescriptor is for a different SQL code. %s has sqlCode=%s; type override %s has sqlCode=%s", id=419)
  public abstract void resolvedSqlTypeDescriptorForDifferentSqlCode(String paramString1, String paramString2, String paramString3, String paramString4);
  
  @LogMessage(level=Logger.Level.DEBUG)
  @Message(value="Closing un-released batch", id=420)
  public abstract void closingUnreleasedBatch();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Disabling contextual LOB creation as %s is true", id=421)
  public abstract void disablingContextualLOBCreation(String paramString);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Disabling contextual LOB creation as connection was null", id=422)
  public abstract void disablingContextualLOBCreationSinceConnectionNull();
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Disabling contextual LOB creation as JDBC driver reported JDBC version [%s] less than 4", id=423)
  public abstract void disablingContextualLOBCreationSinceOldJdbcVersion(int paramInt);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Disabling contextual LOB creation as createClob() method threw error : %s", id=424)
  public abstract void disablingContextualLOBCreationSinceCreateClobFailed(Throwable paramThrowable);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Could not close session; swallowing exception[%s] as transaction completed", id=425)
  public abstract void unableToCloseSessionButSwallowingError(HibernateException paramHibernateException);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="You should set hibernate.transaction.manager_lookup_class if cache is enabled", id=426)
  public abstract void setManagerLookupClass();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Using deprecated %s strategy [%s], use newer %s strategy instead [%s]", id=427)
  public abstract void deprecatedTransactionManagerStrategy(String paramString1, String paramString2, String paramString3, String paramString4);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="Encountered legacy TransactionManagerLookup specified; convert to newer %s contract specified via %s setting", id=428)
  public abstract void legacyTransactionManagerStrategy(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Setting entity-identifier value binding where one already existed : %s.", id=429)
  public abstract void entityIdentifierValueBindingExists(String paramString);
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="The DerbyDialect dialect has been deprecated; use one of the version-specific dialects instead", id=430)
  public abstract void deprecatedDerbyDialect();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="Unable to determine H2 database version, certain features may not work", id=431)
  public abstract void undeterminedH2Version();
  
  @LogMessage(level=Logger.Level.WARN)
  @Message(value="There were not column names specified for index %s on table %s", id=432)
  public abstract void noColumnsSpecifiedForIndex(String paramString1, String paramString2);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="update timestamps cache puts: %s", id=433)
  public abstract void timestampCachePuts(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="update timestamps cache hits: %s", id=434)
  public abstract void timestampCacheHits(long paramLong);
  
  @LogMessage(level=Logger.Level.INFO)
  @Message(value="update timestamps cache misses: %s", id=435)
  public abstract void timestampCacheMisses(long paramLong);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.CoreMessageLogger
 * JD-Core Version:    0.7.0.1
 */