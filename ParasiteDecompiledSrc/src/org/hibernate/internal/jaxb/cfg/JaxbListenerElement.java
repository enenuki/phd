/*  1:   */ package org.hibernate.internal.jaxb.cfg;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="listener-element")
/* 10:   */ public class JaxbListenerElement
/* 11:   */ {
/* 12:   */   @XmlAttribute(name="class", required=true)
/* 13:   */   protected String clazz;
/* 14:   */   @XmlAttribute
/* 15:   */   protected JaxbTypeAttribute type;
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
/* 27:   */   public JaxbTypeAttribute getType()
/* 28:   */   {
/* 29:77 */     return this.type;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setType(JaxbTypeAttribute value)
/* 33:   */   {
/* 34:89 */     this.type = value;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.cfg.JaxbListenerElement
 * JD-Core Version:    0.7.0.1
 */