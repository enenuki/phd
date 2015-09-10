/*  1:   */ package org.junit.experimental.results;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.junit.runner.Result;
/*  5:   */ import org.junit.runner.notification.Failure;
/*  6:   */ import org.junit.runner.notification.RunListener;
/*  7:   */ 
/*  8:   */ class FailureList
/*  9:   */ {
/* 10:   */   private final List<Failure> failures;
/* 11:   */   
/* 12:   */   public FailureList(List<Failure> failures)
/* 13:   */   {
/* 14:16 */     this.failures = failures;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Result result()
/* 18:   */   {
/* 19:20 */     Result result = new Result();
/* 20:21 */     RunListener listener = result.createListener();
/* 21:22 */     for (Failure failure : this.failures) {
/* 22:   */       try
/* 23:   */       {
/* 24:24 */         listener.testFailure(failure);
/* 25:   */       }
/* 26:   */       catch (Exception e)
/* 27:   */       {
/* 28:26 */         throw new RuntimeException("I can't believe this happened");
/* 29:   */       }
/* 30:   */     }
/* 31:29 */     return result;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.results.FailureList
 * JD-Core Version:    0.7.0.1
 */