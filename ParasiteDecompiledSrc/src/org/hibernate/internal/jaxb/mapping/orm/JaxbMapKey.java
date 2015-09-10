/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="map-key")
/* 10:   */ public class JaxbMapKey
/* 11:   */ {
/* 12:   */   @XmlAttribute
/* 13:   */   protected String name;
/* 14:   */   
/* 15:   */   public String getName()
/* 16:   */   {
/* 17:56 */     return this.name;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setName(String value)
/* 21:   */   {
/* 22:68 */     this.name = value;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbMapKey
 * JD-Core Version:    0.7.0.1
 */