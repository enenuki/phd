/*   1:    */ package org.hibernate.internal.jaxb.mapping.orm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlElement;
/*   8:    */ import javax.xml.bind.annotation.XmlType;
/*   9:    */ 
/*  10:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  11:    */ @XmlType(name="attributes", propOrder={"description", "id", "embeddedId", "basic", "version", "manyToOne", "oneToMany", "oneToOne", "manyToMany", "elementCollection", "embedded", "_transient"})
/*  12:    */ public class JaxbAttributes
/*  13:    */ {
/*  14:    */   protected String description;
/*  15:    */   protected List<JaxbId> id;
/*  16:    */   @XmlElement(name="embedded-id")
/*  17:    */   protected JaxbEmbeddedId embeddedId;
/*  18:    */   protected List<JaxbBasic> basic;
/*  19:    */   protected List<JaxbVersion> version;
/*  20:    */   @XmlElement(name="many-to-one")
/*  21:    */   protected List<JaxbManyToOne> manyToOne;
/*  22:    */   @XmlElement(name="one-to-many")
/*  23:    */   protected List<JaxbOneToMany> oneToMany;
/*  24:    */   @XmlElement(name="one-to-one")
/*  25:    */   protected List<JaxbOneToOne> oneToOne;
/*  26:    */   @XmlElement(name="many-to-many")
/*  27:    */   protected List<JaxbManyToMany> manyToMany;
/*  28:    */   @XmlElement(name="element-collection")
/*  29:    */   protected List<JaxbElementCollection> elementCollection;
/*  30:    */   protected List<JaxbEmbedded> embedded;
/*  31:    */   @XmlElement(name="transient")
/*  32:    */   protected List<JaxbTransient> _transient;
/*  33:    */   
/*  34:    */   public String getDescription()
/*  35:    */   {
/*  36:105 */     return this.description;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setDescription(String value)
/*  40:    */   {
/*  41:117 */     this.description = value;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<JaxbId> getId()
/*  45:    */   {
/*  46:143 */     if (this.id == null) {
/*  47:144 */       this.id = new ArrayList();
/*  48:    */     }
/*  49:146 */     return this.id;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public JaxbEmbeddedId getEmbeddedId()
/*  53:    */   {
/*  54:158 */     return this.embeddedId;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setEmbeddedId(JaxbEmbeddedId value)
/*  58:    */   {
/*  59:170 */     this.embeddedId = value;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<JaxbBasic> getBasic()
/*  63:    */   {
/*  64:196 */     if (this.basic == null) {
/*  65:197 */       this.basic = new ArrayList();
/*  66:    */     }
/*  67:199 */     return this.basic;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List<JaxbVersion> getVersion()
/*  71:    */   {
/*  72:225 */     if (this.version == null) {
/*  73:226 */       this.version = new ArrayList();
/*  74:    */     }
/*  75:228 */     return this.version;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List<JaxbManyToOne> getManyToOne()
/*  79:    */   {
/*  80:254 */     if (this.manyToOne == null) {
/*  81:255 */       this.manyToOne = new ArrayList();
/*  82:    */     }
/*  83:257 */     return this.manyToOne;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public List<JaxbOneToMany> getOneToMany()
/*  87:    */   {
/*  88:283 */     if (this.oneToMany == null) {
/*  89:284 */       this.oneToMany = new ArrayList();
/*  90:    */     }
/*  91:286 */     return this.oneToMany;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<JaxbOneToOne> getOneToOne()
/*  95:    */   {
/*  96:312 */     if (this.oneToOne == null) {
/*  97:313 */       this.oneToOne = new ArrayList();
/*  98:    */     }
/*  99:315 */     return this.oneToOne;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<JaxbManyToMany> getManyToMany()
/* 103:    */   {
/* 104:341 */     if (this.manyToMany == null) {
/* 105:342 */       this.manyToMany = new ArrayList();
/* 106:    */     }
/* 107:344 */     return this.manyToMany;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<JaxbElementCollection> getElementCollection()
/* 111:    */   {
/* 112:370 */     if (this.elementCollection == null) {
/* 113:371 */       this.elementCollection = new ArrayList();
/* 114:    */     }
/* 115:373 */     return this.elementCollection;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public List<JaxbEmbedded> getEmbedded()
/* 119:    */   {
/* 120:399 */     if (this.embedded == null) {
/* 121:400 */       this.embedded = new ArrayList();
/* 122:    */     }
/* 123:402 */     return this.embedded;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<JaxbTransient> getTransient()
/* 127:    */   {
/* 128:428 */     if (this._transient == null) {
/* 129:429 */       this._transient = new ArrayList();
/* 130:    */     }
/* 131:431 */     return this._transient;
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbAttributes
 * JD-Core Version:    0.7.0.1
 */