/*  1:   */ package org.junit.experimental.theories;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.lang.reflect.Constructor;
/*  5:   */ import java.lang.reflect.Method;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Arrays;
/*  8:   */ import java.util.List;
/*  9:   */ 
/* 10:   */ public class ParameterSignature
/* 11:   */ {
/* 12:   */   private final Class<?> type;
/* 13:   */   private final Annotation[] annotations;
/* 14:   */   
/* 15:   */   public static ArrayList<ParameterSignature> signatures(Method method)
/* 16:   */   {
/* 17:15 */     return signatures(method.getParameterTypes(), method.getParameterAnnotations());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static List<ParameterSignature> signatures(Constructor<?> constructor)
/* 21:   */   {
/* 22:20 */     return signatures(constructor.getParameterTypes(), constructor.getParameterAnnotations());
/* 23:   */   }
/* 24:   */   
/* 25:   */   private static ArrayList<ParameterSignature> signatures(Class<?>[] parameterTypes, Annotation[][] parameterAnnotations)
/* 26:   */   {
/* 27:26 */     ArrayList<ParameterSignature> sigs = new ArrayList();
/* 28:27 */     for (int i = 0; i < parameterTypes.length; i++) {
/* 29:28 */       sigs.add(new ParameterSignature(parameterTypes[i], parameterAnnotations[i]));
/* 30:   */     }
/* 31:31 */     return sigs;
/* 32:   */   }
/* 33:   */   
/* 34:   */   private ParameterSignature(Class<?> type, Annotation[] annotations)
/* 35:   */   {
/* 36:39 */     this.type = type;
/* 37:40 */     this.annotations = annotations;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean canAcceptType(Class<?> candidate)
/* 41:   */   {
/* 42:44 */     return this.type.isAssignableFrom(candidate);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Class<?> getType()
/* 46:   */   {
/* 47:48 */     return this.type;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public List<Annotation> getAnnotations()
/* 51:   */   {
/* 52:52 */     return Arrays.asList(this.annotations);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean canAcceptArrayType(Class<?> type)
/* 56:   */   {
/* 57:56 */     return (type.isArray()) && (canAcceptType(type.getComponentType()));
/* 58:   */   }
/* 59:   */   
/* 60:   */   public boolean hasAnnotation(Class<? extends Annotation> type)
/* 61:   */   {
/* 62:60 */     return getAnnotation(type) != null;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public <T extends Annotation> T findDeepAnnotation(Class<T> annotationType)
/* 66:   */   {
/* 67:64 */     Annotation[] annotations2 = this.annotations;
/* 68:65 */     return findDeepAnnotation(annotations2, annotationType, 3);
/* 69:   */   }
/* 70:   */   
/* 71:   */   private <T extends Annotation> T findDeepAnnotation(Annotation[] annotations, Class<T> annotationType, int depth)
/* 72:   */   {
/* 73:70 */     if (depth == 0) {
/* 74:71 */       return null;
/* 75:   */     }
/* 76:72 */     for (Annotation each : annotations)
/* 77:   */     {
/* 78:73 */       if (annotationType.isInstance(each)) {
/* 79:74 */         return (Annotation)annotationType.cast(each);
/* 80:   */       }
/* 81:75 */       Annotation candidate = findDeepAnnotation(each.annotationType().getAnnotations(), annotationType, depth - 1);
/* 82:77 */       if (candidate != null) {
/* 83:78 */         return (Annotation)annotationType.cast(candidate);
/* 84:   */       }
/* 85:   */     }
/* 86:81 */     return null;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public <T extends Annotation> T getAnnotation(Class<T> annotationType)
/* 90:   */   {
/* 91:85 */     for (Annotation each : getAnnotations()) {
/* 92:86 */       if (annotationType.isInstance(each)) {
/* 93:87 */         return (Annotation)annotationType.cast(each);
/* 94:   */       }
/* 95:   */     }
/* 96:88 */     return null;
/* 97:   */   }
/* 98:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.theories.ParameterSignature
 * JD-Core Version:    0.7.0.1
 */