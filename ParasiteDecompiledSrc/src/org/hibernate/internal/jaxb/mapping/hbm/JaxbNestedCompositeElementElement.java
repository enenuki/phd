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
/*  12:    */ @XmlType(name="nested-composite-element-element", propOrder={"parent", "tuplizer", "propertyOrManyToOneOrAny"})
/*  13:    */ public class JaxbNestedCompositeElementElement
/*  14:    */ {
/*  15:    */   protected JaxbParentElement parent;
/*  16:    */   protected List<JaxbTuplizerElement> tuplizer;
/*  17:    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="any", type=JaxbAnyElement.class), @javax.xml.bind.annotation.XmlElement(name="property", type=JaxbPropertyElement.class), @javax.xml.bind.annotation.XmlElement(name="nested-composite-element", type=JaxbNestedCompositeElementElement.class), @javax.xml.bind.annotation.XmlElement(name="many-to-one", type=JaxbManyToOneElement.class)})
/*  18:    */   protected List<Object> propertyOrManyToOneOrAny;
/*  19:    */   @XmlAttribute
/*  20:    */   protected String access;
/*  21:    */   @XmlAttribute(name="class", required=true)
/*  22:    */   protected String clazz;
/*  23:    */   @XmlAttribute(required=true)
/*  24:    */   protected String name;
/*  25:    */   @XmlAttribute
/*  26:    */   protected String node;
/*  27:    */   
/*  28:    */   public JaxbParentElement getParent()
/*  29:    */   {
/*  30: 86 */     return this.parent;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setParent(JaxbParentElement value)
/*  34:    */   {
/*  35: 98 */     this.parent = value;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<JaxbTuplizerElement> getTuplizer()
/*  39:    */   {
/*  40:124 */     if (this.tuplizer == null) {
/*  41:125 */       this.tuplizer = new ArrayList();
/*  42:    */     }
/*  43:127 */     return this.tuplizer;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List<Object> getPropertyOrManyToOneOrAny()
/*  47:    */   {
/*  48:156 */     if (this.propertyOrManyToOneOrAny == null) {
/*  49:157 */       this.propertyOrManyToOneOrAny = new ArrayList();
/*  50:    */     }
/*  51:159 */     return this.propertyOrManyToOneOrAny;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getAccess()
/*  55:    */   {
/*  56:171 */     return this.access;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setAccess(String value)
/*  60:    */   {
/*  61:183 */     this.access = value;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getClazz()
/*  65:    */   {
/*  66:195 */     return this.clazz;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setClazz(String value)
/*  70:    */   {
/*  71:207 */     this.clazz = value;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getName()
/*  75:    */   {
/*  76:219 */     return this.name;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setName(String value)
/*  80:    */   {
/*  81:231 */     this.name = value;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getNode()
/*  85:    */   {
/*  86:243 */     return this.node;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setNode(String value)
/*  90:    */   {
/*  91:255 */     this.node = value;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.JaxbNestedCompositeElementElement
 * JD-Core Version:    0.7.0.1
 */