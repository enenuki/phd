/*  1:   */ package org.junit.internal.requests;
/*  2:   */ 
/*  3:   */ import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
/*  4:   */ import org.junit.runner.Request;
/*  5:   */ import org.junit.runner.Runner;
/*  6:   */ 
/*  7:   */ public class ClassRequest
/*  8:   */   extends Request
/*  9:   */ {
/* 10:   */   private final Class<?> fTestClass;
/* 11:   */   private boolean fCanUseSuiteMethod;
/* 12:   */   
/* 13:   */   public ClassRequest(Class<?> testClass, boolean canUseSuiteMethod)
/* 14:   */   {
/* 15:14 */     this.fTestClass = testClass;
/* 16:15 */     this.fCanUseSuiteMethod = canUseSuiteMethod;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ClassRequest(Class<?> testClass)
/* 20:   */   {
/* 21:19 */     this(testClass, true);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Runner getRunner()
/* 25:   */   {
/* 26:24 */     return new AllDefaultPossibilitiesBuilder(this.fCanUseSuiteMethod).safeRunnerForClass(this.fTestClass);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.requests.ClassRequest
 * JD-Core Version:    0.7.0.1
 */