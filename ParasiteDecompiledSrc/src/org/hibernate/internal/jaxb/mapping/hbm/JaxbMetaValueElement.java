/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="meta-value-element")
/* 10:   */ public class JaxbMetaValueElement
/* 11:   */ {
/* 12:   */   @XmlAttribute(name="class", required=true)
/* 13:   */   protected String clazz;
/* 14:   */   @XmlAttribute(required=true)
/* 15:   */   protected String value;
/* 16:   */   
/* 17:   */   public String getClazz()
/* 18:   */   {
/* 19:53 */     return this.clazz;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setClazz(String value)
/* 23:   */   {
/* 24:65 */     this.clazz = value;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getValue()
/* 28:   */   {
/* 29:77 */     return this.value;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setValue(String value)
/* 33:   */   {
/* 34:89 */     this.value = value;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbMetaValueElement
 * JD-Core Version:    0.7.0.1
 */