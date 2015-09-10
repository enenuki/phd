/*   1:    */ package org.apache.commons.io.monitor;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.concurrent.CopyOnWriteArrayList;
/*   5:    */ import java.util.concurrent.ThreadFactory;
/*   6:    */ 
/*   7:    */ public final class FileAlterationMonitor
/*   8:    */   implements Runnable
/*   9:    */ {
/*  10:    */   private final long interval;
/*  11: 34 */   private final List<FileAlterationObserver> observers = new CopyOnWriteArrayList();
/*  12: 35 */   private Thread thread = null;
/*  13:    */   private ThreadFactory threadFactory;
/*  14: 37 */   private volatile boolean running = false;
/*  15:    */   
/*  16:    */   public FileAlterationMonitor()
/*  17:    */   {
/*  18: 43 */     this(10000L);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public FileAlterationMonitor(long interval)
/*  22:    */   {
/*  23: 53 */     this.interval = interval;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public FileAlterationMonitor(long interval, FileAlterationObserver... observers)
/*  27:    */   {
/*  28: 64 */     this(interval);
/*  29: 65 */     if (observers != null) {
/*  30: 66 */       for (FileAlterationObserver observer : observers) {
/*  31: 67 */         addObserver(observer);
/*  32:    */       }
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public long getInterval()
/*  37:    */   {
/*  38: 78 */     return this.interval;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public synchronized void setThreadFactory(ThreadFactory threadFactory)
/*  42:    */   {
/*  43: 87 */     this.threadFactory = threadFactory;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void addObserver(FileAlterationObserver observer)
/*  47:    */   {
/*  48: 96 */     if (observer != null) {
/*  49: 97 */       this.observers.add(observer);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void removeObserver(FileAlterationObserver observer)
/*  54:    */   {
/*  55:107 */     while ((observer != null) && 
/*  56:108 */       (this.observers.remove(observer))) {}
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Iterable<FileAlterationObserver> getObservers()
/*  60:    */   {
/*  61:120 */     return this.observers;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public synchronized void start()
/*  65:    */     throws Exception
/*  66:    */   {
/*  67:129 */     if (this.running) {
/*  68:130 */       throw new IllegalStateException("Monitor is already running");
/*  69:    */     }
/*  70:132 */     for (FileAlterationObserver observer : this.observers) {
/*  71:133 */       observer.initialize();
/*  72:    */     }
/*  73:135 */     this.running = true;
/*  74:136 */     if (this.threadFactory != null) {
/*  75:137 */       this.thread = this.threadFactory.newThread(this);
/*  76:    */     } else {
/*  77:139 */       this.thread = new Thread(this);
/*  78:    */     }
/*  79:141 */     this.thread.start();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public synchronized void stop()
/*  83:    */     throws Exception
/*  84:    */   {
/*  85:150 */     if (!this.running) {
/*  86:151 */       throw new IllegalStateException("Monitor is not running");
/*  87:    */     }
/*  88:153 */     this.running = false;
/*  89:    */     try
/*  90:    */     {
/*  91:155 */       this.thread.join(this.interval);
/*  92:    */     }
/*  93:    */     catch (InterruptedException e)
/*  94:    */     {
/*  95:157 */       Thread.currentThread().interrupt();
/*  96:    */     }
/*  97:159 */     for (FileAlterationObserver observer : this.observers) {
/*  98:160 */       observer.destroy();
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void run()
/* 103:    */   {
/* 104:168 */     while (this.running)
/* 105:    */     {
/* 106:169 */       for (FileAlterationObserver observer : this.observers) {
/* 107:170 */         observer.checkAndNotify();
/* 108:    */       }
/* 109:172 */       if (!this.running) {
/* 110:    */         break;
/* 111:    */       }
/* 112:    */       try
/* 113:    */       {
/* 114:176 */         Thread.sleep(this.interval);
/* 115:    */       }
/* 116:    */       catch (InterruptedException ignored) {}
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.monitor.FileAlterationMonitor
 * JD-Core Version:    0.7.0.1
 */