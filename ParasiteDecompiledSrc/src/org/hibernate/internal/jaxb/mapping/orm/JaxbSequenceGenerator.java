/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="sequence-generator", propOrder={"description"})
/*  10:    */ public class JaxbSequenceGenerator
/*  11:    */ {
/*  12:    */   protected String description;
/*  13:    */   @XmlAttribute(required=true)
/*  14:    */   protected String name;
/*  15:    */   @XmlAttribute(name="sequence-name")
/*  16:    */   protected String sequenceName;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String catalog;
/*  19:    */   @XmlAttribute
/*  20:    */   protected String schema;
/*  21:    */   @XmlAttribute(name="initial-value")
/*  22:    */   protected Integer initialValue;
/*  23:    */   @XmlAttribute(name="allocation-size")
/*  24:    */   protected Integer allocationSize;
/*  25:    */   
/*  26:    */   public String getDescription()
/*  27:    */   {
/*  28: 79 */     return this.description;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setDescription(String value)
/*  32:    */   {
/*  33: 91 */     this.description = value;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getName()
/*  37:    */   {
/*  38:103 */     return this.name;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setName(String value)
/*  42:    */   {
/*  43:115 */     this.name = value;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getSequenceName()
/*  47:    */   {
/*  48:127 */     return this.sequenceName;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setSequenceName(String value)
/*  52:    */   {
/*  53:139 */     this.sequenceName = value;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getCatalog()
/*  57:    */   {
/*  58:151 */     return this.catalog;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setCatalog(String value)
/*  62:    */   {
/*  63:163 */     this.catalog = value;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getSchema()
/*  67:    */   {
/*  68:175 */     return this.schema;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setSchema(String value)
/*  72:    */   {
/*  73:187 */     this.schema = value;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Integer getInitialValue()
/*  77:    */   {
/*  78:199 */     return this.initialValue;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setInitialValue(Integer value)
/*  82:    */   {
/*  83:211 */     this.initialValue = value;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Integer getAllocationSize()
/*  87:    */   {
/*  88:223 */     return this.allocationSize;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setAllocationSize(Integer value)
/*  92:    */   {
/*  93:235 */     this.allocationSize = value;
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbSequenceGenerator
 * JD-Core Version:    0.7.0.1
 */