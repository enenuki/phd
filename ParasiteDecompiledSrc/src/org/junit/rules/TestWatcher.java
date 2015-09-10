/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Description;
/*  4:   */ import org.junit.runners.model.Statement;
/*  5:   */ 
/*  6:   */ public abstract class TestWatcher
/*  7:   */   extends TestRule
/*  8:   */ {
/*  9:   */   protected Statement apply(final Statement base, final Description description)
/* 10:   */   {
/* 11:42 */     new Statement()
/* 12:   */     {
/* 13:   */       public void evaluate()
/* 14:   */         throws Throwable
/* 15:   */       {
/* 16:45 */         TestWatcher.this.starting(description);
/* 17:   */         try
/* 18:   */         {
/* 19:47 */           base.evaluate();
/* 20:48 */           TestWatcher.this.succeeded(description);
/* 21:   */         }
/* 22:   */         catch (Throwable t)
/* 23:   */         {
/* 24:50 */           TestWatcher.this.failed(t, description);
/* 25:51 */           throw t;
/* 26:   */         }
/* 27:   */         finally
/* 28:   */         {
/* 29:53 */           TestWatcher.this.finished(description);
/* 30:   */         }
/* 31:   */       }
/* 32:   */     };
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void succeeded(Description description) {}
/* 36:   */   
/* 37:   */   public void failed(Throwable e, Description description) {}
/* 38:   */   
/* 39:   */   public void starting(Description description) {}
/* 40:   */   
/* 41:   */   public void finished(Description description) {}
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.TestWatcher
 * JD-Core Version:    0.7.0.1
 */