/*  1:   */ package org.junit.internal.builders;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Runner;
/*  4:   */ import org.junit.runners.BlockJUnit4ClassRunner;
/*  5:   */ import org.junit.runners.model.RunnerBuilder;
/*  6:   */ 
/*  7:   */ public class JUnit4Builder
/*  8:   */   extends RunnerBuilder
/*  9:   */ {
/* 10:   */   public Runner runnerForClass(Class<?> testClass)
/* 11:   */     throws Throwable
/* 12:   */   {
/* 13:13 */     return new BlockJUnit4ClassRunner(testClass);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.builders.JUnit4Builder
 * JD-Core Version:    0.7.0.1
 */