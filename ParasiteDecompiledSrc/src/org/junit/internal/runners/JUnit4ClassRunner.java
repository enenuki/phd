/*   1:    */ package org.junit.internal.runners;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import org.junit.runner.Description;
/*  12:    */ import org.junit.runner.Runner;
/*  13:    */ import org.junit.runner.manipulation.Filter;
/*  14:    */ import org.junit.runner.manipulation.Filterable;
/*  15:    */ import org.junit.runner.manipulation.NoTestsRemainException;
/*  16:    */ import org.junit.runner.manipulation.Sortable;
/*  17:    */ import org.junit.runner.manipulation.Sorter;
/*  18:    */ import org.junit.runner.notification.Failure;
/*  19:    */ import org.junit.runner.notification.RunNotifier;
/*  20:    */ 
/*  21:    */ @Deprecated
/*  22:    */ public class JUnit4ClassRunner
/*  23:    */   extends Runner
/*  24:    */   implements Filterable, Sortable
/*  25:    */ {
/*  26:    */   private final List<Method> fTestMethods;
/*  27:    */   private TestClass fTestClass;
/*  28:    */   
/*  29:    */   public JUnit4ClassRunner(Class<?> klass)
/*  30:    */     throws InitializationError
/*  31:    */   {
/*  32: 35 */     this.fTestClass = new TestClass(klass);
/*  33: 36 */     this.fTestMethods = getTestMethods();
/*  34: 37 */     validate();
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected List<Method> getTestMethods()
/*  38:    */   {
/*  39: 41 */     return this.fTestClass.getTestMethods();
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected void validate()
/*  43:    */     throws InitializationError
/*  44:    */   {
/*  45: 45 */     MethodValidator methodValidator = new MethodValidator(this.fTestClass);
/*  46: 46 */     methodValidator.validateMethodsForDefaultRunner();
/*  47: 47 */     methodValidator.assertValid();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void run(final RunNotifier notifier)
/*  51:    */   {
/*  52: 52 */     new ClassRoadie(notifier, this.fTestClass, getDescription(), new Runnable()
/*  53:    */     {
/*  54:    */       public void run()
/*  55:    */       {
/*  56: 54 */         JUnit4ClassRunner.this.runMethods(notifier);
/*  57:    */       }
/*  58:    */     }).runProtected();
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void runMethods(RunNotifier notifier)
/*  62:    */   {
/*  63: 60 */     for (Method method : this.fTestMethods) {
/*  64: 61 */       invokeTestMethod(method, notifier);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Description getDescription()
/*  69:    */   {
/*  70: 66 */     Description spec = Description.createSuiteDescription(getName(), classAnnotations());
/*  71: 67 */     List<Method> testMethods = this.fTestMethods;
/*  72: 68 */     for (Method method : testMethods) {
/*  73: 69 */       spec.addChild(methodDescription(method));
/*  74:    */     }
/*  75: 70 */     return spec;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected Annotation[] classAnnotations()
/*  79:    */   {
/*  80: 74 */     return this.fTestClass.getJavaClass().getAnnotations();
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected String getName()
/*  84:    */   {
/*  85: 78 */     return getTestClass().getName();
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected Object createTest()
/*  89:    */     throws Exception
/*  90:    */   {
/*  91: 82 */     return getTestClass().getConstructor().newInstance(new Object[0]);
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void invokeTestMethod(Method method, RunNotifier notifier)
/*  95:    */   {
/*  96: 86 */     Description description = methodDescription(method);
/*  97:    */     Object test;
/*  98:    */     try
/*  99:    */     {
/* 100: 89 */       test = createTest();
/* 101:    */     }
/* 102:    */     catch (InvocationTargetException e)
/* 103:    */     {
/* 104: 91 */       testAborted(notifier, description, e.getCause());
/* 105: 92 */       return;
/* 106:    */     }
/* 107:    */     catch (Exception e)
/* 108:    */     {
/* 109: 94 */       testAborted(notifier, description, e);
/* 110: 95 */       return;
/* 111:    */     }
/* 112: 97 */     TestMethod testMethod = wrapMethod(method);
/* 113: 98 */     new MethodRoadie(test, testMethod, notifier, description).run();
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void testAborted(RunNotifier notifier, Description description, Throwable e)
/* 117:    */   {
/* 118:103 */     notifier.fireTestStarted(description);
/* 119:104 */     notifier.fireTestFailure(new Failure(description, e));
/* 120:105 */     notifier.fireTestFinished(description);
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected TestMethod wrapMethod(Method method)
/* 124:    */   {
/* 125:109 */     return new TestMethod(method, this.fTestClass);
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected String testName(Method method)
/* 129:    */   {
/* 130:113 */     return method.getName();
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected Description methodDescription(Method method)
/* 134:    */   {
/* 135:117 */     return Description.createTestDescription(getTestClass().getJavaClass(), testName(method), testAnnotations(method));
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected Annotation[] testAnnotations(Method method)
/* 139:    */   {
/* 140:121 */     return method.getAnnotations();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void filter(Filter filter)
/* 144:    */     throws NoTestsRemainException
/* 145:    */   {
/* 146:125 */     for (Iterator<Method> iter = this.fTestMethods.iterator(); iter.hasNext();)
/* 147:    */     {
/* 148:126 */       Method method = (Method)iter.next();
/* 149:127 */       if (!filter.shouldRun(methodDescription(method))) {
/* 150:128 */         iter.remove();
/* 151:    */       }
/* 152:    */     }
/* 153:130 */     if (this.fTestMethods.isEmpty()) {
/* 154:131 */       throw new NoTestsRemainException();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void sort(final Sorter sorter)
/* 159:    */   {
/* 160:135 */     Collections.sort(this.fTestMethods, new Comparator()
/* 161:    */     {
/* 162:    */       public int compare(Method o1, Method o2)
/* 163:    */       {
/* 164:137 */         return sorter.compare(JUnit4ClassRunner.this.methodDescription(o1), JUnit4ClassRunner.this.methodDescription(o2));
/* 165:    */       }
/* 166:    */     });
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected TestClass getTestClass()
/* 170:    */   {
/* 171:143 */     return this.fTestClass;
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.JUnit4ClassRunner
 * JD-Core Version:    0.7.0.1
 */