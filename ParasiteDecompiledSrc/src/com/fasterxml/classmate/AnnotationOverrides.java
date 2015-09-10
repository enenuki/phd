/*  1:   */ package com.fasterxml.classmate;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.util.ClassKey;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public abstract class AnnotationOverrides
/*  9:   */ {
/* 10:   */   public List<Class<?>> mixInsFor(Class<?> beanClass)
/* 11:   */   {
/* 12:26 */     return mixInsFor(new ClassKey(beanClass));
/* 13:   */   }
/* 14:   */   
/* 15:   */   public abstract List<Class<?>> mixInsFor(ClassKey paramClassKey);
/* 16:   */   
/* 17:   */   public static StdBuilder builder()
/* 18:   */   {
/* 19:36 */     return new StdBuilder();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static class StdBuilder
/* 23:   */   {
/* 24:52 */     protected final HashMap<ClassKey, List<Class<?>>> _targetsToOverrides = new HashMap();
/* 25:   */     
/* 26:   */     public StdBuilder add(Class<?> target, Class<?> mixin)
/* 27:   */     {
/* 28:57 */       return add(new ClassKey(target), mixin);
/* 29:   */     }
/* 30:   */     
/* 31:   */     public StdBuilder add(ClassKey target, Class<?> mixin)
/* 32:   */     {
/* 33:62 */       List<Class<?>> mixins = (List)this._targetsToOverrides.get(target);
/* 34:63 */       if (mixins == null)
/* 35:   */       {
/* 36:64 */         mixins = new ArrayList();
/* 37:65 */         this._targetsToOverrides.put(target, mixins);
/* 38:   */       }
/* 39:67 */       mixins.add(mixin);
/* 40:68 */       return this;
/* 41:   */     }
/* 42:   */     
/* 43:   */     public AnnotationOverrides build()
/* 44:   */     {
/* 45:76 */       return new AnnotationOverrides.StdImpl(this._targetsToOverrides);
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static class StdImpl
/* 50:   */     extends AnnotationOverrides
/* 51:   */   {
/* 52:   */     protected final HashMap<ClassKey, List<Class<?>>> _targetsToOverrides;
/* 53:   */     
/* 54:   */     public StdImpl(HashMap<ClassKey, List<Class<?>>> overrides)
/* 55:   */     {
/* 56:90 */       this._targetsToOverrides = new HashMap(overrides);
/* 57:   */     }
/* 58:   */     
/* 59:   */     public List<Class<?>> mixInsFor(ClassKey target)
/* 60:   */     {
/* 61:95 */       return (List)this._targetsToOverrides.get(target);
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.AnnotationOverrides
 * JD-Core Version:    0.7.0.1
 */