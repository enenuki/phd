/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ import javax.xml.bind.annotation.XmlValue;
/*   8:    */ 
/*   9:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  10:    */ @XmlType(name="sql-update-element", propOrder={"value"})
/*  11:    */ public class JaxbSqlUpdateElement
/*  12:    */   implements CustomSqlElement
/*  13:    */ {
/*  14:    */   @XmlValue
/*  15:    */   protected String value;
/*  16:    */   @XmlAttribute
/*  17:    */   protected Boolean callable;
/*  18:    */   @XmlAttribute
/*  19:    */   protected JaxbCheckAttribute check;
/*  20:    */   
/*  21:    */   public String getValue()
/*  22:    */   {
/*  23: 60 */     return this.value;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setValue(String value)
/*  27:    */   {
/*  28: 72 */     this.value = value;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean isCallable()
/*  32:    */   {
/*  33: 84 */     if (this.callable == null) {
/*  34: 85 */       return false;
/*  35:    */     }
/*  36: 87 */     return this.callable.booleanValue();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setCallable(Boolean value)
/*  40:    */   {
/*  41:100 */     this.callable = value;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public JaxbCheckAttribute getCheck()
/*  45:    */   {
/*  46:112 */     return this.check;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setCheck(JaxbCheckAttribute value)
/*  50:    */   {
/*  51:124 */     this.check = value;
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbSqlUpdateElement
 * JD-Core Version:    0.7.0.1
 */