/*  1:   */ package junit.framework;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Arrays;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.List;
/*  7:   */ import org.junit.runner.Description;
/*  8:   */ import org.junit.runner.notification.Failure;
/*  9:   */ import org.junit.runner.notification.RunListener;
/* 10:   */ import org.junit.runner.notification.RunNotifier;
/* 11:   */ 
/* 12:   */ public class JUnit4TestAdapterCache
/* 13:   */   extends HashMap<Description, Test>
/* 14:   */ {
/* 15:   */   private static final long serialVersionUID = 1L;
/* 16:18 */   private static final JUnit4TestAdapterCache fInstance = new JUnit4TestAdapterCache();
/* 17:   */   
/* 18:   */   public static JUnit4TestAdapterCache getDefault()
/* 19:   */   {
/* 20:21 */     return fInstance;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Test asTest(Description description)
/* 24:   */   {
/* 25:25 */     if (description.isSuite()) {
/* 26:26 */       return createTest(description);
/* 27:   */     }
/* 28:28 */     if (!containsKey(description)) {
/* 29:29 */       put(description, createTest(description));
/* 30:   */     }
/* 31:30 */     return (Test)get(description);
/* 32:   */   }
/* 33:   */   
/* 34:   */   Test createTest(Description description)
/* 35:   */   {
/* 36:35 */     if (description.isTest()) {
/* 37:36 */       return new JUnit4TestCaseFacade(description);
/* 38:   */     }
/* 39:38 */     TestSuite suite = new TestSuite(description.getDisplayName());
/* 40:39 */     for (Description child : description.getChildren()) {
/* 41:40 */       suite.addTest(asTest(child));
/* 42:   */     }
/* 43:41 */     return suite;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public RunNotifier getNotifier(final TestResult result, JUnit4TestAdapter adapter)
/* 47:   */   {
/* 48:47 */     RunNotifier notifier = new RunNotifier();
/* 49:48 */     notifier.addListener(new RunListener()
/* 50:   */     {
/* 51:   */       public void testFailure(Failure failure)
/* 52:   */         throws Exception
/* 53:   */       {
/* 54:51 */         result.addError(JUnit4TestAdapterCache.this.asTest(failure.getDescription()), failure.getException());
/* 55:   */       }
/* 56:   */       
/* 57:   */       public void testFinished(Description description)
/* 58:   */         throws Exception
/* 59:   */       {
/* 60:57 */         result.endTest(JUnit4TestAdapterCache.this.asTest(description));
/* 61:   */       }
/* 62:   */       
/* 63:   */       public void testStarted(Description description)
/* 64:   */         throws Exception
/* 65:   */       {
/* 66:63 */         result.startTest(JUnit4TestAdapterCache.this.asTest(description));
/* 67:   */       }
/* 68:65 */     });
/* 69:66 */     return notifier;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public List<Test> asTestList(Description description)
/* 73:   */   {
/* 74:70 */     if (description.isTest()) {
/* 75:71 */       return Arrays.asList(new Test[] { asTest(description) });
/* 76:   */     }
/* 77:73 */     List<Test> returnThis = new ArrayList();
/* 78:74 */     for (Description child : description.getChildren()) {
/* 79:75 */       returnThis.add(asTest(child));
/* 80:   */     }
/* 81:77 */     return returnThis;
/* 82:   */   }
/* 83:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.JUnit4TestAdapterCache
 * JD-Core Version:    0.7.0.1
 */