/*   1:    */ package junit.textui;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.text.NumberFormat;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import junit.framework.AssertionFailedError;
/*   7:    */ import junit.framework.Test;
/*   8:    */ import junit.framework.TestFailure;
/*   9:    */ import junit.framework.TestListener;
/*  10:    */ import junit.framework.TestResult;
/*  11:    */ import junit.runner.BaseTestRunner;
/*  12:    */ 
/*  13:    */ public class ResultPrinter
/*  14:    */   implements TestListener
/*  15:    */ {
/*  16:    */   PrintStream fWriter;
/*  17: 17 */   int fColumn = 0;
/*  18:    */   
/*  19:    */   public ResultPrinter(PrintStream writer)
/*  20:    */   {
/*  21: 20 */     this.fWriter = writer;
/*  22:    */   }
/*  23:    */   
/*  24:    */   synchronized void print(TestResult result, long runTime)
/*  25:    */   {
/*  26: 27 */     printHeader(runTime);
/*  27: 28 */     printErrors(result);
/*  28: 29 */     printFailures(result);
/*  29: 30 */     printFooter(result);
/*  30:    */   }
/*  31:    */   
/*  32:    */   void printWaitPrompt()
/*  33:    */   {
/*  34: 34 */     getWriter().println();
/*  35: 35 */     getWriter().println("<RETURN> to continue");
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void printHeader(long runTime)
/*  39:    */   {
/*  40: 42 */     getWriter().println();
/*  41: 43 */     getWriter().println("Time: " + elapsedTimeAsString(runTime));
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void printErrors(TestResult result)
/*  45:    */   {
/*  46: 47 */     printDefects(result.errors(), result.errorCount(), "error");
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void printFailures(TestResult result)
/*  50:    */   {
/*  51: 51 */     printDefects(result.failures(), result.failureCount(), "failure");
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected void printDefects(Enumeration<TestFailure> booBoos, int count, String type)
/*  55:    */   {
/*  56: 55 */     if (count == 0) {
/*  57: 55 */       return;
/*  58:    */     }
/*  59: 56 */     if (count == 1) {
/*  60: 57 */       getWriter().println("There was " + count + " " + type + ":");
/*  61:    */     } else {
/*  62: 59 */       getWriter().println("There were " + count + " " + type + "s:");
/*  63:    */     }
/*  64: 60 */     for (int i = 1; booBoos.hasMoreElements(); i++) {
/*  65: 61 */       printDefect((TestFailure)booBoos.nextElement(), i);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void printDefect(TestFailure booBoo, int count)
/*  70:    */   {
/*  71: 66 */     printDefectHeader(booBoo, count);
/*  72: 67 */     printDefectTrace(booBoo);
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected void printDefectHeader(TestFailure booBoo, int count)
/*  76:    */   {
/*  77: 73 */     getWriter().print(count + ") " + booBoo.failedTest());
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void printDefectTrace(TestFailure booBoo)
/*  81:    */   {
/*  82: 77 */     getWriter().print(BaseTestRunner.getFilteredTrace(booBoo.trace()));
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void printFooter(TestResult result)
/*  86:    */   {
/*  87: 81 */     if (result.wasSuccessful())
/*  88:    */     {
/*  89: 82 */       getWriter().println();
/*  90: 83 */       getWriter().print("OK");
/*  91: 84 */       getWriter().println(" (" + result.runCount() + " test" + (result.runCount() == 1 ? "" : "s") + ")");
/*  92:    */     }
/*  93:    */     else
/*  94:    */     {
/*  95: 87 */       getWriter().println();
/*  96: 88 */       getWriter().println("FAILURES!!!");
/*  97: 89 */       getWriter().println("Tests run: " + result.runCount() + ",  Failures: " + result.failureCount() + ",  Errors: " + result.errorCount());
/*  98:    */     }
/*  99: 93 */     getWriter().println();
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected String elapsedTimeAsString(long runTime)
/* 103:    */   {
/* 104:102 */     return NumberFormat.getInstance().format(runTime / 1000.0D);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public PrintStream getWriter()
/* 108:    */   {
/* 109:106 */     return this.fWriter;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void addError(Test test, Throwable t)
/* 113:    */   {
/* 114:112 */     getWriter().print("E");
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void addFailure(Test test, AssertionFailedError t)
/* 118:    */   {
/* 119:119 */     getWriter().print("F");
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void endTest(Test test) {}
/* 123:    */   
/* 124:    */   public void startTest(Test test)
/* 125:    */   {
/* 126:132 */     getWriter().print(".");
/* 127:133 */     if (this.fColumn++ >= 40)
/* 128:    */     {
/* 129:134 */       getWriter().println();
/* 130:135 */       this.fColumn = 0;
/* 131:    */     }
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.textui.ResultPrinter
 * JD-Core Version:    0.7.0.1
 */