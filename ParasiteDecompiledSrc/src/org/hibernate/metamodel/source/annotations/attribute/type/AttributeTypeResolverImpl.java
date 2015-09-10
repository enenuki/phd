/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute.type;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  6:   */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  7:   */ import org.hibernate.metamodel.source.annotations.attribute.MappedAttribute;
/*  8:   */ import org.jboss.jandex.AnnotationInstance;
/*  9:   */ import org.jboss.jandex.AnnotationValue;
/* 10:   */ 
/* 11:   */ public class AttributeTypeResolverImpl
/* 12:   */   extends AbstractAttributeTypeResolver
/* 13:   */ {
/* 14:   */   private final MappedAttribute mappedAttribute;
/* 15:   */   
/* 16:   */   public AttributeTypeResolverImpl(MappedAttribute mappedAttribute)
/* 17:   */   {
/* 18:44 */     this.mappedAttribute = mappedAttribute;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected String resolveHibernateTypeName(AnnotationInstance typeAnnotation)
/* 22:   */   {
/* 23:49 */     String typeName = null;
/* 24:50 */     if (typeAnnotation != null) {
/* 25:51 */       typeName = (String)JandexHelper.getValue(typeAnnotation, "type", String.class);
/* 26:   */     }
/* 27:53 */     return typeName;
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected Map<String, String> resolveHibernateTypeParameters(AnnotationInstance typeAnnotation)
/* 31:   */   {
/* 32:58 */     HashMap<String, String> typeParameters = new HashMap();
/* 33:59 */     AnnotationValue parameterAnnotationValue = typeAnnotation.value("parameters");
/* 34:60 */     if (parameterAnnotationValue != null)
/* 35:   */     {
/* 36:61 */       AnnotationInstance[] parameterAnnotations = parameterAnnotationValue.asNestedArray();
/* 37:62 */       for (AnnotationInstance parameterAnnotationInstance : parameterAnnotations) {
/* 38:63 */         typeParameters.put(JandexHelper.getValue(parameterAnnotationInstance, "name", String.class), JandexHelper.getValue(parameterAnnotationInstance, "value", String.class));
/* 39:   */       }
/* 40:   */     }
/* 41:69 */     return typeParameters;
/* 42:   */   }
/* 43:   */   
/* 44:   */   protected AnnotationInstance getTypeDeterminingAnnotationInstance()
/* 45:   */   {
/* 46:74 */     return JandexHelper.getSingleAnnotation(this.mappedAttribute.annotations(), HibernateDotNames.TYPE);
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.type.AttributeTypeResolverImpl
 * JD-Core Version:    0.7.0.1
 */