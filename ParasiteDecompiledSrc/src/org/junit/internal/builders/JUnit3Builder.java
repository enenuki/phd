/*  1:   */ package org.junit.internal.builders;
/*  2:   */ 
/*  3:   */ import junit.framework.TestCase;
/*  4:   */ import org.junit.internal.runners.JUnit38ClassRunner;
/*  5:   */ import org.junit.runner.Runner;
/*  6:   */ import org.junit.runners.model.RunnerBuilder;
/*  7:   */ 
/*  8:   */ public class JUnit3Builder
/*  9:   */   extends RunnerBuilder
/* 10:   */ {
/* 11:   */   public Runner runnerForClass(Class<?> testClass)
/* 12:   */     throws Throwable
/* 13:   */   {
/* 14:13 */     if (isPre4Test(testClass)) {
/* 15:14 */       return new JUnit38ClassRunner(testClass);
/* 16:   */     }
/* 17:15 */     return null;
/* 18:   */   }
/* 19:   */   
/* 20:   */   boolean isPre4Test(Class<?> testClass)
/* 21:   */   {
/* 22:19 */     return TestCase.class.isAssignableFrom(testClass);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.builders.JUnit3Builder
 * JD-Core Version:    0.7.0.1
 */