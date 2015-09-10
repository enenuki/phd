/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbBasic;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbElementCollection;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddableAttributes;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbedded;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddedId;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbId;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbManyToMany;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbManyToOne;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOneToMany;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOneToOne;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTransient;
/*  17:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbVersion;
/*  18:    */ import org.jboss.jandex.ClassInfo;
/*  19:    */ 
/*  20:    */ class EmbeddableAttributesBuilder
/*  21:    */   extends AbstractAttributesBuilder
/*  22:    */ {
/*  23:    */   private JaxbEmbeddableAttributes attributes;
/*  24:    */   
/*  25:    */   EmbeddableAttributesBuilder(IndexBuilder indexBuilder, ClassInfo classInfo, JaxbAccessType accessType, EntityMappingsMocker.Default defaults, JaxbEmbeddableAttributes embeddableAttributes)
/*  26:    */   {
/*  27: 52 */     super(indexBuilder, classInfo, defaults);
/*  28: 53 */     this.attributes = embeddableAttributes;
/*  29:    */   }
/*  30:    */   
/*  31:    */   List<JaxbBasic> getBasic()
/*  32:    */   {
/*  33: 58 */     return this.attributes.getBasic();
/*  34:    */   }
/*  35:    */   
/*  36:    */   List<JaxbId> getId()
/*  37:    */   {
/*  38: 63 */     return Collections.emptyList();
/*  39:    */   }
/*  40:    */   
/*  41:    */   List<JaxbTransient> getTransient()
/*  42:    */   {
/*  43: 68 */     return this.attributes.getTransient();
/*  44:    */   }
/*  45:    */   
/*  46:    */   List<JaxbVersion> getVersion()
/*  47:    */   {
/*  48: 73 */     return Collections.emptyList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   List<JaxbElementCollection> getElementCollection()
/*  52:    */   {
/*  53: 78 */     return this.attributes.getElementCollection();
/*  54:    */   }
/*  55:    */   
/*  56:    */   List<JaxbEmbedded> getEmbedded()
/*  57:    */   {
/*  58: 83 */     return this.attributes.getEmbedded();
/*  59:    */   }
/*  60:    */   
/*  61:    */   List<JaxbManyToMany> getManyToMany()
/*  62:    */   {
/*  63: 88 */     return this.attributes.getManyToMany();
/*  64:    */   }
/*  65:    */   
/*  66:    */   List<JaxbManyToOne> getManyToOne()
/*  67:    */   {
/*  68: 93 */     return this.attributes.getManyToOne();
/*  69:    */   }
/*  70:    */   
/*  71:    */   List<JaxbOneToMany> getOneToMany()
/*  72:    */   {
/*  73: 98 */     return this.attributes.getOneToMany();
/*  74:    */   }
/*  75:    */   
/*  76:    */   List<JaxbOneToOne> getOneToOne()
/*  77:    */   {
/*  78:103 */     return this.attributes.getOneToOne();
/*  79:    */   }
/*  80:    */   
/*  81:    */   JaxbEmbeddedId getEmbeddedId()
/*  82:    */   {
/*  83:108 */     return null;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.EmbeddableAttributesBuilder
 * JD-Core Version:    0.7.0.1
 */