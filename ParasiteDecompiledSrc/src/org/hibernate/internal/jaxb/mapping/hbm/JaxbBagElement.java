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
/*   12:     */ @XmlType(name="bag-element", propOrder={"meta", "subselect", "cache", "synchronize", "comment", "key", "element", "oneToMany", "manyToMany", "compositeElement", "manyToAny", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "sqlDeleteAll", "filter"})
/*   13:     */ public class JaxbBagElement
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
/*   78:     */   @XmlAttribute(name="subselect")
/*   79:     */   protected String subselectAttribute;
/*   80:     */   @XmlAttribute
/*   81:     */   protected String table;
/*   82:     */   @XmlAttribute
/*   83:     */   protected String where;
/*   84:     */   
/*   85:     */   public List<JaxbMetaElement> getMeta()
/*   86:     */   {
/*   87: 193 */     if (this.meta == null) {
/*   88: 194 */       this.meta = new ArrayList();
/*   89:     */     }
/*   90: 196 */     return this.meta;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public String getSubselect()
/*   94:     */   {
/*   95: 208 */     return this.subselect;
/*   96:     */   }
/*   97:     */   
/*   98:     */   public void setSubselect(String value)
/*   99:     */   {
/*  100: 220 */     this.subselect = value;
/*  101:     */   }
/*  102:     */   
/*  103:     */   public JaxbCacheElement getCache()
/*  104:     */   {
/*  105: 232 */     return this.cache;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public void setCache(JaxbCacheElement value)
/*  109:     */   {
/*  110: 244 */     this.cache = value;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public List<JaxbSynchronizeElement> getSynchronize()
/*  114:     */   {
/*  115: 270 */     if (this.synchronize == null) {
/*  116: 271 */       this.synchronize = new ArrayList();
/*  117:     */     }
/*  118: 273 */     return this.synchronize;
/*  119:     */   }
/*  120:     */   
/*  121:     */   public String getComment()
/*  122:     */   {
/*  123: 285 */     return this.comment;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public void setComment(String value)
/*  127:     */   {
/*  128: 297 */     this.comment = value;
/*  129:     */   }
/*  130:     */   
/*  131:     */   public JaxbKeyElement getKey()
/*  132:     */   {
/*  133: 309 */     return this.key;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public void setKey(JaxbKeyElement value)
/*  137:     */   {
/*  138: 321 */     this.key = value;
/*  139:     */   }
/*  140:     */   
/*  141:     */   public JaxbElementElement getElement()
/*  142:     */   {
/*  143: 333 */     return this.element;
/*  144:     */   }
/*  145:     */   
/*  146:     */   public void setElement(JaxbElementElement value)
/*  147:     */   {
/*  148: 345 */     this.element = value;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public JaxbOneToManyElement getOneToMany()
/*  152:     */   {
/*  153: 357 */     return this.oneToMany;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public void setOneToMany(JaxbOneToManyElement value)
/*  157:     */   {
/*  158: 369 */     this.oneToMany = value;
/*  159:     */   }
/*  160:     */   
/*  161:     */   public JaxbManyToManyElement getManyToMany()
/*  162:     */   {
/*  163: 381 */     return this.manyToMany;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public void setManyToMany(JaxbManyToManyElement value)
/*  167:     */   {
/*  168: 393 */     this.manyToMany = value;
/*  169:     */   }
/*  170:     */   
/*  171:     */   public JaxbCompositeElementElement getCompositeElement()
/*  172:     */   {
/*  173: 405 */     return this.compositeElement;
/*  174:     */   }
/*  175:     */   
/*  176:     */   public void setCompositeElement(JaxbCompositeElementElement value)
/*  177:     */   {
/*  178: 417 */     this.compositeElement = value;
/*  179:     */   }
/*  180:     */   
/*  181:     */   public JaxbManyToAnyElement getManyToAny()
/*  182:     */   {
/*  183: 429 */     return this.manyToAny;
/*  184:     */   }
/*  185:     */   
/*  186:     */   public void setManyToAny(JaxbManyToAnyElement value)
/*  187:     */   {
/*  188: 441 */     this.manyToAny = value;
/*  189:     */   }
/*  190:     */   
/*  191:     */   public JaxbLoaderElement getLoader()
/*  192:     */   {
/*  193: 453 */     return this.loader;
/*  194:     */   }
/*  195:     */   
/*  196:     */   public void setLoader(JaxbLoaderElement value)
/*  197:     */   {
/*  198: 465 */     this.loader = value;
/*  199:     */   }
/*  200:     */   
/*  201:     */   public JaxbSqlInsertElement getSqlInsert()
/*  202:     */   {
/*  203: 477 */     return this.sqlInsert;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void setSqlInsert(JaxbSqlInsertElement value)
/*  207:     */   {
/*  208: 489 */     this.sqlInsert = value;
/*  209:     */   }
/*  210:     */   
/*  211:     */   public JaxbSqlUpdateElement getSqlUpdate()
/*  212:     */   {
/*  213: 501 */     return this.sqlUpdate;
/*  214:     */   }
/*  215:     */   
/*  216:     */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  217:     */   {
/*  218: 513 */     this.sqlUpdate = value;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public JaxbSqlDeleteElement getSqlDelete()
/*  222:     */   {
/*  223: 525 */     return this.sqlDelete;
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void setSqlDelete(JaxbSqlDeleteElement value)
/*  227:     */   {
/*  228: 537 */     this.sqlDelete = value;
/*  229:     */   }
/*  230:     */   
/*  231:     */   public JaxbSqlDeleteAllElement getSqlDeleteAll()
/*  232:     */   {
/*  233: 549 */     return this.sqlDeleteAll;
/*  234:     */   }
/*  235:     */   
/*  236:     */   public void setSqlDeleteAll(JaxbSqlDeleteAllElement value)
/*  237:     */   {
/*  238: 561 */     this.sqlDeleteAll = value;
/*  239:     */   }
/*  240:     */   
/*  241:     */   public List<JaxbFilterElement> getFilter()
/*  242:     */   {
/*  243: 587 */     if (this.filter == null) {
/*  244: 588 */       this.filter = new ArrayList();
/*  245:     */     }
/*  246: 590 */     return this.filter;
/*  247:     */   }
/*  248:     */   
/*  249:     */   public String getAccess()
/*  250:     */   {
/*  251: 602 */     return this.access;
/*  252:     */   }
/*  253:     */   
/*  254:     */   public void setAccess(String value)
/*  255:     */   {
/*  256: 614 */     this.access = value;
/*  257:     */   }
/*  258:     */   
/*  259:     */   public String getBatchSize()
/*  260:     */   {
/*  261: 626 */     return this.batchSize;
/*  262:     */   }
/*  263:     */   
/*  264:     */   public void setBatchSize(String value)
/*  265:     */   {
/*  266: 638 */     this.batchSize = value;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public String getCascade()
/*  270:     */   {
/*  271: 650 */     return this.cascade;
/*  272:     */   }
/*  273:     */   
/*  274:     */   public void setCascade(String value)
/*  275:     */   {
/*  276: 662 */     this.cascade = value;
/*  277:     */   }
/*  278:     */   
/*  279:     */   public String getCatalog()
/*  280:     */   {
/*  281: 674 */     return this.catalog;
/*  282:     */   }
/*  283:     */   
/*  284:     */   public void setCatalog(String value)
/*  285:     */   {
/*  286: 686 */     this.catalog = value;
/*  287:     */   }
/*  288:     */   
/*  289:     */   public String getCheck()
/*  290:     */   {
/*  291: 698 */     return this.check;
/*  292:     */   }
/*  293:     */   
/*  294:     */   public void setCheck(String value)
/*  295:     */   {
/*  296: 710 */     this.check = value;
/*  297:     */   }
/*  298:     */   
/*  299:     */   public String getCollectionType()
/*  300:     */   {
/*  301: 722 */     return this.collectionType;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public void setCollectionType(String value)
/*  305:     */   {
/*  306: 734 */     this.collectionType = value;
/*  307:     */   }
/*  308:     */   
/*  309:     */   public boolean isEmbedXml()
/*  310:     */   {
/*  311: 746 */     if (this.embedXml == null) {
/*  312: 747 */       return true;
/*  313:     */     }
/*  314: 749 */     return this.embedXml.booleanValue();
/*  315:     */   }
/*  316:     */   
/*  317:     */   public void setEmbedXml(Boolean value)
/*  318:     */   {
/*  319: 762 */     this.embedXml = value;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public JaxbFetchAttributeWithSubselect getFetch()
/*  323:     */   {
/*  324: 774 */     return this.fetch;
/*  325:     */   }
/*  326:     */   
/*  327:     */   public void setFetch(JaxbFetchAttributeWithSubselect value)
/*  328:     */   {
/*  329: 786 */     this.fetch = value;
/*  330:     */   }
/*  331:     */   
/*  332:     */   public boolean isInverse()
/*  333:     */   {
/*  334: 798 */     if (this.inverse == null) {
/*  335: 799 */       return false;
/*  336:     */     }
/*  337: 801 */     return this.inverse.booleanValue();
/*  338:     */   }
/*  339:     */   
/*  340:     */   public void setInverse(Boolean value)
/*  341:     */   {
/*  342: 814 */     this.inverse = value;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public JaxbLazyAttributeWithExtra getLazy()
/*  346:     */   {
/*  347: 826 */     return this.lazy;
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void setLazy(JaxbLazyAttributeWithExtra value)
/*  351:     */   {
/*  352: 838 */     this.lazy = value;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public boolean isMutable()
/*  356:     */   {
/*  357: 850 */     if (this.mutable == null) {
/*  358: 851 */       return true;
/*  359:     */     }
/*  360: 853 */     return this.mutable.booleanValue();
/*  361:     */   }
/*  362:     */   
/*  363:     */   public void setMutable(Boolean value)
/*  364:     */   {
/*  365: 866 */     this.mutable = value;
/*  366:     */   }
/*  367:     */   
/*  368:     */   public String getName()
/*  369:     */   {
/*  370: 878 */     return this.name;
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void setName(String value)
/*  374:     */   {
/*  375: 890 */     this.name = value;
/*  376:     */   }
/*  377:     */   
/*  378:     */   public String getNode()
/*  379:     */   {
/*  380: 902 */     return this.node;
/*  381:     */   }
/*  382:     */   
/*  383:     */   public void setNode(String value)
/*  384:     */   {
/*  385: 914 */     this.node = value;
/*  386:     */   }
/*  387:     */   
/*  388:     */   public boolean isOptimisticLock()
/*  389:     */   {
/*  390: 926 */     if (this.optimisticLock == null) {
/*  391: 927 */       return true;
/*  392:     */     }
/*  393: 929 */     return this.optimisticLock.booleanValue();
/*  394:     */   }
/*  395:     */   
/*  396:     */   public void setOptimisticLock(Boolean value)
/*  397:     */   {
/*  398: 942 */     this.optimisticLock = value;
/*  399:     */   }
/*  400:     */   
/*  401:     */   public String getOrderBy()
/*  402:     */   {
/*  403: 954 */     return this.orderBy;
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void setOrderBy(String value)
/*  407:     */   {
/*  408: 966 */     this.orderBy = value;
/*  409:     */   }
/*  410:     */   
/*  411:     */   public JaxbOuterJoinAttribute getOuterJoin()
/*  412:     */   {
/*  413: 978 */     return this.outerJoin;
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/*  417:     */   {
/*  418: 990 */     this.outerJoin = value;
/*  419:     */   }
/*  420:     */   
/*  421:     */   public String getPersister()
/*  422:     */   {
/*  423:1002 */     return this.persister;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void setPersister(String value)
/*  427:     */   {
/*  428:1014 */     this.persister = value;
/*  429:     */   }
/*  430:     */   
/*  431:     */   public String getSchema()
/*  432:     */   {
/*  433:1026 */     return this.schema;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void setSchema(String value)
/*  437:     */   {
/*  438:1038 */     this.schema = value;
/*  439:     */   }
/*  440:     */   
/*  441:     */   public String getSubselectAttribute()
/*  442:     */   {
/*  443:1050 */     return this.subselectAttribute;
/*  444:     */   }
/*  445:     */   
/*  446:     */   public void setSubselectAttribute(String value)
/*  447:     */   {
/*  448:1062 */     this.subselectAttribute = value;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public String getTable()
/*  452:     */   {
/*  453:1074 */     return this.table;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void setTable(String value)
/*  457:     */   {
/*  458:1086 */     this.table = value;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public String getWhere()
/*  462:     */   {
/*  463:1098 */     return this.where;
/*  464:     */   }
/*  465:     */   
/*  466:     */   public void setWhere(String value)
/*  467:     */   {
/*  468:1110 */     this.where = value;
/*  469:     */   }
/*  470:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbBagElement
 * JD-Core Version:    0.7.0.1
 */