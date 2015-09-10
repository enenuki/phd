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
/*  12:    */ @XmlType(name="many-to-many", propOrder={"orderBy", "orderColumn", "mapKey", "mapKeyClass", "mapKeyTemporal", "mapKeyEnumerated", "mapKeyAttributeOverride", "mapKeyJoinColumn", "joinTable", "cascade"})
/*  13:    */ public class JaxbManyToMany
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
/*  29:    */   @XmlElement(name="map-key-join-column")
/*  30:    */   protected List<JaxbMapKeyJoinColumn> mapKeyJoinColumn;
/*  31:    */   @XmlElement(name="join-table")
/*  32:    */   protected JaxbJoinTable joinTable;
/*  33:    */   protected JaxbCascadeType cascade;
/*  34:    */   @XmlAttribute(required=true)
/*  35:    */   protected String name;
/*  36:    */   @XmlAttribute(name="target-entity")
/*  37:    */   protected String targetEntity;
/*  38:    */   @XmlAttribute
/*  39:    */   protected JaxbFetchType fetch;
/*  40:    */   @XmlAttribute
/*  41:    */   protected JaxbAccessType access;
/*  42:    */   @XmlAttribute(name="mapped-by")
/*  43:    */   protected String mappedBy;
/*  44:    */   
/*  45:    */   public String getOrderBy()
/*  46:    */   {
/*  47:125 */     return this.orderBy;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setOrderBy(String value)
/*  51:    */   {
/*  52:137 */     this.orderBy = value;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public JaxbOrderColumn getOrderColumn()
/*  56:    */   {
/*  57:149 */     return this.orderColumn;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setOrderColumn(JaxbOrderColumn value)
/*  61:    */   {
/*  62:161 */     this.orderColumn = value;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public JaxbMapKey getMapKey()
/*  66:    */   {
/*  67:173 */     return this.mapKey;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setMapKey(JaxbMapKey value)
/*  71:    */   {
/*  72:185 */     this.mapKey = value;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public JaxbMapKeyClass getMapKeyClass()
/*  76:    */   {
/*  77:197 */     return this.mapKeyClass;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setMapKeyClass(JaxbMapKeyClass value)
/*  81:    */   {
/*  82:209 */     this.mapKeyClass = value;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public JaxbTemporalType getMapKeyTemporal()
/*  86:    */   {
/*  87:221 */     return this.mapKeyTemporal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setMapKeyTemporal(JaxbTemporalType value)
/*  91:    */   {
/*  92:233 */     this.mapKeyTemporal = value;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public JaxbEnumType getMapKeyEnumerated()
/*  96:    */   {
/*  97:245 */     return this.mapKeyEnumerated;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setMapKeyEnumerated(JaxbEnumType value)
/* 101:    */   {
/* 102:257 */     this.mapKeyEnumerated = value;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<JaxbAttributeOverride> getMapKeyAttributeOverride()
/* 106:    */   {
/* 107:283 */     if (this.mapKeyAttributeOverride == null) {
/* 108:284 */       this.mapKeyAttributeOverride = new ArrayList();
/* 109:    */     }
/* 110:286 */     return this.mapKeyAttributeOverride;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<JaxbMapKeyJoinColumn> getMapKeyJoinColumn()
/* 114:    */   {
/* 115:312 */     if (this.mapKeyJoinColumn == null) {
/* 116:313 */       this.mapKeyJoinColumn = new ArrayList();
/* 117:    */     }
/* 118:315 */     return this.mapKeyJoinColumn;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public JaxbJoinTable getJoinTable()
/* 122:    */   {
/* 123:327 */     return this.joinTable;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setJoinTable(JaxbJoinTable value)
/* 127:    */   {
/* 128:339 */     this.joinTable = value;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public JaxbCascadeType getCascade()
/* 132:    */   {
/* 133:351 */     return this.cascade;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setCascade(JaxbCascadeType value)
/* 137:    */   {
/* 138:363 */     this.cascade = value;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getName()
/* 142:    */   {
/* 143:375 */     return this.name;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setName(String value)
/* 147:    */   {
/* 148:387 */     this.name = value;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getTargetEntity()
/* 152:    */   {
/* 153:399 */     return this.targetEntity;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setTargetEntity(String value)
/* 157:    */   {
/* 158:411 */     this.targetEntity = value;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public JaxbFetchType getFetch()
/* 162:    */   {
/* 163:423 */     return this.fetch;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setFetch(JaxbFetchType value)
/* 167:    */   {
/* 168:435 */     this.fetch = value;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public JaxbAccessType getAccess()
/* 172:    */   {
/* 173:447 */     return this.access;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setAccess(JaxbAccessType value)
/* 177:    */   {
/* 178:459 */     this.access = value;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getMappedBy()
/* 182:    */   {
/* 183:471 */     return this.mappedBy;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setMappedBy(String value)
/* 187:    */   {
/* 188:483 */     this.mappedBy = value;
/* 189:    */   }
/* 190:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbManyToMany
 * JD-Core Version:    0.7.0.1
 */