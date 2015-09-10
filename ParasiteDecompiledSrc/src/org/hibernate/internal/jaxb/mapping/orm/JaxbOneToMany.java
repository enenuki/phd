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
/*  12:    */ @XmlType(name="one-to-many", propOrder={"orderBy", "orderColumn", "mapKey", "mapKeyClass", "mapKeyTemporal", "mapKeyEnumerated", "mapKeyAttributeOverride", "mapKeyColumn", "mapKeyJoinColumn", "joinTable", "joinColumn", "cascade"})
/*  13:    */ public class JaxbOneToMany
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
/*  33:    */   @XmlElement(name="join-table")
/*  34:    */   protected JaxbJoinTable joinTable;
/*  35:    */   @XmlElement(name="join-column")
/*  36:    */   protected List<JaxbJoinColumn> joinColumn;
/*  37:    */   protected JaxbCascadeType cascade;
/*  38:    */   @XmlAttribute(required=true)
/*  39:    */   protected String name;
/*  40:    */   @XmlAttribute(name="target-entity")
/*  41:    */   protected String targetEntity;
/*  42:    */   @XmlAttribute
/*  43:    */   protected JaxbFetchType fetch;
/*  44:    */   @XmlAttribute
/*  45:    */   protected JaxbAccessType access;
/*  46:    */   @XmlAttribute(name="mapped-by")
/*  47:    */   protected String mappedBy;
/*  48:    */   @XmlAttribute(name="orphan-removal")
/*  49:    */   protected Boolean orphanRemoval;
/*  50:    */   
/*  51:    */   public String getOrderBy()
/*  52:    */   {
/*  53:140 */     return this.orderBy;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setOrderBy(String value)
/*  57:    */   {
/*  58:152 */     this.orderBy = value;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public JaxbOrderColumn getOrderColumn()
/*  62:    */   {
/*  63:164 */     return this.orderColumn;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setOrderColumn(JaxbOrderColumn value)
/*  67:    */   {
/*  68:176 */     this.orderColumn = value;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public JaxbMapKey getMapKey()
/*  72:    */   {
/*  73:188 */     return this.mapKey;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setMapKey(JaxbMapKey value)
/*  77:    */   {
/*  78:200 */     this.mapKey = value;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public JaxbMapKeyClass getMapKeyClass()
/*  82:    */   {
/*  83:212 */     return this.mapKeyClass;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setMapKeyClass(JaxbMapKeyClass value)
/*  87:    */   {
/*  88:224 */     this.mapKeyClass = value;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public JaxbTemporalType getMapKeyTemporal()
/*  92:    */   {
/*  93:236 */     return this.mapKeyTemporal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setMapKeyTemporal(JaxbTemporalType value)
/*  97:    */   {
/*  98:248 */     this.mapKeyTemporal = value;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public JaxbEnumType getMapKeyEnumerated()
/* 102:    */   {
/* 103:260 */     return this.mapKeyEnumerated;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setMapKeyEnumerated(JaxbEnumType value)
/* 107:    */   {
/* 108:272 */     this.mapKeyEnumerated = value;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<JaxbAttributeOverride> getMapKeyAttributeOverride()
/* 112:    */   {
/* 113:298 */     if (this.mapKeyAttributeOverride == null) {
/* 114:299 */       this.mapKeyAttributeOverride = new ArrayList();
/* 115:    */     }
/* 116:301 */     return this.mapKeyAttributeOverride;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public JaxbMapKeyColumn getMapKeyColumn()
/* 120:    */   {
/* 121:313 */     return this.mapKeyColumn;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setMapKeyColumn(JaxbMapKeyColumn value)
/* 125:    */   {
/* 126:325 */     this.mapKeyColumn = value;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public List<JaxbMapKeyJoinColumn> getMapKeyJoinColumn()
/* 130:    */   {
/* 131:351 */     if (this.mapKeyJoinColumn == null) {
/* 132:352 */       this.mapKeyJoinColumn = new ArrayList();
/* 133:    */     }
/* 134:354 */     return this.mapKeyJoinColumn;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public JaxbJoinTable getJoinTable()
/* 138:    */   {
/* 139:366 */     return this.joinTable;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setJoinTable(JaxbJoinTable value)
/* 143:    */   {
/* 144:378 */     this.joinTable = value;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public List<JaxbJoinColumn> getJoinColumn()
/* 148:    */   {
/* 149:404 */     if (this.joinColumn == null) {
/* 150:405 */       this.joinColumn = new ArrayList();
/* 151:    */     }
/* 152:407 */     return this.joinColumn;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public JaxbCascadeType getCascade()
/* 156:    */   {
/* 157:419 */     return this.cascade;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setCascade(JaxbCascadeType value)
/* 161:    */   {
/* 162:431 */     this.cascade = value;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getName()
/* 166:    */   {
/* 167:443 */     return this.name;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setName(String value)
/* 171:    */   {
/* 172:455 */     this.name = value;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getTargetEntity()
/* 176:    */   {
/* 177:467 */     return this.targetEntity;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setTargetEntity(String value)
/* 181:    */   {
/* 182:479 */     this.targetEntity = value;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public JaxbFetchType getFetch()
/* 186:    */   {
/* 187:491 */     return this.fetch;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setFetch(JaxbFetchType value)
/* 191:    */   {
/* 192:503 */     this.fetch = value;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public JaxbAccessType getAccess()
/* 196:    */   {
/* 197:515 */     return this.access;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setAccess(JaxbAccessType value)
/* 201:    */   {
/* 202:527 */     this.access = value;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String getMappedBy()
/* 206:    */   {
/* 207:539 */     return this.mappedBy;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setMappedBy(String value)
/* 211:    */   {
/* 212:551 */     this.mappedBy = value;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Boolean isOrphanRemoval()
/* 216:    */   {
/* 217:563 */     return this.orphanRemoval;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setOrphanRemoval(Boolean value)
/* 221:    */   {
/* 222:575 */     this.orphanRemoval = value;
/* 223:    */   }
/* 224:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbOneToMany
 * JD-Core Version:    0.7.0.1
 */