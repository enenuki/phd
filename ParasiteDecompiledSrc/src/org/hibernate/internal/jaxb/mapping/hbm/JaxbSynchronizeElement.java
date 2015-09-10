/*  1:   */ package org.hibernate.internal.jaxb.mapping.hbm;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlAccessType;
/*  4:   */ import javax.xml.bind.annotation.XmlAccessorType;
/*  5:   */ import javax.xml.bind.annotation.XmlAttribute;
/*  6:   */ import javax.xml.bind.annotation.XmlType;
/*  7:   */ 
/*  8:   */ @XmlAccessorType(XmlAccessType.FIELD)
/*  9:   */ @XmlType(name="synchronize-element")
/* 10:   */ public class JaxbSynchronizeElement
/* 11:   */ {
/* 12:   */   @XmlAttribute(required=true)
/* 13:   */   protected String table;
/* 14:   */   
/* 15:   */   public String getTable()
/* 16:   */   {
/* 17:50 */     return this.table;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setTable(String value)
/* 21:   */   {
/* 22:62 */     this.table = value;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbSynchronizeElement
 * JD-Core Version:    0.7.0.1
 */