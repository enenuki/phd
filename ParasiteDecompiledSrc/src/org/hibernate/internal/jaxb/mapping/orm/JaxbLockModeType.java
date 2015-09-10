/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlType;
/*  5:   */ 
/*  6:   */ @XmlType(name="lock-mode-type")
/*  7:   */ @XmlEnum
/*  8:   */ public enum JaxbLockModeType
/*  9:   */ {
/* 10:40 */   READ,  WRITE,  OPTIMISTIC,  OPTIMISTIC_FORCE_INCREMENT,  PESSIMISTIC_READ,  PESSIMISTIC_WRITE,  PESSIMISTIC_FORCE_INCREMENT,  NONE;
/* 11:   */   
/* 12:   */   private JaxbLockModeType() {}
/* 13:   */   
/* 14:   */   public String value()
/* 15:   */   {
/* 16:50 */     return name();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static JaxbLockModeType fromValue(String v)
/* 20:   */   {
/* 21:54 */     return valueOf(v);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbLockModeType
 * JD-Core Version:    0.7.0.1
 */