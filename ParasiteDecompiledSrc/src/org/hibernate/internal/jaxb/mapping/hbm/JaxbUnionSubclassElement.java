/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElement;
/*   9:    */ import javax.xml.bind.annotation.XmlElements;
/*  10:    */ import javax.xml.bind.annotation.XmlType;
/*  11:    */ 
/*  12:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  13:    */ @XmlType(name="union-subclass-element", propOrder={"meta", "subselect", "synchronize", "comment", "tuplizer", "propertyOrManyToOneOrOneToOne", "unionSubclass", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "fetchProfile", "resultset", "queryOrSqlQuery"})
/*  14:    */ public class JaxbUnionSubclassElement
/*  15:    */   implements SubEntityElement
/*  16:    */ {
/*  17:    */   protected List<JaxbMetaElement> meta;
/*  18:    */   protected String subselect;
/*  19:    */   protected List<JaxbSynchronizeElement> synchronize;
/*  20:    */   protected String comment;
/*  21:    */   protected List<JaxbTuplizerElement> tuplizer;
/*  22:    */   @XmlElements({@XmlElement(name="bag", type=JaxbBagElement.class), @XmlElement(name="property", type=JaxbPropertyElement.class), @XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class), @XmlElement(name="one-to-one", type=JaxbOneToOneElement.class), @XmlElement(name="map", type=JaxbMapElement.class), @XmlElement(name="primitive-array", type=JaxbPrimitiveArrayElement.class), @XmlElement(name="list", type=JaxbListElement.class), @XmlElement(name="properties", type=JaxbPropertiesElement.class), @XmlElement(name="array", type=JaxbArrayElement.class), @XmlElement(name="idbag", type=JaxbIdbagElement.class), @XmlElement(name="any", type=JaxbAnyElement.class), @XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @XmlElement(name="set", type=JaxbSetElement.class), @XmlElement(name="component", type=JaxbComponentElement.class)})
/*  23:    */   protected List<Object> propertyOrManyToOneOrOneToOne;
/*  24:    */   @XmlElement(name="union-subclass")
/*  25:    */   protected List<JaxbUnionSubclassElement> unionSubclass;
/*  26:    */   protected JaxbLoaderElement loader;
/*  27:    */   @XmlElement(name="sql-insert")
/*  28:    */   protected JaxbSqlInsertElement sqlInsert;
/*  29:    */   @XmlElement(name="sql-update")
/*  30:    */   protected JaxbSqlUpdateElement sqlUpdate;
/*  31:    */   @XmlElement(name="sql-delete")
/*  32:    */   protected JaxbSqlDeleteElement sqlDelete;
/*  33:    */   @XmlElement(name="fetch-profile")
/*  34:    */   protected List<JaxbFetchProfileElement> fetchProfile;
/*  35:    */   protected List<JaxbResultsetElement> resultset;
/*  36:    */   @XmlElements({@XmlElement(name="query", type=JaxbQueryElement.class), @XmlElement(name="sql-query", type=JaxbSqlQueryElement.class)})
/*  37:    */   protected List<Object> queryOrSqlQuery;
/*  38:    */   @XmlAttribute(name="abstract")
/*  39:    */   protected Boolean _abstract;
/*  40:    */   @XmlAttribute(name="batch-size")
/*  41:    */   protected String batchSize;
/*  42:    */   @XmlAttribute
/*  43:    */   protected String catalog;
/*  44:    */   @XmlAttribute
/*  45:    */   protected String check;
/*  46:    */   @XmlAttribute(name="dynamic-insert")
/*  47:    */   protected Boolean dynamicInsert;
/*  48:    */   @XmlAttribute(name="dynamic-update")
/*  49:    */   protected Boolean dynamicUpdate;
/*  50:    */   @XmlAttribute(name="entity-name")
/*  51:    */   protected String entityName;
/*  52:    */   @XmlAttribute(name="extends")
/*  53:    */   protected String _extends;
/*  54:    */   @XmlAttribute
/*  55:    */   protected Boolean lazy;
/*  56:    */   @XmlAttribute
/*  57:    */   protected String name;
/*  58:    */   @XmlAttribute
/*  59:    */   protected String node;
/*  60:    */   @XmlAttribute
/*  61:    */   protected String persister;
/*  62:    */   @XmlAttribute
/*  63:    */   protected String proxy;
/*  64:    */   @XmlAttribute
/*  65:    */   protected String schema;
/*  66:    */   @XmlAttribute(name="select-before-update")
/*  67:    */   protected Boolean selectBeforeUpdate;
/*  68:    */   @XmlAttribute(name="subselect")
/*  69:    */   protected String subselectAttribute;
/*  70:    */   @XmlAttribute
/*  71:    */   protected String table;
/*  72:    */   
/*  73:    */   public List<JaxbMetaElement> getMeta()
/*  74:    */   {
/*  75:205 */     if (this.meta == null) {
/*  76:206 */       this.meta = new ArrayList();
/*  77:    */     }
/*  78:208 */     return this.meta;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getSubselect()
/*  82:    */   {
/*  83:220 */     return this.subselect;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setSubselect(String value)
/*  87:    */   {
/*  88:232 */     this.subselect = value;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<JaxbSynchronizeElement> getSynchronize()
/*  92:    */   {
/*  93:258 */     if (this.synchronize == null) {
/*  94:259 */       this.synchronize = new ArrayList();
/*  95:    */     }
/*  96:261 */     return this.synchronize;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getComment()
/* 100:    */   {
/* 101:273 */     return this.comment;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setComment(String value)
/* 105:    */   {
/* 106:285 */     this.comment = value;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<JaxbTuplizerElement> getTuplizer()
/* 110:    */   {
/* 111:311 */     if (this.tuplizer == null) {
/* 112:312 */       this.tuplizer = new ArrayList();
/* 113:    */     }
/* 114:314 */     return this.tuplizer;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<Object> getPropertyOrManyToOneOrOneToOne()
/* 118:    */   {
/* 119:353 */     if (this.propertyOrManyToOneOrOneToOne == null) {
/* 120:354 */       this.propertyOrManyToOneOrOneToOne = new ArrayList();
/* 121:    */     }
/* 122:356 */     return this.propertyOrManyToOneOrOneToOne;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<JaxbUnionSubclassElement> getUnionSubclass()
/* 126:    */   {
/* 127:382 */     if (this.unionSubclass == null) {
/* 128:383 */       this.unionSubclass = new ArrayList();
/* 129:    */     }
/* 130:385 */     return this.unionSubclass;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public JaxbLoaderElement getLoader()
/* 134:    */   {
/* 135:397 */     return this.loader;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setLoader(JaxbLoaderElement value)
/* 139:    */   {
/* 140:409 */     this.loader = value;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public JaxbSqlInsertElement getSqlInsert()
/* 144:    */   {
/* 145:421 */     return this.sqlInsert;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setSqlInsert(JaxbSqlInsertElement value)
/* 149:    */   {
/* 150:433 */     this.sqlInsert = value;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public JaxbSqlUpdateElement getSqlUpdate()
/* 154:    */   {
/* 155:445 */     return this.sqlUpdate;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/* 159:    */   {
/* 160:457 */     this.sqlUpdate = value;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public JaxbSqlDeleteElement getSqlDelete()
/* 164:    */   {
/* 165:469 */     return this.sqlDelete;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setSqlDelete(JaxbSqlDeleteElement value)
/* 169:    */   {
/* 170:481 */     this.sqlDelete = value;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public List<JaxbFetchProfileElement> getFetchProfile()
/* 174:    */   {
/* 175:507 */     if (this.fetchProfile == null) {
/* 176:508 */       this.fetchProfile = new ArrayList();
/* 177:    */     }
/* 178:510 */     return this.fetchProfile;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<JaxbResultsetElement> getResultset()
/* 182:    */   {
/* 183:536 */     if (this.resultset == null) {
/* 184:537 */       this.resultset = new ArrayList();
/* 185:    */     }
/* 186:539 */     return this.resultset;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<Object> getQueryOrSqlQuery()
/* 190:    */   {
/* 191:566 */     if (this.queryOrSqlQuery == null) {
/* 192:567 */       this.queryOrSqlQuery = new ArrayList();
/* 193:    */     }
/* 194:569 */     return this.queryOrSqlQuery;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Boolean isAbstract()
/* 198:    */   {
/* 199:581 */     return this._abstract;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setAbstract(Boolean value)
/* 203:    */   {
/* 204:593 */     this._abstract = value;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getBatchSize()
/* 208:    */   {
/* 209:605 */     return this.batchSize;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setBatchSize(String value)
/* 213:    */   {
/* 214:617 */     this.batchSize = value;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getCatalog()
/* 218:    */   {
/* 219:629 */     return this.catalog;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setCatalog(String value)
/* 223:    */   {
/* 224:641 */     this.catalog = value;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getCheck()
/* 228:    */   {
/* 229:653 */     return this.check;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setCheck(String value)
/* 233:    */   {
/* 234:665 */     this.check = value;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public boolean isDynamicInsert()
/* 238:    */   {
/* 239:677 */     if (this.dynamicInsert == null) {
/* 240:678 */       return false;
/* 241:    */     }
/* 242:680 */     return this.dynamicInsert.booleanValue();
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setDynamicInsert(Boolean value)
/* 246:    */   {
/* 247:693 */     this.dynamicInsert = value;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public boolean isDynamicUpdate()
/* 251:    */   {
/* 252:705 */     if (this.dynamicUpdate == null) {
/* 253:706 */       return false;
/* 254:    */     }
/* 255:708 */     return this.dynamicUpdate.booleanValue();
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setDynamicUpdate(Boolean value)
/* 259:    */   {
/* 260:721 */     this.dynamicUpdate = value;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public String getEntityName()
/* 264:    */   {
/* 265:733 */     return this.entityName;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setEntityName(String value)
/* 269:    */   {
/* 270:745 */     this.entityName = value;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public String getExtends()
/* 274:    */   {
/* 275:757 */     return this._extends;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setExtends(String value)
/* 279:    */   {
/* 280:769 */     this._extends = value;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public Boolean isLazy()
/* 284:    */   {
/* 285:781 */     return this.lazy;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setLazy(Boolean value)
/* 289:    */   {
/* 290:793 */     this.lazy = value;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public String getName()
/* 294:    */   {
/* 295:805 */     return this.name;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setName(String value)
/* 299:    */   {
/* 300:817 */     this.name = value;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public String getNode()
/* 304:    */   {
/* 305:829 */     return this.node;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setNode(String value)
/* 309:    */   {
/* 310:841 */     this.node = value;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public String getPersister()
/* 314:    */   {
/* 315:853 */     return this.persister;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setPersister(String value)
/* 319:    */   {
/* 320:865 */     this.persister = value;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public String getProxy()
/* 324:    */   {
/* 325:877 */     return this.proxy;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setProxy(String value)
/* 329:    */   {
/* 330:889 */     this.proxy = value;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public String getSchema()
/* 334:    */   {
/* 335:901 */     return this.schema;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setSchema(String value)
/* 339:    */   {
/* 340:913 */     this.schema = value;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public boolean isSelectBeforeUpdate()
/* 344:    */   {
/* 345:925 */     if (this.selectBeforeUpdate == null) {
/* 346:926 */       return false;
/* 347:    */     }
/* 348:928 */     return this.selectBeforeUpdate.booleanValue();
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setSelectBeforeUpdate(Boolean value)
/* 352:    */   {
/* 353:941 */     this.selectBeforeUpdate = value;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public String getSubselectAttribute()
/* 357:    */   {
/* 358:953 */     return this.subselectAttribute;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setSubselectAttribute(String value)
/* 362:    */   {
/* 363:965 */     this.subselectAttribute = value;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public String getTable()
/* 367:    */   {
/* 368:977 */     return this.table;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setTable(String value)
/* 372:    */   {
/* 373:989 */     this.table = value;
/* 374:    */   }
/* 375:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbUnionSubclassElement
 * JD-Core Version:    0.7.0.1
 */