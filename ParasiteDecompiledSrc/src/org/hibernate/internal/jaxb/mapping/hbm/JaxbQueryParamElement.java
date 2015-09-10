/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="query-param-element")
/* 10:   */ public class JaxbQueryParamElement
/* 11:   */ {
/* 12:   */   @XmlAttribute(required=true)
/* 13:   */   protected String name;
/* 14:   */   @XmlAttribute(required=true)
/* 15:   */   protected String type;
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:53 */     return this.name;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setName(String value)
/* 23:   */   {
/* 24:65 */     this.name = value;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getType()
/* 28:   */   {
/* 29:77 */     return this.type;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setType(String value)
/* 33:   */   {
/* 34:89 */     this.type = value;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbQueryParamElement
 * JD-Core Version:    0.7.0.1
 */