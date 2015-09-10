/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import org.hibernate.AssertionFailure;
/*   4:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAttributes;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityListeners;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbIdClass;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostLoad;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostPersist;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostRemove;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostUpdate;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPrePersist;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreRemove;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreUpdate;
/*  15:    */ import org.jboss.jandex.AnnotationInstance;
/*  16:    */ import org.jboss.jandex.AnnotationTarget;
/*  17:    */ import org.jboss.jandex.ClassInfo;
/*  18:    */ import org.jboss.jandex.DotName;
/*  19:    */ 
/*  20:    */ abstract class AbstractEntityObjectMocker
/*  21:    */   extends AnnotationMocker
/*  22:    */ {
/*  23:    */   private ListenerMocker listenerParser;
/*  24:    */   protected AbstractAttributesBuilder attributesBuilder;
/*  25:    */   protected ClassInfo classInfo;
/*  26:    */   
/*  27:    */   AbstractEntityObjectMocker(IndexBuilder indexBuilder, EntityMappingsMocker.Default defaults)
/*  28:    */   {
/*  29: 53 */     super(indexBuilder, defaults);
/*  30:    */   }
/*  31:    */   
/*  32: 56 */   private boolean isPreProcessCalled = false;
/*  33:    */   
/*  34:    */   final void preProcess()
/*  35:    */   {
/*  36: 62 */     applyDefaults();
/*  37: 63 */     this.classInfo = this.indexBuilder.createClassInfo(getClassName());
/*  38: 64 */     DotName classDotName = this.classInfo.name();
/*  39: 65 */     if (isMetadataComplete()) {
/*  40: 66 */       this.indexBuilder.metadataComplete(classDotName);
/*  41:    */     }
/*  42: 68 */     parserAccessType(getAccessType(), getTarget());
/*  43: 69 */     this.isPreProcessCalled = true;
/*  44:    */   }
/*  45:    */   
/*  46:    */   final void process()
/*  47:    */   {
/*  48: 73 */     if (!this.isPreProcessCalled) {
/*  49: 74 */       throw new AssertionFailure("preProcess should be called before process");
/*  50:    */     }
/*  51: 76 */     if (getAccessType() == null)
/*  52:    */     {
/*  53: 77 */       JaxbAccessType accessType = AccessHelper.getEntityAccess(getTargetName(), this.indexBuilder);
/*  54: 78 */       if (accessType == null) {
/*  55: 79 */         accessType = getDefaults().getAccess();
/*  56:    */       }
/*  57: 81 */       parserAccessType(accessType, getTarget());
/*  58:    */     }
/*  59: 83 */     processExtra();
/*  60: 84 */     if (isExcludeDefaultListeners()) {
/*  61: 85 */       create(EXCLUDE_DEFAULT_LISTENERS);
/*  62:    */     }
/*  63: 87 */     if (isExcludeSuperclassListeners()) {
/*  64: 88 */       create(EXCLUDE_SUPERCLASS_LISTENERS);
/*  65:    */     }
/*  66: 90 */     parserIdClass(getIdClass());
/*  67: 92 */     if (getAttributes() != null) {
/*  68: 93 */       getAttributesBuilder().parser();
/*  69:    */     }
/*  70: 96 */     if (getEntityListeners() != null) {
/*  71: 97 */       getListenerParser().parser(getEntityListeners());
/*  72:    */     }
/*  73: 99 */     getListenerParser().parser(getPrePersist());
/*  74:100 */     getListenerParser().parser(getPreRemove());
/*  75:101 */     getListenerParser().parser(getPreUpdate());
/*  76:102 */     getListenerParser().parser(getPostPersist());
/*  77:103 */     getListenerParser().parser(getPostUpdate());
/*  78:104 */     getListenerParser().parser(getPostRemove());
/*  79:105 */     getListenerParser().parser(getPostLoad());
/*  80:    */     
/*  81:107 */     this.indexBuilder.finishEntityObject(getTargetName(), getDefaults());
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected abstract void processExtra();
/*  85:    */   
/*  86:    */   protected abstract void applyDefaults();
/*  87:    */   
/*  88:    */   protected abstract boolean isMetadataComplete();
/*  89:    */   
/*  90:    */   protected abstract boolean isExcludeDefaultListeners();
/*  91:    */   
/*  92:    */   protected abstract boolean isExcludeSuperclassListeners();
/*  93:    */   
/*  94:    */   protected abstract JaxbIdClass getIdClass();
/*  95:    */   
/*  96:    */   protected abstract JaxbEntityListeners getEntityListeners();
/*  97:    */   
/*  98:    */   protected abstract JaxbAccessType getAccessType();
/*  99:    */   
/* 100:    */   protected abstract String getClassName();
/* 101:    */   
/* 102:    */   protected abstract JaxbPrePersist getPrePersist();
/* 103:    */   
/* 104:    */   protected abstract JaxbPreRemove getPreRemove();
/* 105:    */   
/* 106:    */   protected abstract JaxbPreUpdate getPreUpdate();
/* 107:    */   
/* 108:    */   protected abstract JaxbPostPersist getPostPersist();
/* 109:    */   
/* 110:    */   protected abstract JaxbPostUpdate getPostUpdate();
/* 111:    */   
/* 112:    */   protected abstract JaxbPostRemove getPostRemove();
/* 113:    */   
/* 114:    */   protected abstract JaxbPostLoad getPostLoad();
/* 115:    */   
/* 116:    */   protected abstract JaxbAttributes getAttributes();
/* 117:    */   
/* 118:    */   protected ListenerMocker getListenerParser()
/* 119:    */   {
/* 120:149 */     if (this.listenerParser == null) {
/* 121:150 */       this.listenerParser = new ListenerMocker(this.indexBuilder, this.classInfo);
/* 122:    */     }
/* 123:152 */     return this.listenerParser;
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected AbstractAttributesBuilder getAttributesBuilder()
/* 127:    */   {
/* 128:156 */     if (this.attributesBuilder == null) {
/* 129:157 */       this.attributesBuilder = new AttributesBuilder(this.indexBuilder, this.classInfo, getAccessType(), getDefaults(), getAttributes());
/* 130:    */     }
/* 131:161 */     return this.attributesBuilder;
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected AnnotationInstance parserIdClass(JaxbIdClass idClass)
/* 135:    */   {
/* 136:165 */     if (idClass == null) {
/* 137:166 */       return null;
/* 138:    */     }
/* 139:168 */     String className = MockHelper.buildSafeClassName(idClass.getClazz(), getDefaults().getPackageName());
/* 140:169 */     return create(ID_CLASS, MockHelper.classValueArray("value", className, this.indexBuilder.getServiceRegistry()));
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected DotName getTargetName()
/* 144:    */   {
/* 145:179 */     return this.classInfo.name();
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected AnnotationTarget getTarget()
/* 149:    */   {
/* 150:184 */     return this.classInfo;
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.AbstractEntityObjectMocker
 * JD-Core Version:    0.7.0.1
 */