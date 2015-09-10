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
/*   13:     */ @XmlType(name="joined-subclass-element", propOrder={"meta", "subselect", "synchronize", "comment", "tuplizer", "key", "propertyOrManyToOneOrOneToOne", "joinedSubclass", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "fetchProfile", "resultset", "queryOrSqlQuery"})
/*   14:     */ public class JaxbJoinedSubclassElement
/*   15:     */   implements SubEntityElement
/*   16:     */ {
/*   17:     */   protected List<JaxbMetaElement> meta;
/*   18:     */   protected String subselect;
/*   19:     */   protected List<JaxbSynchronizeElement> synchronize;
/*   20:     */   protected String comment;
/*   21:     */   protected List<JaxbTuplizerElement> tuplizer;
/*   22:     */   @XmlElement(required=true)
/*   23:     */   protected JaxbKeyElement key;
/*   24:     */   @XmlElements({@XmlElement(name="properties", type=JaxbPropertiesElement.class), @XmlElement(name="map", type=JaxbMapElement.class), @XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @XmlElement(name="component", type=JaxbComponentElement.class), @XmlElement(name="bag", type=JaxbBagElement.class), @XmlElement(name="property", type=JaxbPropertyElement.class), @XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class), @XmlElement(name="idbag", type=JaxbIdbagElement.class), @XmlElement(name="primitive-array", type=JaxbPrimitiveArrayElement.class), @XmlElement(name="set", type=JaxbSetElement.class), @XmlElement(name="one-to-one", type=JaxbOneToOneElement.class), @XmlElement(name="any", type=JaxbAnyElement.class), @XmlElement(name="list", type=JaxbListElement.class), @XmlElement(name="array", type=JaxbArrayElement.class)})
/*   25:     */   protected List<Object> propertyOrManyToOneOrOneToOne;
/*   26:     */   @XmlElement(name="joined-subclass")
/*   27:     */   protected List<JaxbJoinedSubclassElement> joinedSubclass;
/*   28:     */   protected JaxbLoaderElement loader;
/*   29:     */   @XmlElement(name="sql-insert")
/*   30:     */   protected JaxbSqlInsertElement sqlInsert;
/*   31:     */   @XmlElement(name="sql-update")
/*   32:     */   protected JaxbSqlUpdateElement sqlUpdate;
/*   33:     */   @XmlElement(name="sql-delete")
/*   34:     */   protected JaxbSqlDeleteElement sqlDelete;
/*   35:     */   @XmlElement(name="fetch-profile")
/*   36:     */   protected List<JaxbFetchProfileElement> fetchProfile;
/*   37:     */   protected List<JaxbResultsetElement> resultset;
/*   38:     */   @XmlElements({@XmlElement(name="sql-query", type=JaxbSqlQueryElement.class), @XmlElement(name="query", type=JaxbQueryElement.class)})
/*   39:     */   protected List<Object> queryOrSqlQuery;
/*   40:     */   @XmlAttribute(name="abstract")
/*   41:     */   protected Boolean _abstract;
/*   42:     */   @XmlAttribute(name="batch-size")
/*   43:     */   protected String batchSize;
/*   44:     */   @XmlAttribute
/*   45:     */   protected String catalog;
/*   46:     */   @XmlAttribute
/*   47:     */   protected String check;
/*   48:     */   @XmlAttribute(name="dynamic-insert")
/*   49:     */   protected Boolean dynamicInsert;
/*   50:     */   @XmlAttribute(name="dynamic-update")
/*   51:     */   protected Boolean dynamicUpdate;
/*   52:     */   @XmlAttribute(name="entity-name")
/*   53:     */   protected String entityName;
/*   54:     */   @XmlAttribute(name="extends")
/*   55:     */   protected String _extends;
/*   56:     */   @XmlAttribute
/*   57:     */   protected Boolean lazy;
/*   58:     */   @XmlAttribute
/*   59:     */   protected String name;
/*   60:     */   @XmlAttribute
/*   61:     */   protected String node;
/*   62:     */   @XmlAttribute
/*   63:     */   protected String persister;
/*   64:     */   @XmlAttribute
/*   65:     */   protected String proxy;
/*   66:     */   @XmlAttribute
/*   67:     */   protected String schema;
/*   68:     */   @XmlAttribute(name="select-before-update")
/*   69:     */   protected Boolean selectBeforeUpdate;
/*   70:     */   @XmlAttribute(name="subselect")
/*   71:     */   protected String subselectAttribute;
/*   72:     */   @XmlAttribute
/*   73:     */   protected String table;
/*   74:     */   
/*   75:     */   public List<JaxbMetaElement> getMeta()
/*   76:     */   {
/*   77: 209 */     if (this.meta == null) {
/*   78: 210 */       this.meta = new ArrayList();
/*   79:     */     }
/*   80: 212 */     return this.meta;
/*   81:     */   }
/*   82:     */   
/*   83:     */   public String getSubselect()
/*   84:     */   {
/*   85: 224 */     return this.subselect;
/*   86:     */   }
/*   87:     */   
/*   88:     */   public void setSubselect(String value)
/*   89:     */   {
/*   90: 236 */     this.subselect = value;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public List<JaxbSynchronizeElement> getSynchronize()
/*   94:     */   {
/*   95: 262 */     if (this.synchronize == null) {
/*   96: 263 */       this.synchronize = new ArrayList();
/*   97:     */     }
/*   98: 265 */     return this.synchronize;
/*   99:     */   }
/*  100:     */   
/*  101:     */   public String getComment()
/*  102:     */   {
/*  103: 277 */     return this.comment;
/*  104:     */   }
/*  105:     */   
/*  106:     */   public void setComment(String value)
/*  107:     */   {
/*  108: 289 */     this.comment = value;
/*  109:     */   }
/*  110:     */   
/*  111:     */   public List<JaxbTuplizerElement> getTuplizer()
/*  112:     */   {
/*  113: 315 */     if (this.tuplizer == null) {
/*  114: 316 */       this.tuplizer = new ArrayList();
/*  115:     */     }
/*  116: 318 */     return this.tuplizer;
/*  117:     */   }
/*  118:     */   
/*  119:     */   public JaxbKeyElement getKey()
/*  120:     */   {
/*  121: 330 */     return this.key;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public void setKey(JaxbKeyElement value)
/*  125:     */   {
/*  126: 342 */     this.key = value;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public List<Object> getPropertyOrManyToOneOrOneToOne()
/*  130:     */   {
/*  131: 381 */     if (this.propertyOrManyToOneOrOneToOne == null) {
/*  132: 382 */       this.propertyOrManyToOneOrOneToOne = new ArrayList();
/*  133:     */     }
/*  134: 384 */     return this.propertyOrManyToOneOrOneToOne;
/*  135:     */   }
/*  136:     */   
/*  137:     */   public List<JaxbJoinedSubclassElement> getJoinedSubclass()
/*  138:     */   {
/*  139: 410 */     if (this.joinedSubclass == null) {
/*  140: 411 */       this.joinedSubclass = new ArrayList();
/*  141:     */     }
/*  142: 413 */     return this.joinedSubclass;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public JaxbLoaderElement getLoader()
/*  146:     */   {
/*  147: 425 */     return this.loader;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public void setLoader(JaxbLoaderElement value)
/*  151:     */   {
/*  152: 437 */     this.loader = value;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public JaxbSqlInsertElement getSqlInsert()
/*  156:     */   {
/*  157: 449 */     return this.sqlInsert;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void setSqlInsert(JaxbSqlInsertElement value)
/*  161:     */   {
/*  162: 461 */     this.sqlInsert = value;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public JaxbSqlUpdateElement getSqlUpdate()
/*  166:     */   {
/*  167: 473 */     return this.sqlUpdate;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  171:     */   {
/*  172: 485 */     this.sqlUpdate = value;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public JaxbSqlDeleteElement getSqlDelete()
/*  176:     */   {
/*  177: 497 */     return this.sqlDelete;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public void setSqlDelete(JaxbSqlDeleteElement value)
/*  181:     */   {
/*  182: 509 */     this.sqlDelete = value;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public List<JaxbFetchProfileElement> getFetchProfile()
/*  186:     */   {
/*  187: 535 */     if (this.fetchProfile == null) {
/*  188: 536 */       this.fetchProfile = new ArrayList();
/*  189:     */     }
/*  190: 538 */     return this.fetchProfile;
/*  191:     */   }
/*  192:     */   
/*  193:     */   public List<JaxbResultsetElement> getResultset()
/*  194:     */   {
/*  195: 564 */     if (this.resultset == null) {
/*  196: 565 */       this.resultset = new ArrayList();
/*  197:     */     }
/*  198: 567 */     return this.resultset;
/*  199:     */   }
/*  200:     */   
/*  201:     */   public List<Object> getQueryOrSqlQuery()
/*  202:     */   {
/*  203: 594 */     if (this.queryOrSqlQuery == null) {
/*  204: 595 */       this.queryOrSqlQuery = new ArrayList();
/*  205:     */     }
/*  206: 597 */     return this.queryOrSqlQuery;
/*  207:     */   }
/*  208:     */   
/*  209:     */   public Boolean isAbstract()
/*  210:     */   {
/*  211: 609 */     return this._abstract;
/*  212:     */   }
/*  213:     */   
/*  214:     */   public void setAbstract(Boolean value)
/*  215:     */   {
/*  216: 621 */     this._abstract = value;
/*  217:     */   }
/*  218:     */   
/*  219:     */   public String getBatchSize()
/*  220:     */   {
/*  221: 633 */     return this.batchSize;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public void setBatchSize(String value)
/*  225:     */   {
/*  226: 645 */     this.batchSize = value;
/*  227:     */   }
/*  228:     */   
/*  229:     */   public String getCatalog()
/*  230:     */   {
/*  231: 657 */     return this.catalog;
/*  232:     */   }
/*  233:     */   
/*  234:     */   public void setCatalog(String value)
/*  235:     */   {
/*  236: 669 */     this.catalog = value;
/*  237:     */   }
/*  238:     */   
/*  239:     */   public String getCheck()
/*  240:     */   {
/*  241: 681 */     return this.check;
/*  242:     */   }
/*  243:     */   
/*  244:     */   public void setCheck(String value)
/*  245:     */   {
/*  246: 693 */     this.check = value;
/*  247:     */   }
/*  248:     */   
/*  249:     */   public boolean isDynamicInsert()
/*  250:     */   {
/*  251: 705 */     if (this.dynamicInsert == null) {
/*  252: 706 */       return false;
/*  253:     */     }
/*  254: 708 */     return this.dynamicInsert.booleanValue();
/*  255:     */   }
/*  256:     */   
/*  257:     */   public void setDynamicInsert(Boolean value)
/*  258:     */   {
/*  259: 721 */     this.dynamicInsert = value;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public boolean isDynamicUpdate()
/*  263:     */   {
/*  264: 733 */     if (this.dynamicUpdate == null) {
/*  265: 734 */       return false;
/*  266:     */     }
/*  267: 736 */     return this.dynamicUpdate.booleanValue();
/*  268:     */   }
/*  269:     */   
/*  270:     */   public void setDynamicUpdate(Boolean value)
/*  271:     */   {
/*  272: 749 */     this.dynamicUpdate = value;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public String getEntityName()
/*  276:     */   {
/*  277: 761 */     return this.entityName;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void setEntityName(String value)
/*  281:     */   {
/*  282: 773 */     this.entityName = value;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public String getExtends()
/*  286:     */   {
/*  287: 785 */     return this._extends;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public void setExtends(String value)
/*  291:     */   {
/*  292: 797 */     this._extends = value;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public Boolean isLazy()
/*  296:     */   {
/*  297: 809 */     return this.lazy;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void setLazy(Boolean value)
/*  301:     */   {
/*  302: 821 */     this.lazy = value;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public String getName()
/*  306:     */   {
/*  307: 833 */     return this.name;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public void setName(String value)
/*  311:     */   {
/*  312: 845 */     this.name = value;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public String getNode()
/*  316:     */   {
/*  317: 857 */     return this.node;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void setNode(String value)
/*  321:     */   {
/*  322: 869 */     this.node = value;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public String getPersister()
/*  326:     */   {
/*  327: 881 */     return this.persister;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public void setPersister(String value)
/*  331:     */   {
/*  332: 893 */     this.persister = value;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public String getProxy()
/*  336:     */   {
/*  337: 905 */     return this.proxy;
/*  338:     */   }
/*  339:     */   
/*  340:     */   public void setProxy(String value)
/*  341:     */   {
/*  342: 917 */     this.proxy = value;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public String getSchema()
/*  346:     */   {
/*  347: 929 */     return this.schema;
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void setSchema(String value)
/*  351:     */   {
/*  352: 941 */     this.schema = value;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public boolean isSelectBeforeUpdate()
/*  356:     */   {
/*  357: 953 */     if (this.selectBeforeUpdate == null) {
/*  358: 954 */       return false;
/*  359:     */     }
/*  360: 956 */     return this.selectBeforeUpdate.booleanValue();
/*  361:     */   }
/*  362:     */   
/*  363:     */   public void setSelectBeforeUpdate(Boolean value)
/*  364:     */   {
/*  365: 969 */     this.selectBeforeUpdate = value;
/*  366:     */   }
/*  367:     */   
/*  368:     */   public String getSubselectAttribute()
/*  369:     */   {
/*  370: 981 */     return this.subselectAttribute;
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void setSubselectAttribute(String value)
/*  374:     */   {
/*  375: 993 */     this.subselectAttribute = value;
/*  376:     */   }
/*  377:     */   
/*  378:     */   public String getTable()
/*  379:     */   {
/*  380:1005 */     return this.table;
/*  381:     */   }
/*  382:     */   
/*  383:     */   public void setTable(String value)
/*  384:     */   {
/*  385:1017 */     this.table = value;
/*  386:     */   }
/*  387:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbJoinedSubclassElement
 * JD-Core Version:    0.7.0.1
 */