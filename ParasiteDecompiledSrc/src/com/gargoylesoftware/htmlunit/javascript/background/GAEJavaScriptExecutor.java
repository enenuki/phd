/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.background;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.WebClient;
/*  4:   */ 
/*  5:   */ public class GAEJavaScriptExecutor
/*  6:   */   extends JavaScriptExecutor
/*  7:   */ {
/*  8:   */   public GAEJavaScriptExecutor(WebClient webClient)
/*  9:   */   {
/* 10:34 */     super(webClient);
/* 11:   */   }
/* 12:   */   
/* 13:   */   protected void startThreadIfNeeded() {}
/* 14:   */   
/* 15:   */   public int pumpEventLoop(long timeoutMillis)
/* 16:   */   {
/* 17:52 */     int count = 0;
/* 18:53 */     long currentTime = System.currentTimeMillis();
/* 19:54 */     long expirationTime = currentTime + timeoutMillis;
/* 20:56 */     while (currentTime < expirationTime)
/* 21:   */     {
/* 22:57 */       JavaScriptJobManager jobManager = getJobManagerWithEarliestJob();
/* 23:58 */       if (jobManager == null) {
/* 24:   */         break;
/* 25:   */       }
/* 26:62 */       JavaScriptJob earliestJob = jobManager.getEarliestJob();
/* 27:63 */       if (earliestJob == null) {
/* 28:   */         break;
/* 29:   */       }
/* 30:66 */       if (expirationTime < earliestJob.getTargetExecutionTime()) {
/* 31:   */         break;
/* 32:   */       }
/* 33:71 */       long sleepTime = earliestJob.getTargetExecutionTime() - currentTime;
/* 34:72 */       if (sleepTime > 0L) {
/* 35:   */         try
/* 36:   */         {
/* 37:74 */           Thread.sleep(sleepTime);
/* 38:   */         }
/* 39:   */         catch (InterruptedException e)
/* 40:   */         {
/* 41:77 */           e.printStackTrace();
/* 42:   */         }
/* 43:   */       }
/* 44:80 */       boolean ran = jobManager.runSingleJob(earliestJob);
/* 45:81 */       if (ran) {
/* 46:82 */         count++;
/* 47:   */       }
/* 48:84 */       currentTime = System.currentTimeMillis();
/* 49:   */     }
/* 50:86 */     return count;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.background.GAEJavaScriptExecutor
 * JD-Core Version:    0.7.0.1
 */