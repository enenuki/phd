/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="order-column")
/*  10:    */ public class JaxbOrderColumn
/*  11:    */ {
/*  12:    */   @XmlAttribute
/*  13:    */   protected String name;
/*  14:    */   @XmlAttribute
/*  15:    */   protected Boolean nullable;
/*  16:    */   @XmlAttribute
/*  17:    */   protected Boolean insertable;
/*  18:    */   @XmlAttribute
/*  19:    */   protected Boolean updatable;
/*  20:    */   @XmlAttribute(name="column-definition")
/*  21:    */   protected String columnDefinition;
/*  22:    */   
/*  23:    */   public String getName()
/*  24:    */   {
/*  25: 69 */     return this.name;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setName(String value)
/*  29:    */   {
/*  30: 81 */     this.name = value;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Boolean isNullable()
/*  34:    */   {
/*  35: 93 */     return this.nullable;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setNullable(Boolean value)
/*  39:    */   {
/*  40:105 */     this.nullable = value;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Boolean isInsertable()
/*  44:    */   {
/*  45:117 */     return this.insertable;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setInsertable(Boolean value)
/*  49:    */   {
/*  50:129 */     this.insertable = value;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Boolean isUpdatable()
/*  54:    */   {
/*  55:141 */     return this.updatable;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setUpdatable(Boolean value)
/*  59:    */   {
/*  60:153 */     this.updatable = value;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getColumnDefinition()
/*  64:    */   {
/*  65:165 */     return this.columnDefinition;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setColumnDefinition(String value)
/*  69:    */   {
/*  70:177 */     this.columnDefinition = value;
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbOrderColumn
 * JD-Core Version:    0.7.0.1
 */