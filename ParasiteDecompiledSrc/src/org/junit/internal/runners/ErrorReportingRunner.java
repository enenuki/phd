/*  1:   */ package org.junit.internal.runners;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ import java.util.Arrays;
/*  5:   */ import java.util.List;
/*  6:   */ import org.junit.runner.Description;
/*  7:   */ import org.junit.runner.Runner;
/*  8:   */ import org.junit.runner.notification.Failure;
/*  9:   */ import org.junit.runner.notification.RunNotifier;
/* 10:   */ 
/* 11:   */ public class ErrorReportingRunner
/* 12:   */   extends Runner
/* 13:   */ {
/* 14:   */   private final List<Throwable> fCauses;
/* 15:   */   private final Class<?> fTestClass;
/* 16:   */   
/* 17:   */   public ErrorReportingRunner(Class<?> testClass, Throwable cause)
/* 18:   */   {
/* 19:19 */     this.fTestClass = testClass;
/* 20:20 */     this.fCauses = getCauses(cause);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Description getDescription()
/* 24:   */   {
/* 25:25 */     Description description = Description.createSuiteDescription(this.fTestClass);
/* 26:26 */     for (Throwable each : this.fCauses) {
/* 27:27 */       description.addChild(describeCause(each));
/* 28:   */     }
/* 29:28 */     return description;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void run(RunNotifier notifier)
/* 33:   */   {
/* 34:33 */     for (Throwable each : this.fCauses) {
/* 35:34 */       runCause(each, notifier);
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   private List<Throwable> getCauses(Throwable cause)
/* 40:   */   {
/* 41:39 */     if ((cause instanceof InvocationTargetException)) {
/* 42:40 */       return getCauses(cause.getCause());
/* 43:   */     }
/* 44:41 */     if ((cause instanceof org.junit.runners.model.InitializationError)) {
/* 45:42 */       return ((org.junit.runners.model.InitializationError)cause).getCauses();
/* 46:   */     }
/* 47:43 */     if ((cause instanceof InitializationError)) {
/* 48:44 */       return ((InitializationError)cause).getCauses();
/* 49:   */     }
/* 50:46 */     return Arrays.asList(new Throwable[] { cause });
/* 51:   */   }
/* 52:   */   
/* 53:   */   private Description describeCause(Throwable child)
/* 54:   */   {
/* 55:50 */     return Description.createTestDescription(this.fTestClass, "initializationError");
/* 56:   */   }
/* 57:   */   
/* 58:   */   private void runCause(Throwable child, RunNotifier notifier)
/* 59:   */   {
/* 60:55 */     Description description = describeCause(child);
/* 61:56 */     notifier.fireTestStarted(description);
/* 62:57 */     notifier.fireTestFailure(new Failure(description, child));
/* 63:58 */     notifier.fireTestFinished(description);
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.ErrorReportingRunner
 * JD-Core Version:    0.7.0.1
 */