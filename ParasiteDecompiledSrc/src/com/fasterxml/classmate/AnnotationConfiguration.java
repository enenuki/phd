/*  1:   */ package com.fasterxml.classmate;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.util.ClassKey;
/*  4:   */ import java.lang.annotation.Annotation;
/*  5:   */ import java.util.HashMap;
/*  6:   */ 
/*  7:   */ public abstract class AnnotationConfiguration
/*  8:   */ {
/*  9:   */   public abstract AnnotationInclusion getInclusionForClass(Class<? extends Annotation> paramClass);
/* 10:   */   
/* 11:   */   public abstract AnnotationInclusion getInclusionForConstructor(Class<? extends Annotation> paramClass);
/* 12:   */   
/* 13:   */   public abstract AnnotationInclusion getInclusionForField(Class<? extends Annotation> paramClass);
/* 14:   */   
/* 15:   */   public abstract AnnotationInclusion getInclusionForMethod(Class<? extends Annotation> paramClass);
/* 16:   */   
/* 17:   */   public static class StdConfiguration
/* 18:   */     extends AnnotationConfiguration
/* 19:   */   {
/* 20:   */     protected final AnnotationInclusion _defaultInclusion;
/* 21:60 */     protected HashMap<ClassKey, AnnotationInclusion> _inclusions = new HashMap();
/* 22:   */     
/* 23:   */     public StdConfiguration(AnnotationInclusion defaultBehavior)
/* 24:   */     {
/* 25:64 */       this._defaultInclusion = defaultBehavior;
/* 26:   */     }
/* 27:   */     
/* 28:   */     public AnnotationInclusion getInclusionForClass(Class<? extends Annotation> annotationType)
/* 29:   */     {
/* 30:69 */       return _inclusionFor(annotationType);
/* 31:   */     }
/* 32:   */     
/* 33:   */     public AnnotationInclusion getInclusionForConstructor(Class<? extends Annotation> annotationType)
/* 34:   */     {
/* 35:74 */       return _inclusionFor(annotationType);
/* 36:   */     }
/* 37:   */     
/* 38:   */     public AnnotationInclusion getInclusionForField(Class<? extends Annotation> annotationType)
/* 39:   */     {
/* 40:79 */       return getInclusionForClass(annotationType);
/* 41:   */     }
/* 42:   */     
/* 43:   */     public AnnotationInclusion getInclusionForMethod(Class<? extends Annotation> annotationType)
/* 44:   */     {
/* 45:84 */       return getInclusionForClass(annotationType);
/* 46:   */     }
/* 47:   */     
/* 48:   */     public void setInclusion(Class<? extends Annotation> annotationType, AnnotationInclusion incl)
/* 49:   */     {
/* 50:89 */       this._inclusions.put(new ClassKey(annotationType), incl);
/* 51:   */     }
/* 52:   */     
/* 53:   */     protected AnnotationInclusion _inclusionFor(Class<? extends Annotation> annotationType)
/* 54:   */     {
/* 55:94 */       ClassKey key = new ClassKey(annotationType);
/* 56:95 */       AnnotationInclusion beh = (AnnotationInclusion)this._inclusions.get(key);
/* 57:96 */       return beh == null ? this._defaultInclusion : beh;
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.AnnotationConfiguration
 * JD-Core Version:    0.7.0.1
 */