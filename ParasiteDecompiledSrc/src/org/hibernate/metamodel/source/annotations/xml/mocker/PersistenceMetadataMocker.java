/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPersistenceUnitDefaults;
/*  6:   */ import org.hibernate.metamodel.source.annotations.xml.PseudoJpaDotNames;
/*  7:   */ import org.jboss.jandex.AnnotationInstance;
/*  8:   */ import org.jboss.jandex.AnnotationTarget;
/*  9:   */ import org.jboss.jandex.AnnotationValue;
/* 10:   */ import org.jboss.jandex.ClassInfo;
/* 11:   */ import org.jboss.jandex.DotName;
/* 12:   */ 
/* 13:   */ class PersistenceMetadataMocker
/* 14:   */   extends AbstractMocker
/* 15:   */ {
/* 16:   */   private final JaxbPersistenceUnitDefaults persistenceUnitDefaults;
/* 17:20 */   private final GlobalAnnotations globalAnnotations = new GlobalAnnotations();
/* 18:24 */   private static final Map<DotName, DotName> nameMapper = new HashMap();
/* 19:   */   
/* 20:   */   static
/* 21:   */   {
/* 22:27 */     nameMapper.put(ACCESS, PseudoJpaDotNames.DEFAULT_ACCESS);
/* 23:28 */     nameMapper.put(ENTITY_LISTENERS, PseudoJpaDotNames.DEFAULT_ENTITY_LISTENERS);
/* 24:29 */     nameMapper.put(POST_LOAD, PseudoJpaDotNames.DEFAULT_POST_LOAD);
/* 25:30 */     nameMapper.put(POST_REMOVE, PseudoJpaDotNames.DEFAULT_POST_REMOVE);
/* 26:31 */     nameMapper.put(POST_UPDATE, PseudoJpaDotNames.DEFAULT_POST_UPDATE);
/* 27:32 */     nameMapper.put(POST_PERSIST, PseudoJpaDotNames.DEFAULT_POST_PERSIST);
/* 28:33 */     nameMapper.put(PRE_REMOVE, PseudoJpaDotNames.DEFAULT_PRE_REMOVE);
/* 29:34 */     nameMapper.put(PRE_UPDATE, PseudoJpaDotNames.DEFAULT_PRE_UPDATE);
/* 30:35 */     nameMapper.put(PRE_PERSIST, PseudoJpaDotNames.DEFAULT_PRE_PERSIST);
/* 31:36 */     nameMapper.put(PseudoJpaDotNames.DEFAULT_DELIMITED_IDENTIFIERS, PseudoJpaDotNames.DEFAULT_DELIMITED_IDENTIFIERS);
/* 32:   */   }
/* 33:   */   
/* 34:   */   PersistenceMetadataMocker(IndexBuilder indexBuilder, JaxbPersistenceUnitDefaults persistenceUnitDefaults)
/* 35:   */   {
/* 36:43 */     super(indexBuilder);
/* 37:44 */     this.persistenceUnitDefaults = persistenceUnitDefaults;
/* 38:   */   }
/* 39:   */   
/* 40:   */   final void process()
/* 41:   */   {
/* 42:52 */     parserAccessType(this.persistenceUnitDefaults.getAccess(), null);
/* 43:53 */     if (this.persistenceUnitDefaults.getDelimitedIdentifiers() != null) {
/* 44:54 */       create(PseudoJpaDotNames.DEFAULT_DELIMITED_IDENTIFIERS, null);
/* 45:   */     }
/* 46:56 */     if (this.persistenceUnitDefaults.getEntityListeners() != null) {
/* 47:57 */       new DefaultListenerMocker(this.indexBuilder, null).parser(this.persistenceUnitDefaults.getEntityListeners());
/* 48:   */     }
/* 49:59 */     this.indexBuilder.finishGlobalConfigurationMocking(this.globalAnnotations);
/* 50:   */   }
/* 51:   */   
/* 52:   */   protected AnnotationInstance push(AnnotationInstance annotationInstance)
/* 53:   */   {
/* 54:64 */     if (annotationInstance != null) {
/* 55:65 */       return this.globalAnnotations.push(annotationInstance.name(), annotationInstance);
/* 56:   */     }
/* 57:67 */     return null;
/* 58:   */   }
/* 59:   */   
/* 60:   */   protected AnnotationInstance create(DotName name, AnnotationTarget target, AnnotationValue[] annotationValues)
/* 61:   */   {
/* 62:72 */     DotName defaultName = (DotName)nameMapper.get(name);
/* 63:73 */     if (defaultName == null) {
/* 64:74 */       return null;
/* 65:   */     }
/* 66:76 */     return super.create(defaultName, target, annotationValues);
/* 67:   */   }
/* 68:   */   
/* 69:   */   private class DefaultListenerMocker
/* 70:   */     extends ListenerMocker
/* 71:   */   {
/* 72:   */     DefaultListenerMocker(IndexBuilder indexBuilder, ClassInfo classInfo)
/* 73:   */     {
/* 74:82 */       super(classInfo);
/* 75:   */     }
/* 76:   */     
/* 77:   */     protected AnnotationInstance push(AnnotationInstance annotationInstance)
/* 78:   */     {
/* 79:87 */       return PersistenceMetadataMocker.this.push(annotationInstance);
/* 80:   */     }
/* 81:   */     
/* 82:   */     protected AnnotationInstance create(DotName name, AnnotationTarget target, AnnotationValue[] annotationValues)
/* 83:   */     {
/* 84:92 */       return PersistenceMetadataMocker.this.create(name, target, annotationValues);
/* 85:   */     }
/* 86:   */     
/* 87:   */     protected ListenerMocker createListenerMocker(IndexBuilder indexBuilder, ClassInfo classInfo)
/* 88:   */     {
/* 89:97 */       return new DefaultListenerMocker(PersistenceMetadataMocker.this, indexBuilder, classInfo);
/* 90:   */     }
/* 91:   */   }
/* 92:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.PersistenceMetadataMocker
 * JD-Core Version:    0.7.0.1
 */