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
/*  12:    */ @XmlType(name="load-collection-element", propOrder={"returnProperty"})
/*  13:    */ public class JaxbLoadCollectionElement
/*  14:    */ {
/*  15:    */   @XmlElement(name="return-property")
/*  16:    */   protected List<JaxbReturnPropertyElement> returnProperty;
/*  17:    */   @XmlAttribute(required=true)
/*  18:    */   protected String alias;
/*  19:    */   @XmlAttribute(name="lock-mode")
/*  20:    */   protected JaxbLockModeAttribute lockMode;
/*  21:    */   @XmlAttribute(required=true)
/*  22:    */   protected String role;
/*  23:    */   
/*  24:    */   public List<JaxbReturnPropertyElement> getReturnProperty()
/*  25:    */   {
/*  26: 80 */     if (this.returnProperty == null) {
/*  27: 81 */       this.returnProperty = new ArrayList();
/*  28:    */     }
/*  29: 83 */     return this.returnProperty;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getAlias()
/*  33:    */   {
/*  34: 95 */     return this.alias;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setAlias(String value)
/*  38:    */   {
/*  39:107 */     this.alias = value;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public JaxbLockModeAttribute getLockMode()
/*  43:    */   {
/*  44:119 */     if (this.lockMode == null) {
/*  45:120 */       return JaxbLockModeAttribute.READ;
/*  46:    */     }
/*  47:122 */     return this.lockMode;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setLockMode(JaxbLockModeAttribute value)
/*  51:    */   {
/*  52:135 */     this.lockMode = value;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getRole()
/*  56:    */   {
/*  57:147 */     return this.role;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setRole(String value)
/*  61:    */   {
/*  62:159 */     this.role = value;
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbLoadCollectionElement
 * JD-Core Version:    0.7.0.1
 */