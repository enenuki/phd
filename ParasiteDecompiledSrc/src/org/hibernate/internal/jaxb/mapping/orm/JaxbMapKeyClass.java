/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="map-key-class")
/* 10:   */ public class JaxbMapKeyClass
/* 11:   */ {
/* 12:   */   @XmlAttribute(name="class", required=true)
/* 13:   */   protected String clazz;
/* 14:   */   
/* 15:   */   public String getClazz()
/* 16:   */   {
/* 17:56 */     return this.clazz;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setClazz(String value)
/* 21:   */   {
/* 22:68 */     this.clazz = value;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbMapKeyClass
 * JD-Core Version:    0.7.0.1
 */