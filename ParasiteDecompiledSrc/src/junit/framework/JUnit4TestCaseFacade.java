/*  1:   */ package junit.framework;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Describable;
/*  4:   */ import org.junit.runner.Description;
/*  5:   */ 
/*  6:   */ public class JUnit4TestCaseFacade
/*  7:   */   implements Test, Describable
/*  8:   */ {
/*  9:   */   private final Description fDescription;
/* 10:   */   
/* 11:   */   JUnit4TestCaseFacade(Description description)
/* 12:   */   {
/* 13:13 */     this.fDescription = description;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String toString()
/* 17:   */   {
/* 18:18 */     return getDescription().toString();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int countTestCases()
/* 22:   */   {
/* 23:22 */     return 1;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void run(TestResult result)
/* 27:   */   {
/* 28:26 */     throw new RuntimeException("This test stub created only for informational purposes.");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Description getDescription()
/* 32:   */   {
/* 33:31 */     return this.fDescription;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.JUnit4TestCaseFacade
 * JD-Core Version:    0.7.0.1
 */