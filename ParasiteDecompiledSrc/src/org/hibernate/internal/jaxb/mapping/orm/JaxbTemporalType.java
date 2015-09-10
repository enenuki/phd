/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlType;
/*  5:   */ 
/*  6:   */ @XmlType(name="temporal-type")
/*  7:   */ @XmlEnum
/*  8:   */ public enum JaxbTemporalType
/*  9:   */ {
/* 10:35 */   DATE,  TIME,  TIMESTAMP;
/* 11:   */   
/* 12:   */   private JaxbTemporalType() {}
/* 13:   */   
/* 14:   */   public String value()
/* 15:   */   {
/* 16:40 */     return name();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static JaxbTemporalType fromValue(String v)
/* 20:   */   {
/* 21:44 */     return valueOf(v);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbTemporalType
 * JD-Core Version:    0.7.0.1
 */