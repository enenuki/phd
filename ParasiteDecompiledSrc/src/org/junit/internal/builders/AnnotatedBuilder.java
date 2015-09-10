/*  1:   */ package org.junit.internal.builders;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Constructor;
/*  4:   */ import org.junit.runner.RunWith;
/*  5:   */ import org.junit.runner.Runner;
/*  6:   */ import org.junit.runners.model.InitializationError;
/*  7:   */ import org.junit.runners.model.RunnerBuilder;
/*  8:   */ 
/*  9:   */ public class AnnotatedBuilder
/* 10:   */   extends RunnerBuilder
/* 11:   */ {
/* 12:   */   private static final String CONSTRUCTOR_ERROR_FORMAT = "Custom runner class %s should have a public constructor with signature %s(Class testClass)";
/* 13:   */   private RunnerBuilder fSuiteBuilder;
/* 14:   */   
/* 15:   */   public AnnotatedBuilder(RunnerBuilder suiteBuilder)
/* 16:   */   {
/* 17:17 */     this.fSuiteBuilder = suiteBuilder;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Runner runnerForClass(Class<?> testClass)
/* 21:   */     throws Exception
/* 22:   */   {
/* 23:22 */     RunWith annotation = (RunWith)testClass.getAnnotation(RunWith.class);
/* 24:23 */     if (annotation != null) {
/* 25:24 */       return buildRunner(annotation.value(), testClass);
/* 26:   */     }
/* 27:25 */     return null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Runner buildRunner(Class<? extends Runner> runnerClass, Class<?> testClass)
/* 31:   */     throws Exception
/* 32:   */   {
/* 33:   */     try
/* 34:   */     {
/* 35:31 */       return (Runner)runnerClass.getConstructor(new Class[] { Class.class }).newInstance(new Object[] { testClass });
/* 36:   */     }
/* 37:   */     catch (NoSuchMethodException e)
/* 38:   */     {
/* 39:   */       try
/* 40:   */       {
/* 41:35 */         return (Runner)runnerClass.getConstructor(new Class[] { Class.class, RunnerBuilder.class }).newInstance(new Object[] { testClass, this.fSuiteBuilder });
/* 42:   */       }
/* 43:   */       catch (NoSuchMethodException e2)
/* 44:   */       {
/* 45:39 */         String simpleName = runnerClass.getSimpleName();
/* 46:40 */         throw new InitializationError(String.format("Custom runner class %s should have a public constructor with signature %s(Class testClass)", new Object[] { simpleName, simpleName }));
/* 47:   */       }
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.builders.AnnotatedBuilder
 * JD-Core Version:    0.7.0.1
 */