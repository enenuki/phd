/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlElement;
/*   7:    */ import javax.xml.bind.annotation.XmlType;
/*   8:    */ 
/*   9:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  10:    */ @XmlType(name="attribute-override", propOrder={"description", "column"})
/*  11:    */ public class JaxbAttributeOverride
/*  12:    */ {
/*  13:    */   protected String description;
/*  14:    */   @XmlElement(required=true)
/*  15:    */   protected JaxbColumn column;
/*  16:    */   @XmlAttribute(required=true)
/*  17:    */   protected String name;
/*  18:    */   
/*  19:    */   public String getDescription()
/*  20:    */   {
/*  21: 67 */     return this.description;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setDescription(String value)
/*  25:    */   {
/*  26: 79 */     this.description = value;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JaxbColumn getColumn()
/*  30:    */   {
/*  31: 91 */     return this.column;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setColumn(JaxbColumn value)
/*  35:    */   {
/*  36:103 */     this.column = value;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getName()
/*  40:    */   {
/*  41:115 */     return this.name;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setName(String value)
/*  45:    */   {
/*  46:127 */     this.name = value;
/*  47:    */   }
/*  48:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbAttributeOverride
 * JD-Core Version:    0.7.0.1
 */