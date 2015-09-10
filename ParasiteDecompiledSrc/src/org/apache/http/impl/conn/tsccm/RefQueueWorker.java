/*   1:    */ package org.apache.http.impl.conn.tsccm;
/*   2:    */ 
/*   3:    */ import java.lang.ref.Reference;
/*   4:    */ import java.lang.ref.ReferenceQueue;
/*   5:    */ 
/*   6:    */ @Deprecated
/*   7:    */ public class RefQueueWorker
/*   8:    */   implements Runnable
/*   9:    */ {
/*  10:    */   protected final ReferenceQueue<?> refQueue;
/*  11:    */   protected final RefQueueHandler refHandler;
/*  12:    */   protected volatile Thread workerThread;
/*  13:    */   
/*  14:    */   public RefQueueWorker(ReferenceQueue<?> queue, RefQueueHandler handler)
/*  15:    */   {
/*  16: 67 */     if (queue == null) {
/*  17: 68 */       throw new IllegalArgumentException("Queue must not be null.");
/*  18:    */     }
/*  19: 70 */     if (handler == null) {
/*  20: 71 */       throw new IllegalArgumentException("Handler must not be null.");
/*  21:    */     }
/*  22: 74 */     this.refQueue = queue;
/*  23: 75 */     this.refHandler = handler;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void run()
/*  27:    */   {
/*  28: 87 */     if (this.workerThread == null) {
/*  29: 88 */       this.workerThread = Thread.currentThread();
/*  30:    */     }
/*  31: 91 */     while (this.workerThread == Thread.currentThread()) {
/*  32:    */       try
/*  33:    */       {
/*  34: 94 */         Reference<?> ref = this.refQueue.remove();
/*  35: 95 */         this.refHandler.handleReference(ref);
/*  36:    */       }
/*  37:    */       catch (InterruptedException ignore) {}
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void shutdown()
/*  42:    */   {
/*  43:107 */     Thread wt = this.workerThread;
/*  44:108 */     if (wt != null)
/*  45:    */     {
/*  46:109 */       this.workerThread = null;
/*  47:110 */       wt.interrupt();
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53:122 */     return "RefQueueWorker::" + this.workerThread;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.RefQueueWorker
 * JD-Core Version:    0.7.0.1
 */