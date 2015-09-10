/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="map-key-column")
/*  10:    */ public class JaxbMapKeyColumn
/*  11:    */ {
/*  12:    */   @XmlAttribute
/*  13:    */   protected String name;
/*  14:    */   @XmlAttribute
/*  15:    */   protected Boolean unique;
/*  16:    */   @XmlAttribute
/*  17:    */   protected Boolean nullable;
/*  18:    */   @XmlAttribute
/*  19:    */   protected Boolean insertable;
/*  20:    */   @XmlAttribute
/*  21:    */   protected Boolean updatable;
/*  22:    */   @XmlAttribute(name="column-definition")
/*  23:    */   protected String columnDefinition;
/*  24:    */   @XmlAttribute
/*  25:    */   protected String table;
/*  26:    */   @XmlAttribute
/*  27:    */   protected Integer length;
/*  28:    */   @XmlAttribute
/*  29:    */   protected Integer precision;
/*  30:    */   @XmlAttribute
/*  31:    */   protected Integer scale;
/*  32:    */   
/*  33:    */   public String getName()
/*  34:    */   {
/*  35: 89 */     return this.name;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setName(String value)
/*  39:    */   {
/*  40:101 */     this.name = value;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Boolean isUnique()
/*  44:    */   {
/*  45:113 */     return this.unique;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setUnique(Boolean value)
/*  49:    */   {
/*  50:125 */     this.unique = value;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Boolean isNullable()
/*  54:    */   {
/*  55:137 */     return this.nullable;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setNullable(Boolean value)
/*  59:    */   {
/*  60:149 */     this.nullable = value;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Boolean isInsertable()
/*  64:    */   {
/*  65:161 */     return this.insertable;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setInsertable(Boolean value)
/*  69:    */   {
/*  70:173 */     this.insertable = value;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Boolean isUpdatable()
/*  74:    */   {
/*  75:185 */     return this.updatable;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setUpdatable(Boolean value)
/*  79:    */   {
/*  80:197 */     this.updatable = value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getColumnDefinition()
/*  84:    */   {
/*  85:209 */     return this.columnDefinition;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setColumnDefinition(String value)
/*  89:    */   {
/*  90:221 */     this.columnDefinition = value;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getTable()
/*  94:    */   {
/*  95:233 */     return this.table;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setTable(String value)
/*  99:    */   {
/* 100:245 */     this.table = value;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Integer getLength()
/* 104:    */   {
/* 105:257 */     return this.length;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setLength(Integer value)
/* 109:    */   {
/* 110:269 */     this.length = value;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Integer getPrecision()
/* 114:    */   {
/* 115:281 */     return this.precision;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setPrecision(Integer value)
/* 119:    */   {
/* 120:293 */     this.precision = value;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Integer getScale()
/* 124:    */   {
/* 125:305 */     return this.scale;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setScale(Integer value)
/* 129:    */   {
/* 130:317 */     this.scale = value;
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbMapKeyColumn
 * JD-Core Version:    0.7.0.1
 */