/*  1:   */ package org.junit.runner;
/*  2:   */ 
/*  3:   */ import org.junit.runners.Suite;
/*  4:   */ import org.junit.runners.model.InitializationError;
/*  5:   */ import org.junit.runners.model.RunnerBuilder;
/*  6:   */ 
/*  7:   */ public class Computer
/*  8:   */ {
/*  9:   */   public static Computer serial()
/* 10:   */   {
/* 11:17 */     return new Computer();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Runner getSuite(final RunnerBuilder builder, Class<?>[] classes)
/* 15:   */     throws InitializationError
/* 16:   */   {
/* 17:26 */     new Suite(new RunnerBuilder()
/* 18:   */     {
/* 19:   */       public Runner runnerForClass(Class<?> testClass)
/* 20:   */         throws Throwable
/* 21:   */       {
/* 22:29 */         return Computer.this.getRunner(builder, testClass);
/* 23:   */       }
/* 24:29 */     }, classes);
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected Runner getRunner(RunnerBuilder builder, Class<?> testClass)
/* 28:   */     throws Throwable
/* 29:   */   {
/* 30:38 */     return builder.runnerForClass(testClass);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.Computer
 * JD-Core Version:    0.7.0.1
 */