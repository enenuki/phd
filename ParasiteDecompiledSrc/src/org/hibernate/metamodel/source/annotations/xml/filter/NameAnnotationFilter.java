/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.filter;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.jboss.jandex.AnnotationInstance;
/*  5:   */ import org.jboss.jandex.DotName;
/*  6:   */ 
/*  7:   */ class NameAnnotationFilter
/*  8:   */   extends AbstractAnnotationFilter
/*  9:   */ {
/* 10:   */   protected void process(DotName annName, AnnotationInstance annotationInstance, List<AnnotationInstance> indexedAnnotationInstanceList)
/* 11:   */   {
/* 12:39 */     indexedAnnotationInstanceList.clear();
/* 13:   */   }
/* 14:   */   
/* 15:42 */   public static NameTargetAnnotationFilter INSTANCE = new NameTargetAnnotationFilter();
/* 16:   */   
/* 17:   */   protected DotName[] targetAnnotation()
/* 18:   */   {
/* 19:46 */     return new DotName[] { CACHEABLE, TABLE, EXCLUDE_DEFAULT_LISTENERS, EXCLUDE_SUPERCLASS_LISTENERS, ID_CLASS, INHERITANCE, DISCRIMINATOR_VALUE, DISCRIMINATOR_COLUMN, ENTITY_LISTENERS };
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.filter.NameAnnotationFilter
 * JD-Core Version:    0.7.0.1
 */