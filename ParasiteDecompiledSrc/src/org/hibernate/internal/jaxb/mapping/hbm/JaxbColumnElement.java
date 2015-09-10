/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="column-element", propOrder={"comment"})
/*  10:    */ public class JaxbColumnElement
/*  11:    */ {
/*  12:    */   protected String comment;
/*  13:    */   @XmlAttribute
/*  14:    */   protected String check;
/*  15:    */   @XmlAttribute(name="default")
/*  16:    */   protected String _default;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String index;
/*  19:    */   @XmlAttribute
/*  20:    */   protected String length;
/*  21:    */   @XmlAttribute(required=true)
/*  22:    */   protected String name;
/*  23:    */   @XmlAttribute(name="not-null")
/*  24:    */   protected Boolean notNull;
/*  25:    */   @XmlAttribute
/*  26:    */   protected String precision;
/*  27:    */   @XmlAttribute
/*  28:    */   protected String read;
/*  29:    */   @XmlAttribute
/*  30:    */   protected String scale;
/*  31:    */   @XmlAttribute(name="sql-type")
/*  32:    */   protected String sqlType;
/*  33:    */   @XmlAttribute
/*  34:    */   protected Boolean unique;
/*  35:    */   @XmlAttribute(name="unique-key")
/*  36:    */   protected String uniqueKey;
/*  37:    */   @XmlAttribute
/*  38:    */   protected String write;
/*  39:    */   
/*  40:    */   public String getComment()
/*  41:    */   {
/*  42: 92 */     return this.comment;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setComment(String value)
/*  46:    */   {
/*  47:104 */     this.comment = value;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getCheck()
/*  51:    */   {
/*  52:116 */     return this.check;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setCheck(String value)
/*  56:    */   {
/*  57:128 */     this.check = value;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getDefault()
/*  61:    */   {
/*  62:140 */     return this._default;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setDefault(String value)
/*  66:    */   {
/*  67:152 */     this._default = value;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getIndex()
/*  71:    */   {
/*  72:164 */     return this.index;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIndex(String value)
/*  76:    */   {
/*  77:176 */     this.index = value;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getLength()
/*  81:    */   {
/*  82:188 */     return this.length;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setLength(String value)
/*  86:    */   {
/*  87:200 */     this.length = value;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getName()
/*  91:    */   {
/*  92:212 */     return this.name;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setName(String value)
/*  96:    */   {
/*  97:224 */     this.name = value;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Boolean isNotNull()
/* 101:    */   {
/* 102:236 */     return this.notNull;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setNotNull(Boolean value)
/* 106:    */   {
/* 107:248 */     this.notNull = value;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getPrecision()
/* 111:    */   {
/* 112:260 */     return this.precision;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setPrecision(String value)
/* 116:    */   {
/* 117:272 */     this.precision = value;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getRead()
/* 121:    */   {
/* 122:284 */     return this.read;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setRead(String value)
/* 126:    */   {
/* 127:296 */     this.read = value;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getScale()
/* 131:    */   {
/* 132:308 */     return this.scale;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setScale(String value)
/* 136:    */   {
/* 137:320 */     this.scale = value;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getSqlType()
/* 141:    */   {
/* 142:332 */     return this.sqlType;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setSqlType(String value)
/* 146:    */   {
/* 147:344 */     this.sqlType = value;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Boolean isUnique()
/* 151:    */   {
/* 152:356 */     return this.unique;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setUnique(Boolean value)
/* 156:    */   {
/* 157:368 */     this.unique = value;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getUniqueKey()
/* 161:    */   {
/* 162:380 */     return this.uniqueKey;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setUniqueKey(String value)
/* 166:    */   {
/* 167:392 */     this.uniqueKey = value;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getWrite()
/* 171:    */   {
/* 172:404 */     return this.write;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setWrite(String value)
/* 176:    */   {
/* 177:416 */     this.write = value;
/* 178:    */   }
/* 179:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbColumnElement
 * JD-Core Version:    0.7.0.1
 */