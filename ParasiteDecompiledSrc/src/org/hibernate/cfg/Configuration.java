/*    1:     */ package org.hibernate.cfg;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileInputStream;
/*    5:     */ import java.io.FileNotFoundException;
/*    6:     */ import java.io.FileOutputStream;
/*    7:     */ import java.io.IOException;
/*    8:     */ import java.io.InputStream;
/*    9:     */ import java.io.ObjectInputStream;
/*   10:     */ import java.io.ObjectOutputStream;
/*   11:     */ import java.io.Serializable;
/*   12:     */ import java.io.StringReader;
/*   13:     */ import java.net.URL;
/*   14:     */ import java.util.ArrayList;
/*   15:     */ import java.util.Arrays;
/*   16:     */ import java.util.Collections;
/*   17:     */ import java.util.Enumeration;
/*   18:     */ import java.util.HashMap;
/*   19:     */ import java.util.HashSet;
/*   20:     */ import java.util.Iterator;
/*   21:     */ import java.util.LinkedHashMap;
/*   22:     */ import java.util.List;
/*   23:     */ import java.util.ListIterator;
/*   24:     */ import java.util.Map;
/*   25:     */ import java.util.Map.Entry;
/*   26:     */ import java.util.Properties;
/*   27:     */ import java.util.Set;
/*   28:     */ import java.util.StringTokenizer;
/*   29:     */ import java.util.TreeMap;
/*   30:     */ import java.util.jar.JarFile;
/*   31:     */ import java.util.zip.ZipEntry;
/*   32:     */ import javax.persistence.Embeddable;
/*   33:     */ import javax.persistence.Entity;
/*   34:     */ import javax.persistence.MapsId;
/*   35:     */ import org.dom4j.Attribute;
/*   36:     */ import org.dom4j.DocumentException;
/*   37:     */ import org.dom4j.Element;
/*   38:     */ import org.dom4j.io.DOMReader;
/*   39:     */ import org.dom4j.io.SAXReader;
/*   40:     */ import org.hibernate.AnnotationException;
/*   41:     */ import org.hibernate.DuplicateMappingException;
/*   42:     */ import org.hibernate.EmptyInterceptor;
/*   43:     */ import org.hibernate.HibernateException;
/*   44:     */ import org.hibernate.Interceptor;
/*   45:     */ import org.hibernate.InvalidMappingException;
/*   46:     */ import org.hibernate.MappingException;
/*   47:     */ import org.hibernate.MappingNotFoundException;
/*   48:     */ import org.hibernate.SessionFactory;
/*   49:     */ import org.hibernate.SessionFactoryObserver;
/*   50:     */ import org.hibernate.annotations.AnyMetaDef;
/*   51:     */ import org.hibernate.annotations.common.reflection.MetadataProvider;
/*   52:     */ import org.hibernate.annotations.common.reflection.MetadataProviderInjector;
/*   53:     */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*   54:     */ import org.hibernate.annotations.common.reflection.XClass;
/*   55:     */ import org.hibernate.annotations.common.reflection.XProperty;
/*   56:     */ import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;
/*   57:     */ import org.hibernate.cfg.annotations.reflection.JPAMetadataProvider;
/*   58:     */ import org.hibernate.cfg.annotations.reflection.XMLContext;
/*   59:     */ import org.hibernate.dialect.Dialect;
/*   60:     */ import org.hibernate.dialect.MySQLDialect;
/*   61:     */ import org.hibernate.dialect.function.SQLFunction;
/*   62:     */ import org.hibernate.engine.ResultSetMappingDefinition;
/*   63:     */ import org.hibernate.engine.spi.FilterDefinition;
/*   64:     */ import org.hibernate.engine.spi.Mapping;
/*   65:     */ import org.hibernate.engine.spi.NamedQueryDefinition;
/*   66:     */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*   67:     */ import org.hibernate.id.IdentifierGenerator;
/*   68:     */ import org.hibernate.id.IdentifierGeneratorAggregator;
/*   69:     */ import org.hibernate.id.PersistentIdentifierGenerator;
/*   70:     */ import org.hibernate.id.factory.IdentifierGeneratorFactory;
/*   71:     */ import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;
/*   72:     */ import org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory;
/*   73:     */ import org.hibernate.internal.CoreMessageLogger;
/*   74:     */ import org.hibernate.internal.SessionFactoryImpl;
/*   75:     */ import org.hibernate.internal.util.ConfigHelper;
/*   76:     */ import org.hibernate.internal.util.ReflectHelper;
/*   77:     */ import org.hibernate.internal.util.SerializationHelper;
/*   78:     */ import org.hibernate.internal.util.StringHelper;
/*   79:     */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   80:     */ import org.hibernate.internal.util.collections.CollectionHelper;
/*   81:     */ import org.hibernate.internal.util.collections.JoinedIterator;
/*   82:     */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*   83:     */ import org.hibernate.internal.util.xml.MappingReader;
/*   84:     */ import org.hibernate.internal.util.xml.Origin;
/*   85:     */ import org.hibernate.internal.util.xml.OriginImpl;
/*   86:     */ import org.hibernate.internal.util.xml.XMLHelper;
/*   87:     */ import org.hibernate.internal.util.xml.XmlDocument;
/*   88:     */ import org.hibernate.internal.util.xml.XmlDocumentImpl;
/*   89:     */ import org.hibernate.mapping.AuxiliaryDatabaseObject;
/*   90:     */ import org.hibernate.mapping.Column;
/*   91:     */ import org.hibernate.mapping.DenormalizedTable;
/*   92:     */ import org.hibernate.mapping.FetchProfile;
/*   93:     */ import org.hibernate.mapping.ForeignKey;
/*   94:     */ import org.hibernate.mapping.IdGenerator;
/*   95:     */ import org.hibernate.mapping.IdentifierCollection;
/*   96:     */ import org.hibernate.mapping.Index;
/*   97:     */ import org.hibernate.mapping.Join;
/*   98:     */ import org.hibernate.mapping.KeyValue;
/*   99:     */ import org.hibernate.mapping.MetadataSource;
/*  100:     */ import org.hibernate.mapping.PersistentClass;
/*  101:     */ import org.hibernate.mapping.Property;
/*  102:     */ import org.hibernate.mapping.RootClass;
/*  103:     */ import org.hibernate.mapping.SimpleValue;
/*  104:     */ import org.hibernate.mapping.Table;
/*  105:     */ import org.hibernate.mapping.TypeDef;
/*  106:     */ import org.hibernate.mapping.UniqueKey;
/*  107:     */ import org.hibernate.mapping.Value;
/*  108:     */ import org.hibernate.proxy.EntityNotFoundDelegate;
/*  109:     */ import org.hibernate.secure.internal.JACCConfiguration;
/*  110:     */ import org.hibernate.service.ServiceRegistry;
/*  111:     */ import org.hibernate.service.ServiceRegistryBuilder;
/*  112:     */ import org.hibernate.service.internal.StandardServiceRegistryImpl;
/*  113:     */ import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
/*  114:     */ import org.hibernate.tool.hbm2ddl.IndexMetadata;
/*  115:     */ import org.hibernate.tool.hbm2ddl.TableMetadata;
/*  116:     */ import org.hibernate.tuple.entity.EntityTuplizerFactory;
/*  117:     */ import org.hibernate.type.BasicType;
/*  118:     */ import org.hibernate.type.SerializationException;
/*  119:     */ import org.hibernate.type.Type;
/*  120:     */ import org.hibernate.type.TypeResolver;
/*  121:     */ import org.hibernate.usertype.CompositeUserType;
/*  122:     */ import org.hibernate.usertype.UserType;
/*  123:     */ import org.jboss.logging.Logger;
/*  124:     */ import org.xml.sax.EntityResolver;
/*  125:     */ import org.xml.sax.InputSource;
/*  126:     */ 
/*  127:     */ public class Configuration
/*  128:     */   implements Serializable
/*  129:     */ {
/*  130: 173 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Configuration.class.getName());
/*  131:     */   public static final String DEFAULT_CACHE_CONCURRENCY_STRATEGY = "hibernate.cache.default_cache_concurrency_strategy";
/*  132:     */   public static final String USE_NEW_ID_GENERATOR_MAPPINGS = "hibernate.id.new_generator_mappings";
/*  133:     */   public static final String ARTEFACT_PROCESSING_ORDER = "hibernate.mapping.precedence";
/*  134:     */   private static final String SEARCH_STARTUP_CLASS = "org.hibernate.search.event.EventListenerRegister";
/*  135:     */   private static final String SEARCH_STARTUP_METHOD = "enableHibernateSearch";
/*  136:     */   protected MetadataSourceQueue metadataSourceQueue;
/*  137:     */   private transient ReflectionManager reflectionManager;
/*  138:     */   protected Map<String, PersistentClass> classes;
/*  139:     */   protected Map<String, String> imports;
/*  140:     */   protected Map<String, org.hibernate.mapping.Collection> collections;
/*  141:     */   protected Map<String, Table> tables;
/*  142:     */   protected List<AuxiliaryDatabaseObject> auxiliaryDatabaseObjects;
/*  143:     */   protected Map<String, NamedQueryDefinition> namedQueries;
/*  144:     */   protected Map<String, NamedSQLQueryDefinition> namedSqlQueries;
/*  145:     */   protected Map<String, ResultSetMappingDefinition> sqlResultSetMappings;
/*  146:     */   protected Map<String, TypeDef> typeDefs;
/*  147:     */   protected Map<String, FilterDefinition> filterDefinitions;
/*  148:     */   protected Map<String, FetchProfile> fetchProfiles;
/*  149:     */   protected Map tableNameBinding;
/*  150:     */   protected Map columnNameBindingPerTable;
/*  151:     */   protected List<SecondPass> secondPasses;
/*  152:     */   protected List<Mappings.PropertyReference> propertyReferences;
/*  153:     */   protected Map<ExtendsQueueEntry, ?> extendsQueue;
/*  154:     */   protected Map<String, SQLFunction> sqlFunctions;
/*  155: 216 */   private TypeResolver typeResolver = new TypeResolver();
/*  156:     */   private EntityTuplizerFactory entityTuplizerFactory;
/*  157:     */   private Interceptor interceptor;
/*  158:     */   private Properties properties;
/*  159:     */   private EntityResolver entityResolver;
/*  160:     */   private EntityNotFoundDelegate entityNotFoundDelegate;
/*  161:     */   protected transient XMLHelper xmlHelper;
/*  162:     */   protected NamingStrategy namingStrategy;
/*  163:     */   private SessionFactoryObserver sessionFactoryObserver;
/*  164:     */   protected final SettingsFactory settingsFactory;
/*  165: 232 */   private transient Mapping mapping = buildMapping();
/*  166:     */   private MutableIdentifierGeneratorFactory identifierGeneratorFactory;
/*  167:     */   private Map<Class<?>, org.hibernate.mapping.MappedSuperclass> mappedSuperClasses;
/*  168:     */   private Map<String, IdGenerator> namedGenerators;
/*  169:     */   private Map<String, Map<String, Join>> joins;
/*  170:     */   private Map<String, AnnotatedClassType> classTypes;
/*  171:     */   private Set<String> defaultNamedQueryNames;
/*  172:     */   private Set<String> defaultNamedNativeQueryNames;
/*  173:     */   private Set<String> defaultSqlResultSetMappingNames;
/*  174:     */   private Set<String> defaultNamedGenerators;
/*  175:     */   private Map<String, Properties> generatorTables;
/*  176:     */   private Map<Table, List<UniqueConstraintHolder>> uniqueConstraintHoldersByTable;
/*  177:     */   private Map<String, String> mappedByResolver;
/*  178:     */   private Map<String, String> propertyRefResolver;
/*  179:     */   private Map<String, AnyMetaDef> anyMetaDefs;
/*  180:     */   private List<CacheHolder> caches;
/*  181: 251 */   private boolean inSecondPass = false;
/*  182: 252 */   private boolean isDefaultProcessed = false;
/*  183:     */   private boolean isValidatorNotPresentLogged;
/*  184:     */   private Map<XClass, Map<String, PropertyData>> propertiesAnnotatedWithMapsId;
/*  185:     */   private Map<XClass, Map<String, PropertyData>> propertiesAnnotatedWithIdAndToOne;
/*  186:     */   private boolean specjProprietarySyntaxEnabled;
/*  187:     */   
/*  188:     */   protected Configuration(SettingsFactory settingsFactory)
/*  189:     */   {
/*  190: 260 */     this.settingsFactory = settingsFactory;
/*  191: 261 */     reset();
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Configuration()
/*  195:     */   {
/*  196: 265 */     this(new SettingsFactory());
/*  197:     */   }
/*  198:     */   
/*  199:     */   protected void reset()
/*  200:     */   {
/*  201: 269 */     this.metadataSourceQueue = new MetadataSourceQueue();
/*  202: 270 */     createReflectionManager();
/*  203:     */     
/*  204: 272 */     this.classes = new HashMap();
/*  205: 273 */     this.imports = new HashMap();
/*  206: 274 */     this.collections = new HashMap();
/*  207: 275 */     this.tables = new TreeMap();
/*  208:     */     
/*  209: 277 */     this.namedQueries = new HashMap();
/*  210: 278 */     this.namedSqlQueries = new HashMap();
/*  211: 279 */     this.sqlResultSetMappings = new HashMap();
/*  212:     */     
/*  213: 281 */     this.typeDefs = new HashMap();
/*  214: 282 */     this.filterDefinitions = new HashMap();
/*  215: 283 */     this.fetchProfiles = new HashMap();
/*  216: 284 */     this.auxiliaryDatabaseObjects = new ArrayList();
/*  217:     */     
/*  218: 286 */     this.tableNameBinding = new HashMap();
/*  219: 287 */     this.columnNameBindingPerTable = new HashMap();
/*  220:     */     
/*  221: 289 */     this.secondPasses = new ArrayList();
/*  222: 290 */     this.propertyReferences = new ArrayList();
/*  223: 291 */     this.extendsQueue = new HashMap();
/*  224:     */     
/*  225: 293 */     this.xmlHelper = new XMLHelper();
/*  226: 294 */     this.interceptor = EmptyInterceptor.INSTANCE;
/*  227: 295 */     this.properties = Environment.getProperties();
/*  228: 296 */     this.entityResolver = XMLHelper.DEFAULT_DTD_RESOLVER;
/*  229:     */     
/*  230: 298 */     this.sqlFunctions = new HashMap();
/*  231:     */     
/*  232: 300 */     this.entityTuplizerFactory = new EntityTuplizerFactory();
/*  233:     */     
/*  234:     */ 
/*  235: 303 */     this.identifierGeneratorFactory = new DefaultIdentifierGeneratorFactory();
/*  236:     */     
/*  237: 305 */     this.mappedSuperClasses = new HashMap();
/*  238:     */     
/*  239: 307 */     this.metadataSourcePrecedence = Collections.emptyList();
/*  240:     */     
/*  241: 309 */     this.namedGenerators = new HashMap();
/*  242: 310 */     this.joins = new HashMap();
/*  243: 311 */     this.classTypes = new HashMap();
/*  244: 312 */     this.generatorTables = new HashMap();
/*  245: 313 */     this.defaultNamedQueryNames = new HashSet();
/*  246: 314 */     this.defaultNamedNativeQueryNames = new HashSet();
/*  247: 315 */     this.defaultSqlResultSetMappingNames = new HashSet();
/*  248: 316 */     this.defaultNamedGenerators = new HashSet();
/*  249: 317 */     this.uniqueConstraintHoldersByTable = new HashMap();
/*  250: 318 */     this.mappedByResolver = new HashMap();
/*  251: 319 */     this.propertyRefResolver = new HashMap();
/*  252: 320 */     this.caches = new ArrayList();
/*  253: 321 */     this.namingStrategy = EJB3NamingStrategy.INSTANCE;
/*  254: 322 */     setEntityResolver(new EJB3DTDEntityResolver());
/*  255: 323 */     this.anyMetaDefs = new HashMap();
/*  256: 324 */     this.propertiesAnnotatedWithMapsId = new HashMap();
/*  257: 325 */     this.propertiesAnnotatedWithIdAndToOne = new HashMap();
/*  258: 326 */     this.specjProprietarySyntaxEnabled = (System.getProperty("hibernate.enable_specj_proprietary_syntax") != null);
/*  259:     */   }
/*  260:     */   
/*  261:     */   public EntityTuplizerFactory getEntityTuplizerFactory()
/*  262:     */   {
/*  263: 330 */     return this.entityTuplizerFactory;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public ReflectionManager getReflectionManager()
/*  267:     */   {
/*  268: 334 */     return this.reflectionManager;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public Iterator<PersistentClass> getClassMappings()
/*  272:     */   {
/*  273: 347 */     return this.classes.values().iterator();
/*  274:     */   }
/*  275:     */   
/*  276:     */   public Iterator getCollectionMappings()
/*  277:     */   {
/*  278: 356 */     return this.collections.values().iterator();
/*  279:     */   }
/*  280:     */   
/*  281:     */   public Iterator<Table> getTableMappings()
/*  282:     */   {
/*  283: 365 */     return this.tables.values().iterator();
/*  284:     */   }
/*  285:     */   
/*  286:     */   public Iterator<org.hibernate.mapping.MappedSuperclass> getMappedSuperclassMappings()
/*  287:     */   {
/*  288: 375 */     return this.mappedSuperClasses.values().iterator();
/*  289:     */   }
/*  290:     */   
/*  291:     */   public PersistentClass getClassMapping(String entityName)
/*  292:     */   {
/*  293: 385 */     return (PersistentClass)this.classes.get(entityName);
/*  294:     */   }
/*  295:     */   
/*  296:     */   public org.hibernate.mapping.Collection getCollectionMapping(String role)
/*  297:     */   {
/*  298: 395 */     return (org.hibernate.mapping.Collection)this.collections.get(role);
/*  299:     */   }
/*  300:     */   
/*  301:     */   public void setEntityResolver(EntityResolver entityResolver)
/*  302:     */   {
/*  303: 406 */     this.entityResolver = entityResolver;
/*  304:     */   }
/*  305:     */   
/*  306:     */   public EntityResolver getEntityResolver()
/*  307:     */   {
/*  308: 410 */     return this.entityResolver;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public EntityNotFoundDelegate getEntityNotFoundDelegate()
/*  312:     */   {
/*  313: 420 */     return this.entityNotFoundDelegate;
/*  314:     */   }
/*  315:     */   
/*  316:     */   public void setEntityNotFoundDelegate(EntityNotFoundDelegate entityNotFoundDelegate)
/*  317:     */   {
/*  318: 431 */     this.entityNotFoundDelegate = entityNotFoundDelegate;
/*  319:     */   }
/*  320:     */   
/*  321:     */   public Configuration addFile(String xmlFile)
/*  322:     */     throws MappingException
/*  323:     */   {
/*  324: 444 */     return addFile(new File(xmlFile));
/*  325:     */   }
/*  326:     */   
/*  327:     */   public Configuration addFile(File xmlFile)
/*  328:     */     throws MappingException
/*  329:     */   {
/*  330: 456 */     LOG.readingMappingsFromFile(xmlFile.getPath());
/*  331: 457 */     String name = xmlFile.getAbsolutePath();
/*  332:     */     InputSource inputSource;
/*  333:     */     try
/*  334:     */     {
/*  335: 460 */       inputSource = new InputSource(new FileInputStream(xmlFile));
/*  336:     */     }
/*  337:     */     catch (FileNotFoundException e)
/*  338:     */     {
/*  339: 463 */       throw new MappingNotFoundException("file", xmlFile.toString());
/*  340:     */     }
/*  341: 465 */     add(inputSource, "file", name);
/*  342: 466 */     return this;
/*  343:     */   }
/*  344:     */   
/*  345:     */   private XmlDocument add(InputSource inputSource, String originType, String originName)
/*  346:     */   {
/*  347: 470 */     return add(inputSource, new OriginImpl(originType, originName));
/*  348:     */   }
/*  349:     */   
/*  350:     */   private XmlDocument add(InputSource inputSource, Origin origin)
/*  351:     */   {
/*  352: 474 */     XmlDocument metadataXml = MappingReader.INSTANCE.readMappingDocument(this.entityResolver, inputSource, origin);
/*  353: 475 */     add(metadataXml);
/*  354: 476 */     return metadataXml;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public void add(XmlDocument metadataXml)
/*  358:     */   {
/*  359: 480 */     if ((this.inSecondPass) || (!isOrmXml(metadataXml)))
/*  360:     */     {
/*  361: 481 */       this.metadataSourceQueue.add(metadataXml);
/*  362:     */     }
/*  363:     */     else
/*  364:     */     {
/*  365: 484 */       MetadataProvider metadataProvider = ((MetadataProviderInjector)this.reflectionManager).getMetadataProvider();
/*  366: 485 */       JPAMetadataProvider jpaMetadataProvider = (JPAMetadataProvider)metadataProvider;
/*  367: 486 */       List<String> classNames = jpaMetadataProvider.getXMLContext().addDocument(metadataXml.getDocumentTree());
/*  368: 487 */       for (String className : classNames) {
/*  369:     */         try
/*  370:     */         {
/*  371: 489 */           this.metadataSourceQueue.add(this.reflectionManager.classForName(className, getClass()));
/*  372:     */         }
/*  373:     */         catch (ClassNotFoundException e)
/*  374:     */         {
/*  375: 492 */           throw new AnnotationException("Unable to load class defined in XML: " + className, e);
/*  376:     */         }
/*  377:     */       }
/*  378:     */     }
/*  379:     */   }
/*  380:     */   
/*  381:     */   private static boolean isOrmXml(XmlDocument xmlDocument)
/*  382:     */   {
/*  383: 499 */     return "entity-mappings".equals(xmlDocument.getDocumentTree().getRootElement().getName());
/*  384:     */   }
/*  385:     */   
/*  386:     */   public Configuration addCacheableFile(File xmlFile)
/*  387:     */     throws MappingException
/*  388:     */   {
/*  389: 519 */     File cachedFile = determineCachedDomFile(xmlFile);
/*  390:     */     try
/*  391:     */     {
/*  392: 522 */       return addCacheableFileStrictly(xmlFile);
/*  393:     */     }
/*  394:     */     catch (SerializationException e)
/*  395:     */     {
/*  396: 525 */       LOG.unableToDeserializeCache(cachedFile.getPath(), e);
/*  397:     */     }
/*  398:     */     catch (FileNotFoundException e)
/*  399:     */     {
/*  400: 528 */       LOG.cachedFileNotFound(cachedFile.getPath(), e);
/*  401:     */     }
/*  402: 531 */     String name = xmlFile.getAbsolutePath();
/*  403:     */     InputSource inputSource;
/*  404:     */     try
/*  405:     */     {
/*  406: 534 */       inputSource = new InputSource(new FileInputStream(xmlFile));
/*  407:     */     }
/*  408:     */     catch (FileNotFoundException e)
/*  409:     */     {
/*  410: 537 */       throw new MappingNotFoundException("file", xmlFile.toString());
/*  411:     */     }
/*  412: 540 */     LOG.readingMappingsFromFile(xmlFile.getPath());
/*  413: 541 */     XmlDocument metadataXml = add(inputSource, "file", name);
/*  414:     */     try
/*  415:     */     {
/*  416: 544 */       LOG.debugf("Writing cache file for: %s to: %s", xmlFile, cachedFile);
/*  417: 545 */       SerializationHelper.serialize((Serializable)metadataXml.getDocumentTree(), new FileOutputStream(cachedFile));
/*  418:     */     }
/*  419:     */     catch (Exception e)
/*  420:     */     {
/*  421: 548 */       LOG.unableToWriteCachedFile(cachedFile.getPath(), e.getMessage());
/*  422:     */     }
/*  423: 551 */     return this;
/*  424:     */   }
/*  425:     */   
/*  426:     */   private File determineCachedDomFile(File xmlFile)
/*  427:     */   {
/*  428: 555 */     return new File(xmlFile.getAbsolutePath() + ".bin");
/*  429:     */   }
/*  430:     */   
/*  431:     */   public Configuration addCacheableFileStrictly(File xmlFile)
/*  432:     */     throws SerializationException, FileNotFoundException
/*  433:     */   {
/*  434: 572 */     File cachedFile = determineCachedDomFile(xmlFile);
/*  435:     */     
/*  436: 574 */     boolean useCachedFile = (xmlFile.exists()) && (cachedFile.exists()) && (xmlFile.lastModified() < cachedFile.lastModified());
/*  437: 578 */     if (!useCachedFile) {
/*  438: 579 */       throw new FileNotFoundException("Cached file could not be found or could not be used");
/*  439:     */     }
/*  440: 582 */     LOG.readingCachedMappings(cachedFile);
/*  441: 583 */     org.dom4j.Document document = (org.dom4j.Document)SerializationHelper.deserialize(new FileInputStream(cachedFile));
/*  442: 584 */     add(new XmlDocumentImpl(document, "file", xmlFile.getAbsolutePath()));
/*  443: 585 */     return this;
/*  444:     */   }
/*  445:     */   
/*  446:     */   public Configuration addCacheableFile(String xmlFile)
/*  447:     */     throws MappingException
/*  448:     */   {
/*  449: 599 */     return addCacheableFile(new File(xmlFile));
/*  450:     */   }
/*  451:     */   
/*  452:     */   public Configuration addXML(String xml)
/*  453:     */     throws MappingException
/*  454:     */   {
/*  455: 612 */     LOG.debugf("Mapping XML:\n%s", xml);
/*  456: 613 */     InputSource inputSource = new InputSource(new StringReader(xml));
/*  457: 614 */     add(inputSource, "string", "XML String");
/*  458: 615 */     return this;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public Configuration addURL(URL url)
/*  462:     */     throws MappingException
/*  463:     */   {
/*  464: 627 */     String urlExternalForm = url.toExternalForm();
/*  465:     */     
/*  466: 629 */     LOG.debugf("Reading mapping document from URL : %s", urlExternalForm);
/*  467:     */     try
/*  468:     */     {
/*  469: 632 */       add(url.openStream(), "URL", urlExternalForm);
/*  470:     */     }
/*  471:     */     catch (IOException e)
/*  472:     */     {
/*  473: 635 */       throw new InvalidMappingException("Unable to open url stream [" + urlExternalForm + "]", "URL", urlExternalForm, e);
/*  474:     */     }
/*  475: 637 */     return this;
/*  476:     */   }
/*  477:     */   
/*  478:     */   private XmlDocument add(InputStream inputStream, String type, String name)
/*  479:     */   {
/*  480: 641 */     InputSource inputSource = new InputSource(inputStream);
/*  481:     */     try
/*  482:     */     {
/*  483: 643 */       return add(inputSource, type, name);
/*  484:     */     }
/*  485:     */     finally
/*  486:     */     {
/*  487:     */       try
/*  488:     */       {
/*  489: 647 */         inputStream.close();
/*  490:     */       }
/*  491:     */       catch (IOException ignore)
/*  492:     */       {
/*  493: 650 */         LOG.trace("Was unable to close input stream");
/*  494:     */       }
/*  495:     */     }
/*  496:     */   }
/*  497:     */   
/*  498:     */   public Configuration addDocument(org.w3c.dom.Document doc)
/*  499:     */     throws MappingException
/*  500:     */   {
/*  501: 664 */     LOG.debugf("Mapping Document:\n%s", doc);
/*  502:     */     
/*  503: 666 */     org.dom4j.Document document = this.xmlHelper.createDOMReader().read(doc);
/*  504: 667 */     add(new XmlDocumentImpl(document, "unknown", null));
/*  505:     */     
/*  506: 669 */     return this;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public Configuration addInputStream(InputStream xmlInputStream)
/*  510:     */     throws MappingException
/*  511:     */   {
/*  512: 681 */     add(xmlInputStream, "input stream", null);
/*  513: 682 */     return this;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public Configuration addResource(String resourceName, ClassLoader classLoader)
/*  517:     */     throws MappingException
/*  518:     */   {
/*  519: 695 */     LOG.readingMappingsFromResource(resourceName);
/*  520: 696 */     InputStream resourceInputStream = classLoader.getResourceAsStream(resourceName);
/*  521: 697 */     if (resourceInputStream == null) {
/*  522: 698 */       throw new MappingNotFoundException("resource", resourceName);
/*  523:     */     }
/*  524: 700 */     add(resourceInputStream, "resource", resourceName);
/*  525: 701 */     return this;
/*  526:     */   }
/*  527:     */   
/*  528:     */   public Configuration addResource(String resourceName)
/*  529:     */     throws MappingException
/*  530:     */   {
/*  531: 714 */     LOG.readingMappingsFromResource(resourceName);
/*  532: 715 */     ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/*  533: 716 */     InputStream resourceInputStream = null;
/*  534: 717 */     if (contextClassLoader != null) {
/*  535: 718 */       resourceInputStream = contextClassLoader.getResourceAsStream(resourceName);
/*  536:     */     }
/*  537: 720 */     if (resourceInputStream == null) {
/*  538: 721 */       resourceInputStream = Environment.class.getClassLoader().getResourceAsStream(resourceName);
/*  539:     */     }
/*  540: 723 */     if (resourceInputStream == null) {
/*  541: 724 */       throw new MappingNotFoundException("resource", resourceName);
/*  542:     */     }
/*  543: 726 */     add(resourceInputStream, "resource", resourceName);
/*  544: 727 */     return this;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public Configuration addClass(Class persistentClass)
/*  548:     */     throws MappingException
/*  549:     */   {
/*  550: 741 */     String mappingResourceName = persistentClass.getName().replace('.', '/') + ".hbm.xml";
/*  551: 742 */     LOG.readingMappingsFromResource(mappingResourceName);
/*  552: 743 */     return addResource(mappingResourceName, persistentClass.getClassLoader());
/*  553:     */   }
/*  554:     */   
/*  555:     */   public Configuration addAnnotatedClass(Class annotatedClass)
/*  556:     */   {
/*  557: 755 */     XClass xClass = this.reflectionManager.toXClass(annotatedClass);
/*  558: 756 */     this.metadataSourceQueue.add(xClass);
/*  559: 757 */     return this;
/*  560:     */   }
/*  561:     */   
/*  562:     */   public Configuration addPackage(String packageName)
/*  563:     */     throws MappingException
/*  564:     */   {
/*  565: 770 */     LOG.debugf("Mapping Package %s", packageName);
/*  566:     */     try
/*  567:     */     {
/*  568: 772 */       AnnotationBinder.bindPackage(packageName, createMappings());
/*  569: 773 */       return this;
/*  570:     */     }
/*  571:     */     catch (MappingException me)
/*  572:     */     {
/*  573: 776 */       LOG.unableToParseMetadata(packageName);
/*  574: 777 */       throw me;
/*  575:     */     }
/*  576:     */   }
/*  577:     */   
/*  578:     */   public Configuration addJar(File jar)
/*  579:     */     throws MappingException
/*  580:     */   {
/*  581: 792 */     LOG.searchingForMappingDocuments(jar.getName());
/*  582: 793 */     JarFile jarFile = null;
/*  583:     */     try
/*  584:     */     {
/*  585:     */       try
/*  586:     */       {
/*  587: 796 */         jarFile = new JarFile(jar);
/*  588:     */       }
/*  589:     */       catch (IOException ioe)
/*  590:     */       {
/*  591: 799 */         throw new InvalidMappingException("Could not read mapping documents from jar: " + jar.getName(), "jar", jar.getName(), ioe);
/*  592:     */       }
/*  593: 804 */       Enumeration jarEntries = jarFile.entries();
/*  594: 805 */       while (jarEntries.hasMoreElements())
/*  595:     */       {
/*  596: 806 */         ZipEntry ze = (ZipEntry)jarEntries.nextElement();
/*  597: 807 */         if (ze.getName().endsWith(".hbm.xml"))
/*  598:     */         {
/*  599: 808 */           LOG.foundMappingDocument(ze.getName());
/*  600:     */           try
/*  601:     */           {
/*  602: 810 */             addInputStream(jarFile.getInputStream(ze));
/*  603:     */           }
/*  604:     */           catch (Exception e)
/*  605:     */           {
/*  606: 813 */             throw new InvalidMappingException("Could not read mapping documents from jar: " + jar.getName(), "jar", jar.getName(), e);
/*  607:     */           }
/*  608:     */         }
/*  609:     */       }
/*  610: 834 */       return this;
/*  611:     */     }
/*  612:     */     finally
/*  613:     */     {
/*  614:     */       try
/*  615:     */       {
/*  616: 825 */         if (jarFile != null) {
/*  617: 826 */           jarFile.close();
/*  618:     */         }
/*  619:     */       }
/*  620:     */       catch (IOException ioe)
/*  621:     */       {
/*  622: 830 */         LOG.unableToCloseJar(ioe.getMessage());
/*  623:     */       }
/*  624:     */     }
/*  625:     */   }
/*  626:     */   
/*  627:     */   public Configuration addDirectory(File dir)
/*  628:     */     throws MappingException
/*  629:     */   {
/*  630: 848 */     File[] files = dir.listFiles();
/*  631: 849 */     for (File file : files) {
/*  632: 850 */       if (file.isDirectory()) {
/*  633: 851 */         addDirectory(file);
/*  634: 853 */       } else if (file.getName().endsWith(".hbm.xml")) {
/*  635: 854 */         addFile(file);
/*  636:     */       }
/*  637:     */     }
/*  638: 857 */     return this;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public Mappings createMappings()
/*  642:     */   {
/*  643: 866 */     return new MappingsImpl();
/*  644:     */   }
/*  645:     */   
/*  646:     */   private Iterator<IdentifierGenerator> iterateGenerators(Dialect dialect)
/*  647:     */     throws MappingException
/*  648:     */   {
/*  649: 873 */     TreeMap generators = new TreeMap();
/*  650: 874 */     String defaultCatalog = this.properties.getProperty("hibernate.default_catalog");
/*  651: 875 */     String defaultSchema = this.properties.getProperty("hibernate.default_schema");
/*  652: 877 */     for (PersistentClass pc : this.classes.values()) {
/*  653: 878 */       if (!pc.isInherited())
/*  654:     */       {
/*  655: 879 */         IdentifierGenerator ig = pc.getIdentifier().createIdentifierGenerator(getIdentifierGeneratorFactory(), dialect, defaultCatalog, defaultSchema, (RootClass)pc);
/*  656: 887 */         if ((ig instanceof PersistentIdentifierGenerator)) {
/*  657: 888 */           generators.put(((PersistentIdentifierGenerator)ig).generatorKey(), ig);
/*  658: 890 */         } else if ((ig instanceof IdentifierGeneratorAggregator)) {
/*  659: 891 */           ((IdentifierGeneratorAggregator)ig).registerPersistentGenerators(generators);
/*  660:     */         }
/*  661:     */       }
/*  662:     */     }
/*  663: 896 */     for (org.hibernate.mapping.Collection collection : this.collections.values()) {
/*  664: 897 */       if (collection.isIdentified())
/*  665:     */       {
/*  666: 898 */         IdentifierGenerator ig = ((IdentifierCollection)collection).getIdentifier().createIdentifierGenerator(getIdentifierGeneratorFactory(), dialect, defaultCatalog, defaultSchema, null);
/*  667: 906 */         if ((ig instanceof PersistentIdentifierGenerator)) {
/*  668: 907 */           generators.put(((PersistentIdentifierGenerator)ig).generatorKey(), ig);
/*  669:     */         }
/*  670:     */       }
/*  671:     */     }
/*  672: 912 */     return generators.values().iterator();
/*  673:     */   }
/*  674:     */   
/*  675:     */   public String[] generateDropSchemaScript(Dialect dialect)
/*  676:     */     throws HibernateException
/*  677:     */   {
/*  678: 927 */     secondPassCompile();
/*  679:     */     
/*  680: 929 */     String defaultCatalog = this.properties.getProperty("hibernate.default_catalog");
/*  681: 930 */     String defaultSchema = this.properties.getProperty("hibernate.default_schema");
/*  682:     */     
/*  683: 932 */     ArrayList<String> script = new ArrayList(50);
/*  684:     */     
/*  685:     */ 
/*  686:     */ 
/*  687: 936 */     ListIterator itr = this.auxiliaryDatabaseObjects.listIterator(this.auxiliaryDatabaseObjects.size());
/*  688: 937 */     while (itr.hasPrevious())
/*  689:     */     {
/*  690: 938 */       AuxiliaryDatabaseObject object = (AuxiliaryDatabaseObject)itr.previous();
/*  691: 939 */       if (object.appliesToDialect(dialect)) {
/*  692: 940 */         script.add(object.sqlDropString(dialect, defaultCatalog, defaultSchema));
/*  693:     */       }
/*  694:     */     }
/*  695: 945 */     if (dialect.dropConstraints())
/*  696:     */     {
/*  697: 946 */       Iterator itr = getTableMappings();
/*  698: 947 */       while (itr.hasNext())
/*  699:     */       {
/*  700: 948 */         Table table = (Table)itr.next();
/*  701: 949 */         if (table.isPhysicalTable())
/*  702:     */         {
/*  703: 950 */           Iterator subItr = table.getForeignKeyIterator();
/*  704: 951 */           while (subItr.hasNext())
/*  705:     */           {
/*  706: 952 */             ForeignKey fk = (ForeignKey)subItr.next();
/*  707: 953 */             if (fk.isPhysicalConstraint()) {
/*  708: 954 */               script.add(fk.sqlDropString(dialect, defaultCatalog, defaultSchema));
/*  709:     */             }
/*  710:     */           }
/*  711:     */         }
/*  712:     */       }
/*  713:     */     }
/*  714: 968 */     Iterator itr = getTableMappings();
/*  715: 969 */     while (itr.hasNext())
/*  716:     */     {
/*  717: 971 */       Table table = (Table)itr.next();
/*  718: 972 */       if (table.isPhysicalTable()) {
/*  719: 982 */         script.add(table.sqlDropString(dialect, defaultCatalog, defaultSchema));
/*  720:     */       }
/*  721:     */     }
/*  722: 994 */     itr = iterateGenerators(dialect);
/*  723: 995 */     while (itr.hasNext())
/*  724:     */     {
/*  725: 996 */       String[] lines = ((PersistentIdentifierGenerator)itr.next()).sqlDropStrings(dialect);
/*  726: 997 */       script.addAll(Arrays.asList(lines));
/*  727:     */     }
/*  728:1000 */     return ArrayHelper.toStringArray(script);
/*  729:     */   }
/*  730:     */   
/*  731:     */   public String[] generateSchemaCreationScript(Dialect dialect)
/*  732:     */     throws HibernateException
/*  733:     */   {
/*  734:1014 */     secondPassCompile();
/*  735:     */     
/*  736:1016 */     ArrayList<String> script = new ArrayList(50);
/*  737:1017 */     String defaultCatalog = this.properties.getProperty("hibernate.default_catalog");
/*  738:1018 */     String defaultSchema = this.properties.getProperty("hibernate.default_schema");
/*  739:     */     
/*  740:1020 */     Iterator iter = getTableMappings();
/*  741:1021 */     while (iter.hasNext())
/*  742:     */     {
/*  743:1022 */       Table table = (Table)iter.next();
/*  744:1023 */       if (table.isPhysicalTable())
/*  745:     */       {
/*  746:1024 */         script.add(table.sqlCreateString(dialect, this.mapping, defaultCatalog, defaultSchema));
/*  747:     */         
/*  748:     */ 
/*  749:     */ 
/*  750:     */ 
/*  751:     */ 
/*  752:     */ 
/*  753:     */ 
/*  754:1032 */         Iterator<String> comments = table.sqlCommentStrings(dialect, defaultCatalog, defaultSchema);
/*  755:1033 */         while (comments.hasNext()) {
/*  756:1034 */           script.add(comments.next());
/*  757:     */         }
/*  758:     */       }
/*  759:     */     }
/*  760:1039 */     iter = getTableMappings();
/*  761:1040 */     while (iter.hasNext())
/*  762:     */     {
/*  763:1041 */       Table table = (Table)iter.next();
/*  764:1042 */       if (table.isPhysicalTable())
/*  765:     */       {
/*  766:1044 */         if (!dialect.supportsUniqueConstraintInCreateAlterTable())
/*  767:     */         {
/*  768:1045 */           Iterator subIter = table.getUniqueKeyIterator();
/*  769:1046 */           while (subIter.hasNext())
/*  770:     */           {
/*  771:1047 */             UniqueKey uk = (UniqueKey)subIter.next();
/*  772:1048 */             String constraintString = uk.sqlCreateString(dialect, this.mapping, defaultCatalog, defaultSchema);
/*  773:1049 */             if (constraintString != null) {
/*  774:1049 */               script.add(constraintString);
/*  775:     */             }
/*  776:     */           }
/*  777:     */         }
/*  778:1054 */         Iterator subIter = table.getIndexIterator();
/*  779:1055 */         while (subIter.hasNext())
/*  780:     */         {
/*  781:1056 */           Index index = (Index)subIter.next();
/*  782:1057 */           script.add(index.sqlCreateString(dialect, this.mapping, defaultCatalog, defaultSchema));
/*  783:     */         }
/*  784:1067 */         if (dialect.hasAlterTable())
/*  785:     */         {
/*  786:1068 */           subIter = table.getForeignKeyIterator();
/*  787:1069 */           while (subIter.hasNext())
/*  788:     */           {
/*  789:1070 */             ForeignKey fk = (ForeignKey)subIter.next();
/*  790:1071 */             if (fk.isPhysicalConstraint()) {
/*  791:1072 */               script.add(fk.sqlCreateString(dialect, this.mapping, defaultCatalog, defaultSchema));
/*  792:     */             }
/*  793:     */           }
/*  794:     */         }
/*  795:     */       }
/*  796:     */     }
/*  797:1086 */     iter = iterateGenerators(dialect);
/*  798:1087 */     while (iter.hasNext())
/*  799:     */     {
/*  800:1088 */       String[] lines = ((PersistentIdentifierGenerator)iter.next()).sqlCreateStrings(dialect);
/*  801:1089 */       script.addAll(Arrays.asList(lines));
/*  802:     */     }
/*  803:1092 */     for (AuxiliaryDatabaseObject auxiliaryDatabaseObject : this.auxiliaryDatabaseObjects) {
/*  804:1093 */       if (auxiliaryDatabaseObject.appliesToDialect(dialect)) {
/*  805:1094 */         script.add(auxiliaryDatabaseObject.sqlCreateString(dialect, this.mapping, defaultCatalog, defaultSchema));
/*  806:     */       }
/*  807:     */     }
/*  808:1098 */     return ArrayHelper.toStringArray(script);
/*  809:     */   }
/*  810:     */   
/*  811:     */   public String[] generateSchemaUpdateScript(Dialect dialect, DatabaseMetadata databaseMetadata)
/*  812:     */     throws HibernateException
/*  813:     */   {
/*  814:1115 */     secondPassCompile();
/*  815:     */     
/*  816:1117 */     String defaultCatalog = this.properties.getProperty("hibernate.default_catalog");
/*  817:1118 */     String defaultSchema = this.properties.getProperty("hibernate.default_schema");
/*  818:     */     
/*  819:1120 */     ArrayList<String> script = new ArrayList(50);
/*  820:     */     
/*  821:1122 */     Iterator iter = getTableMappings();
/*  822:1123 */     while (iter.hasNext())
/*  823:     */     {
/*  824:1124 */       Table table = (Table)iter.next();
/*  825:1125 */       if (table.isPhysicalTable())
/*  826:     */       {
/*  827:1127 */         TableMetadata tableInfo = databaseMetadata.getTableMetadata(table.getName(), table.getSchema() == null ? defaultSchema : table.getSchema(), table.getCatalog() == null ? defaultCatalog : table.getCatalog(), table.isQuoted());
/*  828:1134 */         if (tableInfo == null)
/*  829:     */         {
/*  830:1135 */           script.add(table.sqlCreateString(dialect, this.mapping, defaultCatalog, defaultSchema));
/*  831:     */         }
/*  832:     */         else
/*  833:     */         {
/*  834:1145 */           Iterator<String> subiter = table.sqlAlterStrings(dialect, this.mapping, tableInfo, defaultCatalog, defaultSchema);
/*  835:1152 */           while (subiter.hasNext()) {
/*  836:1153 */             script.add(subiter.next());
/*  837:     */           }
/*  838:     */         }
/*  839:1157 */         Iterator<String> comments = table.sqlCommentStrings(dialect, defaultCatalog, defaultSchema);
/*  840:1158 */         while (comments.hasNext()) {
/*  841:1159 */           script.add(comments.next());
/*  842:     */         }
/*  843:     */       }
/*  844:     */     }
/*  845:1165 */     iter = getTableMappings();
/*  846:1166 */     while (iter.hasNext())
/*  847:     */     {
/*  848:1167 */       Table table = (Table)iter.next();
/*  849:1168 */       if (table.isPhysicalTable())
/*  850:     */       {
/*  851:1170 */         TableMetadata tableInfo = databaseMetadata.getTableMetadata(table.getName(), table.getSchema(), table.getCatalog(), table.isQuoted());
/*  852:1177 */         if (dialect.hasAlterTable())
/*  853:     */         {
/*  854:1178 */           Iterator subIter = table.getForeignKeyIterator();
/*  855:1179 */           while (subIter.hasNext())
/*  856:     */           {
/*  857:1180 */             ForeignKey fk = (ForeignKey)subIter.next();
/*  858:1181 */             if (fk.isPhysicalConstraint())
/*  859:     */             {
/*  860:1182 */               boolean create = (tableInfo == null) || ((tableInfo.getForeignKeyMetadata(fk) == null) && ((!(dialect instanceof MySQLDialect)) || (tableInfo.getIndexMetadata(fk.getName()) == null)));
/*  861:1189 */               if (create) {
/*  862:1190 */                 script.add(fk.sqlCreateString(dialect, this.mapping, defaultCatalog, defaultSchema));
/*  863:     */               }
/*  864:     */             }
/*  865:     */           }
/*  866:     */         }
/*  867:1203 */         Iterator subIter = table.getIndexIterator();
/*  868:1204 */         while (subIter.hasNext())
/*  869:     */         {
/*  870:1205 */           Index index = (Index)subIter.next();
/*  871:1207 */           if ((tableInfo != null) && (StringHelper.isNotEmpty(index.getName())))
/*  872:     */           {
/*  873:1208 */             IndexMetadata meta = tableInfo.getIndexMetadata(index.getName());
/*  874:1209 */             if (meta != null) {}
/*  875:     */           }
/*  876:     */           else
/*  877:     */           {
/*  878:1213 */             script.add(index.sqlCreateString(dialect, this.mapping, defaultCatalog, defaultSchema));
/*  879:     */           }
/*  880:     */         }
/*  881:     */       }
/*  882:     */     }
/*  883:1234 */     iter = iterateGenerators(dialect);
/*  884:1235 */     while (iter.hasNext())
/*  885:     */     {
/*  886:1236 */       PersistentIdentifierGenerator generator = (PersistentIdentifierGenerator)iter.next();
/*  887:1237 */       Object key = generator.generatorKey();
/*  888:1238 */       if ((!databaseMetadata.isSequence(key)) && (!databaseMetadata.isTable(key)))
/*  889:     */       {
/*  890:1239 */         String[] lines = generator.sqlCreateStrings(dialect);
/*  891:1240 */         script.addAll(Arrays.asList(lines));
/*  892:     */       }
/*  893:     */     }
/*  894:1244 */     return ArrayHelper.toStringArray(script);
/*  895:     */   }
/*  896:     */   
/*  897:     */   public void validateSchema(Dialect dialect, DatabaseMetadata databaseMetadata)
/*  898:     */     throws HibernateException
/*  899:     */   {
/*  900:1248 */     secondPassCompile();
/*  901:     */     
/*  902:1250 */     String defaultCatalog = this.properties.getProperty("hibernate.default_catalog");
/*  903:1251 */     String defaultSchema = this.properties.getProperty("hibernate.default_schema");
/*  904:     */     
/*  905:1253 */     Iterator iter = getTableMappings();
/*  906:1254 */     while (iter.hasNext())
/*  907:     */     {
/*  908:1255 */       Table table = (Table)iter.next();
/*  909:1256 */       if (table.isPhysicalTable())
/*  910:     */       {
/*  911:1259 */         TableMetadata tableInfo = databaseMetadata.getTableMetadata(table.getName(), table.getSchema() == null ? defaultSchema : table.getSchema(), table.getCatalog() == null ? defaultCatalog : table.getCatalog(), table.isQuoted());
/*  912:1264 */         if (tableInfo == null) {
/*  913:1265 */           throw new HibernateException("Missing table: " + table.getName());
/*  914:     */         }
/*  915:1268 */         table.validateColumns(dialect, this.mapping, tableInfo);
/*  916:     */       }
/*  917:     */     }
/*  918:1274 */     iter = iterateGenerators(dialect);
/*  919:1275 */     while (iter.hasNext())
/*  920:     */     {
/*  921:1276 */       PersistentIdentifierGenerator generator = (PersistentIdentifierGenerator)iter.next();
/*  922:1277 */       Object key = generator.generatorKey();
/*  923:1278 */       if ((!databaseMetadata.isSequence(key)) && (!databaseMetadata.isTable(key))) {
/*  924:1279 */         throw new HibernateException("Missing sequence or table: " + key);
/*  925:     */       }
/*  926:     */     }
/*  927:     */   }
/*  928:     */   
/*  929:     */   private void validate()
/*  930:     */     throws MappingException
/*  931:     */   {
/*  932:1285 */     Iterator iter = this.classes.values().iterator();
/*  933:1286 */     while (iter.hasNext()) {
/*  934:1287 */       ((PersistentClass)iter.next()).validate(this.mapping);
/*  935:     */     }
/*  936:1289 */     iter = this.collections.values().iterator();
/*  937:1290 */     while (iter.hasNext()) {
/*  938:1291 */       ((org.hibernate.mapping.Collection)iter.next()).validate(this.mapping);
/*  939:     */     }
/*  940:     */   }
/*  941:     */   
/*  942:     */   public void buildMappings()
/*  943:     */   {
/*  944:1300 */     secondPassCompile();
/*  945:     */   }
/*  946:     */   
/*  947:     */   protected void secondPassCompile()
/*  948:     */     throws MappingException
/*  949:     */   {
/*  950:1304 */     LOG.trace("Starting secondPassCompile() processing");
/*  951:1308 */     if (!this.isDefaultProcessed)
/*  952:     */     {
/*  953:1310 */       Map defaults = this.reflectionManager.getDefaults();
/*  954:1311 */       Object isDelimited = defaults.get("delimited-identifier");
/*  955:1312 */       if ((isDelimited != null) && (isDelimited == Boolean.TRUE)) {
/*  956:1313 */         getProperties().put("hibernate.globally_quoted_identifiers", "true");
/*  957:     */       }
/*  958:1316 */       String schema = (String)defaults.get("schema");
/*  959:1317 */       if (StringHelper.isNotEmpty(schema)) {
/*  960:1318 */         getProperties().put("hibernate.default_schema", schema);
/*  961:     */       }
/*  962:1321 */       String catalog = (String)defaults.get("catalog");
/*  963:1322 */       if (StringHelper.isNotEmpty(catalog)) {
/*  964:1323 */         getProperties().put("hibernate.default_catalog", catalog);
/*  965:     */       }
/*  966:1326 */       AnnotationBinder.bindDefaults(createMappings());
/*  967:1327 */       this.isDefaultProcessed = true;
/*  968:     */     }
/*  969:1333 */     this.metadataSourceQueue.syncAnnotatedClasses();
/*  970:1334 */     this.metadataSourceQueue.processMetadata(determineMetadataSourcePrecedence());
/*  971:1339 */     for (CacheHolder holder : this.caches) {
/*  972:1340 */       if (holder.isClass) {
/*  973:1341 */         applyCacheConcurrencyStrategy(holder);
/*  974:     */       } else {
/*  975:1344 */         applyCollectionCacheConcurrencyStrategy(holder);
/*  976:     */       }
/*  977:     */     }
/*  978:1347 */     this.caches.clear();
/*  979:     */     try
/*  980:     */     {
/*  981:1351 */       this.inSecondPass = true;
/*  982:1352 */       processSecondPassesOfType(PkDrivenByDefaultMapsIdSecondPass.class);
/*  983:1353 */       processSecondPassesOfType(SetSimpleValueTypeSecondPass.class);
/*  984:1354 */       processSecondPassesOfType(CopyIdentifierComponentSecondPass.class);
/*  985:1355 */       processFkSecondPassInOrder();
/*  986:1356 */       processSecondPassesOfType(CreateKeySecondPass.class);
/*  987:1357 */       processSecondPassesOfType(SecondaryTableSecondPass.class);
/*  988:     */       
/*  989:1359 */       originalSecondPassCompile();
/*  990:     */       
/*  991:1361 */       this.inSecondPass = false;
/*  992:     */     }
/*  993:     */     catch (RecoverableException e)
/*  994:     */     {
/*  995:1365 */       throw ((RuntimeException)e.getCause());
/*  996:     */     }
/*  997:1368 */     for (Map.Entry<Table, List<UniqueConstraintHolder>> tableListEntry : this.uniqueConstraintHoldersByTable.entrySet())
/*  998:     */     {
/*  999:1369 */       table = (Table)tableListEntry.getKey();
/* 1000:1370 */       List<UniqueConstraintHolder> uniqueConstraints = (List)tableListEntry.getValue();
/* 1001:1371 */       uniqueIndexPerTable = 0;
/* 1002:1372 */       for (UniqueConstraintHolder holder : uniqueConstraints)
/* 1003:     */       {
/* 1004:1373 */         uniqueIndexPerTable++;
/* 1005:1374 */         String keyName = StringHelper.isEmpty(holder.getName()) ? "key" + uniqueIndexPerTable : holder.getName();
/* 1006:     */         
/* 1007:     */ 
/* 1008:1377 */         buildUniqueKeyFromColumnNames(table, keyName, holder.getColumns());
/* 1009:     */       }
/* 1010:     */     }
/* 1011:     */     Table table;
/* 1012:     */     int uniqueIndexPerTable;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   private void processSecondPassesOfType(Class<? extends SecondPass> type)
/* 1016:     */   {
/* 1017:1383 */     Iterator iter = this.secondPasses.iterator();
/* 1018:1384 */     while (iter.hasNext())
/* 1019:     */     {
/* 1020:1385 */       SecondPass sp = (SecondPass)iter.next();
/* 1021:1387 */       if (type.isInstance(sp))
/* 1022:     */       {
/* 1023:1388 */         sp.doSecondPass(this.classes);
/* 1024:1389 */         iter.remove();
/* 1025:     */       }
/* 1026:     */     }
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   private void processFkSecondPassInOrder()
/* 1030:     */   {
/* 1031:1400 */     LOG.debug("Processing fk mappings (*ToOne and JoinedSubclass)");
/* 1032:1401 */     List<FkSecondPass> fkSecondPasses = getFKSecondPassesOnly();
/* 1033:1403 */     if (fkSecondPasses.size() == 0) {
/* 1034:1404 */       return;
/* 1035:     */     }
/* 1036:1409 */     Map<String, Set<FkSecondPass>> isADependencyOf = new HashMap();
/* 1037:1410 */     List<FkSecondPass> endOfQueueFkSecondPasses = new ArrayList(fkSecondPasses.size());
/* 1038:1411 */     for (FkSecondPass sp : fkSecondPasses) {
/* 1039:1412 */       if (sp.isInPrimaryKey())
/* 1040:     */       {
/* 1041:1413 */         String referenceEntityName = sp.getReferencedEntityName();
/* 1042:1414 */         PersistentClass classMapping = getClassMapping(referenceEntityName);
/* 1043:1415 */         String dependentTable = classMapping.getTable().getQuotedName();
/* 1044:1416 */         if (!isADependencyOf.containsKey(dependentTable)) {
/* 1045:1417 */           isADependencyOf.put(dependentTable, new HashSet());
/* 1046:     */         }
/* 1047:1419 */         ((Set)isADependencyOf.get(dependentTable)).add(sp);
/* 1048:     */       }
/* 1049:     */       else
/* 1050:     */       {
/* 1051:1422 */         endOfQueueFkSecondPasses.add(sp);
/* 1052:     */       }
/* 1053:     */     }
/* 1054:1427 */     List<FkSecondPass> orderedFkSecondPasses = new ArrayList(fkSecondPasses.size());
/* 1055:1428 */     for (String tableName : isADependencyOf.keySet()) {
/* 1056:1429 */       buildRecursiveOrderedFkSecondPasses(orderedFkSecondPasses, isADependencyOf, tableName, tableName);
/* 1057:     */     }
/* 1058:1433 */     for (FkSecondPass sp : orderedFkSecondPasses) {
/* 1059:1434 */       sp.doSecondPass(this.classes);
/* 1060:     */     }
/* 1061:1437 */     processEndOfQueue(endOfQueueFkSecondPasses);
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   private List<FkSecondPass> getFKSecondPassesOnly()
/* 1065:     */   {
/* 1066:1445 */     Iterator iter = this.secondPasses.iterator();
/* 1067:1446 */     List<FkSecondPass> fkSecondPasses = new ArrayList(this.secondPasses.size());
/* 1068:1447 */     while (iter.hasNext())
/* 1069:     */     {
/* 1070:1448 */       SecondPass sp = (SecondPass)iter.next();
/* 1071:1450 */       if ((sp instanceof FkSecondPass))
/* 1072:     */       {
/* 1073:1451 */         fkSecondPasses.add((FkSecondPass)sp);
/* 1074:1452 */         iter.remove();
/* 1075:     */       }
/* 1076:     */     }
/* 1077:1455 */     return fkSecondPasses;
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   private void buildRecursiveOrderedFkSecondPasses(List<FkSecondPass> orderedFkSecondPasses, Map<String, Set<FkSecondPass>> isADependencyOf, String startTable, String currentTable)
/* 1081:     */   {
/* 1082:1477 */     Set<FkSecondPass> dependencies = (Set)isADependencyOf.get(currentTable);
/* 1083:1480 */     if ((dependencies == null) || (dependencies.size() == 0)) {
/* 1084:1481 */       return;
/* 1085:     */     }
/* 1086:1484 */     for (FkSecondPass sp : dependencies)
/* 1087:     */     {
/* 1088:1485 */       String dependentTable = sp.getValue().getTable().getQuotedName();
/* 1089:1486 */       if (dependentTable.compareTo(startTable) == 0)
/* 1090:     */       {
/* 1091:1487 */         StringBuilder sb = new StringBuilder("Foreign key circularity dependency involving the following tables: ");
/* 1092:     */         
/* 1093:     */ 
/* 1094:1490 */         throw new AnnotationException(sb.toString());
/* 1095:     */       }
/* 1096:1492 */       buildRecursiveOrderedFkSecondPasses(orderedFkSecondPasses, isADependencyOf, startTable, dependentTable);
/* 1097:1493 */       if (!orderedFkSecondPasses.contains(sp)) {
/* 1098:1494 */         orderedFkSecondPasses.add(0, sp);
/* 1099:     */       }
/* 1100:     */     }
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   private void processEndOfQueue(List<FkSecondPass> endOfQueueFkSecondPasses)
/* 1104:     */   {
/* 1105:1506 */     boolean stopProcess = false;
/* 1106:1507 */     RuntimeException originalException = null;
/* 1107:1508 */     while (!stopProcess)
/* 1108:     */     {
/* 1109:1509 */       List<FkSecondPass> failingSecondPasses = new ArrayList();
/* 1110:1510 */       Iterator<FkSecondPass> it = endOfQueueFkSecondPasses.listIterator();
/* 1111:1511 */       while (it.hasNext())
/* 1112:     */       {
/* 1113:1512 */         FkSecondPass pass = (FkSecondPass)it.next();
/* 1114:     */         try
/* 1115:     */         {
/* 1116:1514 */           pass.doSecondPass(this.classes);
/* 1117:     */         }
/* 1118:     */         catch (RecoverableException e)
/* 1119:     */         {
/* 1120:1517 */           failingSecondPasses.add(pass);
/* 1121:1518 */           if (originalException == null) {
/* 1122:1519 */             originalException = (RuntimeException)e.getCause();
/* 1123:     */           }
/* 1124:     */         }
/* 1125:     */       }
/* 1126:1523 */       stopProcess = (failingSecondPasses.size() == 0) || (failingSecondPasses.size() == endOfQueueFkSecondPasses.size());
/* 1127:1524 */       endOfQueueFkSecondPasses = failingSecondPasses;
/* 1128:     */     }
/* 1129:1526 */     if (endOfQueueFkSecondPasses.size() > 0) {
/* 1130:1527 */       throw originalException;
/* 1131:     */     }
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   private void buildUniqueKeyFromColumnNames(Table table, String keyName, String[] columnNames)
/* 1135:     */   {
/* 1136:1532 */     keyName = this.normalizer.normalizeIdentifierQuoting(keyName);
/* 1137:     */     
/* 1138:     */ 
/* 1139:1535 */     int size = columnNames.length;
/* 1140:1536 */     Column[] columns = new Column[size];
/* 1141:1537 */     Set<Column> unbound = new HashSet();
/* 1142:1538 */     Set<Column> unboundNoLogical = new HashSet();
/* 1143:1539 */     for (int index = 0; index < size; index++)
/* 1144:     */     {
/* 1145:1540 */       String logicalColumnName = this.normalizer.normalizeIdentifierQuoting(columnNames[index]);
/* 1146:     */       try
/* 1147:     */       {
/* 1148:1542 */         String columnName = createMappings().getPhysicalColumnName(logicalColumnName, table);
/* 1149:1543 */         columns[index] = new Column(columnName);
/* 1150:1544 */         unbound.add(columns[index]);
/* 1151:     */       }
/* 1152:     */       catch (MappingException e)
/* 1153:     */       {
/* 1154:1548 */         unboundNoLogical.add(new Column(logicalColumnName));
/* 1155:     */       }
/* 1156:     */     }
/* 1157:1551 */     for (Column column : columns) {
/* 1158:1552 */       if (table.containsColumn(column))
/* 1159:     */       {
/* 1160:1553 */         UniqueKey uc = table.getOrCreateUniqueKey(keyName);
/* 1161:1554 */         uc.addColumn(table.getColumn(column));
/* 1162:1555 */         unbound.remove(column);
/* 1163:     */       }
/* 1164:     */     }
/* 1165:1558 */     if ((unbound.size() > 0) || (unboundNoLogical.size() > 0))
/* 1166:     */     {
/* 1167:1559 */       StringBuilder sb = new StringBuilder("Unable to create unique key constraint (");
/* 1168:1560 */       for (String columnName : columnNames) {
/* 1169:1561 */         sb.append(columnName).append(", ");
/* 1170:     */       }
/* 1171:1563 */       sb.setLength(sb.length() - 2);
/* 1172:1564 */       sb.append(") on table ").append(table.getName()).append(": database column ");
/* 1173:1565 */       for (Column column : unbound) {
/* 1174:1566 */         sb.append(column.getName()).append(", ");
/* 1175:     */       }
/* 1176:1568 */       for (Column column : unboundNoLogical) {
/* 1177:1569 */         sb.append(column.getName()).append(", ");
/* 1178:     */       }
/* 1179:1571 */       sb.setLength(sb.length() - 2);
/* 1180:1572 */       sb.append(" not found. Make sure that you use the correct column name which depends on the naming strategy in use (it may not be the same as the property name in the entity, especially for relational types)");
/* 1181:1573 */       throw new AnnotationException(sb.toString());
/* 1182:     */     }
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   private void originalSecondPassCompile()
/* 1186:     */     throws MappingException
/* 1187:     */   {
/* 1188:1578 */     LOG.debug("Processing extends queue");
/* 1189:1579 */     processExtendsQueue();
/* 1190:     */     
/* 1191:1581 */     LOG.debug("Processing collection mappings");
/* 1192:1582 */     Iterator itr = this.secondPasses.iterator();
/* 1193:1583 */     while (itr.hasNext())
/* 1194:     */     {
/* 1195:1584 */       SecondPass sp = (SecondPass)itr.next();
/* 1196:1585 */       if (!(sp instanceof QuerySecondPass))
/* 1197:     */       {
/* 1198:1586 */         sp.doSecondPass(this.classes);
/* 1199:1587 */         itr.remove();
/* 1200:     */       }
/* 1201:     */     }
/* 1202:1591 */     LOG.debug("Processing native query and ResultSetMapping mappings");
/* 1203:1592 */     itr = this.secondPasses.iterator();
/* 1204:1593 */     while (itr.hasNext())
/* 1205:     */     {
/* 1206:1594 */       SecondPass sp = (SecondPass)itr.next();
/* 1207:1595 */       sp.doSecondPass(this.classes);
/* 1208:1596 */       itr.remove();
/* 1209:     */     }
/* 1210:1599 */     LOG.debug("Processing association property references");
/* 1211:     */     
/* 1212:1601 */     itr = this.propertyReferences.iterator();
/* 1213:1602 */     while (itr.hasNext())
/* 1214:     */     {
/* 1215:1603 */       Mappings.PropertyReference upr = (Mappings.PropertyReference)itr.next();
/* 1216:     */       
/* 1217:1605 */       PersistentClass clazz = getClassMapping(upr.referencedClass);
/* 1218:1606 */       if (clazz == null) {
/* 1219:1607 */         throw new MappingException("property-ref to unmapped class: " + upr.referencedClass);
/* 1220:     */       }
/* 1221:1613 */       Property prop = clazz.getReferencedProperty(upr.propertyName);
/* 1222:1614 */       if (upr.unique) {
/* 1223:1615 */         ((SimpleValue)prop.getValue()).setAlternateUniqueKey(true);
/* 1224:     */       }
/* 1225:     */     }
/* 1226:1621 */     LOG.debug("Processing foreign key constraints");
/* 1227:     */     
/* 1228:1623 */     itr = getTableMappings();
/* 1229:1624 */     Set done = new HashSet();
/* 1230:1625 */     while (itr.hasNext()) {
/* 1231:1626 */       secondPassCompileForeignKeys((Table)itr.next(), done);
/* 1232:     */     }
/* 1233:     */   }
/* 1234:     */   
/* 1235:     */   private int processExtendsQueue()
/* 1236:     */   {
/* 1237:1632 */     LOG.debug("Processing extends queue");
/* 1238:1633 */     int added = 0;
/* 1239:1634 */     ExtendsQueueEntry extendsQueueEntry = findPossibleExtends();
/* 1240:1635 */     while (extendsQueueEntry != null)
/* 1241:     */     {
/* 1242:1636 */       this.metadataSourceQueue.processHbmXml(extendsQueueEntry.getMetadataXml(), extendsQueueEntry.getEntityNames());
/* 1243:1637 */       extendsQueueEntry = findPossibleExtends();
/* 1244:     */     }
/* 1245:1640 */     if (this.extendsQueue.size() > 0)
/* 1246:     */     {
/* 1247:1641 */       Iterator iterator = this.extendsQueue.keySet().iterator();
/* 1248:1642 */       StringBuffer buf = new StringBuffer("Following super classes referenced in extends not found: ");
/* 1249:1643 */       while (iterator.hasNext())
/* 1250:     */       {
/* 1251:1644 */         ExtendsQueueEntry entry = (ExtendsQueueEntry)iterator.next();
/* 1252:1645 */         buf.append(entry.getExplicitName());
/* 1253:1646 */         if (entry.getMappingPackage() != null) {
/* 1254:1647 */           buf.append("[").append(entry.getMappingPackage()).append("]");
/* 1255:     */         }
/* 1256:1649 */         if (iterator.hasNext()) {
/* 1257:1650 */           buf.append(",");
/* 1258:     */         }
/* 1259:     */       }
/* 1260:1653 */       throw new MappingException(buf.toString());
/* 1261:     */     }
/* 1262:1656 */     return added;
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   protected ExtendsQueueEntry findPossibleExtends()
/* 1266:     */   {
/* 1267:1660 */     Iterator<ExtendsQueueEntry> itr = this.extendsQueue.keySet().iterator();
/* 1268:1661 */     while (itr.hasNext())
/* 1269:     */     {
/* 1270:1662 */       ExtendsQueueEntry entry = (ExtendsQueueEntry)itr.next();
/* 1271:1663 */       boolean found = (getClassMapping(entry.getExplicitName()) != null) || (getClassMapping(HbmBinder.getClassName(entry.getExplicitName(), entry.getMappingPackage())) != null);
/* 1272:1665 */       if (found)
/* 1273:     */       {
/* 1274:1666 */         itr.remove();
/* 1275:1667 */         return entry;
/* 1276:     */       }
/* 1277:     */     }
/* 1278:1670 */     return null;
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   protected void secondPassCompileForeignKeys(Table table, Set done)
/* 1282:     */     throws MappingException
/* 1283:     */   {
/* 1284:1674 */     table.createForeignKeys();
/* 1285:1675 */     Iterator iter = table.getForeignKeyIterator();
/* 1286:1676 */     while (iter.hasNext())
/* 1287:     */     {
/* 1288:1678 */       ForeignKey fk = (ForeignKey)iter.next();
/* 1289:1679 */       if (!done.contains(fk))
/* 1290:     */       {
/* 1291:1680 */         done.add(fk);
/* 1292:1681 */         String referencedEntityName = fk.getReferencedEntityName();
/* 1293:1682 */         if (referencedEntityName == null) {
/* 1294:1683 */           throw new MappingException("An association from the table " + fk.getTable().getName() + " does not specify the referenced entity");
/* 1295:     */         }
/* 1296:1689 */         LOG.debugf("Resolving reference to class: %s", referencedEntityName);
/* 1297:1690 */         PersistentClass referencedClass = (PersistentClass)this.classes.get(referencedEntityName);
/* 1298:1691 */         if (referencedClass == null) {
/* 1299:1692 */           throw new MappingException("An association from the table " + fk.getTable().getName() + " refers to an unmapped class: " + referencedEntityName);
/* 1300:     */         }
/* 1301:1699 */         if (referencedClass.isJoinedSubclass()) {
/* 1302:1700 */           secondPassCompileForeignKeys(referencedClass.getSuperclass().getTable(), done);
/* 1303:     */         }
/* 1304:1702 */         fk.setReferencedTable(referencedClass.getTable());
/* 1305:1703 */         fk.alignColumns();
/* 1306:     */       }
/* 1307:     */     }
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public Map<String, NamedQueryDefinition> getNamedQueries()
/* 1311:     */   {
/* 1312:1709 */     return this.namedQueries;
/* 1313:     */   }
/* 1314:     */   
/* 1315:     */   public SessionFactory buildSessionFactory(ServiceRegistry serviceRegistry)
/* 1316:     */     throws HibernateException
/* 1317:     */   {
/* 1318:1722 */     LOG.debugf("Preparing to build session factory with filters : %s", this.filterDefinitions);
/* 1319:     */     
/* 1320:1724 */     secondPassCompile();
/* 1321:1725 */     if (!this.metadataSourceQueue.isEmpty()) {
/* 1322:1726 */       LOG.incompleteMappingMetadataCacheProcessing();
/* 1323:     */     }
/* 1324:1729 */     validate();
/* 1325:     */     
/* 1326:1731 */     Environment.verifyProperties(this.properties);
/* 1327:1732 */     Properties copy = new Properties();
/* 1328:1733 */     copy.putAll(this.properties);
/* 1329:1734 */     ConfigurationHelper.resolvePlaceHolders(copy);
/* 1330:1735 */     Settings settings = buildSettings(copy, serviceRegistry);
/* 1331:     */     
/* 1332:1737 */     return new SessionFactoryImpl(this, this.mapping, serviceRegistry, settings, this.sessionFactoryObserver);
/* 1333:     */   }
/* 1334:     */   
/* 1335:     */   /**
/* 1336:     */    * @deprecated
/* 1337:     */    */
/* 1338:     */   public SessionFactory buildSessionFactory()
/* 1339:     */     throws HibernateException
/* 1340:     */   {
/* 1341:1758 */     Environment.verifyProperties(this.properties);
/* 1342:1759 */     ConfigurationHelper.resolvePlaceHolders(this.properties);
/* 1343:1760 */     final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(this.properties).buildServiceRegistry();
/* 1344:     */     
/* 1345:     */ 
/* 1346:1763 */     setSessionFactoryObserver(new SessionFactoryObserver()
/* 1347:     */     {
/* 1348:     */       public void sessionFactoryCreated(SessionFactory factory) {}
/* 1349:     */       
/* 1350:     */       public void sessionFactoryClosed(SessionFactory factory)
/* 1351:     */       {
/* 1352:1771 */         ((StandardServiceRegistryImpl)serviceRegistry).destroy();
/* 1353:     */       }
/* 1354:1774 */     });
/* 1355:1775 */     return buildSessionFactory(serviceRegistry);
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   public Interceptor getInterceptor()
/* 1359:     */   {
/* 1360:1784 */     return this.interceptor;
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   public Configuration setInterceptor(Interceptor interceptor)
/* 1364:     */   {
/* 1365:1796 */     this.interceptor = interceptor;
/* 1366:1797 */     return this;
/* 1367:     */   }
/* 1368:     */   
/* 1369:     */   public Properties getProperties()
/* 1370:     */   {
/* 1371:1806 */     return this.properties;
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public String getProperty(String propertyName)
/* 1375:     */   {
/* 1376:1817 */     return this.properties.getProperty(propertyName);
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   public Configuration setProperties(Properties properties)
/* 1380:     */   {
/* 1381:1828 */     this.properties = properties;
/* 1382:1829 */     return this;
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   public Configuration addProperties(Properties extraProperties)
/* 1386:     */   {
/* 1387:1841 */     this.properties.putAll(extraProperties);
/* 1388:1842 */     return this;
/* 1389:     */   }
/* 1390:     */   
/* 1391:     */   public Configuration mergeProperties(Properties properties)
/* 1392:     */   {
/* 1393:1854 */     for (Map.Entry entry : properties.entrySet()) {
/* 1394:1855 */       if (!this.properties.containsKey(entry.getKey())) {
/* 1395:1858 */         this.properties.setProperty((String)entry.getKey(), (String)entry.getValue());
/* 1396:     */       }
/* 1397:     */     }
/* 1398:1860 */     return this;
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   public Configuration setProperty(String propertyName, String value)
/* 1402:     */   {
/* 1403:1872 */     this.properties.setProperty(propertyName, value);
/* 1404:1873 */     return this;
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   private void addProperties(Element parent)
/* 1408:     */   {
/* 1409:1877 */     Iterator itr = parent.elementIterator("property");
/* 1410:1878 */     while (itr.hasNext())
/* 1411:     */     {
/* 1412:1879 */       Element node = (Element)itr.next();
/* 1413:1880 */       String name = node.attributeValue("name");
/* 1414:1881 */       String value = node.getText().trim();
/* 1415:1882 */       LOG.debugf("%s=%s", name, value);
/* 1416:1883 */       this.properties.setProperty(name, value);
/* 1417:1884 */       if (!name.startsWith("hibernate")) {
/* 1418:1885 */         this.properties.setProperty("hibernate." + name, value);
/* 1419:     */       }
/* 1420:     */     }
/* 1421:1888 */     Environment.verifyProperties(this.properties);
/* 1422:     */   }
/* 1423:     */   
/* 1424:     */   public Configuration configure()
/* 1425:     */     throws HibernateException
/* 1426:     */   {
/* 1427:1901 */     configure("/hibernate.cfg.xml");
/* 1428:1902 */     return this;
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public Configuration configure(String resource)
/* 1432:     */     throws HibernateException
/* 1433:     */   {
/* 1434:1920 */     LOG.configuringFromResource(resource);
/* 1435:1921 */     InputStream stream = getConfigurationInputStream(resource);
/* 1436:1922 */     return doConfigure(stream, resource);
/* 1437:     */   }
/* 1438:     */   
/* 1439:     */   protected InputStream getConfigurationInputStream(String resource)
/* 1440:     */     throws HibernateException
/* 1441:     */   {
/* 1442:1939 */     LOG.configurationResource(resource);
/* 1443:1940 */     return ConfigHelper.getResourceAsStream(resource);
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   public Configuration configure(URL url)
/* 1447:     */     throws HibernateException
/* 1448:     */   {
/* 1449:1956 */     LOG.configuringFromUrl(url);
/* 1450:     */     try
/* 1451:     */     {
/* 1452:1958 */       return doConfigure(url.openStream(), url.toString());
/* 1453:     */     }
/* 1454:     */     catch (IOException ioe)
/* 1455:     */     {
/* 1456:1961 */       throw new HibernateException("could not configure from URL: " + url, ioe);
/* 1457:     */     }
/* 1458:     */   }
/* 1459:     */   
/* 1460:     */   public Configuration configure(File configFile)
/* 1461:     */     throws HibernateException
/* 1462:     */   {
/* 1463:1978 */     LOG.configuringFromFile(configFile.getName());
/* 1464:     */     try
/* 1465:     */     {
/* 1466:1980 */       return doConfigure(new FileInputStream(configFile), configFile.toString());
/* 1467:     */     }
/* 1468:     */     catch (FileNotFoundException fnfe)
/* 1469:     */     {
/* 1470:1983 */       throw new HibernateException("could not find file: " + configFile, fnfe);
/* 1471:     */     }
/* 1472:     */   }
/* 1473:     */   
/* 1474:     */   protected Configuration doConfigure(InputStream stream, String resourceName)
/* 1475:     */     throws HibernateException
/* 1476:     */   {
/* 1477:     */     try
/* 1478:     */     {
/* 1479:2001 */       List errors = new ArrayList();
/* 1480:2002 */       org.dom4j.Document document = this.xmlHelper.createSAXReader(resourceName, errors, this.entityResolver).read(new InputSource(stream));
/* 1481:2004 */       if (errors.size() != 0) {
/* 1482:2005 */         throw new MappingException("invalid configuration", (Throwable)errors.get(0));
/* 1483:     */       }
/* 1484:2007 */       doConfigure(document);
/* 1485:     */       
/* 1486:     */ 
/* 1487:     */ 
/* 1488:     */ 
/* 1489:     */ 
/* 1490:     */ 
/* 1491:     */ 
/* 1492:     */ 
/* 1493:     */ 
/* 1494:     */ 
/* 1495:     */ 
/* 1496:     */ 
/* 1497:2020 */       return this;
/* 1498:     */     }
/* 1499:     */     catch (DocumentException e)
/* 1500:     */     {
/* 1501:2010 */       throw new HibernateException("Could not parse configuration: " + resourceName, e);
/* 1502:     */     }
/* 1503:     */     finally
/* 1504:     */     {
/* 1505:     */       try
/* 1506:     */       {
/* 1507:2014 */         stream.close();
/* 1508:     */       }
/* 1509:     */       catch (IOException ioe)
/* 1510:     */       {
/* 1511:2017 */         LOG.unableToCloseInputStreamForResource(resourceName, ioe);
/* 1512:     */       }
/* 1513:     */     }
/* 1514:     */   }
/* 1515:     */   
/* 1516:     */   public Configuration configure(org.w3c.dom.Document document)
/* 1517:     */     throws HibernateException
/* 1518:     */   {
/* 1519:2033 */     LOG.configuringFromXmlDocument();
/* 1520:2034 */     return doConfigure(this.xmlHelper.createDOMReader().read(document));
/* 1521:     */   }
/* 1522:     */   
/* 1523:     */   protected Configuration doConfigure(org.dom4j.Document doc)
/* 1524:     */     throws HibernateException
/* 1525:     */   {
/* 1526:2048 */     Element sfNode = doc.getRootElement().element("session-factory");
/* 1527:2049 */     String name = sfNode.attributeValue("name");
/* 1528:2050 */     if (name != null) {
/* 1529:2051 */       this.properties.setProperty("hibernate.session_factory_name", name);
/* 1530:     */     }
/* 1531:2053 */     addProperties(sfNode);
/* 1532:2054 */     parseSessionFactory(sfNode, name);
/* 1533:     */     
/* 1534:2056 */     Element secNode = doc.getRootElement().element("security");
/* 1535:2057 */     if (secNode != null) {
/* 1536:2058 */       parseSecurity(secNode);
/* 1537:     */     }
/* 1538:2061 */     LOG.configuredSessionFactory(name);
/* 1539:2062 */     LOG.debugf("Properties: %s", this.properties);
/* 1540:     */     
/* 1541:2064 */     return this;
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   private void parseSessionFactory(Element sfNode, String name)
/* 1545:     */   {
/* 1546:2069 */     Iterator elements = sfNode.elementIterator();
/* 1547:2070 */     while (elements.hasNext())
/* 1548:     */     {
/* 1549:2071 */       Element subelement = (Element)elements.next();
/* 1550:2072 */       String subelementName = subelement.getName();
/* 1551:2073 */       if ("mapping".equals(subelementName))
/* 1552:     */       {
/* 1553:2074 */         parseMappingElement(subelement, name);
/* 1554:     */       }
/* 1555:2076 */       else if ("class-cache".equals(subelementName))
/* 1556:     */       {
/* 1557:2077 */         String className = subelement.attributeValue("class");
/* 1558:2078 */         Attribute regionNode = subelement.attribute("region");
/* 1559:2079 */         String region = regionNode == null ? className : regionNode.getValue();
/* 1560:2080 */         boolean includeLazy = !"non-lazy".equals(subelement.attributeValue("include"));
/* 1561:2081 */         setCacheConcurrencyStrategy(className, subelement.attributeValue("usage"), region, includeLazy);
/* 1562:     */       }
/* 1563:2083 */       else if ("collection-cache".equals(subelementName))
/* 1564:     */       {
/* 1565:2084 */         String role = subelement.attributeValue("collection");
/* 1566:2085 */         Attribute regionNode = subelement.attribute("region");
/* 1567:2086 */         String region = regionNode == null ? role : regionNode.getValue();
/* 1568:2087 */         setCollectionCacheConcurrencyStrategy(role, subelement.attributeValue("usage"), region);
/* 1569:     */       }
/* 1570:     */     }
/* 1571:     */   }
/* 1572:     */   
/* 1573:     */   private void parseMappingElement(Element mappingElement, String name)
/* 1574:     */   {
/* 1575:2093 */     Attribute resourceAttribute = mappingElement.attribute("resource");
/* 1576:2094 */     Attribute fileAttribute = mappingElement.attribute("file");
/* 1577:2095 */     Attribute jarAttribute = mappingElement.attribute("jar");
/* 1578:2096 */     Attribute packageAttribute = mappingElement.attribute("package");
/* 1579:2097 */     Attribute classAttribute = mappingElement.attribute("class");
/* 1580:2099 */     if (resourceAttribute != null)
/* 1581:     */     {
/* 1582:2100 */       String resourceName = resourceAttribute.getValue();
/* 1583:2101 */       LOG.debugf("Session-factory config [%s] named resource [%s] for mapping", name, resourceName);
/* 1584:2102 */       addResource(resourceName);
/* 1585:     */     }
/* 1586:2104 */     else if (fileAttribute != null)
/* 1587:     */     {
/* 1588:2105 */       String fileName = fileAttribute.getValue();
/* 1589:2106 */       LOG.debugf("Session-factory config [%s] named file [%s] for mapping", name, fileName);
/* 1590:2107 */       addFile(fileName);
/* 1591:     */     }
/* 1592:2109 */     else if (jarAttribute != null)
/* 1593:     */     {
/* 1594:2110 */       String jarFileName = jarAttribute.getValue();
/* 1595:2111 */       LOG.debugf("Session-factory config [%s] named jar file [%s] for mapping", name, jarFileName);
/* 1596:2112 */       addJar(new File(jarFileName));
/* 1597:     */     }
/* 1598:2114 */     else if (packageAttribute != null)
/* 1599:     */     {
/* 1600:2115 */       String packageName = packageAttribute.getValue();
/* 1601:2116 */       LOG.debugf("Session-factory config [%s] named package [%s] for mapping", name, packageName);
/* 1602:2117 */       addPackage(packageName);
/* 1603:     */     }
/* 1604:2119 */     else if (classAttribute != null)
/* 1605:     */     {
/* 1606:2120 */       String className = classAttribute.getValue();
/* 1607:2121 */       LOG.debugf("Session-factory config [%s] named class [%s] for mapping", name, className);
/* 1608:     */       try
/* 1609:     */       {
/* 1610:2123 */         addAnnotatedClass(ReflectHelper.classForName(className));
/* 1611:     */       }
/* 1612:     */       catch (Exception e)
/* 1613:     */       {
/* 1614:2126 */         throw new MappingException("Unable to load class [ " + className + "] declared in Hibernate configuration <mapping/> entry", e);
/* 1615:     */       }
/* 1616:     */     }
/* 1617:     */     else
/* 1618:     */     {
/* 1619:2133 */       throw new MappingException("<mapping> element in configuration specifies no known attributes");
/* 1620:     */     }
/* 1621:     */   }
/* 1622:     */   
/* 1623:     */   private void parseSecurity(Element secNode)
/* 1624:     */   {
/* 1625:2138 */     String contextId = secNode.attributeValue("context");
/* 1626:2139 */     setProperty("hibernate.jacc_context_id", contextId);
/* 1627:2140 */     LOG.jaccContextId(contextId);
/* 1628:2141 */     JACCConfiguration jcfg = new JACCConfiguration(contextId);
/* 1629:2142 */     Iterator grantElements = secNode.elementIterator();
/* 1630:2143 */     while (grantElements.hasNext())
/* 1631:     */     {
/* 1632:2144 */       Element grantElement = (Element)grantElements.next();
/* 1633:2145 */       String elementName = grantElement.getName();
/* 1634:2146 */       if ("grant".equals(elementName)) {
/* 1635:2147 */         jcfg.addPermission(grantElement.attributeValue("role"), grantElement.attributeValue("entity-name"), grantElement.attributeValue("actions"));
/* 1636:     */       }
/* 1637:     */     }
/* 1638:     */   }
/* 1639:     */   
/* 1640:     */   RootClass getRootClassMapping(String clazz)
/* 1641:     */     throws MappingException
/* 1642:     */   {
/* 1643:     */     try
/* 1644:     */     {
/* 1645:2158 */       return (RootClass)getClassMapping(clazz);
/* 1646:     */     }
/* 1647:     */     catch (ClassCastException cce)
/* 1648:     */     {
/* 1649:2161 */       throw new MappingException("You may only specify a cache for root <class> mappings");
/* 1650:     */     }
/* 1651:     */   }
/* 1652:     */   
/* 1653:     */   public Configuration setCacheConcurrencyStrategy(String entityName, String concurrencyStrategy)
/* 1654:     */   {
/* 1655:2174 */     setCacheConcurrencyStrategy(entityName, concurrencyStrategy, entityName);
/* 1656:2175 */     return this;
/* 1657:     */   }
/* 1658:     */   
/* 1659:     */   public Configuration setCacheConcurrencyStrategy(String entityName, String concurrencyStrategy, String region)
/* 1660:     */   {
/* 1661:2188 */     setCacheConcurrencyStrategy(entityName, concurrencyStrategy, region, true);
/* 1662:2189 */     return this;
/* 1663:     */   }
/* 1664:     */   
/* 1665:     */   public void setCacheConcurrencyStrategy(String entityName, String concurrencyStrategy, String region, boolean cacheLazyProperty)
/* 1666:     */     throws MappingException
/* 1667:     */   {
/* 1668:2197 */     this.caches.add(new CacheHolder(entityName, concurrencyStrategy, region, true, cacheLazyProperty));
/* 1669:     */   }
/* 1670:     */   
/* 1671:     */   private void applyCacheConcurrencyStrategy(CacheHolder holder)
/* 1672:     */   {
/* 1673:2201 */     RootClass rootClass = getRootClassMapping(holder.role);
/* 1674:2202 */     if (rootClass == null) {
/* 1675:2203 */       throw new MappingException("Cannot cache an unknown entity: " + holder.role);
/* 1676:     */     }
/* 1677:2205 */     rootClass.setCacheConcurrencyStrategy(holder.usage);
/* 1678:2206 */     rootClass.setCacheRegionName(holder.region);
/* 1679:2207 */     rootClass.setLazyPropertiesCacheable(holder.cacheLazy);
/* 1680:     */   }
/* 1681:     */   
/* 1682:     */   public Configuration setCollectionCacheConcurrencyStrategy(String collectionRole, String concurrencyStrategy)
/* 1683:     */   {
/* 1684:2219 */     setCollectionCacheConcurrencyStrategy(collectionRole, concurrencyStrategy, collectionRole);
/* 1685:2220 */     return this;
/* 1686:     */   }
/* 1687:     */   
/* 1688:     */   public void setCollectionCacheConcurrencyStrategy(String collectionRole, String concurrencyStrategy, String region)
/* 1689:     */   {
/* 1690:2233 */     this.caches.add(new CacheHolder(collectionRole, concurrencyStrategy, region, false, false));
/* 1691:     */   }
/* 1692:     */   
/* 1693:     */   private void applyCollectionCacheConcurrencyStrategy(CacheHolder holder)
/* 1694:     */   {
/* 1695:2237 */     org.hibernate.mapping.Collection collection = getCollectionMapping(holder.role);
/* 1696:2238 */     if (collection == null) {
/* 1697:2239 */       throw new MappingException("Cannot cache an unknown collection: " + holder.role);
/* 1698:     */     }
/* 1699:2241 */     collection.setCacheConcurrencyStrategy(holder.usage);
/* 1700:2242 */     collection.setCacheRegionName(holder.region);
/* 1701:     */   }
/* 1702:     */   
/* 1703:     */   public Map<String, String> getImports()
/* 1704:     */   {
/* 1705:2251 */     return this.imports;
/* 1706:     */   }
/* 1707:     */   
/* 1708:     */   public Settings buildSettings(ServiceRegistry serviceRegistry)
/* 1709:     */   {
/* 1710:2260 */     Properties clone = (Properties)this.properties.clone();
/* 1711:2261 */     ConfigurationHelper.resolvePlaceHolders(clone);
/* 1712:2262 */     return buildSettingsInternal(clone, serviceRegistry);
/* 1713:     */   }
/* 1714:     */   
/* 1715:     */   public Settings buildSettings(Properties props, ServiceRegistry serviceRegistry)
/* 1716:     */     throws HibernateException
/* 1717:     */   {
/* 1718:2266 */     return buildSettingsInternal(props, serviceRegistry);
/* 1719:     */   }
/* 1720:     */   
/* 1721:     */   private Settings buildSettingsInternal(Properties props, ServiceRegistry serviceRegistry)
/* 1722:     */   {
/* 1723:2270 */     Settings settings = this.settingsFactory.buildSettings(props, serviceRegistry);
/* 1724:2271 */     settings.setEntityTuplizerFactory(getEntityTuplizerFactory());
/* 1725:     */     
/* 1726:2273 */     return settings;
/* 1727:     */   }
/* 1728:     */   
/* 1729:     */   public Map getNamedSQLQueries()
/* 1730:     */   {
/* 1731:2277 */     return this.namedSqlQueries;
/* 1732:     */   }
/* 1733:     */   
/* 1734:     */   public Map getSqlResultSetMappings()
/* 1735:     */   {
/* 1736:2281 */     return this.sqlResultSetMappings;
/* 1737:     */   }
/* 1738:     */   
/* 1739:     */   public NamingStrategy getNamingStrategy()
/* 1740:     */   {
/* 1741:2285 */     return this.namingStrategy;
/* 1742:     */   }
/* 1743:     */   
/* 1744:     */   public Configuration setNamingStrategy(NamingStrategy namingStrategy)
/* 1745:     */   {
/* 1746:2296 */     this.namingStrategy = namingStrategy;
/* 1747:2297 */     return this;
/* 1748:     */   }
/* 1749:     */   
/* 1750:     */   public MutableIdentifierGeneratorFactory getIdentifierGeneratorFactory()
/* 1751:     */   {
/* 1752:2306 */     return this.identifierGeneratorFactory;
/* 1753:     */   }
/* 1754:     */   
/* 1755:     */   public Mapping buildMapping()
/* 1756:     */   {
/* 1757:2310 */     new Mapping()
/* 1758:     */     {
/* 1759:     */       public IdentifierGeneratorFactory getIdentifierGeneratorFactory()
/* 1760:     */       {
/* 1761:2312 */         return Configuration.this.identifierGeneratorFactory;
/* 1762:     */       }
/* 1763:     */       
/* 1764:     */       public Type getIdentifierType(String entityName)
/* 1765:     */         throws MappingException
/* 1766:     */       {
/* 1767:2319 */         PersistentClass pc = (PersistentClass)Configuration.this.classes.get(entityName);
/* 1768:2320 */         if (pc == null) {
/* 1769:2321 */           throw new MappingException("persistent class not known: " + entityName);
/* 1770:     */         }
/* 1771:2323 */         return pc.getIdentifier().getType();
/* 1772:     */       }
/* 1773:     */       
/* 1774:     */       public String getIdentifierPropertyName(String entityName)
/* 1775:     */         throws MappingException
/* 1776:     */       {
/* 1777:2327 */         PersistentClass pc = (PersistentClass)Configuration.this.classes.get(entityName);
/* 1778:2328 */         if (pc == null) {
/* 1779:2329 */           throw new MappingException("persistent class not known: " + entityName);
/* 1780:     */         }
/* 1781:2331 */         if (!pc.hasIdentifierProperty()) {
/* 1782:2332 */           return null;
/* 1783:     */         }
/* 1784:2334 */         return pc.getIdentifierProperty().getName();
/* 1785:     */       }
/* 1786:     */       
/* 1787:     */       public Type getReferencedPropertyType(String entityName, String propertyName)
/* 1788:     */         throws MappingException
/* 1789:     */       {
/* 1790:2338 */         PersistentClass pc = (PersistentClass)Configuration.this.classes.get(entityName);
/* 1791:2339 */         if (pc == null) {
/* 1792:2340 */           throw new MappingException("persistent class not known: " + entityName);
/* 1793:     */         }
/* 1794:2342 */         Property prop = pc.getReferencedProperty(propertyName);
/* 1795:2343 */         if (prop == null) {
/* 1796:2344 */           throw new MappingException("property not known: " + entityName + '.' + propertyName);
/* 1797:     */         }
/* 1798:2349 */         return prop.getType();
/* 1799:     */       }
/* 1800:     */     };
/* 1801:     */   }
/* 1802:     */   
/* 1803:     */   private void readObject(ObjectInputStream ois)
/* 1804:     */     throws IOException, ClassNotFoundException
/* 1805:     */   {
/* 1806:2356 */     MetadataProvider metadataProvider = (MetadataProvider)ois.readObject();
/* 1807:2357 */     this.mapping = buildMapping();
/* 1808:2358 */     this.xmlHelper = new XMLHelper();
/* 1809:2359 */     createReflectionManager(metadataProvider);
/* 1810:2360 */     ois.defaultReadObject();
/* 1811:     */   }
/* 1812:     */   
/* 1813:     */   private void writeObject(ObjectOutputStream out)
/* 1814:     */     throws IOException
/* 1815:     */   {
/* 1816:2365 */     MetadataProvider metadataProvider = ((MetadataProviderInjector)this.reflectionManager).getMetadataProvider();
/* 1817:2366 */     out.writeObject(metadataProvider);
/* 1818:2367 */     out.defaultWriteObject();
/* 1819:     */   }
/* 1820:     */   
/* 1821:     */   private void createReflectionManager()
/* 1822:     */   {
/* 1823:2371 */     createReflectionManager(new JPAMetadataProvider());
/* 1824:     */   }
/* 1825:     */   
/* 1826:     */   private void createReflectionManager(MetadataProvider metadataProvider)
/* 1827:     */   {
/* 1828:2375 */     this.reflectionManager = new JavaReflectionManager();
/* 1829:2376 */     ((MetadataProviderInjector)this.reflectionManager).setMetadataProvider(metadataProvider);
/* 1830:     */   }
/* 1831:     */   
/* 1832:     */   public Map getFilterDefinitions()
/* 1833:     */   {
/* 1834:2380 */     return this.filterDefinitions;
/* 1835:     */   }
/* 1836:     */   
/* 1837:     */   public void addFilterDefinition(FilterDefinition definition)
/* 1838:     */   {
/* 1839:2384 */     this.filterDefinitions.put(definition.getFilterName(), definition);
/* 1840:     */   }
/* 1841:     */   
/* 1842:     */   public Iterator iterateFetchProfiles()
/* 1843:     */   {
/* 1844:2388 */     return this.fetchProfiles.values().iterator();
/* 1845:     */   }
/* 1846:     */   
/* 1847:     */   public void addFetchProfile(FetchProfile fetchProfile)
/* 1848:     */   {
/* 1849:2392 */     this.fetchProfiles.put(fetchProfile.getName(), fetchProfile);
/* 1850:     */   }
/* 1851:     */   
/* 1852:     */   public void addAuxiliaryDatabaseObject(AuxiliaryDatabaseObject object)
/* 1853:     */   {
/* 1854:2396 */     this.auxiliaryDatabaseObjects.add(object);
/* 1855:     */   }
/* 1856:     */   
/* 1857:     */   public Map getSqlFunctions()
/* 1858:     */   {
/* 1859:2400 */     return this.sqlFunctions;
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   public void addSqlFunction(String functionName, SQLFunction function)
/* 1863:     */   {
/* 1864:2404 */     this.sqlFunctions.put(functionName, function);
/* 1865:     */   }
/* 1866:     */   
/* 1867:     */   public TypeResolver getTypeResolver()
/* 1868:     */   {
/* 1869:2408 */     return this.typeResolver;
/* 1870:     */   }
/* 1871:     */   
/* 1872:     */   public void registerTypeOverride(BasicType type)
/* 1873:     */   {
/* 1874:2418 */     getTypeResolver().registerTypeOverride(type);
/* 1875:     */   }
/* 1876:     */   
/* 1877:     */   public void registerTypeOverride(UserType type, String[] keys)
/* 1878:     */   {
/* 1879:2423 */     getTypeResolver().registerTypeOverride(type, keys);
/* 1880:     */   }
/* 1881:     */   
/* 1882:     */   public void registerTypeOverride(CompositeUserType type, String[] keys)
/* 1883:     */   {
/* 1884:2427 */     getTypeResolver().registerTypeOverride(type, keys);
/* 1885:     */   }
/* 1886:     */   
/* 1887:     */   public SessionFactoryObserver getSessionFactoryObserver()
/* 1888:     */   {
/* 1889:2431 */     return this.sessionFactoryObserver;
/* 1890:     */   }
/* 1891:     */   
/* 1892:     */   public void setSessionFactoryObserver(SessionFactoryObserver sessionFactoryObserver)
/* 1893:     */   {
/* 1894:2435 */     this.sessionFactoryObserver = sessionFactoryObserver;
/* 1895:     */   }
/* 1896:     */   
/* 1897:     */   protected class MappingsImpl
/* 1898:     */     implements ExtendedMappings, Serializable
/* 1899:     */   {
/* 1900:     */     private String schemaName;
/* 1901:     */     private String catalogName;
/* 1902:     */     private String defaultPackage;
/* 1903:     */     private boolean autoImport;
/* 1904:     */     private boolean defaultLazy;
/* 1905:     */     private String defaultCascade;
/* 1906:     */     private String defaultAccess;
/* 1907:     */     private Boolean useNewGeneratorMappings;
/* 1908:     */     
/* 1909:     */     protected MappingsImpl() {}
/* 1910:     */     
/* 1911:     */     public String getSchemaName()
/* 1912:     */     {
/* 1913:2450 */       return this.schemaName;
/* 1914:     */     }
/* 1915:     */     
/* 1916:     */     public void setSchemaName(String schemaName)
/* 1917:     */     {
/* 1918:2454 */       this.schemaName = schemaName;
/* 1919:     */     }
/* 1920:     */     
/* 1921:     */     public String getCatalogName()
/* 1922:     */     {
/* 1923:2461 */       return this.catalogName;
/* 1924:     */     }
/* 1925:     */     
/* 1926:     */     public void setCatalogName(String catalogName)
/* 1927:     */     {
/* 1928:2465 */       this.catalogName = catalogName;
/* 1929:     */     }
/* 1930:     */     
/* 1931:     */     public String getDefaultPackage()
/* 1932:     */     {
/* 1933:2472 */       return this.defaultPackage;
/* 1934:     */     }
/* 1935:     */     
/* 1936:     */     public void setDefaultPackage(String defaultPackage)
/* 1937:     */     {
/* 1938:2476 */       this.defaultPackage = defaultPackage;
/* 1939:     */     }
/* 1940:     */     
/* 1941:     */     public boolean isAutoImport()
/* 1942:     */     {
/* 1943:2483 */       return this.autoImport;
/* 1944:     */     }
/* 1945:     */     
/* 1946:     */     public void setAutoImport(boolean autoImport)
/* 1947:     */     {
/* 1948:2487 */       this.autoImport = autoImport;
/* 1949:     */     }
/* 1950:     */     
/* 1951:     */     public boolean isDefaultLazy()
/* 1952:     */     {
/* 1953:2494 */       return this.defaultLazy;
/* 1954:     */     }
/* 1955:     */     
/* 1956:     */     public void setDefaultLazy(boolean defaultLazy)
/* 1957:     */     {
/* 1958:2498 */       this.defaultLazy = defaultLazy;
/* 1959:     */     }
/* 1960:     */     
/* 1961:     */     public String getDefaultCascade()
/* 1962:     */     {
/* 1963:2505 */       return this.defaultCascade;
/* 1964:     */     }
/* 1965:     */     
/* 1966:     */     public void setDefaultCascade(String defaultCascade)
/* 1967:     */     {
/* 1968:2509 */       this.defaultCascade = defaultCascade;
/* 1969:     */     }
/* 1970:     */     
/* 1971:     */     public String getDefaultAccess()
/* 1972:     */     {
/* 1973:2516 */       return this.defaultAccess;
/* 1974:     */     }
/* 1975:     */     
/* 1976:     */     public void setDefaultAccess(String defaultAccess)
/* 1977:     */     {
/* 1978:2520 */       this.defaultAccess = defaultAccess;
/* 1979:     */     }
/* 1980:     */     
/* 1981:     */     public NamingStrategy getNamingStrategy()
/* 1982:     */     {
/* 1983:2525 */       return Configuration.this.namingStrategy;
/* 1984:     */     }
/* 1985:     */     
/* 1986:     */     public void setNamingStrategy(NamingStrategy namingStrategy)
/* 1987:     */     {
/* 1988:2529 */       Configuration.this.namingStrategy = namingStrategy;
/* 1989:     */     }
/* 1990:     */     
/* 1991:     */     public TypeResolver getTypeResolver()
/* 1992:     */     {
/* 1993:2533 */       return Configuration.this.typeResolver;
/* 1994:     */     }
/* 1995:     */     
/* 1996:     */     public Iterator<PersistentClass> iterateClasses()
/* 1997:     */     {
/* 1998:2537 */       return Configuration.this.classes.values().iterator();
/* 1999:     */     }
/* 2000:     */     
/* 2001:     */     public PersistentClass getClass(String entityName)
/* 2002:     */     {
/* 2003:2541 */       return (PersistentClass)Configuration.this.classes.get(entityName);
/* 2004:     */     }
/* 2005:     */     
/* 2006:     */     public PersistentClass locatePersistentClassByEntityName(String entityName)
/* 2007:     */     {
/* 2008:2545 */       PersistentClass persistentClass = (PersistentClass)Configuration.this.classes.get(entityName);
/* 2009:2546 */       if (persistentClass == null)
/* 2010:     */       {
/* 2011:2547 */         String actualEntityName = (String)Configuration.this.imports.get(entityName);
/* 2012:2548 */         if (StringHelper.isNotEmpty(actualEntityName)) {
/* 2013:2549 */           persistentClass = (PersistentClass)Configuration.this.classes.get(actualEntityName);
/* 2014:     */         }
/* 2015:     */       }
/* 2016:2552 */       return persistentClass;
/* 2017:     */     }
/* 2018:     */     
/* 2019:     */     public void addClass(PersistentClass persistentClass)
/* 2020:     */       throws DuplicateMappingException
/* 2021:     */     {
/* 2022:2556 */       Object old = Configuration.this.classes.put(persistentClass.getEntityName(), persistentClass);
/* 2023:2557 */       if (old != null) {
/* 2024:2558 */         throw new DuplicateMappingException("class/entity", persistentClass.getEntityName());
/* 2025:     */       }
/* 2026:     */     }
/* 2027:     */     
/* 2028:     */     public void addImport(String entityName, String rename)
/* 2029:     */       throws DuplicateMappingException
/* 2030:     */     {
/* 2031:2563 */       String existing = (String)Configuration.this.imports.put(rename, entityName);
/* 2032:2564 */       if (existing != null) {
/* 2033:2565 */         if (existing.equals(entityName)) {
/* 2034:2565 */           Configuration.LOG.duplicateImport(entityName, rename);
/* 2035:     */         } else {
/* 2036:2566 */           throw new DuplicateMappingException("duplicate import: " + rename + " refers to both " + entityName + " and " + existing + " (try using auto-import=\"false\")", "import", rename);
/* 2037:     */         }
/* 2038:     */       }
/* 2039:     */     }
/* 2040:     */     
/* 2041:     */     public org.hibernate.mapping.Collection getCollection(String role)
/* 2042:     */     {
/* 2043:2572 */       return (org.hibernate.mapping.Collection)Configuration.this.collections.get(role);
/* 2044:     */     }
/* 2045:     */     
/* 2046:     */     public Iterator<org.hibernate.mapping.Collection> iterateCollections()
/* 2047:     */     {
/* 2048:2576 */       return Configuration.this.collections.values().iterator();
/* 2049:     */     }
/* 2050:     */     
/* 2051:     */     public void addCollection(org.hibernate.mapping.Collection collection)
/* 2052:     */       throws DuplicateMappingException
/* 2053:     */     {
/* 2054:2580 */       Object old = Configuration.this.collections.put(collection.getRole(), collection);
/* 2055:2581 */       if (old != null) {
/* 2056:2582 */         throw new DuplicateMappingException("collection role", collection.getRole());
/* 2057:     */       }
/* 2058:     */     }
/* 2059:     */     
/* 2060:     */     public Table getTable(String schema, String catalog, String name)
/* 2061:     */     {
/* 2062:2587 */       String key = Table.qualify(catalog, schema, name);
/* 2063:2588 */       return (Table)Configuration.this.tables.get(key);
/* 2064:     */     }
/* 2065:     */     
/* 2066:     */     public Iterator<Table> iterateTables()
/* 2067:     */     {
/* 2068:2592 */       return Configuration.this.tables.values().iterator();
/* 2069:     */     }
/* 2070:     */     
/* 2071:     */     public Table addTable(String schema, String catalog, String name, String subselect, boolean isAbstract)
/* 2072:     */     {
/* 2073:2601 */       name = getObjectNameNormalizer().normalizeIdentifierQuoting(name);
/* 2074:2602 */       schema = getObjectNameNormalizer().normalizeIdentifierQuoting(schema);
/* 2075:2603 */       catalog = getObjectNameNormalizer().normalizeIdentifierQuoting(catalog);
/* 2076:     */       
/* 2077:2605 */       String key = subselect == null ? Table.qualify(catalog, schema, name) : subselect;
/* 2078:2606 */       Table table = (Table)Configuration.this.tables.get(key);
/* 2079:2608 */       if (table == null)
/* 2080:     */       {
/* 2081:2609 */         table = new Table();
/* 2082:2610 */         table.setAbstract(isAbstract);
/* 2083:2611 */         table.setName(name);
/* 2084:2612 */         table.setSchema(schema);
/* 2085:2613 */         table.setCatalog(catalog);
/* 2086:2614 */         table.setSubselect(subselect);
/* 2087:2615 */         Configuration.this.tables.put(key, table);
/* 2088:     */       }
/* 2089:2618 */       else if (!isAbstract)
/* 2090:     */       {
/* 2091:2619 */         table.setAbstract(false);
/* 2092:     */       }
/* 2093:2623 */       return table;
/* 2094:     */     }
/* 2095:     */     
/* 2096:     */     public Table addDenormalizedTable(String schema, String catalog, String name, boolean isAbstract, String subselect, Table includedTable)
/* 2097:     */       throws DuplicateMappingException
/* 2098:     */     {
/* 2099:2633 */       name = getObjectNameNormalizer().normalizeIdentifierQuoting(name);
/* 2100:2634 */       schema = getObjectNameNormalizer().normalizeIdentifierQuoting(schema);
/* 2101:2635 */       catalog = getObjectNameNormalizer().normalizeIdentifierQuoting(catalog);
/* 2102:     */       
/* 2103:2637 */       String key = subselect == null ? Table.qualify(catalog, schema, name) : subselect;
/* 2104:2638 */       if (Configuration.this.tables.containsKey(key)) {
/* 2105:2639 */         throw new DuplicateMappingException("table", name);
/* 2106:     */       }
/* 2107:2642 */       Table table = new DenormalizedTable(includedTable);
/* 2108:2643 */       table.setAbstract(isAbstract);
/* 2109:2644 */       table.setName(name);
/* 2110:2645 */       table.setSchema(schema);
/* 2111:2646 */       table.setCatalog(catalog);
/* 2112:2647 */       table.setSubselect(subselect);
/* 2113:     */       
/* 2114:2649 */       Configuration.this.tables.put(key, table);
/* 2115:2650 */       return table;
/* 2116:     */     }
/* 2117:     */     
/* 2118:     */     public NamedQueryDefinition getQuery(String name)
/* 2119:     */     {
/* 2120:2654 */       return (NamedQueryDefinition)Configuration.this.namedQueries.get(name);
/* 2121:     */     }
/* 2122:     */     
/* 2123:     */     public void addQuery(String name, NamedQueryDefinition query)
/* 2124:     */       throws DuplicateMappingException
/* 2125:     */     {
/* 2126:2658 */       if (!Configuration.this.defaultNamedQueryNames.contains(name)) {
/* 2127:2659 */         applyQuery(name, query);
/* 2128:     */       }
/* 2129:     */     }
/* 2130:     */     
/* 2131:     */     private void applyQuery(String name, NamedQueryDefinition query)
/* 2132:     */     {
/* 2133:2664 */       checkQueryName(name);
/* 2134:2665 */       Configuration.this.namedQueries.put(name.intern(), query);
/* 2135:     */     }
/* 2136:     */     
/* 2137:     */     private void checkQueryName(String name)
/* 2138:     */       throws DuplicateMappingException
/* 2139:     */     {
/* 2140:2669 */       if ((Configuration.this.namedQueries.containsKey(name)) || (Configuration.this.namedSqlQueries.containsKey(name))) {
/* 2141:2670 */         throw new DuplicateMappingException("query", name);
/* 2142:     */       }
/* 2143:     */     }
/* 2144:     */     
/* 2145:     */     public void addDefaultQuery(String name, NamedQueryDefinition query)
/* 2146:     */     {
/* 2147:2675 */       applyQuery(name, query);
/* 2148:2676 */       Configuration.this.defaultNamedQueryNames.add(name);
/* 2149:     */     }
/* 2150:     */     
/* 2151:     */     public NamedSQLQueryDefinition getSQLQuery(String name)
/* 2152:     */     {
/* 2153:2680 */       return (NamedSQLQueryDefinition)Configuration.this.namedSqlQueries.get(name);
/* 2154:     */     }
/* 2155:     */     
/* 2156:     */     public void addSQLQuery(String name, NamedSQLQueryDefinition query)
/* 2157:     */       throws DuplicateMappingException
/* 2158:     */     {
/* 2159:2684 */       if (!Configuration.this.defaultNamedNativeQueryNames.contains(name)) {
/* 2160:2685 */         applySQLQuery(name, query);
/* 2161:     */       }
/* 2162:     */     }
/* 2163:     */     
/* 2164:     */     private void applySQLQuery(String name, NamedSQLQueryDefinition query)
/* 2165:     */       throws DuplicateMappingException
/* 2166:     */     {
/* 2167:2690 */       checkQueryName(name);
/* 2168:2691 */       Configuration.this.namedSqlQueries.put(name.intern(), query);
/* 2169:     */     }
/* 2170:     */     
/* 2171:     */     public void addDefaultSQLQuery(String name, NamedSQLQueryDefinition query)
/* 2172:     */     {
/* 2173:2695 */       applySQLQuery(name, query);
/* 2174:2696 */       Configuration.this.defaultNamedNativeQueryNames.add(name);
/* 2175:     */     }
/* 2176:     */     
/* 2177:     */     public ResultSetMappingDefinition getResultSetMapping(String name)
/* 2178:     */     {
/* 2179:2700 */       return (ResultSetMappingDefinition)Configuration.this.sqlResultSetMappings.get(name);
/* 2180:     */     }
/* 2181:     */     
/* 2182:     */     public void addResultSetMapping(ResultSetMappingDefinition sqlResultSetMapping)
/* 2183:     */       throws DuplicateMappingException
/* 2184:     */     {
/* 2185:2704 */       if (!Configuration.this.defaultSqlResultSetMappingNames.contains(sqlResultSetMapping.getName())) {
/* 2186:2705 */         applyResultSetMapping(sqlResultSetMapping);
/* 2187:     */       }
/* 2188:     */     }
/* 2189:     */     
/* 2190:     */     public void applyResultSetMapping(ResultSetMappingDefinition sqlResultSetMapping)
/* 2191:     */       throws DuplicateMappingException
/* 2192:     */     {
/* 2193:2710 */       Object old = Configuration.this.sqlResultSetMappings.put(sqlResultSetMapping.getName(), sqlResultSetMapping);
/* 2194:2711 */       if (old != null) {
/* 2195:2712 */         throw new DuplicateMappingException("resultSet", sqlResultSetMapping.getName());
/* 2196:     */       }
/* 2197:     */     }
/* 2198:     */     
/* 2199:     */     public void addDefaultResultSetMapping(ResultSetMappingDefinition definition)
/* 2200:     */     {
/* 2201:2717 */       String name = definition.getName();
/* 2202:2718 */       if ((!Configuration.this.defaultSqlResultSetMappingNames.contains(name)) && (getResultSetMapping(name) != null)) {
/* 2203:2719 */         removeResultSetMapping(name);
/* 2204:     */       }
/* 2205:2721 */       applyResultSetMapping(definition);
/* 2206:2722 */       Configuration.this.defaultSqlResultSetMappingNames.add(name);
/* 2207:     */     }
/* 2208:     */     
/* 2209:     */     protected void removeResultSetMapping(String name)
/* 2210:     */     {
/* 2211:2726 */       Configuration.this.sqlResultSetMappings.remove(name);
/* 2212:     */     }
/* 2213:     */     
/* 2214:     */     public TypeDef getTypeDef(String typeName)
/* 2215:     */     {
/* 2216:2730 */       return (TypeDef)Configuration.this.typeDefs.get(typeName);
/* 2217:     */     }
/* 2218:     */     
/* 2219:     */     public void addTypeDef(String typeName, String typeClass, Properties paramMap)
/* 2220:     */     {
/* 2221:2734 */       TypeDef def = new TypeDef(typeClass, paramMap);
/* 2222:2735 */       Configuration.this.typeDefs.put(typeName, def);
/* 2223:2736 */       Configuration.LOG.debugf("Added %s with class %s", typeName, typeClass);
/* 2224:     */     }
/* 2225:     */     
/* 2226:     */     public Map getFilterDefinitions()
/* 2227:     */     {
/* 2228:2740 */       return Configuration.this.filterDefinitions;
/* 2229:     */     }
/* 2230:     */     
/* 2231:     */     public FilterDefinition getFilterDefinition(String name)
/* 2232:     */     {
/* 2233:2744 */       return (FilterDefinition)Configuration.this.filterDefinitions.get(name);
/* 2234:     */     }
/* 2235:     */     
/* 2236:     */     public void addFilterDefinition(FilterDefinition definition)
/* 2237:     */     {
/* 2238:2748 */       Configuration.this.filterDefinitions.put(definition.getFilterName(), definition);
/* 2239:     */     }
/* 2240:     */     
/* 2241:     */     public FetchProfile findOrCreateFetchProfile(String name, MetadataSource source)
/* 2242:     */     {
/* 2243:2752 */       FetchProfile profile = (FetchProfile)Configuration.this.fetchProfiles.get(name);
/* 2244:2753 */       if (profile == null)
/* 2245:     */       {
/* 2246:2754 */         profile = new FetchProfile(name, source);
/* 2247:2755 */         Configuration.this.fetchProfiles.put(name, profile);
/* 2248:     */       }
/* 2249:2757 */       return profile;
/* 2250:     */     }
/* 2251:     */     
/* 2252:     */     public Iterator<AuxiliaryDatabaseObject> iterateAuxliaryDatabaseObjects()
/* 2253:     */     {
/* 2254:2761 */       return iterateAuxiliaryDatabaseObjects();
/* 2255:     */     }
/* 2256:     */     
/* 2257:     */     public Iterator<AuxiliaryDatabaseObject> iterateAuxiliaryDatabaseObjects()
/* 2258:     */     {
/* 2259:2765 */       return Configuration.this.auxiliaryDatabaseObjects.iterator();
/* 2260:     */     }
/* 2261:     */     
/* 2262:     */     public ListIterator<AuxiliaryDatabaseObject> iterateAuxliaryDatabaseObjectsInReverse()
/* 2263:     */     {
/* 2264:2769 */       return iterateAuxiliaryDatabaseObjectsInReverse();
/* 2265:     */     }
/* 2266:     */     
/* 2267:     */     public ListIterator<AuxiliaryDatabaseObject> iterateAuxiliaryDatabaseObjectsInReverse()
/* 2268:     */     {
/* 2269:2773 */       return Configuration.this.auxiliaryDatabaseObjects.listIterator(Configuration.this.auxiliaryDatabaseObjects.size());
/* 2270:     */     }
/* 2271:     */     
/* 2272:     */     public void addAuxiliaryDatabaseObject(AuxiliaryDatabaseObject auxiliaryDatabaseObject)
/* 2273:     */     {
/* 2274:2777 */       Configuration.this.auxiliaryDatabaseObjects.add(auxiliaryDatabaseObject);
/* 2275:     */     }
/* 2276:     */     
/* 2277:     */     private class TableDescription
/* 2278:     */       implements Serializable
/* 2279:     */     {
/* 2280:     */       final String logicalName;
/* 2281:     */       final Table denormalizedSupertable;
/* 2282:     */       
/* 2283:     */       TableDescription(String logicalName, Table denormalizedSupertable)
/* 2284:     */       {
/* 2285:2788 */         this.logicalName = logicalName;
/* 2286:2789 */         this.denormalizedSupertable = denormalizedSupertable;
/* 2287:     */       }
/* 2288:     */     }
/* 2289:     */     
/* 2290:     */     public String getLogicalTableName(Table table)
/* 2291:     */       throws MappingException
/* 2292:     */     {
/* 2293:2794 */       return getLogicalTableName(table.getQuotedSchema(), table.getCatalog(), table.getQuotedName());
/* 2294:     */     }
/* 2295:     */     
/* 2296:     */     private String getLogicalTableName(String schema, String catalog, String physicalName)
/* 2297:     */       throws MappingException
/* 2298:     */     {
/* 2299:2798 */       String key = buildTableNameKey(schema, catalog, physicalName);
/* 2300:2799 */       TableDescription descriptor = (TableDescription)Configuration.this.tableNameBinding.get(key);
/* 2301:2800 */       if (descriptor == null) {
/* 2302:2801 */         throw new MappingException("Unable to find physical table: " + physicalName);
/* 2303:     */       }
/* 2304:2803 */       return descriptor.logicalName;
/* 2305:     */     }
/* 2306:     */     
/* 2307:     */     public void addTableBinding(String schema, String catalog, String logicalName, String physicalName, Table denormalizedSuperTable)
/* 2308:     */       throws DuplicateMappingException
/* 2309:     */     {
/* 2310:2812 */       String key = buildTableNameKey(schema, catalog, physicalName);
/* 2311:2813 */       TableDescription tableDescription = new TableDescription(logicalName, denormalizedSuperTable);
/* 2312:2814 */       TableDescription oldDescriptor = (TableDescription)Configuration.this.tableNameBinding.put(key, tableDescription);
/* 2313:2815 */       if ((oldDescriptor != null) && (!oldDescriptor.logicalName.equals(logicalName))) {
/* 2314:2817 */         throw new DuplicateMappingException("Same physical table name [" + physicalName + "] references several logical table names: [" + oldDescriptor.logicalName + "], [" + logicalName + ']', "table", physicalName);
/* 2315:     */       }
/* 2316:     */     }
/* 2317:     */     
/* 2318:     */     private String buildTableNameKey(String schema, String catalog, String finalName)
/* 2319:     */     {
/* 2320:2827 */       StringBuffer keyBuilder = new StringBuffer();
/* 2321:2828 */       if (schema != null) {
/* 2322:2828 */         keyBuilder.append(schema);
/* 2323:     */       }
/* 2324:2829 */       keyBuilder.append(".");
/* 2325:2830 */       if (catalog != null) {
/* 2326:2830 */         keyBuilder.append(catalog);
/* 2327:     */       }
/* 2328:2831 */       keyBuilder.append(".");
/* 2329:2832 */       keyBuilder.append(finalName);
/* 2330:2833 */       return keyBuilder.toString();
/* 2331:     */     }
/* 2332:     */     
/* 2333:     */     private class TableColumnNameBinding
/* 2334:     */       implements Serializable
/* 2335:     */     {
/* 2336:     */       private final String tableName;
/* 2337:2843 */       private Map logicalToPhysical = new HashMap();
/* 2338:2844 */       private Map physicalToLogical = new HashMap();
/* 2339:     */       
/* 2340:     */       private TableColumnNameBinding(String tableName)
/* 2341:     */       {
/* 2342:2847 */         this.tableName = tableName;
/* 2343:     */       }
/* 2344:     */       
/* 2345:     */       public void addBinding(String logicalName, Column physicalColumn)
/* 2346:     */       {
/* 2347:2851 */         bindLogicalToPhysical(logicalName, physicalColumn);
/* 2348:2852 */         bindPhysicalToLogical(logicalName, physicalColumn);
/* 2349:     */       }
/* 2350:     */       
/* 2351:     */       private void bindLogicalToPhysical(String logicalName, Column physicalColumn)
/* 2352:     */         throws DuplicateMappingException
/* 2353:     */       {
/* 2354:2856 */         String logicalKey = logicalName.toLowerCase();
/* 2355:2857 */         String physicalName = physicalColumn.getQuotedName();
/* 2356:2858 */         String existingPhysicalName = (String)this.logicalToPhysical.put(logicalKey, physicalName);
/* 2357:2859 */         if (existingPhysicalName != null)
/* 2358:     */         {
/* 2359:2860 */           boolean areSamePhysicalColumn = physicalColumn.isQuoted() ? existingPhysicalName.equals(physicalName) : existingPhysicalName.equalsIgnoreCase(physicalName);
/* 2360:2863 */           if (!areSamePhysicalColumn) {
/* 2361:2864 */             throw new DuplicateMappingException(" Table [" + this.tableName + "] contains logical column name [" + logicalName + "] referenced by multiple physical column names: [" + existingPhysicalName + "], [" + physicalName + "]", "column-binding", this.tableName + "." + logicalName);
/* 2362:     */           }
/* 2363:     */         }
/* 2364:     */       }
/* 2365:     */       
/* 2366:     */       private void bindPhysicalToLogical(String logicalName, Column physicalColumn)
/* 2367:     */         throws DuplicateMappingException
/* 2368:     */       {
/* 2369:2876 */         String physicalName = physicalColumn.getQuotedName();
/* 2370:2877 */         String existingLogicalName = (String)this.physicalToLogical.put(physicalName, logicalName);
/* 2371:2878 */         if ((existingLogicalName != null) && (!existingLogicalName.equals(logicalName))) {
/* 2372:2879 */           throw new DuplicateMappingException(" Table [" + this.tableName + "] contains phyical column name [" + physicalName + "] represented by different logical column names: [" + existingLogicalName + "], [" + logicalName + "]", "column-binding", this.tableName + "." + physicalName);
/* 2373:     */         }
/* 2374:     */       }
/* 2375:     */     }
/* 2376:     */     
/* 2377:     */     public void addColumnBinding(String logicalName, Column physicalColumn, Table table)
/* 2378:     */       throws DuplicateMappingException
/* 2379:     */     {
/* 2380:2891 */       TableColumnNameBinding binding = (TableColumnNameBinding)Configuration.this.columnNameBindingPerTable.get(table);
/* 2381:2892 */       if (binding == null)
/* 2382:     */       {
/* 2383:2893 */         binding = new TableColumnNameBinding(table.getName(), null);
/* 2384:2894 */         Configuration.this.columnNameBindingPerTable.put(table, binding);
/* 2385:     */       }
/* 2386:2896 */       binding.addBinding(logicalName, physicalColumn);
/* 2387:     */     }
/* 2388:     */     
/* 2389:     */     public String getPhysicalColumnName(String logicalName, Table table)
/* 2390:     */       throws MappingException
/* 2391:     */     {
/* 2392:2900 */       logicalName = logicalName.toLowerCase();
/* 2393:2901 */       String finalName = null;
/* 2394:2902 */       Table currentTable = table;
/* 2395:     */       do
/* 2396:     */       {
/* 2397:2904 */         TableColumnNameBinding binding = (TableColumnNameBinding)Configuration.this.columnNameBindingPerTable.get(currentTable);
/* 2398:2905 */         if (binding != null) {
/* 2399:2906 */           finalName = (String)binding.logicalToPhysical.get(logicalName);
/* 2400:     */         }
/* 2401:2908 */         String key = buildTableNameKey(currentTable.getQuotedSchema(), currentTable.getCatalog(), currentTable.getQuotedName());
/* 2402:     */         
/* 2403:     */ 
/* 2404:2911 */         TableDescription description = (TableDescription)Configuration.this.tableNameBinding.get(key);
/* 2405:2912 */         if (description != null) {
/* 2406:2913 */           currentTable = description.denormalizedSupertable;
/* 2407:     */         } else {
/* 2408:2916 */           currentTable = null;
/* 2409:     */         }
/* 2410:2918 */       } while ((finalName == null) && (currentTable != null));
/* 2411:2920 */       if (finalName == null) {
/* 2412:2921 */         throw new MappingException("Unable to find column with logical name " + logicalName + " in table " + table.getName());
/* 2413:     */       }
/* 2414:2925 */       return finalName;
/* 2415:     */     }
/* 2416:     */     
/* 2417:     */     public String getLogicalColumnName(String physicalName, Table table)
/* 2418:     */       throws MappingException
/* 2419:     */     {
/* 2420:2929 */       String logical = null;
/* 2421:2930 */       Table currentTable = table;
/* 2422:2931 */       TableDescription description = null;
/* 2423:     */       do
/* 2424:     */       {
/* 2425:2933 */         TableColumnNameBinding binding = (TableColumnNameBinding)Configuration.this.columnNameBindingPerTable.get(currentTable);
/* 2426:2934 */         if (binding != null) {
/* 2427:2935 */           logical = (String)binding.physicalToLogical.get(physicalName);
/* 2428:     */         }
/* 2429:2937 */         String key = buildTableNameKey(currentTable.getQuotedSchema(), currentTable.getCatalog(), currentTable.getQuotedName());
/* 2430:     */         
/* 2431:     */ 
/* 2432:2940 */         description = (TableDescription)Configuration.this.tableNameBinding.get(key);
/* 2433:2941 */         if (description != null) {
/* 2434:2942 */           currentTable = description.denormalizedSupertable;
/* 2435:     */         } else {
/* 2436:2945 */           currentTable = null;
/* 2437:     */         }
/* 2438:2948 */       } while ((logical == null) && (currentTable != null) && (description != null));
/* 2439:2949 */       if (logical == null) {
/* 2440:2950 */         throw new MappingException("Unable to find logical column name from physical name " + physicalName + " in table " + table.getName());
/* 2441:     */       }
/* 2442:2955 */       return logical;
/* 2443:     */     }
/* 2444:     */     
/* 2445:     */     public void addSecondPass(SecondPass sp)
/* 2446:     */     {
/* 2447:2959 */       addSecondPass(sp, false);
/* 2448:     */     }
/* 2449:     */     
/* 2450:     */     public void addSecondPass(SecondPass sp, boolean onTopOfTheQueue)
/* 2451:     */     {
/* 2452:2963 */       if (onTopOfTheQueue) {
/* 2453:2964 */         Configuration.this.secondPasses.add(0, sp);
/* 2454:     */       } else {
/* 2455:2967 */         Configuration.this.secondPasses.add(sp);
/* 2456:     */       }
/* 2457:     */     }
/* 2458:     */     
/* 2459:     */     public void addPropertyReference(String referencedClass, String propertyName)
/* 2460:     */     {
/* 2461:2972 */       Configuration.this.propertyReferences.add(new Mappings.PropertyReference(referencedClass, propertyName, false));
/* 2462:     */     }
/* 2463:     */     
/* 2464:     */     public void addUniquePropertyReference(String referencedClass, String propertyName)
/* 2465:     */     {
/* 2466:2976 */       Configuration.this.propertyReferences.add(new Mappings.PropertyReference(referencedClass, propertyName, true));
/* 2467:     */     }
/* 2468:     */     
/* 2469:     */     public void addToExtendsQueue(ExtendsQueueEntry entry)
/* 2470:     */     {
/* 2471:2980 */       Configuration.this.extendsQueue.put(entry, null);
/* 2472:     */     }
/* 2473:     */     
/* 2474:     */     public MutableIdentifierGeneratorFactory getIdentifierGeneratorFactory()
/* 2475:     */     {
/* 2476:2984 */       return Configuration.this.identifierGeneratorFactory;
/* 2477:     */     }
/* 2478:     */     
/* 2479:     */     public void addMappedSuperclass(Class type, org.hibernate.mapping.MappedSuperclass mappedSuperclass)
/* 2480:     */     {
/* 2481:2988 */       Configuration.this.mappedSuperClasses.put(type, mappedSuperclass);
/* 2482:     */     }
/* 2483:     */     
/* 2484:     */     public org.hibernate.mapping.MappedSuperclass getMappedSuperclass(Class type)
/* 2485:     */     {
/* 2486:2992 */       return (org.hibernate.mapping.MappedSuperclass)Configuration.this.mappedSuperClasses.get(type);
/* 2487:     */     }
/* 2488:     */     
/* 2489:     */     public ObjectNameNormalizer getObjectNameNormalizer()
/* 2490:     */     {
/* 2491:2996 */       return Configuration.this.normalizer;
/* 2492:     */     }
/* 2493:     */     
/* 2494:     */     public Properties getConfigurationProperties()
/* 2495:     */     {
/* 2496:3000 */       return Configuration.this.properties;
/* 2497:     */     }
/* 2498:     */     
/* 2499:     */     public void addDefaultGenerator(IdGenerator generator)
/* 2500:     */     {
/* 2501:3007 */       addGenerator(generator);
/* 2502:3008 */       Configuration.this.defaultNamedGenerators.add(generator.getName());
/* 2503:     */     }
/* 2504:     */     
/* 2505:     */     public boolean isInSecondPass()
/* 2506:     */     {
/* 2507:3012 */       return Configuration.this.inSecondPass;
/* 2508:     */     }
/* 2509:     */     
/* 2510:     */     public PropertyData getPropertyAnnotatedWithMapsId(XClass entityType, String propertyName)
/* 2511:     */     {
/* 2512:3016 */       Map<String, PropertyData> map = (Map)Configuration.this.propertiesAnnotatedWithMapsId.get(entityType);
/* 2513:3017 */       return map == null ? null : (PropertyData)map.get(propertyName);
/* 2514:     */     }
/* 2515:     */     
/* 2516:     */     public void addPropertyAnnotatedWithMapsId(XClass entityType, PropertyData property)
/* 2517:     */     {
/* 2518:3021 */       Map<String, PropertyData> map = (Map)Configuration.this.propertiesAnnotatedWithMapsId.get(entityType);
/* 2519:3022 */       if (map == null)
/* 2520:     */       {
/* 2521:3023 */         map = new HashMap();
/* 2522:3024 */         Configuration.this.propertiesAnnotatedWithMapsId.put(entityType, map);
/* 2523:     */       }
/* 2524:3026 */       map.put(((MapsId)property.getProperty().getAnnotation(MapsId.class)).value(), property);
/* 2525:     */     }
/* 2526:     */     
/* 2527:     */     public boolean isSpecjProprietarySyntaxEnabled()
/* 2528:     */     {
/* 2529:3030 */       return Configuration.this.specjProprietarySyntaxEnabled;
/* 2530:     */     }
/* 2531:     */     
/* 2532:     */     public void addPropertyAnnotatedWithMapsIdSpecj(XClass entityType, PropertyData property, String mapsIdValue)
/* 2533:     */     {
/* 2534:3034 */       Map<String, PropertyData> map = (Map)Configuration.this.propertiesAnnotatedWithMapsId.get(entityType);
/* 2535:3035 */       if (map == null)
/* 2536:     */       {
/* 2537:3036 */         map = new HashMap();
/* 2538:3037 */         Configuration.this.propertiesAnnotatedWithMapsId.put(entityType, map);
/* 2539:     */       }
/* 2540:3039 */       map.put(mapsIdValue, property);
/* 2541:     */     }
/* 2542:     */     
/* 2543:     */     public PropertyData getPropertyAnnotatedWithIdAndToOne(XClass entityType, String propertyName)
/* 2544:     */     {
/* 2545:3043 */       Map<String, PropertyData> map = (Map)Configuration.this.propertiesAnnotatedWithIdAndToOne.get(entityType);
/* 2546:3044 */       return map == null ? null : (PropertyData)map.get(propertyName);
/* 2547:     */     }
/* 2548:     */     
/* 2549:     */     public void addToOneAndIdProperty(XClass entityType, PropertyData property)
/* 2550:     */     {
/* 2551:3048 */       Map<String, PropertyData> map = (Map)Configuration.this.propertiesAnnotatedWithIdAndToOne.get(entityType);
/* 2552:3049 */       if (map == null)
/* 2553:     */       {
/* 2554:3050 */         map = new HashMap();
/* 2555:3051 */         Configuration.this.propertiesAnnotatedWithIdAndToOne.put(entityType, map);
/* 2556:     */       }
/* 2557:3053 */       map.put(property.getPropertyName(), property);
/* 2558:     */     }
/* 2559:     */     
/* 2560:     */     public boolean useNewGeneratorMappings()
/* 2561:     */     {
/* 2562:3058 */       if (this.useNewGeneratorMappings == null)
/* 2563:     */       {
/* 2564:3059 */         String booleanName = getConfigurationProperties().getProperty("hibernate.id.new_generator_mappings");
/* 2565:3060 */         this.useNewGeneratorMappings = Boolean.valueOf(booleanName);
/* 2566:     */       }
/* 2567:3062 */       return this.useNewGeneratorMappings.booleanValue();
/* 2568:     */     }
/* 2569:     */     
/* 2570:     */     public IdGenerator getGenerator(String name)
/* 2571:     */     {
/* 2572:3066 */       return getGenerator(name, null);
/* 2573:     */     }
/* 2574:     */     
/* 2575:     */     public IdGenerator getGenerator(String name, Map<String, IdGenerator> localGenerators)
/* 2576:     */     {
/* 2577:3070 */       if (localGenerators != null)
/* 2578:     */       {
/* 2579:3071 */         IdGenerator result = (IdGenerator)localGenerators.get(name);
/* 2580:3072 */         if (result != null) {
/* 2581:3073 */           return result;
/* 2582:     */         }
/* 2583:     */       }
/* 2584:3076 */       return (IdGenerator)Configuration.this.namedGenerators.get(name);
/* 2585:     */     }
/* 2586:     */     
/* 2587:     */     public void addGenerator(IdGenerator generator)
/* 2588:     */     {
/* 2589:3080 */       if (!Configuration.this.defaultNamedGenerators.contains(generator.getName()))
/* 2590:     */       {
/* 2591:3081 */         IdGenerator old = (IdGenerator)Configuration.this.namedGenerators.put(generator.getName(), generator);
/* 2592:3082 */         if (old != null) {
/* 2593:3083 */           Configuration.LOG.duplicateGeneratorName(old.getName());
/* 2594:     */         }
/* 2595:     */       }
/* 2596:     */     }
/* 2597:     */     
/* 2598:     */     public void addGeneratorTable(String name, Properties params)
/* 2599:     */     {
/* 2600:3089 */       Object old = Configuration.this.generatorTables.put(name, params);
/* 2601:3090 */       if (old != null) {
/* 2602:3091 */         Configuration.LOG.duplicateGeneratorTable(name);
/* 2603:     */       }
/* 2604:     */     }
/* 2605:     */     
/* 2606:     */     public Properties getGeneratorTableProperties(String name, Map<String, Properties> localGeneratorTables)
/* 2607:     */     {
/* 2608:3096 */       if (localGeneratorTables != null)
/* 2609:     */       {
/* 2610:3097 */         Properties result = (Properties)localGeneratorTables.get(name);
/* 2611:3098 */         if (result != null) {
/* 2612:3099 */           return result;
/* 2613:     */         }
/* 2614:     */       }
/* 2615:3102 */       return (Properties)Configuration.this.generatorTables.get(name);
/* 2616:     */     }
/* 2617:     */     
/* 2618:     */     public Map<String, Join> getJoins(String entityName)
/* 2619:     */     {
/* 2620:3106 */       return (Map)Configuration.this.joins.get(entityName);
/* 2621:     */     }
/* 2622:     */     
/* 2623:     */     public void addJoins(PersistentClass persistentClass, Map<String, Join> joins)
/* 2624:     */     {
/* 2625:3110 */       Object old = Configuration.this.joins.put(persistentClass.getEntityName(), joins);
/* 2626:3111 */       if (old != null) {
/* 2627:3112 */         Configuration.LOG.duplicateJoins(persistentClass.getEntityName());
/* 2628:     */       }
/* 2629:     */     }
/* 2630:     */     
/* 2631:     */     public AnnotatedClassType getClassType(XClass clazz)
/* 2632:     */     {
/* 2633:3117 */       AnnotatedClassType type = (AnnotatedClassType)Configuration.this.classTypes.get(clazz.getName());
/* 2634:3118 */       if (type == null) {
/* 2635:3119 */         return addClassType(clazz);
/* 2636:     */       }
/* 2637:3122 */       return type;
/* 2638:     */     }
/* 2639:     */     
/* 2640:     */     public AnnotatedClassType addClassType(XClass clazz)
/* 2641:     */     {
/* 2642:     */       AnnotatedClassType type;
/* 2643:     */       AnnotatedClassType type;
/* 2644:3130 */       if (clazz.isAnnotationPresent(Entity.class))
/* 2645:     */       {
/* 2646:3131 */         type = AnnotatedClassType.ENTITY;
/* 2647:     */       }
/* 2648:     */       else
/* 2649:     */       {
/* 2650:     */         AnnotatedClassType type;
/* 2651:3133 */         if (clazz.isAnnotationPresent(Embeddable.class))
/* 2652:     */         {
/* 2653:3134 */           type = AnnotatedClassType.EMBEDDABLE;
/* 2654:     */         }
/* 2655:     */         else
/* 2656:     */         {
/* 2657:     */           AnnotatedClassType type;
/* 2658:3136 */           if (clazz.isAnnotationPresent(javax.persistence.MappedSuperclass.class)) {
/* 2659:3137 */             type = AnnotatedClassType.EMBEDDABLE_SUPERCLASS;
/* 2660:     */           } else {
/* 2661:3140 */             type = AnnotatedClassType.NONE;
/* 2662:     */           }
/* 2663:     */         }
/* 2664:     */       }
/* 2665:3142 */       Configuration.this.classTypes.put(clazz.getName(), type);
/* 2666:3143 */       return type;
/* 2667:     */     }
/* 2668:     */     
/* 2669:     */     public Map<Table, List<String[]>> getTableUniqueConstraints()
/* 2670:     */     {
/* 2671:3150 */       Map<Table, List<String[]>> deprecatedStructure = new HashMap(CollectionHelper.determineProperSizing(getUniqueConstraintHoldersByTable()), 0.75F);
/* 2672:3154 */       for (Map.Entry<Table, List<UniqueConstraintHolder>> entry : getUniqueConstraintHoldersByTable().entrySet())
/* 2673:     */       {
/* 2674:3155 */         columnsPerConstraint = new ArrayList(CollectionHelper.determineProperSizing(((List)entry.getValue()).size()));
/* 2675:     */         
/* 2676:     */ 
/* 2677:3158 */         deprecatedStructure.put(entry.getKey(), columnsPerConstraint);
/* 2678:3159 */         for (UniqueConstraintHolder holder : (List)entry.getValue()) {
/* 2679:3160 */           columnsPerConstraint.add(holder.getColumns());
/* 2680:     */         }
/* 2681:     */       }
/* 2682:     */       List<String[]> columnsPerConstraint;
/* 2683:3163 */       return deprecatedStructure;
/* 2684:     */     }
/* 2685:     */     
/* 2686:     */     public Map<Table, List<UniqueConstraintHolder>> getUniqueConstraintHoldersByTable()
/* 2687:     */     {
/* 2688:3167 */       return Configuration.this.uniqueConstraintHoldersByTable;
/* 2689:     */     }
/* 2690:     */     
/* 2691:     */     public void addUniqueConstraints(Table table, List uniqueConstraints)
/* 2692:     */     {
/* 2693:3172 */       List<UniqueConstraintHolder> constraintHolders = new ArrayList(CollectionHelper.determineProperSizing(uniqueConstraints.size()));
/* 2694:     */       
/* 2695:     */ 
/* 2696:     */ 
/* 2697:3176 */       int keyNameBase = determineCurrentNumberOfUniqueConstraintHolders(table);
/* 2698:3177 */       for (String[] columns : uniqueConstraints)
/* 2699:     */       {
/* 2700:3178 */         String keyName = "key" + keyNameBase++;
/* 2701:3179 */         constraintHolders.add(new UniqueConstraintHolder().setName(keyName).setColumns(columns));
/* 2702:     */       }
/* 2703:3183 */       addUniqueConstraintHolders(table, constraintHolders);
/* 2704:     */     }
/* 2705:     */     
/* 2706:     */     private int determineCurrentNumberOfUniqueConstraintHolders(Table table)
/* 2707:     */     {
/* 2708:3187 */       List currentHolders = (List)getUniqueConstraintHoldersByTable().get(table);
/* 2709:3188 */       return currentHolders == null ? 0 : currentHolders.size();
/* 2710:     */     }
/* 2711:     */     
/* 2712:     */     public void addUniqueConstraintHolders(Table table, List<UniqueConstraintHolder> uniqueConstraintHolders)
/* 2713:     */     {
/* 2714:3194 */       List<UniqueConstraintHolder> holderList = (List)getUniqueConstraintHoldersByTable().get(table);
/* 2715:3195 */       if (holderList == null)
/* 2716:     */       {
/* 2717:3196 */         holderList = new ArrayList();
/* 2718:3197 */         getUniqueConstraintHoldersByTable().put(table, holderList);
/* 2719:     */       }
/* 2720:3199 */       holderList.addAll(uniqueConstraintHolders);
/* 2721:     */     }
/* 2722:     */     
/* 2723:     */     public void addMappedBy(String entityName, String propertyName, String inversePropertyName)
/* 2724:     */     {
/* 2725:3203 */       Configuration.this.mappedByResolver.put(entityName + "." + propertyName, inversePropertyName);
/* 2726:     */     }
/* 2727:     */     
/* 2728:     */     public String getFromMappedBy(String entityName, String propertyName)
/* 2729:     */     {
/* 2730:3207 */       return (String)Configuration.this.mappedByResolver.get(entityName + "." + propertyName);
/* 2731:     */     }
/* 2732:     */     
/* 2733:     */     public void addPropertyReferencedAssociation(String entityName, String propertyName, String propertyRef)
/* 2734:     */     {
/* 2735:3211 */       Configuration.this.propertyRefResolver.put(entityName + "." + propertyName, propertyRef);
/* 2736:     */     }
/* 2737:     */     
/* 2738:     */     public String getPropertyReferencedAssociation(String entityName, String propertyName)
/* 2739:     */     {
/* 2740:3215 */       return (String)Configuration.this.propertyRefResolver.get(entityName + "." + propertyName);
/* 2741:     */     }
/* 2742:     */     
/* 2743:     */     public ReflectionManager getReflectionManager()
/* 2744:     */     {
/* 2745:3219 */       return Configuration.this.reflectionManager;
/* 2746:     */     }
/* 2747:     */     
/* 2748:     */     public Map getClasses()
/* 2749:     */     {
/* 2750:3223 */       return Configuration.this.classes;
/* 2751:     */     }
/* 2752:     */     
/* 2753:     */     public void addAnyMetaDef(AnyMetaDef defAnn)
/* 2754:     */       throws AnnotationException
/* 2755:     */     {
/* 2756:3227 */       if (Configuration.this.anyMetaDefs.containsKey(defAnn.name())) {
/* 2757:3228 */         throw new AnnotationException("Two @AnyMetaDef with the same name defined: " + defAnn.name());
/* 2758:     */       }
/* 2759:3230 */       Configuration.this.anyMetaDefs.put(defAnn.name(), defAnn);
/* 2760:     */     }
/* 2761:     */     
/* 2762:     */     public AnyMetaDef getAnyMetaDef(String name)
/* 2763:     */     {
/* 2764:3234 */       return (AnyMetaDef)Configuration.this.anyMetaDefs.get(name);
/* 2765:     */     }
/* 2766:     */   }
/* 2767:     */   
/* 2768:3238 */   final ObjectNameNormalizer normalizer = new ObjectNameNormalizerImpl();
/* 2769:     */   
/* 2770:     */   final class ObjectNameNormalizerImpl
/* 2771:     */     extends ObjectNameNormalizer
/* 2772:     */     implements Serializable
/* 2773:     */   {
/* 2774:     */     ObjectNameNormalizerImpl() {}
/* 2775:     */     
/* 2776:     */     public boolean isUseQuotedIdentifiersGlobally()
/* 2777:     */     {
/* 2778:3244 */       String setting = (String)Configuration.this.properties.get("hibernate.globally_quoted_identifiers");
/* 2779:3245 */       return (setting != null) && (Boolean.valueOf(setting).booleanValue());
/* 2780:     */     }
/* 2781:     */     
/* 2782:     */     public NamingStrategy getNamingStrategy()
/* 2783:     */     {
/* 2784:3249 */       return Configuration.this.namingStrategy;
/* 2785:     */     }
/* 2786:     */   }
/* 2787:     */   
/* 2788:     */   protected class MetadataSourceQueue
/* 2789:     */     implements Serializable
/* 2790:     */   {
/* 2791:3254 */     private LinkedHashMap<XmlDocument, Set<String>> hbmMetadataToEntityNamesMap = new LinkedHashMap();
/* 2792:3256 */     private Map<String, XmlDocument> hbmMetadataByEntityNameXRef = new HashMap();
/* 2793:3259 */     private transient List<XClass> annotatedClasses = new ArrayList();
/* 2794:3261 */     private transient Map<String, XClass> annotatedClassesByEntityNameMap = new HashMap();
/* 2795:     */     
/* 2796:     */     protected MetadataSourceQueue() {}
/* 2797:     */     
/* 2798:     */     private void readObject(ObjectInputStream ois)
/* 2799:     */       throws IOException, ClassNotFoundException
/* 2800:     */     {
/* 2801:3264 */       ois.defaultReadObject();
/* 2802:3265 */       this.annotatedClassesByEntityNameMap = new HashMap();
/* 2803:     */       
/* 2804:     */ 
/* 2805:     */ 
/* 2806:3269 */       List<Class> serializableAnnotatedClasses = (List)ois.readObject();
/* 2807:3270 */       this.annotatedClasses = new ArrayList(serializableAnnotatedClasses.size());
/* 2808:3271 */       for (Class clazz : serializableAnnotatedClasses) {
/* 2809:3272 */         this.annotatedClasses.add(Configuration.this.reflectionManager.toXClass(clazz));
/* 2810:     */       }
/* 2811:     */     }
/* 2812:     */     
/* 2813:     */     private void writeObject(ObjectOutputStream out)
/* 2814:     */       throws IOException
/* 2815:     */     {
/* 2816:3277 */       out.defaultWriteObject();
/* 2817:3278 */       List<Class> serializableAnnotatedClasses = new ArrayList(this.annotatedClasses.size());
/* 2818:3279 */       for (XClass xClass : this.annotatedClasses) {
/* 2819:3280 */         serializableAnnotatedClasses.add(Configuration.this.reflectionManager.toClass(xClass));
/* 2820:     */       }
/* 2821:3282 */       out.writeObject(serializableAnnotatedClasses);
/* 2822:     */     }
/* 2823:     */     
/* 2824:     */     public void add(XmlDocument metadataXml)
/* 2825:     */     {
/* 2826:3286 */       org.dom4j.Document document = metadataXml.getDocumentTree();
/* 2827:3287 */       Element hmNode = document.getRootElement();
/* 2828:3288 */       Attribute packNode = hmNode.attribute("package");
/* 2829:3289 */       String defaultPackage = packNode != null ? packNode.getValue() : "";
/* 2830:3290 */       Set<String> entityNames = new HashSet();
/* 2831:3291 */       findClassNames(defaultPackage, hmNode, entityNames);
/* 2832:3292 */       for (String entity : entityNames) {
/* 2833:3293 */         this.hbmMetadataByEntityNameXRef.put(entity, metadataXml);
/* 2834:     */       }
/* 2835:3295 */       this.hbmMetadataToEntityNamesMap.put(metadataXml, entityNames);
/* 2836:     */     }
/* 2837:     */     
/* 2838:     */     private void findClassNames(String defaultPackage, Element startNode, Set<String> names)
/* 2839:     */     {
/* 2840:3301 */       Iterator[] classes = new Iterator[4];
/* 2841:3302 */       classes[0] = startNode.elementIterator("class");
/* 2842:3303 */       classes[1] = startNode.elementIterator("subclass");
/* 2843:3304 */       classes[2] = startNode.elementIterator("joined-subclass");
/* 2844:3305 */       classes[3] = startNode.elementIterator("union-subclass");
/* 2845:     */       
/* 2846:3307 */       Iterator classIterator = new JoinedIterator(classes);
/* 2847:3308 */       while (classIterator.hasNext())
/* 2848:     */       {
/* 2849:3309 */         Element element = (Element)classIterator.next();
/* 2850:3310 */         String entityName = element.attributeValue("entity-name");
/* 2851:3311 */         if (entityName == null) {
/* 2852:3312 */           entityName = getClassName(element.attribute("name"), defaultPackage);
/* 2853:     */         }
/* 2854:3314 */         names.add(entityName);
/* 2855:3315 */         findClassNames(defaultPackage, element, names);
/* 2856:     */       }
/* 2857:     */     }
/* 2858:     */     
/* 2859:     */     private String getClassName(Attribute name, String defaultPackage)
/* 2860:     */     {
/* 2861:3320 */       if (name == null) {
/* 2862:3321 */         return null;
/* 2863:     */       }
/* 2864:3323 */       String unqualifiedName = name.getValue();
/* 2865:3324 */       if (unqualifiedName == null) {
/* 2866:3325 */         return null;
/* 2867:     */       }
/* 2868:3327 */       if ((unqualifiedName.indexOf('.') < 0) && (defaultPackage != null)) {
/* 2869:3328 */         return defaultPackage + '.' + unqualifiedName;
/* 2870:     */       }
/* 2871:3330 */       return unqualifiedName;
/* 2872:     */     }
/* 2873:     */     
/* 2874:     */     public void add(XClass annotatedClass)
/* 2875:     */     {
/* 2876:3334 */       this.annotatedClasses.add(annotatedClass);
/* 2877:     */     }
/* 2878:     */     
/* 2879:     */     protected void syncAnnotatedClasses()
/* 2880:     */     {
/* 2881:3338 */       Iterator<XClass> itr = this.annotatedClasses.iterator();
/* 2882:3339 */       while (itr.hasNext())
/* 2883:     */       {
/* 2884:3340 */         XClass annotatedClass = (XClass)itr.next();
/* 2885:3341 */         if (annotatedClass.isAnnotationPresent(Entity.class)) {
/* 2886:3342 */           this.annotatedClassesByEntityNameMap.put(annotatedClass.getName(), annotatedClass);
/* 2887:3346 */         } else if (!annotatedClass.isAnnotationPresent(javax.persistence.MappedSuperclass.class)) {
/* 2888:3347 */           itr.remove();
/* 2889:     */         }
/* 2890:     */       }
/* 2891:     */     }
/* 2892:     */     
/* 2893:     */     protected void processMetadata(List<MetadataSourceType> order)
/* 2894:     */     {
/* 2895:3353 */       syncAnnotatedClasses();
/* 2896:3355 */       for (MetadataSourceType type : order) {
/* 2897:3356 */         if (MetadataSourceType.HBM.equals(type)) {
/* 2898:3357 */           processHbmXmlQueue();
/* 2899:3359 */         } else if (MetadataSourceType.CLASS.equals(type)) {
/* 2900:3360 */           processAnnotatedClassesQueue();
/* 2901:     */         }
/* 2902:     */       }
/* 2903:     */     }
/* 2904:     */     
/* 2905:     */     private void processHbmXmlQueue()
/* 2906:     */     {
/* 2907:3366 */       Configuration.LOG.debug("Processing hbm.xml files");
/* 2908:3367 */       for (Map.Entry<XmlDocument, Set<String>> entry : this.hbmMetadataToEntityNamesMap.entrySet()) {
/* 2909:3369 */         processHbmXml((XmlDocument)entry.getKey(), (Set)entry.getValue());
/* 2910:     */       }
/* 2911:3371 */       this.hbmMetadataToEntityNamesMap.clear();
/* 2912:3372 */       this.hbmMetadataByEntityNameXRef.clear();
/* 2913:     */     }
/* 2914:     */     
/* 2915:     */     private void processHbmXml(XmlDocument metadataXml, Set<String> entityNames)
/* 2916:     */     {
/* 2917:     */       try
/* 2918:     */       {
/* 2919:3377 */         HbmBinder.bindRoot(metadataXml, Configuration.this.createMappings(), CollectionHelper.EMPTY_MAP, entityNames);
/* 2920:     */       }
/* 2921:     */       catch (MappingException me)
/* 2922:     */       {
/* 2923:3380 */         throw new InvalidMappingException(metadataXml.getOrigin().getType(), metadataXml.getOrigin().getName(), me);
/* 2924:     */       }
/* 2925:3387 */       for (String entityName : entityNames) {
/* 2926:3388 */         if (this.annotatedClassesByEntityNameMap.containsKey(entityName))
/* 2927:     */         {
/* 2928:3389 */           this.annotatedClasses.remove(this.annotatedClassesByEntityNameMap.get(entityName));
/* 2929:3390 */           this.annotatedClassesByEntityNameMap.remove(entityName);
/* 2930:     */         }
/* 2931:     */       }
/* 2932:     */     }
/* 2933:     */     
/* 2934:     */     private void processAnnotatedClassesQueue()
/* 2935:     */     {
/* 2936:3396 */       Configuration.LOG.debug("Process annotated classes");
/* 2937:     */       
/* 2938:3398 */       List<XClass> orderedClasses = orderAndFillHierarchy(this.annotatedClasses);
/* 2939:3399 */       Mappings mappings = Configuration.this.createMappings();
/* 2940:3400 */       Map<XClass, InheritanceState> inheritanceStatePerClass = AnnotationBinder.buildInheritanceStates(orderedClasses, mappings);
/* 2941:3405 */       for (XClass clazz : orderedClasses)
/* 2942:     */       {
/* 2943:3406 */         AnnotationBinder.bindClass(clazz, inheritanceStatePerClass, mappings);
/* 2944:     */         
/* 2945:3408 */         String entityName = clazz.getName();
/* 2946:3409 */         if (this.hbmMetadataByEntityNameXRef.containsKey(entityName))
/* 2947:     */         {
/* 2948:3410 */           this.hbmMetadataToEntityNamesMap.remove(this.hbmMetadataByEntityNameXRef.get(entityName));
/* 2949:3411 */           this.hbmMetadataByEntityNameXRef.remove(entityName);
/* 2950:     */         }
/* 2951:     */       }
/* 2952:3414 */       this.annotatedClasses.clear();
/* 2953:3415 */       this.annotatedClassesByEntityNameMap.clear();
/* 2954:     */     }
/* 2955:     */     
/* 2956:     */     private List<XClass> orderAndFillHierarchy(List<XClass> original)
/* 2957:     */     {
/* 2958:3419 */       List<XClass> copy = new ArrayList(original);
/* 2959:3420 */       insertMappedSuperclasses(original, copy);
/* 2960:     */       
/* 2961:     */ 
/* 2962:3423 */       List<XClass> workingCopy = new ArrayList(copy);
/* 2963:3424 */       List<XClass> newList = new ArrayList(copy.size());
/* 2964:3425 */       while (workingCopy.size() > 0)
/* 2965:     */       {
/* 2966:3426 */         XClass clazz = (XClass)workingCopy.get(0);
/* 2967:3427 */         orderHierarchy(workingCopy, newList, copy, clazz);
/* 2968:     */       }
/* 2969:3429 */       return newList;
/* 2970:     */     }
/* 2971:     */     
/* 2972:     */     private void insertMappedSuperclasses(List<XClass> original, List<XClass> copy)
/* 2973:     */     {
/* 2974:3433 */       for (Iterator i$ = original.iterator(); i$.hasNext(); goto 36)
/* 2975:     */       {
/* 2976:3433 */         XClass clazz = (XClass)i$.next();
/* 2977:3434 */         XClass superClass = clazz.getSuperclass();
/* 2978:3437 */         if ((superClass != null) && (!Configuration.this.reflectionManager.equals(superClass, Object.class)) && (!copy.contains(superClass)))
/* 2979:     */         {
/* 2980:3438 */           if ((superClass.isAnnotationPresent(Entity.class)) || (superClass.isAnnotationPresent(javax.persistence.MappedSuperclass.class))) {
/* 2981:3440 */             copy.add(superClass);
/* 2982:     */           }
/* 2983:3442 */           superClass = superClass.getSuperclass();
/* 2984:     */         }
/* 2985:     */       }
/* 2986:     */     }
/* 2987:     */     
/* 2988:     */     private void orderHierarchy(List<XClass> copy, List<XClass> newList, List<XClass> original, XClass clazz)
/* 2989:     */     {
/* 2990:3448 */       if ((clazz == null) || (Configuration.this.reflectionManager.equals(clazz, Object.class))) {
/* 2991:3449 */         return;
/* 2992:     */       }
/* 2993:3452 */       orderHierarchy(copy, newList, original, clazz.getSuperclass());
/* 2994:3453 */       if (original.contains(clazz))
/* 2995:     */       {
/* 2996:3454 */         if (!newList.contains(clazz)) {
/* 2997:3455 */           newList.add(clazz);
/* 2998:     */         }
/* 2999:3457 */         copy.remove(clazz);
/* 3000:     */       }
/* 3001:     */     }
/* 3002:     */     
/* 3003:     */     public boolean isEmpty()
/* 3004:     */     {
/* 3005:3462 */       return (this.hbmMetadataToEntityNamesMap.isEmpty()) && (this.annotatedClasses.isEmpty());
/* 3006:     */     }
/* 3007:     */   }
/* 3008:     */   
/* 3009:3468 */   public static final MetadataSourceType[] DEFAULT_ARTEFACT_PROCESSING_ORDER = { MetadataSourceType.HBM, MetadataSourceType.CLASS };
/* 3010:     */   private List<MetadataSourceType> metadataSourcePrecedence;
/* 3011:     */   
/* 3012:     */   private List<MetadataSourceType> determineMetadataSourcePrecedence()
/* 3013:     */   {
/* 3014:3476 */     if ((this.metadataSourcePrecedence.isEmpty()) && (StringHelper.isNotEmpty(getProperties().getProperty("hibernate.mapping.precedence")))) {
/* 3015:3478 */       this.metadataSourcePrecedence = parsePrecedence(getProperties().getProperty("hibernate.mapping.precedence"));
/* 3016:     */     }
/* 3017:3480 */     if (this.metadataSourcePrecedence.isEmpty()) {
/* 3018:3481 */       this.metadataSourcePrecedence = Arrays.asList(DEFAULT_ARTEFACT_PROCESSING_ORDER);
/* 3019:     */     }
/* 3020:3483 */     this.metadataSourcePrecedence = Collections.unmodifiableList(this.metadataSourcePrecedence);
/* 3021:     */     
/* 3022:3485 */     return this.metadataSourcePrecedence;
/* 3023:     */   }
/* 3024:     */   
/* 3025:     */   public void setPrecedence(String precedence)
/* 3026:     */   {
/* 3027:3489 */     this.metadataSourcePrecedence = parsePrecedence(precedence);
/* 3028:     */   }
/* 3029:     */   
/* 3030:     */   private List<MetadataSourceType> parsePrecedence(String s)
/* 3031:     */   {
/* 3032:3493 */     if (StringHelper.isEmpty(s)) {
/* 3033:3494 */       return Collections.emptyList();
/* 3034:     */     }
/* 3035:3496 */     StringTokenizer precedences = new StringTokenizer(s, ",; ", false);
/* 3036:3497 */     List<MetadataSourceType> tmpPrecedences = new ArrayList();
/* 3037:3498 */     while (precedences.hasMoreElements()) {
/* 3038:3499 */       tmpPrecedences.add(MetadataSourceType.parsePrecedence((String)precedences.nextElement()));
/* 3039:     */     }
/* 3040:3501 */     return tmpPrecedences;
/* 3041:     */   }
/* 3042:     */   
/* 3043:     */   private static class CacheHolder
/* 3044:     */   {
/* 3045:     */     public String role;
/* 3046:     */     public String usage;
/* 3047:     */     public String region;
/* 3048:     */     public boolean isClass;
/* 3049:     */     public boolean cacheLazy;
/* 3050:     */     
/* 3051:     */     public CacheHolder(String role, String usage, String region, boolean isClass, boolean cacheLazy)
/* 3052:     */     {
/* 3053:3506 */       this.role = role;
/* 3054:3507 */       this.usage = usage;
/* 3055:3508 */       this.region = region;
/* 3056:3509 */       this.isClass = isClass;
/* 3057:3510 */       this.cacheLazy = cacheLazy;
/* 3058:     */     }
/* 3059:     */   }
/* 3060:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.Configuration
 * JD-Core Version:    0.7.0.1
 */