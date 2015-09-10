/*  1:   */ package org.hibernate.metamodel.source.annotations.xml.filter;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  4:   */ import org.hibernate.metamodel.source.annotations.xml.mocker.IndexBuilder;
/*  5:   */ import org.jboss.jandex.AnnotationInstance;
/*  6:   */ import org.jboss.jandex.DotName;
/*  7:   */ 
/*  8:   */ public abstract interface IndexedAnnotationFilter
/*  9:   */   extends JPADotNames
/* 10:   */ {
/* 11:36 */   public static final IndexedAnnotationFilter[] ALL_FILTERS = { ExclusiveAnnotationFilter.INSTANCE, NameAnnotationFilter.INSTANCE, NameTargetAnnotationFilter.INSTANCE };
/* 12:   */   
/* 13:   */   public abstract void beforePush(IndexBuilder paramIndexBuilder, DotName paramDotName, AnnotationInstance paramAnnotationInstance);
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.filter.IndexedAnnotationFilter
 * JD-Core Version:    0.7.0.1
 */