/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute.type;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.jboss.jandex.AnnotationInstance;
/*  7:   */ 
/*  8:   */ public abstract class AbstractAttributeTypeResolver
/*  9:   */   implements AttributeTypeResolver
/* 10:   */ {
/* 11:   */   protected abstract AnnotationInstance getTypeDeterminingAnnotationInstance();
/* 12:   */   
/* 13:   */   protected abstract String resolveHibernateTypeName(AnnotationInstance paramAnnotationInstance);
/* 14:   */   
/* 15:   */   protected Map<String, String> resolveHibernateTypeParameters(AnnotationInstance annotationInstance)
/* 16:   */   {
/* 17:43 */     return Collections.emptyMap();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public final String getExplicitHibernateTypeName()
/* 21:   */   {
/* 22:48 */     return resolveHibernateTypeName(getTypeDeterminingAnnotationInstance());
/* 23:   */   }
/* 24:   */   
/* 25:   */   public final Map<String, String> getExplicitHibernateTypeParameters()
/* 26:   */   {
/* 27:53 */     if (StringHelper.isNotEmpty(getExplicitHibernateTypeName())) {
/* 28:54 */       return resolveHibernateTypeParameters(getTypeDeterminingAnnotationInstance());
/* 29:   */     }
/* 30:57 */     return Collections.emptyMap();
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.type.AbstractAttributeTypeResolver
 * JD-Core Version:    0.7.0.1
 */