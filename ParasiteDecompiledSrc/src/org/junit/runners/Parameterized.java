/*   1:    */ package org.junit.runners;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.lang.annotation.Retention;
/*   5:    */ import java.lang.annotation.RetentionPolicy;
/*   6:    */ import java.lang.annotation.Target;
/*   7:    */ import java.lang.reflect.Constructor;
/*   8:    */ import java.lang.reflect.Method;
/*   9:    */ import java.lang.reflect.Modifier;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collections;
/*  12:    */ import java.util.List;
/*  13:    */ import org.junit.runner.Runner;
/*  14:    */ import org.junit.runner.notification.RunNotifier;
/*  15:    */ import org.junit.runners.model.FrameworkMethod;
/*  16:    */ import org.junit.runners.model.InitializationError;
/*  17:    */ import org.junit.runners.model.Statement;
/*  18:    */ import org.junit.runners.model.TestClass;
/*  19:    */ 
/*  20:    */ public class Parameterized
/*  21:    */   extends Suite
/*  22:    */ {
/*  23:    */   private class TestClassRunnerForParameters
/*  24:    */     extends BlockJUnit4ClassRunner
/*  25:    */   {
/*  26:    */     private final int fParameterSetNumber;
/*  27:    */     private final List<Object[]> fParameterList;
/*  28:    */     
/*  29:    */     TestClassRunnerForParameters(List<Object[]> type, int parameterList)
/*  30:    */       throws InitializationError
/*  31:    */     {
/*  32: 79 */       super();
/*  33: 80 */       this.fParameterList = parameterList;
/*  34: 81 */       this.fParameterSetNumber = i;
/*  35:    */     }
/*  36:    */     
/*  37:    */     public Object createTest()
/*  38:    */       throws Exception
/*  39:    */     {
/*  40: 86 */       return getTestClass().getOnlyConstructor().newInstance(computeParams());
/*  41:    */     }
/*  42:    */     
/*  43:    */     private Object[] computeParams()
/*  44:    */       throws Exception
/*  45:    */     {
/*  46:    */       try
/*  47:    */       {
/*  48: 92 */         return (Object[])this.fParameterList.get(this.fParameterSetNumber);
/*  49:    */       }
/*  50:    */       catch (ClassCastException e)
/*  51:    */       {
/*  52: 94 */         throw new Exception(String.format("%s.%s() must return a Collection of arrays.", new Object[] { getTestClass().getName(), Parameterized.this.getParametersMethod(getTestClass()).getName() }));
/*  53:    */       }
/*  54:    */     }
/*  55:    */     
/*  56:    */     protected String getName()
/*  57:    */     {
/*  58:103 */       return String.format("[%s]", new Object[] { Integer.valueOf(this.fParameterSetNumber) });
/*  59:    */     }
/*  60:    */     
/*  61:    */     protected String testName(FrameworkMethod method)
/*  62:    */     {
/*  63:108 */       return String.format("%s[%s]", new Object[] { method.getName(), Integer.valueOf(this.fParameterSetNumber) });
/*  64:    */     }
/*  65:    */     
/*  66:    */     protected void validateConstructor(List<Throwable> errors)
/*  67:    */     {
/*  68:114 */       validateOnlyOneConstructor(errors);
/*  69:    */     }
/*  70:    */     
/*  71:    */     protected Statement classBlock(RunNotifier notifier)
/*  72:    */     {
/*  73:119 */       return childrenInvoker(notifier);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:123 */   private final ArrayList<Runner> runners = new ArrayList();
/*  78:    */   
/*  79:    */   public Parameterized(Class<?> klass)
/*  80:    */     throws Throwable
/*  81:    */   {
/*  82:129 */     super(klass, Collections.emptyList());
/*  83:130 */     List<Object[]> parametersList = getParametersList(getTestClass());
/*  84:131 */     for (int i = 0; i < parametersList.size(); i++) {
/*  85:132 */       this.runners.add(new TestClassRunnerForParameters(getTestClass().getJavaClass(), parametersList, i));
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected List<Runner> getChildren()
/*  90:    */   {
/*  91:138 */     return this.runners;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private List<Object[]> getParametersList(TestClass klass)
/*  95:    */     throws Throwable
/*  96:    */   {
/*  97:144 */     return (List)getParametersMethod(klass).invokeExplosively(null, new Object[0]);
/*  98:    */   }
/*  99:    */   
/* 100:    */   private FrameworkMethod getParametersMethod(TestClass testClass)
/* 101:    */     throws Exception
/* 102:    */   {
/* 103:150 */     List<FrameworkMethod> methods = testClass.getAnnotatedMethods(Parameters.class);
/* 104:152 */     for (FrameworkMethod each : methods)
/* 105:    */     {
/* 106:153 */       int modifiers = each.getMethod().getModifiers();
/* 107:154 */       if ((Modifier.isStatic(modifiers)) && (Modifier.isPublic(modifiers))) {
/* 108:155 */         return each;
/* 109:    */       }
/* 110:    */     }
/* 111:158 */     throw new Exception("No public static parameters method on class " + testClass.getName());
/* 112:    */   }
/* 113:    */   
/* 114:    */   @Retention(RetentionPolicy.RUNTIME)
/* 115:    */   @Target({java.lang.annotation.ElementType.METHOD})
/* 116:    */   public static @interface Parameters {}
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.Parameterized
 * JD-Core Version:    0.7.0.1
 */