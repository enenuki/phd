/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="generated-value")
/* 10:   */ public class JaxbGeneratedValue
/* 11:   */ {
/* 12:   */   @XmlAttribute
/* 13:   */   protected JaxbGenerationType strategy;
/* 14:   */   @XmlAttribute
/* 15:   */   protected String generator;
/* 16:   */   
/* 17:   */   public JaxbGenerationType getStrategy()
/* 18:   */   {
/* 19:59 */     return this.strategy;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setStrategy(JaxbGenerationType value)
/* 23:   */   {
/* 24:71 */     this.strategy = value;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getGenerator()
/* 28:   */   {
/* 29:83 */     return this.generator;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setGenerator(String value)
/* 33:   */   {
/* 34:95 */     this.generator = value;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbGeneratedValue
 * JD-Core Version:    0.7.0.1
 */