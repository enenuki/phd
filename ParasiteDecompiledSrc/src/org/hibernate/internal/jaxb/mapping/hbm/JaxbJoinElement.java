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
/*  13:    */ @XmlType(name="join-element", propOrder={"subselect", "comment", "key", "propertyOrManyToOneOrComponent", "sqlInsert", "sqlUpdate", "sqlDelete"})
/*  14:    */ public class JaxbJoinElement
/*  15:    */ {
/*  16:    */   protected String subselect;
/*  17:    */   protected String comment;
/*  18:    */   @XmlElement(required=true)
/*  19:    */   protected JaxbKeyElement key;
/*  20:    */   @XmlElements({@XmlElement(name="component", type=JaxbComponentElement.class), @XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class), @XmlElement(name="property", type=JaxbPropertyElement.class), @XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @XmlElement(name="any", type=JaxbAnyElement.class)})
/*  21:    */   protected List<Object> propertyOrManyToOneOrComponent;
/*  22:    */   @XmlElement(name="sql-insert")
/*  23:    */   protected JaxbSqlInsertElement sqlInsert;
/*  24:    */   @XmlElement(name="sql-update")
/*  25:    */   protected JaxbSqlUpdateElement sqlUpdate;
/*  26:    */   @XmlElement(name="sql-delete")
/*  27:    */   protected JaxbSqlDeleteElement sqlDelete;
/*  28:    */   @XmlAttribute
/*  29:    */   protected String catalog;
/*  30:    */   @XmlAttribute
/*  31:    */   protected JaxbFetchAttribute fetch;
/*  32:    */   @XmlAttribute
/*  33:    */   protected Boolean inverse;
/*  34:    */   @XmlAttribute
/*  35:    */   protected Boolean optional;
/*  36:    */   @XmlAttribute
/*  37:    */   protected String schema;
/*  38:    */   @XmlAttribute(name="subselect")
/*  39:    */   protected String subselectAttribute;
/*  40:    */   @XmlAttribute(required=true)
/*  41:    */   protected String table;
/*  42:    */   
/*  43:    */   public String getSubselect()
/*  44:    */   {
/*  45:113 */     return this.subselect;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setSubselect(String value)
/*  49:    */   {
/*  50:125 */     this.subselect = value;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getComment()
/*  54:    */   {
/*  55:137 */     return this.comment;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setComment(String value)
/*  59:    */   {
/*  60:149 */     this.comment = value;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public JaxbKeyElement getKey()
/*  64:    */   {
/*  65:161 */     return this.key;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setKey(JaxbKeyElement value)
/*  69:    */   {
/*  70:173 */     this.key = value;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<Object> getPropertyOrManyToOneOrComponent()
/*  74:    */   {
/*  75:203 */     if (this.propertyOrManyToOneOrComponent == null) {
/*  76:204 */       this.propertyOrManyToOneOrComponent = new ArrayList();
/*  77:    */     }
/*  78:206 */     return this.propertyOrManyToOneOrComponent;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public JaxbSqlInsertElement getSqlInsert()
/*  82:    */   {
/*  83:218 */     return this.sqlInsert;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setSqlInsert(JaxbSqlInsertElement value)
/*  87:    */   {
/*  88:230 */     this.sqlInsert = value;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public JaxbSqlUpdateElement getSqlUpdate()
/*  92:    */   {
/*  93:242 */     return this.sqlUpdate;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setSqlUpdate(JaxbSqlUpdateElement value)
/*  97:    */   {
/*  98:254 */     this.sqlUpdate = value;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public JaxbSqlDeleteElement getSqlDelete()
/* 102:    */   {
/* 103:266 */     return this.sqlDelete;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setSqlDelete(JaxbSqlDeleteElement value)
/* 107:    */   {
/* 108:278 */     this.sqlDelete = value;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getCatalog()
/* 112:    */   {
/* 113:290 */     return this.catalog;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setCatalog(String value)
/* 117:    */   {
/* 118:302 */     this.catalog = value;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public JaxbFetchAttribute getFetch()
/* 122:    */   {
/* 123:314 */     if (this.fetch == null) {
/* 124:315 */       return JaxbFetchAttribute.JOIN;
/* 125:    */     }
/* 126:317 */     return this.fetch;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setFetch(JaxbFetchAttribute value)
/* 130:    */   {
/* 131:330 */     this.fetch = value;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean isInverse()
/* 135:    */   {
/* 136:342 */     if (this.inverse == null) {
/* 137:343 */       return false;
/* 138:    */     }
/* 139:345 */     return this.inverse.booleanValue();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setInverse(Boolean value)
/* 143:    */   {
/* 144:358 */     this.inverse = value;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isOptional()
/* 148:    */   {
/* 149:370 */     if (this.optional == null) {
/* 150:371 */       return false;
/* 151:    */     }
/* 152:373 */     return this.optional.booleanValue();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setOptional(Boolean value)
/* 156:    */   {
/* 157:386 */     this.optional = value;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getSchema()
/* 161:    */   {
/* 162:398 */     return this.schema;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setSchema(String value)
/* 166:    */   {
/* 167:410 */     this.schema = value;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getSubselectAttribute()
/* 171:    */   {
/* 172:422 */     return this.subselectAttribute;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setSubselectAttribute(String value)
/* 176:    */   {
/* 177:434 */     this.subselectAttribute = value;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getTable()
/* 181:    */   {
/* 182:446 */     return this.table;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setTable(String value)
/* 186:    */   {
/* 187:458 */     this.table = value;
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbJoinElement
 * JD-Core Version:    0.7.0.1
 */