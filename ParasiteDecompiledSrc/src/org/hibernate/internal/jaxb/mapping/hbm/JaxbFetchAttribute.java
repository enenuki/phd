/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlEnum;
/*  4:   */ import javax.xml.bind.annotation.XmlEnumValue;
/*  5:   */ import javax.xml.bind.annotation.XmlType;
/*  6:   */ 
/*  7:   */ @XmlType(name="fetch-attribute")
/*  8:   */ @XmlEnum
/*  9:   */ public enum JaxbFetchAttribute
/* 10:   */ {
/* 11:35 */   JOIN("join"),  SELECT("select");
/* 12:   */   
/* 13:   */   private final String value;
/* 14:   */   
/* 15:   */   private JaxbFetchAttribute(String v)
/* 16:   */   {
/* 17:42 */     this.value = v;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String value()
/* 21:   */   {
/* 22:46 */     return this.value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static JaxbFetchAttribute fromValue(String v)
/* 26:   */   {
/* 27:50 */     for (JaxbFetchAttribute c : ) {
/* 28:51 */       if (c.value.equals(v)) {
/* 29:52 */         return c;
/* 30:   */       }
/* 31:   */     }
/* 32:55 */     throw new IllegalArgumentException(v);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchAttribute
 * JD-Core Version:    0.7.0.1
 */