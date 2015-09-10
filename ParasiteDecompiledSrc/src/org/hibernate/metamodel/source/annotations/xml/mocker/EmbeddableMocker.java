/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import org.hibernate.internal.CoreMessageLogger;
/*   4:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAttributes;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddable;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityListeners;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbIdClass;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostLoad;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostPersist;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostRemove;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostUpdate;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPrePersist;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreRemove;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreUpdate;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ class EmbeddableMocker
/*  19:    */   extends AbstractEntityObjectMocker
/*  20:    */ {
/*  21: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EmbeddableMocker.class.getName());
/*  22:    */   private JaxbEmbeddable embeddable;
/*  23:    */   
/*  24:    */   EmbeddableMocker(IndexBuilder indexBuilder, JaxbEmbeddable embeddable, EntityMappingsMocker.Default defaults)
/*  25:    */   {
/*  26: 55 */     super(indexBuilder, defaults);
/*  27: 56 */     this.embeddable = embeddable;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected AbstractAttributesBuilder getAttributesBuilder()
/*  31:    */   {
/*  32: 61 */     if (this.attributesBuilder == null) {
/*  33: 62 */       this.attributesBuilder = new EmbeddableAttributesBuilder(this.indexBuilder, this.classInfo, getAccessType(), getDefaults(), this.embeddable.getAttributes());
/*  34:    */     }
/*  35: 66 */     return this.attributesBuilder;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void processExtra()
/*  39:    */   {
/*  40: 71 */     create(EMBEDDABLE);
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void applyDefaults()
/*  44:    */   {
/*  45: 76 */     DefaultConfigurationHelper.INSTANCE.applyDefaults(this.embeddable, getDefaults());
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected boolean isMetadataComplete()
/*  49:    */   {
/*  50: 81 */     return (this.embeddable.isMetadataComplete() != null) && (this.embeddable.isMetadataComplete().booleanValue());
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected boolean isExcludeDefaultListeners()
/*  54:    */   {
/*  55: 86 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected boolean isExcludeSuperclassListeners()
/*  59:    */   {
/*  60: 91 */     return false;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected JaxbIdClass getIdClass()
/*  64:    */   {
/*  65: 96 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected JaxbEntityListeners getEntityListeners()
/*  69:    */   {
/*  70:101 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected JaxbAccessType getAccessType()
/*  74:    */   {
/*  75:106 */     return this.embeddable.getAccess();
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected String getClassName()
/*  79:    */   {
/*  80:111 */     return this.embeddable.getClazz();
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected JaxbPrePersist getPrePersist()
/*  84:    */   {
/*  85:116 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected JaxbPreRemove getPreRemove()
/*  89:    */   {
/*  90:121 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected JaxbPreUpdate getPreUpdate()
/*  94:    */   {
/*  95:126 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected JaxbPostPersist getPostPersist()
/*  99:    */   {
/* 100:131 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected JaxbPostUpdate getPostUpdate()
/* 104:    */   {
/* 105:136 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected JaxbPostRemove getPostRemove()
/* 109:    */   {
/* 110:141 */     return null;
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected JaxbPostLoad getPostLoad()
/* 114:    */   {
/* 115:146 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected JaxbAttributes getAttributes()
/* 119:    */   {
/* 120:151 */     return null;
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.EmbeddableMocker
 * JD-Core Version:    0.7.0.1
 */