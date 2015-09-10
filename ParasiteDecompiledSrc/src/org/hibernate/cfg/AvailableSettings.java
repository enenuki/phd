package org.hibernate.cfg;

public abstract interface AvailableSettings
{
  public static final String SESSION_FACTORY_NAME = "hibernate.session_factory_name";
  public static final String CONNECTION_PROVIDER = "hibernate.connection.provider_class";
  public static final String DRIVER = "hibernate.connection.driver_class";
  public static final String URL = "hibernate.connection.url";
  public static final String USER = "hibernate.connection.username";
  public static final String PASS = "hibernate.connection.password";
  public static final String ISOLATION = "hibernate.connection.isolation";
  public static final String AUTOCOMMIT = "hibernate.connection.autocommit";
  public static final String POOL_SIZE = "hibernate.connection.pool_size";
  public static final String DATASOURCE = "hibernate.connection.datasource";
  public static final String CONNECTION_PREFIX = "hibernate.connection";
  public static final String JNDI_CLASS = "hibernate.jndi.class";
  public static final String JNDI_URL = "hibernate.jndi.url";
  public static final String JNDI_PREFIX = "hibernate.jndi";
  public static final String DIALECT = "hibernate.dialect";
  public static final String DIALECT_RESOLVERS = "hibernate.dialect_resolvers";
  public static final String DEFAULT_SCHEMA = "hibernate.default_schema";
  public static final String DEFAULT_CATALOG = "hibernate.default_catalog";
  public static final String SHOW_SQL = "hibernate.show_sql";
  public static final String FORMAT_SQL = "hibernate.format_sql";
  public static final String USE_SQL_COMMENTS = "hibernate.use_sql_comments";
  public static final String MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
  public static final String DEFAULT_BATCH_FETCH_SIZE = "hibernate.default_batch_fetch_size";
  public static final String USE_STREAMS_FOR_BINARY = "hibernate.jdbc.use_streams_for_binary";
  public static final String USE_SCROLLABLE_RESULTSET = "hibernate.jdbc.use_scrollable_resultset";
  public static final String USE_GET_GENERATED_KEYS = "hibernate.jdbc.use_get_generated_keys";
  public static final String STATEMENT_FETCH_SIZE = "hibernate.jdbc.fetch_size";
  public static final String STATEMENT_BATCH_SIZE = "hibernate.jdbc.batch_size";
  public static final String BATCH_STRATEGY = "hibernate.jdbc.factory_class";
  public static final String BATCH_VERSIONED_DATA = "hibernate.jdbc.batch_versioned_data";
  public static final String OUTPUT_STYLESHEET = "hibernate.xml.output_stylesheet";
  public static final String C3P0_MAX_SIZE = "hibernate.c3p0.max_size";
  public static final String C3P0_MIN_SIZE = "hibernate.c3p0.min_size";
  public static final String C3P0_TIMEOUT = "hibernate.c3p0.timeout";
  public static final String C3P0_MAX_STATEMENTS = "hibernate.c3p0.max_statements";
  public static final String C3P0_ACQUIRE_INCREMENT = "hibernate.c3p0.acquire_increment";
  public static final String C3P0_IDLE_TEST_PERIOD = "hibernate.c3p0.idle_test_period";
  /**
   * @deprecated
   */
  public static final String PROXOOL_PREFIX = "hibernate.proxool";
  public static final String PROXOOL_XML = "hibernate.proxool.xml";
  public static final String PROXOOL_PROPERTIES = "hibernate.proxool.properties";
  public static final String PROXOOL_EXISTING_POOL = "hibernate.proxool.existing_pool";
  public static final String PROXOOL_POOL_ALIAS = "hibernate.proxool.pool_alias";
  public static final String AUTO_CLOSE_SESSION = "hibernate.transaction.auto_close_session";
  public static final String FLUSH_BEFORE_COMPLETION = "hibernate.transaction.flush_before_completion";
  public static final String RELEASE_CONNECTIONS = "hibernate.connection.release_mode";
  public static final String CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";
  public static final String TRANSACTION_STRATEGY = "hibernate.transaction.factory_class";
  public static final String JTA_PLATFORM = "hibernate.transaction.jta.platform";
  @Deprecated
  public static final String TRANSACTION_MANAGER_STRATEGY = "hibernate.transaction.manager_lookup_class";
  @Deprecated
  public static final String USER_TRANSACTION = "jta.UserTransaction";
  public static final String CACHE_REGION_FACTORY = "hibernate.cache.region.factory_class";
  public static final String CACHE_PROVIDER_CONFIG = "hibernate.cache.provider_configuration_file_resource_path";
  public static final String CACHE_NAMESPACE = "hibernate.cache.jndi";
  public static final String USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
  public static final String QUERY_CACHE_FACTORY = "hibernate.cache.query_cache_factory";
  public static final String USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
  public static final String USE_MINIMAL_PUTS = "hibernate.cache.use_minimal_puts";
  public static final String CACHE_REGION_PREFIX = "hibernate.cache.region_prefix";
  public static final String USE_STRUCTURED_CACHE = "hibernate.cache.use_structured_entries";
  public static final String GENERATE_STATISTICS = "hibernate.generate_statistics";
  public static final String USE_IDENTIFIER_ROLLBACK = "hibernate.use_identifier_rollback";
  public static final String USE_REFLECTION_OPTIMIZER = "hibernate.bytecode.use_reflection_optimizer";
  public static final String QUERY_TRANSLATOR = "hibernate.query.factory_class";
  public static final String QUERY_SUBSTITUTIONS = "hibernate.query.substitutions";
  public static final String QUERY_STARTUP_CHECKING = "hibernate.query.startup_check";
  public static final String HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
  public static final String HBM2DDL_IMPORT_FILES = "hibernate.hbm2ddl.import_files";
  public static final String HBM2DDL_IMPORT_FILES_SQL_EXTRACTOR = "hibernate.hbm2ddl.import_files_sql_extractor";
  public static final String SQL_EXCEPTION_CONVERTER = "hibernate.jdbc.sql_exception_converter";
  public static final String WRAP_RESULT_SETS = "hibernate.jdbc.wrap_result_sets";
  public static final String ORDER_UPDATES = "hibernate.order_updates";
  public static final String ORDER_INSERTS = "hibernate.order_inserts";
  public static final String DEFAULT_ENTITY_MODE = "hibernate.default_entity_mode";
  public static final String JACC_CONTEXTID = "hibernate.jacc_context_id";
  public static final String GLOBALLY_QUOTED_IDENTIFIERS = "hibernate.globally_quoted_identifiers";
  public static final String CHECK_NULLABILITY = "hibernate.check_nullability";
  public static final String BYTECODE_PROVIDER = "hibernate.bytecode.provider";
  public static final String JPAQL_STRICT_COMPLIANCE = "hibernate.query.jpaql_strict_compliance";
  public static final String PREFER_POOLED_VALUES_LO = "hibernate.id.optimizer.pooled.prefer_lo";
  public static final String QUERY_PLAN_CACHE_MAX_STRONG_REFERENCES = "hibernate.query.plan_cache_max_strong_references";
  public static final String QUERY_PLAN_CACHE_MAX_SOFT_REFERENCES = "hibernate.query.plan_cache_max_soft_references";
  public static final String NON_CONTEXTUAL_LOB_CREATION = "hibernate.jdbc.lob.non_contextual_creation";
  public static final String MULTI_TENANT = "hibernate.multiTenancy";
  public static final String APP_CLASSLOADER = "hibernate.classLoader.application";
  public static final String RESOURCES_CLASSLOADER = "hibernate.classLoader.resources";
  public static final String HIBERNATE_CLASSLOADER = "hibernate.classLoader.hibernate";
  public static final String ENVIRONMENT_CLASSLOADER = "hibernate.classLoader.environment";
  public static final String C3P0_CONFIG_PREFIX = "hibernate.c3p0";
  public static final String PROXOOL_CONFIG_PREFIX = "hibernate.proxool";
  public static final String JMX_ENABLED = "hibernate.jmx.enabled";
  public static final String JMX_PLATFORM_SERVER = "hibernate.jmx.usePlatformServer";
  public static final String JMX_AGENT_ID = "hibernate.jmx.agentId";
  public static final String JMX_DOMAIN_NAME = "hibernate.jmx.defaultDomain";
  public static final String JMX_SF_NAME = "hibernate.jmx.sessionFactoryName";
  public static final String JMX_DEFAULT_OBJ_NAME_DOMAIN = "org.hibernate.core";
  public static final String JTA_CACHE_TM = "hibernate.jta.cacheTransactionManager";
  public static final String JTA_CACHE_UT = "hibernate.jta.cacheUserTransaction";
  public static final String DEFAULT_CACHE_CONCURRENCY_STRATEGY = "hibernate.cache.default_cache_concurrency_strategy";
  public static final String USE_NEW_ID_GENERATOR_MAPPINGS = "hibernate.id.new_generator_mappings";
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.AvailableSettings
 * JD-Core Version:    0.7.0.1
 */