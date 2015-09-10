/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="embeddable", propOrder={"description", "attributes"})
/*  10:    */ public class JaxbEmbeddable
/*  11:    */ {
/*  12:    */   protected String description;
/*  13:    */   protected JaxbEmbeddableAttributes attributes;
/*  14:    */   @XmlAttribute(name="class", required=true)
/*  15:    */   protected String clazz;
/*  16:    */   @XmlAttribute
/*  17:    */   protected JaxbAccessType access;
/*  18:    */   @XmlAttribute(name="metadata-complete")
/*  19:    */   protected Boolean metadataComplete;
/*  20:    */   
/*  21:    */   public String getDescription()
/*  22:    */   {
/*  23: 75 */     return this.description;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setDescription(String value)
/*  27:    */   {
/*  28: 87 */     this.description = value;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public JaxbEmbeddableAttributes getAttributes()
/*  32:    */   {
/*  33: 99 */     return this.attributes;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setAttributes(JaxbEmbeddableAttributes value)
/*  37:    */   {
/*  38:111 */     this.attributes = value;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getClazz()
/*  42:    */   {
/*  43:123 */     return this.clazz;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setClazz(String value)
/*  47:    */   {
/*  48:135 */     this.clazz = value;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public JaxbAccessType getAccess()
/*  52:    */   {
/*  53:147 */     return this.access;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setAccess(JaxbAccessType value)
/*  57:    */   {
/*  58:159 */     this.access = value;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Boolean isMetadataComplete()
/*  62:    */   {
/*  63:171 */     return this.metadataComplete;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setMetadataComplete(Boolean value)
/*  67:    */   {
/*  68:183 */     this.metadataComplete = value;
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddable
 * JD-Core Version:    0.7.0.1
 */