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
/*   12:     */ @XmlType(name="set-element", propOrder={"meta", "subselect", "cache", "synchronize", "comment", "key", "element", "oneToMany", "manyToMany", "compositeElement", "manyToAny", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "sqlDeleteAll", "filter"})
/*   13:     */ public class JaxbSetElement
/*   14:     */   implements PluralAttributeElement
/*   15:     */ {
/*   16:     */   protected List<JaxbMetaElement> meta;
/*   17:     */   protected String subselect;
/*   18:     */   protected JaxbCacheElement cache;
/*   19:     */   protected List<JaxbSynchronizeElement> synchronize;
/*   20:     */   protected String comment;
/*   21:     */   @XmlElement(required=true)
/*   22:     */   protected JaxbKeyElement key;
/*   23:     */   protected JaxbElementElement element;
/*   24:     */   @XmlElement(name="one-to-many")
/*   25:     */   protected JaxbOneToManyElement oneToMany;
/*   26:     */   @XmlElement(name="many-to-many")
/*   27:     */   protected JaxbManyToManyElement manyToMany;
/*   28:     */   @XmlElement(name="composite-element")
/*   29:     */   protected JaxbCompositeElementElement compositeElement;
/*   30:     */   @XmlElement(name="many-to-any")
/*   31:     */   protected JaxbManyToAnyElement manyToAny;
/*   32:     */   protected JaxbLoaderElement loader;
/*   33:     */   @XmlElement(name="sql-insert")
/*   34:     */   protected JaxbSqlInsertElement sqlInsert;
/*   35:     */   @XmlElement(name="sql-update")
/*   36:     */   protected JaxbSqlUpdateElement sqlUpdate;
/*   37:     */   @XmlElement(name="sql-delete")
/*   38:     */   protected JaxbSqlDeleteElement sqlDelete;
/*   39:     */   @XmlElement(name="sql-delete-all")
/*   40:     */   protected JaxbSqlDeleteAllElement sqlDeleteAll;
/*   41:     */   protected List<JaxbFilterElement> filter;
/*   42:     */   @XmlAttribute
/*   43:     */   protected String access;
/*   44:     */   @XmlAttribute(name="batch-size")
/*   45:     */   protected String batchSize;
/*   46:     */   @XmlAttribute
/*   47:     */   protected String cascade;
/*   48:     */   @XmlAttribute
/*   49:     */   protected String catalog;
/*   50:     */   @XmlAttribute
/*   51:     */   protected String check;
/*   52:     */   @XmlAttribute(name="collection-type")
/*   53:     */   protected String collectionType;
/*   54:     */   @XmlAttribute(name="embed-xml")
/*   55:     */   protected Boolean embedXml;
/*   56:     */   @XmlAttribute
/*   57:     */   protected JaxbFetchAttributeWithSubselect fetch;
/*   58:     */   @XmlAttribute
/*   59:     */   protected Boolean inverse;
/*   60:     */   @XmlAttribute
/*   61:     */   protected JaxbLazyAttributeWithExtra lazy;
/*   62:     */   @XmlAttribute
/*   63:     */   protected Boolean mutable;
/*   64:     */   @XmlAttribute(required=true)
/*   65:     */   protected String name;
/*   66:     */   @XmlAttribute
/*   67:     */   protected String node;
/*   68:     */   @XmlAttribute(name="optimistic-lock")
/*   69:     */   protected Boolean optimisticLock;
/*   70:     */   @XmlAttribute(name="order-by")
/*   71:     */   protected String orderBy;
/*   72:     */   @XmlAttribute(name="outer-join")
/*   73:     */   protected JaxbOuterJoinAttribute outerJoin;
/*   74:     */   @XmlAttribute
/*   75:     */   protected String persister;
/*   76:     */   @XmlAttribute
/*   77:     */   protected String schema;
/*   78:     */   @XmlAttribute
/*   79:     */   protected String sort;
/*   80:     */   @XmlAttribute(name="subselect")
/*   81:     */   protected String subselectAttribute;
/*   82:     */   @XmlAttribute
/*   83:     */   protected String table;
/*   84:     */   @XmlAttribute
/*   85:     */   protected String where;
/*   86:     */   
/*   87:     */   public List<JaxbMetaElement> getMeta()
/*   88:     */   {
/*   89: 196 */     if (this.meta == null) {
/*   90: 197 */       this.meta = new ArrayList();
/*   91:     */     }
/*   92: 199 */     return this.meta;
/*   93:     */   }
/*   94:     */   
/*   95:     */   public String getSubselect()
/*   96:     */   {
/*   97: 211 */     return this.subselect;
/*   98:     */   }
/*   99:     */   
/*  100:     */   public void setSubselect(String value)
/*  101:     */   {
/*  102: 223 */     this.subselect = value;
/*  103:     */   }
/*  104:     */   
/*  105:     */   public JaxbCacheElement getCache()
/*  106:     */   {
/*  107: 235 */     return this.cache;
/*  108:     */   }
/*  109:     */   
/*  110:     */   public void setCache(JaxbCacheElement value)
/*  111:     */   {
/*  112: 247 */     this.cache = value;
/*  113:     */   }
/*  114:     */   
/*  115:     */   public List<JaxbSynchronizeElement> getSynchronize()
/*  116:     */   {
/*  117: 273 */     if (this.synchronize == null) {
/*  118: 274 */       this.synchronize = new ArrayList();
/*  119:     */     }
/*  120: 276 */     return this.synchronize;
/*  121:     */   }
/*  122:     */   
/*  123:     */   public String getComment()
/*  124:     */   {
/*  125: 288 */     return this.comment;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public void setComment(String value)
/*  129:     */   {
/*  130: 300 */     this.comment = value;
/*  131:     */   }
/*  132:     */   
/*  133:     */   public JaxbKeyElement getKey()
/*  134:     */   {
/*  135: 312 */     return this.key;
/*  136:     */   }
/*  137:     */   
/*  138:     */   public void setKey(JaxbKeyElement value)
/*  139:     */   {
/*  140: 324 */     this.key = value;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public JaxbElementElement getElement()
/*  144:     */   {
/*  145: 336 */     return this.element;
/*  146:     */   }
/*  147:     */   
/*  148:     */   public void setElement(JaxbElementElement value)
/*  149:     */   {
/*  150: 348 */     this.element = value;
/*  151:     */   }
/*  152:     */   
/*  153:     */   public JaxbOneToManyElement getOneToMany()
/*  154:     */   {
/*  155: 360 */     return this.oneToMany;
/*  156:     */   }
/*  157:     */   
/*  158:     */   public void setOneToMany(JaxbOneToManyElement value)
/*  159:     */   {
/*  160: 372 */     this.oneToMany = value;
/*  161:     */   }
/*  162:     */   
/*  163:     */   public JaxbManyToManyElement getManyToMany()
/*  164:     */   {
/*  165: 384 */     return this.manyToMany;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public void setManyToMany(JaxbManyToManyElement value)
/*  169:     */   {
/*  170: 396 */     this.manyToMany = value;
/*  171:     */   }
/*  172:     */   
/*  173:     */   public JaxbCompositeElementElement getCompositeElement()
/*  174:     */   {
/*  175: 408 */     return this.compositeElement;
/*  176:     */   }
/*  177:     */   
/*  178:     */   public void setCompositeElement(JaxbCompositeElementElement value)
/*  179:     */   {
/*  180: 420 */     this.compositeElement = value;
/*  181:     */   }
/*  182:     */   
/*  183:     */   public JaxbManyToAnyElement getManyToAny()
/*  184:     */   {
/*  185: 432 */     return this.manyToAny;
/*  186:     */   }
/*  187:     */   
/*  188:     */   public void setManyToAny(JaxbManyToAnyElement value)
/*  189:     */   {
/*  190: 444 */     this.manyToAny = value;
/*  191:     */   }
/*  192:     */   
/*  193:     */   public JaxbLoaderElement getLoader()
/*  194:     */   {
/*  195: 456 */     return this.loader;
/*  196:     */   }
/*  197:     */   
/*  198:     */   public void setLoader(JaxbLoaderElement value)
/*  199:     */   {
/*  200: 468 */     this.loader = value;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public JaxbSqlInsertElement getSqlInsert()
/*  204:     */   {
/*  205: 480 */     return this.sqlInsert;
/*  206:     */   }
/*  207:     */   
/*  208:     */   public void setSqlInsert(JaxbSqlInsertElement value)
/*  209:     */   {
/*  210: 492 */     this.sqlInsert = value;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public JaxbSqlUpdateElement getSqlUpdate()
/*  214:     */   {
/*  215: 504 */     return this.sqlUpdate;
/*  216:     */   }
/*  217:     */   
/*  218:     */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  219:     */   {
/*  220: 516 */     this.sqlUpdate = value;
/*  221:     */   }
/*  222:     */   
/*  223:     */   public JaxbSqlDeleteElement getSqlDelete()
/*  224:     */   {
/*  225: 528 */     return this.sqlDelete;
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void setSqlDelete(JaxbSqlDeleteElement value)
/*  229:     */   {
/*  230: 540 */     this.sqlDelete = value;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public JaxbSqlDeleteAllElement getSqlDeleteAll()
/*  234:     */   {
/*  235: 552 */     return this.sqlDeleteAll;
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void setSqlDeleteAll(JaxbSqlDeleteAllElement value)
/*  239:     */   {
/*  240: 564 */     this.sqlDeleteAll = value;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public List<JaxbFilterElement> getFilter()
/*  244:     */   {
/*  245: 590 */     if (this.filter == null) {
/*  246: 591 */       this.filter = new ArrayList();
/*  247:     */     }
/*  248: 593 */     return this.filter;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public String getAccess()
/*  252:     */   {
/*  253: 605 */     return this.access;
/*  254:     */   }
/*  255:     */   
/*  256:     */   public void setAccess(String value)
/*  257:     */   {
/*  258: 617 */     this.access = value;
/*  259:     */   }
/*  260:     */   
/*  261:     */   public String getBatchSize()
/*  262:     */   {
/*  263: 629 */     return this.batchSize;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public void setBatchSize(String value)
/*  267:     */   {
/*  268: 641 */     this.batchSize = value;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public String getCascade()
/*  272:     */   {
/*  273: 653 */     return this.cascade;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public void setCascade(String value)
/*  277:     */   {
/*  278: 665 */     this.cascade = value;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public String getCatalog()
/*  282:     */   {
/*  283: 677 */     return this.catalog;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public void setCatalog(String value)
/*  287:     */   {
/*  288: 689 */     this.catalog = value;
/*  289:     */   }
/*  290:     */   
/*  291:     */   public String getCheck()
/*  292:     */   {
/*  293: 701 */     return this.check;
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void setCheck(String value)
/*  297:     */   {
/*  298: 713 */     this.check = value;
/*  299:     */   }
/*  300:     */   
/*  301:     */   public String getCollectionType()
/*  302:     */   {
/*  303: 725 */     return this.collectionType;
/*  304:     */   }
/*  305:     */   
/*  306:     */   public void setCollectionType(String value)
/*  307:     */   {
/*  308: 737 */     this.collectionType = value;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public boolean isEmbedXml()
/*  312:     */   {
/*  313: 749 */     if (this.embedXml == null) {
/*  314: 750 */       return true;
/*  315:     */     }
/*  316: 752 */     return this.embedXml.booleanValue();
/*  317:     */   }
/*  318:     */   
/*  319:     */   public void setEmbedXml(Boolean value)
/*  320:     */   {
/*  321: 765 */     this.embedXml = value;
/*  322:     */   }
/*  323:     */   
/*  324:     */   public JaxbFetchAttributeWithSubselect getFetch()
/*  325:     */   {
/*  326: 777 */     return this.fetch;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public void setFetch(JaxbFetchAttributeWithSubselect value)
/*  330:     */   {
/*  331: 789 */     this.fetch = value;
/*  332:     */   }
/*  333:     */   
/*  334:     */   public boolean isInverse()
/*  335:     */   {
/*  336: 801 */     if (this.inverse == null) {
/*  337: 802 */       return false;
/*  338:     */     }
/*  339: 804 */     return this.inverse.booleanValue();
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void setInverse(Boolean value)
/*  343:     */   {
/*  344: 817 */     this.inverse = value;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public JaxbLazyAttributeWithExtra getLazy()
/*  348:     */   {
/*  349: 829 */     return this.lazy;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public void setLazy(JaxbLazyAttributeWithExtra value)
/*  353:     */   {
/*  354: 841 */     this.lazy = value;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public boolean isMutable()
/*  358:     */   {
/*  359: 853 */     if (this.mutable == null) {
/*  360: 854 */       return true;
/*  361:     */     }
/*  362: 856 */     return this.mutable.booleanValue();
/*  363:     */   }
/*  364:     */   
/*  365:     */   public void setMutable(Boolean value)
/*  366:     */   {
/*  367: 869 */     this.mutable = value;
/*  368:     */   }
/*  369:     */   
/*  370:     */   public String getName()
/*  371:     */   {
/*  372: 881 */     return this.name;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void setName(String value)
/*  376:     */   {
/*  377: 893 */     this.name = value;
/*  378:     */   }
/*  379:     */   
/*  380:     */   public String getNode()
/*  381:     */   {
/*  382: 905 */     return this.node;
/*  383:     */   }
/*  384:     */   
/*  385:     */   public void setNode(String value)
/*  386:     */   {
/*  387: 917 */     this.node = value;
/*  388:     */   }
/*  389:     */   
/*  390:     */   public boolean isOptimisticLock()
/*  391:     */   {
/*  392: 929 */     if (this.optimisticLock == null) {
/*  393: 930 */       return true;
/*  394:     */     }
/*  395: 932 */     return this.optimisticLock.booleanValue();
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void setOptimisticLock(Boolean value)
/*  399:     */   {
/*  400: 945 */     this.optimisticLock = value;
/*  401:     */   }
/*  402:     */   
/*  403:     */   public String getOrderBy()
/*  404:     */   {
/*  405: 957 */     return this.orderBy;
/*  406:     */   }
/*  407:     */   
/*  408:     */   public void setOrderBy(String value)
/*  409:     */   {
/*  410: 969 */     this.orderBy = value;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public JaxbOuterJoinAttribute getOuterJoin()
/*  414:     */   {
/*  415: 981 */     return this.outerJoin;
/*  416:     */   }
/*  417:     */   
/*  418:     */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/*  419:     */   {
/*  420: 993 */     this.outerJoin = value;
/*  421:     */   }
/*  422:     */   
/*  423:     */   public String getPersister()
/*  424:     */   {
/*  425:1005 */     return this.persister;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void setPersister(String value)
/*  429:     */   {
/*  430:1017 */     this.persister = value;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public String getSchema()
/*  434:     */   {
/*  435:1029 */     return this.schema;
/*  436:     */   }
/*  437:     */   
/*  438:     */   public void setSchema(String value)
/*  439:     */   {
/*  440:1041 */     this.schema = value;
/*  441:     */   }
/*  442:     */   
/*  443:     */   public String getSort()
/*  444:     */   {
/*  445:1053 */     if (this.sort == null) {
/*  446:1054 */       return "unsorted";
/*  447:     */     }
/*  448:1056 */     return this.sort;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public void setSort(String value)
/*  452:     */   {
/*  453:1069 */     this.sort = value;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public String getSubselectAttribute()
/*  457:     */   {
/*  458:1081 */     return this.subselectAttribute;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public void setSubselectAttribute(String value)
/*  462:     */   {
/*  463:1093 */     this.subselectAttribute = value;
/*  464:     */   }
/*  465:     */   
/*  466:     */   public String getTable()
/*  467:     */   {
/*  468:1105 */     return this.table;
/*  469:     */   }
/*  470:     */   
/*  471:     */   public void setTable(String value)
/*  472:     */   {
/*  473:1117 */     this.table = value;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public String getWhere()
/*  477:     */   {
/*  478:1129 */     return this.where;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public void setWhere(String value)
/*  482:     */   {
/*  483:1141 */     this.where = value;
/*  484:     */   }
/*  485:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbSetElement
 * JD-Core Version:    0.7.0.1
 */