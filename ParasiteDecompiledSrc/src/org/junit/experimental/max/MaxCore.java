/*   1:    */ package org.junit.experimental.max;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.lang.annotation.Annotation;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.List;
/*   8:    */ import junit.framework.TestSuite;
/*   9:    */ import org.junit.internal.requests.SortingRequest;
/*  10:    */ import org.junit.internal.runners.ErrorReportingRunner;
/*  11:    */ import org.junit.internal.runners.JUnit38ClassRunner;
/*  12:    */ import org.junit.runner.Description;
/*  13:    */ import org.junit.runner.JUnitCore;
/*  14:    */ import org.junit.runner.Request;
/*  15:    */ import org.junit.runner.Result;
/*  16:    */ import org.junit.runner.Runner;
/*  17:    */ import org.junit.runners.Suite;
/*  18:    */ import org.junit.runners.model.InitializationError;
/*  19:    */ 
/*  20:    */ public class MaxCore
/*  21:    */ {
/*  22:    */   private static final String MALFORMED_JUNIT_3_TEST_CLASS_PREFIX = "malformed JUnit 3 test class: ";
/*  23:    */   private final MaxHistory fHistory;
/*  24:    */   
/*  25:    */   @Deprecated
/*  26:    */   public static MaxCore forFolder(String folderName)
/*  27:    */   {
/*  28: 42 */     return storedLocally(new File(folderName));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static MaxCore storedLocally(File storedResults)
/*  32:    */   {
/*  33: 49 */     return new MaxCore(storedResults);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private MaxCore(File storedResults)
/*  37:    */   {
/*  38: 55 */     this.fHistory = MaxHistory.forFolder(storedResults);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Result run(Class<?> testClass)
/*  42:    */   {
/*  43: 63 */     return run(Request.aClass(testClass));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Result run(Request request)
/*  47:    */   {
/*  48: 72 */     return run(request, new JUnitCore());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Result run(Request request, JUnitCore core)
/*  52:    */   {
/*  53: 86 */     core.addListener(this.fHistory.listener());
/*  54: 87 */     return core.run(sortRequest(request).getRunner());
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Request sortRequest(Request request)
/*  58:    */   {
/*  59: 95 */     if ((request instanceof SortingRequest)) {
/*  60: 96 */       return request;
/*  61:    */     }
/*  62: 97 */     List<Description> leaves = findLeaves(request);
/*  63: 98 */     Collections.sort(leaves, this.fHistory.testComparator());
/*  64: 99 */     return constructLeafRequest(leaves);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private Request constructLeafRequest(List<Description> leaves)
/*  68:    */   {
/*  69:103 */     final List<Runner> runners = new ArrayList();
/*  70:104 */     for (Description each : leaves) {
/*  71:105 */       runners.add(buildRunner(each));
/*  72:    */     }
/*  73:106 */     new Request()
/*  74:    */     {
/*  75:    */       public Runner getRunner()
/*  76:    */       {
/*  77:    */         try
/*  78:    */         {
/*  79:110 */           new Suite((Class)null, runners) {};
/*  80:    */         }
/*  81:    */         catch (InitializationError e)
/*  82:    */         {
/*  83:112 */           return new ErrorReportingRunner(null, e);
/*  84:    */         }
/*  85:    */       }
/*  86:    */     };
/*  87:    */   }
/*  88:    */   
/*  89:    */   private Runner buildRunner(Description each)
/*  90:    */   {
/*  91:119 */     if (each.toString().equals("TestSuite with 0 tests")) {
/*  92:120 */       return Suite.emptySuite();
/*  93:    */     }
/*  94:121 */     if (each.toString().startsWith("malformed JUnit 3 test class: ")) {
/*  95:126 */       return new JUnit38ClassRunner(new TestSuite(getMalformedTestClass(each)));
/*  96:    */     }
/*  97:127 */     Class<?> type = each.getTestClass();
/*  98:128 */     if (type == null) {
/*  99:129 */       throw new RuntimeException("Can't build a runner from description [" + each + "]");
/* 100:    */     }
/* 101:130 */     String methodName = each.getMethodName();
/* 102:131 */     if (methodName == null) {
/* 103:132 */       return Request.aClass(type).getRunner();
/* 104:    */     }
/* 105:133 */     return Request.method(type, methodName).getRunner();
/* 106:    */   }
/* 107:    */   
/* 108:    */   private Class<?> getMalformedTestClass(Description each)
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:138 */       return Class.forName(each.toString().replace("malformed JUnit 3 test class: ", ""));
/* 113:    */     }
/* 114:    */     catch (ClassNotFoundException e) {}
/* 115:140 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public List<Description> sortedLeavesForTest(Request request)
/* 119:    */   {
/* 120:150 */     return findLeaves(sortRequest(request));
/* 121:    */   }
/* 122:    */   
/* 123:    */   private List<Description> findLeaves(Request request)
/* 124:    */   {
/* 125:154 */     List<Description> results = new ArrayList();
/* 126:155 */     findLeaves(null, request.getRunner().getDescription(), results);
/* 127:156 */     return results;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void findLeaves(Description parent, Description description, List<Description> results)
/* 131:    */   {
/* 132:160 */     if (description.getChildren().isEmpty())
/* 133:    */     {
/* 134:161 */       if (description.toString().equals("warning(junit.framework.TestSuite$1)")) {
/* 135:162 */         results.add(Description.createSuiteDescription("malformed JUnit 3 test class: " + parent, new Annotation[0]));
/* 136:    */       } else {
/* 137:164 */         results.add(description);
/* 138:    */       }
/* 139:    */     }
/* 140:    */     else {
/* 141:166 */       for (Description each : description.getChildren()) {
/* 142:167 */         findLeaves(description, each, results);
/* 143:    */       }
/* 144:    */     }
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.max.MaxCore
 * JD-Core Version:    0.7.0.1
 */