/*  1:   */ package org.hibernate.metamodel.source.annotations.entity;
/*  2:   */ 
/*  3:   */ import javax.persistence.AccessType;
/*  4:   */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*  5:   */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  6:   */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  7:   */ import org.jboss.jandex.AnnotationInstance;
/*  8:   */ import org.jboss.jandex.ClassInfo;
/*  9:   */ 
/* 10:   */ public class EmbeddableClass
/* 11:   */   extends ConfiguredClass
/* 12:   */ {
/* 13:   */   private final String embeddedAttributeName;
/* 14:   */   private final String parentReferencingAttributeName;
/* 15:   */   
/* 16:   */   public EmbeddableClass(ClassInfo classInfo, String embeddedAttributeName, ConfiguredClass parent, AccessType defaultAccessType, AnnotationBindingContext context)
/* 17:   */   {
/* 18:50 */     super(classInfo, defaultAccessType, parent, context);
/* 19:51 */     this.embeddedAttributeName = embeddedAttributeName;
/* 20:52 */     this.parentReferencingAttributeName = checkParentAnnotation();
/* 21:   */   }
/* 22:   */   
/* 23:   */   private String checkParentAnnotation()
/* 24:   */   {
/* 25:56 */     AnnotationInstance parentAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.PARENT);
/* 26:60 */     if (parentAnnotation == null) {
/* 27:61 */       return null;
/* 28:   */     }
/* 29:64 */     return JandexHelper.getPropertyName(parentAnnotation.target());
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getEmbeddedAttributeName()
/* 33:   */   {
/* 34:69 */     return this.embeddedAttributeName;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getParentReferencingAttributeName()
/* 38:   */   {
/* 39:73 */     return this.parentReferencingAttributeName;
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.EmbeddableClass
 * JD-Core Version:    0.7.0.1
 */