/*   1:    */ package org.hibernate.internal.jaxb.mapping.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.xml.bind.annotation.XmlAccessType;
/*   6:    */ import javax.xml.bind.annotation.XmlAccessorType;
/*   7:    */ import javax.xml.bind.annotation.XmlAttribute;
/*   8:    */ import javax.xml.bind.annotation.XmlElements;
/*   9:    */ import javax.xml.bind.annotation.XmlType;
/*  10:    */ 
/*  11:    */ @XmlAccessorType(XmlAccessType.FIELD)
/*  12:    */ @XmlType(name="composite-element-element", propOrder={"meta", "parent", "tuplizer", "propertyOrManyToOneOrAny"})
/*  13:    */ public class JaxbCompositeElementElement
/*  14:    */ {
/*  15:    */   protected List<JaxbMetaElement> meta;
/*  16:    */   protected JaxbParentElement parent;
/*  17:    */   protected List<JaxbTuplizerElement> tuplizer;
/*  18:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="any", type=JaxbAnyElement.class), @javax.xml.bind.annotation.XmlElement(name="property", type=JaxbPropertyElement.class), @javax.xml.bind.annotation.XmlElement(name="nested-composite-element", type=JaxbNestedCompositeElementElement.class), @javax.xml.bind.annotation.XmlElement(name="many-to-one", type=JaxbManyToOneElement.class)})
/*  19:    */   protected List<Object> propertyOrManyToOneOrAny;
/*  20:    */   @XmlAttribute(name="class", required=true)
/*  21:    */   protected String clazz;
/*  22:    */   @XmlAttribute
/*  23:    */   protected String node;
/*  24:    */   
/*  25:    */   public List<JaxbMetaElement> getMeta()
/*  26:    */   {
/*  27: 99 */     if (this.meta == null) {
/*  28:100 */       this.meta = new ArrayList();
/*  29:    */     }
/*  30:102 */     return this.meta;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public JaxbParentElement getParent()
/*  34:    */   {
/*  35:114 */     return this.parent;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setParent(JaxbParentElement value)
/*  39:    */   {
/*  40:126 */     this.parent = value;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<JaxbTuplizerElement> getTuplizer()
/*  44:    */   {
/*  45:152 */     if (this.tuplizer == null) {
/*  46:153 */       this.tuplizer = new ArrayList();
/*  47:    */     }
/*  48:155 */     return this.tuplizer;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<Object> getPropertyOrManyToOneOrAny()
/*  52:    */   {
/*  53:184 */     if (this.propertyOrManyToOneOrAny == null) {
/*  54:185 */       this.propertyOrManyToOneOrAny = new ArrayList();
/*  55:    */     }
/*  56:187 */     return this.propertyOrManyToOneOrAny;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getClazz()
/*  60:    */   {
/*  61:199 */     return this.clazz;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setClazz(String value)
/*  65:    */   {
/*  66:211 */     this.clazz = value;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getNode()
/*  70:    */   {
/*  71:223 */     return this.node;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setNode(String value)
/*  75:    */   {
/*  76:235 */     this.node = value;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbCompositeElementElement
 * JD-Core Version:    0.7.0.1
 */