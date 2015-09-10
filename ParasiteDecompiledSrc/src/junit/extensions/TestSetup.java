/*  1:   */ package junit.extensions;
/*  2:   */ 
/*  3:   */ import junit.framework.Protectable;
/*  4:   */ import junit.framework.Test;
/*  5:   */ import junit.framework.TestResult;
/*  6:   */ 
/*  7:   */ public class TestSetup
/*  8:   */   extends TestDecorator
/*  9:   */ {
/* 10:   */   public TestSetup(Test test)
/* 11:   */   {
/* 12:15 */     super(test);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void run(final TestResult result)
/* 16:   */   {
/* 17:20 */     Protectable p = new Protectable()
/* 18:   */     {
/* 19:   */       public void protect()
/* 20:   */         throws Exception
/* 21:   */       {
/* 22:22 */         TestSetup.this.setUp();
/* 23:23 */         TestSetup.this.basicRun(result);
/* 24:24 */         TestSetup.this.tearDown();
/* 25:   */       }
/* 26:26 */     };
/* 27:27 */     result.runProtected(this, p);
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void setUp()
/* 31:   */     throws Exception
/* 32:   */   {}
/* 33:   */   
/* 34:   */   protected void tearDown()
/* 35:   */     throws Exception
/* 36:   */   {}
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.extensions.TestSetup
 * JD-Core Version:    0.7.0.1
 */