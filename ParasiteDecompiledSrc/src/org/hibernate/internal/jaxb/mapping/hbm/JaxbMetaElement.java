/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ import javax.xml.bind.annotation.XmlValue;
/*   8:    */ 
/*   9:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  10:    */ @XmlType(name="meta-element", propOrder={"value"})
/*  11:    */ public class JaxbMetaElement
/*  12:    */ {
/*  13:    */   @XmlValue
/*  14:    */   protected String value;
/*  15:    */   @XmlAttribute(required=true)
/*  16:    */   protected String attribute;
/*  17:    */   @XmlAttribute
/*  18:    */   protected Boolean inherit;
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
/*  30:    */   public String getAttribute()
/*  31:    */   {
/*  32: 82 */     return this.attribute;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setAttribute(String value)
/*  36:    */   {
/*  37: 94 */     this.attribute = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isInherit()
/*  41:    */   {
/*  42:106 */     if (this.inherit == null) {
/*  43:107 */       return true;
/*  44:    */     }
/*  45:109 */     return this.inherit.booleanValue();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setInherit(Boolean value)
/*  49:    */   {
/*  50:122 */     this.inherit = value;
/*  51:    */   }
/*  52:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbMetaElement
 * JD-Core Version:    0.7.0.1
 */