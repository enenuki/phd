/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="one-to-many-element")
/*  10:    */ public class JaxbOneToManyElement
/*  11:    */ {
/*  12:    */   @XmlAttribute(name="class")
/*  13:    */   protected String clazz;
/*  14:    */   @XmlAttribute(name="embed-xml")
/*  15:    */   protected Boolean embedXml;
/*  16:    */   @XmlAttribute(name="entity-name")
/*  17:    */   protected String entityName;
/*  18:    */   @XmlAttribute
/*  19:    */   protected String node;
/*  20:    */   @XmlAttribute(name="not-found")
/*  21:    */   protected JaxbNotFoundAttribute notFound;
/*  22:    */   
/*  23:    */   public String getClazz()
/*  24:    */   {
/*  25: 62 */     return this.clazz;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setClazz(String value)
/*  29:    */   {
/*  30: 74 */     this.clazz = value;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean isEmbedXml()
/*  34:    */   {
/*  35: 86 */     if (this.embedXml == null) {
/*  36: 87 */       return true;
/*  37:    */     }
/*  38: 89 */     return this.embedXml.booleanValue();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setEmbedXml(Boolean value)
/*  42:    */   {
/*  43:102 */     this.embedXml = value;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getEntityName()
/*  47:    */   {
/*  48:114 */     return this.entityName;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setEntityName(String value)
/*  52:    */   {
/*  53:126 */     this.entityName = value;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getNode()
/*  57:    */   {
/*  58:138 */     return this.node;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setNode(String value)
/*  62:    */   {
/*  63:150 */     this.node = value;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public JaxbNotFoundAttribute getNotFound()
/*  67:    */   {
/*  68:162 */     if (this.notFound == null) {
/*  69:163 */       return JaxbNotFoundAttribute.EXCEPTION;
/*  70:    */     }
/*  71:165 */     return this.notFound;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setNotFound(JaxbNotFoundAttribute value)
/*  75:    */   {
/*  76:178 */     this.notFound = value;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbOneToManyElement
 * JD-Core Version:    0.7.0.1
 */