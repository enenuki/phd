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
/*  12:    */ @XmlType(name="many-to-many-element", propOrder={"meta", "columnOrFormula", "filter"})
/*  13:    */ public class JaxbManyToManyElement
/*  14:    */ {
/*  15:    */   protected List<JaxbMetaElement> meta;
/*  16:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="formula", type=String.class), @javax.xml.bind.annotation.XmlElement(name="column", type=JaxbColumnElement.class)})
/*  17:    */   protected List<Object> columnOrFormula;
/*  18:    */   protected List<JaxbFilterElement> filter;
/*  19:    */   @XmlAttribute(name="class")
/*  20:    */   protected String clazz;
/*  21:    */   @XmlAttribute
/*  22:    */   protected String column;
/*  23:    */   @XmlAttribute(name="embed-xml")
/*  24:    */   protected Boolean embedXml;
/*  25:    */   @XmlAttribute(name="entity-name")
/*  26:    */   protected String entityName;
/*  27:    */   @XmlAttribute
/*  28:    */   protected JaxbFetchAttribute fetch;
/*  29:    */   @XmlAttribute(name="foreign-key")
/*  30:    */   protected String foreignKey;
/*  31:    */   @XmlAttribute
/*  32:    */   protected String formula;
/*  33:    */   @XmlAttribute
/*  34:    */   protected JaxbLazyAttribute lazy;
/*  35:    */   @XmlAttribute
/*  36:    */   protected String node;
/*  37:    */   @XmlAttribute(name="not-found")
/*  38:    */   protected JaxbNotFoundAttribute notFound;
/*  39:    */   @XmlAttribute(name="order-by")
/*  40:    */   protected String orderBy;
/*  41:    */   @XmlAttribute(name="outer-join")
/*  42:    */   protected JaxbOuterJoinAttribute outerJoin;
/*  43:    */   @XmlAttribute(name="property-ref")
/*  44:    */   protected String propertyRef;
/*  45:    */   @XmlAttribute
/*  46:    */   protected Boolean unique;
/*  47:    */   @XmlAttribute
/*  48:    */   protected String where;
/*  49:    */   
/*  50:    */   public List<JaxbMetaElement> getMeta()
/*  51:    */   {
/*  52:129 */     if (this.meta == null) {
/*  53:130 */       this.meta = new ArrayList();
/*  54:    */     }
/*  55:132 */     return this.meta;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<Object> getColumnOrFormula()
/*  59:    */   {
/*  60:159 */     if (this.columnOrFormula == null) {
/*  61:160 */       this.columnOrFormula = new ArrayList();
/*  62:    */     }
/*  63:162 */     return this.columnOrFormula;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List<JaxbFilterElement> getFilter()
/*  67:    */   {
/*  68:188 */     if (this.filter == null) {
/*  69:189 */       this.filter = new ArrayList();
/*  70:    */     }
/*  71:191 */     return this.filter;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getClazz()
/*  75:    */   {
/*  76:203 */     return this.clazz;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setClazz(String value)
/*  80:    */   {
/*  81:215 */     this.clazz = value;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getColumn()
/*  85:    */   {
/*  86:227 */     return this.column;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setColumn(String value)
/*  90:    */   {
/*  91:239 */     this.column = value;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isEmbedXml()
/*  95:    */   {
/*  96:251 */     if (this.embedXml == null) {
/*  97:252 */       return true;
/*  98:    */     }
/*  99:254 */     return this.embedXml.booleanValue();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setEmbedXml(Boolean value)
/* 103:    */   {
/* 104:267 */     this.embedXml = value;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getEntityName()
/* 108:    */   {
/* 109:279 */     return this.entityName;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setEntityName(String value)
/* 113:    */   {
/* 114:291 */     this.entityName = value;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public JaxbFetchAttribute getFetch()
/* 118:    */   {
/* 119:303 */     return this.fetch;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setFetch(JaxbFetchAttribute value)
/* 123:    */   {
/* 124:315 */     this.fetch = value;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getForeignKey()
/* 128:    */   {
/* 129:327 */     return this.foreignKey;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setForeignKey(String value)
/* 133:    */   {
/* 134:339 */     this.foreignKey = value;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getFormula()
/* 138:    */   {
/* 139:351 */     return this.formula;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setFormula(String value)
/* 143:    */   {
/* 144:363 */     this.formula = value;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public JaxbLazyAttribute getLazy()
/* 148:    */   {
/* 149:375 */     return this.lazy;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setLazy(JaxbLazyAttribute value)
/* 153:    */   {
/* 154:387 */     this.lazy = value;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getNode()
/* 158:    */   {
/* 159:399 */     return this.node;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setNode(String value)
/* 163:    */   {
/* 164:411 */     this.node = value;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public JaxbNotFoundAttribute getNotFound()
/* 168:    */   {
/* 169:423 */     if (this.notFound == null) {
/* 170:424 */       return JaxbNotFoundAttribute.EXCEPTION;
/* 171:    */     }
/* 172:426 */     return this.notFound;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setNotFound(JaxbNotFoundAttribute value)
/* 176:    */   {
/* 177:439 */     this.notFound = value;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getOrderBy()
/* 181:    */   {
/* 182:451 */     return this.orderBy;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setOrderBy(String value)
/* 186:    */   {
/* 187:463 */     this.orderBy = value;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public JaxbOuterJoinAttribute getOuterJoin()
/* 191:    */   {
/* 192:475 */     return this.outerJoin;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/* 196:    */   {
/* 197:487 */     this.outerJoin = value;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getPropertyRef()
/* 201:    */   {
/* 202:499 */     return this.propertyRef;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setPropertyRef(String value)
/* 206:    */   {
/* 207:511 */     this.propertyRef = value;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean isUnique()
/* 211:    */   {
/* 212:523 */     if (this.unique == null) {
/* 213:524 */       return false;
/* 214:    */     }
/* 215:526 */     return this.unique.booleanValue();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setUnique(Boolean value)
/* 219:    */   {
/* 220:539 */     this.unique = value;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String getWhere()
/* 224:    */   {
/* 225:551 */     return this.where;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setWhere(String value)
/* 229:    */   {
/* 230:563 */     this.where = value;
/* 231:    */   }
/* 232:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbManyToManyElement
 * JD-Core Version:    0.7.0.1
 */