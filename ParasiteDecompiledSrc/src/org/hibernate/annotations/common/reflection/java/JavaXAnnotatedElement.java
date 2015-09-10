/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.lang.reflect.AnnotatedElement;
/*  5:   */ import org.hibernate.annotations.common.reflection.AnnotationReader;
/*  6:   */ import org.hibernate.annotations.common.reflection.XAnnotatedElement;
/*  7:   */ 
/*  8:   */ abstract class JavaXAnnotatedElement
/*  9:   */   implements XAnnotatedElement
/* 10:   */ {
/* 11:   */   private final JavaReflectionManager factory;
/* 12:   */   private final AnnotatedElement annotatedElement;
/* 13:   */   
/* 14:   */   public JavaXAnnotatedElement(AnnotatedElement annotatedElement, JavaReflectionManager factory)
/* 15:   */   {
/* 16:43 */     this.factory = factory;
/* 17:44 */     this.annotatedElement = annotatedElement;
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected JavaReflectionManager getFactory()
/* 21:   */   {
/* 22:48 */     return this.factory;
/* 23:   */   }
/* 24:   */   
/* 25:   */   private AnnotationReader getAnnotationReader()
/* 26:   */   {
/* 27:52 */     return this.factory.buildAnnotationReader(this.annotatedElement);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public <T extends Annotation> T getAnnotation(Class<T> annotationType)
/* 31:   */   {
/* 32:56 */     return getAnnotationReader().getAnnotation(annotationType);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType)
/* 36:   */   {
/* 37:60 */     return getAnnotationReader().isAnnotationPresent(annotationType);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Annotation[] getAnnotations()
/* 41:   */   {
/* 42:64 */     return getAnnotationReader().getAnnotations();
/* 43:   */   }
/* 44:   */   
/* 45:   */   AnnotatedElement toAnnotatedElement()
/* 46:   */   {
/* 47:68 */     return this.annotatedElement;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean equals(Object obj)
/* 51:   */   {
/* 52:73 */     if (!(obj instanceof JavaXAnnotatedElement)) {
/* 53:73 */       return false;
/* 54:   */     }
/* 55:74 */     JavaXAnnotatedElement other = (JavaXAnnotatedElement)obj;
/* 56:   */     
/* 57:76 */     return this.annotatedElement.equals(other.toAnnotatedElement());
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int hashCode()
/* 61:   */   {
/* 62:81 */     return this.annotatedElement.hashCode();
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String toString()
/* 66:   */   {
/* 67:86 */     return this.annotatedElement.toString();
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXAnnotatedElement
 * JD-Core Version:    0.7.0.1
 */