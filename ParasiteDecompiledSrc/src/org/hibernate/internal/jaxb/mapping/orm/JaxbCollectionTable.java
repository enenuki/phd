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
/*  12:    */ @XmlType(name="collection-table", propOrder={"joinColumn", "uniqueConstraint"})
/*  13:    */ public class JaxbCollectionTable
/*  14:    */ {
/*  15:    */   @XmlElement(name="join-column")
/*  16:    */   protected List<JaxbJoinColumn> joinColumn;
/*  17:    */   @XmlElement(name="unique-constraint")
/*  18:    */   protected List<JaxbUniqueConstraint> uniqueConstraint;
/*  19:    */   @XmlAttribute
/*  20:    */   protected String name;
/*  21:    */   @XmlAttribute
/*  22:    */   protected String catalog;
/*  23:    */   @XmlAttribute
/*  24:    */   protected String schema;
/*  25:    */   
/*  26:    */   public List<JaxbJoinColumn> getJoinColumn()
/*  27:    */   {
/*  28: 92 */     if (this.joinColumn == null) {
/*  29: 93 */       this.joinColumn = new ArrayList();
/*  30:    */     }
/*  31: 95 */     return this.joinColumn;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<JaxbUniqueConstraint> getUniqueConstraint()
/*  35:    */   {
/*  36:121 */     if (this.uniqueConstraint == null) {
/*  37:122 */       this.uniqueConstraint = new ArrayList();
/*  38:    */     }
/*  39:124 */     return this.uniqueConstraint;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getName()
/*  43:    */   {
/*  44:136 */     return this.name;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setName(String value)
/*  48:    */   {
/*  49:148 */     this.name = value;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getCatalog()
/*  53:    */   {
/*  54:160 */     return this.catalog;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setCatalog(String value)
/*  58:    */   {
/*  59:172 */     this.catalog = value;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getSchema()
/*  63:    */   {
/*  64:184 */     return this.schema;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setSchema(String value)
/*  68:    */   {
/*  69:196 */     this.schema = value;
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbCollectionTable
 * JD-Core Version:    0.7.0.1
 */