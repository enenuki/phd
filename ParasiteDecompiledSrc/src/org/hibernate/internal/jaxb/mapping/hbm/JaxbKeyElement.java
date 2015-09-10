/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlType;
/*   9:    */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*  10:    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*  11:    */ 
/*  12:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  13:    */ @XmlType(name="key-element", propOrder={"column"})
/*  14:    */ public class JaxbKeyElement
/*  15:    */ {
/*  16:    */   protected List<JaxbColumnElement> column;
/*  17:    */   @XmlAttribute(name="column")
/*  18:    */   protected String columnAttribute;
/*  19:    */   @XmlAttribute(name="foreign-key")
/*  20:    */   protected String foreignKey;
/*  21:    */   @XmlAttribute(name="not-null")
/*  22:    */   protected Boolean notNull;
/*  23:    */   @XmlAttribute(name="on-delete")
/*  24:    */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  25:    */   protected String onDelete;
/*  26:    */   @XmlAttribute(name="property-ref")
/*  27:    */   protected String propertyRef;
/*  28:    */   @XmlAttribute
/*  29:    */   protected Boolean unique;
/*  30:    */   @XmlAttribute
/*  31:    */   protected Boolean update;
/*  32:    */   
/*  33:    */   public List<JaxbColumnElement> getColumn()
/*  34:    */   {
/*  35:100 */     if (this.column == null) {
/*  36:101 */       this.column = new ArrayList();
/*  37:    */     }
/*  38:103 */     return this.column;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getColumnAttribute()
/*  42:    */   {
/*  43:115 */     return this.columnAttribute;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setColumnAttribute(String value)
/*  47:    */   {
/*  48:127 */     this.columnAttribute = value;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getForeignKey()
/*  52:    */   {
/*  53:139 */     return this.foreignKey;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setForeignKey(String value)
/*  57:    */   {
/*  58:151 */     this.foreignKey = value;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Boolean isNotNull()
/*  62:    */   {
/*  63:163 */     return this.notNull;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setNotNull(Boolean value)
/*  67:    */   {
/*  68:175 */     this.notNull = value;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getOnDelete()
/*  72:    */   {
/*  73:187 */     if (this.onDelete == null) {
/*  74:188 */       return "noaction";
/*  75:    */     }
/*  76:190 */     return this.onDelete;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setOnDelete(String value)
/*  80:    */   {
/*  81:203 */     this.onDelete = value;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getPropertyRef()
/*  85:    */   {
/*  86:215 */     return this.propertyRef;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setPropertyRef(String value)
/*  90:    */   {
/*  91:227 */     this.propertyRef = value;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Boolean isUnique()
/*  95:    */   {
/*  96:239 */     return this.unique;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setUnique(Boolean value)
/* 100:    */   {
/* 101:251 */     this.unique = value;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Boolean isUpdate()
/* 105:    */   {
/* 106:263 */     return this.update;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setUpdate(Boolean value)
/* 110:    */   {
/* 111:275 */     this.update = value;
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbKeyElement
 * JD-Core Version:    0.7.0.1
 */