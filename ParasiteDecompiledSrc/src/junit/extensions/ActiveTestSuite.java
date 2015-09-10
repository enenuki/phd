/*  1:   */ package junit.extensions;
/*  2:   */ 
/*  3:   */ import junit.framework.Test;
/*  4:   */ import junit.framework.TestCase;
/*  5:   */ import junit.framework.TestResult;
/*  6:   */ import junit.framework.TestSuite;
/*  7:   */ 
/*  8:   */ public class ActiveTestSuite
/*  9:   */   extends TestSuite
/* 10:   */ {
/* 11:   */   private volatile int fActiveTestDeathCount;
/* 12:   */   
/* 13:   */   public ActiveTestSuite() {}
/* 14:   */   
/* 15:   */   public ActiveTestSuite(Class<? extends TestCase> theClass)
/* 16:   */   {
/* 17:21 */     super(theClass);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ActiveTestSuite(String name)
/* 21:   */   {
/* 22:25 */     super(name);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ActiveTestSuite(Class<? extends TestCase> theClass, String name)
/* 26:   */   {
/* 27:29 */     super(theClass, name);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void run(TestResult result)
/* 31:   */   {
/* 32:34 */     this.fActiveTestDeathCount = 0;
/* 33:35 */     super.run(result);
/* 34:36 */     waitUntilFinished();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void runTest(final Test test, final TestResult result)
/* 38:   */   {
/* 39:41 */     Thread t = new Thread()
/* 40:   */     {
/* 41:   */       public void run()
/* 42:   */       {
/* 43:   */         try
/* 44:   */         {
/* 45:47 */           test.run(result);
/* 46:   */         }
/* 47:   */         finally
/* 48:   */         {
/* 49:49 */           ActiveTestSuite.this.runFinished();
/* 50:   */         }
/* 51:   */       }
/* 52:52 */     };
/* 53:53 */     t.start();
/* 54:   */   }
/* 55:   */   
/* 56:   */   synchronized void waitUntilFinished()
/* 57:   */   {
/* 58:57 */     while (this.fActiveTestDeathCount < testCount()) {
/* 59:   */       try
/* 60:   */       {
/* 61:59 */         wait();
/* 62:   */       }
/* 63:   */       catch (InterruptedException e) {}
/* 64:   */     }
/* 65:   */   }
/* 66:   */   
/* 67:   */   public synchronized void runFinished()
/* 68:   */   {
/* 69:67 */     this.fActiveTestDeathCount += 1;
/* 70:68 */     notifyAll();
/* 71:   */   }
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.extensions.ActiveTestSuite
 * JD-Core Version:    0.7.0.1
 */