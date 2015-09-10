/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.filter;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.metamodel.source.annotations.xml.mocker.MockHelper;
/*  6:   */ import org.jboss.jandex.AnnotationInstance;
/*  7:   */ import org.jboss.jandex.AnnotationTarget;
/*  8:   */ import org.jboss.jandex.DotName;
/*  9:   */ 
/* 10:   */ class NameTargetAnnotationFilter
/* 11:   */   extends AbstractAnnotationFilter
/* 12:   */ {
/* 13:   */   protected void process(DotName annName, AnnotationInstance annotationInstance, List<AnnotationInstance> indexedAnnotationInstanceList)
/* 14:   */   {
/* 15:41 */     AnnotationTarget target = annotationInstance.target();
/* 16:43 */     for (Iterator<AnnotationInstance> iter = indexedAnnotationInstanceList.iterator(); iter.hasNext();)
/* 17:   */     {
/* 18:44 */       AnnotationInstance ann = (AnnotationInstance)iter.next();
/* 19:45 */       if (MockHelper.targetEquals(target, ann.target())) {
/* 20:46 */         iter.remove();
/* 21:   */       }
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:51 */   public static NameTargetAnnotationFilter INSTANCE = new NameTargetAnnotationFilter();
/* 26:   */   
/* 27:   */   protected DotName[] targetAnnotation()
/* 28:   */   {
/* 29:55 */     return new DotName[] { LOB, ID, BASIC, GENERATED_VALUE, VERSION, TRANSIENT, ACCESS, POST_LOAD, POST_PERSIST, POST_REMOVE, POST_UPDATE, PRE_PERSIST, PRE_REMOVE, PRE_UPDATE, EMBEDDED_ID, EMBEDDED, MANY_TO_ONE, MANY_TO_MANY, ONE_TO_ONE, ONE_TO_MANY, ELEMENT_COLLECTION, COLLECTION_TABLE, COLUMN, ENUMERATED, JOIN_TABLE, TEMPORAL, ORDER_BY, ORDER_COLUMN, JOIN_COLUMN, JOIN_COLUMNS, MAPS_ID, MAP_KEY_TEMPORAL, MAP_KEY, MAP_KEY_CLASS, MAP_KEY_COLUMN, MAP_KEY_ENUMERATED };
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.filter.NameTargetAnnotationFilter
 * JD-Core Version:    0.7.0.1
 */