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
/*  12:    */ @XmlType(name="embedded", propOrder={"attributeOverride", "associationOverride"})
/*  13:    */ public class JaxbEmbedded
/*  14:    */ {
/*  15:    */   @XmlElement(name="attribute-override")
/*  16:    */   protected List<JaxbAttributeOverride> attributeOverride;
/*  17:    */   @XmlElement(name="association-override")
/*  18:    */   protected List<JaxbAssociationOverride> associationOverride;
/*  19:    */   @XmlAttribute(required=true)
/*  20:    */   protected String name;
/*  21:    */   @XmlAttribute
/*  22:    */   protected JaxbAccessType access;
/*  23:    */   
/*  24:    */   public List<JaxbAttributeOverride> getAttributeOverride()
/*  25:    */   {
/*  26: 85 */     if (this.attributeOverride == null) {
/*  27: 86 */       this.attributeOverride = new ArrayList();
/*  28:    */     }
/*  29: 88 */     return this.attributeOverride;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<JaxbAssociationOverride> getAssociationOverride()
/*  33:    */   {
/*  34:114 */     if (this.associationOverride == null) {
/*  35:115 */       this.associationOverride = new ArrayList();
/*  36:    */     }
/*  37:117 */     return this.associationOverride;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getName()
/*  41:    */   {
/*  42:129 */     return this.name;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setName(String value)
/*  46:    */   {
/*  47:141 */     this.name = value;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public JaxbAccessType getAccess()
/*  51:    */   {
/*  52:153 */     return this.access;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setAccess(JaxbAccessType value)
/*  56:    */   {
/*  57:165 */     this.access = value;
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEmbedded
 * JD-Core Version:    0.7.0.1
 */