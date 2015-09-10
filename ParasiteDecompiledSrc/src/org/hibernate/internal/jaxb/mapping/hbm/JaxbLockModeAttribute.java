/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlEnumValue;
/*  5:   */ import javax.xml.bind.annotation.XmlType;
/*  6:   */ 
/*  7:   */ @XmlType(name="lock-mode-attribute")
/*  8:   */ @XmlEnum
/*  9:   */ public enum JaxbLockModeAttribute
/* 10:   */ {
/* 11:38 */   NONE("none"),  READ("read"),  UPGRADE("upgrade"),  UPGRADE_NOWAIT("upgrade-nowait"),  WRITE("write");
/* 12:   */   
/* 13:   */   private final String value;
/* 14:   */   
/* 15:   */   private JaxbLockModeAttribute(String v)
/* 16:   */   {
/* 17:51 */     this.value = v;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String value()
/* 21:   */   {
/* 22:55 */     return this.value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static JaxbLockModeAttribute fromValue(String v)
/* 26:   */   {
/* 27:59 */     for (JaxbLockModeAttribute c : ) {
/* 28:60 */       if (c.value.equals(v)) {
/* 29:61 */         return c;
/* 30:   */       }
/* 31:   */     }
/* 32:64 */     throw new IllegalArgumentException(v);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbLockModeAttribute
 * JD-Core Version:    0.7.0.1
 */