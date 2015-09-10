/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
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
/*  12:    */ @XmlType(name="table", propOrder={"uniqueConstraint"})
/*  13:    */ public class JaxbTable
/*  14:    */ {
/*  15:    */   @XmlElement(name="unique-constraint")
/*  16:    */   protected List<JaxbUniqueConstraint> uniqueConstraint;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String name;
/*  19:    */   @XmlAttribute
/*  20:    */   protected String catalog;
/*  21:    */   @XmlAttribute
/*  22:    */   protected String schema;
/*  23:    */   
/*  24:    */   public List<JaxbUniqueConstraint> getUniqueConstraint()
/*  25:    */   {
/*  26: 88 */     if (this.uniqueConstraint == null) {
/*  27: 89 */       this.uniqueConstraint = new ArrayList();
/*  28:    */     }
/*  29: 91 */     return this.uniqueConstraint;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getName()
/*  33:    */   {
/*  34:103 */     return this.name;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setName(String value)
/*  38:    */   {
/*  39:115 */     this.name = value;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getCatalog()
/*  43:    */   {
/*  44:127 */     return this.catalog;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setCatalog(String value)
/*  48:    */   {
/*  49:139 */     this.catalog = value;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getSchema()
/*  53:    */   {
/*  54:151 */     return this.schema;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setSchema(String value)
/*  58:    */   {
/*  59:163 */     this.schema = value;
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbTable
 * JD-Core Version:    0.7.0.1
 */