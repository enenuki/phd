/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ class ThreadMonitor
/*   4:    */   implements Runnable
/*   5:    */ {
/*   6:    */   private final Thread thread;
/*   7:    */   private final long timeout;
/*   8:    */   
/*   9:    */   public static Thread start(long timeout)
/*  10:    */   {
/*  11: 55 */     return start(Thread.currentThread(), timeout);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static Thread start(Thread thread, long timeout)
/*  15:    */   {
/*  16: 68 */     Thread monitor = null;
/*  17: 69 */     if (timeout > 0L)
/*  18:    */     {
/*  19: 70 */       ThreadMonitor timout = new ThreadMonitor(thread, timeout);
/*  20: 71 */       monitor = new Thread(timout, ThreadMonitor.class.getSimpleName());
/*  21: 72 */       monitor.setDaemon(true);
/*  22: 73 */       monitor.start();
/*  23:    */     }
/*  24: 75 */     return monitor;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void stop(Thread thread)
/*  28:    */   {
/*  29: 84 */     if (thread != null) {
/*  30: 85 */       thread.interrupt();
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   private ThreadMonitor(Thread thread, long timeout)
/*  35:    */   {
/*  36: 96 */     this.thread = thread;
/*  37: 97 */     this.timeout = timeout;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void run()
/*  41:    */   {
/*  42:    */     try
/*  43:    */     {
/*  44:108 */       Thread.sleep(this.timeout);
/*  45:109 */       this.thread.interrupt();
/*  46:    */     }
/*  47:    */     catch (InterruptedException e) {}
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.ThreadMonitor
 * JD-Core Version:    0.7.0.1
 */