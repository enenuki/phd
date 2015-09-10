/*   1:    */ package org.junit.runners;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.lang.annotation.Inherited;
/*   5:    */ import java.lang.annotation.Retention;
/*   6:    */ import java.lang.annotation.RetentionPolicy;
/*   7:    */ import java.lang.annotation.Target;
/*   8:    */ import java.util.List;
/*   9:    */ import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
/*  10:    */ import org.junit.runner.Description;
/*  11:    */ import org.junit.runner.Runner;
/*  12:    */ import org.junit.runner.notification.RunNotifier;
/*  13:    */ import org.junit.runners.model.InitializationError;
/*  14:    */ import org.junit.runners.model.RunnerBuilder;
/*  15:    */ 
/*  16:    */ public class Suite
/*  17:    */   extends ParentRunner<Runner>
/*  18:    */ {
/*  19:    */   private final List<Runner> fRunners;
/*  20:    */   
/*  21:    */   public static Runner emptySuite()
/*  22:    */   {
/*  23:    */     try
/*  24:    */     {
/*  25: 30 */       return new Suite((Class)null, new Class[0]);
/*  26:    */     }
/*  27:    */     catch (InitializationError e)
/*  28:    */     {
/*  29: 32 */       throw new RuntimeException("This shouldn't be possible");
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   private static Class<?>[] getAnnotatedClasses(Class<?> klass)
/*  34:    */     throws InitializationError
/*  35:    */   {
/*  36: 51 */     SuiteClasses annotation = (SuiteClasses)klass.getAnnotation(SuiteClasses.class);
/*  37: 52 */     if (annotation == null) {
/*  38: 53 */       throw new InitializationError(String.format("class '%s' must have a SuiteClasses annotation", new Object[] { klass.getName() }));
/*  39:    */     }
/*  40: 54 */     return annotation.value();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Suite(Class<?> klass, RunnerBuilder builder)
/*  44:    */     throws InitializationError
/*  45:    */   {
/*  46: 67 */     this(builder, klass, getAnnotatedClasses(klass));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Suite(RunnerBuilder builder, Class<?>[] classes)
/*  50:    */     throws InitializationError
/*  51:    */   {
/*  52: 79 */     this(null, builder.runners(null, classes));
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected Suite(Class<?> klass, Class<?>[] suiteClasses)
/*  56:    */     throws InitializationError
/*  57:    */   {
/*  58: 89 */     this(new AllDefaultPossibilitiesBuilder(true), klass, suiteClasses);
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected Suite(RunnerBuilder builder, Class<?> klass, Class<?>[] suiteClasses)
/*  62:    */     throws InitializationError
/*  63:    */   {
/*  64:101 */     this(klass, builder.runners(klass, suiteClasses));
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected Suite(Class<?> klass, List<Runner> runners)
/*  68:    */     throws InitializationError
/*  69:    */   {
/*  70:112 */     super(klass);
/*  71:113 */     this.fRunners = runners;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected List<Runner> getChildren()
/*  75:    */   {
/*  76:118 */     return this.fRunners;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected Description describeChild(Runner child)
/*  80:    */   {
/*  81:123 */     return child.getDescription();
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void runChild(Runner runner, RunNotifier notifier)
/*  85:    */   {
/*  86:128 */     runner.run(notifier);
/*  87:    */   }
/*  88:    */   
/*  89:    */   @Retention(RetentionPolicy.RUNTIME)
/*  90:    */   @Target({java.lang.annotation.ElementType.TYPE})
/*  91:    */   @Inherited
/*  92:    */   public static @interface SuiteClasses
/*  93:    */   {
/*  94:    */     Class<?>[] value();
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.Suite
 * JD-Core Version:    0.7.0.1
 */