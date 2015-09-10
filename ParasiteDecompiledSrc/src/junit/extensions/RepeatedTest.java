/*  1:   */ package junit.extensions;
/*  2:   */ 
/*  3:   */ import junit.framework.Test;
/*  4:   */ import junit.framework.TestResult;
/*  5:   */ 
/*  6:   */ public class RepeatedTest
/*  7:   */   extends TestDecorator
/*  8:   */ {
/*  9:   */   private int fTimesRepeat;
/* 10:   */   
/* 11:   */   public RepeatedTest(Test test, int repeat)
/* 12:   */   {
/* 13:14 */     super(test);
/* 14:15 */     if (repeat < 0) {
/* 15:16 */       throw new IllegalArgumentException("Repetition count must be >= 0");
/* 16:   */     }
/* 17:17 */     this.fTimesRepeat = repeat;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int countTestCases()
/* 21:   */   {
/* 22:22 */     return super.countTestCases() * this.fTimesRepeat;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void run(TestResult result)
/* 26:   */   {
/* 27:27 */     for (int i = 0; i < this.fTimesRepeat; i++)
/* 28:   */     {
/* 29:28 */       if (result.shouldStop()) {
/* 30:   */         break;
/* 31:   */       }
/* 32:30 */       super.run(result);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String toString()
/* 37:   */   {
/* 38:36 */     return super.toString() + "(repeated)";
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.extensions.RepeatedTest
 * JD-Core Version:    0.7.0.1
 */