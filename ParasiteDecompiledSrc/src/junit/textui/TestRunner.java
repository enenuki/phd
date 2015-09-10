/*   1:    */ package junit.textui;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import junit.framework.Test;
/*   6:    */ import junit.framework.TestCase;
/*   7:    */ import junit.framework.TestResult;
/*   8:    */ import junit.framework.TestSuite;
/*   9:    */ import junit.runner.BaseTestRunner;
/*  10:    */ import junit.runner.Version;
/*  11:    */ 
/*  12:    */ public class TestRunner
/*  13:    */   extends BaseTestRunner
/*  14:    */ {
/*  15:    */   private ResultPrinter fPrinter;
/*  16:    */   public static final int SUCCESS_EXIT = 0;
/*  17:    */   public static final int FAILURE_EXIT = 1;
/*  18:    */   public static final int EXCEPTION_EXIT = 2;
/*  19:    */   
/*  20:    */   public TestRunner()
/*  21:    */   {
/*  22: 41 */     this(System.out);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public TestRunner(PrintStream writer)
/*  26:    */   {
/*  27: 48 */     this(new ResultPrinter(writer));
/*  28:    */   }
/*  29:    */   
/*  30:    */   public TestRunner(ResultPrinter printer)
/*  31:    */   {
/*  32: 55 */     this.fPrinter = printer;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void run(Class<? extends TestCase> testClass)
/*  36:    */   {
/*  37: 62 */     run(new TestSuite(testClass));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static TestResult run(Test test)
/*  41:    */   {
/*  42: 76 */     TestRunner runner = new TestRunner();
/*  43: 77 */     return runner.doRun(test);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void runAndWait(Test suite)
/*  47:    */   {
/*  48: 85 */     TestRunner aTestRunner = new TestRunner();
/*  49: 86 */     aTestRunner.doRun(suite, true);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void testFailed(int status, Test test, Throwable t) {}
/*  53:    */   
/*  54:    */   public void testStarted(String testName) {}
/*  55:    */   
/*  56:    */   public void testEnded(String testName) {}
/*  57:    */   
/*  58:    */   protected TestResult createTestResult()
/*  59:    */   {
/*  60:105 */     return new TestResult();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public TestResult doRun(Test test)
/*  64:    */   {
/*  65:109 */     return doRun(test, false);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public TestResult doRun(Test suite, boolean wait)
/*  69:    */   {
/*  70:113 */     TestResult result = createTestResult();
/*  71:114 */     result.addListener(this.fPrinter);
/*  72:115 */     long startTime = System.currentTimeMillis();
/*  73:116 */     suite.run(result);
/*  74:117 */     long endTime = System.currentTimeMillis();
/*  75:118 */     long runTime = endTime - startTime;
/*  76:119 */     this.fPrinter.print(result, runTime);
/*  77:    */     
/*  78:121 */     pause(wait);
/*  79:122 */     return result;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void pause(boolean wait)
/*  83:    */   {
/*  84:126 */     if (!wait) {
/*  85:126 */       return;
/*  86:    */     }
/*  87:127 */     this.fPrinter.printWaitPrompt();
/*  88:    */     try
/*  89:    */     {
/*  90:129 */       System.in.read();
/*  91:    */     }
/*  92:    */     catch (Exception e) {}
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static void main(String[] args)
/*  96:    */   {
/*  97:136 */     TestRunner aTestRunner = new TestRunner();
/*  98:    */     try
/*  99:    */     {
/* 100:138 */       TestResult r = aTestRunner.start(args);
/* 101:139 */       if (!r.wasSuccessful()) {
/* 102:140 */         System.exit(1);
/* 103:    */       }
/* 104:141 */       System.exit(0);
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:143 */       System.err.println(e.getMessage());
/* 109:144 */       System.exit(2);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public TestResult start(String[] args)
/* 114:    */     throws Exception
/* 115:    */   {
/* 116:153 */     String testCase = "";
/* 117:154 */     String method = "";
/* 118:155 */     boolean wait = false;
/* 119:157 */     for (int i = 0; i < args.length; i++) {
/* 120:158 */       if (args[i].equals("-wait"))
/* 121:    */       {
/* 122:159 */         wait = true;
/* 123:    */       }
/* 124:160 */       else if (args[i].equals("-c"))
/* 125:    */       {
/* 126:161 */         testCase = extractClassName(args[(++i)]);
/* 127:    */       }
/* 128:162 */       else if (args[i].equals("-m"))
/* 129:    */       {
/* 130:163 */         String arg = args[(++i)];
/* 131:164 */         int lastIndex = arg.lastIndexOf('.');
/* 132:165 */         testCase = arg.substring(0, lastIndex);
/* 133:166 */         method = arg.substring(lastIndex + 1);
/* 134:    */       }
/* 135:167 */       else if (args[i].equals("-v"))
/* 136:    */       {
/* 137:168 */         System.err.println("JUnit " + Version.id() + " by Kent Beck and Erich Gamma");
/* 138:    */       }
/* 139:    */       else
/* 140:    */       {
/* 141:170 */         testCase = args[i];
/* 142:    */       }
/* 143:    */     }
/* 144:173 */     if (testCase.equals("")) {
/* 145:174 */       throw new Exception("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");
/* 146:    */     }
/* 147:    */     try
/* 148:    */     {
/* 149:177 */       if (!method.equals("")) {
/* 150:178 */         return runSingleMethod(testCase, method, wait);
/* 151:    */       }
/* 152:179 */       Test suite = getTest(testCase);
/* 153:180 */       return doRun(suite, wait);
/* 154:    */     }
/* 155:    */     catch (Exception e)
/* 156:    */     {
/* 157:182 */       throw new Exception("Could not create and run test suite: " + e);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   protected TestResult runSingleMethod(String testCase, String method, boolean wait)
/* 162:    */     throws Exception
/* 163:    */   {
/* 164:187 */     Class<? extends TestCase> testClass = loadSuiteClass(testCase).asSubclass(TestCase.class);
/* 165:188 */     Test test = TestSuite.createTest(testClass, method);
/* 166:189 */     return doRun(test, wait);
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected void runFailed(String message)
/* 170:    */   {
/* 171:194 */     System.err.println(message);
/* 172:195 */     System.exit(1);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setPrinter(ResultPrinter printer)
/* 176:    */   {
/* 177:199 */     this.fPrinter = printer;
/* 178:    */   }
/* 179:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.textui.TestRunner
 * JD-Core Version:    0.7.0.1
 */