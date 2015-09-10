/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="return-scalar-element")
/* 10:   */ public class JaxbReturnScalarElement
/* 11:   */ {
/* 12:   */   @XmlAttribute(required=true)
/* 13:   */   protected String column;
/* 14:   */   @XmlAttribute
/* 15:   */   protected String type;
/* 16:   */   
/* 17:   */   public String getColumn()
/* 18:   */   {
/* 19:53 */     return this.column;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setColumn(String value)
/* 23:   */   {
/* 24:65 */     this.column = value;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getType()
/* 28:   */   {
/* 29:77 */     return this.type;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setType(String value)
/* 33:   */   {
/* 34:89 */     this.type = value;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbReturnScalarElement
 * JD-Core Version:    0.7.0.1
 */