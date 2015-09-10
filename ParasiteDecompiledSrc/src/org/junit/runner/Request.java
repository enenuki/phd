/*   1:    */ package org.junit.runner;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
/*   5:    */ import org.junit.internal.requests.ClassRequest;
/*   6:    */ import org.junit.internal.requests.FilterRequest;
/*   7:    */ import org.junit.internal.requests.SortingRequest;
/*   8:    */ import org.junit.internal.runners.ErrorReportingRunner;
/*   9:    */ import org.junit.runner.manipulation.Filter;
/*  10:    */ import org.junit.runners.model.InitializationError;
/*  11:    */ 
/*  12:    */ public abstract class Request
/*  13:    */ {
/*  14:    */   public static Request method(Class<?> clazz, String methodName)
/*  15:    */   {
/*  16: 35 */     Description method = Description.createTestDescription(clazz, methodName);
/*  17: 36 */     return aClass(clazz).filterWith(method);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Request aClass(Class<?> clazz)
/*  21:    */   {
/*  22: 46 */     return new ClassRequest(clazz);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static Request classWithoutSuiteMethod(Class<?> clazz)
/*  26:    */   {
/*  27: 56 */     return new ClassRequest(clazz, false);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Request classes(Computer computer, Class<?>... classes)
/*  31:    */   {
/*  32:    */     try
/*  33:    */     {
/*  34: 68 */       AllDefaultPossibilitiesBuilder builder = new AllDefaultPossibilitiesBuilder(true);
/*  35: 69 */       Runner suite = computer.getSuite(builder, classes);
/*  36: 70 */       return runner(suite);
/*  37:    */     }
/*  38:    */     catch (InitializationError e)
/*  39:    */     {
/*  40: 72 */       throw new RuntimeException("Bug in saff's brain: Suite constructor, called as above, should always complete");
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static Request classes(Class<?>... classes)
/*  45:    */   {
/*  46: 84 */     return classes(JUnitCore.defaultComputer(), classes);
/*  47:    */   }
/*  48:    */   
/*  49:    */   @Deprecated
/*  50:    */   public static Request errorReport(Class<?> klass, Throwable cause)
/*  51:    */   {
/*  52: 93 */     return runner(new ErrorReportingRunner(klass, cause));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Request runner(Runner runner)
/*  56:    */   {
/*  57:101 */     new Request()
/*  58:    */     {
/*  59:    */       public Runner getRunner()
/*  60:    */       {
/*  61:104 */         return this.val$runner;
/*  62:    */       }
/*  63:    */     };
/*  64:    */   }
/*  65:    */   
/*  66:    */   public abstract Runner getRunner();
/*  67:    */   
/*  68:    */   public Request filterWith(Filter filter)
/*  69:    */   {
/*  70:122 */     return new FilterRequest(this, filter);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Request filterWith(Description desiredDescription)
/*  74:    */   {
/*  75:132 */     return filterWith(Filter.matchMethodDescription(desiredDescription));
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Request sortWith(Comparator<Description> comparator)
/*  79:    */   {
/*  80:159 */     return new SortingRequest(this, comparator);
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.Request
 * JD-Core Version:    0.7.0.1
 */