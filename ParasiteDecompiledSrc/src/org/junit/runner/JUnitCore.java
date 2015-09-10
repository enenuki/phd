/*   1:    */ package org.junit.runner;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.lang.annotation.Annotation;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import junit.framework.Test;
/*   8:    */ import junit.runner.Version;
/*   9:    */ import org.junit.internal.JUnitSystem;
/*  10:    */ import org.junit.internal.RealSystem;
/*  11:    */ import org.junit.internal.TextListener;
/*  12:    */ import org.junit.internal.runners.JUnit38ClassRunner;
/*  13:    */ import org.junit.runner.notification.Failure;
/*  14:    */ import org.junit.runner.notification.RunListener;
/*  15:    */ import org.junit.runner.notification.RunNotifier;
/*  16:    */ 
/*  17:    */ public class JUnitCore
/*  18:    */ {
/*  19:    */   private RunNotifier fNotifier;
/*  20:    */   
/*  21:    */   public JUnitCore()
/*  22:    */   {
/*  23: 34 */     this.fNotifier = new RunNotifier();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static void main(String... args)
/*  27:    */   {
/*  28: 45 */     runMainAndExit(new RealSystem(), args);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static void runMainAndExit(JUnitSystem system, String... args)
/*  32:    */   {
/*  33: 53 */     Result result = new JUnitCore().runMain(system, args);
/*  34: 54 */     system.exit(result.wasSuccessful() ? 0 : 1);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static Result runClasses(Computer computer, Class<?>... classes)
/*  38:    */   {
/*  39: 66 */     return new JUnitCore().run(computer, classes);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static Result runClasses(Class<?>... classes)
/*  43:    */   {
/*  44: 76 */     return new JUnitCore().run(defaultComputer(), classes);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Result runMain(JUnitSystem system, String... args)
/*  48:    */   {
/*  49: 84 */     system.out().println("JUnit version " + Version.id());
/*  50: 85 */     List<Class<?>> classes = new ArrayList();
/*  51: 86 */     List<Failure> missingClasses = new ArrayList();
/*  52: 87 */     for (String each : args) {
/*  53:    */       try
/*  54:    */       {
/*  55: 89 */         classes.add(Class.forName(each));
/*  56:    */       }
/*  57:    */       catch (ClassNotFoundException e)
/*  58:    */       {
/*  59: 91 */         system.out().println("Could not find class: " + each);
/*  60: 92 */         Description description = Description.createSuiteDescription(each, new Annotation[0]);
/*  61: 93 */         Failure failure = new Failure(description, e);
/*  62: 94 */         missingClasses.add(failure);
/*  63:    */       }
/*  64:    */     }
/*  65: 96 */     RunListener listener = new TextListener(system);
/*  66: 97 */     addListener(listener);
/*  67: 98 */     Result result = run((Class[])classes.toArray(new Class[0]));
/*  68: 99 */     for (Failure each : missingClasses) {
/*  69:100 */       result.getFailures().add(each);
/*  70:    */     }
/*  71:101 */     return result;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getVersion()
/*  75:    */   {
/*  76:108 */     return Version.id();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Result run(Class<?>... classes)
/*  80:    */   {
/*  81:117 */     return run(Request.classes(defaultComputer(), classes));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Result run(Computer computer, Class<?>... classes)
/*  85:    */   {
/*  86:127 */     return run(Request.classes(computer, classes));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Result run(Request request)
/*  90:    */   {
/*  91:136 */     return run(request.getRunner());
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Result run(Test test)
/*  95:    */   {
/*  96:145 */     return run(new JUnit38ClassRunner(test));
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Result run(Runner runner)
/* 100:    */   {
/* 101:152 */     Result result = new Result();
/* 102:153 */     RunListener listener = result.createListener();
/* 103:154 */     this.fNotifier.addFirstListener(listener);
/* 104:    */     try
/* 105:    */     {
/* 106:156 */       this.fNotifier.fireTestRunStarted(runner.getDescription());
/* 107:157 */       runner.run(this.fNotifier);
/* 108:158 */       this.fNotifier.fireTestRunFinished(result);
/* 109:    */     }
/* 110:    */     finally
/* 111:    */     {
/* 112:160 */       removeListener(listener);
/* 113:    */     }
/* 114:162 */     return result;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void addListener(RunListener listener)
/* 118:    */   {
/* 119:171 */     this.fNotifier.addListener(listener);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void removeListener(RunListener listener)
/* 123:    */   {
/* 124:179 */     this.fNotifier.removeListener(listener);
/* 125:    */   }
/* 126:    */   
/* 127:    */   static Computer defaultComputer()
/* 128:    */   {
/* 129:183 */     return new Computer();
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.JUnitCore
 * JD-Core Version:    0.7.0.1
 */