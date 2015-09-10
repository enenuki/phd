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
/*  12:    */ @XmlType(name="element-element", propOrder={"columnOrFormula", "type"})
/*  13:    */ public class JaxbElementElement
/*  14:    */ {
/*  15:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="formula", type=String.class), @javax.xml.bind.annotation.XmlElement(name="column", type=JaxbColumnElement.class)})
/*  16:    */   protected List<Object> columnOrFormula;
/*  17:    */   protected JaxbTypeElement type;
/*  18:    */   @XmlAttribute
/*  19:    */   protected String column;
/*  20:    */   @XmlAttribute
/*  21:    */   protected String formula;
/*  22:    */   @XmlAttribute
/*  23:    */   protected String length;
/*  24:    */   @XmlAttribute
/*  25:    */   protected String node;
/*  26:    */   @XmlAttribute(name="not-null")
/*  27:    */   protected Boolean notNull;
/*  28:    */   @XmlAttribute
/*  29:    */   protected String precision;
/*  30:    */   @XmlAttribute
/*  31:    */   protected String scale;
/*  32:    */   @XmlAttribute(name="type")
/*  33:    */   protected String typeAttribute;
/*  34:    */   @XmlAttribute
/*  35:    */   protected Boolean unique;
/*  36:    */   
/*  37:    */   public List<Object> getColumnOrFormula()
/*  38:    */   {
/*  39:109 */     if (this.columnOrFormula == null) {
/*  40:110 */       this.columnOrFormula = new ArrayList();
/*  41:    */     }
/*  42:112 */     return this.columnOrFormula;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public JaxbTypeElement getType()
/*  46:    */   {
/*  47:124 */     return this.type;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setType(JaxbTypeElement value)
/*  51:    */   {
/*  52:136 */     this.type = value;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getColumn()
/*  56:    */   {
/*  57:148 */     return this.column;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setColumn(String value)
/*  61:    */   {
/*  62:160 */     this.column = value;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getFormula()
/*  66:    */   {
/*  67:172 */     return this.formula;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setFormula(String value)
/*  71:    */   {
/*  72:184 */     this.formula = value;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getLength()
/*  76:    */   {
/*  77:196 */     return this.length;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setLength(String value)
/*  81:    */   {
/*  82:208 */     this.length = value;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getNode()
/*  86:    */   {
/*  87:220 */     return this.node;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setNode(String value)
/*  91:    */   {
/*  92:232 */     this.node = value;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isNotNull()
/*  96:    */   {
/*  97:244 */     if (this.notNull == null) {
/*  98:245 */       return false;
/*  99:    */     }
/* 100:247 */     return this.notNull.booleanValue();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNotNull(Boolean value)
/* 104:    */   {
/* 105:260 */     this.notNull = value;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getPrecision()
/* 109:    */   {
/* 110:272 */     return this.precision;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setPrecision(String value)
/* 114:    */   {
/* 115:284 */     this.precision = value;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getScale()
/* 119:    */   {
/* 120:296 */     return this.scale;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setScale(String value)
/* 124:    */   {
/* 125:308 */     this.scale = value;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getTypeAttribute()
/* 129:    */   {
/* 130:320 */     return this.typeAttribute;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setTypeAttribute(String value)
/* 134:    */   {
/* 135:332 */     this.typeAttribute = value;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isUnique()
/* 139:    */   {
/* 140:344 */     if (this.unique == null) {
/* 141:345 */       return false;
/* 142:    */     }
/* 143:347 */     return this.unique.booleanValue();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setUnique(Boolean value)
/* 147:    */   {
/* 148:360 */     this.unique = value;
/* 149:    */   }
/* 150:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbElementElement
 * JD-Core Version:    0.7.0.1
 */