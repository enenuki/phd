/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlEnumValue;
/*  5:   */ import javax.xml.bind.annotation.XmlType;
/*  6:   */ 
/*  7:   */ @XmlType(name="cache-mode-attribute")
/*  8:   */ @XmlEnum
/*  9:   */ public enum JaxbCacheModeAttribute
/* 10:   */ {
/* 11:38 */   GET("get"),  IGNORE("ignore"),  NORMAL("normal"),  PUT("put"),  REFRESH("refresh");
/* 12:   */   
/* 13:   */   private final String value;
/* 14:   */   
/* 15:   */   private JaxbCacheModeAttribute(String v)
/* 16:   */   {
/* 17:51 */     this.value = v;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String value()
/* 21:   */   {
/* 22:55 */     return this.value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static JaxbCacheModeAttribute fromValue(String v)
/* 26:   */   {
/* 27:59 */     for (JaxbCacheModeAttribute c : ) {
/* 28:60 */       if (c.value.equals(v)) {
/* 29:61 */         return c;
/* 30:   */       }
/* 31:   */     }
/* 32:64 */     throw new IllegalArgumentException(v);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbCacheModeAttribute
 * JD-Core Version:    0.7.0.1
 */