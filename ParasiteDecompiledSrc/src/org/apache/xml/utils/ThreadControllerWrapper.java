/*  1:   */ package org.apache.xml.utils;
/*  2:   */ 
/*  3:   */ public class ThreadControllerWrapper
/*  4:   */ {
/*  5:31 */   private static ThreadController m_tpool = new ThreadController();
/*  6:   */   
/*  7:   */   public static Thread runThread(Runnable runnable, int priority)
/*  8:   */   {
/*  9:35 */     return m_tpool.run(runnable, priority);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static void waitThread(Thread worker, Runnable task)
/* 13:   */     throws InterruptedException
/* 14:   */   {
/* 15:41 */     m_tpool.waitThread(worker, task);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static class ThreadController
/* 19:   */   {
/* 20:   */     public Thread run(Runnable task, int priority)
/* 21:   */     {
/* 22:69 */       Thread t = new Thread(task);
/* 23:   */       
/* 24:71 */       t.start();
/* 25:   */       
/* 26:   */ 
/* 27:   */ 
/* 28:75 */       return t;
/* 29:   */     }
/* 30:   */     
/* 31:   */     public void waitThread(Thread worker, Runnable task)
/* 32:   */       throws InterruptedException
/* 33:   */     {
/* 34:92 */       worker.join();
/* 35:   */     }
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.ThreadControllerWrapper
 * JD-Core Version:    0.7.0.1
 */