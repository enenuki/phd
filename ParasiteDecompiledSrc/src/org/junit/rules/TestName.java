/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Description;
/*  4:   */ 
/*  5:   */ public class TestName
/*  6:   */   extends TestWatcher
/*  7:   */ {
/*  8:   */   private String fName;
/*  9:   */   
/* 10:   */   public void starting(Description d)
/* 11:   */   {
/* 12:30 */     this.fName = d.getMethodName();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getMethodName()
/* 16:   */   {
/* 17:37 */     return this.fName;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.TestName
 * JD-Core Version:    0.7.0.1
 */