/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   4:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   5:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*   9:    */ @XmlType(name="map-key-join-column")
/*  10:    */ public class JaxbMapKeyJoinColumn
/*  11:    */ {
/*  12:    */   @XmlAttribute
/*  13:    */   protected String name;
/*  14:    */   @XmlAttribute(name="referenced-column-name")
/*  15:    */   protected String referencedColumnName;
/*  16:    */   @XmlAttribute
/*  17:    */   protected Boolean unique;
/*  18:    */   @XmlAttribute
/*  19:    */   protected Boolean nullable;
/*  20:    */   @XmlAttribute
/*  21:    */   protected Boolean insertable;
/*  22:    */   @XmlAttribute
/*  23:    */   protected Boolean updatable;
/*  24:    */   @XmlAttribute(name="column-definition")
/*  25:    */   protected String columnDefinition;
/*  26:    */   @XmlAttribute
/*  27:    */   protected String table;
/*  28:    */   
/*  29:    */   public String getName()
/*  30:    */   {
/*  31: 80 */     return this.name;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setName(String value)
/*  35:    */   {
/*  36: 92 */     this.name = value;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getReferencedColumnName()
/*  40:    */   {
/*  41:104 */     return this.referencedColumnName;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setReferencedColumnName(String value)
/*  45:    */   {
/*  46:116 */     this.referencedColumnName = value;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Boolean isUnique()
/*  50:    */   {
/*  51:128 */     return this.unique;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setUnique(Boolean value)
/*  55:    */   {
/*  56:140 */     this.unique = value;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Boolean isNullable()
/*  60:    */   {
/*  61:152 */     return this.nullable;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setNullable(Boolean value)
/*  65:    */   {
/*  66:164 */     this.nullable = value;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Boolean isInsertable()
/*  70:    */   {
/*  71:176 */     return this.insertable;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setInsertable(Boolean value)
/*  75:    */   {
/*  76:188 */     this.insertable = value;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Boolean isUpdatable()
/*  80:    */   {
/*  81:200 */     return this.updatable;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setUpdatable(Boolean value)
/*  85:    */   {
/*  86:212 */     this.updatable = value;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getColumnDefinition()
/*  90:    */   {
/*  91:224 */     return this.columnDefinition;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setColumnDefinition(String value)
/*  95:    */   {
/*  96:236 */     this.columnDefinition = value;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getTable()
/* 100:    */   {
/* 101:248 */     return this.table;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setTable(String value)
/* 105:    */   {
/* 106:260 */     this.table = value;
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbMapKeyJoinColumn
 * JD-Core Version:    0.7.0.1
 */