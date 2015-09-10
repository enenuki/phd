/*  1:   */ package org.junit.internal.builders;
/*  2:   */ 
/*  3:   */ import org.junit.Ignore;
/*  4:   */ import org.junit.runner.Runner;
/*  5:   */ import org.junit.runners.model.RunnerBuilder;
/*  6:   */ 
/*  7:   */ public class IgnoredBuilder
/*  8:   */   extends RunnerBuilder
/*  9:   */ {
/* 10:   */   public Runner runnerForClass(Class<?> testClass)
/* 11:   */   {
/* 12:13 */     if (testClass.getAnnotation(Ignore.class) != null) {
/* 13:14 */       return new IgnoredClassRunner(testClass);
/* 14:   */     }
/* 15:15 */     return null;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.builders.IgnoredBuilder
 * JD-Core Version:    0.7.0.1
 */