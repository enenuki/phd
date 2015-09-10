/*    1:     */ package org.hibernate.hql.internal.classic;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.lang.reflect.Constructor;
/*    5:     */ import java.sql.PreparedStatement;
/*    6:     */ import java.sql.ResultSet;
/*    7:     */ import java.sql.SQLException;
/*    8:     */ import java.util.ArrayList;
/*    9:     */ import java.util.Arrays;
/*   10:     */ import java.util.Collection;
/*   11:     */ import java.util.HashMap;
/*   12:     */ import java.util.HashSet;
/*   13:     */ import java.util.Iterator;
/*   14:     */ import java.util.LinkedHashMap;
/*   15:     */ import java.util.List;
/*   16:     */ import java.util.Map;
/*   17:     */ import java.util.Map.Entry;
/*   18:     */ import java.util.Set;
/*   19:     */ import org.hibernate.HibernateException;
/*   20:     */ import org.hibernate.LockMode;
/*   21:     */ import org.hibernate.LockOptions;
/*   22:     */ import org.hibernate.MappingException;
/*   23:     */ import org.hibernate.QueryException;
/*   24:     */ import org.hibernate.ScrollableResults;
/*   25:     */ import org.hibernate.dialect.Dialect;
/*   26:     */ import org.hibernate.engine.internal.JoinSequence;
/*   27:     */ import org.hibernate.engine.internal.JoinSequence.Selector;
/*   28:     */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   29:     */ import org.hibernate.engine.spi.QueryParameters;
/*   30:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   31:     */ import org.hibernate.engine.spi.SessionImplementor;
/*   32:     */ import org.hibernate.event.spi.EventSource;
/*   33:     */ import org.hibernate.hql.internal.HolderInstantiator;
/*   34:     */ import org.hibernate.hql.internal.NameGenerator;
/*   35:     */ import org.hibernate.hql.spi.FilterTranslator;
/*   36:     */ import org.hibernate.hql.spi.ParameterTranslations;
/*   37:     */ import org.hibernate.internal.CoreMessageLogger;
/*   38:     */ import org.hibernate.internal.IteratorImpl;
/*   39:     */ import org.hibernate.internal.util.ReflectHelper;
/*   40:     */ import org.hibernate.internal.util.StringHelper;
/*   41:     */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   42:     */ import org.hibernate.loader.BasicLoader;
/*   43:     */ import org.hibernate.persister.collection.CollectionPersister;
/*   44:     */ import org.hibernate.persister.collection.QueryableCollection;
/*   45:     */ import org.hibernate.persister.entity.Loadable;
/*   46:     */ import org.hibernate.persister.entity.PropertyMapping;
/*   47:     */ import org.hibernate.persister.entity.Queryable;
/*   48:     */ import org.hibernate.sql.JoinFragment;
/*   49:     */ import org.hibernate.sql.JoinType;
/*   50:     */ import org.hibernate.sql.QuerySelect;
/*   51:     */ import org.hibernate.stat.Statistics;
/*   52:     */ import org.hibernate.stat.spi.StatisticsImplementor;
/*   53:     */ import org.hibernate.transform.ResultTransformer;
/*   54:     */ import org.hibernate.type.AssociationType;
/*   55:     */ import org.hibernate.type.EntityType;
/*   56:     */ import org.hibernate.type.Type;
/*   57:     */ import org.hibernate.type.TypeFactory;
/*   58:     */ import org.hibernate.type.TypeResolver;
/*   59:     */ import org.jboss.logging.Logger;
/*   60:     */ 
/*   61:     */ public class QueryTranslatorImpl
/*   62:     */   extends BasicLoader
/*   63:     */   implements FilterTranslator
/*   64:     */ {
/*   65:  85 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, QueryTranslatorImpl.class.getName());
/*   66:  87 */   private static final String[] NO_RETURN_ALIASES = new String[0];
/*   67:     */   private final String queryIdentifier;
/*   68:     */   private final String queryString;
/*   69:  92 */   private final Map typeMap = new LinkedHashMap();
/*   70:  93 */   private final Map collections = new LinkedHashMap();
/*   71:  94 */   private List returnedTypes = new ArrayList();
/*   72:  95 */   private final List fromTypes = new ArrayList();
/*   73:  96 */   private final List scalarTypes = new ArrayList();
/*   74:  97 */   private final Map namedParameters = new HashMap();
/*   75:  98 */   private final Map aliasNames = new HashMap();
/*   76:  99 */   private final Map oneToOneOwnerNames = new HashMap();
/*   77: 100 */   private final Map uniqueKeyOwnerReferences = new HashMap();
/*   78: 101 */   private final Map decoratedPropertyMappings = new HashMap();
/*   79: 103 */   private final List scalarSelectTokens = new ArrayList();
/*   80: 104 */   private final List whereTokens = new ArrayList();
/*   81: 105 */   private final List havingTokens = new ArrayList();
/*   82: 106 */   private final Map joins = new LinkedHashMap();
/*   83: 107 */   private final List orderByTokens = new ArrayList();
/*   84: 108 */   private final List groupByTokens = new ArrayList();
/*   85: 109 */   private final Set querySpaces = new HashSet();
/*   86: 110 */   private final Set entitiesToFetch = new HashSet();
/*   87: 112 */   private final Map pathAliases = new HashMap();
/*   88: 113 */   private final Map pathJoins = new HashMap();
/*   89:     */   private Queryable[] persisters;
/*   90:     */   private int[] owners;
/*   91:     */   private EntityType[] ownerAssociationTypes;
/*   92:     */   private String[] names;
/*   93:     */   private boolean[] includeInSelect;
/*   94:     */   private int selectLength;
/*   95:     */   private Type[] returnTypes;
/*   96:     */   private Type[] actualReturnTypes;
/*   97:     */   private String[][] scalarColumnNames;
/*   98:     */   private Map tokenReplacements;
/*   99: 125 */   private int nameCount = 0;
/*  100: 126 */   private int parameterCount = 0;
/*  101: 127 */   private boolean distinct = false;
/*  102:     */   private boolean compiled;
/*  103:     */   private String sqlString;
/*  104:     */   private Class holderClass;
/*  105:     */   private Constructor holderConstructor;
/*  106:     */   private boolean hasScalars;
/*  107:     */   private boolean shallowQuery;
/*  108:     */   private QueryTranslatorImpl superQuery;
/*  109:     */   private QueryableCollection collectionPersister;
/*  110: 137 */   private int collectionOwnerColumn = -1;
/*  111:     */   private String collectionOwnerName;
/*  112:     */   private String fetchName;
/*  113:     */   private String[] suffixes;
/*  114:     */   private Map enabledFilters;
/*  115:     */   
/*  116:     */   public QueryTranslatorImpl(String queryIdentifier, String queryString, Map enabledFilters, SessionFactoryImplementor factory)
/*  117:     */   {
/*  118: 160 */     super(factory);
/*  119: 161 */     this.queryIdentifier = queryIdentifier;
/*  120: 162 */     this.queryString = queryString;
/*  121: 163 */     this.enabledFilters = enabledFilters;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public QueryTranslatorImpl(String queryString, Map enabledFilters, SessionFactoryImplementor factory)
/*  125:     */   {
/*  126: 177 */     this(queryString, queryString, enabledFilters, factory);
/*  127:     */   }
/*  128:     */   
/*  129:     */   void compile(QueryTranslatorImpl superquery)
/*  130:     */     throws QueryException, MappingException
/*  131:     */   {
/*  132: 191 */     this.tokenReplacements = superquery.tokenReplacements;
/*  133: 192 */     this.superQuery = superquery;
/*  134: 193 */     this.shallowQuery = true;
/*  135: 194 */     this.enabledFilters = superquery.getEnabledFilters();
/*  136: 195 */     compile();
/*  137:     */   }
/*  138:     */   
/*  139:     */   public synchronized void compile(Map replacements, boolean scalar)
/*  140:     */     throws QueryException, MappingException
/*  141:     */   {
/*  142: 206 */     if (!this.compiled)
/*  143:     */     {
/*  144: 207 */       this.tokenReplacements = replacements;
/*  145: 208 */       this.shallowQuery = scalar;
/*  146: 209 */       compile();
/*  147:     */     }
/*  148:     */   }
/*  149:     */   
/*  150:     */   public synchronized void compile(String collectionRole, Map replacements, boolean scalar)
/*  151:     */     throws QueryException, MappingException
/*  152:     */   {
/*  153: 222 */     if (!isCompiled())
/*  154:     */     {
/*  155: 223 */       addFromAssociation("this", collectionRole);
/*  156: 224 */       compile(replacements, scalar);
/*  157:     */     }
/*  158:     */   }
/*  159:     */   
/*  160:     */   private void compile()
/*  161:     */     throws QueryException, MappingException
/*  162:     */   {
/*  163: 237 */     LOG.trace("Compiling query");
/*  164:     */     try
/*  165:     */     {
/*  166: 239 */       ParserHelper.parse(new PreprocessingParser(this.tokenReplacements), this.queryString, " \n\r\f\t,()=<>&|+-=/*'^![]#~\\", this);
/*  167:     */       
/*  168:     */ 
/*  169:     */ 
/*  170: 243 */       renderSQL();
/*  171:     */     }
/*  172:     */     catch (QueryException qe)
/*  173:     */     {
/*  174: 246 */       qe.setQueryString(this.queryString);
/*  175: 247 */       throw qe;
/*  176:     */     }
/*  177:     */     catch (MappingException me)
/*  178:     */     {
/*  179: 250 */       throw me;
/*  180:     */     }
/*  181:     */     catch (Exception e)
/*  182:     */     {
/*  183: 253 */       LOG.debug("Unexpected query compilation problem", e);
/*  184: 254 */       e.printStackTrace();
/*  185: 255 */       QueryException qe = new QueryException("Incorrect query syntax", e);
/*  186: 256 */       qe.setQueryString(this.queryString);
/*  187: 257 */       throw qe;
/*  188:     */     }
/*  189: 260 */     postInstantiate();
/*  190:     */     
/*  191: 262 */     this.compiled = true;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public String getSQLString()
/*  195:     */   {
/*  196: 268 */     return this.sqlString;
/*  197:     */   }
/*  198:     */   
/*  199:     */   public List collectSqlStrings()
/*  200:     */   {
/*  201: 272 */     return ArrayHelper.toList(new String[] { this.sqlString });
/*  202:     */   }
/*  203:     */   
/*  204:     */   public String getQueryString()
/*  205:     */   {
/*  206: 276 */     return this.queryString;
/*  207:     */   }
/*  208:     */   
/*  209:     */   protected Loadable[] getEntityPersisters()
/*  210:     */   {
/*  211: 286 */     return this.persisters;
/*  212:     */   }
/*  213:     */   
/*  214:     */   public Type[] getReturnTypes()
/*  215:     */   {
/*  216: 295 */     return this.actualReturnTypes;
/*  217:     */   }
/*  218:     */   
/*  219:     */   public String[] getReturnAliases()
/*  220:     */   {
/*  221: 300 */     return NO_RETURN_ALIASES;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public String[][] getColumnNames()
/*  225:     */   {
/*  226: 304 */     return this.scalarColumnNames;
/*  227:     */   }
/*  228:     */   
/*  229:     */   private static void logQuery(String hql, String sql)
/*  230:     */   {
/*  231: 308 */     if (LOG.isDebugEnabled())
/*  232:     */     {
/*  233: 309 */       LOG.debugf("HQL: %s", hql);
/*  234: 310 */       LOG.debugf("SQL: %s", sql);
/*  235:     */     }
/*  236:     */   }
/*  237:     */   
/*  238:     */   void setAliasName(String alias, String name)
/*  239:     */   {
/*  240: 315 */     this.aliasNames.put(alias, name);
/*  241:     */   }
/*  242:     */   
/*  243:     */   public String getAliasName(String alias)
/*  244:     */   {
/*  245: 319 */     String name = (String)this.aliasNames.get(alias);
/*  246: 320 */     if (name == null) {
/*  247: 321 */       if (this.superQuery != null) {
/*  248: 322 */         name = this.superQuery.getAliasName(alias);
/*  249:     */       } else {
/*  250: 325 */         name = alias;
/*  251:     */       }
/*  252:     */     }
/*  253: 328 */     return name;
/*  254:     */   }
/*  255:     */   
/*  256:     */   String unalias(String path)
/*  257:     */   {
/*  258: 332 */     String alias = StringHelper.root(path);
/*  259: 333 */     String name = getAliasName(alias);
/*  260: 334 */     if (name != null) {
/*  261: 334 */       return name + path.substring(alias.length());
/*  262:     */     }
/*  263: 335 */     return path;
/*  264:     */   }
/*  265:     */   
/*  266:     */   void addEntityToFetch(String name, String oneToOneOwnerName, AssociationType ownerAssociationType)
/*  267:     */   {
/*  268: 339 */     addEntityToFetch(name);
/*  269: 340 */     if (oneToOneOwnerName != null) {
/*  270: 340 */       this.oneToOneOwnerNames.put(name, oneToOneOwnerName);
/*  271:     */     }
/*  272: 341 */     if (ownerAssociationType != null) {
/*  273: 341 */       this.uniqueKeyOwnerReferences.put(name, ownerAssociationType);
/*  274:     */     }
/*  275:     */   }
/*  276:     */   
/*  277:     */   private void addEntityToFetch(String name)
/*  278:     */   {
/*  279: 345 */     this.entitiesToFetch.add(name);
/*  280:     */   }
/*  281:     */   
/*  282:     */   private int nextCount()
/*  283:     */   {
/*  284: 349 */     return this.superQuery == null ? this.nameCount++ : this.superQuery.nameCount++;
/*  285:     */   }
/*  286:     */   
/*  287:     */   String createNameFor(String type)
/*  288:     */   {
/*  289: 353 */     return StringHelper.generateAlias(type, nextCount());
/*  290:     */   }
/*  291:     */   
/*  292:     */   String createNameForCollection(String role)
/*  293:     */   {
/*  294: 357 */     return StringHelper.generateAlias(role, nextCount());
/*  295:     */   }
/*  296:     */   
/*  297:     */   private String getType(String name)
/*  298:     */   {
/*  299: 361 */     String type = (String)this.typeMap.get(name);
/*  300: 362 */     if ((type == null) && (this.superQuery != null)) {
/*  301: 363 */       type = this.superQuery.getType(name);
/*  302:     */     }
/*  303: 365 */     return type;
/*  304:     */   }
/*  305:     */   
/*  306:     */   private String getRole(String name)
/*  307:     */   {
/*  308: 369 */     String role = (String)this.collections.get(name);
/*  309: 370 */     if ((role == null) && (this.superQuery != null)) {
/*  310: 371 */       role = this.superQuery.getRole(name);
/*  311:     */     }
/*  312: 373 */     return role;
/*  313:     */   }
/*  314:     */   
/*  315:     */   boolean isName(String name)
/*  316:     */   {
/*  317: 377 */     return (this.aliasNames.containsKey(name)) || (this.typeMap.containsKey(name)) || (this.collections.containsKey(name)) || ((this.superQuery != null) && (this.superQuery.isName(name)));
/*  318:     */   }
/*  319:     */   
/*  320:     */   PropertyMapping getPropertyMapping(String name)
/*  321:     */     throws QueryException
/*  322:     */   {
/*  323: 385 */     PropertyMapping decorator = getDecoratedPropertyMapping(name);
/*  324: 386 */     if (decorator != null) {
/*  325: 386 */       return decorator;
/*  326:     */     }
/*  327: 388 */     String type = getType(name);
/*  328: 389 */     if (type == null)
/*  329:     */     {
/*  330: 390 */       String role = getRole(name);
/*  331: 391 */       if (role == null) {
/*  332: 392 */         throw new QueryException("alias not found: " + name);
/*  333:     */       }
/*  334: 394 */       return getCollectionPersister(role);
/*  335:     */     }
/*  336: 397 */     Queryable persister = getEntityPersister(type);
/*  337: 398 */     if (persister == null) {
/*  338: 398 */       throw new QueryException("persistent class not found: " + type);
/*  339:     */     }
/*  340: 399 */     return persister;
/*  341:     */   }
/*  342:     */   
/*  343:     */   private PropertyMapping getDecoratedPropertyMapping(String name)
/*  344:     */   {
/*  345: 404 */     return (PropertyMapping)this.decoratedPropertyMappings.get(name);
/*  346:     */   }
/*  347:     */   
/*  348:     */   void decoratePropertyMapping(String name, PropertyMapping mapping)
/*  349:     */   {
/*  350: 408 */     this.decoratedPropertyMappings.put(name, mapping);
/*  351:     */   }
/*  352:     */   
/*  353:     */   private Queryable getEntityPersisterForName(String name)
/*  354:     */     throws QueryException
/*  355:     */   {
/*  356: 412 */     String type = getType(name);
/*  357: 413 */     Queryable persister = getEntityPersister(type);
/*  358: 414 */     if (persister == null) {
/*  359: 414 */       throw new QueryException("persistent class not found: " + type);
/*  360:     */     }
/*  361: 415 */     return persister;
/*  362:     */   }
/*  363:     */   
/*  364:     */   Queryable getEntityPersisterUsingImports(String className)
/*  365:     */   {
/*  366: 419 */     String importedClassName = getFactory().getImportedClassName(className);
/*  367: 420 */     if (importedClassName == null) {
/*  368: 421 */       return null;
/*  369:     */     }
/*  370:     */     try
/*  371:     */     {
/*  372: 424 */       return (Queryable)getFactory().getEntityPersister(importedClassName);
/*  373:     */     }
/*  374:     */     catch (MappingException me) {}
/*  375: 427 */     return null;
/*  376:     */   }
/*  377:     */   
/*  378:     */   Queryable getEntityPersister(String entityName)
/*  379:     */     throws QueryException
/*  380:     */   {
/*  381:     */     try
/*  382:     */     {
/*  383: 433 */       return (Queryable)getFactory().getEntityPersister(entityName);
/*  384:     */     }
/*  385:     */     catch (Exception e)
/*  386:     */     {
/*  387: 436 */       throw new QueryException("persistent class not found: " + entityName);
/*  388:     */     }
/*  389:     */   }
/*  390:     */   
/*  391:     */   QueryableCollection getCollectionPersister(String role)
/*  392:     */     throws QueryException
/*  393:     */   {
/*  394:     */     try
/*  395:     */     {
/*  396: 442 */       return (QueryableCollection)getFactory().getCollectionPersister(role);
/*  397:     */     }
/*  398:     */     catch (ClassCastException cce)
/*  399:     */     {
/*  400: 445 */       throw new QueryException("collection role is not queryable: " + role);
/*  401:     */     }
/*  402:     */     catch (Exception e)
/*  403:     */     {
/*  404: 448 */       throw new QueryException("collection role not found: " + role);
/*  405:     */     }
/*  406:     */   }
/*  407:     */   
/*  408:     */   void addType(String name, String type)
/*  409:     */   {
/*  410: 453 */     this.typeMap.put(name, type);
/*  411:     */   }
/*  412:     */   
/*  413:     */   void addCollection(String name, String role)
/*  414:     */   {
/*  415: 457 */     this.collections.put(name, role);
/*  416:     */   }
/*  417:     */   
/*  418:     */   void addFrom(String name, String type, JoinSequence joinSequence)
/*  419:     */     throws QueryException
/*  420:     */   {
/*  421: 462 */     addType(name, type);
/*  422: 463 */     addFrom(name, joinSequence);
/*  423:     */   }
/*  424:     */   
/*  425:     */   void addFromCollection(String name, String collectionRole, JoinSequence joinSequence)
/*  426:     */     throws QueryException
/*  427:     */   {
/*  428: 469 */     addCollection(name, collectionRole);
/*  429: 470 */     addJoin(name, joinSequence);
/*  430:     */   }
/*  431:     */   
/*  432:     */   void addFrom(String name, JoinSequence joinSequence)
/*  433:     */     throws QueryException
/*  434:     */   {
/*  435: 475 */     this.fromTypes.add(name);
/*  436: 476 */     addJoin(name, joinSequence);
/*  437:     */   }
/*  438:     */   
/*  439:     */   void addFromClass(String name, Queryable classPersister)
/*  440:     */     throws QueryException
/*  441:     */   {
/*  442: 481 */     JoinSequence joinSequence = new JoinSequence(getFactory()).setRoot(classPersister, name);
/*  443:     */     
/*  444:     */ 
/*  445: 484 */     addFrom(name, classPersister.getEntityName(), joinSequence);
/*  446:     */   }
/*  447:     */   
/*  448:     */   void addSelectClass(String name)
/*  449:     */   {
/*  450: 488 */     this.returnedTypes.add(name);
/*  451:     */   }
/*  452:     */   
/*  453:     */   void addSelectScalar(Type type)
/*  454:     */   {
/*  455: 492 */     this.scalarTypes.add(type);
/*  456:     */   }
/*  457:     */   
/*  458:     */   void appendWhereToken(String token)
/*  459:     */   {
/*  460: 496 */     this.whereTokens.add(token);
/*  461:     */   }
/*  462:     */   
/*  463:     */   void appendHavingToken(String token)
/*  464:     */   {
/*  465: 500 */     this.havingTokens.add(token);
/*  466:     */   }
/*  467:     */   
/*  468:     */   void appendOrderByToken(String token)
/*  469:     */   {
/*  470: 504 */     this.orderByTokens.add(token);
/*  471:     */   }
/*  472:     */   
/*  473:     */   void appendGroupByToken(String token)
/*  474:     */   {
/*  475: 508 */     this.groupByTokens.add(token);
/*  476:     */   }
/*  477:     */   
/*  478:     */   void appendScalarSelectToken(String token)
/*  479:     */   {
/*  480: 512 */     this.scalarSelectTokens.add(token);
/*  481:     */   }
/*  482:     */   
/*  483:     */   void appendScalarSelectTokens(String[] tokens)
/*  484:     */   {
/*  485: 516 */     this.scalarSelectTokens.add(tokens);
/*  486:     */   }
/*  487:     */   
/*  488:     */   void addFromJoinOnly(String name, JoinSequence joinSequence)
/*  489:     */     throws QueryException
/*  490:     */   {
/*  491: 520 */     addJoin(name, joinSequence.getFromPart());
/*  492:     */   }
/*  493:     */   
/*  494:     */   void addJoin(String name, JoinSequence joinSequence)
/*  495:     */     throws QueryException
/*  496:     */   {
/*  497: 524 */     if (!this.joins.containsKey(name)) {
/*  498: 524 */       this.joins.put(name, joinSequence);
/*  499:     */     }
/*  500:     */   }
/*  501:     */   
/*  502:     */   void addNamedParameter(String name)
/*  503:     */   {
/*  504: 528 */     if (this.superQuery != null) {
/*  505: 528 */       this.superQuery.addNamedParameter(name);
/*  506:     */     }
/*  507: 529 */     Integer loc = Integer.valueOf(this.parameterCount++);
/*  508: 530 */     Object o = this.namedParameters.get(name);
/*  509: 531 */     if (o == null)
/*  510:     */     {
/*  511: 532 */       this.namedParameters.put(name, loc);
/*  512:     */     }
/*  513: 534 */     else if ((o instanceof Integer))
/*  514:     */     {
/*  515: 535 */       ArrayList list = new ArrayList(4);
/*  516: 536 */       list.add(o);
/*  517: 537 */       list.add(loc);
/*  518: 538 */       this.namedParameters.put(name, list);
/*  519:     */     }
/*  520:     */     else
/*  521:     */     {
/*  522: 541 */       ((ArrayList)o).add(loc);
/*  523:     */     }
/*  524:     */   }
/*  525:     */   
/*  526:     */   public int[] getNamedParameterLocs(String name)
/*  527:     */     throws QueryException
/*  528:     */   {
/*  529: 547 */     Object o = this.namedParameters.get(name);
/*  530: 548 */     if (o == null)
/*  531:     */     {
/*  532: 549 */       QueryException qe = new QueryException("Named parameter does not appear in Query: " + name);
/*  533: 550 */       qe.setQueryString(this.queryString);
/*  534: 551 */       throw qe;
/*  535:     */     }
/*  536: 553 */     if ((o instanceof Integer)) {
/*  537: 554 */       return new int[] { ((Integer)o).intValue() };
/*  538:     */     }
/*  539: 557 */     return ArrayHelper.toIntArray((ArrayList)o);
/*  540:     */   }
/*  541:     */   
/*  542:     */   private void renderSQL()
/*  543:     */     throws QueryException, MappingException
/*  544:     */   {
/*  545:     */     int rtsize;
/*  546:     */     int rtsize;
/*  547: 564 */     if ((this.returnedTypes.size() == 0) && (this.scalarTypes.size() == 0))
/*  548:     */     {
/*  549: 566 */       this.returnedTypes = this.fromTypes;
/*  550: 567 */       rtsize = this.returnedTypes.size();
/*  551:     */     }
/*  552:     */     else
/*  553:     */     {
/*  554: 570 */       rtsize = this.returnedTypes.size();
/*  555: 571 */       Iterator iter = this.entitiesToFetch.iterator();
/*  556: 572 */       while (iter.hasNext()) {
/*  557: 573 */         this.returnedTypes.add(iter.next());
/*  558:     */       }
/*  559:     */     }
/*  560: 576 */     int size = this.returnedTypes.size();
/*  561: 577 */     this.persisters = new Queryable[size];
/*  562: 578 */     this.names = new String[size];
/*  563: 579 */     this.owners = new int[size];
/*  564: 580 */     this.ownerAssociationTypes = new EntityType[size];
/*  565: 581 */     this.suffixes = new String[size];
/*  566: 582 */     this.includeInSelect = new boolean[size];
/*  567: 583 */     for (int i = 0; i < size; i++)
/*  568:     */     {
/*  569: 584 */       String name = (String)this.returnedTypes.get(i);
/*  570:     */       
/*  571: 586 */       this.persisters[i] = getEntityPersisterForName(name);
/*  572:     */       
/*  573: 588 */       this.suffixes[i] = (Integer.toString(i) + '_');
/*  574: 589 */       this.names[i] = name;
/*  575: 590 */       this.includeInSelect[i] = (!this.entitiesToFetch.contains(name) ? 1 : false);
/*  576: 591 */       if (this.includeInSelect[i] != 0) {
/*  577: 591 */         this.selectLength += 1;
/*  578:     */       }
/*  579: 592 */       if (name.equals(this.collectionOwnerName)) {
/*  580: 592 */         this.collectionOwnerColumn = i;
/*  581:     */       }
/*  582: 593 */       String oneToOneOwner = (String)this.oneToOneOwnerNames.get(name);
/*  583: 594 */       this.owners[i] = (oneToOneOwner == null ? -1 : this.returnedTypes.indexOf(oneToOneOwner));
/*  584: 595 */       this.ownerAssociationTypes[i] = ((EntityType)this.uniqueKeyOwnerReferences.get(name));
/*  585:     */     }
/*  586: 598 */     if (ArrayHelper.isAllNegative(this.owners)) {
/*  587: 598 */       this.owners = null;
/*  588:     */     }
/*  589: 600 */     String scalarSelect = renderScalarSelect();
/*  590:     */     
/*  591: 602 */     int scalarSize = this.scalarTypes.size();
/*  592: 603 */     this.hasScalars = (this.scalarTypes.size() != rtsize);
/*  593:     */     
/*  594: 605 */     this.returnTypes = new Type[scalarSize];
/*  595: 606 */     for (int i = 0; i < scalarSize; i++) {
/*  596: 607 */       this.returnTypes[i] = ((Type)this.scalarTypes.get(i));
/*  597:     */     }
/*  598: 610 */     QuerySelect sql = new QuerySelect(getFactory().getDialect());
/*  599: 611 */     sql.setDistinct(this.distinct);
/*  600: 613 */     if (!this.shallowQuery)
/*  601:     */     {
/*  602: 614 */       renderIdentifierSelect(sql);
/*  603: 615 */       renderPropertiesSelect(sql);
/*  604:     */     }
/*  605: 618 */     if (this.collectionPersister != null) {
/*  606: 619 */       sql.addSelectFragmentString(this.collectionPersister.selectFragment(this.fetchName, "__"));
/*  607:     */     }
/*  608: 622 */     if ((this.hasScalars) || (this.shallowQuery)) {
/*  609: 622 */       sql.addSelectFragmentString(scalarSelect);
/*  610:     */     }
/*  611: 625 */     mergeJoins(sql.getJoinFragment());
/*  612:     */     
/*  613: 627 */     sql.setWhereTokens(this.whereTokens.iterator());
/*  614:     */     
/*  615: 629 */     sql.setGroupByTokens(this.groupByTokens.iterator());
/*  616: 630 */     sql.setHavingTokens(this.havingTokens.iterator());
/*  617: 631 */     sql.setOrderByTokens(this.orderByTokens.iterator());
/*  618: 633 */     if ((this.collectionPersister != null) && (this.collectionPersister.hasOrdering())) {
/*  619: 634 */       sql.addOrderBy(this.collectionPersister.getSQLOrderByString(this.fetchName));
/*  620:     */     }
/*  621: 637 */     this.scalarColumnNames = NameGenerator.generateColumnNames(this.returnTypes, getFactory());
/*  622:     */     
/*  623:     */ 
/*  624: 640 */     Iterator iter = this.collections.values().iterator();
/*  625: 641 */     while (iter.hasNext())
/*  626:     */     {
/*  627: 642 */       CollectionPersister p = getCollectionPersister((String)iter.next());
/*  628: 643 */       addQuerySpaces(p.getCollectionSpaces());
/*  629:     */     }
/*  630: 645 */     iter = this.typeMap.keySet().iterator();
/*  631: 646 */     while (iter.hasNext())
/*  632:     */     {
/*  633: 647 */       Queryable p = getEntityPersisterForName((String)iter.next());
/*  634: 648 */       addQuerySpaces(p.getQuerySpaces());
/*  635:     */     }
/*  636: 651 */     this.sqlString = sql.toQueryString();
/*  637: 653 */     if (this.holderClass != null) {
/*  638: 653 */       this.holderConstructor = ReflectHelper.getConstructor(this.holderClass, this.returnTypes);
/*  639:     */     }
/*  640: 655 */     if (this.hasScalars)
/*  641:     */     {
/*  642: 656 */       this.actualReturnTypes = this.returnTypes;
/*  643:     */     }
/*  644:     */     else
/*  645:     */     {
/*  646: 659 */       this.actualReturnTypes = new Type[this.selectLength];
/*  647: 660 */       int j = 0;
/*  648: 661 */       for (int i = 0; i < this.persisters.length; i++) {
/*  649: 662 */         if (this.includeInSelect[i] != 0) {
/*  650: 663 */           this.actualReturnTypes[(j++)] = getFactory().getTypeResolver().getTypeFactory().manyToOne(this.persisters[i].getEntityName(), this.shallowQuery);
/*  651:     */         }
/*  652:     */       }
/*  653:     */     }
/*  654:     */   }
/*  655:     */   
/*  656:     */   private void renderIdentifierSelect(QuerySelect sql)
/*  657:     */   {
/*  658: 673 */     int size = this.returnedTypes.size();
/*  659: 675 */     for (int k = 0; k < size; k++)
/*  660:     */     {
/*  661: 676 */       String name = (String)this.returnedTypes.get(k);
/*  662: 677 */       String suffix = Integer.toString(k) + '_';
/*  663: 678 */       sql.addSelectFragmentString(this.persisters[k].identifierSelectFragment(name, suffix));
/*  664:     */     }
/*  665:     */   }
/*  666:     */   
/*  667:     */   private void renderPropertiesSelect(QuerySelect sql)
/*  668:     */   {
/*  669: 700 */     int size = this.returnedTypes.size();
/*  670: 701 */     for (int k = 0; k < size; k++)
/*  671:     */     {
/*  672: 702 */       String suffix = Integer.toString(k) + '_';
/*  673: 703 */       String name = (String)this.returnedTypes.get(k);
/*  674: 704 */       sql.addSelectFragmentString(this.persisters[k].propertySelectFragment(name, suffix, false));
/*  675:     */     }
/*  676:     */   }
/*  677:     */   
/*  678:     */   private String renderScalarSelect()
/*  679:     */   {
/*  680: 713 */     boolean isSubselect = this.superQuery != null;
/*  681:     */     
/*  682: 715 */     StringBuffer buf = new StringBuffer(20);
/*  683: 717 */     if (this.scalarTypes.size() == 0)
/*  684:     */     {
/*  685: 719 */       int size = this.returnedTypes.size();
/*  686: 720 */       for (int k = 0; k < size; k++)
/*  687:     */       {
/*  688: 722 */         this.scalarTypes.add(getFactory().getTypeResolver().getTypeFactory().manyToOne(this.persisters[k].getEntityName(), this.shallowQuery));
/*  689:     */         
/*  690:     */ 
/*  691:     */ 
/*  692: 726 */         String[] idColumnNames = this.persisters[k].getIdentifierColumnNames();
/*  693: 727 */         for (int i = 0; i < idColumnNames.length; i++)
/*  694:     */         {
/*  695: 728 */           buf.append(this.returnedTypes.get(k)).append('.').append(idColumnNames[i]);
/*  696: 729 */           if (!isSubselect) {
/*  697: 729 */             buf.append(" as ").append(NameGenerator.scalarName(k, i));
/*  698:     */           }
/*  699: 730 */           if ((i != idColumnNames.length - 1) || (k != size - 1)) {
/*  700: 730 */             buf.append(", ");
/*  701:     */           }
/*  702:     */         }
/*  703:     */       }
/*  704:     */     }
/*  705:     */     else
/*  706:     */     {
/*  707: 738 */       Iterator iter = this.scalarSelectTokens.iterator();
/*  708: 739 */       int c = 0;
/*  709: 740 */       boolean nolast = false;
/*  710: 741 */       int parenCount = 0;
/*  711: 742 */       while (iter.hasNext())
/*  712:     */       {
/*  713: 743 */         Object next = iter.next();
/*  714: 744 */         if ((next instanceof String))
/*  715:     */         {
/*  716: 745 */           String token = (String)next;
/*  717: 747 */           if ("(".equals(token)) {
/*  718: 748 */             parenCount++;
/*  719: 750 */           } else if (")".equals(token)) {
/*  720: 751 */             parenCount--;
/*  721:     */           }
/*  722: 754 */           String lc = token.toLowerCase();
/*  723: 755 */           if (lc.equals(", ")) {
/*  724: 756 */             if (nolast)
/*  725:     */             {
/*  726: 757 */               nolast = false;
/*  727:     */             }
/*  728: 760 */             else if ((!isSubselect) && (parenCount == 0))
/*  729:     */             {
/*  730: 761 */               int x = c++;
/*  731: 762 */               buf.append(" as ").append(NameGenerator.scalarName(x, 0));
/*  732:     */             }
/*  733:     */           }
/*  734: 767 */           buf.append(token);
/*  735: 768 */           if ((lc.equals("distinct")) || (lc.equals("all"))) {
/*  736: 769 */             buf.append(' ');
/*  737:     */           }
/*  738:     */         }
/*  739:     */         else
/*  740:     */         {
/*  741: 773 */           nolast = true;
/*  742: 774 */           String[] tokens = (String[])next;
/*  743: 775 */           for (int i = 0; i < tokens.length; i++)
/*  744:     */           {
/*  745: 776 */             buf.append(tokens[i]);
/*  746: 777 */             if (!isSubselect) {
/*  747: 778 */               buf.append(" as ").append(NameGenerator.scalarName(c, i));
/*  748:     */             }
/*  749: 781 */             if (i != tokens.length - 1) {
/*  750: 781 */               buf.append(", ");
/*  751:     */             }
/*  752:     */           }
/*  753: 783 */           c++;
/*  754:     */         }
/*  755:     */       }
/*  756: 786 */       if ((!isSubselect) && (!nolast))
/*  757:     */       {
/*  758: 787 */         int x = c++;
/*  759: 788 */         buf.append(" as ").append(NameGenerator.scalarName(x, 0));
/*  760:     */       }
/*  761:     */     }
/*  762: 794 */     return buf.toString();
/*  763:     */   }
/*  764:     */   
/*  765:     */   private void mergeJoins(JoinFragment ojf)
/*  766:     */     throws MappingException, QueryException
/*  767:     */   {
/*  768: 799 */     Iterator iter = this.joins.entrySet().iterator();
/*  769: 800 */     while (iter.hasNext())
/*  770:     */     {
/*  771: 801 */       Map.Entry me = (Map.Entry)iter.next();
/*  772: 802 */       String name = (String)me.getKey();
/*  773: 803 */       JoinSequence join = (JoinSequence)me.getValue();
/*  774: 804 */       join.setSelector(new JoinSequence.Selector()
/*  775:     */       {
/*  776:     */         public boolean includeSubclasses(String alias)
/*  777:     */         {
/*  778: 806 */           boolean include = (QueryTranslatorImpl.this.returnedTypes.contains(alias)) && (!QueryTranslatorImpl.this.isShallowQuery());
/*  779: 807 */           return include;
/*  780:     */         }
/*  781:     */       });
/*  782: 811 */       if (this.typeMap.containsKey(name)) {
/*  783: 812 */         ojf.addFragment(join.toJoinFragment(this.enabledFilters, true));
/*  784: 814 */       } else if (this.collections.containsKey(name)) {
/*  785: 815 */         ojf.addFragment(join.toJoinFragment(this.enabledFilters, true));
/*  786:     */       }
/*  787:     */     }
/*  788:     */   }
/*  789:     */   
/*  790:     */   public final Set getQuerySpaces()
/*  791:     */   {
/*  792: 826 */     return this.querySpaces;
/*  793:     */   }
/*  794:     */   
/*  795:     */   boolean isShallowQuery()
/*  796:     */   {
/*  797: 835 */     return this.shallowQuery;
/*  798:     */   }
/*  799:     */   
/*  800:     */   void addQuerySpaces(Serializable[] spaces)
/*  801:     */   {
/*  802: 839 */     for (int i = 0; i < spaces.length; i++) {
/*  803: 840 */       this.querySpaces.add(spaces[i]);
/*  804:     */     }
/*  805: 842 */     if (this.superQuery != null) {
/*  806: 842 */       this.superQuery.addQuerySpaces(spaces);
/*  807:     */     }
/*  808:     */   }
/*  809:     */   
/*  810:     */   void setDistinct(boolean distinct)
/*  811:     */   {
/*  812: 846 */     this.distinct = distinct;
/*  813:     */   }
/*  814:     */   
/*  815:     */   boolean isSubquery()
/*  816:     */   {
/*  817: 850 */     return this.superQuery != null;
/*  818:     */   }
/*  819:     */   
/*  820:     */   public CollectionPersister[] getCollectionPersisters()
/*  821:     */   {
/*  822: 858 */     return new CollectionPersister[] { this.collectionPersister == null ? null : this.collectionPersister };
/*  823:     */   }
/*  824:     */   
/*  825:     */   protected String[] getCollectionSuffixes()
/*  826:     */   {
/*  827: 863 */     return new String[] { this.collectionPersister == null ? null : "__" };
/*  828:     */   }
/*  829:     */   
/*  830:     */   void setCollectionToFetch(String role, String name, String ownerName, String entityName)
/*  831:     */     throws QueryException
/*  832:     */   {
/*  833: 868 */     this.fetchName = name;
/*  834: 869 */     this.collectionPersister = getCollectionPersister(role);
/*  835: 870 */     this.collectionOwnerName = ownerName;
/*  836: 871 */     if (this.collectionPersister.getElementType().isEntityType()) {
/*  837: 872 */       addEntityToFetch(entityName);
/*  838:     */     }
/*  839:     */   }
/*  840:     */   
/*  841:     */   protected String[] getSuffixes()
/*  842:     */   {
/*  843: 878 */     return this.suffixes;
/*  844:     */   }
/*  845:     */   
/*  846:     */   protected String[] getAliases()
/*  847:     */   {
/*  848: 883 */     return this.names;
/*  849:     */   }
/*  850:     */   
/*  851:     */   private void addFromAssociation(String elementName, String collectionRole)
/*  852:     */     throws QueryException
/*  853:     */   {
/*  854: 892 */     QueryableCollection persister = getCollectionPersister(collectionRole);
/*  855: 893 */     Type collectionElementType = persister.getElementType();
/*  856: 894 */     if (!collectionElementType.isEntityType()) {
/*  857: 895 */       throw new QueryException("collection of values in filter: " + elementName);
/*  858:     */     }
/*  859: 898 */     String[] keyColumnNames = persister.getKeyColumnNames();
/*  860:     */     
/*  861:     */ 
/*  862:     */ 
/*  863: 902 */     JoinSequence join = new JoinSequence(getFactory());
/*  864: 903 */     String collectionName = persister.isOneToMany() ? elementName : createNameForCollection(collectionRole);
/*  865:     */     
/*  866:     */ 
/*  867: 906 */     join.setRoot(persister, collectionName);
/*  868: 907 */     if (!persister.isOneToMany())
/*  869:     */     {
/*  870: 909 */       addCollection(collectionName, collectionRole);
/*  871:     */       try
/*  872:     */       {
/*  873: 911 */         join.addJoin((AssociationType)persister.getElementType(), elementName, JoinType.INNER_JOIN, persister.getElementColumnNames(collectionName));
/*  874:     */       }
/*  875:     */       catch (MappingException me)
/*  876:     */       {
/*  877: 917 */         throw new QueryException(me);
/*  878:     */       }
/*  879:     */     }
/*  880: 920 */     join.addCondition(collectionName, keyColumnNames, " = ?");
/*  881:     */     
/*  882: 922 */     EntityType elemType = (EntityType)collectionElementType;
/*  883: 923 */     addFrom(elementName, elemType.getAssociatedEntityName(), join);
/*  884:     */   }
/*  885:     */   
/*  886:     */   String getPathAlias(String path)
/*  887:     */   {
/*  888: 928 */     return (String)this.pathAliases.get(path);
/*  889:     */   }
/*  890:     */   
/*  891:     */   JoinSequence getPathJoin(String path)
/*  892:     */   {
/*  893: 932 */     return (JoinSequence)this.pathJoins.get(path);
/*  894:     */   }
/*  895:     */   
/*  896:     */   void addPathAliasAndJoin(String path, String alias, JoinSequence joinSequence)
/*  897:     */   {
/*  898: 936 */     this.pathAliases.put(path, alias);
/*  899: 937 */     this.pathJoins.put(path, joinSequence);
/*  900:     */   }
/*  901:     */   
/*  902:     */   public List list(SessionImplementor session, QueryParameters queryParameters)
/*  903:     */     throws HibernateException
/*  904:     */   {
/*  905: 942 */     return list(session, queryParameters, getQuerySpaces(), this.actualReturnTypes);
/*  906:     */   }
/*  907:     */   
/*  908:     */   public Iterator iterate(QueryParameters queryParameters, EventSource session)
/*  909:     */     throws HibernateException
/*  910:     */   {
/*  911: 951 */     boolean stats = session.getFactory().getStatistics().isStatisticsEnabled();
/*  912: 952 */     long startTime = 0L;
/*  913: 953 */     if (stats) {
/*  914: 953 */       startTime = System.currentTimeMillis();
/*  915:     */     }
/*  916:     */     try
/*  917:     */     {
/*  918: 957 */       PreparedStatement st = prepareQueryStatement(queryParameters, false, session);
/*  919: 958 */       ResultSet rs = getResultSet(st, queryParameters.hasAutoDiscoverScalarTypes(), false, queryParameters.getRowSelection(), session);
/*  920: 959 */       HolderInstantiator hi = HolderInstantiator.createClassicHolderInstantiator(this.holderConstructor, queryParameters.getResultTransformer());
/*  921: 960 */       Iterator result = new IteratorImpl(rs, st, session, queryParameters.isReadOnly(session), this.returnTypes, getColumnNames(), hi);
/*  922: 962 */       if (stats) {
/*  923: 963 */         session.getFactory().getStatisticsImplementor().queryExecuted("HQL: " + this.queryString, 0, System.currentTimeMillis() - startTime);
/*  924:     */       }
/*  925: 970 */       return result;
/*  926:     */     }
/*  927:     */     catch (SQLException sqle)
/*  928:     */     {
/*  929: 974 */       throw getFactory().getSQLExceptionHelper().convert(sqle, "could not execute query using iterate", getSQLString());
/*  930:     */     }
/*  931:     */   }
/*  932:     */   
/*  933:     */   public int executeUpdate(QueryParameters queryParameters, SessionImplementor session)
/*  934:     */     throws HibernateException
/*  935:     */   {
/*  936: 984 */     throw new UnsupportedOperationException("Not supported!  Use the AST translator...");
/*  937:     */   }
/*  938:     */   
/*  939:     */   protected boolean[] includeInResultRow()
/*  940:     */   {
/*  941: 989 */     boolean[] isResultReturned = this.includeInSelect;
/*  942: 990 */     if (this.hasScalars)
/*  943:     */     {
/*  944: 991 */       isResultReturned = new boolean[this.returnedTypes.size()];
/*  945: 992 */       Arrays.fill(isResultReturned, true);
/*  946:     */     }
/*  947: 994 */     return isResultReturned;
/*  948:     */   }
/*  949:     */   
/*  950:     */   protected ResultTransformer resolveResultTransformer(ResultTransformer resultTransformer)
/*  951:     */   {
/*  952:1000 */     return HolderInstantiator.resolveClassicResultTransformer(this.holderConstructor, resultTransformer);
/*  953:     */   }
/*  954:     */   
/*  955:     */   protected Object getResultColumnOrRow(Object[] row, ResultTransformer transformer, ResultSet rs, SessionImplementor session)
/*  956:     */     throws SQLException, HibernateException
/*  957:     */   {
/*  958:1009 */     Object[] resultRow = getResultRow(row, rs, session);
/*  959:1010 */     return (this.holderClass == null) && (resultRow.length == 1) ? resultRow[0] : resultRow;
/*  960:     */   }
/*  961:     */   
/*  962:     */   protected Object[] getResultRow(Object[] row, ResultSet rs, SessionImplementor session)
/*  963:     */     throws SQLException, HibernateException
/*  964:     */   {
/*  965:     */     Object[] resultRow;
/*  966:1020 */     if (this.hasScalars)
/*  967:     */     {
/*  968:1021 */       String[][] scalarColumns = getColumnNames();
/*  969:1022 */       int queryCols = this.returnTypes.length;
/*  970:1023 */       Object[] resultRow = new Object[queryCols];
/*  971:1024 */       for (int i = 0; i < queryCols; i++) {
/*  972:1025 */         resultRow[i] = this.returnTypes[i].nullSafeGet(rs, scalarColumns[i], session, null);
/*  973:     */       }
/*  974:     */     }
/*  975:     */     else
/*  976:     */     {
/*  977:1029 */       resultRow = toResultRow(row);
/*  978:     */     }
/*  979:1031 */     return resultRow;
/*  980:     */   }
/*  981:     */   
/*  982:     */   protected List getResultList(List results, ResultTransformer resultTransformer)
/*  983:     */     throws QueryException
/*  984:     */   {
/*  985:1036 */     if (this.holderClass != null) {
/*  986:1037 */       for (int i = 0; i < results.size(); i++)
/*  987:     */       {
/*  988:1038 */         Object[] row = (Object[])results.get(i);
/*  989:     */         try
/*  990:     */         {
/*  991:1040 */           results.set(i, this.holderConstructor.newInstance(row));
/*  992:     */         }
/*  993:     */         catch (Exception e)
/*  994:     */         {
/*  995:1043 */           throw new QueryException("could not instantiate: " + this.holderClass, e);
/*  996:     */         }
/*  997:     */       }
/*  998:     */     }
/*  999:1047 */     return results;
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   private Object[] toResultRow(Object[] row)
/* 1003:     */   {
/* 1004:1051 */     if (this.selectLength == row.length) {
/* 1005:1052 */       return row;
/* 1006:     */     }
/* 1007:1055 */     Object[] result = new Object[this.selectLength];
/* 1008:1056 */     int j = 0;
/* 1009:1057 */     for (int i = 0; i < row.length; i++) {
/* 1010:1058 */       if (this.includeInSelect[i] != 0) {
/* 1011:1058 */         result[(j++)] = row[i];
/* 1012:     */       }
/* 1013:     */     }
/* 1014:1060 */     return result;
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   void setHolderClass(Class clazz)
/* 1018:     */   {
/* 1019:1065 */     this.holderClass = clazz;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   protected LockMode[] getLockModes(LockOptions lockOptions)
/* 1023:     */   {
/* 1024:1074 */     HashMap nameLockOptions = new HashMap();
/* 1025:1075 */     if (lockOptions == null) {
/* 1026:1076 */       lockOptions = LockOptions.NONE;
/* 1027:     */     }
/* 1028:1079 */     if (lockOptions.getAliasLockCount() > 0)
/* 1029:     */     {
/* 1030:1080 */       Iterator iter = lockOptions.getAliasLockIterator();
/* 1031:1081 */       while (iter.hasNext())
/* 1032:     */       {
/* 1033:1082 */         Map.Entry me = (Map.Entry)iter.next();
/* 1034:1083 */         nameLockOptions.put(getAliasName((String)me.getKey()), me.getValue());
/* 1035:     */       }
/* 1036:     */     }
/* 1037:1087 */     LockMode[] lockModesArray = new LockMode[this.names.length];
/* 1038:1088 */     for (int i = 0; i < this.names.length; i++)
/* 1039:     */     {
/* 1040:1089 */       LockMode lm = (LockMode)nameLockOptions.get(this.names[i]);
/* 1041:1091 */       if (lm == null) {
/* 1042:1091 */         lm = lockOptions.getLockMode();
/* 1043:     */       }
/* 1044:1092 */       lockModesArray[i] = lm;
/* 1045:     */     }
/* 1046:1094 */     return lockModesArray;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   protected String applyLocks(String sql, LockOptions lockOptions, Dialect dialect)
/* 1050:     */     throws QueryException
/* 1051:     */   {
/* 1052:1101 */     if ((lockOptions == null) || ((lockOptions.getLockMode() == LockMode.NONE) && (lockOptions.getAliasLockCount() == 0))) {
/* 1053:1103 */       return sql;
/* 1054:     */     }
/* 1055:1106 */     LockOptions locks = new LockOptions();
/* 1056:1107 */     locks.setLockMode(lockOptions.getLockMode());
/* 1057:1108 */     locks.setTimeOut(lockOptions.getTimeOut());
/* 1058:1109 */     locks.setScope(lockOptions.getScope());
/* 1059:1110 */     Iterator iter = lockOptions.getAliasLockIterator();
/* 1060:1111 */     while (iter.hasNext())
/* 1061:     */     {
/* 1062:1112 */       Map.Entry me = (Map.Entry)iter.next();
/* 1063:1113 */       locks.setAliasSpecificLockMode(getAliasName((String)me.getKey()), (LockMode)me.getValue());
/* 1064:     */     }
/* 1065:1115 */     Map keyColumnNames = null;
/* 1066:1116 */     if (dialect.forUpdateOfColumns())
/* 1067:     */     {
/* 1068:1117 */       keyColumnNames = new HashMap();
/* 1069:1118 */       for (int i = 0; i < this.names.length; i++) {
/* 1070:1119 */         keyColumnNames.put(this.names[i], this.persisters[i].getIdentifierColumnNames());
/* 1071:     */       }
/* 1072:     */     }
/* 1073:1122 */     String result = dialect.applyLocksToSql(sql, locks, keyColumnNames);
/* 1074:     */     
/* 1075:1124 */     logQuery(this.queryString, result);
/* 1076:1125 */     return result;
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   protected boolean upgradeLocks()
/* 1080:     */   {
/* 1081:1130 */     return true;
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   protected int[] getCollectionOwners()
/* 1085:     */   {
/* 1086:1135 */     return new int[] { this.collectionOwnerColumn };
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   protected boolean isCompiled()
/* 1090:     */   {
/* 1091:1139 */     return this.compiled;
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public String toString()
/* 1095:     */   {
/* 1096:1144 */     return this.queryString;
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   protected int[] getOwners()
/* 1100:     */   {
/* 1101:1149 */     return this.owners;
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   protected EntityType[] getOwnerAssociationTypes()
/* 1105:     */   {
/* 1106:1154 */     return this.ownerAssociationTypes;
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public Class getHolderClass()
/* 1110:     */   {
/* 1111:1158 */     return this.holderClass;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public Map getEnabledFilters()
/* 1115:     */   {
/* 1116:1162 */     return this.enabledFilters;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public ScrollableResults scroll(QueryParameters queryParameters, SessionImplementor session)
/* 1120:     */     throws HibernateException
/* 1121:     */   {
/* 1122:1168 */     HolderInstantiator hi = HolderInstantiator.createClassicHolderInstantiator(this.holderConstructor, queryParameters.getResultTransformer());
/* 1123:     */     
/* 1124:     */ 
/* 1125:1171 */     return scroll(queryParameters, this.returnTypes, hi, session);
/* 1126:     */   }
/* 1127:     */   
/* 1128:     */   public String getQueryIdentifier()
/* 1129:     */   {
/* 1130:1176 */     return this.queryIdentifier;
/* 1131:     */   }
/* 1132:     */   
/* 1133:     */   protected boolean isSubselectLoadingEnabled()
/* 1134:     */   {
/* 1135:1181 */     return hasSubselectLoadableCollections();
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   public void validateScrollability()
/* 1139:     */     throws HibernateException
/* 1140:     */   {
/* 1141:1186 */     if (getCollectionPersisters() != null) {
/* 1142:1187 */       throw new HibernateException("Cannot scroll queries which initialize collections");
/* 1143:     */     }
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public boolean containsCollectionFetches()
/* 1147:     */   {
/* 1148:1192 */     return false;
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public boolean isManipulationStatement()
/* 1152:     */   {
/* 1153:1197 */     return false;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public Class getDynamicInstantiationResultType()
/* 1157:     */   {
/* 1158:1202 */     return this.holderClass;
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   public ParameterTranslations getParameterTranslations()
/* 1162:     */   {
/* 1163:1206 */     new ParameterTranslations()
/* 1164:     */     {
/* 1165:     */       public boolean supportsOrdinalParameterMetadata()
/* 1166:     */       {
/* 1167:1211 */         return false;
/* 1168:     */       }
/* 1169:     */       
/* 1170:     */       public int getOrdinalParameterCount()
/* 1171:     */       {
/* 1172:1215 */         return 0;
/* 1173:     */       }
/* 1174:     */       
/* 1175:     */       public int getOrdinalParameterSqlLocation(int ordinalPosition)
/* 1176:     */       {
/* 1177:1219 */         return 0;
/* 1178:     */       }
/* 1179:     */       
/* 1180:     */       public Type getOrdinalParameterExpectedType(int ordinalPosition)
/* 1181:     */       {
/* 1182:1223 */         return null;
/* 1183:     */       }
/* 1184:     */       
/* 1185:     */       public Set getNamedParameterNames()
/* 1186:     */       {
/* 1187:1227 */         return QueryTranslatorImpl.this.namedParameters.keySet();
/* 1188:     */       }
/* 1189:     */       
/* 1190:     */       public int[] getNamedParameterSqlLocations(String name)
/* 1191:     */       {
/* 1192:1231 */         return QueryTranslatorImpl.this.getNamedParameterLocs(name);
/* 1193:     */       }
/* 1194:     */       
/* 1195:     */       public Type getNamedParameterExpectedType(String name)
/* 1196:     */       {
/* 1197:1235 */         return null;
/* 1198:     */       }
/* 1199:     */     };
/* 1200:     */   }
/* 1201:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.QueryTranslatorImpl
 * JD-Core Version:    0.7.0.1
 */