/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlType;
/*   9:    */ 
/*  10:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  11:    */ @XmlType(name="one-to-one-element", propOrder={"meta", "formula"})
/*  12:    */ public class JaxbOneToOneElement
/*  13:    */ {
/*  14:    */   protected List<JaxbMetaElement> meta;
/*  15:    */   protected List<String> formula;
/*  16:    */   @XmlAttribute
/*  17:    */   protected String access;
/*  18:    */   @XmlAttribute
/*  19:    */   protected String cascade;
/*  20:    */   @XmlAttribute(name="class")
/*  21:    */   protected String clazz;
/*  22:    */   @XmlAttribute
/*  23:    */   protected Boolean constrained;
/*  24:    */   @XmlAttribute(name="embed-xml")
/*  25:    */   protected Boolean embedXml;
/*  26:    */   @XmlAttribute(name="entity-name")
/*  27:    */   protected String entityName;
/*  28:    */   @XmlAttribute
/*  29:    */   protected JaxbFetchAttribute fetch;
/*  30:    */   @XmlAttribute(name="foreign-key")
/*  31:    */   protected String foreignKey;
/*  32:    */   @XmlAttribute(name="formula")
/*  33:    */   protected String formulaAttribute;
/*  34:    */   @XmlAttribute
/*  35:    */   protected JaxbLazyAttributeWithNoProxy lazy;
/*  36:    */   @XmlAttribute(required=true)
/*  37:    */   protected String name;
/*  38:    */   @XmlAttribute
/*  39:    */   protected String node;
/*  40:    */   @XmlAttribute(name="outer-join")
/*  41:    */   protected JaxbOuterJoinAttribute outerJoin;
/*  42:    */   @XmlAttribute(name="property-ref")
/*  43:    */   protected String propertyRef;
/*  44:    */   
/*  45:    */   public List<JaxbMetaElement> getMeta()
/*  46:    */   {
/*  47:114 */     if (this.meta == null) {
/*  48:115 */       this.meta = new ArrayList();
/*  49:    */     }
/*  50:117 */     return this.meta;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<String> getFormula()
/*  54:    */   {
/*  55:143 */     if (this.formula == null) {
/*  56:144 */       this.formula = new ArrayList();
/*  57:    */     }
/*  58:146 */     return this.formula;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getAccess()
/*  62:    */   {
/*  63:158 */     return this.access;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setAccess(String value)
/*  67:    */   {
/*  68:170 */     this.access = value;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getCascade()
/*  72:    */   {
/*  73:182 */     return this.cascade;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setCascade(String value)
/*  77:    */   {
/*  78:194 */     this.cascade = value;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getClazz()
/*  82:    */   {
/*  83:206 */     return this.clazz;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setClazz(String value)
/*  87:    */   {
/*  88:218 */     this.clazz = value;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isConstrained()
/*  92:    */   {
/*  93:230 */     if (this.constrained == null) {
/*  94:231 */       return false;
/*  95:    */     }
/*  96:233 */     return this.constrained.booleanValue();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setConstrained(Boolean value)
/* 100:    */   {
/* 101:246 */     this.constrained = value;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isEmbedXml()
/* 105:    */   {
/* 106:258 */     if (this.embedXml == null) {
/* 107:259 */       return true;
/* 108:    */     }
/* 109:261 */     return this.embedXml.booleanValue();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setEmbedXml(Boolean value)
/* 113:    */   {
/* 114:274 */     this.embedXml = value;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getEntityName()
/* 118:    */   {
/* 119:286 */     return this.entityName;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setEntityName(String value)
/* 123:    */   {
/* 124:298 */     this.entityName = value;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public JaxbFetchAttribute getFetch()
/* 128:    */   {
/* 129:310 */     return this.fetch;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setFetch(JaxbFetchAttribute value)
/* 133:    */   {
/* 134:322 */     this.fetch = value;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getForeignKey()
/* 138:    */   {
/* 139:334 */     return this.foreignKey;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setForeignKey(String value)
/* 143:    */   {
/* 144:346 */     this.foreignKey = value;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getFormulaAttribute()
/* 148:    */   {
/* 149:358 */     return this.formulaAttribute;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setFormulaAttribute(String value)
/* 153:    */   {
/* 154:370 */     this.formulaAttribute = value;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public JaxbLazyAttributeWithNoProxy getLazy()
/* 158:    */   {
/* 159:382 */     return this.lazy;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setLazy(JaxbLazyAttributeWithNoProxy value)
/* 163:    */   {
/* 164:394 */     this.lazy = value;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getName()
/* 168:    */   {
/* 169:406 */     return this.name;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setName(String value)
/* 173:    */   {
/* 174:418 */     this.name = value;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getNode()
/* 178:    */   {
/* 179:430 */     return this.node;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setNode(String value)
/* 183:    */   {
/* 184:442 */     this.node = value;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public JaxbOuterJoinAttribute getOuterJoin()
/* 188:    */   {
/* 189:454 */     return this.outerJoin;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setOuterJoin(JaxbOuterJoinAttribute value)
/* 193:    */   {
/* 194:466 */     this.outerJoin = value;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getPropertyRef()
/* 198:    */   {
/* 199:478 */     return this.propertyRef;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setPropertyRef(String value)
/* 203:    */   {
/* 204:490 */     this.propertyRef = value;
/* 205:    */   }
/* 206:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbOneToOneElement
 * JD-Core Version:    0.7.0.1
 */