/*   1:    */ package org.apache.http.impl.conn.tsccm;
/*   2:    */ 
/*   3:    */ import java.util.Date;
/*   4:    */ import java.util.concurrent.locks.Condition;
/*   5:    */ import org.apache.http.annotation.NotThreadSafe;
/*   6:    */ 
/*   7:    */ @NotThreadSafe
/*   8:    */ public class WaitingThread
/*   9:    */ {
/*  10:    */   private final Condition cond;
/*  11:    */   private final RouteSpecificPool pool;
/*  12:    */   private Thread waiter;
/*  13:    */   private boolean aborted;
/*  14:    */   
/*  15:    */   public WaitingThread(Condition cond, RouteSpecificPool pool)
/*  16:    */   {
/*  17: 74 */     if (cond == null) {
/*  18: 75 */       throw new IllegalArgumentException("Condition must not be null.");
/*  19:    */     }
/*  20: 78 */     this.cond = cond;
/*  21: 79 */     this.pool = pool;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public final Condition getCondition()
/*  25:    */   {
/*  26: 90 */     return this.cond;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public final RouteSpecificPool getPool()
/*  30:    */   {
/*  31:102 */     return this.pool;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final Thread getThread()
/*  35:    */   {
/*  36:113 */     return this.waiter;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean await(Date deadline)
/*  40:    */     throws InterruptedException
/*  41:    */   {
/*  42:141 */     if (this.waiter != null) {
/*  43:142 */       throw new IllegalStateException("A thread is already waiting on this object.\ncaller: " + Thread.currentThread() + "\nwaiter: " + this.waiter);
/*  44:    */     }
/*  45:148 */     if (this.aborted) {
/*  46:149 */       throw new InterruptedException("Operation interrupted");
/*  47:    */     }
/*  48:151 */     this.waiter = Thread.currentThread();
/*  49:    */     
/*  50:153 */     boolean success = false;
/*  51:    */     try
/*  52:    */     {
/*  53:155 */       if (deadline != null)
/*  54:    */       {
/*  55:156 */         success = this.cond.awaitUntil(deadline);
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59:158 */         this.cond.await();
/*  60:159 */         success = true;
/*  61:    */       }
/*  62:161 */       if (this.aborted) {
/*  63:162 */         throw new InterruptedException("Operation interrupted");
/*  64:    */       }
/*  65:    */     }
/*  66:    */     finally
/*  67:    */     {
/*  68:164 */       this.waiter = null;
/*  69:    */     }
/*  70:166 */     return success;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void wakeup()
/*  74:    */   {
/*  75:180 */     if (this.waiter == null) {
/*  76:181 */       throw new IllegalStateException("Nobody waiting on this object.");
/*  77:    */     }
/*  78:187 */     this.cond.signalAll();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void interrupt()
/*  82:    */   {
/*  83:191 */     this.aborted = true;
/*  84:192 */     this.cond.signalAll();
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.WaitingThread
 * JD-Core Version:    0.7.0.1
 */