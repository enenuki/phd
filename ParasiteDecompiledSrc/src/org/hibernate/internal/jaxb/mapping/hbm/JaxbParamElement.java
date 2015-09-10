/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ import javax.xml.bind.annotation.XmlValue;
/*  8:   */ 
/*  9:   */ @XmlAccessorType(XmlAccessType.FIELD)
/* 10:   */ @XmlType(name="param-element", propOrder={"value"})
/* 11:   */ public class JaxbParamElement
/* 12:   */ {
/* 13:   */   @XmlValue
/* 14:   */   protected String value;
/* 15:   */   @XmlAttribute(required=true)
/* 16:   */   protected String name;
/* 17:   */   
/* 18:   */   public String getValue()
/* 19:   */   {
/* 20:55 */     return this.value;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setValue(String value)
/* 24:   */   {
/* 25:67 */     this.value = value;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getName()
/* 29:   */   {
/* 30:79 */     return this.name;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setName(String value)
/* 34:   */   {
/* 35:91 */     this.name = value;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbParamElement
 * JD-Core Version:    0.7.0.1
 */