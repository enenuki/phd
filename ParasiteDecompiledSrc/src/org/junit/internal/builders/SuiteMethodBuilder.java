/*  1:   */ package org.junit.internal.builders;
/*  2:   */ 
/*  3:   */ import org.junit.internal.runners.SuiteMethod;
/*  4:   */ import org.junit.runner.Runner;
/*  5:   */ import org.junit.runners.model.RunnerBuilder;
/*  6:   */ 
/*  7:   */ public class SuiteMethodBuilder
/*  8:   */   extends RunnerBuilder
/*  9:   */ {
/* 10:   */   public Runner runnerForClass(Class<?> each)
/* 11:   */     throws Throwable
/* 12:   */   {
/* 13:13 */     if (hasSuiteMethod(each)) {
/* 14:14 */       return new SuiteMethod(each);
/* 15:   */     }
/* 16:15 */     return null;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean hasSuiteMethod(Class<?> testClass)
/* 20:   */   {
/* 21:   */     try
/* 22:   */     {
/* 23:20 */       testClass.getMethod("suite", new Class[0]);
/* 24:   */     }
/* 25:   */     catch (NoSuchMethodException e)
/* 26:   */     {
/* 27:22 */       return false;
/* 28:   */     }
/* 29:24 */     return true;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.builders.SuiteMethodBuilder
 * JD-Core Version:    0.7.0.1
 */