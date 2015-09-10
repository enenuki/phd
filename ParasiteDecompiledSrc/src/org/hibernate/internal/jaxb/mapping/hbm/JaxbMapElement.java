/*    1:     */ package org.hibernate.internal.jaxb.mapping.hbm;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.List;
/*    5:     */ import javax.xml.bind.annotation.XmlAccessType;
/*    6:     */ import javax.xml.bind.annotation.XmlAccessorType;
/*    7:     */ import javax.xml.bind.annotation.XmlAttribute;
/*    8:     */ import javax.xml.bind.annotation.XmlElement;
/*    9:     */ import javax.xml.bind.annotation.XmlElements;
/*   10:     */ import javax.xml.bind.annotation.XmlType;
/*   11:     */ 
/*   12:     */ @XmlAccessorType(XmlAccessType.FIELD)
/*   13:     */ @XmlType(name="map-element", propOrder={"meta", "subselect", "cache", "synchronize", "comment", "key", "mapKey", "compositeMapKey", "mapKeyManyToMany", "index", "compositeIndex", "indexManyToMany", "indexManyToAny", "element", "oneToMany", "manyToMany", "compositeElement", "manyToAny", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "sqlDeleteAll", "filter"})
/*   14:     */ public class JaxbMapElement
/*   15:     */   implements PluralAttributeElement
/*   16:     */ {
/*   17:     */   protected List<JaxbMetaElement> meta;
/*   18:     */   protected String subselect;
/*   19:     */   protected JaxbCacheElement cache;
/*   20:     */   protected List<JaxbSynchronizeElement> synchronize;
/*   21:     */   protected String comment;
/*   22:     */   @XmlElement(required=true)
/*   23:     */   protected JaxbKeyElement key;
/*   24:     */   @XmlElement(name="map-key")
/*   25:     */   protected JaxbMapKey mapKey;
/*   26:     */   @XmlElement(name="composite-map-key")
/*   27:     */   protected JaxbCompositeMapKey compositeMapKey;
/*   28:     */   @XmlElement(name="map-key-many-to-many")
/*   29:     */   protected JaxbMapKeyManyToMany mapKeyManyToMany;
/*   30:     */   protected JaxbIndexElement index;
/*   31:     */   @XmlElement(name="composite-index")
/*   32:     */   protected JaxbCompositeIndex compositeIndex;
/*   33:     */   @XmlElement(name="index-many-to-many")
/*   34:     */   protected JaxbIndexManyToMany indexManyToMany;
/*   35:     */   @XmlElement(name="index-many-to-any")
/*   36:     */   protected JaxbIndexManyToAny indexManyToAny;
/*   37:     */   protected JaxbElementElement element;
/*   38:     */   @XmlElement(name="one-to-many")
/*   39:     */   protected JaxbOneToManyElement oneToMany;
/*   40:     */   @XmlElement(name="many-to-many")
/*   41:     */   protected JaxbManyToManyElement manyToMany;
/*   42:     */   @XmlElement(name="composite-element")
/*   43:     */   protected JaxbCompositeElementElement compositeElement;
/*   44:     */   @XmlElement(name="many-to-any")
/*   45:     */   protected JaxbManyToAnyElement manyToAny;
/*   46:     */   protected JaxbLoaderElement loader;
/*   47:     */   @XmlElement(name="sql-insert")
/*   48:     */   protected JaxbSqlInsertElement sqlInsert;
/*   49:     */   @XmlElement(name="sql-update")
/*   50:     */   protected JaxbSqlUpdateElement sqlUpdate;
/*   51:     */   @XmlElement(name="sql-delete")
/*   52:     */   protected JaxbSqlDeleteElement sqlDelete;
/*   53:     */   @XmlElement(name="sql-delete-all")
/*   54:     */   protected JaxbSqlDeleteAllElement sqlDeleteAll;
/*   55:     */   protected List<JaxbFilterElement> filter;
/*   56:     */   @XmlAttribute
/*   57:     */   protected String access;
/*   58:     */   @XmlAttribute(name="batch-size")
/*   59:     */   protected String batchSize;
/*   60:     */   @XmlAttribute
/*   61:     */   protected String cascade;
/*   62:     */   @XmlAttribute
/*   63:     */   protected String catalog;
/*   64:     */   @XmlAttribute
/*   65:     */   protected String check;
/*   66:     */   @XmlAttribute(name="collection-type")
/*   67:     */   protected String collectionType;
/*   68:     */   @XmlAttribute(name="embed-xml")
/*   69:     */   protected Boolean embedXml;
/*   70:     */   @XmlAttribute
/*   71:     */   protected JaxbFetchAttributeWithSubselect fetch;
/*   72:     */   @XmlAttribute
/*   73:     */   protected Boolean inverse;
/*   74:     */   @XmlAttribute
/*   75:     */   protected JaxbLazyAttributeWithExtra lazy;
/*   76:     */   @XmlAttribute
/*   77:     */   protected Boolean mutable;
/*   78:     */   @XmlAttribute(required=true)
/*   79:     */   protected String name;
/*   80:     */   @XmlAttribute
/*   81:     */   protected String node;
/*   82:     */   @XmlAttribute(name="optimistic-lock")
/*   83:     */   protected Boolean optimisticLock;
/*   84:     */   @XmlAttribute(name="order-by")
/*   85:     */   protected String orderBy;
/*   86:     */   @XmlAttribute(name="outer-join")
/*   87:     */   protected JaxbOuterJoinAttribute outerJoin;
/*   88:     */   @XmlAttribute
/*   89:     */   protected String persister;
/*   90:     */   @XmlAttribute
/*   91:     */   protected String schema;
/*   92:     */   @XmlAttribute
/*   93:     */   protected String sort;
/*   94:     */   @XmlAttribute(name="subselect")
/*   95:     */   protected String subselectAttribute;
/*   96:     */   @XmlAttribute
/*   97:     */   protected String table;
/*   98:     */   @XmlAttribute
/*   99:     */   protected String where;
/*  100:     */   
/*  101:     */   public List<JaxbMetaElement> getMeta()
/*  102:     */   {
/*  103: 317 */     if (this.meta == null) {
/*  104: 318 */       this.meta = new ArrayList();
/*  105:     */     }
/*  106: 320 */     return this.meta;
/*  107:     */   }
/*  108:     */   
/*  109:     */   public String getSubselect()
/*  110:     */   {
/*  111: 332 */     return this.subselect;
/*  112:     */   }
/*  113:     */   
/*  114:     */   public void setSubselect(String value)
/*  115:     */   {
/*  116: 344 */     this.subselect = value;
/*  117:     */   }
/*  118:     */   
/*  119:     */   public JaxbCacheElement getCache()
/*  120:     */   {
/*  121: 356 */     return this.cache;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public void setCache(JaxbCacheElement value)
/*  125:     */   {
/*  126: 368 */     this.cache = value;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public List<JaxbSynchronizeElement> getSynchronize()
/*  130:     */   {
/*  131: 394 */     if (this.synchronize == null) {
/*  132: 395 */       this.synchronize = new ArrayList();
/*  133:     */     }
/*  134: 397 */     return this.synchronize;
/*  135:     */   }
/*  136:     */   
/*  137:     */   public String getComment()
/*  138:     */   {
/*  139: 409 */     return this.comment;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public void setComment(String value)
/*  143:     */   {
/*  144: 421 */     this.comment = value;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public JaxbKeyElement getKey()
/*  148:     */   {
/*  149: 433 */     return this.key;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public void setKey(JaxbKeyElement value)
/*  153:     */   {
/*  154: 445 */     this.key = value;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public JaxbMapKey getMapKey()
/*  158:     */   {
/*  159: 457 */     return this.mapKey;
/*  160:     */   }
/*  161:     */   
/*  162:     */   public void setMapKey(JaxbMapKey value)
/*  163:     */   {
/*  164: 469 */     this.mapKey = value;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public JaxbCompositeMapKey getCompositeMapKey()
/*  168:     */   {
/*  169: 481 */     return this.compositeMapKey;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public void setCompositeMapKey(JaxbCompositeMapKey value)
/*  173:     */   {
/*  174: 493 */     this.compositeMapKey = value;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public JaxbMapKeyManyToMany getMapKeyManyToMany()
/*  178:     */   {
/*  179: 505 */     return this.mapKeyManyToMany;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void setMapKeyManyToMany(JaxbMapKeyManyToMany value)
/*  183:     */   {
/*  184: 517 */     this.mapKeyManyToMany = value;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public JaxbIndexElement getIndex()
/*  188:     */   {
/*  189: 529 */     return this.index;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public void setIndex(JaxbIndexElement value)
/*  193:     */   {
/*  194: 541 */     this.index = value;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public JaxbCompositeIndex getCompositeIndex()
/*  198:     */   {
/*  199: 553 */     return this.compositeIndex;
/*  200:     */   }
/*  201:     */   
/*  202:     */   public void setCompositeIndex(JaxbCompositeIndex value)
/*  203:     */   {
/*  204: 565 */     this.compositeIndex = value;
/*  205:     */   }
/*  206:     */   
/*  207:     */   public JaxbIndexManyToMany getIndexManyToMany()
/*  208:     */   {
/*  209: 577 */     return this.indexManyToMany;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public void setIndexManyToMany(JaxbIndexManyToMany value)
/*  213:     */   {
/*  214: 589 */     this.indexManyToMany = value;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public JaxbIndexManyToAny getIndexManyToAny()
/*  218:     */   {
/*  219: 601 */     return this.indexManyToAny;
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void setIndexManyToAny(JaxbIndexManyToAny value)
/*  223:     */   {
/*  224: 613 */     this.indexManyToAny = value;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public JaxbElementElement getElement()
/*  228:     */   {
/*  229: 625 */     return this.element;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public void setElement(JaxbElementElement value)
/*  233:     */   {
/*  234: 637 */     this.element = value;
/*  235:     */   }
/*  236:     */   
/*  237:     */   public JaxbOneToManyElement getOneToMany()
/*  238:     */   {
/*  239: 649 */     return this.oneToMany;
/*  240:     */   }
/*  241:     */   
/*  242:     */   public void setOneToMany(JaxbOneToManyElement value)
/*  243:     */   {
/*  244: 661 */     this.oneToMany = value;
/*  245:     */   }
/*  246:     */   
/*  247:     */   public JaxbManyToManyElement getManyToMany()
/*  248:     */   {
/*  249: 673 */     return this.manyToMany;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public void setManyToMany(JaxbManyToManyElement value)
/*  253:     */   {
/*  254: 685 */     this.manyToMany = value;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public JaxbCompositeElementElement getCompositeElement()
/*  258:     */   {
/*  259: 697 */     return this.compositeElement;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public void setCompositeElement(JaxbCompositeElementElement value)
/*  263:     */   {
/*  264: 709 */     this.compositeElement = value;
/*  265:     */   }
/*  266:     */   
/*  267:     */   public JaxbManyToAnyElement getManyToAny()
/*  268:     */   {
/*  269: 721 */     return this.manyToAny;
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void setManyToAny(JaxbManyToAnyElement value)
/*  273:     */   {
/*  274: 733 */     this.manyToAny = value;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public JaxbLoaderElement getLoader()
/*  278:     */   {
/*  279: 745 */     return this.loader;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public void setLoader(JaxbLoaderElement value)
/*  283:     */   {
/*  284: 757 */     this.loader = value;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public JaxbSqlInsertElement getSqlInsert()
/*  288:     */   {
/*  289: 769 */     return this.sqlInsert;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public void setSqlInsert(JaxbSqlInsertElement value)
/*  293:     */   {
/*  294: 781 */     this.sqlInsert = value;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public JaxbSqlUpdateElement getSqlUpdate()
/*  298:     */   {
/*  299: 793 */     return this.sqlUpdate;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  303:     */   {
/*  304: 805 */     this.sqlUpdate = value;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public JaxbSqlDeleteElement getSqlDelete()
/*  308:     */   {
/*  309: 817 */     return this.sqlDelete;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void setSqlDelete(JaxbSqlDeleteElement value)
/*  313:     */   {
/*  314: 829 */     this.sqlDelete = value;
/*  315:     */   }
/*  316:     */   
/*  317:     */   public JaxbSqlDeleteAllElement getSqlDeleteAll()
/*  318:     */   {
/*  319: 841 */     return this.sqlDeleteAll;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public void setSqlDeleteAll(JaxbSqlDeleteAllElement value)
/*  323:     */   {
/*  324: 853 */     this.sqlDeleteAll = value;
/*  325:     */   }
/*  326:     */   
/*  327:     */   public List<JaxbFilterElement> getFilter()
/*  328:     */   {
/*  329: 879 */     if (this.filter == null) {
/*  330: 880 */       this.filter = new ArrayList();
/*  331:     */     }
/*  332: 882 */     return this.filter;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public String getAccess()
/*  336:     */   {
/*  337: 894 */     return this.access;
/*  338:     */   }
/*  339:     */   
/*  340:     */   public void setAccess(String value)
/*  341:     */   {
/*  342: 906 */     this.access = value;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public String getBatchSize()
/*  346:     */   {
/*  347: 918 */     return this.batchSize;
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void setBatchSize(String value)
/*  351:     */   {
/*  352: 930 */     this.batchSize = value;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public String getCascade()
/*  356:     */   {
/*  357: 942 */     return this.cascade;
/*  358:     */   }
/*  359:     */   
/*  360:     */   public void setCascade(String value)
/*  361:     */   {
/*  362: 954 */     this.cascade = value;
/*  363:     */   }
/*  364:     */   
/*  365:     */   public String getCatalog()
/*  366:     */   {
/*  367: 966 */     return this.catalog;
/*  368:     */   }
/*  369:     */   
/*  370:     */   public void setCatalog(String value)
/*  371:     */   {
/*  372: 978 */     this.catalog = value;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public String getCheck()
/*  376:     */   {
/*  377: 990 */     return this.check;
/*  378:     */   }
/*  379:     */   
/*  380:     */   public void setCheck(String value)
/*  381:     */   {
/*  382:1002 */     this.check = value;
/*  383:     */   }
/*  384:     */   
/*  385:     */   public String getCollectionType()
/*  386:     */   {
/*  387:1014 */     return this.collectionType;
/*  388:     */   }
/*  389:     */   
/*  390:     */   public void setCollectionType(String value)
/*  391:     */   {
/*  392:1026 */     this.collectionType = value;
/*  393:     */   }
/*  394:     */   
/*  395:     */   public boolean isEmbedXml()
/*  396:     */   {
/*  397:1038 */     if (this.embedXml == null) {
/*  398:1039 */       return true;
/*  399:     */     }
/*  400:1041 */     return this.embedXml.booleanValue();
/*  401:     */   }
/*  402:     */   
/*  403:     */   public void setEmbedXml(Boolean value)
/*  404:     */   {
/*  405:1054 */     this.embedXml = value;
/*  406:     */   }
/*  407:     */   
/*  408:     */   public JaxbFetchAttributeWithSubselect getFetch()
/*  409:     */   {
/*  410:1066 */     return this.fetch;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public void setFetch(JaxbFetchAttributeWithSubselect value)
/*  414:     */   {
/*  415:1078 */     this.fetch = value;
/*  416:     */   }
/*  417:     */   
/*  418:     */   public boolean isInverse()
/*  419:     */   {
/*  420:1090 */     if (this.inverse == null) {
/*  421:1091 */       return false;
/*  422:     */     }
/*  423:1093 */     return this.inverse.booleanValue();
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void setInverse(Boolean value)
/*  427:     */   {
/*  428:1106 */     this.inverse = value;
/*  429:     */   }
/*  430:     */   
/*  431:     */   public JaxbLazyAttributeWithExtra getLazy()
/*  432:     */   {
/*  433:1118 */     return this.lazy;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void setLazy(JaxbLazyAttributeWithExtra value)
/*  437:     */   {
/*  438:1130 */     this.lazy = value;
/*  439:     */   }
/*  440:     */   
/*  441:     */   public boolean isMutable()
/*  442:     */   {
/*  443:1142 */     if (this.mutable == null) {
/*  444:1143 */       return true;
/*  445:     */     }
/*  446:1145 */     return this.mutable.booleanValue();
/*  447:     */   }
/*  448:     */   
/*  449:     */   public void setMutable(Boolean value)
/*  450:     */   {
/*  451:1158 */     this.mutable = value;
/*  452:     */   }
/*  453:     */   
/*  454:     */   public String getName()
/*  455:     */   {
/*  456:1170 */     return this.name;
/*  457:     */   }
/*  458:     */   
/*  459:     */   public void setName(String value)
/*  460:     */   {
/*  461:1182 */     this.name = value;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public String getNode()
/*  465:     */   {
/*  466:1194 */     return this.node;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public void setNode(String value)
/*  470:     */   {
/*  471:1206 */     this.node = value;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public boolean isOptimisticLock()
/*  475:     */   {
/*  476:1218 */     if (this.optimisticLock == null) {
/*  477:1219 */       return true;
/*  478:     */     }
/*  479:1221 */     return this.optimisticLock.booleanValue();
/*  480:     */   }
/*  481:     */   
/*  482:     */   public void setOptimisticLock(Boolean value)
/*  483:     */   {
/*  484:1234 */     this.optimisticLock = value;
/*  485:     */   }
/*  486:     */   
/*  487:     */   public String getOrderBy()
/*  488:     */   {
/*  489:1246 */     return this.orderBy;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void setOrderBy(String value)
/*  493:     */   {
/*  494:1258 */     this.orderBy = value;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public JaxbOuterJoinAttribute getOuterJoin()
/*  498:     */   {
/*  499:1270 */     return this.outerJoin;
/*  500:     */   }
/*  501:     */   
/*  502:     */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/*  503:     */   {
/*  504:1282 */     this.outerJoin = value;
/*  505:     */   }
/*  506:     */   
/*  507:     */   public String getPersister()
/*  508:     */   {
/*  509:1294 */     return this.persister;
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void setPersister(String value)
/*  513:     */   {
/*  514:1306 */     this.persister = value;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public String getSchema()
/*  518:     */   {
/*  519:1318 */     return this.schema;
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void setSchema(String value)
/*  523:     */   {
/*  524:1330 */     this.schema = value;
/*  525:     */   }
/*  526:     */   
/*  527:     */   public String getSort()
/*  528:     */   {
/*  529:1342 */     if (this.sort == null) {
/*  530:1343 */       return "unsorted";
/*  531:     */     }
/*  532:1345 */     return this.sort;
/*  533:     */   }
/*  534:     */   
/*  535:     */   public void setSort(String value)
/*  536:     */   {
/*  537:1358 */     this.sort = value;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public String getSubselectAttribute()
/*  541:     */   {
/*  542:1370 */     return this.subselectAttribute;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void setSubselectAttribute(String value)
/*  546:     */   {
/*  547:1382 */     this.subselectAttribute = value;
/*  548:     */   }
/*  549:     */   
/*  550:     */   public String getTable()
/*  551:     */   {
/*  552:1394 */     return this.table;
/*  553:     */   }
/*  554:     */   
/*  555:     */   public void setTable(String value)
/*  556:     */   {
/*  557:1406 */     this.table = value;
/*  558:     */   }
/*  559:     */   
/*  560:     */   public String getWhere()
/*  561:     */   {
/*  562:1418 */     return this.where;
/*  563:     */   }
/*  564:     */   
/*  565:     */   public void setWhere(String value)
/*  566:     */   {
/*  567:1430 */     this.where = value;
/*  568:     */   }
/*  569:     */   
/*  570:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  571:     */   @XmlType(name="", propOrder={"keyPropertyOrKeyManyToOne"})
/*  572:     */   public static class JaxbCompositeIndex
/*  573:     */   {
/*  574:     */     @XmlElements({@XmlElement(name="key-many-to-one", type=JaxbKeyManyToOneElement.class), @XmlElement(name="key-property", type=JaxbKeyPropertyElement.class)})
/*  575:     */     protected List<Object> keyPropertyOrKeyManyToOne;
/*  576:     */     @XmlAttribute(name="class", required=true)
/*  577:     */     protected String clazz;
/*  578:     */     
/*  579:     */     public List<Object> getKeyPropertyOrKeyManyToOne()
/*  580:     */     {
/*  581:1495 */       if (this.keyPropertyOrKeyManyToOne == null) {
/*  582:1496 */         this.keyPropertyOrKeyManyToOne = new ArrayList();
/*  583:     */       }
/*  584:1498 */       return this.keyPropertyOrKeyManyToOne;
/*  585:     */     }
/*  586:     */     
/*  587:     */     public String getClazz()
/*  588:     */     {
/*  589:1510 */       return this.clazz;
/*  590:     */     }
/*  591:     */     
/*  592:     */     public void setClazz(String value)
/*  593:     */     {
/*  594:1522 */       this.clazz = value;
/*  595:     */     }
/*  596:     */   }
/*  597:     */   
/*  598:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  599:     */   @XmlType(name="", propOrder={"keyPropertyOrKeyManyToOne"})
/*  600:     */   public static class JaxbCompositeMapKey
/*  601:     */   {
/*  602:     */     @XmlElements({@XmlElement(name="key-many-to-one", type=JaxbKeyManyToOneElement.class), @XmlElement(name="key-property", type=JaxbKeyPropertyElement.class)})
/*  603:     */     protected List<Object> keyPropertyOrKeyManyToOne;
/*  604:     */     @XmlAttribute(name="class", required=true)
/*  605:     */     protected String clazz;
/*  606:     */     
/*  607:     */     public List<Object> getKeyPropertyOrKeyManyToOne()
/*  608:     */     {
/*  609:1589 */       if (this.keyPropertyOrKeyManyToOne == null) {
/*  610:1590 */         this.keyPropertyOrKeyManyToOne = new ArrayList();
/*  611:     */       }
/*  612:1592 */       return this.keyPropertyOrKeyManyToOne;
/*  613:     */     }
/*  614:     */     
/*  615:     */     public String getClazz()
/*  616:     */     {
/*  617:1604 */       return this.clazz;
/*  618:     */     }
/*  619:     */     
/*  620:     */     public void setClazz(String value)
/*  621:     */     {
/*  622:1616 */       this.clazz = value;
/*  623:     */     }
/*  624:     */   }
/*  625:     */   
/*  626:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  627:     */   @XmlType(name="", propOrder={"column"})
/*  628:     */   public static class JaxbIndexManyToAny
/*  629:     */   {
/*  630:     */     @XmlElement(required=true)
/*  631:     */     protected JaxbColumnElement column;
/*  632:     */     @XmlAttribute(name="id-type", required=true)
/*  633:     */     protected String idType;
/*  634:     */     @XmlAttribute(name="meta-type")
/*  635:     */     protected String metaType;
/*  636:     */     
/*  637:     */     public JaxbColumnElement getColumn()
/*  638:     */     {
/*  639:1665 */       return this.column;
/*  640:     */     }
/*  641:     */     
/*  642:     */     public void setColumn(JaxbColumnElement value)
/*  643:     */     {
/*  644:1677 */       this.column = value;
/*  645:     */     }
/*  646:     */     
/*  647:     */     public String getIdType()
/*  648:     */     {
/*  649:1689 */       return this.idType;
/*  650:     */     }
/*  651:     */     
/*  652:     */     public void setIdType(String value)
/*  653:     */     {
/*  654:1701 */       this.idType = value;
/*  655:     */     }
/*  656:     */     
/*  657:     */     public String getMetaType()
/*  658:     */     {
/*  659:1713 */       return this.metaType;
/*  660:     */     }
/*  661:     */     
/*  662:     */     public void setMetaType(String value)
/*  663:     */     {
/*  664:1725 */       this.metaType = value;
/*  665:     */     }
/*  666:     */   }
/*  667:     */   
/*  668:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  669:     */   @XmlType(name="", propOrder={"column"})
/*  670:     */   public static class JaxbIndexManyToMany
/*  671:     */   {
/*  672:     */     protected List<JaxbColumnElement> column;
/*  673:     */     @XmlAttribute(name="class", required=true)
/*  674:     */     protected String clazz;
/*  675:     */     @XmlAttribute(name="column")
/*  676:     */     protected String columnAttribute;
/*  677:     */     @XmlAttribute(name="entity-name")
/*  678:     */     protected String entityName;
/*  679:     */     @XmlAttribute(name="foreign-key")
/*  680:     */     protected String foreignKey;
/*  681:     */     
/*  682:     */     public List<JaxbColumnElement> getColumn()
/*  683:     */     {
/*  684:1793 */       if (this.column == null) {
/*  685:1794 */         this.column = new ArrayList();
/*  686:     */       }
/*  687:1796 */       return this.column;
/*  688:     */     }
/*  689:     */     
/*  690:     */     public String getClazz()
/*  691:     */     {
/*  692:1808 */       return this.clazz;
/*  693:     */     }
/*  694:     */     
/*  695:     */     public void setClazz(String value)
/*  696:     */     {
/*  697:1820 */       this.clazz = value;
/*  698:     */     }
/*  699:     */     
/*  700:     */     public String getColumnAttribute()
/*  701:     */     {
/*  702:1832 */       return this.columnAttribute;
/*  703:     */     }
/*  704:     */     
/*  705:     */     public void setColumnAttribute(String value)
/*  706:     */     {
/*  707:1844 */       this.columnAttribute = value;
/*  708:     */     }
/*  709:     */     
/*  710:     */     public String getEntityName()
/*  711:     */     {
/*  712:1856 */       return this.entityName;
/*  713:     */     }
/*  714:     */     
/*  715:     */     public void setEntityName(String value)
/*  716:     */     {
/*  717:1868 */       this.entityName = value;
/*  718:     */     }
/*  719:     */     
/*  720:     */     public String getForeignKey()
/*  721:     */     {
/*  722:1880 */       return this.foreignKey;
/*  723:     */     }
/*  724:     */     
/*  725:     */     public void setForeignKey(String value)
/*  726:     */     {
/*  727:1892 */       this.foreignKey = value;
/*  728:     */     }
/*  729:     */   }
/*  730:     */   
/*  731:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  732:     */   @XmlType(name="", propOrder={"columnOrFormula", "type"})
/*  733:     */   public static class JaxbMapKey
/*  734:     */   {
/*  735:     */     @XmlElements({@XmlElement(name="column", type=JaxbColumnElement.class), @XmlElement(name="formula", type=String.class)})
/*  736:     */     protected List<Object> columnOrFormula;
/*  737:     */     protected JaxbTypeElement type;
/*  738:     */     @XmlAttribute
/*  739:     */     protected String column;
/*  740:     */     @XmlAttribute
/*  741:     */     protected String formula;
/*  742:     */     @XmlAttribute
/*  743:     */     protected String length;
/*  744:     */     @XmlAttribute
/*  745:     */     protected String node;
/*  746:     */     @XmlAttribute(name="type")
/*  747:     */     protected String typeAttribute;
/*  748:     */     
/*  749:     */     public List<Object> getColumnOrFormula()
/*  750:     */     {
/*  751:1974 */       if (this.columnOrFormula == null) {
/*  752:1975 */         this.columnOrFormula = new ArrayList();
/*  753:     */       }
/*  754:1977 */       return this.columnOrFormula;
/*  755:     */     }
/*  756:     */     
/*  757:     */     public JaxbTypeElement getType()
/*  758:     */     {
/*  759:1989 */       return this.type;
/*  760:     */     }
/*  761:     */     
/*  762:     */     public void setType(JaxbTypeElement value)
/*  763:     */     {
/*  764:2001 */       this.type = value;
/*  765:     */     }
/*  766:     */     
/*  767:     */     public String getColumn()
/*  768:     */     {
/*  769:2013 */       return this.column;
/*  770:     */     }
/*  771:     */     
/*  772:     */     public void setColumn(String value)
/*  773:     */     {
/*  774:2025 */       this.column = value;
/*  775:     */     }
/*  776:     */     
/*  777:     */     public String getFormula()
/*  778:     */     {
/*  779:2037 */       return this.formula;
/*  780:     */     }
/*  781:     */     
/*  782:     */     public void setFormula(String value)
/*  783:     */     {
/*  784:2049 */       this.formula = value;
/*  785:     */     }
/*  786:     */     
/*  787:     */     public String getLength()
/*  788:     */     {
/*  789:2061 */       return this.length;
/*  790:     */     }
/*  791:     */     
/*  792:     */     public void setLength(String value)
/*  793:     */     {
/*  794:2073 */       this.length = value;
/*  795:     */     }
/*  796:     */     
/*  797:     */     public String getNode()
/*  798:     */     {
/*  799:2085 */       return this.node;
/*  800:     */     }
/*  801:     */     
/*  802:     */     public void setNode(String value)
/*  803:     */     {
/*  804:2097 */       this.node = value;
/*  805:     */     }
/*  806:     */     
/*  807:     */     public String getTypeAttribute()
/*  808:     */     {
/*  809:2109 */       return this.typeAttribute;
/*  810:     */     }
/*  811:     */     
/*  812:     */     public void setTypeAttribute(String value)
/*  813:     */     {
/*  814:2121 */       this.typeAttribute = value;
/*  815:     */     }
/*  816:     */   }
/*  817:     */   
/*  818:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  819:     */   @XmlType(name="", propOrder={"columnOrFormula"})
/*  820:     */   public static class JaxbMapKeyManyToMany
/*  821:     */   {
/*  822:     */     @XmlElements({@XmlElement(name="formula", type=String.class), @XmlElement(name="column", type=JaxbColumnElement.class)})
/*  823:     */     protected List<Object> columnOrFormula;
/*  824:     */     @XmlAttribute(name="class")
/*  825:     */     protected String clazz;
/*  826:     */     @XmlAttribute
/*  827:     */     protected String column;
/*  828:     */     @XmlAttribute(name="entity-name")
/*  829:     */     protected String entityName;
/*  830:     */     @XmlAttribute(name="foreign-key")
/*  831:     */     protected String foreignKey;
/*  832:     */     @XmlAttribute
/*  833:     */     protected String formula;
/*  834:     */     
/*  835:     */     public List<Object> getColumnOrFormula()
/*  836:     */     {
/*  837:2200 */       if (this.columnOrFormula == null) {
/*  838:2201 */         this.columnOrFormula = new ArrayList();
/*  839:     */       }
/*  840:2203 */       return this.columnOrFormula;
/*  841:     */     }
/*  842:     */     
/*  843:     */     public String getClazz()
/*  844:     */     {
/*  845:2215 */       return this.clazz;
/*  846:     */     }
/*  847:     */     
/*  848:     */     public void setClazz(String value)
/*  849:     */     {
/*  850:2227 */       this.clazz = value;
/*  851:     */     }
/*  852:     */     
/*  853:     */     public String getColumn()
/*  854:     */     {
/*  855:2239 */       return this.column;
/*  856:     */     }
/*  857:     */     
/*  858:     */     public void setColumn(String value)
/*  859:     */     {
/*  860:2251 */       this.column = value;
/*  861:     */     }
/*  862:     */     
/*  863:     */     public String getEntityName()
/*  864:     */     {
/*  865:2263 */       return this.entityName;
/*  866:     */     }
/*  867:     */     
/*  868:     */     public void setEntityName(String value)
/*  869:     */     {
/*  870:2275 */       this.entityName = value;
/*  871:     */     }
/*  872:     */     
/*  873:     */     public String getForeignKey()
/*  874:     */     {
/*  875:2287 */       return this.foreignKey;
/*  876:     */     }
/*  877:     */     
/*  878:     */     public void setForeignKey(String value)
/*  879:     */     {
/*  880:2299 */       this.foreignKey = value;
/*  881:     */     }
/*  882:     */     
/*  883:     */     public String getFormula()
/*  884:     */     {
/*  885:2311 */       return this.formula;
/*  886:     */     }
/*  887:     */     
/*  888:     */     public void setFormula(String value)
/*  889:     */     {
/*  890:2323 */       this.formula = value;
/*  891:     */     }
/*  892:     */   }
/*  893:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbMapElement
 * JD-Core Version:    0.7.0.1
 */