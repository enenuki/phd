/*   1:    */ package org.junit.experimental.theories;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Modifier;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import org.junit.Assert;
/*  11:    */ import org.junit.experimental.theories.internal.Assignments;
/*  12:    */ import org.junit.experimental.theories.internal.ParameterizedAssertionError;
/*  13:    */ import org.junit.internal.AssumptionViolatedException;
/*  14:    */ import org.junit.runners.BlockJUnit4ClassRunner;
/*  15:    */ import org.junit.runners.model.FrameworkMethod;
/*  16:    */ import org.junit.runners.model.InitializationError;
/*  17:    */ import org.junit.runners.model.Statement;
/*  18:    */ import org.junit.runners.model.TestClass;
/*  19:    */ 
/*  20:    */ public class Theories
/*  21:    */   extends BlockJUnit4ClassRunner
/*  22:    */ {
/*  23:    */   public Theories(Class<?> klass)
/*  24:    */     throws InitializationError
/*  25:    */   {
/*  26: 25 */     super(klass);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void collectInitializationErrors(List<Throwable> errors)
/*  30:    */   {
/*  31: 30 */     super.collectInitializationErrors(errors);
/*  32: 31 */     validateDataPointFields(errors);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void validateDataPointFields(List<Throwable> errors)
/*  36:    */   {
/*  37: 35 */     Field[] fields = getTestClass().getJavaClass().getDeclaredFields();
/*  38: 37 */     for (Field each : fields) {
/*  39: 38 */       if ((each.getAnnotation(DataPoint.class) != null) && (!Modifier.isStatic(each.getModifiers()))) {
/*  40: 39 */         errors.add(new Error("DataPoint field " + each.getName() + " must be static"));
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void validateConstructor(List<Throwable> errors)
/*  46:    */   {
/*  47: 44 */     validateOnlyOneConstructor(errors);
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void validateTestMethods(List<Throwable> errors)
/*  51:    */   {
/*  52: 49 */     for (FrameworkMethod each : computeTestMethods()) {
/*  53: 50 */       if (each.getAnnotation(Theory.class) != null) {
/*  54: 51 */         each.validatePublicVoid(false, errors);
/*  55:    */       } else {
/*  56: 53 */         each.validatePublicVoidNoArg(false, errors);
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected List<FrameworkMethod> computeTestMethods()
/*  62:    */   {
/*  63: 58 */     List<FrameworkMethod> testMethods = super.computeTestMethods();
/*  64: 59 */     List<FrameworkMethod> theoryMethods = getTestClass().getAnnotatedMethods(Theory.class);
/*  65: 60 */     testMethods.removeAll(theoryMethods);
/*  66: 61 */     testMethods.addAll(theoryMethods);
/*  67: 62 */     return testMethods;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Statement methodBlock(FrameworkMethod method)
/*  71:    */   {
/*  72: 67 */     return new TheoryAnchor(method, getTestClass());
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static class TheoryAnchor
/*  76:    */     extends Statement
/*  77:    */   {
/*  78: 71 */     private int successes = 0;
/*  79:    */     private FrameworkMethod fTestMethod;
/*  80:    */     private TestClass fTestClass;
/*  81: 76 */     private List<AssumptionViolatedException> fInvalidParameters = new ArrayList();
/*  82:    */     
/*  83:    */     public TheoryAnchor(FrameworkMethod method, TestClass testClass)
/*  84:    */     {
/*  85: 79 */       this.fTestMethod = method;
/*  86: 80 */       this.fTestClass = testClass;
/*  87:    */     }
/*  88:    */     
/*  89:    */     private TestClass getTestClass()
/*  90:    */     {
/*  91: 84 */       return this.fTestClass;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public void evaluate()
/*  95:    */       throws Throwable
/*  96:    */     {
/*  97: 89 */       runWithAssignment(Assignments.allUnassigned(this.fTestMethod.getMethod(), getTestClass()));
/*  98: 92 */       if (this.successes == 0) {
/*  99: 93 */         Assert.fail("Never found parameters that satisfied method assumptions.  Violated assumptions: " + this.fInvalidParameters);
/* 100:    */       }
/* 101:    */     }
/* 102:    */     
/* 103:    */     protected void runWithAssignment(Assignments parameterAssignment)
/* 104:    */       throws Throwable
/* 105:    */     {
/* 106:100 */       if (!parameterAssignment.isComplete()) {
/* 107:101 */         runWithIncompleteAssignment(parameterAssignment);
/* 108:    */       } else {
/* 109:103 */         runWithCompleteAssignment(parameterAssignment);
/* 110:    */       }
/* 111:    */     }
/* 112:    */     
/* 113:    */     protected void runWithIncompleteAssignment(Assignments incomplete)
/* 114:    */       throws InstantiationException, IllegalAccessException, Throwable
/* 115:    */     {
/* 116:110 */       for (PotentialAssignment source : incomplete.potentialsForNextUnassigned()) {
/* 117:112 */         runWithAssignment(incomplete.assignNext(source));
/* 118:    */       }
/* 119:    */     }
/* 120:    */     
/* 121:    */     protected void runWithCompleteAssignment(final Assignments complete)
/* 122:    */       throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Throwable
/* 123:    */     {
/* 124:119 */       new BlockJUnit4ClassRunner(getTestClass().getJavaClass())
/* 125:    */       {
/* 126:    */         protected void collectInitializationErrors(List<Throwable> errors) {}
/* 127:    */         
/* 128:    */         public Statement methodBlock(FrameworkMethod method)
/* 129:    */         {
/* 130:128 */           final Statement statement = super.methodBlock(method);
/* 131:129 */           new Statement()
/* 132:    */           {
/* 133:    */             public void evaluate()
/* 134:    */               throws Throwable
/* 135:    */             {
/* 136:    */               try
/* 137:    */               {
/* 138:133 */                 statement.evaluate();
/* 139:134 */                 Theories.TheoryAnchor.this.handleDataPointSuccess();
/* 140:    */               }
/* 141:    */               catch (AssumptionViolatedException e)
/* 142:    */               {
/* 143:136 */                 Theories.TheoryAnchor.this.handleAssumptionViolation(e);
/* 144:    */               }
/* 145:    */               catch (Throwable e)
/* 146:    */               {
/* 147:138 */                 Theories.TheoryAnchor.this.reportParameterizedError(e, Theories.TheoryAnchor.1.this.val$complete.getArgumentStrings(Theories.TheoryAnchor.this.nullsOk()));
/* 148:    */               }
/* 149:    */             }
/* 150:    */           };
/* 151:    */         }
/* 152:    */         
/* 153:    */         protected Statement methodInvoker(FrameworkMethod method, Object test)
/* 154:    */         {
/* 155:148 */           return Theories.TheoryAnchor.this.methodCompletesWithParameters(method, complete, test);
/* 156:    */         }
/* 157:    */         
/* 158:    */         public Object createTest()
/* 159:    */           throws Exception
/* 160:    */         {
/* 161:153 */           return getTestClass().getOnlyConstructor().newInstance(complete.getConstructorArguments(Theories.TheoryAnchor.this.nullsOk()));
/* 162:    */         }
/* 163:153 */       }.methodBlock(this.fTestMethod).evaluate();
/* 164:    */     }
/* 165:    */     
/* 166:    */     private Statement methodCompletesWithParameters(final FrameworkMethod method, final Assignments complete, final Object freshInstance)
/* 167:    */     {
/* 168:161 */       new Statement()
/* 169:    */       {
/* 170:    */         public void evaluate()
/* 171:    */           throws Throwable
/* 172:    */         {
/* 173:    */           try
/* 174:    */           {
/* 175:165 */             Object[] values = complete.getMethodArguments(Theories.TheoryAnchor.this.nullsOk());
/* 176:    */             
/* 177:167 */             method.invokeExplosively(freshInstance, values);
/* 178:    */           }
/* 179:    */           catch (PotentialAssignment.CouldNotGenerateValueException e) {}
/* 180:    */         }
/* 181:    */       };
/* 182:    */     }
/* 183:    */     
/* 184:    */     protected void handleAssumptionViolation(AssumptionViolatedException e)
/* 185:    */     {
/* 186:176 */       this.fInvalidParameters.add(e);
/* 187:    */     }
/* 188:    */     
/* 189:    */     protected void reportParameterizedError(Throwable e, Object... params)
/* 190:    */       throws Throwable
/* 191:    */     {
/* 192:181 */       if (params.length == 0) {
/* 193:182 */         throw e;
/* 194:    */       }
/* 195:183 */       throw new ParameterizedAssertionError(e, this.fTestMethod.getName(), params);
/* 196:    */     }
/* 197:    */     
/* 198:    */     private boolean nullsOk()
/* 199:    */     {
/* 200:188 */       Theory annotation = (Theory)this.fTestMethod.getMethod().getAnnotation(Theory.class);
/* 201:190 */       if (annotation == null) {
/* 202:191 */         return false;
/* 203:    */       }
/* 204:192 */       return annotation.nullsAccepted();
/* 205:    */     }
/* 206:    */     
/* 207:    */     protected void handleDataPointSuccess()
/* 208:    */     {
/* 209:196 */       this.successes += 1;
/* 210:    */     }
/* 211:    */   }
/* 212:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.theories.Theories
 * JD-Core Version:    0.7.0.1
 */