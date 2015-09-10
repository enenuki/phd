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
/*   12:     */ @XmlType(name="list-element", propOrder={"meta", "subselect", "cache", "synchronize", "comment", "key", "index", "listIndex", "element", "oneToMany", "manyToMany", "compositeElement", "manyToAny", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "sqlDeleteAll", "filter"})
/*   13:     */ public class JaxbListElement
/*   14:     */   implements PluralAttributeElement
/*   15:     */ {
/*   16:     */   protected List<JaxbMetaElement> meta;
/*   17:     */   protected String subselect;
/*   18:     */   protected JaxbCacheElement cache;
/*   19:     */   protected List<JaxbSynchronizeElement> synchronize;
/*   20:     */   protected String comment;
/*   21:     */   @XmlElement(required=true)
/*   22:     */   protected JaxbKeyElement key;
/*   23:     */   protected JaxbIndexElement index;
/*   24:     */   @XmlElement(name="list-index")
/*   25:     */   protected JaxbListIndexElement listIndex;
/*   26:     */   protected JaxbElementElement element;
/*   27:     */   @XmlElement(name="one-to-many")
/*   28:     */   protected JaxbOneToManyElement oneToMany;
/*   29:     */   @XmlElement(name="many-to-many")
/*   30:     */   protected JaxbManyToManyElement manyToMany;
/*   31:     */   @XmlElement(name="composite-element")
/*   32:     */   protected JaxbCompositeElementElement compositeElement;
/*   33:     */   @XmlElement(name="many-to-any")
/*   34:     */   protected JaxbManyToAnyElement manyToAny;
/*   35:     */   protected JaxbLoaderElement loader;
/*   36:     */   @XmlElement(name="sql-insert")
/*   37:     */   protected JaxbSqlInsertElement sqlInsert;
/*   38:     */   @XmlElement(name="sql-update")
/*   39:     */   protected JaxbSqlUpdateElement sqlUpdate;
/*   40:     */   @XmlElement(name="sql-delete")
/*   41:     */   protected JaxbSqlDeleteElement sqlDelete;
/*   42:     */   @XmlElement(name="sql-delete-all")
/*   43:     */   protected JaxbSqlDeleteAllElement sqlDeleteAll;
/*   44:     */   protected List<JaxbFilterElement> filter;
/*   45:     */   @XmlAttribute
/*   46:     */   protected String access;
/*   47:     */   @XmlAttribute(name="batch-size")
/*   48:     */   protected String batchSize;
/*   49:     */   @XmlAttribute
/*   50:     */   protected String cascade;
/*   51:     */   @XmlAttribute
/*   52:     */   protected String catalog;
/*   53:     */   @XmlAttribute
/*   54:     */   protected String check;
/*   55:     */   @XmlAttribute(name="collection-type")
/*   56:     */   protected String collectionType;
/*   57:     */   @XmlAttribute(name="embed-xml")
/*   58:     */   protected Boolean embedXml;
/*   59:     */   @XmlAttribute
/*   60:     */   protected JaxbFetchAttributeWithSubselect fetch;
/*   61:     */   @XmlAttribute
/*   62:     */   protected Boolean inverse;
/*   63:     */   @XmlAttribute
/*   64:     */   protected JaxbLazyAttributeWithExtra lazy;
/*   65:     */   @XmlAttribute
/*   66:     */   protected Boolean mutable;
/*   67:     */   @XmlAttribute(required=true)
/*   68:     */   protected String name;
/*   69:     */   @XmlAttribute
/*   70:     */   protected String node;
/*   71:     */   @XmlAttribute(name="optimistic-lock")
/*   72:     */   protected Boolean optimisticLock;
/*   73:     */   @XmlAttribute(name="outer-join")
/*   74:     */   protected JaxbOuterJoinAttribute outerJoin;
/*   75:     */   @XmlAttribute
/*   76:     */   protected String persister;
/*   77:     */   @XmlAttribute
/*   78:     */   protected String schema;
/*   79:     */   @XmlAttribute(name="subselect")
/*   80:     */   protected String subselectAttribute;
/*   81:     */   @XmlAttribute
/*   82:     */   protected String table;
/*   83:     */   @XmlAttribute
/*   84:     */   protected String where;
/*   85:     */   
/*   86:     */   public List<JaxbMetaElement> getMeta()
/*   87:     */   {
/*   88: 199 */     if (this.meta == null) {
/*   89: 200 */       this.meta = new ArrayList();
/*   90:     */     }
/*   91: 202 */     return this.meta;
/*   92:     */   }
/*   93:     */   
/*   94:     */   public String getSubselect()
/*   95:     */   {
/*   96: 214 */     return this.subselect;
/*   97:     */   }
/*   98:     */   
/*   99:     */   public void setSubselect(String value)
/*  100:     */   {
/*  101: 226 */     this.subselect = value;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public JaxbCacheElement getCache()
/*  105:     */   {
/*  106: 238 */     return this.cache;
/*  107:     */   }
/*  108:     */   
/*  109:     */   public void setCache(JaxbCacheElement value)
/*  110:     */   {
/*  111: 250 */     this.cache = value;
/*  112:     */   }
/*  113:     */   
/*  114:     */   public List<JaxbSynchronizeElement> getSynchronize()
/*  115:     */   {
/*  116: 276 */     if (this.synchronize == null) {
/*  117: 277 */       this.synchronize = new ArrayList();
/*  118:     */     }
/*  119: 279 */     return this.synchronize;
/*  120:     */   }
/*  121:     */   
/*  122:     */   public String getComment()
/*  123:     */   {
/*  124: 291 */     return this.comment;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public void setComment(String value)
/*  128:     */   {
/*  129: 303 */     this.comment = value;
/*  130:     */   }
/*  131:     */   
/*  132:     */   public JaxbKeyElement getKey()
/*  133:     */   {
/*  134: 315 */     return this.key;
/*  135:     */   }
/*  136:     */   
/*  137:     */   public void setKey(JaxbKeyElement value)
/*  138:     */   {
/*  139: 327 */     this.key = value;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public JaxbIndexElement getIndex()
/*  143:     */   {
/*  144: 339 */     return this.index;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public void setIndex(JaxbIndexElement value)
/*  148:     */   {
/*  149: 351 */     this.index = value;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public JaxbListIndexElement getListIndex()
/*  153:     */   {
/*  154: 363 */     return this.listIndex;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void setListIndex(JaxbListIndexElement value)
/*  158:     */   {
/*  159: 375 */     this.listIndex = value;
/*  160:     */   }
/*  161:     */   
/*  162:     */   public JaxbElementElement getElement()
/*  163:     */   {
/*  164: 387 */     return this.element;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public void setElement(JaxbElementElement value)
/*  168:     */   {
/*  169: 399 */     this.element = value;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public JaxbOneToManyElement getOneToMany()
/*  173:     */   {
/*  174: 411 */     return this.oneToMany;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public void setOneToMany(JaxbOneToManyElement value)
/*  178:     */   {
/*  179: 423 */     this.oneToMany = value;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public JaxbManyToManyElement getManyToMany()
/*  183:     */   {
/*  184: 435 */     return this.manyToMany;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public void setManyToMany(JaxbManyToManyElement value)
/*  188:     */   {
/*  189: 447 */     this.manyToMany = value;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public JaxbCompositeElementElement getCompositeElement()
/*  193:     */   {
/*  194: 459 */     return this.compositeElement;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public void setCompositeElement(JaxbCompositeElementElement value)
/*  198:     */   {
/*  199: 471 */     this.compositeElement = value;
/*  200:     */   }
/*  201:     */   
/*  202:     */   public JaxbManyToAnyElement getManyToAny()
/*  203:     */   {
/*  204: 483 */     return this.manyToAny;
/*  205:     */   }
/*  206:     */   
/*  207:     */   public void setManyToAny(JaxbManyToAnyElement value)
/*  208:     */   {
/*  209: 495 */     this.manyToAny = value;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public JaxbLoaderElement getLoader()
/*  213:     */   {
/*  214: 507 */     return this.loader;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public void setLoader(JaxbLoaderElement value)
/*  218:     */   {
/*  219: 519 */     this.loader = value;
/*  220:     */   }
/*  221:     */   
/*  222:     */   public JaxbSqlInsertElement getSqlInsert()
/*  223:     */   {
/*  224: 531 */     return this.sqlInsert;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public void setSqlInsert(JaxbSqlInsertElement value)
/*  228:     */   {
/*  229: 543 */     this.sqlInsert = value;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public JaxbSqlUpdateElement getSqlUpdate()
/*  233:     */   {
/*  234: 555 */     return this.sqlUpdate;
/*  235:     */   }
/*  236:     */   
/*  237:     */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  238:     */   {
/*  239: 567 */     this.sqlUpdate = value;
/*  240:     */   }
/*  241:     */   
/*  242:     */   public JaxbSqlDeleteElement getSqlDelete()
/*  243:     */   {
/*  244: 579 */     return this.sqlDelete;
/*  245:     */   }
/*  246:     */   
/*  247:     */   public void setSqlDelete(JaxbSqlDeleteElement value)
/*  248:     */   {
/*  249: 591 */     this.sqlDelete = value;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public JaxbSqlDeleteAllElement getSqlDeleteAll()
/*  253:     */   {
/*  254: 603 */     return this.sqlDeleteAll;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public void setSqlDeleteAll(JaxbSqlDeleteAllElement value)
/*  258:     */   {
/*  259: 615 */     this.sqlDeleteAll = value;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public List<JaxbFilterElement> getFilter()
/*  263:     */   {
/*  264: 641 */     if (this.filter == null) {
/*  265: 642 */       this.filter = new ArrayList();
/*  266:     */     }
/*  267: 644 */     return this.filter;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public String getAccess()
/*  271:     */   {
/*  272: 656 */     return this.access;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public void setAccess(String value)
/*  276:     */   {
/*  277: 668 */     this.access = value;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public String getBatchSize()
/*  281:     */   {
/*  282: 680 */     return this.batchSize;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public void setBatchSize(String value)
/*  286:     */   {
/*  287: 692 */     this.batchSize = value;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public String getCascade()
/*  291:     */   {
/*  292: 704 */     return this.cascade;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public void setCascade(String value)
/*  296:     */   {
/*  297: 716 */     this.cascade = value;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public String getCatalog()
/*  301:     */   {
/*  302: 728 */     return this.catalog;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public void setCatalog(String value)
/*  306:     */   {
/*  307: 740 */     this.catalog = value;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public String getCheck()
/*  311:     */   {
/*  312: 752 */     return this.check;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public void setCheck(String value)
/*  316:     */   {
/*  317: 764 */     this.check = value;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public String getCollectionType()
/*  321:     */   {
/*  322: 776 */     return this.collectionType;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public void setCollectionType(String value)
/*  326:     */   {
/*  327: 788 */     this.collectionType = value;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public boolean isEmbedXml()
/*  331:     */   {
/*  332: 800 */     if (this.embedXml == null) {
/*  333: 801 */       return true;
/*  334:     */     }
/*  335: 803 */     return this.embedXml.booleanValue();
/*  336:     */   }
/*  337:     */   
/*  338:     */   public void setEmbedXml(Boolean value)
/*  339:     */   {
/*  340: 816 */     this.embedXml = value;
/*  341:     */   }
/*  342:     */   
/*  343:     */   public JaxbFetchAttributeWithSubselect getFetch()
/*  344:     */   {
/*  345: 828 */     return this.fetch;
/*  346:     */   }
/*  347:     */   
/*  348:     */   public void setFetch(JaxbFetchAttributeWithSubselect value)
/*  349:     */   {
/*  350: 840 */     this.fetch = value;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public boolean isInverse()
/*  354:     */   {
/*  355: 852 */     if (this.inverse == null) {
/*  356: 853 */       return false;
/*  357:     */     }
/*  358: 855 */     return this.inverse.booleanValue();
/*  359:     */   }
/*  360:     */   
/*  361:     */   public void setInverse(Boolean value)
/*  362:     */   {
/*  363: 868 */     this.inverse = value;
/*  364:     */   }
/*  365:     */   
/*  366:     */   public JaxbLazyAttributeWithExtra getLazy()
/*  367:     */   {
/*  368: 880 */     return this.lazy;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public void setLazy(JaxbLazyAttributeWithExtra value)
/*  372:     */   {
/*  373: 892 */     this.lazy = value;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public boolean isMutable()
/*  377:     */   {
/*  378: 904 */     if (this.mutable == null) {
/*  379: 905 */       return true;
/*  380:     */     }
/*  381: 907 */     return this.mutable.booleanValue();
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void setMutable(Boolean value)
/*  385:     */   {
/*  386: 920 */     this.mutable = value;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public String getName()
/*  390:     */   {
/*  391: 932 */     return this.name;
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void setName(String value)
/*  395:     */   {
/*  396: 944 */     this.name = value;
/*  397:     */   }
/*  398:     */   
/*  399:     */   public String getNode()
/*  400:     */   {
/*  401: 956 */     return this.node;
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void setNode(String value)
/*  405:     */   {
/*  406: 968 */     this.node = value;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public boolean isOptimisticLock()
/*  410:     */   {
/*  411: 980 */     if (this.optimisticLock == null) {
/*  412: 981 */       return true;
/*  413:     */     }
/*  414: 983 */     return this.optimisticLock.booleanValue();
/*  415:     */   }
/*  416:     */   
/*  417:     */   public void setOptimisticLock(Boolean value)
/*  418:     */   {
/*  419: 996 */     this.optimisticLock = value;
/*  420:     */   }
/*  421:     */   
/*  422:     */   public JaxbOuterJoinAttribute getOuterJoin()
/*  423:     */   {
/*  424:1008 */     return this.outerJoin;
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/*  428:     */   {
/*  429:1020 */     this.outerJoin = value;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public String getPersister()
/*  433:     */   {
/*  434:1032 */     return this.persister;
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void setPersister(String value)
/*  438:     */   {
/*  439:1044 */     this.persister = value;
/*  440:     */   }
/*  441:     */   
/*  442:     */   public String getSchema()
/*  443:     */   {
/*  444:1056 */     return this.schema;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public void setSchema(String value)
/*  448:     */   {
/*  449:1068 */     this.schema = value;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public String getSubselectAttribute()
/*  453:     */   {
/*  454:1080 */     return this.subselectAttribute;
/*  455:     */   }
/*  456:     */   
/*  457:     */   public void setSubselectAttribute(String value)
/*  458:     */   {
/*  459:1092 */     this.subselectAttribute = value;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public String getTable()
/*  463:     */   {
/*  464:1104 */     return this.table;
/*  465:     */   }
/*  466:     */   
/*  467:     */   public void setTable(String value)
/*  468:     */   {
/*  469:1116 */     this.table = value;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public String getWhere()
/*  473:     */   {
/*  474:1128 */     return this.where;
/*  475:     */   }
/*  476:     */   
/*  477:     */   public void setWhere(String value)
/*  478:     */   {
/*  479:1140 */     this.where = value;
/*  480:     */   }
/*  481:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbListElement
 * JD-Core Version:    0.7.0.1
 */