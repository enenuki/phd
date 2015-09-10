/*   1:    */ package org.junit.internal.runners;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import junit.extensions.TestDecorator;
/*   5:    */ import junit.framework.AssertionFailedError;
/*   6:    */ import junit.framework.Test;
/*   7:    */ import junit.framework.TestCase;
/*   8:    */ import junit.framework.TestListener;
/*   9:    */ import junit.framework.TestResult;
/*  10:    */ import junit.framework.TestSuite;
/*  11:    */ import org.junit.runner.Describable;
/*  12:    */ import org.junit.runner.Description;
/*  13:    */ import org.junit.runner.Runner;
/*  14:    */ import org.junit.runner.manipulation.Filter;
/*  15:    */ import org.junit.runner.manipulation.Filterable;
/*  16:    */ import org.junit.runner.manipulation.NoTestsRemainException;
/*  17:    */ import org.junit.runner.manipulation.Sortable;
/*  18:    */ import org.junit.runner.manipulation.Sorter;
/*  19:    */ import org.junit.runner.notification.Failure;
/*  20:    */ import org.junit.runner.notification.RunNotifier;
/*  21:    */ 
/*  22:    */ public class JUnit38ClassRunner
/*  23:    */   extends Runner
/*  24:    */   implements Filterable, Sortable
/*  25:    */ {
/*  26:    */   private Test fTest;
/*  27:    */   
/*  28:    */   private final class OldTestClassAdaptingListener
/*  29:    */     implements TestListener
/*  30:    */   {
/*  31:    */     private final RunNotifier fNotifier;
/*  32:    */     
/*  33:    */     private OldTestClassAdaptingListener(RunNotifier notifier)
/*  34:    */     {
/*  35: 27 */       this.fNotifier = notifier;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public void endTest(Test test)
/*  39:    */     {
/*  40: 31 */       this.fNotifier.fireTestFinished(asDescription(test));
/*  41:    */     }
/*  42:    */     
/*  43:    */     public void startTest(Test test)
/*  44:    */     {
/*  45: 35 */       this.fNotifier.fireTestStarted(asDescription(test));
/*  46:    */     }
/*  47:    */     
/*  48:    */     public void addError(Test test, Throwable t)
/*  49:    */     {
/*  50: 40 */       Failure failure = new Failure(asDescription(test), t);
/*  51: 41 */       this.fNotifier.fireTestFailure(failure);
/*  52:    */     }
/*  53:    */     
/*  54:    */     private Description asDescription(Test test)
/*  55:    */     {
/*  56: 45 */       if ((test instanceof Describable))
/*  57:    */       {
/*  58: 46 */         Describable facade = (Describable)test;
/*  59: 47 */         return facade.getDescription();
/*  60:    */       }
/*  61: 49 */       return Description.createTestDescription(getEffectiveClass(test), getName(test));
/*  62:    */     }
/*  63:    */     
/*  64:    */     private Class<? extends Test> getEffectiveClass(Test test)
/*  65:    */     {
/*  66: 53 */       return test.getClass();
/*  67:    */     }
/*  68:    */     
/*  69:    */     private String getName(Test test)
/*  70:    */     {
/*  71: 57 */       if ((test instanceof TestCase)) {
/*  72: 58 */         return ((TestCase)test).getName();
/*  73:    */       }
/*  74: 60 */       return test.toString();
/*  75:    */     }
/*  76:    */     
/*  77:    */     public void addFailure(Test test, AssertionFailedError t)
/*  78:    */     {
/*  79: 64 */       addError(test, t);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public JUnit38ClassRunner(Class<?> klass)
/*  84:    */   {
/*  85: 71 */     this(new TestSuite(klass.asSubclass(TestCase.class)));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public JUnit38ClassRunner(Test test)
/*  89:    */   {
/*  90: 76 */     setTest(test);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void run(RunNotifier notifier)
/*  94:    */   {
/*  95: 81 */     TestResult result = new TestResult();
/*  96: 82 */     result.addListener(createAdaptingListener(notifier));
/*  97: 83 */     getTest().run(result);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public TestListener createAdaptingListener(RunNotifier notifier)
/* 101:    */   {
/* 102: 87 */     return new OldTestClassAdaptingListener(notifier, null);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Description getDescription()
/* 106:    */   {
/* 107: 92 */     return makeDescription(getTest());
/* 108:    */   }
/* 109:    */   
/* 110:    */   private static Description makeDescription(Test test)
/* 111:    */   {
/* 112: 96 */     if ((test instanceof TestCase))
/* 113:    */     {
/* 114: 97 */       TestCase tc = (TestCase)test;
/* 115: 98 */       return Description.createTestDescription(tc.getClass(), tc.getName());
/* 116:    */     }
/* 117: 99 */     if ((test instanceof TestSuite))
/* 118:    */     {
/* 119:100 */       TestSuite ts = (TestSuite)test;
/* 120:101 */       String name = ts.getName() == null ? createSuiteDescription(ts) : ts.getName();
/* 121:102 */       Description description = Description.createSuiteDescription(name, new Annotation[0]);
/* 122:103 */       int n = ts.testCount();
/* 123:104 */       for (int i = 0; i < n; i++)
/* 124:    */       {
/* 125:105 */         Description made = makeDescription(ts.testAt(i));
/* 126:106 */         description.addChild(made);
/* 127:    */       }
/* 128:108 */       return description;
/* 129:    */     }
/* 130:109 */     if ((test instanceof Describable))
/* 131:    */     {
/* 132:110 */       Describable adapter = (Describable)test;
/* 133:111 */       return adapter.getDescription();
/* 134:    */     }
/* 135:112 */     if ((test instanceof TestDecorator))
/* 136:    */     {
/* 137:113 */       TestDecorator decorator = (TestDecorator)test;
/* 138:114 */       return makeDescription(decorator.getTest());
/* 139:    */     }
/* 140:117 */     return Description.createSuiteDescription(test.getClass());
/* 141:    */   }
/* 142:    */   
/* 143:    */   private static String createSuiteDescription(TestSuite ts)
/* 144:    */   {
/* 145:122 */     int count = ts.countTestCases();
/* 146:123 */     String example = count == 0 ? "" : String.format(" [example: %s]", new Object[] { ts.testAt(0) });
/* 147:124 */     return String.format("TestSuite with %s tests%s", new Object[] { Integer.valueOf(count), example });
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void filter(Filter filter)
/* 151:    */     throws NoTestsRemainException
/* 152:    */   {
/* 153:128 */     if ((getTest() instanceof Filterable))
/* 154:    */     {
/* 155:129 */       Filterable adapter = (Filterable)getTest();
/* 156:130 */       adapter.filter(filter);
/* 157:    */     }
/* 158:131 */     else if ((getTest() instanceof TestSuite))
/* 159:    */     {
/* 160:132 */       TestSuite suite = (TestSuite)getTest();
/* 161:133 */       TestSuite filtered = new TestSuite(suite.getName());
/* 162:134 */       int n = suite.testCount();
/* 163:135 */       for (int i = 0; i < n; i++)
/* 164:    */       {
/* 165:136 */         Test test = suite.testAt(i);
/* 166:137 */         if (filter.shouldRun(makeDescription(test))) {
/* 167:138 */           filtered.addTest(test);
/* 168:    */         }
/* 169:    */       }
/* 170:140 */       setTest(filtered);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void sort(Sorter sorter)
/* 175:    */   {
/* 176:145 */     if ((getTest() instanceof Sortable))
/* 177:    */     {
/* 178:146 */       Sortable adapter = (Sortable)getTest();
/* 179:147 */       adapter.sort(sorter);
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   private void setTest(Test test)
/* 184:    */   {
/* 185:152 */     this.fTest = test;
/* 186:    */   }
/* 187:    */   
/* 188:    */   private Test getTest()
/* 189:    */   {
/* 190:156 */     return this.fTest;
/* 191:    */   }
/* 192:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.JUnit38ClassRunner
 * JD-Core Version:    0.7.0.1
 */