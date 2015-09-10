/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlElement;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="persistence-unit-metadata", propOrder={"description", "xmlMappingMetadataComplete", "persistenceUnitDefaults"})
/*  10:    */ public class JaxbPersistenceUnitMetadata
/*  11:    */ {
/*  12:    */   protected String description;
/*  13:    */   @XmlElement(name="xml-mapping-metadata-complete")
/*  14:    */   protected JaxbEmptyType xmlMappingMetadataComplete;
/*  15:    */   @XmlElement(name="persistence-unit-defaults")
/*  16:    */   protected JaxbPersistenceUnitDefaults persistenceUnitDefaults;
/*  17:    */   
/*  18:    */   public String getDescription()
/*  19:    */   {
/*  20: 67 */     return this.description;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setDescription(String value)
/*  24:    */   {
/*  25: 79 */     this.description = value;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public JaxbEmptyType getXmlMappingMetadataComplete()
/*  29:    */   {
/*  30: 91 */     return this.xmlMappingMetadataComplete;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setXmlMappingMetadataComplete(JaxbEmptyType value)
/*  34:    */   {
/*  35:103 */     this.xmlMappingMetadataComplete = value;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public JaxbPersistenceUnitDefaults getPersistenceUnitDefaults()
/*  39:    */   {
/*  40:115 */     return this.persistenceUnitDefaults;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setPersistenceUnitDefaults(JaxbPersistenceUnitDefaults value)
/*  44:    */   {
/*  45:127 */     this.persistenceUnitDefaults = value;
/*  46:    */   }
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbPersistenceUnitMetadata
 * JD-Core Version:    0.7.0.1
 */