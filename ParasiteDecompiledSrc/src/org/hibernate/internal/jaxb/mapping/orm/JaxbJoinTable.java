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
/*  12:    */ @XmlType(name="join-table", propOrder={"joinColumn", "inverseJoinColumn", "uniqueConstraint"})
/*  13:    */ public class JaxbJoinTable
/*  14:    */ {
/*  15:    */   @XmlElement(name="join-column")
/*  16:    */   protected List<JaxbJoinColumn> joinColumn;
/*  17:    */   @XmlElement(name="inverse-join-column")
/*  18:    */   protected List<JaxbJoinColumn> inverseJoinColumn;
/*  19:    */   @XmlElement(name="unique-constraint")
/*  20:    */   protected List<JaxbUniqueConstraint> uniqueConstraint;
/*  21:    */   @XmlAttribute
/*  22:    */   protected String name;
/*  23:    */   @XmlAttribute
/*  24:    */   protected String catalog;
/*  25:    */   @XmlAttribute
/*  26:    */   protected String schema;
/*  27:    */   
/*  28:    */   public List<JaxbJoinColumn> getJoinColumn()
/*  29:    */   {
/*  30: 97 */     if (this.joinColumn == null) {
/*  31: 98 */       this.joinColumn = new ArrayList();
/*  32:    */     }
/*  33:100 */     return this.joinColumn;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<JaxbJoinColumn> getInverseJoinColumn()
/*  37:    */   {
/*  38:126 */     if (this.inverseJoinColumn == null) {
/*  39:127 */       this.inverseJoinColumn = new ArrayList();
/*  40:    */     }
/*  41:129 */     return this.inverseJoinColumn;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<JaxbUniqueConstraint> getUniqueConstraint()
/*  45:    */   {
/*  46:155 */     if (this.uniqueConstraint == null) {
/*  47:156 */       this.uniqueConstraint = new ArrayList();
/*  48:    */     }
/*  49:158 */     return this.uniqueConstraint;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getName()
/*  53:    */   {
/*  54:170 */     return this.name;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setName(String value)
/*  58:    */   {
/*  59:182 */     this.name = value;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getCatalog()
/*  63:    */   {
/*  64:194 */     return this.catalog;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setCatalog(String value)
/*  68:    */   {
/*  69:206 */     this.catalog = value;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getSchema()
/*  73:    */   {
/*  74:218 */     return this.schema;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setSchema(String value)
/*  78:    */   {
/*  79:230 */     this.schema = value;
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbJoinTable
 * JD-Core Version:    0.7.0.1
 */