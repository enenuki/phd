/*  1:   */ package org.junit.internal.runners;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.util.List;
/*  6:   */ import org.junit.internal.AssumptionViolatedException;
/*  7:   */ import org.junit.runner.Description;
/*  8:   */ import org.junit.runner.notification.Failure;
/*  9:   */ import org.junit.runner.notification.RunNotifier;
/* 10:   */ 
/* 11:   */ @Deprecated
/* 12:   */ public class ClassRoadie
/* 13:   */ {
/* 14:   */   private RunNotifier fNotifier;
/* 15:   */   private TestClass fTestClass;
/* 16:   */   private Description fDescription;
/* 17:   */   private final Runnable fRunnable;
/* 18:   */   
/* 19:   */   public ClassRoadie(RunNotifier notifier, TestClass testClass, Description description, Runnable runnable)
/* 20:   */   {
/* 21:27 */     this.fNotifier = notifier;
/* 22:28 */     this.fTestClass = testClass;
/* 23:29 */     this.fDescription = description;
/* 24:30 */     this.fRunnable = runnable;
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected void runUnprotected()
/* 28:   */   {
/* 29:34 */     this.fRunnable.run();
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected void addFailure(Throwable targetException)
/* 33:   */   {
/* 34:38 */     this.fNotifier.fireTestFailure(new Failure(this.fDescription, targetException));
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void runProtected()
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:43 */       runBefores();
/* 42:44 */       runUnprotected();
/* 43:   */     }
/* 44:   */     catch (FailedBefore e) {}finally
/* 45:   */     {
/* 46:47 */       runAfters();
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   private void runBefores()
/* 51:   */     throws FailedBefore
/* 52:   */   {
/* 53:   */     try
/* 54:   */     {
/* 55:   */       try
/* 56:   */       {
/* 57:54 */         List<Method> befores = this.fTestClass.getBefores();
/* 58:55 */         for (Method before : befores) {
/* 59:56 */           before.invoke(null, new Object[0]);
/* 60:   */         }
/* 61:   */       }
/* 62:   */       catch (InvocationTargetException e)
/* 63:   */       {
/* 64:58 */         throw e.getTargetException();
/* 65:   */       }
/* 66:   */     }
/* 67:   */     catch (AssumptionViolatedException e)
/* 68:   */     {
/* 69:61 */       throw new FailedBefore();
/* 70:   */     }
/* 71:   */     catch (Throwable e)
/* 72:   */     {
/* 73:63 */       addFailure(e);
/* 74:64 */       throw new FailedBefore();
/* 75:   */     }
/* 76:   */   }
/* 77:   */   
/* 78:   */   private void runAfters()
/* 79:   */   {
/* 80:69 */     List<Method> afters = this.fTestClass.getAfters();
/* 81:70 */     for (Method after : afters) {
/* 82:   */       try
/* 83:   */       {
/* 84:72 */         after.invoke(null, new Object[0]);
/* 85:   */       }
/* 86:   */       catch (InvocationTargetException e)
/* 87:   */       {
/* 88:74 */         addFailure(e.getTargetException());
/* 89:   */       }
/* 90:   */       catch (Throwable e)
/* 91:   */       {
/* 92:76 */         addFailure(e);
/* 93:   */       }
/* 94:   */     }
/* 95:   */   }
/* 96:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.ClassRoadie
 * JD-Core Version:    0.7.0.1
 */