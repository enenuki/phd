/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="version", propOrder={"column", "temporal"})
/*  10:    */ public class JaxbVersion
/*  11:    */ {
/*  12:    */   protected JaxbColumn column;
/*  13:    */   protected JaxbTemporalType temporal;
/*  14:    */   @XmlAttribute(required=true)
/*  15:    */   protected String name;
/*  16:    */   @XmlAttribute
/*  17:    */   protected JaxbAccessType access;
/*  18:    */   
/*  19:    */   public JaxbColumn getColumn()
/*  20:    */   {
/*  21: 66 */     return this.column;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setColumn(JaxbColumn value)
/*  25:    */   {
/*  26: 78 */     this.column = value;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JaxbTemporalType getTemporal()
/*  30:    */   {
/*  31: 90 */     return this.temporal;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setTemporal(JaxbTemporalType value)
/*  35:    */   {
/*  36:102 */     this.temporal = value;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getName()
/*  40:    */   {
/*  41:114 */     return this.name;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setName(String value)
/*  45:    */   {
/*  46:126 */     this.name = value;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public JaxbAccessType getAccess()
/*  50:    */   {
/*  51:138 */     return this.access;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setAccess(JaxbAccessType value)
/*  55:    */   {
/*  56:150 */     this.access = value;
/*  57:    */   }
/*  58:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbVersion
 * JD-Core Version:    0.7.0.1
 */