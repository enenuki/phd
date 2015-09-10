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
/*  12:    */ @XmlType(name="table-generator", propOrder={"description", "uniqueConstraint"})
/*  13:    */ public class JaxbTableGenerator
/*  14:    */ {
/*  15:    */   protected String description;
/*  16:    */   @XmlElement(name="unique-constraint")
/*  17:    */   protected List<JaxbUniqueConstraint> uniqueConstraint;
/*  18:    */   @XmlAttribute(required=true)
/*  19:    */   protected String name;
/*  20:    */   @XmlAttribute
/*  21:    */   protected String table;
/*  22:    */   @XmlAttribute
/*  23:    */   protected String catalog;
/*  24:    */   @XmlAttribute
/*  25:    */   protected String schema;
/*  26:    */   @XmlAttribute(name="pk-column-name")
/*  27:    */   protected String pkColumnName;
/*  28:    */   @XmlAttribute(name="value-column-name")
/*  29:    */   protected String valueColumnName;
/*  30:    */   @XmlAttribute(name="pk-column-value")
/*  31:    */   protected String pkColumnValue;
/*  32:    */   @XmlAttribute(name="initial-value")
/*  33:    */   protected Integer initialValue;
/*  34:    */   @XmlAttribute(name="allocation-size")
/*  35:    */   protected Integer allocationSize;
/*  36:    */   
/*  37:    */   public String getDescription()
/*  38:    */   {
/*  39: 97 */     return this.description;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setDescription(String value)
/*  43:    */   {
/*  44:109 */     this.description = value;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<JaxbUniqueConstraint> getUniqueConstraint()
/*  48:    */   {
/*  49:135 */     if (this.uniqueConstraint == null) {
/*  50:136 */       this.uniqueConstraint = new ArrayList();
/*  51:    */     }
/*  52:138 */     return this.uniqueConstraint;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getName()
/*  56:    */   {
/*  57:150 */     return this.name;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setName(String value)
/*  61:    */   {
/*  62:162 */     this.name = value;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getTable()
/*  66:    */   {
/*  67:174 */     return this.table;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setTable(String value)
/*  71:    */   {
/*  72:186 */     this.table = value;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getCatalog()
/*  76:    */   {
/*  77:198 */     return this.catalog;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setCatalog(String value)
/*  81:    */   {
/*  82:210 */     this.catalog = value;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getSchema()
/*  86:    */   {
/*  87:222 */     return this.schema;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setSchema(String value)
/*  91:    */   {
/*  92:234 */     this.schema = value;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getPkColumnName()
/*  96:    */   {
/*  97:246 */     return this.pkColumnName;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPkColumnName(String value)
/* 101:    */   {
/* 102:258 */     this.pkColumnName = value;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getValueColumnName()
/* 106:    */   {
/* 107:270 */     return this.valueColumnName;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setValueColumnName(String value)
/* 111:    */   {
/* 112:282 */     this.valueColumnName = value;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getPkColumnValue()
/* 116:    */   {
/* 117:294 */     return this.pkColumnValue;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setPkColumnValue(String value)
/* 121:    */   {
/* 122:306 */     this.pkColumnValue = value;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Integer getInitialValue()
/* 126:    */   {
/* 127:318 */     return this.initialValue;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setInitialValue(Integer value)
/* 131:    */   {
/* 132:330 */     this.initialValue = value;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Integer getAllocationSize()
/* 136:    */   {
/* 137:342 */     return this.allocationSize;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setAllocationSize(Integer value)
/* 141:    */   {
/* 142:354 */     this.allocationSize = value;
/* 143:    */   }
/* 144:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbTableGenerator
 * JD-Core Version:    0.7.0.1
 */