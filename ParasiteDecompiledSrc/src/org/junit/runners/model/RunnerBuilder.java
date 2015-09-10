/*   1:    */ package org.junit.runners.model;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.junit.internal.runners.ErrorReportingRunner;
/*   8:    */ import org.junit.runner.Runner;
/*   9:    */ 
/*  10:    */ public abstract class RunnerBuilder
/*  11:    */ {
/*  12: 39 */   private final Set<Class<?>> parents = new HashSet();
/*  13:    */   
/*  14:    */   public abstract Runner runnerForClass(Class<?> paramClass)
/*  15:    */     throws Throwable;
/*  16:    */   
/*  17:    */   public Runner safeRunnerForClass(Class<?> testClass)
/*  18:    */   {
/*  19:    */     try
/*  20:    */     {
/*  21: 57 */       return runnerForClass(testClass);
/*  22:    */     }
/*  23:    */     catch (Throwable e)
/*  24:    */     {
/*  25: 59 */       return new ErrorReportingRunner(testClass, e);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   Class<?> addParent(Class<?> parent)
/*  30:    */     throws InitializationError
/*  31:    */   {
/*  32: 64 */     if (!this.parents.add(parent)) {
/*  33: 65 */       throw new InitializationError(String.format("class '%s' (possibly indirectly) contains itself as a SuiteClass", new Object[] { parent.getName() }));
/*  34:    */     }
/*  35: 66 */     return parent;
/*  36:    */   }
/*  37:    */   
/*  38:    */   void removeParent(Class<?> klass)
/*  39:    */   {
/*  40: 70 */     this.parents.remove(klass);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<Runner> runners(Class<?> parent, Class<?>[] children)
/*  44:    */     throws InitializationError
/*  45:    */   {
/*  46: 81 */     addParent(parent);
/*  47:    */     try
/*  48:    */     {
/*  49: 84 */       return runners(children);
/*  50:    */     }
/*  51:    */     finally
/*  52:    */     {
/*  53: 86 */       removeParent(parent);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<Runner> runners(Class<?> parent, List<Class<?>> children)
/*  58:    */     throws InitializationError
/*  59:    */   {
/*  60: 92 */     return runners(parent, (Class[])children.toArray(new Class[0]));
/*  61:    */   }
/*  62:    */   
/*  63:    */   private List<Runner> runners(Class<?>[] children)
/*  64:    */   {
/*  65: 96 */     ArrayList<Runner> runners = new ArrayList();
/*  66: 97 */     for (Class<?> each : children)
/*  67:    */     {
/*  68: 98 */       Runner childRunner = safeRunnerForClass(each);
/*  69: 99 */       if (childRunner != null) {
/*  70:100 */         runners.add(childRunner);
/*  71:    */       }
/*  72:    */     }
/*  73:102 */     return runners;
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.model.RunnerBuilder
 * JD-Core Version:    0.7.0.1
 */