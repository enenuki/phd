/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlElement;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="persistence-unit-defaults", propOrder={"description", "schema", "catalog", "delimitedIdentifiers", "access", "cascadePersist", "entityListeners"})
/*  10:    */ public class JaxbPersistenceUnitDefaults
/*  11:    */ {
/*  12:    */   protected String description;
/*  13:    */   protected String schema;
/*  14:    */   protected String catalog;
/*  15:    */   @XmlElement(name="delimited-identifiers")
/*  16:    */   protected JaxbEmptyType delimitedIdentifiers;
/*  17:    */   protected JaxbAccessType access;
/*  18:    */   @XmlElement(name="cascade-persist")
/*  19:    */   protected JaxbEmptyType cascadePersist;
/*  20:    */   @XmlElement(name="entity-listeners")
/*  21:    */   protected JaxbEntityListeners entityListeners;
/*  22:    */   
/*  23:    */   public String getDescription()
/*  24:    */   {
/*  25: 89 */     return this.description;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setDescription(String value)
/*  29:    */   {
/*  30:101 */     this.description = value;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getSchema()
/*  34:    */   {
/*  35:113 */     return this.schema;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setSchema(String value)
/*  39:    */   {
/*  40:125 */     this.schema = value;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getCatalog()
/*  44:    */   {
/*  45:137 */     return this.catalog;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setCatalog(String value)
/*  49:    */   {
/*  50:149 */     this.catalog = value;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public JaxbEmptyType getDelimitedIdentifiers()
/*  54:    */   {
/*  55:161 */     return this.delimitedIdentifiers;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setDelimitedIdentifiers(JaxbEmptyType value)
/*  59:    */   {
/*  60:173 */     this.delimitedIdentifiers = value;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public JaxbAccessType getAccess()
/*  64:    */   {
/*  65:185 */     return this.access;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setAccess(JaxbAccessType value)
/*  69:    */   {
/*  70:197 */     this.access = value;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public JaxbEmptyType getCascadePersist()
/*  74:    */   {
/*  75:209 */     return this.cascadePersist;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setCascadePersist(JaxbEmptyType value)
/*  79:    */   {
/*  80:221 */     this.cascadePersist = value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public JaxbEntityListeners getEntityListeners()
/*  84:    */   {
/*  85:233 */     return this.entityListeners;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setEntityListeners(JaxbEntityListeners value)
/*  89:    */   {
/*  90:245 */     this.entityListeners = value;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbPersistenceUnitDefaults
 * JD-Core Version:    0.7.0.1
 */