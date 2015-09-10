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
/*  12:    */ @XmlType(name="component-element", propOrder={"meta", "tuplizer", "parent", "propertyOrManyToOneOrOneToOne"})
/*  13:    */ public class JaxbComponentElement
/*  14:    */ {
/*  15:    */   protected List<JaxbMetaElement> meta;
/*  16:    */   protected List<JaxbTuplizerElement> tuplizer;
/*  17:    */   protected JaxbParentElement parent;
/*  18:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="bag", type=JaxbBagElement.class), @javax.xml.bind.annotation.XmlElement(name="list", type=JaxbListElement.class), @javax.xml.bind.annotation.XmlElement(name="any", type=JaxbAnyElement.class), @javax.xml.bind.annotation.XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @javax.xml.bind.annotation.XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class), @javax.xml.bind.annotation.XmlElement(name="array", type=JaxbArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="component", type=JaxbComponentElement.class), @javax.xml.bind.annotation.XmlElement(name="primitive-array", type=JaxbPrimitiveArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="property", type=JaxbPropertyElement.class), @javax.xml.bind.annotation.XmlElement(name="map", type=JaxbMapElement.class), @javax.xml.bind.annotation.XmlElement(name="one-to-one", type=JaxbOneToOneElement.class), @javax.xml.bind.annotation.XmlElement(name="set", type=JaxbSetElement.class)})
/*  19:    */   protected List<Object> propertyOrManyToOneOrOneToOne;
/*  20:    */   @XmlAttribute
/*  21:    */   protected String access;
/*  22:    */   @XmlAttribute(name="class")
/*  23:    */   protected String clazz;
/*  24:    */   @XmlAttribute
/*  25:    */   protected Boolean insert;
/*  26:    */   @XmlAttribute
/*  27:    */   protected Boolean lazy;
/*  28:    */   @XmlAttribute(required=true)
/*  29:    */   protected String name;
/*  30:    */   @XmlAttribute
/*  31:    */   protected String node;
/*  32:    */   @XmlAttribute(name="optimistic-lock")
/*  33:    */   protected Boolean optimisticLock;
/*  34:    */   @XmlAttribute
/*  35:    */   protected Boolean unique;
/*  36:    */   @XmlAttribute
/*  37:    */   protected Boolean update;
/*  38:    */   
/*  39:    */   public List<JaxbMetaElement> getMeta()
/*  40:    */   {
/*  41:134 */     if (this.meta == null) {
/*  42:135 */       this.meta = new ArrayList();
/*  43:    */     }
/*  44:137 */     return this.meta;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<JaxbTuplizerElement> getTuplizer()
/*  48:    */   {
/*  49:163 */     if (this.tuplizer == null) {
/*  50:164 */       this.tuplizer = new ArrayList();
/*  51:    */     }
/*  52:166 */     return this.tuplizer;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public JaxbParentElement getParent()
/*  56:    */   {
/*  57:178 */     return this.parent;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setParent(JaxbParentElement value)
/*  61:    */   {
/*  62:190 */     this.parent = value;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<Object> getPropertyOrManyToOneOrOneToOne()
/*  66:    */   {
/*  67:227 */     if (this.propertyOrManyToOneOrOneToOne == null) {
/*  68:228 */       this.propertyOrManyToOneOrOneToOne = new ArrayList();
/*  69:    */     }
/*  70:230 */     return this.propertyOrManyToOneOrOneToOne;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getAccess()
/*  74:    */   {
/*  75:242 */     return this.access;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setAccess(String value)
/*  79:    */   {
/*  80:254 */     this.access = value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getClazz()
/*  84:    */   {
/*  85:266 */     return this.clazz;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setClazz(String value)
/*  89:    */   {
/*  90:278 */     this.clazz = value;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isInsert()
/*  94:    */   {
/*  95:290 */     if (this.insert == null) {
/*  96:291 */       return true;
/*  97:    */     }
/*  98:293 */     return this.insert.booleanValue();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setInsert(Boolean value)
/* 102:    */   {
/* 103:306 */     this.insert = value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isLazy()
/* 107:    */   {
/* 108:318 */     if (this.lazy == null) {
/* 109:319 */       return false;
/* 110:    */     }
/* 111:321 */     return this.lazy.booleanValue();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setLazy(Boolean value)
/* 115:    */   {
/* 116:334 */     this.lazy = value;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getName()
/* 120:    */   {
/* 121:346 */     return this.name;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setName(String value)
/* 125:    */   {
/* 126:358 */     this.name = value;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getNode()
/* 130:    */   {
/* 131:370 */     return this.node;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setNode(String value)
/* 135:    */   {
/* 136:382 */     this.node = value;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isOptimisticLock()
/* 140:    */   {
/* 141:394 */     if (this.optimisticLock == null) {
/* 142:395 */       return true;
/* 143:    */     }
/* 144:397 */     return this.optimisticLock.booleanValue();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setOptimisticLock(Boolean value)
/* 148:    */   {
/* 149:410 */     this.optimisticLock = value;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isUnique()
/* 153:    */   {
/* 154:422 */     if (this.unique == null) {
/* 155:423 */       return false;
/* 156:    */     }
/* 157:425 */     return this.unique.booleanValue();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setUnique(Boolean value)
/* 161:    */   {
/* 162:438 */     this.unique = value;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean isUpdate()
/* 166:    */   {
/* 167:450 */     if (this.update == null) {
/* 168:451 */       return true;
/* 169:    */     }
/* 170:453 */     return this.update.booleanValue();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setUpdate(Boolean value)
/* 174:    */   {
/* 175:466 */     this.update = value;
/* 176:    */   }
/* 177:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbComponentElement
 * JD-Core Version:    0.7.0.1
 */