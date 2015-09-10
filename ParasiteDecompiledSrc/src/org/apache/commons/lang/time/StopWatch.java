/*   1:    */ package org.apache.commons.lang.time;
/*   2:    */ 
/*   3:    */ public class StopWatch
/*   4:    */ {
/*   5:    */   private static final int STATE_UNSTARTED = 0;
/*   6:    */   private static final int STATE_RUNNING = 1;
/*   7:    */   private static final int STATE_STOPPED = 2;
/*   8:    */   private static final int STATE_SUSPENDED = 3;
/*   9:    */   private static final int STATE_UNSPLIT = 10;
/*  10:    */   private static final int STATE_SPLIT = 11;
/*  11: 78 */   private int runningState = 0;
/*  12: 83 */   private int splitState = 10;
/*  13: 88 */   private long startTime = -1L;
/*  14: 93 */   private long stopTime = -1L;
/*  15:    */   
/*  16:    */   public void start()
/*  17:    */   {
/*  18:117 */     if (this.runningState == 2) {
/*  19:118 */       throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
/*  20:    */     }
/*  21:120 */     if (this.runningState != 0) {
/*  22:121 */       throw new IllegalStateException("Stopwatch already started. ");
/*  23:    */     }
/*  24:123 */     this.stopTime = -1L;
/*  25:124 */     this.startTime = System.currentTimeMillis();
/*  26:125 */     this.runningState = 1;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void stop()
/*  30:    */   {
/*  31:141 */     if ((this.runningState != 1) && (this.runningState != 3)) {
/*  32:142 */       throw new IllegalStateException("Stopwatch is not running. ");
/*  33:    */     }
/*  34:144 */     if (this.runningState == 1) {
/*  35:145 */       this.stopTime = System.currentTimeMillis();
/*  36:    */     }
/*  37:147 */     this.runningState = 2;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void reset()
/*  41:    */   {
/*  42:160 */     this.runningState = 0;
/*  43:161 */     this.splitState = 10;
/*  44:162 */     this.startTime = -1L;
/*  45:163 */     this.stopTime = -1L;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void split()
/*  49:    */   {
/*  50:180 */     if (this.runningState != 1) {
/*  51:181 */       throw new IllegalStateException("Stopwatch is not running. ");
/*  52:    */     }
/*  53:183 */     this.stopTime = System.currentTimeMillis();
/*  54:184 */     this.splitState = 11;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void unsplit()
/*  58:    */   {
/*  59:201 */     if (this.splitState != 11) {
/*  60:202 */       throw new IllegalStateException("Stopwatch has not been split. ");
/*  61:    */     }
/*  62:204 */     this.stopTime = -1L;
/*  63:205 */     this.splitState = 10;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void suspend()
/*  67:    */   {
/*  68:222 */     if (this.runningState != 1) {
/*  69:223 */       throw new IllegalStateException("Stopwatch must be running to suspend. ");
/*  70:    */     }
/*  71:225 */     this.stopTime = System.currentTimeMillis();
/*  72:226 */     this.runningState = 3;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void resume()
/*  76:    */   {
/*  77:243 */     if (this.runningState != 3) {
/*  78:244 */       throw new IllegalStateException("Stopwatch must be suspended to resume. ");
/*  79:    */     }
/*  80:246 */     this.startTime += System.currentTimeMillis() - this.stopTime;
/*  81:247 */     this.stopTime = -1L;
/*  82:248 */     this.runningState = 1;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public long getTime()
/*  86:    */   {
/*  87:264 */     if ((this.runningState == 2) || (this.runningState == 3)) {
/*  88:265 */       return this.stopTime - this.startTime;
/*  89:    */     }
/*  90:266 */     if (this.runningState == 0) {
/*  91:267 */       return 0L;
/*  92:    */     }
/*  93:268 */     if (this.runningState == 1) {
/*  94:269 */       return System.currentTimeMillis() - this.startTime;
/*  95:    */     }
/*  96:271 */     throw new RuntimeException("Illegal running state has occured. ");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public long getSplitTime()
/* 100:    */   {
/* 101:290 */     if (this.splitState != 11) {
/* 102:291 */       throw new IllegalStateException("Stopwatch must be split to get the split time. ");
/* 103:    */     }
/* 104:293 */     return this.stopTime - this.startTime;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public long getStartTime()
/* 108:    */   {
/* 109:305 */     if (this.runningState == 0) {
/* 110:306 */       throw new IllegalStateException("Stopwatch has not been started");
/* 111:    */     }
/* 112:308 */     return this.startTime;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String toString()
/* 116:    */   {
/* 117:323 */     return DurationFormatUtils.formatDurationHMS(getTime());
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String toSplitString()
/* 121:    */   {
/* 122:339 */     return DurationFormatUtils.formatDurationHMS(getSplitTime());
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.time.StopWatch
 * JD-Core Version:    0.7.0.1
 */