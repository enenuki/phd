/*   1:    */ package org.junit.runner;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   7:    */ import org.junit.runner.notification.Failure;
/*   8:    */ import org.junit.runner.notification.RunListener;
/*   9:    */ 
/*  10:    */ public class Result
/*  11:    */ {
/*  12:    */   private AtomicInteger fCount;
/*  13:    */   private AtomicInteger fIgnoreCount;
/*  14:    */   private final List<Failure> fFailures;
/*  15:    */   private long fRunTime;
/*  16:    */   private long fStartTime;
/*  17:    */   
/*  18:    */   public Result()
/*  19:    */   {
/*  20: 17 */     this.fCount = new AtomicInteger();
/*  21: 18 */     this.fIgnoreCount = new AtomicInteger();
/*  22: 19 */     this.fFailures = Collections.synchronizedList(new ArrayList());
/*  23: 20 */     this.fRunTime = 0L;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getRunCount()
/*  27:    */   {
/*  28: 27 */     return this.fCount.get();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getFailureCount()
/*  32:    */   {
/*  33: 34 */     return this.fFailures.size();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public long getRunTime()
/*  37:    */   {
/*  38: 41 */     return this.fRunTime;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<Failure> getFailures()
/*  42:    */   {
/*  43: 48 */     return this.fFailures;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIgnoreCount()
/*  47:    */   {
/*  48: 55 */     return this.fIgnoreCount.get();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean wasSuccessful()
/*  52:    */   {
/*  53: 62 */     return getFailureCount() == 0;
/*  54:    */   }
/*  55:    */   
/*  56:    */   private class Listener
/*  57:    */     extends RunListener
/*  58:    */   {
/*  59:    */     private Listener() {}
/*  60:    */     
/*  61:    */     public void testRunStarted(Description description)
/*  62:    */       throws Exception
/*  63:    */     {
/*  64: 68 */       Result.this.fStartTime = System.currentTimeMillis();
/*  65:    */     }
/*  66:    */     
/*  67:    */     public void testRunFinished(Result result)
/*  68:    */       throws Exception
/*  69:    */     {
/*  70: 73 */       long endTime = System.currentTimeMillis();
/*  71: 74 */       Result.access$114(Result.this, endTime - Result.this.fStartTime);
/*  72:    */     }
/*  73:    */     
/*  74:    */     public void testFinished(Description description)
/*  75:    */       throws Exception
/*  76:    */     {
/*  77: 79 */       Result.this.fCount.getAndIncrement();
/*  78:    */     }
/*  79:    */     
/*  80:    */     public void testFailure(Failure failure)
/*  81:    */       throws Exception
/*  82:    */     {
/*  83: 84 */       Result.this.fFailures.add(failure);
/*  84:    */     }
/*  85:    */     
/*  86:    */     public void testIgnored(Description description)
/*  87:    */       throws Exception
/*  88:    */     {
/*  89: 89 */       Result.this.fIgnoreCount.getAndIncrement();
/*  90:    */     }
/*  91:    */     
/*  92:    */     public void testAssumptionFailure(Failure failure) {}
/*  93:    */   }
/*  94:    */   
/*  95:    */   public RunListener createListener()
/*  96:    */   {
/*  97:102 */     return new Listener(null);
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.Result
 * JD-Core Version:    0.7.0.1
 */