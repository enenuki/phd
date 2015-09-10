/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="discriminator-column")
/*  10:    */ public class JaxbDiscriminatorColumn
/*  11:    */ {
/*  12:    */   @XmlAttribute
/*  13:    */   protected String name;
/*  14:    */   @XmlAttribute(name="discriminator-type")
/*  15:    */   protected JaxbDiscriminatorType discriminatorType;
/*  16:    */   @XmlAttribute(name="column-definition")
/*  17:    */   protected String columnDefinition;
/*  18:    */   @XmlAttribute
/*  19:    */   protected Integer length;
/*  20:    */   
/*  21:    */   public String getName()
/*  22:    */   {
/*  23: 67 */     return this.name;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setName(String value)
/*  27:    */   {
/*  28: 79 */     this.name = value;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public JaxbDiscriminatorType getDiscriminatorType()
/*  32:    */   {
/*  33: 91 */     return this.discriminatorType;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setDiscriminatorType(JaxbDiscriminatorType value)
/*  37:    */   {
/*  38:103 */     this.discriminatorType = value;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getColumnDefinition()
/*  42:    */   {
/*  43:115 */     return this.columnDefinition;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setColumnDefinition(String value)
/*  47:    */   {
/*  48:127 */     this.columnDefinition = value;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Integer getLength()
/*  52:    */   {
/*  53:139 */     return this.length;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setLength(Integer value)
/*  57:    */   {
/*  58:151 */     this.length = value;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbDiscriminatorColumn
 * JD-Core Version:    0.7.0.1
 */