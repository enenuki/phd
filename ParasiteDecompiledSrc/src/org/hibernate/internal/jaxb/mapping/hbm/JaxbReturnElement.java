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
/*  12:    */ @XmlType(name="return-element", propOrder={"returnDiscriminatorAndReturnProperty"})
/*  13:    */ public class JaxbReturnElement
/*  14:    */ {
/*  15:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="return-property", type=JaxbReturnPropertyElement.class), @javax.xml.bind.annotation.XmlElement(name="return-discriminator", type=JaxbReturnDiscriminator.class)})
/*  16:    */   protected List<Object> returnDiscriminatorAndReturnProperty;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String alias;
/*  19:    */   @XmlAttribute(name="class")
/*  20:    */   protected String clazz;
/*  21:    */   @XmlAttribute(name="entity-name")
/*  22:    */   protected String entityName;
/*  23:    */   @XmlAttribute(name="lock-mode")
/*  24:    */   protected JaxbLockModeAttribute lockMode;
/*  25:    */   
/*  26:    */   public List<Object> getReturnDiscriminatorAndReturnProperty()
/*  27:    */   {
/*  28: 97 */     if (this.returnDiscriminatorAndReturnProperty == null) {
/*  29: 98 */       this.returnDiscriminatorAndReturnProperty = new ArrayList();
/*  30:    */     }
/*  31:100 */     return this.returnDiscriminatorAndReturnProperty;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getAlias()
/*  35:    */   {
/*  36:112 */     return this.alias;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setAlias(String value)
/*  40:    */   {
/*  41:124 */     this.alias = value;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getClazz()
/*  45:    */   {
/*  46:136 */     return this.clazz;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setClazz(String value)
/*  50:    */   {
/*  51:148 */     this.clazz = value;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getEntityName()
/*  55:    */   {
/*  56:160 */     return this.entityName;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setEntityName(String value)
/*  60:    */   {
/*  61:172 */     this.entityName = value;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public JaxbLockModeAttribute getLockMode()
/*  65:    */   {
/*  66:184 */     if (this.lockMode == null) {
/*  67:185 */       return JaxbLockModeAttribute.READ;
/*  68:    */     }
/*  69:187 */     return this.lockMode;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setLockMode(JaxbLockModeAttribute value)
/*  73:    */   {
/*  74:200 */     this.lockMode = value;
/*  75:    */   }
/*  76:    */   
/*  77:    */   @XmlAccessorType(XmlAccessType.FIELD)
/*  78:    */   @XmlType(name="")
/*  79:    */   public static class JaxbReturnDiscriminator
/*  80:    */   {
/*  81:    */     @XmlAttribute(required=true)
/*  82:    */     protected String column;
/*  83:    */     
/*  84:    */     public String getColumn()
/*  85:    */     {
/*  86:237 */       return this.column;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public void setColumn(String value)
/*  90:    */     {
/*  91:249 */       this.column = value;
/*  92:    */     }
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbReturnElement
 * JD-Core Version:    0.7.0.1
 */