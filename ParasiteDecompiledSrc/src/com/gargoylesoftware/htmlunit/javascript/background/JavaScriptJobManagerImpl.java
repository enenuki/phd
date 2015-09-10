/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.background;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.Page;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.ObjectInputStream;
/*   7:    */ import java.io.ObjectOutputStream;
/*   8:    */ import java.lang.ref.WeakReference;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.PriorityQueue;
/*  11:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  12:    */ import org.apache.commons.logging.Log;
/*  13:    */ import org.apache.commons.logging.LogFactory;
/*  14:    */ 
/*  15:    */ public class JavaScriptJobManagerImpl
/*  16:    */   implements JavaScriptJobManager
/*  17:    */ {
/*  18:    */   private final transient WeakReference<WebWindow> window_;
/*  19: 57 */   private transient PriorityQueue<JavaScriptJob> scheduledJobsQ_ = new PriorityQueue();
/*  20: 59 */   private transient ArrayList<Integer> cancelledJobs_ = new ArrayList();
/*  21: 61 */   private transient JavaScriptJob currentlyRunningJob_ = null;
/*  22: 64 */   private static final AtomicInteger NEXT_JOB_ID_ = new AtomicInteger(1);
/*  23: 67 */   private static final Log LOG = LogFactory.getLog(JavaScriptJobManagerImpl.class);
/*  24:    */   
/*  25:    */   public JavaScriptJobManagerImpl(WebWindow window)
/*  26:    */   {
/*  27: 75 */     this.window_ = new WeakReference(window);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public synchronized int getJobCount()
/*  31:    */   {
/*  32: 80 */     return this.scheduledJobsQ_.size() + (this.currentlyRunningJob_ != null ? 1 : 0);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int addJob(JavaScriptJob job, Page page)
/*  36:    */   {
/*  37: 85 */     WebWindow w = getWindow();
/*  38: 86 */     if (w == null) {
/*  39: 91 */       return 0;
/*  40:    */     }
/*  41: 93 */     if (w.getEnclosedPage() != page) {
/*  42: 98 */       return 0;
/*  43:    */     }
/*  44:100 */     int id = NEXT_JOB_ID_.getAndIncrement();
/*  45:101 */     job.setId(Integer.valueOf(id));
/*  46:103 */     synchronized (this)
/*  47:    */     {
/*  48:104 */       this.scheduledJobsQ_.add(job);
/*  49:106 */       if (LOG.isDebugEnabled())
/*  50:    */       {
/*  51:107 */         LOG.debug("\twindow is: " + getWindow());
/*  52:108 */         LOG.debug("\tadded job: " + job.toString());
/*  53:109 */         LOG.debug("after adding job to the queue, the queue is: ");
/*  54:110 */         printQueue();
/*  55:    */       }
/*  56:113 */       notify();
/*  57:    */     }
/*  58:116 */     return id;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public synchronized void removeJob(int id)
/*  62:    */   {
/*  63:121 */     for (JavaScriptJob job : this.scheduledJobsQ_)
/*  64:    */     {
/*  65:122 */       int jobId = job.getId().intValue();
/*  66:123 */       if (jobId == id)
/*  67:    */       {
/*  68:124 */         this.scheduledJobsQ_.remove(job);
/*  69:125 */         break;
/*  70:    */       }
/*  71:    */     }
/*  72:128 */     this.cancelledJobs_.add(Integer.valueOf(id));
/*  73:129 */     notify();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public synchronized void stopJob(int id)
/*  77:    */   {
/*  78:134 */     for (JavaScriptJob job : this.scheduledJobsQ_)
/*  79:    */     {
/*  80:135 */       int jobId = job.getId().intValue();
/*  81:136 */       if (jobId == id)
/*  82:    */       {
/*  83:137 */         this.scheduledJobsQ_.remove(job);
/*  84:    */         
/*  85:139 */         break;
/*  86:    */       }
/*  87:    */     }
/*  88:142 */     this.cancelledJobs_.add(Integer.valueOf(id));
/*  89:143 */     notify();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public synchronized void removeAllJobs()
/*  93:    */   {
/*  94:148 */     if (this.currentlyRunningJob_ != null) {
/*  95:149 */       this.cancelledJobs_.add(this.currentlyRunningJob_.getId());
/*  96:    */     }
/*  97:151 */     for (JavaScriptJob job : this.scheduledJobsQ_) {
/*  98:152 */       this.cancelledJobs_.add(job.getId());
/*  99:    */     }
/* 100:154 */     this.scheduledJobsQ_.clear();
/* 101:155 */     notify();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int waitForJobs(long timeoutMillis)
/* 105:    */   {
/* 106:160 */     if (LOG.isDebugEnabled()) {
/* 107:161 */       LOG.debug("Waiting for all jobs to finish (will wait max " + timeoutMillis + " millis).");
/* 108:    */     }
/* 109:163 */     if (timeoutMillis > 0L)
/* 110:    */     {
/* 111:164 */       long now = System.currentTimeMillis();
/* 112:165 */       long end = now + timeoutMillis;
/* 113:166 */       while ((getJobCount() > 0) && (now < end)) {
/* 114:    */         try
/* 115:    */         {
/* 116:168 */           synchronized (this)
/* 117:    */           {
/* 118:169 */             wait(end - now);
/* 119:    */           }
/* 120:174 */           now = System.currentTimeMillis();
/* 121:    */         }
/* 122:    */         catch (InterruptedException e)
/* 123:    */         {
/* 124:177 */           LOG.error("InterruptedException while in waitForJobs", e);
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:181 */     int jobs = getJobCount();
/* 129:182 */     if (LOG.isDebugEnabled()) {
/* 130:183 */       LOG.debug("Finished waiting for all jobs to finish (final job count is " + jobs + ").");
/* 131:    */     }
/* 132:185 */     return jobs;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int waitForJobsStartingBefore(long delayMillis)
/* 136:    */   {
/* 137:190 */     if (LOG.isDebugEnabled()) {
/* 138:191 */       LOG.debug("Waiting for all jobs that have execution time before " + delayMillis + " to finish");
/* 139:    */     }
/* 140:193 */     long targetExecutionTime = System.currentTimeMillis() + delayMillis;
/* 141:194 */     JavaScriptJob earliestJob = getEarliestJob();
/* 142:    */     boolean currentJob;
/* 143:197 */     synchronized (this)
/* 144:    */     {
/* 145:198 */       currentJob = (this.currentlyRunningJob_ != null) && (this.currentlyRunningJob_.getTargetExecutionTime() < targetExecutionTime);
/* 146:    */     }
/* 147:202 */     long interval = Math.max(40L, delayMillis);
/* 148:204 */     while ((currentJob) || ((earliestJob != null) && (earliestJob.getTargetExecutionTime() < targetExecutionTime)))
/* 149:    */     {
/* 150:    */       try
/* 151:    */       {
/* 152:206 */         synchronized (this)
/* 153:    */         {
/* 154:207 */           wait(interval);
/* 155:    */         }
/* 156:    */       }
/* 157:    */       catch (InterruptedException e)
/* 158:    */       {
/* 159:211 */         LOG.error("InterruptedException while in waitForJobsStartingBefore", e);
/* 160:    */       }
/* 161:213 */       earliestJob = getEarliestJob();
/* 162:214 */       synchronized (this)
/* 163:    */       {
/* 164:215 */         currentJob = (this.currentlyRunningJob_ != null) && (this.currentlyRunningJob_.getTargetExecutionTime() < targetExecutionTime);
/* 165:    */       }
/* 166:    */     }
/* 167:219 */     int jobs = getJobCount();
/* 168:220 */     if (LOG.isDebugEnabled()) {
/* 169:221 */       LOG.debug("Finished waiting for all jobs that have target execution time earlier than " + targetExecutionTime + ", final job count is " + jobs);
/* 170:    */     }
/* 171:224 */     return jobs;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public synchronized void shutdown()
/* 175:    */   {
/* 176:229 */     this.scheduledJobsQ_.clear();
/* 177:230 */     notify();
/* 178:    */   }
/* 179:    */   
/* 180:    */   private WebWindow getWindow()
/* 181:    */   {
/* 182:241 */     return (WebWindow)this.window_.get();
/* 183:    */   }
/* 184:    */   
/* 185:    */   private void printQueue()
/* 186:    */   {
/* 187:248 */     if (LOG.isDebugEnabled())
/* 188:    */     {
/* 189:249 */       LOG.debug("------ printing JavaScript job queue -----");
/* 190:250 */       LOG.debug("number of jobs on the queue: " + this.scheduledJobsQ_.size());
/* 191:251 */       for (JavaScriptJob job : this.scheduledJobsQ_)
/* 192:    */       {
/* 193:252 */         LOG.debug("\tJob target execution time:" + job.getTargetExecutionTime());
/* 194:253 */         LOG.debug("\tjob to string: " + job.toString());
/* 195:254 */         LOG.debug("\tjob id: " + job.getId());
/* 196:    */       }
/* 197:256 */       LOG.debug("------------------------------------------");
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public JavaScriptJob getEarliestJob()
/* 202:    */   {
/* 203:264 */     return (JavaScriptJob)this.scheduledJobsQ_.peek();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean runSingleJob(JavaScriptJob givenJob)
/* 207:    */   {
/* 208:271 */     assert (givenJob != null);
/* 209:272 */     JavaScriptJob job = (JavaScriptJob)this.scheduledJobsQ_.peek();
/* 210:273 */     if (job != givenJob) {
/* 211:274 */       return false;
/* 212:    */     }
/* 213:277 */     long currentTime = System.currentTimeMillis();
/* 214:278 */     if (job.getTargetExecutionTime() > currentTime) {
/* 215:279 */       return false;
/* 216:    */     }
/* 217:281 */     synchronized (this)
/* 218:    */     {
/* 219:282 */       if (this.scheduledJobsQ_.remove(job)) {
/* 220:283 */         this.currentlyRunningJob_ = job;
/* 221:    */       }
/* 222:    */     }
/* 223:288 */     boolean isPeriodicJob = job.isPeriodic();
/* 224:289 */     if (isPeriodicJob)
/* 225:    */     {
/* 226:290 */       long jobPeriod = job.getPeriod().longValue();
/* 227:    */       
/* 228:    */ 
/* 229:293 */       long timeDifference = currentTime - job.getTargetExecutionTime();
/* 230:294 */       timeDifference = timeDifference / jobPeriod * jobPeriod + jobPeriod;
/* 231:295 */       job.setTargetExecutionTime(job.getTargetExecutionTime() + timeDifference);
/* 232:298 */       synchronized (this)
/* 233:    */       {
/* 234:299 */         if (!this.cancelledJobs_.contains(job.getId()))
/* 235:    */         {
/* 236:300 */           if (LOG.isDebugEnabled()) {
/* 237:301 */             LOG.debug("Reschedulling job " + job);
/* 238:    */           }
/* 239:303 */           this.scheduledJobsQ_.add(job);
/* 240:304 */           notify();
/* 241:    */         }
/* 242:    */       }
/* 243:    */     }
/* 244:308 */     if (LOG.isDebugEnabled())
/* 245:    */     {
/* 246:309 */       String periodicJob = isPeriodicJob ? "interval " : "";
/* 247:310 */       LOG.debug("Starting " + periodicJob + "job " + job);
/* 248:    */     }
/* 249:    */     try
/* 250:    */     {
/* 251:313 */       job.run();
/* 252:    */     }
/* 253:    */     catch (RuntimeException e)
/* 254:    */     {
/* 255:316 */       LOG.error("Job run failed with unexpected RuntimeException: " + e.getMessage(), e);
/* 256:    */     }
/* 257:    */     finally
/* 258:    */     {
/* 259:319 */       synchronized (this)
/* 260:    */       {
/* 261:320 */         if (job == this.currentlyRunningJob_) {
/* 262:321 */           this.currentlyRunningJob_ = null;
/* 263:    */         }
/* 264:323 */         notify();
/* 265:    */       }
/* 266:    */     }
/* 267:326 */     if (LOG.isDebugEnabled())
/* 268:    */     {
/* 269:327 */       String periodicJob = isPeriodicJob ? "interval " : "";
/* 270:328 */       LOG.debug("Finished " + periodicJob + "job " + job);
/* 271:    */     }
/* 272:330 */     return true;
/* 273:    */   }
/* 274:    */   
/* 275:    */   private void writeObject(ObjectOutputStream out)
/* 276:    */     throws IOException
/* 277:    */   {
/* 278:339 */     out.defaultWriteObject();
/* 279:    */   }
/* 280:    */   
/* 281:    */   private void readObject(ObjectInputStream in)
/* 282:    */     throws IOException, ClassNotFoundException
/* 283:    */   {
/* 284:349 */     in.defaultReadObject();
/* 285:    */     
/* 286:    */ 
/* 287:352 */     this.scheduledJobsQ_ = new PriorityQueue();
/* 288:353 */     this.cancelledJobs_ = new ArrayList();
/* 289:354 */     this.currentlyRunningJob_ = null;
/* 290:    */   }
/* 291:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManagerImpl
 * JD-Core Version:    0.7.0.1
 */