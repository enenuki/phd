/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElements;
/*   9:    */ import javax.xml.bind.annotation.XmlType;
/*  10:    */ 
/*  11:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  12:    */ @XmlType(name="many-to-one-element", propOrder={"meta", "columnOrFormula"})
/*  13:    */ public class JaxbManyToOneElement
/*  14:    */ {
/*  15:    */   protected List<JaxbMetaElement> meta;
/*  16:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="formula", type=String.class), @javax.xml.bind.annotation.XmlElement(name="column", type=JaxbColumnElement.class)})
/*  17:    */   protected List<Object> columnOrFormula;
/*  18:    */   @XmlAttribute
/*  19:    */   protected String access;
/*  20:    */   @XmlAttribute
/*  21:    */   protected String cascade;
/*  22:    */   @XmlAttribute(name="class")
/*  23:    */   protected String clazz;
/*  24:    */   @XmlAttribute
/*  25:    */   protected String column;
/*  26:    */   @XmlAttribute(name="embed-xml")
/*  27:    */   protected Boolean embedXml;
/*  28:    */   @XmlAttribute(name="entity-name")
/*  29:    */   protected String entityName;
/*  30:    */   @XmlAttribute
/*  31:    */   protected JaxbFetchAttribute fetch;
/*  32:    */   @XmlAttribute(name="foreign-key")
/*  33:    */   protected String foreignKey;
/*  34:    */   @XmlAttribute
/*  35:    */   protected String formula;
/*  36:    */   @XmlAttribute
/*  37:    */   protected String index;
/*  38:    */   @XmlAttribute
/*  39:    */   protected Boolean insert;
/*  40:    */   @XmlAttribute
/*  41:    */   protected JaxbLazyAttributeWithNoProxy lazy;
/*  42:    */   @XmlAttribute(required=true)
/*  43:    */   protected String name;
/*  44:    */   @XmlAttribute
/*  45:    */   protected String node;
/*  46:    */   @XmlAttribute(name="not-found")
/*  47:    */   protected JaxbNotFoundAttribute notFound;
/*  48:    */   @XmlAttribute(name="not-null")
/*  49:    */   protected Boolean notNull;
/*  50:    */   @XmlAttribute(name="optimistic-lock")
/*  51:    */   protected Boolean optimisticLock;
/*  52:    */   @XmlAttribute(name="outer-join")
/*  53:    */   protected JaxbOuterJoinAttribute outerJoin;
/*  54:    */   @XmlAttribute(name="property-ref")
/*  55:    */   protected String propertyRef;
/*  56:    */   @XmlAttribute
/*  57:    */   protected Boolean unique;
/*  58:    */   @XmlAttribute(name="unique-key")
/*  59:    */   protected String uniqueKey;
/*  60:    */   @XmlAttribute
/*  61:    */   protected Boolean update;
/*  62:    */   
/*  63:    */   public List<JaxbMetaElement> getMeta()
/*  64:    */   {
/*  65:147 */     if (this.meta == null) {
/*  66:148 */       this.meta = new ArrayList();
/*  67:    */     }
/*  68:150 */     return this.meta;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<Object> getColumnOrFormula()
/*  72:    */   {
/*  73:177 */     if (this.columnOrFormula == null) {
/*  74:178 */       this.columnOrFormula = new ArrayList();
/*  75:    */     }
/*  76:180 */     return this.columnOrFormula;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getAccess()
/*  80:    */   {
/*  81:192 */     return this.access;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setAccess(String value)
/*  85:    */   {
/*  86:204 */     this.access = value;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getCascade()
/*  90:    */   {
/*  91:216 */     return this.cascade;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setCascade(String value)
/*  95:    */   {
/*  96:228 */     this.cascade = value;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getClazz()
/* 100:    */   {
/* 101:240 */     return this.clazz;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setClazz(String value)
/* 105:    */   {
/* 106:252 */     this.clazz = value;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getColumn()
/* 110:    */   {
/* 111:264 */     return this.column;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setColumn(String value)
/* 115:    */   {
/* 116:276 */     this.column = value;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isEmbedXml()
/* 120:    */   {
/* 121:288 */     if (this.embedXml == null) {
/* 122:289 */       return true;
/* 123:    */     }
/* 124:291 */     return this.embedXml.booleanValue();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setEmbedXml(Boolean value)
/* 128:    */   {
/* 129:304 */     this.embedXml = value;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getEntityName()
/* 133:    */   {
/* 134:316 */     return this.entityName;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setEntityName(String value)
/* 138:    */   {
/* 139:328 */     this.entityName = value;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public JaxbFetchAttribute getFetch()
/* 143:    */   {
/* 144:340 */     return this.fetch;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setFetch(JaxbFetchAttribute value)
/* 148:    */   {
/* 149:352 */     this.fetch = value;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getForeignKey()
/* 153:    */   {
/* 154:364 */     return this.foreignKey;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setForeignKey(String value)
/* 158:    */   {
/* 159:376 */     this.foreignKey = value;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getFormula()
/* 163:    */   {
/* 164:388 */     return this.formula;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setFormula(String value)
/* 168:    */   {
/* 169:400 */     this.formula = value;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getIndex()
/* 173:    */   {
/* 174:412 */     return this.index;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setIndex(String value)
/* 178:    */   {
/* 179:424 */     this.index = value;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean isInsert()
/* 183:    */   {
/* 184:436 */     if (this.insert == null) {
/* 185:437 */       return true;
/* 186:    */     }
/* 187:439 */     return this.insert.booleanValue();
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setInsert(Boolean value)
/* 191:    */   {
/* 192:452 */     this.insert = value;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public JaxbLazyAttributeWithNoProxy getLazy()
/* 196:    */   {
/* 197:464 */     return this.lazy;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setLazy(JaxbLazyAttributeWithNoProxy value)
/* 201:    */   {
/* 202:476 */     this.lazy = value;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String getName()
/* 206:    */   {
/* 207:488 */     return this.name;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setName(String value)
/* 211:    */   {
/* 212:500 */     this.name = value;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String getNode()
/* 216:    */   {
/* 217:512 */     return this.node;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setNode(String value)
/* 221:    */   {
/* 222:524 */     this.node = value;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public JaxbNotFoundAttribute getNotFound()
/* 226:    */   {
/* 227:536 */     if (this.notFound == null) {
/* 228:537 */       return JaxbNotFoundAttribute.EXCEPTION;
/* 229:    */     }
/* 230:539 */     return this.notFound;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setNotFound(JaxbNotFoundAttribute value)
/* 234:    */   {
/* 235:552 */     this.notFound = value;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Boolean isNotNull()
/* 239:    */   {
/* 240:564 */     return this.notNull;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setNotNull(Boolean value)
/* 244:    */   {
/* 245:576 */     this.notNull = value;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public boolean isOptimisticLock()
/* 249:    */   {
/* 250:588 */     if (this.optimisticLock == null) {
/* 251:589 */       return true;
/* 252:    */     }
/* 253:591 */     return this.optimisticLock.booleanValue();
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setOptimisticLock(Boolean value)
/* 257:    */   {
/* 258:604 */     this.optimisticLock = value;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public JaxbOuterJoinAttribute getOuterJoin()
/* 262:    */   {
/* 263:616 */     return this.outerJoin;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/* 267:    */   {
/* 268:628 */     this.outerJoin = value;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getPropertyRef()
/* 272:    */   {
/* 273:640 */     return this.propertyRef;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setPropertyRef(String value)
/* 277:    */   {
/* 278:652 */     this.propertyRef = value;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public boolean isUnique()
/* 282:    */   {
/* 283:664 */     if (this.unique == null) {
/* 284:665 */       return false;
/* 285:    */     }
/* 286:667 */     return this.unique.booleanValue();
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setUnique(Boolean value)
/* 290:    */   {
/* 291:680 */     this.unique = value;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public String getUniqueKey()
/* 295:    */   {
/* 296:692 */     return this.uniqueKey;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setUniqueKey(String value)
/* 300:    */   {
/* 301:704 */     this.uniqueKey = value;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public boolean isUpdate()
/* 305:    */   {
/* 306:716 */     if (this.update == null) {
/* 307:717 */       return true;
/* 308:    */     }
/* 309:719 */     return this.update.booleanValue();
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setUpdate(Boolean value)
/* 313:    */   {
/* 314:732 */     this.update = value;
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbManyToOneElement
 * JD-Core Version:    0.7.0.1
 */