/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlEnumValue;
/*  5:   */ import javax.xml.bind.annotation.XmlType;
/*  6:   */ 
/*  7:   */ @XmlType(name="outer-join-attribute")
/*  8:   */ @XmlEnum
/*  9:   */ public enum JaxbOuterJoinAttribute
/* 10:   */ {
/* 11:36 */   AUTO("auto"),  FALSE("false"),  TRUE("true");
/* 12:   */   
/* 13:   */   private final String value;
/* 14:   */   
/* 15:   */   private JaxbOuterJoinAttribute(String v)
/* 16:   */   {
/* 17:45 */     this.value = v;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String value()
/* 21:   */   {
/* 22:49 */     return this.value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static JaxbOuterJoinAttribute fromValue(String v)
/* 26:   */   {
/* 27:53 */     for (JaxbOuterJoinAttribute c : ) {
/* 28:54 */       if (c.value.equals(v)) {
/* 29:55 */         return c;
/* 30:   */       }
/* 31:   */     }
/* 32:58 */     throw new IllegalArgumentException(v);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbOuterJoinAttribute
 * JD-Core Version:    0.7.0.1
 */