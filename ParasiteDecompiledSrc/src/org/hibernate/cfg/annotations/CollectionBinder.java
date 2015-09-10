/*    1:     */ package org.hibernate.cfg.annotations;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Comparator;
/*    5:     */ import java.util.HashMap;
/*    6:     */ import java.util.Iterator;
/*    7:     */ import java.util.List;
/*    8:     */ import java.util.Map;
/*    9:     */ import java.util.Set;
/*   10:     */ import java.util.SortedMap;
/*   11:     */ import java.util.SortedSet;
/*   12:     */ import java.util.StringTokenizer;
/*   13:     */ import javax.persistence.AttributeOverride;
/*   14:     */ import javax.persistence.AttributeOverrides;
/*   15:     */ import javax.persistence.ElementCollection;
/*   16:     */ import javax.persistence.Embeddable;
/*   17:     */ import javax.persistence.FetchType;
/*   18:     */ import javax.persistence.JoinColumn;
/*   19:     */ import javax.persistence.JoinColumns;
/*   20:     */ import javax.persistence.JoinTable;
/*   21:     */ import javax.persistence.ManyToMany;
/*   22:     */ import javax.persistence.MapKey;
/*   23:     */ import javax.persistence.MapKeyColumn;
/*   24:     */ import org.hibernate.AnnotationException;
/*   25:     */ import org.hibernate.MappingException;
/*   26:     */ import org.hibernate.annotations.BatchSize;
/*   27:     */ import org.hibernate.annotations.Cache;
/*   28:     */ import org.hibernate.annotations.CollectionId;
/*   29:     */ import org.hibernate.annotations.Fetch;
/*   30:     */ import org.hibernate.annotations.Filter;
/*   31:     */ import org.hibernate.annotations.FilterJoinTable;
/*   32:     */ import org.hibernate.annotations.FilterJoinTables;
/*   33:     */ import org.hibernate.annotations.Filters;
/*   34:     */ import org.hibernate.annotations.ForeignKey;
/*   35:     */ import org.hibernate.annotations.Immutable;
/*   36:     */ import org.hibernate.annotations.LazyCollection;
/*   37:     */ import org.hibernate.annotations.LazyCollectionOption;
/*   38:     */ import org.hibernate.annotations.Loader;
/*   39:     */ import org.hibernate.annotations.ManyToAny;
/*   40:     */ import org.hibernate.annotations.OptimisticLock;
/*   41:     */ import org.hibernate.annotations.Persister;
/*   42:     */ import org.hibernate.annotations.ResultCheckStyle;
/*   43:     */ import org.hibernate.annotations.SQLDelete;
/*   44:     */ import org.hibernate.annotations.SQLDeleteAll;
/*   45:     */ import org.hibernate.annotations.SQLInsert;
/*   46:     */ import org.hibernate.annotations.SQLUpdate;
/*   47:     */ import org.hibernate.annotations.Sort;
/*   48:     */ import org.hibernate.annotations.SortType;
/*   49:     */ import org.hibernate.annotations.Where;
/*   50:     */ import org.hibernate.annotations.WhereJoinTable;
/*   51:     */ import org.hibernate.annotations.common.AssertionFailure;
/*   52:     */ import org.hibernate.annotations.common.reflection.XClass;
/*   53:     */ import org.hibernate.annotations.common.reflection.XProperty;
/*   54:     */ import org.hibernate.cfg.AccessType;
/*   55:     */ import org.hibernate.cfg.AnnotatedClassType;
/*   56:     */ import org.hibernate.cfg.AnnotationBinder;
/*   57:     */ import org.hibernate.cfg.BinderHelper;
/*   58:     */ import org.hibernate.cfg.CollectionSecondPass;
/*   59:     */ import org.hibernate.cfg.Ejb3Column;
/*   60:     */ import org.hibernate.cfg.Ejb3JoinColumn;
/*   61:     */ import org.hibernate.cfg.IndexColumn;
/*   62:     */ import org.hibernate.cfg.InheritanceState;
/*   63:     */ import org.hibernate.cfg.Mappings;
/*   64:     */ import org.hibernate.cfg.PropertyData;
/*   65:     */ import org.hibernate.cfg.PropertyHolder;
/*   66:     */ import org.hibernate.cfg.PropertyHolderBuilder;
/*   67:     */ import org.hibernate.cfg.PropertyInferredData;
/*   68:     */ import org.hibernate.cfg.PropertyPreloadedData;
/*   69:     */ import org.hibernate.cfg.SecondPass;
/*   70:     */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*   71:     */ import org.hibernate.engine.spi.FilterDefinition;
/*   72:     */ import org.hibernate.internal.CoreMessageLogger;
/*   73:     */ import org.hibernate.internal.util.StringHelper;
/*   74:     */ import org.hibernate.mapping.Any;
/*   75:     */ import org.hibernate.mapping.Backref;
/*   76:     */ import org.hibernate.mapping.Column;
/*   77:     */ import org.hibernate.mapping.Component;
/*   78:     */ import org.hibernate.mapping.DependantValue;
/*   79:     */ import org.hibernate.mapping.IdGenerator;
/*   80:     */ import org.hibernate.mapping.Join;
/*   81:     */ import org.hibernate.mapping.KeyValue;
/*   82:     */ import org.hibernate.mapping.ManyToOne;
/*   83:     */ import org.hibernate.mapping.PersistentClass;
/*   84:     */ import org.hibernate.mapping.Property;
/*   85:     */ import org.hibernate.mapping.Selectable;
/*   86:     */ import org.hibernate.mapping.SimpleValue;
/*   87:     */ import org.hibernate.mapping.SingleTableSubclass;
/*   88:     */ import org.hibernate.mapping.Table;
/*   89:     */ import org.hibernate.mapping.Value;
/*   90:     */ import org.jboss.logging.Logger;
/*   91:     */ 
/*   92:     */ public abstract class CollectionBinder
/*   93:     */ {
/*   94: 123 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, CollectionBinder.class.getName());
/*   95:     */   protected org.hibernate.mapping.Collection collection;
/*   96:     */   protected String propertyName;
/*   97:     */   PropertyHolder propertyHolder;
/*   98:     */   int batchSize;
/*   99:     */   private String mappedBy;
/*  100:     */   private XClass collectionType;
/*  101:     */   private XClass targetEntity;
/*  102:     */   private Mappings mappings;
/*  103:     */   private Ejb3JoinColumn[] inverseJoinColumns;
/*  104:     */   private String cascadeStrategy;
/*  105:     */   String cacheConcurrencyStrategy;
/*  106:     */   String cacheRegionName;
/*  107:     */   private boolean oneToMany;
/*  108:     */   protected IndexColumn indexColumn;
/*  109:     */   private String orderBy;
/*  110:     */   protected String hqlOrderBy;
/*  111:     */   private boolean isSorted;
/*  112:     */   private Class comparator;
/*  113:     */   private boolean hasToBeSorted;
/*  114:     */   protected boolean cascadeDeleteEnabled;
/*  115:     */   protected String mapKeyPropertyName;
/*  116: 146 */   private boolean insertable = true;
/*  117: 147 */   private boolean updatable = true;
/*  118:     */   private Ejb3JoinColumn[] fkJoinColumns;
/*  119:     */   private boolean isExplicitAssociationTable;
/*  120:     */   private Ejb3Column[] elementColumns;
/*  121:     */   private boolean isEmbedded;
/*  122:     */   private XProperty property;
/*  123:     */   private boolean ignoreNotFound;
/*  124:     */   private TableBinder tableBinder;
/*  125:     */   private Ejb3Column[] mapKeyColumns;
/*  126:     */   private Ejb3JoinColumn[] mapKeyManyToManyColumns;
/*  127:     */   protected HashMap<String, IdGenerator> localGenerators;
/*  128:     */   protected Map<XClass, InheritanceState> inheritanceStatePerClass;
/*  129:     */   private XClass declaringClass;
/*  130:     */   private boolean declaringClassSet;
/*  131:     */   private AccessType accessType;
/*  132:     */   private boolean hibernateExtensionMapping;
/*  133:     */   private Ejb3JoinColumn[] joinColumns;
/*  134:     */   
/*  135:     */   protected Mappings getMappings()
/*  136:     */   {
/*  137: 165 */     return this.mappings;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public boolean isMap()
/*  141:     */   {
/*  142: 169 */     return false;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public void setIsHibernateExtensionMapping(boolean hibernateExtensionMapping)
/*  146:     */   {
/*  147: 173 */     this.hibernateExtensionMapping = hibernateExtensionMapping;
/*  148:     */   }
/*  149:     */   
/*  150:     */   protected boolean isHibernateExtensionMapping()
/*  151:     */   {
/*  152: 177 */     return this.hibernateExtensionMapping;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void setUpdatable(boolean updatable)
/*  156:     */   {
/*  157: 181 */     this.updatable = updatable;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void setInheritanceStatePerClass(Map<XClass, InheritanceState> inheritanceStatePerClass)
/*  161:     */   {
/*  162: 185 */     this.inheritanceStatePerClass = inheritanceStatePerClass;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public void setInsertable(boolean insertable)
/*  166:     */   {
/*  167: 189 */     this.insertable = insertable;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public void setCascadeStrategy(String cascadeStrategy)
/*  171:     */   {
/*  172: 193 */     this.cascadeStrategy = cascadeStrategy;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public void setAccessType(AccessType accessType)
/*  176:     */   {
/*  177: 197 */     this.accessType = accessType;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public void setInverseJoinColumns(Ejb3JoinColumn[] inverseJoinColumns)
/*  181:     */   {
/*  182: 201 */     this.inverseJoinColumns = inverseJoinColumns;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void setJoinColumns(Ejb3JoinColumn[] joinColumns)
/*  186:     */   {
/*  187: 205 */     this.joinColumns = joinColumns;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public void setPropertyHolder(PropertyHolder propertyHolder)
/*  191:     */   {
/*  192: 211 */     this.propertyHolder = propertyHolder;
/*  193:     */   }
/*  194:     */   
/*  195:     */   public void setBatchSize(BatchSize batchSize)
/*  196:     */   {
/*  197: 215 */     this.batchSize = (batchSize == null ? -1 : batchSize.size());
/*  198:     */   }
/*  199:     */   
/*  200:     */   public void setEjb3OrderBy(javax.persistence.OrderBy orderByAnn)
/*  201:     */   {
/*  202: 219 */     if (orderByAnn != null) {
/*  203: 220 */       this.hqlOrderBy = orderByAnn.value();
/*  204:     */     }
/*  205:     */   }
/*  206:     */   
/*  207:     */   public void setSqlOrderBy(org.hibernate.annotations.OrderBy orderByAnn)
/*  208:     */   {
/*  209: 225 */     if ((orderByAnn != null) && 
/*  210: 226 */       (!BinderHelper.isEmptyAnnotationValue(orderByAnn.clause()))) {
/*  211: 227 */       this.orderBy = orderByAnn.clause();
/*  212:     */     }
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void setSort(Sort sortAnn)
/*  216:     */   {
/*  217: 233 */     if (sortAnn != null)
/*  218:     */     {
/*  219: 234 */       this.isSorted = (!SortType.UNSORTED.equals(sortAnn.type()));
/*  220: 235 */       if ((this.isSorted) && (SortType.COMPARATOR.equals(sortAnn.type()))) {
/*  221: 236 */         this.comparator = sortAnn.comparator();
/*  222:     */       }
/*  223:     */     }
/*  224:     */   }
/*  225:     */   
/*  226:     */   public static CollectionBinder getCollectionBinder(String entityName, XProperty property, boolean isIndexed, boolean isHibernateExtensionMapping)
/*  227:     */   {
/*  228:     */     CollectionBinder result;
/*  229: 250 */     if (property.isArray())
/*  230:     */     {
/*  231:     */       CollectionBinder result;
/*  232: 251 */       if (property.getElementClass().isPrimitive()) {
/*  233: 252 */         result = new PrimitiveArrayBinder();
/*  234:     */       } else {
/*  235: 255 */         result = new ArrayBinder();
/*  236:     */       }
/*  237:     */     }
/*  238:     */     else
/*  239:     */     {
/*  240:     */       CollectionBinder result;
/*  241: 258 */       if (property.isCollection())
/*  242:     */       {
/*  243: 260 */         Class returnedClass = property.getCollectionClass();
/*  244:     */         CollectionBinder result;
/*  245: 261 */         if (Set.class.equals(returnedClass))
/*  246:     */         {
/*  247: 262 */           if (property.isAnnotationPresent(CollectionId.class)) {
/*  248: 263 */             throw new AnnotationException("Set do not support @CollectionId: " + StringHelper.qualify(entityName, property.getName()));
/*  249:     */           }
/*  250: 266 */           result = new SetBinder();
/*  251:     */         }
/*  252:     */         else
/*  253:     */         {
/*  254:     */           CollectionBinder result;
/*  255: 268 */           if (SortedSet.class.equals(returnedClass))
/*  256:     */           {
/*  257: 269 */             if (property.isAnnotationPresent(CollectionId.class)) {
/*  258: 270 */               throw new AnnotationException("Set do not support @CollectionId: " + StringHelper.qualify(entityName, property.getName()));
/*  259:     */             }
/*  260: 273 */             result = new SetBinder(true);
/*  261:     */           }
/*  262:     */           else
/*  263:     */           {
/*  264:     */             CollectionBinder result;
/*  265: 275 */             if (Map.class.equals(returnedClass))
/*  266:     */             {
/*  267: 276 */               if (property.isAnnotationPresent(CollectionId.class)) {
/*  268: 277 */                 throw new AnnotationException("Map do not support @CollectionId: " + StringHelper.qualify(entityName, property.getName()));
/*  269:     */               }
/*  270: 280 */               result = new MapBinder();
/*  271:     */             }
/*  272:     */             else
/*  273:     */             {
/*  274:     */               CollectionBinder result;
/*  275: 282 */               if (SortedMap.class.equals(returnedClass))
/*  276:     */               {
/*  277: 283 */                 if (property.isAnnotationPresent(CollectionId.class)) {
/*  278: 284 */                   throw new AnnotationException("Map do not support @CollectionId: " + StringHelper.qualify(entityName, property.getName()));
/*  279:     */                 }
/*  280: 287 */                 result = new MapBinder(true);
/*  281:     */               }
/*  282:     */               else
/*  283:     */               {
/*  284:     */                 CollectionBinder result;
/*  285: 289 */                 if (java.util.Collection.class.equals(returnedClass))
/*  286:     */                 {
/*  287:     */                   CollectionBinder result;
/*  288: 290 */                   if (property.isAnnotationPresent(CollectionId.class)) {
/*  289: 291 */                     result = new IdBagBinder();
/*  290:     */                   } else {
/*  291: 294 */                     result = new BagBinder();
/*  292:     */                   }
/*  293:     */                 }
/*  294:     */                 else
/*  295:     */                 {
/*  296:     */                   CollectionBinder result;
/*  297: 297 */                   if (List.class.equals(returnedClass))
/*  298:     */                   {
/*  299:     */                     CollectionBinder result;
/*  300: 298 */                     if (isIndexed)
/*  301:     */                     {
/*  302: 299 */                       if (property.isAnnotationPresent(CollectionId.class)) {
/*  303: 300 */                         throw new AnnotationException("List do not support @CollectionId and @OrderColumn (or @IndexColumn) at the same time: " + StringHelper.qualify(entityName, property.getName()));
/*  304:     */                       }
/*  305: 304 */                       result = new ListBinder();
/*  306:     */                     }
/*  307:     */                     else
/*  308:     */                     {
/*  309:     */                       CollectionBinder result;
/*  310: 306 */                       if (property.isAnnotationPresent(CollectionId.class)) {
/*  311: 307 */                         result = new IdBagBinder();
/*  312:     */                       } else {
/*  313: 310 */                         result = new BagBinder();
/*  314:     */                       }
/*  315:     */                     }
/*  316:     */                   }
/*  317:     */                   else
/*  318:     */                   {
/*  319: 314 */                     throw new AnnotationException(returnedClass.getName() + " collection not yet supported: " + StringHelper.qualify(entityName, property.getName()));
/*  320:     */                   }
/*  321:     */                 }
/*  322:     */               }
/*  323:     */             }
/*  324:     */           }
/*  325:     */         }
/*  326:     */       }
/*  327:     */       else
/*  328:     */       {
/*  329: 321 */         throw new AnnotationException("Illegal attempt to map a non collection as a @OneToMany, @ManyToMany or @CollectionOfElements: " + StringHelper.qualify(entityName, property.getName()));
/*  330:     */       }
/*  331:     */     }
/*  332:     */     CollectionBinder result;
/*  333: 326 */     result.setIsHibernateExtensionMapping(isHibernateExtensionMapping);
/*  334: 327 */     return result;
/*  335:     */   }
/*  336:     */   
/*  337:     */   protected CollectionBinder() {}
/*  338:     */   
/*  339:     */   protected CollectionBinder(boolean sorted)
/*  340:     */   {
/*  341: 334 */     this.hasToBeSorted = sorted;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void setMappedBy(String mappedBy)
/*  345:     */   {
/*  346: 338 */     this.mappedBy = mappedBy;
/*  347:     */   }
/*  348:     */   
/*  349:     */   public void setTableBinder(TableBinder tableBinder)
/*  350:     */   {
/*  351: 342 */     this.tableBinder = tableBinder;
/*  352:     */   }
/*  353:     */   
/*  354:     */   public void setCollectionType(XClass collectionType)
/*  355:     */   {
/*  356: 346 */     this.collectionType = collectionType;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public void setTargetEntity(XClass targetEntity)
/*  360:     */   {
/*  361: 350 */     this.targetEntity = targetEntity;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void setMappings(Mappings mappings)
/*  365:     */   {
/*  366: 354 */     this.mappings = mappings;
/*  367:     */   }
/*  368:     */   
/*  369:     */   protected abstract org.hibernate.mapping.Collection createCollection(PersistentClass paramPersistentClass);
/*  370:     */   
/*  371:     */   public org.hibernate.mapping.Collection getCollection()
/*  372:     */   {
/*  373: 360 */     return this.collection;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void setPropertyName(String propertyName)
/*  377:     */   {
/*  378: 364 */     this.propertyName = propertyName;
/*  379:     */   }
/*  380:     */   
/*  381:     */   public void setDeclaringClass(XClass declaringClass)
/*  382:     */   {
/*  383: 368 */     this.declaringClass = declaringClass;
/*  384: 369 */     this.declaringClassSet = true;
/*  385:     */   }
/*  386:     */   
/*  387:     */   public void bind()
/*  388:     */   {
/*  389: 373 */     this.collection = createCollection(this.propertyHolder.getPersistentClass());
/*  390: 374 */     String role = StringHelper.qualify(this.propertyHolder.getPath(), this.propertyName);
/*  391: 375 */     LOG.debugf("Collection role: %s", role);
/*  392: 376 */     this.collection.setRole(role);
/*  393: 377 */     this.collection.setNodeName(this.propertyName);
/*  394: 379 */     if ((this.property.isAnnotationPresent(MapKeyColumn.class)) && (this.mapKeyPropertyName != null)) {
/*  395: 381 */       throw new AnnotationException("Cannot mix @javax.persistence.MapKey and @MapKeyColumn or @org.hibernate.annotations.MapKey on the same collection: " + StringHelper.qualify(this.propertyHolder.getPath(), this.propertyName));
/*  396:     */     }
/*  397: 390 */     defineFetchingStrategy();
/*  398: 391 */     this.collection.setBatchSize(this.batchSize);
/*  399: 392 */     if ((this.orderBy != null) && (this.hqlOrderBy != null)) {
/*  400: 393 */       throw new AnnotationException("Cannot use sql order by clause in conjunction of EJB3 order by clause: " + safeCollectionRole());
/*  401:     */     }
/*  402: 398 */     this.collection.setMutable(!this.property.isAnnotationPresent(Immutable.class));
/*  403: 399 */     OptimisticLock lockAnn = (OptimisticLock)this.property.getAnnotation(OptimisticLock.class);
/*  404: 400 */     if (lockAnn != null) {
/*  405: 400 */       this.collection.setOptimisticLocked(!lockAnn.excluded());
/*  406:     */     }
/*  407: 402 */     Persister persisterAnn = (Persister)this.property.getAnnotation(Persister.class);
/*  408: 403 */     if (persisterAnn != null) {
/*  409: 404 */       this.collection.setCollectionPersisterClass(persisterAnn.impl());
/*  410:     */     }
/*  411: 408 */     if (this.orderBy != null) {
/*  412: 408 */       this.collection.setOrderBy(this.orderBy);
/*  413:     */     }
/*  414: 409 */     if (this.isSorted)
/*  415:     */     {
/*  416: 410 */       this.collection.setSorted(true);
/*  417: 411 */       if (this.comparator != null) {
/*  418:     */         try
/*  419:     */         {
/*  420: 413 */           this.collection.setComparator((Comparator)this.comparator.newInstance());
/*  421:     */         }
/*  422:     */         catch (ClassCastException e)
/*  423:     */         {
/*  424: 416 */           throw new AnnotationException("Comparator not implementing java.util.Comparator class: " + this.comparator.getName() + "(" + safeCollectionRole() + ")");
/*  425:     */         }
/*  426:     */         catch (Exception e)
/*  427:     */         {
/*  428: 422 */           throw new AnnotationException("Could not instantiate comparator class: " + this.comparator.getName() + "(" + safeCollectionRole() + ")");
/*  429:     */         }
/*  430:     */       }
/*  431:     */     }
/*  432: 430 */     else if (this.hasToBeSorted)
/*  433:     */     {
/*  434: 431 */       throw new AnnotationException("A sorted collection has to define @Sort: " + safeCollectionRole());
/*  435:     */     }
/*  436: 439 */     if (StringHelper.isNotEmpty(this.cacheConcurrencyStrategy))
/*  437:     */     {
/*  438: 440 */       this.collection.setCacheConcurrencyStrategy(this.cacheConcurrencyStrategy);
/*  439: 441 */       this.collection.setCacheRegionName(this.cacheRegionName);
/*  440:     */     }
/*  441: 445 */     SQLInsert sqlInsert = (SQLInsert)this.property.getAnnotation(SQLInsert.class);
/*  442: 446 */     SQLUpdate sqlUpdate = (SQLUpdate)this.property.getAnnotation(SQLUpdate.class);
/*  443: 447 */     SQLDelete sqlDelete = (SQLDelete)this.property.getAnnotation(SQLDelete.class);
/*  444: 448 */     SQLDeleteAll sqlDeleteAll = (SQLDeleteAll)this.property.getAnnotation(SQLDeleteAll.class);
/*  445: 449 */     Loader loader = (Loader)this.property.getAnnotation(Loader.class);
/*  446: 450 */     if (sqlInsert != null) {
/*  447: 451 */       this.collection.setCustomSQLInsert(sqlInsert.sql().trim(), sqlInsert.callable(), ExecuteUpdateResultCheckStyle.fromExternalName(sqlInsert.check().toString().toLowerCase()));
/*  448:     */     }
/*  449: 456 */     if (sqlUpdate != null) {
/*  450: 457 */       this.collection.setCustomSQLUpdate(sqlUpdate.sql(), sqlUpdate.callable(), ExecuteUpdateResultCheckStyle.fromExternalName(sqlUpdate.check().toString().toLowerCase()));
/*  451:     */     }
/*  452: 461 */     if (sqlDelete != null) {
/*  453: 462 */       this.collection.setCustomSQLDelete(sqlDelete.sql(), sqlDelete.callable(), ExecuteUpdateResultCheckStyle.fromExternalName(sqlDelete.check().toString().toLowerCase()));
/*  454:     */     }
/*  455: 466 */     if (sqlDeleteAll != null) {
/*  456: 467 */       this.collection.setCustomSQLDeleteAll(sqlDeleteAll.sql(), sqlDeleteAll.callable(), ExecuteUpdateResultCheckStyle.fromExternalName(sqlDeleteAll.check().toString().toLowerCase()));
/*  457:     */     }
/*  458: 471 */     if (loader != null) {
/*  459: 472 */       this.collection.setLoaderName(loader.namedQuery());
/*  460:     */     }
/*  461: 476 */     boolean isMappedBy = !BinderHelper.isEmptyAnnotationValue(this.mappedBy);
/*  462: 478 */     if ((isMappedBy) && ((this.property.isAnnotationPresent(JoinColumn.class)) || (this.property.isAnnotationPresent(JoinColumns.class)) || (this.propertyHolder.getJoinTable(this.property) != null)))
/*  463:     */     {
/*  464: 482 */       String message = "Associations marked as mappedBy must not define database mappings like @JoinTable or @JoinColumn: ";
/*  465: 483 */       message = message + StringHelper.qualify(this.propertyHolder.getPath(), this.propertyName);
/*  466: 484 */       throw new AnnotationException(message);
/*  467:     */     }
/*  468: 487 */     this.collection.setInverse(isMappedBy);
/*  469: 490 */     if ((!this.oneToMany) && (isMappedBy)) {
/*  470: 491 */       this.mappings.addMappedBy(getCollectionType().getName(), this.mappedBy, this.propertyName);
/*  471:     */     }
/*  472: 494 */     XClass collectionType = getCollectionType();
/*  473: 495 */     if (this.inheritanceStatePerClass == null) {
/*  474: 495 */       throw new AssertionFailure("inheritanceStatePerClass not set");
/*  475:     */     }
/*  476: 496 */     SecondPass sp = getSecondPass(this.fkJoinColumns, this.joinColumns, this.inverseJoinColumns, this.elementColumns, this.mapKeyColumns, this.mapKeyManyToManyColumns, this.isEmbedded, this.property, collectionType, this.ignoreNotFound, this.oneToMany, this.tableBinder, this.mappings);
/*  477: 506 */     if ((collectionType.isAnnotationPresent(Embeddable.class)) || (this.property.isAnnotationPresent(ElementCollection.class))) {
/*  478: 512 */       this.mappings.addSecondPass(sp, !isMappedBy);
/*  479:     */     } else {
/*  480: 515 */       this.mappings.addSecondPass(sp, !isMappedBy);
/*  481:     */     }
/*  482: 518 */     this.mappings.addCollection(this.collection);
/*  483:     */     
/*  484:     */ 
/*  485: 521 */     PropertyBinder binder = new PropertyBinder();
/*  486: 522 */     binder.setName(this.propertyName);
/*  487: 523 */     binder.setValue(this.collection);
/*  488: 524 */     binder.setCascade(this.cascadeStrategy);
/*  489: 525 */     if ((this.cascadeStrategy != null) && (this.cascadeStrategy.indexOf("delete-orphan") >= 0)) {
/*  490: 526 */       this.collection.setOrphanDelete(true);
/*  491:     */     }
/*  492: 528 */     binder.setAccessType(this.accessType);
/*  493: 529 */     binder.setProperty(this.property);
/*  494: 530 */     binder.setInsertable(this.insertable);
/*  495: 531 */     binder.setUpdatable(this.updatable);
/*  496: 532 */     Property prop = binder.makeProperty();
/*  497: 534 */     if (!this.declaringClassSet) {
/*  498: 534 */       throw new AssertionFailure("DeclaringClass is not set in CollectionBinder while binding");
/*  499:     */     }
/*  500: 535 */     this.propertyHolder.addProperty(prop, this.declaringClass);
/*  501:     */   }
/*  502:     */   
/*  503:     */   private void defineFetchingStrategy()
/*  504:     */   {
/*  505: 539 */     LazyCollection lazy = (LazyCollection)this.property.getAnnotation(LazyCollection.class);
/*  506: 540 */     Fetch fetch = (Fetch)this.property.getAnnotation(Fetch.class);
/*  507: 541 */     javax.persistence.OneToMany oneToMany = (javax.persistence.OneToMany)this.property.getAnnotation(javax.persistence.OneToMany.class);
/*  508: 542 */     ManyToMany manyToMany = (ManyToMany)this.property.getAnnotation(ManyToMany.class);
/*  509: 543 */     ElementCollection elementCollection = (ElementCollection)this.property.getAnnotation(ElementCollection.class);
/*  510: 544 */     ManyToAny manyToAny = (ManyToAny)this.property.getAnnotation(ManyToAny.class);
/*  511:     */     FetchType fetchType;
/*  512: 546 */     if (oneToMany != null)
/*  513:     */     {
/*  514: 547 */       fetchType = oneToMany.fetch();
/*  515:     */     }
/*  516:     */     else
/*  517:     */     {
/*  518:     */       FetchType fetchType;
/*  519: 549 */       if (manyToMany != null)
/*  520:     */       {
/*  521: 550 */         fetchType = manyToMany.fetch();
/*  522:     */       }
/*  523:     */       else
/*  524:     */       {
/*  525:     */         FetchType fetchType;
/*  526: 552 */         if (elementCollection != null)
/*  527:     */         {
/*  528: 553 */           fetchType = elementCollection.fetch();
/*  529:     */         }
/*  530:     */         else
/*  531:     */         {
/*  532:     */           FetchType fetchType;
/*  533: 555 */           if (manyToAny != null) {
/*  534: 556 */             fetchType = FetchType.LAZY;
/*  535:     */           } else {
/*  536: 559 */             throw new AssertionFailure("Define fetch strategy on a property not annotated with @ManyToOne nor @OneToMany nor @CollectionOfElements");
/*  537:     */           }
/*  538:     */         }
/*  539:     */       }
/*  540:     */     }
/*  541:     */     FetchType fetchType;
/*  542: 563 */     if (lazy != null)
/*  543:     */     {
/*  544: 564 */       this.collection.setLazy(lazy.value() != LazyCollectionOption.FALSE);
/*  545: 565 */       this.collection.setExtraLazy(lazy.value() == LazyCollectionOption.EXTRA);
/*  546:     */     }
/*  547:     */     else
/*  548:     */     {
/*  549: 568 */       this.collection.setLazy(fetchType == FetchType.LAZY);
/*  550: 569 */       this.collection.setExtraLazy(false);
/*  551:     */     }
/*  552: 571 */     if (fetch != null)
/*  553:     */     {
/*  554: 572 */       if (fetch.value() == org.hibernate.annotations.FetchMode.JOIN)
/*  555:     */       {
/*  556: 573 */         this.collection.setFetchMode(org.hibernate.FetchMode.JOIN);
/*  557: 574 */         this.collection.setLazy(false);
/*  558:     */       }
/*  559: 576 */       else if (fetch.value() == org.hibernate.annotations.FetchMode.SELECT)
/*  560:     */       {
/*  561: 577 */         this.collection.setFetchMode(org.hibernate.FetchMode.SELECT);
/*  562:     */       }
/*  563: 579 */       else if (fetch.value() == org.hibernate.annotations.FetchMode.SUBSELECT)
/*  564:     */       {
/*  565: 580 */         this.collection.setFetchMode(org.hibernate.FetchMode.SELECT);
/*  566: 581 */         this.collection.setSubselectLoadable(true);
/*  567: 582 */         this.collection.getOwner().setSubselectLoadableCollections(true);
/*  568:     */       }
/*  569:     */       else
/*  570:     */       {
/*  571: 585 */         throw new AssertionFailure("Unknown FetchMode: " + fetch.value());
/*  572:     */       }
/*  573:     */     }
/*  574:     */     else {
/*  575: 589 */       this.collection.setFetchMode(AnnotationBinder.getFetchMode(fetchType));
/*  576:     */     }
/*  577:     */   }
/*  578:     */   
/*  579:     */   private XClass getCollectionType()
/*  580:     */   {
/*  581: 594 */     if (AnnotationBinder.isDefault(this.targetEntity, this.mappings))
/*  582:     */     {
/*  583: 595 */       if (this.collectionType != null) {
/*  584: 596 */         return this.collectionType;
/*  585:     */       }
/*  586: 599 */       String errorMsg = "Collection has neither generic type or OneToMany.targetEntity() defined: " + safeCollectionRole();
/*  587:     */       
/*  588: 601 */       throw new AnnotationException(errorMsg);
/*  589:     */     }
/*  590: 605 */     return this.targetEntity;
/*  591:     */   }
/*  592:     */   
/*  593:     */   public SecondPass getSecondPass(final Ejb3JoinColumn[] fkJoinColumns, final Ejb3JoinColumn[] keyColumns, final Ejb3JoinColumn[] inverseColumns, final Ejb3Column[] elementColumns, Ejb3Column[] mapKeyColumns, Ejb3JoinColumn[] mapKeyManyToManyColumns, final boolean isEmbedded, final XProperty property, final XClass collType, final boolean ignoreNotFound, final boolean unique, final TableBinder assocTableBinder, final Mappings mappings)
/*  594:     */   {
/*  595: 623 */     new CollectionSecondPass(mappings, this.collection)
/*  596:     */     {
/*  597:     */       public void secondPass(Map persistentClasses, Map inheritedMetas)
/*  598:     */         throws MappingException
/*  599:     */       {
/*  600: 626 */         CollectionBinder.this.bindStarToManySecondPass(persistentClasses, collType, fkJoinColumns, keyColumns, inverseColumns, elementColumns, isEmbedded, property, unique, assocTableBinder, ignoreNotFound, mappings);
/*  601:     */       }
/*  602:     */     };
/*  603:     */   }
/*  604:     */   
/*  605:     */   protected boolean bindStarToManySecondPass(Map persistentClasses, XClass collType, Ejb3JoinColumn[] fkJoinColumns, Ejb3JoinColumn[] keyColumns, Ejb3JoinColumn[] inverseColumns, Ejb3Column[] elementColumns, boolean isEmbedded, XProperty property, boolean unique, TableBinder associationTableBinder, boolean ignoreNotFound, Mappings mappings)
/*  606:     */   {
/*  607: 650 */     PersistentClass persistentClass = (PersistentClass)persistentClasses.get(collType.getName());
/*  608: 651 */     boolean reversePropertyInJoin = false;
/*  609: 652 */     if ((persistentClass != null) && (StringHelper.isNotEmpty(this.mappedBy))) {
/*  610:     */       try
/*  611:     */       {
/*  612: 654 */         reversePropertyInJoin = 0 != persistentClass.getJoinNumber(persistentClass.getRecursiveProperty(this.mappedBy));
/*  613:     */       }
/*  614:     */       catch (MappingException e)
/*  615:     */       {
/*  616: 659 */         StringBuilder error = new StringBuilder(80);
/*  617: 660 */         error.append("mappedBy reference an unknown target entity property: ").append(collType).append(".").append(this.mappedBy).append(" in ").append(this.collection.getOwnerEntityName()).append(".").append(property.getName());
/*  618:     */         
/*  619:     */ 
/*  620:     */ 
/*  621:     */ 
/*  622:     */ 
/*  623: 666 */         throw new AnnotationException(error.toString());
/*  624:     */       }
/*  625:     */     }
/*  626: 669 */     if ((persistentClass != null) && (!reversePropertyInJoin) && (this.oneToMany) && (!this.isExplicitAssociationTable) && (((this.joinColumns[0].isImplicit()) && (!BinderHelper.isEmptyAnnotationValue(this.mappedBy))) || (!fkJoinColumns[0].isImplicit())))
/*  627:     */     {
/*  628: 677 */       bindOneToManySecondPass(getCollection(), persistentClasses, fkJoinColumns, collType, this.cascadeDeleteEnabled, ignoreNotFound, this.hqlOrderBy, mappings, this.inheritanceStatePerClass);
/*  629:     */       
/*  630:     */ 
/*  631:     */ 
/*  632:     */ 
/*  633:     */ 
/*  634:     */ 
/*  635:     */ 
/*  636:     */ 
/*  637:     */ 
/*  638: 687 */       return true;
/*  639:     */     }
/*  640: 691 */     bindManyToManySecondPass(this.collection, persistentClasses, keyColumns, inverseColumns, elementColumns, isEmbedded, collType, ignoreNotFound, unique, this.cascadeDeleteEnabled, associationTableBinder, property, this.propertyHolder, this.hqlOrderBy, mappings);
/*  641:     */     
/*  642:     */ 
/*  643:     */ 
/*  644:     */ 
/*  645:     */ 
/*  646:     */ 
/*  647:     */ 
/*  648:     */ 
/*  649:     */ 
/*  650:     */ 
/*  651: 702 */     return false;
/*  652:     */   }
/*  653:     */   
/*  654:     */   protected void bindOneToManySecondPass(org.hibernate.mapping.Collection collection, Map persistentClasses, Ejb3JoinColumn[] fkJoinColumns, XClass collectionType, boolean cascadeDeleteEnabled, boolean ignoreNotFound, String hqlOrderBy, Mappings mappings, Map<XClass, InheritanceState> inheritanceStatePerClass)
/*  655:     */   {
/*  656: 716 */     if (LOG.isDebugEnabled()) {
/*  657: 717 */       LOG.debugf("Binding a OneToMany: %s.%s through a foreign key", this.propertyHolder.getEntityName(), this.propertyName);
/*  658:     */     }
/*  659: 719 */     org.hibernate.mapping.OneToMany oneToMany = new org.hibernate.mapping.OneToMany(mappings, collection.getOwner());
/*  660: 720 */     collection.setElement(oneToMany);
/*  661: 721 */     oneToMany.setReferencedEntityName(collectionType.getName());
/*  662: 722 */     oneToMany.setIgnoreNotFound(ignoreNotFound);
/*  663:     */     
/*  664: 724 */     String assocClass = oneToMany.getReferencedEntityName();
/*  665: 725 */     PersistentClass associatedClass = (PersistentClass)persistentClasses.get(assocClass);
/*  666: 726 */     String orderBy = buildOrderByClauseFromHql(hqlOrderBy, associatedClass, collection.getRole());
/*  667: 727 */     if (orderBy != null) {
/*  668: 727 */       collection.setOrderBy(orderBy);
/*  669:     */     }
/*  670: 728 */     if (mappings == null) {
/*  671: 729 */       throw new AssertionFailure("CollectionSecondPass for oneToMany should not be called with null mappings");
/*  672:     */     }
/*  673: 733 */     Map<String, Join> joins = mappings.getJoins(assocClass);
/*  674: 734 */     if (associatedClass == null) {
/*  675: 735 */       throw new MappingException("Association references unmapped class: " + assocClass);
/*  676:     */     }
/*  677: 739 */     oneToMany.setAssociatedClass(associatedClass);
/*  678: 740 */     for (Ejb3JoinColumn column : fkJoinColumns)
/*  679:     */     {
/*  680: 741 */       column.setPersistentClass(associatedClass, joins, inheritanceStatePerClass);
/*  681: 742 */       column.setJoins(joins);
/*  682: 743 */       collection.setCollectionTable(column.getTable());
/*  683:     */     }
/*  684: 745 */     if (LOG.isDebugEnabled()) {
/*  685: 746 */       LOG.debugf("Mapping collection: %s -> %s", collection.getRole(), collection.getCollectionTable().getName());
/*  686:     */     }
/*  687: 748 */     bindFilters(false);
/*  688: 749 */     bindCollectionSecondPass(collection, null, fkJoinColumns, cascadeDeleteEnabled, this.property, mappings);
/*  689: 750 */     if ((!collection.isInverse()) && (!collection.getKey().isNullable()))
/*  690:     */     {
/*  691: 753 */       String entityName = oneToMany.getReferencedEntityName();
/*  692: 754 */       PersistentClass referenced = mappings.getClass(entityName);
/*  693: 755 */       Backref prop = new Backref();
/*  694: 756 */       prop.setName('_' + fkJoinColumns[0].getPropertyName() + "Backref");
/*  695: 757 */       prop.setUpdateable(false);
/*  696: 758 */       prop.setSelectable(false);
/*  697: 759 */       prop.setCollectionRole(collection.getRole());
/*  698: 760 */       prop.setEntityName(collection.getOwner().getEntityName());
/*  699: 761 */       prop.setValue(collection.getKey());
/*  700: 762 */       referenced.addProperty(prop);
/*  701:     */     }
/*  702:     */   }
/*  703:     */   
/*  704:     */   private void bindFilters(boolean hasAssociationTable)
/*  705:     */   {
/*  706: 768 */     Filter simpleFilter = (Filter)this.property.getAnnotation(Filter.class);
/*  707: 772 */     if (simpleFilter != null) {
/*  708: 773 */       if (hasAssociationTable) {
/*  709: 774 */         this.collection.addManyToManyFilter(simpleFilter.name(), getCondition(simpleFilter));
/*  710:     */       } else {
/*  711: 777 */         this.collection.addFilter(simpleFilter.name(), getCondition(simpleFilter));
/*  712:     */       }
/*  713:     */     }
/*  714: 780 */     Filters filters = (Filters)this.property.getAnnotation(Filters.class);
/*  715: 781 */     if (filters != null) {
/*  716: 782 */       for (Filter filter : filters.value()) {
/*  717: 783 */         if (hasAssociationTable) {
/*  718: 784 */           this.collection.addManyToManyFilter(filter.name(), getCondition(filter));
/*  719:     */         } else {
/*  720: 787 */           this.collection.addFilter(filter.name(), getCondition(filter));
/*  721:     */         }
/*  722:     */       }
/*  723:     */     }
/*  724: 791 */     FilterJoinTable simpleFilterJoinTable = (FilterJoinTable)this.property.getAnnotation(FilterJoinTable.class);
/*  725: 792 */     if (simpleFilterJoinTable != null) {
/*  726: 793 */       if (hasAssociationTable) {
/*  727: 794 */         this.collection.addFilter(simpleFilterJoinTable.name(), getCondition(simpleFilterJoinTable));
/*  728:     */       } else {
/*  729: 797 */         throw new AnnotationException("Illegal use of @FilterJoinTable on an association without join table:" + StringHelper.qualify(this.propertyHolder.getPath(), this.propertyName));
/*  730:     */       }
/*  731:     */     }
/*  732: 803 */     FilterJoinTables filterJoinTables = (FilterJoinTables)this.property.getAnnotation(FilterJoinTables.class);
/*  733: 804 */     if (filterJoinTables != null) {
/*  734: 805 */       for (FilterJoinTable filter : filterJoinTables.value()) {
/*  735: 806 */         if (hasAssociationTable) {
/*  736: 807 */           this.collection.addFilter(filter.name(), getCondition(filter));
/*  737:     */         } else {
/*  738: 810 */           throw new AnnotationException("Illegal use of @FilterJoinTable on an association without join table:" + StringHelper.qualify(this.propertyHolder.getPath(), this.propertyName));
/*  739:     */         }
/*  740:     */       }
/*  741:     */     }
/*  742: 818 */     Where where = (Where)this.property.getAnnotation(Where.class);
/*  743: 819 */     String whereClause = where == null ? null : where.clause();
/*  744: 820 */     if (StringHelper.isNotEmpty(whereClause)) {
/*  745: 821 */       if (hasAssociationTable) {
/*  746: 822 */         this.collection.setManyToManyWhere(whereClause);
/*  747:     */       } else {
/*  748: 825 */         this.collection.setWhere(whereClause);
/*  749:     */       }
/*  750:     */     }
/*  751: 829 */     WhereJoinTable whereJoinTable = (WhereJoinTable)this.property.getAnnotation(WhereJoinTable.class);
/*  752: 830 */     String whereJoinTableClause = whereJoinTable == null ? null : whereJoinTable.clause();
/*  753: 831 */     if (StringHelper.isNotEmpty(whereJoinTableClause)) {
/*  754: 832 */       if (hasAssociationTable) {
/*  755: 833 */         this.collection.setWhere(whereJoinTableClause);
/*  756:     */       } else {
/*  757: 836 */         throw new AnnotationException("Illegal use of @WhereJoinTable on an association without join table:" + StringHelper.qualify(this.propertyHolder.getPath(), this.propertyName));
/*  758:     */       }
/*  759:     */     }
/*  760:     */   }
/*  761:     */   
/*  762:     */   private String getCondition(FilterJoinTable filter)
/*  763:     */   {
/*  764: 855 */     String name = filter.name();
/*  765: 856 */     String cond = filter.condition();
/*  766: 857 */     return getCondition(cond, name);
/*  767:     */   }
/*  768:     */   
/*  769:     */   private String getCondition(Filter filter)
/*  770:     */   {
/*  771: 862 */     String name = filter.name();
/*  772: 863 */     String cond = filter.condition();
/*  773: 864 */     return getCondition(cond, name);
/*  774:     */   }
/*  775:     */   
/*  776:     */   private String getCondition(String cond, String name)
/*  777:     */   {
/*  778: 868 */     if (BinderHelper.isEmptyAnnotationValue(cond))
/*  779:     */     {
/*  780: 869 */       cond = this.mappings.getFilterDefinition(name).getDefaultFilterCondition();
/*  781: 870 */       if (StringHelper.isEmpty(cond)) {
/*  782: 871 */         throw new AnnotationException("no filter condition found for filter " + name + " in " + StringHelper.qualify(this.propertyHolder.getPath(), this.propertyName));
/*  783:     */       }
/*  784:     */     }
/*  785: 877 */     return cond;
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void setCache(Cache cacheAnn)
/*  789:     */   {
/*  790: 881 */     if (cacheAnn != null)
/*  791:     */     {
/*  792: 882 */       this.cacheRegionName = (BinderHelper.isEmptyAnnotationValue(cacheAnn.region()) ? null : cacheAnn.region());
/*  793: 883 */       this.cacheConcurrencyStrategy = EntityBinder.getCacheConcurrencyStrategy(cacheAnn.usage());
/*  794:     */     }
/*  795:     */     else
/*  796:     */     {
/*  797: 886 */       this.cacheConcurrencyStrategy = null;
/*  798: 887 */       this.cacheRegionName = null;
/*  799:     */     }
/*  800:     */   }
/*  801:     */   
/*  802:     */   public void setOneToMany(boolean oneToMany)
/*  803:     */   {
/*  804: 892 */     this.oneToMany = oneToMany;
/*  805:     */   }
/*  806:     */   
/*  807:     */   public void setIndexColumn(IndexColumn indexColumn)
/*  808:     */   {
/*  809: 896 */     this.indexColumn = indexColumn;
/*  810:     */   }
/*  811:     */   
/*  812:     */   public void setMapKey(MapKey key)
/*  813:     */   {
/*  814: 900 */     if (key != null) {
/*  815: 901 */       this.mapKeyPropertyName = key.name();
/*  816:     */     }
/*  817:     */   }
/*  818:     */   
/*  819:     */   private static String buildOrderByClauseFromHql(String hqlOrderBy, PersistentClass associatedClass, String role)
/*  820:     */   {
/*  821: 906 */     String orderByString = null;
/*  822: 907 */     if (hqlOrderBy != null)
/*  823:     */     {
/*  824: 908 */       List<String> properties = new ArrayList();
/*  825: 909 */       List<String> ordering = new ArrayList();
/*  826: 910 */       StringBuilder orderByBuffer = new StringBuilder();
/*  827:     */       int index;
/*  828: 911 */       if (hqlOrderBy.length() == 0)
/*  829:     */       {
/*  830: 913 */         Iterator it = associatedClass.getIdentifier().getColumnIterator();
/*  831: 914 */         while (it.hasNext())
/*  832:     */         {
/*  833: 915 */           Selectable col = (Selectable)it.next();
/*  834: 916 */           orderByBuffer.append(col.getText()).append(" asc").append(", ");
/*  835:     */         }
/*  836:     */       }
/*  837:     */       else
/*  838:     */       {
/*  839: 920 */         StringTokenizer st = new StringTokenizer(hqlOrderBy, " ,", false);
/*  840: 921 */         String currentOrdering = null;
/*  841: 923 */         while (st.hasMoreTokens())
/*  842:     */         {
/*  843: 924 */           String token = st.nextToken();
/*  844: 925 */           if (isNonPropertyToken(token))
/*  845:     */           {
/*  846: 926 */             if (currentOrdering != null) {
/*  847: 927 */               throw new AnnotationException("Error while parsing HQL orderBy clause: " + hqlOrderBy + " (" + role + ")");
/*  848:     */             }
/*  849: 932 */             currentOrdering = token;
/*  850:     */           }
/*  851:     */           else
/*  852:     */           {
/*  853: 936 */             if (currentOrdering == null)
/*  854:     */             {
/*  855: 938 */               ordering.add("asc");
/*  856:     */             }
/*  857:     */             else
/*  858:     */             {
/*  859: 941 */               ordering.add(currentOrdering);
/*  860: 942 */               currentOrdering = null;
/*  861:     */             }
/*  862: 944 */             properties.add(token);
/*  863:     */           }
/*  864:     */         }
/*  865: 947 */         ordering.remove(0);
/*  866: 949 */         if (currentOrdering == null)
/*  867:     */         {
/*  868: 951 */           ordering.add("asc");
/*  869:     */         }
/*  870:     */         else
/*  871:     */         {
/*  872: 954 */           ordering.add(currentOrdering);
/*  873: 955 */           currentOrdering = null;
/*  874:     */         }
/*  875: 957 */         index = 0;
/*  876: 959 */         for (String property : properties)
/*  877:     */         {
/*  878: 960 */           Property p = BinderHelper.findPropertyByName(associatedClass, property);
/*  879: 961 */           if (p == null) {
/*  880: 962 */             throw new AnnotationException("property from @OrderBy clause not found: " + associatedClass.getEntityName() + "." + property);
/*  881:     */           }
/*  882: 967 */           PersistentClass pc = p.getPersistentClass();
/*  883:     */           String table;
/*  884:     */           String table;
/*  885: 969 */           if (pc == null)
/*  886:     */           {
/*  887: 973 */             table = "";
/*  888:     */           }
/*  889:     */           else
/*  890:     */           {
/*  891:     */             String table;
/*  892: 976 */             if ((pc == associatedClass) || (((associatedClass instanceof SingleTableSubclass)) && (pc.getMappedClass().isAssignableFrom(associatedClass.getMappedClass())))) {
/*  893: 980 */               table = "";
/*  894:     */             } else {
/*  895: 982 */               table = pc.getTable().getQuotedName() + ".";
/*  896:     */             }
/*  897:     */           }
/*  898: 985 */           Iterator propertyColumns = p.getColumnIterator();
/*  899: 986 */           while (propertyColumns.hasNext())
/*  900:     */           {
/*  901: 987 */             Selectable column = (Selectable)propertyColumns.next();
/*  902: 988 */             orderByBuffer.append(table).append(column.getText()).append(" ").append((String)ordering.get(index)).append(", ");
/*  903:     */           }
/*  904: 994 */           index++;
/*  905:     */         }
/*  906:     */       }
/*  907: 997 */       orderByString = orderByBuffer.substring(0, orderByBuffer.length() - 2);
/*  908:     */     }
/*  909: 999 */     return orderByString;
/*  910:     */   }
/*  911:     */   
/*  912:     */   private static String buildOrderByClauseFromHql(String hqlOrderBy, Component component, String role)
/*  913:     */   {
/*  914:1003 */     String orderByString = null;
/*  915:1004 */     if (hqlOrderBy != null)
/*  916:     */     {
/*  917:1005 */       List<String> properties = new ArrayList();
/*  918:1006 */       List<String> ordering = new ArrayList();
/*  919:1007 */       StringBuilder orderByBuffer = new StringBuilder();
/*  920:1008 */       if (hqlOrderBy.length() != 0)
/*  921:     */       {
/*  922:1012 */         StringTokenizer st = new StringTokenizer(hqlOrderBy, " ,", false);
/*  923:1013 */         String currentOrdering = null;
/*  924:1015 */         while (st.hasMoreTokens())
/*  925:     */         {
/*  926:1016 */           String token = st.nextToken();
/*  927:1017 */           if (isNonPropertyToken(token))
/*  928:     */           {
/*  929:1018 */             if (currentOrdering != null) {
/*  930:1019 */               throw new AnnotationException("Error while parsing HQL orderBy clause: " + hqlOrderBy + " (" + role + ")");
/*  931:     */             }
/*  932:1024 */             currentOrdering = token;
/*  933:     */           }
/*  934:     */           else
/*  935:     */           {
/*  936:1028 */             if (currentOrdering == null)
/*  937:     */             {
/*  938:1030 */               ordering.add("asc");
/*  939:     */             }
/*  940:     */             else
/*  941:     */             {
/*  942:1033 */               ordering.add(currentOrdering);
/*  943:1034 */               currentOrdering = null;
/*  944:     */             }
/*  945:1036 */             properties.add(token);
/*  946:     */           }
/*  947:     */         }
/*  948:1039 */         ordering.remove(0);
/*  949:1041 */         if (currentOrdering == null)
/*  950:     */         {
/*  951:1043 */           ordering.add("asc");
/*  952:     */         }
/*  953:     */         else
/*  954:     */         {
/*  955:1046 */           ordering.add(currentOrdering);
/*  956:1047 */           currentOrdering = null;
/*  957:     */         }
/*  958:1049 */         int index = 0;
/*  959:1051 */         for (String property : properties)
/*  960:     */         {
/*  961:1052 */           Property p = BinderHelper.findPropertyByName(component, property);
/*  962:1053 */           if (p == null) {
/*  963:1054 */             throw new AnnotationException("property from @OrderBy clause not found: " + role + "." + property);
/*  964:     */           }
/*  965:1060 */           Iterator propertyColumns = p.getColumnIterator();
/*  966:1061 */           while (propertyColumns.hasNext())
/*  967:     */           {
/*  968:1062 */             Selectable column = (Selectable)propertyColumns.next();
/*  969:1063 */             orderByBuffer.append(column.getText()).append(" ").append((String)ordering.get(index)).append(", ");
/*  970:     */           }
/*  971:1068 */           index++;
/*  972:     */         }
/*  973:1071 */         if (orderByBuffer.length() >= 2) {
/*  974:1072 */           orderByString = orderByBuffer.substring(0, orderByBuffer.length() - 2);
/*  975:     */         }
/*  976:     */       }
/*  977:     */     }
/*  978:1076 */     return orderByString;
/*  979:     */   }
/*  980:     */   
/*  981:     */   private static boolean isNonPropertyToken(String token)
/*  982:     */   {
/*  983:1080 */     if (" ".equals(token)) {
/*  984:1080 */       return true;
/*  985:     */     }
/*  986:1081 */     if (",".equals(token)) {
/*  987:1081 */       return true;
/*  988:     */     }
/*  989:1082 */     if (token.equalsIgnoreCase("desc")) {
/*  990:1082 */       return true;
/*  991:     */     }
/*  992:1083 */     if (token.equalsIgnoreCase("asc")) {
/*  993:1083 */       return true;
/*  994:     */     }
/*  995:1084 */     return false;
/*  996:     */   }
/*  997:     */   
/*  998:     */   private static SimpleValue buildCollectionKey(org.hibernate.mapping.Collection collValue, Ejb3JoinColumn[] joinColumns, boolean cascadeDeleteEnabled, XProperty property, Mappings mappings)
/*  999:     */   {
/* 1000:1095 */     if ((joinColumns.length > 0) && (StringHelper.isNotEmpty(joinColumns[0].getMappedBy())))
/* 1001:     */     {
/* 1002:1096 */       String entityName = joinColumns[0].getManyToManyOwnerSideEntityName() != null ? "inverse__" + joinColumns[0].getManyToManyOwnerSideEntityName() : joinColumns[0].getPropertyHolder().getEntityName();
/* 1003:     */       
/* 1004:     */ 
/* 1005:1099 */       String propRef = mappings.getPropertyReferencedAssociation(entityName, joinColumns[0].getMappedBy());
/* 1006:1103 */       if (propRef != null)
/* 1007:     */       {
/* 1008:1104 */         collValue.setReferencedPropertyName(propRef);
/* 1009:1105 */         mappings.addPropertyReference(collValue.getOwnerEntityName(), propRef);
/* 1010:     */       }
/* 1011:     */     }
/* 1012:1108 */     String propRef = collValue.getReferencedPropertyName();
/* 1013:     */     KeyValue keyVal;
/* 1014:     */     KeyValue keyVal;
/* 1015:1109 */     if (propRef == null) {
/* 1016:1110 */       keyVal = collValue.getOwner().getIdentifier();
/* 1017:     */     } else {
/* 1018:1113 */       keyVal = (KeyValue)collValue.getOwner().getRecursiveProperty(propRef).getValue();
/* 1019:     */     }
/* 1020:1117 */     DependantValue key = new DependantValue(mappings, collValue.getCollectionTable(), keyVal);
/* 1021:1118 */     key.setTypeName(null);
/* 1022:1119 */     Ejb3Column.checkPropertyConsistency(joinColumns, collValue.getOwnerEntityName());
/* 1023:1120 */     key.setNullable((joinColumns.length == 0) || (joinColumns[0].isNullable()));
/* 1024:1121 */     key.setUpdateable((joinColumns.length == 0) || (joinColumns[0].isUpdatable()));
/* 1025:1122 */     key.setCascadeDeleteEnabled(cascadeDeleteEnabled);
/* 1026:1123 */     collValue.setKey(key);
/* 1027:1124 */     ForeignKey fk = property != null ? (ForeignKey)property.getAnnotation(ForeignKey.class) : null;
/* 1028:1125 */     String fkName = fk != null ? fk.name() : "";
/* 1029:1126 */     if (!BinderHelper.isEmptyAnnotationValue(fkName)) {
/* 1030:1126 */       key.setForeignKeyName(fkName);
/* 1031:     */     }
/* 1032:1127 */     return key;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   protected void bindManyToManySecondPass(org.hibernate.mapping.Collection collValue, Map persistentClasses, Ejb3JoinColumn[] joinColumns, Ejb3JoinColumn[] inverseJoinColumns, Ejb3Column[] elementColumns, boolean isEmbedded, XClass collType, boolean ignoreNotFound, boolean unique, boolean cascadeDeleteEnabled, TableBinder associationTableBinder, XProperty property, PropertyHolder parentPropertyHolder, String hqlOrderBy, Mappings mappings)
/* 1036:     */     throws MappingException
/* 1037:     */   {
/* 1038:1146 */     PersistentClass collectionEntity = (PersistentClass)persistentClasses.get(collType.getName());
/* 1039:1147 */     boolean isCollectionOfEntities = collectionEntity != null;
/* 1040:1148 */     ManyToAny anyAnn = (ManyToAny)property.getAnnotation(ManyToAny.class);
/* 1041:1149 */     if (LOG.isDebugEnabled())
/* 1042:     */     {
/* 1043:1150 */       String path = collValue.getOwnerEntityName() + "." + joinColumns[0].getPropertyName();
/* 1044:1151 */       if ((isCollectionOfEntities) && (unique)) {
/* 1045:1151 */         LOG.debugf("Binding a OneToMany: %s through an association table", path);
/* 1046:1152 */       } else if (isCollectionOfEntities) {
/* 1047:1152 */         LOG.debugf("Binding as ManyToMany: %s", path);
/* 1048:1153 */       } else if (anyAnn != null) {
/* 1049:1153 */         LOG.debugf("Binding a ManyToAny: %s", path);
/* 1050:     */       } else {
/* 1051:1154 */         LOG.debugf("Binding a collection of element: %s", path);
/* 1052:     */       }
/* 1053:     */     }
/* 1054:1157 */     if (!isCollectionOfEntities)
/* 1055:     */     {
/* 1056:1158 */       if ((property.isAnnotationPresent(ManyToMany.class)) || (property.isAnnotationPresent(javax.persistence.OneToMany.class)))
/* 1057:     */       {
/* 1058:1159 */         String path = collValue.getOwnerEntityName() + "." + joinColumns[0].getPropertyName();
/* 1059:1160 */         throw new AnnotationException("Use of @OneToMany or @ManyToMany targeting an unmapped class: " + path + "[" + collType + "]");
/* 1060:     */       }
/* 1061:1164 */       if (anyAnn != null)
/* 1062:     */       {
/* 1063:1165 */         if (parentPropertyHolder.getJoinTable(property) == null)
/* 1064:     */         {
/* 1065:1166 */           String path = collValue.getOwnerEntityName() + "." + joinColumns[0].getPropertyName();
/* 1066:1167 */           throw new AnnotationException("@JoinTable is mandatory when @ManyToAny is used: " + path);
/* 1067:     */         }
/* 1068:     */       }
/* 1069:     */       else
/* 1070:     */       {
/* 1071:1173 */         JoinTable joinTableAnn = parentPropertyHolder.getJoinTable(property);
/* 1072:1174 */         if ((joinTableAnn != null) && (joinTableAnn.inverseJoinColumns().length > 0))
/* 1073:     */         {
/* 1074:1175 */           String path = collValue.getOwnerEntityName() + "." + joinColumns[0].getPropertyName();
/* 1075:1176 */           throw new AnnotationException("Use of @JoinTable.inverseJoinColumns targeting an unmapped class: " + path + "[" + collType + "]");
/* 1076:     */         }
/* 1077:     */       }
/* 1078:     */     }
/* 1079:1183 */     boolean mappedBy = !BinderHelper.isEmptyAnnotationValue(joinColumns[0].getMappedBy());
/* 1080:1184 */     if (mappedBy)
/* 1081:     */     {
/* 1082:1185 */       if (!isCollectionOfEntities)
/* 1083:     */       {
/* 1084:1186 */         StringBuilder error = new StringBuilder(80).append("Collection of elements must not have mappedBy or association reference an unmapped entity: ").append(collValue.getOwnerEntityName()).append(".").append(joinColumns[0].getPropertyName());
/* 1085:     */         
/* 1086:     */ 
/* 1087:     */ 
/* 1088:     */ 
/* 1089:     */ 
/* 1090:     */ 
/* 1091:1193 */         throw new AnnotationException(error.toString());
/* 1092:     */       }
/* 1093:     */       Property otherSideProperty;
/* 1094:     */       try
/* 1095:     */       {
/* 1096:1197 */         otherSideProperty = collectionEntity.getRecursiveProperty(joinColumns[0].getMappedBy());
/* 1097:     */       }
/* 1098:     */       catch (MappingException e)
/* 1099:     */       {
/* 1100:1200 */         StringBuilder error = new StringBuilder(80);
/* 1101:1201 */         error.append("mappedBy reference an unknown target entity property: ").append(collType).append(".").append(joinColumns[0].getMappedBy()).append(" in ").append(collValue.getOwnerEntityName()).append(".").append(joinColumns[0].getPropertyName());
/* 1102:     */         
/* 1103:     */ 
/* 1104:     */ 
/* 1105:     */ 
/* 1106:     */ 
/* 1107:1207 */         throw new AnnotationException(error.toString());
/* 1108:     */       }
/* 1109:     */       Table table;
/* 1110:     */       Table table;
/* 1111:1210 */       if ((otherSideProperty.getValue() instanceof org.hibernate.mapping.Collection)) {
/* 1112:1212 */         table = ((org.hibernate.mapping.Collection)otherSideProperty.getValue()).getCollectionTable();
/* 1113:     */       } else {
/* 1114:1216 */         table = otherSideProperty.getValue().getTable();
/* 1115:     */       }
/* 1116:1218 */       collValue.setCollectionTable(table);
/* 1117:1219 */       String entityName = collectionEntity.getEntityName();
/* 1118:1220 */       for (Ejb3JoinColumn column : joinColumns) {
/* 1119:1222 */         column.setManyToManyOwnerSideEntityName(entityName);
/* 1120:     */       }
/* 1121:     */     }
/* 1122:     */     else
/* 1123:     */     {
/* 1124:1228 */       for (Ejb3JoinColumn column : joinColumns)
/* 1125:     */       {
/* 1126:1229 */         String mappedByProperty = mappings.getFromMappedBy(collValue.getOwnerEntityName(), column.getPropertyName());
/* 1127:     */         
/* 1128:     */ 
/* 1129:1232 */         Table ownerTable = collValue.getOwner().getTable();
/* 1130:1233 */         column.setMappedBy(collValue.getOwner().getEntityName(), mappings.getLogicalTableName(ownerTable), mappedByProperty);
/* 1131:     */       }
/* 1132:1240 */       if (StringHelper.isEmpty(associationTableBinder.getName())) {
/* 1133:1242 */         associationTableBinder.setDefaultName(collValue.getOwner().getEntityName(), mappings.getLogicalTableName(collValue.getOwner().getTable()), collectionEntity != null ? collectionEntity.getEntityName() : null, collectionEntity != null ? mappings.getLogicalTableName(collectionEntity.getTable()) : null, joinColumns[0].getPropertyName());
/* 1134:     */       }
/* 1135:1250 */       associationTableBinder.setJPA2ElementCollection((!isCollectionOfEntities) && (property.isAnnotationPresent(ElementCollection.class)));
/* 1136:1251 */       collValue.setCollectionTable(associationTableBinder.bind());
/* 1137:     */     }
/* 1138:1253 */     bindFilters(isCollectionOfEntities);
/* 1139:1254 */     bindCollectionSecondPass(collValue, collectionEntity, joinColumns, cascadeDeleteEnabled, property, mappings);
/* 1140:     */     
/* 1141:1256 */     ManyToOne element = null;
/* 1142:1257 */     if (isCollectionOfEntities)
/* 1143:     */     {
/* 1144:1258 */       element = new ManyToOne(mappings, collValue.getCollectionTable());
/* 1145:     */       
/* 1146:1260 */       collValue.setElement(element);
/* 1147:1261 */       element.setReferencedEntityName(collType.getName());
/* 1148:     */       
/* 1149:     */ 
/* 1150:     */ 
/* 1151:1265 */       element.setFetchMode(org.hibernate.FetchMode.JOIN);
/* 1152:1266 */       element.setLazy(false);
/* 1153:1267 */       element.setIgnoreNotFound(ignoreNotFound);
/* 1154:1269 */       if (hqlOrderBy != null) {
/* 1155:1270 */         collValue.setManyToManyOrdering(buildOrderByClauseFromHql(hqlOrderBy, collectionEntity, collValue.getRole()));
/* 1156:     */       }
/* 1157:1274 */       ForeignKey fk = property != null ? (ForeignKey)property.getAnnotation(ForeignKey.class) : null;
/* 1158:1275 */       String fkName = fk != null ? fk.inverseName() : "";
/* 1159:1276 */       if (!BinderHelper.isEmptyAnnotationValue(fkName)) {
/* 1160:1276 */         element.setForeignKeyName(fkName);
/* 1161:     */       }
/* 1162:     */     }
/* 1163:1278 */     else if (anyAnn != null)
/* 1164:     */     {
/* 1165:1281 */       PropertyData inferredData = new PropertyInferredData(null, property, "unsupported", mappings.getReflectionManager());
/* 1166:1283 */       for (Ejb3Column column : inverseJoinColumns) {
/* 1167:1284 */         column.setTable(collValue.getCollectionTable());
/* 1168:     */       }
/* 1169:1286 */       Any any = BinderHelper.buildAnyValue(anyAnn.metaDef(), inverseJoinColumns, anyAnn.metaColumn(), inferredData, cascadeDeleteEnabled, Nullability.NO_CONSTRAINT, this.propertyHolder, new EntityBinder(), true, mappings);
/* 1170:     */       
/* 1171:     */ 
/* 1172:1289 */       collValue.setElement(any);
/* 1173:     */     }
/* 1174:     */     else
/* 1175:     */     {
/* 1176:1295 */       PropertyHolder holder = null;
/* 1177:     */       XClass elementClass;
/* 1178:     */       XClass elementClass;
/* 1179:     */       AnnotatedClassType classType;
/* 1180:1296 */       if (BinderHelper.PRIMITIVE_NAMES.contains(collType.getName()))
/* 1181:     */       {
/* 1182:1297 */         AnnotatedClassType classType = AnnotatedClassType.NONE;
/* 1183:1298 */         elementClass = null;
/* 1184:     */       }
/* 1185:     */       else
/* 1186:     */       {
/* 1187:1301 */         elementClass = collType;
/* 1188:1302 */         classType = mappings.getClassType(elementClass);
/* 1189:     */         
/* 1190:1304 */         holder = PropertyHolderBuilder.buildPropertyHolder(collValue, collValue.getRole(), elementClass, property, parentPropertyHolder, mappings);
/* 1191:     */         
/* 1192:     */ 
/* 1193:     */ 
/* 1194:     */ 
/* 1195:     */ 
/* 1196:     */ 
/* 1197:1311 */         boolean attributeOverride = (property.isAnnotationPresent(AttributeOverride.class)) || (property.isAnnotationPresent(AttributeOverrides.class));
/* 1198:1313 */         if ((isEmbedded) || (attributeOverride)) {
/* 1199:1314 */           classType = AnnotatedClassType.EMBEDDABLE;
/* 1200:     */         }
/* 1201:     */       }
/* 1202:1318 */       if (AnnotatedClassType.EMBEDDABLE.equals(classType))
/* 1203:     */       {
/* 1204:1319 */         EntityBinder entityBinder = new EntityBinder();
/* 1205:1320 */         PersistentClass owner = collValue.getOwner();
/* 1206:     */         boolean isPropertyAnnotated;
/* 1207:1324 */         if (owner.getIdentifierProperty() != null)
/* 1208:     */         {
/* 1209:1325 */           isPropertyAnnotated = owner.getIdentifierProperty().getPropertyAccessorName().equals("property");
/* 1210:     */         }
/* 1211:     */         else
/* 1212:     */         {
/* 1213:     */           boolean isPropertyAnnotated;
/* 1214:1327 */           if ((owner.getIdentifierMapper() != null) && (owner.getIdentifierMapper().getPropertySpan() > 0))
/* 1215:     */           {
/* 1216:1328 */             Property prop = (Property)owner.getIdentifierMapper().getPropertyIterator().next();
/* 1217:1329 */             isPropertyAnnotated = prop.getPropertyAccessorName().equals("property");
/* 1218:     */           }
/* 1219:     */           else
/* 1220:     */           {
/* 1221:1332 */             throw new AssertionFailure("Unable to guess collection property accessor name");
/* 1222:     */           }
/* 1223:     */         }
/* 1224:     */         boolean isPropertyAnnotated;
/* 1225:     */         PropertyData inferredData;
/* 1226:     */         PropertyData inferredData;
/* 1227:1336 */         if (isMap())
/* 1228:     */         {
/* 1229:     */           PropertyData inferredData;
/* 1230:1338 */           if (isHibernateExtensionMapping()) {
/* 1231:1339 */             inferredData = new PropertyPreloadedData(AccessType.PROPERTY, "element", elementClass);
/* 1232:     */           } else {
/* 1233:1342 */             inferredData = new PropertyPreloadedData(AccessType.PROPERTY, "value", elementClass);
/* 1234:     */           }
/* 1235:     */         }
/* 1236:     */         else
/* 1237:     */         {
/* 1238:     */           PropertyData inferredData;
/* 1239:1346 */           if (isHibernateExtensionMapping()) {
/* 1240:1347 */             inferredData = new PropertyPreloadedData(AccessType.PROPERTY, "element", elementClass);
/* 1241:     */           } else {
/* 1242:1351 */             inferredData = new PropertyPreloadedData(AccessType.PROPERTY, "collection&&element", elementClass);
/* 1243:     */           }
/* 1244:     */         }
/* 1245:1355 */         Component component = AnnotationBinder.fillComponent(holder, inferredData, isPropertyAnnotated ? AccessType.PROPERTY : AccessType.FIELD, true, entityBinder, false, false, true, mappings, this.inheritanceStatePerClass);
/* 1246:     */         
/* 1247:     */ 
/* 1248:     */ 
/* 1249:     */ 
/* 1250:     */ 
/* 1251:1361 */         collValue.setElement(component);
/* 1252:1363 */         if (StringHelper.isNotEmpty(hqlOrderBy))
/* 1253:     */         {
/* 1254:1364 */           String path = collValue.getOwnerEntityName() + "." + joinColumns[0].getPropertyName();
/* 1255:1365 */           String orderBy = buildOrderByClauseFromHql(hqlOrderBy, component, path);
/* 1256:1366 */           if (orderBy != null) {
/* 1257:1367 */             collValue.setOrderBy(orderBy);
/* 1258:     */           }
/* 1259:     */         }
/* 1260:     */       }
/* 1261:     */       else
/* 1262:     */       {
/* 1263:1372 */         SimpleValueBinder elementBinder = new SimpleValueBinder();
/* 1264:1373 */         elementBinder.setMappings(mappings);
/* 1265:1374 */         elementBinder.setReturnedClassName(collType.getName());
/* 1266:1375 */         if ((elementColumns == null) || (elementColumns.length == 0))
/* 1267:     */         {
/* 1268:1376 */           elementColumns = new Ejb3Column[1];
/* 1269:1377 */           Ejb3Column column = new Ejb3Column();
/* 1270:1378 */           column.setImplicit(false);
/* 1271:     */           
/* 1272:1380 */           column.setNullable(true);
/* 1273:1381 */           column.setLength(255);
/* 1274:1382 */           column.setLogicalColumnName("elt");
/* 1275:     */           
/* 1276:1384 */           column.setJoins(new HashMap());
/* 1277:1385 */           column.setMappings(mappings);
/* 1278:1386 */           column.bind();
/* 1279:1387 */           elementColumns[0] = column;
/* 1280:     */         }
/* 1281:1390 */         for (Ejb3Column column : elementColumns) {
/* 1282:1391 */           column.setTable(collValue.getCollectionTable());
/* 1283:     */         }
/* 1284:1393 */         elementBinder.setColumns(elementColumns);
/* 1285:1394 */         elementBinder.setType(property, elementClass);
/* 1286:1395 */         collValue.setElement(elementBinder.make());
/* 1287:     */       }
/* 1288:     */     }
/* 1289:1399 */     checkFilterConditions(collValue);
/* 1290:1402 */     if (isCollectionOfEntities) {
/* 1291:1403 */       bindManytoManyInverseFk(collectionEntity, inverseJoinColumns, element, unique, mappings);
/* 1292:     */     }
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   private static void checkFilterConditions(org.hibernate.mapping.Collection collValue)
/* 1296:     */   {
/* 1297:1410 */     if (((collValue.getFilterMap().size() != 0) || (StringHelper.isNotEmpty(collValue.getWhere()))) && (collValue.getFetchMode() == org.hibernate.FetchMode.JOIN) && (!(collValue.getElement() instanceof SimpleValue)) && (collValue.getElement().getFetchMode() != org.hibernate.FetchMode.JOIN)) {
/* 1298:1414 */       throw new MappingException("@ManyToMany or @CollectionOfElements defining filter or where without join fetching not valid within collection using join fetching[" + collValue.getRole() + "]");
/* 1299:     */     }
/* 1300:     */   }
/* 1301:     */   
/* 1302:     */   private static void bindCollectionSecondPass(org.hibernate.mapping.Collection collValue, PersistentClass collectionEntity, Ejb3JoinColumn[] joinColumns, boolean cascadeDeleteEnabled, XProperty property, Mappings mappings)
/* 1303:     */   {
/* 1304:1428 */     BinderHelper.createSyntheticPropertyReference(joinColumns, collValue.getOwner(), collectionEntity, collValue, false, mappings);
/* 1305:     */     
/* 1306:     */ 
/* 1307:1431 */     SimpleValue key = buildCollectionKey(collValue, joinColumns, cascadeDeleteEnabled, property, mappings);
/* 1308:1432 */     if ((property.isAnnotationPresent(ElementCollection.class)) && (joinColumns.length > 0)) {
/* 1309:1433 */       joinColumns[0].setJPA2ElementCollection(true);
/* 1310:     */     }
/* 1311:1435 */     TableBinder.bindFk(collValue.getOwner(), collectionEntity, joinColumns, key, false, mappings);
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   public void setCascadeDeleteEnabled(boolean onDeleteCascade)
/* 1315:     */   {
/* 1316:1439 */     this.cascadeDeleteEnabled = onDeleteCascade;
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   private String safeCollectionRole()
/* 1320:     */   {
/* 1321:1443 */     if (this.propertyHolder != null) {
/* 1322:1444 */       return this.propertyHolder.getEntityName() + "." + this.propertyName;
/* 1323:     */     }
/* 1324:1447 */     return "";
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public static void bindManytoManyInverseFk(PersistentClass referencedEntity, Ejb3JoinColumn[] columns, SimpleValue value, boolean unique, Mappings mappings)
/* 1328:     */   {
/* 1329:1464 */     String mappedBy = columns[0].getMappedBy();
/* 1330:1465 */     if (StringHelper.isNotEmpty(mappedBy))
/* 1331:     */     {
/* 1332:1466 */       Property property = referencedEntity.getRecursiveProperty(mappedBy);
/* 1333:     */       Iterator mappedByColumns;
/* 1334:     */       Iterator mappedByColumns;
/* 1335:1468 */       if ((property.getValue() instanceof org.hibernate.mapping.Collection))
/* 1336:     */       {
/* 1337:1469 */         mappedByColumns = ((org.hibernate.mapping.Collection)property.getValue()).getKey().getColumnIterator();
/* 1338:     */       }
/* 1339:     */       else
/* 1340:     */       {
/* 1341:1473 */         Iterator joinsIt = referencedEntity.getJoinIterator();
/* 1342:1474 */         KeyValue key = null;
/* 1343:1475 */         while (joinsIt.hasNext())
/* 1344:     */         {
/* 1345:1476 */           Join join = (Join)joinsIt.next();
/* 1346:1477 */           if (join.containsProperty(property))
/* 1347:     */           {
/* 1348:1478 */             key = join.getKey();
/* 1349:1479 */             break;
/* 1350:     */           }
/* 1351:     */         }
/* 1352:1482 */         if (key == null) {
/* 1353:1482 */           key = property.getPersistentClass().getIdentifier();
/* 1354:     */         }
/* 1355:1483 */         mappedByColumns = key.getColumnIterator();
/* 1356:     */       }
/* 1357:1485 */       while (mappedByColumns.hasNext())
/* 1358:     */       {
/* 1359:1486 */         Column column = (Column)mappedByColumns.next();
/* 1360:1487 */         columns[0].linkValueUsingAColumnCopy(column, value);
/* 1361:     */       }
/* 1362:1489 */       String referencedPropertyName = mappings.getPropertyReferencedAssociation("inverse__" + referencedEntity.getEntityName(), mappedBy);
/* 1363:1493 */       if (referencedPropertyName != null)
/* 1364:     */       {
/* 1365:1495 */         ((ManyToOne)value).setReferencedPropertyName(referencedPropertyName);
/* 1366:1496 */         mappings.addUniquePropertyReference(referencedEntity.getEntityName(), referencedPropertyName);
/* 1367:     */       }
/* 1368:1498 */       value.createForeignKey();
/* 1369:     */     }
/* 1370:     */     else
/* 1371:     */     {
/* 1372:1501 */       BinderHelper.createSyntheticPropertyReference(columns, referencedEntity, null, value, true, mappings);
/* 1373:1502 */       TableBinder.bindFk(referencedEntity, null, columns, value, unique, mappings);
/* 1374:     */     }
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   public void setFkJoinColumns(Ejb3JoinColumn[] ejb3JoinColumns)
/* 1378:     */   {
/* 1379:1507 */     this.fkJoinColumns = ejb3JoinColumns;
/* 1380:     */   }
/* 1381:     */   
/* 1382:     */   public void setExplicitAssociationTable(boolean explicitAssocTable)
/* 1383:     */   {
/* 1384:1511 */     this.isExplicitAssociationTable = explicitAssocTable;
/* 1385:     */   }
/* 1386:     */   
/* 1387:     */   public void setElementColumns(Ejb3Column[] elementColumns)
/* 1388:     */   {
/* 1389:1515 */     this.elementColumns = elementColumns;
/* 1390:     */   }
/* 1391:     */   
/* 1392:     */   public void setEmbedded(boolean annotationPresent)
/* 1393:     */   {
/* 1394:1519 */     this.isEmbedded = annotationPresent;
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   public void setProperty(XProperty property)
/* 1398:     */   {
/* 1399:1523 */     this.property = property;
/* 1400:     */   }
/* 1401:     */   
/* 1402:     */   public void setIgnoreNotFound(boolean ignoreNotFound)
/* 1403:     */   {
/* 1404:1527 */     this.ignoreNotFound = ignoreNotFound;
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   public void setMapKeyColumns(Ejb3Column[] mapKeyColumns)
/* 1408:     */   {
/* 1409:1531 */     this.mapKeyColumns = mapKeyColumns;
/* 1410:     */   }
/* 1411:     */   
/* 1412:     */   public void setMapKeyManyToManyColumns(Ejb3JoinColumn[] mapJoinColumns)
/* 1413:     */   {
/* 1414:1535 */     this.mapKeyManyToManyColumns = mapJoinColumns;
/* 1415:     */   }
/* 1416:     */   
/* 1417:     */   public void setLocalGenerators(HashMap<String, IdGenerator> localGenerators)
/* 1418:     */   {
/* 1419:1539 */     this.localGenerators = localGenerators;
/* 1420:     */   }
/* 1421:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.CollectionBinder
 * JD-Core Version:    0.7.0.1
 */