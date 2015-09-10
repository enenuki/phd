/*  1:   */ package org.junit.experimental;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.concurrent.Callable;
/*  6:   */ import java.util.concurrent.ExecutorService;
/*  7:   */ import java.util.concurrent.Executors;
/*  8:   */ import java.util.concurrent.Future;
/*  9:   */ import org.junit.runner.Computer;
/* 10:   */ import org.junit.runner.Runner;
/* 11:   */ import org.junit.runners.ParentRunner;
/* 12:   */ import org.junit.runners.model.InitializationError;
/* 13:   */ import org.junit.runners.model.RunnerBuilder;
/* 14:   */ import org.junit.runners.model.RunnerScheduler;
/* 15:   */ 
/* 16:   */ public class ParallelComputer
/* 17:   */   extends Computer
/* 18:   */ {
/* 19:   */   private final boolean fClasses;
/* 20:   */   private final boolean fMethods;
/* 21:   */   
/* 22:   */   public ParallelComputer(boolean classes, boolean methods)
/* 23:   */   {
/* 24:23 */     this.fClasses = classes;
/* 25:24 */     this.fMethods = methods;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static Computer classes()
/* 29:   */   {
/* 30:28 */     return new ParallelComputer(true, false);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static Computer methods()
/* 34:   */   {
/* 35:32 */     return new ParallelComputer(false, true);
/* 36:   */   }
/* 37:   */   
/* 38:   */   private static <T> Runner parallelize(Runner runner)
/* 39:   */   {
/* 40:36 */     if ((runner instanceof ParentRunner)) {
/* 41:37 */       ((ParentRunner)runner).setScheduler(new RunnerScheduler()
/* 42:   */       {
/* 43:38 */         private final List<Future<Object>> fResults = new ArrayList();
/* 44:40 */         private final ExecutorService fService = Executors.newCachedThreadPool();
/* 45:   */         
/* 46:   */         public void schedule(final Runnable childStatement)
/* 47:   */         {
/* 48:44 */           this.fResults.add(this.fService.submit(new Callable()
/* 49:   */           {
/* 50:   */             public Object call()
/* 51:   */               throws Exception
/* 52:   */             {
/* 53:46 */               childStatement.run();
/* 54:47 */               return null;
/* 55:   */             }
/* 56:   */           }));
/* 57:   */         }
/* 58:   */         
/* 59:   */         public void finished()
/* 60:   */         {
/* 61:53 */           for (Future<Object> each : this.fResults) {
/* 62:   */             try
/* 63:   */             {
/* 64:55 */               each.get();
/* 65:   */             }
/* 66:   */             catch (Exception e)
/* 67:   */             {
/* 68:57 */               e.printStackTrace();
/* 69:   */             }
/* 70:   */           }
/* 71:   */         }
/* 72:   */       });
/* 73:   */     }
/* 74:62 */     return runner;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public Runner getSuite(RunnerBuilder builder, Class<?>[] classes)
/* 78:   */     throws InitializationError
/* 79:   */   {
/* 80:68 */     Runner suite = super.getSuite(builder, classes);
/* 81:69 */     return this.fClasses ? parallelize(suite) : suite;
/* 82:   */   }
/* 83:   */   
/* 84:   */   protected Runner getRunner(RunnerBuilder builder, Class<?> testClass)
/* 85:   */     throws Throwable
/* 86:   */   {
/* 87:75 */     Runner runner = super.getRunner(builder, testClass);
/* 88:76 */     return this.fMethods ? parallelize(runner) : runner;
/* 89:   */   }
/* 90:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.ParallelComputer
 * JD-Core Version:    0.7.0.1
 */