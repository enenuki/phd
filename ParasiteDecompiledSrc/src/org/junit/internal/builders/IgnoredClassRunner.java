/*  1:   */ package org.junit.internal.builders;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Description;
/*  4:   */ import org.junit.runner.Runner;
/*  5:   */ import org.junit.runner.notification.RunNotifier;
/*  6:   */ 
/*  7:   */ public class IgnoredClassRunner
/*  8:   */   extends Runner
/*  9:   */ {
/* 10:   */   private final Class<?> fTestClass;
/* 11:   */   
/* 12:   */   public IgnoredClassRunner(Class<?> testClass)
/* 13:   */   {
/* 14:14 */     this.fTestClass = testClass;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void run(RunNotifier notifier)
/* 18:   */   {
/* 19:19 */     notifier.fireTestIgnored(getDescription());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Description getDescription()
/* 23:   */   {
/* 24:24 */     return Description.createSuiteDescription(this.fTestClass);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.builders.IgnoredClassRunner
 * JD-Core Version:    0.7.0.1
 */