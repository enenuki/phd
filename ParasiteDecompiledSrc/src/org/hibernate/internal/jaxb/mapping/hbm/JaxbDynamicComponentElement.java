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
/*  12:    */ @XmlType(name="dynamic-component-element", propOrder={"propertyOrManyToOneOrOneToOne"})
/*  13:    */ public class JaxbDynamicComponentElement
/*  14:    */ {
/*  15:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="array", type=JaxbArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="component", type=JaxbComponentElement.class), @javax.xml.bind.annotation.XmlElement(name="primitive-array", type=JaxbPrimitiveArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class), @javax.xml.bind.annotation.XmlElement(name="map", type=JaxbMapElement.class), @javax.xml.bind.annotation.XmlElement(name="set", type=JaxbSetElement.class), @javax.xml.bind.annotation.XmlElement(name="one-to-one", type=JaxbOneToOneElement.class), @javax.xml.bind.annotation.XmlElement(name="list", type=JaxbListElement.class), @javax.xml.bind.annotation.XmlElement(name="any", type=JaxbAnyElement.class), @javax.xml.bind.annotation.XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @javax.xml.bind.annotation.XmlElement(name="bag", type=JaxbBagElement.class), @javax.xml.bind.annotation.XmlElement(name="property", type=JaxbPropertyElement.class)})
/*  16:    */   protected List<Object> propertyOrManyToOneOrOneToOne;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String access;
/*  19:    */   @XmlAttribute
/*  20:    */   protected Boolean insert;
/*  21:    */   @XmlAttribute(required=true)
/*  22:    */   protected String name;
/*  23:    */   @XmlAttribute
/*  24:    */   protected String node;
/*  25:    */   @XmlAttribute(name="optimistic-lock")
/*  26:    */   protected Boolean optimisticLock;
/*  27:    */   @XmlAttribute
/*  28:    */   protected Boolean unique;
/*  29:    */   @XmlAttribute
/*  30:    */   protected Boolean update;
/*  31:    */   
/*  32:    */   public List<Object> getPropertyOrManyToOneOrOneToOne()
/*  33:    */   {
/*  34:130 */     if (this.propertyOrManyToOneOrOneToOne == null) {
/*  35:131 */       this.propertyOrManyToOneOrOneToOne = new ArrayList();
/*  36:    */     }
/*  37:133 */     return this.propertyOrManyToOneOrOneToOne;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getAccess()
/*  41:    */   {
/*  42:145 */     return this.access;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setAccess(String value)
/*  46:    */   {
/*  47:157 */     this.access = value;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isInsert()
/*  51:    */   {
/*  52:169 */     if (this.insert == null) {
/*  53:170 */       return true;
/*  54:    */     }
/*  55:172 */     return this.insert.booleanValue();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setInsert(Boolean value)
/*  59:    */   {
/*  60:185 */     this.insert = value;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getName()
/*  64:    */   {
/*  65:197 */     return this.name;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setName(String value)
/*  69:    */   {
/*  70:209 */     this.name = value;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getNode()
/*  74:    */   {
/*  75:221 */     return this.node;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setNode(String value)
/*  79:    */   {
/*  80:233 */     this.node = value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isOptimisticLock()
/*  84:    */   {
/*  85:245 */     if (this.optimisticLock == null) {
/*  86:246 */       return true;
/*  87:    */     }
/*  88:248 */     return this.optimisticLock.booleanValue();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setOptimisticLock(Boolean value)
/*  92:    */   {
/*  93:261 */     this.optimisticLock = value;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isUnique()
/*  97:    */   {
/*  98:273 */     if (this.unique == null) {
/*  99:274 */       return false;
/* 100:    */     }
/* 101:276 */     return this.unique.booleanValue();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setUnique(Boolean value)
/* 105:    */   {
/* 106:289 */     this.unique = value;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isUpdate()
/* 110:    */   {
/* 111:301 */     if (this.update == null) {
/* 112:302 */       return true;
/* 113:    */     }
/* 114:304 */     return this.update.booleanValue();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setUpdate(Boolean value)
/* 118:    */   {
/* 119:317 */     this.update = value;
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbDynamicComponentElement
 * JD-Core Version:    0.7.0.1
 */