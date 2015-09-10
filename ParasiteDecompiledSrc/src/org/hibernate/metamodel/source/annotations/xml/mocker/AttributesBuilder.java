/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAttributes;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbBasic;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbElementCollection;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbedded;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddedId;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbId;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbManyToMany;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbManyToOne;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOneToMany;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOneToOne;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTransient;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbVersion;
/*  17:    */ import org.jboss.jandex.ClassInfo;
/*  18:    */ 
/*  19:    */ class AttributesBuilder
/*  20:    */   extends AbstractAttributesBuilder
/*  21:    */ {
/*  22:    */   private JaxbAttributes attributes;
/*  23:    */   
/*  24:    */   AttributesBuilder(IndexBuilder indexBuilder, ClassInfo classInfo, JaxbAccessType accessType, EntityMappingsMocker.Default defaults, JaxbAttributes attributes)
/*  25:    */   {
/*  26: 51 */     super(indexBuilder, classInfo, defaults);
/*  27: 52 */     this.attributes = attributes;
/*  28:    */   }
/*  29:    */   
/*  30:    */   List<JaxbBasic> getBasic()
/*  31:    */   {
/*  32: 57 */     return this.attributes.getBasic();
/*  33:    */   }
/*  34:    */   
/*  35:    */   List<JaxbId> getId()
/*  36:    */   {
/*  37: 62 */     return this.attributes.getId();
/*  38:    */   }
/*  39:    */   
/*  40:    */   List<JaxbTransient> getTransient()
/*  41:    */   {
/*  42: 67 */     return this.attributes.getTransient();
/*  43:    */   }
/*  44:    */   
/*  45:    */   List<JaxbVersion> getVersion()
/*  46:    */   {
/*  47: 72 */     return this.attributes.getVersion();
/*  48:    */   }
/*  49:    */   
/*  50:    */   List<JaxbElementCollection> getElementCollection()
/*  51:    */   {
/*  52: 77 */     return this.attributes.getElementCollection();
/*  53:    */   }
/*  54:    */   
/*  55:    */   List<JaxbEmbedded> getEmbedded()
/*  56:    */   {
/*  57: 82 */     return this.attributes.getEmbedded();
/*  58:    */   }
/*  59:    */   
/*  60:    */   List<JaxbManyToMany> getManyToMany()
/*  61:    */   {
/*  62: 87 */     return this.attributes.getManyToMany();
/*  63:    */   }
/*  64:    */   
/*  65:    */   List<JaxbManyToOne> getManyToOne()
/*  66:    */   {
/*  67: 92 */     return this.attributes.getManyToOne();
/*  68:    */   }
/*  69:    */   
/*  70:    */   List<JaxbOneToMany> getOneToMany()
/*  71:    */   {
/*  72: 97 */     return this.attributes.getOneToMany();
/*  73:    */   }
/*  74:    */   
/*  75:    */   List<JaxbOneToOne> getOneToOne()
/*  76:    */   {
/*  77:102 */     return this.attributes.getOneToOne();
/*  78:    */   }
/*  79:    */   
/*  80:    */   JaxbEmbeddedId getEmbeddedId()
/*  81:    */   {
/*  82:107 */     return this.attributes.getEmbeddedId();
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.AttributesBuilder
 * JD-Core Version:    0.7.0.1
 */