/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ import javax.xml.bind.annotation.XmlValue;
/*   8:    */ 
/*   9:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  10:    */ @XmlType(name="filter-element", propOrder={"value"})
/*  11:    */ public class JaxbFilterElement
/*  12:    */ {
/*  13:    */   @XmlValue
/*  14:    */   protected String value;
/*  15:    */   @XmlAttribute
/*  16:    */   protected String condition;
/*  17:    */   @XmlAttribute(required=true)
/*  18:    */   protected String name;
/*  19:    */   
/*  20:    */   public String getValue()
/*  21:    */   {
/*  22: 58 */     return this.value;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setValue(String value)
/*  26:    */   {
/*  27: 70 */     this.value = value;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getCondition()
/*  31:    */   {
/*  32: 82 */     return this.condition;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setCondition(String value)
/*  36:    */   {
/*  37: 94 */     this.condition = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getName()
/*  41:    */   {
/*  42:106 */     return this.name;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setName(String value)
/*  46:    */   {
/*  47:118 */     this.name = value;
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbFilterElement
 * JD-Core Version:    0.7.0.1
 */