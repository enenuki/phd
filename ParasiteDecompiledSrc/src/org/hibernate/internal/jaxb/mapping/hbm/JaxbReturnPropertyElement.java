/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElement;
/*   9:    */ import javax.xml.bind.annotation.XmlType;
/*  10:    */ 
/*  11:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  12:    */ @XmlType(name="return-property-element", propOrder={"returnColumn"})
/*  13:    */ public class JaxbReturnPropertyElement
/*  14:    */ {
/*  15:    */   @XmlElement(name="return-column")
/*  16:    */   protected List<JaxbReturnColumn> returnColumn;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String column;
/*  19:    */   @XmlAttribute(required=true)
/*  20:    */   protected String name;
/*  21:    */   
/*  22:    */   public List<JaxbReturnColumn> getReturnColumn()
/*  23:    */   {
/*  24: 85 */     if (this.returnColumn == null) {
/*  25: 86 */       this.returnColumn = new ArrayList();
/*  26:    */     }
/*  27: 88 */     return this.returnColumn;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getColumn()
/*  31:    */   {
/*  32:100 */     return this.column;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setColumn(String value)
/*  36:    */   {
/*  37:112 */     this.column = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getName()
/*  41:    */   {
/*  42:124 */     return this.name;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setName(String value)
/*  46:    */   {
/*  47:136 */     this.name = value;
/*  48:    */   }
/*  49:    */   
/*  50:    */   @XmlAccessorType(XmlAccessType.FIELD)
/*  51:    */   @XmlType(name="")
/*  52:    */   public static class JaxbReturnColumn
/*  53:    */   {
/*  54:    */     @XmlAttribute(required=true)
/*  55:    */     protected String name;
/*  56:    */     
/*  57:    */     public String getName()
/*  58:    */     {
/*  59:173 */       return this.name;
/*  60:    */     }
/*  61:    */     
/*  62:    */     public void setName(String value)
/*  63:    */     {
/*  64:185 */       this.name = value;
/*  65:    */     }
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbReturnPropertyElement
 * JD-Core Version:    0.7.0.1
 */