/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="primary-key-join-column")
/*  10:    */ public class JaxbPrimaryKeyJoinColumn
/*  11:    */ {
/*  12:    */   @XmlAttribute
/*  13:    */   protected String name;
/*  14:    */   @XmlAttribute(name="referenced-column-name")
/*  15:    */   protected String referencedColumnName;
/*  16:    */   @XmlAttribute(name="column-definition")
/*  17:    */   protected String columnDefinition;
/*  18:    */   
/*  19:    */   public String getName()
/*  20:    */   {
/*  21: 63 */     return this.name;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setName(String value)
/*  25:    */   {
/*  26: 75 */     this.name = value;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getReferencedColumnName()
/*  30:    */   {
/*  31: 87 */     return this.referencedColumnName;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setReferencedColumnName(String value)
/*  35:    */   {
/*  36: 99 */     this.referencedColumnName = value;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getColumnDefinition()
/*  40:    */   {
/*  41:111 */     return this.columnDefinition;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setColumnDefinition(String value)
/*  45:    */   {
/*  46:123 */     this.columnDefinition = value;
/*  47:    */   }
/*  48:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbPrimaryKeyJoinColumn
 * JD-Core Version:    0.7.0.1
 */