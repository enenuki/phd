/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbBasic;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbElementCollection;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbedded;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddedId;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbId;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbManyToMany;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbManyToOne;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOneToMany;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbOneToOne;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTransient;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbVersion;
/*  15:    */ import org.jboss.jandex.ClassInfo;
/*  16:    */ 
/*  17:    */ abstract class AbstractAttributesBuilder
/*  18:    */ {
/*  19:    */   private ClassInfo classInfo;
/*  20:    */   private EntityMappingsMocker.Default defaults;
/*  21:    */   private IndexBuilder indexBuilder;
/*  22:    */   
/*  23:    */   AbstractAttributesBuilder(IndexBuilder indexBuilder, ClassInfo classInfo, EntityMappingsMocker.Default defaults)
/*  24:    */   {
/*  25: 57 */     this.indexBuilder = indexBuilder;
/*  26: 58 */     this.classInfo = classInfo;
/*  27: 59 */     this.defaults = defaults;
/*  28:    */   }
/*  29:    */   
/*  30:    */   final void parser()
/*  31:    */   {
/*  32: 63 */     for (JaxbId id : getId()) {
/*  33: 64 */       new IdMocker(this.indexBuilder, this.classInfo, this.defaults, id).process();
/*  34:    */     }
/*  35: 66 */     for (JaxbTransient transientObj : getTransient()) {
/*  36: 67 */       new TransientMocker(this.indexBuilder, this.classInfo, this.defaults, transientObj).process();
/*  37:    */     }
/*  38: 69 */     for (JaxbVersion version : getVersion()) {
/*  39: 70 */       new VersionMocker(this.indexBuilder, this.classInfo, this.defaults, version).process();
/*  40:    */     }
/*  41: 73 */     for (JaxbBasic basic : getBasic()) {
/*  42: 74 */       new BasicMocker(this.indexBuilder, this.classInfo, this.defaults, basic).process();
/*  43:    */     }
/*  44: 76 */     for (JaxbElementCollection elementCollection : getElementCollection()) {
/*  45: 77 */       new ElementCollectionMocker(this.indexBuilder, this.classInfo, this.defaults, elementCollection).process();
/*  46:    */     }
/*  47: 81 */     for (JaxbEmbedded embedded : getEmbedded()) {
/*  48: 82 */       new EmbeddedMocker(this.indexBuilder, this.classInfo, this.defaults, embedded).process();
/*  49:    */     }
/*  50: 84 */     for (JaxbManyToMany manyToMany : getManyToMany()) {
/*  51: 85 */       new ManyToManyMocker(this.indexBuilder, this.classInfo, this.defaults, manyToMany).process();
/*  52:    */     }
/*  53: 88 */     for (JaxbManyToOne manyToOne : getManyToOne()) {
/*  54: 89 */       new ManyToOneMocker(this.indexBuilder, this.classInfo, this.defaults, manyToOne).process();
/*  55:    */     }
/*  56: 91 */     for (JaxbOneToMany oneToMany : getOneToMany()) {
/*  57: 92 */       new OneToManyMocker(this.indexBuilder, this.classInfo, this.defaults, oneToMany).process();
/*  58:    */     }
/*  59: 96 */     for (JaxbOneToOne oneToOne : getOneToOne()) {
/*  60: 97 */       new OneToOneMocker(this.indexBuilder, this.classInfo, this.defaults, oneToOne).process();
/*  61:    */     }
/*  62: 99 */     if (getEmbeddedId() != null) {
/*  63:100 */       new EmbeddedIdMocker(this.indexBuilder, this.classInfo, this.defaults, getEmbeddedId()).process();
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   abstract List<JaxbId> getId();
/*  68:    */   
/*  69:    */   abstract List<JaxbTransient> getTransient();
/*  70:    */   
/*  71:    */   abstract List<JaxbVersion> getVersion();
/*  72:    */   
/*  73:    */   abstract List<JaxbBasic> getBasic();
/*  74:    */   
/*  75:    */   abstract List<JaxbElementCollection> getElementCollection();
/*  76:    */   
/*  77:    */   abstract List<JaxbEmbedded> getEmbedded();
/*  78:    */   
/*  79:    */   abstract List<JaxbManyToMany> getManyToMany();
/*  80:    */   
/*  81:    */   abstract List<JaxbManyToOne> getManyToOne();
/*  82:    */   
/*  83:    */   abstract List<JaxbOneToMany> getOneToMany();
/*  84:    */   
/*  85:    */   abstract List<JaxbOneToOne> getOneToOne();
/*  86:    */   
/*  87:    */   abstract JaxbEmbeddedId getEmbeddedId();
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.AbstractAttributesBuilder
 * JD-Core Version:    0.7.0.1
 */