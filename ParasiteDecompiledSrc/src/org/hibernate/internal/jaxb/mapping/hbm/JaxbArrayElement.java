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
/*   12:     */ @XmlType(name="array-element", propOrder={"meta", "subselect", "cache", "synchronize", "comment", "key", "index", "listIndex", "element", "oneToMany", "manyToMany", "compositeElement", "manyToAny", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "sqlDeleteAll"})
/*   13:     */ public class JaxbArrayElement
/*   14:     */ {
/*   15:     */   protected List<JaxbMetaElement> meta;
/*   16:     */   protected String subselect;
/*   17:     */   protected JaxbCacheElement cache;
/*   18:     */   protected List<JaxbSynchronizeElement> synchronize;
/*   19:     */   protected String comment;
/*   20:     */   @XmlElement(required=true)
/*   21:     */   protected JaxbKeyElement key;
/*   22:     */   protected JaxbIndexElement index;
/*   23:     */   @XmlElement(name="list-index")
/*   24:     */   protected JaxbListIndexElement listIndex;
/*   25:     */   protected JaxbElementElement element;
/*   26:     */   @XmlElement(name="one-to-many")
/*   27:     */   protected JaxbOneToManyElement oneToMany;
/*   28:     */   @XmlElement(name="many-to-many")
/*   29:     */   protected JaxbManyToManyElement manyToMany;
/*   30:     */   @XmlElement(name="composite-element")
/*   31:     */   protected JaxbCompositeElementElement compositeElement;
/*   32:     */   @XmlElement(name="many-to-any")
/*   33:     */   protected JaxbManyToAnyElement manyToAny;
/*   34:     */   protected JaxbLoaderElement loader;
/*   35:     */   @XmlElement(name="sql-insert")
/*   36:     */   protected JaxbSqlInsertElement sqlInsert;
/*   37:     */   @XmlElement(name="sql-update")
/*   38:     */   protected JaxbSqlUpdateElement sqlUpdate;
/*   39:     */   @XmlElement(name="sql-delete")
/*   40:     */   protected JaxbSqlDeleteElement sqlDelete;
/*   41:     */   @XmlElement(name="sql-delete-all")
/*   42:     */   protected JaxbSqlDeleteAllElement sqlDeleteAll;
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
/*   55:     */   @XmlAttribute(name="element-class")
/*   56:     */   protected String elementClass;
/*   57:     */   @XmlAttribute(name="embed-xml")
/*   58:     */   protected Boolean embedXml;
/*   59:     */   @XmlAttribute
/*   60:     */   protected JaxbFetchAttributeWithSubselect fetch;
/*   61:     */   @XmlAttribute
/*   62:     */   protected Boolean inverse;
/*   63:     */   @XmlAttribute
/*   64:     */   protected Boolean mutable;
/*   65:     */   @XmlAttribute(required=true)
/*   66:     */   protected String name;
/*   67:     */   @XmlAttribute
/*   68:     */   protected String node;
/*   69:     */   @XmlAttribute(name="optimistic-lock")
/*   70:     */   protected Boolean optimisticLock;
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
/*   86: 195 */     if (this.meta == null) {
/*   87: 196 */       this.meta = new ArrayList();
/*   88:     */     }
/*   89: 198 */     return this.meta;
/*   90:     */   }
/*   91:     */   
/*   92:     */   public String getSubselect()
/*   93:     */   {
/*   94: 210 */     return this.subselect;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public void setSubselect(String value)
/*   98:     */   {
/*   99: 222 */     this.subselect = value;
/*  100:     */   }
/*  101:     */   
/*  102:     */   public JaxbCacheElement getCache()
/*  103:     */   {
/*  104: 234 */     return this.cache;
/*  105:     */   }
/*  106:     */   
/*  107:     */   public void setCache(JaxbCacheElement value)
/*  108:     */   {
/*  109: 246 */     this.cache = value;
/*  110:     */   }
/*  111:     */   
/*  112:     */   public List<JaxbSynchronizeElement> getSynchronize()
/*  113:     */   {
/*  114: 272 */     if (this.synchronize == null) {
/*  115: 273 */       this.synchronize = new ArrayList();
/*  116:     */     }
/*  117: 275 */     return this.synchronize;
/*  118:     */   }
/*  119:     */   
/*  120:     */   public String getComment()
/*  121:     */   {
/*  122: 287 */     return this.comment;
/*  123:     */   }
/*  124:     */   
/*  125:     */   public void setComment(String value)
/*  126:     */   {
/*  127: 299 */     this.comment = value;
/*  128:     */   }
/*  129:     */   
/*  130:     */   public JaxbKeyElement getKey()
/*  131:     */   {
/*  132: 311 */     return this.key;
/*  133:     */   }
/*  134:     */   
/*  135:     */   public void setKey(JaxbKeyElement value)
/*  136:     */   {
/*  137: 323 */     this.key = value;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public JaxbIndexElement getIndex()
/*  141:     */   {
/*  142: 335 */     return this.index;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public void setIndex(JaxbIndexElement value)
/*  146:     */   {
/*  147: 347 */     this.index = value;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public JaxbListIndexElement getListIndex()
/*  151:     */   {
/*  152: 359 */     return this.listIndex;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void setListIndex(JaxbListIndexElement value)
/*  156:     */   {
/*  157: 371 */     this.listIndex = value;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public JaxbElementElement getElement()
/*  161:     */   {
/*  162: 383 */     return this.element;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public void setElement(JaxbElementElement value)
/*  166:     */   {
/*  167: 395 */     this.element = value;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public JaxbOneToManyElement getOneToMany()
/*  171:     */   {
/*  172: 407 */     return this.oneToMany;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public void setOneToMany(JaxbOneToManyElement value)
/*  176:     */   {
/*  177: 419 */     this.oneToMany = value;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public JaxbManyToManyElement getManyToMany()
/*  181:     */   {
/*  182: 431 */     return this.manyToMany;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void setManyToMany(JaxbManyToManyElement value)
/*  186:     */   {
/*  187: 443 */     this.manyToMany = value;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public JaxbCompositeElementElement getCompositeElement()
/*  191:     */   {
/*  192: 455 */     return this.compositeElement;
/*  193:     */   }
/*  194:     */   
/*  195:     */   public void setCompositeElement(JaxbCompositeElementElement value)
/*  196:     */   {
/*  197: 467 */     this.compositeElement = value;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public JaxbManyToAnyElement getManyToAny()
/*  201:     */   {
/*  202: 479 */     return this.manyToAny;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void setManyToAny(JaxbManyToAnyElement value)
/*  206:     */   {
/*  207: 491 */     this.manyToAny = value;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public JaxbLoaderElement getLoader()
/*  211:     */   {
/*  212: 503 */     return this.loader;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void setLoader(JaxbLoaderElement value)
/*  216:     */   {
/*  217: 515 */     this.loader = value;
/*  218:     */   }
/*  219:     */   
/*  220:     */   public JaxbSqlInsertElement getSqlInsert()
/*  221:     */   {
/*  222: 527 */     return this.sqlInsert;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void setSqlInsert(JaxbSqlInsertElement value)
/*  226:     */   {
/*  227: 539 */     this.sqlInsert = value;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public JaxbSqlUpdateElement getSqlUpdate()
/*  231:     */   {
/*  232: 551 */     return this.sqlUpdate;
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  236:     */   {
/*  237: 563 */     this.sqlUpdate = value;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public JaxbSqlDeleteElement getSqlDelete()
/*  241:     */   {
/*  242: 575 */     return this.sqlDelete;
/*  243:     */   }
/*  244:     */   
/*  245:     */   public void setSqlDelete(JaxbSqlDeleteElement value)
/*  246:     */   {
/*  247: 587 */     this.sqlDelete = value;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public JaxbSqlDeleteAllElement getSqlDeleteAll()
/*  251:     */   {
/*  252: 599 */     return this.sqlDeleteAll;
/*  253:     */   }
/*  254:     */   
/*  255:     */   public void setSqlDeleteAll(JaxbSqlDeleteAllElement value)
/*  256:     */   {
/*  257: 611 */     this.sqlDeleteAll = value;
/*  258:     */   }
/*  259:     */   
/*  260:     */   public String getAccess()
/*  261:     */   {
/*  262: 623 */     return this.access;
/*  263:     */   }
/*  264:     */   
/*  265:     */   public void setAccess(String value)
/*  266:     */   {
/*  267: 635 */     this.access = value;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public String getBatchSize()
/*  271:     */   {
/*  272: 647 */     return this.batchSize;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public void setBatchSize(String value)
/*  276:     */   {
/*  277: 659 */     this.batchSize = value;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public String getCascade()
/*  281:     */   {
/*  282: 671 */     return this.cascade;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public void setCascade(String value)
/*  286:     */   {
/*  287: 683 */     this.cascade = value;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public String getCatalog()
/*  291:     */   {
/*  292: 695 */     return this.catalog;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public void setCatalog(String value)
/*  296:     */   {
/*  297: 707 */     this.catalog = value;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public String getCheck()
/*  301:     */   {
/*  302: 719 */     return this.check;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public void setCheck(String value)
/*  306:     */   {
/*  307: 731 */     this.check = value;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public String getCollectionType()
/*  311:     */   {
/*  312: 743 */     return this.collectionType;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public void setCollectionType(String value)
/*  316:     */   {
/*  317: 755 */     this.collectionType = value;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public String getElementClass()
/*  321:     */   {
/*  322: 767 */     return this.elementClass;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public void setElementClass(String value)
/*  326:     */   {
/*  327: 779 */     this.elementClass = value;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public boolean isEmbedXml()
/*  331:     */   {
/*  332: 791 */     if (this.embedXml == null) {
/*  333: 792 */       return true;
/*  334:     */     }
/*  335: 794 */     return this.embedXml.booleanValue();
/*  336:     */   }
/*  337:     */   
/*  338:     */   public void setEmbedXml(Boolean value)
/*  339:     */   {
/*  340: 807 */     this.embedXml = value;
/*  341:     */   }
/*  342:     */   
/*  343:     */   public JaxbFetchAttributeWithSubselect getFetch()
/*  344:     */   {
/*  345: 819 */     return this.fetch;
/*  346:     */   }
/*  347:     */   
/*  348:     */   public void setFetch(JaxbFetchAttributeWithSubselect value)
/*  349:     */   {
/*  350: 831 */     this.fetch = value;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public boolean isInverse()
/*  354:     */   {
/*  355: 843 */     if (this.inverse == null) {
/*  356: 844 */       return false;
/*  357:     */     }
/*  358: 846 */     return this.inverse.booleanValue();
/*  359:     */   }
/*  360:     */   
/*  361:     */   public void setInverse(Boolean value)
/*  362:     */   {
/*  363: 859 */     this.inverse = value;
/*  364:     */   }
/*  365:     */   
/*  366:     */   public boolean isMutable()
/*  367:     */   {
/*  368: 871 */     if (this.mutable == null) {
/*  369: 872 */       return true;
/*  370:     */     }
/*  371: 874 */     return this.mutable.booleanValue();
/*  372:     */   }
/*  373:     */   
/*  374:     */   public void setMutable(Boolean value)
/*  375:     */   {
/*  376: 887 */     this.mutable = value;
/*  377:     */   }
/*  378:     */   
/*  379:     */   public String getName()
/*  380:     */   {
/*  381: 899 */     return this.name;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void setName(String value)
/*  385:     */   {
/*  386: 911 */     this.name = value;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public String getNode()
/*  390:     */   {
/*  391: 923 */     return this.node;
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void setNode(String value)
/*  395:     */   {
/*  396: 935 */     this.node = value;
/*  397:     */   }
/*  398:     */   
/*  399:     */   public boolean isOptimisticLock()
/*  400:     */   {
/*  401: 947 */     if (this.optimisticLock == null) {
/*  402: 948 */       return true;
/*  403:     */     }
/*  404: 950 */     return this.optimisticLock.booleanValue();
/*  405:     */   }
/*  406:     */   
/*  407:     */   public void setOptimisticLock(Boolean value)
/*  408:     */   {
/*  409: 963 */     this.optimisticLock = value;
/*  410:     */   }
/*  411:     */   
/*  412:     */   public JaxbOuterJoinAttribute getOuterJoin()
/*  413:     */   {
/*  414: 975 */     return this.outerJoin;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/*  418:     */   {
/*  419: 987 */     this.outerJoin = value;
/*  420:     */   }
/*  421:     */   
/*  422:     */   public String getPersister()
/*  423:     */   {
/*  424: 999 */     return this.persister;
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void setPersister(String value)
/*  428:     */   {
/*  429:1011 */     this.persister = value;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public String getSchema()
/*  433:     */   {
/*  434:1023 */     return this.schema;
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void setSchema(String value)
/*  438:     */   {
/*  439:1035 */     this.schema = value;
/*  440:     */   }
/*  441:     */   
/*  442:     */   public String getSubselectAttribute()
/*  443:     */   {
/*  444:1047 */     return this.subselectAttribute;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public void setSubselectAttribute(String value)
/*  448:     */   {
/*  449:1059 */     this.subselectAttribute = value;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public String getTable()
/*  453:     */   {
/*  454:1071 */     return this.table;
/*  455:     */   }
/*  456:     */   
/*  457:     */   public void setTable(String value)
/*  458:     */   {
/*  459:1083 */     this.table = value;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public String getWhere()
/*  463:     */   {
/*  464:1095 */     return this.where;
/*  465:     */   }
/*  466:     */   
/*  467:     */   public void setWhere(String value)
/*  468:     */   {
/*  469:1107 */     this.where = value;
/*  470:     */   }
/*  471:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbArrayElement
 * JD-Core Version:    0.7.0.1
 */