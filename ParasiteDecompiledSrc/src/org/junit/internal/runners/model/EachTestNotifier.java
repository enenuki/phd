/*  1:   */ package org.junit.internal.runners.model;
/*  2:   */ 
/*  3:   */ import org.junit.internal.AssumptionViolatedException;
/*  4:   */ import org.junit.runner.Description;
/*  5:   */ import org.junit.runner.notification.Failure;
/*  6:   */ import org.junit.runner.notification.RunNotifier;
/*  7:   */ import org.junit.runners.model.MultipleFailureException;
/*  8:   */ 
/*  9:   */ public class EachTestNotifier
/* 10:   */ {
/* 11:   */   private final RunNotifier fNotifier;
/* 12:   */   private final Description fDescription;
/* 13:   */   
/* 14:   */   public EachTestNotifier(RunNotifier notifier, Description description)
/* 15:   */   {
/* 16:18 */     this.fNotifier = notifier;
/* 17:19 */     this.fDescription = description;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void addFailure(Throwable targetException)
/* 21:   */   {
/* 22:23 */     if ((targetException instanceof MultipleFailureException)) {
/* 23:24 */       addMultipleFailureException((MultipleFailureException)targetException);
/* 24:   */     } else {
/* 25:26 */       this.fNotifier.fireTestFailure(new Failure(this.fDescription, targetException));
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   private void addMultipleFailureException(MultipleFailureException mfe)
/* 30:   */   {
/* 31:32 */     for (Throwable each : mfe.getFailures()) {
/* 32:33 */       addFailure(each);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void addFailedAssumption(AssumptionViolatedException e)
/* 37:   */   {
/* 38:37 */     this.fNotifier.fireTestAssumptionFailed(new Failure(this.fDescription, e));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void fireTestFinished()
/* 42:   */   {
/* 43:41 */     this.fNotifier.fireTestFinished(this.fDescription);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void fireTestStarted()
/* 47:   */   {
/* 48:45 */     this.fNotifier.fireTestStarted(this.fDescription);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void fireTestIgnored()
/* 52:   */   {
/* 53:49 */     this.fNotifier.fireTestIgnored(this.fDescription);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.model.EachTestNotifier
 * JD-Core Version:    0.7.0.1
 */