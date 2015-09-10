/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="field-result")
/* 10:   */ public class JaxbFieldResult
/* 11:   */ {
/* 12:   */   @XmlAttribute(required=true)
/* 13:   */   protected String name;
/* 14:   */   @XmlAttribute(required=true)
/* 15:   */   protected String column;
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:59 */     return this.name;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setName(String value)
/* 23:   */   {
/* 24:71 */     this.name = value;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getColumn()
/* 28:   */   {
/* 29:83 */     return this.column;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setColumn(String value)
/* 33:   */   {
/* 34:95 */     this.column = value;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbFieldResult
 * JD-Core Version:    0.7.0.1
 */