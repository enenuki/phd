/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
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
/*  12:    */ @XmlType(name="element-collection", propOrder={"orderBy", "orderColumn", "mapKey", "mapKeyClass", "mapKeyTemporal", "mapKeyEnumerated", "mapKeyAttributeOverride", "mapKeyColumn", "mapKeyJoinColumn", "column", "temporal", "enumerated", "lob", "attributeOverride", "associationOverride", "collectionTable"})
/*  13:    */ public class JaxbElementCollection
/*  14:    */ {
/*  15:    */   @XmlElement(name="order-by")
/*  16:    */   protected String orderBy;
/*  17:    */   @XmlElement(name="order-column")
/*  18:    */   protected JaxbOrderColumn orderColumn;
/*  19:    */   @XmlElement(name="map-key")
/*  20:    */   protected JaxbMapKey mapKey;
/*  21:    */   @XmlElement(name="map-key-class")
/*  22:    */   protected JaxbMapKeyClass mapKeyClass;
/*  23:    */   @XmlElement(name="map-key-temporal")
/*  24:    */   protected JaxbTemporalType mapKeyTemporal;
/*  25:    */   @XmlElement(name="map-key-enumerated")
/*  26:    */   protected JaxbEnumType mapKeyEnumerated;
/*  27:    */   @XmlElement(name="map-key-attribute-override")
/*  28:    */   protected List<JaxbAttributeOverride> mapKeyAttributeOverride;
/*  29:    */   @XmlElement(name="map-key-column")
/*  30:    */   protected JaxbMapKeyColumn mapKeyColumn;
/*  31:    */   @XmlElement(name="map-key-join-column")
/*  32:    */   protected List<JaxbMapKeyJoinColumn> mapKeyJoinColumn;
/*  33:    */   protected JaxbColumn column;
/*  34:    */   protected JaxbTemporalType temporal;
/*  35:    */   protected JaxbEnumType enumerated;
/*  36:    */   protected JaxbLob lob;
/*  37:    */   @XmlElement(name="attribute-override")
/*  38:    */   protected List<JaxbAttributeOverride> attributeOverride;
/*  39:    */   @XmlElement(name="association-override")
/*  40:    */   protected List<JaxbAssociationOverride> associationOverride;
/*  41:    */   @XmlElement(name="collection-table")
/*  42:    */   protected JaxbCollectionTable collectionTable;
/*  43:    */   @XmlAttribute(required=true)
/*  44:    */   protected String name;
/*  45:    */   @XmlAttribute(name="target-class")
/*  46:    */   protected String targetClass;
/*  47:    */   @XmlAttribute
/*  48:    */   protected JaxbFetchType fetch;
/*  49:    */   @XmlAttribute
/*  50:    */   protected JaxbAccessType access;
/*  51:    */   
/*  52:    */   public String getOrderBy()
/*  53:    */   {
/*  54:149 */     return this.orderBy;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setOrderBy(String value)
/*  58:    */   {
/*  59:161 */     this.orderBy = value;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public JaxbOrderColumn getOrderColumn()
/*  63:    */   {
/*  64:173 */     return this.orderColumn;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setOrderColumn(JaxbOrderColumn value)
/*  68:    */   {
/*  69:185 */     this.orderColumn = value;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public JaxbMapKey getMapKey()
/*  73:    */   {
/*  74:197 */     return this.mapKey;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setMapKey(JaxbMapKey value)
/*  78:    */   {
/*  79:209 */     this.mapKey = value;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public JaxbMapKeyClass getMapKeyClass()
/*  83:    */   {
/*  84:221 */     return this.mapKeyClass;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setMapKeyClass(JaxbMapKeyClass value)
/*  88:    */   {
/*  89:233 */     this.mapKeyClass = value;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public JaxbTemporalType getMapKeyTemporal()
/*  93:    */   {
/*  94:245 */     return this.mapKeyTemporal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setMapKeyTemporal(JaxbTemporalType value)
/*  98:    */   {
/*  99:257 */     this.mapKeyTemporal = value;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public JaxbEnumType getMapKeyEnumerated()
/* 103:    */   {
/* 104:269 */     return this.mapKeyEnumerated;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setMapKeyEnumerated(JaxbEnumType value)
/* 108:    */   {
/* 109:281 */     this.mapKeyEnumerated = value;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<JaxbAttributeOverride> getMapKeyAttributeOverride()
/* 113:    */   {
/* 114:307 */     if (this.mapKeyAttributeOverride == null) {
/* 115:308 */       this.mapKeyAttributeOverride = new ArrayList();
/* 116:    */     }
/* 117:310 */     return this.mapKeyAttributeOverride;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public JaxbMapKeyColumn getMapKeyColumn()
/* 121:    */   {
/* 122:322 */     return this.mapKeyColumn;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setMapKeyColumn(JaxbMapKeyColumn value)
/* 126:    */   {
/* 127:334 */     this.mapKeyColumn = value;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<JaxbMapKeyJoinColumn> getMapKeyJoinColumn()
/* 131:    */   {
/* 132:360 */     if (this.mapKeyJoinColumn == null) {
/* 133:361 */       this.mapKeyJoinColumn = new ArrayList();
/* 134:    */     }
/* 135:363 */     return this.mapKeyJoinColumn;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public JaxbColumn getColumn()
/* 139:    */   {
/* 140:375 */     return this.column;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setColumn(JaxbColumn value)
/* 144:    */   {
/* 145:387 */     this.column = value;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public JaxbTemporalType getTemporal()
/* 149:    */   {
/* 150:399 */     return this.temporal;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setTemporal(JaxbTemporalType value)
/* 154:    */   {
/* 155:411 */     this.temporal = value;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public JaxbEnumType getEnumerated()
/* 159:    */   {
/* 160:423 */     return this.enumerated;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setEnumerated(JaxbEnumType value)
/* 164:    */   {
/* 165:435 */     this.enumerated = value;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public JaxbLob getLob()
/* 169:    */   {
/* 170:447 */     return this.lob;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setLob(JaxbLob value)
/* 174:    */   {
/* 175:459 */     this.lob = value;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<JaxbAttributeOverride> getAttributeOverride()
/* 179:    */   {
/* 180:485 */     if (this.attributeOverride == null) {
/* 181:486 */       this.attributeOverride = new ArrayList();
/* 182:    */     }
/* 183:488 */     return this.attributeOverride;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<JaxbAssociationOverride> getAssociationOverride()
/* 187:    */   {
/* 188:514 */     if (this.associationOverride == null) {
/* 189:515 */       this.associationOverride = new ArrayList();
/* 190:    */     }
/* 191:517 */     return this.associationOverride;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public JaxbCollectionTable getCollectionTable()
/* 195:    */   {
/* 196:529 */     return this.collectionTable;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setCollectionTable(JaxbCollectionTable value)
/* 200:    */   {
/* 201:541 */     this.collectionTable = value;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String getName()
/* 205:    */   {
/* 206:553 */     return this.name;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setName(String value)
/* 210:    */   {
/* 211:565 */     this.name = value;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String getTargetClass()
/* 215:    */   {
/* 216:577 */     return this.targetClass;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setTargetClass(String value)
/* 220:    */   {
/* 221:589 */     this.targetClass = value;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public JaxbFetchType getFetch()
/* 225:    */   {
/* 226:601 */     return this.fetch;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setFetch(JaxbFetchType value)
/* 230:    */   {
/* 231:613 */     this.fetch = value;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public JaxbAccessType getAccess()
/* 235:    */   {
/* 236:625 */     return this.access;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setAccess(JaxbAccessType value)
/* 240:    */   {
/* 241:637 */     this.access = value;
/* 242:    */   }
/* 243:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbElementCollection
 * JD-Core Version:    0.7.0.1
 */