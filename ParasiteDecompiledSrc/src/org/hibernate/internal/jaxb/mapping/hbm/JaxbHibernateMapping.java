/*    1:     */ package org.hibernate.internal.jaxb.mapping.hbm;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.List;
/*    6:     */ import javax.xml.bind.JAXBElement;
/*    7:     */ import javax.xml.bind.annotation.XmlAccessType;
/*    8:     */ import javax.xml.bind.annotation.XmlAccessorType;
/*    9:     */ import javax.xml.bind.annotation.XmlAttribute;
/*   10:     */ import javax.xml.bind.annotation.XmlElement;
/*   11:     */ import javax.xml.bind.annotation.XmlElementRef;
/*   12:     */ import javax.xml.bind.annotation.XmlElements;
/*   13:     */ import javax.xml.bind.annotation.XmlMixed;
/*   14:     */ import javax.xml.bind.annotation.XmlRootElement;
/*   15:     */ import javax.xml.bind.annotation.XmlType;
/*   16:     */ import javax.xml.bind.annotation.XmlValue;
/*   17:     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*   18:     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*   19:     */ 
/*   20:     */ @XmlAccessorType(XmlAccessType.FIELD)
/*   21:     */ @XmlType(name="", propOrder={"meta", "identifierGenerator", "typedef", "filterDef", "_import", "clazzOrSubclassOrJoinedSubclass", "resultset", "queryOrSqlQuery", "fetchProfile", "databaseObject"})
/*   22:     */ @XmlRootElement(name="hibernate-mapping")
/*   23:     */ public class JaxbHibernateMapping
/*   24:     */ {
/*   25:     */   protected List<JaxbMetaElement> meta;
/*   26:     */   @XmlElement(name="identifier-generator")
/*   27:     */   protected List<JaxbIdentifierGenerator> identifierGenerator;
/*   28:     */   protected List<JaxbTypedef> typedef;
/*   29:     */   @XmlElement(name="filter-def")
/*   30:     */   protected List<JaxbFilterDef> filterDef;
/*   31:     */   @XmlElement(name="import")
/*   32:     */   protected List<JaxbImport> _import;
/*   33:     */   @XmlElements({@XmlElement(name="union-subclass", type=JaxbUnionSubclassElement.class), @XmlElement(name="class", type=JaxbClass.class), @XmlElement(name="joined-subclass", type=JaxbJoinedSubclassElement.class), @XmlElement(name="subclass", type=JaxbSubclassElement.class)})
/*   34:     */   protected List<Object> clazzOrSubclassOrJoinedSubclass;
/*   35:     */   protected List<JaxbResultsetElement> resultset;
/*   36:     */   @XmlElements({@XmlElement(name="query", type=JaxbQueryElement.class), @XmlElement(name="sql-query", type=JaxbSqlQueryElement.class)})
/*   37:     */   protected List<Object> queryOrSqlQuery;
/*   38:     */   @XmlElement(name="fetch-profile")
/*   39:     */   protected List<JaxbFetchProfileElement> fetchProfile;
/*   40:     */   @XmlElement(name="database-object")
/*   41:     */   protected List<JaxbDatabaseObject> databaseObject;
/*   42:     */   @XmlAttribute(name="auto-import")
/*   43:     */   protected Boolean autoImport;
/*   44:     */   @XmlAttribute
/*   45:     */   protected String catalog;
/*   46:     */   @XmlAttribute(name="default-access")
/*   47:     */   protected String defaultAccess;
/*   48:     */   @XmlAttribute(name="default-cascade")
/*   49:     */   protected String defaultCascade;
/*   50:     */   @XmlAttribute(name="default-lazy")
/*   51:     */   protected Boolean defaultLazy;
/*   52:     */   @XmlAttribute(name="package")
/*   53:     */   protected String _package;
/*   54:     */   @XmlAttribute
/*   55:     */   protected String schema;
/*   56:     */   
/*   57:     */   public List<JaxbMetaElement> getMeta()
/*   58:     */   {
/*   59: 477 */     if (this.meta == null) {
/*   60: 478 */       this.meta = new ArrayList();
/*   61:     */     }
/*   62: 480 */     return this.meta;
/*   63:     */   }
/*   64:     */   
/*   65:     */   public List<JaxbIdentifierGenerator> getIdentifierGenerator()
/*   66:     */   {
/*   67: 506 */     if (this.identifierGenerator == null) {
/*   68: 507 */       this.identifierGenerator = new ArrayList();
/*   69:     */     }
/*   70: 509 */     return this.identifierGenerator;
/*   71:     */   }
/*   72:     */   
/*   73:     */   public List<JaxbTypedef> getTypedef()
/*   74:     */   {
/*   75: 535 */     if (this.typedef == null) {
/*   76: 536 */       this.typedef = new ArrayList();
/*   77:     */     }
/*   78: 538 */     return this.typedef;
/*   79:     */   }
/*   80:     */   
/*   81:     */   public List<JaxbFilterDef> getFilterDef()
/*   82:     */   {
/*   83: 564 */     if (this.filterDef == null) {
/*   84: 565 */       this.filterDef = new ArrayList();
/*   85:     */     }
/*   86: 567 */     return this.filterDef;
/*   87:     */   }
/*   88:     */   
/*   89:     */   public List<JaxbImport> getImport()
/*   90:     */   {
/*   91: 593 */     if (this._import == null) {
/*   92: 594 */       this._import = new ArrayList();
/*   93:     */     }
/*   94: 596 */     return this._import;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public List<Object> getClazzOrSubclassOrJoinedSubclass()
/*   98:     */   {
/*   99: 625 */     if (this.clazzOrSubclassOrJoinedSubclass == null) {
/*  100: 626 */       this.clazzOrSubclassOrJoinedSubclass = new ArrayList();
/*  101:     */     }
/*  102: 628 */     return this.clazzOrSubclassOrJoinedSubclass;
/*  103:     */   }
/*  104:     */   
/*  105:     */   public List<JaxbResultsetElement> getResultset()
/*  106:     */   {
/*  107: 654 */     if (this.resultset == null) {
/*  108: 655 */       this.resultset = new ArrayList();
/*  109:     */     }
/*  110: 657 */     return this.resultset;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public List<Object> getQueryOrSqlQuery()
/*  114:     */   {
/*  115: 684 */     if (this.queryOrSqlQuery == null) {
/*  116: 685 */       this.queryOrSqlQuery = new ArrayList();
/*  117:     */     }
/*  118: 687 */     return this.queryOrSqlQuery;
/*  119:     */   }
/*  120:     */   
/*  121:     */   public List<JaxbFetchProfileElement> getFetchProfile()
/*  122:     */   {
/*  123: 713 */     if (this.fetchProfile == null) {
/*  124: 714 */       this.fetchProfile = new ArrayList();
/*  125:     */     }
/*  126: 716 */     return this.fetchProfile;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public List<JaxbDatabaseObject> getDatabaseObject()
/*  130:     */   {
/*  131: 742 */     if (this.databaseObject == null) {
/*  132: 743 */       this.databaseObject = new ArrayList();
/*  133:     */     }
/*  134: 745 */     return this.databaseObject;
/*  135:     */   }
/*  136:     */   
/*  137:     */   public boolean isAutoImport()
/*  138:     */   {
/*  139: 757 */     if (this.autoImport == null) {
/*  140: 758 */       return true;
/*  141:     */     }
/*  142: 760 */     return this.autoImport.booleanValue();
/*  143:     */   }
/*  144:     */   
/*  145:     */   public void setAutoImport(Boolean value)
/*  146:     */   {
/*  147: 773 */     this.autoImport = value;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public String getCatalog()
/*  151:     */   {
/*  152: 785 */     return this.catalog;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void setCatalog(String value)
/*  156:     */   {
/*  157: 797 */     this.catalog = value;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public String getDefaultAccess()
/*  161:     */   {
/*  162: 809 */     if (this.defaultAccess == null) {
/*  163: 810 */       return "property";
/*  164:     */     }
/*  165: 812 */     return this.defaultAccess;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public void setDefaultAccess(String value)
/*  169:     */   {
/*  170: 825 */     this.defaultAccess = value;
/*  171:     */   }
/*  172:     */   
/*  173:     */   public String getDefaultCascade()
/*  174:     */   {
/*  175: 837 */     if (this.defaultCascade == null) {
/*  176: 838 */       return "none";
/*  177:     */     }
/*  178: 840 */     return this.defaultCascade;
/*  179:     */   }
/*  180:     */   
/*  181:     */   public void setDefaultCascade(String value)
/*  182:     */   {
/*  183: 853 */     this.defaultCascade = value;
/*  184:     */   }
/*  185:     */   
/*  186:     */   public boolean isDefaultLazy()
/*  187:     */   {
/*  188: 865 */     if (this.defaultLazy == null) {
/*  189: 866 */       return true;
/*  190:     */     }
/*  191: 868 */     return this.defaultLazy.booleanValue();
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void setDefaultLazy(Boolean value)
/*  195:     */   {
/*  196: 881 */     this.defaultLazy = value;
/*  197:     */   }
/*  198:     */   
/*  199:     */   public String getPackage()
/*  200:     */   {
/*  201: 893 */     return this._package;
/*  202:     */   }
/*  203:     */   
/*  204:     */   public void setPackage(String value)
/*  205:     */   {
/*  206: 905 */     this._package = value;
/*  207:     */   }
/*  208:     */   
/*  209:     */   public String getSchema()
/*  210:     */   {
/*  211: 917 */     return this.schema;
/*  212:     */   }
/*  213:     */   
/*  214:     */   public void setSchema(String value)
/*  215:     */   {
/*  216: 929 */     this.schema = value;
/*  217:     */   }
/*  218:     */   
/*  219:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  220:     */   @XmlType(name="", propOrder={"meta", "subselect", "cache", "synchronize", "comment", "tuplizer", "id", "compositeId", "discriminator", "naturalId", "version", "timestamp", "propertyOrManyToOneOrOneToOne", "join", "subclass", "joinedSubclass", "unionSubclass", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "filter", "fetchProfile", "resultset", "queryOrSqlQuery"})
/*  221:     */   public static class JaxbClass
/*  222:     */     implements EntityElement, JoinElementSource
/*  223:     */   {
/*  224:     */     protected List<JaxbMetaElement> meta;
/*  225:     */     protected String subselect;
/*  226:     */     protected JaxbCacheElement cache;
/*  227:     */     protected List<JaxbSynchronizeElement> synchronize;
/*  228:     */     protected String comment;
/*  229:     */     protected List<JaxbTuplizerElement> tuplizer;
/*  230:     */     protected JaxbId id;
/*  231:     */     @XmlElement(name="composite-id")
/*  232:     */     protected JaxbCompositeId compositeId;
/*  233:     */     protected JaxbDiscriminator discriminator;
/*  234:     */     @XmlElement(name="natural-id")
/*  235:     */     protected JaxbNaturalId naturalId;
/*  236:     */     protected JaxbVersion version;
/*  237:     */     protected JaxbTimestamp timestamp;
/*  238:     */     @XmlElements({@XmlElement(name="properties", type=JaxbPropertiesElement.class), @XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @XmlElement(name="set", type=JaxbSetElement.class), @XmlElement(name="one-to-one", type=JaxbOneToOneElement.class), @XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class), @XmlElement(name="any", type=JaxbAnyElement.class), @XmlElement(name="component", type=JaxbComponentElement.class), @XmlElement(name="idbag", type=JaxbIdbagElement.class), @XmlElement(name="primitive-array", type=JaxbPrimitiveArrayElement.class), @XmlElement(name="property", type=JaxbPropertyElement.class), @XmlElement(name="list", type=JaxbListElement.class), @XmlElement(name="bag", type=JaxbBagElement.class), @XmlElement(name="map", type=JaxbMapElement.class), @XmlElement(name="array", type=JaxbArrayElement.class)})
/*  239:     */     protected List<Object> propertyOrManyToOneOrOneToOne;
/*  240:     */     protected List<JaxbJoinElement> join;
/*  241:     */     protected List<JaxbSubclassElement> subclass;
/*  242:     */     @XmlElement(name="joined-subclass")
/*  243:     */     protected List<JaxbJoinedSubclassElement> joinedSubclass;
/*  244:     */     @XmlElement(name="union-subclass")
/*  245:     */     protected List<JaxbUnionSubclassElement> unionSubclass;
/*  246:     */     protected JaxbLoaderElement loader;
/*  247:     */     @XmlElement(name="sql-insert")
/*  248:     */     protected JaxbSqlInsertElement sqlInsert;
/*  249:     */     @XmlElement(name="sql-update")
/*  250:     */     protected JaxbSqlUpdateElement sqlUpdate;
/*  251:     */     @XmlElement(name="sql-delete")
/*  252:     */     protected JaxbSqlDeleteElement sqlDelete;
/*  253:     */     protected List<JaxbFilterElement> filter;
/*  254:     */     @XmlElement(name="fetch-profile")
/*  255:     */     protected List<JaxbFetchProfileElement> fetchProfile;
/*  256:     */     protected List<JaxbResultsetElement> resultset;
/*  257:     */     @XmlElements({@XmlElement(name="query", type=JaxbQueryElement.class), @XmlElement(name="sql-query", type=JaxbSqlQueryElement.class)})
/*  258:     */     protected List<Object> queryOrSqlQuery;
/*  259:     */     @XmlAttribute(name="abstract")
/*  260:     */     protected Boolean _abstract;
/*  261:     */     @XmlAttribute(name="batch-size")
/*  262:     */     protected String batchSize;
/*  263:     */     @XmlAttribute
/*  264:     */     protected String catalog;
/*  265:     */     @XmlAttribute
/*  266:     */     protected String check;
/*  267:     */     @XmlAttribute(name="discriminator-value")
/*  268:     */     protected String discriminatorValue;
/*  269:     */     @XmlAttribute(name="dynamic-insert")
/*  270:     */     protected Boolean dynamicInsert;
/*  271:     */     @XmlAttribute(name="dynamic-update")
/*  272:     */     protected Boolean dynamicUpdate;
/*  273:     */     @XmlAttribute(name="entity-name")
/*  274:     */     protected String entityName;
/*  275:     */     @XmlAttribute
/*  276:     */     protected Boolean lazy;
/*  277:     */     @XmlAttribute
/*  278:     */     protected Boolean mutable;
/*  279:     */     @XmlAttribute
/*  280:     */     protected String name;
/*  281:     */     @XmlAttribute
/*  282:     */     protected String node;
/*  283:     */     @XmlAttribute(name="optimistic-lock")
/*  284:     */     @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  285:     */     protected String optimisticLock;
/*  286:     */     @XmlAttribute
/*  287:     */     protected String persister;
/*  288:     */     @XmlAttribute
/*  289:     */     @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  290:     */     protected String polymorphism;
/*  291:     */     @XmlAttribute
/*  292:     */     protected String proxy;
/*  293:     */     @XmlAttribute
/*  294:     */     protected String rowid;
/*  295:     */     @XmlAttribute
/*  296:     */     protected String schema;
/*  297:     */     @XmlAttribute(name="select-before-update")
/*  298:     */     protected Boolean selectBeforeUpdate;
/*  299:     */     @XmlAttribute(name="subselect")
/*  300:     */     protected String subselectAttribute;
/*  301:     */     @XmlAttribute
/*  302:     */     protected String table;
/*  303:     */     @XmlAttribute
/*  304:     */     protected String where;
/*  305:     */     
/*  306:     */     public List<JaxbMetaElement> getMeta()
/*  307:     */     {
/*  308:1338 */       if (this.meta == null) {
/*  309:1339 */         this.meta = new ArrayList();
/*  310:     */       }
/*  311:1341 */       return this.meta;
/*  312:     */     }
/*  313:     */     
/*  314:     */     public String getSubselect()
/*  315:     */     {
/*  316:1353 */       return this.subselect;
/*  317:     */     }
/*  318:     */     
/*  319:     */     public void setSubselect(String value)
/*  320:     */     {
/*  321:1365 */       this.subselect = value;
/*  322:     */     }
/*  323:     */     
/*  324:     */     public JaxbCacheElement getCache()
/*  325:     */     {
/*  326:1377 */       return this.cache;
/*  327:     */     }
/*  328:     */     
/*  329:     */     public void setCache(JaxbCacheElement value)
/*  330:     */     {
/*  331:1389 */       this.cache = value;
/*  332:     */     }
/*  333:     */     
/*  334:     */     public List<JaxbSynchronizeElement> getSynchronize()
/*  335:     */     {
/*  336:1415 */       if (this.synchronize == null) {
/*  337:1416 */         this.synchronize = new ArrayList();
/*  338:     */       }
/*  339:1418 */       return this.synchronize;
/*  340:     */     }
/*  341:     */     
/*  342:     */     public String getComment()
/*  343:     */     {
/*  344:1430 */       return this.comment;
/*  345:     */     }
/*  346:     */     
/*  347:     */     public void setComment(String value)
/*  348:     */     {
/*  349:1442 */       this.comment = value;
/*  350:     */     }
/*  351:     */     
/*  352:     */     public List<JaxbTuplizerElement> getTuplizer()
/*  353:     */     {
/*  354:1468 */       if (this.tuplizer == null) {
/*  355:1469 */         this.tuplizer = new ArrayList();
/*  356:     */       }
/*  357:1471 */       return this.tuplizer;
/*  358:     */     }
/*  359:     */     
/*  360:     */     public JaxbId getId()
/*  361:     */     {
/*  362:1483 */       return this.id;
/*  363:     */     }
/*  364:     */     
/*  365:     */     public void setId(JaxbId value)
/*  366:     */     {
/*  367:1495 */       this.id = value;
/*  368:     */     }
/*  369:     */     
/*  370:     */     public JaxbCompositeId getCompositeId()
/*  371:     */     {
/*  372:1507 */       return this.compositeId;
/*  373:     */     }
/*  374:     */     
/*  375:     */     public void setCompositeId(JaxbCompositeId value)
/*  376:     */     {
/*  377:1519 */       this.compositeId = value;
/*  378:     */     }
/*  379:     */     
/*  380:     */     public JaxbDiscriminator getDiscriminator()
/*  381:     */     {
/*  382:1531 */       return this.discriminator;
/*  383:     */     }
/*  384:     */     
/*  385:     */     public void setDiscriminator(JaxbDiscriminator value)
/*  386:     */     {
/*  387:1543 */       this.discriminator = value;
/*  388:     */     }
/*  389:     */     
/*  390:     */     public JaxbNaturalId getNaturalId()
/*  391:     */     {
/*  392:1555 */       return this.naturalId;
/*  393:     */     }
/*  394:     */     
/*  395:     */     public void setNaturalId(JaxbNaturalId value)
/*  396:     */     {
/*  397:1567 */       this.naturalId = value;
/*  398:     */     }
/*  399:     */     
/*  400:     */     public JaxbVersion getVersion()
/*  401:     */     {
/*  402:1579 */       return this.version;
/*  403:     */     }
/*  404:     */     
/*  405:     */     public void setVersion(JaxbVersion value)
/*  406:     */     {
/*  407:1591 */       this.version = value;
/*  408:     */     }
/*  409:     */     
/*  410:     */     public JaxbTimestamp getTimestamp()
/*  411:     */     {
/*  412:1603 */       return this.timestamp;
/*  413:     */     }
/*  414:     */     
/*  415:     */     public void setTimestamp(JaxbTimestamp value)
/*  416:     */     {
/*  417:1615 */       this.timestamp = value;
/*  418:     */     }
/*  419:     */     
/*  420:     */     public List<Object> getPropertyOrManyToOneOrOneToOne()
/*  421:     */     {
/*  422:1654 */       if (this.propertyOrManyToOneOrOneToOne == null) {
/*  423:1655 */         this.propertyOrManyToOneOrOneToOne = new ArrayList();
/*  424:     */       }
/*  425:1657 */       return this.propertyOrManyToOneOrOneToOne;
/*  426:     */     }
/*  427:     */     
/*  428:     */     public List<JaxbJoinElement> getJoin()
/*  429:     */     {
/*  430:1683 */       if (this.join == null) {
/*  431:1684 */         this.join = new ArrayList();
/*  432:     */       }
/*  433:1686 */       return this.join;
/*  434:     */     }
/*  435:     */     
/*  436:     */     public List<JaxbSubclassElement> getSubclass()
/*  437:     */     {
/*  438:1712 */       if (this.subclass == null) {
/*  439:1713 */         this.subclass = new ArrayList();
/*  440:     */       }
/*  441:1715 */       return this.subclass;
/*  442:     */     }
/*  443:     */     
/*  444:     */     public List<JaxbJoinedSubclassElement> getJoinedSubclass()
/*  445:     */     {
/*  446:1741 */       if (this.joinedSubclass == null) {
/*  447:1742 */         this.joinedSubclass = new ArrayList();
/*  448:     */       }
/*  449:1744 */       return this.joinedSubclass;
/*  450:     */     }
/*  451:     */     
/*  452:     */     public List<JaxbUnionSubclassElement> getUnionSubclass()
/*  453:     */     {
/*  454:1770 */       if (this.unionSubclass == null) {
/*  455:1771 */         this.unionSubclass = new ArrayList();
/*  456:     */       }
/*  457:1773 */       return this.unionSubclass;
/*  458:     */     }
/*  459:     */     
/*  460:     */     public JaxbLoaderElement getLoader()
/*  461:     */     {
/*  462:1785 */       return this.loader;
/*  463:     */     }
/*  464:     */     
/*  465:     */     public void setLoader(JaxbLoaderElement value)
/*  466:     */     {
/*  467:1797 */       this.loader = value;
/*  468:     */     }
/*  469:     */     
/*  470:     */     public JaxbSqlInsertElement getSqlInsert()
/*  471:     */     {
/*  472:1809 */       return this.sqlInsert;
/*  473:     */     }
/*  474:     */     
/*  475:     */     public void setSqlInsert(JaxbSqlInsertElement value)
/*  476:     */     {
/*  477:1821 */       this.sqlInsert = value;
/*  478:     */     }
/*  479:     */     
/*  480:     */     public JaxbSqlUpdateElement getSqlUpdate()
/*  481:     */     {
/*  482:1833 */       return this.sqlUpdate;
/*  483:     */     }
/*  484:     */     
/*  485:     */     public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  486:     */     {
/*  487:1845 */       this.sqlUpdate = value;
/*  488:     */     }
/*  489:     */     
/*  490:     */     public JaxbSqlDeleteElement getSqlDelete()
/*  491:     */     {
/*  492:1857 */       return this.sqlDelete;
/*  493:     */     }
/*  494:     */     
/*  495:     */     public void setSqlDelete(JaxbSqlDeleteElement value)
/*  496:     */     {
/*  497:1869 */       this.sqlDelete = value;
/*  498:     */     }
/*  499:     */     
/*  500:     */     public List<JaxbFilterElement> getFilter()
/*  501:     */     {
/*  502:1895 */       if (this.filter == null) {
/*  503:1896 */         this.filter = new ArrayList();
/*  504:     */       }
/*  505:1898 */       return this.filter;
/*  506:     */     }
/*  507:     */     
/*  508:     */     public List<JaxbFetchProfileElement> getFetchProfile()
/*  509:     */     {
/*  510:1924 */       if (this.fetchProfile == null) {
/*  511:1925 */         this.fetchProfile = new ArrayList();
/*  512:     */       }
/*  513:1927 */       return this.fetchProfile;
/*  514:     */     }
/*  515:     */     
/*  516:     */     public List<JaxbResultsetElement> getResultset()
/*  517:     */     {
/*  518:1953 */       if (this.resultset == null) {
/*  519:1954 */         this.resultset = new ArrayList();
/*  520:     */       }
/*  521:1956 */       return this.resultset;
/*  522:     */     }
/*  523:     */     
/*  524:     */     public List<Object> getQueryOrSqlQuery()
/*  525:     */     {
/*  526:1983 */       if (this.queryOrSqlQuery == null) {
/*  527:1984 */         this.queryOrSqlQuery = new ArrayList();
/*  528:     */       }
/*  529:1986 */       return this.queryOrSqlQuery;
/*  530:     */     }
/*  531:     */     
/*  532:     */     public Boolean isAbstract()
/*  533:     */     {
/*  534:1998 */       return this._abstract;
/*  535:     */     }
/*  536:     */     
/*  537:     */     public void setAbstract(Boolean value)
/*  538:     */     {
/*  539:2010 */       this._abstract = value;
/*  540:     */     }
/*  541:     */     
/*  542:     */     public String getBatchSize()
/*  543:     */     {
/*  544:2022 */       return this.batchSize;
/*  545:     */     }
/*  546:     */     
/*  547:     */     public void setBatchSize(String value)
/*  548:     */     {
/*  549:2034 */       this.batchSize = value;
/*  550:     */     }
/*  551:     */     
/*  552:     */     public String getCatalog()
/*  553:     */     {
/*  554:2046 */       return this.catalog;
/*  555:     */     }
/*  556:     */     
/*  557:     */     public void setCatalog(String value)
/*  558:     */     {
/*  559:2058 */       this.catalog = value;
/*  560:     */     }
/*  561:     */     
/*  562:     */     public String getCheck()
/*  563:     */     {
/*  564:2070 */       return this.check;
/*  565:     */     }
/*  566:     */     
/*  567:     */     public void setCheck(String value)
/*  568:     */     {
/*  569:2082 */       this.check = value;
/*  570:     */     }
/*  571:     */     
/*  572:     */     public String getDiscriminatorValue()
/*  573:     */     {
/*  574:2094 */       return this.discriminatorValue;
/*  575:     */     }
/*  576:     */     
/*  577:     */     public void setDiscriminatorValue(String value)
/*  578:     */     {
/*  579:2106 */       this.discriminatorValue = value;
/*  580:     */     }
/*  581:     */     
/*  582:     */     public boolean isDynamicInsert()
/*  583:     */     {
/*  584:2118 */       if (this.dynamicInsert == null) {
/*  585:2119 */         return false;
/*  586:     */       }
/*  587:2121 */       return this.dynamicInsert.booleanValue();
/*  588:     */     }
/*  589:     */     
/*  590:     */     public void setDynamicInsert(Boolean value)
/*  591:     */     {
/*  592:2134 */       this.dynamicInsert = value;
/*  593:     */     }
/*  594:     */     
/*  595:     */     public boolean isDynamicUpdate()
/*  596:     */     {
/*  597:2146 */       if (this.dynamicUpdate == null) {
/*  598:2147 */         return false;
/*  599:     */       }
/*  600:2149 */       return this.dynamicUpdate.booleanValue();
/*  601:     */     }
/*  602:     */     
/*  603:     */     public void setDynamicUpdate(Boolean value)
/*  604:     */     {
/*  605:2162 */       this.dynamicUpdate = value;
/*  606:     */     }
/*  607:     */     
/*  608:     */     public String getEntityName()
/*  609:     */     {
/*  610:2174 */       return this.entityName;
/*  611:     */     }
/*  612:     */     
/*  613:     */     public void setEntityName(String value)
/*  614:     */     {
/*  615:2186 */       this.entityName = value;
/*  616:     */     }
/*  617:     */     
/*  618:     */     public Boolean isLazy()
/*  619:     */     {
/*  620:2198 */       return this.lazy;
/*  621:     */     }
/*  622:     */     
/*  623:     */     public void setLazy(Boolean value)
/*  624:     */     {
/*  625:2210 */       this.lazy = value;
/*  626:     */     }
/*  627:     */     
/*  628:     */     public boolean isMutable()
/*  629:     */     {
/*  630:2222 */       if (this.mutable == null) {
/*  631:2223 */         return true;
/*  632:     */       }
/*  633:2225 */       return this.mutable.booleanValue();
/*  634:     */     }
/*  635:     */     
/*  636:     */     public void setMutable(Boolean value)
/*  637:     */     {
/*  638:2238 */       this.mutable = value;
/*  639:     */     }
/*  640:     */     
/*  641:     */     public String getName()
/*  642:     */     {
/*  643:2250 */       return this.name;
/*  644:     */     }
/*  645:     */     
/*  646:     */     public void setName(String value)
/*  647:     */     {
/*  648:2262 */       this.name = value;
/*  649:     */     }
/*  650:     */     
/*  651:     */     public String getNode()
/*  652:     */     {
/*  653:2274 */       return this.node;
/*  654:     */     }
/*  655:     */     
/*  656:     */     public void setNode(String value)
/*  657:     */     {
/*  658:2286 */       this.node = value;
/*  659:     */     }
/*  660:     */     
/*  661:     */     public String getOptimisticLock()
/*  662:     */     {
/*  663:2298 */       if (this.optimisticLock == null) {
/*  664:2299 */         return "version";
/*  665:     */       }
/*  666:2301 */       return this.optimisticLock;
/*  667:     */     }
/*  668:     */     
/*  669:     */     public void setOptimisticLock(String value)
/*  670:     */     {
/*  671:2314 */       this.optimisticLock = value;
/*  672:     */     }
/*  673:     */     
/*  674:     */     public String getPersister()
/*  675:     */     {
/*  676:2326 */       return this.persister;
/*  677:     */     }
/*  678:     */     
/*  679:     */     public void setPersister(String value)
/*  680:     */     {
/*  681:2338 */       this.persister = value;
/*  682:     */     }
/*  683:     */     
/*  684:     */     public String getPolymorphism()
/*  685:     */     {
/*  686:2350 */       if (this.polymorphism == null) {
/*  687:2351 */         return "implicit";
/*  688:     */       }
/*  689:2353 */       return this.polymorphism;
/*  690:     */     }
/*  691:     */     
/*  692:     */     public void setPolymorphism(String value)
/*  693:     */     {
/*  694:2366 */       this.polymorphism = value;
/*  695:     */     }
/*  696:     */     
/*  697:     */     public String getProxy()
/*  698:     */     {
/*  699:2378 */       return this.proxy;
/*  700:     */     }
/*  701:     */     
/*  702:     */     public void setProxy(String value)
/*  703:     */     {
/*  704:2390 */       this.proxy = value;
/*  705:     */     }
/*  706:     */     
/*  707:     */     public String getRowid()
/*  708:     */     {
/*  709:2402 */       return this.rowid;
/*  710:     */     }
/*  711:     */     
/*  712:     */     public void setRowid(String value)
/*  713:     */     {
/*  714:2414 */       this.rowid = value;
/*  715:     */     }
/*  716:     */     
/*  717:     */     public String getSchema()
/*  718:     */     {
/*  719:2426 */       return this.schema;
/*  720:     */     }
/*  721:     */     
/*  722:     */     public void setSchema(String value)
/*  723:     */     {
/*  724:2438 */       this.schema = value;
/*  725:     */     }
/*  726:     */     
/*  727:     */     public boolean isSelectBeforeUpdate()
/*  728:     */     {
/*  729:2450 */       if (this.selectBeforeUpdate == null) {
/*  730:2451 */         return false;
/*  731:     */       }
/*  732:2453 */       return this.selectBeforeUpdate.booleanValue();
/*  733:     */     }
/*  734:     */     
/*  735:     */     public void setSelectBeforeUpdate(Boolean value)
/*  736:     */     {
/*  737:2466 */       this.selectBeforeUpdate = value;
/*  738:     */     }
/*  739:     */     
/*  740:     */     public String getSubselectAttribute()
/*  741:     */     {
/*  742:2478 */       return this.subselectAttribute;
/*  743:     */     }
/*  744:     */     
/*  745:     */     public void setSubselectAttribute(String value)
/*  746:     */     {
/*  747:2490 */       this.subselectAttribute = value;
/*  748:     */     }
/*  749:     */     
/*  750:     */     public String getTable()
/*  751:     */     {
/*  752:2502 */       return this.table;
/*  753:     */     }
/*  754:     */     
/*  755:     */     public void setTable(String value)
/*  756:     */     {
/*  757:2514 */       this.table = value;
/*  758:     */     }
/*  759:     */     
/*  760:     */     public String getWhere()
/*  761:     */     {
/*  762:2526 */       return this.where;
/*  763:     */     }
/*  764:     */     
/*  765:     */     public void setWhere(String value)
/*  766:     */     {
/*  767:2538 */       this.where = value;
/*  768:     */     }
/*  769:     */     
/*  770:     */     @XmlAccessorType(XmlAccessType.FIELD)
/*  771:     */     @XmlType(name="", propOrder={"meta", "keyPropertyOrKeyManyToOne", "generator"})
/*  772:     */     public static class JaxbCompositeId
/*  773:     */     {
/*  774:     */       protected List<JaxbMetaElement> meta;
/*  775:     */       @XmlElements({@XmlElement(name="key-property", type=JaxbKeyPropertyElement.class), @XmlElement(name="key-many-to-one", type=JaxbKeyManyToOneElement.class)})
/*  776:     */       protected List<Object> keyPropertyOrKeyManyToOne;
/*  777:     */       protected JaxbGeneratorElement generator;
/*  778:     */       @XmlAttribute
/*  779:     */       protected String access;
/*  780:     */       @XmlAttribute(name="class")
/*  781:     */       protected String clazz;
/*  782:     */       @XmlAttribute
/*  783:     */       protected Boolean mapped;
/*  784:     */       @XmlAttribute
/*  785:     */       protected String name;
/*  786:     */       @XmlAttribute
/*  787:     */       protected String node;
/*  788:     */       @XmlAttribute(name="unsaved-value")
/*  789:     */       @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  790:     */       protected String unsavedValue;
/*  791:     */       
/*  792:     */       public List<JaxbMetaElement> getMeta()
/*  793:     */       {
/*  794:2632 */         if (this.meta == null) {
/*  795:2633 */           this.meta = new ArrayList();
/*  796:     */         }
/*  797:2635 */         return this.meta;
/*  798:     */       }
/*  799:     */       
/*  800:     */       public List<Object> getKeyPropertyOrKeyManyToOne()
/*  801:     */       {
/*  802:2662 */         if (this.keyPropertyOrKeyManyToOne == null) {
/*  803:2663 */           this.keyPropertyOrKeyManyToOne = new ArrayList();
/*  804:     */         }
/*  805:2665 */         return this.keyPropertyOrKeyManyToOne;
/*  806:     */       }
/*  807:     */       
/*  808:     */       public JaxbGeneratorElement getGenerator()
/*  809:     */       {
/*  810:2677 */         return this.generator;
/*  811:     */       }
/*  812:     */       
/*  813:     */       public void setGenerator(JaxbGeneratorElement value)
/*  814:     */       {
/*  815:2689 */         this.generator = value;
/*  816:     */       }
/*  817:     */       
/*  818:     */       public String getAccess()
/*  819:     */       {
/*  820:2701 */         return this.access;
/*  821:     */       }
/*  822:     */       
/*  823:     */       public void setAccess(String value)
/*  824:     */       {
/*  825:2713 */         this.access = value;
/*  826:     */       }
/*  827:     */       
/*  828:     */       public String getClazz()
/*  829:     */       {
/*  830:2725 */         return this.clazz;
/*  831:     */       }
/*  832:     */       
/*  833:     */       public void setClazz(String value)
/*  834:     */       {
/*  835:2737 */         this.clazz = value;
/*  836:     */       }
/*  837:     */       
/*  838:     */       public boolean isMapped()
/*  839:     */       {
/*  840:2749 */         if (this.mapped == null) {
/*  841:2750 */           return false;
/*  842:     */         }
/*  843:2752 */         return this.mapped.booleanValue();
/*  844:     */       }
/*  845:     */       
/*  846:     */       public void setMapped(Boolean value)
/*  847:     */       {
/*  848:2765 */         this.mapped = value;
/*  849:     */       }
/*  850:     */       
/*  851:     */       public String getName()
/*  852:     */       {
/*  853:2777 */         return this.name;
/*  854:     */       }
/*  855:     */       
/*  856:     */       public void setName(String value)
/*  857:     */       {
/*  858:2789 */         this.name = value;
/*  859:     */       }
/*  860:     */       
/*  861:     */       public String getNode()
/*  862:     */       {
/*  863:2801 */         return this.node;
/*  864:     */       }
/*  865:     */       
/*  866:     */       public void setNode(String value)
/*  867:     */       {
/*  868:2813 */         this.node = value;
/*  869:     */       }
/*  870:     */       
/*  871:     */       public String getUnsavedValue()
/*  872:     */       {
/*  873:2825 */         if (this.unsavedValue == null) {
/*  874:2826 */           return "undefined";
/*  875:     */         }
/*  876:2828 */         return this.unsavedValue;
/*  877:     */       }
/*  878:     */       
/*  879:     */       public void setUnsavedValue(String value)
/*  880:     */       {
/*  881:2841 */         this.unsavedValue = value;
/*  882:     */       }
/*  883:     */     }
/*  884:     */     
/*  885:     */     @XmlAccessorType(XmlAccessType.FIELD)
/*  886:     */     @XmlType(name="", propOrder={"column", "formula"})
/*  887:     */     public static class JaxbDiscriminator
/*  888:     */     {
/*  889:     */       protected JaxbColumnElement column;
/*  890:     */       protected String formula;
/*  891:     */       @XmlAttribute(name="column")
/*  892:     */       protected String columnAttribute;
/*  893:     */       @XmlAttribute
/*  894:     */       protected Boolean force;
/*  895:     */       @XmlAttribute(name="formula")
/*  896:     */       protected String formulaAttribute;
/*  897:     */       @XmlAttribute
/*  898:     */       protected Boolean insert;
/*  899:     */       @XmlAttribute
/*  900:     */       protected String length;
/*  901:     */       @XmlAttribute(name="not-null")
/*  902:     */       protected Boolean notNull;
/*  903:     */       @XmlAttribute
/*  904:     */       protected String type;
/*  905:     */       
/*  906:     */       public JaxbColumnElement getColumn()
/*  907:     */       {
/*  908:2909 */         return this.column;
/*  909:     */       }
/*  910:     */       
/*  911:     */       public void setColumn(JaxbColumnElement value)
/*  912:     */       {
/*  913:2921 */         this.column = value;
/*  914:     */       }
/*  915:     */       
/*  916:     */       public String getFormula()
/*  917:     */       {
/*  918:2933 */         return this.formula;
/*  919:     */       }
/*  920:     */       
/*  921:     */       public void setFormula(String value)
/*  922:     */       {
/*  923:2945 */         this.formula = value;
/*  924:     */       }
/*  925:     */       
/*  926:     */       public String getColumnAttribute()
/*  927:     */       {
/*  928:2957 */         return this.columnAttribute;
/*  929:     */       }
/*  930:     */       
/*  931:     */       public void setColumnAttribute(String value)
/*  932:     */       {
/*  933:2969 */         this.columnAttribute = value;
/*  934:     */       }
/*  935:     */       
/*  936:     */       public boolean isForce()
/*  937:     */       {
/*  938:2981 */         if (this.force == null) {
/*  939:2982 */           return false;
/*  940:     */         }
/*  941:2984 */         return this.force.booleanValue();
/*  942:     */       }
/*  943:     */       
/*  944:     */       public void setForce(Boolean value)
/*  945:     */       {
/*  946:2997 */         this.force = value;
/*  947:     */       }
/*  948:     */       
/*  949:     */       public String getFormulaAttribute()
/*  950:     */       {
/*  951:3009 */         return this.formulaAttribute;
/*  952:     */       }
/*  953:     */       
/*  954:     */       public void setFormulaAttribute(String value)
/*  955:     */       {
/*  956:3021 */         this.formulaAttribute = value;
/*  957:     */       }
/*  958:     */       
/*  959:     */       public boolean isInsert()
/*  960:     */       {
/*  961:3033 */         if (this.insert == null) {
/*  962:3034 */           return true;
/*  963:     */         }
/*  964:3036 */         return this.insert.booleanValue();
/*  965:     */       }
/*  966:     */       
/*  967:     */       public void setInsert(Boolean value)
/*  968:     */       {
/*  969:3049 */         this.insert = value;
/*  970:     */       }
/*  971:     */       
/*  972:     */       public String getLength()
/*  973:     */       {
/*  974:3061 */         return this.length;
/*  975:     */       }
/*  976:     */       
/*  977:     */       public void setLength(String value)
/*  978:     */       {
/*  979:3073 */         this.length = value;
/*  980:     */       }
/*  981:     */       
/*  982:     */       public boolean isNotNull()
/*  983:     */       {
/*  984:3085 */         if (this.notNull == null) {
/*  985:3086 */           return true;
/*  986:     */         }
/*  987:3088 */         return this.notNull.booleanValue();
/*  988:     */       }
/*  989:     */       
/*  990:     */       public void setNotNull(Boolean value)
/*  991:     */       {
/*  992:3101 */         this.notNull = value;
/*  993:     */       }
/*  994:     */       
/*  995:     */       public String getType()
/*  996:     */       {
/*  997:3113 */         if (this.type == null) {
/*  998:3114 */           return "string";
/*  999:     */         }
/* 1000:3116 */         return this.type;
/* 1001:     */       }
/* 1002:     */       
/* 1003:     */       public void setType(String value)
/* 1004:     */       {
/* 1005:3129 */         this.type = value;
/* 1006:     */       }
/* 1007:     */     }
/* 1008:     */     
/* 1009:     */     @XmlAccessorType(XmlAccessType.FIELD)
/* 1010:     */     @XmlType(name="", propOrder={"meta", "column", "type", "generator"})
/* 1011:     */     public static class JaxbId
/* 1012:     */       implements SingularAttributeSource
/* 1013:     */     {
/* 1014:     */       protected List<JaxbMetaElement> meta;
/* 1015:     */       protected List<JaxbColumnElement> column;
/* 1016:     */       protected JaxbTypeElement type;
/* 1017:     */       protected JaxbGeneratorElement generator;
/* 1018:     */       @XmlAttribute
/* 1019:     */       protected String access;
/* 1020:     */       @XmlAttribute(name="column")
/* 1021:     */       protected String columnAttribute;
/* 1022:     */       @XmlAttribute
/* 1023:     */       protected String length;
/* 1024:     */       @XmlAttribute
/* 1025:     */       protected String name;
/* 1026:     */       @XmlAttribute
/* 1027:     */       protected String node;
/* 1028:     */       @XmlAttribute(name="type")
/* 1029:     */       protected String typeAttribute;
/* 1030:     */       @XmlAttribute(name="unsaved-value")
/* 1031:     */       protected String unsavedValue;
/* 1032:     */       
/* 1033:     */       public List<JaxbMetaElement> getMeta()
/* 1034:     */       {
/* 1035:3217 */         if (this.meta == null) {
/* 1036:3218 */           this.meta = new ArrayList();
/* 1037:     */         }
/* 1038:3220 */         return this.meta;
/* 1039:     */       }
/* 1040:     */       
/* 1041:     */       public List<JaxbColumnElement> getColumn()
/* 1042:     */       {
/* 1043:3246 */         if (this.column == null) {
/* 1044:3247 */           this.column = new ArrayList();
/* 1045:     */         }
/* 1046:3249 */         return this.column;
/* 1047:     */       }
/* 1048:     */       
/* 1049:     */       public JaxbTypeElement getType()
/* 1050:     */       {
/* 1051:3261 */         return this.type;
/* 1052:     */       }
/* 1053:     */       
/* 1054:     */       public void setType(JaxbTypeElement value)
/* 1055:     */       {
/* 1056:3273 */         this.type = value;
/* 1057:     */       }
/* 1058:     */       
/* 1059:     */       public JaxbGeneratorElement getGenerator()
/* 1060:     */       {
/* 1061:3285 */         return this.generator;
/* 1062:     */       }
/* 1063:     */       
/* 1064:     */       public void setGenerator(JaxbGeneratorElement value)
/* 1065:     */       {
/* 1066:3297 */         this.generator = value;
/* 1067:     */       }
/* 1068:     */       
/* 1069:     */       public String getAccess()
/* 1070:     */       {
/* 1071:3309 */         return this.access;
/* 1072:     */       }
/* 1073:     */       
/* 1074:     */       public void setAccess(String value)
/* 1075:     */       {
/* 1076:3321 */         this.access = value;
/* 1077:     */       }
/* 1078:     */       
/* 1079:     */       public String getColumnAttribute()
/* 1080:     */       {
/* 1081:3333 */         return this.columnAttribute;
/* 1082:     */       }
/* 1083:     */       
/* 1084:     */       public void setColumnAttribute(String value)
/* 1085:     */       {
/* 1086:3345 */         this.columnAttribute = value;
/* 1087:     */       }
/* 1088:     */       
/* 1089:     */       public String getLength()
/* 1090:     */       {
/* 1091:3357 */         return this.length;
/* 1092:     */       }
/* 1093:     */       
/* 1094:     */       public void setLength(String value)
/* 1095:     */       {
/* 1096:3369 */         this.length = value;
/* 1097:     */       }
/* 1098:     */       
/* 1099:     */       public String getName()
/* 1100:     */       {
/* 1101:3381 */         return this.name;
/* 1102:     */       }
/* 1103:     */       
/* 1104:     */       public void setName(String value)
/* 1105:     */       {
/* 1106:3393 */         this.name = value;
/* 1107:     */       }
/* 1108:     */       
/* 1109:     */       public String getNode()
/* 1110:     */       {
/* 1111:3405 */         return this.node;
/* 1112:     */       }
/* 1113:     */       
/* 1114:     */       public void setNode(String value)
/* 1115:     */       {
/* 1116:3417 */         this.node = value;
/* 1117:     */       }
/* 1118:     */       
/* 1119:     */       public String getTypeAttribute()
/* 1120:     */       {
/* 1121:3429 */         return this.typeAttribute;
/* 1122:     */       }
/* 1123:     */       
/* 1124:     */       public void setTypeAttribute(String value)
/* 1125:     */       {
/* 1126:3441 */         this.typeAttribute = value;
/* 1127:     */       }
/* 1128:     */       
/* 1129:     */       public String getUnsavedValue()
/* 1130:     */       {
/* 1131:3453 */         return this.unsavedValue;
/* 1132:     */       }
/* 1133:     */       
/* 1134:     */       public void setUnsavedValue(String value)
/* 1135:     */       {
/* 1136:3465 */         this.unsavedValue = value;
/* 1137:     */       }
/* 1138:     */     }
/* 1139:     */     
/* 1140:     */     @XmlAccessorType(XmlAccessType.FIELD)
/* 1141:     */     @XmlType(name="", propOrder={"propertyOrManyToOneOrComponent"})
/* 1142:     */     public static class JaxbNaturalId
/* 1143:     */     {
/* 1144:     */       @XmlElements({@XmlElement(name="property", type=JaxbPropertyElement.class), @XmlElement(name="any", type=JaxbAnyElement.class), @XmlElement(name="component", type=JaxbComponentElement.class), @XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class)})
/* 1145:     */       protected List<Object> propertyOrManyToOneOrComponent;
/* 1146:     */       @XmlAttribute
/* 1147:     */       protected Boolean mutable;
/* 1148:     */       
/* 1149:     */       public List<Object> getPropertyOrManyToOneOrComponent()
/* 1150:     */       {
/* 1151:3541 */         if (this.propertyOrManyToOneOrComponent == null) {
/* 1152:3542 */           this.propertyOrManyToOneOrComponent = new ArrayList();
/* 1153:     */         }
/* 1154:3544 */         return this.propertyOrManyToOneOrComponent;
/* 1155:     */       }
/* 1156:     */       
/* 1157:     */       public boolean isMutable()
/* 1158:     */       {
/* 1159:3556 */         if (this.mutable == null) {
/* 1160:3557 */           return false;
/* 1161:     */         }
/* 1162:3559 */         return this.mutable.booleanValue();
/* 1163:     */       }
/* 1164:     */       
/* 1165:     */       public void setMutable(Boolean value)
/* 1166:     */       {
/* 1167:3572 */         this.mutable = value;
/* 1168:     */       }
/* 1169:     */     }
/* 1170:     */     
/* 1171:     */     @XmlAccessorType(XmlAccessType.FIELD)
/* 1172:     */     @XmlType(name="", propOrder={"meta"})
/* 1173:     */     public static class JaxbTimestamp
/* 1174:     */     {
/* 1175:     */       protected List<JaxbMetaElement> meta;
/* 1176:     */       @XmlAttribute
/* 1177:     */       protected String access;
/* 1178:     */       @XmlAttribute
/* 1179:     */       protected String column;
/* 1180:     */       @XmlAttribute
/* 1181:     */       protected JaxbGeneratedAttribute generated;
/* 1182:     */       @XmlAttribute(required=true)
/* 1183:     */       protected String name;
/* 1184:     */       @XmlAttribute
/* 1185:     */       protected String node;
/* 1186:     */       @XmlAttribute
/* 1187:     */       @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/* 1188:     */       protected String source;
/* 1189:     */       @XmlAttribute(name="unsaved-value")
/* 1190:     */       @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/* 1191:     */       protected String unsavedValue;
/* 1192:     */       
/* 1193:     */       public List<JaxbMetaElement> getMeta()
/* 1194:     */       {
/* 1195:3665 */         if (this.meta == null) {
/* 1196:3666 */           this.meta = new ArrayList();
/* 1197:     */         }
/* 1198:3668 */         return this.meta;
/* 1199:     */       }
/* 1200:     */       
/* 1201:     */       public String getAccess()
/* 1202:     */       {
/* 1203:3680 */         return this.access;
/* 1204:     */       }
/* 1205:     */       
/* 1206:     */       public void setAccess(String value)
/* 1207:     */       {
/* 1208:3692 */         this.access = value;
/* 1209:     */       }
/* 1210:     */       
/* 1211:     */       public String getColumn()
/* 1212:     */       {
/* 1213:3704 */         return this.column;
/* 1214:     */       }
/* 1215:     */       
/* 1216:     */       public void setColumn(String value)
/* 1217:     */       {
/* 1218:3716 */         this.column = value;
/* 1219:     */       }
/* 1220:     */       
/* 1221:     */       public JaxbGeneratedAttribute getGenerated()
/* 1222:     */       {
/* 1223:3728 */         if (this.generated == null) {
/* 1224:3729 */           return JaxbGeneratedAttribute.NEVER;
/* 1225:     */         }
/* 1226:3731 */         return this.generated;
/* 1227:     */       }
/* 1228:     */       
/* 1229:     */       public void setGenerated(JaxbGeneratedAttribute value)
/* 1230:     */       {
/* 1231:3744 */         this.generated = value;
/* 1232:     */       }
/* 1233:     */       
/* 1234:     */       public String getName()
/* 1235:     */       {
/* 1236:3756 */         return this.name;
/* 1237:     */       }
/* 1238:     */       
/* 1239:     */       public void setName(String value)
/* 1240:     */       {
/* 1241:3768 */         this.name = value;
/* 1242:     */       }
/* 1243:     */       
/* 1244:     */       public String getNode()
/* 1245:     */       {
/* 1246:3780 */         return this.node;
/* 1247:     */       }
/* 1248:     */       
/* 1249:     */       public void setNode(String value)
/* 1250:     */       {
/* 1251:3792 */         this.node = value;
/* 1252:     */       }
/* 1253:     */       
/* 1254:     */       public String getSource()
/* 1255:     */       {
/* 1256:3804 */         if (this.source == null) {
/* 1257:3805 */           return "vm";
/* 1258:     */         }
/* 1259:3807 */         return this.source;
/* 1260:     */       }
/* 1261:     */       
/* 1262:     */       public void setSource(String value)
/* 1263:     */       {
/* 1264:3820 */         this.source = value;
/* 1265:     */       }
/* 1266:     */       
/* 1267:     */       public String getUnsavedValue()
/* 1268:     */       {
/* 1269:3832 */         if (this.unsavedValue == null) {
/* 1270:3833 */           return "null";
/* 1271:     */         }
/* 1272:3835 */         return this.unsavedValue;
/* 1273:     */       }
/* 1274:     */       
/* 1275:     */       public void setUnsavedValue(String value)
/* 1276:     */       {
/* 1277:3848 */         this.unsavedValue = value;
/* 1278:     */       }
/* 1279:     */     }
/* 1280:     */     
/* 1281:     */     @XmlAccessorType(XmlAccessType.FIELD)
/* 1282:     */     @XmlType(name="", propOrder={"meta", "column"})
/* 1283:     */     public static class JaxbVersion
/* 1284:     */     {
/* 1285:     */       protected List<JaxbMetaElement> meta;
/* 1286:     */       protected List<JaxbColumnElement> column;
/* 1287:     */       @XmlAttribute
/* 1288:     */       protected String access;
/* 1289:     */       @XmlAttribute(name="column")
/* 1290:     */       protected String columnAttribute;
/* 1291:     */       @XmlAttribute
/* 1292:     */       protected JaxbGeneratedAttribute generated;
/* 1293:     */       @XmlAttribute
/* 1294:     */       protected Boolean insert;
/* 1295:     */       @XmlAttribute(required=true)
/* 1296:     */       protected String name;
/* 1297:     */       @XmlAttribute
/* 1298:     */       protected String node;
/* 1299:     */       @XmlAttribute
/* 1300:     */       protected String type;
/* 1301:     */       @XmlAttribute(name="unsaved-value")
/* 1302:     */       @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/* 1303:     */       protected String unsavedValue;
/* 1304:     */       
/* 1305:     */       public List<JaxbMetaElement> getMeta()
/* 1306:     */       {
/* 1307:3940 */         if (this.meta == null) {
/* 1308:3941 */           this.meta = new ArrayList();
/* 1309:     */         }
/* 1310:3943 */         return this.meta;
/* 1311:     */       }
/* 1312:     */       
/* 1313:     */       public List<JaxbColumnElement> getColumn()
/* 1314:     */       {
/* 1315:3969 */         if (this.column == null) {
/* 1316:3970 */           this.column = new ArrayList();
/* 1317:     */         }
/* 1318:3972 */         return this.column;
/* 1319:     */       }
/* 1320:     */       
/* 1321:     */       public String getAccess()
/* 1322:     */       {
/* 1323:3984 */         return this.access;
/* 1324:     */       }
/* 1325:     */       
/* 1326:     */       public void setAccess(String value)
/* 1327:     */       {
/* 1328:3996 */         this.access = value;
/* 1329:     */       }
/* 1330:     */       
/* 1331:     */       public String getColumnAttribute()
/* 1332:     */       {
/* 1333:4008 */         return this.columnAttribute;
/* 1334:     */       }
/* 1335:     */       
/* 1336:     */       public void setColumnAttribute(String value)
/* 1337:     */       {
/* 1338:4020 */         this.columnAttribute = value;
/* 1339:     */       }
/* 1340:     */       
/* 1341:     */       public JaxbGeneratedAttribute getGenerated()
/* 1342:     */       {
/* 1343:4032 */         if (this.generated == null) {
/* 1344:4033 */           return JaxbGeneratedAttribute.NEVER;
/* 1345:     */         }
/* 1346:4035 */         return this.generated;
/* 1347:     */       }
/* 1348:     */       
/* 1349:     */       public void setGenerated(JaxbGeneratedAttribute value)
/* 1350:     */       {
/* 1351:4048 */         this.generated = value;
/* 1352:     */       }
/* 1353:     */       
/* 1354:     */       public Boolean isInsert()
/* 1355:     */       {
/* 1356:4060 */         return this.insert;
/* 1357:     */       }
/* 1358:     */       
/* 1359:     */       public void setInsert(Boolean value)
/* 1360:     */       {
/* 1361:4072 */         this.insert = value;
/* 1362:     */       }
/* 1363:     */       
/* 1364:     */       public String getName()
/* 1365:     */       {
/* 1366:4084 */         return this.name;
/* 1367:     */       }
/* 1368:     */       
/* 1369:     */       public void setName(String value)
/* 1370:     */       {
/* 1371:4096 */         this.name = value;
/* 1372:     */       }
/* 1373:     */       
/* 1374:     */       public String getNode()
/* 1375:     */       {
/* 1376:4108 */         return this.node;
/* 1377:     */       }
/* 1378:     */       
/* 1379:     */       public void setNode(String value)
/* 1380:     */       {
/* 1381:4120 */         this.node = value;
/* 1382:     */       }
/* 1383:     */       
/* 1384:     */       public String getType()
/* 1385:     */       {
/* 1386:4132 */         if (this.type == null) {
/* 1387:4133 */           return "integer";
/* 1388:     */         }
/* 1389:4135 */         return this.type;
/* 1390:     */       }
/* 1391:     */       
/* 1392:     */       public void setType(String value)
/* 1393:     */       {
/* 1394:4148 */         this.type = value;
/* 1395:     */       }
/* 1396:     */       
/* 1397:     */       public String getUnsavedValue()
/* 1398:     */       {
/* 1399:4160 */         if (this.unsavedValue == null) {
/* 1400:4161 */           return "undefined";
/* 1401:     */         }
/* 1402:4163 */         return this.unsavedValue;
/* 1403:     */       }
/* 1404:     */       
/* 1405:     */       public void setUnsavedValue(String value)
/* 1406:     */       {
/* 1407:4176 */         this.unsavedValue = value;
/* 1408:     */       }
/* 1409:     */     }
/* 1410:     */   }
/* 1411:     */   
/* 1412:     */   @XmlAccessorType(XmlAccessType.FIELD)
/* 1413:     */   @XmlType(name="", propOrder={"definition", "create", "drop", "dialectScope"})
/* 1414:     */   public static class JaxbDatabaseObject
/* 1415:     */   {
/* 1416:     */     protected JaxbDefinition definition;
/* 1417:     */     protected String create;
/* 1418:     */     protected String drop;
/* 1419:     */     @XmlElement(name="dialect-scope")
/* 1420:     */     protected List<JaxbDialectScope> dialectScope;
/* 1421:     */     
/* 1422:     */     public JaxbDefinition getDefinition()
/* 1423:     */     {
/* 1424:4250 */       return this.definition;
/* 1425:     */     }
/* 1426:     */     
/* 1427:     */     public void setDefinition(JaxbDefinition value)
/* 1428:     */     {
/* 1429:4262 */       this.definition = value;
/* 1430:     */     }
/* 1431:     */     
/* 1432:     */     public String getCreate()
/* 1433:     */     {
/* 1434:4274 */       return this.create;
/* 1435:     */     }
/* 1436:     */     
/* 1437:     */     public void setCreate(String value)
/* 1438:     */     {
/* 1439:4286 */       this.create = value;
/* 1440:     */     }
/* 1441:     */     
/* 1442:     */     public String getDrop()
/* 1443:     */     {
/* 1444:4298 */       return this.drop;
/* 1445:     */     }
/* 1446:     */     
/* 1447:     */     public void setDrop(String value)
/* 1448:     */     {
/* 1449:4310 */       this.drop = value;
/* 1450:     */     }
/* 1451:     */     
/* 1452:     */     public List<JaxbDialectScope> getDialectScope()
/* 1453:     */     {
/* 1454:4336 */       if (this.dialectScope == null) {
/* 1455:4337 */         this.dialectScope = new ArrayList();
/* 1456:     */       }
/* 1457:4339 */       return this.dialectScope;
/* 1458:     */     }
/* 1459:     */     
/* 1460:     */     @XmlAccessorType(XmlAccessType.FIELD)
/* 1461:     */     @XmlType(name="")
/* 1462:     */     public static class JaxbDefinition
/* 1463:     */     {
/* 1464:     */       @XmlAttribute(name="class", required=true)
/* 1465:     */       protected String clazz;
/* 1466:     */       
/* 1467:     */       public String getClazz()
/* 1468:     */       {
/* 1469:4376 */         return this.clazz;
/* 1470:     */       }
/* 1471:     */       
/* 1472:     */       public void setClazz(String value)
/* 1473:     */       {
/* 1474:4388 */         this.clazz = value;
/* 1475:     */       }
/* 1476:     */     }
/* 1477:     */     
/* 1478:     */     @XmlAccessorType(XmlAccessType.FIELD)
/* 1479:     */     @XmlType(name="", propOrder={"value"})
/* 1480:     */     public static class JaxbDialectScope
/* 1481:     */     {
/* 1482:     */       @XmlValue
/* 1483:     */       protected String value;
/* 1484:     */       @XmlAttribute(required=true)
/* 1485:     */       protected String name;
/* 1486:     */       
/* 1487:     */       public String getValue()
/* 1488:     */       {
/* 1489:4431 */         return this.value;
/* 1490:     */       }
/* 1491:     */       
/* 1492:     */       public void setValue(String value)
/* 1493:     */       {
/* 1494:4443 */         this.value = value;
/* 1495:     */       }
/* 1496:     */       
/* 1497:     */       public String getName()
/* 1498:     */       {
/* 1499:4455 */         return this.name;
/* 1500:     */       }
/* 1501:     */       
/* 1502:     */       public void setName(String value)
/* 1503:     */       {
/* 1504:4467 */         this.name = value;
/* 1505:     */       }
/* 1506:     */     }
/* 1507:     */   }
/* 1508:     */   
/* 1509:     */   @XmlAccessorType(XmlAccessType.FIELD)
/* 1510:     */   @XmlType(name="", propOrder={"content"})
/* 1511:     */   public static class JaxbFilterDef
/* 1512:     */   {
/* 1513:     */     @XmlElementRef(name="filter-param", namespace="http://www.hibernate.org/xsd/hibernate-mapping", type=JAXBElement.class)
/* 1514:     */     @XmlMixed
/* 1515:     */     protected List<Serializable> content;
/* 1516:     */     @XmlAttribute
/* 1517:     */     protected String condition;
/* 1518:     */     @XmlAttribute(required=true)
/* 1519:     */     protected String name;
/* 1520:     */     
/* 1521:     */     public List<Serializable> getContent()
/* 1522:     */     {
/* 1523:4543 */       if (this.content == null) {
/* 1524:4544 */         this.content = new ArrayList();
/* 1525:     */       }
/* 1526:4546 */       return this.content;
/* 1527:     */     }
/* 1528:     */     
/* 1529:     */     public String getCondition()
/* 1530:     */     {
/* 1531:4558 */       return this.condition;
/* 1532:     */     }
/* 1533:     */     
/* 1534:     */     public void setCondition(String value)
/* 1535:     */     {
/* 1536:4570 */       this.condition = value;
/* 1537:     */     }
/* 1538:     */     
/* 1539:     */     public String getName()
/* 1540:     */     {
/* 1541:4582 */       return this.name;
/* 1542:     */     }
/* 1543:     */     
/* 1544:     */     public void setName(String value)
/* 1545:     */     {
/* 1546:4594 */       this.name = value;
/* 1547:     */     }
/* 1548:     */     
/* 1549:     */     @XmlAccessorType(XmlAccessType.FIELD)
/* 1550:     */     @XmlType(name="")
/* 1551:     */     public static class JaxbFilterParam
/* 1552:     */     {
/* 1553:     */       @XmlAttribute(required=true)
/* 1554:     */       protected String name;
/* 1555:     */       @XmlAttribute(required=true)
/* 1556:     */       protected String type;
/* 1557:     */       
/* 1558:     */       public String getName()
/* 1559:     */       {
/* 1560:4634 */         return this.name;
/* 1561:     */       }
/* 1562:     */       
/* 1563:     */       public void setName(String value)
/* 1564:     */       {
/* 1565:4646 */         this.name = value;
/* 1566:     */       }
/* 1567:     */       
/* 1568:     */       public String getType()
/* 1569:     */       {
/* 1570:4658 */         return this.type;
/* 1571:     */       }
/* 1572:     */       
/* 1573:     */       public void setType(String value)
/* 1574:     */       {
/* 1575:4670 */         this.type = value;
/* 1576:     */       }
/* 1577:     */     }
/* 1578:     */   }
/* 1579:     */   
/* 1580:     */   @XmlAccessorType(XmlAccessType.FIELD)
/* 1581:     */   @XmlType(name="")
/* 1582:     */   public static class JaxbIdentifierGenerator
/* 1583:     */   {
/* 1584:     */     @XmlAttribute(name="class", required=true)
/* 1585:     */     protected String clazz;
/* 1586:     */     @XmlAttribute(required=true)
/* 1587:     */     protected String name;
/* 1588:     */     
/* 1589:     */     public String getClazz()
/* 1590:     */     {
/* 1591:4714 */       return this.clazz;
/* 1592:     */     }
/* 1593:     */     
/* 1594:     */     public void setClazz(String value)
/* 1595:     */     {
/* 1596:4726 */       this.clazz = value;
/* 1597:     */     }
/* 1598:     */     
/* 1599:     */     public String getName()
/* 1600:     */     {
/* 1601:4738 */       return this.name;
/* 1602:     */     }
/* 1603:     */     
/* 1604:     */     public void setName(String value)
/* 1605:     */     {
/* 1606:4750 */       this.name = value;
/* 1607:     */     }
/* 1608:     */   }
/* 1609:     */   
/* 1610:     */   @XmlAccessorType(XmlAccessType.FIELD)
/* 1611:     */   @XmlType(name="")
/* 1612:     */   public static class JaxbImport
/* 1613:     */   {
/* 1614:     */     @XmlAttribute(name="class", required=true)
/* 1615:     */     protected String clazz;
/* 1616:     */     @XmlAttribute
/* 1617:     */     protected String rename;
/* 1618:     */     
/* 1619:     */     public String getClazz()
/* 1620:     */     {
/* 1621:4792 */       return this.clazz;
/* 1622:     */     }
/* 1623:     */     
/* 1624:     */     public void setClazz(String value)
/* 1625:     */     {
/* 1626:4804 */       this.clazz = value;
/* 1627:     */     }
/* 1628:     */     
/* 1629:     */     public String getRename()
/* 1630:     */     {
/* 1631:4816 */       return this.rename;
/* 1632:     */     }
/* 1633:     */     
/* 1634:     */     public void setRename(String value)
/* 1635:     */     {
/* 1636:4828 */       this.rename = value;
/* 1637:     */     }
/* 1638:     */   }
/* 1639:     */   
/* 1640:     */   @XmlAccessorType(XmlAccessType.FIELD)
/* 1641:     */   @XmlType(name="", propOrder={"param"})
/* 1642:     */   public static class JaxbTypedef
/* 1643:     */   {
/* 1644:     */     protected List<JaxbParamElement> param;
/* 1645:     */     @XmlAttribute(name="class", required=true)
/* 1646:     */     protected String clazz;
/* 1647:     */     @XmlAttribute(required=true)
/* 1648:     */     protected String name;
/* 1649:     */     
/* 1650:     */     public List<JaxbParamElement> getParam()
/* 1651:     */     {
/* 1652:4890 */       if (this.param == null) {
/* 1653:4891 */         this.param = new ArrayList();
/* 1654:     */       }
/* 1655:4893 */       return this.param;
/* 1656:     */     }
/* 1657:     */     
/* 1658:     */     public String getClazz()
/* 1659:     */     {
/* 1660:4905 */       return this.clazz;
/* 1661:     */     }
/* 1662:     */     
/* 1663:     */     public void setClazz(String value)
/* 1664:     */     {
/* 1665:4917 */       this.clazz = value;
/* 1666:     */     }
/* 1667:     */     
/* 1668:     */     public String getName()
/* 1669:     */     {
/* 1670:4929 */       return this.name;
/* 1671:     */     }
/* 1672:     */     
/* 1673:     */     public void setName(String value)
/* 1674:     */     {
/* 1675:4941 */       this.name = value;
/* 1676:     */     }
/* 1677:     */   }
/* 1678:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping
 * JD-Core Version:    0.7.0.1
 */