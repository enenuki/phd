/*   1:    */ package junit.framework;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class TestResult
/*   9:    */ {
/*  10:    */   protected List<TestFailure> fFailures;
/*  11:    */   protected List<TestFailure> fErrors;
/*  12:    */   protected List<TestListener> fListeners;
/*  13:    */   protected int fRunTests;
/*  14:    */   private boolean fStop;
/*  15:    */   
/*  16:    */   public TestResult()
/*  17:    */   {
/*  18: 25 */     this.fFailures = new ArrayList();
/*  19: 26 */     this.fErrors = new ArrayList();
/*  20: 27 */     this.fListeners = new ArrayList();
/*  21: 28 */     this.fRunTests = 0;
/*  22: 29 */     this.fStop = false;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public synchronized void addError(Test test, Throwable t)
/*  26:    */   {
/*  27: 36 */     this.fErrors.add(new TestFailure(test, t));
/*  28: 37 */     for (TestListener each : cloneListeners()) {
/*  29: 38 */       each.addError(test, t);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public synchronized void addFailure(Test test, AssertionFailedError t)
/*  34:    */   {
/*  35: 45 */     this.fFailures.add(new TestFailure(test, t));
/*  36: 46 */     for (TestListener each : cloneListeners()) {
/*  37: 47 */       each.addFailure(test, t);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public synchronized void addListener(TestListener listener)
/*  42:    */   {
/*  43: 53 */     this.fListeners.add(listener);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public synchronized void removeListener(TestListener listener)
/*  47:    */   {
/*  48: 59 */     this.fListeners.remove(listener);
/*  49:    */   }
/*  50:    */   
/*  51:    */   private synchronized List<TestListener> cloneListeners()
/*  52:    */   {
/*  53: 65 */     List<TestListener> result = new ArrayList();
/*  54: 66 */     result.addAll(this.fListeners);
/*  55: 67 */     return result;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void endTest(Test test)
/*  59:    */   {
/*  60: 73 */     for (TestListener each : cloneListeners()) {
/*  61: 74 */       each.endTest(test);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public synchronized int errorCount()
/*  66:    */   {
/*  67: 80 */     return this.fErrors.size();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public synchronized Enumeration<TestFailure> errors()
/*  71:    */   {
/*  72: 86 */     return Collections.enumeration(this.fErrors);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public synchronized int failureCount()
/*  76:    */   {
/*  77: 94 */     return this.fFailures.size();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public synchronized Enumeration<TestFailure> failures()
/*  81:    */   {
/*  82:100 */     return Collections.enumeration(this.fFailures);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void run(final TestCase test)
/*  86:    */   {
/*  87:107 */     startTest(test);
/*  88:108 */     Protectable p = new Protectable()
/*  89:    */     {
/*  90:    */       public void protect()
/*  91:    */         throws Throwable
/*  92:    */       {
/*  93:110 */         test.runBare();
/*  94:    */       }
/*  95:112 */     };
/*  96:113 */     runProtected(test, p);
/*  97:    */     
/*  98:115 */     endTest(test);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public synchronized int runCount()
/* 102:    */   {
/* 103:121 */     return this.fRunTests;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void runProtected(Test test, Protectable p)
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:128 */       p.protect();
/* 111:    */     }
/* 112:    */     catch (AssertionFailedError e)
/* 113:    */     {
/* 114:131 */       addFailure(test, e);
/* 115:    */     }
/* 116:    */     catch (ThreadDeath e)
/* 117:    */     {
/* 118:134 */       throw e;
/* 119:    */     }
/* 120:    */     catch (Throwable e)
/* 121:    */     {
/* 122:137 */       addError(test, e);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public synchronized boolean shouldStop()
/* 127:    */   {
/* 128:144 */     return this.fStop;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void startTest(Test test)
/* 132:    */   {
/* 133:150 */     int count = test.countTestCases();
/* 134:151 */     synchronized (this)
/* 135:    */     {
/* 136:152 */       this.fRunTests += count;
/* 137:    */     }
/* 138:154 */     for (TestListener each : cloneListeners()) {
/* 139:155 */       each.startTest(test);
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public synchronized void stop()
/* 144:    */   {
/* 145:161 */     this.fStop = true;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public synchronized boolean wasSuccessful()
/* 149:    */   {
/* 150:167 */     return (failureCount() == 0) && (errorCount() == 0);
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.framework.TestResult
 * JD-Core Version:    0.7.0.1
 */