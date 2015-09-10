/*   1:    */ package org.junit.runners;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import java.util.List;
/*   7:    */ import org.junit.After;
/*   8:    */ import org.junit.Before;
/*   9:    */ import org.junit.Ignore;
/*  10:    */ import org.junit.Rule;
/*  11:    */ import org.junit.Test;
/*  12:    */ import org.junit.Test.None;
/*  13:    */ import org.junit.internal.runners.model.ReflectiveCallable;
/*  14:    */ import org.junit.internal.runners.statements.ExpectException;
/*  15:    */ import org.junit.internal.runners.statements.Fail;
/*  16:    */ import org.junit.internal.runners.statements.FailOnTimeout;
/*  17:    */ import org.junit.internal.runners.statements.InvokeMethod;
/*  18:    */ import org.junit.internal.runners.statements.RunAfters;
/*  19:    */ import org.junit.internal.runners.statements.RunBefores;
/*  20:    */ import org.junit.rules.MethodRule;
/*  21:    */ import org.junit.rules.RunRules;
/*  22:    */ import org.junit.rules.TestRule;
/*  23:    */ import org.junit.runner.Description;
/*  24:    */ import org.junit.runner.notification.RunNotifier;
/*  25:    */ import org.junit.runners.model.FrameworkField;
/*  26:    */ import org.junit.runners.model.FrameworkMethod;
/*  27:    */ import org.junit.runners.model.InitializationError;
/*  28:    */ import org.junit.runners.model.Statement;
/*  29:    */ import org.junit.runners.model.TestClass;
/*  30:    */ 
/*  31:    */ public class BlockJUnit4ClassRunner
/*  32:    */   extends ParentRunner<FrameworkMethod>
/*  33:    */ {
/*  34:    */   public BlockJUnit4ClassRunner(Class<?> klass)
/*  35:    */     throws InitializationError
/*  36:    */   {
/*  37: 57 */     super(klass);
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void runChild(FrameworkMethod method, RunNotifier notifier)
/*  41:    */   {
/*  42: 66 */     Description description = describeChild(method);
/*  43: 67 */     if (method.getAnnotation(Ignore.class) != null) {
/*  44: 68 */       notifier.fireTestIgnored(description);
/*  45:    */     } else {
/*  46: 70 */       runLeaf(methodBlock(method), description, notifier);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected Description describeChild(FrameworkMethod method)
/*  51:    */   {
/*  52: 76 */     return Description.createTestDescription(getTestClass().getJavaClass(), testName(method), method.getAnnotations());
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected List<FrameworkMethod> getChildren()
/*  56:    */   {
/*  57: 82 */     return computeTestMethods();
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected List<FrameworkMethod> computeTestMethods()
/*  61:    */   {
/*  62: 95 */     return getTestClass().getAnnotatedMethods(Test.class);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void collectInitializationErrors(List<Throwable> errors)
/*  66:    */   {
/*  67:100 */     super.collectInitializationErrors(errors);
/*  68:    */     
/*  69:102 */     validateConstructor(errors);
/*  70:103 */     validateInstanceMethods(errors);
/*  71:104 */     validateFields(errors);
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void validateConstructor(List<Throwable> errors)
/*  75:    */   {
/*  76:113 */     validateOnlyOneConstructor(errors);
/*  77:114 */     validateZeroArgConstructor(errors);
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void validateOnlyOneConstructor(List<Throwable> errors)
/*  81:    */   {
/*  82:122 */     if (!hasOneConstructor())
/*  83:    */     {
/*  84:123 */       String gripe = "Test class should have exactly one public constructor";
/*  85:124 */       errors.add(new Exception(gripe));
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void validateZeroArgConstructor(List<Throwable> errors)
/*  90:    */   {
/*  91:133 */     if ((hasOneConstructor()) && (getTestClass().getOnlyConstructor().getParameterTypes().length != 0))
/*  92:    */     {
/*  93:135 */       String gripe = "Test class should have exactly one public zero-argument constructor";
/*  94:136 */       errors.add(new Exception(gripe));
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   private boolean hasOneConstructor()
/*  99:    */   {
/* 100:141 */     return getTestClass().getJavaClass().getConstructors().length == 1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   @Deprecated
/* 104:    */   protected void validateInstanceMethods(List<Throwable> errors)
/* 105:    */   {
/* 106:153 */     validatePublicVoidNoArgMethods(After.class, false, errors);
/* 107:154 */     validatePublicVoidNoArgMethods(Before.class, false, errors);
/* 108:155 */     validateTestMethods(errors);
/* 109:157 */     if (computeTestMethods().size() == 0) {
/* 110:158 */       errors.add(new Exception("No runnable methods"));
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void validateFields(List<Throwable> errors)
/* 115:    */   {
/* 116:162 */     for (FrameworkField each : getTestClass().getAnnotatedFields(Rule.class)) {
/* 117:164 */       validateRuleField(each.getField(), errors);
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void validateRuleField(Field field, List<Throwable> errors)
/* 122:    */   {
/* 123:168 */     Class<?> type = field.getType();
/* 124:169 */     if ((!isMethodRule(type)) && (!isTestRule(type))) {
/* 125:170 */       errors.add(new Exception("Field " + field.getName() + " must implement MethodRule"));
/* 126:    */     }
/* 127:172 */     if (!Modifier.isPublic(field.getModifiers())) {
/* 128:173 */       errors.add(new Exception("Field " + field.getName() + " must be public"));
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   private boolean isTestRule(Class<?> type)
/* 133:    */   {
/* 134:178 */     return TestRule.class.isAssignableFrom(type);
/* 135:    */   }
/* 136:    */   
/* 137:    */   private boolean isMethodRule(Class<?> type)
/* 138:    */   {
/* 139:183 */     return MethodRule.class.isAssignableFrom(type);
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void validateTestMethods(List<Throwable> errors)
/* 143:    */   {
/* 144:191 */     validatePublicVoidNoArgMethods(Test.class, false, errors);
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected Object createTest()
/* 148:    */     throws Exception
/* 149:    */   {
/* 150:200 */     return getTestClass().getOnlyConstructor().newInstance(new Object[0]);
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected String testName(FrameworkMethod method)
/* 154:    */   {
/* 155:208 */     return method.getName();
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected Statement methodBlock(FrameworkMethod method)
/* 159:    */   {
/* 160:    */     Object test;
/* 161:    */     try
/* 162:    */     {
/* 163:246 */       test = new ReflectiveCallable()
/* 164:    */       {
/* 165:    */         protected Object runReflectiveCall()
/* 166:    */           throws Throwable
/* 167:    */         {
/* 168:249 */           return BlockJUnit4ClassRunner.this.createTest();
/* 169:    */         }
/* 170:    */       }.run();
/* 171:    */     }
/* 172:    */     catch (Throwable e)
/* 173:    */     {
/* 174:253 */       return new Fail(e);
/* 175:    */     }
/* 176:256 */     Statement statement = methodInvoker(method, test);
/* 177:257 */     statement = possiblyExpectingExceptions(method, test, statement);
/* 178:258 */     statement = withPotentialTimeout(method, test, statement);
/* 179:259 */     statement = withBefores(method, test, statement);
/* 180:260 */     statement = withAfters(method, test, statement);
/* 181:261 */     statement = withRules(method, test, statement);
/* 182:262 */     return statement;
/* 183:    */   }
/* 184:    */   
/* 185:    */   protected Statement methodInvoker(FrameworkMethod method, Object test)
/* 186:    */   {
/* 187:273 */     return new InvokeMethod(method, test);
/* 188:    */   }
/* 189:    */   
/* 190:    */   @Deprecated
/* 191:    */   protected Statement possiblyExpectingExceptions(FrameworkMethod method, Object test, Statement next)
/* 192:    */   {
/* 193:287 */     Test annotation = (Test)method.getAnnotation(Test.class);
/* 194:288 */     return expectsException(annotation) ? new ExpectException(next, getExpectedException(annotation)) : next;
/* 195:    */   }
/* 196:    */   
/* 197:    */   @Deprecated
/* 198:    */   protected Statement withPotentialTimeout(FrameworkMethod method, Object test, Statement next)
/* 199:    */   {
/* 200:302 */     long timeout = getTimeout((Test)method.getAnnotation(Test.class));
/* 201:303 */     return timeout > 0L ? new FailOnTimeout(next, timeout) : next;
/* 202:    */   }
/* 203:    */   
/* 204:    */   @Deprecated
/* 205:    */   protected Statement withBefores(FrameworkMethod method, Object target, Statement statement)
/* 206:    */   {
/* 207:316 */     List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(Before.class);
/* 208:    */     
/* 209:318 */     return befores.isEmpty() ? statement : new RunBefores(statement, befores, target);
/* 210:    */   }
/* 211:    */   
/* 212:    */   @Deprecated
/* 213:    */   protected Statement withAfters(FrameworkMethod method, Object target, Statement statement)
/* 214:    */   {
/* 215:334 */     List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(After.class);
/* 216:    */     
/* 217:336 */     return afters.isEmpty() ? statement : new RunAfters(statement, afters, target);
/* 218:    */   }
/* 219:    */   
/* 220:    */   private Statement withRules(FrameworkMethod method, Object target, Statement statement)
/* 221:    */   {
/* 222:342 */     Statement result = statement;
/* 223:343 */     result = withMethodRules(method, target, result);
/* 224:344 */     result = withTestRules(method, target, result);
/* 225:345 */     return result;
/* 226:    */   }
/* 227:    */   
/* 228:    */   private Statement withMethodRules(FrameworkMethod method, Object target, Statement result)
/* 229:    */   {
/* 230:351 */     List<TestRule> testRules = getTestRules(target);
/* 231:352 */     for (MethodRule each : getMethodRules(target)) {
/* 232:353 */       if (!testRules.contains(each)) {
/* 233:354 */         result = each.apply(result, method, target);
/* 234:    */       }
/* 235:    */     }
/* 236:355 */     return result;
/* 237:    */   }
/* 238:    */   
/* 239:    */   private List<MethodRule> getMethodRules(Object target)
/* 240:    */   {
/* 241:360 */     return getTestClass().getAnnotatedFieldValues(target, Rule.class, MethodRule.class);
/* 242:    */   }
/* 243:    */   
/* 244:    */   private Statement withTestRules(FrameworkMethod method, Object target, Statement statement)
/* 245:    */   {
/* 246:374 */     List<TestRule> testRules = getTestRules(target);
/* 247:375 */     return testRules.isEmpty() ? statement : new RunRules(statement, testRules, describeChild(method));
/* 248:    */   }
/* 249:    */   
/* 250:    */   private List<TestRule> getTestRules(Object target)
/* 251:    */   {
/* 252:380 */     return getTestClass().getAnnotatedFieldValues(target, Rule.class, TestRule.class);
/* 253:    */   }
/* 254:    */   
/* 255:    */   private Class<? extends Throwable> getExpectedException(Test annotation)
/* 256:    */   {
/* 257:385 */     if ((annotation == null) || (annotation.expected() == Test.None.class)) {
/* 258:386 */       return null;
/* 259:    */     }
/* 260:388 */     return annotation.expected();
/* 261:    */   }
/* 262:    */   
/* 263:    */   private boolean expectsException(Test annotation)
/* 264:    */   {
/* 265:392 */     return getExpectedException(annotation) != null;
/* 266:    */   }
/* 267:    */   
/* 268:    */   private long getTimeout(Test annotation)
/* 269:    */   {
/* 270:396 */     if (annotation == null) {
/* 271:397 */       return 0L;
/* 272:    */     }
/* 273:398 */     return annotation.timeout();
/* 274:    */   }
/* 275:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.BlockJUnit4ClassRunner
 * JD-Core Version:    0.7.0.1
 */