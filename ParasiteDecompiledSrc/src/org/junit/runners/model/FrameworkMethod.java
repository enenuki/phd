/*   1:    */ package org.junit.runners.model;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import java.util.List;
/*   7:    */ import org.junit.internal.runners.model.ReflectiveCallable;
/*   8:    */ 
/*   9:    */ public class FrameworkMethod
/*  10:    */   extends FrameworkMember<FrameworkMethod>
/*  11:    */ {
/*  12:    */   final Method fMethod;
/*  13:    */   
/*  14:    */   public FrameworkMethod(Method method)
/*  15:    */   {
/*  16: 24 */     this.fMethod = method;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Method getMethod()
/*  20:    */   {
/*  21: 31 */     return this.fMethod;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Object invokeExplosively(final Object target, final Object... params)
/*  25:    */     throws Throwable
/*  26:    */   {
/*  27: 41 */     new ReflectiveCallable()
/*  28:    */     {
/*  29:    */       protected Object runReflectiveCall()
/*  30:    */         throws Throwable
/*  31:    */       {
/*  32: 44 */         return FrameworkMethod.this.fMethod.invoke(target, params);
/*  33:    */       }
/*  34:    */     }.run();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getName()
/*  38:    */   {
/*  39: 53 */     return this.fMethod.getName();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void validatePublicVoidNoArg(boolean isStatic, List<Throwable> errors)
/*  43:    */   {
/*  44: 66 */     validatePublicVoid(isStatic, errors);
/*  45: 67 */     if (this.fMethod.getParameterTypes().length != 0) {
/*  46: 68 */       errors.add(new Exception("Method " + this.fMethod.getName() + " should have no parameters"));
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void validatePublicVoid(boolean isStatic, List<Throwable> errors)
/*  51:    */   {
/*  52: 81 */     if (Modifier.isStatic(this.fMethod.getModifiers()) != isStatic)
/*  53:    */     {
/*  54: 82 */       String state = isStatic ? "should" : "should not";
/*  55: 83 */       errors.add(new Exception("Method " + this.fMethod.getName() + "() " + state + " be static"));
/*  56:    */     }
/*  57: 85 */     if (!Modifier.isPublic(this.fMethod.getDeclaringClass().getModifiers())) {
/*  58: 86 */       errors.add(new Exception("Class " + this.fMethod.getDeclaringClass().getName() + " should be public"));
/*  59:    */     }
/*  60: 87 */     if (!Modifier.isPublic(this.fMethod.getModifiers())) {
/*  61: 88 */       errors.add(new Exception("Method " + this.fMethod.getName() + "() should be public"));
/*  62:    */     }
/*  63: 89 */     if (this.fMethod.getReturnType() != Void.TYPE) {
/*  64: 90 */       errors.add(new Exception("Method " + this.fMethod.getName() + "() should be void"));
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isShadowedBy(FrameworkMethod other)
/*  69:    */   {
/*  70: 95 */     if (!other.getName().equals(getName())) {
/*  71: 96 */       return false;
/*  72:    */     }
/*  73: 97 */     if (other.getParameterTypes().length != getParameterTypes().length) {
/*  74: 98 */       return false;
/*  75:    */     }
/*  76: 99 */     for (int i = 0; i < other.getParameterTypes().length; i++) {
/*  77:100 */       if (!other.getParameterTypes()[i].equals(getParameterTypes()[i])) {
/*  78:101 */         return false;
/*  79:    */       }
/*  80:    */     }
/*  81:102 */     return true;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean equals(Object obj)
/*  85:    */   {
/*  86:107 */     if (!FrameworkMethod.class.isInstance(obj)) {
/*  87:108 */       return false;
/*  88:    */     }
/*  89:109 */     return ((FrameworkMethod)obj).fMethod.equals(this.fMethod);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int hashCode()
/*  93:    */   {
/*  94:114 */     return this.fMethod.hashCode();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean producesType(Class<?> type)
/*  98:    */   {
/*  99:122 */     return (getParameterTypes().length == 0) && (type.isAssignableFrom(this.fMethod.getReturnType()));
/* 100:    */   }
/* 101:    */   
/* 102:    */   private Class<?>[] getParameterTypes()
/* 103:    */   {
/* 104:127 */     return this.fMethod.getParameterTypes();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Annotation[] getAnnotations()
/* 108:    */   {
/* 109:135 */     return this.fMethod.getAnnotations();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public <T extends Annotation> T getAnnotation(Class<T> annotationType)
/* 113:    */   {
/* 114:143 */     return this.fMethod.getAnnotation(annotationType);
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.model.FrameworkMethod
 * JD-Core Version:    0.7.0.1
 */