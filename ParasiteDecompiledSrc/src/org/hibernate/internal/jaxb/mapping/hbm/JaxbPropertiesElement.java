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
/*  12:    */ @XmlType(name="properties-element", propOrder={"propertyOrManyToOneOrComponent"})
/*  13:    */ public class JaxbPropertiesElement
/*  14:    */ {
/*  15:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="component", type=JaxbComponentElement.class), @javax.xml.bind.annotation.XmlElement(name="many-to-one", type=JaxbManyToOneElement.class), @javax.xml.bind.annotation.XmlElement(name="dynamic-component", type=JaxbDynamicComponentElement.class), @javax.xml.bind.annotation.XmlElement(name="property", type=JaxbPropertyElement.class)})
/*  16:    */   protected List<Object> propertyOrManyToOneOrComponent;
/*  17:    */   @XmlAttribute
/*  18:    */   protected Boolean insert;
/*  19:    */   @XmlAttribute(required=true)
/*  20:    */   protected String name;
/*  21:    */   @XmlAttribute
/*  22:    */   protected String node;
/*  23:    */   @XmlAttribute(name="optimistic-lock")
/*  24:    */   protected Boolean optimisticLock;
/*  25:    */   @XmlAttribute
/*  26:    */   protected Boolean unique;
/*  27:    */   @XmlAttribute
/*  28:    */   protected Boolean update;
/*  29:    */   
/*  30:    */   public List<Object> getPropertyOrManyToOneOrComponent()
/*  31:    */   {
/*  32:103 */     if (this.propertyOrManyToOneOrComponent == null) {
/*  33:104 */       this.propertyOrManyToOneOrComponent = new ArrayList();
/*  34:    */     }
/*  35:106 */     return this.propertyOrManyToOneOrComponent;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isInsert()
/*  39:    */   {
/*  40:118 */     if (this.insert == null) {
/*  41:119 */       return true;
/*  42:    */     }
/*  43:121 */     return this.insert.booleanValue();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setInsert(Boolean value)
/*  47:    */   {
/*  48:134 */     this.insert = value;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getName()
/*  52:    */   {
/*  53:146 */     return this.name;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setName(String value)
/*  57:    */   {
/*  58:158 */     this.name = value;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getNode()
/*  62:    */   {
/*  63:170 */     return this.node;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setNode(String value)
/*  67:    */   {
/*  68:182 */     this.node = value;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isOptimisticLock()
/*  72:    */   {
/*  73:194 */     if (this.optimisticLock == null) {
/*  74:195 */       return true;
/*  75:    */     }
/*  76:197 */     return this.optimisticLock.booleanValue();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setOptimisticLock(Boolean value)
/*  80:    */   {
/*  81:210 */     this.optimisticLock = value;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isUnique()
/*  85:    */   {
/*  86:222 */     if (this.unique == null) {
/*  87:223 */       return false;
/*  88:    */     }
/*  89:225 */     return this.unique.booleanValue();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setUnique(Boolean value)
/*  93:    */   {
/*  94:238 */     this.unique = value;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isUpdate()
/*  98:    */   {
/*  99:250 */     if (this.update == null) {
/* 100:251 */       return true;
/* 101:    */     }
/* 102:253 */     return this.update.booleanValue();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setUpdate(Boolean value)
/* 106:    */   {
/* 107:266 */     this.update = value;
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbPropertiesElement
 * JD-Core Version:    0.7.0.1
 */