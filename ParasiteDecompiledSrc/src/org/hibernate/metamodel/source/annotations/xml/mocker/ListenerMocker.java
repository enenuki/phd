/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityListener;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityListeners;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostLoad;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostPersist;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostRemove;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPostUpdate;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPrePersist;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreRemove;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPreUpdate;
/*  15:    */ import org.jboss.jandex.AnnotationInstance;
/*  16:    */ import org.jboss.jandex.AnnotationTarget;
/*  17:    */ import org.jboss.jandex.AnnotationValue;
/*  18:    */ import org.jboss.jandex.ClassInfo;
/*  19:    */ 
/*  20:    */ class ListenerMocker
/*  21:    */   extends AbstractMocker
/*  22:    */ {
/*  23:    */   private final ClassInfo classInfo;
/*  24:    */   
/*  25:    */   ListenerMocker(IndexBuilder indexBuilder, ClassInfo classInfo)
/*  26:    */   {
/*  27: 54 */     super(indexBuilder);
/*  28: 55 */     this.classInfo = classInfo;
/*  29:    */   }
/*  30:    */   
/*  31:    */   AnnotationInstance parser(JaxbEntityListeners entityListeners)
/*  32:    */   {
/*  33: 59 */     if (entityListeners.getEntityListener().isEmpty()) {
/*  34: 60 */       throw new MappingException("No child element of <entity-listener> found under <entity-listeners>.");
/*  35:    */     }
/*  36: 62 */     List<AnnotationValue> annotationValueList = new ArrayList(1);
/*  37: 63 */     List<String> clazzNameList = new ArrayList(entityListeners.getEntityListener().size());
/*  38: 64 */     for (JaxbEntityListener listener : entityListeners.getEntityListener())
/*  39:    */     {
/*  40: 65 */       MockHelper.addToCollectionIfNotNull(clazzNameList, listener.getClazz());
/*  41: 66 */       parserEntityListener(listener);
/*  42:    */     }
/*  43: 68 */     MockHelper.classArrayValue("value", clazzNameList, annotationValueList, this.indexBuilder.getServiceRegistry());
/*  44: 69 */     return create(ENTITY_LISTENERS, this.classInfo, annotationValueList);
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void parserEntityListener(JaxbEntityListener listener)
/*  48:    */   {
/*  49: 73 */     String clazz = listener.getClazz();
/*  50: 74 */     ClassInfo tempClassInfo = this.indexBuilder.createClassInfo(clazz);
/*  51: 75 */     ListenerMocker mocker = createListenerMocker(this.indexBuilder, tempClassInfo);
/*  52: 76 */     mocker.parser(listener.getPostLoad());
/*  53: 77 */     mocker.parser(listener.getPostPersist());
/*  54: 78 */     mocker.parser(listener.getPostRemove());
/*  55: 79 */     mocker.parser(listener.getPostUpdate());
/*  56: 80 */     mocker.parser(listener.getPrePersist());
/*  57: 81 */     mocker.parser(listener.getPreRemove());
/*  58: 82 */     mocker.parser(listener.getPreUpdate());
/*  59: 83 */     this.indexBuilder.finishEntityObject(tempClassInfo.name(), null);
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected ListenerMocker createListenerMocker(IndexBuilder indexBuilder, ClassInfo classInfo)
/*  63:    */   {
/*  64: 87 */     return new ListenerMocker(indexBuilder, classInfo);
/*  65:    */   }
/*  66:    */   
/*  67:    */   AnnotationInstance parser(JaxbPrePersist callback)
/*  68:    */   {
/*  69: 92 */     if (callback == null) {
/*  70: 93 */       return null;
/*  71:    */     }
/*  72: 95 */     return create(PRE_PERSIST, getListenerTarget(callback.getMethodName()));
/*  73:    */   }
/*  74:    */   
/*  75:    */   AnnotationInstance parser(JaxbPreRemove callback)
/*  76:    */   {
/*  77:100 */     if (callback == null) {
/*  78:101 */       return null;
/*  79:    */     }
/*  80:103 */     return create(PRE_REMOVE, getListenerTarget(callback.getMethodName()));
/*  81:    */   }
/*  82:    */   
/*  83:    */   AnnotationInstance parser(JaxbPreUpdate callback)
/*  84:    */   {
/*  85:108 */     if (callback == null) {
/*  86:109 */       return null;
/*  87:    */     }
/*  88:111 */     return create(PRE_UPDATE, getListenerTarget(callback.getMethodName()));
/*  89:    */   }
/*  90:    */   
/*  91:    */   AnnotationInstance parser(JaxbPostPersist callback)
/*  92:    */   {
/*  93:116 */     if (callback == null) {
/*  94:117 */       return null;
/*  95:    */     }
/*  96:119 */     return create(POST_PERSIST, getListenerTarget(callback.getMethodName()));
/*  97:    */   }
/*  98:    */   
/*  99:    */   AnnotationInstance parser(JaxbPostUpdate callback)
/* 100:    */   {
/* 101:124 */     if (callback == null) {
/* 102:125 */       return null;
/* 103:    */     }
/* 104:127 */     return create(POST_UPDATE, getListenerTarget(callback.getMethodName()));
/* 105:    */   }
/* 106:    */   
/* 107:    */   AnnotationInstance parser(JaxbPostRemove callback)
/* 108:    */   {
/* 109:132 */     if (callback == null) {
/* 110:133 */       return null;
/* 111:    */     }
/* 112:135 */     return create(POST_REMOVE, getListenerTarget(callback.getMethodName()));
/* 113:    */   }
/* 114:    */   
/* 115:    */   AnnotationInstance parser(JaxbPostLoad callback)
/* 116:    */   {
/* 117:140 */     if (callback == null) {
/* 118:141 */       return null;
/* 119:    */     }
/* 120:143 */     return create(POST_LOAD, getListenerTarget(callback.getMethodName()));
/* 121:    */   }
/* 122:    */   
/* 123:    */   private AnnotationTarget getListenerTarget(String methodName)
/* 124:    */   {
/* 125:147 */     return MockHelper.getTarget(this.indexBuilder.getServiceRegistry(), this.classInfo, methodName, MockHelper.TargetType.METHOD);
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected AnnotationInstance push(AnnotationInstance annotationInstance)
/* 129:    */   {
/* 130:154 */     if ((annotationInstance != null) && (annotationInstance.target() != null)) {
/* 131:155 */       this.indexBuilder.addAnnotationInstance(this.classInfo.name(), annotationInstance);
/* 132:    */     }
/* 133:157 */     return annotationInstance;
/* 134:    */   }
/* 135:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.ListenerMocker
 * JD-Core Version:    0.7.0.1
 */