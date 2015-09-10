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
/*  11:    */ @XmlType(name="key-property-element", propOrder={"meta", "column", "type"})
/*  12:    */ public class JaxbKeyPropertyElement
/*  13:    */ {
/*  14:    */   protected List<JaxbMetaElement> meta;
/*  15:    */   protected List<JaxbColumnElement> column;
/*  16:    */   protected JaxbTypeElement type;
/*  17:    */   @XmlAttribute
/*  18:    */   protected String access;
/*  19:    */   @XmlAttribute(name="column")
/*  20:    */   protected String columnAttribute;
/*  21:    */   @XmlAttribute
/*  22:    */   protected String length;
/*  23:    */   @XmlAttribute(required=true)
/*  24:    */   protected String name;
/*  25:    */   @XmlAttribute
/*  26:    */   protected String node;
/*  27:    */   @XmlAttribute(name="type")
/*  28:    */   protected String typeAttribute;
/*  29:    */   
/*  30:    */   public List<JaxbMetaElement> getMeta()
/*  31:    */   {
/*  32: 93 */     if (this.meta == null) {
/*  33: 94 */       this.meta = new ArrayList();
/*  34:    */     }
/*  35: 96 */     return this.meta;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<JaxbColumnElement> getColumn()
/*  39:    */   {
/*  40:122 */     if (this.column == null) {
/*  41:123 */       this.column = new ArrayList();
/*  42:    */     }
/*  43:125 */     return this.column;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public JaxbTypeElement getType()
/*  47:    */   {
/*  48:137 */     return this.type;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setType(JaxbTypeElement value)
/*  52:    */   {
/*  53:149 */     this.type = value;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getAccess()
/*  57:    */   {
/*  58:161 */     return this.access;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setAccess(String value)
/*  62:    */   {
/*  63:173 */     this.access = value;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getColumnAttribute()
/*  67:    */   {
/*  68:185 */     return this.columnAttribute;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setColumnAttribute(String value)
/*  72:    */   {
/*  73:197 */     this.columnAttribute = value;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getLength()
/*  77:    */   {
/*  78:209 */     return this.length;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setLength(String value)
/*  82:    */   {
/*  83:221 */     this.length = value;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getName()
/*  87:    */   {
/*  88:233 */     return this.name;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setName(String value)
/*  92:    */   {
/*  93:245 */     this.name = value;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getNode()
/*  97:    */   {
/*  98:257 */     return this.node;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setNode(String value)
/* 102:    */   {
/* 103:269 */     this.node = value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getTypeAttribute()
/* 107:    */   {
/* 108:281 */     return this.typeAttribute;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setTypeAttribute(String value)
/* 112:    */   {
/* 113:293 */     this.typeAttribute = value;
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbKeyPropertyElement
 * JD-Core Version:    0.7.0.1
 */