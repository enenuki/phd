/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.filter;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.HashSet;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.Set;
/*  8:   */ import org.hibernate.metamodel.source.annotations.xml.mocker.IndexBuilder;
/*  9:   */ import org.jboss.jandex.AnnotationInstance;
/* 10:   */ import org.jboss.jandex.DotName;
/* 11:   */ 
/* 12:   */ abstract class AbstractAnnotationFilter
/* 13:   */   implements IndexedAnnotationFilter
/* 14:   */ {
/* 15:41 */   protected static final DotName[] EMPTY_DOTNAME_ARRAY = new DotName[0];
/* 16:   */   private Set<DotName> candidates;
/* 17:   */   
/* 18:   */   private boolean match(DotName annName)
/* 19:   */   {
/* 20:45 */     if (this.candidates == null)
/* 21:   */     {
/* 22:46 */       this.candidates = new HashSet();
/* 23:47 */       this.candidates.addAll(Arrays.asList(targetAnnotation()));
/* 24:   */     }
/* 25:49 */     return this.candidates.contains(annName);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void beforePush(IndexBuilder indexBuilder, DotName classDotName, AnnotationInstance annotationInstance)
/* 29:   */   {
/* 30:54 */     DotName annName = annotationInstance.name();
/* 31:55 */     if (!match(annName)) {
/* 32:56 */       return;
/* 33:   */     }
/* 34:58 */     Map<DotName, List<AnnotationInstance>> map = indexBuilder.getIndexedAnnotations(classDotName);
/* 35:59 */     overrideIndexedAnnotationMap(annName, annotationInstance, map);
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected void overrideIndexedAnnotationMap(DotName annName, AnnotationInstance annotationInstance, Map<DotName, List<AnnotationInstance>> map)
/* 39:   */   {
/* 40:63 */     if (!map.containsKey(annName)) {
/* 41:64 */       return;
/* 42:   */     }
/* 43:66 */     List<AnnotationInstance> indexedAnnotationInstanceList = (List)map.get(annName);
/* 44:67 */     if (indexedAnnotationInstanceList.isEmpty()) {
/* 45:68 */       return;
/* 46:   */     }
/* 47:70 */     process(annName, annotationInstance, indexedAnnotationInstanceList);
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected void process(DotName annName, AnnotationInstance annotationInstance, List<AnnotationInstance> indexedAnnotationInstanceList) {}
/* 51:   */   
/* 52:   */   protected DotName[] targetAnnotation()
/* 53:   */   {
/* 54:77 */     return EMPTY_DOTNAME_ARRAY;
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.filter.AbstractAnnotationFilter
 * JD-Core Version:    0.7.0.1
 */