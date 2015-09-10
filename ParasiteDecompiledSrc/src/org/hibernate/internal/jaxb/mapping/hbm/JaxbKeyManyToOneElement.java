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
/*  11:    */ @XmlType(name="key-many-to-one-element", propOrder={"meta", "column"})
/*  12:    */ public class JaxbKeyManyToOneElement
/*  13:    */ {
/*  14:    */   protected List<JaxbMetaElement> meta;
/*  15:    */   protected List<JaxbColumnElement> column;
/*  16:    */   @XmlAttribute
/*  17:    */   protected String access;
/*  18:    */   @XmlAttribute(name="class")
/*  19:    */   protected String clazz;
/*  20:    */   @XmlAttribute(name="column")
/*  21:    */   protected String columnAttribute;
/*  22:    */   @XmlAttribute(name="entity-name")
/*  23:    */   protected String entityName;
/*  24:    */   @XmlAttribute(name="foreign-key")
/*  25:    */   protected String foreignKey;
/*  26:    */   @XmlAttribute
/*  27:    */   protected JaxbLazyAttribute lazy;
/*  28:    */   @XmlAttribute(required=true)
/*  29:    */   protected String name;
/*  30:    */   
/*  31:    */   public List<JaxbMetaElement> getMeta()
/*  32:    */   {
/*  33: 93 */     if (this.meta == null) {
/*  34: 94 */       this.meta = new ArrayList();
/*  35:    */     }
/*  36: 96 */     return this.meta;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<JaxbColumnElement> getColumn()
/*  40:    */   {
/*  41:122 */     if (this.column == null) {
/*  42:123 */       this.column = new ArrayList();
/*  43:    */     }
/*  44:125 */     return this.column;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getAccess()
/*  48:    */   {
/*  49:137 */     return this.access;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setAccess(String value)
/*  53:    */   {
/*  54:149 */     this.access = value;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getClazz()
/*  58:    */   {
/*  59:161 */     return this.clazz;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setClazz(String value)
/*  63:    */   {
/*  64:173 */     this.clazz = value;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getColumnAttribute()
/*  68:    */   {
/*  69:185 */     return this.columnAttribute;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setColumnAttribute(String value)
/*  73:    */   {
/*  74:197 */     this.columnAttribute = value;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getEntityName()
/*  78:    */   {
/*  79:209 */     return this.entityName;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setEntityName(String value)
/*  83:    */   {
/*  84:221 */     this.entityName = value;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getForeignKey()
/*  88:    */   {
/*  89:233 */     return this.foreignKey;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setForeignKey(String value)
/*  93:    */   {
/*  94:245 */     this.foreignKey = value;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public JaxbLazyAttribute getLazy()
/*  98:    */   {
/*  99:257 */     return this.lazy;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setLazy(JaxbLazyAttribute value)
/* 103:    */   {
/* 104:269 */     this.lazy = value;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getName()
/* 108:    */   {
/* 109:281 */     return this.name;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setName(String value)
/* 113:    */   {
/* 114:293 */     this.name = value;
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbKeyManyToOneElement
 * JD-Core Version:    0.7.0.1
 */