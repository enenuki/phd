/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlType;
/*  5:   */ 
/*  6:   */ @XmlType(name="inheritance-type")
/*  7:   */ @XmlEnum
/*  8:   */ public enum JaxbInheritanceType
/*  9:   */ {
/* 10:35 */   SINGLE_TABLE,  JOINED,  TABLE_PER_CLASS;
/* 11:   */   
/* 12:   */   private JaxbInheritanceType() {}
/* 13:   */   
/* 14:   */   public String value()
/* 15:   */   {
/* 16:40 */     return name();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static JaxbInheritanceType fromValue(String v)
/* 20:   */   {
/* 21:44 */     return valueOf(v);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbInheritanceType
 * JD-Core Version:    0.7.0.1
 */