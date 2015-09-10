/*  1:   */ package org.junit.runner;
/*  2:   */ 
/*  3:   */ import org.junit.runner.notification.RunNotifier;
/*  4:   */ 
/*  5:   */ public abstract class Runner
/*  6:   */   implements Describable
/*  7:   */ {
/*  8:   */   public abstract Description getDescription();
/*  9:   */   
/* 10:   */   public abstract void run(RunNotifier paramRunNotifier);
/* 11:   */   
/* 12:   */   public int testCount()
/* 13:   */   {
/* 14:38 */     return getDescription().testCount();
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.Runner
 * JD-Core Version:    0.7.0.1
 */