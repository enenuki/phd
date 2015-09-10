/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlType;
/*  5:   */ 
/*  6:   */ @XmlType(name="generation-type")
/*  7:   */ @XmlEnum
/*  8:   */ public enum JaxbGenerationType
/*  9:   */ {
/* 10:36 */   TABLE,  SEQUENCE,  IDENTITY,  AUTO;
/* 11:   */   
/* 12:   */   private JaxbGenerationType() {}
/* 13:   */   
/* 14:   */   public String value()
/* 15:   */   {
/* 16:42 */     return name();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static JaxbGenerationType fromValue(String v)
/* 20:   */   {
/* 21:46 */     return valueOf(v);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbGenerationType
 * JD-Core Version:    0.7.0.1
 */