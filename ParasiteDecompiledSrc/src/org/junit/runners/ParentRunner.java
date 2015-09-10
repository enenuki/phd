/*   1:    */ package org.junit.runners;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.List;
/*   8:    */ import org.junit.AfterClass;
/*   9:    */ import org.junit.BeforeClass;
/*  10:    */ import org.junit.ClassRule;
/*  11:    */ import org.junit.internal.AssumptionViolatedException;
/*  12:    */ import org.junit.internal.runners.model.EachTestNotifier;
/*  13:    */ import org.junit.internal.runners.statements.RunAfters;
/*  14:    */ import org.junit.internal.runners.statements.RunBefores;
/*  15:    */ import org.junit.rules.RunRules;
/*  16:    */ import org.junit.rules.TestRule;
/*  17:    */ import org.junit.runner.Description;
/*  18:    */ import org.junit.runner.Runner;
/*  19:    */ import org.junit.runner.manipulation.Filter;
/*  20:    */ import org.junit.runner.manipulation.Filterable;
/*  21:    */ import org.junit.runner.manipulation.NoTestsRemainException;
/*  22:    */ import org.junit.runner.manipulation.Sortable;
/*  23:    */ import org.junit.runner.manipulation.Sorter;
/*  24:    */ import org.junit.runner.notification.RunNotifier;
/*  25:    */ import org.junit.runner.notification.StoppedByUserException;
/*  26:    */ import org.junit.runners.model.FrameworkField;
/*  27:    */ import org.junit.runners.model.FrameworkMethod;
/*  28:    */ import org.junit.runners.model.InitializationError;
/*  29:    */ import org.junit.runners.model.RunnerScheduler;
/*  30:    */ import org.junit.runners.model.Statement;
/*  31:    */ import org.junit.runners.model.TestClass;
/*  32:    */ 
/*  33:    */ public abstract class ParentRunner<T>
/*  34:    */   extends Runner
/*  35:    */   implements Filterable, Sortable
/*  36:    */ {
/*  37:    */   private final TestClass fTestClass;
/*  38: 52 */   private Filter fFilter = null;
/*  39: 54 */   private Sorter fSorter = Sorter.NULL;
/*  40: 56 */   private RunnerScheduler fScheduler = new RunnerScheduler()
/*  41:    */   {
/*  42:    */     public void schedule(Runnable childStatement)
/*  43:    */     {
/*  44: 58 */       childStatement.run();
/*  45:    */     }
/*  46:    */     
/*  47:    */     public void finished() {}
/*  48:    */   };
/*  49:    */   
/*  50:    */   protected ParentRunner(Class<?> testClass)
/*  51:    */     throws InitializationError
/*  52:    */   {
/*  53: 71 */     this.fTestClass = new TestClass(testClass);
/*  54: 72 */     validate();
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected abstract List<T> getChildren();
/*  58:    */   
/*  59:    */   protected abstract Description describeChild(T paramT);
/*  60:    */   
/*  61:    */   protected abstract void runChild(T paramT, RunNotifier paramRunNotifier);
/*  62:    */   
/*  63:    */   protected void collectInitializationErrors(List<Throwable> errors)
/*  64:    */   {
/*  65:109 */     validatePublicVoidNoArgMethods(BeforeClass.class, true, errors);
/*  66:110 */     validatePublicVoidNoArgMethods(AfterClass.class, true, errors);
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors)
/*  70:    */   {
/*  71:125 */     List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
/*  72:127 */     for (FrameworkMethod eachTestMethod : methods) {
/*  73:128 */       eachTestMethod.validatePublicVoidNoArg(isStatic, errors);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected Statement classBlock(RunNotifier notifier)
/*  78:    */   {
/*  79:149 */     Statement statement = childrenInvoker(notifier);
/*  80:150 */     statement = withBeforeClasses(statement);
/*  81:151 */     statement = withAfterClasses(statement);
/*  82:152 */     statement = withClassRules(statement);
/*  83:153 */     return statement;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected Statement withBeforeClasses(Statement statement)
/*  87:    */   {
/*  88:162 */     List<FrameworkMethod> befores = this.fTestClass.getAnnotatedMethods(BeforeClass.class);
/*  89:    */     
/*  90:164 */     return befores.isEmpty() ? statement : new RunBefores(statement, befores, null);
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected Statement withAfterClasses(Statement statement)
/*  94:    */   {
/*  95:176 */     List<FrameworkMethod> afters = this.fTestClass.getAnnotatedMethods(AfterClass.class);
/*  96:    */     
/*  97:178 */     return afters.isEmpty() ? statement : new RunAfters(statement, afters, null);
/*  98:    */   }
/*  99:    */   
/* 100:    */   private Statement withClassRules(Statement statement)
/* 101:    */   {
/* 102:192 */     List<TestRule> classRules = classRules();
/* 103:193 */     return classRules.isEmpty() ? statement : new RunRules(statement, classRules, getDescription());
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected List<TestRule> classRules()
/* 107:    */   {
/* 108:202 */     List<TestRule> results = new ArrayList();
/* 109:203 */     for (FrameworkField field : classRuleFields()) {
/* 110:204 */       results.add(getClassRule(field));
/* 111:    */     }
/* 112:205 */     return results;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private TestRule getClassRule(FrameworkField field)
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:210 */       return (TestRule)field.get(null);
/* 120:    */     }
/* 121:    */     catch (IllegalAccessException e)
/* 122:    */     {
/* 123:212 */       throw new RuntimeException("How did getAnnotatedFields return a field we couldn't access?");
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected List<FrameworkField> classRuleFields()
/* 128:    */   {
/* 129:221 */     return this.fTestClass.getAnnotatedFields(ClassRule.class);
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected Statement childrenInvoker(final RunNotifier notifier)
/* 133:    */   {
/* 134:230 */     new Statement()
/* 135:    */     {
/* 136:    */       public void evaluate()
/* 137:    */       {
/* 138:233 */         ParentRunner.this.runChildren(notifier);
/* 139:    */       }
/* 140:    */     };
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void runChildren(final RunNotifier notifier)
/* 144:    */   {
/* 145:239 */     for (final T each : getFilteredChildren()) {
/* 146:240 */       this.fScheduler.schedule(new Runnable()
/* 147:    */       {
/* 148:    */         public void run()
/* 149:    */         {
/* 150:242 */           ParentRunner.this.runChild(each, notifier);
/* 151:    */         }
/* 152:    */       });
/* 153:    */     }
/* 154:245 */     this.fScheduler.finished();
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected String getName()
/* 158:    */   {
/* 159:252 */     return this.fTestClass.getName();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public final TestClass getTestClass()
/* 163:    */   {
/* 164:263 */     return this.fTestClass;
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected final void runLeaf(Statement statement, Description description, RunNotifier notifier)
/* 168:    */   {
/* 169:271 */     EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
/* 170:272 */     eachNotifier.fireTestStarted();
/* 171:    */     try
/* 172:    */     {
/* 173:274 */       statement.evaluate();
/* 174:    */     }
/* 175:    */     catch (AssumptionViolatedException e)
/* 176:    */     {
/* 177:276 */       eachNotifier.addFailedAssumption(e);
/* 178:    */     }
/* 179:    */     catch (Throwable e)
/* 180:    */     {
/* 181:278 */       eachNotifier.addFailure(e);
/* 182:    */     }
/* 183:    */     finally
/* 184:    */     {
/* 185:280 */       eachNotifier.fireTestFinished();
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Description getDescription()
/* 190:    */   {
/* 191:290 */     Description description = Description.createSuiteDescription(getName(), this.fTestClass.getAnnotations());
/* 192:292 */     for (T child : getFilteredChildren()) {
/* 193:293 */       description.addChild(describeChild(child));
/* 194:    */     }
/* 195:294 */     return description;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void run(RunNotifier notifier)
/* 199:    */   {
/* 200:299 */     EachTestNotifier testNotifier = new EachTestNotifier(notifier, getDescription());
/* 201:    */     try
/* 202:    */     {
/* 203:302 */       Statement statement = classBlock(notifier);
/* 204:303 */       statement.evaluate();
/* 205:    */     }
/* 206:    */     catch (AssumptionViolatedException e)
/* 207:    */     {
/* 208:305 */       testNotifier.fireTestIgnored();
/* 209:    */     }
/* 210:    */     catch (StoppedByUserException e)
/* 211:    */     {
/* 212:307 */       throw e;
/* 213:    */     }
/* 214:    */     catch (Throwable e)
/* 215:    */     {
/* 216:309 */       testNotifier.addFailure(e);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void filter(Filter filter)
/* 221:    */     throws NoTestsRemainException
/* 222:    */   {
/* 223:318 */     this.fFilter = filter;
/* 224:320 */     for (T each : getChildren()) {
/* 225:321 */       if (shouldRun(each)) {
/* 226:322 */         return;
/* 227:    */       }
/* 228:    */     }
/* 229:323 */     throw new NoTestsRemainException();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void sort(Sorter sorter)
/* 233:    */   {
/* 234:327 */     this.fSorter = sorter;
/* 235:    */   }
/* 236:    */   
/* 237:    */   private void validate()
/* 238:    */     throws InitializationError
/* 239:    */   {
/* 240:335 */     List<Throwable> errors = new ArrayList();
/* 241:336 */     collectInitializationErrors(errors);
/* 242:337 */     if (!errors.isEmpty()) {
/* 243:338 */       throw new InitializationError(errors);
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   private List<T> getFilteredChildren()
/* 248:    */   {
/* 249:342 */     ArrayList<T> filtered = new ArrayList();
/* 250:343 */     for (T each : getChildren()) {
/* 251:344 */       if (shouldRun(each)) {
/* 252:    */         try
/* 253:    */         {
/* 254:346 */           filterChild(each);
/* 255:347 */           sortChild(each);
/* 256:348 */           filtered.add(each);
/* 257:    */         }
/* 258:    */         catch (NoTestsRemainException e) {}
/* 259:    */       }
/* 260:    */     }
/* 261:352 */     Collections.sort(filtered, comparator());
/* 262:353 */     return filtered;
/* 263:    */   }
/* 264:    */   
/* 265:    */   private void sortChild(T child)
/* 266:    */   {
/* 267:357 */     this.fSorter.apply(child);
/* 268:    */   }
/* 269:    */   
/* 270:    */   private void filterChild(T child)
/* 271:    */     throws NoTestsRemainException
/* 272:    */   {
/* 273:361 */     if (this.fFilter != null) {
/* 274:362 */       this.fFilter.apply(child);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   private boolean shouldRun(T each)
/* 279:    */   {
/* 280:366 */     return (this.fFilter == null) || (this.fFilter.shouldRun(describeChild(each)));
/* 281:    */   }
/* 282:    */   
/* 283:    */   private Comparator<? super T> comparator()
/* 284:    */   {
/* 285:370 */     new Comparator()
/* 286:    */     {
/* 287:    */       public int compare(T o1, T o2)
/* 288:    */       {
/* 289:372 */         return ParentRunner.this.fSorter.compare(ParentRunner.this.describeChild(o1), ParentRunner.this.describeChild(o2));
/* 290:    */       }
/* 291:    */     };
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setScheduler(RunnerScheduler scheduler)
/* 295:    */   {
/* 296:382 */     this.fScheduler = scheduler;
/* 297:    */   }
/* 298:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.ParentRunner
 * JD-Core Version:    0.7.0.1
 */