/*  1:   */ package org.junit.internal.runners;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.lang.reflect.Constructor;
/*  5:   */ import java.lang.reflect.Method;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Collections;
/*  8:   */ import java.util.List;
/*  9:   */ import org.junit.AfterClass;
/* 10:   */ import org.junit.Before;
/* 11:   */ import org.junit.BeforeClass;
/* 12:   */ import org.junit.Test;
/* 13:   */ 
/* 14:   */ @Deprecated
/* 15:   */ public class TestClass
/* 16:   */ {
/* 17:   */   private final Class<?> fClass;
/* 18:   */   
/* 19:   */   public TestClass(Class<?> klass)
/* 20:   */   {
/* 21:26 */     this.fClass = klass;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<Method> getTestMethods()
/* 25:   */   {
/* 26:30 */     return getAnnotatedMethods(Test.class);
/* 27:   */   }
/* 28:   */   
/* 29:   */   List<Method> getBefores()
/* 30:   */   {
/* 31:34 */     return getAnnotatedMethods(BeforeClass.class);
/* 32:   */   }
/* 33:   */   
/* 34:   */   List<Method> getAfters()
/* 35:   */   {
/* 36:38 */     return getAnnotatedMethods(AfterClass.class);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public List<Method> getAnnotatedMethods(Class<? extends Annotation> annotationClass)
/* 40:   */   {
/* 41:42 */     List<Method> results = new ArrayList();
/* 42:43 */     for (Class<?> eachClass : getSuperClasses(this.fClass))
/* 43:   */     {
/* 44:44 */       Method[] methods = eachClass.getDeclaredMethods();
/* 45:45 */       for (Method eachMethod : methods)
/* 46:   */       {
/* 47:46 */         Annotation annotation = eachMethod.getAnnotation(annotationClass);
/* 48:47 */         if ((annotation != null) && (!isShadowed(eachMethod, results))) {
/* 49:48 */           results.add(eachMethod);
/* 50:   */         }
/* 51:   */       }
/* 52:   */     }
/* 53:51 */     if (runsTopToBottom(annotationClass)) {
/* 54:52 */       Collections.reverse(results);
/* 55:   */     }
/* 56:53 */     return results;
/* 57:   */   }
/* 58:   */   
/* 59:   */   private boolean runsTopToBottom(Class<? extends Annotation> annotation)
/* 60:   */   {
/* 61:57 */     return (annotation.equals(Before.class)) || (annotation.equals(BeforeClass.class));
/* 62:   */   }
/* 63:   */   
/* 64:   */   private boolean isShadowed(Method method, List<Method> results)
/* 65:   */   {
/* 66:61 */     for (Method each : results) {
/* 67:62 */       if (isShadowed(method, each)) {
/* 68:63 */         return true;
/* 69:   */       }
/* 70:   */     }
/* 71:65 */     return false;
/* 72:   */   }
/* 73:   */   
/* 74:   */   private boolean isShadowed(Method current, Method previous)
/* 75:   */   {
/* 76:69 */     if (!previous.getName().equals(current.getName())) {
/* 77:70 */       return false;
/* 78:   */     }
/* 79:71 */     if (previous.getParameterTypes().length != current.getParameterTypes().length) {
/* 80:72 */       return false;
/* 81:   */     }
/* 82:73 */     for (int i = 0; i < previous.getParameterTypes().length; i++) {
/* 83:74 */       if (!previous.getParameterTypes()[i].equals(current.getParameterTypes()[i])) {
/* 84:75 */         return false;
/* 85:   */       }
/* 86:   */     }
/* 87:77 */     return true;
/* 88:   */   }
/* 89:   */   
/* 90:   */   private List<Class<?>> getSuperClasses(Class<?> testClass)
/* 91:   */   {
/* 92:81 */     ArrayList<Class<?>> results = new ArrayList();
/* 93:82 */     Class<?> current = testClass;
/* 94:83 */     while (current != null)
/* 95:   */     {
/* 96:84 */       results.add(current);
/* 97:85 */       current = current.getSuperclass();
/* 98:   */     }
/* 99:87 */     return results;
/* :0:   */   }
/* :1:   */   
/* :2:   */   public Constructor<?> getConstructor()
/* :3:   */     throws SecurityException, NoSuchMethodException
/* :4:   */   {
/* :5:91 */     return this.fClass.getConstructor(new Class[0]);
/* :6:   */   }
/* :7:   */   
/* :8:   */   public Class<?> getJavaClass()
/* :9:   */   {
/* ;0:95 */     return this.fClass;
/* ;1:   */   }
/* ;2:   */   
/* ;3:   */   public String getName()
/* ;4:   */   {
/* ;5:99 */     return this.fClass.getName();
/* ;6:   */   }
/* ;7:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.TestClass
 * JD-Core Version:    0.7.0.1
 */