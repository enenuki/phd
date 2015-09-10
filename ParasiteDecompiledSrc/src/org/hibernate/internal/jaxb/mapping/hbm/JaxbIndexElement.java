/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlType;
/*   9:    */ 
/*  10:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  11:    */ @XmlType(name="index-element", propOrder={"column"})
/*  12:    */ public class JaxbIndexElement
/*  13:    */ {
/*  14:    */   protected List<JaxbColumnElement> column;
/*  15:    */   @XmlAttribute(name="column")
/*  16:    */   protected String columnAttribute;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String length;
/*  19:    */   @XmlAttribute
/*  20:    */   protected String type;
/*  21:    */   
/*  22:    */   public List<JaxbColumnElement> getColumn()
/*  23:    */   {
/*  24: 78 */     if (this.column == null) {
/*  25: 79 */       this.column = new ArrayList();
/*  26:    */     }
/*  27: 81 */     return this.column;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getColumnAttribute()
/*  31:    */   {
/*  32: 93 */     return this.columnAttribute;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setColumnAttribute(String value)
/*  36:    */   {
/*  37:105 */     this.columnAttribute = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getLength()
/*  41:    */   {
/*  42:117 */     return this.length;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setLength(String value)
/*  46:    */   {
/*  47:129 */     this.length = value;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getType()
/*  51:    */   {
/*  52:141 */     return this.type;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setType(String value)
/*  56:    */   {
/*  57:153 */     this.type = value;
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbIndexElement
 * JD-Core Version:    0.7.0.1
 */