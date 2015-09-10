/*  1:   */ package org.junit.internal.builders;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.List;
/*  5:   */ import org.junit.runner.Runner;
/*  6:   */ import org.junit.runners.model.RunnerBuilder;
/*  7:   */ 
/*  8:   */ public class AllDefaultPossibilitiesBuilder
/*  9:   */   extends RunnerBuilder
/* 10:   */ {
/* 11:   */   private final boolean fCanUseSuiteMethod;
/* 12:   */   
/* 13:   */   public AllDefaultPossibilitiesBuilder(boolean canUseSuiteMethod)
/* 14:   */   {
/* 15:16 */     this.fCanUseSuiteMethod = canUseSuiteMethod;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Runner runnerForClass(Class<?> testClass)
/* 19:   */     throws Throwable
/* 20:   */   {
/* 21:21 */     List<RunnerBuilder> builders = Arrays.asList(new RunnerBuilder[] { ignoredBuilder(), annotatedBuilder(), suiteMethodBuilder(), junit3Builder(), junit4Builder() });
/* 22:28 */     for (RunnerBuilder each : builders)
/* 23:   */     {
/* 24:29 */       Runner runner = each.safeRunnerForClass(testClass);
/* 25:30 */       if (runner != null) {
/* 26:31 */         return runner;
/* 27:   */       }
/* 28:   */     }
/* 29:33 */     return null;
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected JUnit4Builder junit4Builder()
/* 33:   */   {
/* 34:37 */     return new JUnit4Builder();
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected JUnit3Builder junit3Builder()
/* 38:   */   {
/* 39:41 */     return new JUnit3Builder();
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected AnnotatedBuilder annotatedBuilder()
/* 43:   */   {
/* 44:45 */     return new AnnotatedBuilder(this);
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected IgnoredBuilder ignoredBuilder()
/* 48:   */   {
/* 49:49 */     return new IgnoredBuilder();
/* 50:   */   }
/* 51:   */   
/* 52:   */   protected RunnerBuilder suiteMethodBuilder()
/* 53:   */   {
/* 54:53 */     if (this.fCanUseSuiteMethod) {
/* 55:54 */       return new SuiteMethodBuilder();
/* 56:   */     }
/* 57:55 */     return new NullBuilder();
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.builders.AllDefaultPossibilitiesBuilder
 * JD-Core Version:    0.7.0.1
 */