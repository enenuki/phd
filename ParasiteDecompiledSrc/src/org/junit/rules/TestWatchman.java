/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import org.junit.runners.model.FrameworkMethod;
/*  4:   */ import org.junit.runners.model.Statement;
/*  5:   */ 
/*  6:   */ @Deprecated
/*  7:   */ public class TestWatchman
/*  8:   */   implements MethodRule
/*  9:   */ {
/* 10:   */   public Statement apply(final Statement base, final FrameworkMethod method, Object target)
/* 11:   */   {
/* 12:47 */     new Statement()
/* 13:   */     {
/* 14:   */       public void evaluate()
/* 15:   */         throws Throwable
/* 16:   */       {
/* 17:50 */         TestWatchman.this.starting(method);
/* 18:   */         try
/* 19:   */         {
/* 20:52 */           base.evaluate();
/* 21:53 */           TestWatchman.this.succeeded(method);
/* 22:   */         }
/* 23:   */         catch (Throwable t)
/* 24:   */         {
/* 25:55 */           TestWatchman.this.failed(t, method);
/* 26:56 */           throw t;
/* 27:   */         }
/* 28:   */         finally
/* 29:   */         {
/* 30:58 */           TestWatchman.this.finished(method);
/* 31:   */         }
/* 32:   */       }
/* 33:   */     };
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void succeeded(FrameworkMethod method) {}
/* 37:   */   
/* 38:   */   public void failed(Throwable e, FrameworkMethod method) {}
/* 39:   */   
/* 40:   */   public void starting(FrameworkMethod method) {}
/* 41:   */   
/* 42:   */   public void finished(FrameworkMethod method) {}
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.TestWatchman
 * JD-Core Version:    0.7.0.1
 */