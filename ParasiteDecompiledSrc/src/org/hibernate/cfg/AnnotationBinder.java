/*    1:     */ package org.hibernate.cfg;
/*    2:     */ 
/*    3:     */ import java.lang.annotation.Annotation;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.Arrays;
/*    6:     */ import java.util.Collection;
/*    7:     */ import java.util.EnumSet;
/*    8:     */ import java.util.HashMap;
/*    9:     */ import java.util.HashSet;
/*   10:     */ import java.util.Iterator;
/*   11:     */ import java.util.List;
/*   12:     */ import java.util.Map;
/*   13:     */ import java.util.Properties;
/*   14:     */ import java.util.Set;
/*   15:     */ import javax.persistence.Basic;
/*   16:     */ import javax.persistence.Cacheable;
/*   17:     */ import javax.persistence.CollectionTable;
/*   18:     */ import javax.persistence.DiscriminatorColumn;
/*   19:     */ import javax.persistence.DiscriminatorType;
/*   20:     */ import javax.persistence.DiscriminatorValue;
/*   21:     */ import javax.persistence.ElementCollection;
/*   22:     */ import javax.persistence.Embeddable;
/*   23:     */ import javax.persistence.Embedded;
/*   24:     */ import javax.persistence.EmbeddedId;
/*   25:     */ import javax.persistence.FetchType;
/*   26:     */ import javax.persistence.GeneratedValue;
/*   27:     */ import javax.persistence.GenerationType;
/*   28:     */ import javax.persistence.Id;
/*   29:     */ import javax.persistence.IdClass;
/*   30:     */ import javax.persistence.InheritanceType;
/*   31:     */ import javax.persistence.JoinColumn;
/*   32:     */ import javax.persistence.JoinTable;
/*   33:     */ import javax.persistence.ManyToMany;
/*   34:     */ import javax.persistence.MapKey;
/*   35:     */ import javax.persistence.MapKeyColumn;
/*   36:     */ import javax.persistence.MapKeyJoinColumn;
/*   37:     */ import javax.persistence.MapKeyJoinColumns;
/*   38:     */ import javax.persistence.MapsId;
/*   39:     */ import javax.persistence.OneToMany;
/*   40:     */ import javax.persistence.OneToOne;
/*   41:     */ import javax.persistence.OrderColumn;
/*   42:     */ import javax.persistence.PrimaryKeyJoinColumn;
/*   43:     */ import javax.persistence.PrimaryKeyJoinColumns;
/*   44:     */ import javax.persistence.SecondaryTable;
/*   45:     */ import javax.persistence.SecondaryTables;
/*   46:     */ import javax.persistence.SequenceGenerator;
/*   47:     */ import javax.persistence.SharedCacheMode;
/*   48:     */ import javax.persistence.SqlResultSetMapping;
/*   49:     */ import javax.persistence.SqlResultSetMappings;
/*   50:     */ import javax.persistence.UniqueConstraint;
/*   51:     */ import javax.persistence.Version;
/*   52:     */ import org.hibernate.AnnotationException;
/*   53:     */ import org.hibernate.AssertionFailure;
/*   54:     */ import org.hibernate.EntityMode;
/*   55:     */ import org.hibernate.MappingException;
/*   56:     */ import org.hibernate.annotations.BatchSize;
/*   57:     */ import org.hibernate.annotations.Cache;
/*   58:     */ import org.hibernate.annotations.CacheConcurrencyStrategy;
/*   59:     */ import org.hibernate.annotations.Cascade;
/*   60:     */ import org.hibernate.annotations.Check;
/*   61:     */ import org.hibernate.annotations.CollectionId;
/*   62:     */ import org.hibernate.annotations.Columns;
/*   63:     */ import org.hibernate.annotations.DiscriminatorFormula;
/*   64:     */ import org.hibernate.annotations.DiscriminatorOptions;
/*   65:     */ import org.hibernate.annotations.Fetch;
/*   66:     */ import org.hibernate.annotations.FetchProfile;
/*   67:     */ import org.hibernate.annotations.FetchProfile.FetchOverride;
/*   68:     */ import org.hibernate.annotations.FetchProfiles;
/*   69:     */ import org.hibernate.annotations.Filter;
/*   70:     */ import org.hibernate.annotations.FilterDef;
/*   71:     */ import org.hibernate.annotations.FilterDefs;
/*   72:     */ import org.hibernate.annotations.Filters;
/*   73:     */ import org.hibernate.annotations.ForeignKey;
/*   74:     */ import org.hibernate.annotations.Formula;
/*   75:     */ import org.hibernate.annotations.GenericGenerator;
/*   76:     */ import org.hibernate.annotations.GenericGenerators;
/*   77:     */ import org.hibernate.annotations.Index;
/*   78:     */ import org.hibernate.annotations.LazyToOne;
/*   79:     */ import org.hibernate.annotations.LazyToOneOption;
/*   80:     */ import org.hibernate.annotations.ManyToAny;
/*   81:     */ import org.hibernate.annotations.MapKeyType;
/*   82:     */ import org.hibernate.annotations.NaturalId;
/*   83:     */ import org.hibernate.annotations.NotFound;
/*   84:     */ import org.hibernate.annotations.NotFoundAction;
/*   85:     */ import org.hibernate.annotations.OnDelete;
/*   86:     */ import org.hibernate.annotations.OnDeleteAction;
/*   87:     */ import org.hibernate.annotations.ParamDef;
/*   88:     */ import org.hibernate.annotations.Parameter;
/*   89:     */ import org.hibernate.annotations.Parent;
/*   90:     */ import org.hibernate.annotations.Proxy;
/*   91:     */ import org.hibernate.annotations.Sort;
/*   92:     */ import org.hibernate.annotations.Source;
/*   93:     */ import org.hibernate.annotations.SourceType;
/*   94:     */ import org.hibernate.annotations.Tables;
/*   95:     */ import org.hibernate.annotations.Tuplizer;
/*   96:     */ import org.hibernate.annotations.Tuplizers;
/*   97:     */ import org.hibernate.annotations.TypeDef;
/*   98:     */ import org.hibernate.annotations.TypeDefs;
/*   99:     */ import org.hibernate.annotations.Where;
/*  100:     */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  101:     */ import org.hibernate.annotations.common.reflection.XAnnotatedElement;
/*  102:     */ import org.hibernate.annotations.common.reflection.XClass;
/*  103:     */ import org.hibernate.annotations.common.reflection.XMethod;
/*  104:     */ import org.hibernate.annotations.common.reflection.XPackage;
/*  105:     */ import org.hibernate.annotations.common.reflection.XProperty;
/*  106:     */ import org.hibernate.cache.spi.RegionFactory;
/*  107:     */ import org.hibernate.cfg.annotations.CollectionBinder;
/*  108:     */ import org.hibernate.cfg.annotations.EntityBinder;
/*  109:     */ import org.hibernate.cfg.annotations.MapKeyColumnDelegator;
/*  110:     */ import org.hibernate.cfg.annotations.MapKeyJoinColumnDelegator;
/*  111:     */ import org.hibernate.cfg.annotations.Nullability;
/*  112:     */ import org.hibernate.cfg.annotations.PropertyBinder;
/*  113:     */ import org.hibernate.cfg.annotations.QueryBinder;
/*  114:     */ import org.hibernate.cfg.annotations.SimpleValueBinder;
/*  115:     */ import org.hibernate.cfg.annotations.TableBinder;
/*  116:     */ import org.hibernate.engine.spi.FilterDefinition;
/*  117:     */ import org.hibernate.id.MultipleHiLoPerTableGenerator;
/*  118:     */ import org.hibernate.id.enhanced.SequenceStyleGenerator;
/*  119:     */ import org.hibernate.internal.CoreMessageLogger;
/*  120:     */ import org.hibernate.mapping.Component;
/*  121:     */ import org.hibernate.mapping.DependantValue;
/*  122:     */ import org.hibernate.mapping.IdGenerator;
/*  123:     */ import org.hibernate.mapping.Join;
/*  124:     */ import org.hibernate.mapping.JoinedSubclass;
/*  125:     */ import org.hibernate.mapping.KeyValue;
/*  126:     */ import org.hibernate.mapping.PersistentClass;
/*  127:     */ import org.hibernate.mapping.Property;
/*  128:     */ import org.hibernate.mapping.RootClass;
/*  129:     */ import org.hibernate.mapping.SimpleValue;
/*  130:     */ import org.hibernate.mapping.SingleTableSubclass;
/*  131:     */ import org.hibernate.mapping.Subclass;
/*  132:     */ import org.hibernate.mapping.ToOne;
/*  133:     */ import org.hibernate.mapping.UnionSubclass;
/*  134:     */ import org.hibernate.type.Type;
/*  135:     */ import org.hibernate.type.TypeResolver;
/*  136:     */ import org.jboss.logging.Logger;
/*  137:     */ 
/*  138:     */ public final class AnnotationBinder
/*  139:     */ {
/*  140: 182 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AnnotationBinder.class.getName());
/*  141:     */   private static CacheConcurrencyStrategy DEFAULT_CACHE_CONCURRENCY_STRATEGY;
/*  142:     */   
/*  143:     */   public static void bindDefaults(Mappings mappings)
/*  144:     */   {
/*  145: 202 */     Map defaults = mappings.getReflectionManager().getDefaults();
/*  146:     */     
/*  147: 204 */     List<SequenceGenerator> anns = (List)defaults.get(SequenceGenerator.class);
/*  148: 205 */     if (anns != null) {
/*  149: 206 */       for (SequenceGenerator ann : anns)
/*  150:     */       {
/*  151: 207 */         IdGenerator idGen = buildIdGenerator(ann, mappings);
/*  152: 208 */         if (idGen != null) {
/*  153: 209 */           mappings.addDefaultGenerator(idGen);
/*  154:     */         }
/*  155:     */       }
/*  156:     */     }
/*  157: 215 */     List<javax.persistence.TableGenerator> anns = (List)defaults.get(javax.persistence.TableGenerator.class);
/*  158: 216 */     if (anns != null) {
/*  159: 217 */       for (javax.persistence.TableGenerator ann : anns)
/*  160:     */       {
/*  161: 218 */         IdGenerator idGen = buildIdGenerator(ann, mappings);
/*  162: 219 */         if (idGen != null) {
/*  163: 220 */           mappings.addDefaultGenerator(idGen);
/*  164:     */         }
/*  165:     */       }
/*  166:     */     }
/*  167: 226 */     List<javax.persistence.NamedQuery> anns = (List)defaults.get(javax.persistence.NamedQuery.class);
/*  168: 227 */     if (anns != null) {
/*  169: 228 */       for (javax.persistence.NamedQuery ann : anns) {
/*  170: 229 */         QueryBinder.bindQuery(ann, mappings, true);
/*  171:     */       }
/*  172:     */     }
/*  173: 234 */     List<javax.persistence.NamedNativeQuery> anns = (List)defaults.get(javax.persistence.NamedNativeQuery.class);
/*  174: 235 */     if (anns != null) {
/*  175: 236 */       for (javax.persistence.NamedNativeQuery ann : anns) {
/*  176: 237 */         QueryBinder.bindNativeQuery(ann, mappings, true);
/*  177:     */       }
/*  178:     */     }
/*  179: 242 */     List<SqlResultSetMapping> anns = (List)defaults.get(SqlResultSetMapping.class);
/*  180: 243 */     if (anns != null) {
/*  181: 244 */       for (SqlResultSetMapping ann : anns) {
/*  182: 245 */         QueryBinder.bindSqlResultsetMapping(ann, mappings, true);
/*  183:     */       }
/*  184:     */     }
/*  185:     */   }
/*  186:     */   
/*  187:     */   public static void bindPackage(String packageName, Mappings mappings)
/*  188:     */   {
/*  189:     */     XPackage pckg;
/*  190:     */     try
/*  191:     */     {
/*  192: 254 */       pckg = mappings.getReflectionManager().packageForName(packageName);
/*  193:     */     }
/*  194:     */     catch (ClassNotFoundException cnf)
/*  195:     */     {
/*  196: 257 */       LOG.packageNotFound(packageName);
/*  197: 258 */       return;
/*  198:     */     }
/*  199: 260 */     if (pckg.isAnnotationPresent(SequenceGenerator.class))
/*  200:     */     {
/*  201: 261 */       SequenceGenerator ann = (SequenceGenerator)pckg.getAnnotation(SequenceGenerator.class);
/*  202: 262 */       IdGenerator idGen = buildIdGenerator(ann, mappings);
/*  203: 263 */       mappings.addGenerator(idGen);
/*  204: 264 */       if (LOG.isTraceEnabled()) {
/*  205: 265 */         LOG.tracev("Add sequence generator with name: {0}", idGen.getName());
/*  206:     */       }
/*  207:     */     }
/*  208: 268 */     if (pckg.isAnnotationPresent(javax.persistence.TableGenerator.class))
/*  209:     */     {
/*  210: 269 */       javax.persistence.TableGenerator ann = (javax.persistence.TableGenerator)pckg.getAnnotation(javax.persistence.TableGenerator.class);
/*  211: 270 */       IdGenerator idGen = buildIdGenerator(ann, mappings);
/*  212: 271 */       mappings.addGenerator(idGen);
/*  213:     */     }
/*  214: 274 */     bindGenericGenerators(pckg, mappings);
/*  215: 275 */     bindQueries(pckg, mappings);
/*  216: 276 */     bindFilterDefs(pckg, mappings);
/*  217: 277 */     bindTypeDefs(pckg, mappings);
/*  218: 278 */     bindFetchProfiles(pckg, mappings);
/*  219: 279 */     BinderHelper.bindAnyMetaDefs(pckg, mappings);
/*  220:     */   }
/*  221:     */   
/*  222:     */   private static void bindGenericGenerators(XAnnotatedElement annotatedElement, Mappings mappings)
/*  223:     */   {
/*  224: 283 */     GenericGenerator defAnn = (GenericGenerator)annotatedElement.getAnnotation(GenericGenerator.class);
/*  225: 284 */     GenericGenerators defsAnn = (GenericGenerators)annotatedElement.getAnnotation(GenericGenerators.class);
/*  226: 285 */     if (defAnn != null) {
/*  227: 286 */       bindGenericGenerator(defAnn, mappings);
/*  228:     */     }
/*  229: 288 */     if (defsAnn != null) {
/*  230: 289 */       for (GenericGenerator def : defsAnn.value()) {
/*  231: 290 */         bindGenericGenerator(def, mappings);
/*  232:     */       }
/*  233:     */     }
/*  234:     */   }
/*  235:     */   
/*  236:     */   private static void bindGenericGenerator(GenericGenerator def, Mappings mappings)
/*  237:     */   {
/*  238: 296 */     IdGenerator idGen = buildIdGenerator(def, mappings);
/*  239: 297 */     mappings.addGenerator(idGen);
/*  240:     */   }
/*  241:     */   
/*  242:     */   private static void bindQueries(XAnnotatedElement annotatedElement, Mappings mappings)
/*  243:     */   {
/*  244: 302 */     SqlResultSetMapping ann = (SqlResultSetMapping)annotatedElement.getAnnotation(SqlResultSetMapping.class);
/*  245: 303 */     QueryBinder.bindSqlResultsetMapping(ann, mappings, false);
/*  246:     */     
/*  247:     */ 
/*  248: 306 */     SqlResultSetMappings ann = (SqlResultSetMappings)annotatedElement.getAnnotation(SqlResultSetMappings.class);
/*  249: 307 */     if (ann != null) {
/*  250: 308 */       for (SqlResultSetMapping current : ann.value()) {
/*  251: 309 */         QueryBinder.bindSqlResultsetMapping(current, mappings, false);
/*  252:     */       }
/*  253:     */     }
/*  254: 314 */     javax.persistence.NamedQuery ann = (javax.persistence.NamedQuery)annotatedElement.getAnnotation(javax.persistence.NamedQuery.class);
/*  255: 315 */     QueryBinder.bindQuery(ann, mappings, false);
/*  256:     */     
/*  257:     */ 
/*  258: 318 */     org.hibernate.annotations.NamedQuery ann = (org.hibernate.annotations.NamedQuery)annotatedElement.getAnnotation(org.hibernate.annotations.NamedQuery.class);
/*  259:     */     
/*  260:     */ 
/*  261: 321 */     QueryBinder.bindQuery(ann, mappings);
/*  262:     */     
/*  263:     */ 
/*  264: 324 */     javax.persistence.NamedQueries ann = (javax.persistence.NamedQueries)annotatedElement.getAnnotation(javax.persistence.NamedQueries.class);
/*  265: 325 */     QueryBinder.bindQueries(ann, mappings, false);
/*  266:     */     
/*  267:     */ 
/*  268: 328 */     org.hibernate.annotations.NamedQueries ann = (org.hibernate.annotations.NamedQueries)annotatedElement.getAnnotation(org.hibernate.annotations.NamedQueries.class);
/*  269:     */     
/*  270:     */ 
/*  271: 331 */     QueryBinder.bindQueries(ann, mappings);
/*  272:     */     
/*  273:     */ 
/*  274: 334 */     javax.persistence.NamedNativeQuery ann = (javax.persistence.NamedNativeQuery)annotatedElement.getAnnotation(javax.persistence.NamedNativeQuery.class);
/*  275: 335 */     QueryBinder.bindNativeQuery(ann, mappings, false);
/*  276:     */     
/*  277:     */ 
/*  278: 338 */     org.hibernate.annotations.NamedNativeQuery ann = (org.hibernate.annotations.NamedNativeQuery)annotatedElement.getAnnotation(org.hibernate.annotations.NamedNativeQuery.class);
/*  279:     */     
/*  280:     */ 
/*  281: 341 */     QueryBinder.bindNativeQuery(ann, mappings);
/*  282:     */     
/*  283:     */ 
/*  284: 344 */     javax.persistence.NamedNativeQueries ann = (javax.persistence.NamedNativeQueries)annotatedElement.getAnnotation(javax.persistence.NamedNativeQueries.class);
/*  285: 345 */     QueryBinder.bindNativeQueries(ann, mappings, false);
/*  286:     */     
/*  287:     */ 
/*  288: 348 */     org.hibernate.annotations.NamedNativeQueries ann = (org.hibernate.annotations.NamedNativeQueries)annotatedElement.getAnnotation(org.hibernate.annotations.NamedNativeQueries.class);
/*  289:     */     
/*  290:     */ 
/*  291: 351 */     QueryBinder.bindNativeQueries(ann, mappings);
/*  292:     */   }
/*  293:     */   
/*  294:     */   private static IdGenerator buildIdGenerator(Annotation ann, Mappings mappings)
/*  295:     */   {
/*  296: 356 */     IdGenerator idGen = new IdGenerator();
/*  297: 357 */     if (mappings.getSchemaName() != null) {
/*  298: 358 */       idGen.addParam("schema", mappings.getSchemaName());
/*  299:     */     }
/*  300: 360 */     if (mappings.getCatalogName() != null) {
/*  301: 361 */       idGen.addParam("catalog", mappings.getCatalogName());
/*  302:     */     }
/*  303: 363 */     boolean useNewGeneratorMappings = mappings.useNewGeneratorMappings();
/*  304: 364 */     if (ann == null)
/*  305:     */     {
/*  306: 365 */       idGen = null;
/*  307:     */     }
/*  308: 367 */     else if ((ann instanceof javax.persistence.TableGenerator))
/*  309:     */     {
/*  310: 368 */       javax.persistence.TableGenerator tabGen = (javax.persistence.TableGenerator)ann;
/*  311: 369 */       idGen.setName(tabGen.name());
/*  312: 370 */       if (useNewGeneratorMappings)
/*  313:     */       {
/*  314: 371 */         idGen.setIdentifierGeneratorStrategy(org.hibernate.id.enhanced.TableGenerator.class.getName());
/*  315: 372 */         idGen.addParam("prefer_entity_table_as_segment_value", "true");
/*  316: 374 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.catalog())) {
/*  317: 375 */           idGen.addParam("catalog", tabGen.catalog());
/*  318:     */         }
/*  319: 377 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.schema())) {
/*  320: 378 */           idGen.addParam("schema", tabGen.schema());
/*  321:     */         }
/*  322: 380 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.table())) {
/*  323: 381 */           idGen.addParam("table_name", tabGen.table());
/*  324:     */         }
/*  325: 383 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.pkColumnName())) {
/*  326: 384 */           idGen.addParam("segment_column_name", tabGen.pkColumnName());
/*  327:     */         }
/*  328: 388 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.pkColumnValue())) {
/*  329: 389 */           idGen.addParam("segment_value", tabGen.pkColumnValue());
/*  330:     */         }
/*  331: 393 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.valueColumnName())) {
/*  332: 394 */           idGen.addParam("value_column_name", tabGen.valueColumnName());
/*  333:     */         }
/*  334: 398 */         idGen.addParam("increment_size", String.valueOf(tabGen.allocationSize()));
/*  335:     */         
/*  336:     */ 
/*  337:     */ 
/*  338:     */ 
/*  339: 403 */         idGen.addParam("initial_value", String.valueOf(tabGen.initialValue() + 1));
/*  340: 407 */         if ((tabGen.uniqueConstraints() != null) && (tabGen.uniqueConstraints().length > 0)) {
/*  341: 407 */           LOG.warn(tabGen.name());
/*  342:     */         }
/*  343:     */       }
/*  344:     */       else
/*  345:     */       {
/*  346: 410 */         idGen.setIdentifierGeneratorStrategy(MultipleHiLoPerTableGenerator.class.getName());
/*  347: 412 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.table())) {
/*  348: 413 */           idGen.addParam("table", tabGen.table());
/*  349:     */         }
/*  350: 415 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.catalog())) {
/*  351: 416 */           idGen.addParam("catalog", tabGen.catalog());
/*  352:     */         }
/*  353: 418 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.schema())) {
/*  354: 419 */           idGen.addParam("schema", tabGen.schema());
/*  355:     */         }
/*  356: 422 */         if ((tabGen.uniqueConstraints() != null) && (tabGen.uniqueConstraints().length > 0)) {
/*  357: 422 */           LOG.ignoringTableGeneratorConstraints(tabGen.name());
/*  358:     */         }
/*  359: 424 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.pkColumnName())) {
/*  360: 425 */           idGen.addParam("primary_key_column", tabGen.pkColumnName());
/*  361:     */         }
/*  362: 427 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.valueColumnName())) {
/*  363: 428 */           idGen.addParam("value_column", tabGen.valueColumnName());
/*  364:     */         }
/*  365: 430 */         if (!BinderHelper.isEmptyAnnotationValue(tabGen.pkColumnValue())) {
/*  366: 431 */           idGen.addParam("primary_key_value", tabGen.pkColumnValue());
/*  367:     */         }
/*  368: 433 */         idGen.addParam("max_lo", String.valueOf(tabGen.allocationSize() - 1));
/*  369:     */       }
/*  370: 435 */       if (LOG.isTraceEnabled()) {
/*  371: 436 */         LOG.tracev("Add table generator with name: {0}", idGen.getName());
/*  372:     */       }
/*  373:     */     }
/*  374: 439 */     else if ((ann instanceof SequenceGenerator))
/*  375:     */     {
/*  376: 440 */       SequenceGenerator seqGen = (SequenceGenerator)ann;
/*  377: 441 */       idGen.setName(seqGen.name());
/*  378: 442 */       if (useNewGeneratorMappings)
/*  379:     */       {
/*  380: 443 */         idGen.setIdentifierGeneratorStrategy(SequenceStyleGenerator.class.getName());
/*  381: 445 */         if (!BinderHelper.isEmptyAnnotationValue(seqGen.catalog())) {
/*  382: 446 */           idGen.addParam("catalog", seqGen.catalog());
/*  383:     */         }
/*  384: 448 */         if (!BinderHelper.isEmptyAnnotationValue(seqGen.schema())) {
/*  385: 449 */           idGen.addParam("schema", seqGen.schema());
/*  386:     */         }
/*  387: 451 */         if (!BinderHelper.isEmptyAnnotationValue(seqGen.sequenceName())) {
/*  388: 452 */           idGen.addParam("sequence_name", seqGen.sequenceName());
/*  389:     */         }
/*  390: 454 */         idGen.addParam("increment_size", String.valueOf(seqGen.allocationSize()));
/*  391: 455 */         idGen.addParam("initial_value", String.valueOf(seqGen.initialValue()));
/*  392:     */       }
/*  393:     */       else
/*  394:     */       {
/*  395: 458 */         idGen.setIdentifierGeneratorStrategy("seqhilo");
/*  396: 460 */         if (!BinderHelper.isEmptyAnnotationValue(seqGen.sequenceName())) {
/*  397: 461 */           idGen.addParam("sequence", seqGen.sequenceName());
/*  398:     */         }
/*  399: 465 */         if (seqGen.initialValue() != 1) {
/*  400: 466 */           LOG.unsupportedInitialValue("hibernate.id.new_generator_mappings");
/*  401:     */         }
/*  402: 468 */         idGen.addParam("max_lo", String.valueOf(seqGen.allocationSize() - 1));
/*  403: 469 */         if (LOG.isTraceEnabled()) {
/*  404: 470 */           LOG.tracev("Add sequence generator with name: {0}", idGen.getName());
/*  405:     */         }
/*  406:     */       }
/*  407:     */     }
/*  408: 474 */     else if ((ann instanceof GenericGenerator))
/*  409:     */     {
/*  410: 475 */       GenericGenerator genGen = (GenericGenerator)ann;
/*  411: 476 */       idGen.setName(genGen.name());
/*  412: 477 */       idGen.setIdentifierGeneratorStrategy(genGen.strategy());
/*  413: 478 */       Parameter[] params = genGen.parameters();
/*  414: 479 */       for (Parameter parameter : params) {
/*  415: 480 */         idGen.addParam(parameter.name(), parameter.value());
/*  416:     */       }
/*  417: 482 */       if (LOG.isTraceEnabled()) {
/*  418: 483 */         LOG.tracev("Add generic generator with name: {0}", idGen.getName());
/*  419:     */       }
/*  420:     */     }
/*  421:     */     else
/*  422:     */     {
/*  423: 487 */       throw new AssertionFailure("Unknown Generator annotation: " + ann);
/*  424:     */     }
/*  425: 489 */     return idGen;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public static void bindClass(XClass clazzToProcess, Map<XClass, InheritanceState> inheritanceStatePerClass, Mappings mappings)
/*  429:     */     throws MappingException
/*  430:     */   {
/*  431: 506 */     if ((clazzToProcess.isAnnotationPresent(javax.persistence.Entity.class)) && (clazzToProcess.isAnnotationPresent(javax.persistence.MappedSuperclass.class))) {
/*  432: 508 */       throw new AnnotationException("An entity cannot be annotated with both @Entity and @MappedSuperclass: " + clazzToProcess.getName());
/*  433:     */     }
/*  434: 513 */     InheritanceState inheritanceState = (InheritanceState)inheritanceStatePerClass.get(clazzToProcess);
/*  435: 514 */     AnnotatedClassType classType = mappings.getClassType(clazzToProcess);
/*  436: 517 */     if (AnnotatedClassType.EMBEDDABLE_SUPERCLASS.equals(classType))
/*  437:     */     {
/*  438: 518 */       bindQueries(clazzToProcess, mappings);
/*  439: 519 */       bindTypeDefs(clazzToProcess, mappings);
/*  440: 520 */       bindFilterDefs(clazzToProcess, mappings);
/*  441:     */     }
/*  442: 523 */     if (!isEntityClassType(clazzToProcess, classType)) {
/*  443: 524 */       return;
/*  444:     */     }
/*  445: 527 */     if (LOG.isDebugEnabled()) {
/*  446: 528 */       LOG.debugf("Binding entity from annotated class: %s", clazzToProcess.getName());
/*  447:     */     }
/*  448: 531 */     PersistentClass superEntity = getSuperEntity(clazzToProcess, inheritanceStatePerClass, mappings, inheritanceState);
/*  449:     */     
/*  450:     */ 
/*  451:     */ 
/*  452: 535 */     PersistentClass persistentClass = makePersistentClass(inheritanceState, superEntity);
/*  453: 536 */     javax.persistence.Entity entityAnn = (javax.persistence.Entity)clazzToProcess.getAnnotation(javax.persistence.Entity.class);
/*  454: 537 */     org.hibernate.annotations.Entity hibEntityAnn = (org.hibernate.annotations.Entity)clazzToProcess.getAnnotation(org.hibernate.annotations.Entity.class);
/*  455:     */     
/*  456:     */ 
/*  457: 540 */     EntityBinder entityBinder = new EntityBinder(entityAnn, hibEntityAnn, clazzToProcess, persistentClass, mappings);
/*  458:     */     
/*  459:     */ 
/*  460: 543 */     entityBinder.setInheritanceState(inheritanceState);
/*  461:     */     
/*  462: 545 */     bindQueries(clazzToProcess, mappings);
/*  463: 546 */     bindFilterDefs(clazzToProcess, mappings);
/*  464: 547 */     bindTypeDefs(clazzToProcess, mappings);
/*  465: 548 */     bindFetchProfiles(clazzToProcess, mappings);
/*  466: 549 */     BinderHelper.bindAnyMetaDefs(clazzToProcess, mappings);
/*  467:     */     
/*  468: 551 */     String schema = "";
/*  469: 552 */     String table = "";
/*  470: 553 */     String catalog = "";
/*  471: 554 */     List<UniqueConstraintHolder> uniqueConstraints = new ArrayList();
/*  472: 555 */     if (clazzToProcess.isAnnotationPresent(javax.persistence.Table.class))
/*  473:     */     {
/*  474: 556 */       javax.persistence.Table tabAnn = (javax.persistence.Table)clazzToProcess.getAnnotation(javax.persistence.Table.class);
/*  475: 557 */       table = tabAnn.name();
/*  476: 558 */       schema = tabAnn.schema();
/*  477: 559 */       catalog = tabAnn.catalog();
/*  478: 560 */       uniqueConstraints = TableBinder.buildUniqueConstraintHolders(tabAnn.uniqueConstraints());
/*  479:     */     }
/*  480: 563 */     Ejb3JoinColumn[] inheritanceJoinedColumns = makeInheritanceJoinColumns(clazzToProcess, mappings, inheritanceState, superEntity);
/*  481:     */     
/*  482:     */ 
/*  483: 566 */     Ejb3DiscriminatorColumn discriminatorColumn = null;
/*  484: 567 */     if (InheritanceType.SINGLE_TABLE.equals(inheritanceState.getType())) {
/*  485: 568 */       discriminatorColumn = processDiscriminatorProperties(clazzToProcess, mappings, inheritanceState, entityBinder);
/*  486:     */     }
/*  487: 573 */     entityBinder.setProxy((Proxy)clazzToProcess.getAnnotation(Proxy.class));
/*  488: 574 */     entityBinder.setBatchSize((BatchSize)clazzToProcess.getAnnotation(BatchSize.class));
/*  489: 575 */     entityBinder.setWhere((Where)clazzToProcess.getAnnotation(Where.class));
/*  490: 576 */     entityBinder.setCache(determineCacheSettings(clazzToProcess, mappings));
/*  491: 579 */     if (!inheritanceState.hasParents()) {
/*  492: 580 */       bindFilters(clazzToProcess, entityBinder, mappings);
/*  493:     */     }
/*  494: 583 */     entityBinder.bindEntity();
/*  495: 585 */     if (inheritanceState.hasTable())
/*  496:     */     {
/*  497: 586 */       Check checkAnn = (Check)clazzToProcess.getAnnotation(Check.class);
/*  498: 587 */       String constraints = checkAnn == null ? null : checkAnn.constraints();
/*  499:     */       
/*  500:     */ 
/*  501: 590 */       entityBinder.bindTable(schema, catalog, table, uniqueConstraints, constraints, inheritanceState.hasDenormalizedTable() ? superEntity.getTable() : null);
/*  502:     */     }
/*  503: 597 */     else if (clazzToProcess.isAnnotationPresent(javax.persistence.Table.class))
/*  504:     */     {
/*  505: 598 */       LOG.invalidTableAnnotation(clazzToProcess.getName());
/*  506:     */     }
/*  507: 602 */     PropertyHolder propertyHolder = PropertyHolderBuilder.buildPropertyHolder(clazzToProcess, persistentClass, entityBinder, mappings, inheritanceStatePerClass);
/*  508:     */     
/*  509:     */ 
/*  510:     */ 
/*  511:     */ 
/*  512:     */ 
/*  513: 608 */     SecondaryTable secTabAnn = (SecondaryTable)clazzToProcess.getAnnotation(SecondaryTable.class);
/*  514:     */     
/*  515:     */ 
/*  516: 611 */     SecondaryTables secTabsAnn = (SecondaryTables)clazzToProcess.getAnnotation(SecondaryTables.class);
/*  517:     */     
/*  518:     */ 
/*  519: 614 */     entityBinder.firstLevelSecondaryTablesBinding(secTabAnn, secTabsAnn);
/*  520:     */     
/*  521: 616 */     OnDelete onDeleteAnn = (OnDelete)clazzToProcess.getAnnotation(OnDelete.class);
/*  522: 617 */     boolean onDeleteAppropriate = false;
/*  523: 618 */     if ((InheritanceType.JOINED.equals(inheritanceState.getType())) && (inheritanceState.hasParents()))
/*  524:     */     {
/*  525: 619 */       onDeleteAppropriate = true;
/*  526: 620 */       JoinedSubclass jsc = (JoinedSubclass)persistentClass;
/*  527: 621 */       SimpleValue key = new DependantValue(mappings, jsc.getTable(), jsc.getIdentifier());
/*  528: 622 */       jsc.setKey(key);
/*  529: 623 */       ForeignKey fk = (ForeignKey)clazzToProcess.getAnnotation(ForeignKey.class);
/*  530: 624 */       if ((fk != null) && (!BinderHelper.isEmptyAnnotationValue(fk.name()))) {
/*  531: 625 */         key.setForeignKeyName(fk.name());
/*  532:     */       }
/*  533: 627 */       if (onDeleteAnn != null) {
/*  534: 628 */         key.setCascadeDeleteEnabled(OnDeleteAction.CASCADE.equals(onDeleteAnn.action()));
/*  535:     */       } else {
/*  536: 631 */         key.setCascadeDeleteEnabled(false);
/*  537:     */       }
/*  538: 634 */       SecondPass sp = new JoinedSubclassFkSecondPass(jsc, inheritanceJoinedColumns, key, mappings);
/*  539: 635 */       mappings.addSecondPass(sp);
/*  540: 636 */       mappings.addSecondPass(new CreateKeySecondPass(jsc));
/*  541:     */     }
/*  542: 639 */     else if (InheritanceType.SINGLE_TABLE.equals(inheritanceState.getType()))
/*  543:     */     {
/*  544: 640 */       if ((!inheritanceState.hasParents()) && (
/*  545: 641 */         (inheritanceState.hasSiblings()) || (!discriminatorColumn.isImplicit())))
/*  546:     */       {
/*  547: 643 */         bindDiscriminatorToPersistentClass((RootClass)persistentClass, discriminatorColumn, entityBinder.getSecondaryTables(), propertyHolder, mappings);
/*  548:     */         
/*  549:     */ 
/*  550:     */ 
/*  551:     */ 
/*  552:     */ 
/*  553:     */ 
/*  554: 650 */         entityBinder.bindDiscriminatorValue();
/*  555:     */       }
/*  556:     */     }
/*  557: 654 */     else if (!InheritanceType.TABLE_PER_CLASS.equals(inheritanceState.getType())) {}
/*  558: 657 */     if ((onDeleteAnn != null) && (!onDeleteAppropriate)) {
/*  559: 657 */       LOG.invalidOnDeleteAnnotation(propertyHolder.getEntityName());
/*  560:     */     }
/*  561: 660 */     HashMap<String, IdGenerator> classGenerators = buildLocalGenerators(clazzToProcess, mappings);
/*  562:     */     
/*  563:     */ 
/*  564: 663 */     InheritanceState.ElementsToProcess elementsToProcess = inheritanceState.getElementsToProcess();
/*  565: 664 */     inheritanceState.postProcess(persistentClass, entityBinder);
/*  566:     */     
/*  567: 666 */     boolean subclassAndSingleTableStrategy = (inheritanceState.getType() == InheritanceType.SINGLE_TABLE) && (inheritanceState.hasParents());
/*  568:     */     
/*  569: 668 */     Set<String> idPropertiesIfIdClass = new HashSet();
/*  570: 669 */     boolean isIdClass = mapAsIdClass(inheritanceStatePerClass, inheritanceState, persistentClass, entityBinder, propertyHolder, elementsToProcess, idPropertiesIfIdClass, mappings);
/*  571: 680 */     if (!isIdClass) {
/*  572: 681 */       entityBinder.setWrapIdsInEmbeddedComponents(elementsToProcess.getIdPropertyCount() > 1);
/*  573:     */     }
/*  574: 684 */     processIdPropertiesIfNotAlready(inheritanceStatePerClass, mappings, persistentClass, entityBinder, propertyHolder, classGenerators, elementsToProcess, subclassAndSingleTableStrategy, idPropertiesIfIdClass);
/*  575: 696 */     if (!inheritanceState.hasParents())
/*  576:     */     {
/*  577: 697 */       RootClass rootClass = (RootClass)persistentClass;
/*  578: 698 */       mappings.addSecondPass(new CreateKeySecondPass(rootClass));
/*  579:     */     }
/*  580:     */     else
/*  581:     */     {
/*  582: 701 */       superEntity.addSubclass((Subclass)persistentClass);
/*  583:     */     }
/*  584: 704 */     mappings.addClass(persistentClass);
/*  585:     */     
/*  586:     */ 
/*  587: 707 */     mappings.addSecondPass(new SecondaryTableSecondPass(entityBinder, propertyHolder, clazzToProcess));
/*  588:     */     
/*  589:     */ 
/*  590: 710 */     entityBinder.processComplementaryTableDefinitions((org.hibernate.annotations.Table)clazzToProcess.getAnnotation(org.hibernate.annotations.Table.class));
/*  591: 711 */     entityBinder.processComplementaryTableDefinitions((Tables)clazzToProcess.getAnnotation(Tables.class));
/*  592:     */   }
/*  593:     */   
/*  594:     */   private static Ejb3DiscriminatorColumn processDiscriminatorProperties(XClass clazzToProcess, Mappings mappings, InheritanceState inheritanceState, EntityBinder entityBinder)
/*  595:     */   {
/*  596: 717 */     Ejb3DiscriminatorColumn discriminatorColumn = null;
/*  597: 718 */     DiscriminatorColumn discAnn = (DiscriminatorColumn)clazzToProcess.getAnnotation(DiscriminatorColumn.class);
/*  598:     */     
/*  599:     */ 
/*  600: 721 */     DiscriminatorType discriminatorType = discAnn != null ? discAnn.discriminatorType() : DiscriminatorType.STRING;
/*  601:     */     
/*  602:     */ 
/*  603:     */ 
/*  604: 725 */     DiscriminatorFormula discFormulaAnn = (DiscriminatorFormula)clazzToProcess.getAnnotation(DiscriminatorFormula.class);
/*  605: 728 */     if (!inheritanceState.hasParents()) {
/*  606: 729 */       discriminatorColumn = Ejb3DiscriminatorColumn.buildDiscriminatorColumn(discriminatorType, discAnn, discFormulaAnn, mappings);
/*  607:     */     }
/*  608: 733 */     if ((discAnn != null) && (inheritanceState.hasParents())) {
/*  609: 734 */       LOG.invalidDiscriminatorAnnotation(clazzToProcess.getName());
/*  610:     */     }
/*  611: 737 */     String discrimValue = clazzToProcess.isAnnotationPresent(DiscriminatorValue.class) ? ((DiscriminatorValue)clazzToProcess.getAnnotation(DiscriminatorValue.class)).value() : null;
/*  612:     */     
/*  613:     */ 
/*  614: 740 */     entityBinder.setDiscriminatorValue(discrimValue);
/*  615:     */     
/*  616: 742 */     DiscriminatorOptions discriminatorOptions = (DiscriminatorOptions)clazzToProcess.getAnnotation(DiscriminatorOptions.class);
/*  617: 743 */     if (discriminatorOptions != null)
/*  618:     */     {
/*  619: 744 */       entityBinder.setForceDiscriminator(discriminatorOptions.force());
/*  620: 745 */       entityBinder.setInsertableDiscriminator(discriminatorOptions.insert());
/*  621:     */     }
/*  622: 748 */     return discriminatorColumn;
/*  623:     */   }
/*  624:     */   
/*  625:     */   private static void processIdPropertiesIfNotAlready(Map<XClass, InheritanceState> inheritanceStatePerClass, Mappings mappings, PersistentClass persistentClass, EntityBinder entityBinder, PropertyHolder propertyHolder, HashMap<String, IdGenerator> classGenerators, InheritanceState.ElementsToProcess elementsToProcess, boolean subclassAndSingleTableStrategy, Set<String> idPropertiesIfIdClass)
/*  626:     */   {
/*  627: 761 */     Set<String> missingIdProperties = new HashSet(idPropertiesIfIdClass);
/*  628: 762 */     for (PropertyData propertyAnnotatedElement : elementsToProcess.getElements())
/*  629:     */     {
/*  630: 763 */       String propertyName = propertyAnnotatedElement.getPropertyName();
/*  631: 764 */       if (!idPropertiesIfIdClass.contains(propertyName)) {
/*  632: 765 */         processElementAnnotations(propertyHolder, subclassAndSingleTableStrategy ? Nullability.FORCED_NULL : Nullability.NO_CONSTRAINT, propertyAnnotatedElement, classGenerators, entityBinder, false, false, false, mappings, inheritanceStatePerClass);
/*  633:     */       } else {
/*  634: 775 */         missingIdProperties.remove(propertyName);
/*  635:     */       }
/*  636:     */     }
/*  637: 779 */     if (missingIdProperties.size() != 0)
/*  638:     */     {
/*  639: 780 */       StringBuilder missings = new StringBuilder();
/*  640: 781 */       for (String property : missingIdProperties) {
/*  641: 782 */         missings.append(property).append(", ");
/*  642:     */       }
/*  643: 784 */       throw new AnnotationException("Unable to find properties (" + missings.substring(0, missings.length() - 2) + ") in entity annotated with @IdClass:" + persistentClass.getEntityName());
/*  644:     */     }
/*  645:     */   }
/*  646:     */   
/*  647:     */   private static boolean mapAsIdClass(Map<XClass, InheritanceState> inheritanceStatePerClass, InheritanceState inheritanceState, PersistentClass persistentClass, EntityBinder entityBinder, PropertyHolder propertyHolder, InheritanceState.ElementsToProcess elementsToProcess, Set<String> idPropertiesIfIdClass, Mappings mappings)
/*  648:     */   {
/*  649: 809 */     XClass classWithIdClass = inheritanceState.getClassWithIdClass(false);
/*  650: 810 */     if (classWithIdClass != null)
/*  651:     */     {
/*  652: 811 */       IdClass idClass = (IdClass)classWithIdClass.getAnnotation(IdClass.class);
/*  653: 812 */       XClass compositeClass = mappings.getReflectionManager().toXClass(idClass.value());
/*  654: 813 */       PropertyData inferredData = new PropertyPreloadedData(entityBinder.getPropertyAccessType(), "id", compositeClass);
/*  655:     */       
/*  656:     */ 
/*  657: 816 */       PropertyData baseInferredData = new PropertyPreloadedData(entityBinder.getPropertyAccessType(), "id", classWithIdClass);
/*  658:     */       
/*  659:     */ 
/*  660: 819 */       AccessType propertyAccessor = entityBinder.getPropertyAccessor(compositeClass);
/*  661:     */       
/*  662:     */ 
/*  663: 822 */       boolean isFakeIdClass = isIdClassPkOfTheAssociatedEntity(elementsToProcess, compositeClass, inferredData, baseInferredData, propertyAccessor, inheritanceStatePerClass, mappings);
/*  664: 832 */       if (isFakeIdClass) {
/*  665: 833 */         return false;
/*  666:     */       }
/*  667: 836 */       boolean isComponent = true;
/*  668: 837 */       String generatorType = "assigned";
/*  669: 838 */       String generator = "";
/*  670:     */       
/*  671: 840 */       boolean ignoreIdAnnotations = entityBinder.isIgnoreIdAnnotations();
/*  672: 841 */       entityBinder.setIgnoreIdAnnotations(true);
/*  673: 842 */       propertyHolder.setInIdClass(Boolean.valueOf(true));
/*  674: 843 */       bindIdClass(generatorType, generator, inferredData, baseInferredData, null, propertyHolder, isComponent, propertyAccessor, entityBinder, true, false, mappings, inheritanceStatePerClass);
/*  675:     */       
/*  676:     */ 
/*  677:     */ 
/*  678:     */ 
/*  679:     */ 
/*  680:     */ 
/*  681:     */ 
/*  682:     */ 
/*  683:     */ 
/*  684:     */ 
/*  685:     */ 
/*  686:     */ 
/*  687:     */ 
/*  688:     */ 
/*  689: 858 */       propertyHolder.setInIdClass(null);
/*  690: 859 */       inferredData = new PropertyPreloadedData(propertyAccessor, "_identifierMapper", compositeClass);
/*  691:     */       
/*  692:     */ 
/*  693: 862 */       Component mapper = fillComponent(propertyHolder, inferredData, baseInferredData, propertyAccessor, false, entityBinder, true, true, false, mappings, inheritanceStatePerClass);
/*  694:     */       
/*  695:     */ 
/*  696:     */ 
/*  697:     */ 
/*  698:     */ 
/*  699:     */ 
/*  700:     */ 
/*  701:     */ 
/*  702:     */ 
/*  703:     */ 
/*  704:     */ 
/*  705:     */ 
/*  706: 875 */       entityBinder.setIgnoreIdAnnotations(ignoreIdAnnotations);
/*  707: 876 */       persistentClass.setIdentifierMapper(mapper);
/*  708:     */       
/*  709:     */ 
/*  710: 879 */       org.hibernate.mapping.MappedSuperclass superclass = BinderHelper.getMappedSuperclassOrNull(inferredData.getDeclaringClass(), inheritanceStatePerClass, mappings);
/*  711: 885 */       if (superclass != null) {
/*  712: 886 */         superclass.setDeclaredIdentifierMapper(mapper);
/*  713:     */       } else {
/*  714: 890 */         persistentClass.setDeclaredIdentifierMapper(mapper);
/*  715:     */       }
/*  716: 893 */       Property property = new Property();
/*  717: 894 */       property.setName("_identifierMapper");
/*  718: 895 */       property.setNodeName("id");
/*  719: 896 */       property.setUpdateable(false);
/*  720: 897 */       property.setInsertable(false);
/*  721: 898 */       property.setValue(mapper);
/*  722: 899 */       property.setPropertyAccessorName("embedded");
/*  723: 900 */       persistentClass.addProperty(property);
/*  724: 901 */       entityBinder.setIgnoreIdAnnotations(true);
/*  725:     */       
/*  726: 903 */       Iterator properties = mapper.getPropertyIterator();
/*  727: 904 */       while (properties.hasNext()) {
/*  728: 905 */         idPropertiesIfIdClass.add(((Property)properties.next()).getName());
/*  729:     */       }
/*  730: 907 */       return true;
/*  731:     */     }
/*  732: 910 */     return false;
/*  733:     */   }
/*  734:     */   
/*  735:     */   private static boolean isIdClassPkOfTheAssociatedEntity(InheritanceState.ElementsToProcess elementsToProcess, XClass compositeClass, PropertyData inferredData, PropertyData baseInferredData, AccessType propertyAccessor, Map<XClass, InheritanceState> inheritanceStatePerClass, Mappings mappings)
/*  736:     */   {
/*  737: 922 */     if (elementsToProcess.getIdPropertyCount() == 1)
/*  738:     */     {
/*  739: 923 */       PropertyData idPropertyOnBaseClass = getUniqueIdPropertyFromBaseClass(inferredData, baseInferredData, propertyAccessor, mappings);
/*  740:     */       
/*  741:     */ 
/*  742: 926 */       InheritanceState state = (InheritanceState)inheritanceStatePerClass.get(idPropertyOnBaseClass.getClassOrElement());
/*  743: 927 */       if (state == null) {
/*  744: 928 */         return false;
/*  745:     */       }
/*  746: 930 */       XClass associatedClassWithIdClass = state.getClassWithIdClass(true);
/*  747: 931 */       if (associatedClassWithIdClass == null)
/*  748:     */       {
/*  749: 934 */         XProperty property = idPropertyOnBaseClass.getProperty();
/*  750: 935 */         return (property.isAnnotationPresent(javax.persistence.ManyToOne.class)) || (property.isAnnotationPresent(OneToOne.class));
/*  751:     */       }
/*  752: 940 */       XClass idClass = mappings.getReflectionManager().toXClass(((IdClass)associatedClassWithIdClass.getAnnotation(IdClass.class)).value());
/*  753:     */       
/*  754:     */ 
/*  755: 943 */       return idClass.equals(compositeClass);
/*  756:     */     }
/*  757: 947 */     return false;
/*  758:     */   }
/*  759:     */   
/*  760:     */   private static Cache determineCacheSettings(XClass clazzToProcess, Mappings mappings)
/*  761:     */   {
/*  762: 952 */     Cache cacheAnn = (Cache)clazzToProcess.getAnnotation(Cache.class);
/*  763: 953 */     if (cacheAnn != null) {
/*  764: 954 */       return cacheAnn;
/*  765:     */     }
/*  766: 957 */     Cacheable cacheableAnn = (Cacheable)clazzToProcess.getAnnotation(Cacheable.class);
/*  767: 958 */     SharedCacheMode mode = determineSharedCacheMode(mappings);
/*  768: 959 */     switch (1.$SwitchMap$javax$persistence$SharedCacheMode[mode.ordinal()])
/*  769:     */     {
/*  770:     */     case 1: 
/*  771: 961 */       cacheAnn = buildCacheMock(clazzToProcess.getName(), mappings);
/*  772: 962 */       break;
/*  773:     */     case 2: 
/*  774: 965 */       if ((cacheableAnn != null) && (cacheableAnn.value())) {
/*  775: 966 */         cacheAnn = buildCacheMock(clazzToProcess.getName(), mappings);
/*  776:     */       }
/*  777:     */       break;
/*  778:     */     case 3: 
/*  779: 971 */       if ((cacheableAnn == null) || (cacheableAnn.value())) {
/*  780: 972 */         cacheAnn = buildCacheMock(clazzToProcess.getName(), mappings);
/*  781:     */       }
/*  782:     */       break;
/*  783:     */     }
/*  784: 981 */     return cacheAnn;
/*  785:     */   }
/*  786:     */   
/*  787:     */   private static SharedCacheMode determineSharedCacheMode(Mappings mappings)
/*  788:     */   {
/*  789: 986 */     Object value = mappings.getConfigurationProperties().get("javax.persistence.sharedCache.mode");
/*  790:     */     SharedCacheMode mode;
/*  791:     */     SharedCacheMode mode;
/*  792: 987 */     if (value == null)
/*  793:     */     {
/*  794: 988 */       LOG.debug("No value specified for 'javax.persistence.sharedCache.mode'; using UNSPECIFIED");
/*  795: 989 */       mode = SharedCacheMode.UNSPECIFIED;
/*  796:     */     }
/*  797:     */     else
/*  798:     */     {
/*  799:     */       SharedCacheMode mode;
/*  800: 992 */       if (SharedCacheMode.class.isInstance(value)) {
/*  801: 993 */         mode = (SharedCacheMode)value;
/*  802:     */       } else {
/*  803:     */         try
/*  804:     */         {
/*  805: 997 */           mode = SharedCacheMode.valueOf(value.toString());
/*  806:     */         }
/*  807:     */         catch (Exception e)
/*  808:     */         {
/*  809:1000 */           LOG.debugf("Unable to resolve given mode name [%s]; using UNSPECIFIED : %s", value, e);
/*  810:1001 */           mode = SharedCacheMode.UNSPECIFIED;
/*  811:     */         }
/*  812:     */       }
/*  813:     */     }
/*  814:1005 */     return mode;
/*  815:     */   }
/*  816:     */   
/*  817:     */   private static Cache buildCacheMock(String region, Mappings mappings)
/*  818:     */   {
/*  819:1009 */     return new LocalCacheAnnotationImpl(region, determineCacheConcurrencyStrategy(mappings), null);
/*  820:     */   }
/*  821:     */   
/*  822:     */   static void prepareDefaultCacheConcurrencyStrategy(Properties properties)
/*  823:     */   {
/*  824:1015 */     if (DEFAULT_CACHE_CONCURRENCY_STRATEGY != null)
/*  825:     */     {
/*  826:1016 */       LOG.trace("Default cache concurrency strategy already defined");
/*  827:1017 */       return;
/*  828:     */     }
/*  829:1020 */     if (!properties.containsKey("hibernate.cache.default_cache_concurrency_strategy"))
/*  830:     */     {
/*  831:1021 */       LOG.trace("Given properties did not contain any default cache concurrency strategy setting");
/*  832:1022 */       return;
/*  833:     */     }
/*  834:1025 */     String strategyName = properties.getProperty("hibernate.cache.default_cache_concurrency_strategy");
/*  835:1026 */     LOG.tracev("Discovered default cache concurrency strategy via config [{0}]", strategyName);
/*  836:1027 */     CacheConcurrencyStrategy strategy = CacheConcurrencyStrategy.parse(strategyName);
/*  837:1028 */     if (strategy == null)
/*  838:     */     {
/*  839:1029 */       LOG.trace("Discovered default cache concurrency strategy specified nothing");
/*  840:1030 */       return;
/*  841:     */     }
/*  842:1033 */     LOG.debugf("Setting default cache concurrency strategy via config [%s]", strategy.name());
/*  843:1034 */     DEFAULT_CACHE_CONCURRENCY_STRATEGY = strategy;
/*  844:     */   }
/*  845:     */   
/*  846:     */   private static CacheConcurrencyStrategy determineCacheConcurrencyStrategy(Mappings mappings)
/*  847:     */   {
/*  848:1038 */     if (DEFAULT_CACHE_CONCURRENCY_STRATEGY == null)
/*  849:     */     {
/*  850:1039 */       RegionFactory cacheRegionFactory = SettingsFactory.createRegionFactory(mappings.getConfigurationProperties(), true);
/*  851:     */       
/*  852:     */ 
/*  853:1042 */       DEFAULT_CACHE_CONCURRENCY_STRATEGY = CacheConcurrencyStrategy.fromAccessType(cacheRegionFactory.getDefaultAccessType());
/*  854:     */     }
/*  855:1044 */     return DEFAULT_CACHE_CONCURRENCY_STRATEGY;
/*  856:     */   }
/*  857:     */   
/*  858:     */   private static class LocalCacheAnnotationImpl
/*  859:     */     implements Cache
/*  860:     */   {
/*  861:     */     private final String region;
/*  862:     */     private final CacheConcurrencyStrategy usage;
/*  863:     */     
/*  864:     */     private LocalCacheAnnotationImpl(String region, CacheConcurrencyStrategy usage)
/*  865:     */     {
/*  866:1053 */       this.region = region;
/*  867:1054 */       this.usage = usage;
/*  868:     */     }
/*  869:     */     
/*  870:     */     public CacheConcurrencyStrategy usage()
/*  871:     */     {
/*  872:1058 */       return this.usage;
/*  873:     */     }
/*  874:     */     
/*  875:     */     public String region()
/*  876:     */     {
/*  877:1062 */       return this.region;
/*  878:     */     }
/*  879:     */     
/*  880:     */     public String include()
/*  881:     */     {
/*  882:1066 */       return "all";
/*  883:     */     }
/*  884:     */     
/*  885:     */     public Class<? extends Annotation> annotationType()
/*  886:     */     {
/*  887:1070 */       return Cache.class;
/*  888:     */     }
/*  889:     */   }
/*  890:     */   
/*  891:     */   private static PersistentClass makePersistentClass(InheritanceState inheritanceState, PersistentClass superEntity)
/*  892:     */   {
/*  893:     */     PersistentClass persistentClass;
/*  894:1078 */     if (!inheritanceState.hasParents())
/*  895:     */     {
/*  896:1079 */       persistentClass = new RootClass();
/*  897:     */     }
/*  898:     */     else
/*  899:     */     {
/*  900:     */       PersistentClass persistentClass;
/*  901:1081 */       if (InheritanceType.SINGLE_TABLE.equals(inheritanceState.getType()))
/*  902:     */       {
/*  903:1082 */         persistentClass = new SingleTableSubclass(superEntity);
/*  904:     */       }
/*  905:     */       else
/*  906:     */       {
/*  907:     */         PersistentClass persistentClass;
/*  908:1084 */         if (InheritanceType.JOINED.equals(inheritanceState.getType()))
/*  909:     */         {
/*  910:1085 */           persistentClass = new JoinedSubclass(superEntity);
/*  911:     */         }
/*  912:     */         else
/*  913:     */         {
/*  914:     */           PersistentClass persistentClass;
/*  915:1087 */           if (InheritanceType.TABLE_PER_CLASS.equals(inheritanceState.getType())) {
/*  916:1088 */             persistentClass = new UnionSubclass(superEntity);
/*  917:     */           } else {
/*  918:1091 */             throw new AssertionFailure("Unknown inheritance type: " + inheritanceState.getType());
/*  919:     */           }
/*  920:     */         }
/*  921:     */       }
/*  922:     */     }
/*  923:     */     PersistentClass persistentClass;
/*  924:1093 */     return persistentClass;
/*  925:     */   }
/*  926:     */   
/*  927:     */   private static Ejb3JoinColumn[] makeInheritanceJoinColumns(XClass clazzToProcess, Mappings mappings, InheritanceState inheritanceState, PersistentClass superEntity)
/*  928:     */   {
/*  929:1101 */     Ejb3JoinColumn[] inheritanceJoinedColumns = null;
/*  930:1102 */     boolean hasJoinedColumns = (inheritanceState.hasParents()) && (InheritanceType.JOINED.equals(inheritanceState.getType()));
/*  931:1104 */     if (hasJoinedColumns)
/*  932:     */     {
/*  933:1106 */       PrimaryKeyJoinColumns jcsAnn = (PrimaryKeyJoinColumns)clazzToProcess.getAnnotation(PrimaryKeyJoinColumns.class);
/*  934:1107 */       boolean explicitInheritanceJoinedColumns = (jcsAnn != null) && (jcsAnn.value().length != 0);
/*  935:1108 */       if (explicitInheritanceJoinedColumns)
/*  936:     */       {
/*  937:1109 */         int nbrOfInhJoinedColumns = jcsAnn.value().length;
/*  938:     */         
/*  939:1111 */         inheritanceJoinedColumns = new Ejb3JoinColumn[nbrOfInhJoinedColumns];
/*  940:1112 */         for (int colIndex = 0; colIndex < nbrOfInhJoinedColumns; colIndex++)
/*  941:     */         {
/*  942:1113 */           PrimaryKeyJoinColumn jcAnn = jcsAnn.value()[colIndex];
/*  943:1114 */           inheritanceJoinedColumns[colIndex] = Ejb3JoinColumn.buildJoinColumn(jcAnn, null, superEntity.getIdentifier(), (Map)null, (PropertyHolder)null, mappings);
/*  944:     */         }
/*  945:     */       }
/*  946:     */       else
/*  947:     */       {
/*  948:1121 */         PrimaryKeyJoinColumn jcAnn = (PrimaryKeyJoinColumn)clazzToProcess.getAnnotation(PrimaryKeyJoinColumn.class);
/*  949:1122 */         inheritanceJoinedColumns = new Ejb3JoinColumn[1];
/*  950:1123 */         inheritanceJoinedColumns[0] = Ejb3JoinColumn.buildJoinColumn(jcAnn, null, superEntity.getIdentifier(), (Map)null, (PropertyHolder)null, mappings);
/*  951:     */       }
/*  952:1128 */       LOG.trace("Subclass joined column(s) created");
/*  953:     */     }
/*  954:1131 */     else if ((clazzToProcess.isAnnotationPresent(PrimaryKeyJoinColumns.class)) || (clazzToProcess.isAnnotationPresent(PrimaryKeyJoinColumn.class)))
/*  955:     */     {
/*  956:1133 */       LOG.invalidPrimaryKeyJoinColumnAnnotation();
/*  957:     */     }
/*  958:1136 */     return inheritanceJoinedColumns;
/*  959:     */   }
/*  960:     */   
/*  961:     */   private static PersistentClass getSuperEntity(XClass clazzToProcess, Map<XClass, InheritanceState> inheritanceStatePerClass, Mappings mappings, InheritanceState inheritanceState)
/*  962:     */   {
/*  963:1140 */     InheritanceState superEntityState = InheritanceState.getInheritanceStateOfSuperEntity(clazzToProcess, inheritanceStatePerClass);
/*  964:     */     
/*  965:     */ 
/*  966:1143 */     PersistentClass superEntity = superEntityState != null ? mappings.getClass(superEntityState.getClazz().getName()) : null;
/*  967:1148 */     if (superEntity == null) {
/*  968:1150 */       if (inheritanceState.hasParents()) {
/*  969:1151 */         throw new AssertionFailure("Subclass has to be binded after it's mother class: " + superEntityState.getClazz().getName());
/*  970:     */       }
/*  971:     */     }
/*  972:1157 */     return superEntity;
/*  973:     */   }
/*  974:     */   
/*  975:     */   private static boolean isEntityClassType(XClass clazzToProcess, AnnotatedClassType classType)
/*  976:     */   {
/*  977:1161 */     if ((AnnotatedClassType.EMBEDDABLE_SUPERCLASS.equals(classType)) || (AnnotatedClassType.NONE.equals(classType)) || (AnnotatedClassType.EMBEDDABLE.equals(classType)))
/*  978:     */     {
/*  979:1165 */       if ((AnnotatedClassType.NONE.equals(classType)) && (clazzToProcess.isAnnotationPresent(org.hibernate.annotations.Entity.class))) {
/*  980:1167 */         LOG.missingEntityAnnotation(clazzToProcess.getName());
/*  981:     */       }
/*  982:1169 */       return false;
/*  983:     */     }
/*  984:1172 */     if (!classType.equals(AnnotatedClassType.ENTITY)) {
/*  985:1173 */       throw new AnnotationException("Annotated class should have a @javax.persistence.Entity, @javax.persistence.Embeddable or @javax.persistence.EmbeddedSuperclass annotation: " + clazzToProcess.getName());
/*  986:     */     }
/*  987:1179 */     return true;
/*  988:     */   }
/*  989:     */   
/*  990:     */   private static void bindFilters(XClass annotatedClass, EntityBinder entityBinder, Mappings mappings)
/*  991:     */   {
/*  992:1190 */     bindFilters(annotatedClass, entityBinder);
/*  993:     */     
/*  994:1192 */     XClass classToProcess = annotatedClass.getSuperclass();
/*  995:1193 */     while (classToProcess != null)
/*  996:     */     {
/*  997:1194 */       AnnotatedClassType classType = mappings.getClassType(classToProcess);
/*  998:1195 */       if (AnnotatedClassType.EMBEDDABLE_SUPERCLASS.equals(classType)) {
/*  999:1196 */         bindFilters(classToProcess, entityBinder);
/* 1000:     */       }
/* 1001:1198 */       classToProcess = classToProcess.getSuperclass();
/* 1002:     */     }
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   private static void bindFilters(XAnnotatedElement annotatedElement, EntityBinder entityBinder)
/* 1006:     */   {
/* 1007:1205 */     Filters filtersAnn = (Filters)annotatedElement.getAnnotation(Filters.class);
/* 1008:1206 */     if (filtersAnn != null) {
/* 1009:1207 */       for (Filter filter : filtersAnn.value()) {
/* 1010:1208 */         entityBinder.addFilter(filter.name(), filter.condition());
/* 1011:     */       }
/* 1012:     */     }
/* 1013:1212 */     Filter filterAnn = (Filter)annotatedElement.getAnnotation(Filter.class);
/* 1014:1213 */     if (filterAnn != null) {
/* 1015:1214 */       entityBinder.addFilter(filterAnn.name(), filterAnn.condition());
/* 1016:     */     }
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   private static void bindFilterDefs(XAnnotatedElement annotatedElement, Mappings mappings)
/* 1020:     */   {
/* 1021:1219 */     FilterDef defAnn = (FilterDef)annotatedElement.getAnnotation(FilterDef.class);
/* 1022:1220 */     FilterDefs defsAnn = (FilterDefs)annotatedElement.getAnnotation(FilterDefs.class);
/* 1023:1221 */     if (defAnn != null) {
/* 1024:1222 */       bindFilterDef(defAnn, mappings);
/* 1025:     */     }
/* 1026:1224 */     if (defsAnn != null) {
/* 1027:1225 */       for (FilterDef def : defsAnn.value()) {
/* 1028:1226 */         bindFilterDef(def, mappings);
/* 1029:     */       }
/* 1030:     */     }
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   private static void bindFilterDef(FilterDef defAnn, Mappings mappings)
/* 1034:     */   {
/* 1035:1232 */     Map<String, Type> params = new HashMap();
/* 1036:1233 */     for (ParamDef param : defAnn.parameters()) {
/* 1037:1234 */       params.put(param.name(), mappings.getTypeResolver().heuristicType(param.type()));
/* 1038:     */     }
/* 1039:1236 */     FilterDefinition def = new FilterDefinition(defAnn.name(), defAnn.defaultCondition(), params);
/* 1040:1237 */     LOG.debugf("Binding filter definition: %s", def.getFilterName());
/* 1041:1238 */     mappings.addFilterDefinition(def);
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   private static void bindTypeDefs(XAnnotatedElement annotatedElement, Mappings mappings)
/* 1045:     */   {
/* 1046:1242 */     TypeDef defAnn = (TypeDef)annotatedElement.getAnnotation(TypeDef.class);
/* 1047:1243 */     TypeDefs defsAnn = (TypeDefs)annotatedElement.getAnnotation(TypeDefs.class);
/* 1048:1244 */     if (defAnn != null) {
/* 1049:1245 */       bindTypeDef(defAnn, mappings);
/* 1050:     */     }
/* 1051:1247 */     if (defsAnn != null) {
/* 1052:1248 */       for (TypeDef def : defsAnn.value()) {
/* 1053:1249 */         bindTypeDef(def, mappings);
/* 1054:     */       }
/* 1055:     */     }
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   private static void bindFetchProfiles(XAnnotatedElement annotatedElement, Mappings mappings)
/* 1059:     */   {
/* 1060:1255 */     FetchProfile fetchProfileAnnotation = (FetchProfile)annotatedElement.getAnnotation(FetchProfile.class);
/* 1061:1256 */     FetchProfiles fetchProfileAnnotations = (FetchProfiles)annotatedElement.getAnnotation(FetchProfiles.class);
/* 1062:1257 */     if (fetchProfileAnnotation != null) {
/* 1063:1258 */       bindFetchProfile(fetchProfileAnnotation, mappings);
/* 1064:     */     }
/* 1065:1260 */     if (fetchProfileAnnotations != null) {
/* 1066:1261 */       for (FetchProfile profile : fetchProfileAnnotations.value()) {
/* 1067:1262 */         bindFetchProfile(profile, mappings);
/* 1068:     */       }
/* 1069:     */     }
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   private static void bindFetchProfile(FetchProfile fetchProfileAnnotation, Mappings mappings)
/* 1073:     */   {
/* 1074:1268 */     for (FetchProfile.FetchOverride fetch : fetchProfileAnnotation.fetchOverrides())
/* 1075:     */     {
/* 1076:1269 */       org.hibernate.annotations.FetchMode mode = fetch.mode();
/* 1077:1270 */       if (!mode.equals(org.hibernate.annotations.FetchMode.JOIN)) {
/* 1078:1271 */         throw new MappingException("Only FetchMode.JOIN is currently supported");
/* 1079:     */       }
/* 1080:1274 */       SecondPass sp = new VerifyFetchProfileReferenceSecondPass(fetchProfileAnnotation.name(), fetch, mappings);
/* 1081:1275 */       mappings.addSecondPass(sp);
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   private static void bindTypeDef(TypeDef defAnn, Mappings mappings)
/* 1086:     */   {
/* 1087:1280 */     Properties params = new Properties();
/* 1088:1281 */     for (Parameter param : defAnn.parameters()) {
/* 1089:1282 */       params.setProperty(param.name(), param.value());
/* 1090:     */     }
/* 1091:1285 */     if ((BinderHelper.isEmptyAnnotationValue(defAnn.name())) && (defAnn.defaultForType().equals(Void.TYPE))) {
/* 1092:1286 */       throw new AnnotationException("Either name or defaultForType (or both) attribute should be set in TypeDef having typeClass " + defAnn.typeClass().getName());
/* 1093:     */     }
/* 1094:1292 */     String typeBindMessageF = "Binding type definition: %s";
/* 1095:1293 */     if (!BinderHelper.isEmptyAnnotationValue(defAnn.name()))
/* 1096:     */     {
/* 1097:1294 */       if (LOG.isDebugEnabled()) {
/* 1098:1295 */         LOG.debugf("Binding type definition: %s", defAnn.name());
/* 1099:     */       }
/* 1100:1297 */       mappings.addTypeDef(defAnn.name(), defAnn.typeClass().getName(), params);
/* 1101:     */     }
/* 1102:1299 */     if (!defAnn.defaultForType().equals(Void.TYPE))
/* 1103:     */     {
/* 1104:1300 */       if (LOG.isDebugEnabled()) {
/* 1105:1301 */         LOG.debugf("Binding type definition: %s", defAnn.defaultForType().getName());
/* 1106:     */       }
/* 1107:1303 */       mappings.addTypeDef(defAnn.defaultForType().getName(), defAnn.typeClass().getName(), params);
/* 1108:     */     }
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   private static void bindDiscriminatorToPersistentClass(RootClass rootClass, Ejb3DiscriminatorColumn discriminatorColumn, Map<String, Join> secondaryTables, PropertyHolder propertyHolder, Mappings mappings)
/* 1112:     */   {
/* 1113:1315 */     if (rootClass.getDiscriminator() == null)
/* 1114:     */     {
/* 1115:1316 */       if (discriminatorColumn == null) {
/* 1116:1317 */         throw new AssertionFailure("discriminator column should have been built");
/* 1117:     */       }
/* 1118:1319 */       discriminatorColumn.setJoins(secondaryTables);
/* 1119:1320 */       discriminatorColumn.setPropertyHolder(propertyHolder);
/* 1120:1321 */       SimpleValue discrim = new SimpleValue(mappings, rootClass.getTable());
/* 1121:1322 */       rootClass.setDiscriminator(discrim);
/* 1122:1323 */       discriminatorColumn.linkWithValue(discrim);
/* 1123:1324 */       discrim.setTypeName(discriminatorColumn.getDiscriminatorTypeName());
/* 1124:1325 */       rootClass.setPolymorphic(true);
/* 1125:1326 */       if (LOG.isTraceEnabled()) {
/* 1126:1327 */         LOG.tracev("Setting discriminator for entity {0}", rootClass.getEntityName());
/* 1127:     */       }
/* 1128:     */     }
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   static int addElementsOfClass(List<PropertyData> elements, AccessType defaultAccessType, PropertyContainer propertyContainer, Mappings mappings)
/* 1132:     */   {
/* 1133:1347 */     int idPropertyCounter = 0;
/* 1134:1348 */     AccessType accessType = defaultAccessType;
/* 1135:1350 */     if (propertyContainer.hasExplicitAccessStrategy()) {
/* 1136:1351 */       accessType = propertyContainer.getExplicitAccessStrategy();
/* 1137:     */     }
/* 1138:1354 */     Collection<XProperty> properties = propertyContainer.getProperties(accessType);
/* 1139:1355 */     for (XProperty p : properties)
/* 1140:     */     {
/* 1141:1356 */       int currentIdPropertyCounter = addProperty(propertyContainer, p, elements, accessType.getType(), mappings);
/* 1142:     */       
/* 1143:     */ 
/* 1144:1359 */       idPropertyCounter += currentIdPropertyCounter;
/* 1145:     */     }
/* 1146:1361 */     return idPropertyCounter;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   private static int addProperty(PropertyContainer propertyContainer, XProperty property, List<PropertyData> annElts, String propertyAccessor, Mappings mappings)
/* 1150:     */   {
/* 1151:1370 */     XClass declaringClass = propertyContainer.getDeclaringClass();
/* 1152:1371 */     XClass entity = propertyContainer.getEntityAtStake();
/* 1153:1372 */     int idPropertyCounter = 0;
/* 1154:1373 */     PropertyData propertyAnnotatedElement = new PropertyInferredData(declaringClass, property, propertyAccessor, mappings.getReflectionManager());
/* 1155:     */     
/* 1156:     */ 
/* 1157:     */ 
/* 1158:     */ 
/* 1159:     */ 
/* 1160:     */ 
/* 1161:     */ 
/* 1162:     */ 
/* 1163:1382 */     XAnnotatedElement element = propertyAnnotatedElement.getProperty();
/* 1164:1383 */     if ((element.isAnnotationPresent(Id.class)) || (element.isAnnotationPresent(EmbeddedId.class)))
/* 1165:     */     {
/* 1166:1384 */       annElts.add(0, propertyAnnotatedElement);
/* 1167:     */       String columnName;
/* 1168:1391 */       if ((mappings.isSpecjProprietarySyntaxEnabled()) && 
/* 1169:1392 */         (element.isAnnotationPresent(Id.class)) && (element.isAnnotationPresent(javax.persistence.Column.class)))
/* 1170:     */       {
/* 1171:1393 */         columnName = ((javax.persistence.Column)element.getAnnotation(javax.persistence.Column.class)).name();
/* 1172:1394 */         for (XProperty prop : declaringClass.getDeclaredProperties(AccessType.FIELD.getType())) {
/* 1173:1395 */           if ((prop.isAnnotationPresent(JoinColumn.class)) && (((JoinColumn)prop.getAnnotation(JoinColumn.class)).name().equals(columnName)) && (!prop.isAnnotationPresent(MapsId.class)))
/* 1174:     */           {
/* 1175:1399 */             PropertyData specJPropertyData = new PropertyInferredData(declaringClass, prop, propertyAccessor, mappings.getReflectionManager());
/* 1176:     */             
/* 1177:     */ 
/* 1178:     */ 
/* 1179:     */ 
/* 1180:     */ 
/* 1181:1405 */             mappings.addPropertyAnnotatedWithMapsIdSpecj(entity, specJPropertyData, element.toString());
/* 1182:     */           }
/* 1183:     */         }
/* 1184:     */       }
/* 1185:1411 */       if ((element.isAnnotationPresent(javax.persistence.ManyToOne.class)) || (element.isAnnotationPresent(OneToOne.class))) {
/* 1186:1412 */         mappings.addToOneAndIdProperty(entity, propertyAnnotatedElement);
/* 1187:     */       }
/* 1188:1414 */       idPropertyCounter++;
/* 1189:     */     }
/* 1190:     */     else
/* 1191:     */     {
/* 1192:1417 */       annElts.add(propertyAnnotatedElement);
/* 1193:     */     }
/* 1194:1419 */     if (element.isAnnotationPresent(MapsId.class)) {
/* 1195:1420 */       mappings.addPropertyAnnotatedWithMapsId(entity, propertyAnnotatedElement);
/* 1196:     */     }
/* 1197:1423 */     return idPropertyCounter;
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   private static void processElementAnnotations(PropertyHolder propertyHolder, Nullability nullability, PropertyData inferredData, HashMap<String, IdGenerator> classGenerators, EntityBinder entityBinder, boolean isIdentifierMapper, boolean isComponentEmbedded, boolean inSecondPass, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/* 1201:     */     throws MappingException
/* 1202:     */   {
/* 1203:1447 */     if (LOG.isTraceEnabled()) {
/* 1204:1448 */       LOG.tracev("Processing annotations of {0}.{1}", propertyHolder.getEntityName(), inferredData.getPropertyName());
/* 1205:     */     }
/* 1206:1451 */     XProperty property = inferredData.getProperty();
/* 1207:1452 */     if (property.isAnnotationPresent(Parent.class))
/* 1208:     */     {
/* 1209:1453 */       if (propertyHolder.isComponent()) {
/* 1210:1454 */         propertyHolder.setParentProperty(property.getName());
/* 1211:     */       } else {
/* 1212:1457 */         throw new AnnotationException("@Parent cannot be applied outside an embeddable object: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 1213:     */       }
/* 1214:1462 */       return;
/* 1215:     */     }
/* 1216:1465 */     ColumnsBuilder columnsBuilder = new ColumnsBuilder(propertyHolder, nullability, property, inferredData, entityBinder, mappings).extractMetadata();
/* 1217:     */     
/* 1218:     */ 
/* 1219:1468 */     Ejb3Column[] columns = columnsBuilder.getColumns();
/* 1220:1469 */     Ejb3JoinColumn[] joinColumns = columnsBuilder.getJoinColumns();
/* 1221:     */     
/* 1222:1471 */     XClass returnedClass = inferredData.getClassOrElement();
/* 1223:     */     
/* 1224:     */ 
/* 1225:1474 */     PropertyBinder propertyBinder = new PropertyBinder();
/* 1226:1475 */     propertyBinder.setName(inferredData.getPropertyName());
/* 1227:1476 */     propertyBinder.setReturnedClassName(inferredData.getTypeName());
/* 1228:1477 */     propertyBinder.setAccessType(inferredData.getDefaultAccess());
/* 1229:1478 */     propertyBinder.setHolder(propertyHolder);
/* 1230:1479 */     propertyBinder.setProperty(property);
/* 1231:1480 */     propertyBinder.setReturnedClass(inferredData.getPropertyClass());
/* 1232:1481 */     propertyBinder.setMappings(mappings);
/* 1233:1482 */     if (isIdentifierMapper)
/* 1234:     */     {
/* 1235:1483 */       propertyBinder.setInsertable(false);
/* 1236:1484 */       propertyBinder.setUpdatable(false);
/* 1237:     */     }
/* 1238:1486 */     propertyBinder.setDeclaringClass(inferredData.getDeclaringClass());
/* 1239:1487 */     propertyBinder.setEntityBinder(entityBinder);
/* 1240:1488 */     propertyBinder.setInheritanceStatePerClass(inheritanceStatePerClass);
/* 1241:     */     
/* 1242:1490 */     boolean isId = (!entityBinder.isIgnoreIdAnnotations()) && ((property.isAnnotationPresent(Id.class)) || (property.isAnnotationPresent(EmbeddedId.class)));
/* 1243:     */     
/* 1244:     */ 
/* 1245:1493 */     propertyBinder.setId(isId);
/* 1246:1495 */     if (property.isAnnotationPresent(Version.class))
/* 1247:     */     {
/* 1248:1496 */       if (isIdentifierMapper) {
/* 1249:1497 */         throw new AnnotationException("@IdClass class should not have @Version property");
/* 1250:     */       }
/* 1251:1501 */       if (!(propertyHolder.getPersistentClass() instanceof RootClass)) {
/* 1252:1502 */         throw new AnnotationException("Unable to define/override @Version on a subclass: " + propertyHolder.getEntityName());
/* 1253:     */       }
/* 1254:1507 */       if (!propertyHolder.isEntity()) {
/* 1255:1508 */         throw new AnnotationException("Unable to define @Version on an embedded class: " + propertyHolder.getEntityName());
/* 1256:     */       }
/* 1257:1513 */       if (LOG.isTraceEnabled()) {
/* 1258:1514 */         LOG.tracev("{0} is a version property", inferredData.getPropertyName());
/* 1259:     */       }
/* 1260:1516 */       RootClass rootClass = (RootClass)propertyHolder.getPersistentClass();
/* 1261:1517 */       propertyBinder.setColumns(columns);
/* 1262:1518 */       Property prop = propertyBinder.makePropertyValueAndBind();
/* 1263:1519 */       setVersionInformation(property, propertyBinder);
/* 1264:1520 */       rootClass.setVersion(prop);
/* 1265:     */       
/* 1266:     */ 
/* 1267:1523 */       org.hibernate.mapping.MappedSuperclass superclass = BinderHelper.getMappedSuperclassOrNull(inferredData.getDeclaringClass(), inheritanceStatePerClass, mappings);
/* 1268:1528 */       if (superclass != null) {
/* 1269:1529 */         superclass.setDeclaredVersion(prop);
/* 1270:     */       } else {
/* 1271:1533 */         rootClass.setDeclaredVersion(prop);
/* 1272:     */       }
/* 1273:1536 */       SimpleValue simpleValue = (SimpleValue)prop.getValue();
/* 1274:1537 */       simpleValue.setNullValue("undefined");
/* 1275:1538 */       rootClass.setOptimisticLockMode(0);
/* 1276:1539 */       if (LOG.isTraceEnabled()) {
/* 1277:1540 */         LOG.tracev("Version name: {0}, unsavedValue: {1}", rootClass.getVersion().getName(), ((SimpleValue)rootClass.getVersion().getValue()).getNullValue());
/* 1278:     */       }
/* 1279:     */     }
/* 1280:     */     else
/* 1281:     */     {
/* 1282:1545 */       boolean forcePersist = (property.isAnnotationPresent(MapsId.class)) || (property.isAnnotationPresent(Id.class));
/* 1283:1547 */       if (property.isAnnotationPresent(javax.persistence.ManyToOne.class))
/* 1284:     */       {
/* 1285:1548 */         javax.persistence.ManyToOne ann = (javax.persistence.ManyToOne)property.getAnnotation(javax.persistence.ManyToOne.class);
/* 1286:1551 */         if ((property.isAnnotationPresent(javax.persistence.Column.class)) || (property.isAnnotationPresent(Columns.class))) {
/* 1287:1553 */           throw new AnnotationException("@Column(s) not allowed on a @ManyToOne property: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 1288:     */         }
/* 1289:1559 */         Cascade hibernateCascade = (Cascade)property.getAnnotation(Cascade.class);
/* 1290:1560 */         NotFound notFound = (NotFound)property.getAnnotation(NotFound.class);
/* 1291:1561 */         boolean ignoreNotFound = (notFound != null) && (notFound.action().equals(NotFoundAction.IGNORE));
/* 1292:1562 */         OnDelete onDeleteAnn = (OnDelete)property.getAnnotation(OnDelete.class);
/* 1293:1563 */         boolean onDeleteCascade = (onDeleteAnn != null) && (OnDeleteAction.CASCADE.equals(onDeleteAnn.action()));
/* 1294:1564 */         JoinTable assocTable = propertyHolder.getJoinTable(property);
/* 1295:1565 */         if (assocTable != null)
/* 1296:     */         {
/* 1297:1566 */           Join join = propertyHolder.addJoin(assocTable, false);
/* 1298:1567 */           for (Ejb3JoinColumn joinColumn : joinColumns) {
/* 1299:1568 */             joinColumn.setSecondaryTableName(join.getTable().getName());
/* 1300:     */           }
/* 1301:     */         }
/* 1302:1571 */         boolean mandatory = (!ann.optional()) || (forcePersist);
/* 1303:1572 */         bindManyToOne(getCascadeStrategy(ann.cascade(), hibernateCascade, false, forcePersist), joinColumns, !mandatory, ignoreNotFound, onDeleteCascade, ToOneBinder.getTargetEntity(inferredData, mappings), propertyHolder, inferredData, false, isIdentifierMapper, inSecondPass, propertyBinder, mappings);
/* 1304:     */       }
/* 1305:1583 */       else if (property.isAnnotationPresent(OneToOne.class))
/* 1306:     */       {
/* 1307:1584 */         OneToOne ann = (OneToOne)property.getAnnotation(OneToOne.class);
/* 1308:1587 */         if ((property.isAnnotationPresent(javax.persistence.Column.class)) || (property.isAnnotationPresent(Columns.class))) {
/* 1309:1589 */           throw new AnnotationException("@Column(s) not allowed on a @OneToOne property: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 1310:     */         }
/* 1311:1596 */         boolean trueOneToOne = (property.isAnnotationPresent(PrimaryKeyJoinColumn.class)) || (property.isAnnotationPresent(PrimaryKeyJoinColumns.class));
/* 1312:     */         
/* 1313:1598 */         Cascade hibernateCascade = (Cascade)property.getAnnotation(Cascade.class);
/* 1314:1599 */         NotFound notFound = (NotFound)property.getAnnotation(NotFound.class);
/* 1315:1600 */         boolean ignoreNotFound = (notFound != null) && (notFound.action().equals(NotFoundAction.IGNORE));
/* 1316:1601 */         OnDelete onDeleteAnn = (OnDelete)property.getAnnotation(OnDelete.class);
/* 1317:1602 */         boolean onDeleteCascade = (onDeleteAnn != null) && (OnDeleteAction.CASCADE.equals(onDeleteAnn.action()));
/* 1318:1603 */         JoinTable assocTable = propertyHolder.getJoinTable(property);
/* 1319:1604 */         if (assocTable != null)
/* 1320:     */         {
/* 1321:1605 */           Join join = propertyHolder.addJoin(assocTable, false);
/* 1322:1606 */           for (Ejb3JoinColumn joinColumn : joinColumns) {
/* 1323:1607 */             joinColumn.setSecondaryTableName(join.getTable().getName());
/* 1324:     */           }
/* 1325:     */         }
/* 1326:1612 */         boolean mandatory = (!ann.optional()) || (forcePersist);
/* 1327:1613 */         bindOneToOne(getCascadeStrategy(ann.cascade(), hibernateCascade, ann.orphanRemoval(), forcePersist), joinColumns, !mandatory, getFetchMode(ann.fetch()), ignoreNotFound, onDeleteCascade, ToOneBinder.getTargetEntity(inferredData, mappings), propertyHolder, inferredData, ann.mappedBy(), trueOneToOne, isIdentifierMapper, inSecondPass, propertyBinder, mappings);
/* 1328:     */       }
/* 1329:1630 */       else if (property.isAnnotationPresent(org.hibernate.annotations.Any.class))
/* 1330:     */       {
/* 1331:1633 */         if ((property.isAnnotationPresent(javax.persistence.Column.class)) || (property.isAnnotationPresent(Columns.class))) {
/* 1332:1635 */           throw new AnnotationException("@Column(s) not allowed on a @Any property: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 1333:     */         }
/* 1334:1641 */         Cascade hibernateCascade = (Cascade)property.getAnnotation(Cascade.class);
/* 1335:1642 */         OnDelete onDeleteAnn = (OnDelete)property.getAnnotation(OnDelete.class);
/* 1336:1643 */         boolean onDeleteCascade = (onDeleteAnn != null) && (OnDeleteAction.CASCADE.equals(onDeleteAnn.action()));
/* 1337:1644 */         JoinTable assocTable = propertyHolder.getJoinTable(property);
/* 1338:1645 */         if (assocTable != null)
/* 1339:     */         {
/* 1340:1646 */           Join join = propertyHolder.addJoin(assocTable, false);
/* 1341:1647 */           for (Ejb3JoinColumn joinColumn : joinColumns) {
/* 1342:1648 */             joinColumn.setSecondaryTableName(join.getTable().getName());
/* 1343:     */           }
/* 1344:     */         }
/* 1345:1651 */         bindAny(getCascadeStrategy(null, hibernateCascade, false, forcePersist), joinColumns, onDeleteCascade, nullability, propertyHolder, inferredData, entityBinder, isIdentifierMapper, mappings);
/* 1346:     */       }
/* 1347:1664 */       else if ((property.isAnnotationPresent(OneToMany.class)) || (property.isAnnotationPresent(ManyToMany.class)) || (property.isAnnotationPresent(ElementCollection.class)) || (property.isAnnotationPresent(ManyToAny.class)))
/* 1348:     */       {
/* 1349:1668 */         OneToMany oneToManyAnn = (OneToMany)property.getAnnotation(OneToMany.class);
/* 1350:1669 */         ManyToMany manyToManyAnn = (ManyToMany)property.getAnnotation(ManyToMany.class);
/* 1351:1670 */         ElementCollection elementCollectionAnn = (ElementCollection)property.getAnnotation(ElementCollection.class);
/* 1352:     */         IndexColumn indexColumn;
/* 1353:     */         IndexColumn indexColumn;
/* 1354:1674 */         if (property.isAnnotationPresent(OrderColumn.class)) {
/* 1355:1675 */           indexColumn = IndexColumn.buildColumnFromAnnotation((OrderColumn)property.getAnnotation(OrderColumn.class), propertyHolder, inferredData, entityBinder.getSecondaryTables(), mappings);
/* 1356:     */         } else {
/* 1357:1686 */           indexColumn = IndexColumn.buildColumnFromAnnotation((org.hibernate.annotations.IndexColumn)property.getAnnotation(org.hibernate.annotations.IndexColumn.class), propertyHolder, inferredData, mappings);
/* 1358:     */         }
/* 1359:1693 */         CollectionBinder collectionBinder = CollectionBinder.getCollectionBinder(propertyHolder.getEntityName(), property, !indexColumn.isImplicit(), property.isAnnotationPresent(MapKeyType.class));
/* 1360:     */         
/* 1361:     */ 
/* 1362:     */ 
/* 1363:     */ 
/* 1364:     */ 
/* 1365:     */ 
/* 1366:     */ 
/* 1367:1701 */         collectionBinder.setIndexColumn(indexColumn);
/* 1368:1702 */         collectionBinder.setMapKey((MapKey)property.getAnnotation(MapKey.class));
/* 1369:1703 */         collectionBinder.setPropertyName(inferredData.getPropertyName());
/* 1370:1704 */         BatchSize batchAnn = (BatchSize)property.getAnnotation(BatchSize.class);
/* 1371:1705 */         collectionBinder.setBatchSize(batchAnn);
/* 1372:1706 */         javax.persistence.OrderBy ejb3OrderByAnn = (javax.persistence.OrderBy)property.getAnnotation(javax.persistence.OrderBy.class);
/* 1373:1707 */         org.hibernate.annotations.OrderBy orderByAnn = (org.hibernate.annotations.OrderBy)property.getAnnotation(org.hibernate.annotations.OrderBy.class);
/* 1374:1708 */         collectionBinder.setEjb3OrderBy(ejb3OrderByAnn);
/* 1375:1709 */         collectionBinder.setSqlOrderBy(orderByAnn);
/* 1376:1710 */         Sort sortAnn = (Sort)property.getAnnotation(Sort.class);
/* 1377:1711 */         collectionBinder.setSort(sortAnn);
/* 1378:1712 */         Cache cachAnn = (Cache)property.getAnnotation(Cache.class);
/* 1379:1713 */         collectionBinder.setCache(cachAnn);
/* 1380:1714 */         collectionBinder.setPropertyHolder(propertyHolder);
/* 1381:1715 */         Cascade hibernateCascade = (Cascade)property.getAnnotation(Cascade.class);
/* 1382:1716 */         NotFound notFound = (NotFound)property.getAnnotation(NotFound.class);
/* 1383:1717 */         boolean ignoreNotFound = (notFound != null) && (notFound.action().equals(NotFoundAction.IGNORE));
/* 1384:1718 */         collectionBinder.setIgnoreNotFound(ignoreNotFound);
/* 1385:1719 */         collectionBinder.setCollectionType(inferredData.getProperty().getElementClass());
/* 1386:1720 */         collectionBinder.setMappings(mappings);
/* 1387:1721 */         collectionBinder.setAccessType(inferredData.getDefaultAccess());
/* 1388:     */         
/* 1389:     */ 
/* 1390:     */ 
/* 1391:1725 */         boolean isJPA2ForValueMapping = property.isAnnotationPresent(ElementCollection.class);
/* 1392:1726 */         PropertyData virtualProperty = isJPA2ForValueMapping ? inferredData : new WrappedInferredData(inferredData, "element");
/* 1393:     */         Ejb3Column[] elementColumns;
/* 1394:     */         Ejb3Column[] elementColumns;
/* 1395:1729 */         if ((property.isAnnotationPresent(javax.persistence.Column.class)) || (property.isAnnotationPresent(Formula.class)))
/* 1396:     */         {
/* 1397:1732 */           javax.persistence.Column ann = (javax.persistence.Column)property.getAnnotation(javax.persistence.Column.class);
/* 1398:1733 */           Formula formulaAnn = (Formula)property.getAnnotation(Formula.class);
/* 1399:1734 */           elementColumns = Ejb3Column.buildColumnFromAnnotation(new javax.persistence.Column[] { ann }, formulaAnn, nullability, propertyHolder, virtualProperty, entityBinder.getSecondaryTables(), mappings);
/* 1400:     */         }
/* 1401:     */         else
/* 1402:     */         {
/* 1403:     */           Ejb3Column[] elementColumns;
/* 1404:1744 */           if (property.isAnnotationPresent(Columns.class))
/* 1405:     */           {
/* 1406:1745 */             Columns anns = (Columns)property.getAnnotation(Columns.class);
/* 1407:1746 */             elementColumns = Ejb3Column.buildColumnFromAnnotation(anns.columns(), null, nullability, propertyHolder, virtualProperty, entityBinder.getSecondaryTables(), mappings);
/* 1408:     */           }
/* 1409:     */           else
/* 1410:     */           {
/* 1411:1752 */             elementColumns = Ejb3Column.buildColumnFromAnnotation(null, null, nullability, propertyHolder, virtualProperty, entityBinder.getSecondaryTables(), mappings);
/* 1412:     */           }
/* 1413:     */         }
/* 1414:1763 */         javax.persistence.Column[] keyColumns = null;
/* 1415:     */         
/* 1416:1765 */         Boolean isJPA2 = null;
/* 1417:1766 */         if (property.isAnnotationPresent(MapKeyColumn.class))
/* 1418:     */         {
/* 1419:1767 */           isJPA2 = Boolean.TRUE;
/* 1420:1768 */           keyColumns = new javax.persistence.Column[] { new MapKeyColumnDelegator((MapKeyColumn)property.getAnnotation(MapKeyColumn.class)) };
/* 1421:     */         }
/* 1422:1772 */         if (isJPA2 == null) {
/* 1423:1773 */           isJPA2 = Boolean.TRUE;
/* 1424:     */         }
/* 1425:1777 */         keyColumns = (keyColumns != null) && (keyColumns.length > 0) ? keyColumns : null;
/* 1426:     */         
/* 1427:     */ 
/* 1428:1780 */         PropertyData mapKeyVirtualProperty = new WrappedInferredData(inferredData, "mapkey");
/* 1429:1781 */         Ejb3Column[] mapColumns = Ejb3Column.buildColumnFromAnnotation(keyColumns, null, Nullability.FORCED_NOT_NULL, propertyHolder, isJPA2.booleanValue() ? inferredData : mapKeyVirtualProperty, isJPA2.booleanValue() ? "_KEY" : null, entityBinder.getSecondaryTables(), mappings);
/* 1430:     */         
/* 1431:     */ 
/* 1432:     */ 
/* 1433:     */ 
/* 1434:     */ 
/* 1435:     */ 
/* 1436:     */ 
/* 1437:     */ 
/* 1438:     */ 
/* 1439:1791 */         collectionBinder.setMapKeyColumns(mapColumns);
/* 1440:     */         
/* 1441:     */ 
/* 1442:1794 */         JoinColumn[] joinKeyColumns = null;
/* 1443:     */         
/* 1444:1796 */         Boolean isJPA2 = null;
/* 1445:1797 */         if (property.isAnnotationPresent(MapKeyJoinColumns.class))
/* 1446:     */         {
/* 1447:1798 */           isJPA2 = Boolean.TRUE;
/* 1448:1799 */           MapKeyJoinColumn[] mapKeyJoinColumns = ((MapKeyJoinColumns)property.getAnnotation(MapKeyJoinColumns.class)).value();
/* 1449:     */           
/* 1450:1801 */           joinKeyColumns = new JoinColumn[mapKeyJoinColumns.length];
/* 1451:1802 */           int index = 0;
/* 1452:1803 */           for (MapKeyJoinColumn joinColumn : mapKeyJoinColumns)
/* 1453:     */           {
/* 1454:1804 */             joinKeyColumns[index] = new MapKeyJoinColumnDelegator(joinColumn);
/* 1455:1805 */             index++;
/* 1456:     */           }
/* 1457:1807 */           if (property.isAnnotationPresent(MapKeyJoinColumn.class)) {
/* 1458:1808 */             throw new AnnotationException("@MapKeyJoinColumn and @MapKeyJoinColumns used on the same property: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 1459:     */           }
/* 1460:     */         }
/* 1461:1814 */         else if (property.isAnnotationPresent(MapKeyJoinColumn.class))
/* 1462:     */         {
/* 1463:1815 */           isJPA2 = Boolean.TRUE;
/* 1464:1816 */           joinKeyColumns = new JoinColumn[] { new MapKeyJoinColumnDelegator((MapKeyJoinColumn)property.getAnnotation(MapKeyJoinColumn.class)) };
/* 1465:     */         }
/* 1466:1825 */         if (isJPA2 == null) {
/* 1467:1826 */           isJPA2 = Boolean.TRUE;
/* 1468:     */         }
/* 1469:1829 */         PropertyData mapKeyVirtualProperty = new WrappedInferredData(inferredData, "mapkey");
/* 1470:1830 */         Ejb3JoinColumn[] mapJoinColumns = Ejb3JoinColumn.buildJoinColumnsWithDefaultColumnSuffix(joinKeyColumns, null, entityBinder.getSecondaryTables(), propertyHolder, isJPA2.booleanValue() ? inferredData.getPropertyName() : mapKeyVirtualProperty.getPropertyName(), isJPA2.booleanValue() ? "_KEY" : null, mappings);
/* 1471:     */         
/* 1472:     */ 
/* 1473:     */ 
/* 1474:     */ 
/* 1475:     */ 
/* 1476:     */ 
/* 1477:     */ 
/* 1478:     */ 
/* 1479:1839 */         collectionBinder.setMapKeyManyToManyColumns(mapJoinColumns);
/* 1480:     */         
/* 1481:     */ 
/* 1482:     */ 
/* 1483:1843 */         collectionBinder.setEmbedded(property.isAnnotationPresent(Embedded.class));
/* 1484:1844 */         collectionBinder.setElementColumns(elementColumns);
/* 1485:1845 */         collectionBinder.setProperty(property);
/* 1486:1848 */         if ((oneToManyAnn != null) && (manyToManyAnn != null)) {
/* 1487:1849 */           throw new AnnotationException("@OneToMany and @ManyToMany on the same property is not allowed: " + propertyHolder.getEntityName() + "." + inferredData.getPropertyName());
/* 1488:     */         }
/* 1489:1854 */         String mappedBy = null;
/* 1490:1855 */         if (oneToManyAnn != null)
/* 1491:     */         {
/* 1492:1856 */           for (Ejb3JoinColumn column : joinColumns) {
/* 1493:1857 */             if (column.isSecondary()) {
/* 1494:1858 */               throw new NotYetImplementedException("Collections having FK in secondary table");
/* 1495:     */             }
/* 1496:     */           }
/* 1497:1861 */           collectionBinder.setFkJoinColumns(joinColumns);
/* 1498:1862 */           mappedBy = oneToManyAnn.mappedBy();
/* 1499:1863 */           collectionBinder.setTargetEntity(mappings.getReflectionManager().toXClass(oneToManyAnn.targetEntity()));
/* 1500:     */           
/* 1501:     */ 
/* 1502:1866 */           collectionBinder.setCascadeStrategy(getCascadeStrategy(oneToManyAnn.cascade(), hibernateCascade, oneToManyAnn.orphanRemoval(), false));
/* 1503:     */           
/* 1504:     */ 
/* 1505:     */ 
/* 1506:     */ 
/* 1507:1871 */           collectionBinder.setOneToMany(true);
/* 1508:     */         }
/* 1509:1873 */         else if (elementCollectionAnn != null)
/* 1510:     */         {
/* 1511:1874 */           for (Ejb3JoinColumn column : joinColumns) {
/* 1512:1875 */             if (column.isSecondary()) {
/* 1513:1876 */               throw new NotYetImplementedException("Collections having FK in secondary table");
/* 1514:     */             }
/* 1515:     */           }
/* 1516:1879 */           collectionBinder.setFkJoinColumns(joinColumns);
/* 1517:1880 */           mappedBy = "";
/* 1518:1881 */           Class<?> targetElement = elementCollectionAnn.targetClass();
/* 1519:1882 */           collectionBinder.setTargetEntity(mappings.getReflectionManager().toXClass(targetElement));
/* 1520:     */           
/* 1521:     */ 
/* 1522:     */ 
/* 1523:1886 */           collectionBinder.setOneToMany(true);
/* 1524:     */         }
/* 1525:1888 */         else if (manyToManyAnn != null)
/* 1526:     */         {
/* 1527:1889 */           mappedBy = manyToManyAnn.mappedBy();
/* 1528:1890 */           collectionBinder.setTargetEntity(mappings.getReflectionManager().toXClass(manyToManyAnn.targetEntity()));
/* 1529:     */           
/* 1530:     */ 
/* 1531:1893 */           collectionBinder.setCascadeStrategy(getCascadeStrategy(manyToManyAnn.cascade(), hibernateCascade, false, false));
/* 1532:     */           
/* 1533:     */ 
/* 1534:     */ 
/* 1535:     */ 
/* 1536:1898 */           collectionBinder.setOneToMany(false);
/* 1537:     */         }
/* 1538:1900 */         else if (property.isAnnotationPresent(ManyToAny.class))
/* 1539:     */         {
/* 1540:1901 */           mappedBy = "";
/* 1541:1902 */           collectionBinder.setTargetEntity(mappings.getReflectionManager().toXClass(Void.TYPE));
/* 1542:     */           
/* 1543:     */ 
/* 1544:1905 */           collectionBinder.setCascadeStrategy(getCascadeStrategy(null, hibernateCascade, false, false));
/* 1545:1906 */           collectionBinder.setOneToMany(false);
/* 1546:     */         }
/* 1547:1908 */         collectionBinder.setMappedBy(mappedBy);
/* 1548:     */         
/* 1549:1910 */         bindJoinedTableAssociation(property, mappings, entityBinder, collectionBinder, propertyHolder, inferredData, mappedBy);
/* 1550:     */         
/* 1551:     */ 
/* 1552:     */ 
/* 1553:1914 */         OnDelete onDeleteAnn = (OnDelete)property.getAnnotation(OnDelete.class);
/* 1554:1915 */         boolean onDeleteCascade = (onDeleteAnn != null) && (OnDeleteAction.CASCADE.equals(onDeleteAnn.action()));
/* 1555:1916 */         collectionBinder.setCascadeDeleteEnabled(onDeleteCascade);
/* 1556:1917 */         if (isIdentifierMapper)
/* 1557:     */         {
/* 1558:1918 */           collectionBinder.setInsertable(false);
/* 1559:1919 */           collectionBinder.setUpdatable(false);
/* 1560:     */         }
/* 1561:1921 */         if (property.isAnnotationPresent(CollectionId.class))
/* 1562:     */         {
/* 1563:1922 */           HashMap<String, IdGenerator> localGenerators = (HashMap)classGenerators.clone();
/* 1564:1923 */           localGenerators.putAll(buildLocalGenerators(property, mappings));
/* 1565:1924 */           collectionBinder.setLocalGenerators(localGenerators);
/* 1566:     */         }
/* 1567:1927 */         collectionBinder.setInheritanceStatePerClass(inheritanceStatePerClass);
/* 1568:1928 */         collectionBinder.setDeclaringClass(inferredData.getDeclaringClass());
/* 1569:1929 */         collectionBinder.bind();
/* 1570:     */       }
/* 1571:1933 */       else if ((!isId) || (!entityBinder.isIgnoreIdAnnotations()))
/* 1572:     */       {
/* 1573:1936 */         boolean isComponent = false;
/* 1574:     */         
/* 1575:     */ 
/* 1576:1939 */         boolean isOverridden = false;
/* 1577:1940 */         if ((isId) || (propertyHolder.isOrWithinEmbeddedId()) || (propertyHolder.isInIdClass()))
/* 1578:     */         {
/* 1579:1942 */           PropertyData overridingProperty = BinderHelper.getPropertyOverriddenByMapperOrMapsId(isId, propertyHolder, property.getName(), mappings);
/* 1580:1945 */           if (overridingProperty != null)
/* 1581:     */           {
/* 1582:1946 */             isOverridden = true;
/* 1583:1947 */             InheritanceState state = (InheritanceState)inheritanceStatePerClass.get(overridingProperty.getClassOrElement());
/* 1584:1948 */             if (state != null) {
/* 1585:1949 */               isComponent = (isComponent) || (state.hasIdClassOrEmbeddedId().booleanValue());
/* 1586:     */             }
/* 1587:1952 */             columns = columnsBuilder.overrideColumnFromMapperOrMapsIdProperty(isId);
/* 1588:     */           }
/* 1589:     */         }
/* 1590:1956 */         isComponent = (isComponent) || (property.isAnnotationPresent(Embedded.class)) || (property.isAnnotationPresent(EmbeddedId.class)) || (returnedClass.isAnnotationPresent(Embeddable.class));
/* 1591:1962 */         if (isComponent)
/* 1592:     */         {
/* 1593:1963 */           String referencedEntityName = null;
/* 1594:1964 */           if (isOverridden)
/* 1595:     */           {
/* 1596:1965 */             PropertyData mapsIdProperty = BinderHelper.getPropertyOverriddenByMapperOrMapsId(isId, propertyHolder, property.getName(), mappings);
/* 1597:     */             
/* 1598:     */ 
/* 1599:1968 */             referencedEntityName = mapsIdProperty.getClassOrElementName();
/* 1600:     */           }
/* 1601:1970 */           AccessType propertyAccessor = entityBinder.getPropertyAccessor(property);
/* 1602:1971 */           propertyBinder = bindComponent(inferredData, propertyHolder, propertyAccessor, entityBinder, isIdentifierMapper, mappings, isComponentEmbedded, isId, inheritanceStatePerClass, referencedEntityName, isOverridden ? (Ejb3JoinColumn[])columns : null);
/* 1603:     */         }
/* 1604:     */         else
/* 1605:     */         {
/* 1606:1987 */           boolean optional = true;
/* 1607:1988 */           boolean lazy = false;
/* 1608:1989 */           if (property.isAnnotationPresent(Basic.class))
/* 1609:     */           {
/* 1610:1990 */             Basic ann = (Basic)property.getAnnotation(Basic.class);
/* 1611:1991 */             optional = ann.optional();
/* 1612:1992 */             lazy = ann.fetch() == FetchType.LAZY;
/* 1613:     */           }
/* 1614:1995 */           if ((isId) || ((!optional) && (nullability != Nullability.FORCED_NULL))) {
/* 1615:1997 */             for (Ejb3Column col : columns) {
/* 1616:1998 */               col.forceNotNull();
/* 1617:     */             }
/* 1618:     */           }
/* 1619:2002 */           propertyBinder.setLazy(lazy);
/* 1620:2003 */           propertyBinder.setColumns(columns);
/* 1621:2004 */           if (isOverridden)
/* 1622:     */           {
/* 1623:2005 */             PropertyData mapsIdProperty = BinderHelper.getPropertyOverriddenByMapperOrMapsId(isId, propertyHolder, property.getName(), mappings);
/* 1624:     */             
/* 1625:     */ 
/* 1626:2008 */             propertyBinder.setReferencedEntityName(mapsIdProperty.getClassOrElementName());
/* 1627:     */           }
/* 1628:2011 */           propertyBinder.makePropertyValueAndBind();
/* 1629:     */         }
/* 1630:2014 */         if (isOverridden)
/* 1631:     */         {
/* 1632:2015 */           PropertyData mapsIdProperty = BinderHelper.getPropertyOverriddenByMapperOrMapsId(isId, propertyHolder, property.getName(), mappings);
/* 1633:     */           
/* 1634:     */ 
/* 1635:2018 */           Map<String, IdGenerator> localGenerators = (HashMap)classGenerators.clone();
/* 1636:2019 */           IdGenerator foreignGenerator = new IdGenerator();
/* 1637:2020 */           foreignGenerator.setIdentifierGeneratorStrategy("assigned");
/* 1638:2021 */           foreignGenerator.setName("Hibernate-local--foreign generator");
/* 1639:2022 */           foreignGenerator.setIdentifierGeneratorStrategy("foreign");
/* 1640:2023 */           foreignGenerator.addParam("property", mapsIdProperty.getPropertyName());
/* 1641:2024 */           localGenerators.put(foreignGenerator.getName(), foreignGenerator);
/* 1642:     */           
/* 1643:2026 */           BinderHelper.makeIdGenerator((SimpleValue)propertyBinder.getValue(), foreignGenerator.getIdentifierGeneratorStrategy(), foreignGenerator.getName(), mappings, localGenerators);
/* 1644:     */         }
/* 1645:2034 */         if (isId)
/* 1646:     */         {
/* 1647:2036 */           SimpleValue value = (SimpleValue)propertyBinder.getValue();
/* 1648:2037 */           if (!isOverridden) {
/* 1649:2038 */             processId(propertyHolder, inferredData, value, classGenerators, isIdentifierMapper, mappings);
/* 1650:     */           }
/* 1651:     */         }
/* 1652:     */       }
/* 1653:     */     }
/* 1654:2052 */     Index index = (Index)property.getAnnotation(Index.class);
/* 1655:2053 */     if (index != null) {
/* 1656:2054 */       if (joinColumns != null) {
/* 1657:2056 */         for (Ejb3Column column : joinColumns) {
/* 1658:2057 */           column.addIndex(index, inSecondPass);
/* 1659:     */         }
/* 1660:2061 */       } else if (columns != null) {
/* 1661:2062 */         for (Ejb3Column column : columns) {
/* 1662:2063 */           column.addIndex(index, inSecondPass);
/* 1663:     */         }
/* 1664:     */       }
/* 1665:     */     }
/* 1666:2069 */     NaturalId naturalIdAnn = (NaturalId)property.getAnnotation(NaturalId.class);
/* 1667:2070 */     if (naturalIdAnn != null) {
/* 1668:2071 */       if (joinColumns != null) {
/* 1669:2072 */         for (Ejb3Column column : joinColumns) {
/* 1670:2073 */           column.addUniqueKey("_UniqueKey", inSecondPass);
/* 1671:     */         }
/* 1672:     */       } else {
/* 1673:2077 */         for (Ejb3Column column : columns) {
/* 1674:2078 */           column.addUniqueKey("_UniqueKey", inSecondPass);
/* 1675:     */         }
/* 1676:     */       }
/* 1677:     */     }
/* 1678:     */   }
/* 1679:     */   
/* 1680:     */   private static void setVersionInformation(XProperty property, PropertyBinder propertyBinder)
/* 1681:     */   {
/* 1682:2085 */     propertyBinder.getSimpleValueBinder().setVersion(true);
/* 1683:2086 */     if (property.isAnnotationPresent(Source.class))
/* 1684:     */     {
/* 1685:2087 */       Source source = (Source)property.getAnnotation(Source.class);
/* 1686:2088 */       propertyBinder.getSimpleValueBinder().setTimestampVersionType(source.value().typeName());
/* 1687:     */     }
/* 1688:     */   }
/* 1689:     */   
/* 1690:     */   private static void processId(PropertyHolder propertyHolder, PropertyData inferredData, SimpleValue idValue, HashMap<String, IdGenerator> classGenerators, boolean isIdentifierMapper, Mappings mappings)
/* 1691:     */   {
/* 1692:2099 */     if (isIdentifierMapper) {
/* 1693:2100 */       throw new AnnotationException("@IdClass class should not have @Id nor @EmbeddedId properties: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 1694:     */     }
/* 1695:2105 */     XClass returnedClass = inferredData.getClassOrElement();
/* 1696:2106 */     XProperty property = inferredData.getProperty();
/* 1697:     */     
/* 1698:2108 */     HashMap<String, IdGenerator> localGenerators = (HashMap)classGenerators.clone();
/* 1699:2109 */     localGenerators.putAll(buildLocalGenerators(property, mappings));
/* 1700:     */     
/* 1701:     */ 
/* 1702:     */ 
/* 1703:2113 */     boolean isComponent = (returnedClass.isAnnotationPresent(Embeddable.class)) || (property.isAnnotationPresent(EmbeddedId.class));
/* 1704:     */     
/* 1705:     */ 
/* 1706:2116 */     GeneratedValue generatedValue = (GeneratedValue)property.getAnnotation(GeneratedValue.class);
/* 1707:2117 */     String generatorType = generatedValue != null ? generatorType(generatedValue.strategy(), mappings) : "assigned";
/* 1708:     */     
/* 1709:     */ 
/* 1710:2120 */     String generatorName = generatedValue != null ? generatedValue.generator() : "";
/* 1711:2123 */     if (isComponent) {
/* 1712:2124 */       generatorType = "assigned";
/* 1713:     */     }
/* 1714:2126 */     BinderHelper.makeIdGenerator(idValue, generatorType, generatorName, mappings, localGenerators);
/* 1715:2128 */     if (LOG.isTraceEnabled()) {
/* 1716:2129 */       LOG.tracev("Bind {0} on {1}", isComponent ? "@EmbeddedId" : "@Id", inferredData.getPropertyName());
/* 1717:     */     }
/* 1718:     */   }
/* 1719:     */   
/* 1720:     */   private static void bindJoinedTableAssociation(XProperty property, Mappings mappings, EntityBinder entityBinder, CollectionBinder collectionBinder, PropertyHolder propertyHolder, PropertyData inferredData, String mappedBy)
/* 1721:     */   {
/* 1722:2143 */     TableBinder associationTableBinder = new TableBinder();
/* 1723:     */     
/* 1724:     */ 
/* 1725:2146 */     JoinTable assocTable = propertyHolder.getJoinTable(property);
/* 1726:2147 */     CollectionTable collectionTable = (CollectionTable)property.getAnnotation(CollectionTable.class);
/* 1727:     */     JoinColumn[] annInverseJoins;
/* 1728:     */     JoinColumn[] annJoins;
/* 1729:     */     JoinColumn[] annInverseJoins;
/* 1730:2149 */     if ((assocTable != null) || (collectionTable != null))
/* 1731:     */     {
/* 1732:     */       JoinColumn[] inverseJoins;
/* 1733:     */       String catalog;
/* 1734:     */       String schema;
/* 1735:     */       String tableName;
/* 1736:     */       UniqueConstraint[] uniqueConstraints;
/* 1737:     */       JoinColumn[] joins;
/* 1738:     */       JoinColumn[] inverseJoins;
/* 1739:2159 */       if (collectionTable != null)
/* 1740:     */       {
/* 1741:2160 */         String catalog = collectionTable.catalog();
/* 1742:2161 */         String schema = collectionTable.schema();
/* 1743:2162 */         String tableName = collectionTable.name();
/* 1744:2163 */         UniqueConstraint[] uniqueConstraints = collectionTable.uniqueConstraints();
/* 1745:2164 */         JoinColumn[] joins = collectionTable.joinColumns();
/* 1746:2165 */         inverseJoins = null;
/* 1747:     */       }
/* 1748:     */       else
/* 1749:     */       {
/* 1750:2168 */         catalog = assocTable.catalog();
/* 1751:2169 */         schema = assocTable.schema();
/* 1752:2170 */         tableName = assocTable.name();
/* 1753:2171 */         uniqueConstraints = assocTable.uniqueConstraints();
/* 1754:2172 */         joins = assocTable.joinColumns();
/* 1755:2173 */         inverseJoins = assocTable.inverseJoinColumns();
/* 1756:     */       }
/* 1757:2176 */       collectionBinder.setExplicitAssociationTable(true);
/* 1758:2178 */       if (!BinderHelper.isEmptyAnnotationValue(schema)) {
/* 1759:2179 */         associationTableBinder.setSchema(schema);
/* 1760:     */       }
/* 1761:2181 */       if (!BinderHelper.isEmptyAnnotationValue(catalog)) {
/* 1762:2182 */         associationTableBinder.setCatalog(catalog);
/* 1763:     */       }
/* 1764:2184 */       if (!BinderHelper.isEmptyAnnotationValue(tableName)) {
/* 1765:2185 */         associationTableBinder.setName(tableName);
/* 1766:     */       }
/* 1767:2187 */       associationTableBinder.setUniqueConstraints(uniqueConstraints);
/* 1768:     */       
/* 1769:     */ 
/* 1770:2190 */       JoinColumn[] annJoins = joins.length == 0 ? null : joins;
/* 1771:2191 */       annInverseJoins = (inverseJoins == null) || (inverseJoins.length == 0) ? null : inverseJoins;
/* 1772:     */     }
/* 1773:     */     else
/* 1774:     */     {
/* 1775:2194 */       annJoins = null;
/* 1776:2195 */       annInverseJoins = null;
/* 1777:     */     }
/* 1778:2197 */     Ejb3JoinColumn[] joinColumns = Ejb3JoinColumn.buildJoinTableJoinColumns(annJoins, entityBinder.getSecondaryTables(), propertyHolder, inferredData.getPropertyName(), mappedBy, mappings);
/* 1779:     */     
/* 1780:     */ 
/* 1781:     */ 
/* 1782:2201 */     Ejb3JoinColumn[] inverseJoinColumns = Ejb3JoinColumn.buildJoinTableJoinColumns(annInverseJoins, entityBinder.getSecondaryTables(), propertyHolder, inferredData.getPropertyName(), mappedBy, mappings);
/* 1783:     */     
/* 1784:     */ 
/* 1785:     */ 
/* 1786:2205 */     associationTableBinder.setMappings(mappings);
/* 1787:2206 */     collectionBinder.setTableBinder(associationTableBinder);
/* 1788:2207 */     collectionBinder.setJoinColumns(joinColumns);
/* 1789:2208 */     collectionBinder.setInverseJoinColumns(inverseJoinColumns);
/* 1790:     */   }
/* 1791:     */   
/* 1792:     */   private static PropertyBinder bindComponent(PropertyData inferredData, PropertyHolder propertyHolder, AccessType propertyAccessor, EntityBinder entityBinder, boolean isIdentifierMapper, Mappings mappings, boolean isComponentEmbedded, boolean isId, Map<XClass, InheritanceState> inheritanceStatePerClass, String referencedEntityName, Ejb3JoinColumn[] columns)
/* 1793:     */   {
/* 1794:     */     Component comp;
/* 1795:2224 */     if (referencedEntityName != null)
/* 1796:     */     {
/* 1797:2225 */       Component comp = createComponent(propertyHolder, inferredData, isComponentEmbedded, isIdentifierMapper, mappings);
/* 1798:2226 */       SecondPass sp = new CopyIdentifierComponentSecondPass(comp, referencedEntityName, columns, mappings);
/* 1799:     */       
/* 1800:     */ 
/* 1801:     */ 
/* 1802:     */ 
/* 1803:     */ 
/* 1804:2232 */       mappings.addSecondPass(sp);
/* 1805:     */     }
/* 1806:     */     else
/* 1807:     */     {
/* 1808:2235 */       comp = fillComponent(propertyHolder, inferredData, propertyAccessor, !isId, entityBinder, isComponentEmbedded, isIdentifierMapper, false, mappings, inheritanceStatePerClass);
/* 1809:     */     }
/* 1810:2241 */     if (isId)
/* 1811:     */     {
/* 1812:2242 */       comp.setKey(true);
/* 1813:2243 */       if (propertyHolder.getPersistentClass().getIdentifier() != null) {
/* 1814:2244 */         throw new AnnotationException(comp.getComponentClassName() + " must not have @Id properties when used as an @EmbeddedId: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 1815:     */       }
/* 1816:2250 */       if ((referencedEntityName == null) && (comp.getPropertySpan() == 0)) {
/* 1817:2251 */         throw new AnnotationException(comp.getComponentClassName() + " has no persistent id property: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 1818:     */       }
/* 1819:     */     }
/* 1820:2258 */     XProperty property = inferredData.getProperty();
/* 1821:2259 */     setupComponentTuplizer(property, comp);
/* 1822:2260 */     PropertyBinder binder = new PropertyBinder();
/* 1823:2261 */     binder.setName(inferredData.getPropertyName());
/* 1824:2262 */     binder.setValue(comp);
/* 1825:2263 */     binder.setProperty(inferredData.getProperty());
/* 1826:2264 */     binder.setAccessType(inferredData.getDefaultAccess());
/* 1827:2265 */     binder.setEmbedded(isComponentEmbedded);
/* 1828:2266 */     binder.setHolder(propertyHolder);
/* 1829:2267 */     binder.setId(isId);
/* 1830:2268 */     binder.setEntityBinder(entityBinder);
/* 1831:2269 */     binder.setInheritanceStatePerClass(inheritanceStatePerClass);
/* 1832:2270 */     binder.setMappings(mappings);
/* 1833:2271 */     binder.makePropertyAndBind();
/* 1834:2272 */     return binder;
/* 1835:     */   }
/* 1836:     */   
/* 1837:     */   public static Component fillComponent(PropertyHolder propertyHolder, PropertyData inferredData, AccessType propertyAccessor, boolean isNullable, EntityBinder entityBinder, boolean isComponentEmbedded, boolean isIdentifierMapper, boolean inSecondPass, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/* 1838:     */   {
/* 1839:2286 */     return fillComponent(propertyHolder, inferredData, null, propertyAccessor, isNullable, entityBinder, isComponentEmbedded, isIdentifierMapper, inSecondPass, mappings, inheritanceStatePerClass);
/* 1840:     */   }
/* 1841:     */   
/* 1842:     */   public static Component fillComponent(PropertyHolder propertyHolder, PropertyData inferredData, PropertyData baseInferredData, AccessType propertyAccessor, boolean isNullable, EntityBinder entityBinder, boolean isComponentEmbedded, boolean isIdentifierMapper, boolean inSecondPass, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/* 1843:     */   {
/* 1844:2310 */     Component comp = createComponent(propertyHolder, inferredData, isComponentEmbedded, isIdentifierMapper, mappings);
/* 1845:2311 */     String subpath = BinderHelper.getPath(propertyHolder, inferredData);
/* 1846:2312 */     LOG.tracev("Binding component with path: {0}", subpath);
/* 1847:2313 */     PropertyHolder subHolder = PropertyHolderBuilder.buildPropertyHolder(comp, subpath, inferredData, propertyHolder, mappings);
/* 1848:     */     
/* 1849:     */ 
/* 1850:     */ 
/* 1851:     */ 
/* 1852:2318 */     XClass xClassProcessed = inferredData.getPropertyClass();
/* 1853:2319 */     List<PropertyData> classElements = new ArrayList();
/* 1854:2320 */     XClass returnedClassOrElement = inferredData.getClassOrElement();
/* 1855:     */     
/* 1856:2322 */     List<PropertyData> baseClassElements = null;
/* 1857:2323 */     Map<String, PropertyData> orderedBaseClassElements = new HashMap();
/* 1858:2325 */     if (baseInferredData != null)
/* 1859:     */     {
/* 1860:2326 */       baseClassElements = new ArrayList();
/* 1861:2327 */       XClass baseReturnedClassOrElement = baseInferredData.getClassOrElement();
/* 1862:2328 */       bindTypeDefs(baseReturnedClassOrElement, mappings);
/* 1863:2329 */       PropertyContainer propContainer = new PropertyContainer(baseReturnedClassOrElement, xClassProcessed);
/* 1864:2330 */       addElementsOfClass(baseClassElements, propertyAccessor, propContainer, mappings);
/* 1865:2331 */       for (PropertyData element : baseClassElements) {
/* 1866:2332 */         orderedBaseClassElements.put(element.getPropertyName(), element);
/* 1867:     */       }
/* 1868:     */     }
/* 1869:2337 */     bindTypeDefs(returnedClassOrElement, mappings);
/* 1870:2338 */     PropertyContainer propContainer = new PropertyContainer(returnedClassOrElement, xClassProcessed);
/* 1871:2339 */     addElementsOfClass(classElements, propertyAccessor, propContainer, mappings);
/* 1872:     */     
/* 1873:     */ 
/* 1874:2342 */     XClass superClass = xClassProcessed.getSuperclass();
/* 1875:2343 */     while ((superClass != null) && (superClass.isAnnotationPresent(javax.persistence.MappedSuperclass.class)))
/* 1876:     */     {
/* 1877:2345 */       propContainer = new PropertyContainer(superClass, xClassProcessed);
/* 1878:2346 */       addElementsOfClass(classElements, propertyAccessor, propContainer, mappings);
/* 1879:2347 */       superClass = superClass.getSuperclass();
/* 1880:     */     }
/* 1881:2349 */     if (baseClassElements != null) {
/* 1882:2351 */       if (!hasAnnotationsOnIdClass(xClassProcessed)) {
/* 1883:2352 */         for (int i = 0; i < classElements.size(); i++)
/* 1884:     */         {
/* 1885:2353 */           PropertyData idClassPropertyData = (PropertyData)classElements.get(i);
/* 1886:2354 */           PropertyData entityPropertyData = (PropertyData)orderedBaseClassElements.get(idClassPropertyData.getPropertyName());
/* 1887:2355 */           if (propertyHolder.isInIdClass())
/* 1888:     */           {
/* 1889:2356 */             if (entityPropertyData == null) {
/* 1890:2357 */               throw new AnnotationException("Property of @IdClass not found in entity " + baseInferredData.getPropertyClass().getName() + ": " + idClassPropertyData.getPropertyName());
/* 1891:     */             }
/* 1892:2363 */             boolean hasXToOneAnnotation = (entityPropertyData.getProperty().isAnnotationPresent(javax.persistence.ManyToOne.class)) || (entityPropertyData.getProperty().isAnnotationPresent(OneToOne.class));
/* 1893:     */             
/* 1894:     */ 
/* 1895:2366 */             boolean isOfDifferentType = !entityPropertyData.getClassOrElement().equals(idClassPropertyData.getClassOrElement());
/* 1896:2368 */             if ((!hasXToOneAnnotation) || (!isOfDifferentType)) {
/* 1897:2373 */               classElements.set(i, entityPropertyData);
/* 1898:     */             }
/* 1899:     */           }
/* 1900:     */           else
/* 1901:     */           {
/* 1902:2377 */             classElements.set(i, entityPropertyData);
/* 1903:     */           }
/* 1904:     */         }
/* 1905:     */       }
/* 1906:     */     }
/* 1907:2382 */     for (PropertyData propertyAnnotatedElement : classElements)
/* 1908:     */     {
/* 1909:2383 */       processElementAnnotations(subHolder, isNullable ? Nullability.NO_CONSTRAINT : Nullability.FORCED_NOT_NULL, propertyAnnotatedElement, new HashMap(), entityBinder, isIdentifierMapper, isComponentEmbedded, inSecondPass, mappings, inheritanceStatePerClass);
/* 1910:     */       
/* 1911:     */ 
/* 1912:     */ 
/* 1913:     */ 
/* 1914:     */ 
/* 1915:     */ 
/* 1916:     */ 
/* 1917:     */ 
/* 1918:2392 */       XProperty property = propertyAnnotatedElement.getProperty();
/* 1919:2393 */       if ((property.isAnnotationPresent(GeneratedValue.class)) && (property.isAnnotationPresent(Id.class)))
/* 1920:     */       {
/* 1921:2396 */         Map<String, IdGenerator> localGenerators = new HashMap();
/* 1922:2397 */         localGenerators.putAll(buildLocalGenerators(property, mappings));
/* 1923:     */         
/* 1924:2399 */         GeneratedValue generatedValue = (GeneratedValue)property.getAnnotation(GeneratedValue.class);
/* 1925:2400 */         String generatorType = generatedValue != null ? generatorType(generatedValue.strategy(), mappings) : "assigned";
/* 1926:     */         
/* 1927:     */ 
/* 1928:2403 */         String generator = generatedValue != null ? generatedValue.generator() : "";
/* 1929:     */         
/* 1930:2405 */         BinderHelper.makeIdGenerator((SimpleValue)comp.getProperty(property.getName()).getValue(), generatorType, generator, mappings, localGenerators);
/* 1931:     */       }
/* 1932:     */     }
/* 1933:2415 */     return comp;
/* 1934:     */   }
/* 1935:     */   
/* 1936:     */   public static Component createComponent(PropertyHolder propertyHolder, PropertyData inferredData, boolean isComponentEmbedded, boolean isIdentifierMapper, Mappings mappings)
/* 1937:     */   {
/* 1938:2424 */     Component comp = new Component(mappings, propertyHolder.getPersistentClass());
/* 1939:2425 */     comp.setEmbedded(isComponentEmbedded);
/* 1940:     */     
/* 1941:2427 */     comp.setTable(propertyHolder.getTable());
/* 1942:2429 */     if ((isIdentifierMapper) || ((isComponentEmbedded) && (inferredData.getPropertyName() == null))) {
/* 1943:2430 */       comp.setComponentClassName(comp.getOwner().getClassName());
/* 1944:     */     } else {
/* 1945:2433 */       comp.setComponentClassName(inferredData.getClassOrElementName());
/* 1946:     */     }
/* 1947:2435 */     comp.setNodeName(inferredData.getPropertyName());
/* 1948:2436 */     return comp;
/* 1949:     */   }
/* 1950:     */   
/* 1951:     */   private static void bindIdClass(String generatorType, String generatorName, PropertyData inferredData, PropertyData baseInferredData, Ejb3Column[] columns, PropertyHolder propertyHolder, boolean isComposite, AccessType propertyAccessor, EntityBinder entityBinder, boolean isEmbedded, boolean isIdentifierMapper, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/* 1952:     */   {
/* 1953:2457 */     PersistentClass persistentClass = propertyHolder.getPersistentClass();
/* 1954:2458 */     if (!(persistentClass instanceof RootClass)) {
/* 1955:2459 */       throw new AnnotationException("Unable to define/override @Id(s) on a subclass: " + propertyHolder.getEntityName());
/* 1956:     */     }
/* 1957:2464 */     RootClass rootClass = (RootClass)persistentClass;
/* 1958:2465 */     String persistentClassName = rootClass.getClassName();
/* 1959:     */     
/* 1960:2467 */     String propertyName = inferredData.getPropertyName();
/* 1961:2468 */     HashMap<String, IdGenerator> localGenerators = new HashMap();
/* 1962:     */     SimpleValue id;
/* 1963:2469 */     if (isComposite)
/* 1964:     */     {
/* 1965:2470 */       SimpleValue id = fillComponent(propertyHolder, inferredData, baseInferredData, propertyAccessor, false, entityBinder, isEmbedded, isIdentifierMapper, false, mappings, inheritanceStatePerClass);
/* 1966:     */       
/* 1967:     */ 
/* 1968:     */ 
/* 1969:2474 */       Component componentId = (Component)id;
/* 1970:2475 */       componentId.setKey(true);
/* 1971:2476 */       if (rootClass.getIdentifier() != null) {
/* 1972:2477 */         throw new AnnotationException(componentId.getComponentClassName() + " must not have @Id properties when used as an @EmbeddedId");
/* 1973:     */       }
/* 1974:2479 */       if (componentId.getPropertySpan() == 0) {
/* 1975:2480 */         throw new AnnotationException(componentId.getComponentClassName() + " has no persistent id property");
/* 1976:     */       }
/* 1977:2483 */       XProperty property = inferredData.getProperty();
/* 1978:2484 */       setupComponentTuplizer(property, componentId);
/* 1979:     */     }
/* 1980:     */     else
/* 1981:     */     {
/* 1982:2489 */       for (Ejb3Column column : columns) {
/* 1983:2490 */         column.forceNotNull();
/* 1984:     */       }
/* 1985:2492 */       SimpleValueBinder value = new SimpleValueBinder();
/* 1986:2493 */       value.setPropertyName(propertyName);
/* 1987:2494 */       value.setReturnedClassName(inferredData.getTypeName());
/* 1988:2495 */       value.setColumns(columns);
/* 1989:2496 */       value.setPersistentClassName(persistentClassName);
/* 1990:2497 */       value.setMappings(mappings);
/* 1991:2498 */       value.setType(inferredData.getProperty(), inferredData.getClassOrElement());
/* 1992:2499 */       id = value.make();
/* 1993:     */     }
/* 1994:2501 */     rootClass.setIdentifier(id);
/* 1995:2502 */     BinderHelper.makeIdGenerator(id, generatorType, generatorName, mappings, localGenerators);
/* 1996:2503 */     if (isEmbedded)
/* 1997:     */     {
/* 1998:2504 */       rootClass.setEmbeddedIdentifier(inferredData.getPropertyClass() == null);
/* 1999:     */     }
/* 2000:     */     else
/* 2001:     */     {
/* 2002:2507 */       PropertyBinder binder = new PropertyBinder();
/* 2003:2508 */       binder.setName(propertyName);
/* 2004:2509 */       binder.setValue(id);
/* 2005:2510 */       binder.setAccessType(inferredData.getDefaultAccess());
/* 2006:2511 */       binder.setProperty(inferredData.getProperty());
/* 2007:2512 */       Property prop = binder.makeProperty();
/* 2008:2513 */       rootClass.setIdentifierProperty(prop);
/* 2009:     */       
/* 2010:2515 */       org.hibernate.mapping.MappedSuperclass superclass = BinderHelper.getMappedSuperclassOrNull(inferredData.getDeclaringClass(), inheritanceStatePerClass, mappings);
/* 2011:2520 */       if (superclass != null) {
/* 2012:2521 */         superclass.setDeclaredIdentifierProperty(prop);
/* 2013:     */       } else {
/* 2014:2525 */         rootClass.setDeclaredIdentifierProperty(prop);
/* 2015:     */       }
/* 2016:     */     }
/* 2017:     */   }
/* 2018:     */   
/* 2019:     */   private static PropertyData getUniqueIdPropertyFromBaseClass(PropertyData inferredData, PropertyData baseInferredData, AccessType propertyAccessor, Mappings mappings)
/* 2020:     */   {
/* 2021:2535 */     List<PropertyData> baseClassElements = new ArrayList();
/* 2022:2536 */     XClass baseReturnedClassOrElement = baseInferredData.getClassOrElement();
/* 2023:2537 */     PropertyContainer propContainer = new PropertyContainer(baseReturnedClassOrElement, inferredData.getPropertyClass());
/* 2024:     */     
/* 2025:     */ 
/* 2026:2540 */     addElementsOfClass(baseClassElements, propertyAccessor, propContainer, mappings);
/* 2027:     */     
/* 2028:2542 */     return (PropertyData)baseClassElements.get(0);
/* 2029:     */   }
/* 2030:     */   
/* 2031:     */   private static void setupComponentTuplizer(XProperty property, Component component)
/* 2032:     */   {
/* 2033:2546 */     if (property == null) {
/* 2034:2547 */       return;
/* 2035:     */     }
/* 2036:2549 */     if (property.isAnnotationPresent(Tuplizers.class)) {
/* 2037:2550 */       for (Tuplizer tuplizer : ((Tuplizers)property.getAnnotation(Tuplizers.class)).value())
/* 2038:     */       {
/* 2039:2551 */         EntityMode mode = EntityMode.parse(tuplizer.entityMode());
/* 2040:     */         
/* 2041:2553 */         component.addTuplizer(mode, tuplizer.impl().getName());
/* 2042:     */       }
/* 2043:     */     }
/* 2044:2556 */     if (property.isAnnotationPresent(Tuplizer.class))
/* 2045:     */     {
/* 2046:2557 */       Tuplizer tuplizer = (Tuplizer)property.getAnnotation(Tuplizer.class);
/* 2047:2558 */       EntityMode mode = EntityMode.parse(tuplizer.entityMode());
/* 2048:     */       
/* 2049:2560 */       component.addTuplizer(mode, tuplizer.impl().getName());
/* 2050:     */     }
/* 2051:     */   }
/* 2052:     */   
/* 2053:     */   private static void bindManyToOne(String cascadeStrategy, Ejb3JoinColumn[] columns, boolean optional, boolean ignoreNotFound, boolean cascadeOnDelete, XClass targetEntity, PropertyHolder propertyHolder, PropertyData inferredData, boolean unique, boolean isIdentifierMapper, boolean inSecondPass, PropertyBinder propertyBinder, Mappings mappings)
/* 2054:     */   {
/* 2055:2579 */     org.hibernate.mapping.ManyToOne value = new org.hibernate.mapping.ManyToOne(mappings, columns[0].getTable());
/* 2056:2581 */     if (unique) {
/* 2057:2582 */       value.markAsLogicalOneToOne();
/* 2058:     */     }
/* 2059:2584 */     value.setReferencedEntityName(ToOneBinder.getReferenceEntityName(inferredData, targetEntity, mappings));
/* 2060:2585 */     XProperty property = inferredData.getProperty();
/* 2061:2586 */     defineFetchingStrategy(value, property);
/* 2062:     */     
/* 2063:2588 */     value.setIgnoreNotFound(ignoreNotFound);
/* 2064:2589 */     value.setCascadeDeleteEnabled(cascadeOnDelete);
/* 2065:2591 */     if (!optional) {
/* 2066:2592 */       for (Ejb3JoinColumn column : columns) {
/* 2067:2593 */         column.setNullable(false);
/* 2068:     */       }
/* 2069:     */     }
/* 2070:2596 */     if (property.isAnnotationPresent(MapsId.class)) {
/* 2071:2598 */       for (Ejb3JoinColumn column : columns)
/* 2072:     */       {
/* 2073:2599 */         column.setInsertable(false);
/* 2074:2600 */         column.setUpdatable(false);
/* 2075:     */       }
/* 2076:     */     }
/* 2077:2605 */     boolean hasSpecjManyToOne = false;
/* 2078:     */     String columnName;
/* 2079:2606 */     if (mappings.isSpecjProprietarySyntaxEnabled())
/* 2080:     */     {
/* 2081:2607 */       columnName = "";
/* 2082:2608 */       for (XProperty prop : inferredData.getDeclaringClass().getDeclaredProperties(AccessType.FIELD.getType()))
/* 2083:     */       {
/* 2084:2610 */         if ((prop.isAnnotationPresent(Id.class)) && (prop.isAnnotationPresent(javax.persistence.Column.class))) {
/* 2085:2611 */           columnName = ((javax.persistence.Column)prop.getAnnotation(javax.persistence.Column.class)).name();
/* 2086:     */         }
/* 2087:2614 */         JoinColumn joinColumn = (JoinColumn)property.getAnnotation(JoinColumn.class);
/* 2088:2615 */         if ((property.isAnnotationPresent(javax.persistence.ManyToOne.class)) && (joinColumn != null) && (!BinderHelper.isEmptyAnnotationValue(joinColumn.name())) && (joinColumn.name().equals(columnName)) && (!property.isAnnotationPresent(MapsId.class)))
/* 2089:     */         {
/* 2090:2619 */           hasSpecjManyToOne = true;
/* 2091:2620 */           for (Ejb3JoinColumn column : columns)
/* 2092:     */           {
/* 2093:2621 */             column.setInsertable(false);
/* 2094:2622 */             column.setUpdatable(false);
/* 2095:     */           }
/* 2096:     */         }
/* 2097:     */       }
/* 2098:     */     }
/* 2099:2628 */     value.setTypeName(inferredData.getClassOrElementName());
/* 2100:2629 */     String propertyName = inferredData.getPropertyName();
/* 2101:2630 */     value.setTypeUsingReflection(propertyHolder.getClassName(), propertyName);
/* 2102:     */     
/* 2103:2632 */     ForeignKey fk = (ForeignKey)property.getAnnotation(ForeignKey.class);
/* 2104:2633 */     String fkName = fk != null ? fk.name() : "";
/* 2105:2636 */     if (!BinderHelper.isEmptyAnnotationValue(fkName)) {
/* 2106:2637 */       value.setForeignKeyName(fkName);
/* 2107:     */     }
/* 2108:2640 */     String path = propertyHolder.getPath() + "." + propertyName;
/* 2109:2641 */     FkSecondPass secondPass = new ToOneFkSecondPass(value, columns, (!optional) && (unique), propertyHolder.getEntityOwnerClassName(), path, mappings);
/* 2110:2647 */     if (inSecondPass) {
/* 2111:2648 */       secondPass.doSecondPass(mappings.getClasses());
/* 2112:     */     } else {
/* 2113:2651 */       mappings.addSecondPass(secondPass);
/* 2114:     */     }
/* 2115:2655 */     Ejb3Column.checkPropertyConsistency(columns, propertyHolder.getEntityName() + propertyName);
/* 2116:     */     
/* 2117:2657 */     propertyBinder.setName(propertyName);
/* 2118:2658 */     propertyBinder.setValue(value);
/* 2119:2660 */     if (isIdentifierMapper)
/* 2120:     */     {
/* 2121:2661 */       propertyBinder.setInsertable(false);
/* 2122:2662 */       propertyBinder.setUpdatable(false);
/* 2123:     */     }
/* 2124:2664 */     else if (hasSpecjManyToOne)
/* 2125:     */     {
/* 2126:2665 */       propertyBinder.setInsertable(false);
/* 2127:2666 */       propertyBinder.setUpdatable(false);
/* 2128:     */     }
/* 2129:     */     else
/* 2130:     */     {
/* 2131:2669 */       propertyBinder.setInsertable(columns[0].isInsertable());
/* 2132:2670 */       propertyBinder.setUpdatable(columns[0].isUpdatable());
/* 2133:     */     }
/* 2134:2672 */     propertyBinder.setColumns(columns);
/* 2135:2673 */     propertyBinder.setAccessType(inferredData.getDefaultAccess());
/* 2136:2674 */     propertyBinder.setCascade(cascadeStrategy);
/* 2137:2675 */     propertyBinder.setProperty(property);
/* 2138:2676 */     propertyBinder.setXToMany(true);
/* 2139:2677 */     propertyBinder.makePropertyAndBind();
/* 2140:     */   }
/* 2141:     */   
/* 2142:     */   protected static void defineFetchingStrategy(ToOne toOne, XProperty property)
/* 2143:     */   {
/* 2144:2681 */     LazyToOne lazy = (LazyToOne)property.getAnnotation(LazyToOne.class);
/* 2145:2682 */     Fetch fetch = (Fetch)property.getAnnotation(Fetch.class);
/* 2146:2683 */     javax.persistence.ManyToOne manyToOne = (javax.persistence.ManyToOne)property.getAnnotation(javax.persistence.ManyToOne.class);
/* 2147:2684 */     OneToOne oneToOne = (OneToOne)property.getAnnotation(OneToOne.class);
/* 2148:     */     FetchType fetchType;
/* 2149:2686 */     if (manyToOne != null)
/* 2150:     */     {
/* 2151:2687 */       fetchType = manyToOne.fetch();
/* 2152:     */     }
/* 2153:     */     else
/* 2154:     */     {
/* 2155:     */       FetchType fetchType;
/* 2156:2689 */       if (oneToOne != null) {
/* 2157:2690 */         fetchType = oneToOne.fetch();
/* 2158:     */       } else {
/* 2159:2693 */         throw new AssertionFailure("Define fetch strategy on a property not annotated with @OneToMany nor @OneToOne");
/* 2160:     */       }
/* 2161:     */     }
/* 2162:     */     FetchType fetchType;
/* 2163:2697 */     if (lazy != null)
/* 2164:     */     {
/* 2165:2698 */       toOne.setLazy(lazy.value() != LazyToOneOption.FALSE);
/* 2166:2699 */       toOne.setUnwrapProxy(lazy.value() == LazyToOneOption.NO_PROXY);
/* 2167:     */     }
/* 2168:     */     else
/* 2169:     */     {
/* 2170:2702 */       toOne.setLazy(fetchType == FetchType.LAZY);
/* 2171:2703 */       toOne.setUnwrapProxy(false);
/* 2172:     */     }
/* 2173:2705 */     if (fetch != null)
/* 2174:     */     {
/* 2175:2706 */       if (fetch.value() == org.hibernate.annotations.FetchMode.JOIN)
/* 2176:     */       {
/* 2177:2707 */         toOne.setFetchMode(org.hibernate.FetchMode.JOIN);
/* 2178:2708 */         toOne.setLazy(false);
/* 2179:2709 */         toOne.setUnwrapProxy(false);
/* 2180:     */       }
/* 2181:2711 */       else if (fetch.value() == org.hibernate.annotations.FetchMode.SELECT)
/* 2182:     */       {
/* 2183:2712 */         toOne.setFetchMode(org.hibernate.FetchMode.SELECT);
/* 2184:     */       }
/* 2185:     */       else
/* 2186:     */       {
/* 2187:2714 */         if (fetch.value() == org.hibernate.annotations.FetchMode.SUBSELECT) {
/* 2188:2715 */           throw new AnnotationException("Use of FetchMode.SUBSELECT not allowed on ToOne associations");
/* 2189:     */         }
/* 2190:2718 */         throw new AssertionFailure("Unknown FetchMode: " + fetch.value());
/* 2191:     */       }
/* 2192:     */     }
/* 2193:     */     else {
/* 2194:2722 */       toOne.setFetchMode(getFetchMode(fetchType));
/* 2195:     */     }
/* 2196:     */   }
/* 2197:     */   
/* 2198:     */   private static void bindOneToOne(String cascadeStrategy, Ejb3JoinColumn[] joinColumns, boolean optional, org.hibernate.FetchMode fetchMode, boolean ignoreNotFound, boolean cascadeOnDelete, XClass targetEntity, PropertyHolder propertyHolder, PropertyData inferredData, String mappedBy, boolean trueOneToOne, boolean isIdentifierMapper, boolean inSecondPass, PropertyBinder propertyBinder, Mappings mappings)
/* 2199:     */   {
/* 2200:2742 */     String propertyName = inferredData.getPropertyName();
/* 2201:2743 */     LOG.tracev("Fetching {0} with {1}", propertyName, fetchMode);
/* 2202:2744 */     boolean mapToPK = true;
/* 2203:2745 */     if (!trueOneToOne)
/* 2204:     */     {
/* 2205:2747 */       KeyValue identifier = propertyHolder.getIdentifier();
/* 2206:2748 */       if (identifier == null)
/* 2207:     */       {
/* 2208:2751 */         mapToPK = false;
/* 2209:     */       }
/* 2210:     */       else
/* 2211:     */       {
/* 2212:2754 */         Iterator idColumns = identifier.getColumnIterator();
/* 2213:2755 */         List<String> idColumnNames = new ArrayList();
/* 2214:2757 */         if (identifier.getColumnSpan() != joinColumns.length)
/* 2215:     */         {
/* 2216:2758 */           mapToPK = false;
/* 2217:     */         }
/* 2218:     */         else
/* 2219:     */         {
/* 2220:2761 */           while (idColumns.hasNext())
/* 2221:     */           {
/* 2222:2762 */             org.hibernate.mapping.Column currentColumn = (org.hibernate.mapping.Column)idColumns.next();
/* 2223:2763 */             idColumnNames.add(currentColumn.getName());
/* 2224:     */           }
/* 2225:2765 */           for (Ejb3JoinColumn col : joinColumns) {
/* 2226:2766 */             if (!idColumnNames.contains(col.getMappingColumn().getName()))
/* 2227:     */             {
/* 2228:2767 */               mapToPK = false;
/* 2229:2768 */               break;
/* 2230:     */             }
/* 2231:     */           }
/* 2232:     */         }
/* 2233:     */       }
/* 2234:     */     }
/* 2235:2774 */     if ((trueOneToOne) || (mapToPK) || (!BinderHelper.isEmptyAnnotationValue(mappedBy)))
/* 2236:     */     {
/* 2237:2777 */       OneToOneSecondPass secondPass = new OneToOneSecondPass(mappedBy, propertyHolder.getEntityName(), propertyName, propertyHolder, inferredData, targetEntity, ignoreNotFound, cascadeOnDelete, optional, cascadeStrategy, joinColumns, mappings);
/* 2238:2784 */       if (inSecondPass) {
/* 2239:2785 */         secondPass.doSecondPass(mappings.getClasses());
/* 2240:     */       } else {
/* 2241:2788 */         mappings.addSecondPass(secondPass, BinderHelper.isEmptyAnnotationValue(mappedBy));
/* 2242:     */       }
/* 2243:     */     }
/* 2244:     */     else
/* 2245:     */     {
/* 2246:2795 */       bindManyToOne(cascadeStrategy, joinColumns, optional, ignoreNotFound, cascadeOnDelete, targetEntity, propertyHolder, inferredData, true, isIdentifierMapper, inSecondPass, propertyBinder, mappings);
/* 2247:     */     }
/* 2248:     */   }
/* 2249:     */   
/* 2250:     */   private static void bindAny(String cascadeStrategy, Ejb3JoinColumn[] columns, boolean cascadeOnDelete, Nullability nullability, PropertyHolder propertyHolder, PropertyData inferredData, EntityBinder entityBinder, boolean isIdentifierMapper, Mappings mappings)
/* 2251:     */   {
/* 2252:2814 */     org.hibernate.annotations.Any anyAnn = (org.hibernate.annotations.Any)inferredData.getProperty().getAnnotation(org.hibernate.annotations.Any.class);
/* 2253:2816 */     if (anyAnn == null) {
/* 2254:2817 */       throw new AssertionFailure("Missing @Any annotation: " + BinderHelper.getPath(propertyHolder, inferredData));
/* 2255:     */     }
/* 2256:2822 */     org.hibernate.mapping.Any value = BinderHelper.buildAnyValue(anyAnn.metaDef(), columns, anyAnn.metaColumn(), inferredData, cascadeOnDelete, nullability, propertyHolder, entityBinder, anyAnn.optional(), mappings);
/* 2257:     */     
/* 2258:     */ 
/* 2259:     */ 
/* 2260:     */ 
/* 2261:2827 */     PropertyBinder binder = new PropertyBinder();
/* 2262:2828 */     binder.setName(inferredData.getPropertyName());
/* 2263:2829 */     binder.setValue(value);
/* 2264:     */     
/* 2265:2831 */     binder.setLazy(anyAnn.fetch() == FetchType.LAZY);
/* 2266:2833 */     if (isIdentifierMapper)
/* 2267:     */     {
/* 2268:2834 */       binder.setInsertable(false);
/* 2269:2835 */       binder.setUpdatable(false);
/* 2270:     */     }
/* 2271:     */     else
/* 2272:     */     {
/* 2273:2838 */       binder.setInsertable(columns[0].isInsertable());
/* 2274:2839 */       binder.setUpdatable(columns[0].isUpdatable());
/* 2275:     */     }
/* 2276:2841 */     binder.setAccessType(inferredData.getDefaultAccess());
/* 2277:2842 */     binder.setCascade(cascadeStrategy);
/* 2278:2843 */     Property prop = binder.makeProperty();
/* 2279:     */     
/* 2280:2845 */     propertyHolder.addProperty(prop, columns, inferredData.getDeclaringClass());
/* 2281:     */   }
/* 2282:     */   
/* 2283:     */   private static String generatorType(GenerationType generatorEnum, Mappings mappings)
/* 2284:     */   {
/* 2285:2849 */     boolean useNewGeneratorMappings = mappings.useNewGeneratorMappings();
/* 2286:2850 */     switch (1.$SwitchMap$javax$persistence$GenerationType[generatorEnum.ordinal()])
/* 2287:     */     {
/* 2288:     */     case 1: 
/* 2289:2852 */       return "identity";
/* 2290:     */     case 2: 
/* 2291:2854 */       return useNewGeneratorMappings ? SequenceStyleGenerator.class.getName() : "native";
/* 2292:     */     case 3: 
/* 2293:2858 */       return useNewGeneratorMappings ? org.hibernate.id.enhanced.TableGenerator.class.getName() : MultipleHiLoPerTableGenerator.class.getName();
/* 2294:     */     case 4: 
/* 2295:2862 */       return useNewGeneratorMappings ? SequenceStyleGenerator.class.getName() : "seqhilo";
/* 2296:     */     }
/* 2297:2866 */     throw new AssertionFailure("Unknown GeneratorType: " + generatorEnum);
/* 2298:     */   }
/* 2299:     */   
/* 2300:     */   private static EnumSet<org.hibernate.annotations.CascadeType> convertToHibernateCascadeType(javax.persistence.CascadeType[] ejbCascades)
/* 2301:     */   {
/* 2302:2870 */     EnumSet<org.hibernate.annotations.CascadeType> hibernateCascadeSet = EnumSet.noneOf(org.hibernate.annotations.CascadeType.class);
/* 2303:2871 */     if ((ejbCascades != null) && (ejbCascades.length > 0)) {
/* 2304:2872 */       for (javax.persistence.CascadeType cascade : ejbCascades) {
/* 2305:2873 */         switch (1.$SwitchMap$javax$persistence$CascadeType[cascade.ordinal()])
/* 2306:     */         {
/* 2307:     */         case 1: 
/* 2308:2875 */           hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.ALL);
/* 2309:2876 */           break;
/* 2310:     */         case 2: 
/* 2311:2878 */           hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.PERSIST);
/* 2312:2879 */           break;
/* 2313:     */         case 3: 
/* 2314:2881 */           hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.MERGE);
/* 2315:2882 */           break;
/* 2316:     */         case 4: 
/* 2317:2884 */           hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.REMOVE);
/* 2318:2885 */           break;
/* 2319:     */         case 5: 
/* 2320:2887 */           hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.REFRESH);
/* 2321:2888 */           break;
/* 2322:     */         case 6: 
/* 2323:2890 */           hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.DETACH);
/* 2324:     */         }
/* 2325:     */       }
/* 2326:     */     }
/* 2327:2896 */     return hibernateCascadeSet;
/* 2328:     */   }
/* 2329:     */   
/* 2330:     */   private static String getCascadeStrategy(javax.persistence.CascadeType[] ejbCascades, Cascade hibernateCascadeAnnotation, boolean orphanRemoval, boolean forcePersist)
/* 2331:     */   {
/* 2332:2904 */     EnumSet<org.hibernate.annotations.CascadeType> hibernateCascadeSet = convertToHibernateCascadeType(ejbCascades);
/* 2333:2905 */     org.hibernate.annotations.CascadeType[] hibernateCascades = hibernateCascadeAnnotation == null ? null : hibernateCascadeAnnotation.value();
/* 2334:2909 */     if ((hibernateCascades != null) && (hibernateCascades.length > 0)) {
/* 2335:2910 */       hibernateCascadeSet.addAll(Arrays.asList(hibernateCascades));
/* 2336:     */     }
/* 2337:2913 */     if (orphanRemoval)
/* 2338:     */     {
/* 2339:2914 */       hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.DELETE_ORPHAN);
/* 2340:2915 */       hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.REMOVE);
/* 2341:     */     }
/* 2342:2917 */     if (forcePersist) {
/* 2343:2918 */       hibernateCascadeSet.add(org.hibernate.annotations.CascadeType.PERSIST);
/* 2344:     */     }
/* 2345:2921 */     StringBuilder cascade = new StringBuilder();
/* 2346:2922 */     for (org.hibernate.annotations.CascadeType aHibernateCascadeSet : hibernateCascadeSet) {
/* 2347:2923 */       switch (1.$SwitchMap$org$hibernate$annotations$CascadeType[aHibernateCascadeSet.ordinal()])
/* 2348:     */       {
/* 2349:     */       case 1: 
/* 2350:2925 */         cascade.append(",").append("all");
/* 2351:2926 */         break;
/* 2352:     */       case 2: 
/* 2353:2928 */         cascade.append(",").append("save-update");
/* 2354:2929 */         break;
/* 2355:     */       case 3: 
/* 2356:2931 */         cascade.append(",").append("persist");
/* 2357:2932 */         break;
/* 2358:     */       case 4: 
/* 2359:2934 */         cascade.append(",").append("merge");
/* 2360:2935 */         break;
/* 2361:     */       case 5: 
/* 2362:2937 */         cascade.append(",").append("lock");
/* 2363:2938 */         break;
/* 2364:     */       case 6: 
/* 2365:2940 */         cascade.append(",").append("refresh");
/* 2366:2941 */         break;
/* 2367:     */       case 7: 
/* 2368:2943 */         cascade.append(",").append("replicate");
/* 2369:2944 */         break;
/* 2370:     */       case 8: 
/* 2371:     */       case 9: 
/* 2372:2947 */         cascade.append(",").append("evict");
/* 2373:2948 */         break;
/* 2374:     */       case 10: 
/* 2375:2950 */         cascade.append(",").append("delete");
/* 2376:2951 */         break;
/* 2377:     */       case 11: 
/* 2378:2953 */         cascade.append(",").append("delete-orphan");
/* 2379:2954 */         break;
/* 2380:     */       case 12: 
/* 2381:2956 */         cascade.append(",").append("delete");
/* 2382:     */       }
/* 2383:     */     }
/* 2384:2960 */     return cascade.length() > 0 ? cascade.substring(1) : "none";
/* 2385:     */   }
/* 2386:     */   
/* 2387:     */   public static org.hibernate.FetchMode getFetchMode(FetchType fetch)
/* 2388:     */   {
/* 2389:2966 */     if (fetch == FetchType.EAGER) {
/* 2390:2967 */       return org.hibernate.FetchMode.JOIN;
/* 2391:     */     }
/* 2392:2970 */     return org.hibernate.FetchMode.SELECT;
/* 2393:     */   }
/* 2394:     */   
/* 2395:     */   private static HashMap<String, IdGenerator> buildLocalGenerators(XAnnotatedElement annElt, Mappings mappings)
/* 2396:     */   {
/* 2397:2975 */     HashMap<String, IdGenerator> generators = new HashMap();
/* 2398:2976 */     javax.persistence.TableGenerator tabGen = (javax.persistence.TableGenerator)annElt.getAnnotation(javax.persistence.TableGenerator.class);
/* 2399:2977 */     SequenceGenerator seqGen = (SequenceGenerator)annElt.getAnnotation(SequenceGenerator.class);
/* 2400:2978 */     GenericGenerator genGen = (GenericGenerator)annElt.getAnnotation(GenericGenerator.class);
/* 2401:2979 */     if (tabGen != null)
/* 2402:     */     {
/* 2403:2980 */       IdGenerator idGen = buildIdGenerator(tabGen, mappings);
/* 2404:2981 */       generators.put(idGen.getName(), idGen);
/* 2405:     */     }
/* 2406:2983 */     if (seqGen != null)
/* 2407:     */     {
/* 2408:2984 */       IdGenerator idGen = buildIdGenerator(seqGen, mappings);
/* 2409:2985 */       generators.put(idGen.getName(), idGen);
/* 2410:     */     }
/* 2411:2987 */     if (genGen != null)
/* 2412:     */     {
/* 2413:2988 */       IdGenerator idGen = buildIdGenerator(genGen, mappings);
/* 2414:2989 */       generators.put(idGen.getName(), idGen);
/* 2415:     */     }
/* 2416:2991 */     return generators;
/* 2417:     */   }
/* 2418:     */   
/* 2419:     */   public static boolean isDefault(XClass clazz, Mappings mappings)
/* 2420:     */   {
/* 2421:2995 */     return mappings.getReflectionManager().equals(clazz, Void.TYPE);
/* 2422:     */   }
/* 2423:     */   
/* 2424:     */   public static Map<XClass, InheritanceState> buildInheritanceStates(List<XClass> orderedClasses, Mappings mappings)
/* 2425:     */   {
/* 2426:3009 */     ReflectionManager reflectionManager = mappings.getReflectionManager();
/* 2427:3010 */     Map<XClass, InheritanceState> inheritanceStatePerClass = new HashMap(orderedClasses.size());
/* 2428:3013 */     for (XClass clazz : orderedClasses)
/* 2429:     */     {
/* 2430:3014 */       InheritanceState superclassState = InheritanceState.getSuperclassInheritanceState(clazz, inheritanceStatePerClass);
/* 2431:     */       
/* 2432:     */ 
/* 2433:3017 */       InheritanceState state = new InheritanceState(clazz, inheritanceStatePerClass, mappings);
/* 2434:3018 */       if (superclassState != null)
/* 2435:     */       {
/* 2436:3021 */         superclassState.setHasSiblings(true);
/* 2437:3022 */         InheritanceState superEntityState = InheritanceState.getInheritanceStateOfSuperEntity(clazz, inheritanceStatePerClass);
/* 2438:     */         
/* 2439:     */ 
/* 2440:3025 */         state.setHasParents(superEntityState != null);
/* 2441:3026 */         boolean nonDefault = (state.getType() != null) && (!InheritanceType.SINGLE_TABLE.equals(state.getType()));
/* 2442:3028 */         if (superclassState.getType() != null)
/* 2443:     */         {
/* 2444:3029 */           boolean mixingStrategy = (state.getType() != null) && (!state.getType().equals(superclassState.getType()));
/* 2445:3031 */           if ((nonDefault) && (mixingStrategy)) {
/* 2446:3032 */             LOG.invalidSubStrategy(clazz.getName());
/* 2447:     */           }
/* 2448:3034 */           state.setType(superclassState.getType());
/* 2449:     */         }
/* 2450:     */       }
/* 2451:3037 */       inheritanceStatePerClass.put(clazz, state);
/* 2452:     */     }
/* 2453:3039 */     return inheritanceStatePerClass;
/* 2454:     */   }
/* 2455:     */   
/* 2456:     */   private static boolean hasAnnotationsOnIdClass(XClass idClass)
/* 2457:     */   {
/* 2458:3046 */     List<XProperty> properties = idClass.getDeclaredProperties("field");
/* 2459:3047 */     for (XProperty property : properties) {
/* 2460:3048 */       if ((property.isAnnotationPresent(javax.persistence.Column.class)) || (property.isAnnotationPresent(OneToMany.class)) || (property.isAnnotationPresent(javax.persistence.ManyToOne.class)) || (property.isAnnotationPresent(Id.class)) || (property.isAnnotationPresent(GeneratedValue.class)) || (property.isAnnotationPresent(OneToOne.class)) || (property.isAnnotationPresent(ManyToMany.class))) {
/* 2461:3053 */         return true;
/* 2462:     */       }
/* 2463:     */     }
/* 2464:3056 */     List<XMethod> methods = idClass.getDeclaredMethods();
/* 2465:3057 */     for (XMethod method : methods) {
/* 2466:3058 */       if ((method.isAnnotationPresent(javax.persistence.Column.class)) || (method.isAnnotationPresent(OneToMany.class)) || (method.isAnnotationPresent(javax.persistence.ManyToOne.class)) || (method.isAnnotationPresent(Id.class)) || (method.isAnnotationPresent(GeneratedValue.class)) || (method.isAnnotationPresent(OneToOne.class)) || (method.isAnnotationPresent(ManyToMany.class))) {
/* 2467:3063 */         return true;
/* 2468:     */       }
/* 2469:     */     }
/* 2470:3066 */     return false;
/* 2471:     */   }
/* 2472:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.AnnotationBinder
 * JD-Core Version:    0.7.0.1
 */