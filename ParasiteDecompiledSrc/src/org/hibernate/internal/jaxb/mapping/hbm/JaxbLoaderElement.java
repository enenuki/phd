/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="loader-element")
/* 10:   */ public class JaxbLoaderElement
/* 11:   */ {
/* 12:   */   @XmlAttribute(name="query-ref", required=true)
/* 13:   */   protected String queryRef;
/* 14:   */   
/* 15:   */   public String getQueryRef()
/* 16:   */   {
/* 17:50 */     return this.queryRef;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setQueryRef(String value)
/* 21:   */   {
/* 22:62 */     this.queryRef = value;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbLoaderElement
 * JD-Core Version:    0.7.0.1
 */