/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElement;
/*   9:    */ import javax.xml.bind.annotation.XmlType;
/*  10:    */ 
/*  11:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  12:    */ @XmlType(name="primitive-array-element", propOrder={"meta", "subselect", "cache", "synchronize", "comment", "key", "index", "listIndex", "element", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "sqlDeleteAll"})
/*  13:    */ public class JaxbPrimitiveArrayElement
/*  14:    */ {
/*  15:    */   protected List<JaxbMetaElement> meta;
/*  16:    */   protected String subselect;
/*  17:    */   protected JaxbCacheElement cache;
/*  18:    */   protected List<JaxbSynchronizeElement> synchronize;
/*  19:    */   protected String comment;
/*  20:    */   @XmlElement(required=true)
/*  21:    */   protected JaxbKeyElement key;
/*  22:    */   protected JaxbIndexElement index;
/*  23:    */   @XmlElement(name="list-index")
/*  24:    */   protected JaxbListIndexElement listIndex;
/*  25:    */   @XmlElement(required=true)
/*  26:    */   protected JaxbElementElement element;
/*  27:    */   protected JaxbLoaderElement loader;
/*  28:    */   @XmlElement(name="sql-insert")
/*  29:    */   protected JaxbSqlInsertElement sqlInsert;
/*  30:    */   @XmlElement(name="sql-update")
/*  31:    */   protected JaxbSqlUpdateElement sqlUpdate;
/*  32:    */   @XmlElement(name="sql-delete")
/*  33:    */   protected JaxbSqlDeleteElement sqlDelete;
/*  34:    */   @XmlElement(name="sql-delete-all")
/*  35:    */   protected JaxbSqlDeleteAllElement sqlDeleteAll;
/*  36:    */   @XmlAttribute
/*  37:    */   protected String access;
/*  38:    */   @XmlAttribute(name="batch-size")
/*  39:    */   protected String batchSize;
/*  40:    */   @XmlAttribute
/*  41:    */   protected String catalog;
/*  42:    */   @XmlAttribute
/*  43:    */   protected String check;
/*  44:    */   @XmlAttribute(name="collection-type")
/*  45:    */   protected String collectionType;
/*  46:    */   @XmlAttribute(name="embed-xml")
/*  47:    */   protected Boolean embedXml;
/*  48:    */   @XmlAttribute
/*  49:    */   protected JaxbFetchAttributeWithSubselect fetch;
/*  50:    */   @XmlAttribute
/*  51:    */   protected Boolean mutable;
/*  52:    */   @XmlAttribute(required=true)
/*  53:    */   protected String name;
/*  54:    */   @XmlAttribute
/*  55:    */   protected String node;
/*  56:    */   @XmlAttribute(name="optimistic-lock")
/*  57:    */   protected Boolean optimisticLock;
/*  58:    */   @XmlAttribute(name="outer-join")
/*  59:    */   protected JaxbOuterJoinAttribute outerJoin;
/*  60:    */   @XmlAttribute
/*  61:    */   protected String persister;
/*  62:    */   @XmlAttribute
/*  63:    */   protected String schema;
/*  64:    */   @XmlAttribute(name="subselect")
/*  65:    */   protected String subselectAttribute;
/*  66:    */   @XmlAttribute
/*  67:    */   protected String table;
/*  68:    */   @XmlAttribute
/*  69:    */   protected String where;
/*  70:    */   
/*  71:    */   public List<JaxbMetaElement> getMeta()
/*  72:    */   {
/*  73:169 */     if (this.meta == null) {
/*  74:170 */       this.meta = new ArrayList();
/*  75:    */     }
/*  76:172 */     return this.meta;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getSubselect()
/*  80:    */   {
/*  81:184 */     return this.subselect;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setSubselect(String value)
/*  85:    */   {
/*  86:196 */     this.subselect = value;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public JaxbCacheElement getCache()
/*  90:    */   {
/*  91:208 */     return this.cache;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setCache(JaxbCacheElement value)
/*  95:    */   {
/*  96:220 */     this.cache = value;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List<JaxbSynchronizeElement> getSynchronize()
/* 100:    */   {
/* 101:246 */     if (this.synchronize == null) {
/* 102:247 */       this.synchronize = new ArrayList();
/* 103:    */     }
/* 104:249 */     return this.synchronize;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getComment()
/* 108:    */   {
/* 109:261 */     return this.comment;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setComment(String value)
/* 113:    */   {
/* 114:273 */     this.comment = value;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public JaxbKeyElement getKey()
/* 118:    */   {
/* 119:285 */     return this.key;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setKey(JaxbKeyElement value)
/* 123:    */   {
/* 124:297 */     this.key = value;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public JaxbIndexElement getIndex()
/* 128:    */   {
/* 129:309 */     return this.index;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setIndex(JaxbIndexElement value)
/* 133:    */   {
/* 134:321 */     this.index = value;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public JaxbListIndexElement getListIndex()
/* 138:    */   {
/* 139:333 */     return this.listIndex;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setListIndex(JaxbListIndexElement value)
/* 143:    */   {
/* 144:345 */     this.listIndex = value;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public JaxbElementElement getElement()
/* 148:    */   {
/* 149:357 */     return this.element;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setElement(JaxbElementElement value)
/* 153:    */   {
/* 154:369 */     this.element = value;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public JaxbLoaderElement getLoader()
/* 158:    */   {
/* 159:381 */     return this.loader;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setLoader(JaxbLoaderElement value)
/* 163:    */   {
/* 164:393 */     this.loader = value;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public JaxbSqlInsertElement getSqlInsert()
/* 168:    */   {
/* 169:405 */     return this.sqlInsert;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setSqlInsert(JaxbSqlInsertElement value)
/* 173:    */   {
/* 174:417 */     this.sqlInsert = value;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public JaxbSqlUpdateElement getSqlUpdate()
/* 178:    */   {
/* 179:429 */     return this.sqlUpdate;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/* 183:    */   {
/* 184:441 */     this.sqlUpdate = value;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public JaxbSqlDeleteElement getSqlDelete()
/* 188:    */   {
/* 189:453 */     return this.sqlDelete;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setSqlDelete(JaxbSqlDeleteElement value)
/* 193:    */   {
/* 194:465 */     this.sqlDelete = value;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public JaxbSqlDeleteAllElement getSqlDeleteAll()
/* 198:    */   {
/* 199:477 */     return this.sqlDeleteAll;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setSqlDeleteAll(JaxbSqlDeleteAllElement value)
/* 203:    */   {
/* 204:489 */     this.sqlDeleteAll = value;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getAccess()
/* 208:    */   {
/* 209:501 */     return this.access;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setAccess(String value)
/* 213:    */   {
/* 214:513 */     this.access = value;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getBatchSize()
/* 218:    */   {
/* 219:525 */     return this.batchSize;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setBatchSize(String value)
/* 223:    */   {
/* 224:537 */     this.batchSize = value;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getCatalog()
/* 228:    */   {
/* 229:549 */     return this.catalog;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setCatalog(String value)
/* 233:    */   {
/* 234:561 */     this.catalog = value;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getCheck()
/* 238:    */   {
/* 239:573 */     return this.check;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setCheck(String value)
/* 243:    */   {
/* 244:585 */     this.check = value;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getCollectionType()
/* 248:    */   {
/* 249:597 */     return this.collectionType;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setCollectionType(String value)
/* 253:    */   {
/* 254:609 */     this.collectionType = value;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean isEmbedXml()
/* 258:    */   {
/* 259:621 */     if (this.embedXml == null) {
/* 260:622 */       return true;
/* 261:    */     }
/* 262:624 */     return this.embedXml.booleanValue();
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setEmbedXml(Boolean value)
/* 266:    */   {
/* 267:637 */     this.embedXml = value;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public JaxbFetchAttributeWithSubselect getFetch()
/* 271:    */   {
/* 272:649 */     return this.fetch;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setFetch(JaxbFetchAttributeWithSubselect value)
/* 276:    */   {
/* 277:661 */     this.fetch = value;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean isMutable()
/* 281:    */   {
/* 282:673 */     if (this.mutable == null) {
/* 283:674 */       return true;
/* 284:    */     }
/* 285:676 */     return this.mutable.booleanValue();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setMutable(Boolean value)
/* 289:    */   {
/* 290:689 */     this.mutable = value;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public String getName()
/* 294:    */   {
/* 295:701 */     return this.name;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setName(String value)
/* 299:    */   {
/* 300:713 */     this.name = value;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public String getNode()
/* 304:    */   {
/* 305:725 */     return this.node;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setNode(String value)
/* 309:    */   {
/* 310:737 */     this.node = value;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public boolean isOptimisticLock()
/* 314:    */   {
/* 315:749 */     if (this.optimisticLock == null) {
/* 316:750 */       return true;
/* 317:    */     }
/* 318:752 */     return this.optimisticLock.booleanValue();
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setOptimisticLock(Boolean value)
/* 322:    */   {
/* 323:765 */     this.optimisticLock = value;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public JaxbOuterJoinAttribute getOuterJoin()
/* 327:    */   {
/* 328:777 */     return this.outerJoin;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/* 332:    */   {
/* 333:789 */     this.outerJoin = value;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public String getPersister()
/* 337:    */   {
/* 338:801 */     return this.persister;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setPersister(String value)
/* 342:    */   {
/* 343:813 */     this.persister = value;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public String getSchema()
/* 347:    */   {
/* 348:825 */     return this.schema;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setSchema(String value)
/* 352:    */   {
/* 353:837 */     this.schema = value;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public String getSubselectAttribute()
/* 357:    */   {
/* 358:849 */     return this.subselectAttribute;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setSubselectAttribute(String value)
/* 362:    */   {
/* 363:861 */     this.subselectAttribute = value;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public String getTable()
/* 367:    */   {
/* 368:873 */     return this.table;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setTable(String value)
/* 372:    */   {
/* 373:885 */     this.table = value;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public String getWhere()
/* 377:    */   {
/* 378:897 */     return this.where;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setWhere(String value)
/* 382:    */   {
/* 383:909 */     this.where = value;
/* 384:    */   }
/* 385:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbPrimitiveArrayElement
 * JD-Core Version:    0.7.0.1
 */