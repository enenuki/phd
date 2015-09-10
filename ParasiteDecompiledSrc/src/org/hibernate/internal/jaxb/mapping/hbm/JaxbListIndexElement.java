/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="list-index-element", propOrder={"column"})
/*  10:    */ public class JaxbListIndexElement
/*  11:    */ {
/*  12:    */   protected JaxbColumnElement column;
/*  13:    */   @XmlAttribute
/*  14:    */   protected String base;
/*  15:    */   @XmlAttribute(name="column")
/*  16:    */   protected String columnAttribute;
/*  17:    */   
/*  18:    */   public JaxbColumnElement getColumn()
/*  19:    */   {
/*  20: 59 */     return this.column;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setColumn(JaxbColumnElement value)
/*  24:    */   {
/*  25: 71 */     this.column = value;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getBase()
/*  29:    */   {
/*  30: 83 */     if (this.base == null) {
/*  31: 84 */       return "0";
/*  32:    */     }
/*  33: 86 */     return this.base;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setBase(String value)
/*  37:    */   {
/*  38: 99 */     this.base = value;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getColumnAttribute()
/*  42:    */   {
/*  43:111 */     return this.columnAttribute;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setColumnAttribute(String value)
/*  47:    */   {
/*  48:123 */     this.columnAttribute = value;
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbListIndexElement
 * JD-Core Version:    0.7.0.1
 */