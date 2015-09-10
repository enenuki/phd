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
/*  12:    */ @XmlType(name="embedded-id", propOrder={"attributeOverride"})
/*  13:    */ public class JaxbEmbeddedId
/*  14:    */ {
/*  15:    */   @XmlElement(name="attribute-override")
/*  16:    */   protected List<JaxbAttributeOverride> attributeOverride;
/*  17:    */   @XmlAttribute(required=true)
/*  18:    */   protected String name;
/*  19:    */   @XmlAttribute
/*  20:    */   protected JaxbAccessType access;
/*  21:    */   
/*  22:    */   public List<JaxbAttributeOverride> getAttributeOverride()
/*  23:    */   {
/*  24: 81 */     if (this.attributeOverride == null) {
/*  25: 82 */       this.attributeOverride = new ArrayList();
/*  26:    */     }
/*  27: 84 */     return this.attributeOverride;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getName()
/*  31:    */   {
/*  32: 96 */     return this.name;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setName(String value)
/*  36:    */   {
/*  37:108 */     this.name = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public JaxbAccessType getAccess()
/*  41:    */   {
/*  42:120 */     return this.access;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setAccess(JaxbAccessType value)
/*  46:    */   {
/*  47:132 */     this.access = value;
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddedId
 * JD-Core Version:    0.7.0.1
 */