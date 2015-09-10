/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="transient")
/* 10:   */ public class JaxbTransient
/* 11:   */ {
/* 12:   */   @XmlAttribute(required=true)
/* 13:   */   protected String name;
/* 14:   */   
/* 15:   */   public String getName()
/* 16:   */   {
/* 17:54 */     return this.name;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setName(String value)
/* 21:   */   {
/* 22:66 */     this.name = value;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbTransient
 * JD-Core Version:    0.7.0.1
 */