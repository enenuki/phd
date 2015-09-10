/*    1:     */ package org.hibernate.internal.jaxb.mapping.hbm;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.List;
/*    5:     */ import javax.xml.bind.annotation.XmlAccessType;
/*    6:     */ import javax.xml.bind.annotation.XmlAccessorType;
/*    7:     */ import javax.xml.bind.annotation.XmlAttribute;
/*    8:     */ import javax.xml.bind.annotation.XmlElement;
/*    9:     */ import javax.xml.bind.annotation.XmlType;
/*   10:     */ 
/*   11:     */ @XmlAccessorType(XmlAccessType.FIELD)
/*   12:     */ @XmlType(name="idbag-element", propOrder={"meta", "subselect", "cache", "synchronize", "comment", "collectionId", "key", "element", "manyToMany", "compositeElement", "manyToAny", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "sqlDeleteAll", "filter"})
/*   13:     */ public class JaxbIdbagElement
/*   14:     */   extends IdBagPluralAttributeElementAdapter
/*   15:     */   implements PluralAttributeElement
/*   16:     */ {
/*   17:     */   protected List<JaxbMetaElement> meta;
/*   18:     */   protected String subselect;
/*   19:     */   protected JaxbCacheElement cache;
/*   20:     */   protected List<JaxbSynchronizeElement> synchronize;
/*   21:     */   protected String comment;
/*   22:     */   @XmlElement(name="collection-id", required=true)
/*   23:     */   protected JaxbCollectionId collectionId;
/*   24:     */   @XmlElement(required=true)
/*   25:     */   protected JaxbKeyElement key;
/*   26:     */   protected JaxbElementElement element;
/*   27:     */   @XmlElement(name="many-to-many")
/*   28:     */   protected JaxbManyToManyElement manyToMany;
/*   29:     */   @XmlElement(name="composite-element")
/*   30:     */   protected JaxbCompositeElementElement compositeElement;
/*   31:     */   @XmlElement(name="many-to-any")
/*   32:     */   protected JaxbManyToAnyElement manyToAny;
/*   33:     */   protected JaxbLoaderElement loader;
/*   34:     */   @XmlElement(name="sql-insert")
/*   35:     */   protected JaxbSqlInsertElement sqlInsert;
/*   36:     */   @XmlElement(name="sql-update")
/*   37:     */   protected JaxbSqlUpdateElement sqlUpdate;
/*   38:     */   @XmlElement(name="sql-delete")
/*   39:     */   protected JaxbSqlDeleteElement sqlDelete;
/*   40:     */   @XmlElement(name="sql-delete-all")
/*   41:     */   protected JaxbSqlDeleteAllElement sqlDeleteAll;
/*   42:     */   protected List<JaxbFilterElement> filter;
/*   43:     */   @XmlAttribute
/*   44:     */   protected String access;
/*   45:     */   @XmlAttribute(name="batch-size")
/*   46:     */   protected String batchSize;
/*   47:     */   @XmlAttribute
/*   48:     */   protected String cascade;
/*   49:     */   @XmlAttribute
/*   50:     */   protected String catalog;
/*   51:     */   @XmlAttribute
/*   52:     */   protected String check;
/*   53:     */   @XmlAttribute(name="collection-type")
/*   54:     */   protected String collectionType;
/*   55:     */   @XmlAttribute(name="embed-xml")
/*   56:     */   protected Boolean embedXml;
/*   57:     */   @XmlAttribute
/*   58:     */   protected JaxbFetchAttributeWithSubselect fetch;
/*   59:     */   @XmlAttribute
/*   60:     */   protected JaxbLazyAttributeWithExtra lazy;
/*   61:     */   @XmlAttribute
/*   62:     */   protected Boolean mutable;
/*   63:     */   @XmlAttribute(required=true)
/*   64:     */   protected String name;
/*   65:     */   @XmlAttribute
/*   66:     */   protected String node;
/*   67:     */   @XmlAttribute(name="optimistic-lock")
/*   68:     */   protected Boolean optimisticLock;
/*   69:     */   @XmlAttribute(name="order-by")
/*   70:     */   protected String orderBy;
/*   71:     */   @XmlAttribute(name="outer-join")
/*   72:     */   protected JaxbOuterJoinAttribute outerJoin;
/*   73:     */   @XmlAttribute
/*   74:     */   protected String persister;
/*   75:     */   @XmlAttribute
/*   76:     */   protected String schema;
/*   77:     */   @XmlAttribute(name="subselect")
/*   78:     */   protected String subselectAttribute;
/*   79:     */   @XmlAttribute
/*   80:     */   protected String table;
/*   81:     */   @XmlAttribute
/*   82:     */   protected String where;
/*   83:     */   
/*   84:     */   public List<JaxbMetaElement> getMeta()
/*   85:     */   {
/*   86: 207 */     if (this.meta == null) {
/*   87: 208 */       this.meta = new ArrayList();
/*   88:     */     }
/*   89: 210 */     return this.meta;
/*   90:     */   }
/*   91:     */   
/*   92:     */   public String getSubselect()
/*   93:     */   {
/*   94: 222 */     return this.subselect;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public void setSubselect(String value)
/*   98:     */   {
/*   99: 234 */     this.subselect = value;
/*  100:     */   }
/*  101:     */   
/*  102:     */   public JaxbCacheElement getCache()
/*  103:     */   {
/*  104: 246 */     return this.cache;
/*  105:     */   }
/*  106:     */   
/*  107:     */   public void setCache(JaxbCacheElement value)
/*  108:     */   {
/*  109: 258 */     this.cache = value;
/*  110:     */   }
/*  111:     */   
/*  112:     */   public List<JaxbSynchronizeElement> getSynchronize()
/*  113:     */   {
/*  114: 284 */     if (this.synchronize == null) {
/*  115: 285 */       this.synchronize = new ArrayList();
/*  116:     */     }
/*  117: 287 */     return this.synchronize;
/*  118:     */   }
/*  119:     */   
/*  120:     */   public String getComment()
/*  121:     */   {
/*  122: 299 */     return this.comment;
/*  123:     */   }
/*  124:     */   
/*  125:     */   public void setComment(String value)
/*  126:     */   {
/*  127: 311 */     this.comment = value;
/*  128:     */   }
/*  129:     */   
/*  130:     */   public JaxbCollectionId getCollectionId()
/*  131:     */   {
/*  132: 323 */     return this.collectionId;
/*  133:     */   }
/*  134:     */   
/*  135:     */   public void setCollectionId(JaxbCollectionId value)
/*  136:     */   {
/*  137: 335 */     this.collectionId = value;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public JaxbKeyElement getKey()
/*  141:     */   {
/*  142: 347 */     return this.key;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public void setKey(JaxbKeyElement value)
/*  146:     */   {
/*  147: 359 */     this.key = value;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public JaxbElementElement getElement()
/*  151:     */   {
/*  152: 371 */     return this.element;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void setElement(JaxbElementElement value)
/*  156:     */   {
/*  157: 383 */     this.element = value;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public JaxbManyToManyElement getManyToMany()
/*  161:     */   {
/*  162: 395 */     return this.manyToMany;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public void setManyToMany(JaxbManyToManyElement value)
/*  166:     */   {
/*  167: 407 */     this.manyToMany = value;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public JaxbCompositeElementElement getCompositeElement()
/*  171:     */   {
/*  172: 419 */     return this.compositeElement;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public void setCompositeElement(JaxbCompositeElementElement value)
/*  176:     */   {
/*  177: 431 */     this.compositeElement = value;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public JaxbManyToAnyElement getManyToAny()
/*  181:     */   {
/*  182: 443 */     return this.manyToAny;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void setManyToAny(JaxbManyToAnyElement value)
/*  186:     */   {
/*  187: 455 */     this.manyToAny = value;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public JaxbLoaderElement getLoader()
/*  191:     */   {
/*  192: 467 */     return this.loader;
/*  193:     */   }
/*  194:     */   
/*  195:     */   public void setLoader(JaxbLoaderElement value)
/*  196:     */   {
/*  197: 479 */     this.loader = value;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public JaxbSqlInsertElement getSqlInsert()
/*  201:     */   {
/*  202: 491 */     return this.sqlInsert;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void setSqlInsert(JaxbSqlInsertElement value)
/*  206:     */   {
/*  207: 503 */     this.sqlInsert = value;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public JaxbSqlUpdateElement getSqlUpdate()
/*  211:     */   {
/*  212: 515 */     return this.sqlUpdate;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  216:     */   {
/*  217: 527 */     this.sqlUpdate = value;
/*  218:     */   }
/*  219:     */   
/*  220:     */   public JaxbSqlDeleteElement getSqlDelete()
/*  221:     */   {
/*  222: 539 */     return this.sqlDelete;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void setSqlDelete(JaxbSqlDeleteElement value)
/*  226:     */   {
/*  227: 551 */     this.sqlDelete = value;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public JaxbSqlDeleteAllElement getSqlDeleteAll()
/*  231:     */   {
/*  232: 563 */     return this.sqlDeleteAll;
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void setSqlDeleteAll(JaxbSqlDeleteAllElement value)
/*  236:     */   {
/*  237: 575 */     this.sqlDeleteAll = value;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public List<JaxbFilterElement> getFilter()
/*  241:     */   {
/*  242: 601 */     if (this.filter == null) {
/*  243: 602 */       this.filter = new ArrayList();
/*  244:     */     }
/*  245: 604 */     return this.filter;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public String getAccess()
/*  249:     */   {
/*  250: 616 */     return this.access;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public void setAccess(String value)
/*  254:     */   {
/*  255: 628 */     this.access = value;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public String getBatchSize()
/*  259:     */   {
/*  260: 640 */     return this.batchSize;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public void setBatchSize(String value)
/*  264:     */   {
/*  265: 652 */     this.batchSize = value;
/*  266:     */   }
/*  267:     */   
/*  268:     */   public String getCascade()
/*  269:     */   {
/*  270: 664 */     return this.cascade;
/*  271:     */   }
/*  272:     */   
/*  273:     */   public void setCascade(String value)
/*  274:     */   {
/*  275: 676 */     this.cascade = value;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public String getCatalog()
/*  279:     */   {
/*  280: 688 */     return this.catalog;
/*  281:     */   }
/*  282:     */   
/*  283:     */   public void setCatalog(String value)
/*  284:     */   {
/*  285: 700 */     this.catalog = value;
/*  286:     */   }
/*  287:     */   
/*  288:     */   public String getCheck()
/*  289:     */   {
/*  290: 712 */     return this.check;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void setCheck(String value)
/*  294:     */   {
/*  295: 724 */     this.check = value;
/*  296:     */   }
/*  297:     */   
/*  298:     */   public String getCollectionType()
/*  299:     */   {
/*  300: 736 */     return this.collectionType;
/*  301:     */   }
/*  302:     */   
/*  303:     */   public void setCollectionType(String value)
/*  304:     */   {
/*  305: 748 */     this.collectionType = value;
/*  306:     */   }
/*  307:     */   
/*  308:     */   public boolean isEmbedXml()
/*  309:     */   {
/*  310: 760 */     if (this.embedXml == null) {
/*  311: 761 */       return true;
/*  312:     */     }
/*  313: 763 */     return this.embedXml.booleanValue();
/*  314:     */   }
/*  315:     */   
/*  316:     */   public void setEmbedXml(Boolean value)
/*  317:     */   {
/*  318: 776 */     this.embedXml = value;
/*  319:     */   }
/*  320:     */   
/*  321:     */   public JaxbFetchAttributeWithSubselect getFetch()
/*  322:     */   {
/*  323: 788 */     return this.fetch;
/*  324:     */   }
/*  325:     */   
/*  326:     */   public void setFetch(JaxbFetchAttributeWithSubselect value)
/*  327:     */   {
/*  328: 800 */     this.fetch = value;
/*  329:     */   }
/*  330:     */   
/*  331:     */   public JaxbLazyAttributeWithExtra getLazy()
/*  332:     */   {
/*  333: 812 */     return this.lazy;
/*  334:     */   }
/*  335:     */   
/*  336:     */   public void setLazy(JaxbLazyAttributeWithExtra value)
/*  337:     */   {
/*  338: 824 */     this.lazy = value;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public boolean isMutable()
/*  342:     */   {
/*  343: 836 */     if (this.mutable == null) {
/*  344: 837 */       return true;
/*  345:     */     }
/*  346: 839 */     return this.mutable.booleanValue();
/*  347:     */   }
/*  348:     */   
/*  349:     */   public void setMutable(Boolean value)
/*  350:     */   {
/*  351: 852 */     this.mutable = value;
/*  352:     */   }
/*  353:     */   
/*  354:     */   public String getName()
/*  355:     */   {
/*  356: 864 */     return this.name;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public void setName(String value)
/*  360:     */   {
/*  361: 876 */     this.name = value;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public String getNode()
/*  365:     */   {
/*  366: 888 */     return this.node;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public void setNode(String value)
/*  370:     */   {
/*  371: 900 */     this.node = value;
/*  372:     */   }
/*  373:     */   
/*  374:     */   public boolean isOptimisticLock()
/*  375:     */   {
/*  376: 912 */     if (this.optimisticLock == null) {
/*  377: 913 */       return true;
/*  378:     */     }
/*  379: 915 */     return this.optimisticLock.booleanValue();
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void setOptimisticLock(Boolean value)
/*  383:     */   {
/*  384: 928 */     this.optimisticLock = value;
/*  385:     */   }
/*  386:     */   
/*  387:     */   public String getOrderBy()
/*  388:     */   {
/*  389: 940 */     return this.orderBy;
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void setOrderBy(String value)
/*  393:     */   {
/*  394: 952 */     this.orderBy = value;
/*  395:     */   }
/*  396:     */   
/*  397:     */   public JaxbOuterJoinAttribute getOuterJoin()
/*  398:     */   {
/*  399: 964 */     return this.outerJoin;
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/*  403:     */   {
/*  404: 976 */     this.outerJoin = value;
/*  405:     */   }
/*  406:     */   
/*  407:     */   public String getPersister()
/*  408:     */   {
/*  409: 988 */     return this.persister;
/*  410:     */   }
/*  411:     */   
/*  412:     */   public void setPersister(String value)
/*  413:     */   {
/*  414:1000 */     this.persister = value;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public String getSchema()
/*  418:     */   {
/*  419:1012 */     return this.schema;
/*  420:     */   }
/*  421:     */   
/*  422:     */   public void setSchema(String value)
/*  423:     */   {
/*  424:1024 */     this.schema = value;
/*  425:     */   }
/*  426:     */   
/*  427:     */   public String getSubselectAttribute()
/*  428:     */   {
/*  429:1036 */     return this.subselectAttribute;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public void setSubselectAttribute(String value)
/*  433:     */   {
/*  434:1048 */     this.subselectAttribute = value;
/*  435:     */   }
/*  436:     */   
/*  437:     */   public String getTable()
/*  438:     */   {
/*  439:1060 */     return this.table;
/*  440:     */   }
/*  441:     */   
/*  442:     */   public void setTable(String value)
/*  443:     */   {
/*  444:1072 */     this.table = value;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public String getWhere()
/*  448:     */   {
/*  449:1084 */     return this.where;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public void setWhere(String value)
/*  453:     */   {
/*  454:1096 */     this.where = value;
/*  455:     */   }
/*  456:     */   
/*  457:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  458:     */   @XmlType(name="", propOrder={"meta", "column", "generator"})
/*  459:     */   public static class JaxbCollectionId
/*  460:     */   {
/*  461:     */     protected List<JaxbMetaElement> meta;
/*  462:     */     protected List<JaxbColumnElement> column;
/*  463:     */     @XmlElement(required=true)
/*  464:     */     protected JaxbGeneratorElement generator;
/*  465:     */     @XmlAttribute(name="column", required=true)
/*  466:     */     protected String columnAttribute;
/*  467:     */     @XmlAttribute
/*  468:     */     protected String length;
/*  469:     */     @XmlAttribute(required=true)
/*  470:     */     protected String type;
/*  471:     */     
/*  472:     */     public List<JaxbMetaElement> getMeta()
/*  473:     */     {
/*  474:1166 */       if (this.meta == null) {
/*  475:1167 */         this.meta = new ArrayList();
/*  476:     */       }
/*  477:1169 */       return this.meta;
/*  478:     */     }
/*  479:     */     
/*  480:     */     public List<JaxbColumnElement> getColumn()
/*  481:     */     {
/*  482:1195 */       if (this.column == null) {
/*  483:1196 */         this.column = new ArrayList();
/*  484:     */       }
/*  485:1198 */       return this.column;
/*  486:     */     }
/*  487:     */     
/*  488:     */     public JaxbGeneratorElement getGenerator()
/*  489:     */     {
/*  490:1210 */       return this.generator;
/*  491:     */     }
/*  492:     */     
/*  493:     */     public void setGenerator(JaxbGeneratorElement value)
/*  494:     */     {
/*  495:1222 */       this.generator = value;
/*  496:     */     }
/*  497:     */     
/*  498:     */     public String getColumnAttribute()
/*  499:     */     {
/*  500:1234 */       return this.columnAttribute;
/*  501:     */     }
/*  502:     */     
/*  503:     */     public void setColumnAttribute(String value)
/*  504:     */     {
/*  505:1246 */       this.columnAttribute = value;
/*  506:     */     }
/*  507:     */     
/*  508:     */     public String getLength()
/*  509:     */     {
/*  510:1258 */       return this.length;
/*  511:     */     }
/*  512:     */     
/*  513:     */     public void setLength(String value)
/*  514:     */     {
/*  515:1270 */       this.length = value;
/*  516:     */     }
/*  517:     */     
/*  518:     */     public String getType()
/*  519:     */     {
/*  520:1282 */       return this.type;
/*  521:     */     }
/*  522:     */     
/*  523:     */     public void setType(String value)
/*  524:     */     {
/*  525:1294 */       this.type = value;
/*  526:     */     }
/*  527:     */   }
/*  528:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbIdbagElement
 * JD-Core Version:    0.7.0.1
 */