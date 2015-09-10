/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import org.hibernate.internal.CoreMessageLogger;
/*   4:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAttributes;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityListeners;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbIdClass;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbMappedSuperclass;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostLoad;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostPersist;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostRemove;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostUpdate;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPrePersist;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreRemove;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreUpdate;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ class MappedSuperclassMocker
/*  19:    */   extends AbstractEntityObjectMocker
/*  20:    */ {
/*  21: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, MappedSuperclassMocker.class.getName());
/*  22:    */   private JaxbMappedSuperclass mappedSuperclass;
/*  23:    */   
/*  24:    */   MappedSuperclassMocker(IndexBuilder indexBuilder, JaxbMappedSuperclass mappedSuperclass, EntityMappingsMocker.Default defaults)
/*  25:    */   {
/*  26: 55 */     super(indexBuilder, defaults);
/*  27: 56 */     this.mappedSuperclass = mappedSuperclass;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void applyDefaults()
/*  31:    */   {
/*  32: 61 */     DefaultConfigurationHelper.INSTANCE.applyDefaults(this.mappedSuperclass, getDefaults());
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected void processExtra()
/*  36:    */   {
/*  37: 66 */     create(MAPPED_SUPERCLASS);
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected JaxbAttributes getAttributes()
/*  41:    */   {
/*  42: 71 */     return this.mappedSuperclass.getAttributes();
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected JaxbAccessType getAccessType()
/*  46:    */   {
/*  47: 76 */     return this.mappedSuperclass.getAccess();
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected boolean isMetadataComplete()
/*  51:    */   {
/*  52: 81 */     return (this.mappedSuperclass.isMetadataComplete() != null) && (this.mappedSuperclass.isMetadataComplete().booleanValue());
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected boolean isExcludeDefaultListeners()
/*  56:    */   {
/*  57: 86 */     return this.mappedSuperclass.getExcludeDefaultListeners() != null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected boolean isExcludeSuperclassListeners()
/*  61:    */   {
/*  62: 91 */     return this.mappedSuperclass.getExcludeSuperclassListeners() != null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected JaxbIdClass getIdClass()
/*  66:    */   {
/*  67: 96 */     return this.mappedSuperclass.getIdClass();
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected JaxbEntityListeners getEntityListeners()
/*  71:    */   {
/*  72:101 */     return this.mappedSuperclass.getEntityListeners();
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected String getClassName()
/*  76:    */   {
/*  77:105 */     return this.mappedSuperclass.getClazz();
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected JaxbPrePersist getPrePersist()
/*  81:    */   {
/*  82:110 */     return this.mappedSuperclass.getPrePersist();
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected JaxbPreRemove getPreRemove()
/*  86:    */   {
/*  87:115 */     return this.mappedSuperclass.getPreRemove();
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected JaxbPreUpdate getPreUpdate()
/*  91:    */   {
/*  92:120 */     return this.mappedSuperclass.getPreUpdate();
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected JaxbPostPersist getPostPersist()
/*  96:    */   {
/*  97:125 */     return this.mappedSuperclass.getPostPersist();
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected JaxbPostUpdate getPostUpdate()
/* 101:    */   {
/* 102:130 */     return this.mappedSuperclass.getPostUpdate();
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected JaxbPostRemove getPostRemove()
/* 106:    */   {
/* 107:135 */     return this.mappedSuperclass.getPostRemove();
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected JaxbPostLoad getPostLoad()
/* 111:    */   {
/* 112:140 */     return this.mappedSuperclass.getPostLoad();
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.MappedSuperclassMocker
 * JD-Core Version:    0.7.0.1
 */