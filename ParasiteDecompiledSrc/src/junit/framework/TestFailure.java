/*  1:   */ package junit.framework;
/*  2:   */ 
/*  3:   */ import java.io.PrintWriter;
/*  4:   */ import java.io.StringWriter;
/*  5:   */ 
/*  6:   */ public class TestFailure
/*  7:   */ {
/*  8:   */   protected Test fFailedTest;
/*  9:   */   protected Throwable fThrownException;
/* 10:   */   
/* 11:   */   public TestFailure(Test failedTest, Throwable thrownException)
/* 12:   */   {
/* 13:21 */     this.fFailedTest = failedTest;
/* 14:22 */     this.fThrownException = thrownException;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Test failedTest()
/* 18:   */   {
/* 19:28 */     return this.fFailedTest;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Throwable thrownException()
/* 23:   */   {
/* 24:34 */     return this.fThrownException;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String toString()
/* 28:   */   {
/* 29:41 */     StringBuffer buffer = new StringBuffer();
/* 30:42 */     buffer.append(this.fFailedTest + ": " + this.fThrownException.getMessage());
/* 31:43 */     return buffer.toString();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String trace()
/* 35:   */   {
/* 36:46 */     StringWriter stringWriter = new StringWriter();
/* 37:47 */     PrintWriter writer = new PrintWriter(stringWriter);
/* 38:48 */     thrownException().printStackTrace(writer);
/* 39:49 */     StringBuffer buffer = stringWriter.getBuffer();
/* 40:50 */     return buffer.toString();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String exceptionMessage()
/* 44:   */   {
/* 45:53 */     return thrownException().getMessage();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean isFailure()
/* 49:   */   {
/* 50:56 */     return thrownException() instanceof AssertionFailedError;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.TestFailure
 * JD-Core Version:    0.7.0.1
 */