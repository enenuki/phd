/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
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
/*  12:    */ @XmlType(name="any-element", propOrder={"meta", "metaValue", "column"})
/*  13:    */ public class JaxbAnyElement
/*  14:    */ {
/*  15:    */   protected List<JaxbMetaElement> meta;
/*  16:    */   @XmlElement(name="meta-value")
/*  17:    */   protected List<JaxbMetaValueElement> metaValue;
/*  18:    */   @XmlElement(required=true)
/*  19:    */   protected JaxbColumnElement column;
/*  20:    */   @XmlAttribute
/*  21:    */   protected String access;
/*  22:    */   @XmlAttribute
/*  23:    */   protected String cascade;
/*  24:    */   @XmlAttribute(name="id-type", required=true)
/*  25:    */   protected String idType;
/*  26:    */   @XmlAttribute
/*  27:    */   protected String index;
/*  28:    */   @XmlAttribute
/*  29:    */   protected Boolean insert;
/*  30:    */   @XmlAttribute
/*  31:    */   protected Boolean lazy;
/*  32:    */   @XmlAttribute(name="meta-type")
/*  33:    */   protected String metaType;
/*  34:    */   @XmlAttribute(required=true)
/*  35:    */   protected String name;
/*  36:    */   @XmlAttribute
/*  37:    */   protected String node;
/*  38:    */   @XmlAttribute(name="optimistic-lock")
/*  39:    */   protected Boolean optimisticLock;
/*  40:    */   @XmlAttribute
/*  41:    */   protected Boolean update;
/*  42:    */   
/*  43:    */   public List<JaxbMetaElement> getMeta()
/*  44:    */   {
/*  45:111 */     if (this.meta == null) {
/*  46:112 */       this.meta = new ArrayList();
/*  47:    */     }
/*  48:114 */     return this.meta;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<JaxbMetaValueElement> getMetaValue()
/*  52:    */   {
/*  53:140 */     if (this.metaValue == null) {
/*  54:141 */       this.metaValue = new ArrayList();
/*  55:    */     }
/*  56:143 */     return this.metaValue;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public JaxbColumnElement getColumn()
/*  60:    */   {
/*  61:155 */     return this.column;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setColumn(JaxbColumnElement value)
/*  65:    */   {
/*  66:167 */     this.column = value;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getAccess()
/*  70:    */   {
/*  71:179 */     return this.access;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setAccess(String value)
/*  75:    */   {
/*  76:191 */     this.access = value;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getCascade()
/*  80:    */   {
/*  81:203 */     return this.cascade;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setCascade(String value)
/*  85:    */   {
/*  86:215 */     this.cascade = value;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getIdType()
/*  90:    */   {
/*  91:227 */     return this.idType;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdType(String value)
/*  95:    */   {
/*  96:239 */     this.idType = value;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getIndex()
/* 100:    */   {
/* 101:251 */     return this.index;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIndex(String value)
/* 105:    */   {
/* 106:263 */     this.index = value;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isInsert()
/* 110:    */   {
/* 111:275 */     if (this.insert == null) {
/* 112:276 */       return true;
/* 113:    */     }
/* 114:278 */     return this.insert.booleanValue();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setInsert(Boolean value)
/* 118:    */   {
/* 119:291 */     this.insert = value;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isLazy()
/* 123:    */   {
/* 124:303 */     if (this.lazy == null) {
/* 125:304 */       return false;
/* 126:    */     }
/* 127:306 */     return this.lazy.booleanValue();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setLazy(Boolean value)
/* 131:    */   {
/* 132:319 */     this.lazy = value;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getMetaType()
/* 136:    */   {
/* 137:331 */     return this.metaType;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setMetaType(String value)
/* 141:    */   {
/* 142:343 */     this.metaType = value;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getName()
/* 146:    */   {
/* 147:355 */     return this.name;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setName(String value)
/* 151:    */   {
/* 152:367 */     this.name = value;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getNode()
/* 156:    */   {
/* 157:379 */     return this.node;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setNode(String value)
/* 161:    */   {
/* 162:391 */     this.node = value;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean isOptimisticLock()
/* 166:    */   {
/* 167:403 */     if (this.optimisticLock == null) {
/* 168:404 */       return true;
/* 169:    */     }
/* 170:406 */     return this.optimisticLock.booleanValue();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setOptimisticLock(Boolean value)
/* 174:    */   {
/* 175:419 */     this.optimisticLock = value;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isUpdate()
/* 179:    */   {
/* 180:431 */     if (this.update == null) {
/* 181:432 */       return true;
/* 182:    */     }
/* 183:434 */     return this.update.booleanValue();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setUpdate(Boolean value)
/* 187:    */   {
/* 188:447 */     this.update = value;
/* 189:    */   }
/* 190:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbAnyElement
 * JD-Core Version:    0.7.0.1
 */