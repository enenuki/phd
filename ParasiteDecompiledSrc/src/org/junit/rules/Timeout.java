/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import org.junit.internal.runners.statements.FailOnTimeout;
/*  4:   */ import org.junit.runner.Description;
/*  5:   */ import org.junit.runners.model.Statement;
/*  6:   */ 
/*  7:   */ public class Timeout
/*  8:   */   extends TestRule
/*  9:   */ {
/* 10:   */   private final int fMillis;
/* 11:   */   
/* 12:   */   public Timeout(int millis)
/* 13:   */   {
/* 14:43 */     this.fMillis = millis;
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected Statement apply(Statement base, Description description)
/* 18:   */   {
/* 19:48 */     return new FailOnTimeout(base, this.fMillis);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.Timeout
 * JD-Core Version:    0.7.0.1
 */