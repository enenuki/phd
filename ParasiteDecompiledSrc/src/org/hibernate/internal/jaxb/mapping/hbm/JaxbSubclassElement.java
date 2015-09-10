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
/*  13:    */ @XmlType(name="subclass-element", propOrder={"meta", "tuplizer", "synchronize", "propertyOrManyToOneOrOneToOne", "join", "subclass", "loader", "sqlInsert", "sqlUpdate", "sqlDelete", "fetchProfile", "resultset", "queryOrSqlQuery"})
/*  14:    */ public class JaxbSubclassElement
/*  15:    */   implements JoinElementSource, SubEntityElement
/*  16:    */ {
/*  17:    */   protected List<JaxbMetaElement> meta;
/*  18:    */   protected List<JaxbTuplizerElement> tuplizer;
/*  19:    */   protected List<JaxbSynchronizeElement> synchronize;
/*  20:    */   @XmlElements({@XmlElement(name="primitive-array", type=JaxbPrimitiveArrayElement.class), @XmlElement(name="property", type=JaxbPropertyElement.class), @XmlElement(name="one-to-one", type=JaxbOneToOneElement.class), @XmlElement(name="bag", type=JaxbBagElement.class), @XmlElement(name="map", type=JaxbMapElement.class), @XmlElement(name="any", type=JaxbAnyElement.class), @XmlElement(name="component", type=JaxbComponentElement.class), @XmlElement(name="set", type=JaxbSetElement.class), @XmlElement(name="array", type=JaxbArrayElement.class), @XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class), @XmlElement(name="idbag", type=JaxbIdbagElement.class), @XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @XmlElement(name="list", type=JaxbListElement.class)})
/*  21:    */   protected List<Object> propertyOrManyToOneOrOneToOne;
/*  22:    */   protected List<JaxbJoinElement> join;
/*  23:    */   protected List<JaxbSubclassElement> subclass;
/*  24:    */   protected JaxbLoaderElement loader;
/*  25:    */   @XmlElement(name="sql-insert")
/*  26:    */   protected JaxbSqlInsertElement sqlInsert;
/*  27:    */   @XmlElement(name="sql-update")
/*  28:    */   protected JaxbSqlUpdateElement sqlUpdate;
/*  29:    */   @XmlElement(name="sql-delete")
/*  30:    */   protected JaxbSqlDeleteElement sqlDelete;
/*  31:    */   @XmlElement(name="fetch-profile")
/*  32:    */   protected List<JaxbFetchProfileElement> fetchProfile;
/*  33:    */   protected List<JaxbResultsetElement> resultset;
/*  34:    */   @XmlElements({@XmlElement(name="sql-query", type=JaxbSqlQueryElement.class), @XmlElement(name="query", type=JaxbQueryElement.class)})
/*  35:    */   protected List<Object> queryOrSqlQuery;
/*  36:    */   @XmlAttribute(name="abstract")
/*  37:    */   protected Boolean _abstract;
/*  38:    */   @XmlAttribute(name="batch-size")
/*  39:    */   protected String batchSize;
/*  40:    */   @XmlAttribute(name="discriminator-value")
/*  41:    */   protected String discriminatorValue;
/*  42:    */   @XmlAttribute(name="dynamic-insert")
/*  43:    */   protected Boolean dynamicInsert;
/*  44:    */   @XmlAttribute(name="dynamic-update")
/*  45:    */   protected Boolean dynamicUpdate;
/*  46:    */   @XmlAttribute(name="entity-name")
/*  47:    */   protected String entityName;
/*  48:    */   @XmlAttribute(name="extends")
/*  49:    */   protected String _extends;
/*  50:    */   @XmlAttribute
/*  51:    */   protected Boolean lazy;
/*  52:    */   @XmlAttribute
/*  53:    */   protected String name;
/*  54:    */   @XmlAttribute
/*  55:    */   protected String node;
/*  56:    */   @XmlAttribute
/*  57:    */   protected String persister;
/*  58:    */   @XmlAttribute
/*  59:    */   protected String proxy;
/*  60:    */   @XmlAttribute(name="select-before-update")
/*  61:    */   protected Boolean selectBeforeUpdate;
/*  62:    */   
/*  63:    */   public List<JaxbMetaElement> getMeta()
/*  64:    */   {
/*  65:187 */     if (this.meta == null) {
/*  66:188 */       this.meta = new ArrayList();
/*  67:    */     }
/*  68:190 */     return this.meta;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<JaxbTuplizerElement> getTuplizer()
/*  72:    */   {
/*  73:216 */     if (this.tuplizer == null) {
/*  74:217 */       this.tuplizer = new ArrayList();
/*  75:    */     }
/*  76:219 */     return this.tuplizer;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<JaxbSynchronizeElement> getSynchronize()
/*  80:    */   {
/*  81:245 */     if (this.synchronize == null) {
/*  82:246 */       this.synchronize = new ArrayList();
/*  83:    */     }
/*  84:248 */     return this.synchronize;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<Object> getPropertyOrManyToOneOrOneToOne()
/*  88:    */   {
/*  89:286 */     if (this.propertyOrManyToOneOrOneToOne == null) {
/*  90:287 */       this.propertyOrManyToOneOrOneToOne = new ArrayList();
/*  91:    */     }
/*  92:289 */     return this.propertyOrManyToOneOrOneToOne;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List<JaxbJoinElement> getJoin()
/*  96:    */   {
/*  97:315 */     if (this.join == null) {
/*  98:316 */       this.join = new ArrayList();
/*  99:    */     }
/* 100:318 */     return this.join;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<JaxbSubclassElement> getSubclass()
/* 104:    */   {
/* 105:344 */     if (this.subclass == null) {
/* 106:345 */       this.subclass = new ArrayList();
/* 107:    */     }
/* 108:347 */     return this.subclass;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public JaxbLoaderElement getLoader()
/* 112:    */   {
/* 113:359 */     return this.loader;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setLoader(JaxbLoaderElement value)
/* 117:    */   {
/* 118:371 */     this.loader = value;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public JaxbSqlInsertElement getSqlInsert()
/* 122:    */   {
/* 123:383 */     return this.sqlInsert;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setSqlInsert(JaxbSqlInsertElement value)
/* 127:    */   {
/* 128:395 */     this.sqlInsert = value;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public JaxbSqlUpdateElement getSqlUpdate()
/* 132:    */   {
/* 133:407 */     return this.sqlUpdate;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/* 137:    */   {
/* 138:419 */     this.sqlUpdate = value;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public JaxbSqlDeleteElement getSqlDelete()
/* 142:    */   {
/* 143:431 */     return this.sqlDelete;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setSqlDelete(JaxbSqlDeleteElement value)
/* 147:    */   {
/* 148:443 */     this.sqlDelete = value;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public List<JaxbFetchProfileElement> getFetchProfile()
/* 152:    */   {
/* 153:469 */     if (this.fetchProfile == null) {
/* 154:470 */       this.fetchProfile = new ArrayList();
/* 155:    */     }
/* 156:472 */     return this.fetchProfile;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<JaxbResultsetElement> getResultset()
/* 160:    */   {
/* 161:498 */     if (this.resultset == null) {
/* 162:499 */       this.resultset = new ArrayList();
/* 163:    */     }
/* 164:501 */     return this.resultset;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Object> getQueryOrSqlQuery()
/* 168:    */   {
/* 169:528 */     if (this.queryOrSqlQuery == null) {
/* 170:529 */       this.queryOrSqlQuery = new ArrayList();
/* 171:    */     }
/* 172:531 */     return this.queryOrSqlQuery;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Boolean isAbstract()
/* 176:    */   {
/* 177:543 */     return this._abstract;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setAbstract(Boolean value)
/* 181:    */   {
/* 182:555 */     this._abstract = value;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getBatchSize()
/* 186:    */   {
/* 187:567 */     return this.batchSize;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setBatchSize(String value)
/* 191:    */   {
/* 192:579 */     this.batchSize = value;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getDiscriminatorValue()
/* 196:    */   {
/* 197:591 */     return this.discriminatorValue;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setDiscriminatorValue(String value)
/* 201:    */   {
/* 202:603 */     this.discriminatorValue = value;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public boolean isDynamicInsert()
/* 206:    */   {
/* 207:615 */     if (this.dynamicInsert == null) {
/* 208:616 */       return false;
/* 209:    */     }
/* 210:618 */     return this.dynamicInsert.booleanValue();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setDynamicInsert(Boolean value)
/* 214:    */   {
/* 215:631 */     this.dynamicInsert = value;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isDynamicUpdate()
/* 219:    */   {
/* 220:643 */     if (this.dynamicUpdate == null) {
/* 221:644 */       return false;
/* 222:    */     }
/* 223:646 */     return this.dynamicUpdate.booleanValue();
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setDynamicUpdate(Boolean value)
/* 227:    */   {
/* 228:659 */     this.dynamicUpdate = value;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String getEntityName()
/* 232:    */   {
/* 233:671 */     return this.entityName;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setEntityName(String value)
/* 237:    */   {
/* 238:683 */     this.entityName = value;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getExtends()
/* 242:    */   {
/* 243:695 */     return this._extends;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setExtends(String value)
/* 247:    */   {
/* 248:707 */     this._extends = value;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public Boolean isLazy()
/* 252:    */   {
/* 253:719 */     return this.lazy;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setLazy(Boolean value)
/* 257:    */   {
/* 258:731 */     this.lazy = value;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getName()
/* 262:    */   {
/* 263:743 */     return this.name;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setName(String value)
/* 267:    */   {
/* 268:755 */     this.name = value;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getNode()
/* 272:    */   {
/* 273:767 */     return this.node;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setNode(String value)
/* 277:    */   {
/* 278:779 */     this.node = value;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public String getPersister()
/* 282:    */   {
/* 283:791 */     return this.persister;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setPersister(String value)
/* 287:    */   {
/* 288:803 */     this.persister = value;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public String getProxy()
/* 292:    */   {
/* 293:815 */     return this.proxy;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setProxy(String value)
/* 297:    */   {
/* 298:827 */     this.proxy = value;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public boolean isSelectBeforeUpdate()
/* 302:    */   {
/* 303:839 */     if (this.selectBeforeUpdate == null) {
/* 304:840 */       return false;
/* 305:    */     }
/* 306:842 */     return this.selectBeforeUpdate.booleanValue();
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setSelectBeforeUpdate(Boolean value)
/* 310:    */   {
/* 311:855 */     this.selectBeforeUpdate = value;
/* 312:    */   }
/* 313:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbSubclassElement
 * JD-Core Version:    0.7.0.1
 */