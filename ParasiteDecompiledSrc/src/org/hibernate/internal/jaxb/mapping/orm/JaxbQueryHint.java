/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="query-hint", propOrder={"description"})
/*  10:    */ public class JaxbQueryHint
/*  11:    */ {
/*  12:    */   protected String description;
/*  13:    */   @XmlAttribute(required=true)
/*  14:    */   protected String name;
/*  15:    */   @XmlAttribute(required=true)
/*  16:    */   protected String value;
/*  17:    */   
/*  18:    */   public String getDescription()
/*  19:    */   {
/*  20: 65 */     return this.description;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setDescription(String value)
/*  24:    */   {
/*  25: 77 */     this.description = value;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getName()
/*  29:    */   {
/*  30: 89 */     return this.name;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setName(String value)
/*  34:    */   {
/*  35:101 */     this.name = value;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getValue()
/*  39:    */   {
/*  40:113 */     return this.value;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setValue(String value)
/*  44:    */   {
/*  45:125 */     this.value = value;
/*  46:    */   }
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbQueryHint
 * JD-Core Version:    0.7.0.1
 */