/*  1:   */ package org.junit.internal.runners;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.lang.reflect.Modifier;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import org.junit.After;
/*  9:   */ import org.junit.AfterClass;
/* 10:   */ import org.junit.Before;
/* 11:   */ import org.junit.BeforeClass;
/* 12:   */ import org.junit.Test;
/* 13:   */ 
/* 14:   */ @Deprecated
/* 15:   */ public class MethodValidator
/* 16:   */ {
/* 17:24 */   private final List<Throwable> fErrors = new ArrayList();
/* 18:   */   private TestClass fTestClass;
/* 19:   */   
/* 20:   */   public MethodValidator(TestClass testClass)
/* 21:   */   {
/* 22:29 */     this.fTestClass = testClass;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void validateInstanceMethods()
/* 26:   */   {
/* 27:33 */     validateTestMethods(After.class, false);
/* 28:34 */     validateTestMethods(Before.class, false);
/* 29:35 */     validateTestMethods(Test.class, false);
/* 30:   */     
/* 31:37 */     List<Method> methods = this.fTestClass.getAnnotatedMethods(Test.class);
/* 32:38 */     if (methods.size() == 0) {
/* 33:39 */       this.fErrors.add(new Exception("No runnable methods"));
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void validateStaticMethods()
/* 38:   */   {
/* 39:43 */     validateTestMethods(BeforeClass.class, true);
/* 40:44 */     validateTestMethods(AfterClass.class, true);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<Throwable> validateMethodsForDefaultRunner()
/* 44:   */   {
/* 45:48 */     validateNoArgConstructor();
/* 46:49 */     validateStaticMethods();
/* 47:50 */     validateInstanceMethods();
/* 48:51 */     return this.fErrors;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void assertValid()
/* 52:   */     throws InitializationError
/* 53:   */   {
/* 54:55 */     if (!this.fErrors.isEmpty()) {
/* 55:56 */       throw new InitializationError(this.fErrors);
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void validateNoArgConstructor()
/* 60:   */   {
/* 61:   */     try
/* 62:   */     {
/* 63:61 */       this.fTestClass.getConstructor();
/* 64:   */     }
/* 65:   */     catch (Exception e)
/* 66:   */     {
/* 67:63 */       this.fErrors.add(new Exception("Test class should have public zero-argument constructor", e));
/* 68:   */     }
/* 69:   */   }
/* 70:   */   
/* 71:   */   private void validateTestMethods(Class<? extends Annotation> annotation, boolean isStatic)
/* 72:   */   {
/* 73:69 */     List<Method> methods = this.fTestClass.getAnnotatedMethods(annotation);
/* 74:71 */     for (Method each : methods)
/* 75:   */     {
/* 76:72 */       if (Modifier.isStatic(each.getModifiers()) != isStatic)
/* 77:   */       {
/* 78:73 */         String state = isStatic ? "should" : "should not";
/* 79:74 */         this.fErrors.add(new Exception("Method " + each.getName() + "() " + state + " be static"));
/* 80:   */       }
/* 81:77 */       if (!Modifier.isPublic(each.getDeclaringClass().getModifiers())) {
/* 82:78 */         this.fErrors.add(new Exception("Class " + each.getDeclaringClass().getName() + " should be public"));
/* 83:   */       }
/* 84:80 */       if (!Modifier.isPublic(each.getModifiers())) {
/* 85:81 */         this.fErrors.add(new Exception("Method " + each.getName() + " should be public"));
/* 86:   */       }
/* 87:83 */       if (each.getReturnType() != Void.TYPE) {
/* 88:84 */         this.fErrors.add(new Exception("Method " + each.getName() + " should be void"));
/* 89:   */       }
/* 90:86 */       if (each.getParameterTypes().length != 0) {
/* 91:87 */         this.fErrors.add(new Exception("Method " + each.getName() + " should have no parameters"));
/* 92:   */       }
/* 93:   */     }
/* 94:   */   }
/* 95:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.MethodValidator
 * JD-Core Version:    0.7.0.1
 */