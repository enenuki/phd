/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ public class TimeoutError
/*  4:   */   extends Error
/*  5:   */ {
/*  6:   */   private final long allowedTime_;
/*  7:   */   private final long executionTime_;
/*  8:   */   
/*  9:   */   TimeoutError(long allowedTime, long executionTime)
/* 10:   */   {
/* 11:29 */     super("Javascript execution takes too long (allowed: " + allowedTime + ", already elapsed: " + executionTime + ")");
/* 12:   */     
/* 13:31 */     this.allowedTime_ = allowedTime;
/* 14:32 */     this.executionTime_ = executionTime;
/* 15:   */   }
/* 16:   */   
/* 17:   */   long getAllowedTime()
/* 18:   */   {
/* 19:40 */     return this.allowedTime_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   long getExecutionTime()
/* 23:   */   {
/* 24:48 */     return this.executionTime_;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.TimeoutError
 * JD-Core Version:    0.7.0.1
 */