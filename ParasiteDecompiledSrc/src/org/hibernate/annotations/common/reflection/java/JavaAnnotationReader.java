/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.lang.reflect.AnnotatedElement;
/*  5:   */ import org.hibernate.annotations.common.reflection.AnnotationReader;
/*  6:   */ 
/*  7:   */ class JavaAnnotationReader
/*  8:   */   implements AnnotationReader
/*  9:   */ {
/* 10:   */   protected final AnnotatedElement element;
/* 11:   */   
/* 12:   */   public JavaAnnotationReader(AnnotatedElement el)
/* 13:   */   {
/* 14:42 */     this.element = el;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public <T extends Annotation> T getAnnotation(Class<T> annotationType)
/* 18:   */   {
/* 19:46 */     return this.element.getAnnotation(annotationType);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType)
/* 23:   */   {
/* 24:50 */     return this.element.isAnnotationPresent(annotationType);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Annotation[] getAnnotations()
/* 28:   */   {
/* 29:54 */     return this.element.getAnnotations();
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaAnnotationReader
 * JD-Core Version:    0.7.0.1
 */