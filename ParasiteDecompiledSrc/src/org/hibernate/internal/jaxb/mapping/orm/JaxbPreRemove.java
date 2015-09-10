/*  1:   */ package org.hibernate.internal.jaxb.mapping.orm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="pre-remove", propOrder={"description"})
/* 10:   */ public class JaxbPreRemove
/* 11:   */ {
/* 12:   */   protected String description;
/* 13:   */   @XmlAttribute(name="method-name", required=true)
/* 14:   */   protected String methodName;
/* 15:   */   
/* 16:   */   public String getDescription()
/* 17:   */   {
/* 18:60 */     return this.description;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setDescription(String value)
/* 22:   */   {
/* 23:72 */     this.description = value;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getMethodName()
/* 27:   */   {
/* 28:84 */     return this.methodName;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setMethodName(String value)
/* 32:   */   {
/* 33:96 */     this.methodName = value;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbPreRemove
 * JD-Core Version:    0.7.0.1
 */