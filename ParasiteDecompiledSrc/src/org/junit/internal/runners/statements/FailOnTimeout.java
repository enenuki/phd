/*  1:   */ package org.junit.internal.runners.statements;
/*  2:   */ 
/*  3:   */ import org.junit.runners.model.Statement;
/*  4:   */ 
/*  5:   */ public class FailOnTimeout
/*  6:   */   extends Statement
/*  7:   */ {
/*  8:   */   private Statement fNext;
/*  9:   */   private final long fTimeout;
/* 10:13 */   private boolean fFinished = false;
/* 11:15 */   private Throwable fThrown = null;
/* 12:   */   
/* 13:   */   public FailOnTimeout(Statement next, long timeout)
/* 14:   */   {
/* 15:18 */     this.fNext = next;
/* 16:19 */     this.fTimeout = timeout;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void evaluate()
/* 20:   */     throws Throwable
/* 21:   */   {
/* 22:24 */     Thread thread = new Thread()
/* 23:   */     {
/* 24:   */       public void run()
/* 25:   */       {
/* 26:   */         try
/* 27:   */         {
/* 28:28 */           FailOnTimeout.this.fNext.evaluate();
/* 29:29 */           FailOnTimeout.this.fFinished = true;
/* 30:   */         }
/* 31:   */         catch (Throwable e)
/* 32:   */         {
/* 33:31 */           FailOnTimeout.this.fThrown = e;
/* 34:   */         }
/* 35:   */       }
/* 36:34 */     };
/* 37:35 */     thread.start();
/* 38:36 */     thread.join(this.fTimeout);
/* 39:37 */     if (this.fFinished) {
/* 40:38 */       return;
/* 41:   */     }
/* 42:39 */     if (this.fThrown != null) {
/* 43:40 */       throw this.fThrown;
/* 44:   */     }
/* 45:41 */     Exception exception = new Exception(String.format("test timed out after %d milliseconds", new Object[] { Long.valueOf(this.fTimeout) }));
/* 46:   */     
/* 47:43 */     exception.setStackTrace(thread.getStackTrace());
/* 48:44 */     throw exception;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.statements.FailOnTimeout
 * JD-Core Version:    0.7.0.1
 */