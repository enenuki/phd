/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElements;
/*   9:    */ import javax.xml.bind.annotation.XmlType;
/*  10:    */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*  11:    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*  12:    */ 
/*  13:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  14:    */ @XmlType(name="property-element", propOrder={"meta", "columnOrFormula", "type"})
/*  15:    */ public class JaxbPropertyElement
/*  16:    */   implements SingularAttributeSource
/*  17:    */ {
/*  18:    */   protected List<JaxbMetaElement> meta;
/*  19:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="formula", type=String.class), @javax.xml.bind.annotation.XmlElement(name="column", type=JaxbColumnElement.class)})
/*  20:    */   protected List<Object> columnOrFormula;
/*  21:    */   protected JaxbTypeElement type;
/*  22:    */   @XmlAttribute
/*  23:    */   protected String access;
/*  24:    */   @XmlAttribute
/*  25:    */   protected String column;
/*  26:    */   @XmlAttribute
/*  27:    */   protected String formula;
/*  28:    */   @XmlAttribute
/*  29:    */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  30:    */   protected String generated;
/*  31:    */   @XmlAttribute
/*  32:    */   protected String index;
/*  33:    */   @XmlAttribute
/*  34:    */   protected Boolean insert;
/*  35:    */   @XmlAttribute
/*  36:    */   protected Boolean lazy;
/*  37:    */   @XmlAttribute
/*  38:    */   protected String length;
/*  39:    */   @XmlAttribute(required=true)
/*  40:    */   protected String name;
/*  41:    */   @XmlAttribute
/*  42:    */   protected String node;
/*  43:    */   @XmlAttribute(name="not-null")
/*  44:    */   protected Boolean notNull;
/*  45:    */   @XmlAttribute(name="optimistic-lock")
/*  46:    */   protected Boolean optimisticLock;
/*  47:    */   @XmlAttribute
/*  48:    */   protected String precision;
/*  49:    */   @XmlAttribute
/*  50:    */   protected String scale;
/*  51:    */   @XmlAttribute(name="type")
/*  52:    */   protected String typeAttribute;
/*  53:    */   @XmlAttribute
/*  54:    */   protected Boolean unique;
/*  55:    */   @XmlAttribute(name="unique-key")
/*  56:    */   protected String uniqueKey;
/*  57:    */   @XmlAttribute
/*  58:    */   protected Boolean update;
/*  59:    */   
/*  60:    */   public List<JaxbMetaElement> getMeta()
/*  61:    */   {
/*  62:150 */     if (this.meta == null) {
/*  63:151 */       this.meta = new ArrayList();
/*  64:    */     }
/*  65:153 */     return this.meta;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<Object> getColumnOrFormula()
/*  69:    */   {
/*  70:180 */     if (this.columnOrFormula == null) {
/*  71:181 */       this.columnOrFormula = new ArrayList();
/*  72:    */     }
/*  73:183 */     return this.columnOrFormula;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public JaxbTypeElement getType()
/*  77:    */   {
/*  78:195 */     return this.type;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setType(JaxbTypeElement value)
/*  82:    */   {
/*  83:207 */     this.type = value;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getAccess()
/*  87:    */   {
/*  88:219 */     return this.access;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setAccess(String value)
/*  92:    */   {
/*  93:231 */     this.access = value;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getColumn()
/*  97:    */   {
/*  98:243 */     return this.column;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setColumn(String value)
/* 102:    */   {
/* 103:255 */     this.column = value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getFormula()
/* 107:    */   {
/* 108:267 */     return this.formula;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setFormula(String value)
/* 112:    */   {
/* 113:279 */     this.formula = value;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getGenerated()
/* 117:    */   {
/* 118:291 */     if (this.generated == null) {
/* 119:292 */       return "never";
/* 120:    */     }
/* 121:294 */     return this.generated;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setGenerated(String value)
/* 125:    */   {
/* 126:307 */     this.generated = value;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getIndex()
/* 130:    */   {
/* 131:319 */     return this.index;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setIndex(String value)
/* 135:    */   {
/* 136:331 */     this.index = value;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Boolean isInsert()
/* 140:    */   {
/* 141:343 */     return this.insert;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setInsert(Boolean value)
/* 145:    */   {
/* 146:355 */     this.insert = value;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean isLazy()
/* 150:    */   {
/* 151:367 */     if (this.lazy == null) {
/* 152:368 */       return false;
/* 153:    */     }
/* 154:370 */     return this.lazy.booleanValue();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setLazy(Boolean value)
/* 158:    */   {
/* 159:383 */     this.lazy = value;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getLength()
/* 163:    */   {
/* 164:395 */     return this.length;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setLength(String value)
/* 168:    */   {
/* 169:407 */     this.length = value;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getName()
/* 173:    */   {
/* 174:419 */     return this.name;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setName(String value)
/* 178:    */   {
/* 179:431 */     this.name = value;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String getNode()
/* 183:    */   {
/* 184:443 */     return this.node;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setNode(String value)
/* 188:    */   {
/* 189:455 */     this.node = value;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Boolean isNotNull()
/* 193:    */   {
/* 194:467 */     return this.notNull;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setNotNull(Boolean value)
/* 198:    */   {
/* 199:479 */     this.notNull = value;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean isOptimisticLock()
/* 203:    */   {
/* 204:491 */     if (this.optimisticLock == null) {
/* 205:492 */       return true;
/* 206:    */     }
/* 207:494 */     return this.optimisticLock.booleanValue();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setOptimisticLock(Boolean value)
/* 211:    */   {
/* 212:507 */     this.optimisticLock = value;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String getPrecision()
/* 216:    */   {
/* 217:519 */     return this.precision;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setPrecision(String value)
/* 221:    */   {
/* 222:531 */     this.precision = value;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public String getScale()
/* 226:    */   {
/* 227:543 */     return this.scale;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setScale(String value)
/* 231:    */   {
/* 232:555 */     this.scale = value;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String getTypeAttribute()
/* 236:    */   {
/* 237:567 */     return this.typeAttribute;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setTypeAttribute(String value)
/* 241:    */   {
/* 242:579 */     this.typeAttribute = value;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isUnique()
/* 246:    */   {
/* 247:591 */     if (this.unique == null) {
/* 248:592 */       return false;
/* 249:    */     }
/* 250:594 */     return this.unique.booleanValue();
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setUnique(Boolean value)
/* 254:    */   {
/* 255:607 */     this.unique = value;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String getUniqueKey()
/* 259:    */   {
/* 260:619 */     return this.uniqueKey;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setUniqueKey(String value)
/* 264:    */   {
/* 265:631 */     this.uniqueKey = value;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Boolean isUpdate()
/* 269:    */   {
/* 270:643 */     return this.update;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setUpdate(Boolean value)
/* 274:    */   {
/* 275:655 */     this.update = value;
/* 276:    */   }
/* 277:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbPropertyElement
 * JD-Core Version:    0.7.0.1
 */