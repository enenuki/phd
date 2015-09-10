/*  1:   */ package junit.extensions;
/*  2:   */ 
/*  3:   */ import junit.framework.Assert;
/*  4:   */ import junit.framework.Test;
/*  5:   */ import junit.framework.TestResult;
/*  6:   */ 
/*  7:   */ public class TestDecorator
/*  8:   */   extends Assert
/*  9:   */   implements Test
/* 10:   */ {
/* 11:   */   protected Test fTest;
/* 12:   */   
/* 13:   */   public TestDecorator(Test test)
/* 14:   */   {
/* 15:17 */     this.fTest = test;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void basicRun(TestResult result)
/* 19:   */   {
/* 20:24 */     this.fTest.run(result);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int countTestCases()
/* 24:   */   {
/* 25:28 */     return this.fTest.countTestCases();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void run(TestResult result)
/* 29:   */   {
/* 30:32 */     basicRun(result);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String toString()
/* 34:   */   {
/* 35:37 */     return this.fTest.toString();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Test getTest()
/* 39:   */   {
/* 40:41 */     return this.fTest;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.extensions.TestDecorator
 * JD-Core Version:    0.7.0.1
 */