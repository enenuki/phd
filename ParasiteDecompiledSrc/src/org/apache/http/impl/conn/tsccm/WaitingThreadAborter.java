/*  1:   */ package org.apache.http.impl.conn.tsccm;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.NotThreadSafe;
/*  4:   */ 
/*  5:   */ @NotThreadSafe
/*  6:   */ public class WaitingThreadAborter
/*  7:   */ {
/*  8:   */   private WaitingThread waitingThread;
/*  9:   */   private boolean aborted;
/* 10:   */   
/* 11:   */   public void abort()
/* 12:   */   {
/* 13:49 */     this.aborted = true;
/* 14:51 */     if (this.waitingThread != null) {
/* 15:52 */       this.waitingThread.interrupt();
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setWaitingThread(WaitingThread waitingThread)
/* 20:   */   {
/* 21:63 */     this.waitingThread = waitingThread;
/* 22:64 */     if (this.aborted) {
/* 23:65 */       waitingThread.interrupt();
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.WaitingThreadAborter
 * JD-Core Version:    0.7.0.1
 */