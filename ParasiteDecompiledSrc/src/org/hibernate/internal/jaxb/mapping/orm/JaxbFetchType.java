/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlType;
/*  5:   */ 
/*  6:   */ @XmlType(name="fetch-type")
/*  7:   */ @XmlEnum
/*  8:   */ public enum JaxbFetchType
/*  9:   */ {
/* 10:34 */   LAZY,  EAGER;
/* 11:   */   
/* 12:   */   private JaxbFetchType() {}
/* 13:   */   
/* 14:   */   public String value()
/* 15:   */   {
/* 16:38 */     return name();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static JaxbFetchType fromValue(String v)
/* 20:   */   {
/* 21:42 */     return valueOf(v);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbFetchType
 * JD-Core Version:    0.7.0.1
 */