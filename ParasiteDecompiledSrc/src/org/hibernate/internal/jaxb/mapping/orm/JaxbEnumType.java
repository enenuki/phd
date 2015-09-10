/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlType;
/*  5:   */ 
/*  6:   */ @XmlType(name="enum-type")
/*  7:   */ @XmlEnum
/*  8:   */ public enum JaxbEnumType
/*  9:   */ {
/* 10:34 */   ORDINAL,  STRING;
/* 11:   */   
/* 12:   */   private JaxbEnumType() {}
/* 13:   */   
/* 14:   */   public String value()
/* 15:   */   {
/* 16:38 */     return name();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static JaxbEnumType fromValue(String v)
/* 20:   */   {
/* 21:42 */     return valueOf(v);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEnumType
 * JD-Core Version:    0.7.0.1
 */