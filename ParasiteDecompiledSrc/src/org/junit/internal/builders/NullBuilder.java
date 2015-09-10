/*  1:   */ package org.junit.internal.builders;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Runner;
/*  4:   */ import org.junit.runners.model.RunnerBuilder;
/*  5:   */ 
/*  6:   */ public class NullBuilder
/*  7:   */   extends RunnerBuilder
/*  8:   */ {
/*  9:   */   public Runner runnerForClass(Class<?> each)
/* 10:   */     throws Throwable
/* 11:   */   {
/* 12:12 */     return null;
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.builders.NullBuilder
 * JD-Core Version:    0.7.0.1
 */