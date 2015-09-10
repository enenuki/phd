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
/*  11:    */ @XmlType(name="embeddable-attributes", propOrder={"basic", "manyToOne", "oneToMany", "oneToOne", "manyToMany", "elementCollection", "embedded", "_transient"})
/*  12:    */ public class JaxbEmbeddableAttributes
/*  13:    */ {
/*  14:    */   protected List<JaxbBasic> basic;
/*  15:    */   @XmlElement(name="many-to-one")
/*  16:    */   protected List<JaxbManyToOne> manyToOne;
/*  17:    */   @XmlElement(name="one-to-many")
/*  18:    */   protected List<JaxbOneToMany> oneToMany;
/*  19:    */   @XmlElement(name="one-to-one")
/*  20:    */   protected List<JaxbOneToOne> oneToOne;
/*  21:    */   @XmlElement(name="many-to-many")
/*  22:    */   protected List<JaxbManyToMany> manyToMany;
/*  23:    */   @XmlElement(name="element-collection")
/*  24:    */   protected List<JaxbElementCollection> elementCollection;
/*  25:    */   protected List<JaxbEmbedded> embedded;
/*  26:    */   @XmlElement(name="transient")
/*  27:    */   protected List<JaxbTransient> _transient;
/*  28:    */   
/*  29:    */   public List<JaxbBasic> getBasic()
/*  30:    */   {
/*  31: 96 */     if (this.basic == null) {
/*  32: 97 */       this.basic = new ArrayList();
/*  33:    */     }
/*  34: 99 */     return this.basic;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<JaxbManyToOne> getManyToOne()
/*  38:    */   {
/*  39:125 */     if (this.manyToOne == null) {
/*  40:126 */       this.manyToOne = new ArrayList();
/*  41:    */     }
/*  42:128 */     return this.manyToOne;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<JaxbOneToMany> getOneToMany()
/*  46:    */   {
/*  47:154 */     if (this.oneToMany == null) {
/*  48:155 */       this.oneToMany = new ArrayList();
/*  49:    */     }
/*  50:157 */     return this.oneToMany;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<JaxbOneToOne> getOneToOne()
/*  54:    */   {
/*  55:183 */     if (this.oneToOne == null) {
/*  56:184 */       this.oneToOne = new ArrayList();
/*  57:    */     }
/*  58:186 */     return this.oneToOne;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<JaxbManyToMany> getManyToMany()
/*  62:    */   {
/*  63:212 */     if (this.manyToMany == null) {
/*  64:213 */       this.manyToMany = new ArrayList();
/*  65:    */     }
/*  66:215 */     return this.manyToMany;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List<JaxbElementCollection> getElementCollection()
/*  70:    */   {
/*  71:241 */     if (this.elementCollection == null) {
/*  72:242 */       this.elementCollection = new ArrayList();
/*  73:    */     }
/*  74:244 */     return this.elementCollection;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<JaxbEmbedded> getEmbedded()
/*  78:    */   {
/*  79:270 */     if (this.embedded == null) {
/*  80:271 */       this.embedded = new ArrayList();
/*  81:    */     }
/*  82:273 */     return this.embedded;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<JaxbTransient> getTransient()
/*  86:    */   {
/*  87:299 */     if (this._transient == null) {
/*  88:300 */       this._transient = new ArrayList();
/*  89:    */     }
/*  90:302 */     return this._transient;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddableAttributes
 * JD-Core Version:    0.7.0.1
 */