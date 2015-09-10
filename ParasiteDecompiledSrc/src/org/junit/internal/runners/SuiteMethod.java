/*  1:   */ package org.junit.internal.runners;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.lang.reflect.Modifier;
/*  6:   */ import junit.framework.Test;
/*  7:   */ 
/*  8:   */ public class SuiteMethod
/*  9:   */   extends JUnit38ClassRunner
/* 10:   */ {
/* 11:   */   public SuiteMethod(Class<?> klass)
/* 12:   */     throws Throwable
/* 13:   */   {
/* 14:23 */     super(testFromSuiteMethod(klass));
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static Test testFromSuiteMethod(Class<?> klass)
/* 18:   */     throws Throwable
/* 19:   */   {
/* 20:27 */     Method suiteMethod = null;
/* 21:28 */     Test suite = null;
/* 22:   */     try
/* 23:   */     {
/* 24:30 */       suiteMethod = klass.getMethod("suite", new Class[0]);
/* 25:31 */       if (!Modifier.isStatic(suiteMethod.getModifiers())) {
/* 26:32 */         throw new Exception(klass.getName() + ".suite() must be static");
/* 27:   */       }
/* 28:34 */       suite = (Test)suiteMethod.invoke(null, new Object[0]);
/* 29:   */     }
/* 30:   */     catch (InvocationTargetException e)
/* 31:   */     {
/* 32:36 */       throw e.getCause();
/* 33:   */     }
/* 34:38 */     return suite;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.SuiteMethod
 * JD-Core Version:    0.7.0.1
 */