/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.background;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.lang.ref.WeakReference;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ 
/*  12:    */ public class JavaScriptExecutor
/*  13:    */   implements Runnable, Serializable
/*  14:    */ {
/*  15:    */   private transient WeakReference<WebClient> webClient_;
/*  16:    */   private transient List<WeakReference<JavaScriptJobManager>> jobManagerList_;
/*  17: 43 */   private volatile boolean shutdown_ = false;
/*  18: 45 */   private transient Thread eventLoopThread_ = null;
/*  19: 48 */   private static final Log LOG = LogFactory.getLog(JavaScriptExecutor.class);
/*  20:    */   
/*  21:    */   public JavaScriptExecutor(WebClient webClient)
/*  22:    */   {
/*  23: 55 */     this.jobManagerList_ = new ArrayList();
/*  24: 56 */     this.webClient_ = new WeakReference(webClient);
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected void startThreadIfNeeded()
/*  28:    */   {
/*  29: 63 */     if (this.eventLoopThread_ == null)
/*  30:    */     {
/*  31: 64 */       this.eventLoopThread_ = new Thread(this, "JS executor for " + this.webClient_.get());
/*  32: 65 */       this.eventLoopThread_.setDaemon(true);
/*  33: 66 */       this.eventLoopThread_.start();
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void killThread()
/*  38:    */   {
/*  39: 71 */     if (this.eventLoopThread_ == null) {
/*  40: 72 */       return;
/*  41:    */     }
/*  42:    */     try
/*  43:    */     {
/*  44: 75 */       this.eventLoopThread_.interrupt();
/*  45: 76 */       this.eventLoopThread_.join(10000L);
/*  46:    */     }
/*  47:    */     catch (InterruptedException e)
/*  48:    */     {
/*  49: 79 */       LOG.warn("InterruptedException while waiting for the eventLoop thread to join " + e);
/*  50:    */     }
/*  51: 82 */     if (this.eventLoopThread_.isAlive()) {
/*  52: 83 */       LOG.warn("Event loop thread " + this.eventLoopThread_.getName() + " still alive at " + System.currentTimeMillis());
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected synchronized JavaScriptJobManager getJobManagerWithEarliestJob()
/*  57:    */   {
/*  58: 95 */     JavaScriptJobManager javaScriptJobManager = null;
/*  59: 96 */     JavaScriptJob earliestJob = null;
/*  60: 98 */     for (WeakReference<JavaScriptJobManager> jobManagerRef : this.jobManagerList_)
/*  61:    */     {
/*  62: 99 */       JavaScriptJobManager jobManager = (JavaScriptJobManager)jobManagerRef.get();
/*  63:100 */       if (jobManager != null)
/*  64:    */       {
/*  65:101 */         JavaScriptJob newJob = jobManager.getEarliestJob();
/*  66:102 */         if ((newJob != null) && (
/*  67:103 */           (earliestJob == null) || (earliestJob.compareTo(newJob) > 0)))
/*  68:    */         {
/*  69:104 */           earliestJob = newJob;
/*  70:105 */           javaScriptJobManager = jobManager;
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74:110 */     return javaScriptJobManager;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int pumpEventLoop(long timeoutMillis)
/*  78:    */   {
/*  79:120 */     return 0;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void run()
/*  83:    */   {
/*  84:127 */     long sleepInterval = 10L;
/*  85:128 */     while ((!this.shutdown_) && (this.webClient_.get() != null))
/*  86:    */     {
/*  87:129 */       if (LOG.isTraceEnabled()) {
/*  88:130 */         LOG.trace("started finding earliestJob at " + System.currentTimeMillis());
/*  89:    */       }
/*  90:132 */       JavaScriptJobManager jobManager = getJobManagerWithEarliestJob();
/*  91:133 */       if (LOG.isTraceEnabled()) {
/*  92:134 */         LOG.trace("stopped finding earliestJob at " + System.currentTimeMillis());
/*  93:    */       }
/*  94:137 */       if (jobManager != null)
/*  95:    */       {
/*  96:138 */         JavaScriptJob earliestJob = jobManager.getEarliestJob();
/*  97:139 */         if (earliestJob != null)
/*  98:    */         {
/*  99:140 */           long waitTime = earliestJob.getTargetExecutionTime() - System.currentTimeMillis();
/* 100:143 */           if (waitTime < 1L)
/* 101:    */           {
/* 102:145 */             if (LOG.isTraceEnabled()) {
/* 103:146 */               LOG.trace("started executing job at " + System.currentTimeMillis());
/* 104:    */             }
/* 105:148 */             jobManager.runSingleJob(earliestJob);
/* 106:149 */             if (!LOG.isTraceEnabled()) {
/* 107:    */               continue;
/* 108:    */             }
/* 109:150 */             LOG.trace("stopped executing job at " + System.currentTimeMillis()); continue;
/* 110:    */           }
/* 111:    */         }
/* 112:    */       }
/* 113:160 */       if ((this.shutdown_) || (this.webClient_.get() == null)) {
/* 114:    */         break;
/* 115:    */       }
/* 116:    */       try
/* 117:    */       {
/* 118:166 */         Thread.sleep(10L);
/* 119:    */       }
/* 120:    */       catch (InterruptedException e) {}
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public synchronized void addWindow(WebWindow newWindow)
/* 125:    */   {
/* 126:179 */     JavaScriptJobManager jobManager = newWindow.getJobManager();
/* 127:180 */     if ((jobManager != null) && (!contains(jobManager)))
/* 128:    */     {
/* 129:181 */       this.jobManagerList_.add(new WeakReference(jobManager));
/* 130:182 */       startThreadIfNeeded();
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   private boolean contains(JavaScriptJobManager newJobManager)
/* 135:    */   {
/* 136:187 */     for (WeakReference<JavaScriptJobManager> jobManagerRef : this.jobManagerList_) {
/* 137:188 */       if (jobManagerRef.get() == newJobManager) {
/* 138:189 */         return true;
/* 139:    */       }
/* 140:    */     }
/* 141:192 */     return false;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void shutdown()
/* 145:    */   {
/* 146:197 */     this.shutdown_ = true;
/* 147:198 */     killThread();
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor
 * JD-Core Version:    0.7.0.1
 */