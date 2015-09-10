/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="inheritance")
/* 10:   */ public class JaxbInheritance
/* 11:   */ {
/* 12:   */   @XmlAttribute
/* 13:   */   protected JaxbInheritanceType strategy;
/* 14:   */   
/* 15:   */   public JaxbInheritanceType getStrategy()
/* 16:   */   {
/* 17:56 */     return this.strategy;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setStrategy(JaxbInheritanceType value)
/* 21:   */   {
/* 22:68 */     this.strategy = value;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbInheritance
 * JD-Core Version:    0.7.0.1
 */